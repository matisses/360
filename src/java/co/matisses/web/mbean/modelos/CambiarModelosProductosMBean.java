package co.matisses.web.mbean.modelos;

import co.matisses.b1ws.client.drafts.DraftsServiceConnector;
import co.matisses.b1ws.client.drafts.GoodsIssueDraftDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueLocationsDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptDetailDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptLocationsDTO;
import co.matisses.b1ws.client.goodsreceipt.GoodsReceiptServiceConnector;
import co.matisses.b1ws.client.items.ItemsServiceConnector;
import co.matisses.b1ws.dto.ItemDTO;
import co.matisses.b1ws.dto.ItemPricesLineDTO;
import co.matisses.persistence.sap.entity.BaruColorArticulo;
import co.matisses.persistence.sap.entity.BaruCombinacionPrincipal;
import co.matisses.persistence.sap.entity.BaruMaterialArticulo;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.PrecioVentaItem;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.BaruColorArticuloFacade;
import co.matisses.persistence.sap.facade.BaruCombinacionPrincipalFacade;
import co.matisses.persistence.sap.facade.BaruMaterialArticuloFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.CambioModelo;
import co.matisses.persistence.web.entity.DetalleCambioModelo;
import co.matisses.persistence.web.facade.CambioModeloFacade;
import co.matisses.persistence.web.facade.DetalleCambioModeloFacade;
import co.matisses.web.dto.CambioModeloDTO;
import co.matisses.web.dto.DetalleCambioModeloDTO;
import co.matisses.web.dto.ItemInventarioDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.apache.commons.lang3.RandomStringUtils;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "cambiarModelosProductosMBean")
public class CambiarModelosProductosMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private SendHTMLEmailMBean emailSender;
    private static final Logger log = Logger.getLogger(CambiarModelosProductosMBean.class.getSimpleName());
    private Integer idCambioModelo;
    private String modeloAnterior;
    private String modeloNuevo;
    private List<ItemInventarioDTO> items;
    private List<DetalleCambioModeloDTO> detalleCambio;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private PrecioVentaItemFacade precioVentaItemFacade;
    @EJB
    private BaruColorArticuloFacade baruColxArtFacade;
    @EJB
    private BaruMaterialArticuloFacade baruMaterialArticuloFacade;
    @EJB
    private BaruCombinacionPrincipalFacade baruCombinacionPrincipalFacade;
    @EJB
    private CambioModeloFacade cambioModeloFacade;
    @EJB
    private DetalleCambioModeloFacade detalleCambioModeloFacade;
    @EJB
    private AlmacenFacade almacenFacade;

    public CambiarModelosProductosMBean() {
        items = new ArrayList<>();
        detalleCambio = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public UserSessionInfoMBean getSessionInfoMBean() {
        return sessionInfoMBean;
    }

    public void setSessionInfoMBean(UserSessionInfoMBean sessionInfoMBean) {
        this.sessionInfoMBean = sessionInfoMBean;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public String getModeloAnterior() {
        return modeloAnterior;
    }

    public void setModeloAnterior(String modeloAnterior) {
        this.modeloAnterior = modeloAnterior;
    }

    public String getModeloNuevo() {
        return modeloNuevo;
    }

    public void setModeloNuevo(String modeloNuevo) {
        this.modeloNuevo = modeloNuevo;
    }

    public List<ItemInventarioDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemInventarioDTO> items) {
        this.items = items;
    }

    public void consultarModelo() {
        detalleCambio = new ArrayList<>();
        items = new ArrayList<>();

        List<ItemInventario> referencias = itemFacade.obtenerReferenciasModelo(modeloAnterior);

        if (referencias != null && !referencias.isEmpty()) {
            for (ItemInventario i : referencias) {
                items.add(new ItemInventarioDTO(i.getItemCode(), i.getItemName(), i.getFrgnName(), i.getItmsGrpCod(), i.getPicturName(),
                        i.getQryGroup2(), i.getExportCode(), i.getIndirctTax(), i.getTaxCodeAR(),
                        i.getTaxCodeAP(), i.getProductSrc(), i.getUGrupo(), i.getUSubGrupo(), null, i.getUUPicPro(), i.getUURefPro(), i.getUURefAduana(),
                        i.getUUDesAduana(), i.getUUCodAran(), i.getUUCarat(), i.getUUColEstru(), i.getUUPalCla(), i.getUUAlt(), i.getUUHabDes(),
                        i.getUUActQn(), i.getUdescripciona(), i.getUCuidados(), i.getUCojineria(), i.getUType(), i.getUmateriales(), i.getUNumpartes(),
                        i.getUfotohd(), i.getUmodelo(), i.getUfotosOK(), i.getUDescCorta(), i.getUModelado(), i.getUreprocesarfotos(), i.getuCodigoEan(),
                        obtenerSaldoStock(i.getItemCode()), i.getuFactorRedondeo(), i.getuColeccion(), i.getuCodigoMarca(),
                        i.getSHeight1(), i.getSWidth1(), i.getSLength1(), i.getSWeight1()));
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos para la busqueda.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos para la busqueda");
            return;
        }
    }

    private Integer obtenerSaldoStock(String referencia) {
        if (referencia != null && !referencia.isEmpty()) {
            return saldoItemInventarioFacade.obtenerSaldoStockCambiarModelo(referencia);
        }
        return 0;
    }

    public void seleccionarItem() {
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item");
        log.log(Level.INFO, "Se selecciono el item {0}", item);

        if (item != null && !item.isEmpty()) {
            for (ItemInventarioDTO i : items) {
                if (i.getItemCode().equals(item)) {
                    if (i.isSeleccionado()) {
                        i.setSeleccionado(false);
                        log.log(Level.INFO, "Se deselecciono el item {0}, para cambio de modelo", item);
                    } else {
                        i.setSeleccionado(true);
                        log.log(Level.INFO, "Se selecciono el item {0}, para cambio de modelo", item);
                    }
                }
            }
        }
    }

    public void cambiarModelo() {
        log.log(Level.INFO, "Inicia cambio de modelo para las referencias seleccionadas del modelo [{0}], al modelo nuevo [{1}]", new Object[]{modeloAnterior, modeloNuevo});

        /*Inicia evaluacion de los items, para verificar que si hayan seleccionado al menos uno*/
        List<ItemInventarioDTO> itemsTmp = new ArrayList<>(items);
        for (int i = 0; i < items.size(); i++) {
            if (!items.get(i).isSeleccionado()) {
                items.remove(i);
                i--;
            }
        }

        if (items.isEmpty()) {
            items = new ArrayList<>(itemsTmp);
            mostrarMensaje("Error", "Debe seleccionar al menos un item para poder cambiar el modelo.", true, false, false);
            log.log(Level.SEVERE, "Debe seleccionar al menos un item para poder cambiar el modelo");
            return;
        }

        /*Si se encuentran items seleccionados, se valida que el usuario haya ingresado el nuevo modelo*/
        if (modeloNuevo == null || modeloNuevo.isEmpty()) {
            items = new ArrayList<>(itemsTmp);
            mostrarMensaje("Error", "Ingrese el modelo nuevo que le quiere asignar a los items seleccionados.", true, false, false);
            log.log(Level.SEVERE, "Ingrese el modelo nuevo que le quiere asignar a los items seleccionados");
            return;
        }

        /*Inicia obtencion de datos completos de cada uno de los items*/
        for (ItemInventarioDTO i : items) {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldosReferenciaCambiarModelo(i.getItemCode());

            if (saldos != null && !saldos.isEmpty()) {
                i.setSaldos(new ArrayList<SaldoItemDTO>());
                for (SaldoItemInventario s : saldos) {
                    i.getSaldos().add(0, new SaldoItemDTO(s.getOnHand().intValue(), s.getAvgPrice().doubleValue(), i.getItemCode(),
                            s.getSaldoItemInventarioPK().getWhsCode().getWhsCode(), new ArrayList<SaldoUbicacionDTO>()));

                    if (ubicacionSAPFacade.validarUbicacionesAlmacen(s.getSaldoItemInventarioPK().getWhsCode().getWhsCode())) {
                        List<SaldoUbicacion> saldosUbicacion = saldoUbicacionFacade.findByItemCodeAndWhsCode(true, i.getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode());

                        for (SaldoUbicacion saldo : saldosUbicacion) {
                            i.getSaldos().get(0).getSaldoUbicacion().add(new SaldoUbicacionDTO(saldo.getAbsEntry(), saldo.getUbicacion().getAbsEntry(), saldo.getFreezeDoc(),
                                    saldo.getOnHandQty().intValue(), saldo.getItemCode(), saldo.getWhsCode(), saldo.getUbicacion().getBinCode(), saldo.getFreezed()));
                        }
                    }
                }
            }
        }

        if (!validarReferencias()) {
            items = new ArrayList<>(itemsTmp);
            return;
        }

        SesionSAPB1WSDTO sesionSAP = applicationMBean.obtenerSesionSAP(sessionInfoMBean.getUsuario());
        CambioModeloDTO cambio = new CambioModeloDTO();
        /*Se bloquean los articulos anteriores*/
        if (!marcarItemsBloqueados()) {
            items = new ArrayList<>(itemsTmp);
            mostrarMensaje("Error", "No se pudieron bloquear los items anteriores.", true, false, false);
            log.log(Level.SEVERE, "No se pudieron bloquear los items anteriores");
            return;
        }
        /*Se crean los items nuevos*/
        if (!crearItemsNuevos(sesionSAP)) {
            items = new ArrayList<>(itemsTmp);
            mostrarMensaje("Error", "No se pudo crear los items nuevos", true, false, false);
            log.log(Level.SEVERE, "No se pudo crear los items nuevos");
            return;
        }
        cambio.setCombinaciones(crearCombinaciones());
        /*Inicia codigo para la salida de la mercancia*/
        cambio.setSalida(crearSalidaMercancia(sesionSAP));
        if (cambio.getSalida() == null) {
            items = new ArrayList<>(itemsTmp);
            return;
        }
        /*Inicia codigo para la entrada de la mercancia nueva*/
        cambio.setEntrada(crearEntradaMercancia(sesionSAP, cambio.getSalida()));
        if (cambio.getEntrada() == null) {
            items = new ArrayList<>(itemsTmp);
            return;
        }

        registrarCambioBD(cambio);

        enviarCorreo();
        limpiar();
        mostrarMensaje("Éxito", "Se hicieron los cambios pertinentes.", false, true, false);
        log.log(Level.INFO, "Se hicieron los cambios pertinentes");
    }

    private boolean validarReferencias() {
        for (ItemInventarioDTO i : items) {
            ItemInventario item = itemFacade.find(baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo()));

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                mostrarMensaje("Error", "La referencia: " + item.getItemCode() + ", ya se encuentra registrada.", true, false, false);
                log.log(Level.SEVERE, "La refeerncia {0}, ya se encuentra registrada", item.getItemCode());
                return false;
            }
        }
        return true;
    }

    private void registrarCambioBD(CambioModeloDTO cambio) {
        CambioModelo c = new CambioModelo();

        c.setCombinaciones(cambio.isCombinaciones());
        c.setEntrada(cambio.getEntrada());
        c.setFecha(new Date());
        c.setModeloAnterior(modeloAnterior);
        c.setModeloNuevo(modeloNuevo);
        c.setSalida(cambio.getSalida());
        c.setUsuario(sessionInfoMBean.getUsuario());
        c.setToken(generarToken());

        try {
            cambioModeloFacade.create(c);
            log.log(Level.INFO, "Se registro un cambio de modelo [{0}]", c.getIdCambioModelo());
            idCambioModelo = c.getIdCambioModelo();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al registrar el cambio de modelo. Error {0}", e.getMessage());
            return;
        }

        for (DetalleCambioModeloDTO d : detalleCambio) {
            DetalleCambioModelo det = new DetalleCambioModelo();

            det.setColores(d.isColores());
            det.setFotosHercules(d.isFotosHercules());
            det.setFotosSAP(d.isFotosSAP());
            det.setIdCambioModelo(new CambioModelo(c.getIdCambioModelo()));
            det.setMateriales(d.isMateriales());
            det.setReferenciaAnterior(d.getReferenciaAnterior());
            det.setReferenciaNueva(d.getReferenciaNueva());

            try {
                detalleCambioModeloFacade.create(det);
                log.log(Level.INFO, "Detalle registrado id {0}", det.getIdDetalleCambioModelo());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al registrar el detalle. Error {0}", e.getMessage());
                return;
            }
        }
    }

    private String generarToken() {
        return RandomStringUtils.randomAlphanumeric(20);
    }

    private boolean marcarItemsBloqueados() {
        if (items != null && !items.isEmpty()) {
            for (ItemInventarioDTO i : items) {
                try {
                    itemFacade.bloquearItem(i.getItemCode());
                } catch (Exception e) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private boolean crearItemsNuevos(SesionSAPB1WSDTO sesionSAP) {
        if (items != null && !items.isEmpty()) {
            for (ItemInventarioDTO i : items) {
                ItemDTO dto = new ItemDTO();

                dto.setItemsGroupCode(i.getItmsGrpCod() != null ? i.getItmsGrpCod().longValue() : null);
                dto.setuCojineria(i.getuCojineria() != null ? i.getuCojineria().longValue() : null);
                dto.setuFactorRedondeo(i.getuFactorRedondeo() != null ? Long.valueOf(i.getuFactorRedondeo()) : null);
                dto.setItemCode(baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo()));
                dto.setItemName(i.getItemName());
                //dto.setBarCode(new Encode128WS().codificar(baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo())));
                dto.setForeignName(i.getFrgnName());
                dto.setProductSource(i.getProductSrc());
                dto.setuGrupo(i.getuGrupo());
                dto.setuSubGrupo(i.getuSubGrupo());
                dto.setuURefPro(i.getuURefPro());
                dto.setuURefAduana(i.getuURefAduana());
                dto.setuUDesAduana(i.getuUDesAduana());
                dto.setuUCodAran(i.getuUCodAran());
                dto.setuUCarat(i.getuUCarat());
                dto.setuUColEstru(i.getuUColEstru());
                dto.setuUPalCla(i.getuUPalCla());
                dto.setuUAlt(i.getuUAlt());
                dto.setuUHabDes(i.getuUHabDes());
                dto.setuDescripciona(i.getUdescripciona());
                dto.setuCuidados(i.getuCuidados());
                dto.setuType(i.getuType());
                dto.setuMateriales(i.getUmateriales());
                dto.setuNumpartes(i.getuNumpartes());
                dto.setuModelo(modeloNuevo);
                dto.setuFotosOK(i.getUfotosOK());
                dto.setuDescCorta(i.getuDescCorta());
                dto.setuModelado(i.getuModelado());
                dto.setuCodigoEan(i.getuCodigoEan());
                dto.setuCodigoMarca(i.getuCodigoMarca());
                dto.setuColeccion(i.getuColeccion());
                dto.setSalesUnitHeight(i.getsHeight1().doubleValue());
                dto.setSalesUnitLength(i.getsLength1().doubleValue());
                dto.setSalesUnitWeight(i.getsWeight1().doubleValue());
                dto.setSalesUnitWidth(i.getsWidth1().doubleValue());
                dto.setuUActQn(i.getuUActQn());
                dto.setuRegistroCambio(new SimpleDateFormat("dd/MM/yyyy") + " " + sessionInfoMBean.getUsuario() + ": Cambio de modelo");

                /*Se asignan los precios actuales*/
                List<PrecioVentaItem> precios = precioVentaItemFacade.obtenerListaPreciosActuales(i.getItemCode());

                if (precios != null && !precios.isEmpty()) {
                    dto.setItemPrices(new ArrayList<ItemPricesLineDTO>());
                    for (PrecioVentaItem p : precios) {
                        ItemPricesLineDTO price = new ItemPricesLineDTO();

                        price.setCurrency(p.getCurrency());
                        price.setPrice(p.getPrice());
                        price.setPriceList(Integer.parseInt(String.valueOf(p.getPrecioVentaItemPK().getPriceList())));

                        dto.getItemPrices().add(price);
                    }
                }

                try {
                    ItemsServiceConnector isc = sapB1WSBean.getItemsServiceConnectorInstance(sesionSAP.getIdSesionSAP());
                    isc.createItem(dto);
                    registrarDetalleCambio(i);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al crear el item. Error [{0}]", e.getMessage());
                    mostrarMensaje("", e.getMessage(), true, false, false);
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private void registrarDetalleCambio(ItemInventarioDTO i) {
        DetalleCambioModeloDTO d = new DetalleCambioModeloDTO();

        d.setColores(asignarColoresItem(i));
        d.setFotosHercules(copiarImagenesHercules(i));
        d.setFotosSAP(copiarImagenesSAP(i));
        d.setMateriales(asignarMaterialesItem(i));
        d.setReferenciaAnterior(i.getItemCode());
        d.setReferenciaNueva(baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo()));

        detalleCambio.add(d);
    }

    private boolean copiarImagenesHercules(ItemInventarioDTO i) {
        boolean exito = false;
        String itemNuevo = baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo());
        /*Codigo de la copia de archivos en hermes*/
        File f = new File(applicationMBean.obtenerValorPropiedad("url.local.folder.image") + i.getItemCode());

        if (f.exists()) {
            File[] archivos = f.listFiles(new FileFilter() {
                @Override
                public boolean accept(File path) {
                    return !path.isHidden();
                }
            });

            if (archivos != null && archivos.length > 0) {
                File directorioNuevo = new File(applicationMBean.obtenerValorPropiedad("url.local.folder.image") + itemNuevo);

                log.log(Level.INFO, "Se creara directorio del nuevo item en la ruta: [{0}]", directorioNuevo.getPath());
                directorioNuevo.mkdir();
                log.log(Level.INFO, "Se creo el directorio");

                /*Se crearan todos los subdirectorios*/
                for (File archivo : archivos) {
                    System.out.println("Archivo encontrado: " + archivo.getPath());
                    File subDirectorio = new File(archivo.getPath().replace(i.getItemCode(), itemNuevo));

                    log.log(Level.INFO, "Se creara subDirectorio del nuevo item en la ruta [{0}]", subDirectorio.getPath());
                    subDirectorio.mkdir();
                    log.log(Level.INFO, "Se creo el subDirectorio");

                    /*Se inserta el contenido*/
                    File[] subArchivos = archivo.listFiles(new FileFilter() {
                        @Override
                        public boolean accept(File file) {
                            return !file.isHidden();
                        }
                    });

                    if (subArchivos != null && subArchivos.length > 0) {
                        for (File subArchivo : subArchivos) {
                            if (subArchivo.isDirectory()) {
                                log.log(Level.INFO, "Se encontro un directorio en la ruta [{0}]", subArchivo.getPath());

                                File archivoDirectorio = new File(subArchivo.getPath().replace(i.getItemCode(), itemNuevo));

                                log.log(Level.INFO, "Se creara directorio de archivos del nuevo item en la ruta [{0}]", archivoDirectorio.getPath());
                                archivoDirectorio.mkdir();
                                log.log(Level.INFO, "Se creo el directorio de archivos");

                                File[] subArchivosDirectorio = subArchivo.listFiles(new FileFilter() {
                                    @Override
                                    public boolean accept(File file) {
                                        return !file.isHidden();
                                    }
                                });

                                if (subArchivosDirectorio != null && subArchivosDirectorio.length > 0) {
                                    for (File subArchivoDirectorio : subArchivosDirectorio) {
                                        try {
                                            InputStream origen = new FileInputStream(subArchivoDirectorio.getPath());
                                            OutputStream destino = new FileOutputStream(subArchivoDirectorio.getPath().replace(i.getItemCode(), itemNuevo));

                                            byte[] buf = new byte[(int) subArchivoDirectorio.length()];
                                            int len;

                                            while ((len = origen.read(buf)) > 0) {
                                                destino.write(buf, 0, len);
                                            }

                                            origen.close();
                                            destino.close();
                                            log.log(Level.INFO, "Se copio la imagen {0}, nueva {1}", new Object[]{subArchivoDirectorio.getPath(),
                                                subArchivoDirectorio.getPath().replace(i.getItemCode(), itemNuevo)});
                                        } catch (Exception e) {
                                            log.log(Level.SEVERE, "Error al copiar los archivos. Error {0}", e.getMessage());
                                            exito = false;
                                        }
                                    }
                                }
                            } else {
                                try {
                                    InputStream origen = new FileInputStream(subArchivo.getPath());
                                    OutputStream destino = new FileOutputStream(subArchivo.getPath().replace(i.getItemCode(), itemNuevo));

                                    byte[] buf = new byte[(int) subArchivo.length()];
                                    int len;

                                    while ((len = origen.read(buf)) > 0) {
                                        destino.write(buf, 0, len);
                                    }

                                    origen.close();
                                    destino.close();
                                    log.log(Level.INFO, "Se copio la imagen {0}, nueva {1}", new Object[]{subArchivo.getPath(), subArchivo.getPath().replace(i.getItemCode(), itemNuevo)});
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, "Error al copiar los archivos. Error {0}", e.getMessage());
                                    exito = false;
                                }
                            }
                        }
                    }
                }
            }
        }
        return exito;
    }

    private boolean copiarImagenesSAP(ItemInventarioDTO i) {
        boolean exito = false;
        String itemNuevo = baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo());

        /*Codigo de la copia de archivos en SAP*/
        List<File> archivos = new ArrayList<>();
        /*Obtener ruta plantilla*/
        File sapPlantilla = new File(applicationMBean.obtenerValorPropiedad("url.local.folder.sap") + i.getItemCode() + "_plantilla.jpg");

        if (sapPlantilla.exists()) {
            archivos.add(sapPlantilla);
        }

        /*Obtener ruta mini*/
        File sapMini = new File(applicationMBean.obtenerValorPropiedad("url.local.folder.sap") + i.getItemCode() + "_mini.jpg");

        if (sapMini.exists()) {
            archivos.add(sapMini);
        }

        /*Obtener ruta hd*/
        File sapHD = new File(applicationMBean.obtenerValorPropiedad("url.local.folder.sap") + i.getItemCode() + "_foto.jpg");

        if (sapHD.exists()) {
            archivos.add(sapHD);
        }

        if (archivos != null && !archivos.isEmpty()) {
            for (File archivo : archivos) {
                try {
                    InputStream origen = new FileInputStream(archivo.getPath());
                    OutputStream destino = new FileOutputStream(archivo.getPath().replace(i.getItemCode(), itemNuevo));

                    byte[] buf = new byte[(int) archivo.length()];
                    int len;

                    while ((len = origen.read(buf)) > 0) {
                        destino.write(buf, 0, len);
                    }

                    origen.close();
                    destino.close();
                    log.log(Level.INFO, "Se copio la imagen {0}, nueva {1}", new Object[]{archivo.getPath(), archivo.getPath().replace(i.getItemCode(), itemNuevo)});
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al copiar los archivos. Error {0}", e.getMessage());
                    exito = false;
                }
            }
        }
        return exito;
    }

    private boolean asignarMaterialesItem(ItemInventarioDTO i) {
        String itemNuevo = baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo());
        List<BaruMaterialArticulo> materiales = baruMaterialArticuloFacade.obtenerMaterialesArticulo(i.getItemCode());

        if (materiales != null && !materiales.isEmpty()) {
            for (BaruMaterialArticulo material : materiales) {
                Integer nextCode = baruMaterialArticuloFacade.obtenerSiguienteCodigo();
                if (nextCode != 0) {
                    BaruMaterialArticulo m = new BaruMaterialArticulo();

                    m.setCode(String.valueOf(nextCode + 1));
                    m.setName(baruGenericMBean.convertirARefCorta(itemNuevo).replace("*", "") + "-" + material.getuMatCode());
                    m.setuItemCode(itemNuevo);
                    m.setuMatCode(material.getuMatCode());

                    try {
                        baruMaterialArticuloFacade.create(m);
                        log.log(Level.INFO, "Se registro el material {0}, articulo {1}", new Object[]{material.getuMatCode(), itemNuevo});
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al registrar el material {0}, para el item {1}. Error {2}", new Object[]{material.getuMatCode(), itemNuevo, e.getMessage()});
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean asignarColoresItem(ItemInventarioDTO i) {
        String itemNuevo = baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo());
        List<BaruColorArticulo> colores = baruColxArtFacade.obtenerColoresArticulo(i.getItemCode());

        if (colores != null && !colores.isEmpty()) {
            for (BaruColorArticulo color : colores) {
                Integer nextCode = baruColxArtFacade.obtenerSiguienteCodigo();
                if (nextCode != 0) {
                    BaruColorArticulo c = new BaruColorArticulo();

                    c.setCode(String.valueOf(nextCode + 1));
                    c.setName(baruGenericMBean.convertirARefCorta(itemNuevo).replace("*", "") + "-" + color.getuColor());
                    c.setuArticulo(itemNuevo);
                    c.setuColor(color.getuColor());
                    c.setuPrincipal(color.getuPrincipal());

                    try {
                        baruColxArtFacade.create(c);
                        log.log(Level.INFO, "Se registro el color {0}, principal {1}, articulo {2}", new Object[]{color.getuColor(), color.getuPrincipal(), itemNuevo});
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al registrar el color {0}, para el item {1}. Error {2}", new Object[]{color.getuColor(), itemNuevo, e.getMessage()});
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }

    private boolean crearCombinaciones() {
        List<BaruCombinacionPrincipal> combinaciones = baruCombinacionPrincipalFacade.obtenerCombinacionesModeloAnterior(modeloNuevo);
        int contador;
        if (combinaciones == null || combinaciones.isEmpty()) {
            contador = 1;
        } else {
            contador = combinaciones.size();
        }

        for (ItemInventarioDTO i : items) {
            String itemNuevo = baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo());

            String identificador = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
            BaruCombinacionPrincipal c = new BaruCombinacionPrincipal();

            c.setCode(identificador);
            c.setName(identificador);
            c.setuModelo(modeloNuevo);
            c.setuReferencia(itemNuevo);
            c.setuOrden(String.valueOf(contador));

            try {
                baruCombinacionPrincipalFacade.create(c);
                log.log(Level.INFO, "Se registro la combinacion {0}, articulo {1}, orden {2}", new Object[]{identificador, itemNuevo, contador});
                contador++;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al registrar la combinacion, para el item {0}. Error {1}", new Object[]{itemNuevo, e.getMessage()});
                return false;
            }
        }
        return true;
    }

    private Integer crearSalidaMercancia(SesionSAPB1WSDTO sesionSAP) {
        if (items != null && !items.isEmpty()) {
            GoodsIssueDTO documento = new GoodsIssueDTO();
            documento.setComments("Se hace salida de articulos, por cambio de modelo");
            documento.setJournalMemo("Salida de mercancia por cambio de modelo");
            documento.setGroupNum(String.valueOf(-1L));
            documento.setSeries("26");

            int contador = 0;
            boolean requiere = false;
            for (ItemInventarioDTO i : items) {
                if (i.getCantidadStock() > 0) {
                    for (SaldoItemDTO s : i.getSaldos()) {
                        if (s.getCantidad() > 0) {
                            GoodsIssueDetailDTO det = new GoodsIssueDetailDTO();
                            det.setItemCode(i.getItemCode());
                            det.setItemName(i.getItemName());
                            det.setLineNum(String.valueOf(contador));
                            det.setQuantity(s.getCantidad().toString());
                            det.setWhsCode(s.getAlmacen());
                            if (s.getSaldoUbicacion() != null && !s.getSaldoUbicacion().isEmpty()) {
                                for (SaldoUbicacionDTO u : s.getSaldoUbicacion()) {
                                    GoodsIssueLocationsDTO ubi = new GoodsIssueLocationsDTO();

                                    ubi.setBinAbs(u.getUbicacion().toString());
                                    ubi.setQuantity(u.getOnHandQty().toString());

                                    det.addLocation(ubi);
                                }
                            }

                            documento.addDetail(det);
                            requiere = true;
                        } else if (!requiere) {
                            requiere = false;
                        }
                        contador++;
                    }
                }
            }

            if (requiere) {
                try {
                    GoodsIssueServiceConnector gisc = sapB1WSBean.getGoodsIssueServiceConnectorInstance(sesionSAP.getIdSesionSAP());
                    Long docEntry = gisc.createDocument(documento);
                    log.log(Level.INFO, "Se creo salida de mercancia por cambio de modelo. DocEntry: {0}", docEntry);
                    return docEntry.intValue();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al crear la salida de mercancia por cambio de modelo. Creando documento preliminar. Error: {0}", e.getMessage());
                    return crearSalidaMercanciaPreliminar(documento, sesionSAP);
                    //mostrarMensaje("Error", "No se pudo realizar la salida de mercancia. Error: " + e.getMessage(), true, false, false);
                    //return null;
                }
            } else {
                return 0;
            }
        }
        return null;
    }

    private GoodsIssueDraftDTO convertDocumentToDraft(GoodsIssueDTO docDto) {
        GoodsIssueDraftDTO draftDoc = new GoodsIssueDraftDTO();
        draftDoc.setComments(docDto.getComments());
        draftDoc.setDocumentType("I");
        draftDoc.setGroupNumber(Long.valueOf(docDto.getGroupNum()));
        draftDoc.setJournalMemo(docDto.getJournalMemo());
        draftDoc.setReference2(null);
        draftDoc.setSeries(Long.valueOf(docDto.getSeries()));
        for (GoodsIssueDetailDTO detDto : docDto.getDetail()) {
            GoodsIssueDraftDTO.GoodsIssueDraftDetailDTO det = draftDoc.new GoodsIssueDraftDetailDTO();
            det.setItemCode(detDto.getItemCode());
            det.setLineNum(Long.valueOf(detDto.getLineNum()));
            det.setQuantity(Double.valueOf(detDto.getQuantity()));
            det.setWhsCode(detDto.getWhsCode());
            det.setAccountCode(detDto.getAccountCode());
            for (GoodsIssueLocationsDTO locDto : detDto.getLocations()) {
                GoodsIssueDraftDTO.GoodsIssueDraftDetailDTO.GoodsIssueDraftLocationsDTO loc = det.new GoodsIssueDraftLocationsDTO();
                loc.setAllowNegativeQuantity("N");
                loc.setBaseLineNum(Long.valueOf(detDto.getLineNum()));
                loc.setBinAbsEntry(Long.valueOf(locDto.getBinAbs()));
                loc.setQuantity(Double.valueOf(locDto.getQuantity()));
                det.addLocation(loc);
            }
            draftDoc.addDetail(det);
        }
        return draftDoc;
    }

    private Integer crearSalidaMercanciaPreliminar(GoodsIssueDTO docDto, SesionSAPB1WSDTO sesionSAP) {
        try {
            DraftsServiceConnector dsc = sapB1WSBean.getDraftsServiceConnectorInstance(sesionSAP.getIdSesionSAP());
            Long docEntry = dsc.createGoodsIssueDraft(convertDocumentToDraft(docDto));
            log.log(Level.INFO, "Se creo salida de mercancia por cambio de modelo. DocEntry: {0}", docEntry);
            return docEntry.intValue() * -1;
        } catch (Exception e) {

            mostrarMensaje("Error", "No se pudo realizar el documento preliminar de salida de mercancia. Error: " + e.getMessage(), true, false, false);
        }
        return null;
    }

    private Integer crearEntradaMercancia(SesionSAPB1WSDTO sesionSAP, Integer numeroSalida) {
        if (items != null && !items.isEmpty()) {
            GoodsReceiptDTO documento = new GoodsReceiptDTO();

            documento.setComments("Se hace entrada de articulos, por cambio de modelo");
            documento.setJournalMemo("Goods Receipt");
            documento.setSeries(69L);
            documento.setOrigen("I");
            documento.setInvoiceNumber(numeroSalida.toString());

            int contador = 0;
            boolean requiere = false;
            for (ItemInventarioDTO i : items) {
                if (i.getCantidadStock() > 0) {
                    for (SaldoItemDTO s : i.getSaldos()) {
                        if (s.getCantidad() > 0) {
                            GoodsReceiptDetailDTO det = new GoodsReceiptDetailDTO();

                            det.setItemCode(baruGenericMBean.completarReferencia(i.getItemCode().substring(0, 3) + "*" + i.getItemNuevo()));
                            det.setItemName(i.getItemName());
                            det.setLineNum(Long.parseLong(String.valueOf(contador)));
                            det.setQuantity(s.getCantidad());
                            det.setWhsCode(s.getAlmacen());
                            det.setPrice(s.getCosto());

                            if (s.getSaldoUbicacion() != null && !s.getSaldoUbicacion().isEmpty()) {
                                for (SaldoUbicacionDTO u : s.getSaldoUbicacion()) {
                                    GoodsReceiptLocationsDTO ubi = new GoodsReceiptLocationsDTO();

                                    ubi.setBinAbs(u.getUbicacion());
                                    ubi.setQuantity(u.getOnHandQty());

                                    det.addLocation(ubi);
                                }
                            }

                            documento.addDetail(det);
                            requiere = true;
                        } else if (!requiere) {
                            requiere = false;
                        }
                        contador++;
                    }
                }
            }

            if (requiere) {
                try {
                    GoodsReceiptServiceConnector gisc = sapB1WSBean.getGoodsReceiptServiceConnectorInstance(sesionSAP.getIdSesionSAP());
                    Long docEntry = gisc.createDocument(documento);
                    log.log(Level.INFO, "Se creo entrada de mercancia por cambio de modelo. DocEntry: {0}", docEntry);
                    return docEntry.intValue();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al crear la entrada de mercancia por cambio de modelo. Error: {0}", e.getMessage());
                    mostrarMensaje("Error", "No se pudo realizar la entrada de mercancia. Error: " + e.getMessage(), true, false, false);
                    return null;
                }
            } else {
                return 0;
            }
        }
        return null;
    }

    public void limpiar() {
        idCambioModelo = null;
        modeloAnterior = null;
        modeloNuevo = null;
        items = null;
        detalleCambio = null;
    }

    private void enviarCorreo() {
        MailMessageDTO mailMessage = new MailMessageDTO();

        mailMessage.setFrom("Cambio Modelo <cambiosmodelo@matisses.co>");
        mailMessage.setSubject("Cambio de modelo");
        mailMessage.addToAddress(applicationMBean.obtenerValorPropiedad("cambioModelo.mail.ToAddress"));
        mailMessage.addCcAddress(applicationMBean.obtenerValorPropiedad("cambioModelo.mail.CcAddress"));

        Map<String, String> params = new HashMap<>();
        params.put("modeloAnterior", modeloAnterior);
        params.put("modeloNuevo", modeloNuevo);
        params.put("totalRefsCambiadas", String.valueOf(detalleCambio.size()));
        params.put("userName", sessionInfoMBean.getUsuario());
        params.put("texto1", detalleCambio.size() > 1 ? "cambiaron" : "cambio");
        params.put("texto2", detalleCambio.size() > 1 ? "referencias" : "referencia");
        params.put("url", applicationMBean.obtenerValorPropiedad("url.resumen.cambioModelo") + completarUrlCambio());

        try {
            emailSender.sendMail(mailMessage, SendHTMLEmailMBean.MessageTemplate.cambiar_modelo, params, null);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible enviar el correo de notificacion para el cambio de modelo.", e);
            return;
        }
    }

    private String completarUrlCambio() {
        if (idCambioModelo != null && idCambioModelo != 0) {
            CambioModelo c = cambioModeloFacade.find(idCambioModelo);

            if (c != null && c.getIdCambioModelo() != null && c.getIdCambioModelo() != 0) {
                return "?idCambio=" + c.getIdCambioModelo() + "&token=" + c.getToken();
            } else {
                return "";
            }
        } else {
            return "";
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
