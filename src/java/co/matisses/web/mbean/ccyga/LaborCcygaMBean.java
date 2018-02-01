package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.entity.ProcesoProductoCcyga;
import co.matisses.persistence.web.entity.ProductoCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoProductoCcygaFacade;
import co.matisses.persistence.web.facade.ProductoCcygaFacade;
import co.matisses.web.dto.LaborCcygaDTO;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "laborCcygaMBean")
public class LaborCcygaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(LaborCcygaMBean.class.getSimpleName());
    private ProductoCcygaDTO productoCcygaDTO;
    private List<LaborCcygaDTO> labores = null;
    private ProcesoCcygaDTO procesoCcygaDTO;
    private Integer nroLaborVisible = 0;
    @Inject
    private CcygaSessionMBean ccygaSession;
    @EJB
    private ProductoCcygaFacade productoFacade;
    @EJB
    private ProcesoProductoCcygaFacade procesoProductoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;
    @EJB
    private ProcesoCcygaFacade procesoFacade;

    public LaborCcygaMBean() {
        labores = new ArrayList<>();
    }

    private void redirigir(String ruta) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + ruta);
        } catch (Exception e) {
        }
    }

    private void cargarProductoSesion() {
        Integer idProducto = ccygaSession.getIdProductoSeleccionado();
        if (idProducto == null) {
            redirigir("/ccyga/empleado/index.xhtml");
            return;
        }

        ProductoCcyga entidad = productoFacade.find(idProducto);
        productoCcygaDTO = new ProductoCcygaDTO();
        productoCcygaDTO.setDescripcion(entidad.getDescripcion());
        productoCcygaDTO.setDocumento(entidad.getDocumento());
        productoCcygaDTO.setIdProducto(entidad.getIdProducto());
        productoCcygaDTO.setRefProveedor(entidad.getRefProveedor());
        productoCcygaDTO.setReferencia(entidad.getReferencia());
        productoCcygaDTO.setRequerimiento(entidad.getRequerimiento());

        ProcesoCcyga entidadProceso = procesoFacade.find(ccygaSession.getIdProcesoSeleccionado());
        procesoCcygaDTO = new ProcesoCcygaDTO(entidadProceso.getIdProceso(), entidadProceso.getNombre(), entidadProceso.getPermiteSimultaneos(), entidadProceso.getRequiereProducto());
    }

    private void cargarLaboresSesion() {
        labores = ccygaSession.getLabores();
        if (labores != null && !labores.isEmpty()) {
            nroLaborVisible = 0;
            //ccygaSession.setLabor(null);
            productoCcygaDTO = getLaborCcygaDTO().getProducto();
            procesoCcygaDTO = getLaborCcygaDTO().getProceso();
        }
    }

    @PostConstruct
    protected void initialize() {
        cargarLaboresSesion();
        if (productoCcygaDTO != null && ccygaSession.getIdProductoSeleccionado() != null && !productoCcygaDTO.getIdProducto().equals(ccygaSession.getIdProductoSeleccionado())) {
            log.log(Level.INFO, "Se cargo la labor [{0}] con idProducto [{1}] y producto seleccionado sesion [{2}]",
                    new Object[]{getLaborCcygaDTO().getIdLabor(), productoCcygaDTO.getIdProducto(), ccygaSession.getIdProductoSeleccionado()});
            labores = null;
            productoCcygaDTO = null;
            procesoCcygaDTO = null;
        }
        if (labores == null || labores.isEmpty()) {
            cargarProductoSesion();
        }
    }

    public CcygaSessionMBean getCcygaSession() {
        return ccygaSession;
    }

    public void setCcygaSession(CcygaSessionMBean ccygaSession) {
        this.ccygaSession = ccygaSession;
    }

    public ProductoCcygaDTO getProductoCcygaDTO() {
        return productoCcygaDTO;
    }

    public void setProductoCcygaDTO(ProductoCcygaDTO productoCcygaDTO) {
        this.productoCcygaDTO = productoCcygaDTO;
    }

    public LaborCcygaDTO getLaborCcygaDTO() {
        if (labores == null || labores.isEmpty()) {
            return null;
        }
        return labores.get(nroLaborVisible);
    }

    public void setLaborCcygaDTO(LaborCcygaDTO laborCcygaDTO) {
        if (labores.size() <= nroLaborVisible) {
            this.labores.add(laborCcygaDTO);
        } else {
            this.labores.set(nroLaborVisible, laborCcygaDTO);
        }
    }

    public ProcesoCcygaDTO getProcesoCcygaDTO() {
        return procesoCcygaDTO;
    }

    public void setProcesoCcygaDTO(ProcesoCcygaDTO procesoCcygaDTO) {
        this.procesoCcygaDTO = procesoCcygaDTO;
    }

    public String iniciarLabor() {
        log.log(Level.INFO, "Iniciando labor para el empleado [{0}], producto [{1}] y proceso [{2}]",
                new Object[]{ccygaSession.getCodigoRevisado(), ccygaSession.getIdProductoSeleccionado(), ccygaSession.getIdProcesoSeleccionado()});
        ProcesoProductoCcyga procProd = procesoProductoFacade.buscarPorProcesoyProducto(ccygaSession.getIdProcesoSeleccionado(), ccygaSession.getIdProductoSeleccionado());

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
        log.log(Level.INFO, "Finalizando labor para el empleado [{0}], producto [{1}] y proceso [{2}]",
                new Object[]{getLaborCcygaDTO().getCodRevisado(), getLaborCcygaDTO().getProducto().getIdProducto(), getLaborCcygaDTO().getProceso().getIdProceso()});

        ProcesoProductoCcyga procProd = procesoProductoFacade.buscarPorProcesoyProducto(getLaborCcygaDTO().getProceso().getIdProceso(), getLaborCcygaDTO().getProducto().getIdProducto());
        laborFacade.finalizarLabor(getLaborCcygaDTO().getIdLabor());

        ccygaSession.limpiar();

        return "empleado";
    }

    public String agregarSimultaneo() {
        ccygaSession.setCodigoRevisado(getLaborCcygaDTO().getCodRevisado());
        ccygaSession.setIdProcesoSeleccionado(getLaborCcygaDTO().getProceso().getIdProceso());
        ccygaSession.agregarPaso("labor");
        return "producto";
    }

    public String obtenerEstiloBotonSiguiente() {
        if (labores == null || labores.isEmpty() || nroLaborVisible >= labores.size() - 1) {
            return "ccyga-boton-labor-disabled";
        }
        return null;
    }

    public String obtenerEstiloBotonAnterior() {
        if (labores == null || labores.isEmpty() || nroLaborVisible <= 0) {
            return "ccyga-boton-labor-disabled";
        }
        return null;
    }

    public void mostrarLaborAnterior() {
        if (obtenerEstiloBotonAnterior() != null) {
            return;
        }
        if (nroLaborVisible <= 0) {
            nroLaborVisible = 0;
        } else {
            nroLaborVisible = nroLaborVisible - 1;
        }
        productoCcygaDTO = getLaborCcygaDTO().getProducto();
        procesoCcygaDTO = getLaborCcygaDTO().getProceso();
    }

    public void mostrarLaborSiguiente() {
        if (obtenerEstiloBotonSiguiente() != null) {
            return;
        }
        int max = labores.size();
        if (nroLaborVisible >= max) {
            nroLaborVisible = max;
        } else if (nroLaborVisible < 0) {
            nroLaborVisible = 0;
        } else {
            nroLaborVisible = nroLaborVisible + 1;
        }
        productoCcygaDTO = getLaborCcygaDTO().getProducto();
        procesoCcygaDTO = getLaborCcygaDTO().getProceso();
    }

    public boolean mostrarBotonesNavegacion() {
        if (labores != null && labores.size() > 1) {
            return true;
        }
        return false;
    }

    public String obtenerTextoPaginaActual() {
        if (labores == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(nroLaborVisible + 1);
        sb.append(" de ");
        sb.append(labores.size());
        return sb.toString();
    }
}
