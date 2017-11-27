package com.gtl.mmf.service.vo.financialplanner;

import java.util.List;
import java.util.Date;


public class FPResponseSet {
	private List<LifeGoalsInput> lifeGoals;
	private List<CurrentOutstandingLoans> outstandingLoans;
	private String gender;
	private int userAge;
	private String relationStatus;
	private float userMonthlyTakehomeSalary;
	private int spouseAge;
	private float spouseMonthlyTakehomeSalary;
	private float monthlySavings;
	private float savingsRate;
	private List<FinancialAssetList> financialAssetList;
	private float initialTotalFinancialAsset;
	private List<LifeInsuranceCover> lifeInsuranceCover;
	private List<HealthInsuranceCover> healthInsuranceCover;
	private int riskGroupId;
	private Date responseDate;
	private Long responseId;
	
	
	public FPResponseSet(List<LifeGoalsInput> lifeGoals, List<CurrentOutstandingLoans> outstandingLoans, String gender, int userAge, String relationStatus, float userMonthlyTakehomeSalary, int spouseAge, float spouseMonthlyTakehomeSalary, float monthlySavings, float savingsRate, List<FinancialAssetList> financialAssetList, int riskGroupId, Date responseDate, Long responseId){
		this.lifeGoals = lifeGoals;
		this.outstandingLoans = outstandingLoans;
		this.gender = gender;
		this.userAge = userAge;
		this.relationStatus = relationStatus;
		this.userMonthlyTakehomeSalary = userMonthlyTakehomeSalary;
		this.spouseAge = spouseAge;
		this.spouseMonthlyTakehomeSalary = spouseMonthlyTakehomeSalary;
		this.monthlySavings = monthlySavings;
		this.savingsRate = savingsRate;
		this.financialAssetList = financialAssetList;
		this.riskGroupId = riskGroupId;
		this.responseDate = responseDate;
		this.responseId = responseId;
	}
	
	public FPResponseSet(String gender, int userAge, String relationStatus, float userMonthlyTakehomeSalary, int spouseAge, float spouseMonthlyTakehomeSalary, float monthlySavings, float savingsRate, List<FinancialAssetList> financialAssetList, int riskGroupId, Long responseId){
		
		this.gender = gender;
		this.userAge = userAge;
		this.relationStatus = relationStatus;
		this.userMonthlyTakehomeSalary = userMonthlyTakehomeSalary;
		this.spouseAge = spouseAge;
		this.spouseMonthlyTakehomeSalary = spouseMonthlyTakehomeSalary;
		this.monthlySavings = monthlySavings;
		this.savingsRate = savingsRate;
		this.financialAssetList = financialAssetList;
		this.riskGroupId = riskGroupId;
		this.responseId = responseId;
		
	}
	
	public FPResponseSet() {
		// TODO Auto-generated constructor stub
		this.responseDate = new Date();
	}

	public List<LifeGoalsInput> getLifeGoals() {
		return lifeGoals;
	}

	public void setLifeGoals(List<LifeGoalsInput> lifeGoals) {
		this.lifeGoals = lifeGoals;
	}

	public List<CurrentOutstandingLoans> getOutstandingLoans() {
		return outstandingLoans;
	}

	public void setOutstandingLoans(List<CurrentOutstandingLoans> outstandingLoans) {
		this.outstandingLoans = outstandingLoans;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getRelationStatus() {
		return relationStatus;
	}

	public void setRelationStatus(String relationStatus) {
		this.relationStatus = relationStatus;
	}

	public float getUserMonthlyTakehomeSalary() {
		return userMonthlyTakehomeSalary;
	}

	public void setUserMonthlyTakehomeSalary(float userMonthlyTakehomeSalary) {
		this.userMonthlyTakehomeSalary = userMonthlyTakehomeSalary;
	}

	public int getSpouseAge() {
		return spouseAge;
	}

	public void setSpouseAge(int spouseAge) {
		this.spouseAge = spouseAge;
	}

	public float getSpouseMonthlyTakehomeSalary() {
		return spouseMonthlyTakehomeSalary;
	}

	public void setSpouseMonthlyTakehomeSalary(float spouseMonthlyTakehomeSalary) {
		this.spouseMonthlyTakehomeSalary = spouseMonthlyTakehomeSalary;
	}

	public float getMonthlySavings() {
		return monthlySavings;
	}

	public void setMonthlySavings(float monthlySavings) {
		this.monthlySavings = monthlySavings;
	}

	public float getSavingsRate() {
		return savingsRate;
	}

	public void setSavingsRate(float savingsRate) {
		this.savingsRate = savingsRate;
	}

	public List<FinancialAssetList> getFinancialAssetList() {
		return financialAssetList;
	}

	public void setFinancialAssetList(List<FinancialAssetList> financialAssetList) {
		this.financialAssetList = financialAssetList;
	}
	
	public float getInitialTotalFinancialAsset() {
		return initialTotalFinancialAsset;
	}

	public void setInitialTotalFinancialAsset(float initialTotalFinancialAsset) {
		this.initialTotalFinancialAsset = initialTotalFinancialAsset;
	}
	
	public List<LifeInsuranceCover> getLifeInsuranceCover() {
		return lifeInsuranceCover;
	}

	public void setLifeInsuranceCover(List<LifeInsuranceCover> lifeInsuranceCover) {
		this.lifeInsuranceCover = lifeInsuranceCover;
	}

	public List<HealthInsuranceCover> getHealthInsuranceCover() {
		return healthInsuranceCover;
	}

	public void setHealthInsuranceCover(List<HealthInsuranceCover> healthInsuranceCover) {
		this.healthInsuranceCover = healthInsuranceCover;
	}
	
	public int getRiskGroupId() {
		return riskGroupId;
	}

	public void setRiskGroupId(int riskGroupId) {
		this.riskGroupId = riskGroupId;
	}

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Long getResponseId() {
		return responseId;
	}

	public void setResponseId(Long responseId) {
		this.responseId = responseId;
	}

	
	
}
	
	
	
	

