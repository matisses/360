package co.matisses.web.dto.epson;

import java.util.Objects;

public class InvoicePaymentDetail {

    private String paymentId;
    private String paymentType;
    private Float valuePaid;
    private Boolean requiresCashDrawer;

    public InvoicePaymentDetail(String paymentId) {
        this.paymentId = paymentId;
    }

    public InvoicePaymentDetail() {
    }

    public InvoicePaymentDetail(String paymentType, Float valuePaid, Boolean requiresCashDrawer) {
        this.paymentType = paymentType;
        this.valuePaid = valuePaid;
        this.requiresCashDrawer = requiresCashDrawer;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Float getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(Float valuePaid) {
        this.valuePaid = valuePaid;
    }

    public Boolean getRequiresCashDrawer() {
        return requiresCashDrawer;
    }

    public void setRequiresCashDrawer(Boolean requiresCashDrawer) {
        this.requiresCashDrawer = requiresCashDrawer;
    }

    @Override
    public String toString() {
        return "InvoicePaymentDetail [paymentType=" + paymentType + ", valuePaid=" + valuePaid + ", requiresCashDrawer="
                + requiresCashDrawer + "]";
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.paymentId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final InvoicePaymentDetail other = (InvoicePaymentDetail) obj;
        if (!Objects.equals(this.paymentId, other.paymentId)) {
            return false;
        }
        return true;
    }

}
