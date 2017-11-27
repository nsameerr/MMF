package com.gtl.mmf.service.vo.financialplanner;

public class LifeInsuranceCover {
	private String lifeInsuranceDescription;
	private float insuranceCover;
	private float annualPremium;
	private int finalYearofPayment;

	public LifeInsuranceCover(){
		// This is a default constructor
	}
	
	public String getLifeInsuranceDescription() {
		return lifeInsuranceDescription;
	}

	public void setLifeInsuranceDescription(String lifeInsuranceDescription) {
		this.lifeInsuranceDescription = lifeInsuranceDescription;
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
	
	public int getFinalYearofPayment() {
		return finalYearofPayment;
	}
	
	public void setFinalYearofPayment(int finalYearofPayment) {
		this.finalYearofPayment = finalYearofPayment;
	}
		
}
