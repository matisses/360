package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dbotero
 */
@XmlRootElement
public class VentaPOSDTO {

    private Date fecha;
    private String numeroFactura;
    private String prefijoFactura;
    private String estado;
    private String nit;
    private String nombreCliente;
    private String almacen;
    private Long idVentaPOS;
    private String cuentaEfectivo;
    private String estadoPedido;
    private String usuario;
    private String estacion;
    private String comentarios;
    private Integer numeroProductos;
    private Integer totalVenta;
    private Integer efectivo;
    private Integer idTurnoCaja;
    private Integer apertura;
    private List<EmpleadoDTO> asesores;
    private List<DetalleVentaPOSDTO> productos;
    private List<PagoTarjetaPOSDTO> pagosTarjeta;
    private List<GiftCertificate> certificadosRegalo;
    private List<PagoCuentaDTO> pagosCuenta;
    private Integer docNum;
    private String almacenNombre;

    public VentaPOSDTO() {
        productos = new ArrayList<>();
        pagosTarjeta = new ArrayList<>();
        asesores = new ArrayList<>();
        certificadosRegalo = new ArrayList<>();
        pagosCuenta = new ArrayList<>();
    }

    public String getPrefijoFactura() {
        return prefijoFactura;
    }

    public void setPrefijoFactura(String prefijoFactura) {
        this.prefijoFactura = prefijoFactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getIdTurnoCaja() {
        return idTurnoCaja;
    }

    public void setIdTurnoCaja(Integer idTurnoCaja) {
        this.idTurnoCaja = idTurnoCaja;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstadoPedido() {
        return estadoPedido;
    }

    public void setEstadoPedido(String estadoPedido) {
        this.estadoPedido = estadoPedido;
    }

    public Integer getEfectivo() {
        return efectivo;
    }

    public void setEfectivo(Integer efectivo) {
        this.efectivo = efectivo;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public Long getIdVentaPOS() {
        return idVentaPOS;
    }

    public void setIdVentaPOS(Long idVentaPOS) {
        this.idVentaPOS = idVentaPOS;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstacion() {
        return estacion;
    }

    public void setEstacion(String estacion) {
        this.estacion = estacion;
    }

    public Integer getNumeroProductos() {
        return numeroProductos;
    }

    public void setNumeroProductos(Integer numeroProductos) {
        this.numeroProductos = numeroProductos;
    }

    public Integer getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(Integer totalVenta) {
        this.totalVenta = totalVenta;
    }

    public List<DetalleVentaPOSDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<DetalleVentaPOSDTO> productos) {
        this.productos = productos;
    }

    public List<PagoTarjetaPOSDTO> getPagosTarjeta() {
        return pagosTarjeta;
    }

    public void setPagosTarjeta(List<PagoTarjetaPOSDTO> pagosTarjeta) {
        this.pagosTarjeta = pagosTarjeta;
    }

    public List<EmpleadoDTO> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<EmpleadoDTO> asesores) {
        this.asesores = asesores;
    }

    public String getCuentaEfectivo() {
        return cuentaEfectivo;
    }

    public void setCuentaEfectivo(String cuentaEfectivo) {
        this.cuentaEfectivo = cuentaEfectivo;
    }

    public List<GiftCertificate> getCertificadosRegalo() {
        return certificadosRegalo;
    }

    public void setCertificadosRegalo(List<GiftCertificate> certificadosRegalo) {
        this.certificadosRegalo = certificadosRegalo;
    }

    public List<PagoCuentaDTO> getPagosCuenta() {
        return pagosCuenta;
    }

    public void setPagosCuenta(List<PagoCuentaDTO> pagosCuenta) {
        this.pagosCuenta = pagosCuenta;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

    private String productosToJSON() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productos.size(); i++) {
            DetalleVentaPOSDTO det = productos.get(i);
            sb.append(det.toJSON());
            if (i < productos.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }

    private String pagosToJSON() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < pagosTarjeta.size(); i++) {
            PagoTarjetaPOSDTO pago = pagosTarjeta.get(i);
            sb.append(pago.toJSON());
            if (i < pagosTarjeta.size() - 1) {
                sb.append(",");
            }
        }
        return sb.toString();
    }
    
    
    public Integer getApertura() {
        return apertura;
}

    public void setApertura(Integer apertura) {
        this.apertura= apertura;
    }
    
    public String getAlmacenNombre() {
        return almacenNombre;
    }

    public void setAlmacenNombre(String almacenNombre) {
        this.almacenNombre = almacenNombre;
    }
}
