package com.gtl.mmf.entity;
// Generated Oct 12, 2016 5:40:24 PM by Hibernate Tools 4.3.1

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * PortfolioTb generated by hbm2java
 */
public class PortfolioTb implements java.io.Serializable {

    private Integer portfolioId;
    private MasterAdvisorTb masterAdvisorTb;
    private MasterBenchmarkIndexTb masterBenchmarkIndexTb;
    private MasterPortfolioTypeTb masterPortfolioTypeTb;
    private String portfolioName;
    private BigDecimal stdDev;
    private Integer sharpeRatio;
    private BigDecimal expReturns;
    private BigDecimal alpha;
    private BigDecimal beta;
    private BigDecimal infoRatio;
    private Byte RSquared;
    private BigDecimal vaR;
    private BigDecimal portfolio90DayReturns;
    private BigDecimal portfolio1YearReturns;
    private BigDecimal benchmark90DaysReturns;
    private BigDecimal benchmark1YearReturns;
    private Integer noOfCustomersAssigned;
    private Boolean rebalanceRequired;
    private Date rebalanceReqdDate;
    private Date lastUpdated;
    private Date dateCreated;
    private Date startCurrentPeriod;
    private Boolean status;
    private BigDecimal portfolioValue;
    private Boolean rebalanceViewed;
    private Double balanceCash;
    private BigDecimal portfolio5DayReturn;
    private BigDecimal portfolio1MonthReturn;
    private BigDecimal portfolio5YearReturn;
    private BigDecimal portfolio10YearReturn;
    private BigDecimal portfolio6MonthReturn;
    private BigDecimal portflioYtdReturn;
    private BigDecimal sinceInception;
    private Set portfolioSecuritiesTbs = new HashSet(0);
    private Set portfolioDetailsTbs = new HashSet(0);
    private Set customerPortfolioSecuritiesTbs = new HashSet(0);
    private Set customerDailyCashBalanceTbs = new HashSet(0);
    private Set customerTransactionOrderDetailsTbs = new HashSet(0);
    private Set customerPortfolioDetailsTbs = new HashSet(0);
    private Set portfolioSecuritiesPerformanceTbs = new HashSet(0);
    private Set recomendedCustomerPortfolioSecuritiesTbs = new HashSet(0);
    private Set portfolioDetailsPerformanceTbs = new HashSet(0);
    private Set customerPortfolioTbs = new HashSet(0);
    private Set portfolioPerformanceTbs = new HashSet(0);
    private Set customerTransactionExecutionDetailsTbs = new HashSet(0);

    public PortfolioTb() {
    }

    public PortfolioTb(MasterAdvisorTb masterAdvisorTb) {
        this.masterAdvisorTb = masterAdvisorTb;
    }

    public PortfolioTb(MasterAdvisorTb masterAdvisorTb, MasterBenchmarkIndexTb masterBenchmarkIndexTb, MasterPortfolioTypeTb masterPortfolioTypeTb, String portfolioName, BigDecimal stdDev, Integer sharpeRatio, BigDecimal expReturns, BigDecimal alpha, BigDecimal beta, BigDecimal infoRatio, Byte RSquared, BigDecimal vaR, BigDecimal portfolio90DayReturns, BigDecimal portfolio1YearReturns, BigDecimal benchmark90DaysReturns, BigDecimal benchmark1YearReturns, Integer noOfCustomersAssigned, Boolean rebalanceRequired, Date rebalanceReqdDate, Date lastUpdated, Date dateCreated, Date startCurrentPeriod, Boolean status, BigDecimal portfolioValue, Boolean rebalanceViewed, Double balanceCash, BigDecimal portfolio5DayReturn, BigDecimal portfolio1MonthReturn, BigDecimal portfolio5YearReturn, BigDecimal portfolio10YearReturn, BigDecimal portfolio6MonthReturn, BigDecimal portflioYtdReturn, BigDecimal sinceInception, Set portfolioSecuritiesTbs, Set portfolioDetailsTbs, Set customerPortfolioSecuritiesTbs, Set customerDailyCashBalanceTbs, Set customerTransactionOrderDetailsTbs, Set customerPortfolioDetailsTbs, Set portfolioSecuritiesPerformanceTbs, Set recomendedCustomerPortfolioSecuritiesTbs, Set portfolioDetailsPerformanceTbs, Set customerPortfolioTbs, Set portfolioPerformanceTbs, Set customerTransactionExecutionDetailsTbs) {
        this.masterAdvisorTb = masterAdvisorTb;
        this.masterBenchmarkIndexTb = masterBenchmarkIndexTb;
        this.masterPortfolioTypeTb = masterPortfolioTypeTb;
        this.portfolioName = portfolioName;
        this.stdDev = stdDev;
        this.sharpeRatio = sharpeRatio;
        this.expReturns = expReturns;
        this.alpha = alpha;
        this.beta = beta;
        this.infoRatio = infoRatio;
        this.RSquared = RSquared;
        this.vaR = vaR;
        this.portfolio90DayReturns = portfolio90DayReturns;
        this.portfolio1YearReturns = portfolio1YearReturns;
        this.benchmark90DaysReturns = benchmark90DaysReturns;
        this.benchmark1YearReturns = benchmark1YearReturns;
        this.noOfCustomersAssigned = noOfCustomersAssigned;
        this.rebalanceRequired = rebalanceRequired;
        this.rebalanceReqdDate = rebalanceReqdDate;
        this.lastUpdated = lastUpdated;
        this.dateCreated = dateCreated;
        this.startCurrentPeriod = startCurrentPeriod;
        this.status = status;
        this.portfolioValue = portfolioValue;
        this.rebalanceViewed = rebalanceViewed;
        this.balanceCash = balanceCash;
        this.portfolio5DayReturn = portfolio5DayReturn;
        this.portfolio1MonthReturn = portfolio1MonthReturn;
        this.portfolio5YearReturn = portfolio5YearReturn;
        this.portfolio10YearReturn = portfolio10YearReturn;
        this.portfolio6MonthReturn = portfolio6MonthReturn;
        this.portflioYtdReturn = portflioYtdReturn;
        this.sinceInception = sinceInception;
        this.portfolioSecuritiesTbs = portfolioSecuritiesTbs;
        this.portfolioDetailsTbs = portfolioDetailsTbs;
        this.customerPortfolioSecuritiesTbs = customerPortfolioSecuritiesTbs;
        this.customerDailyCashBalanceTbs = customerDailyCashBalanceTbs;
        this.customerTransactionOrderDetailsTbs = customerTransactionOrderDetailsTbs;
        this.customerPortfolioDetailsTbs = customerPortfolioDetailsTbs;
        this.portfolioSecuritiesPerformanceTbs = portfolioSecuritiesPerformanceTbs;
        this.recomendedCustomerPortfolioSecuritiesTbs = recomendedCustomerPortfolioSecuritiesTbs;
        this.portfolioDetailsPerformanceTbs = portfolioDetailsPerformanceTbs;
        this.customerPortfolioTbs = customerPortfolioTbs;
        this.portfolioPerformanceTbs = portfolioPerformanceTbs;
        this.customerTransactionExecutionDetailsTbs = customerTransactionExecutionDetailsTbs;
    }

    public Integer getPortfolioId() {
        return this.portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public MasterAdvisorTb getMasterAdvisorTb() {
        return this.masterAdvisorTb;
    }

    public void setMasterAdvisorTb(MasterAdvisorTb masterAdvisorTb) {
        this.masterAdvisorTb = masterAdvisorTb;
    }

    public MasterBenchmarkIndexTb getMasterBenchmarkIndexTb() {
        return this.masterBenchmarkIndexTb;
    }

    public void setMasterBenchmarkIndexTb(MasterBenchmarkIndexTb masterBenchmarkIndexTb) {
        this.masterBenchmarkIndexTb = masterBenchmarkIndexTb;
    }

    public MasterPortfolioTypeTb getMasterPortfolioTypeTb() {
        return this.masterPortfolioTypeTb;
    }

    public void setMasterPortfolioTypeTb(MasterPortfolioTypeTb masterPortfolioTypeTb) {
        this.masterPortfolioTypeTb = masterPortfolioTypeTb;
    }

    public String getPortfolioName() {
        return this.portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public BigDecimal getStdDev() {
        return this.stdDev;
    }

    public void setStdDev(BigDecimal stdDev) {
        this.stdDev = stdDev;
    }

    public Integer getSharpeRatio() {
        return this.sharpeRatio;
    }

    public void setSharpeRatio(Integer sharpeRatio) {
        this.sharpeRatio = sharpeRatio;
    }

    public BigDecimal getExpReturns() {
        return this.expReturns;
    }

    public void setExpReturns(BigDecimal expReturns) {
        this.expReturns = expReturns;
    }

    public BigDecimal getAlpha() {
        return this.alpha;
    }

    public void setAlpha(BigDecimal alpha) {
        this.alpha = alpha;
    }

    public BigDecimal getBeta() {
        return this.beta;
    }

    public void setBeta(BigDecimal beta) {
        this.beta = beta;
    }

    public BigDecimal getInfoRatio() {
        return this.infoRatio;
    }

    public void setInfoRatio(BigDecimal infoRatio) {
        this.infoRatio = infoRatio;
    }

    public Byte getRSquared() {
        return this.RSquared;
    }

    public void setRSquared(Byte RSquared) {
        this.RSquared = RSquared;
    }

    public BigDecimal getVaR() {
        return this.vaR;
    }

    public void setVaR(BigDecimal vaR) {
        this.vaR = vaR;
    }

    public BigDecimal getPortfolio90DayReturns() {
        return this.portfolio90DayReturns;
    }

    public void setPortfolio90DayReturns(BigDecimal portfolio90DayReturns) {
        this.portfolio90DayReturns = portfolio90DayReturns;
    }

    public BigDecimal getPortfolio1YearReturns() {
        return this.portfolio1YearReturns;
    }

    public void setPortfolio1YearReturns(BigDecimal portfolio1YearReturns) {
        this.portfolio1YearReturns = portfolio1YearReturns;
    }

    public BigDecimal getBenchmark90DaysReturns() {
        return this.benchmark90DaysReturns;
    }

    public void setBenchmark90DaysReturns(BigDecimal benchmark90DaysReturns) {
        this.benchmark90DaysReturns = benchmark90DaysReturns;
    }

    public BigDecimal getBenchmark1YearReturns() {
        return this.benchmark1YearReturns;
    }

    public void setBenchmark1YearReturns(BigDecimal benchmark1YearReturns) {
        this.benchmark1YearReturns = benchmark1YearReturns;
    }

    public Integer getNoOfCustomersAssigned() {
        return this.noOfCustomersAssigned;
    }

    public void setNoOfCustomersAssigned(Integer noOfCustomersAssigned) {
        this.noOfCustomersAssigned = noOfCustomersAssigned;
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

    public Date getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getStartCurrentPeriod() {
        return this.startCurrentPeriod;
    }

    public void setStartCurrentPeriod(Date startCurrentPeriod) {
        this.startCurrentPeriod = startCurrentPeriod;
    }

    public Boolean getStatus() {
        return this.status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public BigDecimal getPortfolioValue() {
        return this.portfolioValue;
    }

    public void setPortfolioValue(BigDecimal portfolioValue) {
        this.portfolioValue = portfolioValue;
    }

    public Boolean getRebalanceViewed() {
        return this.rebalanceViewed;
    }

    public void setRebalanceViewed(Boolean rebalanceViewed) {
        this.rebalanceViewed = rebalanceViewed;
    }

    public Double getBalanceCash() {
        return this.balanceCash;
    }

    public void setBalanceCash(Double balanceCash) {
        this.balanceCash = balanceCash;
    }

    public BigDecimal getPortfolio5DayReturn() {
        return this.portfolio5DayReturn;
    }

    public void setPortfolio5DayReturn(BigDecimal portfolio5DayReturn) {
        this.portfolio5DayReturn = portfolio5DayReturn;
    }

    public BigDecimal getPortfolio1MonthReturn() {
        return this.portfolio1MonthReturn;
    }

    public void setPortfolio1MonthReturn(BigDecimal portfolio1MonthReturn) {
        this.portfolio1MonthReturn = portfolio1MonthReturn;
    }

    public BigDecimal getPortfolio5YearReturn() {
        return this.portfolio5YearReturn;
    }

    public void setPortfolio5YearReturn(BigDecimal portfolio5YearReturn) {
        this.portfolio5YearReturn = portfolio5YearReturn;
    }

    public BigDecimal getPortfolio10YearReturn() {
        return this.portfolio10YearReturn;
    }

    public void setPortfolio10YearReturn(BigDecimal portfolio10YearReturn) {
        this.portfolio10YearReturn = portfolio10YearReturn;
    }

    public BigDecimal getPortfolio6MonthReturn() {
        return this.portfolio6MonthReturn;
    }

    public void setPortfolio6MonthReturn(BigDecimal portfolio6MonthReturn) {
        this.portfolio6MonthReturn = portfolio6MonthReturn;
    }

    public BigDecimal getPortflioYtdReturn() {
        return this.portflioYtdReturn;
    }

    public void setPortflioYtdReturn(BigDecimal portflioYtdReturn) {
        this.portflioYtdReturn = portflioYtdReturn;
    }

    public BigDecimal getSinceInception() {
        return sinceInception;
    }

    public void setSinceInception(BigDecimal sinceInception) {
        this.sinceInception = sinceInception;
    }

    public Set getPortfolioSecuritiesTbs() {
        return this.portfolioSecuritiesTbs;
    }

    public void setPortfolioSecuritiesTbs(Set portfolioSecuritiesTbs) {
        this.portfolioSecuritiesTbs = portfolioSecuritiesTbs;
    }

    public Set getPortfolioDetailsTbs() {
        return this.portfolioDetailsTbs;
    }

    public void setPortfolioDetailsTbs(Set portfolioDetailsTbs) {
        this.portfolioDetailsTbs = portfolioDetailsTbs;
    }

    public Set getCustomerPortfolioSecuritiesTbs() {
        return this.customerPortfolioSecuritiesTbs;
    }

    public void setCustomerPortfolioSecuritiesTbs(Set customerPortfolioSecuritiesTbs) {
        this.customerPortfolioSecuritiesTbs = customerPortfolioSecuritiesTbs;
    }

    public Set getCustomerDailyCashBalanceTbs() {
        return this.customerDailyCashBalanceTbs;
    }

    public void setCustomerDailyCashBalanceTbs(Set customerDailyCashBalanceTbs) {
        this.customerDailyCashBalanceTbs = customerDailyCashBalanceTbs;
    }

    public Set getCustomerTransactionOrderDetailsTbs() {
        return this.customerTransactionOrderDetailsTbs;
    }

    public void setCustomerTransactionOrderDetailsTbs(Set customerTransactionOrderDetailsTbs) {
        this.customerTransactionOrderDetailsTbs = customerTransactionOrderDetailsTbs;
    }

    public Set getCustomerPortfolioDetailsTbs() {
        return this.customerPortfolioDetailsTbs;
    }

    public void setCustomerPortfolioDetailsTbs(Set customerPortfolioDetailsTbs) {
        this.customerPortfolioDetailsTbs = customerPortfolioDetailsTbs;
    }

    public Set getPortfolioSecuritiesPerformanceTbs() {
        return this.portfolioSecuritiesPerformanceTbs;
    }

    public void setPortfolioSecuritiesPerformanceTbs(Set portfolioSecuritiesPerformanceTbs) {
        this.portfolioSecuritiesPerformanceTbs = portfolioSecuritiesPerformanceTbs;
    }

    public Set getRecomendedCustomerPortfolioSecuritiesTbs() {
        return this.recomendedCustomerPortfolioSecuritiesTbs;
    }

    public void setRecomendedCustomerPortfolioSecuritiesTbs(Set recomendedCustomerPortfolioSecuritiesTbs) {
        this.recomendedCustomerPortfolioSecuritiesTbs = recomendedCustomerPortfolioSecuritiesTbs;
    }

    public Set getPortfolioDetailsPerformanceTbs() {
        return this.portfolioDetailsPerformanceTbs;
    }

    public void setPortfolioDetailsPerformanceTbs(Set portfolioDetailsPerformanceTbs) {
        this.portfolioDetailsPerformanceTbs = portfolioDetailsPerformanceTbs;
    }

    public Set getCustomerPortfolioTbs() {
        return this.customerPortfolioTbs;
    }

    public void setCustomerPortfolioTbs(Set customerPortfolioTbs) {
        this.customerPortfolioTbs = customerPortfolioTbs;
    }

    public Set getPortfolioPerformanceTbs() {
        return this.portfolioPerformanceTbs;
    }

    public void setPortfolioPerformanceTbs(Set portfolioPerformanceTbs) {
        this.portfolioPerformanceTbs = portfolioPerformanceTbs;
    }

    public Set getCustomerTransactionExecutionDetailsTbs() {
        return this.customerTransactionExecutionDetailsTbs;
    }

    public void setCustomerTransactionExecutionDetailsTbs(Set customerTransactionExecutionDetailsTbs) {
        this.customerTransactionExecutionDetailsTbs = customerTransactionExecutionDetailsTbs;
    }

}
