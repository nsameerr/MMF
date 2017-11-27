/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IAdvisorMappingBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IAdvisorMappingDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.MasterAdvisorTb;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;

/**
 *
 * @author 07958
 */
public class AdvisorMappingBAOImpl implements IAdvisorMappingBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.AdvisorMappingBAOImpl");

    @Autowired
    private IAdvisorMappingDAO advisorMappingDAO;

  /**
   * Sending invitation to the investor. Checks an invite is already sent or not. 
   * @param investorDetails - Details of investor to send invitation
   * @param advisorId - ID of advisor logged in.
   * @param advisorName - Advisor name
   * @return A String represents the invite is success or the investor is not available
   * @throws ClassNotFoundException 
   */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String inviteInvestor(InvestorDetailsVO investorDetails, int advisorId,
            String advisorName) throws ClassNotFoundException {
        String responseMessage;
        
        //Checking Whether an invite is already send to the investor
        if (isInvestorAvailable(investorDetails.getCustomerId())) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = investorDetails.toCustomerAdvisorMappingTb();
            MasterAdvisorTb masterAdvisorTb = new MasterAdvisorTb();
            masterAdvisorTb.setAdvisorId(advisorId);
            customerAdvisorMappingTb.setMasterAdvisorTb(masterAdvisorTb);
            customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.INVITE_SENT.getValue());

            customerAdvisorMappingTb.setAdvisorRequest(true);

            //Variable is used to notify that notification is not viewd by customer 
            customerAdvisorMappingTb.setInvestorViewed(false);

            //updating status date 
            customerAdvisorMappingTb.setStatusDate(new Date());
            customerAdvisorMappingTb.setContractTerminateStatus(false);
            if (investorDetails.getRelationId() == ZERO) {

                //Inviting new investor
                advisorMappingDAO.inviteNewInvestor(customerAdvisorMappingTb);
            } else {

                //Inviting old invester by updating his status to INVITE_SENT
                advisorMappingDAO.inviteOldInvestor(customerAdvisorMappingTb);
            }
            responseMessage = SUCCESS;
        } else {
            responseMessage = INVESTOR_UNAVAILABLE;
        }
        String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                EnumAdvisorMappingStatus.INVITE_SENT.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
        return responseMessage;
    }

   /**
    * withdrawing Invitations send to the  Investor
    * @param investorDetails Details of investor to whom invitation is send
    * @param advisorId ID of advisor who send the invitation
    * @param advisorName name of advisor
    * @throws ClassNotFoundException 
    */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void withdrawInvestor(InvestorDetailsVO investorDetails, int advisorId,
            String advisorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = investorDetails.toCustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.NO_RELATION.getValue());

        //Variable is used to notify that notification is not viewd by customer 
        customerAdvisorMappingTb.setInvestorViewed(false);

        //updating status date 
        customerAdvisorMappingTb.setStatusDate(new Date());
        advisorMappingDAO.withdrawInvestor(customerAdvisorMappingTb);
        String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                EnumAdvisorMappingStatus.INVESTOR_WITHDRAW.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
    }

    /**
     * Advisor withdrawing invitation
     * @param advisorDetails 
     * @param advisorId 
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void withdrawAdvisor(AdvisorDetailsVO advisorDetails, int advisorId) {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = advisorDetails.toCustomerAdvisorMappingTb();

        //Variable is used to notify that notification is not viewd by customer 
        customerAdvisorMappingTb.setAdvisorViewed(false);

        //updating status date 
        customerAdvisorMappingTb.setStatusDate(new Date());

        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.NO_RELATION.getValue());
        advisorMappingDAO.withdrawInvestor(customerAdvisorMappingTb);
    }

    /**
     * Decline investor invitation by advisor
     * @param investorDetails
     * @param advisorId
     * @param advisorName
     * @throws ClassNotFoundException 
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void declineInvestorInvite(InvestorDetailsVO investorDetails,
            int advisorId, String advisorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = investorDetails.toCustomerAdvisorMappingTb();

        //Variable is used to notify that notification is not viewd by customer 
        customerAdvisorMappingTb.setInvestorViewed(false);

        //updating status date 
        customerAdvisorMappingTb.setStatusDate(new Date());
        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.INVITE_DECLINED.getValue());
        advisorMappingDAO.declineInvestorInvite(customerAdvisorMappingTb);
        String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                EnumAdvisorMappingStatus.INVITE_DECLINED.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String acceptInvestor(InvestorDetailsVO investorDetails, int advisorId,
            String advisorName) throws ClassNotFoundException {
        String responseMessage;
        
         //Checking Whether an invite is already send to the investor or investor is already in a relation with advisor
        if (isInvestorAvailable(investorDetails.getCustomerId())) {
            advisorMappingDAO.acceptInvestor(investorDetails.getRelationId(),
                    (short) EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue());
            responseMessage = SUCCESS;
        } else {
            responseMessage = INVESTOR_UNAVAILABLE;
        }
        String notificationMsg = InvestorNotificationMsgService.getMessage(false,
                EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
        return responseMessage;
    }
    
   /**
    * Submitting Contract to investor by advisor after accepting invitation
    * @param contractDetailsVO - Contract information
    * @param reviewFrequency - 
    * @param investorDetails - details of investor to send contract
    * @param advisorName
    * @return a string representing invitation is success or investor_unavailable
    * @throws ClassNotFoundException 
    */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public String submitContract(ContractDetailsVO contractDetailsVO,
            int reviewFrequency, InvestorDetailsVO investorDetails,
            String advisorName) throws ClassNotFoundException {
        String responseMessage;

      //Checking Whether an invite is already send to the investor or investor is already in a relation with advisor
        if (isInvestorAvailable(contractDetailsVO.getCustomerId())) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = contractDetailsVO.toCustomerAdvisorMappingTb();
            customerAdvisorMappingTb.setInvestorViewed(false);
            advisorMappingDAO.submitContract(customerAdvisorMappingTb, reviewFrequency);
            responseMessage = SUCCESS;
        } else {
            responseMessage = INVESTOR_UNAVAILABLE;
        }
        String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                EnumAdvisorMappingStatus.CONTRACT_SENT.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
        return responseMessage;
    }

    /**
     * Checking Whether an invite is already send to the investor or investor is already in a relation with advisor
     * @param customerId investor ID to check 
     * @return boolean true if there is no relation exist for customer with any advisor
     */
    private boolean isInvestorAvailable(Integer customerId) {
        Integer relationId = advisorMappingDAO.getInvestorAvailableStatus(customerId,
                getStatusBeforeSignContract());
        return relationId == null;
    }
    
    /**
     * investor status that need to check when submitting contract.
        if investor is in any of these status contract will not send
     * @return List of Investor Status to check when sending Contract.
     */
    private List<Integer> getStatusBeforeSignContract() {
        List<Integer> statusesAvoided = new ArrayList<Integer>();
        statusesAvoided.add(EnumCustomerMappingStatus.NO_RELATION.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_SENT.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_RECIEVED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_RECIEVED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.INVITE_DECLINED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.ADVISOR_WITHDRAW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.WITHDRAW.getValue());
        statusesAvoided.add(EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue());
        return statusesAvoided;
    }
    
   /**
    * Sending Risk profile Questionnaire to customer once contract is signed by the investor
    * @param investorDetails
    * @param advisorName
    * @throws ClassNotFoundException 
    */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void sentQuestionnaire(InvestorDetailsVO investorDetails,
            String advisorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = investorDetails.toCustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationStatus((short) EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT.getValue());

        //Variable is used to notify that notification is not viewd by customer 
        customerAdvisorMappingTb.setInvestorViewed(false);

        //updating status date while submitting questionnaire
        customerAdvisorMappingTb.setStatusDate(new Date());
        advisorMappingDAO.sentQuestionnaire(customerAdvisorMappingTb);
        String notificationMsg = InvestorNotificationMsgService.getMessage(true,
                EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT.getValue(), advisorName);
        sendMail(investorDetails.getFirstName(), investorDetails.getEmail(), notificationMsg);
    }

    private void sendMail(String firstName, String email, String message) throws ClassNotFoundException {
        MailUtil.getInstance().sendNotificationMail(firstName, email, message);
    }

  /**
   * To update notification status after  advisor viewed the notification by setting 
     advisorViewed field
   * @param relationId 
   */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateNotificationStatus(Integer relationId) {
        advisorMappingDAO.updateNotificationStatus(relationId);
    }
    
  /**
   *  To update Rebalance notification status after  advisor/investor viewed the notification by setting
     rebalanceViewed field
   * @param portfolioId 
   */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateNotificationStatusForRebalance(Integer portfolioId) {
        advisorMappingDAO.updateNotificationStatusForRebalance(portfolioId);
    }

}
