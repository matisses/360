package co.matisses.web.rest;

import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.web.dto.SesionPOSDTO;
import co.matisses.web.dto.ValidarPermisosDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.util.Calendar;
import java.util.Date;
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
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("session")
public class POSSessionValidatorREST {

    private static final Logger log = Logger.getLogger(POSSessionValidatorREST.class.getSimpleName());

    @PersistenceContext(unitName = "BARUWEBPU")
    private EntityManager em;
    @EJB
    private SaldoItemInventarioFacade saldoItemFacade;
    @Inject
    private BaruApplicationMBean applicationBean;

    @GET
    @Path("{sessionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarSesion(@PathParam("sessionId") String sessionId, @Context HttpServletRequest req) {
        SesionPOSDTO response = new SesionPOSDTO();
        if (sessionId == null || sessionId.trim().isEmpty()) {
            response.setSesionValida(false);
            response.setMensajeError("No se recibió un id de sesión para validar");
            return Response.ok(response).build();
        }
        response = applicationBean.obtenerSesionPOS(sessionId);
        if (response == null) {
            response = new SesionPOSDTO(false, false, null, null, null, null, null, null, "No se encontró una sesión válida en 360");
            return Response.ok(response).build();
        }
        if (!response.getIp().equals(req.getRemoteAddr())) {
            response = new SesionPOSDTO(false, false, null, null, null, null, null, null, "La terminal actual es diferente de la que usó para iniciar sesión");
            return Response.ok(response).build();
        }
        if (!response.isSesionValida()) {
            return Response.ok(response).build();
        }

        SesionPOSDTO responseTmp = validarEstadoCaja(response.getIp(), response.getUsuario());
        if (!responseTmp.isSesionValida()) {
            return Response.ok(responseTmp).build();
        }
        response.setIdTurnoCaja(responseTmp.getIdTurnoCaja());
        response.setCajaAbierta(responseTmp.isCajaAbierta());
        //consulta el nro de cuenta para efectivo correspondiente a esta caja
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(cuentaEfectivo as varchar) cuenta, cast(isnull(alias,ip) as varchar) alias ");
        sb.append("from BWS_TERMINAL_POS ");
        sb.append("where ip = '");
        sb.append(response.getIp());
        sb.append("' ");
        try {
            Object[] datosCaja = (Object[]) em.createNativeQuery(sb.toString()).getSingleResult();
            String cuenta = (String) datosCaja[0];
            String nombreCaja = (String) datosCaja[1];
            if (cuenta == null) {
                throw new Exception("No se ha configurado una cuenta para la terminal " + response.getIp());
            }
            response.setCuentaEfectivo(cuenta);
            response.setNombreCaja(nombreCaja);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar la cuenta de efectivo para la caja " + response.getIp(), e);
            response = new SesionPOSDTO(false, false, null, null, null, null, null, null, "Ocurrio un error al consultar la cuenta de efectivo para la caja. ");
            return Response.ok(response).build();
        }
        //consulta y configura el saldo de tarjetas de regalo para el almacen
        response.setTarjetasRegaloDisponibles(consultarSaldoTarjetasRegalo(response.getAlmacen()));
        return Response.ok(response).build();
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    private Long consultarSaldoTarjetasRegalo(String almacen) {
        return saldoItemFacade.consultarSaldoTarjetaRegalo(almacen);
    }

    private SesionPOSDTO validarEstadoCaja(String ip, String usuario) {
        SesionPOSDTO response = new SesionPOSDTO();
        StringBuilder sb = new StringBuilder();
        sb.append("select top 1 case when cierre is null then 1 else 0 end as estado ");
        sb.append("from TURNO_CAJA ");
        sb.append("where terminal = '");
        sb.append(ip);
        sb.append("' order by fecha desc, idTurnoCaja desc ");
        int estado = -1;
        try {
            Object res = em.createNativeQuery(sb.toString()).getSingleResult();
            if (res == null) {
                estado = 0;
            } else {
                estado = (Integer) res;
            }
        } catch (NoResultException e) {
            estado = 0;
        } catch (Exception e) {
            estado = -1;
        }
        try {
            switch (estado) {
                case 0:
                    //la caja se encuentra cerrada
                    response.setSesionValida(true);
                    response.setCajaAbierta(false);
                    return response;
                case 1:
                    //la caja se encuentra abierta, se deben validar la fecha y usuaro de apertura
                    //si alguno de los dos es diferente de los actuales, se genera error
                    response.setCajaAbierta(true);

                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("select top 1 t.idTurnoCaja, t.usuario, o.fecha ");
                    sb2.append("from   OPERACION_CAJA o ");
                    sb2.append("inner join TURNO_CAJA t on t.idTurnoCaja = o.idTurnoCaja ");
                    sb2.append("where  t.terminal = '");
                    sb2.append(ip);
                    sb2.append("'and    o.tipo = 'apertura' ");
                    sb2.append("order by o.fecha desc ");

                    Object[] obj = (Object[]) em.createNativeQuery(sb2.toString()).getSingleResult();
                    Integer idTurnoCaja = (Integer) obj[0];
                    String usr = (String) obj[1];
                    Date fecha = (Date) obj[2];

                    if (!usuario.equals(usr)) {
                        response.setMensajeError("Esta terminal fue abierta por otro usuario (" + usr + "). Es necesario que ese usuario ejecute un cierre de caja antes de que otro usuario pueda utilizarla.");
                        response.setSesionValida(false);
                        log.log(Level.SEVERE, response.getMensajeError());
                        return response;
                    }

                    if (!DateUtils.truncatedEquals(fecha, new Date(), Calendar.DATE)) {
                        response.setMensajeError("Esta terminal fue abierta en días pasados y nunca se ejecutó el cierre de caja correspondiente. Comuníquese con el departamento de sistemas para obtener instrucciones.");
                        response.setSesionValida(false);
                        log.log(Level.SEVERE, response.getMensajeError());
                        return response;
                    }
                    response.setIdTurnoCaja(idTurnoCaja.longValue());
                    response.setSesionValida(true);
                    return response;
                default:
                    response.setCajaAbierta(false);
                    //la caja se encuentra con inconsistencias en el historial de estados y no puede ser operada
                    response.setMensajeError("Esta terminal presenta inconsistencias en los procesos de cierre y apertura. Comuníquese con el departamento de sistemas para poder utilizala.");
                    response.setSesionValida(false);
                    log.log(Level.SEVERE, response.getMensajeError());
                    return response;
            }
        } catch (Exception e) {
            response.setMensajeError("No fue posible validar el estado de la caja.");
            response.setSesionValida(false);
            log.log(Level.SEVERE, "Ocurrio un error al validar el estado de la caja. ", e);
            return response;
        }
    }

    @GET
    @Path("recargarterminales")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response recargarTerminalesAutorizadas() {
        applicationBean.recargarTerminalesPOSAutorizadas();
        return Response.ok().build();
    }

    @POST
    @Path("validar")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response validarPermisos(final ValidarPermisosDTO permisosDto) {
        try {
            Boolean resp = applicationBean.usuarioPuede(permisosDto.getUsuario(), permisosDto.getClave(), permisosDto.getAccion(), permisosDto.getObjeto());
            return Response.ok(resp).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudieron validar los permisos del usuario " + permisosDto.getUsuario() + ". " + e.getMessage(), e);
            return Response.ok(Boolean.FALSE).build();
        }
    }
}
