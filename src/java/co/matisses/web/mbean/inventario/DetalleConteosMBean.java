package co.matisses.web.mbean.inventario;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoItemInventarioPK;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.web.entity.ConteoTienda;
import co.matisses.persistence.web.entity.DetalleConteo;
import co.matisses.persistence.web.entity.DiferenciasConteo;
import co.matisses.persistence.web.entity.ResultadoConteo;
import co.matisses.persistence.web.facade.ConteoTiendaFacade;
import co.matisses.persistence.web.facade.DetalleConteoFacade;
import co.matisses.persistence.web.facade.DiferenciasConteoFacade;
import co.matisses.persistence.web.facade.ResultadoConteoFacade;
import co.matisses.web.dto.DetalleConteoDTO;
import co.matisses.web.dto.DiferenciasConteoDTO;
import co.matisses.web.dto.MailMessageDTO;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.ImagenProductoMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.email.SendHTMLEmailMBean;
import co.matisses.web.mbean.session.ConteosSessionMBean;
import java.io.Serializable;
import java.math.BigDecimal;
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
@Named(value = "detalleConteosMBean")
public class DetalleConteosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private ConteosSessionMBean conteosSessionMBean;
    @Inject
    private ImagenProductoMBean imagenProductoMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    @Inject
    private SendHTMLEmailMBean emailSender;
    private static final Logger log = Logger.getLogger(DetalleConteosMBean.class.getSimpleName());
    private int pagina;
    private int totalPaginas;
    private int conteosPagina;
    private Integer idConteo;
    private Integer idDiferencia;
    private String parametroBusqueda;
    private String filtrarPor;
    private String sortOrder;
    private String referencia;
    private String comentario;
    private List<Integer> paginas;
    private List<DiferenciasConteoDTO> diferencias;
    private List<DetalleConteoDTO> detalle;
    @EJB
    private DiferenciasConteoFacade diferenciasConteoFacade;
    @EJB
    private DetalleConteoFacade detalleConteoFacade;
    @EJB
    private ResultadoConteoFacade resultadoConteoFacade;
    @EJB
    private ConteoTiendaFacade conteoTiendaFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    public DetalleConteosMBean() {
        pagina = 1;
        totalPaginas = 0;
        conteosPagina = 12;
        filtrarPor = "Seleccione";
        sortOrder = "ASC";
        paginas = new ArrayList<>();
        diferencias = new ArrayList<>();
        detalle = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        validarSession();
    }

    private void validarSession() {
        if (conteosSessionMBean == null || conteosSessionMBean.getIdConteo() == null || conteosSessionMBean.getIdConteo() == 0) {
            mostrarMensaje("Error", "El usuario no ha seleccionado una conteo para poder trabajar", true, false, false);
            log.log(Level.SEVERE, "El usuario no ha seleccionado una conteo para poder trabajar");
            return;
        } else {
            idConteo = conteosSessionMBean.getIdConteo();
            obtenerDiferencias();
        }
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public ConteosSessionMBean getConteosSessionMBean() {
        return conteosSessionMBean;
    }

    public Integer getIdConteo() {
        return idConteo;
    }

    public void setIdConteo(Integer idConteo) {
        this.idConteo = idConteo;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getFiltrarPor() {
        return filtrarPor;
    }

    public String getOrdenarPor() {
        switch (filtrarPor) {
            case "Falta":
                return "Falta";
            case "Sobra":
                return "Sobra";
            case "Esperado":
                return "Esperado";
            case "Encontrado":
                return "Encontrado";
            default:
                return "Seleccione";
        }
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public List<DiferenciasConteoDTO> getDiferencias() {
        return diferencias;
    }

    public void setDiferencias(List<DiferenciasConteoDTO> diferencias) {
        this.diferencias = diferencias;
    }

    public int getConteosPagina() {
        return conteosPagina;
    }

    public String getSizePage() {
        if (conteosPagina != 100000) {
            return Integer.toString(conteosPagina);
        } else {
            return "Todos";
        }
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public List<DetalleConteoDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleConteoDTO> detalle) {
        this.detalle = detalle;
    }

    private void obtenerDiferencias() {
        diferencias.clear();

        int totalRegistros = diferenciasConteoFacade.consultarTotalRegistros(idConteo, baruGenericMBean.completarReferencia(parametroBusqueda), filtrarPor);
        totalPaginas = (totalRegistros / conteosPagina) + (totalRegistros % conteosPagina > 0 ? 1 : 0);
        if (pagina > totalPaginas) {
            pagina = totalPaginas;
        }
        construirListaPaginas();
        List<DiferenciasConteo> counting = diferenciasConteoFacade.consultarConteos(pagina, conteosPagina, idConteo, baruGenericMBean.completarReferencia(parametroBusqueda), filtrarPor, sortOrder);
        if (counting != null && !counting.isEmpty()) {
            for (DiferenciasConteo d : counting) {
                DiferenciasConteoDTO dif = new DiferenciasConteoDTO(d.getIdConteo().getIdConteo(), d.getEsperado(), d.getEncontrado(),
                        d.getIdDiferenciaConteo(), d.getReferencia(), d.getComentarios() == null ? "" : d.getComentarios(),
                        d.getTipo() == null ? "" : d.getTipo(), d.getResuelta() == null ? false : d.getResuelta());

                if (dif.getTipo() == null || dif.getTipo().isEmpty()) {
                    if (dif.getEsperado() - dif.getEncontrado() < 0) {
                        dif.setTipo("S");
                    } else {
                        dif.setTipo("F");
                    }
                }

                diferencias.add(dif);
            }
        }
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void ordenarProductos() {
        filtrarPor = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        log.log(Level.INFO, "Ordenando por [{0}]", filtrarPor);
        obtenerDiferencias();
    }

    public void alternarOrden() {
        log.log(Level.INFO, "Alternando orden. Antes [{0}]", sortOrder);
        if (sortOrder.equals("ASC")) {
            sortOrder = "DESC";
        } else {
            sortOrder = "ASC";
        }
        log.log(Level.INFO, "Ahora [{0}]", sortOrder);
        obtenerDiferencias();
    }

    public void cambiarSizePage() {
        conteosPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("sizePage"));
        log.log(Level.INFO, "Cambiando # de conteos x pagina a [{0}]", conteosPagina);
        obtenerDiferencias();
    }

    public void buscarConteo() {
        pagina = 1;
        obtenerDiferencias();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerDiferencias();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerDiferencias();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerDiferencias();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerDiferencias();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerDiferencias();
    }

    public void obtenerDetalleReferencia() {
        detalle = new ArrayList<>();
        referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("referencia");
        idDiferencia = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDiferencia"));

        DiferenciasConteo dif = diferenciasConteoFacade.find(idDiferencia.longValue());

        if (dif != null && dif.getIdDiferenciaConteo() != null && dif.getIdDiferenciaConteo() != 0) {
            comentario = dif.getComentarios();
        }

        List<DetalleConteo> detail = detalleConteoFacade.obtenerDetalleReferencia(idConteo, referencia);

        for (DetalleConteo d : detail) {
            detalle.add(new DetalleConteoDTO(d.getIdConteo().getIdConteo(), 0, d.getUsuario(), d.getReferencia(), d.getFecha()));
        }
    }

    public void guardarComentario() {
        DiferenciasConteo dif = diferenciasConteoFacade.find(idDiferencia.longValue());

        if (dif != null && dif.getIdDiferenciaConteo() != null && dif.getIdDiferenciaConteo() != 0) {
            dif.setComentarios(comentario);

            try {
                diferenciasConteoFacade.edit(dif);
                log.log(Level.INFO, "Se almaceno correctamente el comentario de la diferencia con id {0}", idDiferencia);
            } catch (Exception e) {
                log.log(Level.SEVERE, "Error al guardar el comentario de la diferencia con id {0}. Error {1}", new Object[]{idDiferencia, e.getMessage()});
                return;
            }
        }
    }

    public String reprocesarConteo() {
        ConteoTienda conteo = conteoTiendaFacade.find(idConteo);

        if (conteo != null && conteo.getIdConteo() != null && conteo.getIdConteo() != 0) {
            final long MILLSECS_PER_DAY = 24 * 60 * 60 * 1000;

            long diferencia = (new Date().getTime() - conteo.getFecha().getTime()) / MILLSECS_PER_DAY;

            if (diferencia > 2) {
                mostrarMensaje("Error", "El inventario no se puede re procesar, debido a que ya han transcurrido más de 2 días desde que se hizo.", true, false, false);
                log.log(Level.SEVERE, "El inventario no se puede re procesar, debido a que ya han transcurrido mas de 2 dias desde que se hizo");
                return null;
            }

            log.log(Level.INFO, "Inicia reproceso del conteo con id [{0}]", idConteo);

            log.log(Level.INFO, "Paso 1. Se eliminan las diferencias de base de datos");
            try {
                diferenciasConteoFacade.eliminarDiferenciasConteo(idConteo);
                log.log(Level.INFO, "Se eliminaron las diferencias del conteo");
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                mostrarMensaje("Error", "No se puede reprocesar el conteo debido a un problema con la tabla de diferencias", true, false, false);
                return null;
            }

            log.log(Level.INFO, "Paso 2. Se eliminan los resultados de base de datos");
            try {
                resultadoConteoFacade.eliminarResultadosConteo(idConteo);
                log.log(Level.INFO, "Se eliminaron los resultados del conteo");
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                mostrarMensaje("Error", "No se puede reprocesar el conteo debido a un problema con la tabla de resultados", true, false, false);
                return null;
            }

            log.log(Level.INFO, "Paso 3. Se coloca el conteo como pendiente");
            try {
                conteo.setEstado("P");

                conteoTiendaFacade.edit(conteo);
                log.log(Level.INFO, "Se modifico el estado del conteo a pendiente");
            } catch (Exception e) {
                log.log(Level.SEVERE, e.getMessage());
                mostrarMensaje("Error", "No se puede reprocesar el conteo debido a un problema con la actualizacion del conteo", true, false, false);
                return null;
            }

            log.log(Level.INFO, "Paso 4. Se manda el conteo a reprocesar");

            return finalizarConteo();
        }
        return "";
    }

    private ConteoTienda validarCondicionesAcceso() {
        //En este metodo se valida que el conteo no se haya cerrado, y asi evitar que los usuarios sigan agregando o eliminando referencias
        ConteoTienda c = conteoTiendaFacade.find(idConteo);
        if (c != null && c.getIdConteo() != null && c.getIdConteo() != 0 && c.getEstado().equals("P")) {
            List<ResultadoConteo> resultados = resultadoConteoFacade.obtenerResultadoConteo(idConteo);
            if (resultados != null && !resultados.isEmpty()) {
                return null;
            } else {
                return c;
            }
        } else {
            return null;
        }
    }

    public String finalizarConteo() {
        ConteoTienda conteo = validarCondicionesAcceso();
        if (conteo != null) {
            List<ResultadoConteo> resultados = new ArrayList<>();
            List<Object[]> filas = detalleConteoFacade.obtenerDetalleConteoAgrupado(idConteo);
            if (filas != null && !filas.isEmpty()) {
                for (Object[] columna : filas) {
                    ResultadoConteo resultado = new ResultadoConteo();

                    resultado.setCantidad((Integer) columna[1]);
                    resultado.setIdConteo(conteo);
                    resultado.setReferencia((String) columna[0]);

                    try {
                        resultadoConteoFacade.create(resultado);
                        resultados.add(resultado);
                    } catch (Exception e) {
                        log.log(Level.SEVERE, "Ocurrio un error al agregar el registro de resultado para la referencia [{0}]. Error [{1}]", new Object[]{resultado.getReferencia(), e.getMessage()});
                    }
                }

                conteo.setEstado("F");

                try {
                    conteoTiendaFacade.edit(conteo);
                    log.log(Level.INFO, "Se marco el conteo con id [{0}] como FINALIZADO", conteo.getIdConteo());
                } catch (Exception e) {
                    log.log(Level.SEVERE, "Error al actualizar el conteo con id [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                    mostrarMensaje("Error", "Error al finalizar el conteo, comuniquese con sistemas", true, false, false);
                    return null;
                }

                List<DiferenciasConteo> difs = new ArrayList<>();

                Map<String, Long> saldos = new HashMap<>();

                //Carga los saldos de mercancia para la venta desde SAP
                List<SaldoItemInventario> saldoSAP = new ArrayList<>();
                //Valida si el conteo es de una ubicación especifica solo incluye lo que encuentra en esta
                if (conteo.getCasilla()) {
                    //Cargar saldos de la ubicación
                    List<SaldoItemInventario> saldoSAPVenta = new ArrayList<>();
                    if (conteo.getVenta()) {

                        for (SaldoUbicacion saldoUbicacion : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen(conteo.getTienda(), conteo.getUbicacion())) {
                            SaldoItemInventario tmpSaldo = new SaldoItemInventario();
                            tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                            tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoUbicacion.getItemCode());
                            tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                            tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                            tmpSaldo.setOnHand(saldoUbicacion.getOnHandQty());
                            saldoSAPVenta.add(tmpSaldo);
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
                        for (SaldoUbicacion saldoClientes : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen("CL" + conteo.getTienda(), conteo.getUbicacion())) {
                            SaldoItemInventario tmpSaldo = new SaldoItemInventario();
                            tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                            tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoClientes.getItemCode());
                            tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                            tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                            tmpSaldo.setOnHand(saldoClientes.getOnHandQty());
                            saldoSAPCliente.add(tmpSaldo);
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
                        for (SaldoUbicacion saldoDotacion : saldoUbicacionFacade.obtenerSaldoUbicacionAlmacen("MU" + conteo.getTienda(), conteo.getUbicacion())) {
                            SaldoItemInventario tmpSaldo = new SaldoItemInventario();
                            tmpSaldo.setSaldoItemInventarioPK(new SaldoItemInventarioPK());
                            tmpSaldo.getSaldoItemInventarioPK().setItemCode(saldoDotacion.getItemCode());
                            tmpSaldo.getSaldoItemInventarioPK().setWhsCode(new Almacen());
                            tmpSaldo.getSaldoItemInventarioPK().getWhsCode().setWhsCode(conteo.getTienda());
                            tmpSaldo.setOnHand(saldoDotacion.getOnHandQty());
                            saldoSAPDotacion.add(tmpSaldo);
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
                        saldoSAP = saldoItemInventarioFacade.obtenerSaldoAlmacen(conteo.getTienda());
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

                    //Valida si el conteo incluye mercancia en uso (dotacion). Si no la incluye, no consulta este almacen
                    if (conteo.getDotacion()) {
                        //Carga los saldos de mercancia en uso (dotacion) desde SAP y los agrega al saldo esperado
                        for (SaldoItemInventario saldoDotacion : saldoItemInventarioFacade.obtenerSaldoAlmacen("MU" + conteo.getTienda())) {
                            String ref = saldoDotacion.getSaldoItemInventarioPK().getItemCode();
                            Long saldo = saldoDotacion.getOnHand().longValue();
                            if (saldos.get(ref) == null) {
                                saldos.put(ref, saldo);
                                saldoSAP.add(saldoDotacion);
                                //agregar.add(saldoClientes);
                            } else {
                                saldos.put(ref, saldo + saldos.get(ref));
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
                            difs.add(dif);
                        }
                        //Agrego los valores encontrados a las diferencias
                        for (ResultadoConteo resultado : resultados) {
                            boolean encontrado = false;
                            for (int i = 0; i < difs.size(); i++) {
                                DiferenciasConteo diferencia = difs.get(i);
                                if (diferencia.getReferencia().equals(resultado.getReferencia())) {
                                    encontrado = true;
                                    diferencia.setEncontrado(resultado.getCantidad());
                                    break;
                                }
                            }
                            if (!encontrado) {
                                DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                                difs.add(dif);
                            }
                        }
                        //Elimina los registros en los que los valores esperado y encontrado son iguales
                        for (int i = 0; i < difs.size(); i++) {
                            DiferenciasConteo dif = difs.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                difs.remove(i);
                                System.out.println("Se elimina la referencia [" + dif.getReferencia() + "]");
                                i = -1;
                            }
                        }
                        for (DiferenciasConteo diferencia : difs) {
                            try {
                                diferenciasConteoFacade.create(diferencia);
//                                log.log(Level.INFO, "Se agrego registro de diferencias para el conteo con id [{0}]", conteo.getIdConteo());
                            } catch (Exception e) {
                                log.log(Level.SEVERE, "Ocurrio un error al crear el registro de diferencia para el conteo [{0}]. Error [{1}]", new Object[]{conteo.getIdConteo(), e.getMessage()});
                            }
                        }
                        break;
                    //Si el tido de conteo es igual a 2, quiere decir que es un conto parcial
                    case 2:
                        //Agrego los resultados como saldos
                        for (ResultadoConteo resultado : resultados) {
                            DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                            difs.add(dif);
                        }
                        //Agrego los saldos para las referencias encontradas a las diferencias
                        for (SaldoItemInventario saldo : saldoSAP) {
                            for (int i = 0; i < difs.size(); i++) {
                                DiferenciasConteo diferencia = difs.get(i);
                                if (diferencia.getReferencia().equals(saldo.getSaldoItemInventarioPK().getItemCode())) {
                                    diferencia.setEsperado(saldo.getOnHand().longValue());
                                    break;
                                }
                            }
                        }
                        //Elimina las diferencias en las que los valores encontrado y esperado son iguales
                        for (int i = 0; i < difs.size(); i++) {
                            DiferenciasConteo dif = difs.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                difs.remove(i);
                                i = -1;
                            }
                        }
                        //Inserta los registros en la base de datos
                        for (DiferenciasConteo diferencia : difs) {
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
                            difs.add(dif);
                        }
                        //Agrego los valores encontrados a las diferencias
                        for (ResultadoConteo resultado : resultados) {
                            boolean encontrado = false;
                            for (int i = 0; i < difs.size(); i++) {
                                DiferenciasConteo diferencia = difs.get(i);
                                if (diferencia.getReferencia().equals(resultado.getReferencia())) {
                                    encontrado = true;
                                    diferencia.setEncontrado(resultado.getCantidad());
                                    break;
                                }
                            }
                            if (!encontrado) {
                                DiferenciasConteo dif = new DiferenciasConteo(null, resultado.getReferencia(), 0L, resultado.getCantidad(), null, false, conteo);
                                difs.add(dif);
                            }
                        }
                        //Elimina los registros en los que los valores esperado y encontrado son iguales
                        for (int i = 0; i < difs.size(); i++) {
                            DiferenciasConteo dif = difs.get(i);
                            if (dif.getEncontrado() == dif.getEsperado()) {
                                difs.remove(i);
                                i = -1;
                            }
                        }
                        break;
                    default:
                        break;
                }
                log.log(Level.INFO, "Se registraron [{0}], diferencias", difs.size());
                enviarNotificacionEmail(difs, conteo);
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
        params.put("idConteo", idConteo.toString());
        params.put("usuario", sessionMBean.getUsuario().toUpperCase());
        params.put("almacen", conteo.getTienda());
        params.put("texto1", conteo.getUbicacion() != null ? conteo.getUbicacion() : "");
        params.put("diferencias", String.valueOf(diferencias.size()));
        params.put("tipo", "re-procesado");

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
