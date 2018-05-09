package co.matisses.web.bcs.client;

import co.matisses.web.bcs.servicecalls.ServiceCall;
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
public class ServiceCallsClient {

    private WebTarget webTarget;
    private Client client;

    public ServiceCallsClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("servicecalls");
    }

    public ServiceCallsClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public ServiceCall buscarLlamadaServicio(Integer callID) {
        return webTarget.path("find").path(callID.toString()).request(MediaType.APPLICATION_JSON).get(ServiceCall.class);
    }

    public GenericRESTResponseDTO crearLlamadaServicio(ServiceCall dto) {
        return webTarget.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO editarLlamadaServicio(ServiceCall dto) {
        return webTarget.path("edit").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
