package com.gtl.mmf.entity;
// Generated 5 Dec, 2015 1:20:59 PM by Hibernate Tools 3.6.0


import java.math.BigDecimal;
import java.util.Date;

/**
 * CustomerPortfolioSecuritiesAuditTb generated by hbm2java
 */
public class CustomerPortfolioSecuritiesAuditTb  implements java.io.Serializable {


     private Integer customerPortfolioSecuritiesAuditId;
     private Integer customerPortfolioSecuritiesId;
     private Integer customerPortfolioId;
     private Integer customerPortfolioDetailsId;
     private Integer portfolioId;
     private Integer portfolioDetailsId;
     private Integer portfolioSecuritiesId;
     private Short assetClassId;
     private String securityId;
     private Integer currentUnits;
     private Float currentPrice;
     private Float currentValue;
     private Float currentWeight;
     private BigDecimal exeUnits;
     private Float recommentedPrice;
     private Float avgAcquisitionPrice;
     private Float initialValue;
     private Float initialWeight;
     private Float requiredValue;
     private BigDecimal requiredUnits;
     private Boolean rebalanceRequired;
     private Date rebalanceReqdDate;
     private Float newAllocation;
     private String securityDescription;
     private Integer newToleranceNegativeRange;
     private Integer newTolerancePositiveRange;
     private String venueCode;
     private String venueScriptCode;
     private String securityCode;
     private Boolean status;
     private String instrumentType;
     private Date expirationDate;
     private BigDecimal strikePrice;
     private Date lastUpadteOn;
     private String activityType;

    public CustomerPortfolioSecuritiesAuditTb() {
    }

    public CustomerPortfolioSecuritiesAuditTb(Integer customerPortfolioSecuritiesId, Integer customerPortfolioId, Integer customerPortfolioDetailsId, Integer portfolioId, Integer portfolioDetailsId, Integer portfolioSecuritiesId, Short assetClassId, String securityId, Integer currentUnits, Float currentPrice, Float currentValue, Float currentWeight, BigDecimal exeUnits, Float recommentedPrice, Float avgAcquisitionPrice, Float initialValue, Float initialWeight, Float requiredValue, BigDecimal requiredUnits, Boolean rebalanceRequired, Date rebalanceReqdDate, Float newAllocation, String securityDescription, Integer newToleranceNegativeRange, Integer newTolerancePositiveRange, String venueCode, String venueScriptCode, String securityCode, Boolean status, String instrumentType, Date expirationDate, BigDecimal strikePrice, Date lastUpadteOn, String activityType) {
       this.customerPortfolioSecuritiesId = customerPortfolioSecuritiesId;
       this.customerPortfolioId = customerPortfolioId;
       this.customerPortfolioDetailsId = customerPortfolioDetailsId;
       this.portfolioId = portfolioId;
       this.portfolioDetailsId = portfolioDetailsId;
       this.portfolioSecuritiesId = portfolioSecuritiesId;
       this.assetClassId = assetClassId;
       this.securityId = securityId;
       this.currentUnits = currentUnits;
       this.currentPrice = currentPrice;
       this.currentValue = currentValue;
       this.currentWeight = currentWeight;
       this.exeUnits = exeUnits;
       this.recommentedPrice = recommentedPrice;
       this.avgAcquisitionPrice = avgAcquisitionPrice;
       this.initialValue = initialValue;
       this.initialWeight = initialWeight;
       this.requiredValue = requiredValue;
       this.requiredUnits = requiredUnits;
       this.rebalanceRequired = rebalanceRequired;
       this.rebalanceReqdDate = rebalanceReqdDate;
       this.newAllocation = newAllocation;
       this.securityDescription = securityDescription;
       this.newToleranceNegativeRange = newToleranceNegativeRange;
       this.newTolerancePositiveRange = newTolerancePositiveRange;
       this.venueCode = venueCode;
       this.venueScriptCode = venueScriptCode;
       this.securityCode = securityCode;
       this.status = status;
       this.instrumentType = instrumentType;
       this.expirationDate = expirationDate;
       this.strikePrice = strikePrice;
       this.lastUpadteOn = lastUpadteOn;
       this.activityType = activityType;
    }
   
    public Integer getCustomerPortfolioSecuritiesAuditId() {
        return this.customerPortfolioSecuritiesAuditId;
    }
    
    public void setCustomerPortfolioSecuritiesAuditId(Integer customerPortfolioSecuritiesAuditId) {
        this.customerPortfolioSecuritiesAuditId = customerPortfolioSecuritiesAuditId;
    }
    public Integer getCustomerPortfolioSecuritiesId() {
        return this.customerPortfolioSecuritiesId;
    }
    
    public void setCustomerPortfolioSecuritiesId(Integer customerPortfolioSecuritiesId) {
        this.customerPortfolioSecuritiesId = customerPortfolioSecuritiesId;
    }
    public Integer getCustomerPortfolioId() {
        return this.customerPortfolioId;
    }
    
    public void setCustomerPortfolioId(Integer customerPortfolioId) {
        this.customerPortfolioId = customerPortfolioId;
    }
    public Integer getCustomerPortfolioDetailsId() {
        return this.customerPortfolioDetailsId;
    }
    
    public void setCustomerPortfolioDetailsId(Integer customerPortfolioDetailsId) {
        this.customerPortfolioDetailsId = customerPortfolioDetailsId;
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
    public Integer getPortfolioSecuritiesId() {
        return this.portfolioSecuritiesId;
    }
    
    public void setPortfolioSecuritiesId(Integer portfolioSecuritiesId) {
        this.portfolioSecuritiesId = portfolioSecuritiesId;
    }
    public Short getAssetClassId() {
        return this.assetClassId;
    }
    
    public void setAssetClassId(Short assetClassId) {
        this.assetClassId = assetClassId;
    }
    public String getSecurityId() {
        return this.securityId;
    }
    
    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }
    public Integer getCurrentUnits() {
        return this.currentUnits;
    }
    
    public void setCurrentUnits(Integer currentUnits) {
        this.currentUnits = currentUnits;
    }
    public Float getCurrentPrice() {
        return this.currentPrice;
    }
    
    public void setCurrentPrice(Float currentPrice) {
        this.currentPrice = currentPrice;
    }
    public Float getCurrentValue() {
        return this.currentValue;
    }
    
    public void setCurrentValue(Float currentValue) {
        this.currentValue = currentValue;
    }
    public Float getCurrentWeight() {
        return this.currentWeight;
    }
    
    public void setCurrentWeight(Float currentWeight) {
        this.currentWeight = currentWeight;
    }
    public BigDecimal getExeUnits() {
        return this.exeUnits;
    }
    
    public void setExeUnits(BigDecimal exeUnits) {
        this.exeUnits = exeUnits;
    }
    public Float getRecommentedPrice() {
        return this.recommentedPrice;
    }
    
    public void setRecommentedPrice(Float recommentedPrice) {
        this.recommentedPrice = recommentedPrice;
    }
    public Float getAvgAcquisitionPrice() {
        return this.avgAcquisitionPrice;
    }
    
    public void setAvgAcquisitionPrice(Float avgAcquisitionPrice) {
        this.avgAcquisitionPrice = avgAcquisitionPrice;
    }
    public Float getInitialValue() {
        return this.initialValue;
    }
    
    public void setInitialValue(Float initialValue) {
        this.initialValue = initialValue;
    }
    public Float getInitialWeight() {
        return this.initialWeight;
    }
    
    public void setInitialWeight(Float initialWeight) {
        this.initialWeight = initialWeight;
    }
    public Float getRequiredValue() {
        return this.requiredValue;
    }
    
    public void setRequiredValue(Float requiredValue) {
        this.requiredValue = requiredValue;
    }
    public BigDecimal getRequiredUnits() {
        return this.requiredUnits;
    }
    
    public void setRequiredUnits(BigDecimal requiredUnits) {
        this.requiredUnits = requiredUnits;
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
    public Float getNewAllocation() {
        return this.newAllocation;
    }
    
    public void setNewAllocation(Float newAllocation) {
        this.newAllocation = newAllocation;
    }
    public String getSecurityDescription() {
        return this.securityDescription;
    }
    
    public void setSecurityDescription(String securityDescription) {
        this.securityDescription = securityDescription;
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
    public String getVenueCode() {
        return this.venueCode;
    }
    
    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }
    public String getVenueScriptCode() {
        return this.venueScriptCode;
    }
    
    public void setVenueScriptCode(String venueScriptCode) {
        this.venueScriptCode = venueScriptCode;
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
    public Date getLastUpadteOn() {
        return this.lastUpadteOn;
    }
    
    public void setLastUpadteOn(Date lastUpadteOn) {
        this.lastUpadteOn = lastUpadteOn;
    }
    public String getActivityType() {
        return this.activityType;
    }
    
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }




}


