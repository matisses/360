package co.matisses.web.mbean.ubicaciones;

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
@Named(value = "cargueUbicacionMBean")
public class CargueUbicacionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger log = Logger.getLogger(CargueUbicacionMBean.class.getSimpleName());
    private Integer factura;
    private final String ubicacionOrigen = "TM";
    private String ubicacionDestino;
    private String referencia;
    private boolean dlgFactura = false;
    private boolean dlgEliminar = false;
    private boolean ubicacionValida = false;
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

    public CargueUbicacionMBean() {
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
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

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
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

    public boolean isUbicacionValida() {
        return ubicacionValida;
    }

    public void setUbicacionValida(boolean ubicacionValida) {
        this.ubicacionValida = ubicacionValida;
    }

    public boolean isDlgFactura() {
        return dlgFactura;
    }

    public void setDlgFactura(boolean dlgFactura) {
        this.dlgFactura = dlgFactura;
    }

    public boolean isDlgEliminar() {
        return dlgEliminar;
    }

    public void setDlgEliminar(boolean dlgEliminar) {
        this.dlgEliminar = dlgEliminar;
    }

    private void verificarTrasladosPendientes() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario tiene traslados pendientes");

        Traslado t = trasladoFacade.obtenerTrasladoPendiente(sessionMBean.getUsuario(), "cargue", sessionMBean.getAlmacen());

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

        if (trasladoDetalles != null && trasladoDetalles.get(0).getAlmacenOrigen().equals(sessionMBean.getAlmacen())) {
            if (trasladoDetalles.get(0).getTrasladoUbicaciones() != null && !trasladoDetalles.get(0).getTrasladoUbicaciones().isEmpty()) {
                ubicacionDestino = trasladoDetalles.get(0).getTrasladoUbicaciones().get(0).getUbicacionDestino();
                validarDatosUbicacion();
            }
        } else {
            log.log(Level.INFO, "Se encontro traslado pendiente para el usuario, pero no del almacen en el que se encuentra logeado");
            limpiar();
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

    public void validarDatosUbicacion() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.CARGAR)) {
            ubicacionValida = false;
            boolean tieneUbicaciones = ubicacionSAPFacade.validarUbicacionesAlmacen(sessionMBean.getAlmacen());
            if (tieneUbicaciones) {
                UbicacionSAP location = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(sessionMBean.getAlmacen() + ubicacionDestino);

                if (location != null && location.getAbsEntry() != null && location.getAbsEntry() != 0) {
                    if (location.getDisabled() != null && !location.getDisabled().equals('Y')) {
                        ubicacionValida = true;
                    } else {
                        mostrarMensaje("Error", "La ubicación ingresada se encuentra inactiva", true, false, false);
                        log.log(Level.SEVERE, "La ubicacion ingresada se encuentra inactiva");
                        return;
                    }
                } else {
                    mostrarMensaje("Error", "La ubicación ingresada no existe para el almacén", true, false, false);
                    log.log(Level.SEVERE, "La ubicacion ingresada no existe para el almacen");
                    return;
                }
            } else {
                log.log(Level.SEVERE, "No se encontraron ubicaciones para el almacen");
                mostrarMensaje("Error", "No se encontraron ubicaciones para el almacén", true, false, false);
                return;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    public void agregarReferencia() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.CARGAR)) {
            if (ubicacionValida) {
                if (ubicacionDestino == null || ubicacionDestino.isEmpty()) {
                    log.log(Level.SEVERE, "Se debe ingresar una ubicacion para continuar");
                    mostrarMensaje("Error", "Se debe ingresar una ubicación para continuar", true, false, false);
                    limpiarBasico();
                    return;
                }
                if (referencia == null || referencia.isEmpty()) {
                    log.log(Level.SEVERE, "Se debe ingresar una referencia para poder continuar");
                    mostrarMensaje("Error", "Se debe ingresar una referencia para poder continuar", true, false, false);
                    limpiarBasico();
                    return;
                }
                if (referencia.length() < 20) {
                    if (sessionMBean.validarPermisoUsuario(Objetos.REFERENCIA_CORTA, Acciones.USAR)) {
                        referencia = genericMBean.completarReferencia(referencia);
                    } else {
                        mostrarMensaje("Error", "Debe ingresar una referencia de 20 caracteres, para poder agregar.", true, false, false);
                        log.log(Level.SEVERE, "Debe ingresar una referencia de 20 caracteres, para poder agregar");
                        return;
                    }
                } else if (referencia.length() > 20) {
                    log.log(Level.SEVERE, "Se debe ingresar una referencia de 20 caracteres para poder continuar");
                    mostrarMensaje("Error", "Se debe ingresar una referencia de 20 caracteres para poder continuar", true, false, false);
                    limpiarBasico();
                    return;
                }

                log.log(Level.INFO, "Procesando referencia [{0}]", referencia);
                if (sessionMBean.getAlmacen().contains("CL")) {
                    if (factura == null || factura == 0) {
                        mostrarMensaje("Error", "Ingrese el número de la factura.", true, false, false);
                        log.log(Level.SEVERE, "Ingrese el numero de la factura");
                        dlgFactura = true;
                        return;
                    }
                }
                SaldoUbicacion saldo = saldoUbicacionFacade.obtenerSaldoXUbicacion(referencia, sessionMBean.getAlmacen() + ubicacionOrigen);

                if (saldo != null && saldo.getAbsEntry() != null && saldo.getAbsEntry() != 0) {
                    for (TrasladoDetalleDTO t : trasladoDetalles) {
                        if (t.getReferencia().equals(referencia) && t.getAlmacenOrigen().equals(sessionMBean.getAlmacen())) {
                            saldo.setOnHandQty(new BigDecimal(saldo.getOnHandQty().intValue() - t.getCantidad()));
                        }
                    }
                    if (saldo.getOnHandQty().intValue() == 0) {
                        log.log(Level.SEVERE, "No se encontro slado disponible de la referencia [{0}] para cargar", referencia);
                        mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", para cargar", true, false, false);
                        limpiarBasico();
                        return;
                    }
                } else {
                    log.log(Level.SEVERE, "No se encontro saldo de la referencia [{0}] en la ubicacion [TM]");
                    mostrarMensaje("Error", "No se encontró saldo de la referencia " + referencia + ", en la ubicación TM", true, false, false);
                    limpiarBasico();
                    return;
                }
                agregarItemBD(new TrasladoDetalleDTO(null, null, 1, null, factura, referencia, sessionMBean.getAlmacen(), sessionMBean.getAlmacen(), null, new Date()));
            } else if (!ubicacionValida) {
                log.log(Level.SEVERE, "La ubicacion [{0}], no se encuentra activa, no existe o el almacen no posee ubicaciones. Valide la informacion y continue.", ubicacionOrigen);
                mostrarMensaje("Error", "La ubicación " + ubicacionOrigen + ", no se encuentra activa, no existe o el almacén no posee ubicaciones. Valide la información y continue.",
                        true, false, false);
                limpiarBasico();
                return;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            limpiarBasico();
            return;
        }
    }

    private void agregarItemBD(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia [{0}], al traslado de ubicacion cargue en BD", referencia);

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
                if (dto.getReferencia().equals(td.getReferencia()) && dto.getIdTrasladoDetalle() != null && dto.getIdTrasladoDetalle() != 0) {
                    if (td.getDocumentoReferencia() != null && dto.getDocumentoReferencia() != null) {
                        if (dto.getDocumentoReferencia().equals(td.getDocumentoReferencia())) {
                            td.setIdTrasladoDetalle(dto.getIdTrasladoDetalle());
                            td.setCantidad(dto.getCantidad() + 1);
                            break;
                        }
                    } else {
                        td.setIdTrasladoDetalle(dto.getIdTrasladoDetalle());
                        td.setCantidad(dto.getCantidad() + 1);
                        break;
                    }
                }
            }
        }

        if (td.getIdTrasladoDetalle() != null && td.getIdTrasladoDetalle() != 0) {
            if (!actualizarTrasladoDetalle(td, traslado)) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado cargue ubicacion", true, false, false);
                limpiarBasico();
                return;
            } else {
                agregarMovimiento(td);
            }
        } else {
            td.setIdTrasladoDetalle(agregarTrasladoDetalle(td, traslado));
            if (td.getIdTrasladoDetalle() == null || td.getIdTrasladoDetalle() == 0) {
                mostrarMensaje("Error", "No se pudo agregar la referencia solicitada al traslado cargue ubicacion", true, false, false);
                log.log(Level.SEVERE, "No se pudo agregar la referencia solicitada al traslado cargue ubicacion");
                limpiarBasico();
                return;
            } else {
                agregarMovimiento(td);
            }
        }
    }

    private void agregarMovimiento(TrasladoDetalleDTO td) {
        log.log(Level.INFO, "Agregando referencia para traslado de ubicacion");
        boolean existe = false;
        if (trasladoDetalles.isEmpty()) {
            existe = false;
        } else {
            for (TrasladoDetalleDTO dto : trasladoDetalles) {
                if (dto.getReferencia().equals(td.getReferencia()) && ((td.getDocumentoReferencia() == null || dto.getDocumentoReferencia() == null)
                        || (td.getDocumentoReferencia().equals(dto.getDocumentoReferencia())))) {
                    dto.setCantidad(td.getCantidad());
                    existe = true;

                    for (TrasladoUbicacionesDTO d : dto.getTrasladoUbicaciones()) {
                        if (d.getUbicacionDestino().equals(ubicacionDestino) && d.getUbicacionOrigen().equals(ubicacionOrigen)) {
                            d.setCantidad(d.getCantidad() + 1);
                            actualizarTrasladoUbicacion(d);
                            break;
                        }
                    }
                    break;
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
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.CARGAR)) {
            String datosTraslado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("traslado");
            if (datosTraslado != null && !datosTraslado.isEmpty()) {
                String[] s = datosTraslado.split("-");
                if (s.length > 1) {
                    referencia = s[0];
                    factura = Integer.parseInt(s[1]);
                } else if (s.length == 1) {
                    referencia = s[0];
                }
                dlgEliminar = true;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    public void quitarReferencia() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.TRANSFERIR)) {
            if (referencia != null && !referencia.isEmpty()) {
                for (TrasladoDetalleDTO d : trasladoDetalles) {
                    if (d.getReferencia().equals(referencia)
                            && ((factura == null && d.getDocumentoReferencia() == null) || d.getDocumentoReferencia().equals(factura))) {
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
        t.setComentario("Traslado desde 360");
        t.setEstado("TP");
        t.setFecha(new Date());
        t.setSerie(205);
        t.setTipoMovimiento("cargue");
        t.setUsuario(sessionMBean.getUsuario());
        t.setSucursal(sessionMBean.getAlmacen());

        try {
            trasladoFacade.create(t);
            log.log(Level.INFO, "Se creo traslado [cargue] en web con id [{0}]", t.getIdTraslado());
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
                log.log(Level.SEVERE, "Error al modificar el detalle del traslado cargue ubicacion. Error [{0}]", e.getMessage());
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
        trasladoUbicacion.setUbicacionDestino(ubicacionDestino);
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
                log.log(Level.SEVERE, "Error al modificar la ubicacion del traslado cargue ubicacion. Error [{0}]", e.getMessage());
                return false;
            }
        }
        return false;
    }

    public void limpiarBasico() {
        referencia = null;
        factura = null;
        dlgEliminar = false;
        dlgFactura = false;
    }

    public void limpiar() {
        factura = null;
        ubicacionDestino = null;
        referencia = null;
        dlgFactura = false;
        dlgEliminar = false;
        ubicacionValida = false;
        traslado = new TrasladoDTO();
        trasladoDetalles = new ArrayList<>();
    }

    public void crearTrasladoSAP() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.CARGAR)) {
            if (traslado != null && traslado.getIdTraslado() != null && traslado.getIdTraslado() != null && traslado.getEstado().equals("TP")) {
                SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(sessionMBean.getUsuario());
                if (sesionSAPDTO == null) {
                    log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
                    mostrarMensaje("Error", "No fue posible iniciar una sesion en SAP B1WS.", true, false, false);
                    return;
                }
                //Se crea el encabezado del traslado
                StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

                transfer.setCardCode(traslado.getCedulaUsuario());
                transfer.setComments("Traslado cargue ubicación - WebService 360");
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
                        } else {
                            log.log(Level.SEVERE, "No se puede continuar debido a que no se encontraron datos para realizar el traslado");
                            mostrarMensaje("Error", "No se puede continuar debido a que no se encontraron datos para realizar el traslado", true, false, false);
                            return;
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
                    log.log(Level.SEVERE, "Ocurrio un error al crear el traslado cargue ubicacion. Error: [{0}]", ex.getMessage());
                    mostrarMensaje("Error", "Ocurrio un error al crear el traslado cargue ubicación. " + ex.getMessage(), true, false, false);
                    return;
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
