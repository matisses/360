package co.matisses.web.mbean.informes;

import co.matisses.web.EmailPatternValidator;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "certificadosRetencionMBean")
public class CertificadosRetencionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(CertificadosRetencionMBean.class.getSimpleName());
    private String nit;
    private String correo;
    private String tipo;
    private String year;
    private List<Integer> years;

    public CertificadosRetencionMBean() {
        years = new ArrayList<>();
        obtenerYears();
    }

    @PostConstruct
    protected void initialize() {
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public StreamedContent getDescargarCertificado() {
        if (nit == null || nit.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nit para la generación del certificado.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar un nit para la generacion del certificado");
            return null;
        }
        Pattern pattern = Pattern.compile(EmailPatternValidator.getInstance().getEmailPattern());
        if (correo == null || correo.isEmpty()) {
            mostrarMensaje("Error", "Debe suministrar un correo para la generación del certificado.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe suministrar un correo para la generacion del certificado");
            return null;
        } else if (!pattern.matcher(correo).matches()) {
            mostrarMensaje("Error", "El formato del correo ingresado no se reconoce como válido.", true, false, false);
            CONSOLE.log(Level.SEVERE, "El formato del correo ingresado no se reconoce como valido");
            return null;
        }
        if (tipo == null || tipo.isEmpty()) {
            mostrarMensaje("Error", "Seleccione uno de los tipos de certificados disponibles para poder generar el certificado.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Seleccione uno de los tipos de certificados disponibles para poder generar el certificado");
            return null;
        }
        if (year == null || year.isEmpty()) {
            mostrarMensaje("Error", "Seleccione el año del que quiere generar el certificado.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Seleccione el año del que quiere generar el certificado");
            return null;
        }
        if (!validarExistenciaCertificado()) {
            mostrarMensaje("Error", "No se encontró el archivo solicitado.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontro el archivo solicitado");
            return null;
        }

        try {
            InputStream input = new FileInputStream(new File(applicationMBean.obtenerValorPropiedad("url.local.certificados") + year + File.separator + nit + "_" + tipo + ".pdf"));
            StreamedContent streamed = new DefaultStreamedContent(input, applicationMBean.obtenerValorPropiedad("url.local.certificados") + year + File.separator + nit + "_" + tipo + ".pdf",
                    nit + "_" + tipo + ".pdf");
            enviarNotificacion();
            limpiar();
            return streamed;
        } catch (Exception e) {
        } finally {
            limpiar();
        }

        return null;
    }

    private boolean validarExistenciaCertificado() {
        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.certificados") + year + File.separator + nit + "_" + tipo + ".pdf");

        if (!file.exists()) {
            CONSOLE.log(Level.SEVERE, "No se encontraron datos para los certificados");
            return false;
        } else {
            return true;
        }
    }

    private void obtenerYears() {
        years.clear();

        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String fechas = dateFormat.format(fecha);
        int inicio = Integer.parseInt(fechas) - 1;
        int fin = 2014;

        do {
            years.add(inicio);
            inicio--;
        } while (inicio >= fin);
    }

    private void enviarNotificacion() {
        MailMessageDTO dtoMail = new MailMessageDTO();

        dtoMail.setTemplateName("certificado_retencion");

        Map<String, String> params = new HashMap<>();

        params.put("nit", nit);
        switch (tipo) {
            case "RF":
                params.put("tipoCertificado", "retención en la fuente");
                break;
            case "RI":
                params.put("tipoCertificado", "retención en la fuente por iva");
                break;
            case "ICA":
                params.put("tipoCertificado", "retención de ica");
                break;
            default:
                params.put("tipoCertificado", "");
                break;
        }
        params.put("hora", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        params.put("correo", correo);

        dtoMail.setParams(params);
        dtoMail.setAttachments(null);
        dtoMail.setFrom("Certificados retención <notificaciones@matisses.co>");
        dtoMail.setSubject("Certificados retención");
        dtoMail.addToAddress(applicationMBean.getDestinatariosPlantillaEmail().get("certificado_retencion").getTo().get(0));

        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        client.enviarHtmlEmail(dtoMail);
    }

    public void limpiar() {
        nit = null;
        correo = null;
        tipo = null;
        year = null;
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
