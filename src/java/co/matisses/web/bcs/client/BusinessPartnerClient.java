package co.matisses.web.bcs.client;

import co.matisses.web.bcs.businesspartner.BusinessPartner;
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
public class BusinessPartnerClient {

    private WebTarget webTarget;
    private Client client;

    public BusinessPartnerClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("businesspartner");
    }

    public BusinessPartnerClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO crearCliente(BusinessPartner dto) {
        return webTarget.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO editarCliente(BusinessPartner dto) {
        return webTarget.path("edit").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public BusinessPartner buscarCliente(String cardCode) {
        return webTarget.path("find").path(cardCode).request(MediaType.APPLICATION_JSON).get(BusinessPartner.class);
    }
}
