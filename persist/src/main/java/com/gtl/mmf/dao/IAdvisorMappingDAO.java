/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IAdvisorMappingDAO {

    public Integer inviteNewInvestor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int inviteOldInvestor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int withdrawInvestor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int acceptInvestor(Integer relationId, short relationStatus);

    public int declineInvestorInvite(CustomerAdvisorMappingTb customerAdvisorMappingTb);

    public int submitContract(CustomerAdvisorMappingTb customerAdvisorMappingTb, Integer reviewFrequency);

    public Integer getInvestorAvailableStatus(Integer customerId, List<Integer> unavailableStatus);

    public int sentQuestionnaire(CustomerAdvisorMappingTb customerAdvisorMapping);
    
    public void updateNotificationStatus(Integer relationId);
    
    public void updateNotificationStatusForRebalance(Integer portfolioId);
}
