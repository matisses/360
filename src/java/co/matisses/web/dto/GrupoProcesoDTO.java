package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class GrupoProcesoDTO {

    private Integer producto;
    private String referencia;
    private String tiempoTotal;
    private List<HistoricoProductoCcygaDTO> historico;

    public GrupoProcesoDTO() {
        historico = new ArrayList<>();
    }

    public GrupoProcesoDTO(Integer producto) {
        this.producto = producto;
    }

    public GrupoProcesoDTO(Integer producto, String referencia, String tiempoTotal, List<HistoricoProductoCcygaDTO> historico) {
        this.producto = producto;
        this.referencia = referencia;
        this.tiempoTotal = tiempoTotal;
        this.historico = historico;
    }

    public Integer getProducto() {
        return producto;
    }

    public void setProducto(Integer producto) {
        this.producto = producto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(String tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public List<HistoricoProductoCcygaDTO> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoProductoCcygaDTO> historico) {
        this.historico = historico;
    }
}
