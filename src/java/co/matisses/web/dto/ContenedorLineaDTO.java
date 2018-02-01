package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ContenedorLineaDTO {

    private Integer idContenedorLinea;
    private Integer cantidad;
    private Integer idDetalleProforma;
    private String usuario;
    private Date fecha;
    private ContenedorProformaDTO contenedorProforma;

    public ContenedorLineaDTO() {
    }

    public ContenedorLineaDTO(Integer idContenedorLinea, Integer cantidad, Integer idDetalleProforma, String usuario, Date fecha, ContenedorProformaDTO contenedorProforma) {
        this.idContenedorLinea = idContenedorLinea;
        this.cantidad = cantidad;
        this.idDetalleProforma = idDetalleProforma;
        this.usuario = usuario;
        this.fecha = fecha;
        this.contenedorProforma = contenedorProforma;
    }

    public Integer getIdContenedorLinea() {
        return idContenedorLinea;
    }

    public void setIdContenedorLinea(Integer idContenedorLinea) {
        this.idContenedorLinea = idContenedorLinea;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdDetalleProforma() {
        return idDetalleProforma;
    }

    public void setIdDetalleProforma(Integer idDetalleProforma) {
        this.idDetalleProforma = idDetalleProforma;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public ContenedorProformaDTO getContenedorProforma() {
        return contenedorProforma;
    }

    public void setContenedorProforma(ContenedorProformaDTO contenedorProforma) {
        this.contenedorProforma = contenedorProforma;
    }
}
