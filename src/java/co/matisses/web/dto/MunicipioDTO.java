package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class MunicipioDTO implements Comparable<MunicipioDTO> {

    private String codigo;
    private String nombre;

    public MunicipioDTO() {
    }

    public MunicipioDTO(String codigo, String nombre) {
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
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.codigo);
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
        final MunicipioDTO other = (MunicipioDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MunicipioDTO{" + "codigo=" + codigo + ", nombre=" + nombre + '}';
    }

    @Override
    public int compareTo(MunicipioDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
