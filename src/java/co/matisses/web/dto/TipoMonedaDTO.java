package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TipoMonedaDTO {

    private Integer idTipoMoneda;
    private String simbolo;
    private String moneda;
    private boolean activo = false;

    public TipoMonedaDTO() {
    }

    public TipoMonedaDTO(Integer idTipoMoneda, String simbolo, String moneda) {
        this.idTipoMoneda = idTipoMoneda;
        this.simbolo = simbolo;
        this.moneda = moneda;
    }

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(Integer idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
