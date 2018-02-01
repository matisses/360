package co.matisses.web.bcs.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 * @author dbotero
 */
public class SMSServiceClient {

    private WebTarget webTarget;
    private Client client;

    public SMSServiceClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("sms");
    }

    public void enviarSMS(Object requestEntity) throws ClientErrorException {
        webTarget.path("enviarSMS").request(MediaType.APPLICATION_JSON).post(Entity.entity(requestEntity, MediaType.APPLICATION_JSON));
    }

    public void close() {
        client.close();
    }

}
