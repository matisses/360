package co.matisses.web.mbean.traslados;

import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentBinAllocationDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferDocumentLinesDTO;
import co.matisses.b1ws.client.stocktransfer.StockTransferServiceConnector;
import co.matisses.b1ws.client.stocktransfer.StockTransferServiceException;
import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.TrasladosSAPFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.Traslado;
import co.matisses.persistence.web.facade.TrasladoFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.dto.AlmacenDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.dto.UbicacionSAPDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
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
@Named(value = "trasladoPalletMBean")
public class TrasladoPalletMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    private static final Logger log = Logger.getLogger(TrasladoPalletMBean.class.getSimpleName());
    private Integer ubicacionesSeleccionadas;
    private String almacenOrigen;
    private String almacenDestino;
    private boolean imprimir = false;
    private List<AlmacenDTO> almacenesOrigen;
    private List<AlmacenDTO> almacenesDestino;
    private List<UbicacionSAPDTO> ubicacionesPallet;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private TrasladoFacade trasladoFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;

    public TrasladoPalletMBean() {
        ubicacionesSeleccionadas = 0;
        almacenesDestino = new ArrayList<>();
        almacenesOrigen = new ArrayList<>();
        ubicacionesPallet = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerAlmacenesOrigen();
    }

    public BaruApplicationMBean getBaruAplicationBean() {
        return baruAplicationBean;
    }

    public void setBaruAplicationBean(BaruApplicationMBean baruAplicationBean) {
        this.baruAplicationBean = baruAplicationBean;
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public Integer getUbicacionesSeleccionadas() {
        return ubicacionesSeleccionadas;
    }

    public void setUbicacionesSeleccionadas(Integer ubicacionesSeleccionadas) {
        this.ubicacionesSeleccionadas = ubicacionesSeleccionadas;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public String getAlmacenOrigenSeleccionado() {
        if (almacenOrigen != null && !almacenOrigen.isEmpty()) {
            for (AlmacenDTO a : almacenesOrigen) {
                if (a.getWhsCode().equals(almacenOrigen)) {
                    return a.getWhsCode() + " - " + a.getNombrextablet();
                }
            }
        }
        return "Seleccione";
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public String getAlmacenDestinoSeleccionado() {
        if (almacenDestino != null && !almacenDestino.isEmpty()) {
            for (AlmacenDTO a : almacenesDestino) {
                if (a.getWhsCode().equals(almacenDestino)) {
                    return a.getWhsCode() + " - " + a.getNombrextablet();
                }
            }
        }
        return "Seleccione";
    }

    public boolean isImprimir() {
        return imprimir;
    }

    public void setImprimir(boolean imprimir) {
        this.imprimir = imprimir;
    }

    public List<AlmacenDTO> getAlmacenesOrigen() {
        return almacenesOrigen;
    }

    public void setAlmacenesOrigen(List<AlmacenDTO> almacenesOrigen) {
        this.almacenesOrigen = almacenesOrigen;
    }

    public List<AlmacenDTO> getAlmacenesDestino() {
        return almacenesDestino;
    }

    public void setAlmacenesDestino(List<AlmacenDTO> almacenesDestino) {
        this.almacenesDestino = almacenesDestino;
    }

    public List<UbicacionSAPDTO> getUbicacionesPallet() {
        return ubicacionesPallet;
    }

    public void setUbicacionesPallet(List<UbicacionSAPDTO> ubicacionesPallet) {
        this.ubicacionesPallet = ubicacionesPallet;
    }

    private void obtenerAlmacenesOrigen() {
        almacenesOrigen = new ArrayList<>();
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesPallet();

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen a : almacenes) {
                almacenesOrigen.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
            }
        }
    }

    public void seleccionarAlmacenOrigen() {
        almacenOrigen = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacenOrigen");
        log.log(Level.INFO, "Almacen origen seleccionado {0}", almacenOrigen);

        obtenerAlmacenesDestino();
    }

    public void obtenerAlmacenesDestino() {
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesPallet();

        if (almacenes != null && !almacenes.isEmpty()) {
            String prefijo = almacenOrigen.substring(0, 2);

            for (Almacen a : almacenes) {
                if (!a.getWhsCode().equals(almacenOrigen)) {
                    if (prefijo.substring(0, 1).equals("0") && a.getWhsCode().substring(0, 1).equals("0")) {
                        almacenesDestino.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                    } else if (a.getWhsCode().substring(0, 2).equals(prefijo)) {
                        almacenesDestino.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                    }
                }
            }
        }
    }

    public void seleccionarAlmacenDestino() {
        almacenDestino = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("almacenDestino");
        log.log(Level.INFO, "Almacen destino seleccionado {0}", almacenDestino);

        obtenerUbicacionesPallet();
    }

    private void obtenerUbicacionesPallet() {
        /*NOTA: Unicamente se mostraran las ubicaciones que tienen saldo*/
        ubicacionesPallet = new ArrayList<>();

        List<UbicacionSAP> allocation = ubicacionSAPFacade.obtenerUbicacionPalletAlmacenSaldo(almacenOrigen);

        if (allocation != null && !allocation.isEmpty()) {
            log.log(Level.INFO, "Se encontraron {0} con saldo en la ubicacion PALLET", allocation.size());

            for (UbicacionSAP u : allocation) {
                ubicacionesPallet.add(new UbicacionSAPDTO(u.getAbsEntry(), u.getBinCode().replace(u.getWhsCode(), ""), u.getWhsCode(), u.getSL1Code(), u.getSL2Code(), u.getAttr2Val()));
            }
        }
    }

    public void seleccionarUbicacion() {
        Integer absEntry = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("absEntry"));

        for (UbicacionSAPDTO u : ubicacionesPallet) {
            if (absEntry != null && absEntry.equals(u.getAbsEntry())) {
                if (u.isUsando()) {
                    u.setUsando(false);
                    ubicacionesSeleccionadas--;
                    log.log(Level.INFO, "Se marco la ubicacion [{0}] como no incluida", u.getBinCode());
                } else {
                    u.setUsando(true);
                    ubicacionesSeleccionadas++;
                    log.log(Level.INFO, "Se marco la ubicacion [{0}] como incluida", u.getBinCode());
                }
                break;
            }
        }
    }

    public void imprimirTraslado() {
        if (imprimir) {
            imprimir = false;
        } else {
            imprimir = true;
        }
    }

    public void crearTrasladoSAP() {
        if (sessionMBean.validarPermisoUsuario(Objetos.TRASLADO_PALLET, Acciones.CREAR)) {
            SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(sessionMBean.getUsuario());
            if (sesionSAPDTO == null) {
                log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
                mostrarMensaje("Error", "No fue posible iniciar una sesión en SAP B1WS.", true, false, false);
                return;
            }

            /*Se crea el encabezado del traslado*/
            StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

            transfer.setCardCode(sessionMBean.getCedulaEmpleado() + "PR");
            transfer.setComments("Traslado ubicacion PALLET - WebService 360");
            transfer.setSalesPersonCode(Long.parseLong(sessionMBean.getCodigoVentas()));
            transfer.setSeries(205L);
            transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());
            transfer.setFromWarehouse(almacenOrigen);
            transfer.setToWarehouse(almacenDestino);

            /*Se obtienen los saldos de las ubicaciones PALLET y se agregan al detalle del traslado*/
            int line = 0;

            boolean error = false;
            for (UbicacionSAPDTO u : ubicacionesPallet) {
                if (u.isUsando()) {
                    List<SaldoUbicacion> saldoUbicacion = saldoUbicacionFacade.buscarXBinCode(u.getWhsCode() + u.getBinCode());

                    if (saldoUbicacion != null && !saldoUbicacion.isEmpty()) {
                        for (SaldoUbicacion s : saldoUbicacion) {
                            UbicacionSAP ubi = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(almacenDestino + u.getBinCode());

                            if (ubi != null && ubi.getAbsEntry() != null && ubi.getAbsEntry() != 0) {
                                StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

                                detailLine.setFromWarehouseCode(almacenOrigen);
                                detailLine.setItemCode(s.getItemCode());
                                detailLine.setLineNum(line);
                                detailLine.setQuantity(s.getOnHandQty().doubleValue());
                                detailLine.setWarehouseCode(almacenDestino);
                                detailLine.setuComments("");
                                detailLine.setBinAllocations(new ArrayList<StockTransferDocumentBinAllocationDTO>());

                                StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();
                                StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

                                allocationLineFrom.setAllowNegativeQuantity(false);
                                allocationLineFrom.setBaseLineNumber(line);
                                allocationLineFrom.setBinAbsEntry(u.getAbsEntry().longValue());
                                allocationLineFrom.setBinActionType("salida");
                                allocationLineFrom.setQuantity(s.getOnHandQty().doubleValue());

                                detailLine.addBinAllocation(allocationLineFrom);

                                allocationLineTo.setAllowNegativeQuantity(false);
                                allocationLineTo.setBaseLineNumber(line);
                                allocationLineTo.setBinAbsEntry(ubi.getAbsEntry().longValue());
                                allocationLineTo.setBinActionType("entrada");
                                allocationLineTo.setQuantity(s.getOnHandQty().doubleValue());

                                detailLine.addBinAllocation(allocationLineTo);

                                transfer.addLine(detailLine);
                            } else {
                                mostrarMensaje("Error", "No se encontró la ubicación destino " + u.getBinCode() + ", para poder realizar el traslado.", true, false, false);
                                log.log(Level.SEVERE, "No se encontro la ubicacion destino {}, para poder realizar el traslado", u.getBinCode());
                                error = true;
                                return;
                            }
                        }
                    }
                }

                if (error) {
                    return;
                }
            }

            if (!error) {
                Integer numeroTraslado = 0;
                try {
                    StockTransferServiceConnector connection = sapB1WSBean.getStockTransferServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
                    numeroTraslado = connection.createStockTransferDocument(transfer);
                    mostrarMensaje("Éxito", "Se creó el traslado #" + trasladosSAPFacade.find(numeroTraslado).getDocNum(), false, true, false);
                    log.log(Level.INFO, "Se creo el traslado #{0}", trasladosSAPFacade.find(numeroTraslado).getDocNum());
                } catch (StockTransferServiceException ex) {
                    log.log(Level.SEVERE, "Ocurrio un error al crear el traslado entre almacenes. Error: [{0}]", ex.getMessage());
                    mostrarMensaje("Error", "Ocurrió un error al crear el traslado entre almacenes.", true, false, false);
                    return;
                }

                if (imprimir) {
                    /*Se crea el encabezado del traslado para poder imprimir el traslado*/
                    Traslado t = new Traslado();

                    t.setCedulaUsuario(sessionMBean.getCedulaEmpleado() + "PR");
                    t.setComentario("Traslado de ubicaciones PALLET desde 360");
                    t.setEstado("TF");
                    t.setFecha(new Date());
                    t.setNumeroTraslado(String.valueOf(trasladosSAPFacade.find(numeroTraslado).getDocNum()));
                    t.setSerie(27);
                    t.setSucursal(sessionMBean.getAlmacen());
                    t.setTipoMovimiento("pallet");
                    t.setUsuario(sessionMBean.getUsuario());

                    imprimir(trasladosSAPFacade.find(numeroTraslado).getDocNum());

                    try {
                        trasladoFacade.create(t);
                        log.log(Level.INFO, "Se crea traslado con id [{0}] en web para imprimir el traslado PALLET", t.getIdTraslado());
                    } catch (Exception e) {
                        log.log(Level.SEVERE, e.getMessage());
                        mostrarMensaje("Error", "No se pudo imprimir el traslado.", true, false, false);
                        return;
                    }
                }

                limpiar();
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este módulo.", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    public void imprimir(Integer nroDoc) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(null);
        print.setCopias(1);
        print.setDocumento("traslado");
        print.setId(nroDoc);
        print.setImprimir(imprimir);
        print.setSucursal(sessionMBean.getAlmacen());
        print.setDocumentosRelacionados(null);

        PrintRepostClient client = new PrintRepostClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{"TRASLADO", e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
        }
    }

    public void cancelarTraslado() {
        limpiar();
    }

    public void limpiar() {
        ubicacionesSeleccionadas = 0;
        almacenOrigen = null;
        almacenDestino = null;
        imprimir = false;
        almacenesOrigen = new ArrayList<>();
        almacenesDestino = new ArrayList<>();
        ubicacionesPallet = new ArrayList<>();

        obtenerAlmacenesOrigen();
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
