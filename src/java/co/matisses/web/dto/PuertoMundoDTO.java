package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class PuertoMundoDTO {

    private Integer idPuertoMundo;
    private Integer idPais;
    private String nombrePuerto;

    public PuertoMundoDTO() {
    }

    public PuertoMundoDTO(Integer idPuertoMundo, Integer idPais, String nombrePuerto) {
        this.idPuertoMundo = idPuertoMundo;
        this.idPais = idPais;
        this.nombrePuerto = nombrePuerto;
    }

    public Integer getIdPuertoMundo() {
        return idPuertoMundo;
    }

    public void setIdPuertoMundo(Integer idPuertoMundo) {
        this.idPuertoMundo = idPuertoMundo;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNombrePuerto() {
        return nombrePuerto;
    }

    public void setNombrePuerto(String nombrePuerto) {
        this.nombrePuerto = nombrePuerto;
    }
}
