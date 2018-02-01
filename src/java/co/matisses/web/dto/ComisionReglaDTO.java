package co.matisses.web.dto;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author ygil
 */
public class ComisionReglaDTO {

    private Integer idRegla;
    private boolean query;
    private boolean complementos;
    private Date validoHasta;
    private ComisionTipoReglaDTO idTipoRegla;
    private Map<String, Double> valorRegla;

    public ComisionReglaDTO() {
    }

    public ComisionReglaDTO(Integer idRegla, boolean query, boolean complementos, Date validoHasta, ComisionTipoReglaDTO idTipoRegla, Map<String, Double> valorRegla) {
        this.idRegla = idRegla;
        this.query = query;
        this.complementos = complementos;
        this.validoHasta = validoHasta;
        this.idTipoRegla = idTipoRegla;
        this.valorRegla = valorRegla;
    }

    public Integer getIdRegla() {
        return idRegla;
    }

    public void setIdRegla(Integer idRegla) {
        this.idRegla = idRegla;
    }

    public boolean isQuery() {
        return query;
    }

    public void setQuery(boolean query) {
        this.query = query;
    }

    public boolean isComplementos() {
        return complementos;
    }

    public void setComplementos(boolean complementos) {
        this.complementos = complementos;
    }

    public Date getValidoHasta() {
        return validoHasta;
    }

    public void setValidoHasta(Date validoHasta) {
        this.validoHasta = validoHasta;
    }

    public ComisionTipoReglaDTO getIdTipoRegla() {
        return idTipoRegla;
    }

    public void setIdTipoRegla(ComisionTipoReglaDTO idTipoRegla) {
        this.idTipoRegla = idTipoRegla;
    }

    public Map<String, Double> getValorRegla() {
        return valorRegla;
    }

    public void setValorRegla(Map<String, Double> valorRegla) {
        this.valorRegla = valorRegla;
    }
}
