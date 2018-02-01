package co.matisses.web.mbean.firma;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.entity.Equipo;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.EquipoFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
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
import org.apache.commons.lang3.text.StrSubstitutor;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "generarFirmaCorreoMBean")
public class GenerarFirmaCorreoMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(GenerarFirmaCorreoMBean.class.getSimpleName());
    private boolean firmaGeneral = false;
    private boolean firmaImports = false;
    private EmpleadoDTO empleado;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private EquipoFacade equipoEmpleadoFacade;

    public GenerarFirmaCorreoMBean() {
        empleado = new EmpleadoDTO();
    }

    @PostConstruct
    protected void initialize() {
        obtenerInformacionEmpelado();
    }

    public boolean isFirmaGeneral() {
        return firmaGeneral;
    }

    public void setFirmaGeneral(boolean firmaGeneral) {
        this.firmaGeneral = firmaGeneral;
    }

    public boolean isFirmaImports() {
        return firmaImports;
    }

    public void setFirmaImports(boolean firmaImports) {
        this.firmaImports = firmaImports;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    private void obtenerInformacionEmpelado() {
        Empleado emp = empleadoFacade.obtenerEmpleadoDocumento(sessionMBean.getCedulaEmpleado());

        if (emp != null && emp.getEmpID() != null && emp.getEmpID() != 0) {
            Equipo equipo = equipoEmpleadoFacade.obtenerEquipoEmpleado(emp.getEmpID());

            if (equipo != null && equipo.getTeamID() != null && equipo.getTeamID() != 0) {
                empleado = new EmpleadoDTO();

                empleado.setCedula(emp.getOfficeExt());
                empleado.setFuncion(emp.getJobTitle().toUpperCase());
                empleado.setCelularCorporativo(emp.getPager() == null || emp.getPager().isEmpty() ? "4440434" : emp.getPager());
                empleado.setSkypeCorporativo(emp.getGovID());
                empleado.setNombre(emp.getFirstName().toUpperCase() + " " + emp.getLastName().toUpperCase());
                empleado.setDepartamento(equipo.getDescriptio().toUpperCase());
            }
        }
    }

    public void seleccionarFirmaGeneral() {
        if (firmaGeneral) {
            firmaGeneral = false;
        } else {
            firmaImports = false;
            firmaGeneral = true;

            obtenerInformacionEmpelado();
        }
    }

    public void seleccionarFirmaImportaciones() {
        if (sessionMBean.validarPermisoUsuario(Objetos.FIRMA_IMPORTS, Acciones.GENERAR)) {
            if (firmaImports) {
                firmaImports = false;
            } else {
                firmaGeneral = false;
                firmaImports = true;

                if (empleado.getDepartamento().contains("IMPORTACIONES")) {
                    empleado.setDepartamento("IMPORTS DEPARTMENT");
                    empleado.setFuncion("IMPORTS COORDINATOR");
                } else if (empleado.getDepartamento().contains("ESTADÍSTICA")) {
                    empleado.setDepartamento("STATISTICS DEPARTMENT");
                    empleado.setFuncion("STATISTICS COORDINATOR");
                }
            }
        } else {
            mostrarMensaje("Error", "El usuario no esta autorizado para generar una firma tipo Imports.", true, false, false);
            log.log(Level.SEVERE, "El usuario no esta autorizado para generar una firma tipo Imports");
            return;
        }
    }

    public StreamedContent getSignatureDownload() throws IOException {
        log.log(Level.INFO, "Se inicia el proceso de descarga de la firma del empleado");
        String nombreFirma = "firma360-" + sessionMBean.getCedulaEmpleado() + ".htm";

        Map<String, String> params = new HashMap<>();
        params.put("cedula", empleado.getCedula());
        params.put("departamento", empleado.getDepartamento());
        params.put("nombre", empleado.getNombre());
        params.put("funcion", empleado.getFuncion());
        params.put("celular", empleado.getCelularCorporativo() == null || empleado.getCelularCorporativo().isEmpty() ? "4440434" : empleado.getCelularCorporativo());
        params.put("skype", empleado.getSkypeCorporativo());

        byte[] bytes = null;
        if (firmaGeneral) {
            String templateContent = new String(Files.readAllBytes(Paths.get(applicationMBean.obtenerValorPropiedad("mail.templates") + "firmaCorreo.html")), StandardCharsets.UTF_8);
            templateContent = StrSubstitutor.replace(templateContent, params);

            /*Se guarda el archivo en los temporales*/
            FileOutputStream out = new FileOutputStream(System.getProperty("jboss.server.temp.dir") + File.separator + nombreFirma);
            bytes = templateContent.getBytes(StandardCharsets.UTF_8);
            out.write(bytes);
            out.close();
        }
        if (firmaImports) {
            String templateContent = new String(Files.readAllBytes(Paths.get(applicationMBean.obtenerValorPropiedad("mail.templates") + "firmaCorreoImports.html")), StandardCharsets.UTF_8);
            templateContent = StrSubstitutor.replace(templateContent, params);

            /*Se guarda el archivo en los temporales*/
            FileOutputStream out = new FileOutputStream(System.getProperty("jboss.server.temp.dir") + File.separator + nombreFirma);
            bytes = templateContent.getBytes(StandardCharsets.UTF_8);
            out.write(bytes);
            out.close();
        }

        /*Se obtiene el archivo y se exporta*/
        InputStream stream = new ByteArrayInputStream(bytes);
        return new DefaultStreamedContent(stream, "text/html", nombreFirma, StandardCharsets.UTF_8.toString());
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
