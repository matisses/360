package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class EncabezadoTrasladoUbicacionDTO {

    private Integer idEncabezadoTrasladoUbicacion;
    private Integer nroTrasladoSAP;
    private String estado;
    private String almacen;
    private String usuario;
    private String cedulaEmpleado;
    private String ubicacion;
    private String movimiento;
    private String ubicacionDestino;
    private Date fecha;

    public EncabezadoTrasladoUbicacionDTO() {
    }

    public EncabezadoTrasladoUbicacionDTO(Integer idEncabezadoTrasladoUbicacion, Integer nroTrasladoSAP, String estado,
            String almacen, String usuario, String cedulaEmpleado, String ubicacion, String movimiento,
            String ubicacionDestino, Date fecha) {
        this.idEncabezadoTrasladoUbicacion = idEncabezadoTrasladoUbicacion;
        this.nroTrasladoSAP = nroTrasladoSAP;
        this.estado = estado;
        this.almacen = almacen;
        this.usuario = usuario;
        this.cedulaEmpleado = cedulaEmpleado;
        this.ubicacion = ubicacion;
        this.movimiento = movimiento;
        this.ubicacionDestino = ubicacionDestino;
        this.fecha = fecha;
    }

    public Integer getIdEncabezadoTrasladoUbicacion() {
        return idEncabezadoTrasladoUbicacion;
    }

    public void setIdEncabezadoTrasladoUbicacion(Integer idEncabezadoTrasladoUbicacion) {
        this.idEncabezadoTrasladoUbicacion = idEncabezadoTrasladoUbicacion;
    }

    public Integer getNroTrasladoSAP() {
        return nroTrasladoSAP;
    }

    public void setNroTrasladoSAP(Integer nroTrasladoSAP) {
        this.nroTrasladoSAP = nroTrasladoSAP;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }
}
