package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TipoGiroDTO {

    private Integer idTipoGiro;
    private String tipo;
    private boolean activo;

    public TipoGiroDTO() {
    }

    public TipoGiroDTO(Integer idTipoGiro, String tipo, Boolean activo) {
        this.idTipoGiro = idTipoGiro;
        this.tipo = tipo;
        this.activo = activo;
    }

    public Integer getIdTipoGiro() {
        return idTipoGiro;
    }

    public void setIdTipoGiro(Integer idTipoGiro) {
        this.idTipoGiro = idTipoGiro;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
