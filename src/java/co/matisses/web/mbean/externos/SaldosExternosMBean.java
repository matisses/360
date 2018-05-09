package co.matisses.web.mbean.externos;

import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.BaruDecoradoresFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.UsuarioPagina;
import co.matisses.persistence.web.entity.UsuarioPaginaRedencion;
import co.matisses.persistence.web.facade.UsuarioPaginaFacade;
import co.matisses.persistence.web.facade.UsuarioPaginaRedencionFacade;
import co.matisses.web.bcs.client.JournalEntryClient;
import co.matisses.web.bcs.client.SessionUsuarioPaginaClient;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.JournalEntryDTO;
import co.matisses.web.dto.JournalEntryLineDTO;
import co.matisses.web.dto.UsuarioPaginaDTO;
import co.matisses.web.dto.UsuarioPaginaRedencionDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
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
@Named(value = "saldosExternosMBean")
public class SaldosExternosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(SaldosExternosMBean.class.getSimpleName());
    private UsuarioPaginaRedencionDTO usuario;
    private List<String> documentacion;
    private List<UsuarioPaginaRedencionDTO> redencionWedding;
    private List<UsuarioPaginaRedencionDTO> redencionDecorador;
    private List<UsuarioPaginaDTO> usuariosEspecialesFull;
    private List<UsuarioPaginaDTO> usuariosEspeciales;
    @EJB
    private UsuarioPaginaRedencionFacade usuarioPaginaRedencionFacade;
    @EJB
    private UsuarioPaginaFacade usuarioPaginaFacade;
    @EJB
    private BaruDecoradoresFacade baruDecoradoresFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;

    public SaldosExternosMBean() {
        usuario = new UsuarioPaginaRedencionDTO();
        documentacion = new ArrayList<>();
        redencionWedding = new ArrayList<>();
        redencionDecorador = new ArrayList<>();
        usuariosEspecialesFull = new ArrayList<>();
        usuariosEspeciales = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerUsuariosWeddingPlanner();
        obtenerUsuariosDecorador();
        obtenerUsuariosEspeciales();
    }

    public UsuarioPaginaRedencionDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioPaginaRedencionDTO usuario) {
        this.usuario = usuario;
    }

    public List<String> getDocumentacion() {
        return documentacion;
    }

    public void setDocumentacion(List<String> documentacion) {
        this.documentacion = documentacion;
    }

    public List<UsuarioPaginaRedencionDTO> getRedencionWedding() {
        return redencionWedding;
    }

    public void setRedencionWedding(List<UsuarioPaginaRedencionDTO> redencionWedding) {
        this.redencionWedding = redencionWedding;
    }

    public List<UsuarioPaginaRedencionDTO> getRedencionDecorador() {
        return redencionDecorador;
    }

    public void setRedencionDecorador(List<UsuarioPaginaRedencionDTO> redencionDecorador) {
        this.redencionDecorador = redencionDecorador;
    }

    public List<UsuarioPaginaDTO> getUsuariosEspeciales() {
        return usuariosEspeciales;
    }

    public void setUsuariosEspeciales(List<UsuarioPaginaDTO> usuariosEspeciales) {
        this.usuariosEspeciales = usuariosEspeciales;
    }

    private void obtenerUsuariosWeddingPlanner() {
        redencionWedding = new ArrayList<>();

        List<UsuarioPaginaRedencion> users = usuarioPaginaRedencionFacade.obtenerRedencionPendiente("WP");

        if (users != null && !users.isEmpty()) {
            for (UsuarioPaginaRedencion u : users) {
                redencionWedding.add(new UsuarioPaginaRedencionDTO(u));
            }
        }
    }

    private void obtenerUsuariosDecorador() {
        redencionDecorador = new ArrayList<>();

        List<UsuarioPaginaRedencion> users = usuarioPaginaRedencionFacade.obtenerRedencionPendiente("DC");

        if (users != null && !users.isEmpty()) {
            for (UsuarioPaginaRedencion u : users) {
                redencionDecorador.add(new UsuarioPaginaRedencionDTO(u));
            }
        }
    }

    public void obtenerDocumentacion() {
        usuario = new UsuarioPaginaRedencionDTO();
        documentacion = new ArrayList<>();
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");

        if (id != null && id != 0) {
            File file = new File("");

            if (tipo != null && tipo.equals("WP")) {
                for (UsuarioPaginaRedencionDTO p : redencionWedding) {
                    if (p.getIdRedencion().equals(id)) {
                        file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "usuario" + File.separator
                                + p.getUsuarioPagina().getDocumento() + File.separator + "redencion" + File.separator + p.getIdRedencion());
                        usuario = p;
                        break;
                    }
                }
            } else {
                for (UsuarioPaginaRedencionDTO p : redencionDecorador) {
                    if (p.getIdRedencion().equals(id)) {
                        file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "usuario" + File.separator
                                + p.getUsuarioPagina().getDocumento() + File.separator + "redencion" + File.separator + p.getIdRedencion());
                        usuario = p;
                        break;
                    }
                }
            }

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
    }

    public String visualizarDocumento(String documento) {
        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "usuario" + File.separator
                + usuario.getUsuarioPagina().getDocumento() + File.separator + "redencion" + File.separator + usuario.getIdRedencion());

        if (file.exists()) {
            return "openRuta('" + applicationMBean.obtenerValorPropiedad("url.web.shared") + "usuario/" + usuario.getUsuarioPagina().getDocumento()
                    + "/redencion/" + usuario.getIdRedencion() + "/" + documento + "'); closeWindow();";
        } else {
            return "";
        }
    }

    public void aplicar() {
        usuario = new UsuarioPaginaRedencionDTO();
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");

        if (id != null && id != 0) {
            if (tipo != null && tipo.equals("WP")) {
                for (UsuarioPaginaRedencionDTO p : redencionWedding) {
                    if (p.getIdRedencion().equals(id)) {
                        usuario = p;
                        break;
                    }
                }
            } else {
                for (UsuarioPaginaRedencionDTO p : redencionDecorador) {
                    if (p.getIdRedencion().equals(id)) {
                        usuario = p;
                        break;
                    }
                }
            }
        }

        if (usuario != null && usuario.getIdRedencion() != null && usuario.getIdRedencion() != 0) {
            JournalEntryDTO journalEntryHeader = new JournalEntryDTO();

            journalEntryHeader.setMemo("REDENCIÓN DECORADORES");
            journalEntryHeader.setRef1(usuario.getIdRedencion().toString());
            journalEntryHeader.setTransactionCode("DECO");
            journalEntryHeader.setDueDate(new Date());
            journalEntryHeader.setRefDate(new Date());
            journalEntryHeader.setTaxDate(new Date());

            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(usuario.getUsuarioPagina().getDocumento());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                JournalEntryLineDTO line1 = new JournalEntryLineDTO();

                line1.setLineId(0L);
                line1.setCredit(0D);
                line1.setDebit(usuario.getMonto().doubleValue());
                line1.setShortName("52950501");
                line1.setContraAccount("52950501");
                line1.setInfoCo01(socio.getCardCode().replace("CL", "PR"));

                journalEntryHeader.addLine(line1);

                if (socio.getRegimenTributario().equals("RC")) {
                    JournalEntryLineDTO line2 = new JournalEntryLineDTO();

                    line2.setLineId(1L);
                    line2.setCredit(0D);
                    line2.setDebit((usuario.getMonto().doubleValue() / 100) * 19);
                    line2.setShortName("24080308");
                    line2.setContraAccount("24080308");
                    line2.setInfoCo01(socio.getCardCode().replace("CL", "PR"));

                    journalEntryHeader.addLine(line2);

                    if (usuario.getModo().equals("BN")) {
                        JournalEntryLineDTO line3 = new JournalEntryLineDTO();

                        line3.setLineId(2L);
                        line3.setCredit(socio.getRegimenTributario().equals("RC") ? (usuario.getMonto().doubleValue() + ((usuario.getMonto().doubleValue() / 100) * 19)) : usuario.getMonto().doubleValue());
                        line3.setDebit(0D);
                        line3.setShortName(socio.getCardCode().replace("CL", "PR"));
                        line3.setContraAccount(socio.getCardCode().replace("CL", "PR"));
                        line3.setInfoCo01(socio.getCardCode().replace("CL", "PR"));

                        journalEntryHeader.addLine(line3);

                        JournalEntryLineDTO line4 = new JournalEntryLineDTO();

                        line4.setLineId(2L);
                        line4.setCredit(usuario.getMonto().doubleValue());
                        line4.setDebit(0D);
                        line4.setShortName(socio.getCardCode());
                        line4.setContraAccount(socio.getCardCode());
                        line4.setInfoCo01(socio.getCardCode());

                        journalEntryHeader.addLine(line4);

                        JournalEntryLineDTO line5 = new JournalEntryLineDTO();

                        line5.setLineId(3L);
                        line5.setCredit(0D);
                        line5.setDebit(usuario.getMonto().doubleValue());
                        line5.setShortName("53053501");
                        line5.setContraAccount("53053501");
                        line5.setInfoCo01(socio.getCardCode());

                        journalEntryHeader.addLine(line5);
                    } else {
                        JournalEntryLineDTO line3 = new JournalEntryLineDTO();

                        line3.setLineId(2L);
                        line3.setCredit(usuario.getMonto().doubleValue() + ((usuario.getMonto().doubleValue() / 100) * 19));
                        line3.setDebit(0D);
                        line3.setShortName(socio.getCardCode().replace("CL", "PR"));
                        line3.setContraAccount(socio.getCardCode().replace("CL", "PR"));

                        journalEntryHeader.addLine(line3);
                    }
                } else {
                    JournalEntryLineDTO line2 = new JournalEntryLineDTO();

                    line2.setLineId(1L);
                    line2.setCredit(usuario.getMonto().doubleValue());
                    line2.setDebit(0D);
                    line2.setShortName(socio.getCardCode());
                    line2.setContraAccount(socio.getCardCode());
                    line2.setInfoCo01(socio.getCardCode());

                    journalEntryHeader.addLine(line2);

                    if (usuario.getModo().equals("BN")) {
                        JournalEntryLineDTO line3 = new JournalEntryLineDTO();

                        line3.setLineId(2L);
                        line3.setCredit(usuario.getMonto().doubleValue());
                        line3.setDebit(0D);
                        line3.setShortName(socio.getCardCode().replace("CL", "PR"));
                        line3.setContraAccount(socio.getCardCode().replace("CL", "PR"));
                        line3.setInfoCo01(socio.getCardCode().replace("CL", "PR"));

                        journalEntryHeader.addLine(line3);

                        JournalEntryLineDTO line4 = new JournalEntryLineDTO();

                        line4.setLineId(3L);
                        line4.setCredit(0D);
                        line4.setDebit(usuario.getMonto().doubleValue());
                        line4.setShortName("53053501");
                        line4.setContraAccount("53053501");
                        line4.setInfoCo01(socio.getCardCode());

                        journalEntryHeader.addLine(line4);
                    }
                }
            }

            try {
                JournalEntryClient client = new JournalEntryClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

                GenericRESTResponseDTO res = client.crearAsiento(journalEntryHeader);

                if (res.getEstado() > 0) {
                    UsuarioPaginaRedencion user = usuarioPaginaRedencionFacade.find(usuario.getIdRedencion());

                    if (user != null && user.getIdRedencion() != null && user.getIdRedencion() != 0) {
                        user.setEstado("RF");

                        usuarioPaginaRedencionFacade.edit(user);
                    }

                    if (usuario.getTipo().equals("DC")) {
                        obtenerUsuariosDecorador();
                    } else {
                        obtenerUsuariosWeddingPlanner();
                    }
                } else {
                    mostrarMensaje("Error", "No se pudo crear el asiento contable.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se pudo crear el asiento contable");
                    return;
                }
            } catch (Exception e) {
                mostrarMensaje("Error", e.getMessage(), true, false, false);
            }
        }
    }

    private void obtenerUsuariosEspeciales() {
        usuariosEspecialesFull = new ArrayList<>();
        usuariosEspeciales = new ArrayList<>();

        List<UsuarioPagina> users = usuarioPaginaFacade.obtenerUsuariosEspecial(false);

        if (users != null && !users.isEmpty()) {
            SessionUsuarioPaginaClient client = new SessionUsuarioPaginaClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            for (UsuarioPagina u : users) {
                usuariosEspecialesFull.add(0, new UsuarioPaginaDTO(u));
                usuariosEspecialesFull.get(0).setPuntos(client.cargarMontoAcumulado(u.getDocumento()));
                usuariosEspecialesFull.get(0).setEsDecorador(false);
            }
        }

        users = usuarioPaginaFacade.obtenerUsuariosEspecial(true);

        if (users != null && !users.isEmpty()) {
            SessionUsuarioPaginaClient client = new SessionUsuarioPaginaClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            for (UsuarioPagina u : users) {
                usuariosEspecialesFull.add(0, new UsuarioPaginaDTO(u));

                String decorador = baruDecoradoresFacade.consultarCodigoDecorador(u.getDocumento());

                if (decorador != null && !decorador.isEmpty()) {
                    List<String> clientes = client.cargarClientesDecorador(decorador);

                    if (clientes != null && !clientes.isEmpty()) {
                        for (String s : clientes) {
                            List dto = client.cargarFacturasClientesDecorador(decorador, s);

                            if (dto != null && !dto.isEmpty()) {
                                for (Object d : dto) {
                                    LinkedHashMap h = (LinkedHashMap) d;

                                    if (h.get("comision") != null) {
                                        if (h.get("comision") instanceof Integer) {
                                            usuariosEspecialesFull.get(0).getPuntos().setRedimeBono(usuariosEspecialesFull.get(0).getPuntos().getRedimeBono().add(new BigDecimal((Integer) h.get("comision"))));
                                            usuariosEspecialesFull.get(0).getPuntos().setRedimeEfectivo(usuariosEspecialesFull.get(0).getPuntos().getRedimeEfectivo().add(new BigDecimal((Integer) h.get("comision"))));
                                        } else if (h.get("comision") instanceof Double) {
                                            usuariosEspecialesFull.get(0).getPuntos().setRedimeBono(usuariosEspecialesFull.get(0).getPuntos().getRedimeBono().add(new BigDecimal((Double) h.get("comision"))));
                                            usuariosEspecialesFull.get(0).getPuntos().setRedimeEfectivo(usuariosEspecialesFull.get(0).getPuntos().getRedimeEfectivo().add(new BigDecimal((Double) h.get("comision"))));
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        usuariosEspecialesFull.get(0).getPuntos().setRedimeBono(new BigDecimal(0));
                        usuariosEspecialesFull.get(0).getPuntos().setRedimeEfectivo(new BigDecimal(0));
                    }
                }

                usuariosEspecialesFull.get(0).setEsPlanificador(false);
            }
        }

        usuariosEspeciales = new ArrayList<>(usuariosEspecialesFull);
    }

    public void cancelarSolicitud() {
        usuario = new UsuarioPaginaRedencionDTO();
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");

        if (id != null && id != 0) {
            if (tipo != null && tipo.equals("WP")) {
                for (UsuarioPaginaRedencionDTO p : redencionWedding) {
                    if (p.getIdRedencion().equals(id)) {
                        usuario = p;
                        break;
                    }
                }
            } else {
                for (UsuarioPaginaRedencionDTO p : redencionDecorador) {
                    if (p.getIdRedencion().equals(id)) {
                        usuario = p;
                        break;
                    }
                }
            }
        }

        if (usuario != null && usuario.getIdRedencion() != null && usuario.getIdRedencion() != 0) {
            UsuarioPaginaRedencion user = usuarioPaginaRedencionFacade.find(usuario.getIdRedencion());

            if (user != null && user.getIdRedencion() != null && user.getIdRedencion() != 0) {
                user.setEstado("RC");

                usuarioPaginaRedencionFacade.edit(user);
                CONSOLE.log(Level.INFO, "El usuario {0}, cancelo la solicitud numero {1}", new Object[]{sessionMBean.getUsuario(), user.getIdRedencion()});
            }

            if (usuario.getTipo().equals("DC")) {
                obtenerUsuariosDecorador();
            } else {
                obtenerUsuariosWeddingPlanner();
            }
        }
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
