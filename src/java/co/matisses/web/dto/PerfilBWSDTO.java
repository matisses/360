package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class PerfilBWSDTO implements Comparable<PerfilBWSDTO> {

    private Integer codigo;
    private String nombre;
    private List<AccionObjetoBWSDTO> acciones;
    private List<String> usuarios;

    public PerfilBWSDTO() {
        acciones = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public PerfilBWSDTO(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        acciones = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    public List<AccionObjetoBWSDTO> getAcciones() {
        return acciones;
    }

    public void setAcciones(List<AccionObjetoBWSDTO> acciones) {
        this.acciones = acciones;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public void agregarUsuario(String usuario) {
        usuarios.add(usuario);
    }

    public void agregarAccion(Integer idAccionObjeto, String nombreAccion, Integer codigoObjeto, Integer codigoAccion) {
        acciones.add(new AccionObjetoBWSDTO(idAccionObjeto, nombreAccion, codigoObjeto, codigoAccion));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.codigo);
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
        final PerfilBWSDTO other = (PerfilBWSDTO) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(PerfilBWSDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
