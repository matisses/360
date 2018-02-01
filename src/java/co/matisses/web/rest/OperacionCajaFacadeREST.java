package co.matisses.web.rest;

import co.matisses.persistence.web.entity.OperacionCaja;
import co.matisses.persistence.web.entity.TurnoCaja;
import co.matisses.persistence.web.facade.TurnoCajaFacade;
import co.matisses.web.dto.DepositoDTO;
import co.matisses.web.dto.MedioPagoDTO;
import co.matisses.web.dto.TirillaZDTO;
import co.matisses.web.dto.TransaccionCajaDTO;
import co.matisses.web.dto.VentaPOSDTO;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("caja")
public class OperacionCajaFacadeREST extends AbstractFacade<OperacionCaja> {

    private static final Logger log = Logger.getLogger(OperacionCajaFacadeREST.class.getSimpleName());
    @PersistenceContext(unitName = "BARUWEBPU")
    private EntityManager em;
    @EJB
    private TurnoCajaFacade turnoCajaFacade;

    public OperacionCajaFacadeREST() {
        super(OperacionCaja.class);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @POST
    @Path("saldo")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarSaldoCaja(final String usuario, @Context HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(sum(valor*signo) as int) total ");
        sb.append("from ( ");
        sb.append("select o.valor, case when o.tipo = 'cambio' then -1 when o.tipo = 'deposito' then -1 when o.tipo = 'tarjeta' then 0 when tipo = 'otro' then 0 else 1 end signo ");
        sb.append("from OPERACION_CAJA o ");
        sb.append("inner join TURNO_CAJA t on t.idTurnoCaja = o.idTurnoCaja ");
        sb.append("where t.terminal = '");
        sb.append(req.getRemoteAddr());
        sb.append("' and t.usuario = '");
        sb.append(usuario);
        sb.append("' and t.fecha = cast(GETDATE() as date) ");
        sb.append("and cierre is null ");
        sb.append("and (o.anulada is null or o.anulada = 0) ");
        sb.append(") operaciones ");

        try {
            Integer saldo = (Integer) em.createNativeQuery(sb.toString()).getSingleResult();
            return Response.ok(saldo).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el saldo para la caja. ", e);
            return Response.ok(0).build();
        }
    }

    public TirillaZDTO generarDatosTirillaZ(String usuario, Integer idTurnoCaja, TirillaZDTO tirilla, String ipCaja) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(o.tipo as varchar) tipo, o.valor, o.fecha, o.justificacion ");
        sb.append("from   OPERACION_CAJA o ");
        sb.append("inner join TURNO_CAJA t on t.idTurnoCaja = o.idTurnoCaja ");
        sb.append("where  t.terminal = '");
        sb.append(ipCaja);
        sb.append("' and    t.usuario = '");
        sb.append(usuario);
        sb.append("' and    t.fecha = cast(o.fecha as date) ");
        sb.append("and    t.idTurnoCaja = ");
        sb.append(idTurnoCaja);
        sb.append(" and (o.anulada is null or o.anulada = 0)");

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            //TirillaZDTO tirilla = new TirillaZDTO();
            //tirilla.setCaja(req.getRemoteAddr());
            tirilla.setCajero(usuario.toLowerCase());
            List rows = em.createNativeQuery(sb.toString()).getResultList();
            for (Object row : rows) {
                Object[] cols = (Object[]) row;
                String tipo = (String) cols[0];
                if (tipo != null) {
                    if (tipo.equals("apertura")) {
                        Date fecha = (Date) cols[2];
                        tirilla.setHoraApertura(sdf.format(fecha));
                    } else if (tipo.equals("cierre")) {
                        Date fecha = (Date) cols[2];
                        tirilla.setHoraCierre(sdf.format(fecha));
                    } else if (tipo.equals("pago")) {
                        MedioPagoDTO pagoDto = new MedioPagoDTO();
                        pagoDto.setNombre("Efectivo");
                        pagoDto.setTransacciones(1);
                        pagoDto.setValor(((Integer) cols[1]).floatValue());
                        tirilla.agregarMedioPago(pagoDto);
                    } else if (tipo.equals("tarjeta")) {
                        MedioPagoDTO pagoDto = new MedioPagoDTO();
                        pagoDto.setNombre("Tarjetas");
                        pagoDto.setTransacciones(1);
                        pagoDto.setValor(((Integer) cols[1]).floatValue());
                        tirilla.agregarMedioPago(pagoDto);
                    } else if (tipo.equals("deposito")) {
                        DepositoDTO deposito = new DepositoDTO();
                        deposito.setNombre("Deposito ");
                        deposito.setValor((Integer) cols[1]);
                        tirilla.agregarDeposito(deposito);
                    } else if (tipo.equals("cambio")) {
                        MedioPagoDTO pagoDto = new MedioPagoDTO();
                        pagoDto.setNombre("Efectivo");
                        pagoDto.setTransacciones(1);
                        pagoDto.setValor(((Integer) cols[1]).floatValue() * -1f);
                        tirilla.agregarMedioPago(pagoDto);
                    } else if (tipo.equals("otro")) {
                        MedioPagoDTO pagoDto = new MedioPagoDTO();
                        pagoDto.setNombre("Otro");
                        pagoDto.setTransacciones(1);
                        pagoDto.setValor(((Integer) cols[1]).floatValue());
                        tirilla.agregarMedioPago(pagoDto);
                    }
                }
            }
            try {
                Date fecha = (Date) ((Object[]) rows.get(0))[2];
                tirilla.setFecha(new SimpleDateFormat("yyyy-MM-dd").format(fecha));
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al obtener la fecha del reporte. ", e);
            }

            return tirilla;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al construir el informe de cierre. ", e);
        }
        return null;
    }

    @POST
    @Path("generarZ/{usuario}/{idTurnoCaja}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarDatosTirillaZ(@PathParam("usuario") String usuario, @PathParam("idTurnoCaja") Integer idTurnoCaja, TirillaZDTO tirilla, @Context HttpServletRequest req) {
        TirillaZDTO resp = generarDatosTirillaZ(usuario, idTurnoCaja, tirilla, req.getRemoteAddr());
        if (resp != null) {
            return Response.ok(resp).build();
        }
        return Response.ok().build();
    }

    @POST
    @Path(value = "transaccion")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response registrarTransaccionCaja(final TransaccionCajaDTO transaccion, @Context final HttpServletRequest req) {
        log.log(Level.INFO, "Se recibio una transaccion. {0}", transaccion);
         //if (transaccion.getValor() == null || transaccion.getValor() == 0) {
        if (transaccion.getValor() == null ) {
            log.log(Level.WARNING, "No se ejecutara la transaccion ya que no es necesario registrar operaciones con valor cero. ");
            return Response.ok(true).build();
        }
        StringBuilder sb = new StringBuilder();

        sb.append("select top 1 idTurnoCaja, usuario, terminal, fecha ");
        sb.append("from   turno_caja ");
        sb.append("where  usuario = '");
        sb.append(transaccion.getUsuario());
        sb.append("' and    terminal = '");
        sb.append(req.getRemoteAddr());
        sb.append("' and    fecha = cast(getdate() as date) ");
        sb.append("and cierre is null ");
        sb.append("order by idTurnoCaja desc ");

        TurnoCaja turno = null;
        try {
            Object[] cols = (Object[]) em.createNativeQuery(sb.toString()).getSingleResult();
            turno = new TurnoCaja((Integer) cols[0], (String) cols[1], (String) cols[2], (Date) cols[3]);
        } catch (NoResultException e) {
            //Si no existe un turno para la caja, lo crea
            turno = new TurnoCaja(null, req.getRemoteAddr(), transaccion.getUsuario(), new Date());
            turnoCajaFacade.create(turno);
            log.log(Level.INFO, "Se creo el turno_caja #{0}", turno.getIdTurnoCaja());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar el turno para la caja. ", e);
        }

        if (turno != null && turno.getIdTurnoCaja() != null) {
            OperacionCaja entidad = new OperacionCaja();
            entidad.setFecha(new Date());
            entidad.setTipo(transaccion.getTipo());
            entidad.setValor(transaccion.getValor());
            entidad.setJustificacion(transaccion.getJustificacion());
            entidad.setIdTurno(turno.getIdTurnoCaja());

            try {
                super.create(entidad);
                if (transaccion.getCierre() != null && transaccion.getCierre()) {
                    StringBuilder sbUpdate = new StringBuilder();
                    sbUpdate.append("update turno_caja ");
                    sbUpdate.append("set cierre = getdate() ");
                    sbUpdate.append("where idTurnoCaja = ");
                    sbUpdate.append(turno.getIdTurnoCaja());
                    try {
                        int result = em.createNativeQuery(sbUpdate.toString()).executeUpdate();
                        if (result == 1) {
                            log.log(Level.INFO, "Se actualizo correctamente la fecha de cierre del turno {0}", turno.getIdTurnoCaja());
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al cerrar el turno. ", e);
                    }
                }

                if (transaccion.getAnulacion() != null && transaccion.getAnulacion()) {
                    StringBuilder sbUpdate = new StringBuilder();
                    sbUpdate.append("update OPERACION_CAJA set anulada = 1 ");
                    sbUpdate.append("where idOperacionCaja in ( ");
                    sbUpdate.append("select idOperacionCaja from ( ");
                    sbUpdate.append("select idOperacionCaja ");
                    sbUpdate.append(", idTurnoCaja, fecha ");
                    sbUpdate.append(", case when tipo = 'cambio' then 'pago' else tipo end tipo ");
                    sbUpdate.append(", case when tipo = 'cambio' then valor * -1 else valor end valor ");
                    sbUpdate.append(", justificacion, anulada ");
                    sbUpdate.append("from OPERACION_CAJA ");
                    sbUpdate.append("where idTurnoCaja = ");
                    sbUpdate.append(turno.getIdTurnoCaja());
                    sbUpdate.append(" and justificacion = '");
                    sbUpdate.append(transaccion.getJustificacion());
                    sbUpdate.append("') operaciones where tipo = '");
                    sbUpdate.append(transaccion.getTipo());
                    sbUpdate.append("')");
                    try {
                        int result = em.createNativeQuery(sbUpdate.toString()).executeUpdate();
                        if (result == 1) {
                            log.log(Level.INFO, "Se marcaron anuladas las transacciones relacionadas con la transaccion {0}", transaccion);
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al marcar anuladas las transacciones relacionadas. ", e);
                    }
                }
                return Response.ok(true).build();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al insertar la transaccion. ", e);
            }
        }
        return Response.ok(false).build();
    }

    @POST
    @Path(value = "transacciones")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public Response registrarTransaccionCaja(final TransaccionCajaDTO[] transacciones, @Context final HttpServletRequest req) {
        log.log(Level.INFO, "Se recibieron {0} transacciones", transacciones != null ? transacciones.length : 0);

        for (TransaccionCajaDTO transaccion : transacciones) {
            log.log(Level.INFO, "Procesando transaccion {0}", transaccion);
            if (transaccion.getValor() == null || transaccion.getValor() == 0) {
                log.log(Level.WARNING, "No se ejecutara la transaccion ya que no es necesario registrar operaciones con valor cero. ");
                continue;
            }
            StringBuilder sb = new StringBuilder();
            sb.append("select top 1 idTurnoCaja, usuario, terminal, fecha ");
            sb.append("from   turno_caja ");
            sb.append("where  usuario = '");
            sb.append(transaccion.getUsuario());
            sb.append("' and    terminal = '");
            sb.append(req.getRemoteAddr());
            sb.append("' and    fecha = cast(getdate() as date) ");
            sb.append("and cierre is null ");
            sb.append("order by idTurnoCaja desc ");

            TurnoCaja turno = null;
            try {
                Object[] cols = (Object[]) em.createNativeQuery(sb.toString()).getSingleResult();
                turno = new TurnoCaja((Integer) cols[0], (String) cols[1], (String) cols[2], (Date) cols[3]);
            } catch (NoResultException e) {
                //Si no existe un turno para la caja, lo crea
                turno = new TurnoCaja(null, req.getRemoteAddr(), transaccion.getUsuario(), new Date());
                turnoCajaFacade.create(turno);
                log.log(Level.INFO, "Se creo el turno_caja #{0}", turno.getIdTurnoCaja());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al consultar el turno para la caja. ", e);
            }

            if (turno != null && turno.getIdTurnoCaja() != null) {
                OperacionCaja entidad = new OperacionCaja();
                entidad.setFecha(new Date());
                entidad.setTipo(transaccion.getTipo());
                entidad.setValor(transaccion.getValor());
                entidad.setJustificacion(transaccion.getJustificacion());
                entidad.setIdTurno(turno.getIdTurnoCaja());
                try {
                    super.create(entidad);
                    if (transaccion.getCierre() != null && transaccion.getCierre()) {
                        StringBuilder sbUpdate = new StringBuilder();
                        sbUpdate.append("update turno_caja ");
                        sbUpdate.append("set cierre = getdate() ");
                        sbUpdate.append("where idTurnoCaja = ");
                        sbUpdate.append(turno.getIdTurnoCaja());
                        try {
                            int result = em.createNativeQuery(sbUpdate.toString()).executeUpdate();
                            if (result == 1) {
                                log.log(Level.INFO, "Se actualizo correctamente la fecha de cierre del turno {0}", turno.getIdTurnoCaja());
                            }
                        } catch (Exception e) {
                            log.log(Level.SEVERE, "Ocurrio un error al cerrar el turno. ", e);
                        }
                    }
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al insertar la transaccion. ", e);
                }

                if (transaccion.getAnulacion() != null && transaccion.getAnulacion()) {
                    StringBuilder sbUpdate = new StringBuilder();
                    sbUpdate.append("update OPERACION_CAJA set anulada = 1 ");
                    sbUpdate.append("where idOperacionCaja in ( ");
                    sbUpdate.append("select idOperacionCaja from ( ");
                    sbUpdate.append("select idOperacionCaja ");
                    sbUpdate.append(", idTurnoCaja, fecha ");
                    sbUpdate.append(", case when tipo = 'cambio' then 'pago' else tipo end tipo ");
                    sbUpdate.append(", case when tipo = 'cambio' then valor * -1 else valor end valor ");
                    sbUpdate.append(", justificacion, anulada ");
                    sbUpdate.append("from OPERACION_CAJA ");
                    sbUpdate.append("where idTurnoCaja = ");
                    sbUpdate.append(turno.getIdTurnoCaja());
                    sbUpdate.append(" and justificacion = '");
                    sbUpdate.append(transaccion.getJustificacion());
                    sbUpdate.append("') operaciones where tipo = '");
                    sbUpdate.append(transaccion.getTipo());
                    sbUpdate.append("')");
                    try {
                        int result = em.createNativeQuery(sbUpdate.toString()).executeUpdate();
                        if (result == 1) {
                            log.log(Level.INFO, "Se marcaron anuladas las transacciones relacionadas con la transaccion {0}", transaccion);
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al marcar anuladas las transacciones relacionadas. ", e);
                    }
                }
            }
        }

        return Response.ok(true).build();
    }

    @POST
    @Path(value = "consultartransacciones/")
    @Consumes(value = {MediaType.APPLICATION_JSON})
    @Produces(value = {MediaType.APPLICATION_JSON})
    public List<TransaccionCajaDTO> consultarTransaccionesFactura(final VentaPOSDTO factura) {
        List<TransaccionCajaDTO> transacciones = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        sb.append("select cast('pago' as varchar(20)) as tipo, isnull(cast(sum(case when tipo = 'cambio' then valor*-1 else valor end)*-1 as int), 0) as valor ");
        sb.append("from OPERACION_CAJA ");
        sb.append("where justificacion = '");
        sb.append(factura.getPrefijoFactura()).append(factura.getNumeroFactura().substring(2));
        sb.append("' and tipo in ('pago','cambio') ");//TODO: parametrizar tipos de operacion 
        sb.append("union all ");
        sb.append("select cast(tipo as varchar(20)) as pago, cast(valor*-1 as int) as valor ");
        sb.append("from OPERACION_CAJA ");
        sb.append("where justificacion = '");
        sb.append(factura.getPrefijoFactura()).append(factura.getNumeroFactura().substring(2));
        sb.append("' and tipo in ('tarjeta','otro') ");//TODO: parametrizar tipos de operacion 
        try {
            List<Object[]> rows = em.createNativeQuery(sb.toString()).getResultList();
            for (Object[] row : rows) {
                TransaccionCajaDTO dto = new TransaccionCajaDTO();
                dto.setTipo((String) row[0]);
                dto.setValor((Integer) row[1]);

                transacciones.add(dto);
            }
            return transacciones;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar las transacciones para la factura " + factura.getNumeroFactura(), e);
            return new ArrayList<>();
        }
    }
}
