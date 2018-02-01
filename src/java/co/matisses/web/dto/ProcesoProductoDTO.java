package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class ProcesoProductoDTO {
    private Integer idProcesoProducto;
    private Integer idProducto;
    private Integer idProceso;
    private String nombreProceso;
    private String comentario;

    public ProcesoProductoDTO() {
    }

    public Integer getIdProcesoProducto() {
        return idProcesoProducto;
    }

    public void setIdProcesoProducto(Integer idProcesoProducto) {
        this.idProcesoProducto = idProcesoProducto;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdProceso() {
        return idProceso;
    }

    public void setIdProceso(Integer idProceso) {
        this.idProceso = idProceso;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }
}
