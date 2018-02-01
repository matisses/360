package co.matisses.web.mbean.ubicaciones;

import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.SaldoUbicacionDTO;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
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
@Named(value = "consultarUbicacionesMBean")
public class ConsultarUbicacionesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger log = Logger.getLogger(ConsultarUbicacionesMBean.class.getSimpleName());
    private String parametroBusqueda;
    private List<SaldoUbicacionDTO> saldos;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;

    public ConsultarUbicacionesMBean() {
        saldos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public List<SaldoUbicacionDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoUbicacionDTO> saldos) {
        this.saldos = saldos;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public void buscarMercancia() {
        saldos.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            parametroBusqueda = baruGenericMBean.completarReferencia(parametroBusqueda);
            List<SaldoUbicacion> balances = new ArrayList<>();

            balances.addAll(saldoUbicacionFacade.buscarXBinCode(sessionMBean.getAlmacen() + parametroBusqueda));
            balances.addAll(saldoUbicacionFacade.buscarXReferencia(parametroBusqueda,
                    sessionMBean.validarPermisoUsuario(Objetos.BODEGAS_NO_VENTA, Acciones.CONSULTAR)));

            if (balances != null && !balances.isEmpty()) {
                for (SaldoUbicacion balance : balances) {
                    saldos.add(new SaldoUbicacionDTO(balance.getAbsEntry(),
                            balance.getUbicacion() == null ? null : balance.getUbicacion().getAbsEntry(), balance.getFreezeDoc(),
                            balance.getOnHandQty().intValue(), balance.getItemCode(), balance.getWhsCode(),
                            balance.getUbicacion() == null ? null : balance.getUbicacion().getBinCode(), balance.getFreezed()));
                }
            } else {
                mostrarMensaje("Error", "No se encontraron datos correspondientes a la búsqueda", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos correspondientes a la busqueda");
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
