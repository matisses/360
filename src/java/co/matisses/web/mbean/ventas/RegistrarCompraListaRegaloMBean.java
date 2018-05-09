package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CompraListaRegalos;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.facade.CompraListaRegalosFacade;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.web.bcs.client.SMSServiceClient;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.ListaRegalosDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.MensajeTextoDTO;
import co.matisses.web.dto.ProductoListaRegalosDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.rest.regalos.dto.ProductoCompraExternaDTO;
import co.matisses.web.rest.regalos.dto.RegistroCompraExternaDTO;
import java.io.Serializable;
import java.math.BigDecimal;
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

/**
 *
 * @author jguisao
 */
@ViewScoped
@Named(value = "registrarCompraListaRegaloMBean")
public class RegistrarCompraListaRegaloMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private BaruApplicationMBean applicationBean;
    private static final Logger CONSOLE = Logger.getLogger(RegistrarCompraListaRegaloMBean.class.getSimpleName());
    private Integer idLista;
    private Integer factura;
    private String parametroBusquedaLista;
    private String comentario;
    private boolean dlgListas = false;
    private boolean agregando = true;
    private List<ListaRegalosDTO> listasRegalos;
    private List<ProductoListaRegalosDTO> productos;
    @EJB
    private ListaRegalosFacade listaRegalosFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private ProductoListaRegalosFacade productoListaRegalosFacade;
    @EJB
    private CompraListaRegalosFacade compraListaRegalosFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;

    public RegistrarCompraListaRegaloMBean() {
        listasRegalos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public Integer getFactura() {
        return factura;
    }

    public void setFactura(Integer factura) {
        this.factura = factura;
    }

    public String getParametroBusquedaLista() {
        return parametroBusquedaLista;
    }

    public void setParametroBusquedaLista(String parametroBusquedaLista) {
        this.parametroBusquedaLista = parametroBusquedaLista;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isDlgListas() {
        return dlgListas;
    }

    public void setDlgListas(boolean dlgListas) {
        this.dlgListas = dlgListas;
    }

    public boolean isAgregando() {
        return agregando;
    }

    public void setAgregando(boolean agregando) {
        this.agregando = agregando;
    }

    public List<ListaRegalosDTO> getListasRegalos() {
        return listasRegalos;
    }

    public void setListasRegalos(List<ListaRegalosDTO> listasRegalos) {
        this.listasRegalos = listasRegalos;
    }

    public List<ProductoListaRegalosDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoListaRegalosDTO> productos) {
        this.productos = productos;
    }

    public void obtenerDatosLista() {
        listasRegalos = new ArrayList<>();
        dlgListas = false;

        if (parametroBusquedaLista == null || parametroBusquedaLista.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un valor en el parámetro de búsqueda para poder continuar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar un valor en el parametro de busqueda para poder continuar");
            return;
        }

        List<ListaRegalos> listas = listaRegalosFacade.obtenerListasParametro(parametroBusquedaLista.replace("'", "'''+'"));

        if (listas != null && !listas.isEmpty()) {
            if (listas.size() == 1) {
                idLista = listas.get(0).getIdLista().intValue();
                parametroBusquedaLista = listas.get(0).getNombre();

                obtenerProductoListaRegalo();
            } else {
                for (ListaRegalos l : listas) {
                    listasRegalos.add(new ListaRegalosDTO(l.getIdLista(), l.getCodigo(), l.getNombre(), l.getNombreCreador(), l.getApellidoCreador(), l.getNombreCocreador(), l.getApellidoCocreador()));
                }

                dlgListas = true;
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos coincidentes con la búsqueda.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos coincidente con la busqueda");
            return;
        }
    }

    public void seleccionarLista() {
        idLista = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idLista"));

        CONSOLE.log(Level.INFO, "Se selecciono la lista de regalo con id [{0}]", idLista);
        dlgListas = false;

        for (ListaRegalosDTO l : listasRegalos) {
            if (l.getIdLista() == idLista.longValue()) {
                parametroBusquedaLista = l.getNombre();
                break;
            }
        }
        obtenerProductoListaRegalo();
    }

    public void obtenerProductoListaRegalo() {
        productos = new ArrayList<>();
        if (factura == null || factura == 0) {
            mostrarMensaje("Error", "Debe ingresar un número de factura.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar un numero de factura");
            return;
        }
        if (idLista == null || idLista == 0) {
            mostrarMensaje("Error", "Debe ingresar una lista de regalos.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar una lista de regalos");
            return;
        }
        FacturaSAP invoice = facturaSAPFacade.findByDocNum(factura);

        if (invoice != null && invoice.getDocEntry() != null && invoice.getDocEntry() != 0) {
            List<DetalleFacturaSAP> detalle = detalleFacturaSAPFacade.obtenerDetalleFactura(invoice.getDocEntry().doubleValue());

            List<ProductoListaRegalos> products = productoListaRegalosFacade.consultarPorLista(idLista.longValue());

            if (products != null && !products.isEmpty()) {
                Map<String, Integer> saldos = new HashMap<>();

                for (DetalleFacturaSAP d : detalle) {
                    if (saldos.containsKey(d.getItemCode())) {
                        Integer saldo = saldos.get(d.getItemCode());

                        saldos.replace(d.getItemCode(), saldo + d.getQuantity().intValue());
                    } else {
                        saldos.put(d.getItemCode(), d.getQuantity().intValue());
                    }
                }

                for (ProductoListaRegalos p : products) {
                    if (p.getActivo() && p.getCantidadComprada() < p.getCantidadElegida()) {
                        for (Map.Entry<String, Integer> d : saldos.entrySet()) {
                            if (d.getKey().equals(p.getReferencia())) {
                                ProductoListaRegalosDTO prod = new ProductoListaRegalosDTO();

                                prod.setIdProductoLista(p.getIdProductoLista());
                                prod.setReferencia(p.getReferencia());
                                prod.setCantidadComprada(p.getCantidadComprada());
                                if (d.getValue() >= (p.getCantidadElegida()/* - p.getCantidadComprada()*/)) {
                                    prod.setCantidadElegida(p.getCantidadElegida() - p.getCantidadComprada());
                                } else if ((p.getCantidadElegida()/* - p.getCantidadComprada()*/) > d.getValue()) {
                                    prod.setCantidadElegida(d.getValue());
                                }

                                productos.add(prod);
                                break;
                            }
                        }
                    }
                }
            } else {
                mostrarMensaje("Error", "No se encontraron ítems para la lista ingresada.", true, false, false);
                CONSOLE.log(Level.SEVERE, "No se encontraron items para la lista ingresada");
                return;
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos para la factura y lista ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos para la lista [{0}] y factura [{1}] ingresadas", new Object[]{idLista, factura});
            return;
        }
    }

    public void cambiarAccion() {
        agregando = !agregando;
    }

    public void realizarAccion() {
        String item = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item");

        if (agregando) {
            agregarProductoListaRegalo(item);
        } else {
            quitarProductoListaRegalo(item);
        }
    }

    private void agregarProductoListaRegalo(String item) {
        for (ProductoListaRegalosDTO p : productos) {
            if (item.equals(p.getReferencia()) && (p.getCantidadElegida() >= p.getCantidadSeleccionadaFactura())) {
                p.setCantidadSeleccionadaFactura(p.getCantidadSeleccionadaFactura() + 1);
                break;
            }
        }
    }

    private void quitarProductoListaRegalo(String item) {
        for (ProductoListaRegalosDTO p : productos) {
            if (item.equals(p.getReferencia()) && p.getCantidadSeleccionadaFactura() > 0) {
                p.setCantidadSeleccionadaFactura(p.getCantidadSeleccionadaFactura() - 1);
                break;
            }
        }
    }

    public void cancelarListaRegalo() {
        productos = new ArrayList<>();
        listasRegalos = new ArrayList<>();
        factura = null;
        parametroBusquedaLista = null;
        comentario = null;
    }

    public void guardarDatosWS() {
        RegistroCompraExternaDTO dto = new RegistroCompraExternaDTO();

        dto.setCodigoLista(listaRegalosFacade.find(idLista.longValue()).getCodigo());
        dto.setDocNumFV(factura.toString());
        dto.setMensaje(comentario);
        dto.setProductos(new ArrayList<ProductoCompraExternaDTO>());

        for (ProductoListaRegalosDTO p : productos) {
            if (p.getCantidadSeleccionadaFactura() > 0) {
                ProductoCompraExternaDTO prod = new ProductoCompraExternaDTO();

                prod.setCantidad(p.getCantidadSeleccionadaFactura());
                prod.setReferencia(p.getReferencia());

                dto.getProductos().add(prod);
            }
        }

        if (dto.getProductos() != null && !dto.getProductos().isEmpty()) {
            registrarCompraOtrosMedios(dto);
        } else {
            mostrarMensaje("Error", "No se encontraron datos para guardar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos para guardar");
            return;
        }
    }

    public void registrarCompraOtrosMedios(RegistroCompraExternaDTO registroCompra) {
        CONSOLE.log(Level.INFO, "Registrando compra externa para lista de regalos con datos {0}", registroCompra);
        FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(registroCompra.getDocNumFV()));

        if (fac == null) {
            CONSOLE.log(Level.SEVERE, "No se encontro ninguna factura con el numero {0}", registroCompra.getDocNumFV());
            mostrarMensaje("Error", "No se encontró ninguna factura con el número " + registroCompra.getDocNumFV(), true, false, false);
            return;
        }
        if (fac.getDocStatus() != 'C') {
            CONSOLE.log(Level.SEVERE, "La factura {0} aun se encuentra abierta. Se debe registrar el pago de la misma en SAP antes de poderla asociar con la lista de regalos", registroCompra.getDocNumFV());
            mostrarMensaje("Error", "La factura " + registroCompra.getDocNumFV() + " aún se encuentra abierta. Se debe registrar el pago de la misma en SAP antes de poderla asociar con la lista de regalos",
                    true, false, false);
            return;
        }

        ListaRegalos lista = listaRegalosFacade.consultarListaPorCodigo(registroCompra.getCodigoLista());
        if (lista == null) {
            CONSOLE.log(Level.SEVERE, "No se encontro ninguna lista con el codigo {0}", registroCompra.getCodigoLista());
            mostrarMensaje("Error", "No se encontró ninguna lista con el código.", true, false, false);
            return;
        }

        List<ProductoListaRegalos> products = productoListaRegalosFacade.consultarPorLista(lista.getIdLista());
        if (products == null || products.isEmpty()) {
            CONSOLE.log(Level.SEVERE, "No se encontraron productos para la lista con codigo {0}", registroCompra.getCodigoLista());
            mostrarMensaje("Error", "No se encontraron productos para la lista con código.", true, false, false);
            return;
        }

        //Si se reportaron solo algunos productos de la factura, se revisa cuales y con que cantidad para filtrarlos
        List<DetalleFacturaSAP> detalleFactura = agruparDetalleFactura(fac.getDetalle());
        CONSOLE.log(Level.INFO, "La factura tiene {0} items", detalleFactura.size());

        if (!registroCompra.getProductos().isEmpty()) {
            List<DetalleFacturaSAP> productosReportados = new ArrayList<>();

            for (ProductoCompraExternaDTO productoDTO : registroCompra.getProductos()) {
                for (DetalleFacturaSAP productoFactura : detalleFactura) {
                    if (productoFactura.getItemCode().equals(productoDTO.getReferencia())) {
                        if (productoFactura.getQuantity().intValue() >= productoDTO.getCantidad()) {
                            productoFactura.setQuantity(new BigDecimal(productoDTO.getCantidad()));
                        } else {
                            CONSOLE.log(Level.SEVERE, "No se estan intentando registrar mas unidades de las que  {0}", registroCompra.getCodigoLista());
                            mostrarMensaje("Error", "Se indico que se regalaran " + productoDTO.getCantidad() + " unidades de la referencia " + productoDTO.getReferencia()
                                    + " pero la factura solo tiene " + productoDTO.getCantidad(), true, false, false);
                            return;
                        }
                        productosReportados.add(productoFactura);
                    }
                }
            }
            detalleFactura = productosReportados;
        }

        CONSOLE.log(Level.INFO, "Procesando {0} items", detalleFactura.size());
        int productosDescontados = 0;
        List<CompraListaRegalos> compras = new ArrayList<>();

        //Descuenta los productos de la lista y registra la compra 
        for (DetalleFacturaSAP detalle : detalleFactura) {
            for (ProductoListaRegalos producto : products) {
                if (!producto.getReferencia().equals(detalle.getItemCode())) {
                    continue;
                }
                if (producto.getCantidadElegida() - producto.getCantidadComprada() >= detalle.getQuantity().intValue()) {
                    if (!compraListaRegalosFacade.consultarFacturaProductoReportado(registroCompra.getDocNumFV(), producto)) {
                        productosDescontados++;
                        producto.setCantidadComprada(producto.getCantidadComprada() + detalle.getQuantity().intValue());
                        productoListaRegalosFacade.edit(producto);

                        CompraListaRegalos compra = new CompraListaRegalos();

                        compra.setCantidad(detalle.getQuantity().intValue());
                        compra.setFactura(registroCompra.getDocNumFV());
                        compra.setFecha(new Date());
                        compra.setLista(lista);
                        compra.setMensaje(registroCompra.getMensaje());
                        compra.setProducto(producto);
                        compra.setQuienCompra(null);
                        compra.setTotal(detalle.getPriceAfVAT().longValue() * detalle.getQuantity().intValue());

                        compraListaRegalosFacade.create(compra);
                        compras.add(compra);
                        break;
                    } else {
                        //Ya se registro una compra para ese producto y esa factura. Ignorando el registro
                        CONSOLE.log(Level.WARNING, "El producto {0}({1}) ya fue registrado como comprado con la factura {2}. ",
                                new Object[]{producto.getIdProductoLista(), producto.getReferencia(), registroCompra.getDocNumFV()});
                    }
                } else {
                    break;
                }
            }
        }

        if (compras != null && !compras.isEmpty()) {
            notificarCompra(lista, registroCompra, fac, compras);
        }

        if (productosDescontados == 0) {
            CONSOLE.log(Level.SEVERE, "No se encontraron productos en comun entre la lista y la factura");
            mostrarMensaje("Error", "No se encontraron productos en común entre la lista y la factura.", true, false, false);
            return;
        }

        cancelarListaRegalo();
        mostrarMensaje("Éxito", "Se registro la compra.", true, false, false);
    }
    
    private List<DetalleFacturaSAP> agruparDetalleFactura(List<DetalleFacturaSAP> detalleFactura) {
        CONSOLE.log(Level.INFO, "Agrupando detalle de factura. Tamano inicial: {0}", detalleFactura.size());
        HashMap<String, Integer> referencias = new HashMap<>();
        List<DetalleFacturaSAP> detalleAgrupado = new ArrayList<>();

        for (DetalleFacturaSAP detalle : detalleFactura) {
            if (!referencias.containsKey(detalle.getItemCode())) {
                referencias.put(detalle.getItemCode(), detalleAgrupado.size());
                detalleAgrupado.add(detalle);
            } else {
                DetalleFacturaSAP detalleIncrementar = detalleAgrupado.get(referencias.get(detalle.getItemCode()));
                detalleIncrementar.setQuantity(detalleIncrementar.getQuantity().add(detalle.getQuantity()));
            }
        }

        CONSOLE.log(Level.INFO, "Termino de agrupar detalle de factura. Tamano final: {0}", detalleAgrupado.size());
        return detalleAgrupado;
    }

    private void notificarCompra(ListaRegalos lista, RegistroCompraExternaDTO registroCompra, FacturaSAP fac, List<CompraListaRegalos> compras) {
        if (lista != null && lista.getIdLista() != null && lista.getIdLista() != 0) {
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(fac.getCardCode());
            Map<String, String> params = new HashMap<>();

            params.put("comentario", "Mira los regalos que te han comprado por Lista de Regalos");
            params.put("link", applicationBean.obtenerValorPropiedad("listaregalos.url.web") + lista.getCodigo());

            if (registroCompra.getMensaje() != null && !registroCompra.getMensaje().isEmpty()) {
                StringBuilder sb = new StringBuilder();

                sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                sb.append(registroCompra.getMensaje());
                sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                sb.append("Atentamente,<br/>");
                sb.append("<span style=\"font-style: italic\">");
                sb.append(socio.getNombres());
                sb.append("</span></td></tr>");
                sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que te compraron.</td></tr>");

                params.put("mensaje", sb.toString());
            }

            if (compras != null && !compras.isEmpty()) {
                StringBuilder sb = new StringBuilder();

                sb.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                sb.append("<tr>");
                sb.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                sb.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                sb.append("</tr>");

                for (CompraListaRegalos i : compras) {
                    sb.append("<tr>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                    sb.append(imagenProductoMBean.obtenerUrlProducto(i.getProducto().getReferencia(), true));
                    sb.append("\"></img></td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                    sb.append(genericMBean.obtenerNombreReferencia(i.getProducto().getReferencia()));
                    sb.append("<br/>Ref: ");
                    sb.append(genericMBean.convertirARefCorta(i.getProducto().getReferencia()).replace("*", ""));
                    sb.append("</td>");
                    sb.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                    sb.append(i.getCantidad());
                    sb.append("</td>");
                    sb.append("</tr>");
                }

                sb.append("</table>");
                params.put("tabla", sb.toString());
            } else {
                params.put("tabla", "");
            }

            if (lista.getNotificacionInmediataMailCreador()) {
                params.put("cliente", lista.getNombreCreador());

                enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCreador(),
                        applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
            }
            if (lista.getNotificacionInmediataMailCocreador()) {
                params.put("cliente", lista.getNombreCocreador());

                enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", lista.getCorreoCocreador(),
                        applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, params);
            }

            try {
                SMSServiceClient client = new SMSServiceClient(applicationBean.obtenerValorPropiedad("url.bcs.rest"));

                MensajeTextoDTO sms = new MensajeTextoDTO();

                sms.setCodigoPais("57");
                sms.setIp("192.168.5.56");
                sms.setPruebas(false);
                sms.setUsuario("sonda");

                if (lista.getNotificacionDiariaSmsCreador()) {
                    sms.setDestino(lista.getTelefonoCreador());

                    if (lista.getNotificacionDiariaMailCreador()) {
                        sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                    } else {
                        sms.setMensaje("Hola " + lista.getNombreCreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                    }

                    client.enviarSMS(sms);
                }
                if (lista.getNotificacionDiariaSmsCocreador()) {
                    sms.setDestino(lista.getTelefonoCocreador());

                    if (lista.getNotificacionDiariaMailCocreador()) {
                        sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu correo para mas detalle.");
                    } else {
                        sms.setMensaje("Hola " + lista.getNombreCocreador() + ", te acaban de comprar un regalo. Revisa tu lista para mas detalle.");
                    }

                    client.enviarSMS(sms);
                }
            } catch (Exception e) {
            }

            //Se envia mail de agradecimiento al comprador
            Map<String, String> paramsInvitado = new HashMap<>();

            paramsInvitado.put("comentario", "");

            if (lista.getMensajeAgradecimiento() != null && !lista.getMensajeAgradecimiento().isEmpty()) {
                StringBuilder sb = new StringBuilder();

                sb.append("<tr><td style=\"text-align: justify; padding: 20px; border: 1px solid #DDDDDD; border-radius: 10px;\">");
                sb.append(lista.getMensajeAgradecimiento());
                sb.append("</td></tr><tr><td style=\"text-align: right; padding-bottom: 20px;\"><br/>");
                sb.append("Atentamente,<br/>");
                sb.append("<span style=\"font-style: italic\">");
                sb.append(lista.getNombre().replace("Boda", "").replace("Matrimonio", ""));
                sb.append("</span></td></tr>");
                sb.append("<tr><td style=\"background-color: #EEEEEE; padding: 5px; text-align: left;\">Regalos que compro.</td></tr>");

                paramsInvitado.put("mensaje", sb.toString());

                if (compras != null && !compras.isEmpty()) {
                    StringBuilder sb1 = new StringBuilder();

                    sb1.append("<table style=\"width: 100%; border: 1px solid #DDDDDD; margin-top: 10px; border-collapse: collapse;\">");
                    sb1.append("<tr>");
                    sb1.append("<th style=\"border: 1px solid #DDDDDD;\">Imagen</th>");
                    sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">PRODUCTO</th>");
                    sb1.append("<th style=\"border: 1px solid #DDDDDD; margin: 10px;\">MONTO</th>");
                    sb1.append("</tr>");

                    for (CompraListaRegalos i : compras) {
                        sb1.append("<tr>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; text-align: center; margin: 10px;\"><img src=\"");
                        sb1.append(imagenProductoMBean.obtenerUrlProducto(i.getProducto().getReferencia(), true));
                        sb1.append("\"></img></td>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px;\">");
                        sb1.append(genericMBean.obtenerNombreReferencia(i.getProducto().getReferencia()));
                        sb1.append("<br/>Ref: ");
                        sb1.append(genericMBean.convertirARefCorta(i.getProducto().getReferencia()).replace("*", ""));
                        sb1.append("</td>");
                        sb1.append("<td style=\"border: 1px solid #DDDDDD; margin: 10px; text-align: right;\">");
                        sb1.append(i.getCantidad());
                        sb1.append("</td>");
                        sb1.append("</tr>");
                    }

                    sb1.append("</table>");
                    paramsInvitado.put("tabla", sb1.toString());
                } else {
                    paramsInvitado.put("tabla", "");
                }

                if (socio.getEmail() != null && !socio.getEmail().isEmpty()) {
                    paramsInvitado.put("cliente", socio.getNombres());

                    enviarCorreo("lista_regalos", "Lista Regalos Matisses <listaideal@matisses.co>", "Lista Regalos Matisses", socio.getEmail(),
                            applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos").getTo().get(0), null, paramsInvitado);
                }
            }
        }
    }

    public void enviarCorreo(String template, String from, String subject, String toAddress, String bccAddress, List<String[]> adjuntos, Map<String, String> params) {
        MailMessageDTO dtoMail = new MailMessageDTO();

        dtoMail.setTemplateName(template);
        dtoMail.setParams(params);
        dtoMail.setAttachments(adjuntos);
        dtoMail.setFrom(from);
        dtoMail.setSubject(subject);
        dtoMail.addToAddress(toAddress);
        dtoMail.addBccAddress(bccAddress);

        try {
            SendHtmlEmailClient client = new SendHtmlEmailClient(applicationBean.obtenerValorPropiedad("url.bcs.rest"));

            client.enviarHtmlEmail(dtoMail);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion", e);
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
