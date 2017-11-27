/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class PortfolioRebalanceTriggerVO {

    private int portfolioId;
    private String portfolioName;
    private int assetClassId;
    private String assetClassName;
    private String allocation;
    private Double currentLevel;
    private String dateBreached;

    public int getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(int portfolioId) {
        this.portfolioId = portfolioId;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public int getAssetClassId() {
        return assetClassId;
    }

    public void setAssetClassId(int assetClassId) {
        this.assetClassId = assetClassId;
    }

    public String getAssetClassName() {
        return assetClassName;
    }

    public void setAssetClassName(String assetClassName) {
        this.assetClassName = assetClassName;
    }

    public String getAllocation() {
        return allocation;
    }

    public void setAllocation(String allocation) {
        this.allocation = allocation;
    }

    public Double getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(Double currentLevel) {
        this.currentLevel = currentLevel;
    }

    public String getDateBreached() {
        return dateBreached;
    }

    public void setDateBreached(String dateBreached) {
        this.dateBreached = dateBreached;
    }
}
