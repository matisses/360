package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class MensajeDTO {

    private MensajeDTO.Tipo tipo;
    private String titulo;
    private String mensaje;

    public MensajeDTO() {
    }

    public MensajeDTO(Tipo tipo, String titulo, String mensaje) {
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public static enum Tipo {

        ERROR, ADVERTENCIA, INFO;
    }
}
