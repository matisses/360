package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class CambioModeloDTO {

    private Integer idCambioModelo;
    private Integer salida;
    private Integer entrada;
    private String modeloAnterior;
    private String modeloNuevo;
    private String usuario;
    private String token;
    private boolean combinaciones;
    private Date fecha;
    private List<DetalleCambioModeloDTO> detalle;

    public CambioModeloDTO() {
        detalle = new ArrayList<>();
    }

    public CambioModeloDTO(Integer idCambioModelo, Integer salida, Integer entrada, String modeloAnterior, String modeloNuevo,
            String usuario, String token, boolean combinaciones, Date fecha, List<DetalleCambioModeloDTO> detalle) {
        this.idCambioModelo = idCambioModelo;
        this.salida = salida;
        this.entrada = entrada;
        this.modeloAnterior = modeloAnterior;
        this.modeloNuevo = modeloNuevo;
        this.usuario = usuario;
        this.token = token;
        this.combinaciones = combinaciones;
        this.fecha = fecha;
        this.detalle = detalle;
    }

    public Integer getIdCambioModelo() {
        return idCambioModelo;
    }

    public void setIdCambioModelo(Integer idCambioModelo) {
        this.idCambioModelo = idCambioModelo;
    }

    public Integer getSalida() {
        return salida;
    }

    public void setSalida(Integer salida) {
        this.salida = salida;
    }

    public Integer getEntrada() {
        return entrada;
    }

    public void setEntrada(Integer entrada) {
        this.entrada = entrada;
    }

    public String getModeloAnterior() {
        return modeloAnterior;
    }

    public void setModeloAnterior(String modeloAnterior) {
        this.modeloAnterior = modeloAnterior;
    }

    public String getModeloNuevo() {
        return modeloNuevo;
    }

    public void setModeloNuevo(String modeloNuevo) {
        this.modeloNuevo = modeloNuevo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isCombinaciones() {
        return combinaciones;
    }

    public void setCombinaciones(boolean combinaciones) {
        this.combinaciones = combinaciones;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<DetalleCambioModeloDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleCambioModeloDTO> detalle) {
        this.detalle = detalle;
    }
}
