package co.matisses.web.mbean.comisiones;

import co.matisses.persistence.sap.entity.CentroCostosEmpleados;
import co.matisses.persistence.sap.entity.DetalleDevolucionSAP;
import co.matisses.persistence.sap.entity.DetalleFacturaSAP;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.entity.SalesPerson;
import co.matisses.persistence.sap.facade.CentroCostosEmpleadosFacade;
import co.matisses.persistence.sap.facade.DetalleDevolucionSAPFacade;
import co.matisses.persistence.sap.facade.DetalleFacturaSAPFacade;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.web.entity.ComisionAsesor;
import co.matisses.persistence.web.entity.ComisionDetalleAsesor;
import co.matisses.persistence.web.entity.ComisionPorcentaje;
import co.matisses.persistence.web.entity.ProveedoresExterior;
import co.matisses.persistence.web.facade.ComisionAsesorFacade;
import co.matisses.persistence.web.facade.ComisionDetalleAsesorFacade;
import co.matisses.persistence.web.facade.ComisionPorcentajeFacade;
import co.matisses.persistence.web.facade.ComisionReglaFacade;
import co.matisses.persistence.web.facade.ProveedoresExteriorFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.SendHtmlEmailClient;
import co.matisses.web.dto.ComisionCentroDTO;
import co.matisses.web.dto.ComisionDTO;
import co.matisses.web.dto.ComisionItemDTO;
import co.matisses.web.dto.ComisionPorcentajeDTO;
import co.matisses.web.dto.ComisionReglaDTO;
import co.matisses.web.dto.ComisionTipoReglaDTO;
import co.matisses.web.dto.DetalleDocumentoComisionDTO;
import co.matisses.web.dto.DocumentoComisionDTO;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.File;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
@Named(value = "comisionesMBean")
public class ComisionesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger CONSOLE = Logger.getLogger(ComisionesMBean.class.getSimpleName());
    private Integer paso;
    private Integer year;
    private Integer month;
    private Integer documento;
    private Integer mesesSinComisionar;
    private Double porcentajeMobiliario;
    private Double porcentajeComplementos;
    private BigDecimal valorAntesIVA;
    private BigDecimal valorDespuesIVA;
    private BigDecimal acumuladoMobiliario;
    private BigDecimal acumuladoComplementos;
    private String asesor;
    private String cliente;
    private String tipo;
    private String docRelacionados;
    private String aprobador;
    private String comentario;
    private boolean comisiona = false;
    private boolean comisionAprobada = false;
    private Date fechaInicio;
    private Date fechaFin;
    private StringBuilder tablaPagos;
    private EmpleadoDTO empleado;
    private List<Integer> years;
    private List<String[]> pagos;
    private List<Object[]> documentosPendientes;
    private List<ComisionDTO> datosComision;
    private List<ComisionPorcentajeDTO> reglasPorcentaje;
    private List<DetalleDocumentoComisionDTO> detalleDocumento;
    private List<DocumentoComisionDTO> documentos;
    private Map<String, List<EmpleadoDTO>> empleadosVenta;
    private final Map<String, Boolean> proveedores;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;
    @EJB
    private ComisionPorcentajeFacade comisionPorcentajeFacade;
    @EJB
    private ComisionReglaFacade comisionReglaFacade;
    @EJB
    private DetalleFacturaSAPFacade detalleFacturaSAPFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private ProveedoresExteriorFacade proveedoresExteriorFacade;
    @EJB
    private ComisionAsesorFacade comisionAsesorFacade;
    @EJB
    private ComisionDetalleAsesorFacade comisionDetalleAsesorFacade;
    @EJB
    private CentroCostosEmpleadosFacade centroCostosEmpleadosFacade;
    @EJB
    private DetalleDevolucionSAPFacade detalleDevolucionSAPFacade;

    public ComisionesMBean() {
        paso = 1;
        empleado = new EmpleadoDTO();
        pagos = new ArrayList<>();
        documentosPendientes = new ArrayList<>();
        reglasPorcentaje = new ArrayList<>();
        detalleDocumento = new ArrayList<>();
        documentos = new ArrayList<>();
        empleadosVenta = new HashMap<>();
        proveedores = new HashMap<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerYears();
        obtenerAsesores();
        try {
            if (!sessionMBean.validarPermisoUsuario(Objetos.COMISION, Acciones.ADMINISTRAR)) {
                aplicarAsesor(sessionMBean.getCodigoVentas());
            }
        } catch (Exception e) {
        }

        GregorianCalendar cal = new GregorianCalendar();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH) + 1;
        actualizarFechas();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getYearSeleccionado() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public Integer getDocumento() {
        return documento;
    }

    public void setDocumento(Integer documento) {
        this.documento = documento;
    }

    public Integer getMesesSinComisionar() {
        return mesesSinComisionar;
    }

    public void setMesesSinComisionar(Integer mesesSinComisionar) {
        this.mesesSinComisionar = mesesSinComisionar;
    }

    public Double getPorcentajeMobiliario() {
        return porcentajeMobiliario;
    }

    public Double getPorcentajeComplementos() {
        return porcentajeComplementos;
    }

    public BigDecimal getValorAntesIVA() {
        return valorAntesIVA;
    }

    public void setValorAntesIVA(BigDecimal valorAntesIVA) {
        this.valorAntesIVA = valorAntesIVA;
    }

    public BigDecimal getValorDespuesIVA() {
        return valorDespuesIVA;
    }

    public void setValorDespuesIVA(BigDecimal valorDespuesIVA) {
        this.valorDespuesIVA = valorDespuesIVA;
    }

    public BigDecimal getAcumuladoMobiliario() {
        return acumuladoMobiliario;
    }

    public void setAcumuladoMobiliario(BigDecimal acumuladoMobiliario) {
        this.acumuladoMobiliario = acumuladoMobiliario;
    }

    public BigDecimal getAcumuladoComplementos() {
        return acumuladoComplementos;
    }

    public void setAcumuladoComplementos(BigDecimal acumuladoComplementos) {
        this.acumuladoComplementos = acumuladoComplementos;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocRelacionados() {
        return docRelacionados;
    }

    public void setDocRelacionados(String docRelacionados) {
        this.docRelacionados = docRelacionados;
    }

    public String getAprobador() {
        return aprobador;
    }

    public void setAprobador(String aprobador) {
        this.aprobador = aprobador;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isComisiona() {
        return comisiona;
    }

    public void setComisiona(boolean comisiona) {
        this.comisiona = comisiona;
    }

    public boolean isComisionAprobada() {
        return comisionAprobada;
    }

    public void setComisionAprobada(boolean comisionAprobada) {
        this.comisionAprobada = comisionAprobada;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public StringBuilder getTablaPagos() {
        return tablaPagos;
    }

    public void setTablaPagos(StringBuilder tablaPagos) {
        this.tablaPagos = tablaPagos;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<String[]> getPagos() {
        return pagos;
    }

    public void setPagos(List<String[]> pagos) {
        this.pagos = pagos;
    }

    public List<Object[]> getDocumentosPendientes() {
        return documentosPendientes;
    }

    public void setDocumentosPendientes(List<Object[]> documentosPendientes) {
        this.documentosPendientes = documentosPendientes;
    }

    public List<ComisionDTO> getDatosComision() {
        return datosComision;
    }

    public void setDatosComision(List<ComisionDTO> datosComision) {
        this.datosComision = datosComision;
    }

    public List<DetalleDocumentoComisionDTO> getDetalleDocumento() {
        return detalleDocumento;
    }

    public void setDetalleDocumento(List<DetalleDocumentoComisionDTO> detalleDocumento) {
        this.detalleDocumento = detalleDocumento;
    }

    public List<DocumentoComisionDTO> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(List<DocumentoComisionDTO> documentos) {
        this.documentos = documentos;
    }

    public Map<String, List<EmpleadoDTO>> getEmpleadosVenta() {
        return empleadosVenta;
    }

    public void setEmpleadosVenta(Map<String, List<EmpleadoDTO>> empleadosVenta) {
        this.empleadosVenta = empleadosVenta;
    }

    public String getMonthSeleccionado() {
        switch (month) {
            case 1:
                return "Enero";
            case 2:
                return "Febrero";
            case 3:
                return "Marzo";
            case 4:
                return "Abril";
            case 5:
                return "Mayo";
            case 6:
                return "Junio";
            case 7:
                return "Julio";
            case 8:
                return "Agosto";
            case 9:
                return "Septiembre";
            case 10:
                return "Octubre";
            case 11:
                return "Noviembre";
            case 12:
                return "Diciembre";
            default:
                break;
        }
        return "Seleccione mes";
    }

    public BigDecimal getObtenerTotalVendido() {
        if (datosComision != null && !datosComision.isEmpty()) {
            return acumuladoMobiliario.add(acumuladoComplementos);
        }
        return new BigDecimal(0);
    }

    public BigDecimal getObtenerTotalDevuelto() {
        if (datosComision != null && !datosComision.isEmpty()) {
            BigDecimal total = new BigDecimal(0);

            for (ComisionDTO c : datosComision) {
                if (c.getTipo().equals("DV") && c.isIncluir()) {
                    total = total.add(new BigDecimal(c.getValor()));
                }
            }

            return total;
        }
        return new BigDecimal(0);
    }

    public Double getObtenerTotalComision() {
        DecimalFormat format = new DecimalFormat("####");
        Double total = 0D;

        if (datosComision != null && !datosComision.isEmpty()) {
            if (porcentajeComplementos > 0) {
                total += ((acumuladoComplementos.doubleValue() / 100) * porcentajeComplementos);
            }
            if (porcentajeMobiliario > 0) {
                if (porcentajeComplementos < 0) {
                    total += ((acumuladoComplementos.doubleValue() / 100) * porcentajeMobiliario);
                }
                total += ((acumuladoMobiliario.doubleValue() / 100) * porcentajeMobiliario);
            }
            return Double.parseDouble(format.format(total));
        }
        return total;
    }

    private void obtenerYears() {
        years = new ArrayList<>();

        Date fecha = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        String fechas = dateFormat.format(fecha);
        int yearIni = 2013;
        int yearFin = Integer.parseInt(fechas);
        do {
            years.add(yearFin);
            yearFin--;
        } while (yearFin >= yearIni);
    }

    private void obtenerAsesores() {
        boolean verTodos = sessionMBean.validarPermisoUsuario(Objetos.COMISION, Acciones.ADMINISTRAR);
        empleadosVenta.clear();
        /*Se obtienen los asesores que comisionan*/
        List<Object[]> employeeds = empleadoFacade.obtenerEmpleadosVentas(null);

        if (employeeds != null && !employeeds.isEmpty()) {
            for (Object[] o : employeeds) {
                boolean agregar = false;
                if (verTodos) {
                    agregar = true;
                } else if (((String) o[7]).equals(sessionMBean.getCodigoVentas())) {
                    agregar = true;
                }

                if (agregar) {
                    if (empleadosVenta.containsKey((String) o[1])) {
                        empleadosVenta.get((String) o[1])
                                .add(new EmpleadoDTO((String) o[3], (String) o[5], (String) o[2], (String) o[7], baruGenericMBean.obtenerRutaFotoEmpleadoComision((String) o[2]), (String) o[8], false));
                    } else {
                        List<EmpleadoDTO> emp = new ArrayList<>();

                        emp.add(new EmpleadoDTO((String) o[3], (String) o[5], (String) o[2], (String) o[7], baruGenericMBean.obtenerRutaFotoEmpleadoComision((String) o[2]), (String) o[8], false));

                        empleadosVenta.put((String) o[1], emp);
                    }
                }
            }
        }
    }

    public void obtenerDocumentos() throws ParseException {
        acumuladoComplementos = new BigDecimal(BigInteger.ZERO);
        acumuladoMobiliario = new BigDecimal(BigInteger.ZERO);
        datosComision = new ArrayList<>();

        obtenerMesesVencidos();

        Date fechaFinaMaxima = fechaFin;
        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(fechaFinaMaxima);

        calendarFinal.set(Calendar.DATE, calendarFinal.getActualMaximum(Calendar.DATE));
        calendarFinal.add(Calendar.DAY_OF_YEAR, 8);
        fechaFinaMaxima = calendarFinal.getTime();

        List<Object[]> datos = facturaSAPFacade.obtenerDocumentosAsesorComisionar(asesor, fechaInicio, fechaFin, fechaFinaMaxima);
        List<Integer> devolucionesNoAplicables = devolucionSAPFacade.obtenerDevolucionesNoAplicablesComision(asesor, fechaInicio, fechaFin);

        if (datos != null && !datos.isEmpty()) {
            for (Object[] i : datos) {
                Integer cantidad = (Integer) i[4];
                Double precio = ((BigDecimal) i[9]).doubleValue();
                String referencia = (String) i[3];

                ComisionDTO comision = new ComisionDTO();

                comision.setAplicar((((Character) i[10]) == null || !((Character) i[10]).toString().equals("A")));
                comision.setComision(new BigDecimal(0.0));
                comision.setDocumento((Integer) i[1]);
                comision.setFecha((Date) i[0]);
                comision.setTipo((String) i[8]);
                comision.setDocumentoCerrado(validarPagoDocumento(comision.getDocumento(), comision.getTipo()));
                comision.setIncluir(comision.isDocumentoCerrado());
                comision.setPorcentajeComision(((BigDecimal) i[6]).doubleValue());
                comision.setReferencias(new ArrayList<ComisionItemDTO>());
                comision.setValor(((BigDecimal) i[9]).doubleValue());
                comision.setValorDocumento(((BigDecimal) i[7]).doubleValue());

                if (!documentoAplicado(comision.getDocumento()) && aplicarDiasGracia(comision.getDocumento(), comision.getTipo())) {
                    if (datosComision.isEmpty()) {
                        if (comision.getTipo().equals("DV")) {
                            if (validarDevolucion(comision.getDocumento(), devolucionesNoAplicables)) {
                                if (validarSiFVComisionada(comision.getDocumento())) {
                                    comision.setAplicarDV(true);
                                } else {
                                    comision.setAplicarDV(false);
                                    comision.setIncluir(false);
                                }
                                datosComision.add(0, comision);
                                datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                            }
                        } else if (validarDocumento(comision.getDocumento(), comision.getTipo(), comision.getFecha())) {
                            datosComision.add(0, comision);
                            datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                            clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                        } else if (validarPagoDocumento(comision.getDocumento(), comision.getTipo())) {
                            datosComision.add(0, comision);
                            datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                            clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                        }
                    } else {
                        boolean existe = false;

                        for (ComisionDTO f : datosComision) {
                            if (f.getDocumento().equals((Integer) i[1])) {
                                f.setValor(f.getValor() + comision.getValor());
                                datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);

                                existe = true;
                                break;
                            } else {
                                existe = false;
                            }
                        }

                        if (!existe) {
                            if (comision.getTipo().equals("DV")) {
                                if (validarDevolucion(comision.getDocumento(), devolucionesNoAplicables)) {
                                    if (validarSiFVComisionada(comision.getDocumento())) {
                                        comision.setAplicarDV(true);
                                        comision.setIncluir(true);
                                        comision.setDocumentoCerrado(true);
                                    } else {
                                        comision.setAplicarDV(false);
                                        comision.setIncluir(false);
                                    }
                                    datosComision.add(0, comision);
                                    datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                    clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                }
                            } else if (validarDocumento((Integer) i[1], (String) i[8], (Date) i[0])) {
                                datosComision.add(0, comision);
                                datosComision.get(0).getReferencias().add(new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                            } else if (validarPagoDocumento(comision.getDocumento(), comision.getTipo())) {
                                datosComision.add(0, comision);
                                datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                            }
                        }
                    }
                }
            }

            Collections.sort(datosComision, new Comparator<ComisionDTO>() {
                @Override
                public int compare(ComisionDTO o1, ComisionDTO o2) {
                    int posicion1 = o1.getFecha().compareTo(o2.getFecha());
                    if (posicion1 == 0) {
                        return o1.getDocumento().compareTo(o2.getDocumento());
                    } else {
                        return posicion1;
                    }
                }
            });
            validarUsuarioComisiona();
        }
        obtenerComision();
    }

    private boolean aplicarDiasGracia(Integer documento, String tipo) {
        if (tipo.equals("DV")) {
            DevolucionSAP dev = devolucionSAPFacade.obtenerDevolucion(documento);

            if (dev != null && dev.getDocEntry() != null && dev.getDocEntry() != 0) {
                FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(dev.getNumAtCard()));

                if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
                    if (!fac.getDocDate().after(fechaInicio) || !fac.getDocDate().before(fechaFin)) {
                        Date fechaFinalAplica = fechaFin;

                        Calendar calendarFinal = Calendar.getInstance();
                        calendarFinal.setTime(fechaFinalAplica);

                        calendarFinal.set(Calendar.DATE, calendarFinal.getActualMaximum(Calendar.DATE));
                        calendarFinal.add(Calendar.DAY_OF_YEAR, 8);
                        fechaFinalAplica = calendarFinal.getTime();

                        if (dev.getDocDate().after(fechaFin) && dev.getDocDate().before(fechaFinalAplica)) {
                            return false;
                        }
                    } else {

                    }
                }
            }
        }
        return true;
    }

    private boolean documentoAplicado(Integer documento) {
        ComisionDetalleAsesor det = comisionDetalleAsesorFacade.validarDocumento(documento, empleado.getCodigoAsesor());

        if (det != null && det.getIdComisionDetalleAsesor() != null && det.getIdComisionDetalleAsesor() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validarSiFVComisionada(Integer documento) {
        DevolucionSAP dev = devolucionSAPFacade.obtenerDevolucion(documento);

        if (dev != null && dev.getDocEntry() != null && dev.getDocEntry() != 0) {
            ComisionDetalleAsesor det = comisionDetalleAsesorFacade.obtenerDetalleDocumento(Integer.parseInt(dev.getNumAtCard()), empleado.getCodigoAsesor());

            if (det != null && det.getIdComisionDetalleAsesor() != null && det.getIdComisionDetalleAsesor() != 0) {
                if (det.getAplicar()) {
                    return true;
                } else {
                    for (ComisionDTO c : datosComision) {
                        if (c.getDocumento().equals(Integer.parseInt(dev.getNumAtCard())) && c.isAplicar()) {
                            return true;
                        }
                    }

                    return false;
                }
            } else {
                for (ComisionDTO c : datosComision) {
                    if (c.getDocumento().equals(Integer.parseInt(dev.getNumAtCard())) && c.isAplicar() && c.isIncluir()) {
                        return true;
                    }
                }

                return false;
            }
        }
        return true;
    }

    private boolean validarDocumento(Integer documento, String tipoDocumento, Date fechaDocumento) {
        if (fechaDocumento.before(fechaInicio)) {
            /*
            1. Si la fecha es anterior a la fecha actual entonces se debe validar si la fecha no entra en los dias de gracias que son 8 del proximo mes.
            2. No entraran las facturas que se hayan creado en el mes siguiente.
             */

            String[] initialDate = new SimpleDateFormat("yyyy-MM-dd").format(fechaInicio).split("-");
            String[] finishDate = new SimpleDateFormat("yyyy-MM-dd").format(fechaDocumento).split("-");
            String[] documentDate = null;

            if (initialDate != null && initialDate.length == 3 && finishDate != null && finishDate.length == 3) {
                FacturaSAP factura;

                if (tipoDocumento != null && tipoDocumento.equals("FV")) {
                    factura = facturaSAPFacade.findByDocNum(documento);

                    if (factura != null && factura.getDocEntry() != null && factura.getDocEntry() != 0) {
                        documentDate = new SimpleDateFormat("yyyy-MM-dd").format(factura.getDocDate()).split("-");
                    }
                }

                if (documentDate != null && documentDate.length == 3) {
                    return !(initialDate[1].equals("01") && Integer.parseInt(initialDate[2]) <= 8
                            && initialDate[1].equals("01") ? Integer.parseInt(initialDate[0]) - 1 == Integer.parseInt(finishDate[0]) : Integer.parseInt(initialDate[0]) == Integer.parseInt(finishDate[0]));
                }
            }
        } else if (fechaDocumento.after(fechaFin)) {
            return false;
        }

        return true;
    }

    private boolean validarDevolucion(Integer documento, List<Integer> devolucionesNoAplicables) {
        if (devolucionesNoAplicables != null && !devolucionesNoAplicables.isEmpty()) {
            for (Integer d : devolucionesNoAplicables) {
                if (d.equals(documento)) {
                    /*Se valida si el documento fue incluido antes y si no aplicaria*/
                    ComisionDetalleAsesor det = comisionDetalleAsesorFacade.validarDocumento(documento, empleado.getCodigoAsesor());

                    if (det == null || det.getIdComisionDetalleAsesor() == null || det.getIdComisionDetalleAsesor() == 0 || !det.getAplicar() || !det.getIncluir()) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean validarPagoDocumento(Integer documento, String tipoDocumento) throws ParseException {
        if (tipoDocumento != null && tipoDocumento.equals("DV")) {
            DevolucionSAP dev = devolucionSAPFacade.obtenerDevolucion(documento);

            if (dev != null && dev.getDocEntry() != null && dev.getDocEntry() != 0) {
                FacturaSAP fac = facturaSAPFacade.findByDocNum(Integer.parseInt(dev.getNumAtCard()));

                if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
                    Date fechaInicialAplica = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + "01");
                    Date fechaFinalAplica = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + "01");

                    Calendar calendarFinal = Calendar.getInstance();
                    calendarFinal.setTime(fechaFinalAplica);

                    calendarFinal.set(Calendar.DATE, calendarFinal.getActualMaximum(Calendar.DATE));
                    calendarFinal.add(Calendar.DAY_OF_YEAR, 8);
                    fechaFinalAplica = calendarFinal.getTime();

                    Date fechaFactura = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(fac.getDocDate()));

                    if ((fechaFactura.after(fechaInicialAplica) || fechaFactura.equals(fechaInicialAplica)) && (fechaFactura.before(fechaFinalAplica) || fechaFactura.equals(fechaFinalAplica))) {
                        return true;
                    } else {
                        ComisionDetalleAsesor det = comisionDetalleAsesorFacade.validarDocumento(fac.getDocNum(), empleado.getCodigoAsesor());

                        if (det != null && det.getIdComisionDetalleAsesor() != null && det.getIdComisionDetalleAsesor() != 0 && validarPagoFV(fac.getDocNum())) {
                            return true;
                        }
                    }
                }
            }

            return false;
        } else {
            return validarPagoFV(documento);
        }
    }

    private boolean validarPagoFV(Integer documento) throws ParseException {
        /*Validar si el documento fue pagado*/
        FacturaSAP fac = facturaSAPFacade.findByDocNum(documento);

        if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
            if (fac.getUTipoNota() != null && !fac.getUTipoNota().toString().isEmpty()) {
                return true;
            } else {
                BigDecimal pagoTotal = new BigDecimal(0D);
                BigDecimal ajustePeso = new BigDecimal(0D);
                List<Date> FechaRecibo = new ArrayList<>();
                List<Date> FechaEgreso = new ArrayList<>();
                List<Date> FechaReconciliacion = new ArrayList<>();
                List<Object[]> payments = facturaSAPFacade.obtenerPagosFactura(documento);

                if (payments != null && !payments.isEmpty()) {
                    /*Se deben sumar las posiciones 5, 6, 7, 8, 9, 10, 12, 13, 14, 15*/
                    for (Object[] o : payments) {
                        if (o[2] != null) {
                            FechaRecibo.add((Date) o[2]);
                        }
                        if (o[11] != null) {
                            FechaEgreso.add((Date) o[11]);
                        }
                        if (o[16] != null) {
                            FechaReconciliacion.add((Date) o[16]);
                        }

                        pagoTotal = pagoTotal.add((BigDecimal) o[5]).add((BigDecimal) o[6]).add((BigDecimal) o[7]).add((BigDecimal) o[8]).add((BigDecimal) o[9]).
                                add((BigDecimal) o[10]).add((BigDecimal) o[12]).add((BigDecimal) o[13]).add((BigDecimal) o[14]).add((BigDecimal) o[15]);
                        ajustePeso = ajustePeso.add((BigDecimal) o[17]);
                    }
                }

                boolean fechaValida = validarFechaPago(FechaRecibo, FechaEgreso, FechaReconciliacion);
                if (pagoTotal.compareTo(fac.getDocTotal()) >= 0 && fechaValida) {
                    return true;
                } else if (pagoTotal.add(ajustePeso).compareTo(fac.getDocTotal()) >= 0 && fechaValida) {
                    return true;
                } else {
                    /*Si el valor pagado no cuadra, se verifica si hay una devolucion de por medio*/
                    List<DevolucionSAP> returns = devolucionSAPFacade.obtenerDevolucionesFactura(fac.getDocNum());

                    if (returns != null && !returns.isEmpty()) {
                        pagoTotal = new BigDecimal(0);

                        for (DevolucionSAP d : returns) {
                            pagoTotal = pagoTotal.add(d.getDocTotal());
                        }

                        return pagoTotal.compareTo(fac.getDocTotal()) >= 0 && fechaValida;
                    }
                }
            }
        }

        return false;
    }

    private boolean validarFechaPago(List<Date> FechaRecibo, List<Date> FechaEgreso, List<Date> FechaReconciliacion) throws ParseException {
        Date fechaInicialAplica = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + "01");
        Date fechaFinalAplica = new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + "01");

        Calendar calendarFinal = Calendar.getInstance();
        calendarFinal.setTime(fechaFinalAplica);

        calendarFinal.set(Calendar.DATE, calendarFinal.getActualMaximum(Calendar.DATE));
        calendarFinal.add(Calendar.DAY_OF_YEAR, 8);
        fechaFinalAplica = calendarFinal.getTime();

        boolean aplica = true;
        if (FechaRecibo != null && !FechaRecibo.isEmpty()) {
            for (Date f : FechaRecibo) {
                Date fecha = new SimpleDateFormat("yyyy-MM-dd").parse(new SimpleDateFormat("yyyy-MM-dd").format(f));
                if ((fecha.equals(fechaInicialAplica) || fecha.after(fechaInicialAplica)) || (fecha.equals(fechaFinalAplica) || fecha.before(fechaFinalAplica))) {
                    aplica = true;
                } else {
                    aplica = false;
                    break;
                }
            }
        }
        if (FechaEgreso != null && !FechaEgreso.isEmpty() && !aplica) {
            for (Date f : FechaEgreso) {
                if (f.after(fechaInicialAplica) && f.before(fechaFinalAplica)) {
                    aplica = true;
                } else {
                    aplica = false;
                    break;
                }
            }
        }
        if (FechaReconciliacion != null && !FechaReconciliacion.isEmpty() && !aplica) {
            for (Date f : FechaReconciliacion) {
                if (f.after(fechaInicialAplica) && f.before(fechaFinalAplica)) {
                    aplica = true;
                } else {
                    aplica = false;
                    break;
                }
            }
        }

        return aplica;
    }

    private void obtenerReglas(String centroCostos) {
        reglasPorcentaje = new ArrayList<>();

        /*Obtener comision por asesor si aplica*/
        List<ComisionPorcentaje> commissions = comisionPorcentajeFacade.obtenerPorcentajesCentroCosto(mesesSinComisionar, empleado.getCedula());

        if (commissions == null || commissions.isEmpty()) {
            commissions = comisionPorcentajeFacade.obtenerPorcentajesCentroCosto(mesesSinComisionar, centroCostos);
        }

        if (commissions != null && !commissions.isEmpty()) {
            for (ComisionPorcentaje c : commissions) {
                ComisionPorcentajeDTO commission = new ComisionPorcentajeDTO();

                commission.setIdCentro(new ComisionCentroDTO(c.getIdCentro().getIdCentro(), c.getIdCentro().getCentroCostos()));
                commission.setIdPorcentaje(c.getIdPorcentaje());
                commission.setLimiteInferior(c.getLimiteInferior());
                commission.setLimiteSuperior(c.getLimiteSuperior());
                commission.setMesVencidoAplicable(c.getMesVencidoAplicable());
                commission.setPorcentaje(c.getPorcentaje());

                if (c.getIdRegla() != null) {
                    ComisionReglaDTO rule = new ComisionReglaDTO();

                    rule.setIdRegla(c.getIdRegla().getIdRegla());
                    rule.setIdTipoRegla(new ComisionTipoReglaDTO(c.getIdRegla().getIdTipoRegla().getIdTipoRegla(), c.getIdRegla().getIdTipoRegla().getTipoRegla()));
                    rule.setQuery(c.getIdRegla().getQuery());
                    rule.setComplementos(c.getIdRegla().getComplementos());
                    rule.setValidoHasta(c.getIdRegla().getValidoHasta());
                    rule.setValorRegla(new HashMap<String, Double>());

                    if (rule.isQuery()) {
                        List<String> ruleValues = comisionReglaFacade.obtenerValoresReglaQuery(c.getIdRegla().getValorRegla());

                        if (ruleValues != null && !ruleValues.isEmpty()) {

                            for (String r : ruleValues) {
                                rule.getValorRegla().put(r, c.getPorcentaje());
                            }
                        }
                    } else {
                        rule.getValorRegla().put(c.getIdRegla().getValorRegla(), c.getPorcentaje());
                    }

                    commission.setIdRegla(rule);
                }

                reglasPorcentaje.add(commission);
            }
        }
    }

    private void obtenerMesesVencidos() {
        List<ComisionAsesor> comision = comisionAsesorFacade.obtenerUltimosTresMeses(asesor);

        mesesSinComisionar = 0;
        if (comision != null && !comision.isEmpty()) {
            for (ComisionAsesor c : comision) {
                if (!c.getComisiona()) {
                    mesesSinComisionar++;
                } else {
                    break;
                }
            }
        }
    }

    private boolean validarEmpleadoComplementos() {
        if (empleado != null && empleado.getCodigoAsesor() != null && !empleado.getCodigoAsesor().isEmpty()) {
            CentroCostosEmpleados cce = centroCostosEmpleadosFacade.validarCentroEmpleado(Integer.parseInt(empleado.getCodigoAsesor()));

            if (cce != null && cce.getCode() != null && cce.getCode() != 0) {
                if (cce.getName().contains("COMPLEMENTOS")) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        }
        return false;
    }

    private void obtenerComision() {
        //TODO: Se debe acondicionar el codigo para la segunda version, con porcentaje por cliente
        boolean asesorComplementos = validarEmpleadoComplementos();
        porcentajeComplementos = -1D;
        porcentajeMobiliario = 0D;

        obtenerReglas(empleado.getSucursalVenta());

        for (ComisionPorcentajeDTO c : reglasPorcentaje) {
            if (c.getIdRegla().isComplementos()) {
                if (acumuladoComplementos.compareTo(new BigDecimal(c.getLimiteInferior())) >= 0 && acumuladoComplementos.compareTo(new BigDecimal(c.getLimiteSuperior())) <= 0) {
                    porcentajeComplementos = c.getPorcentaje();
                    break;
                } else if (asesorComplementos) {
                    porcentajeComplementos = 0D;
                }
            }
        }

        for (ComisionPorcentajeDTO c : reglasPorcentaje) {
            if (porcentajeComplementos == -1D) {
                BigDecimal tmpAcumulado = acumuladoMobiliario.add(acumuladoComplementos);

                if (tmpAcumulado.compareTo(new BigDecimal(c.getLimiteInferior())) >= 0 && tmpAcumulado.compareTo(new BigDecimal(c.getLimiteSuperior())) <= 0) {
                    porcentajeMobiliario = c.getPorcentaje();
                    porcentajeComplementos = c.getPorcentaje();
                    break;
                }
            } else if (!c.getIdRegla().isComplementos() && acumuladoMobiliario.compareTo(new BigDecimal(c.getLimiteInferior())) >= 0 && acumuladoMobiliario.compareTo(new BigDecimal(c.getLimiteSuperior())) <= 0) {
                porcentajeMobiliario = c.getPorcentaje();
                break;
            }
        }

        validarUsuarioComisiona();

        Map<String, Double> referenciasOtraRegla = new HashMap<>();

        for (ComisionPorcentajeDTO c : reglasPorcentaje) {
            if (c.getIdRegla().getIdTipoRegla().getTipoRegla().equals("Referencia")) {
                referenciasOtraRegla = c.getIdRegla().getValorRegla();
            }
        }

        if (asesorComplementos && porcentajeComplementos <= 0) {
            CONSOLE.log(Level.SEVERE, "El asesor de complementos no puede coisionar en mobiliario, debido a que no cumple la meta de complementos.");
            porcentajeComplementos = 0D;
            porcentajeMobiliario = 0D;
            return;
        }

        for (ComisionDTO c : datosComision) {
            c.setComision(new BigDecimal(0.0));
            for (ComisionItemDTO i : c.getReferencias()) {
                Double comision = 0D;

                if (c.getTipo().equals("FV") && c.isIncluir()) {
                    if (referenciasOtraRegla != null && referenciasOtraRegla.containsKey(i.getReferencia())) {
                        comision = ((i.getPrecio() / 100) * referenciasOtraRegla.get(i.getReferencia()));
                        c.setComision(c.getComision().add(new BigDecimal(comision)));

                        i.setComision(comision);
                    } else if (proveedores.get(i.getReferencia().substring(0, 3)) != null && proveedores.get(i.getReferencia().substring(0, 3)) && porcentajeComplementos > 0D) {
                        comision = ((i.getPrecio() / 100) * porcentajeComplementos);
                        c.setComision(c.getComision().add(new BigDecimal(comision)));

                        i.setComision(comision);
                    } else if (proveedores.get(i.getReferencia().substring(0, 3)) == null || proveedores.get(i.getReferencia().substring(0, 3))) {
                        comision = ((i.getPrecio() / 100) * porcentajeMobiliario);
                        c.setComision(c.getComision().add(new BigDecimal(comision)));

                        i.setComision(comision);
                    } else if (porcentajeComplementos == -1D) {
                        comision = ((i.getPrecio() / 100) * porcentajeMobiliario);
                        c.setComision(c.getComision().add(new BigDecimal(comision)));

                        i.setComision(comision);
                    } else {
                        comision = 0D;
                        c.setComision(c.getComision().add(new BigDecimal(comision)));

                        i.setComision(comision);
                    }
                } else if (c.getTipo().equals("DV")) {
                    comision = ((i.getPrecio() / 100) * 1);
                    c.setComision(c.getComision().add(new BigDecimal(comision)));

                    i.setComision(comision);
                }
            }
        }
    }

    private void clasificarItem(Double valorItem, String itemCode, String tipo, boolean incluir, boolean modificar) {
        if (proveedores == null || proveedores.isEmpty()) {
            List<ProveedoresExterior> pro = proveedoresExteriorFacade.findAll();

            if (pro != null && !pro.isEmpty()) {
                for (ProveedoresExterior p : pro) {
                    if (!proveedores.containsKey(p.getProveedor())) {
                        proveedores.put(p.getProveedor(), p.getComplemento() != null ? p.getComplemento() : false);
                    }
                }
            }
        }

        if (proveedores.containsKey(itemCode.substring(0, 3))) {
            if (proveedores.get(itemCode.substring(0, 3))) {
                if (tipo.equals("FV") && incluir) {
                    acumuladoComplementos = acumuladoComplementos.add(new BigDecimal(valorItem));
                } else if (tipo.equals("FV") && !incluir && modificar) {
                    acumuladoComplementos = acumuladoComplementos.subtract(new BigDecimal(valorItem));
                } else if (tipo.equals("DV") && incluir) {
                    acumuladoComplementos = acumuladoComplementos.subtract(new BigDecimal(valorItem));
                } else if (tipo.equals("DV") && !incluir && modificar) {
                    acumuladoComplementos = acumuladoComplementos.add(new BigDecimal(valorItem));
                }
            } else if (tipo.equals("FV") && incluir) {
                acumuladoMobiliario = acumuladoMobiliario.add(new BigDecimal(valorItem));
            } else if (tipo.equals("FV") && !incluir && modificar) {
                acumuladoMobiliario = acumuladoMobiliario.subtract(new BigDecimal(valorItem));
            } else if (tipo.equals("DV") && incluir) {
                acumuladoMobiliario = acumuladoMobiliario.subtract(new BigDecimal(valorItem));
            } else if (tipo.equals("DV") && !incluir && modificar) {
                acumuladoMobiliario = acumuladoMobiliario.add(new BigDecimal(valorItem));
            }
        } else if (tipo.equals("FV") && incluir) {
            acumuladoMobiliario = acumuladoMobiliario.add(new BigDecimal(valorItem));
        } else if (tipo.equals("FV") && !incluir && modificar) {
            acumuladoMobiliario = acumuladoMobiliario.subtract(new BigDecimal(valorItem));
        } else if (tipo.equals("DV") && incluir) {
            acumuladoMobiliario = acumuladoMobiliario.subtract(new BigDecimal(valorItem));
        } else if (tipo.equals("DV") && !incluir && modificar) {
            acumuladoMobiliario = acumuladoMobiliario.add(new BigDecimal(valorItem));
        }
    }

    public void validarUsuarioComisiona() {
        for (ComisionPorcentajeDTO c : reglasPorcentaje) {
            if (getObtenerTotalVendido().doubleValue() >= c.getLimiteInferior() && getObtenerTotalVendido().doubleValue() <= c.getLimiteSuperior() && c.getPorcentaje() > 0) {
                comisiona = true;
                break;
            } else {
                comisiona = false;
            }
        }
    }

    public void aplicarDocumento() {
        if (sessionMBean.validarPermisoUsuario(Objetos.COMISION, Acciones.ADMINISTRAR)) {
            Integer doc = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documento"));

            for (ComisionDTO c : datosComision) {
                if (c.getDocumento().equals(doc)) {
                    if (c.isIncluir()) {
                        c.setIncluir(false);
                    } else {
                        c.setIncluir(true);
                    }
                    validarUsuarioComisiona();

                    for (ComisionItemDTO i : c.getReferencias()) {
                        clasificarItem(i.getPrecio(), i.getReferencia(), c.getTipo(), c.isIncluir(), true);
                    }
                    break;
                }
            }

            obtenerComision();
        }
    }

    public void seleccionarDocumento() {
        pagos = new ArrayList<>();
        detalleDocumento = new ArrayList<>();

        documento = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documento"));
        tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");

        if (tipo.equals("DV")) {
            DevolucionSAP dev = devolucionSAPFacade.obtenerDevolucion(documento);

            if (dev != null && dev.getDocEntry() != null && dev.getDocEntry() != 0) {
                cliente = dev.getCardCode() + " - " + dev.getCardName();
                valorAntesIVA = dev.getDocTotal().subtract(dev.getVatSum());
                valorDespuesIVA = dev.getDocTotal();

                if (dev.getNumAtCard() != null && !dev.getNumAtCard().isEmpty()) {
                    docRelacionados = "Factura: " + dev.getNumAtCard();
                }

                List<DetalleDevolucionSAP> det = detalleDevolucionSAPFacade.obtenerDetalleDevolucion(dev.getDocEntry());

                if (det != null && !det.isEmpty()) {
                    for (DetalleDevolucionSAP d : det) {
                        detalleDocumento.add(new DetalleDocumentoComisionDTO(d.getQuantity().intValue(), d.getItemCode(), d.getWhsCode()));
                    }
                }

                List<Object[]> payments = facturaSAPFacade.obtenerPagosFactura(Integer.parseInt(dev.getNumAtCard()));

                if (payments != null && !payments.isEmpty()) {
                    //Recibo caja - Total recibo - Pago efectivo - Pago otros medios - Pagos notas credito - Pagos cheque - Pagos tarjetas - Pagos reconciliacion interna
                    for (Object[] o : payments) {
                        String[] s = new String[14];

                        s[0] = o[2] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[2]) : "";
                        s[1] = o[3] != null ? ((Integer) o[3]).toString() : "";
                        s[2] = o[4] != null ? ((BigDecimal) o[4]).toString() : "";
                        s[3] = o[5] != null ? ((BigDecimal) o[5]).toString() : "";
                        s[4] = o[12] != null ? ((BigDecimal) o[12]).toString() : "";
                        s[5] = o[13] != null ? ((BigDecimal) o[13]).toString() : "";
                        s[6] = o[7] != null ? ((BigDecimal) o[7]).toString() : "";
                        s[7] = o[8] != null ? ((BigDecimal) o[8]).toString() : "";
                        s[8] = o[16] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[16]) : "";
                        s[9] = o[14] != null ? ((BigDecimal) o[14]).toString() : "";
                        s[10] = o[9] != null ? ((BigDecimal) o[9]).toString() : "";
                        s[11] = o[11] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[11]) : "";
                        s[12] = o[10] != null ? ((BigDecimal) o[10]).toString() : "";
                        s[13] = o[15] != null ? ((BigDecimal) o[15]).toString() : "";

                        pagos.add(s);
                    }
                }
            }
        } else if (tipo.equals("FV")) {
            FacturaSAP fac = facturaSAPFacade.findByDocNum(documento);

            if (fac != null && fac.getDocEntry() != null && fac.getDocEntry() != 0) {
                cliente = fac.getCardCode() + " - " + fac.getCardName();
                valorAntesIVA = fac.getDocTotal().subtract(fac.getVatSum());
                valorDespuesIVA = fac.getDocTotal();

                if (fac.getNumAtCard() != null && !fac.getNumAtCard().isEmpty()) {
                    docRelacionados = "Cotización: " + fac.getNumAtCard();
                }

                List<DetalleFacturaSAP> det = detalleFacturaSAPFacade.obtenerDetalleFactura(fac.getDocEntry().doubleValue());

                if (det != null && !det.isEmpty()) {
                    for (DetalleFacturaSAP d : det) {
                        detalleDocumento.add(new DetalleDocumentoComisionDTO(d.getQuantity().intValue(), d.getItemCode(), d.getWhsCode()));
                    }
                }

                List<Object[]> payments = facturaSAPFacade.obtenerPagosFactura(documento);

                if (payments != null && !payments.isEmpty()) {
                    //Recibo caja - Total recibo - Pago efectivo - Pago otros medios - Pagos notas credito - Pagos cheque - Pagos tarjetas - Pagos reconciliacion interna
                    for (Object[] o : payments) {
                        String[] s = new String[14];

                        s[0] = o[2] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[2]) : "";
                        s[1] = o[3] != null ? ((Integer) o[3]).toString() : "";
                        s[2] = o[4] != null ? ((BigDecimal) o[4]).toString() : "";
                        s[3] = o[5] != null ? ((BigDecimal) o[5]).toString() : "";
                        s[4] = o[12] != null ? ((BigDecimal) o[12]).toString() : "";
                        s[5] = o[13] != null ? ((BigDecimal) o[13]).toString() : "";
                        s[6] = o[7] != null ? ((BigDecimal) o[7]).toString() : "";
                        s[7] = o[8] != null ? ((BigDecimal) o[8]).toString() : "";
                        s[8] = o[16] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[16]) : "";
                        s[9] = o[14] != null ? ((BigDecimal) o[14]).toString() : "";
                        s[10] = o[9] != null ? ((BigDecimal) o[9]).toString() : "";
                        s[11] = o[11] != null ? new SimpleDateFormat("yyyy-MM-dd").format((Date) o[11]) : "";
                        s[12] = o[10] != null ? ((BigDecimal) o[10]).toString() : "";
                        s[13] = o[15] != null ? ((BigDecimal) o[15]).toString() : "";

                        pagos.add(s);
                    }
                }
            }
        }
    }

    public void deseleccionarDocumento() {
        pagos = new ArrayList<>();
        detalleDocumento = new ArrayList<>();
        documento = null;
        tipo = null;
        cliente = null;
        valorAntesIVA = null;
        valorDespuesIVA = null;
        docRelacionados = null;
    }

    public void seleccionarAsesor() throws ParseException {
        String codigoAsesor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoAsesor");

        aplicarAsesor(codigoAsesor);
    }

    private void aplicarAsesor(String codigoAsesor) throws ParseException {
        if (empleado != null && empleado.getCodigoAsesor() != null && !empleado.getCodigoAsesor().isEmpty() && codigoAsesor.equals(empleado.getCodigoAsesor())) {
            empleado = new EmpleadoDTO();
            asesor = null;

            paso = 1;
        } else {
            for (Map.Entry<String, List<EmpleadoDTO>> entry : empleadosVenta.entrySet()) {
                for (EmpleadoDTO e : entry.getValue()) {
                    if (e.getCodigoAsesor().equals(codigoAsesor)) {
                        empleado = e;

                        CONSOLE.log(Level.INFO, "Se selecciono el asesor con codigo de ventas [{0}]", codigoAsesor);
                        paso = 2;

                        SalesPerson sales = salesPersonFacade.find(Short.valueOf(e.getCodigoAsesor()));

                        if (sales != null && sales.getSlpCode() != null && sales.getSlpCode() != 0) {
                            asesor = sales.getSlpName();
                        }

                        comisiona = true;
                        reglasPorcentaje = new ArrayList<>();
                        validarComisionBD();
                        if (!comisionAprobada) {
                            obtenerDocumentos();
                        }
                        break;
                    }
                }
            }
        }
    }

    public void seleccionarYear() {
        year = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));

        CONSOLE.log(Level.INFO, "Se selecciono el year [{0}]", year);
        actualizarFechas();
    }

    public void seleccionarMonth() {
        month = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("month"));

        CONSOLE.log(Level.INFO, "Se selecciono el mes [{0}]", month);
        actualizarFechas();
    }

    private void actualizarFechas() {
        try {
            /*Se asignan las fechas*/
            GregorianCalendar cal = new GregorianCalendar();

            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);

            fechaInicio = new SimpleDateFormat("yyyy-MM-dd").parse((year) + "-" + (month) + "-" + (1));
            fechaFin = new SimpleDateFormat("yyyy-MM-dd").parse((year) + "-" + (month) + "-" + (cal.getActualMaximum(Calendar.DATE)));

            validarComisionBD();
            if (!comisionAprobada) {
                obtenerDocumentos();
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "", e);
        }
    }

    private void validarComisionBD() {
        aprobador = null;
        comisionAprobada = false;
        ComisionAsesor comision = comisionAsesorFacade.obtenerComisionAsesorPeriodo(asesor, fechaInicio, fechaFin);

        if (comision != null && comision.getIdComisionAsesor() != null && comision.getIdComisionAsesor() != 0) {
            aprobador = comision.getUsuarioAplica();
            comisionAprobada = true;
            acumuladoComplementos = new BigDecimal(Double.parseDouble(comision.getVentasComplementos().replace(",", ".")));
            acumuladoMobiliario = new BigDecimal(Double.parseDouble(comision.getVentasMobiliario().replace(",", ".")));
            porcentajeComplementos = Double.parseDouble(comision.getPorcentajeComisionComplementos().replace(",", "."));
            porcentajeMobiliario = Double.parseDouble(comision.getPorcentajeComisionMobiliario().replace(",", "."));

            cargarDatosBD(comision.getIdComisionAsesor());
        }
    }

    private void cargarDatosBD(Integer idComisionAsesor) {
        datosComision = new ArrayList<>();
        List<ComisionDetalleAsesor> detalle = comisionDetalleAsesorFacade.obtenerDetalleComisionAsesor(idComisionAsesor);

        if (detalle != null && !detalle.isEmpty()) {
            for (ComisionDetalleAsesor c : detalle) {
                ComisionDTO dto = new ComisionDTO();

                dto.setAplicar(c.getAplicar());
                dto.setComision(c.getComision());
                dto.setDocumento(c.getDocumento());
                dto.setDocumentoCerrado(c.getDocumentoCerrado());
                dto.setFecha(c.getFecha());
                dto.setIncluir(c.getIncluir());
                dto.setPorcentajeComision(c.getPorcentajeComision());
                dto.setTipo(c.getTipo());
                dto.setValor(c.getValor().doubleValue());
                dto.setValorDocumento(c.getValorDocumento().doubleValue());

                datosComision.add(dto);
            }
        }
    }

    public void aplicarComision() {
        if (datosComision != null && !datosComision.isEmpty()) {
            DecimalFormat format = new DecimalFormat("####.##");
            ComisionAsesor comisionAsesor = new ComisionAsesor();

            comisionAsesor.setAsesor(asesor);
            comisionAsesor.setCodigoAsesor(empleado.getCodigoAsesor());

            boolean complementos = validarEmpleadoComplementos();
            if (complementos && getPorcentajeComplementos() > 0 && comisiona && getObtenerTotalComision() > 0) {
                comisionAsesor.setComisiona(true);
            } else if (!complementos && comisiona && getObtenerTotalComision() > 0) {
                comisionAsesor.setComisiona(true);
            } else {
                comisionAsesor.setComisiona(false);
            }

            comisionAsesor.setFechaAplica(new Date());
            comisionAsesor.setFinPeriodo(fechaFin);
            comisionAsesor.setInicioPeriodo(fechaInicio);
            comisionAsesor.setPorcentajeComisionComplementos(format.format(porcentajeComplementos));
            comisionAsesor.setPorcentajeComisionMobiliario(format.format(porcentajeMobiliario));
            comisionAsesor.setTotalComision(format.format(getObtenerTotalComision()));
            comisionAsesor.setTotalDevuelto(format.format(getObtenerTotalDevuelto()));
            comisionAsesor.setTotalVendido(format.format(getObtenerTotalVendido()));
            comisionAsesor.setUsuarioAplica(sessionMBean.getCedulaEmpleado());
            comisionAsesor.setVentasComplementos(format.format(acumuladoComplementos));
            comisionAsesor.setVentasMobiliario(format.format(acumuladoMobiliario));

            try {
                comisionAsesorFacade.create(comisionAsesor);

                if (comisionAsesor.getIdComisionAsesor() != null && comisionAsesor.getIdComisionAsesor() != 0) {
                    for (ComisionDTO c : datosComision) {
                        ComisionDetalleAsesor det = new ComisionDetalleAsesor();

                        det.setAplicar(c.isIncluir());
                        det.setBaseComision(new BigDecimal(c.getValor()));
                        det.setDocumento(c.getDocumento());
                        det.setFecha(c.getFecha());
                        det.setIdComisionAsesor(comisionAsesor);
                        det.setPorcentajeComision(c.getPorcentajeComision());
                        det.setTipo(c.getTipo());
                        det.setValorDocumento(new BigDecimal(c.getValorDocumento()));
                        det.setValor(new BigDecimal(c.getValor()));
                        det.setComision(c.getComision());
                        det.setDocumentoCerrado(c.isDocumentoCerrado());
                        det.setIncluir(c.isIncluir());

                        try {
                            comisionDetalleAsesorFacade.createManual(det);
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al registrar el detalle de la comision. ", e);
                            return;
                        }
                    }

                    comisionAprobada = true;
                    aprobador = comisionAsesor.getUsuarioAplica();

                    obtenerMesesVencidos();
                    if (mesesSinComisionar >= 3) {
                        enviarNotificacion();
                    }
                } else {
                    mostrarMensaje("Error", "No se encontraron datos para guardar la comisión en base de datos.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se encontraron datos para guardar la comision en base de datos");
                    return;
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al registrar el encabezado de la comision. ", e);
            }
        }
    }

    public String abrirPdf() {
        if (empleado != null && empleado.getCodigoAsesor() != null && !empleado.getCodigoAsesor().isEmpty()) {
            String alias = new SimpleDateFormat("yyyy-MM").format(new Date()) + "[" + empleado.getCodigoAsesor() + "]";
            List<String[]> adjunto = generarDocumento(Integer.parseInt(empleado.getCodigoAsesor()), 1, "comisiones", null, alias, false, null, new SimpleDateFormat("yyyy-MM-dd").format(getFechaInicio()),
                    new SimpleDateFormat("yyyy-MM-dd").format(getFechaFin()));

            if (adjunto != null && !adjunto.isEmpty()) {
                if (new File(adjunto.get(0)[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.comisiones") + alias;
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "No se pudo generar la URL para el documento", e);
                        return "";
                    }
                } else {
                    CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            } else {
                CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                return "";
            }
        } else {
            CONSOLE.log(Level.SEVERE, "No encontro datos para generar documento");
            return "";
        }
    }

    private List<String[]> generarDocumento(Integer id, Integer copias, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados, String inicio, String fin) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);
        print.setInicio(inicio);
        print.setFin(fin);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            List<String[]> adjuntos = new ArrayList<>();

            for (String s : res.getMensaje().split("||")) {
                File f = new File(s);

                if (f.exists()) {
                    adjuntos.add(new String[]{s, f.getName()});
                }
            }

            return adjuntos;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    public void obtenerDocumentosPendientes() {
        documentosPendientes = new ArrayList<>();

        documentosPendientes = comisionDetalleAsesorFacade.obtenerDocumentosPendientes(asesor);

        if (documentosPendientes != null && !documentosPendientes.isEmpty()) {
            for (int i = 0; i < documentosPendientes.size(); i++) {
                for (ComisionDTO c : datosComision) {
                    if (c.getDocumento().equals((Integer) documentosPendientes.get(i)[0])) {
                        documentosPendientes.remove(i);
                        i--;
                        break;
                    }
                }
            }
        }
    }

    public void seleccionarDocumentoPendiente() {
        documento = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documento"));
        tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
    }

    public void agregarDocumento() throws ParseException {
        if (documento == null || documento == 0) {
            mostrarMensaje("Error", "Para agregar un documento, debe ingresar el número documento.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Para agregar un documento, debe ingresar el numero documento");
            return;
        }
        if (tipo == null || tipo.isEmpty()) {
            mostrarMensaje("Error", "Para agregar un documento, debe seleccionar el tipo de documento.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Para agregar un documento, debe seleccionar el tipo de documento");
            return;
        }
        if (comentario == null || comentario.isEmpty()) {
            mostrarMensaje("Error", "Para agregar un documento, debe agregar un comentario.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Para agregar un documento, debe agregar un comentario");
            return;
        }

        if (tipo.equals("FV")) {
            FacturaSAP factura = facturaSAPFacade.findByDocNum(documento);

            if (factura.getDocEntry() != null && factura.getDocEntry() != 0 && ((factura.getuVendedor1() != null && factura.getuVendedor1().equals(asesor))
                    || (factura.getuVendedor2() != null && factura.getuVendedor2().equals(asesor)) || (factura.getuVendedor3() != null && factura.getuVendedor3().equals(asesor))
                    || (factura.getuVendedor4() != null && factura.getuVendedor4().equals(asesor)) || (factura.getuVendedor5() != null && factura.getuVendedor5().equals(asesor)))) {
                List<Object[]> datos = facturaSAPFacade.validarDocumento("FV", documento.toString(), asesor);

                if (datos != null && !datos.isEmpty()) {
                    for (Object[] i : datos) {
                        Integer cantidad = (Integer) i[4];
                        Double precio = ((BigDecimal) i[9]).doubleValue();
                        String referencia = (String) i[3];

                        ComisionDTO comision = new ComisionDTO();

                        comision.setNuevo(true);
                        comision.setAplicar(true);
                        comision.setComision(new BigDecimal(0.0));
                        comision.setDocumento((Integer) i[1]);
                        comision.setFecha((Date) i[0]);
                        comision.setTipo((String) i[8]);
                        comision.setDocumentoCerrado(validarPagoDocumento(comision.getDocumento(), comision.getTipo()));
                        comision.setIncluir(true);
                        comision.setPorcentajeComision(((BigDecimal) i[6]).doubleValue());
                        comision.setReferencias(new ArrayList<ComisionItemDTO>());
                        comision.setValor(((BigDecimal) i[9]).doubleValue());
                        comision.setValorDocumento(((BigDecimal) i[7]).doubleValue());
                        comision.setComentario(comentario);

                        if (!documentoAplicado(comision.getDocumento()) && aplicarDiasGracia(comision.getDocumento(), comision.getTipo())) {
                            if (datosComision.isEmpty()) {
                                if (validarDocumento(comision.getDocumento(), comision.getTipo(), comision.getFecha())) {
                                    datosComision.add(0, comision);
                                    datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                    clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                } else if (validarPagoDocumento(comision.getDocumento(), comision.getTipo())) {
                                    datosComision.add(0, comision);
                                    datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                    clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                }
                            } else {
                                boolean existe = false;

                                for (ComisionDTO f : datosComision) {
                                    if (f.getDocumento().equals((Integer) i[1])) {
                                        f.setValor(f.getValor() + comision.getValor());
                                        datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                        clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);

                                        existe = true;
                                        break;
                                    } else {
                                        existe = false;
                                    }
                                }

                                if (!existe) {
                                    datosComision.add(0, comision);
                                    datosComision.get(0).getReferencias().add(new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                    clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                }
                            }
                        }
                    }

                    Collections.sort(datosComision, new Comparator<ComisionDTO>() {
                        @Override
                        public int compare(ComisionDTO o1, ComisionDTO o2) {
                            int posicion1 = o1.getFecha().compareTo(o2.getFecha());
                            if (posicion1 == 0) {
                                return o1.getDocumento().compareTo(o2.getDocumento());
                            } else {
                                return posicion1;
                            }
                        }
                    });
                    validarUsuarioComisiona();
                }
                obtenerComision();
            }
        } else if (tipo.equals("DV")) {
            DevolucionSAP devolucion = devolucionSAPFacade.obtenerDevolucion(documento);

            if (devolucion.getDocEntry() != null && devolucion.getDocEntry() != 0 && ((devolucion.getuVendedor1() != null && devolucion.getuVendedor1().equals(asesor))
                    || (devolucion.getuVendedor2() != null && devolucion.getuVendedor2().equals(asesor)) || (devolucion.getuVendedor3() != null && devolucion.getuVendedor3().equals(asesor))
                    || (devolucion.getuVendedor4() != null && devolucion.getuVendedor4().equals(asesor)) || (devolucion.getuVendedor5() != null && devolucion.getuVendedor5().equals(asesor)))) {
                List<Object[]> datos = facturaSAPFacade.validarDocumento("DV", documento.toString(), asesor);

                if (datos != null && !datos.isEmpty()) {
                    for (Object[] i : datos) {
                        Integer cantidad = (Integer) i[4];
                        Double precio = ((BigDecimal) i[9]).doubleValue();
                        String referencia = (String) i[3];

                        ComisionDTO comision = new ComisionDTO();

                        comision.setNuevo(true);
                        comision.setAplicar(true);
                        comision.setComision(new BigDecimal(0.0));
                        comision.setDocumento((Integer) i[1]);
                        comision.setFecha((Date) i[0]);
                        comision.setTipo((String) i[8]);
                        comision.setDocumentoCerrado(validarPagoDocumento(comision.getDocumento(), comision.getTipo()));
                        comision.setIncluir(true);
                        comision.setPorcentajeComision(((BigDecimal) i[6]).doubleValue());
                        comision.setReferencias(new ArrayList<ComisionItemDTO>());
                        comision.setValor(((BigDecimal) i[9]).doubleValue());
                        comision.setValorDocumento(((BigDecimal) i[7]).doubleValue());
                        comision.setComentario(comentario);

                        if (!documentoAplicado(comision.getDocumento()) && aplicarDiasGracia(comision.getDocumento(), comision.getTipo())) {
                            if (datosComision.isEmpty()) {
                                if (comision.getTipo().equals("DV")) {
                                    if (validarDevolucion(comision.getDocumento(), null)) {
                                        if (validarSiFVComisionada(comision.getDocumento())) {
                                            comision.setAplicarDV(true);
                                        } else {
                                            comision.setAplicarDV(false);
                                            comision.setIncluir(false);
                                        }
                                        datosComision.add(0, comision);
                                        datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                        clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                    }
                                }
                            } else {
                                boolean existe = false;

                                for (ComisionDTO f : datosComision) {
                                    if (f.getDocumento().equals((Integer) i[1])) {
                                        f.setValor(f.getValor() + comision.getValor());
                                        datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                        clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);

                                        existe = true;
                                        break;
                                    } else {
                                        existe = false;
                                    }
                                }

                                if (!existe) {
                                    if (comision.getTipo().equals("DV")) {
                                        if (validarDevolucion(comision.getDocumento(), null)) {
                                            if (validarSiFVComisionada(comision.getDocumento())) {
                                                comision.setAplicarDV(true);
                                            } else {
                                                comision.setAplicarDV(false);
                                                comision.setIncluir(false);
                                            }
                                            datosComision.add(0, comision);
                                            datosComision.get(0).getReferencias().add(0, new ComisionItemDTO(cantidad, precio, comision.getPorcentajeComision(), referencia));
                                            clasificarItem(precio, referencia, comision.getTipo(), comision.isIncluir(), false);
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Collections.sort(datosComision, new Comparator<ComisionDTO>() {
                        @Override
                        public int compare(ComisionDTO o1, ComisionDTO o2) {
                            int posicion1 = o1.getFecha().compareTo(o2.getFecha());
                            if (posicion1 == 0) {
                                return o1.getDocumento().compareTo(o2.getDocumento());
                            } else {
                                return posicion1;
                            }
                        }
                    });
                    validarUsuarioComisiona();
                }
                obtenerComision();
            }
        }

        documento = null;
        tipo = null;
        comentario = null;
    }

    private void enviarNotificacion() {
        SendHtmlEmailClient client = new SendHtmlEmailClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        Map<String, String> params = new HashMap<>();
        params.put("asesor", asesor);

        client.enviarHtmlEmail("Comisiones ", "Comisiones", applicationMBean.getDestinatariosPlantillaEmail().get("comisiones").getTo().get(0), "comisiones", null, params);
    }

    public void borrarComision() {
        ComisionAsesor comision = comisionAsesorFacade.obtenerComisionAsesorPeriodo(asesor, fechaInicio, fechaFin);

        try {
            comisionDetalleAsesorFacade.eliminarDetalleComision(comision.getIdComisionAsesor());

            comisionAsesorFacade.remove(comision);
            CONSOLE.log(Level.INFO, "Se borro la comision del asesor {0}, borrada por {1}", new Object[]{comision.getAsesor(), sessionMBean.getUsuario()});
            validarComisionBD();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Error al borrar la comision. ", e);
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
