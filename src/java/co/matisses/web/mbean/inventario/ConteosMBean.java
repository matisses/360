package co.matisses.web.mbean.inventario;

import co.matisses.persistence.web.entity.ConteoTienda;
import co.matisses.persistence.web.facade.ConteoTiendaFacade;
import co.matisses.web.dto.ConteoTiendaDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.session.ConteosSessionMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
@Named(value = "conteosMBean")
public class ConteosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private ConteosSessionMBean conteosSessionMBean;
    private static final Logger log = Logger.getLogger(ConteosMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int conteosPagina;
    private String parametroBusqueda;
    private String orderBy;
    private String sortOrder;
    private List<Integer> paginas;
    private List<ConteoTiendaDTO> conteos;
    @EJB
    private ConteoTiendaFacade conteoTiendaFacade;

    public ConteosMBean() {
        pagina = 1;
        totalPaginas = 0;
        conteosPagina = 12;
        orderBy = "Seleccione";
        sortOrder = "DESC";
        paginas = new ArrayList<>();
        conteos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        conteosSessionMBean.setIdConteo(null);
        obtenerConteos();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public ConteosSessionMBean getConteosSessionMBean() {
        return conteosSessionMBean;
    }

    public void setConteosSessionMBean(ConteosSessionMBean conteosSessionMBean) {
        this.conteosSessionMBean = conteosSessionMBean;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getOrdenarPor() {
        switch (orderBy) {
            case "#":
                return "#";
            case "Tienda":
                return "Tienda";
            case "Fecha":
                return "Fecha";
            case "Tipo":
                return "Tipo";
            case "Ubicacion":
                return "Ubicación";
            case "Estado":
                return "Estado";
            default:
                return "Seleccione";
        }
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public List<ConteoTiendaDTO> getConteos() {
        return conteos;
    }

    public void setConteos(List<ConteoTiendaDTO> conteos) {
        this.conteos = conteos;
    }

    public int getConteosPagina() {
        return conteosPagina;
    }

    public String getSizePage() {
        if (conteosPagina != 100000) {
            return Integer.toString(conteosPagina);
        } else {
            return "Todos";
        }
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

    private void obtenerConteos() {
        conteos.clear();

        int totalRegistros = conteoTiendaFacade.consultarTotalRegistros(parametroBusqueda);
        totalPaginas = (totalRegistros / conteosPagina) + (totalRegistros % conteosPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();
        List<ConteoTienda> counting = conteoTiendaFacade.consultarConteos(pagina, conteosPagina, parametroBusqueda, orderBy, sortOrder);
        if (counting != null && !counting.isEmpty()) {
            for (ConteoTienda c : counting) {
                conteos.add(new ConteoTiendaDTO(c.getIdConteo(), c.getTienda(), c.getEstado(), c.getUsuario(), c.getUbicacion(), c.getUsuarioFinaliza(),
                        c.getDotacion(), c.getCliente(), c.getVenta(), c.getCasilla(), c.getPda(), c.getFecha(),
                        c.getIdTipoConteo()));
            }
        }
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void ordenarProductos() {
        orderBy = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        log.log(Level.INFO, "Ordenando por [{0}]", orderBy);
        obtenerConteos();
    }

    public void alternarOrden() {
        log.log(Level.INFO, "Alternando orden. Antes [{0}]", sortOrder);
        if (sortOrder.equals("ASC")) {
            sortOrder = "DESC";
        } else {
            sortOrder = "ASC";
        }
        log.log(Level.INFO, "Ahora [{0}]", sortOrder);
        obtenerConteos();
    }

    public void cambiarSizePage() {
        conteosPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de conteos x pagina a [{0}]", conteosPagina);
        pagina = 1;
        obtenerConteos();
    }

    public void buscarConteo() {
        obtenerConteos();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerConteos();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerConteos();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerConteos();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerConteos();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerConteos();
    }

    public String seleccionarConteo() {
        Integer idConteo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idConteo"));
        String estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");

        log.log(Level.INFO, "Se selecciono el conteo con id [{0}], estado [{1}]", new Object[]{idConteo, estado});
        if (estado.equals("P")) {
            conteosSessionMBean.setIdConteo(idConteo);
            return "contar";
        } else if (estado.equals("F")) {
            conteosSessionMBean.setIdConteo(idConteo);
            return "detalle";
        }
        return null;
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
