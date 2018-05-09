package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.dwb.entity.Filtro;
import co.matisses.persistence.dwb.facade.FiltroFacade;
import co.matisses.web.dto.FiltroDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "filtrosRotacionMBean")
public class FiltrosRotacionMBean implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(FiltrosRotacionMBean.class.getSimpleName());
    private Integer idFiltro;
    private String nombre;
    private String columna;
    private String tipoDato;
    private String sufijo;
    private boolean tipoReferencia = false;
    private boolean valoresMultiples = false;
    private List<FiltroDTO> filtros;
    @EJB
    private FiltroFacade filtroFacade;

    public FiltrosRotacionMBean() {
        filtros = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarFiltros();
    }

    public Integer getIdFiltro() {
        return idFiltro;
    }

    public void setIdFiltro(Integer idFiltro) {
        this.idFiltro = idFiltro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColumna() {
        return columna;
    }

    public void setColumna(String columna) {
        this.columna = columna;
    }

    public String getTipoDato() {
        return tipoDato;
    }

    public void setTipoDato(String tipoDato) {
        this.tipoDato = tipoDato;
    }

    public String getSufijo() {
        return sufijo;
    }

    public void setSufijo(String sufijo) {
        this.sufijo = sufijo;
    }

    public boolean isTipoReferencia() {
        return tipoReferencia;
    }

    public void setTipoReferencia(boolean tipoReferencia) {
        this.tipoReferencia = tipoReferencia;
    }

    public boolean isValoresMultiples() {
        return valoresMultiples;
    }

    public void setValoresMultiples(boolean valoresMultiples) {
        this.valoresMultiples = valoresMultiples;
    }

    public List<FiltroDTO> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<FiltroDTO> filtros) {
        this.filtros = filtros;
    }

    private void cargarFiltros() {
        filtros = new ArrayList<>();

        List<Filtro> filters = filtroFacade.findAll();

        if (filters != null && !filters.isEmpty()) {
            for (Filtro f : filters) {
                FiltroDTO dto = new FiltroDTO();

                dto.setCodigoColumna(f.getCodigoColumna());
                dto.setFachada(f.getFachada());
                dto.setFormato(f.getFormato());
                dto.setIdFiltro(f.getIdFiltro());
                dto.setMetodo(f.getMetodo());
                dto.setMultiplesValores(f.isMultiplesValores());
                dto.setNombre(f.getNombre());
                dto.setSufijo(f.getSufijo());
                dto.setTamanoMaximo(f.getTamanoMaximo());
                dto.setTamanoMinimo(f.getTamanoMinimo());
                dto.setTipo(f.getTipo());
                dto.setTipoReferencia(f.isTipoReferencia());
                dto.setTipoRetorno(f.getTipoRetorno());

                filtros.add(dto);
            }
        }
    }

    public void seleccionarTipoReferencia() {
        tipoReferencia = !tipoReferencia;
    }

    public void seleccionarValoresMultiples() {
        valoresMultiples = !valoresMultiples;
    }

    public void agregarFiltro() {
        if (nombre == null || nombre.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre que quiere para el filtro.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el nombre que quiere para el filtro");
            return;
        }
        if (columna == null || columna.isEmpty()) {
            mostrarMensaje("Error", "Ingrese la columna que se va a filtrar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese la columna que se va a filtrar");
            return;
        }
        if (tipoDato == null || tipoDato.isEmpty()) {
            mostrarMensaje("Error", "Seleccione el tipo de dato que se va a manejar.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Seleccione el tipo de dato que se va a manejar");
            return;
        }

        for (FiltroDTO f : filtros) {
            if (f.getNombre().equals(nombre.toUpperCase())) {
                mostrarMensaje("Error", "Ya existe un filtro con el nombre digitado.", true, false, false);
                CONSOLE.log(Level.SEVERE, "Ya existe un filtro con el nombre digitado");
                break;
            }
        }

        Filtro filtro = new Filtro();

        filtro.setCodigoColumna(columna);
        filtro.setMultiplesValores(valoresMultiples);
        filtro.setNombre(nombre.toUpperCase());
        filtro.setTipo(tipoDato);
        filtro.setTipoReferencia(tipoReferencia);

        try {
            filtroFacade.create(filtro);
            CONSOLE.log(Level.INFO, "Se registro el filtro con id {0}", filtro.getIdFiltro());
            limpiar();
        } catch (Exception e) {
        }
    }

    public void eliminarFiltro() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idFiltro"));

        for (FiltroDTO f : filtros) {
            if (f.getIdFiltro().equals(id)) {
                Filtro filtro = filtroFacade.find(id);

                if (filtro != null && filtro.getIdFiltro() != null && filtro.getIdFiltro() != 0) {
                    try {
                        filtroFacade.remove(filtro);
                        CONSOLE.log(Level.INFO, "Se elimino el filtro con id {0}", id);
                        limpiar();
                    } catch (Exception e) {
                        mostrarMensaje("Error", "No se puede eliminar el filtro debido a que ocurrió un error o el filtro esta en uso.", true, false, false);
                        CONSOLE.log(Level.SEVERE, "No se puede eliminar el filtro debido a que ocurrio un error o el filtro esta en uso", e);
                        return;
                    }
                }
                break;
            }
        }
    }

    public void limpiar() {
        idFiltro = null;
        nombre = null;
        columna = null;
        tipoDato = null;
        sufijo = null;
        tipoReferencia = false;
        valoresMultiples = false;
        cargarFiltros();
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
