/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;

/**
 *
 * @author 07958
 */
public interface IInvestorMappingBAO {

    public void inviteAdvisor(AdvisorDetailsVO advisorDetails, int customerId, Double allocatedFunds, String investorName) throws ClassNotFoundException;

    public void withdrawAdvisor(AdvisorDetailsVO advisorDetails, int customerId, String investorName) throws ClassNotFoundException;

    public void declineAdvisorInvite(AdvisorDetailsVO advisorDetails, int customerId, String investorName) throws ClassNotFoundException;

    public void acceptAdvisor(AdvisorDetailsVO advisorDetails, int customerId, Double allocatedFunds, String investorName) throws ClassNotFoundException;

    public void contractReview(ContractDetailsVO contractDetailsVO, AdvisorDetailsVO advisorDetails, String investorName) throws ClassNotFoundException;

    public void contractAccept(ContractDetailsVO contractDetailsVO, AdvisorDetailsVO advisorDetails, String investorName) throws ClassNotFoundException;

    public void updateNotificationStatus(Integer relationId);
    
    public void updateNotificationStatusRebalance(Integer relationId,Integer advisorid);
    
    public void updateNotificationStatusRateAdvisor(Integer relationId);
    
    public boolean contractTerminate(Integer relationId,String email,String investorName);
}
