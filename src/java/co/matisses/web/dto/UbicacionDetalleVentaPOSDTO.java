package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class UbicacionDetalleVentaPOSDTO {
    private Integer binAbsEntry;
    private Integer cantidad;
    private String almacen;

    public UbicacionDetalleVentaPOSDTO() {
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public Integer getBinAbsEntry() {
        return binAbsEntry;
    }

    public void setBinAbsEntry(Integer binAbsEntry) {
        this.binAbsEntry = binAbsEntry;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "UbicacionDetalleVentaPOSDTO{" + "binAbsEntry=" + binAbsEntry + ", cantidad=" + cantidad + ", almacen=" + almacen + '}';
    }
}
