package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class DireccionesClienteDTO {

    private Integer lineNumSAP;
    private String nit;
    private String nombre;
    private String direccion;
    private String codCiudad;
    private String nombreCiudad;
    private String codDepartamento;
    private String nombreDepartamento;
    private String telefono;
    private String celular;
    private String email;
    private String tipo;
    private Long idDireccion;
    private boolean vieneDesdeSAP;

    public DireccionesClienteDTO() {
    }

    public DireccionesClienteDTO(Integer lineNumSAP, String nit, String nombre, String direccion, String codCiudad, String nombreCiudad, String codDepartamento, String nombreDepartamento, String telefono, String celular, String email, String tipo, Long idDireccion) {
        this.lineNumSAP = lineNumSAP;
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codCiudad = codCiudad;
        this.nombreCiudad = nombreCiudad;
        this.codDepartamento = codDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.tipo = tipo;
        this.idDireccion = idDireccion;
    }

    public DireccionesClienteDTO(String nit, String nombre, String direccion, String codCiudad, String nombreCiudad, String codDepartamento, String nombreDepartamento, String telefono, String celular, String email, String tipo) {
        this.nit = nit;
        this.nombre = nombre;
        this.direccion = direccion;
        this.codCiudad = codCiudad;
        this.nombreCiudad = nombreCiudad;
        this.codDepartamento = codDepartamento;
        this.nombreDepartamento = nombreDepartamento;
        this.telefono = telefono;
        this.celular = celular;
        this.email = email;
        this.tipo = tipo;
    }

    public Integer getLineNumSAP() {
        return lineNumSAP;
    }

    public void setLineNumSAP(Integer lineNumSAP) {
        this.lineNumSAP = lineNumSAP;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCodCiudad() {
        return codCiudad;
    }

    public void setCodCiudad(String codCiudad) {
        this.codCiudad = codCiudad;
    }

    public String getNombreCiudad() {
        return nombreCiudad;
    }

    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }

    public String getCodDepartamento() {
        return codDepartamento;
    }

    public void setCodDepartamento(String codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = nombreDepartamento;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Long getIdDireccion() {
        return idDireccion;
    }

    public void setIdDireccion(Long idDireccion) {
        this.idDireccion = idDireccion;
    }

    public boolean isVieneDesdeSAP() {
        return vieneDesdeSAP;
    }

    public void setVieneDesdeSAP(boolean vieneDesdeSAP) {
        this.vieneDesdeSAP = vieneDesdeSAP;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + (this.idDireccion != null ? this.idDireccion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DireccionesClienteDTO other = (DireccionesClienteDTO) obj;
        if (!Objects.equals(this.idDireccion, other.idDireccion)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DireccionesClienteDTO{" + "lineNumSAP=" + lineNumSAP + ", nit=" + nit + ", nombre=" + nombre + ", direccion=" + direccion + ", codCiudad=" + codCiudad + ", nombreCiudad=" + nombreCiudad + ", codDepartamento=" + codDepartamento + ", nombreDepartamento=" + nombreDepartamento + ", telefono=" + telefono + ", celular=" + celular + ", email=" + email + ", tipo=" + tipo + ", idDireccion=" + idDireccion + '}';
    }
}
