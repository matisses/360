package co.matisses.web.mbean;

import co.matisses.persistence.dto.ItemMenuDTO;
import co.matisses.persistence.web.BwsSecurityManager;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dbotero
 */
@SessionScoped
@Named(value = "navbarSessionMBean")
public class NavbarSessionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(NavbarSessionMBean.class.getSimpleName());
    private String busqueda;
    private List<ItemMenuDTO> menu;
    private List<ItemMenuDTO> menuBusqueda;
    @EJB
    private BwsSecurityManager securityManager;
    @Inject
    private UserSessionInfoMBean userSession;

    public NavbarSessionMBean() {
    }

    @PostConstruct
    protected void cargarMenu() {
        menu = securityManager.cargarMenuUsuario(userSession.getUsuario(), userSession.getSessionId());
        Collections.sort(menu);
    }

    public List<ItemMenuDTO> getMenu() {
        return menu;
    }

    public void setMenu(List<ItemMenuDTO> menu) {
        this.menu = menu;
    }

    public UserSessionInfoMBean getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSessionInfoMBean userSession) {
        this.userSession = userSession;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<String> buscarEnMenu(String texto) {
        log.log(Level.INFO, "buscando [{0}]", texto);
        if (texto == null || texto.isEmpty()) {
            return new ArrayList<>();
        }
        if (menuBusqueda == null || menuBusqueda.isEmpty()) {
            menuBusqueda = securityManager.consultarAccionesDisponiblesUsuario(userSession.getUsuario());
        }
        List<String> valores = new ArrayList<>();
        for (ItemMenuDTO item : menuBusqueda) {
            if (item.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                valores.add(item.getNombre());
            }
            if (valores.size() >= 10) {
                break;
            }
        }
        if (valores.isEmpty()) {
            valores.add("No hay elementos");
        }
        return valores;
    }

    public void itemBusquedaSeleccionado(SelectEvent event) {
        String nombreAccion = event.getObject().toString();
        log.info(nombreAccion);
        for (ItemMenuDTO dto : menuBusqueda) {
            if (dto.getNombre().equalsIgnoreCase(nombreAccion)) {
                try {
                    busqueda = "";
                    FacesContext.getCurrentInstance().getExternalContext().redirect(dto.getRuta());
                } catch (IOException ex) {
                    log.log(Level.SEVERE, "Ocurrio un error al seleccionar un resultado de busqueda. ", ex);
                    Logger.getLogger(NavbarSessionMBean.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}
