package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class TipoSolicitudNextDTO implements Comparable<TipoSolicitudNextDTO>{

    private Integer idTipoSolicitud;
    private String nombre;

    public TipoSolicitudNextDTO() {
    }

    public TipoSolicitudNextDTO(Integer idTipoSolicitud, String nombre) {
        this.idTipoSolicitud = idTipoSolicitud;
        this.nombre = nombre;
    }

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

    @Override
    public int compareTo(TipoSolicitudNextDTO o) {
        return this.getNombre().compareTo(o.getNombre());
    }

}
