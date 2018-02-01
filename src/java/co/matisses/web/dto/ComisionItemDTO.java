package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComisionItemDTO {

    private Integer cantidad;
    private Double precio;
    private Double porcentajeComision;
    private Double comision;
    private String referencia;

    public ComisionItemDTO() {
    }

    public ComisionItemDTO(Integer cantidad, Double precio, Double porcentajeComision, String referencia) {
        this.cantidad = cantidad;
        this.precio = precio;
        this.porcentajeComision = porcentajeComision;
        this.referencia = referencia;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    @Override
    public String toString() {
        return "ComisionItemDTO{" + "cantidad=" + cantidad + ", precio=" + precio + ", porcentajeComision=" + porcentajeComision + ", comision=" + comision + ", referencia=" + referencia + '}';
    }
}
