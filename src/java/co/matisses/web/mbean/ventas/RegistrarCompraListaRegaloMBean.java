package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.web.entity.ListaRegalos;
import co.matisses.persistence.web.entity.ProductoListaRegalos;
import co.matisses.persistence.web.facade.ListaRegalosFacade;
import co.matisses.persistence.web.facade.ProductoListaRegalosFacade;
import co.matisses.web.dto.ListaRegalosDTO;
import co.matisses.web.dto.ProductoListaRegalosDTO;
import co.matisses.web.rest.regalos.ProcesoPagosREST;
import co.matisses.web.rest.regalos.dto.ProductoCompraExternaDTO;
import co.matisses.web.rest.regalos.dto.RegistroCompraExternaDTO;
import java.io.Serializable;
import java.util.ArrayList;
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
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author jguisao
 */
@ViewScoped
@Named(value = "registrarCompraListaRegaloMBean")
public class RegistrarCompraListaRegaloMBean implements Serializable {

    private static final Logger log = Logger.getLogger(RegistrarCompraListaRegaloMBean.class.getSimpleName());
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
    private ProcesoPagosREST procesoPagoREST;

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
            log.log(Level.SEVERE, "Debe ingresar un valor en el parametro de busqueda para poder continuar");
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
            log.log(Level.SEVERE, "No se encontraron datos coincidente con la busqueda");
            return;
        }
    }

    public void seleccionarLista() {
        idLista = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idLista"));

        log.log(Level.INFO, "Se selecciono la lista de regalo con id [{0}]", idLista);
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
            log.log(Level.SEVERE, "Debe ingresar un numero de factura");
            return;
        }
        if (idLista == null || idLista == 0) {
            mostrarMensaje("Error", "Debe ingresar una lista de regalos.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar una lista de regalos");
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
                log.log(Level.SEVERE, "No se encontraron items para la lista ingresada");
                return;
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos para la factura y lista ingresadas.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos para la lista [{0}] y factura [{1}] ingresadas", new Object[]{idLista, factura});
            return;
        }
    }

    public void cambiarAccion() {
        agregando = !agregando;
//        if (agregando) {
//            agregando = false;
//        } else {
//            agregando = true;
//        }
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
            FacesContext fc = FacesContext.getCurrentInstance();
            if (fc != null) {
                try {
                    HttpServletRequest httpServletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();
                    procesoPagoREST.registrarCompraOtrosMedios(dto, httpServletRequest);
                    cancelarListaRegalo();
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al notificar una compra externa de lista de regalos. ", e);
                }
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos para guardar.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos para guardar");
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
