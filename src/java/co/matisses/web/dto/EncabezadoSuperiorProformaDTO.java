package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EncabezadoSuperiorProformaDTO {

    private Integer numeroColumna;
    private Integer ultimaColumna;
    private Integer fila;
    private Integer ultimaFila;
    private String nombreColumna;
    private boolean titulo = false;
    private boolean wrapText = false;
    private boolean infoImportante = false;

    public EncabezadoSuperiorProformaDTO() {
    }

    public EncabezadoSuperiorProformaDTO(Integer numeroColumna, Integer ultimaColumna, Integer fila, Integer ultimaFila, String nombreColumna, boolean titulo, boolean infoImportante) {
        this.numeroColumna = numeroColumna;
        this.ultimaColumna = ultimaColumna;
        this.fila = fila;
        this.ultimaFila = ultimaFila;
        this.nombreColumna = nombreColumna;
        this.titulo = titulo;
        this.infoImportante = infoImportante;
    }

    public Integer getNumeroColumna() {
        return numeroColumna;
    }

    public void setNumeroColumna(Integer numeroColumna) {
        this.numeroColumna = numeroColumna;
    }

    public Integer getUltimaColumna() {
        return ultimaColumna;
    }

    public void setUltimaColumna(Integer ultimaColumna) {
        this.ultimaColumna = ultimaColumna;
    }

    public Integer getFila() {
        return fila;
    }

    public void setFila(Integer fila) {
        this.fila = fila;
    }

    public Integer getUltimaFila() {
        return ultimaFila;
    }

    public void setUltimaFila(Integer ultimaFila) {
        this.ultimaFila = ultimaFila;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    public void setNombreColumna(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public boolean isTitulo() {
        return titulo;
    }

    public void setTitulo(boolean titulo) {
        this.titulo = titulo;
    }

    public boolean isWrapText() {
        return wrapText;
    }

    public void setWrapText(boolean wrapText) {
        this.wrapText = wrapText;
    }

    public boolean isInfoImportante() {
        return infoImportante;
    }

    public void setInfoImportante(boolean infoImportante) {
        this.infoImportante = infoImportante;
    }
}
