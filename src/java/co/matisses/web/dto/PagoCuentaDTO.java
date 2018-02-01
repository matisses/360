package co.matisses.web.dto;

import co.matisses.web.ObjectUtils;
import java.math.BigDecimal;

/**
 *
 * @author dbotero
 */
public class PagoCuentaDTO {
    private String accountCode;
    private String cardCode;
    private BigDecimal sumPaid;

    public PagoCuentaDTO() {
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public BigDecimal getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(BigDecimal sumPaid) {
        this.sumPaid = sumPaid;
    }

    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
