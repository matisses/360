package co.matisses.web.rest.regalos;

import co.matisses.web.ObjectUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class FiltroProducto implements Comparable<FiltroProducto> {

    private String valor;
    private String codigoPadre;
    private String tipoPadre;
    private List<String> infoAdicional;

    public FiltroProducto() {
        infoAdicional = new ArrayList<>();
    }

    public FiltroProducto(String infoAdicional, String valor) {
        this.valor = valor;
        this.infoAdicional = new ArrayList<>();
        this.infoAdicional.add(infoAdicional);
    }

    public FiltroProducto(String valor, String codigoPadre, String tipoPadre, String infoAdicional) {
        this.valor = valor;
        this.codigoPadre = codigoPadre;
        this.tipoPadre = tipoPadre;
        this.infoAdicional = new ArrayList<>();
        this.infoAdicional.add(infoAdicional);
    }

    public List<String> getInfoAdicional() {
        return infoAdicional;
    }

    public void setInfoAdicional(List<String> infoAdicional) {
        this.infoAdicional = infoAdicional;
    }

    public void addInfoAdicional(String info) {
        if (info != null && !infoAdicional.contains(info)) {
            infoAdicional.add(info);
        }
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getCodigoPadre() {
        return codigoPadre;
    }

    public void setCodigoPadre(String codigoPadre) {
        this.codigoPadre = codigoPadre;
    }

    public String getTipoPadre() {
        return tipoPadre;
    }

    public void setTipoPadre(String tipoPadre) {
        this.tipoPadre = tipoPadre;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

    @Override
    public int compareTo(FiltroProducto o) {
        return this.getValor().compareTo(o.getValor());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 43 * hash + Objects.hashCode(this.valor);
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
        final FiltroProducto other = (FiltroProducto) obj;
        return Objects.equals(this.valor, other.valor);
    }

}
