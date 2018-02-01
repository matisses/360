package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class DatosProveedorDTO {

    private String codProveedor;
    private String razonSocial;
    private String nombreSocioNegocios;
    private String direccion;
    private String telefono;
    private String correo;
    private String personaContacto;
    private String logo;
    private String nombreMostrar;
    private Integer estado;

    public DatosProveedorDTO() {
    }

    public DatosProveedorDTO(String codProveedor, String razonSocial, String nombreSocioNegocios, String direccion, String telefono,
            String correo, String personaContacto, String logo, Integer estado) {
        this.codProveedor = codProveedor;
        this.razonSocial = razonSocial;
        this.nombreSocioNegocios = nombreSocioNegocios;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.personaContacto = personaContacto;
        this.logo = logo;
        this.estado = estado;
    }

    public DatosProveedorDTO(String codProveedor, String razonSocial, String nombreSocioNegocios, String direccion, String telefono,
            String correo, String personaContacto, String logo, Integer estado, String nombreMostrar) {
        this.codProveedor = codProveedor;
        this.razonSocial = razonSocial;
        this.nombreSocioNegocios = nombreSocioNegocios;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.personaContacto = personaContacto;
        this.logo = logo;
        this.estado = estado;
        this.nombreMostrar = nombreMostrar;
    }

    public String getCodProveedor() {
        return codProveedor;
    }

    public void setCodProveedor(String codProveedor) {
        this.codProveedor = codProveedor;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getNombreSocioNegocios() {
        return nombreSocioNegocios;
    }

    public void setNombreSocioNegocios(String nombreSocioNegocios) {
        this.nombreSocioNegocios = nombreSocioNegocios;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPersonaContacto() {
        return personaContacto;
    }

    public void setPersonaContacto(String personaContacto) {
        this.personaContacto = personaContacto;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getNombreMostrar() {
        return nombreMostrar;
    }

    public void setNombreMostrar(String nombreMostrar) {
        this.nombreMostrar = nombreMostrar;
    }
}
