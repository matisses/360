package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class GrupoArticuloDTO implements Comparable<GrupoArticuloDTO> {

    private String codigo;
    private String nombre;

    public GrupoArticuloDTO() {
    }

    public GrupoArticuloDTO(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    @Override
    public int compareTo(GrupoArticuloDTO o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
