package co.matisses.web.mbean.clientes;

import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.DireccionesClienteDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.SelectEvent;

/**
 *
 * @author dbotero
 */
@SessionScoped
@Named(value = "clienteSessionMBean")
public class ClienteSessionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ClienteSessionMBean.class.getSimpleName());

    @Inject
    private BaruApplicationMBean applicationBean;
    private String ciudadBusqueda;
    private ClienteSAPDTO clienteDto;
    private DireccionesClienteDTO direccionCliente;

    public ClienteSessionMBean() {
        clienteDto = new ClienteSAPDTO();
        direccionCliente = new DireccionesClienteDTO();
    }

    public String getCiudadBusqueda() {
        return ciudadBusqueda;
    }

    public void setCiudadBusqueda(String ciudadBusqueda) {
        this.ciudadBusqueda = ciudadBusqueda;
    }

    public ClienteSAPDTO getClienteDto() {
        return clienteDto;
    }

    public void setClienteDto(ClienteSAPDTO clienteDto) {
        this.clienteDto = clienteDto;
    }

    public DireccionesClienteDTO getDireccionCliente() {
        return direccionCliente;
    }

    public void setDireccionCliente(DireccionesClienteDTO direccionCliente) {
        this.direccionCliente = direccionCliente;
    }

    public List<CiudadDTO> filtrarCiudades(String textoBusqueda) {
        List<CiudadDTO> sublist = new ArrayList<>();
        for (CiudadDTO dto : applicationBean.getCiudades()) {
            if (dto.similarTo(textoBusqueda)) {
                sublist.add(dto);
            }
            if (sublist.size() >= 10) {
                break;
            }
        }
        Collections.sort(sublist);
        return sublist;
    }

    public void seleccionarCiudad(SelectEvent event) {
        CiudadDTO ciudad = (CiudadDTO) event.getObject();
        log.log(Level.INFO, "selecciono la ciudad {0}", ciudad);
        if (ciudad == null || ciudad.getCodigo() == null) {
            return;
        }
        direccionCliente.setCodCiudad(ciudad.getCodigo());
        direccionCliente.setNombreCiudad(ciudad.getNombre());
        direccionCliente.setCodDepartamento(ciudad.getCodDepartamento());
        direccionCliente.setNombreDepartamento(ciudad.getNomDepartamento());
    }
}
