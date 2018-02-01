package co.matisses.web.mbean.ventas;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.web.entity.CotizacionWeb;
import co.matisses.persistence.web.entity.DetalleCotizacionWeb;
import co.matisses.persistence.web.facade.CotizacionWebFacade;
import co.matisses.persistence.web.facade.DetalleCotizacionWebFacade;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.AlmacenDTO;
import co.matisses.web.dto.CotizacionWebDTO;
import co.matisses.web.dto.DetalleCotizacionWebDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import co.matisses.web.mbean.session.VentasSessionMBean;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Named(value = "cotizacionAlmacenMBean")
public class CotizacionAlmacenMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    private static final Logger log = Logger.getLogger(CotizacionAlmacenMBean.class.getSimpleName());
    private String almacen;
    private boolean dlgTipoPrecio = false;
    private boolean precio = false;
    private boolean costo = false;
    private boolean imprimir = false;
    private boolean enviar = false;
    private CotizacionWebDTO cotizacionDTO;
    private List<AlmacenDTO> almacenes;
    private List<SaldoItemDTO> saldos;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private CotizacionWebFacade cotizacionWebFacade;
    @EJB
    private DetalleCotizacionWebFacade detalleCotizacionWebFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;

    public CotizacionAlmacenMBean() {
        cotizacionDTO = new CotizacionWebDTO();
        almacenes = new ArrayList<>();
        saldos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        if (ventasSessionMBean.isCotizacionEspecial() && ventasSessionMBean.isExitoCotizacion() && ventasSessionMBean.getNumeroCotizacion() != null && ventasSessionMBean.getNumeroCotizacion() != 0) {
            mostrarMensaje("Éxito", "Se creó la cotización especial " + ventasSessionMBean.getNumeroCotizacion(), false, true, false);
        } else if (ventasSessionMBean.isCotizacionEspecial() && ventasSessionMBean.getCotizacion() != null && ventasSessionMBean.getCotizacion().getIdCotizacion() != null
                && ventasSessionMBean.getCotizacion().getIdCotizacion() != 0) {
            cotizacionDTO = ventasSessionMBean.getCotizacion();
            almacen = ventasSessionMBean.getCotizacion().getSucursal();
            precio = ventasSessionMBean.isPrecio();
            costo = ventasSessionMBean.isCosto();

            for (DetalleCotizacionWebDTO d : ventasSessionMBean.getCotizacion().getDetalle()) {
                saldos.add(0, new SaldoItemDTO(d.getCantidad(), d.getCantidad(), null, d.getReferencia(), d.getBodega()));
            }

            Collections.sort(saldos, new Comparator<SaldoItemDTO>() {
                @Override
                public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                    return o1.getReferencia().compareTo(o2.getReferencia());
                }
            });
        }
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public boolean isDlgTipoPrecio() {
        return dlgTipoPrecio;
    }

    public void setDlgTipoPrecio(boolean dlgTipoPrecio) {
        this.dlgTipoPrecio = dlgTipoPrecio;
    }

    public boolean isPrecio() {
        return precio;
    }

    public void setPrecio(boolean precio) {
        this.precio = precio;
    }

    public boolean isCosto() {
        return costo;
    }

    public void setCosto(boolean costo) {
        this.costo = costo;
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public boolean isEnviar() {
        return enviar;
    }

    public void setEnviar(boolean enviar) {
        this.enviar = enviar;
    }

    public List<AlmacenDTO> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<AlmacenDTO> almacenes) {
        this.almacenes = almacenes;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public BigDecimal getTotalCotizacion() {
        if (saldos != null && !saldos.isEmpty()) {
            BigDecimal total = new BigDecimal(0);
            for (SaldoItemDTO s : saldos) {
                if (precio) {
                    total = total.add(new BigDecimal(baruGenericMBean.obtenerPrecioVenta(s.getReferencia()) * s.getCantidadNecesaria()));
                } else if (costo) {
                    total = total.add(new BigDecimal(baruGenericMBean.obtenerPrecioCosto(s.getReferencia(), almacen) * s.getCantidadNecesaria()));
                }
            }
            return total;
        } else {
            return new BigDecimal(0);
        }
    }

    public void consultarAlmacen() {
        dlgTipoPrecio = false;
        precio = false;
        costo = false;
        almacenes = new ArrayList<>();
        saldos = new ArrayList<>();
        List<Almacen> storages = new ArrayList<>();

        storages = almacenFacade.obtenerAlmacenes(almacen);

        if (storages != null && !storages.isEmpty() && storages.size() > 1) {
            for (Almacen a : storages) {
                AlmacenDTO dto = new AlmacenDTO();

                dto.setWhsCode(a.getWhsCode());
                dto.setWhsName(a.getWhsName());
                dto.setNombrextablet(a.getUnombrextablet());

                almacenes.add(dto);
            }
        } else if (storages != null && !storages.isEmpty() && storages.size() == 1) {
            dlgTipoPrecio = true;
        }
    }

    public void seleccionarAlmacen() {
        dlgTipoPrecio = false;
        almacen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacen");

        if (almacen != null && !almacen.isEmpty()) {
            dlgTipoPrecio = true;
        }
    }

    public void seleccionarCosto() {
        if (costo) {
            costo = false;
        } else {
            costo = true;
            precio = false;
        }
    }

    public void seleccionarPrecio() {
        if (precio) {
            precio = false;
        } else {
            precio = true;
            costo = false;
        }
    }

    public void cargarItemsAlmacen() {
        if (precio || costo) {
            List<SaldoItemInventario> balances = saldoItemInventarioFacade.obtenerSaldoAlmacen(almacen);

            if (balances != null && !balances.isEmpty()) {
                for (SaldoItemInventario s : balances) {
                    saldos.add(0, new SaldoItemDTO(s.getOnHand().intValue(), s.getOnHand().intValue(), null, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode()));
                }

                Collections.sort(saldos, new Comparator<SaldoItemDTO>() {
                    @Override
                    public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                        return o1.getReferencia().compareTo(o2.getReferencia());
                    }
                });
            }
        } else {
            mostrarMensaje("Error", "No se seleccionó un tipo de precio.", true, false, false);
            log.log(Level.SEVERE, "No se selecciono un tipo de precio");
            return;
        }
    }

    public String crearCotizacion() {
        if (cotizacionDTO == null || cotizacionDTO.getIdCotizacion() == null || cotizacionDTO.getIdCotizacion() == 0) {
            /*Se hace registro en BD para tener la contancia de la creacion de la cotizacion*/
            CotizacionWeb cotizacion = new CotizacionWeb();

            cotizacion.setEstado("SP");
            cotizacion.setIdVendedor(Integer.parseInt(userSessionInfoMBean.getCodigoVentas()));
            cotizacion.setSucursal(almacen);
            cotizacion.setUsuario(userSessionInfoMBean.getUsuario());
            cotizacion.setFecha(new Date());

            try {
                cotizacionWebFacade.create(cotizacion);
                log.log(Level.INFO, "Se creo la cotizacion especial con id {0}", cotizacion.getIdCotizacion());
                cotizacionDTO = new CotizacionWebDTO(cotizacion.getIdVendedor(), cotizacion.getIdCotizacion(), cotizacion.getSucursal(), cotizacion.getEstado(),
                        null, cotizacion.getFecha(), new ArrayList<DetalleCotizacionWebDTO>());
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al crear cotizacion especial", e);
                mostrarMensaje("Error", "Ocurrió un error al registrar los datos de la cotización.", true, false, false);
                return "";
            }
        } else {
            detalleCotizacionWebFacade.eliminarDetalleCotizacion(cotizacionDTO.getIdCotizacion());
        }

        try {
            for (SaldoItemDTO s : saldos) {
                DetalleCotizacionWeb detalle = new DetalleCotizacionWeb();

                detalle.setBodega(s.getAlmacen());
                detalle.setCantidad(s.getCantidadNecesaria());
                detalle.setReferencia(s.getReferencia());
                detalle.setIdCotizacion(new CotizacionWeb(cotizacionDTO.getIdCotizacion()));
                detalle.setPrecioUnitario(costo ? baruGenericMBean.obtenerPrecioCosto(s.getReferencia(), almacen).longValue() : precio ? baruGenericMBean.obtenerPrecioVentaAntesIVA(s.getReferencia()) : 0);

                detalleCotizacionWebFacade.create(detalle);
                log.log(Level.INFO, "Se creo detalle con el id {0}", detalle.getIdDetalleCotizacion());

                cotizacionDTO.getDetalle().add(new DetalleCotizacionWebDTO(s.getCantidad(), detalle.getPrecioUnitario(), detalle.getIdDetalleCotizacion(), s.getReferencia(), s.getAlmacen()));
            }

            /*Si los datos son guardados correctamente, se manda al usuario a que ingrese los datos del cliente*/
            ventasSessionMBean.setCotizacion(cotizacionDTO);
            ventasSessionMBean.setCotizacionEspecial(true);
            ventasSessionMBean.setPrecio(precio);
            ventasSessionMBean.setCosto(costo);
            return "clientes";
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al crear cotizacion especial", e);
            mostrarMensaje("Error", "Ocurrió un error al registrar los datos de la cotización.", true, false, false);
            return "";
        }
    }

    public void seleccionarImprimir() {
        if (imprimir) {
            imprimir = false;
            log.log(Level.INFO, "No se imprimira la cotizacion especial {0}", ventasSessionMBean.getNumeroCotizacion());
        } else {
            imprimir = true;
            log.log(Level.INFO, "Se imprimira la cotizacion especial {0}", ventasSessionMBean.getNumeroCotizacion());
        }
    }

    public void seleccionarEnivar() {
        if (enviar) {
            enviar = false;
            log.log(Level.INFO, "No se mandara la cotizacion especial {0} por correo electronico", ventasSessionMBean.getNumeroCotizacion());
        } else {
            enviar = true;
            log.log(Level.INFO, "Se mandara la cotizacion especial {0} por correo electronico", ventasSessionMBean.getNumeroCotizacion());
        }
    }

    public void actualizarDatosCotizacionWeb() {
        String[] s = null;

        if (ventasSessionMBean.isExitoCotizacion()) {
            CotizacionWeb cot = cotizacionWebFacade.find(ventasSessionMBean.getCotizacion().getIdCotizacion());

            if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
                cot.setEstado("ST");
                cot.setNumeroDocSAP(String.valueOf(ventasSessionMBean.getNumeroCotizacion()));
                cot.setNitCliente(ventasSessionMBean.getCotizacion().getNitCliente());
                cot.setImprimir(imprimir);
                cot.setEnviarEmail(enviar ? "1" : "0");

                try {
                    cotizacionWebFacade.edit(cot);
                    log.log(Level.INFO, "Se marco la cotizacion con el estado CT, a la cotizacion con id [{0}]", ventasSessionMBean.getCotizacion().getIdCotizacion());

                    s = generarDocumento(ventasSessionMBean.getNumeroCotizacion().intValue(), 1, ventasSessionMBean.getNumeroCotizacion().toString(), "cotizacion",
                            userSessionInfoMBean.getAlmacen(), null, imprimir, null);

                    if (enviar) {
                        cotizacionDTO.setNitCliente(ventasSessionMBean.getCotizacion().getNitCliente());
                        notificarProceso(s);
                    }
                    ventasSessionMBean.setCotizacion(new CotizacionWebDTO());
                    ventasSessionMBean.setExitoCotizacion(false);
                    ventasSessionMBean.setNumeroCotizacion(0L);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al actualizar el estado de la cotizacion con id [{0}]. Error [{1}].",
                            new Object[]{ventasSessionMBean.getCotizacion().getIdCotizacion(), e.getMessage()});
                }
            }
        }

        limpiar();
    }

    private String[] generarDocumento(Integer id, Integer copias, String nombreArchivo, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), nombreArchivo + ".pdf"};
            } else {
                log.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    private void notificarProceso(String[] adjunto) {
        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
        List<String[]> adjuntos = new ArrayList<>();
        Map<String, String> params = new HashMap<>();

        try {
            GenericRESTResponseDTO res = new GenericRESTResponseDTO();
            if (adjunto != null) {
                adjuntos.add(adjunto);
            }

            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cotizacionDTO.getNitCliente());

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty() && socio.getEmail() != null && !socio.getEmail().isEmpty()) {
                /*Se escribe el nombre del cliente*/
                String nombreCliente = (socio.getNombres() + " " + socio.getApellido1()).replace("Ó", "&Oacute;").replace("Á", "&Aacute;").replace("É", "&Eacute;")
                        .replace("Í", "&Iacute;").replace("Ú", "&Uacute;").replace("Ñ", "&Ntilde;");

                params.put("documento", "cotizaci&oacute;n");
                params.put("tipo", "acaba de realizar en nuestra tienda");
                params.put("cliente", nombreCliente);

                res = client.enviarHtmlEmail("Cotización matisses", "Cotización Matisses " + ventasSessionMBean.getNumeroCotizacion(),
                        socio.getEmail(), "venta", adjuntos, params);
            }

            log.log(Level.INFO, res.getMensaje());
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al enviar la notificacion. ", e);
        }
    }

    public void cancelarCotizacionEspecial() {
        CotizacionWeb cot = cotizacionWebFacade.find(cotizacionDTO.getIdCotizacion());

        if (cot != null && cot.getIdCotizacion() != null && cot.getIdCotizacion() != 0) {
            cot.setEstado("SC");

            try {
                cotizacionWebFacade.edit(cot);
                log.log(Level.INFO, "Se marco la cotizaicon con id {0}, con el estado SC -> COTIZACION ESPECIAL CANCELADA", cotizacionDTO.getIdCotizacion());
                mostrarMensaje("Éxito", "El proceso de creación de la cotización fue cancelado correctamente.", false, true, false);
                limpiar();
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al cancelar la cotizacion con id {0}. Error {1}", new Object[]{cotizacionDTO.getIdCotizacion(), e.getMessage()});
                mostrarMensaje("Error", "No se pudo cancelar el proceso de creación de la cotización especial.", true, false, false);
                return;
            }
        }
    }

    private void limpiar() {
        almacen = "";
        dlgTipoPrecio = false;
        precio = false;
        costo = false;
        imprimir = false;
        enviar = false;
        cotizacionDTO = new CotizacionWebDTO();
        almacenes = new ArrayList<>();
        saldos = new ArrayList<>();
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setCotizacion(null);
        ventasSessionMBean.setDocumentoCliente(null);
        ventasSessionMBean.setExitoCotizacion(false);
        ventasSessionMBean.setNumeroCotizacion(null);
        ventasSessionMBean.setCosto(false);
        ventasSessionMBean.setPrecio(false);
        ventasSessionMBean.setCotizacionEspecial(false);
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
