/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.persist.vo;

import java.util.Date;

/**
 *
 * @author trainee8
 */
public class EcsDebtFileReplyVo {

    String regId;
    Double debtAmountFee;
    String status;
    String failureReason;
    Date dueDate;
    Date feeCalculateDate;
    String mgntRowId;
    String perfRowId;
    boolean checked;

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public Double getDebtAmountFee() {
        return debtAmountFee;
    }

    public void setDebtAmountFee(Double debtAmountFee) {
        this.debtAmountFee = debtAmountFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getMgntRowId() {
        return mgntRowId;
    }

    public void setMgntRowId(String mgntRowId) {
        this.mgntRowId = mgntRowId;
    }

    public String getPerfRowId() {
        return perfRowId;
    }

    public void setPerfRowId(String perfRowId) {
        this.perfRowId = perfRowId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public Date getFeeCalculateDate() {
        return feeCalculateDate;
    }

    public void setFeeCalculateDate(Date feeCalculateDate) {
        this.feeCalculateDate = feeCalculateDate;
    }
    
    

}
