/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

/**
 *
 * @author 09607
 */
public class AdvisorRegistrationVo {

    private String regNO;
    private String fname;
    private String middle_name;
    private String last_name;
    private String sebi_regno;
    private Date sebi_validity;
    private String pan;
    private Date dob;
    private String certificateFilePath;
    private String regEmail;

    private String raddressLine1;
    private String raddressLine2;
    private String rlandMark;
    private String rpincode;
    private String rcountry;
    private String rstate;
    private String rcity;
    private String rmobile;
    private String remail;
    private String risd;
    private String rstd;
    private String rtnumber;

    private String organization;
    private String jobTitle;
    private boolean samePermentaddr;

    private String oaddressLine1;
    private String oaddressLine2;
    private String olandMark;
    private String opincode;
    private String ocountry;
    private String ostate;
    private String ocity;
    private String omobile;
    private String oemail;
    private String oisd;
    private String ostd;
    private String otnumber;

    private String correspondenceAddress;
    private String bankName;
    private String accountType;
    private String accountNumber;
    private String raccountNumber;
    private String ifscNo;
    private String micrNo;
    private String branch;

    private String baddressLine1;
    private String baddressLine2;
    private String blandMark;
    private String bpincode;
    private String bcountry;
    private String bstate;
    private String bcity;

    private String pqualification;
    private String pinsititute;
    private String pyear;
    private String pqualificationId;

    private String squalification;
    private String sinsititute;

    private String syear;
    private String squalificationId;

    private String tqualification;
    private String tinsititute;
    private String tyear;
    private String tqualificationId;
    private boolean aggrementAccepted;
    private int userStatus;
    private boolean isAdvisor;

    private String access_token;
    private String expire_in;
    private String linkedin_member_id;
    private String linkedin_user;
    private String pictureUrl;
    private boolean linkedInConnected;
    private Date expire_in_date;
    Integer Id;
    // for other city
    private String off_city_other;
    private String res_city_other;
    private String bnk_city_other;
    private String sebiPath;
    
    private boolean individualOrCoprt = true;

    private String advPicPath;
    private String oneLineDesc;
    private String aboutMe;
    private String myInvestmentStrategy;
    
    public String getRegNO() {
        return regNO;
    }

    public void setRegNO(String regNO) {
        this.regNO = regNO;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMiddle_name() {
        return middle_name;
    }

    public boolean isIsAdvisor() {
        return isAdvisor;
    }

    public void setIsAdvisor(boolean isAdvisor) {
        this.isAdvisor = isAdvisor;
    }

    public void setMiddle_name(String middle_name) {
        this.middle_name = middle_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getRegEmail() {
        return regEmail;
    }

    public void setRegEmail(String regEmail) {
        this.regEmail = regEmail;
    }

    public Date getExpire_in_date() {
        return expire_in_date;
    }

    public void setExpire_in_date(Date expire_in_date) {
        this.expire_in_date = expire_in_date;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getSebi_regno() {
        return sebi_regno;
    }

    public void setSebi_regno(String sebi_regno) {
        this.sebi_regno = sebi_regno;
    }

    public Date getSebi_validity() {
        return sebi_validity;
    }

    public void setSebi_validity(Date sebi_validity) {
        this.sebi_validity = sebi_validity;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getCertificateFilePath() {
        return certificateFilePath;
    }

    public void setCertificateFilePath(String certificateFilePath) {
        this.certificateFilePath = certificateFilePath;
    }

    public String getRaddressLine1() {
        return raddressLine1;
    }

    public void setRaddressLine1(String raddressLine1) {
        this.raddressLine1 = raddressLine1;
    }

    public String getRaddressLine2() {
        return raddressLine2;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer Id) {
        this.Id = Id;
    }

    public void setRaddressLine2(String raddressLine2) {
        this.raddressLine2 = raddressLine2;
    }

    public String getRlandMark() {
        return rlandMark;
    }

    public void setRlandMark(String rlandMark) {
        this.rlandMark = rlandMark;
    }

    public String getRpincode() {
        return rpincode;
    }

    public void setRpincode(String rpincode) {
        this.rpincode = rpincode;
    }

    public String getRcountry() {
        return rcountry;
    }

    public void setRcountry(String rcountry) {
        this.rcountry = rcountry;
    }

    public String getRstate() {
        return rstate;
    }

    public void setRstate(String rstate) {
        this.rstate = rstate;
    }

    public String getRcity() {
        return rcity;
    }

    public void setRcity(String rcity) {
        this.rcity = rcity;
    }

    public String getRmobile() {
        return rmobile;
    }

    public void setRmobile(String rmobile) {
        this.rmobile = rmobile;
    }

    public String getRemail() {
        return remail;
    }

    public void setRemail(String remail) {
        this.remail = remail;
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

    public String getRtnumber() {
        return rtnumber;
    }

    public void setRtnumber(String rtnumber) {
        this.rtnumber = rtnumber;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public boolean isSamePermentaddr() {
        return samePermentaddr;
    }

    public void setSamePermentaddr(boolean samePermentaddr) {
        this.samePermentaddr = samePermentaddr;
    }

    public boolean isAggrementAccepted() {
        return aggrementAccepted;
    }

    public void setAggrementAccepted(boolean aggrementAccepted) {
        this.aggrementAccepted = aggrementAccepted;
    }

    public String getOaddressLine1() {
        return oaddressLine1;
    }

    public void setOaddressLine1(String oaddressLine1) {
        this.oaddressLine1 = oaddressLine1;
    }

    public String getOaddressLine2() {
        return oaddressLine2;
    }

    public void setOaddressLine2(String oaddressLine2) {
        this.oaddressLine2 = oaddressLine2;
    }

    public String getOlandMark() {
        return olandMark;
    }

    public void setOlandMark(String olandMark) {
        this.olandMark = olandMark;
    }

    public String getOpincode() {
        return opincode;
    }

    public void setOpincode(String opincode) {
        this.opincode = opincode;
    }

    public String getOcountry() {
        return ocountry;
    }

    public void setOcountry(String ocountry) {
        this.ocountry = ocountry;
    }

    public String getOstate() {
        return ostate;
    }

    public void setOstate(String ostate) {
        this.ostate = ostate;
    }

    public String getOcity() {
        return ocity;
    }

    public void setOcity(String ocity) {
        this.ocity = ocity;
    }

    public String getOmobile() {
        return omobile;
    }

    public void setOmobile(String omobile) {
        this.omobile = omobile;
    }

    public String getOemail() {
        return oemail;
    }

    public void setOemail(String oemail) {
        this.oemail = oemail;
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

    public String getOtnumber() {
        return otnumber;
    }

    public void setOtnumber(String otnumber) {
        this.otnumber = otnumber;
    }

    public String getCorrespondenceAddress() {
        return correspondenceAddress;
    }

    public void setCorrespondenceAddress(String correspondenceAddress) {
        this.correspondenceAddress = correspondenceAddress;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscNo() {
        return ifscNo;
    }

    public void setIfscNo(String ifscNo) {
        this.ifscNo = ifscNo;
    }

    public String getMicrNo() {
        return micrNo;
    }

    public void setMicrNo(String micrNo) {
        this.micrNo = micrNo;
    }

    public String getBaddressLine1() {
        return baddressLine1;
    }

    public void setBaddressLine1(String baddressLine1) {
        this.baddressLine1 = baddressLine1;
    }

    public String getBaddressLine2() {
        return baddressLine2;
    }

    public void setBaddressLine2(String baddressLine2) {
        this.baddressLine2 = baddressLine2;
    }

    public String getBlandMark() {
        return blandMark;
    }

    public void setBlandMark(String blandMark) {
        this.blandMark = blandMark;
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

    public String getPqualification() {
        return pqualification;
    }

    public void setPqualification(String pqualification) {
        this.pqualification = pqualification;
    }

    public String getPinsititute() {
        return pinsititute;
    }

    public void setPinsititute(String pinsititute) {
        this.pinsititute = pinsititute;
    }

    public String getPyear() {
        return pyear;
    }

    public void setPyear(String pyear) {
        this.pyear = pyear;
    }

    public String getPqualificationId() {
        return pqualificationId;
    }

    public void setPqualificationId(String pqualificationId) {
        this.pqualificationId = pqualificationId;
    }

    public String getSqualification() {
        return squalification;
    }

    public void setSqualification(String squalification) {
        this.squalification = squalification;
    }

    public String getSinsititute() {
        return sinsititute;
    }

    public void setSinsititute(String sinsititute) {
        this.sinsititute = sinsititute;
    }

    public String getSyear() {
        return syear;
    }

    public void setSyear(String syear) {
        this.syear = syear;
    }

    public String getSqualificationId() {
        return squalificationId;
    }

    public void setSqualificationId(String squalificationId) {
        this.squalificationId = squalificationId;
    }

    public String getTqualification() {
        return tqualification;
    }

    public void setTqualification(String tqualification) {
        this.tqualification = tqualification;
    }

    public String getTinsititute() {
        return tinsititute;
    }

    public void setTinsititute(String tinsititute) {
        this.tinsititute = tinsititute;
    }

    public String getTyear() {
        return tyear;
    }

    public void setTyear(String tyear) {
        this.tyear = tyear;
    }

    public String getTqualificationId() {
        return tqualificationId;
    }

    public void setTqualificationId(String tqualificationId) {
        this.tqualificationId = tqualificationId;
    }

    public String getRaccountNumber() {
        return raccountNumber;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public void setRaccountNumber(String raccountNumber) {
        this.raccountNumber = raccountNumber;
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

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public String getRes_city_other() {
        return res_city_other;
    }

    public void setRes_city_other(String res_city_other) {
        this.res_city_other = res_city_other;
    }

    public String getBnk_city_other() {
        return bnk_city_other;
    }

    public void setBnk_city_other(String bnk_city_other) {
        this.bnk_city_other = bnk_city_other;
    }

    public String getOff_city_other() {
        return off_city_other;
    }

    public void setOff_city_other(String off_city_other) {
        this.off_city_other = off_city_other;
    }

    public boolean isIndividualOrCoprt() {
        return individualOrCoprt;
    }

    public void setIndividualOrCoprt(boolean individualOrCoprt) {
        this.individualOrCoprt = individualOrCoprt;
    }

    public String getSebiPath() {
        return sebiPath;
    }

    public void setSebiPath(String sebiPath) {
        this.sebiPath = sebiPath;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

	public String getAdvPicPath() {
		return advPicPath;
	}

	public void setAdvPicPath(String advPicPath) {
		this.advPicPath = advPicPath;
	}

	public String getOneLineDesc() {
		return oneLineDesc;
	}

	public void setOneLineDesc(String oneLineDesc) {
		this.oneLineDesc = oneLineDesc;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}

	public String getMyInvestmentStrategy() {
		return myInvestmentStrategy;
	}

	public void setMyInvestmentStrategy(String myInvestmentStrategy) {
		this.myInvestmentStrategy = myInvestmentStrategy;
	}
}
