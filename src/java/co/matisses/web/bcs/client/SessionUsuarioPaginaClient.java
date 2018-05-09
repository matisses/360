package co.matisses.web.bcs.client;

import co.matisses.web.dto.PuntosWpDTO;
import co.matisses.web.dto.VentaDTO;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class SessionUsuarioPaginaClient {

    private WebTarget webTarget;
    private Client client;

    public SessionUsuarioPaginaClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("sessionusuario");
    }

    public SessionUsuarioPaginaClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public PuntosWpDTO cargarMontoAcumulado(String documento) {
        return webTarget.path("cargarmontoacumulado").path(documento).request(MediaType.APPLICATION_JSON).get(PuntosWpDTO.class);
    }

    public List<String> cargarClientesDecorador(String decorador) {
        return webTarget.path("cargar-clientes-decorador").path(decorador).request(MediaType.APPLICATION_JSON).get(List.class);
    }

    public List<VentaDTO> cargarFacturasClientesDecorador(String decorador, String cliente) {
        return (List<VentaDTO>) webTarget.path("cargar-facturas-clientes-decorador").path(decorador).path(cliente).request(MediaType.APPLICATION_JSON).get(List.class);
    }
}
