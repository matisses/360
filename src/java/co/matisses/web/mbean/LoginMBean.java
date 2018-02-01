package co.matisses.web.mbean;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "loginMBean")
public class LoginMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSession;

    public LoginMBean() {
    }

    public String getAlmacen() {
        if (userSession.getIp() == null) {
            userSession.obtenerIp();
            userSession.asignarSucursalAutomatica();
        }
        return userSession.getAlmacen();
    }

    public void setAlmacen(String almacen) {
        userSession.setAlmacen(almacen);
    }

    public UserSessionInfoMBean getUserSession() {
        return userSession;
    }

    public void setUserSession(UserSessionInfoMBean userSession) {
        this.userSession = userSession;
    }
}
