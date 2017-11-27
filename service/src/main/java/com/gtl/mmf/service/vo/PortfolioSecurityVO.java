/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import static com.gtl.mmf.service.util.IConstants.CMP_TEXT_COLOR_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.DECIMALPLACES_VALUE;
import static com.gtl.mmf.service.util.IConstants.PRICE_TOLERANCE_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SECURITY_ACTIVE_STATUS;
import static com.gtl.mmf.service.util.IConstants.SECURITY_ALLOCATION_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Date;

/**
 *
 * @author 07958
 */
public class PortfolioSecurityVO {

    private Integer portfolioSecurityId;
    private Integer portfolioId;
    private Integer portfolioDetailsId;
    private Short assetClassId;
    private String securityId;
    private Double expectedReturns;
    private Double standardDeviation;
    private Double currentValue;
    private Double initialValue;
    private Double newAllocation;
    private Double initialAllocation;
    private Double initialWeight;
    private Double initialPrice;
    private Double currentPrice;
    private Double newUnits;
//    private Integer initialUnits;
    private Integer newToleranceNegativeRange;
    private Integer newTolerancePositiveRange;
    private Integer initialToleranceNegativeRange;
    private Integer initialTolerancePositiveRange;
    private Boolean rebalanceRequiered;
    private Date rebalanceRequieredDate;
    private String assetClass;
    private String scripDecription;
    private String buysellStatusText;
    private int buysellStatusValue;
    private String venueCode;
    private String venueScriptCode;
    private String instrumentType;
    private Double strikePrice;
    private Date expirationdate;
    private Integer customerPortfolioId;
    private String securityLabel;
    private String securityCode;
    private Double currentWeight;
    private Integer currentUnits;
    private SymbolRecordVO symbolRecordVO;
    private Integer benchMark;
    private Double transactUnits;
    private Double transactAmount;
    private Double transactionAmount;
    private Float recommendedPrice;
    private Double executedUnits;
    private String cmpTextColor;
    private String  buySellTextColor;
    // Check for security price in range
    private Boolean priceNotInRange;
    private Boolean status;
    private Boolean allocatedPreviously;
    private String tradeSignal;
    private Double minInvestment;
    private Float boughtPrice;
    private Double acquisitonValue;
    private Double totalGainLoss;
    private boolean gainOrLoss;
    private Double acquisitionCost;
    private Double clientTWR;
    private Double recommendedTWR;
    private Double clientportfolioPercentage;
    private Double masterportfolioPercentage;
    private Double portfolio_currentValue;
    private Double dashbrd_return;
    private Float cash_amount;
    private Float blockedcash;
    private Double blockedCount;

    public Float getCash_amount() {
        return cash_amount;
    }

    public void setCash_amount(Float cash_amount) {
        this.cash_amount = cash_amount;
    }

    public Double getPortfolio_currentValue() {
        return portfolio_currentValue;
    }

    public void setPortfolio_currentValue(Double portfolio_currentValue) {
        this.portfolio_currentValue = portfolio_currentValue;
    }

    public Double getDashbrd_return() {
        return dashbrd_return;
    }

    public void setDashbrd_return(Double dashbrd_return) {
        this.dashbrd_return = dashbrd_return;
    }

    public PortfolioSecurityVO() {
        newToleranceNegativeRange = PRICE_TOLERANCE_DEFAULT;
        newTolerancePositiveRange = PRICE_TOLERANCE_DEFAULT;
        newAllocation = SECURITY_ALLOCATION_DEFAULT;
        cmpTextColor = CMP_TEXT_COLOR_DEFAULT;
        priceNotInRange = false;
        status = SECURITY_ACTIVE_STATUS;
        allocatedPreviously = false;
        executedUnits = ZERO_POINT_ZERO;
        initialPrice = ZERO_POINT_ZERO;
    }

    /**
     * Creating portfolio securities for saving
     * @return mapping object corresponding to the security assigned in the particular asset class
     */
    public PortfolioSecuritiesTb toPortfolioSecuritiesTb() {
        PortfolioSecuritiesTb portfolioSecuritiesTb = new PortfolioSecuritiesTb();
       
        // Set asset class type
        MasterAssetTb masterAssetTb = new MasterAssetTb();
        masterAssetTb.setId(assetClassId);
        portfolioSecuritiesTb.setMasterAssetTb(masterAssetTb);
      
        // Setting securityId, currentPrice, newTolerancePositiveRange, portfolioSecurityId, newAllocation, scripDescription
        portfolioSecuritiesTb.setSecurityId(securityId);
        portfolioSecuritiesTb.setCurrentPrice(BigDecimal.valueOf(currentPrice == null ? ZERO_POINT_ZERO : currentPrice).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioSecuritiesTb.setRecommentedPrice(currentPrice == null ? ZERO: currentPrice.floatValue());
        portfolioSecuritiesTb.setNewTolerancePositiveRange(newTolerancePositiveRange);
        portfolioSecuritiesTb.setNewToleranceNegativeRange(newTolerancePositiveRange);
        portfolioSecuritiesTb.setPortfolioSecuritiesId(portfolioSecurityId);
        portfolioSecuritiesTb.setNewAllocation(BigDecimal.valueOf(newAllocation));
        portfolioSecuritiesTb.setSecurityDescription(securityLabel);
        portfolioSecuritiesTb.setVenueCode(venueCode);
        portfolioSecuritiesTb.setVenueScriptCode(venueScriptCode);
        portfolioSecuritiesTb.setInstrumentType(instrumentType);
        portfolioSecuritiesTb.setExpirationDate(expirationdate);
        portfolioSecuritiesTb.setStrikePrice(strikePrice == null ? BigDecimal.ZERO : BigDecimal.valueOf(strikePrice));
        portfolioSecuritiesTb.setSecurityCode(securityCode);
        portfolioSecuritiesTb.setExeUnits(new BigDecimal(executedUnits));
        portfolioSecuritiesTb.setCurrentValue(BigDecimal.valueOf(currentValue).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioSecuritiesTb.setInitialValue(BigDecimal.valueOf(currentValue).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioSecuritiesTb.setCurrentWeight(BigDecimal.valueOf(currentWeight).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioSecuritiesTb.setInitialWeight(BigDecimal.valueOf(currentWeight).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioSecuritiesTb.setStatus(status);
        return portfolioSecuritiesTb;
    }

    
    public SymbolRecordVO getSymbolRecordVO() {
        return symbolRecordVO;
    }

    public void setSymbolRecordVO(SymbolRecordVO symbolRecordVO) {
        this.symbolRecordVO = symbolRecordVO;
    }
    
    public Integer getPortfolioSecurityId() {
        return portfolioSecurityId;
    }

    public void setPortfolioSecurityId(Integer portfolioSecurityId) {
        this.portfolioSecurityId = portfolioSecurityId;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Integer getPortfolioDetailsId() {
        return portfolioDetailsId;
    }

    public void setPortfolioDetailsId(Integer portfolioDetailsId) {
        this.portfolioDetailsId = portfolioDetailsId;
    }

    public Short getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(Short assetClassId) {
        this.assetClassId = assetClassId;
    }

    public String getSecurityId() {
        return securityId;
    }

    public void setSecurityId(String securityId) {
        this.securityId = securityId;
    }

    public Double getExpectedReturns() {
        return expectedReturns;
    }

    public void setExpectedReturns(Double expectedReturns) {
        this.expectedReturns = expectedReturns;
    }

    public Double getStandardDeviation() {
        return standardDeviation;
    }

    public void setStandardDeviation(Double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getInitialValue() {
        return initialValue;
    }

    public void setInitialValue(Double initialValue) {
        this.initialValue = initialValue;
    }

    public Double getNewAllocation() {
        return newAllocation;
    }

    public void setNewAllocation(Double newAllocation) {
        this.newAllocation = newAllocation;
    }

    public Double getInitialWeight() {
        return initialWeight;
    }

    public void setInitialWeight(Double initialWeight) {
        this.initialWeight = initialWeight;
    }

    public Double getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(Double initialPrice) {
        this.initialPrice = initialPrice;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice == null ? ZERO_POINT_ZERO : currentPrice;
    }

    public Double getNewUnits() {
        return newUnits;
    }

    public void setNewUnits(Double newUnits) {
        this.newUnits = newUnits;
    }

//    public Integer getInitialUnits() {
//        return initialUnits;
//    }
//
//    public void setInitialUnits(Integer initialUnits) {
//        this.initialUnits = initialUnits;
//    }
    public Integer getNewToleranceNegativeRange() {
        return newToleranceNegativeRange;
    }

    public void setNewToleranceNegativeRange(Integer newToleranceNegativeRange) {
        this.newToleranceNegativeRange = newToleranceNegativeRange;
    }

    public Integer getNewTolerancePositiveRange() {
        return newTolerancePositiveRange;
    }

    public void setNewTolerancePositiveRange(Integer newTolerancePositiveRange) {
        this.newTolerancePositiveRange = newTolerancePositiveRange;
    }

    public Integer getInitialToleranceNegativeRange() {
        return initialToleranceNegativeRange;
    }

    public void setInitialToleranceNegativeRange(Integer initialToleranceNegativeRange) {
        this.initialToleranceNegativeRange = initialToleranceNegativeRange;
    }

    public Integer getInitialTolerancePositiveRange() {
        return initialTolerancePositiveRange;
    }

    public void setInitialTolerancePositiveRange(Integer initialTolerancePositiveRange) {
        this.initialTolerancePositiveRange = initialTolerancePositiveRange;
    }

    public Boolean getRebalanceRequiered() {
        return rebalanceRequiered;
    }

    public void setRebalanceRequiered(Boolean rebalanceRequiered) {
        this.rebalanceRequiered = rebalanceRequiered;
    }

    public Date getRebalanceRequieredDate() {
        return rebalanceRequieredDate;
    }

    public void setRebalanceRequieredDate(Date rebalanceRequieredDate) {
        this.rebalanceRequieredDate = rebalanceRequieredDate;
    }

    public String getAssetClass() {
        return assetClass;
    }

    public void setAssetClass(String assetClass) {
        this.assetClass = assetClass;
    }

    public Double getInitialAllocation() {
        return initialAllocation;
    }

    public void setInitialAllocation(Double initialAllocation) {
        this.initialAllocation = initialAllocation;
    }

    public String getScripDecription() {
        return scripDecription;
    }

    public void setScripDecription(String scripDecription) {
        this.scripDecription = scripDecription;
    }

    public String getBuysellStatusText() {
        return buysellStatusText;
    }

    public void setBuysellStatusText(String buysellStatusText) {
        this.buysellStatusText = buysellStatusText;
    }

    public int getBuysellStatusValue() {
        return buysellStatusValue;
    }

    public void setBuysellStatusValue(int buysellStatusValue) {
        this.buysellStatusValue = buysellStatusValue;
    }

    public String getVenueCode() {
        return venueCode;
    }

    public void setVenueCode(String venueCode) {
        this.venueCode = venueCode;
    }

    public String getVenueScriptCode() {
        return venueScriptCode;
    }

    public void setVenueScriptCode(String venueScriptCode) {
        this.venueScriptCode = venueScriptCode;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public Double getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(Double strikePrice) {
        this.strikePrice = strikePrice;
    }

    public Date getExpirationdate() {
        return expirationdate;
    }

    public void setExpirationdate(Date expirationdate) {
        this.expirationdate = expirationdate;
    }

    public Integer getCustomerPortfolioId() {
        return customerPortfolioId;
    }

    public void setCustomerPortfolioId(Integer customerPortfolioId) {
        this.customerPortfolioId = customerPortfolioId;
    }

    public String getSecurityLabel() {
        return securityLabel;
    }

    public void setSecurityLabel(String securityLabel) {
        this.securityLabel = securityLabel;
    }

    public String getSecurityCode() {
        return securityCode;
    }

    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Integer getCurrentUnits() {
        return currentUnits;
    }

    public void setCurrentUnits(Integer currentUnits) {
        this.currentUnits = currentUnits;
    }

    public Integer getBenchMark() {
        return benchMark;
    }

    public void setBenchMark(Integer benchMark) {
        this.benchMark = benchMark;
    }

    public Double getTransactUnits() {
        return transactUnits;
    }

    public void setTransactUnits(Double transactUnits) {
        this.transactUnits = transactUnits;
    }

    public Double getTransactAmount() {
        return transactAmount;
    }

    public void setTransactAmount(Double transactAmount) {
        this.transactAmount = transactAmount;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(Double transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Float getRecommendedPrice() {
        return recommendedPrice;
    }

    public void setRecommendedPrice(Float recommendedPrice) {
        this.recommendedPrice = recommendedPrice;
    }

    public Double getExecutedUnits() {
        return executedUnits;
    }

    public void setExecutedUnits(Double executedUnits) {
        this.executedUnits = executedUnits;
    }

    public String getCmpTextColor() {
        return cmpTextColor;
    }

    public void setCmpTextColor(String cmpTextColor) {
        this.cmpTextColor = cmpTextColor;
    }

    public Boolean getPriceNotInRange() {
        return priceNotInRange;
    }

    public void setPriceNotInRange(Boolean priceInRange) {
        this.priceNotInRange = priceInRange;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Boolean getAllocatedPreviously() {
        return allocatedPreviously;
    }

    public void setAllocatedPreviously(Boolean allocatedPreviously) {
        this.allocatedPreviously = allocatedPreviously;
    }

    public Double getAcquisitionCost() {
        return acquisitionCost;
    }

    public void setAcquisitionCost(Double acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }

    public Double getClientTWR() {
        return clientTWR;
    }

    public void setClientTWR(Double clientTWR) {
        this.clientTWR = clientTWR;
    }

    public Double getRecommendedTWR() {
        return recommendedTWR;
    }

    public void setRecommendedTWR(Double recommendedTWR) {
        this.recommendedTWR = recommendedTWR;
    }

    public Double getClientportfolioPercentage() {
        return clientportfolioPercentage;
    }

    public void setClientportfolioPercentage(Double clientportfolioPercentage) {
        this.clientportfolioPercentage = clientportfolioPercentage;
    }

    public Double getMasterportfolioPercentage() {
        return masterportfolioPercentage;
    }

    public void setMasterportfolioPercentage(Double masterportfolioPercentage) {
        this.masterportfolioPercentage = masterportfolioPercentage;
    }

    public String getBuySellTextColor() {
        return buySellTextColor;
    }

    public void setBuySellTextColor(String buySellTextColor) {
        this.buySellTextColor = buySellTextColor;
    }

    public String getTradeSignal() {
        return tradeSignal;
    }

    public void setTradeSignal(String tradeSignal) {
        this.tradeSignal = tradeSignal;
    }

    public Double getMinInvestment() {
        return minInvestment;
    }

    public void setMinInvestment(Double minInvetment) {
        this.minInvestment = minInvetment;
    }

    public Float getBoughtPrice() {
        return boughtPrice;
    }

    public void setBoughtPrice(Float boughtPrice) {
        this.boughtPrice = boughtPrice;
    }
    
    public Double getAcquisitonValue() {
        return acquisitonValue;
    }

    public void setAcquisitonValue(Double acquisitonValue) {
        this.acquisitonValue = acquisitonValue;
    }

    public boolean isGainOrLoss() {
        return gainOrLoss;
    }

    public void setGainOrLoss(boolean gainOrLoss) {
        this.gainOrLoss = gainOrLoss;
    }

    public Double getTotalGainLoss() {
        return totalGainLoss;
    }

    public void setTotalGainLoss(Double totalGainLoss) {
        this.totalGainLoss = totalGainLoss;
    }

    public Float getBlockedcash() {
        return blockedcash;
    }

    public void setBlockedcash(Float blockedcash) {
        this.blockedcash = blockedcash;
    }

    public Double getBlockedCount() {
        return blockedCount;
    }

    public void setBlockedCount(Double blockedCount) {
        this.blockedCount = blockedCount;
    }

   
    
    
}
