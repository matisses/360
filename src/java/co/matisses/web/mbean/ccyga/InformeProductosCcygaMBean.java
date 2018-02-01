package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.entity.ProcesoProductoCcyga;
import co.matisses.persistence.web.entity.ProductoCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoProductoCcygaFacade;
import co.matisses.persistence.web.facade.ProductoCcygaFacade;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProcesoProductoDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.util.RotuloCcygaPDF;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero Modifica ygil 29-09-2016
 */
@ViewScoped
@Named(value = "informeProductosCcygaMBean")
public class InformeProductosCcygaMBean implements Serializable {

    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(InformeProductosCcygaMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int productosPagina;
    private String orderBy;
    private String sortOrder;
    private String parametroBusqueda;
    private String filtro;
    private boolean dlgProducto;
    private ProductoCcygaDTO producto;
    private ProcesoCcygaDTO proceso;
    private List<Integer> paginas;
    private List<ProductoCcygaDTO> productos;
    private List<ProcesoCcygaDTO> procesos;
    private Map<Integer, Integer> procesosActivos;
    @EJB
    private ProductoCcygaFacade productoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ProcesoProductoCcygaFacade procesoProductoFacade;
    @EJB
    private ProcesoCcygaFacade procesoCcygaFacade;

    public InformeProductosCcygaMBean() {
        pagina = 1;
        totalPaginas = 0;
        productosPagina = 9;
        orderBy = "idProducto";
        sortOrder = "ASC";
        dlgProducto = false;
        producto = new ProductoCcygaDTO();
        proceso = new ProcesoCcygaDTO();
        paginas = new ArrayList<>();
        productos = new ArrayList<>();
        procesos = new ArrayList<>();
        procesosActivos = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarProcesosActivos();
        cargarProductosActivos();
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getProductosPagina() {
        return productosPagina;
    }

    public String getSizePage() {
        return Integer.toString(productosPagina);
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrdenarPor() {
        switch (orderBy) {
            case "idProducto":
                return "Id producto";
            case "referencia":
                return "Referencia";
            case "descripcion":
                return "Descripción";
            default:
                return "IdProducto";
        }
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getFiltro() {
        return filtro;
    }

    public String getFiltroSeleccionado() {
        if (filtro != null && !filtro.isEmpty()) {
            switch (filtro) {
                case "clientes":
                    return "Clientes";
                case "demostracion":
                    return "Demostraciones";
                case "ventas":
                    return "Ventas";
                default:
                    return "Todos";
            }
        }
        return "Todos";
    }

    public boolean isDlgProducto() {
        return dlgProducto;
    }

    public void setDlgProducto(boolean dlgProducto) {
        this.dlgProducto = dlgProducto;
    }

    public ProductoCcygaDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoCcygaDTO producto) {
        this.producto = producto;
    }

    public ProcesoCcygaDTO getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoCcygaDTO proceso) {
        this.proceso = proceso;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<ProductoCcygaDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<ProductoCcygaDTO> productos) {
        this.productos = productos;
    }

    public List<ProcesoCcygaDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoCcygaDTO> procesos) {
        this.procesos = procesos;
    }

    public Map<Integer, Integer> getProcesosActivos() {
        return procesosActivos;
    }

    public void setProcesosActivos(Map<Integer, Integer> procesosActivos) {
        this.procesosActivos = procesosActivos;
    }

    public String getProcesoSeleccionado() {
        if (procesos != null && !procesos.isEmpty()) {
            for (ProcesoCcygaDTO p : procesos) {
                if (p.getIdProceso().equals(proceso.getIdProceso())) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    private void cargarProductosActivos() {
        productos = new ArrayList<>();

        int totalRegistros = productoFacade.consultarTotalRegistrosAbiertos(parametroBusqueda, filtro);
        totalPaginas = (totalRegistros / productosPagina) + (totalRegistros % productosPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        if (totalRegistros <= 0) {
            pagina = 1;
        }
        construirListaPaginas();

        List<ProductoCcyga> entidades = productoFacade.consultarProductosActivos(pagina, productosPagina, orderBy, sortOrder, parametroBusqueda, filtro);
        if (entidades != null && !entidades.isEmpty()) {
            for (ProductoCcyga entidad : entidades) {
                productos.add(gestionarDatos(entidad));
            }
        }
    }

    private void construirListaPaginas() {
        paginas.clear();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    private ProductoCcygaDTO gestionarDatos(ProductoCcyga producto) {
        ProductoCcygaDTO prod = new ProductoCcygaDTO(producto.getIdProducto(), producto.getReferencia(), producto.getDescripcion(),
                producto.getRefProveedor(), producto.getDocumento(), producto.getRequerimiento(), producto.getCliente() == null ? false : producto.getCliente(),
                producto.getDemo() == null ? false : producto.getDemo(), new ArrayList<ProcesoCcygaDTO>());

        for (ProcesoProductoCcyga procesoProducto : producto.getProcesoProductoCcygaList()) {
            ProcesoCcygaDTO procesoProd = new ProcesoCcygaDTO(procesoProducto.getIdProceso().getIdProceso(), procesoProducto.getIdProceso().getNombre(),
                    procesoProducto.getComentarios(), procesoProducto.getIdProcesoProducto(),
                    procesosActivos.containsKey(procesoProducto.getIdProcesoProducto()) ? procesosActivos.get(procesoProducto.getIdProcesoProducto()) : 0,
                    procesoProducto.getIdProceso().getPermiteSimultaneos(), procesoProducto.getIdProceso().getRequiereProducto());

            if (procesoProd.getLaboresActivas() > 0) {
                procesoProd.setEmpleadosLabor(cargarEmpleadosProcesoProducto(procesoProducto.getIdProcesoProducto()));
            }
            prod.getProcesos().add(procesoProd);
        }
        return prod;
    }

    public List<String> cargarEmpleadosProcesoProducto(Integer idProcesoProducto) {
        log.log(Level.INFO, "Buscando los empleados para el proceso-producto [{0}]", idProcesoProducto);

        return laborFacade.obtenerEmpleadosActivosProcesoProducto(idProcesoProducto);
    }

    private void cargarProcesosActivos() {
        procesosActivos = new HashMap<>();
        List<LaborCcyga> labores = laborFacade.obtenerLaboresAbiertas();
        for (LaborCcyga entidad : labores) {
            Integer idProcesoProducto = entidad.getIdProcesoProducto().getIdProcesoProducto();
            Integer ocurrencias = procesosActivos.get(idProcesoProducto);
            if (ocurrencias == null) {
                ocurrencias = 0;
            }
            procesosActivos.put(idProcesoProducto, ocurrencias + 1);
        }
    }

    public void cambiarSizePage() {
        productosPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de productos x pagina a [{0}]", productosPagina);
        pagina = 1;
        cargarProductosActivos();
    }

    public void ordenarProductos() {
        orderBy = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        log.log(Level.INFO, "Ordenando productos CCYGA por [{0}]", orderBy);
        cargarProductosActivos();
    }

    public void filtrarProductos() {
        filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
        log.log(Level.INFO, "Filtrando productos CCYGA por [{0}]", filtro);
        cargarProductosActivos();
    }

    public void alternarOrden() {
        log.log(Level.INFO, "Alternando orden de productos CCYGA. Antes [{0}]", sortOrder);
        if (sortOrder.equals("ASC")) {
            sortOrder = "DESC";
        } else {
            sortOrder = "ASC";
        }
        log.log(Level.INFO, "Ahora [{0}]", sortOrder);
        cargarProductosActivos();
    }

    public void buscar() {
        pagina = 1;
        if (parametroBusqueda != null && parametroBusqueda.contains("*")) {
            parametroBusqueda = baruGenericMBean.completarReferencia(parametroBusqueda);
        }
        log.log(Level.INFO, "Filtrando datos a partir de [{0}]", parametroBusqueda);
        cargarProductosActivos();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        cargarProductosActivos();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        cargarProductosActivos();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            cargarProductosActivos();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            cargarProductosActivos();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        cargarProductosActivos();
    }

    public boolean tieneInventario(String referencia, boolean cliente, boolean demo) {
        int tipoAlmacen = (cliente ? 1 : 0) + (demo ? 2 : 0);
        int saldo = itemFacade.consultarSaldoItemTaller(referencia, tipoAlmacen);
        return saldo > 0;
    }

    public void seleccionarProducto() {
        Integer idProducto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProducto"));
        producto = new ProductoCcygaDTO();
        proceso = new ProcesoCcygaDTO();

        if (idProducto != null && idProducto != 0) {
            for (ProductoCcygaDTO p : productos) {
                if (p.getIdProducto().equals(idProducto)) {
                    producto = p;
                    break;
                }
            }
            if (producto.getIdProducto() != null && producto.getIdProducto() != 0) {
                if (procesos == null || procesos.isEmpty()) {
                    cargarProcesosCcyga();
                }
                dlgProducto = true;
            }
        }
    }

    private void cargarProcesosCcyga() {
        procesos = new ArrayList<>();
        List<ProcesoCcyga> entidades = procesoCcygaFacade.findAll();

        for (ProcesoCcyga entidad : entidades) {
            procesos.add(new ProcesoCcygaDTO(entidad.getIdProceso(), entidad.getNombre()));
        }
        Collections.sort(procesos);
    }

    public void seleccionarProceso() {
        Integer idProceso = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProceso"));

        for (ProcesoCcygaDTO p : producto.getProcesos()) {
            if (p.getIdProcesoProducto().equals(idProceso)) {
                proceso = p;
                break;
            }
        }
    }

    public void seleccionarProcesoCCYGA() {
        proceso.setIdProceso(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("proceso")));
    }

    public void generarPDF() {
        log.log(Level.INFO, "Generando rotulo para prodcuto [{0}]", producto.getIdProducto());
        try {
            List<ProcesoProductoCcyga> entidades = procesoProductoFacade.buscarPorProducto(producto.getIdProducto());
            List<ProcesoProductoDTO> dtos = new ArrayList<>();
            for (ProcesoProductoCcyga entidad : entidades) {
                ProcesoProductoDTO dto = new ProcesoProductoDTO();
                dto.setComentario(entidad.getComentarios());
                dto.setIdProceso(entidad.getIdProceso().getIdProceso());
                dto.setIdProcesoProducto(entidad.getIdProcesoProducto());
                dto.setIdProducto(entidad.getIdProducto().getIdProducto());
                dto.setNombreProceso(entidad.getIdProceso().getNombre());

                dtos.add(dto);
            }

            RotuloCcygaPDF generadorRotulo = new RotuloCcygaPDF();
            generadorRotulo.setDescripcionProducto(producto.getDescripcion());
            generadorRotulo.setProcesos(dtos);

            generadorRotulo.setIdProducto(producto.getIdProducto());
            generadorRotulo.setNroDocumento(producto.getDocumento());
            generadorRotulo.setObservacionesGenerales(producto.getRequerimiento());
            generadorRotulo.setReferencia(producto.getReferencia());
            generadorRotulo.setEsCliente(producto.isCliente());
            generadorRotulo.setEsDemo(producto.isDemo());

            ByteArrayOutputStream bos = generadorRotulo.generar();
            log.log(Level.INFO, "Se recibieron [{0}] bytes en el stream", bos.size());

            FacesContext fc = FacesContext.getCurrentInstance();
            ExternalContext ec = fc.getExternalContext();
            ec.responseReset();
            ec.setResponseContentType("application/pdf");
            ec.setResponseContentLength(bos.size());
            ec.addResponseHeader("Content-Disposition", "attachment; filename=\"" + producto.getIdProducto() + ".pdf\"");

            ec.getResponseOutputStream().write(bos.toByteArray());
            fc.responseComplete();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar y descargar el rotulo de identificacion del producto. ", e);
        }
    }

    public void limpiarDlg() {
        proceso = new ProcesoCcygaDTO();
        cargarProductosActivos();
        dlgProducto = false;
    }

    public void guardarProducto() {
        log.log(Level.INFO, "Procesando accion de guardar para producto [{0}]", producto.getIdProducto());
        if (proceso.getIdProceso() == 0) {
            mostrarMensaje("Error", "Seleccione un proceso para agregar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un proceso para agregar");
            return;
        }

//        ProductoCcyga entidad = productoFacade.find(producto.getIdProducto());
        //Valida que el producto no tenga ya configurado el proceso que se va a agregar
        try {
            for (ProcesoProductoCcyga p : procesoProductoFacade.buscarPorProducto(producto.getIdProducto())) {
                if (p.getIdProceso().getIdProceso().equals(proceso.getIdProceso())) {
                    mostrarMensaje("Error", "El producto seleccionado ya tiene asociado el proceso " + p.getIdProceso().getNombre(), true, false, false);
                    log.log(Level.SEVERE, "El producto seleccionado ya tiene asociado el proceso {0}", p.getIdProceso().getNombre());
                    return;
                }
            }
        } catch (Exception e) {
        }

        ProcesoCcyga procesoCcyga = new ProcesoCcyga(proceso.getIdProceso());
        ProcesoProductoCcyga procesoProductoCcyga = new ProcesoProductoCcyga();
        procesoProductoCcyga.setComentarios(proceso.getComentario());
        procesoProductoCcyga.setIdProceso(procesoCcyga);
        procesoProductoCcyga.setIdProducto(new ProductoCcyga(producto.getIdProducto()));
        procesoProductoCcyga.setEstado("PE");

//        entidad.getProcesoProductoCcygaList().add(procesoProductoCcyga);
        try {
            procesoProductoFacade.create(procesoProductoCcyga);
            proceso = new ProcesoCcygaDTO();

            ProcesoProductoCcyga ppc = procesoProductoFacade.find(procesoProductoCcyga.getIdProcesoProducto());
            producto.getProcesos().add(new ProcesoCcygaDTO(ppc.getIdProceso().getIdProceso(),
                    ppc.getIdProceso().getNombre(), ppc.getComentarios(),
                    ppc.getIdProcesoProducto(), null, ppc.getIdProceso().getPermiteSimultaneos(),
                    ppc.getIdProceso().getRequiereProducto()));
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al modificar el registro. " + e.getMessage(), true, false, false);
            log.log(Level.SEVERE, "Ocurrió un error al modificar el registro. [{0}]", e.getMessage());
            return;
        }
    }

    public void eliminarProducto() {
        log.log(Level.INFO, "Eliminando el proceso [{0}] del producto [{1}]", new Object[]{proceso.getIdProceso(), producto.getIdProducto()});
        ProductoCcyga entidad = productoFacade.find(producto.getIdProducto());
        int posicion = 0;
        while (posicion < entidad.getProcesoProductoCcygaList().size()) {
            ProcesoProductoCcyga procesoProducto = entidad.getProcesoProductoCcygaList().get(posicion);
            if (procesoProducto.getIdProceso().getIdProceso().equals(proceso.getIdProceso())) {
                log.log(Level.INFO, "El proceso a eliminar se encuentra en la posicion [{0}], nombre [{1}]", new Object[]{posicion, procesoProducto.getIdProceso().getNombre()});
                break;
            }
            posicion++;
        }

        try {
            ProcesoProductoCcyga procesoProductoEntidad = entidad.getProcesoProductoCcygaList().get(posicion);
            procesoProductoFacade.eliminarProcesoProducto(procesoProductoEntidad.getIdProcesoProducto());
            producto.getProcesos().remove(proceso);
            proceso = new ProcesoCcygaDTO();
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el producto.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al modificar el producto. ", e);
            return;
        }
    }

    public void finalizarProceso() {
        log.log(Level.INFO, "Finalizando proceso [{0}] producto [{1}]", new Object[]{proceso.getIdProceso(), producto.getIdProducto()});
        //consultar si este proceso se encuentra abierto para este producto
        ProcesoProductoCcyga entidadProcesoProducto = procesoProductoFacade.buscarPorProcesoyProducto(proceso.getIdProceso(), producto.getIdProducto());

        List<LaborCcyga> laboresAbiertas = laborFacade.obtenerLaboresPorProceso(entidadProcesoProducto.getIdProcesoProducto());
        if (laboresAbiertas.isEmpty()) {
            log.log(Level.SEVERE, "No se puede finalizar un proceso que no tiene labores registradas. Registre una labor o elimine el proceso");
            mostrarMensaje("Error", "No se puede finalizar un proceso que no tiene labores registradas. Registre una labor o elimine el proceso", true, false, false);
            return;
        }
        for (LaborCcyga entidadLabor : laboresAbiertas) {
            if (entidadLabor.getHoraFin() == null) {
                log.log(Level.SEVERE, "No se puede finalizar un proceso que tiene labores sin finalizar. Ej: labor [{0}]", entidadLabor.getIdLabor());
                mostrarMensaje("Error", "No se puede finalizar un proceso que tiene labores sin finalizar. Finalice las labores iniciadas antes de continuar", true, false, false);
                return;
            }
        }
        entidadProcesoProducto.setEstado("TE");
        try {
            procesoProductoFacade.edit(entidadProcesoProducto);
            proceso = new ProcesoCcygaDTO();
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al cambiar el estado del proceso. " + e.getMessage(), true, false, false);
            log.log(Level.SEVERE, "Ocurrió un error al cambiar el estado del proceso. [{0}]", e.getMessage());
            return;
        }
    }

    public void cerrarProducto() {
        String idProducto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProducto");
        if (idProducto == null) {
            //TODO: mostrar mensaje error
            return;
        }
        log.log(Level.INFO, "Cerrando producto #[{0}]", idProducto);
        Integer intIdProducto = Integer.valueOf(idProducto);

        //cerrar labores abiertas
        laborFacade.cerrarLaboresAbiertasProducto(intIdProducto);
        log.log(Level.INFO, "Se cerraron las labores del producto");

        //cerrar procesos abiertas
        procesoProductoFacade.cerrarProcesosProducto(intIdProducto);
        log.log(Level.INFO, "Se cerraron todos los procesos del producto");

        //cerrar producto
        productoFacade.cerrarProducto(intIdProducto);

        cargarProductosActivos();
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
