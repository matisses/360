package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class PaisesDTO {

    private Integer idPais;
    private String nombre;
    private String continente;

    public PaisesDTO() {
    }

    public PaisesDTO(Integer idPais) {
        this.idPais = idPais;
    }

    public PaisesDTO(Integer idPais, String nombre, String continente) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.continente = continente;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContinente() {
        return continente;
    }

    public void setContinente(String continente) {
        this.continente = continente;
    }
}
