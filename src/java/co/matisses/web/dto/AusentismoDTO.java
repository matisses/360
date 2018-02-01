package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class AusentismoDTO {

    private int empID;
    private Integer cnfrmrNum;
    private Integer logInstanc;
    private Integer uhoras;
    private Integer uminutos;
    private Integer udias;
    private short line;
    private String reason;
    private String approvedBy;
    private String utype;
    private Date fromDate;
    private Date toDate;

    public AusentismoDTO() {
    }

    public AusentismoDTO(int empID, Integer cnfrmrNum, Integer logInstanc, Integer uhoras, Integer uminutos, Integer udias, short line, String reason, String approvedBy, String utype, Date fromDate, Date toDate) {
        this.empID = empID;
        this.cnfrmrNum = cnfrmrNum;
        this.logInstanc = logInstanc;
        this.uhoras = uhoras;
        this.uminutos = uminutos;
        this.udias = udias;
        this.line = line;
        this.reason = reason;
        this.approvedBy = approvedBy;
        this.utype = utype;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public Integer getCnfrmrNum() {
        return cnfrmrNum;
    }

    public void setCnfrmrNum(Integer cnfrmrNum) {
        this.cnfrmrNum = cnfrmrNum;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Integer getUhoras() {
        return uhoras;
    }

    public void setUhoras(Integer uhoras) {
        this.uhoras = uhoras;
    }

    public Integer getUminutos() {
        return uminutos;
    }

    public void setUminutos(Integer uminutos) {
        this.uminutos = uminutos;
    }

    public Integer getUdias() {
        return udias;
    }

    public void setUdias(Integer udias) {
        this.udias = udias;
    }

    public short getLine() {
        return line;
    }

    public void setLine(short line) {
        this.line = line;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getUtype() {
        return utype;
    }

    public void setUtype(String utype) {
        this.utype = utype;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
}
