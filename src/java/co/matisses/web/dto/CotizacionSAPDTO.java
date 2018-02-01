package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class CotizacionSAPDTO {

    private int docNum;
    private Integer docEntry;
    private Short slpCode;
    private String cardCode;
    private String cardName;
    private Character docStatus;
    private Date docDate;

    public CotizacionSAPDTO() {
    }

    public CotizacionSAPDTO(int docNum, Short slpCode, String cardCode, String cardName, Character docStatus, Date docDate) {
        this.docNum = docNum;
        this.slpCode = slpCode;
        this.cardCode = cardCode;
        this.cardName = cardName;
        this.docStatus = docStatus;
        this.docDate = docDate;
    }

    public CotizacionSAPDTO(int docNum, Integer docEntry, Short slpCode, String cardCode, String cardName, Character docStatus, Date docDate) {
        this.docNum = docNum;
        this.docEntry = docEntry;
        this.slpCode = slpCode;
        this.cardCode = cardCode;
        this.cardName = cardName;
        this.docStatus = docStatus;
        this.docDate = docDate;
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
    }

    public Integer getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(Integer docEntry) {
        this.docEntry = docEntry;
    }

    public Short getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Short slpCode) {
        this.slpCode = slpCode;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public Character getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(Character docStatus) {
        this.docStatus = docStatus;
    }

    public Date getDocDate() {
        return docDate;
    }

    public void setDocDate(Date docDate) {
        this.docDate = docDate;
    }
}
