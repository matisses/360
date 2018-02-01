package co.matisses.web.rest.regalos;

import co.matisses.web.bcs.client.EmailServiceClient;
import co.matisses.web.dto.BCSMailMessageDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.rest.regalos.dto.ContactoDTO;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Path("contacto")
public class ContactoREST {

    private static final Logger log = Logger.getLogger(ContactoREST.class.getSimpleName());

    @Inject
    private BaruApplicationMBean applicationBean;

    @POST
    @Path("enviar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response enviarMensaje(final ContactoDTO contacto) {
        log.log(Level.INFO, "Enviando mensaje desde formulario de contacto. {0}", contacto);
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom(contacto.getNombre() + "<" + contacto.getEmail() + ">");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", contacto.getNombre());
        params.put("asunto", contacto.getAsunto());
        params.put("mensaje", contacto.getMensaje());
        mail.setParams(params);
        mail.setSubject("Mensaje de contacto desde lista de regalos");
        mail.setTemplateName("lista_regalos_contacto");
        mail.setTo(applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos_contacto").getTo());
        mail.setCc(applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos_contacto").getCc());
        mail.setBcc(applicationBean.getDestinatariosPlantillaEmail().get("lista_regalos_contacto").getBcc());
        try {
            client.sendMail(mail);
            log.log(Level.INFO, "Mensaje enviado con exito");
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el correo. ", e);
        }
        return Response.ok().build();
    }
}
