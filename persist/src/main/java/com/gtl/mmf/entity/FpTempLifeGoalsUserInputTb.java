package com.gtl.mmf.entity;
// Generated Jul 15, 2016 5:29:15 PM by Hibernate Tools 3.4.0.CR1

/**
 * FpTempLifeGoalsUserInputTb generated by hbm2java
 */
public class FpTempLifeGoalsUserInputTb implements java.io.Serializable {

	private Integer id;
	private String customerId;
	private Integer goalId;
	private String goalDescription;
	private Integer yearOfRealization;
	private Integer recurringFrequencyId;
	private String wantLoanYesNo;
	private Integer estimatedAmountAsPerCurrentPrices;

	public FpTempLifeGoalsUserInputTb() {
	}

	public FpTempLifeGoalsUserInputTb(String customerId, Integer goalId,
			String goalDescription, Integer yearOfRealization,
			Integer recurringFrequencyId, String wantLoanYesNo,
			Integer estimatedAmountAsPerCurrentPrices) {
		this.customerId = customerId;
		this.goalId = goalId;
		this.goalDescription = goalDescription;
		this.yearOfRealization = yearOfRealization;
		this.recurringFrequencyId = recurringFrequencyId;
		this.wantLoanYesNo = wantLoanYesNo;
		this.estimatedAmountAsPerCurrentPrices = estimatedAmountAsPerCurrentPrices;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Integer getGoalId() {
		return this.goalId;
	}

	public void setGoalId(Integer goalId) {
		this.goalId = goalId;
	}

	public String getGoalDescription() {
		return this.goalDescription;
	}

	public void setGoalDescription(String goalDescription) {
		this.goalDescription = goalDescription;
	}

	public Integer getYearOfRealization() {
		return this.yearOfRealization;
	}

	public void setYearOfRealization(Integer yearOfRealization) {
		this.yearOfRealization = yearOfRealization;
	}

	public Integer getRecurringFrequencyId() {
		return this.recurringFrequencyId;
	}

	public void setRecurringFrequencyId(Integer recurringFrequencyId) {
		this.recurringFrequencyId = recurringFrequencyId;
	}

	public String getWantLoanYesNo() {
		return this.wantLoanYesNo;
	}

	public void setWantLoanYesNo(String wantLoanYesNo) {
		this.wantLoanYesNo = wantLoanYesNo;
	}

	public Integer getEstimatedAmountAsPerCurrentPrices() {
		return this.estimatedAmountAsPerCurrentPrices;
	}

	public void setEstimatedAmountAsPerCurrentPrices(
			Integer estimatedAmountAsPerCurrentPrices) {
		this.estimatedAmountAsPerCurrentPrices = estimatedAmountAsPerCurrentPrices;
	}

}