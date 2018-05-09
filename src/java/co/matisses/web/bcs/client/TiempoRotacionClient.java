package co.matisses.web.bcs.client;

import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class TiempoRotacionClient {

    private WebTarget webTarget;
    private Client client;

    public TiempoRotacionClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("tiemporotacion");
    }

    public TiempoRotacionClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public String calcularRotacion(boolean log, List<String> referencias) {
        return webTarget.path("calcularrotacion").path(String.valueOf(log)).request(MediaType.APPLICATION_JSON).post(Entity.entity(referencias, MediaType.APPLICATION_JSON), String.class);
    }
}
