package co.matisses.web.mbean.webmaster;

import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.JWTTokenManager;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@RequestScoped
@Named(value = "administrarSitioWebMBean")
public class AdministrarSitioWebMBean implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(AdministrarSitioWebMBean.class.getSimpleName());
    @Inject
    private JWTTokenManager tokenManager;
    @Inject
    private BaruApplicationMBean appBean;
    @Inject
    private UserSessionInfoMBean sessionBean;

    private String mensajeError;

    public String getMensajeError() {
        return mensajeError;
    }

    public void redireccionar() {
        CONSOLE.log(Level.INFO, "redireccionando al usuario al sitio de matisses con permisos de administracion");
        try {
            //TODO: validar que el usuario tenga privilegios de administracion del sitio web
            if (sessionBean.getUsuario() == null || sessionBean.getUsuario().trim().isEmpty()) {
                this.mensajeError = "No se encontraron datos de sesión para continuar. ";
                return;
            }
            String url = appBean.obtenerValorPropiedad("sitioweb.url") + "/admin/" + tokenManager.tokenizeData(sessionBean.getUsuario(), sessionBean.getNombreMostrar());
            CONSOLE.log(Level.INFO, "Se redireccionara a la URL {0}", url);
            FacesContext.getCurrentInstance().getExternalContext().redirect(url);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al redireccionar al sitio de matisses para administracion. ", e);
            this.mensajeError = "No fue posible realizar la redirección al sitio web. Por favor comunícate con el departamento de sistemas. " + e.getMessage();
        }
    }
}
