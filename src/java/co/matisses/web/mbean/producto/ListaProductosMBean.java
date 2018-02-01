package co.matisses.web.mbean.producto;

import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.entity.ValorActivoFiltro;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.persistence.web.facade.ValorActivoFiltroFacade;
import co.matisses.web.dto.BaruMaterialesDTO;
import co.matisses.web.dto.InventoryItemDTO;
import co.matisses.web.dto.ProgramacionDescuentoDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
@Named(value = "listaProductosMBean")
public class ListaProductosMBean implements Serializable {

    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(ListaProductosMBean.class.getSimpleName());
    private int pagina = 1;
    private int totalPaginas = 0;
    private int productosPagina = 12;
    private int totalRegistros;
    private String filtroTexto;
    private String sortOrder = "DESC";
    private String orderBy = "NUEVO";
    private String plantilla;
    private String tresSesenta;
    private String wow;
    private String descripcion;
    private String referencia;
    private boolean saldoProductos = true;
    private boolean soloConSaldo = true;
    private boolean mostrarFiltros = false;
    private List<Integer> paginas;
    private List<String[]> itemsSimilares;
    private List<String[]> imagenes;
    private List<InventoryItemDTO> productos;
    private List<SaldoItemDTO> saldos;
    private List<BaruMaterialesDTO> materiales;
    private List<ProgramacionDescuentoDTO> descuentos;
    private Map<String, List<String>> filtros;
    private Map<String, Map<String, Boolean>> filtrosIzquierda;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ValorActivoFiltroFacade valorActivoFiltroFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;

    public ListaProductosMBean() {
        totalRegistros = 0;
        paginas = new ArrayList<>();
        itemsSimilares = new ArrayList<>();
        imagenes = new ArrayList<>();
        productos = new ArrayList<>();
        saldos = new ArrayList<>();
        materiales = new ArrayList<>();
        descuentos = new ArrayList<>();
        filtros = new HashMap<>();
        filtrosIzquierda = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarProductos();
        obtenerFiltrosIzquierda();
    }

    public ImagenProductoMBean getImagenProductoMBean() {
        return imagenProductoMBean;
    }

    public void setImagenProductoMBean(ImagenProductoMBean imagenProductoMBean) {
        this.imagenProductoMBean = imagenProductoMBean;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
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

    public void setProductosPagina(int productosPagina) {
        this.productosPagina = productosPagina;
    }

    public int getTotalRegistros() {
        return totalRegistros;
    }

    public void setTotalRegistros(int totalRegistros) {
        this.totalRegistros = totalRegistros;
    }

    public String getFiltroTexto() {
        return filtroTexto;
    }

    public void setFiltroTexto(String filtroTexto) {
        this.filtroTexto = filtroTexto;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(String plantilla) {
        this.plantilla = plantilla;
    }

    public String getTresSesenta() {
        return tresSesenta;
    }

    public void setTresSesenta(String tresSesenta) {
        this.tresSesenta = tresSesenta;
    }

    public String getWow() {
        return wow;
    }

    public void setWow(String wow) {
        this.wow = wow;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public boolean isSaldoProductos() {
        return saldoProductos;
    }

    public void setSaldoProductos(boolean saldoProductos) {
        this.saldoProductos = saldoProductos;
    }

    public boolean isSoloConSaldo() {
        return soloConSaldo;
    }

    public void setSoloConSaldo(boolean soloConSaldo) {
        this.soloConSaldo = soloConSaldo;
    }

    public boolean isMostrarFiltros() {
        return mostrarFiltros;
    }

    public void setMostrarFiltros(boolean mostrarFiltros) {
        this.mostrarFiltros = mostrarFiltros;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<String[]> getItemsSimilares() {
        return itemsSimilares;
    }

    public void setItemsSimilares(List<String[]> itemsSimilares) {
        this.itemsSimilares = itemsSimilares;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public List<InventoryItemDTO> getProductos() {
        return productos;
    }

    public void setProductos(List<InventoryItemDTO> productos) {
        this.productos = productos;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public List<BaruMaterialesDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<BaruMaterialesDTO> materiales) {
        this.materiales = materiales;
    }

    public List<ProgramacionDescuentoDTO> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<ProgramacionDescuentoDTO> descuentos) {
        this.descuentos = descuentos;
    }

    public Map<String, List<String>> getFiltros() {
        return filtros;
    }

    public void setFiltros(Map<String, List<String>> filtros) {
        this.filtros = filtros;
    }

    public Map<String, Map<String, Boolean>> getFiltrosIzquierda() {
        return filtrosIzquierda;
    }

    public void setFiltrosIzquierda(Map<String, Map<String, Boolean>> filtrosIzquierda) {
        this.filtrosIzquierda = filtrosIzquierda;
    }

    public String getOrdenarPor() {
        switch (orderBy) {
            case "REFERENCIA":
                return "referencia";
            case "PRECIO":
                return "precio";
            case "SALDO":
                return "saldo";
            case "NUEVO":
                return "nuevos";
            default:
                return "Ordenar por...";
        }
    }

    public String getTamanoPagina() {
        if (productosPagina != 100000) {
            return Integer.toString(productosPagina);
        } else {
            return "todos";
        }
    }

    public void processAjaxRequest() {
        log.log(Level.INFO, "processing ajax request");
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    private void cargarProductos() {
        if (pagina == 0) {
            pagina = 1;
        }
        productos = new ArrayList<>();
        totalRegistros = itemFacade.consultarTotalRegistros(soloConSaldo, filtros);
        totalPaginas = (totalRegistros / productosPagina) + (totalRegistros % productosPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();
        List resultado = itemFacade.consultarProductos(pagina, productosPagina, filtros, orderBy, sortOrder, soloConSaldo);
        if (resultado == null) {
            log.log(Level.WARNING, "No se obtuvieron resultados con los filtros seleccionados.");
            return;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (Object row : resultado) {
            int col = 0;
            Object[] cols = (Object[]) row;
            String itemCode = (String) cols[col++];
            String frgnName = (String) cols[col++];
            String refPro = (String) cols[col++];
            Integer precio = (Integer) cols[col++];
            Integer saldo = (Integer) cols[col++];
            Date fechaNuevo = (Date) cols[col++];
            String des = (String) cols[col++];
            Integer colores = (Integer) cols[col++];

            InventoryItemDTO dto = new InventoryItemDTO();
            dto.setItemCode(itemCode);
            dto.setItemName(frgnName);
            dto.setProviderCode(refPro);
            dto.setPrice(Integer.toString(precio));
            dto.setAvailableQuantity(saldo);
            dto.setNewFrom(sdf.format(fechaNuevo));
            dto.setSimilares(colores - 1);

            productos.add(dto);
        }
        log.log(Level.INFO, "Mostrando [{0}] productos", productos.size());
    }

    private void obtenerFiltrosIzquierda() {
        if (filtros == null || filtros.isEmpty()) {
            filtrosIzquierda = new TreeMap<>();

            List<ValorActivoFiltro> filters = valorActivoFiltroFacade.obtenerFiltrosOrdenados();

            if (filters != null && !filters.isEmpty()) {
                for (ValorActivoFiltro v : filters) {
                    String nombreFiltro = "";
                    switch (v.getTipo()) {
                        case "COL":
                            nombreFiltro = "COLOR";
                            break;
                        case "GRU":
                            nombreFiltro = "GRUPO";
                            break;
                        case "MAR":
                            nombreFiltro = "MARCA";
                            break;
                        case "MAT":
                            nombreFiltro = "MATERIAL";
                            break;
                        case "PRO":
                            nombreFiltro = "PROVEEDOR";
                            break;
                        case "SUB":
                            nombreFiltro = "SUBGRUPO";
                            break;
                        case "SUC":
                            nombreFiltro = "SUCURSAL";
                            break;
                        case "DEP":
                            nombreFiltro = "DEPARTAMENTO";
                            break;
                        case "CLC":
                            nombreFiltro = "COLECCIÓN";
                            break;
                    }

                    if (filtrosIzquierda.containsKey(nombreFiltro)) {
                        filtrosIzquierda.get(nombreFiltro).put(v.getValor(), false);
                    } else {
                        Map<String, Boolean> newData = new TreeMap<>();
                        newData.put(v.getValor(), false);

                        filtrosIzquierda.put(nombreFiltro, newData);
                    }
                }
            }
        } else if (filtros != null && !filtros.isEmpty()) {
            if (!filtros.containsKey("MATERIAL")) {
                filtrosIzquierda.remove("MATERIAL");
                /*Se obtienen los nuevos filtros de materiales*/
                List<Object> material = itemFacade.obtenerFiltrosMateriales(soloConSaldo, filtros);

                if (material != null && !material.isEmpty()) {
                    for (Object s : material) {
                        if (filtrosIzquierda.containsKey("MATERIAL")) {
                            filtrosIzquierda.get("MATERIAL").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("MATERIAL", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("COLOR")) {
                filtrosIzquierda.remove("COLOR");
                /*Se obtienen los nuevos filtros de colores*/
                List<Object> colores = itemFacade.obtenerFiltrosColor(soloConSaldo, filtros);

                if (colores != null && !colores.isEmpty()) {
                    for (Object s : colores) {
                        if (filtrosIzquierda.containsKey("COLOR")) {
                            filtrosIzquierda.get("COLOR").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("COLOR", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("PROVEEDOR")) {
                filtrosIzquierda.remove("PROVEEDOR");
                /*Se obtienen los nuevos filtros de proveedores*/
                List<Object> proveedores = itemFacade.obtenerFiltrosProveedor(soloConSaldo, filtros);

                if (proveedores != null && !proveedores.isEmpty()) {
                    for (Object s : proveedores) {
                        if (filtrosIzquierda.containsKey("PROVEEDOR")) {
                            filtrosIzquierda.get("PROVEEDOR").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("PROVEEDOR", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("SUBGRUPO")) {
                filtrosIzquierda.remove("SUBGRUPO");
                /*Se obtienen los nuevos filtros de subgrupos*/
                List<Object> subgrupos = itemFacade.obtenerFiltrosSubGrupo(soloConSaldo, filtros);

                if (subgrupos != null && !subgrupos.isEmpty()) {
                    for (Object s : subgrupos) {
                        if (filtrosIzquierda.containsKey("SUBGRUPO")) {
                            filtrosIzquierda.get("SUBGRUPO").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("SUBGRUPO", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("SUCURSAL")) {
                filtrosIzquierda.remove("SUCURSAL");
                /*Se obtienen los nuevos filtros de sucursales*/
                List<Object> sucursales = itemFacade.obtenerFiltrosSucursal(soloConSaldo, filtros);

                if (sucursales != null && !sucursales.isEmpty()) {
                    for (Object s : sucursales) {
                        if (filtrosIzquierda.containsKey("SUCURSAL")) {
                            filtrosIzquierda.get("SUCURSAL").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("SUCURSAL", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("GRUPO")) {
                filtrosIzquierda.remove("GRUPO");
                /*Se obtienen los nuevos filtros de grupos*/
                List<Object> sucursales = itemFacade.obtenerFiltrosGrupo(soloConSaldo, filtros);

                if (sucursales != null && !sucursales.isEmpty()) {
                    for (Object s : sucursales) {
                        if (filtrosIzquierda.containsKey("GRUPO")) {
                            filtrosIzquierda.get("GRUPO").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("GRUPO", newData);
                        }
                    }
                }
            }

            if (!filtros.containsKey("MARCA")) {
                filtrosIzquierda.remove("MARCA");
                /*Se obtienen los nuevos filtros de marcas*/
                List<Object> marcas = itemFacade.obtenerFiltrosMarca(soloConSaldo, filtros);

                if (marcas != null && !marcas.isEmpty()) {
                    for (Object s : marcas) {
                        if (filtrosIzquierda.containsKey("MARCA")) {
                            filtrosIzquierda.get("MARCA").put((String) s, false);
                        } else {
                            Map<String, Boolean> newData = new TreeMap<>();
                            newData.put((String) s, false);

                            filtrosIzquierda.put("MARCA", newData);
                        }
                    }
                }
            }
        }
    }

    public void seleccionarFiltro() {
        pagina = 1;
        String filter = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("valor");

        if (filtrosIzquierda != null && filtrosIzquierda.get(filter).get(value)) {
            filtrosIzquierda.get(filter).put(value, false);
            log.log(Level.INFO, "Se marco el valor [{0}], del filtro [{1}] como false", new Object[]{value, filter});

            if (filtros.containsKey(filter)) {
                filtros.get(filter).remove(value);
                if (filtros.get(filter).isEmpty()) {
                    filtros.remove(filter);
                }
            }
        } else if (filtrosIzquierda != null) {
            filtrosIzquierda.get(filter).put(value, true);
            log.log(Level.INFO, "Se marco el valor [{0}], del filtro [{1}] como true", new Object[]{value, filter});

            if (filtros.containsKey(filter)) {
                filtros.get(filter).add(value);
            } else {
                List<String> s = new ArrayList<>();
                s.add(value);

                filtros.put(filter, s);
            }
        }
        cargarProductos();
        obtenerFiltrosIzquierda();
    }

    public void filtrarProductosSaldo() {
        soloConSaldo = !soloConSaldo;
        cargarProductos();
        obtenerFiltrosIzquierda();
    }

    public void filtrarProductosTexto() {
        log.log(Level.INFO, "Filtrando con texto [{0}]", filtroTexto);
        if (filtroTexto != null && !filtroTexto.trim().isEmpty()) {
            if (filtroTexto.contains("*")) {
                filtroTexto = baruGenericMBean.completarReferencia(filtroTexto);
            }
            List<String> valor = Arrays.asList(filtroTexto.split(","));

            filtros.put("TEXTO", valor);
        } else {
            filtros.remove("TEXTO");
        }
        cargarProductos();
    }

    public void ordenarProductos() {
        orderBy = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        sortOrder = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sortOrder");
        log.log(Level.INFO, "Ordenando por [{0}] [{1}]", new Object[]{orderBy, sortOrder});
        cargarProductos();
    }

    public void cambiarTamanoPagina() {
        productosPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tamanoPagina"));
        log.log(Level.INFO, "Cambiando # de productos x pagina a [{0}]", productosPagina);
        cargarProductos();
    }

    public void alternarOrden() {
        log.log(Level.INFO, "Alternando orden. Antes [{0}]", sortOrder);
        if (sortOrder.equals("ASC")) {
            sortOrder = "DESC";
        } else {
            sortOrder = "ASC";
        }
        log.log(Level.INFO, "Ahora [{0}]", sortOrder);
        cargarProductos();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        cargarProductos();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        cargarProductos();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
        } else {
            pagina = totalPaginas;
        }
        cargarProductos();
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
        } else {
            pagina = 1;
        }
        cargarProductos();
    }

    public void mostrarFiltrosMovil() {
        mostrarFiltros = !mostrarFiltros;
    }

    public void seleccionarItem() {
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("item");

        /*Se obtienen las imagenes del producto*/
        imagenes = new ArrayList<>();

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
        obtenerSaldo(referencia);
        obtenerDescuentosReferencia(referencia);

        /*Se obtiene la plantilla, 360 y wow*/
        plantilla = imagenProductoMBean.obtenerPlantilla(referencia);
        tresSesenta = imagenProductoMBean.obtener360(referencia);
        wow = imagenProductoMBean.obtenerWOW(referencia);

        /*Se obtiene datos del item*/
        ItemInventario i = itemFacade.find(referencia);

        if (i != null && i.getItemCode() != null && !i.getItemCode().isEmpty()) {
            descripcion = i.getUdescripciona();
        } else {
            descripcion = "No se encontraron datos.";
        }

        /*Se obtiene la lista de materiales*/
        materiales = new ArrayList<>();
        List<BaruMateriales> mats = baruMaterialesFacade.obtenerMaterialesArticulo(referencia);

        if (mats != null && !mats.isEmpty()) {
            for (BaruMateriales b : mats) {
                materiales.add(new BaruMaterialesDTO(b.getCode(), b.getName(), b.getuCuidados()));
            }

            Collections.sort(mats, new Comparator<BaruMateriales>() {
                @Override
                public int compare(BaruMateriales t, BaruMateriales t1) {
                    return t.getName().compareTo(t1.getName());
                }
            });
        }

        /*Se obtienen los items similares*/
        itemsSimilares = imagenProductoMBean.obtenerItemsSimilares(referencia);
    }

    private void obtenerSaldo(String item) {
        if (item != null && !item.isEmpty()) {
            saldos = new ArrayList<>();

            List<SaldoItemInventario> balances = saldoItemInventarioFacade.obtenerSaldo(item);

            if (balances != null && !balances.isEmpty()) {
                for (SaldoItemInventario b : balances) {
                    saldos.add(new SaldoItemDTO(b.getOnHand().intValue(), null, b.getSaldoItemInventarioPK().getItemCode(),
                            b.getSaldoItemInventarioPK().getWhsCode().getWhsCode(), obtenerSaldoUbicacion(item, b.getSaldoItemInventarioPK().getWhsCode().getWhsCode())));
                }
            }
        }
    }

    private List<SaldoUbicacionDTO> obtenerSaldoUbicacion(String item, String almacen) {
        List<SaldoUbicacion> saldoUbicacion = saldoUbicacionFacade.findByItemCodeAndWhsCode(true, item, almacen);

        if (saldoUbicacion != null && !saldoUbicacion.isEmpty()) {
            List<SaldoUbicacionDTO> dto = new ArrayList<>();

            for (SaldoUbicacion u : saldoUbicacion) {
                dto.add(new SaldoUbicacionDTO(u.getAbsEntry(), u.getUbicacion().getAbsEntry(), null, u.getOnHandQty().intValue(),
                        null, null, u.getUbicacion().getBinCode().replace(almacen, ""), null));
            }
            return dto;
        }
        return null;
    }

    private void obtenerDescuentosReferencia(String item) {
        descuentos = new ArrayList<>();

        List<ProgramacionDescuento> discounts = programacionDescuentoFacade.obtenerProgramacionesReferencia(item);

        if (discounts != null && !discounts.isEmpty()) {
            for (ProgramacionDescuento d : discounts) {
                descuentos.add(new ProgramacionDescuentoDTO(d.getPorcentaje(), d.getIdReglaDescuento(), d.getReferencia(), d.getCanal(), d.getFechaInicio(), d.getFechaFin()));
            }
        }
    }

    public void mostrarPagina() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        cargarProductos();
    }
}
