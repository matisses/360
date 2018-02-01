package co.matisses.web.mbean.clientes;

import co.matisses.persistence.sap.facade.ReciboCajaFacade;
import co.matisses.persistence.web.entity.SaldoVencidoCliente;
import co.matisses.persistence.web.facade.SaldoVencidoClienteFacade;
import co.matisses.web.bcs.client.JournalEntryClient;
import co.matisses.web.bcs.client.PaymentClient;
import co.matisses.web.dto.BCSPaymentDTO;
import co.matisses.web.dto.ResponseDTO;
import co.matisses.web.dto.SaldoVencidoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "saldoVencidoMBean")
public class SaldoVencidoMBean implements Serializable {

    private static final Logger console = Logger.getLogger(SaldoVencidoMBean.class.getSimpleName());

    private String busqueda;
    private SaldoVencidoDTO saldoVencidoDto;
    private List<SaldoVencidoDTO> saldos;
    private List<SaldoVencidoDTO> saldosFiltrados;
    private List<SaldoVencidoDTO> transacciones;

    @Inject
    private BaruApplicationMBean baruApplication;
    @Inject
    private UserSessionInfoMBean userSesion;
    @EJB
    private SaldoVencidoClienteFacade saldoVencidoFacade;
    @EJB
    private ReciboCajaFacade reciboCajaFacade;

    public SaldoVencidoMBean() {
        saldos = new ArrayList<>();
        saldosFiltrados = new ArrayList<>();
        transacciones = new ArrayList<>();
    }

    @PostConstruct
    private void initialize() {
        List<Object[]> rows = saldoVencidoFacade.listarSaldos();
        saldos = new ArrayList<>();
        for (Object[] row : rows) {
            saldos.add(new SaldoVencidoDTO((String) row[0], (String) row[1], (Integer) row[2]));
        }
        saldosFiltrados = new ArrayList<>();
        saldosFiltrados.addAll(saldos);
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public List<SaldoVencidoDTO> getSaldosFiltrados() {
        return saldosFiltrados;
    }

    public void setSaldosFiltrados(List<SaldoVencidoDTO> saldosFiltrados) {
        this.saldosFiltrados = saldosFiltrados;
    }

    public List<SaldoVencidoDTO> getTransacciones() {
        return transacciones;
    }

    public SaldoVencidoDTO getSaldoVencidoDto() {
        return saldoVencidoDto;
    }

    public void setSaldoVencidoDto(SaldoVencidoDTO saldoVencidoDto) {
        this.saldoVencidoDto = saldoVencidoDto;
    }

    public void filtrarSaldos() {
        if (busqueda == null || busqueda.trim().isEmpty()) {
            console.severe("No se envio un texto para consultar");
            return;
        }
        saldosFiltrados = new ArrayList<>();
        for (SaldoVencidoDTO dto : saldos) {
            if (dto.getNombre().toLowerCase().contains(busqueda.toLowerCase())
                    || dto.getNit().toLowerCase().contains(busqueda.toLowerCase())) {
                saldosFiltrados.add(dto);
            }
        }
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void activarSaldoCliente() {
        if (saldoVencidoDto == null || saldoVencidoDto.getNit() == null) {
            console.log(Level.SEVERE, "No se ha seleccionado un cliente para activar el saldo");
            return;
        }
        if (saldoVencidoDto.getSaldo() <= 0) {
            console.log(Level.SEVERE, "El cliente seleccionado no tiene saldo a favor vencido");
            return;
        }
        console.log(Level.INFO, "Activando saldo vencido cliente {0}", saldoVencidoDto);

        //Registra un recibo de caja para generar el saldo
        BCSPaymentDTO dto = new BCSPaymentDTO();
        dto.setCardCode(saldoVencidoDto.getNit());
        dto.setPaymentType(BCSPaymentDTO.PaymentTypeDTO.CUSTOMER);
        dto.setSeriesCode("199"); //TODO: parametrizar nro de serie
        dto.setTransferAccount("23550501"); //TODO: parametrizar numero de cuenta
        dto.setTransferReference("Reactivacion Saldo");
        dto.setTransferSum(saldoVencidoDto.getSaldo().toString());

        PaymentClient paymentClient = new PaymentClient(baruApplication.obtenerValorPropiedad("url.servicio.bcs"));
        Response res = paymentClient.crearReciboCaja(dto, userSesion.getUsuario());
        Long docEntryRec = -1L;
        if (res.hasEntity()) {
            ResponseDTO response = res.readEntity(ResponseDTO.class);
            if (response.getEstado() == -1) {
                console.log(Level.SEVERE, response.getMensaje());
                return;
            } else {
                console.log(Level.INFO, "Se creo el RC con docEntry={0}", response.getMensaje());
                docEntryRec = Long.parseLong(response.getMensaje());
            }
        }

        //Consultar el id del asiento generado para el recibo
        Long idAsiento = reciboCajaFacade.obtenerIdAsiento(docEntryRec, "23550510");
        if (idAsiento <= 0) {
            return;
        }

        //Modificar asiento (agregar nit de baru en el detalle)
        JournalEntryClient jEntryClient = new JournalEntryClient(baruApplication.obtenerValorPropiedad("url.servicio.bcs"));
        jEntryClient.actualizarTerceroAsiento(userSesion.getUsuario(), idAsiento, "23550510");

        //Consultar saldo a favor del cliente
        //Si el saldo a favor refleja la transaccion recien realizada, insertar nuevo registro cancelando el saldo recien usado
        SaldoVencidoCliente nuevoSaldo = new SaldoVencidoCliente();
        nuevoSaldo.setFecha(new Date());
        nuevoSaldo.setNit(saldoVencidoDto.getNit());
        nuevoSaldo.setNombre(saldoVencidoDto.getNombre());
        nuevoSaldo.setSaldo(saldoVencidoDto.getSaldo() * -1);
        nuevoSaldo.setUsuario(userSesion.getUsuario());
        saldoVencidoFacade.create(nuevoSaldo);

        //Recarga la lista de saldos
        initialize();
    }

    public void seleccionarSaldoCliente() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nitSeleccionado = params.get("nit");

        if (nitSeleccionado == null || nitSeleccionado.trim().isEmpty()) {
            console.log(Level.SEVERE, "No se recibio un nit valido. ");
            return;
        }
        saldoVencidoDto = null;
        for (SaldoVencidoDTO dto : saldos) {
            if (dto.getNit().equals(nitSeleccionado)) {
                saldoVencidoDto = dto;
                break;
            }
        }
    }

    public void consultarTransaccionesCliente() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String nitSeleccionado = params.get("nit");
        if (nitSeleccionado == null || nitSeleccionado.trim().isEmpty()) {
            console.log(Level.SEVERE, "No se recibio un nit valido. ");
            return;
        }
        transacciones = new ArrayList<>();
        List<SaldoVencidoCliente> entidades = saldoVencidoFacade.buscarPorNit(nitSeleccionado);
        for (SaldoVencidoCliente entidad : entidades) {
            transacciones.add(new SaldoVencidoDTO(entidad.getIdSaldoVencido(), entidad.getNit(), entidad.getNombre(), entidad.getSaldo(), entidad.getUsuario(), entidad.getFecha()));
        }
    }
}
