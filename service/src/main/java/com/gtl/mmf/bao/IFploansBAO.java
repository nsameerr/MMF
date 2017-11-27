package com.gtl.mmf.bao;

import java.util.List;


public interface IFploansBAO {
	
	public float getAnnualEMI(float principal, float rate, int years);
	
	//public float getDownpaymentRate(int loanId);
	
	//public int getloanId(int goalId);
	
	//public float getLoanInterestRate(int loanId);
	
	//public int getLoanDuration(int loanId);
	
	public float getDownpayment(String loanYesNo, float estimatedAmount, float downpaymentRate);
	
	public int getStartAgeofEMIpayment(int yearOfRealization, int userAge);
	
	public float getFutureInflatedAmount(float amount, float inflationRate, int yearOfRealization);
		
}