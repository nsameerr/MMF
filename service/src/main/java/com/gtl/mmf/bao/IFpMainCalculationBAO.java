package com.gtl.mmf.bao;


import java.util.List;

import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;
import com.gtl.mmf.service.vo.financialplanner.FinancialPlannerOutput;
import com.gtl.mmf.service.vo.financialplanner.FPResponseSet;

public interface IFpMainCalculationBAO{
	
	public void setResponses(FPResponseSet FpResponseInput);
	
	public void setAssumptions(FpMasterAssumption fpmasterassumption);
	
	public void setcustomerId(String customerRegId);
	
	public void Initialization();
	
	public float getIncrementRate(int age_counter, int flag);
	
	// Computes the salary of the user every year from current input till Retirement Age and saves it in an Array 
	public void setSelfSalary();
	
	// Computes the salary of the user's spouse for every year from current input till Retirement Age and saves it in an Array
	public void setSpouseSalary();
	
	/* Computes the total salary from the user's input (=self salary + spouse salary<if any>) for every year from current input
    	till Life Expectancy and saves it in an Array    */
	
	public void setTotalSalary();
	
	public void totalOutstandingLoans();
	
	public float getFinAssetGrowthRate(int assetId);
	
	public void inputFinAssetGrowth();
	
	// Computes the expenditure of the user from input details for every year from current input till Life Expectancy and saves it in an Array
	public void setLifestyleExpenditure();
	
	public void setHealthInsuranceExpenditure();
	
	public void setLifeInsuranceExpenditure();
	
	public void calculateRealEstateAssetContribution(float estimatedAmountInflated, float interestRate, int loanDuration, float annualEMI, float loanDownpayment, int startAgeofEMIpayment);
	
	public void setGoalsExpenditure();
	
	public void setTotalExpenditure();
	
	//public void setAnnualSavings();
	
	public void calculateAssets();	
	
	public void calculateAssetsWithOnlyFDInvestment();
	
	public FinancialPlannerOutput getFinancialPlannerOutput();
	
	// checks if the user has edited the default assumptions or not
	public void defaultAssumptionsEditCheck();

	public boolean getUserFpStatus(String regId);
	
	
}