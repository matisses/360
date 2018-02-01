package co.matisses.web.mbean.directorio;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.web.entity.LineaSucursal;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.web.facade.LineaSucursalFacade;
import co.matisses.web.dto.DirectorioDTO;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;
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
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;

/**
 *
 * @author jguisao
 */
@ViewScoped
@Named(value = "directorioMBean")
public class DirectorioMBean implements Serializable {

    @Inject
    private UserSessionInfoMBean sessionMBean;
    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Logger log = Logger.getLogger(DirectorioMBean.class.getSimpleName());
    private int totalPaginas;
    private int datosPagina;
    private int pagina;
    private int idLinea;
    private String filtro;
    private String busqueda;
    private String orderBy;
    private List<Integer> paginas;
    private List<String> filtros;
    private List<DirectorioDTO> directorioFiltrado;
    private List<DirectorioDTO> directorio;
    private List<DirectorioDTO> directorioFull;
    private List<LineaSucursalFacade> lineaSucursal;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private LineaSucursalFacade lineaSucursalFacade;

    public DirectorioMBean() {
        totalPaginas = 0;
        datosPagina = 12;
        pagina = 1;
        filtros = new ArrayList<>();
        directorioFiltrado = new ArrayList<>();
        directorio = new ArrayList<>();
        directorioFull = new ArrayList<>();
        paginas = new ArrayList<>();
        lineaSucursal = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        obtenerDatosDirectorioActivo();
    }

    public UserSessionInfoMBean getSessionMBean() {
        return sessionMBean;
    }

    public void setSessionMBean(UserSessionInfoMBean sessionMBean) {
        this.sessionMBean = sessionMBean;
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public String getFiltro() {
        return filtro;
    }

    public void setFiltro(String filtro) {
        this.filtro = filtro;
    }

    public String getBusqueda() {
        return busqueda;
    }

    public void setBusqueda(String busqueda) {
        this.busqueda = busqueda;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public int getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(int totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getDatosPagina() {
        return datosPagina;
    }

    public void setDatosPagina(int datosPagina) {
        this.datosPagina = datosPagina;
    }

    public int getPagina() {
        return pagina;
    }

    public void setPagina(int pagina) {
        this.pagina = pagina;
    }

    public List<Integer> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Integer> paginas) {
        this.paginas = paginas;
    }

    public String getFiltroSeleccionado() {
        if (filtro != null && !filtro.isEmpty() && !filtro.equals("todos")) {
            for (String s : filtros) {
                if (s.equals(filtro)) {
                    return s;
                }
            }
        } else if (filtro != null && !filtro.isEmpty() && filtro.equals("todos")) {
            return "Todos";
        }
        return "Seleccione";
    }

    public List<String> getFiltros() {
        return filtros;
    }

    public void setFiltros(List<String> filtros) {
        this.filtros = filtros;
    }

    public List<DirectorioDTO> getDirectorio() {
        return directorio;
    }

    public void setDirectorio(List<DirectorioDTO> directorio) {
        this.directorio = directorio;
    }

    public List<DirectorioDTO> getDirectorioFull() {
        return directorioFull;
    }

    public void setDirectorioFull(List<DirectorioDTO> directorioFull) {
        this.directorioFull = directorioFull;
    }

    public String getTamanoPagina() {
        if (datosPagina != 100000) {
            return Integer.toString(datosPagina);
        } else {
            return "todos";
        }
    }

    public List<LineaSucursalFacade> getLineaSucursal() {
        return lineaSucursal;
    }

    public void setLineaSucursal(List<LineaSucursalFacade> lineaSucursal) {
        this.lineaSucursal = lineaSucursal;
    }

    public List<DirectorioDTO> getDirectorioFiltrado() {
        return directorioFiltrado;
    }

    public void setDirectorioFiltrado(List<DirectorioDTO> directorioFiltrado) {
        this.directorioFiltrado = directorioFiltrado;
    }

    public int getIdLinea() {
        return idLinea;
    }

    public void setIdLinea(int idLinea) {
        this.idLinea = idLinea;
    }

    private void obtenerDatosBD() {
        lineaSucursal = new ArrayList<>();
        List<LineaSucursal> lineas = lineaSucursalFacade.obtenerLineaSucursal(sessionMBean.getAlmacen());

        for (DirectorioDTO d : directorioFull) {
            if (lineas != null && !lineas.isEmpty()) {
                for (LineaSucursal l : lineas) {
                    if (l.getDestino().equals(d.getSucursal())) {
                        if (l.getLinea() != null && !l.getLinea().isEmpty() && d.getExtension() != null && !d.getExtension().isEmpty()) {
                            d.setExtension(l.getLinea() + " Ext. " + d.getExtension());
                        } else if (d.getExtension() != null && !d.getExtension().isEmpty()) {
                            d.setExtension("Ext. " + d.getExtension());
                        } else if (l.getLinea() != null && !l.getLinea().isEmpty()) {
                            d.setExtension(l.getLinea());
                        }
                        break;
                    }
                }
            }
        }
    }

    private void obtenerDatosDirectorioActivo() {
        directorio = new ArrayList<>();
        Hashtable<String, Object> auth = new Hashtable<>();

        auth.put(Context.INITIAL_CONTEXT_FACTORY, applicationMBean.obtenerValorPropiedad("initial.context.factory"));
        auth.put(Context.PROVIDER_URL, applicationMBean.obtenerValorPropiedad("provider.url"));
        auth.put(Context.SECURITY_AUTHENTICATION, applicationMBean.obtenerValorPropiedad("security.authentication"));
        auth.put(Context.SECURITY_PRINCIPAL, applicationMBean.obtenerValorPropiedad("security.principal.domain") + applicationMBean.obtenerValorPropiedad("ldap.query.user"));
        auth.put(Context.SECURITY_CREDENTIALS, applicationMBean.obtenerValorPropiedad("ldap.query.password"));

        String usersContainer = "OU=Mail_matisses,DC=baru,DC=local";
        try {
            LdapContext ctx = new InitialLdapContext(auth, null);

            DirContext ctx1 = new InitialDirContext(auth);
            SearchControls ctls = new SearchControls();
            String[] attrIDs = {"description", "mobile", "mail", "title", "pager", "samaccountname", "department", "facsimileTelephoneNumber", "objectClass", "homePhone"};

            ctls.setReturningAttributes(attrIDs);
            ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            NamingEnumeration answer = ctx1.search(usersContainer, "(&(objectClass=user)(!(userAccountControl:1.2.840.113556.1.4.803:=2)))", ctls);

            while (answer.hasMore()) {
                DirectorioDTO dir = new DirectorioDTO();
                SearchResult rslt = (SearchResult) answer.next();
                Attributes attrs = rslt.getAttributes();
                Attribute mobile = attrs.get("mobile");
                Attribute extension = attrs.get("pager");
                Attribute cargo = attrs.get("title");
                Attribute correo = attrs.get("mail");
                Attribute nombre = attrs.get("description");
                Attribute departamento = attrs.get("department");
                Attribute info = attrs.get("facsimileTelephoneNumber");
                Attribute object = attrs.get("objectClass");
                Attribute homePhone = attrs.get("homePhone");

                if (!object.toString().contains("group") && nombre != null && departamento != null && !departamento.toString().isEmpty()) {
                    dir.setCargo(cargo != null ? cargo.toString().replace("title: ", "") : "");
                    dir.setCelular(mobile != null ? mobile.toString().replace("mobile: ", "") : "");
                    dir.setCorreo(correo != null ? correo.toString().replace("mail: ", "") : "");
                    dir.setExtension(extension != null ? extension.toString().replace("pager: ", "") : "");
                    dir.setNombre(nombre.toString().replace("description: ", ""));
                    dir.setDepartamento(departamento.toString().replace("department: ", ""));
                    dir.setSkype(info != null ? info.toString().replace("facsimileTelephoneNumber: ", "") : "");
                    dir.setEmpleado(obtenerDatosEmpleado(dir.getCorreo()));
                    dir.setSucursal(homePhone != null ? homePhone.toString().replace("homePhone: ", "") : "");

                    ctx1.close();
                    directorioFull.add(dir);
                }
            }

            for (DirectorioDTO d : directorioFull) {
                if (d.getDepartamento() != null && !d.getDepartamento().isEmpty()) {
                    if (filtros == null || filtros.isEmpty()) {
                        filtros.add(d.getDepartamento());
                    } else {
                        boolean existe = false;
                        for (String f : filtros) {
                            if (f.equals(d.getDepartamento())) {
                                existe = true;
                                break;
                            }
                        }
                        if (!existe) {
                            filtros.add(d.getDepartamento());
                        }
                    }
                }
            }
            Collections.sort(filtros);
            Collections.sort(directorioFull, new Comparator<DirectorioDTO>() {
                @Override
                public int compare(DirectorioDTO o1, DirectorioDTO o2) {
                    return o1.getNombre().compareTo(o2.getNombre());
                }
            });
            obtenerDatosBD();
            directorioFiltrado = new ArrayList<>(directorioFull);
            obtenerDirectorio();
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
    }

    private EmpleadoDTO obtenerDatosEmpleado(String correo) {
        Empleado emp = empleadoFacade.obtenerEmpleadoCorreo(correo);

        if (emp != null && emp.getEmpID() != null && emp.getEmpID() != 0) {
            EmpleadoDTO e = new EmpleadoDTO();
            e.setCedula(emp.getOfficeExt());
            return e;
        }
        return null;
    }

    public void seleccionarFiltro() {
        filtro = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filtro");
        log.log(Level.INFO, "Se selecciono el filtro [{0}]", filtro);

        filtrarFiltros();
    }

    private void filtrarFiltros() {
        pagina = 1;
        directorio = new ArrayList<>();
        directorioFiltrado.clear();
        if (filtro != null && !filtro.isEmpty()) {
            for (DirectorioDTO d : directorioFull) {
                if (d.getDepartamento().equals(filtro) || filtro.equals("todos")) {
                    if (busqueda != null && !busqueda.isEmpty()) {
                        if (d.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        } else if (d.getCargo().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        } else if (d.getCelular().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        } else if (d.getCorreo().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        } else if (d.getSkype().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        } else if (d.getExtension().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(d);
                        }
                    } else if ((busqueda == null || busqueda.isEmpty())) {
                        directorioFiltrado.add(d);
                    }
                }
            }
        } else {
            directorioFiltrado = new ArrayList<>(directorioFull);
        }
        obtenerDirectorio();
    }

    public void aplicarBusqueda() {
        if (busqueda != null && !busqueda.isEmpty()) {
            pagina = 1;
            directorio.clear();
            directorioFiltrado.clear();
            for (DirectorioDTO dir : directorioFull) {
                if (filtro != null && !filtro.isEmpty() && !filtro.equals("todos")) {
                    if (dir.getDepartamento().toUpperCase().contains(filtro.toUpperCase())) {
                        if (dir.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        } else if (dir.getCargo().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        } else if (dir.getCelular().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        } else if (dir.getCorreo().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        } else if (dir.getSkype().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        } else if (dir.getExtension().toUpperCase().contains(busqueda.toUpperCase())) {
                            directorioFiltrado.add(dir);
                        }
                    }
                } else if (dir.getCargo().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                } else if (dir.getCorreo().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                } else if (dir.getExtension().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                } else if (dir.getCelular().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                } else if (dir.getNombre().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                } else if (dir.getSkype().toUpperCase().contains(busqueda.toUpperCase())) {
                    directorioFiltrado.add(dir);
                }
            }
            obtenerDirectorio();
        } else if (filtro != null && !filtro.isEmpty()) {
            filtrarFiltros();
        } else {
            directorioFiltrado = new ArrayList<>(directorioFull);
            obtenerDirectorio();
        }
    }

    private void obtenerDirectorio() {
        directorio.clear();
        int totalRegistros = directorioFiltrado.size();

        totalPaginas = (totalRegistros / datosPagina) + (totalRegistros % datosPagina > 0 ? 1 : 0);

        if (pagina > totalPaginas && pagina != 1) {
            pagina = totalPaginas;
        }
        construirListaPaginas();

        int datosMostrar;
        if (directorioFiltrado.size() - (datosPagina * (pagina - 1)) >= datosPagina) {
            datosMostrar = datosPagina;
        } else {
            datosMostrar = directorioFiltrado.size() - (datosPagina * (pagina - 1));
        }
        for (int i = (datosPagina * (pagina - 1)); i < ((datosPagina * (pagina - 1)) + datosMostrar); i++) {
            directorio.add(directorioFiltrado.get(i));
        }
        log.log(Level.INFO, "Cantidad de datos de la lista de directorio: [{0}]", directorioFiltrado.size());
    }

    private void construirListaPaginas() {
        paginas = new ArrayList<>();
        for (int i = 1; i <= totalPaginas; i++) {
            paginas.add(i);
        }
    }

    public void ordenarDirectorio() {
        orderBy = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("orderBy");
        log.log(Level.INFO, "Ordenando por [{0}]", orderBy);
        obtenerDirectorio();
    }

    public void mostrarPrimeraPagina() {
        pagina = 1;
        obtenerDirectorio();
    }

    public void mostrarUltimaPagina() {
        pagina = totalPaginas;
        obtenerDirectorio();
    }

    public void mostrarPaginaSiguiente() {
        if (pagina < totalPaginas) {
            pagina++;
            obtenerDirectorio();
        }
    }

    public void mostrarPaginaAnterior() {
        if (pagina > 1) {
            pagina--;
            obtenerDirectorio();
        }
    }

    public void mostrarPaginaEspecifica() {
        pagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("pagina"));
        obtenerDirectorio();
    }

    public void cambiarTamanoPagina() {
        datosPagina = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tamanoPagina"));
        log.log(Level.INFO, "Cambiando # de productos x pagina a [{0}]", datosPagina);
        pagina = 1;
        obtenerDirectorio();
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
