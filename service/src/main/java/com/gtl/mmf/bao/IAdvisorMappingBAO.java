/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;

/**
 *
 * @author 07958
 */
public interface IAdvisorMappingBAO {

    public String inviteInvestor(InvestorDetailsVO investorDetails, int advisorId, String advisorName) throws ClassNotFoundException;

    public void withdrawInvestor(InvestorDetailsVO investorDetails, int advisorId, String advisorName) throws ClassNotFoundException;

    public void withdrawAdvisor(AdvisorDetailsVO advisorDetails, int advisorId);

    public void declineInvestorInvite(InvestorDetailsVO investorDetails, int advisorId, String advisorName) throws ClassNotFoundException;

    public String acceptInvestor(InvestorDetailsVO investorDetails, int advisorId, String advisorName) throws ClassNotFoundException;

    public String submitContract(ContractDetailsVO contractDetailsVO, int reviewFrequency, InvestorDetailsVO investorDetails, String advisorName) throws ClassNotFoundException;

    public void sentQuestionnaire(InvestorDetailsVO investorDetails, String advisorName) throws ClassNotFoundException;

    public void updateNotificationStatus(Integer relationId);
    
    public void updateNotificationStatusForRebalance(Integer portfolioId);
    
}
