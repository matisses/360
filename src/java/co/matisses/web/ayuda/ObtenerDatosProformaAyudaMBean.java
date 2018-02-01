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
@Named(value = "obtenerDatosProformaAyudaMBean")
public class ObtenerDatosProformaAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ObtenerDatosProformaAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ObtenerDatosProformaAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "El tipo de archivo que está intentando subir no es válido. Formatos validos XLSX, XLS",
            "Está subiendo un archivo con extensión diferente a los admitidos", "Extensiones permitidas .XLSX, .XLS."});
        mensajes.add(new String[]{"E", "No ha seleccionado un archivo para subir.",
            "Se debe seleccionar el archivo que desea agregar.", "Seleccione un archivo valido para cargar."});
        mensajes.add(new String[]{"E", "Se ha producido un error al subir el archivo, verifique la extensión de este",
            "Está subiendo un archivo con extensión diferente a los admitidos.", "Extensiones permitidas .XLSX, .XLS."});
        mensajes.add(new String[]{"E", "No se pudo crear la columna, verifique los datos suministrados.",
            "Los datos son incorrectos y no se puede crear la columna.", "Ingrese los datos correctamente."});
        mensajes.add(new String[]{"E", "La columna ya fue asignada.", "La columna que está seleccionando para agregar, ya fue agregada.", "Seleccione o cree una columna nueva."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo ítem.", "Se debe incluir una columna tipo Ítem.", "Incluya una columna tipo Ítem, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo identificador.",
            "Se debe incluir una columna tipo Identificador.", "Incluya una columna tipo Identificador, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo imagen.",
            "Se debe incluir una columna tipo Imagen.", "Incluya una columna tipo Imagen, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo cantidad.",
            "Se debe incluir una columna tipo Cantidad.", "Incluya una columna tipo Cantidad, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo valor total.",
            "Se debe incluir una columna tipo Valor Total.", "Incluya una columna tipo Valor Total, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo CBM.",
            "Se debe incluir una columna tipo CBM.", "Incluya una columna tipo CBM, para poder continuar."});
        mensajes.add(new String[]{"E", "Para poder crear la PI, debe incluir una columna tipo valor unitario.",
            "Se debe incluir una columna tipo Valor Unitario.", "Incluya una columna tipo Valor Unitario, para poder continuar."});
        mensajes.add(new String[]{"E", "No fue posible crear la proforma. Comuníquese con el departamento de sistemas.",
            "Ocurrió un error interno al crear la proforma", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la proforma.", "Ocurrió un error interno al crear la proforma", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se puede continuar, debido a que no se encontraron columnas para aplicar a la información.",
            "No se encontraron columnas configuradas para poder crear la proforma.", "Asigne las columnas necesarias para permitir que la proforma se cree correctamente."});
        mensajes.add(new String[]{"I", "Proforma creada correctamente: ? - ? - ?.", "La proforma se almaceno correctamente, en los \"?\", se muestran los datos de la proforma.", ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
