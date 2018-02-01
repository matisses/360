package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class ClienteSAPDTO {

    private String nit;
    private String digitoVerificacion;
    private String nombres;
//    private String segundoNombre;
    private String apellido1;
    private String apellido2;
    private String autorretenedor;
    private String razonSocial;
    private String direccion;
    private String telefono;
    private String celular;
    private String email;
    private String departamento;
    private String codDepartamento;
    private String ciudad;
    private String codCiudad;
    private String estado;
    private String codAsesor;
    private String direccionFacEstandar;
    private String direccionEntEstandar;
    private String sexo;
    private String tipoCliente;
    private String tipoPersona;
    private String tipoDocumento;
    private String birthYear;
    private String birthMonth;
    private String birthDay;
    private String regimen;
    private String nacionalidad;
    private String tipoExtranjero;
    private String usuario;
    private Date fechaNacimiento;
    private boolean informacionAdicional;
    private boolean traeInfoAdicionalDesdeSAP;
    private List<DireccionesClienteDTO> direcciones;
    private List<TipoRetencionDTO> retenciones;

    public ClienteSAPDTO() {
        nit = "";
        digitoVerificacion = "";
        nombres = "";
//        segundoNombre = "";
        apellido1 = "";
        apellido2 = "";
        autorretenedor = "";
        razonSocial = "";
        direccion = "";
        telefono = "";
        celular = "";
        email = "";
        departamento = "";
        codDepartamento = "";
        ciudad = "";
        codCiudad = "";
        estado = "";
        codAsesor = "";
        direccionFacEstandar = "";
        direccionEntEstandar = "";
        sexo = "";
        tipoPersona = "";
        tipoDocumento = "";
        birthYear = "";
        birthMonth = "";
        birthDay = "";
        regimen = "";
        nacionalidad = "";
        fechaNacimiento = new GregorianCalendar(1900, 0, 1).getTime();
        informacionAdicional = false;
        traeInfoAdicionalDesdeSAP = false;
        tipoCliente = "";
        usuario = "";
        direcciones = new ArrayList<>();
        retenciones = new ArrayList<>();
    }

    public ClienteSAPDTO(String nit, String razonSocial) {
        this.nit = nit;
        digitoVerificacion = "";
        nombres = "";
//        segundoNombre = "";
        apellido1 = "";
        apellido2 = "";
        autorretenedor = "";
        this.razonSocial = razonSocial;
        direccion = "";
        telefono = "";
        celular = "";
        email = "";
        departamento = "";
        codDepartamento = "";
        ciudad = "";
        codCiudad = "";
        estado = "";
        codAsesor = "";
        direccionFacEstandar = "";
        direccionEntEstandar = "";
        sexo = "";
        tipoPersona = "";
        tipoDocumento = "";
        birthYear = "";
        birthMonth = "";
        birthDay = "";
        regimen = "";
        nacionalidad = "";
        fechaNacimiento = new GregorianCalendar(1900, 0, 1).getTime();
        informacionAdicional = false;
        traeInfoAdicionalDesdeSAP = false;
        tipoCliente = "";
        usuario = "";
        direcciones = new ArrayList<>();
        retenciones = new ArrayList<>();
    }

    public ClienteSAPDTO(String nit, String tipoCliente, String nombres, String apellido1, String apellido2, String razonSocial, String direccion, String telefono,
            String celular, String email, String departamento, String codDepartamento, String ciudad, String codCiudad, String estado, String codAsesor,
            String direccionFacEstandar, String direccionEntEstandar, String sexo, String tipoPersona, String tipoDocumento, String usuario, Date fechaNacimiento) {
        this.nit = nit;
        this.tipoCliente = tipoCliente;
        this.nombres = nombres;
//        this.segundoNombre = segundoNombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.razonSocial = razonSocial;
        this.direccion = direccion;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.ciudad = ciudad;
        this.departamento = departamento;
        this.codDepartamento = codDepartamento;
        this.codCiudad = codCiudad;
        this.estado = estado;
        this.codAsesor = codAsesor;
        this.direccionFacEstandar = direccionFacEstandar;
        this.direccionEntEstandar = direccionEntEstandar;
        this.sexo = sexo;
        this.tipoPersona = tipoPersona;
        this.tipoDocumento = tipoDocumento;
        this.fechaNacimiento = fechaNacimiento;
        this.usuario = usuario;
        direcciones = new ArrayList<>();
        retenciones = new ArrayList<>();
    }

    public String getDigitoVerificacion() {
        return digitoVerificacion;
    }

    public void setDigitoVerificacion(String digitoVerificacion) {
        if (digitoVerificacion == null) {
            this.digitoVerificacion = "";
        } else {
            this.digitoVerificacion = digitoVerificacion;
        }
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        if (usuario == null) {
            this.usuario = "";
        } else {
            this.usuario = usuario;
        }
    }

    public String getCodAsesor() {
        return codAsesor;
    }

    public void setCodAsesor(String codAsesor) {
        if (codAsesor == null) {
            this.codAsesor = "";
        } else {
            this.codAsesor = codAsesor;
        }
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        if (estado == null) {
            this.estado = "";
        } else {
            this.estado = estado;
        }
    }

    public String getTipoCliente() {
        return tipoCliente;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        if (departamento == null) {
            this.departamento = "";
        } else {
            this.departamento = departamento;
        }
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        if (codDepartamento == null) {
            this.codDepartamento = "";
        } else {
            this.codDepartamento = codDepartamento;
        }
    }

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        if (codCiudad == null) {
            this.codCiudad = "";
        } else {
            this.codCiudad = codCiudad;
        }
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        if (ciudad == null) {
            this.ciudad = "";
        } else {
            this.ciudad = ciudad;
        }
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        if (nit == null) {
            this.nit = "";
        } else {
            this.nit = nit;
        }
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        if (nombres == null) {
            this.nombres = "";
        } else {
            this.nombres = nombres;
        }
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        if (apellido1 == null) {
            this.apellido1 = "";
        } else {
            this.apellido1 = apellido1;
        }
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        if (apellido2 == null) {
            this.apellido2 = "";
        } else {
            this.apellido2 = apellido2;
        }
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        if (razonSocial == null) {
            this.razonSocial = "";
        } else {
            this.razonSocial = razonSocial;
        }
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        if (direccion == null) {
            this.direccion = "";
        } else {
            this.direccion = direccion;
        }
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        if (telefono == null) {
            this.telefono = "";
        } else {
            this.telefono = telefono;
        }
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        if (celular == null) {
            this.celular = "";
        } else {
            this.celular = celular;
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            this.email = "";
        } else {
            this.email = email;
        }
    }

    public String getRegimen() {
        return regimen;
    }

    public void setRegimen(String regimen) {
        if (regimen == null) {
            this.regimen = "";
        } else {
            this.regimen = regimen;
        }
    }

    public String getDireccionFacEstandar() {
        return direccionFacEstandar;
    }

    public void setDireccionFacEstandar(String direccionFacEstandar) {
        if (direccionFacEstandar == null) {
            this.direccionFacEstandar = "";
        } else {
            this.direccionFacEstandar = direccionFacEstandar;
        }
    }

    public String getDireccionEntEstandar() {
        return direccionEntEstandar;
    }

    public void setDireccionEntEstandar(String direccionEntEstandar) {
        if (direccionEntEstandar == null) {
            this.direccionEntEstandar = "";
        } else {
            this.direccionEntEstandar = direccionEntEstandar;
        }
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        if (sexo == null) {
            this.sexo = "";
        } else {
            this.sexo = sexo;
        }
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        if (fechaNacimiento == null) {
            this.fechaNacimiento = new Date();
        } else {
            this.fechaNacimiento = fechaNacimiento;
        }
    }

    public String getBirthDay() {
        return birthDay;
    }

    public void setBirthDay(String birthDay) {
        if (birthDay == null) {
            this.birthDay = "";
        } else {
            try {
                this.birthDay = birthDay;
                if (birthYear != null && birthMonth != null && birthDay != null) {
                    Calendar cal = new GregorianCalendar(Integer.parseInt(birthYear), Integer.parseInt(birthMonth) - 1, Integer.parseInt(birthDay));
                    fechaNacimiento = cal.getTime();
                }
            } catch (Exception e) {
            }
        }
    }

    public String getBirthMonth() {
        return birthMonth;
    }

    public void setBirthMonth(String birthMonth) {
        if (birthMonth == null) {
            this.birthMonth = "";
        } else {
            this.birthMonth = birthMonth;
        }
    }

    public String getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(String birthYear) {
        if (birthYear == null) {
            this.birthYear = "";
        } else {
            this.birthYear = birthYear;
        }
    }

    public String getTipoPersona() {
        return tipoPersona;
    }

    public void setTipoPersona(String tipoPersona) {
        if (tipoPersona == null) {
            this.tipoPersona = "";
        } else {
            this.tipoPersona = tipoPersona;
        }
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        if (tipoDocumento == null) {
            this.tipoDocumento = "";
        } else {
            this.tipoDocumento = tipoDocumento;
        }
    }

    public boolean isInformacionAdicional() {
        return informacionAdicional;
    }

    public void setInformacionAdicional(boolean informacionAdicional) {
        this.informacionAdicional = informacionAdicional;
    }

    public boolean isTraeInfoAdicionalDesdeSAP() {
        return traeInfoAdicionalDesdeSAP;
    }

    public void setTraeInfoAdicionalDesdeSAP(boolean traeInfoAdicionalDesdeSAP) {
        this.traeInfoAdicionalDesdeSAP = traeInfoAdicionalDesdeSAP;
    }

    public String getAutorretenedor() {
        return autorretenedor;
    }

    public void setAutorretenedor(String autorretenedor) {
        if (autorretenedor == null) {
            this.autorretenedor = "";
        } else {
            this.autorretenedor = autorretenedor;
        }
    }

    public String getTipoExtranjero() {
        return tipoExtranjero;
    }

    public void setTipoExtranjero(String tipoExtranjero) {
        this.tipoExtranjero = tipoExtranjero;
    }

    public List<DireccionesClienteDTO> getDirecciones() {
        return direcciones;
    }

    public void setDirecciones(List<DireccionesClienteDTO> direcciones) {
        if (direcciones == null) {
            this.direcciones = new ArrayList<>();
        } else {
            this.direcciones = direcciones;
        }
    }

    public List<TipoRetencionDTO> getRetenciones() {
        return retenciones;
    }

    public void setRetenciones(List<TipoRetencionDTO> retenciones) {
        if (retenciones == null) {
            this.retenciones = new ArrayList<>();
        } else {
            this.retenciones = retenciones;
        }
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        if (nacionalidad == null) {
            this.nacionalidad = "";
        } else {
            this.nacionalidad = nacionalidad;
        }
    }

    public void agregarDireccion(DireccionesClienteDTO dto) {
        boolean existe = false;
        for (DireccionesClienteDTO d : direcciones) {
            if (d.getNombre().equals(dto.getNombre()) && d.getTipo().equals(dto.getTipo())) {
                existe = true;
                break;
            }
        }
        if (!existe) {
            direcciones.add(dto);
        }
    }

    public boolean eliminarDireccion(DireccionesClienteDTO dto) {
        for (int i = 0; i < direcciones.size(); i++) {
            DireccionesClienteDTO d = direcciones.get(i);
            if (d.getNombre().equals(dto.getNombre()) && d.getTipo().equals(dto.getTipo()) && d.getIdDireccion() != null && d.getIdDireccion() > 0) {
                direcciones.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean contieneDireccion(DireccionesClienteDTO dto) {
        for (DireccionesClienteDTO d : direcciones) {
            if (d.getNombre().equals(dto.getNombre()) && d.getTipo().equals(dto.getTipo())) {
                return true;
            }
        }
        return false;
    }

    public Integer obtenerNumeroLineaSAP(String nombre, String tipo) {
        for (DireccionesClienteDTO d : direcciones) {
            if (d.getNombre().equals(nombre) && d.getTipo().equals(tipo)) {
                return d.getLineNumSAP();
            }
        }
        return null;
    }

    public void agregarRetencion(TipoRetencionDTO dto) {
        if (!retenciones.contains(dto)) {
            retenciones.add(dto);
        }
    }

    public boolean validarDigitoVerificacion() {
        int digito_chequeo = -1;
        int li_peso[] = {71, 67, 59, 53, 47, 43, 41, 37, 29, 23, 19, 17, 13, 7, 3};
        int li_suma = 0;
        String strNit = nit;
        if (strNit != null && strNit.trim().length() > 0) {
            while (strNit.length() < 15) {
                strNit = "0" + strNit;
            }
            try {
                for (int i = 0; i < 15; i++) {
                    li_suma += (new Integer(strNit.substring(i, i + 1))) * li_peso[i];
                }
                digito_chequeo = li_suma % 11;
                if (digito_chequeo >= 2) {
                    digito_chequeo = 11 - digito_chequeo;
                }
            } catch (Exception e) {

            }
        } else {
            return false;
        }
        return Integer.toString(digito_chequeo).equals(digitoVerificacion);
    }

    public String armarFechaNacimiento() {
        if (birthYear == null || birthMonth == null || birthDay == null) {
            return null;
        }
        return birthYear + "-" + birthMonth + "-" + birthDay;
    }

    public void configurarFechaNacimientoNula() {
        birthYear = "1900";
        birthMonth = "1";
        birthDay = "1";
    }

    public List<String> getCorreos() {
        List<String> correos = new ArrayList<>();
        for (DireccionesClienteDTO dir : direcciones) {
            if (dir.getEmail() != null && !dir.getEmail().trim().isEmpty() && !correos.contains(dir.getEmail())) {
                correos.add(dir.getEmail());
            }
        }
        Collections.sort(correos);
        return correos;
    }

    public List<String> getDireccionesFacturacion() {
        List<String> direccionesFactura = new ArrayList<>();
        for (DireccionesClienteDTO dir : direcciones) {
            if (dir.getTipo().equals("F") && dir.getDireccion() != null && !dir.getDireccion().trim().isEmpty() && !direccionesFactura.contains(dir.getNombre())) {
                direccionesFactura.add(dir.getNombre());
            }
        }
        Collections.sort(direccionesFactura);
        return direccionesFactura;
    }

    public List<String> getDireccionesEntrega() {
        List<String> direccionesEntrega = new ArrayList<>();
        for (DireccionesClienteDTO dir : direcciones) {
            if (dir.getTipo().equals("E") && dir.getDireccion() != null && !dir.getDireccion().trim().isEmpty() && !direccionesEntrega.contains(dir.getNombre())) {
                direccionesEntrega.add(dir.getNombre());
            }
        }
        Collections.sort(direccionesEntrega);
        return direccionesEntrega;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
