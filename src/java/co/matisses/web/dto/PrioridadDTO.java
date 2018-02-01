package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class PrioridadDTO {

    private Integer idPrioridad;
    private String prioridad;

    public PrioridadDTO() {
    }

    public PrioridadDTO(Integer idPrioridad, String prioridad) {
        this.idPrioridad = idPrioridad;
        this.prioridad = prioridad;
    }

    public Integer getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(Integer idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }
}
