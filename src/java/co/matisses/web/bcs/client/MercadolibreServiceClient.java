package co.matisses.web.bcs.client;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class MercadolibreServiceClient {

    private WebTarget webTarget;
    private Client client;
    
    public MercadolibreServiceClient(String BASE_URI) {
        client = javax.ws.rs.client.ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("mercadolibre");
    }

    /*public <T> T procesarAuthCode(Class<T> responseType, String code) throws ClientErrorException {
        WebTarget resource = webTarget;
        if (code != null) {
            resource = resource.queryParam("code", code);
        }
        resource = resource.path("procesarAuthCode");
        return resource.get(responseType);
    }*/

    public <T> T obtenerURLAutenticacion(Class<T> responseType) throws ClientErrorException {
        WebTarget resource = webTarget;
        resource = resource.path("urlAutenticacion");
        return resource.request(MediaType.APPLICATION_JSON).get(responseType);
    }

    public void close() {
        client.close();
    }
    
}
