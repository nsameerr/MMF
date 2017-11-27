/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.persist.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 07958
 */
public class RebalanceStateVO {

    private Integer portfolioId;
    private Boolean rebalanceRequired;
    private Date rebalanceRequiredDate;
    private Double currentPrice;
    private Double currentValue;
    private boolean rebalanceViwed;
    
    private List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs;
    private List<RebalanceStateAssetVO> rebalanceStateAssetVOs;

    public RebalanceStateVO() {
        rebalanceStateAssetVOs = new ArrayList<RebalanceStateAssetVO>();
        rebalanceStateSecurityVOs = new ArrayList<RebalanceStateSecurityVO>();
    }

    public RebalanceStateVO(Integer portfolioId, Boolean rebalanceRequired, Date rebalanceRequiredDate) {
        this.portfolioId = portfolioId;
        this.rebalanceRequired = rebalanceRequired;
        this.rebalanceRequiredDate = rebalanceRequiredDate;
        rebalanceStateAssetVOs = new ArrayList<RebalanceStateAssetVO>();
        rebalanceStateAssetVOs = new ArrayList<RebalanceStateAssetVO>();
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public void setRebalanceRequired(Boolean rebalanceRequired) {
        this.rebalanceRequired = rebalanceRequired;
    }

    public void setRebalanceRequiredDate(Date rebalanceRequiredDate) {
        this.rebalanceRequiredDate = rebalanceRequiredDate;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public Boolean getRebalanceRequired() {
        return rebalanceRequired;
    }

    public Date getRebalanceRequiredDate() {
        return rebalanceRequiredDate;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public List<RebalanceStateSecurityVO> getRebalanceStateSecurityVOs() {
        return rebalanceStateSecurityVOs;
    }

    public void setRebalanceStateSecurityVOs(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs) {
        this.rebalanceStateSecurityVOs = rebalanceStateSecurityVOs;
    }

    public List<RebalanceStateAssetVO> getRebalanceStateAssetVOs() {
        return rebalanceStateAssetVOs;
    }

    public void setRebalanceStateAssetVOs(List<RebalanceStateAssetVO> rebalanceStateAssetVOs) {
        this.rebalanceStateAssetVOs = rebalanceStateAssetVOs;
    }

    public boolean isRebalanceViwed() {
        return rebalanceViwed;
    }

    public void setRebalanceViwed(boolean rebalanceViwed) {
        this.rebalanceViwed = rebalanceViwed;
    }
    
    
}
