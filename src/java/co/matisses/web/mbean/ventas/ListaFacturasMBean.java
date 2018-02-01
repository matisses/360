package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.BaruDocumentoPendiente;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.ReciboCaja;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.BaruDocumentoPendienteFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.ProcesarDocumentoClient;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.FacturaSAPDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.session.VentasSessionMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.text.ParseException;
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
@Named(value = "listaFacturasMBean")
public class ListaFacturasMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    private static final Logger log = Logger.getLogger(ListaFacturasMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int facturasPagina;
    private Integer asesor;
    private String estado;
    private String parametroBusqueda;
    private String fechaInicialMovil;
    private String fechaFinalMovil;
    private String correoCliente;
    private String tipoNotaCredito;
    private boolean mostrarFiltros = false;
    private boolean dlgNotasCredito = false;
    private Date fechaInicial;
    private Date fechaFinal;
    private FacturaSAPDTO factura;
    private List<Integer> paginas;
    private List<String[]> asesores;
    private List<FacturaSAPDTO> facturas;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;
    @EJB
    private BaruDocumentoPendienteFacade baruDocumentoPendienteFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;

    public ListaFacturasMBean() {
        pagina = 1;
        totalPaginas = 0;
        facturasPagina = 12;
        factura = new FacturaSAPDTO();
        paginas = new ArrayList<>();
        asesores = new ArrayList<>();
        facturas = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerFacturas();
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

    public int getFacturasPagina() {
        return facturasPagina;
    }

    public String getSizePage() {
        if (facturasPagina != 100000) {
            return Integer.toString(facturasPagina);
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
                case "C":
                    return "Cerrada";
                case "O":
                    return "Abierta";
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

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getTipoNotaCredito() {
        return tipoNotaCredito;
    }

    public void setTipoNotaCredito(String tipoNotaCredito) {
        this.tipoNotaCredito = tipoNotaCredito;
    }

    public boolean isMostrarFiltros() {
        return mostrarFiltros;
    }

    public void setMostrarFiltros(boolean mostrarFiltros) {
        this.mostrarFiltros = mostrarFiltros;
    }

    public boolean isDlgNotasCredito() {
        return dlgNotasCredito;
    }

    public void setDlgNotasCredito(boolean dlgNotasCredito) {
        this.dlgNotasCredito = dlgNotasCredito;
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

    public FacturaSAPDTO getFactura() {
        return factura;
    }

    public void setFactura(FacturaSAPDTO factura) {
        this.factura = factura;
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

    public List<FacturaSAPDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<FacturaSAPDTO> facturas) {
        this.facturas = facturas;
    }

    private void obtenerFacturas() {
        facturas = new ArrayList<>();

        if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.FACTURAS_TODAS, Acciones.VISUALIZAR)) {
            asesor = Integer.parseInt(userSessionInfoMBean.getCodigoVentas());
        }

        long nroRegistros = facturaSAPFacade.obtenerTotalDatos(asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / facturasPagina) + (nroRegistros % facturasPagina > 0 ? 1 : 0);
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

            List<FacturaSAP> invoices = facturaSAPFacade.obtenerFacturas(pagina, facturasPagina, asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

            if (invoices != null && !invoices.isEmpty()) {
                for (FacturaSAP f : invoices) {
                    facturas.add(new FacturaSAPDTO(f.getDocNum(), f.getDocTotal().intValue(), f.getCardCode(), f.getDocStatus().toString(), f.getComments(), f.getSlpCode().toString(), f.getDocDate()));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se encontraron datos.");
        }

        obtenerAsesores();
    }

    private void obtenerAsesores() {
        asesores = new ArrayList<>();

        List<Integer> codsAsesores = facturaSAPFacade.obtenerAsesoresFacturas(estado, parametroBusqueda, fechaInicial, fechaFinal);

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
        log.log(Level.INFO, "Se selecciono el estado [{0}], para filtrar las facturas", estado);

        pagina = 1;
        obtenerFacturas();
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
        obtenerFacturas();
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
        obtenerFacturas();
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
        obtenerFacturas();
    }

    public void aplicarParametroBusqueda() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusqueda);

        pagina = 1;
        obtenerFacturas();
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void cambiarSizePage() {
        facturasPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de facturas x pagina a [{0}]", facturasPagina);
        pagina = 1;
        obtenerFacturas();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerFacturas();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerFacturas();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerFacturas();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerFacturas();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerFacturas();
    }

    public void seleccionarFactura() {
        Integer docNum = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("docNum"));

        if (factura != null && factura.getNumeroFactura() != null && factura.getNumeroFactura().equals(docNum)) {
            factura = new FacturaSAPDTO();
        } else {
            if (docNum == null || docNum == 0) {
                mostrarMensaje("Error", "Debe seleccionar una factura para poder re-enviar el correo.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar una factura para poder re-enviar el correo");
                return;
            }

            /*Se obtienen los datos de la factura*/
            FacturaSAP f = facturaSAPFacade.findByDocNum(docNum);

            if (f != null && f.getDocEntry() != null && f.getDocEntry() != 0) {
                factura = new FacturaSAPDTO(f.getDocNum(), null, f.getCardCode(), f.getDocStatus().toString(), null, null, f.getDocDate());
            } else {
                mostrarMensaje("Error", "No se encontraron datos de la factura seleccionada.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos de la factura seleccionada");
                return;
            }
        }
    }

    public void seleccionarEnvioCorreoCliente() {
        if (factura.getNitCliente() != null && !factura.getNitCliente().isEmpty()) {
            /*Se extren los datos del cliente de la cotizacion y se obtiene el correo actual del cliente*/
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(factura.getNitCliente());

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

        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();

        if (!new File(applicationMBean.obtenerValorPropiedad("url.folder.facturas") + factura.getNumeroFactura()).exists()) {
            List<String[]> relacionados = new ArrayList<>();
            List<DetalleFacturaSAP> detalle = detalleFacturaSAPFacade.obtenerDetalleFactura(facturaSAPFacade.findByDocNum(factura.getNumeroFactura()).getDocEntry().doubleValue());
            if (detalle != null && !detalle.isEmpty()) {
                for (DetalleFacturaSAP d : detalle) {
                    if (d.getUEstadoP().equals("G")) {
                        relacionados.add(new String[]{"daka\\contratoDaka", "2"});
                        break;
                    }
                }
            }

            ReciboCaja recibo = reciboCajaFacade.obtenerReciboCaja(factura.getNumeroFactura().toString());

            if (recibo != null && recibo.getDocEntry() != null && recibo.getDocEntry() != 0) {
                relacionados.add(new String[]{"reciboCaja\\reciboCaja", "2"});
            }

            generarDocumento(factura.getNumeroFactura(), 2, factura.getNumeroFactura().toString(), "factura", userSessionInfoMBean.getAlmacen(), null, false, relacionados);
        }

        File file = new File(applicationMBean.obtenerValorPropiedad("url.folder.facturas") + factura.getNumeroFactura());

        File[] archivos = file.listFiles(new FileFilter() {
            @Override
            public boolean accept(File path) {
                return (!path.isHidden());
            }
        });
        for (File s : archivos) {
            if (s.getPath().contains(" - Copia.pdf")) {
                adjuntos.add(new String[]{s.getPath(), s.getName()});
            }
        }

        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(factura.getNitCliente());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                /*Se escribe el nombre del cliente*/
                String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                        .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                params.put("documento", "factura");
                params.put("tipo", "solicito se le reenviara");
                params.put("cliente", nombreCliente);

                res = client.enviarHtmlEmail("Facturas Matisses", "Facturas Matisses", correoCliente, "venta", adjuntos, params);
            }

            log.log(Level.INFO, res.getMensaje());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion. ", e);
        }
    }

    private List<String[]> generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
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

    public String abrirPdf() {
        if (factura != null && factura.getNumeroFactura() != null && factura.getNumeroFactura() != 0) {
            List<String[]> adjunto = generarDocumento(factura.getNumeroFactura(), 2, factura.getNumeroFactura().toString(), "factura", userSessionInfoMBean.getAlmacen(), null, false, null);

            if (adjunto != null && !adjunto.isEmpty()) {
                if (new File(adjunto.get(0)[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.facturas") + factura.getNumeroFactura() + File.separator + factura.getNumeroFactura();
                        return "openRuta('" + url + " - Copia.pdf');";
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

    public void validarTiposNotaCredito() throws ParseException {
        dlgNotasCredito = false;
        if (factura != null && factura.getNumeroFactura() != null && factura.getNumeroFactura() != 0) {
            Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(baruGenericMBean.getValorFormateado("Date", new Date(), 0));

            if (fecha.equals(factura.getFechaFactura())) {
                tipoNotaCredito = "A";
            } else {
                tipoNotaCredito = "D";
            }

            //Validar si se puede hacer la anulacion
            List<DevolucionSAP> devoluciones = devolucionSAPFacade.obtenerDevolucionesFactura(factura.getNumeroFactura());

            if (devoluciones != null && !devoluciones.isEmpty()) {
                boolean anulada = false;

                for (DevolucionSAP d : devoluciones) {
                    if (d.getuTipoNota().toString().equals("A")) {
                        anulada = true;
                        break;
                    }
                }

                if (anulada) {
                    mostrarMensaje("Error", "La factura ya fue ANULADA.", true, false, false);
                    log.log(Level.INFO, "La factura {0} ya fue ANULADA", factura.getNumeroFactura());
                    return;
                } else {
                    dlgNotasCredito = true;
                }
            } else {
                dlgNotasCredito = true;
            }
        }
    }

    public String crearNotaCredito() {
        if (tipoNotaCredito.equals("A")) {
            ProcesarDocumentoClient client = new ProcesarDocumentoClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            if (factura != null && factura.getNumeroFactura() != null && factura.getNumeroFactura() != 0) {
                String code = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
                BaruDocumentoPendiente doc = new BaruDocumentoPendiente();

                doc.setCode(code);
                doc.setName(code);
                doc.setuEstado("P");
                doc.setuIntentos(0);
                doc.setuNumeroDocumento(factura.getNumeroFactura());
                doc.setuTipoDocumento("NC");
                doc.setuProcesando("Y");

                try {
                    baruDocumentoPendienteFacade.create(doc);
                    log.log(Level.INFO, "El usuario {0} registro en documentos pendientes la FV {1} para ANULAR", new Object[]{userSessionInfoMBean.getUsuario(), factura.getNumeroFactura()});

                    GenericRESTResponseDTO res = client.ejecutarSonda("Y");

                    if (res.getEstado() == 0) {
                        mostrarMensaje("Error", "No se pudo realizar la nota crédito por anulación.", true, false, false);
                        log.log(Level.SEVERE, "No se pudo realizar la nota credito por anulacion");
                        return "";
                    } else {
                        mostrarMensaje("Éxito", "Anulación creada correctamente.", false, true, false);
                        log.log(Level.INFO, "Anulacion creada correctamente");
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al registrar el documento como pendiente para ANULAR. ", e);
                    mostrarMensaje("Error", "No se pudo anular la factura, intentelo nuevamente.", true, false, false);
                    return "";
                }
            }
        } else {
            ventasSessionMBean.setCreaNotaCredito(true);
            ventasSessionMBean.setNumeroFactura(factura.getNumeroFactura());
            return "crearNota";
        }

        dlgNotasCredito = false;
        return "";
    }

    public void limpiarNotaCredito() {
        tipoNotaCredito = null;
        dlgNotasCredito = false;
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
