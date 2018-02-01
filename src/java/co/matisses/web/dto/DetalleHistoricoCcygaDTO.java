package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author dbotero
 */
public class DetalleHistoricoCcygaDTO {

    private Integer idLabor;
    private Date inicio;
    private Date fin;
    private Date fecha;
    private String codRevisado;
    private String nombreEmpleado;
    private boolean finalizado = false;

    public DetalleHistoricoCcygaDTO() {
    }

    public Integer getIdLabor() {
        return idLabor;
    }

    public void setIdLabor(Integer idLabor) {
        this.idLabor = idLabor;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
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

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getCodRevisado() {
        return codRevisado;
    }

    public void setCodRevisado(String codRevisado) {
        this.codRevisado = codRevisado;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
    
    public String getDuracion(){
        long tiempoProceso = fin.getTime() - inicio.getTime();
        long minute = (tiempoProceso / (1000 * 60)) % 60;
        long hour = (tiempoProceso / (1000 * 60 * 60)) % 24;
        
        long hours = (tiempoProceso / 1000 / 60 / 60);
        long mins = (tiempoProceso - (hours * 1000 * 60 * 60)) / 1000 / 60;

        return String.format("%02d:%02d", hours, mins);
    }
}
