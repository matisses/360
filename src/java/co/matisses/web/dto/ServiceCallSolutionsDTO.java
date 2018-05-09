package co.matisses.web.dto;

import co.matisses.persistence.sap.entity.ServiceCallSolutions;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class ServiceCallSolutionsDTO {

    private Integer sltCode;
    private Integer statusNum;
    private Integer owner;
    private Integer createdBy;
    private Integer updateBy;
    private Integer atcEntry;
    private Integer instance;
    private String itemCode;
    private String subject;
    private String symptom;
    private String cause;
    private String descriptio;
    private String attachment;
    private Character transfered;
    private Date dateCreate;
    private Date dateUpdate;

    public ServiceCallSolutionsDTO() {
    }

    public ServiceCallSolutionsDTO(Integer sltCode, Integer statusNum, Integer owner, Integer createdBy, Integer updateBy, Integer atcEntry, Integer instance,
            String itemCode, String subject, String symptom, String cause, String descriptio, String attachment, Character transfered, Date dateCreate, Date dateUpdate) {
        this.sltCode = sltCode;
        this.statusNum = statusNum;
        this.owner = owner;
        this.createdBy = createdBy;
        this.updateBy = updateBy;
        this.atcEntry = atcEntry;
        this.instance = instance;
        this.itemCode = itemCode;
        this.subject = subject;
        this.symptom = symptom;
        this.cause = cause;
        this.descriptio = descriptio;
        this.attachment = attachment;
        this.transfered = transfered;
        this.dateCreate = dateCreate;
        this.dateUpdate = dateUpdate;
    }

    public ServiceCallSolutionsDTO(ServiceCallSolutions solution) {
        this.sltCode = solution.getSltCode();
        this.statusNum = solution.getStatusNum();
        this.owner = solution.getOwner();
        this.createdBy = solution.getCreatedBy();
        this.updateBy = solution.getUpdateBy();
        this.atcEntry = solution.getAtcEntry();
        this.instance = solution.getInstance();
        this.itemCode = solution.getItemCode();
        this.subject = solution.getSubject();
        this.symptom = solution.getSymptom();
        this.cause = solution.getCause();
        this.descriptio = solution.getDescriptio();
        this.attachment = solution.getAttachment();
        this.transfered = solution.getTransfered();
        this.dateCreate = solution.getDateCreate();
        this.dateUpdate = solution.getDateUpdate();
    }

    public Integer getSltCode() {
        return sltCode;
    }

    public void setSltCode(Integer sltCode) {
        this.sltCode = sltCode;
    }

    public Integer getStatusNum() {
        return statusNum;
    }

    public void setStatusNum(Integer statusNum) {
        this.statusNum = statusNum;
    }

    public Integer getOwner() {
        return owner;
    }

    public void setOwner(Integer owner) {
        this.owner = owner;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    public Integer getInstance() {
        return instance;
    }

    public void setInstance(Integer instance) {
        this.instance = instance;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getDescriptio() {
        return descriptio;
    }

    public void setDescriptio(String descriptio) {
        this.descriptio = descriptio;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public Character getTransfered() {
        return transfered;
    }

    public void setTransfered(Character transfered) {
        this.transfered = transfered;
    }

    public Date getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(Date dateCreate) {
        this.dateCreate = dateCreate;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public void setDateUpdate(Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }
}
