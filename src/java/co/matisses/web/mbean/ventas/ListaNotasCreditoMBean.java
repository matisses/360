package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.facade.DetalleDevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.DevolucionSAPDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.File;
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

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "listaNotasCreditoMBean")
public class ListaNotasCreditoMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(ListaNotasCreditoMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int devolucionesPagina;
    private Integer asesor;
    private String estado;
    private String parametroBusqueda;
    private String fechaInicialMovil;
    private String fechaFinalMovil;
    private boolean mostrarFiltros = false;
    private Date fechaInicial;
    private Date fechaFinal;
    private DevolucionSAPDTO devolucion;
    private List<Integer> paginas;
    private List<String[]> asesores;
    private List<DevolucionSAPDTO> devoluciones;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private DetalleDevolucionSAPFacade detalleDevolucionSAPFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;

    public ListaNotasCreditoMBean() {
        pagina = 1;
        totalPaginas = 0;
        devolucionesPagina = 12;
        devolucion = new DevolucionSAPDTO();
        paginas = new ArrayList<>();
        asesores = new ArrayList<>();
        devoluciones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerDevoluciones();
    }

    public UserSessionInfoMBean getUserSessionInfoMBean() {
        return userSessionInfoMBean;
    }

    public void setUserSessionInfoMBean(UserSessionInfoMBean userSessionInfoMBean) {
        this.userSessionInfoMBean = userSessionInfoMBean;
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

    public int getDevolucionesPagina() {
        return devolucionesPagina;
    }

    public String getSizePage() {
        if (devolucionesPagina != 100000) {
            return Integer.toString(devolucionesPagina);
        } else {
            return "Todos";
        }
    }

    public Integer getAsesor() {
        return asesor;
    }

    public String getAsesorSeleccionado() {
        if (asesor != null && asesor != 0) {
            return baruGenericMBean.obtenerNombreAsesor(asesor);
        }
        return "Seleccione asesor";
    }

    public String getEstado() {
        return estado;
    }

    public String getEstadoSeleccionado() {
        if (estado != null && !estado.isEmpty()) {
            switch (estado) {
                case "A":
                    return "Anulación";
                case "D":
                    return "Devolución";
                case "N":
                    return "No reportable";
                default:
                    break;
            }
        }
        return "Seleccione";
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getFechaInicialMovil() {
        return fechaInicialMovil;
    }

    public void setFechaInicialMovil(String fechaInicialMovil) {
        this.fechaInicialMovil = fechaInicialMovil;
    }

    public String getFechaFinalMovil() {
        return fechaFinalMovil;
    }

    public void setFechaFinalMovil(String fechaFinalMovil) {
        this.fechaFinalMovil = fechaFinalMovil;
    }

    public boolean isMostrarFiltros() {
        return mostrarFiltros;
    }

    public void setMostrarFiltros(boolean mostrarFiltros) {
        this.mostrarFiltros = mostrarFiltros;
    }

    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public DevolucionSAPDTO getDevolucion() {
        return devolucion;
    }

    public void setDevolucion(DevolucionSAPDTO devolucion) {
        this.devolucion = devolucion;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<String[]> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<String[]> asesores) {
        this.asesores = asesores;
    }

    public List<DevolucionSAPDTO> getDevoluciones() {
        return devoluciones;
    }

    public void setDevoluciones(List<DevolucionSAPDTO> devoluciones) {
        this.devoluciones = devoluciones;
    }

    private void obtenerDevoluciones() {
        devoluciones = new ArrayList<>();

        if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.NOTAS_CREDITO_TODAS, Acciones.VISUALIZAR)) {
            asesor = Integer.parseInt(userSessionInfoMBean.getCodigoVentas());
        }
        long nroRegistros = devolucionSAPFacade.obtenerTotalDatos(asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / devolucionesPagina) + (nroRegistros % devolucionesPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        } else if (pagina == 0) {
            pagina = 1;
        }
        construirListaPaginas();

        try {
            if (pagina == 0) {
                pagina = 1;
            }

            List<DevolucionSAP> returns = devolucionSAPFacade.obtenerDevoluciones(pagina, devolucionesPagina, asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

            if (returns != null && !returns.isEmpty()) {
                for (DevolucionSAP d : returns) {
                    devoluciones.add(new DevolucionSAPDTO(d.getDocNum(), d.getDocTotal().intValue(), d.getSlpCode(), d.getuTipoNota().toString(), d.getCardCode(), d.getDocStatus(), d.getDocDate()));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se encontraron datos.");
        }

        obtenerAsesores();
    }

    private void obtenerAsesores() {
        asesores = new ArrayList<>();

        List<Integer> codsAsesores = devolucionSAPFacade.obtenerAsesoresDevolucion(estado, parametroBusqueda, fechaInicial, fechaFinal);

        if (codsAsesores != null && !codsAsesores.isEmpty()) {
            for (Integer c : codsAsesores) {
                String nombreAsesor = baruGenericMBean.obtenerNombreAsesor(c);

                asesores.add(new String[]{nombreAsesor, c.toString()});
            }

            Collections.sort(asesores, new Comparator<String[]>() {
                @Override
                public int compare(String[] t, String[] t1) {
                    return t[0].compareTo(t1[0]);
                }
            });
        }
    }

    public void mostrarFiltrosMovil() {
        mostrarFiltros = !mostrarFiltros;
    }

    public void seleccionarFiltroEstado() {
        estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");
        log.log(Level.INFO, "Se selecciono el estado [{0}], para filtrar las devoluciones", estado);

        pagina = 1;
        obtenerDevoluciones();
    }

    public void seleccionarAsesor() {
        String tmp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("asesor");

        if (tmp != null && !tmp.isEmpty()) {
            asesor = Integer.parseInt(tmp);
        } else {
            asesor = null;
        }
        log.log(Level.INFO, "Se selecciono el asesor [{0}], para filtrar las devoluciones", asesor);

        pagina = 1;
        obtenerDevoluciones();
    }

    public void aplicarFiltroFechas() {
        if (fechaInicial != null) {
            if (fechaInicial.after(new Date())) {
                mostrarMensaje("Error", "La fecha inicial no puede ser superior a la fecha actual.", true, false, false);
                log.log(Level.SEVERE, "La fecha inicial no puede ser superior a la fecha actual.");
                return;
            }
        }
        if (fechaFinal != null) {
            if (fechaFinal.after(new Date())) {
                mostrarMensaje("Error", "La fecha no puede ser superior a la fecha actual.", true, false, false);
                log.log(Level.SEVERE, "La fecha no puede ser superior a la fecha actual");
                return;
            }
        }
        if (fechaFinal != null && fechaInicial != null) {
            if (fechaInicial.after(fechaFinal)) {
                mostrarMensaje("Error", "La fecha inicial no puede ser superior a la final.", true, false, false);
                log.log(Level.SEVERE, "La fecha inicial no puede ser superior a la final");
                return;
            }
            if (fechaFinal.before(fechaInicial)) {
                mostrarMensaje("Error", "La fecha final no puede ser anterior a la fecha inicial.", true, false, false);
                log.log(Level.SEVERE, "La fecha final no puede ser anterior a la fecha inicial");
                return;
            }
        }

        if (fechaInicial != null && fechaFinal != null) {
            try {
                String fI = new SimpleDateFormat("dd-MM-yyyy").format(fechaInicial);
                fechaInicial = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(fI + " 00:00:00");

                String fF = new SimpleDateFormat("dd-MM-yyyy").format(fechaFinal);
                fechaFinal = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss").parse(fF + " 23:59:59");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al convertir las fechas. Error [{0}]", e.getMessage());
                mostrarMensaje("Error", "No se pudieron usar las fechas suministradas.", true, false, false);
                return;
            }

            log.log(Level.INFO, "Se aplicara filtro de fechas a las devoluciones. Fecha inicio: [{0}], Fecha fin: [{1}]",
                    new Object[]{new SimpleDateFormat("dd-MM-yyyy").format(fechaInicial), new SimpleDateFormat("dd-MM-yyyy").format(fechaFinal)});
        }

        pagina = 1;
        obtenerDevoluciones();
    }

    public void aplicarFiltroFechasMovil() {
        if (fechaInicialMovil == null || fechaInicialMovil.isEmpty()) {
            mostrarMensaje("Error", "Ingrese la fecha inicial para poder aplicarla.", true, false, false);
            log.log(Level.SEVERE, "Ingrese la fecha inicial para poder aplicarla");
            return;
        }
        if (fechaFinalMovil == null || fechaFinalMovil.isEmpty()) {
            mostrarMensaje("Error", "Ingrese la fecha final para poder aplicarla.", true, false, false);
            log.log(Level.SEVERE, "Ingrese la fecha final para poder aplicarla");
            return;
        }

        try {
            fechaInicial = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaInicialMovil + " 00:00:00");
            fechaFinal = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(fechaFinalMovil + " 23:59:59");

            if (fechaInicial != null) {
                if (fechaInicial.after(new Date())) {
                    mostrarMensaje("Error", "La fecha inicial no puede ser superior a la fecha actual.", true, false, false);
                    log.log(Level.SEVERE, "La fecha inicial no puede ser superior a la fecha actual.");
                    return;
                }
            }
            if (fechaFinal != null) {
                if (fechaFinal.after(new Date())) {
                    mostrarMensaje("Error", "La fecha no puede ser superior a la fecha actual.", true, false, false);
                    log.log(Level.SEVERE, "La fecha no puede ser superior a la fecha actual");
                    return;
                }
            }
            if (fechaFinal != null && fechaInicial != null) {
                if (fechaInicial.after(fechaFinal)) {
                    mostrarMensaje("Error", "La fecha inicial no puede ser superior a la final.", true, false, false);
                    log.log(Level.SEVERE, "La fecha inicial no puede ser superior a la final");
                    return;
                }
                if (fechaFinal.before(fechaInicial)) {
                    mostrarMensaje("Error", "La fecha final no puede ser anterior a la fecha inicial.", true, false, false);
                    log.log(Level.SEVERE, "La fecha final no puede ser anterior a la fecha inicial");
                    return;
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al convertir las fechas. Error [{0}]", e.getMessage());
            mostrarMensaje("Error", "No se pudieron usar las fechas suministradas.", true, false, false);
            return;
        }
        obtenerDevoluciones();
    }

    public void aplicarParametroBusqueda() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusqueda);

        pagina = 1;
        obtenerDevoluciones();
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void cambiarSizePage() {
        devolucionesPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de facturas x pagina a [{0}]", devolucionesPagina);
        pagina = 1;
        obtenerDevoluciones();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerDevoluciones();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerDevoluciones();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerDevoluciones();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerDevoluciones();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerDevoluciones();
    }

    public void seleccionarDevolucion() {
        Integer docNum = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("docNum"));

        if (devolucion != null && devolucion.getNumeroDevolucion() != null && devolucion.getNumeroDevolucion().equals(docNum)) {
            devolucion = new DevolucionSAPDTO();
        } else {
            if (docNum == null || docNum == 0) {
                mostrarMensaje("Error", "Debe seleccionar una devolución.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar una devolucion");
                return;
            }

            /*Se obtienen los datos de la devolucion*/
            DevolucionSAP d = devolucionSAPFacade.obtenerDevolucion(docNum);

            if (d != null && d.getDocEntry() != null && d.getDocEntry() != 0) {
                devolucion = new DevolucionSAPDTO(d.getDocNum(), null, null, d.getuTipoNota().toString(), d.getCardCode(), d.getDocStatus(), d.getDocDate());
            } else {
                mostrarMensaje("Error", "No se encontraron datos de la devolución seleccionada.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos de la devolucion seleccionada");
                return;
            }
        }
    }

    public String abrirPdf() {
        if (devolucion != null && devolucion.getNumeroDevolucion() != null && devolucion.getNumeroDevolucion() != 0) {
            List<String[]> adjunto = generarDocumento(devolucion.getNumeroDevolucion(), 1, "devolucion", userSessionInfoMBean.getAlmacen(), null, false, null);

            if (adjunto != null && !adjunto.isEmpty()) {
                if (new File(adjunto.get(0)[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.devolucion") + devolucion.getNumeroDevolucion();
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "No se pudo generar la URL para el documento", e);
                        return "";
                    }
                } else {
                    log.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return "";
            }
        } else {
            log.log(Level.SEVERE, "No encontro datos para generar documento");
            return "";
        }
    }

    private List<String[]> generarDocumento(Integer id, Integer copias, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            List<String[]> adjuntos = new ArrayList<>();

            for (String s : res.getMensaje().split("||")) {
                File f = new File(s);

                if (f.exists()) {
                    adjuntos.add(new String[]{s, f.getName()});
                }
            }

            return adjuntos;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
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
