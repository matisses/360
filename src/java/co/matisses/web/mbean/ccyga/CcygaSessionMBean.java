package co.matisses.web.mbean.ccyga;

import co.matisses.web.dto.LaborCcygaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import javax.enterprise.context.SessionScoped;

import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@SessionScoped
@Named(value = "ccygaSessionMBean")
public class CcygaSessionMBean implements Serializable {

    private Integer idProcesoSeleccionado;
    private Integer idProductoSeleccionado;
    private String codigoRevisado;
    private String nombreEmpleado;
    private String nombreProceso;
    private Stack<String> atras = new Stack<>();
    private List<LaborCcygaDTO> labores = null;

    public CcygaSessionMBean() {
    }

    public Integer getIdProcesoSeleccionado() {
        return idProcesoSeleccionado;
    }

    public void setIdProcesoSeleccionado(Integer idProcesoSeleccionado) {
        this.idProcesoSeleccionado = idProcesoSeleccionado;
    }

    public void agregarPaso(String paso) {
        atras.push(paso);
    }

    public String getCodigoRevisado() {
        return codigoRevisado;
    }

    public void setCodigoRevisado(String codigoRevisado) {
        this.codigoRevisado = codigoRevisado;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public Integer getIdProductoSeleccionado() {
        return idProductoSeleccionado;
    }

    public void setIdProductoSeleccionado(Integer idProductoSeleccionado) {
        this.idProductoSeleccionado = idProductoSeleccionado;
    }

    public List<LaborCcygaDTO> getLabores() {
        return labores;
    }

    public void setLabores(List<LaborCcygaDTO> labores) {
        this.labores = labores;
    }

    public void limpiar() {
        this.codigoRevisado = null;
        this.idProcesoSeleccionado = null;
        this.idProductoSeleccionado = null;
        this.nombreEmpleado = null;
        this.nombreProceso = null;
        this.labores = new ArrayList<>();
        this.atras = new Stack<>();
    }

    public String atras() {
        if (atras.isEmpty()) {
            return null;
        }
        return atras.pop();
    }

    public String empleado() {
        limpiar();
        return "empleado";
    }
}
