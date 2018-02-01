package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class ValidarPermisosDTO {

    private String usuario;
    private String clave;
    private String accion;
    private String objeto;

    public ValidarPermisosDTO() {
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}
