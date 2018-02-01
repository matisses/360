package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class EmpleoAnteriorDTO {

    private int empID;
    private Integer logInstanc;
    private short line;
    private String employer;
    private String position;
    private String remarks;
    private Date fromDate;
    private Date toDate;

    public EmpleoAnteriorDTO() {
    }

    public EmpleoAnteriorDTO(int empID, Integer logInstanc, short line, String employer, String position, String remarks, Date fromDate, Date toDate) {
        this.empID = empID;
        this.logInstanc = logInstanc;
        this.line = line;
        this.employer = employer;
        this.position = position;
        this.remarks = remarks;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public short getLine() {
        return line;
    }

    public void setLine(short line) {
        this.line = line;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
