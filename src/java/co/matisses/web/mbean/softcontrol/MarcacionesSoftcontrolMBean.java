package co.matisses.web.mbean.softcontrol;

import co.matisses.persistence.softcontrol.facade.MarcacionesFacade;
import co.matisses.persistence.softcontrol.facade.MarcacionesFallidasFacade;
import co.matisses.web.dto.MarcacionesSoftcontrolDTO;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "marcacionesSoftcontrolMBean")
public class MarcacionesSoftcontrolMBean implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(MarcacionesSoftcontrolMBean.class.getSimpleName());
    private List<MarcacionesSoftcontrolDTO> marcaciones;
    private List<MarcacionesSoftcontrolDTO> fallidas;
    @EJB
    private MarcacionesFacade marcacionesFacade;
    @EJB
    private MarcacionesFallidasFacade marcacionesFallidasFacade;

    public MarcacionesSoftcontrolMBean() {
        marcaciones = new ArrayList<>();
        fallidas = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerDatosMarcaciones();
    }

    public List<MarcacionesSoftcontrolDTO> getMarcaciones() {
        return marcaciones;
    }

    public void setMarcaciones(List<MarcacionesSoftcontrolDTO> marcaciones) {
        this.marcaciones = marcaciones;
    }

    public List<MarcacionesSoftcontrolDTO> getFallidas() {
        return fallidas;
    }

    public void setFallidas(List<MarcacionesSoftcontrolDTO> fallidas) {
        this.fallidas = fallidas;
    }

    public void obtenerDatosMarcaciones() {
        obtenerMarcaciones();
        obtenerMarcacionesFallidas();
    }

    private void obtenerMarcaciones() {
        marcaciones = new ArrayList<>();

        List<Object[]> bearings = marcacionesFacade.obtenerRegistros(5);

        if (bearings != null && !bearings.isEmpty()) {
            for (Object[] o : bearings) {
                MarcacionesSoftcontrolDTO dto = new MarcacionesSoftcontrolDTO();

                dto.setCedulaEmpleado((String) o[0]);
                dto.setNombreEmpleado((String) o[1]);
                dto.setFechaHora((Date) o[2]);
                dto.setIp((String) o[3]);
                dto.setTipoMarcacion(((BigInteger) o[4]).intValue());

                marcaciones.add(dto);
            }
        }
    }

    private void obtenerMarcacionesFallidas() {
        fallidas = new ArrayList<>();

        List<Object[]> bearings = marcacionesFallidasFacade.obtenerRegistros();

        if (bearings != null && !bearings.isEmpty()) {
            for (Object[] o : bearings) {
                MarcacionesSoftcontrolDTO dto = new MarcacionesSoftcontrolDTO();

                dto.setCedulaEmpleado((String) o[0]);
                dto.setNombreEmpleado((String) o[1]);
                dto.setFechaHora((Date) o[2]);
                dto.setIp((String) o[3]);
                dto.setExcepcion((String) o[4]);

                if (dto.getExcepcion().contains("llegada tarde")) {
                    dto.setError(true);
                }

                fallidas.add(dto);
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
