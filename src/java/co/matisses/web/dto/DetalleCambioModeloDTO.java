package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DetalleCambioModeloDTO {

    private Integer idDetalleCambioModelo;
    private Integer idCambioModelo;
    private String referenciaAnterior;
    private String referenciaNueva;
    private boolean fotosHercules;
    private boolean fotosSAP;
    private boolean materiales;
    private boolean colores;

    public DetalleCambioModeloDTO() {
    }

    public DetalleCambioModeloDTO(Integer idDetalleCambioModelo, Integer idCambioModelo, String referenciaAnterior, String referenciaNueva, boolean fotosHercules, boolean fotosSAP, boolean materiales, boolean colores) {
        this.idDetalleCambioModelo = idDetalleCambioModelo;
        this.idCambioModelo = idCambioModelo;
        this.referenciaAnterior = referenciaAnterior;
        this.referenciaNueva = referenciaNueva;
        this.fotosHercules = fotosHercules;
        this.fotosSAP = fotosSAP;
        this.materiales = materiales;
        this.colores = colores;
    }

    public Integer getIdDetalleCambioModelo() {
        return idDetalleCambioModelo;
    }

    public void setIdDetalleCambioModelo(Integer idDetalleCambioModelo) {
        this.idDetalleCambioModelo = idDetalleCambioModelo;
    }

    public Integer getIdCambioModelo() {
        return idCambioModelo;
    }

    public void setIdCambioModelo(Integer idCambioModelo) {
        this.idCambioModelo = idCambioModelo;
    }

    public String getReferenciaAnterior() {
        return referenciaAnterior;
    }

    public void setReferenciaAnterior(String referenciaAnterior) {
        this.referenciaAnterior = referenciaAnterior;
    }

    public String getReferenciaNueva() {
        return referenciaNueva;
    }

    public void setReferenciaNueva(String referenciaNueva) {
        this.referenciaNueva = referenciaNueva;
    }

    public boolean isFotosHercules() {
        return fotosHercules;
    }

    public void setFotosHercules(boolean fotosHercules) {
        this.fotosHercules = fotosHercules;
    }

    public boolean isFotosSAP() {
        return fotosSAP;
    }

    public void setFotosSAP(boolean fotosSAP) {
        this.fotosSAP = fotosSAP;
    }

    public boolean isMateriales() {
        return materiales;
    }

    public void setMateriales(boolean materiales) {
        this.materiales = materiales;
    }

    public boolean isColores() {
        return colores;
    }

    public void setColores(boolean colores) {
        this.colores = colores;
    }
}
