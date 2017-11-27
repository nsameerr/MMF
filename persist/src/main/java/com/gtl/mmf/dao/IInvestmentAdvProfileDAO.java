/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

/**
 *
 * @author 07958
 */
public interface IInvestmentAdvProfileDAO {

    public Double getInvestorDetails(Integer customerId);

    public Object[] getAdvisorRelationStatus(Integer relationId);
    
    public Object[] getContractTerminationStatus(Integer relationId);
    
    public Float getAllocatedFundForContractterminateduser(Integer customerId);
}
