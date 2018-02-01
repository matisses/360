package co.matisses.web.rest.regalos;

import co.matisses.web.rest.regalos.dto.ConsultaProductosDTO;
import co.matisses.web.rest.regalos.dto.ConsultaProductosResponseDTO;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ProgramacionDescuento;
import co.matisses.persistence.web.facade.ProgramacionDescuentoFacade;
import co.matisses.web.dto.InventoryItemDTO;
import co.matisses.web.dto.PuntoVentaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
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
@Path("consultaproductos")
public class ConsultaProductosREST {

    private static final Logger log = Logger.getLogger(ConsultaProductosREST.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private ProgramacionDescuentoFacade programacionDescuentoFacade;
    @Inject
    private BaruApplicationMBean applicationBean;

    @POST
    @Path("filtrar/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarProductos(final ConsultaProductosDTO filtros) {
        long inicio = System.currentTimeMillis();
        log.log(Level.INFO, "Consultando productos con filtros {0}", filtros);
        Map<String, List<String>> filtrosAgrupados = new HashMap<>();
        for (String key : filtros.getFiltros().keySet()) {
            for (FiltroProducto filtro : filtros.getFiltros().get(key)) {
                if (filtro.getInfoAdicional().isEmpty() || (filtro.getInfoAdicional().size() == 1
                        && (filtro.getInfoAdicional().get(0) == null || filtro.getInfoAdicional().get(0).equals("")))) {
                    if (filtrosAgrupados.containsKey(key)) {
                        filtrosAgrupados.get(key).add(filtro.getValor());
                    } else {
                        List<String> valores = new ArrayList<>();
                        valores.add(filtro.getValor());
                        filtrosAgrupados.put(key, valores);
                    }
                } else if (filtrosAgrupados.containsKey(key)) {
                    filtrosAgrupados.get(key).addAll(filtro.getInfoAdicional());
                } else {
                    filtrosAgrupados.put(key, filtro.getInfoAdicional());
                }
            }
        }
        List resultado = itemInventarioFacade.consultarProductosVista(filtros.getPagina(), filtros.getRegistrosPagina(),
                filtrosAgrupados, filtros.getOrderBy(), filtros.getSortOrder());
        if (resultado == null) {
            log.log(Level.SEVERE, "No se obtuvieron resultados con los filtros seleccionados");
            return Response.ok().build();
        }
        ArrayList<InventoryItemDTO> productos = new ArrayList();
        for (Object row : resultado) {
            int col = 0;
            Object[] cols = (Object[]) row;
            String itemCode = (String) cols[col++];
            String frgnName = (String) cols[col++];
            String refPro = (String) cols[col++];
            Integer precio = (Integer) cols[col++];
            Integer saldo = (Integer) cols[col++];
            Date fechaNuevo = (Date) cols[col++];
            String desc = (String) cols[col++];

            InventoryItemDTO dto = new InventoryItemDTO();
            dto.setItemCode(itemCode);
            dto.setItemName(frgnName);
            dto.setProviderCode(refPro);
            dto.setPrice(Integer.toString(precio));
            dto.setAvailableQuantity(saldo);
            dto.setShortDescription(desc);

            productos.add(dto);
        }
        int totalProductos = itemInventarioFacade.consultarTotalRegistrosVista(filtrosAgrupados);
        long fin = System.currentTimeMillis();
        log.log(Level.INFO, "La consulta de productos tardo {0}ms", (fin - inicio));
        return Response.ok(new ConsultaProductosResponseDTO(totalProductos, productos)).build();
    }

    private List<String> consultarAlmacenesVenta() {
        List<String> almacenes = new ArrayList<>();
        for (PuntoVentaDTO puntoVenta : applicationBean.getSucursales()) {
            almacenes.add(puntoVenta.getCodigo());
        }
        return almacenes;
    }

    @GET
    @Path("descuento/{canal}/{referencia}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response obtenerDescuento(@PathParam("canal") String canal, @PathParam("referencia") String referencia) {
        if (canal == null || canal.isEmpty()) {
            log.log(Level.SEVERE, "No se recibio un canal valido");
            return Response.ok("0").build();
        }
        if (referencia == null || referencia.isEmpty()) {
            log.log(Level.SEVERE, "No se recibio una referencia valida");
            return Response.ok("0").build();
        }

        ProgramacionDescuento programacion = programacionDescuentoFacade.consultarDescuentosReferencia(canal, referencia);

        if (programacion != null) {
            return Response.ok(programacion.getPorcentaje()).build();
        }
        log.log(Level.SEVERE, "No se encontraron descuentos activos para el canal {0} y referencia {1}", new Object[]{canal, referencia});
        return Response.ok("0").build();
    }
}
