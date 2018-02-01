package co.matisses.web.bcs.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author dbotero
 */
public class EmailServiceClient {

    private WebTarget webTarget;
    private Client client;

    public EmailServiceClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("email");
    }

    public Response sendMail(Object requestEntity) throws ClientErrorException {
        return webTarget.path("enviarEmail").request(MediaType.APPLICATION_JSON).post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON), Response.class);
    }

    public void close() {
        client.close();
    }

}
