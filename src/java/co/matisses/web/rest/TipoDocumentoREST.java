package co.matisses.web.rest;

import co.matisses.persistence.sap.entity.TipoDocumento;
import co.matisses.persistence.sap.facade.TipoDocumentoFacade;
import java.util.Collections;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("tipodocumento")
public class TipoDocumentoREST {

    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;

    @GET
    @Path("listar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTiposDocumento() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoFacade.findAll();
        Collections.sort(tiposDocumento);
        return Response.ok(tiposDocumento).build();
    }
}
