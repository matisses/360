package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class CotizacionWebDTO {

    private int idVendedor;
    private int nroFacturaSAP;
    private Integer docEntry;
    private Integer progresoCreacion;
    private Integer nroDocPrestashop;
    private Long idCotizacion;
    private String sucursal;
    private String estado;
    private String enviarEmail;
    private String numeroDocSAP;
    private String comentariosProceso;
    private String tiendaRecogida;
    private String nitCliente;
    private boolean imprimir;
    private boolean demostracion;
    private Date fecha;
    private List<DetalleCotizacionWebDTO> detalle;

    public CotizacionWebDTO() {
        detalle = new ArrayList<>();
    }

    public CotizacionWebDTO(int idVendedor, Long idCotizacion, String sucursal, String estado, String nitCliente, Date fecha, List<DetalleCotizacionWebDTO> detalle) {
        this.idVendedor = idVendedor;
        this.idCotizacion = idCotizacion;
        this.sucursal = sucursal;
        this.estado = estado;
        this.nitCliente = nitCliente;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public CotizacionWebDTO(int idVendedor, Long idCotizacion, String sucursal, String estado, String numeroDocSAP, String nitCliente, Date fecha) {
        this.idVendedor = idVendedor;
        this.idCotizacion = idCotizacion;
        this.sucursal = sucursal;
        this.estado = estado;
        this.numeroDocSAP = numeroDocSAP;
        this.nitCliente = nitCliente;
        this.fecha = fecha;
    }

    public CotizacionWebDTO(int idVendedor, int nroFacturaSAP, Integer docEntry, Integer progresoCreacion, Integer nroDocPrestashop,
            Long idCotizacion, String sucursal, String estado, String enviarEmail, String numeroDocSAP, String comentariosProceso,
            String tiendaRecogida, String nitCliente, boolean imprimir, boolean demostracion, Date fecha) {
        this.idVendedor = idVendedor;
        this.nroFacturaSAP = nroFacturaSAP;
        this.docEntry = docEntry;
        this.progresoCreacion = progresoCreacion;
        this.nroDocPrestashop = nroDocPrestashop;
        this.idCotizacion = idCotizacion;
        this.sucursal = sucursal;
        this.estado = estado;
        this.enviarEmail = enviarEmail;
        this.numeroDocSAP = numeroDocSAP;
        this.comentariosProceso = comentariosProceso;
        this.tiendaRecogida = tiendaRecogida;
        this.nitCliente = nitCliente;
        this.imprimir = imprimir;
        this.demostracion = demostracion;
        this.fecha = fecha;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(int idVendedor) {
        this.idVendedor = idVendedor;
    }

    public int getNroFacturaSAP() {
        return nroFacturaSAP;
    }

    public void setNroFacturaSAP(int nroFacturaSAP) {
        this.nroFacturaSAP = nroFacturaSAP;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Integer getProgresoCreacion() {
        return progresoCreacion;
    }

    public void setProgresoCreacion(Integer progresoCreacion) {
        this.progresoCreacion = progresoCreacion;
    }

    public Integer getNroDocPrestashop() {
        return nroDocPrestashop;
    }

    public void setNroDocPrestashop(Integer nroDocPrestashop) {
        this.nroDocPrestashop = nroDocPrestashop;
    }

    public Long getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(Long idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEnviarEmail() {
        return enviarEmail;
    }

    public void setEnviarEmail(String enviarEmail) {
        this.enviarEmail = enviarEmail;
    }

    public String getNumeroDocSAP() {
        return numeroDocSAP;
    }

    public void setNumeroDocSAP(String numeroDocSAP) {
        this.numeroDocSAP = numeroDocSAP;
    }

    public String getComentariosProceso() {
        return comentariosProceso;
    }

    public void setComentariosProceso(String comentariosProceso) {
        this.comentariosProceso = comentariosProceso;
    }

    public String getTiendaRecogida() {
        return tiendaRecogida;
    }

    public void setTiendaRecogida(String tiendaRecogida) {
        this.tiendaRecogida = tiendaRecogida;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public void setNitCliente(String nitCliente) {
        this.nitCliente = nitCliente;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isDemostracion() {
        return demostracion;
    }

    public void setDemostracion(boolean demostracion) {
        this.demostracion = demostracion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetalleCotizacionWebDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleCotizacionWebDTO> detalle) {
        this.detalle = detalle;
    }
}
