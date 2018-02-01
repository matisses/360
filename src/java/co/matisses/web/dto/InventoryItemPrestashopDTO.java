package co.matisses.web.dto;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dbotero
 */
@XmlRootElement
public class InventoryItemPrestashopDTO {
    private ItemColorDTO color;
    private ItemBrandDTO brand;
    private String description;
    private String depth;
    private String height;
    private String imgAlt;
    private String itemCode;
    private String itemName;
    private String keyWords;
    private String mainCombination;
    private String model;
    private String newFrom;
    private String price;
    private String providerCode;
    private String processImages;
    private String shortDescription;
    private String subgroupCode;
    private String webName;
    private String weight;
    private String width;
    private List<ItemStockDTO> stock;
    private List<ItemMaterialDTO> materials;

    public InventoryItemPrestashopDTO() {
        stock = new ArrayList<>();
        materials = new ArrayList<>();
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public ItemColorDTO getColor() {
        return color;
    }

    public void setColor(ItemColorDTO color) {
        this.color = color;
    }

    public String getImgAlt() {
        return imgAlt;
    }

    public void setImgAlt(String imgAlt) {
        this.imgAlt = imgAlt;
    }

    public ItemBrandDTO getBrand() {
        return brand;
    }

    public void setBrand(ItemBrandDTO brand) {
        this.brand = brand;
    }

    public void setDepth(String depth) {
        this.depth = depth;
    }

    public String getDepth() {
        return depth;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getWidth() {
        return width;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getHeight() {
        return height;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

    public String getKeyWords() {
        return keyWords;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }

    public String getWebName() {
        return webName;
    }

    public void setWebName(String webName) {
        this.webName = webName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getMainCombination() {
        return mainCombination;
    }

    public void setMainCombination(String mainCombination) {
        this.mainCombination = mainCombination;
    }

    public String getProcessImages() {
        return processImages;
    }

    public void setProcessImages(String processImages) {
        this.processImages = processImages;
    }

    public String getSubgroupCode() {
        return subgroupCode;
    }

    public void setSubgroupCode(String subgroupCode) {
        this.subgroupCode = subgroupCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public List<ItemStockDTO> getStock() {
        return stock;
    }

    public void setStock(List<ItemStockDTO> stock) {
        this.stock = stock;
    }

    public String getNewFrom() {
        return newFrom;
    }

    public void setNewFrom(String newFrom) {
        this.newFrom = newFrom;
    }

    public List<ItemMaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ItemMaterialDTO> materials) {
        this.materials = materials;
    }

    public String toXML() throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(InventoryItemPrestashopDTO.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        m.marshal(this, sw);
        return sw.toString();
    }

    public static InventoryItemPrestashopDTO fromXML(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(InventoryItemPrestashopDTO.class);
        Unmarshaller un = context.createUnmarshaller();
        return (InventoryItemPrestashopDTO) un.unmarshal(new StringReader(xml));
    }

    @Override
    public String toString() {
        return "InventoryItemPrestashopDTO{" + "color=" + color + ", brand=" + brand + ", description=" + description + ", depth=" + depth + ", height=" + height + ", itemCode=" + itemCode + ", itemName=" + itemName + ", keyWords=" + keyWords + ", mainCombination=" + mainCombination + ", model=" + model + ", newFrom=" + newFrom + ", price=" + price + ", providerCode=" + providerCode + ", processImages=" + processImages + ", shortDescription=" + shortDescription + ", subgroupCode=" + subgroupCode + ", webName=" + webName + ", weight=" + weight + ", width=" + width + ", stock=" + stock + ", materials=" + materials + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.itemCode != null ? this.itemCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InventoryItemPrestashopDTO other = (InventoryItemPrestashopDTO) obj;
        if ((this.itemCode == null) ? (other.itemCode != null) : !this.itemCode.equals(other.itemCode)) {
            return false;
        }
        return true;
    }

    public void addStock(String whsCode, Integer quantity) {
        ItemStockDTO itemStock = new ItemStockDTO(whsCode, quantity);
        if (stock.contains(itemStock)) {
            stock.get(stock.indexOf(itemStock)).addStock(quantity);
        } else {
            stock.add(itemStock);
        }
    }

    public void addMaterial(ItemMaterialDTO material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public boolean containsStockInWarehouse(String whsCode) {
        ItemStockDTO itemStock = new ItemStockDTO(whsCode, 0);
        if (stock.contains(itemStock)) {
            return true;
        } else {
            return false;
        }
    }

    public void mergeStockAndMaterials(InventoryItemPrestashopDTO item) {
        //Merges stock. 
        for (ItemStockDTO stck : item.getStock()) {
            if (!this.containsStockInWarehouse(stck.getWarehouseCode())) {
                this.addStock(stck.getWarehouseCode(), stck.getQuantity());
            }
        }

        //Merges materials
        for (ItemMaterialDTO mat : item.getMaterials()) {
            if (!this.getMaterials().contains(mat)) {
                this.addMaterial(mat);
            }
        }
    }
}
