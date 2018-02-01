package co.matisses.web.mbean.sistema.pos;

import co.matisses.persistence.web.entity.OperacionCaja;
import co.matisses.persistence.web.facade.OperacionCajaFacade;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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
@Named(value = "administrarCajaMBean")
public class AdministrarCajaMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    private static final Logger log = Logger.getLogger(AdministrarCajaMBean.class.getSimpleName());
    private Integer estado;
    private String ip;
    private List<String[]> cajas;
    @EJB
    private OperacionCajaFacade operacionCajaFacade;

    public AdministrarCajaMBean() {
        cajas = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerEstadoCajas();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public List<String[]> getCajas() {
        return cajas;
    }

    public void setCajas(List<String[]> cajas) {
        this.cajas = cajas;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void obtenerEstadoCajas() {
        cajas.clear();

        cajas = operacionCajaFacade.obtenerTerinales();

        validarFecha();
    }

    private void validarFecha() {
        String fechaActual = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        for (String[] s : cajas) {
            if (!s[3].equals(fechaActual) && s[0].equals("1")) {
                s[0] = "2";
            }
        }
    }

    public void seleccionarCaja() {
        String tmpIp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ip");
        Integer tmpEstado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado"));

        if (ip != null && ip.equals(tmpIp)) {
            ip = null;
            estado = null;
        } else {
            ip = tmpIp;
            estado = tmpEstado;
        }
    }

    public void ejecutarCierreForzoso() {
        if (ip != null && !ip.isEmpty() && estado != 0) {
            OperacionCaja operacion = new OperacionCaja();

            operacion.setFecha(new Date());
            operacion.setJustificacion("Cierre forzoso");
            //operacion.setTerminal(ip);
            operacion.setTipo("cierre");
            //operacion.setUsuario("ygil");
            operacion.setValor(0);

            try {
                operacionCajaFacade.create(operacion);
                log.log(Level.INFO, "Se genero cierre forzoso de la ip [{0}], por el usuario [{1}]", new Object[]{ip, sessionMBean.getUsuario()});
                obtenerEstadoCajas();
            } catch (Exception e) {
                mostrarMensaje("Error", "No fue posible cerrar forzosamente la ip " + ip, true, false, false);
                log.log(Level.SEVERE, "No fue posible cerrar forzosamente la ip [{0}], error [{1}]", new Object[]{ip, e.getMessage()});
                return;
            }
        } else if (estado == 0) {
            mostrarMensaje("Error", "No se puede cerrar una caja ya cerrada", true, false, false);
            log.log(Level.SEVERE, "No se puede cerrar una caja ya cerrada");
            return;
        }
    }

    private void limpiar() {
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
