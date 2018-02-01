package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class ValoresUsuarioDTO {

    private short fieldID;
    private short indexID;
    private String tableID;
    private String fldValue;
    private String descr;

    public ValoresUsuarioDTO() {
    }

    public ValoresUsuarioDTO(short fieldID) {
        this.fieldID = fieldID;
    }

    public ValoresUsuarioDTO(short fieldID, short indexID, String tableID, String fldValue, String descr) {
        this.fieldID = fieldID;
        this.indexID = indexID;
        this.tableID = tableID;
        this.fldValue = fldValue;
        this.descr = descr;
    }

    public short getFieldID() {
        return fieldID;
    }

    public void setFieldID(short fieldID) {
        this.fieldID = fieldID;
    }

    public short getIndexID() {
        return indexID;
    }

    public void setIndexID(short indexID) {
        this.indexID = indexID;
    }

    public String getTableID() {
        return tableID;
    }

    public void setTableID(String tableID) {
        this.tableID = tableID;
    }

    public String getFldValue() {
        return fldValue;
    }

    public void setFldValue(String fldValue) {
        this.fldValue = fldValue;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }
}
