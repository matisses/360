package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComisionTipoReglaDTO {

    private Integer idTipoRegla;
    private String tipoRegla;

    public ComisionTipoReglaDTO() {
    }

    public ComisionTipoReglaDTO(Integer idTipoRegla, String tipoRegla) {
        this.idTipoRegla = idTipoRegla;
        this.tipoRegla = tipoRegla;
    }

    public Integer getIdTipoRegla() {
        return idTipoRegla;
    }

    public void setIdTipoRegla(Integer idTipoRegla) {
        this.idTipoRegla = idTipoRegla;
    }

    public String getTipoRegla() {
        return tipoRegla;
    }

    public void setTipoRegla(String tipoRegla) {
        this.tipoRegla = tipoRegla;
    }
}
