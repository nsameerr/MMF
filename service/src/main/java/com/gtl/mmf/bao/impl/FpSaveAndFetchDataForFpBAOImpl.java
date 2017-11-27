package com.gtl.mmf.bao.impl;

import java.util.Calendar;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.gtl.mmf.service.vo.financialplanner.assumptions.FpMasterAssumption;
import com.gtl.mmf.service.vo.financialplanner.CurrentOutstandingLoans;
import com.gtl.mmf.service.vo.financialplanner.FPResponseSet;
import com.gtl.mmf.service.vo.financialplanner.FinancialAssetList;
import com.gtl.mmf.service.vo.financialplanner.HealthInsuranceCover;
import com.gtl.mmf.service.vo.financialplanner.LifeGoalsInput;
import com.gtl.mmf.service.vo.financialplanner.LifeInsuranceCover;
import com.gtl.mmf.service.vo.riskprofile.RecommendedAssetAllocation;
import com.gtl.mmf.bao.IFpSaveAndFetchDataForFpBAO;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.entity.FpLifeGoalsUserInputTb;
import com.gtl.mmf.entity.FpFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpOutstandingloansUserInputsTb;
import com.gtl.mmf.entity.FpMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpTempFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpTempLifeGoalsUserInputTb;
import com.gtl.mmf.entity.FpTempMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempOutstandingloansUserInputsTb;

public class FpSaveAndFetchDataForFpBAOImpl implements IFpSaveAndFetchDataForFpBAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.fpsaveandfetchdataforfpbaoimpl");
	
	@Autowired
	private IFinancialPlannerDAO fpDao;
	
	
	@Transactional
	public void deleteFpResponseFromTempTb(String customerId)
	{
		try
		{
		//Making DAO Call
	    fpDao.deleteUserFpMasterDetailsFromTempTb(customerId);
	    fpDao.deleteUserFpOutstandindLoansDetailsFromTempTb(customerId);
	    fpDao.deleteUserFpInsuranceDetailsFromTempTb(customerId);
	    fpDao.deleteUserFpFinAssetDetailsFromTempTb(customerId);
	    fpDao.deleteUserFpLifeGoalsDetailsFromTempTb(customerId);   
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in  deleteFpResponseFromTempTb() in FpSaveAndFetchDataForFpBAOImpl",e);
		}
	}
	
	
	@Transactional
	public void deleteFpResponseFromFinalSaveTb(String customerId)
	{
		try
		{
		//Making DAO Call
	    fpDao.deleteUserFpMasterDetailsFromFinalSaveTb(customerId);
	    fpDao.deleteUserFpOutstandindLoansDetailsFromFinalSaveTb(customerId);
	    fpDao.deleteUserFpInsuranceDetailsFromFinalSaveTb(customerId);
	    fpDao.deleteUserFpFinAssetDetailsFromFinalSaveTb(customerId);
	    fpDao.deleteUserFpLifeGoalsDetailsFromFinalSaveTb(customerId);   
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in  deleteFpResponseFromFinalSaveTb() in FpSaveAndFetchDataForFpBAOImpl",e);
		}
	}
	
	@Transactional
	public void deleteFpSavedAssumptionsFromTb(String customerId)
	{
		try
		{
		//Making DAO Call
	    fpDao.deleteUserFpSavedMiscAssumptionsFromTb(customerId);
	    fpDao.deleteUserFpSavedLoanAssumptionsFromTb(customerId);
	    fpDao.deleteUserFpSavedSalaryExpenseIncrementAssumptionsFromTb(customerId);   
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - error in  deleteFpSavedAssumptionsFromTb() in FpSaveAndFetchDataForFpBAOImpl",e);
		}
	}
	
	
	/*******************  BAO functions to save Fp Input Data to temp Tb  ********************/
	/****************************************************************************************/
	
	@Transactional
	public void saveFpUserBasicInputDetailsToTempTb(FPResponseSet fpresponse, String customerId)
	{
			    
		int spouseAge=0, spouseMonthlyTakehomeSalary=0;  
		String gender = fpresponse.getGender();
		int userAge = fpresponse.getUserAge();
		String relationStatus = fpresponse.getRelationStatus();
		int userMonthlyTakehomeSalary = (int)fpresponse.getUserMonthlyTakehomeSalary();
		
		if(relationStatus.equalsIgnoreCase("Married"))
		{
			spouseAge = fpresponse.getSpouseAge();
			spouseMonthlyTakehomeSalary = (int)fpresponse.getSpouseMonthlyTakehomeSalary();
		}
		
		else if(relationStatus.equalsIgnoreCase("Single"))
		{
			spouseAge = 0;
			spouseMonthlyTakehomeSalary = 0;
		}
		
		int totalSalary = userMonthlyTakehomeSalary + spouseMonthlyTakehomeSalary;
		int monthlySavings = (int)fpresponse.getMonthlySavings();
	    float savingsRate = fpresponse.getSavingsRate();
	    int initialTotalFinancialAsset = (int)fpresponse.getInitialTotalFinancialAsset();
	    int riskGroupId = fpresponse.getRiskGroupId();
	    
	    //Making DAO Call to SAVE
	    fpDao.saveUserFpMasterDetailsToTempTb(customerId,gender,userAge,userMonthlyTakehomeSalary,relationStatus,spouseAge,spouseMonthlyTakehomeSalary,totalSalary,monthlySavings,savingsRate,initialTotalFinancialAsset,riskGroupId);
	
	}
	
	@Transactional
	public void saveFpUserOutstandingLoansToTempTb(List<CurrentOutstandingLoans> outstandingLoans, String customerId)
	{
		String description;
		int amount, emi, finalYear;
		
		for(int i=0; i<outstandingLoans.size(); i++)
		{
			description = outstandingLoans.get(i).getLoanDescription();
			amount = (int)outstandingLoans.get(i).getLoanAmount();
			emi = (int)outstandingLoans.get(i).getemi();
			finalYear = outstandingLoans.get(i).getFinalYearofPayment();
			//Making DAO Call to SAVE
			fpDao.saveUserFpOutstandindLoansDetailsToTempTb(customerId, description, amount, emi, finalYear);
		}
	}
	
	@Transactional
	public void saveFpUserInsuranceDetailsToTempTb(List<HealthInsuranceCover> healthInsurance, List<LifeInsuranceCover> lifeInsurance, String customerId)
	{
		String insuranceDescription, loanType;
		int insuranceCover, annualPremium, finalYear;
		
		//Sending Health Insurance Data
		for(int i=0; i<healthInsurance.size(); i++)
		{
			insuranceDescription = healthInsurance.get(i).getHealthInsuranceDescription();
			insuranceCover = (int)healthInsurance.get(i).getInsuranceCover();	
			annualPremium = (int)healthInsurance.get(i).getAnnualPremium();
			finalYear = 0;
			loanType = "Health";
			//Making DAO Call to SAVE
			fpDao.saveUserFpInsuranceDetailsToTempTb(customerId, insuranceDescription, insuranceCover, annualPremium, finalYear, loanType);
		}
		
		//Sending Life Insurance Data
		for(int i=0; i<lifeInsurance.size(); i++)
		{
			insuranceDescription = lifeInsurance.get(i).getLifeInsuranceDescription();
			insuranceCover = (int)lifeInsurance.get(i).getInsuranceCover();	
			annualPremium = (int)lifeInsurance.get(i).getAnnualPremium();
			finalYear = lifeInsurance.get(i).getFinalYearofPayment();
			loanType = "Life";
			//Making DAO Call to SAVE
			fpDao.saveUserFpInsuranceDetailsToTempTb(customerId, insuranceDescription, insuranceCover, annualPremium, finalYear, loanType);		
		}
	
	}
	
	@Transactional
	public void saveFpFinAssetDetailsToTempTb(List<FinancialAssetList> finAsset, String customerId)
	{
		int assetId, amount;
		String assetName;
		
		for(int i=0; i<finAsset.size(); i++)
		{
			assetId = finAsset.get(i).getAssetId();
			assetName = finAsset.get(i).getAssetDescription();
	        amount = (int)finAsset.get(i).getAssetAmount();
	        
	        //Making DAO Call to SAVE
	        fpDao.saveUserFpFinAssetDetailsToTempTb(customerId, assetId, assetName, amount);
		}
	}
	
	@Transactional
	public void saveFpLifeGoalsToTempTb(List<LifeGoalsInput> lifeGoals, String customerId)
	{
		int goalID, yearofRealization, frequencyId, estimatedAmount;   
		String goalDesc, wantLoan;
		
		for(int i=0; i<lifeGoals.size(); i++)
		{
			goalID = lifeGoals.get(i).getGoalDescriptionId();
			goalDesc = lifeGoals.get(i).getGoalDescription();
			yearofRealization = lifeGoals.get(i).getYearofRealization();
			frequencyId = lifeGoals.get(i).getFrequency();
			wantLoan = lifeGoals.get(i).getLoanYesNo();
			estimatedAmount = (int)lifeGoals.get(i).getEstimatedAmount();
			//Making DAO Call to SAVE
			fpDao.saveUserFpLifeGoalsDetailsToTempTb(customerId,goalID,goalDesc,yearofRealization,frequencyId,wantLoan,estimatedAmount);
		}
	
	}
	
	/*****************  BAO functions to save Fp Data  ***********************/
	/**************  To Final Save Tb On "Save & Submit"  *********************/
	/************************************************************************/
	
	@Transactional
	public void finalSaveFpUserBasicInputDetails(FPResponseSet fpresponse, String customerId)
	{
		int spouseAge=0, spouseMonthlyTakehomeSalary=0;  
		String gender = fpresponse.getGender();
		int userAge = fpresponse.getUserAge();
		String relationStatus = fpresponse.getRelationStatus();
		int userMonthlyTakehomeSalary = (int)fpresponse.getUserMonthlyTakehomeSalary();
		
		if(relationStatus.equalsIgnoreCase("Married"))
		{
			spouseAge = fpresponse.getSpouseAge();
			spouseMonthlyTakehomeSalary = (int)fpresponse.getSpouseMonthlyTakehomeSalary();
		}
		
		else if(relationStatus.equalsIgnoreCase("Single"))
		{
			spouseAge = 0;
			spouseMonthlyTakehomeSalary = 0;
		}
		
		int totalSalary = userMonthlyTakehomeSalary + spouseMonthlyTakehomeSalary;
		int monthlySavings = (int)fpresponse.getMonthlySavings();
	    float savingsRate = fpresponse.getSavingsRate();
	    int initialTotalFinancialAsset = (int)fpresponse.getInitialTotalFinancialAsset();
	    int riskGroupId = fpresponse.getRiskGroupId();
	    
	    //Making DAO Call
	    fpDao.saveUserFpMasterDetailsToTb(customerId,gender,userAge,userMonthlyTakehomeSalary,relationStatus,spouseAge,spouseMonthlyTakehomeSalary,totalSalary,monthlySavings,savingsRate,initialTotalFinancialAsset,riskGroupId);
	}
	
	@Transactional
	public void finalSaveFpUserOutstandingLoans(List<CurrentOutstandingLoans> outstandingLoans, String customerId)
	{
		String description;
		int amount, emi, finalYear;
		
		for(int i=0; i<outstandingLoans.size(); i++)
		{
			description = outstandingLoans.get(i).getLoanDescription();
			amount = (int)outstandingLoans.get(i).getLoanAmount();
			emi = (int)outstandingLoans.get(i).getemi();
			finalYear = outstandingLoans.get(i).getFinalYearofPayment();
			fpDao.saveUserFpOutstandindLoansDetailsToTb(customerId, description, amount, emi, finalYear);
		}
	}
	
	@Transactional
	public void finalSaveFpUserInsuranceDetails(List<HealthInsuranceCover> healthInsurance, List<LifeInsuranceCover> lifeInsurance, String customerId)
	{
		String insuranceDescription, loanType;
		int insuranceCover, annualPremium, finalYear;
		
		//LOGGER.log(Level.SEVERE,"Suman code - insurance details - inside finalSaveFpUserInsuranceDetails");
		
		//Sending Health Insurance Data
		for(int i=0; i<healthInsurance.size(); i++)
		{
			insuranceDescription = healthInsurance.get(i).getHealthInsuranceDescription();
			insuranceCover = (int)healthInsurance.get(i).getInsuranceCover();	
			annualPremium = (int)healthInsurance.get(i).getAnnualPremium();
			finalYear = 0;
			loanType = "Health";
			
			fpDao.saveUserFpInsuranceDetailsToTb(customerId, insuranceDescription, insuranceCover, annualPremium, finalYear, loanType);
		}
		
		//Sending Life Insurance Data
		for(int i=0; i<lifeInsurance.size(); i++)
		{
			insuranceDescription = lifeInsurance.get(i).getLifeInsuranceDescription();
			insuranceCover = (int)lifeInsurance.get(i).getInsuranceCover();	
			annualPremium = (int)lifeInsurance.get(i).getAnnualPremium();
			finalYear = lifeInsurance.get(i).getFinalYearofPayment();
			loanType = "Life";
			//Making DAO Call to SAVE
			fpDao.saveUserFpInsuranceDetailsToTb(customerId, insuranceDescription, insuranceCover, annualPremium, finalYear, loanType);		
		}
	}
	
	@Transactional
	public void finalSaveFpFinAssetDetails(List<FinancialAssetList> finAsset, String customerId)
	{
		int assetId, amount;
		String assetName;
		
		for(int i=0; i<finAsset.size(); i++)
		{
			assetId = finAsset.get(i).getAssetId();
			assetName = finAsset.get(i).getAssetDescription();
	        amount = (int)finAsset.get(i).getAssetAmount();
	        
	        fpDao.saveUserFpFinAssetDetailsToTb(customerId, assetId, assetName, amount);
		}
	}
	
	@Transactional
	public void finalSaveFpLifeGoals(List<LifeGoalsInput> lifeGoals, String customerId)
	{
		int goalID, yearofRealization, frequencyId, estimatedAmount;   
		String goalDesc, wantLoan;
		
		for(int i=0; i<lifeGoals.size(); i++)
		{
			goalID = lifeGoals.get(i).getGoalDescriptionId();
			goalDesc = lifeGoals.get(i).getGoalDescription();
			yearofRealization = lifeGoals.get(i).getYearofRealization();
			frequencyId = lifeGoals.get(i).getFrequency();
			wantLoan = lifeGoals.get(i).getLoanYesNo();
			estimatedAmount = (int)lifeGoals.get(i).getEstimatedAmount();
			
			fpDao.saveUserFpLifeGoalsDetailsToTb(customerId,goalID,goalDesc,yearofRealization,frequencyId,wantLoan,estimatedAmount);
		}
	}
	
	
	/***************** SAVE TO FINAL TB FINISHES ******************************/
	
	@Transactional
	public String getRegIdFromTb(String email)
	{
		String customerRegId = fpDao.getIdFromTempInvTbUsingEmail(email);
		return customerRegId;
	}
	
	
	@Transactional
	public boolean checkUserExistsInFpTempTb(String customerId)
	{
		// true = User entry does not exist in Tb
		// false = User entry exists in Tb
		boolean result = fpDao.checkUserResponseExistsInTempTb(customerId);
		return result;
	}
	
	
	@Transactional
	public boolean checkUserExistsInFpFinalSaveTb(String customerId)
	{
		// true = User entry does not exist in Tb
		// false = User entry exists in Tb
		boolean result = fpDao.checkUserResponseExistsInFinalSaveTb(customerId);
		return result;
	}
	
	@Transactional
	public boolean checkUserExistsInMasterCustomerTb(String customerId)
	{
		// true = User entry does not exist in Tb
		// false = User entry exists in Tb
		boolean result = fpDao.checkUserEntryExistsInMasterCustomerTb(customerId);
		return result;
	}
	
	@Transactional
	public FPResponseSet checkAndGetFpSavedResponseFromDatabase(String customerId)
	{
		FPResponseSet fpresponseset = new FPResponseSet();
		
		boolean fptempdetailsobj = checkUserExistsInFpTempTb(customerId);
		boolean fpdetailsobj = checkUserExistsInFpFinalSaveTb(customerId);
		
		// This means user answered questions and coming to output page
		if(fptempdetailsobj == false)
		{
			fpresponseset = getFpResponseFromTempTb(customerId);
		}
		
		// Means user directly coming to output page
		else if(fptempdetailsobj == true && fpdetailsobj == false)
		{
			fpresponseset = getFpResponseFromTb(customerId);
		}
		
		return fpresponseset;
	}
	
	/************** FETCHING SAVED FP DATA FROM FINAL TB  ******************/
	/**********************************************************************/

	@Transactional
	public FPResponseSet getFpResponseFromTb(String customerId)
	{
		FpMasterUserInputDetailsTb fpmasteruserinputdetailstb;
		List<FpLifeGoalsUserInputTb> fplifegoalsuserinputtb;
		List<FpFinancialAssetUserInputDetailsTb> fpfinancialassetuserinputdetailstb;
		List<FpInsuranceDetailsUserInputsTb> fpinsurancedetailsuserinputstb;
		List<FpOutstandingloansUserInputsTb> foputstandingloansuserinputstb;
		FPResponseSet fpresponseset = new FPResponseSet();
		
		//Fetching saved data from Database Tables
		fpmasteruserinputdetailstb = fpDao.getFpUserInputsFromTb(customerId);
		fplifegoalsuserinputtb = fpDao.getFpGoalsUserInputFromTb(customerId);
		fpfinancialassetuserinputdetailstb = fpDao.getFpFinAssetUserInputFromTb(customerId);
		fpinsurancedetailsuserinputstb = fpDao.getFpInsuranceUserInputFromTb(customerId);
		foputstandingloansuserinputstb = fpDao.getFpOutstandingLoansUserInputFromTb(customerId);
		
		//Assigning data from fpmasteruserinputdetailstb
		fpresponseset.setGender(fpmasteruserinputdetailstb.getGender());
		fpresponseset.setUserAge(fpmasteruserinputdetailstb.getSelfAge());
		fpresponseset.setRelationStatus(fpmasteruserinputdetailstb.getMarritalStatus());
		fpresponseset.setUserMonthlyTakehomeSalary(fpmasteruserinputdetailstb.getSelfMonthlyTakeHomeSalary());
		fpresponseset.setSpouseAge(fpmasteruserinputdetailstb.getSpouseAge());
		fpresponseset.setSpouseMonthlyTakehomeSalary(fpmasteruserinputdetailstb.getSpouseMonthlyTakeHomeSalary());
		fpresponseset.setSavingsRate(fpmasteruserinputdetailstb.getSavingsRate());
		float monthlySavings = (fpresponseset.getSavingsRate()/100)*(fpresponseset.getUserMonthlyTakehomeSalary()+fpresponseset.getSpouseMonthlyTakehomeSalary());
		fpresponseset.setMonthlySavings(monthlySavings);
		fpresponseset.setInitialTotalFinancialAsset(fpmasteruserinputdetailstb.getTotalCurrentFinancialAssets());
		fpresponseset.setRiskGroupId(fpmasteruserinputdetailstb.getExpectedRiskScoreId());
		
		//Assigning data from fplifegoalsuserinputtb
		List<LifeGoalsInput> lifeGoalsListTempObj = new ArrayList<LifeGoalsInput>();
		
		for(int i = 0; i < fplifegoalsuserinputtb.size(); i++)
		{
			LifeGoalsInput tempLifeGoalsObj = new LifeGoalsInput();
			
			tempLifeGoalsObj.setGoalDescription(fplifegoalsuserinputtb.get(i).getGoalDescription());
			tempLifeGoalsObj.setGoalDescriptionId(fplifegoalsuserinputtb.get(i).getGoalId());
			tempLifeGoalsObj.setYearofRealization(fplifegoalsuserinputtb.get(i).getYearOfRealization());
			tempLifeGoalsObj.setFrequency(fplifegoalsuserinputtb.get(i).getRecurringFrequencyId());
			tempLifeGoalsObj.setLoanYesNo(fplifegoalsuserinputtb.get(i).getWantLoanYesNo());
			tempLifeGoalsObj.setEstimatedAmount(fplifegoalsuserinputtb.get(i).getEstimatedAmountAsPerCurrentPrices());
			
			lifeGoalsListTempObj.add(i,tempLifeGoalsObj);	
		}
		
		fpresponseset.setLifeGoals(lifeGoalsListTempObj);
		
		
		//Assigning data from fpfinancialassetuserinputdetailstb
		List<FinancialAssetList> finAssetListTempObj = new ArrayList<FinancialAssetList>();
		
		for(int i = 0; i < fpfinancialassetuserinputdetailstb.size(); i++)
		{
			FinancialAssetList tempFinAssetObj = new FinancialAssetList();
			
			tempFinAssetObj.setAssetId(fpfinancialassetuserinputdetailstb.get(i).getFinancialAssetId());
			tempFinAssetObj.setAssetDescription(fpfinancialassetuserinputdetailstb.get(i).getFinancialAssetName());
			tempFinAssetObj.setAssetAmount(fpfinancialassetuserinputdetailstb.get(i).getAmount());
		
			finAssetListTempObj.add(i,tempFinAssetObj);
		}
		fpresponseset.setFinancialAssetList(finAssetListTempObj);
		
		
		//Assigning data from fpinsurancedetailsuserinputstb
		List<LifeInsuranceCover> lifeInsuranceListTempObj = new ArrayList<LifeInsuranceCover>();
		List<HealthInsuranceCover> healthInsuranceListTempObj = new ArrayList<HealthInsuranceCover>();
		int posLife = 0, posHealth = 0;
		
		for(int i = 0; i < fpinsurancedetailsuserinputstb.size(); i++)
		{
			LifeInsuranceCover tempLifeInsuObj = new LifeInsuranceCover();
			HealthInsuranceCover tempHealthInsuObj = new HealthInsuranceCover();
			
			if(fpinsurancedetailsuserinputstb.get(i).getInsuranceType().equalsIgnoreCase ("Life"))
			{
				tempLifeInsuObj.setLifeInsuranceDescription(fpinsurancedetailsuserinputstb.get(i).getInsuranceName());
				tempLifeInsuObj.setInsuranceCover(fpinsurancedetailsuserinputstb.get(i).getInsuranceCover());
				tempLifeInsuObj.setAnnualPremium(fpinsurancedetailsuserinputstb.get(i).getAnnualPremium());
				tempLifeInsuObj.setFinalYearofPayment(fpinsurancedetailsuserinputstb.get(i).getFinalYearOfPayment());
			
				lifeInsuranceListTempObj.add(posLife,tempLifeInsuObj);
				posLife++;
			}
			
			else if(fpinsurancedetailsuserinputstb.get(i).getInsuranceType().equalsIgnoreCase ("Health"))
			{
				tempHealthInsuObj.setHealthInsuranceDescription(fpinsurancedetailsuserinputstb.get(i).getInsuranceName());
				tempHealthInsuObj.setInsuranceCover(fpinsurancedetailsuserinputstb.get(i).getInsuranceCover());
				tempHealthInsuObj.setAnnualPremium(fpinsurancedetailsuserinputstb.get(i).getAnnualPremium());
			
				healthInsuranceListTempObj.add(posHealth,tempHealthInsuObj);
				posHealth++;
			}
			
		}
		
		fpresponseset.setLifeInsuranceCover(lifeInsuranceListTempObj);
		fpresponseset.setHealthInsuranceCover(healthInsuranceListTempObj);
		
		//Assigning data from foputstandingloansuserinputstb
		List<CurrentOutstandingLoans> currentLoansListTempObj = new ArrayList<CurrentOutstandingLoans>();
		
		for(int i = 0; i < foputstandingloansuserinputstb.size(); i++)
		{
			CurrentOutstandingLoans tempCurrentLoansObj = new CurrentOutstandingLoans();
			
			tempCurrentLoansObj.setLoanDescription(foputstandingloansuserinputstb.get(i).getLoanDescription());
			tempCurrentLoansObj.setLoanAmount(foputstandingloansuserinputstb.get(i).getLoanAmount());
			tempCurrentLoansObj.setemi(foputstandingloansuserinputstb.get(i).getMonthlyEmiAmount());
			tempCurrentLoansObj.setFinalYearofPayment(foputstandingloansuserinputstb.get(i).getFinalYear());
		
			currentLoansListTempObj.add(i,tempCurrentLoansObj);
		}
		fpresponseset.setOutstandingLoans(currentLoansListTempObj);
		
		
		
		return fpresponseset;
		
	}// end of function
	
	/**************** FETCHING SAVED DATA FROM TEMP TB  ******************/
	/********************************************************************/
	
	@Transactional
	public FPResponseSet getFpResponseFromTempTb(String customerId)
	{
		FpTempMasterUserInputDetailsTb fpmasteruserinputdetailstb;
		List<FpTempLifeGoalsUserInputTb> fplifegoalsuserinputtb;
		List<FpTempFinancialAssetUserInputDetailsTb> fpfinancialassetuserinputdetailstb;
		List<FpTempInsuranceDetailsUserInputsTb> fpinsurancedetailsuserinputstb;
		List<FpTempOutstandingloansUserInputsTb> foputstandingloansuserinputstb;
		
		FPResponseSet fpresponseset = new FPResponseSet();
		
		//Fetching saved data from Database Tables
		fpmasteruserinputdetailstb = fpDao.getFpUserInputsFromTempTb(customerId);
		fplifegoalsuserinputtb = fpDao.getFpGoalsUserInputFromTempTb(customerId);
		fpfinancialassetuserinputdetailstb = fpDao.getFpFinAssetUserInputFromTempTb(customerId);
		fpinsurancedetailsuserinputstb = fpDao.getFpInsuranceUserInputFromTempTb(customerId);
		foputstandingloansuserinputstb = fpDao.getFpOutstandingLoansUserInputFromTempTb(customerId);
		
		//Assigning data from fpmasteruserinputdetailstb
		fpresponseset.setGender(fpmasteruserinputdetailstb.getGender());
		fpresponseset.setUserAge(fpmasteruserinputdetailstb.getSelfAge());
		fpresponseset.setRelationStatus(fpmasteruserinputdetailstb.getMarritalStatus());
		fpresponseset.setUserMonthlyTakehomeSalary(fpmasteruserinputdetailstb.getSelfMonthlyTakeHomeSalary());
		fpresponseset.setSpouseAge(fpmasteruserinputdetailstb.getSpouseAge());
		fpresponseset.setSpouseMonthlyTakehomeSalary(fpmasteruserinputdetailstb.getSpouseMonthlyTakeHomeSalary());
		fpresponseset.setSavingsRate(fpmasteruserinputdetailstb.getSavingsRate());
		float monthlySavings = (fpresponseset.getSavingsRate()/100)*(fpresponseset.getUserMonthlyTakehomeSalary()+fpresponseset.getSpouseMonthlyTakehomeSalary());
		fpresponseset.setMonthlySavings(monthlySavings);
		fpresponseset.setInitialTotalFinancialAsset(fpmasteruserinputdetailstb.getTotalCurrentFinancialAssets());
		fpresponseset.setRiskGroupId(fpmasteruserinputdetailstb.getExpectedRiskScoreId());
		
		//Assigning data from fplifegoalsuserinputtb
		List<LifeGoalsInput> lifeGoalsListTempObj = new ArrayList<LifeGoalsInput>();
		
		for(int i = 0; i < fplifegoalsuserinputtb.size(); i++)
		{
			LifeGoalsInput tempLifeGoalsObj = new LifeGoalsInput();
			
			tempLifeGoalsObj.setGoalDescription(fplifegoalsuserinputtb.get(i).getGoalDescription());
			tempLifeGoalsObj.setGoalDescriptionId(fplifegoalsuserinputtb.get(i).getGoalId());
			tempLifeGoalsObj.setYearofRealization(fplifegoalsuserinputtb.get(i).getYearOfRealization());
			tempLifeGoalsObj.setFrequency(fplifegoalsuserinputtb.get(i).getRecurringFrequencyId());
			tempLifeGoalsObj.setLoanYesNo(fplifegoalsuserinputtb.get(i).getWantLoanYesNo());
			tempLifeGoalsObj.setEstimatedAmount(fplifegoalsuserinputtb.get(i).getEstimatedAmountAsPerCurrentPrices());
			
			lifeGoalsListTempObj.add(i,tempLifeGoalsObj);	
		}
		
		fpresponseset.setLifeGoals(lifeGoalsListTempObj);
		
		
		//Assigning data from fpfinancialassetuserinputdetailstb
		List<FinancialAssetList> finAssetListTempObj = new ArrayList<FinancialAssetList>();
		
		for(int i = 0; i < fpfinancialassetuserinputdetailstb.size(); i++)
		{
			FinancialAssetList tempFinAssetObj = new FinancialAssetList();
			
			tempFinAssetObj.setAssetId(fpfinancialassetuserinputdetailstb.get(i).getFinancialAssetId());
			tempFinAssetObj.setAssetDescription(fpfinancialassetuserinputdetailstb.get(i).getFinancialAssetName());
			tempFinAssetObj.setAssetAmount(fpfinancialassetuserinputdetailstb.get(i).getAmount());
		
			finAssetListTempObj.add(i,tempFinAssetObj);
		}
		fpresponseset.setFinancialAssetList(finAssetListTempObj);
		
		
		//Assigning data from fpinsurancedetailsuserinputstb
		List<LifeInsuranceCover> lifeInsuranceListTempObj = new ArrayList<LifeInsuranceCover>();
		List<HealthInsuranceCover> healthInsuranceListTempObj = new ArrayList<HealthInsuranceCover>();
		int positionLife = 0, positionHealth = 0; 
		
		for(int i = 0; i < fpinsurancedetailsuserinputstb.size(); i++)
		{
			LifeInsuranceCover tempLifeInsuObj = new LifeInsuranceCover();
			HealthInsuranceCover tempHealthInsuObj = new HealthInsuranceCover();
			
			if(fpinsurancedetailsuserinputstb.get(i).getInsuranceType().equalsIgnoreCase ("Life"))
			{
				tempLifeInsuObj.setLifeInsuranceDescription(fpinsurancedetailsuserinputstb.get(i).getInsuranceName());
				tempLifeInsuObj.setInsuranceCover(fpinsurancedetailsuserinputstb.get(i).getInsuranceCover());
				tempLifeInsuObj.setAnnualPremium(fpinsurancedetailsuserinputstb.get(i).getAnnualPremium());
				tempLifeInsuObj.setFinalYearofPayment(fpinsurancedetailsuserinputstb.get(i).getFinalYearOfPayment());
				
				lifeInsuranceListTempObj.add(positionLife,tempLifeInsuObj);
				positionLife++;
			}
			
			else if(fpinsurancedetailsuserinputstb.get(i).getInsuranceType().equalsIgnoreCase ("Health"))
			{
				tempHealthInsuObj.setHealthInsuranceDescription(fpinsurancedetailsuserinputstb.get(i).getInsuranceName());
				tempHealthInsuObj.setInsuranceCover(fpinsurancedetailsuserinputstb.get(i).getInsuranceCover());
				tempHealthInsuObj.setAnnualPremium(fpinsurancedetailsuserinputstb.get(i).getAnnualPremium());
				
				healthInsuranceListTempObj.add(positionHealth,tempHealthInsuObj);
				positionHealth++;
			}
			
		}
		
		fpresponseset.setLifeInsuranceCover(lifeInsuranceListTempObj);
		fpresponseset.setHealthInsuranceCover(healthInsuranceListTempObj);
		
		//Assigning data from foputstandingloansuserinputstb
		List<CurrentOutstandingLoans> currentLoansListTempObj = new ArrayList<CurrentOutstandingLoans>();
		
		for(int i = 0; i < foputstandingloansuserinputstb.size(); i++)
		{
			CurrentOutstandingLoans tempCurrentLoansObj = new CurrentOutstandingLoans();
			
			tempCurrentLoansObj.setLoanDescription(foputstandingloansuserinputstb.get(i).getLoanDescription());
			tempCurrentLoansObj.setLoanAmount(foputstandingloansuserinputstb.get(i).getLoanAmount());
			tempCurrentLoansObj.setemi(foputstandingloansuserinputstb.get(i).getMonthlyEmiAmount());
			tempCurrentLoansObj.setFinalYearofPayment(foputstandingloansuserinputstb.get(i).getFinalYear());
		
			currentLoansListTempObj.add(i,tempCurrentLoansObj);
		}
		
		fpresponseset.setOutstandingLoans(currentLoansListTempObj);
		
		
		return fpresponseset;
	}
	
	@Transactional
	public FPResponseSet getFpDefaultResponse()
	{
		LOGGER.log(Level.INFO,"Suman code - Code enters getFpDefaultResponse");
		FpTempMasterUserInputDetailsTb fpmasteruserinputdetailstb;
		List<FpTempLifeGoalsUserInputTb> fplifegoalsuserinputtb;
		List<FpTempFinancialAssetUserInputDetailsTb> fpfinancialassetuserinputdetailstb;
		List<FpTempInsuranceDetailsUserInputsTb> fpinsurancedetailsuserinputstb;
		List<FpTempOutstandingloansUserInputsTb> foputstandingloansuserinputstb;
		
		Calendar current = Calendar.getInstance();
		
		FPResponseSet fpresponseset = new FPResponseSet();
		
		//Assigning data from fpmasteruserinputdetailstb
		fpresponseset.setGender("");
		fpresponseset.setUserAge(18);
		fpresponseset.setRelationStatus("");
		fpresponseset.setUserMonthlyTakehomeSalary(0);
		fpresponseset.setSpouseAge(18);
		fpresponseset.setSpouseMonthlyTakehomeSalary(0);
		fpresponseset.setSavingsRate(0);
		fpresponseset.setMonthlySavings(0);
		fpresponseset.setInitialTotalFinancialAsset(0);
		fpresponseset.setRiskGroupId(0);
		
		//Assigning data from fplifegoalsuserinputtb
		List<LifeGoalsInput> lifeGoalsListTempObj = new ArrayList<LifeGoalsInput>();
		
		LifeGoalsInput tempLifeGoalsObj = new LifeGoalsInput();
			
		tempLifeGoalsObj.setGoalDescription("Select");
		tempLifeGoalsObj.setGoalDescriptionId(-1);
		//tempLifeGoalsObj.setYearofRealization(current.get(Calendar.YEAR));
		tempLifeGoalsObj.setYearofRealization(0);
		tempLifeGoalsObj.setFrequency(-1);
		tempLifeGoalsObj.setLoanYesNo("No");
		tempLifeGoalsObj.setEstimatedAmount(0);
			
		lifeGoalsListTempObj.add(0,tempLifeGoalsObj);	
		
		
		fpresponseset.setLifeGoals(lifeGoalsListTempObj);
		
		
		//Assigning data from fpfinancialassetuserinputdetailstb
		List<FpFinancialAssetGrowthAssumptionTb> finassetgrowthtb = fpDao.getFinAssetGrowthRateFromTb();
		List<FinancialAssetList> finAssetListTempObj = new ArrayList<FinancialAssetList>();
		
		for(int i=0; i < finassetgrowthtb.size()-1; i++)
		{
			FinancialAssetList tempFinAssetObj = new FinancialAssetList();
			
			tempFinAssetObj.setAssetId(finassetgrowthtb.get(i).getFinancialAssetId());
			tempFinAssetObj.setAssetDescription(finassetgrowthtb.get(i).getFinancialAssetName());
			tempFinAssetObj.setAssetAmount(0);
			
			finAssetListTempObj.add(i,tempFinAssetObj);
		}
		
		fpresponseset.setFinancialAssetList(finAssetListTempObj);
		
		
		//Assigning data from fpinsurancedetailsuserinputstb
		List<LifeInsuranceCover> lifeInsuranceListTempObj = new ArrayList<LifeInsuranceCover>();
		List<HealthInsuranceCover> healthInsuranceListTempObj = new ArrayList<HealthInsuranceCover>();
		
		LifeInsuranceCover tempLifeInsuObj = new LifeInsuranceCover();
		HealthInsuranceCover tempHealthInsuObj = new HealthInsuranceCover();
			
		tempLifeInsuObj.setLifeInsuranceDescription("");
		tempLifeInsuObj.setInsuranceCover(0);
		tempLifeInsuObj.setAnnualPremium(0);
		//tempLifeInsuObj.setFinalYearofPayment(current.get(Calendar.YEAR));	
		tempLifeInsuObj.setFinalYearofPayment(0);	
		lifeInsuranceListTempObj.add(0,tempLifeInsuObj);
		
		
		tempHealthInsuObj.setHealthInsuranceDescription("");
		tempHealthInsuObj.setInsuranceCover(0);
		tempHealthInsuObj.setAnnualPremium(0);
		healthInsuranceListTempObj.add(0,tempHealthInsuObj);
			
		
		fpresponseset.setLifeInsuranceCover(lifeInsuranceListTempObj);
		fpresponseset.setHealthInsuranceCover(healthInsuranceListTempObj);
		
		//Assigning data from foputstandingloansuserinputstb
		List<CurrentOutstandingLoans> currentLoansListTempObj = new ArrayList<CurrentOutstandingLoans>();	
			
		for(int i = 0; i < 2; i++)
		{
			CurrentOutstandingLoans tempCurrentLoansObj = new CurrentOutstandingLoans();
			
			if(i == 0)
				tempCurrentLoansObj.setLoanDescription("House");
			else
				tempCurrentLoansObj.setLoanDescription("Vehicle");
			
			tempCurrentLoansObj.setLoanAmount(0);
			tempCurrentLoansObj.setemi(0);
			//tempCurrentLoansObj.setFinalYearofPayment(current.get(Calendar.YEAR));
			tempCurrentLoansObj.setFinalYearofPayment(0);
			
			currentLoansListTempObj.add(i,tempCurrentLoansObj);
		}
		
		fpresponseset.setOutstandingLoans(currentLoansListTempObj);
		
		return fpresponseset;
	}
	
	
	/*
	@Transactional
	public FpMasterAssumption getFpsavedAssumptionsFromTb(String customerId)
	{
		FpMiscAssumptionsTb fpmiscassumption;
		List<FpSalaryExpenseIncrementAssumptionsTb> fpsalexpincrementassumption;
		//List<FpFinancialAssetGrowthAssumptionTb> fpassetgrowthassumption;
		List<FpLoanRelatedAssumptionsTb> fploanassumption;
		
		FpMasterAssumption fpmasterassumption = new FpMasterAssumption();
		
		//Fetching assumptionId from fpmiscassumptiontb based on custTempRegId
		int assumptionId = fpDao.getAssumptionIdFromMiscAssumptionTb(customerId);
		
		//Fetching the Database Assumption Details From Tb
		fpmiscassumption = fpDao.getAssumptionsFromTb(assumptionId);
		fpsalexpincrementassumption = fpDao.getSalaryExpenseIncrementFromTb(assumptionId);
		//fpassetgrowthassumption = fpDao.getFinAssetGrowthRateFromTb();
		fploanassumption = fpDao.getLoanAssumptionsFromTb(assumptionId);
		
		//Assinging values to fpmasterassumption object
		fpmasterassumption.setFpmiscassumptions(fpmiscassumption);
		fpmasterassumption.setFpsalaryexpenseincrement(fpsalexpincrementassumption);
		//fpmasterassumption.setFpfinassetgrowthassumption(fpassetgrowthassumption);
		fpmasterassumption.setFploanassumptions(fploanassumption);
		
		return fpmasterassumption;
		
	}*/
	
	@Transactional
	public String getCustomerRegId(String customerId)
	{
		String result="";
		try
		{
		//Making DAO Call
	    result = fpDao.getCustomerRegId(customerId);   
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Error in  getCustomerRegId() in FpSaveAndFetchDataForFpBAOImpl",e);
		}
		return result;
	}


}
