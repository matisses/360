package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DiferenciasConteoDTO {

    private Integer idConteo;
    private long esperado;
    private long encontrado;
    private Long idDiferenciaConteo;
    private String referencia;
    private String comentarios;
    private String tipo;
    private boolean resuelta;

    public DiferenciasConteoDTO() {
    }

    public DiferenciasConteoDTO(Integer idConteo, long esperado, long encontrado, Long idDiferenciaConteo, String referencia, String comentarios, String tipo, boolean resuelta) {
        this.idConteo = idConteo;
        this.esperado = esperado;
        this.encontrado = encontrado;
        this.idDiferenciaConteo = idDiferenciaConteo;
        this.referencia = referencia;
        this.comentarios = comentarios;
        this.tipo = tipo;
        this.resuelta = resuelta;
    }

    public Integer getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(Integer idConteo) {
        this.idConteo = idConteo;
    }

    public long getEsperado() {
        return esperado;
    }

    public void setEsperado(long esperado) {
        this.esperado = esperado;
    }

    public long getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(long encontrado) {
        this.encontrado = encontrado;
    }

    public Long getIdDiferenciaConteo() {
        return idDiferenciaConteo;
    }

    public void setIdDiferenciaConteo(Long idDiferenciaConteo) {
        this.idDiferenciaConteo = idDiferenciaConteo;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isResuelta() {
        return resuelta;
    }

    public void setResuelta(boolean resuelta) {
        this.resuelta = resuelta;
    }
}
