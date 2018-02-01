package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author jguisao
 */
public class CodigoRevisadoLabelDTO {

    private String codigoRevisado;
    private String nombreEmpleado;
    private String fecha;
    private int cantidad;

    public CodigoRevisadoLabelDTO() {
    }

    public CodigoRevisadoLabelDTO(String codigoRevisado, String nombreEmpleado, String fecha, int cantidad) {
        this.codigoRevisado = codigoRevisado;
        this.nombreEmpleado = nombreEmpleado;
        this.fecha = fecha;
        this.cantidad = cantidad;
    }

    public String getCodigoRevisado() {
        return codigoRevisado;
    }

    public void setCodigoRevisado(String codigoRevisado) {
        this.codigoRevisado = codigoRevisado;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "CodigoRevisadoLabelDTO{" + "codigoRevisado=" + codigoRevisado + ", nombreEmpleado=" + nombreEmpleado + ", fecha=" + fecha + ", cantidad=" + cantidad + '}';
    }

}
