package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ComponenteCustodiaDTO {

    private Integer idComponenteCustodia;
    private String nombreComponente;
    private boolean principal;
    private CustodiaDTO idCustodia;

    public ComponenteCustodiaDTO() {
    }

    public ComponenteCustodiaDTO(Integer idComponenteCustodia) {
        this.idComponenteCustodia = idComponenteCustodia;
    }

    public ComponenteCustodiaDTO(Integer idComponenteCustodia, String nombreComponente, boolean principal, CustodiaDTO idCustodia) {
        this.idComponenteCustodia = idComponenteCustodia;
        this.nombreComponente = nombreComponente;
        this.principal = principal;
        this.idCustodia = idCustodia;
    }

    public Integer getIdComponenteCustodia() {
        return idComponenteCustodia;
    }

    public void setIdComponenteCustodia(Integer idComponenteCustodia) {
        this.idComponenteCustodia = idComponenteCustodia;
    }

    public String getNombreComponente() {
        return nombreComponente;
    }

    public void setNombreComponente(String nombreComponente) {
        this.nombreComponente = nombreComponente;
    }

    public boolean isPrincipal() {
        return principal;
    }

    public void setPrincipal(boolean principal) {
        this.principal = principal;
    }

    public CustodiaDTO getIdCustodia() {
        return idCustodia;
    }

    public void setIdCustodia(CustodiaDTO idCustodia) {
        this.idCustodia = idCustodia;
    }
}
