package co.matisses.web.mbean.ventas;

import co.matisses.persistence.web.entity.TurnoCaja;
import co.matisses.persistence.web.facade.BwsTerminalPOSFacade;
import co.matisses.persistence.web.facade.TurnoCajaFacade;
import co.matisses.web.dto.TerminalPOSDTO;
import co.matisses.web.dto.TirillaZDTO;
import co.matisses.web.dto.TurnoCajaDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.rest.FacturaSAPFacadeREST;
import co.matisses.web.rest.OperacionCajaFacadeREST;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.time.DateUtils;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "tirillaZMBean")
public class TirillaZMBean implements Serializable {

    private static final Logger log = Logger.getLogger(TirillaZMBean.class.getSimpleName());

    private Date fechaConsulta;
    private Integer anio;
    private Integer mes;
    private Integer dia;
    private Integer idTurnoSeleccionado;
    private String cajaSeleccionada;
    private TirillaZDTO tirillaDto;
    private List<TerminalPOSDTO> cajas;
    private List<TurnoCajaDTO> turnos;
    @EJB
    private BwsTerminalPOSFacade terminalPosFacade;
    @EJB
    private TurnoCajaFacade turnoCajaFacade;
    @EJB
    private FacturaSAPFacadeREST facturaSAPFacade;
    @EJB
    private OperacionCajaFacadeREST operacionCajaFacade;
    @Inject
    private UserSessionInfoMBean userSession;

    public TirillaZMBean() {
        cajas = new ArrayList<>();
        turnos = new ArrayList<>();
    }

    public TirillaZDTO getTirillaDto() {
        return tirillaDto;
    }

    public List<TerminalPOSDTO> getCajas() {
        return cajas;
    }

    public String getCajaSeleccionada() {
        return cajaSeleccionada;
    }

    public void setCajaSeleccionada(String cajaSeleccionada) {
        this.cajaSeleccionada = cajaSeleccionada;
    }

    public Integer getIdTurnoSeleccionado() {
        return idTurnoSeleccionado;
    }

    public void setIdTurnoSeleccionado(Integer idTurnoSeleccionado) {
        this.idTurnoSeleccionado = idTurnoSeleccionado;
    }

    public void setCajas(List<TerminalPOSDTO> cajas) {
        this.cajas = cajas;
    }

    public List<TurnoCajaDTO> getTurnos() {
        return turnos;
    }

    public void setTurnos(List<TurnoCajaDTO> turnos) {
        this.turnos = turnos;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public void procesarCambioValor() {

    }

    public void listarCajasActivasFecha() {
        log.log(Level.INFO, "Listando cajas activas para la fecha {0}-{1}-{2}. ", new Object[]{anio, mes, dia});
        cajas = new ArrayList<>();
        fechaConsulta = null;
        if (anio == null || mes == null || dia == null
                || anio <= 0 || mes <= 0 || mes > 12 || dia <= 0) {
            log.log(Level.WARNING, "Las fechas ingresadas no son validas. {0}-{1}-{2}", new Object[]{anio, mes, dia});
            return;
        }
        GregorianCalendar fecha = new GregorianCalendar();
        fecha.set(Calendar.YEAR, anio);
        fecha.set(Calendar.MONTH, mes - 1);
        if (dia <= fecha.getActualMaximum(Calendar.DATE)) {
            fecha.set(Calendar.DATE, dia);
        } else {
            log.log(Level.WARNING, "La fecha ingresada no es valida. El mes seleccionado tiene un maximo de {0} dias", fecha.getActualMaximum(Calendar.DATE));
            return;
        }
        fechaConsulta = fecha.getTime();
        List<Object[]> cajasAbiertas = terminalPosFacade.listarTerminalesActivasFecha(fechaConsulta);
        if (cajasAbiertas == null || cajasAbiertas.isEmpty()) {
            log.log(Level.SEVERE, "No se encontraron cajas abiertas para la fecha indicada. ");
            return;
        }
        log.log(Level.INFO, "Se encontraron {0} cajas en operacion para la fecha indicada. ", cajasAbiertas.size());
        for (Object[] cols : cajasAbiertas) {
            TerminalPOSDTO dto = new TerminalPOSDTO();
            dto.setAlias((String) cols[3]);
            dto.setCuentaEfectivo((String) cols[2]);
            dto.setIp((String) cols[0]);
            cajas.add(dto);
        }
    }

    public void seleccionarCaja() {
        log.log(Level.INFO, "Se selecciono la caja {0}", cajaSeleccionada == null ? "" : cajaSeleccionada);
        listarTurnosCaja();
    }

    public void listarTurnosCaja() {
        turnos = new ArrayList<>();
        if (cajaSeleccionada == null || cajaSeleccionada == null || cajaSeleccionada.trim().isEmpty()) {
            log.log(Level.WARNING, "No se consultaron los turnos por caja ya que no hay una caja seleccionada. ");
            return;
        }
        if (fechaConsulta == null) {
            log.log(Level.WARNING, "No se consultaron los turnos por caja ya que no hay una fecha valida ingresada. ");
            return;
        }

        List<TurnoCaja> entidades = turnoCajaFacade.cargarTurnosCajaFecha(cajaSeleccionada, DateUtils.truncate(fechaConsulta, Calendar.DATE));
        if (entidades == null || entidades.isEmpty()) {
            log.log(Level.WARNING, "No se encontraron turnos para la caja y fecha seleccionadas. ");
            return;
        }
        log.log(Level.INFO, "Se encontraron {0} turnos para la caja y fecha seleccionadas. ", entidades.size());
        for (TurnoCaja entidad : entidades) {
            TurnoCajaDTO dto = new TurnoCajaDTO();
            dto.setFecha(entidad.getFecha());
            dto.setIdTurno(entidad.getIdTurnoCaja());
            dto.setTerminal(entidad.getTerminal());
            dto.setUsuario(entidad.getUsuario());
            turnos.add(dto);
        }
    }

    public void seleccionarTurno() {
        log.log(Level.INFO, "Se selecciono el turno {0}", idTurnoSeleccionado);
        if (idTurnoSeleccionado == null) {
            return;
        }
        TurnoCajaDTO turnoDto = null;
        try {
            turnoDto = turnos.get(turnos.indexOf(new TurnoCajaDTO(idTurnoSeleccionado)));
        } catch (ArrayIndexOutOfBoundsException e) {
            log.log(Level.SEVERE, "No se pudo encontrar el turno seleccionado en la lista de turnos. ", e);
        }
        if (turnoDto == null) {
            return;
        }
        Calendar cal = new GregorianCalendar(anio, mes - 1, dia);
        tirillaDto = facturaSAPFacade.generarDatosInicialesTirilla(userSession.getAlmacen(), idTurnoSeleccionado, cal.getTime());
        log.log(Level.INFO, "Se obtuvieron los datos de la tirilla: {0}", tirillaDto.toString());
        tirillaDto = operacionCajaFacade.generarDatosTirillaZ(turnoDto.getUsuario(), idTurnoSeleccionado, tirillaDto, turnoDto.getTerminal());
        log.log(Level.INFO, "Se obtuvieron los datos de la tirilla: {0}", tirillaDto.toString());
    }

    public String obtenerFechaHora() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
    }
}
