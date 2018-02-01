package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ItemDisponiblesAsignacionDTO {

    private Integer idColumna;
    private Integer linea;
    private String valor;
    private boolean aplicar = false;

    public ItemDisponiblesAsignacionDTO() {
    }

    public ItemDisponiblesAsignacionDTO(Integer idColumna, Integer linea, String valor) {
        this.idColumna = idColumna;
        this.linea = linea;
        this.valor = valor;
    }

    public Integer getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(Integer idColumna) {
        this.idColumna = idColumna;
    }

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isAplicar() {
        return aplicar;
    }

    public void setAplicar(boolean aplicar) {
        this.aplicar = aplicar;
    }
}
