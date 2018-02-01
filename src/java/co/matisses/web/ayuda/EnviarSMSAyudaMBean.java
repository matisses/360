package co.matisses.web.ayuda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "enviarSMSAyudaMBean")
public class EnviarSMSAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(EnviarSMSAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public EnviarSMSAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "No se encontraron datos coincidentes.", "No se encontraron número de celular para el cliente.", "Verifique la información del cliente e inténtelo nuevamente."});

        mensajes.add(new String[]{"E", "Debe ingresar el número de celular, para poder enviar el mensaje de texto.", "No se ha proporcionado un número de celular al que se enviara el mensaje.",
            "Ingrese el número de destinatario al que se le deberá llegar el mensaje."});

        mensajes.add(new String[]{"E", "Ingrese el mensaje que quiere enviar.", "Ingrese el mensaje que desea que se envié.", "Digite el mensaje que quiere enviar."});

        mensajes.add(new String[]{"E", "Ocurrió un error al enviar el mensaje de texto:\"?\". Error: ?.", "Se generó un error al enviar el mensaje.",
            "Inténtelo nuevamente y si el error persiste comuníquese con sistemas."});

        mensajes.add(new String[]{"I", "Se enviaron los mensajes correctamente.", "Se enviaron los mensajes correctamente.", ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
