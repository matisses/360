package co.matisses.web.mbean.ubicaciones;

import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.web.Acciones;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.client.BinLocationsClient;
import co.matisses.web.dto.BinLocationDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.UbicacionSAPDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
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
@Named(value = "habilitarUbicacionesMBean")
public class HabilitarUbicacionesMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(HabilitarUbicacionesMBean.class.getSimpleName());
    private String ubicacion;
    private boolean habilitada = false;
    private UbicacionSAPDTO ubicacionDto;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;

    public HabilitarUbicacionesMBean() {
    }

    @PostConstruct
    protected void initialize() {
        ubicacionDto = new UbicacionSAPDTO();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean isHabilitada() {
        return habilitada;
    }

    public void setHabilitada(boolean habilitada) {
        this.habilitada = habilitada;
    }

    public void buscarUbicacion() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.HABILITAR)) {
            if (ubicacion == null || ubicacion.isEmpty()) {
                mostrarMensaje("Error", "Ingrese una ubicación para poder realizar la consulta", true, false, false);
                log.log(Level.SEVERE, "Ingrese una ubicacion para poder realizar la consulta");
                return;
            }

            UbicacionSAP ubi = ubicacionSAPFacade.obtenerDatosUbicacionBinCode(sessionMBean.getAlmacen() + ubicacion);

            if (ubi == null || ubi.getAbsEntry() == null || ubi.getAbsEntry() == 0) {
                mostrarMensaje("Error", "La ubicación solicitada no se encontro en el almacén logeado", true, false, false);
                log.log(Level.SEVERE, "La ubicacion solicitada no se encontro en el almacen logeado");
                return;
            }

            ubicacionDto = new UbicacionSAPDTO(ubi.getAbsEntry(), ubi.getBinCode(), ubi.getWhsCode(), ubi.getSL1Code(), ubi.getSL2Code(), ubi.getAttr2Val());

            if (!ubi.getDisabled().equals("Y".charAt(0))) {
                habilitada = true;
            } else {
                habilitada = false;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este modulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    public void habilitarUbicacion() {
        if (sessionMBean.validarPermisoUsuario(Objetos.UBICACION, Acciones.HABILITAR)) {
            if (ubicacionDto != null && ubicacionDto.getAbsEntry() != null && ubicacionDto.getAbsEntry() != 0) {
                UbicacionSAP ubi = ubicacionSAPFacade.find(ubicacionDto.getAbsEntry());

                if (ubi == null || ubi.getAbsEntry() == null || ubi.getAbsEntry() == 0) {
                    mostrarMensaje("Error", "No se encontraron datos para proceder", true, false, false);
                    log.log(Level.SEVERE, "No se encontraron datos para proceder");
                    return;
                }

                BinLocationsClient client = new BinLocationsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));
                GenericRESTResponseDTO res = new GenericRESTResponseDTO();
                BinLocationDTO dto = new BinLocationDTO();

                if (habilitada) {
                    List<SaldoUbicacion> saldos = saldoUbicacionFacade.buscarXBinCode(sessionMBean.getAlmacen() + ubicacion);
                    if (saldos != null && !saldos.isEmpty()) {
                        mostrarMensaje("Error", "No se puede deshabilitar la ubicación debido a que tiene saldo para el almacén logeado", true, false, false);
                        log.log(Level.SEVERE, "No se puede deshabilitar la ubicacion debido a que tiene saldo para el almacen logeado");
                        return;
                    }

                    dto.setAbsEntry(ubi.getAbsEntry());
                    dto.setWarehouse(ubi.getWhsCode());
                    dto.setSublevel1(ubi.getSL1Code());
                    dto.setSublevel2(ubi.getSL2Code());
                    dto.setAttribute1((ubi.getAttr1Val() != null && ubi.getAttr1Val().equals("Sí")));
                    dto.setAttribute2(null);
                    dto.setAttribute3(null);
                    dto.setAttribute4(null);
                    dto.setAttribute5(null);
                    dto.setAttribute6((ubi.getAttr6Val() != null && ubi.getAttr6Val().equals("Sí")));
                    dto.setAttribute7(null);
                    dto.setAttribute8(null);
                    dto.setAttribute9((ubi.getAttr9Val() != null && ubi.getAttr9Val().equals("Sí")));
                    dto.setInactive(true);

                    try {
                        res = client.editarUbicacion("jguisao", dto);
                        if (res.getEstado() == 0) {
                            log.log(Level.SEVERE, res.getMensaje());
                        }
                    } catch (Exception e) {
                        log.log(Level.SEVERE, e.getMessage());
                        return;
                    }
                } else {
                    String almacen;
                    if (sessionMBean.getAlmacen().contains("CL")) {
                        almacen = sessionMBean.getAlmacen().replace("CL", "");
                    } else if (sessionMBean.getAlmacen().contains("AF")) {
                        almacen = sessionMBean.getAlmacen().replace("AF", "");
                    } else if (sessionMBean.getAlmacen().contains("MU")) {
                        almacen = sessionMBean.getAlmacen().replace("MU", "");
                    } else if (sessionMBean.getAlmacen().contains("IN")) {
                        almacen = sessionMBean.getAlmacen().replace("IN", "");
                    } else {
                        almacen = sessionMBean.getAlmacen();
                    }

                    List<UbicacionSAP> ubis = ubicacionSAPFacade.obtenerUbicacionesRelacionadas(almacen + ubicacion);

                    if (ubis != null && !ubis.isEmpty()) {
                        for (UbicacionSAP u : ubis) {
                            List<SaldoUbicacion> saldos = saldoUbicacionFacade.buscarXBinCode(u.getBinCode());
                            if (saldos != null && !saldos.isEmpty()) {
                                mostrarMensaje("Error", "No se puede habilitar la ubicación debido a que en el almacén: " + u.getWhsCode() + ", hay mercancia ubicada.", true, false, false);
                                log.log(Level.SEVERE, "No se puede habilitar la ubicación debido a que en el almacén: [{0}], hay mercancia ubicada.", u.getWhsCode());
                                return;
                            }
                        }

                        for (UbicacionSAP u : ubis) {
                            dto = new BinLocationDTO();

                            dto.setAbsEntry(u.getAbsEntry());
                            dto.setWarehouse(u.getWhsCode());
                            dto.setSublevel1(u.getSL1Code());
                            dto.setSublevel2(u.getSL2Code());
                            dto.setAttribute1((u.getAttr1Val() != null && u.getAttr1Val().equals("Sí")));
                            dto.setAttribute2(null);
                            dto.setAttribute3(null);
                            dto.setAttribute4(null);
                            dto.setAttribute5(null);
                            dto.setAttribute6((u.getAttr6Val() != null && u.getAttr6Val().equals("Sí")));
                            dto.setAttribute7(null);
                            dto.setAttribute8(null);
                            dto.setAttribute9((u.getAttr9Val() != null && u.getAttr9Val().equals("Sí")));

                            if (u.getWhsCode().equals(sessionMBean.getAlmacen())) {
                                dto.setInactive(false);

                                try {
                                    res = client.editarUbicacion(sessionMBean.getUsuario(), dto);
                                    if (res.getEstado() == 0) {
                                        log.log(Level.SEVERE, res.getMensaje());
                                    }
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, e.getMessage());
                                    return;
                                }

                            } else {
                                dto.setInactive(true);

                                try {
                                    res = client.editarUbicacion(sessionMBean.getUsuario(), dto);
                                    if (res.getEstado() == 0) {
                                        log.log(Level.SEVERE, res.getMensaje());
                                    }
                                } catch (Exception e) {
                                    log.log(Level.SEVERE, e.getMessage());
                                    return;
                                }
                            }
                        }
                    }
                }
                limpiar();
            } else {
                mostrarMensaje("Error", "No se encontraron datos para proceder", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos para proceder");
                return;
            }
        } else {
            mostrarMensaje("Error", "El usuario no tiene permiso para usar este modulo", true, false, false);
            log.log(Level.SEVERE, "El usuario no tiene permiso para usar este modulo");
            return;
        }
    }

    private void limpiar() {
        ubicacion = null;
        habilitada = false;
        ubicacionDto = new UbicacionSAPDTO();
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
