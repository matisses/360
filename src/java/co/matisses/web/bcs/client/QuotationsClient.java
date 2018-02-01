package co.matisses.web.bcs.client;

import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.QuotationsDocumentDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class QuotationsClient {

    private WebTarget webTarget;
    private Client client;

    public QuotationsClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("quotations");
    }

    public QuotationsClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO createQuotation(QuotationsDocumentDTO dto) {
        try {
            return webTarget.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
        } catch (Exception e) {
            return new GenericRESTResponseDTO(-1, e.getMessage());
        }
    }

    public GenericRESTResponseDTO editQuotation(QuotationsDocumentDTO dto) {
        try {
            return webTarget.path("edit").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
        } catch (Exception e) {
            return new GenericRESTResponseDTO(-1, e.getMessage());
        }
    }
}
