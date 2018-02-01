package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ZonaTiendaDTO {
    private Integer idZona;
    private String nroZona;
    private String piso;
    private String tienda;

    public ZonaTiendaDTO() {
    }

    public ZonaTiendaDTO(Integer idZona, String nroZona, String piso, String tienda) {
        this.idZona = idZona;
        this.nroZona = nroZona;
        this.piso = piso;
        this.tienda = tienda;
    }

    public Integer getIdZona() {
        return idZona;
    }

    public void setIdZona(Integer idZona) {
        this.idZona = idZona;
    }

    public String getNroZona() {
        return nroZona;
    }

    public void setNroZona(String nroZona) {
        this.nroZona = nroZona;
    }

    public String getPiso() {
        return piso;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idZona);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ZonaTiendaDTO other = (ZonaTiendaDTO) obj;
        if (!Objects.equals(this.idZona, other.idZona)) {
            return false;
        }
        return true;
    }
    
    
}
