package co.matisses.web.dto.epson;

import java.util.Objects;

public class InvoiceDetail {

    private String itemCode;
    private String itemName;
    private Integer quantity;
    private Integer price;

    public InvoiceDetail() {
    }

    public InvoiceDetail(String itemCode, String itemName, Integer quantity, Integer price) {
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.quantity = quantity;
        this.price = price;
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Objects.hashCode(this.itemCode);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoiceDetail other = (InvoiceDetail) obj;
        if (!Objects.equals(this.itemCode, other.itemCode)) {
            return false;
        }
        return true;
    }    

    @Override
    public String toString() {
        return "InvoiceDetail [itemCode=" + itemCode + ", itemName=" + itemName + ", quantity=" + quantity + ", price="
                + price + "]";
    }
}
