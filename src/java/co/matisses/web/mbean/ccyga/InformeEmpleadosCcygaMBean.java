package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.LaborCcyga;
import co.matisses.persistence.web.entity.ProcesoCcyga;
import co.matisses.persistence.web.entity.ProductoCcyga;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProcesoCcygaFacade;
import co.matisses.web.dto.EmpleadoCcygaDTO;
import co.matisses.web.dto.LaborCcygaDTO;
import co.matisses.web.dto.LaborEmpleadoCcygaDTO;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "informeEmpleadosCcygaMBean")
public class InformeEmpleadosCcygaMBean implements Serializable {

    @Inject
    private BaruApplicationMBean aplicacionBean;
    private static final Logger log = Logger.getLogger(InformeEmpleadosCcygaMBean.class.getSimpleName());
    private static String rutaFotos;
    private List<LaborEmpleadoCcygaDTO> empleadosActivos;
    private List<EmpleadoCcygaDTO> empleadosInactivos;
    private List<EmpleadoCcygaDTO> empleados;
    private List<Object[]> resumenProcesos;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;
    @EJB
    private ProcesoCcygaFacade procesoFacade;

    public InformeEmpleadosCcygaMBean() {
        empleados = new ArrayList<>();
        empleadosActivos = new ArrayList<>();
        empleadosInactivos = new ArrayList<>();
        resumenProcesos = null;
    }

    @PostConstruct
    protected void initialize() {
        rutaFotos = aplicacionBean.obtenerValorPropiedad("baru.fotos.empleados");
        cargarEmpleadosCcyga();
        clasificarEmpleados();
    }

    public List<Object[]> getResumenProcesos() {
        return resumenProcesos;
    }

    public void setResumenProcesos(List<Object[]> resumenProcesos) {
        this.resumenProcesos = resumenProcesos;
    }

    public List<LaborEmpleadoCcygaDTO> getEmpleadosActivos() {
        return empleadosActivos;
    }

    public void setEmpleadosActivos(List<LaborEmpleadoCcygaDTO> empleadosActivos) {
        this.empleadosActivos = empleadosActivos;
    }

    public List<EmpleadoCcygaDTO> getEmpleadosInactivos() {
        return empleadosInactivos;
    }

    public void setEmpleadosInactivos(List<EmpleadoCcygaDTO> empleadosInactivos) {
        this.empleadosInactivos = empleadosInactivos;
    }

    public List<EmpleadoCcygaDTO> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<EmpleadoCcygaDTO> empleados) {
        this.empleados = empleados;
    }

    private void cargarEmpleadosCcyga() {
        List<Empleado> entidades = empleadoFacade.consultarEmpleadosCcyga();
        empleados = new ArrayList<>();
        for (Empleado entidad : entidades) {
            EmpleadoCcygaDTO dto = new EmpleadoCcygaDTO();
            dto.setCedula(entidad.getOfficeExt());
            dto.setCodigoRevisado(entidad.getUcodigoRevisado());
            String primerNombre = entidad.getFirstName();
            String segundoNombre = entidad.getMiddleName();
            if (segundoNombre == null || segundoNombre.trim().isEmpty()) {
                dto.setNombre(primerNombre);
            } else {
                dto.setNombre(primerNombre + " " + segundoNombre);
            }
            empleados.add(dto);
        }
        Collections.sort(empleados);
    }

    public void clasificarEmpleados() {
        empleadosActivos = new ArrayList<>();
        empleadosInactivos = new ArrayList<>();
        for (EmpleadoCcygaDTO dto : empleados) {
            List<LaborCcyga> labores = laborFacade.obtenerLaboresAbiertasPorEmpleado(dto.getCodigoRevisado());
            if (!labores.isEmpty()) {
                for (LaborCcyga entidad : labores) {
                    ProcesoCcyga entidadProceso = entidad.getIdProcesoProducto().getIdProceso();
                    ProcesoCcygaDTO procesoDto = new ProcesoCcygaDTO(entidadProceso.getIdProceso(), entidadProceso.getNombre());

                    ProductoCcyga entidadProducto = entidad.getIdProcesoProducto().getIdProducto();
                    ProductoCcygaDTO productoDto = null;
                    if (entidadProducto != null) {
                        productoDto = new ProductoCcygaDTO();
                        productoDto.setDescripcion(entidadProducto.getDescripcion());
                        productoDto.setDocumento(entidadProducto.getDocumento());
                        productoDto.setIdProducto(entidadProducto.getIdProducto());
                        productoDto.setRefProveedor(entidadProducto.getRefProveedor());
                        productoDto.setReferencia(entidadProducto.getReferencia());
                        productoDto.setRequerimiento(entidadProducto.getRequerimiento());
                    }

                    LaborCcygaDTO laborDto = new LaborCcygaDTO();
                    laborDto.setIdLabor(entidad.getIdLabor());
                    laborDto.setFecha(entidad.getFecha());
                    laborDto.setInicio(entidad.getHoraInicio());
                    laborDto.setFin(entidad.getHoraFin());
                    laborDto.setProceso(procesoDto);
                    laborDto.setProducto(productoDto);

                    LaborEmpleadoCcygaDTO laborEmpleadoDto = new LaborEmpleadoCcygaDTO();
                    laborEmpleadoDto.setEmpleado(dto);
                    laborEmpleadoDto.setLabor(laborDto);

                    empleadosActivos.add(laborEmpleadoDto);
                }
            } else {
                dto.setTiempoInactividad(obtenerTiempoInactividad(dto.getCodigoRevisado()));
                empleadosInactivos.add(dto);
            }
        }
    }

    private String obtenerTiempoInactividad(String codRevisado) {
        LaborCcyga l = laborFacade.obtenerUltimaLaborEmpleado(codRevisado);

        if (l != null && l.getIdLabor() != null && l.getIdLabor() != 0) {
            Date fechaActual = new Date();

            //Inicia comparacion de la fecha actual vs la ultima fecha del usuario para calcular el tiempo de inactividad
            long diferencia = fechaActual.getTime() - l.getHoraFin().getTime();
            long horas = diferencia / 1000 / 60 / 60;
            diferencia = diferencia - (1000 * 60 * 60 * horas);
            long minutos = diferencia / 1000 / 60;
            return (horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos);
        }
        return "00:00";
    }

    private boolean fotoValida(String ruta) {
        try {
            URL obj = new URL(ruta);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al validar la imagen. ", e);
            return false;
        }
    }

    public String obtenerRutaFotoEmpleado(String cedula) {
        String rutaFoto = String.format(rutaFotos, cedula);
        if (fotoValida(rutaFoto)) {
            return rutaFoto;
        } else {
            return String.format(rutaFotos, "pawn");
        }
    }

    public boolean laborAtrasada(Date fecha) {
        GregorianCalendar cal = new GregorianCalendar();
        GregorianCalendar cal2 = new GregorianCalendar();
        cal2.setTime(fecha);
        if (cal.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)
                && cal.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)
                && cal.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH)) {
            return false;
        } else {
            return true;
        }
    }

    public String obtenerDuracionLabor(Date fecha) {
        Date fecha2 = new Date();
        long diferencia = fecha2.getTime() - fecha.getTime();
        long horas = diferencia / 1000 / 60 / 60;
        diferencia = diferencia - (1000 * 60 * 60 * horas);
        long minutos = diferencia / 1000 / 60;
        return (horas < 10 ? "0" + horas : horas) + ":" + (minutos < 10 ? "0" + minutos : minutos);
    }

    public void realizarCierreGeneral() {
        log.log(Level.INFO, "Realizando cierre general de todas las labores iniciadas. ");
        List<LaborCcyga> laboresAbiertas = laborFacade.obtenerLaboresAbiertas();
        log.log(Level.INFO, "Se encontraron [{0}] labores para finalizar. ", laboresAbiertas.size());
        for (LaborCcyga entidadLabor : laboresAbiertas) {
            log.log(Level.INFO, "Finalizando labor [{0}]-[{1}]", new Object[]{entidadLabor.getIdLabor(), entidadLabor.getIdProcesoProducto().getIdProceso().getNombre()});
            laborFacade.finalizarLabor(entidadLabor.getIdLabor());
        }
    }

    public void cargarResumen() {
        resumenProcesos = procesoFacade.cargarReumenProcesosActivos();
    }
}
