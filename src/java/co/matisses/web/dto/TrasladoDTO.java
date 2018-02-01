package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class TrasladoDTO {

    private Integer idTraslado;
    private Integer serie;
    private String numeroTraslado;
    private String sucursal;
    private String usuario;
    private String cedulaUsuario;
    private String comentario;
    private String estado;
    private String tipoMovimiento;
    private boolean imprimir;
    private Date fecha;
    private List<TrasladoDetalleDTO> trasladoDetalles;

    public TrasladoDTO() {
        trasladoDetalles = new ArrayList<>();
    }

    public TrasladoDTO(Integer idTraslado, Integer serie, String numeroTraslado, String sucursal, String usuario, String cedulaUsuario,
            String comentario, String estado, String tipoMovimiento, boolean imprimir, Date fecha, List<TrasladoDetalleDTO> trasladoDetalles) {
        this.idTraslado = idTraslado;
        this.serie = serie;
        this.numeroTraslado = numeroTraslado;
        this.sucursal = sucursal;
        this.usuario = usuario;
        this.cedulaUsuario = cedulaUsuario;
        this.comentario = comentario;
        this.estado = estado;
        this.tipoMovimiento = tipoMovimiento;
        this.imprimir = imprimir;
        this.fecha = fecha;
        this.trasladoDetalles = trasladoDetalles;
    }

    public Integer getIdTraslado() {
        return idTraslado;
    }

    public void setIdTraslado(Integer idTraslado) {
        this.idTraslado = idTraslado;
    }

    public Integer getSerie() {
        return serie;
    }

    public void setSerie(Integer serie) {
        this.serie = serie;
    }

    public String getNumeroTraslado() {
        return numeroTraslado;
    }

    public void setNumeroTraslado(String numeroTraslado) {
        this.numeroTraslado = numeroTraslado;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCedulaUsuario() {
        return cedulaUsuario;
    }

    public void setCedulaUsuario(String cedulaUsuario) {
        this.cedulaUsuario = cedulaUsuario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipoMovimiento() {
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<TrasladoDetalleDTO> getTrasladoDetalles() {
        return trasladoDetalles;
    }

    public void setTrasladoDetalles(List<TrasladoDetalleDTO> trasladoDetalles) {
        this.trasladoDetalles = trasladoDetalles;
    }

    public void addTrasladoDetalle(TrasladoDetalleDTO trasladoDetalle) {
        trasladoDetalles.add(trasladoDetalle);
    }
}
