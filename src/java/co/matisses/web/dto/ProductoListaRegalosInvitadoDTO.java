package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoListaRegalosInvitadoDTO {

    private String referencia;
    private String nombre;
    private String descripcion;
    private Long idProductoLista;
    private Integer cantidad;
    private Integer precio;
    private Integer impuesto;

    public ProductoListaRegalosInvitadoDTO() {
    }

    public Long getIdProductoLista() {
        return idProductoLista;
    }

    public void setIdProductoLista(Long idProductoLista) {
        this.idProductoLista = idProductoLista;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + Objects.hashCode(this.idProductoLista);
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
        final ProductoListaRegalosInvitadoDTO other = (ProductoListaRegalosInvitadoDTO) obj;
        return Objects.equals(this.idProductoLista, other.idProductoLista);
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
