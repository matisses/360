package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author ygil
 */
public class ProformaInvoiceDTO {

    private Integer idProforma;
    private Integer consecutivo;
    private Integer idPuertoLlegada;
    private Integer idPuertoSalida;
    private Integer idIncoterm;
    private Double cbmTotal;
    private Double valorTotal;
    private Double valorTotalDescuento;
    private Double valorPendiente;
    private Double valorTransferido;
    private String codProveedor;
    private String anio;
    private String usuario;
    private String estado;
    private String consecutivoVisible;
    private String comentario;
    private String tipoProducto;
    private String contenedoresConsolidados = "";
    private String terminosPago;
    private String terminosEntrega;
    private boolean primeraCarga = false;
    private Date fecha;
    private TipoMonedaDTO idTipoMoneda;
    private List<DetalleProformaDTO> detalleProforma;
    private List<TransaccionBancariaDTO> transacciones;
    private List<ContenedorProformaDTO> contenedores;

    public ProformaInvoiceDTO() {
        detalleProforma = new ArrayList<>();
        transacciones = new ArrayList<>();
        contenedores = new ArrayList<>();
    }

    public ProformaInvoiceDTO(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public ProformaInvoiceDTO(Integer idProforma, String codProveedor, Integer consecutivo, String anio) {
        this.idProforma = idProforma;
        this.codProveedor = codProveedor;
        this.consecutivo = consecutivo;
        this.anio = anio;
    }

    public ProformaInvoiceDTO(Integer idProforma, Integer consecutivo, Integer idPuertoLlegada, Integer idPuertoSalida, Integer idIncoterm,
            Double cbmTotal, Double valorTotal, Double valorTotalDescuento, Double valorPendiente, Double valorTransferido, String codProveedor,
            String anio, String usuario, String estado, String consecutivoVisible, String comentario, String tipoProducto,
            String terminosPago, String terminosEntrega, boolean primeraCarga, Date fecha, TipoMonedaDTO idTipoMoneda) {
        this.idProforma = idProforma;
        this.consecutivo = consecutivo;
        this.idPuertoLlegada = idPuertoLlegada;
        this.idPuertoSalida = idPuertoSalida;
        this.idIncoterm = idIncoterm;
        this.cbmTotal = cbmTotal;
        this.valorTotal = valorTotal;
        this.valorTotalDescuento = valorTotalDescuento;
        this.valorPendiente = valorPendiente;
        this.valorTransferido = valorTransferido;
        this.codProveedor = codProveedor;
        this.anio = anio;
        this.usuario = usuario;
        this.estado = estado;
        this.consecutivoVisible = consecutivoVisible;
        this.comentario = comentario;
        this.tipoProducto = tipoProducto;
        this.terminosPago = terminosPago;
        this.terminosEntrega = terminosEntrega;
        this.primeraCarga = primeraCarga;
        this.fecha = fecha;
        this.idTipoMoneda = idTipoMoneda;
    }

    public ProformaInvoiceDTO(Integer idProforma, Integer consecutivo, Double cbmTotal, Double valorTotal, Double valorTotalDescuento,
            String codProveedor, String anio, String usuario, String estado, String consecutivoVisible, String comentario, String tipoProducto,
            Date fecha, List<DetalleProformaDTO> detalleProforma, List<TransaccionBancariaDTO> transacciones,
            List<ContenedorProformaDTO> contenedores) {
        this.idProforma = idProforma;
        this.consecutivo = consecutivo;
        this.cbmTotal = cbmTotal;
        this.valorTotal = valorTotal;
        this.valorTotalDescuento = valorTotalDescuento;
        this.codProveedor = codProveedor;
        this.anio = anio;
        this.usuario = usuario;
        this.estado = estado;
        this.consecutivoVisible = consecutivoVisible;
        this.comentario = comentario;
        this.tipoProducto = tipoProducto;
        this.fecha = fecha;
        this.detalleProforma = detalleProforma;
        this.transacciones = transacciones;
        this.contenedores = contenedores;
    }

    public ProformaInvoiceDTO(Integer idProforma, Integer consecutivo, Double cbmTotal, Double valorTotal, Double valorTotalDescuento, Double valorPendiente,
            Double valorTransferido, String codProveedor, String anio, String usuario, String estado, String consecutivoVisible, String comentario,
            String tipoProducto, Date fecha, TipoMonedaDTO idTipoMoneda, List<DetalleProformaDTO> detalleProforma, List<TransaccionBancariaDTO> transacciones, List<ContenedorProformaDTO> contenedores) {
        this.idProforma = idProforma;
        this.consecutivo = consecutivo;
        this.cbmTotal = cbmTotal;
        this.valorTotal = valorTotal;
        this.valorTotalDescuento = valorTotalDescuento;
        this.valorPendiente = valorPendiente;
        this.valorTransferido = valorTransferido;
        this.codProveedor = codProveedor;
        this.anio = anio;
        this.usuario = usuario;
        this.estado = estado;
        this.consecutivoVisible = consecutivoVisible;
        this.comentario = comentario;
        this.tipoProducto = tipoProducto;
        this.fecha = fecha;
        this.idTipoMoneda = idTipoMoneda;
        this.detalleProforma = detalleProforma;
        this.transacciones = transacciones;
        this.contenedores = contenedores;
    }

    public Integer getIdProforma() {
        return idProforma;
    }

    public void setIdProforma(Integer idProforma) {
        this.idProforma = idProforma;
    }

    public Integer getConsecutivo() {
        return consecutivo;
    }

    public void setConsecutivo(Integer consecutivo) {
        this.consecutivo = consecutivo;
    }

    public Integer getIdPuertoLlegada() {
        return idPuertoLlegada;
    }

    public void setIdPuertoLlegada(Integer idPuertoLlegada) {
        this.idPuertoLlegada = idPuertoLlegada;
    }

    public Integer getIdPuertoSalida() {
        return idPuertoSalida;
    }

    public void setIdPuertoSalida(Integer idPuertoSalida) {
        this.idPuertoSalida = idPuertoSalida;
    }

    public Integer getIdIncoterm() {
        return idIncoterm;
    }

    public void setIdIncoterm(Integer idIncoterm) {
        this.idIncoterm = idIncoterm;
    }

    public Double getCbmTotal() {
        return cbmTotal;
    }

    public void setCbmTotal(Double cbmTotal) {
        this.cbmTotal = cbmTotal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Double getValorTotalDescuento() {
        return valorTotalDescuento;
    }

    public void setValorTotalDescuento(Double valorTotalDescuento) {
        this.valorTotalDescuento = valorTotalDescuento;
    }

    public Double getValorPendiente() {
        return valorPendiente;
    }

    public void setValorPendiente(Double valorPendiente) {
        this.valorPendiente = valorPendiente;
    }

    public Double getValorTransferido() {
        return valorTransferido;
    }

    public void setValorTransferido(Double valorTransferido) {
        this.valorTransferido = valorTransferido;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getConsecutivoVisible() {
        return consecutivoVisible;
    }

    public void setConsecutivoVisible(String consecutivoVisible) {
        this.consecutivoVisible = consecutivoVisible;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public String getContenedoresConsolidados() {
        return contenedoresConsolidados;
    }

    public void setContenedoresConsolidados(String contenedoresConsolidados) {
        this.contenedoresConsolidados = contenedoresConsolidados;
    }

    public String getTerminosPago() {
        return terminosPago;
    }

    public void setTerminosPago(String terminosPago) {
        this.terminosPago = terminosPago;
    }

    public String getTerminosEntrega() {
        return terminosEntrega;
    }

    public void setTerminosEntrega(String terminosEntrega) {
        this.terminosEntrega = terminosEntrega;
    }

    public boolean isPrimeraCarga() {
        return primeraCarga;
    }

    public void setPrimeraCarga(boolean primeraCarga) {
        this.primeraCarga = primeraCarga;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public TipoMonedaDTO getIdTipoMoneda() {
        return idTipoMoneda;
    }

    public void setIdTipoMoneda(TipoMonedaDTO idTipoMoneda) {
        this.idTipoMoneda = idTipoMoneda;
    }

    public List<DetalleProformaDTO> getDetalleProforma() {
        return detalleProforma;
    }

    public void setDetalleProforma(List<DetalleProformaDTO> detalleProforma) {
        this.detalleProforma = detalleProforma;
    }

    public List<TransaccionBancariaDTO> getTransacciones() {
        return transacciones;
    }

    public void setTransacciones(List<TransaccionBancariaDTO> transacciones) {
        this.transacciones = transacciones;
    }

    public List<ContenedorProformaDTO> getContenedores() {
        return contenedores;
    }

    public void setContenedores(List<ContenedorProformaDTO> contenedores) {
        this.contenedores = contenedores;
    }
}
