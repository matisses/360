package co.matisses.web.mbean.inventario;

import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.BitacoraInventarioUbicacion;
import co.matisses.persistence.web.entity.ConteoTienda;
import co.matisses.persistence.web.entity.TipoConteo;
import co.matisses.persistence.web.facade.BitacoraInventarioUbicacionFacade;
import co.matisses.persistence.web.facade.ConteoTiendaFacade;
import co.matisses.persistence.web.facade.TipoConteoFacade;
import co.matisses.web.dto.UbicacionesConteosDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "crearConteoMBean")
public class CrearConteoMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    private static final Logger CONSOLE = Logger.getLogger(CrearConteoMBean.class.getSimpleName());
    private String tipoConteo;
    private String conteo;
    private String parametroBusqueda;
    private String proveedor;
    private boolean ventas = false;
    private boolean clientes = false;
    private boolean dotacion = false;
    private boolean crearTodos = false;
    private List<String> proveedores;
    private List<UbicacionesConteosDTO> ubicaciones;
    private List<UbicacionesConteosDTO> ubicacionesFull;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private TipoConteoFacade tipoConteoFacade;
    @EJB
    private ConteoTiendaFacade conteoTiendaFacade;
    @EJB
    private BitacoraInventarioUbicacionFacade bitacoraFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    public CrearConteoMBean() {
        proveedores = new ArrayList<>();
        ubicaciones = new ArrayList<>();
        ubicacionesFull = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerProveedores();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public String getTipoConteo() {
        return tipoConteo;
    }

    public void setTipoConteo(String tipoConteo) {
        this.tipoConteo = tipoConteo;
    }

    public String getConteo() {
        return conteo;
    }

    public void setConteo(String conteo) {
        this.conteo = conteo;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public boolean isVentas() {
        return ventas;
    }

    public void setVentas(boolean ventas) {
        this.ventas = ventas;
    }

    public boolean isClientes() {
        return clientes;
    }

    public void setClientes(boolean clientes) {
        this.clientes = clientes;
    }

    public boolean isDotacion() {
        return dotacion;
    }

    public void setDotacion(boolean dotacion) {
        this.dotacion = dotacion;
    }

    public boolean isCrearTodos() {
        return crearTodos;
    }

    public void setCrearTodos(boolean crearTodos) {
        this.crearTodos = crearTodos;
    }

    public List<String> getProveedores() {
        return proveedores;
    }

    public void setProveedores(List<String> proveedores) {
        this.proveedores = proveedores;
    }

    public List<UbicacionesConteosDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<UbicacionesConteosDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    private void obtenerProveedores() {
        proveedores = saldoItemInventarioFacade.obtenerProveedores();
    }

    public void seleccionarTipoConteo() {
        String tmpTipoConteo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoConteo");

        if (tmpTipoConteo == null || tmpTipoConteo.isEmpty()) {
            mostrarMensaje("Error", "No se a seleccionado un tipo conteo", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se a seleccionado un tipo conteo");
            return;
        } else if (tipoConteo == null || !tmpTipoConteo.equals(tipoConteo)) {
            tipoConteo = tmpTipoConteo;
            CONSOLE.log(Level.INFO, "Se selecciono el tipo de conteo [{0}]", tipoConteo);
            if (tipoConteo.equals("ubicacion")) {
                obtenerUbicacionesAlmacen();
            }
        } else {
            CONSOLE.log(Level.INFO, "Se descarto la seleccion del tipo de conteo [{0}]", tipoConteo);
            tipoConteo = null;
        }
    }

    public void seleccionarConteo() {
        String tmpConteo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("conteo");

        if (tmpConteo == null || tmpConteo.isEmpty()) {
            mostrarMensaje("Error", "No se a seleccionado un conteo", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se a seleccionado un conteo");
            return;
        } else if (conteo == null || !tmpConteo.equals(conteo)) {
            conteo = tmpConteo;
            CONSOLE.log(Level.INFO, "Se selecciono el conteo [{0}]", conteo);
        } else {
            CONSOLE.log(Level.INFO, "Se descarto la seleccion del conteo [{0}]", conteo);
            conteo = null;
        }
    }

    public void seleccionarTipoMercancia() {
        String tipoMercancia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoMercancia");

        if (tipoMercancia == null || tipoMercancia.isEmpty()) {
            mostrarMensaje("Error", "No se a seleccionado un tipo de mercancía", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se a seleccionado un tipo de mercancia");
            return;
        } else if (tipoMercancia.equals("ventas") && !ventas) {
            CONSOLE.log(Level.INFO, "Se selecciono el tipo mercancia [{0}]", tipoMercancia);
            ventas = true;
        } else if (tipoMercancia.equals("ventas") && ventas) {
            CONSOLE.log(Level.INFO, "Se descarto la seleccion del tipo mercancia [{0}]", tipoMercancia);
            ventas = false;
        } else if (tipoMercancia.equals("clientes") && !clientes) {
            CONSOLE.log(Level.INFO, "Se selecciono el tipo mercancia [{0}]", tipoMercancia);
            clientes = true;
        } else if (tipoMercancia.equals("clientes") && clientes) {
            CONSOLE.log(Level.INFO, "Se descarto la seleccion del tipo mercancia [{0}]", tipoMercancia);
            clientes = false;
        } else if (tipoMercancia.equals("dotacion") && !dotacion) {
            CONSOLE.log(Level.INFO, "Se selecciono el tipo mercancia [{0}]", tipoMercancia);
            dotacion = true;
        } else if (tipoMercancia.equals("dotacion") && dotacion) {
            CONSOLE.log(Level.INFO, "Se descarto la seleccion del tipo mercancia [{0}]", tipoMercancia);
            dotacion = false;
        }
    }

    public void seleccionarTodasUbicaciones() {
        if (crearTodos) {
            crearTodos = false;
        } else {
            crearTodos = true;
        }
    }

    private void obtenerUbicacionesAlmacen() {
        ubicaciones = new ArrayList<>();

        List<UbicacionSAP> ubis = ubicacionSAPFacade.obtenerUbicacionesAlmacen(sessionMBean.getAlmacen());

        if (ubis != null && !ubis.isEmpty()) {
            for (UbicacionSAP u : ubis) {
                ubicaciones.add(new UbicacionesConteosDTO(u.getAbsEntry(), u.getSL1Code() + (u.getSL2Code() != null ? u.getSL2Code() : "")));
            }

            ubicacionesFull = new ArrayList<>(ubicaciones);
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ubicaciones para el almacén en el que inicio sesión", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ubicaciones para el almacen en el que inicio sesion");
            return;
        }
    }

    public void buscarUbicacion() {
        CONSOLE.log(Level.INFO, "Buscando ubicacion para crear conteo. Parametro de busqueda [{0}]", parametroBusqueda);
        ubicaciones.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            for (UbicacionesConteosDTO u : ubicacionesFull) {
                if (u.getUbicacion().contains(parametroBusqueda.toUpperCase())) {
                    ubicaciones.add(u);
                }
            }
        } else {
            ubicaciones = new ArrayList<>(ubicacionesFull);
        }
    }

    public void seleccionarUbicacion() {
        Integer absEntry = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("absEntry"));

        if (absEntry != null && absEntry != 0) {
            for (UbicacionesConteosDTO u : ubicaciones) {
                if (u.getAbsEntry().equals(absEntry)) {
                    if (u.isElegida()) {
                        CONSOLE.log(Level.INFO, "Se descarto la seleccion de la ubicacion [{0}], para conteo de inventario", u.getUbicacion());
                        u.setElegida(false);
                    } else {
                        CONSOLE.log(Level.INFO, "Se selecciono la ubicacion [{0}], para conteo de inventario", u.getUbicacion());
                        u.setElegida(true);
                    }
                    break;
                }
            }
        }
    }

    public void crearInventarios() {
        if (tipoConteo == null || tipoConteo.isEmpty()) {
            mostrarMensaje("Error", "No se ha seleccionado un tipo conteo", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se ha seleccionado un tipo conteo");
            return;
        }
        if (conteo == null || conteo.isEmpty()) {
            mostrarMensaje("Error", "No se ha seleccionado un conteo", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se ha seleccionado un conteo");
            return;
        }
        if (!ventas && !clientes && !dotacion) {
            mostrarMensaje("Error", "No se ha seleccionado un tipo de mercancía", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se ha seleccionado un tipo de mercancia");
            return;
        }
        if (conteo.equals("Proveedor") && (proveedor == null || proveedor.isEmpty())) {
            mostrarMensaje("Error", "Debe seleccionar un proveedor.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar un proveedor");
            return;
        }

        CONSOLE.log(Level.INFO, "Creando nuevos conteos en la tienda [{0}]", sessionMBean.getAlmacen());
        TipoConteo typeCounting = tipoConteoFacade.obtenerTipoConteo(conteo);

        if (typeCounting != null && typeCounting.getIdTipoConteo() != null && typeCounting.getIdTipoConteo() != 0) {
            if (tipoConteo.equals("ubicacion")) {
                boolean ubicacionSeleccionado = false;
                for (UbicacionesConteosDTO u : ubicaciones) {
                    if (u.isElegida()) {
                        crearConteo(typeCounting, u.getUbicacion());
                        ubicacionSeleccionado = true;
                    } else if (crearTodos) {
                        crearConteo(typeCounting, u.getUbicacion());
                        ubicacionSeleccionado = true;
                    }
                }
                if (!ubicacionSeleccionado) {
                    mostrarMensaje("Error", "No se a seleccionado una ubicación para crear el conteo", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se a seleccionado una ubicacion para crear el conteo");
                    return;
                }
            } else {
                crearConteo(typeCounting, null);
            }
            limpiarDatos();
            mostrarMensaje("Éxito", "Se crearon las ubicaciones correctamente", false, true, false);
        } else {
            mostrarMensaje("Error", "No se encontraron datos para continuar", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontro el tipo de conteo que el usuario esta solicitando");
            return;
        }
    }

    private void crearConteo(TipoConteo typeCounting, String ubicacion) {
        ConteoTienda counting = new ConteoTienda();

        counting.setEstado("P");
        counting.setFecha(new Date());
        counting.setIdTipoConteo(typeCounting);
        counting.setPda(false);
        counting.setTienda(sessionMBean.getAlmacen());
        counting.setUsuario(sessionMBean.getUsuario());

        if (clientes) {
            counting.setCliente(clientes);
        } else {
            counting.setCliente(false);
        }
        if (ventas) {
            counting.setVenta(ventas);
        } else {
            counting.setVenta(false);
        }
        if (dotacion) {
            counting.setDotacion(ventas);
        } else {
            counting.setDotacion(false);
        }
        if (tipoConteo.equals("ubicacion") && ubicacion != null && !ubicacion.isEmpty()) {
            counting.setUbicacion(ubicacion);
            counting.setCasilla(true);
        } else {
            counting.setUbicacion(null);
            counting.setCasilla(false);
        }
        if (conteo.equals("Proveedor")) {
            counting.setProveedor(proveedor);
        }

        try {
            conteoTiendaFacade.create(counting);
            CONSOLE.log(Level.INFO, "Se creo conteo con id [{0}]", counting.getIdConteo());
            //crea registros correspondientes en la bitacora de conteos de ubicaciones
            if (counting.getCasilla()) {
                if (counting.getVenta()) {
                    BitacoraInventarioUbicacion bitacora = new BitacoraInventarioUbicacion();
                    StringBuilder sb = new StringBuilder();
                    sb.append(counting.getTienda());
                    bitacora.setWhsCode(sb.toString());
                    sb.append(counting.getUbicacion());
                    bitacora.setBinCode(sb.toString());
                    bitacora.setFechaCreacionConteo(new Date());
                    bitacora.setUsuarioCreador(sessionMBean.getUsuario());
                    try {
                        bitacoraFacade.create(bitacora);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "No se pudo crear el registro en la bitacora de inventario por ubicacion para la ubicacion " + sb.toString() + ". ", e);
                    }
                }
                if (counting.getDotacion()) {
                    BitacoraInventarioUbicacion bitacora = new BitacoraInventarioUbicacion();
                    StringBuilder sb = new StringBuilder();
                    sb.append("MU");
                    sb.append(counting.getTienda());
                    bitacora.setWhsCode(sb.toString());
                    sb.append(counting.getUbicacion());
                    bitacora.setBinCode(sb.toString());
                    bitacora.setFechaCreacionConteo(new Date());
                    bitacora.setUsuarioCreador(sessionMBean.getUsuario());
                    try {
                        bitacoraFacade.create(bitacora);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "No se pudo crear el registro en la bitacora de inventario por ubicacion para la ubicacion " + sb.toString() + ". ", e);
                    }
                }
                if (counting.getCliente()) {
                    BitacoraInventarioUbicacion bitacora = new BitacoraInventarioUbicacion();
                    StringBuilder sb = new StringBuilder();
                    sb.append("CL");
                    sb.append(counting.getTienda());
                    bitacora.setWhsCode(sb.toString());
                    sb.append(counting.getUbicacion());
                    bitacora.setBinCode(sb.toString());
                    bitacora.setFechaCreacionConteo(new Date());
                    bitacora.setUsuarioCreador(sessionMBean.getUsuario());
                    try {
                        bitacoraFacade.create(bitacora);
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "No se pudo crear el registro en la bitacora de inventario por ubicacion para la ubicacion " + sb.toString() + ". ", e);
                    }
                }
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Error al crear el conteo. Error [{0}]", e.getMessage());
            return;
        }
    }

    public void limpiarDatos() {
        tipoConteo = null;
        conteo = null;
        parametroBusqueda = null;
        ventas = false;
        clientes = false;
        dotacion = false;
        crearTodos = false;
        ubicaciones = new ArrayList<>();
        ubicacionesFull = new ArrayList<>();
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
