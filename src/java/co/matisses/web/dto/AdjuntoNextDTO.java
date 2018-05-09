package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class AdjuntoNextDTO {

    private Integer idSolicitud;
    private Long idAdjunto;
    private String nombre;
    private String contentType;
    private byte[] archivo;

    public AdjuntoNextDTO() {
    }

    public AdjuntoNextDTO(Integer idSolicitud, Long idAdjunto, String nombre, String contentType, byte[] archivo) {
        this.idSolicitud = idSolicitud;
        this.idAdjunto = idAdjunto;
        this.nombre = nombre;
        this.contentType = contentType;
        this.archivo = archivo;
    }

    public Integer getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Integer idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public Long getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Long idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getArchivo() {
        return archivo;
    }

    public void setArchivo(byte[] archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
