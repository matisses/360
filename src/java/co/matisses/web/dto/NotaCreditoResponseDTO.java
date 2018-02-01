package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class NotaCreditoResponseDTO {

    private String codigo;
    private String nroFactura;
    private String nroNotaCredito;
    private String nroReciboCaja;
    private String nroOrdenVenta;
    private String nroSalidaMcia;
    private String mensaje;

    public NotaCreditoResponseDTO() {
    }

    public NotaCreditoResponseDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(String nroFactura) {
        this.nroFactura = nroFactura;
    }

    public String getNroOrdenVenta() {
        return nroOrdenVenta;
    }

    public void setNroOrdenVenta(String nroOrdenVenta) {
        this.nroOrdenVenta = nroOrdenVenta;
    }

    public String getNroReciboCaja() {
        return nroReciboCaja;
    }

    public void setNroReciboCaja(String nroReciboCaja) {
        this.nroReciboCaja = nroReciboCaja;
    }

    public String getNroSalidaMcia() {
        return nroSalidaMcia;
    }

    public void setNroSalidaMcia(String nroSalidaMcia) {
        this.nroSalidaMcia = nroSalidaMcia;
    }

    public String getNroNotaCredito() {
        return nroNotaCredito;
    }

    public void setNroNotaCredito(String nroNotaCredito) {
        this.nroNotaCredito = nroNotaCredito;
    }
}
