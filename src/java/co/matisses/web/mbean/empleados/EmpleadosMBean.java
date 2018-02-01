package co.matisses.web.mbean.empleados;

import co.matisses.persistence.sap.entity.Banco;
import co.matisses.persistence.sap.entity.CentroCostosEmpleados;
import co.matisses.persistence.sap.entity.ClaseFormacion;
import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.entity.Equipo;
import co.matisses.persistence.sap.entity.MotivoCancelacion;
import co.matisses.persistence.sap.entity.Pais;
import co.matisses.persistence.sap.entity.PosicionEmpleado;
import co.matisses.persistence.sap.entity.Rol;
import co.matisses.persistence.sap.entity.SalesPerson;
import co.matisses.persistence.sap.entity.SucursalEmpleado;
import co.matisses.persistence.sap.entity.Usuario;
import co.matisses.persistence.sap.entity.ValoresUsuario;
import co.matisses.persistence.sap.facade.BancoFacade;
import co.matisses.persistence.sap.facade.CentroCostosEmpleadosFacade;
import co.matisses.persistence.sap.facade.ClaseFormacionFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.EquipoFacade;
import co.matisses.persistence.sap.facade.MotivoCancelacionFacade;
import co.matisses.persistence.sap.facade.PaisFacade;
import co.matisses.persistence.sap.facade.PosicionEmpleadoFacade;
import co.matisses.persistence.sap.facade.RolFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.sap.facade.SucursalEmpleadoFacade;
import co.matisses.persistence.sap.facade.UsuarioFacade;
import co.matisses.persistence.sap.facade.ValoresUsuarioFacade;
import co.matisses.persistence.web.entity.ComponenteCustodia;
import co.matisses.persistence.web.entity.ComponenteCustodiaEmpleado;
import co.matisses.persistence.web.entity.Custodia;
import co.matisses.persistence.web.entity.DetalleCustodia;
import co.matisses.persistence.web.facade.ComponenteCustodiaEmpleadoFacade;
import co.matisses.persistence.web.facade.ComponenteCustodiaFacade;
import co.matisses.persistence.web.facade.CustodiaFacade;
import co.matisses.persistence.web.facade.DetalleCustodiaFacade;
import co.matisses.web.bcs.businesspartner.BusinessPartner;
import co.matisses.web.bcs.client.BusinessPartnerClient;
import co.matisses.web.bcs.client.EmployeesInfoClient;
import co.matisses.web.bcs.employeesinfo.EmployeeInfo;
import co.matisses.web.dto.BancoDTO;
import co.matisses.web.dto.CentroCostosEmpleadosDTO;
import co.matisses.web.dto.ClaseFormacionDTO;
import co.matisses.web.dto.ComponenteCustodiaDTO;
import co.matisses.web.dto.ComponenteCustodiaEmpleadoDTO;
import co.matisses.web.dto.CustodiaDTO;
import co.matisses.web.dto.DetalleCustodiaDTO;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.dto.EquipoDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.MotivoCancelacionDTO;
import co.matisses.web.dto.PaisDTO;
import co.matisses.web.dto.PosicionEmpleadoDTO;
import co.matisses.web.dto.PrintReportDTO;
import co.matisses.web.dto.RolDTO;
import co.matisses.web.dto.SalesPersonDTO;
import co.matisses.web.dto.SucursalEmpleadoDTO;
import co.matisses.web.dto.UsuarioDTO;
import co.matisses.web.dto.ValoresUsuarioDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.clientes.PrintRepostClient;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
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
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeConstants;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author ygil
 */
@ViewScoped
@Named(value = "empleadosMBean")
public class EmpleadosMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger CONSOLE = Logger.getLogger(EmpleadosMBean.class.getSimpleName());
    private int paginaModals = 1;
    private int paginaCustodias = 1;
    private int totalPaginasModals = 1;
    private int totalPaginasCustodias = 1;
    private int datosPaginaModals = 5;
    private int datosPaginaCustodias = 2;
    private Integer idCustodia;
    private Integer idComponenteCustodia;
    private Integer idDetalleCustodia;
    private Integer cantidadComponente;
    private boolean dlgBusquedaEmpleado = false;
    private boolean dlgComponenteCustodia = false;
    private boolean dlgEliminarCustodia = false;
    private EmployeeInfo empleado;
    private EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo ausentismo;
    private EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo formacion;
    private EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo empleo;
    private List<Integer> paginasModals;
    private List<Integer> paginasCustodias;
    private List<PosicionEmpleadoDTO> posiciones;
    private List<CentroCostosEmpleadosDTO> centrosCosto;
    private List<SucursalEmpleadoDTO> sucursales;
    private List<EmpleadoDTO> jefes;
    private List<UsuarioDTO> usuarios;
    private List<SalesPersonDTO> empleadosVentas;
    private List<ValoresUsuarioDTO> tiposContrato;
    private List<PaisDTO> paises;
    private List<RolDTO> roles;
    private List<EquipoDTO> equipos;
    private List<ValoresUsuarioDTO> motivosCursoAltura;
    private List<MotivoCancelacionDTO> motivosCancelacion;
    private List<ValoresUsuarioDTO> tiposAusentismo;
    private List<ClaseFormacionDTO> clasesFormacion;
    private List<BancoDTO> bancos;
    private List<ValoresUsuarioDTO> arps;
    private List<ValoresUsuarioDTO> epss;
    private List<ValoresUsuarioDTO> cajasCompesacion;
    private List<ValoresUsuarioDTO> pensiones;
    private List<ValoresUsuarioDTO> cesantias;
    private List<CustodiaDTO> custodias;
    private List<DetalleCustodiaDTO> custodiasEmpleado;
    private List<DetalleCustodiaDTO> custodiasEmpleadoVisibles;
    private List<ComponenteCustodiaDTO> componentesCustodias;
    private List<ComponenteCustodiaEmpleadoDTO> componentesCustodiaEmpleado;
    private List<Empleado> empleados;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private PosicionEmpleadoFacade posicionEmpleadoFacade;
    @EJB
    private CentroCostosEmpleadosFacade centroCostosEmpleadosFacade;
    @EJB
    private SucursalEmpleadoFacade sucursalEmpleadoFacade;
    @EJB
    private UsuarioFacade usuarioFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;
    @EJB
    private ValoresUsuarioFacade valoresUsuarioFacade;
    @EJB
    private PaisFacade paisFacade;
    @EJB
    private RolFacade rolFacade;
    @EJB
    private EquipoFacade equipoFacade;
    @EJB
    private MotivoCancelacionFacade motivoCancelacionFacade;
    @EJB
    private ClaseFormacionFacade claseFormacionFacade;
    @EJB
    private BancoFacade bancoFacade;
    @EJB
    private CustodiaFacade custodiaFacade;
    @EJB
    private DetalleCustodiaFacade detalleCustodiaFacade;
    @EJB
    private ComponenteCustodiaFacade componenteCustodiaFacade;
    @EJB
    private ComponenteCustodiaEmpleadoFacade componenteCustodiaEmpleadoFacade;

    public EmpleadosMBean() {
        empleado = new EmployeeInfo();
        ausentismo = new EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo();
        formacion = new EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo();
        empleo = new EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo();
        paginasModals = new ArrayList<>();
        paginasCustodias = new ArrayList<>();
        posiciones = new ArrayList<>();
        centrosCosto = new ArrayList<>();
        sucursales = new ArrayList<>();
        jefes = new ArrayList<>();
        usuarios = new ArrayList<>();
        empleadosVentas = new ArrayList<>();
        tiposContrato = new ArrayList<>();
        paises = new ArrayList<>();
        roles = new ArrayList<>();
        equipos = new ArrayList<>();
        motivosCursoAltura = new ArrayList<>();
        motivosCancelacion = new ArrayList<>();
        tiposAusentismo = new ArrayList<>();
        clasesFormacion = new ArrayList<>();
        bancos = new ArrayList<>();
        arps = new ArrayList<>();
        epss = new ArrayList<>();
        cajasCompesacion = new ArrayList<>();
        pensiones = new ArrayList<>();
        cesantias = new ArrayList<>();
        custodias = new ArrayList<>();
        custodiasEmpleado = new ArrayList<>();
        custodiasEmpleadoVisibles = new ArrayList<>();
        componentesCustodias = new ArrayList<>();
        componentesCustodiaEmpleado = new ArrayList<>();
        empleados = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarPosiciones();
        cargarCentrosCosto();
        cargarSucursales();
        cargarJefes(0);
        cargarUsuarios();
        cargarEmpleadosVentas();
        cargarTiposContrato();
        cargarPaises();
        cargarRoles();
        cargarEquipos();
        cargarMotivosCursoAltura();
        cargarMotivosCancelacion();
        cargarTiposAusentismo();
        cargarClasesFormacion();
        cargarBancos();
        cargarArps();
        cargarEpss();
        cargarCajasCompesacion();
        cargarPensiones();
        cargarCesantias();
        cargarCustodias();
    }

    public int getPaginaModals() {
        return paginaModals;
    }

    public void setPaginaModals(int paginaModals) {
        this.paginaModals = paginaModals;
    }

    public int getPaginaCustodias() {
        return paginaCustodias;
    }

    public void setPaginaCustodias(int paginaCustodias) {
        this.paginaCustodias = paginaCustodias;
    }

    public int getTotalPaginasModals() {
        return totalPaginasModals;
    }

    public void setTotalPaginasModals(int totalPaginasModals) {
        this.totalPaginasModals = totalPaginasModals;
    }

    public int getTotalPaginasCustodias() {
        return totalPaginasCustodias;
    }

    public void setTotalPaginasCustodias(int totalPaginasCustodias) {
        this.totalPaginasCustodias = totalPaginasCustodias;
    }

    public Integer getIdCustodia() {
        return idCustodia;
    }

    public void setIdCustodia(Integer idCustodia) {
        this.idCustodia = idCustodia;
    }

    public Integer getIdComponenteCustodia() {
        return idComponenteCustodia;
    }

    public void setIdComponenteCustodia(Integer idComponenteCustodia) {
        this.idComponenteCustodia = idComponenteCustodia;
    }

    public Integer getCantidadComponente() {
        return cantidadComponente;
    }

    public void setCantidadComponente(Integer cantidadComponente) {
        this.cantidadComponente = cantidadComponente;
    }

    public boolean isDlgBusquedaEmpleado() {
        return dlgBusquedaEmpleado;
    }

    public void setDlgBusquedaEmpleado(boolean dlgBusquedaEmpleado) {
        this.dlgBusquedaEmpleado = dlgBusquedaEmpleado;
    }

    public boolean isDlgComponenteCustodia() {
        return dlgComponenteCustodia;
    }

    public void setDlgComponenteCustodia(boolean dlgComponenteCustodia) {
        this.dlgComponenteCustodia = dlgComponenteCustodia;
    }

    public boolean isDlgEliminarCustodia() {
        return dlgEliminarCustodia;
    }

    public void setDlgEliminarCustodia(boolean dlgEliminarCustodia) {
        this.dlgEliminarCustodia = dlgEliminarCustodia;
    }

    public EmployeeInfo getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmployeeInfo empleado) {
        this.empleado = empleado;
    }

    public EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo getAusentismo() {
        return ausentismo;
    }

    public void setAusentismo(EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo ausentismo) {
        this.ausentismo = ausentismo;
    }

    public EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo getFormacion() {
        return formacion;
    }

    public void setFormacion(EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo formacion) {
        this.formacion = formacion;
    }

    public EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo getEmpleo() {
        return empleo;
    }

    public void setEmpleo(EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo empleo) {
        this.empleo = empleo;
    }

    public List<Integer> getPaginasModals() {
        return paginasModals;
    }

    public void setPaginasModals(List<Integer> paginasModals) {
        this.paginasModals = paginasModals;
    }

    public List<Integer> getPaginasCustodias() {
        return paginasCustodias;
    }

    public void setPaginasCustodias(List<Integer> paginasCustodias) {
        this.paginasCustodias = paginasCustodias;
    }

    public List<PosicionEmpleadoDTO> getPosiciones() {
        return posiciones;
    }

    public void setPosiciones(List<PosicionEmpleadoDTO> posiciones) {
        this.posiciones = posiciones;
    }

    public List<CentroCostosEmpleadosDTO> getCentrosCosto() {
        return centrosCosto;
    }

    public void setCentrosCosto(List<CentroCostosEmpleadosDTO> centrosCosto) {
        this.centrosCosto = centrosCosto;
    }

    public List<SucursalEmpleadoDTO> getSucursales() {
        return sucursales;
    }

    public void setSucursales(List<SucursalEmpleadoDTO> sucursales) {
        this.sucursales = sucursales;
    }

    public List<EmpleadoDTO> getJefes() {
        return jefes;
    }

    public void setJefes(List<EmpleadoDTO> jefes) {
        this.jefes = jefes;
    }

    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }

    public List<SalesPersonDTO> getEmpleadosVentas() {
        return empleadosVentas;
    }

    public void setEmpleadosVentas(List<SalesPersonDTO> empleadosVentas) {
        this.empleadosVentas = empleadosVentas;
    }

    public List<ValoresUsuarioDTO> getTiposContrato() {
        return tiposContrato;
    }

    public void setTiposContrato(List<ValoresUsuarioDTO> tiposContrato) {
        this.tiposContrato = tiposContrato;
    }

    public List<PaisDTO> getPaises() {
        return paises;
    }

    public void setPaises(List<PaisDTO> paises) {
        this.paises = paises;
    }

    public List<RolDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RolDTO> roles) {
        this.roles = roles;
    }

    public List<EquipoDTO> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<EquipoDTO> equipos) {
        this.equipos = equipos;
    }

    public List<ValoresUsuarioDTO> getMotivosCursoAltura() {
        return motivosCursoAltura;
    }

    public void setMotivosCursoAltura(List<ValoresUsuarioDTO> motivosCursoAltura) {
        this.motivosCursoAltura = motivosCursoAltura;
    }

    public List<MotivoCancelacionDTO> getMotivosCancelacion() {
        return motivosCancelacion;
    }

    public void setMotivosCancelacion(List<MotivoCancelacionDTO> motivosCancelacion) {
        this.motivosCancelacion = motivosCancelacion;
    }

    public List<ValoresUsuarioDTO> getTiposAusentismo() {
        return tiposAusentismo;
    }

    public void setTiposAusentismo(List<ValoresUsuarioDTO> tiposAusentismo) {
        this.tiposAusentismo = tiposAusentismo;
    }

    public List<ClaseFormacionDTO> getClasesFormacion() {
        return clasesFormacion;
    }

    public void setClasesFormacion(List<ClaseFormacionDTO> clasesFormacion) {
        this.clasesFormacion = clasesFormacion;
    }

    public List<BancoDTO> getBancos() {
        return bancos;
    }

    public void setBancos(List<BancoDTO> bancos) {
        this.bancos = bancos;
    }

    public List<ValoresUsuarioDTO> getArps() {
        return arps;
    }

    public void setArps(List<ValoresUsuarioDTO> arps) {
        this.arps = arps;
    }

    public List<ValoresUsuarioDTO> getEpss() {
        return epss;
    }

    public void setEpss(List<ValoresUsuarioDTO> epss) {
        this.epss = epss;
    }

    public List<ValoresUsuarioDTO> getCajasCompesacion() {
        return cajasCompesacion;
    }

    public void setCajasCompesacion(List<ValoresUsuarioDTO> cajasCompesacion) {
        this.cajasCompesacion = cajasCompesacion;
    }

    public List<ValoresUsuarioDTO> getPensiones() {
        return pensiones;
    }

    public void setPensiones(List<ValoresUsuarioDTO> pensiones) {
        this.pensiones = pensiones;
    }

    public List<ValoresUsuarioDTO> getCesantias() {
        return cesantias;
    }

    public void setCesantias(List<ValoresUsuarioDTO> cesantias) {
        this.cesantias = cesantias;
    }

    public List<CustodiaDTO> getCustodias() {
        return custodias;
    }

    public void setCustodias(List<CustodiaDTO> custodias) {
        this.custodias = custodias;
    }

    public List<DetalleCustodiaDTO> getCustodiasEmpleado() {
        return custodiasEmpleado;
    }

    public void setCustodiasEmpleado(List<DetalleCustodiaDTO> custodiasEmpleado) {
        this.custodiasEmpleado = custodiasEmpleado;
    }

    public List<DetalleCustodiaDTO> getCustodiasEmpleadoVisibles() {
        return custodiasEmpleadoVisibles;
    }

    public void setCustodiasEmpleadoVisibles(List<DetalleCustodiaDTO> custodiasEmpleadoVisibles) {
        this.custodiasEmpleadoVisibles = custodiasEmpleadoVisibles;
    }

    public List<ComponenteCustodiaDTO> getComponentesCustodias() {
        return componentesCustodias;
    }

    public void setComponentesCustodias(List<ComponenteCustodiaDTO> componentesCustodias) {
        this.componentesCustodias = componentesCustodias;
    }

    public List<ComponenteCustodiaEmpleadoDTO> getComponentesCustodiaEmpleado() {
        return componentesCustodiaEmpleado;
    }

    public void setComponentesCustodiaEmpleado(List<ComponenteCustodiaEmpleadoDTO> componentesCustodiaEmpleado) {
        this.componentesCustodiaEmpleado = componentesCustodiaEmpleado;
    }

    public List<Empleado> getEmpleados() {
        return empleados;
    }

    public void setEmpleados(List<Empleado> empleados) {
        this.empleados = empleados;
    }

    public StreamedContent getSignatureDownload() throws IOException {
        CONSOLE.log(Level.INFO, "Se inicia el proceso de descarga de la custodia del empleado");
        recibirCustodias();
        String file = "recibir" + "[" + empleado.getOfficeExtension() + "].pdf";
        try {
            InputStream stream = new ByteArrayInputStream(fileToBytes(new File(applicationMBean.obtenerValorPropiedad("url.folder.custodias") + file), file));
            CONSOLE.log(Level.INFO, "Se termina el proceso de descarga de la custodia del empleado");
            return new DefaultStreamedContent(stream, "application/pdf", file);
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "No se encontro el archivo para descargar. ", e);
            return null;
        }
    }

    private void cargarPosiciones() {
        posiciones = new ArrayList<>();

        List<PosicionEmpleado> positions = posicionEmpleadoFacade.findAll();

        if (positions != null && !positions.isEmpty()) {
            for (PosicionEmpleado p : positions) {
                PosicionEmpleadoDTO dto = new PosicionEmpleadoDTO();

                dto.setDescriptio(p.getDescriptio());
                dto.setLocFields(p.getLocFields());
                dto.setName(p.getName());
                dto.setPosID(p.getPosID());

                posiciones.add(dto);
            }

            Collections.sort(posiciones, new Comparator<PosicionEmpleadoDTO>() {
                @Override
                public int compare(PosicionEmpleadoDTO o1, PosicionEmpleadoDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarCentrosCosto() {
        centrosCosto = new ArrayList<>();

        List<CentroCostosEmpleados> centers = centroCostosEmpleadosFacade.findAll();

        if (centers != null && !centers.isEmpty()) {
            for (CentroCostosEmpleados c : centers) {
                CentroCostosEmpleadosDTO dto = new CentroCostosEmpleadosDTO();

                dto.setCode(c.getCode());
                dto.setFather(c.getFather());
                dto.setName(c.getName());
                dto.setRemarks(c.getRemarks());
                dto.setUserSign(c.getUserSign());

                centrosCosto.add(dto);
            }

            Collections.sort(centrosCosto, new Comparator<CentroCostosEmpleadosDTO>() {
                @Override
                public int compare(CentroCostosEmpleadosDTO o1, CentroCostosEmpleadosDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarSucursales() {
        sucursales = new ArrayList<>();

        List<SucursalEmpleado> branchs = sucursalEmpleadoFacade.findAll();

        if (branchs != null && !branchs.isEmpty()) {
            for (SucursalEmpleado s : branchs) {
                SucursalEmpleadoDTO dto = new SucursalEmpleadoDTO();

                dto.setCode(s.getCode());
                dto.setName(s.getName());
                dto.setRemarks(s.getRemarks());
                dto.setUserSign(s.getUserSign());

                sucursales.add(dto);
            }

            Collections.sort(sucursales, new Comparator<SucursalEmpleadoDTO>() {
                @Override
                public int compare(SucursalEmpleadoDTO o1, SucursalEmpleadoDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarJefes(Integer empID) {
        jefes = new ArrayList<>();

        List<Empleado> employee = empleadoFacade.obtenerEmpleadosJefes(empID);

        if (employee != null && !employee.isEmpty()) {
            for (Empleado e : employee) {
                EmpleadoDTO dto = new EmpleadoDTO();

                dto.setEmpID(e.getEmpID());
                dto.setLastName(e.getLastName());
                dto.setFirstName(e.getFirstName());

                jefes.add(dto);
            }

            Collections.sort(jefes, new Comparator<EmpleadoDTO>() {
                @Override
                public int compare(EmpleadoDTO o1, EmpleadoDTO o2) {
                    return o1.getLastName().compareTo(o2.getLastName());
                }
            });
        }
    }

    private void cargarUsuarios() {
        usuarios = new ArrayList<>();

        List<Usuario> users = usuarioFacade.obtenerUsuariosActivos();

        if (users != null && !users.isEmpty()) {
            for (Usuario u : users) {
                UsuarioDTO dto = new UsuarioDTO();

                dto.setLocked(u.getLocked());
                dto.setUserCode(u.getUserCode());
                dto.setUserID(u.getUserID());

                usuarios.add(dto);
            }

            Collections.sort(usuarios, new Comparator<UsuarioDTO>() {
                @Override
                public int compare(UsuarioDTO o1, UsuarioDTO o2) {
                    return o1.getUserCode().compareTo(o2.getUserCode());
                }
            });
        }
    }

    private void cargarEmpleadosVentas() {
        empleadosVentas = new ArrayList<>();

        List<SalesPerson> salesPersons = salesPersonFacade.findAll();

        if (salesPersons != null && !salesPersons.isEmpty()) {
            for (SalesPerson s : salesPersons) {
                SalesPersonDTO dto = new SalesPersonDTO();

                dto.setActive(s.getActive());
                dto.setSlpCode(s.getSlpCode());
                dto.setSlpName(s.getSlpName());

                empleadosVentas.add(dto);
            }

            Collections.sort(empleadosVentas, new Comparator<SalesPersonDTO>() {
                @Override
                public int compare(SalesPersonDTO o1, SalesPersonDTO o2) {
                    return o1.getSlpName().compareTo(o2.getSlpName());
                }
            });
        }
    }

    private void cargarTiposContrato() {
        tiposContrato = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaTipoContrato();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                tiposContrato.add(dto);
            }

            Collections.sort(tiposContrato, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    private void cargarPaises() {
        paises = new ArrayList<>();

        List<Pais> countries = paisFacade.findAll();

        if (countries != null && !countries.isEmpty()) {
            for (Pais p : countries) {
                PaisDTO dto = new PaisDTO();

                dto.setName(p.getName());
                dto.setCode(p.getCode());

                paises.add(dto);
            }

            Collections.sort(paises, new Comparator<PaisDTO>() {
                @Override
                public int compare(PaisDTO o1, PaisDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarRoles() {
        roles = new ArrayList<>();

        List<Rol> rols = rolFacade.findAll();

        if (rols != null && !rols.isEmpty()) {
            for (Rol r : rols) {
                RolDTO dto = new RolDTO();

                dto.setDescriptio(r.getDescriptio());
                dto.setLocked(r.getLocked());
                dto.setName(r.getName());
                dto.setTypeID(r.getTypeID());

                roles.add(dto);
            }

            Collections.sort(roles, new Comparator<RolDTO>() {
                @Override
                public int compare(RolDTO o1, RolDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarEquipos() {
        equipos = new ArrayList<>();

        List<Equipo> teams = equipoFacade.findAll();

        if (teams != null && !teams.isEmpty()) {
            for (Equipo e : teams) {
                EquipoDTO dto = new EquipoDTO();

                dto.setDescriptio(e.getDescriptio());
                dto.setName(e.getName());
                dto.setTeamID(e.getTeamID());

                equipos.add(dto);
            }

            Collections.sort(equipos, new Comparator<EquipoDTO>() {
                @Override
                public int compare(EquipoDTO o1, EquipoDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarMotivosCursoAltura() {
        motivosCursoAltura = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaMotivosCursoAltura();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                motivosCursoAltura.add(dto);
            }

            Collections.sort(motivosCursoAltura, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    private void cargarMotivosCancelacion() {
        motivosCancelacion = new ArrayList<>();

        List<MotivoCancelacion> reasons = motivoCancelacionFacade.findAll();

        if (reasons != null && !reasons.isEmpty()) {
            for (MotivoCancelacion m : reasons) {
                MotivoCancelacionDTO dto = new MotivoCancelacionDTO();

                dto.setDescriptio(m.getDescriptio());
                dto.setName(m.getName());
                dto.setReasonID(m.getReasonID());

                motivosCancelacion.add(dto);
            }

            Collections.sort(motivosCancelacion, new Comparator<MotivoCancelacionDTO>() {
                @Override
                public int compare(MotivoCancelacionDTO o1, MotivoCancelacionDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarTiposAusentismo() {
        tiposAusentismo = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaTiposAusentismo();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                tiposAusentismo.add(dto);
            }

            Collections.sort(tiposAusentismo, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    private void cargarClasesFormacion() {
        clasesFormacion = new ArrayList<>();

        List<ClaseFormacion> training = claseFormacionFacade.findAll();

        if (training != null && !training.isEmpty()) {
            for (ClaseFormacion c : training) {
                ClaseFormacionDTO dto = new ClaseFormacionDTO();

                dto.setDescriptio(c.getDescriptio());
                dto.setEdType(c.getEdType());
                dto.setLocked(c.getLocked());
                dto.setName(c.getName());

                clasesFormacion.add(dto);
            }

            Collections.sort(clasesFormacion, new Comparator<ClaseFormacionDTO>() {
                @Override
                public int compare(ClaseFormacionDTO o1, ClaseFormacionDTO o2) {
                    return o1.getName().compareTo(o2.getName());
                }
            });
        }
    }

    private void cargarBancos() {
        bancos = new ArrayList<>();

        List<Banco> banks = bancoFacade.findAll();

        if (banks != null && !banks.isEmpty()) {
            for (Banco b : banks) {
                BancoDTO dto = new BancoDTO();

                dto.setAbsEntry(b.getAbsEntry());
                dto.setAliasName(b.getAliasName());
                dto.setBankCode(b.getBankCode());
                dto.setBankName(b.getBankName());
                dto.setBnkOpCode(b.getBnkOpCode());
                dto.setBsDocDate(b.getBsDocDate());
                dto.setBsPstDate(b.getBsPstDate());
                dto.setBsValDate(b.getBsValDate());
                dto.setCountryCod(b.getCountryCod());
                dto.setDataSource(b.getDataSource());
                dto.setDfltAcct(b.getDfltAcct());
                dto.setDfltActKey(b.getDfltActKey());
                dto.setDfltBranch(b.getDfltBranch());
                dto.setLocked(b.getLocked());
                dto.setNextChckNo(b.getNextChckNo());
                dto.setNextNum(b.getNextNum());
                dto.setPostOffice(b.getPostOffice());
                dto.setSwiftNum(b.getSwiftNum());
                dto.setUserSign(b.getUserSign());
                dto.setiBAN(b.getiBAN());

                bancos.add(dto);
            }

            Collections.sort(bancos, new Comparator<BancoDTO>() {
                @Override
                public int compare(BancoDTO o1, BancoDTO o2) {
                    return o1.getBankName().compareTo(o2.getBankName());
                }
            });
        }
    }

    public void cargarArps() {
        arps = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaArps();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                arps.add(dto);
            }

            Collections.sort(arps, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    public void cargarEpss() {
        epss = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaEpss();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                epss.add(dto);
            }

            Collections.sort(epss, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    public void cargarCajasCompesacion() {
        cajasCompesacion = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaCajasCompesacion();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                cajasCompesacion.add(dto);
            }

            Collections.sort(cajasCompesacion, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    public void cargarPensiones() {
        pensiones = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaPensiones();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                pensiones.add(dto);
            }

            Collections.sort(pensiones, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    public void cargarCesantias() {
        cesantias = new ArrayList<>();

        List<ValoresUsuario> values = valoresUsuarioFacade.listaCesantias();

        if (values != null && !values.isEmpty()) {
            for (ValoresUsuario v : values) {
                ValoresUsuarioDTO dto = new ValoresUsuarioDTO();

                dto.setDescr(v.getDescr());
                dto.setFieldID(v.getFieldID());
                dto.setFldValue(v.getFldValue());
                dto.setIndexID(v.getIndexID());
                dto.setTableID(v.getTableID());

                cesantias.add(dto);
            }

            Collections.sort(cesantias, new Comparator<ValoresUsuarioDTO>() {
                @Override
                public int compare(ValoresUsuarioDTO o1, ValoresUsuarioDTO o2) {
                    return o1.getDescr().compareTo(o2.getDescr());
                }
            });
        }
    }

    public void cargarCustodias() {
        custodias = new ArrayList<>();

        List<Custodia> custody = custodiaFacade.findAll();

        if (custody != null && !custody.isEmpty()) {
            int contador = 0;
            for (Custodia c : custody) {
                CustodiaDTO dto = new CustodiaDTO();

                dto.setContador(contador);
                dto.setIdCustodia(c.getIdCustodia());
                dto.setImagen(c.getImagen());
                dto.setNombreCustodia(c.getNombreCustodia());
                dto.setRequiereActa(c.getRequiereActa());
                dto.setRequiereContrasena(c.getRequiereContrasena());
                dto.setTipoCantidad(c.getTipoCantidad());
                dto.setTipoCelular(c.getTipoCelular());

                custodias.add(dto);
                contador++;
            }

            Collections.sort(custodias, new Comparator<CustodiaDTO>() {
                @Override
                public int compare(CustodiaDTO o1, CustodiaDTO o2) {
                    return o1.getNombreCustodia().compareTo(o2.getNombreCustodia());
                }
            });
        }
    }

    public void buscarEmpleado() {
        dlgBusquedaEmpleado = false;
        empleados = new ArrayList<>();

        if (empleado.getOfficeExtension() != null && !empleado.getOfficeExtension().isEmpty()) {
            empleados = empleadoFacade.obtenerEmpleado("officeExt", empleado.getOfficeExtension());
        } else if (empleado.getFirstName() != null && !empleado.getFirstName().isEmpty()) {
            empleados = empleadoFacade.obtenerEmpleado("firstName", empleado.getFirstName());
        } else if (empleado.getMiddleName() != null && !empleado.getMiddleName().isEmpty()) {
            empleados = empleadoFacade.obtenerEmpleado("middleName", empleado.getMiddleName());
        } else if (empleado.getLastName() != null && !empleado.getLastName().isEmpty()) {
            empleados = empleadoFacade.obtenerEmpleado("lastName", empleado.getLastName());
        } else if (empleado.getEmployeeID() != null && empleado.getEmployeeID() != 0) {
            empleados = empleadoFacade.obtenerEmpleado("empID", empleado.getEmployeeID().toString());
        }

        if (empleados != null && !empleados.isEmpty()) {
            if (empleados.size() == 1) {
                obtenerDatosEmpleado(empleados.get(0).getEmpID());
            } else {
                dlgBusquedaEmpleado = true;
            }
        } else {
            empleado = new EmployeeInfo();
        }
    }

    public void seleccionarEmpleado() {
        Integer empID = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("empID"));

        obtenerDatosEmpleado(empID);
    }

    private void obtenerDatosEmpleado(Integer empID) {
        EmployeesInfoClient client = new EmployeesInfoClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        empleado = client.buscarEmpleado(empID);
        if (empleado.getStartDate() != null) {
            empleado.setFechaInicio(empleado.getStartDate().toGregorianCalendar().getTime());
        }
        if (empleado.getTerminationDate() != null) {
            empleado.setFechaFin(empleado.getTerminationDate().toGregorianCalendar().getTime());
        }
        if (empleado.getUltCursoAltura() != null) {
            empleado.setFechaCursoAlturas(empleado.getUltCursoAltura().toGregorianCalendar().getTime());
        }
        if (empleado.getDateOfBirth() != null) {
            empleado.setFechaNacimiento(empleado.getDateOfBirth().toGregorianCalendar().getTime());
        }

        /*Se formatean las fechas de los ausentismos*/
        if (empleado.getEmployeeAbsenceInfoLines() != null && empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo() != null && !empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().isEmpty()) {
            for (EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo a : empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo()) {
                if (a.getFromDate() != null) {
                    a.setFechaInicio(a.getFromDate().toGregorianCalendar().getTime());
                }
                if (a.getToDate() != null) {
                    a.setFechaFin(a.getToDate().toGregorianCalendar().getTime());
                }
            }
        }

        /*Se formatean las fechas de las formaciones*/
        if (empleado.getEmployeeEducationInfoLines() != null && empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo() != null
                && !empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().isEmpty()) {
            for (EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo e : empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo()) {
                if (e.getFromDate() != null) {
                    e.setFechaInicio(e.getFromDate().toGregorianCalendar().getTime());
                }
                if (e.getToDate() != null) {
                    e.setFechaFin(e.getToDate().toGregorianCalendar().getTime());
                }
            }
        }

        /*Se formatean las fechas de las valoraciones*/
        if (empleado.getEmployeeReviewsInfoLines() != null && empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo() != null && !empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo().isEmpty()) {
            for (EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo r : empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo()) {
                if (r.getDate() != null) {
                    r.setFecha(r.getDate().toGregorianCalendar().getTime());
                }
            }
        }

        /*Se formatean las fechas de los empleos anteriores*/
        if (empleado.getEmployeePreviousEmpoymentInfoLines() != null && empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo() != null
                && !empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().isEmpty()) {
            for (EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo e : empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo()) {
                if (e.getFromDtae() != null) {
                    e.setFechaInicio(e.getFromDtae().toGregorianCalendar().getTime());
                }
                if (e.getToDate() != null) {
                    e.setFechaFin(e.getToDate().toGregorianCalendar().getTime());
                }
            }
        }

        cargarJefes(empID);
        obtenerCustodiasEmpleado();
    }

    public void guardarEmpleado() {
        try {
            EmployeesInfoClient client = new EmployeesInfoClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

            if (empleado.getFechaInicio() != null) {
                empleado.setStartDate(convertirDate(obtenerXMLDate(empleado.getFechaInicio())));
            }
            if (empleado.getFechaFin() != null) {
                empleado.setTerminationDate(convertirDate(obtenerXMLDate(empleado.getFechaFin())));
            }
            if (empleado.getFechaCursoAlturas() != null) {
                empleado.setUltCursoAltura(convertirDate(obtenerXMLDate(empleado.getFechaCursoAlturas())));
            }
            if (empleado.getFechaNacimiento() != null) {
                empleado.setDateOfBirth(convertirDate(obtenerXMLDate(empleado.getFechaNacimiento())));
            }
            empleado.setPicture(empleado.getOfficeExtension() + ".jpg");

            if (empleado.getEmployeeAbsenceInfoLines() != null && empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo() != null
                    && !empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().isEmpty()) {
                for (EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo a : empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo()) {
                    if (a.getFechaInicio() != null) {
                        a.setFromDate(convertirDate(obtenerXMLDate(a.getFechaInicio())));
                    }
                    if (a.getFechaFin() != null) {
                        a.setToDate(convertirDate(obtenerXMLDate(a.getFechaFin())));
                    }
                }
            }

            if (empleado.getEmployeeEducationInfoLines() != null && empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo() != null
                    && !empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().isEmpty()) {
                for (EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo e : empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo()) {
                    if (e.getFechaInicio() != null) {
                        e.setFromDate(convertirDate(obtenerXMLDate(e.getFechaInicio())));
                    }
                    if (e.getFechaFin() != null) {
                        e.setToDate(convertirDate(obtenerXMLDate(e.getFechaFin())));
                    }
                }
            }

            if (empleado.getEmployeeReviewsInfoLines() != null && empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo() != null
                    && !empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo().isEmpty()) {
                for (EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo r : empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo()) {
                    if (r.getFecha() != null) {
                        r.setDate(convertirDate(obtenerXMLDate(r.getFecha())));
                    }
                }
            }

            if (empleado.getEmployeePreviousEmpoymentInfoLines() != null && empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo() != null
                    && !empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().isEmpty()) {
                for (EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo e : empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo()) {
                    if (e.getFechaInicio() != null) {
                        e.setFromDtae(convertirDate(obtenerXMLDate(e.getFechaInicio())));
                    }
                    if (e.getFechaFin() != null) {
                        e.setToDate(convertirDate(obtenerXMLDate(e.getFechaFin())));
                    }
                }
            }

            if (empleado.getEmployeeID() != null && empleado.getEmployeeID() != 0) {
                GenericRESTResponseDTO res = client.editarEmpleado(empleado);

                if (res == null || res.getEstado() == -1) {
                    mostrarMensaje("Error", "No fue posible modificar el empleado. " + res.getMensaje(), true, false, false);
                    CONSOLE.log(Level.SEVERE, "No fue posible modificar el empleado. {0}", res.getMensaje());
                    return;
                }

                mostrarMensaje("Éxito", "Se modificó el empleado correctamente.", false, true, false);
            } else {
                GenericRESTResponseDTO res = client.crearEmpleado(empleado);

                if (res == null || res.getEstado() == -1) {
                    mostrarMensaje("Error", "No fue posible crear el empleado. " + res.getMensaje(), true, false, false);
                    CONSOLE.log(Level.SEVERE, "No fue posible crear el empleado. {0}", res.getMensaje());
                    return;
                }

                mostrarMensaje("Éxito", "Se creó el empleado correctamente.", false, true, false);
                crearSocio();
            }

            /*Se guardan las custodias*/
            if (custodiasEmpleado != null && !custodiasEmpleado.isEmpty()) {
                for (DetalleCustodiaDTO d : custodiasEmpleado) {
                    if (d.getIdDetalleCustodia() == null || d.getIdDetalleCustodia() == 0) {
                        DetalleCustodia det = new DetalleCustodia();

                        det.setActivo(true);
                        det.setCantidad(d.getCantidad());
                        det.setCedula(d.getCedula());
                        det.setComentario(d.getComentario());
                        det.setContrasenaCustodia(d.getContrasenaCustodia());
                        det.setFechaEntrega(d.getFechaEntrega());
                        det.setIdCustodia(new Custodia(d.getIdCustodia().getIdCustodia()));
                        det.setUsuarioEntrega(d.getUsuarioEntrega());

                        try {
                            detalleCustodiaFacade.create(det);
                            d.setIdDetalleCustodia(det.getIdDetalleCustodia());
                            CONSOLE.log(Level.INFO, "Se registro la custodia con id {0} para el empleado {1}", new Object[]{det.getIdDetalleCustodia(), empleado.getOfficeExtension()});

                            /*Si hay complementos se incluyes*/
                            if (d.getComponentes() != null && !d.getComponentes().isEmpty()) {
                                for (ComponenteCustodiaEmpleadoDTO c : d.getComponentes()) {
                                    ComponenteCustodiaEmpleado comp = new ComponenteCustodiaEmpleado();

                                    comp.setCantidad(c.getCantidad());
                                    comp.setComentario(c.getComentario());
                                    comp.setIdComponente(new ComponenteCustodia(c.getIdComponente().getIdComponenteCustodia()));
                                    comp.setIdDetalleCustodia(new DetalleCustodia(det.getIdDetalleCustodia()));

                                    componenteCustodiaEmpleadoFacade.create(comp);
                                    c.setIdDetalleComponente(comp.getIdDetalleComponente());
                                }
                            }
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al registrar el componente de custodia", e);
                        }
                    } else {
                        DetalleCustodia det = new DetalleCustodia();

                        det.setIdDetalleCustodia(d.getIdDetalleCustodia());
                        det.setActivo(true);
                        det.setCantidad(d.getCantidad());
                        det.setCedula(d.getCedula());
                        det.setComentario(d.getComentario());
                        det.setContrasenaCustodia(d.getContrasenaCustodia());
                        det.setFechaEntrega(d.getFechaEntrega());
                        det.setIdCustodia(new Custodia(d.getIdCustodia().getIdCustodia()));
                        det.setUsuarioEntrega(d.getUsuarioEntrega());

                        try {
                            detalleCustodiaFacade.edit(det);

                            /*Si hay custodias se validan*/
                            if (d.getComponentes() != null && !d.getComponentes().isEmpty()) {
                                for (ComponenteCustodiaEmpleadoDTO c : d.getComponentes()) {
                                    if (c.getIdDetalleComponente() == null || c.getIdDetalleComponente() == 0) {
                                        ComponenteCustodiaEmpleado comp = new ComponenteCustodiaEmpleado();

                                        comp.setCantidad(c.getCantidad());
                                        comp.setComentario(c.getComentario());
                                        comp.setIdComponente(new ComponenteCustodia(c.getIdComponente().getIdComponenteCustodia()));
                                        comp.setIdDetalleCustodia(new DetalleCustodia(det.getIdDetalleCustodia()));

                                        componenteCustodiaEmpleadoFacade.create(comp);
                                        c.setIdDetalleComponente(comp.getIdDetalleComponente());
                                    } else {
                                        ComponenteCustodiaEmpleado comp = new ComponenteCustodiaEmpleado();

                                        comp.setIdDetalleComponente(c.getIdDetalleComponente());
                                        comp.setCantidad(c.getCantidad());
                                        comp.setComentario(c.getComentario());
                                        comp.setIdComponente(new ComponenteCustodia(c.getIdComponente().getIdComponenteCustodia()));
                                        comp.setIdDetalleCustodia(new DetalleCustodia(det.getIdDetalleCustodia()));

                                        componenteCustodiaEmpleadoFacade.edit(comp);
                                    }
                                }
                            }
                        } catch (Exception e) {
                            CONSOLE.log(Level.SEVERE, "Ocurrio un error al registrar el componente de custodia", e);
                        }
                    }
                }
            }

            /*Se validan que custodias se deben eliminar*/
            List<DetalleCustodia> cus = detalleCustodiaFacade.obtenerCustodiasEmpleado(empleado.getOfficeExtension());

            if (cus != null && !cus.isEmpty()) {
                for (int i = 0; i < cus.size(); i++) {
                    DetalleCustodia d = cus.get(i);

                    List<ComponenteCustodiaEmpleado> comp = componenteCustodiaEmpleadoFacade.obtenerComponentesCustodiaEmpleado(d.getIdDetalleCustodia());

                    for (DetalleCustodiaDTO dc : custodiasEmpleado) {
                        if (d.getIdDetalleCustodia().equals(dc.getIdDetalleCustodia())) {
                            if (comp != null && !comp.isEmpty()) {
                                for (int j = 0; j < comp.size(); j++) {
                                    ComponenteCustodiaEmpleado c = comp.get(j);

                                    if (dc.getComponentes() != null && !dc.getComponentes().isEmpty()) {
                                        for (ComponenteCustodiaEmpleadoDTO cc : dc.getComponentes()) {
                                            if (cc.getIdDetalleComponente() != null && cc.getIdDetalleComponente().equals(c.getIdDetalleComponente())) {
                                                comp.remove(j);
                                                j--;
                                                break;
                                            }
                                        }
                                    }
                                }

                                if (dc.getComponentes() != null && !dc.getComponentes().isEmpty()) {
                                    for (ComponenteCustodiaEmpleado c : comp) {
                                        componenteCustodiaEmpleadoFacade.remove(c);
                                    }
                                }
                            }

                            cus.remove(i);
                            i--;
                            break;
                        }
                    }
                }

                if (cus != null && !cus.isEmpty()) {
                    for (DetalleCustodia d : cus) {
                        List<ComponenteCustodiaEmpleado> comp = componenteCustodiaEmpleadoFacade.obtenerComponentesCustodiaEmpleado(d.getIdDetalleCustodia());

                        if (comp != null && !comp.isEmpty()) {
                            for (ComponenteCustodiaEmpleado c : comp) {
                                componenteCustodiaEmpleadoFacade.remove(c);
                            }
                        }
                        detalleCustodiaFacade.remove(d);
                    }
                }
            }

            limpir();
        } catch (ParseException | DatatypeConfigurationException e) {
            CONSOLE.log(Level.SEVERE, "No fue posible modificar o crear el empleado. ", e);
        }
    }

    private void crearSocio() {
        BusinessPartner partner = new BusinessPartner();

        partner.setCardCode(empleado.getOfficeExtension() + "PR");
        partner.setCardType("S");
        if (empleado.getMiddleName() != null) {
            partner.setCardName(empleado.getLastName() + " " + empleado.getFirstName() + " " + empleado.getMiddleName());
            partner.setCardForeignName(empleado.getLastName() + " " + empleado.getFirstName() + " " + empleado.getMiddleName());
        } else {
            partner.setCardName(empleado.getLastName() + " " + empleado.getFirstName());
            partner.setCardForeignName(empleado.getLastName() + " " + empleado.getFirstName());
        }
        partner.setGroupCode(101L);
        partner.setCurrency("COP");
        partner.setFederalTaxID(empleado.getOfficeExtension());
        partner.setValid("tYES");
        partner.setPhone1(empleado.getHomePhone());
        partner.setCellular(empleado.getMobilePhone());
        partner.setFatherType("P");
        partner.setUEsAutorret("N");
        partner.setUBPCORTC("RS");
        partner.setUBPCOTDC("13");
        partner.setUBPCOTP("01");
        partner.setUBPCONombre(empleado.getMiddleName() != null
                ? empleado.getFirstName() + " " + empleado.getMiddleName() : empleado.getFirstName());

        String[] s = empleado.getLastName().split(" ");

        partner.setUBPCO1Apellido(s[0]);
        if (s.length > 1) {
            partner.setUBPCO2Apellido(s[1]);
        }
        partner.setUBPCOBPExt("01");
        partner.setUBPCOTBPE("01");
        partner.setUBPCOAddress(empleado.getHomeStreet());
        partner.setUManejo("DIA");
        partner.setUBDErst("Y");
        partner.setCity(empleado.getHomeCity());
        partner.setAddress("CASA");
        partner.setEmailAddress(empleado.getEMail());

        /*Se registra la direccion*/
        partner.setBPAddresses(new BusinessPartner.BPAddresses());

        BusinessPartner.BPAddresses.BPAddress address = new BusinessPartner.BPAddresses.BPAddress();

        address.setAddressName("CASA");
        address.setAddressType("bo_BillTo");
        address.setBlock(partner.getPhone1());
        address.setBuildingFloorRoom(partner.getCellular());
        address.setCity(partner.getCity());
        address.setCountry(empleado.getHomeCountry());
        address.setCounty(partner.getEmailAddress());
        address.setStreet(partner.getUBPCOAddress());

        partner.getBPAddresses().getBPAddress().add(address);

        BusinessPartnerClient client = new BusinessPartnerClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.crearCliente(partner);

            if (res.getEstado() < 0) {
                mostrarMensaje("Error", "No se pudo crear el proveedor para el empleado. " + res.getMensaje(), true, false, false);
                CONSOLE.log(Level.SEVERE, "No se pudo crear el proveedor para el empleado. {0}", res.getMensaje());
                return;
            }
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "No se pudo crear el proveedor para el empleado. {0}", e);
        }
    }

    private GregorianCalendar obtenerXMLDate(Date fecha) throws ParseException {
        String year = new SimpleDateFormat("yyyy").format(fecha);
        String month = new SimpleDateFormat("MM").format(fecha);
        String day = new SimpleDateFormat("dd").format(fecha);

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(year + "-" + month + "-" + day));
        return calendar;
    }

    private XMLGregorianCalendar convertirDate(GregorianCalendar calendar) throws DatatypeConfigurationException {
        XMLGregorianCalendar xml = DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar);
        xml.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
        xml.setTime(DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED, DatatypeConstants.FIELD_UNDEFINED);

        return xml;
    }

    public void seleccionarEmpleadoActivo() {
        if (empleado.getActive() == null || empleado.getActive().equals("tNO")) {
            empleado.setActive("tYES");
        } else {
            empleado.setActive("tNO");
        }
    }

    public void obtenerAusentismos() {
        empleado.getEmployeeAbsenceInfoLines().setAusentimosVisibles(new ArrayList<EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo>());

        long nroRegistros = empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().size();

        totalPaginasModals = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPaginaModals) + (nroRegistros % datosPaginaModals > 0 ? 1 : 0);
        if (paginaModals == 0) {
            paginaModals = 1;
        } else if (paginaModals > totalPaginasModals) {
            paginaModals = totalPaginasModals;
        }
        construirListaPaginasModals();

        if (empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo() != null && !empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().isEmpty()) {
            for (int i = (((paginaModals * 1) - 1) * datosPaginaModals); i < (datosPaginaModals * paginaModals); i++) {
                try {
                    empleado.getEmployeeAbsenceInfoLines().getAusentimosVisibles().add(empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void agregarAusentismo() {
        if (ausentismo.getFechaInicio() == null) {
            mostrarMensaje("Error", "Debe ingresar la fecha inicio.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar la fecha inicio");
            return;
        }
        if (ausentismo.getFechaFin() == null) {
            mostrarMensaje("Error", "Debe ingresar la fecha fin.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe ingresar la fecha fin");
            return;
        }
        if (ausentismo.getReason() == null || ausentismo.getReason().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el motivo del ausentismo.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el motivo del ausentismo");
            return;
        }
        if (ausentismo.getConfirmerNumber() == null || ausentismo.getConfirmerNumber() == 0) {
            mostrarMensaje("Error", "Debe seleccionar la persona que aprueba el ausentismo.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar la persona que aprueba el ausentismo");
            return;
        }
        if (ausentismo.getType() == null || ausentismo.getType().isEmpty()) {
            mostrarMensaje("Error", "Seleccione el tipo de ausentismo.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Seleccione el tipo de ausentismo");
            return;
        }

        int diferencia = (int) ((ausentismo.getFechaFin().getTime() - ausentismo.getFechaInicio().getTime()) / 1000);

        if (diferencia > 86400) {
            ausentismo.setDias(String.valueOf(Math.floor(diferencia / 86400)).replace(".0", ""));
            diferencia = diferencia - ((int) (Double.parseDouble(ausentismo.getDias()) * 86400));
        }
        if (diferencia > 3600) {
            ausentismo.setHoras(String.valueOf(Math.floor(diferencia / 3600)).replace(".0", ""));
            diferencia = diferencia - ((int) (Double.parseDouble(ausentismo.getHoras()) * 3600));
        }
        if (diferencia > 60) {
            ausentismo.setMinutos(String.valueOf(Math.floor(diferencia / 60)).replace(".0", ""));
            diferencia = diferencia - ((int) (Double.parseDouble(ausentismo.getMinutos()) * 60));
        }

        if (empleado.getEmployeeAbsenceInfoLines() == null) {
            empleado.setEmployeeAbsenceInfoLines(new EmployeeInfo.EmployeeAbsenceInfoLines());
        }
        empleado.getEmployeeAbsenceInfoLines().getEmployeeAbsenceInfo().add(0, ausentismo);
        obtenerAusentismos();
        ausentismo = new EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo();
    }

    public void obtenerFormacion() {
        empleado.getEmployeeEducationInfoLines().setEstudiosVisibles(new ArrayList<EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo>());

        long nroRegistros = empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().size();

        totalPaginasModals = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPaginaModals) + (nroRegistros % datosPaginaModals > 0 ? 1 : 0);
        if (paginaModals == 0) {
            paginaModals = 1;
        } else if (paginaModals > totalPaginasModals) {
            paginaModals = totalPaginasModals;
        }
        construirListaPaginasModals();

        if (empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo() != null && !empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().isEmpty()) {
            for (int i = (((paginaModals * 1) - 1) * datosPaginaModals); i < (datosPaginaModals * paginaModals); i++) {
                try {
                    empleado.getEmployeeEducationInfoLines().getEstudiosVisibles().add(empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void agregarFormacion() {
        if (formacion.getEducationType() == null || formacion.getEducationType() == 0) {
            mostrarMensaje("Error", "Debe seleccionar el tipo de formación.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe seleccionar el tipo de formacion");
            return;
        }

        if (empleado.getEmployeeEducationInfoLines() == null) {
            empleado.setEmployeeEducationInfoLines(new EmployeeInfo.EmployeeEducationInfoLines());
        }
        empleado.getEmployeeEducationInfoLines().getEmployeeEducationInfo().add(0, formacion);
        obtenerFormacion();
        formacion = new EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo();
    }

    public void obtenerValoracion() {
        empleado.getEmployeeReviewsInfoLines().setValoracionesVisibles(new ArrayList<EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo>());

        long nroRegistros = empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo().size();

        totalPaginasModals = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPaginaModals) + (nroRegistros % datosPaginaModals > 0 ? 1 : 0);
        if (paginaModals == 0) {
            paginaModals = 1;
        } else if (paginaModals > totalPaginasModals) {
            paginaModals = totalPaginasModals;
        }
        construirListaPaginasModals();

        if (empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo() != null && !empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo().isEmpty()) {
            for (int i = (((paginaModals * 1) - 1) * datosPaginaModals); i < (datosPaginaModals * paginaModals); i++) {
                try {
                    empleado.getEmployeeReviewsInfoLines().getValoracionesVisibles().add(empleado.getEmployeeReviewsInfoLines().getEmployeeReviewsInfo().get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void obtenerEmpleos() {
        empleado.getEmployeePreviousEmpoymentInfoLines().setEmpleadoAnterioresVisibles(new ArrayList<EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo>());

        long nroRegistros = empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().size();

        totalPaginasModals = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPaginaModals) + (nroRegistros % datosPaginaModals > 0 ? 1 : 0);
        if (paginaModals == 0) {
            paginaModals = 1;
        } else if (paginaModals > totalPaginasModals) {
            paginaModals = totalPaginasModals;
        }
        construirListaPaginasModals();

        if (empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo() != null && !empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().isEmpty()) {
            for (int i = (((paginaModals * 1) - 1) * datosPaginaModals); i < (datosPaginaModals * paginaModals); i++) {
                try {
                    empleado.getEmployeePreviousEmpoymentInfoLines().getEmpleadoAnterioresVisibles().add(empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void agregarEmpleo() {
        if (empleo.getEmployer() == null || empleo.getEmployer().isEmpty()) {
            mostrarMensaje("Error", "Ingrese el empresario.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese el empresario");
            return;
        }
        if (empleo.getPosition() == null || empleo.getPosition().isEmpty()) {
            mostrarMensaje("Error", "Ingrese la posición.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese la posicion");
            return;
        }

        if (empleado.getEmployeePreviousEmpoymentInfoLines() == null) {
            empleado.setEmployeePreviousEmpoymentInfoLines(new EmployeeInfo.EmployeePreviousEmpoymentInfoLines());
        }
        empleado.getEmployeePreviousEmpoymentInfoLines().getEmployeePreviousEmpoymentInfo().add(0, empleo);
        obtenerEmpleos();
        empleo = new EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo();
    }

    public String mostrarNombreTipoAusentismo(String utype) {
        if (utype != null && !utype.isEmpty()) {
            for (ValoresUsuarioDTO v : tiposAusentismo) {
                if (v.getFldValue().equals(utype)) {
                    return v.getDescr();
                }
            }
        }

        return "";
    }

    public String mostrarNombreClaseFormacion(Integer edType) {
        if (edType != null && edType != 0) {
            for (ClaseFormacionDTO c : clasesFormacion) {
                if (c.getEdType().equals(edType)) {
                    return c.getName();
                }
            }
        }

        return "";
    }

    private void obtenerCustodiasEmpleado() {
        custodiasEmpleado = new ArrayList<>();

        List<DetalleCustodia> custodies = detalleCustodiaFacade.obtenerCustodiasEmpleado(empleado.getOfficeExtension());

        if (custodies != null && !custodies.isEmpty()) {
            for (DetalleCustodia c : custodies) {
                DetalleCustodiaDTO dto = new DetalleCustodiaDTO();

                dto.setActivo(c.getActivo());
                dto.setCantidad(c.getCantidad());
                dto.setCantidadBaja(c.getCantidadBaja());
                dto.setCedula(c.getCedula());
                dto.setComentario(c.getComentario());
                dto.setContrasenaCustodia(c.getContrasenaCustodia());
                dto.setFechaCambio(c.getFechaCambio());
                dto.setFechaEntrega(c.getFechaEntrega());
                dto.setIdCustodia(new CustodiaDTO(0, c.getIdCustodia().getIdCustodia(), c.getIdCustodia().getNombreCustodia(), c.getIdCustodia().getImagen(),
                        null, c.getIdCustodia().getRequiereContrasena(), null, c.getIdCustodia().getRequiereActa()));
                dto.setIdDetalleCustodia(c.getIdDetalleCustodia());
                dto.setUsuarioEntrega(c.getUsuarioEntrega());
                dto.setComponentes(new ArrayList<ComponenteCustodiaEmpleadoDTO>());

                List<ComponenteCustodiaEmpleado> comp = componenteCustodiaEmpleadoFacade.obtenerComponentesCustodiaEmpleado(c.getIdDetalleCustodia());

                if (comp != null && !comp.isEmpty()) {
                    for (ComponenteCustodiaEmpleado e : comp) {
                        ComponenteCustodiaEmpleadoDTO cdto = new ComponenteCustodiaEmpleadoDTO();

                        cdto.setCantidad(e.getCantidad());
                        cdto.setCantidadBaja(e.getCantidadBaja());
                        cdto.setIdComponente(new ComponenteCustodiaDTO(e.getIdComponente().getIdComponenteCustodia(), e.getIdComponente().getNombreComponente(), false, null));
                        cdto.setIdDetalleComponente(e.getIdDetalleComponente());
                        cdto.setIdDetalleCustodia(null);
                        cdto.setComentario(e.getComentario());

                        dto.getComponentes().add(cdto);
                    }
                }

                custodiasEmpleado.add(dto);
            }

            gestionarCustodiasEmpleado();
        }
    }

    public void gestionarCustodiasEmpleado() {
        custodiasEmpleadoVisibles = new ArrayList<>();

        long nroRegistros = custodiasEmpleado.size();

        totalPaginasCustodias = (Integer.parseInt(String.valueOf(nroRegistros)) / datosPaginaCustodias) + (nroRegistros % datosPaginaCustodias > 0 ? 1 : 0);
        if (paginaCustodias == 0) {
            paginaCustodias = 1;
        } else if (paginaCustodias > totalPaginasCustodias) {
            paginaCustodias = totalPaginasCustodias;
        }
        construirListaPaginasCustodias();

        if (custodiasEmpleado != null && !custodiasEmpleado.isEmpty()) {
            for (int i = (((paginaCustodias * 1) - 1) * datosPaginaCustodias); i < (datosPaginaCustodias * paginaCustodias); i++) {
                try {
                    custodiasEmpleadoVisibles.add(custodiasEmpleado.get(i));
                } catch (Exception e) {
                    break;
                }
            }
        }
    }

    public void editarCustodia() {
        idCustodia = null;
        idDetalleCustodia = null;
        componentesCustodiaEmpleado = new ArrayList<>();
        dlgComponenteCustodia = false;
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDetalleCustodia"));

        for (DetalleCustodiaDTO d : custodiasEmpleado) {
            if (d.getIdDetalleCustodia().equals(id)) {
                componentesCustodiaEmpleado = d.getComponentes();
                dlgComponenteCustodia = true;
                idCustodia = d.getIdCustodia().getIdCustodia();
                idDetalleCustodia = d.getIdDetalleCustodia();
                cargarComponentesCustodia();
                break;
            }
        }
    }

    public void seleccionarCustodiaRecibir() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDetalleCustodia"));

        for (DetalleCustodiaDTO d : custodiasEmpleado) {
            if (d.getIdDetalleCustodia().equals(id)) {
                if (d.isSeleccionado()) {
                    d.setSeleccionado(false);
                } else {
                    d.setSeleccionado(true);
                }
            }
        }
    }

    public void recibirCustodias() {
        for (DetalleCustodiaDTO d : custodiasEmpleado) {
            if (d.isSeleccionado()) {
                d.setSeleccionado(false);
                d.setActivo(false);

                DetalleCustodia det = detalleCustodiaFacade.find(d.getIdDetalleCustodia());

                det.setActivo(false);
                det.setCantidadBaja(det.getCantidad());
                det.setFechaCambio(new Date());

                try {
                    detalleCustodiaFacade.edit(det);
                } catch (Exception e) {
                }
            }
        }
        recibirCustodia();
    }

    public void seleccionarCustodia() {
        dlgComponenteCustodia = false;
        idCustodia = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idCustodia"));

        for (CustodiaDTO c : custodias) {
            if (c.getIdCustodia().equals(idCustodia)) {
                cargarComponentesCustodia();
                break;
            }
        }

        if (componentesCustodias.size() > 0) {
            idComponenteCustodia = null;
            cantidadComponente = null;
            componentesCustodiaEmpleado = new ArrayList<>();
            dlgComponenteCustodia = true;
        } else {
            agregarCustodia();
        }
    }

    private byte[] fileToBytes(File file, String nombreArchivo) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[(int) file.length()];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum);
            }
        } catch (IOException ex) {
            CONSOLE.log(Level.SEVERE, ex.getMessage());
        }
        byte[] bytes = bos.toByteArray();
        File someFile = new File(nombreArchivo);
        try (FileOutputStream fos = new FileOutputStream(someFile)) {
            fos.write(bytes);
            fos.flush();
        }
        return buf;
    }

    public String recibirCustodia() {
        String url = abrirPdf("recibir");
        if (url != null) {
            return url;
        } else {
            return null;
        }
    }

    public String adquirirCustodia() {
        String url = abrirPdf("adquirir");
        if (url != null) {
            return url;
        } else {
            return null;
        }
    }

    public String abrirPdf(String alias) {
        if (empleado != null && empleado.getOfficeExtension() != null && !empleado.getOfficeExtension().isEmpty()) {
            List<String[]> adjunto = generarDocumento(Integer.parseInt(empleado.getOfficeExtension()), 1, "custodias", null, alias, false, null, null, null);

            if (adjunto != null && !adjunto.isEmpty()) {
                if (new File(adjunto.get(0)[0]).exists()) {
                    try {
                        String url = applicationMBean.obtenerValorPropiedad("url.web.custodias");
                        return "openRuta('" + url + alias + "[" + empleado.getOfficeExtension() + "].pdf');";
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
            CONSOLE.log(Level.SEVERE, "No encontro datos para generar documento");
            return "";
        }
    }

    private List<String[]> generarDocumento(Integer id, Integer copias, String documento, String sucursal, String alias, boolean imprimir, List<String[]> documentosRelacionados, String inicio, String fin) {
        PrintReportDTO print = new PrintReportDTO();

        print.setAlias(alias);
        print.setCopias(copias);
        print.setDocumento(documento);
        print.setId(id);
        print.setImprimir(imprimir);
        print.setSucursal(sucursal);
        print.setDocumentosRelacionados(documentosRelacionados);
        print.setInicio(inicio);
        print.setFin(fin);

        PrintRepostClient client = new PrintRepostClient(applicationMBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            GenericRESTResponseDTO res = client.generarDocumento(print);

            List<String[]> adjuntos = new ArrayList<>();

            for (String s : res.getMensaje().split("||")) {
                File f = new File(s);

                if (f.exists()) {
                    adjuntos.add(new String[]{s, f.getName()});
                }
            }

            return adjuntos;
        } catch (Exception e) {
            CONSOLE.log(Level.SEVERE, "Ocurrio un error al generar el documento para {0}. Error {1}", new Object[]{documento.toUpperCase(), e.getMessage()});
            mostrarMensaje("Error", "Ocurrió un error al generar el documento.", true, false, false);
            return null;
        }
    }

    private void cargarComponentesCustodia() {
        componentesCustodias = new ArrayList<>();
        List<ComponenteCustodia> components = componenteCustodiaFacade.obtenerComponentesCustodia(idCustodia);

        if (components != null && !components.isEmpty()) {
            for (ComponenteCustodia i : components) {
                ComponenteCustodiaDTO dto = new ComponenteCustodiaDTO();

                dto.setIdComponenteCustodia(i.getIdComponenteCustodia());
                dto.setIdCustodia(new CustodiaDTO(0, idCustodia, null, null, null, null, null, null));
                dto.setNombreComponente(i.getNombreComponente());
                dto.setPrincipal(i.getPrincipal());

                componentesCustodias.add(dto);
            }
        }
    }

    public void agregarComponenteCustodia() {
        for (ComponenteCustodiaDTO c : componentesCustodias) {
            if (c.getIdComponenteCustodia().equals(idComponenteCustodia)) {
                if (!c.isPrincipal()) {
                    boolean existe = false;
                    if (!componentesCustodiaEmpleado.isEmpty()) {
                        for (ComponenteCustodiaEmpleadoDTO e : componentesCustodiaEmpleado) {
                            if (e.getIdComponente().getIdComponenteCustodia().equals(idComponenteCustodia)) {
                                existe = true;
                                break;
                            }
                        }
                    }

                    if (!existe) {
                        componentesCustodiaEmpleado.add(new ComponenteCustodiaEmpleadoDTO(null, (cantidadComponente == null || cantidadComponente == 0) ? 1 : cantidadComponente, 0, null,
                                new ComponenteCustodiaDTO(c.getIdComponenteCustodia(), c.getNombreComponente(), false, null), null));
                    }
                }
            }
        }
        cantidadComponente = null;
    }

    public void eliminarComponenteCustodia() {
        Integer id = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idComponenteCustodia"));

        for (ComponenteCustodiaEmpleadoDTO c : componentesCustodiaEmpleado) {
            if (c.getIdDetalleComponente().equals(id)) {
                componentesCustodiaEmpleado.remove(c);
                break;
            }
        }
    }

    public void agregarCustodia() {
        if (componentesCustodias != null && !componentesCustodias.isEmpty() && (componentesCustodiaEmpleado == null || componentesCustodiaEmpleado.isEmpty())) {
            mostrarMensaje("Error", "Debe incluir al menos un componente para agregar esta custodia.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Debe incluir al menos un componente para agregar esta custodia.");
            return;
        }

        if (idDetalleCustodia != null && idDetalleCustodia != 0) {
            for (DetalleCustodiaDTO d : custodiasEmpleado) {
                if (d.getIdDetalleCustodia().equals(idDetalleCustodia)) {
                    d.setComponentes(componentesCustodiaEmpleado);
                    break;
                }
            }
        } else {
            for (CustodiaDTO c : custodias) {
                if (c.getIdCustodia().equals(idCustodia)) {
                    custodiasEmpleado.add(0, new DetalleCustodiaDTO(null, 1, null, empleado.getOfficeExtension(), null, null, sessionMBean.getCodigoVentas(), true, new Date(), null, c, componentesCustodiaEmpleado));
                    break;
                }
            }
        }

        idDetalleCustodia = null;
        dlgComponenteCustodia = false;
        componentesCustodiaEmpleado = new ArrayList<>();
        componentesCustodias = new ArrayList<>();
        gestionarCustodiasEmpleado();
    }

    public void seleccionarEliminarCustodia() {
        idDetalleCustodia = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("idDetalleCustodia"));
        dlgEliminarCustodia = true;
    }

    public void eliminarCustodia() {
        if (idDetalleCustodia != null && idDetalleCustodia != 0) {
            for (DetalleCustodiaDTO d : custodiasEmpleado) {
                if (d.getIdDetalleCustodia().equals(idDetalleCustodia)) {
                    custodiasEmpleado.remove(d);
                    idDetalleCustodia = null;
                    gestionarCustodiasEmpleado();
                    break;
                }
            }
        }
    }

    private void construirListaPaginasModals() {
        paginasModals = new ArrayList<>();
        for (int i = 1; i <= totalPaginasModals; i++) {
            paginasModals.add(i);
        }
    }

    public void mostrarPaginaSiguienteModals() {
        int tipo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo"));
        if (paginaModals < totalPaginasModals) {
            paginaModals++;
            procesarPaginaModals(tipo);
        }
    }

    public void mostrarPaginaAnteriorModals() {
        int tipo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo"));
        if (paginaModals > 1) {
            paginaModals--;
            procesarPaginaModals(tipo);
        }
    }

    public void mostrarPaginaEspecificaModals() {
        int tipo = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipo"));
        paginaModals = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        procesarPaginaModals(tipo);
    }

    private void construirListaPaginasCustodias() {
        paginasCustodias = new ArrayList<>();
        for (int i = 1; i <= totalPaginasCustodias; i++) {
            paginasCustodias.add(i);
        }
    }

    public void mostrarPaginaSiguienteCustodias() {
        if (paginaCustodias < totalPaginasCustodias) {
            paginaCustodias++;
            gestionarCustodiasEmpleado();
        }
    }

    public void mostrarPaginaAnteriorCustodias() {
        if (paginaCustodias > 1) {
            paginaCustodias--;
            gestionarCustodiasEmpleado();
        }
    }

    public void mostrarPaginaEspecificaCustodias() {
        paginaCustodias = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        gestionarCustodiasEmpleado();
    }

    private void procesarPaginaModals(int tipo) {
        switch (tipo) {
            case 1:
                obtenerAusentismos();
                break;
            case 2:
                obtenerFormacion();
                break;
            case 3:
                obtenerValoracion();
                break;
            case 4:
                obtenerEmpleos();
                break;
            default:
                break;
        }
    }

    public void limpir() {
        paginaModals = 1;
        paginaCustodias = 1;
        totalPaginasModals = 1;
        totalPaginasCustodias = 1;
        idCustodia = null;
        idComponenteCustodia = null;
        idDetalleCustodia = null;
        cantidadComponente = null;
        dlgBusquedaEmpleado = false;
        dlgComponenteCustodia = false;
        dlgEliminarCustodia = false;
        empleado = new EmployeeInfo();
        paginasModals = new ArrayList<>();
        paginasCustodias = new ArrayList<>();
        custodiasEmpleado = new ArrayList<>();
        custodiasEmpleadoVisibles = new ArrayList<>();
        componentesCustodias = new ArrayList<>();
        componentesCustodiaEmpleado = new ArrayList<>();
        empleados = new ArrayList<>();
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
