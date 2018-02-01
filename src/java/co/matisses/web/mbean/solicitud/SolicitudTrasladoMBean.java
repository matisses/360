package co.matisses.web.mbean.solicitud;

import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.SolicitudTraslado;
import co.matisses.persistence.web.entity.SolicitudTrasladoDetalle;
import co.matisses.persistence.web.entity.SolicitudTrasladoInforme;
import co.matisses.persistence.web.entity.SolicitudTrasladoUbicacion;
import co.matisses.persistence.web.facade.SolicitudTrasladoDetalleFacade;
import co.matisses.persistence.web.facade.SolicitudTrasladoFacade;
import co.matisses.persistence.web.facade.SolicitudTrasladoInformeFacade;
import co.matisses.persistence.web.facade.SolicitudTrasladoUbicacionFacade;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.dto.SolicitudTrasladoDTO;
import co.matisses.web.dto.SolicitudTrasladoDetalleDTO;
import co.matisses.web.dto.SolicitudTrasladoInformeDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "solicitudTrasladoMBean")
public class SolicitudTrasladoMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger log = Logger.getLogger(SolicitudTrasladoMBean.class.getSimpleName());
    private int paso;
    private int item;
    private Integer cantidad;
    private Integer idDetalle;
    private String referencia;
    private String comentario;
    private boolean dlgItems = false;
    private boolean trabajar = false;
    private boolean imprimir = false;
    private SolicitudTrasladoDTO solicitud;
    private SolicitudTrasladoDetalleDTO detalleDto;
    private List<String[]> imagenes;
    private List<SolicitudTrasladoDetalleDTO> detalle;
    private Map<String, List<SolicitudTrasladoInformeDTO>> informe;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private SolicitudTrasladoFacade solicitudTrasladoFacade;
    @EJB
    private SolicitudTrasladoDetalleFacade solicitudTrasladoDetalleFacade;
    @EJB
    private SolicitudTrasladoInformeFacade solicitudTrasladoInformeFacade;
    @EJB
    private SolicitudTrasladoUbicacionFacade solicitudTrasladoUbicacionFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;

    public SolicitudTrasladoMBean() {
        paso = 1;
        item = 1;
        solicitud = new SolicitudTrasladoDTO();
        detalle = new ArrayList<>();
        imagenes = new ArrayList<>();
        detalle = new ArrayList<>();
        informe = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        if (sessionMBean.getAlmacen().substring(0, 2).equals("01") || sessionMBean.getAlmacen().substring(0, 2).equals("09") || sessionMBean.getAlmacen().substring(0, 2).equals("08")) {
            mostrarMensaje("Error", "Lo sentimos, la sucursal a la que inicio sesión no tiene permisos para realizar este tipo de función.", true, false, false);
            log.log(Level.SEVERE, "Lo sentimos, la sucursal a la que inicio sesion no tiene permisos para realizar este tipo de funcion");
            trabajar = false;
            return;
        }
        obtenerSolicitudPendientes();
        trabajar = true;
    }

    public int getPaso() {
        return paso;
    }

    public void setPaso(int paso) {
        this.paso = paso;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isDlgItems() {
        return dlgItems;
    }

    public void setDlgItems(boolean dlgItems) {
        this.dlgItems = dlgItems;
    }

    public SolicitudTrasladoDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudTrasladoDTO solicitud) {
        this.solicitud = solicitud;
    }

    public SolicitudTrasladoDetalleDTO getDetalleDto() {
        return detalleDto;
    }

    public void setDetalleDto(SolicitudTrasladoDetalleDTO detalleDto) {
        this.detalleDto = detalleDto;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public List<SolicitudTrasladoDetalleDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SolicitudTrasladoDetalleDTO> detalle) {
        this.detalle = detalle;
    }

    public Map<String, List<SolicitudTrasladoInformeDTO>> getInforme() {
        return informe;
    }

    public void setInforme(Map<String, List<SolicitudTrasladoInformeDTO>> informe) {
        this.informe = informe;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public void imprimirSolicitud() {
        if (imprimir) {
            imprimir = false;
        } else {
            imprimir = true;
        }
    }

    private void obtenerSolicitudPendientes() {
        log.log(Level.INFO, "Validando si la sucursal logeada {0} tiene solicitudes pendiente", sessionMBean.getAlmacen());
        List<SolicitudTraslado> requests = solicitudTrasladoFacade.obtenerSolicitudesPendientes(sessionMBean.getAlmacen());

        if (requests != null && !requests.isEmpty()) {
            if (requests.size() > 1) {
                for (int i = 0; i < requests.size() - 1; i++) {
                    SolicitudTraslado sol = requests.get(i);
                    try {
                        /*Se marca la solicitud como NO FINALIZADA*/
                        sol.setEstado("NF");

                        solicitudTrasladoFacade.edit(sol);
                        log.log(Level.INFO, "Se marco la solicitud con id {0} como NO FINALIZADA", sol.getIdSolicitud());
                        requests.remove(i);
                        i--;
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al marcar la solicitud como NO FINALIZADA", e);
                    }
                }

                cargarDatosSolicitudPendiente(requests.get(0));
            } else {
                cargarDatosSolicitudPendiente(requests.get(0));
            }
        } else {
            log.log(Level.INFO, "No se encontraron solicitude pendientes para la sucursal {0}", sessionMBean.getAlmacen());
        }
    }

    private void cargarDatosSolicitudPendiente(SolicitudTraslado request) {
        log.log(Level.INFO, "Cargando solicitud pendiente con id {0}", request.getIdSolicitud());
        detalle = new ArrayList<>();
        solicitud = new SolicitudTrasladoDTO(request.getIdSolicitud(), null, request.getSucursal(), null, null, request.getUsuario(), null,
                request.getEstado(), request.getUsuarioResponsable(), request.getFecha(), null, null);

        /*Obtener el detalle de la solicitud*/
        List<SolicitudTrasladoDetalle> details = solicitudTrasladoDetalleFacade.obtenerDetalleSolicitud(request.getIdSolicitud());

        if (details != null && !details.isEmpty()) {
            for (SolicitudTrasladoDetalle s : details) {
                detalle.add(new SolicitudTrasladoDetalleDTO(s.getIdSolicitudDetalle(), s.getIdSolicitud().getIdSolicitud(), s.getCantidad(), s.getReferencia(), s.getComentario(), s.getUsuario()));
            }
        }
    }

    public void agregarReferencia() {
        if (!trabajar) {
            return;
        }
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "Debe ingrear una referencia.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar una referencia");
            return;
        }
        if (cantidad == null || cantidad == 0) {
            cantidad = 1;
        }
        if (referencia.length() < 20) {
            referencia = baruGenericMBean.completarReferencia(referencia);
        }

        Integer saldos = saldoItemInventarioFacade.obtenerSaldoSolicitud(referencia, sessionMBean.getAlmacen());

        if (saldos != null && saldos > 0) {
            if (saldos < 0 || saldos < cantidad) {
                mostrarMensaje("Error", "No se encontro el saldo necesario en las bodegas.", true, false, false);
                log.log(Level.SEVERE, "No se encontro el saldo necesario en las bodegas");
                return;
            } else {
                /*validar que la cantidad necesaria si se pueda aplicar*/
                boolean existe = false;
                if (detalle != null && !detalle.isEmpty()) {
                    for (int i = 0; i < detalle.size(); i++) {
                        SolicitudTrasladoDetalleDTO s = detalle.get(i);

                        if (s.getReferencia().equals(referencia)) {
                            saldos -= s.getCantidad();
                            if (saldos >= cantidad) {
                                incluirItem(s.getIdSolicitudDetalle(), i);
                            } else {
                                mostrarMensaje("Error", "No se encontro el saldo necesario en las bodegas.", true, false, false);
                                log.log(Level.SEVERE, "No se encontro el saldo necesario en las bodegas");
                                return;
                            }
                            existe = true;
                            break;
                        }
                    }
                }
                if (!existe) {
                    incluirItem(null, null);
                }
                limpiarBasico();
            }
        } else {
            mostrarMensaje("Error", "No se encontro el saldo necesario en las bodegas.", true, false, false);
            log.log(Level.SEVERE, "No se encontro el saldo necesario en las bodegas");
            return;
        }
    }

    private void incluirItem(Integer idDetalle, Integer posicion) {
        if (solicitud == null || solicitud.getIdSolicitud() == null || solicitud.getIdSolicitud() == 0) {
            crearEncabezado();
        }

        if (idDetalle == null || idDetalle == 0) {
            idDetalle = crearDetalle();
            if (idDetalle != null && idDetalle != 0) {
                detalle.add(0, new SolicitudTrasladoDetalleDTO(idDetalle, solicitud.getIdSolicitud(), cantidad, referencia, comentario, sessionMBean.getUsuario()));
            }
        } else {
            SolicitudTrasladoDetalle det = solicitudTrasladoDetalleFacade.find(idDetalle);

            if (det != null && det.getIdSolicitudDetalle() != null && det.getIdSolicitudDetalle() != 0) {
                det.setCantidad(det.getCantidad() + cantidad);

                try {
                    solicitudTrasladoDetalleFacade.edit(det);
                    log.log(Level.INFO, "Se modifico la cantidad del detalle de la solicitud con id {0}", idDetalle);
                    detalle.get(posicion).setCantidad(det.getCantidad());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al modificar la cantidad del detalle de la solicitud. ", e);
                    mostrarMensaje("Error", "No se pudo modificar la cantidad de la referencia " + referencia + ", en el pedido actual.", true, false, false);
                    return;
                }
            }
        }
    }

    private void crearEncabezado() {
        SolicitudTraslado request = new SolicitudTraslado();

        request.setSucursal(sessionMBean.getAlmacen());
        request.setFecha(new Date());
        request.setUsuario(sessionMBean.getUsuario());
        request.setEstado("SP");

        try {
            solicitudTrasladoFacade.create(request);
            log.log(Level.INFO, "Se creo el encabezado de la solicitud id {0} con estado SP", request.getIdSolicitud());
            solicitud = new SolicitudTrasladoDTO(request.getIdSolicitud(), null, request.getSucursal(), null, null,
                    request.getUsuario(), null, request.getEstado(), request.getUsuarioResponsable(), request.getFecha(), null, null);
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrio un error al registrar el pedido.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al crear el encabezado de la solicitud. ", e);
            return;
        }
    }

    private Integer crearDetalle() {
        SolicitudTrasladoDetalle detail = new SolicitudTrasladoDetalle();

        detail.setCantidad(cantidad);
        detail.setReferencia(referencia);
        detail.setIdSolicitud(new SolicitudTraslado(solicitud.getIdSolicitud()));
        detail.setUsuario(sessionMBean.getUsuario());
        detail.setComentario(comentario);

        try {
            solicitudTrasladoDetalleFacade.create(detail);
            log.log(Level.INFO, "Se creo detalle de solicitud con id {0}", detail.getIdSolicitudDetalle());
            return detail.getIdSolicitudDetalle();
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrio un error al registrar la referencia al pedido.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al crear el detalle de la solicitud. ", e);
            return null;
        }
    }

    public void seleccionarItemEliminar() {
        idDetalle = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDetalle"));

        for (SolicitudTrasladoDetalleDTO d : detalle) {
            if (d.getIdSolicitudDetalle().equals(idDetalle)) {
                referencia = d.getReferencia();
                cantidad = d.getCantidad();
                comentario = d.getComentario();

                break;
            }
        }
    }

    public void eliminarItem() {
        if (idDetalle != null && idDetalle != null) {
            for (SolicitudTrasladoDetalleDTO d : detalle) {
                if (d.getIdSolicitudDetalle().equals(idDetalle)) {
                    if (d.getCantidad() > 1) {
                        d.setCantidad(d.getCantidad() - 1);

                        try {
                            SolicitudTrasladoDetalle det = solicitudTrasladoDetalleFacade.find(idDetalle);

                            if (det != null && det.getIdSolicitudDetalle() != null && det.getIdSolicitudDetalle() != 0) {
                                det.setCantidad(det.getCantidad() - 1);

                                solicitudTrasladoDetalleFacade.edit(det);

                                log.log(Level.INFO, "Se modifico el detalle con id {0} del pedido con id {1}", new Object[]{idDetalle, solicitud.getIdSolicitud()});
                            }
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al modicar la cantidad del detalle del pedido. ", e);
                            mostrarMensaje("Error", "No se pudo eliminar la unidad del pedido.", true, false, false);
                            return;
                        }
                    } else {
                        detalle.remove(d);

                        try {
                            SolicitudTrasladoDetalle det = solicitudTrasladoDetalleFacade.find(idDetalle);

                            if (det != null && det.getIdSolicitudDetalle() != null && det.getIdSolicitudDetalle() != 0) {
                                solicitudTrasladoDetalleFacade.remove(det);

                                log.log(Level.INFO, "Se elimino la referencia {0} del pedido con id {1}", new Object[]{referencia, solicitud.getIdSolicitud()});
                            }
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al eliminar la referencia del detalle del pedido. ", e);
                            mostrarMensaje("Error", "No se pudo eliminar la referencia del pedido.", true, false, false);
                            return;
                        }
                    }
                    limpiarBasico();
                    break;
                }
            }
        }
    }

    public void generarInformeSolicitud() {
        informe = new HashMap<>();
        log.log(Level.INFO, "Generando informe para la solicitud con id {0}", solicitud.getIdSolicitud());

        /*Se crea un registro en la lista de informes por almacen*/
        for (SolicitudTrasladoDetalleDTO d : detalle) {
            int cantidadNecesaria = d.getCantidad();
            int cantidadDisponible = 0;
            List<Object[]> saldo = saldoItemInventarioFacade.obtenerSaldosPrioridad(d.getReferencia(), solicitud.getSucursal());

            if (saldo != null && !saldo.isEmpty()) {
                for (Object[] s : saldo) {
                    cantidadDisponible += ((BigDecimal) s[4]).intValue();
                }
            }

            if (cantidadDisponible == 0) {
                mostrarMensaje("Error", "No se encontro saldo de la referencia " + d.getReferencia() + ", para poder realizar el pedido.", true, false, false);
                log.log(Level.SEVERE, "No se encontro saldo de la referencia {0}, para poder realizar el pedido", d.getReferencia());
                return;
            } else if (cantidadDisponible < d.getCantidad()) {
                mostrarMensaje("Error", "No se encontro saldo suficiente de la referencia " + d.getReferencia() + ", para poder incluirla en el pedido.", true, false, false);
                log.log(Level.SEVERE, "No se encontro saldo suficiente de la referencia {0}, para poder incluirla en el pedido", d.getReferencia());
                return;
            }

            for (Object[] s : saldo) {
                Integer cant = ((BigDecimal) s[4]).intValue();
                String almacen = (String) s[1];

                if (informe.containsKey(almacen)) {
                    boolean existe = false;

                    for (SolicitudTrasladoInformeDTO i : informe.get(almacen)) {
                        if (i.getReferencia().equals(d.getReferencia())) {
                            existe = true;
                            i.setCantidad(i.getCantidad() + cant <= cantidadNecesaria ? cant : cantidadNecesaria);

                            break;
                        }
                    }

                    if (!existe) {
                        informe.get(almacen).add(new SolicitudTrasladoInformeDTO(null, d.getIdSolicitudDetalle(), cant <= cantidadNecesaria ? cant : cantidadNecesaria,
                                d.getReferencia(), almacen, d.getComentario(), null));
                    }

                    cantidadNecesaria -= cant <= cantidadNecesaria ? cant : cantidadNecesaria;
                } else {
                    List<SolicitudTrasladoInformeDTO> det = new ArrayList<>();

                    det.add(new SolicitudTrasladoInformeDTO(null, d.getIdSolicitudDetalle(), cant <= cantidadNecesaria ? cant : cantidadNecesaria,
                            d.getReferencia(), almacen, d.getComentario(), null));
                    informe.put(almacen, det);
                    cantidadNecesaria -= cant <= cantidadNecesaria ? cant : cantidadNecesaria;
                }
                if (cantidadNecesaria == 0) {
                    break;
                }
            }
        }
        paso++;
    }

    public void verPedido() {
        paso--;
    }

    public void cancelarSolicitud() {
        log.log(Level.INFO, "Se cancelara el pedido con id {0}", solicitud.getIdSolicitud());
        SolicitudTraslado s = solicitudTrasladoFacade.find(solicitud.getIdSolicitud());

        if (s != null && s.getIdSolicitud() != null && s.getIdSolicitud() != 0) {
            s.setEstado("SC");

            try {
                solicitudTrasladoFacade.edit(s);
                log.log(Level.INFO, "Se cancelo la solicitud con id {0}", solicitud.getIdSolicitud());
                mostrarMensaje("Éxito", "El pedido fue cancelado correctamente.", false, true, false);
                limpiar();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al cancelar el pedido.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al cancelar el pedido", e);
                return;
            }
        }
    }

    public void abrirDlgItems() {
        if (solicitud.getEstado().equals("SP") && !dlgItems) {
            dlgItems = true;
            evaluarItems();
        } else {
            dlgItems = false;
        }
    }

    private void evaluarItems() {
        item = 1;
        obtenerItemPosicion();
    }

    private void obtenerGaleria(String item) {
        log.log(Level.INFO, "Se estan obteniendo el catalogo de imagenes de la referencia {0} para la venta", item);

        /*1. Se obtiene la galeria de imagenes*/
        imagenes = new ArrayList<>();
        int contador = 0;
        List<String> galeria = imagenProductoMBean.obtenerImagenesCatalogo(item);
        if (galeria != null && !galeria.isEmpty()) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.imagen");
            if (url != null) {
                url = String.format(url, item);
            }

            for (String s : galeria) {
                imagenes.add(new String[]{url + s, String.valueOf(contador)});
                contador++;
            }
        }

        /*2. Se obtiene la plantilla de la referencia*/
        imagenes.add(new String[]{imagenProductoMBean.obtenerPlantilla(item), String.valueOf(contador)});
    }

    private void obtenerItemPosicion() {
        detalleDto = new SolicitudTrasladoDetalleDTO();
        if (detalle.get(item - 1).getSaldoUbicaciones() == null || detalle.get(item - 1).getSaldoUbicaciones().isEmpty()) {
            if (ubicacionSAPFacade.validarUbicacionesAlmacen(solicitud.getSucursal())) {
                detalle.get(item - 1).setSaldoUbicaciones(new ArrayList<SaldoUbicacionDTO>());
                detalle.get(item - 1).setCantidadUsada(0);

                List<UbicacionSAP> allocations = ubicacionSAPFacade.obtenerUbicacionesReceptorasAlmacen(solicitud.getSucursal());

                if (allocations != null && !allocations.isEmpty()) {
                    for (UbicacionSAP u : allocations) {
                        detalle.get(item - 1).getSaldoUbicaciones().add(new SaldoUbicacionDTO(u.getAbsEntry(), 0, 0, u.getBinCode().replace(u.getWhsCode(), "")));
                    }
                }
            }
        }
        detalleDto = detalle.get(item - 1);
        obtenerGaleria(detalle.get(item - 1).getReferencia());
    }

    public void obtenerSiguienteItem() {
        item++;
        obtenerItemPosicion();
    }

    public void obtenerAnteriorItem() {
        item--;
        obtenerItemPosicion();
    }

    public void asignarUbicacion() {
        if (detalleDto.getCantidadUsada() < detalleDto.getCantidad()) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < detalleDto.getSaldoUbicaciones().size(); i++) {
                if (detalleDto.getSaldoUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    detalleDto.getSaldoUbicaciones().get(i).setOnHandQtyUsado(detalleDto.getSaldoUbicaciones().get(i).getOnHandQtyUsado() + 1);

                    detalleDto.setCantidadUsada(detalleDto.getCantidadUsada() + 1);

                    log.log(Level.INFO, "Se agrego una unidad de la ubicacion {0} del almacen {1} para la referencia {2}, en el proceso de pedido tienda",
                            new Object[]{ubicacion, solicitud.getSucursal(), detalleDto.getReferencia()});
                }
            }
        } else {
            mostrarMensaje("Error", "No puede asignar más de la cantidad necesaria para el ítem.", true, false, false);
            log.log(Level.SEVERE, "No puede asignar mas de la cantidad necesaria para el item");
            return;
        }
    }

    public void quitarUbicacion() {
        if (detalleDto.getCantidadUsada() > 0) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < detalleDto.getSaldoUbicaciones().size(); i++) {
                if (detalleDto.getSaldoUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    if (detalleDto.getSaldoUbicaciones().get(i).getOnHandQtyUsado() > 0) {
                        detalleDto.getSaldoUbicaciones().get(i).setOnHandQtyUsado(detalleDto.getSaldoUbicaciones().get(i).getOnHandQtyUsado() - 1);
                        detalleDto.setCantidadUsada(detalleDto.getCantidadUsada() - 1);

                        log.log(Level.INFO, "Se quito una unidad de la ubicacion {0} del almacen {1} para la referencia {2}, en el proceso de pedido tienda",
                                new Object[]{ubicacion, solicitud.getSucursal(), detalleDto.getReferencia()});
                    } else {
                        mostrarMensaje("Error", "No puede quitar más de 0.", true, false, false);
                        log.log(Level.SEVERE, "No puede asignar mas de 0");
                        return;
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "No puede quitar más de 0.", true, false, false);
            log.log(Level.SEVERE, "No puede asignar mas de 0");
            return;
        }
    }

    public void solicitar() {
        /*Se manda a eliminar el informe por si ya existe*/
        if (solicitudTrasladoUbicacionFacade.eliminarUbicacionesTraslado(solicitud.getIdSolicitud())) {
            if (solicitudTrasladoInformeFacade.eliminarInformeTraslado(solicitud.getIdSolicitud())) {
                Map<String, List<SolicitudTrasladoInformeDTO>> informeTmp = new HashMap<>(informe);

                for (SolicitudTrasladoDetalleDTO s : detalle) {
                    for (Map.Entry<String, List<SolicitudTrasladoInformeDTO>> m : informeTmp.entrySet()) {
                        for (SolicitudTrasladoInformeDTO t : m.getValue()) {
                            int cant = t.getCantidad();
                            if (t.getReferencia().equals(s.getReferencia())) {
                                SolicitudTrasladoInforme inf = new SolicitudTrasladoInforme();

                                inf.setAlmacen(m.getKey());
                                inf.setCantidad(t.getCantidad());
                                inf.setIdSolicitudDetalle(new SolicitudTrasladoDetalle(s.getIdSolicitudDetalle()));

                                try {
                                    solicitudTrasladoInformeFacade.create(inf);

                                    if (s.getSaldoUbicaciones() != null && !s.getSaldoUbicaciones().isEmpty()) {
                                        StringBuilder sb = new StringBuilder();

                                        for (SaldoUbicacionDTO u : s.getSaldoUbicaciones()) {
                                            SolicitudTrasladoUbicacion ubi = new SolicitudTrasladoUbicacion();

                                            ubi.setAbsEntry(u.getUbicacion());
                                            ubi.setIdSolicitudInforme(inf);

                                            if (u.getOnHandQtyUsado() > 0) {
                                                if (u.getOnHandQtyUsado() >= t.getCantidad()) {
                                                    ubi.setCantidad(t.getCantidad());

                                                    sb.append(u.getUbicacion());
                                                    sb.append(",");
                                                    sb.append(t.getCantidad());
                                                    sb.append(";");

                                                    cant -= t.getCantidad();
                                                    u.setOnHandQtyUsado(u.getOnHandQtyUsado() - t.getCantidad());
                                                } else {
                                                    ubi.setCantidad(u.getOnHandQtyUsado());

                                                    sb.append(u.getUbicacion());
                                                    sb.append(",");
                                                    sb.append(u.getOnHandQtyUsado());
                                                    sb.append(";");

                                                    cant -= u.getOnHandQtyUsado();
                                                    u.setOnHandQtyUsado(0);
                                                }
                                            }

                                            if (ubi.getCantidad() != null && ubi.getCantidad() > 0) {
                                                try {
                                                    solicitudTrasladoUbicacionFacade.create(ubi);
                                                    if (sb.length() > 0) {
                                                        t.setUbicaciones(sb.toString());
                                                    }
                                                } catch (Exception e) {
                                                    log.log(Level.SEVERE, "Ocurrio un error al finalizar el pedido de tienda", e);
                                                    mostrarMensaje("Error", "Ocurrió un error al finaliza el pedido de tienda.", true, false, false);
                                                    return;
                                                }
                                            }

                                            if (cant == 0) {
                                                break;
                                            }
                                        }
                                    }

                                    if (cant == 0) {
                                        break;
                                    }
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Ocurrio un error al finalizar el pedido de tienda", e);
                                    mostrarMensaje("Error", "Ocurrió un error al finaliza el pedido de tienda.", true, false, false);
                                    return;
                                }
                            }
                        }
                    }
                }

                /*Se marca la solicitud como finalizada y se le manda correo a logistica*/
                SolicitudTraslado request = solicitudTrasladoFacade.find(solicitud.getIdSolicitud());

                if (request != null && request.getIdSolicitud() != null && request.getIdSolicitud() != 0) {
                    request.setFechaFinal(new Date());
                    request.setUsuarioFinaliza(sessionMBean.getUsuario());
                    request.setAutor(sessionMBean.getCedulaEmpleado() + "PR");
                    request.setEstado("SS");

                    try {
                        solicitudTrasladoFacade.edit(request);
                        log.log(Level.INFO, "Se finalizo el pedido con id {0}", solicitud.getIdSolicitud());
                        mostrarMensaje("Éxito", "El pedido fue generado correctamente.", false, true, false);
                        /*Imprimir documento Solicitud de Traslado*/
                        if (imprimir) {
                            imprimir(solicitud.getIdSolicitud());
                        }
                        notificarSolicitud(informeTmp);
                        limpiar();
                    } catch (Exception e) {
                        mostrarMensaje("Error", "Ocurrió un error al finalizar el pedido para tiendas.", true, false, false);
                        log.log(Level.SEVERE, "Ocurrio un error al finalizar el pedido para tiendas", e);
                        return;
                    }
                }
            } else {
                mostrarMensaje("Error", "Ocurrió un error al finalizar el pedido de tienda.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al finalizar el pedido de tienda");
                return;
            }
        } else {
            mostrarMensaje("Error", "Ocurrió un error al finalizar el pedido de tienda.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al finalizar el pedido de tienda");
            return;
        }
    }

    public void imprimir(Integer nroDoc) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(null);
        print.setCopias(1);
        print.setDocumento("solicitud");
        print.setId(nroDoc);
        print.setImprimir(imprimir);
        print.setSucursal(sessionMBean.getAlmacen());
        print.setDocumentosRelacionados(null);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{"SOLICITUD", e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
        }
    }

    private boolean notificarSolicitud(Map<String, List<SolicitudTrasladoInformeDTO>> informeTmp) {
        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        Map<String, String> params = new HashMap<>();

        try {
            params.put("almacen", solicitud.getSucursal());

            StringBuilder sb = new StringBuilder();
            sb.append("<table style=\"width: 60%; border-collapse: collapse\">");
            sb.append("<tr style=\"background: #DDDDDD\">");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Imagen</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Referencia</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Cantidad</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Almacen</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Ubicaciones destino</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD; width: 200px\">Comentario</th>");
            sb.append("</tr>");

            for (Map.Entry<String, List<SolicitudTrasladoInformeDTO>> m : informeTmp.entrySet()) {
                for (SolicitudTrasladoInformeDTO t : m.getValue()) {
                    sb.append("<tr>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD\"><img style=\"width: 103px; height: 83px;\" src=\"");
                    sb.append(imagenProductoMBean.obtenerUrlProducto(t.getReferencia(), true));
                    sb.append("\"></img></td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                    sb.append(baruGenericMBean.convertirARefCorta(t.getReferencia()));
                    sb.append("</td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                    sb.append(t.getCantidad());
                    sb.append("</td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                    sb.append(m.getKey());
                    sb.append("</td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                    if (t.getUbicaciones() == null || t.getUbicaciones().isEmpty()) {
                        sb.append("<b>No aplica</b>");
                    } else {
                        for (String s : t.getUbicaciones().split(";")) {
                            String[] s2 = s.split(",");

                            sb.append(ubicacionSAPFacade.find(Integer.parseInt(s2[0])).getBinCode().replace(solicitud.getSucursal(), ""));
                            sb.append(" - ");
                            sb.append("<b>");
                            sb.append(s2[1]);
                            sb.append("</b>");
                            sb.append("<br>");
                        }
                    }
                    sb.append("</td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD; width: 200px\">");
                    sb.append(t.getComentario());
                    sb.append("</td>");
                    sb.append("</tr>");
                }
            }

            sb.append("</table>");
            params.put("tabla", sb.toString());

            client.enviarHtmlEmail("Solicitud mercancía", "Solicitud mercancía", applicationMBean.getDestinatariosPlantillaEmail().get("pedido").getTo().get(0), "notificacion_solicitud", null, params);
            return true;
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al enviar la notificación del pedido.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion del pedido", e);
            return false;
        }
    }

    public void limpiarBasico() {
        idDetalle = null;
        referencia = null;
        cantidad = null;
        comentario = null;
    }

    private void limpiar() {
        paso = 1;
        item = 0;
        cantidad = null;
        referencia = null;
        comentario = null;
        dlgItems = false;
        imprimir = false;
        solicitud = new SolicitudTrasladoDTO();
        detalleDto = new SolicitudTrasladoDetalleDTO();
        imagenes = new ArrayList<>();
        detalle = new ArrayList<>();
        informe = new HashMap<>();
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
}
