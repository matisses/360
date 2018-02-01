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
@Named(value = "proformaAyudaMBean")
public class ProformaAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ProformaAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public ProformaAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "No se pudo asignar la moneda a la proforma, intentelo nuevamente.",
            "La moneda seleccionada no fue asignada correctamente a la proforma.", "Intente asignar la moneda nuevamente."});
        mensajes.add(new String[]{"E", "Seleccione una moneda para poder asignar.",
            "Para poder realizar transacciones, se debe seleccionar una moneda de la lista.", "Seleccione una moneda y continue el proceso."});
        mensajes.add(new String[]{"E", "No se pudo calcular adecuadamente el valor con descuento de la proforma.",
            "El valor de descuento no fue posible calcularlo.", "Comuniquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se encontraron datos coincidentes con el parámetro de busqueda.",
            "El texto suministrado para la busqueda noarrojo datos.", "Intente la busqueda con un texto diferente."});
        mensajes.add(new String[]{"E", "Seleccione un campo por el que quiere duplicar.",
            "Se debe seleccionar un campo de la lista para poder continuar.", "Seleccione el campo para duplicar."});
        mensajes.add(new String[]{"E", "Ingrese un valor para el nuevo ítem.",
            "Se debe ingresar un valor para el campo y poder continuar.", "Ingrese un valor y continue el proceso."});
        mensajes.add(new String[]{"E", "No se pudo realizar la creación del nuevo ítem, comuniquese con sistemas.",
            "Ocurrió un error interno al crear el ítem.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "Ocurrió un problema al crear la nueva línea para la proforma.",
            "Ocurrió un error interno al crear la nueva línea.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "El ítem indicado no se pudo eliminar, comuníquese con el departamento de sistemas.",
            "Ocurrió un error interno al eliminar el ítem.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "Seleccione una columna a agregar.",
            "Se debe seleccionar la columna que quiere agregar.", "Seleccione una columna para poder continuar."});
        mensajes.add(new String[]{"E", "Lo sentimos, pero la columna que desea incluir ya existe en la proforma actual.",
            "La columna que se quiere agregar ya había sido asignada a la proforma.", "Seleccione una columna diferente o verifique la que quiere insertar."});
        mensajes.add(new String[]{"E", "No se pudo agregar la nueva columna a la proforma.",
            "Ocurrió un error al agregar la columna nueva.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo asignar la cantidad al contenedor para este ítem.",
            "No fue posible la asignación de la cantidad.", "Inténtelo nuevamente y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo incluir la línea de la proforma. Comuníquese con el departamento de sistemas.",
            "Ocurrió un error al incluir la línea nueva.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo modificar el valor de la proforma.",
            "Ocurrió un error al modificar el valor de la proforma.", "Re calcule los datos y si el error persiste comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se puede modificar el valor de la proforma, debido a que el campo que quiere modificar no tiene un tipo de dato relacionado.",
            "Las columnas deben tener tipo de dato para poder que se dejen trabajar.", "Vaya al modulo de columnas y asígnele el tipo de dato a la columna que quiere modificar."});
        mensajes.add(new String[]{"E", "Al agregar el contenedor seleccionado.",
            "Ocurrió un error al agregar el contenedor.", "Inténtelo nuevamente y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo eliminar el contenedor seleccionado.",
            "Ocurrió un error al eliminar el contenedor.", "Inténtelo nuevamente y si el error persiste comuníquese con sistemas."});
        mensajes.add(new String[]{"E", "No se pudo modificar la fecha de embarque.",
            "Ocurrió un error al modificar la fecha de embarque.", "Seleccione una fecha del calendario."});
        mensajes.add(new String[]{"E", "El archivo que desea subir no es del formato permitido. Formato permitido .pdf.",
            "Para poder subir un archivo debe ser del formato de .pdf.", "Seleccione un archivo .pdf."});
        mensajes.add(new String[]{"E", "No fue posible guardar el documento.",
            "Ocurrió un error al reemplazar el documento.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo guardar el documento.",
            "Ocurrió un error interno al guardar el documento.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo colocar la transacción bancaria como cancelada.",
            "Ocurrió un error al cancelar la transacción solicitada.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se puede usar el tipo de giro Roll on, debido a que no se han encontrado saldos a favor para la cuenta, moneda y proveedor actual.",
            "No se encontraron datos saldos en cuenta para poder usar.", "Verifique que posea saldos en la cuenta y de la moneda de la proforma."});
        mensajes.add(new String[]{"E", "Está intentando agregar un pago superior al valor de la proforma.",
            "El pago está superando el valor máximo de la proforma.", "Ingrese un valor acorde a lo que se debe."});
        mensajes.add(new String[]{"E", "Seleccione una cuenta para realizar la transacción.",
            "Se debe seleccionar la cuenta que se usara para la transacción.", "Seleccione una de las cuentas para continuar."});
        mensajes.add(new String[]{"E", "Seleccione un tipo de giro para realizar la transacción.",
            "Se debe seleccionar un tipo de giro de los disponibles.", "Seleccione un tipo de giro."});
        mensajes.add(new String[]{"E", "Ingrese un valor a girar para realizar la transacción.", "El usuario debe ingresar un valor para el giro.", "Ingrese el valor que quiere ingresar."});
        mensajes.add(new String[]{"E", "Ingrese una descripción para realizar la transacción.",
            "Se debe ingresar un comentario que justifique el motivo de la transacción.", "Ingrese el comentario para poder realizar la transacción."});
        mensajes.add(new String[]{"E", "No se pudo crear la transacción bancaria para la proforma.",
            "Ocurrió un error al crear la transacción bancaria.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "Debe seleccionar un banco para poder continuar.", "Se debe seleccionar un banco de los disponibles en la lista.", "Seleccione un banco."});
        mensajes.add(new String[]{"E", "Ingrese la razón social del banco.", "Se debe ingresar la razón social del banco.", "Ingrese una razón social."});
        mensajes.add(new String[]{"E", "No se pudo modificar el banco.", "Ocurrió un error al modificar el banco.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear el banco.", "Ocurrió un error al crear el banco.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se ha seleccionado la sucursal del banco para guardar.", "Se debe seleccionar una sucursal del banco.", "Seleccione una de las cuentas disponibles."});
        mensajes.add(new String[]{"E", "Ingrese una dirección para la sucursal, y de clic en Guardar.",
            "Se debe ingresar una dirección para asignarle esta al banco.", "Ingrese una dirección para continuar."});
        mensajes.add(new String[]{"E", "Ingrese un swift para la sucursal, y de clic en Guardar.", "Se debe ingresar el swift de la sucursal.", "Ingrese un swift para continuar."});
        mensajes.add(new String[]{"E", "Ingrese un país para la sucursal, y de clic en Guardar.", "No se ha seleccionado el país del sucursal.", "Seleccione un país para la sucursal."});
        mensajes.add(new String[]{"E", "Ingrese un estado para la sucursal, y de clic en Guardar.", "No se ha seleccionado un estado.", "Seleccione un estado para la sucursal."});
        mensajes.add(new String[]{"E", "Ingrese una ciudad para la sucursal, y de clic en Guardar.", "No se ha seleccionado la ciudad.", "Seleccione una ciudad para la sucursal."});
        mensajes.add(new String[]{"E", "No se pudo modificar la sucursal.", "Ocurrió un error al modificar la sucursal.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la sucursal.", "Ocurrió un error al crear la sucursal.", "Comuníquese con el departamento del sistema."});
        mensajes.add(new String[]{"E", "No ha seleccionado una cuenta para crear.", "Se debe seleccionar una cuenta.", "Seleccione una cuenta para continuar."});
        mensajes.add(new String[]{"E", "Ingrese una cuenta bancaria.", "No ha ingresado una cuenta del banco.", "Seleccione una de las cuentas disponibles."});
        mensajes.add(new String[]{"E", "No se pudo modificar la cuenta.", "Ocurrió un error al modificar la cuenta.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se pudo crear la cuenta bancaria.", "Ocurrió un error al crear la cuenta.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "La proforma ya se encuentra en un estado cerrado o aprobado.",
            "No se puede cerrar la proforma debido a que está en un estado que no lo permite.", "Verifique el estado de la proforma."});
        mensajes.add(new String[]{"E", "La proforma no se puede cerrar, debido a que esta tiene un error en el CBM con respecto a los contenedores asignados.",
            "Se presenta un error en el CBM, este está superando lo permitido por los contenedores.", "Verifique el total del CBM."});
        mensajes.add(new String[]{"E", "Verifique, un ítem tiene asignada una cantidad a los contenedores, superior a la que se va a pedir. Ítem: ?.",
            "La cantidad que se le esta asignando al contenedor es mayor a la que se está pidiendo.", "Verifique la cantidad total y la cantidad necesaria del ítem en el contenedor."});
        mensajes.add(new String[]{"E", "Verifique, un ítem tiene asignada una cantidad a los contenedores, inferior a la que se va a pedir. Ítem: ?.",
            "La cantidad que se le esta asignando al contenedor es menor a la que se está pidiendo.", "Verifique la cantidad total y la cantidad necesaria del ítem en el contenedor."});
        mensajes.add(new String[]{"E", "Verifique en la pestaña contenedores, a que contenedor se le esta asignando más de lo que soporta de CBM.",
            "Uno o varios de los contenedores para la proforma tienen asignado más de lo que soportan de CBM.", "Verifique los CBMs de los contenedores."});
        mensajes.add(new String[]{"E", "Ingrese los términos de pago para poder cerrar la proforma.", "No ha ingresado términos de pago para la proforma.", "Ingrese los términos de pago."});
        mensajes.add(new String[]{"E", "Ingrese los términos de entrega para poder cerrar la proforma.", "No ha ingresado términos de entrega para la proforma.", "Ingrese los términos de entrega."});
        mensajes.add(new String[]{"E", "Seleccione un puerto de salida para poder cerrar la proforma.", "No ha seleccionado el puerto de salida.", "Seleccione el puerto del país desde el que va a salir."});
        mensajes.add(new String[]{"E", "Seleccione un puerto de llegada para poder cerrar la proforma.", "No ha seleccionado el puerto de llegada.", "Seleccione el puerto de Colombia al que va a llegar."});
        mensajes.add(new String[]{"E", "Seleccione un incoterm para poder cerrar la proforma.",
            "No ha seleccionado el incoterm que se usara para la importación.", "Seleccione uno de los incoterms de la lista."});
        mensajes.add(new String[]{"E", "Ocurrió un error al cerrar la proforma actual, comuníquese con el departamento de sistemas.",
            "No se puede cerrar la proforma debido a un error ocurrido.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"E", "No se encontró una proforma seleccionada.", "No se ha seleccionado una proforma para trabajar.", "Seleccione una proforma de las disponibles."});
        mensajes.add(new String[]{"E", "No se encontró un contenedor seleccionado de la proforma [?].",
            "No se ha seleccionado un contenedor o los datos no fueron obtenidos.", "Inténtelo nuevamente y si el error persiste comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"A", "Se encontraron datos desactualizados, comuníquese con el departamento de sistemas.",
            "Los datos no coinciden.", "Comuníquese con el departamento de sistemas."});
        mensajes.add(new String[]{"I", "La fecha de embarque del contenedor, fue modificada correctamente.", "Fecha de embarque asignada", ""});
        mensajes.add(new String[]{"I", "La proforma está en estado pendiente por aprobación.", "La proforma está en estado PENDIENTE.", ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
