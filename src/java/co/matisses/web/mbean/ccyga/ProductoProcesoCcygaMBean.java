package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.web.entity.ProductoCcyga;
import co.matisses.persistence.web.facade.ProductoCcygaFacade;
import co.matisses.web.dto.ProductoCcygaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "productoProcesoCcygaMBean")
public class ProductoProcesoCcygaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ProductoProcesoCcygaMBean.class.getSimpleName());
    private Integer idProceso = -1;
    private List<ProductoCcygaDTO> productos;
    @Inject
    private CcygaSessionMBean ccygaSession;
    @EJB
    private ProductoCcygaFacade productoFacade;

    public ProductoProcesoCcygaMBean() {
        productos = new ArrayList<>();
    }

    private void obtenerProceso() {
        idProceso = ccygaSession.getIdProcesoSeleccionado();
        if (idProceso == null) {
            log.severe("No se encontro el id proceso para filtrar los productos. Redirigiendo a pagina de empleados");
        }
    }

    private void cargarProductosProceso() {
        productos = new ArrayList<>();
        List<ProductoCcyga> entidades = productoFacade.consultarProductosPorProceso(idProceso);
        List<ProductoCcyga> productosActivosEmpleados = productoFacade.consultarProductosActivosEmpleado(ccygaSession.getCodigoRevisado());
        for (ProductoCcyga entidad : entidades) {
            if (!productosActivosEmpleados.contains(entidad)) {
                ProductoCcygaDTO dto = new ProductoCcygaDTO();
                dto.setIdProducto(entidad.getIdProducto());
                dto.setReferencia(entidad.getReferencia());
                dto.setDescripcion(entidad.getDescripcion());
                dto.setRefProveedor(entidad.getRefProveedor());

                productos.add(dto);
            }
        }
        Collections.sort(productos);
    }

    @PostConstruct
    protected void initialize() {
        obtenerProceso();
        cargarProductosProceso();
    }

    public CcygaSessionMBean getCcygaSession() {
        return ccygaSession;
    }

    public void setCcygaSession(CcygaSessionMBean ccygaSession) {
        this.ccygaSession = ccygaSession;
    }

    public List<ProductoCcygaDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCcygaDTO> productos) {
        this.productos = productos;
    }

    public String seleccionarProducto() {
        String idProducto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProducto");
        if (idProducto != null) {
            log.log(Level.INFO, "Seleccionando el producto [{0}] para iniciar labor", idProducto);
            ccygaSession.setIdProductoSeleccionado(Integer.valueOf(idProducto));
            ccygaSession.agregarPaso("producto");
            return "labor";
        } else {
            return null;
        }
    }
}
