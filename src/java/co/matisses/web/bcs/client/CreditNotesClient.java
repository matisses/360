package co.matisses.web.bcs.client;

import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.SalesDocumentDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class CreditNotesClient {

    private WebTarget webTarget;
    private Client client;

    public CreditNotesClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("creditnotes");
    }

    public CreditNotesClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO crearNotaCredito(SalesDocumentDTO dto) {
        return webTarget.path("create").request(MediaType.APPLICATION_JSON).post(Entity.entity(dto, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
