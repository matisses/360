package co.matisses.web.mbean;

import co.matisses.persistence.dto.AccionDTO;
import co.matisses.persistence.dto.ObjetoDTO;
import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.BwsSecurityManager;
import co.matisses.persistence.web.entity.BwsUsuario;
import co.matisses.persistence.web.facade.BwsUsuarioFacade;
import co.matisses.web.Acciones;
import co.matisses.web.ObjectUtils;
import co.matisses.web.Objetos;
import co.matisses.web.dto.SesionPOSDTO;
import co.matisses.web.ldap.BaruLDAPAuth;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author dbotero
 */
@SessionScoped
@Named(value = "userSessionInfoMBean")
public class UserSessionInfoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(UserSessionInfoMBean.class.getSimpleName());
    private boolean sesionValida;
    private String sessionId;
    private String usuario;
    private String clave;
    private String almacen;
    private String nombreMostrar;
    private String errorMessage;
    private String ip;
    private String codigoVentas;
    private String cedulaEmpleado;
    private Map<Integer, ObjetoDTO> permisos;
    @EJB
    private BwsSecurityManager securityManager;
    @EJB
    private BaruLDAPAuth ldapManager;
    @EJB
    private BwsUsuarioFacade bwsUsuarioFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @Inject
    private BaruApplicationMBean aplicationBean;

    public UserSessionInfoMBean() {
        sesionValida = false;
        obtenerIp();
    }

    protected void obtenerIp() {
        FacesContext fc = FacesContext.getCurrentInstance();
        if (fc != null) {
            HttpServletRequest httpServletRequest = (HttpServletRequest) fc.getExternalContext().getRequest();
            ip = httpServletRequest.getRemoteAddr();
        }
    }

    @PostConstruct
    protected void initialize() {
        asignarSucursalAutomatica();
    }

    protected void asignarSucursalAutomatica() {
        if (ip == null) {
            return;
        }
        String direccionIP[] = ip.split("\\.");
        if (direccionIP == null || direccionIP.length != 4) {
            log.log(Level.SEVERE, "No se puede calcular la sucursal automaticamente porque la direccion IP del usuario no es valida [{0}]", ip);
            return;
        }
        try {
            String segmento = direccionIP[2];
            almacen = aplicationBean.getIpSucursal(segmento);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar la sucursal para el segmento de red [{0}]", direccionIP[2]);
            log.severe(e.getMessage());
        }
    }

    public String getIp() {
        return ip;
    }

    public String getSessionId() {
        return sessionId;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getCodigoVentas() {
        return codigoVentas;
    }

    public void setCodigoVentas(String codigoVentas) {
        this.codigoVentas = codigoVentas;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public boolean validarPermisoUsuario(String objeto, String accion) {
        log.log(Level.INFO, "Validando si el usuario {2} puede {0} {1} ", new Object[]{accion, objeto, usuario});
        return validarPermisoUsuario(Objetos.valueOf(objeto), Acciones.valueOf(accion));
    }

    public boolean validarPermisoUsuario(Objetos objeto, Acciones accion) {
        if (permisos == null) {
            return false;
        } else {
            ObjetoDTO obj = aplicationBean.cargarObjetoPorNombre(objeto.name());
            AccionDTO acc = aplicationBean.cargarAccionPorNombre(accion.name());
            if (acc == null || obj == null) {
                return false;
            } else if (permisos.containsKey(obj.getCodigo())) {
                if (permisos.get(obj.getCodigo()).puede(acc)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String limpiarSessionId(String s) {
        if (s == null || s.trim().isEmpty()) {
            return "";
        }
        if (s.contains("; ")) {
            return s.substring(0, s.indexOf("; "));
        }
        return s;
    }

    public String iniciarSesion() {
        log.log(Level.INFO, "Intentando iniciar sesion con usr [{0}]", usuario);
        if (usuario != null && !usuario.trim().isEmpty()) {
            if (clave != null && !clave.trim().isEmpty()) {
                if (almacen != null && !almacen.isEmpty()) {
                    //Si los campos usuario, clave y almacen no estan vacios, procede a consultar 
                    //la contraseña en base de datos y luego en el directorio activo
                    @SuppressWarnings("UnusedAssignment")
                    boolean claveValida = false;
                    String claveCodificada = aplicationBean.codificarClave(clave);
                    String claveAlmacenada = securityManager.consultarClaveUsuario(usuario);
                    if (claveAlmacenada != null && claveAlmacenada.equals(claveCodificada)) {
                        claveValida = true;
                    } else {
                        claveValida = ldapManager.authenticateUser(usuario, clave);
                    }
                    if (claveValida) {
                        //Si la contraseña es valida, borra la variable para reducir riesgos de seguridad
                        clave = "";

                        //Si la contraseña es valida, carga los permisos del usuario desde base de datos
                        permisos = securityManager.cargarPermisosUsuario(usuario);

                        if (permisos != null && !permisos.isEmpty()) {
                            //Valida que el usuario tenga permiso de ingreso a la aplicacion. Este permiso debe ser explicito
                            nombreMostrar = securityManager.obtenerNombreMostrar(usuario);
                            sesionValida = true;
                            obtenerDatosUsuario();

                            SesionPOSDTO dto = new SesionPOSDTO();
                            HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                            String cookie = origRequest.getHeader("Cookie");
                            sessionId = limpiarSessionId(cookie.substring(cookie.indexOf("=") + 1));

                            dto.setSesionValida(sesionValida);
                            boolean autorizacion = validarPermisoUsuario(Objetos.FACTURA_POS, Acciones.VISUALIZAR);
                            if (!autorizacion) {
                                dto.setSesionValida(false);
                                dto.setMensajeError("El usuario [" + usuario + "] no está autorizado para ingresar a facturación POS");
                                log.log(Level.SEVERE, dto.getMensajeError());
                            } else if (!aplicationBean.terminalPOSAutorizada(ip)) {
                                dto.setSesionValida(false);
                                dto.setMensajeError("El equipo desde el que está intentando acceder (" + ip + ") no está autorizado para ingresar a facturación POS");
                                log.log(Level.SEVERE, dto.getMensajeError());
                            }

                            dto.setAlmacen(almacen);
                            dto.setUsuario(usuario);
                            dto.setIp(ip);
                            aplicationBean.agregarSesionPOS(sessionId, dto);
                            return "home";
                        } else {
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingreso no permitido", "El usuario no puede ingresar debido a la falta de permisos"));
                            log.log(Level.SEVERE, "El usuario [{0}], no puede ingresar debido a la falta de permisos", usuario);
                            return null;
                        }
                    } else {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no válido", "El usuario o la clave ingresados no son válidos"));
                    }
                    return null;
                } else {
                    mostrarAlerta("almacén");
                    log.warning("No se selecciono un almacen");
                }
            } else {
                mostrarAlerta("contraseña");
                log.warning("No se ingreso la contrasena");
            }
        } else {
            mostrarAlerta("usuario");
            log.warning("No se ingreso el usuario");
        }
        return null;
    }

    public String finalizarSesion() {
        aplicationBean.finalizarSesionPOS(sessionId);
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "login";
    }

    public void checkSessionBeforeLogin() throws IOException {
        if (sesionValida) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/index.xhtml");
        }
    }

    public void checkAlreadyLoggedin() throws IOException {
        if (!sesionValida) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + "/");
            return;
        }
        //validar permisos
        HttpServletRequest origRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String rutaSolicitada = origRequest.getRequestURI();
        log.log(Level.INFO, "[{1}] Ruta solicitada: [{0}]", new Object[]{rutaSolicitada, usuario});
    }

    public boolean isLoggedIn() {
        return sesionValida;
    }

    private void obtenerDatosUsuario() {
        if (usuario != null && !usuario.isEmpty()) {
            BwsUsuario user = bwsUsuarioFacade.find(usuario);

            if (user != null && user.getUsuario() != null && !user.getUsuario().isEmpty()) {
                codigoVentas = String.valueOf(user.getIdVendedor());

                Empleado empleado = empleadoFacade.obtenerEmpleadoCodVentas(codigoVentas);

                if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
                    cedulaEmpleado = empleado.getOfficeExt();
                } else {
                    log.log(Level.SEVERE, "No se pudo cargar la cedula del empleado logeado debido a que no se encontraron resultados con el codigo asesor encontrado");
                }
            } else {
                log.log(Level.SEVERE, "No se pudo cargar el codigo ventas, debido a que no se encontro en la tabla BWS_USUARIO");
            }
        }
    }

    private void mostrarAlerta(String nombreCampo) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Falta " + nombreCampo, "Verifique que haya ingresado un valor en el campo " + nombreCampo));
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
