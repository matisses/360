package co.matisses.web.rest;

import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.web.dto.EmpleadoDTO;
import co.matisses.web.dto.SucursalDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("empleado")
public class EmpleadoFacadeREST extends AbstractFacade<Empleado> {

    private static final Logger log = Logger.getLogger(EmpleadoFacadeREST.class.getSimpleName());

    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;

    public EmpleadoFacadeREST() {
        super(Empleado.class);
    }

    @GET
    @Path("list")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listEmployeesByBranch() {
        log.log(Level.INFO, "Listando los empleados de ventas agrupados por sucursal");
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(b.Code as varchar(8)) codigoTienda, cast(b.Remarks as varchar(40)) nombreTienda, cast(e.officeExt as varchar(20)) cedula ");
        sb.append(", cast(concat(e.firstName, ' ', e.middleName) as varchar(100)) nombre ");
        sb.append(", cast(e.empID as varchar(4)) codAsesor ");
        sb.append(", cast(rtrim(concat(e.lastName, ' ', e.firstName, ' ', e.middleName)) as varchar(100)) as nombreCompleto ");
        sb.append("from OHEM e inner join OUBR b on b.Code = e.branch ");
        sb.append("inner join OHPS pos on pos.posID = e.position and pos.posID in (7,75) where e.Active = 'Y' ");
        //TODO: parametrizar codigos de cargos que aplican
        try {
            List<SucursalDTO> sucursales = new ArrayList<>();
            List<Object[]> rows = em.createNativeQuery(sb.toString()).getResultList();
            for (Object[] row : rows) {
                String codSucursal = (String) row[0];
                String nomSucursal = (String) row[1];
                String cedula = (String) row[2];
                String nombre = (String) row[3];
                String codAsesor = (String) row[4];
                String nombreCompleto = (String) row[5];

                EmpleadoDTO emp = new EmpleadoDTO();
                emp.setCedula(cedula);
                emp.setCodigoAsesor(codAsesor);
                emp.setNombre(nombre);
                emp.setNombreCompleto(nombreCompleto);

                SucursalDTO sucursal = new SucursalDTO(codSucursal, nomSucursal);
                int pos = sucursales.indexOf(sucursal);
                if (pos >= 0) {
                    sucursales.get(pos).addEmpleado(emp);
                } else {
                    sucursal.addEmpleado(emp);
                    sucursales.add(sucursal);
                }
            }
            return Response.ok(sucursales).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar los empleados por sucursal. ", e);
            return Response.ok().build();
        }
    }

    @GET
    @Path("list/{branchCode}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response listBranchEmployees(@PathParam("branchCode") String branchCode) {
        log.log(Level.INFO, "Consultando los asesores complementos del almacen {0}", branchCode);
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(e.officeExt as varchar(20)) cedula, ");
        sb.append("cast(concat(e.firstName, ' ', e.middleName) as varchar(100)) nombre, ");
        sb.append("cast(e.empID as varchar(4)) codAsesor, ");
        sb.append("cast(rtrim(concat(e.lastName, ' ', e.firstName, ' ', e.middleName)) as varchar(100)) as nombreCompleto ");
        sb.append("from OHEM e ");
        sb.append("inner join OUBR b on b.Code = e.branch ");
        sb.append("where b.Name = '");
        sb.append(branchCode);
        sb.append("' and e.Active = 'Y' ");
        sb.append("order by 2 asc ");

        try {
            List<EmpleadoDTO> employees = new ArrayList<>();
            List<Object[]> rows = em.createNativeQuery(sb.toString()).getResultList();
            for (Object[] cols : rows) {
                String cedula = (String) cols[0];
                String nombre = (String) cols[1];
                String codAsesor = (String) cols[2];
                String nombreCompleto = (String) cols[3];

                EmpleadoDTO emp = new EmpleadoDTO();
                emp.setCedula(cedula);
                emp.setCodigoAsesor(codAsesor);
                emp.setNombre(nombre);
                emp.setNombreCompleto(nombreCompleto);

                employees.add(emp);
            }
            return Response.ok(employees).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar los empleados de la sucursal [" + branchCode + "]", e);
            return Response.ok().build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
