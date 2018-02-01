package co.matisses.web.rest;

import co.matisses.b1ws.client.items.ItemsServiceConnector;
import co.matisses.b1ws.dto.ItemUpdateDTO;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.web.dto.InventoryItemDTO;
import co.matisses.web.dto.InventoryItemPrestashopDTO;
import co.matisses.web.dto.InventoryItemStatusDTO;
import co.matisses.web.dto.ItemBrandDTO;
import co.matisses.web.dto.ItemColorDTO;
import co.matisses.web.dto.ItemInventarioDTO;
import co.matisses.web.dto.ItemMaterialDTO;
import co.matisses.web.dto.ProductoListaRegalosDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("iteminventario")
public class ItemInventarioFacadeREST extends AbstractFacade<ItemInventario> {

    private static final Logger log = Logger.getLogger(ItemInventarioFacadeREST.class.getSimpleName());
    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @EJB
    private ItemInventarioFacade itemFacade;

    public ItemInventarioFacadeREST() {
        super(ItemInventario.class);
    }

    @GET
    @Path("consulta/{itemCode}/{whsCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("itemCode") String itemCode, @PathParam("whsCode") String whsCode) {
        log.log(Level.INFO, "Consultando saldo para la venta POS con referencia [{0}] en almacen [{1}]", new Object[]{itemCode, whsCode});
        try {
            InventoryItemDTO dto = executeSearch(itemCode, whsCode);
            return Response.ok(dto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("consultaPrecio/{itemCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getItemPrice(@PathParam("itemCode") String itemCode) {
        try {
            return Response.ok(itemFacade.getItemPriceAndTax(itemCode, false)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("consultanombre/{itemCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response getItemNameAndDescription(@PathParam("itemCode") String itemCode) {
        try {
            ItemInventarioDTO dto = new ItemInventarioDTO();
            ItemInventario entidad = itemFacade.getItemNamAndDesc(itemCode);
            dto.setItemName(entidad.getFrgnName());
            dto.setUdescripciona(entidad.getUdescripciona());
            dto.setuDescCorta(entidad.getUDescCorta());
            return Response.ok(dto).build();
        } catch (Exception e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("consultastock/{whsCode}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response queryItemsStock(@PathParam("whsCode") String whsCode, List<String> itemCodes) {
        log.log(Level.INFO, "Consultando inventario para el almacen {0} y los items {1}", new Object[]{whsCode, itemCodes});
        List<Object[]> result = itemFacade.consultarSaldoItemPOS(whsCode, itemCodes);
        try {
            Map<String, InventoryItemDTO> items = new HashMap<>();
            for (Object[] cols : result) {
                InventoryItemDTO dto = items.get((String) cols[0]);
                if (dto == null) {
                    dto = new InventoryItemDTO();
                    dto.setItemCode((String) cols[0]);
                    items.put(dto.getItemCode(), dto);
                }
                dto.addStock((String) cols[1], (Integer) cols[2], (Integer) cols[3], (String) cols[4]);
            }
            List<InventoryItemDTO> saldos = new ArrayList<>();
            for (String itemCode : items.keySet()) {
                saldos.add(items.get(itemCode));
            }
            return Response.ok(saldos).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible consultar el saldo del producto. ", e);
            return null;
        }
    }

    public InventoryItemDTO executeSearch(String reference, String whsCode) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(i.ItemCode as varchar(20)) itemCode ");
        sb.append(", cast(i.ItemName as varchar(60)) itemName ");
        sb.append(", cast(i.U_U_Ref_Pro as varchar(50)) ref_prov ");
        sb.append(", cast(i.TaxCodeAR as varchar) taxCode ");
        sb.append(", cast(t.Rate as int) iva ");
        sb.append(", cast(p.Price as int) precio ");
        sb.append(", cast(q.WhsCode as varchar(8)) whsCode ");
        sb.append(", cast(q.OnHandQty as int) cantidad ");
        sb.append(", b.AbsEntry ");
        sb.append(", cast(b.BinCode as varchar(max)) binCode ");
        sb.append("from   OITM i ");
        sb.append("inner join ITM1 p on p.ItemCode = i.ItemCode ");
        sb.append("inner join OSTC t on t.Code = i.TaxCodeAR ");
        sb.append("inner join [@BARU_UBICACION_POS] up on up.U_whsOrigen = '");
        sb.append(whsCode);
        sb.append("' inner join OBIN b on b.WhsCode = up.U_whsVenta and b.BinCode = CONCAT(up.U_whsVenta,up.U_binCodeVenta) ");
        sb.append("inner join OIBQ q on q.BinAbs = b.AbsEntry and q.OnHandQty > 0 and q.ItemCode = i.ItemCode ");
        sb.append("where  i.ItemCode = '");
        sb.append(reference);
        sb.append("' and    p.PriceList = 2 ");

        try {
            InventoryItemDTO dto = null;
            List<Object[]> result = em.createNativeQuery(sb.toString()).getResultList();
            for (Object[] cols : result) {
                if (dto == null) {
                    dto = new InventoryItemDTO();
                    dto.setItemCode((String) cols[0]);
                    dto.setItemName((String) cols[1]);
                    dto.setProviderCode((String) cols[2]);
                    dto.setTaxName((String) cols[3]);
                    dto.setTaxRate(((Integer) cols[4]).toString());
                    dto.setPrice(((Integer) cols[5]).toString());
                    //dto.setAvailableQuantity((Integer) cols[7]);
                }// else {
                //dto.setAvailableQuantity(dto.getAvailableQuantity() + (Integer) cols[7]);
                //}
                dto.addStock((String) cols[6], (Integer) cols[7], (Integer) cols[8], (String) cols[9]);
            }

            if (result != null) {
                return dto;
            } else {
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible consultar el saldo del producto. ", e);
            return null;
        }
    }

    @GET
    @Path("estado")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listItemsStatus() {
        return listItemsStatus(null);
    }

    @GET
    @Path("estado/{reference}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listItemsStatus(@PathParam("reference") String reference) {
        log.log(Level.INFO, "consultando el estado de los productos para pagina web {0}", (reference != null ? "con referencia " + reference : "(todos los productos)"));
        try {
            List<InventoryItemStatusDTO> items = new ArrayList<>();
            log.log(Level.INFO, "consultando informacion en BD");
            List<Object[]> results = em.createNativeQuery("select * from FN_ESTADO_PRODUCTOS_WEB(?)").setParameter(1, reference).getResultList();
            log.log(Level.INFO, "consulta a BD finalizada. {0} filas recuperadas. Iniciando parseo de datos. ", results.size());
            for (Object[] result : results) {
                String referencia = (String) result[0];
                String motivo = (String) result[1];
                InventoryItemStatusDTO statusDto = new InventoryItemStatusDTO();
                statusDto.setActivo(motivo == null || motivo.isEmpty());
                statusDto.setMotivo(motivo);
                statusDto.setReferencia(referencia);
                items.add(statusDto);
            }
            log.log(Level.INFO, "Parseo de datos finalizado. {0} articulos encontrados. ", items.size());
            return Response.ok(items).build();
        } catch (NoResultException e) {
            log.log(Level.WARNING, "No se encontro el articulo especificado. {0}", reference);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al ejecutar la consulta de estado de articulos. ", e);
        }
        return Response.ok().build();
    }

    @GET
    @Path("inactivosConSaldo")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listInactiveItemsWithStock() {
        return listInactiveItemsWithStock(null);
    }

    @GET
    @Path("inactivosConSaldo/{reference}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listInactiveItemsWithStock(@PathParam("reference") String reference) {
        log.log(Level.INFO, "consultando los productos inactivos y con saldo {0}", (reference != null ? "con referencia " + reference : "(todos los productos)"));
        try {
            List<InventoryItemStatusDTO> items = new ArrayList<>();
            log.log(Level.INFO, "consultando informacion en BD");
            List<Object[]> results = em.createNativeQuery("select * from FN_ESTADO_PRODUCTOS_WEB(?)").setParameter(1, reference).getResultList();
            log.log(Level.INFO, "consulta a BD finalizada. {0} filas recuperadas. Iniciando parseo de datos. ", results.size());
            for (Object[] result : results) {
                String referencia = (String) result[0];
                String motivo = (String) result[1];
                if (motivo != null && !motivo.isEmpty() && !motivo.contains("saldo")) {
                    InventoryItemStatusDTO statusDto = new InventoryItemStatusDTO();
                    statusDto.setActivo(false);
                    statusDto.setMotivo(motivo);
                    statusDto.setReferencia(referencia);
                    items.add(statusDto);
                }
            }
            log.log(Level.INFO, "Parseo de datos finalizado. {0} articulos encontrados. ", items.size());
            return Response.ok(items).build();
        } catch (NoResultException e) {
            log.log(Level.WARNING, "No se encontro el articulo especificado. {0}", reference);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al ejecutar la consulta de articulos inactivos con saldo. ", e);
        }
        return Response.ok().build();
    }

    @GET
    @Path("consulta")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listWebItems() {
        return listWebItems(null);
    }

    @GET
    @Path("consulta/{reference}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listWebItems(@PathParam("reference") String reference) {
        log.log(Level.INFO, "consultando informacion de productos para pagina web {0}", (reference != null ? "con referencia " + reference : "(todos los productos)"));
        try {
            List<InventoryItemPrestashopDTO> items = new ArrayList<>();
            InventoryItemPrestashopDTO invItem = null;
            log.log(Level.INFO, "consultando informacion en BD");
            List<Object[]> results = em.createNativeQuery("{call SP_CONSULTAR_PRODUCTOS_WEB(?)}").setParameter(1, reference).getResultList();
            log.log(Level.INFO, "consulta a BD finalizada. {0} filas recuperadas. Iniciando parseo de datos. ", results.size());
            for (Object[] cols : results) {
                //Object cols[] = (Object[]) result;
                InventoryItemPrestashopDTO tmp = new InventoryItemPrestashopDTO();
                tmp.setItemCode((String) cols[0]);
                if (items.contains(tmp)) {
                    invItem = items.get(items.indexOf(tmp));
                } else {
                    invItem = null;
                }
                if (invItem == null) {
                    invItem = processQueryResult(cols);
                    items.add(invItem);
                } else {
                    invItem.mergeStockAndMaterials(processQueryResult(cols));
                    items.set(items.indexOf(invItem), invItem);
                }
            }
            log.log(Level.INFO, "Parseo de datos finalizado. {0} articulos encontrados. ", items.size());
            return Response.ok(items).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al ejecutar el procedimiento almacenado. ", e);
        }
        return Response.ok().build();
    }

    private InventoryItemPrestashopDTO processQueryResult(Object cols[]) {
        int col = 0;

        String itemCode = (String) cols[col++];
        String providerCode = itemCode != null ? itemCode.substring(0, 3) : "";
        String itemName = (String) cols[col++];
        String webName = (String) cols[col++];
        String keyWords = (String) cols[col++];
        Integer price = (Integer) cols[col++];
        String subgroupCode = (String) cols[col++];
        Integer height = (Integer) cols[col++];
        Integer depth = (Integer) cols[col++];
        Integer width = (Integer) cols[col++];
        Integer weight = (Integer) cols[col++];
        String model = (String) cols[col++];
        String colorCode = (String) cols[col++];
        String colorName = (String) cols[col++];
        String colorHexa = (String) cols[col++];
        String description = (String) cols[col++];
        Date newFrom = (Date) cols[col++];
        //String idYoutube = (String) cols[col++];
        String descCorta = (String) cols[col++];
        Date reprocessImages = (Date) cols[col++];
        String brandCode = (String) cols[col++];
        String brandName = (String) cols[col++];
        String mainCombination = (String) cols[col++];
        String warehouse = (String) cols[col++];
        Integer quantity = (Integer) cols[col++];
        String materialCode = (String) cols[col++];
        String materialName = (String) cols[col++];
        String materialCare = (String) cols[col++];

        InventoryItemPrestashopDTO invItem = new InventoryItemPrestashopDTO();
        invItem.setItemCode(itemCode);
        invItem.setProviderCode(providerCode);
        invItem.setItemName(itemName);
        invItem.setImgAlt(itemName);
        invItem.setSubgroupCode(subgroupCode);
        invItem.setWebName(webName);
        invItem.setKeyWords(keyWords);
        invItem.setPrice(price != null ? price.toString() : "0");
        invItem.setHeight(height != null ? height.toString() : "");
        invItem.setDepth(depth != null ? depth.toString() : "");
        invItem.setWidth(width != null ? width.toString() : "");
        invItem.setWeight(weight != null ? weight.toString() : "");
        invItem.setModel(model);
        invItem.setMainCombination(mainCombination);
        invItem.setDescription(description);
        if (descCorta == null || descCorta.trim().isEmpty()) {
            descCorta = description;
        }
        invItem.setShortDescription(descCorta);
        if (newFrom != null) {
            invItem.setNewFrom(Long.toString(newFrom.getTime()));
        }

        if (reprocessImages != null) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                if (sdf.format(reprocessImages).equals(sdf.format(new Date()))) {
                    invItem.setProcessImages("1");
                } else {
                    invItem.setProcessImages("0");
                }
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al procesar la fecha del producto. [" + reprocessImages + "]", e);
                invItem.setProcessImages("0");
            }
        } else {
            invItem.setProcessImages("0");
        }
        invItem.addStock(warehouse, quantity);

        ItemColorDTO itemColor = new ItemColorDTO(colorCode, colorName, colorHexa);
        invItem.setColor(itemColor);

        ItemMaterialDTO itemMaterial = new ItemMaterialDTO(materialCode, materialName, materialCare);
        invItem.addMaterial(itemMaterial);

        ItemBrandDTO itemBrand = new ItemBrandDTO(brandCode, brandName);
        invItem.setBrand(itemBrand);

        return invItem;
    }

    @POST
    @Path("actualizar/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response uptadeItemInfo(final ItemUpdateDTO[] items) {
        try {
            SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP("dbotero");
            ItemsServiceConnector isc = sapB1WSBean.getItemsServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            isc.updateItems(items);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.ok().build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
