package co.matisses.web.mbean.session;

import co.matisses.web.dto.ArticulosFacturacionDTO;
import co.matisses.web.dto.CotizacionWebDTO;
import co.matisses.web.dto.EmpleadoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@SessionScoped
@Named(value = "ventasSessionMBean")
public class VentasSessionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(VentasSessionMBean.class.getSimpleName());
    private Integer idDemostracion;
    private Integer numeroFactura;
    private Long numeroCotizacion;
    private String decorador;
    private String comentarioFactura;
    private String documentoCliente;
    private boolean facturando = false;
    private boolean clienteVerificado = false;
    private boolean exitoCotizacion = false;
    private boolean exitoDemostracion = false;
    private boolean modificando = false;
    private boolean cotizacionCorrompida = false;
    private boolean demostracion = false;
    private boolean creaNotaCredito = false;
    private boolean cotizacionEspecial = false;
    private boolean precio = false;
    private boolean costo = false;
    private CotizacionWebDTO cotizacion;
    private List<EmpleadoDTO> empleadosVenta;
    private List<ArticulosFacturacionDTO> articulosFacturacion;

    public VentasSessionMBean() {
        empleadosVenta = new ArrayList<>();
        articulosFacturacion = new ArrayList<>();
        cotizacion = new CotizacionWebDTO();
    }

    @PostConstruct
    protected void initialize() {
    }

    public Integer getIdDemostracion() {
        return idDemostracion;
    }

    public void setIdDemostracion(Integer idDemostracion) {
        this.idDemostracion = idDemostracion;
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Long getNumeroCotizacion() {
        return numeroCotizacion;
    }

    public void setNumeroCotizacion(Long numeroCotizacion) {
        this.numeroCotizacion = numeroCotizacion;
    }

    public String getDecorador() {
        return decorador;
    }

    public void setDecorador(String decorador) {
        this.decorador = decorador;
    }

    public String getComentarioFactura() {
        return comentarioFactura;
    }

    public void setComentarioFactura(String comentarioFactura) {
        this.comentarioFactura = comentarioFactura;
    }

    public String getDocumentoCliente() {
        return documentoCliente;
    }

    public void setDocumentoCliente(String documentoCliente) {
        this.documentoCliente = documentoCliente;
    }

    public boolean isFacturando() {
        return facturando;
    }

    public void setFacturando(boolean facturando) {
        this.facturando = facturando;
    }

    public boolean isClienteVerificado() {
        return clienteVerificado;
    }

    public void setClienteVerificado(boolean clienteVerificado) {
        this.clienteVerificado = clienteVerificado;
    }

    public boolean isExitoCotizacion() {
        return exitoCotizacion;
    }

    public void setExitoCotizacion(boolean exitoCotizacion) {
        this.exitoCotizacion = exitoCotizacion;
    }

    public boolean isExitoDemostracion() {
        return exitoDemostracion;
    }

    public void setExitoDemostracion(boolean exitoDemostracion) {
        this.exitoDemostracion = exitoDemostracion;
    }

    public boolean isModificando() {
        return modificando;
    }

    public void setModificando(boolean modificando) {
        this.modificando = modificando;
    }

    public boolean isCotizacionCorrompida() {
        return cotizacionCorrompida;
    }

    public void setCotizacionCorrompida(boolean cotizacionCorrompida) {
        this.cotizacionCorrompida = cotizacionCorrompida;
    }

    public boolean isDemostracion() {
        return demostracion;
    }

    public void setDemostracion(boolean demostracion) {
        this.demostracion = demostracion;
    }

    public boolean isCreaNotaCredito() {
        return creaNotaCredito;
    }

    public void setCreaNotaCredito(boolean creaNotaCredito) {
        this.creaNotaCredito = creaNotaCredito;
    }

    public boolean isCotizacionEspecial() {
        return cotizacionEspecial;
    }

    public void setCotizacionEspecial(boolean cotizacionEspecial) {
        this.cotizacionEspecial = cotizacionEspecial;
    }

    public boolean isPrecio() {
        return precio;
    }

    public void setPrecio(boolean precio) {
        this.precio = precio;
    }

    public boolean isCosto() {
        return costo;
    }

    public void setCosto(boolean costo) {
        this.costo = costo;
    }

    public CotizacionWebDTO getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionWebDTO cotizacion) {
        this.cotizacion = cotizacion;
    }

    public List<EmpleadoDTO> getEmpleadosVenta() {
        return empleadosVenta;
    }

    public void setEmpleadosVenta(List<EmpleadoDTO> empleadosVenta) {
        this.empleadosVenta = empleadosVenta;
    }

    public List<ArticulosFacturacionDTO> getArticulosFacturacion() {
        return articulosFacturacion;
    }

    public void setArticulosFacturacion(List<ArticulosFacturacionDTO> articulosFacturacion) {
        this.articulosFacturacion = articulosFacturacion;
    }
}
