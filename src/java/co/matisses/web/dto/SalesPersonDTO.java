package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class SalesPersonDTO {

    private Short slpCode;
    private String slpName;
    private Character active;

    public SalesPersonDTO() {
    }

    public SalesPersonDTO(Short slpCode, String slpName, Character active) {
        this.slpCode = slpCode;
        this.slpName = slpName;
        this.active = active;
    }

    public Short getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Short slpCode) {
        this.slpCode = slpCode;
    }

    public String getSlpName() {
        return slpName;
    }

    public void setSlpName(String slpName) {
        this.slpName = slpName;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }
}
