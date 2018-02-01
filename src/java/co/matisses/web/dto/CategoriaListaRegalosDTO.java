package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class CategoriaListaRegalosDTO {

    private Long idCategoria;
    private String nombre;
    private Integer valorMinimo;

    public CategoriaListaRegalosDTO() {
    }

    public CategoriaListaRegalosDTO(Long idCategoria, String nombre, Integer valorMinimo) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.valorMinimo = valorMinimo;
    }

    public Long getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(Long idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValorMinimo() {
        return valorMinimo;
    }

    public void setValorMinimo(Integer valorMinimo) {
        this.valorMinimo = valorMinimo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idCategoria);
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
        final CategoriaListaRegalosDTO other = (CategoriaListaRegalosDTO) obj;
        return Objects.equals(this.idCategoria, other.idCategoria);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
