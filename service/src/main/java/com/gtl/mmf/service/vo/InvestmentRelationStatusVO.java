/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class InvestmentRelationStatusVO {

    private Integer relationId;
    private Double totalAvailableFund;
    private Double allocatedFund;
    private Double totalFund;
    private String contractStatus;
    private Double feePending;
    private Integer relationStatus;
    private String relationStatusText;

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Double getTotalAvailableFund() {
        return totalAvailableFund;
    }

    public void setTotalAvailableFund(Double totalAvailableFund) {
        this.totalAvailableFund = totalAvailableFund;
    }

    public Double getAllocatedFund() {
        return allocatedFund;
    }

    public void setAllocatedFund(Double allocatedFund) {
        this.allocatedFund = allocatedFund;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(String contractStatus) {
        this.contractStatus = contractStatus;
    }

    public Double getFeePending() {
        return feePending;
    }

    public void setFeePending(Double feePending) {
        this.feePending = feePending;
    }

    public Double getTotalFund() {
        return totalFund;
    }

    public void setTotalFund(Double totalFund) {
        this.totalFund = totalFund;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

    public String getRelationStatusText() {
        return relationStatusText;
    }

    public void setRelationStatusText(String relationStatusText) {
        this.relationStatusText = relationStatusText;
    }
}
