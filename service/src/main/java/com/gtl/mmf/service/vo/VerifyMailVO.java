/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * 07662
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.entity.MasterApplicantTb;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.D_SPACE_MMM_COMMA_YYYY;
import java.text.ParseException;

public class VerifyMailVO {

    private String verificationCode;
    private String parameterUid;
    private String DOB;
    private String moble;
    private String email;

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getParameterUid() {
        return parameterUid;
    }

    public void setParameterUid(String parameterUid) {
        this.parameterUid = parameterUid;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getMoble() {
        return moble;
    }

    public void setMoble(String moble) {
        this.moble = moble;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public MasterApplicantTb toMasterApplicantTb() throws ParseException {
        MasterApplicantTb masterApplicantTb = new MasterApplicantTb();
        masterApplicantTb.setVerificationCode(verificationCode);
        masterApplicantTb.setMobile(moble);
        masterApplicantTb.setDob(DateUtil.stringToDate(DOB, D_SPACE_MMM_COMMA_YYYY));
        masterApplicantTb.setEmail(email);
        masterApplicantTb.setIsMailVerified(Boolean.TRUE);
        masterApplicantTb.setStatus(EnumStatus.NEW_APPLICANT.getValue());
        return masterApplicantTb;
    }

}
