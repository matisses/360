package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.web.dto.ProgramacionDescuentoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
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
@Named(value = "consultarDescuentosMBean")
public class ConsultarDescuentosMBean implements Serializable {

    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    private static final Logger log = Logger.getLogger(ConsultarDescuentosMBean.class.getSimpleName());
    private String parametroBusqueda;
    private String referencia;
    private List<String[]> imagenes;
    private List<ProgramacionDescuentoDTO> descuentos;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;

    public ConsultarDescuentosMBean() {
        imagenes = new ArrayList<>();
        descuentos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public List<String[]> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String[]> imagenes) {
        this.imagenes = imagenes;
    }

    public List<ProgramacionDescuentoDTO> getDescuentos() {
        return descuentos;
    }

    public void setDescuentos(List<ProgramacionDescuentoDTO> descuentos) {
        this.descuentos = descuentos;
    }

    public void consultarDescuentoReferencia() {
        referencia = null;
        imagenes = new ArrayList<>();
        descuentos = new ArrayList<>();
        if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar una referencia para consultar.", true, false, false);
            log.log(Level.SEVERE, "Debe ingresar una referencia para consultar");
            return;
        }

        String tmpRef = parametroBusqueda;
        if (tmpRef.length() < 20) {
            tmpRef = baruGenericMBean.completarReferencia(tmpRef);
        }

        ItemInventario item = itemInventarioFacade.getItemBasicInfo(tmpRef);

        if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
            referencia = item.getItemCode();
        } else {
            item = itemInventarioFacade.buscarXEan(parametroBusqueda);

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                referencia = item.getItemCode();
            } else {
                mostrarMensaje("Error", "El ítem ingresado no corresponde a una referencia valida.", true, false, false);
                log.log(Level.SEVERE, "El item ingresado {0} no corresponde a una referencia valida", referencia);
                return;
            }
        }

        List<ProgramacionDescuento> discounts = programacionDescuentoFacade.obtenerProgramacionesReferencia(referencia);

        for (ProgramacionDescuento d : discounts) {
            descuentos.add(new ProgramacionDescuentoDTO(d.getPorcentaje(), null, null, d.getCanal(), d.getFechaInicio(), d.getFechaFin()));
        }

        parametroBusqueda = null;
        obtenerGaleria();
    }

    private void obtenerGaleria() {
        log.log(Level.INFO, "Se estan obteniendo el catalogo de imagenes de la referencia {0}", referencia);

        /*1. Se obtiene la galeria de imagenes*/
        imagenes = new ArrayList<>();
        int contador = 0;
        List<String> galeria = imagenProductoMBean.obtenerImagenesCatalogo(referencia);
        if (galeria != null && !galeria.isEmpty()) {
            String url = applicationMBean.obtenerValorPropiedad("url.web.imagen");
            if (url != null) {
                url = String.format(url, referencia);
            }

            for (String s : galeria) {
                imagenes.add(new String[]{url + s, String.valueOf(contador)});
                contador++;
            }
        }

        /*2. Se obtiene la plantilla de la referencia*/
        imagenes.add(new String[]{imagenProductoMBean.obtenerPlantilla(referencia), String.valueOf(contador)});
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
