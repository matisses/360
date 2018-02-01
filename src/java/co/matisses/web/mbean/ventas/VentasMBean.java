package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.BaruDecoradores;
import co.matisses.persistence.sap.entity.BaruMunicipios;
import co.matisses.persistence.sap.entity.BaruSeriesAlmacen;
import co.matisses.persistence.sap.entity.CondicionPago;
import co.matisses.persistence.sap.entity.CotizacionSAP;
import co.matisses.persistence.sap.entity.DetalleCotizacionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.entity.TarjetaCreditoSAP;
import co.matisses.persistence.sap.entity.TaxExtension;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.BaruDecoradoresFacade;
import co.matisses.persistence.sap.facade.BaruMunicipiosFacade;
import co.matisses.persistence.sap.facade.BaruParametrosWebFacade;
import co.matisses.persistence.sap.facade.BaruSeriesAlmacenFacade;
import co.matisses.persistence.sap.facade.CondicionPagoFacade;
import co.matisses.persistence.sap.facade.ConfiguracionAtributoUbicacionFacade;
import co.matisses.persistence.sap.facade.CotizacionSAPFacade;
import co.matisses.persistence.sap.facade.DetalleCotizacionSAPFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.SubnivelUbicacionFacade;
import co.matisses.persistence.sap.facade.TarjetaCreditoSAPFacade;
import co.matisses.persistence.sap.facade.TaxExtensionFacade;
import co.matisses.persistence.sap.facade.TrasladosSAPFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.AutorizacionPago;
import co.matisses.persistence.web.entity.BancoFacturacion;
import co.matisses.persistence.web.entity.CotizacionWeb;
import co.matisses.persistence.web.entity.Demostracion;
import co.matisses.persistence.web.entity.DetalleCotizacionWeb;
import co.matisses.persistence.web.entity.DetalleDemostracion;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.entity.ProveedoresExterior;
import co.matisses.persistence.web.facade.AutorizacionPagoFacade;
import co.matisses.persistence.web.facade.BancoFacturacionFacade;
import co.matisses.persistence.web.facade.CotizacionWebFacade;
import co.matisses.persistence.web.facade.DemostracionFacade;
import co.matisses.persistence.web.facade.DetalleCotizacionWebFacade;
import co.matisses.persistence.web.facade.DetalleDemostracionFacade;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.persistence.web.facade.ProveedoresExteriorFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.BinLocationAttributesClient;
import co.matisses.web.bcs.client.BinLocationsClient;
import co.matisses.web.bcs.client.GoodsReceiptClient;
import co.matisses.web.bcs.client.IncomingPaymentClient;
import co.matisses.web.bcs.client.InvoicesClient;
import co.matisses.web.bcs.client.OrderClient;
import co.matisses.web.bcs.client.QuotationsClient;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.bcs.client.StockTransferClient;
import co.matisses.web.bcs.client.WarehouseSublevelCodesClient;
import co.matisses.web.dto.AddressExtensionDTO;
import co.matisses.web.dto.AutorizacionPagoDTO;
import co.matisses.web.dto.AutorizadoresPagoFacturaDTO;
import co.matisses.web.dto.BancoFacturacionDTO;
import co.matisses.web.dto.BaruDecoradoresDTO;
import co.matisses.web.dto.BinLocationAttributesDTO;
import co.matisses.web.dto.BinLocationDTO;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.CondicionPagoDTO;
import co.matisses.web.dto.CotizacionWebDTO;
import co.matisses.web.dto.CreditCardPaymentDTO;
import co.matisses.web.dto.DetalleCotizacionWebDTO;
import co.matisses.web.dto.DireccionesClienteDTO;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.GoodsReceiptDTO;
import co.matisses.web.dto.GoodsReceiptDetailDTO;
import co.matisses.web.dto.GoodsReceiptLocationsDTO;
import co.matisses.web.dto.InformacionAlmacenDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.OrderDTO;
import co.matisses.web.dto.OrderDetailDTO;
import co.matisses.web.dto.PagoTarjetaDTO;
import co.matisses.web.dto.PaymentDTO;
import co.matisses.web.dto.PaymentInvoicesDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.QuotationsDocumentDTO;
import co.matisses.web.dto.QuotationsDocumentLinesDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.dto.SalesDocumentDTO;
import co.matisses.web.dto.SalesDocumentLineBinAllocationDTO;
import co.matisses.web.dto.SalesDocumentLineDTO;
import co.matisses.web.dto.SalesEmployeeDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.StockTransferDocumentBinAllocationDTO;
import co.matisses.web.dto.StockTransferDocumentDTO;
import co.matisses.web.dto.StockTransferDocumentLinesDTO;
import co.matisses.web.dto.TarjetaCreditoDTO;
import co.matisses.web.dto.TaxExtensionDTO;
import co.matisses.web.dto.WarehouseSublevelCodesDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.ClienteSessionMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.session.VentasSessionMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "ventasMBean")
public class VentasMBean implements Serializable {

    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private ClienteSessionMBean clienteSessionMBean;
    private static final Logger log = Logger.getLogger(VentasMBean.class.getSimpleName());
    private int posicionItem = 0;
    private int asesores = 0;
    private Integer medioPago = 0;
    private Integer pasosPago = 1;
    private Integer condicionPago;
    private Integer pasosFacturacion = 1;
    private Integer banco;
    private Integer facturaAsociada;
    private Integer idDemostracion;
    private Integer asesorDemo;
    private Long pagoTarjeta;
    private Long descuento;
    private Long porcentajeDescuento;
    private BigDecimal totalFactura;
    private BigDecimal valorPendienteFactura;
    private BigDecimal valorPagoPendiente;
    private BigDecimal valorEfectivo;
    private BigDecimal valorTarjeta;
    private BigDecimal saldoFavor;
    private BigDecimal valorCruce;
    private String referencia;
    private String almacen;
    private String comentarioPagoPendiente;
    private String franquicia;
    private String tarjeta;
    private String voucher;
    private String digitos;
    private String datafono;
    private String tabAsesor;
    private String decorador;
    private String comentarioFactura;
    private String nombreDemostracion;
    private String comentarioDemostracion;
    private String slot;
    private boolean dlgSaldos = false;
    private boolean dlgConfirmarEliminacion = false;
    private boolean dlgCrearFactura = false;
    private boolean dlgDemostracion = false;
    private boolean dlgFacturarDemo = false;
    private boolean imprimir = false;
    private boolean enviar = false;
    private boolean ventaCorrompida = false;
    private Date fechaDemostracion;
    private CotizacionWebDTO cotizacion;
    private DetalleCotizacionWebDTO detalleVenta;
    private List<Integer> facturas;
    private List<String> franquicias;
    private List<String[]> imagenes;
    private List<SaldoItemDTO> saldos;
    private List<SaldoItemDTO> saldosAcumulados;
    private List<AutorizadoresPagoFacturaDTO> autorizadoresPagoFactura;
    private List<CondicionPagoDTO> condicionesPago;
    private List<AutorizacionPagoDTO> autorizaciones;
    private List<BancoFacturacionDTO> bancos;
    private List<TarjetaCreditoDTO> datafonos;
    private List<PagoTarjetaDTO> pagosTarjetas;
    private List<BaruDecoradoresDTO> decoradores;
    private Map<String, List<EmpleadoDTO>> empleadosComision;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private CotizacionWebFacade cotizacionWebFacade;
    @EJB
    private DetalleCotizacionWebFacade detalleCotizacionWebFacade;
    @EJB
    private CotizacionSAPFacade cotizacionSAPFacade;
    @EJB
    private DetalleCotizacionSAPFacade detalleCotizacionSAPFacade;
    @EJB
    private TaxExtensionFacade taxExtensionFacade;
    @EJB
    private BaruSeriesAlmacenFacade baruSeriesAlmacenFacade;
    @EJB
    private CondicionPagoFacade condicionPagoFacade;
    @EJB
    private AutorizacionPagoFacade autorizacionPagoFacade;
    @EJB
    private BancoFacturacionFacade bancoFacturacionFacade;
    @EJB
    private TarjetaCreditoSAPFacade tarjetaCreditoSAPFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private BaruDecoradoresFacade baruDecoradoresFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private BaruMunicipiosFacade baruMunicipiosFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private BaruParametrosWebFacade baruParametrosWebFacade;
    @EJB
    private SubnivelUbicacionFacade subnivelUbicacionFacade;
    @EJB
    private DemostracionFacade demostracionFacade;
    @EJB
    private DetalleDemostracionFacade detalleDemostracionFacade;
    @EJB
    private ConfiguracionAtributoUbicacionFacade configuracionAtributoUbicacionFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;
    @EJB
    private ProveedoresExteriorFacade proveedoresExteriorFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;

    public VentasMBean() {
        cotizacion = new CotizacionWebDTO();
        detalleVenta = new DetalleCotizacionWebDTO();
        facturas = new ArrayList<>();
        franquicias = new ArrayList<>();
        imagenes = new ArrayList<>();
        saldos = new ArrayList<>();
        saldosAcumulados = new ArrayList<>();
        autorizadoresPagoFactura = new ArrayList<>();
        condicionesPago = new ArrayList<>();
        autorizaciones = new ArrayList<>();
        bancos = new ArrayList<>();
        datafonos = new ArrayList<>();
        pagosTarjetas = new ArrayList<>();
        decoradores = new ArrayList<>();
        empleadosComision = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
            asignarSaldosDemostracion();
            ventasSessionMBean.setClienteVerificado(true);
            return;
        } else if (ventasSessionMBean.isModificando() && !ventasSessionMBean.isCotizacionCorrompida()) {
            obtenerCotizacionSAP();
        } else if (ventasSessionMBean.getCotizacion() != null && ventasSessionMBean.getCotizacion().getIdCotizacion() != null && ventasSessionMBean.getCotizacion().getIdCotizacion() != 0) {
            cotizacion = ventasSessionMBean.getCotizacion();
            ventaCorrompida = ventasSessionMBean.isCotizacionCorrompida();
            if (cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
                for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                    saldosAcumulados.add(0, new SaldoItemDTO(d.getCantidad(), d.getCantidad(), getObtenerDescuento(d.getReferencia()), d.getReferencia(), d.getBodega()));
                }
            }

            if (ventasSessionMBean.isExitoCotizacion()) {
                mostrarMensaje("Éxito", "Se creó la cotización con id " + ventasSessionMBean.getNumeroCotizacion(), false, true, false);
            }
        } else {
            obtenerVentaPendiente();
        }
        if (ventasSessionMBean.isFacturando()) {
            totalFactura = getTotalVenta();
            valorPendienteFactura = totalFactura;
            crearFactura();
        } else if (ventasSessionMBean.isDemostracion()) {
            dlgDemostracion = true;

            if (ventasSessionMBean.getDocumentoCliente() != null && !ventasSessionMBean.getDocumentoCliente().isEmpty()) {
                facturas = facturaSAPFacade.obtenerFacturasPendientes(ventasSessionMBean.getDocumentoCliente().contains("CL")
                        ? ventasSessionMBean.getDocumentoCliente() : ventasSessionMBean.getDocumentoCliente() + "CL");
            }
        }
    }

    public int getPosicionItem() {
        return posicionItem;
    }

    public void setPosicionItem(int posicionItem) {
        this.posicionItem = posicionItem;
    }

    public Integer getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(Integer medioPago) {
        this.medioPago = medioPago;
    }

    public Integer getPasosPago() {
        return pasosPago;
    }

    public void setPasosPago(Integer pasosPago) {
        this.pasosPago = pasosPago;
    }

    public Integer getPasosFacturacion() {
        return pasosFacturacion;
    }

    public void setPasosFacturacion(Integer pasosFacturacion) {
        this.pasosFacturacion = pasosFacturacion;
    }

    public Long getPagoTarjeta() {
        return pagoTarjeta;
    }

    public void setPagoTarjeta(Long pagoTarjeta) {
        this.pagoTarjeta = pagoTarjeta;
    }

    public Long getDescuento() {
        return descuento;
    }

    public void setDescuento(Long descuento) {
        this.descuento = descuento;
    }

    public Long getPorcentajeDescuento() {
        return porcentajeDescuento;
    }

    public void setPorcentajeDescuento(Long porcentajeDescuento) {
        this.porcentajeDescuento = porcentajeDescuento;
    }

    public BigDecimal getTotalFactura() {
        return totalFactura;
    }

    public void setTotalFactura(BigDecimal totalFactura) {
        this.totalFactura = totalFactura;
    }

    public BigDecimal getValorPendienteFactura() {
        return valorPendienteFactura;
    }

    public void setValorPendienteFactura(BigDecimal valorPendienteFactura) {
        this.valorPendienteFactura = valorPendienteFactura;
    }

    public BigDecimal getValorPagoPendiente() {
        return valorPagoPendiente;
    }

    public void setValorPagoPendiente(BigDecimal valorPagoPendiente) {
        this.valorPagoPendiente = valorPagoPendiente;
    }

    public BigDecimal getValorEfectivo() {
        return valorEfectivo;
    }

    public void setValorEfectivo(BigDecimal valorEfectivo) {
        this.valorEfectivo = valorEfectivo;
    }

    public BigDecimal getValorTarjeta() {
        return valorTarjeta;
    }

    public void setValorTarjeta(BigDecimal valorTarjeta) {
        this.valorTarjeta = valorTarjeta;
    }

    public BigDecimal getSaldoFavor() {
        return saldoFavor;
    }

    public void setSaldoFavor(BigDecimal saldoFavor) {
        this.saldoFavor = saldoFavor;
    }

    public BigDecimal getValorCruce() {
        return valorCruce;
    }

    public void setValorCruce(BigDecimal valorCruce) {
        this.valorCruce = valorCruce;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getComentarioPagoPendiente() {
        return comentarioPagoPendiente;
    }

    public void setComentarioPagoPendiente(String comentarioPagoPendiente) {
        this.comentarioPagoPendiente = comentarioPagoPendiente;
    }

    public String getVoucher() {
        return voucher;
    }

    public void setVoucher(String voucher) {
        this.voucher = voucher;
    }

    public String getDigitos() {
        return digitos;
    }

    public void setDigitos(String digitos) {
        this.digitos = digitos;
    }

    public String getTabAsesor() {
        return tabAsesor;
    }

    public void setTabAsesor(String tabAsesor) {
        this.tabAsesor = tabAsesor;
    }

    public String getComentarioFactura() {
        return comentarioFactura;
    }

    public void setComentarioFactura(String comentarioFactura) {
        this.comentarioFactura = comentarioFactura;
    }

    public String getNombreDemostracion() {
        return nombreDemostracion;
    }

    public void setNombreDemostracion(String nombreDemostracion) {
        this.nombreDemostracion = nombreDemostracion;
    }

    public String getComentarioDemostracion() {
        return comentarioDemostracion;
    }

    public void setComentarioDemostracion(String comentarioDemostracion) {
        this.comentarioDemostracion = comentarioDemostracion;
    }

    public boolean isDlgSaldos() {
        return dlgSaldos;
    }

    public void setDlgSaldos(boolean dlgSaldos) {
        this.dlgSaldos = dlgSaldos;
    }

    public boolean isDlgConfirmarEliminacion() {
        return dlgConfirmarEliminacion;
    }

    public void setDlgConfirmarEliminacion(boolean dlgConfirmarEliminacion) {
        this.dlgConfirmarEliminacion = dlgConfirmarEliminacion;
    }

    public boolean isDlgCrearFactura() {
        return dlgCrearFactura;
    }

    public void setDlgCrearFactura(boolean dlgCrearFactura) {
        this.dlgCrearFactura = dlgCrearFactura;
    }

    public boolean isDlgDemostracion() {
        return dlgDemostracion;
    }

    public void setDlgDemostracion(boolean dlgDemostracion) {
        this.dlgDemostracion = dlgDemostracion;
    }

    public boolean getDlgFacturarDemo() {
        return dlgFacturarDemo;
    }

    public void setDlgFacturarDemo(boolean dlgFacturarDemo) {
        this.dlgFacturarDemo = dlgFacturarDemo;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isEnviar() {
        return enviar;
    }

    public void setEnviar(boolean enviar) {
        this.enviar = enviar;
    }

    public boolean isVentaCorrompida() {
        return ventaCorrompida;
    }

    public void setVentaCorrompida(boolean ventaCorrompida) {
        this.ventaCorrompida = ventaCorrompida;
    }

    public Date getFechaDemostracion() {
        return fechaDemostracion;
    }

    public void setFechaDemostracion(Date fechaDemostracion) {
        this.fechaDemostracion = fechaDemostracion;
    }

    public DetalleCotizacionWebDTO getDetalleVenta() {
        return detalleVenta;
    }

    public void setDetalleVenta(DetalleCotizacionWebDTO detalleVenta) {
        this.detalleVenta = detalleVenta;
    }

    public List<Integer> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<Integer> facturas) {
        this.facturas = facturas;
    }

    public List<String> getFranquicias() {
        return franquicias;
    }

    public void setFranquicias(List<String> franquicias) {
        this.franquicias = franquicias;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public List<SaldoItemDTO> getSaldosAcumulados() {
        return saldosAcumulados;
    }

    public void setSaldosAcumulados(List<SaldoItemDTO> saldosAcumulados) {
        this.saldosAcumulados = saldosAcumulados;
    }

    public List<AutorizadoresPagoFacturaDTO> getAutorizadoresPagoFactura() {
        return autorizadoresPagoFactura;
    }

    public void setAutorizadoresPagoFactura(List<AutorizadoresPagoFacturaDTO> autorizadoresPagoFactura) {
        this.autorizadoresPagoFactura = autorizadoresPagoFactura;
    }

    public List<CondicionPagoDTO> getCondicionesPago() {
        return condicionesPago;
    }

    public void setCondicionesPago(List<CondicionPagoDTO> condicionesPago) {
        this.condicionesPago = condicionesPago;
    }

    public List<AutorizacionPagoDTO> getAutorizaciones() {
        return autorizaciones;
    }

    public void setAutorizaciones(List<AutorizacionPagoDTO> autorizaciones) {
        this.autorizaciones = autorizaciones;
    }

    public List<BancoFacturacionDTO> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoFacturacionDTO> bancos) {
        this.bancos = bancos;
    }

    public List<TarjetaCreditoDTO> getDatafonos() {
        return datafonos;
    }

    public void setDatafonos(List<TarjetaCreditoDTO> datafonos) {
        this.datafonos = datafonos;
    }

    public List<PagoTarjetaDTO> getPagosTarjetas() {
        return pagosTarjetas;
    }

    public void setPagosTarjetas(List<PagoTarjetaDTO> pagosTarjetas) {
        this.pagosTarjetas = pagosTarjetas;
    }

    public List<BaruDecoradoresDTO> getDecoradores() {
        return decoradores;
    }

    public void setDecoradores(List<BaruDecoradoresDTO> decoradores) {
        this.decoradores = decoradores;
    }

    public Map<String, List<EmpleadoDTO>> getEmpleadosComision() {
        return empleadosComision;
    }

    public void setEmpleadosComision(Map<String, List<EmpleadoDTO>> empleadosComision) {
        this.empleadosComision = empleadosComision;
    }

    public String getObtenerMiga() {
        if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
            return "Modificando demostración <b>[" + idDemostracion + "] " + nombreDemostracion + "</b>";
        } else if (ventasSessionMBean.isModificando()) {
            return "Modificando cotización <b>" + (ventasSessionMBean.getNumeroCotizacion() != null ? ventasSessionMBean.getNumeroCotizacion() : "") + "</b>";
        } else {
            return "Crear venta";
        }
    }

    public String getObtenerMigaFacturacion() {
        StringBuilder miga = new StringBuilder();

        miga.append("<ol class=\"breadcrumb\">");
        if (null != pasosFacturacion) {
            switch (pasosFacturacion) {
                case 1:
                    miga.append("<li class=\"active\">");
                    miga.append("<a>");
                    miga.append("Pagos");
                    miga.append("</a>");
                    miga.append("</li>");
                    if (null != medioPago) {
                        switch (medioPago) {
                            case 1:
                                miga.append("<li class=\"active\">");
                                miga.append("<a>");
                                miga.append("Efectivo");
                                miga.append("</a>");
                                miga.append("</li>");
                                break;
                            case 2:
                                miga.append("<li class=\"active\">");
                                miga.append("<a>");
                                miga.append("Tarjetas");
                                miga.append("</a>");
                                miga.append("</li>");
                                if (null != pasosPago) {
                                    switch (pasosPago) {
                                        case 1:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Pagos realizados");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        case 2:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Datos pago");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        case 3:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Franquicias");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        case 4:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Bancos");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        case 5:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Tipos tarjetas");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        case 6:
                                            miga.append("<li class=\"active\">");
                                            miga.append("<a>");
                                            miga.append("Datafonos");
                                            miga.append("</a>");
                                            miga.append("</li>");
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                break;
                            case 3:
                                miga.append("<li class=\"active\">");
                                miga.append("<a>");
                                miga.append("Cruces");
                                miga.append("</a>");
                                miga.append("</li>");
                                break;
                            case 4:
                                miga.append("<li class=\"active\">");
                                miga.append("<a>");
                                miga.append("Pendiente");
                                miga.append("</a>");
                                miga.append("</li>");
                                break;
                            default:
                                break;
                        }
                    }
                    break;
                case 2:
                    miga.append("<li class=\"active\">");
                    miga.append("<a>");
                    miga.append("Ítems");
                    miga.append("</a>");
                    miga.append("</li>");
                    miga.append("<li class=\"active\">");
                    miga.append("<a>");
                    miga.append(posicionItem + 1);
                    miga.append(" de ");
                    miga.append(cotizacion.getDetalle().size());
                    miga.append("</a>");
                    miga.append("</li>");
                    break;
                case 3:
                    miga.append("<li class=\"active\">");
                    miga.append("<a>");
                    miga.append("Comisiones");
                    miga.append("</a>");
                    miga.append("</li>");
                    break;
                case 4:
                    miga.append("<li class=\"active\">");
                    miga.append("<a>");
                    miga.append("Crear factura");
                    miga.append("</a>");
                    miga.append("</li>");
                    break;
                default:
                    break;
            }
        }
        miga.append("</ol>");

        return miga.toString();
    }

    public BigDecimal getTotalVenta() {
        if (saldosAcumulados != null && !saldosAcumulados.isEmpty()) {
            BigDecimal total = new BigDecimal(0);
            for (SaldoItemDTO s : saldosAcumulados) {
                if (s.getDescuento() != null && s.getDescuento() > 0) {
                    total = total.add(new BigDecimal(baruGenericMBean.obtenerPrecioVentaDescuento(s.getDescuento(), s.getReferencia()) * s.getCantidadNecesaria()));
                } else {
                    total = total.add(new BigDecimal(baruGenericMBean.obtenerPrecioVenta(s.getReferencia()) * s.getCantidadNecesaria()));
                }
            }
            return total;
        } else {
            return new BigDecimal(0);
        }
    }

    public String getCondicionPagoSeleccionado() {
        if (condicionPago != null && condicionPago != 0) {
            for (CondicionPagoDTO c : condicionesPago) {
                if (c.getGroupNum().equals(condicionPago)) {
                    return c.getPymntGroup();
                }
            }
        }
        return "Seleccione";
    }

    public String getEstadoSeleccionado() {
        if (detalleVenta != null && detalleVenta.getIdDetalleCotizacion() != null && detalleVenta.getIdDetalleCotizacion() != 0 && detalleVenta.getEstado() != null) {
            switch (detalleVenta.getEstado()) {
                case "P":
                    return "Pendiente entrega 1 a 10 días";
                case "D":
                    return "Despachado - Entrega inmediata";
                case "G":
                    return "Enviar a DAKA";
                default:
                    break;
            }
        }

        return "Seleccione";
    }

    public String getDecoradorSeleccionado() {
        if (decorador != null && !decorador.isEmpty()) {
            for (BaruDecoradoresDTO b : decoradores) {
                if (b.getName().equals(decorador)) {
                    return b.getName();
                }
            }
        }
        return "Seleccione";
    }

    public String getFacturaAsociadaSeleccionada() {
        if (facturaAsociada != null && facturaAsociada != 0) {
            return facturaAsociada.toString();
        }
        return "Seleccione";
    }

    private Long getObtenerDescuento(String ref) {
        ProgramacionDescuento pr = programacionDescuentoFacade.consultarDescuentosReferencia("TS", ref);

        if (pr != null && pr.getIdReglaDescuento() != null && pr.getIdReglaDescuento() != 0) {
            return pr.getPorcentaje().longValue();
        }
        return 0L;
    }

    private void obtenerVentaPendiente() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario {0} tiene cotizaciones pendientes", userSessionInfoMBean.getUsuario());
        List<CotizacionWeb> cotizaciones = cotizacionWebFacade.obtenerCotizacionesPendientes(Integer.parseInt(userSessionInfoMBean.getCodigoVentas()));
        if (cotizaciones != null && !cotizaciones.isEmpty()) {
            log.log(Level.INFO, "Se encontraron {0} cotizaciones pendiente para el usuario {1}", new Object[]{cotizaciones.size(), userSessionInfoMBean.getUsuario()});

            if (cotizaciones != null && !cotizaciones.isEmpty()) {
                if (cotizaciones.size() > 1) {
                    for (int i = 0; i < cotizaciones.size() - 1; i++) {
                        CotizacionWeb cot = cotizaciones.get(i);

                        cot.setEstado("NF");

                        try {
                            cotizacionWebFacade.edit(cot);
                            log.log(Level.INFO, "Se marco la cotizacion con id {0} con el estado NO FINALIZADO", cot.getIdCotizacion());
                            cotizaciones.remove(i);
                            i--;
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al marcar la cotizacion con id {0} como NO FINALIZADA. Error {1}", new Object[]{cot.getIdCotizacion(), e.getMessage()});
                            return;
                        }
                    }
                }

                CotizacionWeb cot = cotizaciones.get(0);

                cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                cotizacion.setDemostracion(cot.getDemostracion() != null ? cot.getDemostracion() : false);

                saldosAcumulados = new ArrayList<>();
                List<DetalleCotizacionWeb> detalles = detalleCotizacionWebFacade.findByIdCotizacion(cotizacion.getIdCotizacion());

                if (detalles != null && !detalles.isEmpty()) {
                    for (DetalleCotizacionWeb d : detalles) {
                        cotizacion.getDetalle().add(new DetalleCotizacionWebDTO(d.getCantidad(), cotizacion.getIdCotizacion().intValue(), null,
                                d.getIdDetalleCotizacion(), d.getReferencia(), d.getBodega(), null, null));
                        saldosAcumulados.add(0, new SaldoItemDTO(d.getCantidad(), d.getCantidad(), getObtenerDescuento(d.getReferencia()), d.getReferencia(), d.getBodega()));
                    }
                }
            }
        } else {
            log.log(Level.INFO, "No se encontraron cotizaciones pendiente para el usuario {0}", userSessionInfoMBean.getUsuario());
        }
    }

    private void obtenerCotizacionSAP() {
        CotizacionWeb cotizacionWeb = cotizacionWebFacade.obtenerCotizacionesWeb(ventasSessionMBean.getNumeroCotizacion().intValue());

        if (cotizacionWeb != null && cotizacionWeb.getIdCotizacion() != null && cotizacionWeb.getIdCotizacion() != 0) {
            cotizacionWeb.setEstado("MC");

            try {
                cotizacionWebFacade.edit(cotizacionWeb);
                log.log(Level.INFO, "Actualizando cotizacion web de la cotizacion numero {0}", cotizacionWeb.getNumeroDocSAP());

                cotizacion = new CotizacionWebDTO(cotizacionWeb.getIdVendedor(), cotizacionWeb.getIdCotizacion(), cotizacionWeb.getSucursal(),
                        cotizacionWeb.getEstado(), cotizacionWeb.getNitCliente(), cotizacionWeb.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                cotizacion.setDemostracion(cotizacionWeb.getDemostracion() != null ? cotizacionWeb.getDemostracion() : false);
            } catch (Exception e) {
                mostrarMensaje("Error", "Se detectó un error al actualizar la cotización web.", true, false, false);
                log.log(Level.SEVERE, "Se detecto un error al actualizar la cotizacion web", e);
                return;
            }
        } else {
            CotizacionSAP cotSAP = cotizacionSAPFacade.find(ventasSessionMBean.getNumeroCotizacion().intValue());

            if (cotSAP != null && cotSAP.getDocEntry() != null && cotSAP.getDocEntry() != 0) {
                cotizacionWeb = new CotizacionWeb();

                cotizacionWeb.setEstado("MC");
                cotizacionWeb.setFecha(cotSAP.getDocDate());
                cotizacionWeb.setIdVendedor(cotSAP.getSlpCode());
                cotizacionWeb.setNitCliente(cotSAP.getCardCode());
                cotizacionWeb.setNumeroDocSAP(String.valueOf(cotSAP.getDocNum()));
                cotizacionWeb.setUsuario(userSessionInfoMBean.getUsuario());

                try {
                    cotizacionWebFacade.create(cotizacionWeb);
                    log.log(Level.INFO, "Generando cotizacion web a la cotizacion numero {0}", cotizacionWeb.getNumeroDocSAP());

                    cotizacion = new CotizacionWebDTO(cotizacionWeb.getIdVendedor(), cotizacionWeb.getIdCotizacion(), cotizacionWeb.getSucursal(),
                            cotizacionWeb.getEstado(), cotizacionWeb.getNitCliente(), cotizacionWeb.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                } catch (Exception e) {
                    mostrarMensaje("Error", "Se detectó un error al obtener los datos de la cotización a modificar.", true, false, false);
                    log.log(Level.SEVERE, "Se detecto un error al obtener los datos de la cotizacion a modificar", e);
                    return;
                }
            }
        }

        /*Se elimina el detalle actual de la cotizacion web*/
        try {
            detalleCotizacionWebFacade.eliminarDetalleCotizacion(cotizacionWeb.getIdCotizacion());
            log.log(Level.INFO, "Se eliminaron los datos encontrados en la tabla de DETALLE_COTIZACION_WEB, para la cotizacion con id [{0}]", cotizacionWeb.getIdCotizacion());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al eliminar los datos del detalle de cotizacion", e);
            mostrarMensaje("Error", "Ocurrió un error al eliminar los registros actuales de la cotización.", true, false, false);
            return;
        }

        List<DetalleCotizacionSAP> detalleCot = detalleCotizacionSAPFacade.obtenerDetalleCotizacion(Long.parseLong(cotizacionWeb.getNumeroDocSAP()));

        if (detalleCot != null && !detalleCot.isEmpty()) {
            for (DetalleCotizacionSAP d : detalleCot) {
                if (d.getLineStatus().toString().equals("C")) {
//                    lineasCerradas = true;
                }
                DetalleCotizacionWeb detWeb = new DetalleCotizacionWeb();

                detWeb.setBodega(d.getWhsCode());
                detWeb.setCantidad(d.getQuantity().intValue());
                detWeb.setIdCotizacion(cotizacionWeb);
                detWeb.setReferencia(d.getItemCode());

                try {
                    detalleCotizacionWebFacade.create(detWeb);
                    log.log(Level.INFO, "Se agrego detalle con id [{0}] a la cotizacion con id [{1}]", new Object[]{detWeb.getIdDetalleCotizacion(), cotizacionWeb.getIdCotizacion()});

                    cotizacion.getDetalle().add(new DetalleCotizacionWebDTO(detWeb.getCantidad(), cotizacion.getIdCotizacion().intValue(), d.getDetalleCotizacionSAPPK().getLineNum(),
                            detWeb.getIdDetalleCotizacion(), detWeb.getReferencia(), detWeb.getBodega(), d.getTaxCode(), d.getLineStatus().toString()));
                    saldosAcumulados.add(0, new SaldoItemDTO(d.getQuantity().intValue(), d.getQuantity().intValue(), getObtenerDescuento(d.getItemCode()), d.getItemCode(), d.getWhsCode()));
                } catch (Exception e) {
                    log.log(Level.SEVERE, "", e);
                    mostrarMensaje("Error", "No se pudo continuar con la obtención de los datos de la cotización.", true, false, false);
                    return;
                }
            }
        }
    }

    public void obtenerSaldos() {
        descuento = 0L;
        if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
            if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.DEMOSTRACION, Acciones.MODIFICAR) && ventasSessionMBean.isModificando()
                    && (ventasSessionMBean.getNumeroCotizacion() == null || ventasSessionMBean.getNumeroCotizacion() == 0)) {
                mostrarMensaje("Error", "Usted no tiene permisos para modificar demostraciones.", true, false, false);
                log.log(Level.SEVERE, "Usted no tiene permiso para modificar demostraciones");
                return;
            }
        }

        saldos = new ArrayList<>();
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar la referencia que quiere agregar.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar la referencia que quiere agregar");
            return;
        }

        referencia = baruGenericMBean.completarReferencia(referencia);

        if (referencia.length() < 20 || referencia.length() > 20) {
            mostrarMensaje("Error", "La referencia ingresada no es valida.", true, false, false);
            log.log(Level.SEVERE, "La referencia ingresada no es valida");
            return;
        }

        /*Se obtienen los datos de la referencia, incluyendo los saldos*/
        List<SaldoItemInventario> balances = saldoItemInventarioFacade.obtenerSaldo(referencia);

        if (balances != null && !balances.isEmpty()) {
            for (SaldoItemInventario s : balances) {
                if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
                    if (s.getSaldoItemInventarioPK().getWhsCode().getWhsCode().substring(0, 1).contains("0") || s.getSaldoItemInventarioPK().getWhsCode().getWhsCode().equals(almacen)) {
                        saldos.add(new SaldoItemDTO(s.getOnHand().intValue(), 0, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode()));
                    }
                } else if (s.getSaldoItemInventarioPK().getWhsCode().getWhsCode().substring(0, 1).contains("0")) {
                    saldos.add(new SaldoItemDTO(s.getOnHand().intValue(), 0, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode()));
                }
            }

            porcentajeDescuento = getObtenerDescuento(referencia);
            if (porcentajeDescuento != null && porcentajeDescuento != 0) {
                descuento = baruGenericMBean.obtenerPrecioVentaDescuento(porcentajeDescuento, referencia);
            }
            obtenerSaldoUsado();
            obtenerGaleria();
            dlgSaldos = true;
        } else {
            mostrarMensaje("Error", "No se encontró saldo de la referencia en los almacenes.", true, false, false);
            log.log(Level.SEVERE, "No se encontro saldo de la referencia en los almacenes");
            dlgSaldos = false;
            return;
        }
    }

    private void obtenerSaldoUsado() {
        if (saldosAcumulados != null && !saldosAcumulados.isEmpty()) {
            for (SaldoItemDTO s : saldos) {
                for (SaldoItemDTO si : saldosAcumulados) {
                    if (si.getReferencia().equals(s.getReferencia()) && si.getAlmacen().equals(s.getAlmacen())) {
                        s.setCantidadNecesaria(si.getCantidadNecesaria());
                        break;
                    }
                }
            }
        }
    }

    private void obtenerGaleria() {
        log.log(Level.INFO, "Se estan obteniendo el catalogo de imagenes de la referencia {0} para la venta", referencia);

        /*1. Se obtiene la galeria de imagenes*/
        imagenes = new ArrayList<>();
        int contador = 0;
        List<String> galeria = imagenProductoMBean.obtenerImagenesCatalogo(referencia);
        if (galeria != null && !galeria.isEmpty()) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.imagen");
            if (url != null) {
                url = String.format(url, referencia);
            }

            for (String s : galeria) {
                imagenes.add(new String[]{url + s, String.valueOf(contador)});
                contador++;
            }
        }

        /*2. Se obtiene la plantilla de la referencia*/
        imagenes.add(new String[]{imagenProductoMBean.obtenerPlantilla(referencia), String.valueOf(contador)});
    }

    public void asignarSaldo() {
        StringBuilder sb = new StringBuilder();
        if (saldosAcumulados == null) {
            saldosAcumulados = new ArrayList<>();
        }
        for (SaldoItemDTO s : saldos) {
            Long discount = getObtenerDescuento(s.getReferencia());
            boolean existe = false;
            for (SaldoItemDTO si : saldosAcumulados) {
                if (si.getReferencia().equals(s.getReferencia()) && si.getAlmacen().equals(s.getAlmacen()) && s.getCantidadNecesaria() > 0) {
                    si.setCantidadNecesaria(s.getCantidadNecesaria());
                    existe = true;
                    break;
                } else if (si.getReferencia().equals(s.getReferencia()) && si.getAlmacen().equals(s.getAlmacen())) {
                    saldosAcumulados.remove(si);
                    existe = true;
                    break;
                } else if (s.getCantidadNecesaria() > 0) {
                    existe = false;
                } else if (s.getCantidadNecesaria() < 0) {
                    existe = false;
                }
            }

            if (discount != null && discount > 0) {
                s.setDescuento(discount);
            } else {
                s.setDescuento(0L);
            }

            if (!existe && s.getCantidadNecesaria() > 0) {
                saldosAcumulados.add(0, s);
            } else if (!existe && s.getCantidadNecesaria() < 0) {
                sb.append("La referencia: ");
                sb.append(s.getReferencia());
                sb.append(" del almacen ");
                sb.append(s.getAlmacen());
                sb.append(", no se puede agregar porque la cantidad necesaria es menor a 0. ");
            }
        }

        aplicarDatosBD();
        limpiarDatosSaldo();
        if (!sb.toString().isEmpty()) {
            mostrarMensaje("Error", sb.toString(), true, false, false);
            log.log(Level.SEVERE, sb.toString());
        }
    }

    public void seleccionarItemEliminar() {
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        almacen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacen");

        log.log(Level.INFO, "Se selecciono la referencia {0} del almacen {1}, para eliminar del proceso de venta", new Object[]{referencia, almacen});
        obtenerSaldos();
    }

    public void cancelarItemEliminar() {
        ventaCorrompida = false;
        dlgConfirmarEliminacion = false;
        limpiarDatosSaldo();
    }

    public void eliminarItem() {
        if (dlgConfirmarEliminacion && !ventaCorrompida) {
            ventaCorrompida = true;
            dlgConfirmarEliminacion = false;
        }

        if (ventasSessionMBean.getNumeroCotizacion() != null && ventasSessionMBean.getNumeroCotizacion() != 0 && !ventaCorrompida) {
            log.log(Level.SEVERE, "Pidiendo confirmacion al usuario de que quiere dejar de modificar la cotizacion");
            dlgConfirmarEliminacion = true;
            return;
        } else if (ventasSessionMBean.getNumeroCotizacion() != null && ventasSessionMBean.getNumeroCotizacion() != 0 && ventaCorrompida) {
            ventasSessionMBean.setCotizacionCorrompida(true);
        }
        if (saldosAcumulados != null && !saldosAcumulados.isEmpty()) {
            for (int i = 0; i < saldosAcumulados.size(); i++) {
                SaldoItemDTO s = saldosAcumulados.get(i);
                if (s.getReferencia().equals(referencia) && s.getAlmacen().equals(almacen) && s.getCantidadNecesaria() > 0) {
                    saldosAcumulados.remove(i);
                    break;
                }
            }
        }

        for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
            if (d.getReferencia().equals(referencia) && d.getBodega().equals(almacen)) {
                DetalleCotizacionWeb det = detalleCotizacionWebFacade.find(d.getIdDetalleCotizacion());

                if (det != null && det.getIdDetalleCotizacion() != null && det.getIdDetalleCotizacion() != 0) {
                    try {
                        detalleCotizacionWebFacade.remove(det);
                        log.log(Level.INFO, "Se elimino la referencia [{0}], del almacen [{1}], con id de detalle [{2}]", new Object[]{referencia, almacen, det.getIdDetalleCotizacion()});
                        cotizacion.getDetalle().remove(d);
                        break;
                    } catch (Exception e) {
                        mostrarMensaje("Error", "No se pudo eliminar la referencia solicitada.", true, false, false);
                        log.log(Level.SEVERE, "Ocurrio un error al eliminar la referencia", e);
                        return;
                    }
                }
            }
        }
        limpiarDatosSaldo();
    }

    private void aplicarDatosBD() {
        if (cotizacion == null || cotizacion.getIdCotizacion() == null || cotizacion.getIdCotizacion() == 0) {
            CotizacionWeb cot = new CotizacionWeb();

            cot.setEstado("CP");
            cot.setSucursal(userSessionInfoMBean.getAlmacen());
            cot.setUsuario(userSessionInfoMBean.getUsuario());
            cot.setIdVendedor(Integer.parseInt(userSessionInfoMBean.getCodigoVentas()));
            cot.setFecha(new Date());
            cot.setNitCliente(null);

            try {
                cotizacionWebFacade.create(cot);
                log.log(Level.INFO, "Se creo la cotizacion con id {0}", cot.getIdCotizacion());
                cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al crear cotizacion", e);
                mostrarMensaje("Error", "Ocurrió un error al registrar los datos de la venta.", true, false, false);
                return;
            }
        }

        asignarDatosDTO();
    }

    private void asignarDatosDTO() {
        /*Se llenan los datos del detalle*/
        if (cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
            for (SaldoItemDTO s : saldosAcumulados) {
                boolean existe = false;
                for (int i = 0; i < cotizacion.getDetalle().size(); i++) {
                    DetalleCotizacionWebDTO det = cotizacion.getDetalle().get(i);

                    if (s.getReferencia().equals(det.getReferencia()) && s.getAlmacen().equals(det.getBodega())) {
                        if (s.getCantidadNecesaria() != det.getCantidad() && s.getCantidadNecesaria() > 0) {
                            DetalleCotizacionWeb detalle = detalleCotizacionWebFacade.find(det.getIdDetalleCotizacion());

                            if (detalle != null && detalle.getIdDetalleCotizacion() != null && detalle.getIdDetalleCotizacion() != 0) {
                                detalle.setCantidad(s.getCantidadNecesaria());
                                det.setCantidad(s.getCantidadNecesaria());

                                try {
                                    detalleCotizacionWebFacade.edit(detalle);
                                    log.log(Level.INFO, "Se modifico el detalle de id {0} para la cotizacion con id {1}", new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion()});
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Ocurrio un error al modificar el detalle de id {0} para la cotizacion con id {1}. Error {2}",
                                            new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion(), e.getMessage()});
                                    return;
                                }
                            }
                        } else if ((s.getCantidadNecesaria() == null || s.getCantidadNecesaria() == 0)) {
                            DetalleCotizacionWeb detalle = detalleCotizacionWebFacade.find(det.getIdDetalleCotizacion());

                            if (detalle != null && detalle.getIdDetalleCotizacion() != null && detalle.getIdDetalleCotizacion() != 0) {
                                detalle.setCantidad(s.getCantidadNecesaria());
                                det.setCantidad(s.getCantidadNecesaria());

                                try {
                                    detalleCotizacionWebFacade.remove(detalle);
                                    log.log(Level.INFO, "Se elimino el detalle de id [{0}] para la cotizacion con id [{1}]", new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion()});
                                    cotizacion.getDetalle().remove(i);
                                    i--;
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Ocurrio un error al eliminar el detalle de id [{0}] para la cotizacion con id [{1}]. Error [{2}]",
                                            new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion(), e.getMessage()});
                                    return;
                                }
                            }
                        }
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    crearDetalleCotizacion(s);
                }
            }
        } else {
            for (SaldoItemDTO s : saldosAcumulados) {
                if (s.getCantidadNecesaria() != null && s.getCantidadNecesaria() > 0) {
                    crearDetalleCotizacion(s);
                }
            }
        }
    }

    public void crearDetalleCotizacion(SaldoItemDTO s) {
        DetalleCotizacionWeb detalle = new DetalleCotizacionWeb();

        detalle.setBodega(s.getAlmacen());
        detalle.setCantidad(s.getCantidadNecesaria());
        detalle.setReferencia(s.getReferencia());
        detalle.setIdCotizacion(new CotizacionWeb(cotizacion.getIdCotizacion()));

        try {
            detalleCotizacionWebFacade.create(detalle);
            log.log(Level.INFO, "Se creo detalle con el id {0}", detalle.getIdDetalleCotizacion());
            cotizacion.getDetalle().add(new DetalleCotizacionWebDTO(detalle.getCantidad(), cotizacion.getIdCotizacion().intValue(), null, detalle.getIdDetalleCotizacion(),
                    detalle.getReferencia(), detalle.getBodega()));
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    public String crearCotizacion() {
        if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
            modificaDemostracion();
        } else if (ventasSessionMBean.isModificando()) {
            try {
                modificarCotizacion();
                limpiar();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al mdodificar la cotización.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al modificar la cotizacion. Error {0}", e.getMessage());
                return null;
            }
        } else if (cotizacion != null && cotizacion.getIdCotizacion() != null && cotizacion.getIdCotizacion() != 0) {
            ventasSessionMBean.setCotizacion(cotizacion);
            return "clientes";
        }
        return null;
    }

    public void cancelarVenta() {
        if (ventasSessionMBean.isModificando()) {
            limpiar();
        } else {
            CotizacionWeb cot = cotizacionWebFacade.find(cotizacion.getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
                cot.setEstado("CC");

                try {
                    cotizacionWebFacade.edit(cot);
                    log.log(Level.INFO, "Se marco la cotizaicon con id {0}, con el estado CC -> COTIZACION CANCELADA", cotizacion.getIdCotizacion());
                    mostrarMensaje("Éxito", "El proceso de creación de la cotización fue cancelado correctamente.", false, true, false);
                    limpiar();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al cancelar la cotizacion con id {0}. Error {1}", new Object[]{cotizacion.getIdCotizacion(), e.getMessage()});
                    mostrarMensaje("Error", "No se pudo cancelar el proceso de creación de la cotización.", true, false, false);
                    return;
                }
            }
        }
    }

    public void seleccionarImprimir() {
        if (imprimir) {
            imprimir = false;
            log.log(Level.INFO, "No se imprimira la cotizacion {0}", ventasSessionMBean.getNumeroCotizacion());
        } else {
            imprimir = true;
            log.log(Level.INFO, "Se imprimira la cotizacion {0}", ventasSessionMBean.getNumeroCotizacion());
        }
    }

    public void seleccionarEnivar() {
        if (enviar) {
            enviar = false;
            log.log(Level.INFO, "No se mandara la cotizacion {0} por correo electronico", ventasSessionMBean.getNumeroCotizacion());
        } else {
            enviar = true;
            log.log(Level.INFO, "Se mandara la cotizacion {0} por correo electronico", ventasSessionMBean.getNumeroCotizacion());
        }
    }

    private void modificarCotizacion() throws DatatypeConfigurationException {
        CotizacionSAP cotSAP = cotizacionSAPFacade.find(ventasSessionMBean.getNumeroCotizacion().intValue());

        if (cotSAP != null && cotSAP.getDocEntry() != null && cotSAP.getDocEntry() != 0) {
            GregorianCalendar docDate = new GregorianCalendar();
            GregorianCalendar docDueDate = new GregorianCalendar();
            QuotationsDocumentDTO quotation = new QuotationsDocumentDTO();

            docDate.setTime(cotSAP.getDocDate());
            docDueDate.setTime(cotSAP.getDocDueDate());

            quotation.setDocDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(docDate));
            quotation.setDocDueDate(DatatypeFactory.newInstance().newXMLGregorianCalendar(docDueDate));
            quotation.setDocEntry(cotSAP.getDocEntry().longValue());
            quotation.setCardCode(cotSAP.getCardCode());
            quotation.setCardName(cotSAP.getCardName());
            quotation.setAddress(cotSAP.getAddress());
            quotation.setPaymentGroupCode(cotSAP.getGroupNum().longValue());
            quotation.setSalesPersonCode(cotSAP.getSlpCode().longValue());
            quotation.setContactPersonCode(cotSAP.getCntctCode() != null && cotSAP.getCntctCode() != 0 ? cotSAP.getCntctCode().longValue() : null);
            quotation.setShipToCode(cotSAP.getShipToCode());
            quotation.setDocumentsOwner(cotSAP.getOwnerCode() != null && cotSAP.getOwnerCode() != 0 ? cotSAP.getOwnerCode().longValue() : null);
            quotation.setAddress2(cotSAP.getAddress2());
            quotation.setPayToCode(cotSAP.getPayToCode());
            quotation.setExtraMonth(cotSAP.getExtraMonth().longValue());
            quotation.setExtraDays(cotSAP.getExtraDays().longValue());
            quotation.setQuotationsDocumentLines(new ArrayList<QuotationsDocumentLinesDTO>());

            if (cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
                for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                    QuotationsDocumentLinesDTO line = new QuotationsDocumentLinesDTO();

                    line.setItemCode(d.getReferencia());
                    line.setLinea(d.getLinea() != null && d.getLinea() >= 0 ? d.getLinea() : -1);
                    line.setWarehouseCode(d.getBodega());
                    line.setQuantity(d.getCantidad());
                    line.setTaxCode(d.getTaxCode());

                    quotation.getQuotationsDocumentLines().add(line);
                }
            } else {
                log.log(Level.SEVERE, "No se puede continuar debido a que no se encontraton items en la cotizacion");
                mostrarMensaje("Error", "No se puede continuar debido a que no se encontraron ítems en la cotización.", true, false, false);
                return;
            }

            /*Se obtienen los datos de la tabla QUT2 que equivalen a TaxExtension y AddressExtension*/
            TaxExtension tax = taxExtensionFacade.obtenerTaxExtensionCotizacion(cotSAP.getDocEntry());

            if (tax != null && tax.getDocEntry() != null && tax.getDocEntry() != 0) {
                TaxExtensionDTO taxExtension = new TaxExtensionDTO();

                taxExtension.setStreetS(tax.getStreetS());
                taxExtension.setBlockS(tax.getBlockS());
                taxExtension.setBuildingS(tax.getBuildingS());
                taxExtension.setCityS(tax.getCityS());
                taxExtension.setCountyS(tax.getCountyS());
                taxExtension.setStateS(tax.getStateS());
                taxExtension.setCountryS(tax.getCountryS());
                taxExtension.setStreetB(tax.getStreetB());
                taxExtension.setBlockB(tax.getBlockB());
                taxExtension.setBuildingB(tax.getBuildingB());
                taxExtension.setCityB(tax.getCityB());
                taxExtension.setCountyB(tax.getCountyB());
                taxExtension.setStateB(tax.getStateB());
                taxExtension.setCountryB(tax.getCountryB());

                quotation.setTaxExtension(taxExtension);

                AddressExtensionDTO addressExtension = new AddressExtensionDTO();

                addressExtension.setShipToStreet(tax.getStreetS());
                addressExtension.setShipToBlock(tax.getBlockS());
                addressExtension.setShipToBuilding(tax.getBuildingS());
                addressExtension.setShipToCity(tax.getCityS());
                addressExtension.setShipToCounty(tax.getCountyS());
                addressExtension.setShipToState(tax.getStateS());
                addressExtension.setShipToCountry(tax.getCountryS());
                addressExtension.setBillToStreet(tax.getStreetB());
                addressExtension.setBillToBlock(tax.getBlockB());
                addressExtension.setBillToBuilding(tax.getBuildingB());
                addressExtension.setBillToCity(tax.getCityB());
                addressExtension.setBillToCounty(tax.getCountyB());
                addressExtension.setBillToState(tax.getStateB());
                addressExtension.setBillToCountry(tax.getCountryB());

                quotation.setAddressExtension(addressExtension);
            } else {
                mostrarMensaje("Error", "No se encontraron datos necesarios para poder actualizar la cotización.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos necesarios para poder actualizar la cotizacion");
                return;
            }

            try {
                QuotationsClient client = new QuotationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                GenericRESTResponseDTO res = client.editQuotation(quotation);
                if (res.getValor() != null && res.getValor() != 0) {
                    log.log(Level.INFO, "Se modifico la cotizacion correctamente");
                    mostrarMensaje("Éxito", "Se modifico la cotización correctamente.", false, true, false);
                } else {
                    mostrarMensaje("Error", "Ocurrió un error al modificar la cotización. " + res.getMensaje(), true, false, false);
                    log.log(Level.SEVERE, "Ocurrio un error al modificar la cotizacion. Error {0}", res.getMensaje());
                    return;
                }
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al modificar la cotización. " + e.getMessage(), true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al modificar la cotizacion. Error {0}", e.getMessage());
                return;
            }
        }
    }

    public String cancelarEdicionCotizacion() {
        if (ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
            limpiar();
            return "demostraciones";
        } else {
            limpiar();
            return "cotizaciones";
        }
    }

    public void actualizarDatosCotizacionWeb() {
        String[] s = null;

        if (ventasSessionMBean.isExitoCotizacion()) {
            CotizacionWeb cot = cotizacionWebFacade.find(ventasSessionMBean.getCotizacion().getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
                cot.setEstado("CT");
                cot.setNumeroDocSAP(String.valueOf(ventasSessionMBean.getNumeroCotizacion()));
                cot.setNitCliente(ventasSessionMBean.getCotizacion().getNitCliente());
                cot.setImprimir(imprimir);
                cot.setEnviarEmail(enviar ? "1" : "0");

                try {
                    cotizacionWebFacade.edit(cot);
                    log.log(Level.INFO, "Se marco la cotizacion con el estado CT, a la cotizacion con id [{0}]", ventasSessionMBean.getCotizacion().getIdCotizacion());

                    s = generarDocumento(ventasSessionMBean.getNumeroCotizacion().intValue(), 1, ventasSessionMBean.getNumeroCotizacion().toString(), "cotizacion",
                            userSessionInfoMBean.getAlmacen(), null, imprimir, null);

                    if (enviar) {
                        cotizacion.setNitCliente(ventasSessionMBean.getCotizacion().getNitCliente());
                        notificarProceso(null, "cotizacion", "", true, s);
                    }
                    ventasSessionMBean.setCotizacion(new CotizacionWebDTO());
                    ventasSessionMBean.setExitoCotizacion(false);
                    ventasSessionMBean.setNumeroCotizacion(0L);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al actualizar el estado de la cotizacion con id [{0}]. Error [{1}].",
                            new Object[]{ventasSessionMBean.getCotizacion().getIdCotizacion(), e.getMessage()});
                }
            }
        }

        if (ventasSessionMBean.isExitoDemostracion()) {
            s = generarDocumento(idDemostracion, 1, "[" + idDemostracion + "] " + nombreDemostracion.toUpperCase(), "demostracion", userSessionInfoMBean.getAlmacen(), nombreDemostracion.toUpperCase(), imprimir, null);

            if (enviar) {
                cotizacion.setNitCliente(ventasSessionMBean.getDocumentoCliente());
                notificarProceso(null, "demostracion", "", true, s);
            }
        }

        limpiar();
    }

    /**
     * ******EMPIEZA BLOQUE DE FACTURAS******
     */
    public String crearFactura() {
        BaruSeriesAlmacen serie = baruSeriesAlmacenFacade.obtenerSerieFacturaAlmacen(userSessionInfoMBean.getAlmacen());

        if (serie == null || serie.getCode() == null || serie.getCode().isEmpty()) {
            mostrarMensaje("Error", "La sucursal donde inicio sesión no posee serie de numeración por lo que no está permitido factura desde esta.", true, false, false);
            log.log(Level.SEVERE, "La sucursal donde inicio sesion no posee serie de numeracion por lo que no esta permitido factura desde esta");
            return null;
        }

        if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0 && !dlgFacturarDemo) {
            facturarDemostracion();
            return null;
        } else {
            dlgFacturarDemo = false;
            ventasSessionMBean.setIdDemostracion(null);
        }

        StringBuilder sb = new StringBuilder();
        for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
            SaldoItemInventario saldoItem = saldoItemInventarioFacade.buscarReferenciaSaldoBodega(d.getReferencia(), d.getBodega());

            if (saldoItem != null && saldoItem.getSaldoItemInventarioPK() != null && saldoItem.getSaldoItemInventarioPK().getItemCode() != null && !saldoItem.getSaldoItemInventarioPK().getItemCode().isEmpty()) {
                if (saldoItem.getOnHand().intValue() < d.getCantidad()) {
                    sb.append(" referencia: ");
                    sb.append(baruGenericMBean.convertirARefCorta(d.getReferencia()));
                    sb.append(", almacén: ");
                    sb.append(d.getBodega());
                }
            } else {
                sb.append(" referencia: ");
                sb.append(baruGenericMBean.convertirARefCorta(d.getReferencia()));
                sb.append(", almacén: ");
                sb.append(d.getBodega());
            }
        }

        if (!sb.toString().isEmpty()) {
            mostrarMensaje("Error", "No se encontro saldo de los siguientes ítems:" + sb.toString(), true, false, false);
            log.log(Level.SEVERE, "No se encontro saldo de los siguientes items{0}", sb.toString());
            return null;
        }

        ventasSessionMBean.setFacturando(true);
        ventasSessionMBean.setCotizacion(cotizacion);
        ventasSessionMBean.setCotizacionCorrompida(ventaCorrompida);

        if (!ventasSessionMBean.isClienteVerificado()) {
            if (cotizacion.getNitCliente() != null && !cotizacion.getNitCliente().isEmpty()) {
                clienteSessionMBean.setClienteDto(new ClienteSAPDTO(cotizacion.getNitCliente(), null));
            }
            return "clientes";
        }

        autorizaciones = new ArrayList<>();
        consultarEstadoAprobacion();
        return null;
    }

    public void obtenerSiguientePasoFacturacion() {
        if (null != pasosFacturacion) {
            switch (pasosFacturacion) {
                case 1:
                    if (valorPendienteFactura.compareTo(new BigDecimal(0)) > 0) {
                        mostrarMensaje("Error", "No puede continuar, debido a que no se han ingresado los pagos que cubran el valor total de la factura.", true, false, false);
                        log.log(Level.SEVERE, "No puede continuar, debido a que no se han ingresado los pagos que cubran el valor total de la factura");
                        return;
                    }

                    posicionItem = 0;
                    obtenerItemPosicion();
                    pasosFacturacion++;
                    break;
                case 2:
                    if (detalleVenta.getCantidad() != detalleVenta.getCantidadUsada()) {
                        mostrarMensaje("Error", "Debe seleccionar las ubicaciones de donde se extraerá la mercancía.", true, false, false);
                        log.log(Level.SEVERE, "Debe seleccionar las ubicaciones de donde se extraera la mercancia");
                        return;
                    }
                    if (detalleVenta.getEstado() == null || detalleVenta.getEstado().isEmpty() || detalleVenta.getEstado().equals("O")) {
                        mostrarMensaje("Error", "Seleccione el estado de la referencia.", true, false, false);
                        log.log(Level.SEVERE, "Seleccione el estado de la referencia");
                        return;
                    }
                    if (cotizacion.getDetalle().size() - 1 > posicionItem) {
                        posicionItem++;
                        obtenerItemPosicion();
                    } else {
                        posicionItem = 0;
                        referencia = null;
                        imagenes = new ArrayList<>();

                        log.log(Level.INFO, "No se encontraron mas referencias para validar, se sigue al siguiente paso");
                        pasosFacturacion++;
                        obtenerAsesoresVenta();
                    }
                    break;
                case 3:
                    if (asesores == 0) {
                        mostrarMensaje("Error", "Debe seleccionar al menos un asesor que comisionara.", true, false, false);
                        log.log(Level.SEVERE, "Debe seleccionar al menos un asesor que comisionara");
                        return;
                    }
                    pasosFacturacion++;
                    obtenerDecoradores();
                    break;
                default:
                    break;
            }
        }
    }

    public void obtenerSiguientePasoPago() {
        if (medioPago == 2) {
            if (null != pasosPago) {
                switch (pasosPago) {
                    case 2:
                        if (voucher == null || voucher.isEmpty()) {
                            mostrarMensaje("Error", "Ingrese el número del voucher.", true, false, false);
                            log.log(Level.SEVERE, "Ingrese el numero del voucher");
                            return;
                        }
                        if (digitos == null || digitos.isEmpty()) {
                            mostrarMensaje("Error", "Ingrese los últimos dígitos de la tarjeta.", true, false, false);
                            log.log(Level.SEVERE, "Ingrese los ultimos digitos de la tarjeta");
                            return;
                        }
                        if (valorTarjeta == null || valorTarjeta.equals(0)) {
                            mostrarMensaje("Error", "Ingrese el valor a pagar.", true, false, false);
                            log.log(Level.SEVERE, "Ingrese el valor a pagar");
                            return;
                        }
                        if (valorTarjeta.compareTo(valorPendienteFactura) > 0) {
                            mostrarMensaje("Error", "El valor que ingreso para el pago es mayor al que se debe.", true, false, false);
                            log.log(Level.SEVERE, "El valor que ingreso para el pago es mayor al que se debe");
                            return;
                        }

                        if (franquicias == null || franquicias.isEmpty()) {
                            obtenerFranquicias();
                        }
                        break;
                    case 3:
                        obtenerBancosFranquicia();
                        break;
                    default:
                        break;
                }
            }
        } else if (medioPago == 4) {
            if (valorPagoPendiente == null || valorPagoPendiente.equals(0)) {
                consultarEstadoAprobacion();
            }
            medioPago = 4;
            if (autorizaciones == null || autorizaciones.isEmpty()) {
                if (pasosPago == 1) {
                    obtenerAprobadores();
                    if (autorizadoresPagoFactura == null || autorizadoresPagoFactura.isEmpty()) {
                        mostrarMensaje("Error", "No se encontraron personas con autorización para aprobar la factura por este medio de pago.", true, false, false);
                        log.log(Level.SEVERE, "No se encontraron personas con autorizacion para aprobar la factura por este medio de pago");
                        return;
                    }
                } else if (pasosPago == 2) {
                    int aprobadoresSeleccionados = 0;
                    for (AutorizadoresPagoFacturaDTO a : autorizadoresPagoFactura) {
                        if (a.isSeleccionado()) {
                            aprobadoresSeleccionados++;
                        }
                    }

                    if (aprobadoresSeleccionados == 0) {
                        mostrarMensaje("Error", "Debe seleccionar al menos un aprobador.", true, false, false);
                        log.log(Level.SEVERE, "Debe seleccionar al menos un aprobador");
                        return;
                    }
                    obtenerCondicionesPago();
                }
            } else {
                return;
            }
        }
        pasosPago++;
    }

    public void seleccionarMedioPago() {
        medioPago = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("medioPago"));
        log.log(Level.INFO, "Se selecciono el medio de pago: {0}", medioPago == 1 ? "Efectivo" : medioPago == 2 ? "Tarjetas" : medioPago == 3 ? "Cruces" : "Pendiente");

        if (autorizaciones == null || autorizaciones.isEmpty()) {
            valorPagoPendiente = new BigDecimal(0);
        }
        if (null != medioPago) {
            switch (medioPago) {
                case 3:
                    obtenerSaldoFavorCliente();
                    break;
                case 4:
                    obtenerSiguientePasoPago();
                    if (autorizaciones == null || autorizaciones.isEmpty()) {
                        pasosPago = 1;
                    }
                    break;
                default:
                    pasosPago = 1;
                    break;
            }
        }
    }

    private void obtenerFranquicias() {
        File file = new File(applicationMBean.obtenerValorPropiedad("facturacion.url.local.franquicias"));

        if (file.exists()) {
            File[] archivos = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File path) {
                    return (!path.isHidden() && path.getPath().contains(".png"));
                }
            });
            for (File s : archivos) {
                franquicias.add(s.getName());
            }
        } else {
            mostrarMensaje("Error", "No se encontraron franquicias para los pagos con tarjetas.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron franquicias para los pagos con tarjetas");
            return;
        }
    }

    public void seleccionarFranquicia() {
        franquicia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("franquicia").replace(".png", "");
        log.log(Level.INFO, "Se selecciono la franquicia {0} para el pago", franquicia);

        obtenerSiguientePasoPago();
    }

    private void obtenerBancosFranquicia() {
        bancos = new ArrayList<>();

        List<BancoFacturacion> banks = bancoFacturacionFacade.obtenerBancosFranquicia(franquicia);

        if (banks != null && !banks.isEmpty()) {
            for (BancoFacturacion b : banks) {
                bancos.add(new BancoFacturacionDTO(b.getIdBancoFacturacion(), b.getNombreBanco()));
            }
        } else {
            mostrarMensaje("Error", "No se encontraron bancos para la franquicia seleccionada.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron bancos para la franquicia seleccionada");
            return;
        }
    }

    public void seleccionarBanco() {
        banco = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idBanco"));
        log.log(Level.INFO, "Se selecciono el banco con id {0}", banco);

        obtenerSiguientePasoPago();
    }

    public void seleccionarTarjeta() {
        tarjeta = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tarjeta");

        if (tarjeta == null || tarjeta.isEmpty()) {
            mostrarMensaje("Error", "No se ha seleccionado ningún tipo tarjeta.", true, false, false);
            log.log(Level.SEVERE, "No se ha seleccionado ningun tipo tarjeta");
            return;
        }
        log.log(Level.INFO, "Se selecciono la tarjeta {0}, para el pago de la factura", tarjeta);

        obtenerDatafonos();
    }

    private void obtenerDatafonos() {
        datafonos = new ArrayList<>();
        List<TarjetaCreditoSAP> cards = tarjetaCreditoSAPFacade.obtenerDatafonosDisponiblesTarjeta(franquicia, userSessionInfoMBean.getAlmacen());

        if (cards != null && !cards.isEmpty()) {
            if (cards.size() > 1) {
                for (TarjetaCreditoSAP t : cards) {
                    datafonos.add(new TarjetaCreditoDTO(t.getCreditCard(), t.getCardName(), t.getAcctCode(), null));
                }

                obtenerSiguientePasoPago();
            } else {
                datafono = cards.get(0).getCreditCard().toString();
                aplicarPago();
            }
        } else {
            mostrarMensaje("Error", "Seleccione uno de los tipos anteriores.", true, false, false);
            log.log(Level.SEVERE, "Seleccione uno de los tipos anteriores");
            pasosPago--;
            return;
        }
    }

    public void seleccionarDatafono() {
        datafono = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("datafono");
        log.log(Level.INFO, "Se selecciono el datafono {0} para el pago", datafono);

        aplicarPago();
    }

    private void obtenerSaldoFavorCliente() {
        saldoFavor = new BigDecimal(socioDeNegociosFacade.obtenerSaldoFavor(clienteSessionMBean.getClienteDto().getNit()));

        if (saldoFavor == null || saldoFavor.compareTo(new BigDecimal(0)) <= 0) {
            saldoFavor = new BigDecimal(0);
            log.log(Level.INFO, "No se encontro saldo a favor para el cliente con nit {0}", clienteSessionMBean.getClienteDto().getNit());
        }
    }

    public void seleccionarAprobador() {
        Integer codigoVentaAprobador = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoVenta"));

        for (AutorizadoresPagoFacturaDTO a : autorizadoresPagoFactura) {
            if (a.getCodigoVentas().equals(codigoVentaAprobador)) {
                if (a.isSeleccionado()) {
                    log.log(Level.INFO, "Se deselecciono el usuario con cedula #{0} para pedirle autorizacion por pago pendiente", a.getCedula());
                    a.setSeleccionado(false);
                } else {
                    log.log(Level.INFO, "Se selecciono el usuario con cedula #{0} para pedirle autorizacion por pago pendiente", a.getCedula());
                    a.setSeleccionado(true);
                }
            }
        }
    }

    public void seleccionarPagoTarjeta() {
        Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPago"));

        if (id != null && id.equals(pagoTarjeta)) {
            pagoTarjeta = null;
            log.log(Level.INFO, "Se deselecciono el pago con tarjeta con id {0}", id);
        } else {
            pagoTarjeta = id;
            log.log(Level.INFO, "Se selecciono el pago con tarjeta con id {0}", id);
        }
    }

    private void obtenerAprobadores() {
        autorizadoresPagoFactura = new ArrayList<>();
        String emails = applicationMBean.obtenerValorPropiedad("facturas.autorizacion.emails");
        if (emails != null && !emails.isEmpty()) {
            for (String email : emails.split(";")) {
                String valores[] = email.split(",");

                autorizadoresPagoFactura.add(new AutorizadoresPagoFacturaDTO(Integer.parseInt(valores[2]), valores[1], valores[0], false));
            }
        }
    }

    private void obtenerCondicionesPago() {
        condicionesPago = new ArrayList<>();

        List<CondicionPago> conditions = condicionPagoFacade.obtenerCondicionesPagoFactura();

        if (conditions != null && !conditions.isEmpty()) {
            for (CondicionPago c : conditions) {
                condicionesPago.add(new CondicionPagoDTO(c.getGroupNum(), c.getPymntGroup()));
            }
        }
    }

    public void asignarValorTotal() {
        if (valorPendienteFactura.compareTo(new BigDecimal(0)) > 0) {
            if (null != medioPago) {
                switch (medioPago) {
                    case 1:
                        valorEfectivo = valorPendienteFactura;
                        aplicarPago();
                        break;
                    case 2:
                        valorTarjeta = valorPendienteFactura;
                        break;
                    case 3:
                        if (saldoFavor.doubleValue() > 0 && saldoFavor.doubleValue() >= valorPendienteFactura.doubleValue()) {
                            valorCruce = valorPendienteFactura;
                            aplicarPago();
                            break;
                        }
                    case 4:
                        valorPagoPendiente = valorPendienteFactura;
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void aplicarPago() {
        BigDecimal valor = null;

        if (null != medioPago) {
            switch (medioPago) {
                case 1:
                    valor = valorEfectivo;
                    break;
                case 2:
                    valor = valorTarjeta;
                    pagosTarjetas.add(0, new PagoTarjetaDTO(banco, Integer.parseInt(datafono), System.currentTimeMillis(), valorTarjeta, voucher, digitos, franquicia, tarjeta));
                    log.log(Level.INFO, "Se registro el pago con tarjeta");
                    banco = null;
                    valorTarjeta = null;
                    voucher = null;
                    digitos = null;
                    franquicia = null;
                    tarjeta = null;
                    pasosPago = 1;
                    break;
                case 3:
                    valor = valorCruce;
                    break;
                case 4:
                    valor = valorPagoPendiente;
                    break;
                default:
                    break;
            }
        }

        if (valor != null) {
            if (valor.compareTo(valorPendienteFactura) > 0) {
                mostrarMensaje("Error", "El valor que ingreso para el pago es mayor al que se debe.", true, false, false);
                log.log(Level.SEVERE, "El valor que ingreso para el pago es mayor al que se debe");
                if (null != medioPago) {
                    switch (medioPago) {
                        case 1:
                            valorEfectivo = null;
                            break;
                        case 2:
                            valorTarjeta = null;
                            break;
                        case 3:
                            valorCruce = null;
                            break;
                        case 4:
                            valorPagoPendiente = null;
                            break;
                        default:
                            break;
                    }
                }
                return;
            }

            valorPendienteFactura = valorPendienteFactura.subtract(valor);
        }
    }

    public void quitarPago() {
        BigDecimal valor = null;

        if (null != medioPago) {
            switch (medioPago) {
                case 1:
                    valor = valorEfectivo;
                    valorEfectivo = null;
                    break;
                case 2:
                    for (PagoTarjetaDTO p : pagosTarjetas) {
                        if (p.getIdentificador().equals(pagoTarjeta)) {
                            valor = p.getValorPago();
                            pagosTarjetas.remove(p);
                            pagoTarjeta = null;
                            break;
                        }
                    }
                    break;
                case 3:
                    valor = valorCruce;
                    valorCruce = null;
                    break;
                case 4:
                    valor = valorPagoPendiente;
                    break;
                default:
                    break;
            }
        }

        if (valor != null) {
            valorPendienteFactura = valorPendienteFactura.add(valor);
        }
    }

    public void seleccionarCondicionPago() {
        condicionPago = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("condicionPago"));
        log.log(Level.INFO, "Se selecciono la condicion de pago {0}", condicionPago);
    }

    public void solicitarAprobacion() {
        if (valorPagoPendiente == null || valorPagoPendiente.equals(0)) {
            mostrarMensaje("Error", "Debe ingresar el valor que solicitara para este pago.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar el valor que solicitara para este pago");
            return;
        }
        if (condicionPago == null || condicionPago == 0) {
            mostrarMensaje("Error", "Seleccione una condición de pago.", true, false, false);
            log.log(Level.SEVERE, "Seleccione una condicion de pago");
            return;
        }

        for (AutorizadoresPagoFacturaDTO a : autorizadoresPagoFactura) {
            if (a.isSeleccionado()) {
                AutorizacionPago auto = new AutorizacionPago();

                auto.setComentario(comentarioPagoPendiente);
                auto.setCorreoAprueba(a.getCorreo());
                auto.setEstado("AP");
                auto.setFechaSolicita(new Date());
                auto.setIdCondicionPago(condicionPago);
                auto.setIdCotizacion(cotizacion.getIdCotizacion().intValue());
                auto.setToken(RandomStringUtils.randomAlphanumeric(20));
                auto.setUsuarioAprueba(a.getCedula());
                auto.setUsuarioSolicita(userSessionInfoMBean.getUsuario());
                auto.setValorSolicitado(valorPagoPendiente);
                auto.setCliente(clienteSessionMBean.getClienteDto().getNit());
                auto.setCodAsesor(Integer.parseInt(userSessionInfoMBean.getCodigoVentas()));

                try {
                    autorizacionPagoFacade.create(auto);
                    MailMessageDTO mail = new MailMessageDTO();
                    mail.addToAddress(a.getCorreo());
                    mail.setFrom("Solicitud aprobación FV <notificaciones@matisses.co>");
                    mail.setSubject("Solicitud aprobación FV");

                    Map<String, String> params = new HashMap<>();
                    params.put("usuario", baruGenericMBean.obtenerNombreAsesor(Integer.parseInt(userSessionInfoMBean.getCodigoVentas())));
                    params.put("url", applicationMBean.obtenerValorPropiedad("url.web.aprobacionFactura") + "id=" + auto.getIdAutorizacionPago() + "&token=" + auto.getToken());

                    try {
                        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                        client.enviarHtmlEmail("Solicitud aprobación FV", "Solicitud aprobación FV", a.getCorreo(), "autorizacion_pago_factura", null, params);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al mandar la solicitud de aprobacion", e);
                        mostrarMensaje("Error", "Ocurrión un error al mandar la solicitud de aprobación.", true, false, false);
                        return;
                    }
                } catch (Exception e) {
                }
            }
        }
        valorPagoPendiente = null;
        obtenerSiguientePasoPago();
    }

    public void consultarEstadoAprobacion() {
        autorizaciones = new ArrayList<>();
        if (cotizacion != null && cotizacion.getIdCotizacion() != null && cotizacion.getIdCotizacion() != 0) {
            List<AutorizacionPago> authorizations = autorizacionPagoFacade.obtenerAutorizacionesCotizacion(cotizacion.getIdCotizacion().intValue());

            if (authorizations != null && !authorizations.isEmpty()) {
                boolean aplicar = false;
                int aprobadaRechazada = 0;
                for (AutorizacionPago a : authorizations) {
                    autorizaciones.add(new AutorizacionPagoDTO(a.getIdAutorizacionPago(), a.getIdCotizacion(), a.getCodAsesor(), a.getIdCondicionPago(), a.getValorSolicitado(),
                            a.getCliente(), a.getUsuarioSolicita(), a.getComentario(), a.getUsuarioAprueba(), a.getCorreoAprueba(), a.getComentarioAprueba(), a.getToken(),
                            a.getEstado(), a.getFechaSolicita(), a.getFechaAprobacion(), a.getRespuesta() == null ? false : a.getRespuesta()));
                    if (a.getRespuesta() != null) {
                        if (a.getRespuesta() && !aplicar) {
                            aplicar = true;
                            condicionPago = a.getIdCondicionPago();
                        }
                        aprobadaRechazada++;
                    }
                }

                if (aplicar) {
                    valorPagoPendiente = autorizaciones.get(0).getValorSolicitado();
                    medioPago = 4;
                    aplicarPago();
                    medioPago = null;
                }

                if (aprobadaRechazada > 0) {
                    pasosPago = 5;
                } else {
                    pasosPago = 4;
                }
            }
        }
    }

    public void obtenerItemPosicion() {
        detalleVenta = cotizacion.getDetalle().get(posicionItem);

        if (detalleVenta.getUbicaciones() == null || detalleVenta.getUbicaciones().isEmpty()) {
            if (ubicacionSAPFacade.validarUbicacionesAlmacen(detalleVenta.getBodega())) {
                detalleVenta.setTieneUbicaciones(true);
                detalleVenta.setUbicaciones(new ArrayList<SaldoUbicacionDTO>());

                List<SaldoUbicacion> balances = saldoUbicacionFacade.findByItemCodeAndWhsCode(false, detalleVenta.getReferencia(), detalleVenta.getBodega());

                if (balances != null && !balances.isEmpty()) {
                    for (SaldoUbicacion s : balances) {
                        if (cotizacion.isDemostracion()) {
                            if (s.getUbicacion().getSL1Code().equals("DEMO")) {
                                detalleVenta.getUbicaciones().add(new SaldoUbicacionDTO(s.getAbsEntry(), s.getUbicacion().getAbsEntry(), null, s.getOnHandQty().intValue(), 0, null, null,
                                        s.getUbicacion().getBinCode().replace(detalleVenta.getBodega(), ""), null));
                            }
                        } else {
                            detalleVenta.getUbicaciones().add(new SaldoUbicacionDTO(s.getAbsEntry(), s.getUbicacion().getAbsEntry(), null, s.getOnHandQty().intValue(), 0, null, null,
                                    s.getUbicacion().getBinCode().replace(detalleVenta.getBodega(), ""), null));
                        }
                    }
                } else {
                    mostrarMensaje("Error", "No se encontraron ubicaciones para el ítem.", true, false, false);
                    log.log(Level.SEVERE, "No se encontraron ubicaciones para el item");
                    return;
                }
            } else {
                detalleVenta.setTieneUbicaciones(false);
            }

            cotizacion.getDetalle().set(posicionItem, detalleVenta);
        }

        referencia = detalleVenta.getReferencia();
        obtenerGaleria();
    }

    public void obtenerAnteriorPasoFacturacion() {
        if (pasosFacturacion == 2) {
            if (detalleVenta.getCantidad() != detalleVenta.getCantidadUsada()) {
                mostrarMensaje("Error", "Debe seleccionar las ubicaciones de donde se extraerá la mercancía.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar las ubicaciones de donde se extraera la mercancia");
                return;
            }
            if (detalleVenta.getEstado() == null || detalleVenta.getEstado().isEmpty() || detalleVenta.getEstado().equals("O")) {
                mostrarMensaje("Error", "Seleccione el estado de la referencia.", true, false, false);
                log.log(Level.SEVERE, "Seleccione el estado de la referencia");
                return;
            }

            posicionItem--;
            detalleVenta = cotizacion.getDetalle().get(posicionItem);

            referencia = detalleVenta.getReferencia();
            obtenerGaleria();
        }
    }

    public void asignarUbicacion() {
        if (detalleVenta.getCantidadUsada() < detalleVenta.getCantidad()) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < detalleVenta.getUbicaciones().size(); i++) {
                if (detalleVenta.getUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    if (detalleVenta.getUbicaciones().get(i).getOnHandQtyUsado() < detalleVenta.getUbicaciones().get(i).getOnHandQty()) {
                        detalleVenta.getUbicaciones().get(i).setOnHandQtyUsado(detalleVenta.getUbicaciones().get(i).getOnHandQtyUsado() + 1);
                        cotizacion.getDetalle().set(posicionItem, detalleVenta);
                        detalleVenta.setCantidadUsada(detalleVenta.getCantidadUsada() + 1);

                        log.log(Level.INFO, "Se agrego una unidad de la ubicacion {0} del almace {1} para la referencia {2}, en el proceso de facturacion",
                                new Object[]{ubicacion, detalleVenta.getBodega(), detalleVenta.getReferencia()});
                    } else {
                        mostrarMensaje("Error", "No puede asignar más de la cantidad disponible de la ubicación.", true, false, false);
                        log.log(Level.SEVERE, "No puede asignar mas de la cantidad disponible de la ubicacion");
                        return;
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "No puede asignar más de la cantidad necesaria para el ítem.", true, false, false);
            log.log(Level.SEVERE, "No puede asignar mas de la cantidad necesaria para el item");
            return;
        }
    }

    public void quitarUbicacion() {
        if (detalleVenta.getCantidadUsada() > 0) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < detalleVenta.getUbicaciones().size(); i++) {
                if (detalleVenta.getUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    if (detalleVenta.getUbicaciones().get(i).getOnHandQtyUsado() > 0) {
                        detalleVenta.getUbicaciones().get(i).setOnHandQtyUsado(detalleVenta.getUbicaciones().get(i).getOnHandQtyUsado() - 1);
                        cotizacion.getDetalle().set(posicionItem, detalleVenta);
                        detalleVenta.setCantidadUsada(detalleVenta.getCantidadUsada() - 1);

                        log.log(Level.INFO, "Se quito una unidad de la ubicacion {0} del almace {1} para la referencia {2}, en el proceso de facturacion",
                                new Object[]{ubicacion, detalleVenta.getBodega(), detalleVenta.getReferencia()});
                    } else {
                        mostrarMensaje("Error", "No puede quitar más de 0.", true, false, false);
                        log.log(Level.SEVERE, "No puede asignar mas de 0");
                        return;
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "No puede quitar más de 0.", true, false, false);
            log.log(Level.SEVERE, "No puede asignar mas de 0");
            return;
        }
    }

    public void seleccionarEstadoItem() {
        String estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");

        if (estado.equals("D") && !detalleVenta.getBodega().equals(userSessionInfoMBean.getAlmacen())) {
            //TODO: Excepción Estado Entrega inmediata, para productos en consignación, asociados a la tienda.
            if ((userSessionInfoMBean.getAlmacen().equals("0203") && !detalleVenta.getBodega().equals("0821"))
                    || (userSessionInfoMBean.getAlmacen().equals("0301") && !detalleVenta.getBodega().equals("0831"))) {
                mostrarMensaje("Error", "No puede asignar el estado: Despachado - Entrega inmediata, porque el ítem no sale del mismo almacén donde inicio sesión.", true, false, false);
                log.log(Level.SEVERE, "No puede asignar el estado: Despachado - Entrega inmediata, porque el item no sale del mismo almacen donde inicio sesion");
                return;
            }
        }

        log.log(Level.INFO, "Se selecciono el estado {0}, para la referencia {1} del almacen {2}", new Object[]{estado, detalleVenta.getReferencia(), detalleVenta.getBodega()});
        detalleVenta.setEstado(estado);
        cotizacion.getDetalle().set(posicionItem, detalleVenta);
    }

    private void obtenerAsesoresVenta() {
        empleadosComision = new HashMap<>();

        List<Object[]> employeeds = empleadoFacade.obtenerEmpleadosVentas(userSessionInfoMBean.getAlmacen());

        if (employeeds == null || employeeds.isEmpty()) {
            employeeds = empleadoFacade.obtenerEmpleadosVentas(null);
        }

        if (employeeds != null && !employeeds.isEmpty()) {
            for (Object[] e : employeeds) {
                if (empleadosComision.containsKey(((String) e[1]).replace(" ", "").replace("(", "-").replace(")", ""))) {
                    EmpleadoDTO emp = new EmpleadoDTO((String) e[3], (String) e[9], (String) e[2], (String) e[7], ((String) e[7]).equals(userSessionInfoMBean.getCodigoVentas()) ? true : false);

                    empleadosComision.get(((String) e[1]).replace(" ", "").replace("(", "-").replace(")", "")).add(emp);

                    if (((String) e[7]).equals(userSessionInfoMBean.getCodigoVentas()) && (tabAsesor == null || tabAsesor.isEmpty())) {
                        tabAsesor = ((String) e[1]).replace(" ", "").replace("(", "-").replace(")", "");
                    }
                } else {
                    List<EmpleadoDTO> list = new ArrayList<>();
                    EmpleadoDTO emp = new EmpleadoDTO((String) e[3], (String) e[9], (String) e[2], (String) e[7], ((String) e[7]).equals(userSessionInfoMBean.getCodigoVentas()) ? true : false);
                    list.add(emp);

                    empleadosComision.put(((String) e[1]).replace(" ", "").replace("(", "-").replace(")", ""), list);

                    if (((String) e[7]).equals(userSessionInfoMBean.getCodigoVentas()) && (tabAsesor == null || tabAsesor.isEmpty())) {
                        tabAsesor = ((String) e[1]).replace(" ", "").replace("(", "-").replace(")", "");
                    }
                }

                if (((String) e[7]).equals(userSessionInfoMBean.getCodigoVentas())) {
                    asesores++;
                }
            }
        }
    }

    public void cambiarTagAsesor() {
        tabAsesor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tabAsesor");

        log.log(Level.INFO, "Se selecciono el tag {0}", tabAsesor);
    }

    public void seleccionarAsesor() {
        String asesor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("asesor");

        for (EmpleadoDTO e : empleadosComision.get(tabAsesor)) {
            if (e.getCodigoAsesor().equals(asesor)) {
                if (e.isAsesorSeleccionado()) {
                    e.setAsesorSeleccionado(false);
                    asesores--;
                    log.log(Level.INFO, "Se selecciono el asesor {0}, para no comisionar en la factura que se esta creando", asesor);
                } else if (asesores < 5) {
                    e.setAsesorSeleccionado(true);
                    asesores++;
                    log.log(Level.INFO, "Se selecciono el asesor {0}, para comisionar en la factura que se esta creando", asesor);
                } else {
                    mostrarMensaje("Error", "No puede seleccionar más de 5 asesores para la comisión.", true, false, false);
                    log.log(Level.SEVERE, "No puede seleccionar mas de 5 asesores para la comision");
                }
                break;
            }
        }
    }

    public void obtenerDecoradores() {
        decoradores = new ArrayList<>();

        List<BaruDecoradores> decorations = baruDecoradoresFacade.obtenerDecoradoresActivos();

        if (decorations != null && !decorations.isEmpty()) {
            for (BaruDecoradores b : decorations) {
                decoradores.add(new BaruDecoradoresDTO(b.getCode(), b.getName(), b.getuNit(), b.getuFechaIngreso(), b.getuEstado()));
            }
        }
    }

    public void seleccionarDecorador() {
        decorador = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("decorador");
        log.log(Level.SEVERE, "Se selecciono el decorador {0} para la factura", decorador);
    }

    public void validarFacturar() {
        if (dlgCrearFactura) {
            dlgCrearFactura = false;
            ventasSessionMBean.setFacturando(true);
        } else {
            dlgCrearFactura = true;
            ventasSessionMBean.setFacturando(false);
        }
    }

    public void facturar() {
        dlgFacturarDemo = false;
        dlgCrearFactura = false;
        if (comentarioFactura == null || comentarioFactura.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un comentario para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un comentario para poder continuar");
            ventasSessionMBean.setFacturando(true);
            return;
        }

        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(userSessionInfoMBean.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            mostrarMensaje("Error", "No fue posible iniciar una sesión en SAP B1WS.", true, false, false);
            ventasSessionMBean.setFacturando(true);
            return;
        }

        /*Se crea el encabezado de la factura*/
        SalesDocumentDTO invoice = new SalesDocumentDTO();

        /*Se obtienen todos los parametros necesarios para la factura, de los cuales solo se usaran el numero */
        //consultar datos configurados para el detalle de la factura
        // 0. Cod Ciudad
        // 1. Codigo serie numeracion
        // 2. Nombre serie numeracion
        // 3. Codigo ventas
        // 4. Codigo logistica
        // 5. Codigo proyecto
        // 6. WUID
        // 7. Serie recibo
        // 8. Resolucion fact. desde
        //9. Resolucion fact. hasta
        //10. Resolucion fact. numero y fecha (separados por "de")
        //11. Resolucion fact. prefijo
        //12. Serie anulacion
        Object[] parametros = almacenFacade.obtenerParametrosFacturaMobiliario(userSessionInfoMBean.getAlmacen());

        if (parametros == null || parametros.length == 0) {
            mostrarMensaje("Error", "No fue posible obtener los parametros de: Logistica, Ventas, Proyecto, etc...", true, false, false);
            log.log(Level.SEVERE, "No fue posible obtener los parametro de: Logistica, Ventas, Proyecto, etc...");
            ventasSessionMBean.setFacturando(true);
            return;
        }

        invoice.setCardCode(clienteSessionMBean.getClienteDto().getNit());
        invoice.setLogisticsCostingCode((String) parametros[4]);
        invoice.setProjectCode((String) parametros[5]);
        invoice.setSeriesCode((String) parametros[1]);
        invoice.setWuid((String) parametros[6]);
        invoice.setDocumentLines(new ArrayList<SalesDocumentLineDTO>());
        invoice.setSalesEmployees(new ArrayList<SalesEmployeeDTO>());
        invoice.setRouteCostingCode(obtenerRutaCliente(clienteSessionMBean.getDireccionCliente()));
        invoice.setSalesCostingCode((String) parametros[3]);
        if (valorPagoPendiente != null && valorPagoPendiente.compareTo(new BigDecimal(0)) > 0) {
            invoice.setPaymentGroupCode(condicionPago.toString());
        } else {
            invoice.setPaymentGroupCode("15");
        }
        invoice.setComments("Factura creada por 360 - " + comentarioFactura.toUpperCase());
        invoice.setSource("T");
        invoice.setSalesPerson(Long.parseLong(userSessionInfoMBean.getCodigoVentas()));
        invoice.setDesignerCode(decorador);
        if (ventasSessionMBean.getNumeroCotizacion() != null && ventasSessionMBean.getNumeroCotizacion() != 0) {
            invoice.setRefDocnum(ventasSessionMBean.getNumeroCotizacion().toString());
        }

        if (empleadosComision != null && !empleadosComision.isEmpty()) {
            for (Map.Entry<String, List<EmpleadoDTO>> emp : empleadosComision.entrySet()) {
                for (EmpleadoDTO e : emp.getValue()) {
                    if (e.isAsesorSeleccionado()) {
                        SalesEmployeeDTO salesEmployee = new SalesEmployeeDTO();

                        salesEmployee.setName(e.getNombreCompleto());
                        salesEmployee.setSlpCode(e.getCodigoAsesor());

                        invoice.getSalesEmployees().add(salesEmployee);
                    }
                }
            }
        }

        /*Contra validar los items de la cotizacion para cerrar las lineas que se van a facturar*/
        List<DetalleCotizacionSAP> detalleCot = detalleCotizacionSAPFacade.obtenerDetalleCotizacion(ventasSessionMBean.getNumeroCotizacion());

        if ((detalleCot != null && !detalleCot.isEmpty() && ventasSessionMBean.isModificando()) || (!ventasSessionMBean.isModificando())) {
            if (cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
                int contador = 0;
                for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                    SalesDocumentLineDTO documentLine = new SalesDocumentLineDTO();

                    documentLine.setItemCode(d.getReferencia());
                    documentLine.setLineNum(contador);
                    documentLine.setPrice(baruGenericMBean.obtenerPrecioVenta(d.getReferencia()).doubleValue());
                    documentLine.setQuantity(d.getCantidad());
                    documentLine.setWhsCode(d.getBodega());
                    documentLine.setShippingStatus(d.getEstado());

                    Long discount = getObtenerDescuento(d.getReferencia());

                    if (discount != null && discount > 0) {
                        documentLine.setDiscountPercent(discount.doubleValue());
                    }

                    for (DetalleCotizacionSAP dC : detalleCot) {
                        if (dC.getItemCode().equals(d.getReferencia()) && dC.getWhsCode().equals(d.getBodega()) && dC.getQuantity().intValue() == d.getCantidad()
                                && cotizacion.getNitCliente().equals(clienteSessionMBean.getClienteDto().getNit())) {
                            documentLine.setBaseEntry(ventasSessionMBean.getNumeroCotizacion());
                            documentLine.setBaseLine(Long.parseLong(String.valueOf(dC.getDetalleCotizacionSAPPK().getLineNum())));

                            detalleCot.remove(dC);
                            break;
                        }
                    }

                    if (d.getUbicaciones() != null && !d.getUbicaciones().isEmpty()) {
                        for (SaldoUbicacionDTO s : d.getUbicaciones()) {
                            if (s.getOnHandQtyUsado() > 0) {
                                SalesDocumentLineBinAllocationDTO binAllocation = new SalesDocumentLineBinAllocationDTO();

                                binAllocation.setBinAbsEntry(s.getUbicacion());
                                binAllocation.setQuantity(s.getOnHandQtyUsado());
                                binAllocation.setWhsCode(d.getBodega());

                                documentLine.getBinAllocations().add(binAllocation);
                            }
                        }
                    }

                    invoice.getDocumentLines().add(documentLine);
                    contador++;
                }

                InvoicesClient client = new InvoicesClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                try {
                    GenericRESTResponseDTO res = client.crearFactura(userSessionInfoMBean.getUsuario(), invoice);
                    if (res.getEstado() <= 0) {
                        mostrarMensaje("Error", res.getMensaje(), true, false, false);
                        ventasSessionMBean.setFacturando(true);
                        return;
                    }

                    if (res.getValor() != null && res.getValor() != 0) {
                        log.log(Level.INFO, "Se creo la factura con numero: [{0}]", res.getValor());
                        FacturaSAP factura = facturaSAPFacade.find(res.getValor());

                        /*Se crea el recibo de caja si es necesario*/
                        //TODO: Se debe condicionar la creacion del recibo si es con pago pendiente
                        Long reciboCaja = crearPagoFactura(res.getValor().longValue(), parametros);
                        Long numeroEntrada = null;
                        if ((reciboCaja == null || reciboCaja == 0) && (valorCruce == null || valorCruce.equals(0))) {
                            mostrarMensaje("Error", "Ocurrió un error al crear el recibo de caja para la factura.", true, false, false);
                            log.log(Level.SEVERE, "Ocurrio un error al crear el recibo de caja para la factura");
                        } else {
                            log.log(Level.INFO, "Se creo el recibo de caja numero {0}", reciboCaja);
                        }

                        ///*Se valida si hay referencias en consignacion - Preguntar si este tipo de factura aplica en el desarrollo*/
                        /*Si hay mercancia en estado pendiente o guardar, generar la entrada de mercancia a clientes y generar la orden de venta*/
                        int pendiente = 0;
                        int guardar = 0;
                        for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                            if (d.getEstado().equals("P")) {
                                pendiente++;
                            } else if (d.getEstado().equals("G")) {
                                guardar++;
                            }
                        }

                        if (pendiente > 0 || guardar > 0) {
                            numeroEntrada = crearEntradaMercancia(factura);
                            if (numeroEntrada != null && numeroEntrada != 0) {
                                crearOrdenVenta(factura);
                            }
                        }

                        //TODO: se debe mandar a imprimir los documentos
                        List<String[]> documentosRelacionados = new ArrayList<>();

                        if (guardar > 0) {
                            documentosRelacionados.add(new String[]{"daka\\contratoDaka", "2"});
                        }
                        if (reciboCaja != null && reciboCaja != 0) {
                            documentosRelacionados.add(new String[]{"reciboCaja\\reciboCaja", "2"});
                        }

                        String[] s = generarDocumento(factura.getDocNum(), 2, String.valueOf(factura.getDocNum()), "factura", userSessionInfoMBean.getAlmacen(), null, true, documentosRelacionados);
                        notificarProceso(factura.getDocNum(), "factura", null, false, s);

                        marcarCotizacion(factura.getDocNum(), "CF", "factura");
                        limpiar();
                        mostrarMensaje("Éxito", "Se creo correctamente la factura número " + factura.getDocNum(), false, true, false);
                        return;
                    } else {
                        mostrarMensaje("Error", "Ocurrio un error al crear la factura.", true, false, false);
                        ventasSessionMBean.setFacturando(true);
                        return;
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "", e);
                    mostrarMensaje("Error", e.getMessage(), true, false, false);
                    ventasSessionMBean.setFacturando(true);
                    return;
                }
            }
        }
    }

    public String obtenerRutaCliente(DireccionesClienteDTO direccion) {
        if (direccion != null && direccion.getNombre() != null && !direccion.getNombre().isEmpty()) {
            BaruMunicipios municipio = baruMunicipiosFacade.find(direccion.getCodCiudad());

            if (municipio != null && municipio.getCode() != null && !municipio.getCode().isEmpty()) {
                return municipio.getuRutaRelacionada().toString();
            }
        }
        return null;
    }

    private Long crearPagoFactura(Long numeroFactura, Object[] parametros) {
        if ((valorEfectivo != null && valorEfectivo.compareTo(new BigDecimal(0)) > 0) || (pagosTarjetas != null && !pagosTarjetas.isEmpty())) {
            if (numeroFactura != null && numeroFactura != 0) {
                PaymentDTO pago = new PaymentDTO();

                pago.setCardCode(clienteSessionMBean.getClienteDto().getNit());
                pago.setCreditType("I");
                if (numeroFactura != null) {
                    double valorPago = 0;
                    if (valorPagoPendiente.compareTo(new BigDecimal(0)) > 0) {
                        valorPago = totalFactura.subtract(valorPagoPendiente).doubleValue();
                    } else {
                        valorPago = totalFactura.doubleValue();
                    }
                    List<PaymentInvoicesDTO> documents = new ArrayList<>();
                    documents.add(new PaymentInvoicesDTO(numeroFactura, valorPago));
                    pago.setPaymentInvoice(documents);
                    pago.setPaymentType(PaymentDTO.PaymentTypeDTO.CUSTOMER);
                } else {
                    pago.setPaymentType(PaymentDTO.PaymentTypeDTO.ACCOUNT);
                }
                pago.setCashAccount(baruParametrosWebFacade.obtenerCuentaGeneralAlmacen(userSessionInfoMBean.getAlmacen()));
                pago.setPaidCash(valorEfectivo == null ? "0" : valorEfectivo.toString());
                pago.setSeriesCode((String) parametros[7]);

                List<CreditCardPaymentDTO> creditCardPayments = new ArrayList<>();

                for (PagoTarjetaDTO p : pagosTarjetas) {
                    CreditCardPaymentDTO creditPayment = new CreditCardPaymentDTO();

                    creditPayment.setCreditCardCode(p.getIdCuenta().toString());
                    creditPayment.setCreditCardNumber(p.getDigitos());
                    creditPayment.setNumberOfPayments("1");
                    creditPayment.setPaidSum(p.getValorPago().toString().replace(".0", ""));
                    creditPayment.setValidUntil(null);
                    creditPayment.setVoucherNumber(p.getVoucher());

                    creditCardPayments.add(creditPayment);
                }
                pago.setCreditCardPayments(creditCardPayments);

                IncomingPaymentClient client = new IncomingPaymentClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                try {
                    GenericRESTResponseDTO res = client.crearPago(userSessionInfoMBean.getUsuario(), pago);
                    if (res.getEstado() <= 0) {
                        mostrarMensaje("Error", res.getMensaje(), true, false, false);
                        ventasSessionMBean.setFacturando(true);
                        return 0L;
                    }
                    log.log(Level.INFO, "Se creo el recibo de caja con numero: [{0}]", res.getValor());
                    return res.getValor().longValue();
                } catch (Exception e) {
                    log.log(Level.INFO, "No se agregaron pagos a la factura, por lo tanto no se generara recibo de caja ", e);
                    return 0L;
                }
            }
        }
        return 0L;
    }

    private Long crearEntradaMercancia(FacturaSAP factura) {
        GoodsReceiptDTO entrada = new GoodsReceiptDTO();

        entrada.setComments("Entrada de mercancia para FV " + factura.getDocNum() + " y cliente " + factura.getCardCode());
        entrada.setInvoiceNumber(Integer.toString(factura.getDocNum()));
        entrada.setJournalMemo("Good Receipt");
        entrada.setSeries(69L);
        entrada.setOrigen("T");

        Integer contador = 0;
        for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
            if (d.getEstado().equals("P") || d.getEstado().equals("G")) {
                GoodsReceiptDetailDTO detEntrada = new GoodsReceiptDetailDTO();

                InformacionAlmacenDTO infoAlmacenEntrada = applicationMBean.getInfoAlmacen(d.getBodega());

                if (d.isTieneUbicaciones()) {
                    if (ubicacionSAPFacade.validarUbicacionesAlmacen(d.getBodega().contains("CL") ? d.getBodega() : d.getBodega() + "CL")) {
                        GoodsReceiptLocationsDTO ubEntrada = new GoodsReceiptLocationsDTO();
                        ubEntrada.setBinAbs(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getBodega().contains("CL") ? d.getBodega() : d.getBodega() + "CL" + "TM").getAbsEntry());
                        ubEntrada.setQuantity(d.getCantidad());

                        detEntrada.addLocation(ubEntrada);
                    }
                }

                detEntrada.setAccountCode("91051001");
                detEntrada.setItemCode(d.getReferencia());
                detEntrada.setLineNum(contador.longValue());
                detEntrada.setQuantity(d.getCantidad());
                detEntrada.setPrice(baruGenericMBean.obtenerPrecioVenta(d.getReferencia()).doubleValue());

                if (infoAlmacenEntrada != null && infoAlmacenEntrada.getAlmacenClientes() != null && !infoAlmacenEntrada.getAlmacenClientes().isEmpty()) {
                    detEntrada.setWhsCode(infoAlmacenEntrada.getAlmacenClientes());
                } else {
                    detEntrada.setWhsCode(d.getBodega().contains("CL") ? d.getBodega() : "CL" + d.getBodega());
                }

                entrada.addDetail(detEntrada);
                contador++;
            }
        }

        GoodsReceiptClient client = new GoodsReceiptClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.crearEntradaMercancia(userSessionInfoMBean.getUsuario(), entrada);
            if (res.getEstado() <= 0) {
                mostrarMensaje("Error", res.getMensaje(), true, false, false);
                ventasSessionMBean.setFacturando(true);
                return 0L;
            }
            log.log(Level.INFO, "Se creo la entrada de mercancia con numero: [{0}]", res.getValor());
            return res.getValor().longValue();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear la entrada al almacen de clientes ", e);
            return 0L;
        }
    }

    private Long crearOrdenVenta(FacturaSAP factura) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, 1);

        OrderDTO order = new OrderDTO();

        order.setCardCode(factura.getCardCode());
        order.setComments(factura.getComments());
        order.setDocDate(new Date());
        order.setDocDueDate(cal.getTime());
        order.setInvoiceNumber(Integer.toString(factura.getDocNum()));
        order.setOrigen("T");
        order.setSalesPersonCode(factura.getSlpCode().longValue());
        order.setSeries(13L);

        List<DetalleFacturaSAP> detalleFactura = detalleFacturaSAPFacade.obtenerDetalleFactura(factura.getDocEntry().doubleValue());
        if (detalleFactura != null && !detalleFactura.isEmpty()) {
            Integer contador = 0;
            for (DetalleFacturaSAP d : detalleFactura) {
                if (d.getUEstadoP().equals("P") || d.getUEstadoP().equals("G")) {
                    OrderDetailDTO detail = new OrderDetailDTO();

                    detail.setEstado(d.getUEstadoP());
                    detail.setItemCode(d.getItemCode());
                    detail.setLineNum(contador.longValue());
                    detail.setQuantity(Double.parseDouble(String.valueOf(d.getQuantity())));
                    detail.setWarehouseCode(d.getWhsCode().contains("CL") ? d.getWhsCode() : "CL" + d.getWhsCode());
                    detail.setuLineNumFv(((Integer) d.getDetalleFacturaSAPPK().getLineNum()).longValue());

                    order.addLine(detail);
                    contador++;
                }
            }

            OrderClient client = new OrderClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            try {
                GenericRESTResponseDTO res = client.crearOrdenVenta(userSessionInfoMBean.getUsuario(), order);
                if (res.getEstado() <= 0) {
                    mostrarMensaje("Error", res.getMensaje(), true, false, false);
                    ventasSessionMBean.setFacturando(true);
                    return 0L;
                }
                log.log(Level.INFO, "Se creo la orden de venta con numero: [{0}]", res.getValor());
                return res.getValor().longValue();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la orden de venta de la factura ", e);
                return 0L;
            }
        } else {
            log.log(Level.SEVERE, "No se encontraron datos de detalle para la factura");
            return 0L;
        }
    }

    private void marcarCotizacion(Integer documento, String estado, String tipo) {
        if (cotizacion != null && cotizacion.getIdCotizacion() != null && cotizacion.getIdCotizacion() != 0) {
            CotizacionWeb cot = cotizacionWebFacade.find(cotizacion.getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null & cot.getIdCotizacion() != 0) {
                cot.setEstado(estado);
                if (cot.getNitCliente() == null || cot.getNitCliente().isEmpty()) {
                    cot.setNitCliente(clienteSessionMBean.getClienteDto().getNit());
                }
                if (tipo.equals("factura")) {
                    cot.setNroFacturaSAP(documento);
                }

                try {
                    cotizacionWebFacade.edit(cot);
                    log.log(Level.INFO, "Se marco la cotizacion [{0}] con el estado {1}", new Object[]{cotizacion.getIdCotizacion(), estado});
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al cambiar el estado de la cotizacion. ", e);
                    mostrarMensaje("Error", "No se pudo cambiar el estado a la cotización.", true, false, false);
                    return;
                }
            }
        }
    }

    public void cancelarFacturacion() {
        posicionItem = 0;
        asesores = 0;
        medioPago = 0;
        pasosPago = 1;
        condicionPago = null;
        pasosFacturacion = 1;
        banco = null;
        pagoTarjeta = null;
        totalFactura = null;
        valorPendienteFactura = null;
        valorPagoPendiente = null;
        valorEfectivo = null;
        valorTarjeta = null;
        saldoFavor = null;
        valorCruce = null;
        comentarioPagoPendiente = null;
        franquicia = null;
        tarjeta = null;
        voucher = null;
        digitos = null;
        datafono = null;
        tabAsesor = null;
        decorador = null;
        comentarioFactura = null;
        detalleVenta = null;
        franquicias = new ArrayList<>();
        autorizadoresPagoFactura = new ArrayList<>();
        condicionesPago = new ArrayList<>();
        autorizaciones = new ArrayList<>();
        bancos = new ArrayList<>();
        datafonos = new ArrayList<>();
        pagosTarjetas = new ArrayList<>();
        decoradores = new ArrayList<>();
        empleadosComision = new HashMap<>();
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setFacturando(false);
        limpiarSesionCliente();
    }

    /**
     * ****INICIA BLOQUE DE DEMOSTRACIONES Y FINALIZA BLOQUE DE FACTURAS*****
     */
    public String crearDemostracion() throws ParseException {
        if (!ventasSessionMBean.isDemostracion()) {
            ventasSessionMBean.setDemostracion(true);
            if (cotizacion.getNitCliente() != null && !cotizacion.getNitCliente().isEmpty()) {
                clienteSessionMBean.setClienteDto(new ClienteSAPDTO(cotizacion.getNitCliente(), null));
            }
            return "clientes";
        } else if (saldosAcumulados != null && !saldosAcumulados.isEmpty()) {
            log.log(Level.INFO, "Inicia el proceso de creacion de la demostracion");
            /**
             * Se deben seguir los siguientes pasos: 1- Realizar los movimientos
             * pertinentes de la mercancia de ventas a almacenes demo. 2-
             * Actualizar datos de las ubicaciones del asesor, si las
             * ubicaciones no existen se deben crear.
             */

            log.log(Level.INFO, "Primer paso: Se validan los datos de la demostracion, como: Nombre, fecha y factura o comentario");
            if (nombreDemostracion == null || nombreDemostracion.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar un nombre para la demostración.", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar un nombre para la demostracion");
                return null;
            } else if (nombreDemostracion.length() > 20) {
                mostrarMensaje("Error", "El nombre de la demostración no puede superar los 20 caracteres.", true, false, false);
                log.log(Level.SEVERE, "El nombre de la demostracion no puede superar los 20 caracteres");
                return null;
            }
            if (fechaDemostracion == null) {
                mostrarMensaje("Error", "Debe ingresar una fecha para la demostración.", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar una fecha para la demostracion");
                return null;
            } else if (fechaDemostracion.before(new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(new Date())))) {
                mostrarMensaje("Error", "La fecha de la demostración no puede ser anterior a la actual.", true, false, false);
                log.log(Level.SEVERE, "La fecha de la demostracion no puede ser anterior a la actual");
                return null;
            }

            /*Se valida que no hayan items de mercancia en consignacion*/
            List<ProveedoresExterior> prov = proveedoresExteriorFacade.obtenerProveedoresConsignacion();
            if (prov != null && !prov.isEmpty()) {
                StringBuilder sb = new StringBuilder();

                Map<String, String> proveedores = new HashMap<>();
                for (ProveedoresExterior p : prov) {
                    if (!proveedores.containsKey(p.getProveedor())) {
                        proveedores.put(p.getProveedor(), p.getNombreProveedor());
                    }
                }

                for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                    if (proveedores.containsKey(d.getReferencia().substring(0, 3))) {
                        sb.append(baruGenericMBean.convertirARefCorta(d.getReferencia()));
                        sb.append(" - ");
                    }
                }

                if (sb.length() > 1) {
                    mostrarMensaje("Error", "No se pueden llevar los artículos en consignación para las demostraciones: " + sb.toString(), true, false, false);
                    log.log(Level.SEVERE, "No se pueden llevar los articulos en consignacion para las demostraciones: {0}", sb.toString());
                    return null;
                }
            }

            String slotDemo = null;
            log.log(Level.INFO, "Segundo paso: Se debe validar que slot tiene disponible el usuario. NOTA: Por usuario unicamente hay 5 slots disponibles iniciando del 001 al 005");
            String[] slots = {"001", "002", "003", "004", "005"};
            for (String s : slots) {
                log.log(Level.INFO, "Tercer paso: Validar si el usuario tiene la ubicacion {0}", s);
                if (!ubicacionSAPFacade.validarUbicacionExiste("DM0101" + userSessionInfoMBean.getCodigoVentas() + s)) {
                    if (!crearUbicacionAsesor(s)) {
                        mostrarMensaje("Error", "No se pudo crear la demostración, comuniquese con el departamento de sistemas.", true, false, false);
                        return null;
                    }

                    slotDemo = s;
                    break;
                }

                if (saldoUbicacionFacade.validarSlotDisponible(userSessionInfoMBean.getCodigoVentas(), s)) {
                    slotDemo = s;
                    break;
                }
            }

            if (slotDemo == null || slotDemo.isEmpty()) {
                mostrarMensaje("Error", "La demostración no se puede crear, debido a que el asesor no tiene ubicaciones disponibles.", true, false, false);
                log.log(Level.SEVERE, "La demostracion no se puede crear, debido a que el asesor no tiene ubicaciones disponibles");
                return null;
            }

            log.log(Level.INFO, "Cuarto paso: Se hace el registro en la base de datos del encabezado de la demostracion");
            /**
             * Calcular la fecha fin de la demostracion
             */
            Date fechaFinDemo = fechaDemostracion;

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinDemo);
            cal.add(Calendar.DATE, 5);
            fechaFinDemo = cal.getTime();

            Demostracion demo = new Demostracion();

            demo.setAlias(nombreDemostracion.toUpperCase());
            demo.setAlmacen(userSessionInfoMBean.getAlmacen());
            demo.setCedulaCliente(ventasSessionMBean.getDocumentoCliente().contains("CL") ? ventasSessionMBean.getDocumentoCliente() : ventasSessionMBean.getDocumentoCliente() + "CL");
            demo.setCodigoAsesor(userSessionInfoMBean.getCodigoVentas());
            demo.setEstado("DP");
            demo.setFacturaAsociada(facturaAsociada);
            demo.setFechaCreacion(new Date());
            demo.setFechaFin(fechaFinDemo);
            demo.setFechaInicio(fechaDemostracion);
            demo.setUsuario(userSessionInfoMBean.getUsuario());
            demo.setSlotDemo(slotDemo);

            if (idDemostracion == null || idDemostracion == 0) {
                try {
                    demostracionFacade.create(demo);
                    log.log(Level.INFO, "Se creo la demostracion con id {0}", demo.getIdDemostracion());
                    idDemostracion = demo.getIdDemostracion();
                } catch (Exception e) {
                    mostrarMensaje("Error", "No fue posible crear la demostración, comuniquese con el departamento de sistemas.", true, false, false);
                    log.log(Level.SEVERE, "No se pudo hacer el registro en la base de datos web para la demostracion, el proceso no puede continuar.", e);
                    return null;
                }
            } else {
                log.log(Level.INFO, "Se encontro el id de la demostracion {0} y se trabajara sobre esta.", idDemostracion);
                demo.setIdDemostracion(idDemostracion);
            }

            log.log(Level.INFO, "Quinto paso: Se hace el traslado de la mercancia de los almacenes de ventas a los almacenes de demostraciones");
            asesorDemo = Integer.parseInt(userSessionInfoMBean.getCodigoVentas());
            if (!asignarAtributosDemostracion(idDemostracion, slotDemo, fechaFinDemo)) {
                mostrarMensaje("Error", "No se pudo procesar los datos de la demostración, comuníquese con el departamento de sistemas.", true, false, false);
                return null;
            }
            if (!crearTrasladoDemostracion(demo.getIdDemostracion(), slotDemo, fechaFinDemo, cotizacion.getDetalle())) {
                return null;
            }

            log.log(Level.INFO, "Sexto paso: Se marca la cotizacion como demostrada");
            CotizacionWeb cot = cotizacionWebFacade.find(cotizacion.getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null & cot.getIdCotizacion() != 0) {
                cot.setEstado("CD");
                if (cot.getNitCliente() == null || cot.getNitCliente().isEmpty()) {
                    cot.setNitCliente(ventasSessionMBean.getDocumentoCliente().contains("CL") ? ventasSessionMBean.getDocumentoCliente() : ventasSessionMBean.getDocumentoCliente() + "CL");
                }

                try {
                    cotizacionWebFacade.edit(cot);
                    log.log(Level.INFO, "Se marco la cotizacion [{0}] con el estado COTIZACION DEMOSTRADA", cotizacion.getIdCotizacion());
                    ventasSessionMBean.setExitoDemostracion(true);
                    dlgDemostracion = false;

                    mostrarMensaje("Éxito", "Demostración [" + demo.getIdDemostracion() + "] - " + demo.getAlias() + ", creada correctamente.", false, true, false);
                    log.log(Level.INFO, "Demostracion {0} - {1}, creada correctamente", new Object[]{idDemostracion, nombreDemostracion});

                    String[] s = generarDocumento(idDemostracion, 1, "[" + idDemostracion + "] " + nombreDemostracion.toUpperCase(),
                            "demostracion", userSessionInfoMBean.getAlmacen(), nombreDemostracion.toUpperCase(), imprimir, null);

                    notificarProceso(null, "demostracion", "Nueva", false, s);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al cambiar la cotizacion al estado COTIZACION DEMOSTRADA. ", e);
                    return "";
                }
            }
        } else {
            log.log(Level.SEVERE, "No se encontraron datos para la creacion de la demostracion");
            mostrarMensaje("Error", "No se encontraron datos para la creación de la demostración.", true, false, false);
            return "";
        }
        return "";
    }

    private boolean crearUbicacionAsesor(String slot) {
        /*Para crear las ubicaciones del asesor se deben seguir los siguientes pasos:
            1- Si la propiedad de piso (1) existe para el codigo de asesor, sino se debe crear
            2- Crear las ubicaciones
         */

        if (!subnivelUbicacionFacade.validarExistenciSubNivel(userSessionInfoMBean.getCodigoVentas())) {
            WarehouseSublevelCodesClient client = new WarehouseSublevelCodesClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            try {
                WarehouseSublevelCodesDTO dto = new WarehouseSublevelCodesDTO();

                dto.setCode(userSessionInfoMBean.getCodigoVentas());
                dto.setWarehouseSublevel(1);

                GenericRESTResponseDTO res = client.crearSubnivelUbicacion(userSessionInfoMBean.getUsuario(), dto);

                if (res.getEstado() <= 0) {
                    log.log(Level.SEVERE, "Ocurrio un error al crear la propiedad de ubicacion para el asesor");
                    return false;
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al crear la propiedad de ubicacion para el asesor. ", e);
                return false;
            }
        }

        List<String> almacenesDemo = almacenFacade.obtenerAlmacenesDemoSinUbicacionAsesor(userSessionInfoMBean.getCodigoVentas(), slot);

        if (almacenesDemo != null && !almacenesDemo.isEmpty()) {
            BinLocationsClient client = new BinLocationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            for (String s : almacenesDemo) {
                try {
                    BinLocationDTO dto = new BinLocationDTO();

                    dto.setAttribute1(true);
                    dto.setAttribute6(false);
                    dto.setAttribute9(false);
                    dto.setSublevel1(userSessionInfoMBean.getCodigoVentas());
                    dto.setSublevel2(slot);
                    dto.setWarehouse(s);

                    GenericRESTResponseDTO res = client.crearUbicacion(userSessionInfoMBean.getUsuario(), dto);

                    if (res.getEstado() <= 0) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear la ubicacion");
                        return false;
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al crear la ubicacion del asesor. ", e);
                    return false;
                }
            }
        }
        return true;
    }

    private boolean crearTrasladoDemostracion(Integer idDemoWeb, String slot, Date fechaFinDemo, List<DetalleCotizacionWebDTO> detalle) {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(userSessionInfoMBean.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            mostrarMensaje("Error", "No fue posible iniciar una sesion en SAP B1WS.", true, false, false);
            return false;
        }

        //Se crea el encabezado del traslado
        StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

        transfer.setCardCode(userSessionInfoMBean.getCedulaEmpleado() + "PR");
        transfer.setComments("Traslado demostración - Creada por asesor - WebService 360");
        transfer.setSalesPersonCode(Long.parseLong(userSessionInfoMBean.getCodigoVentas()));
        transfer.setSeries(193L);
        transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());

        int line = 0;
        for (DetalleCotizacionWebDTO d : detalle) {
            StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

            detailLine.setFromWarehouseCode(d.getBodega());
            detailLine.setItemCode(d.getReferencia());
            detailLine.setLineNum(line);
            detailLine.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));
            detailLine.setWarehouseCode("DM" + d.getBodega());
            detailLine.setBinAllocations(new ArrayList<StockTransferDocumentBinAllocationDTO>());

            /**
             * Se agrega el detalle a la demostracion en la base de datos
             */
            detalleDemostracionFacade.eliminarDetalleDemostracion(idDemostracion, d.getReferencia(), d.getBodega());

            DetalleDemostracion det = new DetalleDemostracion();

            det.setAlmacenDestino("DM" + d.getBodega());
            det.setAlmacenOrigen(d.getBodega());
            det.setCantidad(d.getCantidad());
            det.setIdDemostracion(new Demostracion(idDemoWeb));
            det.setReferencia(d.getReferencia());

            if (ubicacionSAPFacade.validarUbicacionesAlmacen(d.getBodega())) {
                boolean cantidadTotal = false;
                List<SaldoUbicacion> sds = saldoUbicacionFacade.findByItemCodeAndWhsCode(false, d.getReferencia(), d.getBodega());
                if (sds != null && !sds.isEmpty()) {
                    Integer cantidadNecesaria = d.getCantidad();
                    for (SaldoUbicacion saldo : sds) {
                        if (cantidadNecesaria > 0 && saldo.getUbicacion().getAttr1Val().equals("No")) {
                            StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();

                            allocationLineFrom.setAllowNegativeQuantity(false);
                            allocationLineFrom.setBaseLineNumber(line);
                            allocationLineFrom.setBinAbsEntry(saldo.getUbicacion().getAbsEntry().longValue());
                            allocationLineFrom.setBinActionType("salida");

                            if (saldo.getOnHandQty().intValue() >= cantidadNecesaria) {
                                allocationLineFrom.setQuantity(cantidadNecesaria.doubleValue());

                                detailLine.addBinAllocation(allocationLineFrom);
                                det.setUbicacionOrigen(saldo.getUbicacion().getBinCode() + "-");

                                cantidadNecesaria = 0;
                                cantidadTotal = true;
                                break;
                            } else {
                                cantidadNecesaria -= saldo.getOnHandQty().intValue();

                                allocationLineFrom.setQuantity(saldo.getOnHandQty().doubleValue());

                                detailLine.addBinAllocation(allocationLineFrom);
                                det.setUbicacionOrigen(saldo.getUbicacion().getBinCode() + "-");
                            }
                        }
                    }
                }

                if (!cantidadTotal) {
                    mostrarMensaje("Error", "No se encontró saldo suficiente de la referencia: " + d.getReferencia() + ", ubicado para crear la demostración.", true, false, false);
                    log.log(Level.SEVERE, "No se encontro saldo suficiente de la referencia: [{0}], ubicado para crear la demostracion", d.getReferencia());
                    return false;
                }

            }

            StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

            allocationLineTo.setAllowNegativeQuantity(false);
            allocationLineTo.setBaseLineNumber(line);
            allocationLineTo.setBinActionType("entrada");
            allocationLineTo.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));
            allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode("DM" + d.getBodega() + asesorDemo + slot).getAbsEntry().longValue());

            detailLine.addBinAllocation(allocationLineTo);
            det.setUbicacionDestino("DM" + d.getBodega() + asesorDemo + slot);

            transfer.addLine(detailLine);

            try {
                detalleDemostracionFacade.create(det);
                log.log(Level.INFO, "Se creo detalle demostracion con id {0}", det.getIdDetalleDemo());
            } catch (Exception e) {
                log.log(Level.SEVERE, "No se pudo crear el detalle de demostracion. Error {0}", e.getMessage());
            }
        }

        Integer numeroTraslado = crearTraslado(transfer);
        if (numeroTraslado > 0) {
            log.log(Level.INFO, "Se creo traslado de demostracion numero {0}", trasladosSAPFacade.find(numeroTraslado).getDocNum());
            return true;
        } else {
            return false;
        }
    }

    private boolean crearTrasladoVenta(String slot, String ubicacion, List<DetalleCotizacionWebDTO> detalle) {
        Integer traslado = null;
        StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

        transfer.setCardCode(userSessionInfoMBean.getCedulaEmpleado() + "PR");
        transfer.setComments("Traslado demostración - Creada por asesor - WebService 360");
        transfer.setSalesPersonCode(Long.parseLong(userSessionInfoMBean.getCodigoVentas()));
        transfer.setSeries(193L);

        int line = 0;
        if (detalle != null && !detalle.isEmpty()) {
            transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());
            for (DetalleCotizacionWebDTO d : detalle) {
                StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

                detailLine.setFromWarehouseCode(d.getBodega());
                detailLine.setItemCode(d.getReferencia());
                detailLine.setLineNum(line);
                detailLine.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));
                detailLine.setWarehouseCode(d.getBodega().replace("DM", ""));
                detailLine.setBinAllocations(new ArrayList<StockTransferDocumentBinAllocationDTO>());

                StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();

                allocationLineFrom.setAllowNegativeQuantity(false);
                allocationLineFrom.setBaseLineNumber(line);
                allocationLineFrom.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getBodega() + asesorDemo + slot).getAbsEntry().longValue());
                allocationLineFrom.setBinActionType("salida");
                allocationLineFrom.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));

                detailLine.addBinAllocation(allocationLineFrom);

                if (ubicacionSAPFacade.validarUbicacionesAlmacen(d.getBodega().replace("DM", ""))) {
                    StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

                    allocationLineTo.setAllowNegativeQuantity(false);
                    allocationLineTo.setBaseLineNumber(line);
                    allocationLineTo.setBinActionType("entrada");
                    allocationLineTo.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));
                    allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getBodega().replace("DM", "") + ubicacion).getAbsEntry().longValue());

                    detailLine.addBinAllocation(allocationLineTo);
                }

                transfer.addLine(detailLine);
            }

            traslado = crearTraslado(transfer);

            if (traslado != null && traslado > 0) {
                log.log(Level.INFO, "Se creo traslado con id {0}", traslado);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    private boolean asignarAtributosDemostracion(Integer idDemoWeb, String slot, Date fechaFinDemo) {
        /**
         * Se deben asignar a todas las ubicaciones los valores de atributos
         * asignados por el usuario
         */

        List<String> almacenesDemo = almacenFacade.obtenerAlmacenesDemoAsesor(asesorDemo.toString());

        if (almacenesDemo != null && !almacenesDemo.isEmpty()) {
            /*
                Se debe obtener y crear los atributos, antes de asignarlos a las ubicaciones
                    0. Tipo atributo
                    1. Id atributo
                    2. AbsEntry valor del atributo
                    3. Valor del atributo
             */

            List<Object[]> parametros = configuracionAtributoUbicacionFacade.obtenerDatosAtributo(nombreDemostracion.toUpperCase(),
                    new SimpleDateFormat("yyyy-MM-dd").format(fechaDemostracion),
                    new SimpleDateFormat("yyyy-MM-dd").format(fechaFinDemo),
                    ventasSessionMBean.getDocumentoCliente().contains("CL") ? ventasSessionMBean.getDocumentoCliente() : ventasSessionMBean.getDocumentoCliente() + "CL",
                    idDemoWeb.toString(),
                    facturaAsociada == null || facturaAsociada == 0 ? "" : facturaAsociada.toString());

            if (parametros != null && !parametros.isEmpty()) {
                BinLocationAttributesClient client = new BinLocationAttributesClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                for (Object[] p : parametros) {
                    if ((Integer) p[2] == null) {
                        try {
                            BinLocationAttributesDTO dto = new BinLocationAttributesDTO();

                            dto.setAttributo((Integer) p[1]);
                            dto.setCode((String) p[3]);

                            GenericRESTResponseDTO res = client.crearAtributoUbicacion(userSessionInfoMBean.getUsuario(), dto);

                            if (res.getEstado() <= 0) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el atributo para la ubicacion");
                                return false;
                            }
                            p[2] = res.getValor();
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al crear el atributo para la ubicacion. ", e);
                            return false;
                        }
                    }
                }
            }

            BinLocationsClient client = new BinLocationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
            for (String a : almacenesDemo) {
                UbicacionSAP ubicacion = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(a + asesorDemo + slot);

                if (ubicacion != null && ubicacion.getAbsEntry() != null && ubicacion.getAbsEntry() != 0) {
                    try {
                        BinLocationDTO dto = new BinLocationDTO();

                        dto.setAbsEntry(ubicacion.getAbsEntry());
                        dto.setAttribute1(ubicacion.getAttr1Val() != null && ubicacion.getAttr1Val().equals("Sí") ? true : false);
                        dto.setAttribute6(ubicacion.getAttr6Val() != null && ubicacion.getAttr6Val().equals("Sí") ? true : false);
                        dto.setAttribute9(ubicacion.getAttr9Val() != null && ubicacion.getAttr9Val().equals("Sí") ? true : false);
                        dto.setSublevel1(ubicacion.getSL1Code());
                        dto.setSublevel2(ubicacion.getSL2Code());
                        dto.setWarehouse(ubicacion.getWhsCode());
                        dto.setAttribute2((String) parametros.get(0)[3]);
                        dto.setAttribute3((String) parametros.get(1)[3]);
                        dto.setAttribute4((String) parametros.get(2)[3]);
                        dto.setAttribute5((String) parametros.get(3)[3]);
                        dto.setAttribute7((String) parametros.get(4)[3]);
                        dto.setAttribute8((String) parametros.get(5)[3]);

                        GenericRESTResponseDTO res = client.editarUbicacion(userSessionInfoMBean.getUsuario(), dto);
                        if (res.getEstado() <= 0) {
                            log.log(Level.SEVERE, "Ocurrio un error al crear la ubicacion del asesor");
                            return false;
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear la ubicacion del asesor. ", e);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Integer crearTraslado(StockTransferDocumentDTO transfer) {
        StockTransferClient client = new StockTransferClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        GenericRESTResponseDTO res = client.crearStockTransfer(transfer);
        if (res.getEstado() <= 0) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el traslado para la demostracion");
            return null;
        }
        log.log(Level.INFO, "Se creo traslado de demostracion numero {0}", trasladosSAPFacade.find(res.getValor()).getDocNum());
        return res.getValor();
    }

    public void seleccionarFactura() {
        facturaAsociada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("factura"));
        log.log(Level.INFO, "Se selecciono la factura asociada {0} para la demostracion", facturaAsociada);
    }

    public void cancelarProcesoDemo() {
        facturaAsociada = null;
        idDemostracion = null;
        asesorDemo = null;
        nombreDemostracion = null;
        comentarioDemostracion = null;
        slot = null;
        dlgDemostracion = false;
        imprimir = false;
        enviar = false;
        fechaDemostracion = null;
        facturas = new ArrayList<>();
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setDemostracion(false);
        ventasSessionMBean.setExitoDemostracion(false);
        ventasSessionMBean.setIdDemostracion(null);
        limpiarSesionCliente();
    }

    private void asignarSaldosDemostracion() {
        //[0] Codigo asesor
        //[1] Slot
        //[2] Nombre demostracion
        //[3] Fecha demostracion
        //[4] Fecha vencimiento
        //[5] Documento cliente
        //[6] Id demostracion
        //[7] Factura asociada
        Object[] datosDemo = ubicacionSAPFacade.obtenerDemoXId(ventasSessionMBean.getIdDemostracion());

        if (datosDemo != null) {
            nombreDemostracion = (String) datosDemo[2];
            idDemostracion = ventasSessionMBean.getIdDemostracion();
            slot = (String) datosDemo[1];
            fechaDemostracion = (Date) datosDemo[3];
            facturaAsociada = (Integer) datosDemo[7];
            asesorDemo = (Integer) datosDemo[0];
            ventasSessionMBean.setDocumentoCliente((String) datosDemo[5]);

            CotizacionWeb cot = cotizacionWebFacade.obtenerCotizacionesDemo(ventasSessionMBean.getIdDemostracion());

            if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
                cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                cotizacion.setDemostracion(cot.getDemostracion() != null ? cot.getDemostracion() : false);
            } else {
                cot = new CotizacionWeb();

                cot.setDemostracion(true);
                cot.setFecha(new Date());
                cot.setIdDemostracion(new Demostracion(ventasSessionMBean.getIdDemostracion()));
                cot.setEstado("CD");
                cot.setIdVendedor((Integer) datosDemo[0]);
                cot.setNitCliente((String) datosDemo[5]);
                cot.setSucursal(userSessionInfoMBean.getAlmacen());
                cot.setUsuario(userSessionInfoMBean.getUsuario());

                try {
                    cotizacionWebFacade.create(cot);
                    log.log(Level.INFO, "Se creo cotizacion web con id {0} para la demostracion con id {1}", new Object[]{cot.getIdCotizacion(), idDemostracion});
                    cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "No se pudo hacer el registro de la cotizacion web para la demostracion", e);
                    mostrarMensaje("Error", "No se pudieron traer los datos de la demostración seleccionada.", true, false, false);
                    return;
                }
            }

            List<SaldoUbicacion> balances = saldoUbicacionFacade.obtenerSaldosDemostracion(((Integer) datosDemo[0]).toString(), (String) datosDemo[1]);
            saldosAcumulados = new ArrayList<>();

            if (balances != null && !balances.isEmpty()) {
                detalleCotizacionWebFacade.eliminarDetalleCotizacion(cot.getIdCotizacion());

                for (SaldoUbicacion s : balances) {
                    DetalleCotizacionWeb det = new DetalleCotizacionWeb();

                    det.setBodega(s.getWhsCode());
                    det.setCantidad(s.getOnHandQty().intValue());
                    det.setIdCotizacion(new CotizacionWeb(cot.getIdCotizacion()));
                    det.setReferencia(s.getItemCode());

                    try {
                        detalleCotizacionWebFacade.create(det);
                        log.log(Level.INFO, "Se creo detalle con id {0} para la cotizacion {1} para la demostracion con id {2}", new Object[]{det.getIdDetalleCotizacion(), cot.getIdCotizacion(), idDemostracion});
                        cotizacion.getDetalle().add(new DetalleCotizacionWebDTO(det.getCantidad(), cot.getIdCotizacion().intValue(), null, det.getIdDetalleCotizacion(), det.getReferencia(), det.getBodega()));
                        saldosAcumulados.add(0, new SaldoItemDTO(det.getCantidad(), det.getCantidad(), det.getReferencia(), det.getBodega()));
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear el detalle", e);
                        return;
                    }
                }
            }
        }
    }

    public void modificaDemostracion() {
        if (!dlgDemostracion) {
            dlgDemostracion = true;
            facturas = facturaSAPFacade.obtenerFacturasPendientes(ventasSessionMBean.getDocumentoCliente());
            return;
        }

        log.log(Level.INFO, "Modificando demostacion [{0}] {1}", new Object[]{idDemostracion, nombreDemostracion});
        List<SaldoUbicacion> balances = saldoUbicacionFacade.obtenerSaldosDemostracion(asesorDemo.toString(), slot);

        if (balances != null && !balances.isEmpty()) {
            Map<String, Integer> b = new HashMap<>();

            for (SaldoUbicacion s : balances) {
                b.put(s.getItemCode() + "-" + s.getWhsCode(), s.getOnHandQty().intValue());

            }
            List<DetalleCotizacionWebDTO> detalles = new ArrayList<>(cotizacion.getDetalle());
            List<DetalleCotizacionWebDTO> modificar = new ArrayList<>();
            List<DetalleCotizacionWebDTO> agregar = new ArrayList<>();

            for (int i = 0; i < detalles.size(); i++) {
                DetalleCotizacionWebDTO detalle = detalles.get(i);

                if (b.containsKey(detalle.getReferencia() + "-" + detalle.getBodega())) {
                    if (b.get(detalle.getReferencia() + "-" + detalle.getBodega()) < detalle.getCantidad()) {
                        detalle.setCantidad(detalle.getCantidad() - b.get(detalle.getReferencia() + "-" + detalle.getBodega()));
                        agregar.add(detalle);
                    } else if (b.get(detalle.getReferencia() + "-" + detalle.getBodega()) > detalle.getCantidad()) {
                        detalle.setCantidad(b.get(detalle.getReferencia() + "-" + detalle.getBodega()) - detalle.getCantidad());
                        modificar.add(detalle);
                    }
                    detalles.remove(i);
                    b.remove(detalle.getReferencia() + "-" + detalle.getBodega());
                    i--;
                } else {
                    agregar.add(detalle);
                    detalles.remove(i);
                    b.remove(detalle.getReferencia() + "-" + detalle.getBodega());
                    i--;
                }
            }

            if (b != null && !b.isEmpty()) {
                for (Map.Entry<String, Integer> m : b.entrySet()) {
                    String[] s = m.getKey().split("-");

                    detalles.add(new DetalleCotizacionWebDTO(m.getValue(), cotizacion.getIdCotizacion().intValue(), null, null, s[0], s[1]));
                }
            }

            /**
             * Calcular la fecha fin de la demostracion
             */
            Date fechaFinDemo = fechaDemostracion;

            Calendar cal = Calendar.getInstance();
            cal.setTime(fechaFinDemo);
            cal.add(Calendar.DATE, 5);
            fechaFinDemo = cal.getTime();
            if (!asignarAtributosDemostracion(idDemostracion, slot, fechaFinDemo)) {
                mostrarMensaje("Error", "No se pudo procesar los datos de la demostración, comuníquese con el departamento de sistemas.", true, false, false);
                return;
            }

            if (agregar != null && !agregar.isEmpty()) {
                if (!crearTrasladoDemostracion(idDemostracion, slot, fechaDemostracion, agregar)) {
                    mostrarMensaje("Error", "No fue posible crear el traslado con las nuevas referencias para la demostración.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible crear el traslado con las nuevas referencias para la demostracion");
                    return;
                }
                log.log(Level.INFO, "Se agregaron {0} referencias a la demostracion", agregar.size());
            } else {
                log.log(Level.INFO, "No se agregaron nuevas referencias a la demostracion [{0}] {1}", new Object[]{idDemostracion, nombreDemostracion});
            }
            if (modificar != null && !modificar.isEmpty()) {
                if (!crearTrasladoVenta(slot, "TM", modificar)) {
                    mostrarMensaje("Error", "No fue posible crear el traslado con las referencias modificadas de la demostración.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible crear el traslado con las referencias modificadas de la demostracion");
                    return;
                }
                log.log(Level.INFO, "Se modificaron {0} referencias de la demostracion", modificar.size());
            } else {
                log.log(Level.INFO, "No se modificaron referencias de la demostracion [{0}] {1}", new Object[]{idDemostracion, nombreDemostracion});
            }
            if (detalles != null && !detalles.isEmpty()) {
                if (!crearTrasladoVenta(slot, "TM", detalles)) {
                    mostrarMensaje("Error", "No fue posible crear el traslado con las referencias eliminadas de la demostración.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible crear el traslado con las referencias eliminadas de la demostracion");
                    return;
                }
                log.log(Level.INFO, "Se eliminaron {0} referencias de la demostracion", detalles.size());
            } else {
                log.log(Level.INFO, "No se eliminaron referencias de la demostracion [{0}] {1}", new Object[]{idDemostracion, nombreDemostracion});
            }
        }

        dlgDemostracion = false;
        ventasSessionMBean.setExitoDemostracion(true);
        mostrarMensaje("Éxito", "Demostración [" + idDemostracion + "] - " + nombreDemostracion + ", modificada correctamente.", false, true, false);

        String[] s = s = generarDocumento(idDemostracion, 1, "[" + idDemostracion + "] " + nombreDemostracion.toUpperCase(),
                "demostracion", userSessionInfoMBean.getAlmacen(), nombreDemostracion.toUpperCase(), imprimir, null);

        notificarProceso(null, "demostracion", "Modificada", false, s);
    }

    public void facturarDemostracion() {
        if (!dlgFacturarDemo) {
            ventasSessionMBean.setClienteVerificado(false);
            ventasSessionMBean.setFacturando(false);
            dlgFacturarDemo = true;
            return;
        }

        for (int i = 0; i < cotizacion.getDetalle().size(); i++) {
            DetalleCotizacionWebDTO detalle = cotizacion.getDetalle().get(i);

            if (!detalle.getBodega().contains("DM")) {
                cotizacion.getDetalle().remove(i);
                i--;
            }
        }

        if (!crearTrasladoVenta(slot, "DEMO", cotizacion.getDetalle())) {
            mostrarMensaje("Error", "No fue posible preparar la demostración para facturarla.", true, false, false);
            log.log(Level.SEVERE, "No fue posible preparar la demostracion para facturarla");
            return;
        } else {
            notificarProceso(null, "demostracion", "Facturando", false, null);
        }

        ventaCorrompida = true;
        Integer doc = cotizar();
        if (doc != null && doc != 0) {
            for (DetalleCotizacionWebDTO d : cotizacion.getDetalle()) {
                d.setBodega(d.getBodega().replace("DM", ""));
            }

            for (SaldoItemDTO s : saldosAcumulados) {
                s.setAlmacen(s.getAlmacen().replace("DM", ""));
            }

            CotizacionWeb cot = cotizacionWebFacade.find(cotizacion.getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null & cot.getIdCotizacion() != 0) {
                cot.setEstado("CD");
                cot.setNumeroDocSAP(doc.toString());
                cot.setDemostracion(true);

                try {
                    cotizacionWebFacade.edit(cot);
                    ventasSessionMBean.setNumeroCotizacion(Long.parseLong(cot.getNumeroDocSAP()));
                    cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), null);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "", e);
                }
            }

            mostrarMensaje("", "Elimine los ítems y las cantidades que el cliente no dejo y de nuevamente clic en facturar.", false, false, true);
            log.log(Level.WARNING, "Se deben eliminar los items y las cantidades que el cliente no dejo y de nuevamente clic en facturar");
            return;
        }
    }

    public Integer cotizar() {
        /*Se crea el encabezado de la cotizacion*/
        QuotationsDocumentDTO quotation = new QuotationsDocumentDTO();

        quotation.setCardCode(ventasSessionMBean.getDocumentoCliente());
        quotation.setSalesPersonCode(asesorDemo.longValue());

        if (cotizacion != null && cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
            for (DetalleCotizacionWebDTO detail : cotizacion.getDetalle()) {
                QuotationsDocumentLinesDTO detailLine = new QuotationsDocumentLinesDTO();

                detailLine.setItemCode(detail.getReferencia());
                detailLine.setQuantity(detail.getCantidad());
                detailLine.setWarehouseCode(detail.getBodega());

                quotation.getQuotationsDocumentLines().add(detailLine);
            }
        } else {
            log.log(Level.SEVERE, "No se puede crear la cotizacion debido a que no se encontraron datos");
            mostrarMensaje("Error", "No se puede crear la cotización debido a que no se encontraron datos.", true, false, false);
            return null;
        }

        try {
            QuotationsClient client = new QuotationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            GenericRESTResponseDTO res = client.createQuotation(quotation);

            if (res.getEstado() > 0) {
                log.log(Level.INFO, "Se creo la cotizacion con numero: [{0}]", res.getValor());
                return res.getValor();
            } else {
                mostrarMensaje("Error", res.getMensaje(), true, false, false);
                log.log(Level.SEVERE, res.getMensaje());
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
            return null;
        }
    }

    public void cancelarFacturarDemo() {
        dlgFacturarDemo = false;
    }

    /**
     * ****INICIA BLOQUE GENERAL Y FINALIZA BLOQUE DE DEMOSTRACIONES*****
     */
    public void limpiarDatosSaldo() {
        referencia = null;
        almacen = null;
        dlgSaldos = false;
        saldos = new ArrayList<>();
    }

    private void notificarProceso(Integer documento, String tipo, String estado, boolean cliente, String[] adjunto) {
        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            if (adjunto != null) {
                adjuntos.add(adjunto);
            }
            switch (tipo) {
                case "cotizacion":
                    if (cliente) {
                        SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cotizacion.getNitCliente());

                        if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty() && socio.getEmail() != null && !socio.getEmail().isEmpty()) {
                            /*Se escribe el nombre del cliente*/
                            String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                                    .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                            params.put("documento", "cotizaci&oacute;n");
                            params.put("tipo", "acaba de realizar en nuestra tienda");
                            params.put("cliente", nombreCliente);

                            res = client.enviarHtmlEmail("Cotización matisses", "Cotización Matisses " + ventasSessionMBean.getNumeroCotizacion(),
                                    socio.getEmail(), "venta", adjuntos, params);
                        }
                    }
                    break;
                case "demostracion":
                    if (cliente) {
                        SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cotizacion.getNitCliente());

                        if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty() && socio.getEmail() != null && !socio.getEmail().isEmpty()) {
                            /*Se escribe el nombre del cliente*/
                            String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                                    .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                            params.put("documento", "demostraci&oacute;n");
                            if (ventasSessionMBean.isModificando() && ventasSessionMBean.getIdDemostracion() != null && ventasSessionMBean.getIdDemostracion() != 0) {
                                params.put("tipo", "se actualizo con los &iacute;tems modificados");
                            } else {
                                params.put("tipo", "acaba de realizar en nuestra tienda");
                            }
                            params.put("cliente", nombreCliente);

                            res = client.enviarHtmlEmail("Demostración Matisses", "Demostración [" + idDemostracion + "] " + nombreDemostracion.toUpperCase(),
                                    socio.getEmail(), "venta", adjuntos, params);
                        }
                    } else {
                        params.put("empleado", baruGenericMBean.obtenerNombreAsesor(asesorDemo));
                        params.put("nombreDemostracion", "[" + idDemostracion + "] " + nombreDemostracion.toUpperCase());
                        params.put("sucursal", userSessionInfoMBean.getAlmacen());
                        params.put("fecha", new SimpleDateFormat("dd-MM-yyyy").format(fechaDemostracion));
                        params.put("direccion", socioDeNegociosFacade.obtenerDireccionEntrgaCliente(ventasSessionMBean.getDocumentoCliente()));
                        params.put("factura", facturaAsociada != null && facturaAsociada != 0 ? facturaAsociada.toString() : "No aplica");
                        params.put("comentario", comentarioDemostracion != null && !comentarioDemostracion.isEmpty() ? comentarioDemostracion : "No aplica");
                        params.put("estado", estado);
                        params.put("usuario", userSessionInfoMBean.getUsuario().toUpperCase());

                        res = client.enviarHtmlEmail("Demostración", "Demostración [" + idDemostracion + "] " + nombreDemostracion.toUpperCase() + " ***** " + estado.toUpperCase(),
                                applicationMBean.getDestinatariosPlantillaEmail().get("demostracion").getTo().get(0), "demostracion", adjuntos, params);
                    }
                    break;
                case "factura":
                    params.put("invoiceNumber", documento.toString());
                    params.put("customerName", clienteSessionMBean.getClienteDto().getNombres() + " " + clienteSessionMBean.getClienteDto().getApellido1());
                    params.put("customerId", clienteSessionMBean.getClienteDto().getNit());
                    params.put("username", userSessionInfoMBean.getUsuario());
                    params.put("url", applicationMBean.obtenerValorPropiedad("mail.rute.invoiceDetail"));
                    if (tipo.equals("factura")) {
                        res = client.enviarHtmlEmail("Facturas Matisses", "Facturas Matisses", applicationMBean.getDestinatariosPlantillaEmail().get("factura").getTo().get(0), "factura", null, params);
                    }
                    if (tipo.equals("cruce")) {
                        params.put("valorCruce", baruGenericMBean.getValorFormateado("Double", valorCruce.toString(), 2));

                        res = client.enviarHtmlEmail("Facturas Matisses", "Facturas Matisses", applicationMBean.getDestinatariosPlantillaEmail().get("reconciliar_factura").getTo().get(0),
                                "reconciliar_factura", null, params);
                    }
                    break;
                default:
                    break;
            }

            log.log(Level.INFO, res.getMensaje());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion. ", e);
        }
    }

    private String[] generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), nombreArchivo + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    public String abrirPdf() {
        String documento = "";
        if (ventasSessionMBean.isExitoCotizacion()) {
            documento = "cotizacion";
        }
        if (ventasSessionMBean.isExitoDemostracion()) {
            documento = "demostracion";
        }
        if (ventasSessionMBean.isFacturando()) {
            documento = "factura";
        }
        if (ventasSessionMBean.getNumeroCotizacion() != null && ventasSessionMBean.getNumeroCotizacion() != 0) {
            String[] adjunto = generarDocumento(Integer.parseInt(String.valueOf(ventasSessionMBean.getNumeroCotizacion())), 1, String.valueOf(ventasSessionMBean.getNumeroCotizacion()),
                    documento, userSessionInfoMBean.getAlmacen(), null, false, null);

            if (adjunto != null) {
                if (new File(adjunto[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.ventas") + documento + "/" + ventasSessionMBean.getNumeroCotizacion();
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "No se pudo generar la URL para el documento");
                        return "";
                    }
                } else {
                    log.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return "";
            }
        } else {
            log.log(Level.SEVERE, "No se pudo generar el documento");
            return "";
        }
    }

    private void limpiar() {
        posicionItem = 0;
        asesores = 0;
        medioPago = 0;
        pasosPago = 1;
        condicionPago = null;
        pasosFacturacion = 1;
        asesorDemo = null;
        banco = null;
        pagoTarjeta = null;
        totalFactura = null;
        valorPendienteFactura = null;
        valorPagoPendiente = null;
        valorEfectivo = null;
        valorTarjeta = null;
        saldoFavor = null;
        valorCruce = null;
        referencia = null;
        almacen = null;
        comentarioPagoPendiente = null;
        franquicia = null;
        tarjeta = null;
        voucher = null;
        digitos = null;
        datafono = null;
        tabAsesor = null;
        decorador = null;
        comentarioFactura = null;
        slot = null;
        dlgSaldos = false;
        dlgConfirmarEliminacion = false;
        dlgCrearFactura = false;
        imprimir = false;
        enviar = false;
        ventaCorrompida = false;
        cotizacion = new CotizacionWebDTO();
        detalleVenta = new DetalleCotizacionWebDTO();
        franquicias = new ArrayList<>();
        imagenes = new ArrayList<>();
        saldos = new ArrayList<>();
        saldosAcumulados = new ArrayList<>();
        autorizadoresPagoFactura = new ArrayList<>();
        condicionesPago = new ArrayList<>();
        autorizaciones = new ArrayList<>();
        bancos = new ArrayList<>();
        datafonos = new ArrayList<>();
        pagosTarjetas = new ArrayList<>();
        decoradores = new ArrayList<>();
        empleadosComision = new HashMap<>();
        limpiarSesionCliente();
        limpiarSesionVenta();
    }

    private void limpiarSesionCliente() {
        clienteSessionMBean.setCiudadBusqueda(null);
        clienteSessionMBean.setClienteDto(new ClienteSAPDTO());
        clienteSessionMBean.setDireccionCliente(new DireccionesClienteDTO());
    }

    private void limpiarSesionVenta() {
        ventasSessionMBean.setArticulosFacturacion(null);
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setComentarioFactura(null);
        ventasSessionMBean.setCotizacion(null);
        ventasSessionMBean.setCotizacionCorrompida(false);
        ventasSessionMBean.setDecorador(null);
        ventasSessionMBean.setDemostracion(false);
        ventasSessionMBean.setDocumentoCliente(null);
        ventasSessionMBean.setEmpleadosVenta(null);
        ventasSessionMBean.setExitoCotizacion(false);
        ventasSessionMBean.setExitoDemostracion(false);
        ventasSessionMBean.setFacturando(false);
        ventasSessionMBean.setModificando(false);
        ventasSessionMBean.setNumeroCotizacion(null);
        ventasSessionMBean.setIdDemostracion(null);
    }

    public boolean mostrarModificar() {
        if (ventaCorrompida || (idDemostracion != null && idDemostracion != 0 && !userSessionInfoMBean.validarPermisoUsuario(Objetos.DEMOSTRACION, Acciones.MODIFICAR))) {
            return false;
        } else {
            return true;
        }
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
