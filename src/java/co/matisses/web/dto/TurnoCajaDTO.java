package co.matisses.web.dto;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class TurnoCajaDTO {

    private Integer idTurno;
    private String usuario;
    private Date fecha;
    private String terminal;

    public TurnoCajaDTO() {
    }

    public TurnoCajaDTO(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(Integer idTurno) {
        this.idTurno = idTurno;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 89 * hash + Objects.hashCode(this.idTurno);
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
        final TurnoCajaDTO other = (TurnoCajaDTO) obj;
        if (!Objects.equals(this.idTurno, other.idTurno)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TurnoCajaDTO{" + "idTurno=" + idTurno + ", usuario=" + usuario + ", fecha=" + fecha + ", terminal=" + terminal + '}';
    }

}
