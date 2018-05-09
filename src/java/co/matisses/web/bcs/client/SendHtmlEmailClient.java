package co.matisses.web.bcs.client;

import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author ygil
 */
public class SendHtmlEmailClient {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private WebTarget webTarget;
    private Client client;

    public SendHtmlEmailClient(String BASE_URI) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path("email");
    }

    public SendHtmlEmailClient(String BASE_URI, String path) {
        client = ClientBuilder.newClient();
        webTarget = client.target(BASE_URI).path(path);
    }

    public GenericRESTResponseDTO enviarHtmlEmail(String from, String subject, String to, String plantilla, List<String[]> adjuntos, Map<String, String> params) {
        MailMessageDTO msg = new MailMessageDTO();

        msg.setFrom(from + "<notificaciones@matisses.co>");
        msg.setSubject(subject);
        msg.setTemplateName(plantilla);
        msg.addToAddress(to);
        msg.setAttachments(adjuntos);
        msg.setParams(params);

        return webTarget.path("enviaremail360").request(MediaType.APPLICATION_JSON).post(Entity.entity(msg, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO enviarHtmlEmail(MailMessageDTO msg) {
        return webTarget.path("enviaremail360").request(MediaType.APPLICATION_JSON).post(Entity.entity(msg, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }

    public GenericRESTResponseDTO enviarHtmlEmailSoporte(MailMessageDTO msg) {
        return webTarget.path("enviaremailsoporte").request(MediaType.APPLICATION_JSON).post(Entity.entity(msg, MediaType.APPLICATION_JSON), GenericRESTResponseDTO.class);
    }
}
