/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.io.Serializable;

/**
 *
 * @author 09860
 */
public class CompleteTempInvVo implements Serializable {

	//TempInvVo
    private String regId;
    private String email;
    private String firstname;
    private String middlename;
    private String lastname;
    private String fatherSpouse;
    private String motherName;
    private String dob;
    private String nationality;
    private String status;
    private String gender;
    private String mstatus;
    private String uid;
    private String pan;
    private String caddressline1;
    private String caddressline2;
    private String clandmark;
    private String cpincode;
    private String country;
    private String cstate;
    private String ccity;
    private String cproof;
    private String cvalidity;
    private String permenentAddress;
    private String paddressline1;
    private String paddressline2;
    private String plandmark;
    private String ppin;
    private String pcountry;
    private String pstate;
    private String pcity;
    private String pproof;
    private String pvalidity;
    private String mobile;
    private String histd;
    private String hstd;
    private String htelephone;
    private String risd;
    private String rstd;
    private String rtelephone;
    private String fisd;
    private String fstd;
    private String ftelphone;
    private String bankname;
//    private String branch;
    private String accountType;
    private String accno;
    private String reAccno;
    private String bifsc;
    private String bmicr;
    private String baddressline1;
    private String baddressline2;
    private String blandmark;
    private String bpincode;
    private String bcountry;
    private String bstate;
    private String bcity;
    private String cCityOther;
    private String pCityOther;
    private String bnkCityOther;

    private String panFilePath;
    private String corsFilePath;
    private String prmntFilePath;
    
    
    // TempInvNextPageVo
    private String openAccountType;
    private String dp;
    private String tradingtAccount;
    private String dematAccount;
    private String smsFacility;
    private String fstHldrOccup;
    private String fstHldrOrg;
    private String fstHldrDesig;
    private String fstHldrIncome;
    private String fstHldrNet;
    private String fstHldrAmt;
    private String pep;
    private String rpep;
    private boolean scndHldrExist;
    private String scndHldrName;
    private String scndHldrOccup;
    private String scndHldrOrg;
    private String scndHldrDesig;
    private String scndHldrSms;
    private String scndHldrIncome;
    private String scndHldrNet;
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
    private boolean nomineeExist;
    private String nameNominee;
    private String nomineeRelation;
    private String nomineeDob;
    private String nomineeProof;
    private String nomineAadhar;
    private String nominePan;
    private String nomineeAdrs1;
    private String nomineeAdrs2;
    private String nomineeLnd;
    private String nomineePincode;
    private String nomCountry;
    private String nomState;
    private String nomCity;
    private String noisd;
    private String nostd;
    private String notelephone;
    private String nrisd;
    private String nrstd;
    private String nRtelephone;
    private String nfisd;
    private String nfstd;
    private String nomineeFax;
    private String nomMobile;
    private String nomEmail;
    private boolean  minorExist;
    private String minorGuard;
    private String mnrReltn;
    private String mnrDob;
    private String mnrProof;
    private String mnrPan;
    private String mnrAadhar;
    private String mnrAdrs1;
    private String mnrAdrs2;
    private String mnrLnd;
    private String mnrCountry;
    private String mnrState;
    private String mnrCity;
    private String mnrPincode;
    private String moisd;
    private String mostd;
    private String motel;
    private String mrisd;
    private String mrstd;
    private String mrtel;
    private String mfisd;
    private String mfstd;
    private String minorfax;
    private String mnrMob;
    private String mnrEmail;
    private String nomCityOther;
    private String mnrCityOther;
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


    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFatherSpouse() {
        return fatherSpouse;
    }

    public void setFatherSpouse(String fatherSpouse) {
        this.fatherSpouse = fatherSpouse;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMstatus() {
        return mstatus;
    }

    public void setMstatus(String mstatus) {
        this.mstatus = mstatus;
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

    public String getCaddressline1() {
        return caddressline1;
    }

    public void setCaddressline1(String caddressline1) {
        this.caddressline1 = caddressline1;
    }

    public String getCaddressline2() {
        return caddressline2;
    }

    public void setCaddressline2(String caddressline2) {
        this.caddressline2 = caddressline2;
    }

    public String getClandmark() {
        return clandmark;
    }

    public void setClandmark(String clandmark) {
        this.clandmark = clandmark;
    }

    public String getCpincode() {
        return cpincode;
    }

    public void setCpincode(String cpincode) {
        this.cpincode = cpincode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCstate() {
        return cstate;
    }

    public void setCstate(String cstate) {
        this.cstate = cstate;
    }

    public String getCcity() {
        return ccity;
    }

    public void setCcity(String ccity) {
        this.ccity = ccity;
    }

    public String getCproof() {
        return cproof;
    }

    public void setCproof(String cproof) {
        this.cproof = cproof;
    }

    public String getCvalidity() {
        return cvalidity;
    }

    public void setCvalidity(String cvalidity) {
        this.cvalidity = cvalidity;
    }

    public String getPermenentAddress() {
        return permenentAddress;
    }

    public void setPermenentAddress(String permenentAddress) {
        this.permenentAddress = permenentAddress;
    }

    public String getPaddressline1() {
        return paddressline1;
    }

    public void setPaddressline1(String paddressline1) {
        this.paddressline1 = paddressline1;
    }

    public String getPaddressline2() {
        return paddressline2;
    }

    public void setPaddressline2(String paddressline2) {
        this.paddressline2 = paddressline2;
    }

    public String getPlandmark() {
        return plandmark;
    }

    public void setPlandmark(String plandmark) {
        this.plandmark = plandmark;
    }

    public String getPpin() {
        return ppin;
    }

    public void setPpin(String ppin) {
        this.ppin = ppin;
    }

    public String getPcountry() {
        return pcountry;
    }

    public void setPcountry(String pcountry) {
        this.pcountry = pcountry;
    }

    public String getPstate() {
        return pstate;
    }

    public void setPstate(String pstate) {
        this.pstate = pstate;
    }

    public String getPcity() {
        return pcity;
    }

    public void setPcity(String pcity) {
        this.pcity = pcity;
    }

    public String getPproof() {
        return pproof;
    }

    public void setPproof(String pproof) {
        this.pproof = pproof;
    }

    public String getPvalidity() {
        return pvalidity;
    }

    public void setPvalidity(String pvalidity) {
        this.pvalidity = pvalidity;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getHistd() {
        return histd;
    }

    public void setHistd(String histd) {
        this.histd = histd;
    }

    public String getHstd() {
        return hstd;
    }

    public void setHstd(String hstd) {
        this.hstd = hstd;
    }

    public String getHtelephone() {
        return htelephone;
    }

    public void setHtelephone(String htelephone) {
        this.htelephone = htelephone;
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

    public String getRtelephone() {
        return rtelephone;
    }

    public void setRtelephone(String rtelephone) {
        this.rtelephone = rtelephone;
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

    public String getFtelphone() {
        return ftelphone;
    }

    public void setFtelphone(String ftelphone) {
        this.ftelphone = ftelphone;
    }

    public String getBankname() {
        return bankname;
    }

    public void setBankname(String bankname) {
        this.bankname = bankname;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getReAccno() {
        return reAccno;
    }

    public void setReAccno(String reAccno) {
        this.reAccno = reAccno;
    }

    public String getBifsc() {
        return bifsc;
    }

    public void setBifsc(String bifsc) {
        this.bifsc = bifsc;
    }

    public String getBmicr() {
        return bmicr;
    }

    public void setBmicr(String bmicr) {
        this.bmicr = bmicr;
    }

    public String getBaddressline1() {
        return baddressline1;
    }

    public void setBaddressline1(String baddressline1) {
        this.baddressline1 = baddressline1;
    }

    public String getBaddressline2() {
        return baddressline2;
    }

    public void setBaddressline2(String baddressline2) {
        this.baddressline2 = baddressline2;
    }

    public String getBlandmark() {
        return blandmark;
    }

    public void setBlandmark(String blandmark) {
        this.blandmark = blandmark;
    }

    public String getBpincode() {
        return bpincode;
    }

    public void setBpincode(String bpincode) {
        this.bpincode = bpincode;
    }

    public String getBcountry() {
        return bcountry;
    }

    public void setBcountry(String bcountry) {
        this.bcountry = bcountry;
    }

    public String getBstate() {
        return bstate;
    }

    public void setBstate(String bstate) {
        this.bstate = bstate;
    }

    public String getBcity() {
        return bcity;
    }

    public void setBcity(String bcity) {
        this.bcity = bcity;
    }

    public String getcCityOther() {
        return cCityOther;
    }

    public void setcCityOther(String cCityOther) {
        this.cCityOther = cCityOther;
    }

    public String getpCityOther() {
        return pCityOther;
    }

    public void setpCityOther(String pCityOther) {
        this.pCityOther = pCityOther;
    }

    public String getBnkCityOther() {
        return bnkCityOther;
    }

    public void setBnkCityOther(String bnkCityOther) {
        this.bnkCityOther = bnkCityOther;
    }

    public String getPanFilePath() {
        return panFilePath;
    }

    public void setPanFilePath(String panFilePath) {
        this.panFilePath = panFilePath;
    }

    public String getCorsFilePath() {
        return corsFilePath;
    }

    public void setCorsFilePath(String corsFilePath) {
        this.corsFilePath = corsFilePath;
    }

    public String getPrmntFilePath() {
        return prmntFilePath;
    }

    public void setPrmntFilePath(String prmntFilePath) {
        this.prmntFilePath = prmntFilePath;
    }

//    public String getBranch() {
//        return branch;
//    }
//
//    public void setBranch(String branch) {
//        this.branch = branch;
//    }




    public String getOpenAccountType() {
        return openAccountType;
    }

    public void setOpenAccountType(String openAccountType) {
        this.openAccountType = openAccountType;
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getTradingtAccount() {
        return tradingtAccount;
    }

    public void setTradingtAccount(String tradingtAccount) {
        this.tradingtAccount = tradingtAccount;
    }

    public String getDematAccount() {
        return dematAccount;
    }

    public void setDematAccount(String dematAccount) {
        this.dematAccount = dematAccount;
    }

    public String getSmsFacility() {
        return smsFacility;
    }

    public void setSmsFacility(String smsFacility) {
        this.smsFacility = smsFacility;
    }

    public String getFstHldrOccup() {
        return fstHldrOccup;
    }

    public void setFstHldrOccup(String fstHldrOccup) {
        this.fstHldrOccup = fstHldrOccup;
    }

    public String getFstHldrOrg() {
        return fstHldrOrg;
    }

    public void setFstHldrOrg(String fstHldrOrg) {
        this.fstHldrOrg = fstHldrOrg;
    }

    public String getFstHldrDesig() {
        return fstHldrDesig;
    }

    public void setFstHldrDesig(String fstHldrDesig) {
        this.fstHldrDesig = fstHldrDesig;
    }

    public String getFstHldrIncome() {
        return fstHldrIncome;
    }

    public void setFstHldrIncome(String fstHldrIncome) {
        this.fstHldrIncome = fstHldrIncome;
    }

    public String getFstHldrNet() {
        return fstHldrNet;
    }

    public void setFstHldrNet(String fstHldrNet) {
        this.fstHldrNet = fstHldrNet;
    }

    public String getFstHldrAmt() {
        return fstHldrAmt;
    }

    public void setFstHldrAmt(String fstHldrAmt) {
        this.fstHldrAmt = fstHldrAmt;
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

    public String getScndHldrName() {
        return scndHldrName;
    }

    public void setScndHldrName(String scndHldrName) {
        this.scndHldrName = scndHldrName;
    }

    public String getScndHldrOccup() {
        return scndHldrOccup;
    }

    public void setScndHldrOccup(String scndHldrOccup) {
        this.scndHldrOccup = scndHldrOccup;
    }

    public String getScndHldrOrg() {
        return scndHldrOrg;
    }

    public void setScndHldrOrg(String scndHldrOrg) {
        this.scndHldrOrg = scndHldrOrg;
    }

    public String getScndHldrDesig() {
        return scndHldrDesig;
    }

    public void setScndHldrDesig(String scndHldrDesig) {
        this.scndHldrDesig = scndHldrDesig;
    }

    public String getScndHldrSms() {
        return scndHldrSms;
    }

    public void setScndHldrSms(String scndHldrSms) {
        this.scndHldrSms = scndHldrSms;
    }

    public String getScndHldrIncome() {
        return scndHldrIncome;
    }

    public void setScndHldrIncome(String scndHldrIncome) {
        this.scndHldrIncome = scndHldrIncome;
    }

    public String getScndHldrNet() {
        return scndHldrNet;
    }

    public void setScndHldrNet(String scndHldrNet) {
        this.scndHldrNet = scndHldrNet;
    }

    public String getScndHldrAmt() {
        return scndHldrAmt;
    }

    public void setScndHldrAmt(String scndHldrAmt) {
        this.scndHldrAmt = scndHldrAmt;
    }

    public String getScndPep() {
        return scndPep;
    }

    public void setScndPep(String scndPep) {
        this.scndPep = scndPep;
    }

    public String getScndRpep() {
        return scndRpep;
    }

    public void setScndRpep(String scndRpep) {
        this.scndRpep = scndRpep;
    }

    public String getInstrn1() {
        return instrn1;
    }

    public void setInstrn1(String instrn1) {
        this.instrn1 = instrn1;
    }

    public String getInstrn2() {
        return instrn2;
    }

    public void setInstrn2(String instrn2) {
        this.instrn2 = instrn2;
    }

    public String getInstrn3() {
        return instrn3;
    }

    public void setInstrn3(String instrn3) {
        this.instrn3 = instrn3;
    }

    public String getInstrn4() {
        return instrn4;
    }

    public void setInstrn4(String instrn4) {
        this.instrn4 = instrn4;
    }

    public String getInstrn5() {
        return instrn5;
    }

    public void setInstrn5(String instrn5) {
        this.instrn5 = instrn5;
    }

    public String getDepoPartcpnt() {
        return depoPartcpnt;
    }

    public void setDepoPartcpnt(String depoPartcpnt) {
        this.depoPartcpnt = depoPartcpnt;
    }

    public String getDeponame() {
        return deponame;
    }

    public void setDeponame(String deponame) {
        this.deponame = deponame;
    }

    public String getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(String beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getDocEvdnc() {
        return docEvdnc;
    }

    public void setDocEvdnc(String docEvdnc) {
        this.docEvdnc = docEvdnc;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getContractNote() {
        return contractNote;
    }

    public void setContractNote(String contractNote) {
        this.contractNote = contractNote;
    }

    public String getIntrntTrading() {
        return intrntTrading;
    }

    public void setIntrntTrading(String intrntTrading) {
        this.intrntTrading = intrntTrading;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getPanAddtnl() {
        return panAddtnl;
    }

    public void setPanAddtnl(String panAddtnl) {
        this.panAddtnl = panAddtnl;
    }

    public String getOtherInformation() {
        return otherInformation;
    }

    public void setOtherInformation(String otherInformation) {
        this.otherInformation = otherInformation;
    }

    public String getNameNominee() {
        return nameNominee;
    }

    public void setNameNominee(String nameNominee) {
        this.nameNominee = nameNominee;
    }

    public String getNomineeRelation() {
        return nomineeRelation;
    }

    public void setNomineeRelation(String nomineeRelation) {
        this.nomineeRelation = nomineeRelation;
    }

    public String getNomineeDob() {
        return nomineeDob;
    }

    public void setNomineeDob(String nomineeDob) {
        this.nomineeDob = nomineeDob;
    }

    public String getNominePan() {
        return nominePan;
    }

    public void setNominePan(String nominePan) {
        this.nominePan = nominePan;
    }

    public String getNomineeAdrs1() {
        return nomineeAdrs1;
    }

    public void setNomineeAdrs1(String nomineeAdrs1) {
        this.nomineeAdrs1 = nomineeAdrs1;
    }

    public String getNomineeAdrs2() {
        return nomineeAdrs2;
    }

    public void setNomineeAdrs2(String nomineeAdrs2) {
        this.nomineeAdrs2 = nomineeAdrs2;
    }

    public String getNomineeLnd() {
        return nomineeLnd;
    }

    public void setNomineeLnd(String nomineeLnd) {
        this.nomineeLnd = nomineeLnd;
    }

    public String getNomineePincode() {
        return nomineePincode;
    }

    public void setNomineePincode(String nomineePincode) {
        this.nomineePincode = nomineePincode;
    }

    public String getNomCountry() {
        return nomCountry;
    }

    public void setNomCountry(String nomCountry) {
        this.nomCountry = nomCountry;
    }

    public String getNomState() {
        return nomState;
    }

    public void setNomState(String nomState) {
        this.nomState = nomState;
    }

    public String getNomCity() {
        return nomCity;
    }

    public void setNomCity(String nomCity) {
        this.nomCity = nomCity;
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

    public String getNotelephone() {
        return notelephone;
    }

    public void setNotelephone(String notelephone) {
        this.notelephone = notelephone;
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

    public String getnRtelephone() {
        return nRtelephone;
    }

    public void setnRtelephone(String nRtelephone) {
        this.nRtelephone = nRtelephone;
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

    public String getNomineeFax() {
        return nomineeFax;
    }

    public void setNomineeFax(String nomineeFax) {
        this.nomineeFax = nomineeFax;
    }

    public String getNomMobile() {
        return nomMobile;
    }

    public void setNomMobile(String nomMobile) {
        this.nomMobile = nomMobile;
    }

    public String getNomEmail() {
        return nomEmail;
    }

    public void setNomEmail(String nomEmail) {
        this.nomEmail = nomEmail;
    }

    public String getMinorGuard() {
        return minorGuard;
    }

    public void setMinorGuard(String minorGuard) {
        this.minorGuard = minorGuard;
    }

    public String getMnrReltn() {
        return mnrReltn;
    }

    public void setMnrReltn(String mnrReltn) {
        this.mnrReltn = mnrReltn;
    }

    public String getMnrDob() {
        return mnrDob;
    }

    public void setMnrDob(String mnrDob) {
        this.mnrDob = mnrDob;
    }

    public String getMnrAdrs1() {
        return mnrAdrs1;
    }

    public void setMnrAdrs1(String mnrAdrs1) {
        this.mnrAdrs1 = mnrAdrs1;
    }

    public String getMnrAdrs2() {
        return mnrAdrs2;
    }

    public void setMnrAdrs2(String mnrAdrs2) {
        this.mnrAdrs2 = mnrAdrs2;
    }

    public String getMnrLnd() {
        return mnrLnd;
    }

    public void setMnrLnd(String mnrLnd) {
        this.mnrLnd = mnrLnd;
    }

    public String getMnrCountry() {
        return mnrCountry;
    }

    public void setMnrCountry(String mnrCountry) {
        this.mnrCountry = mnrCountry;
    }

    public String getMnrState() {
        return mnrState;
    }

    public void setMnrState(String mnrState) {
        this.mnrState = mnrState;
    }

    public String getMnrCity() {
        return mnrCity;
    }

    public void setMnrCity(String mnrCity) {
        this.mnrCity = mnrCity;
    }

    public String getMnrPincode() {
        return mnrPincode;
    }

    public void setMnrPincode(String mnrPincode) {
        this.mnrPincode = mnrPincode;
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

    public String getMotel() {
        return motel;
    }

    public void setMotel(String motel) {
        this.motel = motel;
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

    public String getMrtel() {
        return mrtel;
    }

    public void setMrtel(String mrtel) {
        this.mrtel = mrtel;
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

    public String getMinorfax() {
        return minorfax;
    }

    public void setMinorfax(String minorfax) {
        this.minorfax = minorfax;
    }

    public String getMnrMob() {
        return mnrMob;
    }

    public void setMnrMob(String mnrMob) {
        this.mnrMob = mnrMob;
    }

    public String getMnrEmail() {
        return mnrEmail;
    }

    public void setMnrEmail(String mnrEmail) {
        this.mnrEmail = mnrEmail;
    }

    public boolean isScndHldrExist() {
        return scndHldrExist;
    }

    public void setScndHldrExist(boolean scndHldrExist) {
        this.scndHldrExist = scndHldrExist;
    }

    public boolean isNomineeExist() {
        return nomineeExist;
    }

    public void setNomineeExist(boolean nomineeExist) {
        this.nomineeExist = nomineeExist;
    }

    public boolean isMinorExist() {
        return minorExist;
    }

    public void setMinorExist(boolean minorExist) {
        this.minorExist = minorExist;
    }

    public String getNomCityOther() {
        return nomCityOther;
    }

    public void setNomCityOther(String nomCityOther) {
        this.nomCityOther = nomCityOther;
    }

    public String getMnrCityOther() {
        return mnrCityOther;
    }

    public void setMnrCityOther(String mnrCityOther) {
        this.mnrCityOther = mnrCityOther;
    }

    public String getDocFilePath() {
        return docFilePath;
    }

    public void setDocFilePath(String docFilePath) {
        this.docFilePath = docFilePath;
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

    public String getFirstHldrDependentUsed() {
        return firstHldrDependentUsed;
    }

    public void setFirstHldrDependentUsed(String firstHldrDependentUsed) {
        this.firstHldrDependentUsed = firstHldrDependentUsed;
    }

    public String getNomineeProof() {
        return nomineeProof;
    }

    public void setNomineeProof(String nomineeProof) {
        this.nomineeProof = nomineeProof;
    }

    public String getNomineAadhar() {
        return nomineAadhar;
    }

    public void setNomineAadhar(String nomineAadhar) {
        this.nomineAadhar = nomineAadhar;
    }

    public String getMnrProof() {
        return mnrProof;
    }

    public void setMnrProof(String mnrProof) {
        this.mnrProof = mnrProof;
    }

    public String getMnrPan() {
        return mnrPan;
    }

    public void setMnrPan(String mnrPan) {
        this.mnrPan = mnrPan;
    }

    public String getMnrAadhar() {
        return mnrAadhar;
    }

    public void setMnrAadhar(String mnrAadhar) {
        this.mnrAadhar = mnrAadhar;
    }
    
    public CompleteTempInvVo() {
		
		this.regId = "null";
		this.email = "null";
		this.firstname = "null";
		this.middlename = "null";
		this.lastname = "null";
		this.fatherSpouse = "null";
		this.dob = "null";
		this.nationality = "IN";
		this.status = "Resident Individual";
		this.gender = "null";
		this.mstatus = "null";
		this.uid = "null";
		this.pan = "null";
		this.caddressline1 = "null";
		this.caddressline2 = "null";
		this.clandmark = "null";
		this.cpincode = "null";
		this.country = "IN";
		this.cstate = "null";
		this.ccity = "null";
		this.cproof = "null";
		this.cvalidity = "null";
		this.permenentAddress = "false";
		this.paddressline1 = "null";
		this.paddressline2 = "null";
		this.plandmark = "null";
		this.ppin = "null";
		this.pcountry = "IN";
		this.pstate = "null";
		this.pcity = "null";
		this.pproof = "null";
		this.pvalidity = "null";
		this.mobile = "null";
		this.histd = "null";
		this.hstd = "null";
		this.htelephone = "null";
		this.risd = "null";
		this.rstd = "null";
		this.rtelephone = "null";
		this.fisd = "null";
		this.fstd = "null";
		this.ftelphone = "null";
		this.bankname = "null";
		this.accountType = "null";
		this.accno = "null";
		this.reAccno = "null";
		this.bifsc = "null";
		this.bmicr = "null";
		this.baddressline1 = "null";
		this.baddressline2 = "null";
		this.blandmark = "null";
		this.bpincode = "null";
		this.bcountry = "IN";
		this.bstate = "null";
		this.bcity = "null";
		this.cCityOther = "null";
		this.pCityOther = "null";
		this.bnkCityOther = "null";
		this.panFilePath = "null";
		this.corsFilePath = "null";
		this.prmntFilePath = "null";
		this.openAccountType = "null";
		this.dp = "null";
		this.tradingtAccount = "null";
		this.dematAccount = "null";
		this.smsFacility = "null";
		this.fstHldrOccup = "null";
		this.fstHldrOrg = "null";
		this.fstHldrDesig = "null";
		this.fstHldrIncome = "null";
		this.fstHldrNet = "null";
		this.fstHldrAmt = "null";
		this.pep = "null";
		this.rpep = "null";
		this.scndHldrExist = false;
		this.scndHldrName = "null";
		this.scndHldrOccup = "null";
		this.scndHldrOrg = "null";
		this.scndHldrDesig = "null";
		this.scndHldrSms = "null";
		this.scndHldrIncome = "null";
		this.scndHldrNet = "null";
		this.scndHldrAmt = "null";
		this.scndPep = "null";
		this.scndRpep = "null";
		this.instrn1 = "null";
		this.instrn2 = "null";
		this.instrn3 = "null";
		this.instrn4 = "null";
		this.instrn5 = "null";
		this.depoPartcpnt = "null";
		this.deponame = "null";
		this.beneficiary = "null";
		this.dpId = "null";
		this.docEvdnc = "null";
		this.other = "null";
		this.experience = "null";
		this.contractNote = "null";
		this.intrntTrading = "null";
		this.alert = "null";
		this.relationship = "null";
		this.panAddtnl = "null";
		this.otherInformation = "null";
		this.nomineeExist = false;
		this.nameNominee = "null";
		this.nomineeRelation = "null";
		this.nomineeDob = "null";
		this.nomineeProof = "null";
		this.nomineAadhar = "null";
		this.nominePan = "null";
		this.nomineeAdrs1 = "null";
		this.nomineeAdrs2 = "null";
		this.nomineeLnd = "null";
		this.nomineePincode = "null";
		this.nomCountry = "IN";
		this.nomState = "null";
		this.nomCity = "null";
		this.noisd = "null";
		this.nostd = "null";
		this.notelephone = "null";
		this.nrisd = "null";
		this.nrstd = "null";
		this.nRtelephone = "null";
		this.nfisd = "null";
		this.nfstd = "null";
		this.nomineeFax = "null";
		this.nomMobile = "null";
		this.nomEmail = "null";
		this.minorExist = false;
		this.minorGuard = "null";
		this.mnrReltn = "null";
		this.mnrDob = "null";
		this.mnrProof = "null";
		this.mnrPan = "null";
		this.mnrAadhar = "null";
		this.mnrAdrs1 = "null";
		this.mnrAdrs2 = "null";
		this.mnrLnd = "null";
		this.mnrCountry = "IN";
		this.mnrState = "null";
		this.mnrCity = "null";
		this.mnrPincode = "null";
		this.moisd = "null";
		this.mostd = "null";
		this.motel = "null";
		this.mrisd = "null";
		this.mrstd = "null";
		this.mrtel = "null";
		this.mfisd = "null";
		this.mfstd = "null";
		this.minorfax = "null";
		this.mnrMob = "null";
		this.mnrEmail = "null";
		this.nomCityOther = "null";
		this.mnrCityOther = "null";
		this.docFilePath = "null";
		this.usNational = "null";
		this.usResident = "null";
		this.usBorn = "null";
		this.usAddress = "null";
		this.usTelephone = "null";
		this.usStandingInstruction = "null";
		this.usPoa = "null";
		this.usMailAddress = "null";
		this.individualTaxIdntfcnNmbr = "null";
		this.secondHldrPan = "null";
		this.secondHldrDependentRelation = "null";
		this.secondHldrDependentUsed = "null";
		this.firstHldrDependentUsed = "null";
		this.motherName = "null";
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

}
