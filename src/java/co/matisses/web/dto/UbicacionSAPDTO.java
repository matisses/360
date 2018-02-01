package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class UbicacionSAPDTO {

    private Integer absEntry;
    private Integer sL1Abs;
    private Integer sL2Abs;
    private Integer sL3Abs;
    private Integer sL4Abs;
    private Integer attr1Abs;
    private Integer attr2Abs;
    private Integer attr3Abs;
    private Integer attr4Abs;
    private Integer attr5Abs;
    private Integer attr6Abs;
    private Integer attr7Abs;
    private Integer attr8Abs;
    private Integer attr10Abs;
    private Integer attr9Abs;
    private Integer logInstanc;
    private Integer idDemo;
    private Short itmRtrictT;
    private Short spcItmGrpC;
    private Short rtrictType;
    private Short userSign;
    private Short userSign2;
    private Short instance;
    private String binCode;
    private String whsCode;
    private String sL1Code;
    private String sL2Code;
    private String sL3Code;
    private String sL4Code;
    private String attr1Val;
    private String attr2Val;
    private String attr3Val;
    private String attr4Val;
    private String attr5Val;
    private String attr6Val;
    private String attr7Val;
    private String attr8Val;
    private String attr9Val;
    private String attr10Val;
    private String descr;
    private String barCode;
    private String altSortCod;
    private String spcItmCode;
    private String rtrictResn;
    private String estado;
    private Character sysBin;
    private Character disabled;
    private Character sngBatch;
    private Character dataSource;
    private Character transfered;
    private Character deleted;
    private Character receiveBin;
    private Character noAutoAllc;
    private BigDecimal minLevel;
    private BigDecimal maxLevel;
    private Date rtrictDate;
    private Date createDate;
    private Date updateDate;
    private boolean demoEditada = false;
    private boolean usando = false;

    public UbicacionSAPDTO() {
    }

    public UbicacionSAPDTO(Integer absEntry, Integer sL1Abs, Integer sL2Abs, Integer sL3Abs, Integer sL4Abs, Integer attr1Abs,
            Integer attr2Abs, Integer attr3Abs, Integer attr4Abs, Integer attr5Abs, Integer attr6Abs, Integer attr7Abs,
            Integer attr8Abs, Integer attr10Abs, Integer attr9Abs, Integer logInstanc, Integer idDemo, Short itmRtrictT,
            Short spcItmGrpC, Short rtrictType, Short userSign, Short userSign2, Short instance, String binCode, String whsCode,
            String sL1Code, String sL2Code, String sL3Code, String sL4Code, String attr1Val, String attr2Val, String attr3Val,
            String attr4Val, String attr5Val, String attr6Val, String attr7Val, String attr8Val, String attr9Val, String attr10Val,
            String descr, String barCode, String altSortCod, String spcItmCode, String rtrictResn, String estado, Character sysBin,
            Character disabled, Character sngBatch, Character dataSource, Character transfered, Character deleted,
            Character receiveBin, Character noAutoAllc, BigDecimal minLevel, BigDecimal maxLevel, Date rtrictDate, Date createDate, Date updateDate) {
        this.absEntry = absEntry;
        this.sL1Abs = sL1Abs;
        this.sL2Abs = sL2Abs;
        this.sL3Abs = sL3Abs;
        this.sL4Abs = sL4Abs;
        this.attr1Abs = attr1Abs;
        this.attr2Abs = attr2Abs;
        this.attr3Abs = attr3Abs;
        this.attr4Abs = attr4Abs;
        this.attr5Abs = attr5Abs;
        this.attr6Abs = attr6Abs;
        this.attr7Abs = attr7Abs;
        this.attr8Abs = attr8Abs;
        this.attr10Abs = attr10Abs;
        this.attr9Abs = attr9Abs;
        this.logInstanc = logInstanc;
        this.idDemo = idDemo;
        this.itmRtrictT = itmRtrictT;
        this.spcItmGrpC = spcItmGrpC;
        this.rtrictType = rtrictType;
        this.userSign = userSign;
        this.userSign2 = userSign2;
        this.instance = instance;
        this.binCode = binCode;
        this.whsCode = whsCode;
        this.sL1Code = sL1Code;
        this.sL2Code = sL2Code;
        this.sL3Code = sL3Code;
        this.sL4Code = sL4Code;
        this.attr1Val = attr1Val;
        this.attr2Val = attr2Val;
        this.attr3Val = attr3Val;
        this.attr4Val = attr4Val;
        this.attr5Val = attr5Val;
        this.attr6Val = attr6Val;
        this.attr7Val = attr7Val;
        this.attr8Val = attr8Val;
        this.attr9Val = attr9Val;
        this.attr10Val = attr10Val;
        this.descr = descr;
        this.barCode = barCode;
        this.altSortCod = altSortCod;
        this.spcItmCode = spcItmCode;
        this.rtrictResn = rtrictResn;
        this.estado = estado;
        this.sysBin = sysBin;
        this.disabled = disabled;
        this.sngBatch = sngBatch;
        this.dataSource = dataSource;
        this.transfered = transfered;
        this.deleted = deleted;
        this.receiveBin = receiveBin;
        this.noAutoAllc = noAutoAllc;
        this.minLevel = minLevel;
        this.maxLevel = maxLevel;
        this.rtrictDate = rtrictDate;
        this.createDate = createDate;
        this.updateDate = updateDate;
    }

    public UbicacionSAPDTO(Integer absEntry, String binCode, String whsCode, String sL1Code, String sL2Code, String attr2Val) {
        this.absEntry = absEntry;
        this.binCode = binCode;
        this.whsCode = whsCode;
        this.sL1Code = sL1Code;
        this.sL2Code = sL2Code;
        this.attr2Val = attr2Val;
    }

    public Integer getAbsEntry() {
        return absEntry;
    }

    public void setAbsEntry(Integer absEntry) {
        this.absEntry = absEntry;
    }

    public Integer getsL1Abs() {
        return sL1Abs;
    }

    public void setsL1Abs(Integer sL1Abs) {
        this.sL1Abs = sL1Abs;
    }

    public Integer getsL2Abs() {
        return sL2Abs;
    }

    public void setsL2Abs(Integer sL2Abs) {
        this.sL2Abs = sL2Abs;
    }

    public Integer getsL3Abs() {
        return sL3Abs;
    }

    public void setsL3Abs(Integer sL3Abs) {
        this.sL3Abs = sL3Abs;
    }

    public Integer getsL4Abs() {
        return sL4Abs;
    }

    public void setsL4Abs(Integer sL4Abs) {
        this.sL4Abs = sL4Abs;
    }

    public Integer getAttr1Abs() {
        return attr1Abs;
    }

    public void setAttr1Abs(Integer attr1Abs) {
        this.attr1Abs = attr1Abs;
    }

    public Integer getAttr2Abs() {
        return attr2Abs;
    }

    public void setAttr2Abs(Integer attr2Abs) {
        this.attr2Abs = attr2Abs;
    }

    public Integer getAttr3Abs() {
        return attr3Abs;
    }

    public void setAttr3Abs(Integer attr3Abs) {
        this.attr3Abs = attr3Abs;
    }

    public Integer getAttr4Abs() {
        return attr4Abs;
    }

    public void setAttr4Abs(Integer attr4Abs) {
        this.attr4Abs = attr4Abs;
    }

    public Integer getAttr5Abs() {
        return attr5Abs;
    }

    public void setAttr5Abs(Integer attr5Abs) {
        this.attr5Abs = attr5Abs;
    }

    public Integer getAttr6Abs() {
        return attr6Abs;
    }

    public void setAttr6Abs(Integer attr6Abs) {
        this.attr6Abs = attr6Abs;
    }

    public Integer getAttr7Abs() {
        return attr7Abs;
    }

    public void setAttr7Abs(Integer attr7Abs) {
        this.attr7Abs = attr7Abs;
    }

    public Integer getAttr8Abs() {
        return attr8Abs;
    }

    public void setAttr8Abs(Integer attr8Abs) {
        this.attr8Abs = attr8Abs;
    }

    public Integer getAttr10Abs() {
        return attr10Abs;
    }

    public void setAttr10Abs(Integer attr10Abs) {
        this.attr10Abs = attr10Abs;
    }

    public Integer getAttr9Abs() {
        return attr9Abs;
    }

    public void setAttr9Abs(Integer attr9Abs) {
        this.attr9Abs = attr9Abs;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Integer getIdDemo() {
        return idDemo;
    }

    public void setIdDemo(Integer idDemo) {
        this.idDemo = idDemo;
    }

    public Short getItmRtrictT() {
        return itmRtrictT;
    }

    public void setItmRtrictT(Short itmRtrictT) {
        this.itmRtrictT = itmRtrictT;
    }

    public Short getSpcItmGrpC() {
        return spcItmGrpC;
    }

    public void setSpcItmGrpC(Short spcItmGrpC) {
        this.spcItmGrpC = spcItmGrpC;
    }

    public Short getRtrictType() {
        return rtrictType;
    }

    public void setRtrictType(Short rtrictType) {
        this.rtrictType = rtrictType;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public Short getInstance() {
        return instance;
    }

    public void setInstance(Short instance) {
        this.instance = instance;
    }

    public String getBinCode() {
        return binCode;
    }

    public void setBinCode(String binCode) {
        this.binCode = binCode;
    }

    public String getWhsCode() {
        return whsCode;
    }

    public void setWhsCode(String whsCode) {
        this.whsCode = whsCode;
    }

    public String getsL1Code() {
        return sL1Code;
    }

    public void setsL1Code(String sL1Code) {
        this.sL1Code = sL1Code;
    }

    public String getsL2Code() {
        return sL2Code;
    }

    public void setsL2Code(String sL2Code) {
        this.sL2Code = sL2Code;
    }

    public String getsL3Code() {
        return sL3Code;
    }

    public void setsL3Code(String sL3Code) {
        this.sL3Code = sL3Code;
    }

    public String getsL4Code() {
        return sL4Code;
    }

    public void setsL4Code(String sL4Code) {
        this.sL4Code = sL4Code;
    }

    public String getAttr1Val() {
        return attr1Val;
    }

    public void setAttr1Val(String attr1Val) {
        this.attr1Val = attr1Val;
    }

    public String getAttr2Val() {
        return attr2Val;
    }

    public void setAttr2Val(String attr2Val) {
        this.attr2Val = attr2Val;
    }

    public String getAttr3Val() {
        return attr3Val;
    }

    public void setAttr3Val(String attr3Val) {
        this.attr3Val = attr3Val;
    }

    public String getAttr4Val() {
        return attr4Val;
    }

    public void setAttr4Val(String attr4Val) {
        this.attr4Val = attr4Val;
    }

    public String getAttr5Val() {
        return attr5Val;
    }

    public void setAttr5Val(String attr5Val) {
        this.attr5Val = attr5Val;
    }

    public String getAttr6Val() {
        return attr6Val;
    }

    public void setAttr6Val(String attr6Val) {
        this.attr6Val = attr6Val;
    }

    public String getAttr7Val() {
        return attr7Val;
    }

    public void setAttr7Val(String attr7Val) {
        this.attr7Val = attr7Val;
    }

    public String getAttr8Val() {
        return attr8Val;
    }

    public void setAttr8Val(String attr8Val) {
        this.attr8Val = attr8Val;
    }

    public String getAttr9Val() {
        return attr9Val;
    }

    public void setAttr9Val(String attr9Val) {
        this.attr9Val = attr9Val;
    }

    public String getAttr10Val() {
        return attr10Val;
    }

    public void setAttr10Val(String attr10Val) {
        this.attr10Val = attr10Val;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    public String getAltSortCod() {
        return altSortCod;
    }

    public void setAltSortCod(String altSortCod) {
        this.altSortCod = altSortCod;
    }

    public String getSpcItmCode() {
        return spcItmCode;
    }

    public void setSpcItmCode(String spcItmCode) {
        this.spcItmCode = spcItmCode;
    }

    public String getRtrictResn() {
        return rtrictResn;
    }

    public void setRtrictResn(String rtrictResn) {
        this.rtrictResn = rtrictResn;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Character getSysBin() {
        return sysBin;
    }

    public void setSysBin(Character sysBin) {
        this.sysBin = sysBin;
    }

    public Character getDisabled() {
        return disabled;
    }

    public void setDisabled(Character disabled) {
        this.disabled = disabled;
    }

    public Character getSngBatch() {
        return sngBatch;
    }

    public void setSngBatch(Character sngBatch) {
        this.sngBatch = sngBatch;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Character getTransfered() {
        return transfered;
    }

    public void setTransfered(Character transfered) {
        this.transfered = transfered;
    }

    public Character getDeleted() {
        return deleted;
    }

    public void setDeleted(Character deleted) {
        this.deleted = deleted;
    }

    public Character getReceiveBin() {
        return receiveBin;
    }

    public void setReceiveBin(Character receiveBin) {
        this.receiveBin = receiveBin;
    }

    public Character getNoAutoAllc() {
        return noAutoAllc;
    }

    public void setNoAutoAllc(Character noAutoAllc) {
        this.noAutoAllc = noAutoAllc;
    }

    public BigDecimal getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(BigDecimal minLevel) {
        this.minLevel = minLevel;
    }

    public BigDecimal getMaxLevel() {
        return maxLevel;
    }

    public void setMaxLevel(BigDecimal maxLevel) {
        this.maxLevel = maxLevel;
    }

    public Date getRtrictDate() {
        return rtrictDate;
    }

    public void setRtrictDate(Date rtrictDate) {
        this.rtrictDate = rtrictDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setDemoEditada(boolean demoEditada) {
        this.demoEditada = demoEditada;
    }

    public boolean isDemoEditada() {
        return demoEditada;
    }

    public void setUsando(boolean usando) {
        this.usando = usando;
    }

    public boolean isUsando() {
        return usando;
    }
}