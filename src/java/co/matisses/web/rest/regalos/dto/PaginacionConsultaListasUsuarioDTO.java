package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class PaginacionConsultaListasUsuarioDTO {

    private int pagina;
    private int registrosPagina;
    private String orderBy;
    private String sortOrder;

    public PaginacionConsultaListasUsuarioDTO() {
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getPagina() {
        return pagina;
    }

    public void setRegistrosPagina(int registrosPagina) {
        this.registrosPagina = registrosPagina;
    }

    public int getRegistrosPagina() {
        return registrosPagina;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
