package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class FiltroDTO {

    private Integer idFiltro;
    private Integer tamanoMinimo;
    private Integer tamanoMaximo;
    private String nombre;
    private String codigoColumna;
    private String tipo;
    private String sufijo;
    private String formato;
    private String fachada;
    private String metodo;
    private String tipoRetorno;
    private boolean multiplesValores;
    private boolean tipoReferencia;

    public FiltroDTO() {
    }

    public FiltroDTO(Integer idFiltro, Integer tamanoMinimo, Integer tamanoMaximo, String nombre, String codigoColumna, String tipo, String sufijo,
            String formato, String fachada, String metodo, String tipoRetorno, boolean multiplesValores, boolean tipoReferencia) {
        this.idFiltro = idFiltro;
        this.tamanoMinimo = tamanoMinimo;
        this.tamanoMaximo = tamanoMaximo;
        this.nombre = nombre;
        this.codigoColumna = codigoColumna;
        this.tipo = tipo;
        this.sufijo = sufijo;
        this.formato = formato;
        this.fachada = fachada;
        this.metodo = metodo;
        this.tipoRetorno = tipoRetorno;
        this.multiplesValores = multiplesValores;
        this.tipoReferencia = tipoReferencia;
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public Integer getTamanoMinimo() {
        return tamanoMinimo;
    }

    public void setTamanoMinimo(Integer tamanoMinimo) {
        this.tamanoMinimo = tamanoMinimo;
    }

    public Integer getTamanoMaximo() {
        return tamanoMaximo;
    }

    public void setTamanoMaximo(Integer tamanoMaximo) {
        this.tamanoMaximo = tamanoMaximo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoColumna() {
        return codigoColumna;
    }

    public void setCodigoColumna(String codigoColumna) {
        this.codigoColumna = codigoColumna;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }

    public String getFachada() {
        return fachada;
    }

    public void setFachada(String fachada) {
        this.fachada = fachada;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public boolean isMultiplesValores() {
        return multiplesValores;
    }

    public void setMultiplesValores(boolean multiplesValores) {
        this.multiplesValores = multiplesValores;
    }

    public boolean isTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(boolean tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }
}
