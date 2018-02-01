package co.matisses.web.mbean;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.entity.Banner;
import co.matisses.persistence.web.facade.BannerFacade;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@RequestScoped
@Named(value = "homeTilesMBean")
public class HomeTilesMBean implements Serializable {

    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger log = Logger.getLogger(HomeTilesMBean.class.getSimpleName());
    private String[] fichas = new String[8];
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private BannerFacade bannerFacade;

    public HomeTilesMBean() {

    }

    @PostConstruct
    protected void construirInicio() {
        //agregarFichaBanner();
        agregarFichaCumpleanos();
        //agregarFichaNovedades();
        //agregarFichasPersonalizadas();
        //agregarFichasFrecuentes();
        //agregarFichasAdicionales();
    }

    private boolean hayFichasLibres() {
        for (String ficha : fichas) {
            if (ficha == null) {
                return true;
            }
        }
        return false;
    }

    private void agregarFichaBanner() {
        List<Banner> banners = bannerFacade.consultarBannersVigentes();
        if (banners.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Banner banner : banners) {
            sb.append(banner.getCodigo());
        }

//        sb.append("<script>$(function(){$('#bannersEndo').carousel({interval: 5250, pause: \"false\"});});</script>");
//        sb.append("<div id=\"bannersEndo\" class=\"carousel slide\" data-ride=\"carousel\">");
//        sb.append("<div class=\"carousel-inner\" role=\"listbox\">");
//        sb.append("<div class=\"item active\">");
//        sb.append("<img class=\"img-responsive\" src=\"/360/images/banners/banner1.jpg\"></img>");
//        sb.append("</div>");
//        sb.append("<div class=\"item\">");
//        sb.append("<img class=\"img-responsive\" src=\"/360/images/banners/banner2.jpg\"></img>");
//        sb.append("</div>");
//        sb.append("<div class=\"item\">");
//        sb.append("<img class=\"img-responsive\" src=\"/360/images/banners/banner3.jpg\"></img>");
//        sb.append("</div></div></div>");
        fichas[0] = sb.toString();
    }

    private String volverCamelCase(String texto) {
        StringBuilder sb = new StringBuilder();
        for (String parte : texto.split(" ")) {
            sb.append(StringUtils.capitalize(parte.toLowerCase()));
            sb.append(" ");
        }
        return StringUtils.chop(sb.toString());
    }

    private void agregarFichaCumpleanos() {
        List<Empleado> empleados = empleadoFacade.consultarCumpleanosDia();
        if (empleados.isEmpty()) {
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"panel-heading text-center\" style=\"font-size: 20px;\">¡Hoy está de cumpleaños!</div>");
        sb.append("<div class=\"panel-body\">");
        sb.append("<script>$(function(){$('#cumpleanosCarousel').carousel({interval: 7700, pause: \"false\"});});</script>");
        sb.append("<div id=\"cumpleanosCarousel\" class=\"carousel slide\" data-ride=\"carousel\">");
        sb.append("<div class=\"carousel-inner\" role=\"listbox\">");

        boolean primero = true;
        for (Empleado emp : empleados) {
            if (primero) {
                sb.append("<div class=\"item active\">");
                primero = false;
            } else {
                sb.append("<div class=\"item\">");
            }
            sb.append("<img style=\"height: 160px;\" class=\"img-responsive\" src=\"");
            sb.append(genericMBean.obtenerRutaFotoEmpleadoCumples(emp.getOfficeExt()));
            sb.append("\" alt=\"");
            sb.append(emp.getOfficeExt());
            sb.append("\"></img>");
            sb.append("<div class=\"carousel-caption\">");
            sb.append("<h3>");

            sb.append(volverCamelCase(emp.getFirstName()));
            if (emp.getMiddleName() != null && !emp.getMiddleName().trim().isEmpty()) {
                sb.append(" ");
                sb.append(volverCamelCase(emp.getMiddleName()));
            }
            sb.append(" ");
            sb.append(volverCamelCase(emp.getLastName()));
            sb.append("</h3>");
            sb.append("<p>");
            sb.append(emp.getJobTitle());
            sb.append("</p>");
            sb.append("</div></div>");
        }

        sb.append("</div>");
        sb.append("<ol class=\"carousel-indicators tile-home-bottom\">");
        for (int i = 0; i < empleados.size(); i++) {
            if (i == 0) {
                sb.append("<li data-target=\"#cumpleanosCarousel\" data-slide-to=\"0\" class=\"active\"></li>");
            } else {
                sb.append("<li data-target=\"#cumpleanosCarousel\" data-slide-to=\"1");
                sb.append(i);
                sb.append("\"></li>");
            }
        }

        sb.append("</ol>");
        sb.append("</div>");
        sb.append("</div>");

        fichas[2] = sb.toString();
    }

    private void agregarFichaNovedades() {
        StringBuilder sb = new StringBuilder();
        sb.append("<div class=\"panel-body\">");
        sb.append("<script>$(function(){$('#novedadesCarousel').carousel({interval: 15000,pause: \"false\"});});</script>");
        sb.append("<div id=\"novedadesCarousel\" class=\"carousel slide\" data-ride=\"carousel\">");
        sb.append("<div class=\"carousel-inner\" role=\"listbox\">");
        sb.append("<div class=\"item active\">");
        sb.append("<div class=\"text-center carousel-caption-title\">");
        sb.append("<p>Hasta hoy trabajó con nosotros</p>");
        sb.append("</div>");
        sb.append("<img class=\"img-responsive imagen-empleado\" src=\"/360/images/1035866418.jpg\" ></img>");
        sb.append("<div class=\"carousel-caption\">");
        sb.append("<h3>Jadilson Guisao</h3>");
        sb.append("<p>Sistemas</p>");
        sb.append("</div>");
        sb.append("</div>");
        sb.append("<div class=\"item\">");
        sb.append("<div class=\"text-center carousel-caption-title\">");
        sb.append("<p>Desde hoy está con nosotros</p>");
        sb.append("</div>");
        sb.append("<img class=\"img-responsive imagen-empleado\" src=\"/360/images/bill.jpeg\" ></img>");
        sb.append("<div class=\"carousel-caption\">");
        sb.append("<h3>Bill Gates</h3>");
        sb.append("<p>Sistemas</p>");
        sb.append("</div></div></div></div></div>");

        fichas[7] = sb.toString();
    }

    private void agregarFichasPersonalizadas() {
        //consultar fichas personalizadas para el usuario
        //para cada elemento encontrado, buscar la primera posicion vacia y asignarla
    }

    private void agregarFichasFrecuentes() {
        if (hayFichasLibres()) {

        }
    }

    private void agregarFichasAdicionales() {
        if (hayFichasLibres()) {
            for (int i = 0; i < 8; i++) {
                if (fichas[i] == null) {
                    fichas[i] = "<div class=\"panel-body text-center\"><span class=\"tile-home-icon\"></span><p class=\"tile-home-title\">Ficha sin uso</p></div>";
                }
            }
        }
    }

    public String[] getFichas() {
        return fichas;
    }
}
