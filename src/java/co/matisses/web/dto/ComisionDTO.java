package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class ComisionDTO {

    private Integer documento;
    private Double valor;
    private Double porcentajeComision;
    private Double valorDocumento;
    private BigDecimal comision;
    private String tipo;
    private String comentario;
    private boolean documentoCerrado;
    private boolean aplicar;
    private boolean incluir = true;
    private boolean aplicarDV = true;
    private boolean nuevo = false;
    private Date fecha;
    private List<ComisionItemDTO> referencias;

    public ComisionDTO() {
    }

    public ComisionDTO(Integer documento, Double valor, Double porcentajeComision, String tipo, boolean documentoCerrado, boolean aplicar, Date fecha, List<ComisionItemDTO> referencias) {
        this.documento = documento;
        this.valor = valor;
        comision = new BigDecimal(0.0);
        this.porcentajeComision = porcentajeComision;
        this.tipo = tipo;
        this.documentoCerrado = documentoCerrado;
        this.aplicar = aplicar;
        this.fecha = fecha;
        this.referencias = referencias;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getPorcentajeComision() {
        return porcentajeComision;
    }

    public void setPorcentajeComision(Double porcentajeComision) {
        this.porcentajeComision = porcentajeComision;
    }

    public Double getValorDocumento() {
        return valorDocumento;
    }

    public void setValorDocumento(Double valorDocumento) {
        this.valorDocumento = valorDocumento;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isDocumentoCerrado() {
        return documentoCerrado;
    }

    public void setDocumentoCerrado(boolean documentoCerrado) {
        this.documentoCerrado = documentoCerrado;
    }

    public boolean isAplicar() {
        return aplicar;
    }

    public void setAplicar(boolean aplicar) {
        this.aplicar = aplicar;
    }

    public boolean isIncluir() {
        return incluir;
    }

    public void setIncluir(boolean incluir) {
        this.incluir = incluir;
    }

    public boolean isAplicarDV() {
        return aplicarDV;
    }

    public void setAplicarDV(boolean aplicarDV) {
        this.aplicarDV = aplicarDV;
    }

    public boolean isNuevo() {
        return nuevo;
    }

    public void setNuevo(boolean nuevo) {
        this.nuevo = nuevo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<ComisionItemDTO> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<ComisionItemDTO> referencias) {
        this.referencias = referencias;
    }

    @Override
    public String toString() {
        return "ComisionDTO{" + "documento=" + documento + ", valor=" + valor + ", porcentajeComision=" + porcentajeComision + ", valorDocumento=" + valorDocumento
                + ", comision=" + comision + ", tipo=" + tipo + ", documentoCerrado=" + documentoCerrado + ", aplicar=" + aplicar + ", incluir=" + incluir
                + ", fecha=" + fecha + ", referencias=" + referencias + '}';
    }
}
