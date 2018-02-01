package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class PaymentAccountDTO {

    private String accountCode;
    private BigDecimal sumPaid;

    public PaymentAccountDTO() {
    }

    public String getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(String accountCode) {
        this.accountCode = accountCode;
    }

    public BigDecimal getSumPaid() {
        return sumPaid;
    }

    public void setSumPaid(BigDecimal sumPaid) {
        this.sumPaid = sumPaid;
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
