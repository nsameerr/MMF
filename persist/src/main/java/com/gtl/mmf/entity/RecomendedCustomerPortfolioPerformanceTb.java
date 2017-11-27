package com.gtl.mmf.entity;
// Generated Jan 13, 2015 2:14:55 PM by Hibernate Tools 3.2.1.GA


import java.math.BigDecimal;
import java.util.Date;

/**
 * RecomendedCustomerPortfolioPerformanceTb generated by hbm2java
 */
public class RecomendedCustomerPortfolioPerformanceTb  implements java.io.Serializable {


     private Integer customerPortfolioAllocationId;
     private CustomerPortfolioTb customerPortfolioTb;
     private Date datetime;
     private BigDecimal closingValue;
     private BigDecimal marketValue;
     private BigDecimal cashFlow;
     private BigDecimal marketValueAfterCashFlow;
     private BigDecimal subPeriodReturn;

    public RecomendedCustomerPortfolioPerformanceTb() {
    }

	
    public RecomendedCustomerPortfolioPerformanceTb(CustomerPortfolioTb customerPortfolioTb, Date datetime, BigDecimal closingValue) {
        this.customerPortfolioTb = customerPortfolioTb;
        this.datetime = datetime;
        this.closingValue = closingValue;
    }
    public RecomendedCustomerPortfolioPerformanceTb(CustomerPortfolioTb customerPortfolioTb, Date datetime, BigDecimal closingValue, BigDecimal marketValue, BigDecimal cashFlow, BigDecimal marketValueAfterCashFlow, BigDecimal subPeriodReturn) {
       this.customerPortfolioTb = customerPortfolioTb;
       this.datetime = datetime;
       this.closingValue = closingValue;
       this.marketValue = marketValue;
       this.cashFlow = cashFlow;
       this.marketValueAfterCashFlow = marketValueAfterCashFlow;
       this.subPeriodReturn = subPeriodReturn;
    }
   
    public Integer getCustomerPortfolioAllocationId() {
        return this.customerPortfolioAllocationId;
    }
    
    public void setCustomerPortfolioAllocationId(Integer customerPortfolioAllocationId) {
        this.customerPortfolioAllocationId = customerPortfolioAllocationId;
    }
    public CustomerPortfolioTb getCustomerPortfolioTb() {
        return this.customerPortfolioTb;
    }
    
    public void setCustomerPortfolioTb(CustomerPortfolioTb customerPortfolioTb) {
        this.customerPortfolioTb = customerPortfolioTb;
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




}


