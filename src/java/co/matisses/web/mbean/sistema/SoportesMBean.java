package co.matisses.web.mbean.sistema;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.AdjuntoNext;
import co.matisses.persistence.web.entity.BwsUsuario;
import co.matisses.persistence.web.entity.ComentarioNext;
import co.matisses.persistence.web.entity.EstadoSolicitudNext;
import co.matisses.persistence.web.entity.EstadosNext;
import co.matisses.persistence.web.entity.PrioridadNext;
import co.matisses.persistence.web.entity.SolicitudNext;
import co.matisses.persistence.web.entity.TipoSolicitudNext;
import co.matisses.persistence.web.facade.AdjuntoNextFacade;
import co.matisses.persistence.web.facade.BwsUsuarioFacade;
import co.matisses.persistence.web.facade.ComentarioNextFacade;
import co.matisses.persistence.web.facade.EstadoSolicitudNextFacade;
import co.matisses.persistence.web.facade.PrioridadNextFacade;
import co.matisses.persistence.web.facade.SolicitudNextFacade;
import co.matisses.persistence.web.facade.TipoSolicitudNextFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.AdjuntoNextDTO;
import co.matisses.web.dto.ComentarioNextDTO;
import co.matisses.web.dto.EstadosNextDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.PrioridadNextDTO;
import co.matisses.web.dto.SolicitudDTO;
import co.matisses.web.dto.TipoSolicitudNextDTO;
import co.matisses.web.ldap.BaruLDAPAuth;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.servlet.http.Part;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "soportesMBean")
public class SoportesMBean implements Serializable {

    @Inject
    private BaruLDAPAuth ldapAuth;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(SoportesMBean.class.getSimpleName());
    private int pagina = 1;
    private int totalPaginas = 1;
    private int datosPagina = 10;
    private Integer idTipoSolicitud;
    private String asunto;
    private String solicitud;
    private String comentario;
    private String parametro;
    private boolean dlgAdvertencia;
    private boolean dlgCrear;
    private Part adjunto;
    private SolicitudDTO soporte;
    private List<Integer> paginas;
    private List<String> administradores;
    private List<SolicitudDTO> solicitudes;
    private List<TipoSolicitudNextDTO> tiposSolicitud;
    private List<AdjuntoNextDTO> adjuntos;
    private List<PrioridadNextDTO> prioridades;
    @EJB
    private SolicitudNextFacade solicitudNextFacade;
    @EJB
    private TipoSolicitudNextFacade tipoSolicitudNextFacade;
    @EJB
    private AdjuntoNextFacade adjuntoNextFacade;
    @EJB
    private ComentarioNextFacade comentarioNextFacade;
    @EJB
    private BwsUsuarioFacade bwsUsuarioFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private PrioridadNextFacade prioridadNextFacade;
    @EJB
    private EstadoSolicitudNextFacade estadoSolicitudNextFacade;

    public SoportesMBean() {
        soporte = new SolicitudDTO();
        solicitudes = new ArrayList<>();
        tiposSolicitud = new ArrayList<>();
        adjuntos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarTiposSolicitudes();
        cargarAdministradores();
        cargarPrioridades();
        obtenerSoportes();

        String id = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("solicitud");
        if (id != null && !id.isEmpty()) {
            cargarSolicitud(Integer.parseInt(id));
        }
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

    public Integer getIdTipoSolicitud() {
        return idTipoSolicitud;
    }

    public void setIdTipoSolicitud(Integer idTipoSolicitud) {
        this.idTipoSolicitud = idTipoSolicitud;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getSolicitud() {
        return solicitud;
    }

    public void setSolicitud(String solicitud) {
        this.solicitud = solicitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public boolean isDlgAdvertencia() {
        return dlgAdvertencia;
    }

    public void setDlgAdvertencia(boolean dlgAdvertencia) {
        this.dlgAdvertencia = dlgAdvertencia;
    }

    public boolean isDlgCrear() {
        return dlgCrear;
    }

    public void setDlgCrear(boolean dlgCrear) {
        this.dlgCrear = dlgCrear;
    }

    public Part getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Part adjunto) {
        this.adjunto = adjunto;
    }

    public SolicitudDTO getSoporte() {
        return soporte;
    }

    public void setSoporte(SolicitudDTO soporte) {
        this.soporte = soporte;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<String> getAdministradores() {
        return administradores;
    }

    public void setAdministradores(List<String> administradores) {
        this.administradores = administradores;
    }

    public List<SolicitudDTO> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudDTO> solicitudes) {
        this.solicitudes = solicitudes;
    }

    public List<TipoSolicitudNextDTO> getTiposSolicitud() {
        return tiposSolicitud;
    }

    public void setTiposSolicitud(List<TipoSolicitudNextDTO> tiposSolicitud) {
        this.tiposSolicitud = tiposSolicitud;
    }

    public List<AdjuntoNextDTO> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoNextDTO> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<PrioridadNextDTO> getPrioridades() {
        return prioridades;
    }

    public void setPrioridades(List<PrioridadNextDTO> prioridades) {
        this.prioridades = prioridades;
    }

    public boolean getResponsable() {
        if (soporte != null && soporte.getEstado().getNombreEstado() != null && !soporte.getEstado().getNombreEstado().equals("Resuelto") && !soporte.getEstado().getNombreEstado().equals("Cancelado")
                && !soporte.getEstado().getNombreEstado().equals("Aprobado") && !soporte.getEstado().getNombreEstado().equals("Imprimir Aut")
                && soporte.getEncargado() != null && soporte.getEncargado().equals(sessionInfoMBean.getUsuario())) {
            return true;
        }

        return false;
    }

    public boolean getEditable() {
        if ((soporte != null && soporte.getEstado().getNombreEstado() != null) && (soporte.getEstado().getNombreEstado().equals("Cancelado") || soporte.getEstado().getNombreEstado().equals("Aprobado")
                || (soporte.getEstado().getNombreEstado().equals("Resuelto")
                && soporte.getUsuario() != null && !soporte.getUsuario().equals(sessionInfoMBean.getUsuario())))) {
            return false;
        }

        return true;
    }

    public void cargarTiposSolicitudes() {
        tiposSolicitud = new ArrayList<>();

        List<TipoSolicitudNext> types = tipoSolicitudNextFacade.findAll();

        if (types != null && !types.isEmpty()) {
            types.stream().map((t) -> {
                TipoSolicitudNextDTO dto = new TipoSolicitudNextDTO();

                dto.setIdTipoSolicitud(t.getIdTipoSolicitud());
                dto.setNombre(t.getSolicitud());

                return dto;
            }).forEach((dto) -> {
                tiposSolicitud.add(dto);
            });

            Collections.sort(tiposSolicitud, (TipoSolicitudNextDTO o1, TipoSolicitudNextDTO o2) -> o1.getNombre().compareTo(o2.getNombre()));
        }
    }

    private void cargarAdministradores() {
        administradores = ldapAuth.listAdminUsers();
    }

    private void cargarPrioridades() {
        prioridades = new ArrayList<>();

        List<PrioridadNext> priorities = prioridadNextFacade.findAll();

        if (priorities != null && !priorities.isEmpty()) {
            priorities.stream().map((p) -> {
                PrioridadNextDTO dto = new PrioridadNextDTO();

                dto.setIdPrioridad(p.getIdPrioridad());
                dto.setPrioridad(p.getPrioridad());

                return dto;
            }).forEach((dto) -> {
                prioridades.add(dto);
            });
        }
    }

    public void obtenerSoportes() {
        long nroRegistros = 0;
        if (sessionInfoMBean.validarPermisoUsuario(Objetos.SOLICITUD_SISTEMAS, Acciones.ADMINISTRAR)) {
            nroRegistros = solicitudNextFacade.obtenerTotalDatos(null, parametro);
        } else {
            nroRegistros = solicitudNextFacade.obtenerTotalDatos(sessionInfoMBean.getUsuario(), parametro);
        }
        solicitudes = new ArrayList<>();

        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPagina) + (nroRegistros % datosPagina > 0 ? 1 : 0);
        if (pagina == 0) {
            pagina = 1;
        } else if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }

        List<SolicitudNext> sol = new ArrayList<>();
        if (sessionInfoMBean.validarPermisoUsuario(Objetos.SOLICITUD_SISTEMAS, Acciones.ADMINISTRAR)) {
            sol = solicitudNextFacade.listarSolicitudes(pagina, datosPagina, null, parametro);
        } else {
            sol = solicitudNextFacade.listarSolicitudes(pagina, datosPagina, sessionInfoMBean.getUsuario(), parametro);
        }

        if (sol != null && !sol.isEmpty()) {
            for (SolicitudNext s : sol) {
                SolicitudDTO dto = new SolicitudDTO();

                dto.setAsunto(s.getAsunto());
                dto.setUsuario(s.getUsuario());
                if (s.getIdPrioridad() != null) {
                    dto.setIdPrioridad(new PrioridadNextDTO(s.getIdPrioridad().getIdPrioridad(), s.getIdPrioridad().getPrioridad()));
                }
                dto.setIdSolicitud(s.getIdSolicitud());
                dto.setIdTipoSolicitud(new TipoSolicitudNextDTO(s.getIdTipoSolicitud().getIdTipoSolicitud(), s.getIdTipoSolicitud().getSolicitud()));
                dto = validarEstadoSoporte(dto);

                solicitudes.add(dto);
            }
        }

        construirPaginador();
    }

    private void construirPaginador() {
        paginas = new ArrayList<>();
        int posIni, posFin;
        if (pagina == 1) {
            posIni = pagina - 0;
            posFin = pagina + 4;
        } else if (pagina == 2) {
            posIni = pagina - 1;
            posFin = pagina + 3;
        } else if (pagina > 2 && pagina <= (totalPaginas - 2)) {
            posIni = pagina - 2;
            posFin = pagina + 2;
        } else if (totalPaginas - pagina == 1) {
            posIni = (pagina - 3) == 0 ? 1 : pagina - 3;
            posFin = pagina + 1;
        } else {
            posIni = (pagina - 4) <= 0 ? 1 : pagina - 4;
            posFin = (int) totalPaginas;
        }
        for (int i = posIni; i <= posFin && i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerSoportes();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerSoportes();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerSoportes();
    }

    public void agregarAdjunto() {
        if (adjunto == null) {
            return;
        }

        CONSOLE.log(Level.INFO, "Recibiendo archivo {0}", adjunto.getSubmittedFileName());
        try (InputStream input = adjunto.getInputStream()) {
            File file = new File(System.getProperty("jboss.server.temp.dir"), adjunto.getSubmittedFileName());
            Files.copy(input, file.toPath());

            if (soporte != null && soporte.getIdSolicitud() != null && soporte.getIdSolicitud() != 0) {
                soporte.getAdjuntos().add(new AdjuntoNextDTO(null, null, file.getName(), Files.probeContentType(Paths.get(file.getPath())), Files.readAllBytes(file.toPath())));
                CONSOLE.log(Level.INFO, "Numero de archivos adjuntos: {0}", soporte.getAdjuntos().size());
            } else {
                adjuntos.add(new AdjuntoNextDTO(null, null, file.getName(), Files.probeContentType(Paths.get(file.getPath())), Files.readAllBytes(file.toPath())));
                CONSOLE.log(Level.INFO, "Numero de archivos adjuntos: {0}", adjuntos.size());
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }

    public StreamedContent getFile(AdjuntoNextDTO attachment) {
        InputStream stream = new ByteArrayInputStream(attachment.getArchivo());
        return new DefaultStreamedContent(stream, attachment.getContentType(), attachment.getNombre());
    }

    public void eliminarAdjunto() {
        String nombre = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombre");

        if (soporte != null && soporte.getIdSolicitud() != null && soporte.getIdSolicitud() != 0) {
            for (AdjuntoNextDTO a : soporte.getAdjuntos()) {
                if (a.getNombre().equals(nombre)) {
                    soporte.getAdjuntos().remove(a);
                    break;
                }
            }
        } else {
            for (AdjuntoNextDTO a : adjuntos) {
                if (a.getNombre().equals(nombre)) {
                    adjuntos.remove(a);
                    break;
                }
            }
        }
    }

    public void abrirDlgCrear() {
        dlgCrear = false;
        dlgAdvertencia = false;
        if (solicitudNextFacade.contarSolicitudesPendientes(sessionInfoMBean.getUsuario()) >= 5) {
            dlgAdvertencia = true;
        } else {
            dlgCrear = true;
        }
    }

    public void guardarSolicitud() {
        if (asunto == null || asunto.isEmpty()) {
            mostrarMensaje("Error", "Debe asignar un asunto al soporte.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe asignar un asunto al soporte");
            return;
        }
        if (idTipoSolicitud == null || idTipoSolicitud == 0) {
            mostrarMensaje("Error", "Debe seleccionar un tipo de solicitud.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar un tipo de solicitud");
            return;
        }
        if (solicitud == null || solicitud.isEmpty()) {
            mostrarMensaje("Error", "Describa su solicitud.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Describa su solicitud");
            return;
        }

        SolicitudNext request = new SolicitudNext();

        request.setAsunto(asunto.toUpperCase());
        request.setFecha(new Date());
        request.setIdPrioridad(new PrioridadNext(4));
        request.setIdTipoSolicitud(new TipoSolicitudNext(idTipoSolicitud));
        request.setUsuario(sessionInfoMBean.getUsuario());

        try {
            solicitudNextFacade.create(request);
            CONSOLE.log(Level.INFO, "Se registro un nuevo soporte con id {0}, asunto {1}, requerimiento: {2}", new Object[]{request.getIdSolicitud(), request.getAsunto(), solicitud});

            if (adjuntos != null && !adjuntos.isEmpty()) {
                agregarAdjuntos(request.getIdSolicitud(), adjuntos);
            }

            /*Se guarda en la tabla de comentarios, el requerimiento*/
            ComentarioNext comment = new ComentarioNext();

            comment.setComentario(solicitud);
            comment.setFecha(new Date());
            comment.setIdSolicitud(new SolicitudNext(request.getIdSolicitud()));
            comment.setUsuario(sessionInfoMBean.getUsuario());

            comentarioNextFacade.create(comment);
            CONSOLE.log(Level.INFO, "Se registro un comentario con id {0}, para el idSoporte {1}", new Object[]{comment.getIdComentario(), request.getIdSolicitud()});

            /*Se guarda el registro para estado de solicitud*/
            EstadoSolicitudNext estado = new EstadoSolicitudNext();

            estado.setFecha(new Date());
            estado.setIdEstado(new EstadosNext(1));
            estado.setIdSolicitud(new SolicitudNext(request.getIdSolicitud()));
            estado.setUsuario(sessionInfoMBean.getUsuario());

            estadoSolicitudNextFacade.create(estado);
            CONSOLE.log(Level.INFO, "Se registro el estado del soporte como pendiente");

            dlgCrear = false;
            obtenerSoportes();
            enviarNotificacion(request.getIdSolicitud(), null, "Nuevo",
                    "Se ha creado una solicitud de servicio por el usuario <b>" + genericMBean.obtenerNombreEmpleadoCedula(obtenerDocumentoUsuario(sessionInfoMBean.getUsuario())) + "</b>.", solicitud);
            limpiarSoporte();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear la solicitud. ", e);
        }
    }

    public void abrirSolicitud() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolicitud"));
        cargarSolicitud(id);
    }

    private void cargarSolicitud(Integer idSolicitud) {
        soporte = new SolicitudDTO();

        SolicitudNext request = solicitudNextFacade.find(idSolicitud);

        if (request != null && request.getIdSolicitud() != null && request.getIdSolicitud() != 0) {
            soporte.setIdSolicitud(request.getIdSolicitud());
            soporte.setAsunto(request.getAsunto());
            soporte.setIdTipoSolicitud(new TipoSolicitudNextDTO(request.getIdTipoSolicitud().getIdTipoSolicitud(), request.getIdTipoSolicitud().getSolicitud()));
            soporte.setUsuario(request.getUsuario());
            soporte.setEmpleado(obtenerDocumentoUsuario(request.getUsuario()));
            soporte.setIdPrioridad(new PrioridadNextDTO(request.getIdPrioridad().getIdPrioridad(), request.getIdPrioridad().getPrioridad()));

            soporte = validarEstadoSoporte(soporte);

            /*Se carga el historial de comentarios*/
            List<ComentarioNext> comments = comentarioNextFacade.obtenerComentariosSolicitud(soporte.getIdSolicitud());

            if (comments != null && !comments.isEmpty()) {
                comments.stream().map((c) -> {
                    ComentarioNextDTO dto = new ComentarioNextDTO();

                    dto.setComentario(c.getComentario());
                    dto.setFecha(c.getFecha());
                    dto.setIdComentario(c.getIdComentario());
                    dto.setUsuario(c.getUsuario());

                    return dto;
                }).forEach((dto) -> {
                    soporte.getComentarios().add(dto);
                });
            }

            /*Se cargan los adjuntos*/
            List<AdjuntoNext> attachments = adjuntoNextFacade.obtenerAdjuntosSolicitud(idSolicitud);

            if (attachments != null && !attachments.isEmpty()) {
                attachments.stream().map((a) -> {
                    AdjuntoNextDTO dto = new AdjuntoNextDTO();

                    dto.setArchivo(a.getArchivo());
                    dto.setContentType(a.getContentType());
                    dto.setIdAdjunto(a.getIdAdjunto());
                    dto.setIdSolicitud(idSolicitud);
                    dto.setNombre(a.getNombre());

                    return dto;
                }).forEach((dto) -> {
                    soporte.getAdjuntos().add(dto);
                });
            }
        }
    }

    private SolicitudDTO validarEstadoSoporte(SolicitudDTO solicitud) {
        List<EstadoSolicitudNext> status = estadoSolicitudNextFacade.obtenerEstadosSolicitud(solicitud.getIdSolicitud());

        if (status != null && !status.isEmpty()) {
            solicitud.setEstado(new EstadosNextDTO(status.get(0).getIdEstado().getIdEstado(), status.get(0).getIdEstado().getNombreEstado()));

            if (null != status.get(0).getIdEstado().getIdEstado()) {
                List<EstadoSolicitudNext> estado = estadoSolicitudNextFacade.obtenerSolicitudEstado(solicitud.getIdSolicitud(), 2);

                switch (status.get(0).getIdEstado().getIdEstado()) {
                    case 1:
                        break;
                    case 2:
                        solicitud.setEncargado(status.get(0).getUsuario());
                        break;
                    case 3:
                        if (estado != null && estado.size() > 0 && estado.get(0).getIdEstSolicitud() != null && estado.get(0).getIdEstSolicitud() != 0) {
                            solicitud.setEncargado(estado.get(0).getUsuario());
                        }
                        break;
                    case 4:
                        if (estado != null && estado.size() > 0 && estado.get(0).getIdEstSolicitud() != null && estado.get(0).getIdEstSolicitud() != 0) {
                            solicitud.setEncargado(estado.get(0).getUsuario());
                        }
                        break;
                    case 5:
                        if (estado != null && estado.size() > 0 && estado.get(0).getIdEstSolicitud() != null && estado.get(0).getIdEstSolicitud() != 0) {
                            solicitud.setEncargado(estado.get(0).getUsuario());
                        }
                        break;
                    case 7:
                        if (estado != null && estado.size() > 0 && estado.get(0).getIdEstSolicitud() != null && estado.get(0).getIdEstSolicitud() != 0) {
                            solicitud.setEncargado(estado.get(0).getUsuario());
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return solicitud;
    }

    public void guardarCambiosSoporte() {
        if (!validarComentario()) {
            return;
        }

        if (soporte.getEstado() != null && soporte.getEstado().getNombreEstado() != null && soporte.getEstado().getNombreEstado().equals("Pendiente")
                && soporte.getEncargado() != null && !soporte.getEncargado().isEmpty()) {
            if (soporte.getIdPrioridad() == null || soporte.getIdPrioridad().getIdPrioridad() == null || soporte.getIdPrioridad().getIdPrioridad() == 4) {
                mostrarMensaje("Error", "Debe seleccionar la prioridad del soporte para poder guardar.", true, false, false);
                CONSOLE.log(Level.SEVERE, "Debe seleccionar la prioridad del soporte para poder guardar");
                return;
            }

            /*Guarda el registro para el estado de la solicitud*/
            EstadoSolicitudNext estado = new EstadoSolicitudNext();

            estado.setFecha(new Date());
            estado.setIdEstado(new EstadosNext(2));
            estado.setIdSolicitud(new SolicitudNext(soporte.getIdSolicitud()));
            estado.setUsuario(soporte.getEncargado());

            try {
                estadoSolicitudNextFacade.create(estado);
                CONSOLE.log(Level.INFO, "Se asigno el soporte con id {0}", soporte.getIdSolicitud());

                /*Guardar el comentario de la solicitud*/
                agregarComentario();
                agregarAdjuntos(soporte.getIdSolicitud(), soporte.getAdjuntos());

                /*Se guarda la prioridad en la bd*/
                SolicitudNext request = solicitudNextFacade.find(soporte.getIdSolicitud());

                if (request != null && request.getIdSolicitud() != null && request.getIdSolicitud() != 0) {
                    request.setIdPrioridad(new PrioridadNext(soporte.getIdPrioridad().getIdPrioridad()));

                    solicitudNextFacade.edit(request);
                }
                enviarNotificacion(soporte.getIdSolicitud(), soporte.getEncargado(), "Asignado", "Se ha asignado la solicitud al empleado <b>"
                        + genericMBean.obtenerNombreEmpleadoCedula(obtenerDocumentoUsuario(soporte.getEncargado())) + "</b>, con la prioridad <b>"
                        + obtenerNombrePrioridad(soporte.getIdPrioridad().getIdPrioridad()) + "</b>", comentario);
            } catch (Exception e) {
            }
        } else {
            agregarComentario();
            agregarAdjuntos(soporte.getIdSolicitud(), soporte.getAdjuntos());
            enviarNotificacion(soporte.getIdSolicitud(), soporte.getEncargado(), "Nuevo comentario y/o adjuntos",
                    "Se ha ingresado un nuevo comentario y/o adjuntos por el usuario <b>" + genericMBean.obtenerNombreEmpleadoCedula(obtenerDocumentoUsuario(sessionInfoMBean.getUsuario())) + "</b>.",
                    comentario);
        }
        cargarSolicitud(soporte.getIdSolicitud());
        limpiarDatosBasicos();
        obtenerSoportes();
    }

    public void solucionarSoporte() {
        if (!validarComentario()) {
            return;
        }

        boolean resuelto = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("resuelto"));

        EstadoSolicitudNext estado = new EstadoSolicitudNext();

        estado.setFecha(new Date());
        estado.setIdSolicitud(new SolicitudNext(soporte.getIdSolicitud()));
        estado.setUsuario(sessionInfoMBean.getUsuario());

        if (resuelto) {
            estado.setIdEstado(new EstadosNext(3));
        } else {
            estado.setIdEstado(new EstadosNext(4));
        }

        try {
            estadoSolicitudNextFacade.create(estado);
            CONSOLE.log(Level.INFO, "Se {1} el soporte con id {0}", new Object[]{soporte.getIdSolicitud(), (estado.getIdEstado().getIdEstado() == 3) ? "resolvio" : "cancelo"});

            agregarComentario();
            agregarAdjuntos(soporte.getIdSolicitud(), soporte.getAdjuntos());
            cargarSolicitud(soporte.getIdSolicitud());
            enviarNotificacion(soporte.getIdSolicitud(), soporte.getEncargado(), resuelto ? "Resuelto" : "Rechazado", "", comentario);
            limpiarDatosBasicos();
            obtenerSoportes();
        } catch (Exception e) {
        }
    }

    public void aprobarSoporte() {
        if (!validarComentario()) {
            return;
        }

        boolean aprobado = Boolean.parseBoolean(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("aprobado"));

        EstadoSolicitudNext estado = new EstadoSolicitudNext();

        estado.setUsuario(sessionInfoMBean.getUsuario());
        estado.setIdSolicitud(new SolicitudNext(soporte.getIdSolicitud()));
        estado.setFecha(new Date());

        if (aprobado) {
            estado.setIdEstado(new EstadosNext(5));
        } else {
            estado.setIdEstado(new EstadosNext(7));
        }

        try {
            estadoSolicitudNextFacade.create(estado);
            CONSOLE.log(Level.INFO, "{1} el soporte con id {0}", new Object[]{soporte.getIdSolicitud(), (estado.getIdEstado().getIdEstado() == 5) ? "Se aprobo" : "No se aprobo"});

            agregarComentario();
            agregarAdjuntos(soporte.getIdSolicitud(), soporte.getAdjuntos());
            cargarSolicitud(soporte.getIdSolicitud());
            enviarNotificacion(soporte.getIdSolicitud(), soporte.getEncargado(), aprobado ? "Aprobado" : "No aprobado", "", comentario);
            limpiarDatosBasicos();
            obtenerSoportes();
        } catch (Exception e) {
        }
    }

    private boolean validarComentario() {
        if (comentario == null || comentario.isEmpty() || comentario.trim().isEmpty() || (comentario.contains(".") && comentario.length() < 5)) {
            mostrarMensaje("Error", "Debe ingresar un comentario para guardar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar un comentario para guardar");
            return false;
        }

        return true;
    }

    private String obtenerNombrePrioridad(Integer idPrioridad) {
        for (PrioridadNextDTO p : prioridades) {
            if (p.getIdPrioridad().equals(idPrioridad)) {
                return p.getPrioridad();
            }
        }

        return "";
    }

    private void agregarComentario() {
        ComentarioNext comment = new ComentarioNext();

        comment.setComentario(comentario);
        comment.setFecha(new Date());
        comment.setIdSolicitud(new SolicitudNext(soporte.getIdSolicitud()));
        comment.setUsuario(sessionInfoMBean.getUsuario());

        try {
            comentarioNextFacade.create(comment);
            CONSOLE.log(Level.INFO, "Se agrego un nuevo comentario al soporte con id {0}", soporte.getIdSolicitud());
        } catch (Exception e) {
        }
    }

    private void agregarAdjuntos(Integer idSolicitud, List<AdjuntoNextDTO> attachments) {
        attachments.stream().filter((a) -> (a.getIdAdjunto() == null)).map((a) -> {
            AdjuntoNext attached = new AdjuntoNext();

            attached.setArchivo(a.getArchivo());
            attached.setContentType(a.getContentType());
            attached.setIdSolicitud(new SolicitudNext(idSolicitud));
            attached.setNombre(a.getNombre());

            return attached;
        }).forEach((attached) -> {
            try {
                adjuntoNextFacade.create(attached);
            } catch (Exception e) {
            }
        });
    }

    public String obtenerDocumentoUsuario(String usuario) {
        BwsUsuario bwsUsuario = bwsUsuarioFacade.find(usuario);

        if (bwsUsuario != null && bwsUsuario.getUsuario() != null && !bwsUsuario.getUsuario().isEmpty()) {
            Empleado empleado = empleadoFacade.obtenerEmpleadoCodVentas(String.valueOf(bwsUsuario.getIdVendedor()));

            if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
                return empleado.getOfficeExt();
            }
        }

        return usuario;
    }

    private void limpiarDatosBasicos() {
        comentario = null;
        adjunto = null;
    }

    public void limpiarSoporte() {
        idTipoSolicitud = null;
        asunto = null;
        solicitud = null;
        dlgAdvertencia = false;
        dlgCrear = false;
        soporte = new SolicitudDTO();
        limpiarDatosBasicos();
    }

    private void enviarNotificacion(Integer idSolucitud, String encargado, String estado, String mensaje, String message) {
        Map<String, String> params = new HashMap<>();

        params.put("numero", idSolucitud.toString());
        params.put("estado", estado);
        params.put("mensaje", mensaje);
        params.put("comentario", "<b>Comentario</b>: " + message);
        params.put("link", applicationMBean.obtenerValorPropiedad("url.servidor") + "sistema/soporte/?solicitud=" + idSolucitud);

        MailMessageDTO dto = new MailMessageDTO();

        dto.setFrom("Solicitud servicio <notificaciones@matisses.co>");
        dto.setSubject("Solicitud servicio #" + idSolucitud);

        SolicitudNext next = solicitudNextFacade.find(idSolucitud);

        if (next != null && next.getIdSolicitud() != null && next.getIdSolicitud() != 0) {
            dto.addToAddress(next.getUsuario() + "@matisses.co");
        }
        if (encargado != null && !encargado.isEmpty()) {
            dto.addToAddress(encargado + "@matisses.co");
        }
        dto.addBccAddress(applicationMBean.getDestinatariosPlantillaEmail().get("soporte").getBcc().get(0));
        dto.setParams(params);
        dto.setTemplateName("soporte");

        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        client.enviarHtmlEmailSoporte(dto);
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
