package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class ResolucionFacturacionDTO {

    private String prefijo;
    private String desde;
    private String hasta;
    private String fecha;
    private String numero;

    public ResolucionFacturacionDTO() {
    }

    public ResolucionFacturacionDTO(String prefijo, String desde, String hasta, String fecha, String numero) {
        this.prefijo = prefijo;
        this.desde = desde;
        this.hasta = hasta;
        this.fecha = fecha;
        this.numero = numero;
    }

    public String getPrefijo() {
        return prefijo;
    }

    public void setPrefijo(String prefijo) {
        this.prefijo = prefijo;
    }

    public String getDesde() {
        return desde;
    }

    public void setDesde(String desde) {
        this.desde = desde;
    }

    public String getHasta() {
        return hasta;
    }

    public void setHasta(String hasta) {
        this.hasta = hasta;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "ResolucionFacturacionDTO{" + "prefijo=" + prefijo + ", desde=" + desde + ", hasta=" + hasta + ", fecha=" + fecha + ", numero=" + numero + '}';
    }

    public String toJSON() {
        return "{" + "\"prefijo\":\"" + prefijo + "\", \"desde\":\"" + desde + "\", \"hasta\":" + hasta + "\", \"fecha\":" + fecha + "\", \"numero\":\"" + numero + "\"}";
    }
}
