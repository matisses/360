package co.matisses.web.ayuda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;

/**
 *
 * @author jguisao
 */
@ViewScoped
@Named(value = "registarCompraListaRegaloMBean")
public class RegistarCompraListaRegaloMBean implements Serializable {

    private static final Logger log = Logger.getLogger(RegistarCompraListaRegaloMBean.class.getSimpleName());
    private List<String[]> mensaje;

    public RegistarCompraListaRegaloMBean() {
        mensaje = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        mensaje.add(new String[]{"E", "Debe ingresar un valor en el parámetro de búsqueda para poder continuar.",
            "No ingreso el primer parámetro de búsqueda.",
            "Como primer parámetro de búsqueda, ingrese el número de factura de venta completo."});
        mensaje.add(new String[]{"E", "No se encontraron datos coincidentes con la búsqueda.",
            "La factura de venta no contiene productos coincidentes la lista de regalo.",
            "Verifique que la factura ingresada si corresponda a la del cliente o que este mal ingresada."});
        mensaje.add(new String[]{"E", "Debe ingresar un número de factura.",
            "Ingrese la factura de venta completa.",
            "Verifique que la factura ingresada si corresponda a la del cliente o que este mal ingresada."});
        mensaje.add(new String[]{"E", "Debe ingresar una lista de regalos.",
            "No ingreso el segundo parámetro de búsqueda.",
            "Como segundo parámetro de búsqueda, ingrese ya sea nombre de la lista de regalo, código, nombre del creador o cocreador."});
        mensaje.add(new String[]{"E", "No se encontraron ítems para la lista ingresada.",
            "La lista de regalo seleccionada no contiene productos.",
            "Verifique que la lista de regalo seleccionada corresponda a la del cliente."});
        mensaje.add(new String[]{"E", "No se encontraron datos para la factura y lista ingresadas.",
            "La lista de regalo y factura de venta seleccionada no contiene productos coincidentes.",
            "Verifique que la factura de venta y la lista de regalo seleccionada correspondan a la del cliente o este mal ingresado."});
        mensaje.add(new String[]{"E", "No se encontraron datos para guardar.",
            "No se seleccionó ningún producto para poder guardar.",
            "Seleccione algún producto coincidente con la lista de regalo."});
    }

    public List<String[]> getMensaje() {
        return mensaje;
    }

    public void setMensaje(List<String[]> mensaje) {
        this.mensaje = mensaje;
    }
}
