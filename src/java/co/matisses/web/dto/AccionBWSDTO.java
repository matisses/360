package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class AccionBWSDTO implements Comparable<AccionBWSDTO>{
    private Integer codigo;
    private String nombre;

    public AccionBWSDTO() {
    }

    public AccionBWSDTO(Integer codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
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

    @Override
    public int compareTo(AccionBWSDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
