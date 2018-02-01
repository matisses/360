package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class SesionListaRegalosDTO {

    private Date fechaCreacion;
    private TipoSesion tipo;
    private String idSession;
    private boolean sesionValida;
    private String mensajeError;
    private ClienteSAPDTO cliente;
    private int valorBono;
    private List<ProductoListaRegalosInvitadoDTO> productos;

    public SesionListaRegalosDTO() {
        fechaCreacion = new Date();
        productos = new ArrayList<>();
        sesionValida = true;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public ClienteSAPDTO getCliente() {
        return cliente;
    }

    public int getValorBono() {
        return valorBono;
    }

    public void setValorBono(int valorBono) {
        this.valorBono = valorBono;
    }

    public void setCliente(ClienteSAPDTO cliente) {
        this.cliente = cliente;
    }

    public TipoSesion getTipo() {
        return tipo;
    }

    public void setTipo(TipoSesion tipo) {
        this.tipo = tipo;
    }

    public String getIdSession() {
        return idSession;
    }

    public void setIdSession(String idSession) {
        this.idSession = idSession;
    }

    public boolean isSesionValida() {
        return sesionValida;
    }

    public void setSesionValida(boolean sesionValida) {
        this.sesionValida = sesionValida;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public List<ProductoListaRegalosInvitadoDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoListaRegalosInvitadoDTO> productos) {
        this.productos = productos;
    }

    public void agregarProducto(ProductoListaRegalosInvitadoDTO producto) {
        int pos = productos.indexOf(producto);
        if (pos < 0) {
            productos.add(producto);
        } else {
            productos.get(pos).setCantidad(producto.getCantidad());
        }
    }

    public void eliminarProducto(ProductoListaRegalosInvitadoDTO producto) {
        if (productos.contains(producto)) {
            productos.remove(producto);
        }
    }

    public static enum TipoSesion {
        INVITADO("I"), PROPIETARIO("P"), ADMINISTRADOR("A"), VENDEDOR("V");
        private String tipo;

        private TipoSesion(String t) {
            this.tipo = t;
        }

        public String getTipo() {
            return tipo;
        }

        public TipoSesion getType(String t) {
            for (TipoSesion dto : values()) {
                if (dto.getTipo().equals(t)) {
                    return dto;
                }
            }
            return null;
        }
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
