package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComisionPorcentajeDTO {

    private Integer idPorcentaje;
    private int limiteInferior;
    private int limiteSuperior;
    private Integer mesVencidoAplicable;
    private Double porcentaje;
    private ComisionCentroDTO idCentro;
    private ComisionReglaDTO idRegla;

    public ComisionPorcentajeDTO() {
    }

    public ComisionPorcentajeDTO(Integer idPorcentaje, int limiteInferior, int limiteSuperior, Integer mesVencidoAplicable, Double porcentaje, ComisionCentroDTO idCentro, ComisionReglaDTO idRegla) {
        this.idPorcentaje = idPorcentaje;
        this.limiteInferior = limiteInferior;
        this.limiteSuperior = limiteSuperior;
        this.mesVencidoAplicable = mesVencidoAplicable;
        this.porcentaje = porcentaje;
        this.idCentro = idCentro;
        this.idRegla = idRegla;
    }

    public Integer getIdPorcentaje() {
        return idPorcentaje;
    }

    public void setIdPorcentaje(Integer idPorcentaje) {
        this.idPorcentaje = idPorcentaje;
    }

    public int getLimiteInferior() {
        return limiteInferior;
    }

    public void setLimiteInferior(int limiteInferior) {
        this.limiteInferior = limiteInferior;
    }

    public int getLimiteSuperior() {
        return limiteSuperior;
    }

    public void setLimiteSuperior(int limiteSuperior) {
        this.limiteSuperior = limiteSuperior;
    }

    public Integer getMesVencidoAplicable() {
        return mesVencidoAplicable;
    }

    public void setMesVencidoAplicable(Integer mesVencidoAplicable) {
        this.mesVencidoAplicable = mesVencidoAplicable;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public ComisionCentroDTO getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(ComisionCentroDTO idCentro) {
        this.idCentro = idCentro;
    }

    public ComisionReglaDTO getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(ComisionReglaDTO idRegla) {
        this.idRegla = idRegla;
    }
}
