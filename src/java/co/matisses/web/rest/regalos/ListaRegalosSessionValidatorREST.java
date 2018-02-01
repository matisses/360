package co.matisses.web.rest.regalos;

import co.matisses.persistence.sap.entity.DireccionSocioDeNegocios;
import co.matisses.web.rest.regalos.dto.ClienteResponseDTO;
import co.matisses.web.rest.regalos.dto.ValidacionCodigoDTO;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CodigoSeguridad;
import co.matisses.persistence.web.facade.CodigoSeguridadFacade;
import co.matisses.web.bcs.client.EmailServiceClient;
import co.matisses.web.bcs.client.SMSServiceClient;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.MensajeTextoDTO;
import co.matisses.web.dto.SesionListaRegalosDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.dto.BCSMailMessageDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
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
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("listaregalos/sesion")
public class ListaRegalosSessionValidatorREST {

    private static final Logger log = Logger.getLogger(ListaRegalosSessionValidatorREST.class.getSimpleName());
    @Inject
    private BaruApplicationMBean applicationBean;
    @Inject
    private UserSessionInfoMBean userSession;
    @EJB
    private SocioDeNegociosFacade clienteFacade;
    @EJB
    private CodigoSeguridadFacade codigoSeguridadFacade;
    @EJB
    private DireccionSocioDeNegociosFacade direccionFacade;

    @GET
    @Path("{sessionId}")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response validarSesion(@PathParam("sessionId") String sessionId, @Context HttpServletRequest req) {
        //Valida que exista una sesion activa 
        SesionListaRegalosDTO response = new SesionListaRegalosDTO();
        if (sessionId == null || sessionId.trim().isEmpty()) {
            response.setSesionValida(false);
            response.setMensajeError("No se recibió un id de sesión válido");
            return Response.ok(response).build();
        }
        response = applicationBean.obtenerSesionListaRegalos(sessionId);
        if (response == null) {
            response = new SesionListaRegalosDTO();
            response.setMensajeError("No se encontró una sesión válida.");
            response.setSesionValida(false);
            return Response.ok(response).build();
        }
        if (!response.isSesionValida()) {
            return Response.ok(response).build();
        }
        return Response.ok(response).build();
    }

    @GET
    @Path("crear")
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response crearSesion() {
        //Registra la sesion en el bean de aplicacion 
        //TODO: configurar tipo de sesion (invitado, propietario, administrador, asesor)
        SesionListaRegalosDTO sesion = new SesionListaRegalosDTO();
        sesion.setIdSession(RandomStringUtils.randomAlphanumeric(32));
        sesion.setTipo(SesionListaRegalosDTO.TipoSesion.INVITADO);
        log.log(Level.INFO, "Se creo la sesion {0}", sesion.getIdSession());
        applicationBean.agregarSesionListaRegalos(sesion.getIdSession(), sesion);
        return Response.ok(sesion).build();
    }

    @POST
    @Path("validarcliente/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON + ";charset=utf-8"})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarDatosCliente(final ClienteSAPDTO cliente, @Context HttpServletRequest req) {
        if (cliente == null) {
            log.log(Level.WARNING, "No se recibio informacion del cliente para validar");
            return Response.ok(new ClienteResponseDTO(-1, "No se recibió información para validar")).build();
        }
        if (cliente.getNit() == null || cliente.getNit().trim().isEmpty()) {
            log.log(Level.WARNING, "No se recibio la cedula para validar");
            return Response.ok(new ClienteResponseDTO(-1, "No se recibió la cédula del usuario")).build();
        }
        if (cliente.getEmail() == null || cliente.getEmail().trim().isEmpty()) {
            log.log(Level.WARNING, "No se recibio el correo para validar");
            return Response.ok(new ClienteResponseDTO(-1, "No se recibió el correo del usuario")).build();
        }
        log.log(Level.INFO, "Validando usuario {0}", cliente.toString());
        if (!cliente.getNit().toUpperCase().endsWith("CL")) {
            cliente.setNit(cliente.getNit().concat("CL"));
        }
        SocioDeNegocios entidad = clienteFacade.findByCardCode(cliente.getNit());
        if (entidad == null) {
            SocioDeNegocios entidad2 = clienteFacade.findByEmail(cliente.getEmail());
            if (entidad2 == null || entidad2.getCardCode() == null) {
                log.log(Level.WARNING, "No se encontro un cliente con el nit {0} ni el correo {1}", new Object[]{cliente.getNit(), cliente.getEmail()});
                return Response.ok(new ClienteResponseDTO(-2, null)).build();
            }
            log.log(Level.WARNING, "Se encontro un cliente con correo {0}, pero las cedulas no coinciden. {1} != {2}",
                    new Object[]{cliente.getEmail(), cliente.getNit(), entidad2.getCardCode()});
            return Response.ok(new ClienteResponseDTO(-1, "La información ingresada no coincide. Por favor intenta nuevamente o comunícate con nuestra línea de servicio al cliente")).build();
        } else if (entidad.getEmail() != null && !entidad.getEmail().equalsIgnoreCase(cliente.getEmail())) {
            log.log(Level.WARNING, "Se encontro un cliente con cedula {0}, pero los correos no coinciden. {1} != {2}",
                    new Object[]{cliente.getNit(), cliente.getEmail(), entidad.getEmail()});
            return Response.ok(new ClienteResponseDTO(-1, "La información ingresada no coincide. Por favor intenta nuevamente o comunícate con nuestra línea de servicio al cliente")).build();
        }
        if (entidad.getCelular() == null) {
            //El celular del cliente no esta bien configurado. Validando si tiene celular en la direccion de facturacion
            List<DireccionSocioDeNegocios> direcciones = direccionFacade.findByCardCode(entidad.getCardCode());
            for (DireccionSocioDeNegocios direccion : direcciones) {
                if (direccion.getDireccionSocioDeNegociosPK().getAdresType() == 'B') {
                    if (direccion.getBuilding() != null) {
                        entidad.setCelular(direccion.getBuilding());
                        break;
                    }
                }
            }
        }
        if (entidad.getEmail() == null) {
            //El celular del cliente no esta bien configurado. Validando si tiene celular en la direccion de facturacion
            List<DireccionSocioDeNegocios> direcciones = direccionFacade.findByCardCode(entidad.getCardCode());
            for (DireccionSocioDeNegocios direccion : direcciones) {
                if (direccion.getAddrType().equals("B")) {
                    if (direccion.getCounty() != null) {
                        entidad.setEmail(direccion.getCounty());
                        break;
                    }
                }
            }
        }
        if (entidad.getCelular() == null || entidad.getEmail() == null) {
            return Response.ok(new ClienteResponseDTO(-1,
                    "No tienes correctamente registrados tu correo o tu celular. Comunícate con servicio al cliente para ayudarte. ")).build();
        }
        log.log(Level.INFO, "Cliente validado con exito. Celular de confirmacion SMS {0}", entidad.getCelular());
        String codigo = codigoEnviadoHoy(entidad.getEmail(), entidad.getCelular());
        if (codigo == null) {
            return generarCodigo(entity2Dto(entidad), req);
        } else {
            log.log(Level.INFO, "El cliente ya tiene un codigo activo para hoy ({0}). Solo se enviara otro si se solicita uno nuevo. ", codigo);
            return Response.ok(new ClienteResponseDTO(1, null, entity2Dto(entidad))).build();
        }
    }

    private ClienteSAPDTO entity2Dto(SocioDeNegocios entidad) {
        ClienteSAPDTO dto = new ClienteSAPDTO();
        dto.setApellido1(entidad.getApellido1());
        dto.setApellido2(entidad.getApellido2());
        dto.setNombres(entidad.getNombres());
        dto.setRazonSocial(entidad.getRazonSocial());
        dto.setNit(entidad.getCardCode());
        dto.setEmail(entidad.getEmail());
        dto.setCelular(entidad.getCelular());
        //TODO: agregar datos de direccion
        return dto;
    }

    private void guardarCodigo(String random, String celular, String email) {
        CodigoSeguridad codigo = new CodigoSeguridad();
        codigo.setCelular(celular);
        codigo.setCodigo(random);
        codigo.setCorreo(email);
        codigo.setFecha(new Date());
        codigoSeguridadFacade.create(codigo);
    }

    private String codigoEnviadoHoy(String email, String celular) {
        CodigoSeguridad codigo = codigoSeguridadFacade.consultarUltimoCodigo(email, celular);
        if (codigo != null) {
//            Calendar fechaCodigo = new GregorianCalendar();
//            fechaCodigo.setTime(codigo.getFecha());
//
//            Calendar hoy = new GregorianCalendar();
//            if (fechaCodigo.get(Calendar.YEAR) == hoy.get(Calendar.YEAR)
//                    && fechaCodigo.get(Calendar.MONTH) == hoy.get(Calendar.MONTH)
//                    && fechaCodigo.get(Calendar.DATE) == hoy.get(Calendar.DATE)) {
            return codigo.getCodigo();
//            }
        }
        return null;
    }

    private String generarCodigoSecreto() {
        return StringUtils.leftPad(String.valueOf((int) (Math.random() * 1000000)), 6, "0");
    }

    @POST
    @Path("generarcodigo/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response generarCodigo(final ClienteSAPDTO cliente, @Context HttpServletRequest req) {
        try {
            //TODO: limitar el numero de mensajes por usuario por hora?dia?
            String random = generarCodigoSecreto();
            log.log(Level.INFO, "Se genero el codigo {0} para el numero {1} y correo {2}",
                    new Object[]{random, cliente.getCelular(), cliente.getEmail()});
            guardarCodigo(random, cliente.getCelular(), cliente.getEmail());
            //TODO: consultar numero de sms enviados hoy a ese numero
            if (cliente.getCelular() != null && cliente.getCelular().trim().length() == 10) {
                enviarSMS(random, cliente.getNombres(), cliente.getCelular(), req.getRemoteAddr());
            }
            if (cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty()) {
                enviarEmail(random, cliente.getNombres(), cliente.getEmail());
            }
            return Response.ok(new ClienteResponseDTO(0, null, cliente)).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar el codigo de seguridad. ", e);
            return Response.ok(new ClienteResponseDTO(-1, "Ocurrio un error al enviar el codigo de seguridad.")).build();
        }
    }

    @POST
    @Path("validarcodigo/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response validarCodigo(final ValidacionCodigoDTO validacion) {
        log.log(Level.INFO, "Validando codigo {0}", validacion);
        CodigoSeguridad entidad = codigoSeguridadFacade.consultarUltimoCodigo(validacion.getCliente().getEmail(), validacion.getCliente().getCelular());
        if (entidad == null || !entidad.getCodigo().equals(validacion.getCodigo())) {
            log.log(Level.INFO, "El codigo ingresado no corresponde con el ultimo registro. {0}", entidad);
            return Response.ok(new ValidacionCodigoDTO()).build();
        }
        //Crea una nueva sesion y configura el perfil en modo propietario 
        SesionListaRegalosDTO sesion = new SesionListaRegalosDTO();
        sesion.setIdSession(RandomStringUtils.randomAlphanumeric(32));
        sesion.setTipo(SesionListaRegalosDTO.TipoSesion.PROPIETARIO);
        sesion.setCliente(validacion.getCliente());
        log.log(Level.INFO, "Se creo la sesion {0}", sesion);
        applicationBean.agregarSesionListaRegalos(sesion.getIdSession(), sesion);
        return Response.ok(sesion).build();
    }

    @GET
    @Path("empleado/")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public Response crearSesionEmpleado() {
        log.log(Level.INFO, "Creando sesion de administrador de lista de regalos para empleado");
        //valida que exista una sesion de 360 acitva
        if (userSession.getUsuario() == null) {
            log.log(Level.SEVERE, "Se intento acceder a la URL de administracion de listas de regalo sin haber iniciado una sesion en 360.");
            return Response.temporaryRedirect(null).build();
        }
        //valida si el usuario tiene permisos para administrar listas
        if (!applicationBean.usuarioPuede(userSession.getUsuario(), "ADMINISTRAR", "LISTA_REGALOS")) {
            log.log(Level.SEVERE, "El usuario {0} no tiene autorizacion de administrar listas de regalo.", userSession.getUsuario());
            return Response.temporaryRedirect(null).build();
        }
        //SocioDeNegocios cliente = clienteFacade.findByCardCode(userSession.getCedulaEmpleado());

        //crea sesion lista de regalos
        SesionListaRegalosDTO sesion = new SesionListaRegalosDTO();
        sesion.setIdSession(RandomStringUtils.randomAlphanumeric(32));
        sesion.setTipo(SesionListaRegalosDTO.TipoSesion.ADMINISTRADOR);
        sesion.setCliente(new ClienteSAPDTO(userSession.getCedulaEmpleado(), null));
        log.log(Level.INFO, "Se creo la sesion {0}", sesion);
        applicationBean.agregarSesionListaRegalos(sesion.getIdSession(), sesion);
        return Response.temporaryRedirect(URI.create(applicationBean.obtenerValorPropiedad("listaregalos.url.web.inicio") + "pages/admin_lista.html?sesion=".concat(sesion.getIdSession()))).build();
    }

    private void enviarSMS(String random, String nombre, String numero, String ip) throws Exception {
        //TODO: parametrizar ruta BCS
        SMSServiceClient client = new SMSServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        MensajeTextoDTO sms = new MensajeTextoDTO();
        sms.setUsuario("listaregalos");
        sms.setIp(ip);
        sms.setCodigoPais("57");
        sms.setDestino(numero);
        sms.setMensaje("Hola " + nombre + ". El codigo de seguridad para que ingreses a tu panel de administracion de listas de regalos Matisses es " + random);
        sms.setPruebas(false);
        client.enviarSMS(sms);
    }

    private void enviarEmail(String random, String nombre, String email) {
        EmailServiceClient client = new EmailServiceClient(applicationBean.obtenerValorPropiedad("url.servicio.bcs"));
        BCSMailMessageDTO mail = new BCSMailMessageDTO();
        mail.setFrom("Lista Regalos Matisses <listaregalos@matisses.co>");
        HashMap<String, String> params = new HashMap<>();
        params.put("nombre", nombre);
        params.put("codigo", random);
        mail.setParams(params);
        mail.setSubject("Código de ingreso a lista regalos Matisses");
        mail.setTemplateName("lista_regalos_codigo_ingreso");
        mail.addToAddress(email);
        try {
            client.sendMail(mail);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion de correo. ", e);
        }
    }
}
