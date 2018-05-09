package co.matisses.web.mbean.producto;

import co.matisses.persistence.sap.entity.SaldoItemInventario;
import co.matisses.persistence.sap.entity.SaldoUbicacion;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SaldoUbicacionFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import co.matisses.web.dto.SaldoItemDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
@Named(value = "saldoAlmacenMBean")
public class SaldoAlmacenMBean implements Serializable {

    private static final Logger CONSOLE = Logger.getLogger(SaldoAlmacenMBean.class.getSimpleName());
    private String almacen;
    private List<SaldoItemDTO> ventas;
    private List<SaldoItemDTO> clientes;
    private List<SaldoItemDTO> usos;
    private List<SaldoItemDTO> insumos;
    private List<SaldoItemDTO> demostraciones;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private SaldoUbicacionFacade saldoUbicacionFacade;

    public SaldoAlmacenMBean() {
        ventas = new ArrayList<>();
        clientes = new ArrayList<>();
        usos = new ArrayList<>();
        insumos = new ArrayList<>();
        demostraciones = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public List<SaldoItemDTO> getVentas() {
        return ventas;
    }

    public void setVentas(List<SaldoItemDTO> ventas) {
        this.ventas = ventas;
    }

    public List<SaldoItemDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<SaldoItemDTO> clientes) {
        this.clientes = clientes;
    }

    public List<SaldoItemDTO> getUsos() {
        return usos;
    }

    public void setUsos(List<SaldoItemDTO> usos) {
        this.usos = usos;
    }

    public List<SaldoItemDTO> getInsumos() {
        return insumos;
    }

    public void setInsumos(List<SaldoItemDTO> insumos) {
        this.insumos = insumos;
    }

    public List<SaldoItemDTO> getDemostraciones() {
        return demostraciones;
    }

    public void setDemostraciones(List<SaldoItemDTO> demostraciones) {
        this.demostraciones = demostraciones;
    }

    public void obtenerSaldoAlmacen() {
        ventas = new ArrayList<>();

        if (almacen == null || almacen.isEmpty()) {
            mostrarMensaje("Error", "Ingrese un almacén para poder realizar la consulta del saldo.", true, false, false);
            CONSOLE.log(Level.SEVERE, "Ingrese un almacen para poder realizar la consulta del saldo");
            return;
        }

        String warehouse = almacen.toUpperCase().replace("CL", "").replace("AF", "").replace("IN", "").replace("DM", "").replace("MU", "");
        //Se obtiene el saldo en almacen de venta
        if (ubicacionSAPFacade.validarUbicacionesAlmacen(warehouse)) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoAlmacen(warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                ventas = construirSaldoUbicacion(warehouse, saldos);
            }
        } else {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldoAlmacen(warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                ventas = construirSaldo(saldos);
            }
        }

        //Se obtiene el saldo en almacenes de clientes
        if (ubicacionSAPFacade.validarUbicacionesAlmacen("CL" + warehouse)) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoAlmacen("CL" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                clientes = construirSaldoUbicacion("CL" + warehouse, saldos);
            }
        } else {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldoAlmacen("CL" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                clientes = construirSaldo(saldos);
            }
        }

        //Se obtiene el saldo en almacenes de mercancia en uso
        if (ubicacionSAPFacade.validarUbicacionesAlmacen("MU" + warehouse)) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoAlmacen("MU" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                usos = construirSaldoUbicacion("MU" + warehouse, saldos);
            }
        } else {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldoAlmacen("MU" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                usos = construirSaldo(saldos);
            }
        }

        //Se obtiene el saldo en almacenes de insumos
        if (ubicacionSAPFacade.validarUbicacionesAlmacen("IN" + warehouse)) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoAlmacen("IN" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                insumos = construirSaldoUbicacion("IN" + warehouse, saldos);
            }
        } else {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldoAlmacen("IN" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                insumos = construirSaldo(saldos);
            }
        }

        //Se obtiene el saldo en almacenes de demostraciones
        if (ubicacionSAPFacade.validarUbicacionesAlmacen("DM" + warehouse)) {
            List<SaldoUbicacion> saldos = saldoUbicacionFacade.obtenerSaldoAlmacen("DM" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                demostraciones = construirSaldoUbicacion("DM" + warehouse, saldos);
            }
        } else {
            List<SaldoItemInventario> saldos = saldoItemInventarioFacade.obtenerSaldoAlmacen("DM" + warehouse);

            if (saldos != null && !saldos.isEmpty()) {
                demostraciones = construirSaldo(saldos);
            }
        }

        Collections.sort(ventas, new Comparator<SaldoItemDTO>() {
            @Override
            public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                return o1.getReferencia().compareTo(o2.getReferencia());
            }
        });
        Collections.sort(clientes, new Comparator<SaldoItemDTO>() {
            @Override
            public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                return o1.getReferencia().compareTo(o2.getReferencia());
            }
        });
        Collections.sort(usos, new Comparator<SaldoItemDTO>() {
            @Override
            public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                return o1.getReferencia().compareTo(o2.getReferencia());
            }
        });
        Collections.sort(insumos, new Comparator<SaldoItemDTO>() {
            @Override
            public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                return o1.getReferencia().compareTo(o2.getReferencia());
            }
        });
        Collections.sort(demostraciones, new Comparator<SaldoItemDTO>() {
            @Override
            public int compare(SaldoItemDTO o1, SaldoItemDTO o2) {
                return o1.getReferencia().compareTo(o2.getReferencia());
            }
        });
    }

    private List<SaldoItemDTO> construirSaldo(List<SaldoItemInventario> saldos) {
        if (saldos != null && !saldos.isEmpty()) {
            List<SaldoItemDTO> balances = new ArrayList<>();

            for (SaldoItemInventario s : saldos) {
                SaldoItemDTO dto = new SaldoItemDTO();

                dto.setCantidad(s.getOnHand().intValue());
                dto.setReferencia(s.getSaldoItemInventarioPK().getItemCode());

                balances.add(dto);
            }

            return balances;
        }

        return null;
    }

    private List<SaldoItemDTO> construirSaldoUbicacion(String warehouse, List<SaldoUbicacion> saldos) {
        if (saldos != null && !saldos.isEmpty()) {
            List<SaldoItemDTO> balances = new ArrayList<>();

            for (SaldoUbicacion s : saldos) {
                SaldoItemDTO dto = new SaldoItemDTO();

                dto.setCantidad(s.getOnHandQty().intValue());
                dto.setReferencia(s.getItemCode());
                dto.setEstado(s.getUbicacion().getBinCode().replace(warehouse, ""));

                balances.add(dto);
            }

            return balances;
        }

        return null;
    }

    public void limpiar() {
        almacen = null;
        ventas = new ArrayList<>();
        clientes = new ArrayList<>();
        usos = new ArrayList<>();
        insumos = new ArrayList<>();
        demostraciones = new ArrayList<>();
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
