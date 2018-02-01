package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DocumentoComisionDTO {

    private Integer documento;
    private String tipo;
    private String comentario;

    public DocumentoComisionDTO() {
    }

    public DocumentoComisionDTO(Integer documento, String tipo, String comentario) {
        this.documento = documento;
        this.tipo = tipo;
        this.comentario = comentario;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
