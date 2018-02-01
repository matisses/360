package co.matisses.web.rest.regalos.dto;

import co.matisses.web.p2p.P2PWSAuthentication;

/**
 *
 * @author dbotero
 */
public class InformacionPagoResponseDTO {

    private P2PWSAuthentication auth;
    private String locale;
    private String expiration;
    private String ipAddress;

    public InformacionPagoResponseDTO() {
    }

    public P2PWSAuthentication getAuth() {
        return auth;
    }

    public void setAuth(P2PWSAuthentication auth) {
        this.auth = auth;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}
