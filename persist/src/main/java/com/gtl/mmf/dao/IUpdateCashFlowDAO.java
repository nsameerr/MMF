/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;

/**
 *
 * @author 09860
 */
public interface IUpdateCashFlowDAO {

    public List<Object[]> loadDailyCashFlow(Date eod);

    public void recalculateAllocatedFund(CashFlowTb cashFlowTb,String environmentType);

    public List<Object> getAllCustomersHavingRelation();

    public Object[] calculateInvestorManagementFee(CustomerAdvisorMappingTb mapping, Date ecsPaydate, boolean quarter,CustomerPortfolioTb customerTb);

    public Object[] calculateInvestorPerformanceFee(CustomerAdvisorMappingTb customer);

    List<Object> createUserListforFailedDebtPay(Integer relationID);

    public CustomerPortfolioTb getCustomerPortfolio(int relationId);
    

}
