package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class DetalleDevolucionSAPDTO {

    private Integer cantidad;
    private String referencia;

    public DetalleDevolucionSAPDTO() {
    }

    public DetalleDevolucionSAPDTO(Integer cantidad, String referencia) {
        this.cantidad = cantidad;
        this.referencia = referencia;
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
}
