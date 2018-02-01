package co.matisses.web.dto;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ClasificacionImpuestosDTO {

    private String nombre;
    private Float totalBase;
    private Float totalImpuesto;
    private Float totalDescuento;
    private List<DetalleImpuestosDTO> detalleImpuestos;
    private NumberFormat nf = new DecimalFormat("#,###");

    public ClasificacionImpuestosDTO() {
        detalleImpuestos = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<DetalleImpuestosDTO> getDetalleImpuestos() {
        return detalleImpuestos;
    }

    public void agregarDetalleImpuestos(DetalleImpuestosDTO det) {
        if (det == null) {
            return;
        }
        int pos = detalleImpuestos.indexOf(det);
        if (pos == -1) {
            detalleImpuestos.add(det);
        } else if (detalleImpuestos.contains(det)) {
            DetalleImpuestosDTO dto = detalleImpuestos.get(pos);
            dto.setTotal(dto.getTotal() + det.getTotal());
            dto.setValorBase(dto.getValorBase() + det.getValorBase());
            dto.setValorImpuesto(dto.getValorImpuesto() + det.getValorImpuesto());
            dto.setValorDescuento(dto.getValorDescuento() + det.getValorDescuento());
            detalleImpuestos.set(pos, dto);
        }
        calcularTotales();
    }

    private void calcularTotales() {
        totalBase = 0f;
        totalDescuento = 0f;
        totalImpuesto = 0f;
        for (DetalleImpuestosDTO dto : detalleImpuestos) {
            totalBase += dto.getValorBase();
            totalDescuento += dto.getValorDescuento();
            totalImpuesto += dto.getValorImpuesto();
        }
    }

    public Float getTotalBase() {
        return totalBase;
    }

    public Float getTotalImpuesto() {
        return totalImpuesto;
    }

    public Float getTotalDescuento() {
        return totalDescuento;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.nombre);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClasificacionImpuestosDTO other = (ClasificacionImpuestosDTO) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ClasificacionImpuestosDTO{" + "nombre=" + nombre + ", detalleImpuestos=" + detalleImpuestos + '}';
    }

}
