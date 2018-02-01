package co.matisses.web.rest.regalos.dto;

import co.matisses.web.rest.regalos.dto.PaginacionConsultaListasUsuarioDTO;
import co.matisses.web.ObjectUtils;

/**
 *
 * @author dbotero
 */
public class ConsultaListasDTO {

    private String nombre;
    private String apellido;
    private String codigo;
    private String cedula;
    private String idSesion;
    private PaginacionConsultaListasUsuarioDTO paginacion;

    public ConsultaListasDTO() {
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(String idSesion) {
        this.idSesion = idSesion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public PaginacionConsultaListasUsuarioDTO getPaginacion() {
        return paginacion;
    }

    public void setPaginacion(PaginacionConsultaListasUsuarioDTO paginacion) {
        this.paginacion = paginacion;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
