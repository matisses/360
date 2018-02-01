package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class EncabezadoExcelDTO {

    private Integer idEncabezadoExcel;
    private Integer columnaInicial;
    private Integer columnaFinal;
    private Integer filaInicial;
    private Integer filaFinal;
    private String objecto;
    private String valor;
    private boolean alineadoDerecha;
    private boolean alineadoIzquierda;
    private boolean alineadoCentro;

    public EncabezadoExcelDTO() {
    }

    public EncabezadoExcelDTO(Integer idEncabezadoExcel, Integer columnaInicial, Integer columnaFinal, Integer filaInicial,
            Integer filaFinal, String objecto, String valor, boolean alineadoDerecha, boolean alineadoIzquierda, boolean alineadoCentro) {
        this.idEncabezadoExcel = idEncabezadoExcel;
        this.columnaInicial = columnaInicial;
        this.columnaFinal = columnaFinal;
        this.filaInicial = filaInicial;
        this.filaFinal = filaFinal;
        this.objecto = objecto;
        this.valor = valor;
        this.alineadoDerecha = alineadoDerecha;
        this.alineadoIzquierda = alineadoIzquierda;
        this.alineadoCentro = alineadoCentro;
    }

    public Integer getIdEncabezadoExcel() {
        return idEncabezadoExcel;
    }

    public void setIdEncabezadoExcel(Integer idEncabezadoExcel) {
        this.idEncabezadoExcel = idEncabezadoExcel;
    }

    public Integer getColumnaInicial() {
        return columnaInicial;
    }

    public void setColumnaInicial(Integer columnaInicial) {
        this.columnaInicial = columnaInicial;
    }

    public Integer getColumnaFinal() {
        return columnaFinal;
    }

    public void setColumnaFinal(Integer columnaFinal) {
        this.columnaFinal = columnaFinal;
    }

    public Integer getFilaInicial() {
        return filaInicial;
    }

    public void setFilaInicial(Integer filaInicial) {
        this.filaInicial = filaInicial;
    }

    public Integer getFilaFinal() {
        return filaFinal;
    }

    public void setFilaFinal(Integer filaFinal) {
        this.filaFinal = filaFinal;
    }

    public String getObjecto() {
        return objecto;
    }

    public void setObjecto(String objecto) {
        this.objecto = objecto;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setAlineadoCentro(boolean alineadoCentro) {
        this.alineadoCentro = alineadoCentro;
    }

    public boolean isAlineadoCentro() {
        return alineadoCentro;
    }

    public void setAlineadoDerecha(boolean alineadoDerecha) {
        this.alineadoDerecha = alineadoDerecha;
    }

    public boolean isAlineadoDerecha() {
        return alineadoDerecha;
    }

    public void setAlineadoIzquierda(boolean alineadoIzquierda) {
        this.alineadoIzquierda = alineadoIzquierda;
    }

    public boolean isAlineadoIzquierda() {
        return alineadoIzquierda;
    }
}
