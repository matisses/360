package co.matisses.web.dto.epson;

import java.util.Objects;

public class InvoiceVATDetail {

    private String vatName;
    private Float value;
    private Float baseValue;

    public InvoiceVATDetail() {
    }

    public InvoiceVATDetail(String vatName, Float value, Float baseValue) {
        this.vatName = vatName;
        this.value = value;
        this.baseValue = baseValue;
    }

    public Float getBaseValue() {
        return baseValue;
    }

    public void setBaseValue(Float baseValue) {
        this.baseValue = baseValue;
    }

    public String getVatName() {
        return vatName;
    }

    public void setVatName(String vatName) {
        this.vatName = vatName;
    }

    public Float getValue() {
        return value;
    }

    public void setValue(Float value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.vatName);
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
        final InvoiceVATDetail other = (InvoiceVATDetail) obj;
        if (!Objects.equals(this.vatName, other.vatName)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InvoiceVATDetail [vatName=" + vatName + ", value=" + value + ", baseValue=" + baseValue + "]";
    }
}
