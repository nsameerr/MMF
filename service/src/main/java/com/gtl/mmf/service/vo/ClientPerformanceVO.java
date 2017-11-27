/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class ClientPerformanceVO {

    private int customerId;
    private String customerName;
    private String companyName;
    private int portfolioId;
    private int customerPortfolioId;
    private String portfolioName;
    private Double actualPerformance;
    private Double recmdPerformance;
    private Integer balancePeriodDays;
    private Integer relationId;
    private Integer clientStataus;
    private String email;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

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

    public Double getActualPerformance() {
        return actualPerformance;
    }

    public void setActualPerformance(Double actualPerformance) {
        this.actualPerformance = actualPerformance;
    }

    public Double getRecmdPerformance() {
        return recmdPerformance;
    }

    public void setRecmdPerformance(Double recmdPerformance) {
        this.recmdPerformance = recmdPerformance;
    }

    public Integer getBalancePeriodDays() {
        return balancePeriodDays;
    }

    public void setBalancePeriodDays(Integer balancePeriodDays) {
        this.balancePeriodDays = balancePeriodDays;
    }

    public int getCustomerPortfolioId() {
        return customerPortfolioId;
    }

    public void setCustomerPortfolioId(int customerPortfolioId) {
        this.customerPortfolioId = customerPortfolioId;
    }

    public Integer getClientStataus() {
        return clientStataus;
    }

    public void setClientStataus(Integer clientStataus) {
        this.clientStataus = clientStataus;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
