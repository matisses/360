package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class DetalleCustodiaDTO {

    private Integer idDetalleCustodia;
    private Integer cantidad;
    private Integer cantidadBaja;
    private String cedula;
    private String contrasenaCustodia;
    private String comentario;
    private String usuarioEntrega;
    private boolean activo;
    private boolean seleccionado = false;
    private Date fechaEntrega;
    private Date fechaCambio;
    private CustodiaDTO idCustodia;
    private List<ComponenteCustodiaEmpleadoDTO> componentes;

    public DetalleCustodiaDTO() {
        componentes = new ArrayList<>();
    }

    public DetalleCustodiaDTO(Integer idDetalleCustodia) {
        this.idDetalleCustodia = idDetalleCustodia;
    }

    public DetalleCustodiaDTO(Integer idDetalleCustodia, Integer cantidad, Integer cantidadBaja, String cedula, String contrasenaCustodia, String comentario, String usuarioEntrega,
            boolean activo, Date fechaEntrega, Date fechaCambio, CustodiaDTO idCustodia) {
        this.idDetalleCustodia = idDetalleCustodia;
        this.cantidad = cantidad;
        this.cantidadBaja = cantidadBaja;
        this.cedula = cedula;
        this.contrasenaCustodia = contrasenaCustodia;
        this.comentario = comentario;
        this.usuarioEntrega = usuarioEntrega;
        this.activo = activo;
        this.fechaEntrega = fechaEntrega;
        this.fechaCambio = fechaCambio;
        this.idCustodia = idCustodia;
    }

    public DetalleCustodiaDTO(Integer idDetalleCustodia, Integer cantidad, Integer cantidadBaja, String cedula, String contrasenaCustodia, String comentario, String usuarioEntrega,
            boolean activo, Date fechaEntrega, Date fechaCambio, CustodiaDTO idCustodia, List<ComponenteCustodiaEmpleadoDTO> componentes) {
        this.idDetalleCustodia = idDetalleCustodia;
        this.cantidad = cantidad;
        this.cantidadBaja = cantidadBaja;
        this.cedula = cedula;
        this.contrasenaCustodia = contrasenaCustodia;
        this.comentario = comentario;
        this.usuarioEntrega = usuarioEntrega;
        this.activo = activo;
        this.fechaEntrega = fechaEntrega;
        this.fechaCambio = fechaCambio;
        this.idCustodia = idCustodia;
        this.componentes = componentes;
    }

    public Integer getIdDetalleCustodia() {
        return idDetalleCustodia;
    }

    public void setIdDetalleCustodia(Integer idDetalleCustodia) {
        this.idDetalleCustodia = idDetalleCustodia;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadBaja() {
        return cantidadBaja;
    }

    public void setCantidadBaja(Integer cantidadBaja) {
        this.cantidadBaja = cantidadBaja;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getContrasenaCustodia() {
        return contrasenaCustodia;
    }

    public void setContrasenaCustodia(String contasenaCustodia) {
        this.contrasenaCustodia = contasenaCustodia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUsuarioEntrega() {
        return usuarioEntrega;
    }

    public void setUsuarioEntrega(String usuarioEntrega) {
        this.usuarioEntrega = usuarioEntrega;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public Date getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(Date fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public CustodiaDTO getIdCustodia() {
        return idCustodia;
    }

    public void setIdCustodia(CustodiaDTO idCustodia) {
        this.idCustodia = idCustodia;
    }

    public List<ComponenteCustodiaEmpleadoDTO> getComponentes() {
        return componentes;
    }

    public void setComponentes(List<ComponenteCustodiaEmpleadoDTO> componentes) {
        this.componentes = componentes;
    }
}
