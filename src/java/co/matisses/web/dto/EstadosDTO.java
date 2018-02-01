package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EstadosDTO {

    private Integer idEstado;
    private String nombre;
    private PaisesDTO idPais;

    public EstadosDTO() {
        idPais = new PaisesDTO();
    }

    public EstadosDTO(Integer idEstado, String nombre, PaisesDTO idPais) {
        this.idEstado = idEstado;
        this.nombre = nombre;
        this.idPais = idPais;
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(Integer idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public PaisesDTO getIdPais() {
        return idPais;
    }

    public void setIdPais(PaisesDTO idPais) {
        this.idPais = idPais;
    }
}
