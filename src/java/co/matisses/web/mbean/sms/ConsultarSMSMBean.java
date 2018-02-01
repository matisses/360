package co.matisses.web.mbean.sms;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.BwsUsuario;
import co.matisses.persistence.web.entity.SmsEnviado;
import co.matisses.persistence.web.facade.BwsUsuarioFacade;
import co.matisses.persistence.web.facade.SmsEnviadoFacade;
import co.matisses.web.dto.SmsEnviadoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
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
@Named(value = "consultarSMSMBean")
public class ConsultarSMSMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(ConsultarSMSMBean.class.getSimpleName());
    private int contadorEstilos;
    private int pagina;
    private int totalRegistros;
    private int totalPaginas = 0;
    private int smsPagina = 12;
    private String parametroBusqueda;
    private List<Integer> paginas;
    private final List<String> coloresPastel;
    private List<SmsEnviadoDTO> smsEnviados;
    private Map<String, String> mapaEstilosUsuario;
    @EJB
    private SmsEnviadoFacade smsEnviadoFacade;
    @EJB
    private BwsUsuarioFacade bwsUsuarioFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;

    public ConsultarSMSMBean() {
        pagina = 1;
        paginas = new ArrayList<>();
        smsEnviados = new ArrayList<>();
        coloresPastel = new ArrayList<>();
        mapaEstilosUsuario = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        asignarColoresPastel();
        obtenerDatos();
    }

    public UserSessionInfoMBean getUserSessionInfoMBean() {
        return userSessionInfoMBean;
    }

    public void setUserSessionInfoMBean(UserSessionInfoMBean userSessionInfoMBean) {
        this.userSessionInfoMBean = userSessionInfoMBean;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getSmsPagina() {
        return smsPagina;
    }

    public void setSmsPagina(int smsPagina) {
        this.smsPagina = smsPagina;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<SmsEnviadoDTO> getSmsEnviados() {
        return smsEnviados;
    }

    public void setSmsEnviados(List<SmsEnviadoDTO> smsEnviados) {
        this.smsEnviados = smsEnviados;
    }

    private void asignarColoresPastel() {
        coloresPastel.add("globo-mensaje-sms-azul-claro");
        coloresPastel.add("globo-mensaje-sms-morado");
        coloresPastel.add("globo-mensaje-sms-naranja");
        coloresPastel.add("globo-mensaje-sms-amarillo");
        coloresPastel.add("globo-mensaje-sms-verde-claro");
        coloresPastel.add("globo-mensaje-sms-verde");
        coloresPastel.add("globo-mensaje-sms-azul");
        coloresPastel.add("globo-mensaje-sms-naranja-claro");
        coloresPastel.add("globo-mensaje-sms-rosa");
    }

    public void cambiarRegistrosMostrar() {
        smsPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de cotizaicones x pagina a [{0}]", smsPagina);
        pagina = 1;
        obtenerDatos();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerDatos();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerDatos();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerDatos();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerDatos();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerDatos();
    }

    public void obtenerDatos() {
        if (pagina == 0) {
            pagina = 1;
        }
        contadorEstilos = 0;
        smsEnviados = new ArrayList<>();
        mapaEstilosUsuario = new HashMap<>();

        totalRegistros = smsEnviadoFacade.obtenerTotalRegistros(parametroBusqueda);
        totalPaginas = (totalRegistros / smsPagina) + (totalRegistros % smsPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();
        List<SmsEnviado> sms = smsEnviadoFacade.obtenerSMSEnviados(pagina, smsPagina, parametroBusqueda);

        if (sms != null && !sms.isEmpty()) {
            for (SmsEnviado s : sms) {
                smsEnviados.add(new SmsEnviadoDTO(s.getIdSMSEnviado(), s.getCodigoPais(), s.getCelular(), s.getMensaje(), s.getMensajeRespuesta(), s.getReferenciaRespuesta(),
                        s.getEstadoRespuesta(), s.getUsuario(), s.getIp(), obtenerEstiloUsuario(s.getUsuario()), obtenerDocumentoUsuario(s.getUsuario()), s.getFecha()));
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos disponibles.", true, false, false);
            log.log(Level.SEVERE, "No se encontraron datos disponibles");
            return;
        }
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    private String obtenerEstiloUsuario(String usuario) {
        if (mapaEstilosUsuario.containsKey(usuario)) {
            return mapaEstilosUsuario.get(usuario);
        } else {
            mapaEstilosUsuario.put(usuario, coloresPastel.get(contadorEstilos));
            contadorEstilos++;
            return mapaEstilosUsuario.get(usuario);
        }
    }

    private String obtenerDocumentoUsuario(String usuario) {
        if (usuario.equals("listaregalos")) {
            return "0000000";
        } else {
            BwsUsuario bwsUsuario = bwsUsuarioFacade.find(usuario);

            if (bwsUsuario != null && bwsUsuario.getUsuario() != null && !bwsUsuario.getUsuario().isEmpty()) {
                Empleado empleado = empleadoFacade.obtenerEmpleadoCodVentas(String.valueOf(bwsUsuario.getIdVendedor()));

                if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
                    return empleado.getOfficeExt();
                }
            }
        }

        return usuario;
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
