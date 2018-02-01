package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.ContenedorProforma;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.entity.NotificacionProforma;
import co.matisses.persistence.web.entity.ProformaInvoice;
import co.matisses.persistence.web.facade.ContenedorProformaFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.DetalleProformaFacade;
import co.matisses.persistence.web.facade.NotificacionProformaFacade;
import co.matisses.persistence.web.facade.ProformaInvoiceFacade;
import co.matisses.web.dto.DatosProveedorDTO;
import co.matisses.web.dto.DetalleProformaAutorizacionDTO;
import co.matisses.web.dto.NotificacionProformaDTO;
import co.matisses.web.dto.ProformaInvoiceDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.session.ProformaSessionMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
@Named(value = "aprobacionProformaMBean")
public class AprobacionProformaMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private ProformaSessionMBean proformaSessionMBean;
    private static final Logger log = Logger.getLogger(AprobacionProformaMBean.class.getSimpleName());
    private Double cbmDisponible = 0.0;
    private String comentario;
    private boolean mostrarTerminos = false;
    private ProformaInvoiceDTO proforma;
    private DatosProveedorDTO proveedor;
    private NotificacionProforma notificacion;
    private List<NotificacionProformaDTO> notificaciones;
    private List<DetalleProformaAutorizacionDTO> detalle;
    @EJB
    private NotificacionProformaFacade notificacionProformaFacade;
    @EJB
    private ProformaInvoiceFacade proformaInvoiceFacade;
    @EJB
    private DatosProveedorFacade datosProveedorFacade;
    @EJB
    private DetalleProformaFacade detalleProformaFacade;
    @EJB
    private ContenedorProformaFacade contenedorProformaFacade;

    public AprobacionProformaMBean() {
        notificacion = new NotificacionProforma();
        notificaciones = new ArrayList<>();
        detalle = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        proveedor = new DatosProveedorDTO();
        notificaciones = new ArrayList<>();
        if (!cargarValoresProforma()) {
            proforma = null;
            notificacion = null;
            mostrarMensaje("Error", "No se encontraron resultados de acuerdo a lo solicitado.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron resultados de acuerdo a lo solicitado");
            return;
        }
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public Double getCbmDisponible() {
        return cbmDisponible;
    }

    public void setCbmDisponible(Double cbmDisponible) {
        this.cbmDisponible = cbmDisponible;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isMostrarTerminos() {
        return mostrarTerminos;
    }

    public void setMostrarTerminos(boolean mostrarTerminos) {
        this.mostrarTerminos = mostrarTerminos;
    }

    public ProformaInvoiceDTO getProforma() {
        return proforma;
    }

    public void setProforma(ProformaInvoiceDTO proforma) {
        this.proforma = proforma;
    }

    public DatosProveedorDTO getProveedor() {
        return proveedor;
    }

    public void setProveedor(DatosProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }

    public NotificacionProforma getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(NotificacionProforma notificacion) {
        this.notificacion = notificacion;
    }

    public List<NotificacionProformaDTO> getNotificaciones() {
        return notificaciones;
    }

    public void setNotificaciones(List<NotificacionProformaDTO> notificaciones) {
        this.notificaciones = notificaciones;
    }

    public List<DetalleProformaAutorizacionDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleProformaAutorizacionDTO> detalle) {
        this.detalle = detalle;
    }

    public String getAbrirTablaProforma() {
        return applicationMBean.obtenerValorPropiedad("url.web.ruta.tablaProformaAutorizacion");
    }

    private boolean cargarValoresProforma() {
        log.log(Level.INFO, "Verificando parametros para aprobacion de la proforma");
        String idProformaTmp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("proforma");
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        if (idProformaTmp != null && !idProformaTmp.isEmpty() && token != null && !token.isEmpty()) {
            Integer idProforma = Integer.parseInt(idProformaTmp);
            notificacion = notificacionProformaFacade.obtenerNotificacionSolicitada(idProforma, token);

            if (notificacion != null && notificacion.getIdNotificacionProforma() != null && notificacion.getIdNotificacionProforma() != 0) {
                ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

                if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
                    proforma = new ProformaInvoiceDTO();

                    proforma.setAnio(pi.getAnio());
                    proforma.setCbmTotal(pi.getCbmTotal());
                    proforma.setCodProveedor(pi.getCodProveedor());
                    proforma.setConsecutivo(pi.getConsecutivo());
                    proforma.setIdProforma(pi.getIdProforma());
                    proforma.setValorTotal(pi.getValorTotal());
                    proforma.setValorTotalDescuento(pi.getValorTotalDescuento());
                    proforma.setTerminosPago(pi.getTerminosPago());
                    proforma.setTerminosEntrega(pi.getTerminosEntrega());

                    DatosProveedor datosProveedor = datosProveedorFacade.consultarProveedor(proforma.getCodProveedor());

                    if (datosProveedor != null && datosProveedor.getCodProveedor() != null && !datosProveedor.getCodProveedor().isEmpty()) {
                        proveedor.setCodProveedor(datosProveedor.getCodProveedor());
                        proveedor.setNombreSocioNegocios(datosProveedor.getNombreSocioNegocios());
                        proveedor.setRazonSocial(datosProveedor.getRazonSocial());
                    }

                    List<NotificacionProforma> notifications = notificacionProformaFacade.obtenerNotificacionesRelacionadas(idProforma, token);

                    if (notifications != null && !notifications.isEmpty()) {
                        for (NotificacionProforma n : notifications) {
                            notificaciones.add(new NotificacionProformaDTO(n.getIdNotificacionProforma(), n.getIdProforma().getIdProforma(), n.getNombreUsuario(), null, null, null, n.getRespuesta(), null));
                        }
                    }
                    obtenerCBMDisponible();
                    proformaSessionMBean.setIdProforma(idProforma);
                    return true;
                }
            }
        }
        return false;
    }

    private void obtenerCBMDisponible() {
        List<ContenedorProforma> contenedores = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

        if (contenedores != null && !contenedores.isEmpty()) {
            Double tmpCont = 0.0;

            for (ContenedorProforma c : contenedores) {
                tmpCont += c.getIdContenedorProveedor().getIdContenedor().getCBMMaximo();
            }

            cbmDisponible = tmpCont - proforma.getCbmTotal();
        }
    }

    public void mostrarTerminos() {
        mostrarTerminos = true;
    }

    public void obtenerDetalleProforma() {
        detalle = new ArrayList<>();
        Integer lineas = detalleProformaFacade.maxRegistros(proforma.getIdProforma());

        if (lineas > 0) {
            for (int i = 0; i < lineas; i++) {
                final Object[] details = detalleProformaFacade.obtenerDetalleAutorizacion(proforma.getIdProforma(), i);

                if (details != null && details.length > 0) {
                    DetalleProformaAutorizacionDTO dto = new DetalleProformaAutorizacionDTO();

                    dto.setCantidad(((BigDecimal) details[0]).doubleValue());
                    dto.setFoto(obtenerFotos((String) details[1]));
                    dto.setReferencia((String) details[1]);
                    dto.setValorUnitario(((BigDecimal) details[3]).doubleValue());
                    dto.setValorTotal(((BigDecimal) details[4]).doubleValue());
                    dto.setDescripcion((String) details[2]);

                    detalle.add(dto);
                }
            }
        }
    }

    private String obtenerFotos(final String referencia) {
        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma") + proforma.getCodProveedor() + "-"
                + proforma.getAnio() + "-" + proforma.getConsecutivo());

        if (file.exists()) {
            File[] arrArchivos = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (!pathname.isHidden() && pathname.getName().contains(referencia)) {
                        return true;
                    } else {
                        return false;
                    }
                }
            });
            if (arrArchivos.length > 0) {
                File s = arrArchivos[0];
                try {
                    return verFoto(s.getName());
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            } else {
                return applicationMBean.obtenerValorPropiedad("url.web.noimage");
            }
        }
        return applicationMBean.obtenerValorPropiedad("url.web.noimage");
    }

    private String verFoto(String img) {
        if (img != null && !img.isEmpty()) {
            try {
                return applicationMBean.obtenerValorPropiedad("url.web.proforma") + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo() + "/" + img;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return applicationMBean.obtenerValorPropiedad("url.web.noimage");
    }

    public void aprobarProforma() {
        notificacion.setFechaRespuesta(new Date());
        notificacion.setRespuesta(true);

        try {
            notificacionProformaFacade.edit(notificacion);
            log.log(Level.SEVERE, "Se marco como aprobada la proforma con id {0}, por el usuario {1}", new Object[]{proforma.getIdProforma(), notificacion.getNombreUsuario()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al marcar la notificacion con id {0} como aprobada. Error {1}", new Object[]{notificacion.getIdNotificacionProforma(), e.getMessage()});
            mostrarMensaje("Error", "Su solicitud de aprobación no se pudo realizar", true, false, false);
            return;
        }
    }

    public void rechazarProforma() {
        notificacion.setComentario(comentario);
        notificacion.setFechaRespuesta(new Date());
        notificacion.setRespuesta(false);

        try {
            notificacionProformaFacade.edit(notificacion);
            log.log(Level.SEVERE, "Se marco como no aprobada la proforma con id {0}, por el usuario {1}", new Object[]{proforma.getIdProforma(), notificacion.getNombreUsuario()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al marcar la notificacion con id {0} como rechazada. Error {1}", new Object[]{notificacion.getIdNotificacionProforma(), e.getMessage()});
            mostrarMensaje("Error", "Su solicitud de no aprobación no se pudo realizar", true, false, false);
            return;
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
}
