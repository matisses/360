package co.matisses.web.dto;


/**
 *
 * @author jguisao
 */
public class PaymentInvoicesDTO {

    private Long invoiceDocEntry;
    private Double invoicePaidSum;

    public PaymentInvoicesDTO() {
    }

    public PaymentInvoicesDTO(Long invoiceDocEntry, Double invoicePaidSum) {
        this.invoiceDocEntry = invoiceDocEntry;
        this.invoicePaidSum = invoicePaidSum;
    }

    public Long getInvoiceDocEntry() {
        return invoiceDocEntry;
    }

    public void setInvoiceDocEntry(Long invoiceDocEntry) {
        this.invoiceDocEntry = invoiceDocEntry;
    }

    public Double getInvoicePaidSum() {
        return invoicePaidSum;
    }

    public void setInvoicePaidSum(Double invoicePaidSum) {
        this.invoicePaidSum = invoicePaidSum;
    }

    @Override
    public String toString() {
        return "PaymentInvoicesDTO{" + "invoiceDocEntry=" + invoiceDocEntry + ", invoicePaidSum=" + invoicePaidSum + '}';
    }
}