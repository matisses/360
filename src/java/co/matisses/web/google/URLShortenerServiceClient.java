package co.matisses.web.google;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class URLShortenerServiceClient {

    private WebTarget webTarget;
    private Client client;

    public URLShortenerServiceClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("google");
    }

    public URLShortenerResponseDTO generar(String urlLarga) throws Exception {
        return webTarget.path("generarurlcorta").queryParam("urlLarga", urlLarga)
                .request(MediaType.APPLICATION_JSON).get(URLShortenerResponseDTO.class);
    }

    public void close() {
        client.close();
    }

}
