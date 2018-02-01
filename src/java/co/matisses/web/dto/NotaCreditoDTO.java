package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class NotaCreditoDTO {

    private int linea;
    private int cantidadUsada;
    private int cantidadDisponible;
    private int cantidadUbicaciones = 0;
    private Double price;
    private String referencia;
    private List<SaldoUbicacionDTO> ubicaciones;

    public NotaCreditoDTO() {
        ubicaciones = new ArrayList<>();
    }

    public NotaCreditoDTO(int linea, int cantidadUsada, int cantidadDisponible, Double price, String referencia) {
        this.linea = linea;
        this.cantidadUsada = cantidadUsada;
        this.cantidadDisponible = cantidadDisponible;
        this.price = price;
        this.referencia = referencia;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public int getCantidadUsada() {
        return cantidadUsada;
    }

    public void setCantidadUsada(int cantidadUsada) {
        this.cantidadUsada = cantidadUsada;
    }

    public int getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(int cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public int getCantidadUbicaciones() {
        return cantidadUbicaciones;
    }

    public void setCantidadUbicaciones(int cantidadUbicaciones) {
        this.cantidadUbicaciones = cantidadUbicaciones;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<SaldoUbicacionDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<SaldoUbicacionDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
