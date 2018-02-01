package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class DetalleEmpaqueVentaDTO {

    private String referencia;
    private Integer cantidad;

    public DetalleEmpaqueVentaDTO() {
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

    @Override
    public String toString() {
        return "DetalleEmpaqueVentaDTO{" + "referencia=" + referencia + ", cantidad=" + cantidad + '}';
    }
}
