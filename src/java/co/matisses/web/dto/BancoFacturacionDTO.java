package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BancoFacturacionDTO {

    private Integer idBancoFacturacion;
    private String nombreBanco;
    private String franquicia;

    public BancoFacturacionDTO() {
    }

    public BancoFacturacionDTO(Integer idBancoFacturacion, String nombreBanco) {
        this.idBancoFacturacion = idBancoFacturacion;
        this.nombreBanco = nombreBanco;
    }

    public BancoFacturacionDTO(Integer idBancoFacturacion, String nombreBanco, String franquicia) {
        this.idBancoFacturacion = idBancoFacturacion;
        this.nombreBanco = nombreBanco;
        this.franquicia = franquicia;
    }

    public Integer getIdBancoFacturacion() {
        return idBancoFacturacion;
    }

    public void setIdBancoFacturacion(Integer idBancoFacturacion) {
        this.idBancoFacturacion = idBancoFacturacion;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public String getFranquicia() {
        return franquicia;
    }

    public void setFranquicia(String franquicia) {
        this.franquicia = franquicia;
    }
}
