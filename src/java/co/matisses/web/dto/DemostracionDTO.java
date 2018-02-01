package co.matisses.web.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class DemostracionDTO {

    private int idDemostracion;
    private Integer facturaAsociada;
    private Integer codigoAsesor;
    private String slot;
    private String nombreDemostracion;
    private String documentoCliente;
    private String razonSocialCliente;
    private String estado;
    private Date fechaDemostracion;
    private Date fechaVencimiento;

    public DemostracionDTO() {
    }

    public DemostracionDTO(int idDemostracion, Integer facturaAsociada, Integer codigoAsesor, String slot, String nombreDemostracion,
            String documentoCliente, String razonSocialCliente, Date fechaDemostracion, Date fechaVencimiento) throws ParseException {
        this.idDemostracion = idDemostracion;
        this.facturaAsociada = facturaAsociada;
        this.codigoAsesor = codigoAsesor;
        this.slot = slot;
        this.nombreDemostracion = nombreDemostracion;
        this.documentoCliente = documentoCliente;
        this.razonSocialCliente = razonSocialCliente;
        this.fechaDemostracion = fechaDemostracion;
        this.fechaVencimiento = fechaVencimiento;
        obtenerEstadoDemo();
    }

    public int getIdDemostracion() {
        return idDemostracion;
    }

    public void setIdDemostracion(int idDemostracion) {
        this.idDemostracion = idDemostracion;
    }

    public Integer getFacturaAsociada() {
        return facturaAsociada;
    }

    public void setFacturaAsociada(Integer facturaAsociada) {
        this.facturaAsociada = facturaAsociada;
    }

    public Integer getCodigoAsesor() {
        return codigoAsesor;
    }

    public void setCodigoAsesor(Integer codigoAsesor) {
        this.codigoAsesor = codigoAsesor;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public String getNombreDemostracion() {
        return nombreDemostracion;
    }

    public void setNombreDemostracion(String nombreDemostracion) {
        this.nombreDemostracion = nombreDemostracion;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public String getRazonSocialCliente() {
        return razonSocialCliente;
    }

    public void setRazonSocialCliente(String razonSocialCliente) {
        this.razonSocialCliente = razonSocialCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaDemostracion() {
        return fechaDemostracion;
    }

    public void setFechaDemostracion(Date fechaDemostracion) {
        this.fechaDemostracion = fechaDemostracion;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    private void obtenerEstadoDemo() throws ParseException {
        if (fechaVencimiento != null) {
            String fechaActualFormateada = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            Date fechaActual = new SimpleDateFormat("yyyy-MM-dd").parse(fechaActualFormateada);

            if (fechaVencimiento.before(fechaActual)) {
                estado = "VENCIDA";
            } else {
                estado = "ACTIVA";
            }
        }
    }
}
