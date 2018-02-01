package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.CondicionPago;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.CondicionPagoFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.AutorizacionPago;
import co.matisses.persistence.web.facade.AutorizacionPagoFacade;
import co.matisses.web.dto.AutorizacionPagoDTO;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "aprobacionPagoMBean")
public class AprobacionPagoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(AprobacionPagoMBean.class.getSimpleName());
    private String comentario;
    private AutorizacionPagoDTO autorizacion;
    @EJB
    private AutorizacionPagoFacade autorizacionPagoFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private CondicionPagoFacade condicionPagoFacade;

    public AprobacionPagoMBean() {
        autorizacion = new AutorizacionPagoDTO();
    }

    @PostConstruct
    protected void initialize() {
        if (!cargarValoresPago()) {
            mostrarMensaje("Error", "No se encontraron resultados de acuerdo a lo solicitado.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron resultados de acuerdo a lo solicitado");
            return;
        }
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public AutorizacionPagoDTO getAutorizacion() {
        return autorizacion;
    }

    public void setAutorizacion(AutorizacionPagoDTO autorizacion) {
        this.autorizacion = autorizacion;
    }

    private boolean cargarValoresPago() {
        log.log(Level.INFO, "Verificando parametros para aprobacion de pago");
        Integer idAutorizacionPago = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        if (idAutorizacionPago != null && idAutorizacionPago != 0 && token != null && !token.isEmpty()) {
            AutorizacionPago authorization = autorizacionPagoFacade.obtenerAutorizacion(idAutorizacionPago, token);

            if (authorization != null && authorization.getIdAutorizacionPago() != null && authorization.getIdAutorizacionPago() != 0) {
                if (authorization.getCliente() != null && !authorization.getCliente().isEmpty()) {
                    SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(authorization.getCliente());

                    autorizacion.setCliente(socio.getRazonSocial());
                }
                autorizacion.setCodAsesor(authorization.getCodAsesor());
                autorizacion.setComentario(authorization.getComentario());
                autorizacion.setComentarioAprueba(authorization.getComentarioAprueba());
                autorizacion.setCorreoAprueba(authorization.getCorreoAprueba());
                autorizacion.setEstado(authorization.getEstado());
                autorizacion.setFechaAprobacion(authorization.getFechaAprobacion());
                autorizacion.setFechaSolicita(authorization.getFechaSolicita());
                autorizacion.setIdAutorizacionPago(authorization.getIdAutorizacionPago());
                if (authorization.getIdCondicionPago() != null && authorization.getIdCondicionPago() != 0) {
                    CondicionPago condicion = condicionPagoFacade.find(authorization.getIdCondicionPago());

                    autorizacion.setCondicionPago(condicion.getPymntGroup());
                    autorizacion.setIdCondicionPago(authorization.getIdCondicionPago());
                }
                autorizacion.setIdCotizacion(authorization.getIdCotizacion());
                autorizacion.setRespuesta(authorization.getRespuesta());
                autorizacion.setToken(authorization.getToken());
                autorizacion.setUsuarioAprueba(authorization.getUsuarioAprueba());
                autorizacion.setUsuarioSolicita(authorization.getUsuarioSolicita());
                autorizacion.setValorSolicitado(authorization.getValorSolicitado());

                return true;
            }
        }
        return false;
    }

    public void aprobarPago() {
        AutorizacionPago authorization = autorizacionPagoFacade.find(autorizacion.getIdAutorizacionPago());

        if (authorization != null && authorization.getIdAutorizacionPago() != null && authorization.getIdAutorizacionPago() != 0) {
            authorization.setFechaAprobacion(new Date());
            authorization.setRespuesta(true);
            authorization.setEstado("AA");

            try {
                autorizacionPagoFacade.edit(authorization);
                log.log(Level.INFO, "Se aprobo un pago pendiente para el usuario {0}, aprueba {1} - {2} - {3}", new Object[]{autorizacion.getUsuarioSolicita(),
                    autorizacion.getUsuarioAprueba(), autorizacion.getCorreoAprueba(), autorizacion.getToken()});
                autorizacion.setRespuesta(true);
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrio un error al aprobar el pago.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de aprobacion en la tabla de Autorizacion_Pago. ", e);
            }
        }
    }

    public void rechazarPago() {
        if (comentario == null || comentario.isEmpty()) {
            mostrarMensaje("Error", "Debe agregar un comentario.", true, false, false);
            log.log(Level.SEVERE, "Debe agregar un comentario");
            return;
        }

        AutorizacionPago authorization = autorizacionPagoFacade.find(autorizacion.getIdAutorizacionPago());

        if (authorization != null && authorization.getIdAutorizacionPago() != null && authorization.getIdAutorizacionPago() != 0) {
            authorization.setFechaAprobacion(new Date());
            authorization.setRespuesta(false);
            authorization.setComentarioAprueba(comentario);
            authorization.setEstado("AC");

            try {
                autorizacionPagoFacade.edit(authorization);
                log.log(Level.INFO, "Se rechazo un pago pendiente para el usuario {0}, aprueba {1} - {2} - {3}", new Object[]{autorizacion.getUsuarioSolicita(),
                    autorizacion.getUsuarioAprueba(), autorizacion.getCorreoAprueba(), autorizacion.getToken()});
                autorizacion.setRespuesta(false);
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrio un error al rechazar el pago.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al actualizar los datos de aprobacion en la tabla de Autorizacion_Pago. ", e);
            }
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
