package com.gtl.mmf.bao;

import java.util.List;

import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;


public interface IFpSetAssumptionsBAO {
    
	//Assigning Default Assumptions From Tb
	//Called when user visits Fp for first time
	public FpMiscAssumptionsTb assignDefaultFpMiscAssumptions();
	public List<FpLoanRelatedAssumptionsTb> assignDefaultFpLoanAssumption();
	public List<FpSalaryExpenseIncrementAssumptionsTb> assignDefaultFpSalaryExpenseIncrementAssumption();
	public List<FpFinancialAssetGrowthAssumptionTb> assignDefaultFpAssetGrowthAssumption();
	
    //Assigning Saved Assumptions From Tb
	//Called for successive visits of the user to Fp
	public FpMiscAssumptionsTb assignSavedFpMiscAssumptions(String customerId);
	public List<FpLoanRelatedAssumptionsTb> assignSavedFpLoanAssumption(String customerId);
	public List<FpSalaryExpenseIncrementAssumptionsTb> assignSavedFpSalaryExpenseIncrementAssumption(String customerId);
	
	
}
