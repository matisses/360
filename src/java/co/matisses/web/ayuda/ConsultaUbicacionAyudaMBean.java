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
@Named(value = "consultaUbicacionAyudaMBean")
public class ConsultaUbicacionAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ColumnasProformaAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ConsultaUbicacionAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "No se encontraron datos correspondientes a la búsqueda.",
            "El valor digitado por el usuario, no genero resultados.",
            "Ingrese un valor valido."});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
