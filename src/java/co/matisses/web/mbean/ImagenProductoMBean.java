package co.matisses.web.mbean;

import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import java.io.File;
import java.io.FileFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author dbotero
 */
@Path("imagenes")
@RequestScoped
@Named(value = "imagenProductoMBean")
public class ImagenProductoMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ImagenProductoMBean.class.getSimpleName());
    @Inject
    private BaruApplicationMBean aplicacionBean;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;

    public ImagenProductoMBean() {
    }

    public BaruApplicationMBean getAplicacionBean() {
        return aplicacionBean;
    }

    public void setAplicacionBean(BaruApplicationMBean aplicacionBean) {
        this.aplicacionBean = aplicacionBean;
    }

    public String obtenerUrlProducto(String referencia, boolean mini) {
        String sufijoMini = "";
        if (mini) {
            sufijoMini = ".mini";
        }
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.images" + sufijoMini);
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.images[{0}]] en baru.properties", sufijoMini);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.images" + sufijoMini);
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.images[{0}]] en baru.properties", sufijoMini);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }
    }

    public String obtenerUrlLocalProducto(String referencia, boolean mini) {
        String sufijoMini = "";
        if (mini) {
            sufijoMini = ".mini";
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.images" + sufijoMini);
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.images[{0}]] en baru.properties", sufijoMini);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return url;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad(mini ? "url.web.noimage.mini" : "url.web.noimage");
        }
    }

    public String obtenerUrlFotoZona(String nombreFoto) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.fotoZona");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, nombreFoto);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.fotoZona] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
        String url = aplicacionBean.obtenerValorPropiedad("url.local.fotoZona");
        if (url != null) {
            url = String.format(url, nombreFoto);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.fotoZona] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public String obtenerUrlFotoTienda(String tienda) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.sketch");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.sketch] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
        urlWeb = String.format(urlWeb, tienda, "", "");

        String url = aplicacionBean.obtenerValorPropiedad("url.local.sketch");
        if (url != null) {
            url = String.format(url, tienda, "", "");
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.sketch] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            log.log(Level.INFO, "Retornando la ruta de la tienda [{0}]", tienda);
            return urlWeb;
        } else {
            log.log(Level.WARNING, "La tienda [{0}] no tiene imagen ", tienda);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    @GET
    @Path("listar/{referencia}")
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public List<String> obtenerImagenesCatalogo(@PathParam("referencia") String referencia) {
        List<String> galeria = new ArrayList<>();
        if (aplicacionBean == null) {
            aplicacionBean = new BaruApplicationMBean();
            aplicacionBean.cargarProperties();
        }
        String url = aplicacionBean.obtenerValorPropiedad("url.local.catalogo.dir");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de la propiedad [url.local.catalogo.dir] en baru.properties");
            return new ArrayList<>();
        }
        File file = new File(url);
        if (file.exists()) {
            File[] archivos = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File path) {
                    return (!path.isHidden() && path.getPath().contains(".jpg"));
                }
            });
            for (File s : archivos) {
                galeria.add(s.getName());
            }
            Collections.sort(galeria);
            return galeria;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return new ArrayList<>();
        }
    }

    public String obtenerPlantilla(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.plantilla");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.plantilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.plantilla");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.plantilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public String obtener360(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.360");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.360] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.360");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.360] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public String obtenerWOW(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.wow");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.wow] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.wow");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.wow] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage");
        }
    }

    public List<String[]> obtenerItemsSimilares(String item) {
        List<String[]> similares = new ArrayList<>();
        List<String> itemsSimilares = itemInventarioFacade.obtenerItemsSimilares(item);

        if (itemsSimilares != null && !itemsSimilares.isEmpty()) {
            for (String i : itemsSimilares) {
                if (!i.equals(item)) {
                    similares.add(new String[]{obtenerUrlProducto(i, true), i});
                }
            }
        }

        return similares;
    }

    public String obtenerParrilla(String referencia) {
        String urlWeb = aplicacionBean.obtenerValorPropiedad("url.web.parrilla");
        if (urlWeb != null) {
            urlWeb = String.format(urlWeb, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.web.parrilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage.parrilla");
        }

        String url = aplicacionBean.obtenerValorPropiedad("url.local.parrilla");
        if (url != null) {
            url = String.format(url, referencia);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [url.local.parrilla] en baru.properties");
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage.parrilla");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.SEVERE, "No se encontro la imagen en la ruta [{0}]", url);
            return aplicacionBean.obtenerValorPropiedad("url.web.noimage.parrilla");
        }
    }
}
