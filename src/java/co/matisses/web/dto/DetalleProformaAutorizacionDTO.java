package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DetalleProformaAutorizacionDTO {

    private Double cantidad;
    private Double valorUnitario;
    private Double valorTotal;
    private String referencia;
    private String foto;
    private String descripcion;

    public DetalleProformaAutorizacionDTO() {
    }

    public DetalleProformaAutorizacionDTO(Double cantidad, Double valorUnitario, Double valorTotal, String referencia, String foto, String descripcion) {
        this.cantidad = cantidad;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.referencia = referencia;
        this.foto = foto;
        this.descripcion = descripcion;
    }

    public Double getCantidad() {
        return cantidad;
    }

    public void setCantidad(Double cantidad) {
        this.cantidad = cantidad;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
