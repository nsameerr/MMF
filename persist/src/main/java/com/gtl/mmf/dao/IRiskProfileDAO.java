package com.gtl.mmf.dao;

import java.util.List;

import com.gtl.mmf.entity.RiskProfileGroupDescriptionMasterTb;
import com.gtl.mmf.entity.RiskProfileRecommendedPortfolioMasterTb;
import com.gtl.mmf.entity.CustomerRiskProfileResultTb;
import com.gtl.mmf.entity.CustomerRiskScoreTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioSizeTb;

import java.util.concurrent.atomic.AtomicLong;


public interface IRiskProfileDAO{
	
	public String fetchRegistrationIdFromTb(String emailId);
	public int getOptionValueFromTb(int optionId);
	
	public List<RiskProfileGroupDescriptionMasterTb> getRiskProfileGroupDetailsFromTb();
	
	public List<RiskProfileRecommendedPortfolioMasterTb> getRecommendedPortfolioFromTb(int recommendedPortfolioId); 
	
	//public void saveRiskProfileResponse(Integer customerId, Integer responseSetId, Integer questionId, Integer answerId);
	public void saveRiskProfileResponse(int response_set_id, int question_id, int option_id);
	public void saveRiskScore(String customerRegistrationId, int riskScore, int rpGroupId, int recommendedPortfolioId);
	public CustomerRiskProfileResultTb getCustomerRpResultFromTb(String customerRegistrationId);
	public List<MasterAssetTb> getMasterAssetTb();
	public int getPortfolioSizeId(int funds);
	public double getTotalAllocatedFunds(String customerRegistrationId);
	public int getOptionValueFromTb(int qid, int answerId);
	
	//public Integer saveRiskProfileResult(Integer responsetSetId, Integer customerId, Integer finalRiskScore, Integer riskProfileGroupId, Integer riskProfileRecommendedPortfolioId);
	//public String getRiskProfileGroupDescription(Integer riskProfileGroupId);
	//public List<CustomerRiskProfileResultTb> getCustomerRiskProfileResult(Integer customerId, Integer riskProfileResultId);
	//public Integer getLastResponseSetId();

    
}