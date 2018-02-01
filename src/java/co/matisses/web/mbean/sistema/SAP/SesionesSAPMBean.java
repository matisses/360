package co.matisses.web.mbean.sistema.SAP;

import co.matisses.persistence.web.entity.BwsSesionSAP;
import co.matisses.persistence.web.facade.BwsSesionSAPFacade;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "sesionesSAPMBean")
public class SesionesSAPMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(SesionesSAPMBean.class.getSimpleName());
    private Integer idSesionSAP;
    private List<SesionSAPB1WSDTO> sesiones;
    @EJB
    private BwsSesionSAPFacade sesionSAPFacade;

    public SesionesSAPMBean() {
        sesiones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerSesionesSAPActivas();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public void actualizarIpsBaruApplication() {
        applicationMBean.recargarTerminalesPOSAutorizadas();
    }

    public Integer getIdSesionSAP() {
        return idSesionSAP;
    }

    public void setIdSesionSAP(Integer idSesionSAP) {
        this.idSesionSAP = idSesionSAP;
    }

    public List<SesionSAPB1WSDTO> getSesiones() {
        return sesiones;
    }

    public void setSesiones(List<SesionSAPB1WSDTO> sesiones) {
        this.sesiones = sesiones;
    }

    public void obtenerSesionesSAPActivas() {
        sesiones = new ArrayList<>();

        List<BwsSesionSAP> sesions = null;
        if (applicationMBean.usuarioPuede(sessionMBean.getUsuario(), "ELIMINAR", "SESIONES_SAP")) {
            sesions = sesionSAPFacade.obtenerSesionesSAPActuales();
        } else {
            BwsSesionSAP bwsSesionSAP = sesionSAPFacade.ObtenerSesionActiva(sessionMBean.getUsuario());

            if (bwsSesionSAP != null) {
                sesions = new ArrayList<>();

                sesions.add(bwsSesionSAP);
            }
        }

        if (sesions != null && !sesions.isEmpty()) {
            for (BwsSesionSAP b : sesions) {
                SesionSAPB1WSDTO sesion = new SesionSAPB1WSDTO();

                sesion.setEstado(b.getEstado() != null && b.getEstado().equals("I") ? "INACTIVA" : "ACTIVA");
                sesion.setFecha(b.getFecha());
                sesion.setId(b.getId());
                sesion.setIdSesionSAP(b.getIdSesionSAP());
                sesion.setUsuario(b.getUsuario().toUpperCase());

                sesiones.add(sesion);
            }
        }
    }

    public void seleccionarSesion() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        if (id.equals(idSesionSAP)) {
            idSesionSAP = null;
        } else {
            idSesionSAP = id;
            log.log(Level.INFO, "se selecciono la sesion SAP con id {0}", idSesionSAP);
        }
    }

    public void eliminarSesionSeleccionada() {
        BwsSesionSAP sesion = sesionSAPFacade.find(idSesionSAP);

        if (sesion != null && sesion.getId() != null && sesion.getId() != 0) {
            try {
                sesionSAPFacade.remove(sesion);
                log.log(Level.INFO, "Se elimino la sesion con id {0}", idSesionSAP);
                idSesionSAP = null;
                obtenerSesionesSAPActivas();
                applicationMBean.limpiarSesionesSAP();
                mostrarMensaje("Éxito", "Se elimino la sesión correctamente.", false, true, false);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar la sesion con id {0}. Error {1}", new Object[]{idSesionSAP, e.getMessage()});
                mostrarMensaje("Error", "Ocurrio un error al eliminar la sesión seleccionada.", true, false, false);
                return;
            }
        }
    }

    public void eliminarSesiones() {
        try {
            if (applicationMBean.usuarioPuede(sessionMBean.getUsuario(), "ELIMINAR", "SESIONES_SAP")) {
                sesionSAPFacade.eliminarSesiones();
            } else {
                idSesionSAP = sesiones.get(0).getId();

                eliminarSesionSeleccionada();
                return;
            }

            applicationMBean.limpiarSesionesSAP();
            log.log(Level.INFO, "Se eliminaron las sesiones");
            mostrarMensaje("Éxito", "Se eliminaron la sesiones correctamente.", false, true, false);
            obtenerSesionesSAPActivas();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al eliminar las sesiones actuales", e);
            mostrarMensaje("Error", "Ocurrio un error al eliminar las sesiones actuales.", true, false, false);
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
