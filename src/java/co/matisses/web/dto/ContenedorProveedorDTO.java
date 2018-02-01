package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ContenedorProveedorDTO {

    private Integer idContenedorProveedor;
    private Integer idContenedor;
    private Integer cantidad;
    private String codigoContenedor;
    private ContenedorDTO contenedor;

    public ContenedorProveedorDTO() {
    }

    public ContenedorProveedorDTO(Integer idContenedorProveedor, Integer idContenedor, String codigoContenedor) {
        this.idContenedorProveedor = idContenedorProveedor;
        this.idContenedor = idContenedor;
        this.codigoContenedor = codigoContenedor;
    }

    public ContenedorProveedorDTO(Integer idContenedorProveedor, Integer idContenedor, Integer cantidad, String codigoContenedor, ContenedorDTO contenedor) {
        this.idContenedorProveedor = idContenedorProveedor;
        this.idContenedor = idContenedor;
        this.cantidad = cantidad;
        this.codigoContenedor = codigoContenedor;
        this.contenedor = contenedor;
    }

    public Integer getIdContenedorProveedor() {
        return idContenedorProveedor;
    }

    public void setIdContenedorProveedor(Integer idContenedorProveedor) {
        this.idContenedorProveedor = idContenedorProveedor;
    }

    public Integer getIdContenedor() {
        return idContenedor;
    }

    public void setIdContenedor(Integer idContenedor) {
        this.idContenedor = idContenedor;
    }

    public String getCodigoContenedor() {
        return codigoContenedor;
    }

    public void setCodigoContenedor(String codigoContenedor) {
        this.codigoContenedor = codigoContenedor;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public ContenedorDTO getContenedor() {
        return contenedor;
    }

    public void setContenedor(ContenedorDTO contenedor) {
        this.contenedor = contenedor;
    }
}
