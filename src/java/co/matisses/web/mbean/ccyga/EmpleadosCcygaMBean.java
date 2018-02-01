package co.matisses.web.mbean.ccyga;


import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.web.dto.EmpleadoCcygaDTO;
import co.matisses.web.dto.LaborCcygaDTO;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "empleadosCcygaMBean")
public class EmpleadosCcygaMBean implements Serializable {

    private static final Logger log = Logger.getLogger(EmpleadosCcygaMBean.class.getSimpleName());
    private static String rutaFotos;
    private List<EmpleadoCcygaDTO> empleados;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;
    @Inject
    private CcygaSessionMBean ccygaSession;
    @Inject
    private BaruApplicationMBean aplicacionBean;

    public EmpleadosCcygaMBean() {
        empleados = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        rutaFotos = aplicacionBean.obtenerValorPropiedad("baru.fotos.empleados");
        List<Empleado> entidades = empleadoFacade.consultarEmpleadosCcyga();
        empleados = new ArrayList<>();
        for (Empleado entidad : entidades) {
            EmpleadoCcygaDTO dto = new EmpleadoCcygaDTO();
            dto.setCedula(entidad.getOfficeExt());
            dto.setCodigoRevisado(entidad.getUcodigoRevisado());
            String primerNombre = entidad.getFirstName();
            String segundoNombre = entidad.getMiddleName();
            String apellido = entidad.getLastName();
            if (segundoNombre == null || segundoNombre.trim().isEmpty()) {
                //dto.setNombre(primerNombre + " " + apellido);
                dto.setNombre(primerNombre);
            } else {
                //dto.setNombre(primerNombre + " " + segundoNombre + " " + apellido);
                dto.setNombre(primerNombre + " " + segundoNombre);
            }
            empleados.add(dto);
        }
        Collections.sort(empleados);

        ccygaSession.limpiar();
    }

    public CcygaSessionMBean getCcygaSession() {
        return ccygaSession;
    }

    public void setCcygaSession(CcygaSessionMBean ccygaSession) {
        this.ccygaSession = ccygaSession;
    }

    public List<EmpleadoCcygaDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoCcygaDTO> empleados) {
        this.empleados = empleados;
    }

    private boolean fotoValida(String ruta) {
        try {
            URL obj = new URL(ruta);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al validar la imagen. ", e);
            return false;
        }
    }

    public String obtenerRutaFoto(String cedula) {
        String rutaFoto = String.format(rutaFotos, cedula);
        if (fotoValida(rutaFoto)) {
            return rutaFoto;
        } else {
            log.log(Level.WARNING, "No se encontro la foto para el empleado [{0}]", cedula);
            return String.format(rutaFotos, "pawn");
        }
    }

    public String seleccionarEmpleado() {
        log.info("Seleccionando empleado");
        String cedulaEmpleado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("cedula");
        String codigo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        String nombre = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nombre");
        log.log(Level.INFO, "Cedula [{0}], codigo [{1}]", new Object[]{cedulaEmpleado, codigo});
        if (cedulaEmpleado == null || codigo == null) {
            return null;
        }
        //consultar procesos abiertos empleado
        List<LaborCcyga> laboresAbiertas = laborFacade.obtenerLaboresAbiertasPorEmpleado(codigo);
        ccygaSession.setCodigoRevisado(codigo);
        ccygaSession.setNombreEmpleado(nombre);
        ccygaSession.agregarPaso("empleado");
        if (laboresAbiertas.isEmpty()) {
            //continua a la pagina de seleccion de proceso
            return "proceso";
        } else {
            boolean continuarConProducto = false;
            List<LaborCcygaDTO> labores = new ArrayList<>();
            for (LaborCcyga entidad : laboresAbiertas) {
                ProductoCcygaDTO productoDto = null;
                if (entidad.getIdProcesoProducto().getIdProducto() != null) {
                    continuarConProducto = true;
                    productoDto = new ProductoCcygaDTO();
                    productoDto.setIdProducto(entidad.getIdProcesoProducto().getIdProducto().getIdProducto());
                    productoDto.setReferencia(entidad.getIdProcesoProducto().getIdProducto().getReferencia());
                    productoDto.setRefProveedor(entidad.getIdProcesoProducto().getIdProducto().getRefProveedor());
                    productoDto.setRequerimiento(entidad.getIdProcesoProducto().getIdProducto().getRequerimiento());
                }

                LaborCcygaDTO dto = new LaborCcygaDTO();
                dto.setCodRevisado(entidad.getCodRevisado());
                dto.setFecha(entidad.getFecha());
                dto.setFin(entidad.getHoraFin());
                dto.setIdLabor(entidad.getIdLabor());
                dto.setInicio(entidad.getHoraInicio());
                dto.setProceso(new ProcesoCcygaDTO(entidad.getIdProcesoProducto().getIdProceso().getIdProceso(),
                        entidad.getIdProcesoProducto().getIdProceso().getNombre(),
                        entidad.getIdProcesoProducto().getIdProceso().getPermiteSimultaneos(),
                        entidad.getIdProcesoProducto().getIdProceso().getRequiereProducto()));
                dto.setProducto(productoDto);

                labores.add(dto);
            }
            ccygaSession.setLabores(labores);

            //continua a la pagina de cierre de proceso
            if (continuarConProducto) {
                return "labor";
            } else {
                return "laborsinproducto";
            }
        }
    }
}
