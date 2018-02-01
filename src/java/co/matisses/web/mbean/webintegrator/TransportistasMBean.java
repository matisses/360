package co.matisses.web.mbean.webintegrator;

import co.matisses.persistence.web.entity.TransportistaExterno;
import co.matisses.persistence.web.facade.TransportistaExternoFacade;
import co.matisses.web.dto.webintegrator.TransportistaExternoDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "transportistasMBean")
public class TransportistasMBean implements Serializable {

    private static final Logger log = Logger.getLogger(TransportistasMBean.class.getSimpleName());

    private TransportistaExternoDTO transportista;
    private List<TransportistaExternoDTO> transportistas;

    @EJB
    private TransportistaExternoFacade transportistaFacade;

    public TransportistasMBean() {
        transportista = new TransportistaExternoDTO();
        transportistas = new ArrayList<>();
    }

    @PostConstruct
    public void initialize() {
        cargarTransportistas();
    }

    public void cargarTransportistas() {
        transportistas = new ArrayList<>();
        List<TransportistaExterno> entidades = transportistaFacade.findAll();
        for (TransportistaExterno entidad : entidades) {
            transportistas.add(new TransportistaExternoDTO(entidad.getCarrierId(), entidad.getCarrierName(), entidad.getClassName(),
                    entidad.getQuotingMethod(), entidad.getTrackingMethod(), entidad.getWsdl(), entidad.getActive(), entidad.getCuentaSAP()));
        }
    }

    public TransportistaExternoDTO getTransportista() {
        return transportista;
    }

    public void setTransportista(TransportistaExternoDTO transportista) {
        this.transportista = transportista;
    }

    public List<TransportistaExternoDTO> getTransportistas() {
        return transportistas;
    }

    public void setTransportistas(List<TransportistaExternoDTO> transportistas) {
        this.transportistas = transportistas;
    }

    public void seleccionarTransportista() {
        try {
            Integer idTransportistaSeleccionado = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idTransportista"));
            if (transportista.getIdTransportista() != null && transportista.getIdTransportista().equals(idTransportistaSeleccionado)) {
                limpiar();
                return;
            }
            limpiar();
            for (TransportistaExternoDTO dto : transportistas) {
                if (dto.getIdTransportista().equals(idTransportistaSeleccionado)) {
                    transportista = dto;
                    break;
                }
            }
        } catch (Exception e) {
        }
    }

    public void guardar() {
        if (transportista.getNombre() == null || transportista.getNombre().trim().isEmpty()) {
            return;
        }
        TransportistaExterno entidad = new TransportistaExterno();
        entidad.setActive(transportista.getActivo());
        entidad.setCarrierName(transportista.getNombre());
        entidad.setClassName(transportista.getClase());
        entidad.setCuentaSAP(transportista.getCuentaSAP());
        entidad.setQuotingMethod(transportista.getMetodoCotizacion());
        entidad.setTrackingMethod(transportista.getMetodoRastreo());
        entidad.setWsdl(transportista.getWsdl());

        try {
            if (transportista.getIdTransportista() != null && transportista.getIdTransportista() > 0) {
                //modificar
                entidad.setCarrierId(transportista.getIdTransportista());
                transportistaFacade.edit(entidad);
            } else {
                //crear
                transportistaFacade.create(entidad);
            }
            cargarTransportistas();
            limpiar();
        } catch (Exception e) {
        }
    }

    public void limpiar() {
        transportista = new TransportistaExternoDTO();
    }

    public void valorCambio() {

    }
}
