package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dbotero
 */
@XmlRootElement
public class VentaPOSResponseDTO {

    private String codigo;
    private String mensaje;
    private String numeroFactura;
    private ResolucionFacturacionDTO resolucion;
    private List<UbicacionFacturaDTO> ubicaciones;
    private List<GiftCertificate> certificados;
    private int docNum;

    public VentaPOSResponseDTO() {
        ubicaciones = new ArrayList<>();
        certificados = new ArrayList<>();
    }

    public VentaPOSResponseDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public VentaPOSResponseDTO(String codigo, String mensaje, String numeroFactura, ResolucionFacturacionDTO resolucion, List<UbicacionFacturaDTO> ubicaciones, List<GiftCertificate> certificados) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.numeroFactura = numeroFactura;
        this.resolucion = resolucion;
        this.ubicaciones = ubicaciones;
        this.certificados = certificados;
    }

    public VentaPOSResponseDTO(String codigo, String mensaje, String numeroFactura, ResolucionFacturacionDTO resolucion, List<UbicacionFacturaDTO> ubicaciones, List<GiftCertificate> certificados, Integer docNum) {
        this.codigo = codigo;
        this.mensaje = mensaje;
        this.numeroFactura = numeroFactura;
        this.resolucion = resolucion;
        this.ubicaciones = ubicaciones;
        this.certificados = certificados;
        this.docNum = docNum;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }

    public ResolucionFacturacionDTO getResolucion() {
        return resolucion;
    }

    public void setResolucion(ResolucionFacturacionDTO resolucion) {
        this.resolucion = resolucion;
    }

    public List<UbicacionFacturaDTO> getUbicaciones() {
        return ubicaciones;
    }

    public void setUbicaciones(List<UbicacionFacturaDTO> ubicaciones) {
        this.ubicaciones = ubicaciones;
    }

    public List<GiftCertificate> getCertificados() {
        return certificados;
    }

    public void setCertificados(List<GiftCertificate> certificados) {
        this.certificados = certificados;
    }

    public String toJSON() {
        return "{" + "\"codigo\":\"" + codigo + "\", \"mensaje\":\"" + mensaje + "\", \"numeroFactura\":\"" + numeroFactura + "\", \"resolucion\":" + resolucion.toJSON() + "}";
    }

    @Override
    public String toString() {
        return "VentaPOSResponseDTO{" + "codigo=" + codigo + ", mensaje=" + mensaje + ", numeroFactura=" + numeroFactura + ", resolucion=" + resolucion + ", ubicaciones=" + ubicaciones + ", certificados=" + certificados + '}';
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public int getDocNum() {
        return this.docNum;
    }
}
