package co.matisses.web.rest;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 *
 * @author dbotero
 */
@ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<>();
        /*
        resources.add(co.matisses.web.rest.ClienteWebFacadeREST.class);
        resources.add(co.matisses.web.rest.DepartamentoSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.ItemInventarioFacadeREST.class);
        resources.add(co.matisses.web.rest.MunicipioFacadeREST.class);
        resources.add(co.matisses.web.rest.OperacionCajaFacadeREST.class);
        resources.add(co.matisses.web.rest.POSSessionValidatorREST.class);
        resources.add(co.matisses.web.rest.SocioDeNegociosFacadeREST.class);
        resources.add(co.matisses.web.rest.TarjetaCreditoSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.VentaPOSFacadeREST.class);
        resources.add(co.matisses.web.rest.FacturaSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.EmpleadoFacadeREST.class);
        resources.add(co.matisses.web.rest.NotaCreditoFacadeREST.class);
        resources.add(co.matisses.web.rest.regalos.ListaRegalosSessionValidatorREST.class);
        resources.add(co.matisses.web.rest.regalos.ListaRegalosREST.class);
        resources.add(co.matisses.web.rest.regalos.ConsultaProductosREST.class);
         */
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(co.matisses.web.mbean.BaruApplicationMBean.class);
        resources.add(co.matisses.web.mbean.ImagenProductoMBean.class);
        resources.add(co.matisses.web.rest.ClienteWebFacadeREST.class);
        resources.add(co.matisses.web.rest.DepartamentoSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.EmpaqueVentaFacadeREST.class);
        resources.add(co.matisses.web.rest.EmpleadoFacadeREST.class);
        resources.add(co.matisses.web.rest.FacturaSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.ItemInventarioFacadeREST.class);
        resources.add(co.matisses.web.rest.MercadoLibreREST.class);
        resources.add(co.matisses.web.rest.MunicipioFacadeREST.class);
        resources.add(co.matisses.web.rest.NotaCreditoFacadeREST.class);
        resources.add(co.matisses.web.rest.OperacionCajaFacadeREST.class);
        resources.add(co.matisses.web.rest.POSSessionValidatorREST.class);
        resources.add(co.matisses.web.rest.SocioDeNegociosFacadeREST.class);
        resources.add(co.matisses.web.rest.TarjetaCreditoSAPFacadeREST.class);
        resources.add(co.matisses.web.rest.TipoDocumentoREST.class);
        resources.add(co.matisses.web.rest.VentaPOSFacadeREST.class);
        resources.add(co.matisses.web.rest.regalos.ConsultaProductosREST.class);
        resources.add(co.matisses.web.rest.regalos.ContactoREST.class);
        resources.add(co.matisses.web.rest.regalos.FiltrosProductoREST.class);
        resources.add(co.matisses.web.rest.regalos.ListaRegalosREST.class);
        resources.add(co.matisses.web.rest.regalos.ListaRegalosSessionValidatorREST.class);
    }
}
