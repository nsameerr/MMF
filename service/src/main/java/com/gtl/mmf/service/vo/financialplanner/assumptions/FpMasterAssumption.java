package com.gtl.mmf.service.vo.financialplanner.assumptions;

import java.util.List;

import com.gtl.mmf.entity.FpMiscAssumptionsTb;
import com.gtl.mmf.entity.FpFinancialAssetGrowthAssumptionTb;
import com.gtl.mmf.entity.FpLoanRelatedAssumptionsTb;
import com.gtl.mmf.entity.FpSalaryExpenseIncrementAssumptionsTb;

public class FpMasterAssumption {
	
	private FpMiscAssumptionsTb fpmiscassumptions;
	//private List<FpFinancialAssetGrowthAssumptionTb> fpfinassetgrowthassumption;
	private List<FpLoanRelatedAssumptionsTb> fploanassumptions;
	private List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrement;
	
	public FpMasterAssumption(){
		// This is a default constructor
	}
	
	public FpMiscAssumptionsTb getFpmiscassumptions() {
		return fpmiscassumptions;
	}
	
	public void setFpmiscassumptions(FpMiscAssumptionsTb fpmiscassumptions) {
		this.fpmiscassumptions = fpmiscassumptions;
	}
	
	/*
	public List<FpFinancialAssetGrowthAssumptionTb> getFpfinassetgrowthassumption() {
		return fpfinassetgrowthassumption;
	}
	
	public void setFpfinassetgrowthassumption(
			List<FpFinancialAssetGrowthAssumptionTb> fpfinassetgrowthassumption) {
		this.fpfinassetgrowthassumption = fpfinassetgrowthassumption;
	} */
	
	public List<FpLoanRelatedAssumptionsTb> getFploanassumptions() {
		return fploanassumptions;
	}
	
	public void setFploanassumptions(
			List<FpLoanRelatedAssumptionsTb> fploanassumptions) {
		this.fploanassumptions = fploanassumptions;
	}
	
	public List<FpSalaryExpenseIncrementAssumptionsTb> getFpsalaryexpenseincrement() {
		return fpsalaryexpenseincrement;
	}
	
	public void setFpsalaryexpenseincrement(
			List<FpSalaryExpenseIncrementAssumptionsTb> fpsalaryexpenseincrement) {
		this.fpsalaryexpenseincrement = fpsalaryexpenseincrement;
	}

	
}