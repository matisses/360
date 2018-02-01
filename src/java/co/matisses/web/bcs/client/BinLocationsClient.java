package co.matisses.web.bcs.client;

import co.matisses.web.dto.BinLocationDTO;
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
public class BinLocationsClient {

    private WebTarget webTarget;
    private Client client;

    public BinLocationsClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("binlocations");
    }

    public BinLocationsClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO crearUbicacion(String usuario, BinLocationDTO dto) {
        return webTarget.path("create").path(usuario).request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO editarUbicacion(String usuario, BinLocationDTO dto) {
        return webTarget.path("update").path(usuario).request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO habilitarUbicacion(BinLocationDTO dto) {
        return webTarget.path("enable").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
