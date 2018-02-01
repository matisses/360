package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class TrasladoUbicacionDTO {

    private Integer idTrasladoUbicacion;
    private Integer nroFactura;
    private Integer nroTrasladoSAP;
    private Integer cantidad;
    private Integer idEncabezadoTrasladoUbicacion;
    private String nit;
    private String almacen;
    private String referencia;
    private String ubicacionOrigen;
    private String ubicacionDestino;
    private String usuario;
    private String estado;
    private Date fecha;

    public TrasladoUbicacionDTO() {
    }

    public TrasladoUbicacionDTO(Integer idTrasladoUbicacion, Integer nroFactura, Integer nroTrasladoSAP, Integer cantidad, Integer idEncabezadoTrasladoUbicacion, String nit, String almacen, String referencia, String ubicacionOrigen, String ubicacionDestino, String usuario, String estado, Date fecha) {
        this.idTrasladoUbicacion = idTrasladoUbicacion;
        this.nroFactura = nroFactura;
        this.nroTrasladoSAP = nroTrasladoSAP;
        this.cantidad = cantidad;
        this.idEncabezadoTrasladoUbicacion = idEncabezadoTrasladoUbicacion;
        this.nit = nit;
        this.almacen = almacen;
        this.referencia = referencia;
        this.ubicacionOrigen = ubicacionOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.usuario = usuario;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Integer getIdTrasladoUbicacion() {
        return idTrasladoUbicacion;
    }

    public void setIdTrasladoUbicacion(Integer idTrasladoUbicacion) {
        this.idTrasladoUbicacion = idTrasladoUbicacion;
    }

    public Integer getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(Integer nroFactura) {
        this.nroFactura = nroFactura;
    }

    public Integer getNroTrasladoSAP() {
        return nroTrasladoSAP;
    }

    public void setNroTrasladoSAP(Integer nroTrasladoSAP) {
        this.nroTrasladoSAP = nroTrasladoSAP;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdEncabezadoTrasladoUbicacion() {
        return idEncabezadoTrasladoUbicacion;
    }

    public void setIdEncabezadoTrasladoUbicacion(Integer idEncabezadoTrasladoUbicacion) {
        this.idEncabezadoTrasladoUbicacion = idEncabezadoTrasladoUbicacion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public void setUbicacionOrigen(String ubicacionOrigen) {
        this.ubicacionOrigen = ubicacionOrigen;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
