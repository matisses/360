package co.matisses.web.mbean.compras;

import co.matisses.persistence.web.entity.ConfiguracionProforma;
import co.matisses.persistence.web.entity.ContenedorProforma;
import co.matisses.persistence.web.entity.DetalleProforma;
import co.matisses.persistence.web.entity.LineaIgnoradaProforma;
import co.matisses.persistence.web.entity.ProformaInvoice;
import co.matisses.persistence.web.facade.ConfiguracionProformaFacade;
import co.matisses.persistence.web.facade.ContenedorLineaFacade;
import co.matisses.persistence.web.facade.ContenedorProformaFacade;
import co.matisses.persistence.web.facade.DetalleProformaFacade;
import co.matisses.persistence.web.facade.LineaIgnoradaProformaFacade;
import co.matisses.persistence.web.facade.ProformaInvoiceFacade;
import co.matisses.web.dto.ColumnaProformaDTO;
import co.matisses.web.dto.DatosProformaDTO;
import co.matisses.web.dto.DetalleProformaDTO;
import co.matisses.web.dto.ColumnaDatosProformaDTO;
import co.matisses.web.dto.ProformaInvoiceDTO;
import co.matisses.web.dto.TipoDatosDTO;
import co.matisses.web.dto.TipoMonedaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.session.ProformaSessionMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
 * @author ygil
 */
@ViewScoped
@Named(value = "tablaProformaMBean")
public class TablaProformaMBean implements Serializable {

    @Inject
    private ProformaSessionMBean proformaSession;
    @Inject
    private BaruApplicationMBean aplicacionBean;
    private static final Logger log = Logger.getLogger(TablaProformaMBean.class.getSimpleName());
    private String parametroBusqueda;
    private boolean buscando = false;
    private ProformaInvoiceDTO proforma;
    private List<DatosProformaDTO> datosProforma;
    private List<DatosProformaDTO> datosProformaFull;
    private List<LineaIgnoradaProforma> lineasIgnoradas;
    private List<ColumnaDatosProformaDTO> encabezado;
    @EJB
    private DetalleProformaFacade detalleProformaFacade;
    @EJB
    private ConfiguracionProformaFacade configuracionProformaFacade;
    @EJB
    private LineaIgnoradaProformaFacade lineaIgnoradaProformaFacade;
    @EJB
    private ContenedorProformaFacade contenedorProformaFacade;
    @EJB
    private ContenedorLineaFacade contenedorLineaFacade;
    @EJB
    private ProformaInvoiceFacade proformaInvoiceFacade;

    public TablaProformaMBean() {
        proforma = new ProformaInvoiceDTO();
        datosProforma = new ArrayList<>();
        lineasIgnoradas = new ArrayList<>();
        encabezado = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        Integer idProforma = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProforma"));
        if (idProforma != null && idProforma != 0) {
            ProformaInvoice p = proformaInvoiceFacade.find(idProforma);

            if (p != null && p.getIdProforma() != null && p.getIdProforma() != 0) {
                proforma = new ProformaInvoiceDTO(p.getIdProforma(), p.getConsecutivo(),
                        p.getIdPuertoLlegada() != null && p.getIdPuertoLlegada().getIdPuertoMundo() != null ? p.getIdPuertoLlegada().getIdPuertoMundo() : null,
                        p.getIdPuertoSalida() != null && p.getIdPuertoSalida().getIdPuertoMundo() != null ? p.getIdPuertoSalida().getIdPuertoMundo() : null,
                        p.getIdIncoterm() != null && p.getIdIncoterm().getIdIncoterm() != null ? p.getIdIncoterm().getIdIncoterm() : null,
                        p.getCbmTotal(), p.getValorTotal(), p.getValorTotalDescuento(), 0.0, 0.0, p.getCodProveedor(), p.getAnio(), p.getUsuario(), p.getEstado(), null,
                        p.getComentario(), p.getTipoProducto(), p.getTerminosPago(), p.getTerminosEntrega(), p.getPrimeraCarga(), p.getFecha(),
                        p.getIdTipoMoneda() == null ? null : new TipoMonedaDTO(p.getIdTipoMoneda().getIdTipoMoneda(), p.getIdTipoMoneda().getSimbolo(), p.getIdTipoMoneda().getMoneda()));
            }
            obtenerDatosProforma();
        }
    }

    public ProformaSessionMBean getProformaSession() {
        return proformaSession;
    }

    public void setProformaSession(ProformaSessionMBean proformaSession) {
        this.proformaSession = proformaSession;
    }

    public BaruApplicationMBean getAplicacionBean() {
        return aplicacionBean;
    }

    public void setAplicacionBean(BaruApplicationMBean aplicacionBean) {
        this.aplicacionBean = aplicacionBean;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public boolean isBuscando() {
        return buscando;
    }

    public void setBuscando(boolean buscando) {
        this.buscando = buscando;
    }

    public List<DatosProformaDTO> getDatosProforma() {
        return datosProforma;
    }

    public void setDatosProforma(List<DatosProformaDTO> datosProforma) {
        this.datosProforma = datosProforma;
    }

    public List<ColumnaDatosProformaDTO> getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(List<ColumnaDatosProformaDTO> encabezado) {
        this.encabezado = encabezado;
    }

    public void aplicarBusqueda() {
        if (buscando) {
            buscando = false;
        } else {
            buscando = true;
        }
    }

    private void obtenerConfiguracionAMostrar() {
        List<ConfiguracionProforma> confs = configuracionProformaFacade.obtenerConfiguracionesProforma(proforma.getIdProforma());

        if (confs != null && !confs.isEmpty()) {
            for (ConfiguracionProforma c : confs) {
                boolean existe = false;
                if (encabezado != null && !encabezado.isEmpty() && encabezado.get(0).getNombre().equals(c.getIdColumna().getNombre1())) {
                    if (encabezado.get(0).getColumnas() == null) {
                        encabezado.get(0).setColumnas(new ArrayList<ColumnaProformaDTO>());
                    }
                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        encabezado.get(0).getColumnas().add(new ColumnaProformaDTO(0, c.getIdColumna().getIdColumna(),
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
                    existe = true;
                } else {
                    existe = false;
                }

                if (!existe) {
                    encabezado.add(0, new ColumnaDatosProformaDTO(c.getOrden(),
                            c.getIdColumna().getLongitudOcupadaTabla() == null || c.getIdColumna().getLongitudOcupadaTabla().isEmpty() ? "100px"
                                    : c.getIdColumna().getLongitudOcupadaTabla(), c.getIdColumna().getNombre1(),
                            c.getIdColumna().getNombre1Ingles(), new ArrayList<ColumnaProformaDTO>()));

                    if (c.getIdColumna().getNombre2() != null && !c.getIdColumna().getNombre2().isEmpty()) {
                        encabezado.get(0).getColumnas().add(new ColumnaProformaDTO(c.getOrden(), c.getIdColumna().getIdColumna(),
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
                }
            }

            Collections.sort(encabezado, new Comparator<ColumnaDatosProformaDTO>() {
                @Override
                public int compare(ColumnaDatosProformaDTO t, ColumnaDatosProformaDTO t1) {
                    return t.getPosicion() - t1.getPosicion();
                }
            });

            //Inicia bloque de contenedores, aplicara si y solo si la PI tiene mas de un contenedor
            List<ContenedorProforma> containers = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

            if (containers != null && containers.size() > 1) {
                for (int i = 0; i < containers.size(); i++) {
                    ContenedorProforma c = containers.get(i);

                    encabezado.add(new ColumnaDatosProformaDTO(encabezado.size() + 1, "100px",
                            "Contenedor (" + (i + 1) + ") - " + c.getIdContenedorProveedor().getIdContenedor().getNombreCorto(), "",
                            new ArrayList<ColumnaProformaDTO>()));

                    encabezado.get(encabezado.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Cantidad", null, "100px", false, false,
                            false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                    encabezado.get(encabezado.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "CBM", null, "100px", false, false,
                            false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                    encabezado.get(encabezado.size() - 1).getColumnas().add(new ColumnaProformaDTO(i, null, null, 2, null, null, "Total", null, "100px", false, false,
                            false, false, false, false, false, false, false, false, false, false, false, false, null, null));
                }
            }
        }
    }

    private void obtenerDatosProforma() {
        System.out.println("Inicia obtencion de datos de la tabla de datos proforma");
        obtenerConfiguracionAMostrar();
        obtenerLineasIgnoradas();

        Integer lineas = detalleProformaFacade.maxRegistros(proforma.getIdProforma());

        if (lineas > 0) {
            for (int i = 0; i <= lineas; i++) {
                DatosProformaDTO d = new DatosProformaDTO(i, validarLineaIncluida(i), new ArrayList<DetalleProformaDTO>());
                List<DetalleProforma> det = detalleProformaFacade.consultarXIdProformaLinea(proforma.getIdProforma(), i);
                if (det != null && !det.isEmpty()) {
                    for (DetalleProforma dp : det) {
                        d.getDetalleProforma().add(new DetalleProformaDTO(dp.getIdDetalleProforma(), dp.getIdConfiguracion().getIdConfiguracion(),
                                dp.getLineNum(), dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(), dp.getValor(),
                                dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() != null ? dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() : "100px",
                                dp.getIdConfiguracion().getIdColumna().getIdTipoDato() != null ? dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato() : null,
                                dp.getIdConfiguracion().getIdColumna().getTipoImagen(), false,
                                new ColumnaProformaDTO(0, dp.getIdConfiguracion().getIdColumna().getIdColumna(),
                                        dp.getIdConfiguracion().getIdColumna().getDecimalesVisibles(),
                                        dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna() != null
                                                ? dp.getIdConfiguracion().getIdColumna().getIdOperacionColumna().getIdOperacionColumnaProforma() : null,
                                        dp.getIdConfiguracion().getIdColumna().getNombre1(), dp.getIdConfiguracion().getIdColumna().getNombre1Ingles(),
                                        dp.getIdConfiguracion().getIdColumna().getNombre2(), dp.getIdConfiguracion().getIdColumna().getNombre2Ingles(),
                                        dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() != null ? dp.getIdConfiguracion().getIdColumna().getLongitudOcupadaTabla() : "100px",
                                        dp.getIdConfiguracion().getIdColumna().getTipoCantidad(), dp.getIdConfiguracion().getIdColumna().getPermitirCrearItem(),
                                        dp.getIdConfiguracion().getIdColumna().getRequiereOperacion(), dp.getIdConfiguracion().getIdColumna().getTipoItem(),
                                        dp.getIdConfiguracion().getIdColumna().getTipoIdentificador(), dp.getIdConfiguracion().getIdColumna().getTipoImagen(),
                                        dp.getIdConfiguracion().getIdColumna().getObligatoria(), dp.getIdConfiguracion().getIdColumna().getIncluirProforma(),
                                        dp.getIdConfiguracion().getIdColumna().getModificable(), dp.getIdConfiguracion().getIdColumna().getModificableSiNuevo(),
                                        dp.getIdConfiguracion().getIdColumna().getTipoValorTotal(), dp.getIdConfiguracion().getIdColumna().getTipoCBM(),
                                        dp.getIdConfiguracion().getIdColumna().getTipoValorUnitario(), dp.getIdConfiguracion().getIdColumna().getTipoDescuento(),
                                        dp.getIdConfiguracion().getIdColumna().getIdTipoDato() != null
                                                ? new TipoDatosDTO(dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getIdTipoDato(), dp.getIdConfiguracion().getIdColumna().getIdTipoDato().getTipoDato()) : null,
                                        null)));

                        if (dp.getIdConfiguracion().getIdColumna().getTipoItem()) {
                            d.setImagenObtenida(galeria(dp.getValor()));
                        }
                    }

                    List<ContenedorProforma> containers = contenedorProformaFacade.contenedoresProforma(proforma.getIdProforma());

                    if (containers != null && containers.size() > 1) {
                        for (ContenedorProforma c : containers) {
                            Object[] o = contenedorLineaFacade.obtenerDatosLineaContenedor(proforma.getIdProforma(), i, c.getIdContenedorProforma());
                            d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 0, (String) o[0], "100px", "Integer", false, false, null));
                            d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 2, (String) o[1], "100px", "Double", false, false, null));
                            d.getDetalleProforma().add(new DetalleProformaDTO(null, null, i, 2, (String) o[2], "100px", "Double", false, false, null));
                        }
                    }

                }
                datosProforma.add(d);
            }
        }
        datosProformaFull = new ArrayList<>(datosProforma);
        log.log(Level.INFO, "Finaliza obtencion de datos de la tabla de datos proforma");
    }

    public void filtrarDatos() {
        datosProforma.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            for (DatosProformaDTO dato : datosProformaFull) {
                for (DetalleProformaDTO detalle : dato.getDetalleProforma()) {
                    if (detalle.getValor() != null && detalle.getValor().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                        datosProforma.add(dato);
                        break;
                    }
                }
            }
        } else {
            datosProforma = new ArrayList<>(datosProformaFull);
        }
    }

    private void obtenerLineasIgnoradas() {
        lineasIgnoradas = lineaIgnoradaProformaFacade.obtenerLineasIgnoradasProforma(proforma.getIdProforma());
    }

    private boolean validarLineaIncluida(Integer linea) {
        for (LineaIgnoradaProforma line : lineasIgnoradas) {
            if (line.getLinea().equals(linea)) {
                return false;
            }
        }
        return true;
    }

    private String galeria(final String referenciaProv) {
        if (referenciaProv != null && !referenciaProv.isEmpty()) {
            File file = new File(aplicacionBean.obtenerValorPropiedad("url.local.imagesProductosProforma") + proforma.getCodProveedor() + "-" + proforma.getAnio() + "-" + proforma.getConsecutivo());
            if (file.exists()) {
                File[] arrArchivos = file.listFiles(new FileFilter() {
                    @Override
                    public boolean accept(File pathname) {
                        if (!pathname.isHidden() && pathname.getName().contains(referenciaProv)) {
                            return true;
                        } else {
                            return false;
                        }
                    }
                });
                if (arrArchivos.length > 0) {
                    File s = arrArchivos[0];
                    try {
                        return verFoto(s.getName());
                    } catch (Exception e) {
                        log.log(Level.SEVERE, e.getMessage());
                    }
                } else {
                    return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
                }
            }
        }
        return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
    }

    private String verFoto(String img) {
        if (img != null && !img.isEmpty()) {
            try {
                return aplicacionBean.obtenerValorPropiedad("url.web.proforma") + proforma.getCodProveedor() + "-"
                        + proforma.getAnio() + "-" + proforma.getConsecutivo() + "/" + img;
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
            }
        }
        return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
    }

    public void seleccionarLinea() {
        Integer linea = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("linea"));
        if (linea != null && linea > -1) {
            proformaSession.setLinea(linea + 1);
            proformaSession.setIdProforma(proforma.getIdProforma());
        } else {
            proformaSession.setLinea(1);
        }
    }
}
