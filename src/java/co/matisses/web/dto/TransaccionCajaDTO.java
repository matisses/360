package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class TransaccionCajaDTO {

    private Integer idOperacionCaja;
    private Date fecha;
    private String terminal;
    private String usuario;
    private String tipo;
    private Integer valor;
    private String justificacion;
    private String tipoDocumento;
    private Boolean cierre;
    private Boolean anulacion;

    public TransaccionCajaDTO() {
    }

    public Boolean getAnulacion() {
        return anulacion;
    }

    public void setAnulacion(Boolean anulacion) {
        this.anulacion = anulacion;
    }

    public Boolean getCierre() {
        return cierre;
    }

    public void setCierre(Boolean cierre) {
        this.cierre = cierre;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public Integer getIdOperacionCaja() {
        return idOperacionCaja;
    }

    public void setIdOperacionCaja(Integer idOperacionCaja) {
        this.idOperacionCaja = idOperacionCaja;
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

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getValor() {
        return valor;
    }

    public void setValor(Integer valor) {
        this.valor = valor;
    }

    public String getJustificacion() {
        return justificacion;
    }

    public void setJustificacion(String justificacion) {
        this.justificacion = justificacion;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + Objects.hashCode(this.idOperacionCaja);
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
        final TransaccionCajaDTO other = (TransaccionCajaDTO) obj;
        if (!Objects.equals(this.idOperacionCaja, other.idOperacionCaja)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
