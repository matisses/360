//package co.matisses.web.mbean;
//
//import co.matisses.b1ws.client.items.ItemsServiceConnector;
//import co.matisses.b1ws.items.Item;
//import co.matisses.b1ws.items.ItemsServiceSoap;
//import co.matisses.persistence.web.entity.ActualizacionPrecios;
//import co.matisses.persistence.web.facade.ActualizacionPreciosFacade;
//import co.matisses.web.dto.SesionSAPB1WSDTO;
//import java.io.Serializable;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//
///**
// *
// * @author dbotero
// */
//@ViewScoped
//@Named(value = "actualizacionPreciosMBean")
//public class ActualizacionPreciosMBean implements Serializable {
//
//    private static final Logger log = Logger.getLogger(ActualizacionPreciosMBean.class.getSimpleName());
//    private List<ActualizacionPrecios> itemsPendientes;
//    private ActualizacionPrecios ultimo = null;
//    private int productosProcesados = 0;
//    @EJB
//    private ActualizacionPreciosFacade actualizacionPreciosFacade;
//    @Inject
//    private SAPB1WSBean sapB1WSBean;
//    @Inject
//    private BaruApplicationMBean baruApplicationBean;
//
//    public ActualizacionPreciosMBean() {
//    }
//
//    @PostConstruct
//    protected void initialize() {
//        actualizacionEnProceso();
//    }
//
//    public ActualizacionPrecios getUltimo() {
//        return ultimo;
//    }
//
//    public List<ActualizacionPrecios> getItemsPendientes() {
//        return itemsPendientes;
//    }
//
//    public int getProductosProcesados() {
//        return productosProcesados;
//    }
//
//    private void procesarPendientes() {
//        log.log(Level.INFO, "{0} productos pendientes por procesar", itemsPendientes.size());
//        SesionSAPB1WSDTO sesionSap = baruApplicationBean.obtenerSesionSAP("actPrecios");
//        ItemsServiceConnector isc = sapB1WSBean.getItemsServiceConnectorInstance(sesionSap.getIdSesionSAP());
//        ItemsServiceSoap port = sapB1WSBean.getItemsServicePort();
//
//        long tiempoTotal = 0;
//        for (ActualizacionPrecios producto : itemsPendientes) {
//            productosProcesados++;
//            long inicio = System.currentTimeMillis();
//            procesarProducto(producto, isc, port);
//            long fin = System.currentTimeMillis();
//            tiempoTotal += fin - inicio;
//            log.log(Level.INFO, "Se actualizo con exito la referencia {0} en {1}ms. Promedio por producto: {2}ms",
//                    new Object[]{producto.getReferencia(), fin - inicio, tiempoTotal / productosProcesados});
//        }
//    }
//
//    public void iniciar2() {
//        List<ActualizacionPrecios> tmp = actualizacionPreciosFacade.consultarNoProcesados();
//        itemsPendientes = new ArrayList<>();
//        for (ActualizacionPrecios item : tmp) {
//            if (item.getReferencia().equals("10500000000000000012")) {
//                itemsPendientes.add(item);
//                break;
//            }
//        }
//        procesarPendientes();
//    }
//
//    public void iniciar() {
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //validar tiempo de ejecucion de ultima referencia (minimo 1 minuto)
//        if (actualizacionEnProceso()) {
//            FacesContext.getCurrentInstance().addMessage(null,
//                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ya hay una actualización en curso",
//                            "Hay una actualización en curso (el producto " + ultimo.getReferencia() + " se actualizó el " + sdf.format(ultimo.getActualizado()) + ")"));
//            return;
//        }
//        //consultar productos sin procesar
//        itemsPendientes = actualizacionPreciosFacade.consultarNoProcesados();
//        if (itemsPendientes == null) {
//            return;
//        }
//        procesarPendientes();
//    }
//
//    private void procesarProducto(ActualizacionPrecios producto, ItemsServiceConnector isc, ItemsServiceSoap port) {
//        try {
//            //consulta datos B1WS
//            Item item = isc.getItemByID(producto.getReferencia(), port);
//            //Modifica IVA y precio 1
//            item.setArTaxCode("IGV05");
//            item.setUGrupo("047");
//            for (Item.ItemPrices.ItemPrice itemPrice : item.getItemPrices().getItemPrice()) {
//                if (itemPrice.getPriceList() == 1L) {
//                    itemPrice.setPrice(producto.getPrecio());
//                    break;
//                }
//            }
//            //Guarda cambios
//            isc.updateItem(item, port);
//            //Actualiza registro en base de datos
//            producto.setActualizado(new Date());
//            actualizacionPreciosFacade.edit(producto);
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Ocurrio un error al actualizar el item " + producto.getReferencia() + ". ", e);
//        }
//    }
//
//    public boolean actualizacionEnProceso() {
//        ultimo = actualizacionPreciosFacade.consultarUltimoItemActualizado();
//        return !(ultimo == null || System.currentTimeMillis() - ultimo.getActualizado().getTime() > 5000);
//    }
//
//    public void actualizarEstado() {
//        actualizacionEnProceso();
//    }
//
//    public double obtenerSegundosDiferencia(Date fecha) {
//        return ((double) new Date().getTime() - (double) fecha.getTime()) / 1000;
//    }
//}
