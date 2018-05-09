package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.entity.ProcesoProductoCcyga;
import co.matisses.persistence.web.entity.ProductoCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoProductoCcygaFacade;
import co.matisses.persistence.web.facade.ProductoCcygaFacade;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.ItemCcygaDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProcesoProductoDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
 * @author dbotero
 */
@ViewScoped
@Named(value = "productoCcygaMBean")
public class ProductoCcygaMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(ProductoCcygaMBean.class.getSimpleName());
    private int procesoSeleccionado;
    private int productoSeleccionado;
    private int pagina;
    private int totalPaginas;
    private int productosPagina;
    private boolean dlgInfoArticuloVisible = false;
    private boolean btnEliminarDeshabilitado = true;
    private boolean requerimientoNoEditable = false;
    private Integer idProcesoProductoSeleccionado;
    private String filtro = "";
    private String referenciaSeleccionada;
    private String descripcionProducto;
    private String documento;
    private String requerimiento;
    private String comentario;
    private String cliente;
    private String demo;
    private List<Integer> paginas;
    private List<ItemCcygaDTO> articulos;
    private List<ProcesoCcygaDTO> procesos;
    private List<ProductoCcygaDTO> productosReferencia;
    private List<ProcesoProductoDTO> listaProcesoProducto;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ProcesoCcygaFacade procesoFacade;
    @EJB
    private ProductoCcygaFacade productoCcygaFacade;
    @EJB
    private ProcesoProductoCcygaFacade procesoProductoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;

    public ProductoCcygaMBean() {
        pagina = 1;
        totalPaginas = 0;
        productosPagina = 12;
        paginas = new ArrayList<>();
        articulos = new ArrayList<>();
        procesos = new ArrayList<>();
        productosReferencia = new ArrayList<>();
        listaProcesoProducto = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarProductosCcyga();
        cargarProcesosCcyga();

    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public int getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(int productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public String getProducto() {
        if (productoSeleccionado == -1) {
            return "Nuevo";
        } else if (productosReferencia != null && !productosReferencia.isEmpty()) {
            for (ProductoCcygaDTO p : productosReferencia) {
                if (p.getIdProducto().equals(productoSeleccionado)) {
                    return p.getIdProducto().toString();
                }
            }
        }
        return "Seleccione";
    }

    public int getProcesoSeleccionado() {
        return procesoSeleccionado;
    }

    public void setProcesoSeleccionado(int procesoSeleccionado) {
        this.procesoSeleccionado = procesoSeleccionado;
    }

    public String getProceso() {
        if (procesos != null && !procesos.isEmpty()) {
            for (ProcesoCcygaDTO p : procesos) {
                if (p.getIdProceso().equals(procesoSeleccionado)) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getRequerimiento() {
        return requerimiento;
    }

    public void setRequerimiento(String requerimiento) {
        this.requerimiento = requerimiento;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Integer getIdProcesoProductoSeleccionado() {
        return idProcesoProductoSeleccionado;
    }

    public void setIdProcesoProductoSeleccionado(Integer idProcesoProductoSeleccionado) {
        this.idProcesoProductoSeleccionado = idProcesoProductoSeleccionado;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getReferenciaSeleccionada() {
        return referenciaSeleccionada;
    }

    public void setReferenciaSeleccionada(String referenciaSeleccionada) {
        this.referenciaSeleccionada = referenciaSeleccionada;
    }

    public List<ItemCcygaDTO> getArticulos() {
        return articulos;
    }

    public void setArticulos(List<ItemCcygaDTO> articulos) {
        this.articulos = articulos;
    }

    public List<ProcesoCcygaDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoCcygaDTO> procesos) {
        this.procesos = procesos;
    }

    public void ocultarDlgInfoArticulo() {
        dlgInfoArticuloVisible = false;
    }

    public boolean isDlgInfoArticuloVisible() {
        return dlgInfoArticuloVisible;
    }

    public void setDlgInfoArticuloVisible(boolean dlgInfoArticuloVisible) {
        this.dlgInfoArticuloVisible = dlgInfoArticuloVisible;
    }

    public boolean isRequerimientoNoEditable() {
        return requerimientoNoEditable;
    }

    public void setRequerimientoNoEditable(boolean requerimientoNoEditable) {
        this.requerimientoNoEditable = requerimientoNoEditable;
    }

    public boolean isBtnEliminarDeshabilitado() {
        return btnEliminarDeshabilitado;
    }

    public void setBtnEliminarDeshabilitado(boolean btnEliminarDeshabilitado) {
        this.btnEliminarDeshabilitado = btnEliminarDeshabilitado;
    }

    public List<ProductoCcygaDTO> getProductosReferencia() {
        return productosReferencia;
    }

    public void setProductosReferencia(List<ProductoCcygaDTO> productosReferencia) {
        this.productosReferencia = productosReferencia;
    }

    public List<ProcesoProductoDTO> getListaProcesoProducto() {
        return listaProcesoProducto;
    }

    public void setListaProcesoProducto(List<ProcesoProductoDTO> listaProcesoProducto) {
        this.listaProcesoProducto = listaProcesoProducto;
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

    public void setProductosPagina(int productosPagina) {
        this.productosPagina = productosPagina;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    private void cargarProcesosCcyga() {
        procesos = new ArrayList<>();
        List<ProcesoCcyga> entidades = procesoFacade.findAll();
        for (ProcesoCcyga entidad : entidades) {
            procesos.add(new ProcesoCcygaDTO(entidad.getIdProceso(), entidad.getNombre()));
        }
        Collections.sort(procesos);
    }

    private void cargarProductosCcyga() {
        articulos.clear();

        int totalRegistros = itemFacade.consultarTotalItemsCcyga(filtro.contains("*") ? baruGenericMBean.completarReferencia(filtro) : filtro);
        totalPaginas = (totalRegistros / productosPagina) + (totalRegistros % productosPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();

        List<Object[]> items = itemFacade.obtenerItemsCcyga(pagina, productosPagina, filtro.contains("*") ? baruGenericMBean.completarReferencia(filtro) : filtro);

        if (items != null && !items.isEmpty()) {
            for (Object[] i : items) {
                articulos.add(new ItemCcygaDTO(null, (String) i[0], (String) i[1], (String) i[2], (Integer) i[3], Boolean.valueOf((String) i[4]), Boolean.valueOf((String) i[5])));
            }
        }
    }

    private void construirListaPaginas() {
        paginas.clear();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    private void buscarProductosReferencia() {
        productosReferencia = new ArrayList<>();
        List<ProductoCcyga> entidades = productoCcygaFacade.consultarProductosCcygaReferencia(referenciaSeleccionada);
        for (ProductoCcyga entidad : entidades) {
            if (entidad.getEstado().equals("PE")) {
                ProductoCcygaDTO dto = new ProductoCcygaDTO();
                dto.setDocumento(entidad.getDocumento());
                dto.setIdProducto(entidad.getIdProducto());
                dto.setReferencia(entidad.getReferencia());
                dto.setRequerimiento(entidad.getRequerimiento());

                for (ProcesoProductoCcyga subentidad : entidad.getProcesoProductoCcygaList()) {
                    if (subentidad.getEstado().equals("PE")) {
                        ProcesoCcygaDTO procesoDTO = new ProcesoCcygaDTO();
                        procesoDTO.setComentario(subentidad.getComentarios());
                        procesoDTO.setIdProceso(subentidad.getIdProceso().getIdProceso());
                        procesoDTO.setNombre(subentidad.getIdProceso().getNombre());
                        procesoDTO.setIdProcesoProducto(subentidad.getIdProcesoProducto());
                        dto.getProcesos().add(procesoDTO);
                    }
                }
                productosReferencia.add(dto);
            }
        }
    }

    private void buscarProcesosProducto() {
        listaProcesoProducto = new ArrayList<>();
        for (ProductoCcygaDTO prod : productosReferencia) {
            for (ProcesoCcygaDTO proc : prod.getProcesos()) {
                ProcesoProductoDTO dtoProc = new ProcesoProductoDTO();
                dtoProc.setIdProcesoProducto(proc.getIdProcesoProducto());
                dtoProc.setComentario(proc.getComentario());
                dtoProc.setIdProceso(proc.getIdProceso());
                dtoProc.setIdProducto(prod.getIdProducto());
                dtoProc.setNombreProceso(proc.getNombre());

                listaProcesoProducto.add(dtoProc);
            }
        }
    }

    private void buscarInformacionProducto() {
        for (ItemCcygaDTO dto : articulos) {
            if (dto.getReferencia().equals(referenciaSeleccionada)) {
                buscarProductosReferencia();
                buscarProcesosProducto();
                return;
            }
        }
    }

    public void seleccionarProducto() {
        String referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        String descProducto = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("descripcionProducto");
        cliente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cliente");
        demo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("demo");
        log.log(Level.INFO, "Aumentando la cantidad de la referencia [{0}]", referencia);
        referenciaSeleccionada = referencia;
        descripcionProducto = descProducto;

        buscarInformacionProducto();
    }

    public void filtrarProductos() {
        log.log(Level.INFO, "Buscando productos que coincidan con el parametro: [{0}]", filtro);
        pagina = 1;
        cargarProductosCcyga();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        initialize();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        initialize();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            initialize();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            initialize();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        initialize();
    }

    public void guardarProducto() {
        log.log(Level.INFO, "Procesando accion de guardar para producto [{0}]", productoSeleccionado);
        if (procesoSeleccionado == 0) {
            mostrarMensaje("Error", "Seleccione un proceso para agregar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un proceso para agregar");
            return;
        }
        if (productoSeleccionado == 0) {
            mostrarMensaje("Error", "Seleccione un producto para poder agregar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un producto para poder agregar");
            return;
        }
        if (Boolean.valueOf(cliente) && ((documento == null || documento.isEmpty()) && (productoSeleccionado <= 0))) {
            mostrarMensaje("Error", "Ingrese un documento para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un documento para poder continuar");
            return;
        }

        ProductoCcyga entidad;
        if (productoSeleccionado == -1) {
            ItemInventario item = itemFacade.getItemBasicInfo(referenciaSeleccionada);
            //Crear un nuevo registro de producto ccyga
            entidad = new ProductoCcyga();
            entidad.setDocumento(documento);
            entidad.setReferencia(referenciaSeleccionada);
            entidad.setRequerimiento(requerimiento);
            entidad.setDescripcion(item.getItemName());
            entidad.setRefProveedor(item.getUURefPro());
            entidad.setEstado("PE");
            entidad.setCliente(Boolean.valueOf(cliente));
            entidad.setDemo(Boolean.valueOf(demo));

            ProcesoCcyga procesoCcyga = new ProcesoCcyga(procesoSeleccionado);

            ProcesoProductoCcyga procesoProductoCcyga = new ProcesoProductoCcyga();
            procesoProductoCcyga.setComentarios(comentario);
            procesoProductoCcyga.setIdProceso(procesoCcyga);
            procesoProductoCcyga.setIdProducto(entidad);
            procesoProductoCcyga.setEstado("PE");

            List<ProcesoProductoCcyga> procesosProducto = new ArrayList<>();
            procesosProducto.add(procesoProductoCcyga);

            entidad.setProcesoProductoCcygaList(procesosProducto);

            try {
                productoCcygaFacade.create(entidad);
                limpiarDlg(false);
                buscarProductosReferencia();
                buscarProcesosProducto();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al guardar el registro. " + e.getMessage(), true, false, false);
                log.log(Level.SEVERE, "Ocurrió un error al guardar el registro. [{0}]", e.getMessage());
                return;
            }
        } else {

            //Valida que el producto no tenga ya configurado el proceso que se va a agregar
            try {
                for (ProcesoProductoCcyga proceso : procesoProductoFacade.buscarPorProducto(productoSeleccionado)) {
                    if (proceso.getIdProceso().getIdProceso().equals(procesoSeleccionado)) {
                        mostrarMensaje("Error", "El producto seleccionado ya tiene asociado el proceso " + procesoSeleccionado, true, false, false);
                        log.log(Level.SEVERE, "El producto seleccionado ya tiene asociado el proceso {0}", procesoSeleccionado);
                        return;
                    }
                }
            } catch (Exception e) {
            }

            ProcesoCcyga procesoCcyga = new ProcesoCcyga(procesoSeleccionado);
            ProcesoProductoCcyga procesoProductoCcyga = new ProcesoProductoCcyga();
            procesoProductoCcyga.setComentarios(comentario);
            procesoProductoCcyga.setIdProceso(procesoCcyga);
            procesoProductoCcyga.setIdProducto(new ProductoCcyga(productoSeleccionado));
            procesoProductoCcyga.setEstado("PE");

            try {
                procesoProductoFacade.create(procesoProductoCcyga);
                limpiarDlg(false);
                buscarProductosReferencia();
                buscarProcesosProducto();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al modificar el registro. " + e.getMessage(), true, false, false);
                log.log(Level.SEVERE, "Ocurrió un error al modificar el registro. [{0}]", e.getMessage());
                return;
            }
        }
    }

    public void limpiarDlg(boolean borrarReferencia) {
        if (borrarReferencia) {
            referenciaSeleccionada = null;
        }
        documento = null;
        requerimiento = null;
        comentario = null;
        procesoSeleccionado = 0;
        productoSeleccionado = 0;
        idProcesoProductoSeleccionado = null;
        btnEliminarDeshabilitado = true;
        requerimientoNoEditable = false;
    }

    public void eliminarProducto() {
        log.log(Level.INFO, "Eliminando el proceso [{0}] del producto [{1}]", new Object[]{procesoSeleccionado, productoSeleccionado});
        ProductoCcyga entidad = productoCcygaFacade.find(productoSeleccionado);
        int posicion = 0;
        while (posicion < entidad.getProcesoProductoCcygaList().size()) {
            ProcesoProductoCcyga procesoProducto = entidad.getProcesoProductoCcygaList().get(posicion);
            if (procesoProducto.getIdProceso().getIdProceso().equals(procesoSeleccionado)) {
                log.log(Level.INFO, "El proceso a eliminar se encuentra en la posicion [{0}], nombre [{1}]", new Object[]{posicion, procesoProducto.getIdProceso().getNombre()});
                break;
            }
            posicion++;
        }

        try {
            ProcesoProductoCcyga procesoProductoEntidad = entidad.getProcesoProductoCcygaList().get(posicion);
            procesoProductoFacade.eliminarProcesoProducto(procesoProductoEntidad.getIdProcesoProducto());
            buscarInformacionProducto();
            btnEliminarDeshabilitado = true;
            limpiarDlg(false);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo eliminar el producto.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al modificar el producto. ", e);
            return;
        }
    }

    public void finalizarProceso() {
        log.log(Level.INFO, "Finalizando proceso [{0}] producto [{1}]", new Object[]{procesoSeleccionado, productoSeleccionado});
        //consultar si este proceso se encuentra abierto para este producto
        ProcesoProductoCcyga entidadProcesoProducto = procesoProductoFacade.buscarPorProcesoyProducto(procesoSeleccionado, productoSeleccionado);

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
            buscarProductosReferencia();
            limpiarDlg(false);
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al cambiar el estado del proceso. " + e.getMessage(), true, false, false);
            log.log(Level.SEVERE, "Ocurrió un error al cambiar el estado del proceso. [{0}]", e.getMessage());
            return;
        }
    }

    public void finalizarProducto() {
        productoSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("producto"));
        log.log(Level.INFO, "Finalizando los procesos para el producto [{0}]", productoSeleccionado);
        //consultar los procesos sin terminar para el producto
        List<ProcesoProductoCcyga> listaProcesos = procesoProductoFacade.buscarPorProducto(productoSeleccionado);
        //Recorre la lista de procesos para validar que ninguno tenga labores abiertas
        for (ProcesoProductoCcyga entidadProcesoProducto : listaProcesos) {
            if (entidadProcesoProducto.getEstado().equals("PE")) {
                List<LaborCcyga> laboresAbiertas = laborFacade.obtenerLaboresPorProceso(entidadProcesoProducto.getIdProcesoProducto());
                if (laboresAbiertas.isEmpty()) {
                    log.log(Level.SEVERE, "No se puede finalizar el proceso [{0}] porque no tiene labores registradas. Registre una labor o elimine el proceso",
                            entidadProcesoProducto.getIdProceso().getNombre());
                    mostrarMensaje("Error", "No se puede finalizar el proceso " + entidadProcesoProducto.getIdProceso().getNombre()
                            + " que no tiene labores registradas. Registre una labor o elimine el proceso", true, false, false);
                    return;
                }
                for (LaborCcyga entidadLabor : laboresAbiertas) {
                    if (entidadLabor.getHoraFin() == null) {
                        log.log(Level.SEVERE, "No se puede finalizar el proceso [{0}] porque tiene labores sin finalizar por parte del empleado con codigo [{1}]",
                                new Object[]{entidadProcesoProducto.getIdProceso().getNombre(), entidadLabor.getCodRevisado()});
                        mostrarMensaje("Error", "No se puede finalizar el proceso " + entidadProcesoProducto.getIdProceso().getNombre()
                                + " porque tiene labores sin finalizar por parte del empleado con codigo " + entidadLabor.getCodRevisado()
                                + ". Finalice las labores iniciadas antes de continuar", true, false, false);
                        return;
                    }
                }
            }
        }

        //Actualiza el estado de los procesos asociados al producto
        for (ProcesoProductoCcyga entidadProcesoProducto : listaProcesos) {
            entidadProcesoProducto.setEstado("TE");
            try {
                procesoProductoFacade.edit(entidadProcesoProducto);
            } catch (Exception e) {
                mostrarMensaje("Error", "No se pudo marcar el proceso " + entidadProcesoProducto.getIdProceso().getNombre() + " como terminado. " + e.getMessage(), true, false, false);
                log.log(Level.SEVERE, "No se pudo marcar el proceso {0} como terminado. {1}", new Object[]{entidadProcesoProducto.getIdProceso().getNombre(), e.getMessage()});
                return;
            }
        }
        //Actualiza el estado del producto
        try {
            ProductoCcyga entidadProducto = productoCcygaFacade.find(productoSeleccionado);
            entidadProducto.setEstado("TE");
            productoCcygaFacade.edit(entidadProducto);
        } catch (Exception e) {
            mostrarMensaje("Error", "No se pudo marcar el producto como terminado. " + e.getMessage(), true, false, false);
            log.log(Level.SEVERE, "No se pudo marcar el producto como terminado. [{0}]", e.getMessage());
            return;
        }
        limpiarDlg(false);
        buscarInformacionProducto();
    }

    public void cerrarDialog() {
        dlgInfoArticuloVisible = false;
        limpiarDlg(true);
    }

    public void valorCambio() {

    }

    public void seleccionarProcesoProducto() {
        log.info("Selecciono un proceso-producto para editar");
        Integer nuevoProcesoProducto = -1;

        nuevoProcesoProducto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProcesoProducto"));

        if (nuevoProcesoProducto.equals(idProcesoProductoSeleccionado)) {
            idProcesoProductoSeleccionado = -1;
            limpiarDlg(false);
            return;
        } else {
            idProcesoProductoSeleccionado = nuevoProcesoProducto;
        }

        log.log(Level.INFO, "Mostrando el proceso-producto #[{0}]", idProcesoProductoSeleccionado);
        for (ProductoCcygaDTO prod : productosReferencia) {
            for (ProcesoCcygaDTO proc : prod.getProcesos()) {
                if (proc.getIdProcesoProducto().equals(idProcesoProductoSeleccionado)) {
                    procesoSeleccionado = proc.getIdProceso();
                    comentario = proc.getComentario();
                    productoSeleccionado = prod.getIdProducto();
                    documento = prod.getDocumento();
                    requerimiento = prod.getRequerimiento();

                    btnEliminarDeshabilitado = false;
                    return;
                }
            }
        }
    }

    public void seleccionarProceso() {
        procesoSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("proceso"));
    }

    public void seleccionarProductos() {
        productoSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("producto"));
    }

    public void seleccionarProductoLista() {
        if (productoSeleccionado != -1) {
            requerimientoNoEditable = true;
            for (ProductoCcygaDTO dto : productosReferencia) {
                if (dto.getIdProducto().equals(productoSeleccionado)) {
                    requerimiento = dto.getRequerimiento();
                    documento = dto.getDocumento();
                    break;
                }
            }
        } else {
            requerimientoNoEditable = false;
            requerimiento = "";
        }
    }

    public String[] generarDocumento(Integer id) {
        log.log(Level.INFO, "Generando rotulo para producto [{0}]", productoSeleccionado);
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(null);
        print.setCopias(1);
        print.setDocumento("rotuloCcyga");
        print.setId(id);
        print.setImprimir(false);
        print.setSucursal("9901");
        print.setDocumentosRelacionados(null);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (res.getValor() == null || new File(res.getMensaje()).exists()) {
                log.log(Level.INFO, "Se pudo generar el documento para el id [{0}]", id);
                return new String[]{res.getMensaje(), id + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
        }
        return null;
    }

    public String abrirPdf() {
        if (productoSeleccionado != 0) {
            String document = "rotuloCcyga";
            String url = applicationMBean.obtenerValorPropiedad("url.web.logistica") + document + "/" + productoSeleccionado;
            if (new File(applicationMBean.obtenerValorPropiedad("url.folder.logistica") + document + File.separator + productoSeleccionado + ".pdf").exists()) {
                return "openRuta('" + url + ".pdf');";
            } else {
                String[] rotulo = generarDocumento(productoSeleccionado);
                if (rotulo != null) {
                    try {
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "No se pudo generar la URL para el documento");
                        return "";
                    }
                } else {
                    log.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    public void duplicarProducto() {
        productoSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("producto"));

        if (productoSeleccionado != 0) {
            ProductoCcyga p = productoCcygaFacade.find(productoSeleccionado);

            if (p != null && p.getIdProducto() != null && p.getIdProducto() != 0) {
                ProductoCcyga pCopia = new ProductoCcyga();

                pCopia.setCliente(p.getCliente());
                pCopia.setDemo(p.getDemo());
                pCopia.setDescripcion(p.getDescripcion());
                pCopia.setDocumento(p.getDocumento());
                pCopia.setEstado("PE");
                pCopia.setRefProveedor(p.getRefProveedor());
                pCopia.setReferencia(p.getReferencia());
                pCopia.setRequerimiento(p.getRequerimiento());

                try {
                    productoCcygaFacade.create(pCopia);
                    log.log(Level.INFO, "Se creo copia del producto con id [{0}], id producto copia [{1}]", new Object[]{p.getIdProducto(), pCopia.getIdProducto()});
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al duplicar el producto seleccionado. Error [{0}]", e.getMessage());
                    mostrarMensaje("Error", "No se pudo crear la copia del producto seleccionado.", true, false, false);
                    return;
                }

                for (ProcesoProductoCcyga ppc : procesoProductoFacade.buscarPorProducto(p.getIdProducto())) {
                    ProcesoProductoCcyga ppcCopia = new ProcesoProductoCcyga();

                    ppcCopia.setComentarios(ppc.getComentarios());
                    ppcCopia.setEstado("PE");
                    ppcCopia.setIdProceso(ppc.getIdProceso());
                    ppcCopia.setIdProducto(pCopia);

                    try {
                        procesoProductoFacade.create(ppcCopia);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Error al copiar los procesos del producto seleccionado. Error [{0}]", e.getMessage());
                        mostrarMensaje("Error", "No se pudo crear copia de los procesos del producto seleccionado", true, false, false);
                        return;
                    }
                }
                buscarProductosReferencia();
                limpiarDlg(false);
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
