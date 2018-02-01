package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class RegimenTributarioDTO implements Comparable<RegimenTributarioDTO> {

    private String codigo;
    private String nombre;

    public RegimenTributarioDTO() {
    }

    public RegimenTributarioDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.codigo);
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
        final RegimenTributarioDTO other = (RegimenTributarioDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(RegimenTributarioDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

}
