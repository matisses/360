package co.matisses.web.mbean.inventario;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventarioPK;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.web.entity.BitacoraInventarioUbicacion;
import co.matisses.persistence.web.entity.ConteoTienda;
import co.matisses.persistence.web.entity.DetalleConteo;
import co.matisses.persistence.web.entity.DiferenciasConteo;
import co.matisses.persistence.web.entity.ResultadoConteo;
import co.matisses.persistence.web.facade.BitacoraInventarioUbicacionFacade;
import co.matisses.persistence.web.facade.ConteoTiendaFacade;
import co.matisses.persistence.web.facade.DetalleConteoFacade;
import co.matisses.persistence.web.facade.DiferenciasConteoFacade;
import co.matisses.persistence.web.facade.ResultadoConteoFacade;
import co.matisses.web.dto.DetalleConteoDTO;
import co.matisses.web.dto.GaleriaDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import co.matisses.web.mbean.session.ConteosSessionMBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "contarMBean")
public class ContarMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private ConteosSessionMBean conteosSessionMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private SendHTMLEmailMBean emailSender;
    private static final Logger log = Logger.getLogger(ContarMBean.class.getSimpleName());
    private String referencia;
    private String ultimaReferencia;
    private String parametroBusqueda;
    private boolean permitirTrabajo = true;
    private List<DetalleConteoDTO> detalle;
    private List<DetalleConteoDTO> detalleFull;
    private List<GaleriaDTO> galeria;
    private Queue<DetalleConteoDTO> cola;
    @EJB
    private BitacoraInventarioUbicacionFacade bitacoraFacade;
    @EJB
    private DetalleConteoFacade detalleConteoFacade;
    @EJB
    private ConteoTiendaFacade conteoTiendaFacade;
    @EJB
    private ResultadoConteoFacade resultadoConteoFacade;
    @EJB
    private DiferenciasConteoFacade diferenciasConteoFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    public ContarMBean() {
        detalle = new ArrayList<>();
        detalleFull = new ArrayList<>();
        galeria = new ArrayList<>();
        cola = new LinkedList<>();
    }

    @PostConstruct
    protected void initialize() {
        validarSession();
    }

    private void validarSession() {
        if (conteosSessionMBean == null || conteosSessionMBean.getIdConteo() == null || conteosSessionMBean.getIdConteo() == 0) {
            mostrarMensaje("Error", "El usuario no ha seleccionado una conteo para poder trabajar", true, false, false);
            log.log(Level.SEVERE, "El usuario no ha seleccionado una conteo para poder trabajar");
            permitirTrabajo = false;
            return;
//            redirigir("/inventario/conteos/index.xhtml");
        } else {
            obtenerDatosRegistrados();
        }
    }

    private void redirigir(String ruta) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect(ec.getRequestContextPath() + ruta);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error redireccionando. ", e);
        }
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

    public ImagenProductoMBean getImagenProductoMBean() {
        return imagenProductoMBean;
    }

    public void setImagenProductoMBean(ImagenProductoMBean imagenProductoMBean) {
        this.imagenProductoMBean = imagenProductoMBean;
    }

    public BaruGenericMBean getBaruGenericMBean() {
        return baruGenericMBean;
    }

    public void setBaruGenericMBean(BaruGenericMBean baruGenericMBean) {
        this.baruGenericMBean = baruGenericMBean;
    }

    public ConteosSessionMBean getConteosSessionMBean() {
        return conteosSessionMBean;
    }

    public void setConteosSessionMBean(ConteosSessionMBean conteosSessionMBean) {
        this.conteosSessionMBean = conteosSessionMBean;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getUltimaReferencia() {
        return ultimaReferencia;
    }

    public void setUltimaReferencia(String ultimaReferencia) {
        this.ultimaReferencia = ultimaReferencia;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public List<DetalleConteoDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleConteoDTO> detalle) {
        this.detalle = detalle;
    }

    public List<GaleriaDTO> getGaleria() {
        return galeria;
    }

    public void setGaleria(List<GaleriaDTO> galeria) {
        this.galeria = galeria;
    }

    public Queue<DetalleConteoDTO> getCola() {
        return cola;
    }

    public void setCola(Queue<DetalleConteoDTO> cola) {
        this.cola = cola;
    }

    private void obtenerDatosRegistrados() {
        log.log(Level.INFO, "Verificando si el usuario tiene referencias agregadas al conteo");
        detalleFull = new ArrayList<>();

        List<DetalleConteo> detail = detalleConteoFacade.obtenerDetalleConteoUsuario(conteosSessionMBean.getIdConteo(), sessionMBean.getUsuario());

        if (detail != null && !detail.isEmpty()) {
            for (DetalleConteo d : detail) {
                if (detalleFull.isEmpty()) {
                    detalleFull.add(new DetalleConteoDTO(d.getIdConteo().getIdConteo(), 1, d.getUsuario(), d.getReferencia(), d.getFecha()));
                } else {
                    boolean existe = false;
                    for (DetalleConteoDTO dto : detalleFull) {
                        if (dto.getReferencia().equals(d.getReferencia())) {
                            dto.setCantidad(dto.getCantidad() + 1);
                            existe = true;
                            break;
                        }
                    }
                    if (!existe) {
                        detalleFull.add(new DetalleConteoDTO(d.getIdConteo().getIdConteo(), 1, d.getUsuario(), d.getReferencia(), d.getFecha()));
                    }
                }
            }
            detalle = new ArrayList<>(detalleFull.subList(0, detalleFull.size() > 16 ? 16 : detalleFull.size()));
        }
    }

    private boolean validarCondicionesAcceso() {
        //En este metodo se valida que el conteo no se haya cerrado, y asi evitar que los usuarios sigan agregando o eliminando referencias
        ConteoTienda c = conteoTiendaFacade.find(conteosSessionMBean.getIdConteo());

        if (c != null && c.getIdConteo() != null && c.getIdConteo() != 0 && c.getEstado().equals("P")) {
            List<ResultadoConteo> resultados = resultadoConteoFacade.obtenerResultadoConteo(conteosSessionMBean.getIdConteo());

            if (resultados != null && !resultados.isEmpty()) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public void agregarReferencia() {
        if (permitirTrabajo) {
            if (referencia == null || referencia.isEmpty() || referencia.length() < 20 || referencia.length() > 20) {
                mostrarMensaje("Error", "Ingrese una referencia de 20 caracteres para poder continuar", true, false, false);
                log.log(Level.SEVERE, "Ingrese una referencia de 20 caracteres para poder continuar");
                return;
            }

            if (validarCondicionesAcceso()) {
                if (agregarReferenciaBD()) {
                    if (detalleFull != null && !detalleFull.isEmpty()) {
                        if (ultimaReferencia != null && ultimaReferencia.equals(referencia)) {
                            detalleFull.get(0).setCantidad(detalleFull.get(0).getCantidad() + 1);
                        } else {
                            detalleFull.add(0, new DetalleConteoDTO(conteosSessionMBean.getIdConteo(), 1, sessionMBean.getUsuario(), referencia, new Date()));
                        }
                    } else {
                        detalleFull.add(0, new DetalleConteoDTO(conteosSessionMBean.getIdConteo(), 1, sessionMBean.getUsuario(), referencia, new Date()));
                    }
                    log.log(Level.INFO, "Agregando la referencia [{0}], al conteo con id [{1}]", new Object[]{referencia, conteosSessionMBean.getIdConteo()});
                    ultimaReferencia = referencia;
                    obtenerGaleria();
                    referencia = null;
                    detalle = new ArrayList<>(detalleFull.subList(0, detalleFull.size() > 16 ? 16 : detalleFull.size()));
                } else {
                    mostrarMensaje("Error", "Ocurrio un error al agregar la referencia: " + referencia, true, false, false);
                    return;
                }
            } else {
                mostrarMensaje("Error", "No se pueden agregar mas referencias al conteo debido a que este ya fue finalizado", true, false, false);
                log.log(Level.SEVERE, "No se pueden agregar mas referencias al conteo debido a que este ya fue finalizado");
                return;
            }
        } else {
            validarSession();
        }
    }

    private boolean agregarReferenciaBD() {
        DetalleConteo detail = new DetalleConteo();

        detail.setFecha(new Date());
        detail.setIdConteo(new ConteoTienda(conteosSessionMBean.getIdConteo()));
        detail.setReferencia(referencia);
        detail.setUsuario(sessionMBean.getUsuario());

        try {
            detalleConteoFacade.create(detail);
            log.log(Level.INFO, "Se agrego la referencia [{0}], al conteo con id [{1}]", new Object[]{referencia, conteosSessionMBean.getIdConteo()});
            return true;
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al agregar la referencia. Error [{0}]", e.getMessage());
            return false;
        }
    }

    private void obtenerGaleria() {
        galeria.clear();

        galeria.add(new GaleriaDTO(0, imagenProductoMBean.obtenerPlantilla(referencia), referencia));
        galeria.add(new GaleriaDTO(1, imagenProductoMBean.obtenerUrlProducto(referencia, false), referencia));
    }

    public void eliminarReferencia() {
        if (referencia == null || referencia.isEmpty()) {
            referencia = ultimaReferencia;
        }

        if (referencia == null || referencia.isEmpty()) {
            mostrarMensaje("Error", "No se encontró una referencia seleccionada para eliminar", true, false, false);
            log.log(Level.SEVERE, "No se encontro una referencia seleccionada para eliminar");
            return;
        }

        if (validarCondicionesAcceso()) {
            if (eliminarReferenciaBD()) {
                for (DetalleConteoDTO d : detalleFull) {
                    if (d.getReferencia().equals(referencia)) {
                        if (d.getCantidad() > 1) {
                            d.setCantidad(d.getCantidad() - 1);
                        } else {
                            detalleFull.remove(d);
                        }
                        log.log(Level.INFO, "Eliminando la referencia [{0}], del conteo con id [{1}]", new Object[]{referencia, conteosSessionMBean.getIdConteo()});
                        break;
                    }
                }

                if (ultimaReferencia != null && referencia != null && ultimaReferencia.equals(referencia)) {
                    ultimaReferencia = null;
                    galeria = new ArrayList<>();
                }
            } else {
                mostrarMensaje("Error", "Ocurrio un error al eliminar la referencia: " + referencia, true, false, false);
            }
            referencia = null;
            detalle = new ArrayList<>(detalleFull.subList(0, detalleFull.size() > 16 ? 16 : detalleFull.size()));
        } else {
            mostrarMensaje("Error", "No se pueden eliminar mas referencias del conteo debido a que este ya fue finalizado", true, false, false);
            log.log(Level.SEVERE, "No se pueden eliminar mas referencias del conteo debido a que este ya fue finalizado");
            return;
        }
    }

    public void seleccionarReferenciaEliminar() {
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        log.log(Level.INFO, "Se selecciono la referencia [{0}] del conteo [{1}], para eliminar", new Object[]{referencia, conteosSessionMBean.getIdConteo()});
    }

    private boolean eliminarReferenciaBD() {
        DetalleConteo detail = detalleConteoFacade.obtenerDetalleConteo(conteosSessionMBean.getIdConteo(), referencia);

        if (detail != null && detail.getFecha() != null) {
            try {
                detalleConteoFacade.remove(detail);
                log.log(Level.INFO, "Se elimino la referencia [{0}], del conteo con id [{1}]", new Object[]{referencia, conteosSessionMBean.getIdConteo()});
                return true;
            } catch (Exception e) {
                log.log(Level.SEVERE, "Ocurrio un error al eliminar la referencia. Error [{0}]", e.getMessage());
                return false;
            }
        } else {
            return false;
        }
    }

    public void buscarReferencia() {
        detalle.clear();
        if (parametroBusqueda != null && !parametroBusqueda.isEmpty()) {
            for (DetalleConteoDTO d : detalleFull) {
                if (detalle.size() < 17) {
                    if (d.getReferencia().contains(parametroBusqueda)) {
                        detalle.add(d);
                    } else if (baruGenericMBean.obtenerNombreReferencia(d.getReferencia()).toUpperCase().contains(parametroBusqueda.toUpperCase())) {
                        detalle.add(d);
                    } else if (d.getCantidad().toString().contains(parametroBusqueda.toUpperCase())) {
                        detalle.add(d);
                    }
                }
            }
        } else {
            detalle = new ArrayList<>(detalleFull.subList(0, detalleFull.size() > 16 ? 16 : detalleFull.size()));
        }
    }

    public String finalizarConteo() {
        if (validarCondicionesAcceso()) {
            List<ResultadoConteo> resultados = new ArrayList<>();
            List<Object[]> filas = detalleConteoFacade.obtenerDetalleConteoAgrupado(conteosSessionMBean.getIdConteo());
            ConteoTienda conteo = conteoTiendaFacade.find(conteosSessionMBean.getIdConteo());

            if (filas != null && !filas.isEmpty()) {
                for (Object[] columna : filas) {
                    ResultadoConteo resultado = new ResultadoConteo();

                    resultado.setCantidad((Integer) columna[1]);
                    resultado.setIdConteo(conteo);
                    resultado.setReferencia((String) columna[0]);

                    try {
                        resultadoConteoFacade.create(resultado);
//                        log.log(Level.INFO, "Agregando resultado a la referencia [{0}] con cantidad [{1}], para el conteo con id [{2}]",
//                                new Object[]{resultado.getReferencia(), resultado.getCantidad(), conteo.getIdConteo()});
                        resultados.add(resultado);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al agregar el registro de resultado para la referencia [{0}]. Error [{1}]", new Object[]{resultado.getReferencia(), e.getMessage()});
                    }
                }
                conteo.setEstado("F");
                //actualiza el registro en la bitacora de inventario por ubicacion, si aplica
                cerrarRegistroBitacora(conteo, 0, 0, 0);
                try {
                    conteoTiendaFacade.edit(conteo);
                    log.log(Level.INFO, "Se marco el conteo con id [{0}] como FINALIZADO", conteo.getIdConteo());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al actualizar el conteo con id [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                    mostrarMensaje("Error", "Error al finalizar el conteo, comuniquese con sistemas", true, false, false);
                    return null;
                }

                List<DiferenciasConteo> diferencias = new ArrayList<>();
                Map<String, Long> saldos = new HashMap<>();
                //Carga los saldos de mercancia para la venta desde SAP
                List<SaldoItemInventario> saldoSAP = new ArrayList<>();

                //Valida si el conteo es de una ubicación especifica solo incluye lo que encuentra en esta
                if (conteo.getCasilla()) {
                    //Cargar saldos de la ubicación
                    List<SaldoItemInventario> saldoSAPVenta = new ArrayList<>();
                    if (conteo.getVenta()) {
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            for (SaldoUbicacion saldoUbicacion : saldoUbicacionFacade.obtenerSaldoProveedor(conteo.getTienda(), conteo.getUbicacion(), conteo.getProveedor())) {
                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();

                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoUbicacion.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoUbicacion.getOnHandQty());

                                saldoSAPVenta.add(tmpSaldo);
                            }
                        } else {
                            for (SaldoUbicacion saldoUbicacion : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen(conteo.getTienda(), conteo.getUbicacion())) {
                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();

                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoUbicacion.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoUbicacion.getOnHandQty());

                                saldoSAPVenta.add(tmpSaldo);
                            }
                        }

                        for (SaldoItemInventario s : saldoSAPVenta) {
                            String ref = s.getSaldoItemInventarioPK().getItemCode();
                            Long saldo = s.getOnHand().longValue();
                            if (saldos.get(ref) == null) {
                                saldos.put(ref, saldo);
                                saldoSAP.add(s);
                            } else {
                                saldos.put(ref, saldo + saldos.get(ref));
                            }
                        }
                    }

                    //Carga los saldos de mercancia de clientes desde SAP y los agrega al saldo esperado
                    List<SaldoItemInventario> saldoSAPCliente = new ArrayList<>();
                    if (conteo.getCliente()) {
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            for (SaldoUbicacion saldoClientes : saldoUbicacionFacade.obtenerSaldoProveedor("CL" + conteo.getTienda(), conteo.getUbicacion(), conteo.getProveedor())) {
                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();

                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoClientes.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoClientes.getOnHandQty());

                                saldoSAPCliente.add(tmpSaldo);
                            }
                        } else {
                            for (SaldoUbicacion saldoClientes : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen("CL" + conteo.getTienda(), conteo.getUbicacion())) {
                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();

                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoClientes.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoClientes.getOnHandQty());

                                saldoSAPCliente.add(tmpSaldo);
                            }
                        }

                        for (SaldoItemInventario s : saldoSAPCliente) {
                            String ref = s.getSaldoItemInventarioPK().getItemCode();
                            Long saldo = s.getOnHand().longValue();
                            if (saldos.get(ref) == null) {
                                saldos.put(ref, saldo);
                                saldoSAP.add(s);
                            } else {
                                saldos.put(ref, saldo + saldos.get(ref));
                            }
                        }
                    }

                    //Valida si el conteo incluye mercancia en uso (dotacion). Si no la incluye, no consulta este almacen
                    List<SaldoItemInventario> saldoSAPDotacion = new ArrayList<>();
                    if (conteo.getDotacion()) {
                        //Carga los saldos de mercancia en uso (dotacion) desde SAP y los agrega al saldo esperado
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            for (SaldoUbicacion saldoDotacion : saldoUbicacionFacade.obtenerSaldoProveedor("MU" + conteo.getTienda(), conteo.getUbicacion(), conteo.getProveedor())) {

                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();
                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoDotacion.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoDotacion.getOnHandQty());

                                saldoSAPDotacion.add(tmpSaldo);
                            }
                        } else {
                            for (SaldoUbicacion saldoDotacion : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen("MU" + conteo.getTienda(), conteo.getUbicacion())) {
                                SaldoItemInventario tmpSaldo = new SaldoItemInventario();

                                tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                                tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoDotacion.getItemCode());
                                tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                                tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                                tmpSaldo.setOnHand(saldoDotacion.getOnHandQty());

                                saldoSAPDotacion.add(tmpSaldo);
                            }
                        }

                        for (SaldoItemInventario s : saldoSAPDotacion) {
                            String ref = s.getSaldoItemInventarioPK().getItemCode();
                            Long saldo = s.getOnHand().longValue();
                            if (saldos.get(ref) == null) {
                                saldos.put(ref, saldo);
                                saldoSAP.add(s);
                            } else {
                                saldos.put(ref, saldo + saldos.get(ref));
                            }
                        }
                    }
                } else {
                    if (conteo.getVenta()) {
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            saldoSAP = saldoItemInventarioFacade.obtenerSaldoProveedor(conteo.getTienda(), conteo.getProveedor());
                        } else {
                            saldoSAP = saldoItemInventarioFacade.obtenerSaldoAlmacen(conteo.getTienda());
                        }

                        for (SaldoItemInventario saldoItem : saldoSAP) {
                            String ref = saldoItem.getSaldoItemInventarioPK().getItemCode();
                            Long saldo = saldoItem.getOnHand().longValue();
                            if (saldos.get(ref) == null) {
                                saldos.put(ref, saldo);
                            } else {
                                saldos.put(ref, saldo + saldos.get(ref));
                            }
                        }
                    }

                    //Carga los saldos de mercancia de clientes desde SAP y los agrega al saldo esperado
                    if (conteo.getCliente()) {
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            for (SaldoItemInventario saldoClientes : saldoItemInventarioFacade.obtenerSaldoProveedor("CL" + conteo.getTienda(), conteo.getProveedor())) {
                                String ref = saldoClientes.getSaldoItemInventarioPK().getItemCode();
                                Long saldo = saldoClientes.getOnHand().longValue();
                                if (saldos.get(ref) == null) {
                                    saldos.put(ref, saldo);
                                    saldoSAP.add(saldoClientes);
                                } else {
                                    saldos.put(ref, saldo + saldos.get(ref));
                                }
                            }
                        } else {
                            for (SaldoItemInventario saldoClientes : saldoItemInventarioFacade.obtenerSaldoAlmacen("CL" + conteo.getTienda())) {
                                String ref = saldoClientes.getSaldoItemInventarioPK().getItemCode();
                                Long saldo = saldoClientes.getOnHand().longValue();
                                if (saldos.get(ref) == null) {
                                    saldos.put(ref, saldo);
                                    saldoSAP.add(saldoClientes);
                                } else {
                                    saldos.put(ref, saldo + saldos.get(ref));
                                }
                            }
                        }
                    }

                    //Valida si el conteo incluye mercancia en uso (dotacion). Si no la incluye, no consulta este almacen
                    if (conteo.getDotacion()) {
                        //Carga los saldos de mercancia en uso (dotacion) desde SAP y los agrega al saldo esperado
                        if (conteo.getIdTipoConteo().getNombre().contains("Proveedor")) {
                            for (SaldoItemInventario saldoDotacion : saldoItemInventarioFacade.obtenerSaldoProveedor("MU" + conteo.getTienda(), conteo.getProveedor())) {
                                String ref = saldoDotacion.getSaldoItemInventarioPK().getItemCode();
                                Long saldo = saldoDotacion.getOnHand().longValue();
                                if (saldos.get(ref) == null) {
                                    saldos.put(ref, saldo);
                                    saldoSAP.add(saldoDotacion);
                                } else {
                                    saldos.put(ref, saldo + saldos.get(ref));
                                }
                            }
                        } else {
                            for (SaldoItemInventario saldoDotacion : saldoItemInventarioFacade.obtenerSaldoAlmacen("MU" + conteo.getTienda())) {
                                String ref = saldoDotacion.getSaldoItemInventarioPK().getItemCode();
                                Long saldo = saldoDotacion.getOnHand().longValue();
                                if (saldos.get(ref) == null) {
                                    saldos.put(ref, saldo);
                                    saldoSAP.add(saldoDotacion);
                                } else {
                                    saldos.put(ref, saldo + saldos.get(ref));
                                }
                            }
                        }
                    }
                }

                for (SaldoItemInventario saldo : saldoSAP) {
                    if (saldo.getOnHandAsInt() != saldos.get(saldo.getSaldoItemInventarioPK().getItemCode())) {
                        log.log(Level.WARNING, "El saldo es diferente {2} {0}/{1}", new Object[]{saldo.getOnHandAsInt(), saldos.get(saldo.getSaldoItemInventarioPK().getItemCode()),
                            saldo.getSaldoItemInventarioPK().getItemCode()});
                    }
                    saldo.setOnHand(new BigDecimal(saldos.get(saldo.getSaldoItemInventarioPK().getItemCode())));
                }

                switch (conteo.getIdTipoConteo().getIdTipoConteo()) {
                    //Si el tipo de conteo es igual a 1, quiere decir que es un conteo completo
                    case 1:
                        //Agrego los saldos esperados como diferencias
                        for (SaldoItemInventario saldo : saldoSAP) {
                            DiferenciasConteo dif = new DiferenciasConteo(null, saldo.getSaldoItemInventarioPK().getItemCode(), saldo.getOnHand().longValue(), 0L, null, false, conteo);
                            diferencias.add(dif);
                        }
                        //Agrego los valores encontrados a las diferencias
                        for (ResultadoConteo resultado : resultados) {
                            boolean encontrado = false;
                            for (int i = 0; i < diferencias.size(); i++) {
                                DiferenciasConteo diferencia = diferencias.get(i);
                                if (diferencia.getReferencia().equals(resultado.getReferencia())) {
                                    encontrado = true;
                                    diferencia.setEncontrado(resultado.getCantidad());
                                    break;
                                }
                            }
                            if (!encontrado) {
                                DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                                diferencias.add(dif);
                            }
                        }
                        //Elimina los registros en los que los valores esperado y encontrado son iguales
                        for (int i = 0; i < diferencias.size(); i++) {
                            DiferenciasConteo dif = diferencias.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                diferencias.remove(i);
                                System.out.println("Se elimina la referencia [" + dif.getReferencia() + "]");
                                i = -1;
                            }
                        }
                        for (DiferenciasConteo diferencia : diferencias) {
                            try {
                                diferenciasConteoFacade.create(diferencia);
//                                log.log(Level.INFO, "Se agrego registro de diferencias para el conteo con id [{0}]", conteo.getIdConteo());
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el registro de diferencia para el conteo [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                            }
                        }
                        break;
                    //Si el tido de conteo es igual a 2, quiere decir que es un conteo parcial
                    case 2:
                        //Agrego los resultados como saldos
                        for (ResultadoConteo resultado : resultados) {
                            DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                            diferencias.add(dif);
                        }
                        //Agrego los saldos para las referencias encontradas a las diferencias
                        for (SaldoItemInventario saldo : saldoSAP) {
                            for (int i = 0; i < diferencias.size(); i++) {
                                DiferenciasConteo diferencia = diferencias.get(i);
                                if (diferencia.getReferencia().equals(saldo.getSaldoItemInventarioPK().getItemCode())) {
                                    diferencia.setEsperado(saldo.getOnHand().longValue());
                                    break;
                                }
                            }
                        }
                        //Elimina las diferencias en las que los valores encontrado y esperado son iguales
                        for (int i = 0; i < diferencias.size(); i++) {
                            DiferenciasConteo dif = diferencias.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                diferencias.remove(i);
                                i = -1;
                            }
                        }
                        //Inserta los registros en la base de datos
                        for (DiferenciasConteo diferencia : diferencias) {
                            try {
                                diferenciasConteoFacade.create(diferencia);
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el registro de diferencia para el conteo [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                            }
                        }
                        break;
                    case 3:
                        for (SaldoItemInventario saldo : saldoSAP) {
                            DiferenciasConteo dif = new DiferenciasConteo(null, saldo.getSaldoItemInventarioPK().getItemCode(), saldo.getOnHand().longValue(), 0L, null, false, conteo);
                            diferencias.add(dif);
                        }
                        //Agrego los valores encontrados a las diferencias
                        for (ResultadoConteo resultado : resultados) {
                            boolean encontrado = false;
                            for (int i = 0; i < diferencias.size(); i++) {
                                DiferenciasConteo diferencia = diferencias.get(i);
                                if (diferencia.getReferencia().equals(resultado.getReferencia())) {
                                    encontrado = true;
                                    diferencia.setEncontrado(resultado.getCantidad());
                                    break;
                                }
                            }
                            if (!encontrado) {
                                DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                                diferencias.add(dif);
                            }
                        }
                        //Elimina los registros en los que los valores esperado y encontrado son iguales
                        for (int i = 0; i < diferencias.size(); i++) {
                            DiferenciasConteo dif = diferencias.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                diferencias.remove(i);
                                i = -1;
                            }
                        }
                        break;
                    case 4:
                        for (SaldoItemInventario saldo : saldoSAP) {
                            DiferenciasConteo dif = new DiferenciasConteo(null, saldo.getSaldoItemInventarioPK().getItemCode(), saldo.getOnHand().longValue(), 0L, null, false, conteo);
                            diferencias.add(dif);
                        }
                        //Agrego los valores encontrados a las diferencias
                        for (ResultadoConteo resultado : resultados) {
                            boolean encontrado = false;
                            for (int i = 0; i < diferencias.size(); i++) {
                                DiferenciasConteo diferencia = diferencias.get(i);
                                if (diferencia.getReferencia().equals(resultado.getReferencia())) {
                                    encontrado = true;
                                    diferencia.setEncontrado(resultado.getCantidad());
                                    break;
                                }
                            }
                            if (!encontrado) {
                                DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                                diferencias.add(dif);
                            }
                        }
                        //Elimina los registros en los que los valores esperado y encontrado son iguales
                        for (int i = 0; i < diferencias.size(); i++) {
                            DiferenciasConteo dif = diferencias.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                diferencias.remove(i);
                                i = -1;
                            }
                        }
                        //Inserta los registros en la base de datos
                        for (DiferenciasConteo diferencia : diferencias) {
                            try {
                                diferenciasConteoFacade.create(diferencia);
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el registro de diferencia para el conteo [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                            }
                        }
                        break;
                    default:
                        break;
                }
                log.log(Level.INFO, "Se registraron [{0}], diferencias", diferencias.size());
                enviarNotificacionEmail(diferencias, conteo);
                return "conteos";
            } else {
                mostrarMensaje("Error", "No se encontraron datos de conteos para poder finalizar", true, false, false);
                log.log(Level.SEVERE, "No se encontraron datos de conteos para poder finalizar");
                return null;
            }
        } else {
            mostrarMensaje("Error", "No se pueden finalizar el conteo debido a que este ya fue finalizado", true, false, false);
            log.log(Level.SEVERE, "No se pueden finalizar el conteo debido a que este ya fue finalizado");
            return null;
        }
    }

    private void cerrarRegistroBitacora(ConteoTienda conteo, int diferencias, int referencias, int unidades) {
        StringBuilder sb = new StringBuilder();
        if (conteo.getCliente()) {
            sb.append("CL");
            sb.append(conteo.getTienda());
            sb.append(conteo.getUbicacion());
            BitacoraInventarioUbicacion bitacora = bitacoraFacade.findOpenByBinCode(sb.toString());
            if (bitacora != null) {
                bitacora.setFechaFinalizacionConteo(new Date());
                bitacora.setUsuarioFinalizador(sessionMBean.getUsuario());
                bitacora.setReferencias(referencias);
                bitacora.setUnidades(unidades);
                bitacora.setDiferencias(diferencias);
                try {
                    bitacoraFacade.edit(bitacora);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al actualizar el registro en la bitacora de inventario de ubicacion. ", e);
                }
            } else {
                log.log(Level.WARNING, "No se encontro un registro de bitacora abierto para la ubicacion {0}", sb.toString());
            }
        }
        if (conteo.getDotacion()) {
            sb = new StringBuilder();
            sb.append("MU");
            sb.append(conteo.getTienda());
            sb.append(conteo.getUbicacion());
            BitacoraInventarioUbicacion bitacora = bitacoraFacade.findOpenByBinCode(sb.toString());
            if (bitacora != null) {
                bitacora.setFechaFinalizacionConteo(new Date());
                bitacora.setUsuarioFinalizador(sessionMBean.getUsuario());
                bitacora.setReferencias(referencias);
                bitacora.setUnidades(unidades);
                bitacora.setDiferencias(diferencias);
                try {
                    bitacoraFacade.edit(bitacora);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al actualizar el registro en la bitacora de inventario de ubicacion. ", e);
                }
            } else {
                log.log(Level.WARNING, "No se encontro un registro de bitacora abierto para la ubicacion {0}", sb.toString());
            }
        }
        if (conteo.getVenta()) {
            sb = new StringBuilder();
            sb.append(conteo.getTienda());
            sb.append(conteo.getUbicacion());
            BitacoraInventarioUbicacion bitacora = bitacoraFacade.findOpenByBinCode(sb.toString());
            if (bitacora != null) {
                bitacora.setFechaFinalizacionConteo(new Date());
                bitacora.setUsuarioFinalizador(sessionMBean.getUsuario());
                bitacora.setReferencias(referencias);
                bitacora.setUnidades(unidades);
                bitacora.setDiferencias(diferencias);
                try {
                    bitacoraFacade.edit(bitacora);
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Ocurrio un error al actualizar el registro en la bitacora de inventario de ubicacion. ", e);
                }
            } else {
                log.log(Level.WARNING, "No se encontro un registro de bitacora abierto para la ubicacion {0}", sb.toString());
            }
        }
    }

    private void enviarNotificacionEmail(List<DiferenciasConteo> diferencias, ConteoTienda conteo) {
        Collections.sort(diferencias, new Comparator<DiferenciasConteo>() {
            @Override
            public int compare(DiferenciasConteo t, DiferenciasConteo t1) {
                return t.getReferencia().compareTo(t1.getReferencia());
            }
        });

        MailMessageDTO mail = new MailMessageDTO();
        mail.setFrom("Conteos inventario <conteos@matisses.co>");
        mail.setSubject("Conteo de inventario");
        mail.addToAddress("sistemas@matisses.co");

        Map<String, String> params = new HashMap<>();
        params.put("idConteo", conteo.getIdConteo().toString());
        params.put("usuario", sessionMBean.getUsuario().toUpperCase());
        params.put("almacen", conteo.getTienda());
        params.put("texto1", conteo.getUbicacion() != null ? conteo.getUbicacion() : "");
        params.put("diferencias", String.valueOf(diferencias.size()));
        params.put("tipo", "finalizado");

        if (diferencias != null && !diferencias.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("<table style=\"width: 50%; border-collapse: collapse\">");
            sb.append("<tr>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Imagen</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Referencia</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Encontrado</th>");
            sb.append("<th style=\"padding: 10px; border: 1px solid #DDDDDD\">Esperado</th>");
            sb.append("</tr>");

            for (DiferenciasConteo d : diferencias) {
                sb.append("<tr>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\"><img style=\"width: 103px; height: 83px;\" src=\"");
                sb.append(imagenProductoMBean.obtenerUrlProducto(d.getReferencia(), true));
                sb.append("\"></img></td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                sb.append(baruGenericMBean.convertirARefCorta(d.getReferencia()));
                sb.append("</td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                sb.append(d.getEncontrado());
                sb.append("</td>");
                sb.append("<td style=\"border: 1px solid #DDDDDD\">");
                sb.append(d.getEsperado());
                sb.append("</td>");
                sb.append("</tr>");
            }

            sb.append("</table>");
            params.put("tablaDiferencias", sb.toString());
        } else {
            params.put("tablaDiferencias", "");
        }

        try {
            emailSender.sendMail(mail, SendHTMLEmailMBean.MessageTemplate.inventarios, params, null);
        } catch (Exception e) {
            log.log(Level.SEVERE, "No fue posible enviar el correo de notificacion para el inventario.", e);
            return;
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
