package co.matisses.web.rest.regalos.dto;

import co.matisses.web.ObjectUtils;
import co.matisses.web.dto.ClienteSAPDTO;

/**
 *
 * @author dbotero
 */
public class ValidacionCodigoDTO {

    private ClienteSAPDTO cliente;
    private String codigo;

    public ValidacionCodigoDTO() {
    }

    public void setCliente(ClienteSAPDTO cliente) {
        this.cliente = cliente;
    }

    public ClienteSAPDTO getCliente() {
        return cliente;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
