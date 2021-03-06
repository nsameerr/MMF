package com.gtl.mmf.entity;
// Generated Jul 6, 2015 2:08:12 PM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * MandateFormTb generated by hbm2java
 */
public class MandateFormTb  implements java.io.Serializable {


     private Integer id;
     private String registrationId;
     private String umrn;
     private String actionType;
     private String sponserBankCode;
     private String utilityCode;
     private String nameOfBiller;
     private String debtAccountType;
     private String accountNo;
     private String bankName;
     private Boolean ifscOrMicr;
     private String ifscNumber;
     private String amountWords;
     private BigDecimal amount;
     private String frequency;
     private String debtType;
     private String reference1;
     private String customerPhone;
     private String reference2;
     private String email;
     private Date mandateCreatedDate;
     private Date fromDate;
     private Date toDate;
     private Boolean toOrUntill;
     private String micrNumber;

    public MandateFormTb() {
    }

	
    public MandateFormTb(String registrationId, String nameOfBiller, String accountNo) {
        this.registrationId = registrationId;
        this.nameOfBiller = nameOfBiller;
        this.accountNo = accountNo;
    }
    public MandateFormTb(String registrationId, String umrn, String actionType, String sponserBankCode, String utilityCode, String nameOfBiller, String debtAccountType, String accountNo, String bankName, Boolean ifscOrMicr, String ifscNumber, String amountWords, BigDecimal amount, String frequency, String debtType, String reference1, String customerPhone, String reference2, String email, Date mandateCreatedDate, Date fromDate, Date toDate, Boolean toOrUntill, String micrNumber) {
       this.registrationId = registrationId;
       this.umrn = umrn;
       this.actionType = actionType;
       this.sponserBankCode = sponserBankCode;
       this.utilityCode = utilityCode;
       this.nameOfBiller = nameOfBiller;
       this.debtAccountType = debtAccountType;
       this.accountNo = accountNo;
       this.bankName = bankName;
       this.ifscOrMicr = ifscOrMicr;
       this.ifscNumber = ifscNumber;
       this.amountWords = amountWords;
       this.amount = amount;
       this.frequency = frequency;
       this.debtType = debtType;
       this.reference1 = reference1;
       this.customerPhone = customerPhone;
       this.reference2 = reference2;
       this.email = email;
       this.mandateCreatedDate = mandateCreatedDate;
       this.fromDate = fromDate;
       this.toDate = toDate;
       this.toOrUntill = toOrUntill;
       this.micrNumber = micrNumber;
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
    public String getUmrn() {
        return this.umrn;
    }
    
    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }
    public String getActionType() {
        return this.actionType;
    }
    
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    public String getSponserBankCode() {
        return this.sponserBankCode;
    }
    
    public void setSponserBankCode(String sponserBankCode) {
        this.sponserBankCode = sponserBankCode;
    }
    public String getUtilityCode() {
        return this.utilityCode;
    }
    
    public void setUtilityCode(String utilityCode) {
        this.utilityCode = utilityCode;
    }
    public String getNameOfBiller() {
        return this.nameOfBiller;
    }
    
    public void setNameOfBiller(String nameOfBiller) {
        this.nameOfBiller = nameOfBiller;
    }
    public String getDebtAccountType() {
        return this.debtAccountType;
    }
    
    public void setDebtAccountType(String debtAccountType) {
        this.debtAccountType = debtAccountType;
    }
    public String getAccountNo() {
        return this.accountNo;
    }
    
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public String getBankName() {
        return this.bankName;
    }
    
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
    public Boolean getIfscOrMicr() {
        return this.ifscOrMicr;
    }
    
    public void setIfscOrMicr(Boolean ifscOrMicr) {
        this.ifscOrMicr = ifscOrMicr;
    }
    public String getIfscNumber() {
        return this.ifscNumber;
    }
    
    public void setIfscNumber(String ifscNumber) {
        this.ifscNumber = ifscNumber;
    }
    public String getAmountWords() {
        return this.amountWords;
    }
    
    public void setAmountWords(String amountWords) {
        this.amountWords = amountWords;
    }
    public BigDecimal getAmount() {
        return this.amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public String getFrequency() {
        return this.frequency;
    }
    
    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
    public String getDebtType() {
        return this.debtType;
    }
    
    public void setDebtType(String debtType) {
        this.debtType = debtType;
    }
    public String getReference1() {
        return this.reference1;
    }
    
    public void setReference1(String reference1) {
        this.reference1 = reference1;
    }
    public String getCustomerPhone() {
        return this.customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    public String getReference2() {
        return this.reference2;
    }
    
    public void setReference2(String reference2) {
        this.reference2 = reference2;
    }
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    public Date getMandateCreatedDate() {
        return this.mandateCreatedDate;
    }
    
    public void setMandateCreatedDate(Date mandateCreatedDate) {
        this.mandateCreatedDate = mandateCreatedDate;
    }
    public Date getFromDate() {
        return this.fromDate;
    }
    
    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }
    public Date getToDate() {
        return this.toDate;
    }
    
    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }
    public Boolean getToOrUntill() {
        return this.toOrUntill;
    }
    
    public void setToOrUntill(Boolean toOrUntill) {
        this.toOrUntill = toOrUntill;
    }
    public String getMicrNumber() {
        return this.micrNumber;
    }
    
    public void setMicrNumber(String micrNumber) {
        this.micrNumber = micrNumber;
    }




}


