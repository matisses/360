package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class OperacionDTO {

    private Integer idOperacion;
    private String operacion;

    public OperacionDTO() {
    }

    public OperacionDTO(Integer idOperacion, String operacion) {
        this.idOperacion = idOperacion;
        this.operacion = operacion;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(Integer idOperacion) {
        this.idOperacion = idOperacion;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }
}
