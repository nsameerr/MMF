package com.gtl.mmf.service.vo.financialplanner;

public class HealthInsuranceCover {
	private String healthInsuranceDescription;
	private float insuranceCover;
	private float annualPremium;

	public HealthInsuranceCover(){	
		// This is a default constructor
	}
	
	public String getHealthInsuranceDescription() {
		return healthInsuranceDescription;
	}

	public void setHealthInsuranceDescription(String healthInsuranceDescription) {
		this.healthInsuranceDescription = healthInsuranceDescription;
	}
	
	public float getInsuranceCover() {
		return insuranceCover;
	}
	
	public void setInsuranceCover(float insuranceCover) {
		this.insuranceCover = insuranceCover;
	}
	
	public float getAnnualPremium() {
		return annualPremium;
	}

	public void setAnnualPremium(float annualPremium) {
		this.annualPremium = annualPremium;
	}
	
}
