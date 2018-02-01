package co.matisses.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dbotero
 */
@XmlRootElement
public class ClientePOSResponseDTO {
    private String codigo;
    private String mensaje;

    public ClientePOSResponseDTO() {
    }

    public ClientePOSResponseDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
