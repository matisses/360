package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ProveedoresExterior;
import co.matisses.persistence.web.facade.ProveedoresExteriorFacade;
import co.matisses.web.dto.ProveedoresExteriorDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
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
@Named(value = "proveedoresMBean")
public class ProveedoresMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger CONSOLE = Logger.getLogger(ProveedoresMBean.class.getSimpleName());
    private String proveedor;
    private String nombre;
    private List<ProveedoresExteriorDTO> proveedores;
    @EJB
    private ProveedoresExteriorFacade proveedoresExteriorFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;

    public ProveedoresMBean() {
        proveedores = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarProveedores();
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<ProveedoresExteriorDTO> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<ProveedoresExteriorDTO> proveedores) {
        this.proveedores = proveedores;
    }

    private void cargarProveedores() {
        proveedores = new ArrayList<>();

        List<String> providersSAP = itemInventarioFacade.obtenerProveedores();
        List<ProveedoresExterior> providers = proveedoresExteriorFacade.findAll();

        for (String s : providersSAP) {
            boolean existe = false;

            for (ProveedoresExterior p : providers) {
                if (p.getProveedor().equals(s.replace("PE", ""))) {
                    ProveedoresExteriorDTO dto = new ProveedoresExteriorDTO();

                    dto.setComplemento(p.getComplemento() != null ? p.getComplemento() : false);
                    dto.setConsignacion(p.getConsignacion() != null ? p.getConsignacion() : false);
                    dto.setNombreProveedor(p.getNombreProveedor());
                    dto.setProveedor(p.getProveedor());

                    proveedores.add(dto);
                    existe = true;
                    break;
                } else {
                    existe = false;
                }
            }

            if (!existe) {
                ProveedoresExteriorDTO dto = new ProveedoresExteriorDTO();

                dto.setComplemento(false);
                dto.setConsignacion(false);
                dto.setNombreProveedor("SIN NOMBRE");
                dto.setProveedor(s.replace("PE", ""));

                proveedores.add(dto);
            }
        }
    }

    public void guardar() {
        if (proveedor == null || proveedor.isEmpty()) {
            mostrarMensaje("Error", "No se encontró el código del proveedor.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontro el codigo del proveedor");
            return;
        }
        if (nombre == null || nombre.isEmpty() || nombre.equals("SIN NOMBRE")) {
            mostrarMensaje("Error", "Asígnele un nombre al proveedor.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Asignele un nombre al proveedor");
            return;
        }

        ProveedoresExterior provider = proveedoresExteriorFacade.find(proveedor);

        if (provider != null && provider.getProveedor() != null && !provider.getProveedor().isEmpty()) {
            provider.setNombreProveedor(nombre);
        } else {
            provider = new ProveedoresExterior();

            provider.setProveedor(proveedor);
            provider.setNombreProveedor(nombre);
        }

        try {
            proveedoresExteriorFacade.edit(provider);
            cargarProveedores();
            limpiar();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al procesar los datos del proveedor. ", e);
        }
    }

    public void editarProveedor() {
        proveedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("proveedor");
        nombre = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombre");
    }

    public void limpiar() {
        proveedor = null;
        nombre = null;
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
