package com.gtl.mmf.entity;
// Generated 5 Dec, 2015 1:20:59 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PortfolioSecuritiesTb generated by hbm2java
 */
public class PortfolioSecuritiesTb  implements java.io.Serializable {


     private Integer portfolioSecuritiesId;
     private PortfolioDetailsTb portfolioDetailsTb;
     private MasterAssetTb masterAssetTb;
     private PortfolioTb portfolioTb;
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
     private Float recommentedPrice;
     private Integer initialToleranceNegativeRange;
     private Integer initialTolerancePositiveRange;
     private BigDecimal exeUnits;
     private BigDecimal initialValue;
     private Boolean rebalanceRequired;
     private Date rebalanceReqdDate;
     private BigDecimal newAllocation;
     private String securityDescription;
     private String venueScriptCode;
     private String venueCode;
     private String securityCode;
     private Boolean status;
     private String instrumentType;
     private Date expirationDate;
     private BigDecimal strikePrice;
     private BigDecimal yesterDayUnitCount;
     private Set customerTransactionOrderDetailsTbs = new HashSet(0);
     private Set recomendedCustomerPortfolioSecuritiesTbs = new HashSet(0);
     private Set customerPortfolioSecuritiesTbs = new HashSet(0);
     private Set portfolioSecuritiesPerformanceTbs = new HashSet(0);

    public PortfolioSecuritiesTb() {
    }

	
    public PortfolioSecuritiesTb(PortfolioDetailsTb portfolioDetailsTb, MasterAssetTb masterAssetTb, PortfolioTb portfolioTb) {
        this.portfolioDetailsTb = portfolioDetailsTb;
        this.masterAssetTb = masterAssetTb;
        this.portfolioTb = portfolioTb;
    }
    public PortfolioSecuritiesTb(PortfolioDetailsTb portfolioDetailsTb, MasterAssetTb masterAssetTb, PortfolioTb portfolioTb, String securityId, BigDecimal expReturns, BigDecimal stdDev, BigDecimal newWeight, BigDecimal currentPrice, Integer newToleranceNegativeRange, Integer newTolerancePositiveRange, Integer newUnits, BigDecimal currentValue, BigDecimal currentWeight, BigDecimal initialWeight, Float recommentedPrice, Integer initialToleranceNegativeRange, Integer initialTolerancePositiveRange, BigDecimal exeUnits, BigDecimal initialValue, Boolean rebalanceRequired, Date rebalanceReqdDate, BigDecimal newAllocation, String securityDescription, String venueScriptCode, String venueCode, String securityCode, Boolean status, String instrumentType, Date expirationDate, BigDecimal strikePrice, BigDecimal yesterDayUnitCount, Set customerTransactionOrderDetailsTbs, Set recomendedCustomerPortfolioSecuritiesTbs, Set customerPortfolioSecuritiesTbs, Set portfolioSecuritiesPerformanceTbs) {
       this.portfolioDetailsTb = portfolioDetailsTb;
       this.masterAssetTb = masterAssetTb;
       this.portfolioTb = portfolioTb;
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
       this.recommentedPrice = recommentedPrice;
       this.initialToleranceNegativeRange = initialToleranceNegativeRange;
       this.initialTolerancePositiveRange = initialTolerancePositiveRange;
       this.exeUnits = exeUnits;
       this.initialValue = initialValue;
       this.rebalanceRequired = rebalanceRequired;
       this.rebalanceReqdDate = rebalanceReqdDate;
       this.newAllocation = newAllocation;
       this.securityDescription = securityDescription;
       this.venueScriptCode = venueScriptCode;
       this.venueCode = venueCode;
       this.securityCode = securityCode;
       this.status = status;
       this.instrumentType = instrumentType;
       this.expirationDate = expirationDate;
       this.strikePrice = strikePrice;
       this.yesterDayUnitCount = yesterDayUnitCount;
       this.customerTransactionOrderDetailsTbs = customerTransactionOrderDetailsTbs;
       this.recomendedCustomerPortfolioSecuritiesTbs = recomendedCustomerPortfolioSecuritiesTbs;
       this.customerPortfolioSecuritiesTbs = customerPortfolioSecuritiesTbs;
       this.portfolioSecuritiesPerformanceTbs = portfolioSecuritiesPerformanceTbs;
    }
   
    public Integer getPortfolioSecuritiesId() {
        return this.portfolioSecuritiesId;
    }
    
    public void setPortfolioSecuritiesId(Integer portfolioSecuritiesId) {
        this.portfolioSecuritiesId = portfolioSecuritiesId;
    }
    public PortfolioDetailsTb getPortfolioDetailsTb() {
        return this.portfolioDetailsTb;
    }
    
    public void setPortfolioDetailsTb(PortfolioDetailsTb portfolioDetailsTb) {
        this.portfolioDetailsTb = portfolioDetailsTb;
    }
    public MasterAssetTb getMasterAssetTb() {
        return this.masterAssetTb;
    }
    
    public void setMasterAssetTb(MasterAssetTb masterAssetTb) {
        this.masterAssetTb = masterAssetTb;
    }
    public PortfolioTb getPortfolioTb() {
        return this.portfolioTb;
    }
    
    public void setPortfolioTb(PortfolioTb portfolioTb) {
        this.portfolioTb = portfolioTb;
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
    public Float getRecommentedPrice() {
        return this.recommentedPrice;
    }
    
    public void setRecommentedPrice(Float recommentedPrice) {
        this.recommentedPrice = recommentedPrice;
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
    public BigDecimal getExeUnits() {
        return this.exeUnits;
    }
    
    public void setExeUnits(BigDecimal exeUnits) {
        this.exeUnits = exeUnits;
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
    public String getVenueScriptCode() {
        return this.venueScriptCode;
    }
    
    public void setVenueScriptCode(String venueScriptCode) {
        this.venueScriptCode = venueScriptCode;
    }
    public String getVenueCode() {
        return this.venueCode;
    }
    
    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }
    public String getSecurityCode() {
        return this.securityCode;
    }
    
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    public Boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(Boolean status) {
        this.status = status;
    }
    public String getInstrumentType() {
        return this.instrumentType;
    }
    
    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }
    public Date getExpirationDate() {
        return this.expirationDate;
    }
    
    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
    public BigDecimal getStrikePrice() {
        return this.strikePrice;
    }
    
    public void setStrikePrice(BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }
    public BigDecimal getYesterDayUnitCount() {
        return this.yesterDayUnitCount;
    }
    
    public void setYesterDayUnitCount(BigDecimal yesterDayUnitCount) {
        this.yesterDayUnitCount = yesterDayUnitCount;
    }
    public Set getCustomerTransactionOrderDetailsTbs() {
        return this.customerTransactionOrderDetailsTbs;
    }
    
    public void setCustomerTransactionOrderDetailsTbs(Set customerTransactionOrderDetailsTbs) {
        this.customerTransactionOrderDetailsTbs = customerTransactionOrderDetailsTbs;
    }
    public Set getRecomendedCustomerPortfolioSecuritiesTbs() {
        return this.recomendedCustomerPortfolioSecuritiesTbs;
    }
    
    public void setRecomendedCustomerPortfolioSecuritiesTbs(Set recomendedCustomerPortfolioSecuritiesTbs) {
        this.recomendedCustomerPortfolioSecuritiesTbs = recomendedCustomerPortfolioSecuritiesTbs;
    }
    public Set getCustomerPortfolioSecuritiesTbs() {
        return this.customerPortfolioSecuritiesTbs;
    }
    
    public void setCustomerPortfolioSecuritiesTbs(Set customerPortfolioSecuritiesTbs) {
        this.customerPortfolioSecuritiesTbs = customerPortfolioSecuritiesTbs;
    }
    public Set getPortfolioSecuritiesPerformanceTbs() {
        return this.portfolioSecuritiesPerformanceTbs;
    }
    
    public void setPortfolioSecuritiesPerformanceTbs(Set portfolioSecuritiesPerformanceTbs) {
        this.portfolioSecuritiesPerformanceTbs = portfolioSecuritiesPerformanceTbs;
    }




}

