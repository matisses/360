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
@Named(value = "trasladoPalletAyudaMBean")
public class TrasladoPalletAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(TrasladoPalletAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public TrasladoPalletAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "No fue posible iniciar una sesión en SAP B1WS.", "Error de sesión.", "Comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se encontró la ubicación destino ?, para poder realizar el traslado.", "La ubicación a la que desea trasladar no se encontró en el almacén destino.",
            "Informe a sistemas para que cree la ubicación que necesita."});
        mensajes.add(new String[]{"E", "Ocurrió un error al crear el traslado entre almacenes.", "Error al crear el traslado en SAP.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo imprimir el traslado.", "Ocurrió un error al registrar la orden de imprimir el traslado.", "Imprima el traslado desde SAP."});
        mensajes.add(new String[]{"E", "El usuario no tiene permiso para usar este módulo.", "No se le han asignado permisos al usuario para poder realizar estos traslados.",
            "Solicítele permisos a su jefe inmediato."});
        mensajes.add(new String[]{"I", "Se creó el traslado #?.", "Traslado creado con éxito.", ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
