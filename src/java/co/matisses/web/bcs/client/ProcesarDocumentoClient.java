package co.matisses.web.bcs.client;

import co.matisses.web.dto.GenericRESTResponseDTO;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class ProcesarDocumentoClient {

    private WebTarget webTarget;
    private Client client;

    public ProcesarDocumentoClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("procesardocumento");
    }

    public ProcesarDocumentoClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO ejecutarSonda(String procesando) {
        return webTarget.path("ejecutarsonda").path(procesando).request(MediaType.APPLICATION_JSON).get(GenericRESTResponseDTO.class);
    }
}
