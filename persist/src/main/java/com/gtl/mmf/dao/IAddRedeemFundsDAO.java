/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAddRedeemFundsDAO {

    public CustomerPortfolioTb getCustomerPortfolio(int customerId);

    public int updateCustomerPortfolioAllcation(float newCashAmount, int customerPortfolioId,Double amount);

    public int updateCustomerMappingAllcation(double allocatedFunds, int relationId);

    public int updateAvailableFund(double availableFund,double additttionalAmount, int customerId);

    public Integer logAddRedeemFundDetails(AddRedeemLogTb addRedeemLogTb);
    
     public List<Object[]> getlogAddRedeemFundDetails(int customerPortfolioId);

    public void updateCashFlowTb(CashFlowTb cashFlowTb);
}
