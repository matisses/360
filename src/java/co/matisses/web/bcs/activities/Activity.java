package co.matisses.web.bcs.activities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "Activity")
public class Activity {

    @XmlElement(name = "ActivityCode")
    protected Long activityCode;
    @XmlElement(name = "CardCode")
    protected String cardCode;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "ActivityDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar activityDate;
    @XmlElement(name = "ActivityTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar activityTime;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "Closed")
    protected String closed;
    @XmlElement(name = "CloseDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar closeDate;
    @XmlElement(name = "Phone")
    protected String phone;
    @XmlElement(name = "Fax")
    protected String fax;
    @XmlElement(name = "Subject")
    protected Long subject;
    @XmlElement(name = "DocType")
    protected String docType;
    @XmlElement(name = "DocNum")
    protected String docNum;
    @XmlElement(name = "DocEntry")
    protected String docEntry;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "Details")
    protected String details;
    @XmlElement(name = "Activity")
    protected String activity;
    @XmlElement(name = "ActivityType")
    protected Long activityType;
    @XmlElement(name = "Location")
    protected Long location;
    @XmlElement(name = "StartTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "Duration")
    protected Double duration;
    @XmlElement(name = "DurationType")
    protected String durationType;
    @XmlElement(name = "SalesEmployee")
    protected Long salesEmployee;
    @XmlElement(name = "ContactPersonCode")
    protected Long contactPersonCode;
    @XmlElement(name = "HandledBy")
    protected Long handledBy;
    @XmlElement(name = "Reminder")
    protected String reminder;
    @XmlElement(name = "ReminderPeriod")
    protected Double reminderPeriod;
    @XmlElement(name = "ReminderType")
    protected String reminderType;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "PersonalFlag")
    protected String personalFlag;
    @XmlElement(name = "Street")
    protected String street;
    @XmlElement(name = "ParentObjectId")
    protected Long parentObjectId;
    @XmlElement(name = "ParentObjectType")
    protected String parentObjectType;
    @XmlElement(name = "Room")
    protected String room;
    @XmlElement(name = "InactiveFlag")
    protected String inactiveFlag;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "PreviousActivity")
    protected Long previousActivity;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "Status")
    protected Long status;
    @XmlElement(name = "TentativeFlag")
    protected String tentativeFlag;
    @XmlElement(name = "EndDueDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDueDate;
    @XmlElement(name = "DocTypeEx")
    protected String docTypeEx;
    @XmlElement(name = "AttachmentEntry")
    protected Long attachmentEntry;
    @XmlElement(name = "RecurrencePattern")
    protected String recurrencePattern;
    @XmlElement(name = "EndType")
    protected String endType;
    @XmlElement(name = "SeriesStartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar seriesStartDate;
    @XmlElement(name = "SeriesEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar seriesEndDate;
    @XmlElement(name = "MaxOccurrence")
    protected Long maxOccurrence;
    @XmlElement(name = "Interval")
    protected Long interval;
    @XmlElement(name = "Sunday")
    protected String sunday;
    @XmlElement(name = "Monday")
    protected String monday;
    @XmlElement(name = "Tuesday")
    protected String tuesday;
    @XmlElement(name = "Wednesday")
    protected String wednesday;
    @XmlElement(name = "Thursday")
    protected String thursday;
    @XmlElement(name = "Friday")
    protected String friday;
    @XmlElement(name = "Saturday")
    protected String saturday;
    @XmlElement(name = "RepeatOption")
    protected String repeatOption;
    @XmlElement(name = "BelongedSeriesNum")
    protected Long belongedSeriesNum;
    @XmlElement(name = "IsRemoved")
    protected String isRemoved;
    @XmlElement(name = "AddressName")
    protected String addressName;
    @XmlElement(name = "AddressType")
    protected String addressType;
    @XmlElement(name = "HandledByEmployee")
    protected Long handledByEmployee;
    @XmlElement(name = "RecurrenceSequenceSpecifier")
    protected String recurrenceSequenceSpecifier;
    @XmlElement(name = "RecurrenceDayInMonth")
    protected Long recurrenceDayInMonth;
    @XmlElement(name = "RecurrenceMonth")
    protected Long recurrenceMonth;
    @XmlElement(name = "RecurrenceDayOfWeek")
    protected String recurrenceDayOfWeek;
    @XmlElement(name = "SalesOpportunityId")
    protected Long salesOpportunityId;
    @XmlElement(name = "SalesOpportunityLine")
    protected Long salesOpportunityLine;

    public Long getActivityCode() {
        return activityCode;
    }

    public void setActivityCode(Long value) {
        this.activityCode = value;
    }

    public String getCardCode() {
        return cardCode;
    }

    public void setCardCode(String value) {
        this.cardCode = value;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String value) {
        this.notes = value;
    }

    public XMLGregorianCalendar getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(XMLGregorianCalendar value) {
        this.activityDate = value;
    }

    public XMLGregorianCalendar getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(XMLGregorianCalendar value) {
        this.activityTime = value;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    public String getClosed() {
        return closed;
    }

    public void setClosed(String value) {
        this.closed = value;
    }

    public XMLGregorianCalendar getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(XMLGregorianCalendar value) {
        this.closeDate = value;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String value) {
        this.phone = value;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String value) {
        this.fax = value;
    }

    public Long getSubject() {
        return subject;
    }

    public void setSubject(Long value) {
        this.subject = value;
    }

    public String getDocType() {
        return docType;
    }

    public void setDocType(String value) {
        this.docType = value;
    }

    public String getDocNum() {
        return docNum;
    }

    public void setDocNum(String value) {
        this.docNum = value;
    }

    public String getDocEntry() {
        return docEntry;
    }

    public void setDocEntry(String value) {
        this.docEntry = value;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String value) {
        this.priority = value;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String value) {
        this.details = value;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String value) {
        this.activity = value;
    }

    public Long getActivityType() {
        return activityType;
    }

    public void setActivityType(Long value) {
        this.activityType = value;
    }

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long value) {
        this.location = value;
    }

    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    public XMLGregorianCalendar getEndTime() {
        return endTime;
    }

    public void setEndTime(XMLGregorianCalendar value) {
        this.endTime = value;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double value) {
        this.duration = value;
    }

    public String getDurationType() {
        return durationType;
    }

    public void setDurationType(String value) {
        this.durationType = value;
    }

    public Long getSalesEmployee() {
        return salesEmployee;
    }

    public void setSalesEmployee(Long value) {
        this.salesEmployee = value;
    }

    public Long getContactPersonCode() {
        return contactPersonCode;
    }

    public void setContactPersonCode(Long value) {
        this.contactPersonCode = value;
    }

    public Long getHandledBy() {
        return handledBy;
    }

    public void setHandledBy(Long value) {
        this.handledBy = value;
    }

    public String getReminder() {
        return reminder;
    }

    public void setReminder(String value) {
        this.reminder = value;
    }

    public Double getReminderPeriod() {
        return reminderPeriod;
    }

    public void setReminderPeriod(Double value) {
        this.reminderPeriod = value;
    }

    public String getReminderType() {
        return reminderType;
    }

    public void setReminderType(String value) {
        this.reminderType = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getPersonalFlag() {
        return personalFlag;
    }

    public void setPersonalFlag(String value) {
        this.personalFlag = value;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String value) {
        this.street = value;
    }

    public Long getParentObjectId() {
        return parentObjectId;
    }

    public void setParentObjectId(Long value) {
        this.parentObjectId = value;
    }

    public String getParentObjectType() {
        return parentObjectType;
    }

    public void setParentObjectType(String value) {
        this.parentObjectType = value;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String value) {
        this.room = value;
    }

    public String getInactiveFlag() {
        return inactiveFlag;
    }

    public void setInactiveFlag(String value) {
        this.inactiveFlag = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public Long getPreviousActivity() {
        return previousActivity;
    }

    public void setPreviousActivity(Long value) {
        this.previousActivity = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long value) {
        this.status = value;
    }

    public String getTentativeFlag() {
        return tentativeFlag;
    }

    public void setTentativeFlag(String value) {
        this.tentativeFlag = value;
    }

    public XMLGregorianCalendar getEndDueDate() {
        return endDueDate;
    }

    public void setEndDueDate(XMLGregorianCalendar value) {
        this.endDueDate = value;
    }

    public String getDocTypeEx() {
        return docTypeEx;
    }

    public void setDocTypeEx(String value) {
        this.docTypeEx = value;
    }

    public Long getAttachmentEntry() {
        return attachmentEntry;
    }

    public void setAttachmentEntry(Long value) {
        this.attachmentEntry = value;
    }

    public String getRecurrencePattern() {
        return recurrencePattern;
    }

    public void setRecurrencePattern(String value) {
        this.recurrencePattern = value;
    }

    public String getEndType() {
        return endType;
    }

    public void setEndType(String value) {
        this.endType = value;
    }

    public XMLGregorianCalendar getSeriesStartDate() {
        return seriesStartDate;
    }

    public void setSeriesStartDate(XMLGregorianCalendar value) {
        this.seriesStartDate = value;
    }

    public XMLGregorianCalendar getSeriesEndDate() {
        return seriesEndDate;
    }

    public void setSeriesEndDate(XMLGregorianCalendar value) {
        this.seriesEndDate = value;
    }

    public Long getMaxOccurrence() {
        return maxOccurrence;
    }

    public void setMaxOccurrence(Long value) {
        this.maxOccurrence = value;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long value) {
        this.interval = value;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String value) {
        this.sunday = value;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String value) {
        this.monday = value;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String value) {
        this.tuesday = value;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String value) {
        this.wednesday = value;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String value) {
        this.thursday = value;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String value) {
        this.friday = value;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String value) {
        this.saturday = value;
    }

    public String getRepeatOption() {
        return repeatOption;
    }

    public void setRepeatOption(String value) {
        this.repeatOption = value;
    }

    public Long getBelongedSeriesNum() {
        return belongedSeriesNum;
    }

    public void setBelongedSeriesNum(Long value) {
        this.belongedSeriesNum = value;
    }

    public String getIsRemoved() {
        return isRemoved;
    }

    public void setIsRemoved(String value) {
        this.isRemoved = value;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String value) {
        this.addressName = value;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String value) {
        this.addressType = value;
    }

    public Long getHandledByEmployee() {
        return handledByEmployee;
    }

    public void setHandledByEmployee(Long value) {
        this.handledByEmployee = value;
    }

    public String getRecurrenceSequenceSpecifier() {
        return recurrenceSequenceSpecifier;
    }

    public void setRecurrenceSequenceSpecifier(String value) {
        this.recurrenceSequenceSpecifier = value;
    }

    public Long getRecurrenceDayInMonth() {
        return recurrenceDayInMonth;
    }

    public void setRecurrenceDayInMonth(Long value) {
        this.recurrenceDayInMonth = value;
    }

    public Long getRecurrenceMonth() {
        return recurrenceMonth;
    }

    public void setRecurrenceMonth(Long value) {
        this.recurrenceMonth = value;
    }

    public String getRecurrenceDayOfWeek() {
        return recurrenceDayOfWeek;
    }

    public void setRecurrenceDayOfWeek(String value) {
        this.recurrenceDayOfWeek = value;
    }

    public Long getSalesOpportunityId() {
        return salesOpportunityId;
    }

    public void setSalesOpportunityId(Long value) {
        this.salesOpportunityId = value;
    }

    public Long getSalesOpportunityLine() {
        return salesOpportunityLine;
    }

    public void setSalesOpportunityLine(Long value) {
        this.salesOpportunityLine = value;
    }
}
