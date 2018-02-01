package co.matisses.web.google;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class URLShortenerServiceClient_ {

    private WebTarget webTarget;
    private Client client;

    public URLShortenerServiceClient_(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("google");
    }

    public String generar(Object requestEntity) throws ClientErrorException {
        return webTarget.path("generarurlcorta").request(MediaType.APPLICATION_JSON).post(Entity.entity(requestEntity, MediaType.TEXT_PLAIN), String.class);
    }

    public void close() {
        client.close();
    }
    
}
