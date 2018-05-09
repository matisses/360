package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EstadosNextDTO {

    private Integer idEstado;
    private String nombreEstado;

    public EstadosNextDTO() {
    }

    public EstadosNextDTO(Integer idEstado, String nombreEstado) {
        this.idEstado = idEstado;
        this.nombreEstado = nombreEstado;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombreEstado() {
        return nombreEstado;
    }

    public void setNombreEstado(String nombreEstado) {
        this.nombreEstado = nombreEstado;
    }
}
