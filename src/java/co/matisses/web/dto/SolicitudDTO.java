package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class SolicitudDTO {

    private Integer idSolicitud;
    private String encargado;
    private String usuario;
    private String asunto;
    private String historial;
    private String empleado;
    private String usuarioAsignado;
    private TipoSolicitudNextDTO idTipoSolicitud;
    private PrioridadNextDTO idPrioridad;
    private EstadosNextDTO estado;
    private List<ComentarioNextDTO> comentarios;
    private List<AdjuntoNextDTO> adjuntos;

    public SolicitudDTO() {
        idTipoSolicitud = new TipoSolicitudNextDTO();
        idPrioridad = new PrioridadNextDTO();
        estado = new EstadosNextDTO();
        comentarios = new ArrayList<>();
        adjuntos = new ArrayList<>();
    }

    public SolicitudDTO(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getEncargado() {
        return encargado;
    }

    public void setEncargado(String encargado) {
        this.encargado = encargado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getHistorial() {
        return historial;
    }

    public void setHistorial(String historial) {
        this.historial = historial;
    }

    public String getEmpleado() {
        return empleado;
    }

    public void setEmpleado(String empleado) {
        this.empleado = empleado;
    }

    public String getUsuarioAsignado() {
        return usuarioAsignado;
    }

    public void setUsuarioAsignado(String usuarioAsignado) {
        this.usuarioAsignado = usuarioAsignado;
    }

    public TipoSolicitudNextDTO getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(TipoSolicitudNextDTO idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public PrioridadNextDTO getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(PrioridadNextDTO idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public EstadosNextDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadosNextDTO estado) {
        this.estado = estado;
    }

    public List<ComentarioNextDTO> getComentarios() {
        return comentarios;
    }

    public void setComentarios(List<ComentarioNextDTO> comentarios) {
        this.comentarios = comentarios;
    }

    public List<AdjuntoNextDTO> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoNextDTO> adjuntos) {
        this.adjuntos = adjuntos;
    }
}
