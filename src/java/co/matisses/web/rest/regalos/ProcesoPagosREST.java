package co.matisses.web.rest.regalos;

import co.matisses.b1ws.client.creditnotes.CreditNotesServiceConnector;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDetailDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptLocationsDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptServiceConnector;
import co.matisses.b1ws.client.journalentries.JournalEntriesServiceConnector;
import co.matisses.b1ws.client.orders.OrdersServiceConnector;
import co.matisses.b1ws.client.payments.IncomingPaymentServiceConnector;
import co.matisses.b1ws.dto.ConstantTypes;
import co.matisses.b1ws.dto.CreditCardPaymentDTO;
import co.matisses.b1ws.dto.JournalEntryDTO;
import co.matisses.b1ws.dto.JournalEntryLineDTO;
import co.matisses.b1ws.dto.PaymentDTO;
import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DocumentosExcluidos;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.DocumentosExcluidosFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CarritoListaRegalos;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.DetalleCarritoListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.entity.TransaccionPlacetopay;
import co.matisses.persistence.web.facade.CarritoListaRegalosFacade;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.persistence.web.facade.TransaccionPlacetopayFacade;
import co.matisses.web.ObjectUtils;
import co.matisses.web.bcs.client.EmailServiceClient;
import co.matisses.web.bcs.client.SMSServiceClient;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.InformacionAlmacenDTO;
import co.matisses.web.dto.MensajeTextoDTO;
import co.matisses.web.dto.SesionListaRegalosDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.google.URLShortenerServiceClient;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.p2p.P2PServiceConnector;
import co.matisses.web.p2p.TransactionStatusRequestDTO;
import co.matisses.web.p2p.TransactionStatusResponseDTO;
import co.matisses.web.dto.BCSMailMessageDTO;
import co.matisses.web.google.URLShortenerResponseDTO;
import co.matisses.web.rest.regalos.dto.ProductoCompraExternaDTO;
import co.matisses.web.rest.regalos.dto.RegistroCompraExternaDTO;
import co.matisses.web.rest.regalos.dto.RegistroCompraExternaResponseDTO;
import co.matisses.web.rest.regalos.dto.SolicitudPagoResponseDTO;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("pagos")
public class ProcesoPagosREST implements Serializable {

    private static final Logger log = Logger.getLogger(ProcesoPagosREST.class.getSimpleName());
    private final List<String> ESTADOS_EXITOSOS = new ArrayList<>();
    private final List<String> ESTADOS_FALLIDOS = new ArrayList<>();
    private final List<String> ESTADOS_PENDIENTES = new ArrayList<>();
    @Inject
    private BaruApplicationMBean applicationBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private TransaccionPlacetopayFacade transaccionP2PFacade;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private CarritoListaRegalosFacade carritoFacade;
    @EJB
    private ProductoListaRegalosFacade productoListaFacade;
    @EJB
    private DocumentosExcluidosFacade documentosExcluidosFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;
    @EJB
    private ItemInventarioFacade itemFacade;

    public ProcesoPagosREST() {
        ESTADOS_EXITOSOS.add("OK");
        ESTADOS_EXITOSOS.add("APPROVED");
        ESTADOS_EXITOSOS.add("APPROVED_PARTIAL");
        ESTADOS_FALLIDOS.add("CANCELLED");
        ESTADOS_FALLIDOS.add("REJECTED");
        ESTADOS_FALLIDOS.add("FAILED");
        ESTADOS_PENDIENTES.add("PENDING");
        ESTADOS_PENDIENTES.add("PENDING_VALIDATION");
    }

    @POST
    @Path("procesar/")
    @Produces({MediaType.TEXT_PLAIN})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response recibirConfirmacionPOST(final PlaceToPayPOSTResponse response) {
        log.log(Level.INFO, "Se recibio notificacion de resolucion de pago de placetopay. {0}", response.toString());
        TransaccionPlacetopay transaccion = transaccionP2PFacade.consultarPorReferenciaPago(response.getReference());
        if (transaccion == null) {
            log.log(Level.SEVERE, "La transaccion enviada no existe");
            return Response.serverError().build();
        }
        StringBuilder data = new StringBuilder();
        data.append(transaccion.getRequestId());
        data.append(response.getStatus().getStatus());
        data.append(response.getStatus().getDate());
        data.append(applicationBean.obtenerValorPropiedad("placetopay.trankey"));

        if (!DigestUtils.sha1Hex(data.toString()).equals(response.getSignature())) {
            log.log(Level.SEVERE, "La firma enviada no corresponde");
            return Response.serverError().build();
        }

        procesarNotificacionPago(response.getReference());
        return Response.ok("OK").build();
    }

    public static void main(String[] args) {

        String trankey = "0xoLWzKu";
        String date = "2017-04-10T11:21:00-05:00";
        String req = "4102";
        String status = "APPROVED";

        StringBuilder data = new StringBuilder();
        data.append(req);
        data.append(status);
        data.append(date);
        data.append(trankey);
        System.out.println(DigestUtils.sha1Hex(data.toString()));
    }

    @GET
    @Path("procesarpendientes/")
    @Produces({MediaType.TEXT_PLAIN})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response procesarTransaccionesPendientes() {
        log.log(Level.INFO, "Procesando transacciones pendientes por confirmacion de pago");
        List<TransaccionPlacetopay> transacciones = transaccionP2PFacade.listarTransaccionesPendientes(false);
        log.log(Level.INFO, "Se encontraron {0} transacciones", transacciones.size());
        StringBuilder sb = new StringBuilder();
        sb.append("se encontraron ");
        sb.append(transacciones.size());
        sb.append(" transacciones");
        for (TransaccionPlacetopay transaccion : transacciones) {
            Response res = procesarNotificacionPago(transaccion.getRefPago());
            Object obj = null;
            try {
                obj = res.readEntity(Object.class);
            } catch (Exception e) {
                log.log(Level.SEVERE, "No fue posible leer la respuesta del metodo de procesar pago. {0}", e.getMessage());
            }
            if (obj != null) {
                sb.append(obj.toString());
            } else {
                sb.append("Se proceso la transaccion con refpago=");
                sb.append(transaccion.getRefPago());
            }
            sb.append("\n");
        }
        log.log(Level.INFO, "Fin del proceso de actualizacion de transacciones pendientes. {0}", sb.toString());
        return Response.ok(sb.toString()).build();
    }

    private Response procesar() {
        log.log(Level.INFO, "Procesando transacciones pendientes por confirmacion de pago");
        List<TransaccionPlacetopay> transacciones = transaccionP2PFacade.listarTransaccionesPendientes(false);
        log.log(Level.INFO, "Se encontraron {0} transacciones", transacciones.size());
        for (TransaccionPlacetopay transaccion : transacciones) {
            //Consulta el estado de la transaccion en placetopay
            TransactionStatusResponseDTO response = consultarTransaccionPlaceToPay(transaccion.getRequestId());
            try {
                actualizarEstadoTransaccion(transaccion, response);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al actualizar el registro de la transaccion. ", e);
                return Response.serverError().build();
            }
            FacturaSAP facturaSAP = facturaSAPFacade.findByDocNum(Integer.parseInt(transaccion.getDocNumFactura()));
            if (ESTADOS_PENDIENTES.contains(response.getStatus().getStatus())) {
                if (new Date().compareTo(transaccion.getExpira()) > 0) {
                    //Si la fecha de expiracion ya paso
                    ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(transaccion.getCodigoLista());
                    String propietarios = lista.getNombreCreador();
                    if (lista.getNombreCocreador() != null) {
                        propietarios = propietarios.concat(" y ").concat(lista.getNombreCocreador());
                    }
                    enviarEmailPagoVencido(transaccion.getNombres(), propietarios, transaccion.getCorreo());
                    SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(transaccion.getDocNumFactura());
                    try {
                        anularFactura(facturaSAP, sesionSAPDTO);
                    } catch (Exception e) {
                        //TODO: notificar dpto sistemas
                        log.log(Level.SEVERE, "Ocurrio un error al anular la factura. ", e);
                    }
                    applicationBean.finalizarSesionSAP(sesionSAPDTO);
                }
            } else if (ESTADOS_FALLIDOS.contains(response.getStatus().getStatus())) {
                SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(transaccion.getDocNumFactura());
                try {
                    anularFactura(facturaSAP, sesionSAPDTO);
                } catch (Exception e) {
                    //TODO: notificar dpto sistemas
                    log.log(Level.SEVERE, "Ocurrio un error al anular la factura. ", e);
                }
                applicationBean.finalizarSesionSAP(sesionSAPDTO);
            } else if (ESTADOS_EXITOSOS.contains(response.getStatus().getStatus())) {
                ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(transaccion.getCodigoLista());
                CarritoListaRegalos carrito = carritoFacade.consultarPorIdSesion(transaccion.getIdSesion());
                crearDocumentosRelacionados(facturaSAP, response);
                for (DetalleCarritoListaRegalos detalleCarrito : carrito.getProductos()) {
                    ItemInventario item = itemFacade.find(detalleCarrito.getReferencia());
                    enviarNotificaciones(lista, transaccion.getMensajeComprador(), transaccion.getIp(), detalleCarrito.getReferencia(), item.getFrgnName(),
                            detalleCarrito.getCantidad().toString());
                }
            } else {
                //El estado de la transaccion no se reconoce
                log.log(Level.SEVERE, "El estado {0} no es un estado valido reconocido. ", response.getStatus().getStatus());
                //TODO: notificar dpto sistemas
            }
        }
        return Response.ok().build();
    }

    private SesionListaRegalosDTO crearSesionListaRegalos(SocioDeNegocios entidad) {
        ClienteSAPDTO cliente = new ClienteSAPDTO();
        cliente.setRegimen("RC");
        cliente.setAutorretenedor("N");
        cliente.setNacionalidad("01");
        cliente.setTipoPersona("01");
        cliente.setCodAsesor("98");
        cliente.setSexo("3");
        cliente.setTipoDocumento("13");
        cliente.setTipoExtranjero("-");
        cliente.setNit(entidad.getCardCode());
        cliente.setEmail(entidad.getEmail());
        cliente.setCelular(entidad.getCelular());
        cliente.setNombres(entidad.getNombres());
        cliente.setApellido1(entidad.getApellido1());
        cliente.setApellido2(entidad.getApellido2());
        cliente.setDireccion(entidad.getDireccion());

        SesionListaRegalosDTO sesion = new SesionListaRegalosDTO();
        sesion.setCliente(cliente);
        sesion.setIdSession(RandomStringUtils.randomAlphanumeric(32));
        sesion.setSesionValida(true);
        sesion.setTipo(SesionListaRegalosDTO.TipoSesion.PROPIETARIO);
        applicationBean.agregarSesionListaRegalos(sesion.getIdSession(), sesion);
        return sesion;
    }

    @GET
    @Path("notificarResultado/{referenciaPago}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response procesarNotificacionPago(@PathParam("referenciaPago") String referenciaPago) {
        log.log(Level.INFO, "Procesando notificacion de pago para transaccion con refPago={0}", referenciaPago);
        if (referenciaPago == null) {
            log.log(Level.SEVERE, "No se recibio una referencia de pago para procesar.");
            return Response.serverError().build();
        }
        TransaccionPlacetopay entidad = transaccionP2PFacade.consultarPorReferenciaPago(referenciaPago);
        if (entidad == null) {
            log.log(Level.SEVERE, "No se encontro una transaccion con la referencia de pago indicada");
            return Response.serverError().build();
        }
        if (entidad.getRequestId() == null) {
            entidad.setPaymentStatus("FAILED");
            transaccionP2PFacade.edit(entidad);
            log.log(Level.SEVERE, "La transaccion no fue aceptada por placetopay y fue cancelada");
            return Response.ok("La transaccion " + entidad.getRefPago() + " no fue aceptada por placetopay y fue cancelada").build();
        }
        TransactionStatusResponseDTO response = consultarTransaccionPlaceToPay(entidad.getRequestId());
        if (response == null) {
            log.log(Level.SEVERE, "No fue posible comunicarse con placetopay para validar el estado de la transaccion");
            return Response.serverError().build();
        }
        //Actualiza la transaccion con la informacion recibida
        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(entidad.getCodigoLista());
        if (entidad.getPaymentStatus() == null) {
            //La transaccion no ha sido procesada aun. Se procede a validar si el pago fue exitoso
            //y a crear o anular los documentos necesarios
            log.log(Level.INFO, "El estado de la transaccion en placetopay es {0}", ObjectUtils.toString(response));
            if (ESTADOS_EXITOSOS.contains(response.getStatus().getStatus())) {
                //El pago fue exitoso. Crear documentos respectivos y notificar al propietario de la lista
                CarritoListaRegalos carrito = carritoFacade.consultarPorIdSesion(entidad.getIdSesion());
                if (entidad.getTipoCompra().equals("P")) {
                    FacturaSAP facturaSAP = facturaSAPFacade.findByDocNum(Integer.parseInt(entidad.getDocNumFactura()));
                    //Procesar compra de productos
                    if (carrito == null) {
                        log.log(Level.SEVERE, "No se encontro informacion del carrito de compras para la transaccion");
                        return Response.serverError().build();
                    }
                    List<DetalleCarritoListaRegalos> productosCorrectos = new ArrayList<>();
                    List<ProductoListaRegalos> productosLista = new ArrayList<>();
                    for (DetalleCarritoListaRegalos productoCarrito : carrito.getProductos()) {
                        ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaProducto(lista.getIdLista(), productoCarrito.getIdProductoLista());
                        if (productoLista.getCantidadElegida() - productoLista.getCantidadComprada() >= productoCarrito.getCantidad()) {
                            productosCorrectos.add(productoCarrito);
                            productosLista.add(productoLista);
                        }
                    }
                    //Valida si los productos no han sido comprados por otra persona mientras se confirmaba el pago
                    if (productosCorrectos.size() == carrito.getProductos().size()) {
                        //Actualiza la cantidad comprada de los productos en la lista
                        for (int i = 0; i < carrito.getProductos().size(); i++) {
                            DetalleCarritoListaRegalos productoCarrito = carrito.getProductos().get(i);
                            ProductoListaRegalos productoLista = productosLista.get(i);
                            try {
                                productoLista.setCantidadComprada(productoLista.getCantidadComprada() + productoCarrito.getCantidad());
                                productoListaFacade.edit(productoLista);
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al actualizar la cantidad comprada de un producto de lista de regalos. ", e);
                            }
                        }
                        crearDocumentosRelacionados(facturaSAP, response);
                        for (DetalleCarritoListaRegalos detalleCarrito : carrito.getProductos()) {
                            ItemInventario item = itemFacade.find(detalleCarrito.getReferencia());
                            enviarNotificaciones(lista, entidad.getMensajeComprador(), entidad.getIp(), detalleCarrito.getReferencia(), item.getFrgnName(),
                                    detalleCarrito.getCantidad().toString());
                        }
                    } else {
                        //Los productos fueron comprados y confirmados primero por otro usuario. 
                        //Se anula la factura y se entrega el saldo al propietario de la lista
                        GregorianCalendar hoy = new GregorianCalendar();
                        GregorianCalendar fechaFactura = new GregorianCalendar();
                        fechaFactura.setTime(facturaSAP.getDocDate());
                        if (hoy.get(Calendar.DAY_OF_YEAR) == fechaFactura.get(Calendar.DAY_OF_YEAR)) {
                            //1. Anular FV
                            SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(entidad.getDocNumFactura());
                            try {
                                anularFactura(facturaSAP, sesionSAPDTO);
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
                                //TODO: notificar dpto sistemas
                            }
                            //2. Crear recibo de caja
                            try {
                                Long docEntryRC = crearReciboCaja(facturaSAP.getDocEntry().toString(), response, sesionSAPDTO);
                                //3. Crear asiento saldo a favor
                                String nroDocPagador = entidad.getNroDocumento();
                                String nroDocPropietario = lista.getCedulaCreador();
                                if (!nroDocPagador.toUpperCase().endsWith("CL")) {
                                    nroDocPagador += "CL";
                                }
                                if (!nroDocPropietario.toUpperCase().endsWith("CL")) {
                                    nroDocPropietario += "CL";
                                }
                                crearAsientoAjusteSaldoFavor(facturaSAPFacade.consultarDocNumReciboCaja(docEntryRC.toString()).toString(),
                                        entidad.getTotalPago().longValue(), nroDocPagador, nroDocPropietario, sesionSAPDTO);
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
                                //TODO: notificar dpto sistemas
                            }
                            applicationBean.finalizarSesionSAP(sesionSAPDTO);
                            //TODO: 3. Notificar al propietario (notificacion saldo a favor por concurrencia en compra)
                        } else {
                            //TODO: notificar servicio al cliente y sistemas para hacer nota credito por devolucion
                            //y asiento de saldo a favor
                        }
                    }
                } else {
                    //Procesar compra de bono de regalo
                    SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(referenciaPago);
                    //1. Crear recibo de caja
                    try {
                        Long docEntryRC = crearReciboCaja(null, response, sesionSAPDTO);
                        //2. Crear asiento saldo a favor
                        String nroDocPagador = entidad.getNroDocumento();
                        String nroDocPropietario = lista.getCedulaCreador();
                        if (!nroDocPagador.toUpperCase().endsWith("CL")) {
                            nroDocPagador += "CL";
                        }
                        if (!nroDocPropietario.toUpperCase().endsWith("CL")) {
                            nroDocPropietario += "CL";
                        }
                        crearAsientoAjusteSaldoFavor(facturaSAPFacade.consultarDocNumReciboCaja(docEntryRC.toString()).toString(),
                                entidad.getTotalPago().longValue(), nroDocPagador, nroDocPropietario, sesionSAPDTO);
                        enviarNotificaciones(lista, entidad.getMensajeComprador(), entidad.getIp(), "Bono de regalo", "Bono de regalo",
                                entidad.getTotalPago().toString());
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
                        //TODO: notificar dpto sistemas
                    }
                    applicationBean.finalizarSesionSAP(sesionSAPDTO);
                }
                //Registrar compra
                registrarCompraLista(entidad, carrito, lista.getIdLista());
            } else if (ESTADOS_FALLIDOS.contains(response.getStatus().getStatus())) {
                if (entidad.getTipoCompra().equals("P") && entidad.getDocNumFactura() != null) {
                    FacturaSAP facturaSAP = facturaSAPFacade.findByDocNum(Integer.parseInt(entidad.getDocNumFactura()));
                    //El pago no fue exitoso. Anular FV
                    GregorianCalendar hoy = new GregorianCalendar();
                    GregorianCalendar fechaFactura = new GregorianCalendar();
                    fechaFactura.setTime(facturaSAP.getDocDate());
                    if (hoy.get(Calendar.DAY_OF_YEAR) == fechaFactura.get(Calendar.DAY_OF_YEAR)) {
                        SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(entidad.getDocNumFactura());
                        try {
                            anularFactura(facturaSAP, sesionSAPDTO);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
                            //TODO: notificar dpto sistemas
                        }
                        applicationBean.finalizarSesionSAP(sesionSAPDTO);
                    } else {
                        //TODO: notificar servicio al cliente y sistemas para hacer nota credito por devolucion
                        //y asiento de saldo a favor
                    }
                } else {
                    log.log(Level.INFO, "No se encontro una factura para anular para la transaccion {0}. ");
                }
            }
        } else if (entidad.getPaymentStatus().toUpperCase().equals("EXPIRED")) {
            //La transaccion ya fue procesada y cancelada por exceder el tiempo de espera.
            //Se debe solicitar reversion del pago y notificar el error al comprador si el 
            //estado del pago fue exitoso. De lo contrario, se ignora la solicitud
            if (ESTADOS_EXITOSOS.contains(response.getStatus().getStatus())) {
                //Solicitar reversion pago
                TransactionStatusResponseDTO resp = reversarTransaccionPlaceToPay(entidad.getPaymentInternalReference());
                if (resp == null) {
                    //TODO: notificar dpto sistemas
                }
                String propietarios = lista.getNombreCreador();
                if (lista.getNombreCocreador() != null) {
                    propietarios = propietarios.concat(" y ").concat(lista.getNombreCocreador());
                }
                enviarEmailPagoVencido(entidad.getNombres(), propietarios, entidad.getCorreo());
            }
        }
        try {
            actualizarEstadoTransaccion(entidad, response);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar el registro de la transaccion. ", e);
            return Response.serverError().build();
        }
        String url = applicationBean.obtenerValorPropiedad("listaregalos.url.web.pago.confirmacion") + referenciaPago;
        URI paginaConfirmacion = URI.create(url);
        return Response.temporaryRedirect(paginaConfirmacion).build();
    }

    @GET
    @Path("consultapendiente/{idSesion}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarTransaccionPendiente(@PathParam("idSesion") String idSesion) {
        log.log(Level.INFO, "Consultando las transacciones placetopay pendientes para la sesion de lista de regalos {0}", idSesion);
        try {
            TransaccionPlacetopay transaccion = transaccionP2PFacade.consultarPorIdSesion(idSesion);
            if (transaccion == null) {
                log.log(Level.INFO, "No se encontro ninguna transaccion pendiente");
                return Response.ok(new SolicitudPagoResponseDTO(0, "")).build();
            } else if (transaccion.getPaymentStatus() != null) {
                log.log(Level.WARNING, "Se encontro una transaccion finalizada. Redirigiendo a pagina de confirmacion");
                String urlConfirmacion = applicationBean.obtenerValorPropiedad("listaregalos.url.web.pago.confirmacion") + transaccion.getRefPago();
                return Response.ok(new SolicitudPagoResponseDTO(-1, urlConfirmacion)).build();
            } else {
                log.log(Level.WARNING, "Se encontro una transaccion pendiente");
                return Response.ok(new SolicitudPagoResponseDTO(1, transaccion.getProcessUrl())).build();
            }
        } catch (Exception e) {
            //Existe mas de una transaccion pendiente
            log.log(Level.SEVERE, "Se encontraron varias transacciones pendientes. Es necesario que el usuario inicie una sesion nueva antes de continuar");
            String urlHome = applicationBean.obtenerValorPropiedad("listaregalos.url.web.inicio");
            return Response.ok(new SolicitudPagoResponseDTO(-1, urlHome)).build();
        }
    }

    @POST
    @Path("registrarcompraexterna/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response registrarCompraOtrosMedios(RegistroCompraExternaDTO registroCompra, @Context HttpServletRequest req) {
        log.log(Level.INFO, "Registrando compra externa para lista de regalos con datos {0}", registroCompra);
        FacturaSAP factura = facturaSAPFacade.findByDocNum(Integer.parseInt(registroCompra.getDocNumFV()));
        if (factura == null) {
            log.log(Level.SEVERE, "No se encontro ninguna factura con el numero {0}", registroCompra.getDocNumFV());
            return Response.ok(new RegistroCompraExternaResponseDTO(1, "No se encontró ninguna factura con el número indicado")).build();
        }
        if (factura.getDocStatus() != 'C') {
            log.log(Level.SEVERE, "La factura {0} aun se encuentra abierta. Se debe registrar el pago de la misma en SAP antes de poderla asociar con la lista de regalos", registroCompra.getDocNumFV());
            return Response.ok(new RegistroCompraExternaResponseDTO(1, "La factura " + registroCompra.getDocNumFV()
                    + " aun se encuentra abierta. Se debe registrar el pago de la misma en SAP antes de poderla asociar con la lista de regalos")).build();
        }
        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(registroCompra.getCodigoLista());
        if (lista == null) {
            log.log(Level.SEVERE, "No se encontro ninguna lista con el codigo {0}", registroCompra.getCodigoLista());
            return Response.ok(new RegistroCompraExternaResponseDTO(1, "No se encontro ninguna lista con el codigo indicado")).build();
        }
        List<ProductoListaRegalos> productos = productoListaFacade.consultarPorLista(lista.getIdLista());
        if (productos == null || productos.isEmpty()) {
            log.log(Level.SEVERE, "No se encontraron productos para la lista con codigo {0}", registroCompra.getCodigoLista());
            return Response.ok(new RegistroCompraExternaResponseDTO(1, "No se encontraron productos para la lista con el codigo indicado")).build();
        }
        //Si se reportaron solo algunos productos de la factura, se revisa cuales y con que cantidad para filtrarlos
        List<DetalleFacturaSAP> detalleFactura = agruparDetalleFactura(factura.getDetalle());
        log.log(Level.INFO, "La factura tiene {0} items", detalleFactura.size());
        if (!registroCompra.getProductos().isEmpty()) {
            List<DetalleFacturaSAP> productosReportados = new ArrayList<>();
            for (ProductoCompraExternaDTO productoDTO : registroCompra.getProductos()) {
                for (DetalleFacturaSAP productoFactura : detalleFactura) {
                    if (productoFactura.getItemCode().equals(productoDTO.getReferencia())) {
                        if (productoFactura.getQuantity().intValue() >= productoDTO.getCantidad()) {
                            productoFactura.setQuantity(new BigDecimal(productoDTO.getCantidad()));
                        } else {
                            log.log(Level.SEVERE, "No se estan intentando registrar mas unidades de las que  {0}", registroCompra.getCodigoLista());
                            String msgError = "Se indico que se regalaron " + productoDTO.getCantidad() + " unidades de la ref " + productoDTO.getReferencia()
                                    + " pero la factura solo tiene " + productoFactura.getQuantity().intValue();
                            return Response.ok(new RegistroCompraExternaResponseDTO(1, msgError)).build();
                        }
                        productosReportados.add(productoFactura);
                    }
                }
            }
            detalleFactura = productosReportados;
        }
        log.log(Level.INFO, "Procesando {0} items", detalleFactura.size());
        int productosDescontados = 0;
        //Descuenta los productos de la lista y registra la compra 
        for (DetalleFacturaSAP detalle : detalleFactura) {
            for (ProductoListaRegalos producto : productos) {
                if (!producto.getReferencia().equals(detalle.getItemCode())) {
                    continue;
                }
                if (producto.getCantidadElegida() - producto.getCantidadComprada() >= detalle.getQuantity().intValue()) {
                    if (!compraListaRegalosFacade.consultarFacturaProductoReportado(registroCompra.getDocNumFV(), producto)) {
                        productosDescontados++;
                        producto.setCantidadComprada(producto.getCantidadComprada() + detalle.getQuantity().intValue());
                        productoListaFacade.edit(producto);

                        CompraListaRegalos compra = new CompraListaRegalos();
                        compra.setCantidad(detalle.getQuantity().intValue());
                        compra.setFactura(registroCompra.getDocNumFV());
                        compra.setFecha(new Date());
                        compra.setLista(lista);
                        compra.setMensaje(registroCompra.getMensaje());
                        compra.setProducto(producto);
                        compra.setQuienCompra(null);
                        compra.setTotal(detalle.getPriceAfVAT().longValue());

                        compraListaRegalosFacade.create(compra);
                        //Notificar al propietario y copropietario, teniendo en cuenta la configuracion de notificaciones
                        enviarNotificaciones(lista, registroCompra.getMensaje(), req.getRemoteAddr(), producto.getReferencia(), producto.getDescripcionProducto(), compra.getCantidad().toString());
                        break;
                    } else {
                        //Ya se registro una compra para ese producto y esa factura. Ignorando el registro
                        log.log(Level.WARNING, "El producto {0}({1}) ya fue registrado como comprado con la factura {2}. ",
                                new Object[]{producto.getIdProductoLista(), producto.getReferencia(), registroCompra.getDocNumFV()});
                    }
                } else {
                    //TODO: la cantidad comprada es mayor que la cantidad pendiente por comprar en la lista
                    //      si la factura es de hoy, se debe anular la factura y pasar el saldo a favor a nombre del 
                    //      propietario de la lista. si la factura es de una fecha anterior, se debe notificar a 
                    //      servicio al cliente para que realice todo el proceso
                    break;
                }
            }
        }
        if (productosDescontados == 0) {
            log.log(Level.SEVERE, "No se encontraron productos en comun entre la lista y la factura");
            return Response.ok(new RegistroCompraExternaResponseDTO(1, "No se encontraron productos en comun entre la lista y la factura")).build();
        }
        return Response.ok(new RegistroCompraExternaResponseDTO(0, null)).build();
    }

    private List<DetalleFacturaSAP> agruparDetalleFactura(List<DetalleFacturaSAP> detalleFactura) {
        log.log(Level.INFO, "Agrupando detalle de factura. Tamano inicial: {0}", detalleFactura.size());
        HashMap<String, Integer> referencias = new HashMap<>();
        List<DetalleFacturaSAP> detalleAgrupado = new ArrayList<>();
        for (DetalleFacturaSAP detalle : detalleFactura) {
            if (!referencias.containsKey(detalle.getItemCode())) {
                referencias.put(detalle.getItemCode(), detalleAgrupado.size());
                detalleAgrupado.add(detalle);
            } else {
                DetalleFacturaSAP detalleIncrementar = detalleAgrupado.get(referencias.get(detalle.getItemCode()));
                detalleIncrementar.setQuantity(detalleIncrementar.getQuantity().add(detalle.getQuantity()));
            }
        }
        log.log(Level.INFO, "Termino de agrupar detalle de factura. Tamano final: {0}", detalleAgrupado.size());
        return detalleAgrupado;
    }

    private void crearDocumentosRelacionados(FacturaSAP facturaSAP, TransactionStatusResponseDTO response) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationBean.obtenerSesionSAP(Integer.toString(facturaSAP.getDocNum()));
        try {
            crearOrdenDeVenta(facturaSAP, sesionSAPDTO);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear la orden de venta. ", e);
            //TODO: notificar dpto sistemas
        }
        try {
            crearEntradaMcia(facturaSAP, sesionSAPDTO);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear la entrada de mcia de clientes. ", e);
            //TODO: notificar dpto sistemas
        }
        try {
            crearAsientoAjusteFactura(facturaSAP.getDocEntry().longValue(), sesionSAPDTO);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el asiento de ajuste para mcia en consignacion. ", e);
            //TODO: notificar dpto sistemas
        }
        try {
            crearReciboCaja(facturaSAP.getDocEntry().toString(), response, sesionSAPDTO);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja. ", e);
            //TODO: notificar dpto sistemas
        }
        applicationBean.finalizarSesionSAP(sesionSAPDTO);
    }

    private void actualizarEstadoTransaccion(TransaccionPlacetopay entidad, TransactionStatusResponseDTO response) throws Exception {
        if (!ESTADOS_EXITOSOS.contains(response.getStatus().getStatus())
                && !ESTADOS_FALLIDOS.contains(response.getStatus().getStatus())) {
            if (entidad.getExpira().before(new Date())) {
                //Se marca la transaccion como expirada
                entidad.setPaymentStatus("EXPIRED");
                entidad.setPaymentMessage("La transacción excedio el tiempo límite para ser confirmada");
                transaccionP2PFacade.edit(entidad);
                log.log(Level.INFO, "La transaccion refPago={0} fue marcada como EXPIRADA", entidad.getRefPago());
            } else {
                log.log(Level.INFO, "No se actualiza el estado de la transaccion ya que el estado recibido fue {0}", response.getStatus().getStatus());
            }
            return;
        }
        entidad.setPaymentStatus(response.getStatus().getStatus());
        entidad.setPaymentMessage(response.getStatus().getMessage());
        if (response.getPayment() != null && !response.getPayment().isEmpty()) {
            TransactionStatusResponseDTO.Payment payment = response.getPayment().get(0);
            entidad.setPaymentAuthorization(payment.getAuthorization());
            entidad.setPaymentFranchise(payment.getFranchise());
            entidad.setPaymentIssuerName(payment.getIssuerName());
            for (TransactionStatusResponseDTO.Payment.ProcessorField field : payment.getProcessorFields()) {
                if (field.getKeyword().toUpperCase().equals("LASTDIGITS")) {
                    entidad.setPaymentLastDigits(field.getValue());
                    break;
                }
            }
            entidad.setPaymentDate(payment.getStatus().getDate());
            entidad.setPaymentMethod(payment.getPaymentMethod());
            entidad.setPaymentMethodName(payment.getPaymentMethodName());
            entidad.setPaymentReceipt(payment.getReceipt());
            entidad.setPaymentReference(payment.getReference());
            entidad.setPaymentInternalReference(payment.getInternalReference());
        }
        transaccionP2PFacade.edit(entidad);
    }

    private TransactionStatusResponseDTO consultarTransaccionPlaceToPay(String requestId) {
        try {
            return P2PServiceConnector.postPlaceToPayRequest(applicationBean.obtenerValorPropiedad("placetopay.url"), "session/" + requestId,
                    new TransactionStatusRequestDTO(applicationBean.obtenerValorPropiedad("placetopay.login"),
                            applicationBean.obtenerValorPropiedad("placetopay.trankey")), TransactionStatusResponseDTO.class);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el estado de la transaccion en placetopay. ", e);
            return null;
        }
    }

    private TransactionStatusResponseDTO reversarTransaccionPlaceToPay(String internalReference) {
        try {
            return P2PServiceConnector.postPlaceToPayRequest(applicationBean.obtenerValorPropiedad("placetopay.url"), "reverse/",
                    new TransactionStatusRequestDTO(applicationBean.obtenerValorPropiedad("placetopay.login"),
                            applicationBean.obtenerValorPropiedad("placetopay.trankey"), internalReference), TransactionStatusResponseDTO.class);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el estado de la transaccion en placetopay. ", e);
            return null;
        }
    }

    private void crearEntradaMcia(FacturaSAP facturaSAP, SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
        GoodsReceiptDTO entrada = new GoodsReceiptDTO();
        entrada.setComments("Entrada de mcia para FV " + facturaSAP.getDocNum() + " y cliente " + facturaSAP.getCardCode());
        entrada.setInvoiceNumber(Integer.toString(facturaSAP.getDocNum()));
        entrada.setJournalMemo("Goods Receipt");
        entrada.setSeries(69L); //TODO: parametrizar numero de serie de entradas de mcia de clientes
        entrada.setOrigen("L");
        for (DetalleFacturaSAP detalle : facturaSAP.getDetalle()) {
            InformacionAlmacenDTO infoAlmacenVenta = applicationBean.getInfoAlmacen(detalle.getWhsCode());
            InformacionAlmacenDTO infoAlmacenCliente = applicationBean.getInfoAlmacen(infoAlmacenVenta.getAlmacenClientes());

            GoodsReceiptLocationsDTO ubEntrada = new GoodsReceiptLocationsDTO();
            ubEntrada.setBinAbs(infoAlmacenCliente.getIdUbicacionTM());
            ubEntrada.setQuantity(detalle.getQuantity().intValue());

            GoodsReceiptDetailDTO detEntrada = new GoodsReceiptDetailDTO();
            detEntrada.addLocation(ubEntrada);
            detEntrada.setAccountCode("91051001"); //TODO: parametrizar cuenta de mcia de clientes
            detEntrada.setItemCode(detalle.getItemCode());
            detEntrada.setLineNum((long) detalle.getDetalleFacturaSAPPK().getLineNum());
            detEntrada.setQuantity(detalle.getQuantity().intValue());
            //El whsCode se consulta ya que para productos en consignacion se manejan almacenes especiales
            detEntrada.setWhsCode(infoAlmacenVenta.getAlmacenClientes());
            detEntrada.setPrice(detalle.getPrice().doubleValue());
            entrada.addDetail(detEntrada);
        }
        GoodsReceiptServiceConnector grsc = sapB1WSBean.getGoodsReceiptServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long docEntryEntrada = grsc.createDocument(entrada);
        log.log(Level.INFO, "Se creo la entrada de mcia de cliente con DocEntry={0}", docEntryEntrada);
    }

    private Long crearReciboCaja(String docEntryFactura, TransactionStatusResponseDTO datosPago, SesionSAPB1WSDTO sesionSap) throws Exception {
        String nroDocumento = datosPago.getRequest().getPayer().getDocument();
        if (!nroDocumento.toUpperCase().endsWith("CL")) {
            nroDocumento += "CL";
        }
        PaymentDTO pagoDto = new PaymentDTO();
        pagoDto.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
        pagoDto.setCardCode(nroDocumento);
        pagoDto.setCreditType("I");
        pagoDto.setInvoiceDocEntry(docEntryFactura);
        pagoDto.setPaidTotal(Long.toString(datosPago.getRequest().getPayment().getAmount().getTotal()));
        pagoDto.setSeriesCode("151"); //TODO: parametrizar serie de numeracion
        pagoDto.setDocType(ConstantTypes.DocType.INVOICE);
        List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();

        CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();
        creditPayment.setCreditCardCode(applicationBean.getTipoTarjetaP2P(datosPago.getPayment().get(0).getFranchise()).getIdTarjetaSAP().toString());
        creditPayment.setCreditCardNumber(StringUtils.right(datosPago.getPayment().get(0).getProcessorFields().get(0).getValue(), 4));
        creditPayment.setNumberOfPayments("1");
        creditPayment.setPaidSum(pagoDto.getPaidTotal());
        creditPayment.setValidUntil(null);//TODO: configurar fecha de validez
        creditPayment.setVoucherNumber(datosPago.getPayment().get(0).getAuthorization());
        creditCardPayments.add(creditPayment);
        pagoDto.setCreditCardPayments(creditCardPayments);

        IncomingPaymentServiceConnector ipsc = sapB1WSBean.getIncomingPaymentServiceConnectorInstance(sesionSap.getIdSesionSAP());
        Long docEntryRecibo = ipsc.addPayment(pagoDto);
        log.log(Level.INFO, "Se creo satisfactoriamente el recibo de caja con DocEntry={0}", docEntryRecibo);
        return docEntryRecibo;
    }

    private void crearOrdenDeVenta(FacturaSAP facturaSAP, SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);
        co.matisses.b1ws.dto.OrderDTO orderDto = new co.matisses.b1ws.dto.OrderDTO();
        orderDto.setCardCode(facturaSAP.getCardCode());
        orderDto.setComments(facturaSAP.getComments());
        orderDto.setDocDate(new Date());
        orderDto.setDocDueDate(cal.getTime());
        orderDto.setInvoiceNumber(Integer.toString(facturaSAP.getDocNum()));
        orderDto.setOrigen("L");
        orderDto.setSalesPersonCode(facturaSAP.getSlpCode().longValue());
        orderDto.setSeries(13L); //TODO: parametrizar codigo de numero de serie de ordenes de venta
        for (DetalleFacturaSAP detalle : facturaSAP.getDetalle()) {
            InformacionAlmacenDTO infoAlmacenVenta = applicationBean.getInfoAlmacen(detalle.getWhsCode());
            co.matisses.b1ws.dto.OrderDetailDTO detailDto = new co.matisses.b1ws.dto.OrderDetailDTO();
            detailDto.setEstado("P");
            detailDto.setItemCode(detalle.getItemCode());
            detailDto.setLineNum((long) detalle.getDetalleFacturaSAPPK().getLineNum());
            detailDto.setQuantity(detalle.getQuantity().doubleValue());
            detailDto.setWarehouseCode(infoAlmacenVenta.getAlmacenClientes());
            orderDto.addLine(detailDto);
        }

        OrdersServiceConnector osc = sapB1WSBean.getOrderServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long docEntryOrden = osc.createOrder(orderDto);
        log.log(Level.INFO, "Se creo la orden de venta con DocEntry={0}", docEntryOrden);
    }

    private void crearAsientoAjusteSaldoFavor(String docNumRC, Long valorBono, String cedulaPagador, String cedulaPropietario,
            SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
        JournalEntryDTO journalEntryHeader = new JournalEntryDTO();
        journalEntryHeader.setMemo("BONO LISTA DE REGALOS");
        journalEntryHeader.setRef1(docNumRC);
        journalEntryHeader.setTransactionCode("BONO");//TODO: parametrizar codigo de asiento
        journalEntryHeader.setDueDate(new Date());
        journalEntryHeader.setRefDate(new Date());
        journalEntryHeader.setTaxDate(new Date());

        JournalEntryLineDTO line1 = new JournalEntryLineDTO();
        line1.setLineId(0L);
        line1.setCredit(0D);
        line1.setDebit(valorBono.doubleValue());
        line1.setShortName(cedulaPagador);
        line1.setContraAccount(cedulaPropietario);
        line1.setInfoCo01(cedulaPagador);

        JournalEntryLineDTO line2 = new JournalEntryLineDTO();
        line2.setLineId(1L);
        line2.setCredit(valorBono.doubleValue());
        line2.setDebit(0D);
        line2.setShortName(cedulaPropietario);
        line2.setContraAccount(cedulaPagador);
        line2.setInfoCo01(cedulaPropietario);

        journalEntryHeader.addLine(line1);
        journalEntryHeader.addLine(line2);
        JournalEntriesServiceConnector jesc = sapB1WSBean.getJournalEntriesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long idEntradaDiario = jesc.createJournalEntry(journalEntryHeader);
        log.log(Level.INFO, "Se creo el asiento contable con id={0}", idEntradaDiario);
    }

    private void crearAsientoAjusteFactura(Long docEntryFactura, SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
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
            Long idEntradaDiario = jesc.createJournalEntry(journalEntryHeader);
            log.log(Level.INFO, "Se creo el asiento de ajuste por mercancia en consignacion con id={0}", idEntradaDiario);
        }
    }

    private void anularFactura(FacturaSAP factura, SesionSAPB1WSDTO sesionSAPDTO) throws Exception {
        //Consultar la informacion de la factura y llenar el documento completo
        List<Object[]> rows = facturaSAPFacade.consultarDatosFactura(Integer.toString(factura.getDocNum()));
        if (rows == null || rows.isEmpty()) {
            throw new Exception("No se encontro una factura valida para anular.");
        }
        SalesDocumentDTO enc = procesarDatosConsultaFactura(Integer.toString(factura.getDocNum()), rows);
        if (enc == null) {
            throw new Exception("No se pudo consultar la información de la factura. ");
        }
        //crea la nota credito por anulacion
        CreditNotesServiceConnector sc = sapB1WSBean.getCreditNotesServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
        Long docEntry = sc.createCreditNote(enc);
        if (docEntry == null) {
            log.log(Level.SEVERE, "No se pudo registrar la nota credito en SAP.");
            throw new Exception("No se pudo registrar la nota credito en SAP. ");
        } else {
            log.log(Level.INFO, "Se creo satisfactoriamente la nota credito con DocEntry={0}", docEntry);
        }
        //registra la factura y la nota credito en la tabla de documentos excluidos para que no se visualicen en el informe de ventas
        try {
            DocumentosExcluidos fac = new DocumentosExcluidos();
            DocumentosExcluidos dev = new DocumentosExcluidos();
            long id = System.currentTimeMillis();
            fac.setCode(Long.toString(id));
            fac.setName(Long.toString(id));
            fac.setDocNum(Integer.toString(factura.getDocNum()));
            fac.setFechaExclusion(new Date());
            fac.setUsuarioExcluye("listaregalos");
            fac.setTipoDocumento("Factura");

            dev.setCode(Long.toString(id + 1));
            dev.setName(Long.toString(id + 1));
            dev.setDocNum(Integer.toString(factura.getDocNum()));
            dev.setFechaExclusion(new Date());
            dev.setUsuarioExcluye("listaregalos");
            dev.setTipoDocumento("Devolucion");

            documentosExcluidosFacade.create(fac);
            documentosExcluidosFacade.create(dev);
        } catch (Exception e) {
            log.log(Level.WARNING, "No fue posible excluir la factura y la nota credito del informe de ventas. ", e);
            //TODO: notificar dpto sistemas
        }
    }

    private SalesDocumentDTO procesarDatosConsultaFactura(String nroFactura, List<Object[]> rows) {
        SalesDocumentDTO dto = new SalesDocumentDTO();
        if (rows != null && !rows.isEmpty()) {
            if (!DateUtils.isSameDay((Date) rows.get(0)[0], new Date())) {
                log.log(Level.SEVERE, "No se puede realizar la anulacion de una factura de fechas pasadas. ");
                return null;
            }
            dto.setDocEntry(((Integer) rows.get(0)[1]).longValue());
            dto.setRefDocnum(nroFactura);
            dto.setCardCode((String) rows.get(0)[3]);
            dto.setComments("Anulacion generada por 360 para la factura " + nroFactura);
            dto.setPaymentGroupCode(((Short) rows.get(0)[5]).toString());
            dto.setSource("L");
            dto.setSeriesCode(((Short) rows.get(0)[31]).toString());
            dto.setWuid((String) rows.get(0)[16]);
            dto.setPosShiftId(rows.get(0)[17] == null ? null : ((Integer) rows.get(0)[17]).longValue());
            dto.setCreditNoteType("A");

            //Agrega los vendedores a la devolucion
            if (rows.get(0)[6] != null) {
                SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
                salesEmp.setName((String) rows.get(0)[6]);
                dto.addSalesEmployee(salesEmp);
                if (rows.get(0)[7] != null) {
                    SalesEmployeeDTO salesEmp2 = new SalesEmployeeDTO();
                    salesEmp2.setName((String) rows.get(0)[7]);
                    dto.addSalesEmployee(salesEmp2);
                    if (rows.get(0)[8] != null) {
                        SalesEmployeeDTO salesEmp3 = new SalesEmployeeDTO();
                        salesEmp3.setName((String) rows.get(0)[8]);
                        dto.addSalesEmployee(salesEmp3);
                        if (rows.get(0)[9] != null) {
                            SalesEmployeeDTO salesEmp4 = new SalesEmployeeDTO();
                            salesEmp4.setName((String) rows.get(0)[9]);
                            dto.addSalesEmployee(salesEmp4);
                            if (rows.get(0)[10] != null) {
                                SalesEmployeeDTO salesEmp5 = new SalesEmployeeDTO();
                                salesEmp5.setName((String) rows.get(0)[10]);
                                dto.addSalesEmployee(salesEmp5);
                            }
                        }
                    }
                }
            }

            List<SalesDocumentLineDTO> detDtos = new ArrayList<>();
            for (Object[] cols : rows) {
                dto.setShippingStatus((String) cols[27]);
                dto.setSalesCostingCode((String) cols[23]);
                dto.setLogisticsCostingCode((String) cols[24]);
                dto.setRouteCostingCode((String) cols[25]);

                SalesDocumentLineBinAllocationDTO binAllocDto = new SalesDocumentLineBinAllocationDTO();
                binAllocDto.setBinAbsEntry((Integer) cols[28]);
                binAllocDto.setQuantity((Integer) cols[30]);
                binAllocDto.setWhsCode((String) cols[22]);

                //SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO((String) cols[20], binAllocDto.getWhsCode(), ((BigDecimal) cols[21]).intValue(), (Integer) cols[19], (Integer) cols[32]);
                SalesDocumentLineDTO lineDto = new SalesDocumentLineDTO((String) cols[20], binAllocDto.getWhsCode(), ((Integer) cols[32]).doubleValue(), ((BigDecimal) cols[33]).doubleValue());
                int pos = detDtos.indexOf(lineDto);
                if (pos >= 0) {
                    lineDto = detDtos.get(pos);
                    lineDto.addBinAllocation(binAllocDto);
                    lineDto.setQuantity(lineDto.getQuantity() + ((BigDecimal) cols[21]).intValue());
                    detDtos.set(pos, lineDto);
                } else {
                    lineDto.setQuantity(((BigDecimal) cols[21]).intValue());
                    lineDto.addBinAllocation(binAllocDto);
                    lineDto.setLineNum((Integer) cols[19]);
                    detDtos.add(lineDto);
                }
            }
            dto.setDocumentLines(detDtos);
        }
        return dto;
    }

    private void enviarSMS(String nombre, String celular, String shortURL, String ip) throws Exception {
        String mensaje = "Hola " + StringUtils.capitalize(nombre)
                + "!. Se registro una compra para tu lista de regalos. Para ver tu lista, haz clic aqui " + shortURL;
        log.log(Level.INFO, "El mensaje a enviar es de {0} caracteres: {1}", new Object[]{mensaje.length(), mensaje});
        SMSServiceClient client = new SMSServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        MensajeTextoDTO sms = new MensajeTextoDTO();
        sms.setCodigoPais("57");
        sms.setDestino(celular);
        sms.setMensaje(mensaje);
        sms.setPruebas(false);
        sms.setIp(ip);
        sms.setUsuario("listaregalos");
        client.enviarSMS(sms);
    }

    private void enviarEmailPagoVencido(String nombre, String propietarios, String emailDestinatario) {
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom("Lista Regalos Matisses <listaregalos@matisses.co>");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("nombreLista", propietarios);
        mail.setParams(params);
        mail.setSubject("Tu proceso de compra ha caducado");
        mail.setTemplateName("lista_regalos_pago_vencido");
        mail.addToAddress(emailDestinatario);
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion de correo. ", e);
        }
    }

    private void enviarEmailInternoProductoComprado(String nombre, String nombreProducto, String referencia, String cantidad, String mensaje) {
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom("Lista Regalos Matisses <listaregalos@matisses.co>");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("referencia", referencia);
        params.put("refCorta", referencia.substring(0, 3) + "." + referencia.substring(16));
        params.put("nombreProducto", nombreProducto);
        params.put("cantidad", cantidad);
        params.put("mensaje", mensaje);
        mail.setParams(params);
        mail.setSubject("Nueva compra lista de regalos");
        mail.setTemplateName("lista_regalos_producto_comprado_interno");
        mail.addToAddress("Sistemas Matisses <sistemas@matisses.co>");
        mail.addToAddress("Mercadeo Matisses <mercadeo@matisses.co>");
        mail.addToAddress("Servicio al Cliente Matisses <servicioalcliente@matisses.co>");
        mail.addToAddress("Finanzas Matisses <contable@matisses.co>");
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion de correo. ", e);
        }
    }

    private void enviarEmailProductoComprado(String nombre, String nombreProducto, String referencia, String cantidad, String mensaje, String emailDestinatario) {
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom("Lista Regalos Matisses <listaregalos@matisses.co>");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("referencia", referencia);
        params.put("refCorta", referencia.substring(0, 3) + "." + referencia.substring(16));
        params.put("nombreProducto", nombreProducto);
        params.put("cantidad", cantidad);
        params.put("mensaje", mensaje);
        mail.setParams(params);
        mail.setSubject("Has recibido un regalo");
        mail.setTemplateName("lista_regalos_producto_comprado");
        mail.addToAddress(emailDestinatario);
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion de correo. ", e);
        }
    }

    @GET
    @Path("/genUrlCorta/{cod}/{idSesion}")
    public String generarUrlCorta(@PathParam("cod") String codigoLista, @PathParam("idSesion") String idSesion) {
        StringBuilder urlLarga = new StringBuilder();
        urlLarga.append(applicationBean.obtenerValorPropiedad("listaregalos.url.web.editar"));
        urlLarga.append(codigoLista);
        urlLarga.append("&sesion=");
        urlLarga.append(idSesion);

        URLShortenerServiceClient client = new URLShortenerServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        try {
            URLShortenerResponseDTO response = client.generar(URLEncoder.encode(urlLarga.toString(), "UTF-8"));
            if (response.getError() == null) {
                return response.getId();
            } else {
                log.log(Level.SEVERE, "Ocurrio un error al obtener la url corta. {0}", response);
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al obtener la url corta. ", e);
            return null;
        }
    }

    private void enviarNotificaciones(ListaRegalos lista, String mensajeComprador, String ip, String referencia, String nombreProducto, String cantidad) {
        String urlCortaCreador;
        String urlCortaCocreador;
        SocioDeNegocios creador;
        SocioDeNegocios cocreador;
        SesionListaRegalosDTO sesionCreador;
        SesionListaRegalosDTO sesionCocreador;
        boolean smsCreador = false;
        boolean emailCreador = false;
        boolean smsCocreador = false;
        boolean emailCocreador = false;

        switch (lista.getNotificacionInmediataCreador()) {
            case "T":
                emailCreador = true;
                smsCreador = true;
                break;
            case "S":
                smsCreador = true;
                break;
            case "E":
                emailCreador = true;
                break;
            default:
                break;
        }
        switch (lista.getNotificacionInmediataCocreador()) {
            case "T":
                emailCocreador = true;
                smsCocreador = true;
                break;
            case "S":
                smsCocreador = true;
                break;
            case "E":
                emailCocreador = true;
                break;
            default:
                break;
        }

        //Notifica al creador
        if (emailCreador || smsCreador) {
            creador = socioDeNegociosFacade.findByCardCode(lista.getCedulaCreador());
            sesionCreador = crearSesionListaRegalos(creador);
            urlCortaCreador = generarUrlCorta(lista.getCodigo(), sesionCreador.getIdSession());
            if (emailCreador) {
                try {
                    enviarEmailProductoComprado(lista.getNombreCreador(), nombreProducto, referencia, cantidad, mensajeComprador, creador.getEmail());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS de notificacion de compra. ", e);
                }
            }
            if (smsCreador && urlCortaCreador != null) {
                try {
                    enviarSMS(lista.getNombreCreador(), creador.getCelular(), urlCortaCreador, ip);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS de notificacion de compra. ", e);
                }
            }
        }

        //Notifica al cocreador
        if (emailCocreador || smsCocreador) {
            cocreador = socioDeNegociosFacade.findByCardCode(lista.getCedulaCocreador());
            if (cocreador != null) {
                sesionCocreador = crearSesionListaRegalos(cocreador);
                urlCortaCocreador = generarUrlCorta(lista.getCodigo(), sesionCocreador.getIdSession());
                if (emailCocreador) {
                    try {
                        enviarEmailProductoComprado(lista.getNombreCocreador(), nombreProducto, referencia, cantidad, mensajeComprador, cocreador.getEmail());
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS de notificacion de compra. ", e);
                    }
                }
                if (smsCocreador && urlCortaCocreador != null) {
                    try {
                        enviarSMS(lista.getNombreCocreador(), cocreador.getCelular(), urlCortaCocreador, ip);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS de notificacion de compra. ", e);
                    }
                }
            }
        }

        try {
            enviarEmailInternoProductoComprado(lista.getNombreCreador(), nombreProducto, referencia, cantidad, mensajeComprador);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el SMS de notificacion de compra. ", e);
        }
    }

    private void registrarCompraLista(TransaccionPlacetopay transaccion, CarritoListaRegalos carrito, Long idLista) {
        if (transaccion.getTipoCompra().equals("P")) {
            for (DetalleCarritoListaRegalos detalle : carrito.getProductos()) {
                CompraListaRegalos compra = new CompraListaRegalos();
                compra.setCantidad(detalle.getCantidad());
                compra.setFactura(transaccion.getDocNumFactura());
                compra.setFecha(new Date());
                compra.setLista(new ListaRegalos(idLista));
                compra.setMensaje(transaccion.getMensajeComprador());
                compra.setProducto(new ProductoListaRegalos(detalle.getIdProductoLista()));
                compra.setQuienCompra(null);
                compra.setTotal(transaccion.getTotalPago().longValue());

                compraListaRegalosFacade.create(compra);
            }
        } else {
            CompraListaRegalos compra = new CompraListaRegalos();
            compra.setCantidad(1);
            compra.setFactura(null);
            compra.setFecha(new Date());
            compra.setLista(new ListaRegalos(idLista));
            compra.setMensaje(transaccion.getMensajeComprador());
            compra.setProducto(null);
            compra.setQuienCompra(null);
            compra.setTotal(transaccion.getTotalPago().longValue());

            compraListaRegalosFacade.create(compra);
        }
    }
}
