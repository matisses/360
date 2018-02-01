package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class UbicacionesConteosDTO {

    private Integer absEntry;
    private String ubicacion;
    private boolean elegida = false;

    public UbicacionesConteosDTO() {
    }

    public UbicacionesConteosDTO(Integer absEntry, String ubicacion) {
        this.absEntry = absEntry;
        this.ubicacion = ubicacion;
    }

    public UbicacionesConteosDTO(Integer absEntry, String ubicacion, boolean elegida) {
        this.absEntry = absEntry;
        this.ubicacion = ubicacion;
        this.elegida = elegida;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isElegida() {
        return elegida;
    }

    public void setElegida(boolean elegida) {
        this.elegida = elegida;
    }
}
