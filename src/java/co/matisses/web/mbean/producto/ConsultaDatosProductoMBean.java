package co.matisses.web.mbean.producto;

import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.DetalleImpresionSticker;
import co.matisses.persistence.web.entity.ImpresionSticker;
import co.matisses.persistence.web.entity.Impresora;
import co.matisses.persistence.web.entity.TipoSticker;
import co.matisses.persistence.web.facade.DetalleImpresionStickerFacade;
import co.matisses.persistence.web.facade.ImpresionStickerFacade;
import co.matisses.persistence.web.facade.ImpresoraFacade;
import co.matisses.persistence.web.facade.TipoStickerFacade;
import co.matisses.web.dto.ImpresoraDTO;
import co.matisses.web.dto.ItemInventarioDTO;
import co.matisses.web.dto.ReferenciasDatosProductosDTO;
import co.matisses.web.dto.TipoStickerDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import java.util.Collections;
import java.util.Comparator;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
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
@Named(value = "consultaDatosProductoMBean")
public class ConsultaDatosProductoMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    private static final Logger log = Logger.getLogger(ConsultaDatosProductoMBean.class.getSimpleName());
    private Integer cantidadSticker;
    private Integer idTipoSticker;
    private Integer idImpresora;
    private Integer pasoImpresion;
    private String parametroBusqueda;
    private boolean dlgReferencias = false;
    private boolean dlgSticker = false;
    private boolean permitirImpresion = true;
    private ItemInventarioDTO itemVisible;
    private ImagenProductoMBean imagenProductoMBean;
    private ReferenciasDatosProductosDTO itemSeleccionado;
    private List<ReferenciasDatosProductosDTO> referenciasVisibles;
    private List<String[]> imagenes;
    private List<ItemInventarioDTO> inventario;
    private List<ImpresoraDTO> impresoras;
    private List<TipoStickerDTO> tiposSticker;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ImpresionStickerFacade impresionStickerFacade;
    @EJB
    private DetalleImpresionStickerFacade detalleImpresionStickerFacade;
    @EJB
    private TipoStickerFacade tipoStickerFacade;
    @EJB
    private ImpresoraFacade impresoraFacade;

    public ConsultaDatosProductoMBean() {
        pasoImpresion = 1;
        itemVisible = new ItemInventarioDTO();
        itemSeleccionado = new ReferenciasDatosProductosDTO();
        inventario = new ArrayList<>();
        imagenes = new ArrayList<>();
        referenciasVisibles = new ArrayList<>();
        impresoras = new ArrayList<>();
        tiposSticker = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        imagenProductoMBean = new ImagenProductoMBean();
        obtenerTiposSticker();
        validarImpresoras();
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public ItemInventarioDTO getItemVisible() {
        return itemVisible;
    }

    public void setItemVisible(ItemInventarioDTO itemVisible) {
        this.itemVisible = itemVisible;
    }

    public ReferenciasDatosProductosDTO getItemSeleccionado() {
        return itemSeleccionado;
    }

    public void setItemSeleccionado(ReferenciasDatosProductosDTO itemSeleccionado) {
        this.itemSeleccionado = itemSeleccionado;
    }

    public List<ItemInventarioDTO> getInventario() {
        return inventario;
    }

    public void setInventario(List<ItemInventarioDTO> inventario) {
        this.inventario = inventario;
    }

    public boolean isDlgReferencias() {
        return dlgReferencias;
    }

    public void setDlgReferencias(boolean dlgReferencias) {
        this.dlgReferencias = dlgReferencias;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public Integer getCantidadSticker() {
        return cantidadSticker;
    }

    public void setCantidadSticker(Integer cantidadSticker) {
        this.cantidadSticker = cantidadSticker;
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

    public Integer getPasoImpresion() {
        return pasoImpresion;
    }

    public void setPasoImpresion(Integer pasoImpresion) {
        this.pasoImpresion = pasoImpresion;
    }

    public boolean isDlgSticker() {
        return dlgSticker;
    }

    public void setDlgSticker(boolean dlgSticker) {
        this.dlgSticker = dlgSticker;
    }

    public boolean isPermitirImpresion() {
        return permitirImpresion;
    }

    public void setPermitirImpresion(boolean permitirImpresion) {
        this.permitirImpresion = permitirImpresion;
    }

    public List<ReferenciasDatosProductosDTO> getReferenciasVisibles() {
        return referenciasVisibles;
    }

    public void setReferenciasVisibles(List<ReferenciasDatosProductosDTO> referenciasVisibles) {
        this.referenciasVisibles = referenciasVisibles;
    }

    public List<ImpresoraDTO> getImpresoras() {
        return impresoras;
    }

    public void setImpresoras(List<ImpresoraDTO> impresoras) {
        this.impresoras = impresoras;
    }

    public List<TipoStickerDTO> getTiposSticker() {
        return tiposSticker;
    }

    public void setTiposSticker(List<TipoStickerDTO> tiposSticker) {
        this.tiposSticker = tiposSticker;
    }

    private void validarImpresoras() {
        impresoras.clear();
        List<Impresora> printers = impresoraFacade.obtenerImpresorasSucursal(sessionInfoMBean.getAlmacen());

        if (printers != null && !printers.isEmpty()) {
            for (Impresora i : printers) {
                impresoras.add(new ImpresoraDTO(i.getIdImpresora(), i.getNombreImpresora(), i.getSucursal(), i.getEstadoEjecucion(), i.getCodigoImpresion(), i.getNombreImpresoraServidor(), i.getActivo()));
            }

            Collections.sort(impresoras, new Comparator<ImpresoraDTO>() {
                @Override
                public int compare(ImpresoraDTO t, ImpresoraDTO t1) {
                    return t.getNombreImpresora().compareTo(t1.getNombreImpresora());
                }
            });
            permitirImpresion = true;
        } else {
            permitirImpresion = false;
            mostrarMensaje("Error", "No se encontraron impresoras disponibles para esta sucursal, por lo tanto no se permitirá imprimir.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron impresoras disponibles para esta sucursal, por lo tanto no se permitira imprimir");
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

    public void buscarDatos() {
        inventario = new ArrayList<>();
        if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un parámetro para realizar la busqueda", true, false, false);
            log.log(Level.SEVERE, "debe ingresar un parametro para realizar la busqueda");
            return;
        }

        limpiar();
        List<ItemInventario> items = itemFacade.obtenerReferenciasParametro(parametroBusqueda);
        if (items != null && !items.isEmpty()) {
            for (ItemInventario item : items) {
                boolean existe = false;
                if (inventario.size() > 0) {
                    for (ItemInventarioDTO dto : inventario) {
                        if (dto.getItemCode().equals(item.getItemCode())) {
                            existe = true;
                            break;
                        } else {
                            existe = false;
                        }
                    }
                } else {
                    existe = false;
                }

                if (!existe) {
                    inventario.add(new ItemInventarioDTO(item.getItemCode(), item.getItemName(), item.getFrgnName(),
                            item.getPrchseItem(), item.getSellItem(), item.getInvntItem(), item.getUURefPro(),
                            item.getUUHabDes(), item.getUdescripciona(), item.getUmodelo(), item.getUDescCorta(),
                            item.getuCodigoEan()));
                }
            }

            if (inventario.size() == 1) {
                itemVisible = inventario.get(0);
                asignarFotos(itemVisible.getItemCode());
                agregarUltimasReferencia();
                parametroBusqueda = null;
            } else {
                dlgReferencias = true;
            }
        } else {
            mostrarMensaje("Error", "No se encontro la referencia buscada.", true, false, false);
            log.log(Level.SEVERE, "No se encontro la referencia buscada");
            return;
        }
    }

    public void aplicarReferencia() {
        parametroBusqueda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        buscarDatos();
    }

    private void agregarUltimasReferencia() {
        boolean existe = false;
        for (ReferenciasDatosProductosDTO s : referenciasVisibles) {
            if (s.getReferencia().equals(itemVisible.getItemCode())) {
                if (!s.getEstado().equals("PP")) {
                    s.setEstado("PP");
                    s.setCantidad(1);
                } else {
                    s.setCantidad(s.getCantidad() + 1);
                }
                existe = true;
                break;
            } else {
                existe = false;
            }
        }

        if (!existe) {
            referenciasVisibles.add(0, new ReferenciasDatosProductosDTO(1, itemVisible.getItemCode(), "PP"));
        }
    }

    private void asignarFotos(String referencia) {
        List<String> galeria = imagenProductoMBean.obtenerImagenesCatalogo(referencia);

        if (galeria != null && !galeria.isEmpty()) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.imagen");
            if (url != null) {
                url = String.format(url, referencia);
            }

            int contador = 0;
            for (String s : galeria) {
                imagenes.add(new String[]{url + s, String.valueOf(contador)});
                contador++;
            }
        }
    }

    public void seleccionarReferencia() {
        String ref = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        if (ref != null && !ref.isEmpty()) {
            for (ReferenciasDatosProductosDTO r : referenciasVisibles) {
                if (r.getReferencia().equals(ref)) {
                    itemSeleccionado = r;
                    log.log(Level.INFO, "Se selecciono la referencia [{0}], para impresion de stickers desde Consulta Productos", itemSeleccionado.getReferencia());
                    break;
                }
            }
        }
    }

    public void modificarCantidadReferencia() {
        if (itemSeleccionado != null) {
            for (ReferenciasDatosProductosDTO r : referenciasVisibles) {
                if (r.getReferencia().equals(itemSeleccionado.getReferencia())) {
                    if (!r.getEstado().equals("PP")) {
                        r.setEstado("PP");
                        r.setCantidad(itemSeleccionado.getCantidad());
                        log.log(Level.INFO, "Se modifico la cantidad de la referencia [{0}], para impresion de stickers desde Consulta Productos", itemSeleccionado.getReferencia());
                    } else {
                        r.setCantidad(itemSeleccionado.getCantidad());
                        log.log(Level.INFO, "Se modifico la cantidad de la referencia [{0}], para impresion de stickers desde Consulta Productos", itemSeleccionado.getReferencia());
                    }
                    break;
                }
            }
        }
    }

    public void eliminarReferencia() {
        if (itemSeleccionado != null) {
            for (ReferenciasDatosProductosDTO r : referenciasVisibles) {
                if (r.getReferencia().equals(itemSeleccionado.getReferencia())) {
                    if (r.getCantidad() > 1) {
                        r.setCantidad(r.getCantidad() - 1);
                        log.log(Level.INFO, "Se quito una unidad de la referencia [{0}], para impresion de stickers desde Consulta Productos", itemSeleccionado.getReferencia());
                    } else {
                        referenciasVisibles.remove(r);
                        log.log(Level.INFO, "Se quito la referencia [{0}], para impresion de stickers desde Consulta Productos", itemSeleccionado.getReferencia());
                    }
                    break;
                }
            }
        }
    }

    public void imprimirReferencia() {
        if (impresoras.size() == 1) {
            idImpresora = impresoras.get(0).getIdImpresora();
        }
        if (itemSeleccionado != null && itemSeleccionado.getReferencia() != null && !itemSeleccionado.getReferencia().isEmpty()) {
            for (ReferenciasDatosProductosDTO r : referenciasVisibles) {
                if (r.getReferencia().equals(itemSeleccionado.getReferencia())) {
                    log.log(Level.INFO, "Se manda a imprimir la referencia [{0}]", r.getReferencia());
                    List<ReferenciasDatosProductosDTO> d = new ArrayList<>();
                    d.add(r);
                    imprimirForzadamente(d);
                    cancelar();
                    break;
                }
            }
        } else {
            imprimirForzadamente(referenciasVisibles);
            cancelar();
        }
    }

    public void cancelar() {
        itemSeleccionado = null;
        idTipoSticker = null;
        idImpresora = null;
        pasoImpresion = 1;
    }

    public void seleccionarTipoSticker() {
        Integer idTipoStickerTmp = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoSticker"));

        if (idTipoStickerTmp != null && !idTipoStickerTmp.equals(idTipoSticker)) {
            idTipoSticker = idTipoStickerTmp;
        } else {
            idTipoSticker = null;
        }
    }

    public void aplicarSiguientePaso() {
        if (impresoras.size() > 1) {
            pasoImpresion++;
        } else {
            idImpresora = impresoras.get(0).getIdImpresora();
            if (itemSeleccionado != null) {
                imprimirReferencia();
            }
        }
    }

    public void aplicarAnteriorPaso() {
        pasoImpresion--;
    }

    public void seleccionarImpresora() {
        Integer idImpresoraTmp = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idImpresora"));

        if (idImpresoraTmp != null && !idImpresoraTmp.equals(idImpresora)) {
            idImpresora = idImpresoraTmp;
        } else {
            idImpresora = null;
        }
    }

    private void limpiar() {
        itemVisible = new ItemInventarioDTO();
        inventario = new ArrayList<>();
        dlgReferencias = false;
        imagenes = new ArrayList<>();
        cantidadSticker = null;
    }

    public void abrirDlgImpresion() {
        if (dlgSticker) {
            dlgSticker = false;
        } else {
            dlgSticker = true;
        }
    }

    private void imprimirForzadamente(List<ReferenciasDatosProductosDTO> d) {
        if (referenciasVisibles != null && !referenciasVisibles.isEmpty()) {
            ImpresionSticker impresion = new ImpresionSticker();

            impresion.setEstado("PP");
            impresion.setFecha(new Date());
            impresion.setSucursal(sessionInfoMBean.getAlmacen());
            impresion.setUsuario(sessionInfoMBean.getUsuario());
            impresion.setIdTipoSticker(new TipoSticker(idTipoSticker));

            try {
                impresionStickerFacade.create(impresion);

                for (ReferenciasDatosProductosDTO r : d) {
                    if (r.getEstado().equals("PP")) {
                        DetalleImpresionSticker detalle = new DetalleImpresionSticker();

                        detalle.setAlmacen(sessionInfoMBean.getAlmacen());
                        detalle.setCantidad(r.getCantidad());
                        detalle.setFecha(new Date());
                        detalle.setIdImpresionSticker(new ImpresionSticker(impresion.getIdImpresionSticker()));
                        detalle.setReferencia(r.getReferencia());

                        try {
                            detalleImpresionStickerFacade.create(detalle);
                            log.log(Level.INFO, "Se agrego detalle de impresion de sticker, cantidad [{0}], de la referencia [{1}]",
                                    new Object[]{r.getCantidad(), r.getReferencia()});
                            r.setEstado("FF");
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Error creando el detalle. Error [{0}]", e.getMessage());
                            return;
                        }
                    }
                }

                impresion.setEstado(impresoraFacade.find(idImpresora).getEstadoEjecucion());

                try {
                    impresionStickerFacade.edit(impresion);
                    log.log(Level.INFO, "Se manda informacion a Taskcentre para que inicie la impresion");
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error modificando la impresion. Error [{0}]", e.getMessage());
                    return;
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error creando la impresion. Error [{0}]", e.getMessage());
                return;
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
