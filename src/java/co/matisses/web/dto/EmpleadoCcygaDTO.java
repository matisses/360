package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class EmpleadoCcygaDTO implements Comparable<EmpleadoCcygaDTO> {

    private String nombre;
    private String cedula;
    private String codigoRevisado;
    private String tiempoInactividad;

    public EmpleadoCcygaDTO() {
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoRevisado() {
        return codigoRevisado;
    }

    public void setCodigoRevisado(String codigoRevisado) {
        this.codigoRevisado = codigoRevisado;
    }

    public String getTiempoInactividad() {
        return tiempoInactividad;
    }

    public void setTiempoInactividad(String tiempoInactividad) {
        this.tiempoInactividad = tiempoInactividad;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cedula);
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
        final EmpleadoCcygaDTO other = (EmpleadoCcygaDTO) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(EmpleadoCcygaDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
