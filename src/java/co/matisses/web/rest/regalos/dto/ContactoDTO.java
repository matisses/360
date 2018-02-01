package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class ContactoDTO {

    private String nombre;
    private String email;
    private String asunto;
    private String mensaje;

    public ContactoDTO() {
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
