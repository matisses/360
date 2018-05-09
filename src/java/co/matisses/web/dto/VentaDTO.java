package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class VentaDTO {

    private Integer factura;
    private Integer mesesVencidos;
    private BigDecimal valor;
    private BigDecimal totalIVA;
    private BigDecimal comision;
    private List<DetalleVenta> detalle;
    private List<PagosRecibidos> pagos;

    public VentaDTO() {
        detalle = new ArrayList<>();
    }

    public VentaDTO(Integer factura, BigDecimal totalIVA) {
        this.factura = factura;
        this.totalIVA = totalIVA;
        this.detalle = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public Integer getMesesVencidos() {
        return mesesVencidos;
    }

    public void setMesesVencidos(Integer mesesVencidos) {
        this.mesesVencidos = mesesVencidos;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getTotalIVA() {
        return totalIVA;
    }

    public void setTotalIVA(BigDecimal totalIVA) {
        this.totalIVA = totalIVA;
    }

    public BigDecimal getComision() {
        return comision;
    }

    public void setComision(BigDecimal comision) {
        this.comision = comision;
    }

    public List<DetalleVenta> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleVenta> detalle) {
        this.detalle = detalle;
    }

    public List<PagosRecibidos> getPagos() {
        return pagos;
    }

    public void setPagos(List<PagosRecibidos> pagos) {
        this.pagos = pagos;
    }

    public static class DetalleVenta {

        private Integer cantidad;
        private String itemCode;
        private BigDecimal valor;

        public DetalleVenta() {
        }

        public DetalleVenta(Integer cantidad, String itemCode, BigDecimal valor) {
            this.cantidad = cantidad;
            this.itemCode = itemCode;
            this.valor = valor;
        }

        public Integer getCantidad() {
            return cantidad;
        }

        public void setCantidad(Integer cantidad) {
            this.cantidad = cantidad;
        }

        public String getItemCode() {
            return itemCode;
        }

        public void setItemCode(String itemCode) {
            this.itemCode = itemCode;
        }

        public BigDecimal getValor() {
            return valor;
        }

        public void setValor(BigDecimal valor) {
            this.valor = valor;
        }
    }

    public static class PagosRecibidos {

        private Integer factura;
        private Integer numeroRecibo;
        private BigDecimal total;
        private BigDecimal totalRecibo;
        private BigDecimal totalRecibido;
        private BigDecimal efectivo;
        private BigDecimal retencion;
        private BigDecimal otros;
        private BigDecimal notasCredito;
        private BigDecimal cruces;
        private BigDecimal egresos;
        private BigDecimal cheques;
        private BigDecimal tarjetasCredito;
        private BigDecimal reconciliacion;
        private BigDecimal balance;
        private BigDecimal ajustesPeso;
        private Date fechaPago;
        private Date fechaCruceEgresos;
        private Date fechaReconcializacion;

        public PagosRecibidos() {
        }

        public PagosRecibidos(Integer factura, Integer numeroRecibo, BigDecimal total, BigDecimal totalRecibo, BigDecimal efectivo, BigDecimal retencion, BigDecimal otros,
                BigDecimal notasCredito, BigDecimal cruces, BigDecimal egresos, BigDecimal cheques, BigDecimal tarjetasCredito, BigDecimal reconciliacion,
                BigDecimal balance, BigDecimal ajustesPeso, Date fechaPago, Date fechaCruceEgresos, Date fechaReconcializacion, BigDecimal totalRecibido) {
            this.factura = factura;
            this.numeroRecibo = numeroRecibo;
            this.total = total;
            this.totalRecibo = totalRecibo;//(totalRecibo != null && totalRecibo.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(totalRecibo.doubleValue() / 1.19)) : totalRecibo;
            this.efectivo = efectivo;//(efectivo != null && efectivo.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(efectivo.doubleValue() / 1.19)) : efectivo;
            this.retencion = retencion;//(retencion != null && retencion.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(retencion.doubleValue() / 1.19)) : retencion;
            this.otros = otros;//(otros != null && otros.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(retencion.doubleValue() / 1.19)) : otros;
            this.notasCredito = notasCredito;//(notasCredito != null && notasCredito.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(notasCredito.doubleValue() / 1.19)) : notasCredito;
            this.cruces = cruces;//(cruces != null && cruces.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(cruces.doubleValue() / 1.19)) : cruces;
            this.egresos = egresos;//(egresos != null && egresos.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(egresos.doubleValue() / 1.19)) : egresos;
            this.cheques = cheques;//(cheques != null && cheques.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(cheques.doubleValue() / 1.19)) : cheques;
            this.tarjetasCredito = tarjetasCredito;//(tarjetasCredito != null && tarjetasCredito.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(tarjetasCredito.doubleValue() / 1.19)) : tarjetasCredito;
            this.reconciliacion = reconciliacion;//(reconciliacion != null && reconciliacion.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(reconciliacion.doubleValue() / 1.19)) : reconciliacion;
            this.balance = balance;//(balance != null && balance.compareTo(new BigDecimal(0)) == 1) ? (new BigDecimal(balance.doubleValue() / 1.19)) : balance;
            this.ajustesPeso = ajustesPeso;
            this.fechaPago = fechaPago;
            this.fechaCruceEgresos = fechaCruceEgresos;
            this.fechaReconcializacion = fechaReconcializacion;
            this.totalRecibido = totalRecibido;
        }

        public Integer getFactura() {
            return factura;
        }

        public void setFactura(Integer factura) {
            this.factura = factura;
        }

        public Integer getNumeroRecibo() {
            return numeroRecibo;
        }

        public void setNumeroRecibo(Integer numeroRecibo) {
            this.numeroRecibo = numeroRecibo;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public void setTotal(BigDecimal total) {
            this.total = total;
        }

        public BigDecimal getTotalRecibo() {
            return totalRecibo;
        }

        public void setTotalRecibo(BigDecimal totalRecibo) {
            this.totalRecibo = totalRecibo;
        }

        public BigDecimal getTotalRecibido() {
            return totalRecibido;
        }

        public void setTotalRecibido(BigDecimal totalRecibido) {
            this.totalRecibido = totalRecibido;
        }

        public BigDecimal getEfectivo() {
            return efectivo;
        }

        public void setEfectivo(BigDecimal efectivo) {
            this.efectivo = efectivo;
        }

        public BigDecimal getRetencion() {
            return retencion;
        }

        public void setRetencion(BigDecimal retencion) {
            this.retencion = retencion;
        }

        public BigDecimal getOtros() {
            return otros;
        }

        public void setOtros(BigDecimal otros) {
            this.otros = otros;
        }

        public BigDecimal getNotasCredito() {
            return notasCredito;
        }

        public void setNotasCredito(BigDecimal notasCredito) {
            this.notasCredito = notasCredito;
        }

        public BigDecimal getCruces() {
            return cruces;
        }

        public void setCruces(BigDecimal cruces) {
            this.cruces = cruces;
        }

        public BigDecimal getEgresos() {
            return egresos;
        }

        public void setEgresos(BigDecimal egresos) {
            this.egresos = egresos;
        }

        public BigDecimal getCheques() {
            return cheques;
        }

        public void setCheques(BigDecimal cheques) {
            this.cheques = cheques;
        }

        public BigDecimal getTarjetasCredito() {
            return tarjetasCredito;
        }

        public void setTarjetasCredito(BigDecimal tarjetasCredito) {
            this.tarjetasCredito = tarjetasCredito;
        }

        public BigDecimal getReconciliacion() {
            return reconciliacion;
        }

        public void setReconciliacion(BigDecimal reconciliacion) {
            this.reconciliacion = reconciliacion;
        }

        public BigDecimal getBalance() {
            return balance;
        }

        public void setBalance(BigDecimal balance) {
            this.balance = balance;
        }

        public BigDecimal getAjustesPeso() {
            return ajustesPeso;
        }

        public void setAjustesPeso(BigDecimal ajustesPeso) {
            this.ajustesPeso = ajustesPeso;
        }

        public Date getFechaPago() {
            return fechaPago;
        }

        public void setFechaPago(Date fechaPago) {
            this.fechaPago = fechaPago;
        }

        public Date getFechaCruceEgresos() {
            return fechaCruceEgresos;
        }

        public void setFechaCruceEgresos(Date fechaCruceEgresos) {
            this.fechaCruceEgresos = fechaCruceEgresos;
        }

        public Date getFechaReconcializacion() {
            return fechaReconcializacion;
        }

        public void setFechaReconcializacion(Date fechaReconcializacion) {
            this.fechaReconcializacion = fechaReconcializacion;
        }

    }
}
