//package co.matisses.web.mbean.encuestas;
//
//import co.matisses.persistence.web.entity.ConfiguracionEncuesta;
//import co.matisses.persistence.web.entity.Encuesta;
//import co.matisses.persistence.web.entity.PreguntaEncuesta;
//import co.matisses.persistence.web.facade.ConfiguracionEncuestaFacade;
//import co.matisses.persistence.web.facade.EncuestaFacade;
//import co.matisses.persistence.web.facade.PreguntaEncuestaFacade;
//import co.matisses.web.dto.ConfiguracionEncuestaDTO;
//import co.matisses.web.dto.EncuestaDTO;
//import co.matisses.web.dto.PreguntaEncuestaDTO;
//import co.matisses.web.dto.TipoRespuestaDTO;
//import co.matisses.web.mbean.BaruApplicationMBean;
//import co.matisses.web.mbean.UserSessionInfoMBean;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//import javax.annotation.PostConstruct;
//import javax.ejb.EJB;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.view.ViewScoped;
//import javax.inject.Inject;
//import javax.inject.Named;
//
///**
// *
// * @author ygil
// */
//@ViewScoped
//@Named(value = "maestroEncuestasMBean")
//public class MaestroEncuestasMBean implements Serializable {
//
//    @Inject
//    private UserSessionInfoMBean sessionMBean;
//    @Inject
//    private BaruApplicationMBean applicationMBean;
//    private static final Logger log = Logger.getLogger(MaestroEncuestasMBean.class.getSimpleName());
//    private Integer idEncuesta;
//    private String nombreEncuesta;
//    private boolean plantilla = false;
//    private boolean colaborativa = false;
//    private Date fechaInicio;
//    private Date fechaFin;
//    private EncuestaDTO encuesta;
//    private List<EncuestaDTO> plantillas;
//    private List<EncuestaDTO> encuestas;
//    private List<PreguntaEncuestaDTO> preguntasDisponibles;
//    private List<PreguntaEncuestaDTO> preguntasSeleccionadas;
//    @EJB
//    private EncuestaFacade encuestaFacade;
//    @EJB
//    private PreguntaEncuestaFacade preguntaEncuestaFacade;
//    @EJB
//    private ConfiguracionEncuestaFacade configuracionEncuestaFacade;
//
//    public MaestroEncuestasMBean() {
//        encuesta = new EncuestaDTO();
//        plantillas = new ArrayList<>();
//        encuestas = new ArrayList<>();
//        preguntasDisponibles = new ArrayList<>();
//    }
//
//    @PostConstruct
//    protected void initialize() {
//        obtenerPlantillasEncuesta();
//        obtenerEncuesta();
//    }
//
//    public UserSessionInfoMBean getSessionMBean() {
//        return sessionMBean;
//    }
//
//    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
//        this.sessionMBean = sessionMBean;
//    }
//
//    public BaruApplicationMBean getApplicationMBean() {
//        return applicationMBean;
//    }
//
//    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
//        this.applicationMBean = applicationMBean;
//    }
//
//    public Integer getIdEncuesta() {
//        return idEncuesta;
//    }
//
//    public void setIdEncuesta(Integer idEncuesta) {
//        this.idEncuesta = idEncuesta;
//    }
//
//    public String getNombreEncuesta() {
//        return nombreEncuesta;
//    }
//
//    public void setNombreEncuesta(String nombreEncuesta) {
//        this.nombreEncuesta = nombreEncuesta;
//    }
//
//    public boolean isPlantilla() {
//        return plantilla;
//    }
//
//    public void setPlantilla(boolean plantilla) {
//        this.plantilla = plantilla;
//    }
//
//    public boolean isColaborativa() {
//        return colaborativa;
//    }
//
//    public void setColaborativa(boolean colaborativa) {
//        this.colaborativa = colaborativa;
//    }
//
//    public Date getFechaInicio() {
//        return fechaInicio;
//    }
//
//    public void setFechaInicio(Date fechaInicio) {
//        this.fechaInicio = fechaInicio;
//    }
//
//    public Date getFechaFin() {
//        return fechaFin;
//    }
//
//    public void setFechaFin(Date fechaFin) {
//        this.fechaFin = fechaFin;
//    }
//
//    public List<EncuestaDTO> getPlantillas() {
//        return plantillas;
//    }
//
//    public void setPlantillas(List<EncuestaDTO> plantillas) {
//        this.plantillas = plantillas;
//    }
//
//    public List<EncuestaDTO> getEncuestas() {
//        return encuestas;
//    }
//
//    public void setEncuestas(List<EncuestaDTO> encuestas) {
//        this.encuestas = encuestas;
//    }
//
//    public List<PreguntaEncuestaDTO> getPreguntasDisponibles() {
//        return preguntasDisponibles;
//    }
//
//    public void setPreguntasDisponibles(List<PreguntaEncuestaDTO> preguntasDisponibles) {
//        this.preguntasDisponibles = preguntasDisponibles;
//    }
//
//    public List<PreguntaEncuestaDTO> getPreguntasSeleccionadas() {
//        return preguntasSeleccionadas;
//    }
//
//    public void setPreguntasSeleccionadas(List<PreguntaEncuestaDTO> preguntasSeleccionadas) {
//        this.preguntasSeleccionadas = preguntasSeleccionadas;
//    }
//
//    private void obtenerPlantillasEncuesta() {
//        plantillas = new ArrayList<>();
//
//        List<Encuesta> enc = encuestaFacade.obtenerEncuestasPlantilla();
//
//        if (enc != null && !enc.isEmpty()) {
//            for (Encuesta e : enc) {
//                plantillas.add(new EncuestaDTO(e.getIdEncuesta(), e.getNombreEncuesta(), e.getUsuarioCrea(), e.getEstado(),
//                        e.getPlantilla(), e.getColaborativa(), e.getFechaCreacion(), e.getFechaInicio(), e.getFechaFin(), obtenerConfiguracionEncuesta(e.getIdEncuesta())));
//            }
//        }
//    }
//
//    private void obtenerEncuesta() {
//        encuestas = new ArrayList<>();
//
//        List<Encuesta> enc = encuestaFacade.obtenerEncuestas();
//
//        if (enc != null && !enc.isEmpty()) {
//            for (Encuesta e : enc) {
//                encuestas.add(new EncuestaDTO(e.getIdEncuesta(), e.getNombreEncuesta(), e.getUsuarioCrea(), e.getEstado(),
//                        e.getPlantilla(), e.getColaborativa(), e.getFechaCreacion(), e.getFechaInicio(), e.getFechaFin(), obtenerConfiguracionEncuesta(e.getIdEncuesta())));
//            }
//        }
//    }
//
//    private List<ConfiguracionEncuestaDTO> obtenerConfiguracionEncuesta(Integer idEnc) {
//        if (idEnc != null && idEnc != 0) {
//            List<ConfiguracionEncuesta> conf = configuracionEncuestaFacade.obtenerConfiguracionEncuesta(idEnc);
//            List<ConfiguracionEncuestaDTO> configuracion = new ArrayList<>();
//
//            if (conf != null && !conf.isEmpty()) {
//                for (ConfiguracionEncuesta c : conf) {
//                    ConfiguracionEncuestaDTO dto = new ConfiguracionEncuestaDTO();
//
//                    dto.setIdConfiguracionEncuesta(c.getIdConfiguracionEncuesta());
//                    dto.setIdEncuesta(idEncuesta);
//                    dto.setIdPregunta(new PreguntaEncuestaDTO(c.getIdPregunta().getIdPregunta(), c.getIdPregunta().getPregunta(),
//                            new TipoRespuestaDTO(c.getIdPregunta().getIdTipoRespuesta().getIdTipoRespuesta(), c.getIdPregunta().getIdTipoRespuesta().getTipoRespuesta())));
//                    dto.setOrden(c.getOrden());
//
//                    configuracion.add(dto);
//                }
//            }
//
//            return configuracion;
//        }
//        return null;
//    }
//
//    public void seleccionarOpcionPlantilla() {
//        if (idEncuesta == null || idEncuesta == 0) {
//            if (plantilla) {
//                plantilla = false;
//                log.log(Level.INFO, "Se marco la actual configuracion de encuesta como no plantilla");
//            } else {
//                plantilla = true;
//                log.log(Level.INFO, "Se marco la actual configuracion de encuesta como plantilla");
//            }
//        }
//    }
//
//    public void seleccionarOpcionColaborativa() {
//        if (colaborativa) {
//            colaborativa = false;
//            log.log(Level.INFO, "Se marco la actual configuracion de encuesta como no colaborativa");
//        } else {
//            colaborativa = true;
//            log.log(Level.INFO, "Se marco la actual configuracion de encuesta como colaborativa");
//        }
//    }
//
//    public void agregarEncuesta() {
//        if (nombreEncuesta == null || nombreEncuesta.isEmpty()) {
//            mostrarMensaje("Error", "Debe ingresar un nombre para la encuesta.", true, false, false);
//            log.log(Level.SEVERE, "Debe ingresar un nombre para la encuesta");
//            return;
//        }
//
//        Encuesta enc = new Encuesta();
//
//        enc.setEstado("EP");
//        enc.setFechaCreacion(new Date());
//        enc.setFechaFin(fechaFin);
//        enc.setFechaInicio(fechaInicio);
//        enc.setNombreEncuesta(nombreEncuesta);
//        enc.setPlantilla(plantilla);
//        enc.setColaborativa(colaborativa);
//        enc.setUsuarioCrea("ygil");
//
//        try {
//            encuestaFacade.create(enc);
//            log.log(Level.INFO, "Se creo la encuesta con id [{0}]", enc.getIdEncuesta());
//            mostrarMensaje("Éxito", "Se creo la encuesta correctamente.", false, true, false);
//
//            idEncuesta = enc.getIdEncuesta();
//            obtenerPlantillasEncuesta();
//            obtenerEncuesta();
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Ocurrio un error al crear la encuesta. ", e);
//            mostrarMensaje("Error", "Ocurrió un error al crear la encuesta.", true, false, false);
//            return;
//        }
//    }
//
//    public void seleccionarPlantillaEncuesta() {
//        Integer idEncuestaTmp = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEncuesta"));
//
//        if (idEncuestaTmp.equals(idEncuesta)) {
//            idEncuesta = null;
//            nombreEncuesta = null;
//            fechaFin = null;
//            fechaInicio = null;
//            plantilla = false;
//            colaborativa = false;
//            encuesta = new EncuestaDTO();
//            preguntasDisponibles = new ArrayList<>();
//        } else {
//            for (EncuestaDTO e : plantillas) {
//                if (e.getIdEncuesta().equals(idEncuestaTmp)) {
//                    idEncuesta = e.getIdEncuesta();
//                    nombreEncuesta = e.getNombreEncuesta();
//                    fechaFin = e.getFechaFin();
//                    fechaInicio = e.getFechaInicio();
//                    plantilla = e.isPlantilla();
//                    colaborativa = e.isColaborativa();
//                    encuesta = e;
//
//                    obtenerPreguntasSeleccionadas();
//                    obtenerPreguntasDisponibles();
//
//                    break;
//                }
//            }
//        }
//    }
//
//    public void obtenerPreguntasSeleccionadas() {
//        preguntasSeleccionadas = new ArrayList<>();
//
//        List<PreguntaEncuesta> questions = preguntaEncuestaFacade.obtenerPreguntasEncuesta(idEncuesta);
//
//        if (questions != null && !questions.isEmpty()) {
//            for (PreguntaEncuesta p : questions) {
//                preguntasSeleccionadas.add(new PreguntaEncuestaDTO(p.getIdPregunta(), p.getPregunta(), null));
//            }
//        }
//    }
//
//    public void obtenerPreguntasDisponibles() {
//        preguntasDisponibles = new ArrayList<>();
//
//        List<PreguntaEncuesta> questions = preguntaEncuestaFacade.findAll();
//
//        if (questions != null && !questions.isEmpty()) {
//            for (PreguntaEncuesta p : questions) {
//                boolean existe = preguntasSeleccionadas.contains(new PreguntaEncuestaDTO(p.getIdPregunta(), p.getPregunta(), null));
//
//                if (!existe) {
//                    preguntasDisponibles.add(new PreguntaEncuestaDTO(p.getIdPregunta(), p.getPregunta(), null));
//                }
//            }
//        }
//    }
//
//    public void modificarEncuesta() {
//        if (nombreEncuesta == null || nombreEncuesta.isEmpty()) {
//            mostrarMensaje("Error", "Debe ingresar un nombre para la encuesta.", true, false, false);
//            log.log(Level.SEVERE, "Debe ingresar un nombre para la encuesta");
//            return;
//        }
//
//        Encuesta enc = encuestaFacade.find(idEncuesta);
//
//        if (enc != null && enc.getIdEncuesta() != null && enc.getIdEncuesta() != 0) {
//            enc.setFechaFin(fechaFin);
//            enc.setFechaInicio(fechaInicio);
//            enc.setNombreEncuesta(nombreEncuesta);
//            enc.setColaborativa(colaborativa);
//
//            try {
//                encuestaFacade.edit(enc);
//                log.log(Level.INFO, "Se modifico la encuesta con id [{0}]", enc.getIdEncuesta());
//                mostrarMensaje("Éxito", "Se modifico la encuesta correctamente.", false, true, false);
//
//                idEncuesta = enc.getIdEncuesta();
//                obtenerPlantillasEncuesta();
//                obtenerEncuesta();
//            } catch (Exception e) {
//                log.log(Level.SEVERE, "Ocurrio un error al modifico la encuesta. ", e);
//                mostrarMensaje("Error", "Ocurrió un error al modifico la encuesta.", true, false, false);
//                return;
//            }
//        }
//    }
//
//    public void crearEncuesta() {
//
//    }
//
//    public void seleccionarPreguntaDisponible() {
//        Integer idPregunta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPregunta"));
//
//        if (idPregunta != null && idPregunta != 0) {
//            ConfiguracionEncuesta conf = new ConfiguracionEncuesta();
//
//            conf.setIdEncuesta(new Encuesta(idEncuesta));
//            conf.setIdPregunta(new PreguntaEncuesta(idPregunta));
//            conf.setOrden(encuesta.getConfiguracion().size());
//
//            try {
//                configuracionEncuestaFacade.create(conf);
//                log.log(Level.INFO, "Se creo configuracion con id [{0}] para la encuesta [{1}]", new Object[]{conf.getIdConfiguracionEncuesta(), idEncuesta});
//            } catch (Exception e) {
//                log.log(Level.SEVERE, "Ocurrio un error al crear la configuracion. ", e);
//                mostrarMensaje("Error", "No se pudo agregar la pregunta a la encuesta.", true, false, false);
//                return;
//            }
//        }
//    }
//
//    public void limpiarModificacion() {
//        idEncuesta = null;
//        nombreEncuesta = null;
//        fechaFin = null;
//        fechaInicio = null;
//        plantilla = false;
//        colaborativa = false;
//        preguntasDisponibles = new ArrayList<>();
//    }
//
//    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
//        FacesMessage msg = null;
//        if (error) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
//        } else if (advertencia) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
//        } else if (informacion) {
//            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
//        }
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//    }
//}
