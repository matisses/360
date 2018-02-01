package co.matisses.web.rest.regalos.dto;

import co.matisses.web.p2p.RespuestaPlaceToPayDTO;

/**
 *
 * @author dbotero
 */
public class SolicitudPagoResponseDTO {

    private int codigo;
    private String mensaje;
    private RespuestaPlaceToPayDTO respuestaPlaceToPay;

    public SolicitudPagoResponseDTO() {
    }

    public SolicitudPagoResponseDTO(int codigo, RespuestaPlaceToPayDTO respuestaPlaceToPay) {
        this.codigo = codigo;
        this.respuestaPlaceToPay = respuestaPlaceToPay;
    }

    public SolicitudPagoResponseDTO(int codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public RespuestaPlaceToPayDTO getRespuestaPlaceToPay() {
        return respuestaPlaceToPay;
    }

    public void setRespuestaPlaceToPay(RespuestaPlaceToPayDTO respuestaPlaceToPay) {
        this.respuestaPlaceToPay = respuestaPlaceToPay;
    }
}
