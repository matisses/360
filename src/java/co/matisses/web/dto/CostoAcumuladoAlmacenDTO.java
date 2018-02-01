package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class CostoAcumuladoAlmacenDTO {

    private Integer saldoAcumulado;
    private Long costoAcumulado;
    private Long precioAcumuladoSinIva;
    private Long precioAcumuladoConIva;
    private String whsCode;
    private Date fecha;

    public CostoAcumuladoAlmacenDTO() {
    }

    public CostoAcumuladoAlmacenDTO(Integer saldoAcumulado, Long costoAcumulado, Long precioAcumuladoSinIva, Long precioAcumuladoConIva, String whsCode, Date fecha) {
        this.saldoAcumulado = saldoAcumulado;
        this.costoAcumulado = costoAcumulado;
        this.precioAcumuladoSinIva = precioAcumuladoSinIva;
        this.precioAcumuladoConIva = precioAcumuladoConIva;
        this.whsCode = whsCode;
        this.fecha = fecha;
    }

    public Integer getSaldoAcumulado() {
        return saldoAcumulado;
    }

    public void setSaldoAcumulado(Integer saldoAcumulado) {
        this.saldoAcumulado = saldoAcumulado;
    }

    public Long getCostoAcumulado() {
        return costoAcumulado;
    }

    public void setCostoAcumulado(Long costoAcumulado) {
        this.costoAcumulado = costoAcumulado;
    }

    public Long getPrecioAcumuladoSinIva() {
        return precioAcumuladoSinIva;
    }

    public void setPrecioAcumuladoSinIva(Long precioAcumuladoSinIva) {
        this.precioAcumuladoSinIva = precioAcumuladoSinIva;
    }

    public Long getPrecioAcumuladoConIva() {
        return precioAcumuladoConIva;
    }

    public void setPrecioAcumuladoConIva(Long precioAcumuladoConIva) {
        this.precioAcumuladoConIva = precioAcumuladoConIva;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
