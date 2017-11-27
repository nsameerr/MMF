package com.gtl.mmf.service.vo.financialplanner;

public class CurrentOutstandingLoans {
	private String loanDescription;
	private float loanAmount;
	private float emi;
	private int finalYearofPayment;
	
	public CurrentOutstandingLoans(){
		// This is a default constructor
	}
	
	public String getLoanDescription() {
		return loanDescription;
	}

	public void setLoanDescription(String loanDescription) {
		this.loanDescription = loanDescription;
	}

	public float getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(float loanAmount) {
		this.loanAmount = loanAmount;
	}

	public float getemi() {
		return emi;
	}

	public void setemi(float emi) {
		this.emi = emi;
	}

	public int getFinalYearofPayment() {
		return finalYearofPayment;
	}

	public void setFinalYearofPayment(int finalYearofPayment) {
		this.finalYearofPayment = finalYearofPayment;
	}
	
	
}
