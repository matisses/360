//package co.matisses.web.mbean.encuestas;
//
//import co.matisses.persistence.web.entity.OpcionRespuestaPregunta;
//import co.matisses.persistence.web.entity.PreguntaEncuesta;
//import co.matisses.persistence.web.entity.TipoRespuesta;
//import co.matisses.persistence.web.facade.OpcionRespuestaPreguntaFacade;
//import co.matisses.persistence.web.facade.PreguntaEncuestaFacade;
//import co.matisses.persistence.web.facade.TipoRespuestaFacade;
//import co.matisses.web.dto.OpcionRespuestaPreguntaDTO;
//import co.matisses.web.dto.PreguntaEncuestaDTO;
//import co.matisses.web.dto.TipoRespuestaDTO;
//import co.matisses.web.mbean.BaruApplicationMBean;
//import co.matisses.web.mbean.UserSessionInfoMBean;
//import java.io.Serializable;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
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
//@Named(value = "maestroPreguntasEncuestaMBean")
//public class MaestroPreguntasEncuestaMBean implements Serializable {
//
//    @Inject
//    private UserSessionInfoMBean sessionMBean;
//    @Inject
//    private BaruApplicationMBean applicationMBean;
//    private static final Logger log = Logger.getLogger(MaestroPreguntasEncuestaMBean.class.getSimpleName());
//    private Integer idTipoRespuesta;
//    private Integer idPreguntaEncuesta;
//    private Integer idOpcionRespuesta;
//    private String pregunta;
//    private String opcionRespuesta;
//    private boolean justificable = false;
//    private List<TipoRespuestaDTO> tiposRespuesta;
//    private List<PreguntaEncuestaDTO> preguntas;
//    private List<OpcionRespuestaPreguntaDTO> opcionesRespuesta;
//    @EJB
//    private TipoRespuestaFacade tipoRespuestaFacade;
//    @EJB
//    private PreguntaEncuestaFacade preguntaEncuestaFacade;
//    @EJB
//    private OpcionRespuestaPreguntaFacade opcionRespuestaPreguntaFacade;
//
//    public MaestroPreguntasEncuestaMBean() {
//        tiposRespuesta = new ArrayList<>();
//        preguntas = new ArrayList<>();
//        opcionesRespuesta = new ArrayList<>();
//    }
//
//    @PostConstruct
//    protected void initialize() {
//        obtenerTiposRespuesta();
//        obtenerPreguntas();
//    }
//
//    public Integer getIdTipoRespuesta() {
//        return idTipoRespuesta;
//    }
//
//    public String getIdTipoRespuestaSeleccionado() {
//        if (idTipoRespuesta != null && idTipoRespuesta != 0) {
//            for (TipoRespuestaDTO t : tiposRespuesta) {
//                if (t.getIdTipoRespuesta().equals(idTipoRespuesta)) {
//                    return t.getTipoRespuesta();
//                }
//            }
//        }
//
//        return "Seleccione";
//    }
//
//    public Integer getIdPreguntaEncuesta() {
//        return idPreguntaEncuesta;
//    }
//
//    public void setIdPreguntaEncuesta(Integer idPreguntaEncuesta) {
//        this.idPreguntaEncuesta = idPreguntaEncuesta;
//    }
//
//    public Integer getIdOpcionRespuesta() {
//        return idOpcionRespuesta;
//    }
//
//    public void setIdOpcionRespuesta(Integer idOpcionRespuesta) {
//        this.idOpcionRespuesta = idOpcionRespuesta;
//    }
//
//    public String getPregunta() {
//        return pregunta;
//    }
//
//    public void setPregunta(String pregunta) {
//        this.pregunta = pregunta;
//    }
//
//    public String getOpcionRespuesta() {
//        return opcionRespuesta;
//    }
//
//    public void setOpcionRespuesta(String opcionRespuesta) {
//        this.opcionRespuesta = opcionRespuesta;
//    }
//
//    public boolean isJustificable() {
//        return justificable;
//    }
//
//    public void setJustificable(boolean justificable) {
//        this.justificable = justificable;
//    }
//
//    public List<TipoRespuestaDTO> getTiposRespuesta() {
//        return tiposRespuesta;
//    }
//
//    public void setTiposRespuesta(List<TipoRespuestaDTO> tiposRespuesta) {
//        this.tiposRespuesta = tiposRespuesta;
//    }
//
//    public List<PreguntaEncuestaDTO> getPreguntas() {
//        return preguntas;
//    }
//
//    public void setPreguntas(List<PreguntaEncuestaDTO> preguntas) {
//        this.preguntas = preguntas;
//    }
//
//    public List<OpcionRespuestaPreguntaDTO> getOpcionesRespuesta() {
//        return opcionesRespuesta;
//    }
//
//    public void setOpcionesRespuesta(List<OpcionRespuestaPreguntaDTO> opcionesRespuesta) {
//        this.opcionesRespuesta = opcionesRespuesta;
//    }
//
//    private void obtenerTiposRespuesta() {
//        tiposRespuesta = new ArrayList<>();
//
//        List<TipoRespuesta> typeAnswers = tipoRespuestaFacade.findAll();
//
//        if (typeAnswers != null && !typeAnswers.isEmpty()) {
//            for (TipoRespuesta t : typeAnswers) {
//                tiposRespuesta.add(new TipoRespuestaDTO(t.getIdTipoRespuesta(), t.getTipoRespuesta()));
//            }
//
//            Collections.sort(tiposRespuesta, new Comparator<TipoRespuestaDTO>() {
//                @Override
//                public int compare(TipoRespuestaDTO t, TipoRespuestaDTO t1) {
//                    return t.getTipoRespuesta().compareTo(t1.getTipoRespuesta());
//                }
//            });
//        }
//    }
//
//    private void obtenerPreguntas() {
//        preguntas = new ArrayList<>();
//        List<PreguntaEncuesta> questions = preguntaEncuestaFacade.findAll();
//
//        if (questions != null && !questions.isEmpty()) {
//            log.log(Level.INFO, "Se encontraron {0}, preguntas", questions.size());
//
//            for (PreguntaEncuesta p : questions) {
//                preguntas.add(new PreguntaEncuestaDTO(p.getIdPregunta(), p.getPregunta(),
//                        p.getIdTipoRespuesta() != null ? new TipoRespuestaDTO(p.getIdTipoRespuesta().getIdTipoRespuesta(), p.getIdTipoRespuesta().getTipoRespuesta()) : null));
//            }
//        }
//    }
//
//    public void seleccionarTipoRespuesta() {
//        idTipoRespuesta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipoRespuesta"));
//
//        log.log(Level.INFO, "Se selecciono el tipo respuesta con id [{0}]", idTipoRespuesta);
//    }
//
//    public void crearPregunta() {
//        if (pregunta == null || pregunta.isEmpty()) {
//            mostrarMensaje("Error", "Ingrese una pregunta para poder continuar.", true, false, false);
//            log.log(Level.SEVERE, "Ingrese una pregunta para poder continuar");
//            return;
//        }
//        if (idTipoRespuesta == null || idTipoRespuesta == 0) {
//            mostrarMensaje("Error", "Seleccione un tipo de respuesta de los disponibles oara poder continuar.", true, false, false);
//            log.log(Level.SEVERE, "Seleccione un tipo de respuesta de los disponibles oara poder continuar");
//            return;
//        }
//
//        if (!pregunta.substring(0, 1).contains("¿")) {
//            pregunta = "¿" + pregunta;
//        }
//        if (!pregunta.substring(pregunta.length() - 1, pregunta.length()).contains("?")) {
//            pregunta = pregunta + "?";
//        }
//
//        PreguntaEncuesta preguntaEncuesta = new PreguntaEncuesta();
//
//        preguntaEncuesta.setIdTipoRespuesta(new TipoRespuesta(idTipoRespuesta));
//        preguntaEncuesta.setPregunta(pregunta);
//
//        try {
//            preguntaEncuestaFacade.create(preguntaEncuesta);
//            log.log(Level.INFO, "Se creo la pregunta con id [{0}]", preguntaEncuesta.getIdPregunta());
//            idPreguntaEncuesta = preguntaEncuesta.getIdPregunta();
//        } catch (Exception e) {
//            mostrarMensaje("Error", "No se pudo crear la pregunta.", true, false, false);
//            log.log(Level.SEVERE, "Ocurrio un error al crear la pregunta", e);
//            return;
//        }
//    }
//
//    public void seleccionarPregunta() {
//        Integer idPregunta = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPregunta"));
//
//        if (idPregunta.equals(idPreguntaEncuesta)) {
//            limpiarDatosPregunta();
//        } else {
//            for (PreguntaEncuestaDTO p : preguntas) {
//                if (p.getIdPregunta().equals(idPregunta)) {
//                    idPreguntaEncuesta = p.getIdPregunta();
//                    pregunta = p.getPregunta();
//                    idTipoRespuesta = p.getIdTipoRespuesta().getIdTipoRespuesta();
//                    obtenerOpcionesRespuesta();
//
//                    log.log(Level.INFO, "Se selecciono la pregunta con id [{0}]", idPreguntaEncuesta);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void modificarPregunta() {
//        if (idPreguntaEncuesta != null && idPreguntaEncuesta != 0) {
//            if (pregunta == null || pregunta.isEmpty()) {
//                mostrarMensaje("Error", "Ingrese una pregunta para poder continuar.", true, false, false);
//                log.log(Level.SEVERE, "Ingrese una pregunta para poder continuar");
//                return;
//            }
//            if (idTipoRespuesta == null || idTipoRespuesta == 0) {
//                mostrarMensaje("Error", "Seleccione un tipo de respuesta de los disponibles oara poder continuar.", true, false, false);
//                log.log(Level.SEVERE, "Seleccione un tipo de respuesta de los disponibles oara poder continuar");
//                return;
//            }
//
//            if (!pregunta.substring(0, 1).contains("¿")) {
//                pregunta = "¿" + pregunta;
//            }
//            if (!pregunta.substring(pregunta.length() - 1, pregunta.length()).contains("?")) {
//                pregunta = pregunta + "?";
//            }
//
//            PreguntaEncuesta preguntaEncuesta = preguntaEncuestaFacade.find(idPreguntaEncuesta);
//
//            preguntaEncuesta.setIdTipoRespuesta(new TipoRespuesta(idTipoRespuesta));
//            preguntaEncuesta.setPregunta(pregunta);
//
//            try {
//                preguntaEncuestaFacade.edit(preguntaEncuesta);
//                log.log(Level.INFO, "Se modifico la pregunta con id [{0}]", preguntaEncuesta.getIdPregunta());
//
//                for (PreguntaEncuestaDTO e : preguntas) {
//                    if (e.getIdPregunta().equals(idPreguntaEncuesta)) {
//                        e.setIdTipoRespuesta(new TipoRespuestaDTO(idTipoRespuesta, null));
//                        e.setPregunta(pregunta);
//
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                mostrarMensaje("Error", "No se pudo modificar la pregunta.", true, false, false);
//                log.log(Level.SEVERE, "Ocurrio un error al modificar la pregunta", e);
//                return;
//            }
//        }
//    }
//
//    public void obtenerOpcionesRespuesta() {
//        opcionesRespuesta = new ArrayList<>();
//
//        List<OpcionRespuestaPregunta> options = opcionRespuestaPreguntaFacade.obtenerOpcionesRespuestaPregunta(idPreguntaEncuesta);
//
//        if (options != null && !options.isEmpty()) {
//            for (OpcionRespuestaPregunta o : options) {
//                opcionesRespuesta.add(new OpcionRespuestaPreguntaDTO(o.getIdOpcionRespuestaPregunta(), o.getIdPregunta().getIdPregunta(), o.getOrden(), o.getOpcionRespuesta(), o.getJustificable()));
//            }
//
//            Collections.sort(opcionesRespuesta, new Comparator<OpcionRespuestaPreguntaDTO>() {
//                @Override
//                public int compare(OpcionRespuestaPreguntaDTO t, OpcionRespuestaPreguntaDTO t1) {
//                    return t.getOrden().compareTo(t1.getOrden());
//                }
//            });
//        }
//    }
//
//    public void seleccionarOpcionJustificable() {
//        if (justificable) {
//            justificable = false;
//            log.log(Level.INFO, "Se marco la opcion de respuesta como no justificable");
//        } else {
//            justificable = true;
//            log.log(Level.INFO, "Se marco la opcion de respuesta como justificable");
//        }
//    }
//
//    public void crearOpcionRespuesta() {
//        if (opcionRespuesta == null || opcionRespuesta.isEmpty()) {
//            mostrarMensaje("Error", "Ingrese una opción de respuesta para la pregunta.", true, false, false);
//            log.log(Level.SEVERE, "Ingrese una opcion de respuesta para la pregunta");
//            return;
//        }
//
//        OpcionRespuestaPregunta opcion = new OpcionRespuestaPregunta();
//
//        opcion.setIdPregunta(new PreguntaEncuesta(idPreguntaEncuesta));
//        opcion.setJustificable(justificable);
//        opcion.setOpcionRespuesta(opcionRespuesta);
//        opcion.setOrden(opcionesRespuesta.size() + 1);
//
//        try {
//            opcionRespuestaPreguntaFacade.create(opcion);
//            log.log(Level.INFO, "Se creo la opcion de respuesta con id [{0}]", opcion.getIdOpcionRespuestaPregunta());
//            opcionesRespuesta.add(new OpcionRespuestaPreguntaDTO(opcion.getIdOpcionRespuestaPregunta(), opcion.getIdPregunta().getIdPregunta(), opcion.getOrden(),
//                    opcion.getOpcionRespuesta(), opcion.getJustificable()));
//        } catch (Exception e) {
//            log.log(Level.SEVERE, "Ocurrio un error al agregar la opcion de respuesta. ", e);
//            mostrarMensaje("Error", "No se pudo agregar la opción de respuesta.", true, false, false);
//            return;
//        }
//    }
//
//    public void seleccionarOpcionRespuesta() {
//        Integer idOpcion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idOpcion"));
//
//        if (idOpcion.equals(idOpcionRespuesta)) {
//            limpiarDatosOpcionRespuesta();
//        } else {
//            for (OpcionRespuestaPreguntaDTO o : opcionesRespuesta) {
//                if (o.getIdOpcionRespuestaPregunta().equals(idOpcion)) {
//                    opcionRespuesta = o.getOpcionRespuesta();
//                    justificable = o.isJustificable();
//                    idOpcionRespuesta = o.getIdOpcionRespuestaPregunta();
//
//                    log.log(Level.INFO, "Se selecciono la opcion de respuesta con id [{0}]", idOpcionRespuesta);
//                    break;
//                }
//            }
//        }
//    }
//
//    public void modificarOpcionRespuesta() {
//        if (opcionRespuesta == null || opcionRespuesta.isEmpty()) {
//            mostrarMensaje("Error", "Ingrese una opción de respuesta para la pregunta.", true, false, false);
//            log.log(Level.SEVERE, "Ingrese una opcion de respuesta para la pregunta");
//            return;
//        }
//
//        OpcionRespuestaPregunta opcion = opcionRespuestaPreguntaFacade.find(idOpcionRespuesta);
//
//        if (opcion != null && opcion.getIdOpcionRespuestaPregunta() != null && opcion.getIdOpcionRespuestaPregunta() != 0) {
//            opcion.setJustificable(justificable);
//            opcion.setOpcionRespuesta(opcionRespuesta);
//
//            try {
//                opcionRespuestaPreguntaFacade.edit(opcion);
//                log.log(Level.INFO, "Se modifico la opcion de respuesta con id [{0}]", opcion.getIdOpcionRespuestaPregunta());
//
//                for (OpcionRespuestaPreguntaDTO o : opcionesRespuesta) {
//                    if (o.getIdOpcionRespuestaPregunta().equals(idOpcionRespuesta)) {
//                        o.setJustificable(justificable);
//                        o.setOpcionRespuesta(opcionRespuesta);
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                log.log(Level.SEVERE, "Ocurrio un error al modificar la opcion de respuesta. ", e);
//                mostrarMensaje("Error", "No se pudo modificar la opción de respuesta.", true, false, false);
//                return;
//            }
//        }
//    }
//
//    public void subirPrioridadRespuesta() {
//        if (idOpcionRespuesta != null && idOpcionRespuesta != 0) {
//            for (OpcionRespuestaPreguntaDTO o : opcionesRespuesta) {
//                if (o.getIdOpcionRespuestaPregunta().equals(idOpcionRespuesta) && o.getOrden() > 1) {
//                    o.setOrden(o.getOrden() - 1);
//
//                    int pos = opcionesRespuesta.indexOf(o);
//                    if (pos < 0) {
//                        return;
//                    }
//
//                    OpcionRespuestaPregunta opcionSubir = opcionRespuestaPreguntaFacade.find(o.getIdOpcionRespuestaPregunta());
//                    OpcionRespuestaPregunta opcionBajar = opcionRespuestaPreguntaFacade.find(opcionesRespuesta.get(pos - 1).getIdOpcionRespuestaPregunta());
//
//                    if ((opcionSubir != null && opcionSubir.getIdOpcionRespuestaPregunta() != null && opcionSubir.getIdOpcionRespuestaPregunta() != 0)
//                            && (opcionBajar != null && opcionBajar.getIdOpcionRespuestaPregunta() != null && opcionBajar.getIdOpcionRespuestaPregunta() != 0)) {
//                        /*Se sube la prioridad de la opcion a subir*/
//                        try {
//                            opcionSubir.setOrden(o.getOrden());
//
//                            opcionRespuestaPreguntaFacade.edit(opcionSubir);
//                            log.log(Level.INFO, "Se le subio la prioridad a la opcion de respuesta con id [{0}]", opcionSubir.getIdOpcionRespuestaPregunta());
//                        } catch (Exception e) {
//                            log.log(Level.SEVERE, "Ocurrio un problema al subir la opcion de respuesta. ", e);
//                            mostrarMensaje("Error", "Ocurrió un problema al subir la opción de respuesta.", true, false, false);
//                            return;
//                        }
//
//                        /*Se baja de prioridad la opcion delante de la que se sube*/
//                        try {
//                            opcionesRespuesta.get(pos - 1).setOrden(opcionesRespuesta.get(pos - 1).getOrden() + 1);
//                            opcionBajar.setOrden(opcionBajar.getOrden() + 1);
//
//                            opcionRespuestaPreguntaFacade.edit(opcionBajar);
//                            log.log(Level.INFO, "Se le bajo la prioridad a la opcion de respuesta con id [{0}]", opcionBajar.getIdOpcionRespuestaPregunta());
//                        } catch (Exception e) {
//                            log.log(Level.SEVERE, "Ocurrio un problema al bajar la opcion de respuesta. ", e);
//                            mostrarMensaje("Error", "Ocurrió un problema al bajar la opción de respuesta.", true, false, false);
//                            return;
//                        }
//
//                        /*Se mueven los datos de la lista dependiendo del orden*/
////                        opcionesRespuesta.set(pos - 1, o);
////                        opcionesRespuesta.set(pos, opcionesRespuesta.get(pos));
//                        Collections.sort(opcionesRespuesta, new Comparator<OpcionRespuestaPreguntaDTO>() {
//                            @Override
//                            public int compare(OpcionRespuestaPreguntaDTO t, OpcionRespuestaPreguntaDTO t1) {
//                                return t.getOrden().compareTo(t1.getOrden());
//                            }
//                        });
//                    } else {
//                        mostrarMensaje("Error", "No se encontraron los datos de la opción de respuesta seleccionada.", true, false, false);
//                        log.log(Level.SEVERE, "No se encontraron los datos de la opcion de respuesta seleccionada");
//                        return;
//                    }
//                    break;
//                }
//            }
//        }
//    }
//
//    public void bajarPrioridadRespuesta() {
//        if (idOpcionRespuesta != null && idOpcionRespuesta != 0) {
//            for (OpcionRespuestaPreguntaDTO o : opcionesRespuesta) {
//                if (o.getIdOpcionRespuestaPregunta().equals(idOpcionRespuesta) && o.getOrden() < opcionesRespuesta.size()) {
//                    o.setOrden(o.getOrden() + 1);
//
//                    int pos = opcionesRespuesta.indexOf(o);
//                    if (pos < 0) {
//                        return;
//                    }
//
//                    OpcionRespuestaPregunta opcionBajar = opcionRespuestaPreguntaFacade.find(o.getIdOpcionRespuestaPregunta());
//                    OpcionRespuestaPregunta opcionSubir = opcionRespuestaPreguntaFacade.find(opcionesRespuesta.get(pos + 1).getIdOpcionRespuestaPregunta());
//
//                    if ((opcionBajar != null && opcionBajar.getIdOpcionRespuestaPregunta() != null && opcionBajar.getIdOpcionRespuestaPregunta() != 0)
//                            && (opcionSubir != null && opcionSubir.getIdOpcionRespuestaPregunta() != null && opcionSubir.getIdOpcionRespuestaPregunta() != 0)) {
//                        /*Se sube la prioridad de la opcion a bajo*/
//                        try {
//                            opcionBajar.setOrden(o.getOrden());
//
//                            opcionRespuestaPreguntaFacade.edit(opcionBajar);
//                            log.log(Level.INFO, "Se le bajo la prioridad a la opcion de respuesta con id [{0}]", opcionBajar.getIdOpcionRespuestaPregunta());
//                        } catch (Exception e) {
//                            log.log(Level.SEVERE, "Ocurrio un problema al bajar la opcion de respuesta. ", e);
//                            mostrarMensaje("Error", "Ocurrió un problema al bajar la opción de respuesta.", true, false, false);
//                            return;
//                        }
//
//                        /*Se sube de prioridad la opcion delante de la que se sube*/
//                        try {
//                            opcionesRespuesta.get(pos + 1).setOrden(opcionesRespuesta.get(pos + 1).getOrden() - 1);
//                            opcionSubir.setOrden(opcionSubir.getOrden() - 1);
//
//                            opcionRespuestaPreguntaFacade.edit(opcionSubir);
//                            log.log(Level.INFO, "Se le subio la prioridad a la opcion de respuesta con id [{0}]", opcionSubir.getIdOpcionRespuestaPregunta());
//                        } catch (Exception e) {
//                            log.log(Level.SEVERE, "Ocurrio un problema al subir la opcion de respuesta. ", e);
//                            mostrarMensaje("Error", "Ocurrió un problema al subir la opción de respuesta.", true, false, false);
//                            return;
//                        }
//
//                        /*Se mueven los datos de la lista dependiendo del orden*/
////                        opcionesRespuesta.set(pos - 1, o);
////                        opcionesRespuesta.set(pos, opcionesRespuesta.get(pos));
//                        Collections.sort(opcionesRespuesta, new Comparator<OpcionRespuestaPreguntaDTO>() {
//                            @Override
//                            public int compare(OpcionRespuestaPreguntaDTO t, OpcionRespuestaPreguntaDTO t1) {
//                                return t.getOrden().compareTo(t1.getOrden());
//                            }
//                        });
//                    } else {
//                        mostrarMensaje("Error", "No se encontraron los datos de la opción de respuesta seleccionada.", true, false, false);
//                        log.log(Level.SEVERE, "No se encontraron los datos de la opcion de respuesta seleccionada");
//                        return;
//                    }
//                    break;
//                }
//            }
//        }
//    }
//
//    public void limpiarDatosPregunta() {
//        pregunta = null;
//        idTipoRespuesta = null;
//        idPreguntaEncuesta = null;
//        opcionesRespuesta = new ArrayList<>();
//    }
//
//    public void limpiarDatosOpcionRespuesta() {
//        idOpcionRespuesta = null;
//        opcionRespuesta = null;
//        justificable = false;
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
