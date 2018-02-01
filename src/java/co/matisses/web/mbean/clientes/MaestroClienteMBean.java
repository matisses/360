package co.matisses.web.mbean.clientes;

import co.matisses.web.Acciones;
import co.matisses.web.EmailPatternValidator;
import co.matisses.web.Objetos;
import co.matisses.web.bcs.businesspartner.BusinessPartner;
import co.matisses.web.bcs.client.BusinessPartnerClient;
import co.matisses.web.bcs.client.QuotationsClient;
import co.matisses.web.dto.BusinessPartnerDTO;
import co.matisses.web.dto.CiudadDTO;
import co.matisses.web.dto.ClienteSAPDTO;
import co.matisses.web.dto.DetalleCotizacionWebDTO;
import co.matisses.web.dto.DireccionesClienteDTO;
import co.matisses.web.dto.GenericRESTResponseDTO;
import co.matisses.web.dto.QuotationsDocumentDTO;
import co.matisses.web.dto.QuotationsDocumentLinesDTO;
import co.matisses.web.dto.TipoDocumentoDTO;
import co.matisses.web.dto.TipoRetencionDTO;
import co.matisses.web.mbean.BaruApplicationMBean;
import co.matisses.web.mbean.UserSessionInfoMBean;
import co.matisses.web.mbean.session.VentasSessionMBean;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dbotero
 */
@ViewScoped
@Named(value = "maestroClienteMBean")
public class MaestroClienteMBean implements Serializable {

    @Inject
    private BaruApplicationMBean applicationBean;
    @Inject
    private ClienteSessionMBean clienteSession;
    @Inject
    private UserSessionInfoMBean sessionInfo;
    @Inject
    private VentasSessionMBean ventasSessionMBean;
    private static final Logger log = Logger.getLogger(MaestroClienteMBean.class.getSimpleName());
    private static final String TIPO_PERSONA_NATURAL = "01";
    private static final String TIPO_PERSONA_JURIDICA = "02";
    private static final String TIPO_DOCUMENTO_NIT = "31";
    private boolean buscando = false;
    private boolean cotizacion = false;
    private boolean facturando = false;
    private boolean demostracion = false;
    private boolean notaCredito = false;
    private boolean advertencia = false;
    private BusinessPartner businessPartner;
    private List<Integer> dias;
    private List<TipoRetencionDTO> retencionesCliente;

    public MaestroClienteMBean() {
        businessPartner = new BusinessPartner();
        dias = new ArrayList<>();
        retencionesCliente = new ArrayList<>();
    }

    @PostConstruct
    protected void initialize() {
        cargarRetenciones();

        if (ventasSessionMBean.isFacturando()) {
            facturando = true;
        } else if (ventasSessionMBean.isDemostracion()) {
            demostracion = true;
        } else if (ventasSessionMBean.getCotizacion() != null && ventasSessionMBean.getCotizacion().getIdCotizacion() != null
                && ventasSessionMBean.getCotizacion().getIdCotizacion() != 0) {
            cotizacion = true;
        } else if (ventasSessionMBean.isCreaNotaCredito()) {
            notaCredito = true;
        }

        if (clienteSession.getClienteDto().getNit() != null && !clienteSession.getClienteDto().getNit().isEmpty()) {
            buscar();
        }
    }

    public boolean isBuscando() {
        return buscando;
    }

    public List<TipoRetencionDTO> getRetencionesCliente() {
        return retencionesCliente;
    }

    public void setRetencionesCliente(List<TipoRetencionDTO> retencionesCliente) {
        this.retencionesCliente = retencionesCliente;
    }

    public ClienteSessionMBean getClienteSession() {
        return clienteSession;
    }

    public void setClienteSession(ClienteSessionMBean clienteSession) {
        this.clienteSession = clienteSession;
    }

    public UserSessionInfoMBean getSessionInfo() {
        return sessionInfo;
    }

    public void setSessionInfo(UserSessionInfoMBean sessionInfo) {
        this.sessionInfo = sessionInfo;
    }

    public VentasSessionMBean getVentasSessionMBean() {
        return ventasSessionMBean;
    }

    public void setVentasSessionMBean(VentasSessionMBean ventasSessionMBean) {
        this.ventasSessionMBean = ventasSessionMBean;
    }

    public boolean isCotizacion() {
        return cotizacion;
    }

    public void setCotizacion(boolean cotizacion) {
        this.cotizacion = cotizacion;
    }

    public boolean isFacturando() {
        return facturando;
    }

    public void setFacturando(boolean facturando) {
        this.facturando = facturando;
    }

    public boolean isDemostracion() {
        return demostracion;
    }

    public void setDemostracion(boolean demostracion) {
        this.demostracion = demostracion;
    }

    public boolean isNotaCredito() {
        return notaCredito;
    }

    public void setNotaCredito(boolean notaCredito) {
        this.notaCredito = notaCredito;
    }

    public boolean isAdvertencia() {
        return advertencia;
    }

    public void setAdvertencia(boolean advertencia) {
        this.advertencia = advertencia;
    }

    public List<Integer> getDias() {
        return dias;
    }

    public String getTipoPersonaSeleccionada() {
        if (clienteSession.getClienteDto().getTipoPersona() != null && !clienteSession.getClienteDto().getTipoPersona().isEmpty()) {
            if (clienteSession.getClienteDto().getTipoPersona().equals("01")) {
                return "Natural";
            } else if (clienteSession.getClienteDto().getTipoPersona().equals("02")) {
                return "Jurídica";
            }
        }
        return "Seleccione";
    }

    public String getTipoDocumentoSeleccionado() {
        if (clienteSession.getClienteDto().getTipoDocumento() != null && !clienteSession.getClienteDto().getTipoDocumento().isEmpty()) {
            for (TipoDocumentoDTO t : applicationBean.getTiposDocumento()) {
                if (t.getCodigo().equals(clienteSession.getClienteDto().getTipoDocumento())) {
                    return t.getNombre();
                }
            }
        }
        return "Seleccione";
    }

    public String getNacionalidadSeleccionado() {
        if (clienteSession.getClienteDto().getNacionalidad() != null && !clienteSession.getClienteDto().getNacionalidad().isEmpty()) {
            if (clienteSession.getClienteDto().getNacionalidad().equals("01")) {
                return "Nacional";
            } else if (clienteSession.getClienteDto().getNacionalidad().equals("02")) {
                return "Extranjero";
            }
        }
        return "Seleccione";
    }

    public String getYearSeleccionado() {
        if (clienteSession.getClienteDto().getBirthYear() != null && !clienteSession.getClienteDto().getBirthYear().isEmpty()) {
            return clienteSession.getClienteDto().getBirthYear();
        }
        return "Seleccione año";
    }

    public String getMonthSeleccionado() {
        if (clienteSession.getClienteDto().getBirthMonth() != null && !clienteSession.getClienteDto().getBirthMonth().isEmpty()) {
            switch (clienteSession.getClienteDto().getBirthMonth()) {
                case "1":
                    return "Enero";
                case "2":
                    return "Febrero";
                case "3":
                    return "Marzo";
                case "4":
                    return "Abril";
                case "5":
                    return "Mayo";
                case "6":
                    return "Junio";
                case "7":
                    return "Julio";
                case "8":
                    return "Agosto";
                case "9":
                    return "Septiembre";
                case "10":
                    return "Octubre";
                case "11":
                    return "Noviembre";
                case "12":
                    return "Diciembre";
                default:
                    break;
            }
        }
        return "Seleccione mes";
    }

    public String getDaySeleccionado() {
        if (clienteSession.getClienteDto().getBirthDay() != null && !clienteSession.getClienteDto().getBirthDay().isEmpty()) {
            return clienteSession.getClienteDto().getBirthDay();
        }
        return "Seleccione día";
    }

    private void cargarRetenciones() {
        retencionesCliente = new ArrayList<>();
        for (TipoRetencionDTO dto : applicationBean.getRetenciones()) {
            TipoRetencionDTO newDto = new TipoRetencionDTO(dto.getCodigo(), dto.getNombre(), "", 0, true);
            retencionesCliente.add(newDto);
        }
    }

    public void cargarDias() {
        log.log(Level.INFO, "Cargando los dias para el mes [{0}] del anio [{1}]",
                new Object[]{clienteSession.getClienteDto().getBirthMonth(), clienteSession.getClienteDto().getBirthYear()});
        dias.clear();
        if (clienteSession.getClienteDto().getBirthYear() == null || clienteSession.getClienteDto().getBirthYear().isEmpty()) {
            return;
        }
        if (clienteSession.getClienteDto().getBirthMonth() == null || clienteSession.getClienteDto().getBirthMonth().isEmpty()) {
            return;
        }
        GregorianCalendar cal = new GregorianCalendar();
        cal.set(Calendar.YEAR, Integer.parseInt(clienteSession.getClienteDto().getBirthYear()));
        cal.set(Calendar.MONTH, Integer.parseInt(clienteSession.getClienteDto().getBirthMonth()) - 1);
        int i;
        for (i = 1; i <= cal.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {
            dias.add(i);
        }
    }

    public void procesarCambioTipoPersona(AjaxBehaviorEvent e) {
        log.log(Level.INFO, "Se selecciono el tipo de persona [{0}]", ((UIOutput) e.getSource()).getValue());
        construirRazonSocial();
    }

    public void procesarCambioTipoDocumento(AjaxBehaviorEvent e) {
        log.log(Level.INFO, "Se selecciono el tipo de documento [{0}]", ((UIOutput) e.getSource()).getValue());
        //si tipopersona = juridica o tipoDoc = nit -> calcular digito verificacion
        if (clienteSession.getClienteDto().getTipoPersona().equals(TIPO_PERSONA_JURIDICA)
                || clienteSession.getClienteDto().getTipoDocumento().equals(TIPO_DOCUMENTO_NIT)) {
            //calcular digito de verificacion
            clienteSession.getClienteDto().setDigitoVerificacion(Integer.toString(calcularDigitoVerificacion(clienteSession.getClienteDto().getNit())));
        } else {
            clienteSession.getClienteDto().setDigitoVerificacion("");
        }
    }

    public void procesarCambioValorVista(AjaxBehaviorEvent e) {
        log.log(Level.INFO, "Nuevo valor ingresado [{0}={1}]", new Object[]{e.getComponent().getClientId(), ((UIOutput) e.getSource()).getValue()});
    }

    public void procesarCambioValorRetencion(AjaxBehaviorEvent e) {
        String codigoRetencion = (String) e.getComponent().getAttributes().get("title");
        boolean aplicaRetencion = (Boolean) ((UIOutput) e.getSource()).getValue().equals("1");
        log.log(Level.INFO, "Retencion [{0}] aplica = [{1}]", new Object[]{codigoRetencion, aplicaRetencion});
    }

    public void procesarCambioNroDocumento(AjaxBehaviorEvent e) {
        log.log(Level.INFO, "Se ingreso el nro de documento [{0}]", ((UIOutput) e.getSource()).getValue());
        //si tipopersona = juridica o tipoDoc = nit -> calcular digito verificacion
        if (clienteSession.getClienteDto().getTipoPersona().equals(TIPO_PERSONA_JURIDICA)
                || clienteSession.getClienteDto().getTipoDocumento().equals(TIPO_DOCUMENTO_NIT)) {
            //calcular digito de verificacion
            clienteSession.getClienteDto().setDigitoVerificacion(Integer.toString(calcularDigitoVerificacion(clienteSession.getClienteDto().getNit())));
        } else {
            clienteSession.getClienteDto().setDigitoVerificacion("");
        }
    }

    public void procesarCambioNombres(ValueChangeEvent e) {
        if (!e.getNewValue().equals(e.getOldValue())) {
            log.log(Level.INFO, "Se cambio el valor del nombre, nuevo valor {0}, viejo valor {1}", new Object[]{e.getNewValue(), e.getOldValue()});
            construirRazonSocial();
        }
    }

    public void construirRazonSocial() {
        if (clienteSession.getClienteDto().getTipoPersona().equals("01")) {
            log.log(Level.INFO, "Construyendo razon social ");
            String nombres = StringUtils.defaultIfBlank(clienteSession.getClienteDto().getNombres(), "");
            String apellido1 = StringUtils.defaultIfBlank(clienteSession.getClienteDto().getApellido1(), "");
            String apellido2 = StringUtils.defaultIfBlank(clienteSession.getClienteDto().getApellido2(), "");

            if (apellido1.trim().isEmpty()) {
                log.log(Level.SEVERE, "El primer apellido no puede estar vacio");
                return;
            }
            if (nombres.trim().isEmpty()) {
                log.log(Level.SEVERE, "El primer nombre no puede estar vacio");
                return;
            }
            if (nombres.length() > 30) {
                log.log(Level.SEVERE, "La longitud de los nombres excede la longitud total de 30 caracteres");
                return;
            }

            String razonSocial = apellido1 + (apellido2 == null ? "" : " " + apellido2) + (" " + nombres);

            clienteSession.getClienteDto().setRazonSocial(StringUtils.normalizeSpace(razonSocial));
        }
    }

    private int calcularDigitoVerificacion(String strNit) {
        int digitoChequeo = -1;
        int liPeso[] = {71, 67, 59, 53, 47, 43, 41, 37, 29, 23, 19, 17, 13, 7, 3};
        int liSuma = 0;

        if (strNit != null && strNit.trim().length() > 0) {
            while (strNit.length() < 15) {
                strNit = "0" + strNit;
            }
            try {
                for (int i = 0; i < 15; i++) {
                    liSuma += (new Integer(strNit.substring(i, i + 1))) * liPeso[i];
                }
                digitoChequeo = liSuma % 11;
                if (digitoChequeo >= 2) {
                    digitoChequeo = 11 - digitoChequeo;
                }
            } catch (Exception e) {
                return -2;
            }
        } else {
            return -1;
        }
        return digitoChequeo;
    }

    private void obtenerDatosWebService() {
        businessPartner = new BusinessPartner();

        BusinessPartnerClient client = new BusinessPartnerClient(applicationBean.obtenerValorPropiedad("url.bcs.rest"));

        try {
            businessPartner = client.buscarCliente(clienteSession.getClienteDto().getNit());

            if (businessPartner != null && businessPartner.getCardCode() != null && !businessPartner.getCardCode().isEmpty()) {
                buscando = true;
            } else {
                log.log(Level.SEVERE, "Ocurrio un error al consultar el cliente. El cliente no existe.");
                mostrarMensaje("Ocurrió un error", "No fue posible consultar el cliente. El cliente no existe.", true, false, false);
                buscando = false;
                return;
            }
        } catch (Exception e) {
            if (e.getMessage().contains("Unrecognized field")) {
                log.log(Level.SEVERE, "Ocurrio un error al consultar el cliente. El cliente no existe.");
                mostrarMensaje("Ocurrió un error", "No fue posible consultar el cliente. El cliente no existe.", true, false, false);
            } else {
                log.log(Level.SEVERE, "Ocurrio un error al consultar el cliente. ", e);
                mostrarMensaje("Ocurrió un error", "No fue posible consultar el cliente. " + e.getMessage(), true, false, false);
            }
            buscando = false;
            return;
        }
    }

    private boolean validarClienteDireccion(ClienteSAPDTO clienteDto, DireccionesClienteDTO direccionDto) {
        if (clienteDto.getNacionalidad() == null || clienteDto.getNacionalidad().trim().isEmpty()) {
            log.log(Level.SEVERE, "Seleccione una opcion de nacionalidad para el cliente");
            mostrarMensaje("Nacionalidad no válida", "seleccione una opción de nacionalidad para el cliente.", true, false, false);
            return false;
        }
        if (clienteDto.getTipoDocumento() == null || clienteDto.getTipoDocumento().trim().isEmpty()) {
            log.log(Level.SEVERE, "Selecciona un tipo de documento para el cliente");
            mostrarMensaje("Tipo de documento no válido", "Selecciona un tipo de documento para el cliente.", true, false, false);
            return false;
        }
        if (clienteDto.getNit() == null || clienteDto.getNit().trim().isEmpty()) {
            log.log(Level.SEVERE, "El NIT ingresado esta vacio. Ingrese un NIt valido");
            mostrarMensaje("Debes ingresar un NIT", "El NIT ingresado está vacío. Ingresa un NIT válido.", true, false, false);
            return false;
        }
        if (clienteDto.getNombres() == null || clienteDto.getNombres().trim().isEmpty()) {
            if (clienteDto.getTipoPersona().equals(BusinessPartnerDTO.PersonType.NATURAL.getType())) {
                log.log(Level.SEVERE, "Ingrese los nombres del cliente");
                mostrarMensaje("Falta el nombre", "Ingrese el nombre del cliente.", true, false, false);
            } else {
                log.log(Level.SEVERE, "Ingrese el nombre de la persona de contacto (la persona que realiza la compra)");
                mostrarMensaje("Falta el nombre", "Ingrese el nombre de la persona de contacto (la persona que realiza la compra).", true, false, false);
            }
            return false;
        }
        if (clienteDto.getApellido1() == null || clienteDto.getApellido1().trim().isEmpty()) {
            if (clienteDto.getTipoPersona().equals(BusinessPartnerDTO.PersonType.NATURAL.getType())) {
                log.log(Level.SEVERE, "Ingrese el primer apellido del cliente");
                mostrarMensaje("Falta el primer apellido", "Ingrese el primer apellido del cliente.", true, false, false);
            } else {
                log.log(Level.SEVERE, "Ingrese los apellidos de la persona de contacto (la persona que realiza la compra)");
                mostrarMensaje("Falta los apellidos", "Ingrese los apellidos de la persona de contacto (la persona que realiza la compra).", true, false, false);
            }
            return false;
        }
        if (clienteDto.getTipoPersona().equals(BusinessPartnerDTO.PersonType.JURIDICA.getType())) {
            if (clienteDto.getRazonSocial() == null || clienteDto.getRazonSocial().trim().isEmpty()) {
                log.log(Level.SEVERE, "Ingrese la razón social de la empresa, como aparece en el RUT");
                mostrarMensaje("Falta la razón social", "Ingrese la razón social de la empresa, como aparece en el RUT.", true, false, false);
                return false;
            }
        } else {
            if (clienteDto.getBirthYear() == null || clienteDto.getBirthYear().trim().isEmpty() || clienteDto.getBirthMonth() == null || clienteDto.getBirthMonth().trim().isEmpty()
                    || clienteDto.getBirthDay() == null || clienteDto.getBirthDay().trim().isEmpty()) {
                log.log(Level.SEVERE, "Ingresa la fecha de nacimiento del cliente o enero-01-1900 si no la tienes");
                mostrarMensaje("Fecha de nacimiento incompleta", "Ingrese la fecha de nacimiento del cliente o enero-01-1900 si no la tienes.", true, false, false);
                return false;
            }
            if (clienteDto.getSexo() == null || clienteDto.getSexo().trim().isEmpty()) {
                log.log(Level.SEVERE, "Seleccione el genero del cliente");
                mostrarMensaje("Falta el género", "Seleccione el género del cliente.", true, false, false);
                return false;
            }
        }
//        if (clienteDto.getRegimen() == null || clienteDto.getRegimen().trim().isEmpty()) {
//            mostrarMensaje("Falta el régimen tributario", "Selecciona el régimen tributario del cliente. Si el cliente no lo sabe, es <b>No responsable</b>", FacesMessage.SEVERITY_ERROR);
//            return false;
//        }
        if (direccionDto.getNombre() == null || direccionDto.getNombre().trim().isEmpty()) {
            log.log(Level.SEVERE, "Ingrese el nombre para identificar la dirección (casa, oficina, apartamento, finca, etc)");
            mostrarMensaje("Falta el nombre de la dirección", "Ingrese el nombre para identificar la dirección (casa, oficina, apartamento, finca, etc).", true, false, false);
            return false;
        }
        if (direccionDto.getDireccion() == null || direccionDto.getDireccion().trim().isEmpty()) {
            log.log(Level.SEVERE, "Ingrese la direccion del cliente");
            mostrarMensaje("Falta la dirección", "Ingrese la dirección del cliente.", true, false, false);
            return false;
        }
        if (clienteSession.getCiudadBusqueda() == null || clienteSession.getCiudadBusqueda().trim().isEmpty()) {
            log.log(Level.SEVERE, "Ingrese la ciudad para la dirección del cliente");
            mostrarMensaje("Falta la ciudad", "Ingresa la ciudad para la dirección del cliente.", true, false, false);
            return false;
        }
        if (direccionDto.getCelular() == null || direccionDto.getCelular().trim().isEmpty()) {
            log.log(Level.SEVERE, "Ingrese el celular del cliente");
            mostrarMensaje("Falta el número de celular", "Ingrese el celular del cliente.", true, false, false);
            return false;
        }

        Pattern ptr = Pattern.compile(EmailPatternValidator.getInstance().getEmailPattern());
        if (direccionDto.getEmail() == null || direccionDto.getEmail().trim().isEmpty()) {
            log.log(Level.SEVERE, "Ingrese el correo del cliente");
            mostrarMensaje("Falta el correo electrónico", "Ingresa el correo del cliente.", true, false, false);
            return false;
        } else if (!ptr.matcher(direccionDto.getEmail()).matches()) {
            log.log(Level.SEVERE, "El correo ingresado no es valido");
            mostrarMensaje("Error", "El correo ingresado no es válido.", true, false, false);
            return false;
        }
        return true;
    }

    public void guardarCliente() {
        if (guardarClienteSAP()) {
            limpiar();
        }
    }

    private boolean guardarClienteSAP() {
        ClienteSAPDTO dto = clienteSession.getClienteDto();
        DireccionesClienteDTO direccionDto = clienteSession.getDireccionCliente();

        if (!validarClienteDireccion(dto, direccionDto)) {
            return false;
        }

        log.log(Level.INFO, "Guardando cliente [{0}{1}]", new Object[]{dto, direccionDto});
        if (!dto.getNit().toUpperCase().endsWith("CL")) {
            dto.setNit(dto.getNit() + "CL");
        }

        BusinessPartnerClient client = new BusinessPartnerClient(applicationBean.obtenerValorPropiedad("url.bcs.rest"));

        if (businessPartner == null || businessPartner.getCardCode() == null || businessPartner.getCardCode().isEmpty()) {
            businessPartner = new BusinessPartner();
        }

        businessPartner.setUEsAutorret(clienteSession.getClienteDto().getAutorretenedor());
        businessPartner.setSalesPersonCode(Long.parseLong(sessionInfo.getCodigoVentas()));
        businessPartner.setUBPCOBPExt(clienteSession.getClienteDto().getNacionalidad());
        businessPartner.setCardCode(clienteSession.getClienteDto().getNit());
        businessPartner.setCardName(clienteSession.getClienteDto().getRazonSocial().toUpperCase());
        businessPartner.setUBPCORTC(clienteSession.getClienteDto().getRegimen());
        businessPartner.setUBPCOTDC(clienteSession.getClienteDto().getTipoDocumento());
        businessPartner.setUBPCOTP(clienteSession.getClienteDto().getTipoPersona());
        businessPartner.setUEsAutorret((clienteSession.getClienteDto().getAutorretenedor() != null && !clienteSession.getClienteDto().getAutorretenedor().isEmpty())
                ? clienteSession.getClienteDto().getAutorretenedor() : "N");
        businessPartner.setBilltoDefault(clienteSession.getDireccionCliente().getNombre().toUpperCase());
        businessPartner.setShipToDefault(clienteSession.getDireccionCliente().getNombre().toUpperCase());
        businessPartner.setSubjectToWithholdingTax(businessPartner.getSubjectToWithholdingTax() != null && !businessPartner.getSubjectToWithholdingTax().isEmpty()
                ? businessPartner.getSubjectToWithholdingTax() : "N");

        /*Se valida el tipo de persona y los datos que se deben agregar dependiendo de esto*/
        if (clienteSession.getClienteDto().getTipoPersona().equals("01")) {
            businessPartner.setUBPCONombre(clienteSession.getClienteDto().getNombres().toUpperCase());
            businessPartner.setUSexo(clienteSession.getClienteDto().getSexo());
            businessPartner.setUBPCO1Apellido(clienteSession.getClienteDto().getApellido1().toUpperCase());
            businessPartner.setUBPCO2Apellido(clienteSession.getClienteDto().getApellido2().toUpperCase());

            /*Se convierte la fecha al formato admitido*/
            try {
                GregorianCalendar calendar = new GregorianCalendar();
                calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(clienteSession.getClienteDto().getBirthYear() + "-"
                        + clienteSession.getClienteDto().getBirthMonth() + "-" + clienteSession.getClienteDto().getBirthDay()));
                businessPartner.setUFechaNacimiento(DatatypeFactory.newInstance().newXMLGregorianCalendar(calendar));
            } catch (ParseException | DatatypeConfigurationException e) {
                log.log(Level.SEVERE, "Error al convertir la fecha de nacimiento del cliente. Error [{0}]", e.getMessage());
                return false;
            }
        } else if (clienteSession.getClienteDto().getTipoPersona().equals("02")) {
            boolean personaContactoExiste = false;
            if (businessPartner.getContactEmployees() != null && businessPartner.getContactEmployees().getContactEmployee() != null && !businessPartner.getContactEmployees().getContactEmployee().isEmpty()) {
                for (BusinessPartner.ContactEmployees.ContactEmployee contact : businessPartner.getContactEmployees().getContactEmployee()) {
                    if (contact.getName().equals(clienteSession.getClienteDto().getNombres())) {
                        contact.setFirstName(clienteSession.getClienteDto().getNombres().toUpperCase());
                        contact.setLastName(clienteSession.getClienteDto().getApellido1().toUpperCase());

                        personaContactoExiste = true;
                        businessPartner.setContactPerson(clienteSession.getClienteDto().getNombres().toUpperCase());
                        break;
                    } else {
                        personaContactoExiste = false;
                    }
                }
            } else {
                if (businessPartner.getContactEmployees() == null) {
                    businessPartner.setContactEmployees(new BusinessPartner.ContactEmployees());
                }

                personaContactoExiste = false;
            }

            if (!personaContactoExiste) {
                BusinessPartner.ContactEmployees.ContactEmployee contact = new BusinessPartner.ContactEmployees.ContactEmployee();

                contact.setName(clienteSession.getClienteDto().getNombres().toUpperCase());
                contact.setFirstName(clienteSession.getClienteDto().getNombres().toUpperCase());
                contact.setLastName(clienteSession.getClienteDto().getApellido1().toUpperCase());

                businessPartner.getContactEmployees().getContactEmployee().add(contact);
                businessPartner.setContactPerson(clienteSession.getClienteDto().getNombres().toUpperCase());
            }
        }

//            //Si el cliente esta sujeto a retenciones, realiza una consulta para validar
//            //cuales codigos de retencion tiene asignados y los agrega al dto.
//            if (businessPartner.getSubjectToWithholdingTax().equals("tYes")) {
//                List<TipoRetencionDTO> retencionesDto = new ArrayList();
//                List<TipoRetencionCliente> retCliente = tipoRetencionClienteFacade.findByCardCode(businessPartner.getCardCode());
//                for (TipoRetencionCliente tipoRetEntidad : retCliente) {
//                    TipoRetencionDTO tipoRetDto = new TipoRetencionDTO();
//                    tipoRetDto.setCodigo(tipoRetEntidad.getTipoRetencionClientePK().getWtCode());
//                    tipoRetDto.setBloqueado(true);
//
//                    retencionesDto.add(tipoRetDto);
//
//                }
//                cliente.setRetenciones(retencionesDto);
//            }
//            //Bloquea las retenciones que ya se hayan aplicado, para que solo sean modificables desde SAP
//            for (TipoRetencionDTO tipoRetDto : retencionesCliente) {
//                int pos = cliente.getRetenciones().indexOf(tipoRetDto);
//                if (pos >= 0) {
//                    tipoRetDto.setHabilitado(false);
//                    tipoRetDto.setValor("Y");
//                } else {
//                    tipoRetDto.setHabilitado(true);
//                    tipoRetDto.setValor("N");
//                }
//            }
        if (clienteSession.getClienteDto().getTipoPersona().equals(TIPO_PERSONA_JURIDICA) || clienteSession.getClienteDto().getTipoDocumento().equals(TIPO_DOCUMENTO_NIT)) {
            businessPartner.setFederalTaxID(clienteSession.getClienteDto().getNit().replace("CL", "") + clienteSession.getClienteDto().getDigitoVerificacion());
        } else {
            businessPartner.setFederalTaxID(clienteSession.getClienteDto().getNit().replace("CL", ""));
        }

        /*Inicia bloque de validacion de la direccion, aqui se verifica que la direccion no sea nueva; esto se valida de la siguiente manera:
                - Si el nombre de la direccion cambia, se debe entender que es una direccion nueva.
                - Si el nombre no cambia simplemente se actualiza la direccion.
            
            Nota: Las modificaciones siempre se hacen sobre la direccion estandar de facturacion.
                  Si la direccion se modifica, debe cambiar la direccion estandar de entrega tambien y los datos de direcciones en medios magneticos.
                  Si la direccion es nueva, se debe crear la direccion estandar de entrega y llenar los datos de medios magneticos.
         */
        boolean nuevaDireccionFactura = true;
        boolean nuevaDireccionEntrega = true;
        CiudadDTO ciudad = obtenerDatosCiudad(clienteSession.getDireccionCliente().getCodCiudad());

        if (ciudad != null && ciudad.getCodigo() != null && !ciudad.getCodigo().isEmpty()) {
            if (businessPartner != null && businessPartner.getCardCode() != null && !businessPartner.getCardCode().isEmpty() && businessPartner.getBPAddresses() != null
                    && businessPartner.getBPAddresses().getBPAddress() != null && !businessPartner.getBPAddresses().getBPAddress().isEmpty()) {
                for (BusinessPartner.BPAddresses.BPAddress a : businessPartner.getBPAddresses().getBPAddress()) {
                    if (a.getAddressName().equals(clienteSession.getDireccionCliente().getNombre()) && a.getAddressType().equals("bo_BillTo")) {
                        a.setBuildingFloorRoom(clienteSession.getDireccionCliente().getCelular());
                        a.setUMunicipio(clienteSession.getDireccionCliente().getCodCiudad());
                        a.setState(clienteSession.getDireccionCliente().getCodDepartamento());
                        a.setStreet(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
                        a.setCounty(clienteSession.getDireccionCliente().getEmail().toUpperCase());
                        a.setAddressName(clienteSession.getDireccionCliente().getNombre().toUpperCase());
                        a.setBlock(clienteSession.getDireccionCliente().getTelefono());
                        a.setCity(ciudad.getNombre());
                        a.setAddressType("bo_BillTo");

                        nuevaDireccionFactura = false;

                        /*Se llenan los datos de los medios magneticos que aplica para todos los tipos de persona*/
                        businessPartner.setUBPCOCity(clienteSession.getDireccionCliente().getCodCiudad());
                        businessPartner.setUBPCOCS(clienteSession.getDireccionCliente().getCodCiudad());
                        businessPartner.setUBPCOAddress(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
                        businessPartner.setEmailAddress(clienteSession.getDireccionCliente().getEmail().toUpperCase());
                        businessPartner.setCellular(clienteSession.getDireccionCliente().getCelular());
                        businessPartner.setPhone1(clienteSession.getDireccionCliente().getTelefono());
                    } else if (nuevaDireccionFactura) {
                        nuevaDireccionFactura = true;
                    }

                    if (a.getAddressName().equals(clienteSession.getDireccionCliente().getNombre()) && a.getAddressType().equals("bo_ShipTo")) {
                        a.setBuildingFloorRoom(clienteSession.getDireccionCliente().getCelular());
                        a.setUMunicipio(clienteSession.getDireccionCliente().getCodCiudad());
                        a.setState(clienteSession.getDireccionCliente().getCodDepartamento());
                        a.setStreet(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
                        a.setCounty(clienteSession.getDireccionCliente().getEmail().toUpperCase());
                        a.setAddressName(clienteSession.getDireccionCliente().getNombre().toUpperCase());
                        a.setBlock(clienteSession.getDireccionCliente().getTelefono());
                        a.setCity(ciudad.getNombre());
                        a.setAddressType("bo_ShipTo");

                        nuevaDireccionEntrega = false;
                    } else if (nuevaDireccionEntrega) {
                        nuevaDireccionEntrega = true;
                    }
                }
            }
        } else {
            nuevaDireccionFactura = true;
            nuevaDireccionEntrega = true;
        }

        if (businessPartner.getBPAddresses() == null) {
            businessPartner.setBPAddresses(new BusinessPartner.BPAddresses());
        }
        if (nuevaDireccionFactura) {
            BusinessPartner.BPAddresses.BPAddress addressFactura = new BusinessPartner.BPAddresses.BPAddress();

            addressFactura.setBuildingFloorRoom(clienteSession.getDireccionCliente().getCelular());
            addressFactura.setUMunicipio(clienteSession.getDireccionCliente().getCodCiudad());
            addressFactura.setState(clienteSession.getDireccionCliente().getCodDepartamento());
            addressFactura.setStreet(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
            addressFactura.setCounty(clienteSession.getDireccionCliente().getEmail().toUpperCase());
            addressFactura.setAddressName(clienteSession.getDireccionCliente().getNombre().toUpperCase());
            addressFactura.setBlock(clienteSession.getDireccionCliente().getTelefono());
            addressFactura.setCity(ciudad.getNombre());

            addressFactura.setAddressType("bo_BillTo");
            businessPartner.getBPAddresses().getBPAddress().add(addressFactura);

            /*Se llenan los datos de los medios magneticos que aplica para todos los tipos de persona*/
            businessPartner.setUBPCOCity(clienteSession.getDireccionCliente().getCodCiudad());
            businessPartner.setUBPCOCS(clienteSession.getDireccionCliente().getCodCiudad());
            businessPartner.setUBPCOAddress(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
            businessPartner.setEmailAddress(clienteSession.getDireccionCliente().getEmail().toUpperCase());
            businessPartner.setCellular(clienteSession.getDireccionCliente().getCelular());
            businessPartner.setPhone1(clienteSession.getDireccionCliente().getTelefono());
        }
        if (nuevaDireccionEntrega) {
            BusinessPartner.BPAddresses.BPAddress addressEntrega = new BusinessPartner.BPAddresses.BPAddress();

            addressEntrega.setBuildingFloorRoom(clienteSession.getDireccionCliente().getCelular());
            addressEntrega.setUMunicipio(clienteSession.getDireccionCliente().getCodCiudad());
            addressEntrega.setState(clienteSession.getDireccionCliente().getCodDepartamento());
            addressEntrega.setStreet(clienteSession.getDireccionCliente().getDireccion().toUpperCase());
            addressEntrega.setCounty(clienteSession.getDireccionCliente().getEmail().toUpperCase());
            addressEntrega.setAddressName(clienteSession.getDireccionCliente().getNombre().toUpperCase());
            addressEntrega.setBlock(clienteSession.getDireccionCliente().getTelefono());
            addressEntrega.setCity(ciudad.getNombre());

            addressEntrega.setAddressType("bo_ShipTo");
            businessPartner.getBPAddresses().getBPAddress().add(addressEntrega);
        }

        try {
            if (buscando) {
                if (!sessionInfo.validarPermisoUsuario(Objetos.CLIENTE, Acciones.MODIFICAR)) {
                    log.log(Level.SEVERE, "El usuario {0} no esta autorizado para modificar clientes", sessionInfo.getUsuario());
                    mostrarMensaje("Usuario no autorizado", "El usuario " + sessionInfo.getUsuario() + " no esta autorizado para modificar clientes.", true, false, false);
                    return false;
                }

                GenericRESTResponseDTO res = client.editarCliente(businessPartner);

                if (res == null || res.getEstado() == -1) {
                    mostrarMensaje("Error", "No fue posible modificar el cliente.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible modificar el cliente");
                    return false;
                }
            } else {
                if (!sessionInfo.validarPermisoUsuario(Objetos.CLIENTE, Acciones.CREAR)) {
                    log.log(Level.SEVERE, "El usuario {0} no esta autorizado para crear clientes", sessionInfo.getUsuario());
                    mostrarMensaje("Usuario no autorizado", "El usuario " + sessionInfo.getUsuario() + " no esta autorizado para crear clientes.", true, false, false);
                    return false;
                }

                GenericRESTResponseDTO res = client.crearCliente(businessPartner);

                if (res == null || res.getEstado() == -1) {
                    mostrarMensaje("Error", "No fue posible modificar el cliente.", true, false, false);
                    log.log(Level.SEVERE, "No fue posible modificar el cliente");
                    return false;
                }
            }
        } catch (Exception e) {
            if (e.getMessage().contains("No fue posible modificar el cliente.")) {
                advertencia = true;
            }
            log.log(Level.SEVERE, "Ocurrio un error al editar el cliente. ", e);
            mostrarMensaje("Ocurrió un error", "No fue posible editar el cliente. " + e.getMessage(), true, false, false);
            return false;
        }
        return true;
    }

    public void buscar() {
        //TODO: construir busqueda avanzada con campos ingresados
        if (!buscando) {
            //limpiar();
            buscando = true;
        }
        String nit = clienteSession.getClienteDto().getNit();
        if (nit == null || nit.isEmpty()) {
            mostrarMensaje("Error", "Ingrese el número del documento para poder buscar.", true, false, false);
            log.log(Level.SEVERE, "Ingrese el numero del documento para poder buscar");
            return;
        }

        if (!nit.toUpperCase().endsWith("CL")) {
            nit = nit + "CL";
        }

        obtenerDatosWebService();

        cargarRetenciones();

        if (businessPartner != null && businessPartner.getCardCode() != null && !businessPartner.getCardCode().isEmpty()) {
            /*Se mapean los datos a partir de los obtenidos del WebService*/
            ClienteSAPDTO cliente = new ClienteSAPDTO();

            cliente.setAutorretenedor(businessPartner.getUEsAutorret());
            cliente.setCodAsesor(businessPartner.getSalesPersonCode().toString());
            cliente.setEmail(businessPartner.getEmailAddress());
            cliente.setNacionalidad(businessPartner.getUBPCOBPExt());
            cliente.setNit(businessPartner.getCardCode());
            cliente.setRazonSocial(businessPartner.getCardName());
            cliente.setRegimen(businessPartner.getUBPCORTC());
            cliente.setTipoDocumento(businessPartner.getUBPCOTDC());
            cliente.setTipoPersona(businessPartner.getUBPCOTP());

            if (businessPartner.getUBPCOTP().equals("01")) {
                cliente.setNombres(businessPartner.getUBPCONombre());
                cliente.setApellido1(businessPartner.getUBPCO1Apellido());
                cliente.setApellido2(businessPartner.getUBPCO2Apellido());
                cliente.setSexo(businessPartner.getUSexo());

                if (businessPartner.getUFechaNacimiento() != null) {
                    GregorianCalendar cal = new GregorianCalendar();
                    cal.setTime(businessPartner.getUFechaNacimiento().toGregorianCalendar().getTime());
                    cliente.setBirthDay(Integer.toString(cal.get(Calendar.DATE)));
                    cliente.setBirthMonth(Integer.toString(cal.get(Calendar.MONTH) + 1));
                    cliente.setBirthYear(Integer.toString(cal.get(Calendar.YEAR)));
                }
            } else if (businessPartner.getUBPCOTP().equals("02")) {
                if (businessPartner.getContactEmployees() != null && businessPartner.getContactEmployees().getContactEmployee() != null && !businessPartner.getContactEmployees().getContactEmployee().isEmpty()) {
                    for (BusinessPartner.ContactEmployees.ContactEmployee contact : businessPartner.getContactEmployees().getContactEmployee()) {
                        if (contact.getName().equals(businessPartner.getContactPerson())) {
                            cliente.setNombres(contact.getFirstName());
                            cliente.setApellido1(contact.getLastName());
                            break;
                        }
                    }
                }
            }

//            //Si el cliente esta sujeto a retenciones, realiza una consulta para validar
//            //cuales codigos de retencion tiene asignados y los agrega al dto.
//            if (businessPartner.getSubjectToWithholdingTax().equals("tYes")) {
//                List<TipoRetencionDTO> retencionesDto = new ArrayList();
//                List<TipoRetencionCliente> retCliente = tipoRetencionClienteFacade.findByCardCode(businessPartner.getCardCode());
//                for (TipoRetencionCliente tipoRetEntidad : retCliente) {
//                    TipoRetencionDTO tipoRetDto = new TipoRetencionDTO();
//                    tipoRetDto.setCodigo(tipoRetEntidad.getTipoRetencionClientePK().getWtCode());
//                    tipoRetDto.setBloqueado(true);
//
//                    retencionesDto.add(tipoRetDto);
//
//                }
//                cliente.setRetenciones(retencionesDto);
//            }
//            //Bloquea las retenciones que ya se hayan aplicado, para que solo sean modificables desde SAP
//            for (TipoRetencionDTO tipoRetDto : retencionesCliente) {
//                int pos = cliente.getRetenciones().indexOf(tipoRetDto);
//                if (pos >= 0) {
//                    tipoRetDto.setHabilitado(false);
//                    tipoRetDto.setValor("Y");
//                } else {
//                    tipoRetDto.setHabilitado(true);
//                    tipoRetDto.setValor("N");
//                }
//            }
            //TODO: dtoSAP.setInformacionAdicional();
            if (cliente.getTipoPersona().equals(TIPO_PERSONA_JURIDICA) || cliente.getTipoDocumento().equals(TIPO_DOCUMENTO_NIT)) {
                cliente.setDigitoVerificacion(Integer.toString(calcularDigitoVerificacion(businessPartner.getCardCode().replace("CL", ""))));
            }

            clienteSession.setClienteDto(cliente);
            cargarDias();
            obtenerDireccionActual();
        }
    }

    private void obtenerDireccionActual() {
        if (businessPartner.getBilltoDefault() != null && !businessPartner.getBilltoDefault().isEmpty()) {
            for (BusinessPartner.BPAddresses.BPAddress address : businessPartner.getBPAddresses().getBPAddress()) {
                if (address.getAddressName().equals(businessPartner.getBilltoDefault()) && address.getAddressType().equals("bo_BillTo")) {
                    DireccionesClienteDTO direccion = new DireccionesClienteDTO();

                    direccion.setCelular(address.getBuildingFloorRoom());
                    direccion.setCodCiudad(address.getUMunicipio());
                    direccion.setCodDepartamento(address.getState());
                    direccion.setDireccion(address.getStreet());
                    direccion.setEmail(address.getCounty());
                    direccion.setNombre(address.getAddressName());
                    direccion.setTelefono(address.getBlock());

                    CiudadDTO c = obtenerDatosCiudad(direccion.getCodCiudad());
                    if (c != null && c.getCodigo() != null && c.getCodigo().equals(direccion.getCodCiudad())) {
                        direccion.setNombreCiudad(c.getNombre());
                        direccion.setNombreDepartamento(c.getNomDepartamento());

                        clienteSession.setCiudadBusqueda(direccion.getNombreCiudad());
                    }

                    clienteSession.setDireccionCliente(direccion);
                    break;
                }
            }
        }
    }

    private CiudadDTO obtenerDatosCiudad(String codCiudad) {
        for (CiudadDTO c : applicationBean.getCiudades()) {
            if (c.getCodigo().equals(codCiudad)) {
                return c;
            }
        }

        return null;
    }

    public void limpiar() {
        log.log(Level.INFO, "Limpiando informacion de cliente de formulario y sesion");
        clienteSession.setClienteDto(new ClienteSAPDTO());
        clienteSession.setDireccionCliente(new DireccionesClienteDTO());
        clienteSession.setCiudadBusqueda(null);
        dias = new ArrayList<>();
        buscando = false;
        advertencia = false;
        businessPartner = new BusinessPartner();
        cargarRetenciones();
    }

    public String clienteTieneRetencion(String codigoRetencion) {
        for (TipoRetencionDTO retencionDto : clienteSession.getClienteDto().getRetenciones()) {
            if (retencionDto.getCodigo().equals(codigoRetencion)) {
                return "1";
            }
        }
        return "0";
    }

    public void seleccionarTipoPersona() {
        String tipoPersona = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoPersona");

        clienteSession.getClienteDto().setTipoPersona(tipoPersona);
        log.log(Level.INFO, "Se selecciono el Tipo Persona [{0}]", tipoPersona);
        if (clienteSession.getClienteDto().getTipoPersona().equals(TIPO_PERSONA_JURIDICA)) {
            clienteSession.getClienteDto().setDigitoVerificacion(Integer.toString(calcularDigitoVerificacion(clienteSession.getClienteDto().getNit().replace("CL", ""))));
            log.log(Level.INFO, "Calculando digito de verificacion");
        } else {
            clienteSession.getClienteDto().setDigitoVerificacion(null);
        }
    }

    public void seleccionarTipoDocumento() {
        String tipoDocumento = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("tipoDocumento");

        clienteSession.getClienteDto().setTipoDocumento(tipoDocumento);
        log.log(Level.INFO, "Se selecciono el Tipo Documento [{0}]", tipoDocumento);

        if (clienteSession.getClienteDto().getTipoPersona().equals(TIPO_PERSONA_JURIDICA)
                || clienteSession.getClienteDto().getTipoDocumento().equals(TIPO_DOCUMENTO_NIT)) {
            //calcular digito de verificacion
            clienteSession.getClienteDto().setDigitoVerificacion(Integer.toString(calcularDigitoVerificacion(clienteSession.getClienteDto().getNit())));
        } else {
            clienteSession.getClienteDto().setDigitoVerificacion("");
        }
    }

    public void seleccionarNacionalidad() {
        String nacionalidad = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("nacionalidad");

        clienteSession.getClienteDto().setNacionalidad(nacionalidad);
        log.log(Level.INFO, "Se selecciono la nacionalidad [{0}]", nacionalidad);
    }

    public void seleccionarYear() {
        Integer year = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("year"));

        clienteSession.getClienteDto().setBirthYear(year.toString());
        log.log(Level.INFO, "Se selecciono el ano de nacimiento [{0}]", year);
    }

    public void seleccionarMonth() {
        Integer month = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("month"));

        clienteSession.getClienteDto().setBirthMonth(month.toString());
        log.log(Level.INFO, "Se selecciono el mes de nacimiento [{0}]", month);
        cargarDias();
        log.log(Level.INFO, "Se estan cargando los dias correspondiente al mes. Se obtuvieron [{0}] dias", dias.size());
    }

    public void seleccionarDay() {
        Integer day = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("day"));

        clienteSession.getClienteDto().setBirthDay(day.toString());
        log.log(Level.INFO, "Se selecciono el dia de nacimiento [{0}]", day);
    }

    public void seleccionarGenero() {
        Integer genero = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("genero"));

        clienteSession.getClienteDto().setSexo(genero.toString());
        log.log(Level.INFO, "Se selecciono el genero [{0}]", genero);
    }

    public String crearCotizacion() {
        if (guardarClienteSAP()) {
            return cotizar();
        } else {
            log.log(Level.SEVERE, "No se puede crear la cotizacion, debido a que ocurrio un error al guardar los datos del cliente");
            mostrarMensaje("Error", "No se puede crear la cotización, debido a que ocurrio un error al guardar los datos del cliente.", true, false, false);
            return null;
        }
    }

    private String cotizar() {
        /*Se crea el encabezado de la cotizacion*/
        QuotationsDocumentDTO quotation = new QuotationsDocumentDTO();

        quotation.setCardCode(clienteSession.getClienteDto().getNit());
        quotation.setSalesPersonCode(Long.parseLong(sessionInfo.getCodigoVentas()));

        if (ventasSessionMBean.getCotizacion() != null && ventasSessionMBean.getCotizacion().getDetalle() != null
                && !ventasSessionMBean.getCotizacion().getDetalle().isEmpty()) {

            for (DetalleCotizacionWebDTO detail : ventasSessionMBean.getCotizacion().getDetalle()) {
                QuotationsDocumentLinesDTO detailLine = new QuotationsDocumentLinesDTO();

                detailLine.setItemCode(detail.getReferencia());
                detailLine.setQuantity(detail.getCantidad());
                detailLine.setWarehouseCode(detail.getBodega());
                if (detail.getPrecioUnitario() > 0) {
                    detailLine.setUnitPrice(new BigDecimal(detail.getPrecioUnitario()));
                }

                quotation.getQuotationsDocumentLines().add(detailLine);
            }
        } else {
            log.log(Level.SEVERE, "No se puede continuar debido a que no se encontraron datos");
            mostrarMensaje("Error", "No se puede continuar debido a que no se encontraron datos.", true, false, false);
            return null;
        }

        try {
            QuotationsClient client = new QuotationsClient(applicationBean.obtenerValorPropiedad("url.bcs.rest"));

            GenericRESTResponseDTO res = client.createQuotation(quotation);

            if (res != null && res.getEstado() == 1) {
                log.log(Level.INFO, "Se creo la cotizacion con numero: [{0}]", res.getValor());

                ventasSessionMBean.setNumeroCotizacion(res.getValor().longValue());
                ventasSessionMBean.setExitoCotizacion(true);
                ventasSessionMBean.getCotizacion().setNitCliente(clienteSession.getClienteDto().getNit());
                ventasSessionMBean.setCotizacionCorrompida(false);
                limpiar();
                if (ventasSessionMBean.isCotizacionEspecial()) {
                    return "cotizacionEspecial";
                }
                return "cotizaciones";
            } else {
                mostrarMensaje("Error", "Ocurrio un error al crear la cotización.", true, false, false);
            }
        } catch (Exception e) {
            log.log(Level.SEVERE, "", e);
        }
        return null;
    }

    public String volverACotizaciones() {
        clienteSession.setClienteDto(new ClienteSAPDTO());
        clienteSession.setDireccionCliente(new DireccionesClienteDTO());
        ventasSessionMBean.setClienteVerificado(false);
        ventasSessionMBean.setDemostracion(false);

        if (notaCredito) {
            ventasSessionMBean.setDocumentoCliente(clienteSession.getClienteDto().getNit());
            return "crearNota";
        }
        if (ventasSessionMBean.isCotizacionEspecial()) {
            return "cotizacionEspecial";
        }

        return "cotizaciones";
    }

    public String continuarProceso() {
        if (guardarClienteSAP()) {
            ventasSessionMBean.setClienteVerificado(true);
            if (demostracion) {
                ventasSessionMBean.setDocumentoCliente(clienteSession.getClienteDto().getNit());
            } else if (facturando) {
                ventasSessionMBean.setFacturando(true);
            } else if (notaCredito) {
                ventasSessionMBean.setDocumentoCliente(clienteSession.getClienteDto().getNit());
                return "crearNota";
            }
            return "cotizaciones";
        }
        ventasSessionMBean.setClienteVerificado(false);
        return "";
    }

    public String cancelarFactura() {
        return null;
    }

    public String continuar() {
        if (cotizacion) {
            return cotizar();
        } else if (facturando || demostracion) {
            if (demostracion) {
                ventasSessionMBean.setDocumentoCliente(clienteSession.getClienteDto().getNit());
            } else if (facturando) {
                ventasSessionMBean.setFacturando(true);
            }
            ventasSessionMBean.setClienteVerificado(true);
            return "cotizaciones";
        }
        limpiar();
        return null;
    }

    public void cancelarContinuacion() {
        advertencia = false;
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
