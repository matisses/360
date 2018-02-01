package co.matisses.web.dto;

import java.util.Objects;
import javax.faces.model.SelectItem;

/**
 *
 * @author dbotero
 */
public class PuntoVentaDTO extends SelectItem implements Comparable<PuntoVentaDTO> {

    private String codigo;
    private String nombre;

    public PuntoVentaDTO() {
        super();
    }

    public PuntoVentaDTO(String codigo, String nombre) {
        super(codigo, nombre);
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
        super.setValue(codigo);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        super.setLabel(nombre);
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.codigo);
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
        final PuntoVentaDTO other = (PuntoVentaDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(PuntoVentaDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
