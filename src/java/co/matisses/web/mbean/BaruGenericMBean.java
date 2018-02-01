package co.matisses.web.mbean;

import co.matisses.persistence.sap.entity.Almacen;
import co.matisses.persistence.sap.entity.Empleado;
import co.matisses.persistence.sap.entity.ItemInventario;
import co.matisses.persistence.sap.entity.SocioDeNegocios;
import co.matisses.persistence.sap.entity.UbicacionSAP;
import co.matisses.persistence.sap.facade.AlmacenFacade;
import co.matisses.persistence.sap.facade.EmpleadoFacade;
import co.matisses.persistence.sap.facade.ItemInventarioFacade;
import co.matisses.persistence.sap.facade.PrecioVentaItemFacade;
import co.matisses.persistence.sap.facade.SaldoItemInventarioFacade;
import co.matisses.persistence.sap.facade.SalesPersonFacade;
import co.matisses.persistence.sap.facade.SocioDeNegociosFacade;
import co.matisses.persistence.sap.facade.UbicacionSAPFacade;
import java.io.File;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author ygil
 */
@ApplicationScoped
@Named(value = "baruGenericMBean")
public class BaruGenericMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationMBean;
    private static final Locale LOCALE_MX = new Locale("es", "MX");
    private static final DecimalFormat FORMATO = (DecimalFormat) NumberFormat.getInstance(LOCALE_MX);
    private static final Logger log = Logger.getLogger(BaruGenericMBean.class.getSimpleName());
    @EJB
    private ItemInventarioFacade itemInventarioFacade;
    @EJB
    private PrecioVentaItemFacade precioVentaItemFacade;
    @EJB
    private UbicacionSAPFacade ubicacionSAPFacade;
    @EJB
    private AlmacenFacade almacenFacade;
    @EJB
    private SocioDeNegociosFacade socioDeNegociosFacade;
    @EJB
    private SalesPersonFacade salesPersonFacade;
    @EJB
    private EmpleadoFacade empleadoFacade;
    @EJB
    private SaldoItemInventarioFacade saldoItemInventarioFacade;

    public BaruGenericMBean() {
    }

    @PostConstruct
    protected void initialize() {
    }

    public BaruApplicationMBean getApplicationMBean() {
        return applicationMBean;
    }

    public void setApplicationMBean(BaruApplicationMBean applicationMBean) {
        this.applicationMBean = applicationMBean;
    }

    public String convertirARefCorta(String referencia) {
        if (referencia.length() == 20) {
            if (referencia != null && !referencia.isEmpty()) {
                return referencia = referencia.substring(0, 3) + "*" + referencia.substring(16, 20);
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public String obtenerNombreReferencia(String referencia) {
        if (referencia != null && !referencia.isEmpty()) {
            ItemInventario item = itemInventarioFacade.find(referencia);
            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                return item.getItemName();
            }
        }
        return "";
    }

    public String obtenerReferenciaProveedor(String referencia) {
        if (referencia != null && !referencia.isEmpty()) {
            ItemInventario item = itemInventarioFacade.find(referencia);
            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                return item.getUURefPro();
            }
        }
        return "";
    }

    public String obtenerModelo(String referencia) {
        if (referencia != null && !referencia.isEmpty()) {
            ItemInventario item = itemInventarioFacade.find(referencia);
            if (item != null && item.getItemCode() != null && !item.getItemCode().isEmpty()) {
                return item.getUmodelo();
            }
        }
        return "";
    }

    public String completarReferencia(String ref) {
        if (ref != null && (ref.contains("*") || ref.contains("%") || ref.contains("."))) {
            ref = ref.substring(0, 3) + ref.substring(4);
            ref = StringUtils.rightPad(ref.substring(0, 3), 20 - ref.substring(3).length(), "0") + ref.substring(3);
        }
        return ref;
    }

    public Integer obtenerPrecioVenta(String ref) {
        Integer precio = precioVentaItemFacade.findByItemCodeTaxIncluded(ref);

        if (precio != null && precio != 0) {
            return precio;
        } else {
            return 0;
        }
    }

    public Double obtenerPrecioVentaAntesIVA(String ref) {
        Double precio = precioVentaItemFacade.findByItemCode(ref, false);

        if (precio != null && precio != 0) {
            return precio;
        } else {
            return 0D;
        }
    }

    public Double obtenerPrecioCosto(String ref, String almacen) {
        Double precio = saldoItemInventarioFacade.buscarReferenciaSaldoBodega(ref, almacen).getAvgPrice().doubleValue();

        if (precio != null && precio != 0) {
            return precio;
        } else {
            return 0D;
        }
    }

    public Long obtenerPrecioVentaDescuento(Long descuento, String referencia) {
        if (descuento > 0) {
            Integer precio = obtenerPrecioVenta(referencia);

            return precio - ((precio / 100) * descuento);
        }

        return 0L;
    }

    public String getValorFormateado(String tipoDato, Object valor, Integer decimalesVisibles) {
        if (tipoDato != null && valor != null) {
            switch (tipoDato) {
                case "String":
                    return valor.toString();
                case "Integer":
                    try {
                        return FORMATO.format(NumberFormat.getNumberInstance(LOCALE_MX).parse(valor.toString()));
                    } catch (Exception e) {
                        return valor.toString();
                    }
                case "Double":
                    try {
                        NumberFormat format = NumberFormat.getInstance(LOCALE_MX);
                        if (decimalesVisibles != null && decimalesVisibles > 0) {
                            //format.setMaximumFractionDigits(decimalesVisibles);
                        }
                        return FORMATO.format(format.parse(valor.toString()));
                    } catch (Exception e) {
                        return valor.toString();
                    }
                case "Date":
                    return new SimpleDateFormat("yyyy-MM-dd").format(valor);
                case "Time":
                    return new SimpleDateFormat("HH:mm:ss.SSS").format(valor);
                case "Datetime":
                    return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(valor);
                case "DateSimple":
                    return new SimpleDateFormat("dd-MMMMM").format(valor);
                default:
            }
        }
        return valor != null ? valor.toString() : null;
    }

    public String obtenerAliasUbicacion(Integer absEntry) {
        if (absEntry != null && absEntry != 0) {
            UbicacionSAP u = ubicacionSAPFacade.find(absEntry);

            if (u != null && u.getAbsEntry() != null && u.getAbsEntry() != 0) {
                return u.getAttr2Val();
            }
        }
        return "";
    }

    public String obtenerNombreWebAlmacen(String almacen) {
        if (almacen != null && !almacen.isEmpty()) {
            Almacen alm = almacenFacade.find(almacen);

            if (alm != null && alm.getWhsCode() != null && !alm.getWhsCode().isEmpty()) {
                if (alm.getUnombrextablet() != null && !alm.getUnombrextablet().isEmpty()) {
                    return alm.getUnombrextablet();
                } else {
                    return alm.getWhsCode();
                }
            } else {
                return almacen;
            }
        }
        return "";
    }

    public String obtenerNombreUsuario(String cardCode) {
        if (cardCode != null && !cardCode.isEmpty()) {
            SocioDeNegocios socio = socioDeNegociosFacade.findByCardCode(cardCode);

            if (socio != null && socio.getCardCode() != null && !socio.getCardCode().isEmpty()) {
                if (socio.getApellido2() != null && !socio.getApellido2().isEmpty()) {
                    return socio.getNombres() + " " + socio.getApellido1() + " " + socio.getApellido2();
                } else {
                    return socio.getNombres() + " " + socio.getApellido1();
                }
            }
        }
        return cardCode;
    }

    public String obtenerNombreAsesor(Integer slpCode) {
        if (slpCode != null && slpCode != 0) {
            String salesPerson = salesPersonFacade.obtenerNombreAsesor(slpCode);

            if (salesPerson != null && !salesPerson.isEmpty()) {
                return salesPerson;
            }

            return "";
        }
        return "";
    }

    public String obtenerRutaFotoEmpleado(String cedula) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("empleados.url.web.image");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.web.image] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }
        urlWeb = String.format(urlWeb, cedula);

        String url = applicationMBean.obtenerValorPropiedad("empleados.url.local.image");
        if (url != null) {
            url = String.format(url, cedula);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.local.image] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.WARNING, "el empleado [{0}] no tiene imagen ", cedula);
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage");
        }
    }

    public String obtenerRutaFotoEmpleadoComision(String cedula) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("empleados.url.web.image.comision");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.web.image.comision] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }
        urlWeb = String.format(urlWeb, cedula);

        String url = applicationMBean.obtenerValorPropiedad("empleados.url.local.image.comision");
        if (url != null) {
            url = String.format(url, cedula);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.local.image.comision] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.WARNING, "el empleado [{0}] no tiene imagen ", cedula);
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }
    }

    public String obtenerRutaFotoEmpleadoCumples(String cedula) {
        String urlWeb = applicationMBean.obtenerValorPropiedad("empleados.url.web.image.cumples");
        if (urlWeb == null) {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.web.image.cumples] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }
        urlWeb = String.format(urlWeb, cedula);

        String url = applicationMBean.obtenerValorPropiedad("empleados.url.local.image.cumples");
        if (url != null) {
            url = String.format(url, cedula);
        } else {
            log.log(Level.SEVERE, "No se encontro el valor de [empleados.url.local.image.cumples] en baru.properties");
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }

        File f = new File(url);
        if (f.exists()) {
            return urlWeb;
        } else {
            log.log(Level.WARNING, "el empleado [{0}] no tiene imagen ", cedula);
            return applicationMBean.obtenerValorPropiedad("empleados.url.web.noimage.comision");
        }
    }

    public String obtenerNombreEmpleadoCedula(String cedula) {
        if (cedula != null && !cedula.isEmpty()) {
            Empleado emp = empleadoFacade.obtenerEmpleadoDocumento(cedula.replace("PR", ""));

            if (emp != null && emp.getEmpID() != null && emp.getEmpID() != 0) {
                if (emp.getMiddleName() != null && !emp.getMiddleName().isEmpty()) {
                    return emp.getLastName() + " " + emp.getMiddleName() + " " + emp.getFirstName();
                } else {
                    return emp.getLastName() + " " + emp.getFirstName();
                }
            }
        }

        return "";
    }

    public String obtenerEmpleado(Integer empID) {
        if (empID != null && empID != 0) {
            Empleado empleado = empleadoFacade.find(empID);

            if (empleado != null && empleado.getEmpID() != null && empleado.getEmpID() != 0) {
                return empleado.getLastName() + " " + empleado.getFirstName();
            }
        }

        return "";
    }
}
