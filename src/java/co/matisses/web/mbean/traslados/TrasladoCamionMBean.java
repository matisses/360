package co.matisses.web.mbean.traslados;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.TrasladosSAPFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.Traslado;
import co.matisses.persistence.web.entity.TrasladoDetalle;
import co.matisses.persistence.web.entity.TrasladoUbicaciones;
import co.matisses.persistence.web.facade.TrasladoDetalleFacade;
import co.matisses.persistence.web.facade.TrasladoFacade;
import co.matisses.persistence.web.facade.TrasladoUbicacionesFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.StockTransferClient;
import co.matisses.web.dto.AlmacenDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.StockTransferDocumentBinAllocationDTO;
import co.matisses.web.dto.StockTransferDocumentDTO;
import co.matisses.web.dto.StockTransferDocumentLinesDTO;
import co.matisses.web.dto.TrasladoDTO;
import co.matisses.web.dto.TrasladoDetalleDTO;
import co.matisses.web.dto.TrasladoUbicacionesDTO;
import co.matisses.web.dto.UbicacionSAPDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import java.io.Serializable;
import java.math.BigDecimal;
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
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "trasladoCamionMBean")
public class TrasladoCamionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private SendHTMLEmailMBean emailSender;
    private static final Logger log = Logger.getLogger(TrasladoCamionMBean.class.getSimpleName());
    private Integer factura;
    private Integer idDetalleTraslado;
    private String almacenOrigen;
    private String almacenDestino;
    private String ubicacionOrigen = "TM";
    private String ubicacionDestino = "TM";
    private String referencia;
    private String almacenAsignado;
    private String comentario;
    private String comentarioLinea;
    private boolean dlgEliminar = false;
    private boolean dlgFactura = false;
    private boolean ubicacionOrigenValida = false;
    private boolean ubicacionDestinoValida = false;
    private boolean buscarXEan = false;
    private boolean usarComent = true;
    private boolean dlgUbicaciones = false;
    private boolean almacenElegido = false;
    private boolean dlgAlmacenes = false;
    private boolean imprimir = false;
    private boolean dlgComentarioLinea = false;
    private TrasladoDTO traslado;
    private List<TrasladoDetalleDTO> trasladoDetalles;
    private List<AlmacenDTO> almacenesOrigen;
    private List<AlmacenDTO> almacenesDestino;
    private List<UbicacionSAPDTO> ubicaciones;
    private List<SaldoItemDTO> saldos;
    private Map<String, List<SaldoItemDTO>> mapaSaldos;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private TrasladoFacade trasladoFacade;
    @EJB
    private TrasladoDetalleFacade trasladoDetalleFacade;
    @EJB
    private TrasladoUbicacionesFacade trasladoUbicacionesFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;

    public TrasladoCamionMBean() {
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
        almacenesDestino = new ArrayList<>();
        almacenesOrigen = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        saldos = new ArrayList<>();
        mapaSaldos = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerAlmacenesOrigen();
        verificarTrasladosPendientes();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public BaruGenericMBean getGenericMBean() {
        return genericMBean;
    }

    public void setGenericMBean(BaruGenericMBean genericMBean) {
        this.genericMBean = genericMBean;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public TrasladoDTO getTraslado() {
        return traslado;
    }

    public void setTraslado(TrasladoDTO traslado) {
        this.traslado = traslado;
    }

    public List<TrasladoDetalleDTO> getTrasladoDetalles() {
        return trasladoDetalles;
    }

    public void setTrasladoDetalles(List<TrasladoDetalleDTO> trasladoDetalles) {
        this.trasladoDetalles = trasladoDetalles;
    }

    public boolean isDlgEliminar() {
        return dlgEliminar;
    }

    public void setDlgEliminar(boolean dlgEliminar) {
        this.dlgEliminar = dlgEliminar;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public String getAlmacenOrigenSeleccionado() {
        if (almacenOrigen != null && !almacenOrigen.isEmpty()) {
            for (AlmacenDTO a : almacenesOrigen) {
                if (a.getWhsCode().equals(almacenOrigen)) {
                    if (a.getWhsCode().substring(0, 2).equals("09")) {
                        return a.getNombrextablet();
                    } else {
                        return a.getWhsCode() + " - " + a.getNombrextablet();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public String getAlmacenDestinoSeleccionado() {
        if (almacenDestino != null && !almacenDestino.isEmpty()) {
            for (AlmacenDTO a : almacenesDestino) {
                if (a.getWhsCode().equals(almacenDestino)) {
                    if (a.getWhsCode().substring(0, 2).equals("09")) {
                        return a.getNombrextablet();
                    } else {
                        return a.getWhsCode() + " - " + a.getNombrextablet();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public List<AlmacenDTO> getAlmacenesOrigen() {
        return almacenesOrigen;
    }

    public void setAlmacenesOrigen(List<AlmacenDTO> almacenesOrigen) {
        this.almacenesOrigen = almacenesOrigen;
    }

    public List<AlmacenDTO> getAlmacenesDestino() {
        return almacenesDestino;
    }

    public void setAlmacenesDestino(List<AlmacenDTO> almacenesDestino) {
        this.almacenesDestino = almacenesDestino;
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public boolean isDlgFactura() {
        return dlgFactura;
    }

    public void setDlgFactura(boolean dlgFactura) {
        this.dlgFactura = dlgFactura;
    }

    public boolean isBuscarXEan() {
        return buscarXEan;
    }

    public void setBuscarXEan(boolean buscarXEan) {
        this.buscarXEan = buscarXEan;
    }

    public boolean isUsarComent() {
        return usarComent;
    }

    public void setUsarComent(boolean usarComent) {
        this.usarComent = usarComent;
    }

    public List<UbicacionSAPDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<UbicacionSAPDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public boolean isDlgUbicaciones() {
        return dlgUbicaciones;
    }

    public void setDlgUbicaciones(boolean dlgUbicaciones) {
        this.dlgUbicaciones = dlgUbicaciones;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public boolean isAlmacenElegido() {
        return almacenElegido;
    }

    public void setAlmacenElegido(boolean almacenElegido) {
        this.almacenElegido = almacenElegido;
    }

    public boolean isDlgAlmacenes() {
        return dlgAlmacenes;
    }

    public void setDlgAlmacenes(boolean dlgAlmacenes) {
        this.dlgAlmacenes = dlgAlmacenes;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isDlgComentarioLinea() {
        return dlgComentarioLinea;
    }

    public void setDlgComentarioLinea(boolean dlgComentarioLinea) {
        this.dlgComentarioLinea = dlgComentarioLinea;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getComentarioLinea() {
        return comentarioLinea;
    }

    public void setComentarioLinea(String comentarioLinea) {
        this.comentarioLinea = comentarioLinea;
    }

    private void obtenerAlmacenesOrigen() {
        almacenesOrigen = new ArrayList<>();
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesBaru();

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen a : almacenes) {
                if (a.getWhsCode().substring(0, 2).equals("09") && !a.getWhsCode().contains("DM09") && !a.getWhsCode().contains("AF09")
                        && !a.getWhsCode().contains("IN09") && !a.getWhsCode().contains("MU09") && !a.getWhsCode().contains("CL09")) {
                    almacenesOrigen.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                } else if (!a.getWhsCode().substring(0, 2).equals("09") && !a.getWhsCode().contains("DM09") && !a.getWhsCode().contains("AF09")
                        && !a.getWhsCode().contains("IN09") && !a.getWhsCode().contains("MU09") && !a.getWhsCode().contains("CL09")) {
                    almacenesOrigen.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                }
            }
        }
    }

    public void obtenerAlmacenesDestino() {
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesBaru();

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen a : almacenes) {
                if (almacenOrigen.contains("09") && !a.getWhsCode().contains("DM") && !a.getWhsCode().contains("AF")
                        && !a.getWhsCode().contains("IN") && !a.getWhsCode().contains("MU") && !a.getWhsCode().contains("CL")) {
                    almacenesDestino.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                } else if (!almacenOrigen.contains("09") && a.getWhsCode().contains("09") && !a.getWhsCode().contains("DM")
                        && !a.getWhsCode().contains("AF") && !a.getWhsCode().contains("IN") && !a.getWhsCode().contains("MU") && !a.getWhsCode().contains("CL")) {
                    almacenesDestino.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                }
            }

            if (almacenOrigen.contains("09")) {
                if (almacenOrigen.contains("CL")) {
                    almacenOrigen = almacenOrigen.replace("CL", "");
                } else if (almacenOrigen.contains("DM")) {
                    almacenOrigen = almacenOrigen.replace("DM", "");
                } else if (almacenOrigen.contains("IN")) {
                    almacenOrigen = almacenOrigen.replace("IN", "");
                } else if (almacenOrigen.contains("MU")) {
                    almacenOrigen = almacenOrigen.replace("MU", "");
                } else if (almacenOrigen.contains("AF")) {
                    almacenOrigen = almacenOrigen.replace("AF", "");
                }
            }
        }
    }

    private void verificarTrasladosPendientes() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario tiene traslados pendientes");

        Traslado t = trasladoFacade.obtenerTrasladoPendiente(sessionMBean.getUsuario(), "camiones", sessionMBean.getAlmacen());

        if (t != null) {
            convertirTraslado(t);
            llenarDetalleTrasladoRecuperado();
        } else {
            log.log(Level.INFO, "no se encontraron traslados pendientes para el usuario");
        }
    }

    private void convertirTraslado(Traslado t) {
        traslado = new TrasladoDTO();
        traslado.setCedulaUsuario(t.getCedulaUsuario());
        traslado.setComentario(t.getComentario());
        traslado.setEstado(t.getEstado());
        traslado.setFecha(t.getFecha());
        traslado.setIdTraslado(t.getIdTraslado());
        traslado.setNumeroTraslado(t.getNumeroTraslado());
        traslado.setSerie(t.getSerie());
        traslado.setTipoMovimiento(t.getTipoMovimiento());
        traslado.setUsuario(t.getUsuario());
        traslado.setSucursal(t.getSucursal());
        traslado.setImprimir(t.getImprimir() != null ? t.getImprimir() : false);
        imprimir = traslado.isImprimir();
    }

    private void llenarDetalleTrasladoRecuperado() {
        List<TrasladoDetalle> dts = trasladoDetalleFacade.obtenerDatosTraslado(traslado.getIdTraslado());

        if (dts != null && !dts.isEmpty()) {
            for (TrasladoDetalle dt : dts) {
                trasladoDetalles.add(new TrasladoDetalleDTO(dt.getIdTrasladoDetalle(), dt.getIdTraslado().getIdTraslado(),
                        dt.getCantidad(), dt.getTipoDocumentoReferencia(), dt.getDocumentoReferencia(), dt.getReferencia(),
                        dt.getAlmacenOrigen(), dt.getAlmacenDestino(), dt.getComentario(), dt.getFecha(),
                        obtenerDatosUbicaciones(dt.getIdTrasladoDetalle())));
            }
            if (trasladoDetalles.get(0).getAlmacenOrigen().contains("09")) {
                almacenOrigen = (trasladoDetalles.get(0).getAlmacenOrigen().length() > 4) ? trasladoDetalles.get(0).getAlmacenOrigen().substring(2, 6) : trasladoDetalles.get(0).getAlmacenOrigen();
            } else {
                almacenOrigen = trasladoDetalles.get(0).getAlmacenOrigen();
            }
            obtenerAlmacenesDestino();
            almacenDestino = (trasladoDetalles.get(0).getAlmacenDestino().length() > 4) ? trasladoDetalles.get(0).getAlmacenDestino().substring(2, 6) : trasladoDetalles.get(0).getAlmacenDestino();
            validarUbicacionesAlmacenes(almacenOrigen, almacenDestino);

            Collections.sort(trasladoDetalles, new Comparator<TrasladoDetalleDTO>() {
                @Override
                public int compare(TrasladoDetalleDTO t, TrasladoDetalleDTO t1) {
                    return t1.getIdTrasladoDetalle().compareTo(t.getIdTrasladoDetalle());
                }
            });
        } else {
            Traslado t = trasladoFacade.find(traslado.getIdTraslado());

            if (t != null && t.getIdTraslado() != null && t.getIdTraslado() != 0) {
                t.setEstado("NF");

                try {
                    trasladoFacade.edit(t);
                    log.log(Level.INFO, "Se marco el traslado con id [{0}], como NO FINALIZADO");
                    verificarTrasladosPendientes();
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            }
        }
    }

    private List<TrasladoUbicacionesDTO> obtenerDatosUbicaciones(Integer idTrasladoDetalle) {
        if (idTrasladoDetalle != null && idTrasladoDetalle != 0) {
            List<TrasladoUbicaciones> ubis = trasladoUbicacionesFacade.obtenerTrasladoUbicaciones(idTrasladoDetalle);

            if (ubis != null && !ubis.isEmpty()) {
                List<TrasladoUbicacionesDTO> locations = new ArrayList<>();
                for (TrasladoUbicaciones ubi : ubis) {
                    locations.add(new TrasladoUbicacionesDTO(ubi.getIdTrasladoUbicacion(),
                            ubi.getIdTrasladoDetalle().getIdTrasladoDetalle(), ubi.getCantidad(),
                            ubi.getUbicacionOrigen(), ubi.getUbicacionDestino(), ubi.getFecha()));
                }
                return locations;
            }
        }
        return null;
    }

    private void validarUbicacionesAlmacenes(String whsOrigen, String whsDestino) {
        if (ubicacionSAPFacade.validarUbicacionesAlmacen(whsOrigen)) {
            UbicacionSAP location = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(whsOrigen + ubicacionOrigen);

            if (location != null && location.getAbsEntry() != null && location.getAbsEntry() != 0) {
                if (location.getDisabled() != null && !location.getDisabled().equals('Y')) {
                    ubicacionOrigenValida = true;
                } else {
                    mostrarMensaje("Error", "No se puede mover la mercancia del almacén origen, debido a que la ubicación " + ubicacionOrigen + " esta deshabilitada", true, false, false);
                    log.log(Level.SEVERE, "No se puede mover la mercancia del almacen origen, debido a que la ubicacion {0} esta deshabilitada", ubicacionOrigen);
                    return;
                }
            } else {
                ubicacionOrigenValida = false;
                ubicacionOrigen = null;
            }
        }
        if (ubicacionSAPFacade.validarUbicacionesAlmacen(whsOrigen.length() > 4 ? whsOrigen.substring(0, 2) + whsDestino : whsDestino)) {
            UbicacionSAP location = ubicacionSAPFacade.obtenerDatosUbicacionBinCode((whsOrigen.length() > 4 ? whsOrigen.substring(0, 2) + whsDestino : whsDestino) + ubicacionOrigen);

            if (location != null && location.getAbsEntry() != null && location.getAbsEntry() != 0) {
                if (location.getDisabled() != null && !location.getDisabled().equals('Y')) {
                    ubicacionDestinoValida = true;
                } else {
                    mostrarMensaje("Error", "No se puede mover la mercancia del almacén destino, debido a que la ubicación " + ubicacionOrigen + " esta deshabilitada", true, false, false);
                    log.log(Level.SEVERE, "No se puede mover la mercancia del almacen destino, debido a que la ubicacion {0} esta deshabilitada", ubicacionOrigen);
                    return;
                }
            } else {
                ubicacionDestinoValida = false;
                ubicacionDestino = null;
            }
        }
    }

    public void seleccionarAlmacenOrigen() {
        almacenOrigen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacenOrigen");
        log.log(Level.INFO, "Almacen origen seleccionado {0}", almacenOrigen);
        obtenerAlmacenesDestino();
    }

    public void seleccionarAlmacenDestino() {
        almacenDestino = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacenDestino");
        log.log(Level.INFO, "Almacen destino seleccionado {0}", almacenDestino);
    }

    public void usarEan() {
        if (buscarXEan) {
            buscarXEan = false;
        } else {
            buscarXEan = true;
        }
    }

    public void usarComentario() {
        if (usarComent) {
            usarComent = false;
        } else {
            usarComent = true;
        }
    }

    public void imprimirTraslado() {
        if (imprimir) {
            imprimir = false;
        } else {
            imprimir = true;
        }
    }

    public boolean agregarReferencia() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO_CAMIÓN, Acciones.CREAR)) {
            if (almacenOrigen == null || almacenOrigen.isEmpty()) {
                log.log(Level.SEVERE, "Se debe ingresar un almacen origen para continuar");
                mostrarMensaje("Error", "Se debe ingresar un almacén origen para continuar", true, false, false);
                limpiarBasico();
                ubicacionDestinoValida = false;
                ubicacionOrigenValida = false;
                return false;
            }
            if (almacenDestino == null || almacenDestino.isEmpty()) {
                log.log(Level.SEVERE, "Se debe ingresar un almacen destino para continuar");
                mostrarMensaje("Error", "Se debe ingresar un almacén destino para continuar", true, false, false);
                limpiarBasico();
                ubicacionDestinoValida = false;
                ubicacionOrigenValida = false;
                return false;
            }
            if (referencia == null || referencia.isEmpty()) {
                log.log(Level.SEVERE, "Se debe ingresar una referencia para poder continuar");
                mostrarMensaje("Error", "Se debe ingresar una referencia para poder continuar", true, false, false);
                limpiarBasico();
                ubicacionDestinoValida = false;
                ubicacionOrigenValida = false;
                return false;
            }
            if (!ubicacionDestinoValida && !ubicacionOrigenValida) {
                validarUbicacionesAlmacenes(almacenOrigen, almacenDestino);
            }

            if (buscarXEan) {
                ItemInventario item = itemInventarioFacade.buscarXEan(referencia);

                if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                    referencia = item.getItemCode();
                }
            }

            if (usarComent && (comentarioLinea == null || comentarioLinea.isEmpty())) {
                dlgComentarioLinea = true;
                return true;
            } else {
                dlgComentarioLinea = false;
            }

            if (referencia.length() < 20) {
                if (sessionMBean.validarPermisoUsuario(Objetos.REFERENCIA_CORTA, Acciones.USAR)) {
                    referencia = genericMBean.completarReferencia(referencia);
                } else {
                    mostrarMensaje("Error", "Debe ingresar una referencia de 20 caracteres, para poder agregar.", true, false, false);
                    log.log(Level.SEVERE, "Debe ingresar una referencia de 20 caracteres, para poder agregar");
                    ubicacionDestinoValida = false;
                    ubicacionOrigenValida = false;
                    return false;
                }
            } else if (referencia.length() > 20) {
                log.log(Level.SEVERE, "Se debe ingresar una referencia de 20 caracteres para poder continuar");
                mostrarMensaje("Error", "Se debe ingresar una referencia de 20 caracteres para poder continuar", true, false, false);
                limpiarBasico();
                ubicacionDestinoValida = false;
                ubicacionOrigenValida = false;
                return false;
            }

            log.log(Level.INFO, "Procesando referencia [{0}]", referencia);
            if (almacenOrigen.contains("CL")) {
                if (factura == null || factura == 0) {
                    mostrarMensaje("Advertencia", "Ingrese el número de la factura.", false, false, true);
                    log.log(Level.WARNING, "Ingrese el numero de la factura");
                    dlgFactura = true;
                    ubicacionDestinoValida = false;
                    ubicacionOrigenValida = false;
                    return false;
                }
            }

            /*Se valida si el traslado es de demostraciones*/
            if (almacenOrigen.contains("DM") && (ubicacionDestino == null || ubicacionDestino.equals("TM")) && (ubicacionOrigen == null || ubicacionOrigen.equals("TM"))) {
                ubicacionDestino = null;
                ubicacionOrigen = null;
                ubicaciones.clear();

                List<UbicacionSAP> ubicacionesDemostracion = ubicacionSAPFacade.obtenerUbicacionesDemostracion(almacenOrigen, referencia);

                if (ubicacionesDemostracion != null && !ubicacionesDemostracion.isEmpty()) {
                    for (UbicacionSAP u : ubicacionesDemostracion) {
                        ubicaciones.add(new UbicacionSAPDTO(u.getAbsEntry(), u.getBinCode(), u.getWhsCode(), u.getSL1Code(), u.getSL2Code(), u.getAttr2Val()));
                    }

                    if (ubicaciones != null && !ubicaciones.isEmpty()) {
                        dlgUbicaciones = true;
                        mostrarMensaje("Advertencia", "Seleccione la demostración del asesor.", false, false, true);
                        log.log(Level.WARNING, "Seleccione la demostracion del asesor");
                        ubicacionDestinoValida = false;
                        ubicacionOrigenValida = false;
                        return false;
                    } else {
                        dlgUbicaciones = false;
                        mostrarMensaje("Error", "No se encontraron demostraciones para la referencia ingresada.", true, false, false);
                        log.log(Level.SEVERE, "No se encontraron demostraciones para la referencia ingresada");
                        ubicacionDestinoValida = false;
                        ubicacionOrigenValida = false;
                        return false;
                    }
                }
            }

            if (!almacenOrigen.contains("09")) {
                cargarCamion();
            } else if (almacenOrigen.contains("09")) {
                descargarCamion();
            }
            if (almacenOrigen.contains("DM")) {
                ubicacionOrigen = "TM";
                ubicacionDestino = "TM";
                dlgUbicaciones = false;
                ubicaciones = new ArrayList<>();
                ubicacionDestinoValida = false;
                ubicacionOrigenValida = false;
                return true;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            limpiarBasico();
            ubicacionDestinoValida = false;
            ubicacionOrigenValida = false;
            return false;
        }
        ubicacionDestinoValida = false;
        ubicacionOrigenValida = false;
        return false;
    }

    private boolean cargarCamion() {
        String nuevoAlmacenOrigen = almacenOrigen;
        String nuevoAlmacenDestino = (almacenOrigen.length() > 4) ? almacenOrigen.substring(0, 2) + almacenDestino : almacenDestino;
        if (ubicacionOrigenValida) {
            SaldoUbicacion saldo = saldoUbicacionFacade.obtenerSaldoXUbicacion(referencia, nuevoAlmacenOrigen + ubicacionOrigen);

            if (saldo != null && saldo.getAbsEntry() != null && saldo.getAbsEntry() != 0) {
                for (TrasladoDetalleDTO t : trasladoDetalles) {
                    if (t.getReferencia().equals(referencia) && t.getAlmacenOrigen().equals(nuevoAlmacenOrigen)) {
                        if (t.getTrasladoUbicaciones() != null && !t.getTrasladoUbicaciones().isEmpty()) {
                            for (TrasladoUbicacionesDTO u : t.getTrasladoUbicaciones()) {
                                if (u.getUbicacionOrigen().equals(ubicacionOrigen)) {
                                    saldo.setOnHandQty(new BigDecimal(saldo.getOnHandQty().intValue() - u.getCantidad()));
                                    break;
                                }
                            }
                        }
                    }
                }
                if (saldo.getOnHandQty().intValue() == 0) {
                    log.log(Level.SEVERE, "No se encontro saldo disponible de la referencia [{0}] para mover", referencia);
                    mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", para mover", true, false, false);
                    limpiarBasico();
                    return false;
                }
            } else {
                log.log(Level.SEVERE, "No se encontro saldo de la referencia [{0}] en la ubicacion [{1}]", new Object[]{referencia, ubicacionOrigen});
                mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", en la ubicación " + ubicacionOrigen, true, false, false);
                limpiarBasico();
                return false;
            }
        } else {
            SaldoItemInventario saldo = saldoItemInventarioFacade.buscarReferenciaSaldoBodega(referencia, nuevoAlmacenOrigen);

            if (saldo != null && saldo.getSaldoItemInventarioPK().getItemCode() != null && !saldo.getSaldoItemInventarioPK().getItemCode().isEmpty()) {
                for (TrasladoDetalleDTO t : trasladoDetalles) {
                    if (t.getReferencia().equals(referencia) && t.getAlmacenOrigen().equals(nuevoAlmacenOrigen)) {
                        saldo.setOnHand(new BigDecimal(saldo.getOnHand().intValue() - t.getCantidad()));
                    }
                }
                if (saldo.getOnHand().intValue() == 0) {
                    log.log(Level.SEVERE, "No se encontro saldo disponible de la referencia [{0}] para mover", referencia);
                    mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", para mover", true, false, false);
                    limpiarBasico();
                    return false;
                }
            } else {
                log.log(Level.SEVERE, "No se encontro saldo de la referencia [{1}] en el almacen [{0}]", new Object[]{nuevoAlmacenOrigen, referencia});
                mostrarMensaje("Error", "No se encontró saldo de la referencia ingresada en la almacén " + nuevoAlmacenOrigen, true, false, false);
                limpiarBasico();
                return false;
            }
        }
        if (almacenDestino.contains("990") && (comentarioLinea == null || comentarioLinea.isEmpty())) {
            dlgComentarioLinea = true;
            mostrarMensaje("Error", "Debe ingresar un comentario para la referencia.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un comentario para la referencia");
            return false;
        } else if (almacenDestino.contains("990") && (comentarioLinea.length() > 250)) {
            mostrarMensaje("Error", "El comentario es demasiado largo.", true, false, false);
            log.log(Level.SEVERE, "El comentario es demasiado largo");
            return false;
        }

        if (!agregarItemBD(new TrasladoDetalleDTO(null, null, 1, null,
                (factura == null && nuevoAlmacenOrigen.contains("DM")) ? ubicacionSAPFacade.obtenerDatosUbicacionBinCode(nuevoAlmacenOrigen + ubicacionOrigen).getAbsEntry() : factura,
                referencia, nuevoAlmacenOrigen, nuevoAlmacenDestino, comentarioLinea != null ? comentarioLinea : "", new Date()))) {
            mostrarMensaje("Error", "No se pudo agregar la referencia al traslado en base de datos.", true, false, false);
            log.log(Level.SEVERE, "No se pudo agregar la referencia al traslado en base de datos");
            return false;
        }
        return true;
    }

    private boolean descargarCamion() {
        String nuevoAlmacenOrigen = almacenOrigen;
        String nuevoAlmacenDestino = (almacenOrigen.length() > 4) ? almacenOrigen.substring(0, 2) + almacenDestino : almacenDestino;

        if (mapaSaldos == null || mapaSaldos.isEmpty() || trasladoDetalles == null || trasladoDetalles.isEmpty()) {
            List<SaldoItemInventario> balances = saldoItemInventarioFacade.obtenerSaldosAlmacen(nuevoAlmacenOrigen);

            if (balances != null && !balances.isEmpty()) {
                mapaSaldos = new HashMap<>();

                for (SaldoItemInventario s : balances) {
                    if (mapaSaldos.containsKey(s.getSaldoItemInventarioPK().getItemCode())) {
                        SaldoItemDTO item = new SaldoItemDTO(s.getOnHand().intValue(), 0.0, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode(),
                                new ArrayList<SaldoUbicacionDTO>());

                        List<SaldoUbicacion> ubi = saldoUbicacionFacade.findByItemCodeAndWhsCode(true, s.getSaldoItemInventarioPK().getItemCode(),
                                s.getSaldoItemInventarioPK().getWhsCode().getWhsCode());

                        if (ubi != null && !ubi.isEmpty()) {
                            for (SaldoUbicacion u : ubi) {
                                item.getSaldoUbicacion().add(new SaldoUbicacionDTO(u.getAbsEntry(), u.getUbicacion().getAbsEntry(), null, u.getOnHandQty().intValue(),
                                        u.getItemCode(), u.getWhsCode(), u.getUbicacion().getBinCode(), null));
                            }
                        }

                        mapaSaldos.get(s.getSaldoItemInventarioPK().getItemCode()).add(item);
                    } else {
                        ArrayList<SaldoItemDTO> i = new ArrayList<>();
                        SaldoItemDTO item = new SaldoItemDTO(s.getOnHand().intValue(), 0.0, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode(),
                                new ArrayList<SaldoUbicacionDTO>());

                        List<SaldoUbicacion> ubi = saldoUbicacionFacade.findByItemCodeAndWhsCode(true, s.getSaldoItemInventarioPK().getItemCode(),
                                s.getSaldoItemInventarioPK().getWhsCode().getWhsCode());

                        if (ubi != null && !ubi.isEmpty()) {
                            for (SaldoUbicacion u : ubi) {
                                item.getSaldoUbicacion().add(new SaldoUbicacionDTO(u.getAbsEntry(), u.getUbicacion().getAbsEntry(), null, u.getOnHandQty().intValue(),
                                        u.getItemCode(), u.getWhsCode(), u.getUbicacion().getBinCode(), null));
                            }
                        }

                        i.add(item);

                        mapaSaldos.put(s.getSaldoItemInventarioPK().getItemCode(), i);
                    }
                }
            }
        }

        if (mapaSaldos != null && !mapaSaldos.isEmpty()) {
            List<SaldoItemDTO> s = mapaSaldos.get(referencia);

            if (s != null && s.size() > 1) {
                if (!almacenElegido) {
                    saldos = new ArrayList<>(s);
                    dlgAlmacenes = true;
                    mostrarMensaje("Error", "Seleccione el almacén del que quiere trasladar.", true, false, false);
                    log.log(Level.SEVERE, "Seleccione el almacen del que quiere trasladar");
                    return false;
                } else {
                    nuevoAlmacenOrigen = almacenAsignado;
                    nuevoAlmacenDestino = (nuevoAlmacenOrigen.length() > 4) ? nuevoAlmacenOrigen.substring(0, 2) + nuevoAlmacenDestino : nuevoAlmacenDestino;
                }
            } else if (s != null && !s.isEmpty()) {
                nuevoAlmacenOrigen = s.get(0).getAlmacen();
                nuevoAlmacenDestino = (nuevoAlmacenOrigen.length() > 4) ? nuevoAlmacenOrigen.substring(0, 2) + nuevoAlmacenDestino : nuevoAlmacenDestino;
            } else {
                mostrarMensaje("Error", "No se encontró saldo de la referencia solicitada.", true, false, false);
                log.log(Level.SEVERE, "No se encontro saldo de la referencia solicitada");
                return false;
            }
        }

        if (nuevoAlmacenOrigen.contains("CL")) {
            if (factura == null || factura == 0) {
                mostrarMensaje("Advertencia", "Ingrese el número de la factura.", false, false, true);
                log.log(Level.WARNING, "Ingrese el numero de la factura");
                dlgFactura = true;
                return false;
            }
        }

        /*Se valida si el traslado es de demostraciones*/
        if (nuevoAlmacenOrigen.contains("DM") && (ubicacionDestino == null || ubicacionDestino.equals("TM")) && (ubicacionOrigen == null || ubicacionOrigen.equals("TM"))) {
            ubicacionDestino = null;
            ubicacionOrigen = null;
            ubicaciones.clear();

            List<UbicacionSAP> ubicacionesDemostracion = ubicacionSAPFacade.obtenerUbicacionesDemostracion(nuevoAlmacenOrigen, referencia);

            if (ubicacionesDemostracion != null && !ubicacionesDemostracion.isEmpty()) {
                for (UbicacionSAP u : ubicacionesDemostracion) {
                    ubicaciones.add(new UbicacionSAPDTO(u.getAbsEntry(), u.getBinCode(), u.getWhsCode(), u.getSL1Code(), u.getSL2Code(), u.getAttr2Val()));
                }

                if (ubicaciones != null && !ubicaciones.isEmpty() && ubicaciones.size() > 1) {
                    dlgUbicaciones = true;
                    mostrarMensaje("Advertencia", "Seleccione la demostración del asesor.", false, false, true);
                    log.log(Level.WARNING, "Seleccione la demostracion del asesor");
                    return false;
                } else if (ubicaciones != null && !ubicaciones.isEmpty() && ubicaciones.size() == 1) {
                    ubicacionOrigen = ubicaciones.get(0).getBinCode().replace(almacenOrigen, "");
                    ubicacionOrigen = ubicacionOrigen.replace("DM", "");
                    ubicacionDestino = ubicacionOrigen;

                    validarUbicacionesAlmacenes(nuevoAlmacenOrigen, nuevoAlmacenOrigen);
                } else {
                    dlgUbicaciones = false;
                    mostrarMensaje("Error", "No se encontraron demostraciones para la referencia ingresada.", true, false, false);
                    log.log(Level.SEVERE, "No se encontraron demostraciones para la referencia ingresada");
                    return false;
                }
            }
        }
        if (ubicacionOrigenValida) {
            /*Se valida el saldo disponible vs el que se quiere agregar*/
            List<SaldoItemDTO> s = mapaSaldos.get(referencia);
            for (SaldoItemDTO dto : s) {
                if (dto.getAlmacen().equals(nuevoAlmacenOrigen)) {
                    int cantidad = 0;
                    for (TrasladoDetalleDTO t : trasladoDetalles) {
                        if (t.getReferencia().equals(referencia) && t.getAlmacenOrigen().equals(nuevoAlmacenOrigen)) {
//                            if (t.getTrasladoUbicaciones() != null && !t.getTrasladoUbicaciones().isEmpty()) {
//                                for (TrasladoUbicacionesDTO u : t.getTrasladoUbicaciones()) {
//                                    if (u.getUbicacionOrigen().equals(ubicacionOrigen)) {
//                                        cantidad += u.getCantidad();
                            cantidad += t.getCantidad();
//                                    }
//                                }
//                            }
                        }
                    }
                    if (cantidad >= dto.getCantidad()) {
                        log.log(Level.SEVERE, "No se encontro saldo disponible de la referencia [{0}] para mover", referencia);
                        mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", para mover", true, false, false);
                        limpiarBasico();
                        limpiarDescargueCamion();
                        return false;
                    }
                }
            }
        } else {
            List<SaldoItemDTO> saldo = new ArrayList<>(mapaSaldos.get(referencia));
            for (SaldoItemDTO s : saldo) {
                if (s.getAlmacen().equals(nuevoAlmacenOrigen)) {
                    int cantidad = 0;
                    for (TrasladoDetalleDTO t : trasladoDetalles) {
                        if (t.getReferencia().equals(referencia) && t.getAlmacenOrigen().equals(nuevoAlmacenOrigen)) {
                            cantidad += t.getCantidad();
                        }
                    }
                    if (cantidad >= s.getCantidad()) {
                        log.log(Level.SEVERE, "No se encontro saldo disponible de la referencia [{0}] para mover", referencia);
                        mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", para mover", true, false, false);
                        limpiarBasico();
                        limpiarDescargueCamion();
                        return false;
                    }
                }
            }
        }
        if (almacenDestino.contains("990") && (comentarioLinea == null || comentarioLinea.isEmpty())) {
            dlgComentarioLinea = true;
            mostrarMensaje("Error", "Debe ingresar un comentario para la referencia.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un comentario para la referencia");
            return false;
        } else if (almacenDestino.contains("990") && (comentarioLinea.length() > 250)) {
            mostrarMensaje("Error", "El comentario es demasiado largo.", true, false, false);
            log.log(Level.SEVERE, "El comentario es demasiado largo");
            return false;
        }

        if (!agregarItemBD(new TrasladoDetalleDTO(null, null, 1, null,
                (factura == null && nuevoAlmacenOrigen.contains("DM")) ? ubicacionSAPFacade.obtenerDatosUbicacionBinCode(nuevoAlmacenOrigen + ubicacionOrigen).getAbsEntry() : factura,
                referencia, nuevoAlmacenOrigen, nuevoAlmacenDestino, comentarioLinea != null ? comentarioLinea : "", new Date()))) {
            mostrarMensaje("Error", "No se pudo agregar la referencia al traslado en base de datos.", true, false, false);
            log.log(Level.SEVERE, "No se pudo agregar la referencia al traslado en base de datos");
            limpiarDescargueCamion();
            return false;
        }
        limpiarDescargueCamion();
        return true;
    }

    public void limpiarDescargueCamion() {
        almacenAsignado = null;
        almacenElegido = false;
        ubicacionOrigen = "TM";
        ubicacionDestino = "TM";
        dlgUbicaciones = false;
        ubicaciones = new ArrayList<>();
    }

    public void seleccionarUbicacionDemostracion() {
        String location = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("binCode");
        log.log(Level.INFO, "Se selecciono la ubicacion de demostarcion {0}, para el traslado entre almacenes", location);
        if (location != null && !location.isEmpty()) {
            ubicacionOrigen = location.replace(almacenOrigen, "");
            ubicacionOrigen = ubicacionOrigen.replace("DM", "");
            ubicacionDestino = ubicacionOrigen;

            validarUbicacionesAlmacenes((!almacenOrigen.contains("DM")) ? "DM" + almacenOrigen : almacenOrigen, almacenDestino);
            if (ubicacionDestinoValida && ubicacionOrigenValida) {
                if (!agregarReferencia() && !almacenDestino.contains("990")) {
                    cancelarProcesoUbicacionDemostracion();
                }
            } else {
                mostrarMensaje("Error", "No se pudieron relacionar las ubicaciones, comuniquese con el departamento de sistemas.", true, false, false);
                log.log(Level.SEVERE, "No se pudieron relacionar las ubicaciones, comuniquese con el departamento de sistemas");
                ubicacionOrigen = "TM";
                ubicacionDestino = "TM";
                dlgUbicaciones = false;
                ubicaciones = new ArrayList<>();
                return;
            }
        }
    }

    public void seleccionarAlmacen() {
        almacenAsignado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacen");
        log.log(Level.INFO, "Se selecciono el almacen {0}, para el descargue de camion");
        dlgAlmacenes = false;
        almacenElegido = true;
        agregarReferencia();
    }

    public void cancelarProcesoUbicacionDemostracion() {
        ubicacionOrigen = "TM";
        ubicacionDestino = "TM";
        dlgUbicaciones = false;
        ubicaciones = new ArrayList<>();
    }

    private boolean agregarItemBD(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia [{0}], al traslado entre almacenes en BD", referencia);

        if (traslado == null || traslado.getIdTraslado() == null || traslado.getIdTraslado() == 0) {
            if (!crearTraslado()) {
                mostrarMensaje("Error", "no se pudo crear el traslado. comuniquese con el departamento de sistemas", true, false, false);
                log.log(Level.SEVERE, "no se pudo crear el traslado. comuniquese con el departamento de sistemas");
                limpiarBasico();
                return false;
            }
        }

        if (trasladoDetalles != null && !trasladoDetalles.isEmpty()) {
            for (TrasladoDetalleDTO dto : trasladoDetalles) {
                if (dto.getReferencia().equals(td.getReferencia()) && dto.getIdTrasladoDetalle() != null && dto.getIdTrasladoDetalle() != 0 && dto.getAlmacenOrigen().equals(td.getAlmacenOrigen())
                        && (td.getDocumentoReferencia() == null || (dto.getDocumentoReferencia() != null && dto.getDocumentoReferencia().equals(td.getDocumentoReferencia())))
                        && dto.getComentario().equals(td.getComentario())) {
                    td.setIdTrasladoDetalle(dto.getIdTrasladoDetalle());
                    td.setCantidad(dto.getCantidad() + 1);
                    break;
                }
            }
        }

        if (td.getIdTrasladoDetalle() != null && td.getIdTrasladoDetalle() != 0) {
            if (!actualizarTrasladoDetalle(td, traslado)) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado entre almacenes", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la referencia solicitada al traslado entre almacenes");
                limpiarBasico();
                return false;
            } else {
                agregarMovimiento(td);
            }
        } else {
            td.setIdTrasladoDetalle(agregarTrasladoDetalle(td, traslado));
            if (td.getIdTrasladoDetalle() == null || td.getIdTrasladoDetalle() == 0) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado entre almacenes", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la referencia solicitada al traslado entre almacenes");
                limpiarBasico();
                return false;
            } else {
                agregarMovimiento(td);
            }
        }
        return true;
    }

    private void agregarMovimiento(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia para traslado entre almacenes");
        boolean existe = false;
        boolean ubicacionExiste = false;
        if (trasladoDetalles.isEmpty()) {
            existe = false;
            ubicacionExiste = false;
        } else {
            for (TrasladoDetalleDTO dto : trasladoDetalles) {
                if (dto.getReferencia().equals(td.getReferencia()) && ((td.getDocumentoReferencia() == null || dto.getDocumentoReferencia() == null)
                        || (td.getDocumentoReferencia().equals(dto.getDocumentoReferencia()))) && dto.getAlmacenOrigen().equals(td.getAlmacenOrigen()) && dto.getComentario().equals(td.getComentario())) {
                    dto.setCantidad(td.getCantidad());
                    existe = true;

                    for (TrasladoUbicacionesDTO d : dto.getTrasladoUbicaciones()) {
                        if (d.getUbicacionDestino().equals((ubicacionDestino == null || ubicacionDestino.isEmpty()) ? ubicacionOrigen : ubicacionDestino)
                                && d.getUbicacionOrigen().equals(ubicacionOrigen)) {
                            ubicacionExiste = true;
                            d.setCantidad(d.getCantidad() + 1);
                            actualizarTrasladoUbicacion(d);
                            break;
                        } else {
                            ubicacionExiste = false;
                        }
                    }
                    break;
                } else {
                    existe = false;
                    ubicacionExiste = false;
                }
            }
        }
        if (!existe) {
            trasladoDetalles.add(0, td);

            if (traslado.getTrasladoDetalles() == null) {
                traslado.setTrasladoDetalles(new ArrayList<TrasladoDetalleDTO>());
            }
        }
        if (!ubicacionExiste) {
            TrasladoUbicaciones tu = agregarRegistroUbicacion(td.getIdTrasladoDetalle());

            if (tu != null && tu.getIdTrasladoUbicacion() != null && tu.getIdTrasladoUbicacion() != 0) {
                if (!existe) {
                    if (trasladoDetalles.get(0).getTrasladoUbicaciones() == null) {
                        trasladoDetalles.get(0).setTrasladoUbicaciones(new ArrayList<TrasladoUbicacionesDTO>());
                    }

                    trasladoDetalles.get(0).addTrasladoUbicacion(new TrasladoUbicacionesDTO(tu.getIdTrasladoUbicacion(),
                            tu.getIdTrasladoDetalle().getIdTrasladoDetalle(), tu.getCantidad(), tu.getUbicacionOrigen(),
                            tu.getUbicacionDestino(), tu.getFecha()));
                } else {
                    for (TrasladoDetalleDTO dto : trasladoDetalles) {
                        if (dto.getReferencia().equals(td.getReferencia()) && ((td.getDocumentoReferencia() == null || dto.getDocumentoReferencia() == null)
                                || (td.getDocumentoReferencia().equals(dto.getDocumentoReferencia())))) {
                            for (TrasladoUbicacionesDTO d : dto.getTrasladoUbicaciones()) {
                                if (d.getUbicacionDestino().equals(ubicacionDestino) && d.getUbicacionOrigen().equals(ubicacionOrigen)) {
                                    ubicacionExiste = true;
                                    break;
                                }
                            }

                            if (!ubicacionExiste) {
                                dto.addTrasladoUbicacion(new TrasladoUbicacionesDTO(tu.getIdTrasladoUbicacion(),
                                        tu.getIdTrasladoDetalle().getIdTrasladoDetalle(), tu.getCantidad(), tu.getUbicacionOrigen(), tu.getUbicacionDestino(), tu.getFecha()));
                            }
                            break;
                        }
                    }
                }
            }

        }
        limpiarBasico();
    }

    public void seleccionarReferenciaEliminar() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO_CAMIÓN, Acciones.CREAR)) {
            idDetalleTraslado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDetalleTraslado"));

            if (idDetalleTraslado != null && idDetalleTraslado != 0) {
                for (TrasladoDetalleDTO t : trasladoDetalles) {
                    if (t.getIdTrasladoDetalle().equals(idDetalleTraslado)) {
                        factura = t.getDocumentoReferencia();
                        referencia = t.getReferencia();
                        dlgEliminar = true;
                        comentarioLinea = t.getComentario();
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            limpiarBasico();
            return;
        }
    }

    public void cancelarEliminacionItem() {
        referencia = null;
        factura = null;
        comentarioLinea = null;
        dlgEliminar = false;
        dlgComentarioLinea = false;
    }

    public void quitarReferencia() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO_CAMIÓN, Acciones.CREAR)) {
            for (TrasladoDetalleDTO d : trasladoDetalles) {
                if (d.getIdTrasladoDetalle().equals(idDetalleTraslado)) {
                    if (d.getTrasladoUbicaciones().get(0).getCantidad() > 1) {
                        d.getTrasladoUbicaciones().get(0).setCantidad(d.getTrasladoUbicaciones().get(0).getCantidad() - 1);
                        if (actualizarTrasladoUbicacion(d.getTrasladoUbicaciones().get(0))) {
                            log.log(Level.INFO, "Se elimino una unidad de la referencia [{0}], para el detalle de traslado con id [{1}]",
                                    new Object[]{referencia, d.getIdTrasladoDetalle()});
                        } else {
                            log.log(Level.SEVERE, "Ocurrio un error al eliminar una unidad del detalle con id [{0}], comuniquese con sistemas", d.getIdTrasladoDetalle());
                            mostrarMensaje("Error", "Ocurrio un error al eliminar una unidad del detalle, comuniquese con sistemas", true, false, false);
                            limpiarBasico();
                            return;
                        }

                        d.setCantidad(d.getCantidad() - 1);
                        if (actualizarTrasladoDetalle(d, traslado)) {
                            log.log(Level.INFO, "Se elimino una unidad de la referencia [{0}], para el traslado con id [{1}]",
                                    new Object[]{referencia, traslado.getIdTraslado()});
                        } else {
                            log.log(Level.SEVERE, "Ocurrio un error al eliminar una unidad del traslado con id [{0}], comuniquese con sistemas", traslado.getIdTraslado());
                            mostrarMensaje("Error", "Ocurrio un error al eliminar una unidad del traslado, comuniquese con sistemas", true, false, false);
                            limpiarBasico();
                            return;
                        }
                    } else {
                        //Como solo se encontro una unidad en ubicaciones de la referencia, se eliminan los registros de ubicacion y detalle
                        TrasladoUbicaciones u = trasladoUbicacionesFacade.find(d.getTrasladoUbicaciones().get(0).getIdTrasladoUbicacion());

                        if (u != null && u.getIdTrasladoUbicacion() != null & u.getIdTrasladoUbicacion() != 0) {
                            try {
                                //Se inicia eliminando el registro de la ubicacion
                                trasladoUbicacionesFacade.remove(u);
                                log.log(Level.INFO, "Se elimino registro de ubicacion traslado");

                                //posteriormente despues de eliminar la ubicacion, se elimina el registro del detalle
                                TrasladoDetalle td = trasladoDetalleFacade.find(d.getIdTrasladoDetalle());

                                if (td != null && td.getIdTrasladoDetalle() != null && td.getIdTrasladoDetalle() != 0) {
                                    try {
                                        trasladoDetalleFacade.remove(td);
                                        log.log(Level.INFO, "Se elimino registro de detalle traslado");
                                    } catch (Exception e) {
                                        log.log(Level.SEVERE, "Ocurrio un error al eliminar el detalle del traslado. Error [{0}]", e.getMessage());
                                        mostrarMensaje("Error", "Ocurrio un error al eliminar los datos. Comuniquese con sistemas", true, false, false);
                                        limpiarBasico();
                                        return;
                                    }
                                }
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al eliminar las ubicaciones del traslado. Error [{0}]", e.getMessage());
                                mostrarMensaje("Error", "Ocurrio un error al eliminar los datos. Comuniquese con sistemas", true, false, false);
                                limpiarBasico();
                                return;
                            }
                        }

                        trasladoDetalles.remove(d);
                        break;
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            limpiarBasico();
            return;
        }
        limpiarBasico();
    }

    private boolean crearTraslado() {
        Traslado t = new Traslado();

        t.setCedulaUsuario(sessionMBean.getCedulaEmpleado() + "PR");
        t.setComentario(almacenOrigen.contains("DM") ? "Traslado desde 360 - Demostraciones" : "Traslado desde 360");
        t.setEstado("TP");
        t.setFecha(new Date());
        t.setSerie(almacenOrigen.contains("DM") ? 193 : 27);
        t.setTipoMovimiento("camiones");
        t.setUsuario(sessionMBean.getUsuario());
        t.setSucursal(sessionMBean.getAlmacen());

        try {
            trasladoFacade.create(t);
            log.log(Level.INFO, "Se creo traslado [entre almacenes] en web con id [{0}]", t.getIdTraslado());
            convertirTraslado(t);
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage(), e);
            return false;
        }
    }

    private Integer agregarTrasladoDetalle(TrasladoDetalleDTO td, TrasladoDTO t) {
        TrasladoDetalle trasladoDetalle = new TrasladoDetalle();

        trasladoDetalle.setAlmacenDestino(td.getAlmacenDestino());
        trasladoDetalle.setAlmacenOrigen(td.getAlmacenOrigen());
        trasladoDetalle.setCantidad(td.getCantidad());
        trasladoDetalle.setComentario(td.getComentario() != null && !td.getComentario().isEmpty() ? td.getComentario().toUpperCase() : null);
        trasladoDetalle.setFecha(new Date());
        trasladoDetalle.setIdTraslado(new Traslado(traslado.getIdTraslado()));
        trasladoDetalle.setReferencia(td.getReferencia());

        if (td.getDocumentoReferencia() != null && td.getDocumentoReferencia() != 0) {
            trasladoDetalle.setDocumentoReferencia(td.getDocumentoReferencia());
            trasladoDetalle.setTipoDocumentoReferencia(0);
        }

        try {
            trasladoDetalleFacade.create(trasladoDetalle);
            log.log(Level.INFO, "Se creo detalle para el traslado con id [{0}], para la referencias [{1}], con id de detalle traslado [{2}]",
                    new Object[]{t.getIdTraslado(), td.getReferencia(), trasladoDetalle.getIdTrasladoDetalle()});
            return trasladoDetalle.getIdTrasladoDetalle();
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    private boolean actualizarTrasladoDetalle(TrasladoDetalleDTO td, TrasladoDTO t) {
        TrasladoDetalle trasladoDetalle = trasladoDetalleFacade.find(td.getIdTrasladoDetalle());

        if (trasladoDetalle != null && trasladoDetalle.getIdTrasladoDetalle() != null && trasladoDetalle.getIdTrasladoDetalle() != 0) {
            trasladoDetalle.setCantidad(td.getCantidad());

            try {
                trasladoDetalleFacade.edit(trasladoDetalle);
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al modificar el detalle del traslado entre almacenes. Error [{0}]", e.getMessage());
                return false;
            }
        }
        return false;
    }

    private TrasladoUbicaciones agregarRegistroUbicacion(Integer idTrasladoDetalle) {
        TrasladoUbicaciones trasladoUbicacion = new TrasladoUbicaciones();

        trasladoUbicacion.setCantidad(1);
        trasladoUbicacion.setFecha(new Date());
        trasladoUbicacion.setIdTrasladoDetalle(new TrasladoDetalle(idTrasladoDetalle));
        trasladoUbicacion.setUbicacionDestino((ubicacionDestino != null && !ubicacionDestino.isEmpty()) ? ubicacionDestino : ubicacionOrigen);
        trasladoUbicacion.setUbicacionOrigen(ubicacionOrigen);

        try {
            trasladoUbicacionesFacade.create(trasladoUbicacion);
            log.log(Level.INFO, "Se creo ubicacion para el detalle de traslado con id [{0}], para el traslado con id [{1}], con id ubicacion traslado [{2}]",
                    new Object[]{idTrasladoDetalle, traslado.getIdTraslado(), trasladoUbicacion.getIdTrasladoUbicacion()});
            return trasladoUbicacion;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al registrar la ubicacion, error [{0}]", e.getMessage());
            return null;
        }
    }

    private boolean actualizarTrasladoUbicacion(TrasladoUbicacionesDTO tu) {
        TrasladoUbicaciones trasladoUbicacion = trasladoUbicacionesFacade.find(tu.getIdTrasladoUbicacion());

        if (trasladoUbicacion != null && trasladoUbicacion.getIdTrasladoUbicacion() != null && trasladoUbicacion.getIdTrasladoUbicacion() != 0) {
            trasladoUbicacion.setCantidad(tu.getCantidad());

            try {
                trasladoUbicacionesFacade.edit(trasladoUbicacion);
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al modificar la ubicacion del traslado entre almacenes. Error [{0}]", e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void limpiarBasico() {
        referencia = null;
        factura = null;
        comentarioLinea = null;
        dlgEliminar = false;
        dlgFactura = false;
        dlgComentarioLinea = false;
    }

    public void limpiar() {
        referencia = null;
        almacenDestino = null;
        almacenOrigen = null;
        ubicacionOrigen = "TM";
        ubicacionDestino = "TM";
        comentario = null;
        comentarioLinea = null;
        dlgEliminar = false;
        ubicacionOrigenValida = false;
        ubicacionDestinoValida = false;
        dlgComentarioLinea = false;
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
    }

    public void crearTrasladoSAP() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO_CAMIÓN, Acciones.CREAR)) {
            if (traslado != null && traslado.getIdTraslado() != null && traslado.getIdTraslado() != null && traslado.getEstado().equals("TP")) {
                if (comentario == null || comentario.isEmpty()) {
                    mostrarMensaje("Error", "Debe ingresar un comentario para poder crear el traslado.", true, false, false);
                    log.log(Level.SEVERE, "Debe ingresar un comentario para poder crear el traslado");
                    return;
                }

                SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(sessionMBean.getUsuario());
                if (sesionSAPDTO == null) {
                    log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
                    mostrarMensaje("Error", "No fue posible iniciar una sesion en SAP B1WS.", true, false, false);
                    return;
                }

                /*Se agrupan los detalles por almacenes*/
                Map<String, List<TrasladoDetalleDTO>> mapa = new HashMap<>();
                for (TrasladoDetalleDTO d : trasladoDetalles) {
                    if (mapa.containsKey(d.getAlmacenOrigen())) {
                        mapa.get(d.getAlmacenOrigen()).add(d);
                    } else {
                        List<TrasladoDetalleDTO> det = new ArrayList<>();
                        det.add(d);

                        mapa.put(d.getAlmacenOrigen(), det);
                    }
                }

                String documentos = "";
                for (Map.Entry<String, List<TrasladoDetalleDTO>> tras : mapa.entrySet()) {
                    //Se crea el encabezado del traslado
                    StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

                    transfer.setCardCode(traslado.getCedulaUsuario());
                    transfer.setComments("Traslado entre almacenes - WebService 360********** " + comentario.toUpperCase());
                    transfer.setSalesPersonCode(Long.parseLong(sessionMBean.getCodigoVentas()));
                    transfer.setSeries(tras.toString().contains("DM") ? 193L : 27L);
                    transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());

                    //Se empieza a recorrer el detalle y las ubicaciones
                    if (tras.getValue() != null && !tras.getValue().isEmpty()) {
                        int line = 0;
                        for (TrasladoDetalleDTO detail : tras.getValue()) {
                            StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

                            detailLine.setFromWarehouseCode(detail.getAlmacenOrigen());
                            detailLine.setItemCode(detail.getReferencia());
                            detailLine.setLineNum(line);
                            detailLine.setQuantity(detail.getCantidad().doubleValue());
                            detailLine.setWarehouseCode(detail.getAlmacenDestino());
                            detailLine.setuComments(detail.getComentario());
                            if (detail.getDocumentoReferencia() != null && detail.getDocumentoReferencia() != 0 && !detail.getAlmacenOrigen().contains("DM")) {
                                detailLine.setuNWRBase(detail.getDocumentoReferencia().toString());
                            }
                            detailLine.setBinAllocations(new ArrayList<StockTransferDocumentBinAllocationDTO>());

                            if (transfer.getFromWarehouse() == null || transfer.getFromWarehouse().isEmpty()) {
                                transfer.setFromWarehouse(detail.getAlmacenOrigen());
                            }
                            if (transfer.getToWarehouse() == null || transfer.getToWarehouse().isEmpty()) {
                                transfer.setToWarehouse(detail.getAlmacenDestino());
                            }

                            if (detail.getTrasladoUbicaciones() != null && !detail.getTrasladoUbicaciones().isEmpty()) {
                                for (TrasladoUbicacionesDTO allocation : detail.getTrasladoUbicaciones()) {
                                    StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();
                                    StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

                                    if (ubicacionSAPFacade.validarUbicacionesAlmacen(detail.getAlmacenOrigen())) {
                                        allocationLineFrom.setAllowNegativeQuantity(false);
                                        allocationLineFrom.setBaseLineNumber(line);
                                        allocationLineFrom.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(detail.getAlmacenOrigen() + allocation.getUbicacionOrigen()).getAbsEntry().longValue());
                                        allocationLineFrom.setBinActionType("salida");
                                        allocationLineFrom.setQuantity(allocation.getCantidad().doubleValue());

                                        detailLine.addBinAllocation(allocationLineFrom);
                                    }

                                    if (ubicacionSAPFacade.validarUbicacionesAlmacen(detail.getAlmacenDestino())) {
                                        allocationLineTo.setAllowNegativeQuantity(false);
                                        allocationLineTo.setBaseLineNumber(line);
                                        allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(detail.getAlmacenDestino() + allocation.getUbicacionDestino()).getAbsEntry().longValue());
                                        allocationLineTo.setBinActionType("entrada");
                                        allocationLineTo.setQuantity(allocation.getCantidad().doubleValue());

                                        detailLine.addBinAllocation(allocationLineTo);
                                    }
                                }
                            }

                            transfer.addLine(detailLine);
                        }
                    } else {
                        log.log(Level.SEVERE, "No se puede continuar debido a que no se encontraron datos para realizar el traslado");
                        mostrarMensaje("Error", "No se puede continuar debido a que no se encontraron datos para realizar el traslado", true, false, false);
                        return;
                    }

                    try {
                        StockTransferClient client = new StockTransferClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));

                        GenericRESTResponseDTO res = client.crearStockTransfer(transfer);

                        if (res.getEstado() == 1) {
                            Integer numeroTraslado = res.getValor();
                            Integer docNum = trasladosSAPFacade.find(numeroTraslado).getDocNum();

                            documentos += documentos.length() > 0 ? ", " + docNum : docNum;

                            if (imprimir) {
                                imprimir(trasladosSAPFacade.find(numeroTraslado).getDocNum());
                            }
                        } else {
                            log.log(Level.SEVERE, "No fue posible crear el traslado");
                            mostrarMensaje("Error", "No fue posible crear el traslado.", true, false, false);
                            return;
                        }
                    } catch (Exception ex) {
                        log.log(Level.SEVERE, "Ocurrio un error al crear el traslado entre almacenes. Error: [{0}]", ex.getMessage());
                        mostrarMensaje("Error", "Ocurrio un error al crear el traslado entre almacenes. " + ex.getMessage(), true, false, false);
                        return;
                    }
                }

                if (!documentos.isEmpty()) {
                    if (almacenDestino != null && almacenDestino.contains("990")) {
                        notificarTrasladoCCYGA(documentos);
                    }

                    Traslado t = trasladoFacade.find(traslado.getIdTraslado());

                    if (t != null && t.getIdTraslado() != null && t.getIdTraslado() != 0) {
                        t.setNumeroTraslado(documentos);
                        t.setEstado("TF");
                        try {
                            trasladoFacade.edit(t);
                            log.log(Level.INFO, "Se manda numero traslado [{0}], para el traslado con id [{1}]", new Object[]{documentos, traslado.getIdTraslado()});
                            mostrarMensaje("Éxito", "Traslado #" + documentos + ", creado correctamente", false, true, false);
                            limpiar();
                        } catch (Exception e) {
                            log.log(Level.SEVERE, e.getMessage());
                            return;
                        }
                    }
                }
            } else {
                log.log(Level.SEVERE, "No se encontraron datos para continuar. Verifique");
                mostrarMensaje("Error", "No se encontraron datos para continuar. Verifique", true, false, false);
                return;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    public void notificarTrasladoCCYGA(String numeroTraslado) {
        MailMessageDTO mail = new MailMessageDTO();
        mail.setFrom("Traslados Matisses <notificaciones@matisses.co>");
        mail.setSubject("Traslados Matisses CCYGA");
        mail.addToAddress(baruAplicationBean.getDestinatariosPlantillaEmail().get("notificacion_traslado").getTo().get(0));
        mail.addCcAddress(baruAplicationBean.getDestinatariosPlantillaEmail().get("notificacion_traslado").getCc().get(0));

        Map<String, String> params = new HashMap<>();
        params.put("almacen", "CCYGA");
        params.put("usuario", sessionMBean.getUsuario());
        params.put("trasladoSAP", numeroTraslado);
        params.put("almacenOrigen", almacenOrigen + " (" + genericMBean.obtenerNombreWebAlmacen(almacenOrigen) + ")");

        if (trasladoDetalles != null && !trasladoDetalles.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<table style=\"width: 40%; border-collapse: collapse\">");
            sb.append("<tr>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Imagen</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Referencia</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Cantidad</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Comentario</th>");
            sb.append("</tr>");

            for (TrasladoDetalleDTO t : trasladoDetalles) {
                sb.append("<tr>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\"><img style=\"width: 103px; height: 83px;\" src=\"");
                sb.append(imagenProductoMBean.obtenerUrlProducto(t.getReferencia(), true));
                sb.append("\"></img></td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                sb.append(genericMBean.convertirARefCorta(t.getReferencia()));
                sb.append("</td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                sb.append(t.getCantidad());
                sb.append("</td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD; text-align: justify\">");
                sb.append(t.getComentario());
                sb.append("</td>");
                sb.append("</tr>");
            }

            sb.append("</table>");
            params.put("tablaReferencias", sb.toString());
        } else {
            params.put("tablaReferencias", "");
        }

        try {
            emailSender.sendMail(mail, SendHTMLEmailMBean.MessageTemplate.notificacion_traslado, params, null);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible enviar el correo de notificacion para el traslado.", e);
        }
    }

    public void cancelarTraslado() {
        if (traslado != null && traslado.getIdTraslado() != null && traslado.getIdTraslado() != 0) {
            Traslado t = trasladoFacade.find(traslado.getIdTraslado());

            if (t != null && t.getIdTraslado() != null && t.getIdTraslado() != 0) {
                t.setEstado("TC");

                try {
                    trasladoFacade.edit(t);
                    log.log(Level.INFO, "Se marco el traslado con id [{0}], como cancelado", traslado.getIdTraslado());
                    limpiar();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al marcar el traslado como cancelado. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo cancelar el traslado. comuniquese con sistemas", true, false, false);
                    return;
                }
            }
        }
    }

    public void imprimir(Integer nroDoc) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(null);
        print.setCopias(1);
        print.setDocumento("traslado");
        print.setId(nroDoc);
        print.setImprimir(imprimir);
        print.setSucursal(sessionMBean.getAlmacen());
        print.setDocumentosRelacionados(null);

        PrintRepostClient client = new PrintRepostClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{"TRASLADO", e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
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
