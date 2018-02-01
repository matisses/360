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
@Named(value = "datosProveedorAyudaMBean")
public class DatosProveedorAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(DatosProveedorAyudaMBean.class.getSimpleName());
    private List<String[]> tablaCampos;
    private List<String[]> mensajes;
    private List<String[]> datosContenedor;
    private List<String[]> datosTablaAnticipos;
    private List<String[]> camposProveedor;

    public DatosProveedorAyudaMBean() {
        tablaCampos = new ArrayList<>();
        mensajes = new ArrayList<>();
        datosContenedor = new ArrayList<>();
        datosTablaAnticipos = new ArrayList<>();
        camposProveedor = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        datosContenedor.add(new String[]{"Contenedor", "Tipo de contenedor asignado a la proforma, aquí se mostrar un nombre corto abreviado del original.", "No"});
        datosContenedor.add(new String[]{"Fecha embarque", "Fecha en la que la mercancía se embarcara.", "Si"});
        datosContenedor.add(new String[]{"T.P", "Tiempo de producción", "Si"});
        datosContenedor.add(new String[]{"T.T", "Tiempo de tránsito.", "Si"});
        datosContenedor.add(new String[]{"Fecha vencimiento", "Fecha en la que se vence el pago de la proforma.", "Si"});
        datosContenedor.add(new String[]{"Fecha max pago", "Fecha máxima para pago.", "Si"});
        datosContenedor.add(new String[]{"Días vencidos", "Días que lleva de vencido el pago.", "No"});

        datosTablaAnticipos.add(new String[]{"Fecha", "Fecha en la que se creó la transacción."});
        datosTablaAnticipos.add(new String[]{"Fecha giro", "Fecha en la que se llevara a cabo la transacción."});
        datosTablaAnticipos.add(new String[]{"Cuenta", "Número de la cuenta a la que se le efectuara la transacción."});
        datosTablaAnticipos.add(new String[]{"Valor", "Valor a girar correspondiente a la moneda seleccionada."});
        datosTablaAnticipos.add(new String[]{"Moneda", "Tipo de moneda que se usara para la transacción."});
        datosTablaAnticipos.add(new String[]{"Proforma", "Si la transacción es uso de anticipo de cuenta se registrara aquí a que proforma se le relaciono el saldo."});
        datosTablaAnticipos.add(new String[]{"Comentario", "Comentario correspondiente a la transacción."});
        datosTablaAnticipos.add(new String[]{"Usuario", "Usuario que realiza el movimiento."});

        camposProveedor.add(new String[]{"Código proveedor", "Es el código que identifica al proveedor, este código debe ser único."});
        camposProveedor.add(new String[]{"Nombre socio negocios", "Aquí se debe ingresar el nombre que se usara para nombrar el proveedor."});
        camposProveedor.add(new String[]{"Razón social proveedor", "Es el nombre propio del proveedor."});
        camposProveedor.add(new String[]{"Teléfono", "Número de contacto con el proveedor."});
        camposProveedor.add(new String[]{"Dirección", "Ubicación del proveedor."});
        camposProveedor.add(new String[]{"Correo", "Dirección de e-mail para contacto con el proveedor."});
        camposProveedor.add(new String[]{"Persona contacto", "Es la persona con la que se hace el contacto de compra de la mercancía."});
        camposProveedor.add(new String[]{"País", "País en el que se encuentra ubicado el proveedor."});
        camposProveedor.add(new String[]{"Estado", "Estado del país donde se encuentra ubicado el proveedor."});

        tablaCampos.add(new String[]{"Código Proveedor", "Aquí se ingresara el código actual asignado al proveedor."});
        tablaCampos.add(new String[]{"Razón Social Proveedor", "Nombre de la empresa o razón social de la misma."});
        tablaCampos.add(new String[]{"Dirección", "Ubicación de la empresa proveedora."});
        tablaCampos.add(new String[]{"Teléfono", "Número de contacto de la compañía proveedora."});
        tablaCampos.add(new String[]{"Correo", "Correo de contacto con la compañía proveedora."});
        tablaCampos.add(new String[]{"Persona Contacto", "Persona a la que se contacta para temas relacionados,"
            + " con la empresa proveedora."});
        tablaCampos.add(new String[]{"Seleccione País", "País donde se encuentra localizada la empresa proveedora."});
        tablaCampos.add(new String[]{"Seleccione Estado", "Estado del país donde se localiza la empresa proveedora."});

        mensajes.add(new String[]{"E", "La imagen que está intentando subir es demasiado ancha. Ancho permitido hasta 260px.",
            "La imagen que se está intentando colocar como logo, tiene un tamaño inadecuado.", "Edite la imagen para que sus medidas de ancho sean menores o igual a 260px."});
        mensajes.add(new String[]{"E", "La imagen que está intentando subir es demasiado alta. Alto permitido hasta 260px.",
            "La imagen que se está intentando colocar como logo, tiene un tamaño inadecuado.", "Edite la imagen para que sus medidas de alto sean menores o igual a 260px."});
        mensajes.add(new String[]{"E", "La extensión no es válida, formato permitido jpg o png.",
            "Al momento de agregarle la imagen al proveedor, se debe tener en cuenta que únicamente se reciben imágenes con la extensión jpg o png",
            "Seleccione una imagen con una de las extensiones permitidas."});
        mensajes.add(new String[]{"E", "No ha subido ninguna imagen para cargar.", "No fue posible cargar la imagen que el usuario selecciono, o no selecciono ninguna imagen.",
            "Seleccione una imagen o inténtelo de nuevo."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Código Proveedor.",
            "No se ha ingresado un valor en el campo del código del proveedor.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Nombre Socio Negocios.",
            "No se ha ingresado un valor en el campo del nombre de socio de negocios.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Nombre Proveedor.",
            "No se ha ingresado un valor en el campo del nombre del proveedor.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Dirección.",
            "No se ha ingresado un valor en el campo de la dirección", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Teléfono.",
            "No se ha ingresado un valor en el campo de teléfono.", "Ingrese un valor en el campo para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Correo.",
            "No se ha ingresado un valor en el campo de correo.", "Ingrese un valor en el campo de correo."});
        mensajes.add(new String[]{"E", "Verifique que haya ingresado un valor en el campo Persona Contacto.",
            "No se ha ingresado un valor en el campo de persona de contacto.", "Ingrese un valor en el campo para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya seleccionado un valor en el campo País.",
            "No se ha seleccionado un valor de la lista de países.", "Seleccione uno de los países disponibles para poder continuar."});
        mensajes.add(new String[]{"E", "Verifique que haya seleccionado un valor en el campo Estado.",
            "No se ha seleccionado un valor en la lista de estados.", "Seleccione uno de los estados disponibles para poder continuar."});
        mensajes.add(new String[]{"E", "No se puede guardar debido a que el proveedor ya existe prov: ###.", "El proveedor que se quiere crear ya existe.", "Verifique los datos e inténtelo nuevamente."});
        mensajes.add(new String[]{"E", "No se encontraron datos coincidentes con: ?????.", "El parámetro de búsqueda ingresado no arrojo resultados.", "Ingrese un valor que arroje resultados."});
        mensajes.add(new String[]{"E", "No se puede seleccionar el tipo de pago Roll on, debido a que no se encontraron anticipos a cuenta, para la moneda y cuenta seleccionada.",
            "No se encontró saldo disponible para el tipo de giro seleccionado.", "Seleccione un tipo de giro que permite la creación."});
        mensajes.add(new String[]{"E", "Para poder seleccionar el tipo de giro ROLL ON, primero seleccione una cuenta bancaria.",
            "Para el tipo de giro seleccionado, primero debe seleccionar una cuenta bancaria.", "Seleccione una cuenta bancaria."});
        mensajes.add(new String[]{"E", "No se pudo modificar la transacción bancaria.",
            "La asignación de fecha a la transacción no se ejecutó bien.", "Inténtelo nuevamente o comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "Debe seleccionar la cuenta a la que se girara.", "No se ha seleccionado una cuenta bancaria.", "Seleccione la cuenta que se usara para el giro."});
        mensajes.add(new String[]{"E", "Debe seleccionar el tipo de giro.", "No se ha seleccionado un tipo de giro.", "Seleccione el tipo de giro necesario."});
        mensajes.add(new String[]{"E", "Para el tipo de giro seleccionado, se debe ingresar el valor del anticipo.",
            "No ha ingresado un valor en el campo de anticipo.", "Ingesé el valor que va a girar."});
        mensajes.add(new String[]{"E", "Para el tipo de giro seleccionado, no se encontró balance pendiente.",
            "Se seleccionó el tipo de giro balance y no se encontró saldo pendiente por girar.", "Verifique los datos ingresados."});
        mensajes.add(new String[]{"E", "Para el tipo de giro seleccionado, no se encontró anticipo en Cuenta.",
            "Se seleccionó el tipo de giro roll on y no se encontró saldo a girar.", "Verifique los datos ingresados."});
        mensajes.add(new String[]{"E", "No se encontró total para girar.", "Se está intentando crear una transacción sin total a girar.", "Verifique los datos ingresados."});
        mensajes.add(new String[]{"E", "Para este tipo de giro no se puede pagar una mayor cantidad de dinero de la que se debe.",
            "El tipo de giro seleccionado no permite que se pague más de lo que se debe.", "Ingrese un valor igual o inferior al que se debe."});
        mensajes.add(new String[]{"E", "Ingrese una descripción para la transacción.", "No se ha ingresado un valor en el campo de descripción.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "No se puedo hacer el ingreso del dinero solicitado de anticipos a cuenta.",
            "Ocurrió un error al registrar la transacción.", "Inténtelo nuevamente o comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "Está intentando agregar un pago superior a lo que se debe de la proforma.",
            "Está intentando ingresar un pago superior a lo que se debe.", "Ingrese un pago igual o inferior a lo que se debe."});
        mensajes.add(new String[]{"E", "No se puedo hacer la salida del dinero solicitado de anticipos a cuenta.",
            "Ocurrió un error al registrar la transacción.", "Inténtelo nuevamente o comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se encontró el saldo solicitado de: ###.###, en la cuenta bancaria seleccionada.", "El valor total no se encontró.", "Verifique los datos ingresados."});
        mensajes.add(new String[]{"E", "No se pudo crear la transacción bancaria.", "Ocurrió un error al crear la transacción.", "Inténtelo nuevamente sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se encontró una moneda asignada a la proforma, debido a esto no se puede realizar el pago por este medio.",
            "No se ha asignado una moneda a la proforma.", "Asigne una moneda para poder continuar."});
        mensajes.add(new String[]{"E", "No se pudo reprocesar el documento de la transacción.", "Ocurrió un error al re procesar el documento de la transacción.", "Comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "Debe ingresar un valor en anticipo cuenta, para poder continuar.", "No ha ingresado un valor de anticipo a cuenta.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Seleccione un tipo de moneda para poder continuar.",
            "No se ha seleccionado una moneda para agregar el anticipo a cuenta.", "Seleccione una moneda de la lista para poder continuar."});
        mensajes.add(new String[]{"E", "Seleccione una proforma para poder continuar.", "No se ha seleccionado una proforma para asignar el anticipo de cuenta.", "Seleccione una proforma de la lista para continuar."});
        mensajes.add(new String[]{"E", "Seleccione una cuenta bancaria para poder continuar.",
            "No se ha seleccionado una cuenta bancaria.", "Seleccione una cuenta de las disponibles para poder continuar."});
        mensajes.add(new String[]{"E", "Se debe ingresar un comentario para guardar el anticipo cuenta.",
            "No se ha ingresado un valor en el campo de comentario.", "Ingrese un comentario para poder continuar."});
        mensajes.add(new String[]{"E", "No se puede usar este medio de pago para la proforma seleccionada, debido a que esta no tiene saldo pendiente por pagar.",
            "No se ha encontrado saldo pendiente por pagar.", "Seleccione una proforma que si tenga saldo por pagar."});
        mensajes.add(new String[]{"E", "No se puede pagar por este medio más de lo que se debe de la proforma seleccionada.",
            "Está intentando pagar más de lo que se debe.", "Ingrese un valor inferior o igual a lo que se debe."});
        mensajes.add(new String[]{"E", "No se encontró saldo en cuenta para la moneda y cuenta seleccionada.",
            "De la moneda y cuenta seleccionada no se encontró saldo disponible en cuenta para usar.", "Seleccione una cuenta y una moneda con saldo."});
        mensajes.add(new String[]{"E", "La cantidad solicitada para el uso del anticipo en cuenta, no se encontró.",
            "No se encontró saldo en la cuenta seleccionada.", "Seleccione una cuenta para tomar el saldo."});
        mensajes.add(new String[]{"E", "No se puedo usar el anticipo en cuenta.", "No se pudo usar el anticipo en cuenta.", "Inténtelo nuevamente sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo guardar el cambio en anticipos cuenta.",
            "Ocurrió un error al asignar la fecha de la transacción de anticipo en cuenta.", "Inténtelo nuevamente sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo actualizar la fecha de giro para el anticipo a cuenta seleccionado.",
            "Ocurrió un error al asignar la fecha de la transacción de anticipo en cuenta.", "Inténtelo nuevamente sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "Debe seleccionar un banco para poder continuar.",
            "El usuario no ha seleccionado un banco para poder continuar.", "Se debe seleccionar un banco para la asignación de una cuenta nueva."});
        mensajes.add(new String[]{"E", "Ingrese la razón social del banco.", "No se ha ingresado la razón social del banco.", "Ingrese un valor en el campo para poder continuar."});
        mensajes.add(new String[]{"E", "No se pudo modificar el banco.", "Ocurrió un error al actualizar el banco.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear el banco.", "Ocurrió un error al crear el banco.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se ha seleccionado la sucursal del banco para guardar.", "Se debe seleccionar una sucursal del banco para poder continuar.", "Seleccione una de las opciones."});
        mensajes.add(new String[]{"E", "Ingrese una dirección para la sucursal, y de clic en Guardar.", "No se ha ingresado un valor en el campo dirección.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Ingrese un swift para la sucursal, y de clic en Guardar.", "No se ha ingresado un valor en el campo.", "Ingrese un valor para poder continuar."});
        mensajes.add(new String[]{"E", "Ingrese un país para la sucursal, y de clic en Guardar.", "No ha seleccionado un país para la sucursal.", "Seleccione un país de la lista para continuar."});
        mensajes.add(new String[]{"E", "Ingrese un estado para la sucursal, y de clic en Guardar.", "No ha seleccionado un estado para la sucursal.", "Seleccione un estado de la lista para continuar."});
        mensajes.add(new String[]{"E", "No se pudo modificar la sucursal.", "Error al cambiar los datos de la sucursal.", "Inténtelo nuevo sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la sucursal.", "Error al crear la sucursal.", "Inténtelo nuevo sino comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No ha seleccionado una cuenta para crear.", "El usuario no ha seleccionado una cuenta.", "Seleccione una cuenta para continuar."});
        mensajes.add(new String[]{"E", "Ingrese una cuenta bancaria.", "No se ha encontrado ningún valor en el campo.", "Inserte un valor en el campo de cuenta bancaria."});
        mensajes.add(new String[]{"E", "No se pudo modificar la cuenta.", "Ocurrió un error al modificar la cuenta.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la cuenta bancaria.", "Error al crear el registro de la cuenta.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "Esta opción no se puede seleccionar, debido a que no se encontró un proveedor seleccionado.",
            "No se han encontrado datos de ningún proveedor.", "Ingrese primero un proveedor para administrar y poder descargar el informe."});
        mensajes.add(new String[]{"E", "Seleccione un tipo de exportación para poder continuar.", "No ha seleccionado el tipo de exportación.", "Seleccione el tipo de exportación que desea."});
        mensajes.add(new String[]{"E", "Seleccione un tipo de datos para poder continuar.", "No ha seleccionado ningún tipo de dato.", "Seleccione un tipo de la lista disponible."});
        mensajes.add(new String[]{"E", "Seleccione una proforma para poder continuar.", "No se ha seleccionado ninguna de las proformas disponibles.", "Seleccione una proforma de las disponibles."});
        mensajes.add(new String[]{"E", "Ingrese un valor para el filtro seleccionado para poder continuar.",
            "No se ha seleccionado ningún filtro para aplicar a la exportación.", "Seleccione un filtro para la exportación."});
        mensajes.add(new String[]{"I", "Se actualizo correctamente el proveedor ###.", "El proveedor fue actualizado correctamente.", ""});
        mensajes.add(new String[]{"I", "Se ha guardado correctamente el proveedor ###.", "El proveedor fue creado con éxito.", ""});
        mensajes.add(new String[]{"I", "Se creó la transacción bancaria correctamente.", "La transacción quedo almacenada correctamente en el sistema.", ""});
        mensajes.add(new String[]{"I", "Se usó el anticipo a cuenta adecuadamente.", "Anticipo a cuenta utilizado correctamente.", ""});
        mensajes.add(new String[]{"I", "Se creó el anticipo a cuenta adecuadamente.", "Anticipo a cuenta creado correctamente.", ""});
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

    public List<String[]> getDatosContenedor() {
        return datosContenedor;
    }

    public void setDatosContenedor(List<String[]> datosContenedor) {
        this.datosContenedor = datosContenedor;
    }

    public List<String[]> getDatosTablaAnticipos() {
        return datosTablaAnticipos;
    }

    public void setDatosTablaAnticipos(List<String[]> datosTablaAnticipos) {
        this.datosTablaAnticipos = datosTablaAnticipos;
    }

    public List<String[]> getCamposProveedor() {
        return camposProveedor;
    }

    public void setCamposProveedor(List<String[]> camposProveedor) {
        this.camposProveedor = camposProveedor;
    }
}
