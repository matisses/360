package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComisionCentroDTO {

    private Integer idCentro;
    private String centroCostos;

    public ComisionCentroDTO() {
    }

    public ComisionCentroDTO(Integer idCentro, String centroCostos) {
        this.idCentro = idCentro;
        this.centroCostos = centroCostos;
    }

    public Integer getIdCentro() {
        return idCentro;
    }

    public void setIdCentro(Integer idCentro) {
        this.idCentro = idCentro;
    }

    public String getCentroCostos() {
        return centroCostos;
    }

    public void setCentroCostos(String centroCostos) {
        this.centroCostos = centroCostos;
    }
}
