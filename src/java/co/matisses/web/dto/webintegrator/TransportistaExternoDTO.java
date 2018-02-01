package co.matisses.web.dto.webintegrator;

/**
 *
 * @author dbotero
 */
public class TransportistaExternoDTO {

    private Integer idTransportista;
    private String nombre;
    private String clase;
    private String metodoCotizacion;
    private String metodoRastreo;
    private String wsdl;
    private String cuentaSAP;
    private Boolean activo;

    public TransportistaExternoDTO() {
    }

    public TransportistaExternoDTO(Integer idTransportista, String nombre, String clase, String metodoCotizacion, String metodoRastreo, String wsdl, Boolean activo, String cuentaSAP) {
        this.idTransportista = idTransportista;
        this.nombre = nombre;
        this.clase = clase;
        this.metodoCotizacion = metodoCotizacion;
        this.metodoRastreo = metodoRastreo;
        this.wsdl = wsdl;
        this.activo = activo;
        this.cuentaSAP = cuentaSAP;
    }

    public Integer getIdTransportista() {
        return idTransportista;
    }

    public void setIdTransportista(Integer idTransportista) {
        this.idTransportista = idTransportista;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getMetodoCotizacion() {
        return metodoCotizacion;
    }

    public void setMetodoCotizacion(String metodoCotizacion) {
        this.metodoCotizacion = metodoCotizacion;
    }

    public String getMetodoRastreo() {
        return metodoRastreo;
    }

    public void setMetodoRastreo(String metodoRastreo) {
        this.metodoRastreo = metodoRastreo;
    }

    public String getWsdl() {
        return wsdl;
    }

    public void setWsdl(String wsdl) {
        this.wsdl = wsdl;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getCuentaSAP() {
        return cuentaSAP;
    }

    public void setCuentaSAP(String cuentaSAP) {
        this.cuentaSAP = cuentaSAP;
    }

}
