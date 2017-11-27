package com.gtl.mmf.service.vo.financialplanner;

import java.util.List;
import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;

public class FinancialPlannerOutput {
	
	private int customerId;
	private String name;
	private int[] age;
	private int[] year;
	private float[] totalFinAsset;
	private float[] totalFinAssetOnlyFD;
	private float[] totalLiabilities;
	private int[] statusCheck;
	private int[] statusCheckforAssetLineWithFDOnly;
	private List<LifeGoalsInput> lifeGoals;
	private FpMasterAssumption fpmasterassumption;
	private int riskGroupId;
	private float savingsRate;
	private boolean redirectInfo;
	
	
	public FinancialPlannerOutput(){
		// This is a default constructor
	}

	public FinancialPlannerOutput(int arraySize)
	{
		age = new int [arraySize];
		year = new int [arraySize];
		totalFinAsset = new float [arraySize];
		totalFinAssetOnlyFD = new float [arraySize];
		totalLiabilities = new float [arraySize];
		statusCheck = new int [arraySize];
		statusCheckforAssetLineWithFDOnly = new int [arraySize];
		
		for(int counter=0; counter<arraySize; counter ++)
		{
			age[counter] = 0;
			year[counter] = 0;
			totalFinAsset[counter] = 0;
			totalFinAssetOnlyFD[counter] = 0;
			totalLiabilities[counter] = 0;
			statusCheck[counter] = 0;
			statusCheckforAssetLineWithFDOnly[counter] = 0;
		}
	}
	
	public int getCustomerId() {
		return customerId;
	}
	
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int[] getAge() {
		return age;
	}
	
	public void setAge(int[] age) {
		this.age = age;
	}
	
	public int[] getYear() {
		return year;
	}

	public void setYear(int[] year) {
		this.year = year;
	}
	
	public float[] getTotalFinAsset() {
		return totalFinAsset;
	}
	
	public void setTotalFinAsset(float[] totalFinAsset) {
		this.totalFinAsset = totalFinAsset;
	}
	
	public float[] getTotalFinAssetOnlyFD() {
		return totalFinAssetOnlyFD;
	}

	public void setTotalFinAssetOnlyFD(float[] totalFinAssetOnlyFD) {
		this.totalFinAssetOnlyFD = totalFinAssetOnlyFD;
	}
	
	public float[] getTotalLiabilities() {
		return totalLiabilities;
	}
	
	public void setTotalLiabilities(float[] totalLiabilities) {
		this.totalLiabilities = totalLiabilities;
	}
	
	public int[] getStatusCheck() {
		return statusCheck;
	}

	public void setStatusCheck(int[] statusCheck) {
		this.statusCheck = statusCheck;
	}
	
	public int[] getStatusCheckforAssetLineWithFDOnly() {
		return statusCheckforAssetLineWithFDOnly;
	}

	public void setStatusCheckforAssetLineWithFDOnly(
			int[] statusCheckforAssetLineWithFDOnly) {
		this.statusCheckforAssetLineWithFDOnly = statusCheckforAssetLineWithFDOnly;
	}
	
	public List<LifeGoalsInput> getLifeGoals() {
		return lifeGoals;
	}

	public void setLifeGoals(List<LifeGoalsInput> lifeGoals) {
		this.lifeGoals = lifeGoals;
	}
	
	public FpMasterAssumption getFpmasterassumption() {
		return fpmasterassumption;
	}

	public void setFpmasterassumption(FpMasterAssumption fpmasterassumption) {
		this.fpmasterassumption = fpmasterassumption;
	}

	public int getRiskGroupId() {
		return riskGroupId;
	}

	public void setRiskGroupId(int riskGroupId) {
		this.riskGroupId = riskGroupId;
	}

	public float getSavingsRate() {
		return savingsRate;
	}

	public void setSavingsRate(float savingsRate) {
		this.savingsRate = savingsRate;
	}

	public boolean isRedirectInfo() {
		return redirectInfo;
	}

	public void setRedirectInfo(boolean redirectInfo) {
		this.redirectInfo = redirectInfo;
	}

	
}
