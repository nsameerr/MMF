/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTwrPortfolioReturnTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface IEODPortfolioReturnsDAO {

    public List<Object> getAllClientPortfolioDetails();

    public Date getEODDate();

    public void calculateClientPortfolioReturns(CustomerPortfolioTb CustomerPortfolioTb, String EODdate);

    public void calculateREcommendedPortfolioRerturns(String EOD_Date, String currentDate);

    public List<Integer> getAllCustomerPortfolio();

    public Integer updateCustomerPFPerformance(int portfolioId) throws Exception;

    public List<Integer> getRecommededPortfolios();

    public Integer updateRecmdPFPerformance(int portfolioId) throws Exception;

    public Integer updateBenchmarkPerformance() throws Exception;

    public List<Integer> getRelationsToCalculateMgmtFee(Date currentDate);

    public List<Integer> getRelationsToCalculatePerfFee(Date currentDate);

    public Object[] updateManagementFeeOfRelation(Integer relationId, Date currentDate, Date ecsPayDate);

    public Object[] updatePerformanceFeeOfRelation(Integer relationId, Date currentDate, Date ecsPayDate);

    List<Object[]> getAddOrReedemStatusForCustomer(int portfolioId, Date currentDate);

    public Integer updateBenchmarkUnit(CustomerPortfolioTb customerPortfolioTb);

    public void upadetRecomendedCustomerPortfolio(List<RecomendedCustomerPortfolioSecuritiesTb> portfolioSecuritiesTb, boolean cashFlow, int customerPortfolioId);

    public Integer eodClientPortflioDailyOpeningClosingCalculation(int Customer_PortfolioId, Date currentDate);

    public BigDecimal getTransactionAmount(int Customer_Id, Date add_reedem_date);

    public Integer updateReedemAmount(int Customer_Id, Date add_reedem_date, BigDecimal amount);

    public String getVenueName(int Customer_Id);

    List<Object> getRecomendedPortfolioSecuritiesTB(CustomerPortfolioTb customerPortfolioTb);

    List<CustomerPortfolioSecuritiesTb> getCustomerPortfolioSecuritiesTB(int portfolioId);

    public void upadetCustomerPortfolioSecurites(List<CustomerPortfolioSecuritiesTb> securityListCustomer);

    public List<CashFlowTb> getPayInOut(String omsLoginId,Date eodDate);
    
    public List<Object> getReurnsforSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb,int asset,Integer noOfdays, String EOD_Date);

    public List<CustomerTwrPortfolioReturnTb> getCustomerPortfolioTwrReturnTB(Integer customerPortfolioId);

    public void updateCustomerTwrPortfolioReturnTbsList(List<CustomerTwrPortfolioReturnTb> customerTwrPortfolioReturnTbsList);
}
