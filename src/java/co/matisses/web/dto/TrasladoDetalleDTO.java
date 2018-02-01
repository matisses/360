package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class TrasladoDetalleDTO {

    private Integer idTrasladoDetalle;
    private Integer idTraslado;
    private Integer cantidad;
    private Integer tipoDocumentoReferencia;
    private Integer documentoReferencia;
    private String referencia;
    private String almacenOrigen;
    private String almacenDestino;
    private String comentario;
    private Date fecha;
    private List<TrasladoUbicacionesDTO> trasladoUbicaciones;

    public TrasladoDetalleDTO() {
        trasladoUbicaciones = new ArrayList<>();
    }

    public TrasladoDetalleDTO(Integer idTrasladoDetalle, Integer idTraslado, Integer cantidad, Integer tipoDocumentoReferencia,
            Integer documentoReferencia, String referencia, String almacenOrigen, String almacenDestino, String comentario, Date fecha) {
        this.idTrasladoDetalle = idTrasladoDetalle;
        this.idTraslado = idTraslado;
        this.cantidad = cantidad;
        this.tipoDocumentoReferencia = tipoDocumentoReferencia;
        this.documentoReferencia = documentoReferencia;
        this.referencia = referencia;
        this.almacenOrigen = almacenOrigen;
        this.almacenDestino = almacenDestino;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public TrasladoDetalleDTO(Integer idTrasladoDetalle, Integer idTraslado, Integer cantidad, Integer tipoDocumentoReferencia,
            Integer documentoReferencia, String referencia, String almacenOrigen, String almacenDestino, String comentario, Date fecha,
            List<TrasladoUbicacionesDTO> trasladoUbicaciones) {
        this.idTrasladoDetalle = idTrasladoDetalle;
        this.idTraslado = idTraslado;
        this.cantidad = cantidad;
        this.tipoDocumentoReferencia = tipoDocumentoReferencia;
        this.documentoReferencia = documentoReferencia;
        this.referencia = referencia;
        this.almacenOrigen = almacenOrigen;
        this.almacenDestino = almacenDestino;
        this.comentario = comentario;
        this.fecha = fecha;
        this.trasladoUbicaciones = trasladoUbicaciones;
    }

    public Integer getIdTrasladoDetalle() {
        return idTrasladoDetalle;
    }

    public void setIdTrasladoDetalle(Integer idTrasladoDetalle) {
        this.idTrasladoDetalle = idTrasladoDetalle;
    }

    public Integer getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getTipoDocumentoReferencia() {
        return tipoDocumentoReferencia;
    }

    public void setTipoDocumentoReferencia(Integer tipoDocumentoReferencia) {
        this.tipoDocumentoReferencia = tipoDocumentoReferencia;
    }

    public Integer getDocumentoReferencia() {
        return documentoReferencia;
    }

    public void setDocumentoReferencia(Integer documentoReferencia) {
        this.documentoReferencia = documentoReferencia;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<TrasladoUbicacionesDTO> getTrasladoUbicaciones() {
        return trasladoUbicaciones;
    }

    public void setTrasladoUbicaciones(List<TrasladoUbicacionesDTO> trasladoUbicaciones) {
        this.trasladoUbicaciones = trasladoUbicaciones;
    }

    public void addTrasladoUbicacion(TrasladoUbicacionesDTO trasladoUbicacion) {
        trasladoUbicaciones.add(trasladoUbicacion);
    }
}
