
package com.gtl.mmf.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.gtl.mmf.entity.CustomerRiskScoreTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpMappingGoalsidToLoansidTb;
import com.gtl.mmf.entity.FpMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.entity.FpMappingGoalsidToLoansidTb;
import com.gtl.mmf.entity.FpLifeGoalsUserInputTb;
import com.gtl.mmf.entity.FpFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpOutstandingloansUserInputsTb;
import com.gtl.mmf.entity.FpTempFinancialAssetUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempInsuranceDetailsUserInputsTb;
import com.gtl.mmf.entity.FpTempLifeGoalsUserInputTb;
import com.gtl.mmf.entity.FpTempMasterUserInputDetailsTb;
import com.gtl.mmf.entity.FpTempOutstandingloansUserInputsTb;
import com.gtl.mmf.entity.TempInv;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;


public class FinancialPlannerDAOImpl implements IFinancialPlannerDAO{
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.dao.impl.fpdaoimpl");
	
	@Autowired
	private SessionFactory sessionFactory;
	HibernateTemplate template;

	/*
	public FpMiscAssumptionsTb getDefaultMiscAssumptionsFromTb(int assumptionId)
	{
		String hql = "FROM FpMiscAssumptionsTb Q WHERE Q.assumptionId =:assumptionId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("assumptionId", assumptionId);
		FpMiscAssumptionsTb Q = (FpMiscAssumptionsTb)query.list().get(0);
		return Q;
	}
	
	public List<FpSalaryExpenseIncrementAssumptionsTb> getDefaultSalaryExpenseIncrementFromTb(int assumptionId)
	{
		String hql = "FROM FpSalaryExpenseIncrementAssumptionsTb WHERE assumptionId =:assumptionId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("assumptionId", assumptionId);
		return query.list();
	}
	
	public List<FpLoanRelatedAssumptionsTb> getDefaultLoanAssumptionsFromTb(int assumptionId)
	{
		String hql = "FROM FpLoanRelatedAssumptionsTb WHERE assumptionId =:assumptionId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("assumptionId", assumptionId);
		return query.list();
	}
	*/
	
	/********************* Fetching Assumptions From Tb ******************************/
	/********************************************************************************/
	
	public FpMiscAssumptionsTb getMiscAssumptionsFromTb(String customerRegId)
	{
		String hql = "FROM FpMiscAssumptionsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		FpMiscAssumptionsTb Q = (FpMiscAssumptionsTb)query.list().get(0);
		return Q;
	}
	
	public List<FpLoanRelatedAssumptionsTb> getLoanAssumptionsFromTb(String customerRegId)
	{
		String hql = "FROM FpLoanRelatedAssumptionsTb WHERE customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		return query.list();
	}
	
	public List<FpSalaryExpenseIncrementAssumptionsTb> getSalaryExpenseIncrementFromTb(String customerRegId)
	{
		String hql = "FROM FpSalaryExpenseIncrementAssumptionsTb WHERE customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		return query.list();
	}
	
	public List<FpFinancialAssetGrowthAssumptionTb> getFinAssetGrowthRateFromTb()
	{
		String hql = "FROM FpFinancialAssetGrowthAssumptionTb" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		return query.list();
	}
	
	/*************************** Assumption Fetching Finishes ******************************/
	
	public int getGoalLoanMappingFromTb(int goalId)
	{
		//LOGGER.log(Level.INFO,"Suman code - Inside DAO  1 " + goalId);
		String hql = "SELECT loansId FROM FpMappingGoalsidToLoansidTb WHERE lifeGoalsId =:goalId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("goalId", goalId);
		int loanId = (Integer)query.list().get(0);
		return loanId;
		
	}
	
	public float getLoanDownPaymentPercentFromTb(int loanId)
	{
		String hql = "SELECT downPaymentPercent FROM FpLoanRelatedAssumptionsTb WHERE loanAssumptionId =:loanId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("loanId", loanId);
		float downpaymentPercent = (Float)query.list().get(0);
		return downpaymentPercent;
	}
	
	public int getLoanDurationFromTb(int loanId)
	{
		String hql = "SELECT loanDuration FROM FpLoanRelatedAssumptionsTb WHERE loanAssumptionId =:loanId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("loanId", loanId);
		int loanDuration = (Integer)query.list().get(0);
		return loanDuration;
	}
	
	public float getLoanInterestRateFromTb(int loanId)
	{
		String hql = "SELECT interestRate FROM FpLoanRelatedAssumptionsTb WHERE loanAssumptionId =:loanId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("loanId", loanId);
		float interestRate = (Float)query.list().get(0);
		return interestRate;
	}
	
	public String getFrequencyDescription(int frequencyId)
	{
		String hql = "SELECT frequencyDescription FROM FpFrequencyidMasterTb WHERE frequencyId =:frequencyId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("frequencyId", frequencyId);
		String frequencyDescription = (String)query.list().get(0);
		return frequencyDescription;
	}
	
	public float getMMFReturns(int riskProfileId)
	{
		String hql = "SELECT averageReturns FROM FpPortfolioReturnsMasterTb WHERE portfolioReturnsReferenceId =:riskProfileId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("riskProfileId", riskProfileId);
		float mmfReturns = (Float)query.list().get(0);
		return mmfReturns;
	}
	
	public float getStandardDeviation(int riskProfileId)
	{
		String hql = "SELECT standardDeviation FROM FpPortfolioReturnsMasterTb WHERE portfolioReturnsReferenceId =:riskProfileId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setInteger("riskProfileId", riskProfileId);
		float mmfStandardDeviation = (Float)query.list().get(0);
		return mmfStandardDeviation;
	}
	
	public String getIdFromTempInvTbUsingEmail(String emailId)
	{
		String hql = "SELECT registrationId FROM TempInv WHERE email =:emailId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("emailId", emailId);
		String idFromTempInvTb = (String)query.list().get(0);
		return idFromTempInvTb;
	}
	
	public String getNameFromTempInvTbUsingRegId(String customerRegId)
	{
		String hql = "SELECT firstname FROM TempInv WHERE registrationId =:customerRegId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		String name = (String)query.list().get(0);
		return name;
	}
	
	/******************  FINAL SAVE DATA TO FP TABLES   ************************/
	/*****************  EVENT ON "SAVE & SUBMIT" CLICK  ************************/
	/**************************************************************************/
	
	public void saveUserFpMasterDetailsToTb(String customerRegId, String gender, int selfAge, int selfMonthlyTakeHomeSalary, String marritalStatus, int spouseAge, int spouseMonthlyTakeHomeSalary, int totalMonthlySalary, int monthlySavings, float savingsRate, int totalCurrentFinancialAssets, int expectedRiskScoreId)
	{
		try
		{
		Date date = new Date();	
		FpMasterUserInputDetailsTb masteruserdetails = new FpMasterUserInputDetailsTb();
		masteruserdetails.setCustomerId(customerRegId);
		masteruserdetails.setDateTime(date);
		masteruserdetails.setSelfAge(selfAge);
		masteruserdetails.setGender(gender);
		masteruserdetails.setSelfMonthlyTakeHomeSalary(selfMonthlyTakeHomeSalary);
		masteruserdetails.setMarritalStatus(marritalStatus);
		masteruserdetails.setSpouseAge(spouseAge);
		masteruserdetails.setSpouseMonthlyTakeHomeSalary(spouseMonthlyTakeHomeSalary);
		masteruserdetails.setTotalMonthlySalary(totalMonthlySalary);
		masteruserdetails.setMonthlySavings(monthlySavings);
		masteruserdetails.setSavingsRate(savingsRate);
		masteruserdetails.setTotalCurrentFinancialAssets(totalCurrentFinancialAssets);
		masteruserdetails.setExpectedRiskScoreId(expectedRiskScoreId);
		sessionFactory.getCurrentSession().save(masteruserdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in saveUserFpMasterDetailsToTb", e);
		}
	}
	
	public void saveUserFpLifeGoalsDetailsToTb(String customerRegId, int goalID, String goalDesc, int yearofRealization, int frequencyId, String wantLoan, int estimatedAmount)
	{
		try
		{
		FpLifeGoalsUserInputTb lifegoalsdetails = new FpLifeGoalsUserInputTb();
		lifegoalsdetails.setCustomerId(customerRegId);
		lifegoalsdetails.setGoalId(goalID);
		lifegoalsdetails.setGoalDescription(goalDesc);
		lifegoalsdetails.setYearOfRealization(yearofRealization);
		lifegoalsdetails.setRecurringFrequencyId(frequencyId);
		lifegoalsdetails.setWantLoanYesNo(wantLoan);
		lifegoalsdetails.setEstimatedAmountAsPerCurrentPrices(estimatedAmount);
		sessionFactory.getCurrentSession().save(lifegoalsdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in saveUserFpLifeGoalsDetailsToTb", e);
		}
	}
	
	public void saveUserFpInsuranceDetailsToTb(String customerRegId, String insuranceDesc, int insuranceCover, int annualPremium, int finalYear, String loanType)
	{
		try
		{
		FpInsuranceDetailsUserInputsTb insurancedetails = new FpInsuranceDetailsUserInputsTb();
		insurancedetails.setCustomerId(customerRegId);
		insurancedetails.setInsuranceType(loanType);
		insurancedetails.setInsuranceName(insuranceDesc);
		insurancedetails.setInsuranceCover(insuranceCover);
		insurancedetails.setAnnualPremium(annualPremium);
		insurancedetails.setFinalYearOfPayment(finalYear);
		sessionFactory.getCurrentSession().save(insurancedetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in saveUserFpInsuranceDetailsToTb", e);
		}
	}
	
	public void saveUserFpOutstandindLoansDetailsToTb(String customerRegId, String loanDesc, int loanAmount, int monthlyEmi, int finalYear)
	{
		try
		{
		FpOutstandingloansUserInputsTb outstandingloansdetails = new FpOutstandingloansUserInputsTb();
		outstandingloansdetails.setCustomerId(customerRegId);
		outstandingloansdetails.setLoanDescription(loanDesc);
		outstandingloansdetails.setLoanAmount(loanAmount);
		outstandingloansdetails.setMonthlyEmiAmount(monthlyEmi);
		outstandingloansdetails.setFinalYear(finalYear);
		sessionFactory.getCurrentSession().save(outstandingloansdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in saveUserFpOutstandindLoansDetailsToTb", e);
		}
	}
	
	public void saveUserFpFinAssetDetailsToTb(String customerRegId, int assetId, String assetName, int amount)
	{
		try
		{
		FpFinancialAssetUserInputDetailsTb finassetdetails = new FpFinancialAssetUserInputDetailsTb();
		finassetdetails.setCustomerId(customerRegId);
		finassetdetails.setFinancialAssetId(assetId);
		finassetdetails.setFinancialAssetName(assetName);
		finassetdetails.setAmount(amount);
		sessionFactory.getCurrentSession().save(finassetdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in saveUserFpFinAssetDetailsToTb", e);
		}
	}
	
	
	/******************** REMOVING SAVED ASSUMPTIONS FROM TB ***************************/
	/**********************************************************************************/
	
	public void deleteUserFpSavedMiscAssumptionsFromTb(String customerRegId)
	{
		try
		{
			String hql = "DELETE FROM FpMiscAssumptionsTb Q WHERE Q.customerId =:customerRegId";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString("customerRegId", customerRegId);
			LOGGER.log(Level.INFO,"Suman code - Deleting in FpMiscAssumptionsTb for id  " + customerRegId);
			query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in deleteUserFpSavedMiscAssumptionsFromTb", e);
		
		}
	}
	
	
	public void deleteUserFpSavedLoanAssumptionsFromTb(String customerRegId)
	{
		try
		{
			String hql = "DELETE FROM FpLoanRelatedAssumptionsTb Q WHERE Q.customerId =:customerRegId";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString("customerRegId", customerRegId);
			LOGGER.log(Level.INFO,"Suman code - Deleting in FpLoanRelatedAssumptionsTb for id  " + customerRegId);
			query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in deleteUserFpSavedLoanAssumptionsFromTb", e);
		
		}
	}
	
	
	public void deleteUserFpSavedSalaryExpenseIncrementAssumptionsFromTb(String customerRegId)
	{
		try
		{
			String hql = "DELETE FROM FpSalaryExpenseIncrementAssumptionsTb Q WHERE Q.customerId =:customerRegId";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString("customerRegId", customerRegId);
			LOGGER.log(Level.INFO,"Suman code - Deleting in FpSalaryExpenseIncrementAssumptionsTb for id  " + customerRegId);
			query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in deleteUserFpSavedSalaryExpenseIncrementAssumptionsFromTb", e);
		
		}
	}

	/******************** REMOVING DATA FROM FINAL SAVE TB ***************************/
	/********************************************************************************/
	
	public void deleteUserFpMasterDetailsFromFinalSaveTb(String customerRegId)
	{
		try
		{
			String hql = "DELETE FROM FpMasterUserInputDetailsTb Q WHERE Q.customerId =:customerRegId";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString("customerRegId", customerRegId);
			LOGGER.log(Level.INFO,"Suman code - Deleting in FpMasterUserInputDetailsTb for id  " + customerRegId);
			query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in deleteUserFpMasterDetailsFromFinalSaveTb", e);
		
		}
	}
	
	public void deleteUserFpLifeGoalsDetailsFromFinalSaveTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpLifeGoalsUserInputTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpLifeGoalsUserInputTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpLifeGoalsDetailsFromFinalSaveTb", e);
		}
	}
	
	public void deleteUserFpInsuranceDetailsFromFinalSaveTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpInsuranceDetailsUserInputsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpInsuranceDetailsUserInputsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpInsuranceDetailsFromFinalSaveTb", e);
		}
	}
	
	public void deleteUserFpOutstandindLoansDetailsFromFinalSaveTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpOutstandingloansUserInputsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpOutstandingloansUserInputsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpOutstandindLoansDetailsFromFinalSaveTb", e);
		}
	}
	
	public void deleteUserFpFinAssetDetailsFromFinalSaveTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpFinancialAssetUserInputDetailsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpFinancialAssetUserInputDetailsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpFinAssetDetailsFromFinalSaveTb", e);
		}
	}
	
	
	/******************** REMOVING DATA FROM TEMP TB ***************************/
	/**************************************************************************/
	
	public void deleteUserFpMasterDetailsFromTempTb(String customerRegId)
	{
		try
		{
			String hql = "DELETE FROM FpTempMasterUserInputDetailsTb Q WHERE Q.customerId =:customerRegId";
			Query query = sessionFactory.getCurrentSession().createQuery(hql);
			query.setString("customerRegId", customerRegId);
			LOGGER.log(Level.INFO,"Suman code - Deleting in FpTempMasterUserInputDetailsTb for id  " + customerRegId);
			query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - catch error in deleteUserFpMasterDetailsFromTempTb", e);
		
		}
	
		
	}
	
	public void deleteUserFpLifeGoalsDetailsFromTempTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpTempLifeGoalsUserInputTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpTempLifeGoalsUserInputTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpLifeGoalsDetailsFromTempTb", e);
		}
	}
	
	public void deleteUserFpInsuranceDetailsFromTempTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpTempInsuranceDetailsUserInputsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpTempInsuranceDetailsUserInputsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpInsuranceDetailsFromTempTb", e);
		}
		
	}
	
	public void deleteUserFpOutstandindLoansDetailsFromTempTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpTempOutstandingloansUserInputsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpTempOutstandingloansUserInputsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpOutstandindLoansDetailsFromTempTb", e);
		}
		
	}
	
	public void deleteUserFpFinAssetDetailsFromTempTb(String customerRegId)
	{
		try
		{
		String hql = "DELETE FROM FpTempFinancialAssetUserInputDetailsTb Q WHERE Q.customerId =:customerRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("customerRegId", customerRegId);
		LOGGER.log(Level.INFO,"Suman code - Deleting in FpTempFinancialAssetUserInputDetailsTb for id  " + customerRegId);
		query.executeUpdate();
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in deleteUserFpFinAssetDetailsFromTempTb", e);
		}
	}
	
	
	/******************  SAVING DATA TO TEMP FP TABLES   ************************/
	/***************************************************************************/
	
	public void saveUserFpMasterDetailsToTempTb(String customerRegId, String gender, int selfAge, int selfMonthlyTakeHomeSalary, String marritalStatus, int spouseAge, int spouseMonthlyTakeHomeSalary, int totalMonthlySalary, int monthlySavings, float savingsRate, int totalCurrentFinancialAssets, int expectedRiskScoreId)
	{
		try
		{
		Date date = new Date();
		FpTempMasterUserInputDetailsTb masteruserdetails = new FpTempMasterUserInputDetailsTb();
		masteruserdetails.setCustomerId(customerRegId);
		masteruserdetails.setDateTime(date);
		masteruserdetails.setSelfAge(selfAge);
		masteruserdetails.setGender(gender);
		masteruserdetails.setSelfMonthlyTakeHomeSalary(selfMonthlyTakeHomeSalary);
		masteruserdetails.setMarritalStatus(marritalStatus);
		masteruserdetails.setSpouseAge(spouseAge);
		masteruserdetails.setSpouseMonthlyTakeHomeSalary(spouseMonthlyTakeHomeSalary);
		masteruserdetails.setTotalMonthlySalary(totalMonthlySalary);
		masteruserdetails.setMonthlySavings(monthlySavings);
		masteruserdetails.setSavingsRate(savingsRate);
		masteruserdetails.setTotalCurrentFinancialAssets(totalCurrentFinancialAssets);
		masteruserdetails.setExpectedRiskScoreId(expectedRiskScoreId);
		sessionFactory.getCurrentSession().save(masteruserdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveUserFpMasterDetailsToTempTb", e);
		}
		
	}
	
	public void saveUserFpLifeGoalsDetailsToTempTb(String customerRegId, int goalID, String goalDesc, int yearofRealization, int frequencyId, String wantLoan, int estimatedAmount)
	{		
		try
		{
		FpTempLifeGoalsUserInputTb lifegoalsdetails = new FpTempLifeGoalsUserInputTb();
		lifegoalsdetails.setCustomerId(customerRegId);
		lifegoalsdetails.setGoalId(goalID);
		lifegoalsdetails.setGoalDescription(goalDesc);
		lifegoalsdetails.setYearOfRealization(yearofRealization);
		lifegoalsdetails.setRecurringFrequencyId(frequencyId);
		lifegoalsdetails.setWantLoanYesNo(wantLoan);
		lifegoalsdetails.setEstimatedAmountAsPerCurrentPrices(estimatedAmount);
		sessionFactory.getCurrentSession().save(lifegoalsdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveUserFpLifeGoalsDetailsToTempTb", e);
		}
	}
	
	public void saveUserFpInsuranceDetailsToTempTb(String customerRegId, String insuranceDesc, int insuranceCover, int annualPremium, int finalYear, String loanType)
	{
		try
		{
		FpTempInsuranceDetailsUserInputsTb insurancedetails = new FpTempInsuranceDetailsUserInputsTb();
		insurancedetails.setCustomerId(customerRegId);
		insurancedetails.setInsuranceType(loanType);
		insurancedetails.setInsuranceName(insuranceDesc);
		insurancedetails.setInsuranceCover(insuranceCover);
		insurancedetails.setAnnualPremium(annualPremium);
		insurancedetails.setFinalYearOfPayment(finalYear);
		sessionFactory.getCurrentSession().save(insurancedetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveUserFpInsuranceDetailsToTempTb", e);
		}
	}
	
	public void saveUserFpOutstandindLoansDetailsToTempTb(String customerRegId, String loanDesc, int loanAmount, int monthlyEmi, int finalYear)
	{
		try
		{
		FpTempOutstandingloansUserInputsTb outstandingloansdetails = new FpTempOutstandingloansUserInputsTb();
		outstandingloansdetails.setCustomerId(customerRegId);
		outstandingloansdetails.setLoanDescription(loanDesc);
		outstandingloansdetails.setLoanAmount(loanAmount);
		outstandingloansdetails.setMonthlyEmiAmount(monthlyEmi);
		outstandingloansdetails.setFinalYear(finalYear);
		sessionFactory.getCurrentSession().save(outstandingloansdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveUserFpOutstandindLoansDetailsToTempTb", e);
		}
	}
	
	public void saveUserFpFinAssetDetailsToTempTb(String customerRegId, int assetId, String assetName, int amount)
	{
		try
		{
		FpTempFinancialAssetUserInputDetailsTb finassetdetails = new FpTempFinancialAssetUserInputDetailsTb();
		finassetdetails.setCustomerId(customerRegId);
		finassetdetails.setFinancialAssetId(assetId);
		finassetdetails.setFinancialAssetName(assetName);
		finassetdetails.setAmount(amount);
		sessionFactory.getCurrentSession().save(finassetdetails);
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveUserFpFinAssetDetailsToTempTb", e);
		}
	}
	
	/******************** SAVE TO TEMP FP FINISHES ****************************/
	
	public int getAssumptionIdFromMiscAssumptionTb(String customerRegId)
	{
		String hql = "SELECT assumptionId FROM FpMiscAssumptionsTb WHERE customerId =:custTempRegId " ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("custTempRegId", customerRegId);
		int assumptionId = (Integer)query.list().get(0);
		return assumptionId;
	}
	
	public int getMaxAssumptionIdFromMiscAssumptionTb()
	{
		String hql = "SELECT assumptionId FROM FpMiscAssumptionsTb ORDER BY assumptionId DESC" ;
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		int assumptionId = (Integer)query.list().get(0);
		return assumptionId;
	}
	
	public void saveFpMiscAssumptionsToDatabase(FpMiscAssumptionsTb fpmiscassumptions, String customerRegId, int flag)
	{
		LOGGER.log(Level.SEVERE,"Suman code - saveFpMiscAssumptionsToDatabase flag value", flag);
		
		int assumptionId;
		assumptionId = getMaxAssumptionIdFromMiscAssumptionTb();
		assumptionId++;
		
		try
		{
		FpMiscAssumptionsTb miscassumptions = new FpMiscAssumptionsTb();
		miscassumptions.setAssumptionId(assumptionId);
		miscassumptions.setCustomerId(customerRegId);
		miscassumptions.setRetirementAge(fpmiscassumptions.getRetirementAge());
		miscassumptions.setLifeExpectancy(fpmiscassumptions.getLifeExpectancy());
		miscassumptions.setAmountInvestedToMmf(fpmiscassumptions.getAmountInvestedToMmf());
		miscassumptions.setRateOfGrowthOfFd(fpmiscassumptions.getRateOfGrowthOfFd());
		miscassumptions.setLongTermInflationExpectation(fpmiscassumptions.getLongTermInflationExpectation());
		miscassumptions.setPostRetirementRecurringExpense(fpmiscassumptions.getPostRetirementRecurringExpense());
		sessionFactory.getCurrentSession().save(miscassumptions);
		}catch(Exception e){
		LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveFpMiscAssumptionsToDatabase", e);
		}
		
	}
	
	public void saveFpLoanAssumptionsToDatabase(List<FpLoanRelatedAssumptionsTb> fploanassumptions, String customerRegId)
	{
		int assumptionId = getAssumptionIdFromMiscAssumptionTb(customerRegId);
		
		try
		{
		for(int i = 0; i<fploanassumptions.size(); i++)
		{
			FpLoanRelatedAssumptionsTb loanassumptions = new FpLoanRelatedAssumptionsTb();
			loanassumptions.setAssumptionId(assumptionId);
			loanassumptions.setCustomerId(customerRegId);
			loanassumptions.setLoanAssumptionId(fploanassumptions.get(i).getLoanAssumptionId());
			loanassumptions.setLoanTypeDescription(fploanassumptions.get(i).getLoanTypeDescription());
			loanassumptions.setLoanDuration(fploanassumptions.get(i).getLoanDuration());
			loanassumptions.setInterestRate(fploanassumptions.get(i).getInterestRate());
			loanassumptions.setDownPaymentPercent(fploanassumptions.get(i).getDownPaymentPercent());
			sessionFactory.getCurrentSession().save(loanassumptions);
		}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveFpLoanAssumptionsToDatabase", e);
		}
		
	
	}// end of function
	
	public void saveFpSalaryExpenseIncrementAssumptionsToDatabase(List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrementassumption, String customerRegId)
	{
		int assumptionId = getAssumptionIdFromMiscAssumptionTb(customerRegId);
		
		try
		{
		for(int i = 0; i<fpsalaryexpenseincrementassumption.size(); i++)
		{
			FpSalaryExpenseIncrementAssumptionsTb salexpincrementcassumptions = new FpSalaryExpenseIncrementAssumptionsTb();
			salexpincrementcassumptions.setAssumptionId(assumptionId);
			salexpincrementcassumptions.setCustomerId(customerRegId);
			salexpincrementcassumptions.setLowerLimitAge(fpsalaryexpenseincrementassumption.get(i).getLowerLimitAge());
			salexpincrementcassumptions.setUpperLimitAge(fpsalaryexpenseincrementassumption.get(i).getUpperLimitAge());
			salexpincrementcassumptions.setSelfSalaryIncreaseRate(fpsalaryexpenseincrementassumption.get(i).getSelfSalaryIncreaseRate());
			salexpincrementcassumptions.setSpouseSalaryIncreaseRate(fpsalaryexpenseincrementassumption.get(i).getSpouseSalaryIncreaseRate());
			salexpincrementcassumptions.setExpenseIncreaseRate(fpsalaryexpenseincrementassumption.get(i).getExpenseIncreaseRate());
			sessionFactory.getCurrentSession().save(salexpincrementcassumptions);
		}
		}catch(Exception e){
			LOGGER.log(Level.SEVERE,"Suman code - Catch error in saveFpSalaryExpenseIncrementAssumptionsToDatabase", e);
		}
			
	}
	
	/******************* Fetch user input data from final save tb ********************/
	/*********************************************************************************/
	
	public FpMasterUserInputDetailsTb getFpUserInputsFromTb(String customerRegId)
	{
		String hql = "FROM FpMasterUserInputDetailsTb Q WHERE Q.customerId =:tempRegId ORDER BY Q.userDetialsId DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("tempRegId", customerRegId);
		FpMasterUserInputDetailsTb Q = (FpMasterUserInputDetailsTb)query.list().get(0);
		return Q;
	}
	
	public List<FpLifeGoalsUserInputTb> getFpGoalsUserInputFromTb(String customerRegId)
	{
		String hql = "FROM FpLifeGoalsUserInputTb WHERE customerId =:tempRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("tempRegId", customerRegId);
		return query.list();
	}
	
	public List<FpFinancialAssetUserInputDetailsTb> getFpFinAssetUserInputFromTb(String customerRegId)
	{
		String hql = "FROM FpFinancialAssetUserInputDetailsTb WHERE customerId =:tempRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("tempRegId", customerRegId);
		return query.list();
	}
	
	public List<FpInsuranceDetailsUserInputsTb> getFpInsuranceUserInputFromTb(String customerRegId)
	{
		String hql = "FROM FpInsuranceDetailsUserInputsTb WHERE customerId =:tempRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("tempRegId", customerRegId);
		return query.list();
	}
	
	public List<FpOutstandingloansUserInputsTb> getFpOutstandingLoansUserInputFromTb(String customerRegId)
	{
		String hql = "FROM FpOutstandingloansUserInputsTb WHERE customerId =:tempRegId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("tempRegId", customerRegId);
		return query.list();
	}
	
	/******************* Fetch user input data from temp tb ********************/
	/**************************************************************************/
	
	public FpTempMasterUserInputDetailsTb getFpUserInputsFromTempTb(String customerRegId)
	{
		String hql = "FROM FpTempMasterUserInputDetailsTb Q WHERE Q.customerId =:regId ORDER BY Q.id DESC";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		FpTempMasterUserInputDetailsTb Q = (FpTempMasterUserInputDetailsTb)query.list().get(0);
		return Q;
	}
	
	public List<FpTempLifeGoalsUserInputTb> getFpGoalsUserInputFromTempTb(String customerRegId)
	{
		String hql = "FROM FpTempLifeGoalsUserInputTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		return query.list();
	}
	
	public List<FpTempFinancialAssetUserInputDetailsTb> getFpFinAssetUserInputFromTempTb(String customerRegId)
	{
		String hql = "FROM FpTempFinancialAssetUserInputDetailsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		return query.list();
	}
	
	public List<FpTempInsuranceDetailsUserInputsTb> getFpInsuranceUserInputFromTempTb(String customerRegId)
	{
		String hql = "FROM FpTempInsuranceDetailsUserInputsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		return query.list();
	}
	
	public List<FpTempOutstandingloansUserInputsTb> getFpOutstandingLoansUserInputFromTempTb(String customerRegId)
	{
		String hql = "FROM FpTempOutstandingloansUserInputsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		return query.list();
	}
	
	/*************************** Fetch From Temp Tb Finishes *********************************/
	
	public boolean checkUserResponseExistsInTempTb(String customerRegId)
	{
		String hql = "FROM FpTempMasterUserInputDetailsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
		
	}
	
	public boolean checkUserResponseExistsInFinalSaveTb(String customerRegId)
	{
		String hql = "FROM FpMasterUserInputDetailsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
		
	}
	
	public boolean checkUserEntryExistsInMasterCustomerTb(String customerRegId)
	{
		String hql = "FROM MasterCustomerTb WHERE registrationId  =:regId";	//customerId
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
	}
	
	public boolean checkUserEntryExistsInFpMiscAssumptionTb(String customerRegId)
	{
		String hql = "FROM FpMiscAssumptionsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
	}
	
	public boolean checkUserEntryExistsInFpLoanAssumptionTb(String customerRegId)
	{
		String hql = "FROM FpLoanRelatedAssumptionsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
	}
	
	public boolean checkUserEntryExistsInFpSalaryExpenseIncrementTb(String customerRegId)
	{
		String hql = "FROM FpSalaryExpenseIncrementAssumptionsTb WHERE customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", customerRegId);
		boolean result = query.list().isEmpty();
		return result;
	}
	
	public String getCustomerRegId(String customerID) {
		int customerIdInt=Integer.parseInt(customerID);
        String HQL = "From TempInv where id=:customerID";
        Query query = sessionFactory.getCurrentSession().createQuery(HQL);
        query.setInteger("customerID", customerIdInt);
        TempInv tempInv= (TempInv)query.uniqueResult();
        return tempInv.getRegistrationId();		
	}

	public boolean getUserFpStatus(String regId) {
		String hql = "FROM FpMasterUserInputDetailsTb where customerId =:regId";
		Query query = sessionFactory.getCurrentSession().createQuery(hql);
		query.setString("regId", regId);
		return query.list().isEmpty();
	}

	public void updateFpUsingRp(FpMasterUserInputDetailsTb muid) {
		sessionFactory.getCurrentSession().saveOrUpdate(muid);
		
	}

}