package co.matisses.web.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author dbotero
 */
public class EmpleadoDTO implements Comparable<EmpleadoDTO> {

    private String nombre;
    private String nombreCompleto;
    private String cedula;
    private String codigoAsesor;
    private String funcion;
    private String celularCorporativo;
    private String skypeCorporativo;
    private String departamento;
    private String rutaFoto;
    private String sucursalVenta;
    private boolean asesorSeleccionado;

    /*Campos desde base de datos*/
    private Integer empID;
    private Integer type;
    private Integer manager;
    private Integer userId;
    private Integer salesPrson;
    private Integer status;
    private Integer termReason;
    private Integer position;
    private Integer atcEntry;
    private Integer vacPreYear;
    private Integer vacCurYear;
    private Integer logInstanc;
    private Integer sTDCode;
    private Integer bPLId;
    private Short dept;
    private Short branch;
    private Short nChildren;
    private Short userSign;
    private Short userSign2;
    private BigDecimal salary;
    private BigDecimal emplCost;
    private BigDecimal exemptAmnt;
    private BigDecimal addiAmnt;
    private String lastName;
    private String firstName;
    private String middleName;
    private String jobTitle;
    private String workStreet;
    private String workBlock;
    private String workZip;
    private String workCity;
    private String workCounty;
    private String workCountr;
    private String workState;
    private String officeTel;
    private String officeExt;
    private String mobile;
    private String pager;
    private String homeTel;
    private String fax;
    private String email;
    private String bankCode;
    private String bankBranch;
    private String bankBranNo;
    private String bankAcount;
    private String homeStreet;
    private String homeBlock;
    private String homeZip;
    private String homeCity;
    private String homeCounty;
    private String homeCountr;
    private String homeState;
    private String brthCountr;
    private String govID;
    private String citizenshp;
    private String passportNo;
    private String picture;
    private String remark;
    private String attachment;
    private String salaryCurr;
    private String empCostCur;
    private String workBuild;
    private String homeBuild;
    private String addrTypeW;
    private String addrTypeH;
    private String streetNoW;
    private String streetNoH;
    private String costCenter;
    private String companyNum;
    private String munKey;
    private String taxClass;
    private String inTaxLiabi;
    private String emTaxCCode;
    private String relPartner;
    private String exemptUnit;
    private String addiUnit;
    private String taxOName;
    private String taxONum;
    private String heaInsName;
    private String heaInsCode;
    private String heaInsType;
    private String sInsurNum;
    private String statusOfP;
    private String statusOfE;
    private String bCodeDateV;
    private String fNameSP;
    private String surnameSP;
    private String persGroup;
    private String jTCode;
    private String extEmpNo;
    private String birthPlace;
    private String pymMeth;
    private String exemptCurr;
    private String addiCurr;
    private String fatherName;
    private String cpf;
    private String crc;
    private String ufCrc;
    private String iDType;
    private String manualNUM;
    private String passIssuer;
    private String qualCode;
    private String uUArp;
    private String uUEps;
    private String uUCaComp;
    private String uUPens;
    private String uCert;
    private String uCesantias;
    private String utipocontrato;
    private String ucodigoRevisado;
    private String ucorreoCorp;
    private String uMotivoCursoAlt;
    private Character sex;
    private Character salaryUnit;
    private Character empCostUnt;
    private Character martStatus;
    private Character dispMidNam;
    private Character namePos;
    private Character dispComma;
    private Character devBAOwner;
    private Character contResp;
    private Character repLegal;
    private Character dirfDeclar;
    private Character active;
    private Character pRWebAccss;
    private Character prePRWeb;
    private Date startDate;
    private Date termDate;
    private Date birthDate;
    private Date passportEx;
    private Date updateDate;
    private Date passIssue;
    private Date uUltCursoAltura;
    private RolDTO role;
    private EquipoDTO equipo;
    private List<AusentismoDTO> ausentismos;
    private List<FormacionDTO> formaciones;
    private List<ValoracionDTO> valoraciones;
    private List<EmpleoAnteriorDTO> empleos;

    public EmpleadoDTO() {
        role = new RolDTO();
        equipo = new EquipoDTO();
        ausentismos = new ArrayList<>();
        formaciones = new ArrayList<>();
        valoraciones = new ArrayList<>();
        empleos = new ArrayList<>();
    }

    public EmpleadoDTO(String nombre, String nombreCompleto, String cedula, String codigoAsesor, boolean asesorSeleccionado) {
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.codigoAsesor = codigoAsesor;
        this.asesorSeleccionado = asesorSeleccionado;
    }

    public EmpleadoDTO(String nombre, String nombreCompleto, String cedula, String codigoAsesor, String rutaFoto, String sucursalVenta, boolean asesorSeleccionado) {
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.codigoAsesor = codigoAsesor;
        this.rutaFoto = rutaFoto;
        this.sucursalVenta = sucursalVenta;
        this.asesorSeleccionado = asesorSeleccionado;
    }

    public EmpleadoDTO(String nombre, String nombreCompleto, String cedula, String codigoAsesor, String rutaFoto, String sucursalVenta, Date birthDate) {
        this.nombre = nombre;
        this.nombreCompleto = nombreCompleto;
        this.cedula = cedula;
        this.codigoAsesor = codigoAsesor;
        this.rutaFoto = rutaFoto;
        this.sucursalVenta = sucursalVenta;
        this.birthDate = birthDate;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoAsesor() {
        return codigoAsesor;
    }

    public void setCodigoAsesor(String codigoAsesor) {
        this.codigoAsesor = codigoAsesor;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean isAsesorSeleccionado() {
        return asesorSeleccionado;
    }

    public void setAsesorSeleccionado(boolean asesorSeleccionado) {
        this.asesorSeleccionado = asesorSeleccionado;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public String getCelularCorporativo() {
        return celularCorporativo;
    }

    public void setCelularCorporativo(String celularCorporativo) {
        this.celularCorporativo = celularCorporativo;
    }

    public String getSkypeCorporativo() {
        return skypeCorporativo;
    }

    public void setSkypeCorporativo(String skypeCorporativo) {
        this.skypeCorporativo = skypeCorporativo;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRutaFoto() {
        return rutaFoto;
    }

    public void setRutaFoto(String rutaFoto) {
        this.rutaFoto = rutaFoto;
    }

    public String getSucursalVenta() {
        return sucursalVenta;
    }

    public void setSucursalVenta(String sucursalVenta) {
        this.sucursalVenta = sucursalVenta;
    }

    public Integer getEmpID() {
        return empID;
    }

    public void setEmpID(Integer empID) {
        this.empID = empID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getManager() {
        return manager;
    }

    public void setManager(Integer manager) {
        this.manager = manager;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSalesPrson() {
        return salesPrson;
    }

    public void setSalesPrson(Integer salesPrson) {
        this.salesPrson = salesPrson;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTermReason() {
        return termReason;
    }

    public void setTermReason(Integer termReason) {
        this.termReason = termReason;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public Integer getAtcEntry() {
        return atcEntry;
    }

    public void setAtcEntry(Integer atcEntry) {
        this.atcEntry = atcEntry;
    }

    public Integer getVacPreYear() {
        return vacPreYear;
    }

    public void setVacPreYear(Integer vacPreYear) {
        this.vacPreYear = vacPreYear;
    }

    public Integer getVacCurYear() {
        return vacCurYear;
    }

    public void setVacCurYear(Integer vacCurYear) {
        this.vacCurYear = vacCurYear;
    }

    public Integer getLogInstanc() {
        return logInstanc;
    }

    public void setLogInstanc(Integer logInstanc) {
        this.logInstanc = logInstanc;
    }

    public Integer getsTDCode() {
        return sTDCode;
    }

    public void setsTDCode(Integer sTDCode) {
        this.sTDCode = sTDCode;
    }

    public Integer getbPLId() {
        return bPLId;
    }

    public void setbPLId(Integer bPLId) {
        this.bPLId = bPLId;
    }

    public Short getDept() {
        return dept;
    }

    public void setDept(Short dept) {
        this.dept = dept;
    }

    public Short getBranch() {
        return branch;
    }

    public void setBranch(Short branch) {
        this.branch = branch;
    }

    public Short getnChildren() {
        return nChildren;
    }

    public void setnChildren(Short nChildren) {
        this.nChildren = nChildren;
    }

    public Short getUserSign() {
        return userSign;
    }

    public void setUserSign(Short userSign) {
        this.userSign = userSign;
    }

    public Short getUserSign2() {
        return userSign2;
    }

    public void setUserSign2(Short userSign2) {
        this.userSign2 = userSign2;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public BigDecimal getEmplCost() {
        return emplCost;
    }

    public void setEmplCost(BigDecimal emplCost) {
        this.emplCost = emplCost;
    }

    public BigDecimal getExemptAmnt() {
        return exemptAmnt;
    }

    public void setExemptAmnt(BigDecimal exemptAmnt) {
        this.exemptAmnt = exemptAmnt;
    }

    public BigDecimal getAddiAmnt() {
        return addiAmnt;
    }

    public void setAddiAmnt(BigDecimal addiAmnt) {
        this.addiAmnt = addiAmnt;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getWorkStreet() {
        return workStreet;
    }

    public void setWorkStreet(String workStreet) {
        this.workStreet = workStreet;
    }

    public String getWorkBlock() {
        return workBlock;
    }

    public void setWorkBlock(String workBlock) {
        this.workBlock = workBlock;
    }

    public String getWorkZip() {
        return workZip;
    }

    public void setWorkZip(String workZip) {
        this.workZip = workZip;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String workCity) {
        this.workCity = workCity;
    }

    public String getWorkCounty() {
        return workCounty;
    }

    public void setWorkCounty(String workCounty) {
        this.workCounty = workCounty;
    }

    public String getWorkCountr() {
        return workCountr;
    }

    public void setWorkCountr(String workCountr) {
        this.workCountr = workCountr;
    }

    public String getWorkState() {
        return workState;
    }

    public void setWorkState(String workState) {
        this.workState = workState;
    }

    public String getOfficeTel() {
        return officeTel;
    }

    public void setOfficeTel(String officeTel) {
        this.officeTel = officeTel;
    }

    public String getOfficeExt() {
        return officeExt;
    }

    public void setOfficeExt(String officeExt) {
        this.officeExt = officeExt;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String pager) {
        this.pager = pager;
    }

    public String getHomeTel() {
        return homeTel;
    }

    public void setHomeTel(String homeTel) {
        this.homeTel = homeTel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankBranNo() {
        return bankBranNo;
    }

    public void setBankBranNo(String bankBranNo) {
        this.bankBranNo = bankBranNo;
    }

    public String getBankAcount() {
        return bankAcount;
    }

    public void setBankAcount(String bankAcount) {
        this.bankAcount = bankAcount;
    }

    public String getHomeStreet() {
        return homeStreet;
    }

    public void setHomeStreet(String homeStreet) {
        this.homeStreet = homeStreet;
    }

    public String getHomeBlock() {
        return homeBlock;
    }

    public void setHomeBlock(String homeBlock) {
        this.homeBlock = homeBlock;
    }

    public String getHomeZip() {
        return homeZip;
    }

    public void setHomeZip(String homeZip) {
        this.homeZip = homeZip;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomeCounty() {
        return homeCounty;
    }

    public void setHomeCounty(String homeCounty) {
        this.homeCounty = homeCounty;
    }

    public String getHomeCountr() {
        return homeCountr;
    }

    public void setHomeCountr(String homeCountr) {
        this.homeCountr = homeCountr;
    }

    public String getHomeState() {
        return homeState;
    }

    public void setHomeState(String homeState) {
        this.homeState = homeState;
    }

    public String getBrthCountr() {
        return brthCountr;
    }

    public void setBrthCountr(String brthCountr) {
        this.brthCountr = brthCountr;
    }

    public String getGovID() {
        return govID;
    }

    public void setGovID(String govID) {
        this.govID = govID;
    }

    public String getCitizenshp() {
        return citizenshp;
    }

    public void setCitizenshp(String citizenshp) {
        this.citizenshp = citizenshp;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    public String getSalaryCurr() {
        return salaryCurr;
    }

    public void setSalaryCurr(String salaryCurr) {
        this.salaryCurr = salaryCurr;
    }

    public String getEmpCostCur() {
        return empCostCur;
    }

    public void setEmpCostCur(String empCostCur) {
        this.empCostCur = empCostCur;
    }

    public String getWorkBuild() {
        return workBuild;
    }

    public void setWorkBuild(String workBuild) {
        this.workBuild = workBuild;
    }

    public String getHomeBuild() {
        return homeBuild;
    }

    public void setHomeBuild(String homeBuild) {
        this.homeBuild = homeBuild;
    }

    public String getAddrTypeW() {
        return addrTypeW;
    }

    public void setAddrTypeW(String addrTypeW) {
        this.addrTypeW = addrTypeW;
    }

    public String getAddrTypeH() {
        return addrTypeH;
    }

    public void setAddrTypeH(String addrTypeH) {
        this.addrTypeH = addrTypeH;
    }

    public String getStreetNoW() {
        return streetNoW;
    }

    public void setStreetNoW(String streetNoW) {
        this.streetNoW = streetNoW;
    }

    public String getStreetNoH() {
        return streetNoH;
    }

    public void setStreetNoH(String streetNoH) {
        this.streetNoH = streetNoH;
    }

    public String getCostCenter() {
        return costCenter;
    }

    public void setCostCenter(String costCenter) {
        this.costCenter = costCenter;
    }

    public String getCompanyNum() {
        return companyNum;
    }

    public void setCompanyNum(String companyNum) {
        this.companyNum = companyNum;
    }

    public String getMunKey() {
        return munKey;
    }

    public void setMunKey(String munKey) {
        this.munKey = munKey;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String taxClass) {
        this.taxClass = taxClass;
    }

    public String getInTaxLiabi() {
        return inTaxLiabi;
    }

    public void setInTaxLiabi(String inTaxLiabi) {
        this.inTaxLiabi = inTaxLiabi;
    }

    public String getEmTaxCCode() {
        return emTaxCCode;
    }

    public void setEmTaxCCode(String emTaxCCode) {
        this.emTaxCCode = emTaxCCode;
    }

    public String getRelPartner() {
        return relPartner;
    }

    public void setRelPartner(String relPartner) {
        this.relPartner = relPartner;
    }

    public String getExemptUnit() {
        return exemptUnit;
    }

    public void setExemptUnit(String exemptUnit) {
        this.exemptUnit = exemptUnit;
    }

    public String getAddiUnit() {
        return addiUnit;
    }

    public void setAddiUnit(String addiUnit) {
        this.addiUnit = addiUnit;
    }

    public String getTaxOName() {
        return taxOName;
    }

    public void setTaxOName(String taxOName) {
        this.taxOName = taxOName;
    }

    public String getTaxONum() {
        return taxONum;
    }

    public void setTaxONum(String taxONum) {
        this.taxONum = taxONum;
    }

    public String getHeaInsName() {
        return heaInsName;
    }

    public void setHeaInsName(String heaInsName) {
        this.heaInsName = heaInsName;
    }

    public String getHeaInsCode() {
        return heaInsCode;
    }

    public void setHeaInsCode(String heaInsCode) {
        this.heaInsCode = heaInsCode;
    }

    public String getHeaInsType() {
        return heaInsType;
    }

    public void setHeaInsType(String heaInsType) {
        this.heaInsType = heaInsType;
    }

    public String getsInsurNum() {
        return sInsurNum;
    }

    public void setsInsurNum(String sInsurNum) {
        this.sInsurNum = sInsurNum;
    }

    public String getStatusOfP() {
        return statusOfP;
    }

    public void setStatusOfP(String statusOfP) {
        this.statusOfP = statusOfP;
    }

    public String getStatusOfE() {
        return statusOfE;
    }

    public void setStatusOfE(String statusOfE) {
        this.statusOfE = statusOfE;
    }

    public String getbCodeDateV() {
        return bCodeDateV;
    }

    public void setbCodeDateV(String bCodeDateV) {
        this.bCodeDateV = bCodeDateV;
    }

    public String getfNameSP() {
        return fNameSP;
    }

    public void setfNameSP(String fNameSP) {
        this.fNameSP = fNameSP;
    }

    public String getSurnameSP() {
        return surnameSP;
    }

    public void setSurnameSP(String surnameSP) {
        this.surnameSP = surnameSP;
    }

    public String getPersGroup() {
        return persGroup;
    }

    public void setPersGroup(String persGroup) {
        this.persGroup = persGroup;
    }

    public String getjTCode() {
        return jTCode;
    }

    public void setjTCode(String jTCode) {
        this.jTCode = jTCode;
    }

    public String getExtEmpNo() {
        return extEmpNo;
    }

    public void setExtEmpNo(String extEmpNo) {
        this.extEmpNo = extEmpNo;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String birthPlace) {
        this.birthPlace = birthPlace;
    }

    public String getPymMeth() {
        return pymMeth;
    }

    public void setPymMeth(String pymMeth) {
        this.pymMeth = pymMeth;
    }

    public String getExemptCurr() {
        return exemptCurr;
    }

    public void setExemptCurr(String exemptCurr) {
        this.exemptCurr = exemptCurr;
    }

    public String getAddiCurr() {
        return addiCurr;
    }

    public void setAddiCurr(String addiCurr) {
        this.addiCurr = addiCurr;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public String getUfCrc() {
        return ufCrc;
    }

    public void setUfCrc(String ufCrc) {
        this.ufCrc = ufCrc;
    }

    public String getiDType() {
        return iDType;
    }

    public void setiDType(String iDType) {
        this.iDType = iDType;
    }

    public String getManualNUM() {
        return manualNUM;
    }

    public void setManualNUM(String manualNUM) {
        this.manualNUM = manualNUM;
    }

    public String getPassIssuer() {
        return passIssuer;
    }

    public void setPassIssuer(String passIssuer) {
        this.passIssuer = passIssuer;
    }

    public String getQualCode() {
        return qualCode;
    }

    public void setQualCode(String qualCode) {
        this.qualCode = qualCode;
    }

    public String getuUArp() {
        return uUArp;
    }

    public void setuUArp(String uUArp) {
        this.uUArp = uUArp;
    }

    public String getuUEps() {
        return uUEps;
    }

    public void setuUEps(String uUEps) {
        this.uUEps = uUEps;
    }

    public String getuUCaComp() {
        return uUCaComp;
    }

    public void setuUCaComp(String uUCaComp) {
        this.uUCaComp = uUCaComp;
    }

    public String getuUPens() {
        return uUPens;
    }

    public void setuUPens(String uUPens) {
        this.uUPens = uUPens;
    }

    public String getuCert() {
        return uCert;
    }

    public void setuCert(String uCert) {
        this.uCert = uCert;
    }

    public String getuCesantias() {
        return uCesantias;
    }

    public void setuCesantias(String uCesantias) {
        this.uCesantias = uCesantias;
    }

    public String getUtipocontrato() {
        return utipocontrato;
    }

    public void setUtipocontrato(String utipocontrato) {
        this.utipocontrato = utipocontrato;
    }

    public String getUcodigoRevisado() {
        return ucodigoRevisado;
    }

    public void setUcodigoRevisado(String ucodigoRevisado) {
        this.ucodigoRevisado = ucodigoRevisado;
    }

    public String getUcorreoCorp() {
        return ucorreoCorp;
    }

    public void setUcorreoCorp(String ucorreoCorp) {
        this.ucorreoCorp = ucorreoCorp;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    public Character getSalaryUnit() {
        return salaryUnit;
    }

    public void setSalaryUnit(Character salaryUnit) {
        this.salaryUnit = salaryUnit;
    }

    public Character getEmpCostUnt() {
        return empCostUnt;
    }

    public void setEmpCostUnt(Character empCostUnt) {
        this.empCostUnt = empCostUnt;
    }

    public Character getMartStatus() {
        return martStatus;
    }

    public void setMartStatus(Character martStatus) {
        this.martStatus = martStatus;
    }

    public Character getDispMidNam() {
        return dispMidNam;
    }

    public void setDispMidNam(Character dispMidNam) {
        this.dispMidNam = dispMidNam;
    }

    public Character getNamePos() {
        return namePos;
    }

    public void setNamePos(Character namePos) {
        this.namePos = namePos;
    }

    public Character getDispComma() {
        return dispComma;
    }

    public void setDispComma(Character dispComma) {
        this.dispComma = dispComma;
    }

    public Character getDevBAOwner() {
        return devBAOwner;
    }

    public void setDevBAOwner(Character devBAOwner) {
        this.devBAOwner = devBAOwner;
    }

    public Character getContResp() {
        return contResp;
    }

    public void setContResp(Character contResp) {
        this.contResp = contResp;
    }

    public Character getRepLegal() {
        return repLegal;
    }

    public void setRepLegal(Character repLegal) {
        this.repLegal = repLegal;
    }

    public Character getDirfDeclar() {
        return dirfDeclar;
    }

    public void setDirfDeclar(Character dirfDeclar) {
        this.dirfDeclar = dirfDeclar;
    }

    public Character getActive() {
        return active;
    }

    public void setActive(Character active) {
        this.active = active;
    }

    public Character getpRWebAccss() {
        return pRWebAccss;
    }

    public void setpRWebAccss(Character pRWebAccss) {
        this.pRWebAccss = pRWebAccss;
    }

    public Character getPrePRWeb() {
        return prePRWeb;
    }

    public void setPrePRWeb(Character prePRWeb) {
        this.prePRWeb = prePRWeb;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getTermDate() {
        return termDate;
    }

    public void setTermDate(Date termDate) {
        this.termDate = termDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Date getPassportEx() {
        return passportEx;
    }

    public void setPassportEx(Date passportEx) {
        this.passportEx = passportEx;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getPassIssue() {
        return passIssue;
    }

    public void setPassIssue(Date passIssue) {
        this.passIssue = passIssue;
    }

    public RolDTO getRole() {
        return role;
    }

    public void setRole(RolDTO role) {
        this.role = role;
    }

    public EquipoDTO getEquipo() {
        return equipo;
    }

    public void setEquipo(EquipoDTO equipo) {
        this.equipo = equipo;
    }

    public String getUmotivoCursoAlt() {
        return uMotivoCursoAlt;
    }

    public void setUmotivoCursoAlt(String uMotivoCursoAlt) {
        this.uMotivoCursoAlt = uMotivoCursoAlt;
    }

    public Date getUultCursoAltura() {
        return uUltCursoAltura;
    }

    public void setUultCursoAltura(Date uUltCursoAltura) {
        this.uUltCursoAltura = uUltCursoAltura;
    }

    public List<AusentismoDTO> getAusentismos() {
        return ausentismos;
    }

    public void setAusentismos(List<AusentismoDTO> ausentismos) {
        this.ausentismos = ausentismos;
    }

    public List<FormacionDTO> getFormaciones() {
        return formaciones;
    }

    public void setFormaciones(List<FormacionDTO> formaciones) {
        this.formaciones = formaciones;
    }

    public List<ValoracionDTO> getValoraciones() {
        return valoraciones;
    }

    public void setValoraciones(List<ValoracionDTO> valoraciones) {
        this.valoraciones = valoraciones;
    }

    public List<EmpleoAnteriorDTO> getEmpleos() {
        return empleos;
    }

    public void setEmpleos(List<EmpleoAnteriorDTO> empleos) {
        this.empleos = empleos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cedula);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EmpleadoDTO other = (EmpleadoDTO) obj;
        if (!Objects.equals(this.cedula, other.cedula)) {
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(EmpleadoDTO o) {
        return this.nombre.compareTo(o.getNombre());
    }

    @Override
    public String toString() {
        return "EmpleadoDTO{" + "nombre=" + nombre + ", nombreCompleto=" + nombreCompleto + ", cedula=" + cedula + ", codigoAsesor=" + codigoAsesor + '}';
    }
}
