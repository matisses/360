package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TipoRespuestaDTO {

    private Integer idTipoRespuesta;
    private String tipoRespuesta;

    public TipoRespuestaDTO() {
    }

    public TipoRespuestaDTO(Integer idTipoRespuesta, String tipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
        this.tipoRespuesta = tipoRespuesta;
    }

    public Integer getIdTipoRespuesta() {
        return idTipoRespuesta;
    }

    public void setIdTipoRespuesta(Integer idTipoRespuesta) {
        this.idTipoRespuesta = idTipoRespuesta;
    }

    public String getTipoRespuesta() {
        return tipoRespuesta;
    }

    public void setTipoRespuesta(String tipoRespuesta) {
        this.tipoRespuesta = tipoRespuesta;
    }
}
