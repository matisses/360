package co.matisses.web.mbean.sms;

import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.web.bcs.client.SMSServiceClient;
import co.matisses.web.dto.MensajeTextoDTO;
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
@Named(value = "enviarSMSMBean")
public class EnviarSMSMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(EnviarSMSMBean.class.getSimpleName());
    private final Integer longitudMensaje = 150;
    private String mensaje;
    private String numeroCelular;
    private boolean dlgCelulares = false;
    private List<String> mensajes;
    private List<String> celulares;
    @EJB
    private DireccionSocioDeNegociosFacade direccionSocioDeNegociosFacade;

    public EnviarSMSMBean() {
        mensajes = new ArrayList<>();
        celulares = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public UserSessionInfoMBean getUserSessionInfoMBean() {
        return userSessionInfoMBean;
    }

    public void setUserSessionInfoMBean(UserSessionInfoMBean userSessionInfoMBean) {
        this.userSessionInfoMBean = userSessionInfoMBean;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public Integer getLongitudMensaje() {
        return longitudMensaje;
    }

    public Integer getLongitudMensajeDisponible() {
        return longitudMensaje - (mensajes != null && !mensajes.isEmpty() ? mensajes.get(mensajes.size() - 1).length() : 0);
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public boolean isDlgCelulares() {
        return dlgCelulares;
    }

    public void setDlgCelulares(boolean dlgCelulares) {
        this.dlgCelulares = dlgCelulares;
    }

    public List<String> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String> mensajes) {
        this.mensajes = mensajes;
    }

    public List<String> getCelulares() {
        return celulares;
    }

    public void setCelulares(List<String> celulares) {
        this.celulares = celulares;
    }

    public void obtenerNumerosCelularCliente() {
        celulares = new ArrayList<>();
        dlgCelulares = false;

        celulares = direccionSocioDeNegociosFacade.obtenerCelularesCliente(numeroCelular.contains("CL") ? numeroCelular : numeroCelular + "CL");

        if (celulares != null && celulares.size() == 1) {
            numeroCelular = celulares.get(0).trim();
        } else if (celulares != null && !celulares.isEmpty()) {
            dlgCelulares = true;
        } else {
            mostrarMensaje("Error", "No se encontraron datos coincidentes.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos coincidentes");
            return;
        }
    }

    public void seleccionarCelular() {
        String cel = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("celular");
        numeroCelular = cel.trim();
        dlgCelulares = false;
    }

    public void dividirMensajes() {
        mensajes = new ArrayList<>();
        String[] s = mensaje.split(" ");

        if (mensaje.length() > 0) {
            for (String item : s) {
                if (mensajes.isEmpty()) {
                    mensajes.add((item + " "));
                } else if (mensajes.get(mensajes.size() - 1).length() <= longitudMensaje) {
                    if ((item.length() + mensajes.get(mensajes.size() - 1).length()) >= longitudMensaje) {
                        mensajes.add((item + " "));
                    } else {
                        mensajes.set(mensajes.size() - 1, mensajes.get(mensajes.size() - 1) + item + " ");
                    }
                } else {
                    mensajes.add((item + " "));
                }
            }
        } else {
            mensajes = null;
        }
    }

    public void enviarMensajeSMS() {
        if (numeroCelular == null || numeroCelular.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el número de celular, para poder enviar el mensaje de texto.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar el numero de celular, para poder enviar el mensaje de texto");
            return;
        }
        if (mensaje == null || mensaje.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el mensaje que quiere enviar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese el mensaje que quiere enviar");
            return;
        }

        String[] cels = numeroCelular.split(";");

        if (cels != null && cels.length > 0 && numeroCelular.contains(";")) {
            boolean exito = false;
            for (String s : cels) {
                if (enviarSms(s)) {
                    exito = true;
                } else {
                    exito = false;
                }
            }

            if (exito) {
                limpiar();
                mostrarMensaje("Éxito", "Se enviaron los mensajes correctamente.", false, true, false);
            }
        } else if (enviarSms(numeroCelular)) {
            limpiar();
            mostrarMensaje("Éxito", "Se enviaron los mensajes correctamente.", false, true, false);
        }
    }

    private boolean enviarSms(String num) {
        SMSServiceClient client;

        for (String s : mensajes) {
            MensajeTextoDTO sms = new MensajeTextoDTO();
            try {
                client = new SMSServiceClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                sms.setCodigoPais("57");
                sms.setDestino(num);
                sms.setMensaje(s);
                sms.setPruebas(false);
                sms.setIp(userSessionInfoMBean.getIp());
                sms.setUsuario(userSessionInfoMBean.getUsuario());

                client.enviarSMS(sms);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al enviar el mensaje de texto. Mensaje no enviado {0}. Error {1}", new Object[]{s, e});
                mostrarMensaje("Error", "Ocurrió un error al enviar el mensaje de texto:\" " + s + "\". Error: " + e.getMessage() + ".", true, false, false);
                return false;
            }
        }

        return true;
    }

    public void limpiar() {
        mensaje = null;
        numeroCelular = null;
        dlgCelulares = false;
        mensajes = new ArrayList<>();
        celulares = new ArrayList<>();
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
