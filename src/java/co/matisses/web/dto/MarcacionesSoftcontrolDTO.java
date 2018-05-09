package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class MarcacionesSoftcontrolDTO implements Comparable<MarcacionesSoftcontrolDTO> {

    private Integer tipoMarcacion;
    private String cedulaEmpleado;
    private String nombreEmpleado;
    private String ip;
    private String excepcion;
    private boolean error = false;
    private Date fechaHora;

    public MarcacionesSoftcontrolDTO() {
    }

    public Integer getTipoMarcacion() {
        return tipoMarcacion;
    }

    public void setTipoMarcacion(Integer tipoMarcacion) {
        this.tipoMarcacion = tipoMarcacion;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getExcepcion() {
        return excepcion;
    }

    public void setExcepcion(String excepcion) {
        this.excepcion = excepcion;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(Date fechaHora) {
        this.fechaHora = fechaHora;
    }

    @Override
    public int compareTo(MarcacionesSoftcontrolDTO o) {
        return this.getFechaHora().compareTo(o.getFechaHora());
    }

    @Override
    public String toString() {
        return "MarcacionesSoftcontrolDTO{" + "tipoMarcacion=" + tipoMarcacion + ", cedulaEmpleado=" + cedulaEmpleado + ", nombreEmpleado=" + nombreEmpleado
                + ", ip=" + ip + ", excepcion=" + excepcion + ", fechaHora=" + fechaHora + '}';
    }
}
