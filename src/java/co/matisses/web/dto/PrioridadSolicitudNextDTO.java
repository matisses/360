package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class PrioridadSolicitudNextDTO implements Comparable<PrioridadSolicitudNextDTO> {

    private Integer idPrioridad;
    private String nombre;

    public PrioridadSolicitudNextDTO() {
    }

    public PrioridadSolicitudNextDTO(Integer idPrioridad, String nombre) {
        this.idPrioridad = idPrioridad;
        this.nombre = nombre;
    }

    public Integer getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Integer idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(PrioridadSolicitudNextDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
