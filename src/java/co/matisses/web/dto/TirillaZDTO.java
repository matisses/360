package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class TirillaZDTO {

    private String serie;
    private String caja;
    private String numeroFacturas;
    private String numeroAnulaciones;
    private String horaApertura;
    private String horaCierre;
    private String cajero;
    private String almacen;
    private String fecha;
    private String totalImpuesto;
    private List<ClasificacionImpuestosDTO> impuestos;
    private List<MedioPagoDTO> mediosPago;
    private List<DepositoDTO> depositos;

    public TirillaZDTO() {
        impuestos = new ArrayList<>();
        mediosPago = new ArrayList<>();
        depositos = new ArrayList<>();
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getTotalImpuesto(){
        return totalImpuesto;
    }

    public void setTotalImpuesto(String totalImpuesto) {
        this.totalImpuesto = totalImpuesto;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getCajero() {
        return cajero;
    }

    public void setCajero(String cajero) {
        this.cajero = cajero;
    }

    public String getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(String horaApertura) {
        this.horaApertura = horaApertura;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getCaja() {
        return caja;
    }

    public void setCaja(String caja) {
        this.caja = caja;
    }

    public String getNumeroFacturas() {
        return numeroFacturas;
    }

    public void setNumeroFacturas(String numeroFacturas) {
        this.numeroFacturas = numeroFacturas;
    }

    public String getNumeroAnulaciones() {
        return numeroAnulaciones;
    }

    public void setNumeroAnulaciones(String numeroAnulaciones) {
        this.numeroAnulaciones = numeroAnulaciones;
    }

    public List<ClasificacionImpuestosDTO> getImpuestos() {
        return impuestos;
    }

    public void agregarClasificacionImpuestos(ClasificacionImpuestosDTO imp) {
        if (imp == null || imp.getDetalleImpuestos().isEmpty()) {
            return;
        }
        int pos = impuestos.indexOf(imp);
        if (pos == -1) {
            impuestos.add(imp);
        } else {
            ClasificacionImpuestosDTO dto = impuestos.get(pos);
            for (DetalleImpuestosDTO det : imp.getDetalleImpuestos()) {
                dto.agregarDetalleImpuestos(det);
            }
            impuestos.set(pos, dto);
        }
    }

    public List<MedioPagoDTO> getMediosPago() {
        return mediosPago;
    }

    public void agregarMedioPago(MedioPagoDTO medio) {
        if (medio == null) {
            return;
        }
        int pos = mediosPago.indexOf(medio);
        if (pos == -1) {
            mediosPago.add(medio);
        } else {
            MedioPagoDTO dto = mediosPago.get(pos);
            dto.setValor(medio.getValor() + dto.getValor());
            dto.setTransacciones(dto.getTransacciones() + 1);
            mediosPago.set(pos, dto);
        }
    }

    public List<DepositoDTO> getDepositos() {
        return depositos;
    }

    public void agregarDeposito(DepositoDTO dep) {
        dep.setNombre(dep.getNombre() + (depositos.size() + 1));
        depositos.add(dep);
    }

//    public String getTotalBase() {
//        float totalBase = 0f;
//        for (ClasificacionImpuestosDTO dto : impuestos) {
//            totalBase += dto.getTotalBase();
//        }
//        return new DecimalFormat("#,###").format(totalBase);
//    }

//    public String getTotalDescuento() {
//        float totalDescuento = 0f;
//        for (ClasificacionImpuestosDTO dto : impuestos) {
//            totalDescuento += dto.getTotalDescuento();
//        }
//        return new DecimalFormat("#,###").format(totalDescuento);
//    }

//    public String getTotalImpuesto() {
//        float totalImpuesto = 0f;
//        for (ClasificacionImpuestosDTO dto : impuestos) {
//            totalImpuesto += dto.getTotalImpuesto();
//        }
//        return new DecimalFormat("#,###").format(totalImpuesto);
//    }

//    public String getTotalVentas() {
//        float total = 0f;
//        for (ClasificacionImpuestosDTO dto : impuestos) {
//            total += dto.getTotalImpuesto();
//            total += dto.getTotalDescuento();
//            total += dto.getTotalBase();
//        }
//        return new DecimalFormat("#,###").format(total);
//    }

//    public String getTotalMediosPago() {
//        float total = 0f;
//        for (MedioPagoDTO dto : mediosPago) {
//            total += dto.getValor();
//        }
//        return new DecimalFormat("#,###").format(total);
//    }

//    public String getTotalDepositos() {
//        float total = 0f;
//        for (DepositoDTO dto : depositos) {
//            total += dto.getValor();
//        }
//        return new DecimalFormat("#,###").format(total);
//    }

    @Override
    public String toString() {
        return "TirillaZDTO{" + "serie=" + serie + ", caja=" + caja + ", numeroFacturas=" + numeroFacturas + ", numeroAnulaciones=" + numeroAnulaciones + ", horaApertura=" + horaApertura + ", horaCierre=" + horaCierre + ", cajero=" + cajero + ", almacen=" + almacen + ", fecha=" + fecha + ", impuestos=" + impuestos + ", mediosPago=" + mediosPago + ", depositos=" + depositos + '}';
    }
}
