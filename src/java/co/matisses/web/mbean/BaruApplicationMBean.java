package co.matisses.web.mbean;

import co.matisses.b1ws.client.B1WSServiceUnavailableException;
import co.matisses.b1ws.client.SAPSessionManager;
import co.matisses.persistence.dto.AccionDTO;
import co.matisses.persistence.dto.ObjetoDTO;
import co.matisses.web.dto.PuntoVentaDTO;
import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.RegimenTributario;
import co.matisses.persistence.sap.entity.TipoDocumento;
import co.matisses.persistence.sap.entity.TipoRetencion;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.MunicipioFacade;
import co.matisses.persistence.sap.facade.RegimenTributarioFacade;
import co.matisses.persistence.sap.facade.TipoDocumentoFacade;
import co.matisses.persistence.sap.facade.TipoRetencionFacade;
import co.matisses.persistence.web.BwsSecurityManager;
import co.matisses.persistence.web.entity.BwsAccion;
import co.matisses.persistence.web.entity.BwsObjeto;
import co.matisses.persistence.web.entity.BwsSesionSAP;
import co.matisses.persistence.web.entity.BwsTerminalPOS;
import co.matisses.persistence.web.entity.DestinatarioPlantillaEmail;
import co.matisses.persistence.web.entity.IpSucursal;
import co.matisses.persistence.web.entity.TipoTarjetaPlaceToPay;
import co.matisses.persistence.web.facade.BwsAccionFacade;
import co.matisses.persistence.web.facade.BwsObjetoFacade;
import co.matisses.persistence.web.facade.BwsSesionSAPFacade;
import co.matisses.persistence.web.facade.BwsTerminalPOSFacade;
import co.matisses.persistence.web.facade.DestinatarioPlantillaEmailFacade;
import co.matisses.persistence.web.facade.IpSucursalFacade;
import co.matisses.persistence.web.facade.TipoTarjetaPlaceToPayFacade;
import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.dto.EmailTemplateDestinationDTO;
import co.matisses.web.dto.InformacionAlmacenDTO;
import co.matisses.web.dto.RegimenTributarioDTO;
import co.matisses.web.dto.SesionListaRegalosDTO;
import co.matisses.web.dto.SesionPOSDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.TipoDocumentoDTO;
import co.matisses.web.dto.TipoRetencionDTO;
import co.matisses.web.ldap.BaruLDAPAuth;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dbotero
 */
@ApplicationScoped
@Named("baruApplicationBean")
@Path("baruapplication")
public class BaruApplicationMBean implements Serializable {

    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private MunicipioFacade municipioFacade;
    @EJB
    private IpSucursalFacade ipSucursalFacade;
    @EJB
    private TipoDocumentoFacade tipoDocumentoFacade;
    @EJB
    private RegimenTributarioFacade regimenTributarioFacade;
    @EJB
    private TipoRetencionFacade tipoRetencionFacade;
    @EJB
    private BwsTerminalPOSFacade terminalPOSFacade;
    @EJB
    private BwsAccionFacade accionFacade;
    @EJB
    private BwsObjetoFacade objetoFacade;
    @EJB
    private BwsSecurityManager securityManager;
    @EJB
    private BaruLDAPAuth ldapManager;
    @EJB
    private BwsSesionSAPFacade sesionSAPFacade;
    @EJB
    private TipoTarjetaPlaceToPayFacade tipoTarjetaP2pFacade;
    @EJB
    private DestinatarioPlantillaEmailFacade destinatarioPlantillaFacade;
    private SAPSessionManager sapSessionManager = new SAPSessionManager();
    private Properties props = new Properties();
    private List<RegimenTributarioDTO> regimenesTributarios;
    private List<PuntoVentaDTO> sucursales;
    private List<TipoDocumentoDTO> tiposDocumento;
    private List<TipoRetencionDTO> retenciones;
    private List<String> fondos;
    private List<String> anios;
    private List<String> terminalesPOS;
    private List<CiudadDTO> ciudades;
    private HashMap<String, String> ipSucursal;
    private HashMap<String, SesionPOSDTO> sesionesPOSActivas;
    private HashMap<String, SesionListaRegalosDTO> sesionesListaRegalosActivas;
    private HashMap<String, AccionDTO> acciones;
    private HashMap<String, ObjetoDTO> objetos;
    private HashMap<String, SesionSAPB1WSDTO> sesionesSAPActivas;
    private HashMap<String, InformacionAlmacenDTO> infoAlmacen;
    private HashMap<String, EmailTemplateDestinationDTO> destinatariosPlantillaEmail;
    private HashMap<String, TipoTarjetaPlaceToPay> tiposTarjetaP2P;

    private static final Logger log = Logger.getLogger(BaruApplicationMBean.class.getSimpleName());
    private static final long DIA_EN_MILLIS = 86400000;

    public BaruApplicationMBean() {
        log.info("Iniciando [BaruApplicationMBean]");
        sucursales = new ArrayList<>();
        fondos = new ArrayList<>();
        tiposDocumento = new ArrayList<>();
        anios = new ArrayList<>();
        regimenesTributarios = new ArrayList<>();
        retenciones = new ArrayList<>();
        terminalesPOS = new ArrayList<>();
        ipSucursal = new HashMap<>();
        sesionesPOSActivas = new HashMap<>();
        sesionesListaRegalosActivas = new HashMap<>();
        sesionesSAPActivas = new HashMap<>();
        infoAlmacen = new HashMap<>();
        destinatariosPlantillaEmail = new HashMap<>();
        tiposTarjetaP2P = new HashMap<>();
        ciudades = new ArrayList<>();
    }

    @PostConstruct
    @GET
    @Path("recargar/")
    public void initialize() {
        cargarProperties();
        cargarPuntosDeVenta();
        cargarIpsSucursales();
        cargarFondos();
        cargarTiposDocumento();
        cargarAnios();
        cargarRegimenesTributarios();
        cargarTiposRetencion();
        cargarTerminalesPOSAutorizadas();
        cargarAccionesyObjetos();
        cargarUbicacionesTM();
        cargarDestinatariosPlantillasEmail();
        cargarCiudades();
        cargarTiposTarjetaP2P();
    }

    private void cargarTiposTarjetaP2P() {
        log.log(Level.INFO, "Cargando tipos de tarjetas de placetopay");
        tiposTarjetaP2P = new HashMap<>();
        try {
            for (TipoTarjetaPlaceToPay entidad : tipoTarjetaP2pFacade.findAll()) {
                tiposTarjetaP2P.put(entidad.getIdTarjetaPlaceToPay(), entidad);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar los tipos de tarjetas de placetopay. ", e);
        }
    }

    private void cargarDestinatariosPlantillasEmail() {
        log.log(Level.INFO, "Cargando destinatarios por plantilla para correo");
        destinatariosPlantillaEmail = new HashMap<>();
        try {
            for (DestinatarioPlantillaEmail entidad : destinatarioPlantillaFacade.findAll()) {
                if (destinatariosPlantillaEmail.containsKey(entidad.getNombrePlantilla())) {
                    destinatariosPlantillaEmail.get(entidad.getNombrePlantilla()).addDestinations(entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta());
                } else {
                    destinatariosPlantillaEmail.put(entidad.getNombrePlantilla(), new EmailTemplateDestinationDTO(entidad.getNombrePlantilla(), entidad.getPara(), entidad.getCopia(), entidad.getCopiaOculta()));
                }
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al cargar los destinatarios por plantilla de correo. ", e);
        }
    }

    private void cargarCiudades() {
        log.log(Level.INFO, "Cargando lista de ciudades");
        ciudades = new ArrayList<>();
        for (Object[] row : municipioFacade.listarMunicipiosDepartamentos()) {
            ciudades.add(new CiudadDTO(row));
        }
    }

    private void cargarUbicacionesTM() {
        log.log(Level.INFO, "Cargando ubicaciones TM");
        List<Object[]> datos = almacenFacade.consultarInfoAlmacenes();
        if (datos.isEmpty()) {
            return;
        }
        for (Object[] row : datos) {
            infoAlmacen.put((String) row[0], new InformacionAlmacenDTO((String) row[0], (Integer) row[1], (String) row[2]));
        }
    }

    private void cargarAccionesyObjetos() {
        acciones = new HashMap<>();
        objetos = new HashMap<>();
        try {
            for (BwsAccion entidad : accionFacade.findAll()) {
                acciones.put(entidad.getJavaName(), new AccionDTO(entidad.getCodigo(), entidad.getNombre()));
            }
            for (BwsObjeto entidad : objetoFacade.findAll()) {
                objetos.put(entidad.getJavaName(), new ObjetoDTO(entidad.getCodigo(), entidad.getNombre()));
            }
            log.log(Level.INFO, "Se cargaron [{0}] acciones y [{1}] objetos", new Object[]{acciones.size(), objetos.size()});
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al cargar las acciones y objetos. ", e);
        }
    }

    private void cargarTiposRetencion() {
        log.log(Level.INFO, "Cargando tipos de retencion");
        retenciones = new ArrayList<>();
        List<TipoRetencion> entidades = tipoRetencionFacade.getSalesApplicable();
        for (TipoRetencion t : entidades) {
            retenciones.add(new TipoRetencionDTO(t.getWtCode(), t.getWtName(), null, null, true));
        }
        Collections.sort(retenciones);
    }

    private void cargarRegimenesTributarios() {
        log.log(Level.INFO, "Cargando regimenes tributarios");
        regimenesTributarios = new ArrayList<>();
        List<RegimenTributario> entidades = regimenTributarioFacade.findAll();
        for (RegimenTributario entidad : entidades) {
            regimenesTributarios.add(new RegimenTributarioDTO(entidad.getCode(), entidad.getName()));
        }
        Collections.sort(regimenesTributarios);
    }

    public void cargarProperties() {
        props = new Properties();
        String serverConfUrl = System.getProperty("jboss.server.config.dir");
        log.log(Level.INFO, "Server config URL [{0}]", serverConfUrl);
        String propertiesFileName = "baru.properties";
        String path = serverConfUrl + File.separator + propertiesFileName;
        log.log(Level.INFO, "Loading properties file: [{0}]", path);

        try {
            File propsFile = new File(path);
            if (propsFile.exists()) {
                props.load(new FileInputStream(propsFile));
            } else {
                props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("/" + propertiesFileName));
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "There was an error loading the file.", e);
        }
    }

    private void cargarPuntosDeVenta() {
        log.info("Cargando puntos de venta");
        sucursales = new ArrayList<>();
        List<Almacen> entidades = almacenFacade.almacenesActivos();
        for (Almacen a : entidades) {
            sucursales.add(new PuntoVentaDTO(a.getWhsCode(), a.getWhsName()));
        }
        log.log(Level.INFO, "Se cargaron [{0}] almacenes", sucursales.size());
    }

    private void cargarIpsSucursales() {
        log.info("Cargando IPs sucursales");
        List<IpSucursal> ips = ipSucursalFacade.findAll();
        for (IpSucursal entidad : ips) {
            ipSucursal.put(entidad.getSegmento(), entidad.getSucursal());
        }
    }

    private void cargarFondos() {
        fondos = new ArrayList<>();
        File carpetaFondos = new File(System.getProperty("jboss.server.base.dir") + File.separator + "deployments" + File.separator + "shared.war" + File.separator + "fondos");
        log.log(Level.INFO, "Listando los archivos de la carpeta [{0}]", carpetaFondos.getAbsolutePath());
        if (carpetaFondos.exists()) {
            for (File fondo : carpetaFondos.listFiles()) {
                fondos.add(fondo.getName());
                log.log(Level.INFO, "Se agrego el archivo [{0}] a la lista", fondo.getName());
            }
        }
    }

    private void cargarTiposDocumento() {
        log.log(Level.INFO, "Cargando tipos de documento");
        List<TipoDocumento> entidades = tipoDocumentoFacade.findAll();
        tiposDocumento = new ArrayList<>();
        for (TipoDocumento entidad : entidades) {
            tiposDocumento.add(new TipoDocumentoDTO(entidad.getCode(), entidad.getName().toLowerCase()));
        }
        Collections.sort(tiposDocumento);
    }

    private void cargarAnios() {
        log.log(Level.INFO, "Cargando lista de anios");
        anios.clear();
        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String fechas = dateFormat.format(fecha);
        int yearIni = 1900;
        int yearFin = Integer.parseInt(fechas) - 15;
        do {
            anios.add(Integer.toString(yearIni));
            yearIni++;
        } while (yearIni <= yearFin);
    }

    private void cargarTerminalesPOSAutorizadas() {
        log.log(Level.INFO, "Cargando lista de terminales POS autorizadas");
        terminalesPOS = new ArrayList<>();
        for (BwsTerminalPOS entidad : terminalPOSFacade.listarTerminalesActivas()) {
            terminalesPOS.add(entidad.getIp());
        }
    }

    public void recargarTerminalesPOSAutorizadas() {
        cargarTerminalesPOSAutorizadas();
    }

    public boolean terminalPOSAutorizada(String ip) {
        return terminalesPOS.contains(ip);
    }

    @GET
    @Path("listarciudades/")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CiudadDTO> getCiudades() {
        return ciudades;
    }

    public List<RegimenTributarioDTO> getRegimenesTributarios() {
        return regimenesTributarios;
    }

    public void setRegimenesTributarios(List<RegimenTributarioDTO> regimenesTributarios) {
        this.regimenesTributarios = regimenesTributarios;
    }

    public List<TipoRetencionDTO> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<TipoRetencionDTO> retenciones) {
        this.retenciones = retenciones;
    }

    public List<PuntoVentaDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<PuntoVentaDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public InformacionAlmacenDTO getInfoAlmacen(String whsCode) {
        return infoAlmacen.get(whsCode);
    }

    public HashMap<String, EmailTemplateDestinationDTO> getDestinatariosPlantillaEmail() {
        return destinatariosPlantillaEmail;
    }

    public TipoTarjetaPlaceToPay getTipoTarjetaP2P(String idTarjetaP2P) {
        return tiposTarjetaP2P.get(idTarjetaP2P);
    }

    public String getIpSucursal(String codigo) {
        return ipSucursal.get(codigo);
    }

    public List<String> getFondos() {
        return fondos;
    }

    public List<TipoDocumentoDTO> getTiposDocumento() {
        return tiposDocumento;
    }

    public List<String> getAnios() {
        return anios;
    }

    public String obtenerValorPropiedad(String prop) {
        return props.getProperty(prop);
    }

    public void agregarSesionPOS(String sessionId, SesionPOSDTO dto) {
        log.log(Level.INFO, "Agregando identificador de session POS [{0}] al mapa de sesiones activas", sessionId);
        sesionesPOSActivas.put(sessionId, dto);
    }

    public void agregarSesionListaRegalos(String sessionId, SesionListaRegalosDTO dto) {
        log.log(Level.INFO, "Agregando identificador de session ListaRegalos [{0}] al mapa de sesiones activas", sessionId);
        sesionesListaRegalosActivas.put(sessionId, dto);
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public SesionSAPB1WSDTO obtenerSesionSAP(String usuario) {
        if (usuario == null || usuario.trim().isEmpty()) {
            return null;
        }
        SesionSAPB1WSDTO sesion = sesionesSAPActivas.get(usuario);
        if (sesion != null) {
            log.log(Level.INFO, "Se encontro la sesion {0} en el mapa de sesiones", sesion);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //Si la fecha de la sesion corresponde a la fecha actual, la sesion es valida
            if (sdf.format(sesion.getFecha()).equals(sdf.format(new Date()))) {
                return sesion;
            } else {
                log.log(Level.WARNING, "  Esta sesion se encuentra vencida y es necesario generar una nueva");
                sesion = null;
                sesionesSAPActivas.remove(usuario);
            }
        }

        //se consultan todas las sesiones vencidas del usuario
        List<String> sesionesSAPVencidas = sesionSAPFacade.consultarSesionesVencidasActivas(usuario);
        if (sesionesSAPVencidas != null && !sesionesSAPVencidas.isEmpty()) {
            log.log(Level.WARNING, "Se encontraron {0} sesiones vencidas en base de datos. Se procede a inactivarlas. ", sesionesSAPVencidas.size());
            //se finalizan todas las sesiones vencidas del usuario
            for (String sesionVencida : sesionesSAPVencidas) {
                try {
                    sapSessionManager.logout(sesionVencida);
                } catch (B1WSServiceUnavailableException e) {
                    if (e.getMessage() != null && e.getMessage().contains("Invalid Session ID")) {
                        log.log(Level.WARNING, "La sesion {0} no es valida en SAP y no se pudo finalizar. ", sesionVencida);
                    } else {
                        log.log(Level.SEVERE, "No fue posible comunicarse con el B1WS. ", e);
                        break;
                    }
                }
            }

            //se inactivan todas las sesiones vencidas del usuario
            sesionSAPFacade.inactivarSesiones(usuario);
        }

        //consulta si existe una sesion activa
        SesionSAPB1WSDTO dto = obtenerSesionSAPActiva(usuario);
        if (dto != null) {
            log.log(Level.INFO, "El usuario tiene una sesion activa en base de datos. {0}", dto);
            sesionesSAPActivas.put(usuario, dto);
            return dto;
        }

        try {
            String sessionId = sapSessionManager.login();

            BwsSesionSAP sesionEntidad = new BwsSesionSAP();
            sesionEntidad.setEstado("A");
            sesionEntidad.setFecha(new Date());
            sesionEntidad.setIdSesionSAP(sessionId);
            sesionEntidad.setUsuario(usuario);
            sesionSAPFacade.create(sesionEntidad);

            dto = obtenerSesionSAPActiva(usuario);
            sesionesSAPActivas.put(usuario, dto);

            log.log(Level.INFO, "Se creo la sesion sap {0}", dto);

            return dto;
        } catch (B1WSServiceUnavailableException e) {
            log.log(Level.SEVERE, "No fue posible iniciar sesion en B1WS. ", e);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible registrar la sesion. ", e);
        }

        return null;
    }

    public void finalizarSesionSAP(SesionSAPB1WSDTO sesion) {
        try {
            BwsSesionSAP entidad = new BwsSesionSAP();
            entidad.setId(sesion.getId());
            entidad.setFecha(sesion.getFecha());
            entidad.setIdSesionSAP(sesion.getIdSesionSAP());
            entidad.setUsuario(sesion.getUsuario());
            entidad.setEstado("I");
            sesionSAPFacade.edit(entidad);
            sesionesSAPActivas.remove(sesion.getUsuario());
            sapSessionManager.logout(sesion.getIdSesionSAP());
        } catch (Exception e) {
            log.log(Level.WARNING, "Ocurrio un error al finalizar la sesion {0} en sap. ", e);
        }

    }

    private SesionSAPB1WSDTO obtenerSesionSAPActiva(String usuario) {
        Object[] data = sesionSAPFacade.consultarSesionActiva(usuario);
        if (data == null) {
            return null;
        }
        SesionSAPB1WSDTO dto = new SesionSAPB1WSDTO();
        dto.setId((Integer) data[0]);
        dto.setUsuario((String) data[1]);
        dto.setFecha((Date) data[2]);
        dto.setIdSesionSAP((String) data[3]);
        dto.setEstado((String) data[4]);

        return dto;
    }

    public boolean sesionPOSActiva(String sessionId) {
        SesionPOSDTO dto = sesionesPOSActivas.get(sessionId);
        return dto != null && dto.isSesionValida();
    }

    public SesionPOSDTO obtenerSesionPOS(String sessionId) {
        return sesionesPOSActivas.get(sessionId);
    }

    public void finalizarSesionPOS(String sessionId) {
        sesionesPOSActivas.remove(sessionId);
    }

    public SesionListaRegalosDTO obtenerSesionListaRegalos(String sessionId) {
        //La sesion debe tener una vigencia de 24 horas. Despues de eso se debe inactivar
        SesionListaRegalosDTO sesion = sesionesListaRegalosActivas.get(sessionId);
        if (sesion == null) {
            //TODO: consultar sesion en base de datos y cargar al mapa en memoria
        }
        if (sesion != null && System.currentTimeMillis() - sesion.getFechaCreacion().getTime() > DIA_EN_MILLIS) {
            sesion.setSesionValida(false);
        }
        return sesion;
    }

    public void finalizarSesionListaRegalos(String sessionId) {
        sesionesListaRegalosActivas.remove(sessionId);
    }

    public ObjetoDTO cargarObjetoPorNombre(String nombre) {
        return objetos.get(nombre);
    }

    public AccionDTO cargarAccionPorNombre(String nombre) {
        return acciones.get(nombre);
    }

    public boolean usuarioPuede(String usuario, String accion, String objeto) {
        if (usuario == null || usuario.isEmpty() || accion == null || accion.isEmpty() || objeto == null || objeto.isEmpty()) {
            return false;
        }
        return securityManager.validarPermisoUsuario(usuario, accion, objeto);
    }

    public boolean usuarioPuede(String usuario, String psswd, String accion, String objeto) {
        boolean usuarioValido;
        String claveAlmacenada = securityManager.consultarClaveUsuario(usuario);
        String claveCodificada = codificarClave(psswd);
        if (claveAlmacenada != null && claveAlmacenada.equals(claveCodificada)) {
            usuarioValido = true;
        } else {
            usuarioValido = ldapManager.authenticateUser(usuario, psswd);
        }
        if (usuarioValido) {
            return securityManager.validarPermisoUsuario(usuario, accion, objeto);
        }
        return false;
    }

    public String codificarClave(String clave) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(clave.getBytes());

            byte byteData[] = md.digest();

            //convert the byte to hex format method 1
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < byteData.length; i++) {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (Exception e) {
        }
        return null;
    }

    public CiudadDTO obtenerCiudad(String codigo) {
        try {
            return ciudades.get(ciudades.indexOf(new CiudadDTO(codigo)));
        } catch (Exception e) {
            return null;
        }
    }

    public void limpiarSesionesSAP() {
        sesionesSAPActivas = new HashMap<>();
    }
}
