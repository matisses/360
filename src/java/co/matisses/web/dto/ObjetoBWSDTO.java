package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class ObjetoBWSDTO implements Comparable<ObjetoBWSDTO> {

    private Integer idObjeto;
    private String nombre;

    public ObjetoBWSDTO() {
    }

    public ObjetoBWSDTO(Integer idObjeto, String nombre) {
        this.idObjeto = idObjeto;
        this.nombre = nombre;
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public void setIdObjeto(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public int compareTo(ObjetoBWSDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }
}
