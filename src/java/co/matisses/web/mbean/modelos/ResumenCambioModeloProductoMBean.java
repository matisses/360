package co.matisses.web.mbean.modelos;

import co.matisses.persistence.sap.entity.Entrada;
import co.matisses.persistence.sap.entity.Salida;
import co.matisses.persistence.sap.facade.EntradaFacade;
import co.matisses.persistence.sap.facade.SalidaFacade;
import co.matisses.persistence.web.entity.CambioModelo;
import co.matisses.persistence.web.entity.DetalleCambioModelo;
import co.matisses.persistence.web.facade.CambioModeloFacade;
import co.matisses.persistence.web.facade.DetalleCambioModeloFacade;
import co.matisses.web.dto.CambioModeloDTO;
import co.matisses.web.dto.DetalleCambioModeloDTO;
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
@Named(value = "resumenCambioModeloProductoMBean")
public class ResumenCambioModeloProductoMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    private static final Logger log = Logger.getLogger(ResumenCambioModeloProductoMBean.class.getSimpleName());
    private CambioModeloDTO cambioModelo;
    @EJB
    private CambioModeloFacade cambioModeloFacade;
    @EJB
    private DetalleCambioModeloFacade detalleCambioModeloFacade;
    @EJB
    private EntradaFacade entradaFacade;
    @EJB
    private SalidaFacade salidaFacade;

    public ResumenCambioModeloProductoMBean() {
        cambioModelo = new CambioModeloDTO();
    }

    @PostConstruct
    protected void initialize() {
        if (!cargarValores()) {
            mostrarMensaje("Error", "No se encontraron datos disponibles.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos acordes al id y token suministrados");
            return;
        }
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public UserSessionInfoMBean getSessionInfoMBean() {
        return sessionInfoMBean;
    }

    public void setSessionInfoMBean(UserSessionInfoMBean sessionInfoMBean) {
        this.sessionInfoMBean = sessionInfoMBean;
    }

    public CambioModeloDTO getCambioModelo() {
        return cambioModelo;
    }

    public void setCambioModelo(CambioModeloDTO cambioModelo) {
        this.cambioModelo = cambioModelo;
    }

    private boolean cargarValores() {
        log.log(Level.INFO, "Verificando parametros para el resumen del cambio de modelo");
        Integer idCambioModelo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCambio"));
        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        CambioModelo cambio = cambioModeloFacade.obtenerCambioModeloToken(idCambioModelo, token);

        if (cambio != null && cambio.getIdCambioModelo() != null && cambio.getIdCambioModelo() != 0) {
            cambioModelo = new CambioModeloDTO(cambio.getIdCambioModelo(), obtenerDocNumSalida(cambio.getSalida()), obtenerDocNumEntrada(cambio.getEntrada()), cambio.getModeloAnterior(),
                    cambio.getModeloNuevo(), cambio.getUsuario(), cambio.getToken(), cambio.getCombinaciones(), cambio.getFecha(), obtenerDetalleCambioModelo(idCambioModelo));

            return true;
        }
        return false;
    }

    private Integer obtenerDocNumSalida(Integer docEntry) {
        if (docEntry != null && docEntry != 0) {
            Salida e = salidaFacade.find(docEntry);

            if (e != null && e.getDocEntry() != null && e.getDocEntry() != 0) {
                return e.getDocNum();
            }
        }
        return null;
    }

    private Integer obtenerDocNumEntrada(Integer docEntry) {
        if (docEntry != null && docEntry != 0) {
            Entrada e = entradaFacade.find(docEntry);

            if (e != null && e.getDocEntry() != null && e.getDocEntry() != 0) {
                return e.getDocNum();
            }
        }
        return null;
    }

    private List<DetalleCambioModeloDTO> obtenerDetalleCambioModelo(Integer idCambioModelo) {
        if (idCambioModelo != null && idCambioModelo != null) {
            List<DetalleCambioModeloDTO> dto = new ArrayList<>();
            List<DetalleCambioModelo> detalle = detalleCambioModeloFacade.obtenerDetalleCambioModelo(idCambioModelo);

            if (detalle != null && !detalle.isEmpty()) {
                for (DetalleCambioModelo d : detalle) {
                    dto.add(new DetalleCambioModeloDTO(d.getIdDetalleCambioModelo(), d.getIdCambioModelo().getIdCambioModelo(),
                            d.getReferenciaAnterior(), d.getReferenciaNueva(), d.getFotosHercules(), d.getFotosSAP(), d.getMateriales(), d.getColores()));
                }

                return dto;
            }
        }
        return null;
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
