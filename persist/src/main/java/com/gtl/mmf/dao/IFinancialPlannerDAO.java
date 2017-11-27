package com.gtl.mmf.dao;

import java.util.List;

import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpFrequencyidMasterTb;
import com.gtl.mmf.entity.FpMappingGoalsidToLoansidTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpLifeGoalsUserInputTb;
import com.gtl.mmf.entity.FpFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpOutstandingloansUserInputsTb;
import com.gtl.mmf.entity.FpTempFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpTempMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempOutstandingloansUserInputsTb;
import com.gtl.mmf.entity.FpTempLifeGoalsUserInputTb;


public interface IFinancialPlannerDAO {
	
	//clear fp temp save data from tb with given customerTempRegId
	public void deleteUserFpMasterDetailsFromTempTb(String customerRegId);
	public void deleteUserFpLifeGoalsDetailsFromTempTb(String customerRegId);
	public void deleteUserFpInsuranceDetailsFromTempTb(String customerRegId);
	public void deleteUserFpOutstandindLoansDetailsFromTempTb(String customerRegId);
	public void deleteUserFpFinAssetDetailsFromTempTb(String customerRegId);
	
	//clear fp final save data from tb with given customerTempRegId
	public void deleteUserFpMasterDetailsFromFinalSaveTb(String customerRegId);
	public void deleteUserFpLifeGoalsDetailsFromFinalSaveTb(String customerRegId);
	public void deleteUserFpInsuranceDetailsFromFinalSaveTb(String customerRegId);
	public void deleteUserFpOutstandindLoansDetailsFromFinalSaveTb(String customerRegId);
	public void deleteUserFpFinAssetDetailsFromFinalSaveTb(String customerRegId);
	
	//clear fp assumption save data from tb with given customerTempRegId
	public void deleteUserFpSavedMiscAssumptionsFromTb(String customerRegId);
	public void deleteUserFpSavedLoanAssumptionsFromTb(String customerRegId);
	public void deleteUserFpSavedSalaryExpenseIncrementAssumptionsFromTb(String customerRegId);
	
	//save user input to temp Tb
	public void saveUserFpMasterDetailsToTempTb(String customerRegId, String gender, int selfAge, int selfMonthlyTakeHomeSalary, String marritalStatus, int spouseAge, int spouseMonthlyTakeHomeSalary, int totalMonthlySalary, int monthlySavings, float savingsRate, int totalCurrentFinancialAssets, int expectedRiskScoreId);
	public void saveUserFpLifeGoalsDetailsToTempTb(String customerRegId, int goalID, String goalDesc, int yearofRealization, int frequencyId, String wantLoan, int estimatedAmount);
	public void saveUserFpInsuranceDetailsToTempTb(String customerRegId, String insuranceDesc, int insuranceCover, int annualPremium, int finalYear, String loanType);
	public void saveUserFpOutstandindLoansDetailsToTempTb(String customerRegId, String loanDesc, int loanAmount, int monthlyEmi, int finalYear);
	public void saveUserFpFinAssetDetailsToTempTb(String customerRegId, int assetId, String assetName, int amount);
	
	//final save of user input data to tb
	public void saveUserFpMasterDetailsToTb(String customerRegId, String gender, int selfAge, int selfMonthlyTakeHomeSalary, String marritalStatus, int spouseAge, int spouseMonthlyTakeHomeSalary, int totalMonthlySalary, int monthlySavings, float savingsRate, int totalCurrentFinancialAssets, int expectedRiskScoreId);
	public void saveUserFpLifeGoalsDetailsToTb(String customerRegId, int goalID, String goalDesc, int yearofRealization, int frequencyId, String wantLoan, int estimatedAmount);
	public void saveUserFpInsuranceDetailsToTb(String customerRegId, String insuranceDesc, int insuranceCover, int annualPremium, int finalYear, String loanType);
	public void saveUserFpOutstandindLoansDetailsToTb(String customerRegId, String loanDesc, int loanAmount, int monthlyEmi, int finalYear);
	public void saveUserFpFinAssetDetailsToTb(String customerRegId, int assetId, String assetName, int amount);
	
	//Fetching Default Assumptions from Tb (using assumptionId)
	//public FpMiscAssumptionsTb getDefaultMiscAssumptionsFromTb(int assumptionId);
	//public List<FpLoanRelatedAssumptionsTb> getDefaultLoanAssumptionsFromTb(int assumptionId);
	//public List<FpSalaryExpenseIncrementAssumptionsTb> getDefaultSalaryExpenseIncrementFromTb(int assumptionId);
	
	//Fetching Default Assumptions from Tb (using customerRegId)
	public FpMiscAssumptionsTb getMiscAssumptionsFromTb(String customerRegId);
	public List<FpLoanRelatedAssumptionsTb> getLoanAssumptionsFromTb(String customerRegId);
	public List<FpSalaryExpenseIncrementAssumptionsTb> getSalaryExpenseIncrementFromTb(String customerRegId);
	public List<FpFinancialAssetGrowthAssumptionTb> getFinAssetGrowthRateFromTb();
		
	public boolean checkUserResponseExistsInTempTb(String customerRegId);
	
	public boolean checkUserResponseExistsInFinalSaveTb(String customerRegId);
	
	public boolean checkUserEntryExistsInMasterCustomerTb(String customerRegId);
	
	//Check of presence of user in assumption Fp Tb
	public boolean checkUserEntryExistsInFpMiscAssumptionTb(String customerRegId);
	public boolean checkUserEntryExistsInFpLoanAssumptionTb(String customerRegId);
	public boolean checkUserEntryExistsInFpSalaryExpenseIncrementTb(String customerRegId);
	
	public int getGoalLoanMappingFromTb(int goalId);
	
	public float getLoanDownPaymentPercentFromTb(int loanId);
	
	public int getLoanDurationFromTb(int loanId);
	
	public float getLoanInterestRateFromTb(int loanId);
	
	public String getFrequencyDescription(int frequencyId);
	
	public float getMMFReturns(int riskProfileId);
	
	public float getStandardDeviation(int riskProfileId);
	
	public String getIdFromTempInvTbUsingEmail(String emailId);
	
	public String getNameFromTempInvTbUsingRegId(String customerRegId);
	
	public int getAssumptionIdFromMiscAssumptionTb(String customerRegId);
	
	public int getMaxAssumptionIdFromMiscAssumptionTb();
	
	
	// save assumptions to tb (if edited) by user
	public void saveFpMiscAssumptionsToDatabase(FpMiscAssumptionsTb fpmiscassumptions, String customerRegId, int flag);
	public void saveFpLoanAssumptionsToDatabase(List<FpLoanRelatedAssumptionsTb> fploanassumptions, String customerRegId);
	public void saveFpSalaryExpenseIncrementAssumptionsToDatabase(List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrementassumption, String customerRegId);
	
	//fetch user input data from final tb
	public FpMasterUserInputDetailsTb getFpUserInputsFromTb(String customerRegId);
	public List<FpLifeGoalsUserInputTb> getFpGoalsUserInputFromTb(String customerRegId);
	public List<FpFinancialAssetUserInputDetailsTb> getFpFinAssetUserInputFromTb(String customerRegId);
	public List<FpInsuranceDetailsUserInputsTb> getFpInsuranceUserInputFromTb(String customerRegId);
	public List<FpOutstandingloansUserInputsTb> getFpOutstandingLoansUserInputFromTb(String customerRegId);
	
	//fetch user input data from temp tb
	public FpTempMasterUserInputDetailsTb getFpUserInputsFromTempTb(String customerRegId);
	public List<FpTempLifeGoalsUserInputTb> getFpGoalsUserInputFromTempTb(String customerRegId);
	public List<FpTempFinancialAssetUserInputDetailsTb> getFpFinAssetUserInputFromTempTb(String customerRegId);
	public List<FpTempInsuranceDetailsUserInputsTb> getFpInsuranceUserInputFromTempTb(String customerRegId);
	public List<FpTempOutstandingloansUserInputsTb> getFpOutstandingLoansUserInputFromTempTb(String customerRegId);

	public String getCustomerRegId(String customerID);

	public boolean getUserFpStatus(String regId);

	public void updateFpUsingRp(FpMasterUserInputDetailsTb muid);

}