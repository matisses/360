package co.matisses.web.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class DetalleImpuestosDTO {

    private Float valorBase;
    private String nombreImpuesto;
    private Float valorImpuesto;
    private Float valorDescuento;
    private Float total;
    private NumberFormat nf = new DecimalFormat("#,###");

    public DetalleImpuestosDTO() {
    }

    public String getNombreImpuesto() {
        return nombreImpuesto;
    }

    public void setNombreImpuesto(String nombreImpuesto) {
        this.nombreImpuesto = nombreImpuesto;
    }

    public Float getTotal() {
        return total;
    }

//    public String getTotalTxt() {
//        if (total == null) {
//            return "";
//        }
//        return nf.format(total);
//    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Float getValorBase() {
        return valorBase;
    }

//    public String getValorBaseTxt() {
//        if (valorBase == null) {
//            return "";
//        }
//        return nf.format(valorBase);
//    }

    public void setValorBase(Float valorBase) {
        this.valorBase = valorBase;
    }

    public Float getValorDescuento() {
        return valorDescuento;
    }

//    public String getValorDescuentoTxt() {
//        if (valorDescuento == null) {
//            return "";
//        }
//        return nf.format(valorDescuento);
//    }

    public void setValorDescuento(Float valorDescuento) {
        this.valorDescuento = valorDescuento;
    }

    public Float getValorImpuesto() {
        return valorImpuesto;
    }

//    public String getValorImpuestoTxt() {
//        if (valorImpuesto == null) {
//            return "";
//        }
//        return nf.format(valorImpuesto);
//    }

    public void setValorImpuesto(Float valorImpuesto) {
        this.valorImpuesto = valorImpuesto;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.nombreImpuesto);
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
        final DetalleImpuestosDTO other = (DetalleImpuestosDTO) obj;
        if (!Objects.equals(this.nombreImpuesto, other.nombreImpuesto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DetalleImpuestosDTO{" + "valorBase=" + valorBase + ", nombreImpuesto=" + nombreImpuesto + ", valorImpuesto=" + valorImpuesto + ", valorDescuento=" + valorDescuento + ", total=" + total + '}';
    }

}
