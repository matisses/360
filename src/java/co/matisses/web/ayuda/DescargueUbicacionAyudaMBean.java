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
@Named(value = "descargueUbicacionAyudaMBean")
public class DescargueUbicacionAyudaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ColumnasProformaAyudaMBean.class.getSimpleName());
    private List<String[]> mensajes;

    public DescargueUbicacionAyudaMBean() {
        mensajes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensajes.add(new String[]{"E", "La ubicación ingresada se encuentra inactiva.",
            "La ubicación digitada se encontró inactiva en el almacén.",
            "Habilite o digite una ubicación nueva."});
        mensajes.add(new String[]{"E", "La ubicación ingresada no existe para el almacén.",
            "La ubicación que se ingresó no existe en el almacén.",
            "Ingrese una ubicación válida para el almacén."});
        mensajes.add(new String[]{"E", "No se encontraron ubicaciones para el almacén.",
            "El almacén en el que se inició sesión, no posee ubicaciones.",
            "Cámbiese a un almacén que posea ubicaciones para poder continuar."});
        mensajes.add(new String[]{"E", "Se debe ingresar una ubicación para continuar.",
            "Se dio clic en agregar, sin ingresar la ubicación a la que se quiere mover.",
            "Ingrese una ubicación para poder continuar."});
        mensajes.add(new String[]{"E", "Se debe ingresar una referencia para poder continuar.",
            "Se dio clic en agregar, sin una referencia que agregar.",
            "Ingrese una referencia para poder continuar."});
        mensajes.add(new String[]{"E", "Se debe ingresar una referencia de 20 caracteres para poder continuar.",
            "No se ingresó correctamente la referencia.",
            "Ingrese de forma adecuada la referencia para poder agregarla."});
        mensajes.add(new String[]{"E", "Ingrese el número de la factura.",
            "Se debe ingresar el número de la factura para movimiento de mercancía de clientes.",
            "Ingrese la factura y continúe con el proceso."});
        mensajes.add(new String[]{"E", "No se encontró saldo de la referencia ####################, para descargar.",
            "La referencia ingresada no se encontró en el sistema.",
            "Verifique la referencia ingresada y continúe."});
        mensajes.add(new String[]{"E", "No se encontró saldo de la referencia ####################, en la ubicación ####.",
            "La referencia que se ingresó no fue encontrada en la ubicación ingresada por el usuario.",
            "Ingrese una nueva referencia o valide la referencia actual."});
        mensajes.add(new String[]{"E", "Ocurrió un error al crear el traslado de ubicación.",
            "Ocurrió un error al hacer el movimiento.",
            "Inténtelo nuevamente y comuníquese con el departamento de sistemas si el error persiste."});
        mensajes.add(new String[]{"E", "El usuario no tiene permiso para usar este módulo.",
            "Al usuario no se le han suministrado privilegios para realizar acciones en el módulo de cargue.",
            "Comuníquese con su jefe inmediato, para que este pida los permisos a sistemas."});
        mensajes.add(new String[]{"I", "Se creó el descargue correctamente.",
            "El movimiento fue creado exitosamente.",
            ""});
    }

    public List<String[]> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<String[]> mensajes) {
        this.mensajes = mensajes;
    }
}
