package co.matisses.web.bcs.client;

import co.matisses.web.dto.BCSPaymentDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
public class PaymentClient {

    private WebTarget webTarget;
    private Client client;

    public PaymentClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("payment");
    }

    public PaymentClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public Response crearReciboCaja(BCSPaymentDTO payment, String usuario) {
        return webTarget.path("add").path(usuario).request(MediaType.APPLICATION_JSON).post(Entity.entity(payment, MediaType.APPLICATION_JSON), Response.class);
    }
}
