package co.matisses.web.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dbotero
 */
@XmlRootElement
public class TarjetaCreditoDTO {

    private Integer creditCardId;
    private String franchiseName;
    private String accountCode;
    private String type;

    public TarjetaCreditoDTO() {
    }

    public TarjetaCreditoDTO(Integer creditCardId, String franchiseName, String accountCode, String type) {
        this.creditCardId = creditCardId;
        this.franchiseName = franchiseName;
        this.accountCode = accountCode;
        this.type = type;
    }

    public Integer getCreditCardId() {
        return creditCardId;
    }

    public void setCreditCardId(Integer creditCardId) {
        this.creditCardId = creditCardId;
    }

    public String getFranchiseName() {
        return franchiseName;
    }

    public void setFranchiseName(String franchiseName) {
        this.franchiseName = franchiseName;
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
