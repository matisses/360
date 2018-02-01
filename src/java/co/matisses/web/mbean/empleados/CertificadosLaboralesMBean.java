package co.matisses.web.mbean.empleados;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "certificadosLaboralesMBean")
public class CertificadosLaboralesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(CertificadosLaboralesMBean.class.getSimpleName());
    private Double comision;
    private String cedula;
    private String dirigido;
    private String correoEmpleado;
    private boolean dlgCorreo = false;
    private boolean imprimir = false;
    private boolean enviar = false;
    private Empleado empleado;
    @EJB
    private EmpleadoFacade empleadoFacade;

    public CertificadosLaboralesMBean() {
        empleado = new Empleado();
    }

    @PostConstruct
    protected void initialize() {
        if (!sessionMBean.validarPermisoUsuario(Objetos.CERTIFICADOS_TODOS, Acciones.GENERAR)) {
            cedula = sessionMBean.getCedulaEmpleado();
        }
    }

    public Double getComision() {
        return comision;
    }

    public void setComision(Double comision) {
        this.comision = comision;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getDirigido() {
        return dirigido;
    }

    public void setDirigido(String dirigido) {
        this.dirigido = dirigido;
    }

    public String getCorreoEmpleado() {
        return correoEmpleado;
    }

    public void setCorreoEmpleado(String correoEmpleado) {
        this.correoEmpleado = correoEmpleado;
    }

    public boolean isDlgCorreo() {
        return dlgCorreo;
    }

    public void setDlgCorreo(boolean dlgCorreo) {
        this.dlgCorreo = dlgCorreo;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isEnviar() {
        return enviar;
    }

    public void setEnviar(boolean enviar) {
        this.enviar = enviar;
    }

    public void generarCertificado() {
        if (cedula == null || cedula.isEmpty()) {
            mostrarMensaje("Error", "Ingrese la cedula del empleado.", true, false, false);
            log.log(Level.SEVERE, "Ingrese la cedula del empleado");
            return;
        }
        if (dirigido == null || dirigido.isEmpty()) {
            mostrarMensaje("Error", "Ingrese a quien va dirigido el certificado.", true, false, false);
            log.log(Level.SEVERE, "Ingrese a quien va dirigido el certificado");
            return;
        }

        /*Se valida que el empleado exista*/
        empleado = empleadoFacade.obtenerEmpleadoDocumento(cedula);

        if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
            if (empleado.getUCorreoCorp() != null && !empleado.getUCorreoCorp().isEmpty()) {
                correoEmpleado = empleado.getUCorreoCorp();
            }
            abrirDlgCorreo();
        } else {
            mostrarMensaje("Error", "No se encontró el empleado identificado con el número de cedula ingresada.", true, false, false);
            log.log(Level.SEVERE, "No se encontro el empleado identificado con el numero de cedula {0} ingresada", cedula);
            return;
        }
    }

    public void abrirDlgCorreo() {
        if (dlgCorreo) {
            dlgCorreo = false;
        } else {
            dlgCorreo = true;
        }
    }

    public void imprimir() {
        if (imprimir) {
            imprimir = false;
        } else {
            imprimir = true;
        }
    }

    public void enviar() {
        if (enviar) {
            enviar = false;
        } else {
            enviar = true;
        }
    }

    public void terminar() {
        if (enviar && (correoEmpleado == null || correoEmpleado.isEmpty())) {
            mostrarMensaje("Error", "Ingrese el correo al que desea que llegue el certificado.", true, false, false);
            log.log(Level.SEVERE, "Ingrese el correo al que desea que llegue el certificado");
        }

        String[] s = generarDocumento(empleado.getEmpID(), 1, cedula, "certificado", sessionMBean.getAlmacen(), cedula, imprimir, null);

        if (s != null) {
            if (enviar) {
                enviarCertificado(false, s);
                enviarCertificado(true, s);
            }
        }

        mostrarMensaje("Éxito", "Se genero el certificado correctamente.", false, true, false);
        log.log(Level.INFO, "Se genero el certificado correctamente");
        limpiar();
    }

    private String[] generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setComision(comision);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDirigido(dirigido);
        print.setDocumentosRelacionados(documentosRelacionados);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), nombreArchivo + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    public String abrirPdf() {
        if (!"".equals(cedula)) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.certificado") + cedula;
            if (new File(applicationMBean.obtenerValorPropiedad("url.folder.certificado") + File.separator + cedula + ".pdf").exists()) {
                return "openRuta('" + url + ".pdf');";
            } else {
                String[] s = generarDocumento(empleado.getEmpID(), 1, cedula, "certificado", sessionMBean.getAlmacen(), cedula, imprimir, null);
                if (s != null) {
                    try {
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "No se pudo generar la URL para el documento");
                        return "";
                    }
                } else {
                    log.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            }
        } else {
            return "";
        }
    }

    public void enviarCertificado(boolean notificacion, String[] adjunto) {
        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        GenericRESTResponseDTO res = new GenericRESTResponseDTO();
        Map<String, String> params = new HashMap<>();

        if (!notificacion) {
            params.put("mensaje", "adjunto se encuentra el certificado que solicito en nuestra plataforma.");
        } else {
            params.put("mensaje", "adjunto se encuentra un certificado generado desde la plataforma por el usuario <b>" + sessionMBean.getUsuario() + "</b>.");
        }

        List<String[]> adjuntos = new ArrayList<>();
        if (adjunto != null) {
            adjuntos.add(adjunto);
        }

        String correo = null;
        if (!notificacion) {
            correo = correoEmpleado;
        } else {
            correo = applicationMBean.getDestinatariosPlantillaEmail().get("certificado_laboral").getTo().get(0);
        }
        try {
            res = client.enviarHtmlEmail("Certificado laboral", "Certificado laboral " + baruGenericMBean.getValorFormateado("Double", cedula, 0), correo, "certificado_laboral", adjuntos, params);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el correo con el certificado al correo: {0}. Error {1}", new Object[]{correo, e.getMessage()});
            if (!notificacion) {
                mostrarMensaje("Error", "No se pudo enviar el certificado al correo electrónico.", true, false, false);
            }
            return;
        }
    }

    public void limpiar() {
        comision = null;
        if (sessionMBean.validarPermisoUsuario(Objetos.CERTIFICADOS_TODOS, Acciones.GENERAR)) {
            cedula = null;
        }
        dirigido = null;
        correoEmpleado = null;
        dlgCorreo = false;
        imprimir = false;
        enviar = false;
        empleado = new Empleado();
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