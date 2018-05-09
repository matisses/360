package co.matisses.web.dto;

import co.matisses.persistence.sap.entity.LlamadaServicio;
import java.math.BigDecimal;
import java.util.Date;

/**
 *
 * @author ygil
 */
public class LlamadaServicioDTO {

    private int docNum;
    private Integer callID;
    private Integer contctCode;
    private Integer contractID;
    private Integer logInstanc;
    private Integer sCL1Count;
    private Integer sCL2Count;
    private Integer insID;
    private Integer technician;
    private Integer scl1NxtLn;
    private Integer scl2NxtLn;
    private Integer scl3NxtLn;
    private Integer scl4NxtLn;
    private Integer scl5NxtLn;
    private Integer startTime;
    private Integer endTime;
    private Short resolTime;
    private Short origin;
    private Short itemGroup;
    private Short status;
    private Short callType;
    private Short problemTyp;
    private Short assignee;
    private Short userSign;
    private Short createTime;
    private Short closeTime;
    private Short userSign2;
    private Short resolOnTim;
    private Short respByTime;
    private Short respOnTime;
    private Short respAssign;
    private Short assignTime;
    private Short updateTime;
    private Short responder;
    private Short instance;
    private Short series;
    private Short remTime;
    private Short location;
    private Short uNWRRMAType;
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
    private String uCausa;
    private String uCateGa;
    private String uProExt;
    private String uNumfactura;
    private String uTipoServ;
    private String uNwrRma;
    private String uNwrPo;
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
    private BigDecimal duration;
    private BigDecimal remQty;
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

    public LlamadaServicioDTO() {
    }

    public LlamadaServicioDTO(int docNum, Integer callID, Integer contctCode, Integer contractID, Integer logInstanc, Integer sCL1Count, Integer sCL2Count, Integer insID,
            Integer technician, Integer scl1NxtLn, Integer scl2NxtLn, Integer scl3NxtLn, Integer scl4NxtLn, Integer scl5NxtLn, Integer startTime, Integer endTime,
            Short resolTime, Short origin, Short itemGroup, Short status, Short callType, Short problemTyp, Short assignee, Short userSign, Short createTime, Short closeTime,
            Short userSign2, Short resolOnTim, Short respByTime, Short respOnTime, Short respAssign, Short assignTime, Short updateTime, Short responder, Short instance,
            Short series, Short remTime, Short location, Short uNWRRMAType, String subject, String customer, String custmrName, String manufSN, String internalSN,
            String itemCode, String itemName, String descrption, String objType, String resolution, String queue, String pIndicator, String addrName, String street,
            String city, String room, String state, String country, String supplCode, String uCausa, String uCateGa, String uProExt, String uNumfactura, String uTipoServ,
            String uNwrRma, String uNwrPo, Character free1, Character priority, Character isEntitled, Character isQueue, Character transfered, Character handwrtten,
            Character durType, Character reminder, Character remType, Character remSent, Character addrType, Character displInCal, BigDecimal duration, BigDecimal remQty,
            Date cntrctDate, Date resolDate, Date free2, Date createDate, Date closeDate, Date updateDate, Date resolOnDat, Date respByDate, Date respOnDate, Date assignDate,
            Date startDate, Date endDate, Date remDate) {
        this.docNum = docNum;
        this.callID = callID;
        this.contctCode = contctCode;
        this.contractID = contractID;
        this.logInstanc = logInstanc;
        this.sCL1Count = sCL1Count;
        this.sCL2Count = sCL2Count;
        this.insID = insID;
        this.technician = technician;
        this.scl1NxtLn = scl1NxtLn;
        this.scl2NxtLn = scl2NxtLn;
        this.scl3NxtLn = scl3NxtLn;
        this.scl4NxtLn = scl4NxtLn;
        this.scl5NxtLn = scl5NxtLn;
        this.startTime = startTime;
        this.endTime = endTime;
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
        this.resolOnTim = resolOnTim;
        this.respByTime = respByTime;
        this.respOnTime = respOnTime;
        this.respAssign = respAssign;
        this.assignTime = assignTime;
        this.updateTime = updateTime;
        this.responder = responder;
        this.instance = instance;
        this.series = series;
        this.remTime = remTime;
        this.location = location;
        this.uNWRRMAType = uNWRRMAType;
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
        this.uCausa = uCausa;
        this.uCateGa = uCateGa;
        this.uProExt = uProExt;
        this.uNumfactura = uNumfactura;
        this.uTipoServ = uTipoServ;
        this.uNwrRma = uNwrRma;
        this.uNwrPo = uNwrPo;
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
        this.duration = duration;
        this.remQty = remQty;
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

    public LlamadaServicioDTO(LlamadaServicio llamada) {
        this.docNum = llamada.getDocNum();
        this.callID = llamada.getCallID();
        this.contctCode = llamada.getContctCode();
        this.contractID = llamada.getContractID();
        this.logInstanc = llamada.getLogInstanc();
        this.sCL1Count = llamada.getSCL1Count();
        this.sCL2Count = llamada.getSCL2Count();
        this.insID = llamada.getInsID();
        this.technician = llamada.getTechnician();
        this.scl1NxtLn = llamada.getScl1NxtLn();
        this.scl2NxtLn = llamada.getScl2NxtLn();
        this.scl3NxtLn = llamada.getScl3NxtLn();
        this.scl4NxtLn = llamada.getScl4NxtLn();
        this.scl5NxtLn = llamada.getScl5NxtLn();
        this.startTime = llamada.getStartTime();
        this.endTime = llamada.getEndTime();
        this.resolTime = llamada.getResolTime();
        this.origin = llamada.getOrigin();
        this.itemGroup = llamada.getItemGroup();
        this.status = llamada.getStatus();
        this.callType = llamada.getCallType();
        this.problemTyp = llamada.getProblemTyp();
        this.assignee = llamada.getAssignee();
        this.userSign = llamada.getUserSign();
        this.createTime = llamada.getCreateTime();
        this.closeTime = llamada.getCloseTime();
        this.userSign2 = llamada.getUserSign2();
        this.resolOnTim = llamada.getResolOnTim();
        this.respByTime = llamada.getRespByTime();
        this.respOnTime = llamada.getRespOnTime();
        this.respAssign = llamada.getRespAssign();
        this.assignTime = llamada.getAssignTime();
        this.updateTime = llamada.getUpdateTime();
        this.responder = llamada.getResponder();
        this.instance = llamada.getInstance();
        this.series = llamada.getSeries();
        this.remTime = llamada.getRemTime();
        this.location = llamada.getLocation();
        this.uNWRRMAType = llamada.getUNWRRMAType();
        this.subject = llamada.getSubject();
        this.customer = llamada.getCustomer();
        this.custmrName = llamada.getCustmrName();
        this.manufSN = llamada.getManufSN();
        this.internalSN = llamada.getInternalSN();
        this.itemCode = llamada.getItemCode();
        this.itemName = llamada.getItemName();
        this.descrption = llamada.getDescrption();
        this.objType = llamada.getObjType();
        this.resolution = llamada.getResolution();
        this.queue = llamada.getQueue();
        this.pIndicator = llamada.getPIndicator();
        this.addrName = llamada.getAddrName();
        this.street = llamada.getStreet();
        this.city = llamada.getCity();
        this.room = llamada.getRoom();
        this.state = llamada.getState();
        this.country = llamada.getCountry();
        this.supplCode = llamada.getSupplCode();
        this.uCausa = llamada.getUCausa();
        this.uCateGa = llamada.getUCateGa();
        this.uProExt = llamada.getUProExt();
        this.uNumfactura = llamada.getUNumfactura();
        this.uTipoServ = llamada.getUTipoServ();
        this.uNwrRma = llamada.getUNwrRma();
        this.uNwrPo = llamada.getUNwrPo();
        this.free1 = llamada.getFree1();
        this.priority = llamada.getPriority();
        this.isEntitled = llamada.getIsEntitled();
        this.isQueue = llamada.getIsQueue();
        this.transfered = llamada.getTransfered();
        this.handwrtten = llamada.getHandwrtten();
        this.durType = llamada.getDurType();
        this.reminder = llamada.getReminder();
        this.remType = llamada.getRemType();
        this.remSent = llamada.getRemSent();
        this.addrType = llamada.getAddrType();
        this.displInCal = llamada.getDisplInCal();
        this.duration = llamada.getDuration();
        this.remQty = llamada.getRemQty();
        this.cntrctDate = llamada.getCntrctDate();
        this.resolDate = llamada.getResolDate();
        this.free2 = llamada.getFree2();
        this.createDate = llamada.getCreateDate();
        this.closeDate = llamada.getCloseDate();
        this.updateDate = llamada.getUpdateDate();
        this.resolOnDat = llamada.getResolOnDat();
        this.respByDate = llamada.getRespByDate();
        this.respOnDate = llamada.getRespOnDate();
        this.assignDate = llamada.getAssignDate();
        this.startDate = llamada.getStartDate();
        this.endDate = llamada.getEndDate();
        this.remDate = llamada.getRemDate();
    }

    public int getDocNum() {
        return docNum;
    }

    public void setDocNum(int docNum) {
        this.docNum = docNum;
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

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
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

    public Short getResolTime() {
        return resolTime;
    }

    public void setResolTime(Short resolTime) {
        this.resolTime = resolTime;
    }

    public Short getOrigin() {
        return origin;
    }

    public void setOrigin(Short origin) {
        this.origin = origin;
    }

    public Short getItemGroup() {
        return itemGroup;
    }

    public void setItemGroup(Short itemGroup) {
        this.itemGroup = itemGroup;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Short getCallType() {
        return callType;
    }

    public void setCallType(Short callType) {
        this.callType = callType;
    }

    public Short getProblemTyp() {
        return problemTyp;
    }

    public void setProblemTyp(Short problemTyp) {
        this.problemTyp = problemTyp;
    }

    public Short getAssignee() {
        return assignee;
    }

    public void setAssignee(Short assignee) {
        this.assignee = assignee;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Short getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Short createTime) {
        this.createTime = createTime;
    }

    public Short getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Short closeTime) {
        this.closeTime = closeTime;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public Short getResolOnTim() {
        return resolOnTim;
    }

    public void setResolOnTim(Short resolOnTim) {
        this.resolOnTim = resolOnTim;
    }

    public Short getRespByTime() {
        return respByTime;
    }

    public void setRespByTime(Short respByTime) {
        this.respByTime = respByTime;
    }

    public Short getRespOnTime() {
        return respOnTime;
    }

    public void setRespOnTime(Short respOnTime) {
        this.respOnTime = respOnTime;
    }

    public Short getRespAssign() {
        return respAssign;
    }

    public void setRespAssign(Short respAssign) {
        this.respAssign = respAssign;
    }

    public Short getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(Short assignTime) {
        this.assignTime = assignTime;
    }

    public Short getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Short updateTime) {
        this.updateTime = updateTime;
    }

    public Short getResponder() {
        return responder;
    }

    public void setResponder(Short responder) {
        this.responder = responder;
    }

    public Short getInstance() {
        return instance;
    }

    public void setInstance(Short instance) {
        this.instance = instance;
    }

    public Short getSeries() {
        return series;
    }

    public void setSeries(Short series) {
        this.series = series;
    }

    public Short getRemTime() {
        return remTime;
    }

    public void setRemTime(Short remTime) {
        this.remTime = remTime;
    }

    public Short getLocation() {
        return location;
    }

    public void setLocation(Short location) {
        this.location = location;
    }

    public Short getuNWRRMAType() {
        return uNWRRMAType;
    }

    public void setuNWRRMAType(Short uNWRRMAType) {
        this.uNWRRMAType = uNWRRMAType;
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

    public String getuNumfactura() {
        return uNumfactura;
    }

    public void setuNumfactura(String uNumfactura) {
        this.uNumfactura = uNumfactura;
    }

    public String getuTipoServ() {
        return uTipoServ;
    }

    public void setuTipoServ(String uTipoServ) {
        this.uTipoServ = uTipoServ;
    }

    public String getuNwrRma() {
        return uNwrRma;
    }

    public void setuNwrRma(String uNwrRma) {
        this.uNwrRma = uNwrRma;
    }

    public String getuNwrPo() {
        return uNwrPo;
    }

    public void setuNwrPo(String uNwrPo) {
        this.uNwrPo = uNwrPo;
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
