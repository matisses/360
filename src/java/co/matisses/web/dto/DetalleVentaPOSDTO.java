package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class DetalleVentaPOSDTO {

    private Double descuento;
    private String referencia;
    private String descripcion;
    private String refProveedor;
    private Integer cantidad;
    private Integer precio;
    private Integer impuesto;
    private List<UbicacionDetalleVentaPOSDTO> ubicaciones;

    public DetalleVentaPOSDTO() {
        ubicaciones = new ArrayList<>();
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
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

    public String getRefProveedor() {
        return refProveedor;
    }

    public void setRefProveedor(String refProveedor) {
        this.refProveedor = refProveedor;
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

    public List<UbicacionDetalleVentaPOSDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<UbicacionDetalleVentaPOSDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public Integer getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(Integer impuesto) {
        this.impuesto = impuesto;
    }

    @Override
    public String toString() {
        return "DetalleVentaPOSDTO{" + "referencia=" + referencia + ", descripcion=" + descripcion + ", refProveedor=" + refProveedor + ", cantidad=" + cantidad + ", precio=" + precio + ", ubicaciones=" + ubicaciones + '}';
    }

    public String toJSON() {
        return "{" + "\"referencia\":\"" + referencia + "\", \"descripcion\":\"" + descripcion + "\", \"refProveedor\":\"" + refProveedor + "\", \"cantidad\":" + cantidad + ", \"precio\":" + precio + "}";
    }
}
