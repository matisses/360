package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SolicitudTrasladoInformeDTO {

    private Integer idSolicitudInforme;
    private Integer idSolicitudDetalle;
    private Integer cantidad;
    private String referencia;
    private String almacen;
    private String comentario;
    private String ubicaciones;

    public SolicitudTrasladoInformeDTO() {
    }

    public SolicitudTrasladoInformeDTO(Integer idSolicitudInforme, Integer idSolicitudDetalle, Integer cantidad, String referencia, String almacen, String comentario, String ubicaciones) {
        this.idSolicitudInforme = idSolicitudInforme;
        this.idSolicitudDetalle = idSolicitudDetalle;
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.almacen = almacen;
        this.comentario = comentario;
        this.ubicaciones = ubicaciones;
    }

    public Integer getIdSolicitudInforme() {
        return idSolicitudInforme;
    }

    public void setIdSolicitudInforme(Integer idSolicitudInforme) {
        this.idSolicitudInforme = idSolicitudInforme;
    }

    public Integer getIdSolicitudDetalle() {
        return idSolicitudDetalle;
    }

    public void setIdSolicitudDetalle(Integer idSolicitudDetalle) {
        this.idSolicitudDetalle = idSolicitudDetalle;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(String ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
