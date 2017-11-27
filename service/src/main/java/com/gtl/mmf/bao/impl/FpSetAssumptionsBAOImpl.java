package com.gtl.mmf.bao.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IFpSetAssumptionsBAO;
import com.gtl.mmf.dao.IFinancialPlannerDAO;
import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;

import org.springframework.beans.factory.annotation.Autowired;


public class FpSetAssumptionsBAOImpl implements IFpSetAssumptionsBAO{
	
	@Autowired
	private IFinancialPlannerDAO fpDao;
	
	
	public FpSetAssumptionsBAOImpl ()
	{
		// This is a default constructor
	}
	
	@Transactional
	public FpMiscAssumptionsTb assignDefaultFpMiscAssumptions()
	{
		FpMiscAssumptionsTb fpmiscassumptions;
		String customerId = "Default";
		fpmiscassumptions = fpDao.getMiscAssumptionsFromTb(customerId);
		
		return fpmiscassumptions;
	}
	
	@Transactional
	public List<FpFinancialAssetGrowthAssumptionTb> assignDefaultFpAssetGrowthAssumption()
	{
		List<FpFinancialAssetGrowthAssumptionTb> fpfinancialassetgrowthassumption;
		fpfinancialassetgrowthassumption = fpDao.getFinAssetGrowthRateFromTb();
		
		return fpfinancialassetgrowthassumption;
	}
	
	@Transactional
	public List<FpLoanRelatedAssumptionsTb> assignDefaultFpLoanAssumption()
	{
		List<FpLoanRelatedAssumptionsTb> fploanrelatedassumptions;
		String customerId = "Default";
		fploanrelatedassumptions = fpDao.getLoanAssumptionsFromTb(customerId);
		
		return fploanrelatedassumptions;
	}
	
	@Transactional
	public List<FpSalaryExpenseIncrementAssumptionsTb> assignDefaultFpSalaryExpenseIncrementAssumption()
	{
		List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrementassumptions;
		String customerId = "Default";
		fpsalaryexpenseincrementassumptions = fpDao.getSalaryExpenseIncrementFromTb(customerId);
		
		return fpsalaryexpenseincrementassumptions;
	}
	
	@Transactional
	public FpMiscAssumptionsTb assignSavedFpMiscAssumptions(String customerId)
	{
		FpMiscAssumptionsTb fpmiscassumptions = null;
		boolean status = fpDao.checkUserEntryExistsInFpMiscAssumptionTb(customerId);
		
		if(status == true)
		{
			fpmiscassumptions = assignDefaultFpMiscAssumptions();
		}
		
		else if(status == false)
		{
			fpmiscassumptions = fpDao.getMiscAssumptionsFromTb(customerId);
		}
		
		return fpmiscassumptions;
	}
	
	@Transactional
	public List<FpLoanRelatedAssumptionsTb> assignSavedFpLoanAssumption(String customerId)
	{
		List<FpLoanRelatedAssumptionsTb> fploanrelatedassumptions = null;
		boolean status = fpDao.checkUserEntryExistsInFpLoanAssumptionTb(customerId);
		
		if(status == true)
		{
			fploanrelatedassumptions = assignDefaultFpLoanAssumption();
		}
		
		else if(status == false)
		{
			fploanrelatedassumptions = fpDao.getLoanAssumptionsFromTb(customerId);
		}
		
		return fploanrelatedassumptions;
	}
	
	@Transactional
	public List<FpSalaryExpenseIncrementAssumptionsTb> assignSavedFpSalaryExpenseIncrementAssumption(String customerId)
	{
		List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrementassumptions = null;
		boolean status = fpDao.checkUserEntryExistsInFpSalaryExpenseIncrementTb(customerId);
		
		if(status == true)
		{
			fpsalaryexpenseincrementassumptions = assignDefaultFpSalaryExpenseIncrementAssumption();
		}
		
		else if(status == false)
		{
			fpsalaryexpenseincrementassumptions = fpDao.getSalaryExpenseIncrementFromTb(customerId);
		}
		
		return fpsalaryexpenseincrementassumptions;
	}

}
