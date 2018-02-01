package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class DetalleUbicacionFacturaDTO {
    private String itemCode;
    private Integer quantity;

    public DetalleUbicacionFacturaDTO() {
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
