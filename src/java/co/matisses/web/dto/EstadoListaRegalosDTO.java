package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class EstadoListaRegalosDTO {

    private Long idEstado;
    private String nombre;

    public EstadoListaRegalosDTO() {
    }

    public EstadoListaRegalosDTO(Long idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public Long getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Long idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.idEstado);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EstadoListaRegalosDTO other = (EstadoListaRegalosDTO) obj;
        if (!Objects.equals(this.idEstado, other.idEstado)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
