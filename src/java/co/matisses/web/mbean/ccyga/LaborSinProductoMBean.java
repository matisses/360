package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.entity.ProcesoProductoCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoProductoCcygaFacade;
import co.matisses.web.dto.LaborCcygaDTO;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "laborSinProductoCcygaMBean")
public class LaborSinProductoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(LaborSinProductoMBean.class.getSimpleName());
    private String nombreProceso;
    private LaborCcygaDTO laborCcygaDTO = null;
    @Inject
    private CcygaSessionMBean ccygaSession;
    @EJB
    private ProcesoProductoCcygaFacade procesoProductoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;

    public LaborSinProductoMBean() {
    }

    private void redirigir(String ruta) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    private void cargarProcesoSesion() {
        Integer idProceso = ccygaSession.getIdProcesoSeleccionado();
        nombreProceso = ccygaSession.getNombreProceso();
        if (idProceso == null) {
            //redirigir("/ccyga/proceso/index.xhtml");
        }
    }

    private void cargarLaborSesion() {
        List<LaborCcygaDTO> labores = ccygaSession.getLabores();
        if (labores != null && !labores.isEmpty()) {
            laborCcygaDTO = labores.get(0);
        } else {
            laborCcygaDTO = null;
        }
        if (laborCcygaDTO != null) {
            nombreProceso = laborCcygaDTO.getProceso().getNombre();
        }
    }

    @PostConstruct
    protected void initialize() {
        cargarLaborSesion();
        if (laborCcygaDTO == null) {
            cargarProcesoSesion();
        }
    }

    public CcygaSessionMBean getCcygaSession() {
        return ccygaSession;
    }

    public void setCcygaSession(CcygaSessionMBean ccygaSession) {
        this.ccygaSession = ccygaSession;
    }

    public String getNombreProceso() {
        return nombreProceso;
    }

    public void setNombreProceso(String nombreProceso) {
        this.nombreProceso = nombreProceso;
    }

    public LaborCcygaDTO getLaborCcygaDTO() {
        return laborCcygaDTO;
    }

    public void setLaborCcygaDTO(LaborCcygaDTO laborCcygaDTO) {
        this.laborCcygaDTO = laborCcygaDTO;
    }

    public String iniciarLabor() {
        log.log(Level.INFO, "Iniciando labor para el empleado [{0}] y proceso sin producto [{1}]",
                new Object[]{ccygaSession.getCodigoRevisado(), ccygaSession.getIdProcesoSeleccionado()});
        ProcesoProductoCcyga procProd = procesoProductoFacade.buscarPorProcesoyProducto(ccygaSession.getIdProcesoSeleccionado(), null);

        LaborCcyga entidad = new LaborCcyga();
        entidad.setCodRevisado(ccygaSession.getCodigoRevisado());
        entidad.setFecha(new Date());
        entidad.setHoraInicio(new Date());
        entidad.setHoraFin(null);
        entidad.setIdProcesoProducto(procProd);

        laborFacade.create(entidad);

        ccygaSession.limpiar();

        return "empleado";
    }

    public String finalizarLabor() {
        log.log(Level.INFO, "Finalizando labor para el empleado [{0}] y proceso sin producto [{1}]",
                new Object[]{laborCcygaDTO.getCodRevisado(), laborCcygaDTO.getProceso().getIdProceso()});

        ProcesoProductoCcyga procProd = procesoProductoFacade.buscarPorProcesoyProducto(laborCcygaDTO.getProceso().getIdProceso(), null);
        laborFacade.finalizarLabor(laborCcygaDTO.getIdLabor());

        ccygaSession.limpiar();

        return "empleado";
    }
}
