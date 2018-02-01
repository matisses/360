package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class DetalleDatosVentasDTO {

    private Long total;
    private String tipo;
    private String documento;
    private Date fecha;

    public DetalleDatosVentasDTO() {
    }

    public DetalleDatosVentasDTO(Long total, String tipo, String documento, Date fecha) {
        this.total = total;
        this.tipo = tipo;
        this.documento = documento;
        this.fecha = fecha;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
