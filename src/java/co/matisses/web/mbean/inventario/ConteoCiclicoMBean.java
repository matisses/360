package co.matisses.web.mbean.inventario;

import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.persistence.web.entity.BitacoraInventarioUbicacion;
import co.matisses.persistence.web.entity.ConteoTienda;
import co.matisses.persistence.web.entity.TipoConteo;
import co.matisses.persistence.web.facade.BitacoraInventarioUbicacionFacade;
import co.matisses.persistence.web.facade.ConteoTiendaFacade;
import co.matisses.web.dto.InventarioCiclicoDTO;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.session.ConteosSessionMBean;
import java.io.Serializable;
import java.util.Date;
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
@Named(value = "conteoCiclicoMBean")
public class ConteoCiclicoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ConteoCiclicoMBean.class.getSimpleName());
    private InventarioCiclicoDTO inventarioCiclicoDTO;
    @EJB
    private UbicacionSAPFacade ubicacionFacade;
    @EJB
    private ConteoTiendaFacade conteoTiendaFacade;
    @EJB
    private BitacoraInventarioUbicacionFacade bitacoraInventarioFacade;
    @Inject
    private UserSessionInfoMBean userSessionInfo;
    @Inject
    private ConteosSessionMBean conteosSessionMBean;

    public ConteoCiclicoMBean() {
    }

    public InventarioCiclicoDTO getInventarioCiclicoDTO() {
        return inventarioCiclicoDTO;
    }

    public void setInventarioCiclicoDTO(InventarioCiclicoDTO inventarioCiclicoDTO) {
        this.inventarioCiclicoDTO = inventarioCiclicoDTO;
    }

    @PostConstruct
    protected void initialize() {
        consultarSiguienteUbicacion();
    }

    private void consultarSiguienteUbicacion() {
        inventarioCiclicoDTO = new InventarioCiclicoDTO();
        try {
            Object[] datosUbicacion = ubicacionFacade.obtenerSiguienteUbicacionInventarioCiclico(userSessionInfo.getAlmacen());
            if (datosUbicacion != null) {
                //Integer binAbs = (Integer) datosUbicacion[0];
                String binCode = (String) datosUbicacion[3];
                //Date lastTransactionDate = (Date) datosUbicacion[2];
                //Date lastInventoryDate = (Date) datosUbicacion[3];
                inventarioCiclicoDTO.setBinCode(binCode);
                inventarioCiclicoDTO.setFechaCreacionConteo(new Date());
                inventarioCiclicoDTO.setUsuarioCreador(userSessionInfo.getUsuario());
                inventarioCiclicoDTO.setWhsCode((String) datosUbicacion[2]);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible inicializar el bean. Ocurrio un error al consultar la siguiente ubicacion a inventariar. ", e);
        }
    }

    private boolean validarUltimaUbicacion() {
        InventarioCiclicoDTO tmp = inventarioCiclicoDTO;
        consultarSiguienteUbicacion();
        return tmp.getBinCode().equals(inventarioCiclicoDTO.getBinCode());
    }

    public String crearConteoUbicacion() {
        log.log(Level.INFO, "Creando nuevo conteo...");
        if (!validarUltimaUbicacion()) {
            log.log(Level.WARNING, "La ubicacion a inventariar ha cambiado. La nueva ubicacion es {0}", inventarioCiclicoDTO.getBinCode());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "La ubicación ha cambiado", "La ubicación anterior ya está siendo inventariada por otro usuario. Por favor continuar con la ubicación que se indica abajo"));
            return null;
        }
        //crear conteo en base de datos
        Integer idConteo = crearConteoTienda();
        if (idConteo == null) {
            return null;
        }
        //insertar informacion de nuevo conteo en bitacora
        crearRegistroBitacora();
        //poner informacion de conteo en bean de sesion
        conteosSessionMBean.setIdConteo(idConteo);
        //redireccionar
        return "contar";
    }

    private Integer crearConteoTienda() {
        try {
            ConteoTienda conteo = new ConteoTienda();
            conteo.setTienda(userSessionInfo.getAlmacen());
            conteo.setIdTipoConteo(new TipoConteo(2));
            conteo.setFecha(new Date());
            conteo.setUsuario(userSessionInfo.getUsuario());
            conteo.setEstado("P");
            conteo.setDotacion(inventarioCiclicoDTO.getWhsCode().startsWith("MU"));
            conteo.setCliente(inventarioCiclicoDTO.getWhsCode().startsWith("CL"));
            conteo.setVenta(inventarioCiclicoDTO.getWhsCode().startsWith("0"));
            conteo.setUbicacion(inventarioCiclicoDTO.getBinCode());
            conteo.setCasilla(true);
            conteo.setPda(false);
            conteoTiendaFacade.create(conteo);
            log.log(Level.INFO, "Se creo el conteo #{0}", conteo.getIdConteo());
            return conteo.getIdConteo();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible crear el conteo en base de datos. ", e);
            return null;
        }
    }

    private void crearRegistroBitacora() {
        try {
            BitacoraInventarioUbicacion bitacora = new BitacoraInventarioUbicacion();
            bitacora.setBinCode(inventarioCiclicoDTO.getWhsCode().concat(inventarioCiclicoDTO.getBinCode()));
            bitacora.setFechaCreacionConteo(new Date());
            bitacora.setUsuarioCreador(userSessionInfo.getUsuario());
            bitacora.setWhsCode(inventarioCiclicoDTO.getWhsCode());
            bitacoraInventarioFacade.create(bitacora);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible crear el registro en la bitacora de inventario por ubicacion. ", e);
        }
    }
}
