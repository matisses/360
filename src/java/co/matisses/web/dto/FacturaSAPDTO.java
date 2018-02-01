package co.matisses.web.dto;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class FacturaSAPDTO {

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private static final DecimalFormat df = new DecimalFormat("###,###");
    private Integer numeroFactura;
    private Integer total;
    private String nitCliente;
    private String nombreCliente;
    private String estado;
    private String comentarios;
    private String asesor;
    private Date fechaFactura;
    private Date fechaVencimiento;
    private List<DetalleFacturaSAPDTO> productos;
    private List<DevolucionSAPDTO> devoluciones;
    private List<PagosFacturaDTO> pagos;

    public FacturaSAPDTO() {
        productos = new ArrayList<>();
    }

    public FacturaSAPDTO(Integer numeroFactura, Integer total, String nitCliente, String estado, String comentarios, String asesor, Date fechaFactura) {
        this.numeroFactura = numeroFactura;
        this.total = total;
        this.nitCliente = nitCliente;
        this.estado = estado;
        this.comentarios = comentarios;
        this.asesor = asesor;
        this.fechaFactura = fechaFactura;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public String getNitCliente() {
        return nitCliente;
    }

    public String getNitClienteTxt() {
        if (nitCliente != null) {
            return df.format(Integer.parseInt(nitCliente));
        }
        return null;
    }

    public void setNitCliente(String nitCliente) {
        if (nitCliente != null && nitCliente.endsWith("CL")) {
            this.nitCliente = nitCliente.substring(0, nitCliente.indexOf("CL"));
        } else {
            this.nitCliente = nitCliente;
        }
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaFactura() {
        return fechaFactura;
    }

    public String getFechaFacturaTxt() {
        if (fechaFactura != null) {
            return sdf.format(fechaFactura);
        }
        return null;
    }

    public void setFechaFactura(Date fechaFactura) {
        this.fechaFactura = fechaFactura;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getTotal() {
        return total;
    }

    public String getTotalTxt() {
        if (total != null) {
            return df.format(total);
        }
        return null;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getAsesor() {
        return asesor;
    }

    public void setAsesor(String asesor) {
        this.asesor = asesor;
    }

    public List<DetalleFacturaSAPDTO> getProductos() {
        return productos;
    }

    public void agregarProducto(DetalleFacturaSAPDTO producto) {
        int i = productos.indexOf(producto);
        if (i >= 0) {
            this.productos.get(i).setCantidad(this.productos.get(i).getCantidad() + producto.getCantidad());
        } else {
            this.productos.add(producto);
        }
    }

    public List<DevolucionSAPDTO> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<DevolucionSAPDTO> devoluciones) {
        this.devoluciones = devoluciones;
    }

    public List<PagosFacturaDTO> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosFacturaDTO> pagos) {
        this.pagos = pagos;
    }
}
