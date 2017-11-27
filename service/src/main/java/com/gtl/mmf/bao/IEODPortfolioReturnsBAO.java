/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.entity.CustomerPortfolioTb;
import java.util.Date;
import java.util.List;

public interface IEODPortfolioReturnsBAO {

    public List<Object> loadAllClientPortfolio() throws Exception;

    public void buildClientPortfolioReturnThread(List<CustomerPortfolioTb> customerPortfolioTbList, Date eodDate);

    public void calculteClientPortfolioReturns(CustomerPortfolioTb customerPortfolioTb, String EODdate);

    public void calculateRecommendedPortfolioReturns() throws Exception;

//    public void computeCustomerPFPerformance(ThreadPoolTaskExecutor taskExecutor);
    public void computeCustomerPFPerformance();

    public void updateCustomerPFPerformance(Integer customerPrtfolioId) throws Exception;

//    public void updateRecommededPFPerformance(ThreadPoolTaskExecutor taskExecutor);
    public void updateRecommededPFPerformance(Integer portfolioId) throws Exception;

    public void updateBenchmarkPerformance();

    public void computeRecmdPFPerformance();

    public List<Integer> getRelationsToCalculateMgmtFee();

    public List<Integer> getRelationsToCalculatePerfFee();

    public void updatePerformanceFeeOfRelation(Integer relationId);

    public void updateManagementFeeOfRelation(Integer relationId);

    public void calculteBenchmarkUnitValue(CustomerPortfolioTb customerPortfolioTb, Date eodDate);

    public void updateRecomendedCustomerPortfolio(CustomerPortfolioTb customerPortfolioTb);

    public void eodClientPortflioDailyOpeningClosingCalculation(CustomerPortfolioTb customerPortfolioTb, Date eodDate);

    public void updateCustomerPortfolioSecurities(CustomerPortfolioTb customerPortfolioTb);

    public void calculateReturnsForSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb);

}
