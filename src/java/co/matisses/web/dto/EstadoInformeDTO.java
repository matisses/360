package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EstadoInformeDTO {

    private Integer idEstadoInforme;
    private String nombre;

    public EstadoInformeDTO() {
    }

    public EstadoInformeDTO(Integer idEstadoInforme, String nombre) {
        this.idEstadoInforme = idEstadoInforme;
        this.nombre = nombre;
    }

    public Integer getIdEstadoInforme() {
        return idEstadoInforme;
    }

    public void setIdEstadoInforme(Integer idEstadoInforme) {
        this.idEstadoInforme = idEstadoInforme;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
