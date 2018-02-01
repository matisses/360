package co.matisses.web.rest;

import co.matisses.b1ws.client.businesspartners.BusinessPartnerAddressDTO;
import co.matisses.b1ws.client.businesspartners.BusinessPartnerDTO;
import co.matisses.b1ws.client.businesspartners.BusinessPartnerServiceException;
import co.matisses.b1ws.client.businesspartners.BusinessPartnersServiceConnector;
import co.matisses.persistence.sap.entity.DepartamentoPK;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegocios;
import co.matisses.persistence.sap.entity.DireccionSocioDeNegociosPK;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.facade.DepartamentoSAPFacade;
import co.matisses.persistence.sap.facade.DireccionSocioDeNegociosFacade;
import co.matisses.web.dto.ClientePOSResponseDTO;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.SesionSAPB1WSDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.SAPB1WSBean;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@Stateless
@Path("sociodenegocios")
public class SocioDeNegociosFacadeREST extends AbstractFacade<SocioDeNegocios> {

    private static final Logger log = Logger.getLogger(SocioDeNegociosFacadeREST.class.getSimpleName());
    @PersistenceContext(unitName = "SAPPU")
    private EntityManager em;
    @Inject
    private SAPB1WSBean sapB1WSBean;
    @Inject
    private BaruApplicationMBean baruAplicationBean;
    @EJB
    private DireccionSocioDeNegociosFacade direccionClienteFacade;
    @EJB
    private DepartamentoSAPFacade departamentoFacade;

    public SocioDeNegociosFacadeREST() {
        super(SocioDeNegocios.class);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.APPLICATION_JSON})
    public Response create(final ClienteSAPDTO clienteDto) {
        SesionSAPB1WSDTO sesionSAPDTO = baruAplicationBean.obtenerSesionSAP(clienteDto.getUsuario());
        if (sesionSAPDTO == null) {
            log.log(Level.SEVERE, "No fue posible iniciar una sesion en SAP B1WS.");
            return Response.ok(new ClientePOSResponseDTO("-1",
                    "No fue posible iniciar una sesion en SAP B1WS. Contacte al departamento de sistemas para recibir ayuda.")).build();
        }

        BusinessPartnerDTO dto = new BusinessPartnerDTO();
        dto.setCardName(StringUtils.normalizeSpace(clienteDto.getRazonSocial()));
        if (!clienteDto.getNit().toUpperCase().endsWith("CL")) {
            dto.setCardCode(clienteDto.getNit() + "CL");
        } else {
            dto.setCardCode(clienteDto.getNit());
        }
        dto.setFirstName(clienteDto.getNombres());
        dto.setCardType(BusinessPartnerDTO.CardType.getType("C"));
        dto.setTaxRegime(BusinessPartnerDTO.TaxRegime.getType(clienteDto.getRegimen()));
        dto.setSelfRetainer(clienteDto.getAutorretenedor());
        if (clienteDto.getNacionalidad().equals("01")) {
            dto.setNationality(BusinessPartnerDTO.Nationality.NATIONAL);
        } else if (clienteDto.getNacionalidad().equals("02")) {
            dto.setNationality(BusinessPartnerDTO.Nationality.FOREIGN);
        }
        dto.setPersonType(BusinessPartnerDTO.PersonType.getType(clienteDto.getTipoPersona()));
        dto.setLastName1(clienteDto.getApellido1());
        dto.setLastName2(clienteDto.getApellido2());
        dto.setSalesPersonCode(clienteDto.getCodAsesor() == null ? "98" : clienteDto.getCodAsesor());
        dto.setGender(BusinessPartnerDTO.Gender.getGender(clienteDto.getSexo()));
        //dto.setSalesPersonCode(clienteDto.getCodAsesor());
        dto.setFiscalIdType(BusinessPartnerDTO.FiscalIdType.getType(clienteDto.getTipoDocumento()));
        dto.setFiscalID(clienteDto.getNit());
        dto.setForeignType(BusinessPartnerDTO.ForeignType.getType(clienteDto.getTipoExtranjero()));
        dto.setDefaultBillingAddress("DIRECCION ESTANDAR");
        dto.setDefaultShippingAddress("DIRECCION ESTANDAR");

        BusinessPartnerAddressDTO sAddress = new BusinessPartnerAddressDTO();
        sAddress.setAddressName("DIRECCION ESTANDAR");
        sAddress.setAddressType(BusinessPartnerAddressDTO.AddressType.SHIPPING);
        sAddress.setEmail(clienteDto.getEmail());
        sAddress.setStateCode(clienteDto.getCodDepartamento());
        sAddress.setCityCode(clienteDto.getCodCiudad());
        sAddress.setCityName(clienteDto.getCiudad());
        sAddress.setCellphone(clienteDto.getCelular());
        sAddress.setLandLine(clienteDto.getTelefono());
        sAddress.setAddress(clienteDto.getDireccion());
        sAddress.setTaxCode(null);
        sAddress.setCountry("CO");

        BusinessPartnerAddressDTO bAddress = new BusinessPartnerAddressDTO();
        bAddress.setAddressName("DIRECCION ESTANDAR");
        bAddress.setAddressType(BusinessPartnerAddressDTO.AddressType.BILLING);
        bAddress.setEmail(clienteDto.getEmail());
        bAddress.setStateCode(clienteDto.getCodDepartamento());
        bAddress.setCityCode(clienteDto.getCodCiudad());
        bAddress.setCityName(clienteDto.getCiudad());
        bAddress.setCellphone(clienteDto.getCelular());
        bAddress.setLandLine(clienteDto.getTelefono());
        bAddress.setAddress(clienteDto.getDireccion());
        bAddress.setTaxCode(null);
        bAddress.setCountry("CO");

        dto.addAddress(bAddress);
        dto.addAddress(sAddress);

        try {
            BusinessPartnersServiceConnector bpsc = sapB1WSBean.getBusinessPartnersServiceConnectorInstance(sesionSAPDTO.getIdSesionSAP());
            if (clienteDto.getTipoCliente() != null && clienteDto.getTipoCliente().equals("L")) {
//                bpsc.editBusinessPartner(dto);
            } else {
                bpsc.createBusinessPartner(dto);
            }
            return Response.ok(new ClientePOSResponseDTO("0", null)).build();
        } catch (BusinessPartnerServiceException e) {
            return Response.ok(new ClientePOSResponseDTO("-1", e.getMessage())).build();
        }
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response find(@PathParam("id") String id) {
        log.log(Level.INFO, "Buscando cliente por id [{0}]", id);
        if (id == null || id.trim().isEmpty()) {
            return Response.serverError().build();
        }
        try {
            SocioDeNegocios entidad = super.find(id);
            if (entidad == null) {
                return Response.ok(entidad).build();
            }

            BusinessPartnerDTO bpDto = new BusinessPartnerDTO();
            bpDto.setBirthDate(entidad.getFechaNacimiento() != null ? new SimpleDateFormat("yyyy-MM-dd").format(entidad.getFechaNacimiento()) : null);
            bpDto.setCardCode(entidad.getCardCode());
            bpDto.setCardName(entidad.getRazonSocial());
            bpDto.setCardType(BusinessPartnerDTO.CardType.getType(entidad.getCardType().toString()));
            bpDto.setDefaultBillingAddress(entidad.getDireccionEstandarFactura());
            bpDto.setDefaultShippingAddress(entidad.getDireccionEstandarEntrega());
            bpDto.setFirstName(entidad.getNombres());
            bpDto.setFiscalID(entidad.getLicTradNum());
            bpDto.setFiscalIdType(BusinessPartnerDTO.FiscalIdType.getType(entidad.getTipoDocumento()));
            bpDto.setForeignType(BusinessPartnerDTO.ForeignType.getType("01"));
            bpDto.setGender(BusinessPartnerDTO.Gender.getGender(entidad.getSexo()));
            bpDto.setLastName1(entidad.getApellido1());
            bpDto.setLastName2(entidad.getApellido2());
            if (entidad.getNacionalidad().equals("01")) {
                bpDto.setNationality(BusinessPartnerDTO.Nationality.NATIONAL);
            } else if (entidad.getNacionalidad().equals("02")) {
                bpDto.setNationality(BusinessPartnerDTO.Nationality.FOREIGN);
            }
            bpDto.setPersonType(BusinessPartnerDTO.PersonType.getType(entidad.getTipoPersona()));
            bpDto.setSalesPersonCode(entidad.getSlpCode());
            bpDto.setSelfRetainer(entidad.getAutorretenedor());
            bpDto.setTaxRegime(BusinessPartnerDTO.TaxRegime.getType(entidad.getRegimenTributario()));

            DireccionSocioDeNegocios direccionFactura = null;
            if (entidad.getDireccionEstandarFactura() != null) {
                DireccionSocioDeNegociosPK searchKey = new DireccionSocioDeNegociosPK(entidad.getDireccionEstandarFactura(), entidad.getCardCode(), 'B');
                direccionFactura = direccionClienteFacade.find(searchKey);
            }

            if (direccionFactura != null) {
                BusinessPartnerAddressDTO address = new BusinessPartnerAddressDTO();
                address.setAddress(direccionFactura.getStreet());
                address.setAddressName(direccionFactura.getDireccionSocioDeNegociosPK().getAddress());
                address.setAddressType(BusinessPartnerAddressDTO.AddressType.BILLING);
                address.setCellphone(direccionFactura.getBuilding());
                address.setCityCode(direccionFactura.getUMunicipio());
                address.setCityName(direccionFactura.getCity());
                address.setCountry(direccionFactura.getCountry());
                address.setEmail(direccionFactura.getCounty());
                address.setLandLine(direccionFactura.getBlock());
                address.setStateCode(direccionFactura.getState());
                try {
                    address.setStateName(departamentoFacade.find(new DepartamentoPK(direccionFactura.getState(), "CO")).getName());
                } catch (Exception e) {
                }
                address.setTaxCode(direccionFactura.getTaxCode());
                bpDto.addAddress(address);
            }

            return Response.ok(bpDto).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Ocurrio un error al consultar o procesar los datos del cliente " + id, e);
            return Response.serverError().build();
        }
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    @GET
    @Path("saldo/{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response consultarEstadoCaja(@PathParam("id") String id, @Context HttpServletRequest req) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cast(sum(debit)- sum(credit) as int) * -1 as saldo ");
        sb.append("from JDT1 ");
        sb.append("where Account in (13050501, 28050501, 28050502) ");
        sb.append("and (ShortName='");
        sb.append(id);
        sb.append("')");

        try {
            Integer saldo = (Integer) em.createNativeQuery(sb.toString()).getSingleResult();
            log.log(Level.INFO, "El cliente {0} tiene {1} pesos en saldo a favor", new Object[]{id, saldo});
            return Response.ok(saldo).build();
        } catch (Exception e) {
            log.log(Level.SEVERE, "No se pudo consultar el saldo a favor del cliente [" + id + "]", e);
        }

        return Response.ok(0).build();
    }
}
