package co.matisses.web.mbean.servicio;

import co.matisses.persistence.sap.entity.Actividad;
import co.matisses.persistence.sap.entity.BaruAveriasMaterial;
import co.matisses.persistence.sap.entity.BaruMateriales;
import co.matisses.persistence.sap.entity.DepartamentoSAP;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegocios;
import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.LlamadaServicio;
import co.matisses.persistence.sap.entity.LugarReunion;
import co.matisses.persistence.sap.entity.Pais;
import co.matisses.persistence.sap.entity.ServiceCallHistory;
import co.matisses.persistence.sap.entity.ServiceCallOrigins;
import co.matisses.persistence.sap.entity.ServiceCallProblemSubtype;
import co.matisses.persistence.sap.entity.ServiceCallProblemTypes;
import co.matisses.persistence.sap.entity.ServiceCallSolutionStatuses;
import co.matisses.persistence.sap.entity.ServiceCallStatus;
import co.matisses.persistence.sap.entity.ServiceCallTypes;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.entity.TemaActividad;
import co.matisses.persistence.sap.entity.TipoActividad;
import co.matisses.persistence.sap.entity.Usuario;
import co.matisses.persistence.sap.entity.ValoresUsuario;
import co.matisses.persistence.sap.facade.ActividadFacade;
import co.matisses.persistence.sap.facade.BaruAveriasMaterialFacade;
import co.matisses.persistence.sap.facade.BaruMaterialesFacade;
import co.matisses.persistence.sap.facade.DepartamentoSAPFacade;
import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.FacturaSAPFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.LlamadaServicioFacade;
import co.matisses.persistence.sap.facade.LugarReunionFacade;
import co.matisses.persistence.sap.facade.PaisFacade;
import co.matisses.persistence.sap.facade.ServiceCallHistoryFacade;
import co.matisses.persistence.sap.facade.ServiceCallOriginsFacade;
import co.matisses.persistence.sap.facade.ServiceCallProblemSubtypeFacade;
import co.matisses.persistence.sap.facade.ServiceCallProblemTypesFacade;
import co.matisses.persistence.sap.facade.ServiceCallSolutionStatusesFacade;
import co.matisses.persistence.sap.facade.ServiceCallStatusFacade;
import co.matisses.persistence.sap.facade.ServiceCallTypesFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.TemaActividadFacade;
import co.matisses.persistence.sap.facade.TipoActividadFacade;
import co.matisses.persistence.sap.facade.UsuarioFacade;
import co.matisses.persistence.sap.facade.ValoresUsuarioFacade;
import co.matisses.persistence.web.entity.LlamadaSolucion;
import co.matisses.persistence.web.facade.LlamadaSolucionFacade;
import co.matisses.web.bcs.client.ServiceCallsClient;
import co.matisses.web.bcs.client.SondaSolucionesLlamadaClient;
import co.matisses.web.bcs.servicecalls.ServiceCall;
import co.matisses.web.dto.ActividadDTO;
import co.matisses.web.dto.AdjuntoLlamadaServicioDTO;
import co.matisses.web.dto.BaruAveriasMaterialDTO;
import co.matisses.web.dto.BaruMaterialesDTO;
import co.matisses.web.dto.DepartamentoDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.LlamadaServicioDTO;
import co.matisses.web.dto.LlamadaSolucionDTO;
import co.matisses.web.dto.LugarReunionDTO;
import co.matisses.web.dto.PaisDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.ServiceCallHistoryDTO;
import co.matisses.web.dto.ServiceCallOriginsDTO;
import co.matisses.web.dto.ServiceCallProblemSubtypeDTO;
import co.matisses.web.dto.ServiceCallProblemTypesDTO;
import co.matisses.web.dto.ServiceCallSolutionStatusesDTO;
import co.matisses.web.dto.ServiceCallStatusDTO;
import co.matisses.web.dto.ServiceCallTypesDTO;
import co.matisses.web.dto.TemaActividadDTO;
import co.matisses.web.dto.TipoActividadDTO;
import co.matisses.web.dto.UsuarioDTO;
import co.matisses.web.dto.ValoresUsuarioDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.BaruGenericMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
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
@Named(value = "llamadaServicioMBean")
public class LlamadaServicioMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    @Inject
    private BaruGenericMBean genericMBean;
    @Inject
    private UserSessionInfoMBean sessionInfoMBean;
    private static final Logger CONSOLE = Logger.getLogger(LlamadaServicioMBean.class.getSimpleName());
    private String tipoAsignar = "U";
    private String msgGarantia;
    private boolean dlgGarantia = false;
    private boolean dlgLlamadas = false;
    private LlamadaSolucionDTO solucion;
    private ServiceCall serviceCall;
    private Part adjunto;
    private List<String> direccionesEntrega;
    private List<String> direccionesFactura;
    private List<LlamadaServicioDTO> servicios;
    private List<ServiceCallStatusDTO> estados;
    private List<ServiceCallOriginsDTO> origenes;
    private List<ServiceCallProblemTypesDTO> problemas;
    private List<ServiceCallProblemSubtypeDTO> subProblemas;
    private List<ServiceCallTypesDTO> tiposLlamada;
    private List<UsuarioDTO> usuarios;
    private List<ActividadDTO> operaciones;
    private List<TipoActividadDTO> tiposActividad;
    private List<TemaActividadDTO> temasActividad;
    private List<LugarReunionDTO> localidades;
    //private List<ServiceCallSolutionsDTO> soluciones;
    private List<ServiceCallSolutionStatusesDTO> estadosSolucion;
    private List<ServiceCallHistoryDTO> historico;
    private List<PaisDTO> paises;
    private List<DepartamentoDTO> departamentos;
    private List<ValoresUsuarioDTO> causasGarantia;
    private List<ValoresUsuarioDTO> categoriasReparacion;
    private List<ValoresUsuarioDTO> tiposServicio;
    private List<ValoresUsuarioDTO> rmas;
    private List<ValoresUsuarioDTO> rmasTipos;
    private List<BaruMaterialesDTO> materiales;
    private List<BaruAveriasMaterialDTO> averias;
    private List<LlamadaSolucionDTO> soluciones;
    private List<AdjuntoLlamadaServicioDTO> adjuntos;
    private List<Object[]> asignatarios;
    private List<Object[]> claseDocumentos;
    @EJB
    private ServiceCallStatusFacade serviceCallStatusFacade;
    @EJB
    private ServiceCallOriginsFacade serviceCallOriginsFacade;
    @EJB
    private ServiceCallProblemTypesFacade serviceCallProblemTypesFacade;
    @EJB
    private ServiceCallProblemSubtypeFacade serviceCallProblemSubtypeFacade;
    @EJB
    private ServiceCallTypesFacade serviceCallTypesFacade;
    @EJB
    private LlamadaServicioFacade llamadaServicioFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private DireccionSocioDeNegociosFacade direccionSocioDeNegociosFacade;
    @EJB
    private ActividadFacade actividadFacade;
    @EJB
    private TipoActividadFacade tipoActividadFacade;
    @EJB
    private TemaActividadFacade temaActividadFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private LugarReunionFacade lugarReunionFacade;
//    @EJB
//    private ServiceCallSolutionsFacade serviceCallSolutionsFacade;
    @EJB
    private ServiceCallSolutionStatusesFacade serviceCallSolutionStatusesFacade;
    @EJB
    private ServiceCallHistoryFacade serviceCallHistoryFacade;
    @EJB
    private PaisFacade paisFacade;
    @EJB
    private DepartamentoSAPFacade departamentoSAPFacade;
    @EJB
    private ValoresUsuarioFacade valoresUsuarioFacade;
    @EJB
    private BaruMaterialesFacade baruMaterialesFacade;
    @EJB
    private BaruAveriasMaterialFacade baruAveriasMaterialFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private FacturaSAPFacade facturaSAPFacade;
    @EJB
    private LlamadaSolucionFacade llamadaSolucionFacade;

    public LlamadaServicioMBean() {
        solucion = new LlamadaSolucionDTO();
        servicios = new ArrayList<>();
        serviceCall = new ServiceCall();
        soluciones = new ArrayList<>();
        adjuntos = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerEstados();
        obtenerOrigenes();
        obtenerProblemas();
        obtenerSubProblemas();
        obtenerTiposLlamada();
        obtenerUsuarios();
        obtenerTiposActividad();
        obtenerTemasActividad();
        obtenerAsignatarios();
        obtenerLocalidades();
        obtenerClasesDocumento();
        obtenerEstadosSolucion();
        obtenerPaises();
        obtenerDepartamentos();
        obtenerValoresUsuario(0);
        obtenerValoresUsuario(1);
        obtenerValoresUsuario(5);
        obtenerValoresUsuario(6);
        obtenerValoresUsuario(8);
    }

    public String getTipoAsignar() {
        return tipoAsignar;
    }

    public void setTipoAsignar(String tipoAsignar) {
        this.tipoAsignar = tipoAsignar;
    }

    public String getMsgGarantia() {
        return msgGarantia;
    }

    public void setMsgGarantia(String msgGarantia) {
        this.msgGarantia = msgGarantia;
    }

    public boolean isDlgGarantia() {
        return dlgGarantia;
    }

    public void setDlgGarantia(boolean dlgGarantia) {
        this.dlgGarantia = dlgGarantia;
    }

    public boolean isDlgLlamadas() {
        return dlgLlamadas;
    }

    public void setDlgLlamadas(boolean dlgLlamadas) {
        this.dlgLlamadas = dlgLlamadas;
    }

    public LlamadaSolucionDTO getSolucion() {
        return solucion;
    }

    public void setSolucion(LlamadaSolucionDTO solucion) {
        this.solucion = solucion;
    }

    public ServiceCall getServiceCall() {
        return serviceCall;
    }

    public void setServiceCall(ServiceCall serviceCall) {
        this.serviceCall = serviceCall;
    }

    public Part getAdjunto() {
        return adjunto;
    }

    public void setAdjunto(Part adjunto) {
        this.adjunto = adjunto;
    }

    public List<String> getDireccionesEntrega() {
        return direccionesEntrega;
    }

    public void setDireccionesEntrega(List<String> direccionesEntrega) {
        this.direccionesEntrega = direccionesEntrega;
    }

    public List<String> getDireccionesFactura() {
        return direccionesFactura;
    }

    public void setDireccionesFactura(List<String> direccionesFactura) {
        this.direccionesFactura = direccionesFactura;
    }

    public List<LlamadaServicioDTO> getServicios() {
        return servicios;
    }

    public void setServicios(List<LlamadaServicioDTO> servicios) {
        this.servicios = servicios;
    }

    public List<ServiceCallStatusDTO> getEstados() {
        return estados;
    }

    public void setEstados(List<ServiceCallStatusDTO> estados) {
        this.estados = estados;
    }

    public List<ServiceCallOriginsDTO> getOrigenes() {
        return origenes;
    }

    public void setOrigenes(List<ServiceCallOriginsDTO> origenes) {
        this.origenes = origenes;
    }

    public List<ServiceCallProblemTypesDTO> getProblemas() {
        return problemas;
    }

    public void setProblemas(List<ServiceCallProblemTypesDTO> problemas) {
        this.problemas = problemas;
    }

    public List<ServiceCallProblemSubtypeDTO> getSubProblemas() {
        return subProblemas;
    }

    public void setSubProblemas(List<ServiceCallProblemSubtypeDTO> subProblemas) {
        this.subProblemas = subProblemas;
    }

    public List<ServiceCallTypesDTO> getTiposLlamada() {
        return tiposLlamada;
    }

    public void setTiposLlamada(List<ServiceCallTypesDTO> tiposLlamada) {
        this.tiposLlamada = tiposLlamada;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<ActividadDTO> getOperaciones() {
        return operaciones;
    }

    public void setOperaciones(List<ActividadDTO> operaciones) {
        this.operaciones = operaciones;
    }

    public List<TipoActividadDTO> getTiposActividad() {
        return tiposActividad;
    }

    public void setTiposActividad(List<TipoActividadDTO> tiposActividad) {
        this.tiposActividad = tiposActividad;
    }

    public List<TemaActividadDTO> getTemasActividad() {
        return temasActividad;
    }

    public void setTemasActividad(List<TemaActividadDTO> temasActividad) {
        this.temasActividad = temasActividad;
    }

    public List<Object[]> getAsignatarios() {
        return asignatarios;
    }

    public void setAsignatarios(List<Object[]> asignatarios) {
        this.asignatarios = asignatarios;
    }

    public List<LugarReunionDTO> getLocalidades() {
        return localidades;
    }

    public void setLocalidades(List<LugarReunionDTO> localidades) {
        this.localidades = localidades;
    }

//    public List<ServiceCallSolutionsDTO> getSoluciones() {
//        return soluciones;
//    }
//
//    public void setSoluciones(List<ServiceCallSolutionsDTO> soluciones) {
//        this.soluciones = soluciones;
//    }
    public List<ServiceCallSolutionStatusesDTO> getEstadosSolucion() {
        return estadosSolucion;
    }

    public void setEstadosSolucion(List<ServiceCallSolutionStatusesDTO> estadosSolucion) {
        this.estadosSolucion = estadosSolucion;
    }

    public List<ServiceCallHistoryDTO> getHistorico() {
        return historico;
    }

    public void setHistorico(List<ServiceCallHistoryDTO> historico) {
        this.historico = historico;
    }

    public List<PaisDTO> getPaises() {
        return paises;
    }

    public void setPaises(List<PaisDTO> paises) {
        this.paises = paises;
    }

    public List<DepartamentoDTO> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<DepartamentoDTO> departamentos) {
        this.departamentos = departamentos;
    }

    public List<ValoresUsuarioDTO> getCausasGarantia() {
        return causasGarantia;
    }

    public void setCausasGarantia(List<ValoresUsuarioDTO> causasGarantia) {
        this.causasGarantia = causasGarantia;
    }

    public List<ValoresUsuarioDTO> getCategoriasReparacion() {
        return categoriasReparacion;
    }

    public void setCategoriasReparacion(List<ValoresUsuarioDTO> categoriasReparacion) {
        this.categoriasReparacion = categoriasReparacion;
    }

    public List<ValoresUsuarioDTO> getTiposServicio() {
        return tiposServicio;
    }

    public void setTiposServicio(List<ValoresUsuarioDTO> tiposServicio) {
        this.tiposServicio = tiposServicio;
    }

    public List<ValoresUsuarioDTO> getRmas() {
        return rmas;
    }

    public void setRmas(List<ValoresUsuarioDTO> rmas) {
        this.rmas = rmas;
    }

    public List<ValoresUsuarioDTO> getRmasTipos() {
        return rmasTipos;
    }

    public void setRmasTipos(List<ValoresUsuarioDTO> rmasTipos) {
        this.rmasTipos = rmasTipos;
    }

    public List<BaruMaterialesDTO> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<BaruMaterialesDTO> materiales) {
        this.materiales = materiales;
    }

    public List<BaruAveriasMaterialDTO> getAverias() {
        return averias;
    }

    public void setAverias(List<BaruAveriasMaterialDTO> averias) {
        this.averias = averias;
    }

    public List<LlamadaSolucionDTO> getSoluciones() {
        return soluciones;
    }

    public void setSoluciones(List<LlamadaSolucionDTO> soluciones) {
        this.soluciones = soluciones;
    }

    public List<AdjuntoLlamadaServicioDTO> getAdjuntos() {
        return adjuntos;
    }

    public void setAdjuntos(List<AdjuntoLlamadaServicioDTO> adjuntos) {
        this.adjuntos = adjuntos;
    }

    public List<Object[]> getClaseDocumentos() {
        return claseDocumentos;
    }

    public void setClaseDocumentos(List<Object[]> claseDocumentos) {
        this.claseDocumentos = claseDocumentos;
    }

    public String getObtenerNombre(String usuario) {
        if (usuario != null && !usuario.isEmpty()) {
            for (UsuarioDTO u : usuarios) {
                if (u.getUserID().toString().equals(usuario)) {
                    return u.getName();
                }
            }
        }

        return usuario;
    }

    public String getObtenerRecurrencePatter(String recurrencePatter) {
        if (recurrencePatter != null && !recurrencePatter.isEmpty()) {
            switch (recurrencePatter) {
                case "N":
                    return "Ninguno";
                case "D":
                    return "Diario";
                case "W":
                    return "Semanal";
                case "M":
                    return "Mensual";
                case "A":
                    return "Anual";
                default:
                    break;
            }
        }

        return "";
    }

    public String getObtenerEstadoSolucion(Long status) {
        if (status != null && status != 0) {
            for (ServiceCallSolutionStatusesDTO s : estadosSolucion) {
                if (s.getNumber().equals(status)) {
                    return s.getName();
                }
            }
        }

        return "";
    }

    public String getObtenerEstadoLlamada(Integer estado) {
        if (estado != null && estado != 0) {
            for (ServiceCallStatusDTO c : estados) {
                if (c.getStatusId().equals(estado)) {
                    return c.getName();
                }
            }
        }

        return "";
    }

    private void obtenerEstados() {
        estados = new ArrayList<>();

        List<ServiceCallStatus> status = serviceCallStatusFacade.findAll();

        if (status != null && !status.isEmpty()) {
            for (ServiceCallStatus s : status) {
                estados.add(new ServiceCallStatusDTO(s.getStatusId(), s.getName(), s.getDescription(), s.getLocked()));
            }
        }
    }

    private void obtenerOrigenes() {
        origenes = new ArrayList<>();

        List<ServiceCallOrigins> origins = serviceCallOriginsFacade.findAll();

        if (origins != null && !origins.isEmpty()) {
            for (ServiceCallOrigins s : origins) {
                origenes.add(new ServiceCallOriginsDTO(s.getOriginId(), s.getName().substring(0, 1).toUpperCase() + s.getName().substring(1), s.getDescription(), s.getLocked()));
            }

            Collections.sort(origenes, new Comparator<ServiceCallOriginsDTO>() {
                @Override
                public int compare(ServiceCallOriginsDTO o1, ServiceCallOriginsDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void obtenerProblemas() {
        problemas = new ArrayList<>();

        List<ServiceCallProblemTypes> problems = serviceCallProblemTypesFacade.findAll();

        if (problems != null && !problems.isEmpty()) {
            for (ServiceCallProblemTypes s : problems) {
                problemas.add(new ServiceCallProblemTypesDTO(s.getProblemTypeId(), s.getName(), s.getDescription()));
            }
        }
    }

    private void obtenerSubProblemas() {
        subProblemas = new ArrayList<>();

        List<ServiceCallProblemSubtype> subProblems = serviceCallProblemSubtypeFacade.findAll();

        if (subProblems != null && !subProblems.isEmpty()) {
            for (ServiceCallProblemSubtype s : subProblems) {
                subProblemas.add(new ServiceCallProblemSubtypeDTO(s.getProSubTyId(), s.getName(), s.getDescription()));
            }
        }
    }

    private void obtenerTiposLlamada() {
        tiposLlamada = new ArrayList<>();

        List<ServiceCallTypes> types = serviceCallTypesFacade.findAll();

        if (types != null && !types.isEmpty()) {
            for (ServiceCallTypes s : types) {
                tiposLlamada.add(new ServiceCallTypesDTO(s.getCallTypeId(), s.getName().substring(0, 1).toUpperCase() + s.getName().substring(1), s.getDescription()));
            }
        }
    }

    private void obtenerUsuarios() {
        usuarios = new ArrayList<>();

        List<Usuario> users = usuarioFacade.findAll();

        if (users != null && !users.isEmpty()) {
            for (Usuario u : users) {
                if (u.getName() != null) {
                    usuarios.add(new UsuarioDTO(u.getUserID(), u.getUserCode(), u.getName(), u.getLocked()));
                }
            }

            Collections.sort(usuarios, new Comparator<UsuarioDTO>() {
                @Override
                public int compare(UsuarioDTO o1, UsuarioDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void obtenerTiposActividad() {
        tiposActividad = new ArrayList<>();

        List<TipoActividad> activities = tipoActividadFacade.obtenerTiposActividad();

        if (activities != null && !activities.isEmpty()) {
            for (TipoActividad t : activities) {
                tiposActividad.add(new TipoActividadDTO(t.getCode(), t.getUserSign(), t.getName().substring(0, 1).toUpperCase() + t.getName().substring(1), t.getDataSource(), t.getActive()));
            }
        }
    }

    private void obtenerTemasActividad() {
        temasActividad = new ArrayList<>();

        List<TemaActividad> topics = temaActividadFacade.obtenerTemasActividad();

        if (topics != null && !topics.isEmpty()) {
            for (TemaActividad t : topics) {
                temasActividad.add(new TemaActividadDTO(t.getType(), t.getCode(), t.getUserSign(), t.getName().substring(0, 1).toUpperCase() + t.getName().substring(1), t.getDataSource(), t.getActive()));
            }

            Collections.sort(temasActividad, new Comparator<TemaActividadDTO>() {
                @Override
                public int compare(TemaActividadDTO o1, TemaActividadDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    public void obtenerAsignatarios() {
        asignatarios = new ArrayList<>();

        if (tipoAsignar.equals("U")) {
            for (UsuarioDTO u : usuarios) {
                asignatarios.add(new Object[]{u.getUserID(), u.getName()});
            }
        } else if (tipoAsignar.equals("E")) {
            List<Empleado> employees = empleadoFacade.findAll();

            if (employees != null && !employees.isEmpty()) {
                for (Empleado e : employees) {
                    asignatarios.add(new Object[]{e.getEmpID(), e.getLastName() + ", " + e.getFirstName()});
                }

                Collections.sort(asignatarios, new Comparator<Object[]>() {
                    @Override
                    public int compare(Object[] o1, Object[] o2) {
                        return o1[1].toString().compareTo(o2[1].toString());
                    }
                });
            }
        }
    }

    private void obtenerLocalidades() {
        localidades = new ArrayList<>();

        List<LugarReunion> locations = lugarReunionFacade.obtenerTiposActividad();

        if (locations != null && !locations.isEmpty()) {
            for (LugarReunion l : locations) {
                localidades.add(new LugarReunionDTO(l.getCode(), l.getUserSign(), l.getName().toUpperCase(), l.getDataSource(), l.getLocked()));
            }

            Collections.sort(localidades, new Comparator<LugarReunionDTO>() {
                @Override
                public int compare(LugarReunionDTO o1, LugarReunionDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void obtenerClasesDocumento() {
        claseDocumentos = new ArrayList<>();

        claseDocumentos.add(new Object[]{0, "Ofertas de ventas"});
        claseDocumentos.add(new Object[]{0, "Pedidos de cliente"});
        claseDocumentos.add(new Object[]{0, "Entregas"});
        claseDocumentos.add(new Object[]{0, "Devoluciones"});
        claseDocumentos.add(new Object[]{0, "Facuras clientes"});
        claseDocumentos.add(new Object[]{0, "Notas de crédito clientes"});
        claseDocumentos.add(new Object[]{0, "Anticipo de clientes"});
        claseDocumentos.add(new Object[]{0, "-----------------------------------------------"});
        claseDocumentos.add(new Object[]{0, "Solicitud de compra"});
        claseDocumentos.add(new Object[]{0, "Oferta de compra"});
        claseDocumentos.add(new Object[]{0, "Pedidos"});
        claseDocumentos.add(new Object[]{0, "Pedidos de entrada de mercancías"});
        claseDocumentos.add(new Object[]{0, "Devolución de mercancías"});
        claseDocumentos.add(new Object[]{0, "Fact. proveedores"});
        claseDocumentos.add(new Object[]{0, "Notas de crédito acreedores"});
        claseDocumentos.add(new Object[]{0, "Precio de entrega"});
        claseDocumentos.add(new Object[]{0, "Anticipo de proveedores"});
        claseDocumentos.add(new Object[]{0, "-----------------------------------------------"});
        claseDocumentos.add(new Object[]{0, "Pagos recibidos"});
        claseDocumentos.add(new Object[]{0, "Pagos efectuados"});
        claseDocumentos.add(new Object[]{0, "Depósitos"});
        claseDocumentos.add(new Object[]{0, "Cheques para el pago"});
        claseDocumentos.add(new Object[]{0, "Registros en el diario"});
        claseDocumentos.add(new Object[]{0, "-----------------------------------------------"});
        claseDocumentos.add(new Object[]{0, "Solicitud de traslado"});
        claseDocumentos.add(new Object[]{0, "Traslados"});
        claseDocumentos.add(new Object[]{0, "Salida de mercancías"});
        claseDocumentos.add(new Object[]{0, "Entrada de mercancías"});
        claseDocumentos.add(new Object[]{0, "Revalorización de inventario"});
        claseDocumentos.add(new Object[]{0, "Órdenes fabricación"});
        claseDocumentos.add(new Object[]{0, "Artículos"});
        claseDocumentos.add(new Object[]{0, "Gestión de campañas"});
        claseDocumentos.add(new Object[]{0, "-----------------------------------------------"});
        claseDocumentos.add(new Object[]{0, "Acuerdos globales"});
    }

    private void obtenerEstadosSolucion() {
        estadosSolucion = new ArrayList<>();

        List<ServiceCallSolutionStatuses> statuses = serviceCallSolutionStatusesFacade.findAll();

        if (statuses != null && !statuses.isEmpty()) {
            for (ServiceCallSolutionStatuses s : statuses) {
                estadosSolucion.add(new ServiceCallSolutionStatusesDTO(s.getNumber(), s.getName(), s.getDescription()));
            }
        }
    }

    private void obtenerPaises() {
        paises = new ArrayList<>();

        List<Pais> countries = paisFacade.findAll();

        if (countries != null && !countries.isEmpty()) {
            for (Pais p : countries) {
                paises.add(new PaisDTO(p.getCode(), p.getName()));
            }
        }
    }

    private void obtenerDepartamentos() {
        departamentos = new ArrayList<>();

        if (serviceCall != null && serviceCall.getCountry() != null && !serviceCall.getCountry().isEmpty() && serviceCall.getCountry().equals("CO")) {
            List<DepartamentoSAP> states = departamentoSAPFacade.obtenerDepartamentosColombia();

            if (states != null && !states.isEmpty()) {
                for (DepartamentoSAP d : states) {
                    departamentos.add(new DepartamentoDTO(d.getDepartamentoPK().getCode(), d.getName()));
                }
            }
        }
    }

    private void obtenerValoresUsuario(Integer fieldID) {
        List<ValoresUsuario> values = valoresUsuarioFacade.listarValoresUsuario(fieldID, "OSCL");

        if (values != null && !values.isEmpty()) {
            if (null != fieldID) {
                switch (fieldID) {
                    case 0:
                        causasGarantia = new ArrayList<>();
                        break;
                    case 1:
                        categoriasReparacion = new ArrayList<>();
                        break;
                    case 5:
                        tiposServicio = new ArrayList<>();
                        break;
                    case 6:
                        rmas = new ArrayList<>();
                        break;
                    case 8:
                        rmasTipos = new ArrayList<>();
                        break;
                    default:
                        break;
                }
            }

            for (ValoresUsuario v : values) {
                if (null != fieldID) {
                    switch (fieldID) {
                        case 0:
                            causasGarantia.add(new ValoresUsuarioDTO(v.getFieldID(), v.getIndexID(), v.getTableID(), v.getFldValue(), v.getDescr()));
                            break;
                        case 1:
                            categoriasReparacion.add(new ValoresUsuarioDTO(v.getFieldID(), v.getIndexID(), v.getTableID(), v.getFldValue(), v.getDescr()));
                            break;
                        case 5:
                            tiposServicio.add(new ValoresUsuarioDTO(v.getFieldID(), v.getIndexID(), v.getTableID(), v.getFldValue(), v.getDescr()));
                            break;
                        case 6:
                            rmas.add(new ValoresUsuarioDTO(v.getFieldID(), v.getIndexID(), v.getTableID(), v.getFldValue(), v.getDescr()));
                            break;
                        case 8:
                            rmasTipos.add(new ValoresUsuarioDTO(v.getFieldID(), v.getIndexID(), v.getTableID(), v.getFldValue(), v.getDescr()));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }

    public void consultarLlamadasAbiertas() {
        dlgLlamadas = false;
        servicios = new ArrayList<>();
        List<LlamadaServicio> llamadas = llamadaServicioFacade.obtenerLlamadaAbiertas();

        if (llamadas != null && !llamadas.isEmpty()) {
            dlgLlamadas = true;
            for (LlamadaServicio l : llamadas) {
                servicios.add(new LlamadaServicioDTO(l));
            }
            return;
        }
    }

    public void consultarLlamada() {
        dlgLlamadas = false;
        servicios = new ArrayList<>();
        List<LlamadaServicio> llamadas = new ArrayList<>();
        LlamadaServicio llamada = new LlamadaServicio();

        if (serviceCall.getDocNum() != null && serviceCall.getDocNum() > 0) {
            llamada = llamadaServicioFacade.obtenerLlamadaServicio(serviceCall.getDocNum().intValue());
        } else if (serviceCall.getCustomerCode() != null && !serviceCall.getCustomerCode().isEmpty()) {
            llamadas = llamadaServicioFacade.obtenerLlamadaServicioParametro("customer", serviceCall.getCustomerCode().replace("CL", ""));
        } else if (serviceCall.getItemCode() != null && !serviceCall.getItemCode().isEmpty()) {
            llamadas = llamadaServicioFacade.obtenerLlamadaServicioParametro("itemCode", serviceCall.getItemCode());
        } else if (serviceCall.getDescription() != null && !serviceCall.getDescription().isEmpty()) {
            llamadas = llamadaServicioFacade.obtenerLlamadaServicioParametro("descrption", serviceCall.getDescription());
        }

        if (llamadas != null && !llamadas.isEmpty() && llamadas.size() == 1) {
            llamada = llamadas.get(0);
        } else if (llamadas != null && !llamadas.isEmpty()) {
            dlgLlamadas = true;
            for (LlamadaServicio l : llamadas) {
                servicios.add(new LlamadaServicioDTO(l));
            }
            return;
        }

        if (llamada != null && llamada.getCallID() != null && llamada.getCallID() != 0) {
            consultarLlamada(llamada);
        }
    }

    public void seleccionarLlamada() {
        LlamadaServicio llamada = new LlamadaServicio();

        llamada.setCallID(Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id")));

        dlgLlamadas = false;
        servicios = new ArrayList<>();
        consultarLlamada(llamada);
    }

    private void consultarLlamada(LlamadaServicio llamada) {
        if (llamada != null && llamada.getCallID() != null && llamada.getCallID() != 0) {
            ServiceCallsClient client = new ServiceCallsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            serviceCall = client.buscarLlamadaServicio(llamada.getCallID());

            serviceCall.setFechaCreacion(serviceCall.getCreationDate().toGregorianCalendar().getTime());
            serviceCall.setHoraCreacion(new SimpleDateFormat("HH:mm").format(serviceCall.getCreationTime().toGregorianCalendar().getTime()));
            if (serviceCall.getClosingDate() != null) {
                serviceCall.setFechaCierre(serviceCall.getClosingDate().toGregorianCalendar().getTime());
                if (serviceCall.getClosingTime() != null) {
                    serviceCall.setHoraCierre(new SimpleDateFormat("HH:mm").format(serviceCall.getClosingTime().toGregorianCalendar().getTime()));
                }
            }
            if (serviceCall.getContractEndDate() != null) {
                serviceCall.setFechaFinalizado(serviceCall.getContractEndDate().toGregorianCalendar().getTime());
            }
            if (serviceCall.getStartDate() != null) {
                serviceCall.setFechaInicio(serviceCall.getStartDate().toGregorianCalendar().getTime());
                if (serviceCall.getStartTime() != null) {
                    serviceCall.setHoraInicio(new SimpleDateFormat("HH:mm").format(serviceCall.getStartTime().toGregorianCalendar().getTime()));
                }
            }
            if (serviceCall.getEndDueDate() != null) {
                serviceCall.setFechaFin(serviceCall.getEndDueDate().toGregorianCalendar().getTime());
                if (serviceCall.getEndTime() != null) {
                    serviceCall.setHoraFin(new SimpleDateFormat("HH:mm").format(serviceCall.getEndTime().toGregorianCalendar().getTime()));
                }
            }
            if (serviceCall.getDuration() != null) {
                serviceCall.setDuracion(serviceCall.getDuration() + " " + obtenerType(serviceCall.getDurationType()));
            }
            if (serviceCall.getReminderPeriod() != null) {
                serviceCall.setRecordatorio(serviceCall.getReminderPeriod() + " " + obtenerType(serviceCall.getReminderType()));
            }
            if (serviceCall.getResponseOnDate() != null) {
                serviceCall.setFechaRespuestaOn(serviceCall.getResponseOnDate().toGregorianCalendar().getTime());
                if (serviceCall.getResponseOnTime() != null) {
                    serviceCall.setHoraRespuestaOn(new SimpleDateFormat("HH:mm").format(serviceCall.getResponseOnTime().toGregorianCalendar().getTime()));
                }
            }
            if (serviceCall.getResolutionOnDate() != null) {
                serviceCall.setFechaResolucionOn(serviceCall.getResolutionOnDate().toGregorianCalendar().getTime());
                if (serviceCall.getResolutionOnTime() != null) {
                    serviceCall.setHoraResolucionOn(new SimpleDateFormat("HH:mm").format(serviceCall.getResolutionOnTime().toGregorianCalendar().getTime()));
                }
            }
            obtenerMateriales();
            obtenerAverias();
            obtenerDirecciones();
            obtenerOperaciones();
            obtenerSoluciones();
            obtenerHistorico();
            obtenerDepartamentos();
            obtenerAdjuntos();
        }
    }

    private void obtenerAdjuntos() {
        adjuntos = new ArrayList<>();

        File file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "llamadasServicio" + File.separator + serviceCall.getServiceCallID());

        if (file.exists()) {
            File[] attachments = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    return !pathname.isHidden();
                }
            });

            if (attachments != null && attachments.length > 0) {
                try {
                    long i = 1;
                    for (File f : attachments) {
                        String[] s = f.getName().split("&");
                        if (s != null && s.length == 2) {
                            adjuntos.add(new AdjuntoLlamadaServicioDTO(i, s[1], f.getName(), true, false, new SimpleDateFormat("yyyy-MM-dd").parse(s[0]), null));
                            i++;
                        }
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    public void agregarAdjunto() {
        if (adjunto == null) {
            return;
        }

        CONSOLE.log(Level.INFO, "Recibiendo archivo {0}", adjunto.getSubmittedFileName());
        try (InputStream input = adjunto.getInputStream()) {
            File file = new File(System.getProperty("jboss.server.temp.dir"), adjunto.getSubmittedFileName());
            Files.copy(input, file.toPath());
            mostrarMensaje("", "¿Esta segur@ que desea guardar el documento de nombre " + adjunto.getSubmittedFileName() + "?.", false, false, true);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }

    public void guardarAdjunto() {
        try {
            File file = new File(System.getProperty("jboss.server.temp.dir"), adjunto.getSubmittedFileName());

            AdjuntoLlamadaServicioDTO dto = new AdjuntoLlamadaServicioDTO();

            dto.setId(System.currentTimeMillis());
            dto.setAdjunto(adjunto);
            dto.setFecha(new Date());
            dto.setNombre(adjunto.getSubmittedFileName());
            dto.setArchivo(Files.readAllBytes(file.toPath()));

            adjuntos.add(dto);
            adjunto = null;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al guardar el archivo. ", e);
        }
    }

    public void eliminarAdjunto() {
        Long id = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));

        for (AdjuntoLlamadaServicioDTO a : adjuntos) {
            if (a.getId().equals(id)) {
                a.setEliminar(true);
                break;
            }
        }
    }

    public String descargarDocumento(Long id) {
        if (id != null && id != 0) {
            for (AdjuntoLlamadaServicioDTO a : adjuntos) {
                if (a.getId().equals(id)) {
                    try {
                        return "openRuta('" + applicationMBean.obtenerValorPropiedad("url.web.shared") + "llamadasServicio/" + serviceCall.getServiceCallID() + "/" + a.getNombreOriginal() + "'); closeWindow();";
                    } catch (Exception e) {
                        return "";
                    }
                }
            }
        }
        return "";
    }

    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void guardarLlamada() {
        dlgGarantia = false;
        boolean inicial = Boolean.valueOf(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("inicial"));
        ServiceCallsClient client = new ServiceCallsClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        if (serviceCall.getOrigin() == null || serviceCall.getOrigin() == 0) {
            mostrarMensaje("Error", "Es obligatorio llenar el campo origen.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Es obligatorio llenar el campo origen");
            return;
        }
        if (serviceCall.getProblemType() == null || serviceCall.getProblemType() == 0) {
            mostrarMensaje("Error", "Es obligatorio llenar el campo tipo problema.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Es obligatorio llenar el campo tipo problema");
            return;
        }

        if (serviceCall != null && serviceCall.getServiceCallID() != null && serviceCall.getServiceCallID() != 0) {
            client.editarLlamadaServicio(serviceCall);
        } else {
            /*Validar si el registro se encuentra en garantia, esto aplica solo si la llamada tiene referencia y factura*/
            if (serviceCall.getUNumFactura() != null && !serviceCall.getUNumFactura().isEmpty() && serviceCall.getItemCode() != null && !serviceCall.getItemCode().isEmpty() && inicial) {
                List<Object[]> garantia = facturaSAPFacade.obtenerDetallePedido(serviceCall.getUNumFactura(), serviceCall.getItemCode());

                if (garantia != null && !garantia.isEmpty()) {

                } else {
                    dlgGarantia = true;
                    msgGarantia = "<p>No se encontraron datos correspondientes a la referencia y factura ingresada.</p><br/><p>¿Desea continuar de todos modos?</p>";
                    return;
                }
            }

            GenericRESTResponseDTO res = client.crearLlamadaServicio(serviceCall);

            if (res.getEstado() == 0) {
                serviceCall.setServiceCallID(((Integer) res.getContent()).longValue());
            }
        }

        if (soluciones != null && !soluciones.isEmpty()) {
            try {
                for (LlamadaSolucionDTO l : soluciones) {
                    LlamadaSolucion call = llamadaSolucionFacade.find(l.getIdLlamadaSolucion());

                    if (call == null) {
                        call = new LlamadaSolucion();
                    }

                    call.setCausa(l.getCausa());
                    call.setComentarios(l.getComentarios());
                    call.setDocEntry(serviceCall.getServiceCallID().intValue());
                    call.setEstado(l.getEstado());
                    call.setSintoma(l.getSintoma());
                    call.setSolucion(l.getSolucion());
                    call.setItemCode(l.getItemCode());
                    call.setFecha(l.getFecha());
                    call.setPropietario(l.getPropietario());

                    if (call != null && call.getIdLlamadaSolucion() != null && call.getIdLlamadaSolucion() != 0) {
                        llamadaSolucionFacade.edit(call);
                    } else {
                        call.setIdLlamadaSolucion(l.getIdLlamadaSolucion());

                        llamadaSolucionFacade.create(call);
                    }
                }
            } catch (Exception e) {
            }
        }

        if (adjuntos != null && !adjuntos.isEmpty()) {
            /*Se guardaran los adjuntos
            1. Validar si la carpeta existe
            2. Recorrer y guardar los adjuntos
             */
            File file = new File(applicationMBean.obtenerValorPropiedad("url.local.shared") + "llamadasServicio" + File.separator + serviceCall.getServiceCallID());

            if (!file.exists()) {
                file.mkdir();
            }

            for (AdjuntoLlamadaServicioDTO a : adjuntos) {
                if (!a.isExiste() && !a.isEliminar()) {
                    try {
                        InputStream is = new ByteArrayInputStream(a.getArchivo());

                        File file2 = new File(file.getPath(), new SimpleDateFormat("yyyy-MM-dd").format(a.getFecha()) + "&" + a.getNombre().replace("&", "-"));
                        Files.copy(is, file2.toPath());

                        /*Se elimina el archivo de temporales*/
                        new File(System.getProperty("jboss.server.temp.dir"), a.getNombre()).delete();
                    } catch (Exception e) {
                    }
                } else if (a.isEliminar() && a.isExiste()) {
                    if (new File(file.getPath(), new SimpleDateFormat("yyyy-MM-dd").format(a.getFecha()) + "&" + a.getNombre()).delete()) {
                        CONSOLE.log(Level.INFO, "Se elimino el archivo {0}", a.getNombre());
                    }
                }
            }
        }
    }

    public void seleccionarClaseLlamada() {
        if (serviceCall == null || serviceCall.getServiceCallID() == null || serviceCall.getServiceCallID() == 0) {
            String tipo = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("claseLlamada");

            serviceCall.setServiceBPType(tipo);
        }
    }

    public void cambiarCliente() {
        if (!serviceCall.getCustomerCode().contains("CL")) {
            serviceCall.setCustomerCode(serviceCall.getCustomerCode() + "CL");
        }

        SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(serviceCall.getCustomerCode());

        if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
            serviceCall.setCustomerName(socio.getRazonSocial());
            obtenerDirecciones();
            serviceCall.setBPBillToCode(null);
            serviceCall.setBPShipToCode(null);
            serviceCall.setBPBillToAddress(null);
            serviceCall.setBPShipToAddress(null);
        }
    }

    public void cambiarReferencia() {
        serviceCall.setItemCode(genericMBean.completarReferencia(serviceCall.getItemCode()));

        if (serviceCall.getItemCode() != null && serviceCall.getItemCode().length() == 20) {
            ItemInventario item = itemInventarioFacade.find(serviceCall.getItemCode());

            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                serviceCall.setItemDescription(item.getItemName());
                serviceCall.setItemGroupCode(item.getItmsGrpCod().longValue());
                obtenerMateriales();
            } else {
                serviceCall.setItemCode(null);
                serviceCall.setItemDescription(null);
                serviceCall.setItemGroupCode(null);
            }
        }
    }

    public void obtenerMateriales() {
        materiales = new ArrayList<>();

        if (serviceCall != null && serviceCall.getItemCode() != null && !serviceCall.getItemCode().isEmpty()) {
            List<BaruMateriales> materials = baruMaterialesFacade.obtenerMaterialesArticulo(serviceCall.getItemCode());

            if (materials != null && !materials.isEmpty()) {
                for (BaruMateriales b : materials) {
                    materiales.add(new BaruMaterialesDTO(b.getCode(), b.getName(), b.getuCuidados()));
                }
            }
        }
    }

    public void obtenerAverias() {
        averias = new ArrayList<>();

        List<BaruAveriasMaterial> faults = baruAveriasMaterialFacade.obtenerAveriasMaterial(serviceCall.getUMaterial());

        if (faults != null && !faults.isEmpty()) {
            for (BaruAveriasMaterial b : faults) {
                averias.add(new BaruAveriasMaterialDTO(b.getCode(), b.getName(), b.getUMaterial(), b.getUProblema()));
            }
        }
    }

    private void obtenerDirecciones() {
        direccionesEntrega = direccionSocioDeNegociosFacade.obtenerDirecciones(serviceCall.getCustomerCode(), "S");
        direccionesFactura = direccionSocioDeNegociosFacade.obtenerDirecciones(serviceCall.getCustomerCode(), "B");
    }

    public void seleccionarDireccion(String tipo) {
        if (tipo.equals("S")) {
            DireccionSocioDeNegocios dir = direccionSocioDeNegociosFacade.obtenerDireccion(serviceCall.getCustomerCode(), serviceCall.getBPShipToCode(), tipo);

            if (dir != null && dir.getDireccionSocioDeNegociosPK().getAddress() != null && !dir.getDireccionSocioDeNegociosPK().getAddress().isEmpty()) {
                serviceCall.setBPShipToAddress(dir.getStreet() + "\n  " + dir.getCity() + "\n" + obtenerNombrePais(dir.getCountry()));
            } else {
                serviceCall.setBPShipToAddress(null);
            }
        } else {
            DireccionSocioDeNegocios dir = direccionSocioDeNegociosFacade.obtenerDireccion(serviceCall.getCustomerCode(), serviceCall.getBPBillToCode(), tipo);

            if (dir != null && dir.getDireccionSocioDeNegociosPK().getAddress() != null && !dir.getDireccionSocioDeNegociosPK().getAddress().isEmpty()) {
                serviceCall.setBPBillToAddress(dir.getStreet() + "\n  " + dir.getCity() + "\n" + obtenerNombrePais(dir.getCountry()));
            } else {
                serviceCall.setBPBillToAddress(null);
            }
        }
    }

    private String obtenerNombrePais(String code) {
        for (PaisDTO p : paises) {
            if (p.getCode().equals(code)) {
                return p.getName().toUpperCase();
            }
        }

        return "";
    }

    private void obtenerOperaciones() {
        operaciones = new ArrayList<>();

        List<Actividad> activities = actividadFacade.obtenerActividadServicio(serviceCall.getServiceCallID().intValue());

        if (activities != null && !activities.isEmpty()) {
            for (Actividad a : activities) {
                operaciones.add(new ActividadDTO(a));
            }
        }
    }

    private void obtenerSoluciones() {
        soluciones = new ArrayList<>();

//        if (serviceCall.getServiceCallSolutions() != null && serviceCall.getServiceCallSolutions().getServiceCallSolution() != null && !serviceCall.getServiceCallSolutions().getServiceCallSolution().isEmpty()) {
//            for (ServiceCall.ServiceCallSolutions.ServiceCallSolution s : serviceCall.getServiceCallSolutions().getServiceCallSolution()) {
//                ServiceCallSolutions solution = serviceCallSolutionsFacade.obtenerSolucion(s.getSolutionID().intValue());
//
//                if (solution != null && solution.getSltCode() != null && solution.getSltCode() != 0) {
//                    soluciones.add(new ServiceCallSolutionsDTO(solution));
//                }
//            }
//        }
        List<LlamadaSolucion> calls = llamadaSolucionFacade.obtenerSoluciones(serviceCall.getServiceCallID().intValue());

        if (calls != null && !calls.isEmpty()) {
            for (LlamadaSolucion l : calls) {
                soluciones.add(new LlamadaSolucionDTO(l.getIdLlamadaSolucion(), l.getDocEntry(), l.getNumero(), l.getItemCode(), l.getEstado(),
                        l.getPropietario(), l.getSolucion(), l.getSintoma(), l.getCausa(), l.getComentarios(), l.getFecha()));
            }
        }
    }

    public void obtenerDatosSolucion() {
        solucion = new LlamadaSolucionDTO();
        Long idSolucion = Long.parseLong(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idSolucion"));

        if (idSolucion != null && idSolucion != 0) {
            for (LlamadaSolucionDTO l : soluciones) {
                if (l.getIdLlamadaSolucion().equals(idSolucion)) {
                    solucion = l;
                    break;
                }
            }
        } else {
            /*Significa que es una nueva solucion*/
            solucion.setItemCode(serviceCall.getItemCode());
        }
    }

    public void guardarSolucion() {
        if (solucion != null && solucion.getIdLlamadaSolucion() != null && solucion.getIdLlamadaSolucion() != 0) {
            for (LlamadaSolucionDTO l : soluciones) {
                if (l.getIdLlamadaSolucion().equals(solucion.getIdLlamadaSolucion())) {
                    l = solucion;
                    break;
                }
            }
        } else if (solucion != null) {
            solucion.setIdLlamadaSolucion(System.currentTimeMillis());
            if (solucion.getEstado() == null || solucion.getEstado() <= 0) {
                solucion.setEstado(-3);
            }
            solucion.setFecha(new Date());
            solucion.setPropietario(sessionInfoMBean.getUsuario());
            soluciones.add(solucion);
            solucion = new LlamadaSolucionDTO();
        }

        solucion = new LlamadaSolucionDTO();
    }

    private void obtenerHistorico() {
        historico = new ArrayList<>();

        List<ServiceCallHistory> history = serviceCallHistoryFacade.obtenerHistorico(serviceCall.getServiceCallID().intValue());

        if (history != null && !history.isEmpty()) {
            for (ServiceCallHistory s : history) {
                historico.add(new ServiceCallHistoryDTO(s));
            }
        }
    }

    public void seleccionarCola() {
        if (serviceCall.getQueue() != null && serviceCall.getQueue().equals("tYES")) {
            serviceCall.setQueue("tNO");
        } else {
            serviceCall.setQueue("tYES");
        }
    }

    public void seleccionarRecordatorio() {
        if (serviceCall.getReminder() == null || serviceCall.getReminder().equals("tNO")) {
            serviceCall.setReminder("tYES");
        } else {
            serviceCall.setReminder("tNO");
        }
    }

    public void seleccionarMostrarCalendario() {
        if (serviceCall.getDisplayInCalendar() == null || serviceCall.getDisplayInCalendar().equals("tNO")) {
            serviceCall.setDisplayInCalendar("tYES");
        } else {
            serviceCall.setDisplayInCalendar("tNO");
        }
    }

    private String obtenerType(String tipo) {
        switch (tipo) {
            case "du_Minuts":
                return "minutos";
            case "du_Hours":
                return "horas";
            case "du_Days":
                return "días";
            default:
                return "";
        }
    }

    public void cancelarLlamada() {
        solucion = new LlamadaSolucionDTO();
        adjunto = null;
        servicios = new ArrayList<>();
        serviceCall = new ServiceCall();
        soluciones = new ArrayList<>();
        adjuntos = new ArrayList<>();
    }

    private String[] generarDocumento(Integer id, Integer copias, String documento, boolean imprimir) {
        PrintReportDTO print = new PrintReportDTO();

        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            if (new File(res.getMensaje()).exists()) {
                return new String[]{res.getMensaje(), id + ".pdf"};
            } else {
                CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                return null;
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    public String abrirPdf() {
        if (serviceCall != null && serviceCall.getServiceCallID() != null && serviceCall.getServiceCallID() != 0) {
            String documento = "servicio";
            String url = applicationMBean.obtenerValorPropiedad("url.web.logistica") + documento + "/" + serviceCall.getServiceCallID();
            String[] attachment = generarDocumento(serviceCall.getServiceCallID().intValue(), 1, documento, false);

            if (attachment != null) {
                if (new File(attachment[0]).exists()) {
                    try {
                        return "openRuta('" + url + ".pdf');";
                    } catch (Exception e) {
                        CONSOLE.log(Level.SEVERE, "No se pudo generar la URL para el documento", e);
                        return "";
                    }
                } else {
                    CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                    return "";
                }
            } else {
                CONSOLE.log(Level.SEVERE, "No se pudo generar el documento");
                return "";
            }
        } else {
            return "";
        }
    }

    public void ejecutarSondaSincronizacion() {
        SondaSolucionesLlamadaClient client = new SondaSolucionesLlamadaClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        client.ejecutarSonda();
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
