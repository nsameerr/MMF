package com.gtl.mmf.bao.impl;

import static java.lang.Math.pow;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.bao.IFploansBAO;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpMappingGoalsidToLoansidTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;

public class FploansBAOImpl implements IFploansBAO {
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.fploansimpl");
	
	@Autowired
	private IFinancialPlannerDAO fpDao;	 
	private List<FpMappingGoalsidToLoansidTb> fpMappingLoanGoalId;
	private List<FpLoanRelatedAssumptionsTb> fpLoanAssumptions;
	
	// getAnnualEMI function returns the annual amount of EMI payable
	@Transactional
	public float getAnnualEMI(float principal, float rate, int years)
	{
		float monthlyRate = rate/(12 * 100);
		
		float totalPeriod = years*12;

		float tempVar = (float)Math.pow((1 + monthlyRate),totalPeriod);   // this variable calculates (1+R)^N
           
		float annualEMI = 12 * principal * monthlyRate * (tempVar / (tempVar - 1));
 		
		return annualEMI;
	}
	
	/*@Transactional
	public int getloanId(int goalId)
	{
		int loanId;
		LOGGER.log(Level.INFO,"Suman code - check goalId Inside FploansBAO" + goalId);
		loanId = fpDao.getGoalLoanMappingFromTb(2);
		
		LOGGER.log(Level.INFO,"Suman code - check loanId Inside FploansBAO" + loanId);
		
		return loanId;
	}*/
	
	// Fetch Down Payment Rate based on loanID
	/*@Transactional
	public float getDownpaymentRate(int loanId)
	{
		float downPaymentRate = 0;
		fpLoanAssumptions = fpDao.getLoanAssumptionsFromTb();
		
		for(int i=0; i<fpLoanAssumptions.size(); i++)
		{
			if(loanId == fpLoanAssumptions.get(i).getLoanAssumptionId())
			{
				downPaymentRate = fpLoanAssumptions.get(i).getDownPaymentPercent();
			}
		}
		
		return downPaymentRate;
	}*/
	
	
	// Fetch Loan Interest Rate based on loanID
	/*@Transactional
	public float getLoanInterestRate(int loanId)
	{
		float loanInterestRate = 0;
		fpLoanAssumptions = fpDao.getLoanAssumptionsFromTb();
		
		for(int i=0; i<fpLoanAssumptions.size(); i++)
		{
			if(loanId == fpLoanAssumptions.get(i).getLoanAssumptionId())
			{
				loanInterestRate = fpLoanAssumptions.get(i).getInterestRate();
			}
		}
		
		return loanInterestRate;
	}*/
	
	
	// Fetch Loan Duration based on loanID
	/*@Transactional
	public int getLoanDuration(int loanId)
	{
		int loanDuration = 0;
		fpLoanAssumptions = fpDao.getLoanAssumptionsFromTb();
		
		for(int i=0; i<fpLoanAssumptions.size(); i++)
		{
			if(loanId == fpLoanAssumptions.get(i).getLoanAssumptionId())
			{
				loanDuration = fpLoanAssumptions.get(i).getLoanDuration();
			}
		}
		
		return loanDuration;
	}
	*/
	
	// down_payment function calculates the down_payment needed for the loan
	@Transactional
	public float getDownpayment(String loanYesNo, float estimatedAmount, float downpaymentRate)
	{
		
		float downPayment = 0;
		
		if(loanYesNo.equalsIgnoreCase("Yes") || loanYesNo.equalsIgnoreCase ("true"))
		{
			downPayment = (downpaymentRate/100) * estimatedAmount;
		}
		
		else if (loanYesNo.equalsIgnoreCase("No") || loanYesNo.equalsIgnoreCase ("false"))
		{
			downPayment = estimatedAmount;
		}
		
		return downPayment;
		
	}
	
	@Transactional
	public int getStartAgeofEMIpayment(int yearOfRealization, int userAge)
	{
		int currentYear, startAgeofEMIpayment, timeGap;
		Calendar current = Calendar.getInstance();
		currentYear = current.get(Calendar.YEAR);
		
		timeGap = yearOfRealization - currentYear;
		startAgeofEMIpayment = userAge + timeGap;
		
		return startAgeofEMIpayment;
	}
	
	/* estimated amount entered by the user in as per the current market price
	   This function will incorporate the inflation rate and give the expected future price

       Arguments:  
	   amount - the amount entered by the user
	   inflationRate - rate at which the amount grows annually
	   period - span of time (in years) for which the loan would continue
	   */
	
	@Transactional
	public float getFutureInflatedAmount(float amount, float inflationRate, int yearOfRealization)
	{
		
		float futureAmount;
		Calendar current = Calendar.getInstance();
		int currentYear = current.get(Calendar.YEAR);
		
		/* -> Calculates difference between current year and goal realization year
		   -> User enters estimated amount as per current valuation
		   -> timeGap variable is used to inflate the amount to the realization year */
		
		//Integer timeGap = yearOfRealization - currentYear;
		//futureAmount = amount * Math.pow((1+inflationRate), timeGap);
        
		futureAmount = amount;
		
		for(int counter=currentYear+1; counter<=yearOfRealization; counter++)
		{
			futureAmount = futureAmount * (1+(inflationRate/100));
		}
		
		
			return futureAmount;
		
			
	
	}
	
	
}