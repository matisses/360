package co.matisses.web.rest.regalos;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ValorActivoFiltro;
import co.matisses.persistence.web.facade.ValorActivoFiltroFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("filtros")
public class FiltrosProductoREST implements Serializable {

    private static final Logger log = Logger.getLogger(FiltrosProductoREST.class.getSimpleName());

    @EJB
    private ValorActivoFiltroFacade filtroFacade;
    @EJB
    private ItemInventarioFacade itemFacade;
    @EJB
    private ValorActivoFiltroFacade valorActivoFiltroFacade;

    public FiltrosProductoREST() {
    }

    @GET
    @Path("listar")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response listarFiltrosDisponibles() {
        long inicio = System.currentTimeMillis();
        List<ValorActivoFiltro> entidades = filtroFacade.obtenerFiltrosOrdenados();
        Map<String, List<FiltroProducto>> filtros = new HashMap<>();
        for (ValorActivoFiltro entidad : entidades) {
            String tipo = entidad.getTipo();
            if (tipo != null) {
                FiltroProducto filtro = new FiltroProducto(entidad.getValor(), entidad.getCodigoPadre(), entidad.getTipoPadre(), entidad.getInfoAdicional());
                if (filtros.containsKey(tipo)) {
                    if (filtros.get(tipo).contains(filtro)) {
                        filtros.get(tipo).get(filtros.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtros.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtros.put(tipo, valores);
                }
            }
        }
        long fin = System.currentTimeMillis();
        log.log(Level.INFO, "La consulta de filtros tardo {0}ms", (fin - inicio));
        return Response.ok(filtros).build();
    }

    @POST
    @Path("obtenerconfiltro")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response obtenerFiltros(Map<String, List<FiltroProducto>> filtros) {
        if (filtros == null || filtros.isEmpty()) {
            return listarFiltrosDisponibles();
        }
        Map<String, List<String>> filtrosAgrupados = new HashMap<>();
        for (String key : filtros.keySet()) {
            for (FiltroProducto filtro : filtros.get(key)) {
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
        List<String> proveedoresConFiltroColeccion = new ArrayList<>();
        proveedoresConFiltroColeccion.add("222PE");
        proveedoresConFiltroColeccion.add("226PE");
        proveedoresConFiltroColeccion.add("238PE");
        proveedoresConFiltroColeccion.add("241PE");
        //0.  codDpto
        //1.  nombreDpto
        //2.  codGrupo
        //3.  nombreGrupo
        //4.  codSubgrupo
        //5.  nombreSubgrupo
        //6.  codMarca
        //7.  nombreMarca
        //8.  codColor
        //9.  nombreColor
        //10. codMaterial
        //11. nombreMaterial
        //12. coleccion
        //13. proveedor 
        List<Object[]> resultado = itemFacade.obtenerFiltrosVista(filtrosAgrupados);
        Map<String, List<FiltroProducto>> filtrosDisponibles = new HashMap<>();
        for (Object[] row : resultado) {
            String tipo;
            //Departamento
            if (row[0] != null) {
                tipo = "DEP";
                FiltroProducto filtro = new FiltroProducto((String) row[0], (String) row[1]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Grupo
            if (row[2] != null) {
                tipo = "GRU";
                FiltroProducto filtro = new FiltroProducto((String) row[2], (String) row[3]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Subgrupo
            if (row[4] != null) {
                tipo = "SUB";
                FiltroProducto filtro = new FiltroProducto((String) row[4], (String) row[5]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Marca
            if (row[6] != null) {
                tipo = "MAR";
                FiltroProducto filtro = new FiltroProducto((String) row[6], (String) row[7]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Color
            if (row[8] != null) {
                tipo = "COL";
                FiltroProducto filtro = new FiltroProducto((String) row[8], (String) row[9]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Material
            if (row[10] != null) {
                tipo = "MAT";
                FiltroProducto filtro = new FiltroProducto((String) row[10], (String) row[11]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
            //Coleccion
            if (row[12] != null && proveedoresConFiltroColeccion.contains((String) row[13])) {
                tipo = "CLC";
                FiltroProducto filtro = new FiltroProducto(null, (String) row[12]);
                if (filtrosDisponibles.containsKey(tipo)) {
                    if (filtrosDisponibles.get(tipo).contains(filtro)) {
                        filtrosDisponibles.get(tipo).get(filtrosDisponibles.get(tipo).indexOf(filtro)).addInfoAdicional(filtro.getInfoAdicional().get(0));
                    } else {
                        filtrosDisponibles.get(tipo).add(filtro);
                    }
                } else {
                    List<FiltroProducto> valores = new ArrayList<>();
                    valores.add(filtro);
                    filtrosDisponibles.put(tipo, valores);
                }
            }
        }
        return Response.ok(filtrosDisponibles).build();
    }

    @GET
    @Path("actualizar")
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response actualizarFiltros() {
        log.log(Level.INFO, "Inicia obtencion de los valores para los filtros");
        //List<String[]> filtrosObtenidos = new ArrayList<>();
        List<Object[]> datos = itemFacade.obtenerFiltrosDisponibles();
        log.log(Level.INFO, "Finaliza obtencion de los valores para el filtro");
        log.log(Level.INFO, "Inicia proceso de actualizacion de filtros");
        if (datos != null && !datos.isEmpty()) {
            log.log(Level.INFO, "Se obtuvieron [{0}] filtros para agregar", datos.size());
            try {
                valorActivoFiltroFacade.eliminarRegistrosActuales();
                log.log(Level.INFO, "Se eliminaron los registros actuales");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar los registros existentes. {0}", e.getMessage());
                return Response.ok("Ocurrio un error al eliminar los registros existentes. " + e.getMessage()).build();
            }
            try {
                valorActivoFiltroFacade.insertarRegistrosNuevos(datos);
                log.log(Level.INFO, "Se insertaron los nuevos filtros");
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al insertar los nuevos filtros. {0}", e.getMessage());
                return Response.ok("Ocurrio un error al insertar los nuevos filtros" + e.getMessage()).build();
            }
        }
        log.log(Level.INFO, "Finaliza proceso de actualizacion de filtros");
        return Response.ok("Filtros actualizados con exito").build();
    }

}
