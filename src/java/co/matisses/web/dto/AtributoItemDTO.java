package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class AtributoItemDTO {
    private String nombre;
    private String valor;

    public AtributoItemDTO() {
    }

    public AtributoItemDTO(String nombre, String valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
