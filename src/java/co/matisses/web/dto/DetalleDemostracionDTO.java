package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ygil
 */
public class DetalleDemostracionDTO {

    private int idDetalleDemostracion;
    private int cantidad;
    private String referencia;
    private String almacenOrigen;
    private String almacenDestino;
    private String ubicacionDestino;
    private List<UbicacionesDemostracionDTO> ubicaciones;

    public DetalleDemostracionDTO() {
        ubicaciones = new ArrayList<>();
    }

    public DetalleDemostracionDTO(int idDetalleDemostracion, int cantidad, String referencia, String almacenOrigen, String almacenDestino, List<UbicacionesDemostracionDTO> ubicaciones) {
        this.idDetalleDemostracion = idDetalleDemostracion;
        this.cantidad = cantidad;
        this.referencia = referencia;
        this.almacenOrigen = almacenOrigen;
        this.almacenDestino = almacenDestino;
        this.ubicaciones = ubicaciones;
    }

    public int getIdDetalleDemostracion() {
        return idDetalleDemostracion;
    }

    public void setIdDetalleDemostracion(int idDetalleDemostracion) {
        this.idDetalleDemostracion = idDetalleDemostracion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getAlmacenOrigen() {
        return almacenOrigen;
    }

    public void setAlmacenOrigen(String almacenOrigen) {
        this.almacenOrigen = almacenOrigen;
    }

    public String getAlmacenDestino() {
        return almacenDestino;
    }

    public void setAlmacenDestino(String almacenDestino) {
        this.almacenDestino = almacenDestino;
    }

    public String getUbicacionDestino() {
        return ubicacionDestino;
    }

    public void setUbicacionDestino(String ubicacionDestino) {
        this.ubicacionDestino = ubicacionDestino;
    }

    public List<UbicacionesDemostracionDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<UbicacionesDemostracionDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }
}
