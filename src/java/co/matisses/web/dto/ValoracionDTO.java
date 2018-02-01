package co.matisses.web.dto;

import java.util.Date;

/**
 *
 * @author ygil
 */
public class ValoracionDTO {

    private int empID;
    private Integer logInstanc;
    private Integer manager;
    private short line;
    private String reviewDesc;
    private String grade;
    private String remarks;
    private Date date;

    public ValoracionDTO() {
    }

    public ValoracionDTO(int empID, Integer logInstanc, Integer manager, short line, String reviewDesc, String grade, String remarks, Date date) {
        this.empID = empID;
        this.logInstanc = logInstanc;
        this.manager = manager;
        this.line = line;
        this.reviewDesc = reviewDesc;
        this.grade = grade;
        this.remarks = remarks;
        this.date = date;
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

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public short getLine() {
        return line;
    }

    public void setLine(short line) {
        this.line = line;
    }

    public String getReviewDesc() {
        return reviewDesc;
    }

    public void setReviewDesc(String reviewDesc) {
        this.reviewDesc = reviewDesc;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
