package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class ConsultaProductosListaDTO {

    private long idLista;
    private int pagina;
    private int registrosPagina;
    private String orderBy;
    private String sortOrder;

    public ConsultaProductosListaDTO() {
    }

    public long getIdLista() {
        return idLista;
    }

    public void setIdLista(long idLista) {
        this.idLista = idLista;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getRegistrosPagina() {
        return registrosPagina;
    }

    public void setRegistrosPagina(int registrosPagina) {
        this.registrosPagina = registrosPagina;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
