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
public class RebalanceStateAssetVO {

    private int assetId;
    private double currentValue;
    private double currentWeight;
    private boolean rebalanceRequired;
    private Date rebalanceRequiredDate;

    public int getAssetId() {
        return assetId;
    }

    public void setAssetId(int assetId) {
        this.assetId = assetId;
    }

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double currentValue) {
        this.currentValue = currentValue;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(double currentWeight) {
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
}
