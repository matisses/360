package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;
import co.matisses.web.dto.ClienteSAPDTO;

/**
 *
 * @author dbotero
 */
public class ClienteResponseDTO {

    private int codigo;
    private String mensaje;
    private ClienteSAPDTO cliente;

    public ClienteResponseDTO() {
    }
    
    public ClienteResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ClienteResponseDTO(int codigo, String mensaje, ClienteSAPDTO cliente) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.cliente = cliente;
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

    public ClienteSAPDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteSAPDTO cliente) {
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
