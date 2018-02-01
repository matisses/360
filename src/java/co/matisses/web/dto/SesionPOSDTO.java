package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class SesionPOSDTO {

    private boolean sesionValida;
    private boolean cajaAbierta;
    private String usuario;
    private String almacen;
    private String mensajeError;
    private String ip;
    private String cuentaEfectivo;
    private String nombreCaja;
    private Long idTurnoCaja;
    private Long tarjetasRegaloDisponibles;

    public SesionPOSDTO() {
    }

    public SesionPOSDTO(boolean sesionValida, boolean cajaAbierta, Long idTurnoCaja, String usuario, String almacen, String ip, String cuentaEfectivo, String nombreCaja, String mensajeError) {
        this.sesionValida = sesionValida;
        this.cajaAbierta = cajaAbierta;
        this.usuario = usuario;
        this.almacen = almacen;
        this.mensajeError = mensajeError;
        this.ip = ip;
        this.cuentaEfectivo = cuentaEfectivo;
        this.nombreCaja = nombreCaja;
        this.idTurnoCaja = idTurnoCaja;
    }
    public boolean isCajaAbierta() {
        return cajaAbierta;
    }

    public void setCajaAbierta(boolean cajaAbierta) {
        this.cajaAbierta = cajaAbierta;
    }

    public Long getIdTurnoCaja() {
        return idTurnoCaja;
    }

    public void setIdTurnoCaja(Long idTurnoCaja) {
        this.idTurnoCaja = idTurnoCaja;
    }

    public String getNombreCaja() {
        return nombreCaja;
    }

    public void setNombreCaja(String nombreCaja) {
        this.nombreCaja = nombreCaja;
    }

    public String getCuentaEfectivo() {
        return cuentaEfectivo;
    }

    public void setCuentaEfectivo(String cuentaEfectivo) {
        this.cuentaEfectivo = cuentaEfectivo;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getMensajeError() {
        return mensajeError;
    }

    public void setMensajeError(String mensajeError) {
        this.mensajeError = mensajeError;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public boolean isSesionValida() {
        return sesionValida;
    }

    public void setSesionValida(boolean sesionValida) {
        this.sesionValida = sesionValida;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getTarjetasRegaloDisponibles() {
        return tarjetasRegaloDisponibles;
    }

    public void setTarjetasRegaloDisponibles(Long tarjetasRegaloDisponibles) {
        this.tarjetasRegaloDisponibles = tarjetasRegaloDisponibles;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
        //return "SesionPOSDTO{" + "sesionValida=" + sesionValida + ", cajaAbierta=" + cajaAbierta + ", usuario=" + usuario + ", almacen=" + almacen + ", mensajeError=" + mensajeError + ", ip=" + ip + ", cuentaEfectivo=" + cuentaEfectivo + ", nombreCaja=" + nombreCaja + ", idTurnoCaja=" + idTurnoCaja + '}';
    }

}
