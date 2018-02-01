package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class CrearListaResponseDTO {

    private int codigo;
    private String mensaje;

    public CrearListaResponseDTO() {
    }

    public CrearListaResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
