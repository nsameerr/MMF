/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioTb;
import java.util.List;
import java.util.Set;

public interface IInvestmentPolicyStmtDAO {

    public List<Object> getPagedetails(int customerId);

    public List<Object> getPortfolioAssignPageDetails(int relationId);

    public List<Object> customerIPSView(int relationId);

    public List<PortfolioDetailsTb> getPortfolioPieCharDetails(PortfolioTb portfolioTb);

    public void saveInvestmentPolicy(CustomerRiskProfileTb customerRiskProfileTb, CustomerAdvisorMappingTb customerAdvisorMappingTb);

    public void saveAssignPortfolio(CustomerPortfolioTb customerPortfolioTb, Boolean isportfolioChanged,Integer customerId);

    public void customerReviewIPS(CustomerPortfolioTb customerPortfolioTb);

    public void customerAcceptIPS(CustomerPortfolioTb customerPortfolioTb);

    public MasterPortfolioTypeTb getPortfolioByStyleAndSize(Integer portfolioStyleId, Integer portfolioSizeId);
    
    public boolean getCustomerDetails(Integer customer_id);
    
    public List<CustomerPortfolioSecuritiesTb> getPortfolioSecuritiesForCustomer(Integer CustomerPortfolioId);
    
    public Integer getCustomerPortfolioIdOfUser(Integer CustomerId,Integer advisorId);
    
    public void updateSecuritiesForCustomer(List<CustomerPortfolioSecuritiesTb> seuritylist);
    
    public CustomerPortfolioTb getOldPortfolioDetails(Integer CustomerId);
    
}
