package co.matisses.web.rest.regalos.dto;

import co.matisses.web.p2p.DatosPagoPlaceToPayDTO;
import co.matisses.web.dto.SesionListaRegalosDTO;

/**
 *
 * @author dbotero
 */
public class SolicitudPagoDTO {
    private String codigoLista;
    private String mensaje;
    private DatosPagoPlaceToPayDTO datosPago;
    private SesionListaRegalosDTO sesion;

    public SolicitudPagoDTO() {
    }

    public String getCodigoLista() {
        return codigoLista;
    }

    public void setCodigoLista(String codigoLista) {
        this.codigoLista = codigoLista;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public DatosPagoPlaceToPayDTO getDatosPago() {
        return datosPago;
    }

    public void setDatosPago(DatosPagoPlaceToPayDTO datosPago) {
        this.datosPago = datosPago;
    }

    public SesionListaRegalosDTO getSesion() {
        return sesion;
    }

    public void setSesion(SesionListaRegalosDTO sesion) {
        this.sesion = sesion;
    }
}
