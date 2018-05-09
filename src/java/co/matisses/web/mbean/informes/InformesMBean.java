package co.matisses.web.mbean.informes;

import co.matisses.persistence.dwb.entity.CostoAcumuladoAlmacen;
import co.matisses.persistence.dwb.facade.CostoAcumuladoAlmacenFacade;
import co.matisses.persistence.sap.entity.DevolucionSAP;
import co.matisses.persistence.sap.entity.FacturaSAP;
import co.matisses.persistence.sap.facade.DevolucionSAPFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.web.dto.CostoAcumuladoAlmacenDTO;
import co.matisses.web.dto.DatosVentasDTO;
import co.matisses.web.dto.DetalleDatosVentasDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.poi.ExcelGeneratorDatosVentas;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "informesMBean")
public class InformesMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(InformesMBean.class.getSimpleName());
    private Integer startYear;
    private Integer startMonth;
    private Integer startDay;
    private Integer endYear;
    private Integer endMonth;
    private Integer endDay;
    private Integer startYearRoutes;
    private Integer startMonthRoutes;
    private Integer startDayRoutes;
    private Integer endYearRoutes;
    private Integer endMonthRoutes;
    private Integer endDayRoutes;
    private Integer startYearTaller;
    private Integer startMonthTaller;
    private Integer startDayTaller;
    private Integer endYearTaller;
    private Integer endMonthTaller;
    private Integer endDayTaller;
    private Integer startYearJuan;
    private Integer startMonthJuan;
    private Integer startDayJuan;
    private Integer endYearJuan;
    private Integer endMonthJuan;
    private Integer endDayJuan;
    private Integer paso = 1;
    private Integer proceso = 1;
    private Integer semana;
    private String comentario;
    private String tipo;
    private String documento;
    private String tienda;
    private String mes;
    private boolean dlgComentario = false;
    private boolean juanCamilo = false;
    private Date startDate;
    private Date endDate;
    private Date startDateRoutes;
    private Date endDateRoutes;
    private Date startDateTaller;
    private Date endDateTaller;
    private Date startDateJuan;
    private Date endDateJuan;
    private List<Integer> years;
    private List<Integer> startDays;
    private List<Integer> endDays;
    private List<Integer> startDaysRoutes;
    private List<Integer> endDaysRoutes;
    private List<Integer> startDaysTaller;
    private List<Integer> endDaysTaller;
    private List<Integer> startDaysJuan;
    private List<Integer> endDaysJuan;
    private List<Integer> days;
    private List<Integer> semanas;
    private List<String> rutas;
    private List<String> fechasRutas;
    private List<String[]> months;
    private List<String[]> tiendas;
    private List<Object[]> ventas;
    private List<Object[]> ventasComparativas;
    private List<Object[]> ventasRutas;
    private List<Object[]> movimientosDiarios;
    private List<Object[]> ventasJuan;
    private List<DatosVentasDTO> datos;
    private List<CostoAcumuladoAlmacenDTO> costoTaller;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private DevolucionSAPFacade devolucionSAPFacade;
    @EJB
    private CostoAcumuladoAlmacenFacade costoAcumuladoAlmacenFacade;

    public InformesMBean() {
        years = new ArrayList<>();
        months = new ArrayList<>();
        tiendas = new ArrayList<>();
        ventas = new ArrayList<>();
        ventasComparativas = new ArrayList<>();
        ventasRutas = new ArrayList<>();
        datos = new ArrayList<>();
        ventasJuan = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        juanCamilo = false;

        cargarYears();
        cargarMonths();
        inicializarFechas(true);
        inicializarFechas(false);
        mostrarVentas();
        obtenerTiendas();
        cargarRutas();
        mostrarVentasRutas();
        obtenerSaldoTaller();

        String token = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("token");

        if (token != null && token.toLowerCase().equals("juan")) {
            juanCamilo = true;
            mostrarVentasJuan();
        }
    }

    public Integer getStartYear() {
        return startYear;
    }

    public void setStartYear(Integer startYear) {
        this.startYear = startYear;
    }

    public Integer getStartMonth() {
        return startMonth;
    }

    public void setStartMonth(Integer startMonth) {
        this.startMonth = startMonth;
    }

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndYear() {
        return endYear;
    }

    public void setEndYear(Integer endYear) {
        this.endYear = endYear;
    }

    public Integer getEndMonth() {
        return endMonth;
    }

    public void setEndMonth(Integer endMonth) {
        this.endMonth = endMonth;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getStartYearRoutes() {
        return startYearRoutes;
    }

    public void setStartYearRoutes(Integer startYearRoutes) {
        this.startYearRoutes = startYearRoutes;
    }

    public Integer getStartMonthRoutes() {
        return startMonthRoutes;
    }

    public void setStartMonthRoutes(Integer startMonthRoutes) {
        this.startMonthRoutes = startMonthRoutes;
    }

    public Integer getStartDayRoutes() {
        return startDayRoutes;
    }

    public void setStartDayRoutes(Integer startDayRoutes) {
        this.startDayRoutes = startDayRoutes;
    }

    public Integer getEndYearRoutes() {
        return endYearRoutes;
    }

    public void setEndYearRoutes(Integer endYearRoutes) {
        this.endYearRoutes = endYearRoutes;
    }

    public Integer getEndMonthRoutes() {
        return endMonthRoutes;
    }

    public void setEndMonthRoutes(Integer endMonthRoutes) {
        this.endMonthRoutes = endMonthRoutes;
    }

    public Integer getEndDayRoutes() {
        return endDayRoutes;
    }

    public void setEndDayRoutes(Integer endDayRoutes) {
        this.endDayRoutes = endDayRoutes;
    }

    public Integer getStartYearTaller() {
        return startYearTaller;
    }

    public void setStartYearTaller(Integer startYearTaller) {
        this.startYearTaller = startYearTaller;
    }

    public Integer getStartMonthTaller() {
        return startMonthTaller;
    }

    public void setStartMonthTaller(Integer startMonthTaller) {
        this.startMonthTaller = startMonthTaller;
    }

    public Integer getStartDayTaller() {
        return startDayTaller;
    }

    public void setStartDayTaller(Integer startDayTaller) {
        this.startDayTaller = startDayTaller;
    }

    public Integer getEndYearTaller() {
        return endYearTaller;
    }

    public void setEndYearTaller(Integer endYearTaller) {
        this.endYearTaller = endYearTaller;
    }

    public Integer getEndMonthTaller() {
        return endMonthTaller;
    }

    public void setEndMonthTaller(Integer endMonthTaller) {
        this.endMonthTaller = endMonthTaller;
    }

    public Integer getEndDayTaller() {
        return endDayTaller;
    }

    public void setEndDayTaller(Integer endDayTaller) {
        this.endDayTaller = endDayTaller;
    }

    public Integer getStartYearJuan() {
        return startYearJuan;
    }

    public void setStartYearJuan(Integer startYearJuan) {
        this.startYearJuan = startYearJuan;
    }

    public Integer getStartMonthJuan() {
        return startMonthJuan;
    }

    public void setStartMonthJuan(Integer startMonthJuan) {
        this.startMonthJuan = startMonthJuan;
    }

    public Integer getStartDayJuan() {
        return startDayJuan;
    }

    public void setStartDayJuan(Integer startDayJuan) {
        this.startDayJuan = startDayJuan;
    }

    public Integer getEndYearJuan() {
        return endYearJuan;
    }

    public void setEndYearJuan(Integer endYearJuan) {
        this.endYearJuan = endYearJuan;
    }

    public Integer getEndMonthJuan() {
        return endMonthJuan;
    }

    public void setEndMonthJuan(Integer endMonthJuan) {
        this.endMonthJuan = endMonthJuan;
    }

    public Integer getEndDayJuan() {
        return endDayJuan;
    }

    public void setEndDayJuan(Integer endDayJuan) {
        this.endDayJuan = endDayJuan;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getTienda() {
        return tienda;
    }

    public void setTienda(String tienda) {
        this.tienda = tienda;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    public Integer getProceso() {
        return proceso;
    }

    public void setProceso(Integer proceso) {
        this.proceso = proceso;
    }

    public Integer getSemana() {
        return semana;
    }

    public void setSemana(Integer semana) {
        this.semana = semana;
    }

    public boolean isDlgComentario() {
        return dlgComentario;
    }

    public void setDlgComentario(boolean dlgComentario) {
        this.dlgComentario = dlgComentario;
    }

    public boolean isJuanCamilo() {
        return juanCamilo;
    }

    public void setJuanCamilo(boolean juanCamilo) {
        this.juanCamilo = juanCamilo;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDateRoutes() {
        return startDateRoutes;
    }

    public void setStartDateRoutes(Date startDateRoutes) {
        this.startDateRoutes = startDateRoutes;
    }

    public Date getEndDateRoutes() {
        return endDateRoutes;
    }

    public void setEndDateRoutes(Date endDateRoutes) {
        this.endDateRoutes = endDateRoutes;
    }

    public Date getStartDateTaller() {
        return startDateTaller;
    }

    public void setStartDateTaller(Date startDateTaller) {
        this.startDateTaller = startDateTaller;
    }

    public Date getEndDateTaller() {
        return endDateTaller;
    }

    public void setEndDateTaller(Date endDateTaller) {
        this.endDateTaller = endDateTaller;
    }

    public Date getStartDateJuan() {
        return startDateJuan;
    }

    public void setStartDateJuan(Date startDateJuan) {
        this.startDateJuan = startDateJuan;
    }

    public Date getEndDateJuan() {
        return endDateJuan;
    }

    public void setEndDateJuan(Date endDateJuan) {
        this.endDateJuan = endDateJuan;
    }

    public List<Integer> getYears() {
        return years;
    }

    public void setYears(List<Integer> years) {
        this.years = years;
    }

    public List<Integer> getStartDays() {
        return startDays;
    }

    public void setStartDays(List<Integer> startDays) {
        this.startDays = startDays;
    }

    public List<Integer> getEndDays() {
        return endDays;
    }

    public void setEndDays(List<Integer> endDays) {
        this.endDays = endDays;
    }

    public List<Integer> getStartDaysRoutes() {
        return startDaysRoutes;
    }

    public void setStartDaysRoutes(List<Integer> startDaysRoutes) {
        this.startDaysRoutes = startDaysRoutes;
    }

    public List<Integer> getEndDaysRoutes() {
        return endDaysRoutes;
    }

    public void setEndDaysRoutes(List<Integer> endDaysRoutes) {
        this.endDaysRoutes = endDaysRoutes;
    }

    public List<Integer> getStartDaysTaller() {
        return startDaysTaller;
    }

    public void setStartDaysTaller(List<Integer> startDaysTaller) {
        this.startDaysTaller = startDaysTaller;
    }

    public List<Integer> getEndDaysTaller() {
        return endDaysTaller;
    }

    public void setEndDaysTaller(List<Integer> endDaysTaller) {
        this.endDaysTaller = endDaysTaller;
    }

    public List<Integer> getStartDaysJuan() {
        return startDaysJuan;
    }

    public void setStartDaysJuan(List<Integer> startDaysJuan) {
        this.startDaysJuan = startDaysJuan;
    }

    public List<Integer> getEndDaysJuan() {
        return endDaysJuan;
    }

    public void setEndDaysJuan(List<Integer> endDaysJuan) {
        this.endDaysJuan = endDaysJuan;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<Integer> getSemanas() {
        return semanas;
    }

    public void setSemanas(List<Integer> semanas) {
        this.semanas = semanas;
    }

    public List<String> getRutas() {
        return rutas;
    }

    public void setRutas(List<String> rutas) {
        this.rutas = rutas;
    }

    public List<String> getFechasRutas() {
        return fechasRutas;
    }

    public void setFechasRutas(List<String> fechasRutas) {
        this.fechasRutas = fechasRutas;
    }

    public List<String[]> getMonths() {
        return months;
    }

    public void setMonths(List<String[]> months) {
        this.months = months;
    }

    public List<String[]> getTiendas() {
        return tiendas;
    }

    public void setTiendas(List<String[]> tiendas) {
        this.tiendas = tiendas;
    }

    public List<Object[]> getVentas() {
        return ventas;
    }

    public void setVentas(List<Object[]> ventas) {
        this.ventas = ventas;
    }

    public List<Object[]> getMovimientosDiarios() {
        return movimientosDiarios;
    }

    public void setMovimientosDiarios(List<Object[]> movimientosDiarios) {
        this.movimientosDiarios = movimientosDiarios;
    }

    public List<Object[]> getVentasJuan() {
        return ventasJuan;
    }

    public void setVentasJuan(List<Object[]> ventasJuan) {
        this.ventasJuan = ventasJuan;
    }

    public List<DatosVentasDTO> getDatos() {
        return datos;
    }

    public void setDatos(List<DatosVentasDTO> datos) {
        this.datos = datos;
    }

    public List<CostoAcumuladoAlmacenDTO> getCostoTaller() {
        return costoTaller;
    }

    public void setCostoTaller(List<CostoAcumuladoAlmacenDTO> costoTaller) {
        this.costoTaller = costoTaller;
    }

    public Long getTotalVentas() {
        Long total = 0L;

        for (Object[] o : ventas) {
            total += ((BigDecimal) o[1]).longValue();
        }

        return total;
    }

    public Long getTotalVentasJuan() {
        Long total = 0L;

        for (Object[] o : ventasJuan) {
            total += ((BigDecimal) o[1]).longValue();
        }

        return total;
    }

    public Long getTotalMes(Integer year, String month) {
        if (year != null && year != 0 && month != null && !month.isEmpty()) {
            for (Object[] o : ventasComparativas) {
                if (((Integer) o[0]).equals(year) && ((String) o[1]).equals(month)) {
                    return ((BigDecimal) o[2]).longValue();
                }
            }
        }

        return 0L;
    }

    public Long getTotalDia(Integer year, String day) {
        if (year != null && year != 0 && day != null && !day.isEmpty() && mes != null && !mes.isEmpty()) {
            for (Object[] o : ventasComparativas) {
                if (((Integer) o[0]).equals(year) && ((String) o[1]).equals(mes) && ((String) o[2]).equals(day)) {
                    return ((BigDecimal) o[3]).longValue();
                }
            }
        }

        return 0L;
    }

    public Long getTotalSemana(Integer year, Integer semana) {
        if (year != null && year != 0 && semana != null && semana != 0) {
            for (Object[] o : ventasComparativas) {
                if (((Integer) o[0]).equals(year) && ((Integer) o[1]).equals(semana)) {
                    return ((BigDecimal) o[2]).longValue();
                }
            }
        }

        return 0L;
    }

    public Long getTotalRuta(String fecha, String ruta) {
        if (fecha != null && !fecha.isEmpty() && ruta != null && !ruta.isEmpty()) {
            for (Object[] o : ventasRutas) {
                if (((String) o[0]).equals(fecha) && ((String) o[1]).equals(ruta.toUpperCase())) {
                    return ((BigDecimal) o[2]).longValue();
                }
            }
        }

        return 0L;
    }

    public String getObtenerNombreTienda(String codTienda) {
        for (String[] t : tiendas) {
            if (t[0].equals(codTienda)) {
                return t[1];
            }
        }
        return "Otro(" + codTienda + ")";
    }

    public String getObtenerNombreMes() {
        for (String[] s : months) {
            if (s[0].equals(mes)) {
                return s[1];
            }
        }
        return "";
    }

    public void cambiarProceso() {
        proceso = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("proceso"));
        paso = 1;
    }

    private void inicializarFechas(boolean inicio) {
        try {
            GregorianCalendar cal = new GregorianCalendar();

            if (inicio) {
                startYear = cal.get(Calendar.YEAR);
                startMonth = cal.get(Calendar.MONTH) + 1;
                cargarDays(inicio);
                startDay = cal.getActualMinimum(Calendar.DATE);
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startYear + "-" + startMonth + "-" + startDay);

                startYearRoutes = cal.get(Calendar.YEAR);
                startMonthRoutes = cal.get(Calendar.MONTH) + 1;
                cargarDaysRoutes(inicio);
                startDayRoutes = cal.getActualMinimum(Calendar.DATE);
                startDateRoutes = new SimpleDateFormat("yyyy-MM-dd").parse(startYearRoutes + "-" + startMonthRoutes + "-" + startDayRoutes);

                startYearTaller = cal.get(Calendar.YEAR);
                startMonthTaller = cal.get(Calendar.MONTH) + 1;
                cargarDaysTaller(inicio);
                startDayTaller = cal.getActualMinimum(Calendar.DATE);
                startDateTaller = new SimpleDateFormat("yyyy-MM-dd").parse(startYearTaller + "-" + startMonthTaller + "-" + startDayTaller);

                startYearJuan = cal.get(Calendar.YEAR);
                startMonthJuan = cal.get(Calendar.MONTH) + 1;
                cargarDaysJuan(inicio);
                startDayJuan = cal.getActualMinimum(Calendar.DATE);
                startDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(startYearJuan + "-" + startMonthJuan + "-" + startDayJuan);
            } else {
                endYear = cal.get(Calendar.YEAR);
                endMonth = cal.get(Calendar.MONTH) + 1;
                cargarDays(inicio);
                endDay = cal.get(Calendar.DATE);
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endYear + "-" + endMonth + "-" + endDay);

                endYearRoutes = cal.get(Calendar.YEAR);
                endMonthRoutes = cal.get(Calendar.MONTH) + 1;
                cargarDaysRoutes(inicio);
                endDayRoutes = cal.get(Calendar.DATE);
                endDateRoutes = new SimpleDateFormat("yyyy-MM-dd").parse(endYearRoutes + "-" + endMonthRoutes + "-" + endDayRoutes);

                endYearTaller = cal.get(Calendar.YEAR);
                endMonthTaller = cal.get(Calendar.MONTH) + 1;
                cargarDaysTaller(inicio);
                endDayTaller = cal.get(Calendar.DATE);
                endDateTaller = new SimpleDateFormat("yyyy-MM-dd").parse(endYearTaller + "-" + endMonthTaller + "-" + endDayTaller);

                endYearJuan = cal.get(Calendar.YEAR);
                endMonthJuan = cal.get(Calendar.MONTH) + 1;
                cargarDaysJuan(inicio);
                endDayJuan = cal.get(Calendar.DATE);
                endDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(endYearJuan + "-" + endMonthJuan + "-" + endDayJuan);
            }
        } catch (Exception e) {
        }
    }

    public void mostrarHoy() {
        try {
            GregorianCalendar cal = new GregorianCalendar();

            startYear = cal.get(Calendar.YEAR);
            endYear = cal.get(Calendar.YEAR);
            startMonth = cal.get(Calendar.MONTH) + 1;
            endMonth = cal.get(Calendar.MONTH) + 1;
            cargarDays(true);
            startDay = cal.get(Calendar.DATE);
            endDay = cal.get(Calendar.DATE);
            startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startYear + "-" + startMonth + "-" + startDay);
            endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endYear + "-" + endMonth + "-" + endDay);

            mostrarVentas();
        } catch (Exception e) {
        }
    }

    public void mostrarMes() {
        inicializarFechas(true);
        inicializarFechas(false);
        mostrarVentas();
    }

    public void mostrarVentasDesktop() {
        ventas = new ArrayList<>();

        if (startDate != null && endDate != null) {
            startYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(startDate));
            startMonth = Integer.parseInt(new SimpleDateFormat("MM").format(startDate));
            startDay = Integer.parseInt(new SimpleDateFormat("dd").format(startDate));

            endYear = Integer.parseInt(new SimpleDateFormat("yyyy").format(endDate));
            endMonth = Integer.parseInt(new SimpleDateFormat("MM").format(endDate));
            endDay = Integer.parseInt(new SimpleDateFormat("dd").format(endDate));
            mostrarVentas();
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas con las fechas ingresadas");
            return;
        }
    }

    public void mostrarVentasMobile() {
        ventas = new ArrayList<>();

        if (startYear != null && startYear != 0 && startMonth != null && startMonth != 0 && startDay != null && startDay != 0
                && endYear != null && endYear != 0 && endMonth != null && endMonth != 0 && endDay != null && endDay != 0) {
            try {
                startDate = new SimpleDateFormat("yyyy-MM-dd").parse(startYear + "-" + startMonth + "-" + startDay);
                endDate = new SimpleDateFormat("yyyy-MM-dd").parse(endYear + "-" + endMonth + "-" + endDay);
                mostrarVentas();
            } catch (Exception e) {
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas con las fechas ingresadas");
            return;
        }
    }

    public void mostrarVentas() {
        ventas = facturaSAPFacade.obtenerVentasNetas(startYear, startMonth, startDay, endYear, endMonth, endDay);
    }

    private void cargarYears() {
        years = new ArrayList<>();

        Date fecha = new Date();
        DateFormat format = new SimpleDateFormat("yyyy");
        String fechas = format.format(fecha);
        int inicio = Integer.parseInt(fechas);
        int fin = 2007;

        do {
            years.add(inicio);
            inicio--;
        } while (inicio >= fin);
    }

    private void cargarMonths() {
        months = new ArrayList<>();

        months.add(new String[]{"1", "Enero"});
        months.add(new String[]{"2", "Febrero"});
        months.add(new String[]{"3", "Marzo"});
        months.add(new String[]{"4", "Abril"});
        months.add(new String[]{"5", "Mayo"});
        months.add(new String[]{"6", "Junio"});
        months.add(new String[]{"7", "Julio"});
        months.add(new String[]{"8", "Agostos"});
        months.add(new String[]{"9", "Septiembre"});
        months.add(new String[]{"10", "Octubre"});
        months.add(new String[]{"11", "Noviembre"});
        months.add(new String[]{"12", "Diciembre"});
    }

    public void cargarDays(boolean inicio) {
        GregorianCalendar cal = new GregorianCalendar();

        if (inicio) {
            startDays = new ArrayList<>();

            cal.set(Calendar.YEAR, startYear);
            cal.set(Calendar.MONTH, startMonth - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                startDays.add(j);
            }
        } else {
            endDays = new ArrayList<>();

            cal.set(Calendar.YEAR, endYear);
            cal.set(Calendar.MONTH, endMonth - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                endDays.add(j);
            }
        }
    }

    public void obtenerVentas() {
        paso = 1;
        dlgComentario = false;
        mostrarVentas();
    }

    public void obtenerTiendas() {
        tiendas = new ArrayList<>();
        String props = applicationMBean.obtenerValorPropiedad("consecutivoAlmacen");

        for (String prop : props.split(";")) {
            String valores[] = prop.split(",");
            tiendas.add(new String[]{valores[0], valores[1]});
        }

        Collections.sort(tiendas, new Comparator<String[]>() {
            @Override
            public int compare(String[] o1, String[] o2) {
                return o1[1].compareTo(o2[1]);
            }
        });
    }

    public void obtenerDatos() {
        datos = new ArrayList<>();
        paso = 2;

        if (startYear != null && startYear != 0 && startMonth != null && startMonth != 0 && startDay != null && startDay != 0
                && endYear != null && endYear != 0 && endMonth != null && endMonth != 0 && endDay != null && endDay != 0) {
            List<Object[]> datas = facturaSAPFacade.obtenerDatosVentas(startYear, startMonth, startDay, endYear, endMonth, endDay);

            if (datas != null && !datas.isEmpty()) {
                for (Object[] o : datas) {
                    boolean existe = false;

                    DetalleDatosVentasDTO detalle = new DetalleDatosVentasDTO(((Integer) o[3]).longValue(), (String) o[1], (String) o[2], (Date) o[4]);

                    List<DetalleDatosVentasDTO> det = new ArrayList<>();
                    det.add(detalle);

                    if (datos == null && !datos.isEmpty()) {
                        existe = false;
                    } else {
                        for (DatosVentasDTO d : datos) {
                            if (d.getAlmacen().contains((String) o[0])) {
                                d.getVentas().add(detalle);
                                d.setTotal(d.getTotal() + ((Integer) o[3]).longValue());
                                existe = true;
                                break;
                            } else {
                                existe = false;
                            }
                        }
                    }

                    if (!existe) {
                        datos.add(new DatosVentasDTO(((Integer) o[3]).longValue(), (String) o[0], det));
                    }
                }
            }
        }
    }

    public void obtenerDocumento() {
        tipo = null;
        documento = null;
        comentario = null;

        tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");
        documento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("documento");

        if (documento != null && !documento.isEmpty() && tipo != null && !tipo.isEmpty()) {
            if (tipo.equals("FV")) {

                FacturaSAP f = facturaSAPFacade.findByDocNum(Integer.parseInt(documento));
                if (f != null && f.getDocEntry() != null && f.getDocEntry() != 0) {
                    comentario = f.getComments();
                    dlgComentario = true;
                    if (comentario.length() == 0) {
                        mostrarMensaje("Error", "No se encontró comentario asociado al documento seleccionado.", true, false, false);
                        CONSOLE.log(Level.SEVERE, "No se encontro comentario asociado al documento seleccionado");
                        return;
                    }
                } else {
                    mostrarMensaje("Error", "No se encontró comentario asociado al documento seleccionado.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se encontro comentario asociado al documento seleccionado");
                    dlgComentario = true;
                    return;
                }
            } else if (tipo.equals("DV")) {
                DevolucionSAP dv = devolucionSAPFacade.obtenerDevolucionFactura(Integer.parseInt(documento));

                if (dv != null && dv.getDocEntry() != null && dv.getDocEntry() != 0) {
                    comentario = dv.getComments();
                    dlgComentario = true;

                    if (comentario.length() == 0) {
                        mostrarMensaje("Error", "No se encontró comentario asociado al documento seleccionado.", true, false, false);
                        CONSOLE.log(Level.SEVERE, "No se encontro comentario asociado al documento seleccionado");
                        return;
                    }
                } else {
                    mostrarMensaje("Error", "No se encontró comentario asociado al documento seleccionado.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "No se encontro comentario asociado al documento seleccionado");
                    dlgComentario = false;
                    return;
                }
            }
        }
    }

    public StreamedContent getFile() {
        CONSOLE.log(Level.INFO, "Descargando informe de ventas");
        ExcelGeneratorDatosVentas excel = new ExcelGeneratorDatosVentas(applicationMBean, datos);

        try {
            excel.generarInfome();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al descargar el informe de ventas. ", e);
            return null;
        }
        try {
            InputStream stream = new ByteArrayInputStream(fileToBytes(new File(System.getProperty("jboss.server.temp.dir") + File.separator + "Ventas.xlsx"), "Ventas.xlsx"));
            return new DefaultStreamedContent(stream, "application/vnd.ms-excel", "Ventas.xlsx");
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al descargar el informe de ventas. ", e);
            return null;
        }
    }

    private byte[] fileToBytes(File file, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) file.length()];

        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            CONSOLE.log(Level.SEVERE, ex.getMessage());
        }

        byte[] bytes = bos.toByteArray();
        File someFile = new File(nombreArchivo);

        try (FileOutputStream fos = new FileOutputStream(someFile)) {
            fos.write(bytes);
            fos.flush();
            fos.close();
        }
        return buf;
    }

    /**
     * *********************************************************
     * COMPARATIVOS DE VENTAS
     * *********************************************************
     */
    public void seleccionarOpcion(boolean avanzar) {
        if (avanzar) {
            if (paso == 1) {
                tienda = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tienda");
                paso = 3;
            } else {
                paso++;
            }
        } else if (paso == 3) {
            paso = 1;
        } else {
            paso--;
        }

        if (null != paso) {
            switch (paso) {
                case 3:
                    cargarComparativoMensual();
                    break;
                case 4:
                    mes = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("mes");
                    if (mes == null) {
                        mes = "1";
                    }
                    cargarComparativoDiaria();
                    break;
                case 5:
                    semanas = new ArrayList<>();
                    cargarComparativoSemanal();
                    for (int i = 1; i <= 52; i++) {
                        semanas.add(i);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void seleccionarTienda() {
        if (null != paso) {
            switch (paso) {
                case 3:
                    cargarComparativoMensual();
                    break;
                case 4:
                    cargarComparativoDiaria();
                    break;
                case 5:
                    cargarComparativoSemanal();
                    break;
                default:
                    break;
            }
        }
    }

    public void cargarComparativoMensual() {
        ventasComparativas = facturaSAPFacade.obtenerVentasMensuales(tienda);
    }

    public void cargarComparativoDiaria() {
        ventasComparativas = facturaSAPFacade.obtenerVentasDiarias(mes, tienda);

        /*Cargar días para el mes*/
        days = new ArrayList<>();

        if (!mes.equals("2")) {
            GregorianCalendar cal = new GregorianCalendar();

            try {
                cal.get(Calendar.YEAR);
                cal.set(Calendar.MONTH, Integer.parseInt(mes) - 1);

                int i = cal.getActualMaximum(Calendar.DATE);

                for (int j = 1; j <= i; j++) {
                    days.add(j);
                }
            } catch (Exception e) {
            }
        } else {
            for (int i = 1; i <= 29; i++) {
                days.add(i);
            }
        }
    }

    public void obtenerSiguienteMes() {
        Integer tmp = Integer.parseInt(mes);

        if (tmp == 12) {
            mes = "1";
        } else {
            tmp++;
            mes = (tmp).toString();
        }
        cargarComparativoDiaria();
    }

    public void obtenerAnteriorMes() {
        Integer tmp = Integer.parseInt(mes);

        if (tmp == 1) {
            mes = "12";
        } else {
            tmp--;
            mes = (tmp).toString();
        }
        cargarComparativoDiaria();
    }

    public void cargarComparativoSemanal() {
        ventasComparativas = facturaSAPFacade.obtenerVentasSemanales(semana, tienda);
    }

    /**
     * *********************************************************
     * INFORME DE VENTAS JUAN CAMILO
     * *********************************************************
     */
    public void cargarDaysJuan(boolean inicio) {
        GregorianCalendar cal = new GregorianCalendar();

        if (inicio) {
            startDaysJuan = new ArrayList<>();

            cal.set(Calendar.YEAR, startYearJuan);
            cal.set(Calendar.MONTH, startMonthJuan - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                startDaysJuan.add(j);
            }
        } else {
            endDaysJuan = new ArrayList<>();

            cal.set(Calendar.YEAR, endYearJuan);
            cal.set(Calendar.MONTH, endMonthJuan - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                endDaysJuan.add(j);
            }
        }
    }

    public void mostrarVentasJuan() {
        ventasJuan = facturaSAPFacade.obtenerVentasNetasJuanCamilo(startYearJuan, startMonthJuan, startDayJuan, endYearJuan, endMonthJuan, endDayJuan);
    }

    public void mostrarHoyJuan() {
        try {
            GregorianCalendar cal = new GregorianCalendar();

            startYearJuan = cal.get(Calendar.YEAR);
            endYearJuan = cal.get(Calendar.YEAR);
            startMonthJuan = cal.get(Calendar.MONTH) + 1;
            endMonthJuan = cal.get(Calendar.MONTH) + 1;
            cargarDaysJuan(true);
            startDayJuan = cal.get(Calendar.DATE);
            endDayJuan = cal.get(Calendar.DATE);
            startDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(startYearJuan + "-" + startMonthJuan + "-" + startDayJuan);
            endDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(endYearJuan + "-" + endMonthJuan + "-" + endDayJuan);

            mostrarVentasJuan();
        } catch (Exception e) {
        }
    }

    public void mostrarMesJuan() {
        inicializarFechas(true);
        inicializarFechas(false);
        mostrarVentasJuan();
    }

    public void mostrarVentasJuanDesktop() {
        ventasJuan = new ArrayList<>();

        if (startDateJuan != null && endDateJuan != null) {
            startYearJuan = Integer.parseInt(new SimpleDateFormat("yyyy").format(startDateJuan));
            startMonthJuan = Integer.parseInt(new SimpleDateFormat("MM").format(startDateJuan));
            startDayJuan = Integer.parseInt(new SimpleDateFormat("dd").format(startDateJuan));

            endYearJuan = Integer.parseInt(new SimpleDateFormat("yyyy").format(endDateJuan));
            endMonthJuan = Integer.parseInt(new SimpleDateFormat("MM").format(endDateJuan));
            endDayJuan = Integer.parseInt(new SimpleDateFormat("dd").format(endDateJuan));
            mostrarVentasJuan();
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas para Juan con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas para Juan con las fechas ingresadas");
            return;
        }
    }

    public void mostrarVentasJuanMobile() {
        ventasJuan = new ArrayList<>();

        if (startYearJuan != null && startYearJuan != 0 && startMonthJuan != null && startMonthJuan != 0 && startDayJuan != null && startDayJuan != 0
                && endYearJuan != null && endYearJuan != 0 && endMonthJuan != null && endMonthJuan != 0 && endDayJuan != null && endDayJuan != 0) {
            try {
                startDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(startYearJuan + "-" + startMonthJuan + "-" + startDayJuan);
                endDateJuan = new SimpleDateFormat("yyyy-MM-dd").parse(endYearJuan + "-" + endMonthJuan + "-" + endDayJuan);
                mostrarVentasJuan();
            } catch (Exception e) {
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas para Juan con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas para Juan con las fechas ingresadas");
            return;
        }
    }

    /**
     * *********************************************************
     * INFORME DE VENTAS X RUTA
     * *********************************************************
     */
    private void cargarRutas() {
        rutas = new ArrayList<>();

        rutas.add("Antioquia");
        rutas.add("Cundinamarca");
        rutas.add("Caribe");
        rutas.add("Cafetera");
        rutas.add("Santanderes");
        rutas.add("Urabá");
        rutas.add("Llanos");
        rutas.add("Valledupar");
        rutas.add("Amazonia");
        rutas.add("Pacifica");
        rutas.add("Desconocida");
    }

    public void mostrarVentasRutas() {
        fechasRutas = new ArrayList<>();
        ventasRutas = facturaSAPFacade.obtenerVentasRuta(startYearRoutes, startMonthRoutes, startDayRoutes, endYearRoutes, endMonthRoutes, endDayRoutes);

        if (ventasRutas != null && !ventasRutas.isEmpty()) {
            for (Object[] o : ventasRutas) {
                boolean existe = false;

                for (String f : fechasRutas) {
                    if (f.equals((String) o[0])) {
                        existe = true;
                        break;
                    }
                }

                if (!existe) {
                    fechasRutas.add((String) o[0]);
                }
            }
        }
    }

    public void mostrarVentasRutasDesktop() {
        ventasRutas = new ArrayList<>();

        if (startDateRoutes != null && endDateRoutes != null) {
            startYearRoutes = Integer.parseInt(new SimpleDateFormat("yyyy").format(startDateRoutes));
            startMonthRoutes = Integer.parseInt(new SimpleDateFormat("MM").format(startDateRoutes));
            startDayRoutes = Integer.parseInt(new SimpleDateFormat("dd").format(startDateRoutes));

            endYearRoutes = Integer.parseInt(new SimpleDateFormat("yyyy").format(endDateRoutes));
            endMonthRoutes = Integer.parseInt(new SimpleDateFormat("MM").format(endDateRoutes));
            endDayRoutes = Integer.parseInt(new SimpleDateFormat("dd").format(endDateRoutes));
            mostrarVentasRutas();
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas con las fechas ingresadas");
            return;
        }
    }

    public void mostrarVentasRutasMobile() {
        ventasRutas = new ArrayList<>();

        if (startYearRoutes != null && startYearRoutes != 0 && startMonthRoutes != null && startMonthRoutes != 0 && startDayRoutes != null && startDayRoutes != 0
                && endYearRoutes != null && endYearRoutes != 0 && endMonthRoutes != null && endMonthRoutes != 0 && endDayRoutes != null && endDayRoutes != 0) {
            try {
                startDateRoutes = new SimpleDateFormat("yyyy-MM-dd").parse(startYearRoutes + "-" + startMonthRoutes + "-" + startDayRoutes);
                endDateRoutes = new SimpleDateFormat("yyyy-MM-dd").parse(endYearRoutes + "-" + endMonthRoutes + "-" + endDayRoutes);
                mostrarVentasRutas();
            } catch (Exception e) {
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos de ventas con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos de ventas con las fechas ingresadas");
            return;
        }
    }

    public void cargarDaysRoutes(boolean inicio) {
        GregorianCalendar cal = new GregorianCalendar();

        if (inicio) {
            startDaysRoutes = new ArrayList<>();

            cal.set(Calendar.YEAR, startYearRoutes);
            cal.set(Calendar.MONTH, startMonthRoutes - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                startDaysRoutes.add(j);
            }
        } else {
            endDaysRoutes = new ArrayList<>();

            cal.set(Calendar.YEAR, endYearRoutes);
            cal.set(Calendar.MONTH, endMonthRoutes - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                endDaysRoutes.add(j);
            }
        }
    }

    /**
     * *********************************************************
     * INFORME DEL CCYGA
     * *********************************************************
     */
    public void cargarDaysTaller(boolean inicio) {
        GregorianCalendar cal = new GregorianCalendar();

        if (inicio) {
            startDaysTaller = new ArrayList<>();

            cal.set(Calendar.YEAR, startYearTaller);
            cal.set(Calendar.MONTH, startMonthTaller - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                startDaysTaller.add(j);
            }
        } else {
            endDaysTaller = new ArrayList<>();

            cal.set(Calendar.YEAR, endYearTaller);
            cal.set(Calendar.MONTH, endMonthTaller - 1);

            for (int j = 1; j <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); j++) {
                endDaysTaller.add(j);
            }
        }
    }

    public void obtenerSaldoTaller() {
        paso = 1;
        costoTaller = new ArrayList<>();

        List<CostoAcumuladoAlmacen> costo = costoAcumuladoAlmacenFacade.acumuladosTaller(startDateTaller, endDateTaller);

        if (costo != null && !costo.isEmpty()) {
            for (CostoAcumuladoAlmacen c : costo) {
                CostoAcumuladoAlmacenDTO dto = new CostoAcumuladoAlmacenDTO();

                dto.setCostoAcumulado(c.getCostoAcumulado().longValue());
                dto.setFecha(c.getCostoAcumuladoAlmacenPK().getFecha());
                dto.setPrecioAcumuladoConIva(c.getPrecioAcumuladoConIva().longValue());
                dto.setPrecioAcumuladoSinIva(c.getPrecioAcumuladoSinIva().longValue());
                dto.setSaldoAcumulado(c.getSaldoAcumulado());
                dto.setWhsCode(c.getCostoAcumuladoAlmacenPK().getWhsCode());

                costoTaller.add(dto);
            }
        }

        Collections.sort(costoTaller, new Comparator<CostoAcumuladoAlmacenDTO>() {
            @Override
            public int compare(CostoAcumuladoAlmacenDTO o1, CostoAcumuladoAlmacenDTO o2) {
                return o2.getFecha().compareTo(o1.getFecha());
            }
        });
    }

    public void mostrarTallerDesktop() {
        costoTaller = new ArrayList<>();

        if (startDateTaller != null && endDateTaller != null) {
            startYearTaller = Integer.parseInt(new SimpleDateFormat("yyyy").format(startDateTaller));
            startMonthTaller = Integer.parseInt(new SimpleDateFormat("MM").format(startDateTaller));
            startDayTaller = Integer.parseInt(new SimpleDateFormat("dd").format(startDateTaller));

            endYearTaller = Integer.parseInt(new SimpleDateFormat("yyyy").format(endDateTaller));
            endMonthTaller = Integer.parseInt(new SimpleDateFormat("MM").format(endDateTaller));
            endDayTaller = Integer.parseInt(new SimpleDateFormat("dd").format(endDateTaller));
            obtenerSaldoTaller();
        } else {
            mostrarMensaje("Error", "No se encontraron datos del taller con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos del taller con las fechas ingresadas");
            return;
        }
    }

    public void mostrarTallerMobile() {
        costoTaller = new ArrayList<>();

        if (startYearTaller != null && startYearTaller != 0 && startMonthTaller != null && startMonthTaller != 0 && startDayTaller != null && startDayTaller != 0
                && endYearTaller != null && endYearTaller != 0 && endMonthTaller != null && endMonthTaller != 0 && endDayTaller != null && endDayTaller != 0) {
            try {
                startDateTaller = new SimpleDateFormat("yyyy-MM-dd").parse(startYearTaller + "-" + startMonthTaller + "-" + startDayTaller);
                endDateTaller = new SimpleDateFormat("yyyy-MM-dd").parse(endYearTaller + "-" + endMonthTaller + "-" + endDayTaller);
                obtenerSaldoTaller();
            } catch (Exception e) {
            }
        } else {
            mostrarMensaje("Error", "No se encontraron datos del taller con las fechas ingresadas.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se encontraron datos del taller con las fechas ingresadas");
            return;
        }
    }

    public void mostrarInformeTallerDiario() {
        paso++;
        movimientosDiarios = facturaSAPFacade.obtenerMovimientoDiarioTaller();
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
