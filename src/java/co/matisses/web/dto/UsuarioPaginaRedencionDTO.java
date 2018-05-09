package co.matisses.web.dto;

import co.matisses.persistence.web.entity.UsuarioPaginaRedencion;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class UsuarioPaginaRedencionDTO {

    private Integer idRedencion;
    private String tipo;
    private String modo;
    private String estado;
    private BigDecimal monto;
    private Date fecha;
    private UsuarioPaginaDTO usuarioPagina;

    public UsuarioPaginaRedencionDTO() {
    }

    public UsuarioPaginaRedencionDTO(Integer idRedencion, String tipo, String modo, String estado, BigDecimal monto, Date fecha, UsuarioPaginaDTO usuarioPagina) {
        this.idRedencion = idRedencion;
        this.tipo = tipo;
        this.modo = modo;
        this.estado = estado;
        this.monto = monto;
        this.fecha = fecha;
        this.usuarioPagina = usuarioPagina;
    }

    public UsuarioPaginaRedencionDTO(UsuarioPaginaRedencion redencion) {
        this.idRedencion = redencion.getIdRedencion();
        this.tipo = redencion.getTipo();
        this.modo = redencion.getModo();
        this.estado = redencion.getEstado();
        this.monto = redencion.getMonto();
        this.fecha = redencion.getFecha();
        this.usuarioPagina = new UsuarioPaginaDTO(redencion.getIdUsuarioPagina());
    }

    public Integer getIdRedencion() {
        return idRedencion;
    }

    public void setIdRedencion(Integer idRedencion) {
        this.idRedencion = idRedencion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModo() {
        return modo;
    }

    public void setModo(String modo) {
        this.modo = modo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public UsuarioPaginaDTO getUsuarioPagina() {
        return usuarioPagina;
    }

    public void setUsuarioPagina(UsuarioPaginaDTO usuarioPagina) {
        this.usuarioPagina = usuarioPagina;
    }
}
