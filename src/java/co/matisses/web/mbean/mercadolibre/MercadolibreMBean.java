package co.matisses.web.mbean.mercadolibre;

import co.matisses.web.bcs.client.MercadolibreServiceClient;
import co.matisses.web.mbean.BaruApplicationMBean;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "mercadolibreMBean")
public class MercadolibreMBean implements Serializable {

    private static final Logger console = Logger.getLogger(MercadolibreMBean.class.getSimpleName());
    @Inject
    private BaruApplicationMBean appBean;

    public MercadolibreMBean() {
    }

    public void obtenerURLAuth() {
        console.log(Level.INFO, "Obteniendo URL para redirigir a autenticacion de mercadolibre");
        MercadolibreServiceClient service = new MercadolibreServiceClient(appBean.obtenerValorPropiedad("url.servicio.bcs"));
        Response resp = service.obtenerURLAutenticacion(Response.class);
        String url = resp.readEntity(String.class);
        console.log(Level.INFO, "Se obtuvo la url {0}", url);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        try {
            ec.redirect(url);
        } catch (Exception e) {
            console.log(Level.SEVERE, "Ocurrio un error al redireccionar al sitio de mercadolibre. ", e);
        }
    }
}
