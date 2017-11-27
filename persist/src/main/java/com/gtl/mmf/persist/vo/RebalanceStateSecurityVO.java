/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.persist.vo;

import java.util.Date;

/**
 *
 * @author 07958
 */
public class RebalanceStateSecurityVO {

    private int securityId;
    private int assetId;
    private Double currentPrice;
    private Double currentValue;
    private Double currentWeight;
    private boolean rebalanceRequired;
    private Date rebalanceRequiredDate;
    private Double yesterDayUnitCount;

    public RebalanceStateSecurityVO() {
        rebalanceRequired = false;
    }

    public int getSecurityId() {
        return securityId;
    }

    public void setSecurityId(int securityId) {
        this.securityId = securityId;
    }

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice == null ? 0.0 : currentPrice;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public boolean isRebalanceRequired() {
        return rebalanceRequired;
    }

    public void setRebalanceRequired(boolean rebalanceRequired) {
        this.rebalanceRequired = rebalanceRequired;
    }

    public Date getRebalanceRequiredDate() {
        return rebalanceRequiredDate;
    }

    public void setRebalanceRequiredDate(Date rebalanceRequiredDate) {
        this.rebalanceRequiredDate = rebalanceRequiredDate;
    }

    public Double getYesterDayUnitCount() {
        return yesterDayUnitCount;
    }

    public void setYesterDayUnitCount(Double yesterDayUnitCount) {
        this.yesterDayUnitCount = yesterDayUnitCount;
    }

}
