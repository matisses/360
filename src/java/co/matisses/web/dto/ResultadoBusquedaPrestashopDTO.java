package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ResultadoBusquedaPrestashopDTO {
    private String referencia;
    private List<AtributoItemDTO> atributos;

    public ResultadoBusquedaPrestashopDTO() {
        atributos = new ArrayList<>();
    }

    public ResultadoBusquedaPrestashopDTO(String referencia) {
        this.referencia = referencia;
        atributos = new ArrayList<>();
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<AtributoItemDTO> getAtributos() {
        return atributos;
    }

    public void setAtributos(List<AtributoItemDTO> atributos) {
        this.atributos = atributos;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.referencia);
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
        final ResultadoBusquedaPrestashopDTO other = (ResultadoBusquedaPrestashopDTO) obj;
        if (!Objects.equals(this.referencia, other.referencia)) {
            return false;
        }
        return true;
    }
}
