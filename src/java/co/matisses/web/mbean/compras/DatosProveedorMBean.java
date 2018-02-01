package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.AnticipoCuenta;
import co.matisses.persistence.web.entity.BancoCompras;
import co.matisses.persistence.web.entity.Ciudad;
import co.matisses.persistence.web.entity.ContenedorProforma;
import co.matisses.persistence.web.entity.CuentaBancariaProveedor;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.entity.Estados;
import co.matisses.persistence.web.entity.Paises;
import co.matisses.persistence.web.entity.Prioridad;
import co.matisses.persistence.web.entity.ProformaInvoice;
import co.matisses.persistence.web.entity.SucursalBancoCompras;
import co.matisses.persistence.web.entity.TipoMoneda;
import co.matisses.persistence.web.entity.TransaccionBancaria;
import co.matisses.persistence.web.entity.VersionTransaccionBancaria;
import co.matisses.persistence.web.facade.AnticipoCuentaFacade;
import co.matisses.persistence.web.facade.BancoComprasFacade;
import co.matisses.persistence.web.facade.CiudadFacade;
import co.matisses.persistence.web.facade.ContenedorProformaFacade;
import co.matisses.persistence.web.facade.CuentaBancariaProveedorFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.EstadosFacade;
import co.matisses.persistence.web.facade.PaisesFacade;
import co.matisses.persistence.web.facade.PrioridadFacade;
import co.matisses.persistence.web.facade.ProformaInvoiceFacade;
import co.matisses.persistence.web.facade.SucursalBancoComprasFacade;
import co.matisses.persistence.web.facade.TipoMonedaFacade;
import co.matisses.persistence.web.facade.TransaccionBancariaFacade;
import co.matisses.persistence.web.facade.VersionTransaccionBancariaFacade;
import co.matisses.web.dto.AnticipoCuentaDTO;
import co.matisses.web.dto.BancoComprasDTO;
import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.dto.ContenedorDTO;
import co.matisses.web.dto.ContenedorProformaDTO;
import co.matisses.web.dto.ContenedorProveedorDTO;
import co.matisses.web.dto.CuentaBancariaProveedorDTO;
import co.matisses.web.dto.DatosProveedorDTO;
import co.matisses.web.dto.DetalleProformaDTO;
import co.matisses.web.dto.EstadosDTO;
import co.matisses.web.dto.PaisesDTO;
import co.matisses.web.dto.PrioridadDTO;
import co.matisses.web.dto.ProformaInvoiceDTO;
import co.matisses.web.dto.SucursalBancoComprasDTO;
import co.matisses.web.dto.TipoMonedaDTO;
import co.matisses.web.dto.TransaccionBancariaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.poi.ExcelGeneratorInformeProformas;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.FileUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "datosProveedorMBean")
public class DatosProveedorMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruApplicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(DatosProveedorMBean.class.getSimpleName());
    private Integer estado;
    private Integer pais;
    private Integer idCuentaBancaria;
    private Integer idTipoMoneda;
    private Integer idProforma;
    private Integer idBancoCuenta;
    private Integer paisSucursal;
    private Integer estadoSucursal;
    private Integer ciudadSucursal;
    private Integer idSucursal;
    private Integer idCuenta;
    private Integer idTransaccionBancaria;
    private Integer idAnticipoCuenta;
    private Integer pasoCrearCuenta;
    private Double porcentajeAnticipo;
    private Double totalProforma;
    private String anticipo;
    private String balance;
    private String totalGirar;
    private String tipoGiro;
    private String anticipoCuenta;
    private String codProveedor;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String correo;
    private String personaContacto;
    private String imagenLogo;
    private String rutaImagen;
    private String nombreSocioNegocios;
    private String parametroBusqueda;
    private String tipoExportacion = "";
    private String tipoDato = "";
    private String dato = "";
    private String filtro = "";
    private String valorFiltro = "";
    private String descripcionPago;
    private String comentarioAnticipoCuenta;
    private String razonSocialBanco;
    private String swiftSucursal;
    private String direccionSucursal;
    private String cuentaBancaria;
    private String abaCuenta;
    private String ibanCuenta;
    private String anticipoCuentaDisponible;
    private String tipoAnticipoCuenta;
    private boolean fotoNueva = false;
    private boolean mostrarBtnExportar = false;
    private boolean dlgBusqueda = false;
    private boolean dlgTransaccion = false;
    private boolean editandoTransaccionBancaria = false;
    private boolean editandoAnticipoCuenta = false;
    private Date fechaFiltro;
    private Date fechaGiro;
    private Part logoFile;
    private TransaccionBancariaDTO transaccionVisible;
    private ProformaInvoiceDTO proformaSeleccionada;
    private List<PaisesDTO> paises;
    private List<EstadosDTO> estados;
    private List<DatosProveedor> proveedores;
    private List<DatosProveedorDTO> datosProveedor;
    private List<ProformaInvoiceDTO> proformas;
    private List<PrioridadDTO> prioridades;
    private List<CuentaBancariaProveedorDTO> cuentas;
    private List<AnticipoCuentaDTO> anticiposCuenta;
    private List<TipoMonedaDTO> tiposMonedas;
    private List<BancoComprasDTO> bancos;
    private List<SucursalBancoComprasDTO> sucursales;
    private List<CuentaBancariaProveedorDTO> cuentasSucursal;
    private List<EstadosDTO> estadosSucursal;
    private List<CiudadDTO> ciudadesSucursal;
    @EJB
    private EstadosFacade estadosFacade;
    @EJB
    private PaisesFacade paisesFacade;
    @EJB
    private DatosProveedorFacade datosProveedorFacade;
    @EJB
    private TransaccionBancariaFacade transaccionBancariaFacade;
    @EJB
    private ProformaInvoiceFacade proformaInvoiceFacade;
    @EJB
    private ContenedorProformaFacade contenedorProformaFacade;
    @EJB
    private PrioridadFacade prioridadFacade;
    @EJB
    private CuentaBancariaProveedorFacade cuentaBancariaProveedorFacade;
    @EJB
    private AnticipoCuentaFacade anticipoCuentaFacade;
    @EJB
    private TipoMonedaFacade tipoMonedaFacade;
    @EJB
    private BancoComprasFacade bancoComprasFacade;
    @EJB
    private SucursalBancoComprasFacade sucursalBancoComprasFacade;
    @EJB
    private CiudadFacade ciudadFacade;
    @EJB
    private VersionTransaccionBancariaFacade versionTransaccionBancariaFacade;

    public DatosProveedorMBean() {
        pasoCrearCuenta = 1;
        paises = new ArrayList<>();
        proveedores = new ArrayList<>();
        datosProveedor = new ArrayList<>();
        proformas = new ArrayList<>();
        prioridades = new ArrayList<>();
        cuentas = new ArrayList<>();
        transaccionVisible = new TransaccionBancariaDTO();
        anticiposCuenta = new ArrayList<>();
        tiposMonedas = new ArrayList<>();
        proformaSeleccionada = new ProformaInvoiceDTO();
        bancos = new ArrayList<>();
        sucursales = new ArrayList<>();
        estadosSucursal = new ArrayList<>();
        ciudadesSucursal = new ArrayList<>();
        cuentasSucursal = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerProveedores();
        obtenerPaises();
        obtenerPrioridades();
    }

    public BaruApplicationMBean getBaruApplicationMBean() {
        return baruApplicationMBean;
    }

    public void setBaruApplicationMBean(BaruApplicationMBean baruApplicationMBean) {
        this.baruApplicationMBean = baruApplicationMBean;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public Integer getEstado() {
        return estado;
    }

    public String getEstadoSeleccionado() {
        if (estado != null && estado != 0) {
            for (EstadosDTO e : estados) {
                if (e.getIdEstado().equals(estado)) {
                    return e.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getPais() {
        return pais;
    }

    public String getPaisSeleccionado() {
        if (pais != null && pais != 0) {
            for (PaisesDTO p : paises) {
                if (p.getIdPais().equals(pais)) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdCuentaBancaria() {
        return idCuentaBancaria;
    }

    public String getIdCuentaBancariaSeleccionada() {
        if (idCuentaBancaria != null && idCuentaBancaria != 0) {
            for (CuentaBancariaProveedorDTO c : cuentas) {
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

    public Integer getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public String getIdTipoMonedaSeleccionada() {
        if (idTipoMoneda != null && idTipoMoneda != 0) {
            for (TipoMonedaDTO t : tiposMonedas) {
                if (t.getIdTipoMoneda().equals(idTipoMoneda)) {
                    return t.getSimbolo() + " - " + t.getMoneda();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public String getIdProformaSeleccionada() {
        if (idProforma != null && idProforma != 0) {
            for (ProformaInvoiceDTO p : proformas) {
                if (p.getIdProforma().equals(idProforma)) {
                    return p.getCodProveedor() + " - " + p.getAnio() + " - " + p.getConsecutivo();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdBancoCuenta() {
        return idBancoCuenta;
    }

    public String getIdBancoSeleccionado() {
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

    public Integer getPaisSucursal() {
        return paisSucursal;
    }

    public String getPaisSucursalSeleccionado() {
        if (paisSucursal != null && paisSucursal != 0) {
            for (PaisesDTO p : paises) {
                if (p.getIdPais().equals(paisSucursal)) {
                    if (p.getNombre().length() > 19) {
                        return p.getNombre().substring(0, 19) + "...";
                    }
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getEstadoSucursal() {
        return estadoSucursal;
    }

    public String getEstadoSucursalSeleccionado() {
        if (estadoSucursal != null && estadoSucursal != 0) {
            for (EstadosDTO e : estados) {
                if (e.getIdEstado().equals(estadoSucursal)) {
                    if (e.getNombre().length() > 19) {
                        return e.getNombre().substring(0, 19) + "...";
                    }
                    return e.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getCiudadSucursal() {
        return ciudadSucursal;
    }

    public String getCiudadSucursalSeleccionada() {
        if (ciudadSucursal != null && ciudadSucursal != 0) {
            for (CiudadDTO c : ciudadesSucursal) {
                if (c.getIdCiudad().equals(ciudadSucursal)) {
                    if (c.getNombre().length() > 19) {
                        return c.getNombre().substring(0, 19) + "...";
                    }
                    return c.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdSucursal() {
        return idSucursal;
    }

    public String getIdSucursalSeleccionada() {
        if (idSucursal != null && idSucursal > 0) {
            for (SucursalBancoComprasDTO s : sucursales) {
                if (s.getIdSucursalBanco().equals(idSucursal)) {
                    return s.getDireccion();
                }
            }
        } else if (idSucursal != null && idSucursal == -1) {
            return "Nueva sucursal";
        }
        return "Seleccione";
    }

    public Integer getIdCuenta() {
        return idCuenta;
    }

    public String getIdCuentaSeleccionada() {
        if (idCuenta != null && idCuenta > 0) {
            for (CuentaBancariaProveedorDTO c : cuentasSucursal) {
                if (c.getIdCuentaBancaria().equals(idCuenta)) {
                    return c.getCuentaBancaria();
                }
            }
        } else if (idCuenta != null && idCuenta == -1) {
            return "Nueva cuenta";
        }
        return "Seleccione";
    }

    public Integer getIdTransaccionBancaria() {
        return idTransaccionBancaria;
    }

    public void setIdTransaccionBancaria(Integer idTransaccionBancaria) {
        this.idTransaccionBancaria = idTransaccionBancaria;
    }

    public Integer getIdAnticipoCuenta() {
        return idAnticipoCuenta;
    }

    public void setIdAnticipoCuenta(Integer idAnticipoCuenta) {
        this.idAnticipoCuenta = idAnticipoCuenta;
    }

    public Integer getPasoCrearCuenta() {
        return pasoCrearCuenta;
    }

    public void setPasoCrearCuenta(Integer pasoCrearCuenta) {
        this.pasoCrearCuenta = pasoCrearCuenta;
    }

    public Double getPorcentajeAnticipo() {
        return porcentajeAnticipo;
    }

    public void setPorcentajeAnticipo(Double porcentajeAnticipo) {
        this.porcentajeAnticipo = porcentajeAnticipo;
    }

    public Double getTotalProforma() {
        return totalProforma;
    }

    public void setTotalProforma(Double totalProforma) {
        this.totalProforma = totalProforma;
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

    public String getTotalGirar() {
        return totalGirar == null ? null : baruGenericMBean.getValorFormateado("Double", totalGirar, 2);
    }

    public void setTotalGirar(String totalGirar) {
        this.totalGirar = totalGirar;
    }

    public String getTipoGiro() {
        return tipoGiro;
    }

    public String getTipoGiroSeleccionado() {
        if (tipoGiro != null && !tipoGiro.isEmpty()) {
            switch (tipoGiro.toLowerCase()) {
                case "anticipo":
                    return "Anticipo";
                case "balance":
                    return "Balance";
                case "roll on":
                    return "Roll on";
                default:
                    return "Selecciono";
            }
        }
        return "Seleccione";
    }

    public String getAnticipoCuenta() {
        return anticipoCuenta == null ? null : baruGenericMBean.getValorFormateado("Double", anticipoCuenta, 2);
    }

    public void setAnticipoCuenta(String anticipoCuenta) {
        this.anticipoCuenta = anticipoCuenta;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getImagenLogo() {
        return imagenLogo;
    }

    public void setImagenLogo(String imagenLogo) {
        this.imagenLogo = imagenLogo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getNombreSocioNegocios() {
        return nombreSocioNegocios;
    }

    public void setNombreSocioNegocios(String nombreSocioNegocios) {
        this.nombreSocioNegocios = nombreSocioNegocios;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getTipoExportacion() {
        return tipoExportacion;
    }

    public String getTipoExportacionSeleccionada() {
        if (tipoExportacion != null && !tipoExportacion.isEmpty()) {
            switch (tipoExportacion) {
                case "general":
                    return "General";
                case "proveedor":
                    return "Proveedor";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public String getTipoDatoSeleccionado() {
        if (tipoDato != null && !tipoDato.isEmpty()) {
            switch (tipoDato) {
                case "general":
                    return "General";
                case "proforma":
                    return "Proforma";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getFiltro() {
        return filtro;
    }

    public String getFiltroSeleccionado() {
        if (filtro != null && !filtro.isEmpty()) {
            switch (filtro) {
                case "fecha":
                    return "Fecha";
                case "prioridad":
                    return "Prioridad";
                case "vencidas":
                    return "Vencidas";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public String getValorFiltro() {
        return valorFiltro;
    }

    public void setValorFiltro(String valorFiltro) {
        this.valorFiltro = valorFiltro;
    }

    public String getDescripcionPago() {
        return descripcionPago;
    }

    public void setDescripcionPago(String descripcionPago) {
        this.descripcionPago = descripcionPago;
    }

    public String getComentarioAnticipoCuenta() {
        return comentarioAnticipoCuenta;
    }

    public void setComentarioAnticipoCuenta(String comentarioAnticipoCuenta) {
        this.comentarioAnticipoCuenta = comentarioAnticipoCuenta;
    }

    public String getRazonSocialBanco() {
        return razonSocialBanco;
    }

    public void setRazonSocialBanco(String razonSocialBanco) {
        this.razonSocialBanco = razonSocialBanco;
    }

    public String getSwiftSucursal() {
        return swiftSucursal;
    }

    public void setSwiftSucursal(String swiftSucursal) {
        this.swiftSucursal = swiftSucursal;
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

    public String getAbaCuenta() {
        return abaCuenta;
    }

    public void setAbaCuenta(String abaCuenta) {
        this.abaCuenta = abaCuenta;
    }

    public String getIbanCuenta() {
        return ibanCuenta;
    }

    public void setIbanCuenta(String ibanCuenta) {
        this.ibanCuenta = ibanCuenta;
    }

    public String getAnticipoCuentaDisponible() {
        return anticipoCuentaDisponible;
    }

    public void setAnticipoCuentaDisponible(String anticipoCuentaDisponible) {
        this.anticipoCuentaDisponible = anticipoCuentaDisponible;
    }

    public String getTipoAnticipoCuenta() {
        return tipoAnticipoCuenta;
    }

    public void setTipoAnticipoCuenta(String tipoAnticipoCuenta) {
        this.tipoAnticipoCuenta = tipoAnticipoCuenta;
    }

    public boolean isFotoNueva() {
        return fotoNueva;
    }

    public void setFotoNueva(boolean fotoNueva) {
        this.fotoNueva = fotoNueva;
    }

    public boolean isMostrarBtnExportar() {
        return mostrarBtnExportar;
    }

    public void setMostrarBtnExportar(boolean mostrarBtnExportar) {
        this.mostrarBtnExportar = mostrarBtnExportar;
    }

    public boolean isDlgBusqueda() {
        return dlgBusqueda;
    }

    public void setDlgBusqueda(boolean dlgBusqueda) {
        this.dlgBusqueda = dlgBusqueda;
    }

    public boolean isDlgTransaccion() {
        return dlgTransaccion;
    }

    public void setDlgTransaccion(boolean dlgTransaccion) {
        this.dlgTransaccion = dlgTransaccion;
    }

    public boolean isEditandoTransaccionBancaria() {
        return editandoTransaccionBancaria;
    }

    public void setEditandoTransaccionBancaria(boolean editandoTransaccionBancaria) {
        this.editandoTransaccionBancaria = editandoTransaccionBancaria;
    }

    public boolean isEditandoAnticipoCuenta() {
        return editandoAnticipoCuenta;
    }

    public void setEditandoAnticipoCuenta(boolean editandoAnticipoCuenta) {
        this.editandoAnticipoCuenta = editandoAnticipoCuenta;
    }

    public Date getFechaFiltro() {
        return fechaFiltro;
    }

    public void setFechaFiltro(Date fechaFiltro) {
        this.fechaFiltro = fechaFiltro;
    }

    public Date getFechaGiro() {
        return fechaGiro;
    }

    public void setFechaGiro(Date fechaGiro) {
        this.fechaGiro = fechaGiro;
    }

    public Part getLogoFile() {
        return logoFile;
    }

    public void setLogoFile(Part logoFile) {
        this.logoFile = logoFile;
    }

    public TransaccionBancariaDTO getTransaccionVisible() {
        return transaccionVisible;
    }

    public void setTransaccionVisible(TransaccionBancariaDTO transaccionVisible) {
        this.transaccionVisible = transaccionVisible;
    }

    public ProformaInvoiceDTO getProformaSeleccionada() {
        return proformaSeleccionada;
    }

    public void setProformaSeleccionada(ProformaInvoiceDTO proformaSeleccionada) {
        this.proformaSeleccionada = proformaSeleccionada;
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

    public List<DatosProveedor> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<DatosProveedor> proveedores) {
        this.proveedores = proveedores;
    }

    public List<DatosProveedorDTO> getDatosProveedor() {
        return datosProveedor;
    }

    public void setDatosProveedor(List<DatosProveedorDTO> datosProveedor) {
        this.datosProveedor = datosProveedor;
    }

    public List<ProformaInvoiceDTO> getProformas() {
        return proformas;
    }

    public void setProformas(List<ProformaInvoiceDTO> proformas) {
        this.proformas = proformas;
    }

    public List<PrioridadDTO> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(List<PrioridadDTO> prioridades) {
        this.prioridades = prioridades;
    }

    public List<CuentaBancariaProveedorDTO> getCuentas() {
        return cuentas;
    }

    public void setCuentas(List<CuentaBancariaProveedorDTO> cuentas) {
        this.cuentas = cuentas;
    }

    public List<AnticipoCuentaDTO> getAnticiposCuenta() {
        return anticiposCuenta;
    }

    public void setAnticiposCuenta(List<AnticipoCuentaDTO> anticiposCuenta) {
        this.anticiposCuenta = anticiposCuenta;
    }

    public List<TipoMonedaDTO> getTiposMonedas() {
        return tiposMonedas;
    }

    public void setTiposMonedas(List<TipoMonedaDTO> tiposMonedas) {
        this.tiposMonedas = tiposMonedas;
    }

    public List<BancoComprasDTO> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoComprasDTO> bancos) {
        this.bancos = bancos;
    }

    public List<SucursalBancoComprasDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<SucursalBancoComprasDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public List<CuentaBancariaProveedorDTO> getCuentasSucursal() {
        return cuentasSucursal;
    }

    public void setCuentasSucursal(List<CuentaBancariaProveedorDTO> cuentasSucursal) {
        this.cuentasSucursal = cuentasSucursal;
    }

    public List<EstadosDTO> getEstadosSucursal() {
        return estadosSucursal;
    }

    public void setEstadosSucursal(List<EstadosDTO> estadosSucursal) {
        this.estadosSucursal = estadosSucursal;
    }

    public List<CiudadDTO> getCiudadesSucursal() {
        return ciudadesSucursal;
    }

    public void setCiudadesSucursal(List<CiudadDTO> ciudadesSucursal) {
        this.ciudadesSucursal = ciudadesSucursal;
    }

    public void cambiarTag() {
        limpiar();
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
        if (codProveedor != null && !codProveedor.isEmpty()) {
            if (logoFile != null) {
                try {
                    log.log(Level.INFO, "Recibiendo archivo: " + fileName(logoFile));
                    log.log(Level.INFO, "Tipo contenido: " + logoFile.getContentType());
                    if (logoFile.getContentType().contains("image/jpeg") || logoFile.getContentType().contains("image/png")) {
                        DefaultStreamedContent imagen = new DefaultStreamedContent(logoFile.getInputStream());
                        File almacenarImagen = new File(baruApplicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores") + File.separator + codProveedor + ".jpg");
                        //Validar tamaño permitido de imagen
                        BufferedImage bi = ImageIO.read(logoFile.getInputStream());
                        int width = bi.getWidth();
                        int height = bi.getHeight();
                        if (width > 538) {
                            log.log(Level.SEVERE, "La imagen que esta intentando subir es demaciado ancha. Ancho permitido hasta 260px");
                            mostrarMensaje("Error imagen", "La imagen que está intentando subir es demasiado ancha. Ancho permitido hasta 260px", true, false, false);
                            logoFile = null;
                            return;
                        }
                        if (height > 250) {
                            log.log(Level.SEVERE, "La imagen que esta intentando subir es demaciado alta. Alto permitido hasta 260px");
                            mostrarMensaje("Error imagen", "La imagen que está intentando subir es demasiado alta. Alto permitido hasta 260px", true, false, false);
                            logoFile = null;
                            return;
                        }
                        FileUtils.copyInputStreamToFile(imagen.getStream(), almacenarImagen);
                        fotoNueva = true;
                        obtenerRutaImagen(almacenarImagen.getName());
                    } else {
                        fotoNueva = false;
                        mostrarMensaje("Extensión invalida", "La extensión no es valida, formato permitido jpg o png", true, false, false);
                        log.log(Level.SEVERE, "La extensión no es valida, formato permitido jpg o png");
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al subir la imagen. Error [{0}]", e.getMessage());
                    logoFile = null;
                    return;
                }
            } else {
                fotoNueva = false;
                mostrarMensaje("Sin foto", "No ha subido ninguna imagen para cargar", true, false, false);
                log.log(Level.SEVERE, "No ha subido ninguna imagen para cargar");
                logoFile = null;
            }
        }
    }

    public void obtenerRutaImagen(String img) {
        if (fotoNueva) {
            rutaImagen = baruApplicationMBean.obtenerValorPropiedad("baru.fotos.proveedores") + img;
        } else {
            File f = new File(baruApplicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores") + File.separator + img);

            if (f.exists()) {
                try {
                    rutaImagen = baruApplicationMBean.obtenerValorPropiedad("baru.fotos.proveedores") + img;
                } catch (Exception e) {
                    log.log(Level.SEVERE, "No se encontro la imagen", e);
                    rutaImagen = "";
                }
            } else {
                log.log(Level.SEVERE, "No se encontro la imagen");
                rutaImagen = "";
            }
        }
    }

    /**
     * *************EMPIEZA CÓDIGO FICHA CREAR PROVEEDOR*************
     */
    public void guardarProveedor() {
        if (codProveedor == null || codProveedor.isEmpty()) {
            mostrarMensaje("Falta Código Proveedor", "Verifique que haya ingresado un valor en el campo Código Proveedor", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Codigo Proveedor");
            return;
        }
        if (nombreSocioNegocios == null || nombreSocioNegocios.isEmpty()) {
            mostrarMensaje("Falta Nombre Socio Negocios", "Verifique que haya ingresado un valor en el campo Nombre Socio Negocios", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Nombre Socio Negocios");
            return;
        }
        if (razonSocial == null || razonSocial.isEmpty()) {
            mostrarMensaje("Falta Nombre Proveedor", "Verifique que haya ingresado un valor en el campo Nombre Proveedor", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Nombre Proveedor");
            return;
        }
        if (direccion == null || direccion.isEmpty()) {
            mostrarMensaje("Falta Dirección", "Verifique que haya ingresado un valor en el campo Dirección", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Direccion");
            return;
        }
        if (telefono == null || telefono.isEmpty()) {
            mostrarMensaje("Falta Teléfono", "Verifique que haya ingresado un valor en el campo Teléfono", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Telefono");
            return;
        }
        if (correo == null || correo.isEmpty()) {
            mostrarMensaje("Falta Correo", "Verifique que haya ingresado un valor en el campo Correo", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Correo");
            return;
        }
        if (personaContacto == null || personaContacto.isEmpty()) {
            mostrarMensaje("Falta Persona Contacto", "Verifique que haya ingresado un valor en el campo Persona Contacto", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Persona Contacto");
            return;
        }
        if (pais == null || pais == 0) {
            mostrarMensaje("Falta País", "Verifique que haya seleccionado un valor en el campo País", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya seleccionado un valor en el campo Pais");
            return;
        }
        if (estado == null || estado == 0) {
            mostrarMensaje("Falta Estado", "Verifique que haya seleccionado un valor en el campo Estado", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya seleccionado un valor en el campo Estado");
            return;
        }

        DatosProveedor d = new DatosProveedor();
        d.setCodProveedor(codProveedor);
        d.setCorreo(correo.toUpperCase());
        d.setDireccion(direccion.toUpperCase());
        d.setEstado(new Estados(estado));
        d.setLogo(codProveedor + ".jpg");
        d.setPersonaContacto(personaContacto.toUpperCase());
        d.setRazonSocial(razonSocial.toUpperCase());
        d.setTelefono(telefono.toUpperCase());
        d.setNombreSocioNegocios(nombreSocioNegocios.toUpperCase());

        try {
            DatosProveedor dp = datosProveedorFacade.find(codProveedor);
            if (dp != null && dp.getCodProveedor() != null && !dp.getCodProveedor().isEmpty()) {
                mostrarMensaje("Datos Existentes", "No se puede guardar debido a que el proveedor ya existe prov:" + codProveedor, true, false, false);
                log.log(Level.SEVERE, "No se puede guardar debido a que el proveedor ya existe prov: [{0}]", codProveedor);
                return;
            } else {
                datosProveedorFacade.create(d);
                mostrarMensaje("Éxito", "Se ha guardado correctamente el proveedor " + codProveedor, false, true, false);
                log.log(Level.INFO, "Se a guardado correctamente el proveedor [{0}]", codProveedor);
            }
            limpiar();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return;
        }
    }

    public void limpiar() {
        estado = null;
        pais = null;
        codProveedor = null;
        razonSocial = null;
        nombreSocioNegocios = null;
        direccion = null;
        telefono = null;
        correo = null;
        personaContacto = null;
        imagenLogo = null;
        paises = new ArrayList<>();
        obtenerPaises();
        estados = new ArrayList<>();
        fotoNueva = false;
        logoFile = null;
        rutaImagen = null;
    }

    /**
     * *************EMPIEZA CÓDIGO FICHA ADMINISTRAR - BUSCAR Y FINALIZA CÓDIGO
     * FICHA CREAR PROVEEDOR*************
     */
    public void buscarProveedor() {
        limpiar();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            datosProveedor.clear();
            parametroBusqueda = parametroBusqueda.toUpperCase();
            for (DatosProveedor dp : proveedores) {
                if (dp.getCodProveedor() != null && dp.getCodProveedor().contains(parametroBusqueda)) {
                    datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios() == null ? "" : dp.getNombreSocioNegocios(),
                            dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                            dp.getPersonaContacto(), dp.getLogo(), dp.getEstado() == null ? 0 : dp.getEstado().getIdEstado()));
                } else if (dp.getEstado() != null && obtenerNombreEstado(dp.getEstado().getIdEstado()).contains(parametroBusqueda)) {
                    datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios(), dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                            dp.getPersonaContacto(), dp.getLogo(), dp.getEstado().getIdEstado()));
                } else if (dp.getNombreSocioNegocios() != null && dp.getNombreSocioNegocios().contains(parametroBusqueda)) {
                    datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios(), dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                            dp.getPersonaContacto(), dp.getLogo(), dp.getEstado().getIdEstado()));
                } else if (dp.getPersonaContacto() != null && dp.getPersonaContacto().contains(parametroBusqueda)) {
                    datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios(), dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                            dp.getPersonaContacto(), dp.getLogo(), dp.getEstado().getIdEstado()));
                } else if (dp.getRazonSocial() != null && dp.getRazonSocial().contains(parametroBusqueda)) {
                    datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios(), dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                            dp.getPersonaContacto(), dp.getLogo(), dp.getEstado().getIdEstado()));
                }
            }

            if (datosProveedor.size() > 0) {
                dlgBusqueda = true;
            } else {
                dlgBusqueda = false;
                mostrarMensaje("Error", "No se encontraron datos coincidentes con: " + parametroBusqueda, true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos coincidentes con: [{0}]", parametroBusqueda);
                return;
            }
        } else {
            datosProveedor.clear();
            for (DatosProveedor dp : proveedores) {
                datosProveedor.add(new DatosProveedorDTO(dp.getCodProveedor(), dp.getRazonSocial(), dp.getNombreSocioNegocios() == null ? "" : dp.getNombreSocioNegocios(),
                        dp.getDireccion(), dp.getTelefono(), dp.getCorreo(),
                        dp.getPersonaContacto(), dp.getLogo(), dp.getEstado() == null ? 0 : dp.getEstado().getIdEstado()));
            }
        }
    }

    private String obtenerNombreEstado(Integer idEstado) {
        if (idEstado != null && idEstado != 0) {
            Estados est = estadosFacade.find(idEstado);
            if (est != null && est.getIdEstado() != null && est.getIdEstado() != 0) {
                return est.getNombre();
            }
        }
        return "";
    }

    // INICIA CÓDIGO MODAL BUSQUEDA
    public void seleccionarProveedor() {
        codProveedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codProveedor");
        log.log(Level.INFO, "Se selecciono el proveedor [{0}], para tratarlo", codProveedor);

        if (codProveedor != null && !codProveedor.isEmpty()) {
            DatosProveedor prov = datosProveedorFacade.consultarProveedor(codProveedor);
            if (prov != null && prov.getCodProveedor() != null && !prov.getCodProveedor().isEmpty()) {
                if (prov.getEstado() != null && prov.getEstado().getIdEstado() != null && prov.getEstado().getIdEstado() != 0) {
                    pais = prov.getEstado().getIdPais().getIdPais();
                    obtenerEstados(pais);
                    estado = prov.getEstado().getIdEstado();
                }
                razonSocial = prov.getRazonSocial();
                direccion = prov.getDireccion();
                telefono = prov.getTelefono();
                correo = prov.getCorreo();
                personaContacto = prov.getPersonaContacto();
                imagenLogo = prov.getLogo();
                nombreSocioNegocios = prov.getNombreSocioNegocios();

                obtenerProformaProveedor();
                obtenerAnticiposCuenta();
                obtenerRutaImagen(codProveedor + ".jpg");
            }
        }
    }

    private void obtenerProformaProveedor() {
        proformas = new ArrayList<>();
        List<ProformaInvoice> profs = proformaInvoiceFacade.obtenerProformasProveedor(codProveedor);
        for (ProformaInvoice p : profs) {
            Double valorPendiente = transaccionBancariaFacade.obtenerValorPendiente(p.getIdProforma());
            proformas.add(new ProformaInvoiceDTO(p.getIdProforma(), p.getConsecutivo(), p.getCbmTotal(), p.getValorTotal(),
                    p.getValorTotalDescuento(), (p.getValorTotal() != null ? p.getValorTotal() : 0) - (valorPendiente != null ? valorPendiente : 0),
                    transaccionBancariaFacade.obtenerValorTransferido(p.getIdProforma()), p.getCodProveedor(), p.getAnio(),
                    p.getUsuario(), p.getEstado(), null, p.getComentario(), p.getTipoProducto(), p.getFecha(),
                    p.getIdTipoMoneda() == null ? null
                            : new TipoMonedaDTO(p.getIdTipoMoneda().getIdTipoMoneda(), p.getIdTipoMoneda().getSimbolo(), p.getIdTipoMoneda().getMoneda()),
                    new ArrayList<DetalleProformaDTO>(), new ArrayList<TransaccionBancariaDTO>(), new ArrayList<ContenedorProformaDTO>()));
        }
        obtenerMovimientosBancariosYContenedores();
        obtenerCuentasProveedor();
        obtenerTiposMoneda();
        Collections.sort(proformas, new Comparator<ProformaInvoiceDTO>() {
            @Override
            public int compare(ProformaInvoiceDTO t, ProformaInvoiceDTO t1) {
                return t1.getConsecutivo().compareTo(t.getConsecutivo());
            }
        });
    }

    private void obtenerMovimientosBancariosYContenedores() {
        for (ProformaInvoiceDTO dto : proformas) {
            dto.setTransacciones(new ArrayList<TransaccionBancariaDTO>());
            dto.setContenedores(new ArrayList<ContenedorProformaDTO>());

            List<TransaccionBancaria> trans = transaccionBancariaFacade.transaccionesProforma(dto.getIdProforma());

            for (TransaccionBancaria t : trans) {
                dto.getTransacciones().add(new TransaccionBancariaDTO(t.getIdTransaccionBancaria(), t.getCodProveedor(),
                        t.getIdCuenta().getIdCuentaBancaria(), t.getTipoGiro(), t.getIdProformaInvoice().getIdProforma(), t.getTotalInvoice(),
                        t.getPorcentajeAnticipo(), t.getAnticipo(), t.getAnticipoCuenta(), t.getBalance(), t.getTotalGiro(), t.getDescripcion(),
                        t.getUsuario(), t.getEstado(), obtenerNombreDocumentoTransaccionBancaria(t.getIdTransaccionBancaria()), t.getFecha(),
                        t.getFechaGiro(), validarPdfExistencia(t.getIdTransaccionBancaria()), t.getFirmado(), t.getCancelado()));
            }

            List<ContenedorProforma> contProfs = contenedorProformaFacade.contenedoresProforma(dto.getIdProforma());
            if (contProfs != null && !contProfs.isEmpty()) {
                List<ContenedorProveedorDTO> cont = new ArrayList<>();
                for (ContenedorProforma contPro : contProfs) {
                    dto.getContenedores().add(new ContenedorProformaDTO(contPro.getIdContenedorProforma(), contPro.getIdProforma().getIdProforma(),
                            contPro.getTiempoProduccion(), contPro.getTiempoTransito(),
                            contPro.getValorTotal(), contPro.getCbmAcumulado(), contPro.getFechaEmbarque(), contPro.getFechaVencimiento(), contPro.getFechaMaxPago(),
                            new ContenedorProveedorDTO(contPro.getIdContenedorProveedor().getIdContenedorProveedor(),
                                    contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(), 1, contPro.getIdContenedorProveedor().getCodigoProveedor(),
                                    new ContenedorDTO(contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getNombre(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getCargaMaxima(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getCapacidadCubica()))));
                    if (cont.isEmpty()) {
                        cont.add(new ContenedorProveedorDTO(contPro.getIdContenedorProveedor().getIdContenedorProveedor(),
                                contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(), 1, contPro.getIdContenedorProveedor().getCodigoProveedor(),
                                new ContenedorDTO(contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(), contPro.getIdContenedorProveedor().getIdContenedor().getNombre(),
                                        contPro.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                        contPro.getIdContenedorProveedor().getIdContenedor().getCargaMaxima(), contPro.getIdContenedorProveedor().getIdContenedor().getCapacidadCubica())));
                    } else {
                        boolean existe = true;
                        for (ContenedorProveedorDTO c : cont) {
                            if (contPro.getIdContenedorProveedor().getIdContenedorProveedor().equals(c.getIdContenedorProveedor())) {
                                c.setCantidad(c.getCantidad() + 1);
                                existe = true;
                                break;
                            } else {
                                existe = false;
                            }
                        }
                        if (!existe) {
                            cont.add(new ContenedorProveedorDTO(contPro.getIdContenedorProveedor().getIdContenedorProveedor(),
                                    contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(), 1, contPro.getIdContenedorProveedor().getCodigoProveedor(),
                                    new ContenedorDTO(contPro.getIdContenedorProveedor().getIdContenedor().getIdContenedor(), contPro.getIdContenedorProveedor().getIdContenedor().getNombre(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getNombreCorto(),
                                            contPro.getIdContenedorProveedor().getIdContenedor().getCargaMaxima(), contPro.getIdContenedorProveedor().getIdContenedor().getCapacidadCubica())));
                        }
                    }
                }

                for (ContenedorProveedorDTO c : cont) {
                    dto.setContenedoresConsolidados(dto.getContenedoresConsolidados() + c.getCantidad() + " * " + c.getContenedor().getNombreCorto() + " + ");
                }
            }
        }
    }

    private boolean validarPdfExistencia(Integer idTransferencia) {
        if (idTransferencia != null && idTransferencia != 0) {
            VersionTransaccionBancaria v = versionTransaccionBancariaFacade.obtenerUltimaVersionTransaccionBancaria(idTransferencia);

            if (v != null && v.getIdVersionTransaccionBancaria() != null && v.getIdVersionTransaccionBancaria() != 0) {
                File file = new File(baruApplicationMBean.obtenerValorPropiedad("url.local.transaccionesBancarias") + v.getNombreDocumento());

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
                return baruApplicationMBean.obtenerValorPropiedad("url.web.transaccionesBancarias") + v.getNombreDocumento();
            }
        }
        return null;
    }

//    private boolean validarPdfExistencia(Integer idTransferencia) {
//        if (idTransferencia != null && idTransferencia != 0) {
//            File file = new File(baruApplicationMBean.obtenerValorPropiedad("url.local.transaccionesBancarias") + idTransferencia + ".pdf");
//
//            if (file.exists()) {
//                return true;
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }
    public String obtenerRutaLogoProv(String proveedor) {
        if (proveedor != null & !proveedor.isEmpty()) {
            File f = new File(baruApplicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores") + "\\" + proveedor + ".jpg");

            if (f.exists()) {
                return baruApplicationMBean.obtenerValorPropiedad("baru.fotos.proveedores") + proveedor + ".jpg";
            } else {
                return baruApplicationMBean.obtenerValorPropiedad("url.web.noimage");
            }
        }
        return baruApplicationMBean.obtenerValorPropiedad("url.web.noimage");
    }

    private void obtenerCuentasProveedor() {
        cuentas.clear();

        List<CuentaBancariaProveedor> accounts = cuentaBancariaProveedorFacade.cuentasProveedor(codProveedor);

        if (accounts != null && !accounts.isEmpty()) {
            for (CuentaBancariaProveedor a : accounts) {
                cuentas.add(new CuentaBancariaProveedorDTO(a.getIdCuentaBancaria(), a.getCuentaBancaria(), a.getIban(), a.getAba(),
                        a.getCodProveedor(), new SucursalBancoComprasDTO(a.getIdSucursalBanco().getIdSucursalBanco(),
                                a.getIdSucursalBanco().getDireccion(), a.getIdSucursalBanco().getSwift(),
                                new BancoComprasDTO(a.getIdSucursalBanco().getIdBanco().getIdBanco(), a.getIdSucursalBanco().getIdBanco().getRazonSocial()),
                                a.getIdSucursalBanco().getIdCiudad() == null ? null : new CiudadDTO(a.getIdSucursalBanco().getIdCiudad().getIdCiudad(), a.getIdSucursalBanco().getIdCiudad().getNombre(),
                                                a.getIdSucursalBanco().getIdCiudad() == null ? null : new EstadosDTO(a.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdEstado(),
                                                                a.getIdSucursalBanco().getIdCiudad().getIdEstado().getNombre(),
                                                                a.getIdSucursalBanco().getIdCiudad() == null ? null : new PaisesDTO(a.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getIdPais(),
                                                                                a.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getNombre(),
                                                                                a.getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getContinente()))))));
            }
        }
    }

    private void obtenerTiposMoneda() {
        tiposMonedas = new ArrayList<>();

        List<TipoMoneda> tiposMoneda = tipoMonedaFacade.findAll();

        if (tiposMoneda != null && !tiposMoneda.isEmpty()) {
            for (TipoMoneda t : tiposMoneda) {
                tiposMonedas.add(new TipoMonedaDTO(t.getIdTipoMoneda(), t.getSimbolo(), t.getMoneda()));
            }
        }
    }

    //FINALIZA CÓDIGO MODAL BUSQUEDA
    /**
     * *************FINALIZA CÓDIGO FICHA ADMINISTRAR - BUSCAR Y EMPIEZA CÓDIGO
     * FICHA ADMINISTRAR - DATOS PROVEEDOR*************
     */
    public void seleccionarPaisProveedor() {
        pais = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPais"));
        log.log(Level.INFO, "Se selecciono el pais con id [{0}], para actualizar el proveedor");
        obtenerEstados(pais);
    }

    public void seleccionarEstadoProveedor() {
        estado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEstado"));
        log.log(Level.INFO, "Se selecciono el estado con el id [{0}], para actualiza el proveedor");
    }

    public void actualizarProveedor() {
        if (nombreSocioNegocios == null || nombreSocioNegocios.isEmpty()) {
            mostrarMensaje("Falta Nombre Socio Negocios", "Verifique que haya ingresado un valor en el campo Nombre Socio Negocios", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Nombre Socio Negocios");
            return;
        }
        if (razonSocial == null || razonSocial.isEmpty()) {
            mostrarMensaje("Falta Nombre Proveedor", "Verifique que haya ingresado un valor en el campo Nombre Proveedor", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Nombre Proveedor");
            return;
        }
        if (direccion == null || direccion.isEmpty()) {
            mostrarMensaje("Falta Dirección", "Verifique que haya ingresado un valor en el campo Dirección", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Direccion");
            return;
        }
        if (telefono == null || telefono.isEmpty()) {
            mostrarMensaje("Falta Teléfono", "Verifique que haya ingresado un valor en el campo Teléfono", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Telefono");
            return;
        }
        if (correo == null || correo.isEmpty()) {
            mostrarMensaje("Falta Correo", "Verifique que haya ingresado un valor en el campo Correo", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Correo");
            return;
        }
        if (personaContacto == null || personaContacto.isEmpty()) {
            mostrarMensaje("Falta Persona Contacto", "Verifique que haya ingresado un valor en el campo Persona Contacto", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya ingresado un valor en el campo Persona Contacto");
            return;
        }
        if (pais == null || pais == 0) {
            mostrarMensaje("Falta País", "Verifique que haya seleccionado un valor en el campo País", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya seleccionado un valor en el campo Pais");
            return;
        }
        if (estado == null || estado == 0) {
            mostrarMensaje("Falta Estado", "Verifique que haya seleccionado un valor en el campo Estado", true, false, false);
            log.log(Level.SEVERE, "Verifique que haya seleccionado un valor en el campo Estado");
            return;
        }

        DatosProveedor d = datosProveedorFacade.find(codProveedor);
        d.setCodProveedor(codProveedor);
        d.setCorreo(correo.toUpperCase());
        d.setDireccion(direccion.toUpperCase());
        d.setEstado(new Estados(estado));
        d.setLogo(codProveedor + ".jpg");
        d.setPersonaContacto(personaContacto.toUpperCase());
        d.setRazonSocial(razonSocial.toUpperCase());
        d.setTelefono(telefono.toUpperCase());
        d.setNombreSocioNegocios(nombreSocioNegocios.toUpperCase());

        try {
            datosProveedorFacade.edit(d);
            mostrarMensaje("Éxito", "Se actualizo correctamente el proveedor " + codProveedor, false, true, false);
            log.log(Level.INFO, "Se a actualizo correctamente el proveedor [{0}]", codProveedor);
            limpiar();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return;
        }
    }

    /**
     * *************FINALIZA CÓDIGO FICHA ADMINISTRAR - DATOS PROVEEDOR Y
     * EMPIEZA CÓDIGO FICHA ADMINISTRAR - CONTENEDORES*************
     */
    public void actualizarComentarioProforma(Integer idProforma) {
        if (idProforma != null && idProforma != 0) {
            for (ProformaInvoiceDTO p : proformas) {
                if (p.getIdProforma().equals(idProforma)) {
                    ProformaInvoice pi = proformaInvoiceFacade.find(idProforma);

                    if (pi != null && pi.getIdProforma() != null && pi.getIdProforma() != 0) {
                        pi.setComentario(p.getComentario());

                        try {
                            proformaInvoiceFacade.edit(pi);
                            log.log(Level.INFO, "Se modifico comentario a la proforma: [{0}]", idProforma);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al actualizar el comentario de la proforma: [{0}], error: [{1}]", new Object[]{idProforma, e.getMessage()});
                            return;
                        }
                    }
                }
            }
        }
    }

    public void modificarFechaContenedor(Integer idContenedorProforma, Date fechaEmbarque, Date fechaVencimiento, Date fechaMaxPago) {
        if (idContenedorProforma != null && idContenedorProforma != 0) {
            ContenedorProforma cont = contenedorProformaFacade.find(idContenedorProforma);
            if (cont != null && cont.getIdContenedorProforma() != null && cont.getIdContenedorProforma() != 0) {
                if (fechaEmbarque != null) {
                    cont.setFechaEmbarque(fechaEmbarque);
                } else if (fechaVencimiento != null) {
                    cont.setFechaVencimiento(fechaVencimiento);
                } else if (fechaMaxPago != null) {
                    cont.setFechaMaxPago(fechaMaxPago);
                }

                try {
                    contenedorProformaFacade.edit(cont);
                    log.log(Level.INFO, "Se modifico una fecha del contenedor");
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            }
        }
    }

    public void actualizarTiempos(Integer idContenedorProforma, Integer tiempoProduccion, Integer tiempoTransito) {
        if (idContenedorProforma != null && idContenedorProforma != 0) {
            ContenedorProforma cont = contenedorProformaFacade.find(idContenedorProforma);
            if (cont != null && cont.getIdContenedorProforma() != null && cont.getIdContenedorProforma() != 0) {
                if (tiempoProduccion != null && tiempoProduccion != 0) {
                    cont.setTiempoProduccion(tiempoProduccion);
                } else if (tiempoTransito != null && tiempoTransito != 0) {
                    cont.setTiempoTransito(tiempoTransito);
                }

                try {
                    contenedorProformaFacade.edit(cont);
                    log.log(Level.INFO, "Se modifico un tiempo del contenedor");
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            }
        }
    }

    public long calcularDiasVencidos(Date fechaVencimiento) {
        if (fechaVencimiento != null) {
            final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;
            if (fechaVencimiento.before(new Date())) {
                return (new Date().getTime() - fechaVencimiento.getTime()) / MILLSECS_PER_DAY;
            }
        }
        return 0;
    }

    /**
     * *************FINALIZA CÓDIGO FICHA - CONTENEDORES Y EMPIEZA CÓDIGO FICHA
     * ADMINISTRAR - TRANSACCIONES BANCARIAS*************
     */
    public void seleccionarCuentaBancaria() {
        idCuentaBancaria = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCuentaBancaria"));
        log.log(Level.INFO, "Se selecciono la cuenta bancaria con id [{0}]", idCuentaBancaria);
    }

    public void seleccionarTipoGiro() {
        tipoGiro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoGiro");
        log.log(Level.INFO, "Se selecciono el tipo giro [{0}]", tipoGiro);
        decidirTotalGiro();
    }

    public Double obtenerValorTransferido(Integer idProforma) {
        if (idProforma != null && idProforma != 0) {
            Double d = transaccionBancariaFacade.obtenerValorTransferido(idProforma);

            if (d != null) {
                return d;
            } else {
                return 0.0;
            }
        }
        return 0.0;
    }

    public Double obtenerValorTransferidoYPendiente(Integer idProforma) {
        if (idProforma != null && idProforma != 0) {
            Double d = transaccionBancariaFacade.obtenerValorPendiente(idProforma);

            if (d != null) {
                return d;
            } else {
                return 0.0;
            }
        }
        return 0.0;
    }

    public void seleccionarProformaTransaccionar() {
        Integer proforma = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProforma"));
        if (proforma != null && proforma != 0) {
            for (ProformaInvoiceDTO p : proformas) {
                if (p.getIdProforma().equals(proforma)) {
                    log.log(Level.INFO, "Se selecciono la proforma [{0}], del proveedor [{1}]; a la cual se le creara una nueva transaccion bancaria",
                            new Object[]{p.getIdProforma(), codProveedor});
                    proformaSeleccionada = p;
                    totalProforma = p.getValorTotal();
                    balance = totalProforma.toString();
                    idTransaccionBancaria = null;
                    editandoTransaccionBancaria = false;
                    obtenerBalance(p);
//                    anticipoCuenta = obtenerAnticipoCuentaActual(p.getIdTipoMoneda());
                    break;
                }
            }

            dlgTransaccion = true;
        }
    }

    private void obtenerBalance(ProformaInvoiceDTO dto) {
        Double balanceTmp = Double.parseDouble(balance);
        if (dto != null && dto.getIdProforma() != null && dto.getIdProforma() != 0) {
            if (dto.getTransacciones() != null && !dto.getTransacciones().isEmpty()) {
                for (TransaccionBancariaDTO t : dto.getTransacciones()) {
                    if (t.getFechaGiro() != null && !t.isCancelado()) {
                        balanceTmp -= t.getTotalGiro();
                    }
                }
            } else {
                List<TransaccionBancaria> trans = transaccionBancariaFacade.transaccionesProforma(dto.getIdProforma());

                for (TransaccionBancaria t : trans) {
                    if (t.getFechaGiro() != null && !t.getCancelado()) {
                        balanceTmp -= t.getTotalGiro();
                    }
                }
            }
        }
        balance = balanceTmp.toString();
    }

    private Double obtenerAnticipoCuentaActual(Integer idTipoMoneda) {
        Double prePayment = anticipoCuentaFacade.obtenerAnticiposCuenta(idTipoMoneda, codProveedor);
        if (prePayment != null) {
            return prePayment;
        } else {
            return 0.0;
        }
    }

    public void decidirTotalGiro() {
        if (tipoGiro != null && tipoGiro.equals("anticipo")) {
            totalGirar = anticipo;
        } else if (tipoGiro != null && tipoGiro.equals("balance")) {
            totalGirar = balance;
        } else if (tipoGiro != null && tipoGiro.equals("roll on")) {
            if (idCuentaBancaria != null && idCuentaBancaria != 0) {
                totalGirar = anticipoCuentaFacade.obtenerSaldoCuentaDisponible(proformaSeleccionada.getIdTipoMoneda() != null ? proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda() : null,
                        idCuentaBancaria, codProveedor).toString();
                if (totalGirar == null || totalGirar.equals("0.0")) {
                    mostrarMensaje("Error", "No se puede seleccionar el tipo de pago Roll on, debido a que no se encontraron anticipos a cuenta, para la moneda y cuenta seleccionada.", true, false, false);
                    log.log(Level.SEVERE, "No se puede seleccionar el tipo de pago Roll on, debido a que no se encontraron anticipos a cuenta, para la moneda y cuenta seleccionada");
                    tipoGiro = null;
                    totalGirar = null;
                    return;
                }
            } else {
                mostrarMensaje("Error", "Para poder seleccionar el tipo de giro ROLL ON, primero seleccione una cuenta bancaria", true, false, false);
                log.log(Level.SEVERE, "Para poder seleccionar el tipo de giro ROLL ON, primero seleccione una cuenta bancaria");
                tipoGiro = null;
                return;
            }
        } else {
            totalGirar = null;
        }
    }

    public void calcularPorcentajeAnticipo() {
        if (anticipo != null && !anticipo.isEmpty()) {
            porcentajeAnticipo = (Double.parseDouble(anticipo) * 100) / totalProforma;
            decidirTotalGiro();
        } else {
            porcentajeAnticipo = null;
        }
    }

    public void guardarTransaccionBancaria() {
        if (proformaSeleccionada.getIdTipoMoneda() != null && proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda() != null && proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda() != 0) {
            if (idTransaccionBancaria != null && idTransaccionBancaria != 0) {
                log.log(Level.INFO, "Se modificara la transaccion bancaria con id [{0}]", idTransaccionBancaria);
                TransaccionBancaria t = transaccionBancariaFacade.find(idTransaccionBancaria);

                if (t != null && t.getIdTransaccionBancaria() != null && t.getIdTransaccionBancaria() != 0) {
                    t.setTotalGiro(Double.parseDouble(totalGirar.replace(",", "")));
                    t.setFechaGiro(fechaGiro);
                    t.setEstado("RP");

                    try {
                        transaccionBancariaFacade.edit(t);
                        log.log(Level.INFO, "Se modifico la transaccion bancaria con id: [{0}], de la proforma: [{1}], del proveedor: [{2}]",
                                new Object[]{t.getIdTransaccionBancaria(), t.getIdProformaInvoice().getIdProforma(), t.getCodProveedor()});
                        actualizarTransacciones(t, false);
                        limpiarDatosTransaccion();
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Error al modificar la transaccion bancaria con id: [{0}], de la proforma: [{1}], del proveedor: [{2}], error: [{3}]",
                                new Object[]{t.getIdTransaccionBancaria(), t.getIdProformaInvoice().getIdProforma(), t.getCodProveedor(), e.getMessage()});
                        mostrarMensaje("Error", "No se pudo modificar la transacción bancaria", true, false, false);
                        return;
                    }
                }
            } else {
                log.log(Level.INFO, "Creando pago para proforma [{0}]", proformaSeleccionada.getIdProforma());
                if (idCuentaBancaria == null || idCuentaBancaria == 0) {
                    log.log(Level.SEVERE, "Se debe seleccionar la cuenta a la que se girara");
                    mostrarMensaje("Error", "Debe seleccionar la cuenta a la que se girara", true, false, false);
                    return;
                }
                if (tipoGiro == null || tipoGiro.isEmpty()) {
                    log.log(Level.SEVERE, "Se debe seleccionar el tipo de giro");
                    mostrarMensaje("Error", "Debe seleccionar el tipo de giro", true, false, false);
                    return;
                }
                if (tipoGiro.equals("anticipo") && (anticipo == null || anticipo.isEmpty())) {
                    log.log(Level.SEVERE, "Para el tipo de giro seleccionado, se debe ingresar el valor del anticipo");
                    mostrarMensaje("Error", "Para el tipo de giro seleccionado, se debe ingresar el valor del anticipo", true, false, false);
                    return;
                } else if (tipoGiro.equals("balance") && (balance == null || balance.isEmpty())) {
                    log.log(Level.SEVERE, "Para el tipo de giro seleccionado, no se encontro balance pendiente");
                    mostrarMensaje("Error", "Para el tipo de giro seleccionado, no se encontro balance pendiente", true, false, false);
                    return;
                } else if (tipoGiro.equals("roll on") && (totalGirar == null || totalGirar.isEmpty())) {
                    log.log(Level.SEVERE, "Para el tipo de giro seleccionado, no se encontro anticipo en Cuenta");
                    mostrarMensaje("Error", "Para el tipo de giro seleccionado, no se encontro anticipo en Cuenta", true, false, false);
                    return;
                }
                if (totalGirar == null || totalGirar.isEmpty()) {
                    log.log(Level.SEVERE, "No se encontro total para girar");
                    mostrarMensaje("Error", "No se encontro total para girar", true, false, false);
                    return;
                }
                if (tipoGiro.equals("anticipo") && Double.parseDouble(totalGirar.replace(",", "")) >= Double.parseDouble(balance.replace(",", ""))) {
                    log.log(Level.SEVERE, "Para este tipo de giro no se puede pagar una mayor cantidad de dinero de la que se debe");
                    mostrarMensaje("Error", "Para este tipo de giro no se puede pagar una mayor cantidad de dinero de la que se debe", true, false, false);
                    return;
                }
                if (descripcionPago == null || descripcionPago.isEmpty()) {
                    log.log(Level.SEVERE, "Ingrese una descripcion para la transaccion");
                    mostrarMensaje("Error", "Ingrese una descripción para la transacción", true, false, false);
                    return;
                }
                if (Double.parseDouble(totalGirar.replace(",", "")) > Double.parseDouble(balance.replace(",", "")) && !tipoGiro.equals("roll on")) {
                    anticipoCuenta = String.valueOf(Double.parseDouble(totalGirar.replace(",", "")) - Double.parseDouble(balance.replace(",", "")));
                    if (!asignarAnticipoCuenta(Double.parseDouble(anticipoCuenta.replace(",", "")), proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda(), idCuentaBancaria)) {
                        mostrarMensaje("Error", "No se puedo hacer el ingreso del dinero solicitado de anticipos a cuenta.", true, false, false);
                        log.log(Level.SEVERE, "No se puedo hacer el ingreso del dinero solicitado de anticipos a cuenta");
                        return;
                    }
                } else if (tipoGiro.equals("roll on")) {
                    if (Double.parseDouble(totalGirar.replace(",", "")) > Double.parseDouble(balance.replace(",", ""))) {
                        mostrarMensaje("Error", "Esta intentando agregar un pago superior a lo que se debe de la proforma", true, false, false);
                        log.log(Level.SEVERE, "Esta intentando agregar un pago superior a lo que se debe de la proforma");
                        return;
                    } else if (Double.parseDouble(totalGirar.replace(",", ""))
                            <= anticipoCuentaFacade.obtenerSaldoCuentaDisponible(proformaSeleccionada.getIdTipoMoneda() != null ? proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda() : null,
                                    idCuentaBancaria, codProveedor)) {
                        fechaGiro = new Date();
                        anticipoCuenta = totalGirar;
                        if (!asignarAnticipoCuenta(-Double.parseDouble(totalGirar.replace(",", "")), proformaSeleccionada.getIdTipoMoneda().getIdTipoMoneda(), idCuentaBancaria)) {
                            mostrarMensaje("Error", "No se puedo hacer la salida del dinero solicitado de anticipos a cuenta.", true, false, false);
                            log.log(Level.SEVERE, "No se puedo hacer la salida del dinero solicitado de anticipos a cuenta");
                            return;
                        }
                    } else {
                        mostrarMensaje("Error", "No se encontro el saldo solicitado de: " + totalGirar + ", en la cuenta bancaria seleccionada", true, false, false);
                        log.log(Level.SEVERE, "No se encontro el saldo solicitado de: [{0}], en la cuenta bancaria seleccionada", totalGirar);
                        return;
                    }
                }

                TransaccionBancaria transaccion = new TransaccionBancaria();

                switch (tipoGiro) {
                    case "anticipo":
                        transaccion.setAnticipo((anticipo != null && !anticipo.equals("0.0")) ? Double.parseDouble(anticipo.replace(",", "")) : null);
                        transaccion.setPorcentajeAnticipo(porcentajeAnticipo != null && porcentajeAnticipo != 0 ? porcentajeAnticipo : null);
                        break;
                    case "balance":
                        transaccion.setBalance((balance != null && !balance.equals("0.0")) ? Double.parseDouble(balance.replace(",", "")) : null);
                        break;
                    case "roll on":
                        transaccion.setAnticipoCuenta((anticipoCuenta != null && anticipoCuenta.isEmpty()) ? Double.parseDouble(anticipoCuenta.replace(",", "")) : null);
                        break;
                    default:
                        break;
                }
                transaccion.setCodProveedor(codProveedor);
                transaccion.setDescripcion(descripcionPago.toUpperCase());
                transaccion.setEstado("TP");
                transaccion.setFecha(new Date());
                transaccion.setFechaGiro(fechaGiro);
                transaccion.setIdCuenta(new CuentaBancariaProveedor(idCuentaBancaria));
                transaccion.setIdProformaInvoice(new ProformaInvoice(proformaSeleccionada.getIdProforma()));
                transaccion.setTipoGiro(tipoGiro.toUpperCase());
                transaccion.setTotalGiro(Double.parseDouble(totalGirar.replace(",", "")));
                transaccion.setTotalInvoice(totalProforma);
                transaccion.setUsuario("ygil");
                transaccion.setCancelado(false);
                transaccion.setFirmado(false);

                try {
                    transaccionBancariaFacade.create(transaccion);
                    log.log(Level.INFO, "Se creo nuevo pago para la proforma [{0}]", proformaSeleccionada.getIdProforma());
                    mostrarMensaje("Éxito", "Se creó la transacción bancaria correctamente.", false, true, false);
                    actualizarTransacciones(transaccion, true);
                    obtenerAnticiposCuenta();
                    limpiarDatosTransaccion();
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo crear la transacción bancaria.", true, false, false);
                    log.log(Level.SEVERE, "Error creando el registro de pago para la proforma [{0}], error [{1}]", new Object[]{proformaSeleccionada.getIdProforma(), e.getMessage()});
                    return;
                }
            }
        } else {
            mostrarMensaje("Error", "No se encontro una moneda asignada a la proforma, debido a esto no se puede realizar el pago por este medio.", true, false, false);
            log.log(Level.SEVERE, "No se encontro una moneda asignada a la proforma, debido a esto no se puede realizar el pago por este medio");
            return;
        }
    }

    private boolean asignarAnticipoCuenta(Double prePayments, Integer idTipoMoneda, Integer idCuentaBancaria) {
        if (prePayments != null && prePayments != 0) {
            AnticipoCuenta prePayment = new AnticipoCuenta();
            prePayment.setCodProveedor(new DatosProveedor(codProveedor));
            prePayment.setComentario(descripcionPago.toUpperCase());
            prePayment.setFecha(new Date());
            prePayment.setUsuario("ygil");
            prePayment.setValor(prePayments);
            prePayment.setIdCuentaBancaria(new CuentaBancariaProveedor(idCuentaBancaria));
            prePayment.setIdTipoMoneda(new TipoMoneda(idTipoMoneda));
            prePayment.setIdProforma(new ProformaInvoice(proformaSeleccionada.getIdProforma()));

            try {
                anticipoCuentaFacade.create(prePayment);
                log.log(Level.INFO, "Se quito / asigno a ANTICIPO CUENTA el saldo actual [{0}]", prePayments);
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                return false;
            }
        }
        return false;
    }

    private void actualizarTransacciones(TransaccionBancaria tran, boolean agregar) {
        for (ProformaInvoiceDTO p : proformas) {
            if (p.getIdProforma().equals(tran.getIdProformaInvoice().getIdProforma()) && agregar) {
                Double valorPendiente = transaccionBancariaFacade.obtenerValorPendiente(p.getIdProforma());
                p.setValorTransferido(transaccionBancariaFacade.obtenerValorTransferido(p.getIdProforma()));
                p.setValorPendiente(p.getValorTotal() - (valorPendiente != null ? valorPendiente : 0));

                p.getTransacciones().add(0, new TransaccionBancariaDTO(tran.getIdTransaccionBancaria(), tran.getCodProveedor(), tran.getIdCuenta().getIdCuentaBancaria(),
                        tran.getTipoGiro(), tran.getIdProformaInvoice().getIdProforma(), tran.getTotalInvoice(), tran.getPorcentajeAnticipo(),
                        tran.getAnticipo(), tran.getAnticipoCuenta(), tran.getBalance(), tran.getTotalGiro(), tran.getDescripcion(), tran.getUsuario(), tran.getEstado(),
                        tran.getFecha(), tran.getFechaGiro()));
                break;
            } else if (p.getIdProforma().equals(tran.getIdProformaInvoice().getIdProforma())) {
                Double valorPendiente = transaccionBancariaFacade.obtenerValorPendiente(p.getIdProforma());
                p.setValorTransferido(transaccionBancariaFacade.obtenerValorTransferido(p.getIdProforma()));
                p.setValorPendiente(p.getValorTotal() - (valorPendiente != null ? valorPendiente : 0));

                for (TransaccionBancariaDTO t : p.getTransacciones()) {
                    if (t.getIdTransaccionBancaria().equals(tran.getIdTransaccionBancaria())) {
                        t.setFechaGiro(tran.getFechaGiro());
                        t.setTotalGiro(tran.getTotalGiro());
                    }
                }
            }
        }
    }

    public void editarTransaccionBancaria() {
        idTransaccionBancaria = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTransaccion"));
        if (idTransaccionBancaria != null && idTransaccionBancaria != 0) {
            if (dlgTransaccion) {
                dlgTransaccion = false;
                editandoTransaccionBancaria = false;
                idTransaccionBancaria = null;
            } else {
                dlgTransaccion = true;
                editandoTransaccionBancaria = true;

                for (ProformaInvoiceDTO p : proformas) {
                    boolean encontrado = false;
                    for (TransaccionBancariaDTO t : p.getTransacciones()) {
                        if (t.getIdTransaccionBancaria().equals(idTransaccionBancaria)) {
                            encontrado = true;
                            fechaGiro = t.getFechaGiro();
                            descripcionPago = t.getDescripcion();
                            totalGirar = t.getTotalGiro().toString();
                            idCuentaBancaria = t.getIdCuenta();
                            porcentajeAnticipo = t.getPorcentajeAnticipo();
                            anticipo = t.getAnticipo() != null ? t.getAnticipo().toString() : null;
                            anticipoCuenta = t.getAnticipoCuenta() != null ? t.getAnticipoCuenta().toString() : null;
                            balance = t.getBalance() != null ? t.getBalance().toString() : null;
                            tipoGiro = t.getTipoGiro();
                            break;
                        } else {
                            encontrado = false;
                        }
                    }
                    if (encontrado) {
                        break;
                    }
                }
            }
        }
    }

    public String obtenerRutaPdf(Integer idTransaccion) {
        if (idTransaccion != null && idTransaccion != 0) {

            return baruApplicationMBean.obtenerValorPropiedad("url.web.transaccionesBancarias") + idTransaccion + ".pdf";
        } else {
            return "";
        }
    }

    public void reprocesarPdfTransaccion() {
        Integer idTransaccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTransaccion"));

        if (idTransaccion != null && idTransaccion != 0) {
            TransaccionBancaria t = transaccionBancariaFacade.find(idTransaccion);

            if (t != null && t.getIdTransaccionBancaria() != null && t.getIdTransaccionBancaria() != 0) {
                t.setEstado("RT");

                try {
                    transaccionBancariaFacade.edit(t);
                    log.log(Level.INFO, "Se mando a reprocesar la transaccion bancaria con id: [{0}]", idTransaccion);

                    boolean procesado = false;
                    int intentos = 0;
                    while (intentos < 60) {
                        try {
                            if (intentos % 5 == 0) {
                                log.log(Level.INFO, "Intento de consulta de documento transaccion bancaria guardada #[{0}]", intentos);
                            }
                            TransaccionBancaria validacion = transaccionBancariaFacade.find(idTransaccion);
                            if (validacion.getEstado() != null && validacion.getEstado().equals("TF")) {
                                log.log(Level.INFO, "El documento se almaceno correctamente en el servidor compartido");
                                procesado = true;
                                break;
                            }
                            Thread.sleep(498);
                        } catch (Exception e) {
                            log.log(Level.SEVERE, e.getMessage());
                            mostrarMensaje("Error", "No se pudo reprocesar el documento de la transacción", true, false, false);
                            return;
                        }
                        intentos++;
                    }

                    if (procesado) {
                        for (ProformaInvoiceDTO p : proformas) {
                            boolean encontrado = false;
                            for (TransaccionBancariaDTO dto : p.getTransacciones()) {
                                if (dto.getIdTransaccionBancaria().equals(idTransaccion)) {
                                    validarPdfExistencia(idTransaccion);
                                    encontrado = true;
                                    break;
                                } else {
                                    encontrado = false;
                                }
                            }
                            if (encontrado) {
                                break;
                            }
                        }
                    } else {
                        log.log(Level.SEVERE, "No se pudo reprocesar el documento de la transaccion");
                        mostrarMensaje("Error", "No se pudo reprocesar el documento de la transacción", true, false, false);
                        return;
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al reprocesar la transaferencia bancaria. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo reprocesar el documento de la transacción", true, false, false);
                    return;
                }
            }
        }
    }

    public void limpiarDatosTransaccion() {
        fechaGiro = null;
        tipoGiro = null;
        descripcionPago = null;
        totalGirar = null;
        idCuentaBancaria = null;
        porcentajeAnticipo = null;
        anticipo = null;
        anticipoCuenta = null;
        balance = null;
        dlgTransaccion = false;
        transaccionVisible = new TransaccionBancariaDTO();
    }

    /**
     * *************FINALIZA CÓDIGO FICHA ADMINISTRAR - TRANSACCIONES BANCARIAS
     * Y EMPIEZA CÓDIGO FICHA ANTICIPO CUENTA*************
     */
    private void obtenerAnticiposCuenta() {
        if (codProveedor != null && !codProveedor.isEmpty()) {
            anticiposCuenta.clear();
            List<AnticipoCuenta> ac = anticipoCuentaFacade.obtenerAnticiposCuenta(codProveedor);

            if (ac != null && !ac.isEmpty()) {
                for (AnticipoCuenta a : ac) {
                    anticiposCuenta.add(new AnticipoCuentaDTO(a.getIdAnticipoCuenta(), a.getValor(), a.getComentario(), a.getUsuario(),
                            a.getCodProveedor().getCodProveedor(), a.getFecha(), a.getFechaGiro(),
                            new TipoMonedaDTO(a.getIdTipoMoneda().getIdTipoMoneda(), a.getIdTipoMoneda().getSimbolo(), a.getIdTipoMoneda().getMoneda()),
                            new CuentaBancariaProveedorDTO(a.getIdCuentaBancaria().getIdCuentaBancaria(), a.getIdCuentaBancaria().getCuentaBancaria(),
                                    a.getIdCuentaBancaria().getIban(), a.getIdCuentaBancaria().getAba(), a.getIdCuentaBancaria().getCodProveedor(),
                                    new SucursalBancoComprasDTO(a.getIdCuentaBancaria().getIdSucursalBanco().getIdSucursalBanco(),
                                            a.getIdCuentaBancaria().getIdSucursalBanco().getDireccion(), a.getIdCuentaBancaria().getIdSucursalBanco().getSwift(),
                                            new BancoComprasDTO(a.getIdCuentaBancaria().getIdSucursalBanco().getIdBanco().getIdBanco(),
                                                    a.getIdCuentaBancaria().getIdSucursalBanco().getIdBanco().getRazonSocial()),
                                            a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad() != null
                                                    ? new CiudadDTO(a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdCiudad(),
                                                            a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getNombre(),
                                                            new EstadosDTO(a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdEstado().getIdEstado(),
                                                                    a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdEstado().getNombre(),
                                                                    new PaisesDTO(a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getIdPais(),
                                                                            a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getNombre(),
                                                                            a.getIdCuentaBancaria().getIdSucursalBanco().getIdCiudad().getIdEstado().getIdPais().getContinente()))) : null)),
                            a.getIdProforma() != null ? new ProformaInvoiceDTO(a.getIdProforma().getIdProforma(), a.getIdProforma().getCodProveedor(),
                                            a.getIdProforma().getConsecutivo(), a.getIdProforma().getAnio()) : null));
                }

                Collections.sort(anticiposCuenta, new Comparator<AnticipoCuentaDTO>() {
                    @Override
                    public int compare(AnticipoCuentaDTO t, AnticipoCuentaDTO t1) {
                        return t1.getFecha().compareTo(t.getFecha());
                    }
                });
            }
            anticipoCuentaDisponible = anticipoCuentaFacade.obtenerTotalAnticiposCuentas(codProveedor);
        }
    }

    public String obtenerProformaMostrar(AnticipoCuentaDTO anticipoCuenta) {
        if (anticipoCuenta != null && anticipoCuenta.getIdAnticipoCuenta() != null && anticipoCuenta.getIdAnticipoCuenta() != 0) {
            if (anticipoCuenta.getIdProforma() != null) {
                return anticipoCuenta.getIdProforma().getCodProveedor() + " - " + anticipoCuenta.getIdProforma().getAnio() + " - " + anticipoCuenta.getIdProforma().getConsecutivo();
            } else {
                return "";
            }
        }
        return "";
    }

    //INICIA CÓDIGO MODAL ANTICIPO CUENTA
    public void decidirTipoAnticipoCuenta() {
        tipoAnticipoCuenta = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoAnticipoCuenta");
        log.log(Level.INFO, "Se realizara la accion de {0} un anticipo a cuenta", tipoAnticipoCuenta);
    }

    public void seleccionarTipoMoneda() {
        idTipoMoneda = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoMoneda"));
        log.log(Level.INFO, "Se selecciono el tipo de moneda con id [{0}]", idTipoMoneda);
    }

    public void seleccionarProformaAnticipo() {
        idProforma = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProforma"));
        log.log(Level.INFO, "Se selecciono la proforma con id [{0}]", idProforma);
    }

    public void crearAnticipoCuenta() {
        if (codProveedor != null && !codProveedor.isEmpty()) {
            if (anticipoCuenta == null || anticipoCuenta.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar un valor en anticipo cuenta, para poder continuar.", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar un valor en anticipo cuenta, para poder continuar");
                return;
            }
            if (tipoAnticipoCuenta.equals("Nuevo") && (idTipoMoneda == null || idTipoMoneda == 0)) {
                mostrarMensaje("Error", "Seleccione un tipo de moneda para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Seleccione un tipo de moneda para poder continuar");
                return;
            }
            if (tipoAnticipoCuenta.equals("Usar") && (idProforma == null || idProforma == 0)) {
                mostrarMensaje("Error", "Seleccione una proforma para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Seleccione una proforma para poder continuar");
                return;
            }
            if (idCuentaBancaria == null || idCuentaBancaria == 0) {
                mostrarMensaje("Error", "Seleccione una cuenta bancaria para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Seleccione una cuenta bancaria para poder continuar");
                return;
            }
            if (comentarioAnticipoCuenta == null || comentarioAnticipoCuenta.isEmpty()) {
                mostrarMensaje("Error", "Se debe ingresar un comentario para guardar el anticipo cuenta", true, false, false);
                log.log(Level.SEVERE, "Se debe ingresar un comentario para guardar el anticipo cuenta");
                return;
            }

            AnticipoCuenta a = new AnticipoCuenta();

            if (tipoAnticipoCuenta.equals("Usar")) {
                ProformaInvoiceDTO pi = new ProformaInvoiceDTO();

                for (ProformaInvoiceDTO p : proformas) {
                    if (p.getIdProforma().equals(idProforma)) {
                        pi = p;
                        idTipoMoneda = p.getIdTipoMoneda().getIdTipoMoneda();
                    }
                }

                if (pi.getValorPendiente() == null || pi.getValorPendiente() == 0) {
                    mostrarMensaje("Error", "No se puede usar este medio de pago para la proforma seleccionada, debido a que esta no tiene saldo pendiente por pagar.", true, false, false);
                    log.log(Level.SEVERE, "No se puede usar este medio de pago para la proforma seleccionada, debido a que esta no tiene saldo pendiente por pagar");
                    return;
                } else if (pi.getValorPendiente() < Double.parseDouble(anticipoCuenta.replace(",", ""))) {
                    mostrarMensaje("Error", "No se puede pagar por este medio mas de lo que se debe de la proforma seleccionada.", true, false, false);
                    log.log(Level.SEVERE, "No se puede pagar por este medio mas de lo que se debe de la proforma seleccionada");
                    return;
                } else if (anticipoCuentaFacade.obtenerSaldoCuentaDisponible(idTipoMoneda, idCuentaBancaria, codProveedor) <= 0) {
                    mostrarMensaje("Error", "No se encontro saldo en cuenta para la moneda y cuenta seleccionada.", true, false, false);
                    log.log(Level.SEVERE, "No se encontro saldo en cuenta para la moneda y cuenta seleccionada");
                    return;
                } else if (anticipoCuentaFacade.obtenerSaldoCuentaDisponible(idTipoMoneda, idCuentaBancaria, codProveedor) < Double.parseDouble(anticipoCuenta.replace(",", ""))) {
                    mostrarMensaje("Error", "La cantidad solicitada para el uso del anticipo en cuenta, no se encontro.", true, false, false);
                    log.log(Level.SEVERE, "La cantidad solicitada para el uso del anticipo en cuenta, no se encontro");
                    return;
                }

                a.setIdProforma(new ProformaInvoice(idProforma));
                a.setFechaGiro(new Date());
            }

            a.setCodProveedor(new DatosProveedor(codProveedor));
            a.setComentario(comentarioAnticipoCuenta.toUpperCase());
            a.setFecha(new Date());
            a.setUsuario("ygil");
            a.setValor(tipoAnticipoCuenta.equals("Nuevo") ? Double.parseDouble(anticipoCuenta.replace(",", "")) : (Double.parseDouble(anticipoCuenta.replace(",", "")) * -1));
            a.setIdTipoMoneda(new TipoMoneda(idTipoMoneda));
            a.setIdCuentaBancaria(new CuentaBancariaProveedor(idCuentaBancaria));

            try {
                if (tipoAnticipoCuenta.equals("Usar")) {
                    TransaccionBancaria t = crearTransaccionBancariaXAnticipoCuenta();
                    if (t != null && t.getIdTransaccionBancaria() != null && t.getIdTransaccionBancaria() != 0) {
                        anticipoCuentaFacade.create(a);
                        actualizarTransacciones(t, true);
                        log.log(Level.INFO, "Se uso el anticipo en cuenta, por un valor de [{0}]", anticipoCuenta);
                        mostrarMensaje("Éxito", "Se usó el anticipo a cuenta adecuadamente", false, true, false);
                    } else {
                        mostrarMensaje("Error", "No se puedo usar el anticipo en cuenta.", true, false, false);
                        log.log(Level.SEVERE, "No se puedo usar el anticipo en cuenta");
                        return;
                    }
                } else {
                    anticipoCuentaFacade.create(a);
                    log.log(Level.INFO, "Se creo el anticipo en cuenta, por un valor de [{0}]", anticipoCuenta);
                    mostrarMensaje("Éxito", "Se creó el anticipo a cuenta adecuadamente", false, true, false);
                }
                obtenerAnticiposCuenta();
                limpiarModalAnticipoCuenta();
            } catch (Exception e) {
                mostrarMensaje("Error", "No se pudo guardar el cambio en anticipos cuenta.", true, false, false);
                log.log(Level.SEVERE, "Error al guardar el anticipo. Error [{0}]", e.getMessage());
                return;
            }
        }
    }

    private TransaccionBancaria crearTransaccionBancariaXAnticipoCuenta() {
        ProformaInvoice p = proformaInvoiceFacade.find(idProforma);

        if (p != null && p.getIdProforma() != null && p.getIdProforma() != 0) {
            TransaccionBancaria t = new TransaccionBancaria();

            t.setAnticipo(null);
            t.setAnticipoCuenta(Double.parseDouble(anticipoCuenta.replace(",", "")));
            t.setBalance(null);
            t.setCancelado(false);
            t.setCodProveedor(codProveedor);
            t.setDescripcion("Se uso anticipo de cuenta".toUpperCase());
            t.setEstado("TP");
            t.setFecha(new Date());
            t.setFechaGiro(new Date());
            t.setFirmado(false);
            t.setIdCuenta(new CuentaBancariaProveedor(idCuentaBancaria));
            t.setIdProformaInvoice(new ProformaInvoice(idProforma));
            t.setPorcentajeAnticipo(null);
            t.setTipoGiro("ROLL ON");
            t.setTotalGiro(Double.parseDouble(anticipoCuenta.replace(",", "")));
            t.setTotalInvoice(p.getValorTotal());
            t.setUsuario("ygil");

            try {
                transaccionBancariaFacade.create(t);
                log.log(Level.INFO, "Se creo transaccion bancaria por anticipo a cuenta, para la proforma con id [{0}]", idProforma);
                return t;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la transaccion bancaria por anticipo a cuenta, para la proforma con id [{0}]. Error [{1}]", new Object[]{idProforma, e.getMessage()});
                return null;
            }
        }
        return null;
    }

    public void seleccionarAnticipoCuenta() {
        idAnticipoCuenta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAnticipoCuenta"));
        log.log(Level.INFO, "Se selecciono el anticipo a cuenta con id [{0}] para colocar la fecha giro", idAnticipoCuenta);

        AnticipoCuenta ant = anticipoCuentaFacade.find(idAnticipoCuenta);

        if (ant != null && ant.getIdAnticipoCuenta() != null && ant.getIdAnticipoCuenta() != 0) {
            anticipoCuenta = ant.getValor().toString();
            idTipoMoneda = ant.getIdTipoMoneda().getIdTipoMoneda();
            idCuentaBancaria = ant.getIdCuentaBancaria().getIdCuentaBancaria();
            comentarioAnticipoCuenta = ant.getComentario();
            idProforma = ant.getIdProforma() != null ? ant.getIdProforma().getIdProforma() : null;
            if (ant.getValor() > 0) {
                tipoAnticipoCuenta = "Nuevo";
            } else if (ant.getValor() < 0) {
                tipoAnticipoCuenta = "Usar";
            }
            editandoAnticipoCuenta = true;
        }
    }

    public void actualizarAnticipoCuenta() {
        if (idAnticipoCuenta != null && idAnticipoCuenta != 0) {
            AnticipoCuenta ant = anticipoCuentaFacade.find(idAnticipoCuenta);

            if (ant != null && ant.getIdAnticipoCuenta() != null && ant.getIdAnticipoCuenta() != 0) {
                ant.setFechaGiro(fechaGiro);

                try {
                    anticipoCuentaFacade.edit(ant);
                    log.log(Level.INFO, "Se actualizo la fecha de giro para el anticipo a cuenta con id [{0}]", idAnticipoCuenta);
                    obtenerAnticiposCuenta();
                    limpiarModalAnticipoCuenta();
                } catch (Exception e) {
                    mostrarMensaje("Error", "No se pudo actualizar la fecha de giro para el anticipo a cuenta seleccionado.", true, false, false);
                    log.log(Level.SEVERE, "Error al actualizar la fecha de giro del anticipo a cuenta con id [{0}]. Error [{1}]", new Object[]{idAnticipoCuenta, e.getMessage()});
                    return;
                }
            }
        }
    }

    public void limpiarModalAnticipoCuenta() {
        anticipoCuenta = null;
        idTipoMoneda = null;
        idCuentaBancaria = null;
        comentarioAnticipoCuenta = null;
        idProforma = null;
        editandoAnticipoCuenta = false;
        fechaGiro = null;
        idAnticipoCuenta = null;
    }

    /**
     * *************FINALIZA CÓDIGO FICHA ANTICIPO CUENTA Y INICIA CÓDIGO FICHA
     * CREAR CUENTA*************
     */
    public void obtenerBancosCompras() {
        if (codProveedor != null && !codProveedor.isEmpty()) {
            bancos.clear();
            List<BancoCompras> banks = bancoComprasFacade.findAll();

            if (banks != null && !banks.isEmpty()) {
                for (BancoCompras bank : banks) {
                    bancos.add(new BancoComprasDTO(bank.getIdBanco(), bank.getRazonSocial()));
                }
            }

            Collections.sort(bancos, new Comparator<BancoComprasDTO>() {
                @Override
                public int compare(BancoComprasDTO t, BancoComprasDTO t1) {
                    return t.getRazonSocial().compareTo(t1.getRazonSocial());
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
            log.log(Level.INFO, "Se selecciono crear banco para el proveedor [{0}]", codProveedor);
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
                    log.log(Level.INFO, "Se modifico el banco con id [{0}], para el proveedor [{1}]", new Object[]{idBancoCuenta, codProveedor});
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
                log.log(Level.INFO, "Se creo banco con id [{0}], para el proveedor [{1}]", new Object[]{b.getIdBanco(), codProveedor});
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
        paisSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPais"));
        log.log(Level.INFO, "Se selecciono el pais con id [{0}]", paisSucursal);

        if (paisSucursal != null && paisSucursal != 0) {
            estadoSucursal = null;
            log.log(Level.INFO, "Se estan obteniendo los estados del pais {0}", paisSucursal);
            obtenerEstados();
        }
    }

    private void obtenerEstados() {
        estados = new ArrayList<>();

        List<Estados> states = estadosFacade.estadosXPais(paisSucursal);
        if (states != null && !states.isEmpty()) {
            for (Estados e : states) {
                estados.add(new EstadosDTO(e.getIdEstado(), e.getNombre(), new PaisesDTO(e.getIdPais().getIdPais())));
            }
            log.log(Level.INFO, "Se encontraron {0} estados para el pais con id {1}", new Object[]{estados.size(), paisSucursal});

            Collections.sort(estados, new Comparator<EstadosDTO>() {
                @Override
                public int compare(EstadosDTO t, EstadosDTO t1) {
                    return t.getNombre().compareTo(t1.getNombre());
                }
            });
        }
    }

    public void seleccionarEstado() {
        estadoSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEstado"));
        log.log(Level.INFO, "Se selecciono el estado con id [{0}]", estadoSucursal);

        if (estadoSucursal != null && estadoSucursal != 0) {
            ciudadSucursal = null;
            log.log(Level.INFO, "Se estan obteniendo las ciudades del estado {0}", estadoSucursal);
            obtenerCiudades();
        }
    }

    private void obtenerCiudades() {
        ciudadesSucursal.clear();

        List<Ciudad> cities = ciudadFacade.obtenerCiudadesEstado(estadoSucursal);
        if (cities != null && !cities.isEmpty()) {
            for (Ciudad c : cities) {
                ciudadesSucursal.add(new CiudadDTO(c.getIdCiudad(), c.getNombre(), new EstadosDTO(c.getIdEstado().getIdEstado(), c.getIdEstado().getNombre(),
                        new PaisesDTO(c.getIdEstado().getIdPais().getIdPais(), c.getIdEstado().getIdPais().getNombre(), c.getIdEstado().getIdPais().getContinente()))));
            }
            log.log(Level.INFO, "Se encontraron {0} ciudades para el estado con id {1}", new Object[]{ciudadesSucursal.size(), estadoSucursal});

            Collections.sort(ciudadesSucursal, new Comparator<CiudadDTO>() {
                @Override
                public int compare(CiudadDTO t, CiudadDTO t1) {
                    return t.getNombre().compareTo(t1.getNombre());
                }
            });
        }
    }

    public void seleccionarCiudad() {
        ciudadSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCiudad"));
        log.log(Level.INFO, "Se selecciono la ciudad con id [{0}]", ciudadSucursal);
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

    private void obtenerSucursalesBanco() {
        if (idBancoCuenta != null && idBancoCuenta != 0) {
            sucursales.clear();
            List<SucursalBancoCompras> branchs = sucursalBancoComprasFacade.obtenerBancosProveedor(idBancoCuenta);

            if (branchs != null && !branchs.isEmpty()) {
                for (SucursalBancoCompras s : branchs) {
                    sucursales.add(new SucursalBancoComprasDTO(s.getIdSucursalBanco(), s.getDireccion(), s.getSwift(), s.getIdBanco() != null
                            ? new BancoComprasDTO(s.getIdBanco().getIdBanco(), s.getIdBanco().getRazonSocial()) : null, s.getIdCiudad() != null
                                    ? new CiudadDTO(s.getIdCiudad().getIdCiudad(), s.getIdCiudad().getNombre(),
                                            new EstadosDTO(s.getIdCiudad().getIdEstado().getIdEstado(), s.getIdCiudad().getIdEstado().getNombre(),
                                                    new PaisesDTO(s.getIdCiudad().getIdEstado().getIdPais().getIdPais(),
                                                            s.getIdCiudad().getIdEstado().getIdPais().getNombre(), s.getIdCiudad().getIdEstado().getIdPais().getContinente()))) : null));
                }
            }

            Collections.sort(sucursales, new Comparator<SucursalBancoComprasDTO>() {
                @Override
                public int compare(SucursalBancoComprasDTO t, SucursalBancoComprasDTO t1) {
                    return t.getDireccion().compareTo(t1.getDireccion());
                }
            });
        }
    }

    public void seleccionarSucursalBanco() {
        idSucursal = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSucursalBanco"));

        limpiarPasoCuentaBancaria();
        if (idSucursal != null && idSucursal > 0) {
            log.log(Level.INFO, "Se selecciono la sucursal con id [{0}], del banco con id [{1}]", new Object[]{idSucursal, idBancoCuenta});

            for (SucursalBancoComprasDTO s : sucursales) {
                if (s.getIdSucursalBanco().equals(idSucursal)) {
                    direccionSucursal = s.getDireccion();
                    swiftSucursal = s.getSwift();
                    if (s.getIdCiudad() != null) {
                        paisSucursal = s.getIdCiudad().getIdEstado().getIdPais().getIdPais();
                        obtenerEstados();
                        estadoSucursal = s.getIdCiudad().getIdEstado().getIdEstado();
                        obtenerCiudades();
                        ciudadSucursal = s.getIdCiudad().getIdCiudad();
                    }
                    break;
                }
            }
        } else if (idSucursal != null && idSucursal == -1) {
            log.log(Level.INFO, "Se selecciono crear sucursal para el banco con id [{1}]", idBancoCuenta);
            limpiarPasoSucursalCuenta();
        }
    }

    public boolean guardarSucursalBanco() {
//        if (idSucursal == null || idSucursal == 0) {
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
        if (paisSucursal == null || paisSucursal == 0) {
            mostrarMensaje("Error", "Ingrese un país para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un pais para la sucursal, y de clic en Guardar");
            return false;
        }
        if (estadoSucursal == null || estadoSucursal == 0) {
            mostrarMensaje("Error", "Ingrese un estado para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un estado para la sucursal, y de clic en Guardar");
            return false;
        }
        if (ciudadSucursal == null || ciudadSucursal == 0) {
            mostrarMensaje("Error", "Ingrese un ciudad para la sucursal, y de clic en Guardar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un ciudad para la sucursal, y de clic en Guardar");
            return false;
        }

        SucursalBancoCompras s = null;

        if (idSucursal != null && idSucursal > 0) {
            s = sucursalBancoComprasFacade.find(idSucursal);

            if (s != null && s.getIdSucursalBanco() != null && s.getIdSucursalBanco() != 0) {
                s.setDireccion(direccionSucursal.toUpperCase());
                s.setIdBanco(new BancoCompras(idBancoCuenta));
                s.setIdCiudad(new Ciudad(ciudadSucursal));
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
            s.setIdCiudad(new Ciudad(ciudadSucursal));
            s.setSwift(swiftSucursal.toUpperCase());

            try {
                sucursalBancoComprasFacade.create(s);
                log.log(Level.INFO, "Se creo sucursal con id [{0}], para el banco con id [{1}]", new Object[]{s.getIdSucursalBanco(), idBancoCuenta});
                idSucursal = s.getIdSucursalBanco();
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
        cuentasSucursal.clear();

        List<CuentaBancariaProveedor> accounts = cuentaBancariaProveedorFacade.obtenerCuentasSucursal(idSucursal);
        if (accounts != null && !accounts.isEmpty()) {
            for (CuentaBancariaProveedor c : accounts) {
                cuentasSucursal.add(new CuentaBancariaProveedorDTO(c.getIdCuentaBancaria(), c.getCuentaBancaria(), c.getIban(), c.getAba(),
                        c.getCodProveedor(), new SucursalBancoComprasDTO(c.getIdSucursalBanco().getIdSucursalBanco())));
            }

            Collections.sort(cuentasSucursal, new Comparator<CuentaBancariaProveedorDTO>() {
                @Override
                public int compare(CuentaBancariaProveedorDTO t, CuentaBancariaProveedorDTO t1) {
                    return t.getCuentaBancaria().compareTo(t1.getCuentaBancaria());
                }
            });
        }
    }

    public void seleccionarCuentaSucursal() {
        idCuenta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCuentaSucursal"));
        log.log(Level.INFO, "Se selecciono la cuenta [{0}], de la sucursal bancaria con id [{1}]", new Object[]{idCuenta, idSucursal});

        if (idCuenta != null && idCuenta > 0) {
            for (CuentaBancariaProveedorDTO c : cuentas) {
                if (c.getIdCuentaBancaria().equals(idCuenta)) {
                    cuentaBancaria = c.getCuentaBancaria();
                    ibanCuenta = c.getIban();
                    abaCuenta = c.getAba();
                    break;
                }
            }
        } else if (idCuenta != null && idCuenta == -1) {
            log.log(Level.INFO, "Se selecciono crear cuenta para la sucursal con id [{1}]", idCuenta);
            limpiarPasoCuentaBancaria();
        }
    }

    public void guardarCuentaBancaria() {
//        if (idCuenta == null || idCuenta == 0) {
//            mostrarMensaje("Error", "No ha seleccionado una cuenta para crear", true, false, false);
//            log.log(Level.SEVERE, "No ha seleccionado una cuenta para crear");
//            return;
//        }
        if (cuentaBancaria == null || cuentaBancaria.isEmpty()) {
            mostrarMensaje("Error", "Ingrese una cuenta bancaria.", true, false, false);
            log.log(Level.SEVERE, "Ingrese una cuenta bancaria");
            return;
        }

        CuentaBancariaProveedor c = null;

        if (idCuenta != null && idCuenta > 0) {
            c = cuentaBancariaProveedorFacade.find(idCuenta);

            if (c != null && c.getIdCuentaBancaria() != null && c.getIdCuentaBancaria() != 0) {
                c.setAba(abaCuenta == null ? null : abaCuenta.toUpperCase());
                c.setCodProveedor(codProveedor);
                c.setCuentaBancaria(cuentaBancaria.toUpperCase());
                c.setIban(ibanCuenta == null ? null : ibanCuenta.toUpperCase());
                c.setIdSucursalBanco(new SucursalBancoCompras(idSucursal));

                try {
                    cuentaBancariaProveedorFacade.edit(c);
                    log.log(Level.INFO, "Se modifico la cuenta con id [{0}], para la sucursal con id [{1}]", new Object[]{c.getIdCuentaBancaria(), idSucursal});
                    obtenerCuentasSucursal();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al modificar la cuenta. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo modificar la cuenta.", true, false, false);
                    return;
                }
            }
        } else {
            c = new CuentaBancariaProveedor();

            c.setAba(abaCuenta == null ? null : abaCuenta.toUpperCase());
            c.setCodProveedor(codProveedor);
            c.setCuentaBancaria(cuentaBancaria.toUpperCase());
            c.setIban(ibanCuenta == null ? null : ibanCuenta.toUpperCase());
            c.setIdSucursalBanco(new SucursalBancoCompras(idSucursal));

            try {
                cuentaBancariaProveedorFacade.create(c);
                log.log(Level.INFO, "Se creo la cuenta con id [{0}], para la sucursal con id [{1}]", new Object[]{c.getIdCuentaBancaria(), idSucursal});
                idCuenta = c.getIdCuentaBancaria();
                obtenerCuentasSucursal();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la cuenta. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo crear la cuenta bancaria.", true, false, false);
                return;
            }
        }
    }

    public void cerrarCreacionCuentaBancaria() {
        pasoCrearCuenta = 1;
        idBancoCuenta = null;
        razonSocialBanco = null;
        idSucursal = null;
        sucursales = new ArrayList<>();
        limpiarPasoCuentaBancaria();
        limpiarPasoSucursalCuenta();
        obtenerCuentasProveedor();
    }

    private void limpiarPasoSucursalCuenta() {
        idCuentaBancaria = null;
        paisSucursal = null;
        estadoSucursal = null;
        ciudadSucursal = null;
        swiftSucursal = null;
        direccionSucursal = null;
        estadosSucursal = new ArrayList<>();
        ciudadesSucursal = new ArrayList<>();
        cuentasSucursal = new ArrayList<>();
        limpiarPasoCuentaBancaria();
    }

    private void limpiarPasoCuentaBancaria() {
        cuentaBancaria = null;
        ibanCuenta = null;
        abaCuenta = null;
    }

    /**
     * *************INICIA CÓDIGO FICHA EXPORTAR DATOS*************
     */
    public void seleccionarTipoExportacion() {
        tipoExportacion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoExportacion");

        idProforma = null;
        tipoDato = null;
        filtro = null;
        fechaFiltro = null;
        mostrarBtnExportar = false;
        if (tipoExportacion.equals("proveedor")) {
            if (codProveedor == null || codProveedor.isEmpty()) {
                tipoExportacion = null;
                mostrarBtnExportar = false;
                log.log(Level.SEVERE, "Esta opcion no se puede seleccionar, debido a que no se encontro un proveedor seleccionado");
                mostrarMensaje("Error", "Esta opción no se puede seleccionar, debido a que no se encontró un proveedor seleccionado", true, false, false);
                return;
            }
        } else {
            mostrarBtnExportar = true;
        }
        log.log(Level.INFO, "Se selecciono tipo de exportacion, con valor: [{0}]", tipoExportacion);
    }

    public void seleccionarTipoDatos() {
        tipoDato = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoDato");

        idProforma = null;
        filtro = null;
        fechaFiltro = null;
        mostrarBtnExportar = false;
        if (tipoDato.equals("proforma")) {
            mostrarBtnExportar = false;
        } else {
            mostrarBtnExportar = true;
        }
        log.log(Level.INFO, "Se selecciono tipo de dato, con valor: [{0}]", tipoDato);
    }

    public void seleccionarIdProforma() {
        idProforma = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProforma"));
        log.log(Level.INFO, "Se selecciono la proforma con id [{0}], para exportacion de informe", idProforma);
        filtro = null;
        fechaFiltro = null;
        if (idProforma != null && idProforma != 0) {
            mostrarBtnExportar = true;
        } else {
            mostrarBtnExportar = false;
        }
    }

    public void seleccionarFiltro() {
        filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
        log.log(Level.INFO, "Se selecciono el filtro [{0}], para la descarga del informe", filtro);
        fechaFiltro = null;
    }

    public StreamedContent getExportarExcel() {
        if (tipoExportacion == null || tipoExportacion.isEmpty()) {
            mostrarMensaje("Error", "Seleccione un tipo de exportación para poder continuar", true, false, false);
            log.log(Level.SEVERE, "Seleccione un tipo de exportacion para poder continuar");
            return null;
        } else if (tipoExportacion.equals("proveedor")) {
            if (tipoDato == null || tipoDato.isEmpty()) {
                mostrarMensaje("Error", "Seleccione un tipo de datos para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Seleccione un tipo de datos para poder continuar");
                return null;
            } else if (tipoDato.equals("proforma")) {
                if (dato == null || dato.isEmpty()) {
                    mostrarMensaje("Error", "Seleccione una proforma para poder continuar", true, false, false);
                    log.log(Level.SEVERE, "Seleccione una proforma para poder continuar");
                    return null;
                }
            }
        }
        if (filtro != null && !filtro.isEmpty() && !filtro.equals("vencidas")) {
            if ((valorFiltro == null || valorFiltro.isEmpty()) && fechaFiltro == null) {
                mostrarMensaje("Error", "Ingrese un valor para el filtro seleccionado para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Ingrese un valor para el filtro seleccionado para poder continuar");
                return null;
            } else if (filtro.equals("fecha")) {
                if (fechaFiltro != null) {
                    valorFiltro = new SimpleDateFormat("yyyy-MM-dd").format(fechaFiltro);
                }
            }
        }

        log.log(Level.INFO, "Se inicia proceso de exportacion de informe");
        String nombreInforme = "RELACIÓN PEDIDOS PARA PONER EN PRODUCCIÓN - #" + System.currentTimeMillis();
        ExcelGeneratorInformeProformas excel = new ExcelGeneratorInformeProformas(nombreInforme,
                obtenerEncabezados(),
                obtenerDatos(tipoExportacion, codProveedor, tipoDato, dato, filtro, valorFiltro));
        try {
            excel.generarArchivoExcel();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
        try {
            String file = nombreInforme + ".xlsx";
            InputStream stream = new ByteArrayInputStream(fileToBytes(new File(System.getProperty("jboss.server.temp.dir") + File.separator + file), file));
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", file);
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            log.log(Level.SEVERE, "No se ha podido generar el informe");
            return null;
        }
    }

    private List<String> obtenerEncabezados() {
        List<String> headLine = new ArrayList<>();
        headLine.add(new String("NOMBRE PROVEEDOR"));
        headLine.add(new String("No. CONTENEDOR"));
        headLine.add(new String("VALOR ORDEN TOTAL"));
        headLine.add(new String("% ANTICIPO"));
        headLine.add(new String("FECHA GIRO"));
        headLine.add(new String("ANTICIPO"));
        headLine.add(new String("TIEMPO PRODUCCIÓN"));
        headLine.add(new String("FECHA EMBARQUE"));
        headLine.add(new String("TIEMPO TRANSITO"));
        headLine.add(new String("BALANCE"));
        headLine.add(new String("FECHA VENCIMIENTO"));
        headLine.add(new String("FECHA MÁXIMA PAGO"));
        headLine.add(new String("DÍAS VENCIDOS"));
        headLine.add(new String("MONEDA"));

        return headLine;
    }

    private List<List<Object>> obtenerDatos(String tipoExportacion, String codProveedor, String tipoDato, String proforma, String filtro, String parametro) {
        List<List<Object>> resultado = new ArrayList<>();
        List<Object[]> datos = contenedorProformaFacade.obtenerDatosExportar(tipoExportacion, codProveedor, tipoDato, proforma, filtro, parametro);

        for (Object[] o : datos) {
            List<Object> fila = new ArrayList<>();
            fila.addAll(Arrays.asList(o));
            resultado.add(fila);
        }
        return resultado;
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
            log.log(Level.INFO, ex.getMessage());
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
     * *************FINALIZA CÓDIGO FICHA EXPORTAR DATOS*************
     */
    /**
     * *************EMPIEZA CÓDIGO GENERAL E INICIAL*************
     */
    private void obtenerProveedores() {
        proveedores = datosProveedorFacade.findAll();
    }

    private void obtenerPaises() {
        List<Paises> tmpPaises = paisesFacade.findAll();
        for (Paises p : tmpPaises) {
            PaisesDTO dto = new PaisesDTO();
            dto.setContinente(p.getContinente());
            dto.setIdPais(p.getIdPais());
            dto.setNombre(p.getNombre());
            paises.add(dto);
        }
        Collections.sort(paises, new Comparator<PaisesDTO>() {
            @Override
            public int compare(PaisesDTO o1, PaisesDTO o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
    }

    private void obtenerPrioridades() {
        prioridades.clear();

        List<Prioridad> priorities = prioridadFacade.findAll();
        for (Prioridad priority : priorities) {
            prioridades.add(new PrioridadDTO(priority.getIdPrioridad(), priority.getPrioridad()));
        }
    }

    public void obtenerEstados(Integer idPais) {
        estados = new ArrayList<>();
        List<Estados> tmpEstados = estadosFacade.estadosXPais(idPais);
        for (Estados e : tmpEstados) {
            EstadosDTO dto = new EstadosDTO();
            dto.setIdEstado(e.getIdEstado());
            dto.setIdPais(new PaisesDTO(idPais));
            dto.setNombre(e.getNombre());
            estados.add(dto);
        }
        Collections.sort(estados, new Comparator<EstadosDTO>() {
            @Override
            public int compare(EstadosDTO o1, EstadosDTO o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
    }

//    public void obtenerEstadosActualizar(Integer idPais) {
//        estadosActualizar = new ArrayList<>();
//        List<Estados> tmpEstados = estadosFacade.estadosXPais(idPais);
//        for (Estados e : tmpEstados) {
//            EstadosDTO dto = new EstadosDTO();
//            dto.setIdEstado(e.getIdEstado());
//            dto.setIdPais(new PaisesDTO(idPais));
//            dto.setNombre(e.getNombre());
//            estadosActualizar.add(dto);
//        }
//        Collections.sort(estadosActualizar, new Comparator<EstadosDTO>() {
//            @Override
//            public int compare(EstadosDTO o1, EstadosDTO o2) {
//                return o1.getNombre().compareTo(o2.getNombre());
//            }
//        });
//    }
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
    /**
     * *************FINALIZA CÓDIGO GENERAL E INICIAL*************
     */
}
