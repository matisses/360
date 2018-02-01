package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class TrasladoUbicacionesDTO {

    private Integer idTrasladoUbicacion;
    private Integer idTrasladoDetalle;
    private Integer cantidad;
    private String ubicacionOrigen;
    private String ubicacionDestino;
    private Date fecha;

    public TrasladoUbicacionesDTO() {
    }

    public TrasladoUbicacionesDTO(Integer idTrasladoUbicacion, Integer idTrasladoDetalle, Integer cantidad, String ubicacionOrigen, String ubicacionDestino, Date fecha) {
        this.idTrasladoUbicacion = idTrasladoUbicacion;
        this.idTrasladoDetalle = idTrasladoDetalle;
        this.cantidad = cantidad;
        this.ubicacionOrigen = ubicacionOrigen;
        this.ubicacionDestino = ubicacionDestino;
        this.fecha = fecha;
    }

    public Integer getIdTrasladoUbicacion() {
        return idTrasladoUbicacion;
    }

    public void setIdTrasladoUbicacion(Integer idTrasladoUbicacion) {
        this.idTrasladoUbicacion = idTrasladoUbicacion;
    }

    public Integer getIdTrasladoDetalle() {
        return idTrasladoDetalle;
    }

    public void setIdTrasladoDetalle(Integer idTrasladoDetalle) {
        this.idTrasladoDetalle = idTrasladoDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
