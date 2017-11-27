package com.gtl.mmf.bao;

import java.util.List;

import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;
import com.gtl.mmf.service.vo.financialplanner.FPResponseSet;
import com.gtl.mmf.service.vo.financialplanner.CurrentOutstandingLoans;
import com.gtl.mmf.service.vo.financialplanner.FinancialAssetList;
import com.gtl.mmf.service.vo.financialplanner.HealthInsuranceCover;
import com.gtl.mmf.service.vo.financialplanner.LifeInsuranceCover;
import com.gtl.mmf.service.vo.financialplanner.LifeGoalsInput;

public interface IFpSaveAndFetchDataForFpBAO {

	// BAO function to delete Fp Input Data from temp Tb
	public void deleteFpResponseFromTempTb(String customerId);
	
	// BAO function to delete Fp Input Data from final save Tb
	public void deleteFpResponseFromFinalSaveTb(String customerId);
	
	// BAO function to delete Fp Saved Assumption Data from Tb
	public void deleteFpSavedAssumptionsFromTb(String customerId);
	
	// BAO functions to save Fp Input Data to temp Tb
	public void saveFpUserBasicInputDetailsToTempTb(FPResponseSet fpresponse, String customerId);
	public void saveFpUserOutstandingLoansToTempTb(List<CurrentOutstandingLoans> outstandingLoans, String customerId);
	public void saveFpUserInsuranceDetailsToTempTb(List<HealthInsuranceCover> healthInsurance, List<LifeInsuranceCover> lifeInsurance, String customerId);
	public void saveFpFinAssetDetailsToTempTb(List<FinancialAssetList> finAsset, String customerId);
	public void saveFpLifeGoalsToTempTb(List<LifeGoalsInput> lifeGoals, String customerId);
	
	// BAO functions to save Fp Input Data to Final Save Tb On "Save & Submit"
	public void finalSaveFpUserBasicInputDetails(FPResponseSet fpresponse, String customerId);
	public void finalSaveFpUserOutstandingLoans(List<CurrentOutstandingLoans> outstandingLoans, String customerId);
	public void finalSaveFpUserInsuranceDetails(List<HealthInsuranceCover> healthInsurance, List<LifeInsuranceCover> lifeInsurance, String customerId);
	public void finalSaveFpFinAssetDetails(List<FinancialAssetList> finAsset, String customerId);
	public void finalSaveFpLifeGoals(List<LifeGoalsInput> lifeGoals, String customerId);
	
	public String getRegIdFromTb(String email);
	
	public boolean checkUserExistsInFpTempTb(String customerId);
	
	public boolean checkUserExistsInFpFinalSaveTb(String customerId);
	
	public boolean checkUserExistsInMasterCustomerTb(String customerId);
	
	public FPResponseSet checkAndGetFpSavedResponseFromDatabase(String customerId);
	
	public FPResponseSet getFpResponseFromTb(String customerId);
	
	public FPResponseSet getFpResponseFromTempTb(String customerId);
	
	public FPResponseSet getFpDefaultResponse();
	
	//public FpMasterAssumption getFpsavedAssumptionsFromTb(String customerId);
	
	public String getCustomerRegId(String customerId);
}
