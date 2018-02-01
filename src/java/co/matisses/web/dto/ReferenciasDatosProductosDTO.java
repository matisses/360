package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ReferenciasDatosProductosDTO {

    private Integer cantidad;
    private String referencia;
    private String estado;

    public ReferenciasDatosProductosDTO() {
    }

    public ReferenciasDatosProductosDTO(Integer cantidad, String referencia, String estado) {
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.estado = estado;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
