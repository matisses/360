package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.DetalleDevolucionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.DocumentosExcluidos;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.facade.DetalleDevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DocumentosExcluidosFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
@Named(value = "excluirDocumentosMBean")
public class ExcluirDocumentosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    private static final Logger CONSOLE = Logger.getLogger(ExcluirDocumentosMBean.class.getSimpleName());
    private String documento;
    private String tipo;
    private List<SaldoItemDTO> saldos;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private DetalleDevolucionSAPFacade detalleDevolucionSAPFacade;
    @EJB
    private DocumentosExcluidosFacade documentosExcluidosFacade;

    public ExcluirDocumentosMBean() {
        saldos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public void consultarDocumento() {
        saldos = new ArrayList<>();

        if (documento == null || documento.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el documento que quiere excluir.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el documento que quiere excluir");
            return;
        }
        if (tipo == null || tipo.isEmpty()) {
            mostrarMensaje("Error", "Debe seleccionar el tipo de documento a excluir.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar el tipo de documento a excluir");
            return;
        }

        if (tipo.equals("factura")) {
            FacturaSAP factura = facturaSAPFacade.findByDocNum(Integer.parseInt(documento));

            if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
                List<DetalleFacturaSAP> detalle = detalleFacturaSAPFacade.obtenerDetalleFactura(factura.getDocEntry().doubleValue());

                if (detalle != null && !detalle.isEmpty()) {
                    for (DetalleFacturaSAP d : detalle) {
                        SaldoItemDTO dto = new SaldoItemDTO();

                        dto.setReferencia(d.getItemCode());
                        dto.setCantidad(d.getQuantity().intValue());

                        saldos.add(dto);
                    }
                }
            } else {
                mostrarMensaje("Error", "No se encontraron datos de la factura ingresada.", true, false, false);
                CONSOLE.log(Level.SEVERE, "No se encontraron datos de la factura ingresada");
                return;
            }
        } else if (tipo.equals("devolucion")) {
            DevolucionSAP devolucion = devolucionSAPFacade.obtenerDevolucion(Integer.parseInt(documento));

            if (devolucion != null && devolucion.getDocEntry() != null && devolucion.getDocEntry() != 0) {
                List<DetalleDevolucionSAP> detalle = detalleDevolucionSAPFacade.obtenerDetalleDevolucion(devolucion.getDocEntry());

                if (detalle != null && !detalle.isEmpty()) {
                    for (DetalleDevolucionSAP d : detalle) {
                        SaldoItemDTO dto = new SaldoItemDTO();

                        dto.setReferencia(d.getItemCode());
                        dto.setCantidad(d.getQuantity().intValue());

                        saldos.add(dto);
                    }
                }
            } else {
                mostrarMensaje("Error", "No se encontraron datos de la devolución ingresada.", true, false, false);
                CONSOLE.log(Level.SEVERE, "No se encontraron datos de la devolucion ingresada");
                return;
            }
        }
    }

    public void excluirDocumento() {
        String doc = null;
        if (tipo.equals("devolucion")) {
            DevolucionSAP devolucion = devolucionSAPFacade.obtenerDevolucion(Integer.parseInt(documento));

            if (devolucion != null && devolucion.getDocEntry() != null && devolucion.getDocEntry() != 0) {
                doc = devolucion.getNumAtCard();
            }
        } else {
            doc = documento;
        }

        if (!documentosExcluidosFacade.verificarDocumentoExcluidoExiste(doc, tipo)) {
            long id = System.currentTimeMillis();

            DocumentosExcluidos document = new DocumentosExcluidos();

            document.setCode(String.valueOf(id));
            document.setDocNum(doc);
            document.setFechaExclusion(new Date());
            document.setName(String.valueOf(id));
            document.setTipoDocumento(tipo);
            document.setUsuarioExcluye(userSessionInfoMBean.getUsuario());

            try {
                documentosExcluidosFacade.create(document);
                mostrarMensaje("Éxito", "El documento fue excluido satisfactoriamente.", false, true, false);
                CONSOLE.log(Level.INFO, "Se ha excluido satisfactoriamente el documento {0}, del tipo {1}", new Object[]{documento, tipo});
                limpiar();
            } catch (Exception e) {
                mostrarMensaje("Error", "Ocurrió un error al excluir el documento.", true, false, false);
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al excluir el documento. ", e);
                return;
            }
        } else {
            mostrarMensaje("Error", "Lo sentimos, no se puede excluir el documento debido a que ya fue excluido anteriormente.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Lo sentimos, no se puede excluir el documento debido a que ya fue excluido anteriormente");
            return;
        }
    }

    public void limpiar() {
        documento = null;
        tipo = null;
        saldos = null;
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
