package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DetalleDocumentoComisionDTO {

    private Integer cantidad;
    private String referencia;
    private String almacenVenta;

    public DetalleDocumentoComisionDTO() {
    }

    public DetalleDocumentoComisionDTO(Integer cantidad, String referencia, String almacenVenta) {
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.almacenVenta = almacenVenta;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacenVenta() {
        return almacenVenta;
    }

    public void setAlmacenVenta(String almacenVenta) {
        this.almacenVenta = almacenVenta;
    }
}
