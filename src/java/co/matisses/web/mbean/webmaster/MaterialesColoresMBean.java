package co.matisses.web.mbean.webmaster;

import co.matisses.persistence.sap.entity.BaruColor;
import co.matisses.persistence.sap.entity.BaruColorArticulo;
import co.matisses.persistence.sap.entity.BaruColorGenerico;
import co.matisses.persistence.sap.entity.BaruMaterialArticulo;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.facade.BaruColorArticuloFacade;
import co.matisses.persistence.sap.facade.BaruColorFacade;
import co.matisses.persistence.sap.facade.BaruColorGenericoFacade;
import co.matisses.persistence.sap.facade.BaruMaterialArticuloFacade;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.web.dto.BaruColorDTO;
import co.matisses.web.dto.BaruColorGenericoDTO;
import co.matisses.web.dto.BaruMaterialesDTO;
import co.matisses.web.dto.ItemInventarioDTO;
import co.matisses.web.mbean.BaruGenericMBean;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Named(value = "materialesColoresMBean")
public class MaterialesColoresMBean implements Serializable {

    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(MaterialesColoresMBean.class.getSimpleName());
    private int itemsSeleccionados = 0;
    private String nombreMaterial;
    private String cuidadosMaterial;
    private String nombreColor;
    private String colorGenerico;
    private String hexaColor;
    private String parametroBusqueda;
    private boolean dlgMateriales = false;
    private boolean dlgColores = false;
    private boolean verConfiguracion = false;
    private Part file;
    private List<BaruColorGenericoDTO> coloresGenericos;
    private List<ItemInventarioDTO> items;
    private List<BaruMaterialesDTO> materiales;
    private List<BaruColorDTO> colores;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private BaruColorFacade baruColorFacade;
    @EJB
    private BaruColorGenericoFacade baruColorGenericoFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private BaruMaterialArticuloFacade baruMaterialArticuloFacade;
    @EJB
    private BaruColorArticuloFacade baruColorArticuloFacade;

    public MaterialesColoresMBean() {
        coloresGenericos = new ArrayList<>();
        items = new ArrayList<>();
        materiales = new ArrayList<>();
        colores = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerMateriales();
        obtenerColores();
    }

    public String getNombreMaterial() {
        return nombreMaterial;
    }

    public void setNombreMaterial(String nombreMaterial) {
        this.nombreMaterial = nombreMaterial;
    }

    public String getCuidadosMaterial() {
        return cuidadosMaterial;
    }

    public void setCuidadosMaterial(String cuidadosMaterial) {
        this.cuidadosMaterial = cuidadosMaterial;
    }

    public String getNombreColor() {
        return nombreColor;
    }

    public void setNombreColor(String nombreColor) {
        this.nombreColor = nombreColor;
    }

    public String getColorGenerico() {
        return colorGenerico;
    }

    public void setColorGenerico(String colorGenerico) {
        this.colorGenerico = colorGenerico;
    }

    public String getHexaColor() {
        return hexaColor;
    }

    public void setHexaColor(String hexaColor) {
        this.hexaColor = hexaColor;
    }

    public String getParametroBusqueda() {
        return parametroBusqueda;
    }

    public void setParametroBusqueda(String parametroBusqueda) {
        this.parametroBusqueda = parametroBusqueda;
    }

    public boolean isDlgMateriales() {
        return dlgMateriales;
    }

    public void setDlgMateriales(boolean dlgMateriales) {
        this.dlgMateriales = dlgMateriales;
    }

    public boolean isDlgColores() {
        return dlgColores;
    }

    public void setDlgColores(boolean dlgColores) {
        this.dlgColores = dlgColores;
    }

    public boolean isVerConfiguracion() {
        return verConfiguracion;
    }

    public void setVerConfiguracion(boolean verConfiguracion) {
        this.verConfiguracion = verConfiguracion;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public List<BaruColorGenericoDTO> getColoresGenericos() {
        return coloresGenericos;
    }

    public void setColoresGenericos(List<BaruColorGenericoDTO> coloresGenericos) {
        this.coloresGenericos = coloresGenericos;
    }

    public List<ItemInventarioDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemInventarioDTO> items) {
        this.items = items;
    }

    public List<BaruMaterialesDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<BaruMaterialesDTO> materiales) {
        this.materiales = materiales;
    }

    public List<BaruColorDTO> getColores() {
        return colores;
    }

    public void setColores(List<BaruColorDTO> colores) {
        this.colores = colores;
    }

    public void abrirDlgMaterial() {
        dlgMateriales = true;
    }

    public void crearMaterial() {
        if (nombreMaterial == null || nombreMaterial.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar el nombre del material que quiere agregar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar el nombre del material que quiere agregar");
            return;
        }
        if (cuidadosMaterial == null || cuidadosMaterial.isEmpty()) {
            mostrarMensaje("Error", "Debe ingresar los cuidados correspondientes al material que desea agregar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar los cuidados correspondientes al material que desea agregar");
            return;
        }

        BaruMateriales material = new BaruMateriales();

        material.setCode(baruMaterialesFacade.obtenerSiguienteID());
        material.setName(nombreMaterial.toUpperCase());
        material.setuCuidados(cuidadosMaterial);

        try {
            baruMaterialesFacade.create(material);
            CONSOLE.log(Level.INFO, "Se creo un nuevo material con id {0} y nombre {1}", new Object[]{material.getCode(), material.getName()});
            mostrarMensaje("Éxito", "Se creó el material " + material.getName(), false, true, false);
            nombreMaterial = null;
            cuidadosMaterial = null;
            dlgMateriales = false;
            obtenerMateriales();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el material. ", e);
            mostrarMensaje("Error", "Ocurrió un error al crear el material.", true, false, false);
            return;
        }
    }

    public void abrirDlgColor() {
        dlgColores = true;
        obtenerColoresGenericos();
    }

    private void obtenerColoresGenericos() {
        coloresGenericos = new ArrayList<>();

        List<BaruColorGenerico> generics = baruColorGenericoFacade.findAll();

        if (generics != null & !generics.isEmpty()) {
            for (BaruColorGenerico b : generics) {
                coloresGenericos.add(new BaruColorGenericoDTO(b.getCode(), b.getName()));
            }

            Collections.sort(coloresGenericos, new Comparator<BaruColorGenericoDTO>() {
                @Override
                public int compare(BaruColorGenericoDTO o1, BaruColorGenericoDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    public void crearColor() {
        if (colorGenerico == null || colorGenerico.isEmpty()) {
            mostrarMensaje("Error", "Debe seleccionar el color genérico.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar el color generico");
            return;
        }
        if (nombreColor == null || nombreColor.isEmpty()) {
            mostrarMensaje("error", "Debe ingresar el nombre del color a crear.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar el nombre del color a crear.");
            return;
        }
        if (hexaColor == null || hexaColor.isEmpty()) {
            mostrarMensaje("Error", "Debe seleccionar el color de la paleta disponible.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar el color de la paleta disponible");
            return;
        }

        BaruColor color = new BaruColor();

        color.setCode(baruColorFacade.obtenerSiguienteID());
        color.setCodigoHexa(hexaColor);
        color.setColorGenerico(colorGenerico);
        color.setName(nombreColor);

        try {
            baruColorFacade.create(color);
            CONSOLE.log(Level.INFO, "Se creo un nuevo color con id {0} y nombre {1}", new Object[]{color.getCode(), color.getName()});
            mostrarMensaje("Éxito", "Se creó el color " + color.getName(), false, true, false);
            nombreColor = null;
            hexaColor = null;
            colorGenerico = null;
            obtenerColores();
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al crear el color. ", e);
            mostrarMensaje("Error", "Ocurrió un error al crear el color.", true, false, false);
            return;
        }
    }

    public void buscarItems() {
        itemsSeleccionados = 0;
        verConfiguracion = false;
        items = new ArrayList<>();
        if (parametroBusqueda == null || parametroBusqueda.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un valor en el campo de parámetro de búsqueda.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese un valor en el campo de parametro de busqueda");
        }

        List<ItemInventario> itemsInv = itemInventarioFacade.obtenerReferenciasParametro(parametroBusqueda);

        if (itemsInv != null && !itemsInv.isEmpty()) {
            for (ItemInventario i : itemsInv) {
                items.add(new ItemInventarioDTO(i.getItemCode(), i.getItemName(), i.getUURefPro(), i.getUdescripciona()));
            }

            obtenerColores();
            obtenerMateriales();
        }
    }

    private void obtenerMateriales() {
        materiales = new ArrayList<>();

        List<BaruMateriales> materials = baruMaterialesFacade.findAll();

        if (materials != null && !materials.isEmpty()) {
            for (BaruMateriales m : materials) {
                materiales.add(new BaruMaterialesDTO(m.getCode(), m.getName(), m.getuCuidados(), m.getuParte(), m.getuNombreWeb()));
            }

            Collections.sort(materiales, new Comparator<BaruMaterialesDTO>() {
                @Override
                public int compare(BaruMaterialesDTO o1, BaruMaterialesDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void obtenerColores() {
        colores = new ArrayList<>();

        List<BaruColor> colors = baruColorFacade.findAll();

        if (colors != null && !colors.isEmpty()) {
            for (BaruColor c : colors) {
                colores.add(new BaruColorDTO(c.getCode(), c.getName(), c.getColorGenerico(), "#" + c.getCodigoHexa()));
            }

            Collections.sort(colores, new Comparator<BaruColorDTO>() {
                @Override
                public int compare(BaruColorDTO o1, BaruColorDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    public void seleccionarItem() {
        verConfiguracion = false;
        String referencia = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("itemCode");

        for (ItemInventarioDTO i : items) {
            if (i.getItemCode().equals(referencia)) {
                if (i.isSeleccionado()) {
                    i.setSeleccionado(false);
                    itemsSeleccionados--;
                } else {
                    List<BaruMaterialArticulo> materials = baruMaterialArticuloFacade.obtenerMaterialesArticulo(i.getItemCode());
                    List<BaruColorArticulo> colors = baruColorArticuloFacade.obtenerColoresArticulo(referencia);

                    if (((materials != null && !materials.isEmpty()) || (colors != null && !colors.isEmpty())) && itemsSeleccionados > 0) {
                        mostrarMensaje("Error", "Únicamente puede seleccionar más de un ítem a la vez, si ninguno de estos tiene una configuración anterior.", true, false, false);
                        CONSOLE.log(Level.SEVERE, "Unicamente puede seleccionar mas de un item a la vez, si ninguno de estos tiene una configuracion anterior");
                        break;
                    }
                    i.setSeleccionado(true);
                    itemsSeleccionados++;
                    if (itemsSeleccionados > 0) {
                        obtenerDatosItem(referencia);
                    }
                }
                break;
            }
        }

        int contador = 0;
        for (ItemInventarioDTO i : items) {
            if (i.isSeleccionado()) {
                i.setPosicion(contador);
                contador++;
            }
        }

        if (contador > 0) {
            verConfiguracion = true;
        }
    }

    private void obtenerDatosItem(String referencia) {
        /*Se evaluan los materiales*/
        List<BaruMaterialArticulo> materials = baruMaterialArticuloFacade.obtenerMaterialesArticulo(referencia);

        if (materials != null && !materials.isEmpty()) {
            for (BaruMaterialesDTO m : materiales) {
                for (BaruMaterialArticulo a : materials) {
                    if (m.getCode().equals(a.getuMatCode())) {
                        m.setSeleccionado(true);
                        break;
                    } else if (m.isSeleccionado()) {
                        m.setSeleccionado(false);
                    }
                }
            }
        }

        /*Se evaluan los colores*/
        List<BaruColorArticulo> colors = baruColorArticuloFacade.obtenerColoresArticulo(referencia);

        if (colors != null && !colors.isEmpty()) {
            for (BaruColorDTO c : colores) {
                for (BaruColorArticulo a : colors) {
                    if (a.getuColor().equals(c.getCode())) {
                        c.setSeleccionado(true);
                        if (a.getuPrincipal().equals("1")) {
                            c.setPrincipal(true);
                        }
                        break;
                    } else if (c.isSeleccionado()) {
                        c.setSeleccionado(false);
                    }
                }
            }
        }
    }

    public void seleccionarMaterialDisponible() {
        String code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        for (BaruMaterialesDTO m : materiales) {
            if (m.getCode().equals(code)) {
                for (ItemInventarioDTO i : items) {
                    if (i.isSeleccionado()) {
                        BaruMaterialArticulo baru = new BaruMaterialArticulo();

                        baru.setCode(String.valueOf(baruMaterialArticuloFacade.obtenerSiguienteCodigo() + 1));
                        baru.setName(genericMBean.convertirARefCorta(i.getItemCode()).replace("*", "") + "-" + code);
                        baru.setuItemCode(i.getItemCode());
                        baru.setuMatCode(code);

                        try {
                            baruMaterialArticuloFacade.create(baru);
                            CONSOLE.log(Level.INFO, "Se creo el material x articulo con ID {0}", baru.getCode());
                        } catch (Exception e) {
                        }
                    }
                }
                m.setSeleccionado(true);
                break;
            }
        }
    }

    public void seleccionarMaterialSeleccionado() {
        String code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        for (BaruMaterialesDTO m : materiales) {
            if (m.getCode().equals(code)) {
                for (ItemInventarioDTO i : items) {
                    if (i.isSeleccionado()) {
                        List<BaruMaterialArticulo> materials = baruMaterialArticuloFacade.obtenerMaterialesArticulo(i.getItemCode());

                        for (BaruMaterialArticulo a : materials) {
                            if (a.getuMatCode().equals(m.getCode())) {
                                try {
                                    baruMaterialArticuloFacade.remove(a);
                                    CONSOLE.log(Level.INFO, "Se elimino el material x articulo con ID {0}", m.getCode());
                                } catch (Exception e) {
                                }
                                break;
                            }
                        }
                    }
                }
                m.setSeleccionado(false);
                break;
            }
        }
    }

    public void seleccionarColorDisponible() {
        String code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        int cantColoresSeleccionados = 0;
        for (BaruColorDTO c : colores) {
            if (c.isSeleccionado()) {
                cantColoresSeleccionados++;
            }
        }

        for (BaruColorDTO c : colores) {
            if (c.getCode().equals(code)) {
                for (ItemInventarioDTO i : items) {
                    if (i.isSeleccionado()) {
                        BaruColorArticulo baru = new BaruColorArticulo();

                        baru.setCode(String.valueOf(baruColorArticuloFacade.obtenerSiguienteCodigo() + 1));
                        baru.setName(genericMBean.convertirARefCorta(i.getItemCode()).replace("*", "") + "-" + code);
                        baru.setuArticulo(i.getItemCode());
                        baru.setuColor(code);
                        if (cantColoresSeleccionados == 0) {
                            baru.setuPrincipal("1");
                            c.setPrincipal(true);
                        } else {
                            baru.setuPrincipal("0");
                            c.setPrincipal(false);
                        }

                        try {
                            baruColorArticuloFacade.create(baru);
                            CONSOLE.log(Level.INFO, "Se creo el color x articulo con ID {0}", baru.getCode());
                        } catch (Exception e) {
                        }
                    }
                }
                c.setSeleccionado(true);
                break;
            }
        }
    }

    public void seleccionarColorSeleccionado() {
        String code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        for (BaruColorDTO c : colores) {
            if (c.getCode().equals(code)) {
                for (ItemInventarioDTO i : items) {
                    if (i.isSeleccionado()) {
                        List<BaruColorArticulo> colors = baruColorArticuloFacade.obtenerColoresArticulo(i.getItemCode());

                        for (BaruColorArticulo a : colors) {
                            if (a.getuColor().equals(c.getCode())) {
                                try {
                                    baruColorArticuloFacade.remove(a);
                                    CONSOLE.log(Level.INFO, "Se elimino el color x articulo con ID {0}", c.getCode());
                                } catch (Exception e) {
                                }
                                break;
                            }
                        }
                    }
                }
                c.setSeleccionado(false);
                break;
            }
        }
    }

    public void seleccionarColorPrincipal() {
        String code = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("code");

        for (ItemInventarioDTO i : items) {
            List<BaruColorArticulo> colors = baruColorArticuloFacade.obtenerColoresArticulo(i.getItemCode());

            for (BaruColorArticulo a : colors) {
                for (BaruColorDTO c : colores) {
                    if (a.getuColor().equals(c.getCode()) && a.getuColor().equals(code)) {
                        a.setuPrincipal("1");
                        try {
                            baruColorArticuloFacade.edit(a);
                            CONSOLE.log(Level.INFO, "Se marco el color x articulo con ID {0} como principal", a.getCode());
                        } catch (Exception e) {
                        }
                        c.setPrincipal(true);
                        break;
                    } else if (a.getuColor().equals(c.getCode()) && !a.getuColor().equals(code)) {
                        a.setuPrincipal("0");
                        try {
                            baruColorArticuloFacade.edit(a);
                            CONSOLE.log(Level.INFO, "Se desmarco el color x articulo con ID {0} como principal", a.getCode());
                        } catch (Exception e) {
                        }
                        c.setPrincipal(false);
                    }
                }
            }
        }
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

    public void validarDocumentoCargado() {
        items = new ArrayList<>();
        if (file != null) {
            try {
                CONSOLE.log(Level.INFO, "Recibiendo archivo: {0}", fileName(file));
                CONSOLE.log(Level.INFO, "Tipo contenido: {0}", file.getContentType());
                if (file.getContentType().contains("text/plain")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                    String line = reader.readLine();
                    List<ItemInventario> itemsInv = new ArrayList<>();
                    while (line != null) {
                        if (!line.trim().isEmpty()) {
                            itemsInv.addAll(itemInventarioFacade.obtenerReferenciasParametro(line));
                        }
                        line = reader.readLine();
                    }

                    if (itemsInv != null && !itemsInv.isEmpty()) {
                        for (ItemInventario i : itemsInv) {
                            items.add(new ItemInventarioDTO(i.getItemCode(), i.getItemName(), i.getUURefPro(), i.getUdescripciona()));
                        }
                    }
                } else {
                    mostrarMensaje("Error", "La extensión no es válida, formato permitido .txt.", true, false, false);
                    CONSOLE.log(Level.SEVERE, "La extension no es valida, formato permitido .txt");
                    return;
                }
            } catch (Exception e) {
                CONSOLE.log(Level.SEVERE, "Error al subir el archivo. ", e);
            }
        }
    }

    public void limpiar() {
        itemsSeleccionados = 0;
        nombreMaterial = null;
        cuidadosMaterial = null;
        nombreColor = null;
        colorGenerico = null;
        hexaColor = null;
        parametroBusqueda = null;
        dlgMateriales = false;
        dlgColores = false;
        verConfiguracion = false;
        coloresGenericos = new ArrayList<>();
        items = new ArrayList<>();
        materiales = new ArrayList<>();
        colores = new ArrayList<>();
        initialize();
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
