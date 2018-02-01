package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class FormacionDTO {

    private int empID;
    private Integer type;
    private Integer logInstanc;
    private short line;
    private String institute;
    private String major;
    private String diploma;
    private Date fromDate;
    private Date toDate;

    public FormacionDTO() {
    }

    public FormacionDTO(int empID, Integer type, Integer logInstanc, short line, String institute, String major, String diploma, Date fromDate, Date toDate) {
        this.empID = empID;
        this.type = type;
        this.logInstanc = logInstanc;
        this.line = line;
        this.institute = institute;
        this.major = major;
        this.diploma = diploma;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getDiploma() {
        return diploma;
    }

    public void setDiploma(String diploma) {
        this.diploma = diploma;
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
