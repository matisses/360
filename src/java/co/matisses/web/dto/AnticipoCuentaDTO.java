package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class AnticipoCuentaDTO {

    private Integer idAnticipoCuenta;
    private Double valor;
    private String comentario;
    private String usuario;
    private String codProveedor;
    private Date fecha;
    private Date fechaGiro;
    private TipoMonedaDTO idTipoMoneda;
    private CuentaBancariaProveedorDTO idCuentaBancaria;
    private ProformaInvoiceDTO idProforma;

    public AnticipoCuentaDTO() {
    }

    public AnticipoCuentaDTO(Integer idAnticipoCuenta, Double valor, String comentario, String usuario, String codProveedor, Date fecha,
            TipoMonedaDTO idTipoMoneda, CuentaBancariaProveedorDTO idCuentaBancaria, ProformaInvoiceDTO idProforma) {
        this.idAnticipoCuenta = idAnticipoCuenta;
        this.valor = valor;
        this.comentario = comentario;
        this.usuario = usuario;
        this.codProveedor = codProveedor;
        this.fecha = fecha;
        this.idTipoMoneda = idTipoMoneda;
        this.idCuentaBancaria = idCuentaBancaria;
        this.idProforma = idProforma;
    }

    public AnticipoCuentaDTO(Integer idAnticipoCuenta, Double valor, String comentario, String usuario, String codProveedor, Date fecha,
            Date fechaGiro, TipoMonedaDTO idTipoMoneda, CuentaBancariaProveedorDTO idCuentaBancaria, ProformaInvoiceDTO idProforma) {
        this.idAnticipoCuenta = idAnticipoCuenta;
        this.valor = valor;
        this.comentario = comentario;
        this.usuario = usuario;
        this.codProveedor = codProveedor;
        this.fecha = fecha;
        this.fechaGiro = fechaGiro;
        this.idTipoMoneda = idTipoMoneda;
        this.idCuentaBancaria = idCuentaBancaria;
        this.idProforma = idProforma;
    }

    public Integer getIdAnticipoCuenta() {
        return idAnticipoCuenta;
    }

    public void setIdAnticipoCuenta(Integer idAnticipoCuenta) {
        this.idAnticipoCuenta = idAnticipoCuenta;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
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

    public TipoMonedaDTO getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(TipoMonedaDTO idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public CuentaBancariaProveedorDTO getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public void setIdCuentaBancaria(CuentaBancariaProveedorDTO idCuentaBancaria) {
        this.idCuentaBancaria = idCuentaBancaria;
    }

    public ProformaInvoiceDTO getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(ProformaInvoiceDTO idProforma) {
        this.idProforma = idProforma;
    }
}
