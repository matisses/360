package co.matisses.web.dto;

import java.util.List;

/**
 *
 * @author ygil
 */
public class DetalleCotizacionWebDTO {

    private int cantidad;
    private int cantidadUsada = 0;
    private Integer idCotizacion;
    private Integer linea;
    private double precioUnitario;
    private Long idDetalleCotizacion;
    private Long descuento;
    private String referencia;
    private String bodega;
    private String refProveedor;
    private String descripcion;
    private String taxCode;
    private String estado;
    private boolean tieneUbicaciones;
    private List<SaldoUbicacionDTO> ubicaciones;

    public DetalleCotizacionWebDTO() {
    }

    public DetalleCotizacionWebDTO(int cantidad, Integer idCotizacion, Integer linea, Long idDetalleCotizacion, String referencia, String bodega) {
        this.cantidad = cantidad;
        this.idCotizacion = idCotizacion;
        this.linea = linea;
        this.idDetalleCotizacion = idDetalleCotizacion;
        this.referencia = referencia;
        this.bodega = bodega;
    }

    public DetalleCotizacionWebDTO(int cantidad, Integer idCotizacion, Integer linea, Long idDetalleCotizacion, String referencia, String bodega, String taxCode, String estado) {
        this.cantidad = cantidad;
        this.idCotizacion = idCotizacion;
        this.linea = linea;
        this.idDetalleCotizacion = idDetalleCotizacion;
        this.referencia = referencia;
        this.bodega = bodega;
        this.taxCode = taxCode;
        this.estado = estado;
    }

    public DetalleCotizacionWebDTO(int cantidad, double precioUnitario, Long idDetalleCotizacion, String referencia, String bodega) {
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.idDetalleCotizacion = idDetalleCotizacion;
        this.referencia = referencia;
        this.bodega = bodega;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public Long getIdDetalleCotizacion() {
        return idDetalleCotizacion;
    }

    public void setIdDetalleCotizacion(Long idDetalleCotizacion) {
        this.idDetalleCotizacion = idDetalleCotizacion;
    }

    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getBodega() {
        return bodega;
    }

    public void setBodega(String bodega) {
        this.bodega = bodega;
    }

    public String getRefProveedor() {
        return refProveedor;
    }

    public void setRefProveedor(String refProveedor) {
        this.refProveedor = refProveedor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public boolean isTieneUbicaciones() {
        return tieneUbicaciones;
    }

    public void setTieneUbicaciones(boolean tieneUbicaciones) {
        this.tieneUbicaciones = tieneUbicaciones;
    }

    public List<SaldoUbicacionDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<SaldoUbicacionDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
