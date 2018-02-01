package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SucursalEmpleadoDTO {

    private Integer code;
    private Integer userSign;
    private String name;
    private String remarks;

    public SucursalEmpleadoDTO() {
    }

    public SucursalEmpleadoDTO(Integer code, Integer userSign, String name, String remarks) {
        this.code = code;
        this.userSign = userSign;
        this.name = name;
        this.remarks = remarks;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getUserSign() {
        return userSign;
    }

    public void setUserSign(Integer userSign) {
        this.userSign = userSign;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
