package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class UbicacionesDemostracionDTO {

    private int cantidad;
    private int absEntry;
    private boolean regresar = false;

    public UbicacionesDemostracionDTO() {
    }

    public UbicacionesDemostracionDTO(int cantidad, int absEntry) {
        this.cantidad = cantidad;
        this.absEntry = absEntry;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(int absEntry) {
        this.absEntry = absEntry;
    }

    public boolean isRegresar() {
        return regresar;
    }

    public void setRegresar(boolean regresar) {
        this.regresar = regresar;
    }
}
