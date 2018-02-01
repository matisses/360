package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class CentroCostosEmpleadosDTO {

    private Integer code;
    private Integer userSign;
    private String name;
    private String remarks;
    private String father;

    public CentroCostosEmpleadosDTO() {
    }

    public CentroCostosEmpleadosDTO(Integer code, Integer userSign, String name, String remarks, String father) {
        this.code = code;
        this.userSign = userSign;
        this.name = name;
        this.remarks = remarks;
        this.father = father;
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

    public String getFather() {
        return father;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
