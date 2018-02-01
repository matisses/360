package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BancoComprasDTO {

    private Integer idBanco;
    private String razonSocial;

    public BancoComprasDTO() {
    }

    public BancoComprasDTO(Integer idBanco, String razonSocial) {
        this.idBanco = idBanco;
        this.razonSocial = razonSocial;
    }

    public Integer getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(Integer idBanco) {
        this.idBanco = idBanco;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
}
