package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ProgramacionDescuentoDTO {

    private Double porcentaje;
    private Long idReglaDescuento;
    private String referencia;
    private String canal;
    private Date fechaInicio;
    private Date fechaFin;

    public ProgramacionDescuentoDTO() {
    }

    public ProgramacionDescuentoDTO(Double porcentaje, Long idReglaDescuento, String referencia, String canal, Date fechaInicio, Date fechaFin) {
        this.porcentaje = porcentaje;
        this.idReglaDescuento = idReglaDescuento;
        this.referencia = referencia;
        this.canal = canal;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Double getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Double porcentaje) {
        this.porcentaje = porcentaje;
    }

    public Long getIdReglaDescuento() {
        return idReglaDescuento;
    }

    public void setIdReglaDescuento(Long idReglaDescuento) {
        this.idReglaDescuento = idReglaDescuento;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String canal) {
        this.canal = canal;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
}
