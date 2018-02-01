package co.matisses.web.rest;

import co.matisses.b1ws.client.goodsissue.GoodsIssueDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueDetailDTO;
import co.matisses.b1ws.client.goodsissue.GoodsIssueServiceConnector;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.web.dto.DetalleEmpaqueVentaDTO;
import co.matisses.web.dto.EmpaqueVentaDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
@Path("empaqueventa")
public class EmpaqueVentaFacadeREST extends AbstractFacade<ItemInventario> {

    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruApplicationMBean baruAplicationBean;
    private static final Logger log = Logger.getLogger(EmpaqueVentaFacadeREST.class.getSimpleName());

    public EmpaqueVentaFacadeREST() {
        super(ItemInventario.class);
    }

    /**
     * Lista todos los productos asociados al departamento 'Material de Empaque'
     * (111) que tengan saldo disponible en el almacen de insumos que se envia
     * como parametro. El codigo del almacen se envia generico, es decir, para
     * matisses las vegas se debe enviar '0203', y el query antepone el prefijo
     * 'IN' para consultar el saldo en el inventario de insumos
     *
     * @param whsCode codigo del almacen donde se consultaran los productos de
     * empaque con saldo
     * @return
     */
    @GET
    @Path("list/{whsCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listPackagingOptions(@PathParam("whsCode") String whsCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(a.DecreasAc as varchar) acc, cast(i.itemcode as varchar) itemCode, cast(i.FrgnName as varchar) itemName, cast(a.WhsCode as varchar) whsCode, cast(i.U_U_Ref_Pro as varchar(40)) refProv ");
        sb.append("from OITM i ");
        sb.append("inner join OITW s on s.ItemCode = i.ItemCode and s.OnHand > 0 and s.WhsCode = CONCAT('IN','");
        sb.append(whsCode);
        sb.append("') inner join OWHS a on a.WhsCode = s.WhsCode ");
        sb.append("where i.ItmsGrpCod = '111' ");
        sb.append("and   i.U_Grupo = '036' ");
        sb.append("order by i.U_U_Ref_Pro ");

        try {
            List<GoodsIssueDetailDTO> empaques = new ArrayList<>();
            int lineNum = 0;
            for (Object row : em.createNativeQuery(sb.toString()).getResultList()) {
                Object[] cols = (Object[]) row;
                GoodsIssueDetailDTO dto = new GoodsIssueDetailDTO();
                dto.setAccountCode((String) cols[0]);
                dto.setItemCode((String) cols[1]);
                dto.setItemName((String) cols[2]);
                dto.setLineNum(String.valueOf(lineNum++));
                dto.setQuantity("0");
                dto.setWhsCode((String) cols[3]);
                dto.setProviderCode((String) cols[4]);

                empaques.add(dto);
            }
            return Response.ok(empaques).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar la lista de productos de empaque. ", e);
        }
        return Response.ok().build();
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public void create(final EmpaqueVentaDTO empDto) {
        log.log(Level.INFO, "Creando registro de empaque por venta. {0}", empDto.toString());
        SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(empDto.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return;
        }
        GoodsIssueDTO document = new GoodsIssueDTO();
        document.setComments("Empaque utilizado para la factura " + empDto.getNumeroFactura());
        document.setJournalMemo("Salida de mercancias");
        document.setSeries("105"); //TODO: parametrizar numero de serie de salida de mercancias por inventario
        document.setGroupNum(String.valueOf(-1L)); //Ultimo precio de compra

        int lineNum = 0;
        int totalUnits = 0;
        for (DetalleEmpaqueVentaDTO det : empDto.getReferencias()) {
            GoodsIssueDetailDTO detail = new GoodsIssueDetailDTO();
            detail.setAccountCode(empDto.getCuenta());
            detail.setItemCode(det.getReferencia());
            detail.setLineNum(String.valueOf(lineNum++));
            detail.setQuantity(det.getCantidad().toString());
            detail.setWhsCode(empDto.getAlmacen());

            totalUnits += det.getCantidad();
            document.addDetail(detail);
        }

        if (totalUnits == 0) {
            log.log(Level.INFO, "No se registraron unidades de empaque para la venta");
            return;
        }

        try {
            //crear salida de insumos en sap
            GoodsIssueServiceConnector gisc = sapB1WSBean.getGoodsIssueServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            Long docEntry = gisc.createDocument(document);
            log.log(Level.INFO, "Se creo la salida de insumos {0}", docEntry);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear el registro de empaque. ", e);
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
