package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.TrasladosSAPFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.Demostracion;
import co.matisses.persistence.web.entity.DetalleDemostracion;
import co.matisses.persistence.web.facade.DemostracionFacade;
import co.matisses.persistence.web.facade.DetalleDemostracionFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.BinLocationsClient;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.bcs.client.StockTransferClient;
import co.matisses.web.dto.BinLocationDTO;
import co.matisses.web.dto.DemostracionDTO;
import co.matisses.web.dto.DetalleDemostracionDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.StockTransferDocumentBinAllocationDTO;
import co.matisses.web.dto.StockTransferDocumentDTO;
import co.matisses.web.dto.StockTransferDocumentLinesDTO;
import co.matisses.web.dto.UbicacionesDemostracionDTO;
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
@Named(value = "listaDemostracionesMBean")
public class ListaDemostracionesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    private static final Logger log = Logger.getLogger(ListaDemostracionesMBean.class.getSimpleName());
    private Integer asesor;
    private String estado;
    private String parametroBusqueda;
    private String parametroBusquedaMovil;
    private String fechaInicialMovil;
    private String fechaFinalMovil;
    private String correoCliente;
    private boolean mostrarFiltros = false;
    private boolean regresarSaldo = false;
    private Date fechaInicial;
    private Date fechaFinal;
    private DemostracionDTO demostracion;
    private List<String[]> asesores;
    private List<DemostracionDTO> demostraciones;
    private List<DetalleDemostracionDTO> detalleDemostracion;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private DetalleDemostracionFacade detalleDemostracionFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private DemostracionFacade demostracionFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;

    public ListaDemostracionesMBean() {
        demostracion = new DemostracionDTO();
        asesores = new ArrayList<>();
        demostraciones = new ArrayList<>();
        detalleDemostracion = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerDemostraciones();
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
                case "a":
                    return "Activas";
                case "v":
                    return "Vencidas";
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

    public DemostracionDTO getDemostracion() {
        return demostracion;
    }

    public void setDemostracion(DemostracionDTO demostracion) {
        this.demostracion = demostracion;
    }

    public List<String[]> getAsesores() {
        return asesores;
    }

    public void setAsesores(List<String[]> asesores) {
        this.asesores = asesores;
    }

    public List<DemostracionDTO> getDemostraciones() {
        return demostraciones;
    }

    public void setDemostraciones(List<DemostracionDTO> demostraciones) {
        this.demostraciones = demostraciones;
    }

    private void obtenerDemostraciones() {
        demostraciones = new ArrayList<>();

        if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.DEMOSTRACIONES_TODAS, Acciones.VISUALIZAR)) {
            asesor = Integer.parseInt(userSessionInfoMBean.getCodigoVentas());
        }
        try {
            List<Object[]> ubicaciones = ubicacionSAPFacade.obtenerDemosActivas(asesor, estado, parametroBusqueda, fechaInicial, fechaFinal);

            if (ubicaciones != null && !ubicaciones.isEmpty()) {
                //Se obtiene objecto con los datos de las demostraciones que a la fecha tiene datos, a continuacion se detalle el contenido del objecto
                //[0] - SL1Code: Codigo asesor
                //[1] - SL2Code: Slot
                //[2] - Attr2Val: Nombre demostracion
                //[3] - Attr3Val: Fecha demostracion
                //[4] - Attr4Val: Fecha vencimiento demostracion
                //[5] - Attr5Val: Documento cliente
                //[6] - CardName: Razon social cliente
                //[7] - Attr7Val: Id demostracion web
                //[8] - Attr8Val: Factura asociada

                for (Object[] u : ubicaciones) {
                    demostraciones.add(new DemostracionDTO((Integer) u[7], (Integer) u[8] != null && (Integer) u[8] > 0 ? (Integer) u[8] : null, (Integer) u[0],
                            (String) u[1], (String) u[2], (String) u[5], (String) u[6], (Date) u[3], (Date) u[4]));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se encontraron datos.");
        }

        obtenerAsesores();
    }

    private void obtenerAsesores() {
        asesores = new ArrayList<>();

        List<Integer> codsAsesores = ubicacionSAPFacade.obtenerAsesoresDemosActivas();

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
        log.log(Level.INFO, "Se selecciono el estado [{0}], para filtrar las demostraciones", estado);

        obtenerDemostraciones();
    }

    public void seleccionarAsesor() {
        String tmp = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("asesor");

        if (tmp != null && !tmp.isEmpty()) {
            asesor = Integer.parseInt(tmp);
        } else {
            asesor = null;
        }
        log.log(Level.INFO, "Se selecciono el asesor [{0}], para filtrar las demostraciones", asesor);

        obtenerDemostraciones();
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

        obtenerDemostraciones();
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

        obtenerDemostraciones();
    }

    public void aplicarParametroBusqueda() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusqueda);

        obtenerDemostraciones();
    }

    public void aplicarParametroBusquedaMovil() {
        log.log(Level.INFO, "Se aplicara el parametro de busqueda [{0}]", parametroBusquedaMovil);

        parametroBusqueda = parametroBusquedaMovil;
        obtenerDemostraciones();
    }

    public void seleccionarDemostracion() {
        Integer idDemostracion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDemostracion"));

        if (demostracion != null && demostracion.getIdDemostracion() == idDemostracion) {
            demostracion = null;
        } else {
            if (idDemostracion == null || idDemostracion == 0) {
                mostrarMensaje("Error", "Debe seleccionar una demostración para poder re-enviar el correo.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar una demostracion para poder re-enviar el correo");
                return;
            }

            /*Se obtienen los datos de la cotizacion*/
            for (DemostracionDTO d : demostraciones) {
                if (d.getIdDemostracion() == idDemostracion) {
                    demostracion = d;
                    break;
                }
            }

            if (demostracion == null || demostracion.getIdDemostracion() == 0) {
                mostrarMensaje("Error", "No se encontraron datos de la demostración seleccionada.", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos de la demostracion seleccionada");
                return;
            }
        }
    }

    public String editarDemostracion() {
        if (demostracion.getIdDemostracion() != 0) {
            log.log(Level.INFO, "Se modificara la demostracion numero {0}", demostracion.getIdDemostracion());

            ventasSessionMBean.setIdDemostracion(demostracion.getIdDemostracion());
            ventasSessionMBean.setModificando(true);
            ventasSessionMBean.setDemostracion(true);
            return "venta";
        } else {
            mostrarMensaje("Error", "Se detectó un error con la demostración lo que impide su modificación.", true, false, false);
            log.log(Level.SEVERE, "Se detecto un error con la demostracion lo que impide su modificacion");
            return null;
        }
    }

    public void seleccionarEnvioCorreoCliente() {
        if (demostracion.getDocumentoCliente() != null && !demostracion.getDocumentoCliente().isEmpty()) {
            /*Se extren los datos del cliente de la cotizacion y se obtiene el correo actual del cliente*/
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(demostracion.getDocumentoCliente());

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
        String[] adjunto = generarDocumento(demostracion.getIdDemostracion(), 1, "[" + demostracion.getIdDemostracion() + "] " + demostracion.getNombreDemostracion().toUpperCase(),
                "demostracion", userSessionInfoMBean.getAlmacen(), demostracion.getNombreDemostracion(), false, null);

        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            if (adjunto != null) {
                adjuntos.add(adjunto);
            }
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(demostracion.getDocumentoCliente());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                /*Se escribe el nombre del cliente*/
                String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                        .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                params.put("documento", "demostraci&oacute;n");
                params.put("tipo", "solicito se le reenviara");
                params.put("cliente", nombreCliente);

                res = client.enviarHtmlEmail("Demostración Matisses", "Demostración [" + demostracion.getIdDemostracion() + "] " + demostracion.getNombreDemostracion().toUpperCase(),
                        correoCliente, "venta", adjuntos, params);
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

    public void regresarSaldo() {
        regresarSaldo = true;
        cancelarDemostracion();
    }

    public void regresarSaldoNo() {
        regresarSaldo = false;
        cancelarDemostracion();
    }

    @SuppressWarnings("MalformedRegexp")
    private void cancelarDemostracion() {
        detalleDemostracion = new ArrayList<>();
        if (demostracion != null && demostracion.getIdDemostracion() != 0) {
            List<SaldoUbicacion> balances = saldoUbicacionFacade.obtenerSaldosDemostracion(demostracion.getCodigoAsesor().toString(), demostracion.getSlot());
            List<DetalleDemostracion> detalle = detalleDemostracionFacade.obtenerDetalleDemostracion(demostracion.getIdDemostracion());

            if (balances != null && !balances.isEmpty()) {
                for (SaldoUbicacion s : balances) {
                    DetalleDemostracionDTO det = new DetalleDemostracionDTO();

                    det.setIdDetalleDemostracion(demostracion.getIdDemostracion());
                    det.setCantidad(s.getOnHandQty().intValue());
                    det.setReferencia(s.getItemCode());
                    det.setAlmacenOrigen(s.getWhsCode().replace("DM", ""));
                    det.setAlmacenDestino(s.getWhsCode());
                    det.setUbicacionDestino(s.getUbicacion().getBinCode());
                    det.setUbicaciones(new ArrayList<UbicacionesDemostracionDTO>());

                    if (regresarSaldo) {
                        if (detalle != null && !detalle.isEmpty()) {
                            for (DetalleDemostracion d : detalle) {
                                if (s.getWhsCode().equals(d.getAlmacenDestino()) && s.getItemCode().equals(d.getReferencia()) && d.getUbicacionOrigen() != null && !d.getUbicacionOrigen().isEmpty()
                                        && s.getOnHandQty().intValue() == d.getCantidad()) {
                                    for (String u : d.getUbicacionOrigen().split("-")) {
                                        SaldoUbicacion saldo = saldoUbicacionFacade.obtenerSaldoXUbicacion(d.getReferencia(), d.getUbicacionDestino());

                                        boolean regresar = saldo.getOnHandQty().intValue() == d.getCantidad() ? true : false;

                                        UbicacionesDemostracionDTO ubi = new UbicacionesDemostracionDTO();

                                        ubi.setAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(u).getAbsEntry());
                                        ubi.setRegresar(regresar);
                                        if (u.contains("(")) {
                                            for (String s1 : u.split("(")) {
                                                ubi.setCantidad(Integer.parseInt(s1.replace(")", "")));
                                            }
                                        } else {
                                            ubi.setCantidad(d.getCantidad());
                                        }
                                        det.getUbicaciones().add(ubi);
                                    }

                                    detalle.remove(d);
                                    break;
                                }
                            }
                        }
                    }

                    detalleDemostracion.add(det);
                }
            }

            Demostracion demo = demostracionFacade.find(demostracion.getIdDemostracion());

            if (demo != null && demo.getIdDemostracion() != null && demo.getIdDemostracion() != 0) {
                if (crearTrasladoDemostracion()) {
                    demo.setEstado("DC");

                    try {
                        demostracionFacade.edit(demo);
                        log.log(Level.INFO, "Se cancelo la demostracion con id {0}", demostracion.getIdDemostracion());
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al cancelar la demostracion con id {0}. Error {1}", new Object[]{demostracion.getIdDemostracion(), e.getMessage()});
                    }
                } else {
                    mostrarMensaje("Error", "No fue posible cancelar la demostración.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible cancelar la demostracion");
                    return;
                }

                SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                List<String[]> adjuntos = new ArrayList<>();
                Map<String, String> params = new HashMap<>();

                try {
                    GenericRESTResponseDTO res = new GenericRESTResponseDTO();

                    params.put("empleado", baruGenericMBean.obtenerNombreAsesor(demostracion.getCodigoAsesor()));
                    params.put("nombreDemostracion", "[" + demostracion.getIdDemostracion() + "] " + demostracion.getNombreDemostracion().toUpperCase());
                    params.put("sucursal", userSessionInfoMBean.getAlmacen());
                    params.put("fecha", new SimpleDateFormat("dd-MM-yyyy").format(demostracion.getFechaDemostracion()));
                    params.put("direccion", socioDeNegociosFacade.obtenerDireccionEntrgaCliente(demostracion.getDocumentoCliente()));
                    params.put("factura", demostracion.getFacturaAsociada() != null && demostracion.getFacturaAsociada() != 0 ? demostracion.getFacturaAsociada().toString() : "No aplica");
                    params.put("comentario", "No aplica");
                    params.put("estado", "Cancelada");
                    params.put("usuario", userSessionInfoMBean.getUsuario().toUpperCase());

                    res = client.enviarHtmlEmail("Demostracion", "Demostración [" + demostracion.getIdDemostracion() + "] " + demostracion.getNombreDemostracion().toUpperCase() + " - CANCELADA",
                            applicationMBean.getDestinatariosPlantillaEmail().get("demostracion").getTo().get(0), "demostracion", adjuntos, params);

                    log.log(Level.INFO, res.getMensaje());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion. ", e);
                }

                obtenerDemostraciones();
                obtenerAsesores();
                limpiar();
            }
        }
    }

    private boolean crearTrasladoDemostracion() {
        SesionSAPB1WSDTO sesionSAPDTO = applicationMBean.obtenerSesionSAP(userSessionInfoMBean.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            mostrarMensaje("Error", "No fue posible iniciar una sesion en SAP B1WS.", true, false, false);
            return false;
        }

        //Se crea el encabezado del traslado
        StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

        transfer.setCardCode(userSessionInfoMBean.getCedulaEmpleado() + "PR");
        transfer.setComments("Traslado cancelación demostración - Creada por asesor - WebService 360");
        transfer.setSalesPersonCode(Long.parseLong(userSessionInfoMBean.getCodigoVentas()));
        transfer.setSeries(193L);
        transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());

        int line = 0;
        for (DetalleDemostracionDTO d : detalleDemostracion) {
            StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

            detailLine.setFromWarehouseCode(d.getAlmacenDestino());
            detailLine.setItemCode(d.getReferencia());
            detailLine.setLineNum(line);
            detailLine.setQuantity(Double.valueOf(String.valueOf(d.getCantidad())));
            detailLine.setWarehouseCode(d.getAlmacenOrigen());
            detailLine.setBinAllocations(new ArrayList<StockTransferDocumentBinAllocationDTO>());

            StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();

            allocationLineFrom.setAllowNegativeQuantity(false);
            allocationLineFrom.setBaseLineNumber(line);
            allocationLineFrom.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getUbicacionDestino()).getAbsEntry().longValue());
            allocationLineFrom.setBinActionType("salida");
            allocationLineFrom.setQuantity(Integer.valueOf(d.getCantidad()).doubleValue());

            detailLine.addBinAllocation(allocationLineFrom);

            if (ubicacionSAPFacade.validarUbicacionesAlmacen(d.getAlmacenOrigen())) {
                StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

                allocationLineTo.setAllowNegativeQuantity(false);
                allocationLineTo.setBaseLineNumber(line);
                allocationLineTo.setBinActionType("entrada");

                if (d.getUbicaciones() != null && !d.getUbicaciones().isEmpty()) {
                    for (UbicacionesDemostracionDTO u : d.getUbicaciones()) {
                        allocationLineTo.setQuantity(Integer.valueOf(u.getCantidad()).doubleValue());
                        if (u.getAbsEntry() == ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getUbicacionDestino()).getAbsEntry()) {
                            allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getAlmacenOrigen() + "TM").getAbsEntry().longValue());
                        } else {
                            allocationLineTo.setBinAbsEntry(Integer.valueOf(u.getAbsEntry()).longValue());
                        }
                    }
                } else {
                    allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(d.getAlmacenOrigen() + "TM").getAbsEntry().longValue());
                    allocationLineTo.setQuantity(Integer.valueOf(d.getCantidad()).doubleValue());
                }

                detailLine.addBinAllocation(allocationLineTo);
            }

            transfer.addLine(detailLine);
        }

        StockTransferClient client = new StockTransferClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        GenericRESTResponseDTO res = client.crearStockTransfer(transfer);
        if (res.getEstado() <= 0) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el traslado para cancelar la demostracion");
            return false;
        }
        log.log(Level.INFO, "Se creo traslado de demostracion numero {0}", trasladosSAPFacade.find(res.getValor()).getDocNum());

        if (!asignarAtributosDemostracion()) {
            log.log(Level.INFO, "No se pudo setear los atributos la demostración");
        }
        return true;
    }

    private boolean asignarAtributosDemostracion() {
        /**
         * Se deben asignar a todas las ubicaciones los valores de atributos
         * asignados por el usuario
         */

        List<String> almacenesDemo = almacenFacade.obtenerAlmacenesDemoAsesor(userSessionInfoMBean.getCodigoVentas());

        if (almacenesDemo != null && !almacenesDemo.isEmpty()) {
            BinLocationsClient client = new BinLocationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
            for (String a : almacenesDemo) {
                UbicacionSAP ubicacion = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(a + demostracion.getCodigoAsesor() + demostracion.getSlot());

                if (ubicacion != null && ubicacion.getAbsEntry() != null && ubicacion.getAbsEntry() != 0) {
                    try {
                        BinLocationDTO dto = new BinLocationDTO();

                        dto.setAbsEntry(ubicacion.getAbsEntry());
                        dto.setAttribute1(ubicacion.getAttr1Val() != null && ubicacion.getAttr1Val().equals("Sí") ? true : false);
                        dto.setAttribute6(ubicacion.getAttr6Val() != null && ubicacion.getAttr6Val().equals("Sí") ? true : false);
                        dto.setAttribute9(ubicacion.getAttr9Val() != null && ubicacion.getAttr9Val().equals("Sí") ? true : false);
                        dto.setSublevel1(ubicacion.getSL1Code());
                        dto.setSublevel2(ubicacion.getSL2Code());
                        dto.setWarehouse(ubicacion.getWhsCode());
                        dto.setAttribute2(null);
                        dto.setAttribute3(null);
                        dto.setAttribute4(null);
                        dto.setAttribute5(null);
                        dto.setAttribute7(null);
                        dto.setAttribute8(null);

                        GenericRESTResponseDTO res = client.editarUbicacion(userSessionInfoMBean.getUsuario(), dto);
                        if (res.getEstado() <= 0) {
                            log.log(Level.SEVERE, "Ocurrio un error al limpiar la ubicacion del asesor");
                            return false;
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al limpiar la ubicacion del asesor. ", e);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private void limpiar() {
        demostracion = new DemostracionDTO();
        detalleDemostracion = new ArrayList<>();
    }

    public String abrirPdf() {
        if (demostracion != null && demostracion.getIdDemostracion() != 0) {
            String[] adjunto = generarDocumento(demostracion.getIdDemostracion(), 1, "[" + demostracion.getIdDemostracion() + "] " + demostracion.getNombreDemostracion().toUpperCase(),
                    "demostracion", userSessionInfoMBean.getAlmacen(), demostracion.getNombreDemostracion(), false, null);

            if (adjunto != null) {
                if (new File(adjunto[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.demostraciones") + "[" + demostracion.getIdDemostracion() + "] "
                                + demostracion.getNombreDemostracion().toUpperCase();
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
