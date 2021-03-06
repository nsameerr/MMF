package com.gtl.mmf.entity;
// Generated Jun 11, 2014 3:19:30 PM by Hibernate Tools 3.2.1.GA

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * CustomerPortfolioDetailsPerformanceTb generated by hbm2java
 */
public class CustomerPortfolioDetailsPerformanceTb implements java.io.Serializable {

    private Integer customerPortfolioDetailsAllocationId;
    private CustomerPortfolioDetailsTb customerPortfolioDetailsTb;
    private MasterAssetTb masterAssetTb;
    private CustomerPortfolioTb customerPortfolioTb;
    private CustomerPortfolioPerformanceTb customerPortfolioPerformanceTb;
    private Date datetime;
    private BigDecimal closingValue;
    private BigDecimal marketValue;
    private BigDecimal cashFlow;
    private BigDecimal marketValueAfterCashFlow;
    private BigDecimal subPeriodReturn;
    private Set customerPortfolioSecuritiesPerformanceTbs = new HashSet(0);

    public CustomerPortfolioDetailsPerformanceTb() {
    }

    public CustomerPortfolioDetailsPerformanceTb(CustomerPortfolioDetailsTb customerPortfolioDetailsTb, MasterAssetTb masterAssetTb, CustomerPortfolioTb customerPortfolioTb, CustomerPortfolioPerformanceTb customerPortfolioPerformanceTb, Date datetime, BigDecimal closingValue) {
        this.customerPortfolioDetailsTb = customerPortfolioDetailsTb;
        this.masterAssetTb = masterAssetTb;
        this.customerPortfolioTb = customerPortfolioTb;
        this.customerPortfolioPerformanceTb = customerPortfolioPerformanceTb;
        this.datetime = datetime;
        this.closingValue = closingValue;
    }

    public CustomerPortfolioDetailsPerformanceTb(CustomerPortfolioDetailsTb customerPortfolioDetailsTb, MasterAssetTb masterAssetTb, CustomerPortfolioTb customerPortfolioTb, CustomerPortfolioPerformanceTb customerPortfolioPerformanceTb, Date datetime, BigDecimal closingValue, BigDecimal marketValue, BigDecimal cashFlow, BigDecimal marketValueAfterCashFlow, BigDecimal subPeriodReturn, Set customerPortfolioSecuritiesPerformanceTbs) {
        this.customerPortfolioDetailsTb = customerPortfolioDetailsTb;
        this.masterAssetTb = masterAssetTb;
        this.customerPortfolioTb = customerPortfolioTb;
        this.customerPortfolioPerformanceTb = customerPortfolioPerformanceTb;
        this.datetime = datetime;
        this.closingValue = closingValue;
        this.marketValue = marketValue;
        this.cashFlow = cashFlow;
        this.marketValueAfterCashFlow = marketValueAfterCashFlow;
        this.subPeriodReturn = subPeriodReturn;
        this.customerPortfolioSecuritiesPerformanceTbs = customerPortfolioSecuritiesPerformanceTbs;
    }

    public Integer getCustomerPortfolioDetailsAllocationId() {
        return this.customerPortfolioDetailsAllocationId;
    }

    public void setCustomerPortfolioDetailsAllocationId(Integer customerPortfolioDetailsAllocationId) {
        this.customerPortfolioDetailsAllocationId = customerPortfolioDetailsAllocationId;
    }

    public CustomerPortfolioDetailsTb getCustomerPortfolioDetailsTb() {
        return this.customerPortfolioDetailsTb;
    }

    public void setCustomerPortfolioDetailsTb(CustomerPortfolioDetailsTb customerPortfolioDetailsTb) {
        this.customerPortfolioDetailsTb = customerPortfolioDetailsTb;
    }

    public MasterAssetTb getMasterAssetTb() {
        return this.masterAssetTb;
    }

    public void setMasterAssetTb(MasterAssetTb masterAssetTb) {
        this.masterAssetTb = masterAssetTb;
    }

    public CustomerPortfolioTb getCustomerPortfolioTb() {
        return this.customerPortfolioTb;
    }

    public void setCustomerPortfolioTb(CustomerPortfolioTb customerPortfolioTb) {
        this.customerPortfolioTb = customerPortfolioTb;
    }

    public CustomerPortfolioPerformanceTb getCustomerPortfolioPerformanceTb() {
        return this.customerPortfolioPerformanceTb;
    }

    public void setCustomerPortfolioPerformanceTb(CustomerPortfolioPerformanceTb customerPortfolioPerformanceTb) {
        this.customerPortfolioPerformanceTb = customerPortfolioPerformanceTb;
    }

    public Date getDatetime() {
        return this.datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public BigDecimal getClosingValue() {
        return this.closingValue;
    }

    public void setClosingValue(BigDecimal closingValue) {
        this.closingValue = closingValue;
    }

    public BigDecimal getMarketValue() {
        return this.marketValue;
    }

    public void setMarketValue(BigDecimal marketValue) {
        this.marketValue = marketValue;
    }

    public BigDecimal getCashFlow() {
        return this.cashFlow;
    }

    public void setCashFlow(BigDecimal cashFlow) {
        this.cashFlow = cashFlow;
    }

    public BigDecimal getMarketValueAfterCashFlow() {
        return this.marketValueAfterCashFlow;
    }

    public void setMarketValueAfterCashFlow(BigDecimal marketValueAfterCashFlow) {
        this.marketValueAfterCashFlow = marketValueAfterCashFlow;
    }

    public BigDecimal getSubPeriodReturn() {
        return this.subPeriodReturn;
    }

    public void setSubPeriodReturn(BigDecimal subPeriodReturn) {
        this.subPeriodReturn = subPeriodReturn;
    }

    public Set getCustomerPortfolioSecuritiesPerformanceTbs() {
        return this.customerPortfolioSecuritiesPerformanceTbs;
    }

    public void setCustomerPortfolioSecuritiesPerformanceTbs(Set customerPortfolioSecuritiesPerformanceTbs) {
        this.customerPortfolioSecuritiesPerformanceTbs = customerPortfolioSecuritiesPerformanceTbs;
    }

}
