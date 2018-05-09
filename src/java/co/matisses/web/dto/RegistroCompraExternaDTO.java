package co.matisses.web.rest.regalos.dto;

import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.web.ObjectUtils;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class RegistroCompraExternaDTO {

    private String docNumFV;
    private String codigoLista;
    private String mensaje;
    private List<ProductoCompraExternaDTO> productos;

    public RegistroCompraExternaDTO() {
        productos = new ArrayList<>();
    }

    public String getCodigoLista() {
        return codigoLista;
    }

    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }

    public String getDocNumFV() {
        return docNumFV;
    }

    public void setDocNumFV(String docNumFV) {
        this.docNumFV = docNumFV;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public List<ProductoCompraExternaDTO> getProductos() {
        return productos;
    }    

    public void setProductos(List<ProductoCompraExternaDTO> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
