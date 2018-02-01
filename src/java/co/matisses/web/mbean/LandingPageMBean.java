package co.matisses.web.mbean;

import co.matisses.persistence.web.entity.BwsItemMenu;
import co.matisses.persistence.web.facade.BwsItemMenuFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.MenuItemDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "landingPageMBean")
public class LandingPageMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    private static final Logger log = Logger.getLogger(LandingPageMBean.class.getSimpleName());
    private String ruta;
    private List<MenuItemDTO> listaOpciones;
    @EJB
    private BwsItemMenuFacade menuFacade;

    public LandingPageMBean() {
        listaOpciones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        ruta = request.getRequestURI();
        log.log(Level.INFO, ruta);
        cargarOpciones();
    }

    private void cargarOpciones() {
        List<BwsItemMenu> menus = menuFacade.buscarPorRuta(ruta);
        if (menus.isEmpty()) {
            //mostrar error opcion no existe
            log.log(Level.SEVERE, "No se encontraron menus para la ruta [{0}]", ruta);
            return;
        }
        listaOpciones = new ArrayList<>();
        List<BwsItemMenu> hijos = menuFacade.obtenerHijos(menus.get(0).getIdItemMenu());
        log.log(Level.INFO, "La ruta detectada tiene [{0}] opciones", hijos.size());
        for (BwsItemMenu entidad : hijos) {
            if (entidad.getActivo() && sessionMBean.validarPermisoUsuario(Objetos.valueOf(entidad.getIdObjeto().getJavaName()), Acciones.valueOf(entidad.getIdAccionObjeto().getCodigoAccion().getJavaName()))) {
                MenuItemDTO dto = new MenuItemDTO();
                dto.setIdAccionObjeto(entidad.getIdAccionObjeto().getIdAccionObjeto());
                dto.setIdMenu(entidad.getIdItemMenu());
                dto.setNombre(entidad.getNombre());
                dto.setRuta(entidad.getRuta());

                listaOpciones.add(dto);
            }
        }
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public List<MenuItemDTO> getListaOpciones() {
        return listaOpciones;
    }

    public void setListaOpciones(List<MenuItemDTO> listaOpciones) {
        this.listaOpciones = listaOpciones;
    }
}
