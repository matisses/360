package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author dbotero
 */
public class LaborCcygaDTO {

    private Integer idLabor;
    private ProcesoCcygaDTO proceso;
    private ProductoCcygaDTO producto;
    private Date inicio;
    private Date fin;
    private Date fecha;
    private String codRevisado;

    public LaborCcygaDTO() {
    }

    public Integer getIdLabor() {
        return idLabor;
    }

    public void setIdLabor(Integer idLabor) {
        this.idLabor = idLabor;
    }

    public ProcesoCcygaDTO getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoCcygaDTO proceso) {
        this.proceso = proceso;
    }

    public ProductoCcygaDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoCcygaDTO producto) {
        this.producto = producto;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getCodRevisado() {
        return codRevisado;
    }

    public void setCodRevisado(String codRevisado) {
        this.codRevisado = codRevisado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
