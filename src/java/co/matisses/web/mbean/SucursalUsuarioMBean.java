package co.matisses.web.mbean;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.AlmacenDTO;
import java.io.File;
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
@Named(value = "sucursalUsuarioMBean")
public class SucursalUsuarioMBean implements Serializable {

    private static final Logger log = Logger.getLogger(SucursalUsuarioMBean.class.getSimpleName());
    private String parametroBusqueda;
    private List<AlmacenDTO> sucursales;
    private List<AlmacenDTO> sucursalesFiltradas;
    @EJB
    private AlmacenFacade almacenFacade;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;

    public SucursalUsuarioMBean() {
        sucursales = new ArrayList<>();
        sucursalesFiltradas = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerSucursales();
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public List<AlmacenDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<AlmacenDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public List<AlmacenDTO> getSucursalesFiltradas() {
        return sucursalesFiltradas;
    }

    public void setSucursalesFiltradas(List<AlmacenDTO> sucursalesFiltradas) {
        this.sucursalesFiltradas = sucursalesFiltradas;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    private void obtenerSucursales() {
        sucursales.clear();
        List<Almacen> almacenes = new ArrayList<>();
        if (sessionMBean.validarPermisoUsuario(Objetos.SUCURSALES_TODAS, Acciones.VISUALIZAR)) {
            almacenes = almacenFacade.obtenerAlmacenesBaru();
        } else {
            almacenes = almacenFacade.cargarTiendas(true);
        }

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen almacen : almacenes) {
                sucursales.add(new AlmacenDTO(almacen.getIntrnalKey(), almacen.getWhsCode(), almacen.getWhsName(),
                        almacen.getUNombreReportes(), almacen.getUvisualizar(), almacen.getUVelocidad(),
                        almacen.getUPrioridad(), almacen.getUnombrextablet(), almacen.getEstado(),
                        almacen.getUPlacaVehiculo(), almacen.getUCodigoVentas(), almacen.getCodigoCiudad(),
                        almacen.getCity(), almacen.getStreet(), null));
            }
        }

        sucursalesFiltradas = new ArrayList<>(sucursales);
        colocarSucursalActualPrimero();
    }

    public void buscarSucursal() {
        sucursalesFiltradas.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            for (AlmacenDTO a : sucursales) {
                if (a.getCity() != null && a.getCity().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    sucursalesFiltradas.add(a);
                } else if (a.getNombrextablet() != null && a.getNombrextablet().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    sucursalesFiltradas.add(a);
                } else if (a.getStreet() != null && a.getStreet().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    sucursalesFiltradas.add(a);
                } else if (a.getWhsCode() != null && a.getWhsCode().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    sucursalesFiltradas.add(a);
                } else if (a.getWhsName() != null && a.getWhsName().toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                    sucursalesFiltradas.add(a);
                }
            }
        } else {
            sucursalesFiltradas = new ArrayList<>(sucursales);
        }
        colocarSucursalActualPrimero();
    }

    public void cambiarSucursal() {
        String almacen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacen");

        if (almacen != null && !almacen.isEmpty()) {
            log.log(Level.INFO, "Se cambiara la sucursal del asesor: [{0}], actual: [{1}], nueva: [{2}]",
                    new Object[]{sessionMBean.getUsuario(), sessionMBean.getAlmacen(), almacen});
            sessionMBean.setAlmacen(almacen);
            log.log(Level.INFO, "La sucursal del asesor: [{0}] cambio. nueva: [{1}]", new Object[]{sessionMBean.getUsuario(), almacen});
            colocarSucursalActualPrimero();
        }
    }

    private void colocarSucursalActualPrimero() {
        for (int i = 0; i < sucursalesFiltradas.size(); i++) {
            AlmacenDTO a = sucursalesFiltradas.get(i);

            if (a.getWhsCode().equals(sessionMBean.getAlmacen())) {
                sucursalesFiltradas.remove(i);
                sucursalesFiltradas.add(0, a);
                break;
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
