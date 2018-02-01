package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class MensajeTextoDTO {

    private String codigoPais;
    private String destino;
    private String mensaje;
    private String origen;
    private String metodo;
    private String url;
    private String ip;
    private String usuario;
    private boolean pruebas;

    public MensajeTextoDTO() {
    }

    public boolean isPruebas() {
        return pruebas;
    }

    public void setPruebas(boolean pruebas) {
        this.pruebas = pruebas;
    }

    public String getCodigoPais() {
        return codigoPais;
    }

    public void setCodigoPais(String codigoPais) throws Exception {
        if (codigoPais == null || codigoPais.trim().isEmpty()) {
            throw new Exception("El codigo de pais no es valido.");
        }
        this.codigoPais = codigoPais;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) throws Exception {
        if (destino == null || destino.length() != 10) {
            throw new Exception("El numero de telefono no es valido.");
        }
        this.destino = destino;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) throws Exception {
        if (mensaje == null || mensaje.length() > 150) {
            throw new Exception("La longitud del mensaje excede 150 caracteres.");
        }
        this.mensaje = mensaje;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
