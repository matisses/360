package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Date;

/**
 *
 * @author dbotero
 */
public class InventarioCiclicoDTO {

    private Long idBitacora;
    private String whsCode;
    private String binCode;
    private Date fechaCreacionConteo;
    private String usuarioCreador;
    private Integer referenciasContadas;
    private Integer unidadesContadas;
    private Integer diferenciasEncontradas;
    private Date fechaFinalizacionConteo;
    private String usuarioFinalizador;

    public InventarioCiclicoDTO() {
    }

    public Long getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Long idBitacora) {
        this.idBitacora = idBitacora;
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

    public Date getFechaCreacionConteo() {
        return fechaCreacionConteo;
    }

    public void setFechaCreacionConteo(Date fechaCreacionConteo) {
        this.fechaCreacionConteo = fechaCreacionConteo;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Integer getReferenciasContadas() {
        return referenciasContadas;
    }

    public void setReferenciasContadas(Integer referenciasContadas) {
        this.referenciasContadas = referenciasContadas;
    }

    public Integer getUnidadesContadas() {
        return unidadesContadas;
    }

    public void setUnidadesContadas(Integer unidadesContadas) {
        this.unidadesContadas = unidadesContadas;
    }

    public Integer getDiferenciasEncontradas() {
        return diferenciasEncontradas;
    }

    public void setDiferenciasEncontradas(Integer diferenciasEncontradas) {
        this.diferenciasEncontradas = diferenciasEncontradas;
    }

    public Date getFechaFinalizacionConteo() {
        return fechaFinalizacionConteo;
    }

    public void setFechaFinalizacionConteo(Date fechaFinalizacionConteo) {
        this.fechaFinalizacionConteo = fechaFinalizacionConteo;
    }

    public String getUsuarioFinalizador() {
        return usuarioFinalizador;
    }

    public void setUsuarioFinalizador(String usuarioFinalizador) {
        this.usuarioFinalizador = usuarioFinalizador;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
