package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class SolicitudTrasladoDTO {

    private Integer idSolicitud;
    private Integer documentoSAP;
    private String sucursal;
    private String comentario;
    private String autor;
    private String usuario;
    private String usuarioFinaliza;
    private String estado;
    private String usuarioResponsable;
    private Date fecha;
    private Date fechaFinal;
    private List<SolicitudTrasladoDetalleDTO> detalle;

    public SolicitudTrasladoDTO() {
        detalle = new ArrayList<>();
    }

    public SolicitudTrasladoDTO(Integer idSolicitud, Integer documentoSAP, String sucursal, String comentario, String autor, String usuario,
            String usuarioFinaliza, String estado, String usuarioResponsable, Date fecha, Date fechaFinal, List<SolicitudTrasladoDetalleDTO> detalle) {
        this.idSolicitud = idSolicitud;
        this.documentoSAP = documentoSAP;
        this.sucursal = sucursal;
        this.comentario = comentario;
        this.autor = autor;
        this.usuario = usuario;
        this.usuarioFinaliza = usuarioFinaliza;
        this.estado = estado;
        this.usuarioResponsable = usuarioResponsable;
        this.fecha = fecha;
        this.fechaFinal = fechaFinal;
        this.detalle = detalle;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getDocumentoSAP() {
        return documentoSAP;
    }

    public void setDocumentoSAP(Integer documentoSAP) {
        this.documentoSAP = documentoSAP;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioFinaliza() {
        return usuarioFinaliza;
    }

    public void setUsuarioFinaliza(String usuarioFinaliza) {
        this.usuarioFinaliza = usuarioFinaliza;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuarioResponsable() {
        return usuarioResponsable;
    }

    public void setUsuarioResponsable(String usuarioResponsable) {
        this.usuarioResponsable = usuarioResponsable;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public List<SolicitudTrasladoDetalleDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<SolicitudTrasladoDetalleDTO> detalle) {
        this.detalle = detalle;
    }
}
