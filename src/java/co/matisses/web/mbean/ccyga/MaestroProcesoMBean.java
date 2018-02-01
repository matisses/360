package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.entity.ProcesoProductoCcyga;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoProductoCcygaFacade;
import co.matisses.web.dto.ProcesoCcygaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author dbotero
 */
@ViewScoped
@Named(value = "maestroProcesoMBean")
public class MaestroProcesoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(MaestroProcesoMBean.class.getSimpleName());
    private Integer idProceso = null;
    private String nombre;
    private String permiteSimultaneos;
    private String requiereProducto;
    private List<ProcesoCcygaDTO> procesos;
    @EJB
    private ProcesoCcygaFacade procesoFacade;
    @EJB
    private ProcesoProductoCcygaFacade procesoProductoFacade;

    public MaestroProcesoMBean() {
        procesos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        List<ProcesoCcyga> entidades = procesoFacade.findAll();
        procesos = new ArrayList<>();
        for (ProcesoCcyga entidad : entidades) {
            ProcesoCcygaDTO dto = new ProcesoCcygaDTO(entidad.getIdProceso(), entidad.getNombre(), entidad.getPermiteSimultaneos(), entidad.getRequiereProducto());
            procesos.add(dto);
        }
        Collections.sort(procesos);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPermiteSimultaneos() {
        return permiteSimultaneos;
    }

    public void setPermiteSimultaneos(String permiteSimultaneos) {
        this.permiteSimultaneos = permiteSimultaneos;
    }

    public String getSimultaneosSeleccionado() {
        if (permiteSimultaneos != null && !permiteSimultaneos.isEmpty()) {
            switch (permiteSimultaneos) {
                case "1":
                    return "Sí";
                case "0":
                    return "No";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public String getRequiereProducto() {
        return requiereProducto;
    }

    public void setRequiereProducto(String requiereProducto) {
        this.requiereProducto = requiereProducto;
    }

    public String getRequiereSeleccionado() {
        if (requiereProducto != null && !requiereProducto.isEmpty()) {
            switch (requiereProducto) {
                case "1":
                    return "Sí";
                case "0":
                    return "No";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public List<ProcesoCcygaDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoCcygaDTO> procesos) {
        this.procesos = procesos;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public void seleccionarPermitirSimultaneos() {
        permiteSimultaneos = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("permiteSimultaneos");
        log.log(Level.INFO, "Se selecciono en el combo de permitir simultaneos como [{0}]", permiteSimultaneos);
    }

    public void seleccionarRequiereProducto() {
        requiereProducto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("requiereProducto");
        log.log(Level.INFO, "Se selecciono en el combo de requiere producto como [{0}]", requiereProducto);
    }

    public void guardar() {
        log.log(Level.INFO, "Gestionando proceso");
        if (nombre == null || nombre.trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para poder crear el proceso.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un nombre para poder crear el proceso");
            return;
        }
        if (nombre.length() > 12) {
            mostrarMensaje("Error", "El nombre no debe superar los 12 caracteres, corrija el nombre y continue.", true, false, false);
            log.log(Level.SEVERE, "El nombre no debe superar los 12 caracteres, corrija el nombre y continue");
            return;
        }
        if (permiteSimultaneos == null || permiteSimultaneos.isEmpty()) {
            mostrarMensaje("Error", "Seleccione si el proceso permite o no simultáneos.", true, false, false);
            log.log(Level.SEVERE, "Seleccione si el proceso permite o no simultaneos");
            return;
        }
        if (requiereProducto == null || requiereProducto.isEmpty()) {
            mostrarMensaje("Error", "Seleccione si el proceso requiere o no productos.", true, false, false);
            log.log(Level.SEVERE, "Seleccione si el proceso requiere o no productos");
            return;
        }

        ProcesoCcyga entidad = new ProcesoCcyga();
        entidad.setNombre(nombre);
        entidad.setPermiteSimultaneos(permiteSimultaneos.equals("1") ? true : false);
        entidad.setRequiereProducto(requiereProducto.equals("1") ? true : false);
        if (idProceso == null) {
            log.log(Level.INFO, "Creando proceso con nombre [{0}]", nombre);
            procesoFacade.create(entidad);
        } else {
            log.log(Level.INFO, "Modificando proceso con id [{0}]", idProceso);
            entidad.setIdProceso(idProceso);
            procesoFacade.edit(entidad);
        }
        initialize();
        limpiar();
    }

    public void limpiar() {
        idProceso = null;
        nombre = null;
        permiteSimultaneos = null;
        requiereProducto = null;
    }

    public void seleccionarProceso() {
        log.log(Level.INFO, "Seleccionando proceso para modificar");
        Integer idSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProceso"));
        if (idProceso != null && idProceso.equals(idSeleccionado)) {
            limpiar();
        } else {
            idProceso = idSeleccionado;
            nombre = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombre");
            requiereProducto = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("producto")) ? "1" : "0";
            permiteSimultaneos = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("simultaneos")) ? "1" : "0";
        }
    }

    public void nombreCambio() {
        if (nombre == null || nombre.trim().isEmpty()) {
            if (idProceso != null) {
                ProcesoCcyga entidad = procesoFacade.find(idProceso);
                nombre = entidad.getNombre();
            }
            return;
        }
        if (nombre.length() > 12) {
            //mostrar error
            return;
        }
        if (idProceso != null) {
            ProcesoCcyga entidad = procesoFacade.find(idProceso);
            entidad.setNombre(nombre);
            procesoFacade.edit(entidad);
            log.log(Level.INFO, "Se modifico el nombre del proceso con id={0}", idProceso);
        } else {
            ProcesoCcyga entidad = new ProcesoCcyga();
            entidad.setNombre(nombre);
            entidad.setRequiereProducto(requiereProducto.equals("1"));
            entidad.setPermiteSimultaneos(permiteSimultaneos.equals("1"));
            procesoFacade.create(entidad);
            idProceso = entidad.getIdProceso();
            log.log(Level.INFO, "Se creo el proceso con id={0}", idProceso);
        }
        initialize();
    }

    public void simultaneosCambio() {
        if (idProceso != null) {
            ProcesoCcyga entidad = procesoFacade.find(idProceso);
            entidad.setPermiteSimultaneos(permiteSimultaneos.equals("1"));
            procesoFacade.edit(entidad);
            log.log(Level.INFO, "Se modifico el valor del atributo [permiteSimultaneos] del proceso con id={0}", idProceso);
            initialize();
        }
    }

    public void productoCambio() {
        if (idProceso != null) {
            ProcesoProductoCcyga entidadProcesoProducto = procesoProductoFacade.buscarPorProcesoyProducto(idProceso, null);
            if (requiereProducto.equals("0")) {
                //Si el proceso no requiere producto, se debe insertar un registro "proceso-producto"
                //para que las labores lo utilicen para el registro de tiempo
                if (entidadProcesoProducto == null) {
                    ProcesoCcyga entidadProceso = procesoFacade.find(idProceso);
                    entidadProcesoProducto = new ProcesoProductoCcyga();
                    entidadProcesoProducto.setComentarios(entidadProceso.getNombre());
                    entidadProcesoProducto.setEstado("PE");
                    entidadProcesoProducto.setIdProceso(entidadProceso);
                    entidadProcesoProducto.setIdProducto(null);
                    procesoProductoFacade.create(entidadProcesoProducto);
                }
            } else //Si el proceso requiere producto, se debe buscar si existe un registro "proceso-producto"
            //generico y eliminarlo
            {
                if (entidadProcesoProducto != null) {
                    procesoProductoFacade.remove(entidadProcesoProducto);
                }
            }
            ProcesoCcyga entidadProceso = procesoFacade.find(idProceso);
            entidadProceso.setRequiereProducto(requiereProducto.equals("1"));
            procesoFacade.edit(entidadProceso);
            log.log(Level.INFO, "Se modifico el valor del atributo [requiereProducto] del proceso con id={0}", idProceso);
            initialize();
        }
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
