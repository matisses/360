package co.matisses.web.mbean.sistema.pos;

import co.matisses.persistence.web.entity.BwsTerminalPOS;
import co.matisses.persistence.web.facade.BwsTerminalPOSFacade;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
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
@Named(value = "registrarIpMBean")
public class RegistrarIpMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(RegistrarIpMBean.class.getSimpleName());
    private Integer octeto1;
    private Integer octeto2;
    private Integer octeto3;
    private Integer octeto4;
    private String cuentaEfectivo;
    private String alias;
    private boolean activo = false;
    private boolean manual = false;
    private boolean automatico = true;
    @EJB
    private BwsTerminalPOSFacade bwsTerminalPOSFacade;

    public RegistrarIpMBean() {
    }

    @PostConstruct
    protected void initialize() {
        consultarIp();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public Integer getOcteto1() {
        return octeto1;
    }

    public void setOcteto1(Integer octeto1) {
        this.octeto1 = octeto1;
    }

    public Integer getOcteto2() {
        return octeto2;
    }

    public void setOcteto2(Integer octeto2) {
        this.octeto2 = octeto2;
    }

    public Integer getOcteto3() {
        return octeto3;
    }

    public void setOcteto3(Integer octeto3) {
        this.octeto3 = octeto3;
    }

    public Integer getOcteto4() {
        return octeto4;
    }

    public void setOcteto4(Integer octeto4) {
        this.octeto4 = octeto4;
    }

    public String getCuentaEfectivo() {
        return cuentaEfectivo;
    }

    public void setCuentaEfectivo(String cuentaEfectivo) {
        this.cuentaEfectivo = cuentaEfectivo;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public boolean isAutomatico() {
        return automatico;
    }

    public void setAutomatico(boolean automatico) {
        this.automatico = automatico;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void seleccionarModoManual() {
        activo = false;
        cuentaEfectivo = null;
        if (manual) {
            manual = false;
            automatico = true;
        } else {
            manual = true;
            automatico = false;
            consultarIp();
        }
    }

    public void seleccionarModoAutomatico() {
        activo = false;
        cuentaEfectivo = null;
        if (automatico) {
            manual = true;
            automatico = false;
        } else {
            manual = false;
            automatico = true;
            consultarIp();
        }
    }

    public void consultarIp() {
        BwsTerminalPOS terminal = new BwsTerminalPOS();
        if (automatico) {
            terminal = bwsTerminalPOSFacade.find(sessionMBean.getIp());
        } else if (manual) {
            terminal = bwsTerminalPOSFacade.find(octeto1 + "." + octeto2 + "." + octeto3 + "." + octeto4);
        }

        if (terminal != null && terminal.getIp() != null && !terminal.getIp().isEmpty()) {
            activo = terminal.getActiva();
            cuentaEfectivo = terminal.getCuentaEfectivo();
            alias = terminal.getAlias();
        } else {
            activo = false;
            cuentaEfectivo = null;
            alias = null;
        }
    }

    public void seleccionarActivo() {
        if (activo) {
            activo = false;
        } else {
            activo = true;
        }
    }

    public void agregarIpPos() {
        String ip = "";
        if (manual) {
            if (octeto1 == null || octeto1 == 0) {
                mostrarMensaje("Error", "Ingrese el rango en el octeto 1", true, false, false);
                log.log(Level.SEVERE, "Ingrese el rango en el octeto 1");
                return;
            } else if (octeto1 > 255) {
                mostrarMensaje("Error", "El rango del octeto 1 no puede superar el número 255", true, false, false);
                log.log(Level.SEVERE, "El rango del octeto 1 no puede superar el numero 255");
                return;
            }
            if (octeto2 == null || octeto2 == 0) {
                mostrarMensaje("Error", "Ingrese el rango en el octeto 2", true, false, false);
                log.log(Level.SEVERE, "Ingrese el rango en el octeto 2");
                return;
            } else if (octeto2 > 255) {
                mostrarMensaje("Error", "El rango del octeto 2 no puede superar el número 255", true, false, false);
                log.log(Level.SEVERE, "El rango del octeto 2 no puede superar el numero 255");
                return;
            }
            if (octeto3 == null || octeto3 == 0) {
                mostrarMensaje("Error", "Ingrese el rango en el octeto 3", true, false, false);
                log.log(Level.SEVERE, "Ingrese el rango en el octeto 3");
                return;
            } else if (octeto3 > 255) {
                mostrarMensaje("Error", "El rango del octeto 3 no puede superar el número 255", true, false, false);
                log.log(Level.SEVERE, "El rango del octeto 3 no puede superar el numero 255");
                return;
            }
            if (octeto4 == null || octeto4 == 0) {
                mostrarMensaje("Error", "Ingrese el rango en el octeto 4", true, false, false);
                log.log(Level.SEVERE, "Ingrese el rango en el octeto 4");
                return;
            } else if (octeto4 > 255) {
                mostrarMensaje("Error", "El rango del octeto 4 no puede superar el número 255", true, false, false);
                log.log(Level.SEVERE, "El rango del octeto 4 no puede superar el numero 255");
                return;
            }

            ip = octeto1 + "." + octeto2 + "." + octeto3 + "." + octeto4;
        } else if (automatico) {
            ip = sessionMBean.getIp();
        }

        if (!ip.isEmpty()) {
            if (cuentaEfectivo == null || cuentaEfectivo.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar una cuenta efectivo, para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar una cuenta efectivo, para poder continuar");
                return;
            }
            if (alias == null || alias.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar un alias, para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar un alias, para poder continuar");
                return;
            }

            BwsTerminalPOS terminal = bwsTerminalPOSFacade.find(ip);

            if (terminal == null || terminal.getIp() == null || terminal.getIp().isEmpty()) {
                terminal = new BwsTerminalPOS();
            }

            terminal.setActiva(activo);
            terminal.setIp(ip);
            terminal.setCuentaEfectivo(cuentaEfectivo);
            terminal.setAlias(alias);

            try {
                if (terminal == null || terminal.getIp() == null || terminal.getIp().isEmpty()) {
                    bwsTerminalPOSFacade.create(terminal);
                    log.log(Level.INFO, "Se registro nuevo equipo con ip [{0}], para terminal POS", ip);
                    mostrarMensaje("Éxito", "La ip se registro correctamente", false, true, false);
                } else {
                    bwsTerminalPOSFacade.edit(terminal);
                    log.log(Level.INFO, "Se modifico registro de equipo con ip [{0}], para terminal POS", ip);
                    mostrarMensaje("Éxito", "La ip se modifico correctamente", false, true, false);
                }
                limpiar();
            } catch (Exception e) {
                mostrarMensaje("Error", "La ip no fue posible registrarla", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al registrar la ip [{0}] para POS. Error [{1}]", new Object[]{ip, e.getMessage()});
                return;
            }
        } else {
            mostrarMensaje("Error", "La ip no fue posible registrarla", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al registrar la ip [{0}] para POS.", ip);
            return;
        }
    }

    private void limpiar() {
        octeto1 = null;
        octeto2 = null;
        octeto3 = null;
        octeto4 = null;
        cuentaEfectivo = null;
        alias = null;
        activo = false;
        manual = false;
        automatico = true;
        consultarIp();
        actualizarIpsBaruApplication();
    }

    public void actualizarIpsBaruApplication() {
        applicationMBean.recargarTerminalesPOSAutorizadas();
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
