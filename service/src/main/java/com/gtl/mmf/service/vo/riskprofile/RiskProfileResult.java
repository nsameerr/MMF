package com.gtl.mmf.service.vo.riskprofile;

import java.util.List;


public class RiskProfileResult {
	
	private String customerRegistrationId;
	private int riskScore;
    private String riskProfileDescription;
  //  private String riskProfileGroupName;
	private List<RecommendedAssetAllocation> recommendedAssetAllocation;
	
 /*   public String getRiskProfileGroupName() {
		return riskProfileGroupName;
	}

	public void setRiskProfileGroupName(String riskProfileGroupName) {
		this.riskProfileGroupName = riskProfileGroupName;
	}*/
	
	public String getCustomerRegistrationId() {
		return customerRegistrationId;
	}

	public void setCustomerRegistrationId(String customerRegistrationId) {
		this.customerRegistrationId = customerRegistrationId;
	}

	public int getRiskScore() {
		return riskScore;
	}
	
	public void setRiskScore(int riskScore) {
		this.riskScore = riskScore;
	}
	
	public String getRiskProfileDescription() {
		return riskProfileDescription;
	}
	
	public void setRiskProfileDescription(String riskProfileDescription) {
		this.riskProfileDescription = riskProfileDescription;
	}
	
	public List<RecommendedAssetAllocation> getRecommendedAssetAllocation() {
		return recommendedAssetAllocation;
	}
	
	public void setRecommendedAssetAllocation(List<RecommendedAssetAllocation> recommendedAssetAllocation) {
		this.recommendedAssetAllocation = recommendedAssetAllocation;
	}
        

}
