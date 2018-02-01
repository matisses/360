package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BaruColorGenericoDTO {

    private String code;
    private String name;

    public BaruColorGenericoDTO() {
    }

    public BaruColorGenericoDTO(String code, String name) {
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
