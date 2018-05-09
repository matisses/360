package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.dwb.entity.InformeRotacion;
import co.matisses.persistence.dwb.facade.FiltrosInformeFacade;
import co.matisses.persistence.dwb.facade.InformeRotacionFacade;
import co.matisses.persistence.dwb.facade.RotacionFacade;
import co.matisses.web.dto.EstadoInformeDTO;
import co.matisses.web.dto.InformeRotacionDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "informesRotacionMBean")
public class InformesRotacionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    private static final Logger CONSOLE = Logger.getLogger(InformesRotacionMBean.class.getSimpleName());
    private int pagina = 1;
    private int totalPaginas = 1;
    private int datosPagina = 10;
    private List<Integer> paginas;
    private List<InformeRotacionDTO> informes;
    @EJB
    private InformeRotacionFacade informeRotacionFacade;
    @EJB
    private RotacionFacade rotacionFacade;
    @EJB
    private FiltrosInformeFacade filtrosInformeFacade;

    public InformesRotacionMBean() {
        paginas = new ArrayList<>();
        informes = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarInformes();
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

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<InformeRotacionDTO> getInformes() {
        return informes;
    }

    public void setInformes(List<InformeRotacionDTO> informes) {
        this.informes = informes;
    }

    public void cargarInformes() {
        informes = new ArrayList<>();

        long nroRegistros = informeRotacionFacade.obtenerTotalDatos();
        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPagina) + (nroRegistros % datosPagina > 0 ? 1 : 0);
        if (pagina == 0) {
            pagina = 1;
        } else if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }

        List<InformeRotacion> reports = informeRotacionFacade.obtenerInformes(pagina, datosPagina);

        if (reports != null && !reports.isEmpty()) {
            for (InformeRotacion i : reports) {
                InformeRotacionDTO dto = new InformeRotacionDTO();

                dto.setAutor(i.getAutor());
                dto.setCorreo(i.getCorreo());
                dto.setEstado(new EstadoInformeDTO(i.getEstado().getIdEstadoInforme(), i.getEstado().getNombre()));
                dto.setFecha(i.getFecha());
                dto.setIdInforme(i.getIdInforme());
                dto.setNombre(i.getNombre());

                File file = new File(applicationMBean.obtenerValorPropiedad("url.local.reporte") + i.getIdInforme() + "-" + i.getNombre().replace("*", ".") + ".xlsx");
                if (file.exists()) {
                    dto.setDocumentoExiste(true);
                    dto.setUrlDocumento(applicationMBean.obtenerValorPropiedad("url.web.reporte") + i.getIdInforme() + "-" + i.getNombre().replace("*", ".") + ".xlsx");
                } else {
                    dto.setDocumentoExiste(false);
                }

                informes.add(dto);
            }
        }

        construirPaginador();
    }

    private void construirPaginador() {
        paginas = new ArrayList<>();
        int posIni, posFin;
        if (pagina == 1) {
            posIni = pagina - 0;
            posFin = pagina + 4;
        } else if (pagina == 2) {
            posIni = pagina - 1;
            posFin = pagina + 3;
        } else if (pagina > 2 && pagina <= (totalPaginas - 2)) {
            posIni = pagina - 2;
            posFin = pagina + 2;
        } else if (totalPaginas - pagina == 1) {
            posIni = (pagina - 3) == 0 ? 1 : pagina - 3;
            posFin = pagina + 1;
        } else {
            posIni = (pagina - 4) <= 0 ? 1 : pagina - 4;
            posFin = (int) totalPaginas;
        }
        for (int i = posIni; i <= posFin && i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            cargarInformes();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            cargarInformes();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        cargarInformes();
    }

    public String descargarDocumento(Integer idInforme) {
        if (idInforme != null && idInforme != 0) {
            for (InformeRotacionDTO i : informes) {
                if (i.getIdInforme().equals(idInforme)) {
                    if (new File(applicationMBean.obtenerValorPropiedad("url.local.reporte") + i.getIdInforme() + "-" + i.getNombre().replace("*", ".") + ".xlsx").exists()) {
                        try {
                            return "openRuta('" + i.getUrlDocumento() + "'); closeWindow();";
                        } catch (Exception e) {
                            return "";
                        }
                    }
                }
            }
        }
        return "";
    }

    public void eliminarInforme() {
        Integer idInforme = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idInforme"));

        for (InformeRotacionDTO i : informes) {
            if (i.getIdInforme().equals(idInforme)) {
                if (sessionInfoMBean.getUsuario().equals(i.getAutor())) {
                    rotacionFacade.eliminarInforme(idInforme);
                    filtrosInformeFacade.eliminarInforme(idInforme);
                    informeRotacionFacade.eliminarInforme(idInforme);
                    cargarInformes();
                    break;
                }
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
