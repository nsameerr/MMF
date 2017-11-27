/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class CustomerPortfolioVO {

    private Integer customerPortfolioId;
    private Integer relationId;
    private Integer customerId;
    private Integer advisorId;
    private Integer portfolioId;
    private Float currentValue;
    private Float portfolio90DayReturns;
    private Float portfolio1Yearreturn;
    private String advisorComment;
    private String customerComment;
    private Integer outerPerformance;
    private BigDecimal advisorPortfolioStartValue;
    private BigDecimal benchmarkStartValue;
    private Float advisorPortfolioReturnsFromStart;
    private Float benchmarkReturnsFromStart;
    private String userName;
    private Integer commentFreq;
    private List<String> previousReviews;
    private String advisorPrevoisComments;
    private String customerpreviouComments;
    private String portfolioName;
    private String porfolioStyleName;
    private String benchmarkName;
    private List<PortfolioDetailsVO> portfolioDetailsVOs;
    private Integer benchmarkIndexSelected;
    private Boolean rebalanceRequired;
    private Date rebalanceRequiredDate;
    private Boolean noreBalanceStatus;
    private Double alocatedFunds;
    private Date lastExeDate;
    private Float cashAmount;
    private Double totalTransAmt;
    private Float cashWeight;
    private String omsLoginId;
    private String omsPAN;
    private Date omsdob;
    private Boolean portfolioModified;
    // Used for client side daemon
    private Boolean rebalanceEnabledForAdvisor;
    private Integer portfolioIdBeforeChange;
    private Boolean fromAddReedem;
    private Set venuName;
    private Float buyingPower;
    private Double totalGainLossValue;
    private Double totalAcquisition;
    private Double toatalWeight;
    private Double pfTwrReturn;
    private Double rcmdPfTwrReturn;
    private Float blockedCash;

    public Integer getCustomerPortfolioId() {
        return customerPortfolioId;
    }

    public void setCustomerPortfolioId(Integer customerPortfolioId) {
        this.customerPortfolioId = customerPortfolioId;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public Integer getAdvisorId() {
        return advisorId;
    }

    public void setAdvisorId(Integer advisorId) {
        this.advisorId = advisorId;
    }

    public Integer getPortfolioId() {
        return portfolioId;
    }

    public void setPortfolioId(Integer portfolioId) {
        this.portfolioId = portfolioId;
    }

    public Float getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Float currentValue) {
        this.currentValue = currentValue;
    }

    public Float getPortfolio90DayReturns() {
        return portfolio90DayReturns;
    }

    public void setPortfolio90DayReturns(Float portfolio90DayReturns) {
        this.portfolio90DayReturns = portfolio90DayReturns;
    }

    public Float getPortfolio1Yearreturn() {
        return portfolio1Yearreturn;
    }

    public void setPortfolio1Yearreturn(Float portfolio1Yearreturn) {
        this.portfolio1Yearreturn = portfolio1Yearreturn;
    }

    public String getAdvisorComment() {
        return advisorComment;
    }

    public void setAdvisorComment(String advisorComment) {
        this.advisorComment = advisorComment;
    }

    public String getCustomerComment() {
        return customerComment;
    }

    public void setCustomerComment(String customerComment) {
        this.customerComment = customerComment;
    }

    public Integer getOuterPerformance() {
        return outerPerformance;
    }

    public void setOuterPerformance(Integer outerPerformance) {
        this.outerPerformance = outerPerformance;
    }

    public BigDecimal getAdvisorPortfolioStartValue() {
        return advisorPortfolioStartValue;
    }

    public void setAdvisorPortfolioStartValue(BigDecimal advisorPortfolioStartValue) {
        this.advisorPortfolioStartValue = advisorPortfolioStartValue;
    }

    public BigDecimal getBenchmarkStartValue() {
        return benchmarkStartValue;
    }

    public void setBenchmarkStartValue(BigDecimal benchmarkStartValue) {
        this.benchmarkStartValue = benchmarkStartValue;
    }

    public Float getAdvisorPortfolioReturnsFromStart() {
        return advisorPortfolioReturnsFromStart;
    }

    public void setAdvisorPortfolioReturnsFromStart(Float advisorPortfolioReturnsFromStart) {
        this.advisorPortfolioReturnsFromStart = advisorPortfolioReturnsFromStart;
    }

    public Float getBenchmarkReturnsFromStart() {
        return benchmarkReturnsFromStart;
    }

    public void setBenchmarkReturnsFromStart(Float benchmarkReturnsFromStart) {
        this.benchmarkReturnsFromStart = benchmarkReturnsFromStart;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getCommentFreq() {
        return commentFreq;
    }

    public void setCommentFreq(Integer commentFreq) {
        this.commentFreq = commentFreq;
    }

    public List<String> getPreviousReviews() {
        return previousReviews;
    }

    public void setPreviousReviews(List<String> previousReviews) {
        this.previousReviews = previousReviews;
    }

    public String getAdvisorPrevoisComments() {
        return advisorPrevoisComments;
    }

    public void setAdvisorPrevoisComments(String advisorPrevoisComments) {
        this.advisorPrevoisComments = advisorPrevoisComments;
    }

    public String getCustomerpreviouComments() {
        return customerpreviouComments;
    }

    public void setCustomerpreviouComments(String customerpreviouComments) {
        this.customerpreviouComments = customerpreviouComments;
    }

    public String getPortfolioName() {
        return portfolioName;
    }

    public void setPortfolioName(String portfolioName) {
        this.portfolioName = portfolioName;
    }

    public String getPorfolioStyleName() {
        return porfolioStyleName;
    }

    public void setPorfolioStyleName(String porfolioStyleName) {
        this.porfolioStyleName = porfolioStyleName;
    }

    public String getBenchmarkName() {
        return benchmarkName;
    }

    public void setBenchmarkName(String benchmarkName) {
        this.benchmarkName = benchmarkName;
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOs() {
        return portfolioDetailsVOs;
    }

    public void setPortfolioDetailsVOs(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        this.portfolioDetailsVOs = portfolioDetailsVOs;
    }

    public Integer getBenchmarkIndexSelected() {
        return benchmarkIndexSelected;
    }

    public void setBenchmarkIndexSelected(Integer benchmarkIndexSelected) {
        this.benchmarkIndexSelected = benchmarkIndexSelected;
    }

    public Boolean getRebalanceRequired() {
        return rebalanceRequired;
    }

    public void setRebalanceRequired(Boolean rebalanceRequired) {
        this.rebalanceRequired = rebalanceRequired;
    }

    public Date getRebalanceRequiredDate() {
        return rebalanceRequiredDate;
    }

    public void setRebalanceRequiredDate(Date rebalanceRequiredDate) {
        this.rebalanceRequiredDate = rebalanceRequiredDate;
    }

    public Boolean isNoreBalanceStatus() {
        return noreBalanceStatus;
    }

    public void setNoreBalanceStatus(Boolean noreBalanceStatus) {
        this.noreBalanceStatus = noreBalanceStatus;
    }

    public Double getAlocatedFunds() {
        return alocatedFunds;
    }

    public void setAlocatedFunds(Double alocatedFunds) {
        this.alocatedFunds = alocatedFunds;
    }

    public Date getLastExeDate() {
        return lastExeDate;
    }

    public void setLastExeDate(Date lastExeDate) {
        this.lastExeDate = lastExeDate;
    }

    public Float getCashAmount() {
        return cashAmount;
    }

    public void setCashAmount(Float cashAmount) {
        this.cashAmount = cashAmount;
    }

    public Double getTotalTransAmt() {
        return totalTransAmt;
    }

    public void setTotalTransAmt(Double totalTransAmt) {
        this.totalTransAmt = totalTransAmt;
    }

    public Float getCashWeight() {
        return cashWeight;
    }

    public void setCashWeight(Float cashWeight) {
        this.cashWeight = cashWeight;
    }

    public String getOmsLoginId() {
        return omsLoginId;
    }

    public void setOmsLoginId(String omsLoginId) {
        this.omsLoginId = omsLoginId;
    }

    public String getOmsPAN() {
        return omsPAN;
    }

    public void setOmsPAN(String omsPAN) {
        this.omsPAN = omsPAN;
    }

    public Date getOmsdob() {
        return omsdob;
    }

    public void setOmsdob(Date omsdob) {
        this.omsdob = omsdob;
    }

    public Boolean isRebalanceEnabledForAdvisor() {
        return rebalanceEnabledForAdvisor;
    }

    public void setRebalanceEnabledForAdvisor(Boolean rebalanceEnabledForAdvisor) {
        this.rebalanceEnabledForAdvisor = rebalanceEnabledForAdvisor;
    }

    public Integer getPortfolioIdBeforeChange() {
        return portfolioIdBeforeChange;
    }

    public void setPortfolioIdBeforeChange(Integer portfolioIdBeforeChange) {
        this.portfolioIdBeforeChange = portfolioIdBeforeChange;
    }

    public Boolean getPortfolioModified() {
        return portfolioModified;
    }

    public void setPortfolioModified(Boolean portfolioModified) {
        this.portfolioModified = portfolioModified;
    }

    public Boolean isFromAddReedem() {
        return fromAddReedem;
    }

    public void setFromAddReedem(Boolean fromAddReedem) {
        this.fromAddReedem = fromAddReedem;
    }

    public Set getVenuName() {
        return venuName;
    }

    public void setVenuName(Set venuName) {
        this.venuName = venuName;
    }

    public Float getBuyingPower() {
        return buyingPower;
    }

    public void setBuyingPower(Float buyingPower) {
        this.buyingPower = buyingPower;
    }

    public Double getTotalGainLossValue() {
        return totalGainLossValue;
    }

    public void setTotalGainLossValue(Double totalGainLossValue) {
        this.totalGainLossValue = totalGainLossValue;
    }

    public Double getTotalAcquisition() {
        return totalAcquisition;
    }

    public void setTotalAcquisition(Double totalAcquisition) {
        this.totalAcquisition = totalAcquisition;
    }

    public Double getToatalWeight() {
        return toatalWeight;
    }

    public void setToatalWeight(Double toatalWeight) {
        this.toatalWeight = toatalWeight;
    }

    public Double getPfTwrReturn() {
        return pfTwrReturn;
    }

    public void setPfTwrReturn(Double pfTwrReturn) {
        this.pfTwrReturn = pfTwrReturn;
    }

    public Double getRcmdPfTwrReturn() {
        return rcmdPfTwrReturn;
    }

    public void setRcmdPfTwrReturn(Double rcmdPfTwrReturn) {
        this.rcmdPfTwrReturn = rcmdPfTwrReturn;
    }

    public Float getBlockedCash() {
        return blockedCash;
    }

    public void setBlockedCash(Float blockedCash) {
        this.blockedCash = blockedCash;
    }
    
    
}
