package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class TipoDocumentoDTO implements Comparable<TipoDocumentoDTO> {

    private String codigo;
    private String nombre;

    public TipoDocumentoDTO() {
    }

    public TipoDocumentoDTO(String codigo, String nombre) {
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
        hash = 97 * hash + Objects.hashCode(this.codigo);
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
        final TipoDocumentoDTO other = (TipoDocumentoDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(TipoDocumentoDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

}
