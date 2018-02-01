package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ContenedorProformaDTO {

    private Integer idContenedorProforma;
    private Integer idProforma;
    private Integer tiempoProduccion;
    private Integer tiempoTransito;
    private Double valorTotal;
    private Double cbmAcumulado;
    private String urlTabla;
    private String nombreContenedor;
    private Date fechaEmbarque;
    private Date fechaVencimiento;
    private Date fechaMaximaPago;
    private ContenedorProveedorDTO contenedorProveedor;

    public ContenedorProformaDTO() {
    }

    public ContenedorProformaDTO(Integer idContenedorProforma, String nombreContenedor) {
        this.idContenedorProforma = idContenedorProforma;
        this.nombreContenedor = nombreContenedor;
    }

    public ContenedorProformaDTO(Integer idContenedorProforma, Integer idProforma, Double valorTotal, Double cbmAcumulado, Date fechaEmbarque,
            ContenedorProveedorDTO contenedorProveedor, String urlTabla) {
        this.idContenedorProforma = idContenedorProforma;
        this.idProforma = idProforma;
        this.valorTotal = valorTotal;
        this.cbmAcumulado = cbmAcumulado;
        this.fechaEmbarque = fechaEmbarque;
        this.contenedorProveedor = contenedorProveedor;
        this.urlTabla = urlTabla;
    }

    public ContenedorProformaDTO(Integer idContenedorProforma, Integer idProforma, Integer tiempoProduccion, Integer tiempoTransito, Double valorTotal,
            Double cbmAcumulado, Date fechaEmbarque, Date fechaVencimiento, Date fechaMaximaPago, ContenedorProveedorDTO contenedorProveedor) {
        this.idContenedorProforma = idContenedorProforma;
        this.idProforma = idProforma;
        this.tiempoProduccion = tiempoProduccion;
        this.tiempoTransito = tiempoTransito;
        this.valorTotal = valorTotal;
        this.cbmAcumulado = cbmAcumulado;
        this.fechaEmbarque = fechaEmbarque;
        this.fechaVencimiento = fechaVencimiento;
        this.fechaMaximaPago = fechaMaximaPago;
        this.contenedorProveedor = contenedorProveedor;
    }

    public Integer getIdContenedorProforma() {
        return idContenedorProforma;
    }

    public void setIdContenedorProforma(Integer idContenedorProforma) {
        this.idContenedorProforma = idContenedorProforma;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getCbmAcumulado() {
        return cbmAcumulado;
    }

    public void setCbmAcumulado(Double cbmAcumulado) {
        this.cbmAcumulado = cbmAcumulado;
    }

    public String getUrlTabla() {
        return urlTabla;
    }

    public void setUrlTabla(String urlTabla) {
        this.urlTabla = urlTabla;
    }

    public String getNombreContenedor() {
        return nombreContenedor;
    }

    public void setNombreContenedor(String nombreContenedor) {
        this.nombreContenedor = nombreContenedor;
    }

    public Date getFechaEmbarque() {
        return fechaEmbarque;
    }

    public void setFechaEmbarque(Date fechaEmbarque) {
        this.fechaEmbarque = fechaEmbarque;
    }

    public ContenedorProveedorDTO getContenedorProveedor() {
        return contenedorProveedor;
    }

    public void setContenedorProveedor(ContenedorProveedorDTO contenedorProveedor) {
        this.contenedorProveedor = contenedorProveedor;
    }

    public Integer getTiempoProduccion() {
        return tiempoProduccion;
    }

    public void setTiempoProduccion(Integer tiempoProduccion) {
        this.tiempoProduccion = tiempoProduccion;
    }

    public Integer getTiempoTransito() {
        return tiempoTransito;
    }

    public void setTiempoTransito(Integer tiempoTransito) {
        this.tiempoTransito = tiempoTransito;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public Date getFechaMaximaPago() {
        return fechaMaximaPago;
    }

    public void setFechaMaximaPago(Date fechaMaximaPago) {
        this.fechaMaximaPago = fechaMaximaPago;
    }
}
