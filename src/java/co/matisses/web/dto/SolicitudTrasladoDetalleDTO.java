package co.matisses.web.dto;

import java.util.List;

/**
 *
 * @author ygil
 */
public class SolicitudTrasladoDetalleDTO {

    private Integer idSolicitudDetalle;
    private Integer idSolicitud;
    private Integer cantidad;
    private Integer cantidadUsada;
    private String referencia;
    private String comentario;
    private String usuario;
    private List<SaldoUbicacionDTO> saldoUbicaciones;

    public SolicitudTrasladoDetalleDTO() {
    }

    public SolicitudTrasladoDetalleDTO(Integer idSolicitudDetalle, Integer idSolicitud, Integer cantidad, String referencia, String comentario, String usuario) {
        this.idSolicitudDetalle = idSolicitudDetalle;
        this.idSolicitud = idSolicitud;
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.comentario = comentario;
        this.usuario = usuario;
    }

    public SolicitudTrasladoDetalleDTO(Integer cantidad, String referencia, String comentario) {
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.comentario = comentario;
    }

    public Integer getIdSolicitudDetalle() {
        return idSolicitudDetalle;
    }

    public void setIdSolicitudDetalle(Integer idSolicitudDetalle) {
        this.idSolicitudDetalle = idSolicitudDetalle;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public List<SaldoUbicacionDTO> getSaldoUbicaciones() {
        return saldoUbicaciones;
    }

    public void setSaldoUbicaciones(List<SaldoUbicacionDTO> saldoUbicaciones) {
        this.saldoUbicaciones = saldoUbicaciones;
    }
}
