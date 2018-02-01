package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class SmsEnviadoDTO {

    private Integer idSMSEnviado;
    private Integer codigoPais;
    private String celular;
    private String mensaje;
    private String mensajeRespuesta;
    private String referenciaRespuesta;
    private String estadoRespuesta;
    private String usuario;
    private String ip;
    private String estiloBurbuja;
    private String cedula;
    private Date fecha;

    public SmsEnviadoDTO() {
    }

    public SmsEnviadoDTO(Integer idSMSEnviado, Integer codigoPais, String celular, String mensaje, String mensajeRespuesta,
            String referenciaRespuesta, String estadoRespuesta, String usuario, String ip, String estiloBurbuja, String cedula, Date fecha) {
        this.idSMSEnviado = idSMSEnviado;
        this.codigoPais = codigoPais;
        this.celular = celular;
        this.mensaje = mensaje;
        this.mensajeRespuesta = mensajeRespuesta;
        this.referenciaRespuesta = referenciaRespuesta;
        this.estadoRespuesta = estadoRespuesta;
        this.usuario = usuario;
        this.ip = ip;
        this.estiloBurbuja = estiloBurbuja;
        this.cedula = cedula;
        this.fecha = fecha;
    }

    public Integer getIdSMSEnviado() {
        return idSMSEnviado;
    }

    public void setIdSMSEnviado(Integer idSMSEnviado) {
        this.idSMSEnviado = idSMSEnviado;
    }

    public Integer getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(Integer codigoPais) {
        this.codigoPais = codigoPais;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensajeRespuesta() {
        return mensajeRespuesta;
    }

    public void setMensajeRespuesta(String mensajeRespuesta) {
        this.mensajeRespuesta = mensajeRespuesta;
    }

    public String getReferenciaRespuesta() {
        return referenciaRespuesta;
    }

    public void setReferenciaRespuesta(String referenciaRespuesta) {
        this.referenciaRespuesta = referenciaRespuesta;
    }

    public String getEstadoRespuesta() {
        return estadoRespuesta;
    }

    public void setEstadoRespuesta(String estadoRespuesta) {
        this.estadoRespuesta = estadoRespuesta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getEstiloBurbuja() {
        return estiloBurbuja;
    }

    public void setEstiloBurbuja(String estiloBurbuja) {
        this.estiloBurbuja = estiloBurbuja;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
