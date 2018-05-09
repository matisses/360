package co.matisses.web.mbean.wiki;

import co.matisses.persistence.web.entity.Wiki;
import co.matisses.persistence.web.facade.WikiFacade;
import co.matisses.web.dto.WikiDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "wikiMBean")
public class WikiMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean baruGenericMBean;
    private static final Logger CONSOLE = Logger.getLogger(WikiMBean.class.getSimpleName());
    private Integer paso;
    private Integer idSoporte;
    private String parametroBusqueda;
    private String titulo;
    private String texto;
    private String codigo;
    private String textMostrar;
    private String link;
    private String imagen;
    private boolean mostrarIndex = false;
    private boolean bold = false;
    private boolean italic = false;
    private boolean html = false;
    private boolean list = false;
    private boolean left = false;
    private boolean center = false;
    private boolean right = false;
    private Part file;
    private WikiDTO fragmentoWiki;
    private List<WikiDTO> index;
    private List<WikiDTO> registros;
    private List<WikiDTO> resultadoBusqueda;
    @EJB
    private WikiFacade wikiFacade;

    public WikiMBean() {
        paso = 1;
        fragmentoWiki = new WikiDTO();
        index = new ArrayList<>();
        registros = new ArrayList<>();
        resultadoBusqueda = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerIndex();
        obtenerRegistrosRelevantes();
    }

    public Integer getPaso() {
        return paso;
    }

    public void setPaso(Integer paso) {
        this.paso = paso;
    }

    public Integer getIdSoporte() {
        return idSoporte;
    }

    public void setIdSoporte(Integer idSoporte) {
        this.idSoporte = idSoporte;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTextMostrar() {
        return textMostrar;
    }

    public void setTextMostrar(String textMostrar) {
        this.textMostrar = textMostrar;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public boolean isMostrarIndex() {
        return mostrarIndex;
    }

    public void setMostrarIndex(boolean mostrarIndex) {
        this.mostrarIndex = mostrarIndex;
    }

    public boolean isBold() {
        return bold;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public boolean isItalic() {
        return italic;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public boolean isHtml() {
        return html;
    }

    public void setHtml(boolean html) {
        this.html = html;
    }

    public boolean isList() {
        return list;
    }

    public void setList(boolean list) {
        this.list = list;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isCenter() {
        return center;
    }

    public void setCenter(boolean center) {
        this.center = center;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public WikiDTO getFragmentoWiki() {
        return fragmentoWiki;
    }

    public void setFragmentoWiki(WikiDTO fragmentoWiki) {
        this.fragmentoWiki = fragmentoWiki;
    }

    public List<WikiDTO> getIndex() {
        return index;
    }

    public void setIndex(List<WikiDTO> index) {
        this.index = index;
    }

    public List<WikiDTO> getRegistros() {
        return registros;
    }

    public void setRegistros(List<WikiDTO> registros) {
        this.registros = registros;
    }

    public List<WikiDTO> getResultadoBusqueda() {
        return resultadoBusqueda;
    }

    public void setResultadoBusqueda(List<WikiDTO> resultadoBusqueda) {
        this.resultadoBusqueda = resultadoBusqueda;
    }

    private void obtenerIndex() {
        index = new ArrayList<>();

        List<Wiki> records = wikiFacade.obtenerIndex();

        if (records != null && !records.isEmpty()) {
            records.stream().forEach((w) -> {
                index.add(new WikiDTO(w));
            });
        }
    }

    private void obtenerRegistrosRelevantes() {
        registros = new ArrayList<>();

        List<Wiki> records = wikiFacade.obtenerRegistrosRelevantes();

        if (records != null && !records.isEmpty()) {
            records.stream().forEach((w) -> {
                registros.add(new WikiDTO(w));
            });
        }
    }

    public void mostrarIndex() {
        mostrarIndex = !mostrarIndex;
    }

    public void seleccionarFragmentoWiki() {
        fragmentoWiki = new WikiDTO();
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        for (WikiDTO w : index) {
            if (w.getIdWiki().equals(id)) {
                fragmentoWiki = w;
                acumularVista(w.getIdWiki());
                break;
            }
        }
    }

    public void buscarWiki() {
        fragmentoWiki = new WikiDTO();
        resultadoBusqueda = new ArrayList<>();

        List<Wiki> records = wikiFacade.obtenerWikis(parametroBusqueda);

        if (records != null && !records.isEmpty()) {
            if (records.size() > 1) {
                records.stream().forEach((w) -> {
                    resultadoBusqueda.add(new WikiDTO(w));
                });
            } else {
                fragmentoWiki = new WikiDTO(records.get(0));
                acumularVista(fragmentoWiki.getIdWiki());
            }
        }
    }

    private void acumularVista(Integer id) {
        Wiki wiki = wikiFacade.find(id);

        if (wiki != null && wiki.getIdWiki() != null && wiki.getIdWiki() != 0) {
            wiki.setVisitas(wiki.getVisitas() + 1);

            try {
                wikiFacade.edit(wiki);
            } catch (Exception e) {
            }
        }
    }

    public void obtenerPaso() {
        if (paso == 1) {
            paso = 2;
        } else {
            paso = 1;
        }
    }

    public void convertirTitulo() {
        String[] s = titulo.split(" ");
        titulo = "";
        for (String t : s) {
            titulo += t.substring(0, 1).toUpperCase();
            titulo += t.substring(1);
            titulo += " ";
        }
    }

    public void seleccionarPropiedad() {
        String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo");

        switch (tipo) {
            case "bold":
                bold = !bold;
                texto += bold ? "<b>" : "</b>";
                convertirTexto();
                html = false;
                break;
            case "italic":
                italic = !italic;
                texto += italic ? "<i>" : "</i>";
                html = false;
                break;
            case "html":
                html = !html;
                bold = false;
                italic = false;
                list = false;
                left = false;
                center = false;
                right = false;
                break;
            case "enter":
                texto += "<br/>\n";
                break;
            case "list":
                list = !list;
                texto += list ? "\n<br/><br/><ul><li></li>" : "</ul><br/>\n";
                html = false;
                break;
            case "left":
                left = !left;
                center = false;
                right = false;
                html = false;
                break;
            case "center":
                center = !center;
                left = false;
                right = false;
                html = false;
                break;
            case "right":
                right = !right;
                left = false;
                center = false;
                html = false;
                break;
            default:
                break;
        }
    }

    public void convertirTexto() {
        codigo = texto;
    }

    public void guardarLink() {
        if (texto == null) {
            texto = "";
        }
        if (link == null || link.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar la ruta que desea que el usuario use.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar la ruta que desea que el usuario use");
            return;
        }

        texto += "<a target=\"_blank\" href=\"" + link + "\">" + ((textMostrar == null || textMostrar.isEmpty()) ? link : textMostrar) + "</a>";
        convertirTexto();
    }

    public String fileName(Part part) {
        if (part != null) {
            for (String cd : part.getHeader("content-disposition").split(";")) {
                if (cd.trim().startsWith("filename")) {
                    String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                    return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1);
                }
            }
        }
        return "";
    }

    public void subirImagen() {
        if (file != null) {
            try {
                CONSOLE.log(Level.INFO, "Recibiendo archivo: {0}", file.getSubmittedFileName());
                CONSOLE.log(Level.INFO, "Tipo contenido: {0}", file.getContentType());
                if (file.getContentType().contains("image/jpeg") || file.getContentType().contains("image/png")) {
                    String nombre = String.valueOf(System.currentTimeMillis()) + ".png";
                    InputStream input = file.getInputStream();
                    File archivo = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "wiki" + File.separator, nombre);
                    Files.copy(input, archivo.toPath());
                    imagen = applicationMBean.obtenerValorPropiedad("url.web.shared") + "wiki/" + nombre;
                    file = null;
                } else {
                    mostrarMensaje("Error", "El formato de la imagen que está intentando subir no está permitido.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "El formato de la imagen que esta intentando subir no esta permitido");
                    return;
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar la imagen. ", e);
            }
        }
    }

    public void aplicarImagen() {
        if (texto == null) {
            texto = "";
        }
        texto += "<br/><br/><div class=\"row\"><div class=\"col-xs-8 col-xs-offset-2\"><img class=\"img-responsive\" src=\"" + imagen + "\"/></div></div><br/>";
        convertirTexto();
    }

    public void guardarWiki() {
        if (titulo == null || titulo.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el título que le desea colocar a la publicación en la Wiki.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar el titulo que le desea colocar a la publicacion en la Wiki");
            return;
        }
        if (texto == null || texto.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el texto que quiere mostrarle al usuario.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar el texto que quiere mostrarle al usuario");
            return;
        }

        convertirTitulo();
        convertirTexto();

        Wiki wiki = new Wiki();

        wiki.setActivo(true);
        wiki.setFecha(new Date());
        wiki.setIdSolicitud(idSoporte);
        wiki.setTexto(codigo);
        wiki.setTitulo(titulo);
        wiki.setUsuario(sessionMBean.getUsuario());
        wiki.setVisitas(0);

        try {
            wikiFacade.create(wiki);
            mostrarMensaje("Éxito", "Se creó el registro en la Wiki con id " + wiki.getIdWiki(), false, true, false);
            CONSOLE.log(Level.INFO, "Se creo registro en el Wiki con id {0}", wiki.getIdWiki());
            limpiarRegistroWiki();
            obtenerIndex();
            obtenerRegistrosRelevantes();
        } catch (Exception e) {
            mostrarMensaje("Error", "Ocurrió un error al crear el registro en la Wiki " + e.getMessage(), true, false, false);
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el registro en la Wiki", e);
            return;
        }
    }

    public void limpiarRegistroWiki() {
        titulo = null;
        texto = null;
        codigo = null;
        textMostrar = null;
        link = null;
        imagen = null;
        bold = false;
        italic = false;
        html = false;
        list = false;
        left = false;
        center = false;
        right = false;
        file = null;
    }

    private void mostrarMensaje(String resumen, String mensaje, boolean error, boolean informacion, boolean advertencia) {
        FacesMessage msg = null;
        if (error) {
            msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, resumen, mensaje);
        } else if (advertencia) {
            msg = new FacesMessage(FacesMessage.SEVERITY_WARN, resumen, mensaje);
        } else if (informacion) {
            msg = new FacesMessage(FacesMessage.SEVERITY_INFO, resumen, mensaje);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
}
