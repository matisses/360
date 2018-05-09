package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class InformeRotacionDTO {

    private Integer idInforme;
    private String nombre;
    private String correo;
    private String autor;
    private String urlDocumento;
    private boolean documentoExiste = false;
    private Date fecha;
    private EstadoInformeDTO estado;

    public InformeRotacionDTO() {
        estado = new EstadoInformeDTO();
    }

    public InformeRotacionDTO(Integer idInforme, String nombre, String correo, String autor, Date fecha) {
        this.idInforme = idInforme;
        this.nombre = nombre;
        this.correo = correo;
        this.autor = autor;
        this.fecha = fecha;
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getUrlDocumento() {
        return urlDocumento;
    }

    public void setUrlDocumento(String urlDocumento) {
        this.urlDocumento = urlDocumento;
    }

    public boolean isDocumentoExiste() {
        return documentoExiste;
    }

    public void setDocumentoExiste(boolean documentoExiste) {
        this.documentoExiste = documentoExiste;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public EstadoInformeDTO getEstado() {
        return estado;
    }

    public void setEstado(EstadoInformeDTO estado) {
        this.estado = estado;
    }
}
