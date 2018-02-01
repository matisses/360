package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class EmpaqueVentaDTO {

    private Integer idEmpaqueVenta;
    private String numeroFactura;
    private Date fecha;
    private String usuario;
    private String almacen;
    private String cuenta;
    private List<DetalleEmpaqueVentaDTO> referencias;

    public EmpaqueVentaDTO() {
        referencias = new ArrayList<>();
    }

    public Integer getIdEmpaqueVenta() {
        return idEmpaqueVenta;
    }

    public void setIdEmpaqueVenta(Integer idEmpaqueVenta) {
        this.idEmpaqueVenta = idEmpaqueVenta;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public List<DetalleEmpaqueVentaDTO> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<DetalleEmpaqueVentaDTO> referencias) {
        this.referencias = referencias;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.idEmpaqueVenta);
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
        final EmpaqueVentaDTO other = (EmpaqueVentaDTO) obj;
        if (!Objects.equals(this.idEmpaqueVenta, other.idEmpaqueVenta)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "EmpaqueVentaDTO{" + "idEmpaqueVenta=" + idEmpaqueVenta + ", numeroFactura=" + numeroFactura + ", referencias=" + referencias + ", fecha=" + fecha + ", usuario=" + usuario + '}';
    }
}
