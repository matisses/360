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
@Named(value = "configuracionProformaAyudaMBean")
public class ConfiguracionProformaAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ConfiguracionProformaAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ConfiguracionProformaAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Ítem.",
            "Se está intentando agregar una columna de tipo Ítem; y el proveedor ya tiene una columna con ese tipo.", "Elimine la columna tipo Ítem actual del proveedor y agregue la nueva."});
        mensajes.add(new String[]{"E", "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Identificador.",
            "Se está intentando agregar una columna de tipo Identificador; y el proveedor ya tiene una columna con ese tipo.", "Elimine la columna tipo Identificador actual del proveedor y agregue la nueva."});
        mensajes.add(new String[]{"E", "No se pudo crear la configuración para la columna seleccionada.", "Al agregar una columna ocurre un error inesperado.",
            "Intente nuevamente agregar la columna y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la configuración para la columna seleccionada, debido a que no se encontraron datos.",
            "No se pudieron obtener los datos necesarios para agregar la columna al proveedor.", "Intente nuevamente agregar la columna y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "Ocurrio un error al eliminar el registro de configuración.", "No se puede eliminar el registro de la columna por problemas internos.",
            "Intente nuevamente agregar la columna y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "La columna seleccionada no se puede eliminar, debido a que está en uso.", "La columna que desea eliminar está siendo usada por una proforma u otra columna.",
            "Verifique que columnas están usando la que quiere eliminar y verifique que es posible eliminar estas."});
        mensajes.add(new String[]{"E", "No se ha podido generar la PI", "Ocurrió un error al generar el archivo de excel con los datos para la proforma nueva.",
            "Intente nuevamente agregar la columna y si el error persiste comuníquese con sistemas."});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
