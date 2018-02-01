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
@Named(value = "contenedoresProveedorAyudaMBean")
public class ContenedoresProveedorAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ContenedoresProveedorAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ContenedoresProveedorAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Código Proveedor.",
            "No sé a ingresado dato en el campo Código Proveedor para poder buscar.", "Ingrese un código valido para poder realizar la consulta."});
        mensajes.add(new String[]{"E", "No sé a encontrado el proveedor ingresado.",
            "El proveedor que se está buscando no ha sido registrado en el sistema.", "Ingrese un código valido para poder realizar la consulta."});
        mensajes.add(new String[]{"E", "Se debe seleccionar un contenedor para continuar.",
            "Debe seleccionar el contenedor que se desea agregar.", "Seleccione un contenedor de los disponibles en la lista de contenedores."});
        mensajes.add(new String[]{"E", "El contenedor seleccionado, ya fue agregado a este proveedor.",
            "Previamente el contenedor seleccionado fue agregado y no se puede agregar nuevamente.", "Seleccione un contenedor diferente al que está intentando ingresar."});
        mensajes.add(new String[]{"E", "Se le debe dar eliminar al contenedor a eliminar.",
            "No sé a seleccionado adecuadamente el contenedor a eliminar.", "Seleccione el contenedor que desea eliminar."});
        mensajes.add(new String[]{"E", "El contenedor indicado no se puede eliminar debido a que puede estar en uso.",
            "No sé a seleccionado adecuadamente el contenedor a eliminar.", "El contenedor no se puede eliminar."});
        mensajes.add(new String[]{"I", "Se eliminó el contenedor para el proveedor correctamente.", "El contenedor se eliminó correctamente.", ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
