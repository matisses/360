package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class NotificacionProformaDTO {

    private Integer idNotificacionProforma;
    private Integer idProforma;
    private String nombreUsuario;
    private String correoUsuario;
    private String token;
    private String comentario;
    private Boolean respuesta;
    private Date fechaRespuesta;

    public NotificacionProformaDTO() {
    }

    public NotificacionProformaDTO(Integer idNotificacionProforma, Integer idProforma, String nombreUsuario, String correoUsuario, String token, String comentario, Boolean respuesta, Date fechaRespuesta) {
        this.idNotificacionProforma = idNotificacionProforma;
        this.idProforma = idProforma;
        this.nombreUsuario = nombreUsuario;
        this.correoUsuario = correoUsuario;
        this.token = token;
        this.comentario = comentario;
        this.respuesta = respuesta;
        this.fechaRespuesta = fechaRespuesta;
    }

    public Integer getIdNotificacionProforma() {
        return idNotificacionProforma;
    }

    public void setIdNotificacionProforma(Integer idNotificacionProforma) {
        this.idNotificacionProforma = idNotificacionProforma;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Boolean getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Boolean respuesta) {
        this.respuesta = respuesta;
    }

    public Date getFechaRespuesta() {
        return fechaRespuesta;
    }

    public void setFechaRespuesta(Date fechaRespuesta) {
        this.fechaRespuesta = fechaRespuesta;
    }
}
