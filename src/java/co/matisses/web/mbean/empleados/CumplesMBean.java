package co.matisses.web.mbean.empleados;

import co.matisses.persistence.sap.entity.CentroCostosEmpleados;
import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.CentroCostosEmpleadosFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
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
@Named(value = "cumplesMBean")
public class CumplesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger CONSOLE = Logger.getLogger(CumplesMBean.class.getSimpleName());
    private Integer mes;
    private List<EmpleadoDTO> empleados;
    private List<CentroCostosEmpleados> centrosCosto;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private CentroCostosEmpleadosFacade centroCostosEmpleadosFacade;

    public CumplesMBean() {
        empleados = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        GregorianCalendar calendar = new GregorianCalendar();
        mes = calendar.get(Calendar.MONTH) + 1;
        obtenerEmpleadosMes();
        cargarCentrosCosto();
    }

    public List<EmpleadoDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoDTO> empleados) {
        this.empleados = empleados;
    }

    public String getNombreMes() {
        if (null != mes) {
            switch (mes) {
                case 1:
                    return "ENERO";
                case 2:
                    return "FEBRERO";
                case 3:
                    return "MARZO";
                case 4:
                    return "ABRIL";
                case 5:
                    return "MAYO";
                case 6:
                    return "JUNIO";
                case 7:
                    return "JULIO";
                case 8:
                    return "AGOSTO";
                case 9:
                    return "SEPTIEMBRE";
                case 10:
                    return "OCTUBRE";
                case 11:
                    return "NOVIEMBRE";
                case 12:
                    return "DICIEMBRE";
                default:
                    break;
            }
        }
        return "";
    }

    public String getCentroCostos(String departamento) {
        for (CentroCostosEmpleados c : centrosCosto) {
            if (c.getCode().equals(Integer.parseInt(departamento))) {
                return c.getName();
            }
        }

        return "";
    }

    private void obtenerEmpleadosMes() {
        empleados = new ArrayList<>();

        List<Empleado> employees = empleadoFacade.obtenerEmpleadosMes(mes);

        if (employees != null && !employees.isEmpty()) {
            for (Empleado e : employees) {
                EmpleadoDTO dto = new EmpleadoDTO();

                dto.setNombreCompleto(e.getLastName() + " " + e.getFirstName());
                dto.setSucursalVenta(e.getDept().toString());
                dto.setBirthDate(e.getBirthDate());
                dto.setCedula(e.getOfficeExt());

                empleados.add(dto);
            }
        }
    }

    private void cargarCentrosCosto() {
        centrosCosto = centroCostosEmpleadosFacade.findAll();
    }

    public void obtenerSiguienteMes() {
        if (mes < 12) {
            mes++;
        } else {
            mes = 1;
        }
        obtenerEmpleadosMes();
    }

    public void obtenerAnteriorMes() {
        if (mes > 1) {
            mes--;
        } else {
            mes = 12;
        }
        obtenerEmpleadosMes();
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
