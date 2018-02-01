package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class SaldoItemDTO {

    private Integer cantidad;
    private Integer cantidadNecesaria;
    private Long descuento;
    private Double costo;
    private String referencia;
    private String almacen;
    private String estado;
    private List<SaldoUbicacionDTO> saldoUbicacion;

    public SaldoItemDTO() {
        saldoUbicacion = new ArrayList<>();
    }

    public SaldoItemDTO(String referencia, Integer cantidad) {
        this.referencia = referencia;
        this.cantidad = cantidad;
    }

    public SaldoItemDTO(Integer cantidad, Double costo, String referencia, String almacen, List<SaldoUbicacionDTO> saldoUbicacion) {
        this.cantidad = cantidad;
        this.costo = costo;
        this.referencia = referencia;
        this.almacen = almacen;
        this.saldoUbicacion = saldoUbicacion;
    }

    public SaldoItemDTO(Integer cantidad, Integer cantidadNecesaria, String referencia, String almacen) {
        this.cantidad = cantidad;
        this.cantidadNecesaria = cantidadNecesaria;
        this.referencia = referencia;
        this.almacen = almacen;
    }

    public SaldoItemDTO(Integer cantidad, Integer cantidadNecesaria, Long descuento, String referencia, String almacen) {
        this.cantidad = cantidad;
        this.cantidadNecesaria = cantidadNecesaria;
        this.descuento = descuento;
        this.referencia = referencia;
        this.almacen = almacen;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(Integer cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public void agregarCantidad(Integer cantidad) {
        this.cantidad += cantidad;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
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

    public List<SaldoUbicacionDTO> getSaldoUbicacion() {
        return saldoUbicacion;
    }

    public void setSaldoUbicacion(List<SaldoUbicacionDTO> saldoUbicacion) {
        this.saldoUbicacion = saldoUbicacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.referencia);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SaldoItemDTO other = (SaldoItemDTO) obj;
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        return true;
    }

}
