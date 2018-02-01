package co.matisses.web.mbean.solicitud;

import co.matisses.persistence.web.entity.SolicitudTraslado;
import co.matisses.persistence.web.facade.SolicitudTrasladoFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SolicitudTrasladoDTO;
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
@Named(value = "listaSolicitudesMBean")
public class ListaSolicitudesMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    private static final Logger log = Logger.getLogger(ListaSolicitudesMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int solicitudesPagina;
    private String estado;
    private String sucursal;
    private String fechaInicialMovil;
    private String fechaFinalMovil;
    private String comentario;
    private boolean mostrarFiltros = false;
    private Date fechaInicial;
    private Date fechaFinal;
    private SolicitudTrasladoDTO solicitud;
    private List<Integer> paginas;
    private List<String[]> sucursales;
    private List<SolicitudTrasladoDTO> solicitudes;
    @EJB
    private SolicitudTrasladoFacade solicitudTrasladoFacade;

    public ListaSolicitudesMBean() {
        pagina = 1;
        totalPaginas = 0;
        solicitudesPagina = 12;
        solicitud = new SolicitudTrasladoDTO();
        paginas = new ArrayList<>();
        sucursales = new ArrayList<>();
        solicitudes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerSolicitudes();
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

    public int getSolicitudesPagina() {
        return solicitudesPagina;
    }

    public String getEstado() {
        return estado;
    }

    public String getSucursal() {
        return sucursal;
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

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
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

    public SolicitudTrasladoDTO getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(SolicitudTrasladoDTO solicitud) {
        this.solicitud = solicitud;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<String[]> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<String[]> sucursales) {
        this.sucursales = sucursales;
    }

    public List<SolicitudTrasladoDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudTrasladoDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public String getEstadoSeleccionado() {
        if (estado != null && !estado.isEmpty()) {
            switch (estado) {
                case "NF":
                    return "No finalizado";
                case "SP":
                    return "En proceso";
                case "SF":
                    return "Finalizado";
                case "SC":
                    return "Cancelado";
                case "SS":
                    return "Solicitado";
                default:
                    break;
            }
        }
        return "Seleccione";
    }

    public String getSucursalSeleccionada() {
        if (sucursal != null && !sucursal.isEmpty()) {
            for (String[] s : sucursales) {
                if (s[0].equals(sucursal)) {
                    return s[1];
                }
            }
        }
        return "Seleccione sucursal";
    }

    public String getSizePage() {
        if (solicitudesPagina != 100000) {
            return Integer.toString(solicitudesPagina);
        } else {
            return "Todos";
        }
    }

    public void seleccionarEstado() {
        estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");
        log.log(Level.INFO, "Se selecciono el estado {0} para filtrar", estado);

        pagina = 1;
        obtenerSolicitudes();
    }

    public void seleccionarSucursal() {
        sucursal = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sucursal");
        log.log(Level.INFO, "Se selecciono la sucursal {0} para filtrar", sucursal);

        pagina = 1;
        obtenerSolicitudes();
    }

    private void obtenerSolicitudes() {
        solicitudes = new ArrayList<>();

        if (!userSessionInfoMBean.validarPermisoUsuario(Objetos.SOLICITUDES_TODAS, Acciones.VISUALIZAR)) {
            sucursal = userSessionInfoMBean.getAlmacen();
        }
        long nroRegistros = solicitudTrasladoFacade.obtenerTotalDatos(estado, sucursal, fechaInicial, fechaFinal);
        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / solicitudesPagina) + (nroRegistros % solicitudesPagina > 0 ? 1 : 0);
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

            List<SolicitudTraslado> sols = solicitudTrasladoFacade.obtenerSolicitudes(pagina, solicitudesPagina, estado, sucursal, fechaInicial, fechaFinal);

            if (sols != null && !sols.isEmpty()) {
                for (SolicitudTraslado s : sols) {
                    solicitudes.add(new SolicitudTrasladoDTO(s.getIdSolicitud(), null, s.getSucursal(), s.getComentario(), s.getAutor(),
                            null, null, s.getEstado(), s.getUsuarioResponsable(), s.getFecha(), s.getFechaFinal(), null));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se encontraron datos.", e);
        }

        obtenerSucursales();
    }

    private void obtenerSucursales() {
        sucursales = new ArrayList<>();

        List<String> branchs = solicitudTrasladoFacade.obtenerSucursalesSolicitudes(estado, sucursal, fechaInicial, fechaFinal);

        if (branchs != null && !branchs.isEmpty()) {
            for (String s : branchs) {
                String nombreAlmacen = baruGenericMBean.obtenerNombreWebAlmacen(s);

                sucursales.add(new String[]{s, nombreAlmacen});
            }

            Collections.sort(sucursales, new Comparator<String[]>() {
                @Override
                public int compare(String[] t, String[] t1) {
                    return t[1].compareTo(t1[1]);
                }
            });
        }
    }

    public void mostrarFiltrosMovil() {
        mostrarFiltros = !mostrarFiltros;
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
        obtenerSolicitudes();
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
            } else {
                mostrarMensaje("Error", "Ingrese las fechas con las que quiere realizar la busqueda.", true, false, false);
                log.log(Level.SEVERE, "Ingrese las fechas con las que quiere realizar la busqueda");
                return;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al convertir las fechas. Error [{0}]", e.getMessage());
            mostrarMensaje("Error", "No se pudieron usar las fechas suministradas.", true, false, false);
            return;
        }

        pagina = 1;
        obtenerSolicitudes();
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void cambiarSizePage() {
        solicitudesPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de cotizaicones x pagina a [{0}]", solicitudesPagina);
        pagina = 1;
        obtenerSolicitudes();
    }

    public void mostrarPrimeraPagina() {
        log.log(Level.INFO, "Mostrando los pedidos de la primer pagina");
        pagina = 1;
        obtenerSolicitudes();
    }

    public void mostrarUltimaPagina() {
        log.log(Level.INFO, "Mostrando los pedidos de la ultima pagina");
        pagina = totalPaginas;
        obtenerSolicitudes();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            log.log(Level.INFO, "Mostrando los pedidos de la pagina {0}", pagina);
            obtenerSolicitudes();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            log.log(Level.INFO, "Mostrando los pedidos de la pagina {0}", pagina);
            obtenerSolicitudes();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        log.log(Level.INFO, "Mostrando los pedidos de la pagina {0}", pagina);
        obtenerSolicitudes();
    }

    public void seleccionarSolicitud() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitud"));

        if (solicitud != null && solicitud.getIdSolicitud() != null && solicitud.getIdSolicitud().equals(id)) {
            solicitud = new SolicitudTrasladoDTO();
        } else {
            if (id == null || id == 0) {
                mostrarMensaje("Error", "Debe seleccionar un pedido para poder continuar.", true, false, false);
                log.log(Level.SEVERE, "Debe seleccionar un pedido para poder continuar");
                return;
            }

            for (SolicitudTrasladoDTO s : solicitudes) {
                if (s.getIdSolicitud().equals(id)) {
                    solicitud = s;
                }
            }
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
        if (solicitud != null && solicitud.getIdSolicitud() != null && solicitud.getIdSolicitud() != 0) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.solicitudes") + solicitud.getIdSolicitud();
            if (new File(applicationMBean.obtenerValorPropiedad("url.folder.solicitudes") + File.separator + solicitud.getIdSolicitud() + ".pdf").exists()) {
                return "openRuta('" + url + ".pdf');";
            } else {
                String[] adjunto = generarDocumento(solicitud.getIdSolicitud(), 1, String.valueOf(solicitud.getIdSolicitud()), "solicitud", userSessionInfoMBean.getAlmacen(), null, false, null);

                if (adjunto != null) {
                    if (new File(adjunto[0]).exists()) {
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
                } else {
                    log.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    public void finalizarSolicitud() {
        if (comentario == null || comentario.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un comentario para finalizar la solicitud.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un comentario para finalizar la solicitud");
            return;
        }

        SolicitudTraslado request = solicitudTrasladoFacade.find(solicitud.getIdSolicitud());

        if (request != null && request.getIdSolicitud() != null && request.getIdSolicitud() != 0) {
            request.setUsuarioResponsable(userSessionInfoMBean.getUsuario());
            request.setComentario(comentario);
            request.setEstado("SF");

            try {
                solicitudTrasladoFacade.edit(request);
                obtenerSolicitudes();
                solicitud = new SolicitudTrasladoDTO();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al finalizar la solicitud de traslado. ", e);
                mostrarMensaje("Error", "No fue posible finalizar la solicitud seleccionada.", true, false, false);
                return;
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
