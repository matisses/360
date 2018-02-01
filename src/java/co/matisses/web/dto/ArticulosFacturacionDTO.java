package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class ArticulosFacturacionDTO {

    private Integer cantidad;
    private Integer cantidadUsada = 0;
    private String referencia;
    private String almacen;
    private String estado;
    private boolean tieneUbicaciones;
    private List<SaldoUbicacionDTO> saldosUbicaciones;

    public ArticulosFacturacionDTO() {
        saldosUbicaciones = new ArrayList<>();
    }

    public ArticulosFacturacionDTO(Integer cantidad, String referencia, String almacen, String estado, boolean tieneUbicaciones, List<SaldoUbicacionDTO> saldosUbicaciones) {
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.almacen = almacen;
        this.estado = estado;
        this.tieneUbicaciones = tieneUbicaciones;
        this.saldosUbicaciones = saldosUbicaciones;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(Integer cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoSeleccionado() {
        if (estado != null && !estado.isEmpty()) {
            switch (estado) {
                case "P":
                    return "Pendiente entrega 1 a 10 días";
                case "D":
                    return "Despachado - Entrega inmediata";
                case "G":
                    return "Enviar a DAKA";
                default:
                    break;
            }
        }
        return "Seleccione estado";
    }

    public boolean isTieneUbicaciones() {
        return tieneUbicaciones;
    }

    public void setTieneUbicaciones(boolean tieneUbicaciones) {
        this.tieneUbicaciones = tieneUbicaciones;
    }

    public List<SaldoUbicacionDTO> getSaldosUbicaciones() {
        return saldosUbicaciones;
    }

    public void setSaldosUbicaciones(List<SaldoUbicacionDTO> saldosUbicaciones) {
        this.saldosUbicaciones = saldosUbicaciones;
    }
}
