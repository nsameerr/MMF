/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import java.util.Date;

/**
 *
 * @author trainee12
 */
public class MandateVo {

    String reg_id;
    String utilityName;
    String umrn;
    String[] umrnArray;
    String utilityCode;

    String customerBankName;
    String customerAccountNumber;
    String[] customerAccountNumberArray;
    String customerBankCode;

    String customerDebitAccount;
    String[] customerDebitAccountArray;
    String customerDebitType;
    String[] customerDebitTypeArray;
    String frequency;
    String[] frequencyArray;

    boolean customerIfsc;
    boolean customerMicr;
    String ifsc;
    String[] ifscArray;
    String micr;
    String[] micrArray;
    String ifscNumber;
    String amount;
    String contactNmbr;

    String customerEmail;
    Date fromDate;
    String[] fromDateArray;
    Date toDate;
    String[] toDateArray;
    String cancelDate;
    boolean selectToDate;
    String action;
    String[] actionArray;
    String amountInWords;
    Date date;
    String[] dateArray;
    String mandateisd;
    String mandatestd;
    String micrNumber;
    String customerName;

    public String getUtilityName() {
        return utilityName;
    }

    public void setUtilityName(String utilityName) {
        this.utilityName = utilityName;
    }

    public String getUmrn() {
        return umrn;
    }

    public void setUmrn(String umrn) {
        this.umrn = umrn;
    }

    public String[] getUmrnArray() {
        return umrnArray;
    }

    public void setUmrnArray(String[] umrnArray) {
        this.umrnArray = umrnArray;
    }

    public String getUtilityCode() {
        return utilityCode;
    }

    public void setUtilityCode(String utilityCode) {
        this.utilityCode = utilityCode;
    }

    public String getCustomerBankName() {
        return customerBankName;
    }

    public void setCustomerBankName(String customerBankName) {
        this.customerBankName = customerBankName;
    }

    public String getCustomerAccountNumber() {
        return customerAccountNumber;
    }

    public void setCustomerAccountNumber(String customerAccountNumber) {
        this.customerAccountNumber = customerAccountNumber;
    }

    public String[] getCustomerAccountNumberArray() {
        return customerAccountNumberArray;
    }

    public void setCustomerAccountNumberArray(String[] customerAccountNumberArray) {
        this.customerAccountNumberArray = customerAccountNumberArray;
    }

    public String getCustomerBankCode() {
        return customerBankCode;
    }

    public void setCustomerBankCode(String customerBankCode) {
        this.customerBankCode = customerBankCode;
    }

    public String getCustomerDebitAccount() {
        return customerDebitAccount;
    }

    public void setCustomerDebitAccount(String customerDebitAccount) {
        this.customerDebitAccount = customerDebitAccount;
    }

    public String getCustomerDebitType() {
        return customerDebitType;
    }

    public void setCustomerDebitType(String customerDebitType) {
        this.customerDebitType = customerDebitType;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getIfscNumber() {
        return ifscNumber;
    }

    public void setIfscNumber(String ifscNumber) {
        this.ifscNumber = ifscNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getContactNmbr() {
        return contactNmbr;
    }

    public void setContactNmbr(String contactNmbr) {
        this.contactNmbr = contactNmbr;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public String[] getFromDateArray() {
        return fromDateArray;
    }

    public void setFromDateArray(String[] fromDateArray) {
        this.fromDateArray = fromDateArray;
    }

    public String[] getToDateArray() {
        return toDateArray;
    }

    public void setToDateArray(String[] toDateArray) {
        this.toDateArray = toDateArray;
    }

    public String getCancelDate() {
        return cancelDate;
    }

    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
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

    public String getMicr() {
        return micr;
    }

    public void setMicr(String micr) {
        this.micr = micr;
    }

    public String[] getMicrArray() {
        return micrArray;
    }

    public void setMicrArray(String[] micrArray) {
        this.micrArray = micrArray;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public String[] getCustomerDebitAccountArray() {
        return customerDebitAccountArray;
    }

    public void setCustomerDebitAccountArray(String[] customerDebitAccountArray) {
        this.customerDebitAccountArray = customerDebitAccountArray;
    }

    public String[] getCustomerDebitTypeArray() {
        return customerDebitTypeArray;
    }

    public void setCustomerDebitTypeArray(String[] customerDebitTypeArray) {
        this.customerDebitTypeArray = customerDebitTypeArray;
    }

    public String[] getFrequencyArray() {
        return frequencyArray;
    }

    public void setFrequencyArray(String[] frequencyArray) {
        this.frequencyArray = frequencyArray;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String[] getActionArray() {
        return actionArray;
    }

    public void setActionArray(String[] actionArray) {
        this.actionArray = actionArray;
    }

    public String getAmountInWords() {
        return amountInWords;
    }

    public void setAmountInWords(String amountInWords) {
        this.amountInWords = amountInWords;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String[] getDateArray() {
        return dateArray;
    }

    public void setDateArray(String[] dateArray) {
        this.dateArray = dateArray;
    }

    public boolean isCustomerIfsc() {
        return customerIfsc;
    }

    public void setCustomerIfsc(boolean customerIfsc) {
        this.customerIfsc = customerIfsc;
    }

    public boolean isCustomerMicr() {
        return customerMicr;
    }

    public void setCustomerMicr(boolean customerMicr) {
        this.customerMicr = customerMicr;
    }

    public boolean isSelectToDate() {
        return selectToDate;
    }

    public void setSelectToDate(boolean selectToDate) {
        this.selectToDate = selectToDate;
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

    public String getMicrNumber() {
        return micrNumber;
    }

    public void setMicrNumber(String micrNumber) {
        this.micrNumber = micrNumber;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    
}
