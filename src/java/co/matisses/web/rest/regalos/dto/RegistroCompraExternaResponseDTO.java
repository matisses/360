package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class RegistroCompraExternaResponseDTO {

    private int codigo;
    private String mensaje;

    public RegistroCompraExternaResponseDTO() {
    }

    public RegistroCompraExternaResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
