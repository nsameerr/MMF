/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.InvestorNomineeDetailsTb;
import com.gtl.mmf.entity.MandateFormTb;
import com.gtl.mmf.entity.MasterAdvisorQualificationTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.service.util.IConstants;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class UserDetailsVO implements IConstants {

    private static final String MINUS_ONE = "-1";
    private static final Integer MINUS_ONE_INT = -1;
    private String userType;
    boolean adminUser = false;
    private String password;
    private String cin;
    private String dateJoining;
    private String status;
    private String panNo;
    private String homeAddress;
    private String homeAddress_Line1;
    private String homeAddress_Street;
    private String homeAddress_Landmark;
//    private String homeAddress_Zipcode;
    private String homeCity;
    private String homePincode;
    private String homeCountry = IN;
    private String homeTelephone;
    private String risd;
    private String rstd;
    private String homeMobile;

    String address2_Line1;
    String address2_Street;
    String address2_Landmark;
//    String address2_Zipcode;

    private String bankAddress;
    private String bankAddress_Line1;
    private String bankAddress_Landmark;

    private String officeOrganization;
    private String officeJobTitle;
    private String officeAddress;
    private String officeCity;
    private String officePincode;
    private String officeCountry = IN;
    private String officeTelephone;
    private String oisd;
    private String ostd;
    private String officeMobile;
    private String tradingBrokerName;
    private String tradingBrokerAccNo;
    private String tradingBank;
    private String tradingIFSCNo;
    private String tradingAccNo;
    private Double tradingAmount;
    private int userId;
    private String docStatus;
    private int initLogin;
    private boolean loggedIn;
    private Double amount;
    private boolean exisitngUser;
    private int userStatus;
    private MasterApplicantTb masterApplicant;
    private String previousStatus;
    private String styleClass;
    private Double investorTotalFund;
    private String sebiCertificationNo;
    Date dob;
    private Boolean haveRealtions;
    private String mandateStatus;
    private String mandatestyleClass;
    private boolean linkedInConnected;
    private String[] selectedSegments;

    private MasterAdvisorQualificationTb qualificationTb;
    private InvestorNomineeDetailsTb nomineeDetails;
    private MandateFormTb mandateData;
    private CustomerPortfolioTb customerportfoliotb;

   

    public CustomerPortfolioSecuritiesTb getCustomerportfoliosecurity() {
        return customerportfoliosecurity;
    }

    public void setCustomerportfoliosecurity(CustomerPortfolioSecuritiesTb customerportfoliosecurity) {
        this.customerportfoliosecurity = customerportfoliosecurity;
    }
    private CustomerPortfolioSecuritiesTb customerportfoliosecurity;
    private String nomineCountry;
    String minorCountry;
    String address1_Line1_Nominee;
    String address1_Street_Nominee;
    String address1_Landmark_Nominee;
    String address1_Zipcode_Nominee;
    String address1_Line1_Minor;
    String address1_Street_Minor;
    String address1_Landmark_Minor;
    String address1_Zipcode_Minor;
    String re_accountNumber;
    String filePath;

    String smsFacility;
    String pep;
    String rpep;
    String instruction1;
    String instruction2;
    String instruction3;
    String instruction4;
    String instruction5;
    private String smsFacilitySecondHolder;
    private String politicalyExposedSecondHolder;
    private String politicalyRelatedSecondHolder;
    private String facilityInternetTrading;

    private boolean onlineDetailsSubmitted = false;
    private boolean sebiCertificateValidated = false;
    private boolean verificationCcompleted = false;
    private boolean accountActivated = false;

    private boolean form_couriered_Client = false;
    private boolean form_received_client = false;
    private boolean form_Validated = false;
    private boolean accepted = false;
    private boolean rejected = false;
    private String rejection_Reason;
    private boolean rejection_Resolved = false;
    private boolean uCC_created = false;

    String nfisd;
    String nfstd;

    String noisd;
    String nostd;

    String nrisd;
    String nrstd;

    String mfisd;
    String mfstd;

    String moisd;
    String mostd;

    String mrisd;
    String mrstd;

    String mandateisd;
    String mandatestd;
    private String mandatePhone;

    private String telOfficeNominee;
    private String telResidenceNominee;
    private String faxNominee;

    private String telOfficeNomineeMinor;
    private String telResidenceNomineeMinor;
    private String faxNomineeMinor;
    private String omsLoginId;
    boolean downloadSebi;
    boolean downloadPan;
    boolean downloadCorrespondence;
    boolean downloadPermanentAddress;
    boolean downloadDocEvidence;
    
    private Float ledgerValue;
    private Float portfolioValue;
    private Double HoldingValue;
    private Double unSettledValue;
    private String mandateFailureReason;

    public MasterAdvisorTb toMasterAdvisorTb() {
        MasterAdvisorTb masterAdvisor = new MasterAdvisorTb();
        masterAdvisor.setFirstName(this.masterApplicant.getName());
        masterAdvisor.setMiddleName(this.masterApplicant.getMiddleName());
        masterAdvisor.setLastName(this.masterApplicant.getLastName());
        masterAdvisor.setEmail(this.masterApplicant.getEmail());
        masterAdvisor.setPanNo(panNo);
        masterAdvisor.setHomeAddress1(this.masterApplicant.getAddress1());
        masterAdvisor.setHomeAddressCity(this.masterApplicant.getAddressCity());
        masterAdvisor.setHomeAddressPincode(this.masterApplicant.getAddressPincode());
        masterAdvisor.setHomeAddressCountry(this.masterApplicant.getAddressCountry());
        masterAdvisor.setHomeTelephone(homeTelephone);
        masterAdvisor.setMobile(this.masterApplicant.getMobile());
        masterAdvisor.setWorkOrganization(this.masterApplicant.getWorkOrganization());
        masterAdvisor.setJobTitle(this.masterApplicant.getJobTitle());
        masterAdvisor.setWorkAddress1(this.masterApplicant.getAddress2());
        masterAdvisor.setWorkAddressCity(this.masterApplicant.getAddress2City());
        masterAdvisor.setWorkAddressPincode(this.masterApplicant.getAddress2Pincode());
        masterAdvisor.setWorkAddressCountry(this.masterApplicant.getAddress2Country());
        masterAdvisor.setWorkTelephone(officeTelephone);
        masterAdvisor.setWorkMobile(masterApplicant.getAddress2Mobil());
        masterAdvisor.setIfscCode(this.masterApplicant.getIfcsNumber());
        masterAdvisor.setBankAccountNo(this.masterApplicant.getAccountNumber());
        masterAdvisor.setRegistrationId(this.masterApplicant.getRegistrationId());
        masterAdvisor.setDocumentStatus(docStatus);

        masterAdvisor.setMasterAdvisorQualificationTb(qualificationTb);
//        masterAdvisor.getMasterAdvisorQualificationTb().setPrimaryQualificationDegree(qualificationPrimary);
//        masterAdvisor.getMasterAdvisorQualificationTb().setPrimaryQualificationDegree(qualificationPrimary);
//        masterAdvisor.getMasterAdvisorQualificationTb().setPrimaryQualificationId(qualiPrimaryId);
//        masterAdvisor.getMasterAdvisorQualificationTb().setPrimaryQualificationInstitute(qualiPrimaryInstitute);
//        masterAdvisor.getMasterAdvisorQualificationTb().setPrimaryQualificationYear(qualiPrimaryYear);
//        masterAdvisor.getMasterAdvisorQualificationTb().setSecondaryQualificationDegree(qualificationSecondary);
//        masterAdvisor.getMasterAdvisorQualificationTb().setSecondaryQualificationId(qualiSecondaryId);
//        masterAdvisor.getMasterAdvisorQualificationTb().setSecondaryQualificationInstitute(qualiSecondaryInstitute);
//        masterAdvisor.getMasterAdvisorQualificationTb().setSecondaryQualificationYear(qualiSecondaryYear);
//        masterAdvisor.getMasterAdvisorQualificationTb().setTertiaryQualificationDegree(qualificationTertiary);
//        masterAdvisor.getMasterAdvisorQualificationTb().setTertiaryQualificationId(qualiTertiaryId);
//        masterAdvisor.getMasterAdvisorQualificationTb().setTertiaryQualificationInstitute(qualiTertiaryInstitute);
//        masterAdvisor.getMasterAdvisorQualificationTb().setTertiaryQualificationYear(qualiTertiaryYear);
        masterAdvisor.setAdvisorId(userId);

        masterAdvisor.setAmount(tradingAmount);
        masterAdvisor.setBankName(masterApplicant.getBankName());
        masterAdvisor.setBrokerAccountNo(tradingBrokerAccNo);
        masterAdvisor.setHomeAddress2(masterApplicant.getAddress2());
        masterAdvisor.setPassword(password);
        masterAdvisor.setInitLogin(initLogin);
        masterAdvisor.setLoggedIn(loggedIn);
        masterAdvisor.setMasterApplicantTb(masterApplicant);
        masterAdvisor.setWorkMobile(officeMobile);
        masterAdvisor.setDob(this.masterApplicant.getDob());
        masterAdvisor.setSebiCertificationNo(this.masterApplicant.getSebiRegno());
        masterAdvisor.setMasterAdvisorQualificationTb(qualificationTb);
        masterAdvisor.setOnlineDetailsubmites(onlineDetailsSubmitted);
        masterAdvisor.setSebiCertificateValidated(sebiCertificateValidated);
        masterAdvisor.setVerificationCompleted(verificationCcompleted);
        masterAdvisor.setAccountActivated(accountActivated);
        return masterAdvisor;
    }

    public MasterCustomerTb toMasterCustomerTb() {
        MasterCustomerTb masterCustomer = new MasterCustomerTb();
        masterCustomer.setFirstName(this.masterApplicant.getName());
        masterCustomer.setMiddleName(this.masterApplicant.getMiddleName());
        masterCustomer.setLastName(this.masterApplicant.getLastName());
        masterCustomer.setEmail(this.masterApplicant.getEmail());
        masterCustomer.setPanNo(panNo);
        masterCustomer.setHomeAddress1(this.masterApplicant.getAddress1());
        masterCustomer.setHomeAddressCity(this.masterApplicant.getAddressCity());
        masterCustomer.setHomeAddressPincode(this.masterApplicant.getAddressPincode());
        masterCustomer.setHomeAddressCountry(this.masterApplicant.getAddressCountry());
        masterCustomer.setHomeTelephone(homeTelephone);
        masterCustomer.setMobile(this.masterApplicant.getMobile());
        masterCustomer.setWorkOrganization(this.masterApplicant.getWorkOrganization());
        masterCustomer.setJobTitle(this.masterApplicant.getJobTitle());
        masterCustomer.setWorkAddress1(officeAddress);
        masterCustomer.setWorkAddressCity(officeCity);
        masterCustomer.setWorkAddressPincode(officePincode);
        masterCustomer.setWorkAddressCountry(officeCountry);
        masterCustomer.setWorkTelephone(officeTelephone);
        masterCustomer.setBrokerName(tradingBrokerName);
        masterCustomer.setBrokerAccountNo(tradingBrokerAccNo);
        masterCustomer.setBankName(tradingBank);
        masterCustomer.setIfscCode(tradingIFSCNo);
        masterCustomer.setBankAccountNo(tradingAccNo);
        masterCustomer.setRegistrationId(this.masterApplicant.getRegistrationId());
        masterCustomer.setCustomerId(userId);
        masterCustomer.setDocumentStatus(docStatus);
        masterCustomer.setPassword(password);
        masterCustomer.setInitLogin(initLogin);
        masterCustomer.setLoggedIn(loggedIn);
        masterCustomer.setMasterApplicantTb(masterApplicant);
        masterCustomer.setWorkMobile(officeMobile);
        masterCustomer.setAmount(tradingAmount);
        masterCustomer.setTotalFunds(investorTotalFund);
        masterCustomer.setDob(this.masterApplicant.getDob());
        masterCustomer.setHomeAddress2(this.masterApplicant.getAddress2());

        masterCustomer.setOnlineDetailsubmites(this.isOnlineDetailsSubmitted());
        masterCustomer.setFormReceivedClient(this.isForm_received_client());
        masterCustomer.setFormCourieredClient(this.isForm_couriered_Client());
        masterCustomer.setFormValidated(this.isForm_Validated());
        masterCustomer.setRejected(this.isRejected());
        masterCustomer.setAccepted(this.accepted);
        masterCustomer.setRejectionResolved(this.isRejection_Resolved());
        masterCustomer.setUccCreated(this.isuCC_created());
        masterCustomer.setRejectionReason(this.getRejection_Reason());
        masterCustomer.setAccountActivated(this.isAccountActivated());

        return masterCustomer;
    }

    public String getStatus() { // Getter
        status = status.toLowerCase();
        String statusFL = status.substring(0, 1).toUpperCase();
        status = statusFL + status.substring(1, status.length());
        return status;
    }

    public boolean isExisitngUser() {
        return exisitngUser;
    }

    public void setExisitngUser(boolean exisitngUser) {
        this.exisitngUser = exisitngUser;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOfficeMobile() {
        return officeMobile;
    }

    public void setOfficeMobile(String officeMobile) {
        this.officeMobile = officeMobile;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getDateJoining() {
        return dateJoining;
    }

    public void setDateJoining(String dateJoining) {
        this.dateJoining = dateJoining;
    }

    public String getPanNo() {
        return panNo;
    }

    public void setPanNo(String panNo) {
        this.panNo = panNo;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public void setHomeCity(String homeCity) {
        this.homeCity = homeCity;
    }

    public String getHomePincode() {
        return homePincode;
    }

    public void setHomePincode(String homePincode) {
        this.homePincode = homePincode;
    }

    public String getHomeCountry() {
        return homeCountry;
    }

    public void setHomeCountry(String homeCountry) {
        this.homeCountry = homeCountry;
    }

    public String getHomeTelephone() {
        return homeTelephone;
    }

    public void setHomeTelephone(String homeTelephone) {
        this.homeTelephone = homeTelephone;
    }

    public String getHomeMobile() {
        return homeMobile;
    }

    public void setHomeMobile(String homeMobile) {
        this.homeMobile = homeMobile;
    }

    public String getOfficeOrganization() {
        return officeOrganization;
    }

    public void setOfficeOrganization(String officeOrganization) {
        this.officeOrganization = officeOrganization;
    }

    public String getOfficeJobTitle() {
        return officeJobTitle;
    }

    public void setOfficeJobTitle(String officeJobTitle) {
        this.officeJobTitle = officeJobTitle;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getOfficeCity() {
        return officeCity;
    }

    public void setOfficeCity(String officeCity) {
        this.officeCity = officeCity;
    }

    public String getOfficePincode() {
        return officePincode;
    }

    public void setOfficePincode(String officePincode) {
        this.officePincode = officePincode;
    }

    public String getOfficeCountry() {
        return officeCountry;
    }

    public void setOfficeCountry(String officeCountry) {
        this.officeCountry = officeCountry;
    }

    public String getOfficeTelephone() {
        return officeTelephone;
    }

    public void setOfficeTelephone(String officeTelephone) {
        this.officeTelephone = officeTelephone;
    }

    public String getTradingBrokerName() {
        return tradingBrokerName;
    }

    public void setTradingBrokerName(String tradingBrokerName) {
        this.tradingBrokerName = tradingBrokerName;
    }

    public String getTradingBrokerAccNo() {
        return tradingBrokerAccNo;
    }

    public void setTradingBrokerAccNo(String tradingBrokerAccNo) {
        this.tradingBrokerAccNo = tradingBrokerAccNo;
    }

    public String getTradingBank() {
        return tradingBank;
    }

    public void setTradingBank(String tradingBank) {
        this.tradingBank = tradingBank;
    }

    public String getTradingIFSCNo() {
        return tradingIFSCNo;
    }

    public void setTradingIFSCNo(String tradingIFSCNo) {
        this.tradingIFSCNo = tradingIFSCNo;
    }

    public String getTradingAccNo() {
        return tradingAccNo;
    }

    public void setTradingAccNo(String tradingAccNo) {
        this.tradingAccNo = tradingAccNo;
    }

    public Double getTradingAmount() {
        return tradingAmount;
    }

    public void setTradingAmount(Double tradingAmount) {
        this.tradingAmount = tradingAmount;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getInitLogin() {
        return initLogin;
    }

    public void setInitLogin(int initLogin) {
        this.initLogin = initLogin;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public MasterApplicantTb getMasterApplicant() {
        return masterApplicant;
    }

    public void setMasterApplicant(MasterApplicantTb masterApplicant) {
        this.masterApplicant = masterApplicant;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getPreviousStatus() {
        return previousStatus;
    }

    public void setPreviousStatus(String previousStatus) {
        this.previousStatus = previousStatus;
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String StyleClass) {
        this.styleClass = StyleClass;
    }

    public Double getInvestorTotalFund() {
        return investorTotalFund;
    }

    public void setInvestorTotalFund(Double investorTotalFund) {
        this.investorTotalFund = investorTotalFund;
    }

    public String getSebiCertificationNo() {
        return sebiCertificationNo;
    }

    public void setSebiCertificationNo(String sebiCertificationNo) {
        this.sebiCertificationNo = sebiCertificationNo;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Boolean isHaveRealtions() {
        return haveRealtions;
    }

    public void setHaveRealtions(Boolean haveRealtions) {
        this.haveRealtions = haveRealtions;
    }

    public String getHomeAddress_Line1() {
        return homeAddress_Line1;
    }

    public void setHomeAddress_Line1(String homeAddress_Line1) {
        this.homeAddress_Line1 = homeAddress_Line1;
    }

    public String getHomeAddress_Street() {
        return homeAddress_Street;
    }

    public void setHomeAddress_Street(String homeAddress_Street) {
        this.homeAddress_Street = homeAddress_Street;
    }

    public String getHomeAddress_Landmark() {
        return homeAddress_Landmark;
    }

    public void setHomeAddress_Landmark(String homeAddress_Landmark) {
        this.homeAddress_Landmark = homeAddress_Landmark;
    }

//    public String getHomeAddress_Zipcode() {
//        return homeAddress_Zipcode;
//    }
//
//    public void setHomeAddress_Zipcode(String homeAddress_Zipcode) {
//        this.homeAddress_Zipcode = homeAddress_Zipcode;
//    }
    
    public String getAddress2_Line1() {
        return address2_Line1;
    }

    public void setAddress2_Line1(String address2_Line1) {
        this.address2_Line1 = address2_Line1;
    }

    public String getAddress2_Street() {
        return address2_Street;
    }

    public void setAddress2_Street(String address2_Street) {
        this.address2_Street = address2_Street;
    }

    public String getAddress2_Landmark() {
        return address2_Landmark;
    }

    public void setAddress2_Landmark(String address2_Landmark) {
        this.address2_Landmark = address2_Landmark;
    }

//    public String getAddress2_Zipcode() {
//        return address2_Zipcode;
//    }
//
//    public void setAddress2_Zipcode(String address2_Zipcode) {
//        this.address2_Zipcode = address2_Zipcode;
//    }
    public String getMandateStatus() {
        return mandateStatus;
    }

    public void setMandateStatus(String mandateStatus) {
        this.mandateStatus = mandateStatus;
    }

    public String getMandatestyleClass() {
        return mandatestyleClass;
    }

    public void setMandatestyleClass(String mandatestyleClass) {
        this.mandatestyleClass = mandatestyleClass;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public InvestorNomineeDetailsTb getNomineeDetails() {
        return nomineeDetails;
    }

    public void setNomineeDetails(InvestorNomineeDetailsTb nomineeDetails) {
        this.nomineeDetails = nomineeDetails;
    }

    public String getAddress1_Line1_Nominee() {
        return address1_Line1_Nominee;
    }

    public void setAddress1_Line1_Nominee(String address1_Line1_Nominee) {
        this.address1_Line1_Nominee = address1_Line1_Nominee;
    }

    public String getAddress1_Street_Nominee() {
        return address1_Street_Nominee;
    }

    public void setAddress1_Street_Nominee(String address1_Street_Nominee) {
        this.address1_Street_Nominee = address1_Street_Nominee;
    }

    public String getAddress1_Landmark_Nominee() {
        return address1_Landmark_Nominee;
    }

    public void setAddress1_Landmark_Nominee(String address1_Landmark_Nominee) {
        this.address1_Landmark_Nominee = address1_Landmark_Nominee;
    }

    public String getAddress1_Zipcode_Nominee() {
        return address1_Zipcode_Nominee;
    }

    public void setAddress1_Zipcode_Nominee(String address1_Zipcode_Nominee) {
        this.address1_Zipcode_Nominee = address1_Zipcode_Nominee;
    }

    public String getAddress1_Line1_Minor() {
        return address1_Line1_Minor;
    }

    public void setAddress1_Line1_Minor(String address1_Line1_Minor) {
        this.address1_Line1_Minor = address1_Line1_Minor;
    }

    public String getAddress1_Street_Minor() {
        return address1_Street_Minor;
    }

    public void setAddress1_Street_Minor(String address1_Street_Minor) {
        this.address1_Street_Minor = address1_Street_Minor;
    }

    public String getAddress1_Landmark_Minor() {
        return address1_Landmark_Minor;
    }

    public void setAddress1_Landmark_Minor(String address1_Landmark_Minor) {
        this.address1_Landmark_Minor = address1_Landmark_Minor;
    }

    public String getAddress1_Zipcode_Minor() {
        return address1_Zipcode_Minor;
    }

    public void setAddress1_Zipcode_Minor(String address1_Zipcode_Minor) {
        this.address1_Zipcode_Minor = address1_Zipcode_Minor;
    }

    public String[] getSelectedSegments() {
        return selectedSegments;
    }

    public void setSelectedSegments(String[] selectedSegments) {
        this.selectedSegments = selectedSegments;
    }

    public MasterAdvisorQualificationTb getQualificationTb() {
        return qualificationTb;
    }

    public void setQualificationTb(MasterAdvisorQualificationTb qualificationTb) {
        this.qualificationTb = qualificationTb;
    }

    public String getRisd() {
        return risd;
    }

    public void setRisd(String risd) {
        this.risd = risd;
    }

    public String getRstd() {
        return rstd;
    }

    public void setRstd(String rstd) {
        this.rstd = rstd;
    }

    public String getOisd() {
        return oisd;
    }

    public void setOisd(String oisd) {
        this.oisd = oisd;
    }

    public String getOstd() {
        return ostd;
    }

    public void setOstd(String ostd) {
        this.ostd = ostd;
    }

    public String getBankAddress() {
        return bankAddress;
    }

    public void setBankAddress(String bankAddress) {
        this.bankAddress = bankAddress;
    }

    public String getBankAddress_Line1() {
        return bankAddress_Line1;
    }

    public void setBankAddress_Line1(String bankAddress_Line1) {
        this.bankAddress_Line1 = bankAddress_Line1;
    }

    public String getBankAddress_Landmark() {
        return bankAddress_Landmark;
    }

    public void setBankAddress_Landmark(String bankAddress_Landmark) {
        this.bankAddress_Landmark = bankAddress_Landmark;
    }

    public String getNomineCountry() {
        return nomineCountry;
    }

    public void setNomineCountry(String nomineCountry) {
        this.nomineCountry = nomineCountry;
    }

    public String getMinorCountry() {
        return minorCountry;
    }

    public void setMinorCountry(String minorCountry) {
        this.minorCountry = minorCountry;
    }

    public String getRe_accountNumber() {
        return re_accountNumber;
    }

    public void setRe_accountNumber(String re_accountNumber) {
        this.re_accountNumber = re_accountNumber;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public boolean isOnlineDetailsSubmitted() {
        return onlineDetailsSubmitted;
    }

    public void setOnlineDetailsSubmitted(boolean onlineDetailsSubmitted) {
        this.onlineDetailsSubmitted = onlineDetailsSubmitted;
    }

    public boolean isSebiCertificateValidated() {
        return sebiCertificateValidated;
    }

    public void setSebiCertificateValidated(boolean sebiCertificateValidated) {
        this.sebiCertificateValidated = sebiCertificateValidated;
    }

    public boolean isVerificationCcompleted() {
        return verificationCcompleted;
    }

    public void setVerificationCcompleted(boolean verificationCcompleted) {
        this.verificationCcompleted = verificationCcompleted;
    }

    public boolean isAccountActivated() {
        return accountActivated;
    }

    public void setAccountActivated(boolean accountActivated) {
        this.accountActivated = accountActivated;
    }

    public boolean isForm_couriered_Client() {
        return form_couriered_Client;
    }

    public void setForm_couriered_Client(boolean form_couriered_Client) {
        this.form_couriered_Client = form_couriered_Client;
    }

    public boolean isForm_received_client() {
        return form_received_client;
    }

    public void setForm_received_client(boolean form_received_client) {
        this.form_received_client = form_received_client;
    }

    public boolean isForm_Validated() {
        return form_Validated;
    }

    public void setForm_Validated(boolean form_Validated) {
        this.form_Validated = form_Validated;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }

    public String getRejection_Reason() {
        return rejection_Reason;
    }

    public void setRejection_Reason(String rejection_Reason) {
        this.rejection_Reason = rejection_Reason;
    }

    public boolean isRejection_Resolved() {
        return rejection_Resolved;
    }

    public void setRejection_Resolved(boolean rejection_Resolved) {
        this.rejection_Resolved = rejection_Resolved;
    }

    public boolean isuCC_created() {
        return uCC_created;
    }

    public void setuCC_created(boolean uCC_created) {
        this.uCC_created = uCC_created;
    }

    public String getNfisd() {
        return nfisd;
    }

    public void setNfisd(String nfisd) {
        this.nfisd = nfisd;
    }

    public String getNfstd() {
        return nfstd;
    }

    public void setNfstd(String nfstd) {
        this.nfstd = nfstd;
    }

    public String getNoisd() {
        return noisd;
    }

    public void setNoisd(String noisd) {
        this.noisd = noisd;
    }

    public String getNostd() {
        return nostd;
    }

    public void setNostd(String nostd) {
        this.nostd = nostd;
    }

    public String getNrisd() {
        return nrisd;
    }

    public void setNrisd(String nrisd) {
        this.nrisd = nrisd;
    }

    public String getNrstd() {
        return nrstd;
    }

    public void setNrstd(String nrstd) {
        this.nrstd = nrstd;
    }

    public String getMfisd() {
        return mfisd;
    }

    public void setMfisd(String mfisd) {
        this.mfisd = mfisd;
    }

    public String getMfstd() {
        return mfstd;
    }

    public void setMfstd(String mfstd) {
        this.mfstd = mfstd;
    }

    public String getMoisd() {
        return moisd;
    }

    public void setMoisd(String moisd) {
        this.moisd = moisd;
    }

    public String getMostd() {
        return mostd;
    }

    public void setMostd(String mostd) {
        this.mostd = mostd;
    }

    public String getMrisd() {
        return mrisd;
    }

    public void setMrisd(String mrisd) {
        this.mrisd = mrisd;
    }

    public String getMrstd() {
        return mrstd;
    }

    public void setMrstd(String mrstd) {
        this.mrstd = mrstd;
    }

    public String getMandateisd() {
        return mandateisd;
    }

    public void setMandateisd(String mandateisd) {
        this.mandateisd = mandateisd;
    }

    public String getMandatestd() {
        return mandatestd;
    }

    public void setMandatestd(String mandatestd) {
        this.mandatestd = mandatestd;
    }

    public String getTelOfficeNominee() {
        return telOfficeNominee;
    }

    public void setTelOfficeNominee(String telOfficeNominee) {
        this.telOfficeNominee = telOfficeNominee;
    }

    public String getTelResidenceNominee() {
        return telResidenceNominee;
    }

    public void setTelResidenceNominee(String telResidenceNominee) {
        this.telResidenceNominee = telResidenceNominee;
    }

    public String getFaxNominee() {
        return faxNominee;
    }

    public void setFaxNominee(String faxNominee) {
        this.faxNominee = faxNominee;
    }

    public String getTelOfficeNomineeMinor() {
        return telOfficeNomineeMinor;
    }

    public void setTelOfficeNomineeMinor(String telOfficeNomineeMinor) {
        this.telOfficeNomineeMinor = telOfficeNomineeMinor;
    }

    public String getTelResidenceNomineeMinor() {
        return telResidenceNomineeMinor;
    }

    public void setTelResidenceNomineeMinor(String telResidenceNomineeMinor) {
        this.telResidenceNomineeMinor = telResidenceNomineeMinor;
    }

    public String getFaxNomineeMinor() {
        return faxNomineeMinor;
    }

    public void setFaxNomineeMinor(String faxNomineeMinor) {
        this.faxNomineeMinor = faxNomineeMinor;
    }

    public String getSmsFacility() {
        return smsFacility;
    }

    public void setSmsFacility(String smsFacility) {
        this.smsFacility = smsFacility;
    }

    public String getPep() {
        return pep;
    }

    public void setPep(String pep) {
        this.pep = pep;
    }

    public String getRpep() {
        return rpep;
    }

    public void setRpep(String rpep) {
        this.rpep = rpep;
    }

    public String getInstruction1() {
        return instruction1;
    }

    public void setInstruction1(String instruction1) {
        this.instruction1 = instruction1;
    }

    public String getInstruction2() {
        return instruction2;
    }

    public void setInstruction2(String instruction2) {
        this.instruction2 = instruction2;
    }

    public String getInstruction3() {
        return instruction3;
    }

    public void setInstruction3(String instruction3) {
        this.instruction3 = instruction3;
    }

    public String getInstruction4() {
        return instruction4;
    }

    public void setInstruction4(String instruction4) {
        this.instruction4 = instruction4;
    }

    public String getInstruction5() {
        return instruction5;
    }

    public void setInstruction5(String instruction5) {
        this.instruction5 = instruction5;
    }

    public String getSmsFacilitySecondHolder() {
        return smsFacilitySecondHolder;
    }

    public void setSmsFacilitySecondHolder(String smsFacilitySecondHolder) {
        this.smsFacilitySecondHolder = smsFacilitySecondHolder;
    }

    public String getPoliticalyExposedSecondHolder() {
        return politicalyExposedSecondHolder;
    }

    public void setPoliticalyExposedSecondHolder(String politicalyExposedSecondHolder) {
        this.politicalyExposedSecondHolder = politicalyExposedSecondHolder;
    }

    public String getPoliticalyRelatedSecondHolder() {
        return politicalyRelatedSecondHolder;
    }

    public void setPoliticalyRelatedSecondHolder(String politicalyRelatedSecondHolder) {
        this.politicalyRelatedSecondHolder = politicalyRelatedSecondHolder;
    }

    public String getFacilityInternetTrading() {
        return facilityInternetTrading;
    }

    public void setFacilityInternetTrading(String facilityInternetTrading) {
        this.facilityInternetTrading = facilityInternetTrading;
    }

    public MandateFormTb getMandateData() {
        return mandateData;
    }

    public void setMandateData(MandateFormTb mandateData) {
        this.mandateData = mandateData;
    }

    public String getMandatePhone() {
        return mandatePhone;
    }

    public void setMandatePhone(String mandatePhone) {
        this.mandatePhone = mandatePhone;
    }

    public boolean isAdminUser() {
        return adminUser;
    }

    public void setAdminUser(boolean adminUser) {
        this.adminUser = adminUser;
    }

    public String getOmsLoginId() {
        return omsLoginId;
    }

    public void setOmsLoginId(String omsLoginId) {
        this.omsLoginId = omsLoginId;
    }

    public boolean isDownloadSebi() {
        return downloadSebi;
    }

    public void setDownloadSebi(boolean downloadSebi) {
        this.downloadSebi = downloadSebi;
    }

    public boolean isDownloadPan() {
        return downloadPan;
    }

    public void setDownloadPan(boolean downloadPan) {
        this.downloadPan = downloadPan;
    }

    public boolean isDownloadCorrespondence() {
        return downloadCorrespondence;
    }

    public void setDownloadCorrespondence(boolean downloadCorrespondence) {
        this.downloadCorrespondence = downloadCorrespondence;
    }

    public boolean isDownloadPermanentAddress() {
        return downloadPermanentAddress;
    }

    public void setDownloadPermanentAddress(boolean downloadPermanentAddress) {
        this.downloadPermanentAddress = downloadPermanentAddress;
    }

    public boolean isDownloadDocEvidence() {
        return downloadDocEvidence;
    }

    public void setDownloadDocEvidence(boolean downloadDocEvidence) {
        this.downloadDocEvidence = downloadDocEvidence;
    }
     public CustomerPortfolioTb getCustomerportfoliotb() {
        return customerportfoliotb;
    }

    public void setCustomerportfoliotb(CustomerPortfolioTb customerportfoliotb) {
        this.customerportfoliotb = customerportfoliotb;
    }
  

    public Double getHoldingValue() {
        return HoldingValue;
    }

    public void setHoldingValue(Double HoldingValue) {
        this.HoldingValue = HoldingValue;
    }

    public Double getUnSettledValue() {
        return unSettledValue;
    }

    public void setUnSettledValue(Double unSettledValue) {
        this.unSettledValue = unSettledValue;
    }
     public Float getLedgerValue() {
        return ledgerValue;
    }

    public void setLedgerValue(Float ledgerValue) {
        this.ledgerValue = ledgerValue;
    }

    public Float getPortfolioValue() {
        return portfolioValue;
    }

    public void setPortfolioValue(Float portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public String getMandateFailureReason() {
        return mandateFailureReason;
    }

    public void setMandateFailureReason(String mandateFailureReason) {
        this.mandateFailureReason = mandateFailureReason;
    }

    
}
