package co.matisses.web.mbean.next;

import co.matisses.web.dto.AdjuntoNextDTO;
import co.matisses.web.dto.EquipoNextDTO;
import co.matisses.web.dto.PrioridadSolicitudNextDTO;
import co.matisses.web.dto.TipoSolicitudNextDTO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "crearSolicitudNextMBean")
public class CrearSolicitudNextMBean implements Serializable {

    private static final Logger log = Logger.getLogger(CrearSolicitudNextMBean.class.getSimpleName());

    private String asuntoSolicitud;
    private String descripcionSolicitud;
    private Date fechaLimite;
    private TipoSolicitudNextDTO tipoSolicitud;
    private PrioridadSolicitudNextDTO prioridadSolicitud;
    private EquipoNextDTO equipoSolicitud;
    private Part archivoAdjunto;
    private List<TipoSolicitudNextDTO> tiposSolicitud;
    private List<PrioridadSolicitudNextDTO> prioridadesSolicitud;
    private List<EquipoNextDTO> equiposSolicitud;
    private List<AdjuntoNextDTO> adjuntosSolicitud;

    public CrearSolicitudNextMBean() {
        tiposSolicitud = new ArrayList<>();
        prioridadesSolicitud = new ArrayList<>();
        equiposSolicitud = new ArrayList<>();
        adjuntosSolicitud = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarTiposSolicitud();
        cargarPrioridadesSolicitud();
        cargarEquiposSolicitud();
    }

    private void cargarTiposSolicitud() {
        tiposSolicitud = new ArrayList<>();
        //TODO: cargar desde base de datos
        tiposSolicitud.add(new TipoSolicitudNextDTO(1, "Soporte SAP"));
        tiposSolicitud.add(new TipoSolicitudNextDTO(2, "Cambio Pagina Web"));
        tiposSolicitud.add(new TipoSolicitudNextDTO(3, "Soporte tecnico"));
        tiposSolicitud.add(new TipoSolicitudNextDTO(4, "Cambio imagen producto"));
        tiposSolicitud.add(new TipoSolicitudNextDTO(5, "Cambio modelo"));
        tiposSolicitud.add(new TipoSolicitudNextDTO(6, "Cambio apariencia producto"));

        Collections.sort(tiposSolicitud);
    }

    private void cargarPrioridadesSolicitud() {
        prioridadesSolicitud = new ArrayList<>();
        //TODO: cargar desde base de datos
        prioridadesSolicitud.add(new PrioridadSolicitudNextDTO(1, "Alta"));
        prioridadesSolicitud.add(new PrioridadSolicitudNextDTO(2, "Media"));
        prioridadesSolicitud.add(new PrioridadSolicitudNextDTO(3, "Baja"));
    }

    private void cargarEquiposSolicitud() {
        equiposSolicitud = new ArrayList<>();
        //TODO: cargar desde base de datos
        equiposSolicitud.add(new EquipoNextDTO(1, "Sistemas", "sistemas"));
        equiposSolicitud.add(new EquipoNextDTO(1, "Mercadeo", "mercadeo"));
        equiposSolicitud.add(new EquipoNextDTO(1, "Recursos Humanos", "rrhh"));
        equiposSolicitud.add(new EquipoNextDTO(1, "Finanzas", "finanzas"));
        equiposSolicitud.add(new EquipoNextDTO(1, "Servicio al Cliente", "servcliente"));

        Collections.sort(equiposSolicitud);
    }

    public List<TipoSolicitudNextDTO> getTiposSolicitud() {
        return tiposSolicitud;
    }

    public List<PrioridadSolicitudNextDTO> getPrioridadesSolicitud() {
        return prioridadesSolicitud;
    }

    public List<EquipoNextDTO> getEquiposSolicitud() {
        return equiposSolicitud;
    }

    public String getAsuntoSolicitud() {
        return asuntoSolicitud;
    }

    public void setAsuntoSolicitud(String asuntoSolicitud) {
        this.asuntoSolicitud = asuntoSolicitud;
    }

    public String getDescripcionSolicitud() {
        return descripcionSolicitud;
    }

    public void setDescripcionSolicitud(String descripcionSolicitud) {
        this.descripcionSolicitud = descripcionSolicitud;
    }

    public Part getArchivoAdjunto() {
        return archivoAdjunto;
    }

    public void setArchivoAdjunto(Part archivoAdjunto) {
        this.archivoAdjunto = archivoAdjunto;
    }

    public List<AdjuntoNextDTO> getAdjuntosSolicitud() {
        return adjuntosSolicitud;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getTipoSeleccionado() {
        if (tipoSolicitud != null) {
            return tipoSolicitud.getNombre();
        }
        return "Seleccione";
    }

    public String getPrioridadSeleccionada() {
        if (prioridadSolicitud != null) {
            return prioridadSolicitud.getNombre();
        }
        return "Seleccione";
    }

    public String getEquipoSeleccionado() {
        if (equipoSolicitud != null) {
            return equipoSolicitud.getNombre();
        }
        return "Seleccione";
    }

    public void seleccionarTipo() {
        String idTipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTipo");
        String nombreTipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombreTipo");
        tipoSolicitud = new TipoSolicitudNextDTO(Integer.valueOf(idTipo), nombreTipo);
        log.log(Level.INFO, "Se selecciono el tipo de solicitud [{0}]", tipoSolicitud);
    }

    public void seleccionarPrioridad() {
        String idPrioridad = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idPrioridad");
        String nombrePrioridad = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombrePrioridad");
        prioridadSolicitud = new PrioridadSolicitudNextDTO(Integer.valueOf(idPrioridad), nombrePrioridad);
        log.log(Level.INFO, "Se selecciono la prioridad [{0}]", prioridadSolicitud);
    }

    public void seleccionarEquipo() {
        String idEquipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idEquipo");
        String nombreEquipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombreEquipo");
        String nombreAD = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombreAD");
        equipoSolicitud = new EquipoNextDTO(Integer.valueOf(idEquipo), nombreEquipo, nombreAD);
        log.log(Level.INFO, "Se selecciono el equipo [{0}]", equipoSolicitud);
    }

    public void cambioAsunto() {
        log.log(Level.INFO, "Se ingreso el asunto [{0}]", asuntoSolicitud);
    }

    public void cambioDescripcion() {
        log.log(Level.INFO, "Se ingreso la descripcion [{0}]", descripcionSolicitud);
    }

    public void cambioFechaLimite() {
        log.log(Level.INFO, "Se ingreso la fecha limite [{0}]", fechaLimite);
    }

    public void guardarAdjunto() {
        if (archivoAdjunto == null) {
            return;
        }
        log.log(Level.INFO, "Recibiendo archivo {0}", archivoAdjunto.getSubmittedFileName());
        try (InputStream input = archivoAdjunto.getInputStream()) {
            File newFile = new File(System.getProperty("jboss.server.temp.dir"), archivoAdjunto.getSubmittedFileName());
            Files.copy(input, newFile.toPath());
//            adjuntosSolicitud.add(new AdjuntoNextDTO(null, FilenameUtils.getBaseName(newFile.getName()), FilenameUtils.getExtension(newFile.getName())));
            log.log(Level.INFO, "Numero de archivos adjuntos: {0}", adjuntosSolicitud.size());
        } catch (IOException e) {
            log.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }
}
