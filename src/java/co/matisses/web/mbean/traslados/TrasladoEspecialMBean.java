package co.matisses.web.mbean.traslados;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.TrasladosSAPFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.web.bcs.client.StockTransferClient;
import co.matisses.web.dto.AlmacenDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.StockTransferDocumentBinAllocationDTO;
import co.matisses.web.dto.StockTransferDocumentDTO;
import co.matisses.web.dto.StockTransferDocumentLinesDTO;
import co.matisses.web.dto.TrasladoDetalleDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Named(value = "trasladoEspecialMBean")
public class TrasladoEspecialMBean implements Serializable {

    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @Inject
    private UserSessionInfoMBean sessionMBean;
    private static final Logger log = Logger.getLogger(TrasladoEspecialMBean.class.getSimpleName());
    private String almacenOrigen;
    private String almacenDestino;
    private boolean imprimir = false;
    private List<TrasladoDetalleDTO> trasladoDetalles;
    private List<AlmacenDTO> almacenesOrigen;
    private List<AlmacenDTO> almacenesDestino;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private TrasladosSAPFacade trasladosSAPFacade;

    public TrasladoEspecialMBean() {
        trasladoDetalles = new ArrayList<>();
        almacenesDestino = new ArrayList<>();
        almacenesOrigen = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerAlmacenesOrigen();
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
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

    public List<TrasladoDetalleDTO> getTrasladoDetalles() {
        return trasladoDetalles;
    }

    public void setTrasladoDetalles(List<TrasladoDetalleDTO> trasladoDetalles) {
        this.trasladoDetalles = trasladoDetalles;
    }

    private void obtenerAlmacenesOrigen() {
        almacenesOrigen = new ArrayList<>();
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesBaru();

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen a : almacenes) {
                if (!(a.getWhsCode().contains("CL") || a.getWhsCode().contains("DM") || a.getWhsCode().substring(0, 2).equals("08"))) {
                    almacenesOrigen.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                }
            }

            Collections.sort(almacenesOrigen, new Comparator<AlmacenDTO>() {
                @Override
                public int compare(AlmacenDTO o1, AlmacenDTO o2) {
                    return o1.getWhsCode().compareTo(o2.getWhsCode());
                }
            });
        }
    }

    public void obtenerAlmacenesDestino() {
        almacenesDestino = new ArrayList<>();

        List<Almacen> almacenes = almacenFacade.obtenerAlmacenesBaru();

        if (almacenes != null && !almacenes.isEmpty()) {
            for (Almacen a : almacenes) {
                if (!a.getWhsCode().equals(almacenOrigen) && !(a.getWhsCode().contains("CL") || a.getWhsCode().contains("DM") || a.getWhsCode().substring(0, 2).equals("08"))) {
                    almacenesDestino.add(new AlmacenDTO(a.getWhsCode(), a.getWhsName(), a.getUnombrextablet()));
                }
            }

            Collections.sort(almacenesDestino, new Comparator<AlmacenDTO>() {
                @Override
                public int compare(AlmacenDTO o1, AlmacenDTO o2) {
                    return o1.getWhsCode().compareTo(o2.getWhsCode());
                }
            });
        }
    }

    public void cargarSaldoAlmacen() {
        log.log(Level.INFO, "Se esta obteniendo el saldo del almacen");
        trasladoDetalles = new ArrayList<>();
        List<SaldoItemInventario> saldo = saldoItemInventarioFacade.obtenerSaldoAlmacen(almacenOrigen);

        if (saldo != null && !saldo.isEmpty()) {
            for (SaldoItemInventario s : saldo) {
                if (s.getSaldoItemInventarioPK().getItemCode().length() == 20) {
                    trasladoDetalles.add(new TrasladoDetalleDTO(null, null, s.getOnHand().intValue(), null, null, s.getSaldoItemInventarioPK().getItemCode(), almacenOrigen, almacenDestino, null, null));
                }
            }
        }
    }

    public void crearTraslado() {
        if (trasladoDetalles != null && !trasladoDetalles.isEmpty()) {
            StockTransferDocumentDTO transfer = new StockTransferDocumentDTO();

            transfer.setCardCode("1035866418PR");
            transfer.setComments("Traslado especial - WebService 360***************");
            transfer.setFromWarehouse(almacenOrigen);
            transfer.setSalesPersonCode(240L);
            transfer.setSeries(27L);
            transfer.setStockTransferDocumentLines(new ArrayList<StockTransferDocumentLinesDTO>());
            transfer.setToWarehouse(almacenDestino);

            int line = 0;
            boolean ubicacionesOrigen = ubicacionSAPFacade.validarUbicacionesAlmacen(almacenOrigen);
            boolean ubicacionesDestino = ubicacionSAPFacade.validarUbicacionesAlmacen(almacenDestino);
            for (TrasladoDetalleDTO d : trasladoDetalles) {
                StockTransferDocumentLinesDTO detailLine = new StockTransferDocumentLinesDTO();

                detailLine.setFromWarehouseCode(d.getAlmacenOrigen());
                detailLine.setItemCode(d.getReferencia());
                detailLine.setLineNum(line);
                detailLine.setQuantity(d.getCantidad().doubleValue());
                detailLine.setWarehouseCode(d.getAlmacenDestino());

                if (ubicacionesDestino) {
                    StockTransferDocumentBinAllocationDTO allocationLineTo = new StockTransferDocumentBinAllocationDTO();

                    allocationLineTo.setAllowNegativeQuantity(false);
                    allocationLineTo.setBaseLineNumber(line);
                    allocationLineTo.setBinAbsEntry(ubicacionSAPFacade.obtenerDatosUbicacionBinCode(almacenDestino + "TM").getAbsEntry().longValue());
                    allocationLineTo.setBinActionType("entrada");
                    allocationLineTo.setQuantity(d.getCantidad().doubleValue());

                    detailLine.addBinAllocation(allocationLineTo);
                }

                if (ubicacionesOrigen) {
                    List<SaldoUbicacion> saldo = saldoUbicacionFacade.findByItemCodeAndWhsCode(true, d.getReferencia(), almacenOrigen);

                    if (saldo != null && !saldo.isEmpty()) {
                        for (SaldoUbicacion s : saldo) {
                            StockTransferDocumentBinAllocationDTO allocationLineFrom = new StockTransferDocumentBinAllocationDTO();

                            allocationLineFrom.setAllowNegativeQuantity(false);
                            allocationLineFrom.setBaseLineNumber(line);
                            allocationLineFrom.setBinAbsEntry(s.getUbicacion().getAbsEntry().longValue());
                            allocationLineFrom.setBinActionType("salida");
                            allocationLineFrom.setQuantity(s.getOnHandQty().doubleValue());

                            detailLine.addBinAllocation(allocationLineFrom);
                        }
                    }
                }

                transfer.addLine(detailLine);
            }

            try {
                StockTransferClient client = new StockTransferClient(baruAplicationBean.obtenerValorPropiedad("url.bcs.rest"));

                GenericRESTResponseDTO res = client.crearStockTransfer(transfer);

                if (res.getEstado() == 1) {
                    Integer numeroTraslado = res.getValor();
                    Integer docNum = trasladosSAPFacade.find(numeroTraslado).getDocNum();

                    if (imprimir) {
                        imprimir(trasladosSAPFacade.find(docNum).getDocNum());
                    }
                    limpiar();
                } else {
                    log.log(Level.SEVERE, "No fue posible crear el traslado");
                    mostrarMensaje("Error", "No fue posible crear el traslado.", true, false, false);
                    mostrarMensaje("Advertencia", "Verifique que el traslado no se haya creado, ocurrió un problema de tiempo de espera.", false, false, true);
                    return;
                }
            } catch (Exception ex) {
                log.log(Level.SEVERE, "Ocurrio un error al crear el traslado entre almacenes. Error: [{0}]", ex.getMessage());
                mostrarMensaje("Error", "Ocurrio un error al crear el traslado entre almacenes. " + ex.getMessage(), true, false, false);
                return;
            }
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

    public void limpiar() {
        almacenOrigen = null;
        almacenDestino = null;
        imprimir = false;
        trasladoDetalles = new ArrayList<>();
        almacenesOrigen = new ArrayList<>();
        almacenesDestino = new ArrayList<>();
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
