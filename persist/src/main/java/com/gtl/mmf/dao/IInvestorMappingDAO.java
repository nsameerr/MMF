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
public interface IInvestorMappingDAO {

    public Integer inviteNewAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int inviteOldAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int withdrawAdvisor(CustomerAdvisorMappingTb customerAdvisorMapping);

    public int rejectAdvisor(CustomerAdvisorMappingTb customerAdvisorMappingTb);

    public int acceptAdvisor(Integer relationId, short relationStatus, Double allocatedFunds);

    public int contractReview(Integer relationId, Integer relationStatus, String review, Double allocatedFund, Short reviewFrequency);

    public int contractAccept(Integer relationId, Integer relationStatus);

    public Double getInvestorTotalFunds(int customerId);

    public int updateInvestorFundDetails(int customerId, Double allocatedFund, Double totalAvaialable);
    
    public void updateNotificationStatus(Integer relationId);
    
    public void updateNotificationStatusRebalance(Integer relationId,Integer advisorid);
    
    public void updateNotificationStatusRateAdvisor(Integer relationId);
    
    public Integer updateContratDetails(int relationId);
    
    public List<Object[]> advisorDetails(int relationId);
}
