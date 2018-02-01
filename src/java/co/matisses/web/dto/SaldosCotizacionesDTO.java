package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SaldosCotizacionesDTO {

    private Integer cantidadNecesaria;
    private Integer cantidadDisponible;
    private String referencia;
    private String almacen;
    private String estado;

    public SaldosCotizacionesDTO() {
    }

    public SaldosCotizacionesDTO(Integer cantidadNecesaria, Integer cantidadDisponible, String referencia, String almacen) {
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadDisponible = cantidadDisponible;
        this.referencia = referencia;
        this.almacen = almacen;
    }

    public SaldosCotizacionesDTO(Integer cantidadNecesaria, Integer cantidadDisponible, String referencia, String almacen, String estado) {
        this.cantidadNecesaria = cantidadNecesaria;
        this.cantidadDisponible = cantidadDisponible;
        this.referencia = referencia;
        this.almacen = almacen;
        this.estado = estado;
    }

    public Integer getCantidadNecesaria() {
        return cantidadNecesaria;
    }

    public void setCantidadNecesaria(Integer cantidadNecesaria) {
        this.cantidadNecesaria = cantidadNecesaria;
    }

    public Integer getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(Integer cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
