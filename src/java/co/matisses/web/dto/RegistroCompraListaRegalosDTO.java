package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.Date;

/**
 *
 * @author dbotero
 */
public class RegistroCompraListaRegalosDTO {

    private Long idCompra;
    private Long idLista;
    private Long idProducto;
    private Integer cantidad;
    private Long valor;
    private String quienCompra;
    private Date fecha;
    private String factura;
    private String mensaje;

    public RegistroCompraListaRegalosDTO() {
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public void setIdCompra(Long idCompra) {
        this.idCompra = idCompra;
    }

    public Long getValor() {
        return valor;
    }

    public void setValor(Long valor) {
        this.valor = valor;
    }

    public Long getIdLista() {
        return idLista;
    }

    public void setIdLista(Long idLista) {
        this.idLista = idLista;
    }

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getQuienCompra() {
        return quienCompra;
    }

    public void setQuienCompra(String quienCompra) {
        this.quienCompra = quienCompra;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
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
