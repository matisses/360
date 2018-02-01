package co.matisses.web.dto;

import java.util.List;

/**
 *
 * @author ygil
 */
public class DatosVentasDTO {

    private Long total;
    private String almacen;
    private List<DetalleDatosVentasDTO> ventas;

    public DatosVentasDTO() {
    }

    public DatosVentasDTO(Long total, String almacen, List<DetalleDatosVentasDTO> ventas) {
        this.total = total;
        this.almacen = almacen;
        this.ventas = ventas;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public List<DetalleDatosVentasDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<DetalleDatosVentasDTO> ventas) {
        this.ventas = ventas;
    }
}
