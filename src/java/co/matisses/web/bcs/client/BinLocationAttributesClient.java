package co.matisses.web.bcs.client;

import co.matisses.web.dto.BinLocationAttributesDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class BinLocationAttributesClient {

    private WebTarget webTarget;
    private Client client;

    public BinLocationAttributesClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("binlocationattributes");
    }

    public BinLocationAttributesClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO crearAtributoUbicacion(String usuario, BinLocationAttributesDTO dto) {
        return webTarget.path("create").path(usuario).request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
