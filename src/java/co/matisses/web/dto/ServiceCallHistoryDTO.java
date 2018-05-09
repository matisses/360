package co.matisses.web.dto;

import co.matisses.persistence.sap.entity.ServiceCallHistory;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class ServiceCallHistoryDTO {

    private Integer logInstanc;
    private Integer callID;
    private Integer contctCode;
    private Integer contractID;
    private Integer resolTime;
    private Integer origin;
    private Integer itemGroup;
    private Integer status;
    private Integer callType;
    private Integer problemTyp;
    private Integer assignee;
    private Integer userSign;
    private Integer createTime;
    private Integer closeTime;
    private Integer userSign2;
    private Integer sCL1Count;
    private Integer sCL2Count;
    private Integer insID;
    private Integer technician;
    private Integer scl1NxtLn;
    private Integer scl2NxtLn;
    private Integer scl3NxtLn;
    private Integer scl4NxtLn;
    private Integer scl5NxtLn;
    private Integer resolOnTim;
    private Integer respByTime;
    private Integer respOnTime;
    private Integer respAssign;
    private Integer assignTime;
    private Integer responder;
    private Integer instance;
    private Integer docNum;
    private Integer series;
    private Integer startTime;
    private Integer endTime;
    private Integer remTime;
    private Integer location;
    private Integer atcEntry;
    private Integer proSubType;
    private Integer bPTerrit;
    private Integer ownerCode;
    private Integer uNWR_RMA_Type;
    private String updateTime;
    private String subject;
    private String customer;
    private String custmrName;
    private String manufSN;
    private String internalSN;
    private String itemCode;
    private String itemName;
    private String descrption;
    private String objType;
    private String resolution;
    private String queue;
    private String pIndicator;
    private String addrName;
    private String street;
    private String city;
    private String room;
    private String state;
    private String country;
    private String supplCode;
    private String attachment;
    private String numAtCard;
    private String telephone;
    private String bPPhone1;
    private String bPPhone2;
    private String bPCellular;
    private String bPFax;
    private String bPShipCode;
    private String bPShipAddr;
    private String bPBillCode;
    private String bPBillAddr;
    private String bPE_Mail;
    private String bPProjCode;
    private String bPContact;
    private String uCausa;
    private String uCateGa;
    private String uProExt;
    private String uNum_factura;
    private String uTipoServ;
    private String uNWR_RMA;
    private String uNWR_PO;
    private String uComentSoporte;
    private String uMaterial;
    private String uProblemaMaterial;
    private String ucrearSoporte;
    private BigDecimal duration;
    private BigDecimal remQty;
    private Character free1;
    private Character priority;
    private Character isEntitled;
    private Character isQueue;
    private Character transfered;
    private Character handwrtten;
    private Character durType;
    private Character reminder;
    private Character remType;
    private Character remSent;
    private Character addrType;
    private Character displInCal;
    private Character bPType;
    private Date cntrctDate;
    private Date resolDate;
    private Date free2;
    private Date createDate;
    private Date closeDate;
    private Date updateDate;
    private Date resolOnDat;
    private Date respByDate;
    private Date respOnDate;
    private Date assignDate;
    private Date startDate;
    private Date endDate;
    private Date remDate;

    public ServiceCallHistoryDTO() {
    }

    public ServiceCallHistoryDTO(Integer logInstanc, Integer callID, Integer contctCode, Integer contractID, Integer resolTime, Integer origin, Integer itemGroup, Integer status,
            Integer callType, Integer problemTyp, Integer assignee, Integer userSign, Integer createTime, Integer closeTime, Integer userSign2, Integer sCL1Count, Integer sCL2Count,
            Integer insID, Integer technician, Integer scl1NxtLn, Integer scl2NxtLn, Integer scl3NxtLn, Integer scl4NxtLn, Integer scl5NxtLn, Integer resolOnTim, Integer respByTime,
            Integer respOnTime, Integer respAssign, Integer assignTime, Integer responder, Integer instance, Integer docNum, Integer series, Integer startTime, Integer endTime,
            Integer remTime, Integer location, Integer atcEntry, Integer proSubType, Integer bPTerrit, Integer ownerCode, Integer uNWR_RMA_Type, String updateTime, String subject,
            String customer, String custmrName, String manufSN, String internalSN, String itemCode, String itemName, String descrption, String objType, String resolution,
            String queue, String pIndicator, String addrName, String street, String city, String room, String state, String country, String supplCode, String attachment,
            String numAtCard, String telephone, String bPPhone1, String bPPhone2, String bPCellular, String bPFax, String bPShipCode, String bPShipAddr, String bPBillCode,
            String bPBillAddr, String bPE_Mail, String bPProjCode, String bPContact, String uCausa, String uCateGa, String uProExt, String uNum_factura, String uTipoServ,
            String uNWR_RMA, String uNWR_PO, String uComentSoporte, String uMaterial, String uProblemaMaterial, String ucrearSoporte, BigDecimal duration, BigDecimal remQty,
            Character free1, Character priority, Character isEntitled, Character isQueue, Character transfered, Character handwrtten, Character durType, Character reminder,
            Character remType, Character remSent, Character addrType, Character displInCal, Character bPType, Date cntrctDate, Date resolDate, Date free2, Date createDate,
            Date closeDate, Date updateDate, Date resolOnDat, Date respByDate, Date respOnDate, Date assignDate, Date startDate, Date endDate, Date remDate) {
        this.logInstanc = logInstanc;
        this.callID = callID;
        this.contctCode = contctCode;
        this.contractID = contractID;
        this.resolTime = resolTime;
        this.origin = origin;
        this.itemGroup = itemGroup;
        this.status = status;
        this.callType = callType;
        this.problemTyp = problemTyp;
        this.assignee = assignee;
        this.userSign = userSign;
        this.createTime = createTime;
        this.closeTime = closeTime;
        this.userSign2 = userSign2;
        this.sCL1Count = sCL1Count;
        this.sCL2Count = sCL2Count;
        this.insID = insID;
        this.technician = technician;
        this.scl1NxtLn = scl1NxtLn;
        this.scl2NxtLn = scl2NxtLn;
        this.scl3NxtLn = scl3NxtLn;
        this.scl4NxtLn = scl4NxtLn;
        this.scl5NxtLn = scl5NxtLn;
        this.resolOnTim = resolOnTim;
        this.respByTime = respByTime;
        this.respOnTime = respOnTime;
        this.respAssign = respAssign;
        this.assignTime = assignTime;
        this.responder = responder;
        this.instance = instance;
        this.docNum = docNum;
        this.series = series;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remTime = remTime;
        this.location = location;
        this.atcEntry = atcEntry;
        this.proSubType = proSubType;
        this.bPTerrit = bPTerrit;
        this.ownerCode = ownerCode;
        this.uNWR_RMA_Type = uNWR_RMA_Type;
        this.updateTime = updateTime;
        this.subject = subject;
        this.customer = customer;
        this.custmrName = custmrName;
        this.manufSN = manufSN;
        this.internalSN = internalSN;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.descrption = descrption;
        this.objType = objType;
        this.resolution = resolution;
        this.queue = queue;
        this.pIndicator = pIndicator;
        this.addrName = addrName;
        this.street = street;
        this.city = city;
        this.room = room;
        this.state = state;
        this.country = country;
        this.supplCode = supplCode;
        this.attachment = attachment;
        this.numAtCard = numAtCard;
        this.telephone = telephone;
        this.bPPhone1 = bPPhone1;
        this.bPPhone2 = bPPhone2;
        this.bPCellular = bPCellular;
        this.bPFax = bPFax;
        this.bPShipCode = bPShipCode;
        this.bPShipAddr = bPShipAddr;
        this.bPBillCode = bPBillCode;
        this.bPBillAddr = bPBillAddr;
        this.bPE_Mail = bPE_Mail;
        this.bPProjCode = bPProjCode;
        this.bPContact = bPContact;
        this.uCausa = uCausa;
        this.uCateGa = uCateGa;
        this.uProExt = uProExt;
        this.uNum_factura = uNum_factura;
        this.uTipoServ = uTipoServ;
        this.uNWR_RMA = uNWR_RMA;
        this.uNWR_PO = uNWR_PO;
        this.uComentSoporte = uComentSoporte;
        this.uMaterial = uMaterial;
        this.uProblemaMaterial = uProblemaMaterial;
        this.ucrearSoporte = ucrearSoporte;
        this.duration = duration;
        this.remQty = remQty;
        this.free1 = free1;
        this.priority = priority;
        this.isEntitled = isEntitled;
        this.isQueue = isQueue;
        this.transfered = transfered;
        this.handwrtten = handwrtten;
        this.durType = durType;
        this.reminder = reminder;
        this.remType = remType;
        this.remSent = remSent;
        this.addrType = addrType;
        this.displInCal = displInCal;
        this.bPType = bPType;
        this.cntrctDate = cntrctDate;
        this.resolDate = resolDate;
        this.free2 = free2;
        this.createDate = createDate;
        this.closeDate = closeDate;
        this.updateDate = updateDate;
        this.resolOnDat = resolOnDat;
        this.respByDate = respByDate;
        this.respOnDate = respOnDate;
        this.assignDate = assignDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remDate = remDate;
    }

    public ServiceCallHistoryDTO(ServiceCallHistory history) {
        this.logInstanc = history.getLogInstanc();
        this.callID = history.getCallID();
        this.contctCode = history.getContctCode();
        this.contractID = history.getContractID();
        this.resolTime = history.getResolTime();
        this.origin = history.getOrigin();
        this.itemGroup = history.getItemGroup();
        this.status = history.getStatus();
        this.callType = history.getCallType();
        this.problemTyp = history.getProblemTyp();
        this.assignee = history.getAssignee();
        this.userSign = history.getUserSign();
        this.createTime = history.getCreateTime();
        this.closeTime = history.getCloseTime();
        this.userSign2 = history.getUserSign2();
        this.sCL1Count = history.getsCL1Count();
        this.sCL2Count = history.getsCL2Count();
        this.insID = history.getInsID();
        this.technician = history.getTechnician();
        this.scl1NxtLn = history.getScl1NxtLn();
        this.scl2NxtLn = history.getScl2NxtLn();
        this.scl3NxtLn = history.getScl3NxtLn();
        this.scl4NxtLn = history.getScl4NxtLn();
        this.scl5NxtLn = history.getScl5NxtLn();
        this.resolOnTim = history.getResolOnTim();
        this.respByTime = history.getRespByTime();
        this.respOnTime = history.getRespOnTime();
        this.respAssign = history.getRespAssign();
        this.assignTime = history.getAssignTime();
        if (history.getUpdateTime().toString().length() == 4) {
            this.updateTime = history.getUpdateTime().toString().substring(0, 2) + ":" + history.getUpdateTime().toString().substring(2);
        } else {
            this.updateTime = "0" + history.getUpdateTime().toString().substring(0, 1) + ":" + history.getUpdateTime().toString().substring(1);
        }
        this.responder = history.getResponder();
        this.instance = history.getInstance();
        this.docNum = history.getDocNum();
        this.series = history.getSeries();
        this.startTime = history.getStartTime();
        this.endTime = history.getEndTime();
        this.remTime = history.getRemTime();
        this.location = history.getLocation();
        this.atcEntry = history.getAtcEntry();
        this.proSubType = history.getProSubType();
        this.bPTerrit = history.getbPTerrit();
        this.ownerCode = history.getOwnerCode();
        this.uNWR_RMA_Type = history.getuNWR_RMA_Type();
        this.subject = history.getSubject();
        this.customer = history.getCustomer();
        this.custmrName = history.getCustmrName();
        this.manufSN = history.getManufSN();
        this.internalSN = history.getInternalSN();
        this.itemCode = history.getItemCode();
        this.itemName = history.getItemName();
        this.descrption = history.getDescrption();
        this.objType = history.getObjType();
        this.resolution = history.getResolution();
        this.queue = history.getQueue();
        this.pIndicator = history.getpIndicator();
        this.addrName = history.getAddrName();
        this.street = history.getStreet();
        this.city = history.getCity();
        this.room = history.getRoom();
        this.state = history.getState();
        this.country = history.getCountry();
        this.supplCode = history.getSupplCode();
        this.attachment = history.getAttachment();
        this.numAtCard = history.getNumAtCard();
        this.telephone = history.getTelephone();
        this.bPPhone1 = history.getbPPhone1();
        this.bPPhone2 = history.getbPPhone2();
        this.bPCellular = history.getbPCellular();
        this.bPFax = history.getbPFax();
        this.bPShipCode = history.getbPShipCode();
        this.bPShipAddr = history.getbPShipAddr();
        this.bPBillCode = history.getbPBillCode();
        this.bPBillAddr = history.getbPBillAddr();
        this.bPE_Mail = history.getbPE_Mail();
        this.bPProjCode = history.getbPProjCode();
        this.bPContact = history.getbPContact();
        this.uCausa = history.getuCausa();
        this.uCateGa = history.getuCateGa();
        this.uProExt = history.getuProExt();
        this.uNum_factura = history.getuNum_factura();
        this.uTipoServ = history.getuTipoServ();
        this.uNWR_RMA = history.getuNWR_RMA();
        this.uNWR_PO = history.getuNWR_PO();
        this.uComentSoporte = history.getuComentSoporte();
        this.uMaterial = history.getuMaterial();
        this.uProblemaMaterial = history.getuProblemaMaterial();
        this.ucrearSoporte = history.getUcrearSoporte();
        this.duration = history.getDuration();
        this.remQty = history.getRemQty();
        this.free1 = history.getFree1();
        this.priority = history.getPriority();
        this.isEntitled = history.getIsEntitled();
        this.isQueue = history.getIsQueue();
        this.transfered = history.getTransfered();
        this.handwrtten = history.getHandwrtten();
        this.durType = history.getDurType();
        this.reminder = history.getReminder();
        this.remType = history.getRemType();
        this.remSent = history.getRemSent();
        this.addrType = history.getAddrType();
        this.displInCal = history.getDisplInCal();
        this.bPType = history.getbPType();
        this.cntrctDate = history.getCntrctDate();
        this.resolDate = history.getResolDate();
        this.free2 = history.getFree2();
        this.createDate = history.getCreateDate();
        this.closeDate = history.getCloseDate();
        this.updateDate = history.getUpdateDate();
        this.resolOnDat = history.getResolOnDat();
        this.respByDate = history.getRespByDate();
        this.respOnDate = history.getRespOnDate();
        this.assignDate = history.getAssignDate();
        this.startDate = history.getStartDate();
        this.endDate = history.getEndDate();
        this.remDate = history.getRemDate();
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Integer getCallID() {
        return callID;
    }

    public void setCallID(Integer callID) {
        this.callID = callID;
    }

    public Integer getContctCode() {
        return contctCode;
    }

    public void setContctCode(Integer contctCode) {
        this.contctCode = contctCode;
    }

    public Integer getContractID() {
        return contractID;
    }

    public void setContractID(Integer contractID) {
        this.contractID = contractID;
    }

    public Integer getResolTime() {
        return resolTime;
    }

    public void setResolTime(Integer resolTime) {
        this.resolTime = resolTime;
    }

    public Integer getOrigin() {
        return origin;
    }

    public void setOrigin(Integer origin) {
        this.origin = origin;
    }

    public Integer getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(Integer itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getCallType() {
        return callType;
    }

    public void setCallType(Integer callType) {
        this.callType = callType;
    }

    public Integer getProblemTyp() {
        return problemTyp;
    }

    public void setProblemTyp(Integer problemTyp) {
        this.problemTyp = problemTyp;
    }

    public Integer getAssignee() {
        return assignee;
    }

    public void setAssignee(Integer assignee) {
        this.assignee = assignee;
    }

    public Integer getUserSign() {
        return userSign;
    }

    public void setUserSign(Integer userSign) {
        this.userSign = userSign;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Integer closeTime) {
        this.closeTime = closeTime;
    }

    public Integer getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Integer userSign2) {
        this.userSign2 = userSign2;
    }

    public Integer getsCL1Count() {
        return sCL1Count;
    }

    public void setsCL1Count(Integer sCL1Count) {
        this.sCL1Count = sCL1Count;
    }

    public Integer getsCL2Count() {
        return sCL2Count;
    }

    public void setsCL2Count(Integer sCL2Count) {
        this.sCL2Count = sCL2Count;
    }

    public Integer getInsID() {
        return insID;
    }

    public void setInsID(Integer insID) {
        this.insID = insID;
    }

    public Integer getTechnician() {
        return technician;
    }

    public void setTechnician(Integer technician) {
        this.technician = technician;
    }

    public Integer getScl1NxtLn() {
        return scl1NxtLn;
    }

    public void setScl1NxtLn(Integer scl1NxtLn) {
        this.scl1NxtLn = scl1NxtLn;
    }

    public Integer getScl2NxtLn() {
        return scl2NxtLn;
    }

    public void setScl2NxtLn(Integer scl2NxtLn) {
        this.scl2NxtLn = scl2NxtLn;
    }

    public Integer getScl3NxtLn() {
        return scl3NxtLn;
    }

    public void setScl3NxtLn(Integer scl3NxtLn) {
        this.scl3NxtLn = scl3NxtLn;
    }

    public Integer getScl4NxtLn() {
        return scl4NxtLn;
    }

    public void setScl4NxtLn(Integer scl4NxtLn) {
        this.scl4NxtLn = scl4NxtLn;
    }

    public Integer getScl5NxtLn() {
        return scl5NxtLn;
    }

    public void setScl5NxtLn(Integer scl5NxtLn) {
        this.scl5NxtLn = scl5NxtLn;
    }

    public Integer getResolOnTim() {
        return resolOnTim;
    }

    public void setResolOnTim(Integer resolOnTim) {
        this.resolOnTim = resolOnTim;
    }

    public Integer getRespByTime() {
        return respByTime;
    }

    public void setRespByTime(Integer respByTime) {
        this.respByTime = respByTime;
    }

    public Integer getRespOnTime() {
        return respOnTime;
    }

    public void setRespOnTime(Integer respOnTime) {
        this.respOnTime = respOnTime;
    }

    public Integer getRespAssign() {
        return respAssign;
    }

    public void setRespAssign(Integer respAssign) {
        this.respAssign = respAssign;
    }

    public Integer getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Integer assignTime) {
        this.assignTime = assignTime;
    }

    public Integer getResponder() {
        return responder;
    }

    public void setResponder(Integer responder) {
        this.responder = responder;
    }

    public Integer getInstance() {
        return instance;
    }

    public void setInstance(Integer instance) {
        this.instance = instance;
    }

    public Integer getDocNum() {
        return docNum;
    }

    public void setDocNum(Integer docNum) {
        this.docNum = docNum;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getRemTime() {
        return remTime;
    }

    public void setRemTime(Integer remTime) {
        this.remTime = remTime;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    public Integer getProSubType() {
        return proSubType;
    }

    public void setProSubType(Integer proSubType) {
        this.proSubType = proSubType;
    }

    public Integer getbPTerrit() {
        return bPTerrit;
    }

    public void setbPTerrit(Integer bPTerrit) {
        this.bPTerrit = bPTerrit;
    }

    public Integer getOwnerCode() {
        return ownerCode;
    }

    public void setOwnerCode(Integer ownerCode) {
        this.ownerCode = ownerCode;
    }

    public Integer getuNWR_RMA_Type() {
        return uNWR_RMA_Type;
    }

    public void setuNWR_RMA_Type(Integer uNWR_RMA_Type) {
        this.uNWR_RMA_Type = uNWR_RMA_Type;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustmrName() {
        return custmrName;
    }

    public void setCustmrName(String custmrName) {
        this.custmrName = custmrName;
    }

    public String getManufSN() {
        return manufSN;
    }

    public void setManufSN(String manufSN) {
        this.manufSN = manufSN;
    }

    public String getInternalSN() {
        return internalSN;
    }

    public void setInternalSN(String internalSN) {
        this.internalSN = internalSN;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public String getObjType() {
        return objType;
    }

    public void setObjType(String objType) {
        this.objType = objType;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getQueue() {
        return queue;
    }

    public void setQueue(String queue) {
        this.queue = queue;
    }

    public String getpIndicator() {
        return pIndicator;
    }

    public void setpIndicator(String pIndicator) {
        this.pIndicator = pIndicator;
    }

    public String getAddrName() {
        return addrName;
    }

    public void setAddrName(String addrName) {
        this.addrName = addrName;
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

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSupplCode() {
        return supplCode;
    }

    public void setSupplCode(String supplCode) {
        this.supplCode = supplCode;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getNumAtCard() {
        return numAtCard;
    }

    public void setNumAtCard(String numAtCard) {
        this.numAtCard = numAtCard;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getbPPhone1() {
        return bPPhone1;
    }

    public void setbPPhone1(String bPPhone1) {
        this.bPPhone1 = bPPhone1;
    }

    public String getbPPhone2() {
        return bPPhone2;
    }

    public void setbPPhone2(String bPPhone2) {
        this.bPPhone2 = bPPhone2;
    }

    public String getbPCellular() {
        return bPCellular;
    }

    public void setbPCellular(String bPCellular) {
        this.bPCellular = bPCellular;
    }

    public String getbPFax() {
        return bPFax;
    }

    public void setbPFax(String bPFax) {
        this.bPFax = bPFax;
    }

    public String getbPShipCode() {
        return bPShipCode;
    }

    public void setbPShipCode(String bPShipCode) {
        this.bPShipCode = bPShipCode;
    }

    public String getbPShipAddr() {
        return bPShipAddr;
    }

    public void setbPShipAddr(String bPShipAddr) {
        this.bPShipAddr = bPShipAddr;
    }

    public String getbPBillCode() {
        return bPBillCode;
    }

    public void setbPBillCode(String bPBillCode) {
        this.bPBillCode = bPBillCode;
    }

    public String getbPBillAddr() {
        return bPBillAddr;
    }

    public void setbPBillAddr(String bPBillAddr) {
        this.bPBillAddr = bPBillAddr;
    }

    public String getbPE_Mail() {
        return bPE_Mail;
    }

    public void setbPE_Mail(String bPE_Mail) {
        this.bPE_Mail = bPE_Mail;
    }

    public String getbPProjCode() {
        return bPProjCode;
    }

    public void setbPProjCode(String bPProjCode) {
        this.bPProjCode = bPProjCode;
    }

    public String getbPContact() {
        return bPContact;
    }

    public void setbPContact(String bPContact) {
        this.bPContact = bPContact;
    }

    public String getuCausa() {
        return uCausa;
    }

    public void setuCausa(String uCausa) {
        this.uCausa = uCausa;
    }

    public String getuCateGa() {
        return uCateGa;
    }

    public void setuCateGa(String uCateGa) {
        this.uCateGa = uCateGa;
    }

    public String getuProExt() {
        return uProExt;
    }

    public void setuProExt(String uProExt) {
        this.uProExt = uProExt;
    }

    public String getuNum_factura() {
        return uNum_factura;
    }

    public void setuNum_factura(String uNum_factura) {
        this.uNum_factura = uNum_factura;
    }

    public String getuTipoServ() {
        return uTipoServ;
    }

    public void setuTipoServ(String uTipoServ) {
        this.uTipoServ = uTipoServ;
    }

    public String getuNWR_RMA() {
        return uNWR_RMA;
    }

    public void setuNWR_RMA(String uNWR_RMA) {
        this.uNWR_RMA = uNWR_RMA;
    }

    public String getuNWR_PO() {
        return uNWR_PO;
    }

    public void setuNWR_PO(String uNWR_PO) {
        this.uNWR_PO = uNWR_PO;
    }

    public String getuComentSoporte() {
        return uComentSoporte;
    }

    public void setuComentSoporte(String uComentSoporte) {
        this.uComentSoporte = uComentSoporte;
    }

    public String getuMaterial() {
        return uMaterial;
    }

    public void setuMaterial(String uMaterial) {
        this.uMaterial = uMaterial;
    }

    public String getuProblemaMaterial() {
        return uProblemaMaterial;
    }

    public void setuProblemaMaterial(String uProblemaMaterial) {
        this.uProblemaMaterial = uProblemaMaterial;
    }

    public String getUcrearSoporte() {
        return ucrearSoporte;
    }

    public void setUcrearSoporte(String ucrearSoporte) {
        this.ucrearSoporte = ucrearSoporte;
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

    public Character getFree1() {
        return free1;
    }

    public void setFree1(Character free1) {
        this.free1 = free1;
    }

    public Character getPriority() {
        return priority;
    }

    public void setPriority(Character priority) {
        this.priority = priority;
    }

    public Character getIsEntitled() {
        return isEntitled;
    }

    public void setIsEntitled(Character isEntitled) {
        this.isEntitled = isEntitled;
    }

    public Character getIsQueue() {
        return isQueue;
    }

    public void setIsQueue(Character isQueue) {
        this.isQueue = isQueue;
    }

    public Character getTransfered() {
        return transfered;
    }

    public void setTransfered(Character transfered) {
        this.transfered = transfered;
    }

    public Character getHandwrtten() {
        return handwrtten;
    }

    public void setHandwrtten(Character handwrtten) {
        this.handwrtten = handwrtten;
    }

    public Character getDurType() {
        return durType;
    }

    public void setDurType(Character durType) {
        this.durType = durType;
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

    public Character getRemSent() {
        return remSent;
    }

    public void setRemSent(Character remSent) {
        this.remSent = remSent;
    }

    public Character getAddrType() {
        return addrType;
    }

    public void setAddrType(Character addrType) {
        this.addrType = addrType;
    }

    public Character getDisplInCal() {
        return displInCal;
    }

    public void setDisplInCal(Character displInCal) {
        this.displInCal = displInCal;
    }

    public Character getbPType() {
        return bPType;
    }

    public void setbPType(Character bPType) {
        this.bPType = bPType;
    }

    public Date getCntrctDate() {
        return cntrctDate;
    }

    public void setCntrctDate(Date cntrctDate) {
        this.cntrctDate = cntrctDate;
    }

    public Date getResolDate() {
        return resolDate;
    }

    public void setResolDate(Date resolDate) {
        this.resolDate = resolDate;
    }

    public Date getFree2() {
        return free2;
    }

    public void setFree2(Date free2) {
        this.free2 = free2;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getResolOnDat() {
        return resolOnDat;
    }

    public void setResolOnDat(Date resolOnDat) {
        this.resolOnDat = resolOnDat;
    }

    public Date getRespByDate() {
        return respByDate;
    }

    public void setRespByDate(Date respByDate) {
        this.respByDate = respByDate;
    }

    public Date getRespOnDate() {
        return respOnDate;
    }

    public void setRespOnDate(Date respOnDate) {
        this.respOnDate = respOnDate;
    }

    public Date getAssignDate() {
        return assignDate;
    }

    public void setAssignDate(Date assignDate) {
        this.assignDate = assignDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getRemDate() {
        return remDate;
    }

    public void setRemDate(Date remDate) {
        this.remDate = remDate;
    }
}
