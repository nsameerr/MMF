/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IInvestorMappingBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.dao.IInvestorMappingDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.Promodefinitions;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.REVIEW_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class InvestorMappingBAOImpl implements IInvestorMappingBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.InvestorMappingBAOImpl");

    @Autowired
    private IInvestorMappingDAO investorMappingDAO;

    /**
     * Sending invitation to the advisor. Checks an invite is already sent or
     * not.
     *
     * @param advisorDetails -Advisor details to to send invitation
     * @param customerId
     * @param allocatedFunds
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void inviteAdvisor(AdvisorDetailsVO advisorDetails, int customerId,
            Double allocatedFunds, String investorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = advisorDetails.toCustomerAdvisorMappingTb();
        MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
        masterCustomerTb.setCustomerId(customerId);
        customerAdvisorMappingTb.setMasterCustomerTb(masterCustomerTb);
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.INVITE_SENT.getValue());
        customerAdvisorMappingTb.setAllocatedFunds(allocatedFunds);
        customerAdvisorMappingTb.setAdvisorRequest(false);
        customerAdvisorMappingTb.setAdvisorViewed(false);
        customerAdvisorMappingTb.setPromodefinitions(new Promodefinitions(ONE));
        //Updating Status date while inviting
        customerAdvisorMappingTb.setStatusDate(new Date());

        //Checking whather already an invitation is send or not
        if (advisorDetails.getRelationId() == null || advisorDetails.getRelationId() == 0) {
            investorMappingDAO.inviteNewAdvisor(customerAdvisorMappingTb);
        } else {
            investorMappingDAO.inviteOldAdvisor(customerAdvisorMappingTb);
        }
        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(false,
                        EnumCustomerMappingStatus.INVITE_SENT.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    /**
     * withdrawing Invitations send to the Advisor
     *
     * @param advisorDetails
     * @param customerId
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void withdrawAdvisor(AdvisorDetailsVO advisorDetails, int customerId,
            String investorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = advisorDetails.toCustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.WITHDRAW.getValue());
        customerAdvisorMappingTb.setAdvisorViewed(false);
        //Updating Status date while withdrawing
        customerAdvisorMappingTb.setStatusDate(new Date());
        investorMappingDAO.withdrawAdvisor(customerAdvisorMappingTb);
        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(false,
                        EnumCustomerMappingStatus.WITHDRAW.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    /**
     * Decline investor invitation by advisor
     *
     * @param advisorDetails
     * @param customerId
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void declineAdvisorInvite(AdvisorDetailsVO advisorDetails,
            int customerId, String investorName) throws ClassNotFoundException {
        CustomerAdvisorMappingTb customerAdvisorMappingTb = advisorDetails.toCustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.INVITE_DECLINED.getValue());

        //Updating Status date while inviting
        customerAdvisorMappingTb.setStatusDate(new Date());
        customerAdvisorMappingTb.setAdvisorViewed(false);
        investorMappingDAO.rejectAdvisor(customerAdvisorMappingTb);

        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(false,
                        EnumCustomerMappingStatus.INVITE_DECLINED.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    /**
     * Accepting invitation request
     *
     * @param advisorDetails
     * @param customerId
     * @param allocatedFunds
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void acceptAdvisor(AdvisorDetailsVO advisorDetails, int customerId,
            Double allocatedFunds, String investorName) throws ClassNotFoundException {
        investorMappingDAO.acceptAdvisor(advisorDetails.getRelationId(),
                (short) EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue(), allocatedFunds);
        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(true,
                        EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    /**
     * Method for setting contract review status
     *
     * @param contractDetailsVO
     * @param advisorDetails
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void contractReview(ContractDetailsVO contractDetailsVO,
            AdvisorDetailsVO advisorDetails, String investorName) throws ClassNotFoundException {
        short reviewFrequency = (short) (contractDetailsVO.getReviewFrequency()
                == null ? ONE : contractDetailsVO.getReviewFrequency() + ONE);
        String investorReview = contractDetailsVO.getInvestorReview();
        if (reviewFrequency > ONE) {
            StringBuilder prevReview = new StringBuilder(EMPTY_STRING);
            for (String reviewItem : contractDetailsVO.getPreviousReviews()) {
                prevReview.append(reviewItem).append(REVIEW_SEPERATOR);
            }
            prevReview.append(investorReview);
            investorReview = prevReview.toString();
        }
        investorMappingDAO.contractReview(contractDetailsVO.getRelationId(),
                EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue(),
                investorReview, contractDetailsVO.getAllocatedFunds(), reviewFrequency);
        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(false,
                        EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    /**
     * Method to accept contract
     *
     * @param contractDetailsVO
     * @param advisorDetails
     * @param investorName
     * @throws ClassNotFoundException
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void contractAccept(ContractDetailsVO contractDetailsVO,
            AdvisorDetailsVO advisorDetails, String investorName) throws ClassNotFoundException {
        investorMappingDAO.contractAccept(contractDetailsVO.getRelationId(),
                EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue());
        Double investorTotalFunds = investorMappingDAO.getInvestorTotalFunds(contractDetailsVO.getCustomerId());
        Double allocatedFunds = contractDetailsVO.getAllocatedFunds();
        Double totalAvailableFund = investorTotalFunds - allocatedFunds;
        investorMappingDAO.updateInvestorFundDetails(contractDetailsVO.getCustomerId(), allocatedFunds, totalAvailableFund);
        String notificationMsg = InvestorNotificationMsgService
                .getAdvNotificationMessage(false, EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue(), investorName);
        sendMail(advisorDetails.getFirstName(), advisorDetails.getEmail(), notificationMsg);
    }

    private void sendMail(String firstName, String email, String message) throws ClassNotFoundException {
        MailUtil.getInstance().sendNotificationMail(firstName, email, message);
    }

    /**
     * Updating notification status to remove notification from pop-up after
     * user clicks on the notification
     *
     * @param relationId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateNotificationStatus(Integer relationId) {
        investorMappingDAO.updateNotificationStatus(relationId);
    }

    /**
     * Updating re-balance notification status to remove notification from
     * pop-up after user clicks on the notification
     *
     * @param relationId
     * @param advisorid
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateNotificationStatusRebalance(Integer relationId, Integer advisorid) {
        investorMappingDAO.updateNotificationStatusRebalance(relationId, advisorid);
    }

    /**
     * Updating rate advisor notification status to remove notification from
     * pop-up after user clicks on the notification
     *
     * @param relationId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateNotificationStatusRateAdvisor(Integer relationId) {
        investorMappingDAO.updateNotificationStatusRateAdvisor(relationId);
    }

    /**
     * This method is for update contract details after investor terminate
     * contract contract status,actual contract end date and allocated amount of
     * investor
     *
     * After Terminating contract investor will be a free user he/she can sign
     * contract with other or same advisor
     *
     * @param relationId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean contractTerminate(Integer relationId, String email, String investorName) {
        boolean isContract = false;
        if (relationId != null) {
            Integer noOfRowsUpdated = investorMappingDAO.updateContratDetails(relationId);
            if (noOfRowsUpdated != null || noOfRowsUpdated != ZERO) {
                System.out.println("Status of user" + relationId + "updated");
                isContract = true;
                List<Object[]> advisorDetails = investorMappingDAO.advisorDetails(relationId);
                String advisorName = (String) advisorDetails.get(0)[0] == null ? EMPTY_STRING : (String) advisorDetails.get(0)[0];
                String advisor_email = (String) advisorDetails.get(0)[0] == null ? EMPTY_STRING : (String) advisorDetails.get(0)[1];
                String notificationMsgForAdvisor = InvestorNotificationMsgService.getAdvNotificationMessage(false,
                        EnumAdvisorMappingStatus.CONTRACT_TERMINATED.getValue(),investorName);
                String notificationMsgforInvestor = InvestorNotificationMsgService.getMessage(true,
                        EnumAdvisorMappingStatus.CONTRACT_TERMINATED.getValue(),advisorName);
                try {
                    sendMail(advisorName, advisor_email, notificationMsgForAdvisor);
                    sendMail(investorName,email,notificationMsgforInvestor);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(InvestorMappingBAOImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                System.out.println("No such relation id");
                isContract = false;
            }
        } else {
            System.out.println("No such relation id");
            isContract = false;
        }//String advisorName
        return isContract;
    }
}


