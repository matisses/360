package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ConfiguracionProformaDTO {

    private Integer idConfiguracion;
    private Integer idColumna;
    private Integer idProforma;
    private Integer order;
    private String codigoProveedor;

    public ConfiguracionProformaDTO() {
    }

    public ConfiguracionProformaDTO(Integer idConfiguracion, Integer idColumna, Integer idProforma, Integer order, String codigoProveedor) {
        this.idConfiguracion = idConfiguracion;
        this.idColumna = idColumna;
        this.idProforma = idProforma;
        this.order = order;
        this.codigoProveedor = codigoProveedor;
    }

    public Integer getIdConfiguracion() {
        return idConfiguracion;
    }

    public void setIdConfiguracion(Integer idConfiguracion) {
        this.idConfiguracion = idConfiguracion;
    }

    public Integer getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public String getCodigoProveedor() {
        return codigoProveedor;
    }

    public void setCodigoProveedor(String codigoProveedor) {
        this.codigoProveedor = codigoProveedor;
    }
}
