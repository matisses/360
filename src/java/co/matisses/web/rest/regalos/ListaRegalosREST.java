package co.matisses.web.rest.regalos;

import co.matisses.web.rest.regalos.dto.AgregarProductoListaResponseDTO;
import co.matisses.web.rest.regalos.dto.ConsultaListasResponseDTO;
import co.matisses.web.rest.regalos.dto.ConsultaListasDTO;
import co.matisses.web.rest.regalos.dto.ConsultaProductosListaDTO;
import co.matisses.web.rest.regalos.dto.SolicitudPagoDTO;
import co.matisses.web.rest.regalos.dto.SolicitudPagoResponseDTO;
import co.matisses.web.rest.regalos.dto.RegistroCompraResponseDTO;
import co.matisses.web.rest.regalos.dto.CrearListaResponseDTO;
import co.matisses.web.p2p.DatosPagoPlaceToPayDTO;
import co.matisses.web.p2p.RespuestaPlaceToPayDTO;
import co.matisses.b1ws.client.invoices.InvoiceServiceException;
import co.matisses.b1ws.client.invoices.InvoicesServiceConnector;
import co.matisses.b1ws.dto.SalesDocumentDTO;
import co.matisses.b1ws.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.b1ws.dto.SalesDocumentLineDTO;
import co.matisses.b1ws.dto.SalesEmployeeDTO;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CarritoListaRegalos;
import co.matisses.persistence.web.entity.CategoriaListaRegalos;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.DetalleCarritoListaRegalos;
import co.matisses.persistence.web.entity.EstadoListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.entity.TipoEvento;
import co.matisses.persistence.web.entity.TransaccionPlacetopay;
import co.matisses.persistence.web.facade.CarritoListaRegalosFacade;
import co.matisses.persistence.web.facade.CategoriaListaRegalosFacade;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.persistence.web.facade.TransaccionPlacetopayFacade;
import co.matisses.web.bcs.client.EmailServiceClient;
import co.matisses.web.dto.BCSMailMessageDTO;
import co.matisses.web.dto.CategoriaListaRegalosDTO;
import co.matisses.web.dto.EstadoListaRegalosDTO;
import co.matisses.web.dto.ListaRegalosDTO;
import co.matisses.web.dto.ProductoListaRegalosDTO;
import co.matisses.web.dto.ProductoListaRegalosInvitadoDTO;
import co.matisses.web.dto.RegistroCompraListaRegalosDTO;
import co.matisses.web.dto.SesionListaRegalosDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.TipoEventoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.p2p.P2PServiceConnector;
import co.matisses.web.p2p.P2PWSAuthentication;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.EnumMap;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.imageio.ImageIO;
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
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("listaregalos")
public class ListaRegalosREST {

    private static final Logger log = Logger.getLogger(ListaRegalosREST.class.getSimpleName());
    private String placetopayLogin;
    private String placetopayTranKey;
    private String placetopayServiceURL;
    @Inject
    private BaruApplicationMBean applicationBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @EJB
    private SocioDeNegociosFacade clienteFacade;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private ProductoListaRegalosFacade productoListaFacade;
    @EJB
    private CompraListaRegalosFacade compraRegaloFacade;
    @EJB
    private CategoriaListaRegalosFacade categoriaListaFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemFacade;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private TransaccionPlacetopayFacade transaccionP2PFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private CarritoListaRegalosFacade carritoFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;

    @PostConstruct
    protected void initialize() {
        placetopayLogin = applicationBean.obtenerValorPropiedad("placetopay.login");
        placetopayTranKey = applicationBean.obtenerValorPropiedad("placetopay.trankey");
        placetopayServiceURL = applicationBean.obtenerValorPropiedad("placetopay.url");
    }

    @POST
    @Path("consultarlistas/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarListas(final ConsultaListasDTO consulta) {
        if (consulta == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }
        log.log(Level.INFO, "Consultando listas {0}", consulta.toString());
        List<ListaRegalos> listas = listaRegalosFacade.consultarListas(consulta.getNombre(), consulta.getApellido());
        if (listas.isEmpty()) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontraron listas con los datos ingresados")).build();
        }
        ArrayList<ListaRegalosDTO> dtos = new ArrayList<>();
        for (ListaRegalos entidad : listas) {
            dtos.add(entity2Dto(entidad, null));
        }
        return Response.ok(dtos).build();
    }

    @POST
    @Path("consultarlista/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarLista(final ConsultaListasDTO consulta) {
        if (consulta == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }
        log.log(Level.INFO, "Consultando lista por codigo {0}", consulta.toString());
        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(consulta.getCodigo());
        if (lista == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontró ninguna lista con el código ingresado")).build();
        }
        //List<ProductoListaRegalos> productos = productoListaFacade.consultarPorLista(lista.getIdLista());
        return Response.ok(new ConsultaListasResponseDTO(0, entity2Dto(lista, null))).build();
    }

    @GET
    @Path("cambiarfavorito/{idProducto}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response cambiarValorFavoritoProduco(@PathParam("idProducto") Long idProducto) {
        productoListaFacade.cambiarEstadoFavorito(idProducto);
        return Response.ok().build();
    }

    private ListaRegalosDTO entity2Dto(ListaRegalos entity, List<ProductoListaRegalos> productosEntity) {
        ListaRegalosDTO dto = new ListaRegalosDTO();
        dto.setAceptaBonos(entity.getAceptaBonos());
        dto.setCategoria(new CategoriaListaRegalosDTO(entity.getCategoria().getIdCategoria(), entity.getCategoria().getNombre(), entity.getCategoria().getValorMinimo()));
        dto.setCedulaCocreador(entity.getCedulaCocreador());
        dto.setCedulaCreador(entity.getCedulaCreador());
        dto.setCodigo(entity.getCodigo());
        dto.setEstado(new EstadoListaRegalosDTO(entity.getEstado().getIdEstado(), entity.getEstado().getNombre()));
        dto.setFechaCreacion(entity.getFechaCreacion());
        dto.setFechaEvento(entity.getFechaEvento());
        dto.setIdLista(entity.getIdLista());
        dto.setInvitados(entity.getInvitados());
        dto.setListaPrivada(entity.getListaPrivada());
        dto.setMensajeAgradecimiento(entity.getMensajeAgradecimiento());
        dto.setMensajeBienvenida(entity.getMensajeBienvenida());
        dto.setNombre(entity.getNombre());
        dto.setApellidoCocreador(entity.getApellidoCocreador());
        dto.setApellidoCreador(entity.getApellidoCreador());
        dto.setNombreCocreador(entity.getNombreCocreador());
        dto.setNombreCreador(entity.getNombreCreador());
        dto.setNotificacionCambioCategoriaCreador(entity.getNotificacionCambioCategoriaCreador());
        dto.setNotificacionDiariaCreador(entity.getNotificacionDiariaCreador());
        dto.setNotificacionInmediataCreador(entity.getNotificacionInmediataCreador());
        dto.setNotificacionSemanalCreador(entity.getNotificacionSemanalCreador());
        dto.setNotificacionCambioCategoriaCocreador(entity.getNotificacionCambioCategoriaCocreador());
        dto.setNotificacionDiariaCocreador(entity.getNotificacionDiariaCocreador());
        dto.setNotificacionInmediataCocreador(entity.getNotificacionInmediataCocreador());
        dto.setNotificacionSemanalCocreador(entity.getNotificacionSemanalCocreador());
        dto.setRutaImagenPerfil(entity.getRutaImagenPerfil());
        dto.setRutaImagenPortada(entity.getRutaImagenPortada());
        dto.setPermitirEntregaPersonal(entity.getPermitirEntregaPersonal());
        dto.setTipoEvento(new TipoEventoDTO(entity.getTipoEvento().getIdTipoEvento(), entity.getTipoEvento().getNombre()));
        dto.setValorMinimoBono(entity.getValorMinimoBono());
        if (productosEntity == null) {
            return dto;
        }
        for (ProductoListaRegalos prodEntity : productosEntity) {
            dto.agregarProducto(productoEntity2Dto(prodEntity));
        }
        log.log(Level.INFO, "Se agregaron {0} productos a la lista", dto.getProductos().size());
        return dto;
    }

    private ProductoListaRegalosDTO productoEntity2Dto(ProductoListaRegalos prodEntity) {
        ProductoListaRegalosDTO prodDto = new ProductoListaRegalosDTO();
        prodDto.setCantidadComprada(prodEntity.getCantidadComprada());
        prodDto.setCantidadElegida(prodEntity.getCantidadElegida());
        prodDto.setCantidadEntregada(prodEntity.getCantidadEntregada());
        prodDto.setDescripcionProducto(prodEntity.getDescripcionProducto());
        prodDto.setFavorito(prodEntity.getFavorito());
        prodDto.setIdLista(prodEntity.getLista().getIdLista());
        prodDto.setIdProductoLista(prodEntity.getIdProductoLista());
        prodDto.setMensajeAgradecimiento(prodEntity.getMensajeAgradecimiento());
        prodDto.setReferencia(prodEntity.getReferencia());
        return prodDto;
    }

    @POST
    @Path("consultarlistasusuario/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarListasUsuario(final ConsultaListasDTO consulta) {
        if (consulta == null) {
            return Response.ok(new ConsultaListasResponseDTO(-1, "No se recibieron datos para ejecutar la consulta")).build();
        }
        SesionListaRegalosDTO sesion = applicationBean.obtenerSesionListaRegalos(consulta.getIdSesion());
        if (!sesion.isSesionValida() || sesion.getTipo().equals(SesionListaRegalosDTO.TipoSesion.INVITADO)) {
            //TODO: parametrizar ruta de redireccion
            return Response.temporaryRedirect(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.inicio"))).build();
        }
        log.log(Level.INFO, "Consultando listas {0}", consulta.toString());
        List<ListaRegalos> listas;
        ArrayList<ListaRegalosDTO> dtos = new ArrayList<>();
        SocioDeNegocios cliente = clienteFacade.findByCardCode(consulta.getCedula());
        if (sesion.getTipo().equals(SesionListaRegalosDTO.TipoSesion.ADMINISTRADOR)) {
            //Consulta listas por administrador
            listas = listaRegalosFacade.consultarListasAdministrador(consulta.getPaginacion().getPagina(),
                    consulta.getPaginacion().getRegistrosPagina(), consulta.getPaginacion().getOrderBy());
            if (listas.isEmpty()) {
                return Response.ok(new ConsultaListasResponseDTO(-1, "No se encontró ninguna lista")).build();
            }
            for (ListaRegalos entidad : listas) {
                dtos.add(entity2Dto(entidad, null));
            }
        } else {
            //Consulta listas por propietario
            listas = listaRegalosFacade.consultarListasUsuario(consulta.getCedula(), consulta.getPaginacion().getPagina(),
                    consulta.getPaginacion().getRegistrosPagina(), consulta.getPaginacion().getOrderBy());
            if (listas.isEmpty()) {
                return Response.ok(new ConsultaListasResponseDTO(-1, "El usuario no tiene ninguna lista creada", cliente)).build();
            }
            for (ListaRegalos entidad : listas) {
                dtos.add(entity2Dto(entidad, null));
            }
        }

        return Response.ok(new ConsultaListasResponseDTO(0, cliente, dtos)).build();
    }

    @POST
    @Path("modificar/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response modificarLista(final ListaRegalosDTO lista, @Context HttpServletRequest req) {
        //TODO: validar sesion antes de crear la lista
        log.log(Level.INFO, "Se recibio solicitud para modificar una lista de regalos desde {0}", req.getRemoteAddr());
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes ingresar el nombre de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar el nombre de la lista")).build();
        }
        if (lista.getCedulaCreador() == null || lista.getCedulaCreador().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes ingresar los datos del creador de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar los datos del creador de la lista")).build();
        }
        if (lista.getFechaEvento() == null) {
            log.log(Level.SEVERE, "Debes ingresar la fecha del evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar la fecha del evento")).build();
        }
        if (lista.getFechaEvento().before(new Date())) {
            log.log(Level.SEVERE, "La fecha del evento debe ser en el futuro");
            return Response.ok(new CrearListaResponseDTO(-1, "La fecha del evento debe ser en el futuro")).build();
        }
        if (lista.getTipoEvento() == null || lista.getTipoEvento().getIdTipoEvento() == null) {
            log.log(Level.SEVERE, "Debes seleccionar un tipo de evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes seleccionar un tipo de evento")).build();
        }
        log.log(Level.INFO, "Datos lista a crear: {0}", lista.toString());
        try {
            ListaRegalos entity = listaRegalosFacade.find(lista.getIdLista());
            entity.setAceptaBonos(lista.getAceptaBonos());
            entity.setApellidoCreador(lista.getApellidoCreador());
            entity.setApellidoCocreador(lista.getApellidoCocreador());
            entity.setCategoria(new CategoriaListaRegalos(1L));
            entity.setCedulaCocreador(lista.getCedulaCocreador());
            entity.setCedulaCreador(lista.getCedulaCreador());
            entity.setEstado(new EstadoListaRegalos(1L));
            entity.setFechaCreacion(new Date());
            entity.setFechaEvento(lista.getFechaEvento());
            entity.setTipoEvento(new TipoEvento(lista.getTipoEvento().getIdTipoEvento(), lista.getTipoEvento().getNombre()));
            entity.setInvitados(lista.getInvitados());
            entity.setListaPrivada(lista.getListaPrivada());
            entity.setMensajeAgradecimiento(lista.getMensajeAgradecimiento());
            entity.setMensajeBienvenida(lista.getMensajeBienvenida());
            entity.setNombre(lista.getNombre());
            entity.setNombreCreador(lista.getNombreCreador());
            entity.setNombreCocreador(lista.getNombreCocreador());
            entity.setNotificacionCambioCategoriaCreador(lista.getNotificacionCambioCategoriaCreador());
            entity.setNotificacionDiariaCreador(lista.getNotificacionDiariaCreador());
            entity.setNotificacionInmediataCreador(lista.getNotificacionInmediataCreador());
            entity.setNotificacionSemanalCreador(lista.getNotificacionSemanalCreador());
            entity.setNotificacionCambioCategoriaCocreador(lista.getNotificacionCambioCategoriaCocreador());
            entity.setNotificacionDiariaCocreador(lista.getNotificacionDiariaCocreador());
            entity.setNotificacionInmediataCocreador(lista.getNotificacionInmediataCocreador());
            entity.setNotificacionSemanalCocreador(lista.getNotificacionSemanalCocreador());
            entity.setRutaImagenPerfil(lista.getRutaImagenPerfil());
            entity.setRutaImagenPortada(lista.getRutaImagenPortada());
            entity.setPermitirEntregaPersonal(lista.getPermitirEntregaPersonal());
            entity.setValorMinimoBono(lista.getValorMinimoBono());
            listaRegalosFacade.edit(entity);
            log.log(Level.INFO, "Se modifico la lista {0}", entity.getCodigo());
            return Response.ok(new CrearListaResponseDTO(0, entity.getCodigo())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("crear/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response crearLista(final ListaRegalosDTO lista, @Context HttpServletRequest req) {
        log.log(Level.INFO, "Se recibio solicitud para crear nueva lista de regalos desde {0}", req.getRemoteAddr());
        if (lista.getNombre() == null || lista.getNombre().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes ingresar el nombre de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar el nombre de la lista")).build();
        }
        if (lista.getCedulaCreador() == null || lista.getCedulaCreador().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes ingresar los datos del creador de la lista");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar los datos del creador de la lista")).build();
        }
        if (lista.getFechaEvento() == null) {
            log.log(Level.SEVERE, "Debes ingresar la fecha del evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes ingresar la fecha del evento")).build();
        }
        if (lista.getFechaEvento().before(new Date())) {
            log.log(Level.SEVERE, "La fecha del evento debe ser en el futuro");
            return Response.ok(new CrearListaResponseDTO(-1, "La fecha del evento debe ser en el futuro")).build();
        }
        if (lista.getTipoEvento() == null || lista.getTipoEvento().getIdTipoEvento() == null) {
            log.log(Level.SEVERE, "Debes seleccionar un tipo de evento");
            return Response.ok(new CrearListaResponseDTO(-1, "Debes seleccionar un tipo de evento")).build();
        }
        log.log(Level.INFO, "Datos lista a crear: {0}", lista.toString());
        try {
            ListaRegalos entity = new ListaRegalos();
            entity.setActiva(true);
            entity.setAceptaBonos(lista.getAceptaBonos());
            entity.setApellidoCreador(lista.getApellidoCreador());
            entity.setApellidoCocreador(lista.getApellidoCocreador());
            entity.setCategoria(new CategoriaListaRegalos(1L));
            entity.setCedulaCocreador(lista.getCedulaCocreador());
            entity.setCedulaCreador(lista.getCedulaCreador());
            entity.setCodigo(RandomStringUtils.randomAlphanumeric(8));
            entity.setEstado(new EstadoListaRegalos(1L));
            entity.setFechaCreacion(new Date());
            entity.setFechaEvento(lista.getFechaEvento());
            entity.setTipoEvento(new TipoEvento(lista.getTipoEvento().getIdTipoEvento(), lista.getTipoEvento().getNombre()));
            entity.setInvitados(lista.getInvitados());
            entity.setListaPrivada(lista.getListaPrivada());
            entity.setMensajeAgradecimiento(lista.getMensajeAgradecimiento());
            entity.setMensajeBienvenida(lista.getMensajeBienvenida());
            entity.setNombre(lista.getNombre());
            entity.setNombreCreador(lista.getNombreCreador());
            entity.setNombreCocreador(lista.getNombreCocreador());
            entity.setNotificacionCambioCategoriaCreador(lista.getNotificacionCambioCategoriaCreador());
            entity.setNotificacionDiariaCreador(lista.getNotificacionDiariaCreador());
            entity.setNotificacionInmediataCreador(lista.getNotificacionInmediataCreador());
            entity.setNotificacionSemanalCreador(lista.getNotificacionSemanalCreador());
            entity.setNotificacionCambioCategoriaCocreador(lista.getNotificacionCambioCategoriaCocreador());
            entity.setNotificacionDiariaCocreador(lista.getNotificacionDiariaCocreador());
            entity.setNotificacionInmediataCocreador(lista.getNotificacionInmediataCocreador());
            entity.setNotificacionSemanalCocreador(lista.getNotificacionSemanalCocreador());
            entity.setRutaImagenPerfil(lista.getRutaImagenPerfil());
            entity.setRutaImagenPortada(lista.getRutaImagenPortada());
            entity.setPermitirEntregaPersonal(lista.getPermitirEntregaPersonal());
            entity.setValorMinimoBono(lista.getValorMinimoBono());
            listaRegalosFacade.create(entity);
            crearCodigoQR(entity.getCodigo());
            log.log(Level.INFO, "Se creo la lista {1} con codigo {0}", new Object[]{entity.getCodigo(), entity.getIdLista()});
            return Response.ok(new CrearListaResponseDTO(0, entity.getCodigo())).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("comprarbono/{codigoLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarBonoRegaloCarrito(@PathParam("codigoLista") String codigoLista, SesionListaRegalosDTO sesion) {
        log.log(Level.INFO, "Se recibio solicitud para agregar un bono al carrito de compras");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }
        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
        if (lista == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se encontro una lista con el codigo enviado")).build();
        }
        if (sesion.getValorBono() == 0 && sesion.getValorBono() < lista.getValorMinimoBono()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El valor del bono debe ser superior al valor minimo")).build();
        }
        sesionDto.setValorBono(sesion.getValorBono());
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("nocomprarbono/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response quitarBonoRegaloCarrito(SesionListaRegalosDTO sesion) {
        log.log(Level.INFO, "Se recibio solicitud para quitar un bono del carrito de compras");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }
        sesionDto.setValorBono(0);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("comprarproducto/{codigoLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarProductoCarrito(SesionListaRegalosDTO sesion, @PathParam("codigoLista") String codigoLista) {
        log.log(Level.INFO, "Agregando producto de una lista al carrito de compras {0}", sesion);
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }
        if (sesion.getProductos().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        if (sesion.getProductos().get(0) == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        ProductoListaRegalosInvitadoDTO producto = sesion.getProductos().get(0);
        if (producto == null || producto.getReferencia() == null || producto.getReferencia().trim().isEmpty() || producto.getCantidad() <= 0) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        log.log(Level.INFO, sesion.toString());
        //Validar que la sesion exista
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }
        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
        ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaProducto(lista.getIdLista(), producto.getIdProductoLista());
        if ((productoLista.getCantidadElegida() - productoLista.getCantidadComprada()) < producto.getCantidad()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad a comprar es mayor que la cantidad elegida")).build();
        }

        sesionDto.agregarProducto(producto);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("nocomprarproducto/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response retirarProductoCarrito(SesionListaRegalosDTO sesion) {
        log.log(Level.INFO, "Se recibio solicitud para retirar un producto de una lista del carrito de compras");
        if (sesion == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibieron datos para continuar")).build();
        }
        if (sesion.getIdSession() == null || sesion.getIdSession().trim().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió un código de sesión a la cual asociar el producto")).build();
        }

        if (sesion.getProductos().isEmpty()) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        ProductoListaRegalosInvitadoDTO producto = sesion.getProductos().get(0);
        if (producto == null || producto.getReferencia() == null || producto.getReferencia().trim().isEmpty() || producto.getCantidad() <= 0) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibió información del producto")).build();
        }
        log.log(Level.INFO, sesion.toString());
        //Validar que la sesion exista
        SesionListaRegalosDTO sesionDto = applicationBean.obtenerSesionListaRegalos(sesion.getIdSession());
        if (sesionDto == null) {
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La sesion no es valida")).build();
        }
        sesionDto.eliminarProducto(producto);
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    @POST
    @Path("agregarproducto/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response agregarProducto(ProductoListaRegalosDTO producto) {
        log.log(Level.INFO, "Se recibio solicitud para agregar un producto a una lista");
        if (producto == null) {
            log.log(Level.SEVERE, "No se recibio un producto para agregar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibio un producto para agregar.")).build();
        }
        if (producto.getReferencia() == null || producto.getReferencia().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes seleccionar una referencia.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Debes seleccionar una referencia.")).build();
        }
        if (producto.getCantidadElegida() == null || producto.getCantidadElegida() <= 0) {
            log.log(Level.SEVERE, "La cantidad debe ser mayor que cero.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad debe ser mayor que cero.")).build();
        }
        //consultar si la lista ya tiene agregada esa referencia
        if (!productoListaFacade.productoPuedeAgregarse(producto.getIdLista(), producto.getReferencia())) {
            log.log(Level.SEVERE, "El producto ya se encuentra en la lista.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El producto ya se encuentra en la lista.")).build();
        }
        int saldoDisponible = consultarSaldoVentaItem(producto.getReferencia());
        if (saldoDisponible < producto.getCantidadElegida()) {
            log.log(Level.SEVERE, "La cantidad elegida es mayor que la cantidad disponible E={0} D={1}.", new Object[]{producto.getCantidadElegida(), saldoDisponible});
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad elegida es mayor que la cantidad disponible. (Disponible: " + saldoDisponible + "). ")).build();
        }

        try {
            Object[] priceAndTax = itemFacade.getItemPriceAndTax(producto.getReferencia(), false);
            ProductoListaRegalos entity = new ProductoListaRegalos();
            entity.setCantidadElegida(producto.getCantidadElegida());
            entity.setCantidadComprada(0);
            entity.setCantidadEntregada(0);
            entity.setDescripcionProducto(producto.getDescripcionProducto());
            entity.setFavorito(producto.getFavorito());
            entity.setLista(new ListaRegalos(producto.getIdLista()));
            entity.setMensajeAgradecimiento(producto.getMensajeAgradecimiento());
            entity.setPrecio((Integer) priceAndTax[0]);
            entity.setImpuesto((Integer) priceAndTax[1]);
            entity.setReferencia(producto.getReferencia());
            entity.setActivo(true);

            productoListaFacade.create(entity);
            return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al agregar el producto a la lista. ", e);
            return Response.serverError().build();
        }
    }

    @POST
    @Path("modificarproducto/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response modificarProducto(ProductoListaRegalosDTO producto) {
        log.log(Level.INFO, "Se recibio solicitud para modificar un producto de una lista");
        if (producto == null) {
            log.log(Level.SEVERE, "No se recibio un producto para modificar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "No se recibio un producto para modificar.")).build();
        }
        if (producto.getReferencia() == null || producto.getReferencia().trim().isEmpty()) {
            log.log(Level.SEVERE, "Debes seleccionar una referencia.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Debes seleccionar una referencia.")).build();
        }
        //consultar si la lista tiene agregada esa referencia
        if (productoListaFacade.productoPuedeAgregarse(producto.getIdLista(), producto.getReferencia())) {
            log.log(Level.SEVERE, "El producto no se encuentra en la lista y por lo tanto no se puede modificar.");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "El producto no se encuentra en la lista y por lo tanto no se puede modificar.")).build();
        }
        int saldoDisponible = consultarSaldoVentaItem(producto.getReferencia());
        if (saldoDisponible < producto.getCantidadElegida()) {
            log.log(Level.SEVERE, "La cantidad elegida es mayor que la cantidad disponible E={0} D={1}.", new Object[]{producto.getCantidadElegida(), saldoDisponible});
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "La cantidad elegida es mayor que la cantidad disponible. (Disponible: " + saldoDisponible + "). ")).build();
        }
        ProductoListaRegalos entidad = productoListaFacade.consultarPorListaReferencia(producto.getIdLista(), producto.getReferencia());
        if (entidad == null) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el producto para procesarlo");
            return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al consultar el producto para procesarlo.")).build();
        }
        //Si la cantidad enviada es cero, se elimina el producto
        if (producto.getCantidadElegida() == null || producto.getCantidadElegida() <= 0) {
            try {
                productoListaFacade.remove(entidad);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar el registro");
                return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al eliminar el registro.")).build();
            }
        } else {
            entidad.setCantidadElegida(producto.getCantidadElegida());
            entidad.setFavorito(producto.getFavorito());
            entidad.setMensajeAgradecimiento(producto.getMensajeAgradecimiento());
            try {
                productoListaFacade.edit(entidad);
                log.log(Level.INFO, "La modificacion de la lista se realizo satisfactoriamente. ");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al modificar el registro");
                return Response.ok(new AgregarProductoListaResponseDTO(-1, "Ocurrio un error al modificar el registro.")).build();
            }
        }
        return Response.ok(new AgregarProductoListaResponseDTO(0, null)).build();
    }

    private int consultarSaldoVentaItem(String referencia) {
        int saldoTotal = 0;
        try {
            List<SaldoItemInventario> saldos = saldoItemFacade.findWithParameters(referencia, "0", true);
            for (SaldoItemInventario saldo : saldos) {
                saldoTotal += saldo.getOnHand().intValue();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el saldo para el item " + referencia + ". ", e);
        }
        return saldoTotal;
    }

    private void crearCodigoQR(String codigoLista) {
        String myCodeText = applicationBean.obtenerValorPropiedad("listaregalos.url.web.invitado") + codigoLista;
        String filePath = applicationBean.obtenerValorPropiedad("listaregalos.url.local.imagenes") + codigoLista + ".png";
        log.log(Level.INFO, "Creando codigo QR {0}", filePath);
        int size = 250;
        String fileType = "png";
        File myFile = new File(filePath);
        try {
            Map<EncodeHintType, Object> hintMap = new EnumMap<>(EncodeHintType.class);
            hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");
            // Now with zxing version 3.2.1 you could change border size (white border size to just 1)
            hintMap.put(EncodeHintType.MARGIN, 1);
            /* default = 4 */
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            BitMatrix byteMatrix = qrCodeWriter.encode(myCodeText, BarcodeFormat.QR_CODE, size, size, hintMap);
            int width = byteMatrix.getWidth();
            BufferedImage image = new BufferedImage(width, width, BufferedImage.TYPE_INT_RGB);
            image.createGraphics();

            Graphics2D graphics = (Graphics2D) image.getGraphics();
            graphics.setColor(Color.WHITE);
            graphics.fillRect(0, 0, width, width);
            graphics.setColor(Color.BLACK);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(i, j)) {
                        graphics.fillRect(i, j, 1, 1);
                    }
                }
            }
            ImageIO.write(image, fileType, myFile);
        } catch (WriterException | IOException e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el codigo QR", e);
        }
    }

    @POST
    @Path("registrarcompra/{idLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response registrarCompra(@PathParam("idLista") Long idLista, RegistroCompraListaRegalosDTO[] compras) {
        log.log(Level.INFO, "Se recibio una solicitud de registro de compra de productos de lista de regalos");
        if (compras == null || compras.length == 0) {
            log.log(Level.SEVERE, "No se recibieron datos para registrar.");
            return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibieron datos para registrar.")).build();
        }
        if (idLista == null || idLista <= 0) {
            log.log(Level.SEVERE, "No se recibio el codigo de la lista a la cual se asociará la compra.");
            return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibió el código de la lista a la cual se asociará la compra.")).build();
        }
        List<ProductoListaRegalos> productos = new ArrayList<>();
        List<CompraListaRegalos> registros = new ArrayList<>();
        for (RegistroCompraListaRegalosDTO compra : compras) {
            if (compra.getIdProducto() == null || compra.getIdProducto() <= 0) {
                log.log(Level.SEVERE, "No se recibio el codigo del producto al cual se asociara la compra.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibió el código del producto al cual se asociará la compra.")).build();
            }
            if (compra.getValor() == null || compra.getValor() <= 0) {
                log.log(Level.SEVERE, "No se recibio el valor total de la compra.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibió valor total de la compra.")).build();
            }
            //valida que el producto si pertenezca a la lista
            ProductoListaRegalos entidadProducto = productoListaFacade.find(compra.getIdProducto());
            ProductoListaRegalos producto = productoListaFacade.consultarPorListaProducto(idLista, compra.getIdProducto());
            if (producto == null) {
                log.log(Level.SEVERE, "El producto {0} no pertenece a la lista {1}.", new Object[]{compra.getIdProducto(), idLista});
                return Response.ok(new RegistroCompraResponseDTO(-1, "El producto " + compra.getIdProducto() + " no pertenece a la lista " + idLista + ". ")).build();
            }
            if (compra.getCantidad() == null || compra.getCantidad() <= 0) {
                log.log(Level.SEVERE, "La cantidad comprada debe ser mayor que cero.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "La cantidad comprada debe ser mayor que cero.")).build();
            }
            //Validar que la cantidad comprada no supere la cantidad solicitada
            if (compra.getCantidad() > entidadProducto.getCantidadElegida()
                    || compra.getCantidad() + entidadProducto.getCantidadComprada() > entidadProducto.getCantidadElegida()) {
                log.log(Level.SEVERE, "La cantidad comprada excede la cantidad maxima requerida por el propietario de la lista.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "La cantidad comprada excede la cantidad máxima requerida por el propietario de la lista.")).build();
            }
            if (compra.getFactura() == null || compra.getFactura().trim().isEmpty()) {
                log.log(Level.SEVERE, "No se recibio el numero de la factura.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibió el número de la factura.")).build();
            }
            if (compra.getQuienCompra() == null || compra.getQuienCompra().trim().isEmpty()) {
                log.log(Level.SEVERE, "No se recibio el nombre de la persona que realiza la compra.");
                return Response.ok(new RegistroCompraResponseDTO(-1, "No se recibió el nombre de la persona que realiza la compra.")).build();
            }
            CompraListaRegalos entity = new CompraListaRegalos();
            entity.setCantidad(compra.getCantidad());
            entity.setFactura(compra.getFactura());
            entity.setFecha(new Date());
            entity.setLista(new ListaRegalos(idLista));
            entity.setMensaje(compra.getMensaje());
            entity.setProducto(new ProductoListaRegalos(compra.getIdProducto()));
            entity.setQuienCompra(compra.getQuienCompra());
            entity.setTotal(compra.getValor());
            registros.add(entity);

            //Actualiza la cantidad comprada para el producto
            entidadProducto.setCantidadComprada(entidadProducto.getCantidadComprada() + compra.getCantidad());
            productos.add(entidadProducto);
        }
        for (CompraListaRegalos compra : registros) {
            try {
                compraRegaloFacade.create(compra);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al realizar el registro de compra de producto. " + compra.toString(), e);
                return Response.ok(new RegistroCompraResponseDTO(-1, "Ocurrio un error al realizar el registro de compra de producto. ")).build();
            }
        }

        //Validar total de compras de la lista para ascenso de categoria
        try {
            Long total = compraRegaloFacade.consultarTotalCompradoLista(idLista);
            CategoriaListaRegalos categoria = categoriaListaFacade.consultarCategoriaValor(total);
            ListaRegalos lista = listaRegalosFacade.find(idLista);
            if (!categoria.equals(lista.getCategoria())) {
                log.log(Level.INFO, "La lista {0} ha subidio a la categoria {1} ya que ha acumulado ${2} en compras", new Object[]{lista.getNombre(), categoria.getNombre(), NumberFormat.getInstance().format(total)});
                lista.setCategoria(categoria);
                listaRegalosFacade.edit(lista);
                //TODO: enviar correos de notificacion de cambio de estado
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar el status de la lista. ", e);
            //TODO: enviar correo de notificacion
        }

        //Actualiza el numero de items comprados para el producto
        for (ProductoListaRegalos producto : productos) {
            try {
                productoListaFacade.edit(producto);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al actualizar el numero de items comprados para el producto " + producto.getIdProductoLista(), e);
            }
        }
        return Response.ok(new RegistroCompraResponseDTO(0, null)).build();
    }

    @GET
    @Path("desactivar/{codigoLista}")
    public Response desactivarLista(@PathParam("codigoLista") String codigoLista) {
        log.log(Level.INFO, "Desactivando la lista {0}", codigoLista);
        listaRegalosFacade.desactivarLista(codigoLista);
        return Response.ok().build();
    }

    @POST
    @Path("consultarproductos/sinpaginar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarProductosListaSinPaginar(Long idLista) {
        List<ProductoListaRegalos> entidades;
        int productosValidos;
        List<ProductoListaRegalosDTO> dtos = new ArrayList<>();
        do {
            dtos = new ArrayList<>();
            productosValidos = 0;
            entidades = productoListaFacade.consultarPorLista(idLista);
            //Valida que el saldo de cada producto sea suficiente para la cantidad pendiente (solicitada-comprada)
            //Si un producto no cumple esta condicion, lo desactiva de la lista, notifica al propietario de la misma y 
            //vuelve a realizar la consulta
            for (ProductoListaRegalos entidad : entidades) {
                Integer saldo = saldoItemFacade.obtenerTotalSaldoVenta(entidad.getReferencia());
                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
                    productosValidos++;
                    dtos.add(productoEntity2Dto(entidad));
                } else {
                    //TODO: notificar al propietario de la lista
                    log.log(Level.WARNING, "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
                    //TODO: notificar al administrador de la lista para que se contacte con el cliente
                    try {
                        entidad.setActivo(false);
                        productoListaFacade.edit(entidad);
                    } catch (Exception e) {
                        return Response.ok(dtos).build();
                    }
                    break;
                }
            }
        } while (productosValidos != entidades.size());
        return Response.ok(dtos).build();
    }

    @POST
    @Path("consultarproductos/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarProductosLista(ConsultaProductosListaDTO consulta) {
        List<ProductoListaRegalos> entidades;
        int productosValidos;
        List<ProductoListaRegalosDTO> dtos = new ArrayList<>();
        do {
            dtos = new ArrayList<>();
            productosValidos = 0;
            entidades = productoListaFacade.consultarPorListaPaginacion(consulta.getIdLista(),
                    consulta.getPagina(), consulta.getRegistrosPagina(), consulta.getOrderBy());
            //Valida que el saldo de cada producto sea suficiente para la cantidad pendiente (solicitada-comprada)
            //Si un producto no cumple esta condicion, lo desactiva de la lista, notifica al propietario de la misma y 
            //vuelve a realizar la consulta
            for (ProductoListaRegalos entidad : entidades) {
                Integer saldo = saldoItemFacade.obtenerTotalSaldoVenta(entidad.getReferencia());
                if (entidad.getCantidadElegida() - entidad.getCantidadComprada() <= saldo) {
                    productosValidos++;
                    dtos.add(productoEntity2Dto(entidad));
                } else {
                    //TODO: notificar al propietario de la lista
                    log.log(Level.WARNING, "El producto {0} no tiene saldo suficiente para satisfacer la cantidad solicitada. Req: {1}, Disp: {2}. Se desactiva el producto y se notifica al propietario de la lista",
                            new Object[]{entidad.getReferencia(), entidad.getCantidadElegida() - entidad.getCantidadComprada(), saldo});
                    //TODO: notificar al administrador de la lista para que se contacte con el cliente
                    try {
                        entidad.setActivo(false);
                        productoListaFacade.edit(entidad);
                    } catch (Exception e) {
                        return Response.ok(dtos).build();
                    }
                    break;
                }
            }
        } while (productosValidos != entidades.size());
        return Response.ok(dtos).build();
    }

    @GET
    @Path("consultartotalproductos/{idLista}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarTotalProductosLista(@PathParam("idLista") Long idLista) {
        return Response.ok(productoListaFacade.consultarTotalProductosLista(idLista)).build();
    }

    @GET
    @Path("consultartotallistasusuario/{cedula}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarTotalListasUsuario(@PathParam("cedula") String cedula) {
        return Response.ok(listaRegalosFacade.consultarTotalListasUsuario(cedula)).build();
    }

    @GET
    @Path("consultartotallistasadmin/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarTotalListasUsuario() {
        return Response.ok(listaRegalosFacade.consultarTotalListasUsuario(null)).build();
    }

    @GET
    @Path("cambiarimagen/{codigoLista}/{nombre}/{tipo}")
    @Produces(MediaType.MULTIPART_FORM_DATA)
    public Response configurarImagenPredefinida(@PathParam("codigoLista") String codigoLista, @PathParam("nombre") String nombre, @PathParam("tipo") String tipo) {
        log.log(Level.INFO, "Configurando la imagen {0} como {1} para la lista {2}", new Object[]{nombre, tipo, codigoLista});
        if (nombre == null || nombre.trim().isEmpty() || tipo == null || tipo.trim().isEmpty()
                || codigoLista == null || codigoLista.trim().isEmpty()) {
            log.log(Level.SEVERE, "Los parametros enviados no son validos. No se configurara una nueva imagen");
            return Response.ok().build();
        }
        try {
            ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigoLista);
            String rutaImagenes = applicationBean.obtenerValorPropiedad("listaregalos.url.local.imagenes");
            InputStream isStream = new FileInputStream(new File(rutaImagenes + "imagenes-predisenadas" + File.separator + lista.getTipoEvento().getIdTipoEvento() + File.separator + tipo + File.separator + nombre));
            String extension = nombre.substring(nombre.lastIndexOf("."));
            if (tipo.equals("portada")) {
                String fullFilePath = applicationBean.obtenerValorPropiedad("listaregalos.url.local.imagenes") + codigoLista + "_P" + extension;
                log.log(Level.INFO, "Guardando imagen {0}", fullFilePath);
                saveFile(isStream, fullFilePath);
                lista.setRutaImagenPortada(applicationBean.obtenerValorPropiedad("listaregalos.url.web.imagenes") + codigoLista + "_P" + extension);
            } else if (tipo.equals("perfil")) {
                String fullFilePath = applicationBean.obtenerValorPropiedad("listaregalos.url.local.imagenes") + codigoLista + extension;
                log.log(Level.INFO, "Guardando imagen {0}", fullFilePath);
                saveFile(isStream, fullFilePath);
                lista.setRutaImagenPerfil(applicationBean.obtenerValorPropiedad("listaregalos.url.web.imagenes") + codigoLista + extension);
            }
            listaRegalosFacade.edit(lista);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al configurar la nueva imagen. ", e);
        }

        return Response.ok().build();
    }

    @POST
    @Path("subirimagen/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response subirImagen(MultipartFormDataInput input) {
        Map<String, List<InputPart>> formParts = input.getFormDataMap();
        try {
            String codigo = formParts.get("codigo").get(0).getBody(String.class, null);
            log.log(Level.INFO, "Codigo={0}", codigo);
            String tipoImagen = formParts.get("tipoImagen").get(0).getBody(String.class, null);
            log.log(Level.INFO, "Tipo Imagen={0}", tipoImagen);
            String sesion = formParts.get("sesion").get(0).getBody(String.class, null);
            log.log(Level.INFO, "Sesion={0}", sesion);
            InputStream istream = formParts.get("file").get(0).getBody(InputStream.class, null);
            String fileName = parseFileName(formParts.get("file").get(0).getHeaders(), codigo, tipoImagen);
            String fullFilePath = applicationBean.obtenerValorPropiedad("listaregalos.url.local.imagenes") + fileName;
            String fullWebPath = applicationBean.obtenerValorPropiedad("listaregalos.url.web.imagenes") + fileName;
            log.log(Level.INFO, "Guardando imagen de {1} {0}", new Object[]{fileName, tipoImagen});
            saveFile(istream, fullFilePath);
            try {
                ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(codigo);
                if (tipoImagen.equals("portada")) {
                    lista.setRutaImagenPortada(fullWebPath);
                } else {
                    lista.setRutaImagenPerfil(fullWebPath);
                }
                listaRegalosFacade.edit(lista);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al actualizar la ruta de la imagen de portada. ", e);
            }

            //TODO: validar codigo sesion
            //TODO: validar que la sesion si sea de un propietario de la lista
            return Response.temporaryRedirect(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.editar")
                    .concat(codigo).concat("&sesion=").concat(sesion))).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al recibir la imagen. ", e);
        }
        //TODO: parametrizar ruta de redireccion
        return Response.temporaryRedirect(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.inicio"))).build();
    }

    @POST
    @Path("procesarpago/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response irAPagar(final SolicitudPagoDTO solicitudPago, @Context HttpServletRequest req) {
        //TODO: validar datos recibidos
        if (placetopayLogin == null || placetopayTranKey == null) {
            return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrió un error al contactar el servicio de Place to Pay")).build();
        }
        SesionListaRegalosDTO sesionDto = solicitudPago.getSesion();
        sesionDto = applicationBean.obtenerSesionListaRegalos(sesionDto.getIdSession());
        if (sesionDto == null || !sesionDto.isSesionValida()) {
            return Response.ok(new SolicitudPagoResponseDTO(-1, "La sesion enviada no es válida. Es necesario volver a inicar el proceso")).build();
        }
        if (sesionDto.getProductos().isEmpty() && sesionDto.getValorBono() == 0) {
            return Response.ok(new SolicitudPagoResponseDTO(-1, "Se deben incluir productos o un bono para poder realizar el pago")).build();
        }

        //Generar parametros placetopay
        DatosPagoPlaceToPayDTO datosPago = solicitudPago.getDatosPago();
        datosPago.setIpAddress(req.getRemoteAddr());
        datosPago.getPayment().setReference(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        P2PWSAuthentication p2pAuth = new P2PWSAuthentication(placetopayLogin, placetopayTranKey);
        datosPago.setAuth(p2pAuth.getLogin(), p2pAuth.getSeed(), p2pAuth.getNonce(), p2pAuth.getTranKey());
        if (datosPago.getBuyer().getDocumentType().equals("13")) {
            datosPago.getBuyer().setDocumentType("CC");
        } else if (datosPago.getBuyer().getDocumentType().equals("31")) {
            datosPago.getBuyer().setDocumentType("NIT");
        } else if (datosPago.getBuyer().getDocumentType().equals("22")) {
            datosPago.getBuyer().setDocumentType("CE");
        } else if (datosPago.getBuyer().getDocumentType().equals("12")) {
            datosPago.getBuyer().setDocumentType("TI");
        } else if (datosPago.getBuyer().getDocumentType().equals("41")) {
            datosPago.getBuyer().setDocumentType("PPN");
        }

        GregorianCalendar expira = new GregorianCalendar();
        expira.add(Calendar.HOUR, 1);
        datosPago.setExpiration((new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault())).format(expira.getTime()));
        //TODO: parametrizar ruta de respuesta
        datosPago.setReturnUrl(applicationBean.obtenerValorPropiedad("listaregalos.url.web.pago.notificar") + datosPago.getPayment().getReference());

        TransaccionPlacetopay transaccion = crearTransaccionPlaceToPay(solicitudPago.getCodigoLista(),
                solicitudPago.getSesion().getIdSession(), datosPago, sesionDto.getValorBono() > 0 ? "B" : "P", solicitudPago.getMensaje());
        log.log(Level.INFO, "Se registro la transaccion en la base de datos, pendiente por respuesta de placetopay. {0}", transaccion);
        try {
            RespuestaPlaceToPayDTO respuestaP2P;
            try {
                respuestaP2P = P2PServiceConnector.postPlaceToPayRequest(placetopayServiceURL, "session",
                        datosPago, RespuestaPlaceToPayDTO.class);
            } catch (Exception e) {
                if (e.getMessage().contains("(401) Unauthorized")) {
                    DatosPagoPlaceToPayDTO.Buyer buyer = datosPago.getBuyer();
                    datosPago.setBuyer(null);
                    respuestaP2P = P2PServiceConnector.postPlaceToPayRequest(placetopayServiceURL, "session",
                            datosPago, RespuestaPlaceToPayDTO.class);
                    datosPago.setBuyer(buyer);
                } else {
                    log.log(Level.SEVERE, "Ocurrio un error al conectarse con placetopay. ", e);
                    //TODO: notificar al dpto sistemas
                    return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrio un error al conectarse con placetopay. " + e.getMessage())).build();
                }
            }

            actualizarTransaccionPlaceToPay(transaccion, respuestaP2P);
            if (respuestaP2P.getStatus().getStatus().equalsIgnoreCase("OK")) {
                if (sesionDto.getProductos().size() > 0 && sesionDto.getValorBono() == 0) {
                    try {
                        Object[] respFactura = crearFactura("Compra para lista de regalos "
                                + solicitudPago.getCodigoLista(), sesionDto.getProductos(), datosPago);
                        if ((int) respFactura[0] == 0) {
                            String[] docs = ((String) respFactura[1]).split(";");
                            //Actualiza el registro de la transaccion
                            actualizarTransaccionFactura(transaccion, docs[0], Long.parseLong(docs[1]));
                            //Guarda el carrito en base de datos si la compra incluia productos
                            guardarCarrito(solicitudPago);
                        } else {
                            //TODO: actualizar estado transaccion en base de datos (y placetopay?)
                            return Response.ok(new SolicitudPagoResponseDTO(-1, (String) respFactura[1])).build();
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear la factura. ", e);
                        return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrió un error al generar la factura. " + e.getMessage())).build();
                    }
                }
                return Response.ok(new SolicitudPagoResponseDTO(0, respuestaP2P)).build();
            } else {
                return Response.ok(new SolicitudPagoResponseDTO(-1, respuestaP2P.getStatus().getMessage())).build();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al conectarse con placetopay. ", e);
            //TODO: notificar al dpto sistemas
            return Response.ok(new SolicitudPagoResponseDTO(-1, "Ocurrio un error al conectarse con placetopay. " + e.getMessage())).build();
        }
    }

    @GET
    @Path("consultarbonos/{idLista}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarCompraBonosLista(@PathParam("idLista") Long idLista) {
        try {
            return Response.ok(compraListaRegalosFacade.consultarTotalCompradoBonosLista(idLista)).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el valor total comprado en bonos para una lista. ", e);
            return Response.ok(0L).build();
        }
    }

    private void guardarCarrito(SolicitudPagoDTO solicitudPago) {
        try {
            CarritoListaRegalos carrito = new CarritoListaRegalos();
            carrito.setIdSesion(solicitudPago.getSesion().getIdSession());
            carrito.setNit(solicitudPago.getDatosPago().getBuyer().getDocument());
            carrito.setTipoSesion(solicitudPago.getSesion().getTipo().getTipo());
            ArrayList<DetalleCarritoListaRegalos> productos = new ArrayList<>();
            for (ProductoListaRegalosInvitadoDTO producto : solicitudPago.getSesion().getProductos()) {
                DetalleCarritoListaRegalos detalle = new DetalleCarritoListaRegalos();
                detalle.setCarrito(carrito);
                detalle.setCantidad(producto.getCantidad());
                detalle.setDescripcion(producto.getDescripcion());
                detalle.setIdProductoLista(producto.getIdProductoLista());
                detalle.setImpuesto(producto.getImpuesto());
                detalle.setNombre(producto.getNombre());
                detalle.setPrecio(producto.getPrecio());
                detalle.setReferencia(producto.getReferencia());
                productos.add(detalle);
            }
            carrito.setProductos(productos);
            carritoFacade.create(carrito);
            log.log(Level.INFO, "Se guardo el carrito en base de datos con id={0}", carrito.getIdCarrito());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al guardar el carrito en base de datos. ", e);
        }
    }

    private Object[] crearFactura(String mensaje, List<ProductoListaRegalosInvitadoDTO> productos, DatosPagoPlaceToPayDTO datosPago) throws InvoiceServiceException {
        //consultar datos configurados para el detalle de la factura
        // 0. Codigo serie numeracion
        // 1. Nombre serie numeracion
        // 2. Codigo ventas
        // 3. Codigo logistica
        // 4. Codigo ruta
        // 5. Codigo proyecto
        // 6. WUID
        Object[] datosFacturaAlmacen = almacenFacade.cargarDatosFacturaWeb();
        if (!datosPago.getBuyer().getDocument().toUpperCase().endsWith("CL")) {
            datosPago.getBuyer().setDocument(datosPago.getBuyer().getDocument() + "CL");
        }
        SalesDocumentDTO enc = new SalesDocumentDTO();
        enc.setCardCode(datosPago.getBuyer().getDocument());
        enc.setComments(mensaje);
        enc.setPaymentGroupCode("17");
        enc.setShippingStatus("P");
        enc.setSource("L");

        //Se asigna el asesor generico a las compras por el sitio
        SalesEmployeeDTO salesEmp = new SalesEmployeeDTO();
        salesEmp.setSlpCode("98");
        salesEmp.setName("Vendedor Web");
        enc.addSalesEmployee(salesEmp);

        enc.setSeriesCode((String) datosFacturaAlmacen[0]);
        enc.setWuid((String) datosFacturaAlmacen[6]);
        enc.setLogisticsCostingCode((String) datosFacturaAlmacen[3]);
        enc.setRouteCostingCode((String) datosFacturaAlmacen[4]);
        enc.setSalesCostingCode((String) datosFacturaAlmacen[2]);
        enc.setProjectCode((String) datosFacturaAlmacen[5]);

        int lineNum = 0;
        for (ProductoListaRegalosInvitadoDTO producto : productos) {
            SalesDocumentLineDTO line = new SalesDocumentLineDTO();
            line.setItemCode(producto.getReferencia());
            line.setLineNum(lineNum++);
            line.setPrice(itemFacade.getItemPrice(producto.getReferencia()).doubleValue());
            //Consulta saldo en ubicaciones por referencia, ordenado de mayor a menor
            List<Object[]> saldosUbicacion = saldoUbicacionFacade.buscarPorReferencia(producto.getReferencia());
            //Asigna las ubicaciones por cantidad
            int cantidadPendiente = producto.getCantidad();
            log.log(Level.INFO, "Verificando ubicaciones para la referencia [{0}]", producto.getReferencia());
            //Valida que la cantidad total en saldo sea suficiente para cubrir la cantidad solicitada
            int cantidadTotal = 0;
            for (Object[] row : saldosUbicacion) {
                cantidadTotal += (Integer) row[4];
            }
            if (cantidadTotal < cantidadPendiente) {
                log.log(Level.SEVERE, "No hay saldo suficiente de la referencia {0}. Cantidad necesaria: {1}, cantidad disponible: {2}", new Object[]{producto.getReferencia(), producto.getCantidad(), cantidadTotal});
                return new Object[]{-1, "No hay saldo suficiente de la referencia " + producto.getReferencia() + ". Cantidad necesaria: " + producto.getCantidad() + ", cantidad disponible: " + cantidadTotal};
            }
            for (Object[] row : saldosUbicacion) {
                String whsCode = (String) row[0];
                //Integer saldoAlmacen = (Integer) row[1];
                Integer binAbs = (Integer) row[2];
                String binCode = (String) row[3];
                Integer saldoUbicacion = (Integer) row[4];
                if (line.getWhsCode() == null) {
                    line.setWhsCode(whsCode);
                } else if (!line.getWhsCode().equals(whsCode)) {
                    //Si el almacen cambia, significa que el saldo en las ubicaciones del primer almacen no fue suficiente y se debe crear una nueva linea
                    enc.addLine(line);
                    line = new SalesDocumentLineDTO();
                    line.setItemCode(producto.getReferencia());
                    line.setLineNum(lineNum++);
                    line.setPrice(null);
                    line.setWhsCode(whsCode);
                }
                if (line.getQuantity() == null) {
                    line.setQuantity(Math.min(Math.min(saldoUbicacion, cantidadPendiente), producto.getCantidad()));
                } else {
                    line.setQuantity(line.getQuantity() + Math.min(saldoUbicacion, cantidadPendiente));
                }
                SalesDocumentLineBinAllocationDTO bin = new SalesDocumentLineBinAllocationDTO();
                bin.setBinAbsEntry(binAbs);
                bin.setWhsCode(whsCode);
                if (cantidadPendiente <= saldoUbicacion) {
                    log.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] satisface la cantidad pendiente [{2}]",
                            new Object[]{saldoUbicacion, binCode, cantidadPendiente});
                    bin.setQuantity(cantidadPendiente);
                    cantidadPendiente = 0;
                } else {
                    log.log(Level.INFO, "La cantidad [{0}] en la ubicacion [{1}] NO satisface la cantidad pendiente [{2}]. Faltan [{3}]",
                            new Object[]{saldoUbicacion, binCode, cantidadPendiente, cantidadPendiente - saldoUbicacion});
                    bin.setQuantity(saldoUbicacion);
                    cantidadPendiente -= saldoUbicacion;
                }
                line.addBinAllocation(bin);
                if (cantidadPendiente == 0) {
                    break;
                }
            }
            enc.addLine(line);
        }//Finaliza la asignacion de ubicaciones por referencia
        //Crea la factura
        String invoiceDocNum;
        Long docEntryFactura;
        FacturaSAP facturaSAP;
        SesionSAPB1WSDTO sesionSap = applicationBean.obtenerSesionSAP(datosPago.getBuyer().getDocument());
        if (sesionSap == null || sesionSap.getIdSesionSAP() == null) {
            //TODO: enviar correo de notificacion de error
            //sendErrorEmailNotification("crear factura web", "No se pudo obtener una sesion de B1WS luego de esperar 20 segundos. ");
            log.log(Level.SEVERE, "No fue posible configurar una conexion con el servidor de facturacion. Por favor intente de nuevo. ");
            return new Object[]{-1, "No fue posible configurar una conexion con el servidor de facturacion. Por favor intente de nuevo. "};
        }
        InvoicesServiceConnector isc = sapB1WSBean.getInvoicesServiceConnectorInstance(sesionSap.getIdSesionSAP());
        try {
            docEntryFactura = isc.createInvoice(enc);
            //TODO: enviar mensaje para ejecucion asincrona de creacion de documentos adicionales
            //      cuando esto se ejecute, se debe eliminar la creacion de documentos adicionales en la 
            //      operacion de creacion del recibo de caja
            facturaSAP = facturaSAPFacade.findNoTransaction(docEntryFactura.intValue());
            invoiceDocNum = Integer.toString(facturaSAP.getDocNum());
            log.log(Level.INFO, "Se creo con exito la factura {0}", invoiceDocNum);
        } catch (InvoiceServiceException e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear la factura. " + e.getMessage(), e);
            return new Object[]{-1, "Ocurrio un error al crear la factura. " + e.getMessage()};
        } finally {
            applicationBean.finalizarSesionSAP(sesionSap);
        }
        return new Object[]{0, invoiceDocNum + ";" + docEntryFactura};
    }

    @GET
    @Path("estadotransaccion/{codigo}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarEstadoTransaccion(@PathParam("codigo") String codigoTransaccion) {
        return Response.ok(transaccionP2PFacade.consultarPorReferenciaPago(codigoTransaccion)).build();
    }

    @GET
    @Path("notificacion/diaria")
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response ejecutarNotificacionesDiarias() {
        log.log(Level.INFO, "Ejecutando proceso de notificaciones de compra de listas de regalos para el dia anterior");
        List<CompraListaRegalos> comprasAyer = compraListaRegalosFacade.consultarComprasDiaAnterior();
        if (comprasAyer.isEmpty()) {
            log.log(Level.INFO, "Ayer no se registro ninguna compra de lista de regalos");
            return Response.ok().build();
        }
        log.log(Level.INFO, "Se encontraron {0} compras de listas de regalos para el dia anterior", comprasAyer.size());
        HashMap<ListaRegalos, List<CompraListaRegalos>> regalosLista = new HashMap<>();
        for (CompraListaRegalos entidad : comprasAyer) {
            if (regalosLista.containsKey(entidad.getLista())) {
                int pos = regalosLista.get(entidad.getLista()).indexOf(entidad);
                if (pos >= 0) {
                    //Incrementa la cantidad o el valor comprados, dependiendo si la compra fue por producto o bono
                    if (regalosLista.get(entidad.getLista()).get(pos).getProducto() != null) {
                        regalosLista.get(entidad.getLista()).get(pos).setCantidad(regalosLista.get(entidad.getLista()).get(pos).getCantidad() + entidad.getCantidad());
                    } else {
                        regalosLista.get(entidad.getLista()).get(pos).setTotal(regalosLista.get(entidad.getLista()).get(pos).getTotal() + entidad.getTotal());
                    }
                } else {
                    //Agrega la compra al registro
                    regalosLista.get(entidad.getLista()).add(entidad);
                }
            } else {
                List<CompraListaRegalos> compras = new ArrayList<>();
                compras.add(entidad);
                regalosLista.put(entidad.getLista(), compras);
            }
        }
        for (ListaRegalos lista : regalosLista.keySet()) {
            StringBuilder htmlProductos = null;
            //Envia correos, si la lista esta configurada para hacerlo
            if (lista.getNotificacionDiariaCreador().equals("E") || lista.getNotificacionDiariaCreador().equals("T")) {
                //Si la lista tiene configuradas notificaciones por correo para el creador
                htmlProductos = construirProductosNotificacion(regalosLista.get(lista));
                SocioDeNegocios creador = clienteFacade.findByCardCode(lista.getCedulaCreador());
                enviarEmailComprasDiarias(lista.getNombreCreador(), htmlProductos.toString(), creador.getEmail());
            }
            if (lista.getNotificacionDiariaCocreador() != null && !lista.getNotificacionDiariaCocreador().equals("N")) {
                //Si la lista tiene configuradas notificaciones por correo para el cocreador
                if (htmlProductos == null) {
                    htmlProductos = construirProductosNotificacion(regalosLista.get(lista));
                }
                SocioDeNegocios cocreador = clienteFacade.findByCardCode(lista.getCedulaCreador());
                enviarEmailComprasDiarias(lista.getNombreCocreador(), htmlProductos.toString(), cocreador.getEmail());
            }
            //Envia SMS, si la lista esta configurada para hacerlo
        }
        return Response.ok().build();
    }

    private StringBuilder construirProductosNotificacion(List<CompraListaRegalos> compras) {
        StringBuilder sb = new StringBuilder();
        for (CompraListaRegalos compra : compras) {
            if (compra.getProducto() != null) {
                //Si el registro de compra es de producto
                sb.append("<tr><td style=\"width: 5%\"></td>");
                sb.append("<td style=\"width: 35%; border-bottom: 1px solid gray; padding-bottom: 5px; padding-top: 5px\">");
                sb.append("<img style=\"height: 125px\" src=\"https://img.matisses.co/");
                sb.append(compra.getProducto().getReferencia());
                sb.append("/images/");
                sb.append(compra.getProducto().getReferencia());
                sb.append("_01.jpg\"></td><td style=\"text-align: left; border-bottom: 1px solid gray; vertical-align: top; padding-bottom: 5px; padding-top: 5px\">");
                sb.append("<div style=\"font-weight: 100; font-size: 18pt; color: #BD9E1F\">");
                sb.append(StringUtils.capitalize(compra.getProducto().getDescripcionProducto().toLowerCase()));
                sb.append("</div><div><span style=\"font-weight: 600; font-size: 13pt;\">Recibiste:</span> ");
                sb.append(compra.getCantidad());
                sb.append(" unidades</div></td><td style=\"width: 5%\"></td></tr>");
            } else {
                //Si el registro de compra es de bono de regalo
                sb.append("<tr><td style=\"width: 5%\"></td>");
                sb.append("<td style=\"width: 35%; border-bottom: 1px solid gray; padding-bottom: 5px; padding-top: 5px\">");
                sb.append("<img style=\"height: 125px\" src=\"http://listaregalos.matisses.co/images/bono.jpg\">");
                sb.append("</td><td style=\"text-align: left; border-bottom: 1px solid gray; vertical-align: top; padding-bottom: 5px; padding-top: 5px\">");
                sb.append("<div style=\"font-weight: 100; font-size: 18pt; color: #BD9E1F\">Bono de regalo");
                sb.append("</div><div><span style=\"font-weight: 600; font-size: 13pt;\">Recibiste:</span> ");
                sb.append(NumberFormat.getInstance().format(compra.getTotal()));
                sb.append(" en bonos</div></td><td style=\"width: 5%\"></td></tr>");
            }
        }
        log.log(Level.INFO, sb.toString());
        return sb;
    }

    @GET
    @Path("notificacion/semanal")
    public Response ejecutarNotificacionesSemanales() {
        return Response.ok().build();
    }

    private void actualizarTransaccionPlaceToPay(TransaccionPlacetopay entidad, RespuestaPlaceToPayDTO dto) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault());
        entidad.setRequestId(dto.getRequestId().toString());
        try {
            entidad.setResponseDate(sdf.parse(dto.getStatus().getDate()));
        } catch (Exception e) {
            entidad.setResponseDate(new Date());
        }
        entidad.setStatus(dto.getStatus().getStatus());
        entidad.setMessage(dto.getStatus().getMessage());
        entidad.setProcessUrl(dto.getProcessUrl());
        entidad.setReason(dto.getStatus().getReason());
        try {
            transaccionP2PFacade.edit(entidad);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar la transaccion de placetopay con la respuesta recibida. ", e);
        }
    }

    private void actualizarTransaccionFactura(TransaccionPlacetopay entidad, String docNum, Long docEntry) {
        entidad.setDocNumFactura(docNum);
        entidad.setDocEntryFactura(docEntry);
        try {
            transaccionP2PFacade.edit(entidad);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar la transaccion de placetopay con la respuesta de facturacion. ", e);
        }
    }

    private TransaccionPlacetopay crearTransaccionPlaceToPay(String codigoLista, String idSesion, DatosPagoPlaceToPayDTO dto, String tipoCompra, String mensajeComprador) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mmZ", Locale.getDefault());
        TransaccionPlacetopay entidad = new TransaccionPlacetopay();
        entidad.setApellidos(dto.getBuyer().getSurname());
        entidad.setCiudad(dto.getBuyer().getAddress().getCity());
        entidad.setCorreo(dto.getBuyer().getEmail());
        entidad.setDescPago(dto.getPayment().getDescription());
        entidad.setDireccion(dto.getBuyer().getAddress().getStreet());
        try {
            entidad.setExpira(sdf.parse(dto.getExpiration()));
        } catch (Exception e) {
            entidad.setExpira(new Date());
        }
        entidad.setIdSesion(idSesion);
        entidad.setIp(dto.getIpAddress());
        entidad.setLogin(dto.getAuth().getLogin());
        entidad.setMoneda("COP");
        entidad.setNombres(dto.getBuyer().getName());
        entidad.setNonce(dto.getAuth().getNonce());
        entidad.setNroDocumento(dto.getBuyer().getDocument());
        entidad.setPais("CO");
        entidad.setRefPago(dto.getPayment().getReference());
        entidad.setSeed(dto.getAuth().getSeed());
        entidad.setTipoDocumento(dto.getBuyer().getDocumentType());
        entidad.setTipoImpuesto(dto.getPayment().getAmount().getTaxes().getKind());
        entidad.setTotalImpuesto(Integer.parseInt(dto.getPayment().getAmount().getTaxes().getAmount()));
        entidad.setTotalPago(Integer.parseInt(dto.getPayment().getAmount().getTotal()));
        entidad.setTranKey(dto.getAuth().getTranKey());
        entidad.setUrlRetorno(dto.getReturnUrl());
        entidad.setUserAgent(dto.getUserAgent());
        entidad.setCodigoLista(codigoLista);
        entidad.setTipoCompra(tipoCompra);
        entidad.setMensajeComprador(mensajeComprador);
        transaccionP2PFacade.create(entidad);
        return entidad;
    }

    // Parse Content-Disposition header to get the original file name
    private String parseFileName(MultivaluedMap<String, String> headers, String codigoLista, String tipo) {
        String[] contentDispositionHeader = headers.getFirst("Content-Disposition").split(";");
        String fileName = "";
        for (String name : contentDispositionHeader) {
            if ((name.trim().startsWith("filename"))) {
                String[] tmp = name.split("=");
                fileName = tmp[1].trim().replaceAll("\"", "");
                break;
            }
        }
        fileName = codigoLista + (tipo.equals("portada") ? "_P" : "") + fileName.substring(fileName.lastIndexOf("."));
        log.log(Level.INFO, "Nombre del archivo {0}", fileName);
        return fileName;
    }

    // save uploaded file to a defined location on the server
    private void saveFile(InputStream uploadedInputStream, String serverLocation) {
        try {
            OutputStream outpuStream = new FileOutputStream(new File(serverLocation));
            int read;
            byte[] bytes = new byte[1024];
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                outpuStream.write(bytes, 0, read);
            }
            outpuStream.flush();
            outpuStream.close();
        } catch (IOException e) {
            log.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }

    private void enviarEmailComprasDiarias(String nombre, String htmlProductos, String emailDestinatario) {
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom("Lista Regalos Matisses <listaregalos@matisses.co>");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("htmlProductos", htmlProductos);
        mail.setParams(params);
        mail.setSubject("Estos son los regalos que recibiste ayer");
        mail.setTemplateName("lista_regalos_compras_diarias");
        mail.addToAddress(emailDestinatario);
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion de correo. ", e);
        }
    }

    @GET
    @Path("consultareferencia/{idLista}/{ref}")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response consultarItemAutocompletar(@PathParam("idLista") Long idLista, @PathParam("ref") String itemCode) {
        log.log(Level.INFO, "Consultando item (autocompletar) con itemcode={0}", itemCode);
        if (itemCode == null || itemCode.isEmpty()) {
            return Response.ok().build();
        }
        if (itemCode.length() > 20 || itemCode.length() < 5) {
            return Response.ok().build();
        }
        if (itemCode.length() < 20) {
            itemCode = itemCode.replaceAll("\\.", "");
            itemCode = itemCode.replaceAll("\\*", "");
            itemCode = StringUtils.rightPad(itemCode.substring(0, 3), 20 - (itemCode.length() - 3), "0") + itemCode.substring(3);
        }
        try {
            ItemInventario entidad = itemFacade.getItemByItemCodeWithStock(itemCode);
            ProductoListaRegalosDTO dto = new ProductoListaRegalosDTO();
            dto.setDescripcionProducto(entidad.getItemName());
            dto.setReferencia(entidad.getItemCode());

            ProductoListaRegalos productoLista = productoListaFacade.consultarPorListaReferencia(idLista, dto.getReferencia());
            if (productoLista != null) {
                dto.setIdProductoLista(productoLista.getIdProductoLista());
                dto.setCantidadComprada(productoLista.getCantidadComprada());
                dto.setCantidadElegida(productoLista.getCantidadElegida());
                dto.setCantidadEntregada(productoLista.getCantidadEntregada());
                dto.setFavorito(productoLista.getFavorito());
                dto.setIdLista(productoLista.getLista().getIdLista());
            }
            return Response.ok(dto).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el item. ", e);
        }
        return Response.ok().build();
    }
}
