package co.matisses.web.mbean.sistema.seguridad;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.BwsAccionObjeto;
import co.matisses.persistence.web.entity.BwsPerfil;
import co.matisses.persistence.web.entity.BwsPerfilAccionObjeto;
import co.matisses.persistence.web.entity.BwsPerfilUsuario;
import co.matisses.persistence.web.entity.BwsUsuario;
import co.matisses.persistence.web.facade.BwsAccionObjetoFacade;
import co.matisses.persistence.web.facade.BwsPerfilAccionObjetoFacade;
import co.matisses.persistence.web.facade.BwsPerfilFacade;
import co.matisses.persistence.web.facade.BwsPerfilUsuarioFacade;
import co.matisses.persistence.web.facade.BwsUsuarioFacade;
import co.matisses.web.dto.AccionObjetoBWSDTO;
import co.matisses.web.dto.PerfilBWSDTO;
import co.matisses.web.ldap.BaruLDAPAuth;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.File;
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
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "autorizacionMBean")
public class AutorizacionMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(AutorizacionMBean.class.getSimpleName());
    private Integer idPerfilSeleccionado;
    private Integer accionAgregar;
    private Integer accionQuitar;
    private String filtroDisponibles;
    private String filtroSeleccionados;
    private String nombrePerfil;
    private String usuarioAgregar;
    private String usuarioQuitar;
    private String tipoUsuario = "e";
    private String documentoUsuario;
    private String nombreMostrar;
    private boolean btnCrearHabilitado = true;
    private boolean mostrarPanelResultados = false;
    private boolean dlgUsuario = false;
    private List<String> usuarios;
    private List<String> usuariosSeleccionados;
    private List<String> listaEmpleadosActivos;
    private List<String> listaExternosActivos;
    private List<String> listaEmpleadosSeleccionados;
    private List<String> listaExternosSeleccionados;
    private List<AccionObjetoBWSDTO> listaAcciones;
    private List<AccionObjetoBWSDTO> listaAccionesSeleccionadas;
    private List<AccionObjetoBWSDTO> accionesDisponibles;
    private List<AccionObjetoBWSDTO> accionesUsadas;
    private List<PerfilBWSDTO> resultadoBusqueda;
    @EJB
    private BaruLDAPAuth ldapManager;
    @EJB
    private BwsAccionObjetoFacade accionFacade;
    @EJB
    private BwsPerfilFacade perfilFacade;
    @EJB
    private BwsPerfilUsuarioFacade perfilUsuarioFacade;
    @EJB
    private BwsUsuarioFacade usuarioFacade;
    @EJB
    private BwsPerfilAccionObjetoFacade perfilAccionObjetoFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;

    public AutorizacionMBean() {
        listaEmpleadosActivos = new ArrayList<>();
        listaEmpleadosSeleccionados = new ArrayList<>();
        listaExternosActivos = new ArrayList<>();
        listaExternosSeleccionados = new ArrayList<>();
        listaAcciones = new ArrayList<>();
        accionesDisponibles = new ArrayList<>();
        listaAccionesSeleccionadas = new ArrayList<>();
        accionesUsadas = new ArrayList<>();
        resultadoBusqueda = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarListaEmpleados();
        cargarListaExternos();
        mostrarListaEmpleados();
        cargarListaAcciones();
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
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

    public String getUsuarioAgregar() {
        return usuarioAgregar;
    }

    public void setUsuarioAgregar(String usuarioAgregar) {
        this.usuarioAgregar = usuarioAgregar;
    }

    public String getUsuarioAgregarSeleccionado() {
        if (usuarioAgregar != null && !usuarioAgregar.isEmpty()) {
            return usuarioAgregar;
        }
        return "Seleccione";
    }

    public String getUsuarioQuitar() {
        return usuarioQuitar;
    }

    public void setUsuarioQuitar(String usuarioQuitar) {
        this.usuarioQuitar = usuarioQuitar;
    }

    public Integer getAccionAgregar() {
        return accionAgregar;
    }

    public void setAccionAgregar(Integer accionAgregar) {
        this.accionAgregar = accionAgregar;
    }

    public String getAccionAgregarSeleccionada() {
        if (accionAgregar != null && accionAgregar != 0) {
            for (AccionObjetoBWSDTO a : accionesDisponibles) {
                if (a.getIdAccionObjeto().equals(accionAgregar)) {
                    return a.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public Integer getAccionQuitar() {
        return accionQuitar;
    }

    public void setAccionQuitar(Integer accionQuitar) {
        this.accionQuitar = accionQuitar;
    }

    public String getAccionQuitarSeleccionada() {
        if (accionQuitar != null && accionQuitar != 0) {
            for (AccionObjetoBWSDTO a : accionesUsadas) {
                if (a.getIdAccionObjeto().equals(accionQuitar)) {
                    return a.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public List<String> getListaEmpleadosActivos() {
        return listaEmpleadosActivos;
    }

    public void setListaEmpleadosActivos(List<String> listaEmpleadosActivos) {
        this.listaEmpleadosActivos = listaEmpleadosActivos;
    }

    public List<String> getListaEmpleadosSeleccionados() {
        return listaEmpleadosSeleccionados;
    }

    public void setListaEmpleadosSeleccionados(List<String> listaEmpleadosSeleccionados) {
        this.listaEmpleadosSeleccionados = listaEmpleadosSeleccionados;
    }

    public List<String> getListaExternosSeleccionados() {
        return listaExternosSeleccionados;
    }

    public void setListaExternosSeleccionados(List<String> listaExternosSeleccionados) {
        this.listaExternosSeleccionados = listaExternosSeleccionados;
    }

    public List<AccionObjetoBWSDTO> getListaAcciones() {
        return listaAcciones;
    }

    public void setListaAcciones(List<AccionObjetoBWSDTO> listaAcciones) {
        this.listaAcciones = listaAcciones;
    }

    public List<AccionObjetoBWSDTO> getListaAccionesSeleccionadas() {
        return listaAccionesSeleccionadas;
    }

    public void setListaAccionesSeleccionadas(List<AccionObjetoBWSDTO> listaAccionesSeleccionadas) {
        this.listaAccionesSeleccionadas = listaAccionesSeleccionadas;
    }

    public List<AccionObjetoBWSDTO> getAccionesDisponibles() {
        return accionesDisponibles;
    }

    public void setAccionesDisponibles(List<AccionObjetoBWSDTO> accionesDisponibles) {
        this.accionesDisponibles = accionesDisponibles;
    }

    public List<AccionObjetoBWSDTO> getAccionesUsadas() {
        return accionesUsadas;
    }

    public void setAccionesUsadas(List<AccionObjetoBWSDTO> accionesUsadas) {
        this.accionesUsadas = accionesUsadas;
    }

    public List<PerfilBWSDTO> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(List<PerfilBWSDTO> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    public List<String> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<String> usuarios) {
        this.usuarios = usuarios;
    }

    public Integer getIdPerfilSeleccionado() {
        return idPerfilSeleccionado;
    }

    public void setIdPerfilSeleccionado(Integer idPerfilSeleccionado) {
        this.idPerfilSeleccionado = idPerfilSeleccionado;
    }

    public List<String> getUsuariosSeleccionados() {
        return usuariosSeleccionados;
    }

    public void setUsuariosSeleccionados(List<String> usuariosSeleccionados) {
        this.usuariosSeleccionados = usuariosSeleccionados;
    }

    public List<String> getListaExternosActivos() {
        return listaExternosActivos;
    }

    public void setListaExternosActivos(List<String> listaExternosActivos) {
        this.listaExternosActivos = listaExternosActivos;
    }

    public String getTipoUsuario() {
        return tipoUsuario;
    }

    public String getTipoSeleccionado() {
        switch (tipoUsuario) {
            case "e":
                return "Empleados";
            case "x":
                return "Externos";
            default:
                return "Seleccione";
        }
    }

    public boolean isMostrarPanelResultados() {
        return mostrarPanelResultados;
    }

    public void setMostrarPanelResultados(boolean mostrarPanelResultados) {
        this.mostrarPanelResultados = mostrarPanelResultados;
    }

    public boolean isBtnCrearHabilitado() {
        return btnCrearHabilitado;
    }

    public void setBtnCrearHabilitado(boolean btnCrearHabilitado) {
        this.btnCrearHabilitado = btnCrearHabilitado;
    }

    public boolean isDlgUsuario() {
        return dlgUsuario;
    }

    public void setDlgUsuario(boolean dlgUsuario) {
        this.dlgUsuario = dlgUsuario;
    }

    public String getDocumentoUsuario() {
        return documentoUsuario;
    }

    public void setDocumentoUsuario(String documentoUsuario) {
        this.documentoUsuario = documentoUsuario;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    private void cargarListaEmpleados() {
        listaEmpleadosActivos = ldapManager.listEmployees();
        log.log(Level.INFO, "Se cargaron [{0}] empleados", listaEmpleadosActivos.size());
    }

    private void cargarListaExternos() {
        listaExternosActivos = ldapManager.listOtherUsers();
        log.log(Level.INFO, "Se cargaron [{0}] usuarios externos", listaExternosActivos.size());
    }

    private void mostrarListaEmpleados() {
        usuarios = new ArrayList<>();
        usuariosSeleccionados = new ArrayList<>();
        for (String usuario : listaEmpleadosActivos) {
            if (!listaEmpleadosSeleccionados.contains(usuario)) {
                usuarios.add(usuario);
            } else {
                usuariosSeleccionados.add(usuario);
            }
        }
        Collections.sort(usuarios);
        Collections.sort(usuariosSeleccionados);
    }

    private void mostrarListaExternos() {
        usuarios = new ArrayList<>();
        usuariosSeleccionados = new ArrayList<>();
        for (String usuario : listaExternosActivos) {
            if (!listaExternosSeleccionados.contains(usuario)) {
                usuarios.add(usuario);
            } else {
                usuariosSeleccionados.add(usuario);
            }
        }
        Collections.sort(usuarios);
        Collections.sort(usuariosSeleccionados);
    }

    private void cargarListaAcciones() {
        listaAcciones = new ArrayList<>();
        listaAccionesSeleccionadas = new ArrayList<>();
        List<BwsAccionObjeto> entidades = accionFacade.findAll();
        for (BwsAccionObjeto entidad : entidades) {
            listaAcciones.add(new AccionObjetoBWSDTO(entidad.getIdAccionObjeto(), entidad.getNombre(), entidad.getCodigoObjeto().getCodigo(), entidad.getCodigoAccion().getCodigo()));
        }
        Collections.sort(listaAcciones);
        Collections.sort(listaAccionesSeleccionadas);
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    public void cambiarTipoUsuario() {
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoUsuario");
        log.log(Level.INFO, "Mostrando usuarios tipo [{0}]", tipoUsuario);

        tipoUsuario = tipo;
        if (tipoUsuario.equals("e")) {
            mostrarListaEmpleados();
        } else {
            mostrarListaExternos();
        }
    }

    public void seleccionarUsuarioVersionMovil() {
        usuarioAgregar = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        log.log(Level.INFO, "Agregando usuario [{0}] a lista tipo [{1}] del perfil [{2}]", new Object[]{usuarioAgregar, tipoUsuario, idPerfilSeleccionado});
    }

    public void agregarUsuario() {
        dlgUsuario = false;
        BwsPerfil entidadPerfil = perfilFacade.find(idPerfilSeleccionado);
        BwsUsuario entidadUsuario = usuarioFacade.find(usuarioAgregar);
        if (entidadUsuario == null) {
            dlgUsuario = true;
            log.log(Level.SEVERE, "No se puede seleccionar un usuario que no se ha registrado en el sistema");
            mostrarMensaje("Error", "El usuario seleccionado no ha sido registrado en el sistema. Regístrelo antes de continuar.", true, false, false);
            return;
        }
        BwsPerfilUsuario entidad = new BwsPerfilUsuario();
        entidad.setCodigoPerfil(entidadPerfil);
        entidad.setUsuario(entidadUsuario);

        try {
            perfilUsuarioFacade.create(entidad);
            log.log(Level.INFO, "Se agrego el usuario [{0}], al perfil con id [{1}]", new Object[]{usuarioAgregar, idPerfilSeleccionado});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al agregar el usuario [{0}] al perfil [{1}], [{2}]. Error [{3}]", new Object[]{usuarioAgregar, idPerfilSeleccionado, nombrePerfil, e});
            mostrarMensaje("Error", "Ocurrió un error al agregar el usuario al perfil. " + e.getMessage(), true, false, false);
            return;
        }

        //realizar cambios en las listas de la vista
        if (tipoUsuario.equals("e")) {
            listaEmpleadosSeleccionados.add(usuarioAgregar);
            mostrarListaEmpleados();
        } else {
            listaExternosSeleccionados.add(usuarioAgregar);
            mostrarListaExternos();
        }
        usuarioQuitar = usuarioAgregar;
        usuarioAgregar = null;
    }

    public void seleccionarUsuario() {
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        log.log(Level.INFO, "Agregando usuario [{0}] a lista tipo [{1}] del perfil [{2}]", new Object[]{usuario, tipoUsuario, idPerfilSeleccionado});

        if (usuario != null && !usuario.isEmpty()) {
            usuarioAgregar = usuario;

            //validar errores
            if (usuarioAgregar == null) {
                log.log(Level.WARNING, "Debe seleccionar un usuario antes de continuar");
                mostrarMensaje("Error", "Debe seleccionar un usuario para continuar.", true, false, false);
                return;
            }
            if (idPerfilSeleccionado == null) {
                log.log(Level.WARNING, "Debe seleccionar un perfil antes de continuar");
                mostrarMensaje("Error", "Debe seleccionar un perfil antes de continuar.", true, false, false);
                usuarioAgregar = null;
                return;
            }

            agregarUsuario();
        } else {
            log.log(Level.SEVERE, "Debe seleccionar un usuario para poder agregar.");
            mostrarMensaje("Error", "Debe seleccionar un usuario para poder agregar.", true, false, false);
            return;
        }
    }

    public String getObtenerImagenEmpleado() {
        String urlWeb = applicationMBean.obtenerValorPropiedad("baru.fotos.empleados");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [baru.fotos.empleados] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("url.web.noimage");
        }
        urlWeb = String.format(urlWeb, documentoUsuario);

        String url = applicationMBean.obtenerValorPropiedad("local.fotos.empleados");
        if (url != null) {
            url = String.format(url, documentoUsuario);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de local.fotos.empleados] en baru.properties");
            return "";
        }

        File f = new File(url);
        if (f.exists()) {
            log.log(Level.INFO, "Retornando la ruta de la imagen [{0}]", documentoUsuario);
            return urlWeb;
        } else {
            log.log(Level.WARNING, "El usuario [{0}] no tiene imagen ", documentoUsuario);
            return "";
        }
    }

    public void crearUsuario() {
        dlgUsuario = false;
        Integer codVentas;
        /*Se obtiene el codigo de ventas del usuario*/
        Empleado empleado = empleadoFacade.obtenerEmpleadoDocumento(documentoUsuario);

        if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
            codVentas = empleado.getSalesPrson();
        } else {
            mostrarMensaje("Error", "No se pudieron encontrar datos del usuario.", true, false, false);
            log.log(Level.SEVERE, "No se pudieron encontrar datos del usuario");
            return;
        }

        BwsUsuario bwsUsuario = usuarioFacade.obtenerUsuarioCodigo(codVentas);
        if (bwsUsuario != null && bwsUsuario.getUsuario() != null && !bwsUsuario.getUsuario().isEmpty()) {
            mostrarMensaje("Error", "No se puede crear el usuario, debido a que el id de vendedor ya fue asignado a otro usuario.", true, false, false);
            log.log(Level.SEVERE, "No se puede crear el usuario, debido a que el id de vendedor ya fue asignado a otro usuario");
            return;
        }

        BwsUsuario newUsuario = new BwsUsuario();

        newUsuario.setIdVendedor(codVentas);
        newUsuario.setNombreMostrar(nombreMostrar != null ? nombreMostrar : usuarioAgregar);
        newUsuario.setUsuario(usuarioAgregar);

        try {
            usuarioFacade.create(newUsuario);
            log.log(Level.INFO, "Se creo el usuario correctamente");
            agregarUsuario();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error al crear el usuario.", e);
            mostrarMensaje("Error", "El usuario no se pudo crear, intentelo nuevamente.", true, false, false);
            return;
        }
    }

    public void deseleccionarUsuario() {
        String usuario = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("usuario");
        log.log(Level.INFO, "Quitando usuario [{0}] de lista tipo [{1}] del perfil [{2}]", new Object[]{usuario, tipoUsuario, idPerfilSeleccionado});

        if (usuario != null && !usuario.isEmpty()) {
            usuarioQuitar = usuario;
            //validar errores
            if (usuarioQuitar == null) {
                log.log(Level.SEVERE, "Debe seleccionar un usuario para continuar");
                mostrarMensaje("Error", "Debe seleccionar un usuario para continuar.", true, false, false);
                return;
            }
            if (idPerfilSeleccionado == null) {
                log.log(Level.SEVERE, "Debe seleccionar un perfil antes de continuar");
                mostrarMensaje("Error", "Debe seleccionar un perfil antes de continuar.", true, false, false);
                return;
            }

            //quitar el registro de la base de datos
            try {
                perfilUsuarioFacade.eliminarPorUsuarioPerfil(usuarioQuitar, idPerfilSeleccionado);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al agregar el usuario [{0}] al perfil [{1}], [{2}]. Error [{3}]", new Object[]{usuarioAgregar, idPerfilSeleccionado, nombrePerfil, e});
                mostrarMensaje("Error", "Ocurrió un error al agregar el usuario al perfil.", true, false, false);
                return;
            }

            //realizar cambios en las listas de la vista
            if (tipoUsuario.equals("e")) {
                listaEmpleadosSeleccionados.remove(usuarioQuitar);
                mostrarListaEmpleados();
            } else {
                listaExternosSeleccionados.remove(usuarioQuitar);
                mostrarListaExternos();
            }
            usuarioAgregar = usuarioQuitar;
            usuarioQuitar = null;
        } else {
            log.log(Level.SEVERE, "Debe seleccionar un usuario para poder quitar.");
            mostrarMensaje("Error", "Debe seleccionar un usuario para poder quitar.", true, false, false);
            return;
        }
    }

    private void agregarAccion(Integer idAccion) {
        int pos = listaAcciones.indexOf(new AccionObjetoBWSDTO(idAccion, null, null, null));
        AccionObjetoBWSDTO seleccionado = listaAcciones.get(pos);
        if (listaAccionesSeleccionadas.size() == 1 && listaAccionesSeleccionadas.get(0).getIdAccionObjeto() == null) {
            listaAccionesSeleccionadas.clear();
        }
        listaAccionesSeleccionadas.add(seleccionado);
        listaAcciones.remove(pos);
        if (listaAcciones.isEmpty()) {
            listaAcciones.add(new AccionObjetoBWSDTO(null, "No hay acciones disponibles", null, null));
        }
        Collections.sort(listaAcciones);
        Collections.sort(listaAccionesSeleccionadas);
    }

    public void seleccionarAccionAgregar() {
        accionAgregar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoAccion"));
        log.log(Level.INFO, "Se selecciono la accion a agregar con id [{0}]", accionAgregar);
    }

    public void agregarAccion() {
        BwsAccionObjeto entidadAccionObjeto = accionFacade.find(accionAgregar);
        BwsPerfil entidadPerfil = perfilFacade.find(idPerfilSeleccionado);

        BwsPerfilAccionObjeto entidadPerfilAccion = new BwsPerfilAccionObjeto();
        entidadPerfilAccion.setCodigoPerfil(entidadPerfil);
        entidadPerfilAccion.setIdAccionObjeto(entidadAccionObjeto);

        try {
            perfilAccionObjetoFacade.create(entidadPerfilAccion);
            log.log(Level.INFO, "Se agrego la accion con id [{0}], al perfil con id [{1}]", new Object[]{accionAgregar, idPerfilSeleccionado});
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error inesperado agregando la acción al perfil. " + e.getMessage(), true, false, false);
            log.log(Level.SEVERE, "Ocurrió un error inesperado agregando la acción al perfil. ", e);
            return;
        }

        agregarAccion(accionAgregar);
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    public void seleccionarAccion() {
        Integer codigoAccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoAccion"));

        if (codigoAccion != null && codigoAccion != 0) {
            log.log(Level.INFO, "Agregando accion [{0}]", codigoAccion);
            accionAgregar = codigoAccion;
            agregarAccion();
        } else {
            log.log(Level.SEVERE, "Seleccione una accion para poder agregar");
            mostrarMensaje("Error", "Seleccione una acción para poder agregar.", true, false, false);
            return;
        }
    }

    public void seleccionarAccionQuitar() {
        accionQuitar = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoAccion"));
        log.log(Level.INFO, "Se selecciono la accion a quitar con id [{0}]", accionQuitar);
    }

    public void quitarAccion() {
        try {
            perfilAccionObjetoFacade.eliminar(accionQuitar, idPerfilSeleccionado);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrió un error al retirar la accion de la lista.", e);
            mostrarMensaje("Error", "Ocurrió un error al retirar la accion de la lista. " + e.getMessage(), true, false, false);
            return;
        }

        int pos = listaAccionesSeleccionadas.indexOf(new AccionObjetoBWSDTO(accionQuitar, null, null, null));
        AccionObjetoBWSDTO seleccionado = listaAccionesSeleccionadas.get(pos);
        if (listaAcciones.size() == 1 && listaAcciones.get(0).getIdAccionObjeto() == null) {
            listaAcciones.clear();
        }
        listaAcciones.add(seleccionado);
        listaAccionesSeleccionadas.remove(pos);
        if (listaAccionesSeleccionadas.isEmpty()) {
            listaAccionesSeleccionadas.add(new AccionObjetoBWSDTO(null, "Seleccione una acción", null, null));
        }
        Collections.sort(listaAcciones);
        Collections.sort(listaAccionesSeleccionadas);
        filtrarListaDisponibles();
        filtrarListaSeleccionados();
    }

    public void deseleccionarAccion() {
        Integer codigoAccion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoAccion"));

        if (codigoAccion != null && codigoAccion != 0) {
            log.log(Level.INFO, "Quitando accion [{0}]", codigoAccion);
            accionQuitar = codigoAccion;
            quitarAccion();
        } else {
            log.log(Level.SEVERE, "Seleccione una accion para poder quitar");
            mostrarMensaje("Error", "Seleccione una acción para poder quitar.", true, false, false);
            return;
        }
    }

    public void buscar() {
        log.log(Level.INFO, "Buscando perfiles con filtro [{0}]", nombrePerfil);
        List<BwsPerfil> entidades = new ArrayList<>();
        if (nombrePerfil == null || nombrePerfil.equals("*") || nombrePerfil.trim().isEmpty()) {
            entidades.addAll(perfilFacade.findAll());
        } else {
            entidades.addAll(perfilFacade.buscarPorNombre(nombrePerfil));
        }
        if (entidades.isEmpty()) {
            mostrarMensaje("Error", "No se encontraron resultados para la cadena de búsqueda ingresada.", true, false, false);
            mostrarPanelResultados = false;
        } else if (entidades.size() == 1) {
            //mostrar el resultado obtenido 
            BwsPerfil entidad = entidades.get(0);
            nombrePerfil = entidad.getNombre();
            idPerfilSeleccionado = entidad.getCodigo();
            for (BwsPerfilUsuario usuario : entidad.getBwsPerfilUsuarioList()) {
                if (listaEmpleadosActivos.contains(usuario.getUsuario().getUsuario())) {
                    listaEmpleadosSeleccionados.add(usuario.getUsuario().getUsuario());
                } else if (listaExternosActivos.contains(usuario.getUsuario().getUsuario())) {
                    listaExternosSeleccionados.add(usuario.getUsuario().getUsuario());
                }
            }

            mostrarListaExternos();
            mostrarListaEmpleados();

            cargarListaAcciones();
            for (BwsPerfilAccionObjeto accionObjeto : entidad.getBwsPerfilAccionObjetoList()) {
                agregarAccion(accionObjeto.getIdAccionObjeto().getIdAccionObjeto());
            }
            filtrarListaDisponibles();
            filtrarListaSeleccionados();
            mostrarPanelResultados = false;
            btnCrearHabilitado = false;
        } else {
            //mostrar panel con los resultados obtenidos
            resultadoBusqueda = new ArrayList<>();
            for (BwsPerfil entidad : entidades) {
                PerfilBWSDTO dto = new PerfilBWSDTO(entidad.getCodigo(), entidad.getNombre());
                for (BwsPerfilUsuario perfilUsuario : entidad.getBwsPerfilUsuarioList()) {
                    dto.agregarUsuario(perfilUsuario.getUsuario().getUsuario());
                }
                for (BwsPerfilAccionObjeto accionObjeto : entidad.getBwsPerfilAccionObjetoList()) {
                    dto.agregarAccion(accionObjeto.getIdAccionObjeto().getIdAccionObjeto(),
                            accionObjeto.getIdAccionObjeto().getNombre(),
                            accionObjeto.getIdAccionObjeto().getCodigoObjeto().getCodigo(),
                            accionObjeto.getIdAccionObjeto().getCodigoAccion().getCodigo());
                }
                resultadoBusqueda.add(dto);
            }
            mostrarPanelResultados = true;
        }
    }

    public void seleccionarPerfil() {
        String codigoPerfil = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigo");
        log.log(Level.INFO, "Seleccionar perfil [{0}]", codigoPerfil);

        for (PerfilBWSDTO dto : resultadoBusqueda) {
            idPerfilSeleccionado = new Integer(codigoPerfil);
            if (dto.getCodigo().equals(idPerfilSeleccionado)) {
                nombrePerfil = dto.getNombre();

                for (String usuario : dto.getUsuarios()) {
                    if (listaEmpleadosActivos.contains(usuario)) {
                        listaEmpleadosSeleccionados.add(usuario);
                    } else if (listaExternosActivos.contains(usuario)) {
                        listaExternosSeleccionados.add(usuario);
                    }
                }

                mostrarListaExternos();
                mostrarListaEmpleados();

                for (AccionObjetoBWSDTO accion : dto.getAcciones()) {
                    agregarAccion(accion.getIdAccionObjeto());
                }
                filtrarListaDisponibles();
                filtrarListaSeleccionados();
                break;
            }
        }

        //cargarListaAcciones();
        mostrarPanelResultados = false;
        btnCrearHabilitado = false;
    }

    public void limpiar() {
        nombrePerfil = null;
        idPerfilSeleccionado = null;
        usuarioAgregar = null;
        usuarioQuitar = null;
        accionAgregar = null;
        accionQuitar = null;
        tipoUsuario = "e";
        listaEmpleadosActivos = new ArrayList<>();
        listaEmpleadosSeleccionados = new ArrayList<>();
        listaExternosActivos = new ArrayList<>();
        listaExternosSeleccionados = new ArrayList<>();
        listaAcciones = new ArrayList<>();
        listaAccionesSeleccionadas = new ArrayList<>();
        resultadoBusqueda = new ArrayList<>();
        btnCrearHabilitado = true;
        mostrarPanelResultados = false;

        cargarListaEmpleados();
        cargarListaExternos();
        mostrarListaEmpleados();
        cargarListaAcciones();
    }

    private BwsUsuario buscaryCrearUsuario(String usuario) {
        BwsUsuario usuarioEntidad = null;
        boolean crearUsuario = false;
        try {
            usuarioEntidad = usuarioFacade.find(usuario);
            if (usuarioEntidad == null) {
                crearUsuario = true;
            }
        } catch (NoResultException e) {
            crearUsuario = true;
        }
        if (crearUsuario) {
            usuarioEntidad = new BwsUsuario(usuario);
            usuarioFacade.create(usuarioEntidad);
        }

        return usuarioEntidad;
    }

    public void crear() {
        log.log(Level.INFO, "Creando perfil [{0}]", nombrePerfil);
        if (nombrePerfil == null) {
            log.log(Level.SEVERE, "Debe ingresar un nombre para el perfil");
            mostrarMensaje("Error", "Debe ingresar un nombre para el perfil.", true, false, false);
            return;
        }

        /*Validar si el nombre del permiso ya existe*/
        List<BwsPerfil> profiles = perfilFacade.buscarPorNombre(nombrePerfil);

        if (profiles != null && !profiles.isEmpty()) {
            mostrarMensaje("Error", "No se puede crear el perfil con el nombre de " + nombrePerfil + ", debido a que ya existe un perfil con este nombre.", true, false, false);
            log.log(Level.SEVERE, "No se puede crear el perfil con el nombre de [{0}], debido a que ya existe un perfil con este nombre", nombrePerfil);
            return;
        }

        BwsPerfil entidadPerfil = new BwsPerfil();
        entidadPerfil.setNombre(nombrePerfil);

        try {
            perfilFacade.create(entidadPerfil);
            log.log(Level.INFO, "Se creo el perfil con id [{0}]", entidadPerfil.getCodigo());
            idPerfilSeleccionado = entidadPerfil.getCodigo();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el perfil. ", e);
            mostrarMensaje("Error", "No se pudo crear el perfil, porque ocurrio un error inesperado.", true, false, false);
            return;
        }
    }

    public void filtrarListaDisponibles() {
        log.log(Level.INFO, "Ejecutando filtro disponibles [{0}]", filtroDisponibles);
        accionesDisponibles = new ArrayList<>();
        for (AccionObjetoBWSDTO dto : listaAcciones) {
            if (filtroDisponibles == null || dto.getNombre().toLowerCase().contains(filtroDisponibles.toLowerCase())) {
                accionesDisponibles.add(dto);
            }
        }
        Collections.sort(accionesDisponibles);
        if (accionesDisponibles.isEmpty()) {
            accionesDisponibles.add(new AccionObjetoBWSDTO(null, "Seleccione una acción", null, null));
        }
    }

    public void filtrarListaSeleccionados() {
        log.log(Level.INFO, "Ejecutando filtro seleccionadas [{0}]", filtroSeleccionados);
        accionesUsadas = new ArrayList<>();
        for (AccionObjetoBWSDTO dto : listaAccionesSeleccionadas) {
            if (filtroSeleccionados == null || dto.getNombre().toLowerCase().contains(filtroSeleccionados.toLowerCase())) {
                accionesUsadas.add(dto);
            }
        }
        Collections.sort(accionesUsadas);
        if (accionesUsadas.isEmpty()) {
            accionesUsadas.add(new AccionObjetoBWSDTO(null, "Seleccione una acción", null, null));
        }
    }

    public void actualizarPerfil() {
        if (idPerfilSeleccionado == null) {
            return;
        }
        BwsPerfil entidadPerfil = perfilFacade.find(idPerfilSeleccionado);
        if (entidadPerfil != null && !entidadPerfil.getNombre().equals(nombrePerfil)) {
            entidadPerfil.setNombre(nombrePerfil);
            try {
                perfilFacade.edit(entidadPerfil);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al actualizar el nombre del perfil. ", e);
                mostrarMensaje("Error", "No se pudo actualizar el nombre del perfil.", true, false, false);
                return;
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
