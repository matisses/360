package co.matisses.web.mbean.sistema.seguridad;

import co.matisses.persistence.web.entity.BwsPerfil;
import co.matisses.persistence.web.entity.BwsPerfilUsuario;
import co.matisses.persistence.web.entity.BwsUsuario;
import co.matisses.persistence.web.facade.BwsPerfilFacade;
import co.matisses.persistence.web.facade.BwsPerfilUsuarioFacade;
import co.matisses.persistence.web.facade.BwsUsuarioFacade;
import co.matisses.web.dto.PerfilBWSDTO;
import co.matisses.web.ldap.BaruLDAPAuth;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@Named(value = "usuario360MBean")
public class Usuario360MBean implements Serializable {

    private static final Logger log = Logger.getLogger(Usuario360MBean.class.getSimpleName());
    private Integer perfilAgregar;
    private Integer perfilQuitar;
    private String usuario;
    private String filtroDisponibles;
    private String filtroSeleccionados;
    private List<String> resultadoBusqueda;
    private List<String> usuarios;
    private List<PerfilBWSDTO> listaPerfilesDisponibles;
    private List<PerfilBWSDTO> listaPerfilesSeleccionados;
    private List<PerfilBWSDTO> listaPerfilesDisponiblesFiltrada;
    private List<PerfilBWSDTO> listaPerfilesSeleccionadosFiltrada;
    private boolean btnGuardarHabilitado = false;
    private boolean mostrarPanelResultados = false;
    @EJB
    private BwsPerfilFacade perfilFacade;
    @EJB
    private BwsPerfilUsuarioFacade perfilUsuarioFacade;
    @EJB
    private BwsUsuarioFacade usuarioFacade;
    @EJB
    private BaruLDAPAuth ldapManager;

    public Usuario360MBean() {
        listaPerfilesDisponibles = new ArrayList<>();
        listaPerfilesSeleccionados = new ArrayList<>();
        listaPerfilesDisponiblesFiltrada = new ArrayList<>();
        listaPerfilesSeleccionadosFiltrada = new ArrayList<>();
        resultadoBusqueda = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarUsuarios();
        cargarPerfiles();
        inicializarSeleccionados();
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getUsuarioSeleccionado() {
        if (usuario != null && !usuario.isEmpty()) {
            return usuario;
        }
        return "Seleccione";
    }

    public String getFiltroDisponibles() {
        return filtroDisponibles;
    }

    public void setFiltroDisponibles(String filtroDisponibles) {
        this.filtroDisponibles = filtroDisponibles;
    }

    public String getFiltroSeleccionados() {
        return filtroSeleccionados;
    }

    public void setFiltroSeleccionados(String filtroSeleccionados) {
        this.filtroSeleccionados = filtroSeleccionados;
    }

    public boolean isBtnGuardarHabilitado() {
        return btnGuardarHabilitado;
    }

    public void setBtnGuardarHabilitado(boolean btnGuardarHabilitado) {
        this.btnGuardarHabilitado = btnGuardarHabilitado;
    }

    public boolean isMostrarPanelResultados() {
        return mostrarPanelResultados;
    }

    public void setMostrarPanelResultados(boolean mostrarPanelResultados) {
        this.mostrarPanelResultados = mostrarPanelResultados;
    }

    public Integer getPerfilAgregar() {
        return perfilAgregar;
    }

    public String getPerfilAgregarSeleccionado() {
        if (perfilAgregar != null && perfilAgregar != 0) {
            for (PerfilBWSDTO p : listaPerfilesDisponibles) {
                if (p.getCodigo().equals(perfilAgregar)) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getPerfilQuitar() {
        return perfilQuitar;
    }

    public String getPerfilQuitarSeleccionado() {
        if (perfilQuitar != null && perfilQuitar != 0) {
            for (PerfilBWSDTO p : listaPerfilesSeleccionados) {
                if (p.getCodigo().equals(perfilQuitar)) {
                    return p.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public List<PerfilBWSDTO> getListaPerfilesDisponiblesFiltrada() {
        return listaPerfilesDisponiblesFiltrada;
    }

    public void setListaPerfilesDisponiblesFiltrada(List<PerfilBWSDTO> listaPerfilesDisponiblesFiltrada) {
        this.listaPerfilesDisponiblesFiltrada = listaPerfilesDisponiblesFiltrada;
    }

    public List<PerfilBWSDTO> getListaPerfilesSeleccionadosFiltrada() {
        return listaPerfilesSeleccionadosFiltrada;
    }

    public void setListaPerfilesSeleccionadosFiltrada(List<PerfilBWSDTO> listaPerfilesSeleccionadosFiltrada) {
        this.listaPerfilesSeleccionadosFiltrada = listaPerfilesSeleccionadosFiltrada;
    }

    public List<PerfilBWSDTO> getListaPerfilesDisponibles() {
        return listaPerfilesDisponibles;
    }

    public void setListaPerfilesDisponibles(List<PerfilBWSDTO> listaPerfilesDisponibles) {
        this.listaPerfilesDisponibles = listaPerfilesDisponibles;
    }

    public List<PerfilBWSDTO> getListaPerfilesSeleccionados() {
        return listaPerfilesSeleccionados;
    }

    public void setListaPerfilesSeleccionados(List<PerfilBWSDTO> listaPerfilesSeleccionados) {
        this.listaPerfilesSeleccionados = listaPerfilesSeleccionados;
    }

    public List<String> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(List<String> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    private PerfilBWSDTO perfilEntity2Dto(BwsPerfil entidad) {
        PerfilBWSDTO dto = new PerfilBWSDTO();
        dto.setCodigo(entidad.getCodigo());
        dto.setNombre(entidad.getNombre());
        return dto;
    }

    private void cargarPerfiles() {
        List<BwsPerfil> entidades = perfilFacade.findAll();
        listaPerfilesDisponibles = new ArrayList<>();
        for (BwsPerfil entidad : entidades) {
            listaPerfilesDisponibles.add(perfilEntity2Dto(entidad));
        }
        filtrarListaDisponibles();
    }

    private void inicializarSeleccionados() {
        listaPerfilesSeleccionados = new ArrayList<>();
        listaPerfilesSeleccionadosFiltrada.add(new PerfilBWSDTO(null, "Seleccione un perfil"));
    }

    private void cargarUsuarios() {
        usuarios = new ArrayList<>();
        usuarios.addAll(ldapManager.listEmployees());
        usuarios.addAll(ldapManager.listOtherUsers());
        Collections.sort(usuarios);
    }

    public void cargarUsuario() {
        log.log(Level.INFO, "Cargando usuario [{0}]", usuario);
        listaPerfilesSeleccionados = new ArrayList<>();
        List<BwsPerfilUsuario> entidades = perfilUsuarioFacade.buscarPerfilesPorUsuario(usuario);
        for (BwsPerfilUsuario entidad : entidades) {
            listaPerfilesSeleccionados.add(perfilEntity2Dto(entidad.getCodigoPerfil()));
        }
        limpiarListaDisponibles();
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    private void limpiarListaDisponibles() {
        for (int i = 0; i < listaPerfilesDisponibles.size(); i++) {
            if (listaPerfilesSeleccionados.contains(listaPerfilesDisponibles.get(i))) {
                listaPerfilesDisponibles.remove(i);
                i--;
            }
        }
    }

    public void filtrarListaDisponibles() {
        log.log(Level.INFO, "Filtrando perfiles disponibles [{0}]", filtroDisponibles);
        listaPerfilesDisponiblesFiltrada = new ArrayList<>();
        if (filtroDisponibles == null || filtroDisponibles.isEmpty()) {
            listaPerfilesDisponiblesFiltrada.addAll(listaPerfilesDisponibles);
        } else {
            for (PerfilBWSDTO dto : listaPerfilesDisponibles) {
                if (dto.getNombre().toLowerCase().contains(filtroDisponibles.toLowerCase())) {
                    listaPerfilesDisponiblesFiltrada.add(dto);
                }
            }
        }
        Collections.sort(listaPerfilesDisponiblesFiltrada);
    }

    public void filtrarListaSeleccionados() {
        log.log(Level.INFO, "Filtrando perfiles seleccionados [{0}]", filtroSeleccionados);
        listaPerfilesSeleccionadosFiltrada = new ArrayList<>();
        if (filtroSeleccionados == null || filtroSeleccionados.isEmpty()) {
            listaPerfilesSeleccionadosFiltrada.addAll(listaPerfilesSeleccionados);
        } else {
            for (PerfilBWSDTO dto : listaPerfilesSeleccionados) {
                if (dto.getNombre().toLowerCase().contains(filtroSeleccionados.toLowerCase())) {
                    listaPerfilesSeleccionadosFiltrada.add(dto);
                }
            }
        }
        Collections.sort(listaPerfilesSeleccionadosFiltrada);
    }

    public void seleccionarPerfilAgregar() {
        perfilAgregar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("perfil"));
        log.log(Level.INFO, "Se selecciono el perfil con id [{0}], para agregar", perfilAgregar);
    }

    public void agregarPerfil() {
        int posicion = listaPerfilesDisponibles.indexOf(new PerfilBWSDTO(perfilAgregar, null));
        if (posicion < 0) {
            mostrarMensaje("Error", "No se encontro el perfil seleccionado.", true, false, false);
            log.log(Level.SEVERE, "No se encontro el perfil seleccionado");
            return;
        }
        PerfilBWSDTO dto = listaPerfilesDisponibles.get(posicion);

        BwsPerfil entidadPerfil = perfilFacade.find(dto.getCodigo());

        if (entidadPerfil == null || entidadPerfil.getCodigo() == null || entidadPerfil.getCodigo() == 0) {
            mostrarMensaje("Error", "No se puede agregar el perfil debido a que no se encontro el registro del perfil.", true, false, false);
            log.log(Level.SEVERE, "Eror al agregar el perfil debido a un que no se encontro el registro del perfil");
            return;
        }
        BwsUsuario entidadUsuario = usuarioFacade.find(usuario);

        if (entidadUsuario == null || entidadUsuario.getUsuario() == null || entidadUsuario.getUsuario().isEmpty()) {
            mostrarMensaje("Error", "No se puede agregar el perfil debido a que no se encontro el registro del usuario.", true, false, false);
            log.log(Level.SEVERE, "Eror al agregar el perfil debido a un que no se encontro el registro del usuario");
            return;
        }
        BwsPerfilUsuario entidad = new BwsPerfilUsuario();
        entidad.setCodigoPerfil(entidadPerfil);
        entidad.setUsuario(entidadUsuario);

        try {
            perfilUsuarioFacade.create(entidad);
            log.log(Level.INFO, "Se agrego perfil al usuario seleccionado");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al agregar el perfil al usuario.", e);
            mostrarMensaje("Error", "Ocurrio un error al incluir el perfil al usuario", true, false, false);
            return;
        }

        listaPerfilesSeleccionados.add(dto);
        listaPerfilesDisponibles.remove(posicion);
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    public void seleccionarPerfil() {
        log.log(Level.INFO, "Seleccionando perfil [{0}]", perfilAgregar);
        perfilAgregar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("perfil"));
        log.log(Level.INFO, "Se selecciono el perfil con id [{0}], para agregar", perfilAgregar);

        agregarPerfil();
    }

    public void seleccionarPerfilQuitar() {
        perfilQuitar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("perfil"));
        log.log(Level.INFO, "Se selecciono el perfil con id [{0}], para quitar", perfilQuitar);
    }

    public void quitarPerfil() {
        int posicion = listaPerfilesSeleccionados.indexOf(new PerfilBWSDTO(perfilQuitar, null));
        if (posicion < 0) {
            mostrarMensaje("Error", "No se encontro el perfil seleccionado.", true, false, false);
            log.log(Level.SEVERE, "No se encontro el perfil seleccionado");
            return;
        }
        PerfilBWSDTO dto = listaPerfilesSeleccionados.get(posicion);

        try {
            perfilUsuarioFacade.eliminarPorUsuarioPerfil(usuario, dto.getCodigo());
            log.log(Level.INFO, "Se quito el perfil al usuario seleccionado");
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrio un error al quitar el perfil del usuario.", true, false, false);
            log.log(Level.SEVERE, "Ocurrio un error al agregar el perfil[" + dto.getCodigo() + "," + dto.getNombre() + "] al usuario [" + usuario + "]. ", e);
            return;
        }

        listaPerfilesDisponibles.add(dto);
        listaPerfilesSeleccionados.remove(posicion);
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    public void deseleccionarPerfil() {
        log.log(Level.INFO, "Deseleccionando perfil [{0}]", perfilQuitar);
        perfilQuitar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("perfil"));
        log.log(Level.INFO, "Se selecciono el perfil con id [{0}], para quitar", perfilQuitar);

        quitarPerfil();
    }

    public void seleccionarUsuario() {
        usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        log.log(Level.INFO, "Usuario seleccionado [{0}]", usuario);
        cargarUsuario();
        mostrarPanelResultados = false;
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
