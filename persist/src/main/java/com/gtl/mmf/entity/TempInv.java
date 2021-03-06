package com.gtl.mmf.entity;
// Generated 14 Nov, 2015 10:56:57 AM by Hibernate Tools 3.6.0

import java.io.Serializable;
import java.util.Date;

/**
 * TempInv generated by hbm2java
 */
public class TempInv implements Serializable {

    private Integer id;
    private String registrationId;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String fatherSpouse;
    private Date dob;
    private String nationality;
    private String status;
    private String gender;
    private String mstatus;
    private String uid;
    private String pan;
    private String caddress;
    private String cpincode;
    private String country;
    private String cstate;
    private String ccity;
    private String cproof;
    private Date cvalidity;
    private Boolean permenentAddress;
    private String paddress;
    private String ppin;
    private String pcountry;
    private String pstate;
    private String pcity;
    private String pproof;
    private Date pvalidity;
    private String mobile;
    private String htelephone;
    private String rtelephone;
    private String ftelphone;
    private String bankname;
//     private String branch;
    private String accountType;
    private String accno;
    private String reAccno;
    private String bifsc;
    private String bmicr;
    private String baddress;
    private String bpincode;
    private String bcountry;
    private String bstate;
    private String bcity;
    private String openAccountType;
    private String dp;
    private String tradingtAccount;
    private String dematAccount;
    private String smsFacility;
    private String fstHldrOccup;
    private String fstHldrOrg;
    private String fstHldrDesig;
    private String fstHldrIncome;
    private Date fstHldrNet;
    private String fstHldrAmt;
    private String pep;
    private String rpep;
    private Boolean scndHldrExist;
    private String scndHldrName;
    private String scndHldrOccup;
    private String scndHldrOrg;
    private String scndHldrDesig;
    private String scndHldrSms;
    private String scndHldrIncome;
    private Date scndHldrNet;
    private String scndHldrAmt;
    private String scndPep;
    private String scndRpep;
    private String instrn1;
    private String instrn2;
    private String instrn3;
    private String instrn4;
    private String instrn5;
    private String depoPartcpnt;
    private String deponame;
    private String beneficiary;
    private String dpId;
    private String docEvdnc;
    private String other;
    private String experience;
    private String contractNote;
    private String intrntTrading;
    private String alert;
    private String relationship;
    private String panAddtnl;
    private String otherInformation;
    private Boolean nomineeExist;
    private String nameNominee;
    private String nomineeRelation;
    private Date nomineeDob;
    private String nominePan;
    private String nomineeAddress;
    private String nomineePincode;
    private String nomCountry;
    private String nomState;
    private String nomCity;
    private String notelephone;
    private String nrtelephone;
    private String nomineeFax;
    private String nomMobile;
    private String nomEmail;
    private Boolean minorExist;
    private String minorGuard;
    private String mnrReltn;
    private Date mnrDob;
    private String minorAddress;
    private String mnrCountry;
    private String mnrState;
    private String mnrCity;
    private String mnrPincode;
    private String minortel;
    private String minrRestel;
    private String minorfax;
    private String mnrMob;
    private String mnrEmail;
    private String pcityOther;
    private String ccityOther;
    private String bnkCityOther;
    private String nomCityOther;
    private String minorCityother;
    private String panFilePath;
    private String coresFilePath;
    private String prmntFilePath;
    private String docFilePath;
    private String usNational;
    private String usResident;
    private String usBorn;
    private String usAddress;
    private String usTelephone;
    private String usStandingInstruction;
    private String usPoa;
    private String usMailAddress;
    private String individualTaxIdntfcnNmbr;
    private String secondHldrPan;
    private String secondHldrDependentRelation;
    private String secondHldrDependentUsed;
    private String firstHldrDependentUsed;
    private Integer kitNumber;
    private String nomineeProof;
    private String nomineeAadhar;
    private String minorProof;
    private String minorPan;
    private String minorAadhar;
    private String motherName;
    
    public TempInv() {
    }

    public TempInv(String registrationId, String email, String firstname, String middlename, String lastname, String fatherSpouse, Date dob, String nationality, String status, String gender, String mstatus, String uid, String pan, String caddress, String cpincode, String country, String cstate, String ccity, String cproof, Date cvalidity, Boolean permenentAddress, String paddress, String ppin, String pcountry, String pstate, String pcity, String pproof, Date pvalidity, String mobile, String htelephone, String rtelephone, String ftelphone, String bankname, /*String branch,*/ String accountType, String accno, String reAccno, String bifsc, String bmicr, String baddress, String bpincode, String bcountry, String bstate, String bcity, String openAccountType, String dp, String tradingtAccount, String dematAccount, String smsFacility, String fstHldrOccup, String fstHldrOrg, String fstHldrDesig, String fstHldrIncome, Date fstHldrNet, String fstHldrAmt, String pep, String rpep, Boolean scndHldrExist, String scndHldrName, String scndHldrOccup, String scndHldrOrg, String scndHldrDesig, String scndHldrSms, String scndHldrIncome, Date scndHldrNet, String scndHldrAmt, String scndPep, String scndRpep, String instrn1, String instrn2, String instrn3, String instrn4, String instrn5, String depoPartcpnt, String deponame, String beneficiary, String dpId, String docEvdnc, String other, String experience, String contractNote, String intrntTrading, String alert, String relationship, String panAddtnl, String otherInformation, Boolean nomineeExist, String nameNominee, String nomineeRelation, Date nomineeDob, String nominePan, String nomineeAddress, String nomineePincode, String nomCountry, String nomState, String nomCity, String notelephone, String nrtelephone, String nomineeFax, String nomMobile, String nomEmail, Boolean minorExist, String minorGuard, String mnrReltn, Date mnrDob, String minorAddress, String mnrCountry, String mnrState, String mnrCity, String mnrPincode, String minortel, String minrRestel, String minorfax, String mnrMob, String mnrEmail, String pcityOther, String ccityOther, String bnkCityOther, String nomCityOther, String minorCityother, String panFilePath, String coresFilePath, String prmntFilePath, String docFilePath, String usNational, String usResident, String usBorn, String usAddress, String usTelephone, String usStandingInstruction, String usPoa, String usMailAddress, String individualTaxIdntfcnNmbr, String secondHldrPan, String secondHldrDependentRelation, String secondHldrDependentUsed, String firstHldrDependentUsed, Integer kitNumber, String nomineeProof, String nomineeAadhar, String minorProof, String minorPan, String minorAadharm , String motherName) {
        this.registrationId = registrationId;
        this.email = email;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.fatherSpouse = fatherSpouse;
        this.dob = dob;
        this.nationality = nationality;
        this.status = status;
        this.gender = gender;
        this.mstatus = mstatus;
        this.uid = uid;
        this.pan = pan;
        this.caddress = caddress;
        this.cpincode = cpincode;
        this.country = country;
        this.cstate = cstate;
        this.ccity = ccity;
        this.cproof = cproof;
        this.cvalidity = cvalidity;
        this.permenentAddress = permenentAddress;
        this.paddress = paddress;
        this.ppin = ppin;
        this.pcountry = pcountry;
        this.pstate = pstate;
        this.pcity = pcity;
        this.pproof = pproof;
        this.pvalidity = pvalidity;
        this.mobile = mobile;
        this.htelephone = htelephone;
        this.rtelephone = rtelephone;
        this.ftelphone = ftelphone;
        this.bankname = bankname;
//       this.branch = branch;
        this.accountType = accountType;
        this.accno = accno;
        this.reAccno = reAccno;
        this.bifsc = bifsc;
        this.bmicr = bmicr;
        this.baddress = baddress;
        this.bpincode = bpincode;
        this.bcountry = bcountry;
        this.bstate = bstate;
        this.bcity = bcity;
        this.openAccountType = openAccountType;
        this.dp = dp;
        this.tradingtAccount = tradingtAccount;
        this.dematAccount = dematAccount;
        this.smsFacility = smsFacility;
        this.fstHldrOccup = fstHldrOccup;
        this.fstHldrOrg = fstHldrOrg;
        this.fstHldrDesig = fstHldrDesig;
        this.fstHldrIncome = fstHldrIncome;
        this.fstHldrNet = fstHldrNet;
        this.fstHldrAmt = fstHldrAmt;
        this.pep = pep;
        this.rpep = rpep;
        this.scndHldrExist = scndHldrExist;
        this.scndHldrName = scndHldrName;
        this.scndHldrOccup = scndHldrOccup;
        this.scndHldrOrg = scndHldrOrg;
        this.scndHldrDesig = scndHldrDesig;
        this.scndHldrSms = scndHldrSms;
        this.scndHldrIncome = scndHldrIncome;
        this.scndHldrNet = scndHldrNet;
        this.scndHldrAmt = scndHldrAmt;
        this.scndPep = scndPep;
        this.scndRpep = scndRpep;
        this.instrn1 = instrn1;
        this.instrn2 = instrn2;
        this.instrn3 = instrn3;
        this.instrn4 = instrn4;
        this.instrn5 = instrn5;
        this.depoPartcpnt = depoPartcpnt;
        this.deponame = deponame;
        this.beneficiary = beneficiary;
        this.dpId = dpId;
        this.docEvdnc = docEvdnc;
        this.other = other;
        this.experience = experience;
        this.contractNote = contractNote;
        this.intrntTrading = intrntTrading;
        this.alert = alert;
        this.relationship = relationship;
        this.panAddtnl = panAddtnl;
        this.otherInformation = otherInformation;
        this.nomineeExist = nomineeExist;
        this.nameNominee = nameNominee;
        this.nomineeRelation = nomineeRelation;
        this.nomineeDob = nomineeDob;
        this.nominePan = nominePan;
        this.nomineeAddress = nomineeAddress;
        this.nomineePincode = nomineePincode;
        this.nomCountry = nomCountry;
        this.nomState = nomState;
        this.nomCity = nomCity;
        this.notelephone = notelephone;
        this.nrtelephone = nrtelephone;
        this.nomineeFax = nomineeFax;
        this.nomMobile = nomMobile;
        this.nomEmail = nomEmail;
        this.minorExist = minorExist;
        this.minorGuard = minorGuard;
        this.mnrReltn = mnrReltn;
        this.mnrDob = mnrDob;
        this.minorAddress = minorAddress;
        this.mnrCountry = mnrCountry;
        this.mnrState = mnrState;
        this.mnrCity = mnrCity;
        this.mnrPincode = mnrPincode;
        this.minortel = minortel;
        this.minrRestel = minrRestel;
        this.minorfax = minorfax;
        this.mnrMob = mnrMob;
        this.mnrEmail = mnrEmail;
        this.pcityOther = pcityOther;
        this.ccityOther = ccityOther;
        this.bnkCityOther = bnkCityOther;
        this.nomCityOther = nomCityOther;
        this.minorCityother = minorCityother;
        this.panFilePath = panFilePath;
        this.coresFilePath = coresFilePath;
        this.prmntFilePath = prmntFilePath;
        this.docFilePath = docFilePath;
        this.usNational = usNational;
        this.usResident = usResident;
        this.usBorn = usBorn;
        this.usAddress = usAddress;
        this.usTelephone = usTelephone;
        this.usStandingInstruction = usStandingInstruction;
        this.usPoa = usPoa;
        this.usMailAddress = usMailAddress;
        this.individualTaxIdntfcnNmbr = individualTaxIdntfcnNmbr;
        this.secondHldrPan = secondHldrPan;
        this.secondHldrDependentRelation = secondHldrDependentRelation;
        this.secondHldrDependentUsed = secondHldrDependentUsed;
        this.firstHldrDependentUsed = firstHldrDependentUsed;
        this.kitNumber = kitNumber;
        this.nomineeProof = nomineeProof;
        this.nomineeAadhar = nomineeAadhar;
        this.minorProof = minorProof;
        this.minorPan = minorPan;
        this.minorAadhar = minorAadhar;
        this.motherName =  motherName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRegistrationId() {
        return this.registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return this.middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFatherSpouse() {
        return this.fatherSpouse;
    }

    public void setFatherSpouse(String fatherSpouse) {
        this.fatherSpouse = fatherSpouse;
    }

    public Date getDob() {
        return this.dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return this.nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return this.gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMstatus() {
        return this.mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
    }

    public String getUid() {
        return this.uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPan() {
        return this.pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getCaddress() {
        return this.caddress;
    }

    public void setCaddress(String caddress) {
        this.caddress = caddress;
    }

    public String getCpincode() {
        return this.cpincode;
    }

    public void setCpincode(String cpincode) {
        this.cpincode = cpincode;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCstate() {
        return this.cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String getCcity() {
        return this.ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String getCproof() {
        return this.cproof;
    }

    public void setCproof(String cproof) {
        this.cproof = cproof;
    }

    public Date getCvalidity() {
        return this.cvalidity;
    }

    public void setCvalidity(Date cvalidity) {
        this.cvalidity = cvalidity;
    }

    public Boolean getPermenentAddress() {
        return this.permenentAddress;
    }

    public void setPermenentAddress(Boolean permenentAddress) {
        this.permenentAddress = permenentAddress;
    }

    public String getPaddress() {
        return this.paddress;
    }

    public void setPaddress(String paddress) {
        this.paddress = paddress;
    }

    public String getPpin() {
        return this.ppin;
    }

    public void setPpin(String ppin) {
        this.ppin = ppin;
    }

    public String getPcountry() {
        return this.pcountry;
    }

    public void setPcountry(String pcountry) {
        this.pcountry = pcountry;
    }

    public String getPstate() {
        return this.pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPcity() {
        return this.pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

    public String getPproof() {
        return this.pproof;
    }

    public void setPproof(String pproof) {
        this.pproof = pproof;
    }

    public Date getPvalidity() {
        return this.pvalidity;
    }

    public void setPvalidity(Date pvalidity) {
        this.pvalidity = pvalidity;
    }

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHtelephone() {
        return this.htelephone;
    }

    public void setHtelephone(String htelephone) {
        this.htelephone = htelephone;
    }

    public String getRtelephone() {
        return this.rtelephone;
    }

    public void setRtelephone(String rtelephone) {
        this.rtelephone = rtelephone;
    }

    public String getFtelphone() {
        return this.ftelphone;
    }

    public void setFtelphone(String ftelphone) {
        this.ftelphone = ftelphone;
    }

    public String getBankname() {
        return this.bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountType() {
        return this.accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccno() {
        return this.accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getReAccno() {
        return this.reAccno;
    }

    public void setReAccno(String reAccno) {
        this.reAccno = reAccno;
    }

    public String getBifsc() {
        return this.bifsc;
    }

    public void setBifsc(String bifsc) {
        this.bifsc = bifsc;
    }

    public String getBmicr() {
        return this.bmicr;
    }

    public void setBmicr(String bmicr) {
        this.bmicr = bmicr;
    }

    public String getBaddress() {
        return this.baddress;
    }

    public void setBaddress(String baddress) {
        this.baddress = baddress;
    }

    public String getBpincode() {
        return this.bpincode;
    }

    public void setBpincode(String bpincode) {
        this.bpincode = bpincode;
    }

    public String getBcountry() {
        return this.bcountry;
    }

    public void setBcountry(String bcountry) {
        this.bcountry = bcountry;
    }

    public String getBstate() {
        return this.bstate;
    }

    public void setBstate(String bstate) {
        this.bstate = bstate;
    }

    public String getBcity() {
        return this.bcity;
    }

    public void setBcity(String bcity) {
        this.bcity = bcity;
    }

    public String getOpenAccountType() {
        return this.openAccountType;
    }

    public void setOpenAccountType(String openAccountType) {
        this.openAccountType = openAccountType;
    }

    public String getDp() {
        return this.dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getTradingtAccount() {
        return this.tradingtAccount;
    }

    public void setTradingtAccount(String tradingtAccount) {
        this.tradingtAccount = tradingtAccount;
    }

    public String getDematAccount() {
        return this.dematAccount;
    }

    public void setDematAccount(String dematAccount) {
        this.dematAccount = dematAccount;
    }

    public String getSmsFacility() {
        return this.smsFacility;
    }

    public void setSmsFacility(String smsFacility) {
        this.smsFacility = smsFacility;
    }

    public String getFstHldrOccup() {
        return this.fstHldrOccup;
    }

    public void setFstHldrOccup(String fstHldrOccup) {
        this.fstHldrOccup = fstHldrOccup;
    }

    public String getFstHldrOrg() {
        return this.fstHldrOrg;
    }

    public void setFstHldrOrg(String fstHldrOrg) {
        this.fstHldrOrg = fstHldrOrg;
    }

    public String getFstHldrDesig() {
        return this.fstHldrDesig;
    }

    public void setFstHldrDesig(String fstHldrDesig) {
        this.fstHldrDesig = fstHldrDesig;
    }

    public String getFstHldrIncome() {
        return this.fstHldrIncome;
    }

    public void setFstHldrIncome(String fstHldrIncome) {
        this.fstHldrIncome = fstHldrIncome;
    }

    public Date getFstHldrNet() {
        return this.fstHldrNet;
    }

    public void setFstHldrNet(Date fstHldrNet) {
        this.fstHldrNet = fstHldrNet;
    }

    public String getFstHldrAmt() {
        return this.fstHldrAmt;
    }

    public void setFstHldrAmt(String fstHldrAmt) {
        this.fstHldrAmt = fstHldrAmt;
    }

    public String getPep() {
        return this.pep;
    }

    public void setPep(String pep) {
        this.pep = pep;
    }

    public String getRpep() {
        return this.rpep;
    }

    public void setRpep(String rpep) {
        this.rpep = rpep;
    }

    public Boolean getScndHldrExist() {
        return this.scndHldrExist;
    }

    public void setScndHldrExist(Boolean scndHldrExist) {
        this.scndHldrExist = scndHldrExist;
    }

    public String getScndHldrName() {
        return this.scndHldrName;
    }

    public void setScndHldrName(String scndHldrName) {
        this.scndHldrName = scndHldrName;
    }

    public String getScndHldrOccup() {
        return this.scndHldrOccup;
    }

    public void setScndHldrOccup(String scndHldrOccup) {
        this.scndHldrOccup = scndHldrOccup;
    }

    public String getScndHldrOrg() {
        return this.scndHldrOrg;
    }

    public void setScndHldrOrg(String scndHldrOrg) {
        this.scndHldrOrg = scndHldrOrg;
    }

    public String getScndHldrDesig() {
        return this.scndHldrDesig;
    }

    public void setScndHldrDesig(String scndHldrDesig) {
        this.scndHldrDesig = scndHldrDesig;
    }

    public String getScndHldrSms() {
        return this.scndHldrSms;
    }

    public void setScndHldrSms(String scndHldrSms) {
        this.scndHldrSms = scndHldrSms;
    }

    public String getScndHldrIncome() {
        return this.scndHldrIncome;
    }

    public void setScndHldrIncome(String scndHldrIncome) {
        this.scndHldrIncome = scndHldrIncome;
    }

    public Date getScndHldrNet() {
        return this.scndHldrNet;
    }

    public void setScndHldrNet(Date scndHldrNet) {
        this.scndHldrNet = scndHldrNet;
    }

    public String getScndHldrAmt() {
        return this.scndHldrAmt;
    }

    public void setScndHldrAmt(String scndHldrAmt) {
        this.scndHldrAmt = scndHldrAmt;
    }

    public String getScndPep() {
        return this.scndPep;
    }

    public void setScndPep(String scndPep) {
        this.scndPep = scndPep;
    }

    public String getScndRpep() {
        return this.scndRpep;
    }

    public void setScndRpep(String scndRpep) {
        this.scndRpep = scndRpep;
    }

    public String getInstrn1() {
        return this.instrn1;
    }

    public void setInstrn1(String instrn1) {
        this.instrn1 = instrn1;
    }

    public String getInstrn2() {
        return this.instrn2;
    }

    public void setInstrn2(String instrn2) {
        this.instrn2 = instrn2;
    }

    public String getInstrn3() {
        return this.instrn3;
    }

    public void setInstrn3(String instrn3) {
        this.instrn3 = instrn3;
    }

    public String getInstrn4() {
        return this.instrn4;
    }

    public void setInstrn4(String instrn4) {
        this.instrn4 = instrn4;
    }

    public String getInstrn5() {
        return this.instrn5;
    }

    public void setInstrn5(String instrn5) {
        this.instrn5 = instrn5;
    }

    public String getDepoPartcpnt() {
        return this.depoPartcpnt;
    }

    public void setDepoPartcpnt(String depoPartcpnt) {
        this.depoPartcpnt = depoPartcpnt;
    }

    public String getDeponame() {
        return this.deponame;
    }

    public void setDeponame(String deponame) {
        this.deponame = deponame;
    }

    public String getBeneficiary() {
        return this.beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getDpId() {
        return this.dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDocEvdnc() {
        return this.docEvdnc;
    }

    public void setDocEvdnc(String docEvdnc) {
        this.docEvdnc = docEvdnc;
    }

    public String getOther() {
        return this.other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getExperience() {
        return this.experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getContractNote() {
        return this.contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote;
    }

    public String getIntrntTrading() {
        return this.intrntTrading;
    }

    public void setIntrntTrading(String intrntTrading) {
        this.intrntTrading = intrntTrading;
    }

    public String getAlert() {
        return this.alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getRelationship() {
        return this.relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPanAddtnl() {
        return this.panAddtnl;
    }

    public void setPanAddtnl(String panAddtnl) {
        this.panAddtnl = panAddtnl;
    }

    public String getOtherInformation() {
        return this.otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public Boolean getNomineeExist() {
        return this.nomineeExist;
    }

    public void setNomineeExist(Boolean nomineeExist) {
        this.nomineeExist = nomineeExist;
    }

    public String getNameNominee() {
        return this.nameNominee;
    }

    public void setNameNominee(String nameNominee) {
        this.nameNominee = nameNominee;
    }

    public String getNomineeRelation() {
        return this.nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public Date getNomineeDob() {
        return this.nomineeDob;
    }

    public void setNomineeDob(Date nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getNominePan() {
        return this.nominePan;
    }

    public void setNominePan(String nominePan) {
        this.nominePan = nominePan;
    }

    public String getNomineeAddress() {
        return this.nomineeAddress;
    }

    public void setNomineeAddress(String nomineeAddress) {
        this.nomineeAddress = nomineeAddress;
    }

    public String getNomineePincode() {
        return this.nomineePincode;
    }

    public void setNomineePincode(String nomineePincode) {
        this.nomineePincode = nomineePincode;
    }

    public String getNomCountry() {
        return this.nomCountry;
    }

    public void setNomCountry(String nomCountry) {
        this.nomCountry = nomCountry;
    }

    public String getNomState() {
        return this.nomState;
    }

    public void setNomState(String nomState) {
        this.nomState = nomState;
    }

    public String getNomCity() {
        return this.nomCity;
    }

    public void setNomCity(String nomCity) {
        this.nomCity = nomCity;
    }

    public String getNotelephone() {
        return this.notelephone;
    }

    public void setNotelephone(String notelephone) {
        this.notelephone = notelephone;
    }

    public String getNrtelephone() {
        return this.nrtelephone;
    }

    public void setNrtelephone(String nrtelephone) {
        this.nrtelephone = nrtelephone;
    }

    public String getNomineeFax() {
        return this.nomineeFax;
    }

    public void setNomineeFax(String nomineeFax) {
        this.nomineeFax = nomineeFax;
    }

    public String getNomMobile() {
        return this.nomMobile;
    }

    public void setNomMobile(String nomMobile) {
        this.nomMobile = nomMobile;
    }

    public String getNomEmail() {
        return this.nomEmail;
    }

    public void setNomEmail(String nomEmail) {
        this.nomEmail = nomEmail;
    }

    public Boolean getMinorExist() {
        return this.minorExist;
    }

    public void setMinorExist(Boolean minorExist) {
        this.minorExist = minorExist;
    }

    public String getMinorGuard() {
        return this.minorGuard;
    }

    public void setMinorGuard(String minorGuard) {
        this.minorGuard = minorGuard;
    }

    public String getMnrReltn() {
        return this.mnrReltn;
    }

    public void setMnrReltn(String mnrReltn) {
        this.mnrReltn = mnrReltn;
    }

    public Date getMnrDob() {
        return this.mnrDob;
    }

    public void setMnrDob(Date mnrDob) {
        this.mnrDob = mnrDob;
    }

    public String getMinorAddress() {
        return this.minorAddress;
    }

    public void setMinorAddress(String minorAddress) {
        this.minorAddress = minorAddress;
    }

    public String getMnrCountry() {
        return this.mnrCountry;
    }

    public void setMnrCountry(String mnrCountry) {
        this.mnrCountry = mnrCountry;
    }

    public String getMnrState() {
        return this.mnrState;
    }

    public void setMnrState(String mnrState) {
        this.mnrState = mnrState;
    }

    public String getMnrCity() {
        return this.mnrCity;
    }

    public void setMnrCity(String mnrCity) {
        this.mnrCity = mnrCity;
    }

    public String getMnrPincode() {
        return this.mnrPincode;
    }

    public void setMnrPincode(String mnrPincode) {
        this.mnrPincode = mnrPincode;
    }

    public String getMinortel() {
        return this.minortel;
    }

    public void setMinortel(String minortel) {
        this.minortel = minortel;
    }

    public String getMinrRestel() {
        return this.minrRestel;
    }

    public void setMinrRestel(String minrRestel) {
        this.minrRestel = minrRestel;
    }

    public String getMinorfax() {
        return this.minorfax;
    }

    public void setMinorfax(String minorfax) {
        this.minorfax = minorfax;
    }

    public String getMnrMob() {
        return this.mnrMob;
    }

    public void setMnrMob(String mnrMob) {
        this.mnrMob = mnrMob;
    }

    public String getMnrEmail() {
        return this.mnrEmail;
    }

    public void setMnrEmail(String mnrEmail) {
        this.mnrEmail = mnrEmail;
    }

    public String getPcityOther() {
        return this.pcityOther;
    }

    public void setPcityOther(String pcityOther) {
        this.pcityOther = pcityOther;
    }

    public String getCcityOther() {
        return this.ccityOther;
    }

    public void setCcityOther(String ccityOther) {
        this.ccityOther = ccityOther;
    }

    public String getBnkCityOther() {
        return this.bnkCityOther;
    }

    public void setBnkCityOther(String bnkCityOther) {
        this.bnkCityOther = bnkCityOther;
    }

    public String getNomCityOther() {
        return this.nomCityOther;
    }

    public void setNomCityOther(String nomCityOther) {
        this.nomCityOther = nomCityOther;
    }

    public String getMinorCityother() {
        return this.minorCityother;
    }

    public void setMinorCityother(String minorCityother) {
        this.minorCityother = minorCityother;
    }

    public String getPanFilePath() {
        return this.panFilePath;
    }

    public void setPanFilePath(String panFilePath) {
        this.panFilePath = panFilePath;
    }

    public String getCoresFilePath() {
        return this.coresFilePath;
    }

    public void setCoresFilePath(String coresFilePath) {
        this.coresFilePath = coresFilePath;
    }

    public String getPrmntFilePath() {
        return this.prmntFilePath;
    }

    public void setPrmntFilePath(String prmntFilePath) {
        this.prmntFilePath = prmntFilePath;
    }

    public String getDocFilePath() {
        return this.docFilePath;
    }

    public void setDocFilePath(String docFilePath) {
        this.docFilePath = docFilePath;
    }

    public String getUsNational() {
        return this.usNational;
    }

    public void setUsNational(String usNational) {
        this.usNational = usNational;
    }

    public String getUsResident() {
        return this.usResident;
    }

    public void setUsResident(String usResident) {
        this.usResident = usResident;
    }

    public String getUsBorn() {
        return this.usBorn;
    }

    public void setUsBorn(String usBorn) {
        this.usBorn = usBorn;
    }

    public String getUsAddress() {
        return this.usAddress;
    }

    public void setUsAddress(String usAddress) {
        this.usAddress = usAddress;
    }

    public String getUsTelephone() {
        return this.usTelephone;
    }

    public void setUsTelephone(String usTelephone) {
        this.usTelephone = usTelephone;
    }

    public String getUsStandingInstruction() {
        return this.usStandingInstruction;
    }

    public void setUsStandingInstruction(String usStandingInstruction) {
        this.usStandingInstruction = usStandingInstruction;
    }

    public String getUsPoa() {
        return this.usPoa;
    }

    public void setUsPoa(String usPoa) {
        this.usPoa = usPoa;
    }

    public String getUsMailAddress() {
        return this.usMailAddress;
    }

    public void setUsMailAddress(String usMailAddress) {
        this.usMailAddress = usMailAddress;
    }

    public String getIndividualTaxIdntfcnNmbr() {
        return this.individualTaxIdntfcnNmbr;
    }

    public void setIndividualTaxIdntfcnNmbr(String individualTaxIdntfcnNmbr) {
        this.individualTaxIdntfcnNmbr = individualTaxIdntfcnNmbr;
    }

    public String getSecondHldrPan() {
        return this.secondHldrPan;
    }

    public void setSecondHldrPan(String secondHldrPan) {
        this.secondHldrPan = secondHldrPan;
    }

    public String getSecondHldrDependentRelation() {
        return this.secondHldrDependentRelation;
    }

    public void setSecondHldrDependentRelation(String secondHldrDependentRelation) {
        this.secondHldrDependentRelation = secondHldrDependentRelation;
    }

    public String getSecondHldrDependentUsed() {
        return this.secondHldrDependentUsed;
    }

    public void setSecondHldrDependentUsed(String secondHldrDependentUsed) {
        this.secondHldrDependentUsed = secondHldrDependentUsed;
    }

    public String getFirstHldrDependentUsed() {
        return this.firstHldrDependentUsed;
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

//    public String getBranch() {
//        return branch;
//    }
//
//    public void setBranch(String branch) {
//        this.branch = branch;
//    }
    public String getNomineeProof() {
        return nomineeProof;
    }

    public void setNomineeProof(String nomineeProof) {
        this.nomineeProof = nomineeProof;
    }

    public String getNomineeAadhar() {
        return nomineeAadhar;
    }

    public void setNomineeAadhar(String nomineeAadhar) {
        this.nomineeAadhar = nomineeAadhar;
    }

    public String getMinorProof() {
        return minorProof;
    }

    public void setMinorProof(String minorProof) {
        this.minorProof = minorProof;
    }

    public String getMinorPan() {
        return minorPan;
    }

    public void setMinorPan(String minorPan) {
        this.minorPan = minorPan;
    }

    public String getMinorAadhar() {
        return minorAadhar;
    }

    public void setMinorAadhar(String minorAadhar) {
        this.minorAadhar = minorAadhar;
    }

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}
    
    
}
