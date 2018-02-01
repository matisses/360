package co.matisses.web.dto.epson;

import java.util.ArrayList;
import java.util.List;

public class InvoiceData {

    private String printerName;
    private String invoiceNumber;
    private String creditNoteNumber;
    private String cashierName;
    private String cashRegister;
    private String salesPersonCode;
    private String docType;
    private Float change;
    private Customer customer;
    private String footerText;
    private String orderStatus;
    private InvoiceResolution invoiceResolution;
    private List<InvoiceDetail> items;
    private List<InvoiceVATDetail> vatDetail;
    private List<InvoicePaymentDetail> payments;
    private List<InvoiceBinAllocation> binAllocations;

    public InvoiceData() {
        items = new ArrayList<>();
        vatDetail = new ArrayList<>();
        payments = new ArrayList<>();
        binAllocations = new ArrayList<>();
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getPrinterName() {
        return printerName;
    }

    public void setPrinterName(String printerName) {
        this.printerName = printerName;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getCreditNoteNumber() {
        return creditNoteNumber;
    }

    public void setCreditNoteNumber(String creditNoteNumber) {
        this.creditNoteNumber = creditNoteNumber;
    }

    public String getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(String salesPersonCode) {
        this.salesPersonCode = salesPersonCode;
    }

    public InvoiceResolution getInvoiceResolution() {
        return invoiceResolution;
    }

    public void setInvoiceResolution(InvoiceResolution invoiceResolution) {
        this.invoiceResolution = invoiceResolution;
    }

    public String getFooterText() {
        return footerText;
    }

    public void setFooterText(String footerText) {
        this.footerText = footerText;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getCashRegister() {
        return cashRegister;
    }

    public void setCashRegister(String cashRegister) {
        this.cashRegister = cashRegister;
    }

    public String getCashierName() {
        return cashierName;
    }

    public void setCashierName(String cashierName) {
        this.cashierName = cashierName;
    }

    public Float getChange() {
        return change;
    }

    public void setChange(Float change) {
        this.change = change;
    }

    public List<InvoiceDetail> getItems() {
        return items;
    }

    public void setItems(List<InvoiceDetail> items) {
        this.items = items;
    }

    public List<InvoiceVATDetail> getVatDetail() {
        return vatDetail;
    }

    public void setVatDetail(List<InvoiceVATDetail> vatDetail) {
        this.vatDetail = vatDetail;
    }

    public List<InvoicePaymentDetail> getPayments() {
        return payments;
    }

    public void setPayments(List<InvoicePaymentDetail> payments) {
        this.payments = payments;
    }

    public List<InvoiceBinAllocation> getBinAllocations() {
        return binAllocations;
    }

    public void setBinAllocations(List<InvoiceBinAllocation> binAllocations) {
        this.binAllocations = binAllocations;
    }

    @Override
    public String toString() {
        return "InvoiceData [printerName=" + printerName + ", invoiceNumber=" + invoiceNumber + ", creditNoteNumber="
                + creditNoteNumber + ", cashierName=" + cashierName + ", cashRegister=" + cashRegister
                + ", salesPersonCode=" + salesPersonCode + ", docType=" + docType + ", change=" + change + ", customer="
                + customer + ", footerText=" + footerText + ", orderStatus=" + orderStatus + ", invoiceResolution="
                + invoiceResolution + ", items=" + items + ", vatDetail=" + vatDetail + ", payments=" + payments
                + ", binAllocations=" + binAllocations + "]";
    }

}
