package co.matisses.web.mbean.clientes;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.HistoricoCliente;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.HistoricoClienteFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.HistoricoClienteDTO;
import co.matisses.web.dto.SaldoItemDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
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
@Named(value = "historicoClienteMBean")
public class HistoricoClienteMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationBean;
    private static final Logger CONSOLE = Logger.getLogger(HistoricoClienteMBean.class.getSimpleName());
    private int totalPaginas;
    private int datosPagina = 7;
    private int pagina;
    private int id;
    private Double totalVentasSinIVA = 0.0;
    private Double totalVentasConIVA = 0.0;
    private String parametroBusqueda;
    private String fuente;
    private String factura;
    private String fecha;
    private String referencia;
    private ClienteSAPDTO cliente;
    private List<Integer> paginas;
    private List<String> almacenes;
    private List<HistoricoClienteDTO> historicos;
    private List<HistoricoClienteDTO> historicosVisibles;
    private List<HistoricoClienteDTO> datos;
    private List<HistoricoClienteDTO> detalle;
    private List<SaldoItemDTO> saldos;
    @EJB
    private HistoricoClienteFacade historicoClienteFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;

    public HistoricoClienteMBean() {
        cliente = new ClienteSAPDTO();
        paginas = new ArrayList<>();
        almacenes = new ArrayList<>();
        historicos = new ArrayList<>();
        datos = new ArrayList<>();
        detalle = new ArrayList<>();
        saldos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerAlmacenes();
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getTotalVentasSinIVA() {
        return totalVentasSinIVA;
    }

    public void setTotalVentasSinIVA(Double totalVentasSinIVA) {
        this.totalVentasSinIVA = totalVentasSinIVA;
    }

    public Double getTotalVentasConIVA() {
        return totalVentasConIVA;
    }

    public void setTotalVentasConIVA(Double totalVentasConIVA) {
        this.totalVentasConIVA = totalVentasConIVA;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public ClienteSAPDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteSAPDTO cliente) {
        this.cliente = cliente;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<String> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<String> almacenes) {
        this.almacenes = almacenes;
    }

    public List<HistoricoClienteDTO> getHistoricosVisibles() {
        return historicosVisibles;
    }

    public void setHistoricosVisibles(List<HistoricoClienteDTO> historicosVisibles) {
        this.historicosVisibles = historicosVisibles;
    }

    public List<HistoricoClienteDTO> getDatos() {
        return datos;
    }

    public void setDatos(List<HistoricoClienteDTO> datos) {
        this.datos = datos;
    }

    public List<HistoricoClienteDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<HistoricoClienteDTO> detalle) {
        this.detalle = detalle;
    }

    public List<SaldoItemDTO> getSaldos() {
        return saldos;
    }

    public void setSaldos(List<SaldoItemDTO> saldos) {
        this.saldos = saldos;
    }

    public Long getObtenerTotalCompras() {
        if (cliente != null && cliente.getNit() != null && !cliente.getNit().isEmpty()) {
            return socioDeNegociosFacade.obtenerTotalCompras(cliente.getNit());
        }

        return 0L;
    }

    public Integer getObtenerSaldoAlmacen(String referencia, String almacen) {
        for (SaldoItemDTO s : saldos) {
            if (s.getAlmacen().equals(almacen) && s.getReferencia().equals(referencia)) {
                return s.getCantidad();
            }
        }

        return 0;
    }

    private void obtenerAlmacenes() {
        almacenes = new ArrayList<>();

        List<Almacen> whsCode = almacenFacade.almacenesActivos();

        if (whsCode != null && !whsCode.isEmpty()) {
            for (Almacen a : whsCode) {
                if (a.getWhsCode().substring(0, 1).equals("0")) {
                    almacenes.add(a.getWhsCode());
                }
            }
        }
    }

    public void obtenerHistorico() {
        pagina = 1;
        totalVentasSinIVA = 0.0;
        totalVentasConIVA = 0.0;
        datos = new ArrayList<>();
        cliente = new ClienteSAPDTO();
        id = 0;
        fuente = null;
        factura = null;
        fecha = null;
        historicos = new ArrayList<>();
        historicosVisibles = new ArrayList<>();
        paginas = new ArrayList<>();
        detalle = new ArrayList<>();
        referencia = null;

        CONSOLE.log(Level.INFO, "Consultando ventas para historico de clientes");
        List<HistoricoCliente> history = historicoClienteFacade.obtenerHistoricoCliente("nit", parametroBusqueda);
        history.addAll(historicoClienteFacade.obtenerHistoricoCliente("refCorta", parametroBusqueda));
        history.addAll(historicoClienteFacade.obtenerHistoricoCliente("factura", parametroBusqueda));

        if (history != null && !history.isEmpty()) {
            for (HistoricoCliente h : history) {
                boolean existe = false;

                for (HistoricoClienteDTO d : historicos) {
                    if (d.getId().equals(h.getId())) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    HistoricoClienteDTO dto = new HistoricoClienteDTO();

                    dto.setCantidadDevolucion(h.getCantidadDevolucion().intValue());
                    dto.setCantidadVenta(h.getCantidadVenta().intValue());
                    dto.setCodDpto(h.getCodDpto());
                    dto.setCodGrupo(h.getCodGrupo());
                    dto.setCodSubgrupo(h.getCodSubgrupo());
                    dto.setComentario(h.getComentario());
                    dto.setCosto(h.getCosto());
                    dto.setDescripcion(h.getDescripcion());
                    dto.setDevolucion(h.getDevolucion());
                    dto.setFactura(h.getFactura());
                    dto.setFechaDevolucion(h.getFechaDevolucion());
                    dto.setFechaFactura(h.getFechaFactura());
                    dto.setFuente(h.getFuente().toUpperCase());
                    dto.setId(h.getId());
                    dto.setModelo(h.getModelo());
                    dto.setNit(h.getNit());
                    dto.setNomDpto(h.getNomDpto());
                    dto.setNomGrupo(h.getNomGrupo());
                    dto.setNomSubgrupo(h.getNomSubgrupo());
                    dto.setPorcentajeUtilidad(h.getPorcentajeUtilidad());
                    dto.setPrecioSinIva(h.getPrecioSinIva());
                    dto.setProveedorExterior(h.getProveedorExterior());
                    dto.setRefCorta(h.getRefCorta());
                    dto.setRefProv(h.getRefProv());
                    dto.setReferencia(h.getReferencia());

                    historicos.add(dto);
                }
            }
        }

        Collections.sort(historicos, new Comparator<HistoricoClienteDTO>() {
            @Override
            public int compare(HistoricoClienteDTO o1, HistoricoClienteDTO o2) {
                return o1.getFechaFactura().compareTo(o2.getFechaFactura());
            }
        });

        gestionarHistorico();
    }

    public void gestionarHistorico() {
        historicosVisibles = new ArrayList<>();

        long nroRegistros = historicos.size();

        totalPaginas = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPagina) + (nroRegistros % datosPagina > 0 ? 1 : 0);
        if (pagina == 0) {
            pagina = 1;
        } else if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();

        if (historicos != null && !historicos.isEmpty()) {
            for (int i = (((pagina * 1) - 1) * datosPagina); i < (datosPagina * pagina); i++) {
                try {
                    historicosVisibles.add(historicos.get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        int posIni, posFin;
        if (pagina == 1) {
            posIni = pagina - 0;
            posFin = pagina + 4;
        } else if (pagina == 2) {
            posIni = pagina - 1;
            posFin = pagina + 3;
        } else if (pagina > 2 && pagina <= (totalPaginas - 2)) {
            posIni = pagina - 2;
            posFin = pagina + 2;
        } else if (totalPaginas - pagina == 1) {
            posIni = (pagina - 3) == 0 ? 1 : pagina - 3;
            posFin = pagina + 1;
        } else {
            posIni = (pagina - 4) <= 0 ? 1 : pagina - 4;
            posFin = (int) totalPaginas;
        }
        for (int i = posIni; i <= posFin && i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            gestionarHistorico();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            gestionarHistorico();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        gestionarHistorico();
    }

    public void seleccionarDocumento() {
        totalVentasSinIVA = 0.0;
        datos = new ArrayList<>();
        saldos = new ArrayList<>();
        cliente = new ClienteSAPDTO();
        id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
        fuente = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fuente");
        factura = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("factura");
        fecha = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("fecha");

        SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documento").trim() + "CL");

        if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
            cliente.setNit(socio.getCardCode());
            cliente.setRazonSocial(socio.getRazonSocial());
            cliente.setEmail(socio.getEmail());
            cliente.setCelular(socio.getCelular());
            cliente.setDireccion(socio.getDireccion());
        }

        if (historicos != null && !historicos.isEmpty()) {
            for (HistoricoClienteDTO h : historicos) {
                if (h.getFactura().equals(factura)) {
                    boolean existe = false;

                    for (HistoricoClienteDTO d : datos) {
                        if (d.getReferencia().equals(h.getReferencia())) {
                            existe = true;
                            break;
                        }
                    }

                    if (!existe) {
                        HistoricoClienteDTO dto = new HistoricoClienteDTO();

                        dto.setCantidadDevolucion(h.getCantidadDevolucion());
                        dto.setCantidadVenta(h.getCantidadVenta());
                        dto.setCodDpto(h.getCodDpto());
                        dto.setCodGrupo(h.getCodGrupo());
                        dto.setCodSubgrupo(h.getCodSubgrupo());
                        dto.setComentario(h.getComentario());
                        dto.setCosto(h.getCosto());
                        dto.setDescripcion(h.getDescripcion());
                        dto.setDevolucion(h.getDevolucion());
                        dto.setFactura(h.getFactura());
                        dto.setFechaDevolucion(h.getFechaDevolucion());
                        dto.setFechaFactura(h.getFechaFactura());
                        dto.setFuente(h.getFuente().toUpperCase());
                        dto.setId(h.getId());
                        dto.setModelo(h.getModelo());
                        dto.setNit(h.getNit());
                        dto.setNomDpto(h.getNomDpto());
                        dto.setNomGrupo(h.getNomGrupo());
                        dto.setNomSubgrupo(h.getNomSubgrupo());
                        dto.setPorcentajeUtilidad(h.getPorcentajeUtilidad());
                        dto.setPrecioSinIva(h.getPrecioSinIva());
                        dto.setProveedorExterior(h.getProveedorExterior());
                        dto.setRefCorta(h.getRefCorta());
                        dto.setRefProv(h.getRefProv());
                        dto.setReferencia(h.getReferencia());

                        datos.add(dto);

                        List<SaldoItemInventario> onHand = saldoItemInventarioFacade.buscarReferenciaSaldoBodegaVenta(dto.getReferencia());

                        if (onHand != null && !onHand.isEmpty()) {
                            for (SaldoItemInventario s : onHand) {
                                saldos.add(new SaldoItemDTO(s.getOnHand().intValue(), 0, s.getSaldoItemInventarioPK().getItemCode(), s.getSaldoItemInventarioPK().getWhsCode().getWhsCode()));
                            }
                        }
                    }
                }
            }

            totalVentasSinIVA = historicoClienteFacade.obtenerTotalSinIVA(socio.getCardCode().replace("CL", ""), factura);
            FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(factura));

            if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
                totalVentasConIVA = fac.getDocTotal().doubleValue();
            }
        }
    }

    public void mostrarDetalleDevolucion() {
        detalle = new ArrayList<>();
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");

        for (HistoricoClienteDTO h : historicos) {
            if (h.getFactura().equals(factura) && h.getReferencia().equals(referencia)) {
                detalle.add(h);
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
