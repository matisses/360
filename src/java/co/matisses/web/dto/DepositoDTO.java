package co.matisses.web.dto;

import java.text.DecimalFormat;

/**
 *
 * @author dbotero
 */
public class DepositoDTO {

    private String nombre;
    private Integer valor;

    public DepositoDTO() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getValor() {
        return valor;
    }

    public String getValorTxt() {
        if (valor == null) {
            return "";
        }
        return new DecimalFormat("#,###").format(valor);
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "DepositoDTO{" + "nombre=" + nombre + ", valor=" + valor + '}';
    }
}
