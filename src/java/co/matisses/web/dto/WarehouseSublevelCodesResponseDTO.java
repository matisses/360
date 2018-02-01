package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class WarehouseSublevelCodesResponseDTO {

    private Integer codigo;
    private Integer absEntry;
    private String mensaje;

    public WarehouseSublevelCodesResponseDTO() {
    }

    public WarehouseSublevelCodesResponseDTO(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public WarehouseSublevelCodesResponseDTO(Integer codigo, Integer absEntry) {
        this.codigo = codigo;
        this.absEntry = absEntry;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
