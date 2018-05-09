package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class RotacionImportacionDTO {

    private int idCompra;
    private int cantidadCompra;
    private int diasStock;
    private int saldoPendiente;
    private int cantidadVenta;
    private int cantidadBaja;
    private Integer nroSalidas;
    private String referencia;
    private String tipo;
    private BigDecimal utilidadPromedio;
    private BigDecimal utilidadMinima;
    private BigDecimal sumaUtilidades;
    private Date fechaCompra;
    private Date fechaCierre;
    private Date fechaUltimaVenta;

    public RotacionImportacionDTO() {
    }

    public RotacionImportacionDTO(int idCompra, int cantidadCompra, int diasStock, int saldoPendiente, int cantidadVenta, int cantidadBaja, Integer nroSalidas, String referencia,
            String tipo, BigDecimal utilidadPromedio, BigDecimal utilidadMinima, BigDecimal sumaUtilidades, Date fechaCompra, Date fechaCierre, Date fechaUltimaVenta) {
        this.idCompra = idCompra;
        this.cantidadCompra = cantidadCompra;
        this.diasStock = diasStock;
        this.saldoPendiente = saldoPendiente;
        this.cantidadVenta = cantidadVenta;
        this.cantidadBaja = cantidadBaja;
        this.nroSalidas = nroSalidas;
        this.referencia = referencia;
        this.tipo = tipo;
        this.utilidadPromedio = utilidadPromedio;
        this.utilidadMinima = utilidadMinima;
        this.sumaUtilidades = sumaUtilidades;
        this.fechaCompra = fechaCompra;
        this.fechaCierre = fechaCierre;
        this.fechaUltimaVenta = fechaUltimaVenta;
    }

    public int getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }

    public int getCantidadCompra() {
        return cantidadCompra;
    }

    public void setCantidadCompra(int cantidadCompra) {
        this.cantidadCompra = cantidadCompra;
    }

    public int getDiasStock() {
        return diasStock;
    }

    public void setDiasStock(int diasStock) {
        this.diasStock = diasStock;
    }

    public int getSaldoPendiente() {
        return saldoPendiente;
    }

    public void setSaldoPendiente(int saldoPendiente) {
        this.saldoPendiente = saldoPendiente;
    }

    public int getCantidadVenta() {
        return cantidadVenta;
    }

    public void setCantidadVenta(int cantidadVenta) {
        this.cantidadVenta = cantidadVenta;
    }

    public int getCantidadBaja() {
        return cantidadBaja;
    }

    public void setCantidadBaja(int cantidadBaja) {
        this.cantidadBaja = cantidadBaja;
    }

    public Integer getNroSalidas() {
        return nroSalidas;
    }

    public void setNroSalidas(Integer nroSalidas) {
        this.nroSalidas = nroSalidas;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public BigDecimal getUtilidadPromedio() {
        return utilidadPromedio;
    }

    public void setUtilidadPromedio(BigDecimal utilidadPromedio) {
        this.utilidadPromedio = utilidadPromedio;
    }

    public BigDecimal getUtilidadMinima() {
        return utilidadMinima;
    }

    public void setUtilidadMinima(BigDecimal utilidadMinima) {
        this.utilidadMinima = utilidadMinima;
    }

    public BigDecimal getSumaUtilidades() {
        return sumaUtilidades;
    }

    public void setSumaUtilidades(BigDecimal sumaUtilidades) {
        this.sumaUtilidades = sumaUtilidades;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Date getFechaUltimaVenta() {
        return fechaUltimaVenta;
    }

    public void setFechaUltimaVenta(Date fechaUltimaVenta) {
        this.fechaUltimaVenta = fechaUltimaVenta;
    }
}
