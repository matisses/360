package co.matisses.web.dto;

import java.text.DecimalFormat;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class MedioPagoDTO {

    private String nombre;
    private Float valor;
    private Integer transacciones;

    public MedioPagoDTO() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getTransacciones() {
        return transacciones;
    }

    public String getTransaccionesTxt() {
        if (transacciones == null) {
            return "";
        }
        return new DecimalFormat("#,###").format(transacciones);
    }

    public void setTransacciones(Integer transacciones) {
        this.transacciones = transacciones;
    }

    public Float getValor() {
        return valor;
    }

    public String getValorTxt() {
        if (valor == null) {
            return "";
        }
        return new DecimalFormat("#,###").format(valor);
    }

    public void setValor(Float valor) {
        this.valor = valor;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.nombre);
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
        final MedioPagoDTO other = (MedioPagoDTO) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "MedioPagoDTO{" + "nombre=" + nombre + ", valor=" + valor + ", transacciones=" + transacciones + '}';
    }

}
