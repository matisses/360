package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.web.dto.ProcesoCcygaDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "procesoCcygaMBean")
public class ProcesoCcygaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ProcesoCcygaMBean.class.getSimpleName());
    private List<ProcesoCcygaDTO> procesos;
    @EJB
    private ProcesoCcygaFacade procesoFacade;
    @Inject
    private CcygaSessionMBean ccygaSession;

    public ProcesoCcygaMBean() {
        procesos = new ArrayList<>();
    }

    private void redirigir(String ruta) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    private void obtenerCodigoRevisado() {
        String codigoRevisado = ccygaSession.getCodigoRevisado();
        if (codigoRevisado == null) {
            log.severe("No se encontro un empleado seleccionado, redirigiendo a pagina de empleados");
            redirigir("/ccyga/empleado/index.xhtml");
        }
    }

    @PostConstruct
    protected void initialize() {
        obtenerCodigoRevisado();
        procesos = new ArrayList<>();
        List<ProcesoCcyga> entidades = procesoFacade.findAll();
        for (ProcesoCcyga entidad : entidades) {
            procesos.add(new ProcesoCcygaDTO(entidad.getIdProceso(), entidad.getNombre()));
        }
        Collections.sort(procesos);
    }

    public List<ProcesoCcygaDTO> getProcesos() {
        return procesos;
    }

    public void setProcesos(List<ProcesoCcygaDTO> procesos) {
        this.procesos = procesos;
    }

    public CcygaSessionMBean getCcygaSession() {
        return ccygaSession;
    }

    public void setCcygaSession(CcygaSessionMBean ccygaSession) {
        this.ccygaSession = ccygaSession;
    }

    public String seleccionarProceso() {
        String codigoProceso = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codProceso");
        String nombreProceso = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nomProceso");
        log.log(Level.INFO, "Proceso [{0}] seleccionado", codigoProceso);
        ProcesoCcyga entidadProceso = procesoFacade.find(new Integer(codigoProceso));
        if (entidadProceso.getRequiereProducto()) {
            ccygaSession.setIdProcesoSeleccionado(new Integer(codigoProceso));
            ccygaSession.setNombreProceso(nombreProceso);
            ccygaSession.agregarPaso("proceso");
            return "producto";
        } else {
            ccygaSession.setIdProcesoSeleccionado(new Integer(codigoProceso));
            ccygaSession.setNombreProceso(nombreProceso);
            ccygaSession.agregarPaso("proceso");
            return "laborsinproducto";
        }
    }
}
