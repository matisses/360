package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class DevolucionSAPDTO {

    private Integer numeroDevolucion;
    private Integer total;
    private Integer slpCode;
    private String tipo;
    private String cardCode;
    private Character docStatus;
    private Date fecha;
    private List<DetalleDevolucionSAPDTO> detalle;

    public DevolucionSAPDTO() {
        detalle = new ArrayList<>();
    }

    public DevolucionSAPDTO(Integer numeroDevolucion, Date fecha, Integer total, String tipo, List<DetalleDevolucionSAPDTO> detalle) {
        this.numeroDevolucion = numeroDevolucion;
        this.fecha = fecha;
        this.total = total;
        this.tipo = tipo;
        this.detalle = detalle;
    }

    public DevolucionSAPDTO(Integer numeroDevolucion, Integer total, Integer slpCode, String tipo, String cardCode, Character docStatus, Date fecha) {
        this.numeroDevolucion = numeroDevolucion;
        this.total = total;
        this.slpCode = slpCode;
        this.tipo = tipo;
        this.cardCode = cardCode;
        this.docStatus = docStatus;
        this.fecha = fecha;
    }

    public Integer getNumeroDevolucion() {
        return numeroDevolucion;
    }

    public void setNumeroDevolucion(Integer numeroDevolucion) {
        this.numeroDevolucion = numeroDevolucion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public List<DetalleDevolucionSAPDTO> getDetalle() {
        return detalle;
    }

    public void setDetalle(List<DetalleDevolucionSAPDTO> detalle) {
        this.detalle = detalle;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public Character getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(Character docStatus) {
        this.docStatus = docStatus;
    }

    public Integer getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Integer slpCode) {
        this.slpCode = slpCode;
    }
}
