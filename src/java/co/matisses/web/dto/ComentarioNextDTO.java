package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ComentarioNextDTO {

    private Integer idComentario;
    private String comentario;
    private String usuario;
    private Date fecha;

    public ComentarioNextDTO() {
    }

    public ComentarioNextDTO(Integer idComentario, String comentario, String usuario, Date fecha) {
        this.idComentario = idComentario;
        this.comentario = comentario;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
