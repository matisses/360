package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class AutorizacionPagoDTO {

    private Integer idAutorizacionPago;
    private Integer idCotizacion;
    private Integer codAsesor;
    private Integer idCondicionPago;
    private BigDecimal valorSolicitado;
    private String cliente;
    private String usuarioSolicita;
    private String comentario;
    private String usuarioAprueba;
    private String correoAprueba;
    private String comentarioAprueba;
    private String token;
    private String estado;
    private String condicionPago;
    private Date fechaSolicita;
    private Date fechaAprobacion;
    private Boolean respuesta;

    public AutorizacionPagoDTO() {
    }

    public AutorizacionPagoDTO(Integer idAutorizacionPago, Integer idCotizacion, Integer codAsesor, Integer idCondicionPago, BigDecimal valorSolicitado, String cliente,
            String usuarioSolicita, String comentario, String usuarioAprueba, String correoAprueba, String comentarioAprueba, String token, String estado, Date fechaSolicita,
            Date fechaAprobacion, Boolean respuesta) {
        this.idAutorizacionPago = idAutorizacionPago;
        this.idCotizacion = idCotizacion;
        this.codAsesor = codAsesor;
        this.idCondicionPago = idCondicionPago;
        this.valorSolicitado = valorSolicitado;
        this.cliente = cliente;
        this.usuarioSolicita = usuarioSolicita;
        this.comentario = comentario;
        this.usuarioAprueba = usuarioAprueba;
        this.correoAprueba = correoAprueba;
        this.comentarioAprueba = comentarioAprueba;
        this.token = token;
        this.estado = estado;
        this.fechaSolicita = fechaSolicita;
        this.fechaAprobacion = fechaAprobacion;
        this.respuesta = respuesta;
    }

    public Integer getIdAutorizacionPago() {
        return idAutorizacionPago;
    }

    public void setIdAutorizacionPago(Integer idAutorizacionPago) {
        this.idAutorizacionPago = idAutorizacionPago;
    }

    public Integer getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Integer idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public Integer getCodAsesor() {
        return codAsesor;
    }

    public void setCodAsesor(Integer codAsesor) {
        this.codAsesor = codAsesor;
    }

    public Integer getIdCondicionPago() {
        return idCondicionPago;
    }

    public void setIdCondicionPago(Integer idCondicionPago) {
        this.idCondicionPago = idCondicionPago;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getUsuarioSolicita() {
        return usuarioSolicita;
    }

    public void setUsuarioSolicita(String usuarioSolicita) {
        this.usuarioSolicita = usuarioSolicita;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuarioAprueba() {
        return usuarioAprueba;
    }

    public void setUsuarioAprueba(String usuarioAprueba) {
        this.usuarioAprueba = usuarioAprueba;
    }

    public String getCorreoAprueba() {
        return correoAprueba;
    }

    public void setCorreoAprueba(String correoAprueba) {
        this.correoAprueba = correoAprueba;
    }

    public String getComentarioAprueba() {
        return comentarioAprueba;
    }

    public void setComentarioAprueba(String comentarioAprueba) {
        this.comentarioAprueba = comentarioAprueba;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCondicionPago() {
        return condicionPago;
    }

    public void setCondicionPago(String condicionPago) {
        this.condicionPago = condicionPago;
    }

    public Date getFechaSolicita() {
        return fechaSolicita;
    }

    public void setFechaSolicita(Date fechaSolicita) {
        this.fechaSolicita = fechaSolicita;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public Boolean getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(Boolean respuesta) {
        this.respuesta = respuesta;
    }
}
