/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

import static com.gtl.mmf.service.util.IConstants.DECIMALPLACES_VALUE;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author 07958
 */
public class PortfolioDetailsVO {

    private Integer portfolioDetailsId;
    private Short assetId;
    private Double newAllocation;
    private String range;
    private List<PortfolioSecurityVO> securities;
    private Double currentValue;
    private Double existingWeight;
    private Double initialAllocation;
    private Double initialSetPrice;
    private Double maximumRange;
    private Double minimumRange;
    private String assetClassName;
    private Double currentWeight;
    private Float currentAllocation;
    // Check for portfolio weight within range.
    private Boolean weightNotInRange;
    private Double customerTWR;
    private BigDecimal marketValue;
    private Double recommmendedTWR;
    private Float acquisitionValue;
    private Set customerTwrPortfolioReturnTbs = new HashSet(0);
    private Double totalgainrLoss; 
    private Double acquisitionCost;

    public PortfolioDetailsVO() {
        currentValue = ZERO_POINT_ZERO;
        newAllocation = ZERO_POINT_ZERO;
        securities = new ArrayList<PortfolioSecurityVO>();
        weightNotInRange = false;
    }

    /**
     * This method convert portfoliodetailVo into PortfolioDetailsTb for saving into DB
     * @return Object which contain portfolio details for a particular asset class.
     */
    public PortfolioDetailsTb toPortfolioDetailsTb() {
        PortfolioDetailsTb portfolioDetailsTb = new PortfolioDetailsTb();
        portfolioDetailsTb.setCurrentValue(BigDecimal.valueOf(currentValue));
        portfolioDetailsTb.setNewAllocation(BigDecimal.valueOf(newAllocation));
        portfolioDetailsTb.setPortfolioDetailsId(portfolioDetailsId);
        portfolioDetailsTb.setRangeFrom(BigDecimal.valueOf(minimumRange));
        portfolioDetailsTb.setRangeTo(BigDecimal.valueOf(maximumRange));
        portfolioDetailsTb.setCurrentValue(BigDecimal.valueOf(currentValue).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioDetailsTb.setCurrentWeight(BigDecimal.valueOf(currentWeight == null ? ZERO_POINT_ZERO : currentWeight).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        portfolioDetailsTb.setInitialWeight(BigDecimal.valueOf(currentWeight == null ? ZERO_POINT_ZERO : currentWeight).setScale(DECIMALPLACES_VALUE, RoundingMode.DOWN));
        return portfolioDetailsTb;
    }

    /**
     * This method Select securities assigned in the each asset class into
       PortfolioSecuritiesTb for saving in DB
     * @param portfolioIndex - ID of portfolio created
     * @return List of securities in a selected portfolio asset class
     */
    public List<PortfolioSecuritiesTb> toPortfolioSecuritiesTbs(Integer portfolioIndex) {
        List<PortfolioSecuritiesTb> portfolioSecuritiesTbs = new ArrayList<PortfolioSecuritiesTb>();
        for (PortfolioSecurityVO securityItem : securities) {
//            if (securityItem.getStatus()) {
            PortfolioSecuritiesTb portfolioSecuritiesTb = securityItem.toPortfolioSecuritiesTb();
            PortfolioDetailsTb portfolioDetailsTb = new PortfolioDetailsTb();
            portfolioDetailsTb.setPortfolioDetailsId(portfolioDetailsId);
            portfolioSecuritiesTb.setPortfolioDetailsTb(portfolioDetailsTb);
            PortfolioTb portfolioTb = new PortfolioTb();
            portfolioTb.setPortfolioId(portfolioIndex);
            portfolioSecuritiesTb.setPortfolioTb(portfolioTb);
            portfolioSecuritiesTbs.add(portfolioSecuritiesTb);
            this.currentValue += securityItem.getCurrentPrice() == null ? ZERO_POINT_ZERO:securityItem.getCurrentPrice();
//            }
        }
        return portfolioSecuritiesTbs;
    }

    public Short getAssetId() {
        return assetId;
    }

    public void setAssetId(Short assetId) {
        this.assetId = assetId;
    }

    public Double getNewAllocation() {
        return newAllocation;
    }

    public void setNewAllocation(Double newAllocation) {
        this.newAllocation = newAllocation;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<PortfolioSecurityVO> getSecurities() {
        return securities;
    }

    public void setSecurities(List<PortfolioSecurityVO> securities) {
        this.securities = securities;
    }

    public Integer getPortfolioDetailsId() {
        return portfolioDetailsId;
    }

    public void setPortfolioDetailsId(Integer portfolioDetailsId) {
        this.portfolioDetailsId = portfolioDetailsId;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getExistingWeight() {
        return existingWeight;
    }

    public void setExistingWeight(Double existingWeight) {
        this.existingWeight = existingWeight;
    }

    public Double getInitialAllocation() {
        return initialAllocation;
    }

    public void setInitialAllocation(Double initialAllocation) {
        this.initialAllocation = initialAllocation;
    }

    public Double getInitialSetPrice() {
        return initialSetPrice;
    }

    public void setInitialSetPrice(Double initialSetPrice) {
        this.initialSetPrice = initialSetPrice;
    }

    public Double getMaximumRange() {
        return maximumRange;
    }

    public void setMaximumRange(Double maximumRange) {
        this.maximumRange = maximumRange;
    }

    public Double getMinimumRange() {
        return minimumRange;
    }

    public void setMinimumRange(Double minimumRange) {
        this.minimumRange = minimumRange;
    }

    public String getAssetClassName() {
        return assetClassName;
    }

    public void setAssetClassName(String assetClassName) {
        this.assetClassName = assetClassName;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public Float getCurrentAllocation() {
        return currentAllocation;
    }

    public void setCurrentAllocation(Float currentAllocation) {
        this.currentAllocation = currentAllocation;
    }

    public Boolean getWeightNotInRange() {
        return weightNotInRange;
    }

    public void setWeightNotInRange(Boolean weightNotInRange) {
        this.weightNotInRange = weightNotInRange;
    }

    public Double getCustomerTWR() {
        return customerTWR;
    }

    public void setCustomerTWR(Double customerTWR) {
        this.customerTWR = customerTWR;
    }

    public BigDecimal getMarketValue() {
        return marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public Double getRecommmendedTWR() {
        return recommmendedTWR;
    }

    public void setRecommmendedTWR(Double recommmendedTWR) {
        this.recommmendedTWR = recommmendedTWR;
    }

    public Float getAcquisitionValue() {
        return acquisitionValue;
    }

    public void setAcquisitionValue(Float acquisitionValue) {
        this.acquisitionValue = acquisitionValue;
    }

    public Set getCustomerTwrPortfolioReturnTbs() {
        return customerTwrPortfolioReturnTbs;
    }

    public void setCustomerTwrPortfolioReturnTbs(Set customerTwrPortfolioReturnTbs) {
        this.customerTwrPortfolioReturnTbs = customerTwrPortfolioReturnTbs;
    }
   public Double getTotalgainrLoss() {
        return totalgainrLoss;
    }

    public void setTotalgainrLoss(Double totalgainrLoss) {
        this.totalgainrLoss = totalgainrLoss;
    }

    public Double getAcquisitionCost() {
        return acquisitionCost;
    }

    public void setAcquisitionCost(Double acquisitionCost) {
        this.acquisitionCost = acquisitionCost;
    }
}
