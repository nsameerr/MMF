package com.gtl.mmf.entity;
// Generated Jul 15, 2016 5:29:15 PM by Hibernate Tools 3.4.0.CR1

/**
 * FpPortfolioReturnsMasterTb generated by hbm2java
 */
public class FpPortfolioReturnsMasterTb implements java.io.Serializable {

	private Integer portfolioReturnsReferenceId;
	private String portfolioType;
	private Float averageReturns;
	private Float standardDeviation;

	public FpPortfolioReturnsMasterTb() {
	}

	public FpPortfolioReturnsMasterTb(String portfolioType,
			Float averageReturns, Float standardDeviation) {
		this.portfolioType = portfolioType;
		this.averageReturns = averageReturns;
		this.standardDeviation = standardDeviation;
	}

	public Integer getPortfolioReturnsReferenceId() {
		return this.portfolioReturnsReferenceId;
	}

	public void setPortfolioReturnsReferenceId(
			Integer portfolioReturnsReferenceId) {
		this.portfolioReturnsReferenceId = portfolioReturnsReferenceId;
	}

	public String getPortfolioType() {
		return this.portfolioType;
	}

	public void setPortfolioType(String portfolioType) {
		this.portfolioType = portfolioType;
	}

	public Float getAverageReturns() {
		return this.averageReturns;
	}

	public void setAverageReturns(Float averageReturns) {
		this.averageReturns = averageReturns;
	}

	public Float getStandardDeviation() {
		return this.standardDeviation;
	}

	public void setStandardDeviation(Float standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

}
