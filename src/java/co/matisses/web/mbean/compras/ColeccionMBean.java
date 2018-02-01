package co.matisses.web.mbean.compras;

import co.matisses.persistence.sap.entity.BaruSubgrupo;
import co.matisses.persistence.sap.facade.BaruSubgrupoFacade;
import co.matisses.persistence.web.entity.CategoriaColeccion;
import co.matisses.persistence.web.entity.Coleccion;
import co.matisses.persistence.web.facade.CategoriaColeccionFacade;
import co.matisses.persistence.web.facade.ColeccionFacade;
import co.matisses.web.dto.CategoriaColeccionDTO;
import co.matisses.web.dto.ColeccionDTO;
import co.matisses.web.dto.GrupoArticuloDTO;
import co.matisses.web.dto.MensajeDTO;
import co.matisses.web.dto.SubgrupoArticuloDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "coleccionMBean")
public class ColeccionMBean implements Serializable {

    private static final Logger log = Logger.getLogger(ColeccionMBean.class.getSimpleName());
    private boolean botonCrearVisible = true;
    private boolean mostrarPanelLista = false;
    private String codigoGrupo;
    private String codigoSubgrupo;
    private ColeccionDTO coleccionDto;
    private List<ColeccionDTO> colecciones;
    private List<GrupoArticuloDTO> grupos;
    private List<SubgrupoArticuloDTO> todosSubgrupos;
    private List<SubgrupoArticuloDTO> subgruposFiltrados;
    private List<MensajeDTO> mensajes;

    @EJB
    private ColeccionFacade coleccionFacade;
    @EJB
    private CategoriaColeccionFacade categoriaColeccionFacade;
    @EJB
    private BaruSubgrupoFacade subgrupoFacade;

    public ColeccionMBean() {
        coleccionDto = new ColeccionDTO();
        grupos = new ArrayList<>();
        todosSubgrupos = new ArrayList<>();
        subgruposFiltrados = new ArrayList<>();
        colecciones = new ArrayList<>();
        mensajes = new ArrayList<>();
    }

//    private void cargarGrupos() {
//        grupos = new ArrayList<>();
//        for (BaruGrupo entidad : grupoFacade.findAll()) {
//            if (entidad.getUWeb().equals("1")) {
//                grupos.add(new GrupoArticuloDTO(entidad.getCode(), entidad.getName()));
//            }
//        }
//        Collections.sort(grupos);
//    }
    private void cargarSubgrupos() {
        todosSubgrupos = new ArrayList<>();
        for (BaruSubgrupo entidad : subgrupoFacade.findAll()) {
            if (entidad.getUWeb() != null && entidad.getUWeb().equals(Short.valueOf("1"))) {
                todosSubgrupos.add(new SubgrupoArticuloDTO(entidad.getUCodGrupo(), entidad.getCode(), entidad.getUNomGrupo() + " - " + entidad.getUdescripcion()));
            }
        }
        Collections.sort(todosSubgrupos);
    }

    @PostConstruct
    protected void initialize() {
//        cargarGrupos();
        cargarSubgrupos();
    }

    public List<ColeccionDTO> getColecciones() {
        return colecciones;
    }

    public void setColecciones(List<ColeccionDTO> colecciones) {
        this.colecciones = colecciones;
    }

    public boolean isMostrarPanelLista() {
        return mostrarPanelLista;
    }

    public void setMostrarPanelLista(boolean mostrarPanelLista) {
        this.mostrarPanelLista = mostrarPanelLista;
    }

    public boolean isBotonCrearVisible() {
        return botonCrearVisible;
    }

    public ColeccionDTO getColeccionDto() {
        return coleccionDto;
    }

    public void setColeccionDto(ColeccionDTO coleccionDto) {
        this.coleccionDto = coleccionDto;
    }

    public List<GrupoArticuloDTO> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<GrupoArticuloDTO> grupos) {
        this.grupos = grupos;
    }

    public List<SubgrupoArticuloDTO> getSubgruposFiltrados() {
        return subgruposFiltrados;
    }

    public void setSubgruposFiltrados(List<SubgrupoArticuloDTO> subgruposFiltrados) {
        this.subgruposFiltrados = subgruposFiltrados;
    }

    public List<MensajeDTO> getMensajes() {
        return mensajes;
    }

    public void setMensajes(List<MensajeDTO> mensajes) {
        this.mensajes = mensajes;
    }

    public String getCodigoGrupo() {
        return codigoGrupo;
    }

    public void setCodigoGrupo(String codigoGrupo) {
        this.codigoGrupo = codigoGrupo;
    }

    public String getCodigoSubgrupo() {
        return codigoSubgrupo;
    }

    public void setCodigoSubgrupo(String codigoSubgrupo) {
        this.codigoSubgrupo = codigoSubgrupo;
    }

    public void valueChangeListener() {
        log.info("Valor cambio");
    }

    private CategoriaColeccionDTO categoriaEntity2Dto(CategoriaColeccion entity) {
        CategoriaColeccionDTO dto = new CategoriaColeccionDTO(entity.getIdCategoriaColeccion(), entity.getCodigoSubgrupo(), entity.getNombreSubgrupo());
        return dto;
    }

    private ColeccionDTO coleccionEntity2Dto(Coleccion entity) {
        ColeccionDTO dto = new ColeccionDTO();
        dto.setCodigo(Integer.toString(entity.getIdColeccion()));
        dto.setNombre(entity.getNombre());
        dto.setFechaInicio(entity.getFecha());
        for (CategoriaColeccion categoria : entity.getCategoriaColeccionList()) {
            dto.agregarCategoria(categoriaEntity2Dto(categoria));
        }
        return dto;
    }

    public void buscarColeccion() {
        if ((coleccionDto.getCodigo() == null || coleccionDto.getCodigo().trim().isEmpty())
                && (coleccionDto.getNombre() == null || coleccionDto.getNombre().trim().isEmpty())) {
            agregarMensaje(new MensajeDTO(MensajeDTO.Tipo.ERROR, "No hay valores", "Debe ingresar ya sea el código o el nombre de la colección a buscar"), true);
            log.log(Level.SEVERE, "No se ingreso un criterio de busqueda");
            return;
        }
        boolean listarTodas = false;
        Coleccion resultado = null;
        if (coleccionDto.getCodigo() != null) {
            if (coleccionDto.getCodigo().equals("*")) {
                //listar todas las colecciones
                listarTodas = true;
            } else {
                //buscar por codigo
                log.log(Level.INFO, "Buscando coleccion por codigo [{0}]", coleccionDto.getCodigo());
                resultado = coleccionFacade.buscarPorCodigo(Integer.parseInt(coleccionDto.getCodigo()));
            }
        } else if (coleccionDto.getNombre().equals("*")) {
            //listar todas las colecciones
            listarTodas = true;
        } else {
            //buscar por nombre
            log.log(Level.INFO, "Buscando coleccion por nombre [{0}]", coleccionDto.getNombre());
            resultado = coleccionFacade.buscarPorNombre(coleccionDto.getNombre());
        }
        if (listarTodas) {
            borrarMensajes();
            colecciones = new ArrayList<>();
            for (Coleccion entidad : coleccionFacade.findAll()) {
                colecciones.add(coleccionEntity2Dto(entidad));
            }
            mostrarPanelLista = true;
            log.log(Level.INFO, "Se listaron todas las colecciones, [{0}] colecciones encontradas", colecciones.size());
        } else {
            if (resultado == null) {
                agregarMensaje(new MensajeDTO(MensajeDTO.Tipo.ERROR, "Valor inválido", "No se encontró ninguna colección con el código/nombre ingresado"), true);
                String codigo = coleccionDto.getCodigo();
                String nombre = coleccionDto.getNombre();
                coleccionDto = new ColeccionDTO();
                coleccionDto.setCodigo(codigo);
                coleccionDto.setNombre(nombre);

                log.log(Level.SEVERE, "No se encontraron colecciones con la informacion ingresada");
                botonCrearVisible = true;
                return;
            }
            borrarMensajes();
            coleccionDto = coleccionEntity2Dto(resultado);
            botonCrearVisible = false;
            log.log(Level.INFO, "Se encontro la coleccion [{0}] con [{1}] categorias", new Object[]{coleccionDto.getNombre(), coleccionDto.getCategorias().size()});
        }
    }

    private boolean contieneSubgrupo(String codigo) {
        for (CategoriaColeccionDTO dto : coleccionDto.getCategorias()) {
            if (dto.getCodigoCategoria().equals(codigo)) {
                return true;
            }
        }
        return false;
    }

    public void filtrarSubgrupos() {
        subgruposFiltrados = new ArrayList<>();
        for (SubgrupoArticuloDTO dto : todosSubgrupos) {
            if (dto.getCodigoGrupo().equals(codigoGrupo) && !contieneSubgrupo(dto.getCodigo())) {
                subgruposFiltrados.add(dto);
            }
        }
        Collections.sort(subgruposFiltrados);
    }

    public void limpiar() {
        botonCrearVisible = true;
        codigoGrupo = null;
        codigoSubgrupo = null;
        coleccionDto = new ColeccionDTO();
        filtrarSubgrupos();
    }

    public void crear() {
        if (coleccionDto.getNombre() == null || coleccionDto.getNombre().trim().isEmpty()) {
            //mostrar mensaje de error
            log.log(Level.SEVERE, "No se ingresaron los datos para crear la coleccion");
            return;
        }
        Coleccion entidad = new Coleccion();
        entidad.setIdColeccion(null);
        entidad.setNombre(coleccionDto.getNombre());
        coleccionFacade.create(entidad);
        coleccionDto.setCodigo(Integer.toString(entidad.getIdColeccion()));
        log.log(Level.INFO, "Se creo la coleccion [{0}]", coleccionDto.getCodigo());
        botonCrearVisible = false;
    }

    public void agregarCategoria() {
        if (codigoGrupo == null || codigoSubgrupo == null) {
            //mostrar error
            return;
        }
        Coleccion col = coleccionFacade.buscarPorCodigo(Integer.parseInt(coleccionDto.getCodigo()));

        CategoriaColeccion categoriaColeccion = new CategoriaColeccion();
        categoriaColeccion.setCodigoSubgrupo(codigoSubgrupo);
        categoriaColeccion.setNombreSubgrupo(obtenerNombreSubgrupo(codigoSubgrupo));
        categoriaColeccion.setIdColeccion(col);

        col.getCategoriaColeccionList().add(categoriaColeccion);
        coleccionFacade.edit(col);

        col = coleccionFacade.buscarPorCodigo(Integer.parseInt(coleccionDto.getCodigo()));
        coleccionDto = coleccionEntity2Dto(col);

        codigoGrupo = null;
        codigoSubgrupo = null;
    }

    private String obtenerNombreSubgrupo(String codigo) {
        for (SubgrupoArticuloDTO dto : subgruposFiltrados) {
            if (dto.getCodigo().equals(codigo)) {
                return dto.getNombre();
            }
        }
        return null;
    }

    public void eliminarCategoria() {
        String idCategoriaColeccion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCategoriaColeccion");
        log.log(Level.INFO, "Se elimina la categoria [{0}] de la coleccion [{1}]", new Object[]{idCategoriaColeccion, coleccionDto.getCodigo()});
        if (idCategoriaColeccion == null) {
            //mostrar error
            return;
        }

        categoriaColeccionFacade.eliminarCategoriaColecccion(Integer.parseInt(idCategoriaColeccion));

        Coleccion col = coleccionFacade.buscarPorCodigo(Integer.parseInt(coleccionDto.getCodigo()));

        coleccionFacade.edit(col);
        coleccionDto = coleccionEntity2Dto(col);
        filtrarSubgrupos();
    }

    public void actualizar() {
        log.log(Level.INFO, "Actualizando informacion de la coleccion [{0}]", coleccionDto.getCodigo());
        Coleccion entidad = coleccionFacade.buscarPorCodigo(Integer.parseInt(coleccionDto.getCodigo()));
        entidad.setNombre(coleccionDto.getNombre());
        entidad.setFecha(coleccionDto.getFechaInicio());

        coleccionFacade.edit(entidad);

        //mostrar mensaje
    }

    public void seleccionarColeccion() {
        String codigoColeccion = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("codigoColeccion");
        colecciones = new ArrayList<>();
        coleccionDto = new ColeccionDTO();
        coleccionDto.setCodigo(codigoColeccion);
        buscarColeccion();
        mostrarPanelLista = false;
    }

    private void agregarMensaje(MensajeDTO mensaje, boolean limpiarMensajes) {
        if (limpiarMensajes) {
            mensajes = new ArrayList<>();
        }
        mensajes.add(mensaje);
    }

    private void borrarMensajes() {
        mensajes = new ArrayList<>();
    }
}
