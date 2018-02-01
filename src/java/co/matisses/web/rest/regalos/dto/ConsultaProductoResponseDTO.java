package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class ConsultaProductoResponseDTO {

    private int cantidad;
    private String referencia;
    private String nombre;
    private int precio;

    public ConsultaProductoResponseDTO() {
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
