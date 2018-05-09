package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class PaisDTO {

    private String code;
    private String name;
    private Short addrFormat;
    private Short userSign;
    private Character isEC;
    private String reportCode;
    private Short taxIdDigts;
    private Integer bnkCodDgts;
    private Integer bnkBchDgts;
    private Integer bnkActDgts;
    private Integer bnkCtKDgts;
    private String valDomAcct;
    private Character valIban;
    private Character isBlackLst;
    private String uICCode;

    public PaisDTO() {
    }

    public PaisDTO(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public PaisDTO(String code, String name, Short addrFormat, Short userSign, Character isEC, String reportCode, Short taxIdDigts, Integer bnkCodDgts, Integer bnkBchDgts,
            Integer bnkActDgts, Integer bnkCtKDgts, String valDomAcct, Character valIban, Character isBlackLst, String uICCode) {
        this.code = code;
        this.name = name;
        this.addrFormat = addrFormat;
        this.userSign = userSign;
        this.isEC = isEC;
        this.reportCode = reportCode;
        this.taxIdDigts = taxIdDigts;
        this.bnkCodDgts = bnkCodDgts;
        this.bnkBchDgts = bnkBchDgts;
        this.bnkActDgts = bnkActDgts;
        this.bnkCtKDgts = bnkCtKDgts;
        this.valDomAcct = valDomAcct;
        this.valIban = valIban;
        this.isBlackLst = isBlackLst;
        this.uICCode = uICCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getAddrFormat() {
        return addrFormat;
    }

    public void setAddrFormat(Short addrFormat) {
        this.addrFormat = addrFormat;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Character getIsEC() {
        return isEC;
    }

    public void setIsEC(Character isEC) {
        this.isEC = isEC;
    }

    public String getReportCode() {
        return reportCode;
    }

    public void setReportCode(String reportCode) {
        this.reportCode = reportCode;
    }

    public Short getTaxIdDigts() {
        return taxIdDigts;
    }

    public void setTaxIdDigts(Short taxIdDigts) {
        this.taxIdDigts = taxIdDigts;
    }

    public Integer getBnkCodDgts() {
        return bnkCodDgts;
    }

    public void setBnkCodDgts(Integer bnkCodDgts) {
        this.bnkCodDgts = bnkCodDgts;
    }

    public Integer getBnkBchDgts() {
        return bnkBchDgts;
    }

    public void setBnkBchDgts(Integer bnkBchDgts) {
        this.bnkBchDgts = bnkBchDgts;
    }

    public Integer getBnkActDgts() {
        return bnkActDgts;
    }

    public void setBnkActDgts(Integer bnkActDgts) {
        this.bnkActDgts = bnkActDgts;
    }

    public Integer getBnkCtKDgts() {
        return bnkCtKDgts;
    }

    public void setBnkCtKDgts(Integer bnkCtKDgts) {
        this.bnkCtKDgts = bnkCtKDgts;
    }

    public String getValDomAcct() {
        return valDomAcct;
    }

    public void setValDomAcct(String valDomAcct) {
        this.valDomAcct = valDomAcct;
    }

    public Character getValIban() {
        return valIban;
    }

    public void setValIban(Character valIban) {
        this.valIban = valIban;
    }

    public Character getIsBlackLst() {
        return isBlackLst;
    }

    public void setIsBlackLst(Character isBlackLst) {
        this.isBlackLst = isBlackLst;
    }

    public String getuICCode() {
        return uICCode;
    }

    public void setuICCode(String uICCode) {
        this.uICCode = uICCode;
    }
}
