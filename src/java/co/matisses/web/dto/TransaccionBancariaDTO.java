package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class TransaccionBancariaDTO {

    private Integer idTransaccionBancaria;
    private Integer idCuenta;
    private Integer idProformaInvoice;
    private Double totalInvoice;
    private Double porcentajeAnticipo;
    private Double anticipo;
    private Double anticipoCuenta;
    private Double balance;
    private Double totalGiro;
    private String codProveedor;
    private String descripcion;
    private String usuario;
    private String estado;
    private String tipoGiro;
    private String rutaDocumento;
    private boolean existePdf = false;
    private boolean firmado = false;
    private boolean cancelado = false;
    private Date fecha;
    private Date fechaGiro;

    public TransaccionBancariaDTO() {
    }

    public TransaccionBancariaDTO(Integer idTransaccionBancaria, String codProveedor, Integer idCuenta, String tipoGiro, Integer idProformaInvoice,
            Double totalInvoice, Double porcentajeAnticipo, Double anticipo, Double anticipoCuenta, Double balance, Double totalGiro, String descripcion, String usuario,
            String estado, Date fecha, Date fechaGiro) {
        this.idTransaccionBancaria = idTransaccionBancaria;
        this.codProveedor = codProveedor;
        this.idCuenta = idCuenta;
        this.tipoGiro = tipoGiro;
        this.idProformaInvoice = idProformaInvoice;
        this.totalInvoice = totalInvoice;
        this.porcentajeAnticipo = porcentajeAnticipo;
        this.anticipo = anticipo;
        this.anticipoCuenta = anticipoCuenta;
        this.balance = balance;
        this.totalGiro = totalGiro;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estado = estado;
        this.fecha = fecha;
        this.fechaGiro = fechaGiro;
    }

    public TransaccionBancariaDTO(Integer idTransaccionBancaria, String codProveedor, Integer idCuenta, String tipoGiro, Integer idProformaInvoice,
            Double totalInvoice, Double porcentajeAnticipo, Double anticipo, Double anticipoCuenta, Double balance, Double totalGiro, String descripcion, String usuario,
            String estado, String rutaDocumento, Date fecha, Date fechaGiro, boolean existePdf, boolean firmado, boolean cancelado) {
        this.idTransaccionBancaria = idTransaccionBancaria;
        this.codProveedor = codProveedor;
        this.idCuenta = idCuenta;
        this.tipoGiro = tipoGiro;
        this.idProformaInvoice = idProformaInvoice;
        this.totalInvoice = totalInvoice;
        this.porcentajeAnticipo = porcentajeAnticipo;
        this.anticipo = anticipo;
        this.anticipoCuenta = anticipoCuenta;
        this.balance = balance;
        this.totalGiro = totalGiro;
        this.descripcion = descripcion;
        this.usuario = usuario;
        this.estado = estado;
        this.rutaDocumento = rutaDocumento;
        this.fecha = fecha;
        this.fechaGiro = fechaGiro;
        this.existePdf = existePdf;
        this.firmado = firmado;
        this.cancelado = cancelado;
    }

    public Integer getIdTransaccionBancaria() {
        return idTransaccionBancaria;
    }

    public void setIdTransaccionBancaria(Integer idTransaccionBancaria) {
        this.idTransaccionBancaria = idTransaccionBancaria;
    }

    public Double getTotalInvoice() {
        return totalInvoice;
    }

    public void setTotalInvoice(Double totalInvoice) {
        this.totalInvoice = totalInvoice;
    }

    public Double getPorcentajeAnticipo() {
        return porcentajeAnticipo;
    }

    public void setPorcentajeAnticipo(Double porcentajeAnticipo) {
        this.porcentajeAnticipo = porcentajeAnticipo;
    }

    public Double getAnticipo() {
        return anticipo;
    }

    public void setAnticipo(Double anticipo) {
        this.anticipo = anticipo;
    }

    public Double getAnticipoCuenta() {
        return anticipoCuenta;
    }

    public void setAnticipoCuenta(Double anticipoCuenta) {
        this.anticipoCuenta = anticipoCuenta;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Double getTotalGiro() {
        return totalGiro;
    }

    public void setTotalGiro(Double totalGiro) {
        this.totalGiro = totalGiro;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoGiro() {
        return tipoGiro;
    }

    public void setTipoGiro(String tipoGiro) {
        this.tipoGiro = tipoGiro;
    }

    public Integer getIdProformaInvoice() {
        return idProformaInvoice;
    }

    public void setIdProformaInvoice(Integer idProformaInvoice) {
        this.idProformaInvoice = idProformaInvoice;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getRutaDocumento() {
        return rutaDocumento;
    }

    public void setRutaDocumento(String rutaDocumento) {
        this.rutaDocumento = rutaDocumento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaGiro() {
        return fechaGiro;
    }

    public void setFechaGiro(Date fechaGiro) {
        this.fechaGiro = fechaGiro;
    }

    public boolean isExistePdf() {
        return existePdf;
    }

    public void setExistePdf(boolean existePdf) {
        this.existePdf = existePdf;
    }

    public boolean isFirmado() {
        return firmado;
    }

    public void setFirmado(boolean firmado) {
        this.firmado = firmado;
    }

    public boolean isCancelado() {
        return cancelado;
    }

    public void setCancelado(boolean cancelado) {
        this.cancelado = cancelado;
    }
}
