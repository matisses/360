package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class GaleriaDTO {

    private Integer posicion;
    private String url;
    private String nombreImagen;
    private boolean seleccionada = false;

    public GaleriaDTO() {
    }

    public GaleriaDTO(Integer posicion, String url, String nombreImagen) {
        this.posicion = posicion;
        this.url = url;
        this.nombreImagen = nombreImagen;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombreImagen() {
        return nombreImagen;
    }

    public void setNombreImagen(String nombreImagen) {
        this.nombreImagen = nombreImagen;
    }

    public boolean isSeleccionada() {
        return seleccionada;
    }

    public void setSeleccionada(boolean seleccionada) {
        this.seleccionada = seleccionada;
    }
}
