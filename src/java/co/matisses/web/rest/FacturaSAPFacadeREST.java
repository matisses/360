package co.matisses.web.rest;

import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.web.dto.ClasificacionImpuestosDTO;
import co.matisses.web.dto.DetalleImpuestosDTO;
import co.matisses.web.dto.ListaFacturasAnulablesResponseDTO;
import co.matisses.web.dto.TirillaZDTO;
import co.matisses.web.dto.VentaPOSDTO;
import co.matisses.web.dto.epson.Customer;
import co.matisses.web.dto.epson.InvoiceData;
import co.matisses.web.dto.epson.InvoiceDetail;
import co.matisses.web.dto.epson.InvoicePaymentDetail;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("factura")
public class FacturaSAPFacadeREST {

    private static final Logger log = Logger.getLogger(FacturaSAPFacadeREST.class.getSimpleName());

    @EJB
    private FacturaSAPFacade facturaFacade;
    @EJB
    private AlmacenFacade almacenFacade;

    public FacturaSAPFacadeREST() {
    }

    public TirillaZDTO generarDatosInicialesTirilla(String almacen, Integer idTurnoCaja, Date fecha) {
        TirillaZDTO tirilla = new TirillaZDTO();
        //consultar datos configurados para el detalle de la factura
        // 0. Cod Ciudad
        // 1. Codigo serie numeracion
        // 2. Nombre serie numeracion
        Object[] datosFacturaAlmacen = almacenFacade.cargarDatosFacturacionAlmacen(almacen);
        tirilla.setSerie((String) datosFacturaAlmacen[2]);
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(fecha);
        List<Object[]> filas = facturaFacade.getInvoiceTaxInfoByDate((String) datosFacturaAlmacen[1],
                (String) datosFacturaAlmacen[13],
                String.valueOf(cal.get(Calendar.YEAR)),
                String.valueOf(cal.get(Calendar.MONTH) + 1),
                String.valueOf(cal.get(Calendar.DATE)),
                idTurnoCaja);

        List<String> facturas = new ArrayList<>();
        for (Object[] cols : filas) {
            ClasificacionImpuestosDTO imp = new ClasificacionImpuestosDTO();
            imp.setNombre((String) cols[1]);

            BigDecimal totalImpuesto = (BigDecimal) cols[4];
            BigDecimal valorBase = (BigDecimal) cols[3];
            DetalleImpuestosDTO det = new DetalleImpuestosDTO();
            det.setNombreImpuesto((String) cols[2]);
            det.setTotal(totalImpuesto.floatValue());
            det.setValorBase(valorBase.floatValue());
            det.setValorDescuento(0f);
            det.setValorImpuesto(totalImpuesto.floatValue() - valorBase.floatValue());
            imp.agregarDetalleImpuestos(det);
            tirilla.agregarClasificacionImpuestos(imp);
            if (!facturas.contains((String) cols[0])) {
                facturas.add((String) cols[0]);
            }
        }
        tirilla.setNumeroAnulaciones(facturaFacade.getCreditNotesCount(idTurnoCaja).toString());
        tirilla.setNumeroFacturas(String.valueOf(facturas.size()));
        return tirilla;
    }

    @POST
    @Path("consultarDatosCierre/{almacen}/{idTurnoCaja}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarDatosCierreCaja(@PathParam("almacen") String almacen, @PathParam("idTurnoCaja") Integer idTurnoCaja) {
        return Response.ok(generarDatosInicialesTirilla(almacen, idTurnoCaja, new Date())).build();
    }

    @GET
    @Path("anular/lista/{turnoPOS}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response listVoidableInvoices(@PathParam("turnoPOS") Integer turnoPOS) {

        try {
            List<VentaPOSDTO> facturas = new ArrayList<>();
            List<Object[]> datos = facturaFacade.listVoidableInvoices(turnoPOS);
            for (Object[] row : datos) {
                Integer docNum = (Integer) row[0];
                String nit = (String) row[1];
                String nombreCliente = (String) row[2];
                Integer total = (Integer) row[3];
                String prefijo = (String) row[4];

                VentaPOSDTO dto = new VentaPOSDTO();
                dto.setNit(nit);
                dto.setNombreCliente(nombreCliente);
                dto.setTotalVenta(total);
                dto.setNumeroFactura(Integer.toString(docNum));
                dto.setPrefijoFactura(prefijo);

                facturas.add(dto);
            }
            return Response.ok(new ListaFacturasAnulablesResponseDTO(facturas)).build();
        } catch (NoResultException e) {
            log.log(Level.SEVERE, "No se encontro ninguna factura apta para ser anulada. ");
            return Response.ok(new ListaFacturasAnulablesResponseDTO("-1", "No se encontró ninguna factura apta para ser anulada. ")).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible consultar las facturas aptas para ser anuladas. " + e.getMessage(), e);
            return Response.ok(new ListaFacturasAnulablesResponseDTO("-1", "No fue posible consultar las facturas aptas para ser anuladas. ")).build();
        }
    }

    @GET
    @Path("consulta/anulacion/{nroFactura}/{nroNotaCredito}/{cashRegister}/{cashierName}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response loadCreditNoteInfo(@PathParam("nroFactura") String nroFactura, @PathParam("nroNotaCredito") String nroNotaCredito,
            @PathParam("cashRegister") String cashRegister, @PathParam("cashierName") String cashierName) {
        InvoiceData data = new InvoiceData();
        data.setCashRegister(cashRegister);
        data.setCashierName(cashierName);
        data.setCreditNoteNumber(nroNotaCredito);

        boolean cashPaymentProcessed = false;
        boolean balancePaymentProcessed = false;
        for (Object[] row : facturaFacade.getVoidedInvoiceInfo(nroFactura)) {
            if (data.getInvoiceNumber() == null) {
                data.setInvoiceNumber((String) row[10]);
            }

            if (data.getCustomer() == null) {
                Customer customer = new Customer();
                customer.setId((String) row[0]);
                customer.setName((String) row[1]);
                data.setCustomer(customer);
            }

            InvoiceDetail item = new InvoiceDetail();
            item.setItemCode((String) row[6]);
            item.setItemName((String) row[7]);
            item.setPrice((Integer) row[8]);
            item.setQuantity((Integer) row[11]);

            if (!data.getItems().contains(item)) {
                data.getItems().add(item);
            }

            if (!cashPaymentProcessed && row[2] != null) {
                cashPaymentProcessed = true;
                InvoicePaymentDetail payment = new InvoicePaymentDetail();
                payment.setPaymentType("Efectivo");
                payment.setRequiresCashDrawer(true);
                payment.setValuePaid(((Integer) row[2]).floatValue());
                data.getPayments().add(payment);
            }

            if (!balancePaymentProcessed && row[9] != null) {
                balancePaymentProcessed = true;
                InvoicePaymentDetail payment = new InvoicePaymentDetail();
                payment.setPaymentType("Otro");
                payment.setRequiresCashDrawer(false);
                payment.setValuePaid(((Integer) row[9]).floatValue());
                data.getPayments().add(payment);
            }

            if (row[3] != null) {
                InvoicePaymentDetail payment = new InvoicePaymentDetail((String) row[3]);
                if (!data.getPayments().contains(payment)) {
                    payment.setPaymentType((String) row[4]);
                    payment.setRequiresCashDrawer(false);
                    payment.setValuePaid(((Integer) row[5]).floatValue());
                    data.getPayments().add(payment);
                }
            }
        }
        return Response.ok(data).build();
    }
}
