package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TipoDatosDTO {

    private Integer idTipoDato;
    private String tipoDato;

    public TipoDatosDTO() {
    }

    public TipoDatosDTO(Integer idTipoDato, String tipoDato) {
        this.idTipoDato = idTipoDato;
        this.tipoDato = tipoDato;
    }

    public Integer getIdTipoDato() {
        return idTipoDato;
    }

    public void setIdTipoDato(Integer idTipoDato) {
        this.idTipoDato = idTipoDato;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }
}
