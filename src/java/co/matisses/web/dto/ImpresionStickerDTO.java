package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ImpresionStickerDTO {

    private Integer idImpresionSticker;
    private Integer idTipoSticker;
    private String usuario;
    private String sucursal;
    private String estado;
    private Date fecha;

    public ImpresionStickerDTO() {
    }

    public ImpresionStickerDTO(Integer idImpresionSticker, Integer idTipoSticker, String usuario, String sucursal, String estado, Date fecha) {
        this.idImpresionSticker = idImpresionSticker;
        this.idTipoSticker = idTipoSticker;
        this.usuario = usuario;
        this.sucursal = sucursal;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Integer getIdImpresionSticker() {
        return idImpresionSticker;
    }

    public void setIdImpresionSticker(Integer idImpresionSticker) {
        this.idImpresionSticker = idImpresionSticker;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
