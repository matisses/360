package co.matisses.web.mbean.ccyga;

import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.facade.LaborCcygaFacade;
import co.matisses.persistence.web.facade.ProductoCcygaFacade;
import co.matisses.web.dto.DetalleHistoricoCcygaDTO;
import co.matisses.web.dto.GrupoProcesoDTO;
import co.matisses.web.dto.HistoricoProductoCcygaDTO;
import co.matisses.web.dto.ProcesoCcygaDTO;
import co.matisses.web.dto.ProductoCcygaDTO;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "informeHistoricoProductoMBean")
public class InformeHistoricoProductoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(InformeHistoricoProductoMBean.class.getSimpleName());
    private String referencia;
    private String idProducto;
    private String idProductoSeleccionado;
    private String idProcesoSeleccionado;
    private String sumaTiempoProducto;
    private Date fechaFinModificada;
    private HistoricoProductoCcygaDTO filaSeleccionada;
    private List<GrupoProcesoDTO> resultado;
    @EJB
    private ProductoCcygaFacade productoFacade;
    @EJB
    private LaborCcygaFacade laborFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;

    public InformeHistoricoProductoMBean() {
        resultado = new ArrayList<>();
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getIdProcesoSeleccionado() {
        return idProcesoSeleccionado;
    }

    public void setIdProcesoSeleccionado(String idProcesoSeleccionado) {
        this.idProcesoSeleccionado = idProcesoSeleccionado;
    }

    public String getSumaTiempoProducto() {
        return sumaTiempoProducto;
    }

    public void setSumaTiempoProducto(String sumaTiempoProducto) {
        this.sumaTiempoProducto = sumaTiempoProducto;
    }

    public Date getFechaFinModificada() {
        return fechaFinModificada;
    }

    public void setFechaFinModificada(Date fechaFinModificada) {
        this.fechaFinModificada = fechaFinModificada;
    }

    public String getIdProductoSeleccionado() {
        return idProductoSeleccionado;
    }

    public void setIdProductoSeleccionado(String idProductoSeleccionado) {
        this.idProductoSeleccionado = idProductoSeleccionado;
    }

    public HistoricoProductoCcygaDTO getFilaSeleccionada() {
        return filaSeleccionada;
    }

    public void setFilaSeleccionada(HistoricoProductoCcygaDTO filaSeleccionada) {
        this.filaSeleccionada = filaSeleccionada;
    }

    public List<GrupoProcesoDTO> getResultado() {
        return resultado;
    }

    public void setResultado(List<GrupoProcesoDTO> resultado) {
        this.resultado = resultado;
    }

    private void completarReferencia() {
        try {
            if (referencia != null && !referencia.isEmpty() && referencia.length() < 20 && referencia.length() > 5) {
                if (StringUtils.containsAny(referencia, "*", ".")) {
                    int pos = StringUtils.indexOfAny(referencia, "*", ".");
                    String proveedor = referencia.substring(0, pos);
                    String numItem = referencia.substring(pos + 1);
                    String relleno = StringUtils.repeat("0", 20 - proveedor.length() - numItem.length());
                    referencia = proveedor + relleno + numItem;
                }
            }
        } catch (Exception e) {
        }
    }

    public void buscar() {
        resultado = new ArrayList<>();
        filaSeleccionada = null;
        idProcesoSeleccionado = null;
        idProductoSeleccionado = null;
        completarReferencia();
        log.log(Level.INFO, "Buscando historico con parametros ref={0}, idProducto={1}", new Object[]{referencia, idProducto});
        List<Object[]> resultadoConsulta = new ArrayList<>();
        if (idProducto != null && !idProducto.trim().isEmpty()) {
            resultadoConsulta = productoFacade.obtenerHistoricoProducto(null, Integer.parseInt(idProducto));
        } else if (referencia != null && !referencia.trim().isEmpty()) {
            resultadoConsulta = productoFacade.obtenerHistoricoProducto(referencia, null);
        }
        log.log(Level.INFO, "La consulta de historico por producto retorno [{0}] filas", resultadoConsulta.size());
        for (Object[] fila : resultadoConsulta) {
            int col = 0;
            String ref = (String) fila[col++];
            Integer idProd = (Integer) fila[col++];
            Integer idProc = (Integer) fila[col++];
            String nombreProceso = (String) fila[col++];
            Date fecha = (Date) fila[col++];
            String codRevisado = (String) fila[col++];
            Date horaIni = (Date) fila[col++];
            Date horaFin = (Date) fila[col++];
            Integer idLabor = (Integer) fila[col++];

            boolean finalizado = true;
            if (horaFin == null) {
                horaFin = new Date();
                finalizado = false;
            }

            ProductoCcygaDTO productoDto = new ProductoCcygaDTO();
            productoDto.setIdProducto(idProd);
            productoDto.setReferencia(ref);

            ProcesoCcygaDTO procesoDto = new ProcesoCcygaDTO();
            procesoDto.setIdProceso(idProc);
            procesoDto.setNombre(nombreProceso);

            DetalleHistoricoCcygaDTO detalleDto = new DetalleHistoricoCcygaDTO();
            detalleDto.setCodRevisado(codRevisado);
            detalleDto.setFecha(fecha);
            detalleDto.setFin(horaFin);
            detalleDto.setIdLabor(idLabor);
            detalleDto.setInicio(horaIni);
            detalleDto.setFinalizado(finalizado);

            HistoricoProductoCcygaDTO dto = new HistoricoProductoCcygaDTO();
            dto.setProceso(procesoDto);
            dto.setProducto(productoDto);

            long tiempoLabor = (horaFin.getTime() - horaIni.getTime());
            if (resultado != null && !resultado.isEmpty()) {
                boolean existe = false;
                for (int i = 0; i < resultado.size(); i++) {
                    GrupoProcesoDTO g = resultado.get(i);

                    if (g.getProducto().equals(idProd)) {
                        boolean existeHistorial = false;
                        for (HistoricoProductoCcygaDTO h : g.getHistorico()) {
                            if (h.getProceso().getIdProceso().equals(idProc)) {
                                h.setEntradas(h.getEntradas() + 1);
                                h.setTiempoProceso(h.getTiempoProceso() + tiempoLabor);
                                h.getDetalle().add(detalleDto);
                                existeHistorial = true;
                                break;
                            } else {
                                existeHistorial = false;
                            }
                        }

                        if (!existeHistorial) {
                            g.getHistorico().add(new HistoricoProductoCcygaDTO(1, tiempoLabor, procesoDto, productoDto, detalleDto));
                        }
                        existe = true;
                        break;
                    } else {
                        existe = false;
                    }
                }

                if (!existe) {
                    GrupoProcesoDTO g = new GrupoProcesoDTO(idProd, ref, "0", new ArrayList<HistoricoProductoCcygaDTO>());

                    g.getHistorico().add(new HistoricoProductoCcygaDTO(1, tiempoLabor, procesoDto, productoDto, detalleDto));

                    resultado.add(g);
                }
            } else {
                GrupoProcesoDTO g = new GrupoProcesoDTO(idProd, ref, "0", new ArrayList<HistoricoProductoCcygaDTO>());

                g.getHistorico().add(new HistoricoProductoCcygaDTO(1, tiempoLabor, procesoDto, productoDto, detalleDto));

                resultado.add(g);
            }
        }

        calcularTiempoTotalProducto();
    }

    private void cargarDetalleFila() {
        log.log(Level.INFO, "Cargando informacion para el producto [{0}] y labor [{1}]", new Object[]{idProductoSeleccionado, idProcesoSeleccionado});

        for (GrupoProcesoDTO g : resultado) {
            if (g.getProducto().toString().equals(idProductoSeleccionado)) {
                for (HistoricoProductoCcygaDTO h : g.getHistorico()) {
                    if (h.getProceso().getIdProceso().toString().equals(idProcesoSeleccionado)) {
                        filaSeleccionada = h;
                        for (DetalleHistoricoCcygaDTO detalleDto : filaSeleccionada.getDetalle()) {
                            if (detalleDto.getNombreEmpleado() == null) {
                                detalleDto.setNombreEmpleado(empleadoFacade.consultarNombreEmpleado(detalleDto.getCodRevisado()));
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }

    }

    public void cargarInfoFila() {
        idProcesoSeleccionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProceso");
        idProductoSeleccionado = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idProducto");
        cargarDetalleFila();
    }

    public void calcularTiempoTotalProducto() {
        //sumaTiempoProducto = "";
        for (GrupoProcesoDTO dto : resultado) {
            long total = 0;
            for (HistoricoProductoCcygaDTO h : dto.getHistorico()) {
                total += h.getTiempoProceso();
            }
            long hours = (total / 1000 / 60 / 60);
            long mins = (total - (hours * 1000 * 60 * 60)) / 1000 / 60;

            dto.setTiempoTotal(String.format("%02d:%02d", hours, mins));
        }
    }

    public String formatearFechas(Date fecha, Date hora) {
        SimpleDateFormat sdfFecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm");
        return sdfFecha.format(fecha) + " " + sdfHora.format(hora);
    }

    public void actualizarHora(Integer idLabor) {
        log.log(Level.INFO, "Actualizando hora para labor [{0}] [{1}]", new Object[]{idLabor, fechaFinModificada});
        try {
            laborFacade.modificarFechaFinLabor(idLabor, fechaFinModificada);
            buscar();
            cargarDetalleFila();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al actualizar la fecha", e);
        }

    }
}
