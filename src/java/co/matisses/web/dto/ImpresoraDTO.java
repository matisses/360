package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ImpresoraDTO {

    private Integer idImpresora;
    private String nombreImpresora;
    private String sucursal;
    private String estadoEjecucion;
    private String codigoImpresion;
    private String nombreImpresoraServidor;
    private boolean activo;

    public ImpresoraDTO() {
    }

    public ImpresoraDTO(Integer idImpresora, String nombreImpresora, String sucursal, String estadoEjecucion, String codigoImpresion, String nombreImpresoraServidor, boolean activo) {
        this.idImpresora = idImpresora;
        this.nombreImpresora = nombreImpresora;
        this.sucursal = sucursal;
        this.estadoEjecucion = estadoEjecucion;
        this.codigoImpresion = codigoImpresion;
        this.nombreImpresoraServidor = nombreImpresoraServidor;
        this.activo = activo;
    }

    public Integer getIdImpresora() {
        return idImpresora;
    }

    public void setIdImpresora(Integer idImpresora) {
        this.idImpresora = idImpresora;
    }

    public String getNombreImpresora() {
        return nombreImpresora;
    }

    public void setNombreImpresora(String nombreImpresora) {
        this.nombreImpresora = nombreImpresora;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }

    public String getEstadoEjecucion() {
        return estadoEjecucion;
    }

    public void setEstadoEjecucion(String estadoEjecucion) {
        this.estadoEjecucion = estadoEjecucion;
    }

    public String getCodigoImpresion() {
        return codigoImpresion;
    }

    public void setCodigoImpresion(String codigoImpresion) {
        this.codigoImpresion = codigoImpresion;
    }

    public String getNombreImpresoraServidor() {
        return nombreImpresoraServidor;
    }

    public void setNombreImpresoraServidor(String nombreImpresoraServidor) {
        this.nombreImpresoraServidor = nombreImpresoraServidor;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
