package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class AccionObjetoBWSDTO implements Comparable<AccionObjetoBWSDTO>{
    private Integer idAccionObjeto;
    private String nombre;
    private Integer codigoObjeto;
    private Integer codigoAccion;

    public AccionObjetoBWSDTO() {
    }

    public AccionObjetoBWSDTO(Integer idAccionObjeto, String nombre, Integer codigoObjeto, Integer codigoAccion) {
        this.idAccionObjeto = idAccionObjeto;
        this.nombre = nombre;
        this.codigoObjeto = codigoObjeto;
        this.codigoAccion = codigoAccion;
    }

    public Integer getCodigoAccion() {
        return codigoAccion;
    }

    public void setCodigoAccion(Integer codigoAccion) {
        this.codigoAccion = codigoAccion;
    }

    public Integer getCodigoObjeto() {
        return codigoObjeto;
    }

    public void setCodigoObjeto(Integer codigoObjeto) {
        this.codigoObjeto = codigoObjeto;
    }

    public Integer getIdAccionObjeto() {
        return idAccionObjeto;
    }

    public void setIdAccionObjeto(Integer idAccionObjeto) {
        this.idAccionObjeto = idAccionObjeto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(AccionObjetoBWSDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + Objects.hashCode(this.idAccionObjeto);
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
        final AccionObjetoBWSDTO other = (AccionObjetoBWSDTO) obj;
        if (!Objects.equals(this.idAccionObjeto, other.idAccionObjeto)) {
            return false;
        }
        return true;
    }
    
    
}
