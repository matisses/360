package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.Contenedor;
import co.matisses.persistence.web.entity.ContenedorProveedor;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.facade.ContenedorFacade;
import co.matisses.persistence.web.facade.ContenedorProveedorFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.web.dto.ContenedorDTO;
import co.matisses.web.dto.ContenedorProveedorDTO;
import co.matisses.web.dto.DatosProveedorDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "contenedoresProveedorMBean")
public class ContenedoresProveedorMBean implements Serializable {
    
    private static final Logger log = Logger.getLogger(ContenedoresProveedorMBean.class.getSimpleName());
    private Integer idContenedor;
    private Integer idContenedorProveedor;
    private String parametroBusqueda;
    private DatosProveedorDTO proveedor;
    private List<ContenedorDTO> contenedores;
    private List<ContenedorProveedorDTO> contenedoresProveedor;
    @EJB
    private ContenedorFacade contenedorFacade;
    @EJB
    private ContenedorProveedorFacade contenedorProveedorFacade;
    @EJB
    private DatosProveedorFacade datosProveedorFacade;
    
    public ContenedoresProveedorMBean() {
        proveedor = new DatosProveedorDTO();
        contenedores = new ArrayList<>();
        contenedoresProveedor = new ArrayList<>();
    }
    
    @PostConstruct
    protected void initialize() {
        cargarContenedores();
    }
    
    public String getParametroBusqueda() {
        return parametroBusqueda;
    }
    
    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }
    
    public Integer getIdContenedor() {
        return idContenedor;
    }
    
    public String getIdContenedorSeleccionado() {
        if (idContenedor != null && idContenedor != 0) {
            for (ContenedorDTO c : contenedores) {
                if (c.getIdContenedor().equals(idContenedor)) {
                    return c.getNombre();
                }
            }
        }
        return "Seleccione";
    }
    
    public DatosProveedorDTO getProveedor() {
        return proveedor;
    }
    
    public void setProveedor(DatosProveedorDTO proveedor) {
        this.proveedor = proveedor;
    }
    
    public List<ContenedorDTO> getContenedores() {
        return contenedores;
    }
    
    public void setContenedores(List<ContenedorDTO> contenedores) {
        this.contenedores = contenedores;
    }
    
    public List<ContenedorProveedorDTO> getContenedoresProveedor() {
        return contenedoresProveedor;
    }
    
    public void setContenedoresProveedor(List<ContenedorProveedorDTO> contenedoresProveedor) {
        this.contenedoresProveedor = contenedoresProveedor;
    }
    
    public Integer getIdContenedorProveedor() {
        return idContenedorProveedor;
    }
    
    public void setIdContenedorProveedor(Integer idContenedorProveedor) {
        this.idContenedorProveedor = idContenedorProveedor;
    }
    
    public void buscarProveedor() {
        idContenedor = 0;
        contenedoresProveedor.clear();
        if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
            mostrarMensaje("Error", "Verifique que haya ingresado un valor en el campo Código Proveedor.", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Codigo Proveedor");
            return;
        }
        DatosProveedor dato = datosProveedorFacade.find(parametroBusqueda);
        if (dato != null && dato.getCodProveedor() != null && !dato.getCodProveedor().isEmpty()) {
            List<ContenedorProveedor> conts = contenedorProveedorFacade.consultarXIdProveedor(parametroBusqueda);
            for (ContenedorProveedor c : conts) {
                ContenedorProveedorDTO dto = new ContenedorProveedorDTO();
                dto.setIdContenedor(c.getIdContenedor().getIdContenedor());
                dto.setIdContenedorProveedor(c.getIdContenedorProveedor());
                contenedoresProveedor.add(dto);
            }
            Collections.sort(contenedoresProveedor, new Comparator<ContenedorProveedorDTO>() {
                @Override
                public int compare(ContenedorProveedorDTO o1, ContenedorProveedorDTO o2) {
                    return nombreContenedor(o1.getIdContenedor()).compareTo(nombreContenedor(o2.getIdContenedor()));
                }
            });
        } else {
            mostrarMensaje("Error", "No sé a encontrado el proveedor ingresado.", true, false, false);
            log.log(Level.SEVERE, "No se a encontrado el proveedor ingresado");
            return;
        }
    }
    
    public void seleccionarContenedor() {
        idContenedor = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idContenedor"));
        log.log(Level.INFO, "Se selecciono el contenedor con id [{0}]", idContenedor);
    }
    
    private void cargarContenedores() {
        contenedores.clear();
        List<Contenedor> conts = contenedorFacade.findAll();
        for (Contenedor c : conts) {
            ContenedorDTO dto = new ContenedorDTO();
            dto.setCapacidadCubica(c.getCapacidadCubica());
            dto.setCargaMaxima(c.getCargaMaxima());
            dto.setIdContenedor(c.getIdContenedor());
            dto.setNombre(c.getNombre());
            contenedores.add(dto);
        }
        Collections.sort(contenedores, new Comparator<ContenedorDTO>() {
            @Override
            public int compare(ContenedorDTO o1, ContenedorDTO o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
    }
    
    public String nombreContenedor(Integer idContenedor) {
        String name = "";
        Contenedor con = contenedorFacade.find(idContenedor);
        if (con != null && con.getIdContenedor() != null && con.getIdContenedor() != 0) {
            name = con.getNombre();
        }
        return name;
    }
    
    public void agregarContenedorAProveedor() {
        if (idContenedor == null || idContenedor == 0) {
            mostrarMensaje("Error", "Se debe seleccionar un contenedor para continuar.", true, false, false);
            log.log(Level.SEVERE, "Se debe seleccionar un contenedor para continuar");
            return;
        }
        for (ContenedorProveedorDTO dto : contenedoresProveedor) {
            if (dto.getIdContenedor().equals(idContenedor)) {
                mostrarMensaje("Error", "El contenedor seleccionado, ya fue agregado a este proveedor.", true, false, false);
                log.log(Level.SEVERE, "El contenedor seleccionado, ya fue agregado a este proveedor");
                return;
            }
        }
        ContenedorProveedor cp = new ContenedorProveedor();
        cp.setCodigoProveedor(parametroBusqueda);
        cp.setIdContenedor(new Contenedor(idContenedor));
        
        try {
            contenedorProveedorFacade.create(cp);
            buscarProveedor();
            idContenedor = 0;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }
    
    public void eliminarContenedorAProveedor() {
        idContenedorProveedor = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idContenedorProveedor"));
        if (idContenedorProveedor == null || idContenedorProveedor == 0) {
            mostrarMensaje("Error", "Se le debe dar eliminar al contenedor a eliminar.", true, false, false);
            log.log(Level.SEVERE, "Se le debe dar eliminar a el contenedor a eliminar");
            return;
        }
        for (int i = 0; i < contenedoresProveedor.size(); i++) {
            if (contenedoresProveedor.get(i).getIdContenedorProveedor().equals(idContenedorProveedor)) {
                try {
                    contenedorProveedorFacade.eliminarManual(idContenedorProveedor);
                    i--;
                    mostrarMensaje("Éxito", "Se eliminó el contenedor para el proveedor correctamente.", false, true, false);
                    log.log(Level.INFO, "Se elimino el contenedor para el proveedor correctamente");
                    buscarProveedor();
                    idContenedorProveedor = 0;
                    break;
                } catch (Exception e) {
                    log.log(Level.SEVERE, "El contenedor indicado no se puede eliminar debido a que puede estar en uso. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "El contenedor indicado no se puede eliminar debido a que puede estar en uso.", true, false, false);
                    return;
                }
            }
        }
    }
    
    public void limpiar() {
        idContenedor = 0;
        idContenedorProveedor = 0;
        parametroBusqueda = "";
        proveedor = new DatosProveedorDTO();
        contenedoresProveedor = new ArrayList<>();
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
