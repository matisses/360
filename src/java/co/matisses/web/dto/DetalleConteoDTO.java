package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class DetalleConteoDTO{

    private Integer idConteo;
    private Integer cantidad;
    private String usuario;
    private String referencia;
    private Date fecha;

    public DetalleConteoDTO() {
    }

    public DetalleConteoDTO(Integer idConteo, Integer cantidad, String usuario, String referencia, Date fecha) {
        this.idConteo = idConteo;
        this.cantidad = cantidad;
        this.usuario = usuario;
        this.referencia = referencia;
        this.fecha = fecha;
    }

    public Integer getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(Integer idConteo) {
        this.idConteo = idConteo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
