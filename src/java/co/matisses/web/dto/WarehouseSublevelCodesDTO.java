package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class WarehouseSublevelCodesDTO {

    private Integer warehouseSublevel;
    private String code;

    public WarehouseSublevelCodesDTO() {
    }

    public WarehouseSublevelCodesDTO(Integer warehouseSublevel, String code) {
        this.warehouseSublevel = warehouseSublevel;
        this.code = code;
    }

    public Integer getWarehouseSublevel() {
        return warehouseSublevel;
    }

    public void setWarehouseSublevel(Integer warehouseSublevel) {
        this.warehouseSublevel = warehouseSublevel;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
