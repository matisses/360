package co.matisses.web.dto.epson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InvoiceBinAllocation {

    private String whsCode;
    private String binCode;
    private List<InvoiceDetail> items;

    public InvoiceBinAllocation() {
        items = new ArrayList<>();
    }

    public InvoiceBinAllocation(String whsCode, String binCode) {
        this.whsCode = whsCode;
        this.binCode = binCode;
        items = new ArrayList<>();
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public List<InvoiceDetail> getItems() {
        return items;
    }

    public void setItems(List<InvoiceDetail> items) {
        this.items = items;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.whsCode);
        hash = 89 * hash + Objects.hashCode(this.binCode);
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
        final InvoiceBinAllocation other = (InvoiceBinAllocation) obj;
        if (!Objects.equals(this.whsCode, other.whsCode)) {
            return false;
        }
        if (!Objects.equals(this.binCode, other.binCode)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "InvoiceBinAllocation [whsCode=" + whsCode + ", binCode=" + binCode + ", items=" + items + "]";
    }
}
