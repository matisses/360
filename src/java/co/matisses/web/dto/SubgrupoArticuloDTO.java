package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class SubgrupoArticuloDTO implements Comparable<SubgrupoArticuloDTO> {

    private String codigoGrupo;
    private String codigo;
    private String nombre;

    public SubgrupoArticuloDTO() {
    }

    public SubgrupoArticuloDTO(String codigoGrupo, String codigo, String nombre) {
        this.codigoGrupo = codigoGrupo;
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
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
    public int compareTo(SubgrupoArticuloDTO o) {
        return this.getNombre().compareTo(o.getNombre());
    }
}
