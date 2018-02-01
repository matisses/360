package co.matisses.web.dto;

import java.math.BigDecimal;

/**
 *
 * @author ygil
 */
public class CondicionPagoDTO {

    private Integer groupNum;
    private String pymntGroup;
    private Character payDuMonth;
    private Integer extraMonth;
    private Integer extraDays;
    private Integer paymntsNum;
    private BigDecimal credLimit;
    private BigDecimal volumDscnt;
    private BigDecimal latePyChrg;
    private BigDecimal obligLimit;
    private Integer listNum;
    private Character payments;
    private Integer numOfPmnts;
    private BigDecimal payment1;
    private Character dataSource;
    private Integer userSign;
    private Character openRcpt;
    private String discCode;
    private String dunningCod;
    private Character bslineDate;
    private Integer instNum;
    private Integer tolDays;
    private Character vATFirst;
    private Character crdMthd;

    public CondicionPagoDTO() {
    }

    public CondicionPagoDTO(Integer groupNum, String pymntGroup) {
        this.groupNum = groupNum;
        this.pymntGroup = pymntGroup;
    }

    public CondicionPagoDTO(Integer groupNum, String pymntGroup, Character payDuMonth, Integer extraMonth, Integer extraDays, Integer paymntsNum, BigDecimal credLimit,
            BigDecimal volumDscnt, BigDecimal latePyChrg, BigDecimal obligLimit, Integer listNum, Character payments, Integer numOfPmnts, BigDecimal payment1,
            Character dataSource, Integer userSign, Character openRcpt, String discCode, String dunningCod, Character bslineDate, Integer instNum, Integer tolDays, Character vATFirst, Character crdMthd) {
        this.groupNum = groupNum;
        this.pymntGroup = pymntGroup;
        this.payDuMonth = payDuMonth;
        this.extraMonth = extraMonth;
        this.extraDays = extraDays;
        this.paymntsNum = paymntsNum;
        this.credLimit = credLimit;
        this.volumDscnt = volumDscnt;
        this.latePyChrg = latePyChrg;
        this.obligLimit = obligLimit;
        this.listNum = listNum;
        this.payments = payments;
        this.numOfPmnts = numOfPmnts;
        this.payment1 = payment1;
        this.dataSource = dataSource;
        this.userSign = userSign;
        this.openRcpt = openRcpt;
        this.discCode = discCode;
        this.dunningCod = dunningCod;
        this.bslineDate = bslineDate;
        this.instNum = instNum;
        this.tolDays = tolDays;
        this.vATFirst = vATFirst;
        this.crdMthd = crdMthd;
    }

    public Integer getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public String getPymntGroup() {
        return pymntGroup;
    }

    public void setPymntGroup(String pymntGroup) {
        this.pymntGroup = pymntGroup;
    }

    public Character getPayDuMonth() {
        return payDuMonth;
    }

    public void setPayDuMonth(Character payDuMonth) {
        this.payDuMonth = payDuMonth;
    }

    public Integer getExtraMonth() {
        return extraMonth;
    }

    public void setExtraMonth(Integer extraMonth) {
        this.extraMonth = extraMonth;
    }

    public Integer getExtraDays() {
        return extraDays;
    }

    public void setExtraDays(Integer extraDays) {
        this.extraDays = extraDays;
    }

    public Integer getPaymntsNum() {
        return paymntsNum;
    }

    public void setPaymntsNum(Integer paymntsNum) {
        this.paymntsNum = paymntsNum;
    }

    public BigDecimal getCredLimit() {
        return credLimit;
    }

    public void setCredLimit(BigDecimal credLimit) {
        this.credLimit = credLimit;
    }

    public BigDecimal getVolumDscnt() {
        return volumDscnt;
    }

    public void setVolumDscnt(BigDecimal volumDscnt) {
        this.volumDscnt = volumDscnt;
    }

    public BigDecimal getLatePyChrg() {
        return latePyChrg;
    }

    public void setLatePyChrg(BigDecimal latePyChrg) {
        this.latePyChrg = latePyChrg;
    }

    public BigDecimal getObligLimit() {
        return obligLimit;
    }

    public void setObligLimit(BigDecimal obligLimit) {
        this.obligLimit = obligLimit;
    }

    public Integer getListNum() {
        return listNum;
    }

    public void setListNum(Integer listNum) {
        this.listNum = listNum;
    }

    public Character getPayments() {
        return payments;
    }

    public void setPayments(Character payments) {
        this.payments = payments;
    }

    public Integer getNumOfPmnts() {
        return numOfPmnts;
    }

    public void setNumOfPmnts(Integer numOfPmnts) {
        this.numOfPmnts = numOfPmnts;
    }

    public BigDecimal getPayment1() {
        return payment1;
    }

    public void setPayment1(BigDecimal payment1) {
        this.payment1 = payment1;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Integer getUserSign() {
        return userSign;
    }

    public void setUserSign(Integer userSign) {
        this.userSign = userSign;
    }

    public Character getOpenRcpt() {
        return openRcpt;
    }

    public void setOpenRcpt(Character openRcpt) {
        this.openRcpt = openRcpt;
    }

    public String getDiscCode() {
        return discCode;
    }

    public void setDiscCode(String discCode) {
        this.discCode = discCode;
    }

    public String getDunningCod() {
        return dunningCod;
    }

    public void setDunningCod(String dunningCod) {
        this.dunningCod = dunningCod;
    }

    public Character getBslineDate() {
        return bslineDate;
    }

    public void setBslineDate(Character bslineDate) {
        this.bslineDate = bslineDate;
    }

    public Integer getInstNum() {
        return instNum;
    }

    public void setInstNum(Integer instNum) {
        this.instNum = instNum;
    }

    public Integer getTolDays() {
        return tolDays;
    }

    public void setTolDays(Integer tolDays) {
        this.tolDays = tolDays;
    }

    public Character getvATFirst() {
        return vATFirst;
    }

    public void setvATFirst(Character vATFirst) {
        this.vATFirst = vATFirst;
    }

    public Character getCrdMthd() {
        return crdMthd;
    }

    public void setCrdMthd(Character crdMthd) {
        this.crdMthd = crdMthd;
    }
}
