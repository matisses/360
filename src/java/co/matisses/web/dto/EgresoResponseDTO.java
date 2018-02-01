package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class EgresoResponseDTO {

    private String codigo;
    private String mensaje;

    public EgresoResponseDTO() {
    }

    public EgresoResponseDTO(String codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

}
