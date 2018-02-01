package co.matisses.web.dto;

/**
 *
 * @author ygil
 */
public class BancoDTO {

    private Integer absEntry;
    private Integer nextChckNo;
    private Integer dfltActKey;
    private Integer nextNum;
    private Integer bnkOpCode;
    private Short userSign;
    private String bankCode;
    private String bankName;
    private String dfltAcct;
    private String dfltBranch;
    private String swiftNum;
    private String iBAN;
    private String countryCod;
    private String aliasName;
    private Character locked;
    private Character dataSource;
    private Character postOffice;
    private Character bsPstDate;
    private Character bsValDate;
    private Character bsDocDate;

    public BancoDTO() {
    }

    public BancoDTO(Integer absEntry, Integer nextChckNo, Integer dfltActKey, Integer nextNum, Integer bnkOpCode, Short userSign, String bankCode, String bankName, String dfltAcct,
            String dfltBranch, String swiftNum, String iBAN, String countryCod, String aliasName, Character locked, Character dataSource, Character postOffice, Character bsPstDate,
            Character bsValDate, Character bsDocDate) {
        this.absEntry = absEntry;
        this.nextChckNo = nextChckNo;
        this.dfltActKey = dfltActKey;
        this.nextNum = nextNum;
        this.bnkOpCode = bnkOpCode;
        this.userSign = userSign;
        this.bankCode = bankCode;
        this.bankName = bankName;
        this.dfltAcct = dfltAcct;
        this.dfltBranch = dfltBranch;
        this.swiftNum = swiftNum;
        this.iBAN = iBAN;
        this.countryCod = countryCod;
        this.aliasName = aliasName;
        this.locked = locked;
        this.dataSource = dataSource;
        this.postOffice = postOffice;
        this.bsPstDate = bsPstDate;
        this.bsValDate = bsValDate;
        this.bsDocDate = bsDocDate;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public Integer getNextChckNo() {
        return nextChckNo;
    }

    public void setNextChckNo(Integer nextChckNo) {
        this.nextChckNo = nextChckNo;
    }

    public Integer getDfltActKey() {
        return dfltActKey;
    }

    public void setDfltActKey(Integer dfltActKey) {
        this.dfltActKey = dfltActKey;
    }

    public Integer getNextNum() {
        return nextNum;
    }

    public void setNextNum(Integer nextNum) {
        this.nextNum = nextNum;
    }

    public Integer getBnkOpCode() {
        return bnkOpCode;
    }

    public void setBnkOpCode(Integer bnkOpCode) {
        this.bnkOpCode = bnkOpCode;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getDfltAcct() {
        return dfltAcct;
    }

    public void setDfltAcct(String dfltAcct) {
        this.dfltAcct = dfltAcct;
    }

    public String getDfltBranch() {
        return dfltBranch;
    }

    public void setDfltBranch(String dfltBranch) {
        this.dfltBranch = dfltBranch;
    }

    public String getSwiftNum() {
        return swiftNum;
    }

    public void setSwiftNum(String swiftNum) {
        this.swiftNum = swiftNum;
    }

    public String getiBAN() {
        return iBAN;
    }

    public void setiBAN(String iBAN) {
        this.iBAN = iBAN;
    }

    public String getCountryCod() {
        return countryCod;
    }

    public void setCountryCod(String countryCod) {
        this.countryCod = countryCod;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public Character getLocked() {
        return locked;
    }

    public void setLocked(Character locked) {
        this.locked = locked;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Character getPostOffice() {
        return postOffice;
    }

    public void setPostOffice(Character postOffice) {
        this.postOffice = postOffice;
    }

    public Character getBsPstDate() {
        return bsPstDate;
    }

    public void setBsPstDate(Character bsPstDate) {
        this.bsPstDate = bsPstDate;
    }

    public Character getBsValDate() {
        return bsValDate;
    }

    public void setBsValDate(Character bsValDate) {
        this.bsValDate = bsValDate;
    }

    public Character getBsDocDate() {
        return bsDocDate;
    }

    public void setBsDocDate(Character bsDocDate) {
        this.bsDocDate = bsDocDate;
    }
}
