package co.matisses.web.mbean.traslados;

import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentBinAllocationDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentLinesDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferServiceConnector;
import co.matisses.b1ws.client.stocktransfer.StockTransferServiceException;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.UbicacionSAP;
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
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.TrasladoDTO;
import co.matisses.web.dto.TrasladoDetalleDTO;
import co.matisses.web.dto.TrasladoUbicacionesDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "trasladosAAlmacenMBean")
public class TrasladosAAlmacenMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private BaruApplicationMBean baruApplicationMBean;
    private static final Logger log = Logger.getLogger(TrasladosAAlmacenMBean.class.getSimpleName());
    private final static String UBICACION_DESTINO = "TM";
    private Integer factura;
    private Integer cantidad;
    private String almacenDestino;
    private String ubicacionOrigen;
    private String parametro;
    private boolean dlgEliminar = false;
    private boolean dlgFactura = false;
    private boolean ubicacionOrigenValida = false;
    private TrasladoDTO traslado;
    private List<TrasladoDetalleDTO> trasladoDetalles;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private TrasladoFacade trasladoFacade;
    @EJB
    private TrasladoDetalleFacade trasladoDetalleFacade;
    @EJB
    private TrasladoUbicacionesFacade trasladoUbicacionesFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;

    public TrasladosAAlmacenMBean() {
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        validarAlmacenDestino();
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

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
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

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
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

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getUbicacionOrigen() {
        return ubicacionOrigen;
    }

    public void setUbicacionOrigen(String ubicacionOrigen) {
        this.ubicacionOrigen = ubicacionOrigen;
    }

    private boolean validarAlmacenDestino() {
        log.log(Level.INFO, "Verificar parametro almacen destino");
        almacenDestino = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacenDestino");
        log.log(Level.INFO, "Valor cargado [{0}]", almacenDestino);
        if (almacenDestino != null && !almacenDestino.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    private void verificarTrasladosPendientes() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario tiene traslados pendientes");

        Traslado t = trasladoFacade.obtenerTrasladoPendiente(sessionMBean.getUsuario(), "aAlmacen", sessionMBean.getAlmacen());

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
            almacenDestino = trasladoDetalles.get(0).getAlmacenDestino();
            validarUbicacionesAlmacenes();

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
                    log.log(Level.INFO, "Se marco el traslado con id [{0}], como NO FINALIZADO", t.getIdTraslado());
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
                List<TrasladoUbicacionesDTO> ubicaciones = new ArrayList<>();
                for (TrasladoUbicaciones ubi : ubis) {
                    ubicaciones.add(new TrasladoUbicacionesDTO(ubi.getIdTrasladoUbicacion(),
                            ubi.getIdTrasladoDetalle().getIdTrasladoDetalle(), ubi.getCantidad(),
                            ubi.getUbicacionOrigen(), ubi.getUbicacionDestino(), ubi.getFecha()));
                }
                return ubicaciones;
            }
        }
        return null;
    }

    private void validarUbicacionesAlmacenes() {
        if (ubicacionSAPFacade.validarUbicacionesAlmacen(sessionMBean.getAlmacen())) {
            UbicacionSAP location = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(sessionMBean.getAlmacen() + parametro);

            if (location != null && location.getAbsEntry() != null && location.getAbsEntry() != 0) {
                if (location.getDisabled() != null && !location.getDisabled().equals('Y')) {
                    ubicacionOrigenValida = true;
                    ubicacionOrigen = location.getBinCode().replace(sessionMBean.getAlmacen(), "");
                } else {
                    ubicacionOrigenValida = false;
                    ubicacionOrigen = null;
                }
            } else {
                ubicacionOrigenValida = false;
                ubicacionOrigen = null;
            }
        }
    }

    public void agregarMovimiento() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO, Acciones.CREAR)) {
            if (parametro == null || parametro.isEmpty()) {
                log.log(Level.SEVERE, "Se debe ingresar una ubicacion o referencia para poder continuar");
                mostrarMensaje("Error", "Se debe ingresar una ubicación o referencia para poder continuar", true, false, false);
                limpiarBasico();
                return;
            }

            if (parametro.length() < 20) {
                parametro = genericMBean.completarReferencia(parametro);

                if (parametro.length() < 20) {
                    validarUbicacionesAlmacenes();

                    if (!ubicacionOrigenValida) {
                        mostrarMensaje("Error", "La ubicación ingresada no se encuentra en el almacén que se inicio sesión, no existe o se encuentra deshabilitada.", true, false, false);
                        log.log(Level.SEVERE, "La ubicacion ingresada no se encuentra en el almacen que se inicio sesion, no existe o se encuentra deshabilitada");
                        return;
                    }
                    parametro = null;
                    log.log(Level.INFO, "Se ingreso la ubicacion [{0}], del almacen [{1}], para el traslado a almacen", new Object[]{ubicacionOrigen, sessionMBean.getAlmacen()});
                    return;
                }
            } else if (parametro.length() > 20) {
                log.log(Level.SEVERE, "Se debe ingresar una referencia de 20 caracteres para poder continuar");
                mostrarMensaje("Error", "Se debe ingresar una referencia de 20 caracteres para poder continuar", true, false, false);
                limpiarBasico();
                return;
            }

            if (ubicacionOrigen == null || ubicacionOrigen.isEmpty()) {
                mostrarMensaje("Error", "No se ha ingresado una ubicación para poder trasladar", true, false, false);
                log.log(Level.SEVERE, "No se ha ingresado una ubicacion para poder trasladar");
                return;
            }

            log.log(Level.INFO, "Procesando referencia [" + parametro + "]");
            if (sessionMBean.getAlmacen().contains("CL")) {
                if (factura == null || factura == 0) {
                    mostrarMensaje("Error", "Ingrese el número de la factura.", true, false, false);
                    log.log(Level.SEVERE, "Ingrese el numero de la factura");
                    dlgFactura = true;
                    return;
                }
            }

            if (ubicacionOrigenValida) {
                SaldoUbicacion saldo = saldoUbicacionFacade.obtenerSaldoXUbicacion(parametro, sessionMBean.getAlmacen() + ubicacionOrigen);

                if (saldo != null && saldo.getAbsEntry() != null && saldo.getAbsEntry() != 0) {
                    for (TrasladoDetalleDTO t : trasladoDetalles) {
                        if (t.getReferencia().equals(parametro) && t.getAlmacenOrigen().equals(sessionMBean.getAlmacen())) {
                            if (t.getTrasladoUbicaciones().get(0).getUbicacionOrigen().equals(ubicacionOrigen)) {
                                saldo.setOnHandQty(new BigDecimal(saldo.getOnHandQty().intValue() - t.getCantidad()));
                                break;
                            }
                        }
                    }
                    if (saldo.getOnHandQty().intValue() == 0) {
                        log.log(Level.SEVERE, "No se encontro slado disponible de la referencia [{0}] para mover", parametro);
                        mostrarMensaje("Error", "No se encontró saldo de la referencia " + parametro + ", para mover", true, false, false);
                        limpiarBasico();
                        return;
                    }
                } else {
                    log.log(Level.SEVERE, "No se encontro saldo de la referencia [{0}] en la ubicacion [{1}]", new Object[]{parametro, ubicacionOrigen});
                    mostrarMensaje("Error", "No se encontró saldo de la referencia " + parametro + ", en la ubicación " + ubicacionOrigen, true, false, false);
                    limpiarBasico();
                    return;
                }
            }

            agregarItemBD(new TrasladoDetalleDTO(null, null, 1, null, factura, parametro, sessionMBean.getAlmacen(), almacenDestino, null, new Date()));
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para agregar referencias en este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para agregar referencias en este modulo");
            limpiarBasico();
            return;
        }
    }

    private void agregarItemBD(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia [{0}], al traslado a almacen en BD", parametro);

        if (traslado == null || traslado.getIdTraslado() == null || traslado.getIdTraslado() == 0) {
            if (!crearTraslado()) {
                mostrarMensaje("Error", "no se pudo crear el traslado. comuniquese con el departamento de sistemas", true, false, false);
                log.log(Level.SEVERE, "no se pudo crear el traslado. comuniquese con el departamento de sistemas");
                limpiarBasico();
                return;
            }
        }

        if (trasladoDetalles != null && !trasladoDetalles.isEmpty()) {
            for (TrasladoDetalleDTO dto : trasladoDetalles) {
                if (dto.getReferencia().equals(td.getReferencia()) && dto.getIdTrasladoDetalle() != null && dto.getIdTrasladoDetalle() != 0
                        && (td.getDocumentoReferencia() == null || dto.getDocumentoReferencia().equals(td.getDocumentoReferencia()))) {
                    if (dto.getTrasladoUbicaciones().get(0).getUbicacionOrigen().equals(ubicacionOrigen)) {
                        td.setIdTrasladoDetalle(dto.getIdTrasladoDetalle());
                        td.setCantidad(dto.getCantidad() + 1);
                        break;
                    }
                }
            }
        }

        if (td.getIdTrasladoDetalle()
                != null && td.getIdTrasladoDetalle() != 0) {
            if (!actualizarTrasladoDetalle(td, traslado)) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado a almacen", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la referencia solicitada al traslado a almacen");
                limpiarBasico();
                return;
            } else {
                agregarMovimiento(td);
            }
        } else {
            td.setIdTrasladoDetalle(agregarTrasladoDetalle(td, traslado));
            if (td.getIdTrasladoDetalle() == null || td.getIdTrasladoDetalle() == 0) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado a almacen", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la referencia solicitada al traslado a almacen");
                limpiarBasico();
                return;
            } else {
                agregarMovimiento(td);
            }
        }
    }

    private void agregarMovimiento(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia para traslado a almacen");
        boolean existe = false;
        if (trasladoDetalles.isEmpty()) {
            existe = false;
        } else {
            for (TrasladoDetalleDTO dto : trasladoDetalles) {
                if (dto.getReferencia().equals(td.getReferencia()) && ((td.getDocumentoReferencia() == null || dto.getDocumentoReferencia() == null)
                        || (td.getDocumentoReferencia().equals(dto.getDocumentoReferencia())))) {
                    if (dto.getTrasladoUbicaciones().get(0).getUbicacionOrigen().equals(ubicacionOrigen)) {
                        dto.setCantidad(td.getCantidad());
                        existe = true;

                        TrasladoUbicacionesDTO d = dto.getTrasladoUbicaciones().get(0);
                        d.setCantidad(d.getCantidad() + 1);
                        actualizarTrasladoUbicacion(d);
                        break;
                    } else {
                        existe = false;
                    }
                } else {
                    existe = false;
                }
            }
        }
        if (!existe) {
            trasladoDetalles.add(0, td);

            if (traslado.getTrasladoDetalles() == null) {
                traslado.setTrasladoDetalles(new ArrayList<TrasladoDetalleDTO>());
            }

            TrasladoUbicaciones tu = agregarRegistroUbicacion(td.getIdTrasladoDetalle());

            if (tu != null && tu.getIdTrasladoUbicacion() != null && tu.getIdTrasladoUbicacion() != 0) {
                if (trasladoDetalles.get(0).getTrasladoUbicaciones() == null) {
                    trasladoDetalles.get(0).setTrasladoUbicaciones(new ArrayList<TrasladoUbicacionesDTO>());
                }

                trasladoDetalles.get(0).addTrasladoUbicacion(new TrasladoUbicacionesDTO(tu.getIdTrasladoUbicacion(),
                        tu.getIdTrasladoDetalle().getIdTrasladoDetalle(), tu.getCantidad(), tu.getUbicacionOrigen(),
                        tu.getUbicacionDestino(), tu.getFecha()));
            }

        }
        limpiarBasico();
    }

    public void seleccionarReferenciaEliminar() {
        if (sessionMBean.validarPermisoUsuario(Objetos.REFERENCIA_TRASLADO, Acciones.ELIMINAR)) {
            String fac = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("factura");
            String ref = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
            String ubi = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacionOrigen");

            if (fac != null && !fac.isEmpty()) {
                factura = Integer.parseInt(fac);
            }
            if (ref != null && !ref.isEmpty()) {
                parametro = ref;
                ubicacionOrigen = ubi;
                dlgEliminar = true;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para eliminar referencias en este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para eliminar referencias en este modulo");
            limpiarBasico();
            return;
        }
    }

    public void quitarReferencia() {
        if (sessionMBean.validarPermisoUsuario(Objetos.REFERENCIA_TRASLADO, Acciones.ELIMINAR)) {
            if (parametro != null && !parametro.isEmpty()) {
                for (TrasladoDetalleDTO d : trasladoDetalles) {
                    if (d.getReferencia().equals(parametro) && (factura == null || d.getDocumentoReferencia().equals(factura))) {
                        if (d.getTrasladoUbicaciones().get(0).getCantidad() > 1 && d.getTrasladoUbicaciones().get(0).getUbicacionOrigen().equals(ubicacionOrigen)) {
                            d.getTrasladoUbicaciones().get(0).setCantidad(d.getTrasladoUbicaciones().get(0).getCantidad() - 1);
                            if (actualizarTrasladoUbicacion(d.getTrasladoUbicaciones().get(0))) {
                                log.log(Level.INFO, "Se elimino una unidad de la referencia [{0}], para el detalle de traslado con id [{1}]",
                                        new Object[]{parametro, d.getIdTrasladoDetalle()});
                            } else {
                                log.log(Level.SEVERE, "Ocurrio un error al eliminar una unidad del detalle con id [{0}], comuniquese con sistemas", d.getIdTrasladoDetalle());
                                mostrarMensaje("Error", "Ocurrio un error al eliminar una unidad del detalle, comuniquese con sistemas", true, false, false);
                                limpiarBasico();
                                return;
                            }

                            d.setCantidad(d.getCantidad() - 1);
                            if (actualizarTrasladoDetalle(d, traslado)) {
                                log.log(Level.INFO, "Se elimino una unidad de la referencia [{0}], para el traslado con id [{1}]",
                                        new Object[]{parametro, traslado.getIdTraslado()});
                            } else {
                                log.log(Level.SEVERE, "Ocurrio un error al eliminar una unidad del traslado con id [{0}], comuniquese con sistemas", traslado.getIdTraslado());
                                mostrarMensaje("Error", "Ocurrio un error al eliminar una unidad del traslado, comuniquese con sistemas", true, false, false);
                                limpiarBasico();
                                return;
                            }
                        } else if (d.getTrasladoUbicaciones().get(0).getUbicacionOrigen().equals(ubicacionOrigen)) {
                            //Como solo se encontro una unidad en ubicaciones de la parametro, se eliminan los registros de ubicacion y detalle
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
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para eliminar referencias en este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para eliminar referencias en este modulo");
            limpiarBasico();
            return;
        }
        limpiarBasico();
        ubicacionOrigen = null;
    }

    private boolean crearTraslado() {
        Traslado t = new Traslado();

        t.setCedulaUsuario(sessionMBean.getCedulaEmpleado() + "PR");
        t.setComentario("Traslado desde 360");
        t.setEstado("TP");
        t.setFecha(new Date());
        t.setSerie(27);
        t.setTipoMovimiento("aAlmacen");
        t.setUsuario(sessionMBean.getUsuario());
        t.setSucursal(sessionMBean.getAlmacen());

        try {
            trasladoFacade.create(t);
            log.log(Level.INFO, "Se creo traslado [a almacen] en web con id [{0}]", t.getIdTraslado());
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
        trasladoDetalle.setComentario("");
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
                log.log(Level.SEVERE, "Error al modificar el detalle del traslado a almacen. Error [{0}]", e.getMessage());
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
        trasladoUbicacion.setUbicacionDestino(UBICACION_DESTINO);
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
                log.log(Level.SEVERE, "Error al modificar la ubicacion del traslado a almacen. Error [{0}]", e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void limpiarBasico() {
        parametro = null;
        factura = null;
        dlgEliminar = false;
        dlgFactura = false;
        cantidad = null;
    }

    public void limpiar() {
        parametro = null;
        almacenDestino = null;
        initialize();
        ubicacionOrigen = null;
        dlgEliminar = false;
        ubicacionOrigenValida = false;
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
    }

    public void crearTrasladoSAP() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO, Acciones.CREAR)) {
            if (traslado != null && traslado.getIdTraslado() != null && traslado.getIdTraslado() != null && traslado.getEstado().equals("TP")) {
                SesionSAPB1WSDTO sesionSAPDTO = baruApplicationMBean.obtenerSesionSAP(sessionMBean.getUsuario());
                if (sesionSAPDTO == null) {
                    log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
                    mostrarMensaje("Error", "No fue posible iniciar una sesion en SAP B1WS.", true, false, false);
                    return;
                }
                //Se crea el encabezado del traslado
                StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

                transfer.setCardCode(traslado.getCedulaUsuario());
                transfer.setComments("Traslado a almacen - WebService 360");
                transfer.setSalesPersonCode(Long.parseLong(sessionMBean.getCodigoVentas()));
                transfer.setSeries(traslado.getSerie().longValue());
                transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());

                //Se empieza a recorrer el detalle y las ubicaciones
                if (trasladoDetalles != null && !trasladoDetalles.isEmpty()) {
                    int line = 0;
                    for (TrasladoDetalleDTO detail : trasladoDetalles) {
                        StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

                        detailLine.setFromWarehouseCode(detail.getAlmacenOrigen());
                        detailLine.setItemCode(detail.getReferencia());
                        detailLine.setLineNum(line);
                        detailLine.setQuantity(detail.getCantidad().doubleValue());
                        detailLine.setWarehouseCode(detail.getAlmacenDestino());
                        detailLine.setuComments("");
                        if (detail.getDocumentoReferencia() != null && detail.getDocumentoReferencia() != 0) {
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

                                allocationLineFrom.setAllowNegativeQuantity(false);
                                allocationLineFrom.setBaseLineNumber(line);
                                allocationLineFrom.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(detail.getAlmacenOrigen() + allocation.getUbicacionOrigen()).getAbsEntry().longValue());
                                allocationLineFrom.setBinActionType("salida");
                                allocationLineFrom.setQuantity(allocation.getCantidad().doubleValue());

                                detailLine.addBinAllocation(allocationLineFrom);

                                allocationLineTo.setAllowNegativeQuantity(false);
                                allocationLineTo.setBaseLineNumber(line);
                                allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(detail.getAlmacenDestino() + allocation.getUbicacionDestino()).getAbsEntry().longValue());
                                allocationLineTo.setBinActionType("entrada");
                                allocationLineTo.setQuantity(allocation.getCantidad().doubleValue());

                                detailLine.addBinAllocation(allocationLineTo);
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
                    StockTransferServiceConnector connection = sapB1WSBean.getStockTransferServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
                    Integer numeroTraslado = connection.createStockTransferDocument(transfer);

                    if (numeroTraslado != null && numeroTraslado != 0) {
                        Traslado t = trasladoFacade.find(traslado.getIdTraslado());

                        if (t != null && t.getIdTraslado() != null && t.getIdTraslado() != 0) {
                            Integer docNum = trasladosSAPFacade.find(numeroTraslado).getDocNum();

                            t.setNumeroTraslado(docNum.toString());
                            t.setEstado("TF");

                            try {
                                trasladoFacade.edit(t);
                                log.log(Level.INFO, "Se manda numero traslado [{0}], para el traslado con id [{1}]", new Object[]{docNum, traslado.getIdTraslado()});
                                mostrarMensaje("Éxito", "Traslado #" + docNum + ", creado correctamente", false, true, false);
                                limpiar();
                            } catch (Exception e) {
                                log.log(Level.SEVERE, e.getMessage());
                                return;
                            }
                        }
                    }
                } catch (StockTransferServiceException ex) {
                    log.log(Level.SEVERE, "Ocurrio un error al crear el traslado a almacen. Error: [{0}]", ex.getMessage());
                    mostrarMensaje("Error", "Ocurrio un error al crear el traslado a almacén. " + ex.getMessage(), true, false, false);
                    return;
                }
            } else {
                log.log(Level.SEVERE, "No se encontraron datos para continuar. Verifique");
                mostrarMensaje("Error", "No se encontraron datos para continuar. Verifique", true, false, false);
                return;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para crear traslados en este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para crear traslados en este modulo");
            return;
        }
    }

    public void cancelarTraslado() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO, Acciones.CANCELAR)) {
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
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para cancelar traslados en este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para cancelar traslados en este modulo");
            return;
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
