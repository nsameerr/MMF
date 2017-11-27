/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.service.util.IConstants;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public class ContractDetailsVO implements IConstants {

    private Integer relationId;
    private Boolean mgmtFeeTypeVariable;
    private BigDecimal variableManagementFee;
    private Integer fixedManagementFee;
    private Short mgmtFeeFixedPayableFrequecy;
    private Short mgmtFeeVariablePayableFrequecy;
    private BigDecimal performanceFeePercent;
    private BigDecimal performanceFeeThreshold;
    private Integer performanceFeeFrequency = 3;
    private Integer durationCount;
    private Integer durationFrequency = 12;
    private Date startDate;
    private Date endDate;
    private Double allocatedFunds;
    private List<String> previousReviews;
    private String investorReview;
    private Short reviewFrequency;
    private Integer customerId;

    public ContractDetailsVO() {
        mgmtFeeFixedPayableFrequecy = DEFAULT_AUM_PAYABLE;
        mgmtFeeVariablePayableFrequecy = DEFAULT_AUM_PAYABLE;
        mgmtFeeTypeVariable = true;
    }

    public CustomerAdvisorMappingTb toCustomerAdvisorMappingTb() {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationId(this.getRelationId());

        customerAdvisorMappingTb.setMgmtFeeTypeVariable(this.getMgmtFeeTypeVariable());
        if (this.getMgmtFeeTypeVariable()) {
            customerAdvisorMappingTb.setManagementFeeVariable(this.getVariableManagementFee());
            customerAdvisorMappingTb.setMgmtFeeFreq(this.getMgmtFeeVariablePayableFrequecy());
        } else {
            customerAdvisorMappingTb.setManagementFeeFixed(this.getFixedManagementFee() == null ? 0 : this.getFixedManagementFee());
            customerAdvisorMappingTb.setMgmtFeeFreq(this.getMgmtFeeFixedPayableFrequecy());
        }

        customerAdvisorMappingTb.setPerfFeePercent(this.getPerformanceFeePercent() == null
                ? BigDecimal.ZERO : this.getPerformanceFeePercent());
        customerAdvisorMappingTb.setPerfFeeThreshold(this.getPerformanceFeeThreshold() == null
                ? BigDecimal.ZERO : this.getPerformanceFeeThreshold());
        customerAdvisorMappingTb.setPerfFeeFreq(this.getPerformanceFeeFrequency());

        customerAdvisorMappingTb.setDurationCount(this.getDurationCount());
        customerAdvisorMappingTb.setDurationFrequencyMonth(this.getDurationFrequency());
        customerAdvisorMappingTb.setContractStart(this.getStartDate());
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(startDate);
        endCalendar.add(Calendar.MONTH, (durationCount * durationFrequency));
        endDate = endCalendar.getTime();
        customerAdvisorMappingTb.setContractEnd(endDate);

      //Updateing status date while saving ploicy statement
        customerAdvisorMappingTb.setStatusDate(new Date());
        
        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.CONTRACT_SENT.getValue());
        customerAdvisorMappingTb.setCustomerReview(this.investorReview);
        return customerAdvisorMappingTb;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public BigDecimal getVariableManagementFee() {
        return variableManagementFee;
    }

    public void setVariableManagementFee(BigDecimal variableManagementFee) {
        this.variableManagementFee = variableManagementFee;
    }

    public Short getMgmtFeeFixedPayableFrequecy() {
        return mgmtFeeFixedPayableFrequecy;
    }

    public void setMgmtFeeFixedPayableFrequecy(Short mgmtFeeFixedPayableFrequecy) {
        this.mgmtFeeFixedPayableFrequecy = mgmtFeeFixedPayableFrequecy;
    }

    public BigDecimal getPerformanceFeePercent() {
        return performanceFeePercent;
    }

    public void setPerformanceFeePercent(BigDecimal performanceFeePercent) {
        this.performanceFeePercent = performanceFeePercent;
    }

    public BigDecimal getPerformanceFeeThreshold() {
        return performanceFeeThreshold;
    }

    public void setPerformanceFeeThreshold(BigDecimal performanceFeeThreshold) {
        this.performanceFeeThreshold = performanceFeeThreshold;
    }

    public Integer getPerformanceFeeFrequency() {
        return performanceFeeFrequency;
    }

    public void setPerformanceFeeFrequency(Integer performanceFeeFrequency) {
        this.performanceFeeFrequency = performanceFeeFrequency;
    }

    public Integer getDurationCount() {
        return durationCount;
    }

    public void setDurationCount(Integer durationCount) {
        this.durationCount = durationCount;
    }

    public Integer getDurationFrequency() {
        return durationFrequency;
    }

    public void setDurationFrequency(Integer durationFrequency) {
        this.durationFrequency = durationFrequency;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getAllocatedFunds() {
        return allocatedFunds;
    }

    public void setAllocatedFunds(Double allocatedFunds) {
        this.allocatedFunds = allocatedFunds;
    }

    public Integer getFixedManagementFee() {
        return fixedManagementFee;
    }

    public void setFixedManagementFee(Integer fixedManagementFee) {
        this.fixedManagementFee = fixedManagementFee;
    }

    public Short getMgmtFeeVariablePayableFrequecy() {
        return mgmtFeeVariablePayableFrequecy;
    }

    public void setMgmtFeeVariablePayableFrequecy(Short mgmtFeeVariablePayableFrequecy) {
        this.mgmtFeeVariablePayableFrequecy = mgmtFeeVariablePayableFrequecy;
    }

    public Boolean getMgmtFeeTypeVariable() {
        return mgmtFeeTypeVariable;
    }

    public void setMgmtFeeTypeVariable(Boolean mgmtFeeTypeVariable) {
        this.mgmtFeeTypeVariable = mgmtFeeTypeVariable;
    }

    public String getInvestorReview() {
        return investorReview;
    }

    public void setInvestorReview(String investorReview) {
        this.investorReview = investorReview;
    }

    public List<String> getPreviousReviews() {
        return previousReviews;
    }

    public void setPreviousReviews(List<String> previousReviews) {
        this.previousReviews = previousReviews;
    }

    public Short getReviewFrequency() {
        return reviewFrequency;
    }

    public void setReviewFrequency(Short reviewFrequency) {
        this.reviewFrequency = reviewFrequency;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
}
