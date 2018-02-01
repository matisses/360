package co.matisses.web.rest;

import co.matisses.persistence.sap.entity.Municipio;
import co.matisses.web.dto.MunicipioDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("municipio")
public class MunicipioFacadeREST extends AbstractFacade<Municipio> {

    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;

    public MunicipioFacadeREST() {
        super(Municipio.class);
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") String id) {
        List<MunicipioDTO> mpios = new ArrayList<>();
        for (Municipio mpio : super.findAll()) {
            if (mpio.getCode().substring(0, 2).equals(id)) {
                mpios.add(new MunicipioDTO(mpio.getCode(), mpio.getName()));
            }
        }
        return Response.ok(mpios).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
