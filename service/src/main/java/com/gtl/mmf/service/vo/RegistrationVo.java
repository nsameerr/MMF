/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;
import javax.servlet.http.Part;

/**
 *
 * @author trainee8
 */
public class RegistrationVo {

    //id created for new applicant updation 
    Integer Id;
    String RegId;
    String firstName;
    String sebi_reg_no;
    String[] firstNameArray;
    String FatherOrSpouseName;
    String[] FatherOrSpouseNameArray;
    String status;
    String[] statusArray;
    String gender;
    String[] genderArray; //checkboxarray
    String maritalStatus; //checkboxarray
    String[] maritalStatusArray; //checkboxarray
    Date dob;
    String[] dobArray;
    String nationality;
    String[] nationalityArray; //checkboxarray
    String uid;
    String pan;
    String[] panArray;
    String idProof;
    String[] idProofArray; //checkboxarray

    String caddress;
    String address1_Line1;
    String address1_Street;
    String address1_Landmark;
    String address1_Zipcode;

    String[] cAddressArray;
    String ccity;
    String[] ccityArray;
    String cstate;
    String[] cstateArray;
    String ccountry;
    String[] ccountryArray;
    String cpinCode;
    String[] cpinCodeArray;

    String paddress;
    String address2_Line1;
    String address2_Street;
    String address2_Landmark;
    String address2_Zipcode;
    String[] paddressArray;

    String pcity;
    String[] pctyArray;
    String pstate;
    String[] pstateArray;
    String pcountry;
    String[] pcountryArray;
    String ppinCode;
    String[] ppinCodeArray;

    String address2_proof;
    String[] address2_proofArray;

    Date address2_validity;
    String[] address2_validityArray;

    String risd;
    String rstd;
    String rnumber;
    String[] rnumberArray;

    String hisd;
    String hstd;
    String hnumber;
    String[] hnumberArray;

    String fisd;
    String fstd;
    String fnumber;
    String[] fnumberArray;

    String mobileNumber;
    String[] mobileNumberArray;

    String email;
    String[] emailArray;

    String addressProof;
    String[] AddressproofArray; //checkboxarray

    Date dobValidity;
    String[] dobValidityArray;

    Boolean permenentAddress;

    String openAccountType;
    String[] openAccountTypeArray;

    String dpId;
    String[] dpIdArray;

    String DematAccount;
    String[] DematAccountArray;
    String tradingtAccount;
    String[] tradingtAccountArray;

    String smsFacility = "0";
    String[] smsFacilityArray;

    String incomerange;
    String[] incomerangeArray;

    String occupation;
    String[] occupationArray;

    String pep;
    String rpep;

    String nameOfEmployerOrCompany;

    String designation;

    Date netWorthdate;
    String[] netWorthdateArray;

    String netAmmount;

    String bankAccountNumber;

    String bankSubType;
    String[] bankSubTypeArray;

    String bankname;
//    String branch;
    String building;
    String bstreet;
    String barea;
    String bcity;
    String bpincode;
    String[] bPincodeArray;
    String bank_country;
    String bank_state;

    String micrCode;
    String[] micrCodeArray;

    String ifsc;
    String[] ifscArray;

    String instruction1;
    String instruction2;
    String instruction3;
    String instruction4;
    String instruction5;
    String[] StaingInstructionsArray;

    String clientId;
    String[] clientIdArray;

    private boolean advisor;
    private int user_status;
    private String access_token;
    private String expire_in;
    private String linkedin_member_id;
    private String linkedin_user;
    private String pictureUrl;
    private String name;
    private String middleName;
    private String lastName;
    private Date expire_in_date;
    private String linkedin_password;
    private boolean linkedInConnected;

    private boolean secondHolderDetailsAvailable;

    private String smsFacilitySecondHolder;
    String[] smsFacilitySecondHolderArray;

    private String nameSecondHolder;

    private String occupationSecondHolder;
    private String[] occupationSecondHolderArray;

    private String incomeRangeSecondHolder;
    private String[] incomeRangeSecondHolderArray;

    private Date netWorthDateSecondHolder;
    String[] netWorthDateSecondHolderArray;

    private String amountSecondHolder;

    private String politicalyExposedSecondHolder;
    private String politicalyRelatedSecondHolder;

    private String nameEmployerSecondHolder;
    private String designationSecondHolder;

    private String rbiReferenceNumber;
    private Date rbiApprovalDate;
    String[] rbiApprovalDateArray;

    private String depositoryParticipantName;
    private String depositoryName;
    private String beneficiaryName;
    private String dpIdDepository;
    private String beneficiaryId;

    private String tradingPreferenceExchange;
    private String tradingPreferenceSegment;

    private boolean documentaryEvedenceOther;
    private String documentaryEvedence;
    private String[] documentaryEvedenceArray;

    private boolean dealingThroughSubbroker;
    private String subbrokerName;
    private String nseSebiRegistrationNumber;
    private String bseSebiRegistrationNumber;
    private String registeredAddressSubbroker;
    private String phoneSubbroker;
    private String faxSubbroker;
    private String websiteSubbroker;
    private boolean dealingThroughBroker;
    private String nameStockBroker;
    private String nameSubbroker;
    private String clientCodeSubbroker;
    private String exchangeSubbroker;
    private String detailsBroker;

    private String typeElectronicContract;
    private String[] typeElectronicContractArray;

    private String facilityInternetTrading;
    private String[] facilityInternetTradingArray;

    private String tradingExperince;
    private String addressFirmPropritory;

    private String typeAlert;
    private String[] typeAlertArray;

    private String relationSipMobilenumber;
    private String[] relationSipMobilenumberArray;

    private String panMobileNumber;
    private String[] panMobileNumberArray;

    private String otherInformations;
    private boolean relativeGeojitEmployee;
    private String relationshipGeojitEmployee;
    private String geojitEmployeeCode;
    private String geojitEmployeeName;

    private boolean nomineeExist;
    private String[] nomineeExistArray;

    private String nameNominee;
    private String relationshipApplicant;
    private Date dobNominee;
    private String[] dobNomineeArray;
    private String addressNominee;
    String address1_Line1_Nominee;
    String address1_Street_Nominee;
    String address1_Landmark_Nominee;
    String address1_Zipcode_Nominee;

    private String placeNominee;
    private String pincodeNominee;
    private String stateNominee;
    private String countryNominee;
    private String telOfficeNominee;
    private String telResidenceNominee;
    private String faxNominee;

    private String mobileNominee;
    private String[] mobileNomineeArray;

    private String emailNominee;
    private String[] emailNomineeArray;
    private String nominePan;
    private String[] nominePanArray;
    private boolean nomineeMinor;
    private String nameGuardianNominee;
    private Date dobMinor;
    private String[] dobMinorArray;
    private String addressNomineeMinor;
    String address1_Line1_Minor;
    String address1_Street_Minor;
    String address1_Landmark_Minor;
    String address1_Zipcode_Minor;

    private String placeNomineeMinor;
    private String pincodeNomineeMinor;
    private String stateNomineeMinor;
    private String countryNomineeMinor;
    private String telOfficeNomineeMinor;
    private String telResidenceNomineeMinor;
    private String faxNomineeMinor;
    private String mobileNomineeMinor;
    private String[] mobileNomineeArrayMinor;
    private String emailNomineeMinor;
    private String[] emailNomineeArrayMinor;
    private String relationshipGuardianMinor;

    private String[] selectedSegments;
    private String[] selectedSegmentsArray;
    private String nomineCountryKey;
    private String nomineMinorCountryKey;

    private String telOfficeNominee1;
    private String telResidenceNominee1;
    private String faxNominee1;
    private String telOfficeNomineeMinor1;
    private String telResidenceNomineeMinor1;
    private String faxNomineeMinor1;

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

    Part idProofFile;
    Part corespondenceAddress;
    Part permenentAddressValidity;

    String idProofFileName;
    String corespondenceAddressFile;
    String permenentAddressFile;
    private boolean aggrementAccepted;

    Part docEvidenceFile;
    String documentaryFile;

    Part panAttestedFile;
    String panUploadFile;

    boolean fromRegistration = false;
    private String otherEvidenceProvided;
    private String cp_city_other;
    private String permanentCityOther;
    private String bnk_city_other;
    private String nomCityOther;
    private String minorCityOther;
    private Integer kitNumber;

    private String[] fatcaUsStatusArray;
    private String usNational;
    private String usResident;
    private String usBorn;
    private String usAddress;
    private String usTelephone;
    private String usStandingInstruction;
    private String usPoa;
    private String usMailAddress;
    private String individualTaxIdntfcnNmbr;
    private String[] individualTINArray;
    private String secondHldrPan;
    private String[] secondHldrPanArray;
    private String secondHldrDependentRelation;
    private String[] secondHldrDependentRelationArray;
    private String secondHldrDependentUsed;
    private String firstHldrDependentUsed;
    String nominee_proof;
    String nominee_aadhar;
    String[] nominee_adharArray;
    String nomineeMinor_proof;
    String[] nomineeMinor_aadharArray;
    String nomineeMinor_aadhar;
    String nomineeMinor_pan;
    String[] nomineeMinor_panArray;

    String[] middleNameArray;
    String[] LastNameArray;
    String[] fsFirstNameArray;
    String[] fsMiddleNameArray;
    String[] fsLastNameArray;
    String[] fstdArray;
    String[] hstdArray;
    String[] rstdArray;
    String[] misdArray;
    String[] ccountryCodeArray;
    String[] pstateCodeArray;
    String[] mmf_brokers_name;
    String[] cstateCodeArray;
    String[] pcountryCodeArray;
    String dobNomineeStrng;
    String[] pinArrayNominee;
    String[] pincodeNomineeMinorArray;
    String[] declarationDate;
    String motherName;
    String[] motherNameArray;
    String[] motherSecondNameArray;
    String[] motherThirdNameArray;
    String addressType;
    String[] addressTypeArray;
    String[] addressProofOthers;
    String proofOfIdentification; 
    String proofOfIdentificationMinor; 

    public String[] getPinArrayNominee() {
        return pinArrayNominee;
    }

    public void setPinArrayNominee(String[] pinArrayNominee) {
        this.pinArrayNominee = pinArrayNominee;
    }

    public String[] getCcountryCodeArray() {
        return ccountryCodeArray;
    }

    public void setCcountryCodeArray(String[] ccountryCodeArray) {
        this.ccountryCodeArray = ccountryCodeArray;
    }

    public String[] getPcountryCodeArray() {
        return pcountryCodeArray;
    }

    public void setPcountryCodeArray(String[] pcountryCodeArray) {
        this.pcountryCodeArray = pcountryCodeArray;
    }

    public String[] getPstateCodeArray() {
        return pstateCodeArray;
    }

    public void setPstateCodeArray(String[] pstateCodeArray) {
        this.pstateCodeArray = pstateCodeArray;
    }

    public String[] getCstateCodeArray() {
        return cstateCodeArray;
    }

    public void setCstateCodeArray(String[] cstateCodeArray) {
        this.cstateCodeArray = cstateCodeArray;
    }

    public String[] getMmf_brokers_name() {
        return mmf_brokers_name;
    }

    public void setMmf_brokers_name(String[] mmf_brokers_name) {
        this.mmf_brokers_name = mmf_brokers_name;
    }

    public String[] getFstdArray() {
        return fstdArray;
    }

    public void setFstdArray(String[] fstdArray) {
        this.fstdArray = fstdArray;
    }

    public String[] getHstdArray() {
        return hstdArray;
    }

    public void setHstdArray(String[] hstdArray) {
        this.hstdArray = hstdArray;
    }

    public String[] getRstdArray() {
        return rstdArray;
    }

    public void setRstdArray(String[] rstdArray) {
        this.rstdArray = rstdArray;
    }

    public String[] getMisdArray() {
        return misdArray;
    }

    public void setMisdArray(String[] misdArray) {
        this.misdArray = misdArray;
    }

    public String[] getFsFirstNameArray() {
        return fsFirstNameArray;
    }

    public void setFsFirstNameArray(String[] fsFirstNameArray) {
        this.fsFirstNameArray = fsFirstNameArray;
    }

    public String[] getFsMiddleNameArray() {
        return fsMiddleNameArray;
    }

    public void setFsMiddleNameArray(String[] fsMiddleNameArray) {
        this.fsMiddleNameArray = fsMiddleNameArray;
    }

    public String[] getFsLastNameArray() {
        return fsLastNameArray;
    }

    public void setFsLastNameArray(String[] fsLastNameArray) {
        this.fsLastNameArray = fsLastNameArray;
    }

    public String[] getMiddleNameArray() {
        return middleNameArray;
    }

    public void setMiddleNameArray(String[] middleNameArray) {
        this.middleNameArray = middleNameArray;
    }

    public String[] getLastNameArray() {
        return LastNameArray;
    }

    public void setLastNameArray(String[] LastNameArray) {
        this.LastNameArray = LastNameArray;
    }

    public Boolean getPermenentAddress() {
        return permenentAddress;
    }

    public String getClientId() {
        return clientId;
    }

    public String[] getClientIdArray() {
        return clientIdArray;
    }

    public void setClientIdArray(String[] clientIdArray) {
        this.clientIdArray = clientIdArray;
    }

    public boolean isSecondHolderDetailsAvailable() {
        return secondHolderDetailsAvailable;
    }

    public void setSecondHolderDetailsAvailable(boolean secondHolderDetailsAvailable) {
        this.secondHolderDetailsAvailable = secondHolderDetailsAvailable;
    }

    public boolean isDealingThroughSubbroker() {
        return dealingThroughSubbroker;
    }

    public void setDealingThroughSubbroker(boolean dealingThroughSubbroker) {
        this.dealingThroughSubbroker = dealingThroughSubbroker;
    }

    public boolean isDealingThroughBroker() {
        return dealingThroughBroker;
    }

    public boolean isFromRegistration() {
        return fromRegistration;
    }

    public void setFromRegistration(boolean fromRegistration) {
        this.fromRegistration = fromRegistration;
    }

    public void setDealingThroughBroker(boolean dealingThroughBroker) {
        this.dealingThroughBroker = dealingThroughBroker;
    }

    public boolean isRelativeGeojitEmployee() {
        return relativeGeojitEmployee;
    }

    public void setRelativeGeojitEmployee(boolean relativeGeojitEmployee) {
        this.relativeGeojitEmployee = relativeGeojitEmployee;
    }

    public boolean isNomineeExist() {
        return nomineeExist;
    }

    public void setNomineeExist(boolean nomineeExist) {
        this.nomineeExist = nomineeExist;
    }

    public boolean isNomineeMinor() {
        return nomineeMinor;
    }

    public void setNomineeMinor(boolean nomineeMinor) {
        this.nomineeMinor = nomineeMinor;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setPermenentAddress(Boolean permenentAddress) {
        this.permenentAddress = permenentAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String[] getFirstNameArray() {
        return firstNameArray;
    }

    public void setFirstNameArray(String[] firstNameArray) {
        this.firstNameArray = firstNameArray;
    }

    public String getFatherOrSpouseName() {
        return FatherOrSpouseName;
    }

    public void setFatherOrSpouseName(String FatherOrSpouseName) {
        this.FatherOrSpouseName = FatherOrSpouseName;
    }

    public String[] getFatherOrSpouseNameArray() {
        return FatherOrSpouseNameArray;
    }

    public void setFatherOrSpouseNameArray(String[] FatherOrSpouseNameArray) {
        this.FatherOrSpouseNameArray = FatherOrSpouseNameArray;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getStatusArray() {
        return statusArray;
    }

    public void setStatusArray(String[] statusArray) {
        this.statusArray = statusArray;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String[] getGenderArray() {
        return genderArray;
    }

    public void setGenderArray(String[] genderArray) {
        this.genderArray = genderArray;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String[] getMaritalStatusArray() {
        return maritalStatusArray;
    }

    public void setMaritalStatusArray(String[] maritalStatusArray) {
        this.maritalStatusArray = maritalStatusArray;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDobValidity() {
        return dobValidity;
    }

    public void setDobValidity(Date dobValidity) {
        this.dobValidity = dobValidity;
    }

    public String[] getDobArray() {
        return dobArray;
    }

    public void setDobArray(String[] dobArray) {
        this.dobArray = dobArray;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String[] getNationalityArray() {
        return nationalityArray;
    }

    public void setNationalityArray(String[] nationalityArray) {
        this.nationalityArray = nationalityArray;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String[] getPanArray() {
        return panArray;
    }

    public void setPanArray(String[] panArray) {
        this.panArray = panArray;
    }

    public String getIdProof() {
        return idProof;
    }

    public void setIdProof(String idProof) {
        this.idProof = idProof;
    }

    public String[] getIdProofArray() {
        return idProofArray;
    }

    public void setIdProofArray(String[] idProofArray) {
        this.idProofArray = idProofArray;
    }

    public String getCaddress() {
        return caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String[] getcAddressArray() {
        return cAddressArray;
    }

    public void setcAddressArray(String[] cAddressArray) {
        this.cAddressArray = cAddressArray;
    }

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String[] getCcityArray() {
        return ccityArray;
    }

    public void setCcityArray(String[] ccityArray) {
        this.ccityArray = ccityArray;
    }

    public String getCstate() {
        return cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String[] getCstateArray() {
        return cstateArray;
    }

    public void setCstateArray(String[] cstateArray) {
        this.cstateArray = cstateArray;
    }

    public String getCcountry() {
        return ccountry;
    }

    public void setCcountry(String ccountry) {
        this.ccountry = ccountry;
    }

    public String[] getCcountryArray() {
        return ccountryArray;
    }

    public void setCcountryArray(String[] ccountryArray) {
        this.ccountryArray = ccountryArray;
    }

    public String getCpinCode() {
        return cpinCode;
    }

    public void setCpinCode(String cpinCode) {
        this.cpinCode = cpinCode;
    }

    public String[] getCpinCodeArray() {
        return cpinCodeArray;
    }

    public void setCpinCodeArray(String[] cpinCodeArray) {
        this.cpinCodeArray = cpinCodeArray;
    }

    public String getPaddress() {
        return paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String[] getPaddressArray() {
        return paddressArray;
    }

    public void setPaddressArray(String[] paddressArray) {
        this.paddressArray = paddressArray;
    }

    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

    public String[] getPctyArray() {
        return pctyArray;
    }

    public void setPctyArray(String[] pctyArray) {
        this.pctyArray = pctyArray;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String[] getPstateArray() {
        return pstateArray;
    }

    public void setPstateArray(String[] pstateArray) {
        this.pstateArray = pstateArray;
    }

    public String getPcountry() {
        return pcountry;
    }

    public void setPcountry(String pcountry) {
        this.pcountry = pcountry;
    }

    public String[] getPcountryArray() {
        return pcountryArray;
    }

    public void setPcountryArray(String[] pcountryArray) {
        this.pcountryArray = pcountryArray;
    }

    public String getPpinCode() {
        return ppinCode;
    }

    public void setPpinCode(String ppinCode) {
        this.ppinCode = ppinCode;
    }

    public String[] getPpinCodeArray() {
        return ppinCodeArray;
    }

    public void setPpinCodeArray(String[] ppinCodeArray) {
        this.ppinCodeArray = ppinCodeArray;
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

    public String getRnumber() {
        return rnumber;
    }

    public void setRnumber(String rnumber) {
        this.rnumber = rnumber;
    }

    public String[] getRnumberArray() {
        return rnumberArray;
    }

    public void setRnumberArray(String[] rnumberArray) {
        this.rnumberArray = rnumberArray;
    }

    public String getHisd() {
        return hisd;
    }

    public void setHisd(String hisd) {
        this.hisd = hisd;
    }

    public String getHstd() {
        return hstd;
    }

    public void setHstd(String hstd) {
        this.hstd = hstd;
    }

    public String getHnumber() {
        return hnumber;
    }

    public void setHnumber(String hnumber) {
        this.hnumber = hnumber;
    }

    public String[] getHnumberArray() {
        return hnumberArray;
    }

    public void setHnumberArray(String[] hnumberArray) {
        this.hnumberArray = hnumberArray;
    }

    public String getFisd() {
        return fisd;
    }

    public void setFisd(String fisd) {
        this.fisd = fisd;
    }

    public String getFstd() {
        return fstd;
    }

    public void setFstd(String fstd) {
        this.fstd = fstd;
    }

    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    public String[] getFnumberArray() {
        return fnumberArray;
    }

    public void setFnumberArray(String[] fnumberArray) {
        this.fnumberArray = fnumberArray;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String[] getMobileNumberArray() {
        return mobileNumberArray;
    }

    public void setMobileNumberArray(String[] mobileNumberArray) {
        this.mobileNumberArray = mobileNumberArray;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getEmailArray() {
        return emailArray;
    }

    public void setEmailArray(String[] emailArray) {
        this.emailArray = emailArray;
    }

    public String getAddressProof() {
        return addressProof;
    }

    public void setAddressProof(String addressProof) {
        this.addressProof = addressProof;
    }

    public String[] getAddressproofArray() {
        return AddressproofArray;
    }

    public void setAddressproofArray(String[] AddressproofArray) {
        this.AddressproofArray = AddressproofArray;
    }

    public String[] getDobValidityArray() {
        return dobValidityArray;
    }

    public void setDobValidityArray(String[] dobValidityArray) {
        this.dobValidityArray = dobValidityArray;
    }

    public String getRegId() {
        return RegId;
    }

    public void setRegId(String RegId) {
        this.RegId = RegId;
    }

    public String getOpenAccountType() {
        return openAccountType;
    }

    public void setOpenAccountType(String openAccountType) {
        this.openAccountType = openAccountType;
    }

    public String[] getOpenAccountTypeArray() {
        return openAccountTypeArray;
    }

    public void setOpenAccountTypeArray(String[] openAccountTypeArray) {
        this.openAccountTypeArray = openAccountTypeArray;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String[] getDpIdArray() {
        return dpIdArray;
    }

    public void setDpIdArray(String[] dpIdArray) {
        this.dpIdArray = dpIdArray;
    }

    public String getDematAccount() {
        return DematAccount;
    }

    public void setDematAccount(String DematAccount) {
        this.DematAccount = DematAccount;
    }

    public String[] getDematAccountArray() {
        return DematAccountArray;
    }

    public void setDematAccountArray(String[] DematAccountArray) {
        this.DematAccountArray = DematAccountArray;
    }

    public String getTradingtAccount() {
        return tradingtAccount;
    }

    public void setTradingtAccount(String tradingtAccount) {
        this.tradingtAccount = tradingtAccount;
    }

    public String[] getTradingtAccountArray() {
        return tradingtAccountArray;
    }

    public void setTradingtAccountArray(String[] tradingtAccountArray) {
        this.tradingtAccountArray = tradingtAccountArray;
    }

    public String getSmsFacility() {
        return smsFacility;
    }

    public void setSmsFacility(String smsFacility) {
        this.smsFacility = smsFacility;
    }

    public String[] getSmsFacilityArray() {
        return smsFacilityArray;
    }

    public void setSmsFacilityArray(String[] smsFacilityArray) {
        this.smsFacilityArray = smsFacilityArray;
    }

    public String getIncomerange() {
        return incomerange;
    }

    public void setIncomerange(String incomerange) {
        this.incomerange = incomerange;
    }

    public String[] getIncomerangeArray() {
        return incomerangeArray;
    }

    public void setIncomerangeArray(String[] incomerangeArray) {
        this.incomerangeArray = incomerangeArray;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String[] getOccupationArray() {
        return occupationArray;
    }

    public void setOccupationArray(String[] occupationArray) {
        this.occupationArray = occupationArray;
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

    public String getNameOfEmployerOrCompany() {
        return nameOfEmployerOrCompany;
    }

    public void setNameOfEmployerOrCompany(String nameOfEmployerOrCompany) {
        this.nameOfEmployerOrCompany = nameOfEmployerOrCompany;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public Date getNetWorthdate() {
        return netWorthdate;
    }

    public void setNetWorthdate(Date netWorthdate) {
        this.netWorthdate = netWorthdate;
    }

    public String[] getNetWorthdateArray() {
        return netWorthdateArray;
    }

    public void setNetWorthdateArray(String[] netWorthdateArray) {
        this.netWorthdateArray = netWorthdateArray;
    }

    public String getNetAmmount() {
        return netAmmount;
    }

    public void setNetAmmount(String netAmmount) {
        this.netAmmount = netAmmount;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankSubType() {
        return bankSubType;
    }

    public void setBankSubType(String bankSubType) {
        this.bankSubType = bankSubType;
    }

    public String[] getBankSubTypeArray() {
        return bankSubTypeArray;
    }

    public void setBankSubTypeArray(String[] bankSubTypeArray) {
        this.bankSubTypeArray = bankSubTypeArray;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

//    public String getBranch() {
//        return branch;
//    }
//
//    public void setBranch(String branch) {
//        this.branch = branch;
//    }
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getBstreet() {
        return bstreet;
    }

    public void setBstreet(String bstreet) {
        this.bstreet = bstreet;
    }

    public String getBarea() {
        return barea;
    }

    public void setBarea(String barea) {
        this.barea = barea;
    }

    public String getBcity() {
        return bcity;
    }

    public void setBcity(String bcity) {
        this.bcity = bcity;
    }

    public String getBpincode() {
        return bpincode;
    }

    public void setBpincode(String bpincode) {
        this.bpincode = bpincode;
    }

    public String[] getbPincodeArray() {
        return bPincodeArray;
    }

    public void setbPincodeArray(String[] bPincodeArray) {
        this.bPincodeArray = bPincodeArray;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public String[] getMicrCodeArray() {
        return micrCodeArray;
    }

    public void setMicrCodeArray(String[] micrCodeArray) {
        this.micrCodeArray = micrCodeArray;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String[] getIfscArray() {
        return ifscArray;
    }

    public void setIfscArray(String[] ifscArray) {
        this.ifscArray = ifscArray;
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

    public String[] getStaingInstructionsArray() {
        return StaingInstructionsArray;
    }

    public void setStaingInstructionsArray(String[] StaingInstructionsArray) {
        this.StaingInstructionsArray = StaingInstructionsArray;
    }

    //----
    public String getSebi_reg_no() {
        return sebi_reg_no;
    }

    public void setSebi_reg_no(String sebi_reg_no) {
        this.sebi_reg_no = sebi_reg_no;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public int getUser_status() {
        return user_status;
    }

    public void setUser_status(int user_status) {
        this.user_status = user_status;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpire_in() {
        return expire_in;
    }

    public void setExpire_in(String expire_in) {
        this.expire_in = expire_in;
    }

    public String getLinkedin_member_id() {
        return linkedin_member_id;
    }

    public void setLinkedin_member_id(String linkedin_member_id) {
        this.linkedin_member_id = linkedin_member_id;
    }

    public String getLinkedin_user() {
        return linkedin_user;
    }

    public void setLinkedin_user(String linkedin_user) {
        this.linkedin_user = linkedin_user;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getExpire_in_date() {
        return expire_in_date;
    }

    public void setExpire_in_date(Date expire_in_date) {
        this.expire_in_date = expire_in_date;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public String getLinkedin_password() {
        return linkedin_password;
    }

    public void setLinkedin_password(String linkedin_password) {
        this.linkedin_password = linkedin_password;
    }

    public String getAddress1_Line1() {
        return address1_Line1;
    }

    public void setAddress1_Line1(String address1_Line1) {
        this.address1_Line1 = address1_Line1;
    }

    public String getAddress1_Street() {
        return address1_Street;
    }

    public void setAddress1_Street(String address1_Street) {
        this.address1_Street = address1_Street;
    }

    public String getAddress1_Landmark() {
        return address1_Landmark;
    }

    public void setAddress1_Landmark(String address1_Landmark) {
        this.address1_Landmark = address1_Landmark;
    }

    public String getAddress1_Zipcode() {
        return address1_Zipcode;
    }

    public void setAddress1_Zipcode(String address1_Zipcode) {
        this.address1_Zipcode = address1_Zipcode;
    }

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

    public String getAddress2_Zipcode() {
        return address2_Zipcode;
    }

    public void setAddress2_Zipcode(String address2_Zipcode) {
        this.address2_Zipcode = address2_Zipcode;
    }

    public String getAddress2_proof() {
        return address2_proof;
    }

    public void setAddress2_proof(String address2_proof) {
        this.address2_proof = address2_proof;
    }

    public Date getAddress2_validity() {
        return address2_validity;
    }

    public void setAddress2_validity(Date address2_validity) {
        this.address2_validity = address2_validity;
    }

    public String[] getAddress2_proofArray() {
        return address2_proofArray;
    }

    public void setAddress2_proofArray(String[] address2_proofArray) {
        this.address2_proofArray = address2_proofArray;
    }

    public String[] getAddress2_validityArray() {
        return address2_validityArray;
    }

    public void setAddress2_validityArray(String[] address2_validityArray) {
        this.address2_validityArray = address2_validityArray;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public String getSmsFacilitySecondHolder() {
        return smsFacilitySecondHolder;
    }

    public void setSmsFacilitySecondHolder(String smsFacilitySecondHolder) {
        this.smsFacilitySecondHolder = smsFacilitySecondHolder;
    }

    public String[] getSmsFacilitySecondHolderArray() {
        return smsFacilitySecondHolderArray;
    }

    public void setSmsFacilitySecondHolderArray(String[] smsFacilitySecondHolderArray) {
        this.smsFacilitySecondHolderArray = smsFacilitySecondHolderArray;
    }

    public String getNameSecondHolder() {
        return nameSecondHolder;
    }

    public void setNameSecondHolder(String nameSecondHolder) {
        this.nameSecondHolder = nameSecondHolder;
    }

    public String getOccupationSecondHolder() {
        return occupationSecondHolder;
    }

    public void setOccupationSecondHolder(String occupationSecondHolder) {
        this.occupationSecondHolder = occupationSecondHolder;
    }

    public String[] getOccupationSecondHolderArray() {
        return occupationSecondHolderArray;
    }

    public void setOccupationSecondHolderArray(String[] occupationSecondHolderArray) {
        this.occupationSecondHolderArray = occupationSecondHolderArray;
    }

    public String getIncomeRangeSecondHolder() {
        return incomeRangeSecondHolder;
    }

    public void setIncomeRangeSecondHolder(String incomeRangeSecondHolder) {
        this.incomeRangeSecondHolder = incomeRangeSecondHolder;
    }

    public String[] getIncomeRangeSecondHolderArray() {
        return incomeRangeSecondHolderArray;
    }

    public void setIncomeRangeSecondHolderArray(String[] incomeRangeSecondHolderArray) {
        this.incomeRangeSecondHolderArray = incomeRangeSecondHolderArray;
    }

    public Date getNetWorthDateSecondHolder() {
        return netWorthDateSecondHolder;
    }

    public void setNetWorthDateSecondHolder(Date netWorthDateSecondHolder) {
        this.netWorthDateSecondHolder = netWorthDateSecondHolder;
    }

    public String[] getNetWorthDateSecondHolderArray() {
        return netWorthDateSecondHolderArray;
    }

    public void setNetWorthDateSecondHolderArray(String[] netWorthDateSecondHolderArray) {
        this.netWorthDateSecondHolderArray = netWorthDateSecondHolderArray;
    }

    public String getAmountSecondHolder() {
        return amountSecondHolder;
    }

    public void setAmountSecondHolder(String amountSecondHolder) {
        this.amountSecondHolder = amountSecondHolder;
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

    public String getNameEmployerSecondHolder() {
        return nameEmployerSecondHolder;
    }

    public void setNameEmployerSecondHolder(String nameEmployerSecondHolder) {
        this.nameEmployerSecondHolder = nameEmployerSecondHolder;
    }

    public String getDesignationSecondHolder() {
        return designationSecondHolder;
    }

    public void setDesignationSecondHolder(String designationSecondHolder) {
        this.designationSecondHolder = designationSecondHolder;
    }

    public String getRbiReferenceNumber() {
        return rbiReferenceNumber;
    }

    public void setRbiReferenceNumber(String rbiReferenceNumber) {
        this.rbiReferenceNumber = rbiReferenceNumber;
    }

    public Date getRbiApprovalDate() {
        return rbiApprovalDate;
    }

    public void setRbiApprovalDate(Date rbiApprovalDate) {
        this.rbiApprovalDate = rbiApprovalDate;
    }

    public String[] getRbiApprovalDateArray() {
        return rbiApprovalDateArray;
    }

    public void setRbiApprovalDateArray(String[] rbiApprovalDateArray) {
        this.rbiApprovalDateArray = rbiApprovalDateArray;
    }

    public String getDepositoryParticipantName() {
        return depositoryParticipantName;
    }

    public void setDepositoryParticipantName(String depositoryParticipantName) {
        this.depositoryParticipantName = depositoryParticipantName;
    }

    public String getDepositoryName() {
        return depositoryName;
    }

    public void setDepositoryName(String depositoryName) {
        this.depositoryName = depositoryName;
    }

    public String getBeneficiaryName() {
        return beneficiaryName;
    }

    public void setBeneficiaryName(String beneficiaryName) {
        this.beneficiaryName = beneficiaryName;
    }

    public String getDpIdDepository() {
        return dpIdDepository;
    }

    public void setDpIdDepository(String dpIdDepository) {
        this.dpIdDepository = dpIdDepository;
    }

    public String getBeneficiaryId() {
        return beneficiaryId;
    }

    public void setBeneficiaryId(String beneficiaryId) {
        this.beneficiaryId = beneficiaryId;
    }

    public String getTradingPreferenceExchange() {
        return tradingPreferenceExchange;
    }

    public void setTradingPreferenceExchange(String tradingPreferenceExchange) {
        this.tradingPreferenceExchange = tradingPreferenceExchange;
    }

    public String getTradingPreferenceSegment() {
        return tradingPreferenceSegment;
    }

    public void setTradingPreferenceSegment(String tradingPreferenceSegment) {
        this.tradingPreferenceSegment = tradingPreferenceSegment;
    }

    public String getDocumentaryEvedence() {
        return documentaryEvedence;
    }

    public void setDocumentaryEvedence(String documentaryEvedence) {
        this.documentaryEvedence = documentaryEvedence;
    }

    public String[] getDocumentaryEvedenceArray() {
        return documentaryEvedenceArray;
    }

    public void setDocumentaryEvedenceArray(String[] documentaryEvedenceArray) {
        this.documentaryEvedenceArray = documentaryEvedenceArray;
    }

    public String getSubbrokerName() {
        return subbrokerName;
    }

    public void setSubbrokerName(String subbrokerName) {
        this.subbrokerName = subbrokerName;
    }

    public String getNseSebiRegistrationNumber() {
        return nseSebiRegistrationNumber;
    }

    public void setNseSebiRegistrationNumber(String nseSebiRegistrationNumber) {
        this.nseSebiRegistrationNumber = nseSebiRegistrationNumber;
    }

    public String getBseSebiRegistrationNumber() {
        return bseSebiRegistrationNumber;
    }

    public void setBseSebiRegistrationNumber(String bseSebiRegistrationNumber) {
        this.bseSebiRegistrationNumber = bseSebiRegistrationNumber;
    }

    public String getRegisteredAddressSubbroker() {
        return registeredAddressSubbroker;
    }

    public void setRegisteredAddressSubbroker(String registeredAddressSubbroker) {
        this.registeredAddressSubbroker = registeredAddressSubbroker;
    }

    public String getPhoneSubbroker() {
        return phoneSubbroker;
    }

    public void setPhoneSubbroker(String phoneSubbroker) {
        this.phoneSubbroker = phoneSubbroker;
    }

    public String getFaxSubbroker() {
        return faxSubbroker;
    }

    public void setFaxSubbroker(String faxSubbroker) {
        this.faxSubbroker = faxSubbroker;
    }

    public String getWebsiteSubbroker() {
        return websiteSubbroker;
    }

    public void setWebsiteSubbroker(String websiteSubbroker) {
        this.websiteSubbroker = websiteSubbroker;
    }

    public String getNameStockBroker() {
        return nameStockBroker;
    }

    public void setNameStockBroker(String nameStockBroker) {
        this.nameStockBroker = nameStockBroker;
    }

    public String getNameSubbroker() {
        return nameSubbroker;
    }

    public void setNameSubbroker(String nameSubbroker) {
        this.nameSubbroker = nameSubbroker;
    }

    public String getClientCodeSubbroker() {
        return clientCodeSubbroker;
    }

    public void setClientCodeSubbroker(String clientCodeSubbroker) {
        this.clientCodeSubbroker = clientCodeSubbroker;
    }

    public String getExchangeSubbroker() {
        return exchangeSubbroker;
    }

    public void setExchangeSubbroker(String exchangeSubbroker) {
        this.exchangeSubbroker = exchangeSubbroker;
    }

    public String getDetailsBroker() {
        return detailsBroker;
    }

    public void setDetailsBroker(String detailsBroker) {
        this.detailsBroker = detailsBroker;
    }

    public String getTypeElectronicContract() {
        return typeElectronicContract;
    }

    public void setTypeElectronicContract(String typeElectronicContract) {
        this.typeElectronicContract = typeElectronicContract;
    }

    public String[] getTypeElectronicContractArray() {
        return typeElectronicContractArray;
    }

    public void setTypeElectronicContractArray(String[] typeElectronicContractArray) {
        this.typeElectronicContractArray = typeElectronicContractArray;
    }

    public String getFacilityInternetTrading() {
        return facilityInternetTrading;
    }

    public void setFacilityInternetTrading(String facilityInternetTrading) {
        this.facilityInternetTrading = facilityInternetTrading;
    }

    public String[] getFacilityInternetTradingArray() {
        return facilityInternetTradingArray;
    }

    public void setFacilityInternetTradingArray(String[] facilityInternetTradingArray) {
        this.facilityInternetTradingArray = facilityInternetTradingArray;
    }

    public String getTradingExperince() {
        return tradingExperince;
    }

    public void setTradingExperince(String tradingExperince) {
        this.tradingExperince = tradingExperince;
    }

    public String getAddressFirmPropritory() {
        return addressFirmPropritory;
    }

    public void setAddressFirmPropritory(String addressFirmPropritory) {
        this.addressFirmPropritory = addressFirmPropritory;
    }

    public String getTypeAlert() {
        return typeAlert;
    }

    public void setTypeAlert(String typeAlert) {
        this.typeAlert = typeAlert;
    }

    public String[] getTypeAlertArray() {
        return typeAlertArray;
    }

    public void setTypeAlertArray(String[] typeAlertArray) {
        this.typeAlertArray = typeAlertArray;
    }

    public String getRelationSipMobilenumber() {
        return relationSipMobilenumber;
    }

    public void setRelationSipMobilenumber(String relationSipMobilenumber) {
        this.relationSipMobilenumber = relationSipMobilenumber;
    }

    public String[] getRelationSipMobilenumberArray() {
        return relationSipMobilenumberArray;
    }

    public void setRelationSipMobilenumberArray(String[] relationSipMobilenumberArray) {
        this.relationSipMobilenumberArray = relationSipMobilenumberArray;
    }

    public String getPanMobileNumber() {
        return panMobileNumber;
    }

    public void setPanMobileNumber(String panMobileNumber) {
        this.panMobileNumber = panMobileNumber;
    }

    public String[] getPanMobileNumberArray() {
        return panMobileNumberArray;
    }

    public void setPanMobileNumberArray(String[] panMobileNumberArray) {
        this.panMobileNumberArray = panMobileNumberArray;
    }

    public String getOtherInformations() {
        return otherInformations;
    }

    public void setOtherInformations(String otherInformations) {
        this.otherInformations = otherInformations;
    }

    public void setRelativeGeojitEmployee(Boolean relativeGeojitEmployee) {
        this.relativeGeojitEmployee = relativeGeojitEmployee;
    }

    public String getRelationshipGeojitEmployee() {
        return relationshipGeojitEmployee;
    }

    public void setRelationshipGeojitEmployee(String relationshipGeojitEmployee) {
        this.relationshipGeojitEmployee = relationshipGeojitEmployee;
    }

    public String getGeojitEmployeeCode() {
        return geojitEmployeeCode;
    }

    public void setGeojitEmployeeCode(String geojitEmployeeCode) {
        this.geojitEmployeeCode = geojitEmployeeCode;
    }

    public String getGeojitEmployeeName() {
        return geojitEmployeeName;
    }

    public void setGeojitEmployeeName(String geojitEmployeeName) {
        this.geojitEmployeeName = geojitEmployeeName;
    }

    public String[] getNomineeExistArray() {
        return nomineeExistArray;
    }

    public void setNomineeExistArray(String[] nomineeExistArray) {
        this.nomineeExistArray = nomineeExistArray;
    }

    public String getNameNominee() {
        return nameNominee;
    }

    public void setNameNominee(String nameNominee) {
        this.nameNominee = nameNominee;
    }

    public String getRelationshipApplicant() {
        return relationshipApplicant;
    }

    public void setRelationshipApplicant(String relationshipApplicant) {
        this.relationshipApplicant = relationshipApplicant;
    }

    public Date getDobNominee() {
        return dobNominee;
    }

    public void setDobNominee(Date dobNominee) {
        this.dobNominee = dobNominee;
    }

    public String[] getDobNomineeArray() {
        return dobNomineeArray;
    }

    public void setDobNomineeArray(String[] dobNomineeArray) {
        this.dobNomineeArray = dobNomineeArray;
    }

    public String getAddressNominee() {
        return addressNominee;
    }

    public void setAddressNominee(String addressNominee) {
        this.addressNominee = addressNominee;
    }

    public String getPlaceNominee() {
        return placeNominee;
    }

    public void setPlaceNominee(String placeNominee) {
        this.placeNominee = placeNominee;
    }

    public String getPincodeNominee() {
        return pincodeNominee;
    }

    public void setPincodeNominee(String pincodeNominee) {
        this.pincodeNominee = pincodeNominee;
    }

    public String getStateNominee() {
        return stateNominee;
    }

    public void setStateNominee(String stateNominee) {
        this.stateNominee = stateNominee;
    }

    public String getCountryNominee() {
        return countryNominee;
    }

    public void setCountryNominee(String countryNominee) {
        this.countryNominee = countryNominee;
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

    public String getMobileNominee() {
        return mobileNominee;
    }

    public void setMobileNominee(String mobileNominee) {
        this.mobileNominee = mobileNominee;
    }

    public String[] getMobileNomineeArray() {
        return mobileNomineeArray;
    }

    public void setMobileNomineeArray(String[] mobileNomineeArray) {
        this.mobileNomineeArray = mobileNomineeArray;
    }

    public String getEmailNominee() {
        return emailNominee;
    }

    public void setEmailNominee(String emailNominee) {
        this.emailNominee = emailNominee;
    }

    public String[] getEmailNomineeArray() {
        return emailNomineeArray;
    }

    public void setEmailNomineeArray(String[] emailNomineeArray) {
        this.emailNomineeArray = emailNomineeArray;
    }

    public String getNominePan() {
        return nominePan;
    }

    public void setNominePan(String nominePan) {
        this.nominePan = nominePan;
    }

    public String[] getNominePanArray() {
        return nominePanArray;
    }

    public void setNominePanArray(String[] nominePanArray) {
        this.nominePanArray = nominePanArray;
    }

    public String getNameGuardianNominee() {
        return nameGuardianNominee;
    }

    public void setNameGuardianNominee(String nameGuardianNominee) {
        this.nameGuardianNominee = nameGuardianNominee;
    }

    public Date getDobMinor() {
        return dobMinor;
    }

    public void setDobMinor(Date dobMinor) {
        this.dobMinor = dobMinor;
    }

    public String[] getDobMinorArray() {
        return dobMinorArray;
    }

    public void setDobMinorArray(String[] dobMinorArray) {
        this.dobMinorArray = dobMinorArray;
    }

    public String getAddressNomineeMinor() {
        return addressNomineeMinor;
    }

    public void setAddressNomineeMinor(String addressNomineeMinor) {
        this.addressNomineeMinor = addressNomineeMinor;
    }

    public String getPlaceNomineeMinor() {
        return placeNomineeMinor;
    }

    public void setPlaceNomineeMinor(String placeNomineeMinor) {
        this.placeNomineeMinor = placeNomineeMinor;
    }

    public String getPincodeNomineeMinor() {
        return pincodeNomineeMinor;
    }

    public void setPincodeNomineeMinor(String pincodeNomineeMinor) {
        this.pincodeNomineeMinor = pincodeNomineeMinor;
    }

    public String getStateNomineeMinor() {
        return stateNomineeMinor;
    }

    public void setStateNomineeMinor(String stateNomineeMinor) {
        this.stateNomineeMinor = stateNomineeMinor;
    }

    public String getCountryNomineeMinor() {
        return countryNomineeMinor;
    }

    public void setCountryNomineeMinor(String countryNomineeMinor) {
        this.countryNomineeMinor = countryNomineeMinor;
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

    public String getMobileNomineeMinor() {
        return mobileNomineeMinor;
    }

    public void setMobileNomineeMinor(String mobileNomineeMinor) {
        this.mobileNomineeMinor = mobileNomineeMinor;
    }

    public String[] getMobileNomineeArrayMinor() {
        return mobileNomineeArrayMinor;
    }

    public void setMobileNomineeArrayMinor(String[] mobileNomineeArrayMinor) {
        this.mobileNomineeArrayMinor = mobileNomineeArrayMinor;
    }

    public String getEmailNomineeMinor() {
        return emailNomineeMinor;
    }

    public void setEmailNomineeMinor(String emailNomineeMinor) {
        this.emailNomineeMinor = emailNomineeMinor;
    }

    public String[] getEmailNomineeArrayMinor() {
        return emailNomineeArrayMinor;
    }

    public void setEmailNomineeArrayMinor(String[] emailNomineeArrayMinor) {
        this.emailNomineeArrayMinor = emailNomineeArrayMinor;
    }

    public String getRelationshipGuardianMinor() {
        return relationshipGuardianMinor;
    }

    public void setRelationshipGuardianMinor(String relationshipGuardianMinor) {
        this.relationshipGuardianMinor = relationshipGuardianMinor;
    }

    public boolean isDocumentaryEvedenceOther() {
        return documentaryEvedenceOther;
    }

    public void setDocumentaryEvedenceOther(boolean documentaryEvedenceOther) {
        this.documentaryEvedenceOther = documentaryEvedenceOther;
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

    public String[] getSelectedSegmentsArray() {
        return selectedSegmentsArray;
    }

    public void setSelectedSegmentsArray(String[] selectedSegmentsArray) {
        this.selectedSegmentsArray = selectedSegmentsArray;
    }

    public String getNomineCountryKey() {
        return nomineCountryKey;
    }

    public void setNomineCountryKey(String nomineCountryKey) {
        this.nomineCountryKey = nomineCountryKey;
    }

    public String getNomineMinorCountryKey() {
        return nomineMinorCountryKey;
    }

    public void setNomineMinorCountryKey(String nomineMinorCountryKey) {
        this.nomineMinorCountryKey = nomineMinorCountryKey;
    }

    public String getBank_country() {
        return bank_country;
    }

    public void setBank_country(String bank_country) {
        this.bank_country = bank_country;
    }

    public String getBank_state() {
        return bank_state;
    }

    public void setBank_state(String bank_state) {
        this.bank_state = bank_state;
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

    public Part getIdProofFile() {
        return idProofFile;
    }

    public void setIdProofFile(Part idProofFile) {
        this.idProofFile = idProofFile;
    }

    public Part getCorespondenceAddress() {
        return corespondenceAddress;
    }

    public void setCorespondenceAddress(Part corespondenceAddress) {
        this.corespondenceAddress = corespondenceAddress;
    }

    public Part getPermenentAddressValidity() {
        return permenentAddressValidity;
    }

    public void setPermenentAddressValidity(Part permenentAddressValidity) {
        this.permenentAddressValidity = permenentAddressValidity;
    }

    public String getIdProofFileName() {
        return idProofFileName;
    }

    public void setIdProofFileName(String idProofFileName) {
        this.idProofFileName = idProofFileName;
    }

    public String getCorespondenceAddressFile() {
        return corespondenceAddressFile;
    }

    public void setCorespondenceAddressFile(String corespondenceAddressFile) {
        this.corespondenceAddressFile = corespondenceAddressFile;
    }

    public String getPermenentAddressFile() {
        return permenentAddressFile;
    }

    public void setPermenentAddressFile(String permenentAddressFile) {
        this.permenentAddressFile = permenentAddressFile;
    }

    public boolean isAggrementAccepted() {
        return aggrementAccepted;
    }

    public void setAggrementAccepted(boolean aggrementAccepted) {
        this.aggrementAccepted = aggrementAccepted;
    }

   public Part getDocEvidenceFile() {
        return docEvidenceFile;
    }

    public void setDocEvidenceFile(Part docEvidenceFile) {
        this.docEvidenceFile = docEvidenceFile;
    }

    public String getDocumentaryFile() {
        return documentaryFile;
    }

    public void setDocumentaryFile(String documentaryFile) {
        this.documentaryFile = documentaryFile;
    }

    public Part getPanAttestedFile() {
        return panAttestedFile;
    }

    public void setPanAttestedFile(Part panAttestedFile) {
        this.panAttestedFile = panAttestedFile;
    }

    public String getPanUploadFile() {
        return panUploadFile;
    }

    public void setPanUploadFile(String panUploadFile) {
        this.panUploadFile = panUploadFile;
    }

    public String getTelOfficeNominee1() {
        return telOfficeNominee1;
    }

    public void setTelOfficeNominee1(String telOfficeNominee1) {
        this.telOfficeNominee1 = telOfficeNominee1;
    }

    public String getTelResidenceNominee1() {
        return telResidenceNominee1;
    }

    public void setTelResidenceNominee1(String telResidenceNominee1) {
        this.telResidenceNominee1 = telResidenceNominee1;
    }

    public String getFaxNominee1() {
        return faxNominee1;
    }

    public void setFaxNominee1(String faxNominee1) {
        this.faxNominee1 = faxNominee1;
    }

    public String getTelOfficeNomineeMinor1() {
        return telOfficeNomineeMinor1;
    }

    public void setTelOfficeNomineeMinor1(String telOfficeNomineeMinor1) {
        this.telOfficeNomineeMinor1 = telOfficeNomineeMinor1;
    }

    public String getTelResidenceNomineeMinor1() {
        return telResidenceNomineeMinor1;
    }

    public void setTelResidenceNomineeMinor1(String telResidenceNomineeMinor1) {
        this.telResidenceNomineeMinor1 = telResidenceNomineeMinor1;
    }

    public String getFaxNomineeMinor1() {
        return faxNomineeMinor1;
    }

    public void setFaxNomineeMinor1(String faxNomineeMinor1) {
        this.faxNomineeMinor1 = faxNomineeMinor1;
    }

    public String getOtherEvidenceProvided() {
        return otherEvidenceProvided;
    }

    public void setOtherEvidenceProvided(String otherEvidenceProvided) {
        this.otherEvidenceProvided = otherEvidenceProvided;
    }

    public String getCp_city_other() {
        return cp_city_other;
    }

    public void setCp_city_other(String cp_city_other) {
        this.cp_city_other = cp_city_other;
    }

    public String getPermanentCityOther() {
        return permanentCityOther;
    }

    public void setPermanentCityOther(String permanentCityOther) {
        this.permanentCityOther = permanentCityOther;
    }

    public String getBnk_city_other() {
        return bnk_city_other;
    }

    public void setBnk_city_other(String bnk_city_other) {
        this.bnk_city_other = bnk_city_other;
    }

    public String getNomCityOther() {
        return nomCityOther;
    }

    public void setNomCityOther(String nomCityOther) {
        this.nomCityOther = nomCityOther;
    }

    public String getMinorCityOther() {
        return minorCityOther;
    }

    public void setMinorCityOther(String minorCityOther) {
        this.minorCityOther = minorCityOther;
    }

    public String[] getFatcaUsStatusArray() {
        return fatcaUsStatusArray;
    }

    public void setFatcaUsStatusArray(String[] fatcaUsStatusArray) {
        this.fatcaUsStatusArray = fatcaUsStatusArray;
    }

    public String getUsNational() {
        return usNational;
    }

    public void setUsNational(String usNational) {
        this.usNational = usNational;
    }

    public String getUsResident() {
        return usResident;
    }

    public void setUsResident(String usResident) {
        this.usResident = usResident;
    }

    public String getUsBorn() {
        return usBorn;
    }

    public void setUsBorn(String usBorn) {
        this.usBorn = usBorn;
    }

    public String getUsAddress() {
        return usAddress;
    }

    public void setUsAddress(String usAddress) {
        this.usAddress = usAddress;
    }

    public String getUsTelephone() {
        return usTelephone;
    }

    public void setUsTelephone(String usTelephone) {
        this.usTelephone = usTelephone;
    }

    public String getUsStandingInstruction() {
        return usStandingInstruction;
    }

    public void setUsStandingInstruction(String usStandingInstruction) {
        this.usStandingInstruction = usStandingInstruction;
    }

    public String getUsPoa() {
        return usPoa;
    }

    public void setUsPoa(String usPoa) {
        this.usPoa = usPoa;
    }

    public String getUsMailAddress() {
        return usMailAddress;
    }

    public void setUsMailAddress(String usMailAddress) {
        this.usMailAddress = usMailAddress;
    }

    public String getIndividualTaxIdntfcnNmbr() {
        return individualTaxIdntfcnNmbr;
    }

    public void setIndividualTaxIdntfcnNmbr(String individualTaxIdntfcnNmbr) {
        this.individualTaxIdntfcnNmbr = individualTaxIdntfcnNmbr;
    }

    public String[] getIndividualTINArray() {
        return individualTINArray;
    }

    public void setIndividualTINArray(String[] individualTINArray) {
        this.individualTINArray = individualTINArray;
    }

    public String getSecondHldrPan() {
        return secondHldrPan;
    }

    public void setSecondHldrPan(String secondHldrPan) {
        this.secondHldrPan = secondHldrPan;
    }

    public String getSecondHldrDependentRelation() {
        return secondHldrDependentRelation;
    }

    public void setSecondHldrDependentRelation(String secondHldrDependentRelation) {
        this.secondHldrDependentRelation = secondHldrDependentRelation;
    }

    public String getSecondHldrDependentUsed() {
        return secondHldrDependentUsed;
    }

    public void setSecondHldrDependentUsed(String secondHldrDependentUsed) {
        this.secondHldrDependentUsed = secondHldrDependentUsed;
    }

    public String[] getSecondHldrPanArray() {
        return secondHldrPanArray;
    }

    public void setSecondHldrPanArray(String[] secondHldrPanArray) {
        this.secondHldrPanArray = secondHldrPanArray;
    }

    public String[] getSecondHldrDependentRelationArray() {
        return secondHldrDependentRelationArray;
    }

    public void setSecondHldrDependentRelationArray(String[] secondHldrDependentRelationArray) {
        this.secondHldrDependentRelationArray = secondHldrDependentRelationArray;
    }

    public String getFirstHldrDependentUsed() {
        return firstHldrDependentUsed;
    }

    public void setFirstHldrDependentUsed(String firstHldrDependentUsed) {
        this.firstHldrDependentUsed = firstHldrDependentUsed;
    }

    public Integer getKitNumber() {
        return kitNumber;
    }

    public void setKitNumber(Integer kitNumber) {
        this.kitNumber = kitNumber;
    }

    public String getNominee_proof() {
        return nominee_proof;
    }

    public void setNominee_proof(String nominee_proof) {
        this.nominee_proof = nominee_proof;
    }

    public String getNomineeMinor_proof() {
        return nomineeMinor_proof;
    }

    public void setNomineeMinor_proof(String nomineeMinor_proof) {
        this.nomineeMinor_proof = nomineeMinor_proof;
    }

    public String getNominee_aadhar() {
        return nominee_aadhar;
    }

    public void setNominee_aadhar(String nominee_aadhar) {
        this.nominee_aadhar = nominee_aadhar;
    }

    public String getNomineeMinor_aadhar() {
        return nomineeMinor_aadhar;
    }

    public void setNomineeMinor_aadhar(String nomineeMinor_aadhar) {
        this.nomineeMinor_aadhar = nomineeMinor_aadhar;
    }

    public String getNomineeMinor_pan() {
        return nomineeMinor_pan;
    }

    public void setNomineeMinor_pan(String nomineeMinor_pan) {
        this.nomineeMinor_pan = nomineeMinor_pan;
    }

    public String[] getNominee_adharArray() {
        return nominee_adharArray;
    }

    public void setNominee_adharArray(String[] nominee_adharArray) {
        this.nominee_adharArray = nominee_adharArray;
    }

    public String[] getNomineeMinor_aadharArray() {
        return nomineeMinor_aadharArray;
    }

    public void setNomineeMinor_aadharArray(String[] nomineeMinor_aadharArray) {
        this.nomineeMinor_aadharArray = nomineeMinor_aadharArray;
    }

    public String[] getNomineeMinor_panArray() {
        return nomineeMinor_panArray;
    }

    public void setNomineeMinor_panArray(String[] nomineeMinor_panArray) {
        this.nomineeMinor_panArray = nomineeMinor_panArray;
    }

    public String getDobNomineeStrng() {
        return dobNomineeStrng;
    }

    public void setDobNomineeStrng(String dobNomineeStrng) {
        this.dobNomineeStrng = dobNomineeStrng;
    }

    public String[] getDeclarationDate() {
        return declarationDate;
    }

    public void setDeclarationDate(String[] declarationDate) {
        this.declarationDate = declarationDate;
    }

    public String[] getPincodeNomineeMinorArray() {
        return pincodeNomineeMinorArray;
    }

    public void setPincodeNomineeMinorArray(String[] pincodeNomineeMinorArray) {
        this.pincodeNomineeMinorArray = pincodeNomineeMinorArray;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String[] getMotherNameArray() {
        return motherNameArray;
    }

    public void setMotherNameArray(String[] motherNameArray) {
        this.motherNameArray = motherNameArray;
    }

    public String[] getMotherSecondNameArray() {
        return motherSecondNameArray;
    }

    public void setMotherSecondNameArray(String[] motherSecondNameArray) {
        this.motherSecondNameArray = motherSecondNameArray;
    }

    public String[] getMotherThirdNameArray() {
        return motherThirdNameArray;
    }

    public void setMotherThirdNameArray(String[] motherThirdNameArray) {
        this.motherThirdNameArray = motherThirdNameArray;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String[] getAddressTypeArray() {
        return addressTypeArray;
    }

    public void setAddressTypeArray(String[] addressTypeArray) {
        this.addressTypeArray = addressTypeArray;
    }

    public String[] getAddressProofOthers() {
        return addressProofOthers;
    }

    public void setAddressProofOthers(String[] addressProofOthers) {
        this.addressProofOthers = addressProofOthers;
    }

    public String getProofOfIdentification() {
        return proofOfIdentification;
    }

    public void setProofOfIdentification(String proofOfIdentification) {
        this.proofOfIdentification = proofOfIdentification;
    }

    public String getProofOfIdentificationMinor() {
        return proofOfIdentificationMinor;
    }

    public void setProofOfIdentificationMinor(String proofOfIdentificationMinor) {
        this.proofOfIdentificationMinor = proofOfIdentificationMinor;
    }

    
    
}
