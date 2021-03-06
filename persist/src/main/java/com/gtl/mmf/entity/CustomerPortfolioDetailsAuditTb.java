package com.gtl.mmf.entity;
// Generated 18 Jul, 2014 3:17:57 PM by Hibernate Tools 3.6.0

import java.util.Date;

/**
 * CustomerPortfolioDetailsAuditTb generated by hbm2java
 */
public class CustomerPortfolioDetailsAuditTb implements java.io.Serializable {

    private Integer customerPortfolioDetailsAuditId;
    private Integer customerPortfolioDetailsId;
    private Integer customerPortfolioId;
    private Short assetClassId;
    private Float rangeFrom;
    private Float rangeTo;
    private Integer portfolioId;
    private Integer portfolioDetailsId;
    private Float currentWeight;
    private Float newAllocation;
    private Float currentAllocation;
    private Date lastUpdateOn;
    private String activityType;

    public CustomerPortfolioDetailsAuditTb() {
    }

    public CustomerPortfolioDetailsAuditTb(Integer customerPortfolioDetailsId, Integer customerPortfolioId, Short assetClassId, Float rangeFrom, Float rangeTo, Integer portfolioId, Integer portfolioDetailsId, Float currentWeight, Float newAllocation, Float currentAllocation, Date lastUpdateOn, String activityType) {
        this.customerPortfolioDetailsId = customerPortfolioDetailsId;
        this.customerPortfolioId = customerPortfolioId;
        this.assetClassId = assetClassId;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.portfolioId = portfolioId;
        this.portfolioDetailsId = portfolioDetailsId;
        this.currentWeight = currentWeight;
        this.newAllocation = newAllocation;
        this.currentAllocation = currentAllocation;
        this.lastUpdateOn = lastUpdateOn;
        this.activityType = activityType;
    }

    public Integer getCustomerPortfolioDetailsAuditId() {
        return this.customerPortfolioDetailsAuditId;
    }

    public void setCustomerPortfolioDetailsAuditId(Integer customerPortfolioDetailsAuditId) {
        this.customerPortfolioDetailsAuditId = customerPortfolioDetailsAuditId;
    }

    public Integer getCustomerPortfolioDetailsId() {
        return this.customerPortfolioDetailsId;
    }

    public void setCustomerPortfolioDetailsId(Integer customerPortfolioDetailsId) {
        this.customerPortfolioDetailsId = customerPortfolioDetailsId;
    }

    public Integer getCustomerPortfolioId() {
        return this.customerPortfolioId;
    }

    public void setCustomerPortfolioId(Integer customerPortfolioId) {
        this.customerPortfolioId = customerPortfolioId;
    }

    public Short getAssetClassId() {
        return this.assetClassId;
    }

    public void setAssetClassId(Short assetClassId) {
        this.assetClassId = assetClassId;
    }

    public Float getRangeFrom() {
        return this.rangeFrom;
    }

    public void setRangeFrom(Float rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Float getRangeTo() {
        return this.rangeTo;
    }

    public void setRangeTo(Float rangeTo) {
        this.rangeTo = rangeTo;
    }

    public Integer getPortfolioId() {
        return this.portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Integer getPortfolioDetailsId() {
        return this.portfolioDetailsId;
    }

    public void setPortfolioDetailsId(Integer portfolioDetailsId) {
        this.portfolioDetailsId = portfolioDetailsId;
    }

    public Float getCurrentWeight() {
        return this.currentWeight;
    }

    public void setCurrentWeight(Float currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Float getNewAllocation() {
        return this.newAllocation;
    }

    public void setNewAllocation(Float newAllocation) {
        this.newAllocation = newAllocation;
    }

    public Float getCurrentAllocation() {
        return this.currentAllocation;
    }

    public void setCurrentAllocation(Float currentAllocation) {
        this.currentAllocation = currentAllocation;
    }

    public Date getLastUpdateOn() {
        return this.lastUpdateOn;
    }

    public void setLastUpdateOn(Date lastUpdateOn) {
        this.lastUpdateOn = lastUpdateOn;
    }

    public String getActivityType() {
        return this.activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

}
