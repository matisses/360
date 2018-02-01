package co.matisses.web.dto;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class InventoryItemDTO {

    private Integer availableQuantity;
    private Integer cantidad; //atributo usado para retomar ventas pos que habian sido suspendidas
    private Integer similares;
    private ItemColorDTO color;
    private String departmentName;
    private String departmentCode;
    private String description;
    private String depth;
    private String groupCode;
    private String groupName;
    private String height;
    private String idYoutube;
    private String itemCode;
    private String itemName;
    private String keyWords;
    private String mainCombination;
    private String model;
    private String newFrom;
    private String price;
    private String discountPercent;
    private String providerCode;
    private String processImages;
    private String shortDescription;
    private String subgroupName;
    private String subgroupCode;
    private String taxName;
    private String taxRate;
    private String webName;
    private String weight;
    private String width;
    private List<ItemStockDTO> stock;
    private List<ItemMaterialDTO> materials;

    public InventoryItemDTO() {
        this.availableQuantity = 0;
        stock = new ArrayList<>();
        materials = new ArrayList<>();
    }

    public InventoryItemDTO(Integer availableQuantity, Integer cantidad, ItemColorDTO color, String departmentName,
            String departmentCode, String description, String depth, String groupCode, String groupName, String height,
            String idYoutube, String itemCode, String itemName, String keyWords, String mainCombination, String model,
            String newFrom, String price, String providerCode, String processImages, String shortDescription, String discountPercent,
            String subgroupName, String subgroupCode, String taxName, String taxRate, String webName, String weight,
            String width, List<ItemStockDTO> stock, List<ItemMaterialDTO> materials) {
        this.availableQuantity = availableQuantity;
        this.cantidad = cantidad;
        this.color = color;
        this.departmentName = departmentName;
        this.departmentCode = departmentCode;
        this.description = description;
        this.depth = depth;
        this.groupCode = groupCode;
        this.discountPercent = discountPercent;
        this.groupName = groupName;
        this.height = height;
        this.idYoutube = idYoutube;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.keyWords = keyWords;
        this.mainCombination = mainCombination;
        this.model = model;
        this.newFrom = newFrom;
        this.price = price;
        this.providerCode = providerCode;
        this.processImages = processImages;
        this.shortDescription = shortDescription;
        this.subgroupName = subgroupName;
        this.subgroupCode = subgroupCode;
        this.taxName = taxName;
        this.taxRate = taxRate;
        this.webName = webName;
        this.weight = weight;
        this.width = width;
        this.stock = stock;
        this.materials = materials;
    }

    public Integer getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(Integer availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public Integer getSimilares() {
        return similares;
    }

    public void setSimilares(Integer similares) {
        this.similares = similares;
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

    public String getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(String discountPercent) {
        this.discountPercent = discountPercent;
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

    public String getIdYoutube() {
        return idYoutube;
    }

    public void setIdYoutube(String idYoutube) {
        this.idYoutube = idYoutube;
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

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getProcessImages() {
        return processImages;
    }

    public void setProcessImages(String processImages) {
        this.processImages = processImages;
    }

    public String getTaxName() {
        return taxName;
    }

    public void setTaxName(String taxName) {
        this.taxName = taxName;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }

    public String getSubgroupCode() {
        return subgroupCode;
    }

    public void setSubgroupCode(String subgroupCode) {
        this.subgroupCode = subgroupCode;
    }

    public String getSubgroupName() {
        return subgroupName;
    }

    public void setSubgroupName(String subgroupName) {
        this.subgroupName = subgroupName;
    }

    public String getPrice() {
        return price;
    }

    public int getPriceAsInt() {
        return Integer.parseInt((price != null ? price : "0"));
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

    public Date getNewFromAsDate() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.parse(newFrom);
        } catch (Exception e) {
            return null;
        }
    }

    public List<ItemMaterialDTO> getMaterials() {
        return materials;
    }

    public void setMaterials(List<ItemMaterialDTO> materials) {
        this.materials = materials;
    }

    public String toXML() throws JAXBException {
        StringWriter sw = new StringWriter();
        JAXBContext context = JAXBContext.newInstance(InventoryItemDTO.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FRAGMENT, true);
        m.marshal(this, sw);
        return sw.toString();
    }

    public static InventoryItemDTO fromXML(String xml) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(InventoryItemDTO.class);
        Unmarshaller un = context.createUnmarshaller();
        return (InventoryItemDTO) un.unmarshal(new StringReader(xml));
    }

    @Override
    public String toString() {
        return "InventoryItemDTO{" + "newFrom=" + newFrom + ", color=" + color + ", departmentName=" + departmentName + ", departmentCode=" + departmentCode + ", description=" + description + ", depth=" + depth + ", groupCode=" + groupCode + ", groupName=" + groupName + ", height=" + height + ", idYoutube=" + idYoutube + ", itemCode=" + itemCode + ", itemName=" + itemName + ", keyWords=" + keyWords + ", model=" + model + ", price=" + price + ", providerCode=" + providerCode + ", subgroupName=" + subgroupName + ", subgroupCode=" + subgroupCode + ", webName=" + webName + ", weight=" + weight + ", width=" + width + ", stock=" + stock + ", materials=" + materials + '}';
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
        final InventoryItemDTO other = (InventoryItemDTO) obj;
        if ((this.itemCode == null) ? (other.itemCode != null) : !this.itemCode.equals(other.itemCode)) {
            return false;
        }
        return true;
    }

    public void addStock(String whsCode, Integer quantity, Integer binAbs, String binCode) {
        ItemStockDTO itemStock = new ItemStockDTO(whsCode, quantity, binAbs, binCode);
        if (stock.contains(itemStock)) {
            stock.get(stock.indexOf(itemStock)).addStock(quantity);
        } else {
            stock.add(itemStock);
        }
        this.availableQuantity += quantity;
    }

    public void addMaterial(ItemMaterialDTO material) {
        if (!materials.contains(material)) {
            materials.add(material);
        }
    }

    public boolean containsStock(String whsCode, Integer binAbs) {
        ItemStockDTO itemStock = new ItemStockDTO(whsCode, 0, binAbs, null);
        if (stock.contains(itemStock)) {
            return true;
        } else {
            return false;
        }
    }

    public void mergeStockAndMaterials(InventoryItemDTO item) {
        //Merges stock. 
        for (ItemStockDTO stck : item.getStock()) {
            if (!this.containsStock(stck.getWarehouseCode(), stck.getBinAbs())) {
                this.addStock(stck.getWarehouseCode(), stck.getQuantity(), stck.getBinAbs(), stck.getBinCode());
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
