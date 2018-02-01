package co.matisses.web.dto;

import co.matisses.persistence.web.entity.TipoConteo;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class ConteoTiendaDTO {

    private Integer idConteo;
    private String tienda;
    private String estado;
    private String usuario;
    private String ubicacion;
    private String usuarioFinaliza;
    private boolean dotacion;
    private boolean cliente;
    private boolean venta;
    private boolean casilla;
    private boolean pda;
    private Date fecha;
    private TipoConteo idTipoConteo;

    public ConteoTiendaDTO() {
    }

    public ConteoTiendaDTO(Integer idConteo, String tienda, String estado, String usuario, String ubicacion, String usuarioFinaliza,
            boolean dotacion, boolean cliente, boolean venta, boolean casilla, boolean pda, Date fecha, TipoConteo idTipoConteo) {
        this.idConteo = idConteo;
        this.tienda = tienda;
        this.estado = estado;
        this.usuario = usuario;
        this.ubicacion = ubicacion;
        this.usuarioFinaliza = usuarioFinaliza;
        this.dotacion = dotacion;
        this.cliente = cliente;
        this.venta = venta;
        this.casilla = casilla;
        this.pda = pda;
        this.fecha = fecha;
        this.idTipoConteo = idTipoConteo;
    }

    public Integer getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(Integer idConteo) {
        this.idConteo = idConteo;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getUsuarioFinaliza() {
        return usuarioFinaliza;
    }

    public void setUsuarioFinaliza(String usuarioFinaliza) {
        this.usuarioFinaliza = usuarioFinaliza;
    }

    public boolean isDotacion() {
        return dotacion;
    }

    public void setDotacion(boolean dotacion) {
        this.dotacion = dotacion;
    }

    public boolean isCliente() {
        return cliente;
    }

    public void setCliente(boolean cliente) {
        this.cliente = cliente;
    }

    public boolean isVenta() {
        return venta;
    }

    public void setVenta(boolean venta) {
        this.venta = venta;
    }

    public boolean isCasilla() {
        return casilla;
    }

    public void setCasilla(boolean casilla) {
        this.casilla = casilla;
    }

    public boolean isPda() {
        return pda;
    }

    public void setPda(boolean pda) {
        this.pda = pda;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TipoConteo getIdTipoConteo() {
        return idTipoConteo;
    }

    public void setIdTipoConteo(TipoConteo idTipoConteo) {
        this.idTipoConteo = idTipoConteo;
    }
}
