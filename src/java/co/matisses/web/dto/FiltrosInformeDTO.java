package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class FiltrosInformeDTO {

    private Integer idFiltroInforme;
    private Integer idInforme;
    private String valor;
    private String nombreValor;
    private FiltroDTO filtro;

    public FiltrosInformeDTO() {
    }

    public FiltrosInformeDTO(Integer idFiltroInforme, Integer idInforme, String valor, FiltroDTO filtro) {
        this.idFiltroInforme = idFiltroInforme;
        this.idInforme = idInforme;
        this.valor = valor;
        this.filtro = filtro;
    }

    public Integer getIdFiltroInforme() {
        return idFiltroInforme;
    }

    public void setIdFiltroInforme(Integer idFiltroInforme) {
        this.idFiltroInforme = idFiltroInforme;
    }

    public Integer getIdInforme() {
        return idInforme;
    }

    public void setIdInforme(Integer idInforme) {
        this.idInforme = idInforme;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombreValor() {
        return nombreValor;
    }

    public void setNombreValor(String nombreValor) {
        this.nombreValor = nombreValor;
    }

    public FiltroDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroDTO filtro) {
        this.filtro = filtro;
    }
}
