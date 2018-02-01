package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ProcesoCcygaDTO implements Comparable<ProcesoCcygaDTO> {

    private Integer idProceso;
    private String nombre;
    private String comentario;
    private Integer idProcesoProducto;
    private Integer laboresActivas;
    private Boolean permiteSimultaneos;
    private Boolean requiereProducto;
    private List<String> empleadosLabor;

    public ProcesoCcygaDTO() {
        empleadosLabor = new ArrayList<>();
    }

    public ProcesoCcygaDTO(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public ProcesoCcygaDTO(Integer idProceso, String nombre) {
        this.idProceso = idProceso;
        this.nombre = nombre;
    }

    public ProcesoCcygaDTO(Integer idProceso, String nombre, Boolean permiteSimultaneos, Boolean requiereProducto) {
        this.idProceso = idProceso;
        this.nombre = nombre;
        this.permiteSimultaneos = permiteSimultaneos;
        this.requiereProducto = requiereProducto;
    }

    public ProcesoCcygaDTO(Integer idProceso, String nombre, Boolean permiteSimultaneos, Boolean requiereProducto, Integer laboresActivas) {
        this.idProceso = idProceso;
        this.nombre = nombre;
        this.permiteSimultaneos = permiteSimultaneos;
        this.requiereProducto = requiereProducto;
        this.laboresActivas = laboresActivas;
    }

    public ProcesoCcygaDTO(Integer idProceso, String nombre, String comentario, Integer idProcesoProducto, Integer laboresActivas,
            Boolean permiteSimultaneos, Boolean requiereProducto) {
        this.idProceso = idProceso;
        this.nombre = nombre;
        this.comentario = comentario;
        this.idProcesoProducto = idProcesoProducto;
        this.laboresActivas = laboresActivas;
        this.permiteSimultaneos = permiteSimultaneos;
        this.requiereProducto = requiereProducto;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Boolean getPermiteSimultaneos() {
        return permiteSimultaneos;
    }

    public void setPermiteSimultaneos(Boolean permiteSimultaneos) {
        this.permiteSimultaneos = permiteSimultaneos;
    }

    public Boolean getRequiereProducto() {
        return requiereProducto;
    }

    public void setRequiereProducto(Boolean requiereProducto) {
        this.requiereProducto = requiereProducto;
    }

    public int getLaboresActivas() {
        return laboresActivas;
    }

    public void setLaboresActivas(Integer laboresActivas) {
        this.laboresActivas = laboresActivas;
    }

    public Integer getIdProcesoProducto() {
        return idProcesoProducto;
    }

    public void setIdProcesoProducto(Integer idProcesoProducto) {
        this.idProcesoProducto = idProcesoProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<String> getEmpleadosLabor() {
        return empleadosLabor;
    }

    public void setEmpleadosLabor(List<String> empleadosLabor) {
        this.empleadosLabor = empleadosLabor;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idProceso);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProcesoCcygaDTO other = (ProcesoCcygaDTO) obj;
        if (!Objects.equals(this.idProceso, other.idProceso)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(ProcesoCcygaDTO o) {
        return nombre.compareTo(o.getNombre());
    }
}
