package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruAveriasDTO {

    private String code;
    private String name;

    public BaruAveriasDTO() {
    }

    public BaruAveriasDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
