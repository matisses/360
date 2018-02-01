package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComponenteCustodiaEmpleadoDTO {

    private Integer idDetalleComponente;
    private Integer cantidad;
    private Integer cantidadBaja;
    private String comentario;
    private ComponenteCustodiaDTO idComponente;
    private DetalleCustodiaDTO idDetalleCustodia;

    public ComponenteCustodiaEmpleadoDTO() {
    }

    public ComponenteCustodiaEmpleadoDTO(Integer idDetalleComponente) {
        this.idDetalleComponente = idDetalleComponente;
    }

    public ComponenteCustodiaEmpleadoDTO(Integer idDetalleComponente, Integer cantidad, Integer cantidadBaja, String comentario, ComponenteCustodiaDTO idComponente, DetalleCustodiaDTO idDetalleCustodia) {
        this.idDetalleComponente = idDetalleComponente;
        this.cantidad = cantidad;
        this.cantidadBaja = cantidadBaja;
        this.comentario = comentario;
        this.idComponente = idComponente;
        this.idDetalleCustodia = idDetalleCustodia;
    }

    public Integer getIdDetalleComponente() {
        return idDetalleComponente;
    }

    public void setIdDetalleComponente(Integer idDetalleComponente) {
        this.idDetalleComponente = idDetalleComponente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadBaja() {
        return cantidadBaja;
    }

    public void setCantidadBaja(Integer cantidadBaja) {
        this.cantidadBaja = cantidadBaja;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public ComponenteCustodiaDTO getIdComponente() {
        return idComponente;
    }

    public void setIdComponente(ComponenteCustodiaDTO idComponente) {
        this.idComponente = idComponente;
    }

    public DetalleCustodiaDTO getIdDetalleCustodia() {
        return idDetalleCustodia;
    }

    public void setIdDetalleCustodia(DetalleCustodiaDTO idDetalleCustodia) {
        this.idDetalleCustodia = idDetalleCustodia;
    }
}
