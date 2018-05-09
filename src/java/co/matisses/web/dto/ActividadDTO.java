package co.matisses.web.dto;

import co.matisses.persistence.sap.entity.Actividad;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class ActividadDTO {

    private Integer clgCode;
    private Integer cntctSbjct;
    private Integer attendUser;
    private Integer cntctCode;
    private Integer userSign;
    private Integer slpCode;
    private Integer cntctType;
    private Integer location;
    private Integer beginTime;
    private Integer eNDTime;
    private Integer oprId;
    private Integer oprLine;
    private Integer remTime;
    private Integer instance;
    private Integer status;
    private Integer parentId;
    private Integer prevActvty;
    private Integer atcEntry;
    private Integer dayInMonth;
    private Integer month;
    private Integer dayOfWeek;
    private Integer week;
    private Integer seriesNum;
    private Integer assignedBy;
    private Integer attendEmpl;
    private Integer nextTime;
    private Integer ownerCode;
    private Integer maxOccur;
    private Integer interval;
    private String cntctTime;
    private String cardCode;
    private String notes;
    private String contactPer;
    private String tel;
    private String fax;
    private String docType;
    private String docNum;
    private String docEntry;
    private String attachment;
    private String details;
    private String street;
    private String city;
    private String country;
    private String state;
    private String room;
    private String parentType;
    private String addrName;
    private Character closed;
    private Character transfered;
    private Character dataSource;
    private Character action;
    private Character durType;
    private Character priority;
    private Character reminder;
    private Character remType;
    private Character remSented;
    private Character personal;
    private Character inactive;
    private Character tentative;
    private Character recurPat;
    private Character endType;
    private Character sunday;
    private Character monday;
    private Character tuesday;
    private Character wednesday;
    private Character thursday;
    private Character friday;
    private Character saturday;
    private Character subOption;
    private Character isRemoved;
    private Character addrType;
    private BigDecimal duration;
    private BigDecimal remQty;
    private Date cntctDate;
    private Date recontact;
    private Date closeDate;
    private Date remDate;
    private Date endDate;
    private Date seStartDat;
    private Date seEndDat;
    private Date origDate;
    private Date lastRemind;
    private Date nextDate;

    public ActividadDTO() {
    }

    public ActividadDTO(Integer clgCode) {
        this.clgCode = clgCode;
    }

    public ActividadDTO(Actividad actividad) {
        this.clgCode = actividad.getClgCode();
        this.cntctTime = actividad.getCntctTime().toString().substring(0, 2) + ":" + actividad.getCntctTime().toString().substring(2);
        this.cntctSbjct = actividad.getCntctSbjct();
        this.attendUser = actividad.getAttendUser();
        this.cntctCode = actividad.getCntctCode();
        this.userSign = actividad.getUserSign();
        this.slpCode = actividad.getSlpCode();
        this.cntctType = actividad.getCntctType();
        this.location = actividad.getLocation();
        this.beginTime = actividad.getBeginTime();
        this.eNDTime = actividad.geteNDTime();
        this.oprId = actividad.getOprId();
        this.oprLine = actividad.getOprLine();
        this.remTime = actividad.getRemTime();
        this.instance = actividad.getInstance();
        this.status = actividad.getStatus();
        this.parentId = actividad.getParentId();
        this.prevActvty = actividad.getPrevActvty();
        this.atcEntry = actividad.getAtcEntry();
        this.dayInMonth = actividad.getDayInMonth();
        this.month = actividad.getMonth();
        this.dayOfWeek = actividad.getDayOfWeek();
        this.week = actividad.getWeek();
        this.seriesNum = actividad.getSeriesNum();
        this.assignedBy = actividad.getAssignedBy();
        this.attendEmpl = actividad.getAttendEmpl();
        this.nextTime = actividad.getNextTime();
        this.ownerCode = actividad.getOwnerCode();
        this.maxOccur = actividad.getMaxOccur();
        this.interval = actividad.getInterval();
        this.cardCode = actividad.getCardCode();
        this.notes = actividad.getNotes();
        this.contactPer = actividad.getContactPer();
        this.tel = actividad.getTel();
        this.fax = actividad.getFax();
        this.docType = actividad.getDocType();
        this.docNum = actividad.getDocNum();
        this.docEntry = actividad.getDocEntry();
        this.attachment = actividad.getAttachment();
        this.details = actividad.getDetails();
        this.street = actividad.getStreet();
        this.city = actividad.getCity();
        this.country = actividad.getCountry();
        this.state = actividad.getState();
        this.room = actividad.getRoom();
        this.parentType = actividad.getParentType();
        this.addrName = actividad.getAddrName();
        this.closed = actividad.getClosed();
        this.transfered = actividad.getTransfered();
        this.dataSource = actividad.getDataSource();
        this.action = actividad.getAction();
        this.durType = actividad.getDurType();
        this.priority = actividad.getPriority();
        this.reminder = actividad.getReminder();
        this.remType = actividad.getRemType();
        this.remSented = actividad.getRemSented();
        this.personal = actividad.getPersonal();
        this.inactive = actividad.getInactive();
        this.tentative = actividad.getTentative();
        this.recurPat = actividad.getRecurPat();
        this.endType = actividad.getEndType();
        this.sunday = actividad.getSunday();
        this.monday = actividad.getMonday();
        this.tuesday = actividad.getTuesday();
        this.wednesday = actividad.getWednesday();
        this.thursday = actividad.getThursday();
        this.friday = actividad.getFriday();
        this.saturday = actividad.getSaturday();
        this.subOption = actividad.getSubOption();
        this.isRemoved = actividad.getIsRemoved();
        this.addrType = actividad.getAddrType();
        this.duration = actividad.getDuration();
        this.remQty = actividad.getRemQty();
        this.cntctDate = actividad.getCntctDate();
        this.recontact = actividad.getRecontact();
        this.closeDate = actividad.getCloseDate();
        this.remDate = actividad.getRemDate();
        this.endDate = actividad.getEndDate();
        this.seStartDat = actividad.getSeStartDat();
        this.seEndDat = actividad.getSeEndDat();
        this.origDate = actividad.getOrigDate();
        this.lastRemind = actividad.getLastRemind();
        this.nextDate = actividad.getNextDate();
    }

    public ActividadDTO(Integer clgCode, String cntctTime, Integer cntctSbjct, Integer attendUser, Integer cntctCode, Integer userSign, Integer slpCode, Integer cntctType,
            Integer location, Integer beginTime, Integer eNDTime, Integer oprId, Integer oprLine, Integer remTime, Integer instance, Integer status, Integer parentId,
            Integer prevActvty, Integer atcEntry, Integer dayInMonth, Integer month, Integer dayOfWeek, Integer week, Integer seriesNum, Integer assignedBy, Integer attendEmpl,
            Integer nextTime, Integer ownerCode, Integer maxOccur, Integer interval, String cardCode, String notes, String contactPer, String tel, String fax, String docType,
            String docNum, String docEntry, String attachment, String details, String street, String city, String country, String state, String room, String parentType,
            String addrName, Character closed, Character transfered, Character dataSource, Character action, Character durType, Character priority, Character reminder,
            Character remType, Character remSented, Character personal, Character inactive, Character tentative, Character recurPat, Character endType, Character sunday,
            Character monday, Character tuesday, Character wednesday, Character thursday, Character friday, Character saturday, Character subOption, Character isRemoved,
            Character addrType, BigDecimal duration, BigDecimal remQty, Date cntctDate, Date recontact, Date closeDate, Date remDate, Date endDate, Date seStartDat, Date seEndDat,
            Date origDate, Date lastRemind, Date nextDate) {
        this.clgCode = clgCode;
        this.cntctTime = cntctTime;
        this.cntctSbjct = cntctSbjct;
        this.attendUser = attendUser;
        this.cntctCode = cntctCode;
        this.userSign = userSign;
        this.slpCode = slpCode;
        this.cntctType = cntctType;
        this.location = location;
        this.beginTime = beginTime;
        this.eNDTime = eNDTime;
        this.oprId = oprId;
        this.oprLine = oprLine;
        this.remTime = remTime;
        this.instance = instance;
        this.status = status;
        this.parentId = parentId;
        this.prevActvty = prevActvty;
        this.atcEntry = atcEntry;
        this.dayInMonth = dayInMonth;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.week = week;
        this.seriesNum = seriesNum;
        this.assignedBy = assignedBy;
        this.attendEmpl = attendEmpl;
        this.nextTime = nextTime;
        this.ownerCode = ownerCode;
        this.maxOccur = maxOccur;
        this.interval = interval;
        this.cardCode = cardCode;
        this.notes = notes;
        this.contactPer = contactPer;
        this.tel = tel;
        this.fax = fax;
        this.docType = docType;
        this.docNum = docNum;
        this.docEntry = docEntry;
        this.attachment = attachment;
        this.details = details;
        this.street = street;
        this.city = city;
        this.country = country;
        this.state = state;
        this.room = room;
        this.parentType = parentType;
        this.addrName = addrName;
        this.closed = closed;
        this.transfered = transfered;
        this.dataSource = dataSource;
        this.action = action;
        this.durType = durType;
        this.priority = priority;
        this.reminder = reminder;
        this.remType = remType;
        this.remSented = remSented;
        this.personal = personal;
        this.inactive = inactive;
        this.tentative = tentative;
        this.recurPat = recurPat;
        this.endType = endType;
        this.sunday = sunday;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.subOption = subOption;
        this.isRemoved = isRemoved;
        this.addrType = addrType;
        this.duration = duration;
        this.remQty = remQty;
        this.cntctDate = cntctDate;
        this.recontact = recontact;
        this.closeDate = closeDate;
        this.remDate = remDate;
        this.endDate = endDate;
        this.seStartDat = seStartDat;
        this.seEndDat = seEndDat;
        this.origDate = origDate;
        this.lastRemind = lastRemind;
        this.nextDate = nextDate;
    }

    public Integer getClgCode() {
        return clgCode;
    }

    public void setClgCode(Integer clgCode) {
        this.clgCode = clgCode;
    }

    public String getCntctTime() {
        return cntctTime;
    }

    public void setCntctTime(String cntctTime) {
        this.cntctTime = cntctTime;
    }

    public Integer getCntctSbjct() {
        return cntctSbjct;
    }

    public void setCntctSbjct(Integer cntctSbjct) {
        this.cntctSbjct = cntctSbjct;
    }

    public Integer getAttendUser() {
        return attendUser;
    }

    public void setAttendUser(Integer attendUser) {
        this.attendUser = attendUser;
    }

    public Integer getCntctCode() {
        return cntctCode;
    }

    public void setCntctCode(Integer cntctCode) {
        this.cntctCode = cntctCode;
    }

    public Integer getUserSign() {
        return userSign;
    }

    public void setUserSign(Integer userSign) {
        this.userSign = userSign;
    }

    public Integer getSlpCode() {
        return slpCode;
    }

    public void setSlpCode(Integer slpCode) {
        this.slpCode = slpCode;
    }

    public Integer getCntctType() {
        return cntctType;
    }

    public void setCntctType(Integer cntctType) {
        this.cntctType = cntctType;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Integer beginTime) {
        this.beginTime = beginTime;
    }

    public Integer geteNDTime() {
        return eNDTime;
    }

    public void seteNDTime(Integer eNDTime) {
        this.eNDTime = eNDTime;
    }

    public Integer getOprId() {
        return oprId;
    }

    public void setOprId(Integer oprId) {
        this.oprId = oprId;
    }

    public Integer getOprLine() {
        return oprLine;
    }

    public void setOprLine(Integer oprLine) {
        this.oprLine = oprLine;
    }

    public Integer getRemTime() {
        return remTime;
    }

    public void setRemTime(Integer remTime) {
        this.remTime = remTime;
    }

    public Integer getInstance() {
        return instance;
    }

    public void setInstance(Integer instance) {
        this.instance = instance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPrevActvty() {
        return prevActvty;
    }

    public void setPrevActvty(Integer prevActvty) {
        this.prevActvty = prevActvty;
    }

    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    public Integer getDayInMonth() {
        return dayInMonth;
    }

    public void setDayInMonth(Integer dayInMonth) {
        this.dayInMonth = dayInMonth;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Integer getSeriesNum() {
        return seriesNum;
    }

    public void setSeriesNum(Integer seriesNum) {
        this.seriesNum = seriesNum;
    }

    public Integer getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(Integer assignedBy) {
        this.assignedBy = assignedBy;
    }

    public Integer getAttendEmpl() {
        return attendEmpl;
    }

    public void setAttendEmpl(Integer attendEmpl) {
        this.attendEmpl = attendEmpl;
    }

    public Integer getNextTime() {
        return nextTime;
    }

    public void setNextTime(Integer nextTime) {
        this.nextTime = nextTime;
    }

    public Integer getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Integer getMaxOccur() {
        return maxOccur;
    }

    public void setMaxOccur(Integer maxOccur) {
        this.maxOccur = maxOccur;
    }

    public Integer getInterval() {
        return interval;
    }

    public void setInterval(Integer interval) {
        this.interval = interval;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String cardCode) {
        this.cardCode = cardCode;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getContactPer() {
        return contactPer;
    }

    public void setContactPer(String contactPer) {
        this.contactPer = contactPer;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String docType) {
        this.docType = docType;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String docNum) {
        this.docNum = docNum;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String docEntry) {
        this.docEntry = docEntry;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getParentType() {
        return parentType;
    }

    public void setParentType(String parentType) {
        this.parentType = parentType;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
    }

    public Character getClosed() {
        return closed;
    }

    public void setClosed(Character closed) {
        this.closed = closed;
    }

    public Character getTransfered() {
        return transfered;
    }

    public void setTransfered(Character transfered) {
        this.transfered = transfered;
    }

    public Character getDataSource() {
        return dataSource;
    }

    public void setDataSource(Character dataSource) {
        this.dataSource = dataSource;
    }

    public Character getAction() {
        return action;
    }

    public void setAction(Character action) {
        this.action = action;
    }

    public Character getDurType() {
        return durType;
    }

    public void setDurType(Character durType) {
        this.durType = durType;
    }

    public Character getPriority() {
        return priority;
    }

    public void setPriority(Character priority) {
        this.priority = priority;
    }

    public Character getReminder() {
        return reminder;
    }

    public void setReminder(Character reminder) {
        this.reminder = reminder;
    }

    public Character getRemType() {
        return remType;
    }

    public void setRemType(Character remType) {
        this.remType = remType;
    }

    public Character getRemSented() {
        return remSented;
    }

    public void setRemSented(Character remSented) {
        this.remSented = remSented;
    }

    public Character getPersonal() {
        return personal;
    }

    public void setPersonal(Character personal) {
        this.personal = personal;
    }

    public Character getInactive() {
        return inactive;
    }

    public void setInactive(Character inactive) {
        this.inactive = inactive;
    }

    public Character getTentative() {
        return tentative;
    }

    public void setTentative(Character tentative) {
        this.tentative = tentative;
    }

    public Character getRecurPat() {
        return recurPat;
    }

    public void setRecurPat(Character recurPat) {
        this.recurPat = recurPat;
    }

    public Character getEndType() {
        return endType;
    }

    public void setEndType(Character endType) {
        this.endType = endType;
    }

    public Character getSunday() {
        return sunday;
    }

    public void setSunday(Character sunday) {
        this.sunday = sunday;
    }

    public Character getMonday() {
        return monday;
    }

    public void setMonday(Character monday) {
        this.monday = monday;
    }

    public Character getTuesday() {
        return tuesday;
    }

    public void setTuesday(Character tuesday) {
        this.tuesday = tuesday;
    }

    public Character getWednesday() {
        return wednesday;
    }

    public void setWednesday(Character wednesday) {
        this.wednesday = wednesday;
    }

    public Character getThursday() {
        return thursday;
    }

    public void setThursday(Character thursday) {
        this.thursday = thursday;
    }

    public Character getFriday() {
        return friday;
    }

    public void setFriday(Character friday) {
        this.friday = friday;
    }

    public Character getSaturday() {
        return saturday;
    }

    public void setSaturday(Character saturday) {
        this.saturday = saturday;
    }

    public Character getSubOption() {
        return subOption;
    }

    public void setSubOption(Character subOption) {
        this.subOption = subOption;
    }

    public Character getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(Character isRemoved) {
        this.isRemoved = isRemoved;
    }

    public Character getAddrType() {
        return addrType;
    }

    public void setAddrType(Character addrType) {
        this.addrType = addrType;
    }

    public BigDecimal getDuration() {
        return duration;
    }

    public void setDuration(BigDecimal duration) {
        this.duration = duration;
    }

    public BigDecimal getRemQty() {
        return remQty;
    }

    public void setRemQty(BigDecimal remQty) {
        this.remQty = remQty;
    }

    public Date getCntctDate() {
        return cntctDate;
    }

    public void setCntctDate(Date cntctDate) {
        this.cntctDate = cntctDate;
    }

    public Date getRecontact() {
        return recontact;
    }

    public void setRecontact(Date recontact) {
        this.recontact = recontact;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getRemDate() {
        return remDate;
    }

    public void setRemDate(Date remDate) {
        this.remDate = remDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getSeStartDat() {
        return seStartDat;
    }

    public void setSeStartDat(Date seStartDat) {
        this.seStartDat = seStartDat;
    }

    public Date getSeEndDat() {
        return seEndDat;
    }

    public void setSeEndDat(Date seEndDat) {
        this.seEndDat = seEndDat;
    }

    public Date getOrigDate() {
        return origDate;
    }

    public void setOrigDate(Date origDate) {
        this.origDate = origDate;
    }

    public Date getLastRemind() {
        return lastRemind;
    }

    public void setLastRemind(Date lastRemind) {
        this.lastRemind = lastRemind;
    }

    public Date getNextDate() {
        return nextDate;
    }

    public void setNextDate(Date nextDate) {
        this.nextDate = nextDate;
    }
}
