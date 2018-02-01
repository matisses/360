package co.matisses.web.dto;

/**
 *
 * @author jguisao
 */
public class DirectorioDTO {

    private String celular;
    private String nombre;
    private String cargo;
    private String extension;
    private String correo;
    private String departamento;
    private String skype;
    private String sucursal;
    private EmpleadoDTO empleado;

    public DirectorioDTO() {
    }

    public DirectorioDTO(String celular, String nombre, String cargo, String extension, String correo, String departamento, EmpleadoDTO empleado, String skype, String sucursal) {
        this.celular = celular;
        this.nombre = nombre;
        this.cargo = cargo;
        this.extension = extension;
        this.correo = correo;
        this.departamento = departamento;
        this.empleado = empleado;
        this.skype = skype;
        this.sucursal = sucursal;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getNombre() {
        String nombreCorto;
        if (nombre.length() >= 36) {
            nombreCorto = nombre.substring(0, 33) + "...";
        } else {
            nombreCorto = nombre;
        }
        return nombreCorto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getExtension() {
        String extCorta;
        if (extension.length() >= 36) {
            extCorta = extension.substring(0, 33) + "...";
        } else {
            extCorta = extension;
        }
        return extCorta;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public EmpleadoDTO getEmpleado() {
        return empleado;
    }

    public void setEmpleado(EmpleadoDTO empleado) {
        this.empleado = empleado;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getSucursal() {
        return sucursal;
    }

    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
}