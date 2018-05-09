package co.matisses.web.mbean.rotacion;

import co.matisses.persistence.dwb.entity.EstadoInforme;
import co.matisses.persistence.dwb.entity.Filtro;
import co.matisses.persistence.dwb.entity.FiltrosInforme;
import co.matisses.persistence.dwb.entity.InformeRotacion;
import co.matisses.persistence.dwb.facade.FiltroFacade;
import co.matisses.persistence.dwb.facade.FiltrosInformeFacade;
import co.matisses.persistence.dwb.facade.InformeRotacionFacade;
import co.matisses.persistence.sap.entity.BaruColor;
import co.matisses.persistence.sap.entity.BaruColorGenerico;
import co.matisses.persistence.sap.entity.BaruGrupo;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.entity.BaruSubgrupo;
import co.matisses.persistence.sap.facade.BaruColorFacade;
import co.matisses.persistence.sap.facade.BaruColorGenericoFacade;
import co.matisses.persistence.sap.facade.BaruGrupoFacade;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.persistence.sap.facade.BaruSubgrupoFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.web.entity.ProveedoresExterior;
import co.matisses.persistence.web.facade.ProveedoresExteriorFacade;
import co.matisses.web.dto.FiltroDTO;
import co.matisses.web.dto.FiltrosInformeDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "crearReporteRotacionMBean")
public class CrearReporteRotacionMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean userSessionInfoMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    private static final Logger CONSOLE = Logger.getLogger(CrearReporteRotacionMBean.class.getSimpleName());
    private Integer idFiltro;
    private String valor;
    private String nombre;
    private Date valorDate;
    private FiltroDTO filtro;
    private List<SelectItem> valores;
    private List<FiltroDTO> filtros;
    private List<FiltrosInformeDTO> filtrosInforme;
    @EJB
    private FiltroFacade filtroFacade;
    @EJB
    private InformeRotacionFacade informeRotacionFacade;
    @EJB
    private FiltrosInformeFacade filtrosInformeFacade;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private BaruSubgrupoFacade baruSubgrupoFacade;
    @EJB
    private BaruColorGenericoFacade baruColorGenericoFacade;
    @EJB
    private BaruColorFacade baruColorFacade;
    @EJB
    private BaruGrupoFacade baruGrupoFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private ProveedoresExteriorFacade proveedoresExteriorFacade;

    public CrearReporteRotacionMBean() {
        filtro = new FiltroDTO();
        valores = new ArrayList<>();
        filtros = new ArrayList<>();
        filtrosInforme = new ArrayList<>();
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

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getValorDate() {
        return valorDate;
    }

    public void setValorDate(Date valorDate) {
        this.valorDate = valorDate;
    }

    public FiltroDTO getFiltro() {
        return filtro;
    }

    public void setFiltro(FiltroDTO filtro) {
        this.filtro = filtro;
    }

    public List<SelectItem> getValores() {
        return valores;
    }

    public void setValores(List<SelectItem> valores) {
        this.valores = valores;
    }

    public List<FiltroDTO> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<FiltroDTO> filtros) {
        this.filtros = filtros;
    }

    public List<FiltrosInformeDTO> getFiltrosInforme() {
        return filtrosInforme;
    }

    public void setFiltrosInforme(List<FiltrosInformeDTO> filtrosInforme) {
        this.filtrosInforme = filtrosInforme;
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

        Collections.sort(filtros, new Comparator<FiltroDTO>() {
            @Override
            public int compare(FiltroDTO o1, FiltroDTO o2) {
                return o1.getNombre().compareTo(o2.getNombre());
            }
        });
    }

    public void seleccionarFiltro() {
        valores = new ArrayList<>();
        if (idFiltro != null && idFiltro != 0) {
            for (FiltroDTO f : filtros) {
                if (f.getIdFiltro().equals(idFiltro)) {
                    filtro = f;

                    if (null != f.getIdFiltro()) {
                        switch (f.getIdFiltro()) {
                            case 15:
                                List<BaruMateriales> materiales = baruMaterialesFacade.findAll();

                                for (BaruMateriales m : materiales) {
                                    valores.add(new SelectItem(m.getCode(), m.getName()));
                                }
                                break;
                            case 40: {
                                List<BaruColorGenerico> colores = baruColorGenericoFacade.findAll();

                                for (BaruColorGenerico c : colores) {
                                    valores.add(new SelectItem(c.getCode(), c.getName()));
                                }
                                break;
                            }
                            case 19:
                                List<ProveedoresExterior> proveedores = proveedoresExteriorFacade.findAll();

                                for (ProveedoresExterior p : proveedores) {
                                    if (p.getNombreProveedor() != null && !p.getNombreProveedor().isEmpty()) {
                                        valores.add(new SelectItem(p.getProveedor(), p.getNombreProveedor() + " - " + p.getProveedor()));
                                    } else {
                                        valores.add(new SelectItem(p.getProveedor(), p.getProveedor()));
                                    }
                                }
                                break;
                            case 26:
                                List<BaruSubgrupo> subgrupos = baruSubgrupoFacade.findAll();

                                for (BaruSubgrupo s : subgrupos) {
                                    valores.add(new SelectItem(s.getCode(), s.getName().replace("_", " ")));
                                }
                                break;
                            case 32: {
                                List<BaruColor> colores = baruColorFacade.findAll();

                                for (BaruColor c : colores) {
                                    valores.add(new SelectItem(c.getCode(), c.getName()));
                                }
                                break;
                            }
                            case 34: {
                                List<BaruGrupo> grupos = baruGrupoFacade.findAll();

                                for (BaruGrupo g : grupos) {
                                    valores.add(new SelectItem(g.getCode(), g.getName().replace("_", " ")));
                                }
                                break;
                            }
                            default:
                                break;
                        }
                    }

                    Collections.sort(valores, new Comparator<SelectItem>() {
                        @Override
                        public int compare(SelectItem o1, SelectItem o2) {
                            return o1.getLabel().compareTo(o2.getLabel());
                        }
                    });
                    break;
                }
            }
        }
    }

    public void agregarFiltro() {
        if (filtro == null || filtro.getIdFiltro() == null || filtro.getIdFiltro() == 0) {
            mostrarMensaje("Error", "No se ha seleccionado un filtro.", true, false, false);
            CONSOLE.log(Level.SEVERE, "No se ha seleccionado un filtro");
            return;
        }
        if (!(filtro.getIdFiltro() == 20 || filtro.getIdFiltro() == 21) && (valor == null || valor.isEmpty())) {
            mostrarMensaje("Error", "Ingrese el valor del filtro para poder agregar este.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el valor del filtro para poder agregar este");
            return;
        }

        if (filtro != null && filtro.getIdFiltro() == 12) {
            valor = genericMBean.completarReferencia(valor);

            if (valor.length() < filtro.getTamanoMaximo() || valor.length() > filtro.getTamanoMinimo()) {
                mostrarMensaje("Error", "El filtro de referencia no contiene la cantidad necesaria de caracteres ni los comodines.", true, false, false);
                CONSOLE.log(Level.SEVERE, "El filtro de referencia no contiene la cantidad necesaria de caracteres ni los comodines");
                return;
            }
        } else if (filtro != null && filtro.getIdFiltro() == 18) {
            if (!itemInventarioFacade.validarModelo(valor)) {
                mostrarMensaje("Error", "No se pudo encontrar el modelo digitado.", true, false, false);
                CONSOLE.log(Level.SEVERE, "No se pudo encontrar el modelo digitado {0}", valor);
                return;
            }
        } else if (filtro.getIdFiltro() == 20 || filtro.getIdFiltro() == 21) {
            if (valorDate == null) {
                mostrarMensaje("Error", "Seleccione la fecha del filtro para poder agregar este.", true, false, false);
                CONSOLE.log(Level.SEVERE, "Seleccione la fecha del filtro para poder agregar este");
                return;
            }

            valor = new SimpleDateFormat("dd/MM/yyyy").format(valorDate);
        }

        FiltrosInformeDTO filtroInforme = new FiltrosInformeDTO();

        filtroInforme.setFiltro(filtro);
        filtroInforme.setValor(valor);

        if (filtro.getIdFiltro() == 15 || filtro.getIdFiltro() == 40 || filtro.getIdFiltro() == 19 || filtro.getIdFiltro() == 26 || filtro.getIdFiltro() == 32 || filtro.getIdFiltro() == 34) {
            for (SelectItem s : valores) {
                if (s.getValue().equals(valor)) {
                    filtroInforme.setNombreValor(s.getLabel());
                    break;
                }
            }
        } else {
            filtroInforme.setNombreValor(valor);
        }

        filtrosInforme.add(0, filtroInforme);

        idFiltro = null;
        valor = null;
        valorDate = null;
        filtro = new FiltroDTO();
        valores = new ArrayList<>();
    }

    public void eliminarFiltro() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idFiltro"));
        String value = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("valor");

        for (FiltrosInformeDTO f : filtrosInforme) {
            if (f.getFiltro().getIdFiltro().equals(id) && f.getValor().equals(value)) {
                filtrosInforme.remove(f);
                break;
            }
        }
    }

    public void generarInforme() {
        if (nombre == null || nombre.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el nombre que le quiere asignar al informe.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el nombre que le quiere asignar al informe");
            return;
        }
        if (nombre.contains("\\") || nombre.contains("/") || nombre.contains(":") || nombre.contains("*") || nombre.contains("?")
                || nombre.contains("\"") || nombre.contains("<") || nombre.contains(">") || nombre.contains("|")) {
            mostrarMensaje("Error", "Los nombres de los reportes no pueden contener ninguno de los siguientes caracteres: \\ / : * ? \" < > |", true, false, false);
            CONSOLE.log(Level.SEVERE, "Los nombres de los reportes no pueden contener ninguno de los siguientes caracteres: \\ / : * ? \" < > |");
            return;
        }

        InformeRotacion informe = new InformeRotacion();

        informe.setAutor(userSessionInfoMBean.getUsuario());
        informe.setCorreo(userSessionInfoMBean.getUsuario() + "@matisses.co");
        informe.setEstado(new EstadoInforme(1));
        informe.setFecha(new Date());
        informe.setNombre(nombre.toUpperCase());

        try {
            informeRotacionFacade.create(informe);
            CONSOLE.log(Level.INFO, "Se registro el informe de rotacion con id {0}", informe.getIdInforme());

            for (FiltrosInformeDTO f : filtrosInforme) {
                FiltrosInforme filtroInforme = new FiltrosInforme();

                filtroInforme.setFiltro(new Filtro(f.getFiltro().getIdFiltro()));
                filtroInforme.setIdInforme(informe.getIdInforme());
                filtroInforme.setValor(f.getValor());

                filtrosInformeFacade.create(filtroInforme);
            }

            limpiarCrear();
            mostrarMensaje("Éxito", "Su informe se esta procesando en este momento.", false, true, false);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al registrar el informe de rotacion. ", e);
        }
    }

    public void limpiarCrear() {
        idFiltro = null;
        valor = null;
        nombre = null;
        valorDate = null;
        filtro = new FiltroDTO();
        valores = new ArrayList<>();
        filtros = new ArrayList<>();
        filtrosInforme = new ArrayList<>();
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
