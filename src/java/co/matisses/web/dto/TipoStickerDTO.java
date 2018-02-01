package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class TipoStickerDTO {

    private Integer idTipoSticker;
    private Integer columnas;
    private Integer idTipoEtiqueta;
    private String nombreSticker;
    private String tipoAlmacen;
    private boolean activo;

    public TipoStickerDTO() {
    }

    public TipoStickerDTO(Integer idTipoSticker, Integer columnas, Integer idTipoEtiqueta, String nombreSticker, String tipoAlmacen, boolean activo) {
        this.idTipoSticker = idTipoSticker;
        this.columnas = columnas;
        this.idTipoEtiqueta = idTipoEtiqueta;
        this.nombreSticker = nombreSticker;
        this.tipoAlmacen = tipoAlmacen;
        this.activo = activo;
    }

    public Integer getIdTipoSticker() {
        return idTipoSticker;
    }

    public void setIdTipoSticker(Integer idTipoSticker) {
        this.idTipoSticker = idTipoSticker;
    }

    public Integer getColumnas() {
        return columnas;
    }

    public void setColumnas(Integer columnas) {
        this.columnas = columnas;
    }

    public Integer getIdTipoEtiqueta() {
        return idTipoEtiqueta;
    }

    public void setIdTipoEtiqueta(Integer idTipoEtiqueta) {
        this.idTipoEtiqueta = idTipoEtiqueta;
    }

    public String getNombreSticker() {
        return nombreSticker;
    }

    public void setNombreSticker(String nombreSticker) {
        this.nombreSticker = nombreSticker;
    }

    public String getTipoAlmacen() {
        return tipoAlmacen;
    }

    public void setTipoAlmacen(String tipoAlmacen) {
        this.tipoAlmacen = tipoAlmacen;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
