package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BinLocationDTO {

    private Integer absEntry;
    private String warehouse; //WhsCode
    private String sublevel1; //SL1Code - Piso
    private String sublevel2; //SL2code - Ubicacion
    private String binCode;
    private String attribute2; //Attr2Val - Alias
    private String attribute3; //Attr3Val - Fecha inicio DEMO
    private String attribute4; //Attr4Val - Fecha fin DEMO
    private String attribute5; //Attr5Val - Cliente
    private String attribute7; //Attr7Val - Id demostracion web
    private String attribute8; //Attr8Val - Factura asociada
    private boolean inactive = false; //Disabled
    private boolean attribute1; //Attr1Val - Ubicacion especial
    private boolean attribute6; //Attr6Val - Tipo ruta
    private boolean attribute9; //Attr9Val - Pallet

    public BinLocationDTO() {
    }

    public BinLocationDTO(Integer absEntry, String warehouse, String sublevel1, String sublevel2, String binCode, String attribute2, String attribute3, String attribute4,
            String attribute5, String attribute7, String attribute8, boolean attribute1, boolean attribute6, boolean attribute9) {
        this.absEntry = absEntry;
        this.warehouse = warehouse;
        this.sublevel1 = sublevel1;
        this.sublevel2 = sublevel2;
        this.binCode = binCode;
        this.attribute2 = attribute2;
        this.attribute3 = attribute3;
        this.attribute4 = attribute4;
        this.attribute5 = attribute5;
        this.attribute7 = attribute7;
        this.attribute8 = attribute8;
        this.attribute1 = attribute1;
        this.attribute6 = attribute6;
        this.attribute9 = attribute9;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public String getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(String warehouse) {
        this.warehouse = warehouse;
    }

    public String getSublevel1() {
        return sublevel1;
    }

    public void setSublevel1(String sublevel1) {
        this.sublevel1 = sublevel1;
    }

    public String getSublevel2() {
        return sublevel2;
    }

    public void setSublevel2(String sublevel2) {
        this.sublevel2 = sublevel2;
    }

    public String getBinCode() {
        if (warehouse != null && !warehouse.isEmpty() && sublevel1 != null && !sublevel1.isEmpty() && sublevel2 != null && !sublevel2.isEmpty()) {
            return warehouse + sublevel1 + sublevel2;
        } else if (warehouse != null && !warehouse.isEmpty() && sublevel1 != null && !sublevel1.isEmpty()) {
            return warehouse + sublevel1;
        } else {
            return null;
        }
    }

    public String getAttribute2() {
        return attribute2;
    }

    public void setAttribute2(String attribute2) {
        this.attribute2 = attribute2;
    }

    public String getAttribute3() {
        return attribute3;
    }

    public void setAttribute3(String attribute3) {
        this.attribute3 = attribute3;
    }

    public String getAttribute4() {
        return attribute4;
    }

    public void setAttribute4(String attribute4) {
        this.attribute4 = attribute4;
    }

    public String getAttribute5() {
        return attribute5;
    }

    public void setAttribute5(String attribute5) {
        this.attribute5 = attribute5;
    }

    public String getAttribute7() {
        return attribute7;
    }

    public void setAttribute7(String attribute7) {
        this.attribute7 = attribute7;
    }

    public String getAttribute8() {
        return attribute8;
    }

    public void setAttribute8(String attribute8) {
        this.attribute8 = attribute8;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public boolean isAttribute1() {
        return attribute1;
    }

    public void setAttribute1(boolean attribute1) {
        this.attribute1 = attribute1;
    }

    public boolean isAttribute6() {
        return attribute6;
    }

    public void setAttribute6(boolean attribute6) {
        this.attribute6 = attribute6;
    }

    public boolean isAttribute9() {
        return attribute9;
    }

    public void setAttribute9(boolean attribute9) {
        this.attribute9 = attribute9;
    }
}
