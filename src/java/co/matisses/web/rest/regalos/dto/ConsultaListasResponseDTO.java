package co.matisses.web.rest.regalos.dto;

import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.web.ObjectUtils;
import co.matisses.web.dto.ListaRegalosDTO;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ConsultaListasResponseDTO {

    private int codigo;
    private SocioDeNegocios cliente;
    private String mensaje;
    private ListaRegalosDTO lista;
    private List<ListaRegalosDTO> listas;

    public ConsultaListasResponseDTO() {
    }

    public ConsultaListasResponseDTO(int codigo, SocioDeNegocios cliente, List<ListaRegalosDTO> listas) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.listas = listas;
    }
    
    public ConsultaListasResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public ConsultaListasResponseDTO(int codigo, String mensaje, SocioDeNegocios cliente) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.cliente = cliente;
    }

    public ConsultaListasResponseDTO(int codigo, ListaRegalosDTO lista) {
        this.codigo = codigo;
        this.lista = lista;
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

    public ListaRegalosDTO getLista() {
        return lista;
    }

    public void setLista(ListaRegalosDTO lista) {
        this.lista = lista;
    }

    public SocioDeNegocios getCliente() {
        return cliente;
    }

    public void setCliente(SocioDeNegocios cliente) {
        this.cliente = cliente;
    }

    public List<ListaRegalosDTO> getListas() {
        return listas;
    }

    public void setListas(List<ListaRegalosDTO> listas) {
        this.listas = listas;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
