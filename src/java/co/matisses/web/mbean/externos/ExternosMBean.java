package co.matisses.web.mbean.externos;

import co.matisses.persistence.sap.entity.BaruDecoradores;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.BaruDecoradoresFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.UsuarioPagina;
import co.matisses.persistence.web.facade.UsuarioPaginaFacade;
import co.matisses.web.bcs.businesspartner.BusinessPartner;
import co.matisses.web.bcs.client.BusinessPartnerClient;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.dto.UsuarioPaginaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "externosMBean")
public class ExternosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(ExternosMBean.class.getSimpleName());
    private String parametro;
    private String estado;
    private String comentario;
    private UsuarioPaginaDTO usuario;
    private List<String> documentacion;
    private List<UsuarioPaginaDTO> usuarios;
    @EJB
    private UsuarioPaginaFacade usuarioPaginaFacade;
    @EJB
    private BaruDecoradoresFacade baruDecoradoresFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;

    public ExternosMBean() {
    }

    @PostConstruct
    protected void initialize() {
        obtenerUsuarios();
    }

    public String getParametro() {
        return parametro;
    }

    public void setParametro(String parametro) {
        this.parametro = parametro;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public UsuarioPaginaDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPaginaDTO usuario) {
        this.usuario = usuario;
    }

    public List<String> getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(List<String> documentacion) {
        this.documentacion = documentacion;
    }

    public List<UsuarioPaginaDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioPaginaDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public void obtenerUsuarios() {
        limpiar();
        usuarios = new ArrayList<>();
        List<UsuarioPagina> users = usuarioPaginaFacade.obtenerUsuariosPendienteAprobacion(parametro);

        if (users != null && !users.isEmpty()) {
            int contador = 1;
            for (UsuarioPagina u : users) {
                if (u.getEsDecorador() && u.getPendienteAprobacionDecorador()) {
                    usuarios.add(new UsuarioPaginaDTO(u));
                    usuarios.get(usuarios.size() - 1).setEsPlanificador(false);
                    usuarios.get(usuarios.size() - 1).setId(contador);
                    contador++;
                }
                if (u.getEsPlanificador() && u.getPendienteAprobacionPlanificador()) {
                    usuarios.add(new UsuarioPaginaDTO(u));
                    usuarios.get(usuarios.size() - 1).setEsDecorador(false);
                    usuarios.get(usuarios.size() - 1).setId(contador);
                    contador++;
                }
            }
        }
    }

    public void seleccionarUsuario() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        for (UsuarioPaginaDTO u : usuarios) {
            if (u.getId().equals(id)) {
                usuario = u;
                obtenerDocumentacion();
                break;
            }
        }
    }

    private void obtenerDocumentacion() {
        documentacion = new ArrayList<>();

        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "usuario" + File.separator + usuario.getDocumento()
                + File.separator + (usuario.isEsDecorador() ? "decorador" : "planificador"));

        if (file.exists()) {
            File[] archivos = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return !pathname.isHidden();
                }
            });

            if (archivos != null && archivos.length > 0) {
                for (File f : archivos) {
                    documentacion.add(f.getName());
                }
            }
        }
    }

    public String visualizarDocumento(String nombreDocumento) {
        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "usuario" + File.separator + usuario.getDocumento()
                + File.separator + (usuario.isEsDecorador() ? "decorador" : "planificador") + File.separator + nombreDocumento);

        if (file.exists()) {
            return "openRuta('" + applicationMBean.obtenerValorPropiedad("url.web.shared") + "usuario/" + usuario.getDocumento()
                    + (usuario.isEsDecorador() ? "/decorador/" : "/planificador/") + nombreDocumento + "'); closeWindow();";
        } else {
            return "";
        }
    }

    public void aprobarUsuario() {
        estado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("estado");
    }

    public void continuar() {
        UsuarioPagina user = usuarioPaginaFacade.find(usuario.getUsuarioId());

        if (user != null && user.getUsuarioId() != null && user.getUsuarioId() > 0) {
            if (usuario.isEsDecorador()) {
                user.setPendienteAprobacionDecorador(false);
                user.setEsDecorador(estado.equals("Aprobado"));
            } else {
                user.setPendienteAprobacionPlanificador(false);
                user.setEsPlanificador(estado.equals("Aprobado"));
            }

            if (estado.equals("Aprobado")) {
                comentario = "";
            } else if (comentario == null || comentario.isEmpty()) {
                mostrarMensaje("Error", "Debe ingresar el comentario, informando porque no aprobó la solicitud.", true, false, false);
                CONSOLE.log(Level.SEVERE, "Debe ingresar el comentario, informando porque no aprobo la solicitud");
                return;
            }

            try {
                usuarioPaginaFacade.edit(user);

                BaruDecoradores decorador = new BaruDecoradores();

                if (estado.equals("Aprobado")) {
                    decorador.setCode(baruDecoradoresFacade.obtenerProximoID().toString());
                    decorador.setName(usuario.getNombre());
                    decorador.setuEstado("A");
                    decorador.setuFechaIngreso(new Date());
                    decorador.setuNit(usuario.getDocumento());
                    decorador.setuTipo(usuario.isEsDecorador() ? "D" : "P");

                    baruDecoradoresFacade.create(decorador);
                    crearSocio();
                }

                /*Se manda notificacion al usuario con la respuesta de la solicitud*/
                MailMessageDTO dtoMail = new MailMessageDTO();
                Map<String, String> params = new HashMap<>();

                params.put("cliente", usuario.getNombre());
                params.put("estado", estado);
                params.put("tipoUsuario", usuario.isEsDecorador() ? "Decorador" : "Wedding planner");
                if (comentario == null || comentario.isEmpty()) {
                    params.put("comentario", "");
                } else {
                    params.put("comentario", "<tr><td><br/><br/><b>COMENTARIO<hr/></b></td></tr>" + "<tr><td>" + comentario + "</td></tr>");
                }
                if (decorador != null && decorador.getCode() != null && !decorador.getCode().isEmpty()) {
                    params.put("codigo", "<tr><td><br/><br/><b>CÓDIGO<hr/></b></td></tr><tr><td>Este es tu código <b>" + decorador.getCode()
                            + "</b> para que lo puedas usar en las compras que realices con nosotros.</td></tr>");
                } else {
                    params.put("codigo", "");
                }

                dtoMail.setTemplateName("respuesta_solicitud_web");
                dtoMail.setParams(params);
                dtoMail.setAttachments(null);
                dtoMail.setFrom("Contacto Matisses <info@matisses.co>");
                dtoMail.setSubject("Respuesta solicitud");
                dtoMail.addToAddress(usuario.getNombreUsuario());
                dtoMail.addBccAddress(applicationMBean.getDestinatariosPlantillaEmail().get("respuesta_solicitud_web").getBcc().get(0));

                SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                client.enviarHtmlEmail(dtoMail);
                obtenerUsuarios();
                limpiar();
            } catch (Exception e) {
            }
        }
    }

    private void crearSocio() {
        SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(usuario.getDocumento());

        if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
            SocioDeNegocios proveedor = socioDeNegociosFacade.find(socio.getCardCode().replace("CL", "PR"));

            if (proveedor != null && proveedor.getCardCode() != null && !proveedor.getCardCode().isEmpty()) {
                CONSOLE.log(Level.SEVERE, "Se cancela la creacion del proveedor en SAP, debido a que ya se encontro.");
                return;
            }

            BusinessPartner partner = new BusinessPartner();

            partner.setCardCode(socio.getCardCode().replace("CL", "PR"));
            partner.setCardType("S");
            partner.setCardName(socio.getRazonSocial());
            partner.setCardForeignName(socio.getRazonSocial());
            partner.setGroupCode(101L);
            partner.setCurrency("COP");
            partner.setFederalTaxID(socio.getCardCode().replace("CL", ""));
            partner.setValid("tYES");
            partner.setPhone1(socio.getPhone1());
            partner.setCellular(socio.getCelular());
            partner.setFatherType("P");
            partner.setUEsAutorret("N");
            partner.setUBPCORTC("RS");
            partner.setUBPCOTDC("13");
            partner.setUBPCOTP("01");
            partner.setUBPCONombre(socio.getNombres());
            partner.setUBPCO1Apellido(socio.getApellido1());
            partner.setUBPCO2Apellido(socio.getApellido2());
            partner.setUBPCOBPExt("01");
            partner.setUBPCOTBPE("01");
            partner.setUBPCOAddress(socio.getDireccion());
            partner.setUManejo("DIA");
            partner.setUBDErst("Y");
            partner.setCity(socio.getuBpcoCs());
            partner.setAddress("CASA");
            partner.setEmailAddress(socio.getEmail());

            /*Se registra la direccion*/
            partner.setBPAddresses(new BusinessPartner.BPAddresses());

            BusinessPartner.BPAddresses.BPAddress address = new BusinessPartner.BPAddresses.BPAddress();

            address.setAddressName("CASA");
            address.setAddressType("bo_BillTo");
            address.setBlock(partner.getPhone1());
            address.setBuildingFloorRoom(partner.getCellular());
            address.setCity(partner.getCity());
            address.setCountry("CO");
            address.setCounty(partner.getEmailAddress());
            address.setStreet(partner.getUBPCOAddress());

            partner.getBPAddresses().getBPAddress().add(address);

            BusinessPartnerClient client = new BusinessPartnerClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            try {
                GenericRESTResponseDTO res = client.crearCliente(partner);

                if (res.getEstado() < 0) {
                    mostrarMensaje("Error", "No se pudo crear el proveedor para el usuario externo. " + res.getMensaje(), true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se pudo crear el proveedor para el usuario externo. {0}", res.getMensaje());
                    return;
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "No se pudo crear el proveedor para el usuario externo. {0}", e);
            }
        }
    }

    public void limpiar() {
        estado = null;
        comentario = null;
        usuario = new UsuarioPaginaDTO();
        documentacion = new ArrayList<>();
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
