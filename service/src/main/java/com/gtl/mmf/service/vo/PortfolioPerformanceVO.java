/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.vo;

/**
 *
 * @author 07958
 */
public class PortfolioPerformanceVO {

    private int portfolioId;
    private String portfolioName;
    private Double portfolioReturns;
    private Double benchmarkReturns;
    private String lastModified;
    private Integer noOfClients;
    private Double portfolio_90_day_returns;
    private Double portfolio_1_year_returns;
    private Double benchmark_90_days_returns;
    private Double benchmark_1_year_returns;

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

    public Double getPortfolioReturns() {
        return portfolioReturns;
    }

    public void setPortfolioReturns(Double portfolioReturns) {
        this.portfolioReturns = portfolioReturns;
    }

    public Double getBenchmarkReturns() {
        return benchmarkReturns;
    }

    public void setBenchmarkReturns(Double benchmarkReturns) {
        this.benchmarkReturns = benchmarkReturns;
    }

    public String getLastModified() {
        return lastModified;
    }

    public void setLastModified(String lastModified) {
        this.lastModified = lastModified;
    }

    public Integer getNoOfClients() {
        return noOfClients;
    }

    public void setNoOfClients(Integer noOfClients) {
        this.noOfClients = noOfClients;
    }

    public Double getPortfolio_90_day_returns() {
        return portfolio_90_day_returns;
    }

    public void setPortfolio_90_day_returns(Double portfolio_90_day_returns) {
        this.portfolio_90_day_returns = portfolio_90_day_returns;
    }

    public Double getPortfolio_1_year_returns() {
        return portfolio_1_year_returns;
    }

    public void setPortfolio_1_year_returns(Double portfolio_1_year_returns) {
        this.portfolio_1_year_returns = portfolio_1_year_returns;
    }

    public Double getBenchmark_90_days_returns() {
        return benchmark_90_days_returns;
    }

    public void setBenchmark_90_days_returns(Double benchmark_90_days_returns) {
        this.benchmark_90_days_returns = benchmark_90_days_returns;
    }

    public Double getBenchmark_1_year_returns() {
        return benchmark_1_year_returns;
    }

    public void setBenchmark_1_year_returns(Double benchmark_1_year_returns) {
        this.benchmark_1_year_returns = benchmark_1_year_returns;
    }
}
