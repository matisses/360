package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.DetalleDevolucionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.facade.DetalleDevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.web.dto.DetalleDevolucionSAPDTO;
import co.matisses.web.dto.DetalleFacturaSAPDTO;
import co.matisses.web.dto.DevolucionSAPDTO;
import co.matisses.web.dto.FacturaSAPDTO;
import co.matisses.web.dto.PagosFacturaDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
 * @author dbotero
 */
@ViewScoped
@Named(value = "consultaFacturasMBean")
public class ConsultaFacturasMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    private static final Logger log = Logger.getLogger(ConsultaFacturasMBean.class.getSimpleName());
    private Integer numeroFactura;
    private Integer numeroDevolucion;
    private String parametroBusqueda;
    private boolean obligaLogeo = true;
    private FacturaSAPDTO facturaDto;
    private List<DetalleDevolucionSAPDTO> detalleDevolucion;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private DetalleDevolucionSAPFacade detalleDevolucionSAPFacade;

    public ConsultaFacturasMBean() {
        detalleDevolucion = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        try {
            numeroFactura = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nroFactura"));
            if (numeroFactura != null) {
                parametroBusqueda = numeroFactura.toString();
                consultarInformacionFactura();
                obligaLogeo = false;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo obtener el parametro de request para el numero de factura. ", e);
            obligaLogeo = true;
        }

        if (obligaLogeo && userSessionInfoMBean.getUsuario() == null) {
            mostrarMensaje("Error", "Debe iniciar sesión para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Debe iniciar sesion para poder continuar");
            return;
        }
    }

    public Integer getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(Integer numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public Integer getNumeroDevolucion() {
        return numeroDevolucion;
    }

    public void setNumeroDevolucion(Integer numeroDevolucion) {
        this.numeroDevolucion = numeroDevolucion;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public boolean isObligaLogeo() {
        return obligaLogeo;
    }

    public void setObligaLogeo(boolean obligaLogeo) {
        this.obligaLogeo = obligaLogeo;
    }

    public FacturaSAPDTO getFacturaDto() {
        return facturaDto;
    }

    public void setFacturaDto(FacturaSAPDTO facturaDto) {
        this.facturaDto = facturaDto;
    }

    public List<DetalleDevolucionSAPDTO> getDetalleDevolucion() {
        return detalleDevolucion;
    }

    public void setDetalleDevolucion(List<DetalleDevolucionSAPDTO> detalleDevolucion) {
        this.detalleDevolucion = detalleDevolucion;
    }

    private FacturaSAPDTO entity2Dto(FacturaSAP entity) {
        FacturaSAPDTO dto = new FacturaSAPDTO();

        dto.setComentarios(entity.getComments());
        dto.setEstado((entity.getDocStatus().toString().equals("C")) ? "CERRADA" : (entity.getDocStatus().toString().equals("O")) ? "ABIERTA" : "ESTADO NO IDENTIFICADO");
        dto.setFechaFactura(entity.getDocDate());
        dto.setFechaVencimiento(entity.getDocDueDate());
        dto.setNitCliente(entity.getCardCode());
        dto.setNombreCliente(entity.getCardName());
        dto.setNumeroFactura(entity.getDocNum());
        dto.setTotal(entity.getDocTotal().intValue());
        dto.setAsesor(salesPersonFacade.obtenerNombreAsesor(entity.getSlpCode().intValue()));

        /*Se obtiene el detalle de la factura*/
        for (DetalleFacturaSAP detalle : entity.getDetalle()) {
            DetalleFacturaSAPDTO detalleDto = new DetalleFacturaSAPDTO();
            detalleDto.setAlmacen(detalle.getWhsCode());
            detalleDto.setCantidad(detalle.getQuantity().intValue());
            detalleDto.setDescripcion(detalle.getDscription());
            detalleDto.setEstado(detalle.getUEstadoP());
            detalleDto.setPrecio(detalle.getPriceAfVAT().intValue());
            detalleDto.setReferencia(detalle.getItemCode());
            dto.agregarProducto(detalleDto);
        }

        /*Se obtienen los pagos*/
        List<Object[]> pagos = reciboCajaFacade.obtenerPagosFactura(entity.getDocEntry());

        if (pagos != null && !pagos.isEmpty()) {
            List<PagosFacturaDTO> pagosFactura = new ArrayList<>();
            for (Object[] o : pagos) {
                pagosFactura.add(new PagosFacturaDTO((Integer) o[0], (BigDecimal) o[1], (BigDecimal) o[2], (BigDecimal) o[3], (BigDecimal) o[4], (String) o[5]));
            }
            dto.setPagos(pagosFactura);
        }

        /*Se obtienen datos de las devoluciones*/
        List<DevolucionSAP> devoluciones = devolucionSAPFacade.obtenerDevolucionesFactura(entity.getDocNum());

        if (devoluciones != null && !devoluciones.isEmpty()) {
            List<DevolucionSAPDTO> devolucionesFactura = new ArrayList<>();
            for (DevolucionSAP d : devoluciones) {
                devolucionesFactura.add(new DevolucionSAPDTO(d.getDocNum(), d.getDocDate(), d.getDocTotal().intValue(), null, obtenerDetalleDevolucion(d.getDocEntry())));
            }
            dto.setDevoluciones(devolucionesFactura);
        }
        return dto;
    }

    private List<DetalleDevolucionSAPDTO> obtenerDetalleDevolucion(Integer docEntry) {
        List<DetalleDevolucionSAP> detalle = detalleDevolucionSAPFacade.obtenerDetalleDevolucion(docEntry);

        if (detalle != null && !detalle.isEmpty()) {
            List<DetalleDevolucionSAPDTO> det = new ArrayList<>();

            for (DetalleDevolucionSAP d : detalle) {
                det.add(new DetalleDevolucionSAPDTO(d.getQuantity().intValue(), d.getItemCode()));
            }

            return det;
        } else {
            return null;
        }
    }

    public void consultarInformacionFactura() {
        log.log(Level.INFO, "Consultando los datos de la factura [{0}]", numeroFactura);
        if (numeroFactura == null || numeroFactura <= 0) {
            log.log(Level.WARNING, "No se puede consultar la factura si no se envia un numero de factura valido. [{0}]", numeroFactura);
            return;
        }

        FacturaSAP entidad = facturaSAPFacade.findByDocNum(numeroFactura);
        if (entidad != null) {
            facturaDto = entity2Dto(entidad);
        } else {
            log.log(Level.SEVERE, "No se encontro ninguna factura con el numero [{0}]", numeroFactura);
        }
    }

    public void seleccionarDevolucion() {
        numeroDevolucion = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("numeroDevolucion"));

        for (DevolucionSAPDTO d : facturaDto.getDevoluciones()) {
            if (d.getNumeroDevolucion().equals(numeroDevolucion)) {
                detalleDevolucion = d.getDetalle();
                break;
            }
        }
    }

    public void buscarFactura() {
        if (obligaLogeo && userSessionInfoMBean.getUsuario() != null) {
            if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
                mostrarMensaje("Error", "Ingrese un parámetro de búsqueda.", true, false, false);
                log.log(Level.SEVERE, "Ingrese un parametro de busqueda");
                return;
            }
            try {
                numeroFactura = Integer.parseInt(parametroBusqueda);
            } catch (Exception e) {
                mostrarMensaje("Error", "La factura ingresada no es permitida, intentelo nuevamente.", true, false, false);
                log.log(Level.SEVERE, "La factura ingresada no es permitida, intentelo nuevamente", e);
                return;
            }
            consultarInformacionFactura();
        } else {
            mostrarMensaje("Error", "Debe iniciar sesión para poder continuar.", true, false, false);
            log.log(Level.SEVERE, "Debe iniciar sesion para poder continuar");
            return;
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
