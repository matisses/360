package co.matisses.web.dto;

import java.util.List;

/**
 *
 * @author dbotero
 */
public class ListaFacturasAnulablesResponseDTO {
    private String code;
    private String errorMessage;
    private List<VentaPOSDTO> facturas;

    public ListaFacturasAnulablesResponseDTO(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

    public ListaFacturasAnulablesResponseDTO(List<VentaPOSDTO> facturas) {
        this.facturas = facturas;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<VentaPOSDTO> getFacturas() {
        return facturas;
    }

    public void setFacturas(List<VentaPOSDTO> facturas) {
        this.facturas = facturas;
    }
}
