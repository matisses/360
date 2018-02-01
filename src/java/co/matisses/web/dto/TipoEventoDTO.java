package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class TipoEventoDTO {

    private Long idTipoEvento;
    private String nombre;

    public TipoEventoDTO() {
    }

    public TipoEventoDTO(Long idTipoEvento, String nombre) {
        this.idTipoEvento = idTipoEvento;
        this.nombre = nombre;
    }

    public Long getIdTipoEvento() {
        return idTipoEvento;
    }

    public void setIdTipoEvento(Long idTipoEvento) {
        this.idTipoEvento = idTipoEvento;
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
        hash = 41 * hash + Objects.hashCode(this.idTipoEvento);
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
        final TipoEventoDTO other = (TipoEventoDTO) obj;
        return Objects.equals(this.idTipoEvento, other.idTipoEvento);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
