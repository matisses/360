package co.matisses.web.mbean.ventas;

import co.matisses.persistence.dwb.entity.Devolucion;
import co.matisses.persistence.dwb.facade.DevolucionFacade;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.OrdenVenta;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.OrdenVentaFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.sap.facade.SerieFacade;
import co.matisses.persistence.web.entity.CotizacionWeb;
import co.matisses.persistence.web.entity.DetalleCotizacionWeb;
import co.matisses.persistence.web.facade.CotizacionWebFacade;
import co.matisses.persistence.web.facade.DetalleCotizacionWebFacade;
import co.matisses.web.bcs.client.CreditNotesClient;
import co.matisses.web.bcs.client.GoodsIssueClient;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.CotizacionWebDTO;
import co.matisses.web.dto.DetalleCotizacionWebDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.GoodsIssueDTO;
import co.matisses.web.dto.GoodsIssueDetailDTO;
import co.matisses.web.dto.GoodsIssueLocationsDTO;
import co.matisses.web.dto.NotaCreditoDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.dto.SalesDocumentDTO;
import co.matisses.web.dto.SalesDocumentLineDTO;
import co.matisses.web.dto.SalesEmployeeDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.ClienteSessionMBean;
import co.matisses.web.mbean.session.VentasSessionMBean;
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
@Named(value = "crearNotaCreditoMBean")
public class CrearNotaCreditoMBean implements Serializable {

    @Inject
    private VentasSessionMBean ventasSessionMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private ClienteSessionMBean clienteSessionMBean;
    private static final Logger log = Logger.getLogger(CrearNotaCreditoMBean.class.getSimpleName());
    private Integer totalItemsAsignados = 0;
    private Integer posicionItem = 0;
    private Integer pasoNotaCredito = 1;
    private String referencia;
    private String comentario;
    private String documentoRelacionado;
    private boolean dlgNotaCredito = false;
    private boolean dlgCrearNotaCredito = false;
    private boolean dlgDocumentoRelacionado = false;
    private NotaCreditoDTO item;
    private CotizacionWebDTO cotizacion;
    private List<String[]> imagenes;
    private List<NotaCreditoDTO> items;
    private List<NotaCreditoDTO> itemsAplicables;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private DevolucionFacade devolucionFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private SerieFacade serieFacade;
    @EJB
    private OrdenVentaFacade ordenVentaFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private CotizacionWebFacade cotizacionWebFacade;
    @EJB
    private DetalleCotizacionWebFacade detalleCotizacionWebFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;

    public CrearNotaCreditoMBean() {
        item = new NotaCreditoDTO();
        imagenes = new ArrayList<>();
        items = new ArrayList<>();
        itemsAplicables = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        if (ventasSessionMBean.isCreaNotaCredito() && ventasSessionMBean.getNumeroFactura() != null && ventasSessionMBean.getNumeroFactura() != 0) {
            verificarDatosNotaCredito();
        } else {
            ventasSessionMBean.setCreaNotaCredito(false);
            if (ventasSessionMBean.isClienteVerificado()) {
                dlgDocumentoRelacionado = true;
            }

            obtenerNotaCreditoPendiente();
        }
    }

    public Integer getTotalItemsAsignados() {
        return totalItemsAsignados;
    }

    public void setTotalItemsAsignados(Integer totalItemsAsignados) {
        this.totalItemsAsignados = totalItemsAsignados;
    }

    public Integer getPosicionItem() {
        return posicionItem;
    }

    public void setPosicionItem(Integer posicionItem) {
        this.posicionItem = posicionItem;
    }

    public Integer getPasoNotaCredito() {
        return pasoNotaCredito;
    }

    public void setPasoNotaCredito(Integer pasoNotaCredito) {
        this.pasoNotaCredito = pasoNotaCredito;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getDocumentoRelacionado() {
        return documentoRelacionado;
    }

    public void setDocumentoRelacionado(String documentoRelacionado) {
        this.documentoRelacionado = documentoRelacionado;
    }

    public boolean isDlgNotaCredito() {
        return dlgNotaCredito;
    }

    public void setDlgNotaCredito(boolean dlgNotaCredito) {
        this.dlgNotaCredito = dlgNotaCredito;
    }

    public boolean isDlgCrearNotaCredito() {
        return dlgCrearNotaCredito;
    }

    public void setDlgCrearNotaCredito(boolean dlgCrearNotaCredito) {
        this.dlgCrearNotaCredito = dlgCrearNotaCredito;
    }

    public boolean isDlgDocumentoRelacionado() {
        return dlgDocumentoRelacionado;
    }

    public void setDlgDocumentoRelacionado(boolean dlgDocumentoRelacionado) {
        this.dlgDocumentoRelacionado = dlgDocumentoRelacionado;
    }

    public NotaCreditoDTO getItem() {
        return item;
    }

    public void setItem(NotaCreditoDTO item) {
        this.item = item;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public List<NotaCreditoDTO> getItems() {
        return items;
    }

    public void setItems(List<NotaCreditoDTO> items) {
        this.items = items;
    }

    public BigDecimal getTotalNotaCredito() {
        if (items != null && !items.isEmpty()) {
            BigDecimal total = new BigDecimal(0);
            for (NotaCreditoDTO n : items) {
                total = total.add(new BigDecimal(n.getPrice() * n.getCantidadUsada()));
            }

            return total;
        } else {
            return new BigDecimal(0);
        }
    }

    private void obtenerNotaCreditoPendiente() {
        log.log(Level.INFO, "El sistema esta verificando si el usuario {0} tiene cotizaciones de nota credito pendientes", sessionInfoMBean.getUsuario());
        List<CotizacionWeb> cotizaciones = cotizacionWebFacade.obtenerCotizacionesNotaCreditoPendientes(Integer.parseInt(sessionInfoMBean.getCodigoVentas()));
        if (cotizaciones != null && !cotizaciones.isEmpty()) {
            log.log(Level.INFO, "Se encontraron {0} cotizaciones pendiente para el usuario {1}", new Object[]{cotizaciones.size(), sessionInfoMBean.getUsuario()});

            if (cotizaciones != null && !cotizaciones.isEmpty()) {
                if (cotizaciones.size() > 1) {
                    for (int i = 0; i < cotizaciones.size() - 1; i++) {
                        CotizacionWeb cot = cotizaciones.get(i);

                        cot.setEstado("NF");

                        try {
                            cotizacionWebFacade.edit(cot);
                            log.log(Level.INFO, "Se marco la cotizacion de nota credito con id {0} con el estado NO FINALIZADO", cot.getIdCotizacion());
                            cotizaciones.remove(i);
                            i--;
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al marcar la cotizacion de nota credito con id {0} como NO FINALIZADA. Error {1}", new Object[]{cot.getIdCotizacion(), e.getMessage()});
                            return;
                        }
                    }
                }

                CotizacionWeb cot = cotizaciones.get(0);

                cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
                cotizacion.setDemostracion(cot.getDemostracion() != null ? cot.getDemostracion() : false);

                items = new ArrayList<>();
                List<DetalleCotizacionWeb> detalles = detalleCotizacionWebFacade.findByIdCotizacion(cotizacion.getIdCotizacion());

                if (detalles != null && !detalles.isEmpty()) {
                    for (DetalleCotizacionWeb d : detalles) {
                        cotizacion.getDetalle().add(new DetalleCotizacionWebDTO(d.getCantidad(), cotizacion.getIdCotizacion().intValue(), null,
                                d.getIdDetalleCotizacion(), d.getReferencia(), d.getBodega(), null, null));
                        items.add(0, new NotaCreditoDTO(0, d.getCantidad(), 0, baruGenericMBean.obtenerPrecioVenta(d.getReferencia()).doubleValue(), d.getReferencia()));
                    }
                }
            }
        } else {
            log.log(Level.INFO, "No se encontraron cotizaciones de nota credito pendiente para el usuario {0}", sessionInfoMBean.getUsuario());
        }
    }

    public void agregarReferencia() {
        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar una referencia para agregar.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar una referencia para agregar");
            return;
        }

        if (referencia.length() < 20) {
            referencia = baruGenericMBean.completarReferencia(referencia);
        } else if (referencia.length() > 20) {
            log.log(Level.SEVERE, "Se debe ingresar una referencia de 20 caracteres para poder continuar");
            mostrarMensaje("Error", "Se debe ingresar una referencia de 20 caracteres para poder continuar", true, false, false);
            return;
        }

        ItemInventario inv = itemInventarioFacade.getItemBasicInfo(referencia);

        if (inv != null && inv.getItemCode() != null && !inv.getItemCode().isEmpty()) {
            boolean existe = false;

            for (NotaCreditoDTO n : items) {
                if (n.getReferencia().equals(referencia)) {
                    n.setCantidadUsada(n.getCantidadUsada() + 1);
                    existe = true;
                    break;
                }
            }

            if (!existe) {
                items.add(0, new NotaCreditoDTO(0, 1, 0, baruGenericMBean.obtenerPrecioVenta(referencia).doubleValue(), referencia));
            }
        } else {
            mostrarMensaje("Error", "La referencia ingresada no existe.", true, false, false);
            log.log(Level.SEVERE, "La referencia {0} ingresada no existe", referencia);
            return;
        }

        aplicarDatosBD();
        referencia = null;
    }

    private void aplicarDatosBD() {
        if (cotizacion == null || cotizacion.getIdCotizacion() == null || cotizacion.getIdCotizacion() == 0) {
            CotizacionWeb cot = new CotizacionWeb();

            cot.setEstado("NP");
            cot.setSucursal(sessionInfoMBean.getAlmacen());
            cot.setUsuario(sessionInfoMBean.getUsuario());
            cot.setIdVendedor(Integer.parseInt(sessionInfoMBean.getCodigoVentas()));
            cot.setFecha(new Date());
            cot.setNitCliente(null);

            try {
                cotizacionWebFacade.create(cot);
                log.log(Level.INFO, "Se creo la cotizacion con id {0} para la nota credito", cot.getIdCotizacion());
                cotizacion = new CotizacionWebDTO(cot.getIdVendedor(), cot.getIdCotizacion(), cot.getSucursal(), cot.getEstado(), cot.getNitCliente(), cot.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al crear cotizacion para la nota credito", e);
                mostrarMensaje("Error", "Ocurrió un error al registrar los datos de la nota crédito.", true, false, false);
                return;
            }
        }

        asignarDatosDTO();
    }

    private void asignarDatosDTO() {
        /*Se llenan los datos del detalle*/
        if (cotizacion.getDetalle() != null && !cotizacion.getDetalle().isEmpty()) {
            for (NotaCreditoDTO n : items) {
                boolean existe = false;
                for (int i = 0; i < cotizacion.getDetalle().size(); i++) {
                    DetalleCotizacionWebDTO det = cotizacion.getDetalle().get(i);

                    if (n.getReferencia().equals(det.getReferencia())) {
                        if (n.getCantidadUsada() != det.getCantidad() && n.getCantidadUsada() > 0) {
                            DetalleCotizacionWeb detalle = detalleCotizacionWebFacade.find(det.getIdDetalleCotizacion());

                            if (detalle != null && detalle.getIdDetalleCotizacion() != null && detalle.getIdDetalleCotizacion() != 0) {
                                detalle.setCantidad(n.getCantidadUsada());
                                det.setCantidad(n.getCantidadUsada());

                                try {
                                    detalleCotizacionWebFacade.edit(detalle);
                                    log.log(Level.INFO, "Se modifico el detalle de id {0} para la cotizacion de nota credito con id {1}", new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion()});
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Ocurrio un error al modificar el detalle de id {0} para la cotizacion de nota credito con id {1}. Error {2}",
                                            new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion(), e.getMessage()});
                                    return;
                                }
                            }
                        } else if (n.getCantidadUsada() == 0) {
                            DetalleCotizacionWeb detalle = detalleCotizacionWebFacade.find(det.getIdDetalleCotizacion());

                            if (detalle != null && detalle.getIdDetalleCotizacion() != null && detalle.getIdDetalleCotizacion() != 0) {
                                detalle.setCantidad(n.getCantidadUsada());
                                det.setCantidad(n.getCantidadUsada());

                                try {
                                    detalleCotizacionWebFacade.remove(detalle);
                                    log.log(Level.INFO, "Se elimino el detalle de id [{0}] para la cotizacion de nota credito con id [{1}]",
                                            new Object[]{detalle.getIdDetalleCotizacion(), cotizacion.getIdCotizacion()});
                                    cotizacion.getDetalle().remove(i);
                                    i--;
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Ocurrio un error al eliminar el detalle de id [{0}] para la cotizacion de nota credito con id [{1}]. Error [{2}]",
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
                    crearDetalleCotizacion(n);
                }
            }
        } else {
            for (NotaCreditoDTO n : items) {
                if (n.getCantidadUsada() > 0) {
                    crearDetalleCotizacion(n);
                }
            }
        }
    }

    public void crearDetalleCotizacion(NotaCreditoDTO n) {
        DetalleCotizacionWeb detalle = new DetalleCotizacionWeb();

        detalle.setBodega("9803");
        detalle.setCantidad(n.getCantidadUsada());
        detalle.setReferencia(n.getReferencia());
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

    private void verificarDatosNotaCredito() {
        items = new ArrayList<>();
        FacturaSAP factura = facturaSAPFacade.findByDocNum(ventasSessionMBean.getNumeroFactura());

        if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
            List<DetalleFacturaSAP> det = detalleFacturaSAPFacade.obtenerDetalleFactura(factura.getDocEntry().doubleValue());

            if (det != null && !det.isEmpty()) {
                int contador = 0;
                for (DetalleFacturaSAP d : det) {
                    boolean existe = false;
                    NotaCreditoDTO dto = new NotaCreditoDTO();

                    dto.setLinea(contador);
                    dto.setCantidadDisponible(d.getQuantity().intValue());
                    dto.setCantidadUsada(0);
                    dto.setPrice(d.getPriceAfVAT().doubleValue() * d.getQuantity().doubleValue());
                    dto.setReferencia(d.getItemCode());

                    if (items.isEmpty()) {
                        items.add(dto);
                        existe = true;
                    } else {
                        for (NotaCreditoDTO n : items) {
                            if (n.getReferencia().equals(d.getItemCode())) {
                                n.setCantidadDisponible(n.getCantidadDisponible() + d.getQuantity().intValue());
                                n.setPrice(n.getPrice() + dto.getPrice());
                                existe = true;
                                break;
                            } else {
                                existe = false;
                            }
                        }
                    }

                    if (!existe) {
                        items.add(dto);
                    }
                    contador++;
                }

                List<Devolucion> devoluciones = devolucionFacade.obtenerDevolucionesFactura(String.valueOf(factura.getDocNum()));

                if (devoluciones != null && !devoluciones.isEmpty()) {
                    for (Devolucion d : devoluciones) {
                        for (NotaCreditoDTO n : items) {
                            if (d.getReferencia().equals(n.getReferencia())) {
                                n.setCantidadDisponible(n.getCantidadDisponible() - d.getCantidad().intValue());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    public String iniciarCreacion() {
        dlgNotaCredito = false;
        itemsAplicables = new ArrayList<>();
        posicionItem = 0;

        for (NotaCreditoDTO n : items) {
            if (n.getCantidadUsada() > 0) {
                itemsAplicables.add(n);
            }
        }

        if (itemsAplicables == null || itemsAplicables.isEmpty()) {
            mostrarMensaje("Error", "Debe haber al menos 1 ítem para poder crear la nota crédito.", true, false, false);
            log.log(Level.SEVERE, "Debe haber al menos 1 item para poder crear la nota credito");
            return "";
        }

        if (dlgDocumentoRelacionado) {
            if (documentoRelacionado == null || documentoRelacionado.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar el documento relacionado para poder crear la nota crédito.", true, false, false);
                log.log(Level.SEVERE, "Debe ingresar el documento relacionado para poder crear la nota credito");
                return "";
            }

            pasoNotaCredito = 2;
            dlgDocumentoRelacionado = false;
            dlgNotaCredito = true;
            return "";
        }

        if (ventasSessionMBean.isCreaNotaCredito()) {
            pasoNotaCredito = 1;
            if (itemsAplicables != null && !itemsAplicables.isEmpty()) {
                obtenerItemPosicion();
                dlgNotaCredito = true;
            } else {
                dlgNotaCredito = false;
                mostrarMensaje("Error", "Debe asignarle cantidad al menos a una referencia.", true, false, false);
                log.log(Level.SEVERE, "Debe asignarle cantidad al menos a una referencia");
            }
        } else {
            if (!ventasSessionMBean.isClienteVerificado()) {
                ventasSessionMBean.setCreaNotaCredito(true);
                return "clientes";
            }

            dlgDocumentoRelacionado = true;
        }
        return "";
    }

    public void aumentarCantidadUsado() {
        Integer linea = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("linea"));

        for (NotaCreditoDTO n : items) {
            if (n.getLinea() == linea) {
                if (n.getCantidadDisponible() > n.getCantidadUsada()) {
                    if (n.getCantidadUsada() == 0) {
                        totalItemsAsignados++;
                    }

                    n.setCantidadUsada(n.getCantidadUsada() + 1);
                } else {
                    mostrarMensaje("Error", "No se puede asignar más de la cantidad disponible.", true, false, false);
                    log.log(Level.SEVERE, "No se puede asignar mas de la cantidad disponible");
                    return;
                }
                break;
            }
        }
    }

    public void reducirCantidadUsado() {
        Integer linea = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("linea"));

        for (NotaCreditoDTO n : items) {
            if (n.getLinea() == linea) {
                if (n.getCantidadUsada() <= n.getCantidadDisponible() && n.getCantidadUsada() > 0) {
                    n.setCantidadUsada(n.getCantidadUsada() - 1);

                    if (n.getCantidadUsada() == 0) {
                        totalItemsAsignados--;
                    }
                } else {
                    mostrarMensaje("Error", "No se puede reducir más de la cantidad disponible.", true, false, false);
                    log.log(Level.SEVERE, "No se puede reducir mas de la cantidad disponible");
                    return;
                }
                break;
            }
        }
    }

    public void obtenerSiguientePasoNotaCredito() {
        if (pasoNotaCredito != null) {
            if (pasoNotaCredito == 1) {
                if ((totalItemsAsignados - 1) == posicionItem) {
                    pasoNotaCredito++;
                } else {
                    posicionItem++;
                    obtenerItemPosicion();
                }
            }
        }
    }

    public void obtenerAnteriorPasoNotaCredito() {
        if (pasoNotaCredito != null) {
            if (pasoNotaCredito == 1) {
                posicionItem--;
                obtenerItemPosicion();
            }
        }
    }

    private void obtenerItemPosicion() {
        item = itemsAplicables.get(posicionItem);

        if (item.getUbicaciones() == null || item.getUbicaciones().isEmpty()) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoCliente(item.getReferencia());

            if (saldos != null && !saldos.isEmpty()) {
                item.setUbicaciones(new ArrayList<SaldoUbicacionDTO>());

                for (SaldoUbicacion s : saldos) {
                    SaldoUbicacionDTO dto = new SaldoUbicacionDTO();

                    dto.setAbsEntry(s.getAbsEntry());
                    dto.setBinCode(s.getUbicacion().getBinCode());
                    dto.setOnHandQty(s.getOnHandQty().intValue());
                    dto.setUbicacion(s.getUbicacion().getAbsEntry());
                    dto.setWhsCode(s.getWhsCode());
                    dto.setOnHandQtyUsado(0);

                    item.getUbicaciones().add(dto);
                }
            }
        }
        obtenerGaleria();
    }

    private void obtenerGaleria() {
        log.log(Level.INFO, "Se estan obteniendo el catalogo de imagenes de la referencia {0}", item.getReferencia());

        /*1. Se obtiene la galeria de imagenes*/
        imagenes = new ArrayList<>();
        int contador = 0;
        List<String> galeria = imagenProductoMBean.obtenerImagenesCatalogo(item.getReferencia());
        if (galeria != null && !galeria.isEmpty()) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.imagen");
            if (url != null) {
                url = String.format(url, item.getReferencia());
            }

            for (String s : galeria) {
                imagenes.add(new String[]{url + s, String.valueOf(contador)});
                contador++;
            }
        }

        /*2. Se obtiene la plantilla de la referencia*/
        imagenes.add(new String[]{imagenProductoMBean.obtenerPlantilla(item.getReferencia()), String.valueOf(contador)});
    }

    public void asignarUbicacion() {
        if (item.getCantidadUbicaciones() < item.getCantidadUsada()) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < item.getUbicaciones().size(); i++) {
                if (item.getUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    if (item.getUbicaciones().get(i).getOnHandQtyUsado() < item.getUbicaciones().get(i).getOnHandQty()) {
                        item.getUbicaciones().get(i).setOnHandQtyUsado(item.getUbicaciones().get(i).getOnHandQtyUsado() + 1);
                        items.set(posicionItem, item);
                        item.setCantidadUbicaciones(item.getCantidadUbicaciones() + 1);
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
        if (item.getCantidadUbicaciones() > 0) {
            Integer ubicacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("ubicacion"));

            for (int i = 0; i < item.getUbicaciones().size(); i++) {
                if (item.getUbicaciones().get(i).getUbicacion().equals(ubicacion)) {
                    if (item.getUbicaciones().get(i).getOnHandQtyUsado() > 0) {
                        item.getUbicaciones().get(i).setOnHandQtyUsado(item.getUbicaciones().get(i).getOnHandQtyUsado() - 1);
                        items.set(posicionItem, item);
                        item.setCantidadUbicaciones(item.getCantidadUbicaciones() - 1);
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

    public void validarNotaCredito() {
        if (dlgCrearNotaCredito) {
            dlgCrearNotaCredito = false;
        } else {
            dlgCrearNotaCredito = true;
        }
    }

    public void crearNotaCredito() {
        dlgNotaCredito = false;
        dlgCrearNotaCredito = false;

        if (comentario == null || comentario.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un comentario.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un comentario");
            dlgNotaCredito = true;
            return;
        }

        FacturaSAP factura = facturaSAPFacade.findByDocNum(ventasSessionMBean.getNumeroFactura());

        if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
            if (crearNotaCredito(factura.getDocEntry().longValue(), factura.getCardCode(), String.valueOf(factura.getDocNum()), factura.getGroupNum().toString(), factura.getUWuid()) > 0) {
                OrdenVenta orden = ordenVentaFacade.obtenerOrdenVenta(String.valueOf(factura.getDocNum()));

                if (orden != null && orden.getDocEntry() != null && orden.getDocEntry() != 0) {
                    mostrarMensaje("Advertencia", "Recuerde revisar la orden de venta " + orden.getDocNum() + " para que cierre las líneas necesarias.", false, false, true);
                    log.log(Level.WARNING, "Recuerde revisar la orden de venta {0} para que cierre las lineas necesarias", orden.getDocNum());
                }

                limpiar();
            } else {
                return;
            }
        } else if (!ventasSessionMBean.isCreaNotaCredito()) {
            if (crearNotaCredito(0L, ventasSessionMBean.getDocumentoCliente(), documentoRelacionado.toUpperCase(), "-1", "") > 0) {
                limpiar();
            } else {
                return;
            }
        }
    }

    private Long crearNotaCredito(Long docEntry, String cardCode, String docNum, String groupNum, String wuid) {
        SalesDocumentDTO document = new SalesDocumentDTO();

        FacturaSAP factura = facturaSAPFacade.findByDocNum(ventasSessionMBean.getNumeroFactura());

        if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
            if (docEntry != null && docEntry != 0) {
                document.setSalesPerson(factura.getSlpCode().longValue());

                if (factura.getuVendedor1() != null && !factura.getuVendedor1().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor1(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor1()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor2() != null && !factura.getuVendedor2().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor2(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor2()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor3() != null && !factura.getuVendedor3().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor3(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor3()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor4() != null && !factura.getuVendedor4().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor4(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor4()).toString());
                    document.getSalesEmployees().add(dto);
                }
                if (factura.getuVendedor5() != null && !factura.getuVendedor5().isEmpty()) {
                    SalesEmployeeDTO dto = new SalesEmployeeDTO(Double.parseDouble(factura.getuComVend1()), factura.getuVendedor5(), salesPersonFacade.obtenerSlpCode(factura.getuVendedor5()).toString());
                    document.getSalesEmployees().add(dto);
                }
            }
            document.setDocEntry(docEntry);
            document.setRefDocnum(docNum);
            document.setCardCode(cardCode);
            document.setComments("Devolución generada por 360 ");
            if (!docNum.isEmpty()) {
                document.setComments(document.getComments() + " para la factura " + docNum);
            }
            document.setComments(document.getComments() + ". " + comentario);
            document.setPaymentGroupCode(groupNum);
            document.setSource("T");
            document.setSeriesCode(serieFacade.obtenerSerieNotaCredito().getSeries().toString());
            document.setWuid(wuid);
            document.setCreditNoteType("D");
            document.setDesignerCode(factura.getUDiseno());

            List<SalesDocumentLineDTO> detalle = new ArrayList<>();
            List<DetalleFacturaSAP> detFac = detalleFacturaSAPFacade.obtenerDetalleFactura(docEntry.doubleValue());

            int line = 1;
            int cantidad = 0;
            for (NotaCreditoDTO n : itemsAplicables) {
                cantidad = n.getCantidadUsada();

                if (docEntry > 0) {
                    for (DetalleFacturaSAP d : detFac) {
                        SalesDocumentLineDTO dto = new SalesDocumentLineDTO();

                        dto.setItemCode(n.getReferencia());
                        //dto.setPrice(n.getPrice());
                        dto.setWhsCode("9803");
                        dto.setLineNum(line);
                        document.setSalesCostingCode(d.getOcrCode2());

                        if (d.getItemCode().equals(n.getReferencia()) && d.getQuantity().intValue() > 0) {
                            dto.setPrice(d.getPriceAfVAT());
                            dto.setTaxCode(d.getTaxCode());

                            if (d.getDiscPrcnt() != null && d.getDiscPrcnt().intValue() > 0) {
                                dto.setDiscountPercent(d.getDiscPrcnt().doubleValue());
                            }

                            List<SaldoItemInventario> s = saldoItemInventarioFacade.obtenerSaldoAlmacenItemCode("9803", n.getReferencia());
                            if (s != null && s.isEmpty()) {
                                if (d.getQuantity().intValue() >= cantidad) {
                                    dto.setQuantity(cantidad);
                                    dto.setLineNumFV(d.getuLineNumFV());
                                    dto.setGrossBuyPrice(facturaSAPFacade.obtenerCostoReferencia(docEntry.intValue(), n.getReferencia(), d.getWhsCode()));
                                    d.setQuantity(new BigDecimal(d.getQuantity().intValue() - cantidad));
                                    saldoItemInventarioFacade.alterarCostoAlmacen(dto.getGrossBuyPrice(), d.getItemCode(), "9803");

                                    detalle.add(dto);
                                    cantidad = 0;
                                    line++;
                                    break;
                                } else if (d.getQuantity().intValue() < cantidad) {
                                    dto.setQuantity(d.getQuantity().intValue());
                                    dto.setLineNumFV(d.getuLineNumFV());
                                    dto.setGrossBuyPrice(facturaSAPFacade.obtenerCostoReferencia(docEntry.intValue(), n.getReferencia(), d.getWhsCode()));
                                    d.setQuantity(new BigDecimal(0));
                                    saldoItemInventarioFacade.alterarCostoAlmacen(dto.getGrossBuyPrice(), d.getItemCode(), "9803");

                                    detalle.add(dto);
                                    cantidad -= d.getQuantity().intValue();
                                    line++;
                                }
                            } else {
                                mostrarMensaje("Error", "La bodega 9803 no esta completamente vacia.", true, false, false);
                                log.log(Level.SEVERE, "La bodega 9803 no esta completamente vacia");
                                return 0L;
                            }
                        }
                    }
                }
            }

            if (detalle != null && !detalle.isEmpty()) {
                document.setDocumentLines(detalle);

                CreditNotesClient client = new CreditNotesClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                try {
                    GenericRESTResponseDTO res = client.crearNotaCredito(document);

                    if (res.getEstado() == 0) {
                        mostrarMensaje("Error", "No se pudo crear la nota crédito " + res.getMensaje(), true, false, false);
                        log.log(Level.SEVERE, "No se pudo crear la nota credito ", res.getMensaje());
                        return 0L;
                    } else {
                        DevolucionSAP dev = devolucionSAPFacade.find(res.getValor());

                        mostrarMensaje("Éxito", "Nota crédito " + dev.getDocNum() + " creada correctamente.", false, true, false);
                        log.log(Level.SEVERE, "Nota credito creada correctamente");

                        if (documentoRelacionado == null || documentoRelacionado.isEmpty()) {
                            crearSalida(dev.getDocNum(), docNum);
                        } else {
                            finalizarCotizacionNotaCredito("NF");
                        }

                        return dev.getDocNum().longValue();
                    }
                } catch (Exception e) {
                }
            }
        }
        return 0L;
    }

    private void crearSalida(Integer notaCredito, String docNum) {
        GoodsIssueDTO document = new GoodsIssueDTO();

        document.setComments("Doc. creado con B1WS Segun Nota Credito #" + notaCredito + " (DocEntry) para Factura #" + docNum);
        document.setJournalMemo("Salida de mercancia de clientes");
        document.setSeries("26");
        document.setGroupNum(String.valueOf(-1L));
        document.setInvoiceNumber(docNum);
        document.setuOrigen("T");

        if (itemsAplicables != null && !itemsAplicables.isEmpty()) {
            int line = 0;
            for (NotaCreditoDTO n : itemsAplicables) {
                if (n.getCantidadUsada() > 0) {
                    if (n.getUbicaciones() != null && !n.getUbicaciones().isEmpty()) {
                        for (SaldoUbicacionDTO s : n.getUbicaciones()) {
                            if (s.getOnHandQtyUsado() > 0) {
                                GoodsIssueDetailDTO det = new GoodsIssueDetailDTO();

                                det.setAccountCode("91051001");
                                det.setItemCode(n.getReferencia());
                                det.setLineNum(String.valueOf(line));
                                det.setQuantity(String.valueOf(s.getOnHandQtyUsado()));

                                GoodsIssueLocationsDTO loc = new GoodsIssueLocationsDTO();

                                loc.setBinAbs(s.getUbicacion().toString());
                                loc.setQuantity(s.getOnHandQtyUsado().toString());

                                det.addLocation(loc);
                                det.setWhsCode(s.getWhsCode());

                                document.addDetail(det);
                                line++;
                            }
                        }
                    }
                }
            }

            try {
                GoodsIssueClient client = new GoodsIssueClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                GenericRESTResponseDTO res = client.crearSalidaMercancia(document);

                if (res.getEstado() > 0) {
                    log.log(Level.INFO, "Se creo salida de mercancia numero {0}", res.getValor());
                } else {
                    mostrarMensaje("Error", "Ocurrió un error al crear la salida de mercancía para la nota crédito.", true, false, false);
                    log.log(Level.SEVERE, "Ocurrio un error al crear la salida de mercancia para la nota credito. {0}", res.getMensaje());
                    return;
                }
            } catch (Exception e) {
            }
        }
    }

    private void limpiar() {
        totalItemsAsignados = 0;
        posicionItem = 0;
        pasoNotaCredito = 1;
        comentario = null;
        referencia = null;
        documentoRelacionado = null;
        dlgNotaCredito = false;
        dlgCrearNotaCredito = false;
        dlgDocumentoRelacionado = false;
        item = new NotaCreditoDTO();
        cotizacion = new CotizacionWebDTO();
        imagenes = new ArrayList<>();
        items = new ArrayList<>();
        itemsAplicables = new ArrayList<>();

        ventasSessionMBean.setCreaNotaCredito(false);
        ventasSessionMBean.setNumeroFactura(null);
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setDocumentoCliente(null);
        clienteSessionMBean.setClienteDto(new ClienteSAPDTO());
        obtenerNotaCreditoPendiente();
    }

    private void finalizarCotizacionNotaCredito(String estado) {
        CotizacionWeb cot = cotizacionWebFacade.find(cotizacion.getIdCotizacion());

        if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
            cot.setNitCliente(ventasSessionMBean.getDocumentoCliente());
            cot.setEstado(estado);

            cotizacionWebFacade.edit(cot);
            log.log(Level.INFO, "Se cambio el estado a {1} de la cotizacion para nota credito con id {0}", new Object[]{cotizacion.getIdCotizacion(), estado});
        }
    }

    public String cancelarNotaCredito() {
        if (ventasSessionMBean.isCreaNotaCredito() && ventasSessionMBean.getNumeroFactura() != null && ventasSessionMBean.getNumeroFactura() != 0) {
            limpiar();
            return "";
        } else {
            finalizarCotizacionNotaCredito("NC");
            limpiar();
            return "";
        }
    }

    public void cerrarModalDocumentoRelacionado() {
        dlgDocumentoRelacionado = false;
        documentoRelacionado = null;
    }

    public void cancelarCreacionNotaCredito() {
        dlgCrearNotaCredito = false;
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
