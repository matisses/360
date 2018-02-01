package co.matisses.web.rest;

import co.matisses.persistence.web.entity.ClienteWeb;
import co.matisses.persistence.web.entity.DireccionClienteWeb;
import co.matisses.web.dto.ClienteSAPDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("clienteweb")
public class ClienteWebFacadeREST extends AbstractFacade<ClienteWeb> {
    private static final Logger log = Logger.getLogger(ClienteWebFacadeREST.class.getSimpleName());

    @PersistenceContext(unitName = "BARUWEBPU")
    private EntityManager em;

    public ClienteWebFacadeREST() {
        super(ClienteWeb.class);
    }

    @GET
    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    public Response create(ClienteSAPDTO dto) {
        List<DireccionClienteWeb> direcciones = new ArrayList<>();
        
        DireccionClienteWeb direccionFactura = new DireccionClienteWeb();
        direccionFactura.setCelular(dto.getCelular());
        direccionFactura.setCodCiudad(dto.getCodCiudad());
        direccionFactura.setCodDepartamento(dto.getCodDepartamento());
        direccionFactura.setDireccion(dto.getDireccion());
        direccionFactura.setEmail(dto.getEmail());
        direccionFactura.setNit(dto.getNit());
        direccionFactura.setNombre("DIR. ESTANDAR POS");
        direccionFactura.setNombreCiudad(dto.getCiudad());
        direccionFactura.setNombreDepartamento(dto.getDepartamento());
        direccionFactura.setTelefono(dto.getTelefono());
        direccionFactura.setTipo("F");
        
        DireccionClienteWeb direccionEntrega = new DireccionClienteWeb();
        direccionEntrega.setCelular(dto.getCelular());
        direccionEntrega.setCodCiudad(dto.getCodCiudad());
        direccionEntrega.setCodDepartamento(dto.getCodDepartamento());
        direccionEntrega.setDireccion(dto.getDireccion());
        direccionEntrega.setEmail(dto.getEmail());
        direccionEntrega.setNit(dto.getNit());
        direccionEntrega.setNombre("DIR. ESTANDAR POS");
        direccionEntrega.setNombreCiudad(dto.getCiudad());
        direccionEntrega.setNombreDepartamento(dto.getDepartamento());
        direccionEntrega.setTelefono(dto.getTelefono());
        direccionEntrega.setTipo("E");
        
        direcciones.add(direccionEntrega);
        direcciones.add(direccionFactura);
        
        ClienteWeb entidad = new ClienteWeb();
        entidad.setApellido1(dto.getApellido1());
        entidad.setApellido2(dto.getApellido2());
        entidad.setAutorretenedor(dto.getAutorretenedor());
        entidad.setCodAsesor("100");
        entidad.setDirEstadarFac("DIR. ESTANDAR POS");
        entidad.setDirEstandarEnt("DIR. ESTANDAR POS");
        entidad.setEmail(dto.getEmail());
        entidad.setFechaNacimiento(dto.getFechaNacimiento());
        entidad.setNacionalidad(dto.getNacionalidad());
        entidad.setNit(dto.getNit());
        entidad.setNombres(StringUtils.normalizeSpace(dto.getNombres()));
        entidad.setOrigenModifica("POS");
        entidad.setRazonSocial(StringUtils.normalizeSpace(dto.getApellido1() + " " + dto.getApellido2() + " " + dto.getNombres()));
        entidad.setRegimenTributario(dto.getRegimen());
        entidad.setSexo(dto.getSexo());
        entidad.setTipoDocumento(dto.getTipoDocumento());
        entidad.setTipoPersona(dto.getTipoPersona());
        
        entidad.setDirecciones(direcciones);
        
        log.log(Level.INFO, "creando cliente desde pos. {0}", entidad.toString());
        
        return Response.ok().build();
        //super.create(entidad);
    }

    @PUT
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void edit(@PathParam("id") String id, ClienteWeb entity) {
        super.edit(entity);
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

}
