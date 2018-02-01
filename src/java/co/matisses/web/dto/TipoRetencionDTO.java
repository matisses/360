package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class TipoRetencionDTO implements Comparable<TipoRetencionDTO> {

    private String codigo;
    private String nombre;
    private String valor;
    private Integer lineNumSAP;
    private boolean habilitado;
    private boolean bloqueado;

    public TipoRetencionDTO() {
        bloqueado = false;
    }

    public TipoRetencionDTO(String codigo, String nombre, String valor, Integer lineNumSAP, boolean habilitado) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.lineNumSAP = lineNumSAP;
        this.habilitado = habilitado;
        this.valor = valor;
        bloqueado = false;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public Integer getLineNumSAP() {
        return lineNumSAP;
    }

    public void setLineNumSAP(Integer lineNumSAP) {
        this.lineNumSAP = lineNumSAP;
    }

    public boolean isBloqueado() {
        return bloqueado;
    }

    public void setBloqueado(boolean bloqueado) {
        this.bloqueado = bloqueado;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + (this.codigo != null ? this.codigo.hashCode() : 0);
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
        final TipoRetencionDTO other = (TipoRetencionDTO) obj;
        if ((this.codigo == null) ? (other.codigo != null) : !this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TipoRetencionDTO{" + "codigo=" + codigo + ", nombre=" + nombre + ", habilitado=" + habilitado + '}';
    }

    @Override
    public int compareTo(TipoRetencionDTO o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
