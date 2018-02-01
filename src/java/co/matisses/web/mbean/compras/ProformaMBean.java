package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.BancoCompras;
import co.matisses.persistence.web.entity.Ciudad;
import co.matisses.persistence.web.entity.ColumnaProforma;
import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.entity.ContenedorLinea;
import co.matisses.persistence.web.entity.ContenedorProforma;
import co.matisses.persistence.web.entity.ContenedorProveedor;
import co.matisses.persistence.web.entity.CuentaBancariaProveedor;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.entity.DetalleOperacionColumna;
import co.matisses.persistence.web.entity.DetalleProforma;
import co.matisses.persistence.web.entity.EncabezadoExcel;
import co.matisses.persistence.web.entity.Estados;
import co.matisses.persistence.web.entity.Incoterm;
import co.matisses.persistence.web.entity.LineaIgnoradaProforma;
import co.matisses.persistence.web.entity.NotificacionProforma;
import co.matisses.persistence.web.entity.Paises;
import co.matisses.persistence.web.entity.ProformaInvoice;
import co.matisses.persistence.web.entity.PuertoMundo;
import co.matisses.persistence.web.entity.SucursalBancoCompras;
import co.matisses.persistence.web.entity.TipoMoneda;
import co.matisses.persistence.web.entity.TransaccionBancaria;
import co.matisses.persistence.web.entity.VersionTransaccionBancaria;
import co.matisses.persistence.web.facade.AnticipoCuentaFacade;
import co.matisses.persistence.web.facade.BancoComprasFacade;
import co.matisses.persistence.web.facade.CiudadFacade;
import co.matisses.persistence.web.facade.ColumnaProformaFacade;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import co.matisses.persistence.web.facade.ContenedorLineaFacade;
import co.matisses.persistence.web.facade.ContenedorProformaFacade;
import co.matisses.persistence.web.facade.ContenedorProveedorFacade;
import co.matisses.persistence.web.facade.CuentaBancariaProveedorFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.DetalleOperacionColumnaFacade;
import co.matisses.persistence.web.facade.DetalleProformaFacade;
import co.matisses.persistence.web.facade.EncabezadoExcelFacade;
import co.matisses.persistence.web.facade.EstadosFacade;
import co.matisses.persistence.web.facade.IncotermFacade;
import co.matisses.persistence.web.facade.LineaIgnoradaProformaFacade;
import co.matisses.persistence.web.facade.NotificacionProformaFacade;
import co.matisses.persistence.web.facade.PaisesFacade;
import co.matisses.persistence.web.facade.ProformaInvoiceFacade;
import co.matisses.persistence.web.facade.PuertoMundoFacade;
import co.matisses.persistence.web.facade.SucursalBancoComprasFacade;
import co.matisses.persistence.web.facade.TipoMonedaFacade;
import co.matisses.persistence.web.facade.TransaccionBancariaFacade;
import co.matisses.persistence.web.facade.VersionTransaccionBancariaFacade;
import co.matisses.web.dto.BancoComprasDTO;
import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.dto.ColumnaDatosProformaDTO;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.ContenedorDTO;
import co.matisses.web.dto.ContenedorLineaDTO;
import co.matisses.web.dto.ContenedorProformaDTO;
import co.matisses.web.dto.ContenedorProveedorDTO;
import co.matisses.web.dto.CuentaBancariaProveedorDTO;
import co.matisses.web.dto.DatosProformaDTO;
import co.matisses.web.dto.DetalleProformaDTO;
import co.matisses.web.dto.EncabezadoExcelDTO;
import co.matisses.web.dto.EstadosDTO;
import co.matisses.web.dto.GaleriaDTO;
import co.matisses.web.dto.IncotermDTO;
import co.matisses.web.dto.ItemDisponiblesAsignacionDTO;
import co.matisses.web.dto.LineaProformaDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.PaisesDTO;
import co.matisses.web.dto.ProformaInvoiceDTO;
import co.matisses.web.dto.PuertoMundoDTO;
import co.matisses.web.dto.SucursalBancoComprasDTO;
import co.matisses.web.dto.TipoDatosDTO;
import co.matisses.web.dto.TipoMonedaDTO;
import co.matisses.web.dto.TransaccionBancariaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import co.matisses.web.mbean.session.ProformaSessionMBean;
import co.matisses.web.poi.ExcelGeneratorProforma;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "proformaMBean")
public class ProformaMBean implements Serializable {

    @Inject
    private ProformaSessionMBean proformaSessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private SendHTMLEmailMBean emailSender;
    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    private static final Logger log = Logger.getLogger(ProformaMBean.class.getSimpleName());
    private int itemActivo;
    private int itemsDisponibles;
    private Integer idProforma;
    private Integer idColumnaSeleccionada;
    private Integer idColumnaAsociada;
    private Integer idContenedorSeleccionado;
    private Integer idCuentaBancaria;
    private Integer pasoCrearCuenta;
    private Integer idBancoCuenta;
    private Integer idPais;
    private Integer idEstado;
    private Integer idCiudad;
    private Integer idSucursalBanco;
    private Integer idCuentaSucursal;
    private Integer idMoneda;
    private Integer idTransaccion;
    private Integer tipoDescarga;
    private Integer pasoCerrarPI;
    private Integer idPaisPuertoSalida;
    private Double porcentajeAnticipo;
    private Double totalGirar;
    private Double cbmMinimo;
    private Double cbmMaximo;
    private String parametroBusqueda;
    private String orderBy = "Seleccione";
    private String sortOrder = "ASC";
    private String imgVista;
    private String campoSeleccionado;
    private String nuevoValorCampoSeleccionado;
    private String tipoGiro;
    private String anticipo;
    private String balance;
    private String razonSocialBanco;
    private String direccionSucursal;
    private String swiftSucursal;
    private String cuentaBancaria;
    private String ibanCuentaBancaria;
    private String abaCuentaBancaria;
    private String descripcionTransaccion;
    private String valorMostrar;
    private boolean crearColumna = false;
    private boolean elegirColumna;
    private boolean dlgCrearCuenta = false;
    private boolean dlgMoneda = false;
    private boolean dlgCargaSuelta = false;
    private boolean contenedoresAsignados = true;
    private HtmlInputText inputTextEspecial;
    private HtmlInputText inputTextGeneral;
    private Part documentoTransaccion;
    private ProformaInvoiceDTO proforma;
    private DatosProformaDTO datosProforma;
    private ColumnasProformaMBean columnasProformaMBean;
    private LineaProformaDTO lineaProforma;
    private List<Integer> itemsPaginacion;
    private List<ProformaInvoiceDTO> proformas;
    private List<ProformaInvoiceDTO> proformasFull;
    private List<GaleriaDTO> galeria;
    private List<ItemDisponiblesAsignacionDTO> items;
    private List<ItemDisponiblesAsignacionDTO> camposCrear;
    private List<ColumnaProformaDTO> columnas;
    private List<ContenedorProveedorDTO> contenedores;
    private List<ContenedorProformaDTO> contenedoresProforma;
    private List<CuentaBancariaProveedorDTO> cuentasProveedor;
    private List<BancoComprasDTO> bancos;
    private List<PaisesDTO> paises;
    private List<EstadosDTO> estados;
    private List<CiudadDTO> ciudades;
    private List<SucursalBancoComprasDTO> sucursalesBanco;
    private List<CuentaBancariaProveedorDTO> cuentas;
    private List<TipoMonedaDTO> monedas;
    private List<TransaccionBancariaDTO> transacciones;
    private List<ContenedorProformaDTO> contenedoresDescarga;
    private List<PuertoMundoDTO> puertosColombianos;
    private List<PuertoMundoDTO> puertosProveedor;
    private List<IncotermDTO> incoterms;
    @EJB
    private ProformaInvoiceFacade proformaInvoiceFacade;
    @EJB
    private DetalleProformaFacade detalleProformaFacade;
    @EJB
    private DetalleOperacionColumnaFacade detalleOperacionColumnaFacade;
    @EJB
    private LineaIgnoradaProformaFacade lineaIgnoradaProformaFacade;
    @EJB
    private ColumnaProformaFacade columnaProformaFacade;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;
    @EJB
    private ContenedorProveedorFacade contenedorProveedorFacade;
    @EJB
    private ContenedorProformaFacade contenedorProformaFacade;
    @EJB
    private ContenedorLineaFacade contenedorLineaFacade;
    @EJB
    private CuentaBancariaProveedorFacade cuentaBancariaProveedorFacade;
    @EJB
    private BancoComprasFacade bancoComprasFacade;
    @EJB
    private PaisesFacade paisesFacade;
    @EJB
    private EstadosFacade estadosFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    @EJB
    private SucursalBancoComprasFacade sucursalBancoComprasFacade;
    @EJB
    private AnticipoCuentaFacade anticipoCuentaFacade;
    @EJB
    private TransaccionBancariaFacade transaccionBancariaFacade;
    @EJB
    private TipoMonedaFacade tipoMonedaFacade;
    @EJB
    private VersionTransaccionBancariaFacade versionTransaccionBancariaFacade;
    @EJB
    private EncabezadoExcelFacade encabezadoExcelFacade;
    @EJB
    private DatosProveedorFacade datosProveedorFacade;
    @EJB
    private PuertoMundoFacade puertoMundoFacade;
    @EJB
    private IncotermFacade incotermFacade;
    @EJB
    private NotificacionProformaFacade notificacionProformaFacade;

    public ProformaMBean() {
        pasoCrearCuenta = 1;
        pasoCerrarPI = 1;
        elegirColumna = true;
        proforma = new ProformaInvoiceDTO();
        datosProforma = new DatosProformaDTO();
        columnasProformaMBean = new ColumnasProformaMBean();
        lineaProforma = new LineaProformaDTO();
        itemsPaginacion = new ArrayList<>();
        proformas = new ArrayList<>();
        proformasFull = new ArrayList<>();
        galeria = new ArrayList<>();
        items = new ArrayList<>();
        camposCrear = new ArrayList<>();
        columnas = new ArrayList<>();
        contenedores = new ArrayList<>();
        contenedoresProforma = new ArrayList<>();
        cuentasProveedor = new ArrayList<>();
        bancos = new ArrayList<>();
        paises = new ArrayList<>();
        estados = new ArrayList<>();
        ciudades = new ArrayList<>();
        sucursalesBanco = new ArrayList<>();
        cuentas = new ArrayList<>();
        monedas = new ArrayList<>();
        transacciones = new ArrayList<>();
        contenedoresDescarga = new ArrayList<>();
        puertosColombianos = new ArrayList<>();
        puertosProveedor = new ArrayList<>();
        incoterms = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerPaises();
        obtenerProformas();
        cargarInfoSession();
    }

    public ProformaSessionMBean getProformaSessionMBean() {
        return proformaSessionMBean;
    }

    public void setProformaSessionMBean(ProformaSessionMBean proformaSessionMBean) {
        this.proformaSessionMBean = proformaSessionMBean;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public int getItemActivo() {
        return itemActivo;
    }

    public void setItemActivo(int itemActivo) {
        this.itemActivo = itemActivo;
    }

    public int getItemsDisponibles() {
        return itemsDisponibles;
    }

    public void setItemsDisponibles(int itemsDisponibles) {
        this.itemsDisponibles = itemsDisponibles;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public Integer getIdColumnaAsociada() {
        return idColumnaAsociada;
    }

    public void setIdColumnaAsociada(Integer idColumnaAsociada) {
        this.idColumnaAsociada = idColumnaAsociada;
    }

    public Integer getIdColumnaSeleccionada() {
        return idColumnaSeleccionada;
    }

    public String getContenedorSeleccionado() {
        if (idContenedorSeleccionado != null && idContenedorSeleccionado != null) {
            for (ContenedorProveedorDTO c : contenedores) {
                if (c.getIdContenedorProveedor().equals(idContenedorSeleccionado)) {
                    return c.getContenedor().getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public String getCuentaBancariaSeleccionada() {
        if (idCuentaBancaria != null) {
            for (CuentaBancariaProveedorDTO c : cuentasProveedor) {
                if (c.getIdCuentaBancaria().equals(idCuentaBancaria)) {
                    if (c.getSucursales().getBancoCompras().getRazonSocial().length() > 20) {
                        return c.getSucursales().getBancoCompras().getRazonSocial().substring(0, 20) + "... - " + c.getCuentaBancaria();
                    } else {
                        return c.getSucursales().getBancoCompras().getRazonSocial() + " - " + c.getCuentaBancaria();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public Integer getPasoCrearCuenta() {
        return pasoCrearCuenta;
    }

    public void setPasoCrearCuenta(Integer pasoCrearCuenta) {
        this.pasoCrearCuenta = pasoCrearCuenta;
    }

    public Integer getIdBancoCuenta() {
        return idBancoCuenta;
    }

    public String getBancoSeleccionado() {
        if (idBancoCuenta != null && idBancoCuenta > 0) {
            for (BancoComprasDTO b : bancos) {
                if (b.getIdBanco().equals(idBancoCuenta)) {
                    return b.getRazonSocial();
                }
            }
        } else if (idBancoCuenta != null && idBancoCuenta == -1) {
            return "Nuevo banco";
        }
        return "Seleccione";
    }

    public Integer getIdPais() {
        return idPais;
    }

    public String getPaisSeleccionado() {
        if (idPais != null && idPais != 0) {
            for (PaisesDTO p : paises) {
                if (p.getIdPais().equals(idPais)) {
                    if (p.getNombre().length() > 19) {
                        return p.getNombre().substring(0, 19) + "...";
                    }
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdEstado() {
        return idEstado;
    }

    public String getEstadoSeleccionado() {
        if (idEstado != null && idEstado != 0) {
            for (EstadosDTO e : estados) {
                if (e.getIdEstado().equals(idEstado)) {
                    if (e.getNombre().length() > 19) {
                        return e.getNombre().substring(0, 19) + "...";
                    }
                    return e.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdCiudad() {
        return idCiudad;
    }

    public String getCiudadSeleccionado() {
        if (idCiudad != null && idCiudad != 0) {
            for (CiudadDTO c : ciudades) {
                if (c.getIdCiudad().equals(idCiudad)) {
                    if (c.getNombre().length() > 19) {
                        return c.getNombre().substring(0, 19) + "...";
                    }
                    return c.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdSucursalBanco() {
        return idSucursalBanco;
    }

    public String getSucursalBancoSeleccionada() {
        if (idSucursalBanco != null && idSucursalBanco > 0) {
            for (SucursalBancoComprasDTO s : sucursalesBanco) {
                if (s.getIdSucursalBanco().equals(idSucursalBanco)) {
                    return s.getDireccion();
                }
            }
        } else if (idSucursalBanco != null && idSucursalBanco == -1) {
            return "Nueva sucursal";
        }
        return "Seleccione";
    }

    public Integer getIdCuentaSucursal() {
        return idCuentaSucursal;
    }

    public String getCuentaSucursalSeleccionada() {
        if (idCuentaSucursal != null && idCuentaSucursal > 0) {
            for (CuentaBancariaProveedorDTO c : cuentas) {
                if (c.getIdCuentaBancaria().equals(idCuentaSucursal)) {
                    return c.getCuentaBancaria();
                }
            }
        } else if (idCuentaSucursal != null && idCuentaSucursal == -1) {
            return "Nueva cuenta";
        }
        return "Seleccione";
    }

    public Integer getIdMoneda() {
        return idMoneda;
    }

    public String getMonedaSeleccionada() {
        if (idMoneda != null && idMoneda != 0) {
            for (TipoMonedaDTO t : monedas) {
                if (t.getIdTipoMoneda().equals(idMoneda)) {
                    return "(" + t.getSimbolo() + ") " + t.getMoneda();
                }
            }
        }
        return "Seleccione";
    }

    public String getTipoDescargaSeleccionada() {
        if (tipoDescarga != null) {
            if (tipoDescarga == -1) {
                return "Proforma completa";
            } else if (tipoDescarga > 0) {
                for (ContenedorProformaDTO c : contenedoresDescarga) {
                    if (c.getIdContenedorProforma().equals(tipoDescarga)) {
                        return c.getNombreContenedor();
                    }
                }
            } else {
                return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public Integer getPasoCerrarPI() {
        return pasoCerrarPI;
    }

    public void setPasoCerrarPI(Integer pasoCerrarPI) {
        this.pasoCerrarPI = pasoCerrarPI;
    }

    public String getPaisPuertoSalidaSeleccionado() {
        if (idPaisPuertoSalida != null && idPaisPuertoSalida != 0) {
            for (PaisesDTO p : paises) {
                if (p.getIdPais().equals(idPaisPuertoSalida)) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public String getIdPuertoSalidaSeleccionado() {
        if (proforma.getIdPuertoSalida() != null && proforma.getIdPuertoSalida() != 0) {
            for (PuertoMundoDTO p : puertosProveedor) {
                if (p.getIdPuertoMundo().equals(proforma.getIdPuertoSalida())) {
                    return p.getNombrePuerto();
                }
            }
        }
        return "Seleccione";
    }

    public String getIdPuertoLlegadaSeleccionado() {
        if (proforma.getIdPuertoLlegada() != null && proforma.getIdPuertoLlegada() != 0) {
            for (PuertoMundoDTO p : puertosColombianos) {
                if (p.getIdPuertoMundo().equals(proforma.getIdPuertoLlegada())) {
                    return p.getNombrePuerto();
                }
            }
        }
        return "Seleccione";
    }

    public String getIdIncotermSeleccionado() {
        if (proforma.getIdIncoterm() != null && proforma.getIdIncoterm() != 0) {
            for (IncotermDTO i : incoterms) {
                if (i.getIdIncoterm().equals(proforma.getIdIncoterm())) {
                    return i.getCodigo();
                }
            }
        }
        return "Seleccione";
    }

    public Double getPorcentajeAnticipo() {
        return porcentajeAnticipo;
    }

    public void setPorcentajeAnticipo(Double porcentajeAnticipo) {
        this.porcentajeAnticipo = porcentajeAnticipo;
    }

    public Double getTotalGirar() {
        return totalGirar;
    }

    public void setTotalGirar(Double totalGirar) {
        this.totalGirar = totalGirar;
    }

    public Double getCbmMinimo() {
        return cbmMinimo;
    }

    public void setCbmMinimo(Double cbmMinimo) {
        this.cbmMinimo = cbmMinimo;
    }

    public Double getCbmMaximo() {
        return cbmMaximo;
    }

    public void setCbmMaximo(Double cbmMaximo) {
        this.cbmMaximo = cbmMaximo;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrdenarPor() {
        switch (orderBy) {
            case "Columnas":
                return "Columnas";
            case "Valores":
                return "Valores";
            default:
                return "Seleccione";
        }
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public String getImgVista() {
        return imgVista;
    }

    public void setImgVista(String imgVista) {
        this.imgVista = imgVista;
    }

    public String getCampoSeleccionado() {
        if (campoSeleccionado == null || campoSeleccionado.isEmpty()) {
            return "Seleccione";
        } else {
            return campoSeleccionado;
        }
    }

    public String getNuevoValorCampoSeleccionado() {
        return nuevoValorCampoSeleccionado;
    }

    public void setNuevoValorCampoSeleccionado(String nuevoValorCampoSeleccionado) {
        this.nuevoValorCampoSeleccionado = nuevoValorCampoSeleccionado;
    }

    public String getTipoGiro() {
        return tipoGiro;
    }

    public String getTipoGiroSeleccionado() {
        if (tipoGiro != null) {
            switch (tipoGiro) {
                case "Anticipo":
                    return "Anticipo";
                case "Balance":
                    return "Balance";
                case "Roll on":
                    return "Roll on";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public String getAnticipo() {
        return anticipo == null ? null : baruGenericMBean.getValorFormateado("Double", anticipo, 2);
    }

    public void setAnticipo(String anticipo) {
        this.anticipo = anticipo;
    }

    public String getBalance() {
        return balance == null ? null : baruGenericMBean.getValorFormateado("Double", balance, 2);
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getRazonSocialBanco() {
        return razonSocialBanco;
    }

    public void setRazonSocialBanco(String razonSocialBanco) {
        this.razonSocialBanco = razonSocialBanco;
    }

    public String getDireccionSucursal() {
        return direccionSucursal;
    }

    public void setDireccionSucursal(String direccionSucursal) {
        this.direccionSucursal = direccionSucursal;
    }

    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }

    public String getSwiftSucursal() {
        return swiftSucursal;
    }

    public void setSwiftSucursal(String swiftSucursal) {
        this.swiftSucursal = swiftSucursal;
    }

    public String getIbanCuentaBancaria() {
        return ibanCuentaBancaria;
    }

    public void setIbanCuentaBancaria(String ibanCuentaBancaria) {
        this.ibanCuentaBancaria = ibanCuentaBancaria;
    }

    public String getAbaCuentaBancaria() {
        return abaCuentaBancaria;
    }

    public void setAbaCuentaBancaria(String abaCuentaBancaria) {
        this.abaCuentaBancaria = abaCuentaBancaria;
    }

    public String getDescripcionTransaccion() {
        return descripcionTransaccion;
    }

    public void setDescripcionTransaccion(String descripcionTransaccion) {
        this.descripcionTransaccion = descripcionTransaccion;
    }

    public String getValorMostrar() {
        return valorMostrar;
    }

    public String getValorMostrarSeleccionado() {
        if (valorMostrar != null && !valorMostrar.isEmpty()) {
            if (valorMostrar.equals("total")) {
                return "Total: " + baruGenericMBean.getValorFormateado("Double", proforma.getValorTotal().toString(), 2);
            } else if (valorMostrar.equals("descuento")) {
                return "Descuento: " + baruGenericMBean.getValorFormateado("Double", proforma.getValorTotalDescuento().toString(), 2);
            }
        }
        return "Total: " + baruGenericMBean.getValorFormateado("Double", proforma.getValorTotal().toString(), 2);
    }

    public String getColumnaSeleccionada() {
        if (idColumnaAsociada == null || idColumnaAsociada == 0) {
            return "Seleccione";
        } else {
            for (ColumnaProformaDTO c : columnas) {
                if (c.getIdColumna().equals(idColumnaAsociada)) {
                    if (c.getNombre2() != null && !c.getNombre2().isEmpty()) {
                        return c.getNombre1() + " - " + c.getNombre2();
                    } else {
                        return c.getNombre1();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public boolean isCrearColumna() {
        return crearColumna;
    }

    public void setCrearColumna(boolean crearColumna) {
        this.crearColumna = crearColumna;
    }

    public boolean isElegirColumna() {
        return elegirColumna;
    }

    public void setElegirColumna(boolean elegirColumna) {
        this.elegirColumna = elegirColumna;
    }

    public boolean isDlgCrearCuenta() {
        return dlgCrearCuenta;
    }

    public void setDlgCrearCuenta(boolean dlgCrearCuenta) {
        this.dlgCrearCuenta = dlgCrearCuenta;
    }

    public boolean isDlgMoneda() {
        return dlgMoneda;
    }

    public void setDlgMoneda(boolean dlgMoneda) {
        this.dlgMoneda = dlgMoneda;
    }

    public boolean isDlgCargaSuelta() {
        return dlgCargaSuelta;
    }

    public void setDlgCargaSuelta(boolean dlgCargaSuelta) {
        this.dlgCargaSuelta = dlgCargaSuelta;
    }

    public boolean isContenedoresAsignados() {
        return contenedoresAsignados;
    }

    public void setContenedoresAsignados(boolean contenedoresAsignados) {
        this.contenedoresAsignados = contenedoresAsignados;
    }

    public HtmlInputText getInputTextEspecial() {
        return inputTextEspecial;
    }

    public void setInputTextEspecial(HtmlInputText inputTextEspecial) {
        this.inputTextEspecial = inputTextEspecial;
    }

    public HtmlInputText getInputTextGeneral() {
        return inputTextGeneral;
    }

    public void setInputTextGeneral(HtmlInputText inputTextGeneral) {
        this.inputTextGeneral = inputTextGeneral;
    }

    public Part getDocumentoTransaccion() {
        return documentoTransaccion;
    }

    public void setDocumentoTransaccion(Part documentoTransaccion) {
        this.documentoTransaccion = documentoTransaccion;
    }

    public ProformaInvoiceDTO getProforma() {
        return proforma;
    }

    public void setProforma(ProformaInvoiceDTO proforma) {
        this.proforma = proforma;
    }

    public DatosProformaDTO getDatosProforma() {
        return datosProforma;
    }

    public void setDatosProforma(DatosProformaDTO datosProforma) {
        this.datosProforma = datosProforma;
    }

    public ColumnasProformaMBean getColumnasProformaMBean() {
        return columnasProformaMBean;
    }

    public void setColumnasProformaMBean(ColumnasProformaMBean columnasProformaMBean) {
        this.columnasProformaMBean = columnasProformaMBean;
    }

    public LineaProformaDTO getLineaProforma() {
        return lineaProforma;
    }

    public void setLineaProforma(LineaProformaDTO lineaProforma) {
        this.lineaProforma = lineaProforma;
    }

    public List<Integer> getItemsPaginacion() {
        return itemsPaginacion;
    }

    public void setItemsPaginacion(List<Integer> itemsPaginacion) {
        this.itemsPaginacion = itemsPaginacion;
    }

    public List<ProformaInvoiceDTO> getProformas() {
        return proformas;
    }

    public void setProformas(List<ProformaInvoiceDTO> proformas) {
        this.proformas = proformas;
    }

    public List<GaleriaDTO> getGaleria() {
        return galeria;
    }

    public void setGaleria(List<GaleriaDTO> galeria) {
        this.galeria = galeria;
    }

    public List<ItemDisponiblesAsignacionDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDisponiblesAsignacionDTO> items) {
        this.items = items;
    }

    public List<ItemDisponiblesAsignacionDTO> getCamposCrear() {
        return camposCrear;
    }

    public void setCamposCrear(List<ItemDisponiblesAsignacionDTO> camposCrear) {
        this.camposCrear = camposCrear;
    }

    public List<ColumnaProformaDTO> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<ColumnaProformaDTO> columnas) {
        this.columnas = columnas;
    }

    public List<ContenedorProveedorDTO> getContenedores() {
        return contenedores;
    }

    public void setContenedores(List<ContenedorProveedorDTO> contenedores) {
        this.contenedores = contenedores;
    }

    public List<ContenedorProformaDTO> getContenedoresProforma() {
        return contenedoresProforma;
    }

    public void setContenedoresProforma(List<ContenedorProformaDTO> contenedoresProforma) {
        this.contenedoresProforma = contenedoresProforma;
    }

    public List<CuentaBancariaProveedorDTO> getCuentasProveedor() {
        return cuentasProveedor;
    }

    public void setCuentasProveedor(List<CuentaBancariaProveedorDTO> cuentasProveedor) {
        this.cuentasProveedor = cuentasProveedor;
    }

    public List<BancoComprasDTO> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoComprasDTO> bancos) {
        this.bancos = bancos;
    }

    public List<PaisesDTO> getPaises() {
        return paises;
    }

    public void setPaises(List<PaisesDTO> paises) {
        this.paises = paises;
    }

    public List<EstadosDTO> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadosDTO> estados) {
        this.estados = estados;
    }

    public List<CiudadDTO> getCiudades() {
        return ciudades;
    }

    public void setCiudades(List<CiudadDTO> ciudades) {
        this.ciudades = ciudades;
    }

    public List<SucursalBancoComprasDTO> getSucursalesBanco() {
        return sucursalesBanco;
    }

    public void setSucursalesBanco(List<SucursalBancoComprasDTO> sucursalesBanco) {
        this.sucursalesBanco = sucursalesBanco;
    }

    public List<CuentaBancariaProveedorDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaBancariaProveedorDTO> cuentas) {
        this.cuentas = cuentas;
    }

    public List<TipoMonedaDTO> getMonedas() {
        return monedas;
    }

    public void setMonedas(List<TipoMonedaDTO> monedas) {
        this.monedas = monedas;
    }

    public List<TransaccionBancariaDTO> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionBancariaDTO> transacciones) {
        this.transacciones = transacciones;
    }

    public List<ContenedorProformaDTO> getContenedoresDescarga() {
        return contenedoresDescarga;
    }

    public void setContenedoresDescarga(List<ContenedorProformaDTO> contenedoresDescarga) {
        this.contenedoresDescarga = contenedoresDescarga;
    }

    public List<PuertoMundoDTO> getPuertosColombianos() {
        return puertosColombianos;
    }

    public void setPuertosColombianos(List<PuertoMundoDTO> puertosColombianos) {
        this.puertosColombianos = puertosColombianos;
    }

    public List<PuertoMundoDTO> getPuertosProveedor() {
        return puertosProveedor;
    }

    public void setPuertosProveedor(List<PuertoMundoDTO> puertosProveedor) {
        this.puertosProveedor = puertosProveedor;
    }

    public List<IncotermDTO> getIncoterms() {
        return incoterms;
    }

    public void setIncoterms(List<IncotermDTO> incoterms) {
        this.incoterms = incoterms;
    }

    private void cargarInfoSession() {
        log.log(Level.INFO, "Cargando informacion de sesion");
        idProforma = proformaSessionMBean.getIdProforma();
        log.log(Level.INFO, "Valor cargado [{0}]", idProforma);
        if (idProforma != null && idProforma != 0) {
            if (proformaSessionMBean.getLinea() != null && proformaSessionMBean.getLinea() != 0) {
                itemActivo = proformaSessionMBean.getLinea();
                if (itemActivo <= 0) {
                    itemActivo = 1;
                }
                proformaSessionMBean.setTagEnable(2);
                gestionarDatosProforma();
            }
        } else {
            idProforma = null;
        }
    }

    public void asignarTagSeleccionado(boolean datos) {
        Integer tag = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tag"));

        if (tag > 0) {
            if (!datos) {
                if (!validarSiHayContenedores() && tag != 1) {
                    tag = 3;
                }
                proformaSessionMBean.setTagEnable(tag);
                if (tag == 4) {

                }
            } else {
                if (!validarSiHayContenedores()) {
                    tag = 3;
                }
                proformaSessionMBean.setTagDatosProformaEnable(tag);
            }
        }
        validarContenedorCargaSuelta();
    }

    public void seleccionarValorMostrar() {
        valorMostrar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("valorMostrar");
        log.log(Level.INFO, "Se selecciono el valor mostrar {0}, de la proforma {1}", new Object[]{valorMostrar, idProforma});
    }

    private void validarContenedorCargaSuelta() {
        if (contenedoresProforma != null && !contenedoresProforma.isEmpty()) {
            for (ContenedorProformaDTO c : contenedoresProforma) {
                if (c.getContenedorProveedor().getContenedor().getNombre().contains("CARGA SUELTA")) {
                    if (proforma.getCbmTotal() >= c.getContenedorProveedor().getContenedor().getCBMMaximo()) {
                        dlgCargaSuelta = true;
                        log.log(Level.WARNING, "Se encontro que el CBM actual sirve para asignar un contenedor 20 PIES STANDAR 20' X 8' X 8'6\"");
                    }
                }
            }
        }
    }

    private boolean validarSiHayContenedores() {
        if (contenedoresProforma == null || contenedoresProforma.isEmpty()) {
            contenedoresAsignados = false;
            return false;
        } else {
            contenedoresAsignados = true;
            return true;
        }
    }

    /**
     * ********CODIGO DE LA ASIGNACION DE MONEDA A LA PROFORMA, ESTO CON EL FIN
     * DE QUE LOS DOUMENTOS DE TRANSACCIONES SE GENEREN
     * ADECUADAMENTE*************
     */
    public void validarMoneda() {
        dlgMoneda = false;
        if (proforma.getIdTipoMoneda() == null || proforma.getIdTipoMoneda().getIdTipoMoneda() == null || proforma.getIdTipoMoneda().getIdTipoMoneda() == 0) {
            dlgMoneda = true;
            obtenerMonedas();
        } else {
            proformaSessionMBean.setTagEnable(4);
        }
    }

    private void obtenerMonedas() {
        monedas.clear();

        List<TipoMoneda> coins = tipoMonedaFacade.obtenerMonedasActivas();
        if (coins != null && !coins.isEmpty()) {
            for (TipoMoneda t : coins) {
                monedas.add(new TipoMonedaDTO(t.getIdTipoMoneda(), t.getSimbolo(), t.getMoneda()));
            }

            Collections.sort(monedas, new Comparator<TipoMonedaDTO>() {
                @Override
                public int compare(TipoMonedaDTO t, TipoMonedaDTO t1) {
                    return t.getMoneda().compareTo(t1.getMoneda());
                }
            });
        }
    }

    public void seleccionarMoneda() {
        idMoneda = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMoneda"));
        log.log(Level.INFO, "Se selecciono la moneda con id [{0}]", idMoneda);
    }

    public void usarMonedaSeleccionada() {
        if (idMoneda != null && idMoneda != 0) {
            ProformaInvoice p = proformaInvoiceFacade.find(idProforma);

            if (p != null && p.getIdProforma() != null && p.getIdProforma() != 0) {
                p.setIdTipoMoneda(new TipoMoneda(idMoneda));

                try {
                    proformaInvoiceFacade.edit(p);
                    log.log(Level.INFO, "Se asigno la moneda con id [{0}], para la proforma con id [{1}]", new Object[]{idMoneda, idProforma});
                    for (TipoMonedaDTO t : monedas) {
                        if (t.getIdTipoMoneda().equals(idMoneda)) {
                            proforma.setIdTipoMoneda(t);
                            validarMoneda();
                            break;
                        }
                    }
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo asignar la moneda a la proforma, intentelo nuevamente.", true, false, false);
                    log.log(Level.SEVERE, "No se pudo asignar la moneda a la proforma, intentelo nuevamente. Error [{0}]", e.getMessage());
                    return;
                }
            }
        } else {
            mostrarMensaje("Error", "Seleccione una moneda para poder asignar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione una moneda para poder asignar");
            return;
        }
    }

    /**
     * ***********FINALIZA PROCESO DE MONEDA************
     */
    private void obtenerProformas() {
        proformas.clear();

        List<ProformaInvoice> ps = proformaInvoiceFacade.findAll();
        if (ps != null && !ps.isEmpty()) {
            for (ProformaInvoice p : ps) {
                proformas.add(new ProformaInvoiceDTO(p.getIdProforma(), p.getConsecutivo(), p.getIdPuertoLlegada() == null ? null : p.getIdPuertoLlegada().getIdPuertoMundo(),
                        p.getIdPuertoSalida() == null ? null : p.getIdPuertoSalida().getIdPuertoMundo(), p.getIdIncoterm() == null ? null : p.getIdIncoterm().getIdIncoterm(),
                        p.getCbmTotal(), p.getValorTotal(), p.getValorTotalDescuento(), 0.0, 0.0, p.getCodProveedor(), p.getAnio(), p.getUsuario(), verificarEstado(p.getEstado(), p.getIdProforma()),
                        null, p.getComentario(), p.getTipoProducto(), p.getTerminosPago(), p.getTerminosEntrega(), p.getPrimeraCarga(), p.getFecha(),
                        p.getIdTipoMoneda() == null ? null : new TipoMonedaDTO(p.getIdTipoMoneda().getIdTipoMoneda(), p.getIdTipoMoneda().getSimbolo(), p.getIdTipoMoneda().getMoneda())));
            }

            Collections.sort(proformas, new Comparator<ProformaInvoiceDTO>() {
                @Override
                public int compare(ProformaInvoiceDTO t, ProformaInvoiceDTO t1) {
                    return t1.getIdProforma().compareTo(t.getIdProforma());
                }
            });
            proformasFull = new ArrayList<>(proformas);
        }
    }

    private String verificarEstado(String estado, Integer idPi) {
        if (estado != null && estado.equals("AP")) {
            int aprobaciones = 0;
            int rechazos = 0;

            List<NotificacionProforma> notificaciones = notificacionProformaFacade.obtenerNotificacionesActivas(idPi);

            if (notificaciones != null && !notificaciones.isEmpty()) {
                for (NotificacionProforma n : notificaciones) {
                    if (n.getRespuesta() != null && n.getRespuesta()) {
                        aprobaciones++;
                    } else if (n.getRespuesta() != null && !n.getRespuesta()) {
                        rechazos++;
                    }
                }

                if (aprobaciones == 3) {
                    actualizarEstadoProforma("PA", idPi);
                    return "PA";
                } else if (rechazos > 0) {
                    actualizarEstadoProforma("PR", idPi);
                    return "PR";
                } else {
                    actualizarEstadoProforma("AP", idPi);
                    return "AP";
                }
            } else {
                actualizarEstadoProforma("AP", idPi);
                return "AP";
            }
        } else if (estado != null && !estado.isEmpty()) {
            return estado;
        }
        return "PP";
    }

    private void actualizarEstadoProforma(String estado, Integer idPi) {
        ProformaInvoice p = proformaInvoiceFacade.find(idPi);

        if (p != null && p.getIdProforma() != null && p.getIdProforma() != 0) {
            if (p.getEstado() != null && !p.getEstado().equals(estado)) {
                p.setEstado(estado);

                try {
                    proformaInvoiceFacade.edit(p);
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                }
            }
        }
    }

    public void seleccionarProforma() {
        Integer ultimaSeleccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProforma"));
        if (idProforma != null && ultimaSeleccion.equals(idProforma)) {
            idProforma = null;
            proforma = new ProformaInvoiceDTO();
        } else {
            idProforma = ultimaSeleccion;
            log.log(Level.INFO, "Se selecciono la proforma con id [{0}]", idProforma);
            gestionarDatosProforma();
        }
    }

    private void gestionarDatosProforma() {
        for (ProformaInvoiceDTO p : proformas) {
            if (p.getIdProforma().equals(idProforma)) {
                proforma = p;
                proformaSessionMBean.setIdProforma(p.getIdProforma());
                if (p.isPrimeraCarga()) {
                    ProformaInvoice pro = proformaInvoiceFacade.find(proforma.getIdProforma());

                    if (pro != null && pro.getIdProforma() != null && pro.getIdProforma() != 0) {
                        pro.setPrimeraCarga(false);

                        try {
                            proformaInvoiceFacade.edit(pro);
                            log.log(Level.INFO, "Se marco la proforma: [{0}], como no primera carga", proforma.getIdProforma());
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al cambiar el estado de la proforma a no primera carga. Error [{0}]", e.getMessage());
                            return;
                        }
                    }
                    if (p.isPrimeraCarga()) {
                        tratarDatosPrimeraCarga();
                    }
                }
                verificarTotalDescuento();
                obtenerLineas();
                obtenerTotales();
                obtenerDatosProformas();
                obtenerContenedoresProveedor();
                obtenerContenedoresProforma();
                actualizarTotalesContenedor();
                break;
            }
        }
        log.log(Level.INFO, "Proforma seleccionada [{0}]", idProforma);
    }

    private void verificarTotalDescuento() {
        ConfiguracionProforma confi = configuracionProformaFacade.verificarDescuentoProforma(idProforma);

        if (confi != null && confi.getIdConfiguracion() != null && confi.getIdConfiguracion() != 0) {
            log.log(Level.INFO, "Calculando total descuento para la proforma. Id configuracion [{0}]", confi.getIdConfiguracion());
            Double valorDescuento = detalleProformaFacade.obtenerTotalDescuento(confi.getIdConfiguracion());

            ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

            if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
                pi.setValorTotalDescuento(valorDescuento);

                try {
                    proformaInvoiceFacade.edit(pi);
                    log.log(Level.INFO, "Se le ingreso valor total descuento para la proforma [{0}], valor [{1}]", new Object[]{idProforma, valorDescuento});
                    proforma.setValorTotalDescuento(valorDescuento);
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo calcular adecuadamente el valor con descuento de la proforma.", true, false, false);
                    log.log(Level.SEVERE, "Error al actualizar la proforma [{0}], con el valor de descuento. Error [{1}]", new Object[]{idProforma, e.getMessage()});
                    return;
                }
            }
        }
    }

    private void obtenerLineas() {
        itemActivo = (itemActivo == 0) ? 1 : itemActivo;
        itemsDisponibles = detalleProformaFacade.maxRegistros(idProforma) + 1;
        construirListaPaginas();
    }

    private void construirListaPaginas() {
        itemsPaginacion = new ArrayList<>();
        for (int i = 1; i <= itemsDisponibles; i++) {
            itemsPaginacion.add(i);
        }
    }

    private void tratarDatosPrimeraCarga() {
        log.log(Level.INFO, "Comienza analisis y trato de los datos de la proforma [{0}]. "
                + "Este proceso se puede demorar bastante tiempo debido a que es la primera vez que se usa esta.", proforma.getIdProforma());
        log.log(Level.INFO, "Obteniendo datos del detalle de la proforma");
        List<DetalleProforma> detPro = detalleProformaFacade.consultarXIdProforma(proforma.getIdProforma());
        log.log(Level.INFO, "Empieza la evaluacion de los datos obtenidos de la proforma");

        for (DetalleProforma d : detPro) {
            if (d.getIdConfiguracion() != null) {
                if (d.getIdConfiguracion().getIdColumna().getRequiereOperacion() && d.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null) {
                    d.setValor(String.valueOf(calcularOperacion(d.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(), d.getLineNum())));
                    actualizarDetalle(d);
                }
            }
        }
    }

    public void reCalcularDatos() {
        tratarDatosPrimeraCarga();
        verificarTotalDescuento();
        obtenerTotales();
        itemActivo = 1;
        obtenerDatosProformas();
        actualizarTotalesContenedor();
    }

    private double calcularOperacion(Integer idOperacion, Integer linea) {
        double total = 0.0;
        if (idOperacion != null && idOperacion != 0) {
            List<DetalleOperacionColumna> detOpe = detalleOperacionColumnaFacade.obtenerOperaciones(idOperacion);
            for (DetalleOperacionColumna o : detOpe) {
                String valor1 = detalleProformaFacade.obtenerDetalleColumna(o.getIdColumna1().getIdColumna(), idProforma, linea).getValor();
                log.log(Level.INFO, "Detalle obtenido id columna {0}, valor {1}, linea {2}",
                        new Object[]{o.getIdColumna1().getIdColumna(), valor1, linea});
                String valor2;
                if (o.getConstante() != null && !o.getConstante().isEmpty()) {
                    valor2 = o.getConstante();
                } else {
                    valor2 = detalleProformaFacade.obtenerDetalleColumna(o.getIdColumna2().getIdColumna(), idProforma, linea).getValor();
                }

                if (valor1 != null && !valor1.isEmpty() && valor2 != null & !valor2.isEmpty()) {
                    total = ejecutarOperacion(valor1, valor2, o.getIdOperacion().getOperacion());
                }
            }
        }
        return total;
    }

    private double ejecutarOperacion(String valor1, String valor2, String operacion) {
        double v1 = Double.parseDouble(valor1);
        double v2 = Double.parseDouble(valor2);
        switch (operacion) {
            case "+":
                return v1 + v2;
            case "-":
                return v1 - v2;
            case "*":
                return v1 * v2;
            case "/":
                return v1 / v2;
            case "%":
                return v1 - ((v1 / 100) * v2);
        }
        return 0;
    }

    private void actualizarDetalle(DetalleProforma detalle) {
        if (detalle != null && detalle.getIdDetalleProforma() != null && detalle.getIdDetalleProforma() != 0) {
            try {
                detalleProformaFacade.edit(detalle);
//                log.log(Level.INFO, "Se modifico el detalle [{0}], con el nuevo valor [{1}]", new Object[]{detalle.getIdDetalleProforma(), detalle.getValor()});
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al actualizar el detalle [{0}]. Error [{1}]", new Object[]{detalle.getIdDetalleProforma(), e.getMessage()});
                return;
            }
        }
    }

    public void buscarProforma() {
        proformas.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            for (ProformaInvoiceDTO p : proformasFull) {
                if (p.getCodProveedor().contains(parametroBusqueda)) {
                    proformas.add(p);
                } else if (p.getAnio().contains(parametroBusqueda)) {
                    proformas.add(p);
                } else if (p.getConsecutivo().toString().contains(parametroBusqueda)) {
                    proformas.add(p);
                }
            }
            if (proformas.isEmpty()) {
                mostrarMensaje("Error", "No se encontraron datos coincidentes con el parámetro de busqueda.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos coincidentes con el parametro de busqueda");
                return;
            }
        } else {
            proformas = new ArrayList<>(proformasFull);
        }
    }

    private void obtenerTotales() {
        log.log(Level.INFO, "Inicia actualizacion de valores totales para la proforma con id [{0}]", idProforma);
        try {
            proformaInvoiceFacade.actualizarValoresTotales(idProforma);
            log.log(Level.INFO, "Se actualizaron los valores totales para la proforma con id [{0}]", idProforma);

            ProformaInvoice p = proformaInvoiceFacade.find(idProforma);

            if (p != null && p.getIdProforma() != null && p.getIdProforma() != 0) {
                proforma.setCbmTotal(p.getCbmTotal());
                proforma.setValorTotal(p.getValorTotal());
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudieron actualizar los valores totales para la proforma con id [{0}]. Error [{1}]", new Object[]{idProforma, e.getMessage()});
            mostrarMensaje("Advertencia", "Se encontraron datos desactualizados, comuníquese con el departamento de sistemas.", true, false, false);
            return;
        }
    }

    private void obtenerDatosProformas() {
        log.log(Level.INFO, "Mostrando item [{0}] de la proforma [{1}]", new Object[]{itemActivo, idProforma});
        List<DetalleProforma> detalle = detalleProformaFacade.consultarXIdProformaLinea(idProforma, itemActivo - 1);
        datosProforma = new DatosProformaDTO();

        datosProforma.setLinea(itemActivo);
        datosProforma.setIncluir(lineaIgnoradaProformaFacade.consultarExistencia(idProforma, itemActivo - 1) != null ? false : true);

        for (DetalleProforma det : detalle) {
            if (datosProforma.getDetalleProforma() == null) {
                datosProforma.setDetalleProforma(new ArrayList<DetalleProformaDTO>());
            }
            if (datosProforma.getDetalleProformaEspecial() == null) {
                datosProforma.setDetalleProformaEspecial(new ArrayList<DetalleProformaDTO>());
            }
            if (det.getIdConfiguracion().getOrden() <= 5) {
                datosProforma.getDetalleProformaEspecial().add(new DetalleProformaDTO(det.getIdDetalleProforma(),
                        det.getIdConfiguracion().getIdConfiguracion(), det.getLineNum(),
                        det.getIdConfiguracion().getIdColumna().getDecimalesVisibles(), det.getValor(),
                        det.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(),
                        det.getIdConfiguracion().getIdColumna().getIdTipoDato() == null ? null : det.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato(),
                        false, false,
                        det.getIdConfiguracion().getIdColumna() != null
                                ? new ColumnaProformaDTO(0, det.getIdConfiguracion().getIdColumna().getIdColumna(),
                                        det.getIdConfiguracion().getIdColumna().getDecimalesVisibles(),
                                        det.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null
                                                ? det.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                                        det.getIdConfiguracion().getIdColumna().getNombre1(), det.getIdConfiguracion().getIdColumna().getNombre1Ingles(),
                                        det.getIdConfiguracion().getIdColumna().getNombre2(), det.getIdConfiguracion().getIdColumna().getNombre2Ingles(),
                                        det.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(), det.getIdConfiguracion().getIdColumna().getTipoCantidad(),
                                        det.getIdConfiguracion().getIdColumna().getPermitirCrearItem(),
                                        det.getIdConfiguracion().getIdColumna().getRequiereOperacion(), det.getIdConfiguracion().getIdColumna().getTipoItem(),
                                        det.getIdConfiguracion().getIdColumna().getTipoIdentificador(), det.getIdConfiguracion().getIdColumna().getTipoImagen(),
                                        det.getIdConfiguracion().getIdColumna().getObligatoria(), det.getIdConfiguracion().getIdColumna().getIncluirProforma(),
                                        det.getIdConfiguracion().getIdColumna().getModificable(), det.getIdConfiguracion().getIdColumna().getModificableSiNuevo(),
                                        det.getIdConfiguracion().getIdColumna().getTipoValorTotal(), det.getIdConfiguracion().getIdColumna().getTipoCBM(),
                                        det.getIdConfiguracion().getIdColumna().getTipoValorUnitario(), det.getIdConfiguracion().getIdColumna().getTipoDescuento(),
                                        det.getIdConfiguracion().getIdColumna().getIdTipoDato() != null
                                                ? new TipoDatosDTO(det.getIdConfiguracion().getIdColumna().getIdTipoDato().getIdTipoDato(), det.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                        null) : null));
            } else {
                datosProforma.getDetalleProforma().add(new DetalleProformaDTO(det.getIdDetalleProforma(),
                        det.getIdConfiguracion().getIdConfiguracion(), det.getLineNum(),
                        det.getIdConfiguracion().getIdColumna().getDecimalesVisibles(), det.getValor(),
                        det.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(),
                        det.getIdConfiguracion().getIdColumna().getIdTipoDato() == null ? null : det.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato(),
                        false, false,
                        det.getIdConfiguracion().getIdColumna() != null
                                ? new ColumnaProformaDTO(0, det.getIdConfiguracion().getIdColumna().getIdColumna(),
                                        det.getIdConfiguracion().getIdColumna().getDecimalesVisibles(),
                                        det.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null
                                                ? det.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                                        det.getIdConfiguracion().getIdColumna().getNombre1(), det.getIdConfiguracion().getIdColumna().getNombre1Ingles(),
                                        det.getIdConfiguracion().getIdColumna().getNombre2(), det.getIdConfiguracion().getIdColumna().getNombre2Ingles(),
                                        det.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(), det.getIdConfiguracion().getIdColumna().getTipoCantidad(),
                                        det.getIdConfiguracion().getIdColumna().getPermitirCrearItem(),
                                        det.getIdConfiguracion().getIdColumna().getRequiereOperacion(), det.getIdConfiguracion().getIdColumna().getTipoItem(),
                                        det.getIdConfiguracion().getIdColumna().getTipoIdentificador(), det.getIdConfiguracion().getIdColumna().getTipoImagen(),
                                        det.getIdConfiguracion().getIdColumna().getObligatoria(), det.getIdConfiguracion().getIdColumna().getIncluirProforma(),
                                        det.getIdConfiguracion().getIdColumna().getModificable(), det.getIdConfiguracion().getIdColumna().getModificableSiNuevo(),
                                        det.getIdConfiguracion().getIdColumna().getTipoValorTotal(), det.getIdConfiguracion().getIdColumna().getTipoCBM(),
                                        det.getIdConfiguracion().getIdColumna().getTipoValorUnitario(), det.getIdConfiguracion().getIdColumna().getTipoDescuento(),
                                        det.getIdConfiguracion().getIdColumna().getIdTipoDato() != null
                                                ? new TipoDatosDTO(det.getIdConfiguracion().getIdColumna().getIdTipoDato().getIdTipoDato(), det.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                        null) : null));
            }
        }
        datosProforma.setDetalleProformaFull(new ArrayList<>(datosProforma.getDetalleProforma()));
        obtenerGaleria();
        obtenerItemsAdicionales();
        obtenerCamposCrear();
        obtenerContenedoresLinea();
        obtenerTotalesProforma();
        obtenerCuentasProveedor();
    }

    private void obtenerItemsAdicionales() {
        items = new ArrayList<>();

        List<DetalleProforma> detalle = detalleProformaFacade.itemsDisponiblesAsignacion(idProforma, itemActivo);

        if (detalle != null & !detalle.isEmpty()) {
            for (DetalleProforma d : detalle) {
                if (d.getValor() != null && !d.getValor().isEmpty()) {
                    items.add(new ItemDisponiblesAsignacionDTO(d.getIdDetalleProforma(), d.getLineNum(), d.getValor()));
                }
            }

            Collections.sort(items, new Comparator<ItemDisponiblesAsignacionDTO>() {
                @Override
                public int compare(ItemDisponiblesAsignacionDTO t, ItemDisponiblesAsignacionDTO t1) {
                    return t.getValor().compareTo(t1.getValor());
                }
            });
        }
    }

    public void seleccionarItemAsignarFoto() {
        Integer idItem = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item"));

        if (idItem != null && idItem != 0) {
            for (ItemDisponiblesAsignacionDTO i : items) {
                if (i.getIdColumna().equals(idItem)) {
                    if (i.isAplicar()) {
                        i.setAplicar(false);
                        log.log(Level.INFO, "Se marco el item [{0}], de id [{1}] como no asignado para duplicar las fotos seleccionadas", new Object[]{i.getValor(), i.getIdColumna()});
                    } else {
                        i.setAplicar(true);
                        log.log(Level.INFO, "Se marco el item [{0}], de id [{1}] como asignado para duplicar las fotos seleccionadas", new Object[]{i.getValor(), i.getIdColumna()});
                    }
                    break;
                }
            }
        }
    }

    public void cancelarAsignacionFoto() {
        log.log(Level.INFO, "Se esta cancelando la asignacion de items para duplicar las fotos");
        for (ItemDisponiblesAsignacionDTO i : items) {
            if (i.isAplicar()) {
                i.setAplicar(false);
            }
        }
    }

    public void asignarFoto() {
        log.log(Level.INFO, "Comienza asignacion de las fotos a los items seleccionados");

        boolean imagenesSeleccionadas = false;
        for (GaleriaDTO g : galeria) {
            if (g.isSeleccionada()) {
                imagenesSeleccionadas = true;
            }
        }

        if (imagenesSeleccionadas) {
            boolean itemAsignado = false;
            for (ItemDisponiblesAsignacionDTO i : items) {
                if (i.isAplicar()) {
                    for (GaleriaDTO g : galeria) {
                        if (g.isSeleccionada()) {
                            guardasFotoNueva(g.getNombreImagen(), i.getValor());
                            itemAsignado = true;
                        }
                    }
                }
            }

            if (!itemAsignado) {
                log.log(Level.INFO, "No se encontraron datos de items para asignar las fotos seleccionadas");
                return;
            }
            limpiarDatosGaleria();
        } else {
            log.log(Level.INFO, "No se encontraron imagenes seleccionadas para poder asignar a los items");
            return;
        }
    }

    private void guardasFotoNueva(String nombreImgAnterior, String nombreImgNueva) {
        try {
            String url = applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma")
                    + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo()
                    + File.separator + nombreImgAnterior;
            File file = new File(url);

            if (file.exists()) {
                log.log(Level.INFO, "Copiando imagen: [{0}]", nombreImgAnterior);
                String ruta = applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma")
                        + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo()
                        + File.separator + nombreImgNueva + "-" + System.currentTimeMillis() + ".png";
                BufferedImage sourceImage = ImageIO.read(new File(url));
                boolean result = ImageIO.write(sourceImage, "png", new File(ruta));
                log.log(Level.INFO, "La imagen [{0}] se pudo escribir", result ? "SI" : "NO");
                log.log(Level.INFO, "Imagen [{0}] copiada con exito", nombreImgNueva);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al intentar copiar la imagen [{0}]. Error [{1}]", new Object[]{nombreImgAnterior, e.getMessage()});
            return;
        }
    }

    private void limpiarDatosGaleria() {
        for (GaleriaDTO g : galeria) {
            g.setSeleccionada(false);
        }
    }

    private void obtenerCamposCrear() {
        camposCrear = new ArrayList<>();

        List<ColumnaProforma> cols = columnaProformaFacade.obtenerColumnasTipoCrear(idProforma);

        if (cols != null && !cols.isEmpty()) {
            for (ColumnaProforma c : cols) {
                camposCrear.add(new ItemDisponiblesAsignacionDTO(c.getIdColumna(), itemActivo - 1, c.getNombre2()));
            }

            Collections.sort(camposCrear, new Comparator<ItemDisponiblesAsignacionDTO>() {
                @Override
                public int compare(ItemDisponiblesAsignacionDTO t, ItemDisponiblesAsignacionDTO t1) {
                    return t.getValor().compareTo(t1.getValor());
                }
            });
        }
    }

    public void seleccionarCampoCrear() {
        idColumnaSeleccionada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idColumna"));
        campoSeleccionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("campoSeleccionadoCrear");
        log.log(Level.INFO, "Se selecciono el campo [{0}]", campoSeleccionado);
    }

    public void crearCopiaItem() {
        log.log(Level.INFO, "Inicia copiado de item para la proforma [{0}]", idProforma);

        if (campoSeleccionado == null || campoSeleccionado.isEmpty()) {
            mostrarMensaje("Error", "Seleccione un campo por el que quiere duplicar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un campo por el que quiere duplicar");
            return;
        }
        if (nuevoValorCampoSeleccionado == null || nuevoValorCampoSeleccionado.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un valor para el nuevo ítem.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un valor para el nuevo item");
            return;
        }

        for (DetalleProformaDTO d : datosProforma.getDetalleProformaEspecial()) {
            if (!agregarDetalleProforma(d)) {
                mostrarMensaje("Error", "No se pudo realizar la creación del nuevo ítem, comuníquese con sistemas.", true, false, false);
                log.log(Level.SEVERE, "No se pudo realizar la creacion del nuevo item, comuniquese con sistemas");
                return;
            }
        }
        for (DetalleProformaDTO d : datosProforma.getDetalleProforma()) {
            if (!agregarDetalleProforma(d)) {
                mostrarMensaje("Error", "No se pudo realizar la creación del nuevo ítem, comuníquese con sistemas.", true, false, false);
                log.log(Level.SEVERE, "No se pudo realizar la creacion del nuevo item, comuniquese con sistemas");
                return;
            }
        }

        construirListaPaginas();
        itemsDisponibles++;
        itemActivo = itemsDisponibles;
        obtenerDatosProformas();
        limpiarCopiaItem();
    }

    public void limpiarCopiaItem() {
        nuevoValorCampoSeleccionado = null;
        campoSeleccionado = null;
    }

    private boolean agregarDetalleProforma(DetalleProformaDTO d) {
        DetalleProforma det = new DetalleProforma();

        det.setIdConfiguracion(new ConfiguracionProforma(d.getIdConfiguracion()));
        det.setLineNum(itemsDisponibles);
        det.setNuevo(true);
        if (idColumnaSeleccionada.equals(d.getColumna().getIdColumna())) {
            det.setValor(nuevoValorCampoSeleccionado);
        } else if (d.getColumna().isTipoIdentificador()) {
            det.setValor(String.valueOf(itemsDisponibles + 1));
        } else {
            det.setValor(d.getValor());
        }

        try {
            detalleProformaFacade.create(det);
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear el detalle nuevo. Error [{0}]", e.getMessage());
            return false;
        }
    }

    public void agregarItem() {
        log.log(Level.INFO, "Creando nueva linea para la proforma con id [{0}]", idProforma);
        List<DetalleProforma> det = detalleProformaFacade.consultarXIdProformaLinea(idProforma, itemActivo - 1);

        if (det != null && !det.isEmpty()) {
            for (DetalleProforma d : det) {
                DetalleProforma detalle = new DetalleProforma();

                detalle.setIdConfiguracion(d.getIdConfiguracion());
                detalle.setLineNum(itemsDisponibles);
                detalle.setNuevo(true);
                detalle.setValor("");

                try {
                    detalleProformaFacade.create(detalle);
                    log.log(Level.INFO, "Detalle [{0}] agregado para la nueva linea, de la proforma con id [{1}]", new Object[]{detalle.getIdDetalleProforma(), idProforma});
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al crear el detalle de la nueva linea. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "Ocurrió un problema al crear la nueva línea para la proforma.", true, false, false);
                    return;
                }
            }
            itemsDisponibles++;
            itemActivo = itemsDisponibles;
            construirListaPaginas();
            obtenerDatosProformas();
        }
    }

    public void eliminarItem() {
        log.log(Level.INFO, "Comienza eliminacion de la linea [{0}], de la proforma con id {{1}]", new Object[]{itemActivo - 1, idProforma});
        try {
            detalleProformaFacade.eliminarItemProforma(idProforma, itemActivo - 1);
        } catch (Exception e) {
            mostrarMensaje("Error", "El ítem indicado no se pudo eliminar, comuníquese con el departamento de sistemas.", true, false, false);
            log.log(Level.SEVERE, "El item indicado no se pudo eliminar, comuniquese con el departamento de sistemas. Error [{0}]", e.getMessage());
            return;
        }

        //Aqui se reordenaran los identificadores de los demas articulos que estaban por delante del eliminado
        List<DetalleProforma> det = detalleProformaFacade.obtenerLineasReOrdenar(idProforma, itemActivo - 1);

        if (det != null && !det.isEmpty()) {
            log.log(Level.INFO, "Inicia re-ordenacion de identificaciones para la proforma [{0}]", idProforma);

            for (DetalleProforma d : det) {
                d.setLineNum(d.getLineNum() - 1);
                d.setValor(String.valueOf(Integer.parseInt(d.getValor()) - 1));

                try {
                    detalleProformaFacade.edit(d);
                    log.log(Level.INFO, "Se le cambia de linea al detalle de proforma. Linea anteior [{0}], linea nueva [{1}]", new Object[]{d.getLineNum() + 1, d.getLineNum()});
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al actualizar las linea de los detalles. Error [{0}]", e.getMessage());
                    return;
                }

                try {
                    detalleProformaFacade.actualizarLineas(idProforma, d.getLineNum() + 1, d.getLineNum());
                    log.log(Level.INFO, "Se actualizaron todas las lineas correspondientes");
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al actualizar las linea de los detalles. Error [{0}]", e.getMessage());
                    return;
                }
            }

            log.log(Level.INFO, "Finaliza re-ordenacion de identificaciones para la proforma [{0}]", idProforma);
        }
        itemActivo--;
        itemsDisponibles--;
        construirListaPaginas();
        obtenerDatosProformas();
    }

    public void seleccionarCrearColumna() {
        if (crearColumna) {
            crearColumna = false;
            elegirColumna = true;
        } else {
            crearColumna = true;
            elegirColumna = false;
        }
    }

    public void seleccionarElegirColumna() {
        if (elegirColumna) {
            crearColumna = true;
            elegirColumna = false;
        } else {
            crearColumna = false;
            elegirColumna = true;
        }
    }

    public void seleccionarColumnaAsociada() {
        idColumnaAsociada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idColumna"));
        log.log(Level.INFO, "Se selecciono la columna con id [{0}] a agregar a la proforma con id [{1}]", new Object[]{idColumnaAsociada, idProforma});
    }

    public void obtenerColumnas() {
        log.log(Level.INFO, "Cargando columnas para proforma");
        columnas = new ArrayList<>();
        List<ColumnaProforma> cols = columnaProformaFacade.obtenerColumnasNoProforma(idProforma);

        if (cols != null && !cols.isEmpty()) {
            for (ColumnaProforma c : cols) {
                columnas.add(new ColumnaProformaDTO(0, c.getIdColumna(),
                        c.getDecimalesVisibles(), c.getIdOperacionColumna() == null ? null : c.getIdOperacionColumna().getIdOperacionColumnaProforma(),
                        c.getNombre1(), c.getNombre1Ingles(), c.getNombre2(), c.getNombre2Ingles(), c.getLongitudOcupadaTabla(), c.getTipoCantidad(),
                        c.getPermitirCrearItem(), c.getRequiereOperacion(), c.getTipoItem(), c.getTipoIdentificador(), c.getTipoImagen(),
                        c.getObligatoria(), c.getIncluirProforma(), c.getModificable(), c.getModificableSiNuevo(), c.getTipoValorTotal(), c.getTipoCBM(),
                        c.getTipoValorUnitario(), c.getTipoDescuento(),
                        c.getIdTipoDato() != null ? new TipoDatosDTO(c.getIdTipoDato().getIdTipoDato(), c.getIdTipoDato().getTipoDato()) : null,
                        null));
            }

            Collections.sort(columnas);

            log.log(Level.INFO, "Se encontraron [{0}] columnas", columnas.size());
        }
    }

    public void administrarColumna() {
        Integer idColumna = idColumnaAsociada;

        if (idColumnaAsociada == null || idColumnaAsociada == 0) {
            mostrarMensaje("Error", "Seleccione una columna a agregar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione una columna a agregar");
            return;
        }

        for (DetalleProformaDTO d : datosProforma.getDetalleProformaEspecial()) {
            if (d.getColumna().getIdColumna().equals(idColumna)) {
                mostrarMensaje("Error", "Lo sentimos, pero la columna que desea incluir ya existe en la proforma actual.", true, false, false);
                log.log(Level.SEVERE, "Lo sentimos, pero la columna que desea incluir ya existe en la proforma actual");
                return;
            }
        }
        for (DetalleProformaDTO d : datosProforma.getDetalleProformaFull()) {
            if (d.getColumna().getIdColumna().equals(idColumna)) {
                mostrarMensaje("Error", "Lo sentimos, pero la columna que desea incluir ya existe en la proforma actual.", true, false, false);
                log.log(Level.SEVERE, "Lo sentimos, pero la columna que desea incluir ya existe en la proforma actual");
                return;
            }
        }

        ColumnaProforma col = columnaProformaFacade.find(idColumna);

        if (col != null && col.getIdColumna() != null && col.getIdColumna() != 0) {
            Integer ultimoOrdenConfiguracion = configuracionProformaFacade.ultimoOrdenConfiguracion(idProforma);
            if (col.getRequiereOperacion() && col.getIdOperacionColumna() != null
                    && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != null && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != 0) {
                List<DetalleOperacionColumna> detOper = detalleOperacionColumnaFacade.obtenerOperaciones(col.getIdOperacionColumna().getIdOperacionColumnaProforma());
                if (detOper != null && !detOper.isEmpty()) {
                    boolean columna1Existe = false;
                    boolean columna2Existe = false;

                    for (DetalleProformaDTO d : datosProforma.getDetalleProformaEspecial()) {
                        if (d.getColumna().getIdColumna().equals(detOper.get(0).getIdColumna1().getIdColumna())) {
                            columna1Existe = true;
                        }
                        if (d.getColumna() != null && detOper.get(0).getIdColumna2() != null) {
                            if (d.getColumna().getIdColumna().equals(detOper.get(0).getIdColumna2().getIdColumna())) {
                                columna2Existe = true;
                            }
                        } else {
                            columna2Existe = true;
                        }
                        if (columna1Existe && columna2Existe) {
                            break;
                        }
                    }

                    if (!columna1Existe || !columna2Existe) {
                        for (DetalleProformaDTO d : datosProforma.getDetalleProforma()) {
                            if (d.getColumna().getIdColumna().equals(detOper.get(0).getIdColumna1().getIdColumna())) {
                                columna1Existe = true;
                            }
                            if (d.getColumna() != null && detOper.get(0).getIdColumna2() != null) {
                                if (d.getColumna().getIdColumna().equals(detOper.get(0).getIdColumna2().getIdColumna())) {
                                    columna2Existe = true;
                                }
                            } else {
                                columna2Existe = true;
                            }
                            if (columna1Existe && columna2Existe) {
                                break;
                            }
                        }
                    }

                    if (!columna1Existe) {
                        ConfiguracionProforma conf = crearConfiguracion(detOper.get(0).getIdColumna1().getIdColumna(), ultimoOrdenConfiguracion);
                        ultimoOrdenConfiguracion++;

                        if (conf != null && conf.getIdConfiguracion() != null && conf.getIdConfiguracion() != 0) {
                            for (int i = 0; i < itemsDisponibles; i++) {
                                crearDetalleGenerico(i, "", conf);
                            }
                        } else {
                            mostrarMensaje("Error", "No se pudo agregar la nueva columna a la proforma.", true, false, false);
                            log.log(Level.SEVERE, "No se pudo agregar la nueva columna a la proforma");
                            return;
                        }
                    }

                    if (!columna2Existe) {
                        ConfiguracionProforma conf = crearConfiguracion(detOper.get(0).getIdColumna2().getIdColumna(), ultimoOrdenConfiguracion);
                        ultimoOrdenConfiguracion++;

                        if (conf != null && conf.getIdConfiguracion() != null && conf.getIdConfiguracion() != 0) {
                            for (int i = 0; i < itemsDisponibles; i++) {
                                crearDetalleGenerico(i, "", conf);
                            }
                        } else {
                            mostrarMensaje("Error", "No se pudo agregar la nueva columna a la proforma.", true, false, false);
                            log.log(Level.SEVERE, "No se pudo agregar la nueva columna a la proforma");
                            return;
                        }
                    }
                }
            }

            ConfiguracionProforma conf = crearConfiguracion(col.getIdColumna(), ultimoOrdenConfiguracion);

            if (conf != null && conf.getIdConfiguracion() != null && conf.getIdConfiguracion() != 0) {
                for (int i = 0; i < itemsDisponibles; i++) {
                    crearDetalleGenerico(i, "", conf);
                }
            } else {
                mostrarMensaje("Error", "No se pudo agregar la nueva columna a la proforma.", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la nueva columna a la proforma");
                return;
            }

            if (col.getRequiereOperacion()) {
                reCalcularDatos();
            }
            obtenerDatosProformas();
        }
    }

    private ConfiguracionProforma crearConfiguracion(Integer idColumna, Integer ultimoOrden) {
        ConfiguracionProforma conf = new ConfiguracionProforma();

        conf.setCodigoProveedor(proforma.getCodProveedor());
        conf.setIdColumna(new ColumnaProforma(idColumna));
        conf.setIdProforma(new ProformaInvoice(idProforma));
        conf.setOrden(ultimoOrden);

        try {
            configuracionProformaFacade.create(conf);
            log.log(Level.INFO, "Se agrego una nueva configuracion para la proforma con id [{0}]", idProforma);
            return conf;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al agregar la nueva configuracion para la proforma [{0}]. Error [{1}]", new Object[]{idProforma, e.getMessage()});
            return null;
        }
    }

    private boolean crearDetalleGenerico(Integer linea, String valor, ConfiguracionProforma configuracion) {
        DetalleProforma detalle = new DetalleProforma();

        detalle.setIdConfiguracion(configuracion);
        detalle.setLineNum(linea);
        detalle.setNuevo(true);
        detalle.setValor(valor);

        try {
            detalleProformaFacade.create(detalle);
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al agregar el detalle. Error [{0}]", e.getMessage());
            return false;
        }
    }

    private void obtenerContenedoresLinea() {
        lineaProforma = new LineaProformaDTO();

        DetalleProforma d = detalleProformaFacade.obtenerDetalleTipoCantidad(idProforma, itemActivo - 1);
        lineaProforma.setCantidadNecesaria(d.getValor() != null && !d.getValor().isEmpty() ? Integer.parseInt(d.getValor().replace(".0", "")) : 0);

        List<ContenedorLinea> containersLine = contenedorLineaFacade.obtenerContenedores(itemActivo - 1, idProforma);

        if (containersLine != null && !containersLine.isEmpty()) {
            for (ContenedorLinea c : containersLine) {
                lineaProforma.getContenedores().add(new ContenedorLineaDTO(c.getIdContenedorLinea(), c.getCantidad() == null ? 0 : c.getCantidad(),
                        c.getIdDetalleProforma().getIdDetalleProforma(), c.getUsuario(), c.getFecha(),
                        new ContenedorProformaDTO(c.getIdContenedorProforma().getIdContenedorProforma(),
                                c.getIdContenedorProforma().getIdProforma().getIdProforma(),
                                c.getIdContenedorProforma().getValorTotal(), c.getIdContenedorProforma().getCbmAcumulado(),
                                c.getIdContenedorProforma().getFechaEmbarque(),
                                new ContenedorProveedorDTO(c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedorProveedor(),
                                        null, null, null,
                                        new ContenedorDTO(c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getIdContenedor(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getCBMMinimo(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getCBMMaximo(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getNombre(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getCargaMaxima(),
                                                c.getIdContenedorProforma().getIdContenedorProveedor().getIdContenedor().getCapacidadCubica())), null)));
                lineaProforma.setCantidadUsada(lineaProforma.getCantidadUsada() + c.getCantidad());
            }
        }
    }

    public boolean validarSizeContenedores() {
        if (lineaProforma.getContenedores().size() > 1) {
            return true;
        } else {
            return false;
        }
    }

    public void asignarCantidadContenedor() {
        for (ContenedorLineaDTO l : lineaProforma.getContenedores()) {
            ContenedorLinea linea = contenedorLineaFacade.find(l.getIdContenedorLinea());

            if (linea != null && linea.getIdContenedorLinea() != null && linea.getIdContenedorLinea() != 0) {
                linea.setCantidad(l.getCantidad() == null ? 0 : l.getCantidad());
                linea.setFecha(new Date());

                try {
                    contenedorLineaFacade.edit(linea);
                    log.log(Level.INFO, "Se modifico la cantidad del contenedor linea con id [{0}], de la proforma con id [{1}], cantidad [{2}]",
                            new Object[]{l.getIdContenedorLinea(), idProforma, l.getCantidad()});
                    obtenerContenedoresLinea();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al modificar la cantidad del contenedor linea con id [{0}], de la proforma con id [{1}]. Error [{2}]",
                            new Object[]{l.getIdContenedorLinea(), idProforma, e.getMessage()});
                    mostrarMensaje("Error", "No se pudo asignar la cantidad al contenedor para este ítem.", true, false, false);
                    return;
                }
            }
        }
        actualizarTotalesContenedor();
        obtenerContenedoresProforma();
    }

    public void anteriorItem() {
        if (itemActivo > 0) {
            itemActivo--;
            obtenerDatosProformas();
        }
    }

    public void siguienteItem() {
        if (itemActivo < itemsDisponibles) {
            itemActivo++;
            obtenerDatosProformas();
        }
    }

    public void mostrarItemEspecifico() {
        itemActivo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item"));
        obtenerDatosProformas();
    }

    public void mostrarPrimerItem() {
        itemActivo = 1;
        obtenerDatosProformas();
    }

    public void mostrarUltimoItem() {
        itemActivo = itemsDisponibles;
        obtenerDatosProformas();
    }

    public void marcarLineaIncluida() {
        if (datosProforma.isIncluir()) {
            LineaIgnoradaProforma line = new LineaIgnoradaProforma();

            line.setFecha(new Date());
            line.setIdProforma(new ProformaInvoice(idProforma));
            line.setLinea(itemActivo - 1);
            line.setUsuario("ygil");

            try {
                lineaIgnoradaProformaFacade.create(line);
                datosProforma.setIncluir(false);
                log.log(Level.INFO, "Se marco la linea [{0}], como excluida para la proforma con id [{1}]", new Object[]{itemActivo - 1, proforma.getIdProforma()});
            } catch (Exception e) {
                mostrarMensaje("Error", "No se pudo incluir la línea de la proforma. Comuníquese con el departamento de sistemas.", true, false, false);
                log.log(Level.SEVERE, "No se pudo incluir la linea de la proforma. Comuniquese con el departamento de sistemas. Error [{0}]", e.getMessage());
                return;
            }

        } else {
            LineaIgnoradaProforma line = lineaIgnoradaProformaFacade.consultarExistencia(proforma.getIdProforma(), itemActivo - 1);

            if (line != null && line.getIdItemIgnorado() != null && line.getIdItemIgnorado() != 0) {
                try {
                    lineaIgnoradaProformaFacade.remove(line);
                    datosProforma.setIncluir(true);
                    log.log(Level.INFO, "Se marco la linea [{0}], como incluida para la proforma con id [{1}]", new Object[]{itemActivo - 1, proforma.getIdProforma()});
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo incluir la linea de la proforma. Comuniquese con el departamento de sistemas.", true, false, false);
                    log.log(Level.SEVERE, "No se pudo incluir la linea de la proforma. Comuniquese con el departamento de sistemas. Error [{0}]", e.getMessage());
                    return;
                }
            } else {
                mostrarMensaje("Error", "No se pudo incluir la linea de la proforma. Comuniquese con el departamento de sistemas.", true, false, false);
                log.log(Level.SEVERE, "No se pudo incluir la linea de la proforma. Comuniquese con el departamento de sistemas.");
                return;
            }
        }
        obtenerTotales();
        actualizarTotalesContenedor();
        obtenerContenedoresProforma();
    }

    public void procesarCambioDetalleEspecial(AjaxBehaviorEvent e) {
        actualizarDetalle(Integer.parseInt(inputTextEspecial.getAlt()), ((UIOutput) e.getSource()).getValue().toString());
    }

    public void procesarCambioDetalle(AjaxBehaviorEvent e) {
        actualizarDetalle(Integer.parseInt(inputTextGeneral.getAlt()), ((UIOutput) e.getSource()).getValue().toString());
    }

    private void actualizarDetalle(Integer idDetalleProforma, String valor) {
        DetalleProforma d = detalleProformaFacade.find(idDetalleProforma);

        if (d != null && d.getIdDetalleProforma() != null && d.getIdDetalleProforma() != 0) {
            if (d.getIdConfiguracion().getIdColumna().getIdTipoDato() != null) {
                d.setValor(valor);

                try {
                    detalleProformaFacade.edit(d);
                    log.log(Level.INFO, "Se modifico el detalle [{0}], con el nuevo valor [{1}]", new Object[]{idDetalleProforma, valor});
                    actualizarCamposOperacion(d.getIdConfiguracion().getIdColumna().getIdColumna());
                    obtenerContenedoresLinea();
                    return;
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo modificar el valor de la proforma.", true, false, false);
                    log.log(Level.SEVERE, "No se pudo modificar el valor de la proforma. Error [{0}]", e.getMessage());
                    return;
                }
            } else {
                mostrarMensaje("Error", "No se puede modificar el valor de la proforma, debido a que el campo que quiere modificar no tiene un tipo de dato relacionado.", true, false, false);
                log.log(Level.SEVERE, "No se puede modificar el valor de la proforma, debido a que el campo que quiere modificar no tiene un tipo de dato relacionado");
                return;
            }
        }
    }

    private void actualizarCamposOperacion(Integer idColumna) {
        List<DetalleProforma> dts = detalleProformaFacade.obtenerDetalleColumnasOperacion(idColumna, idProforma, itemActivo - 1);

        if (dts != null && !dts.isEmpty()) {
            for (DetalleProforma d : dts) {
                d.setValor(String.valueOf(calcularOperacion(d.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(), d.getLineNum())));
                actualizarDetalle(d);
                actualizarCamposOperacion(d.getIdConfiguracion().getIdColumna().getIdColumna());
            }
            obtenerDatosProformas();
            obtenerTotales();
            verificarTotalDescuento();
        }
    }

    public void buscarColumna() {
        log.log(Level.INFO, "Buscando [{0}] en nombres y valores de campos", parametroBusqueda);
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            datosProforma.setDetalleProforma(new ArrayList<DetalleProformaDTO>());
            for (DetalleProformaDTO d : datosProforma.getDetalleProformaFull()) {
                if (proformaSessionMBean.tratarColumna(proformaSessionMBean.obtenerColumna(d.getIdConfiguracion()), "bean").toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    datosProforma.getDetalleProforma().add(d);
                } else if (d.getValor().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    datosProforma.getDetalleProforma().add(d);
                }
            }
        } else {
            datosProforma.setDetalleProforma(new ArrayList<>(datosProforma.getDetalleProformaFull()));
        }
    }

    public void ordenarDatos() {
        orderBy = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        log.log(Level.INFO, "Ordenando por [{0}] [{1}]", new Object[]{orderBy, sortOrder});
        organizarDatos();
    }

    public void alternarOrdenDatos() {
        log.log(Level.INFO, "Alternando orden. Antes [{0}]", sortOrder);
        if (sortOrder.equals("ASC")) {
            sortOrder = "DESC";
        } else {
            sortOrder = "ASC";
        }
        log.log(Level.INFO, "Ahora [{0}]", sortOrder);
        organizarDatos();
    }

    private void organizarDatos() {
        if (orderBy.equals("Columnas")) {
            if (sortOrder.equals("DESC")) {
                Collections.sort(datosProforma.getDetalleProforma(), new Comparator<DetalleProformaDTO>() {
                    @Override
                    public int compare(DetalleProformaDTO t, DetalleProformaDTO t1) {
                        return proformaSessionMBean.mostrarDatosColumna(t1.getIdConfiguracion(), "bean").compareTo(proformaSessionMBean.mostrarDatosColumna(t.getIdConfiguracion(), "bean"));
                    }
                });
            } else if (sortOrder.equals("ASC")) {
                Collections.sort(datosProforma.getDetalleProforma(), new Comparator<DetalleProformaDTO>() {
                    @Override
                    public int compare(DetalleProformaDTO t, DetalleProformaDTO t1) {
                        return proformaSessionMBean.mostrarDatosColumna(t.getIdConfiguracion(), "bean").compareTo(proformaSessionMBean.mostrarDatosColumna(t1.getIdConfiguracion(), "bean"));
                    }
                });
            }
        } else if (orderBy.equals("Valores")) {
            if (sortOrder.equals("DESC")) {
                Collections.sort(datosProforma.getDetalleProforma(), new Comparator<DetalleProformaDTO>() {
                    @Override
                    public int compare(DetalleProformaDTO t, DetalleProformaDTO t1) {
                        return t1.getValor().compareTo(t.getValor());
                    }
                });
            } else if (sortOrder.equals("ASC")) {
                Collections.sort(datosProforma.getDetalleProforma(), new Comparator<DetalleProformaDTO>() {
                    @Override
                    public int compare(DetalleProformaDTO t, DetalleProformaDTO t1) {
                        return t.getValor().compareTo(t1.getValor());
                    }
                });
            }
        }
    }

    private void obtenerGaleria() {
        galeria.clear();
        String url = applicationMBean.obtenerValorPropiedad("url.local.imagesProductosProforma");

        File file = new File(url + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo());
        if (file.exists()) {
            DetalleProforma d = detalleProformaFacade.obtenerDetalleEspecifico(idProforma, (itemActivo - 1) < 0 ? 1 : itemActivo - 1, true);
            if (d != null && d.getIdDetalleProforma() != null && d.getIdDetalleProforma() != 0) {
                final String s = d.getValor();
                if (s != null && !s.isEmpty()) {
                    File[] archivos = file.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File file) {
                            String[] f = file.getName().split("¿");
                            return f[0].contains(s);
                        }
                    });

                    int contador = 0;
                    for (File f : archivos) {
                        galeria.add(new GaleriaDTO(contador, applicationMBean.obtenerValorPropiedad("url.web.proforma")
                                + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo() + "/" + f.getName(), f.getName()));
                        contador++;
                    }
                }
            }
        }
    }

    public void seleccionarImgVer() {
        imgVista = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("img");
    }

    public void seleccionarImgDuplicar() {
        Integer pos = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pos"));

        if (pos != null) {
            for (GaleriaDTO g : galeria) {
                if (g.getPosicion().equals(pos)) {
                    if (g.isSeleccionada()) {
                        g.setSeleccionada(false);
                    } else {
                        g.setSeleccionada(true);
                    }
                    break;
                }
            }
        }
    }

    /**
     * ***************************INICIA CODIGO
     * CONTENEDORES*******************************
     */
    private void obtenerContenedoresProveedor() {
        contenedores = new ArrayList<>();

        List<ContenedorProveedor> containers = contenedorProveedorFacade.consultarXIdProveedor(proforma.getCodProveedor());

        if (containers != null && !containers.isEmpty()) {
            for (ContenedorProveedor c : containers) {
                contenedores.add(new ContenedorProveedorDTO(c.getIdContenedorProveedor(), c.getIdContenedor().getIdContenedor(),
                        0, c.getIdContenedorProveedor().toString(),
                        new ContenedorDTO(c.getIdContenedor().getIdContenedor(), c.getIdContenedor().getCBMMinimo(), c.getIdContenedor().getCBMMaximo(),
                                c.getIdContenedor().getNombre(), c.getIdContenedor().getNombreCorto(), c.getIdContenedor().getCargaMaxima(), c.getIdContenedor().getCapacidadCubica())));
            }

            Collections.sort(contenedores, new Comparator<ContenedorProveedorDTO>() {
                @Override
                public int compare(ContenedorProveedorDTO t, ContenedorProveedorDTO t1) {
                    return t.getContenedor().getNombre().compareTo(t1.getContenedor().getNombre());
                }
            });
        }
    }

    private void obtenerContenedoresProforma() {
        contenedoresProforma.clear();
        cbmMaximo = 0.0;
        cbmMinimo = 0.0;

        List<ContenedorProforma> contenedor = contenedorProformaFacade.contenedoresProforma(idProforma);

        if (contenedor != null && !contenedor.isEmpty()) {
            for (ContenedorProforma c : contenedor) {
                contenedoresProforma.add(new ContenedorProformaDTO(c.getIdContenedorProforma(), c.getIdProforma().getIdProforma(),
                        c.getValorTotal(), c.getCbmAcumulado(), c.getFechaEmbarque(),
                        new ContenedorProveedorDTO(c.getIdContenedorProveedor().getIdContenedorProveedor(), null, null, null,
                                new ContenedorDTO(c.getIdContenedorProveedor().getIdContenedor().getIdContenedor(),
                                        c.getIdContenedorProveedor().getIdContenedor().getCBMMinimo(),
                                        c.getIdContenedorProveedor().getIdContenedor().getCBMMaximo(),
                                        c.getIdContenedorProveedor().getIdContenedor().getNombre(),
                                        c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                        c.getIdContenedorProveedor().getIdContenedor().getCargaMaxima(),
                                        c.getIdContenedorProveedor().getIdContenedor().getCapacidadCubica())),
                        applicationMBean.obtenerValorPropiedad("url.web.ruta.contenedor") + "?idContenedorProforma=" + c.getIdContenedorProforma() + "&idProforma=" + idProforma));
                cbmMaximo += c.getIdContenedorProveedor().getIdContenedor().getCBMMaximo().doubleValue();
                cbmMinimo += c.getIdContenedorProveedor().getIdContenedor().getCBMMinimo().doubleValue();
            }
            obtenerContenedoresDescargar();
        }
    }

    public void seleccionarContenedorProveedor() {
        idContenedorSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idContenedorProveedor"));
        log.log(Level.INFO, "Se selecciono el contenedor con id [{0}]", idContenedorSeleccionado);
    }

    public void agregarContenedorProforma() {
        log.log(Level.INFO, "Asignando contenedor a proforma [{0}]", idProforma);
        dlgCargaSuelta = false;
        if (idContenedorSeleccionado != null && idContenedorSeleccionado != 0) {
            ContenedorProforma contenedor = new ContenedorProforma();

            contenedor.setIdContenedorProveedor(new ContenedorProveedor(idContenedorSeleccionado));
            contenedor.setIdProforma(new ProformaInvoice(idProforma));

            try {
                contenedorProformaFacade.create(contenedor);
                log.log(Level.INFO, "Contenedor asignado a proforma [{0}]", idProforma);
                contenedorLineaFacade.insertarLineasContenedor(contenedor, "ygil",
                        contenedorProformaFacade.contenedoresProforma(idProforma) != null && contenedorProformaFacade.contenedoresProforma(idProforma).size() == 1 ? true : false);
                obtenerContenedoresProforma();
                obtenerDatosProformas();
                actualizarTotalesContenedor();
                idContenedorSeleccionado = null;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al agregar el contenedor seleccionado. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "Al agregar el contenedor seleccionado.", true, false, false);
                return;
            }
        } else {
            log.log(Level.SEVERE, "Error al agregar el contenedor seleccionado.");
            mostrarMensaje("Error", "Al agregar el contenedor seleccionado.", true, false, false);
            return;
        }
    }

    public void eliminarContenedorProforma() {
        log.log(Level.INFO, "Eliminando contenedor a proforma [{0}]", idProforma);
        dlgCargaSuelta = false;
        idContenedorSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idContenedorProforma"));

        if (idContenedorSeleccionado != null && idContenedorSeleccionado != 0) {
            ContenedorProforma cp = contenedorProformaFacade.find(idContenedorSeleccionado);

            try {
                contenedorLineaFacade.eliminarContenedores(idContenedorSeleccionado);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar las lineas del contenedor con id [{0}]. Error [{1}]", new Object[]{idContenedorSeleccionado, e.getMessage()});
                mostrarMensaje("Error", "No se pudo eliminar el contenedor seleccionado.", true, false, false);
                return;
            }

            try {
                contenedorProformaFacade.remove(cp);
                log.log(Level.INFO, "Se elimino el contenedor de la proforma");
                obtenerContenedoresProforma();
                obtenerDatosProformas();
                idContenedorSeleccionado = null;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar el contenedor para la proforma. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo eliminar el contenedor seleccionado.", true, false, false);
                return;
            }
        }
    }

    public void asignarFechaEmbarque(Integer idContenedorProforma) {
        log.log(Level.INFO, "Asignando fecha de embarque a contenedor proforma con id [{0}], de la proforma con id [{1}]", new Object[]{idContenedorProforma, idProforma});
        for (ContenedorProformaDTO c : contenedoresProforma) {
            if (c.getIdContenedorProforma().equals(idContenedorProforma)) {
                ContenedorProforma contenedor = contenedorProformaFacade.find(idContenedorProforma);

                if (contenedor != null && contenedor.getIdContenedorProforma() != null && contenedor.getIdContenedorProforma() != 0) {
                    contenedor.setFechaEmbarque(c.getFechaEmbarque());

                    try {
                        contenedorProformaFacade.edit(contenedor);
                        log.log(Level.INFO, "Se asigno fecha de embarque al contenedor proforma con id [{0}], de la proforma con id [{1}], fecha [{2}]",
                                new Object[]{idContenedorProforma, idProforma, c.getFechaEmbarque()});
                        mostrarMensaje("Éxito", "La fecha de embarque del contenedor, fue modificada correctamente.", false, true, false);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al modificar la fecha de embarque para el contenedor proforma con id [{0}], de la proforma con id [{1}]. Error [{2}]",
                                new Object[]{idContenedorProforma, idProforma, e.getMessage()});
                        mostrarMensaje("Error", "No se pudo modificar la fecha de embarque.", true, false, false);
                        return;
                    }
                }
            }
        }
    }

    public void actualizarTotalesContenedor() {
        try {
            log.log(Level.INFO, "Actualizando totales para los contenedores de la proforma [{0}]", idProforma);
            contenedorProformaFacade.actualizarTotalesContenedores(idProforma);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al actualizar los totales de contenedores de la proforma [{0}]. Error [{1}]", new Object[]{idProforma, e.getMessage()});
            return;
        }
    }

    public String verContenedorTabla() {
        proformaSessionMBean.setIdContenedorProforma(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idContenedorProforma")));
        return applicationMBean.obtenerValorPropiedad("url.web.ruta.contenedor");
    }

    /**
     * ********************INICIA CODIGO DE PAGOS************************
     */
    private void obtenerTotalesProforma() {
        //Aqui se cargara el Total transferido y el valor pendiente para la proforma seleccionada

        proforma.setValorTransferido(transaccionBancariaFacade.obtenerValorTransferido(idProforma));
        Double tp = transaccionBancariaFacade.obtenerValorPendiente(idProforma);
        proforma.setValorPendiente((proforma.getValorTotal() == null ? 0 : proforma.getValorTotal()) - (tp == null ? 0 : tp));
        obtenerTransaccionesProforma();
    }

    private void obtenerTransaccionesProforma() {
        transacciones.clear();

        List<TransaccionBancaria> dealings = transaccionBancariaFacade.transaccionesProforma(idProforma);
        if (dealings != null && !dealings.isEmpty()) {
            for (TransaccionBancaria t : dealings) {
                transacciones.add(new TransaccionBancariaDTO(t.getIdTransaccionBancaria(), t.getCodProveedor(),
                        t.getIdCuenta().getIdCuentaBancaria(), t.getTipoGiro(), t.getIdProformaInvoice().getIdProforma(), t.getTotalInvoice(), t.getPorcentajeAnticipo(),
                        t.getAnticipo(), t.getAnticipoCuenta(), t.getBalance(), t.getTotalGiro(), t.getDescripcion(), t.getUsuario(), t.getEstado(),
                        obtenerNombreDocumentoTransaccionBancaria(t.getIdTransaccionBancaria()), t.getFecha(),
                        t.getFechaGiro(), validarPdfExistencia(t.getIdTransaccionBancaria()), t.getFirmado() != null ? t.getFirmado() : false, t.getCancelado() != null ? t.getCancelado() : false));
            }
        }
    }

    private boolean validarPdfExistencia(Integer idTransferencia) {
        if (idTransferencia != null && idTransferencia != 0) {
            VersionTransaccionBancaria v = versionTransaccionBancariaFacade.obtenerUltimaVersionTransaccionBancaria(idTransferencia);

            if (v != null && v.getIdVersionTransaccionBancaria() != null && v.getIdVersionTransaccionBancaria() != 0) {
                File file = new File(applicationMBean.obtenerValorPropiedad("url.local.transaccionesBancarias") + v.getNombreDocumento());

                if (file.exists()) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private String obtenerNombreDocumentoTransaccionBancaria(Integer idTransferencia) {
        if (idTransferencia != null && idTransferencia != 0) {
            VersionTransaccionBancaria v = versionTransaccionBancariaFacade.obtenerUltimaVersionTransaccionBancaria(idTransferencia);

            if (v != null && v.getIdVersionTransaccionBancaria() != null && v.getIdVersionTransaccionBancaria() != 0) {
                return applicationMBean.obtenerValorPropiedad("url.web.transaccionesBancarias") + v.getNombreDocumento();
            }
        }
        return null;
    }

    public void seleccionarTransaccion() {
        idTransaccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTransaccion"));
        log.log(Level.INFO, "Se selecciono la transaccion con id [{0}], de la proforma con id [{1}]", new Object[]{idTransaccion, idProforma});
        documentoTransaccion = null;
    }

    public String fileName(Part part) {
        if (part != null) {
            for (String cd : part.getHeader("content-disposition").split(";")) {
                if (cd.trim().startsWith("filename")) {
                    String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                    return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
                }
            }
        }
        return "";
    }

    public void validarDocumentoCargado() {
        log.log(Level.INFO, "Se cargo el archivo [{0}], con extension [{1}]", new Object[]{fileName(documentoTransaccion), documentoTransaccion.getContentType()});
        if (!documentoTransaccion.getContentType().equals("application/pdf")) {
            mostrarMensaje("Error", "El archivo que desea subir no es del formato permitido. Formato permitido .pdf.", true, false, false);
            log.log(Level.SEVERE, "El archivo que desea subir no es del formato permitido. Formato permitido .pdf");
            documentoTransaccion = null;
            return;
        }
        try {
            if (documentoTransaccion != null) {
                log.log(Level.INFO, "Recibiendo archivo: {0}", fileName(documentoTransaccion));
                log.log(Level.INFO, "Tipo contenido: {0}", documentoTransaccion.getContentType());
                DefaultStreamedContent documento = new DefaultStreamedContent(documentoTransaccion.getInputStream());
//                File almacenarDocumento = new File(applicationMBean.obtenerValorPropiedad("url.local.transaccionesBancarias") + idTransaccion + ".pdf");
                File almacenarDocumento = new File(System.getProperty("jboss.server.temp.dir") + File.separator + idTransaccion + ".pdf");
                FileUtils.copyInputStreamToFile(documento.getStream(), almacenarDocumento);
                log.log(Level.INFO, "Se guardo el nuevo documento");
                obtenerTransaccionesProforma();
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al guardar el documento. Error [{0}]", e.getMessage());
            log.log(Level.SEVERE, "", e);
            mostrarMensaje("Error", "No fue posible guardar el documento.", true, false, false);
            return;
        }
    }

    public void guardarDocumento() {
        try {
            String nombreNuevoArchivo = idTransaccion + "-" + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()).replace("-", "").replace(":", "").replace(" ", "").trim();
            FileUtils.copyFile(new File(System.getProperty("jboss.server.temp.dir") + File.separator + idTransaccion + ".pdf"),
                    new File(applicationMBean.obtenerValorPropiedad("url.local.transaccionesBancarias") + nombreNuevoArchivo + ".pdf"));

            //Agregar registro a base de datos de la version
            VersionTransaccionBancaria version = new VersionTransaccionBancaria();

            version.setFecha(new Date());
            version.setIdProforma(new ProformaInvoice(idProforma));
            version.setIdTransaccionBancaria(new TransaccionBancaria(idTransaccion));
            version.setNombreDocumento(nombreNuevoArchivo + ".pdf");
            version.setUsuario("ygil");

            try {
                versionTransaccionBancariaFacade.create(version);
                log.log(Level.INFO, "Se inserto registro en version tabla proforma con el nombre [{0}]", nombreNuevoArchivo);
                documentoTransaccion = null;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al insertar la nueva version en la tabla version transaccion bancaria. Error [{0}]", e.getMessage());
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al copiar el documento de transaccion desde temporales a la carpeta destino. Error [{0}]", e.getMessage());
            mostrarMensaje("Error", "No se pudo guardar el documento.", true, false, false);
            return;
        }
    }

    public void cancelarTransaccionBancaria() {
        if (idTransaccion != null && idTransaccion != 0) {
            TransaccionBancaria t = transaccionBancariaFacade.find(idTransaccion);

            if (t != null && t.getIdTransaccionBancaria() != null && t.getIdTransaccionBancaria() != 0) {
                t.setCancelado(true);

                try {
                    transaccionBancariaFacade.edit(t);
                    log.log(Level.INFO, "Se marco la transaccion bancaria con id [{0}] como cancelada para la proforma con id [{1}]", new Object[]{idTransaccion, idProforma});
                    idTransaccion = null;
                    obtenerTotalesProforma();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al colocar la transaccion bancaria con if [{0}], de la proforma [{1}]. Error [{2}]", new Object[]{idTransaccion, idProforma, e.getMessage()});
                    mostrarMensaje("Error", "No se pudo colocar la transacción bancaria como cancelada.", true, false, false);
                    return;
                }
            }
        }
    }

    private void obtenerCuentasProveedor() {
        cuentasProveedor.clear();
        log.log(Level.INFO, "Se estan obteniendo las cuentas bancarias para el proveedor [{0}]", proforma.getCodProveedor());
        List<CuentaBancariaProveedor> accounts = cuentaBancariaProveedorFacade.cuentasProveedor(proforma.getCodProveedor());

        if (accounts != null && !accounts.isEmpty()) {
            log.log(Level.INFO, "Se obtuvieron {0} cuentas para el proveedor {1}", new Object[]{accounts.size(), proforma.getCodProveedor()});
            for (CuentaBancariaProveedor c : accounts) {
                cuentasProveedor.add(new CuentaBancariaProveedorDTO(c.getIdCuentaBancaria(), c.getCuentaBancaria(), c.getIban(),
                        c.getAba(), c.getCodProveedor(), new SucursalBancoComprasDTO(c.getIdSucursalBanco().getIdSucursalBanco(),
                                c.getIdSucursalBanco().getDireccion(), c.getIdSucursalBanco().getSwift(),
                                new BancoComprasDTO(c.getIdSucursalBanco().getIdBanco().getIdBanco(), c.getIdSucursalBanco().getIdBanco().getRazonSocial()),
                                c.getIdSucursalBanco().getIdCiudad() == null ? null : new CiudadDTO(c.getIdSucursalBanco().getIdCiudad().getIdCiudad(),
                                                c.getIdSucursalBanco().getIdCiudad().getNombre(),
                                                new EstadosDTO(c.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdEstado(),
                                                        c.getIdSucursalBanco().getIdCiudad().getIdEstado().getNombre(),
                                                        new PaisesDTO(c.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getIdPais(),
                                                                c.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getNombre(),
                                                                c.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getContinente()))))));
            }
        }
    }

    public void seleccionarCuenta() {
        idCuentaBancaria = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCuenta"));
        log.log(Level.INFO, "Se selecciono la cuenta con id [{0}]", idCuentaBancaria);

        if (idCuentaBancaria == -1) {
            dlgCrearCuenta = true;
            if (bancos == null || bancos.isEmpty()) {
                obtenerBancosCompras();
            }
            if (paises == null || paises.isEmpty()) {
                obtenerPaises();
            }
        } else {
            dlgCrearCuenta = false;
        }
    }

    public void seleccionarTipoGiro() {
        tipoGiro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoGiro");
        log.log(Level.INFO, "Se selecciono el tipo giro [{0}]", tipoGiro);

        anticipo = null;
        porcentajeAnticipo = null;
        balance = null;
        totalGirar = null;
        if (tipoGiro != null && tipoGiro.equals("Roll on")) {
            Double anticipos = anticipoCuentaFacade.obtenerSaldoCuentaDisponible(2, idCuentaBancaria, proforma.getCodProveedor());

            if (anticipos == null || anticipos == 0) {
                tipoGiro = null;
                mostrarMensaje("Error", "No se puede usar el tipo de giro Roll on, debido a que no se han encontrado saldos a favor para la cuenta, moneda y proveedor actual.", true, false, false);
                log.log(Level.SEVERE, "No se puede usar el tipo de giro Roll on, debido a que no se han encontrado saldos a favor para la cuenta, moneda y proveedor actual");
                return;
            }
        } else if (tipoGiro.equals("Balance")) {
            balance = proforma.getValorPendiente().toString();
            totalGirar = Double.parseDouble(balance.replace(",", ""));
        }
    }

    public void calcularPorcentajeAnticipo() {
        porcentajeAnticipo = (Double.parseDouble(anticipo.replace(",", "")) * 100) / proforma.getValorTotal();
        decidirTotalGiro();
    }

    private void decidirTotalGiro() {
        if (tipoGiro.equals("Anticipo")) {
            totalGirar = Double.parseDouble(anticipo.replace(",", ""));
        }

        if (totalGirar > proforma.getValorTotal()) {
            mostrarMensaje("Error", "Está intentando agregar un pago superior al valor de la proforma.", true, false, false);
            log.log(Level.SEVERE, "Esta intentando agregar un pago superior al valor de la proforma");
            totalGirar = null;
            return;
        }
    }

    public void crearTransaccionBancaria() {
        if (idCuentaBancaria == null || idCuentaBancaria == 0) {
            mostrarMensaje("Error", "Seleccione una cuenta para realizar la transacción.", true, false, false);
            log.log(Level.SEVERE, "Seleccione una cuenta para realizar la transaccion");
            return;
        }
        if (tipoGiro == null || tipoGiro.isEmpty()) {
            mostrarMensaje("Error", "Seleccione un tipo de giro para realizar la transacción.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un tipo de giro para realizar la transaccion");
            return;
        }
        if ((anticipo == null || anticipo.isEmpty()) && (balance == null || balance.isEmpty()) && (totalGirar == null || totalGirar == 0)) {
            mostrarMensaje("Error", "Ingrese un valor a girar para realizar la transacción.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un valor a girar para realizar la transaccion");
            return;
        }
        if (descripcionTransaccion == null || descripcionTransaccion.isEmpty()) {
            mostrarMensaje("Error", "Ingrese una descripción para realizar la transacción.", true, false, false);
            log.log(Level.SEVERE, "Ingrese una descripcion para realizar la transaccion");
            return;
        }

        TransaccionBancaria t = new TransaccionBancaria();

        t.setAnticipo(anticipo == null ? null : Double.parseDouble(anticipo.replace(",", "")));
        t.setAnticipoCuenta(null);
        t.setBalance(balance == null ? null : Double.parseDouble(balance.replace(",", "")));
        t.setCodProveedor(proforma.getCodProveedor());
        t.setDescripcion(descripcionTransaccion.toUpperCase());
        t.setEstado("TP");
        t.setFecha(new Date());
        t.setIdCuenta(new CuentaBancariaProveedor(idCuentaBancaria));
        t.setIdProformaInvoice(new ProformaInvoice(idProforma));
        t.setPorcentajeAnticipo(porcentajeAnticipo == null ? null : porcentajeAnticipo);
        t.setTipoGiro(tipoGiro.toUpperCase());
        t.setTotalGiro(totalGirar);
        t.setTotalInvoice(proforma.getValorTotal());
        t.setUsuario("ygil");
        t.setCancelado(false);
        t.setFirmado(false);

        try {
            transaccionBancariaFacade.create(t);
            log.log(Level.INFO, "Se creo transaccion bancaria a la proforma [{0}]", idProforma);
            limpiarTransaccion();
            obtenerTotalesProforma();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear la transaccion bancaria para la proforma con id [{0}]. Error [{1}]", new Object[]{idProforma, e.getMessage()});
            mostrarMensaje("Error", "No se pudo crear la transacción bancaria para la proforma.", true, false, false);
            return;
        }
    }

    private void limpiarTransaccion() {
        idCuentaBancaria = null;
        tipoGiro = null;
        anticipo = null;
        porcentajeAnticipo = null;
        balance = null;
        totalGirar = null;
        descripcionTransaccion = null;
    }

    private void obtenerBancosCompras() {
        bancos.clear();

        List<BancoCompras> banks = bancoComprasFacade.findAll();
        if (banks != null && !banks.isEmpty()) {
            for (BancoCompras b : banks) {
                bancos.add(new BancoComprasDTO(b.getIdBanco(), b.getRazonSocial()));
            }

            Collections.sort(bancos, new Comparator<BancoComprasDTO>() {
                @Override
                public int compare(BancoComprasDTO t, BancoComprasDTO t1) {
                    return t.getRazonSocial().compareTo(t1.getRazonSocial());
                }
            });
        }
    }

    private void obtenerPaises() {
        paises.clear();

        List<Paises> countries = paisesFacade.findAll();
        if (countries != null && !countries.isEmpty()) {
            for (Paises c : countries) {
                paises.add(new PaisesDTO(c.getIdPais(), c.getNombre(), c.getContinente()));
            }

            Collections.sort(paises, new Comparator<PaisesDTO>() {
                @Override
                public int compare(PaisesDTO t, PaisesDTO t1) {
                    return t.getNombre().compareTo(t1.getNombre());
                }
            });
        }
    }

    public void seleccionarBancoCompras() {
        idBancoCuenta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idBanco"));
        log.log(Level.INFO, "Se selecciono el banco con id [{0}]", idBancoCuenta);

        limpiarPasoSucursalCuenta();
        if (idBancoCuenta != null && idBancoCuenta > 0) {
            obtenerSucursalesBanco();
            for (BancoComprasDTO b : bancos) {
                if (b.getIdBanco().equals(idBancoCuenta)) {
                    razonSocialBanco = b.getRazonSocial();
                    break;
                }
            }
        } else if (idBancoCuenta != null && idBancoCuenta == -1) {
            log.log(Level.INFO, "Se selecciono crear banco para el proveedor [{0}]", proforma.getCodProveedor());
            razonSocialBanco = null;
        }
    }

    public boolean guardarBancoCompras() {
//        if (idBancoCuenta == null || idBancoCuenta == 0) {
//            mostrarMensaje("Error", "Debe seleccionar un banco para poder continuar.", true, false, false);
//            log.log(Level.SEVERE, "Debe seleccionar un banco para poder continuar");
//            return false;
//        }
        if (razonSocialBanco == null || razonSocialBanco.isEmpty()) {
            mostrarMensaje("Error", "Ingrese la razón social del banco.", true, false, false);
            log.log(Level.SEVERE, "Ingrese la razon social del banco");
            return false;
        }

        BancoCompras b = null;
        if (idBancoCuenta != null && idBancoCuenta > 0) {
            b = bancoComprasFacade.find(idBancoCuenta);
            if (b != null && b.getIdBanco() != null && b.getIdBanco() != 0) {
                b.setRazonSocial(razonSocialBanco.toUpperCase());

                try {
                    bancoComprasFacade.edit(b);
                    log.log(Level.INFO, "Se modifico el banco con id [{0}], para el proveedor [{1}]", new Object[]{idBancoCuenta, proforma.getCodProveedor()});
                    obtenerBancosCompras();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al modificar el banco. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo modificar el banco.", true, false, false);
                    return false;
                }
            }
        } else {
            b = new BancoCompras();

            b.setRazonSocial(razonSocialBanco.toUpperCase());

            try {
                bancoComprasFacade.create(b);
                log.log(Level.INFO, "Se creo banco con id [{0}], para el proveedor [{1}]", new Object[]{b.getIdBanco(), proforma.getCodProveedor()});
                idBancoCuenta = b.getIdBanco();
                obtenerBancosCompras();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear el banco. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo crear el banco.", true, false, false);
                return false;
            }
        }
        obtenerSucursalesBanco();
        return true;
    }

    public void seleccionarPais() {
        idPais = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPais"));
        log.log(Level.INFO, "Se selecciono el pais con id [{0}]", idPais);

        if (idPais != null && idPais != 0) {
            idEstado = null;
            log.log(Level.INFO, "Se estan obteniendo los estados del pais {0}", idPais);
            obtenerEstados();
        }
    }

    private void obtenerEstados() {
        estados.clear();

        List<Estados> states = estadosFacade.estadosXPais(idPais);
        if (states != null && !states.isEmpty()) {
            for (Estados e : states) {
                estados.add(new EstadosDTO(e.getIdEstado(), e.getNombre(), new PaisesDTO(e.getIdPais().getIdPais())));
            }
            log.log(Level.INFO, "Se encontraron {0} estados para el pais con id {1}", new Object[]{estados.size(), idPais});

            Collections.sort(estados, new Comparator<EstadosDTO>() {
                @Override
                public int compare(EstadosDTO t, EstadosDTO t1) {
                    return t.getNombre().compareTo(t1.getNombre());
                }
            });
        }
    }

    public void seleccionarEstado() {
        idEstado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEstado"));
        log.log(Level.INFO, "Se selecciono el estado con id [{0}]", idEstado);

        if (idEstado != null && idEstado != 0) {
            idCiudad = null;
            log.log(Level.INFO, "Se estan obteniendo las ciudades del estado {0}", idEstado);
            obtenerCiudades();
        }
    }

    private void obtenerCiudades() {
        ciudades.clear();

        List<Ciudad> cities = ciudadFacade.obtenerCiudadesEstado(idEstado);
        if (cities != null && !cities.isEmpty()) {
            for (Ciudad c : cities) {
                ciudades.add(new CiudadDTO(c.getIdCiudad(), c.getNombre(), new EstadosDTO(c.getIdEstado().getIdEstado(), c.getIdEstado().getNombre(),
                        new PaisesDTO(c.getIdEstado().getIdPais().getIdPais(), c.getIdEstado().getIdPais().getNombre(), c.getIdEstado().getIdPais().getContinente()))));
            }
            log.log(Level.INFO, "Se encontraron {0} ciudades para el estado con id {1}", new Object[]{ciudades.size(), idEstado});

            Collections.sort(ciudades, new Comparator<CiudadDTO>() {
                @Override
                public int compare(CiudadDTO t, CiudadDTO t1) {
                    return t.getNombre().compareTo(t1.getNombre());
                }
            });
        }
    }

    public void seleccionarCiudad() {
        idCiudad = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCiudad"));
        log.log(Level.INFO, "Se selecciono la ciudad con id [{0}]", idCiudad);
    }

    private void obtenerSucursalesBanco() {
        sucursalesBanco.clear();

        List<SucursalBancoCompras> sucursales = sucursalBancoComprasFacade.obtenerBancosProveedor(idBancoCuenta);
        if (sucursales != null && !sucursales.isEmpty()) {
            for (SucursalBancoCompras s : sucursales) {
                sucursalesBanco.add(new SucursalBancoComprasDTO(s.getIdSucursalBanco(), s.getDireccion(), s.getSwift(),
                        new BancoComprasDTO(s.getIdBanco().getIdBanco(), s.getIdBanco().getRazonSocial()),
                        s.getIdCiudad() == null ? null : new CiudadDTO(s.getIdCiudad().getIdCiudad(), s.getIdCiudad().getNombre(),
                                        new EstadosDTO(s.getIdCiudad().getIdEstado().getIdEstado(), s.getIdCiudad().getIdEstado().getNombre(),
                                                new PaisesDTO(s.getIdCiudad().getIdEstado().getIdPais().getIdPais(),
                                                        s.getIdCiudad().getIdEstado().getIdPais().getNombre(), s.getIdCiudad().getIdEstado().getIdPais().getContinente())))));
            }

            Collections.sort(sucursalesBanco, new Comparator<SucursalBancoComprasDTO>() {
                @Override
                public int compare(SucursalBancoComprasDTO t, SucursalBancoComprasDTO t1) {
                    return t.getDireccion().compareTo(t1.getDireccion());
                }
            });
        }
    }

    public void seleccionarSucursalBanco() {
        idSucursalBanco = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSucursalBanco"));

        limpiarPasoCuentaBancaria();
        if (idSucursalBanco != null && idSucursalBanco > 0) {
            log.log(Level.INFO, "Se selecciono la sucursal con id [{0}], del banco con id [{1}]", new Object[]{idSucursalBanco, idBancoCuenta});

            for (SucursalBancoComprasDTO s : sucursalesBanco) {
                if (s.getIdSucursalBanco().equals(idSucursalBanco)) {
                    direccionSucursal = s.getDireccion();
                    swiftSucursal = s.getSwift();
                    if (s.getIdCiudad() != null) {
                        idPais = s.getIdCiudad().getIdEstado().getIdPais().getIdPais();
                        obtenerEstados();
                        idEstado = s.getIdCiudad().getIdEstado().getIdEstado();
                        obtenerCiudades();
                        idCiudad = s.getIdCiudad().getIdCiudad();
                    }
                    break;
                }
            }
        } else if (idSucursalBanco != null && idSucursalBanco == -1) {
            log.log(Level.INFO, "Se selecciono crear sucursal para el banco con id [{1}]", idBancoCuenta);
            limpiarPasoSucursalCuenta();
        }
    }

    public boolean guardarSucursalBanco() {
//        if (idSucursalBanco == null || idSucursalBanco == 0) {
//            mostrarMensaje("Error", "No se ha seleccionado la sucursal del banco para guardar.", true, false, false);
//            log.log(Level.SEVERE, "No se ha seleccionado la sucursal del banco para guardar");
//            return false;
//        }
        if (direccionSucursal == null || direccionSucursal.isEmpty()) {
            mostrarMensaje("Error", "Ingrese una dirección para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese una direccion para la sucursal, y de clic en Guardar");
            return false;
        }
        if (swiftSucursal == null || swiftSucursal.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un swift para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un swift para la sucursal, y de clic en Guardar");
            return false;
        }
        if (idPais == null || idPais == 0) {
            mostrarMensaje("Error", "Ingrese un país para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un pais para la sucursal, y de clic en Guardar");
            return false;
        }
        if (idEstado == null || idEstado == 0) {
            mostrarMensaje("Error", "Ingrese un estado para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un estado para la sucursal, y de clic en Guardar");
            return false;
        }
        if (idCiudad == null || idCiudad == 0) {
            mostrarMensaje("Error", "Ingrese una ciudad para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese una ciudad para la sucursal, y de clic en Guardar");
            return false;
        }

        SucursalBancoCompras s = null;

        if (idSucursalBanco != null && idSucursalBanco > 0) {
            s = sucursalBancoComprasFacade.find(idSucursalBanco);
            if (s != null && s.getIdSucursalBanco() != null && s.getIdSucursalBanco() != 0) {
                s.setDireccion(direccionSucursal.toUpperCase());
                s.setIdBanco(new BancoCompras(idBancoCuenta));
                s.setIdCiudad(new Ciudad(idCiudad));
                s.setSwift(swiftSucursal);

                try {
                    sucursalBancoComprasFacade.edit(s);
                    log.log(Level.INFO, "Se modifico la sucursal con id [{0}], para el banco con id [{1}]", new Object[]{s.getIdSucursalBanco(), idBancoCuenta});
                    obtenerSucursalesBanco();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al modificar la sucursal. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo modificar la sucursal.", true, false, false);
                    return false;
                }
            }
        } else {
            s = new SucursalBancoCompras();

            s.setDireccion(direccionSucursal.toUpperCase());
            s.setIdBanco(new BancoCompras(idBancoCuenta));
            s.setIdCiudad(new Ciudad(idCiudad));
            s.setSwift(swiftSucursal.toUpperCase());

            try {
                sucursalBancoComprasFacade.create(s);
                log.log(Level.INFO, "Se creo sucursal con id [{0}], para el banco con id [{1}]", new Object[]{s.getIdSucursalBanco(), idBancoCuenta});
                idSucursalBanco = s.getIdSucursalBanco();
                obtenerSucursalesBanco();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la sucursal. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo crear la sucursal.", true, false, false);
                return false;
            }
        }
        obtenerCuentasSucursal();
        return true;
    }

    private void obtenerCuentasSucursal() {
        cuentas.clear();

        List<CuentaBancariaProveedor> accounts = cuentaBancariaProveedorFacade.obtenerCuentasSucursal(idSucursalBanco);
        if (accounts != null && !accounts.isEmpty()) {
            for (CuentaBancariaProveedor c : accounts) {
                cuentas.add(new CuentaBancariaProveedorDTO(c.getIdCuentaBancaria(), c.getCuentaBancaria(), c.getIban(), c.getAba(),
                        c.getCodProveedor(), new SucursalBancoComprasDTO(c.getIdSucursalBanco().getIdSucursalBanco())));
            }

            Collections.sort(cuentas, new Comparator<CuentaBancariaProveedorDTO>() {
                @Override
                public int compare(CuentaBancariaProveedorDTO t, CuentaBancariaProveedorDTO t1) {
                    return t.getCuentaBancaria().compareTo(t1.getCuentaBancaria());
                }
            });
        }
    }

    public void seleccionarCuentaSucursal() {
        idCuentaSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCuentaSucursal"));
        log.log(Level.INFO, "Se selecciono la cuenta [{0}], de la sucursal bancaria con id [{1}]", new Object[]{idCuentaSucursal, idSucursalBanco});

        if (idCuentaSucursal != null && idCuentaSucursal > 0) {
            for (CuentaBancariaProveedorDTO c : cuentas) {
                if (c.getIdCuentaBancaria().equals(idCuentaSucursal)) {
                    cuentaBancaria = c.getCuentaBancaria();
                    ibanCuentaBancaria = c.getIban();
                    abaCuentaBancaria = c.getAba();
                    break;
                }
            }
        } else if (idCuentaSucursal != null && idCuentaSucursal == -1) {
            log.log(Level.INFO, "Se selecciono crear cuenta para la sucursal con id [{1}]", idCuentaSucursal);
            limpiarPasoCuentaBancaria();
        }
    }

    public void guardarCuentaBancaria() {
//        if (idCuentaSucursal == null || idCuentaSucursal == 0) {
//            mostrarMensaje("Error", "No ha seleccionado una cuenta para crear.", true, false, false);
//            log.log(Level.SEVERE, "No ha seleccionado una cuenta para crear");
//            return;
//        }
        if (cuentaBancaria == null || cuentaBancaria.isEmpty()) {
            mostrarMensaje("Error", "Ingrese una cuenta bancaria.", true, false, false);
            log.log(Level.SEVERE, "Ingrese una cuenta bancaria");
            return;
        }

        CuentaBancariaProveedor c = null;

        if (idCuentaSucursal != null && idCuentaSucursal > 0) {
            c = cuentaBancariaProveedorFacade.find(idCuentaSucursal);

            if (c != null && c.getIdCuentaBancaria() != null && c.getIdCuentaBancaria() != 0) {
                c.setAba(abaCuentaBancaria == null ? null : abaCuentaBancaria.toUpperCase());
                c.setCodProveedor(proforma.getCodProveedor());
                c.setCuentaBancaria(cuentaBancaria.toUpperCase());
                c.setIban(ibanCuentaBancaria == null ? null : ibanCuentaBancaria.toUpperCase());
                c.setIdSucursalBanco(new SucursalBancoCompras(idSucursalBanco));

                try {
                    cuentaBancariaProveedorFacade.edit(c);
                    log.log(Level.INFO, "Se modifico la cuenta con id [{0}], para la sucursal con id [{1}]", new Object[]{c.getIdCuentaBancaria(), idSucursalBanco});
                    obtenerCuentasSucursal();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al modificar la cuenta. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo modificar la cuenta.", true, false, false);
                    return;
                }
            }
        } else {
            c = new CuentaBancariaProveedor();

            c.setAba(abaCuentaBancaria == null ? null : abaCuentaBancaria.toUpperCase());
            c.setCodProveedor(proforma.getCodProveedor());
            c.setCuentaBancaria(cuentaBancaria.toUpperCase());
            c.setIban(ibanCuentaBancaria == null ? null : ibanCuentaBancaria.toUpperCase());
            c.setIdSucursalBanco(new SucursalBancoCompras(idSucursalBanco));

            try {
                cuentaBancariaProveedorFacade.create(c);
                log.log(Level.INFO, "Se creo la cuenta con id [{0}], para la sucursal con id [{1}]", new Object[]{c.getIdCuentaBancaria(), idSucursalBanco});
                idCuentaSucursal = c.getIdCuentaBancaria();
                obtenerCuentasSucursal();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la cuenta. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo crear la cuenta bancaria.", true, false, false);
                return;
            }
        }
    }

    public void irSiguientePaso() {
        if (pasoCrearCuenta == 1) {
            if (!guardarBancoCompras()) {
                return;
            }
        }
        if (pasoCrearCuenta == 2) {
            if (!guardarSucursalBanco()) {
                return;
            }
        }
        pasoCrearCuenta++;
    }

    public void irAnteriorPaso() {
        pasoCrearCuenta--;
    }

    public void cerrarCreacionCuentaBancaria() {
        pasoCrearCuenta = 1;
        idBancoCuenta = null;
        razonSocialBanco = null;
        sucursalesBanco = new ArrayList<>();
        limpiarPasoCuentaBancaria();
        limpiarPasoSucursalCuenta();
        obtenerCuentasProveedor();
    }

    private void limpiarPasoSucursalCuenta() {
        idPais = null;
        idEstado = null;
        idCiudad = null;
        direccionSucursal = null;
        swiftSucursal = null;
        estados = new ArrayList<>();
        ciudades = new ArrayList<>();
        cuentas = new ArrayList<>();
    }

    private void limpiarPasoCuentaBancaria() {
        cuentaBancaria = null;
        ibanCuentaBancaria = null;
        abaCuentaBancaria = null;
    }

    /**
     * **********************CÓDIGO PARA ABRIR TABLA
     * PROFORMA**************************
     */
    public String getAbrirTablaProforma() {
        return applicationMBean.obtenerValorPropiedad("url.web.ruta.tablaProforma");
    }

    /**
     * **********************CÓDIGO PARA DESCARGAR LA
     * PROFORMA**************************
     */
    private void obtenerContenedoresDescargar() {
        contenedoresDescarga.clear();

        for (int i = 0; i < contenedoresProforma.size(); i++) {
            contenedoresDescarga.add(new ContenedorProformaDTO(contenedoresProforma.get(i).getIdContenedorProforma(), "Contenedor (" + (i + 1) + ") - "
                    + contenedoresProforma.get(i).getContenedorProveedor().getContenedor().getNombreCorto()));
        }
    }

    public void seleccionarTipoDescarga() {
        //Si el tipo de descarga es -1 entonces el tipo de descarga es de la proforma completa
        tipoDescarga = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoDescarga"));
        log.log(Level.INFO, "Se selecciono el tipo de descarga [{0}]", tipoDescarga);
    }

    public StreamedContent getDescargarProforma() {
        log.log(Level.INFO, "Descargando proforma con id {0}", idProforma);
        String nombre = "Proforma Invoice " + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo();
        ExcelGeneratorProforma excel = new ExcelGeneratorProforma(nombre, obtenerColumnasExportarExcel(), obtenerEncabezadoExcel(), proforma.getCodProveedor(),
                applicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores"), obtenerDatosExportarExcel(), proforma, applicationMBean, detalleProformaFacade,
                cuentaBancariaProveedorFacade, datosProveedorFacade);
        try {
            excel.generarArchivoExcel();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
        try {
            String file = nombre + ".xlsx";
            InputStream stream = new ByteArrayInputStream(fileToBytes(new File(System.getProperty("jboss.server.temp.dir") + File.separator + file), file));
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", file);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    private List<EncabezadoExcelDTO> obtenerEncabezadoExcel() {
        List<EncabezadoExcelDTO> encabezado = new ArrayList<>();

        List<EncabezadoExcel> enc = encabezadoExcelFacade.obtenerEncabezado("compras");
        if (enc != null && !enc.isEmpty()) {
            DatosProveedor proveedor = datosProveedorFacade.consultarProveedor(proforma.getCodProveedor());
            for (EncabezadoExcel e : enc) {
                encabezado.add(new EncabezadoExcelDTO(e.getIdEncabezadoExcel(), e.getColumnaInicial(), e.getColumnaFinal(), e.getFilaInicial(),
                        e.getFilaFinal(), e.getObjecto(), obtenerValorProveedor(e.getFilaInicial(), e.getColumnaInicial(), e.getValor(), proveedor),
                        e.getAlineadoDerecha(), e.getAlineadoIzquierda(), e.getAlineadoCentro()));
            }
            return encabezado;
        }
        return null;
    }

    private String obtenerValorProveedor(Integer filaInicial, Integer columnaInicial, String valor, DatosProveedor proveedor) {
        if (valor != null && valor.isEmpty()) {
            if (proveedor != null && proveedor.getCodProveedor() != null && !proveedor.getCodProveedor().isEmpty()) {
                if (columnaInicial == 3 && filaInicial == 3) {
                    return proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo();
                } else if (columnaInicial == 3 && filaInicial == 4) {
                    return proveedor.getNombreSocioNegocios();
                } else if (columnaInicial == 3 && filaInicial == 5) {
                    return proveedor.getDireccion();
                } else if (columnaInicial == 3 && filaInicial == 6) {
                    return proveedor.getTelefono();
                } else if (columnaInicial == 3 && filaInicial == 7) {
                    return proveedor.getPersonaContacto();
                } else if (columnaInicial == 3 && filaInicial == 8) {
                    return proveedor.getCorreo();
                } else if (columnaInicial == 3 && filaInicial == 10) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                } else if (columnaInicial == 3 && filaInicial == 9) {
                    return puertoMundoFacade.find(proforma.getIdPuertoSalida()).getNombrePuerto();
                } else if (columnaInicial == 6 && filaInicial == 9) {
                    return incotermFacade.find(proforma.getIdIncoterm()).getCodigo();
                } else if (columnaInicial == 14 && filaInicial == 10) {
                    return puertoMundoFacade.find(proforma.getIdPuertoLlegada()).getNombrePuerto();
                }
            }
        }
        return valor;
    }

    private List<ColumnaDatosProformaDTO> obtenerColumnasExportarExcel() {
        List<ColumnaDatosProformaDTO> cols = new ArrayList<>();
        List<ConfiguracionProforma> confs = configuracionProformaFacade.buscarPorCodigoProveedor(proforma.getCodProveedor(), idProforma);

        if (confs != null && !confs.isEmpty()) {
            for (ConfiguracionProforma c : confs) {
                if (cols != null && !cols.isEmpty() && cols.get(0).getNombre().equals(c.getIdColumna().getNombre1())) {
                    if (cols.get(0).getColumnas() == null) {
                        cols.get(0).setColumnas(new ArrayList<ColumnaProformaDTO>());
                    }

                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        cols.get(0).getColumnas().add(new ColumnaProformaDTO(0, c.getIdColumna().getIdColumna(),
                                c.getIdColumna().getDecimalesVisibles(),
                                c.getIdColumna().getIdOperacionColumna() == null ? null : c.getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(),
                                c.getIdColumna().getNombre1(), c.getIdColumna().getNombre1Ingles(), c.getIdColumna().getNombre2(),
                                c.getIdColumna().getNombre2Ingles(), c.getIdColumna().getLongitudOcupadaTabla(),
                                c.getIdColumna().getTipoCantidad(), c.getIdColumna().getPermitirCrearItem(),
                                c.getIdColumna().getRequiereOperacion(), c.getIdColumna().getTipoItem(), c.getIdColumna().getTipoIdentificador(),
                                c.getIdColumna().getTipoImagen(), c.getIdColumna().getObligatoria(), c.getIdColumna().getIncluirProforma(),
                                c.getIdColumna().getModificable(), c.getIdColumna().getModificableSiNuevo(), c.getIdColumna().getTipoValorTotal(),
                                c.getIdColumna().getTipoCBM(), c.getIdColumna().getTipoValorUnitario(), c.getIdColumna().getTipoDescuento(),
                                c.getIdColumna().getIdTipoDato() != null
                                        ? new TipoDatosDTO(c.getIdColumna().getIdTipoDato().getIdTipoDato(), c.getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                null));
                    }
                } else {
                    cols.add(0, new ColumnaDatosProformaDTO(c.getOrden(),
                            c.getIdColumna().getLongitudOcupadaTabla() == null || c.getIdColumna().getLongitudOcupadaTabla().isEmpty() ? "100px"
                                    : c.getIdColumna().getLongitudOcupadaTabla(), c.getIdColumna().getNombre1(),
                            c.getIdColumna().getNombre1Ingles(), new ArrayList<ColumnaProformaDTO>()));

                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        cols.get(0).getColumnas().add(new ColumnaProformaDTO(0, c.getIdColumna().getIdColumna(),
                                c.getIdColumna().getDecimalesVisibles(),
                                c.getIdColumna().getIdOperacionColumna() == null ? null : c.getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(),
                                c.getIdColumna().getNombre1(), c.getIdColumna().getNombre1Ingles(), c.getIdColumna().getNombre2(),
                                c.getIdColumna().getNombre2Ingles(), c.getIdColumna().getLongitudOcupadaTabla(),
                                c.getIdColumna().getTipoCantidad(), c.getIdColumna().getPermitirCrearItem(),
                                c.getIdColumna().getRequiereOperacion(), c.getIdColumna().getTipoItem(), c.getIdColumna().getTipoIdentificador(),
                                c.getIdColumna().getTipoImagen(), c.getIdColumna().getObligatoria(), c.getIdColumna().getIncluirProforma(),
                                c.getIdColumna().getModificable(), c.getIdColumna().getModificableSiNuevo(), c.getIdColumna().getTipoValorTotal(),
                                c.getIdColumna().getTipoCBM(), c.getIdColumna().getTipoValorUnitario(), c.getIdColumna().getTipoDescuento(),
                                c.getIdColumna().getIdTipoDato() != null
                                        ? new TipoDatosDTO(c.getIdColumna().getIdTipoDato().getIdTipoDato(), c.getIdColumna().getIdTipoDato().getTipoDato()) : null, null));
                    }
                }
            }
            Collections.sort(cols, new Comparator<ColumnaDatosProformaDTO>() {
                @Override
                public int compare(ColumnaDatosProformaDTO t, ColumnaDatosProformaDTO t1) {
                    return t.getPosicion() - t1.getPosicion();
                }
            });

            //Inicia bloque de contenedores, aplicara si y solo si la PI tiene mas de un contenedor
            if (tipoDescarga == -1) {
                List<ContenedorProforma> containers = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

                if (containers != null && containers.size() > 1) {
                    for (int i = 0; i < containers.size(); i++) {
                        ContenedorProforma c = containers.get(i);

                        cols.add(new ColumnaDatosProformaDTO(cols.size() + 1, "100px",
                                "Contenedor (" + (i + 1) + ") - " + c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                "Container (" + (i + 1) + ") - " + c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(), new ArrayList<ColumnaProformaDTO>()));

                        cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Cantidad", "Quantity", "100px", false, false,
                                false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                        cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "CBM", "CBM", "100px", false, false,
                                false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                        cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Total", "Total", "100px", false, false,
                                false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                    }
                }
            } else {
                List<ContenedorProforma> containers = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

                if (containers != null && containers.size() > 1) {
                    for (int i = 0; i < containers.size(); i++) {
                        ContenedorProforma c = containers.get(i);

                        if (c.getIdContenedorProforma().equals(tipoDescarga)) {
                            cols.add(new ColumnaDatosProformaDTO(cols.size() + 1, "100px",
                                    "Contenedor (" + (i + 1) + ") - " + c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                    "Container (" + (i + 1) + ") - " + c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(), new ArrayList<ColumnaProformaDTO>()));

                            cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Cantidad", "Quantity", "100px", false, false,
                                    false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                            cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "CBM", "CBM", "100px", false, false,
                                    false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                            cols.get(cols.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Total", "Total", "100px", false, false,
                                    false, false, false, false, false, false, false, false, false, false, false, false, null, null));

                            break;
                        }
                    }
                }
            }
        }
        return cols;
    }

    private List<DatosProformaDTO> obtenerDatosExportarExcel() {
        List<DatosProformaDTO> datosProf = new ArrayList<>();

        if (tipoDescarga == -1) {
            Integer lineas = detalleProformaFacade.maxRegistros(proforma.getIdProforma());

            if (lineas > 0) {
                for (int i = 0; i <= lineas; i++) {
                    DatosProformaDTO d = new DatosProformaDTO(i, lineaIgnoradaProformaFacade.consultarExistencia(idProforma, i) != null ? false : true, new ArrayList<DetalleProformaDTO>());
                    List<DetalleProforma> det = detalleProformaFacade.consultarXIdProformaLinea(proforma.getIdProforma(), i);
                    if (det != null && !det.isEmpty()) {
                        for (DetalleProforma dp : det) {
                            d.getDetalleProforma().add(new DetalleProformaDTO(dp.getIdDetalleProforma(),
                                    dp.getIdConfiguracion().getIdConfiguracion(), dp.getLineNum(),
                                    dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(), dp.getValor(),
                                    dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(),
                                    dp.getIdConfiguracion().getIdColumna().getIdTipoDato() == null ? null : dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato(),
                                    false, false,
                                    dp.getIdConfiguracion().getIdColumna() != null
                                            ? new ColumnaProformaDTO(0, dp.getIdConfiguracion().getIdColumna().getIdColumna(),
                                                    dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(),
                                                    dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null
                                                            ? dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                                                    dp.getIdConfiguracion().getIdColumna().getNombre1(), dp.getIdConfiguracion().getIdColumna().getNombre1Ingles(),
                                                    dp.getIdConfiguracion().getIdColumna().getNombre2(), dp.getIdConfiguracion().getIdColumna().getNombre2Ingles(),
                                                    dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla(),
                                                    dp.getIdConfiguracion().getIdColumna().getTipoCantidad(), dp.getIdConfiguracion().getIdColumna().getPermitirCrearItem(),
                                                    dp.getIdConfiguracion().getIdColumna().getRequiereOperacion(), dp.getIdConfiguracion().getIdColumna().getTipoItem(),
                                                    dp.getIdConfiguracion().getIdColumna().getTipoIdentificador(), dp.getIdConfiguracion().getIdColumna().getTipoImagen(),
                                                    dp.getIdConfiguracion().getIdColumna().getObligatoria(), dp.getIdConfiguracion().getIdColumna().getIncluirProforma(),
                                                    dp.getIdConfiguracion().getIdColumna().getModificable(), dp.getIdConfiguracion().getIdColumna().getModificableSiNuevo(),
                                                    dp.getIdConfiguracion().getIdColumna().getTipoValorTotal(), dp.getIdConfiguracion().getIdColumna().getTipoCBM(),
                                                    dp.getIdConfiguracion().getIdColumna().getTipoValorUnitario(), dp.getIdConfiguracion().getIdColumna().getTipoDescuento(),
                                                    dp.getIdConfiguracion().getIdColumna().getIdTipoDato() != null
                                                            ? new TipoDatosDTO(dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getIdTipoDato(),
                                                                    dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                                    null) : null));
                        }

                        List<ContenedorProforma> containers = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

                        if (containers != null && containers.size() > 1) {
                            for (ContenedorProforma c : containers) {
                                Object[] o = contenedorLineaFacade.obtenerDatosLineaContenedor(proforma.getIdProforma(), i, c.getIdContenedorProforma());
                                d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 0, (String) o[0], "100px", "Integer", false, false, null));
                                d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 2, (String) o[1], "100px", "Double", false, false, null));
                                d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 2, (String) o[2], "100px", "Double", false, false, null));
                            }
                        }

                    }
                    datosProf.add(d);
                }
            }
        } else {
            List<Object[]> lineas = contenedorLineaFacade.obtenerCantidadesLineaContenedor(tipoDescarga);

            if (lineas != null && !lineas.isEmpty()) {
                for (Object[] i : lineas) {
                    DatosProformaDTO d = new DatosProformaDTO((Integer) i[0], lineaIgnoradaProformaFacade.consultarExistencia(idProforma, (Integer) i[0]) != null ? false : true, new ArrayList<DetalleProformaDTO>());
                    List<DetalleProforma> det = detalleProformaFacade.consultarXIdProformaLinea(proforma.getIdProforma(), (Integer) i[0]);
                    if (det != null && !det.isEmpty()) {
                        for (DetalleProforma dp : det) {
                            d.getDetalleProforma().add(new DetalleProformaDTO(dp.getIdDetalleProforma(), dp.getIdConfiguracion().getIdConfiguracion(),
                                    dp.getLineNum(), dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(), dp.getValor(),
                                    dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() != null ? dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() : "100px",
                                    dp.getIdConfiguracion().getIdColumna().getIdTipoDato() != null ? dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato() : null,
                                    dp.getIdConfiguracion().getIdColumna().getTipoImagen(), false,
                                    new ColumnaProformaDTO(0, dp.getIdConfiguracion().getIdColumna().getIdColumna(),
                                            dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(),
                                            dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null
                                                    ? dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                                            dp.getIdConfiguracion().getIdColumna().getNombre1(), dp.getIdConfiguracion().getIdColumna().getNombre1Ingles(),
                                            dp.getIdConfiguracion().getIdColumna().getNombre2(), dp.getIdConfiguracion().getIdColumna().getNombre2Ingles(),
                                            dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() != null ? dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() : "100px",
                                            dp.getIdConfiguracion().getIdColumna().getTipoCantidad(), dp.getIdConfiguracion().getIdColumna().getPermitirCrearItem(),
                                            dp.getIdConfiguracion().getIdColumna().getRequiereOperacion(), dp.getIdConfiguracion().getIdColumna().getTipoItem(),
                                            dp.getIdConfiguracion().getIdColumna().getTipoIdentificador(), dp.getIdConfiguracion().getIdColumna().getTipoImagen(),
                                            dp.getIdConfiguracion().getIdColumna().getObligatoria(), dp.getIdConfiguracion().getIdColumna().getIncluirProforma(),
                                            dp.getIdConfiguracion().getIdColumna().getModificable(), dp.getIdConfiguracion().getIdColumna().getModificableSiNuevo(),
                                            dp.getIdConfiguracion().getIdColumna().getTipoValorTotal(), dp.getIdConfiguracion().getIdColumna().getTipoCBM(),
                                            dp.getIdConfiguracion().getIdColumna().getTipoValorUnitario(), dp.getIdConfiguracion().getIdColumna().getTipoDescuento(),
                                            dp.getIdConfiguracion().getIdColumna().getIdTipoDato() != null
                                                    ? new TipoDatosDTO(dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getIdTipoDato(),
                                                            dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                            null)));
                        }
                        d.getDetalleProforma().add(new DetalleProformaDTO(null, null, (Integer) i[0], ((Integer) i[1]).toString()));
                    }
                    datosProf.add(d);
                }
            }
        }

        return datosProf;
    }

    private byte[] fileToBytes(File file, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) file.length()];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            log.log(Level.SEVERE, ex.getMessage());
        }
        byte[] bytes = bos.toByteArray();
        File someFile = new File(nombreArchivo);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
        return buf;
    }

    /**
     * ******************Inicia codigo de cierre para la
     * proforma*********************
     */
    public void validarCierreProforma() {
        obtenerPuertosColombia();
        obtenerPuertosPaisProveedor();
        obtenerIncotermsDisponibles();
    }

    private void obtenerPuertosColombia() {
        puertosColombianos = new ArrayList<>();
        List<PuertoMundo> puertos = puertoMundoFacade.obtenerPuertosPais(47);

        if (puertos != null && !puertos.isEmpty()) {
            for (PuertoMundo p : puertos) {
                puertosColombianos.add(new PuertoMundoDTO(p.getIdPuertoMundo(), p.getIdPais().getIdPais(), p.getNombrePuerto()));
            }

            Collections.sort(puertosColombianos, new Comparator<PuertoMundoDTO>() {
                @Override
                public int compare(PuertoMundoDTO t, PuertoMundoDTO t1) {
                    return t.getNombrePuerto().compareTo(t1.getNombrePuerto());
                }
            });
        }
    }

    private void obtenerPuertosPaisProveedor() {
        if (proforma.getIdPuertoSalida() != null && proforma.getIdPuertoSalida() != 0) {
            PuertoMundo puerto = puertoMundoFacade.obtenerPuertoPais(proforma.getIdPuertoSalida());

            if (puerto != null && puerto.getIdPuertoMundo() != null && puerto.getIdPuertoMundo() != 0) {
                idPaisPuertoSalida = puerto.getIdPais().getIdPais();
                obtenerPuertos(idPaisPuertoSalida);
            }
        } else {
            obtenerPuertos(paisesFacade.obtenerPaisProveedor(proforma.getCodProveedor()));
        }
    }

    private void obtenerPuertos(Integer idPaisPuerto) {
        puertosProveedor = new ArrayList<>();
        List<PuertoMundo> puertos = puertoMundoFacade.obtenerPuertosPais(idPaisPuerto);

        if (puertos != null && !puertos.isEmpty()) {
            for (PuertoMundo p : puertos) {
                puertosProveedor.add(new PuertoMundoDTO(p.getIdPuertoMundo(), p.getIdPais().getIdPais(), p.getNombrePuerto()));
            }

            Collections.sort(puertosProveedor, new Comparator<PuertoMundoDTO>() {
                @Override
                public int compare(PuertoMundoDTO t, PuertoMundoDTO t1) {
                    return t.getNombrePuerto().compareTo(t1.getNombrePuerto());
                }
            });
        }
    }

    public void seleccionarPaisPuertoSalida() {
        idPaisPuertoSalida = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPais"));
        log.log(Level.INFO, "Se selecciono el pais de puerto de salida [{0}]", idPaisPuertoSalida);
        obtenerPuertos(idPaisPuertoSalida);
    }

    private void obtenerIncotermsDisponibles() {
        incoterms = new ArrayList<>();
        List<Incoterm> incoterm = incotermFacade.findAll();

        if (incoterm != null && !incoterm.isEmpty()) {
            for (Incoterm i : incoterm) {
                incoterms.add(new IncotermDTO(i.getIdIncoterm(), i.getCodigo(), i.getDescripcion()));
            }

            Collections.sort(incoterms, new Comparator<IncotermDTO>() {
                @Override
                public int compare(IncotermDTO t, IncotermDTO t1) {
                    return t.getCodigo().compareTo(t1.getCodigo());
                }
            });
        }
    }

    public void seleccionarPuertoSalida() {
        proforma.setIdPuertoSalida(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPuertoMundo")));
        log.log(Level.INFO, "Se selecciono el puerto de salida con id {0}", proforma.getIdPuertoSalida());

        ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

        if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
            pi.setIdPuertoSalida(new PuertoMundo(proforma.getIdPuertoSalida()));

            try {
                proformaInvoiceFacade.edit(pi);
                log.log(Level.INFO, "Se inserto el puerto de salida a la pi {0}", idProforma);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al insertar el puerto de salida. Error {0}", e.getMessage());
                return;
            }
        }
    }

    public void seleccionarPuertoLlegada() {
        proforma.setIdPuertoLlegada(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPuertoMundo")));
        log.log(Level.INFO, "Se selecciono el puerto de llegada con id {0}", proforma.getIdPuertoLlegada());

        ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

        if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
            pi.setIdPuertoLlegada(new PuertoMundo(proforma.getIdPuertoLlegada()));

            try {
                proformaInvoiceFacade.edit(pi);
                log.log(Level.INFO, "Se inserto el puerto de llegada a la pi {0}", idProforma);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al insertar el puerto de llegada. Error {0}", e.getMessage());
                return;
            }
        }
    }

    public void seleccionarIncoterm() {
        proforma.setIdIncoterm(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idIncoterm")));
        log.log(Level.INFO, "Se selecciono el incoterm con id {0}", proforma.getIdIncoterm());

        ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

        if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
            pi.setIdIncoterm(new Incoterm(proforma.getIdIncoterm()));

            try {
                proformaInvoiceFacade.edit(pi);
                log.log(Level.INFO, "Se inserto el incoterm a la pi {0}", idProforma);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al insertar el incoterm. Error {0}", e.getMessage());
                return;
            }
        }
    }

    public void cerrarProforma() {
        /*Se valida que la proforma no se encuentre en estado cerrado*/
        if (!(proforma.getEstado().equals("PP") || proforma.getEstado().equals("AP") || proforma.getEstado().equals("PR"))) {
            mostrarMensaje("Error", "La proforma ya se encuentra en un estado cerrado o aprobado.", true, false, false);
            log.log(Level.SEVERE, "La proforma ya se encuentra en un estado cerrado o aprobado");
            return;
        }
        /*Se valida que el CBM no supere lo permitido*/
        if (proforma.getCbmTotal() > cbmMaximo) {
            mostrarMensaje("Error", "La proforma no se puede cerrar, debido a que esta tiene un error en el CBM con respecto a los contenedores asignados.", true, false, false);
            log.log(Level.SEVERE, "La proforma no se puede cerrar, debido a que esta tiene un error en el CBM con respecto a los contenedores asignados");
            return;
        }
        /*Validar que los contenedores tengan las unidades solicitadas*/
        for (int i = 1; i <= itemsDisponibles; i++) {
            itemActivo = i;
            obtenerContenedoresLinea();

            if (lineaProforma.getCantidadUsada() > lineaProforma.getCantidadNecesaria()) {
                itemActivo = 1;
                obtenerDatosProformas();
                mostrarMensaje("Error", "Verifique, un ítem tiene asignada una cantidad a los contenedores, superior a la que se va a pedir. Ítem: " + (i), true, false, false);
                log.log(Level.SEVERE, "Verifique, un item tiene asignada una cantidad a los contenedores, superior a la que se va a pedir. Item: [{0}]", (i));
                return;
            } else if (lineaProforma.getCantidadNecesaria() > lineaProforma.getCantidadUsada()) {
                itemActivo = 1;
                obtenerDatosProformas();
                mostrarMensaje("Error", "Verifique, un ítem tiene asignada una cantidad a los contenedores, inferior a la que se va a pedir. Ítem: " + (i), true, false, false);
                log.log(Level.SEVERE, "Verifique, un item tiene asignada una cantidad a los contenedores, inferior a la que se va a pedir. Item: [{0}]", (i));
                return;
            }
        }
        itemActivo = 1;
        obtenerDatosProformas();
        /*Validar si no se esta abusando del CBM de un contenedor*/
        for (ContenedorProformaDTO c : contenedoresProforma) {
            if (c.getCbmAcumulado() > c.getContenedorProveedor().getContenedor().getCBMMaximo()) {
                mostrarMensaje("Error", "Verifique en la pestaña contenedores, a que contenedor se le esta asignando más de lo que soporta de CBM.", true, false, false);
                log.log(Level.SEVERE, "Verfique en la pestaña contenedores, a que contenedor se le esta asignando mas de lo que soporta de CBM");
                return;
            }
        }

        if (proforma.getTerminosPago() == null || proforma.getTerminosPago().isEmpty()) {
            mostrarMensaje("Error", "Ingrese los términos de pago para poder cerrar la proforma.", true, false, false);
            log.log(Level.SEVERE, "Ingrese los terminos de pago para poder cerrar la proforma");
            cancelarCierreProforma();
            return;
        }
        if (proforma.getTerminosEntrega() == null || proforma.getTerminosEntrega().isEmpty()) {
            mostrarMensaje("Error", "Ingrese los términos de entrega para poder cerrar la proforma.", true, false, false);
            log.log(Level.SEVERE, "Ingrese los terminos de entrega para poder cerrar la proforma");
            cancelarCierreProforma();
            return;
        }
        if (proforma.getIdPuertoSalida() == null || proforma.getIdPuertoSalida() == 0) {
            mostrarMensaje("Error", "Seleccione un puerto de salida para poder cerrar la proforma.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un puerto de salida para poder cerrar la proforma");
            cancelarCierreProforma();
            return;
        }
        if (proforma.getIdPuertoLlegada() == null || proforma.getIdPuertoLlegada() == 0) {
            mostrarMensaje("Error", "Seleccione un puerto de llegada para poder cerrar la proforma.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un puerto de llegada para poder cerrar la proforma");
            cancelarCierreProforma();
            return;
        }
        if (proforma.getIdIncoterm() == null || proforma.getIdIncoterm() == 0) {
            mostrarMensaje("Error", "Seleccione un incoterm para poder cerrar la proforma.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un incoterm para poder cerrar la proforma");
            cancelarCierreProforma();
            return;
        }

        ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

        if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
            pi.setIdIncoterm(new Incoterm(proforma.getIdIncoterm()));
            pi.setIdPuertoLlegada(new PuertoMundo(proforma.getIdPuertoLlegada()));
            pi.setIdPuertoSalida(new PuertoMundo(proforma.getIdPuertoSalida()));
            pi.setTerminosPago(proforma.getTerminosPago());
            pi.setTerminosEntrega(proforma.getTerminosEntrega());
            pi.setEstado("AP");

            try {
                proformaInvoiceFacade.edit(pi);
                log.log(Level.INFO, "Se cerro la proforma con id {0}", idProforma);
                mostrarMensaje("Éxito", "La proforma está en estado pendiente por aprobación.", false, true, false);
                log.log(Level.INFO, "La proforma con id {0}, esta en estado pendiente por aprobacion", idProforma);
                enviarEmails();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al cerrar la proforma actual, comuníquese con el departamento de sistemas.", true, false, false);
                log.log(Level.SEVERE, "Ocurrio un error al cerrar la proforma con id {0}, por completo. Error {1}", new Object[]{idProforma, e.getMessage()});
                cancelarCierreProforma();
            }
        }
    }

    public void cancelarCierreProforma() {
        pasoCerrarPI = 1;
    }

    public void aplicarSiguientePasoCerrarPI() {
        if (pasoCerrarPI < 3) {
            if (pasoCerrarPI == 1) {
                if (proforma.getTerminosPago() == null || proforma.getTerminosPago().isEmpty()) {
                    mostrarMensaje("Error", "Ingrese los términos de pago para poder cerrar la proforma.", true, false, false);
                    log.log(Level.SEVERE, "Ingrese los terminos de pago para poder cerrar la proforma");
                    return;
                }
                if (proforma.getTerminosEntrega() == null || proforma.getTerminosEntrega().isEmpty()) {
                    mostrarMensaje("Error", "Ingrese los términos de entrega para poder cerrar la proforma.", true, false, false);
                    log.log(Level.SEVERE, "Ingrese los terminos de entrega para poder cerrar la proforma");
                    return;
                }

                ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

                if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
                    pi.setTerminosPago(proforma.getTerminosPago());
                    pi.setTerminosEntrega(proforma.getTerminosEntrega());

                    try {
                        proformaInvoiceFacade.edit(pi);
                        log.log(Level.INFO, "Se actualizaron los terminos de compra para la id {0}", idProforma);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Error al actualizar los terminos de pago. Error {0}", e.getMessage());
                        return;
                    }
                }
            } else if (pasoCerrarPI == 2) {
                if (proforma.getIdPuertoSalida() == null || proforma.getIdPuertoSalida() == 0) {
                    mostrarMensaje("Error", "Seleccione un puerto de salida para poder cerrar la proforma.", true, false, false);
                    log.log(Level.SEVERE, "Seleccione un puerto de salida para poder cerrar la proforma");
                    return;
                }
                if (proforma.getIdPuertoLlegada() == null || proforma.getIdPuertoLlegada() == 0) {
                    mostrarMensaje("Error", "Seleccione un puerto de llegada para poder cerrar la proforma.", true, false, false);
                    log.log(Level.SEVERE, "Seleccione un puerto de llegada para poder cerrar la proforma");
                    return;
                }
            }
            pasoCerrarPI++;
        }
    }

    public void aplicarAnteriorPasoCerrarPI() {
        if (pasoCerrarPI > 1) {
            pasoCerrarPI--;
        }
    }

    private void enviarEmails() {
        marcarNotificaciones();

        /*Se obtienen los correos de las personas implicadas en la aprobacion de la proforma*/
        String emails = applicationMBean.obtenerValorPropiedad("proforma.autorizacion.emails");
        if (emails != null && !emails.isEmpty()) {
            for (String email : emails.split(";")) {
                String valores[] = email.split(",");
                registrarBDAprobacion(valores[0], valores[1]);
            }
        }
    }

    private void enviarNotificacionEmail(String email, String token) {
        MailMessageDTO mail = new MailMessageDTO();
        mail.setFrom("Proformas baru <proformas@matisses.co>");
        mail.setSubject("Proforma " + datosProveedorFacade.consultarProveedor(proforma.getCodProveedor()).getNombreSocioNegocios() + " - " + proforma.getAnio() + " - " + proforma.getConsecutivo());
        mail.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("autorizacion_proforma").getTo().get(0));
        mail.addBccAddress(email);

        Map<String, String> params = new HashMap<>();
        params.put("proforma", datosProveedorFacade.consultarProveedor(proforma.getCodProveedor()).getNombreSocioNegocios() + " - " + proforma.getAnio() + " - " + proforma.getConsecutivo());
        params.put("userName", userSessionInfoMBean.getUsuario());
        params.put("url", applicationMBean.obtenerValorPropiedad("url.aprobacion.proforma") + "?proforma=" + idProforma + "&token=" + token);

        try {
            emailSender.sendMail(mail, SendHTMLEmailMBean.MessageTemplate.autorizacion_proforma, params, null);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible enviar el correo de notificacion para la proforma.", e);
            return;
        }
    }

    private void registrarBDAprobacion(String email, String nombreUsuario) {
        NotificacionProforma notificacion = new NotificacionProforma();

        notificacion.setCorreoUsuario(email);
        notificacion.setIdProforma(new ProformaInvoice(idProforma));
        notificacion.setToken(RandomStringUtils.randomAlphanumeric(20));
        notificacion.setNombreUsuario(nombreUsuario);
        notificacion.setActivo(true);

        try {
            notificacionProformaFacade.create(notificacion);
            enviarNotificacionEmail(email, notificacion.getToken());
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }

    private void marcarNotificaciones() {
        List<NotificacionProforma> notifications = notificacionProformaFacade.obtenerNotificacionesAnular(idProforma);

        if (notifications != null && !notifications.isEmpty()) {
            for (NotificacionProforma n : notifications) {
                n.setActivo(false);

                try {
                    notificacionProformaFacade.edit(n);
                    log.log(Level.INFO, "Se coloco como inactiva la notificacion proforma con id {0}", n.getIdNotificacionProforma());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al inactivar la notificaicon proforma con id {0}. Error {1}", new Object[]{n.getIdNotificacionProforma(), e.getMessage()});
                    return;
                }
            }
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
