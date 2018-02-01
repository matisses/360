package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class AdjuntoSolicitudNextDTO {

    private Integer idAdjunto;
    private String nombre;
    private String extension;

    public AdjuntoSolicitudNextDTO() {
    }

    public AdjuntoSolicitudNextDTO(Integer idAdjunto, String nombre, String extension) {
        this.idAdjunto = idAdjunto;
        this.nombre = nombre;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Integer getIdAdjunto() {
        return idAdjunto;
    }

    public void setIdAdjunto(Integer idAdjunto) {
        this.idAdjunto = idAdjunto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
