package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class CategoriaColeccionDTO {

    private Integer idCategoriaColeccion;
    private String codigoCategoria;
    private String nombreCategoria;

    public CategoriaColeccionDTO() {
    }

    public CategoriaColeccionDTO(Integer idCategoriaColeccion, String codigoCategoria, String nombreCategoria) {
        this.idCategoriaColeccion = idCategoriaColeccion;
        this.codigoCategoria = codigoCategoria;
        this.nombreCategoria = nombreCategoria;
    }

    public Integer getIdCategoriaColeccion() {
        return idCategoriaColeccion;
    }

    public void setIdCategoriaColeccion(Integer idCategoriaColeccion) {
        this.idCategoriaColeccion = idCategoriaColeccion;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }

    public String getNombreCategoria() {
        return nombreCategoria;
    }

    public void setNombreCategoria(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}
