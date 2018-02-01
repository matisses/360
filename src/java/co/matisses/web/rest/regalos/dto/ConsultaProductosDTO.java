package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;
import co.matisses.web.rest.regalos.FiltroProducto;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ConsultaProductosDTO {

    private int pagina;
    private int registrosPagina;
    private HashMap<String, List<FiltroProducto>> filtros;
    private String orderBy;
    private String sortOrder;
    private boolean conSaldo;

    public ConsultaProductosDTO() {
        filtros = new HashMap<>();
    }

    public ConsultaProductosDTO(int pagina, int registrosPagina, String orderBy, String sortOrder, boolean conSaldo) {
        this.pagina = pagina;
        this.registrosPagina = registrosPagina;
        this.orderBy = orderBy;
        this.sortOrder = sortOrder;
        this.conSaldo = conSaldo;
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

    public HashMap<String, List<FiltroProducto>> getFiltros() {
        return filtros;
    }

    public void setFiltros(HashMap<String, List<FiltroProducto>> filtros) {
        this.filtros = filtros;
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

    public boolean isConSaldo() {
        return conSaldo;
    }

    public void setConSaldo(boolean conSaldo) {
        this.conSaldo = conSaldo;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
