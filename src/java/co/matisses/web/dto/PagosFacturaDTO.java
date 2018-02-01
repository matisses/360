package co.matisses.web.dto;

import java.math.BigDecimal;

/**
 *
 * @author ygil
 */
public class PagosFacturaDTO {

    private Integer docNum;
    private BigDecimal valorEfectivo;
    private BigDecimal valorTarjetas;
    private BigDecimal valorCheque;
    private BigDecimal valorCruces;
    private String usuario;

    public PagosFacturaDTO() {
    }

    public PagosFacturaDTO(Integer docNum, BigDecimal valorEfectivo, BigDecimal valorTarjetas, BigDecimal valorCheque, BigDecimal valorCruces, String usuario) {
        this.docNum = docNum;
        this.valorEfectivo = valorEfectivo;
        this.valorTarjetas = valorTarjetas;
        this.valorCheque = valorCheque;
        this.valorCruces = valorCruces;
        this.usuario = usuario;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public BigDecimal getValorEfectivo() {
        return valorEfectivo;
    }

    public void setValorEfectivo(BigDecimal valorEfectivo) {
        this.valorEfectivo = valorEfectivo;
    }

    public BigDecimal getValorTarjetas() {
        return valorTarjetas;
    }

    public void setValorTarjetas(BigDecimal valorTarjetas) {
        this.valorTarjetas = valorTarjetas;
    }

    public BigDecimal getValorCheque() {
        return valorCheque;
    }

    public void setValorCheque(BigDecimal valorCheque) {
        this.valorCheque = valorCheque;
    }

    public BigDecimal getValorCruces() {
        return valorCruces;
    }

    public void setValorCruces(BigDecimal valorCruces) {
        this.valorCruces = valorCruces;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
