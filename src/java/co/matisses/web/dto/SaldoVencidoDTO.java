package co.matisses.web.dto;

import java.text.NumberFormat;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class SaldoVencidoDTO {

    private Long idSaldoVencido;
    private String nit;
    private String nombre;
    private Integer saldo;
    private String usuario;
    private Date fecha;

    public SaldoVencidoDTO() {
    }
    
    public SaldoVencidoDTO(String nit, String nombre, Integer saldo) {
        this.nit = nit;
        this.nombre = nombre;
        this.saldo = saldo;
    }

    public SaldoVencidoDTO(Long idSaldoVencido, String nit, String nombre, Integer saldo, String usuario, Date fecha) {
        this.idSaldoVencido = idSaldoVencido;
        this.nit = nit;
        this.nombre = nombre;
        this.saldo = saldo;
        this.usuario = usuario;
        this.fecha = fecha;
    }

    public Long getIdSaldoVencido() {
        return idSaldoVencido;
    }

    public void setIdSaldoVencido(Long idSaldoVencido) {
        this.idSaldoVencido = idSaldoVencido;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getFormattedNit() {
        if (nit == null) {
            return "";
        }
        return "NIT: " + nit.replace("CL", "").replace("PR", "");
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public String getSaldoStr() {
        if (saldo == null) {
            return "";
        }
        return NumberFormat.getInstance().format(saldo);
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "SaldoVencidoDTO{" + "idSaldoVencido=" + idSaldoVencido + ", nit=" + nit + ", nombre=" + nombre + ", saldo=" + saldo + ", usuario=" + usuario + ", fecha=" + fecha + '}';
    }

}
