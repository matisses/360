package co.matisses.web.mbean.sistema.seguridad;

import co.matisses.persistence.web.entity.BwsAccion;
import co.matisses.persistence.web.entity.BwsAccionObjeto;
import co.matisses.persistence.web.entity.BwsItemMenu;
import co.matisses.persistence.web.entity.BwsObjeto;
import co.matisses.persistence.web.facade.BwsAccionFacade;
import co.matisses.persistence.web.facade.BwsAccionObjetoFacade;
import co.matisses.persistence.web.facade.BwsItemMenuFacade;
import co.matisses.persistence.web.facade.BwsObjetoFacade;
import co.matisses.web.dto.AccionBWSDTO;
import co.matisses.web.dto.AccionObjetoBWSDTO;
import co.matisses.web.dto.MenuItemDTO;
import co.matisses.web.dto.ObjetoBWSDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "menu360MBean")
public class Menu360MBean implements Serializable {

    private static final Logger log = Logger.getLogger(Menu360MBean.class.getSimpleName());
    private boolean botonCrearVisible = true;
    private boolean panelAccionObjetoVisible = false;
    private boolean panelBusquedaVisible = false;
    private Integer idMenuPadre;
    private Integer idAccion;
    private Integer idObjeto;
    private Integer idObjeto2;
    private String nombreAccion;
    private String nombreObjeto;
    private String nombreAccionObjeto;
    private MenuItemDTO menuItem;
    private LinkedList<MenuItemDTO> migas;
    private List<MenuItemDTO> listaMenusPadre;
    private List<MenuItemDTO> menuCompleto;
    private List<MenuItemDTO> menuVisible;
    private List<AccionBWSDTO> listaAccionesBasicas;
    private List<ObjetoBWSDTO> listaObjetos;
    private List<AccionObjetoBWSDTO> listaAcciones;
    private List<AccionObjetoBWSDTO> listaAccionesCompleta;
    private List<MenuItemDTO> resultadoBusqueda;
    @EJB
    private BwsItemMenuFacade menuFacade;
    @EJB
    private BwsObjetoFacade objetoFacade;
    @EJB
    private BwsAccionObjetoFacade accionObjetoFacade;
    @EJB
    private BwsAccionFacade accionFacade;

    public Menu360MBean() {
        log.log(Level.INFO, "Iniciando Menu360MBean");
        menuItem = new MenuItemDTO();
        listaMenusPadre = new ArrayList<>();
        listaObjetos = new ArrayList<>();
        listaAcciones = new ArrayList<>();
        listaAccionesCompleta = new ArrayList<>();
        listaAccionesBasicas = new ArrayList<>();
        resultadoBusqueda = new ArrayList<>();
        menuCompleto = new ArrayList<>();
        migas = new LinkedList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarMenusPadre();
        cargarListaObjetos();
        cargarListaAcciones();
        cargarListaAccionesBasicas();
        cargarMenuCompleto();
    }

    public Integer getIdObjeto() {
        return idObjeto;
    }

    public String getIdObjetoSeleccionado() {
        if (idObjeto != null && idObjeto != 0) {
            for (ObjetoBWSDTO o : listaObjetos) {
                if (o.getIdObjeto().equals(idObjeto)) {
                    return o.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public String getNombreAccion() {
        return nombreAccion;
    }

    public void setNombreAccion(String nombreAccion) {
        this.nombreAccion = nombreAccion;
    }

    public String getNombreAccionObjeto() {
        return nombreAccionObjeto;
    }

    public void setNombreAccionObjeto(String nombreAccionObjeto) {
        this.nombreAccionObjeto = nombreAccionObjeto;
    }

    public String getNombreObjeto() {
        return nombreObjeto;
    }

    public void setNombreObjeto(String nombreObjeto) {
        this.nombreObjeto = nombreObjeto;
    }

    public Integer getIdMenuPadre() {
        return idMenuPadre;
    }

    public String getIdMenuPadreSeleccionado() {
        if (idMenuPadre != null && idMenuPadre != 0) {
            for (MenuItemDTO m : listaMenusPadre) {
                if (m.getIdMenu().equals(idMenuPadre)) {
                    return m.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdAccion() {
        return idAccion;
    }

    public String getIdAccionSeleccionada() {
        if (idAccion != null && idAccion != 0) {
            for (AccionObjetoBWSDTO a : listaAcciones) {
                if (a.getIdAccionObjeto().equals(idAccion)) {
                    return a.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getIdObjeto2() {
        return idObjeto2;
    }

    public void setIdObjeto2(Integer idObjeto2) {
        this.idObjeto2 = idObjeto2;
    }

    public MenuItemDTO getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItemDTO menuItem) {
        this.menuItem = menuItem;
    }

    public LinkedList<MenuItemDTO> getMigas() {
        return migas;
    }

    public void setMigas(LinkedList<MenuItemDTO> migas) {
        this.migas = migas;
    }

    public List<MenuItemDTO> getListaMenusPadre() {
        return listaMenusPadre;
    }

    public void setListaMenusPadre(List<MenuItemDTO> listaMenusPadre) {
        this.listaMenusPadre = listaMenusPadre;
    }

    public List<MenuItemDTO> getMenuVisible() {
        return menuVisible;
    }

    public void setMenuVisible(List<MenuItemDTO> menuVisible) {
        this.menuVisible = menuVisible;
    }

    public List<ObjetoBWSDTO> getListaObjetos() {
        return listaObjetos;
    }

    public void setListaObjetos(List<ObjetoBWSDTO> listaObjetos) {
        this.listaObjetos = listaObjetos;
    }

    public List<AccionBWSDTO> getListaAccionesBasicas() {
        return listaAccionesBasicas;
    }

    public void setListaAccionesBasicas(List<AccionBWSDTO> listaAccionesBasicas) {
        this.listaAccionesBasicas = listaAccionesBasicas;
    }

    public List<MenuItemDTO> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(List<MenuItemDTO> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public List<AccionObjetoBWSDTO> getListaAcciones() {
        return listaAcciones;
    }

    public void setListaAcciones(List<AccionObjetoBWSDTO> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    public boolean isBotonCrearVisible() {
        return botonCrearVisible;
    }

    public void setBotonCrearVisible(boolean botonCrearVisible) {
        this.botonCrearVisible = botonCrearVisible;
    }

    public boolean isPanelAccionObjetoVisible() {
        return panelAccionObjetoVisible;
    }

    public void setPanelAccionObjetoVisible(boolean panelAccionObjetoVisible) {
        this.panelAccionObjetoVisible = panelAccionObjetoVisible;
        log.log(Level.INFO, "Panel visibility changed [{0}]", panelAccionObjetoVisible);
    }

    public boolean isPanelBusquedaVisible() {
        return panelBusquedaVisible;
    }

    public void setPanelBusquedaVisible(boolean panelBusquedaVisible) {
        this.panelBusquedaVisible = panelBusquedaVisible;
    }

    private MenuItemDTO menuItemEntityToDto(BwsItemMenu entidad) {
        if (entidad == null) {
            return null;
        }
        try {
            MenuItemDTO dto = new MenuItemDTO();
            dto.setActivo(entidad.getActivo() != null ? entidad.getActivo() : false);
            dto.setIdAccionObjeto(entidad.getIdAccionObjeto() != null ? entidad.getIdAccionObjeto().getIdAccionObjeto() : null);
            dto.setIdMenu(entidad.getIdItemMenu());
            dto.setMenuPadre(entidad.getIdItemMenuPadre() != null ? menuItemEntityToDto(entidad.getIdItemMenuPadre()) : null);
            dto.setNombre(entidad.getNombre());
            dto.setNombreAlterno(entidad.getNombreAlterno());
            dto.setObjeto(entidad.getIdObjeto() != null ? new ObjetoBWSDTO(entidad.getIdObjeto().getCodigo(), entidad.getIdObjeto().getNombre()) : null);
            dto.setOrden(entidad.getOrden());
            dto.setPadre(entidad.getEsMenu());
            dto.setRuta(entidad.getRuta());
            if (entidad.getDestino() == null || entidad.getDestino().trim().isEmpty()) {
                dto.setDestino("_self");
            } else {
                dto.setDestino(entidad.getDestino());
            }
            dto.setTieneSeparadorAntes(entidad.getSeparadorAntes() != null ? entidad.getSeparadorAntes() : false);
            dto.setTieneSeparadorDespues(entidad.getSeparadorDespues() != null ? entidad.getSeparadorDespues() : false);

            return dto;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error convirtiendo el item", e);
            return new MenuItemDTO();
        }
    }

    private void cargarMenusPadre() {
        List<BwsItemMenu> entidades = menuFacade.listarMenusPadre();
        listaMenusPadre = new ArrayList<>();
        Map<String, Integer> nombresPadres = new HashMap<>();
        for (BwsItemMenu entidad : entidades) {
            if (nombresPadres.containsKey(entidad.getNombre())) {
                int posicion = nombresPadres.get(entidad.getNombre());
                MenuItemDTO dto = listaMenusPadre.get(posicion);
                dto.setNombre(dto.getNombre() + " (" + dto.getMenuPadre().getNombre() + ")");
                listaMenusPadre.set(posicion, dto);

                MenuItemDTO dto2 = menuItemEntityToDto(entidad);
                dto2.setNombre(dto2.getNombre() + " (" + dto2.getMenuPadre().getNombre() + ")");
                listaMenusPadre.add(dto2);
            } else {
                nombresPadres.put(entidad.getNombre(), listaMenusPadre.size());
                MenuItemDTO dto = menuItemEntityToDto(entidad);
                listaMenusPadre.add(dto);
            }
        }

        Collections.sort(listaMenusPadre);
    }

    private ObjetoBWSDTO objetoEntityToDto(BwsObjeto entidad) {
        ObjetoBWSDTO dto = new ObjetoBWSDTO();
        dto.setIdObjeto(entidad.getCodigo());
        dto.setNombre(entidad.getNombre());
        return dto;
    }

    private void cargarListaObjetos() {
        listaObjetos = new ArrayList<>();
        List<BwsObjeto> entidades = objetoFacade.findAll();
        for (BwsObjeto entidad : entidades) {
            listaObjetos.add(objetoEntityToDto(entidad));
        }
        Collections.sort(listaObjetos);
    }

    private void cargarListaAcciones() {
        listaAccionesCompleta = new ArrayList<>();
        List<BwsAccionObjeto> entidades = accionObjetoFacade.findAll();
        for (BwsAccionObjeto entidad : entidades) {
            listaAccionesCompleta.add(new AccionObjetoBWSDTO(entidad.getIdAccionObjeto(), entidad.getNombre(), entidad.getCodigoObjeto().getCodigo(), entidad.getCodigoAccion().getCodigo()));
        }
    }

    private void cargarListaAccionesBasicas() {
        listaAccionesBasicas = new ArrayList<>();
        for (BwsAccion entidad : accionFacade.findAll()) {
            listaAccionesBasicas.add(new AccionBWSDTO(entidad.getCodigo(), entidad.getNombre()));
        }
        Collections.sort(listaAccionesBasicas);
    }

    private void cargarMenuCompleto() {
        menuCompleto = new ArrayList<>();
        //menuCompleto.addAll(listaMenusPadre);
        List<BwsItemMenu> entidades = menuFacade.listarMenusPrimerNivel();
        for (BwsItemMenu entidad : entidades) {
            menuCompleto.add(menuItemEntityToDto(entidad));
        }

        for (MenuItemDTO dtoPadre : menuCompleto) {
            dtoPadre.setHijos(cargarListaHijos(dtoPadre));
        }
        menuVisible = new ArrayList<>();
        menuVisible.addAll(menuCompleto);
    }

    private List<MenuItemDTO> cargarListaHijos(MenuItemDTO dtoPadre) {
        if (dtoPadre.isPadre()) {
            List<BwsItemMenu> entidades = menuFacade.obtenerHijos(dtoPadre.getIdMenu());
            List<MenuItemDTO> dtos = new ArrayList<>();
            for (BwsItemMenu entidad : entidades) {
                MenuItemDTO dtoHijo = menuItemEntityToDto(entidad);
                if (entidad.getEsMenu()) {
                    dtoHijo.setHijos(cargarListaHijos(dtoHijo));
                }
                dtos.add(dtoHijo);
            }
            return dtos;
        } else {
            return null;
        }
    }

    public void filtrarListaAcciones() {
        log.log(Level.INFO, "Cargando las acciones para el objeto [{0}]", idObjeto);
        listaAcciones = new ArrayList<>();
        for (AccionObjetoBWSDTO dto : listaAccionesCompleta) {
            if (dto.getCodigoObjeto().equals(idObjeto)) {
                listaAcciones.add(dto);
            }
        }
        Collections.sort(listaAcciones);
    }

    public void agregarAccion() {
        if (nombreAccion == null || nombreAccion.trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para la nueva acción.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un nombre para la nueva accion");
            return;
        }

        List<BwsAccion> acciones = accionFacade.obtenerAccionesNombre(nombreAccion);
        if (acciones != null && !acciones.isEmpty()) {
            mostrarMensaje("Error", "La acción ya habia sido registrada.", true, false, false);
            log.log(Level.SEVERE, "La accion ya habia sido registrada");
            return;
        }

        BwsAccion entidad = new BwsAccion();
        entidad.setNombre(nombreAccion);
        entidad.setJavaName(nombreAccion.toUpperCase());

        try {
            accionFacade.create(entidad);
            log.log(Level.INFO, "Se registro la accion de nombre [{0}] y codigo [{1}]", new Object[]{nombreAccion, entidad.getCodigo()});
            mostrarMensaje("Éxito", "Se creo la acción correctamente.", false, true, false);
            nombreAccion = null;
            cargarListaAccionesBasicas();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al registrar la accion. Error {0}", e.getMessage());
            mostrarMensaje("Error", "No fue posible registrar la acción solicitada.", true, false, false);
            return;
        }
    }

    public void agregarObjeto() {
        if (nombreObjeto == null || nombreObjeto.trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre para el nuevo objeto.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un nombre para el nuevo objeto");
            return;
        }

        List<BwsObjeto> objetos = objetoFacade.obtenerObjetosNombre(nombreObjeto);
        if (objetos != null && !objetos.isEmpty()) {
            mostrarMensaje("Error", "El objeto ya habia sido registrado.", true, false, false);
            log.log(Level.SEVERE, "El objeto ya habia sido registrado");
            return;
        }

        BwsObjeto entidad = new BwsObjeto();
        entidad.setNombre(nombreObjeto);
        entidad.setJavaName(nombreObjeto.toUpperCase().replace(" ", "_"));

        try {
            objetoFacade.create(entidad);
            log.log(Level.INFO, "Se registro el objeto de nombre [{0}] y codigo [{1}]", new Object[]{nombreObjeto, entidad.getCodigo()});
            mostrarMensaje("Éxito", "Se creo el objeto correctamente.", false, true, false);
            nombreObjeto = null;
            cargarListaObjetos();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al registrar el objeto. Error {0}", e.getMessage());
            mostrarMensaje("Error", "No fue posible registrar el objeto solicitada.", true, false, false);
            return;
        }
    }

    public void seleccionarObjetoAsignar() {
        idObjeto2 = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idObjeto"));
        log.log(Level.INFO, "Se selecciono el objecto con id [{0}], para la asignacion de accion - objeto", idObjeto2);
        armarNombreAccionObjeto();
    }

    public void seleccionarAccionAsignar() {
        idAccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAccion"));
        log.log(Level.INFO, "Se selecciono la accion con id [{0}], para la asignacion de accion - objeto", idAccion);
        armarNombreAccionObjeto();
    }

    public void agregarAccionObjeto() {
        if (idAccion == null || idAccion == 0) {
            mostrarMensaje("Error", "Seleccione una acción para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione una accion para poder continuar");
            return;
        }
        if (idObjeto2 == null || idObjeto2 == 0) {
            mostrarMensaje("Error", "Seleccione un objeto para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Seleccione un objeto para poder continuar");
            return;
        }
        if (nombreAccionObjeto == null || nombreAccionObjeto.trim().isEmpty()) {
            mostrarMensaje("Error", "El nombre que se esta ingresando no es correcto.", true, false, false);
            log.log(Level.SEVERE, "El nombre que se esta ingresando no es correcto");
            return;
        }

        BwsAccionObjeto accionObjeto = accionObjetoFacade.buscarPorAccionObjeto(idAccion, idObjeto2);

        if (accionObjeto != null && accionObjeto.getIdAccionObjeto() != null && accionObjeto.getIdAccionObjeto() != 0) {
            mostrarMensaje("Error", "La acción - objeto, ya esta asignada.", true, false, false);
            log.log(Level.SEVERE, "La accion - objeto, ya esta asignada");
            return;
        }

        BwsAccionObjeto entidad = new BwsAccionObjeto();
        entidad.setCodigoAccion(new BwsAccion(idAccion));
        entidad.setCodigoObjeto(new BwsObjeto(idObjeto2));
        entidad.setNombre(nombreAccionObjeto);

        try {
            accionObjetoFacade.create(entidad);
            log.log(Level.INFO, "Se creo la accion - objeto correctamente, con id {0}", entidad.getIdAccionObjeto());
            mostrarMensaje("Éxito", "Se creo la acción - objeto correctamente.", false, true, false);

            idAccion = null;
            idObjeto2 = null;
            nombreAccionObjeto = null;
            nombreAccion = null;
            nombreObjeto = null;

            cargarListaObjetos();
            cargarListaAcciones();
            cargarListaAccionesBasicas();
            filtrarListaAcciones();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear la ccion - objeto. Error {0}", e.getMessage());
            mostrarMensaje("Error", "No se pudo crear la acción - objeto.", true, false, false);
            return;
        }
    }

    public void buscar() {
        resultadoBusqueda = new ArrayList<>();
        if (menuItem.getNombre() != null) {
            log.log(Level.INFO, "Buscando menus por nombre", resultadoBusqueda.size());
            //buscar por nombre -incluye asterisco-
            List<BwsItemMenu> resultado = menuFacade.buscarPorNombre(menuItem.getNombre().toLowerCase());
            for (BwsItemMenu entidad : resultado) {
                resultadoBusqueda.add(menuItemEntityToDto(entidad));
            }
        } else if (menuItem.getNombreAlterno() != null) {
            //buscar por nombre alterno
        }
        log.log(Level.INFO, "Se encontraron [{0}] resultados", resultadoBusqueda.size());
        if (resultadoBusqueda.size() == 1) {
            //si solo hay un resultado, lo carga directamente en los campos del formulario
            panelBusquedaVisible = false;
            mostrarMenuEncontrado(resultadoBusqueda.get(0));
        } else {
            panelBusquedaVisible = true;
        }
    }

    public void crear() {
        log.log(Level.INFO, "Creando nuevo menu");
        if (menuItem.getNombre() == null || menuItem.getNombre().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el nombre del menú.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar el nombre del menu");
            return;
        }
        if (menuItem.getNombreAlterno() == null || menuItem.getNombreAlterno().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre alterno.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un nombre alterno");
            return;
        }
        if (menuItem.getRuta() == null || menuItem.getRuta().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar la ruta.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar la ruta");
            return;
        }

        BwsItemMenu entidad = new BwsItemMenu();
        entidad.setActivo(menuItem.isActivo());
        entidad.setEsMenu(menuItem.isPadre());
        entidad.setIdAccionObjeto(new BwsAccionObjeto(idAccion));
        entidad.setIdItemMenuPadre(idMenuPadre != null ? new BwsItemMenu(idMenuPadre) : null);
        entidad.setIdObjeto(idObjeto != null ? new BwsObjeto(idObjeto) : null);
        entidad.setNombre(menuItem.getNombre());
        entidad.setNombreAlterno(menuItem.getNombreAlterno());
        entidad.setRuta(menuItem.getRuta());
        entidad.setDestino(menuItem.getDestino());
        entidad.setSeparadorAntes(menuItem.isTieneSeparadorAntes());
        entidad.setSeparadorDespues(menuItem.isTieneSeparadorDespues());

        if (idMenuPadre == null) {
            entidad.setOrden(menuCompleto.size());
        } else {
            List<MenuItemDTO> hermanos = cargarListaHijos(menuItemEntityToDto(menuFacade.find(idMenuPadre)));
            entidad.setOrden(hermanos.size());
        }

        try {
            menuFacade.create(entidad);
            if (entidad.getIdItemMenu() != null) {
                log.log(Level.INFO, "Se creo el menu #[{0}]. ", entidad.getIdItemMenu());
            } else {
                log.log(Level.WARNING, "No fue posible recuperar el ID del nuevo menu. ");
            }
            mostrarMensaje("Éxito", "Item menú creado correctamente.", false, true, false);
            limpiar();
            cargarMenuCompleto();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el nuevo menu. ", e);
            mostrarMensaje("Error", "No se pudo crear el nuevo item para el menú.", true, false, false);
            return;
        }
    }

    public void limpiar() {
        log.log(Level.INFO, "Limpiando formulario");
        menuItem = new MenuItemDTO();
        idMenuPadre = null;
        idObjeto = null;
        filtrarListaAcciones();
        panelBusquedaVisible = false;
        panelAccionObjetoVisible = false;
        botonCrearVisible = true;
    }

    public void actualizar() {
        log.log(Level.INFO, "Actualizando registro de menu");
        if (menuItem.getNombre() == null || menuItem.getNombre().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el nombre del menú.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar el nombre del menu");
            return;
        }
        if (menuItem.getNombreAlterno() == null || menuItem.getNombreAlterno().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar un nombre alterno.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar un nombre alterno");
            return;
        }
        if (menuItem.getRuta() == null || menuItem.getRuta().trim().isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar la ruta.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar la ruta");
            return;
        }

        if (menuItem.getDestino() == null || menuItem.getDestino().trim().isEmpty()) {
            menuItem.setDestino("_self");
        }

        BwsItemMenu entidad = new BwsItemMenu();
        entidad.setIdItemMenu(menuItem.getIdMenu());
        entidad.setActivo(menuItem.isActivo());
        entidad.setEsMenu(menuItem.isPadre());
        entidad.setIdAccionObjeto(new BwsAccionObjeto(idAccion));
        entidad.setIdItemMenuPadre(menuItem.getMenuPadre() != null ? new BwsItemMenu(menuItem.getMenuPadre().getIdMenu()) : null);
        entidad.setIdObjeto(idObjeto != null ? new BwsObjeto(idObjeto) : null);
        entidad.setNombre(menuItem.getNombre());
        entidad.setNombreAlterno(menuItem.getNombreAlterno());
        entidad.setOrden(menuItem.getOrden());
        entidad.setRuta(menuItem.getRuta());
        entidad.setDestino(menuItem.getDestino());
        entidad.setSeparadorAntes(menuItem.isTieneSeparadorAntes());
        entidad.setSeparadorDespues(menuItem.isTieneSeparadorDespues());

        try {
            menuFacade.edit(entidad);
            mostrarMensaje("Éxito", "Se modifico el item menú correctamente.", false, true, false);
            limpiar();
            cargarMenusPadre();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al modificar el menu. ", e);
            mostrarMensaje("Error", "Ocurrio un error al modificar el item de menú.", true, false, false);
            return;
        }
    }

    public void mostrarPanelAccionObjeto() {
        setPanelAccionObjetoVisible(true);
    }

    public void ocultarPanelAccionObjeto() {
        idAccion = null;
        idObjeto2 = null;
        nombreAccionObjeto = null;
        setPanelAccionObjetoVisible(false);

    }

    private void armarNombreAccionObjeto() {
        String nombreAccionSeleccionado = null;
        for (AccionBWSDTO dto : listaAccionesBasicas) {
            if (dto.getCodigo().equals(idAccion)) {
                nombreAccionSeleccionado = dto.getNombre();
                break;
            }
        }
        String nombreObjetoSeleccionado = null;
        for (ObjetoBWSDTO dto : listaObjetos) {
            if (dto.getIdObjeto().equals(idObjeto2)) {
                nombreObjetoSeleccionado = dto.getNombre();
                break;
            }
        }
        nombreAccionObjeto = (nombreAccionSeleccionado != null ? nombreAccionSeleccionado : "") + " " + (nombreObjetoSeleccionado != null ? nombreObjetoSeleccionado.toLowerCase() : "");
    }

    public void subirMenu() {
        String codigoMenu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        log.log(Level.INFO, "Moviendo hacia arriba el menu [{0}]", codigoMenu);
        for (int i = 0; i < menuVisible.size(); i++) {
            MenuItemDTO dtoSubir = menuVisible.get(i);
            if (dtoSubir.getIdMenu().equals(new Integer(codigoMenu))) {
                MenuItemDTO dtoBajar = menuVisible.get(i - 1);
                menuVisible.set(i - 1, dtoSubir);
                menuVisible.set(i, dtoBajar);

                actualizarOrdenMenu(dtoSubir, dtoBajar);
                return;
            }
        }
    }

    public void bajarMenu() {
        String codigoMenu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        log.log(Level.INFO, "Moviendo hacia abajo el menu [{0}]", codigoMenu);
        for (int i = 0; i < menuVisible.size(); i++) {
            MenuItemDTO dtoBajar = menuVisible.get(i);
            if (dtoBajar.getIdMenu().equals(new Integer(codigoMenu))) {
                MenuItemDTO dtoSubir = menuVisible.get(i + 1);
                menuVisible.set(i, dtoSubir);
                menuVisible.set(i + 1, dtoBajar);

                actualizarOrdenMenu(dtoSubir, dtoBajar);
                return;
            }
        }
    }

    private void actualizarOrdenMenu(MenuItemDTO dtoSubir, MenuItemDTO dtoBajar) {
        try {
            BwsItemMenu entidadSubir = menuFacade.find(dtoSubir.getIdMenu());
            entidadSubir.setOrden(entidadSubir.getOrden() - 1);
            menuFacade.edit(entidadSubir);
            log.log(Level.INFO, "Se movio el menu #[{0}] a la posicion [{1}]", new Object[]{entidadSubir.getIdItemMenu(), entidadSubir.getOrden()});

            BwsItemMenu entidadBajar = menuFacade.find(dtoBajar.getIdMenu());
            entidadBajar.setOrden(entidadBajar.getOrden() + 1);
            menuFacade.edit(entidadBajar);
            log.log(Level.INFO, "Se movio el menu #[{0}] a la posicion [{1}]", new Object[]{entidadBajar.getIdItemMenu(), entidadBajar.getOrden()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar el orden del menu. ", e);
        }
    }

    public void mostrarSubmenu() {
        String codigoMenu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        migas = new LinkedList<>();
        if (codigoMenu == null) {
            menuVisible = new ArrayList<>();
            menuVisible.addAll(menuCompleto);
            return;
        }
        menuVisible = new ArrayList<>();
        for (MenuItemDTO dto : listaMenusPadre) {
            if (dto.getIdMenu().equals(new Integer(codigoMenu))) {
                List<MenuItemDTO> hijos = cargarListaHijos(dto);
                if (hijos.isEmpty()) {
                    mostrarMensaje("No se puede mostrar el menú", "No se encontraron elementos para mostrar como submenú de " + dto.getNombre(), true, false, false);
                    log.log(Level.SEVERE, "No se encontraron elementos para mostrar como submenu de [{0}]", dto.getNombre());
                    return;
                }
                menuVisible.addAll(hijos);
                //arma las migas desde cero
                agregarMigas(dto);
                break;
            }
        }
        limpiar();
    }

    private void agregarMigas(MenuItemDTO dto) {
        migas.addFirst(dto);
        if (dto.getMenuPadre() != null) {
            agregarMigas(dto.getMenuPadre());
        }
    }

    public void seleccionarMenu() {
        String codigoMenu = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        if (codigoMenu == null) {
            return;
        }
        if (resultadoBusqueda.isEmpty()) {
            //Busca en lista de menus visible
            for (MenuItemDTO dto : menuVisible) {
                if (dto.getIdMenu().equals(new Integer(codigoMenu))) {
                    mostrarMenuEncontrado(dto);
                    return;
                }
            }
        } else {
            //Busca en lista de resultados de busqueda
            for (MenuItemDTO dto : resultadoBusqueda) {
                if (dto.getIdMenu().equals(new Integer(codigoMenu))) {
                    mostrarMenuEncontrado(dto);
                    return;
                }
            }
        }
    }

    private void mostrarMenuEncontrado(MenuItemDTO dto) {
        menuItem = dto;
        idMenuPadre = menuItem.getMenuPadre() != null ? menuItem.getMenuPadre().getIdMenu() : null;
        idObjeto = menuItem.getObjeto() != null ? menuItem.getObjeto().getIdObjeto() : null;
        filtrarListaAcciones();
        idAccion = menuItem.getIdAccionObjeto();
        panelBusquedaVisible = false;
        botonCrearVisible = false;
    }

    public String obtenerEstilosAdicionales(MenuItemDTO dto) {
        StringBuilder sb = new StringBuilder();
        if (dto.isTieneSeparadorAntes()) {
            sb.append("menu-item-borde-superior ");
        }
        if (dto.isTieneSeparadorDespues()) {
            sb.append("menu-item-borde-inferior ");
        }
        if (!dto.isActivo()) {
            sb.append("menu-item-inactivo ");
        }
        if (menuItem != null && menuItem.getIdMenu() != null && menuItem.getIdMenu().equals(dto.getIdMenu())) {
            sb.append("menu-item-selected ");
        }
        return sb.toString();
    }

    /*01-12-2016: Se crea bloque que de codigo, por motivo de actualizacion de la vista*/
    public void seleccionarMenuPadre() {
        idMenuPadre = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idMenuPadre"));
        log.log(Level.INFO, "Menu padre seleccionado [{0}]", idMenuPadre);
    }

    public void seleccionarDestino() {
        menuItem.setDestino(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("destino"));
        log.log(Level.INFO, "Destino seleccionado [{0}]", menuItem.getDestino());
    }

    public void seleccionarObjeto() {
        panelAccionObjetoVisible = false;
        idObjeto = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idObjeto"));
        log.log(Level.INFO, "Objeto seleccionado [{0}]", idObjeto);
        if (idObjeto != null && idObjeto > 0) {
            filtrarListaAcciones();
        } else if (idObjeto != null && idObjeto == -1) {
            log.log(Level.INFO, "El usuario selecciono Nuevo objecto");
            panelAccionObjetoVisible = true;
        }
    }

    public void seleccionarAccion() {
        idAccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idAccion"));
        log.log(Level.INFO, "Accion seleccionada [{0}]", idAccion);
    }

    public void seleccionarEsMenu() {
        menuItem.setPadre(Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("esMenu")));
        log.log(Level.INFO, "Es menu [{0}]", menuItem.isPadre());
    }

    public void seleccionarSeparadorAntes() {
        menuItem.setTieneSeparadorAntes(Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("separadorAntes")));
        log.log(Level.INFO, "Separador antes [{0}]", menuItem.isTieneSeparadorAntes());
    }

    public void seleccionarSeparadorDespues() {
        menuItem.setTieneSeparadorDespues(Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("separadorDespues")));
        log.log(Level.INFO, "Separador despues [{0}]", menuItem.isTieneSeparadorDespues());
    }

    public void seleccionarActivo() {
        menuItem.setActivo(Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("activo")));
        log.log(Level.INFO, "Activo [{0}]", menuItem.isActivo());
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
