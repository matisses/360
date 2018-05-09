package co.matisses.web.bcs.servicecalls;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {})
@XmlRootElement(name = "ServiceCall")
public class ServiceCall {

    @XmlElement(name = "ServiceCallID")
    protected Long serviceCallID;
    @XmlElement(name = "Subject")
    protected String subject;
    @XmlElement(name = "CustomerCode")
    protected String customerCode;
    @XmlElement(name = "CustomerName")
    protected String customerName;
    @XmlElement(name = "ContactCode")
    protected Long contactCode;
    @XmlElement(name = "ManufacturerSerialNum")
    protected String manufacturerSerialNum;
    @XmlElement(name = "InternalSerialNum")
    protected String internalSerialNum;
    @XmlElement(name = "ContractID")
    protected Long contractID;
    @XmlElement(name = "ContractEndDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar contractEndDate;
    @XmlElement(name = "ResolutionDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar resolutionDate;
    @XmlElement(name = "ResolutionTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar resolutionTime;
    @XmlElement(name = "Origin")
    protected Long origin;
    @XmlElement(name = "ItemCode")
    protected String itemCode;
    @XmlElement(name = "ItemDescription")
    protected String itemDescription;
    @XmlElement(name = "ItemGroupCode")
    protected Long itemGroupCode;
    @XmlElement(name = "Status")
    protected Long status;
    @XmlElement(name = "Priority")
    protected String priority;
    @XmlElement(name = "CallType")
    protected Long callType;
    @XmlElement(name = "ProblemType")
    protected Long problemType;
    @XmlElement(name = "AssigneeCode")
    protected Long assigneeCode;
    @XmlElement(name = "Description")
    protected String description;
    @XmlElement(name = "TechnicianCode")
    protected Long technicianCode;
    @XmlElement(name = "Resolution")
    protected String resolution;
    @XmlElement(name = "CreationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar creationDate;
    @XmlElement(name = "CreationTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar creationTime;
    @XmlElement(name = "Responder")
    protected Long responder;
    @XmlElement(name = "UpdatedTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar updatedTime;
    @XmlElement(name = "BelongsToAQueue")
    protected String belongsToAQueue;
    @XmlElement(name = "ResponseByTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar responseByTime;
    @XmlElement(name = "ResponseByDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar responseByDate;
    @XmlElement(name = "ResolutionOnDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar resolutionOnDate;
    @XmlElement(name = "ResponseOnTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar responseOnTime;
    @XmlElement(name = "ResponseOnDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar responseOnDate;
    @XmlElement(name = "ClosingTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar closingTime;
    @XmlElement(name = "AssignedDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar assignedDate;
    @XmlElement(name = "Queue")
    protected String queue;
    @XmlElement(name = "ResponseAssignee")
    protected Long responseAssignee;
    @XmlElement(name = "EntitledforService")
    protected String entitledforService;
    @XmlElement(name = "ResolutionOnTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar resolutionOnTime;
    @XmlElement(name = "AssignedTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar assignedTime;
    @XmlElement(name = "ClosingDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar closingDate;
    @XmlElement(name = "Series")
    protected Long series;
    @XmlElement(name = "DocNum")
    protected Long docNum;
    @XmlElement(name = "HandWritten")
    protected String handWritten;
    @XmlElement(name = "PeriodIndicator")
    protected String periodIndicator;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    @XmlElement(name = "StartTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar startTime;
    @XmlElement(name = "EndDueDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar endDueDate;
    @XmlElement(name = "EndTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar endTime;
    @XmlElement(name = "Duration")
    protected Double duration;
    @XmlElement(name = "DurationType")
    protected String durationType;
    @XmlElement(name = "Reminder")
    protected String reminder;
    @XmlElement(name = "ReminderPeriod")
    protected Double reminderPeriod;
    @XmlElement(name = "ReminderType")
    protected String reminderType;
    @XmlElement(name = "Location")
    protected Long location;
    @XmlElement(name = "AddressName")
    protected String addressName;
    @XmlElement(name = "AddressType")
    protected String addressType;
    @XmlElement(name = "Street")
    protected String street;
    @XmlElement(name = "City")
    protected String city;
    @XmlElement(name = "Room")
    protected String room;
    @XmlElement(name = "State")
    protected String state;
    @XmlElement(name = "Country")
    protected String country;
    @XmlElement(name = "DisplayInCalendar")
    protected String displayInCalendar;
    @XmlElement(name = "CustomerRefNo")
    protected String customerRefNo;
    @XmlElement(name = "ProblemSubType")
    protected Long problemSubType;
    @XmlElement(name = "AttachmentEntry")
    protected Long attachmentEntry;
    @XmlElement(name = "U_Causa")
    protected String uCausa;
    @XmlElement(name = "U_CateGa")
    protected String uCateGa;
    @XmlElement(name = "U_ProExt")
    protected String uProExt;
    @XmlElement(name = "U_Num_factura")
    protected String uNumFactura;
    @XmlElement(name = "U_TipoServ")
    protected String uTipoServ;
    @XmlElement(name = "U_NWR_RMA")
    protected String unwrrma;
    @XmlElement(name = "U_NWR_PO")
    protected String unwrpo;
    @XmlElement(name = "U_NWR_RMA_Type")
    protected Long unwrrmaType;
    @XmlElement(name = "U_ComentSoporte")
    protected String uComentSoporte;
    @XmlElement(name = "U_Material")
    protected String uMaterial;
    @XmlElement(name = "U_ProblemaMaterial")
    protected String uProblemaMaterial;
    @XmlElement(name = "U_crearSoporte")
    protected String uCrearSoporte;
    @XmlElement(name = "ServiceCallActivities")
    protected ServiceCall.ServiceCallActivities serviceCallActivities;
    @XmlElement(name = "ServiceCallInventoryExpenses")
    protected ServiceCall.ServiceCallInventoryExpenses serviceCallInventoryExpenses;
    @XmlElement(name = "ServiceCallSolutions")
    protected ServiceCall.ServiceCallSolutions serviceCallSolutions;

    /*Variables manuales debido a error*/
    @XmlElement(name = "ServiceBPType")
    protected String serviceBPType;
    @XmlElement(name = "BPeMail")
    protected String bPeMail;
    @XmlElement(name = "BPShipToCode")
    protected String bPShipToCode;
    @XmlElement(name = "BPShipToAddress")
    protected String bPShipToAddress;
    @XmlElement(name = "BPBillToCode")
    protected String bPBillToCode;
    @XmlElement(name = "BPBillToAddress")
    protected String bPBillToAddress;
    @XmlElement(name = "BPPhone1")
    protected String bPPhone1;
    @XmlElement(name = "BPPhone2")
    protected String bPPhone2;
    @XmlElement(name = "BPCellular")
    protected String bPCellular;
    @XmlElement(name = "BPFax")
    protected String bPFax;
    @XmlElement(name = "BPContactPerson")
    protected String bPContactPerson;
    @XmlElement(name = "ServiceCallSchedulings")
    protected ServiceCall.ServiceCallSchedulings serviceCallSchedulings;
    @XmlElement(name = "ServiceCallBPAddressComponents")
    protected ServiceCall.ServiceCallBPAddressComponents serviceCallBPAddressComponents;
    protected Date fechaCreacion;
    protected String horaCreacion;
    protected Date fechaCierre;
    protected String horaCierre;
    protected Date fechaFinalizado;
    protected Date fechaInicio;
    protected String horaInicio;
    protected Date fechaFin;
    protected String horaFin;
    protected String duracion;
    protected String recordatorio;
    protected Date fechaRespuestaMediante;
    protected String horaRespuestaMediante;
    protected Date fechaRespuestaOn;
    protected String horaRespuestaOn;
    protected Date fechaResolucionMediante;
    protected String horaResolucionMediante;
    protected Date fechaResolucionOn;
    protected String horaResolucionOn;

    public Long getServiceCallID() {
        return serviceCallID;
    }

    public void setServiceCallID(Long value) {
        this.serviceCallID = value;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String value) {
        this.subject = value;
    }

    public String getCustomerCode() {
        return customerCode;
    }

    public void setCustomerCode(String value) {
        this.customerCode = value;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String value) {
        this.customerName = value;
    }

    public Long getContactCode() {
        return contactCode;
    }

    public void setContactCode(Long value) {
        this.contactCode = value;
    }

    public String getManufacturerSerialNum() {
        return manufacturerSerialNum;
    }

    public void setManufacturerSerialNum(String value) {
        this.manufacturerSerialNum = value;
    }

    public String getInternalSerialNum() {
        return internalSerialNum;
    }

    public void setInternalSerialNum(String value) {
        this.internalSerialNum = value;
    }

    public Long getContractID() {
        return contractID;
    }

    public void setContractID(Long value) {
        this.contractID = value;
    }

    public XMLGregorianCalendar getContractEndDate() {
        return contractEndDate;
    }

    public void setContractEndDate(XMLGregorianCalendar value) {
        this.contractEndDate = value;
    }

    public XMLGregorianCalendar getResolutionDate() {
        return resolutionDate;
    }

    public void setResolutionDate(XMLGregorianCalendar value) {
        this.resolutionDate = value;
    }

    public XMLGregorianCalendar getResolutionTime() {
        return resolutionTime;
    }

    public void setResolutionTime(XMLGregorianCalendar value) {
        this.resolutionTime = value;
    }

    public Long getOrigin() {
        return origin;
    }

    public void setOrigin(Long value) {
        this.origin = value;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String value) {
        this.itemCode = value;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String value) {
        this.itemDescription = value;
    }

    public Long getItemGroupCode() {
        return itemGroupCode;
    }

    public void setItemGroupCode(Long value) {
        this.itemGroupCode = value;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long value) {
        this.status = value;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String value) {
        this.priority = value;
    }

    public Long getCallType() {
        return callType;
    }

    public void setCallType(Long value) {
        this.callType = value;
    }

    public Long getProblemType() {
        return problemType;
    }

    public void setProblemType(Long value) {
        this.problemType = value;
    }

    public Long getAssigneeCode() {
        return assigneeCode;
    }

    public void setAssigneeCode(Long value) {
        this.assigneeCode = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String value) {
        this.description = value;
    }

    public Long getTechnicianCode() {
        return technicianCode;
    }

    public void setTechnicianCode(Long value) {
        this.technicianCode = value;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String value) {
        this.resolution = value;
    }

    public XMLGregorianCalendar getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(XMLGregorianCalendar value) {
        this.creationDate = value;
    }

    public XMLGregorianCalendar getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(XMLGregorianCalendar value) {
        this.creationTime = value;
    }

    public Long getResponder() {
        return responder;
    }

    public void setResponder(Long value) {
        this.responder = value;
    }

    public XMLGregorianCalendar getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(XMLGregorianCalendar value) {
        this.updatedTime = value;
    }

    public String getBelongsToAQueue() {
        return belongsToAQueue;
    }

    public void setBelongsToAQueue(String value) {
        this.belongsToAQueue = value;
    }

    public XMLGregorianCalendar getResponseByTime() {
        return responseByTime;
    }

    public void setResponseByTime(XMLGregorianCalendar value) {
        this.responseByTime = value;
    }

    public XMLGregorianCalendar getResponseByDate() {
        return responseByDate;
    }

    public void setResponseByDate(XMLGregorianCalendar value) {
        this.responseByDate = value;
    }

    public XMLGregorianCalendar getResolutionOnDate() {
        return resolutionOnDate;
    }

    public void setResolutionOnDate(XMLGregorianCalendar value) {
        this.resolutionOnDate = value;
    }

    public XMLGregorianCalendar getResponseOnTime() {
        return responseOnTime;
    }

    public void setResponseOnTime(XMLGregorianCalendar value) {
        this.responseOnTime = value;
    }

    public XMLGregorianCalendar getResponseOnDate() {
        return responseOnDate;
    }

    public void setResponseOnDate(XMLGregorianCalendar value) {
        this.responseOnDate = value;
    }

    public XMLGregorianCalendar getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(XMLGregorianCalendar value) {
        this.closingTime = value;
    }

    public XMLGregorianCalendar getAssignedDate() {
        return assignedDate;
    }

    public void setAssignedDate(XMLGregorianCalendar value) {
        this.assignedDate = value;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String value) {
        this.queue = value;
    }

    public Long getResponseAssignee() {
        return responseAssignee;
    }

    public void setResponseAssignee(Long value) {
        this.responseAssignee = value;
    }

    public String getEntitledforService() {
        return entitledforService;
    }

    public void setEntitledforService(String value) {
        this.entitledforService = value;
    }

    public XMLGregorianCalendar getResolutionOnTime() {
        return resolutionOnTime;
    }

    public void setResolutionOnTime(XMLGregorianCalendar value) {
        this.resolutionOnTime = value;
    }

    public XMLGregorianCalendar getAssignedTime() {
        return assignedTime;
    }

    public void setAssignedTime(XMLGregorianCalendar value) {
        this.assignedTime = value;
    }

    public XMLGregorianCalendar getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(XMLGregorianCalendar value) {
        this.closingDate = value;
    }

    public Long getSeries() {
        return series;
    }

    public void setSeries(Long value) {
        this.series = value;
    }

    public Long getDocNum() {
        return docNum;
    }

    public void setDocNum(Long value) {
        this.docNum = value;
    }

    public String getHandWritten() {
        return handWritten;
    }

    public void setHandWritten(String value) {
        this.handWritten = value;
    }

    public String getPeriodIndicator() {
        return periodIndicator;
    }

    public void setPeriodIndicator(String value) {
        this.periodIndicator = value;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    public XMLGregorianCalendar getStartTime() {
        return startTime;
    }

    public void setStartTime(XMLGregorianCalendar value) {
        this.startTime = value;
    }

    public XMLGregorianCalendar getEndDueDate() {
        return endDueDate;
    }

    public void setEndDueDate(XMLGregorianCalendar value) {
        this.endDueDate = value;
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

    public Long getLocation() {
        return location;
    }

    public void setLocation(Long value) {
        this.location = value;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String value) {
        this.street = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String value) {
        this.room = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }

    public String getDisplayInCalendar() {
        return displayInCalendar;
    }

    public void setDisplayInCalendar(String value) {
        this.displayInCalendar = value;
    }

    public String getCustomerRefNo() {
        return customerRefNo;
    }

    public void setCustomerRefNo(String value) {
        this.customerRefNo = value;
    }

    public Long getProblemSubType() {
        return problemSubType;
    }

    public void setProblemSubType(Long value) {
        this.problemSubType = value;
    }

    public Long getAttachmentEntry() {
        return attachmentEntry;
    }

    public void setAttachmentEntry(Long value) {
        this.attachmentEntry = value;
    }

    public String getUCausa() {
        return uCausa;
    }

    public void setUCausa(String value) {
        this.uCausa = value;
    }

    public String getUCateGa() {
        return uCateGa;
    }

    public void setUCateGa(String value) {
        this.uCateGa = value;
    }

    public String getUProExt() {
        return uProExt;
    }

    public void setUProExt(String value) {
        this.uProExt = value;
    }

    public String getUNumFactura() {
        return uNumFactura;
    }

    public void setUNumFactura(String value) {
        this.uNumFactura = value;
    }

    public String getUTipoServ() {
        return uTipoServ;
    }

    public void setUTipoServ(String value) {
        this.uTipoServ = value;
    }

    public String getUNWRRMA() {
        return unwrrma;
    }

    public void setUNWRRMA(String value) {
        this.unwrrma = value;
    }

    public String getUNWRPO() {
        return unwrpo;
    }

    public void setUNWRPO(String value) {
        this.unwrpo = value;
    }

    public Long getUNWRRMAType() {
        return unwrrmaType;
    }

    public void setUNWRRMAType(Long value) {
        this.unwrrmaType = value;
    }

    public String getUComentSoporte() {
        return uComentSoporte;
    }

    public void setUComentSoporte(String value) {
        this.uComentSoporte = value;
    }

    public String getUMaterial() {
        return uMaterial;
    }

    public void setUMaterial(String value) {
        this.uMaterial = value;
    }

    public String getUProblemaMaterial() {
        return uProblemaMaterial;
    }

    public void setUProblemaMaterial(String value) {
        this.uProblemaMaterial = value;
    }

    public String getUCrearSoporte() {
        return uCrearSoporte;
    }

    public void setUCrearSoporte(String value) {
        this.uCrearSoporte = value;
    }

    public ServiceCall.ServiceCallActivities getServiceCallActivities() {
        return serviceCallActivities;
    }

    public void setServiceCallActivities(ServiceCall.ServiceCallActivities value) {
        this.serviceCallActivities = value;
    }

    public ServiceCall.ServiceCallInventoryExpenses getServiceCallInventoryExpenses() {
        return serviceCallInventoryExpenses;
    }

    public void setServiceCallInventoryExpenses(ServiceCall.ServiceCallInventoryExpenses value) {
        this.serviceCallInventoryExpenses = value;
    }

    public ServiceCall.ServiceCallSolutions getServiceCallSolutions() {
        return serviceCallSolutions;
    }

    public void setServiceCallSolutions(ServiceCall.ServiceCallSolutions value) {
        this.serviceCallSolutions = value;
    }

    public String getServiceBPType() {
        return serviceBPType;
    }

    public void setServiceBPType(String serviceBPType) {
        this.serviceBPType = serviceBPType;
    }

    public String getBPeMail() {
        return bPeMail;
    }

    public void setBPeMail(String bPeMail) {
        this.bPeMail = bPeMail;
    }

    public String getBPShipToCode() {
        return bPShipToCode;
    }

    public void setBPShipToCode(String bPShipToCode) {
        this.bPShipToCode = bPShipToCode;
    }

    public String getBPShipToAddress() {
        return bPShipToAddress;
    }

    public void setBPShipToAddress(String bPShipToAddress) {
        this.bPShipToAddress = bPShipToAddress;
    }

    public String getBPBillToCode() {
        return bPBillToCode;
    }

    public void setBPBillToCode(String bPBillToCode) {
        this.bPBillToCode = bPBillToCode;
    }

    public String getBPBillToAddress() {
        return bPBillToAddress;
    }

    public void setBPBillToAddress(String bPBillToAddress) {
        this.bPBillToAddress = bPBillToAddress;
    }

    public String getBPPhone1() {
        return bPPhone1;
    }

    public void setBPPhone1(String bPPhone1) {
        this.bPPhone1 = bPPhone1;
    }

    public String getBPPhone2() {
        return bPPhone2;
    }

    public void setBPPhone2(String bPPhone2) {
        this.bPPhone2 = bPPhone2;
    }

    public String getBPCellular() {
        return bPCellular;
    }

    public void setBPCellular(String bPCellular) {
        this.bPCellular = bPCellular;
    }

    public String getBPFax() {
        return bPFax;
    }

    public void setBPFax(String bPFax) {
        this.bPFax = bPFax;
    }

    public String getBPContactPerson() {
        return bPContactPerson;
    }

    public void setBPContactPerson(String bPContactPerson) {
        this.bPContactPerson = bPContactPerson;
    }

    public ServiceCallSchedulings getServiceCallSchedulings() {
        return serviceCallSchedulings;
    }

    public void setServiceCallSchedulings(ServiceCallSchedulings serviceCallSchedulings) {
        this.serviceCallSchedulings = serviceCallSchedulings;
    }

    public ServiceCallBPAddressComponents getServiceCallBPAddressComponents() {
        return serviceCallBPAddressComponents;
    }

    public void setServiceCallBPAddressComponents(ServiceCallBPAddressComponents serviceCallBPAddressComponents) {
        this.serviceCallBPAddressComponents = serviceCallBPAddressComponents;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(String horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public String getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(String horaCierre) {
        this.horaCierre = horaCierre;
    }

    public Date getFechaFinalizado() {
        return fechaFinalizado;
    }

    public void setFechaFinalizado(Date fechaFinalizado) {
        this.fechaFinalizado = fechaFinalizado;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getRecordatorio() {
        return recordatorio;
    }

    public void setRecordatorio(String recordatorio) {
        this.recordatorio = recordatorio;
    }

    public Date getFechaRespuestaMediante() {
        return fechaRespuestaMediante;
    }

    public void setFechaRespuestaMediante(Date fechaRespuestaMediante) {
        this.fechaRespuestaMediante = fechaRespuestaMediante;
    }

    public String getHoraRespuestaMediante() {
        return horaRespuestaMediante;
    }

    public void setHoraRespuestaMediante(String horaRespuestaMediante) {
        this.horaRespuestaMediante = horaRespuestaMediante;
    }

    public Date getFechaRespuestaOn() {
        return fechaRespuestaOn;
    }

    public void setFechaRespuestaOn(Date fechaRespuestaOn) {
        this.fechaRespuestaOn = fechaRespuestaOn;
    }

    public String getHoraRespuestaOn() {
        return horaRespuestaOn;
    }

    public void setHoraRespuestaOn(String horaRespuestaOn) {
        this.horaRespuestaOn = horaRespuestaOn;
    }

    public Date getFechaResolucionMediante() {
        return fechaResolucionMediante;
    }

    public void setFechaResolucionMediante(Date fechaResolucionMediante) {
        this.fechaResolucionMediante = fechaResolucionMediante;
    }

    public String getHoraResolucionMediante() {
        return horaResolucionMediante;
    }

    public void setHoraResolucionMediante(String horaResolucionMediante) {
        this.horaResolucionMediante = horaResolucionMediante;
    }

    public Date getFechaResolucionOn() {
        return fechaResolucionOn;
    }

    public void setFechaResolucionOn(Date fechaResolucionOn) {
        this.fechaResolucionOn = fechaResolucionOn;
    }

    public String getHoraResolucionOn() {
        return horaResolucionOn;
    }

    public void setHoraResolucionOn(String horaResolucionOn) {
        this.horaResolucionOn = horaResolucionOn;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviceCallActivity"
    })
    public static class ServiceCallActivities {

        @XmlElement(name = "ServiceCallActivity")
        protected List<ServiceCall.ServiceCallActivities.ServiceCallActivity> serviceCallActivity;

        public List<ServiceCall.ServiceCallActivities.ServiceCallActivity> getServiceCallActivity() {
            if (serviceCallActivity == null) {
                serviceCallActivity = new ArrayList<ServiceCall.ServiceCallActivities.ServiceCallActivity>();
            }
            return this.serviceCallActivity;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ServiceCallActivity {

            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "ActivityCode")
            protected Long activityCode;

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public Long getActivityCode() {
                return activityCode;
            }

            public void setActivityCode(Long value) {
                this.activityCode = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviceCallInventoryExpense"
    })
    public static class ServiceCallInventoryExpenses {

        @XmlElement(name = "ServiceCallInventoryExpense")
        protected List<ServiceCall.ServiceCallInventoryExpenses.ServiceCallInventoryExpense> serviceCallInventoryExpense;

        public List<ServiceCall.ServiceCallInventoryExpenses.ServiceCallInventoryExpense> getServiceCallInventoryExpense() {
            if (serviceCallInventoryExpense == null) {
                serviceCallInventoryExpense = new ArrayList<ServiceCall.ServiceCallInventoryExpenses.ServiceCallInventoryExpense>();
            }
            return this.serviceCallInventoryExpense;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ServiceCallInventoryExpense {

            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "PartType")
            protected String partType;
            @XmlElement(name = "DocumentType")
            protected String documentType;
            @XmlElement(name = "DocumentPostingDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar documentPostingDate;
            @XmlElement(name = "DocumentNumber")
            protected Long documentNumber;
            @XmlElement(name = "StockTransferDirection")
            protected String stockTransferDirection;
            @XmlElement(name = "DocEntry")
            protected Long docEntry;

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public String getPartType() {
                return partType;
            }

            public void setPartType(String value) {
                this.partType = value;
            }

            public String getDocumentType() {
                return documentType;
            }

            public void setDocumentType(String value) {
                this.documentType = value;
            }

            public XMLGregorianCalendar getDocumentPostingDate() {
                return documentPostingDate;
            }

            public void setDocumentPostingDate(XMLGregorianCalendar value) {
                this.documentPostingDate = value;
            }

            public Long getDocumentNumber() {
                return documentNumber;
            }

            public void setDocumentNumber(Long value) {
                this.documentNumber = value;
            }

            public String getStockTransferDirection() {
                return stockTransferDirection;
            }

            public void setStockTransferDirection(String value) {
                this.stockTransferDirection = value;
            }

            public Long getDocEntry() {
                return docEntry;
            }

            public void setDocEntry(Long value) {
                this.docEntry = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviceCallSolution"
    })
    public static class ServiceCallSolutions {

        @XmlElement(name = "ServiceCallSolution")
        protected List<ServiceCall.ServiceCallSolutions.ServiceCallSolution> serviceCallSolution;

        public List<ServiceCall.ServiceCallSolutions.ServiceCallSolution> getServiceCallSolution() {
            if (serviceCallSolution == null) {
                serviceCallSolution = new ArrayList<ServiceCall.ServiceCallSolutions.ServiceCallSolution>();
            }
            return this.serviceCallSolution;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ServiceCallSolution {

            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "SolutionID")
            protected Long solutionID;

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public Long getSolutionID() {
                return solutionID;
            }

            public void setSolutionID(Long value) {
                this.solutionID = value;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviceCallScheduling"
    })
    public static class ServiceCallSchedulings {

        @XmlElement(name = "ServiceCallScheduling")
        protected List<ServiceCall.ServiceCallSchedulings.ServiceCallScheduling> serviceCallScheduling;

        public List<ServiceCall.ServiceCallSchedulings.ServiceCallScheduling> getServiceCallScheduling() {
            if (serviceCallScheduling == null) {
                serviceCallScheduling = new ArrayList<>();
            }
            return serviceCallScheduling;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ServiceCallScheduling {

            @XmlElement(name = "LineNum")
            protected Integer lineNum;
            @XmlElement(name = "HandledBy")
            protected Integer handledBy;
            @XmlElement(name = "StartDate")
            protected XMLGregorianCalendar startDate;
            @XmlElement(name = "StartTime")
            protected XMLGregorianCalendar startTime;
            @XmlElement(name = "EndDate")
            protected XMLGregorianCalendar endDate;
            @XmlElement(name = "EndTime")
            protected XMLGregorianCalendar endTime;
            @XmlElement(name = "Duration")
            protected BigDecimal duration;
            @XmlElement(name = "ActualDuration")
            protected BigDecimal actualDuration;
            @XmlElement(name = "DurationType")
            protected String durationType;
            @XmlElement(name = "Reminder")
            protected String reminder;
            @XmlElement(name = "ReminderPeriod")
            protected BigDecimal reminderPeriod;
            @XmlElement(name = "ReminderType")
            protected String reminderType;
            @XmlElement(name = "ReminderSent")
            protected String reminderSent;
            @XmlElement(name = "DisplayInCalendar")
            protected String displayInCalendar;
            @XmlElement(name = "IsUnscheduled")
            protected String isUnscheduled;
            @XmlElement(name = "Location")
            protected Integer location;
            @XmlElement(name = "AddressText")
            protected String addressText;
            @XmlElement(name = "IsClosed")
            protected String isClosed;

            public Integer getLineNum() {
                return lineNum;
            }

            public void setLineNum(Integer lineNum) {
                this.lineNum = lineNum;
            }

            public Integer getHandledBy() {
                return handledBy;
            }

            public void setHandledBy(Integer handledBy) {
                this.handledBy = handledBy;
            }

            public XMLGregorianCalendar getStartDate() {
                return startDate;
            }

            public void setStartDate(XMLGregorianCalendar startDate) {
                this.startDate = startDate;
            }

            public XMLGregorianCalendar getStartTime() {
                return startTime;
            }

            public void setStartTime(XMLGregorianCalendar startTime) {
                this.startTime = startTime;
            }

            public XMLGregorianCalendar getEndDate() {
                return endDate;
            }

            public void setEndDate(XMLGregorianCalendar endDate) {
                this.endDate = endDate;
            }

            public XMLGregorianCalendar getEndTime() {
                return endTime;
            }

            public void setEndTime(XMLGregorianCalendar endTime) {
                this.endTime = endTime;
            }

            public BigDecimal getDuration() {
                return duration;
            }

            public void setDuration(BigDecimal duration) {
                this.duration = duration;
            }

            public BigDecimal getActualDuration() {
                return actualDuration;
            }

            public void setActualDuration(BigDecimal actualDuration) {
                this.actualDuration = actualDuration;
            }

            public String getDurationType() {
                return durationType;
            }

            public void setDurationType(String durationType) {
                this.durationType = durationType;
            }

            public String getReminder() {
                return reminder;
            }

            public void setReminder(String reminder) {
                this.reminder = reminder;
            }

            public BigDecimal getReminderPeriod() {
                return reminderPeriod;
            }

            public void setReminderPeriod(BigDecimal reminderPeriod) {
                this.reminderPeriod = reminderPeriod;
            }

            public String getReminderType() {
                return reminderType;
            }

            public void setReminderType(String reminderType) {
                this.reminderType = reminderType;
            }

            public String getReminderSent() {
                return reminderSent;
            }

            public void setReminderSent(String reminderSent) {
                this.reminderSent = reminderSent;
            }

            public String getDisplayInCalendar() {
                return displayInCalendar;
            }

            public void setDisplayInCalendar(String displayInCalendar) {
                this.displayInCalendar = displayInCalendar;
            }

            public String getIsUnscheduled() {
                return isUnscheduled;
            }

            public void setIsUnscheduled(String isUnscheduled) {
                this.isUnscheduled = isUnscheduled;
            }

            public Integer getLocation() {
                return location;
            }

            public void setLocation(Integer location) {
                this.location = location;
            }

            public String getAddressText() {
                return addressText;
            }

            public void setAddressText(String addressText) {
                this.addressText = addressText;
            }

            public String getIsClosed() {
                return isClosed;
            }

            public void setIsClosed(String isClosed) {
                this.isClosed = isClosed;
            }
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviceCallBPAddressComponent"
    })
    public static class ServiceCallBPAddressComponents {

        @XmlElement(name = "ServiceCallBPAddressComponent")
        protected List<ServiceCall.ServiceCallBPAddressComponents.ServiceCallBPAddressComponent> serviceCallBPAddressComponent;

        public List<ServiceCall.ServiceCallBPAddressComponents.ServiceCallBPAddressComponent> getServiceCallSolution() {
            if (serviceCallBPAddressComponent == null) {
                serviceCallBPAddressComponent = new ArrayList<ServiceCall.ServiceCallBPAddressComponents.ServiceCallBPAddressComponent>();
            }
            return this.serviceCallBPAddressComponent;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class ServiceCallBPAddressComponent {

            @XmlElement(name = "ShipToStreet")
            protected String shipToStreet;
            @XmlElement(name = "ShipToBlock")
            protected String shipToBlock;
            @XmlElement(name = "ShipToBuilding")
            protected String shipToBuilding;
            @XmlElement(name = "ShipToCity")
            protected String shipToCity;
            @XmlElement(name = "ShipToState")
            protected String shipToState;
            @XmlElement(name = "ShipToCounty")
            protected String shipToCounty;
            @XmlElement(name = "ShipToCountry")
            protected String shipToCountry;
            @XmlElement(name = "BillToStreet")
            protected String billToStreet;
            @XmlElement(name = "BillToBlock")
            protected String billToBlock;
            @XmlElement(name = "BillToBuilding")
            protected String billToBuilding;
            @XmlElement(name = "BillToCity")
            protected String billToCity;
            @XmlElement(name = "BillToState")
            protected String billToState;
            @XmlElement(name = "BillToCounty")
            protected String billToCounty;
            @XmlElement(name = "BillToCountry")
            protected String billToCountry;
            @XmlElement(name = "BillToZipCode")
            protected String billToZipCode;

            public String getShipToStreet() {
                return shipToStreet;
            }

            public void setShipToStreet(String shipToStreet) {
                this.shipToStreet = shipToStreet;
            }

            public String getShipToBlock() {
                return shipToBlock;
            }

            public void setShipToBlock(String shipToBlock) {
                this.shipToBlock = shipToBlock;
            }

            public String getShipToBuilding() {
                return shipToBuilding;
            }

            public void setShipToBuilding(String shipToBuilding) {
                this.shipToBuilding = shipToBuilding;
            }

            public String getShipToCity() {
                return shipToCity;
            }

            public void setShipToCity(String shipToCity) {
                this.shipToCity = shipToCity;
            }

            public String getShipToState() {
                return shipToState;
            }

            public void setShipToState(String shipToState) {
                this.shipToState = shipToState;
            }

            public String getShipToCounty() {
                return shipToCounty;
            }

            public void setShipToCounty(String shipToCounty) {
                this.shipToCounty = shipToCounty;
            }

            public String getShipToCountry() {
                return shipToCountry;
            }

            public void setShipToCountry(String shipToCountry) {
                this.shipToCountry = shipToCountry;
            }

            public String getBillToStreet() {
                return billToStreet;
            }

            public void setBillToStreet(String billToStreet) {
                this.billToStreet = billToStreet;
            }

            public String getBillToBlock() {
                return billToBlock;
            }

            public void setBillToBlock(String billToBlock) {
                this.billToBlock = billToBlock;
            }

            public String getBillToBuilding() {
                return billToBuilding;
            }

            public void setBillToBuilding(String billToBuilding) {
                this.billToBuilding = billToBuilding;
            }

            public String getBillToCity() {
                return billToCity;
            }

            public void setBillToCity(String billToCity) {
                this.billToCity = billToCity;
            }

            public String getBillToState() {
                return billToState;
            }

            public void setBillToState(String billToState) {
                this.billToState = billToState;
            }

            public String getBillToCounty() {
                return billToCounty;
            }

            public void setBillToCounty(String billToCounty) {
                this.billToCounty = billToCounty;
            }

            public String getBillToCountry() {
                return billToCountry;
            }

            public void setBillToCountry(String billToCountry) {
                this.billToCountry = billToCountry;
            }

            public String getBillToZipCode() {
                return billToZipCode;
            }

            public void setBillToZipCode(String billToZipCode) {
                this.billToZipCode = billToZipCode;
            }
        }
    }
}
