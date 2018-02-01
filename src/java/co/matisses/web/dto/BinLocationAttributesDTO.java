package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BinLocationAttributesDTO {

    private Integer attributo; //AbsEntry del atributo tabla OBFC
    private String code; //Valor del atributo

    public BinLocationAttributesDTO() {
    }

    public BinLocationAttributesDTO(Integer attributo, String code) {
        this.attributo = attributo;
        this.code = code;
    }

    public Integer getAttributo() {
        return attributo;
    }

    public void setAttributo(Integer attributo) {
        this.attributo = attributo;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
