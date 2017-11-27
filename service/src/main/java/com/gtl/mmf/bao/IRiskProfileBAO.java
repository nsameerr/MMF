
package com.gtl.mmf.bao;

import java.util.Map;

import com.gtl.mmf.entity.CustomerRiskScoreTb;
import com.gtl.mmf.service.vo.QuestionnaireVO;
import com.gtl.mmf.service.vo.riskprofile.RPResponseSet;
import com.gtl.mmf.service.vo.riskprofile.RiskProfileResult;
public interface IRiskProfileBAO{
	
	public String fetchCustomerRegistrationIdUsingEmail(String emailId);
	
	public int getMaxRiskScore(int noOfQuestions);
	
	//public int computeAndSaveRiskScore(RPResponseSet responseSet, Integer customerId);
	
	public int computeAndSaveRiskScore(RPResponseSet responseSet);
	
	public RiskProfileResult getRiskProfileOutput(String customerRegistrationId);

	public Map<Integer, QuestionnaireVO> getRiskProfileBackwordFormat(RPResponseSet responseSet);
	
	
	//public RiskProfileResult getRiskProfileResult(Integer customer_id, Integer riskProfileResultId);
	
}