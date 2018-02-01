package co.matisses.web.mbean.sticker;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.DetalleImpresionSticker;
import co.matisses.persistence.web.entity.ImpresionSticker;
import co.matisses.persistence.web.entity.Impresora;
import co.matisses.persistence.web.entity.TipoSticker;
import co.matisses.persistence.web.facade.DetalleImpresionStickerFacade;
import co.matisses.persistence.web.facade.ImpresionStickerFacade;
import co.matisses.persistence.web.facade.ImpresoraFacade;
import co.matisses.persistence.web.facade.TipoStickerFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.ZebraPrintClient;
import co.matisses.web.dto.CodigoRevisadoLabelDTO;
import co.matisses.web.dto.DetalleImpresionStickerDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.ImpresionStickerDTO;
import co.matisses.web.dto.ImpresoraDTO;
import co.matisses.web.dto.ItemLabelDTO;
import co.matisses.web.dto.TipoStickerDTO;
import co.matisses.web.dto.ZebraPrintDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "impresionStickerMBean")
public class ImpresionStickerMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(ImpresionStickerMBean.class.getSimpleName());
    private Integer cantidad;
    private Integer idTipoSticker;
    private Integer idTipoStickerTmp;
    private Integer idImpresora;
    private String referencia;
    private boolean dlgEliminar = false;
    private boolean usarEan = false;
    private boolean permitirTrabajo = true;
    private boolean dlgCambiarTipoSticker = false;
    private boolean dlgImpresoras = false;
    private boolean codigoRevisado = false;
    private ImpresionStickerDTO impresionSticker;
    private List<TipoStickerDTO> tiposSticker;
    private List<DetalleImpresionStickerDTO> detalleImpresion;
    private List<ImpresoraDTO> impresoras;
    @EJB
    private TipoStickerFacade tipoStickerFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private ImpresoraFacade impresoraFacade;
    @EJB
    private ImpresionStickerFacade impresionStickerFacade;
    @EJB
    private DetalleImpresionStickerFacade detalleImpresionStickerFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;

    public ImpresionStickerMBean() {
        genericMBean = new BaruGenericMBean();
        impresionSticker = new ImpresionStickerDTO();
        tiposSticker = new ArrayList<>();
        detalleImpresion = new ArrayList<>();
        impresoras = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        if (sessionMBean.validarPermisoUsuario(Objetos.PEDIDO_STICKER, Acciones.CREAR)) {
            obtenerTiposSticker();
            validarImpresoras();
            verificarSolicitudesStickerPendientes();
        } else {
            mostrarMensaje("Error", "El usuario no tiene autorización para usar este modulo.", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene autorizacion para usar este modulo");
            return;
        }
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getIdTipoSticker() {
        return idTipoSticker;
    }

    public void setIdTipoSticker(Integer idTipoSticker) {
        this.idTipoSticker = idTipoSticker;
    }

    public Integer getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(Integer idImpresora) {
        this.idImpresora = idImpresora;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isDlgEliminar() {
        return dlgEliminar;
    }

    public void setDlgEliminar(boolean dlgEliminar) {
        this.dlgEliminar = dlgEliminar;
    }

    public boolean isUsarEan() {
        return usarEan;
    }

    public void setUsarEan(boolean usarEan) {
        this.usarEan = usarEan;
    }

    public boolean isDlgCambiarTipoSticker() {
        return dlgCambiarTipoSticker;
    }

    public void setDlgCambiarTipoSticker(boolean dlgCambiarTipoSticker) {
        this.dlgCambiarTipoSticker = dlgCambiarTipoSticker;
    }

    public boolean isDlgImpresoras() {
        return dlgImpresoras;
    }

    public void setDlgImpresoras(boolean dlgImpresoras) {
        this.dlgImpresoras = dlgImpresoras;
    }

    public boolean isCodigoRevisado() {
        return codigoRevisado;
    }

    public void setCodigoRevisado(boolean codigoRevisado) {
        this.codigoRevisado = codigoRevisado;
    }

    public List<TipoStickerDTO> getTiposSticker() {
        return tiposSticker;
    }

    public void setTiposSticker(List<TipoStickerDTO> tiposSticker) {
        this.tiposSticker = tiposSticker;
    }

    public List<DetalleImpresionStickerDTO> getDetalleImpresion() {
        return detalleImpresion;
    }

    public void setDetalleImpresion(List<DetalleImpresionStickerDTO> detalleImpresion) {
        this.detalleImpresion = detalleImpresion;
    }

    public List<ImpresoraDTO> getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(List<ImpresoraDTO> impresoras) {
        this.impresoras = impresoras;
    }

    private void validarImpresoras() {
        impresoras.clear();
        Impresora printers = impresoraFacade.obtenerImpresoraSucursal(sessionMBean.getAlmacen(), "ETQ");

        if (printers != null && printers.getIdImpresora() != null && printers.getIdImpresora() != 0) {
            //for (Impresora i : printers) {
            impresoras.add(new ImpresoraDTO(printers.getIdImpresora(), printers.getNombreImpresora(), printers.getSucursal(), printers.getEstadoEjecucion(),
                    printers.getCodigoImpresion(), printers.getNombreImpresoraServidor(), printers.getActivo()));
            //}

            /*Collections.sort(impresoras, new Comparator<ImpresoraDTO>() {
                @Override
                public int compare(ImpresoraDTO t, ImpresoraDTO t1) {
                    return t.getNombreImpresora().compareTo(t1.getNombreImpresora());
                }
            });*/
            permitirTrabajo = true;
        } else {
            permitirTrabajo = false;
            mostrarMensaje("Error", "No se encontraron impresoras disponibles para esta sucursal.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron impresoras disponibles para esta sucursal");
            return;
        }
    }

    private void obtenerTiposSticker() {
        tiposSticker.clear();

        List<TipoSticker> tipos = tipoStickerFacade.obtenerTiposActivos();

        if (tipos != null && !tipos.isEmpty()) {
            for (TipoSticker t : tipos) {
                tiposSticker.add(new TipoStickerDTO(t.getIdTipoSticker(), t.getColumnas(), t.getIdTipoEtiqueta(), t.getNombreSticker(), t.getTipoAlmacen(), t.getActivo()));
            }
        } else {
            mostrarMensaje("Error", "No se encontraron tipos de sticker habilitados.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron tipos de sticker habilitados");
            return;
        }
    }

    private void verificarSolicitudesStickerPendientes() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario tiene solicitudes de impresion de sticker pendientes");

        ImpresionSticker i = impresionStickerFacade.obtenerSolicitudesStickerPendiente(sessionMBean.getUsuario(), sessionMBean.getAlmacen());

        if (i != null) {
            if (i.getIdTipoSticker().getNombreSticker().contains("CÓDIGO REVISADO")) {
                i.setEstado("NF");

                try {
                    impresionStickerFacade.edit(i);
                    log.log(Level.INFO, "Se marco la solicitud de impresion de sticker con id [{0}], como NO FINALIZADO", i.getIdImpresionSticker());
                    verificarSolicitudesStickerPendientes();
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            } else {
                llenarDetalleImpresionStickerRecuperado(convertirSolicitud(i), i);
            }
        } else {
            log.log(Level.INFO, "no se encontraron solicitudes de impresion de sticker pendientes para el usuario");
        }
    }

    private boolean convertirSolicitud(ImpresionSticker i) {
        if (i.getIdTipoSticker() != null) {
            impresionSticker = new ImpresionStickerDTO(i.getIdImpresionSticker(), i.getIdTipoSticker().getIdTipoSticker(), i.getUsuario(), i.getSucursal(), i.getEstado(), i.getFecha());
            idTipoSticker = i.getIdTipoSticker().getIdTipoSticker();
            return true;
        } else {
            log.log(Level.SEVERE, "No se encontraron solicitudes de impresion de sticker pendientes que cumpla con los requisitos");
            impresionSticker = new ImpresionStickerDTO();
            return false;
        }
    }

    private void llenarDetalleImpresionStickerRecuperado(boolean procesado, ImpresionSticker impresion) {
        if (procesado) {
            List<DetalleImpresionSticker> dts = detalleImpresionStickerFacade.obtenerDatosImpresionSticker(impresionSticker.getIdImpresionSticker());

            if (dts != null && !dts.isEmpty()) {
                for (DetalleImpresionSticker dt : dts) {
                    detalleImpresion.add(new DetalleImpresionStickerDTO(dt.getIdDetalleImpresionSticker(), dt.getIdImpresionSticker().getIdImpresionSticker(), dt.getCantidad(),
                            dt.getPrecio(), dt.getReferencia(), dt.getAlmacen(), dt.getNombreReferencia(), dt.getReferenciaProveedor(), dt.getFecha()));
                }
            } else {
                impresion.setEstado("NF");

                try {
                    impresionStickerFacade.edit(impresion);
                    log.log(Level.INFO, "Se marco la solicitud de impresion de sticker con id [{0}], como NO FINALIZADO", impresion.getIdImpresionSticker());
                    verificarSolicitudesStickerPendientes();
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    return;
                }
            }
        } else if (impresion != null && impresion.getIdImpresionSticker() != null && impresion.getIdImpresionSticker() != 0) {
            impresion.setEstado("NF");

            try {
                impresionStickerFacade.edit(impresion);
                log.log(Level.INFO, "Se marco la solicitud de impresion de sticker con id [{0}], como NO FINALIZADO", impresion.getIdImpresionSticker());
                verificarSolicitudesStickerPendientes();
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                return;
            }
        }
    }

    public void seleccionarTipoSticker() {
        if (permitirTrabajo) {
            codigoRevisado = false;
            if (idTipoStickerTmp == null || idTipoStickerTmp == 0) {
                idTipoStickerTmp = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoSticker"));
            }
            if (detalleImpresion != null && !detalleImpresion.isEmpty() && idTipoSticker != null && idTipoSticker != 0 && !dlgCambiarTipoSticker) {
                dlgCambiarTipoSticker = true;
            } else if (idTipoStickerTmp == null || idTipoStickerTmp.equals(idTipoSticker)) {
                idTipoSticker = null;
                detalleImpresion = new ArrayList<>();
            } else {
                idTipoSticker = idTipoStickerTmp;
                log.log(Level.INFO, "Se selecciono el tipo de sticker id [{0}]", idTipoSticker);

                for (TipoStickerDTO t : tiposSticker) {
                    if (t.getIdTipoSticker().equals(idTipoSticker) && t.getNombreSticker().contains("CÓDIGO REVISADO")) {
                        codigoRevisado = true;
                        break;
                    }
                }

                idTipoStickerTmp = null;
                if (dlgCambiarTipoSticker) {
                    ImpresionSticker i = impresionStickerFacade.find(impresionSticker.getIdImpresionSticker());

                    if (i != null && i.getIdImpresionSticker() != null && i.getIdImpresionSticker() != 0) {
                        i.setIdTipoSticker(new TipoSticker(idTipoSticker));

                        try {
                            impresionStickerFacade.edit(i);
                            log.log(Level.INFO, "Se modifico el tipo de sticker a imprimir para la solicitud de impresion de sticker con id [{0}]", impresionSticker.getIdImpresionSticker());
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al modificar el tipo de sticker a la solicitud de impresion de sticker con id [{0}]. Error [{1}]",
                                    new Object[]{impresionSticker.getIdImpresionSticker(), e.getMessage()});
                            mostrarMensaje("Error", "No se puede cambier el tipo de sticker.", true, false, false);
                            return;
                        }
                    }
                }
                dlgCambiarTipoSticker = false;
            }
        } else {
            mostrarMensaje("Error", "No se encontraron impresoras disponibles para esta sucursal.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron impresoras disponibles para esta sucursal");
            return;
        }
    }

    public void usarEan() {
        if (usarEan) {
            usarEan = false;
        } else {
            usarEan = true;
        }
    }

    public void agregarReferencia() {
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar una referencia para poder continuar", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar una referencia para poder continuar");
            return;
        }
        if (cantidad == null || cantidad == 0) {
            log.log(Level.INFO, "No se encontro cantidad ingresada, el sistema procede a asignar cantidad 1");
            cantidad = 1;
        }

        ItemInventario item = new ItemInventario();
        if (usarEan) {
            item = itemInventarioFacade.buscarXEan(referencia);

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                referencia = item.getItemCode();
            } else {
                item = itemInventarioFacade.find(genericMBean.completarReferencia(referencia));

                if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                    referencia = item.getItemCode();
                } else {
                    mostrarMensaje("Error", "La referencia ingresada no existe.", true, false, false);
                    log.log(Level.SEVERE, "La referencia ingresada no existe");
                    return;
                }
            }
        } else {
            item = itemInventarioFacade.find(genericMBean.completarReferencia(referencia));

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                referencia = item.getItemCode();
            } else {
                mostrarMensaje("Error", "La referencia ingresada no existe.", true, false, false);
                log.log(Level.SEVERE, "La referencia ingresada no existe");
                return;
            }
        }

        if (validarReferenciaIngresada(item)) {
            log.log(Level.INFO, "Se agrego la referencia [{0}], a la impresion de sticker", referencia);
            referencia = null;
            cantidad = null;
        }
    }

    private boolean validarReferenciaIngresada(ItemInventario item) {
        if (impresionSticker == null || impresionSticker.getIdImpresionSticker() == null || impresionSticker.getIdImpresionSticker() == 0) {
            if (!crearImpresionSticker()) {
                mostrarMensaje("Error", "No se puede crear la solicitud de stickers.", true, false, false);
                return false;
            }
        }

        if (detalleImpresion == null || detalleImpresion.isEmpty()) {
            detalleImpresion = new ArrayList<>();
            if (tratarDetalle(crearDetalleImpresion(item))) {
                return true;
            } else {
                return false;
            }
        } else {
            boolean existe = false;
            for (DetalleImpresionStickerDTO d : detalleImpresion) {
                if (d.getReferencia().equals(referencia)) {
                    DetalleImpresionSticker det = detalleImpresionStickerFacade.find(d.getIdDetalleImpresionSticker());

                    if (det != null && det.getIdDetalleImpresionSticker() != null && det.getIdDetalleImpresionSticker() != 0) {
                        det.setCantidad(det.getCantidad() + cantidad);

                        try {
                            detalleImpresionStickerFacade.edit(det);
                            log.log(Level.INFO, "Se modifico la cantidad del detalle de impresion de sticker con id [{0}]", det.getIdDetalleImpresionSticker());
                            d.setCantidad(d.getCantidad() + cantidad);
                            existe = true;
                            return true;
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al modificar la cantidad de la referencia [{0}], para impresion de stickers. Error [{1}]", new Object[]{referencia, e.getMessage()});
                            mostrarMensaje("Error", "No se pudo aumentar la cantidad de impresión para la referencia " + referencia, true, false, false);
                            return false;
                        }
                    }
                }
            }

            if (!existe) {
                if (tratarDetalle(crearDetalleImpresion(item))) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    private boolean tratarDetalle(DetalleImpresionSticker d) {
        if (d != null && d.getIdDetalleImpresionSticker() != null && d.getIdDetalleImpresionSticker() != 0) {
            detalleImpresion.add(0, new DetalleImpresionStickerDTO(d.getIdDetalleImpresionSticker(), d.getIdImpresionSticker().getIdImpresionSticker(), d.getCantidad(),
                    d.getPrecio(), d.getReferencia(), d.getAlmacen(), d.getNombreReferencia(), d.getReferenciaProveedor(), d.getFecha()));
            return true;
        } else {
            mostrarMensaje("Error", "No se pudo agregar la referencia a la impresión de sticker.", true, false, false);
            log.log(Level.SEVERE, "No se pudo agregar la referencia a la impresion de sticker");
            return false;
        }
    }

    private boolean crearImpresionSticker() {
        ImpresionSticker printer = new ImpresionSticker();

        printer.setEstado("PP");
        printer.setFecha(new Date());
        printer.setSucursal(sessionMBean.getAlmacen());
        printer.setUsuario(sessionMBean.getUsuario());
        printer.setIdTipoSticker(new TipoSticker(idTipoSticker));

        try {
            impresionStickerFacade.create(printer);
            log.log(Level.INFO, "Se creo registro de impresion para la sucursal [{0}]", sessionMBean.getAlmacen());

            impresionSticker = new ImpresionStickerDTO(printer.getIdImpresionSticker(), printer.getIdTipoSticker().getIdTipoSticker(), printer.getUsuario(),
                    printer.getSucursal(), printer.getEstado(), printer.getFecha());
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear la impresion de sticker. Error [{0}]", e.getMessage());
            return false;
        }
    }

    private DetalleImpresionSticker crearDetalleImpresion(ItemInventario item) {
        DetalleImpresionSticker det = new DetalleImpresionSticker();

        det.setAlmacen(sessionMBean.getAlmacen());
        det.setCantidad(cantidad);
        det.setFecha(new Date());
        det.setIdImpresionSticker(new ImpresionSticker(impresionSticker.getIdImpresionSticker()));
        det.setReferencia(referencia);
        if (item != null) {
            det.setNombreReferencia(item.getItemName());
            det.setPrecio(genericMBean.obtenerPrecioVenta(referencia));
            det.setReferenciaProveedor(item.getUURefPro());
        }

        try {
            detalleImpresionStickerFacade.create(det);
            log.log(Level.INFO, "Se creo detalle para la impresion de sticker con id [{0}]", impresionSticker.getIdImpresionSticker());
            return det;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear el detalle de impresion. Error [{0}]", e.getMessage());
            return null;
        }
    }

    public void seleccionarReferenciaEliminar() {
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        if (referencia != null && !referencia.isEmpty()) {
            for (DetalleImpresionStickerDTO d : detalleImpresion) {
                if (d.getReferencia().equals(referencia)) {
                    dlgEliminar = true;
                    break;
                }
            }
        }
    }

    public void eliminarReferencia() {
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "No se a seleccionado una referencia para poder continuar", true, false, false);
            log.log(Level.SEVERE, "No se a seleccionado una referencia para poder continuar");
            return;
        }

        for (DetalleImpresionStickerDTO d : detalleImpresion) {
            if (d.getReferencia().equals(referencia)) {
                DetalleImpresionSticker det = detalleImpresionStickerFacade.find(d.getIdDetalleImpresionSticker());

                if (det != null && det.getIdDetalleImpresionSticker() != null && det.getIdDetalleImpresionSticker() != 0) {
                    if (det.getCantidad() > 1) {
                        det.setCantidad(det.getCantidad() - 1);

                        try {
                            detalleImpresionStickerFacade.edit(det);
                            log.log(Level.INFO, "Se elimino una unidad de la referencia [{0}], del detalle de impresion sticker [{1}]",
                                    new Object[]{referencia, d.getIdDetalleImpresionSticker()});

                            d.setCantidad(d.getCantidad() - 1);
                            break;
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al eliminar una unidad de la referencia [{0}], de detalle de impresion sticker [{1}]. Error [{2}]",
                                    new Object[]{referencia, d.getIdDetalleImpresionSticker(), e.getMessage()});
                            mostrarMensaje("Error", "No se pudo eliminar una unidad de la referencia solicitada.", true, false, false);
                            return;
                        }
                    } else {
                        try {
                            detalleImpresionStickerFacade.remove(det);
                            log.log(Level.INFO, "Se elimino la referencia [{0}], de la impresion de sticker con id [{1}]", new Object[]{referencia, impresionSticker.getIdImpresionSticker()});

                            detalleImpresion.remove(d);
                            break;
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al eliminar la referencia [{0}], de la impresion de sticker con id [{1}]. Error [{2}]",
                                    new Object[]{referencia, impresionSticker.getIdImpresionSticker(), e.getMessage()});
                            mostrarMensaje("Error", "No se pudo eliminar la referencia solicitada.", true, false, false);
                            return;
                        }
                    }
                } else {
                    mostrarMensaje("Error", "No se encontraron datos para eliminar.", true, false, false);
                    log.log(Level.SEVERE, "No se encontraron datos para eliminar");
                    return;
                }
            }
        }

        referencia = null;
    }

    public void mandarImpresion() {
        if (impresoras.size() > 1) {
            dlgImpresoras = true;
        } else {
            ejecutarImpresion(impresoras.get(0));
        }
    }

    public void seleccionarImpresora() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idImpresora"));

        if (idImpresora != null && idImpresora.equals(id)) {
            idImpresora = null;
        } else {
            idImpresora = id;
        }
    }

    public void continuarImpresion() {
        if (idImpresora != null && idImpresora != 0) {
            for (ImpresoraDTO i : impresoras) {
                if (i.getIdImpresora().equals(idImpresora)) {
                    ejecutarImpresion(i);
                    break;
                }
            }
        } else {
            mostrarMensaje("Error", "No se pudo mandar la impresión de sticker, verifique que seleccionó una impresora.", true, false, false);
            log.log(Level.SEVERE, "No se pudo mandar la impresion de sticker, verifique que selecciono una impresora");
            return;
        }
    }

    private void ejecutarImpresion2(ImpresoraDTO imp) {
        if (sessionMBean.validarPermisoUsuario(Objetos.PEDIDO_STICKER, Acciones.IMPRIMIR)) {
            ImpresionSticker i = impresionStickerFacade.find(impresionSticker.getIdImpresionSticker());

            if (i != null && i.getIdImpresionSticker() != null && i.getIdImpresionSticker() != 0) {
                i.setEstado(imp.getEstadoEjecucion());

                try {
                    impresionStickerFacade.edit(i);
                    log.log(Level.INFO, "Se manda orden a TASKCENTER para que inicie la impresion de la solicitud de impresion sticker con id [{0}]",
                            impresionSticker.getIdImpresionSticker());
                    limpiar();

                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al mandar a imprimir la solicitud de impresion de sticker con id [{0}]. Error [{1}]",
                            new Object[]{impresionSticker.getIdImpresionSticker(), e.getMessage()});
                    mostrarMensaje("Error", "No se pudo imprimir la solicitud de stickers.", true, false, false);
                    return;
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene autorización para imprimir stickers.", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene autorizacion para imprimir stickers");
            return;
        }
    }

    private void ejecutarImpresion(ImpresoraDTO imp) {
        if (sessionMBean.validarPermisoUsuario(Objetos.PEDIDO_STICKER, Acciones.IMPRIMIR)) {
            ImpresionSticker i = impresionStickerFacade.find(impresionSticker.getIdImpresionSticker());

            if (i != null && i.getIdImpresionSticker() != null && i.getIdImpresionSticker() != 0) {
                ZebraPrintDTO dto = new ZebraPrintDTO();

                dto.setColumns(i.getIdTipoSticker().getColumnas());
                dto.setLabelType(i.getIdTipoSticker().getIdTipoEtiqueta());
                dto.setPrinterName(imp.getNombreImpresoraServidor());
                dto.setItems(new ArrayList<ItemLabelDTO>());
                dto.setCodigos(new ArrayList<CodigoRevisadoLabelDTO>());

                if (!i.getIdTipoSticker().getTipoAlmacen().equals("CODIGO")) {
                    for (DetalleImpresionStickerDTO d : detalleImpresion) {
                        ItemLabelDTO item = new ItemLabelDTO();

                        item.setItemCode(d.getReferencia());
                        item.setItemName(d.getNombreReferencia());
                        item.setPrice(genericMBean.getValorFormateado("Double", d.getPrecio().toString(), 2));
                        item.setProvCode(d.getReferenciaProveedor());
                        item.setQuantity(d.getCantidad());
                        item.setType(i.getIdTipoSticker().getTipoAlmacen());

                        dto.getItems().add(item);
                    }
                } else {
                    for (DetalleImpresionStickerDTO d : detalleImpresion) {
                        Empleado emp = empleadoFacade.obtenerEmpleadoDocumento(d.getReferencia());

                        if (emp != null && emp.getEmpID() != null && emp.getEmpID() != 0) {
                            CodigoRevisadoLabelDTO code = new CodigoRevisadoLabelDTO();

                            code.setCantidad(d.getCantidad());
                            code.setCodigoRevisado(emp.getUcodigoRevisado());
                            code.setFecha("Matisses - " + new GregorianCalendar().get(Calendar.YEAR));
                            code.setNombreEmpleado(emp.getFirstName() + " " + ((emp.getMiddleName() != null && !emp.getMiddleName().isEmpty()) ? emp.getMiddleName() : "") + emp.getLastName());

                            dto.getCodigos().add(code);
                        }
                    }
                }

                if ((dto.getCodigos() != null && !dto.getCodigos().isEmpty()) || (dto.getItems() != null && !dto.getItems().isEmpty())) {
                    ZebraPrintClient client = new ZebraPrintClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                    GenericRESTResponseDTO res = client.print(dto);
                    if (res != null && res.getEstado() >= 0) {
                        try {
                            impresionStickerFacade.finalizarImpresion(i.getIdImpresionSticker());
                            log.log(Level.INFO, "Se manda estado de TERMINADO a la solicitud de impresion sticker con id [{0}]", impresionSticker.getIdImpresionSticker());
                            limpiar();
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error al mandar estado TERMINADO a la solicitud de impresion de sticker con id [{0}]. Error [{1}]",
                                    new Object[]{impresionSticker.getIdImpresionSticker(), e.getMessage()});
                            return;
                        }
                    }
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene autorización para imprimir stickers.", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene autorizacion para imprimir stickers");
            return;
        }
    }

//    private boolean validarSolicitudCreada(ImpresionSticker i) {
//        int intentos = 0;
//        while (intentos < 60) {
//            try {
//                if (intentos % 5 == 0) {
//                    log.log(Level.INFO, "Intento de consulta de impresion de stickers #[{0}]", intentos);
//                }
//                ImpresionSticker validacion = impresionStickerFacade.find(i.getIdImpresionSticker());
//                if (validacion.getEstado() != null && validacion.getEstado().equals("IT")) {
//                    log.log(Level.INFO, "Impresion realiza correctamente");
//                    return true;
//                }
//                Thread.sleep(498);
//            } catch (Exception e) {
//                log.log(Level.SEVERE, e.getMessage());
//                return false;
//            }
//            intentos++;
//        }
//        return false;
//    }
    public void cancelarSolicitudStickers() {
        if (sessionMBean.validarPermisoUsuario(Objetos.PEDIDO_STICKER, Acciones.CANCELAR)) {
            ImpresionSticker i = impresionStickerFacade.find(impresionSticker.getIdImpresionSticker());

            if (i != null && i.getIdImpresionSticker() != null && i.getIdImpresionSticker() != 0) {
                i.setEstado("IC");

                try {
                    impresionStickerFacade.edit(i);
                    log.log(Level.INFO, "Se marco la solicitud de impresion sticker con id [{0}], como cancelada", impresionSticker.getIdImpresionSticker());
                    limpiar();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al cancelar la solicitud de impresion de sticker con id [{0}]. Error [{1}]",
                            new Object[]{impresionSticker.getIdImpresionSticker(), e.getMessage()});
                    mostrarMensaje("Error", "No se pudo cancelar la solicitud de stickers.", true, false, false);
                    return;
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene autorización para cancelar la solicitud de impresión de stickers.", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene autorizacion para cancelar la solicitud de impresion de stickers");
            return;
        }
    }

    private void limpiar() {
        cantidad = null;
        idTipoSticker = null;
        idTipoStickerTmp = null;
        idImpresora = null;
        referencia = null;
        dlgEliminar = false;
        usarEan = false;
        permitirTrabajo = true;
        dlgCambiarTipoSticker = false;
        dlgImpresoras = false;
        codigoRevisado = false;
        impresionSticker = new ImpresionStickerDTO();
        tiposSticker = new ArrayList<>();
        detalleImpresion = new ArrayList<>();
        impresoras = new ArrayList<>();
        initialize();
    }

    /*Codigo para la impresion de los codigos de revisado*/
    public void seleccionarCantidadCodigosRevisados() {
        cantidad = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cantidad"));
        referencia = sessionMBean.getCedulaEmpleado();

        if (cantidad != null && cantidad != 0) {
            if (impresionSticker == null || impresionSticker.getIdImpresionSticker() == null || impresionSticker.getIdImpresionSticker() == 0) {
                crearImpresionSticker();

                detalleImpresion = new ArrayList<>();
            }

            if (detalleImpresion != null && !detalleImpresion.isEmpty()) {
                try {
                    detalleImpresionStickerFacade.remove(new DetalleImpresionSticker(detalleImpresion.get(0).getIdDetalleImpresionSticker()));
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al eliminar la cantidad anterior de codigos de revisado. ", e);
                    mostrarMensaje("Error", "Ocurrió un error al eliminar la cantidad anterior de códigos de revisado.", true, false, false);
                    return;
                }

                crearDetalleImpresion(null);

                DetalleImpresionSticker d = crearDetalleImpresion(null);

                if (d != null && d.getIdDetalleImpresionSticker() != null && d.getIdDetalleImpresionSticker() != 0) {
                    detalleImpresion.add(0, new DetalleImpresionStickerDTO(d.getIdDetalleImpresionSticker(), d.getIdImpresionSticker().getIdImpresionSticker(), d.getCantidad(),
                            d.getPrecio(), d.getReferencia(), d.getAlmacen(), d.getNombreReferencia(), d.getReferenciaProveedor(), d.getFecha()));
                }
            } else {
                DetalleImpresionSticker d = crearDetalleImpresion(null);

                if (d != null && d.getIdDetalleImpresionSticker() != null && d.getIdDetalleImpresionSticker() != 0) {
                    detalleImpresion.add(0, new DetalleImpresionStickerDTO(d.getIdDetalleImpresionSticker(), d.getIdImpresionSticker().getIdImpresionSticker(), d.getCantidad(),
                            d.getPrecio(), d.getReferencia(), d.getAlmacen(), d.getNombreReferencia(), d.getReferenciaProveedor(), d.getFecha()));
                }
            }

            referencia = null;
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
