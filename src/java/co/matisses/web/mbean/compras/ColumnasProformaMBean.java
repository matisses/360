package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.ColumnaProforma;
import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.entity.DetalleOperacionColumna;
import co.matisses.persistence.web.entity.Operacion;
import co.matisses.persistence.web.entity.OperacionColumnasProforma;
import co.matisses.persistence.web.entity.TipoDato;
import co.matisses.persistence.web.facade.ColumnaProformaFacade;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import co.matisses.persistence.web.facade.DetalleOperacionColumnaFacade;
import co.matisses.persistence.web.facade.OperacionColumnasProformaFacade;
import co.matisses.persistence.web.facade.OperacionFacade;
import co.matisses.persistence.web.facade.TipoDatoFacade;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.DetalleOperacionColumnaDTO;
import co.matisses.web.dto.OperacionDTO;
import co.matisses.web.dto.TipoDatosDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "columnasProformaMBean")
public class ColumnasProformaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ColumnasProformaMBean.class.getSimpleName());
    private int idColumna;
    private int pagina;
    private int filasXPagina;
    private int irAPagina;
    private Integer idTipoDato;
    private Integer decimalesVisibles;
    private Integer primerOperando;
    private Integer segundoOperando;
    private Integer idOperacion;
    private long paginas;
    private String nombre1;
    private String nombreIngles1;
    private String nombre2 = "";
    private String nombreIngles2 = "";
    private String longitudTabla;
    private String parametroBusqueda;
    private String tipoOperando;
    private String constante;
    private boolean ultimaPagina;
    private boolean editandoColumna = false;
    private boolean crearItem = false;
    private boolean tipoItem = false;
    private boolean tipoIdentificador = false;
    private boolean tipoImagen = false;
    private boolean tipoCantidad = false;
    private boolean tipoValorTotal = false;
    private boolean obligatoria = false;
    private boolean incluirProforma = false;
    private boolean modificable = false;
    private boolean modificarNuevo = false;
    private boolean requiereOperacion = false;
    private boolean dlgOperacion = false;
    private boolean tipoCBM = false;
    private boolean tipoValorUnitario = false;
    private boolean tipoDescuento = false;
    private boolean tipoDescripcionItem = false;
    private ColumnaProformaDTO columnaSeleccionada;
    private List<Integer> paginasVisibles;
    private List<ColumnaProformaDTO> columnasProforma;
    private List<ColumnaProformaDTO> columnas;
    private List<TipoDatosDTO> tipoDatos;
    private List<OperacionDTO> operaciones;
    private List<DetalleOperacionColumnaDTO> detalleOperaciones;
    @EJB
    private ColumnaProformaFacade columnaProformaFacade;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;
    @EJB
    private TipoDatoFacade tipoDatoFacade;
    @EJB
    private OperacionFacade operacionFacade;
    @EJB
    private DetalleOperacionColumnaFacade detalleOperacionColumnaFacade;
    @EJB
    private OperacionColumnasProformaFacade operacionColumnasProformaFacade;

    public ColumnasProformaMBean() {
        columnasProforma = new ArrayList<>();
        paginasVisibles = new ArrayList<>();
        pagina = 1;
        filasXPagina = 6;
        irAPagina = 1;
        tipoDatos = new ArrayList<>();
        columnas = new ArrayList<>();
        operaciones = new ArrayList<>();
        detalleOperaciones = new ArrayList<>();
        columnaSeleccionada = new ColumnaProformaDTO();
    }

    @PostConstruct
    protected void initialize() {
        obtenerTipoDatos();
        obtenerColumnasProforma();
    }

    public String getNombre1() {
        return nombre1;
    }

    public void setNombre1(String nombre1) {
        this.nombre1 = nombre1;
    }

    public String getNombreIngles1() {
        return nombreIngles1;
    }

    public void setNombreIngles1(String nombreIngles1) {
        this.nombreIngles1 = nombreIngles1;
    }

    public String getNombre2() {
        return nombre2;
    }

    public void setNombre2(String nombre2) {
        this.nombre2 = nombre2;
    }

    public String getNombreIngles2() {
        return nombreIngles2;
    }

    public void setNombreIngles2(String nombreIngles2) {
        this.nombreIngles2 = nombreIngles2;
    }

    public List<ColumnaProformaDTO> getColumnasProforma() {
        return columnasProforma;
    }

    public void setColumnasProforma(List<ColumnaProformaDTO> columnasProforma) {
        this.columnasProforma = columnasProforma;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getFilasXPagina() {
        return filasXPagina;
    }

    public void setFilasXPagina(int filasXPagina) {
        this.filasXPagina = filasXPagina;
    }

    public int getIrAPagina() {
        return irAPagina;
    }

    public void setIrAPagina(int irAPagina) {
        this.irAPagina = irAPagina;
    }

    public long getPaginas() {
        return paginas;
    }

    public void setPaginas(long paginas) {
        this.paginas = paginas;
    }

    public List<Integer> getPaginasVisibles() {
        return paginasVisibles;
    }

    public void setPaginasVisibles(List<Integer> paginasVisibles) {
        this.paginasVisibles = paginasVisibles;
    }

    public boolean isUltimaPagina() {
        return ultimaPagina;
    }

    public void setUltimaPagina(boolean ultimaPagina) {
        this.ultimaPagina = ultimaPagina;
    }

    public boolean isEditandoColumna() {
        return editandoColumna;
    }

    public void setEditandoColumna(boolean editandoColumna) {
        this.editandoColumna = editandoColumna;
    }

    public int getIdColumna() {
        return idColumna;
    }

    public void setIdColumna(int idColumna) {
        this.idColumna = idColumna;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public List<TipoDatosDTO> getTipoDatos() {
        return tipoDatos;
    }

    public void setTipoDatos(List<TipoDatosDTO> tipoDatos) {
        this.tipoDatos = tipoDatos;
    }

    public Integer getIdTipoDato() {
        return idTipoDato;
    }

    public void setIdTipoDato(Integer idTipoDato) {
        this.idTipoDato = idTipoDato;
    }

    public String getIdTipoDatoSeleccionado() {
        if (idTipoDato != null) {
            for (TipoDatosDTO t : tipoDatos) {
                if (t.getIdTipoDato().equals(idTipoDato)) {
                    return t.getTipoDato();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getDecimalesVisibles() {
        return decimalesVisibles;
    }

    public void setDecimalesVisibles(Integer decimalesVisibles) {
        this.decimalesVisibles = decimalesVisibles;
    }

    public String getLongitudTabla() {
        return longitudTabla;
    }

    public void setLongitudTabla(String longitudTabla) {
        this.longitudTabla = longitudTabla;
    }

    public boolean isTipoItem() {
        return tipoItem;
    }

    public void setTipoItem(boolean tipoItem) {
        this.tipoItem = tipoItem;
    }

    public boolean isTipoIdentificador() {
        return tipoIdentificador;
    }

    public void setTipoIdentificador(boolean tipoIdentificador) {
        this.tipoIdentificador = tipoIdentificador;
    }

    public boolean isCrearItem() {
        return crearItem;
    }

    public void setCrearItem(boolean crearItem) {
        this.crearItem = crearItem;
    }

    public boolean isTipoImagen() {
        return tipoImagen;
    }

    public void setTipoImagen(boolean tipoImagen) {
        this.tipoImagen = tipoImagen;
    }

    public boolean isRequiereOperacion() {
        return requiereOperacion;
    }

    public void setRequiereOperacion(boolean requiereOperacion) {
        this.requiereOperacion = requiereOperacion;
    }

    public boolean isObligatoria() {
        return obligatoria;
    }

    public void setObligatoria(boolean obligatoria) {
        this.obligatoria = obligatoria;
    }

    public boolean isModificarNuevo() {
        return modificarNuevo;
    }

    public void setModificarNuevo(boolean modificarNuevo) {
        this.modificarNuevo = modificarNuevo;
    }

    public boolean isModificable() {
        return modificable;
    }

    public void setModificable(boolean modificable) {
        this.modificable = modificable;
    }

    public boolean isIncluirProforma() {
        return incluirProforma;
    }

    public void setIncluirProforma(boolean incluirProforma) {
        this.incluirProforma = incluirProforma;
    }

    public boolean isTipoValorTotal() {
        return tipoValorTotal;
    }

    public void setTipoValorTotal(boolean tipoValorTotal) {
        this.tipoValorTotal = tipoValorTotal;
    }

    public boolean isTipoCantidad() {
        return tipoCantidad;
    }

    public void setTipoCantidad(boolean tipoCantidad) {
        this.tipoCantidad = tipoCantidad;
    }

    public List<ColumnaProformaDTO> getColumnas() {
        return columnas;
    }

    public void setColumnas(List<ColumnaProformaDTO> columnas) {
        this.columnas = columnas;
    }

    public Integer getPrimerOperando() {
        return primerOperando;
    }

    public String getPrimerOperandoSeleccionado() {
        if (primerOperando != null) {
            for (ColumnaProformaDTO c : columnas) {
                if (c.getIdColumna().equals(primerOperando)) {
                    if (c.getNombre2() == null || c.getNombre2().isEmpty()) {
                        return c.getNombre1();
                    } else {
                        return c.getNombre1() + " - " + c.getNombre2();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public Integer getSegundoOperando() {
        return segundoOperando;
    }

    public String getSegundoOperandoSeleccionado() {
        if (segundoOperando != null) {
            for (ColumnaProformaDTO c : columnas) {
                if (c.getIdColumna().equals(segundoOperando)) {
                    if (c.getNombre2() == null || c.getNombre2().isEmpty()) {
                        return c.getNombre1();
                    } else {
                        return c.getNombre1() + " - " + c.getNombre2();
                    }
                }
            }
        }
        return "Seleccione";
    }

    public String getTipoOperando() {
        return tipoOperando;
    }

    public String getTipoOperandoSeleccionado() {
        if (tipoOperando != null) {
            switch (tipoOperando) {
                case "columna":
                    return "Columna";
                case "constante":
                    return "Constante";
                default:
                    return "Seleccione";
            }
        }
        return "Seleccione";
    }

    public boolean isDlgOperacion() {
        return dlgOperacion;
    }

    public void setDlgOperacion(boolean dlgOperacion) {
        this.dlgOperacion = dlgOperacion;
    }

    public boolean isTipoCBM() {
        return tipoCBM;
    }

    public void setTipoCBM(boolean tipoCBM) {
        this.tipoCBM = tipoCBM;
    }

    public boolean isTipoValorUnitario() {
        return tipoValorUnitario;
    }

    public void setTipoValorUnitario(boolean tipoValorUnitario) {
        this.tipoValorUnitario = tipoValorUnitario;
    }

    public boolean isTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(boolean tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public boolean isTipoDescripcionItem() {
        return tipoDescripcionItem;
    }

    public void setTipoDescripcionItem(boolean tipoDescripcionItem) {
        this.tipoDescripcionItem = tipoDescripcionItem;
    }

    public Integer getIdOperacion() {
        return idOperacion;
    }

    public String getIdOperacionSeleccionada() {
        if (idOperacion != null) {
            for (OperacionDTO o : operaciones) {
                if (o.getIdOperacion().equals(idOperacion)) {
                    return o.getOperacion();
                }
            }
        }
        return "Seleccione";
    }

    public List<OperacionDTO> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<OperacionDTO> operaciones) {
        this.operaciones = operaciones;
    }

    public String getConstante() {
        return constante;
    }

    public void setConstante(String constante) {
        this.constante = constante;
    }

    public List<DetalleOperacionColumnaDTO> getDetalleOperaciones() {
        return detalleOperaciones;
    }

    public void setDetalleOperaciones(List<DetalleOperacionColumnaDTO> detalleOperaciones) {
        this.detalleOperaciones = detalleOperaciones;
    }

    public void seleccionarCrearItem() {
        if (crearItem) {
            crearItem = false;
        } else {
            crearItem = true;
        }
    }

    public void seleccionarTipoItem() {
        if (tipoItem) {
            tipoItem = false;
        } else {
            tipoItem = true;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoCantidad = false;
            tipoValorTotal = false;
            tipoCBM = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoIdentificador() {
        if (tipoIdentificador) {
            tipoIdentificador = false;
        } else {
            tipoIdentificador = true;
            tipoItem = false;
            tipoImagen = false;
            tipoCantidad = false;
            tipoValorTotal = false;
            tipoCBM = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoImagen() {
        if (tipoImagen) {
            tipoImagen = false;
        } else {
            tipoImagen = true;
            tipoItem = false;
            tipoIdentificador = false;
            tipoCantidad = false;
            tipoValorTotal = false;
            tipoCBM = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarObligatoria() {
        if (obligatoria) {
            obligatoria = false;
        } else {
            obligatoria = true;
        }
    }

    public void seleccionarIncluirProforma() {
        if (incluirProforma) {
            incluirProforma = false;
        } else {
            incluirProforma = true;
        }
    }

    public void seleccionarModificable() {
        if (modificable) {
            modificable = false;
        } else {
            modificable = true;
        }
    }

    public void seleccionarModificarNuevo() {
        if (modificarNuevo) {
            modificarNuevo = false;
        } else {
            modificarNuevo = true;
        }
    }

    public void seleccionarRequiereOperacion() {
        if (requiereOperacion) {
            requiereOperacion = false;
            dlgOperacion = false;
        } else {
            constante = null;
            primerOperando = null;
            segundoOperando = null;
            idOperacion = null;
            tipoOperando = null;
            requiereOperacion = true;
            dlgOperacion = true;
            obtenerColumnas();
            obtenerOperaciones();
        }
    }

    public void seleccionarTipoValorTotal() {
        if (tipoValorTotal) {
            tipoValorTotal = false;
        } else {
            tipoValorTotal = true;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoCantidad = false;
            tipoCBM = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoCantidad() {
        if (tipoCantidad) {
            tipoCantidad = false;
        } else {
            tipoCantidad = true;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoValorTotal = false;
            tipoCBM = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoCBM() {
        if (tipoCBM) {
            tipoCBM = false;
        } else {
            tipoCBM = true;
            tipoCantidad = false;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoValorTotal = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoValorUnitario() {
        if (tipoValorUnitario) {
            tipoValorUnitario = false;
        } else {
            tipoCBM = false;
            tipoCantidad = false;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoValorTotal = false;
            tipoValorUnitario = true;
            tipoDescuento = false;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoDescuento() {
        if (tipoDescuento) {
            tipoDescuento = false;
        } else {
            tipoCBM = false;
            tipoCantidad = false;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoValorTotal = false;
            tipoValorUnitario = false;
            tipoDescuento = true;
            tipoDescripcionItem = false;
        }
    }

    public void seleccionarTipoDescripcionItem() {
        if (tipoDescripcionItem) {
            tipoDescripcionItem = false;
        } else {
            tipoCBM = false;
            tipoCantidad = false;
            tipoItem = false;
            tipoIdentificador = false;
            tipoImagen = false;
            tipoValorTotal = false;
            tipoValorUnitario = false;
            tipoDescuento = false;
            tipoDescripcionItem = true;
        }
    }

    public void seleccionarDatoColumna() {
        idTipoDato = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoDato"));
        log.log(Level.INFO, "Se selecciono el tipo de dato [{0}]", idTipoDato);
    }

    public void seleccionarPrimerOperando() {
        primerOperando = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPrimerOperando"));
        log.log(Level.INFO, "Se selecciono el id columna [{0}], para el primer operando", primerOperando);
    }

    public void seleccionarTipoOperando() {
        tipoOperando = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoOperando");
        log.log(Level.INFO, "Se selecciono el tipo operando [{0}]", tipoOperando);
        segundoOperando = null;
        constante = null;
    }

    public void seleccionarOperacion() {
        idOperacion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idOperacion"));
        log.log(Level.INFO, "Se selecciono el id operacion [{0}]", idOperacion);
    }

    public void seleccionarSegundoOperando() {
        segundoOperando = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSegundoOperando"));
        log.log(Level.INFO, "Se selecciono el id columna [{0}], para el segundo operando", segundoOperando);
    }

    private void obtenerColumnas() {
        columnas.clear();

        List<ColumnaProforma> cols = columnaProformaFacade.findAll();

        if (cols != null && !cols.isEmpty()) {
            for (ColumnaProforma col : cols) {
                columnas.add(new ColumnaProformaDTO(0, col.getIdColumna(),
                        col.getDecimalesVisibles(), col.getIdOperacionColumna() == null ? null : col.getIdOperacionColumna().getIdOperacionColumnaProforma(),
                        col.getNombre1(), col.getNombre1Ingles(), col.getNombre2(), col.getNombre2Ingles(), col.getLongitudOcupadaTabla(),
                        col.getTipoCantidad(), col.getPermitirCrearItem(), col.getRequiereOperacion(), col.getTipoItem(), col.getTipoIdentificador(),
                        col.getTipoImagen(), col.getObligatoria(), col.getIncluirProforma(), col.getModificable(), col.getModificableSiNuevo(),
                        col.getTipoValorTotal(), col.getTipoCBM(), col.getTipoValorUnitario(), col.getTipoDescuento(),
                        col.getIdTipoDato() != null
                                ? new TipoDatosDTO(col.getIdTipoDato().getIdTipoDato(), col.getIdTipoDato().getTipoDato()) : null, null));
            }
        }

        Collections.sort(columnas, new Comparator<ColumnaProformaDTO>() {
            @Override
            public int compare(ColumnaProformaDTO t, ColumnaProformaDTO t1) {
                return t.getNombre1().compareTo(t1.getNombre1());
            }
        });
    }

    private void obtenerOperaciones() {
        operaciones.clear();

        List<Operacion> operations = operacionFacade.findAll();

        if (operations != null && !operations.isEmpty()) {
            for (Operacion operation : operations) {
                operaciones.add(new OperacionDTO(operation.getIdOperacion(), operation.getOperacion()));
            }
        }
    }

    public void guardarOperacionColumna() {
        if (primerOperando == null || primerOperando == 0) {
            mostrarMensaje("Error", "Seleccione una columna para el operando 1", true, false, false);
            log.log(Level.SEVERE, "Seleccione una columna para el operando 1");
            return;
        }
        if (tipoOperando == null || tipoOperando.isEmpty()) {
            mostrarMensaje("Error", "Seleccione un tipo operando para la columna 2", true, false, false);
            log.log(Level.SEVERE, "Seleccione un tipo operando para la columna 2");
            return;
        }
        if (idOperacion == null || idOperacion == 0) {
            mostrarMensaje("Error", "Seleccione una operación", true, false, false);
            log.log(Level.SEVERE, "Seleccione una operacion");
            return;
        }
        if ((segundoOperando == null || segundoOperando == 0) && (constante == null || constante.isEmpty())) {
            mostrarMensaje("Error", "Ingrese un parametro para el operador 2", true, false, false);
            log.log(Level.SEVERE, "Ingrese un parametro para el operador 2");
            return;
        }

        if (detalleOperaciones != null && !detalleOperaciones.isEmpty()) {
            DetalleOperacionColumna operation = detalleOperacionColumnaFacade.find(detalleOperaciones.get(0).getIdDetalleOperacion());

            if (operation != null && operation.getIdDetalleOperacion() != null && operation.getIdDetalleOperacion() != 0) {
                operation.setIdColumna1(new ColumnaProforma(primerOperando));
                if (constante != null && !constante.isEmpty()) {
                    operation.setConstante(constante);
                    operation.setIdColumna2(null);
                } else if (segundoOperando != null && segundoOperando != 0) {
                    operation.setIdColumna2(new ColumnaProforma(segundoOperando));
                    operation.setConstante(null);
                }
                operation.setIdOperacion(new Operacion(idOperacion));

                try {
                    detalleOperacionColumnaFacade.edit(operation);
                    log.log(Level.INFO, "Se modifico detalle de operacion");
                    obtenerDetalleOperaciones(operation.getIdDetalleOperacion());
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    mostrarMensaje("Error", "No se pudo modificar la operación", true, false, false);
                    return;
                }
            } else {
                mostrarMensaje("Error", "No se pudo modificar la operación para la columna", true, false, false);
                log.log(Level.SEVERE, "No se pudo modificar la operacion para la columna");
                return;
            }
        } else {
            OperacionColumnasProforma opc = agregarEncabezadoOperacion();
            if (opc != null) {
                DetalleOperacionColumna operation = new DetalleOperacionColumna();

                operation.setIdColumna1(new ColumnaProforma(primerOperando));
                if (constante != null && !constante.isEmpty()) {
                    operation.setConstante(constante);
                    operation.setIdColumna2(null);
                } else if (segundoOperando != null && segundoOperando != 0) {
                    operation.setIdColumna2(new ColumnaProforma(segundoOperando));
                    operation.setConstante(null);
                }
                operation.setIdOperacion(new Operacion(idOperacion));
                operation.setIdOperacionColumnaProforma(opc);
                operation.setOrden(1);

                try {
                    detalleOperacionColumnaFacade.create(operation);
                    log.log(Level.INFO, "Se creo detalle de operacion");
                    obtenerDetalleOperaciones(operation.getIdDetalleOperacion());
                } catch (Exception e) {
                    log.log(Level.SEVERE, e.getMessage());
                    mostrarMensaje("Error", "No se pudo crear la operación", true, false, false);
                    operacionColumnasProformaFacade.remove(opc);
                    return;
                }
            } else {
                mostrarMensaje("Error", "No se pudo crear la operación para la columna", true, false, false);
                log.log(Level.SEVERE, "No se pudo crear la operacion para la columna");
                return;
            }
        }
    }

    private OperacionColumnasProforma agregarEncabezadoOperacion() {
        try {
            OperacionColumnasProforma ocp = new OperacionColumnasProforma();
            operacionColumnasProformaFacade.create(ocp);

            return ocp;
        } catch (Exception e) {
            log.log(Level.SEVERE, e.getMessage());
            return null;
        }
    }

    private void obtenerDetalleOperaciones(Integer idDetalleOperacion) {
        detalleOperaciones.clear();

        if (requiereOperacion) {
            List<DetalleOperacionColumna> doc = new ArrayList<>();
            if (columnaSeleccionada != null && columnaSeleccionada.getIdColumna() != null && columnaSeleccionada.getIdColumna() != 0 && idDetalleOperacion == null) {
                doc = detalleOperacionColumnaFacade.obtenerOperaciones(columnaSeleccionada.getIdOperacionColumna() != null ? columnaSeleccionada.getIdOperacionColumna() : 0);
            } else if (idDetalleOperacion != null) {
                doc = detalleOperacionColumnaFacade.obtenerOperaciones(idDetalleOperacion);
            }

            if (doc != null && !doc.isEmpty()) {
                for (DetalleOperacionColumna d : doc) {
                    detalleOperaciones.add(new DetalleOperacionColumnaDTO(d.getIdDetalleOperacion(), d.getOrden(), d.getIdOperacionColumnaProforma().getIdOperacionColumnaProforma(),
                            d.getConstante(),
                            d.getIdColumna1() != null
                                    ? new ColumnaProformaDTO(d.getIdColumna1().getIdColumna(), d.getIdColumna1().getNombre1(), d.getIdColumna1().getNombre2()) : null,
                            d.getIdColumna2() != null
                                    ? new ColumnaProformaDTO(d.getIdColumna2().getIdColumna(), d.getIdColumna2().getNombre1(), d.getIdColumna2().getNombre2()) : null,
                            new OperacionDTO(d.getIdOperacion().getIdOperacion(), d.getIdOperacion().getOperacion())));
                }
            }
        }
    }

    public void obtenerDatosOperacion() {
        if (detalleOperaciones != null && !detalleOperaciones.isEmpty()) {
            constante = detalleOperaciones.get(0).getConstante();
            primerOperando = detalleOperaciones.get(0).getIdColumna1().getIdColumna();
            if (detalleOperaciones.get(0).getIdColumna2() != null) {
                segundoOperando = detalleOperaciones.get(0).getIdColumna2().getIdColumna();
            }
            idOperacion = detalleOperaciones.get(0).getIdOperacion().getIdOperacion();

            if (constante != null && !constante.isEmpty()) {
                tipoOperando = "constante";
            } else {
                tipoOperando = "columna";
            }
            dlgOperacion = true;
            obtenerColumnas();
            obtenerOperaciones();
        }
    }

    private void obtenerTipoDatos() {
        tipoDatos = new ArrayList<>();
        List<TipoDato> tipos = tipoDatoFacade.findAll();
        for (TipoDato tipo : tipos) {
            TipoDatosDTO t = new TipoDatosDTO();
            t.setIdTipoDato(tipo.getIdTipoDato());
            t.setTipoDato(tipo.getTipoDato());
            tipoDatos.add(t);
        }
        Collections.sort(tipoDatos, new Comparator<TipoDatosDTO>() {
            @Override
            public int compare(TipoDatosDTO o1, TipoDatosDTO o2) {
                return o1.getTipoDato().compareTo(o2.getTipoDato());
            }
        });
    }

    public void obtenerColumnasProforma() {
        long nroRegistros = columnaProformaFacade.obtenerTotalDatos(parametroBusqueda);
        columnasProforma.clear();
        ultimaPagina = false;
        editandoColumna = false;
        List<ColumnaProforma> tmpColumnasProforma = columnaProformaFacade.obtenerColumnas(pagina, filasXPagina, parametroBusqueda);
        interpretarDatosColumnas(tmpColumnasProforma);

        if (nroRegistros % filasXPagina > 0) {
            paginas = (nroRegistros / filasXPagina) + 1;
        } else {
            paginas = nroRegistros / filasXPagina;
        }
        if (columnasProforma.size() < filasXPagina || pagina == paginas) {
            ultimaPagina = true;
        }
        construirPaginador();
    }

    public void buscarColumnas() {
        paginas = 0;
        obtenerColumnasProforma();
    }

    private void interpretarDatosColumnas(List<ColumnaProforma> tmpColumnasProforma) {
        for (ColumnaProforma col : tmpColumnasProforma) {
            columnasProforma.add(new ColumnaProformaDTO(0, col.getIdColumna(),
                    col.getDecimalesVisibles(), col.getIdOperacionColumna() != null ? col.getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                    col.getNombre1(), col.getNombre1Ingles(), col.getNombre2(), col.getNombre2Ingles(), col.getLongitudOcupadaTabla(),
                    col.getTipoCantidad(), col.getPermitirCrearItem(), col.getRequiereOperacion(), col.getTipoItem(), col.getTipoIdentificador(), col.getTipoImagen(),
                    col.getObligatoria(), col.getIncluirProforma(), col.getModificable(), col.getModificableSiNuevo(), col.getTipoValorTotal(),
                    col.getTipoCBM(), col.getTipoValorUnitario(), col.getTipoDescuento(), col.getTipoDescripcionItem() != null ? col.getTipoDescripcionItem() : false,
                    col.getIdTipoDato() != null
                            ? new TipoDatosDTO(col.getIdTipoDato().getIdTipoDato(), col.getIdTipoDato().getTipoDato()) : null,
                    null));
        }
    }

    public void obtenerDatosColumnaProforma() {
        idColumna = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idColumna"));
        for (ColumnaProformaDTO dto : columnasProforma) {
            if (dto.getIdColumna().equals(idColumna)) {
                if (dto.getNombre1().equals("[Sin Asignar]")) {
                    nombre1 = "";
                } else {
                    nombre1 = dto.getNombre1();
                }
                if (dto.getNombre1Ingles().equals("[Sin Asignar]")) {
                    nombreIngles1 = "";
                } else {
                    nombreIngles1 = dto.getNombre1Ingles();
                }
                if (dto.getNombre2().equals("[Sin Asignar]")) {
                    nombre2 = "";
                } else {
                    nombre2 = dto.getNombre2();
                }
                if (dto.getNombre2Ingles().equals("[Sin Asignar]")) {
                    nombreIngles2 = "";
                } else {
                    nombreIngles2 = dto.getNombre2Ingles();
                }
                if (dto.getIdTipoDato() == null) {
                    idTipoDato = 0;
                } else {
                    idTipoDato = dto.getIdTipoDato().getIdTipoDato();
                }
                decimalesVisibles = dto.getDecimalesVisibles();
                longitudTabla = dto.getLongitudOcupadaTabla();
                crearItem = dto.isPermitirCrearItem();
                tipoItem = dto.isTipoItem();
                tipoIdentificador = dto.isTipoIdentificador();
                tipoImagen = dto.isTipoImagen();
                tipoCantidad = dto.isTipoCantidad();
                tipoValorTotal = dto.isTipoValorTotal();
                obligatoria = dto.isObligatoria();
                incluirProforma = dto.isIncluirProforma();
                modificable = dto.isModificable();
                modificarNuevo = dto.isModificableSiNuevo();
                requiereOperacion = dto.isRequiereOperacion();
                tipoCBM = dto.isTipoCBM();
                tipoValorUnitario = dto.isTipoValorUnitario();
                tipoDescuento = dto.isTipoDescuento();
                tipoDescripcionItem = dto.isTipoDescripcionItem();

                editandoColumna = true;
                columnaSeleccionada = dto;
                obtenerDetalleOperaciones(null);
                break;
            }
        }
    }

    public void limpiar() {
        idColumna = 0;
        idTipoDato = null;
        decimalesVisibles = null;
        primerOperando = null;
        segundoOperando = null;
        idOperacion = null;
        nombre1 = null;
        nombreIngles1 = null;
        nombre2 = null;
        nombreIngles2 = null;
        longitudTabla = null;
        tipoOperando = null;
        constante = null;
        editandoColumna = false;
        crearItem = false;
        tipoItem = false;
        tipoIdentificador = false;
        tipoImagen = false;
        tipoCantidad = false;
        tipoValorTotal = false;
        tipoDescripcionItem = false;
        obligatoria = false;
        incluirProforma = false;
        modificable = false;
        modificarNuevo = false;
        requiereOperacion = false;
        dlgOperacion = false;
        tipoCBM = false;
        tipoValorUnitario = false;
        columnaSeleccionada = new ColumnaProformaDTO();
        detalleOperaciones = new ArrayList<>();
    }

    public Integer guardarDatosColumna() {
        if (nombre1 == null || nombre1.isEmpty()) {
            mostrarMensaje("Sin Columna", "No sé han ingresado datos para el campo Columna", true, false, false);
            log.log(Level.SEVERE, "No se han ingresado datos para el campo Columna");
            return null;
        }
        if (nombreIngles1 == null || nombreIngles1.isEmpty()) {
            mostrarMensaje("Sin Columna Inglés", "No sé han ingresado datos para el campo Columna Inglés", true, false, false);
            log.log(Level.SEVERE, "No se han ingresado datos para el campo Columna Ingles");
            return null;
        }
        if (idTipoDato == null || idTipoDato == 0) {
            mostrarMensaje("Sin Tipo Dato", "No sé a seleccionado un tipo de dato para la columna", true, false, false);
            log.log(Level.SEVERE, "No se a seleccionado un tipo de dato para la columna");
            return null;
        }
        if (nombre2 != null && !nombre2.isEmpty()) {
            if (nombreIngles2 == null || nombreIngles2.isEmpty()) {
                mostrarMensaje("Sin SubColumna Inglés", "No sé han ingresado datos para el campo SubColumna Inglés", true, false, false);
                log.log(Level.SEVERE, "No se han ingresado datos para el campo SubColumna Ingles");
                return null;
            }
        } else if (nombreIngles2 != null && !nombreIngles2.isEmpty()) {
            if (nombre2 == null || nombre2.isEmpty()) {
                mostrarMensaje("Sin Columna", "No sé han ingresado datos para el campo SubColumna", true, false, false);
                log.log(Level.SEVERE, "No se han ingresado datos para el campo SubColumna");
                return null;
            }
        }

        ColumnaProforma columna = new ColumnaProforma();
        columna.setDecimalesVisibles(decimalesVisibles);
        if (detalleOperaciones != null && !detalleOperaciones.isEmpty()) {
            columna.setIdOperacionColumna(new OperacionColumnasProforma(detalleOperaciones.get(0).getIdOperacionColumnaProforma()));
        }
        columna.setIdTipoDato(new TipoDato(idTipoDato));
        columna.setIncluirProforma(incluirProforma);
        columna.setLongitudOcupadaTabla(longitudTabla);
        columna.setModificable(modificable);
        columna.setModificableSiNuevo(modificarNuevo);
        columna.setNombre1(nombre1);
        columna.setNombre1Ingles(nombreIngles1);
        columna.setNombre2(nombre2);
        columna.setNombre2Ingles(nombreIngles2);
        columna.setObligatoria(obligatoria);
        columna.setPermitirCrearItem(crearItem);
        if (requiereOperacion && detalleOperaciones != null && !detalleOperaciones.isEmpty()) {
            columna.setRequiereOperacion(true);
        } else {
            columna.setRequiereOperacion(false);
        }
        columna.setTipoCantidad(tipoCantidad);
        columna.setTipoIdentificador(tipoIdentificador);
        columna.setTipoImagen(tipoImagen);
        columna.setTipoItem(tipoItem);
        columna.setTipoValorTotal(tipoValorTotal);
        columna.setTipoCBM(tipoCBM);
        columna.setTipoValorUnitario(tipoValorUnitario);
        columna.setTipoDescuento(tipoDescuento);
        columna.setTipoDescripcionItem(tipoDescripcionItem);

        if (editandoColumna) {
            if (idColumna == 0) {
                mostrarMensaje("Sin datos", "No se encontró la columna que desea modificar", true, false, false);
                log.log(Level.SEVERE, "No se encontro la columna que desea modificar");
                return null;
            }
            columna.setIdColumna(idColumna);
            try {
                columnaProformaFacade.edit(columna);
                mostrarMensaje("Éxito", "Columna " + nombre1 + ", editada correctamente", false, true, false);
                log.log(Level.INFO, "Columna [{0}], editada correctamente", nombre1);
            } catch (Exception e) {
                log.log(Level.SEVERE, "La columna seleccionada no se pudo editar. Error: [{0}]", e.getMessage());
                mostrarMensaje("Error", "La columna seleccionada no se pudo editar", true, false, false);
                return null;
            }
        } else {
            try {
                columnaProformaFacade.create(columna);
                mostrarMensaje("Éxito", "Columna " + nombre1 + ", creada correctamente", false, true, false);
                log.log(Level.INFO, "Columna [{0}], creada correctamente", nombre1);
            } catch (Exception e) {
                log.log(Level.SEVERE, "No se pudo crear la columna. Error: [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudo crear la columna", true, false, false);
                return null;
            }
        }
        obtenerColumnasProforma();
        limpiar();
        return columna.getIdColumna();
    }

    public void eliminarColumnaProforma() {
        if (idColumna == 0) {
            mostrarMensaje("Error", "No se encontro la columna que desea modificar", true, false, false);
            log.log(Level.SEVERE, "No se encontro la columna que desea modificar");
            return;
        }
        ColumnaProforma columna = new ColumnaProforma();
        columna.setNombre1(nombre1);
        columna.setNombre1Ingles(nombreIngles1);
        columna.setNombre2(nombre2);
        columna.setNombre2Ingles(nombreIngles2);
        columna.setIdColumna(idColumna);
        columna.setIdTipoDato(new TipoDato(idTipoDato));
        try {
            if (!eliminarConfiguracionProformaRelacionada(columna)) {
                columnaProformaFacade.remove(columna);
                mostrarMensaje("Éxito", "Columna " + nombre1 + ", eliminada correctamente", false, true, false);
                log.log(Level.INFO, "Columna [{0}], eliminada correctamente", nombre1);
                limpiar();
            } else {
                mostrarMensaje("Error", "No se puede eliminar la columna seleccionada, debido a que está en uso", true, false, false);
                log.log(Level.SEVERE, "No se puede eliminar la columna seleccionada, debido a que esta en uso");
                return;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo eliminar la columna seleccionada. Error: [{0}]", e.getMessage());
            mostrarMensaje("Error", "No se pudo eliminar la columna seleccionada", true, false, false);
            return;
        }
        obtenerColumnasProforma();
    }

    private boolean eliminarConfiguracionProformaRelacionada(ColumnaProforma columnaProforma) {
        if (columnaProforma != null && columnaProforma.getIdColumna() != null && columnaProforma.getIdColumna() != 0) {
            List<ConfiguracionProforma> conf = configuracionProformaFacade.obtenerConfiguracionColumna(columnaProforma.getIdColumna());
            if (conf != null && conf.size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void construirPaginador() {
        paginasVisibles = new ArrayList<>();
        int posIni, posFin;
        if (pagina == 1) {
            posIni = pagina - 0;
            posFin = pagina + 4;
        } else if (pagina == 2) {
            posIni = pagina - 1;
            posFin = pagina + 3;
        } else if (pagina > 2 && pagina <= (paginas - 2)) {
            posIni = pagina - 2;
            posFin = pagina + 2;
        } else if (paginas - pagina == 1) {
            posIni = (pagina - 3) == 0 ? 1 : pagina - 3;
            posFin = pagina + 1;
        } else {
            posIni = (pagina - 4) <= 0 ? 1 : pagina - 4;
            posFin = (int) paginas;
        }
        for (int i = posIni; i <= posFin && i <= paginas; i++) {
            paginasVisibles.add(i);
        }
    }

    public void irAPaginaEspecifica() {
        irAPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("irAPagina"));
        if (irAPagina <= paginas && irAPagina >= 1) {
            pagina = irAPagina;
            obtenerColumnasProforma();
        }
    }

    public void siguientePagina() {
        if (pagina < paginas) {
            pagina++;
            obtenerColumnasProforma();
        }
    }

    public void anteriorPagina() {
        if (pagina > 1) {
            pagina--;
            obtenerColumnasProforma();
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
