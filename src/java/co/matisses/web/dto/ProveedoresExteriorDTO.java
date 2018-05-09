package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ProveedoresExteriorDTO {

    private String proveedor;
    private String nombreProveedor;
    private boolean complemento;
    private boolean consignacion;

    public ProveedoresExteriorDTO() {
    }

    public ProveedoresExteriorDTO(String proveedor, String nombreProveedor, boolean complemento, boolean consignacion) {
        this.proveedor = proveedor;
        this.nombreProveedor = nombreProveedor;
        this.complemento = complemento;
        this.consignacion = consignacion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getNombreProveedor() {
        return nombreProveedor;
    }

    public void setNombreProveedor(String nombreProveedor) {
        this.nombreProveedor = nombreProveedor;
    }

    public boolean isComplemento() {
        return complemento;
    }

    public void setComplemento(boolean complemento) {
        this.complemento = complemento;
    }

    public boolean isConsignacion() {
        return consignacion;
    }

    public void setConsignacion(boolean consignacion) {
        this.consignacion = consignacion;
    }
}
