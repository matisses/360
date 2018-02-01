package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class DeclaracionesImportacionDTO {

    private int cantidad;
    private Integer idDIM;
    private Integer saldo;
    private Integer nroFila;
    private String numimp;
    private String do1;
    private String dim;
    private String referencia;
    private String usuario;
    private Date fecha;
    private Date fechaInsercion;

    public DeclaracionesImportacionDTO() {
    }

    public DeclaracionesImportacionDTO(int cantidad, Integer idDIM, Integer saldo, Integer nroFila, String numimp, String do1, String dim, String referencia, String usuario, Date fecha, Date fechaInsercion) {
        this.cantidad = cantidad;
        this.idDIM = idDIM;
        this.saldo = saldo;
        this.nroFila = nroFila;
        this.numimp = numimp;
        this.do1 = do1;
        this.dim = dim;
        this.referencia = referencia;
        this.usuario = usuario;
        this.fecha = fecha;
        this.fechaInsercion = fechaInsercion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdDIM() {
        return idDIM;
    }

    public void setIdDIM(Integer idDIM) {
        this.idDIM = idDIM;
    }

    public Integer getSaldo() {
        return saldo;
    }

    public void setSaldo(Integer saldo) {
        this.saldo = saldo;
    }

    public Integer getNroFila() {
        return nroFila;
    }

    public void setNroFila(Integer nroFila) {
        this.nroFila = nroFila;
    }

    public String getNumimp() {
        return numimp;
    }

    public void setNumimp(String numimp) {
        this.numimp = numimp;
    }

    public String getDo1() {
        return do1;
    }

    public void setDo1(String do1) {
        this.do1 = do1;
    }

    public String getDim() {
        return dim;
    }

    public void setDim(String dim) {
        this.dim = dim;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
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

    public Date getFechaInsercion() {
        return fechaInsercion;
    }

    public void setFechaInsercion(Date fechaInsercion) {
        this.fechaInsercion = fechaInsercion;
    }
}
