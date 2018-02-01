package co.matisses.web.rest;

import co.matisses.persistence.sap.entity.TarjetaCreditoSAP;
import co.matisses.web.dto.TarjetaCreditoDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("tarjetacredito")
public class TarjetaCreditoSAPFacadeREST extends AbstractFacade<TarjetaCreditoSAP> {

    private static final Logger log = Logger.getLogger(TarjetaCreditoSAP.class.getSimpleName());
    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;

    public TarjetaCreditoSAPFacadeREST() {
        super(TarjetaCreditoSAP.class);
    }

    @GET
    @Path("{whsCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public List<TarjetaCreditoDTO> findByWhs(@PathParam("whsCode") String whsCode) {
        log.log(Level.INFO, "Consultando tipos de tarjeta");
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery cq = cb.createQuery(TarjetaCreditoSAP.class);
        Root<TarjetaCreditoSAP> root = cq.from(TarjetaCreditoSAP.class);
        List<String> whsCodes = new ArrayList<>();
        whsCodes.add(whsCode);
        whsCodes.add("0000");
        Expression<String> exp = root.get("whsCode");
        Predicate predicate = exp.in(whsCodes);
        cq.where(
                cb.and(
                        predicate,
                        //cb.equal(root.get("whsCode"), whsCode),
                        cb.like(root.<String>get("terminalName"), "%POS")
                )
        );
        try {
            List<TarjetaCreditoDTO> tarjetas = new ArrayList<>();
            for (TarjetaCreditoSAP ent : (List<TarjetaCreditoSAP>) em.createQuery(cq).getResultList()) {
                String cardName = ent.getTerminalName().substring(0, ent.getTerminalName().indexOf(" POS")).replace(" ", "");
                String cardType = ent.getCardName().startsWith("T.C") ? "credit" : "debit";
                tarjetas.add(new TarjetaCreditoDTO(ent.getCreditCard(), cardName, ent.getAcctCode(), cardType));
            }
            return tarjetas;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar las tarjetas para el almacen [" + whsCode + "]. ", e);
            return null;
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
