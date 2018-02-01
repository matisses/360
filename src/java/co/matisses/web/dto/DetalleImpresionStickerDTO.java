package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class DetalleImpresionStickerDTO {

    private Integer idDetalleImpresionSticker;
    private Integer idImpresionSticker;
    private Integer cantidad;
    private Integer precio;
    private String referencia;
    private String almacen;
    private String nombreReferencia;
    private String referenciaProveedor;
    private Date fecha;

    public DetalleImpresionStickerDTO() {
    }

    public DetalleImpresionStickerDTO(Integer idDetalleImpresionSticker, Integer idImpresionSticker, Integer cantidad, Integer precio, String referencia,
            String almacen, String nombreReferencia, String referenciaProveedor, Date fecha) {
        this.idDetalleImpresionSticker = idDetalleImpresionSticker;
        this.idImpresionSticker = idImpresionSticker;
        this.cantidad = cantidad;
        this.precio = precio;
        this.referencia = referencia;
        this.almacen = almacen;
        this.nombreReferencia = nombreReferencia;
        this.referenciaProveedor = referenciaProveedor;
        this.fecha = fecha;
    }

    public Integer getIdDetalleImpresionSticker() {
        return idDetalleImpresionSticker;
    }

    public void setIdDetalleImpresionSticker(Integer idDetalleImpresionSticker) {
        this.idDetalleImpresionSticker = idDetalleImpresionSticker;
    }

    public Integer getIdImpresionSticker() {
        return idImpresionSticker;
    }

    public void setIdImpresionSticker(Integer idImpresionSticker) {
        this.idImpresionSticker = idImpresionSticker;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getNombreReferencia() {
        return nombreReferencia;
    }

    public void setNombreReferencia(String nombreReferencia) {
        this.nombreReferencia = nombreReferencia;
    }

    public String getReferenciaProveedor() {
        return referenciaProveedor;
    }

    public void setReferenciaProveedor(String referenciaProveedor) {
        this.referenciaProveedor = referenciaProveedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
