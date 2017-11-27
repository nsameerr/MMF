/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author trainee8
 */
public class TransactionResponseVo {

    String MerchantID;
    String CustomerID;
    String TxnReferenceNo;
    String BankReferenceNo;
    String TxnAmount;
    String BankID;
    String BankMerchantID;
    String TxnType;
    String CurrencyName;
    String ItemCode;
    String SecurityType;
    String SecurityID;
    String SecurityPassword;
    String TxnDate;
    String AuthStatus;
    String SettlementType;
    String ErrorStatus;
    String ErrorDescription;
    String CheckSum;
    String AuthStatusMsg;
    String returnUrlMsg;
    boolean chkSumMissMatch;
    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String MerchantID) {
        this.MerchantID = MerchantID;
    }

    public String getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(String CustomerID) {
        this.CustomerID = CustomerID;
    }

    public String getTxnReferenceNo() {
        return TxnReferenceNo;
    }

    public void setTxnReferenceNo(String TxnReferenceNo) {
        this.TxnReferenceNo = TxnReferenceNo;
    }

    public String getBankReferenceNo() {
        return BankReferenceNo;
    }

    public void setBankReferenceNo(String BankReferenceNo) {
        this.BankReferenceNo = BankReferenceNo;
    }

    public String getTxnAmount() {
        return TxnAmount;
    }

    public void setTxnAmount(String TxnAmount) {
        this.TxnAmount = TxnAmount;
    }

    public String getBankID() {
        return BankID;
    }

    public void setBankID(String BankID) {
        this.BankID = BankID;
    }

    public String getBankMerchantID() {
        return BankMerchantID;
    }

    public void setBankMerchantID(String BankMerchantID) {
        this.BankMerchantID = BankMerchantID;
    }

    public String getTxnType() {
        return TxnType;
    }

    public void setTxnType(String TxnType) {
        this.TxnType = TxnType;
    }

    public String getCurrencyName() {
        return CurrencyName;
    }

    public void setCurrencyName(String CurrencyName) {
        this.CurrencyName = CurrencyName;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String ItemCode) {
        this.ItemCode = ItemCode;
    }

    public String getSecurityType() {
        return SecurityType;
    }

    public void setSecurityType(String SecurityType) {
        this.SecurityType = SecurityType;
    }

    public String getSecurityID() {
        return SecurityID;
    }

    public void setSecurityID(String SecurityID) {
        this.SecurityID = SecurityID;
    }

    public String getSecurityPassword() {
        return SecurityPassword;
    }

    public void setSecurityPassword(String SecurityPassword) {
        this.SecurityPassword = SecurityPassword;
    }

    public String getTxnDate() {
        return TxnDate;
    }

    public void setTxnDate(String TxnDate) {
        this.TxnDate = TxnDate;
    }

    public String getAuthStatus() {
        return AuthStatus;
    }

    public void setAuthStatus(String AuthStatus) {
        this.AuthStatus = AuthStatus;
    }

    public String getSettlementType() {
        return SettlementType;
    }

    public void setSettlementType(String SettlementType) {
        this.SettlementType = SettlementType;
    }

    public String getErrorStatus() {
        return ErrorStatus;
    }

    public void setErrorStatus(String ErrorStatus) {
        this.ErrorStatus = ErrorStatus;
    }

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String ErrorDescription) {
        this.ErrorDescription = ErrorDescription;
    }

    public String getCheckSum() {
        return CheckSum;
    }

    public void setCheckSum(String CheckSum) {
        this.CheckSum = CheckSum;
    }

    public String getAuthStatusMsg() {
        return AuthStatusMsg;
    }

    public void setAuthStatusMsg(String AuthStatusMsg) {
        this.AuthStatusMsg = AuthStatusMsg;
    }

    public String getReturnUrlMsg() {
        return returnUrlMsg;
    }

    public void setReturnUrlMsg(String returnUrlMsg) {
        this.returnUrlMsg = returnUrlMsg;
    }

    public boolean isChkSumMissMatch() {
        return chkSumMissMatch;
    }

    public void setChkSumMissMatch(boolean chkSumMissMatch) {
        this.chkSumMissMatch = chkSumMissMatch;
    }

}
