package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class HistoricoProductoCcygaDTO {

    private ProcesoCcygaDTO proceso;
    private ProductoCcygaDTO producto;
    private Long tiempoProceso;
    private Integer entradas;
    private List<DetalleHistoricoCcygaDTO> detalle;

    public HistoricoProductoCcygaDTO() {
        detalle = new ArrayList<>();
    }

    public HistoricoProductoCcygaDTO(ProcesoCcygaDTO proceso, ProductoCcygaDTO producto) {
        this.proceso = proceso;
        this.producto = producto;
    }

    public HistoricoProductoCcygaDTO(Integer entradas, Long tiempoProceso, ProcesoCcygaDTO proceso, ProductoCcygaDTO producto, DetalleHistoricoCcygaDTO detalle) {
        this.entradas = entradas;
        this.tiempoProceso = tiempoProceso;
        this.proceso = proceso;
        this.producto = producto;
        if (this.detalle == null) {
            this.detalle = new ArrayList<>();
        }
        this.detalle.add(detalle);
    }

    public ProcesoCcygaDTO getProceso() {
        return proceso;
    }

    public void setProceso(ProcesoCcygaDTO proceso) {
        this.proceso = proceso;
    }

    public ProductoCcygaDTO getProducto() {
        return producto;
    }

    public void setProducto(ProductoCcygaDTO producto) {
        this.producto = producto;
    }

    public Long getTiempoProceso() {
        return tiempoProceso;
    }

    public void setTiempoProceso(Long tiempoProceso) {
        this.tiempoProceso = tiempoProceso;
    }

    public Integer getEntradas() {
        return entradas;
    }

    public void setEntradas(Integer entradas) {
        this.entradas = entradas;
    }

    public String getTiempoFormateado() {
        //long second = (tiempoProceso / 1000) % 60;
        long minute = (tiempoProceso / (1000 * 60)) % 60;
        long hour = (tiempoProceso / (1000 * 60 * 60)) % 24;

        long hours = (tiempoProceso / 1000 / 60 / 60);
        long mins = (tiempoProceso - (hours * 1000 * 60 * 60)) / 1000 / 60;

        return String.format("%02d:%02d", hours, mins);
    }

    public List<DetalleHistoricoCcygaDTO> getDetalle() {
        return detalle;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.proceso);
        hash = 97 * hash + Objects.hashCode(this.producto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final HistoricoProductoCcygaDTO other = (HistoricoProductoCcygaDTO) obj;
        if (!Objects.equals(this.proceso, other.proceso)) {
            return false;
        }
        if (!Objects.equals(this.producto, other.producto)) {
            return false;
        }
        return true;
    }

    public void agregarDetalle(DetalleHistoricoCcygaDTO dto) {
        detalle.add(dto);
    }
}
