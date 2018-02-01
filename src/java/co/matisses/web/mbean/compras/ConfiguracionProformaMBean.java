package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.ColumnaProforma;
import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.entity.DatosProveedor;
import co.matisses.persistence.web.entity.DetalleOperacionColumna;
import co.matisses.persistence.web.entity.EncabezadoExcel;
import co.matisses.persistence.web.facade.ColumnaProformaFacade;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import co.matisses.persistence.web.facade.DatosProveedorFacade;
import co.matisses.persistence.web.facade.DetalleOperacionColumnaFacade;
import co.matisses.persistence.web.facade.EncabezadoExcelFacade;
import co.matisses.persistence.web.facade.IncotermFacade;
import co.matisses.persistence.web.facade.PuertoMundoFacade;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.DatosProveedorDTO;
import co.matisses.web.dto.EncabezadoSuperiorProformaDTO;
import co.matisses.web.dto.ColumnaDatosProformaDTO;
import co.matisses.web.dto.EncabezadoExcelDTO;
import co.matisses.web.dto.TipoDatosDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.poi.ExcelGeneratorProforma;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "configuracionProformaMBean")
public class ConfiguracionProformaMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruApplicationMBean;
    private static final Logger log = Logger.getLogger(ConfiguracionProformaMBean.class.getSimpleName());
    private boolean botonesOrdenarDeshabilitados = true;
    private Integer idColumnaSeleccionada;
    private String proveedor;
    private String filtroDisponibles;
    private String filtroSeleccionados;
    private String parametroBusquedaDisponibles;
    private String parametroBusquedaSeleccionados;
    private String mensajeAdvertencia;
    private boolean dlgAdvertencia = false;
    private List<DatosProveedorDTO> proveedores;
    private List<ColumnaProformaDTO> todasColumnas;
    private List<ColumnaProformaDTO> columnasDisponibles;
    private List<ColumnaProformaDTO> columnasDisponiblesFiltradas;
    private List<ColumnaProformaDTO> columnasSeleccionadas;
    private List<ColumnaProformaDTO> columnasSeleccionadasFiltradas;
    private final List<EncabezadoSuperiorProformaDTO> encabezadoSuperior;
    @EJB
    private DatosProveedorFacade proveedorFacade;
    @EJB
    private ColumnaProformaFacade columnaProformaFacade;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;
    @EJB
    private DetalleOperacionColumnaFacade detalleOperacionColumnaFacade;
    @EJB
    private EncabezadoExcelFacade encabezadoExcelFacade;
    @EJB
    private PuertoMundoFacade puertoMundoFacade;
    @EJB
    private IncotermFacade incotermFacade;

    public ConfiguracionProformaMBean() {
        proveedores = new ArrayList<>();
        todasColumnas = new ArrayList<>();
        columnasDisponibles = new ArrayList<>();
        columnasDisponiblesFiltradas = new ArrayList<>();
        columnasSeleccionadas = new ArrayList<>();
        columnasSeleccionadasFiltradas = new ArrayList<>();
        encabezadoSuperior = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarProveedores();
        cargarColumnas();
    }

    public boolean isBotonesOrdenarDeshabilitados() {
        return botonesOrdenarDeshabilitados;
    }

    public void setBotonesOrdenarDeshabilitados(boolean botonesOrdenarDeshabilitados) {
        this.botonesOrdenarDeshabilitados = botonesOrdenarDeshabilitados;
    }

    public Integer getIdColumnaSeleccionada() {
        return idColumnaSeleccionada;
    }

    public void setIdColumnaSeleccionada(Integer idColumnaSeleccionada) {
        this.idColumnaSeleccionada = idColumnaSeleccionada;
    }

    public List<DatosProveedorDTO> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<DatosProveedorDTO> proveedores) {
        this.proveedores = proveedores;
    }

    public List<ColumnaProformaDTO> getTodasColumnas() {
        return todasColumnas;
    }

    public void setTodasColumnas(List<ColumnaProformaDTO> todasColumnas) {
        this.todasColumnas = todasColumnas;
    }

    public List<ColumnaProformaDTO> getColumnasDisponibles() {
        return columnasDisponibles;
    }

    public void setColumnasDisponibles(List<ColumnaProformaDTO> columnasDisponibles) {
        this.columnasDisponibles = columnasDisponibles;
    }

    public List<ColumnaProformaDTO> getColumnasDisponiblesFiltradas() {
        return columnasDisponiblesFiltradas;
    }

    public void setColumnasDisponiblesFiltradas(List<ColumnaProformaDTO> columnasDisponiblesFiltradas) {
        this.columnasDisponiblesFiltradas = columnasDisponiblesFiltradas;
    }

    public List<ColumnaProformaDTO> getColumnasSeleccionadas() {
        return columnasSeleccionadas;
    }

    public void setColumnasSeleccionadas(List<ColumnaProformaDTO> columnasSeleccionadas) {
        this.columnasSeleccionadas = columnasSeleccionadas;
    }

    public List<ColumnaProformaDTO> getColumnasSeleccionadasFiltradas() {
        return columnasSeleccionadasFiltradas;
    }

    public void setColumnasSeleccionadasFiltradas(List<ColumnaProformaDTO> columnasSeleccionadasFiltradas) {
        this.columnasSeleccionadasFiltradas = columnasSeleccionadasFiltradas;
    }

    public String getProveedor() {
        return proveedor;
    }

    public String getProveedorSeleccionado() {
        if (proveedor != null && !proveedor.isEmpty()) {
            for (DatosProveedorDTO d : proveedores) {
                if (d.getCodProveedor().equals(proveedor)) {
                    return d.getNombreMostrar();
                }
            }
        }
        return "Seleccione";
    }

    public String getFiltroDisponibles() {
        return filtroDisponibles;
    }

    public void setFiltroDisponibles(String filtroDisponibles) {
        this.filtroDisponibles = filtroDisponibles;
    }

    public String getFiltroSeleccionados() {
        return filtroSeleccionados;
    }

    public void setFiltroSeleccionados(String filtroSeleccionados) {
        this.filtroSeleccionados = filtroSeleccionados;
    }

    public String getParametroBusquedaDisponibles() {
        return parametroBusquedaDisponibles;
    }

    public void setParametroBusquedaDisponibles(String parametroBusquedaDisponibles) {
        this.parametroBusquedaDisponibles = parametroBusquedaDisponibles;
    }

    public String getParametroBusquedaSeleccionados() {
        return parametroBusquedaSeleccionados;
    }

    public void setParametroBusquedaSeleccionados(String parametroBusquedaSeleccionados) {
        this.parametroBusquedaSeleccionados = parametroBusquedaSeleccionados;
    }

    public BaruApplicationMBean getBaruApplicationMBean() {
        return baruApplicationMBean;
    }

    public void setBaruApplicationMBean(BaruApplicationMBean baruApplicationMBean) {
        this.baruApplicationMBean = baruApplicationMBean;
    }

    public String getMensajeAdvertencia() {
        return mensajeAdvertencia;
    }

    public void setMensajeAdvertencia(String mensajeAdvertencia) {
        this.mensajeAdvertencia = mensajeAdvertencia;
    }

    public boolean isDlgAdvertencia() {
        return dlgAdvertencia;
    }

    public void setDlgAdvertencia(boolean dlgAdvertencia) {
        this.dlgAdvertencia = dlgAdvertencia;
    }

    public void valueChangeListener() {
        log.info("Valor cambio");
    }

    private void cargarProveedores() {
        List<DatosProveedor> entidades = proveedorFacade.findAll();
        proveedores = new ArrayList<>();
        for (DatosProveedor entidad : entidades) {
            proveedores.add(new DatosProveedorDTO(entidad.getCodProveedor(), entidad.getRazonSocial(), entidad.getNombreSocioNegocios(),
                    entidad.getDireccion(), entidad.getTelefono(), entidad.getCorreo(), entidad.getPersonaContacto(), entidad.getLogo(), null,
                    entidad.getNombreSocioNegocios() == null || entidad.getNombreSocioNegocios().isEmpty() ? entidad.getCodProveedor()
                            : "(" + entidad.getCodProveedor() + ") " + entidad.getNombreSocioNegocios()));
        }

        Collections.sort(proveedores, new Comparator<DatosProveedorDTO>() {
            @Override
            public int compare(DatosProveedorDTO t, DatosProveedorDTO t1) {
                return t.getNombreMostrar().compareTo(t1.getNombreMostrar());
            }
        });
    }

    private void cargarColumnas() {
        todasColumnas = new ArrayList<>();
        for (ColumnaProforma entidad : columnaProformaFacade.findAll()) {
            todasColumnas.add(new ColumnaProformaDTO(entidad.getIdColumna(), entidad.getIdColumna(), entidad.getDecimalesVisibles(),
                    null, entidad.getNombre1(), entidad.getNombre1Ingles(), entidad.getNombre2(), entidad.getNombre2Ingles(),
                    entidad.getLongitudOcupadaTabla(), entidad.getTipoCantidad(), entidad.getPermitirCrearItem(), entidad.getRequiereOperacion(), entidad.getTipoItem(),
                    entidad.getTipoIdentificador(), entidad.getTipoImagen(), entidad.getObligatoria(), entidad.getIncluirProforma(),
                    entidad.getModificable(), entidad.getModificableSiNuevo(), entidad.getTipoValorTotal(), entidad.getTipoCBM(), entidad.getTipoValorUnitario(), entidad.getTipoDescuento(),
                    entidad.getTipoDescripcionItem() != null ? entidad.getTipoDescripcionItem() : false,
                    entidad.getIdTipoDato() != null
                            ? new TipoDatosDTO(entidad.getIdTipoDato().getIdTipoDato(), entidad.getIdTipoDato().getTipoDato()) : null,
                    null));
        }
        Collections.sort(todasColumnas);
        columnasDisponibles = new ArrayList<>(todasColumnas);
        columnasDisponiblesFiltradas = new ArrayList<>(todasColumnas);
    }

    public void seleccionarProveedor() {
        proveedor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codProveedor");
        log.log(Level.INFO, "Se selecciono el proveedor [{0}]", proveedor);
        cargarDatosProveedor();
    }

    public void filtrarListaDisponibles() {
        log.log(Level.INFO, "Ejecutando filtro disponibles [{0}]", parametroBusquedaDisponibles);
        columnasDisponiblesFiltradas = new ArrayList<>();
        for (ColumnaProformaDTO dto : columnasDisponibles) {
            if (parametroBusquedaDisponibles == null || dto.getNombre1().toLowerCase().contains(parametroBusquedaDisponibles.toLowerCase())
                    || (dto.getNombre2() != null && dto.getNombre2().toLowerCase().contains(parametroBusquedaDisponibles.toLowerCase()))) {
                columnasDisponiblesFiltradas.add(dto);
            }
        }
        Collections.sort(columnasDisponiblesFiltradas);
    }

    public void filtrarListaSeleccionadas() {
        log.log(Level.INFO, "Ejecutando filtro seleccionadas [{0}]", parametroBusquedaSeleccionados);
        columnasSeleccionadasFiltradas = new ArrayList<>();
        for (ColumnaProformaDTO dto : columnasSeleccionadas) {
            if (parametroBusquedaSeleccionados == null || dto.getNombre1().toLowerCase().contains(parametroBusquedaSeleccionados.toLowerCase())
                    || (dto.getNombre2() != null && dto.getNombre2().toLowerCase().contains(parametroBusquedaSeleccionados.toLowerCase()))) {
                columnasSeleccionadasFiltradas.add(dto);
            }
        }
        if (columnasSeleccionadas.size() > columnasSeleccionadasFiltradas.size()) {
            botonesOrdenarDeshabilitados = true;
        } else if (idColumnaSeleccionada != null) {
            botonesOrdenarDeshabilitados = false;
        }
        Collections.sort(columnasSeleccionadasFiltradas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO t, ColumnaProformaDTO t1) {
                return ((Integer) t.getPosicion()).compareTo((Integer) t1.getPosicion());
            }
        });
    }

    public void validarColumnaAgregada() {
        mensajeAdvertencia = "";
        dlgAdvertencia = false;
        idColumnaSeleccionada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idColumna"));
        log.log(Level.INFO, "Agregando la columna [{0}] al proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});
        if (proveedor == null) {
            log.severe("No se selecciono ningun proveedor");
            return;
        }

        int posicion = columnasDisponibles.indexOf(new ColumnaProformaDTO(idColumnaSeleccionada));
        ColumnaProformaDTO dto = columnasDisponibles.get(posicion);

        if (dto.isRequiereOperacion()) {
            ColumnaProforma col = columnaProformaFacade.find(dto.getIdColumna());

            if (col != null && col.getIdColumna() != null && col.getIdColumna() != 0) {
                if (col.getIdOperacionColumna() != null && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != null && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != 0) {
                    List<DetalleOperacionColumna> opers = detalleOperacionColumnaFacade.obtenerOperaciones(col.getIdOperacionColumna().getIdOperacionColumnaProforma());
                    if (opers != null && !opers.isEmpty()) {
                        DetalleOperacionColumna oper = opers.get(0);

                        boolean columna1Existe = false;
                        boolean columna2Existe = false;
                        for (ColumnaProformaDTO c : columnasSeleccionadas) {
                            if (!columna1Existe || !columna2Existe) {
                                if (c.getIdColumna().equals(oper.getIdColumna1().getIdColumna())) {
                                    columna1Existe = true;
                                }
                                if (oper.getIdColumna2() != null && oper.getIdColumna2().getIdColumna() != null) {
                                    if (c.getIdColumna().equals(oper.getIdColumna2().getIdColumna())) {
                                        columna2Existe = true;
                                    }
                                } else {
                                    columna2Existe = true;
                                }
                            }
                        }

                        if (!columna1Existe || !columna2Existe) {
                            if (!columna1Existe) {
                                mensajeAdvertencia += oper.getIdColumna1().getNombre1() + " ";
                                if (oper.getIdColumna1().getNombre2() != null) {
                                    mensajeAdvertencia += "- " + oper.getIdColumna1().getNombre2() + " ";
                                }
                            }
                            if (!columna2Existe && oper.getIdColumna2() != null && oper.getIdColumna2().getIdColumna() != null) {
                                mensajeAdvertencia += "| " + oper.getIdColumna2().getNombre1() + " ";
                                if (oper.getIdColumna2() != null && oper.getIdColumna2().getNombre2() != null) {
                                    mensajeAdvertencia += "- " + oper.getIdColumna2().getNombre2() + " ";
                                }
                            }
                            log.log(Level.INFO, mensajeAdvertencia);
                            dlgAdvertencia = true;
                        } else {
                            agregarColumna();
                        }
                    }
                }
            }
        } else if (dto.isTipoItem()) {
            for (ColumnaProformaDTO c : columnasSeleccionadas) {
                if (c.isTipoItem()) {
                    mostrarMensaje("Error", "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Ítem.", true, false, false);
                    log.log(Level.SEVERE, "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Item");
                    return;
                }
            }
            agregarColumna();
        } else if (dto.isTipoIdentificador()) {
            for (ColumnaProformaDTO c : columnasSeleccionadas) {
                if (c.isTipoIdentificador()) {
                    mostrarMensaje("Error", "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Identificador.", true, false, false);
                    log.log(Level.SEVERE, "La columna seleccionada, no se puede agregar debido a que ya existe una columna para este proveedor con la propiedad Tipo Identificador");
                    return;
                }
            }
            agregarColumna();
        } else {
            agregarColumna();
        }
    }

    public void agregarColumna() {
        if (idColumnaSeleccionada != null && idColumnaSeleccionada != 0) {
            ColumnaProforma col = columnaProformaFacade.find(idColumnaSeleccionada);

            if (col != null && col.getIdColumna() != null && col.getIdColumna() != 0) {
                if (col.getRequiereOperacion()) {
                    if (col.getIdOperacionColumna() != null && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != null && col.getIdOperacionColumna().getIdOperacionColumnaProforma() != 0) {
                        List<DetalleOperacionColumna> opers = detalleOperacionColumnaFacade.obtenerOperaciones(col.getIdOperacionColumna().getIdOperacionColumnaProforma());
                        if (opers != null && !opers.isEmpty()) {
                            DetalleOperacionColumna oper = opers.get(0);

                            boolean columna1Existe = false;
                            boolean columna2Existe = false;
                            for (ColumnaProformaDTO c : columnasSeleccionadas) {
                                if (!columna1Existe || !columna2Existe) {
                                    if (c.getIdColumna().equals(oper.getIdColumna1().getIdColumna())) {
                                        columna1Existe = true;
                                    }
                                    if (oper.getIdColumna2() != null) {
                                        if (c.getIdColumna().equals(oper.getIdColumna2().getIdColumna())) {
                                            columna2Existe = true;
                                        }
                                    } else {
                                        columna2Existe = true;
                                    }
                                }
                            }

                            if (!columna1Existe || !columna2Existe) {
                                if (!columna1Existe) {
                                    if (!crearColumna(oper.getIdColumna1().getIdColumna())) {
                                        mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada.", true, false, false);
                                        log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada");
                                        return;
                                    }
                                }
                                if (!columna2Existe && (oper.getConstante() == null || oper.getConstante().isEmpty())) {
                                    if (!crearColumna(oper.getIdColumna2().getIdColumna())) {
                                        mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada.", true, false, false);
                                        log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada");
                                        return;
                                    }
                                }
                                if (!crearColumna(idColumnaSeleccionada)) {
                                    mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada.", true, false, false);
                                    log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada");
                                    return;
                                }
                            } else if (!crearColumna(idColumnaSeleccionada)) {
                                mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada.", true, false, false);
                                log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada");
                                return;
                            }
                        }
                    }
                } else if (!crearColumna(idColumnaSeleccionada)) {
                    mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada.", true, false, false);
                    log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada");
                    return;
                }

                idColumnaSeleccionada = null;
            }
        } else {
            mostrarMensaje("Error", "No se pudo crear la configuración para la columna seleccionada, debido a que no se encontraron datos", true, false, false);
            log.log(Level.SEVERE, "No se pudo crear la configuracion para la columna seleccionada, debido a que no se encontraron datos");
            return;
        }
    }

    private boolean crearColumna(Integer idColumna) {
        if (idColumna != null && idColumna != 0) {
            ConfiguracionProforma conf = new ConfiguracionProforma();

            conf.setCodigoProveedor(proveedor);
            conf.setIdColumna(new ColumnaProforma(idColumna));
            conf.setOrden(columnasSeleccionadas.size() + 1);

            try {
                configuracionProformaFacade.create(conf);
                log.log(Level.INFO, "Se creo configuracion de columnas para el proveedor [{0}], con la columna de id [{1}]", new Object[]{proveedor, idColumna});

                int posicion = columnasDisponibles.indexOf(new ColumnaProformaDTO(idColumna));
                ColumnaProformaDTO dto = columnasDisponibles.get(posicion);
                //agregar dto a lista seleccionados
                columnasSeleccionadas.add(dto);
                //eliminar dto de lista disponibles
                columnasDisponibles.remove(dto);
                filtrarListaDisponibles();
                filtrarListaSeleccionadas();
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al crear la configuracion de columnas para el proveedor [{0}], con la columna con id [{1}]. Error [{2}]",
                        new Object[]{proveedor, idColumna, e.getMessage()});
                return false;
            }
        }
        return false;
    }

    public void validarColumnaEliminada() {
        mensajeAdvertencia = "";
        dlgAdvertencia = false;
        log.log(Level.INFO, "Eliminando la columna [{0}] del proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});
        if (proveedor == null) {
            log.severe("No se selecciono ningun proveedor");
            return;
        }

        List<DetalleOperacionColumna> opers = detalleOperacionColumnaFacade.obtenerOperacionesColumna(idColumnaSeleccionada);
        if (opers != null && !opers.isEmpty()) {
            for (DetalleOperacionColumna oper : opers) {
                List<ColumnaProforma> cps = columnaProformaFacade.buscarOperacion(oper.getIdOperacionColumnaProforma().getIdOperacionColumnaProforma());

                if (cps != null && !cps.isEmpty()) {
                    for (ColumnaProforma cp : cps) {
                        for (ColumnaProformaDTO c : columnasSeleccionadas) {
                            if (c.getIdColumna().equals(cp.getIdColumna())) {
                                mensajeAdvertencia += cp.getNombre1() + " ";
                                if (cp.getNombre2() != null && !cp.getNombre2().isEmpty()) {
                                    mensajeAdvertencia += "- " + cp.getNombre2();
                                }
                                dlgAdvertencia = true;
                                return;
                            }
                        }
                    }
                    eliminarColumna();
                }
            }
        } else {
            eliminarColumna();
        }
    }

    public void eliminarColumna() {
        for (ColumnaProformaDTO dto : columnasSeleccionadas) {
            if (dto.getIdColumna().equals(idColumnaSeleccionada)) {
                try {
                    //eliminar registro base de datos
                    configuracionProformaFacade.eliminarRegistroConfiguracion(proveedor, dto.getIdColumna());
                    log.log(Level.INFO, "Se elimino la configuracion");

                    //actualizar posicion columnas siguientes
                    for (int i = 0; i < columnasSeleccionadas.size(); i++) {
                        ConfiguracionProforma entidad = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, columnasSeleccionadas.get(i).getIdColumna());
                        if (entidad != null && entidad.getIdConfiguracion() != null && entidad.getIdConfiguracion() != 0) {
                            entidad.setOrden(entidad.getOrden() - 1);
                            configuracionProformaFacade.edit(entidad);
                        }
                    }

                    //agregar dto a lista seleccionados
                    columnasDisponibles.add(dto);

                    //eliminar dto de lista disponibles
                    columnasSeleccionadas.remove(dto);
                    filtrarListaDisponibles();
                    filtrarListaSeleccionadas();
                    botonesOrdenarDeshabilitados = true;
                    break;
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al eliminar el registro de configuracion. ", e);
                    mostrarMensaje("Error", "Ocurrió un error al eliminar el registro de configuración.", true, false, false);
                    return;
                }
            }
        }
    }

    public void cargarDatosProveedor() {
        if (proveedor == null) {
            return;
        }
        //cargar columnas configuradas
        columnasSeleccionadas = new ArrayList<>();
        columnasDisponibles = new ArrayList<>();
        List<ConfiguracionProforma> entidades = configuracionProformaFacade.buscarPorCodigoProveedor(proveedor, null);
        if (entidades != null) {
            for (ConfiguracionProforma entidad : entidades) {
                columnasSeleccionadas.add(new ColumnaProformaDTO(entidad.getOrden(), entidad.getIdColumna().getIdColumna(), entidad.getIdColumna().getDecimalesVisibles(),
                        null, entidad.getIdColumna().getNombre1(), entidad.getIdColumna().getNombre1Ingles(), entidad.getIdColumna().getNombre2(), entidad.getIdColumna().getNombre2Ingles(),
                        entidad.getIdColumna().getLongitudOcupadaTabla(), entidad.getIdColumna().getTipoCantidad(),
                        entidad.getIdColumna().getPermitirCrearItem(), entidad.getIdColumna().getRequiereOperacion(), entidad.getIdColumna().getTipoItem(),
                        entidad.getIdColumna().getTipoIdentificador(), entidad.getIdColumna().getTipoImagen(), entidad.getIdColumna().getObligatoria(), entidad.getIdColumna().getIncluirProforma(),
                        entidad.getIdColumna().getModificable(), entidad.getIdColumna().getModificableSiNuevo(), entidad.getIdColumna().getTipoValorTotal(),
                        entidad.getIdColumna().getTipoCBM(), entidad.getIdColumna().getTipoValorUnitario(), entidad.getIdColumna().getTipoDescuento(),
                        entidad.getIdColumna().getIdTipoDato() != null
                                ? new TipoDatosDTO(entidad.getIdColumna().getIdTipoDato().getIdTipoDato(), entidad.getIdColumna().getIdTipoDato().getTipoDato()) : null,
                        null));
            }

            for (ColumnaProformaDTO dto : todasColumnas) {
                if (!columnasSeleccionadas.contains(dto)) {
                    columnasDisponibles.add(dto);
                }
            }

            //ejecutar filtros
            filtrarListaDisponibles();
            filtrarListaSeleccionadas();
            limpiar();
        }
    }

    public void seleccionarColumna() {
        Integer ultimaSeleccionada = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idColumna"));
        if (idColumnaSeleccionada != null && ultimaSeleccionada.equals(idColumnaSeleccionada)) {
            idColumnaSeleccionada = null;
            botonesOrdenarDeshabilitados = true;
        } else {
            idColumnaSeleccionada = ultimaSeleccionada;
            if (parametroBusquedaSeleccionados != null && !parametroBusquedaSeleccionados.isEmpty()) {
                botonesOrdenarDeshabilitados = true;
            } else {
                botonesOrdenarDeshabilitados = false;
            }
        }

        log.log(Level.INFO, "Selecciono la columna [{0}] del proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});
    }

    public void moverSeleccionIzquierda() {
        if (idColumnaSeleccionada == null) {
            return;
        }
        log.log(Level.INFO, "Moviendo a la izquierda la columna [{0}] del proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});

        int pos = columnasSeleccionadas.indexOf(new ColumnaProformaDTO(idColumnaSeleccionada));
        if (pos == 0) {
            return;
        }

        ConfiguracionProforma entidadIzq = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, columnasSeleccionadas.get(pos - 1).getIdColumna());
        ConfiguracionProforma entidadMover = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, idColumnaSeleccionada);

        entidadIzq.setOrden(entidadIzq.getOrden() + 1);
        entidadMover.setOrden(entidadMover.getOrden() - 1);

        configuracionProformaFacade.edit(entidadMover);
        configuracionProformaFacade.edit(entidadIzq);

        ColumnaProformaDTO columnaMover = columnasSeleccionadas.get(pos);
        columnaMover.setPosicion(columnaMover.getPosicion() - 1);
        ColumnaProformaDTO columnaIzq = columnasSeleccionadas.get(pos - 1);
        columnaIzq.setPosicion(columnaMover.getPosicion() + 1);

        columnasSeleccionadas.set(pos - 1, columnaMover);
        columnasSeleccionadas.set(pos, columnaIzq);
        columnasSeleccionadasFiltradas = new ArrayList<>(columnasSeleccionadas);
    }

    public void moverSeleccionDerecha() {
        if (idColumnaSeleccionada == null) {
            return;
        }
        log.log(Level.INFO, "Moviendo a la derecha la columna [{0}] del proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});

        int pos = columnasSeleccionadas.indexOf(new ColumnaProformaDTO(idColumnaSeleccionada));
        if (pos == columnasSeleccionadas.size() - 1) {
            return;
        }

        ConfiguracionProforma entidadDer = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, columnasSeleccionadas.get(pos + 1).getIdColumna());
        ConfiguracionProforma entidadMover = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, idColumnaSeleccionada);

        entidadDer.setOrden(entidadDer.getOrden() - 1);
        entidadMover.setOrden(entidadMover.getOrden() + 1);

        configuracionProformaFacade.edit(entidadMover);
        configuracionProformaFacade.edit(entidadDer);

        ColumnaProformaDTO columnaMover = columnasSeleccionadas.get(pos);
        columnaMover.setPosicion(columnaMover.getPosicion() + 1);
        ColumnaProformaDTO columnaDer = columnasSeleccionadas.get(pos + 1);
        columnaDer.setPosicion(columnaMover.getPosicion() - 1);

        columnasSeleccionadas.set(pos + 1, columnaMover);
        columnasSeleccionadas.set(pos, columnaDer);

        columnasSeleccionadasFiltradas = new ArrayList<>(columnasSeleccionadas);
//        filtrarListaSeleccionadas();
    }

    public void eliminarSeleccion() {
        if (idColumnaSeleccionada == null) {
            return;
        }
        log.log(Level.INFO, "Eliminando la columna [{0}] del proveedor [{1}]", new Object[]{idColumnaSeleccionada, proveedor});
        if (proveedor == null) {
            log.severe("No se selecciono ningun proveedor");
            return;
        }
        int posicion = columnasSeleccionadas.indexOf(new ColumnaProformaDTO(idColumnaSeleccionada));
        ColumnaProformaDTO dto = columnasSeleccionadas.get(posicion);

        try {
            //eliminar registro base de datos
            try {
                configuracionProformaFacade.eliminarRegistroConfiguracion(proveedor, dto.getIdColumna());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al eliminar la columnas. Error: [{0}]", e.getMessage());
                mostrarMensaje("Error", "La columna seleccionada no se puede eliminar, debido a que está en uso.", true, false, false);
                return;
            }

            //actualizar posicion columnas siguientes
            for (int i = posicion + 1; i < columnasSeleccionadas.size(); i++) {
                ConfiguracionProforma entidad = configuracionProformaFacade.buscarPorCodigoProveedorIdColumna(proveedor, columnasSeleccionadas.get(i).getIdColumna());
                entidad.setOrden(entidad.getOrden() - 1);
                configuracionProformaFacade.edit(entidad);
            }

            //agregar dto a lista seleccionados
            columnasDisponibles.add(dto);

            //eliminar dto de lista disponibles
            columnasSeleccionadas.remove(posicion);
            idColumnaSeleccionada = null;
            botonesOrdenarDeshabilitados = true;
            filtrarListaDisponibles();
            filtrarListaSeleccionadas();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al eliminar el registro de configuracion. ", e);
            //TODO: mostrar mensaje de error
        }
    }

    public void limpiar() {
        botonesOrdenarDeshabilitados = true;
        idColumnaSeleccionada = null;
        filtroDisponibles = null;
        filtroSeleccionados = null;
        parametroBusquedaDisponibles = null;
        parametroBusquedaSeleccionados = null;
        mensajeAdvertencia = null;
        dlgAdvertencia = false;
    }

    public List<EncabezadoExcelDTO> encabezadoSuperiorProforma(DatosProveedor datoProveedor) {
        List<EncabezadoExcelDTO> encabezado = new ArrayList<>();

        List<EncabezadoExcel> enc = encabezadoExcelFacade.obtenerEncabezado("compras");
        if (enc != null && !enc.isEmpty()) {
            DatosProveedor pro = proveedorFacade.consultarProveedor(proveedor);
            for (EncabezadoExcel e : enc) {
                encabezado.add(new EncabezadoExcelDTO(e.getIdEncabezadoExcel(), e.getColumnaInicial(), e.getColumnaFinal(), e.getFilaInicial(),
                        e.getFilaFinal(), e.getObjecto(), obtenerValorProveedor(e.getFilaInicial(), e.getColumnaInicial(), e.getValor(), pro),
                        e.getAlineadoDerecha(), e.getAlineadoIzquierda(), e.getAlineadoCentro()));
            }
            return encabezado;
        }
        return null;
    }

    private String obtenerValorProveedor(Integer filaInicial, Integer columnaInicial, String valor, DatosProveedor proveedor) {
        if (valor != null && valor.isEmpty()) {
            if (proveedor != null && proveedor.getCodProveedor() != null && !proveedor.getCodProveedor().isEmpty()) {
                if (columnaInicial == 3 && filaInicial == 4) {
                    return proveedor.getNombreSocioNegocios();
                } else if (columnaInicial == 3 && filaInicial == 5) {
                    return proveedor.getDireccion();
                } else if (columnaInicial == 3 && filaInicial == 6) {
                    return proveedor.getTelefono();
                } else if (columnaInicial == 3 && filaInicial == 7) {
                    return proveedor.getPersonaContacto();
                } else if (columnaInicial == 3 && filaInicial == 8) {
                    return proveedor.getCorreo();
                } else if (columnaInicial == 3 && filaInicial == 10) {
                    return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                }
            }
        }
        return valor;
    }

    public List<ColumnaDatosProformaDTO> exportarPreviewProforma() {
        List<ConfiguracionProforma> tmpConfiguracion = configuracionProformaFacade.buscarPorCodigoProveedor(proveedor, null);
        List<ColumnaDatosProformaDTO> columnas = new ArrayList<>();
        if (tmpConfiguracion != null && !tmpConfiguracion.isEmpty()) {
            for (ConfiguracionProforma c : tmpConfiguracion) {
                if (columnas != null && !columnas.isEmpty() && columnas.get(0).getNombre().equals(c.getIdColumna().getNombre1())) {
                    if (columnas.get(0).getColumnas() == null) {
                        columnas.get(0).setColumnas(new ArrayList<ColumnaProformaDTO>());
                    }

                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        columnas.get(0).getColumnas().add(new ColumnaProformaDTO(0, c.getIdColumna().getIdColumna(),
                                c.getIdColumna().getDecimalesVisibles(),
                                c.getIdColumna().getIdOperacionColumna() == null ? null : c.getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(),
                                c.getIdColumna().getNombre1(), c.getIdColumna().getNombre1Ingles(), c.getIdColumna().getNombre2(),
                                c.getIdColumna().getNombre2Ingles(), c.getIdColumna().getLongitudOcupadaTabla(),
                                c.getIdColumna().getTipoCantidad(), c.getIdColumna().getPermitirCrearItem(),
                                c.getIdColumna().getRequiereOperacion(), c.getIdColumna().getTipoItem(), c.getIdColumna().getTipoIdentificador(),
                                c.getIdColumna().getTipoImagen(), c.getIdColumna().getObligatoria(), c.getIdColumna().getIncluirProforma(),
                                c.getIdColumna().getModificable(), c.getIdColumna().getModificableSiNuevo(), c.getIdColumna().getTipoValorTotal(),
                                c.getIdColumna().getTipoCBM(), c.getIdColumna().getTipoValorUnitario(), c.getIdColumna().getTipoDescuento(),
                                c.getIdColumna().getIdTipoDato() != null
                                        ? new TipoDatosDTO(c.getIdColumna().getIdTipoDato().getIdTipoDato(), c.getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                null));
                    }
                } else {
                    columnas.add(0, new ColumnaDatosProformaDTO(c.getOrden(),
                            c.getIdColumna().getLongitudOcupadaTabla() == null || c.getIdColumna().getLongitudOcupadaTabla().isEmpty() ? "100px"
                                    : c.getIdColumna().getLongitudOcupadaTabla(), c.getIdColumna().getNombre1(),
                            c.getIdColumna().getNombre1Ingles(), new ArrayList<ColumnaProformaDTO>()));

                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        columnas.get(0).getColumnas().add(new ColumnaProformaDTO(0, c.getIdColumna().getIdColumna(),
                                c.getIdColumna().getDecimalesVisibles(),
                                c.getIdColumna().getIdOperacionColumna() == null ? null : c.getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma(),
                                c.getIdColumna().getNombre1(), c.getIdColumna().getNombre1Ingles(), c.getIdColumna().getNombre2(),
                                c.getIdColumna().getNombre2Ingles(), c.getIdColumna().getLongitudOcupadaTabla(),
                                c.getIdColumna().getTipoCantidad(), c.getIdColumna().getPermitirCrearItem(),
                                c.getIdColumna().getRequiereOperacion(), c.getIdColumna().getTipoItem(), c.getIdColumna().getTipoIdentificador(),
                                c.getIdColumna().getTipoImagen(), c.getIdColumna().getObligatoria(), c.getIdColumna().getIncluirProforma(),
                                c.getIdColumna().getModificable(), c.getIdColumna().getModificableSiNuevo(), c.getIdColumna().getTipoValorTotal(),
                                c.getIdColumna().getTipoCBM(), c.getIdColumna().getTipoValorUnitario(), c.getIdColumna().getTipoDescuento(),
                                c.getIdColumna().getIdTipoDato() != null
                                        ? new TipoDatosDTO(c.getIdColumna().getIdTipoDato().getIdTipoDato(), c.getIdColumna().getIdTipoDato().getTipoDato()) : null, null));
                    }
                }
            }
            Collections.sort(columnas, new Comparator<ColumnaDatosProformaDTO>() {
                @Override
                public int compare(ColumnaDatosProformaDTO t, ColumnaDatosProformaDTO t1) {
                    return t.getPosicion() - t1.getPosicion();
                }
            });
        }
        return columnas;
    }

    public StreamedContent getExcelDownload() {
        log.log(Level.INFO, "Se inicia proceso de descarga de proforma para proveedor");
        String nombreReporte = "PI - Proveedor " + proveedor;
        ExcelGeneratorProforma excel = new ExcelGeneratorProforma(nombreReporte, exportarPreviewProforma(), encabezadoSuperiorProforma(proveedorFacade.find(proveedor)),
                proveedor, baruApplicationMBean.obtenerValorPropiedad("url.local.wildfly.proveedores"));
        try {
            excel.generarArchivoExcel();
        } catch (Exception e) {
            log.log(Level.INFO, e.getMessage());
            return null;
        }
        try {
            String file = nombreReporte + ".xlsx";
            InputStream stream = new ByteArrayInputStream(fileToBytes(new File(System.getProperty("jboss.server.temp.dir") + File.separator + file), file));
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", file);
        } catch (IOException e) {
            log.log(Level.SEVERE, e.getMessage());
            log.log(Level.SEVERE, "No se ha podido generar la PI");
            mostrarMensaje("Error", "No se ha podido generar la PI.", true, false, false);
            return null;
        }
    }

    private byte[] fileToBytes(File file, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) file.length()];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            log.log(Level.INFO, ex.getMessage());
        }
        byte[] bytes = bos.toByteArray();
        File someFile = new File(nombreArchivo);
        FileOutputStream fos = new FileOutputStream(someFile);
        fos.write(bytes);
        fos.flush();
        fos.close();
        return buf;
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
