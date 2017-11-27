/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

/**
 *
 * @author trainee7
 */
public class FailedMailDetailsVO {

    private String email;
    private String email_purpose;
    private String regId;
    private boolean checked;
    private String userType;
    private String verificationCode;
    private String userName;
    private String mobile;
    private boolean mailStatus;
    private String mailStatusText;
    private String mailStatusStyle;
    private boolean sendMail;
        
    private RegistrationDataProcessingVo registrationDataProcessing;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_purpose() {
        return email_purpose;
    }

    public void setEmail_purpose(String email_purpose) {
        this.email_purpose = email_purpose;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String RegId) {
        this.regId = RegId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public RegistrationDataProcessingVo getRegistrationDataProcessing() {
        return registrationDataProcessing;
    }

    public void setRegistrationDataProcessing(RegistrationDataProcessingVo registrationDataProcessing) {
        this.registrationDataProcessing = registrationDataProcessing;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(boolean mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getMailStatusText() {
        return mailStatusText;
    }

    public void setMailStatusText(String mailStatusText) {
        this.mailStatusText = mailStatusText;
    }

    public String getMailStatusStyle() {
        return mailStatusStyle;
    }

    public void setMailStatusStyle(String mailStatusStyle) {
        this.mailStatusStyle = mailStatusStyle;
    }

    public boolean isSendMail() {
        return sendMail;
    }

    public void setSendMail(boolean sendMail) {
        this.sendMail = sendMail;
    }
   
}
