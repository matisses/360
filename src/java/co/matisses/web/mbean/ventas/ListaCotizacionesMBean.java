package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.CotizacionSAP;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.CotizacionSAPFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.CotizacionSAPDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.session.VentasSessionMBean;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "listaCotizacionesMBean")
public class ListaCotizacionesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    private static final Logger log = Logger.getLogger(ListaCotizacionesMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int cotizacionesPagina;
    private Integer asesor;
    private String estado;
    private String parametroBusqueda;
    private String parametroBusquedaMovil;
    private String fechaInicialMovil;
    private String fechaFinalMovil;
    private String correoCliente;
    private boolean mostrarFiltros = false;
    private Date fechaInicial;
    private Date fechaFinal;
    private CotizacionSAPDTO cotizacion;
    private List<Integer> paginas;
    private List<String[]> asesores;
    private List<CotizacionSAPDTO> cotizaciones;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private CotizacionSAPFacade cotizacionSAPFacade;

    public ListaCotizacionesMBean() {
        pagina = 1;
        totalPaginas = 0;
        cotizacionesPagina = 12;
        cotizacion = new CotizacionSAPDTO();
        paginas = new ArrayList<>();
        asesores = new ArrayList<>();
        cotizaciones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerCotizaciones();
    }

    public UserSessionInfoMBean getUserSessionInfoMBean() {
        return userSessionInfoMBean;
    }

    public void setUserSessionInfoMBean(UserSessionInfoMBean userSessionInfoMBean) {
        this.userSessionInfoMBean = userSessionInfoMBean;
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

    public VentasSessionMBean getVentasSessionMBean() {
        return ventasSessionMBean;
    }

    public void setVentasSessionMBean(VentasSessionMBean ventasSessionMBean) {
        this.ventasSessionMBean = ventasSessionMBean;
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

    public int getCotizacionesPagina() {
        return cotizacionesPagina;
    }

    public String getSizePage() {
        if (cotizacionesPagina != 100000) {
            return Integer.toString(cotizacionesPagina);
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
                case "NF":
                    return "No finalizada";
                case "CP, P, G":
                    return "Pendiente";
                case "CC":
                    return "Cancelada";
                case "CF, F":
                    return "Facturada";
                case "CT, A":
                    return "Terminada";
                case "CD":
                    return "Demostrada";
                case "C":
                    return "Cerrada";
                case "E":
                    return "Error";
                case "M":
                    return "Modificada";
                case "N":
                    return "Nota credito";
                case "R":
                    return "Renviar email";
                case "T":
                    return "Pago efectuado";
                case "V":
                    return "Vencida";
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

    public String getParametroBusquedaMovil() {
        return parametroBusquedaMovil;
    }

    public void setParametroBusquedaMovil(String parametroBusquedaMovil) {
        this.parametroBusquedaMovil = parametroBusquedaMovil;
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

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
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

    public CotizacionSAPDTO getCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(CotizacionSAPDTO cotizacion) {
        this.cotizacion = cotizacion;
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

    public List<CotizacionSAPDTO> getCotizaciones() {
        return cotizaciones;
    }

    public void setCotizaciones(List<CotizacionSAPDTO> cotizaciones) {
        this.cotizaciones = cotizaciones;
    }

    private void obtenerCotizaciones() {
        cotizaciones = new ArrayList<>();

        if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.COTIZACIONES_TODAS, Acciones.VISUALIZAR)) {
            asesor = Integer.parseInt(userSessionInfoMBean.getCodigoVentas());
        }

        long nroRegistros = cotizacionSAPFacade.obtenerTotalDatos(asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);
        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / cotizacionesPagina) + (nroRegistros % cotizacionesPagina > 0 ? 1 : 0);
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

            List<CotizacionSAP> cots = cotizacionSAPFacade.obtenerCotizaciones(pagina, cotizacionesPagina, asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

            if (cots != null && !cots.isEmpty()) {
                for (CotizacionSAP c : cots) {
                    cotizaciones.add(new CotizacionSAPDTO(c.getDocNum(), c.getSlpCode(), c.getCardCode(), c.getCardName(), c.getDocStatus(), c.getDocDate()));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se encontraron datos.");
        }

        obtenerAsesores();
    }

    private void obtenerAsesores() {
        asesores = new ArrayList<>();

        List<Integer> codsAsesores = cotizacionSAPFacade.obtenerAsesoresFacturas(estado, parametroBusqueda, fechaInicial, fechaFinal);

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
        log.log(Level.INFO, "Se selecciono el estado [{0}], para filtrar las cotizaciones", estado);

        pagina = 1;
        obtenerCotizaciones();
    }

    public void seleccionarAsesor() {
        String tmp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("asesor");

        if (tmp != null && !tmp.isEmpty()) {
            asesor = Integer.parseInt(tmp);
        } else {
            asesor = null;
        }
        log.log(Level.INFO, "Se selecciono el asesor [{0}], para filtrar las cotizaciones", asesor);

        pagina = 1;
        obtenerCotizaciones();
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

            log.log(Level.INFO, "Se aplicara filtro de fechas a las cotizaciones. Fecha inicio: [{0}], Fecha fin: [{1}]",
                    new Object[]{new SimpleDateFormat("dd-MM-yyyy").format(fechaInicial), new SimpleDateFormat("dd-MM-yyyy").format(fechaFinal)});
        }

        pagina = 1;
        obtenerCotizaciones();
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

        pagina = 1;
        obtenerCotizaciones();
    }

    public void aplicarParametroBusqueda() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusqueda);

        pagina = 1;
        obtenerCotizaciones();
    }

    public void aplicarParametroBusquedaMovil() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusquedaMovil);

        parametroBusqueda = parametroBusquedaMovil;
        pagina = 1;
        obtenerCotizaciones();
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void cambiarSizePage() {
        cotizacionesPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de cotizaicones x pagina a [{0}]", cotizacionesPagina);
        pagina = 1;
        obtenerCotizaciones();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerCotizaciones();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerCotizaciones();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerCotizaciones();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerCotizaciones();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerCotizaciones();
    }

    public void seleccionarCotizacion() {
        Integer docNum = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("docNum"));

        if (cotizacion != null && cotizacion.getDocNum() == docNum) {
            cotizacion = null;
        } else {
            if (docNum == null || docNum == 0) {
                mostrarMensaje("Error", "Debe seleccionar una cotización para poder re-enviar el correo.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar una cotizacion para poder re-enviar el correo");
                return;
            }

            /*Se obtienen los datos de la cotizacion*/
            for (CotizacionSAPDTO c : cotizaciones) {
                if (c.getDocNum() == docNum) {
                    cotizacion = c;
                    break;
                }
            }

            if (cotizacion == null || cotizacion.getDocNum() == 0) {
                mostrarMensaje("Error", "No se encontraron datos de la cotización seleccionada.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos de la cotizacion seleccionada");
                return;
            }
        }
    }

    public String editarCotizacion() {
        if (cotizacion.getDocNum() != 0) {
            if (cotizacion.getDocStatus().toString().equals("C")) {
                mostrarMensaje("Error", "La cotización seleccionada no se puede modificar, debido a que esta ya se encuentra cerrada.", true, false, false);
                log.log(Level.SEVERE, "La cotizacion seleccionada no se puede modificar, debido a que esta ya se encuentra cerrada");
                return null;
            }

            log.log(Level.INFO, "Se modificara la cotizacion numero [{0}]", cotizacion.getDocNum());
            ventasSessionMBean.setNumeroCotizacion(Long.parseLong(String.valueOf(cotizacion.getDocNum())));
            ventasSessionMBean.setModificando(true);
            ventasSessionMBean.setDemostracion(false);
            ventasSessionMBean.setIdDemostracion(null);
            ventasSessionMBean.setExitoDemostracion(false);
            return "venta";
        } else {
            mostrarMensaje("Error", "Se detectó un error con la cotización lo que impide su modificación.", true, false, false);
            log.log(Level.SEVERE, "Se detecto un error con la cotizacion lo que impide su modificacion");
            return null;
        }
    }

    public void seleccionarEnvioCorreoCliente() {
        if (cotizacion.getCardCode() != null && !cotizacion.getCardCode().isEmpty()) {
            /*Se extren los datos del cliente de la cotizacion y se obtiene el correo actual del cliente*/
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cotizacion.getCardCode());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                correoCliente = socio.getEmail();

                log.log(Level.INFO, "Se encontro el siguiente correo principal del cliente: [{0}]", correoCliente);
            } else {
                mostrarMensaje("Error", "No se encontraron datos del cliente.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos del cliente");
                return;
            }
        } else {
            mostrarMensaje("Error", "La cotización seleccionada, no permite esta función debido a que no tiene datos del cliente registrados.", true, false, false);
            log.log(Level.SEVERE, "La cotizacion seleccionada, no permite esta funcion debido a que no tiene datos del cliente registrados");
            return;
        }
    }

    public void enviarCorreoCliente() {
        if (correoCliente == null || correoCliente.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un correo electrónico.", true, false, false);
            log.log(Level.SEVERE, "Ingrese un correo electronico");
            return;
        }
        String[] adjunto = generarDocumento(cotizacion.getDocNum(), 1, String.valueOf(cotizacion.getDocNum()), "cotizacion", userSessionInfoMBean.getAlmacen(), null, false, null);

        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            if (adjunto != null) {
                adjuntos.add(adjunto);
            }
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cotizacion.getCardCode());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                /*Se escribe el nombre del cliente*/
                String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                        .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                params.put("documento", "cotizaci&oacute;n");
                params.put("cliente", nombreCliente);
                params.put("tipo", "pidio que se le reenviara");

                res = client.enviarHtmlEmail("Cotización matisses", "Cotización Matisses " + cotizacion.getDocNum(), correoCliente, "venta", adjuntos, params);
            }

            log.log(Level.INFO, res.getMensaje());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion. ", e);
        }
    }

    private String[] generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
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

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), nombreArchivo + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    public String abrirPdf() {
        if (cotizacion != null && cotizacion.getDocNum() != 0) {
            String[] adjunto = generarDocumento(cotizacion.getDocNum(), 1, String.valueOf(cotizacion.getDocNum()), "cotizacion", userSessionInfoMBean.getAlmacen(), null, false, null);

            if (adjunto != null) {
                if (new File(adjunto[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.cotizaciones") + cotizacion.getDocNum();
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "No se pudo generar la URL para el documento");
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
            log.log(Level.SEVERE, "No se pudo generar el documento");
            return "";
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
