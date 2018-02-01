package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class AutorizadoresPagoFacturaDTO {

    private Integer codigoVentas;
    private String cedula;
    private String correo;
    private boolean seleccionado;

    public AutorizadoresPagoFacturaDTO() {
    }

    public AutorizadoresPagoFacturaDTO(Integer codigoVentas, String cedula, String correo, boolean seleccionado) {
        this.codigoVentas = codigoVentas;
        this.cedula = cedula;
        this.correo = correo;
        this.seleccionado = seleccionado;
    }

    public Integer getCodigoVentas() {
        return codigoVentas;
    }

    public void setCodigoVentas(Integer codigoVentas) {
        this.codigoVentas = codigoVentas;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
