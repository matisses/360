package co.matisses.web.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author dbotero
 */
public class GiftCertificate {

    private String giftName;
    private String certificateNumber;
    private List<GiftItem> items;

    public GiftCertificate() {
        items = new ArrayList<>();
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public List<GiftItem> getItems() {
        return items;
    }

    public void setItems(List<GiftItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "GiftCertificate [giftName=" + giftName + ", certificateNumber=" + certificateNumber + ", items=" + items + "]";
    }
}
