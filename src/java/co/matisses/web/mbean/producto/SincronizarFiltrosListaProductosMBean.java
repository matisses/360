package co.matisses.web.mbean.producto;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.facade.ValorActivoFiltroFacade;
import co.matisses.web.mbean.BaruApplicationMBean;
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
@Named(value = "sincronizarFiltrosListaProductosMBean")
public class SincronizarFiltrosListaProductosMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(SincronizarFiltrosListaProductosMBean.class.getSimpleName());
    private List<String[]> filtrosObtenidos;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ValorActivoFiltroFacade valorActivoFiltroFacade;

    public SincronizarFiltrosListaProductosMBean() {
        filtrosObtenidos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public List<String[]> getFiltrosObtenidos() {
        return filtrosObtenidos;
    }

    public void setFiltrosObtenidos(List<String[]> filtrosObtenidos) {
        this.filtrosObtenidos = filtrosObtenidos;
    }

    public void actualizarFiltros() {
        filtrosObtenidos = new ArrayList<>();
        log.log(Level.INFO, "Inicia obtencion de los valores para los filtros");

        List<Object[]> datos = itemFacade.obtenerFiltrosDisponibles();
        log.log(Level.INFO, "Finaliza obtencion de los valores para el filtro");
        log.log(Level.INFO, "Inicia proceso de actualizacion de filtros");
        if (datos != null && !datos.isEmpty()) {
            log.log(Level.INFO, "Se obtuvieron [{0}] filtros para agregar", datos.size());

            for (Object[] obj : datos) {
                filtrosObtenidos.add(new String[]{(String) obj[0], (String) obj[1], (String) obj[2]});
                log.log(Level.INFO, "[tipo={0}] [valor={1}]", new Object[]{(String) obj[0], (String) obj[1]});
            }
            log.log(Level.INFO, "Se eliminan los registros actuales");
            try {
                valorActivoFiltroFacade.eliminarRegistrosActuales();
                log.log(Level.INFO, "Se eliminaron los registros actuales");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar los registros existente. [{0}]", e.getMessage());
                mostrarMensaje("Error", "Ocurrió un error al eliminar los filtros actuales.", true, false, false);
                return;
            }

            log.log(Level.INFO, "Se insertan los nuevos filtros");
            try {
                valorActivoFiltroFacade.insertarRegistrosNuevos(datos);
                log.log(Level.INFO, "Se insertaron los nuevos filtros");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al insertar los nuevos filtros");
                mostrarMensaje("Error", "Ocurrió un error al insertar los nuevos filtros.", true, false, false);
                return;
            }
        }
        log.log(Level.INFO, "Finaliza proceso de actualizacion de filtros");
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
