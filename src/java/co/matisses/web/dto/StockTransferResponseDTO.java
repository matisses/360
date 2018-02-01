package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class StockTransferResponseDTO {

    private Integer codigo;
    private Integer docEntry;
    private String mensaje;

    public StockTransferResponseDTO() {
    }

    public StockTransferResponseDTO(Integer codigo, String mensaje) {
        this.codigo = codigo;
        this.mensaje = mensaje;
    }

    public StockTransferResponseDTO(Integer codigo, Integer docEntry) {
        this.codigo = codigo;
        this.docEntry = docEntry;
    }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
