package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TarjetaFacturacionDTO {

    private Integer idTarjetaFacturacion;
    private Integer idBancoFacturacion;
    private String nombre;
    private String tipo;

    public TarjetaFacturacionDTO() {
    }

    public TarjetaFacturacionDTO(Integer idTarjetaFacturacion, Integer idBancoFacturacion, String nombre, String tipo) {
        this.idTarjetaFacturacion = idTarjetaFacturacion;
        this.idBancoFacturacion = idBancoFacturacion;
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public Integer getIdTarjetaFacturacion() {
        return idTarjetaFacturacion;
    }

    public void setIdTarjetaFacturacion(Integer idTarjetaFacturacion) {
        this.idTarjetaFacturacion = idTarjetaFacturacion;
    }

    public Integer getIdBancoFacturacion() {
        return idBancoFacturacion;
    }

    public void setIdBancoFacturacion(Integer idBancoFacturacion) {
        this.idBancoFacturacion = idBancoFacturacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
