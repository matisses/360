package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class EquipoNextDTO implements Comparable<EquipoNextDTO> {

    private Integer idEquipo;
    private String nombre;
    private String nombreDirectorioActivo;

    public EquipoNextDTO() {
    }

    public EquipoNextDTO(Integer idEquipo, String nombre, String nombreDirectorioActivo) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.nombreDirectorioActivo = nombreDirectorioActivo;
    }

    public Integer getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(Integer idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombreDirectorioActivo() {
        return nombreDirectorioActivo;
    }

    public void setNombreDirectorioActivo(String nombreDirectorioActivo) {
        this.nombreDirectorioActivo = nombreDirectorioActivo;
    }

    @Override
    public int compareTo(EquipoNextDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
