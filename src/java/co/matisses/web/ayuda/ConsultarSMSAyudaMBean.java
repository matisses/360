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
@Named(value = "consultarSMSAyudaMBean")
public class ConsultarSMSAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ConsultarSMSAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ConsultarSMSAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "No se encontraron datos disponibles.", "No hay datos para mostrar.", "Envié mensajes de texto primero, para poder visualizarlo en este módulo."});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
