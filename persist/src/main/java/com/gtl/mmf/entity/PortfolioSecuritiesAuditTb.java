package com.gtl.mmf.entity;
// Generated 5 Dec, 2015 1:20:59 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * PortfolioSecuritiesAuditTb generated by hbm2java
 */
public class PortfolioSecuritiesAuditTb  implements java.io.Serializable {


     private Integer portfolioSecuritiesAuditId;
     private PortfolioDetailsAuditTb portfolioDetailsAuditTb;
     private Integer portfolioSecuritiesId;
     private int portfolioId;
     private int portfolioDetailsId;
     private short assetClassId;
     private String securityId;
     private BigDecimal expReturns;
     private BigDecimal stdDev;
     private BigDecimal newWeight;
     private BigDecimal currentPrice;
     private Integer newToleranceNegativeRange;
     private Integer newTolerancePositiveRange;
     private Integer newUnits;
     private BigDecimal currentValue;
     private BigDecimal currentWeight;
     private BigDecimal initialWeight;
     private BigDecimal initialPrice;
     private Integer initialToleranceNegativeRange;
     private Integer initialTolerancePositiveRange;
     private BigDecimal initialUnits;
     private BigDecimal initialValue;
     private Boolean rebalanceRequired;
     private Date rebalanceReqdDate;
     private BigDecimal newAllocation;
     private String securityDescription;
     private Date lastUpdateon;
     private String activityType;

    public PortfolioSecuritiesAuditTb() {
    }

	
    public PortfolioSecuritiesAuditTb(int portfolioId, int portfolioDetailsId, short assetClassId) {
        this.portfolioId = portfolioId;
        this.portfolioDetailsId = portfolioDetailsId;
        this.assetClassId = assetClassId;
    }
    public PortfolioSecuritiesAuditTb(PortfolioDetailsAuditTb portfolioDetailsAuditTb, Integer portfolioSecuritiesId, int portfolioId, int portfolioDetailsId, short assetClassId, String securityId, BigDecimal expReturns, BigDecimal stdDev, BigDecimal newWeight, BigDecimal currentPrice, Integer newToleranceNegativeRange, Integer newTolerancePositiveRange, Integer newUnits, BigDecimal currentValue, BigDecimal currentWeight, BigDecimal initialWeight, BigDecimal initialPrice, Integer initialToleranceNegativeRange, Integer initialTolerancePositiveRange, BigDecimal initialUnits, BigDecimal initialValue, Boolean rebalanceRequired, Date rebalanceReqdDate, BigDecimal newAllocation, String securityDescription, Date lastUpdateon, String activityType) {
       this.portfolioDetailsAuditTb = portfolioDetailsAuditTb;
       this.portfolioSecuritiesId = portfolioSecuritiesId;
       this.portfolioId = portfolioId;
       this.portfolioDetailsId = portfolioDetailsId;
       this.assetClassId = assetClassId;
       this.securityId = securityId;
       this.expReturns = expReturns;
       this.stdDev = stdDev;
       this.newWeight = newWeight;
       this.currentPrice = currentPrice;
       this.newToleranceNegativeRange = newToleranceNegativeRange;
       this.newTolerancePositiveRange = newTolerancePositiveRange;
       this.newUnits = newUnits;
       this.currentValue = currentValue;
       this.currentWeight = currentWeight;
       this.initialWeight = initialWeight;
       this.initialPrice = initialPrice;
       this.initialToleranceNegativeRange = initialToleranceNegativeRange;
       this.initialTolerancePositiveRange = initialTolerancePositiveRange;
       this.initialUnits = initialUnits;
       this.initialValue = initialValue;
       this.rebalanceRequired = rebalanceRequired;
       this.rebalanceReqdDate = rebalanceReqdDate;
       this.newAllocation = newAllocation;
       this.securityDescription = securityDescription;
       this.lastUpdateon = lastUpdateon;
       this.activityType = activityType;
    }
   
    public Integer getPortfolioSecuritiesAuditId() {
        return this.portfolioSecuritiesAuditId;
    }
    
    public void setPortfolioSecuritiesAuditId(Integer portfolioSecuritiesAuditId) {
        this.portfolioSecuritiesAuditId = portfolioSecuritiesAuditId;
    }
    public PortfolioDetailsAuditTb getPortfolioDetailsAuditTb() {
        return this.portfolioDetailsAuditTb;
    }
    
    public void setPortfolioDetailsAuditTb(PortfolioDetailsAuditTb portfolioDetailsAuditTb) {
        this.portfolioDetailsAuditTb = portfolioDetailsAuditTb;
    }
    public Integer getPortfolioSecuritiesId() {
        return this.portfolioSecuritiesId;
    }
    
    public void setPortfolioSecuritiesId(Integer portfolioSecuritiesId) {
        this.portfolioSecuritiesId = portfolioSecuritiesId;
    }
    public int getPortfolioId() {
        return this.portfolioId;
    }
    
    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }
    public int getPortfolioDetailsId() {
        return this.portfolioDetailsId;
    }
    
    public void setPortfolioDetailsId(int portfolioDetailsId) {
        this.portfolioDetailsId = portfolioDetailsId;
    }
    public short getAssetClassId() {
        return this.assetClassId;
    }
    
    public void setAssetClassId(short assetClassId) {
        this.assetClassId = assetClassId;
    }
    public String getSecurityId() {
        return this.securityId;
    }
    
    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }
    public BigDecimal getExpReturns() {
        return this.expReturns;
    }
    
    public void setExpReturns(BigDecimal expReturns) {
        this.expReturns = expReturns;
    }
    public BigDecimal getStdDev() {
        return this.stdDev;
    }
    
    public void setStdDev(BigDecimal stdDev) {
        this.stdDev = stdDev;
    }
    public BigDecimal getNewWeight() {
        return this.newWeight;
    }
    
    public void setNewWeight(BigDecimal newWeight) {
        this.newWeight = newWeight;
    }
    public BigDecimal getCurrentPrice() {
        return this.currentPrice;
    }
    
    public void setCurrentPrice(BigDecimal currentPrice) {
        this.currentPrice = currentPrice;
    }
    public Integer getNewToleranceNegativeRange() {
        return this.newToleranceNegativeRange;
    }
    
    public void setNewToleranceNegativeRange(Integer newToleranceNegativeRange) {
        this.newToleranceNegativeRange = newToleranceNegativeRange;
    }
    public Integer getNewTolerancePositiveRange() {
        return this.newTolerancePositiveRange;
    }
    
    public void setNewTolerancePositiveRange(Integer newTolerancePositiveRange) {
        this.newTolerancePositiveRange = newTolerancePositiveRange;
    }
    public Integer getNewUnits() {
        return this.newUnits;
    }
    
    public void setNewUnits(Integer newUnits) {
        this.newUnits = newUnits;
    }
    public BigDecimal getCurrentValue() {
        return this.currentValue;
    }
    
    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
    public BigDecimal getCurrentWeight() {
        return this.currentWeight;
    }
    
    public void setCurrentWeight(BigDecimal currentWeight) {
        this.currentWeight = currentWeight;
    }
    public BigDecimal getInitialWeight() {
        return this.initialWeight;
    }
    
    public void setInitialWeight(BigDecimal initialWeight) {
        this.initialWeight = initialWeight;
    }
    public BigDecimal getInitialPrice() {
        return this.initialPrice;
    }
    
    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }
    public Integer getInitialToleranceNegativeRange() {
        return this.initialToleranceNegativeRange;
    }
    
    public void setInitialToleranceNegativeRange(Integer initialToleranceNegativeRange) {
        this.initialToleranceNegativeRange = initialToleranceNegativeRange;
    }
    public Integer getInitialTolerancePositiveRange() {
        return this.initialTolerancePositiveRange;
    }
    
    public void setInitialTolerancePositiveRange(Integer initialTolerancePositiveRange) {
        this.initialTolerancePositiveRange = initialTolerancePositiveRange;
    }
    public BigDecimal getInitialUnits() {
        return this.initialUnits;
    }
    
    public void setInitialUnits(BigDecimal initialUnits) {
        this.initialUnits = initialUnits;
    }
    public BigDecimal getInitialValue() {
        return this.initialValue;
    }
    
    public void setInitialValue(BigDecimal initialValue) {
        this.initialValue = initialValue;
    }
    public Boolean getRebalanceRequired() {
        return this.rebalanceRequired;
    }
    
    public void setRebalanceRequired(Boolean rebalanceRequired) {
        this.rebalanceRequired = rebalanceRequired;
    }
    public Date getRebalanceReqdDate() {
        return this.rebalanceReqdDate;
    }
    
    public void setRebalanceReqdDate(Date rebalanceReqdDate) {
        this.rebalanceReqdDate = rebalanceReqdDate;
    }
    public BigDecimal getNewAllocation() {
        return this.newAllocation;
    }
    
    public void setNewAllocation(BigDecimal newAllocation) {
        this.newAllocation = newAllocation;
    }
    public String getSecurityDescription() {
        return this.securityDescription;
    }
    
    public void setSecurityDescription(String securityDescription) {
        this.securityDescription = securityDescription;
    }
    public Date getLastUpdateon() {
        return this.lastUpdateon;
    }
    
    public void setLastUpdateon(Date lastUpdateon) {
        this.lastUpdateon = lastUpdateon;
    }
    public String getActivityType() {
        return this.activityType;
    }
    
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }




}


