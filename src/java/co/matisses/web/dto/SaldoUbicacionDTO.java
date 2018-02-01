package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SaldoUbicacionDTO {

    private Integer absEntry;
    private Integer ubicacion;
    private Integer freezeDoc;
    private Integer onHandQty;
    private Integer onHandQtyUsado;
    private String itemCode;
    private String whsCode;
    private String binCode;
    private Character freezed;

    public SaldoUbicacionDTO() {
    }

    public SaldoUbicacionDTO(Integer absEntry, Integer ubicacion, Integer freezeDoc, Integer onHandQty, String itemCode, String whsCode, String binCode, Character freezed) {
        this.absEntry = absEntry;
        this.ubicacion = ubicacion;
        this.freezeDoc = freezeDoc;
        this.onHandQty = onHandQty;
        this.itemCode = itemCode;
        this.whsCode = whsCode;
        this.binCode = binCode;
        this.freezed = freezed;
    }

    public SaldoUbicacionDTO(Integer absEntry, Integer ubicacion, Integer freezeDoc, Integer onHandQty, Integer onHandQtyUsado, String itemCode, String whsCode, String binCode, Character freezed) {
        this.absEntry = absEntry;
        this.ubicacion = ubicacion;
        this.freezeDoc = freezeDoc;
        this.onHandQty = onHandQty;
        this.onHandQtyUsado = onHandQtyUsado;
        this.itemCode = itemCode;
        this.whsCode = whsCode;
        this.binCode = binCode;
        this.freezed = freezed;
    }

    public SaldoUbicacionDTO(Integer ubicacion, Integer onHandQty, Integer onHandQtyUsado, String binCode) {
        this.ubicacion = ubicacion;
        this.onHandQty = onHandQty;
        this.onHandQtyUsado = onHandQtyUsado;
        this.binCode = binCode;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public Integer getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(Integer ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Integer getFreezeDoc() {
        return freezeDoc;
    }

    public void setFreezeDoc(Integer freezeDoc) {
        this.freezeDoc = freezeDoc;
    }

    public Integer getOnHandQty() {
        return onHandQty;
    }

    public void setOnHandQty(Integer onHandQty) {
        this.onHandQty = onHandQty;
    }

    public Integer getOnHandQtyUsado() {
        return onHandQtyUsado;
    }

    public void setOnHandQtyUsado(Integer onHandQtyUsado) {
        this.onHandQtyUsado = onHandQtyUsado;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public Character getFreezed() {
        return freezed;
    }

    public void setFreezed(Character freezed) {
        this.freezed = freezed;
    }
}
