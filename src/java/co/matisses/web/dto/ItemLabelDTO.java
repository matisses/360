package co.matisses.web.dto;

import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class ItemLabelDTO {

    private String itemCode;
    private String itemName;
    private String provCode;
    private String price;
    private String type;
    private int quantity;

    public ItemLabelDTO() {
    }

    public ItemLabelDTO(String itemCode, String itemName, String provCode, String price, String type, int quantity) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.provCode = provCode;
        this.price = price;
        this.type = type;
        this.quantity = quantity;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getProvCode() {
        return provCode;
    }

    public void setProvCode(String provCode) {
        this.provCode = provCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ItemLabelDTO{" + "itemCode=" + itemCode + ", itemName=" + itemName + ", provCode=" + provCode + ", price=" + price + ", type=" + type + ", quantity=" + quantity + '}';
    }

}
