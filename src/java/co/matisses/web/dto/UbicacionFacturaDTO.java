package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class UbicacionFacturaDTO {

    private String whsCode;
    private String binCode;
    private List<DetalleUbicacionFacturaDTO> items;

    public UbicacionFacturaDTO() {
        items = new ArrayList<>();
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public List<DetalleUbicacionFacturaDTO> getItems() {
        return items;
    }

    public void setItems(List<DetalleUbicacionFacturaDTO> items) {
        this.items = items;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.whsCode);
        hash = 11 * hash + Objects.hashCode(this.binCode);
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
        final UbicacionFacturaDTO other = (UbicacionFacturaDTO) obj;
        if (!Objects.equals(this.whsCode, other.whsCode)) {
            return false;
        }
        if (!Objects.equals(this.binCode, other.binCode)) {
            return false;
        }
        return true;
    }
    
    
}
