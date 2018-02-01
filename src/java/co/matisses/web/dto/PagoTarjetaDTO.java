package co.matisses.web.dto;

import java.math.BigDecimal;

/**
 *
 * @author ygil
 */
public class PagoTarjetaDTO {

    private Integer idBanco;
    private Integer idCuenta;
    private Long identificador;
    private BigDecimal valorPago;
    private String voucher;
    private String digitos;
    private String franquicia;
    private String tarjeta;

    public PagoTarjetaDTO() {
    }

    public PagoTarjetaDTO(Integer idBanco, Integer idCuenta, Long identificador, BigDecimal valorPago, String voucher, String digitos, String franquicia, String tarjeta) {
        this.idBanco = idBanco;
        this.idCuenta = idCuenta;
        this.identificador = identificador;
        this.valorPago = valorPago;
        this.voucher = voucher;
        this.digitos = digitos;
        this.franquicia = franquicia;
        this.tarjeta = tarjeta;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Long getIdentificador() {
        return identificador;
    }

    public void setIdentificador(Long identificador) {
        this.identificador = identificador;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }

    public String getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(String tarjeta) {
        this.tarjeta = tarjeta;
    }
}
