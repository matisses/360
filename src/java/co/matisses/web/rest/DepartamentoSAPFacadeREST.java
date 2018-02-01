package co.matisses.web.rest;

import co.matisses.persistence.sap.entity.DepartamentoSAP;
import co.matisses.web.dto.DepartamentoDTO;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
@Path("departamentosap")
public class DepartamentoSAPFacadeREST extends AbstractFacade<DepartamentoSAP> {

    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;

    public DepartamentoSAPFacadeREST() {
        super(DepartamentoSAP.class);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response listAll() {
        List<DepartamentoDTO> dptos = new ArrayList<>();
        for (DepartamentoSAP dpto : super.findAll()) {
            if (dpto.getDepartamentoPK().getCountry().equals("CO")) {
                dptos.add(new DepartamentoDTO(dpto.getDepartamentoPK().getCode(), dpto.getName()));
            }
        }
        return Response.ok(dptos).build();
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
