/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerQuestionResponseSetTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisoryServiceContractDAO {

    public CustomerAdvisorMappingTb getContractDetails(Integer relationId);

    public void submitInvestorQuestionnair(List<CustomerQuestionResponseSetTb> customerQuestionResponseSetTbList);

    public int getTotalScoreOfQuestinnaire(int relationId);

    public void saveRiskProfile(CustomerRiskProfileTb customerRiskProfileTb, CustomerAdvisorMappingTb customerAdvisorMappingTb);
    
    public MasterPortfolioTypeTb getMasterPortfolioType(Integer Id);

    public MasterCustomerTb getInvestorFundDetails(Integer cusId);
}
