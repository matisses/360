package co.matisses.web.bcs.employeesinfo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@XmlRootElement(name = "EmployeeInfo")
public class EmployeeInfo {

    @XmlElement(name = "EmployeeID")
    protected Long employeeID;
    @XmlElement(name = "LastName")
    protected String lastName;
    @XmlElement(name = "FirstName")
    protected String firstName;
    @XmlElement(name = "MiddleName")
    protected String middleName;
    @XmlElement(name = "Gender")
    protected String gender;
    @XmlElement(name = "JobTitle")
    protected String jobTitle;
    @XmlElement(name = "EmployeeType")
    protected Long employeeType;
    @XmlElement(name = "Department")
    protected Long department;
    @XmlElement(name = "Branch")
    protected Long branch;
    @XmlElement(name = "WorkStreet")
    protected String workStreet;
    @XmlElement(name = "WorkBlock")
    protected String workBlock;
    @XmlElement(name = "WorkZipCode")
    protected String workZipCode;
    @XmlElement(name = "WorkCity")
    protected String workCity;
    @XmlElement(name = "WorkCounty")
    protected String workCounty;
    @XmlElement(name = "WorkCountryCode")
    protected String workCountryCode;
    @XmlElement(name = "WorkStateCode")
    protected String workStateCode;
    @XmlElement(name = "Manager")
    protected Long manager;
    @XmlElement(name = "ApplicationUserID")
    protected Long applicationUserID;
    @XmlElement(name = "SalesPersonCode")
    protected Long salesPersonCode;
    @XmlElement(name = "OfficePhone")
    protected String officePhone;
    @XmlElement(name = "OfficeExtension")
    protected String officeExtension;
    @XmlElement(name = "MobilePhone")
    protected String mobilePhone;
    @XmlElement(name = "Pager")
    protected String pager;
    @XmlElement(name = "HomePhone")
    protected String homePhone;
    @XmlElement(name = "Fax")
    protected String fax;
    protected String eMail;
    @XmlElement(name = "StartDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar startDate;
    private Date fechaInicio;
    @XmlElement(name = "StatusCode")
    protected Long statusCode;
    @XmlElement(name = "Salary")
    protected Double salary;
    @XmlElement(name = "SalaryUnit")
    protected String salaryUnit;
    @XmlElement(name = "EmployeeCosts")
    protected Double employeeCosts;
    @XmlElement(name = "EmployeeCostUnit")
    protected String employeeCostUnit;
    @XmlElement(name = "TerminationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar terminationDate;
    private Date fechaFin;
    @XmlElement(name = "TreminationReason")
    protected Long treminationReason;
    @XmlElement(name = "BankCode")
    protected String bankCode;
    @XmlElement(name = "BankBranch")
    protected String bankBranch;
    @XmlElement(name = "BankBranchNum")
    protected String bankBranchNum;
    @XmlElement(name = "BankAccount")
    protected String bankAccount;
    @XmlElement(name = "HomeStreet")
    protected String homeStreet;
    @XmlElement(name = "HomeBlock")
    protected String homeBlock;
    @XmlElement(name = "HomeZipCode")
    protected String homeZipCode;
    @XmlElement(name = "HomeCity")
    protected String homeCity;
    @XmlElement(name = "HomeCounty")
    protected String homeCounty;
    @XmlElement(name = "HomeCountry")
    protected String homeCountry;
    @XmlElement(name = "HomeState")
    protected String homeState;
    @XmlElement(name = "HomeStreetNumber")
    protected String homeStreetNumber;
    @XmlElement(name = "DateOfBirth")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dateOfBirth;
    private Date fechaNacimiento;
    @XmlElement(name = "CountryOfBirth")
    protected String countryOfBirth;
    @XmlElement(name = "MartialStatus")
    protected String martialStatus;
    @XmlElement(name = "NumOfChildren")
    protected Long numOfChildren;
    @XmlElement(name = "IdNumber")
    protected String idNumber;
    @XmlElement(name = "CitizenshipCountryCode")
    protected String citizenshipCountryCode;
    @XmlElement(name = "PassportNumber")
    protected String passportNumber;
    @XmlElement(name = "PassportExpirationDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar passportExpirationDate;
    @XmlElement(name = "Picture")
    protected String picture;
    @XmlElement(name = "Remarks")
    protected String remarks;
    @XmlElement(name = "SalaryCurrency")
    protected String salaryCurrency;
    @XmlElement(name = "EmployeeCostsCurrency")
    protected String employeeCostsCurrency;
    @XmlElement(name = "WorkBuildingFloorRoom")
    protected String workBuildingFloorRoom;
    @XmlElement(name = "HomeBuildingFloorRoom")
    protected String homeBuildingFloorRoom;
    @XmlElement(name = "Position")
    protected Long position;
    @XmlElement(name = "AttachmentEntry")
    protected Long attachmentEntry;
    @XmlElement(name = "CostCenterCode")
    protected String costCenterCode;
    @XmlElement(name = "CompanyNumber")
    protected String companyNumber;
    @XmlElement(name = "VacationPreviousYear")
    protected Long vacationPreviousYear;
    @XmlElement(name = "VacationCurrentYear")
    protected Long vacationCurrentYear;
    @XmlElement(name = "MunicipalityKey")
    protected String municipalityKey;
    @XmlElement(name = "TaxClass")
    protected String taxClass;
    @XmlElement(name = "IncomeTaxLiability")
    protected String incomeTaxLiability;
    @XmlElement(name = "Religion")
    protected String religion;
    @XmlElement(name = "PartnerReligion")
    protected String partnerReligion;
    @XmlElement(name = "ExemptionAmount")
    protected Double exemptionAmount;
    @XmlElement(name = "ExemptionUnit")
    protected String exemptionUnit;
    @XmlElement(name = "ExemptionCurrency")
    protected String exemptionCurrency;
    @XmlElement(name = "AdditionalAmount")
    protected Double additionalAmount;
    @XmlElement(name = "AdditionalUnit")
    protected String additionalUnit;
    @XmlElement(name = "AdditionalCurrency")
    protected String additionalCurrency;
    @XmlElement(name = "TaxOfficeName")
    protected String taxOfficeName;
    @XmlElement(name = "TaxOfficeNumber")
    protected String taxOfficeNumber;
    @XmlElement(name = "HealthInsuranceName")
    protected String healthInsuranceName;
    @XmlElement(name = "HealthInsuranceCode")
    protected String healthInsuranceCode;
    @XmlElement(name = "HealthInsuranceType")
    protected String healthInsuranceType;
    @XmlElement(name = "SocialInsuranceNumber")
    protected String socialInsuranceNumber;
    @XmlElement(name = "ProfessionStatus")
    protected String professionStatus;
    @XmlElement(name = "EducationStatus")
    protected String educationStatus;
    @XmlElement(name = "PersonGroup")
    protected String personGroup;
    @XmlElement(name = "JobTitleCode")
    protected String jobTitleCode;
    @XmlElement(name = "BankCodeForDATEV")
    protected String bankCodeForDATEV;
    @XmlElement(name = "DeviatingBankAccountOwner")
    protected String deviatingBankAccountOwner;
    @XmlElement(name = "SpouseFirstName")
    protected String spouseFirstName;
    @XmlElement(name = "SpouseSurname")
    protected String spouseSurname;
    @XmlElement(name = "ExternalEmployeeNumber")
    protected String externalEmployeeNumber;
    @XmlElement(name = "BirthPlace")
    protected String birthPlace;
    @XmlElement(name = "PaymentMethod")
    protected String paymentMethod;
    @XmlElement(name = "STDCode")
    protected Long stdCode;
    @XmlElement(name = "CPF")
    protected String cpf;
    @XmlElement(name = "CRCNumber")
    protected String crcNumber;
    @XmlElement(name = "AccountantResponsible")
    protected String accountantResponsible;
    @XmlElement(name = "LegalRepresentative")
    protected String legalRepresentative;
    @XmlElement(name = "DIRFResponsible")
    protected String dirfResponsible;
    @XmlElement(name = "CRCState")
    protected String crcState;
    @XmlElement(name = "Active")
    protected String active;
    @XmlElement(name = "IDType")
    protected String idType;
    @XmlElement(name = "BPLID")
    protected Long bplid;
    @XmlElement(name = "PassportIssueDate")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar passportIssueDate;
    @XmlElement(name = "PassportIssuer")
    protected String passportIssuer;
    @XmlElement(name = "QualificationCode")
    protected String qualificationCode;
    @XmlElement(name = "PRWebAccess")
    protected String prWebAccess;
    @XmlElement(name = "PreviousPRWebAccess")
    protected String previousPRWebAccess;
    @XmlElement(name = "U_U_Arp")
    protected String arp;
    @XmlElement(name = "U_U_Eps")
    protected String eps;
    @XmlElement(name = "U_U_CaComp")
    protected String caComp;
    @XmlElement(name = "U_U_Pens")
    protected String pens;
    @XmlElement(name = "U_Cert")
    protected String cert;
    @XmlElement(name = "U_Cesantias")
    protected String cesantias;
    @XmlElement(name = "U_tipocontrato")
    protected String tipoContrato;
    @XmlElement(name = "U_codigoRevisado")
    protected String codigoRevisado;
    @XmlElement(name = "U_CorreoCorp")
    protected String correoCorp;
    @XmlElement(name = "U_UltCursoAltura")
    protected XMLGregorianCalendar ultCursoAltura;
    private Date fechaCursoAlturas;
    @XmlElement(name = "U_MotivoCursoAlt")
    protected String motivoCursoAlt;
    @XmlElement(name = "EmployeeAbsenceInfoLines")
    protected EmployeeInfo.EmployeeAbsenceInfoLines employeeAbsenceInfoLines;
    @XmlElement(name = "EmployeeEducationInfoLines")
    protected EmployeeInfo.EmployeeEducationInfoLines employeeEducationInfoLines;
    @XmlElement(name = "EmployeeReviewsInfoLines")
    protected EmployeeInfo.EmployeeReviewsInfoLines employeeReviewsInfoLines;
    @XmlElement(name = "EmployeePreviousEmpoymentInfoLines")
    protected EmployeeInfo.EmployeePreviousEmpoymentInfoLines employeePreviousEmpoymentInfoLines;
    @XmlElement(name = "EmployeeRolesInfoLines")
    protected EmployeeInfo.EmployeeRolesInfoLines employeeRolesInfoLines;
    @XmlElement(name = "EmployeeSavingsPaymentInfoLines")
    protected EmployeeInfo.EmployeeSavingsPaymentInfoLines employeeSavingsPaymentInfoLines;

    public Long getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(Long value) {
        this.employeeID = value;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String value) {
        this.lastName = value;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String value) {
        this.firstName = value;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String value) {
        this.middleName = value;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String value) {
        this.gender = value;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String value) {
        this.jobTitle = value;
    }

    public Long getEmployeeType() {
        return employeeType;
    }

    public void setEmployeeType(Long value) {
        this.employeeType = value;
    }

    public Long getDepartment() {
        return department;
    }

    public void setDepartment(Long value) {
        this.department = value;
    }

    public Long getBranch() {
        return branch;
    }

    public void setBranch(Long value) {
        this.branch = value;
    }

    public String getWorkStreet() {
        return workStreet;
    }

    public void setWorkStreet(String value) {
        this.workStreet = value;
    }

    public String getWorkBlock() {
        return workBlock;
    }

    public void setWorkBlock(String value) {
        this.workBlock = value;
    }

    public String getWorkZipCode() {
        return workZipCode;
    }

    public void setWorkZipCode(String value) {
        this.workZipCode = value;
    }

    public String getWorkCity() {
        return workCity;
    }

    public void setWorkCity(String value) {
        this.workCity = value;
    }

    public String getWorkCounty() {
        return workCounty;
    }

    public void setWorkCounty(String value) {
        this.workCounty = value;
    }

    public String getWorkCountryCode() {
        return workCountryCode;
    }

    public void setWorkCountryCode(String value) {
        this.workCountryCode = value;
    }

    public String getWorkStateCode() {
        return workStateCode;
    }

    public void setWorkStateCode(String value) {
        this.workStateCode = value;
    }

    public Long getManager() {
        return manager;
    }

    public void setManager(Long value) {
        this.manager = value;
    }

    public Long getApplicationUserID() {
        return applicationUserID;
    }

    public void setApplicationUserID(Long value) {
        this.applicationUserID = value;
    }

    public Long getSalesPersonCode() {
        return salesPersonCode;
    }

    public void setSalesPersonCode(Long value) {
        this.salesPersonCode = value;
    }

    public String getOfficePhone() {
        return officePhone;
    }

    public void setOfficePhone(String value) {
        this.officePhone = value;
    }

    public String getOfficeExtension() {
        return officeExtension;
    }

    public void setOfficeExtension(String value) {
        this.officeExtension = value;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String value) {
        this.mobilePhone = value;
    }

    public String getPager() {
        return pager;
    }

    public void setPager(String value) {
        this.pager = value;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String value) {
        this.homePhone = value;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String value) {
        this.fax = value;
    }

    public String getEMail() {
        return eMail;
    }

    public void setEMail(String value) {
        this.eMail = value;
    }

    public XMLGregorianCalendar getStartDate() {
        return startDate;
    }

    public void setStartDate(XMLGregorianCalendar value) {
        this.startDate = value;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Long getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Long value) {
        this.statusCode = value;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double value) {
        this.salary = value;
    }

    public String getSalaryUnit() {
        return salaryUnit;
    }

    public void setSalaryUnit(String value) {
        this.salaryUnit = value;
    }

    public Double getEmployeeCosts() {
        return employeeCosts;
    }

    public void setEmployeeCosts(Double value) {
        this.employeeCosts = value;
    }

    public String getEmployeeCostUnit() {
        return employeeCostUnit;
    }

    public void setEmployeeCostUnit(String value) {
        this.employeeCostUnit = value;
    }

    public XMLGregorianCalendar getTerminationDate() {
        return terminationDate;
    }

    public void setTerminationDate(XMLGregorianCalendar value) {
        this.terminationDate = value;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public Long getTreminationReason() {
        return treminationReason;
    }

    public void setTreminationReason(Long value) {
        this.treminationReason = value;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String value) {
        this.bankCode = value;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String value) {
        this.bankBranch = value;
    }

    public String getBankBranchNum() {
        return bankBranchNum;
    }

    public void setBankBranchNum(String value) {
        this.bankBranchNum = value;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String value) {
        this.bankAccount = value;
    }

    public String getHomeStreet() {
        return homeStreet;
    }

    public void setHomeStreet(String value) {
        this.homeStreet = value;
    }

    public String getHomeBlock() {
        return homeBlock;
    }

    public void setHomeBlock(String value) {
        this.homeBlock = value;
    }

    public String getHomeZipCode() {
        return homeZipCode;
    }

    public void setHomeZipCode(String value) {
        this.homeZipCode = value;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String value) {
        this.homeCity = value;
    }

    public String getHomeCounty() {
        return homeCounty;
    }

    public void setHomeCounty(String value) {
        this.homeCounty = value;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String value) {
        this.homeCountry = value;
    }

    public String getHomeState() {
        return homeState;
    }

    public void setHomeState(String value) {
        this.homeState = value;
    }

    public String getHomeStreetNumber() {
        return homeStreetNumber;
    }

    public void setHomeStreetNumber(String homeStreetNumber) {
        this.homeStreetNumber = homeStreetNumber;
    }

    public XMLGregorianCalendar getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(XMLGregorianCalendar value) {
        this.dateOfBirth = value;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCountryOfBirth() {
        return countryOfBirth;
    }

    public void setCountryOfBirth(String value) {
        this.countryOfBirth = value;
    }

    public String getMartialStatus() {
        return martialStatus;
    }

    public void setMartialStatus(String value) {
        this.martialStatus = value;
    }

    public Long getNumOfChildren() {
        return numOfChildren;
    }

    public void setNumOfChildren(Long value) {
        this.numOfChildren = value;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String value) {
        this.idNumber = value;
    }

    public String getCitizenshipCountryCode() {
        return citizenshipCountryCode;
    }

    public void setCitizenshipCountryCode(String value) {
        this.citizenshipCountryCode = value;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String value) {
        this.passportNumber = value;
    }

    public XMLGregorianCalendar getPassportExpirationDate() {
        return passportExpirationDate;
    }

    public void setPassportExpirationDate(XMLGregorianCalendar value) {
        this.passportExpirationDate = value;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String value) {
        this.picture = value;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String value) {
        this.remarks = value;
    }

    public String getSalaryCurrency() {
        return salaryCurrency;
    }

    public void setSalaryCurrency(String value) {
        this.salaryCurrency = value;
    }

    public String getEmployeeCostsCurrency() {
        return employeeCostsCurrency;
    }

    public void setEmployeeCostsCurrency(String value) {
        this.employeeCostsCurrency = value;
    }

    public String getWorkBuildingFloorRoom() {
        return workBuildingFloorRoom;
    }

    public void setWorkBuildingFloorRoom(String value) {
        this.workBuildingFloorRoom = value;
    }

    public String getHomeBuildingFloorRoom() {
        return homeBuildingFloorRoom;
    }

    public void setHomeBuildingFloorRoom(String value) {
        this.homeBuildingFloorRoom = value;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long value) {
        this.position = value;
    }

    public Long getAttachmentEntry() {
        return attachmentEntry;
    }

    public void setAttachmentEntry(Long value) {
        this.attachmentEntry = value;
    }

    public String getCostCenterCode() {
        return costCenterCode;
    }

    public void setCostCenterCode(String value) {
        this.costCenterCode = value;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String value) {
        this.companyNumber = value;
    }

    public Long getVacationPreviousYear() {
        return vacationPreviousYear;
    }

    public void setVacationPreviousYear(Long value) {
        this.vacationPreviousYear = value;
    }

    public Long getVacationCurrentYear() {
        return vacationCurrentYear;
    }

    public void setVacationCurrentYear(Long value) {
        this.vacationCurrentYear = value;
    }

    public String getMunicipalityKey() {
        return municipalityKey;
    }

    public void setMunicipalityKey(String value) {
        this.municipalityKey = value;
    }

    public String getTaxClass() {
        return taxClass;
    }

    public void setTaxClass(String value) {
        this.taxClass = value;
    }

    public String getIncomeTaxLiability() {
        return incomeTaxLiability;
    }

    public void setIncomeTaxLiability(String value) {
        this.incomeTaxLiability = value;
    }

    public String getReligion() {
        return religion;
    }

    public void setReligion(String value) {
        this.religion = value;
    }

    public String getPartnerReligion() {
        return partnerReligion;
    }

    public void setPartnerReligion(String value) {
        this.partnerReligion = value;
    }

    public Double getExemptionAmount() {
        return exemptionAmount;
    }

    public void setExemptionAmount(Double value) {
        this.exemptionAmount = value;
    }

    public String getExemptionUnit() {
        return exemptionUnit;
    }

    public void setExemptionUnit(String value) {
        this.exemptionUnit = value;
    }

    public String getExemptionCurrency() {
        return exemptionCurrency;
    }

    public void setExemptionCurrency(String value) {
        this.exemptionCurrency = value;
    }

    public Double getAdditionalAmount() {
        return additionalAmount;
    }

    public void setAdditionalAmount(Double value) {
        this.additionalAmount = value;
    }

    public String getAdditionalUnit() {
        return additionalUnit;
    }

    public void setAdditionalUnit(String value) {
        this.additionalUnit = value;
    }

    public String getAdditionalCurrency() {
        return additionalCurrency;
    }

    public void setAdditionalCurrency(String value) {
        this.additionalCurrency = value;
    }

    public String getTaxOfficeName() {
        return taxOfficeName;
    }

    public void setTaxOfficeName(String value) {
        this.taxOfficeName = value;
    }

    public String getTaxOfficeNumber() {
        return taxOfficeNumber;
    }

    public void setTaxOfficeNumber(String value) {
        this.taxOfficeNumber = value;
    }

    public String getHealthInsuranceName() {
        return healthInsuranceName;
    }

    public void setHealthInsuranceName(String value) {
        this.healthInsuranceName = value;
    }

    public String getHealthInsuranceCode() {
        return healthInsuranceCode;
    }

    public void setHealthInsuranceCode(String value) {
        this.healthInsuranceCode = value;
    }

    public String getHealthInsuranceType() {
        return healthInsuranceType;
    }

    public void setHealthInsuranceType(String value) {
        this.healthInsuranceType = value;
    }

    public String getSocialInsuranceNumber() {
        return socialInsuranceNumber;
    }

    public void setSocialInsuranceNumber(String value) {
        this.socialInsuranceNumber = value;
    }

    public String getProfessionStatus() {
        return professionStatus;
    }

    public void setProfessionStatus(String value) {
        this.professionStatus = value;
    }

    public String getEducationStatus() {
        return educationStatus;
    }

    public void setEducationStatus(String value) {
        this.educationStatus = value;
    }

    public String getPersonGroup() {
        return personGroup;
    }

    public void setPersonGroup(String value) {
        this.personGroup = value;
    }

    public String getJobTitleCode() {
        return jobTitleCode;
    }

    public void setJobTitleCode(String value) {
        this.jobTitleCode = value;
    }

    public String getBankCodeForDATEV() {
        return bankCodeForDATEV;
    }

    public void setBankCodeForDATEV(String value) {
        this.bankCodeForDATEV = value;
    }

    public String getDeviatingBankAccountOwner() {
        return deviatingBankAccountOwner;
    }

    public void setDeviatingBankAccountOwner(String value) {
        this.deviatingBankAccountOwner = value;
    }

    public String getSpouseFirstName() {
        return spouseFirstName;
    }

    public void setSpouseFirstName(String value) {
        this.spouseFirstName = value;
    }

    public String getSpouseSurname() {
        return spouseSurname;
    }

    public void setSpouseSurname(String value) {
        this.spouseSurname = value;
    }

    public String getExternalEmployeeNumber() {
        return externalEmployeeNumber;
    }

    public void setExternalEmployeeNumber(String value) {
        this.externalEmployeeNumber = value;
    }

    public String getBirthPlace() {
        return birthPlace;
    }

    public void setBirthPlace(String value) {
        this.birthPlace = value;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String value) {
        this.paymentMethod = value;
    }

    public Long getSTDCode() {
        return stdCode;
    }

    public void setSTDCode(Long value) {
        this.stdCode = value;
    }

    public String getCPF() {
        return cpf;
    }

    public void setCPF(String value) {
        this.cpf = value;
    }

    public String getCRCNumber() {
        return crcNumber;
    }

    public void setCRCNumber(String value) {
        this.crcNumber = value;
    }

    public String getAccountantResponsible() {
        return accountantResponsible;
    }

    public void setAccountantResponsible(String value) {
        this.accountantResponsible = value;
    }

    public String getLegalRepresentative() {
        return legalRepresentative;
    }

    public void setLegalRepresentative(String value) {
        this.legalRepresentative = value;
    }

    public String getDIRFResponsible() {
        return dirfResponsible;
    }

    public void setDIRFResponsible(String value) {
        this.dirfResponsible = value;
    }

    public String getCRCState() {
        return crcState;
    }

    public void setCRCState(String value) {
        this.crcState = value;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String value) {
        this.active = value;
    }

    public String getIDType() {
        return idType;
    }

    public void setIDType(String value) {
        this.idType = value;
    }

    public Long getBPLID() {
        return bplid;
    }

    public void setBPLID(Long value) {
        this.bplid = value;
    }

    public XMLGregorianCalendar getPassportIssueDate() {
        return passportIssueDate;
    }

    public void setPassportIssueDate(XMLGregorianCalendar value) {
        this.passportIssueDate = value;
    }

    public String getPassportIssuer() {
        return passportIssuer;
    }

    public void setPassportIssuer(String value) {
        this.passportIssuer = value;
    }

    public String getQualificationCode() {
        return qualificationCode;
    }

    public void setQualificationCode(String value) {
        this.qualificationCode = value;
    }

    public String getPRWebAccess() {
        return prWebAccess;
    }

    public void setPRWebAccess(String value) {
        this.prWebAccess = value;
    }

    public String getPreviousPRWebAccess() {
        return previousPRWebAccess;
    }

    public void setPreviousPRWebAccess(String value) {
        this.previousPRWebAccess = value;
    }

    @JsonProperty("U_U_Arp")
    public String getArp() {
        return arp;
    }

    public void setArp(String arp) {
        this.arp = arp;
    }

    @JsonProperty("U_U_Eps")
    public String getEps() {
        return eps;
    }

    public void setEps(String eps) {
        this.eps = eps;
    }

    @JsonProperty("U_U_CaComp")
    public String getCaComp() {
        return caComp;
    }

    public void setCaComp(String caComp) {
        this.caComp = caComp;
    }

    @JsonProperty("U_U_Pens")
    public String getPens() {
        return pens;
    }

    public void setPens(String pens) {
        this.pens = pens;
    }

    @JsonProperty("U_U_Cert")
    public String getCert() {
        return cert;
    }

    public void setCert(String cert) {
        this.cert = cert;
    }

    @JsonProperty("U_U_Cesantias")
    public String getCesantias() {
        return cesantias;
    }

    public void setCesantias(String cesantias) {
        this.cesantias = cesantias;
    }

    @JsonProperty("U_U_tipocontrato")
    public String getTipoContrato() {
        return tipoContrato;
    }

    public void setTipoContrato(String tipoContrato) {
        this.tipoContrato = tipoContrato;
    }

    @JsonProperty("U_codigoRevisado")
    public String getCodigoRevisado() {
        return codigoRevisado;
    }

    public void setCodigoRevisado(String codigoRevisado) {
        this.codigoRevisado = codigoRevisado;
    }

    @JsonProperty("U_CorreoCorp")
    public String getCorreoCorp() {
        return correoCorp;
    }

    public void setCorreoCorp(String correoCorp) {
        this.correoCorp = correoCorp;
    }

    @JsonProperty("U_UltCursoAltura")
    public XMLGregorianCalendar getUltCursoAltura() {
        return ultCursoAltura;
    }

    public void setUltCursoAltura(XMLGregorianCalendar ultCursoAltura) {
        this.ultCursoAltura = ultCursoAltura;
    }

    public Date getFechaCursoAlturas() {
        return fechaCursoAlturas;
    }

    public void setFechaCursoAlturas(Date fechaCursoAlturas) {
        this.fechaCursoAlturas = fechaCursoAlturas;
    }

    @JsonProperty("U_MotivoCursoAlt")
    public String getMotivoCursoAlt() {
        return motivoCursoAlt;
    }

    public void setMotivoCursoAlt(String motivoCursoAlt) {
        this.motivoCursoAlt = motivoCursoAlt;
    }

    public EmployeeInfo.EmployeeAbsenceInfoLines getEmployeeAbsenceInfoLines() {
        return employeeAbsenceInfoLines;
    }

    public void setEmployeeAbsenceInfoLines(EmployeeInfo.EmployeeAbsenceInfoLines value) {
        this.employeeAbsenceInfoLines = value;
    }

    public EmployeeInfo.EmployeeEducationInfoLines getEmployeeEducationInfoLines() {
        return employeeEducationInfoLines;
    }

    public void setEmployeeEducationInfoLines(EmployeeInfo.EmployeeEducationInfoLines value) {
        this.employeeEducationInfoLines = value;
    }

    public EmployeeInfo.EmployeeReviewsInfoLines getEmployeeReviewsInfoLines() {
        return employeeReviewsInfoLines;
    }

    public void setEmployeeReviewsInfoLines(EmployeeInfo.EmployeeReviewsInfoLines value) {
        this.employeeReviewsInfoLines = value;
    }

    public EmployeeInfo.EmployeePreviousEmpoymentInfoLines getEmployeePreviousEmpoymentInfoLines() {
        return employeePreviousEmpoymentInfoLines;
    }

    public void setEmployeePreviousEmpoymentInfoLines(EmployeeInfo.EmployeePreviousEmpoymentInfoLines value) {
        this.employeePreviousEmpoymentInfoLines = value;
    }

    public EmployeeInfo.EmployeeRolesInfoLines getEmployeeRolesInfoLines() {
        return employeeRolesInfoLines;
    }

    public void setEmployeeRolesInfoLines(EmployeeInfo.EmployeeRolesInfoLines value) {
        this.employeeRolesInfoLines = value;
    }

    public EmployeeInfo.EmployeeSavingsPaymentInfoLines getEmployeeSavingsPaymentInfoLines() {
        return employeeSavingsPaymentInfoLines;
    }

    public void setEmployeeSavingsPaymentInfoLines(EmployeeInfo.EmployeeSavingsPaymentInfoLines value) {
        this.employeeSavingsPaymentInfoLines = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeeAbsenceInfo"
    })
    public static class EmployeeAbsenceInfoLines {

        @XmlElement(name = "EmployeeAbsenceInfo")
        protected List<EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo> employeeAbsenceInfo;
        private List<EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo> ausentimosVisibles;

        public List<EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo> getEmployeeAbsenceInfo() {
            if (employeeAbsenceInfo == null) {
                employeeAbsenceInfo = new ArrayList<EmployeeInfo.EmployeeAbsenceInfoLines.EmployeeAbsenceInfo>();
            }
            return this.employeeAbsenceInfo;
        }

        public List<EmployeeAbsenceInfo> getAusentimosVisibles() {
            return ausentimosVisibles;
        }

        public void setAusentimosVisibles(List<EmployeeAbsenceInfo> ausentimosVisibles) {
            this.ausentimosVisibles = ausentimosVisibles;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeeAbsenceInfo {

            @XmlElement(name = "EmployeeID")
            protected Long employeeID;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "FromDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fromDate;
            private Date fechaInicio;
            @XmlElement(name = "ToDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar toDate;
            private Date fechaFin;
            @XmlElement(name = "Reason")
            protected String reason;
            @XmlElement(name = "ApprovedBy")
            protected String approvedBy;
            @XmlElement(name = "ConfirmerNumber")
            protected Long confirmerNumber;
            @XmlElement(name = "U_type")
            protected String type;
            @XmlElement(name = "U_horas")
            protected String horas;
            @XmlElement(name = "U_minutos")
            protected String minutos;
            @XmlElement(name = "U_dias")
            protected String dias;

            public Long getEmployeeID() {
                return employeeID;
            }

            public void setEmployeeID(Long value) {
                this.employeeID = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public XMLGregorianCalendar getFromDate() {
                return fromDate;
            }

            public void setFromDate(XMLGregorianCalendar value) {
                this.fromDate = value;
            }

            public Date getFechaInicio() {
                return fechaInicio;
            }

            public void setFechaInicio(Date fechaInicio) {
                this.fechaInicio = fechaInicio;
            }

            public XMLGregorianCalendar getToDate() {
                return toDate;
            }

            public void setToDate(XMLGregorianCalendar value) {
                this.toDate = value;
            }

            public Date getFechaFin() {
                return fechaFin;
            }

            public void setFechaFin(Date fechaFin) {
                this.fechaFin = fechaFin;
            }

            public String getReason() {
                return reason;
            }

            public void setReason(String value) {
                this.reason = value;
            }

            public String getApprovedBy() {
                return approvedBy;
            }

            public void setApprovedBy(String value) {
                this.approvedBy = value;
            }

            public Long getConfirmerNumber() {
                return confirmerNumber;
            }

            public void setConfirmerNumber(Long value) {
                this.confirmerNumber = value;
            }

            @JsonProperty("U_type")
            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            @JsonProperty("U_horas")
            public String getHoras() {
                return horas;
            }

            public void setHoras(String horas) {
                this.horas = horas;
            }

            @JsonProperty("U_minutos")
            public String getMinutos() {
                return minutos;
            }

            public void setMinutos(String minutos) {
                this.minutos = minutos;
            }

            @JsonProperty("U_dias")
            public String getDias() {
                return dias;
            }

            public void setDias(String dias) {
                this.dias = dias;
            }

            @Override
            public String toString() {
                return "EmployeeAbsenceInfo{" + "employeeID=" + employeeID + ", lineNum=" + lineNum + ", fromDate=" + fromDate + ", toDate=" + toDate + ", reason=" + reason
                        + ", approvedBy=" + approvedBy + ", confirmerNumber=" + confirmerNumber + ", type=" + type + ", horas=" + horas + ", minutos=" + minutos + ", dias=" + dias + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeeAbsenceInfoLines{" + "employeeAbsenceInfo=" + employeeAbsenceInfo + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeeEducationInfo"
    })
    public static class EmployeeEducationInfoLines {

        @XmlElement(name = "EmployeeEducationInfo")
        protected List<EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo> employeeEducationInfo;
        private List<EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo> estudiosVisibles;

        public List<EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo> getEmployeeEducationInfo() {
            if (employeeEducationInfo == null) {
                employeeEducationInfo = new ArrayList<EmployeeInfo.EmployeeEducationInfoLines.EmployeeEducationInfo>();
            }
            return this.employeeEducationInfo;
        }

        public List<EmployeeEducationInfo> getEstudiosVisibles() {
            return estudiosVisibles;
        }

        public void setEstudiosVisibles(List<EmployeeEducationInfo> estudiosVisibles) {
            this.estudiosVisibles = estudiosVisibles;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeeEducationInfo {

            @XmlElement(name = "EmployeeNo")
            protected Long employeeNo;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "FromDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fromDate;
            private Date fechaInicio;
            @XmlElement(name = "ToDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar toDate;
            private Date fechaFin;
            @XmlElement(name = "EducationType")
            protected Long educationType;
            @XmlElement(name = "Institute")
            protected String institute;
            @XmlElement(name = "Major")
            protected String major;
            @XmlElement(name = "Diploma")
            protected String diploma;

            public Long getEmployeeNo() {
                return employeeNo;
            }

            public void setEmployeeNo(Long value) {
                this.employeeNo = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public XMLGregorianCalendar getFromDate() {
                return fromDate;
            }

            public void setFromDate(XMLGregorianCalendar value) {
                this.fromDate = value;
            }

            public Date getFechaInicio() {
                return fechaInicio;
            }

            public void setFechaInicio(Date fechaInicio) {
                this.fechaInicio = fechaInicio;
            }

            public XMLGregorianCalendar getToDate() {
                return toDate;
            }

            public void setToDate(XMLGregorianCalendar value) {
                this.toDate = value;
            }

            public Date getFechaFin() {
                return fechaFin;
            }

            public void setFechaFin(Date fechaFin) {
                this.fechaFin = fechaFin;
            }

            public Long getEducationType() {
                return educationType;
            }

            public void setEducationType(Long value) {
                this.educationType = value;
            }

            public String getInstitute() {
                return institute;
            }

            public void setInstitute(String value) {
                this.institute = value;
            }

            public String getMajor() {
                return major;
            }

            public void setMajor(String value) {
                this.major = value;
            }

            public String getDiploma() {
                return diploma;
            }

            public void setDiploma(String value) {
                this.diploma = value;
            }

            @Override
            public String toString() {
                return "EmployeeEducationInfo{" + "employeeNo=" + employeeNo + ", lineNum=" + lineNum + ", fromDate=" + fromDate + ", toDate=" + toDate
                        + ", educationType=" + educationType + ", institute=" + institute + ", major=" + major + ", diploma=" + diploma + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeeEducationInfoLines{" + "employeeEducationInfo=" + employeeEducationInfo + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeePreviousEmpoymentInfo"
    })
    public static class EmployeePreviousEmpoymentInfoLines {

        @XmlElement(name = "EmployeePreviousEmpoymentInfo")
        protected List<EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo> employeePreviousEmpoymentInfo;
        private List<EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo> empleadoAnterioresVisibles;

        public List<EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo> getEmployeePreviousEmpoymentInfo() {
            if (employeePreviousEmpoymentInfo == null) {
                employeePreviousEmpoymentInfo = new ArrayList<EmployeeInfo.EmployeePreviousEmpoymentInfoLines.EmployeePreviousEmpoymentInfo>();
            }
            return this.employeePreviousEmpoymentInfo;
        }

        public List<EmployeePreviousEmpoymentInfo> getEmpleadoAnterioresVisibles() {
            return empleadoAnterioresVisibles;
        }

        public void setEmpleadoAnterioresVisibles(List<EmployeePreviousEmpoymentInfo> empleadoAnterioresVisibles) {
            this.empleadoAnterioresVisibles = empleadoAnterioresVisibles;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeePreviousEmpoymentInfo {

            @XmlElement(name = "EmployeeNo")
            protected Long employeeNo;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "FromDtae")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar fromDtae;
            private Date fechaInicio;
            @XmlElement(name = "ToDate")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar toDate;
            private Date fechaFin;
            @XmlElement(name = "Employer")
            protected String employer;
            @XmlElement(name = "Position")
            protected String position;
            @XmlElement(name = "Remarks")
            protected String remarks;

            public Long getEmployeeNo() {
                return employeeNo;
            }

            public void setEmployeeNo(Long value) {
                this.employeeNo = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public XMLGregorianCalendar getFromDtae() {
                return fromDtae;
            }

            public void setFromDtae(XMLGregorianCalendar value) {
                this.fromDtae = value;
            }

            public Date getFechaInicio() {
                return fechaInicio;
            }

            public void setFechaInicio(Date fechaInicio) {
                this.fechaInicio = fechaInicio;
            }

            public XMLGregorianCalendar getToDate() {
                return toDate;
            }

            public void setToDate(XMLGregorianCalendar value) {
                this.toDate = value;
            }

            public Date getFechaFin() {
                return fechaFin;
            }

            public void setFechaFin(Date fechaFin) {
                this.fechaFin = fechaFin;
            }

            public String getEmployer() {
                return employer;
            }

            public void setEmployer(String value) {
                this.employer = value;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String value) {
                this.position = value;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String value) {
                this.remarks = value;
            }

            @Override
            public String toString() {
                return "EmployeePreviousEmpoymentInfo{" + "employeeNo=" + employeeNo + ", lineNum=" + lineNum + ", fromDtae=" + fromDtae + ", toDate=" + toDate
                        + ", employer=" + employer + ", position=" + position + ", remarks=" + remarks + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeePreviousEmpoymentInfoLines{" + "employeePreviousEmpoymentInfo=" + employeePreviousEmpoymentInfo + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeeReviewsInfo"
    })
    public static class EmployeeReviewsInfoLines {

        @XmlElement(name = "EmployeeReviewsInfo")
        protected List<EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo> employeeReviewsInfo;
        private List<EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo> valoracionesVisibles;

        public List<EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo> getEmployeeReviewsInfo() {
            if (employeeReviewsInfo == null) {
                employeeReviewsInfo = new ArrayList<EmployeeInfo.EmployeeReviewsInfoLines.EmployeeReviewsInfo>();
            }
            return this.employeeReviewsInfo;
        }

        public List<EmployeeReviewsInfo> getValoracionesVisibles() {
            return valoracionesVisibles;
        }

        public void setValoracionesVisibles(List<EmployeeReviewsInfo> valoracionesVisibles) {
            this.valoracionesVisibles = valoracionesVisibles;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeeReviewsInfo {

            @XmlElement(name = "EmployeeNo")
            protected Long employeeNo;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "Date")
            @XmlSchemaType(name = "date")
            protected XMLGregorianCalendar date;
            private Date fecha;
            @XmlElement(name = "ReviewDescription")
            protected String reviewDescription;
            @XmlElement(name = "Manager")
            protected Long manager;
            @XmlElement(name = "Grade")
            protected String grade;
            @XmlElement(name = "Remarks")
            protected String remarks;

            public Long getEmployeeNo() {
                return employeeNo;
            }

            public void setEmployeeNo(Long value) {
                this.employeeNo = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public XMLGregorianCalendar getDate() {
                return date;
            }

            public void setDate(XMLGregorianCalendar value) {
                this.date = value;
            }

            public Date getFecha() {
                return fecha;
            }

            public void setFecha(Date fecha) {
                this.fecha = fecha;
            }

            public String getReviewDescription() {
                return reviewDescription;
            }

            public void setReviewDescription(String value) {
                this.reviewDescription = value;
            }

            public Long getManager() {
                return manager;
            }

            public void setManager(Long value) {
                this.manager = value;
            }

            public String getGrade() {
                return grade;
            }

            public void setGrade(String value) {
                this.grade = value;
            }

            public String getRemarks() {
                return remarks;
            }

            public void setRemarks(String value) {
                this.remarks = value;
            }

            @Override
            public String toString() {
                return "EmployeeReviewsInfo{" + "employeeNo=" + employeeNo + ", lineNum=" + lineNum + ", date=" + date + ", reviewDescription=" + reviewDescription
                        + ", manager=" + manager + ", grade=" + grade + ", remarks=" + remarks + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeeReviewsInfoLines{" + "employeeReviewsInfo=" + employeeReviewsInfo + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeeRolesInfo"
    })
    public static class EmployeeRolesInfoLines {

        @XmlElement(name = "EmployeeRolesInfo")
        protected List<EmployeeInfo.EmployeeRolesInfoLines.EmployeeRolesInfo> employeeRolesInfo;

        public List<EmployeeInfo.EmployeeRolesInfoLines.EmployeeRolesInfo> getEmployeeRolesInfo() {
            if (employeeRolesInfo == null) {
                employeeRolesInfo = new ArrayList<EmployeeInfo.EmployeeRolesInfoLines.EmployeeRolesInfo>();
            }
            return this.employeeRolesInfo;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeeRolesInfo {

            @XmlElement(name = "EmployeeID")
            protected Long employeeID;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "RoleID")
            protected Long roleID;

            public Long getEmployeeID() {
                return employeeID;
            }

            public void setEmployeeID(Long value) {
                this.employeeID = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public Long getRoleID() {
                return roleID;
            }

            public void setRoleID(Long value) {
                this.roleID = value;
            }

            @Override
            public String toString() {
                return "EmployeeRolesInfo{" + "employeeID=" + employeeID + ", lineNum=" + lineNum + ", roleID=" + roleID + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeeRolesInfoLines{" + "employeeRolesInfo=" + employeeRolesInfo + '}';
        }
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "employeeSavingsPaymentInfo"
    })
    public static class EmployeeSavingsPaymentInfoLines {

        @XmlElement(name = "EmployeeSavingsPaymentInfo")
        protected List<EmployeeInfo.EmployeeSavingsPaymentInfoLines.EmployeeSavingsPaymentInfo> employeeSavingsPaymentInfo;

        public List<EmployeeInfo.EmployeeSavingsPaymentInfoLines.EmployeeSavingsPaymentInfo> getEmployeeSavingsPaymentInfo() {
            if (employeeSavingsPaymentInfo == null) {
                employeeSavingsPaymentInfo = new ArrayList<EmployeeInfo.EmployeeSavingsPaymentInfoLines.EmployeeSavingsPaymentInfo>();
            }
            return this.employeeSavingsPaymentInfo;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {})
        public static class EmployeeSavingsPaymentInfo {

            @XmlElement(name = "EmployeeID")
            protected Long employeeID;
            @XmlElement(name = "LineNum")
            protected Long lineNum;
            @XmlElement(name = "ContractName")
            protected String contractName;
            @XmlElement(name = "PaymentNotes")
            protected String paymentNotes;
            @XmlElement(name = "AN")
            protected Double an;
            @XmlElement(name = "ANcurrency")
            protected String aNcurrency;
            @XmlElement(name = "AG")
            protected Double ag;
            @XmlElement(name = "AGcurrency")
            protected String aGcurrency;
            @XmlElement(name = "BankName")
            protected String bankName;
            @XmlElement(name = "BankCode")
            protected String bankCode;
            @XmlElement(name = "BankAccount")
            protected String bankAccount;
            @XmlElement(name = "Sequence")
            protected String sequence;

            public Long getEmployeeID() {
                return employeeID;
            }

            public void setEmployeeID(Long value) {
                this.employeeID = value;
            }

            public Long getLineNum() {
                return lineNum;
            }

            public void setLineNum(Long value) {
                this.lineNum = value;
            }

            public String getContractName() {
                return contractName;
            }

            public void setContractName(String value) {
                this.contractName = value;
            }

            public String getPaymentNotes() {
                return paymentNotes;
            }

            public void setPaymentNotes(String value) {
                this.paymentNotes = value;
            }

            public Double getAN() {
                return an;
            }

            public void setAN(Double value) {
                this.an = value;
            }

            public String getANcurrency() {
                return aNcurrency;
            }

            public void setANcurrency(String value) {
                this.aNcurrency = value;
            }

            public Double getAG() {
                return ag;
            }

            public void setAG(Double value) {
                this.ag = value;
            }

            public String getAGcurrency() {
                return aGcurrency;
            }

            public void setAGcurrency(String value) {
                this.aGcurrency = value;
            }

            public String getBankName() {
                return bankName;
            }

            public void setBankName(String value) {
                this.bankName = value;
            }

            public String getBankCode() {
                return bankCode;
            }

            public void setBankCode(String value) {
                this.bankCode = value;
            }

            public String getBankAccount() {
                return bankAccount;
            }

            public void setBankAccount(String value) {
                this.bankAccount = value;
            }

            public String getSequence() {
                return sequence;
            }

            public void setSequence(String value) {
                this.sequence = value;
            }

            @Override
            public String toString() {
                return "EmployeeSavingsPaymentInfo{" + "employeeID=" + employeeID + ", lineNum=" + lineNum + ", contractName=" + contractName + ", paymentNotes=" + paymentNotes
                        + ", an=" + an + ", aNcurrency=" + aNcurrency + ", ag=" + ag + ", aGcurrency=" + aGcurrency + ", bankName=" + bankName + ", bankCode=" + bankCode
                        + ", bankAccount=" + bankAccount + ", sequence=" + sequence + '}';
            }
        }

        @Override
        public String toString() {
            return "EmployeeSavingsPaymentInfoLines{" + "employeeSavingsPaymentInfo=" + employeeSavingsPaymentInfo + '}';
        }
    }

    @Override
    public String toString() {
        return "EmployeeInfo{" + "employeeID=" + employeeID + ", lastName=" + lastName + ", firstName=" + firstName + ", middleName=" + middleName + ", gender=" + gender
                + ", jobTitle=" + jobTitle + ", employeeType=" + employeeType + ", department=" + department + ", branch=" + branch + ", workStreet=" + workStreet
                + ", workBlock=" + workBlock + ", workZipCode=" + workZipCode + ", workCity=" + workCity + ", workCounty=" + workCounty + ", workCountryCode=" + workCountryCode
                + ", workStateCode=" + workStateCode + ", manager=" + manager + ", applicationUserID=" + applicationUserID + ", salesPersonCode=" + salesPersonCode
                + ", officePhone=" + officePhone + ", officeExtension=" + officeExtension + ", mobilePhone=" + mobilePhone + ", pager=" + pager + ", homePhone=" + homePhone
                + ", fax=" + fax + ", eMail=" + eMail + ", startDate=" + startDate + ", statusCode=" + statusCode + ", salary=" + salary + ", salaryUnit=" + salaryUnit
                + ", employeeCosts=" + employeeCosts + ", employeeCostUnit=" + employeeCostUnit + ", terminationDate=" + terminationDate + ", treminationReason=" + treminationReason
                + ", bankCode=" + bankCode + ", bankBranch=" + bankBranch + ", bankBranchNum=" + bankBranchNum + ", bankAccount=" + bankAccount + ", homeStreet=" + homeStreet
                + ", homeBlock=" + homeBlock + ", homeZipCode=" + homeZipCode + ", homeCity=" + homeCity + ", homeCounty=" + homeCounty + ", homeCountry=" + homeCountry
                + ", homeState=" + homeState + ", homeStreetNumber=" + homeStreetNumber + ", dateOfBirth=" + dateOfBirth + ", countryOfBirth=" + countryOfBirth + ", martialStatus=" + martialStatus
                + ", numOfChildren=" + numOfChildren + ", idNumber=" + idNumber + ", citizenshipCountryCode=" + citizenshipCountryCode + ", passportNumber=" + passportNumber
                + ", passportExpirationDate=" + passportExpirationDate + ", picture=" + picture + ", remarks=" + remarks + ", salaryCurrency=" + salaryCurrency
                + ", employeeCostsCurrency=" + employeeCostsCurrency + ", workBuildingFloorRoom=" + workBuildingFloorRoom + ", homeBuildingFloorRoom=" + homeBuildingFloorRoom
                + ", position=" + position + ", attachmentEntry=" + attachmentEntry + ", costCenterCode=" + costCenterCode + ", companyNumber=" + companyNumber
                + ", vacationPreviousYear=" + vacationPreviousYear + ", vacationCurrentYear=" + vacationCurrentYear + ", municipalityKey=" + municipalityKey + ", taxClass=" + taxClass
                + ", incomeTaxLiability=" + incomeTaxLiability + ", religion=" + religion + ", partnerReligion=" + partnerReligion + ", exemptionAmount=" + exemptionAmount
                + ", exemptionUnit=" + exemptionUnit + ", exemptionCurrency=" + exemptionCurrency + ", additionalAmount=" + additionalAmount + ", additionalUnit=" + additionalUnit
                + ", additionalCurrency=" + additionalCurrency + ", taxOfficeName=" + taxOfficeName + ", taxOfficeNumber=" + taxOfficeNumber + ", healthInsuranceName=" + healthInsuranceName
                + ", healthInsuranceCode=" + healthInsuranceCode + ", healthInsuranceType=" + healthInsuranceType + ", socialInsuranceNumber=" + socialInsuranceNumber
                + ", professionStatus=" + professionStatus + ", educationStatus=" + educationStatus + ", personGroup=" + personGroup + ", jobTitleCode=" + jobTitleCode
                + ", bankCodeForDATEV=" + bankCodeForDATEV + ", deviatingBankAccountOwner=" + deviatingBankAccountOwner + ", spouseFirstName=" + spouseFirstName
                + ", spouseSurname=" + spouseSurname + ", externalEmployeeNumber=" + externalEmployeeNumber + ", birthPlace=" + birthPlace + ", paymentMethod=" + paymentMethod
                + ", stdCode=" + stdCode + ", cpf=" + cpf + ", crcNumber=" + crcNumber + ", accountantResponsible=" + accountantResponsible + ", legalRepresentative=" + legalRepresentative
                + ", dirfResponsible=" + dirfResponsible + ", crcState=" + crcState + ", active=" + active + ", idType=" + idType + ", bplid=" + bplid
                + ", passportIssueDate=" + passportIssueDate + ", passportIssuer=" + passportIssuer + ", qualificationCode=" + qualificationCode + ", prWebAccess=" + prWebAccess
                + ", previousPRWebAccess=" + previousPRWebAccess + ", arp=" + arp + ", eps=" + eps + ", caComp=" + caComp + ", pens=" + pens + ", cert=" + cert + ", cesantias=" + cesantias
                + ", tipoContrato=" + tipoContrato + ", codigoRevisado=" + codigoRevisado + ", correoCorp=" + correoCorp + ", ultCursoAltura=" + ultCursoAltura
                + ", motivoCursoAlt=" + motivoCursoAlt + ", employeeAbsenceInfoLines=" + employeeAbsenceInfoLines + ", employeeEducationInfoLines=" + employeeEducationInfoLines
                + ", employeeReviewsInfoLines=" + employeeReviewsInfoLines + ", employeePreviousEmpoymentInfoLines=" + employeePreviousEmpoymentInfoLines
                + ", employeeRolesInfoLines=" + employeeRolesInfoLines + ", employeeSavingsPaymentInfoLines=" + employeeSavingsPaymentInfoLines + '}';
    }
}
