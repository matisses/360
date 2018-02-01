package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class DetalleFacturaSAPDTO {

    private String referencia;
    private String descripcion;
    private String almacen;
    private Integer cantidad;
    private Integer precio;
    private String estado;

    public DetalleFacturaSAPDTO() {
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

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.referencia);
        hash = 19 * hash + Objects.hashCode(this.almacen);
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
        final DetalleFacturaSAPDTO other = (DetalleFacturaSAPDTO) obj;
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        if (!Objects.equals(this.almacen, other.almacen)) {
            return false;
        }
        return true;
    }

}
