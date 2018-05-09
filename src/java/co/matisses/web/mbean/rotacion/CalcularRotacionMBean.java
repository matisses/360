package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.TiempoRotacionClient;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "calcularRotacionMBean")
public class CalcularRotacionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(CalcularRotacionMBean.class.getSimpleName());
    private String referencia;
    private String registro;
    private List<String> referencias;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;

    public CalcularRotacionMBean() {
        referencias = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public List<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<String> referencias) {
        this.referencias = referencias;
    }

    public void agregarReferencia() {
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar la referencia que desea agregar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar la referencia que desea agregar");
            return;
        }

        if (referencia.length() < 20 && referencia.endsWith("*")) {
            String proveedor = referencia.substring(0, 3);
            CONSOLE.log(Level.INFO, "Buscando referencias para el proveedor [{0}]", proveedor);

            List<String> items = itemInventarioFacade.consultarReferenciasProveedor(proveedor);

            for (String item : items) {
                if (!validarReferencia(item)) {
                    referencias.add(item);
                }
            }
        } else {
            referencia = genericMBean.completarReferencia(referencia);

            ItemInventario item = itemInventarioFacade.getItemBasicInfo(referencia);

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                if (!validarReferencia(referencia)) {
                    referencias.add(0, referencia);
                }
            } else {
                mostrarMensaje("Error", "La referencia ingresada no existe.", true, false, false);
                CONSOLE.log(Level.SEVERE, "La referencia ingresada no existe");
                return;
            }
        }

        referencia = null;
    }

    private boolean validarReferencia(String ref) {
        for (String r : referencias) {
            if (r.equals(ref)) {
                return true;
            }
        }

        return false;
    }

    public void eliminarReferencia() {
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        referencias.remove(item);
    }

    public void calcularRotacion() {
        TiempoRotacionClient client = new TiempoRotacionClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        registro = client.calcularRotacion(sessionInfoMBean.validarPermisoUsuario(Objetos.LOG_CALCULO_ROTACIÓN, Acciones.VISUALIZAR), referencias);
    }

    public void limpiar() {
        referencia = null;
        registro = null;
        referencias = new ArrayList<>();
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
