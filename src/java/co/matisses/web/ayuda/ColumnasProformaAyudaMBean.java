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
@Named(value = "columnasProformaAyudaMBean")
public class ColumnasProformaAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ColumnasProformaAyudaMBean.class.getSimpleName());
    private List<String[]> tablaCampos;
    private List<String[]> mensajes;
    private List<String[]> tiposDatos;

    public ColumnasProformaAyudaMBean() {
        tablaCampos = new ArrayList<>();
        mensajes = new ArrayList<>();
        tiposDatos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        tablaCampos.add(new String[]{"Columna", "Nombre que se le aplicara a la columna en la proforma.", "Si"});
        tablaCampos.add(new String[]{"Columna Inglés", "Nombre que se le aplicara a la columna en la proforma, en versión inglés.", "Si"});
        tablaCampos.add(new String[]{"SubColumna", "Nombre de la subColumna.", "No"});
        tablaCampos.add(new String[]{"SubColumna Inglés", "Nombre de la subColumna en versión inglés.", "No"});
        tablaCampos.add(new String[]{"Dato Columna", "De qué forma se quiere que el sistema trate los datos que se almacenaran en esta columna.", "Si"});
        tablaCampos.add(new String[]{"Decimales Visibles", "Cuantos decimales se quieren ver de este dato.", "No"});
        tablaCampos.add(new String[]{"Longitud Tabla", "Cuanto espacio en la vista de la proforma como tabla, quiere que la columna ocupe.", "No"});
        tablaCampos.add(new String[]{"Crear Ítem", "Permitir que a partir de la columna se pueda crear una copia solo con cambiar el valor de esta.", "No"});
        tablaCampos.add(new String[]{"Tipo Ítem", "Al seleccionar esta propiedad, la columna será tratada como la más importante.", "No"});
        tablaCampos.add(new String[]{"Tipo Identificar", "Esta propiedad permite que la columna con él, pueda identificar cada ítem.", "No"});
        tablaCampos.add(new String[]{"Tipo Imagen", "Esta propiedad permite que el sistema sepa que en la columna ira una imagen.", "No"});
        tablaCampos.add(new String[]{"Tipo Cantidad", "Permitirá identificar el tipo de la columna, se debe seleccionar solo si la columna es la cantidad principal.", "No"});
        tablaCampos.add(new String[]{"Tipo Valor Total", "Esta propiedad ayuda a identificar si la columna es el total en plata de la proforma.", "No"});
        tablaCampos.add(new String[]{"Tipo CBM", "Permite identificar si la columna es el CBM unitario.", "No"});
        tablaCampos.add(new String[]{"Tipo Valor Unitario", "Permite identificar si la columna es el Total unitario de la moneda de la proforma.", "No"});
        tablaCampos.add(new String[]{"Obligatoria", "Se marca cuando se quiere que todas las proformas deban tener esta columna.", "No"});
        tablaCampos.add(new String[]{"Modificable", "Se debe marcar si el valor de la columna se puede modificar desde la proforma.", "No"});
        tablaCampos.add(new String[]{"Modificar Nuevo", "Ocurre cuando se quiere que al crear un ítem nuevo, los valores de las columnas se puedan o no modificar.", "No"});
        tablaCampos.add(new String[]{"Columna Calculada", "Si la columna depende de otras para mostrar su valor.", "No"});

        tiposDatos.add(new String[]{"Boolean", "Quiere decir que la columna maneja datos como Verdadero o Falso.", "", ""});
        tiposDatos.add(new String[]{"Decimales", "Se refiere cuando la columna tendrá números con decimales.", "± 1.8x10-308", "± 1.8x10308"});
        tiposDatos.add(new String[]{"Float", "Se usa con números de gran precisión con una exactitud de 7 dígitos.", "± 3.4x10-38", "± 3.4x1038"});
        tiposDatos.add(new String[]{"Identificador", "Se usa con columnas que se usaran para identificar los ítems.", "", ""});
        tiposDatos.add(new String[]{"Imagen", "Se usa con columnas que se usaran para imágenes los ítems.", "", ""});
        tiposDatos.add(new String[]{"Long", "Se usa para números largos.", "-9223372036854775808", "9223372036854775807"});
        tiposDatos.add(new String[]{"Números", "Se usa para números.", "-2147483648", "2147483647"});
        tiposDatos.add(new String[]{"Short", "Se usa para datos tipo número de -32.768 hasta 32.767 únicamente.", "-32.768", "32.767"});
        tiposDatos.add(new String[]{"Texto", "Se usa para las columnas que manejan texto normal o datos para los que no hay tipo de dato.", "", ""});

        mensajes.add(new String[]{"E", "No sé ha ingresado un parámetro de búsqueda.", "Se está intentando buscar sin ningún parámetro o palabra clave.", "Inserte un valor para realizar la consulta."});
        mensajes.add(new String[]{"E", "No sé han ingresado datos para el campo Columna", "La Columna no tiene valores para poder procesar, crear o modificar la columna.", "Ingrese el valor para la columna."});
        mensajes.add(new String[]{"E", "No sé han ingresado datos para el campo Columna Ingles.", "Al crear o modificar una columna es indispensable, que la columna en inglés, también se registre.",
            "Debe ingresar el valor para la columna en inglés."});
        mensajes.add(new String[]{"E", "No se encontró la columna que desea modificar.", "Al momento de modificar o eliminar una columna, si esta no es encontrada se mostrara este mensaje.",
            "No se han encontrado datos de la columna solicitada."});
        mensajes.add(new String[]{"E", "No se puede eliminar la columna seleccionada, debido a que está en uso.", "La columna que se desea eliminar está siendo usada por proveedores y/o PI.",
            "No se puede eliminar, debido a que está siendo usada en otras proformas."});
        mensajes.add(new String[]{"I", "Columna ?, creada correctamente", "La columna se creó y fue guardada en base de datos", ""});
        mensajes.add(new String[]{"I", "Columna ?, eliminada correctamente", "Se eliminó la columna seleccionada", ""});
        mensajes.add(new String[]{"I", "Columna ?, editada correctamente", "Los datos fueron modificados exitosamente", ""});
        mensajes.add(new String[]{"I", "Se ha guardado correctamente el proveedor ?", "Se guardó correctamente el proveedor", ""});
    }

    public List<String[]> getTablaCampos() {
        return tablaCampos;
    }

    public void setTablaCampos(List<String[]> tablaCampos) {
        this.tablaCampos = tablaCampos;
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }

    public List<String[]> getTiposDatos() {
        return tiposDatos;
    }

    public void setTiposDatos(List<String[]> tiposDatos) {
        this.tiposDatos = tiposDatos;
    }
}
