package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class IncotermDTO {

    private Integer idIncoterm;
    private String codigo;
    private String descripcion;

    public IncotermDTO() {
    }

    public IncotermDTO(Integer idIncoterm, String codigo, String descripcion) {
        this.idIncoterm = idIncoterm;
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public Integer getIdIncoterm() {
        return idIncoterm;
    }

    public void setIdIncoterm(Integer idIncoterm) {
        this.idIncoterm = idIncoterm;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
