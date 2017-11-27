/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTransactionExecutionDetailsTb;
import com.gtl.mmf.entity.CustomerTransactionOrderDetailsTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import java.util.Date;


import java.util.List;

public interface IRebalancePortfolioDAO {

    public List<Object> getRebalancePortfolioPageDetails(CustomerPortfolioTb customerPortfolioTb);

    public void savetransactionBeforeExecute(List<CustomerTransactionOrderDetailsTb> customerTransactionOrderDetailsTbList);

    public void savetransactionAfterExecute(CustomerPortfolioTb customerPortfolioTb);

    public void reclaculteOrderDetails(List<CustomerTransactionExecutionDetailsTb> customerTransactionOrderDetailsTbList,
            CustomerPortfolioTb customerPortfolioTb);

    public void saveTransactionsEOD(CustomerPortfolioTb customerPortfolioTb,Date eodDate,String environment);
    
    public void  saveTransactionsExecutionDetailsTb(List<CustomerTransactionExecutionDetailsTb> customerTransactionExecutionDetailslist);
    
    public void saveTransactionBypassOMS(List<MmfDailyTxnSummaryTb>mmfDailyTxnSummaryTbList, JobScheduleTb jobScheduleTb);
    
    public void saveDailyClientBalanceTb(MmfDailyClientBalanceTb mmfDailyClientBalanceTb, JobScheduleTb jobScheduleTb);

    public void saveSellPortfolioDetails(AddRedeemLogTb addRedeemLogTb);

    public void saveCustomerBP(String omsLoginId, float bp);

    public void insertIntoCashTxnForTheDayTb(CustomerPortfolioTb customerPortfolioTb);

    public void saveBlockedCashDetails(Integer customerPortfolioId,Double cashFlow,Double blocked_count);
    
    public void updateBlockedCountForSecurity(Integer customerPortfolioId,CustomerTransactionOrderDetailsTb customerTransactionOrderDetailsTb);
    
    public Integer updateBlockedAmountForSecurity(Double security_units,Double security_price,String omsLoginId);
}