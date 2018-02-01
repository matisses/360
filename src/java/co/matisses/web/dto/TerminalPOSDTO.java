package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class TerminalPOSDTO {

    private String ip;
    private String alias;
    private String cuentaEfectivo;

    public TerminalPOSDTO() {
    }

    public TerminalPOSDTO(String ip, String alias, String cuentaEfectivo) {
        this.ip = ip;
        this.alias = alias;
        this.cuentaEfectivo = cuentaEfectivo;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCuentaEfectivo() {
        return cuentaEfectivo;
    }

    public void setCuentaEfectivo(String cuentaEfectivo) {
        this.cuentaEfectivo = cuentaEfectivo;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Objects.hashCode(this.ip);
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
        final TerminalPOSDTO other = (TerminalPOSDTO) obj;
        if (!Objects.equals(this.ip, other.ip)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TerminalPOSDTO{" + "ip=" + ip + ", alias=" + alias + ", cuentaEfectivo=" + cuentaEfectivo + '}';
    }

}
