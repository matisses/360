package co.matisses.web.bcs.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
public class JournalEntryClient {

    private WebTarget webTarget;
    private Client client;

    public JournalEntryClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("journalentry");
    }

    public JournalEntryClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public Response actualizarTerceroAsiento(String usuario, Long docEntry, String account) {
        return webTarget.path("update").path(usuario).path(docEntry.toString()).path(account).request(MediaType.APPLICATION_JSON).get(Response.class);
    }
}
