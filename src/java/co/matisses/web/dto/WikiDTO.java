package co.matisses.web.dto;

import co.matisses.persistence.web.entity.Wiki;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class WikiDTO {

    private Integer idWiki;
    private Integer idSolicitud;
    private Integer visitas;
    private String titulo;
    private String texto;
    private String usuario;
    private boolean activo;
    private Date fecha;

    public WikiDTO() {
    }

    public WikiDTO(Integer idWiki, Integer idSolicitud, String titulo, String texto, String usuario, boolean activo, Date fecha) {
        this.idWiki = idWiki;
        this.idSolicitud = idSolicitud;
        this.titulo = titulo;
        this.texto = texto;
        this.usuario = usuario;
        this.activo = activo;
        this.fecha = fecha;
    }

    public WikiDTO(Wiki wiki) {
        this.idWiki = wiki.getIdWiki();
        this.idSolicitud = wiki.getIdSolicitud();
        this.titulo = wiki.getTitulo();
        this.texto = wiki.getTexto();
        this.usuario = wiki.getUsuario();
        this.activo = wiki.getActivo();
        this.fecha = wiki.getFecha();
        this.visitas = wiki.getVisitas();
    }

    public Integer getIdWiki() {
        return idWiki;
    }

    public void setIdWiki(Integer idWiki) {
        this.idWiki = idWiki;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
