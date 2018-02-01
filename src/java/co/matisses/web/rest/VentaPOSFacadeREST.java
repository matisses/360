package co.matisses.web.rest;

import co.matisses.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDetailDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptLocationsDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptServiceConnector;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptServiceException;
import co.matisses.b1ws.client.invoices.InvoiceServiceException;
import co.matisses.b1ws.client.invoices.InvoicesServiceConnector;
import co.matisses.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.b1ws.client.journalentries.JournalEntryServiceException;
import co.matisses.b1ws.client.orders.OrderServiceException;
import co.matisses.b1ws.client.orders.OrdersServiceConnector;
import co.matisses.b1ws.client.payments.IncomingPaymentServiceConnector;
import co.matisses.b1ws.client.payments.IncomingPaymentServiceException;
import co.matisses.b1ws.dto.CreditCardPaymentDTO;
import co.matisses.b1ws.dto.JournalEntryDTO;
import co.matisses.b1ws.dto.JournalEntryLineDTO;
import co.matisses.b1ws.dto.OrderDTO;
import co.matisses.b1ws.dto.OrderDetailDTO;
import co.matisses.b1ws.dto.PaymentAccountDTO;
import co.matisses.b1ws.dto.PaymentDTO;
import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ReciboCaja;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.DetalleVentaPOS;
import co.matisses.persistence.web.entity.OperacionCaja;
import co.matisses.persistence.web.entity.VentaPOS;
import co.matisses.persistence.web.facade.OperacionCajaFacade;
import co.matisses.persistence.web.facade.VentaPOSFacade;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.DetalleUbicacionFacturaDTO;
import co.matisses.web.dto.DetalleVentaPOSDTO;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.GiftCertificate;
import co.matisses.web.dto.InformacionAlmacenDTO;
import co.matisses.web.dto.InventoryItemDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.PagoCuentaDTO;
import co.matisses.web.dto.PagoTarjetaPOSDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.ResolucionFacturacionDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.UbicacionDetalleVentaPOSDTO;
import co.matisses.web.dto.UbicacionFacturaDTO;
import co.matisses.web.dto.VentaPOSDTO;
import co.matisses.web.dto.VentaPOSResponseDTO;
import co.matisses.web.dto.epson.Customer;
import co.matisses.web.dto.epson.InvoiceBinAllocation;
import co.matisses.web.dto.epson.InvoiceData;
import co.matisses.web.dto.epson.InvoiceDetail;
import co.matisses.web.dto.epson.InvoiceResolution;
import co.matisses.web.dto.epson.InvoiceVATDetail;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import java.io.File;
import java.io.FileFilter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("ventapos")
public class VentaPOSFacadeREST {

    private static final Logger log = Logger.getLogger(VentaPOSFacadeREST.class.getSimpleName());
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private VentaPOSFacade ventaPOSFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;
    @EJB
    private OperacionCajaFacade operacionCajaFacade;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @Inject
    private SendHTMLEmailMBean emailSender;

    public VentaPOSFacadeREST() {
    }

    @GET
    @Path("pendientes/{idTurnoCaja}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response findPending(@PathParam("idTurnoCaja") Integer idTurnoCaja) {
        try {
            List<Object[]> result = ventaPOSFacade.listarVentasPendientes(idTurnoCaja);
            List<VentaPOSDTO> dtos = new ArrayList<>();
            for (Object[] row : result) {
                int pos = 0;
                VentaPOSDTO dto = new VentaPOSDTO();
                dto.setIdVentaPOS(((Integer) row[pos++]).longValue());
                dto.setNit((String) row[pos++]);
                dto.setAlmacen((String) row[pos++]);
                dto.setEstacion((String) row[pos++]);
                dto.setFecha((Date) row[pos++]);
                dto.setUsuario((String) row[pos++]);
                dto.setIdTurnoCaja((Integer) row[pos++]);
                dto.setNumeroProductos((Integer) row[pos++]);
                dto.setTotalVenta((Integer) row[pos++]);

                dtos.add(dto);
            }
            return Response.ok(dtos).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar las ventas pendientes del dia. ", e);
            return Response.ok().build();
        }
    }

    @GET
    @Path("pendientes/productos/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response findPendingProducts(@PathParam("id") Long id) {
        try {
            List<DetalleVentaPOS> detalle = ventaPOSFacade.consultarItemsVentaGuardada(id);
            List<InventoryItemDTO> items = new ArrayList<>();
            for (DetalleVentaPOS det : detalle) {
                InventoryItemDTO dto = new InventoryItemDTO();
                dto.setItemCode(det.getReferencia());
                dto.setItemName(det.getDescripcion());
                dto.setProviderCode(det.getRefProveedor());
                dto.setDescription(det.getDescripcion());
                dto.setCantidad(det.getCantidad());
                dto.setPrice(det.getPrecio().toString());
                dto.setDiscountPercent(det.getDescuento() != null ? det.getDescuento().toString() : null);
                dto.setTaxRate(det.getImpuesto() != null ? det.getImpuesto().toString() : null);

                items.add(dto);
            }
            return Response.ok(items).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al buscar los productos de la venta", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("pendientes/eliminar/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response deletePendingSale(@PathParam("id") Long id) {
        log.log(Level.INFO, "Eliminando la venta POS pendiente con id {0}", id);
        ventaPOSFacade.eliminarVentaPendiente(id);
        return Response.ok().build();
    }

    @POST
    @Path("guardar/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void holdSale(final VentaPOSDTO venta, @Context HttpServletRequest req) {
        if (venta == null) {
            log.log(Level.SEVERE, "No llegaron datos para almacenar la venta");
            return;
        }
        log.info(venta.toString());
        if (venta.getNit() == null || venta.getNit().trim().isEmpty()) {
            log.log(Level.SEVERE, "El NIT es obligatorio para almacenar una venta suspendida");
            return;
        }

        VentaPOS enc = new VentaPOS();
        enc.setAlmacen(venta.getAlmacen());
        enc.setEstacion(req.getRemoteHost());
        enc.setEstado(venta.getEstado());
        enc.setFecha(new Date());
        enc.setNit(venta.getNit());
        enc.setUsuario(venta.getUsuario());
        enc.setIdTurnoCaja(venta.getIdTurnoCaja());

        for (DetalleVentaPOSDTO dto : venta.getProductos()) {
            DetalleVentaPOS det = new DetalleVentaPOS();
            det.setCantidad(dto.getCantidad());
            det.setDescripcion(dto.getDescripcion());
            det.setIdVenta(enc);
            det.setPrecio(dto.getPrecio());
            det.setRefProveedor(dto.getRefProveedor());
            det.setReferencia(dto.getReferencia());
            det.setImpuesto(dto.getImpuesto());
            if (dto.getDescuento() != null) {
                det.setDescuento(dto.getDescuento().intValue());
            }
            enc.getProductos().add(det);
        }

        try {
            ventaPOSFacade.create(enc);
            log.log(Level.INFO, "Se suspendio la venta actual con el id {0}. La venta tiene {1} items. ", new Object[]{enc.getIdVentaPOS(), enc.getProductos().size()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al guardar el encabezado de la venta", e);
        }
    }

    private Object[] createInvoice(Object[] datosFacturaAlmacen, VentaPOSDTO venta, SesionSAPB1WSDTO sesionSAPDTO) throws InvoiceServiceException {
        SalesDocumentDTO enc = new SalesDocumentDTO();
        enc.setBinAbsEntry((String) datosFacturaAlmacen[13]);
        enc.setCardCode(venta.getNit());
        enc.setComments("FACTURA POS creada por 360. " + venta.getComentarios());
        enc.setPaymentGroupCode("17");
        enc.setShippingStatus(venta.getEstadoPedido());
        enc.setSource("P");

        if (venta.getAsesores().isEmpty()) {
            SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
            salesEmp.setSlpCode("98");
            salesEmp.setName("Vendedor Web");

            enc.addSalesEmployee(salesEmp);
        } else {
            for (EmpleadoDTO emp : venta.getAsesores()) {
                SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
                salesEmp.setId(emp.getCedula());
                salesEmp.setSlpCode(emp.getCodigoAsesor());
                salesEmp.setName(emp.getNombreCompleto());
                enc.addSalesEmployee(salesEmp);
            }
        }

        enc.setSeriesCode((String) datosFacturaAlmacen[1]);
        enc.setWuid((String) datosFacturaAlmacen[7]);
        enc.setPosShiftId(venta.getIdVentaPOS());
        enc.setLogisticsCostingCode((String) datosFacturaAlmacen[4]);
        enc.setRouteCostingCode((String) datosFacturaAlmacen[5]);
        enc.setSalesCostingCode((String) datosFacturaAlmacen[3]);
        enc.setProjectCode((String) datosFacturaAlmacen[6]);

        List<SalesDocumentLineDTO> detDtos = new ArrayList<>();
        int lineNum = 0;
        for (DetalleVentaPOSDTO detDto : venta.getProductos()) {
            //agrega las ubicaciones (y cantidades) desde donde se facturara la mercancia
            for (UbicacionDetalleVentaPOSDTO ub : detDto.getUbicaciones()) {
                if (ub.getCantidad() > 0) {
                    SalesDocumentLineBinAllocationDTO binAllocDto = new SalesDocumentLineBinAllocationDTO();
                    binAllocDto.setBinAbsEntry(ub.getBinAbsEntry());
                    binAllocDto.setQuantity(ub.getCantidad());
                    binAllocDto.setWhsCode(ub.getAlmacen());

                    SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO(detDto.getReferencia(), binAllocDto.getWhsCode(), detDto.getPrecio().doubleValue(), detDto.getDescuento());
                    int pos = detDtos.indexOf(lineDto);
                    if (pos >= 0) {
                        lineDto = detDtos.get(pos);
                        lineDto.addBinAllocation(binAllocDto);
                        lineDto.setQuantity(lineDto.getQuantity() + ub.getCantidad());
                        detDtos.set(pos, lineDto);
                    } else {
                        lineDto.setQuantity(ub.getCantidad());
                        lineDto.addBinAllocation(binAllocDto);
                        lineDto.setLineNum(lineNum++);
                        detDtos.add(lineDto);
                    }
                }
            }
        }
        enc.setDocumentLines(detDtos);
        log.log(Level.INFO, "se insertaron {0} filas para el detalle de la venta", enc.getDocumentLines().size());
        InvoicesServiceConnector sc = sapB1WSBean.getInvoicesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        return new Object[]{sc.createInvoice(enc), enc};
    }

    private Long createIncomingPayment(VentaPOSDTO venta, Long docEntryFactura, Object[] datosFacturaAlmacen, SesionSAPB1WSDTO sesionSAPDTO) throws IncomingPaymentServiceException {
        //Si hay pagos en efectivo, bono o tarjeta, lo registra
        if ((venta.getPagosTarjeta() != null && !venta.getPagosTarjeta().isEmpty())
                || (venta.getEfectivo() != null && venta.getEfectivo() > 0)) {
            PaymentDTO pagoDto = new PaymentDTO();
            pagoDto.setCardCode(venta.getNit());
            pagoDto.setCreditType("I");
            if (docEntryFactura != null) {
                pagoDto.setInvoiceDocEntry(docEntryFactura.toString());
                pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
            } else {
                pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.ACCOUNT);
            }
            pagoDto.setCashAccount(venta.getCuentaEfectivo());
            pagoDto.setPaidCash(venta.getEfectivo().toString());
            pagoDto.setPaidTotal(venta.getTotalVenta().toString());
            pagoDto.setSeriesCode((String) datosFacturaAlmacen[8]);
            //pagoDto.setDocType(ConstantTypes.DocType.INVOICE);

            List<PaymentAccountDTO> accountPayments = new ArrayList<>();
            for (PagoCuentaDTO pagoCuenta : venta.getPagosCuenta()) {
                PaymentAccountDTO paymentAccount = new PaymentAccountDTO();
                paymentAccount.setAccountCode(pagoCuenta.getAccountCode());
                paymentAccount.setSumPaid(pagoCuenta.getSumPaid());
                accountPayments.add(paymentAccount);
            }
            pagoDto.setAccountPayments(accountPayments);

            List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();
            for (PagoTarjetaPOSDTO pagoTarjetaPOS : venta.getPagosTarjeta()) {
                CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();
                creditPayment.setCreditCardCode(pagoTarjetaPOS.getFranquicia());
                creditPayment.setCreditCardNumber(pagoTarjetaPOS.getDigitos());
                creditPayment.setNumberOfPayments("1");
                creditPayment.setPaidSum(pagoTarjetaPOS.getValor());
                creditPayment.setValidUntil(null);
                //TODO: configurar fecha de validez
                creditPayment.setVoucherNumber(pagoTarjetaPOS.getVoucher());
                creditCardPayments.add(creditPayment);
            }
            pagoDto.setCreditCardPayments(creditCardPayments);

            IncomingPaymentServiceConnector ipsc = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            return ipsc.addPayment(pagoDto);
        } else {
            log.log(Level.INFO, "No se agregaron pagos a la factura, por lo tanto no se generara recibo de caja");
            return 0L;
        }
    }

    private Long createJournalEntry(Long docEntryFactura, SesionSAPB1WSDTO sesionSAPDTO) throws JournalEntryServiceException {
        //Si hay productos en consignacion, realiza el asiento contable correspondiente
        List<Object[]> asiento = facturaSAPFacade.consultarDatosAsientoConsignacionFactura(docEntryFactura);
        if (!asiento.isEmpty()) {
            JournalEntryDTO journalEntryHeader = null;
            for (Object[] row : asiento) {
                if (journalEntryHeader == null) {
                    journalEntryHeader = new JournalEntryDTO();
                    journalEntryHeader.setDueDate((Date) row[0]);
                    journalEntryHeader.setTaxDate((Date) row[0]);
                    journalEntryHeader.setRefDate((Date) row[0]);
                    journalEntryHeader.setMemo((String) row[1]);
                    journalEntryHeader.setRef1((String) row[2]);
                    journalEntryHeader.setRef2((String) row[3]);
                    journalEntryHeader.setRef3((String) row[4]);
                    journalEntryHeader.setTransactionCode((String) row[5]);
                }

                JournalEntryLineDTO line = new JournalEntryLineDTO();
                line.setRef1((String) row[2]);
                line.setRef2((String) row[3]);

                line.setLineId(((BigInteger) row[6]).longValue());
                line.setShortName((String) row[7]);
                line.setLineMemo((String) row[8]);
                line.setOcrCode2((String) row[9]);
                line.setProject((String) row[10]);
                line.setInfoCo01((String) row[11]);
                line.setDebit(((Integer) row[12]).doubleValue());
                line.setCredit(((Integer) row[13]).doubleValue());

                journalEntryHeader.addLine(line);
            }
            JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            return jesc.createJournalEntry(journalEntryHeader);
        } else {
            return 0L;
        }
    }

    private Long createGoodsReceipt(FacturaSAP facturaSAP, SesionSAPB1WSDTO sesionSAPDTO, SalesDocumentDTO enc) throws GoodsReceiptServiceException {
        GoodsReceiptDTO entrada = new GoodsReceiptDTO();
        entrada.setComments("Entrada de mcia para FV " + facturaSAP.getDocNum() + " y cliente " + facturaSAP.getCardCode());
        entrada.setInvoiceNumber(Integer.toString(facturaSAP.getDocNum()));
        entrada.setJournalMemo("Goods Receipt");
        entrada.setSeries(69L); //TODO: parametrizar numero de serie de entradas de mcia de clientes
        entrada.setOrigen("P"); //Origen: POS
        for (SalesDocumentLineDTO detalle : enc.getDocumentLines()) {
            InformacionAlmacenDTO infoAlmacenVenta = baruAplicationBean.getInfoAlmacen(detalle.getWhsCode());
            InformacionAlmacenDTO infoAlmacenCliente = baruAplicationBean.getInfoAlmacen(infoAlmacenVenta.getAlmacenClientes());

            GoodsReceiptLocationsDTO ubEntrada = new GoodsReceiptLocationsDTO();
            ubEntrada.setBinAbs(infoAlmacenCliente.getIdUbicacionTM());
            ubEntrada.setQuantity(detalle.getQuantity());

            GoodsReceiptDetailDTO detEntrada = new GoodsReceiptDetailDTO();
            detEntrada.addLocation(ubEntrada);
            detEntrada.setAccountCode("91051001"); //TODO: parametrizar cuenta de mcia de clientes
            detEntrada.setItemCode(detalle.getItemCode());
            detEntrada.setLineNum(detalle.getLineNum().longValue());
            detEntrada.setQuantity(detalle.getQuantity());

            detEntrada.setWhsCode(infoAlmacenVenta.getAlmacenClientes());//El whsCode se consulta ya que para productos en consignacion se manejan almacenes especiales
            detEntrada.setPrice(detalle.getPrice());

            entrada.addDetail(detEntrada);
        }
        GoodsReceiptServiceConnector grsc = sapB1WSBean.getGoodsReceiptServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        return grsc.createDocument(entrada);
    }

    private Long createSalesOrder(VentaPOSDTO venta, FacturaSAP facturaSAP, SalesDocumentDTO enc, SesionSAPB1WSDTO sesionSAPDTO) throws OrderServiceException {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);
        OrderDTO orderDto = new OrderDTO();
        orderDto.setCardCode(venta.getNit());
        orderDto.setComments(venta.getComentarios());
        orderDto.setDocDate(new Date());
        orderDto.setDocDueDate(cal.getTime());
        orderDto.setInvoiceNumber(Integer.toString(facturaSAP.getDocNum()));
        orderDto.setOrigen("P");
        orderDto.setSalesPersonCode(facturaSAP.getSlpCode().longValue());
        orderDto.setSeries(13L); //TODO: parametrizar codigo de numero de serie de ordenes de venta
        for (SalesDocumentLineDTO detalle : enc.getDocumentLines()) {
            InformacionAlmacenDTO infoAlmacenVenta = baruAplicationBean.getInfoAlmacen(detalle.getWhsCode());
            OrderDetailDTO detailDto = new OrderDetailDTO();
            detailDto.setEstado(venta.getEstadoPedido());
            detailDto.setItemCode(detalle.getItemCode());
            detailDto.setLineNum(detalle.getLineNum().longValue());
            detailDto.setQuantity(detalle.getQuantity().doubleValue());
            detailDto.setWarehouseCode(infoAlmacenVenta.getAlmacenClientes());
            orderDto.addLine(detailDto);
        }

        OrdersServiceConnector osc = sapB1WSBean.getOrderServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        return osc.createOrder(orderDto);
    }

    @POST
    @Path("facturar/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response createInvoice(final VentaPOSDTO venta) {
        if (venta == null) {
            log.log(Level.SEVERE, "No llegaron datos para almacenar la venta");
            return Response.ok(new VentaPOSResponseDTO("-1", "No llegaron datos para almacenar la venta")).build();
        }
        log.log(Level.INFO, "Registrando una nueva venta POS. {0}", venta.toString());
        if (venta.getNit() == null || venta.getNit().trim().isEmpty()) {
            log.log(Level.SEVERE, "El NIT es obligatorio para registrar una venta.");
            return Response.ok(new VentaPOSResponseDTO("-1", "El NIT es obligatorio para registrar una venta")).build();
        }
        if (venta.getAlmacen() == null || venta.getAlmacen().trim().isEmpty()) {
            log.log(Level.SEVERE, "Se debe indicar el almacén desde donde se registra la venta.");
            return Response.ok(new VentaPOSResponseDTO("-1", "Se debe indicar el almacén desde donde se registra la venta")).build();
        }
        if (venta.getIdVentaPOS() == null || venta.getIdVentaPOS() <= 0) {
            log.log(Level.SEVERE, "Se debe indicar el ID del turno de caja.");
            return Response.ok(new VentaPOSResponseDTO("-1", "No se recibió el ID del turno de caja para asociarlo con la factura. Cierre sesión y vuelva a intentarlo.")).build();
        }

        SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(venta.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new VentaPOSResponseDTO("-1", "No fue posible iniciar una sesion en SAP B1WS. Contacte al departamento de sistemas para recibir ayuda.")).build();
        }

        try {
            //consultar datos configurados para el detalle de la factura
            // 0. Cod Ciudad
            // 1. Codigo serie numeracion
            // 2. Nombre serie numeracion
            // 3. Codigo ventas
            // 4. Codigo logistica
            // 5. Codigo ruta
            // 6. Codigo proyecto
            // 7. WUID
            // 8. Serie recibo
            // 9. Resolucion fact. desde
            //10. Resolucion fact. hasta
            //11. Resolucion fact. numero y fecha (separados por "de")
            //12. Resolucion fact. prefijo
            //13. ID ubicacion COMPLEMENTOS para el almacen
            //14. Serie anulacion
            Object[] datosFacturaAlmacen = new Object[100];

            OperacionCaja operacion = operacionCajaFacade.obtenerValorAperturaTurno(venta.getIdVentaPOS().intValue());

            log.log(Level.WARNING, "Datos de la operacion: {0}", operacion != null ? operacion.toString() : null);

            if (operacion != null && operacion.getIdOperacionCaja() != null && operacion.getIdOperacionCaja() != 0 && operacion.getValor() == 400000) {
                datosFacturaAlmacen = almacenFacade.cargarDatosFacturacionAlmacen(venta.getAlmacen());
                if (datosFacturaAlmacen == null || datosFacturaAlmacen.length != 15) {
                    log.log(Level.SEVERE, "No fue posible obtener los datos de configuración para la factura a partir del almacén (serie, ventas, logistica, ruta, wuid).");
                    return Response.ok(new VentaPOSResponseDTO("-1", "No fue posible obtener los datos de configuración para la factura a partir del almacén (serie, ventas, logistica, ruta, wuid)")).build();
                }
            } else {
                datosFacturaAlmacen = almacenFacade.cargarDatosFacturacionAlmacenHotSale(venta.getAlmacen());
                if (datosFacturaAlmacen == null || datosFacturaAlmacen.length != 15) {
                    log.log(Level.SEVERE, "No fue posible obtener los datos de configuración para la factura a partir del almacén (serie, ventas, logistica, ruta, wuid).");
                    return Response.ok(new VentaPOSResponseDTO("-1", "No fue posible obtener los datos de configuración para la factura a partir del almacén (serie, ventas, logistica, ruta, wuid)")).build();
                }
            }

            //Crea la factura
            Object[] respuestaFactura = createInvoice(datosFacturaAlmacen, venta, sesionSAPDTO);
            Long docEntryFactura = (Long) respuestaFactura[0];
            SalesDocumentDTO enc = (SalesDocumentDTO) respuestaFactura[1];
            if (docEntryFactura == null) {
                log.log(Level.SEVERE, "No se pudo registrar la factura en SAP.");
                return Response.ok(new VentaPOSResponseDTO("-1", "No se pudo registrar la factura en SAP")).build();
            }

            //Consultar numero de factura y unir numero y prefijo
            String prefijo = (String) datosFacturaAlmacen[2];
            FacturaSAP facturaSAP = facturaSAPFacade.findNoTransaction(docEntryFactura.intValue());
            log.log(Level.INFO, "Se creo la factura {0}{1}", new Object[]{prefijo, Integer.toString(facturaSAP.getDocNum()).substring(2)});

            try {
                //Si se registraron pagos, crea un recibo de caja
                Long docEntryRecibo = createIncomingPayment(venta, docEntryFactura, datosFacturaAlmacen, sesionSAPDTO);
                if (docEntryRecibo > 0) {
                    log.log(Level.INFO, "Se creo el recibo de caja con docEntry={0}", docEntryRecibo);
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "No se pudo registrar el pago para la factura. ", e);
            }
            try {
                //Si se facturaron productos en consignacion, crea el asiento contable correspondiente
                Long docEntryAsiento = createJournalEntry(docEntryFactura, sesionSAPDTO);
                if (docEntryAsiento > 0) {
                    log.log(Level.INFO, "Se creo el asiento contable con ID {0}", docEntryAsiento);
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "No se pudo crear el asiento contable. ", e);
                //TODO: enviar email notificando error con asiento
            }
            //Si hay mercancia en estado pendiente, genera la entrada de mcia de clientes y orden de venta
            if (venta.getEstadoPedido() != null && venta.getEstadoPedido().equals("P")) {
                try {
                    //crea la entrada de mercancia a bodega de clientes
                    Long docEntryEntrada = createGoodsReceipt(facturaSAP, sesionSAPDTO, enc);
                    log.log(Level.INFO, "Se creo la entrada de mercancia de clientes docEntry={0}", docEntryEntrada);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al crear la entrada de mercancia de cliente. ", e);
                    //TODO: notificar error por correo
                }
                try {
                    //crea la orden de venta
                    Long docEntryOrden = createSalesOrder(venta, facturaSAP, enc, sesionSAPDTO);
                    log.log(Level.INFO, "Se creo la orden de venta con docEntry={0}", docEntryOrden);
                } catch (Exception e) {
                    log.log(Level.WARNING, "Ocurrio un error al crear la orden de venta. {0}", e.getMessage());
                    //TODO: enviar mail de notificacion de error al crear OV
                }
            } else {
                log.log(Level.INFO, "No se crean documentos de orden de venta ni entrada de mercancia ya que el pedido fue entregado a satisfaccion");
            }

            String fechaYNumeroResolucion = (String) datosFacturaAlmacen[11];
            int posDe = fechaYNumeroResolucion.indexOf("de");
            String fechaResolucion = fechaYNumeroResolucion.substring(posDe + 3);
            String numeroResolucion = fechaYNumeroResolucion.substring(0, posDe - 1);
            String numeroInicio = Integer.valueOf(((String) datosFacturaAlmacen[9]).substring(2)).toString();
            String numeroFin = Integer.valueOf(((String) datosFacturaAlmacen[10]).substring(2)).toString();
            ResolucionFacturacionDTO resDto = new ResolucionFacturacionDTO(
                    (String) datosFacturaAlmacen[12], //prefijo
                    numeroInicio, //desde
                    numeroFin, //hasta
                    fechaResolucion,
                    numeroResolucion);

            //Consulta las ubicaciones desde donde se facturo la mercancia y las agrega a la respuesta
            List<Object[]> binAllocations = facturaSAPFacade.getInvoiceBinAllocations(String.valueOf(facturaSAP.getDocNum()));
            List<UbicacionFacturaDTO> ubicaciones = new ArrayList<>();
            for (Object[] cols : binAllocations) {
                String whsCode = (String) cols[0];
                String binCode = (String) cols[1];
                String itemCode = (String) cols[2];
                Integer quantity = (Integer) cols[3];

                UbicacionFacturaDTO dto = new UbicacionFacturaDTO();
                dto.setBinCode(binCode);
                dto.setWhsCode(whsCode);

                DetalleUbicacionFacturaDTO detDto = new DetalleUbicacionFacturaDTO();
                detDto.setItemCode(itemCode);
                detDto.setQuantity(quantity);
                int pos = ubicaciones.indexOf(dto);
                if (pos >= 0) {
                    ubicaciones.get(pos).getItems().add(detDto);
                } else {
                    dto.getItems().add(detDto);
                    ubicaciones.add(dto);
                }
            }

            createGiftCertificateCodes(venta.getCertificadosRegalo());
            for (GiftCertificate giftCertificate : venta.getCertificadosRegalo()) {
                log.log(Level.INFO, giftCertificate.getCertificateNumber());
            }

            //enviar mail de notificacion de creacion de factura y documentos anexos
            sendInvoiceNotificationMail(Integer.toString(facturaSAP.getDocNum()), facturaSAP.getCardName(), facturaSAP.getCardCode(), venta.getUsuario());
            return Response.ok(new VentaPOSResponseDTO("0", "Factura creada con éxito", prefijo + Integer.toString(facturaSAP.getDocNum()).substring(2), resDto, ubicaciones, venta.getCertificadosRegalo(), facturaSAP.getDocNum())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el documento en SAP. ", e);
            return Response.ok(new VentaPOSResponseDTO("-1", "No se pudo registrar la factura en SAP. " + e.getMessage())).build();
        }
    }

    private void createGiftCertificateCodes(List<GiftCertificate> certificates) {
        for (GiftCertificate giftCertificate : certificates) {
            String code = baruAplicationBean.codificarClave(String.valueOf(System.currentTimeMillis()));
            giftCertificate.setCertificateNumber(code);
        }
    }

    private void sendInvoiceNotificationMail(String invoiceNumber, String customerName, String customerId, String username) {
        MailMessageDTO mailMessage = new MailMessageDTO();
        //TODO: configurar from, subject, to y cc
        mailMessage.setFrom("Factura POS M6 <posm6-1@matisses.co>");
        mailMessage.setSubject("Factura POS creada");
        mailMessage.addToAddress("dbotero@matisses.co");
        mailMessage.addCcAddress("sistemas@matisses.co");

        Map<String, String> params = new HashMap<>();
        params.put("invoiceNumber", invoiceNumber);
        params.put("customerName", customerName);
        params.put("customerId", customerId);
        params.put("username", username);

        try {
            emailSender.sendMail(mailMessage, SendHTMLEmailMBean.MessageTemplate.factura, params, null);
        } catch (Exception e) {
            log.log(Level.WARNING, "No fue posible enviar el correo de notificacion de factura. ", e);
        }

    }

    @POST
    @Path("venderTarjetaRegalo/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response sellGiftCard(final VentaPOSDTO venta) {
        SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(venta.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new VentaPOSResponseDTO("-1", "No fue posible iniciar una sesion en SAP B1WS. Contacte al departamento de sistemas para recibir ayuda.")).build();
        }
        Object[] datosFacturaAlmacen = almacenFacade.cargarDatosFacturacionAlmacen(venta.getAlmacen());
        //crea recibo de caja
        Long docEntryRC;
        String numeroRecibo;
        try {
            docEntryRC = createIncomingPayment(venta, null, datosFacturaAlmacen, sesionSAPDTO);
            numeroRecibo = consultarNumeroRecibo(docEntryRC);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo registrar el pago para el bono de regalo. ", e);
            return Response.ok(new VentaPOSResponseDTO("-1", "No se pudo registrar el pago para el bono de regalo")).build();
        }
        try {
            //crea salida de insumos para la tarjeta
            DetalleVentaPOSDTO producto = venta.getProductos().get(0);
            int pos1 = producto.getDescripcion().indexOf("(");
            int pos2 = producto.getDescripcion().indexOf("TARJETA", pos1);
            int tarjetas = Integer.parseInt(producto.getDescripcion().substring(pos1 + 1, pos2 - 1));
            crearSalidaMcia(tarjetas, sesionSAPDTO, numeroRecibo, venta.getAlmacen());
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo registrar las salida de insumos para la(s) tarjeta(s) de regalo. ", e);
            //TODO: enviar correo
            //return Response.ok(new VentaPOSResponseDTO("-1", "No se pudo registrar el pago para el bono de regalo")).build();
        }
        //TODO: envia correo
        return Response.ok(new VentaPOSResponseDTO("0", numeroRecibo)).build();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private String consultarNumeroRecibo(Long docEntry) {
        if (docEntry == null) {
            return null;
        }
        try {
            return facturaSAPFacade.consultarDocNumReciboCaja(docEntry.toString()).toString();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el numero de documento del recibo de caja. ", e);
            return null;
        }
    }

    public void crearSalidaMcia(int cantidad, SesionSAPB1WSDTO sesionSAPDTO, String numeroRecibo, String almacen) {
        log.log(Level.INFO, "Creando salida de {0} tarjetas de regalo. {0}", cantidad);
        Object[] datosSalida = almacenFacade.consultarDatosSalida(almacen);
        if (datosSalida == null) {
            log.log(Level.SEVERE, "No se realizo la salida de insumos (tarjeta de regalo) porque no se encontraron datos para el almacen {0}", almacen);
            //TODO: enviar correo error
            return;
        }
        GoodsIssueDTO document = new GoodsIssueDTO();
        document.setComments("Tarjeta(s) de regalo relacionada(s) en el RC# " + numeroRecibo);
        document.setJournalMemo("Salida de mercancias");
        document.setSeries("105"); //TODO: parametrizar numero de serie de salida de mercancias por inventario
        document.setGroupNum(String.valueOf(-1L)); //Ultimo precio de compra
        GoodsIssueDetailDTO detail = new GoodsIssueDetailDTO();
        detail.setAccountCode((String) datosSalida[0]);
        detail.setItemCode((String) datosSalida[1]);
        detail.setLineNum("0");
        detail.setQuantity(Integer.toString(cantidad));
        detail.setWhsCode("IN" + almacen);
        document.addDetail(detail);
        try {
            //crear salida de insumos en sap
            GoodsIssueServiceConnector gisc = sapB1WSBean.getGoodsIssueServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            Long docEntry = gisc.createDocument(document);
            log.log(Level.INFO, "Se creo la salida de insumos {0}", docEntry);
            //TODO: enviar correo notificacion
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el registro de empaque. ", e);
            //TODO: enviar correo error
        }
    }

    @GET
    @Path("consultar/{numero}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response findPendingProducts(@PathParam("numero") String numeroFactura) {
        if (numeroFactura == null) {
            return Response.ok().build();
        }
        List<Object[]> rows = facturaSAPFacade.consultarInformacionParaTirilla(numeroFactura);
        InvoiceData data = new InvoiceData();
        for (Object[] row : rows) {
            if (data.getCustomer() == null) {
                data.setCustomer(new Customer((String) row[0], (String) row[0]));
                data.setInvoiceNumber(numeroFactura);
            }
            InvoiceDetail dataLine = new InvoiceDetail((String) row[0], (String) row[0], (Integer) row[0], (Integer) row[0]);
            if (!data.getItems().contains(dataLine)) {
                data.getItems().add(dataLine);
            }
            InvoiceBinAllocation invoiceAllocation = new InvoiceBinAllocation((String) row[0], (String) row[0]);
            if (!data.getBinAllocations().contains(invoiceAllocation)) {
                data.getBinAllocations().add(invoiceAllocation);
            } else if (!data.getBinAllocations().get(data.getBinAllocations().indexOf(invoiceAllocation)).getItems().contains(dataLine)) {
                dataLine.setQuantity((Integer) row[0]);
                data.getBinAllocations().get(data.getBinAllocations().indexOf(invoiceAllocation)).getItems().add(dataLine);
            }
            if (data.getInvoiceResolution() == null) {
                data.setInvoiceResolution(new InvoiceResolution((String) row[0], (String) row[0], (String) row[0], (Integer) row[0], (Integer) row[0]));
            }
            data.setSalesPersonCode(null);

            InvoiceVATDetail vatDetail = new InvoiceVATDetail((String) row[0], (Float) row[0], (Float) row[0]);
            //if(data.getVatDetail().){

            //}
            //data.getVatDetail().contains(row)
            //data.setVatDetail(vatDetail);
        }
        return Response.ok().build();
    }

    @GET
    @Path("imprimirpc/{docnum}/{almacen}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response printInvoicePC(@PathParam("docnum") int docNum, @PathParam("almacen") String almacen) {

        log.log(Level.INFO, "ingreso metodo facturacion {0}", docNum);
        List<String[]> documentosRelacionados = new ArrayList<>();

        documentosRelacionados.add(new String[]{"reciboCaja\\reciboCaja", "2"});

        String[] s = generarDocumento(docNum, 2, String.valueOf(docNum), "factura", almacen, null, true, documentosRelacionados);
        //notificarProceso(numeroFactura, "factura", null, false, s);
        log.log(Level.INFO, "servicio impresion {0}", s[0]);

        return Response.ok().build();
    }

    private String[] generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);

        PrintRepostClient client = new PrintRepostClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), nombreArchivo + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            //mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    @GET
    @Path("enviarfactura/{docnum}/{almacen}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response enviarCorreoCliente(@PathParam("docnum") int docNum, @PathParam("almacen") String almacen) {
        FacturaSAP facturaSAP = facturaSAPFacade.findByDocNum(docNum);

        String nitClient = facturaSAP.getCardCode();

        SendHtmlEmailClient client = new SendHtmlEmailClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();

        if (!new File(baruAplicationBean.obtenerValorPropiedad("url.folder.facturas") + docNum).exists()) {
            List<String[]> relacionados = new ArrayList<>();
            List<DetalleFacturaSAP> detalle = detalleFacturaSAPFacade.obtenerDetalleFactura(facturaSAPFacade.findByDocNum(docNum).getDocEntry().doubleValue());
            if (detalle != null && !detalle.isEmpty()) {
                for (DetalleFacturaSAP d : detalle) {
                    if (d.getUEstadoP().equals("G")) {
                        relacionados.add(new String[]{"daka\\contratoDaka", "2"});
                        break;
                    }
                }
            }

            ReciboCaja recibo = reciboCajaFacade.obtenerReciboCaja(String.valueOf(docNum));

            if (recibo != null && recibo.getDocEntry() != null && recibo.getDocEntry() != 0) {
                relacionados.add(new String[]{"reciboCaja\\reciboCaja", "2"});
            }

            generarDocumento(docNum, 2, String.valueOf(docNum), "factura", almacen, null, false, relacionados);
        }

        File file = new File(baruAplicationBean.obtenerValorPropiedad("url.folder.facturas") + docNum);

        File[] archivos = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File path) {
                return (!path.isHidden());
            }
        });
        for (File s : archivos) {
            if (s.getPath().contains(".pdf")) {
                adjuntos.add(new String[]{s.getPath(), s.getName()});
            }
        }

        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(nitClient);

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                /*Se escribe el nombre del cliente*/
                String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                        .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                params.put("documento", "factura");
                params.put("tipo", "solicito se le enviara");
                params.put("cliente", nombreCliente);

                res = client.enviarHtmlEmail("Facturas Matisses", "Facturas Matisses", socio.getEmail(), "venta", adjuntos, params);
            }

            log.log(Level.INFO, res.getMensaje());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el correo. ", e);
        }

        return Response.ok().build();
    }

}
