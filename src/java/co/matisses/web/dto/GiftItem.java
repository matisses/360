package co.matisses.web.dto;

/**
 *
 * @author dbotero
 */
public class GiftItem {

    private String itemCode;
    private Integer quantity;

    public GiftItem() {

    }

    public GiftItem(String itemCode, Integer quantity) {
        this.itemCode = itemCode;
        this.quantity = quantity;
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

    @Override
    public String toString() {
        return "GiftItem [itemCode=" + itemCode + ", quantity=" + quantity + "]";
    }
}
