/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.linkedin.util.INotificationMessages;
import static com.gtl.linkedin.util.INotificationMessages.ADVISOR_INVITE_ACCEPTED;
import com.gtl.linkedin.util.NotificationsLoader;
import com.gtl.mmf.common.EnumAdminNotificationStatus;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;

/**
 *
 * @author 07958
 */
public class InvestorNotificationMsgService implements INotificationMessages {

    public static InvestorNotificationMsgService notificationMessageService = null;

    private InvestorNotificationMsgService() {
    }

    public static String getMessage(boolean advisorRequest, int status, String advisorName) {
        String notificationMessage = null;
        String nameProperCase = StringCaseConverter.toProperCase(advisorName);
        if (EnumCustomerMappingStatus.INVITE_RECIEVED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_INVITE_RECIEVED), nameProperCase);
        } else if (EnumCustomerMappingStatus.INVITE_ACCEPTED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(
                    advisorRequest ? INVESTOR_INVESTOR_ACCEPTED : INVESTOR_ADVISOR_ACCEPTED), nameProperCase);
        } else if (EnumCustomerMappingStatus.WITHDRAW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_INVITE_WITHDRAW), nameProperCase);
        } else if (EnumCustomerMappingStatus.INVITE_DECLINED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_INVITE_DECLINED), nameProperCase);
        } else if (EnumCustomerMappingStatus.CONTRACT_RECIEVED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_CONTRACT_RECIEVED), nameProperCase);
        } else if (EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_CONTRACT_REVIEW), nameProperCase);
        } else if (EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_CONTRACT_SIGNED), nameProperCase);
        } else if (EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_QUESTIONNAIRE_RECIEVED), nameProperCase);
        } else if (EnumCustomerMappingStatus.RESPONSE_SENT.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_QUESTIONNAIRE_SUBMITTED), nameProperCase);
        } else if (EnumCustomerMappingStatus.RESPONSE_REVIEW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_RESPONSE_REVIEW), nameProperCase);
        } else if (EnumCustomerMappingStatus.IPS_SHARED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_IPS_SHARED), nameProperCase);
        } else if (EnumCustomerMappingStatus.IPS_REVIEWED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_IPS_REVIEWED_INVESTOR_SIDE), nameProperCase);
        } else if (EnumCustomerMappingStatus.IPS_ACCEPTED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_IPS_ACCEPTED_INVESTORSIDE), nameProperCase);
        } else if (EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_REBALCE_INITIATED), nameProperCase);
        } else if (EnumCustomerMappingStatus.ACTIVE.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_ACTIVE), nameProperCase);
        } else if (EnumCustomerMappingStatus.SUSPENDED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_SUSPENDED), nameProperCase);
        } else if (EnumCustomerMappingStatus.EXPIRED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_EXPIRED), nameProperCase);
        } else if(EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue() == status){
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_CONTRACT_TERMINATED),nameProperCase);
        }
        return notificationMessage;
    }

    public static String getAdvNotificationMessage(boolean advisorRequest, int status, String investorName) {
        String notificationMessage = null;
        String nameProperCase = StringCaseConverter.toProperCase(investorName);
        if (EnumAdvisorMappingStatus.INVITE_RECIEVED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_INVITE_RECIEVED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(
                    advisorRequest ? ADVISOR_INVITE_ACCEPTED : ADVISOR_ADVISOR_ACCEPTED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.INVITE_DECLINED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_INVITE_DECLINED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.CONTRACT_REVIEW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_CONTRACT_REVIEW), nameProperCase);
        } else if (EnumAdvisorMappingStatus.CONTRACT_SIGNED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_CONTRACT_SIGNED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.RESPONSE_RECIEVED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_RESPONSE_RECIEVED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.RESPONSE_REVIEW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_RESPONSE_REVIEW), nameProperCase);
        } else if (EnumAdvisorMappingStatus.IPS_REVIEWED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_IPS_REVIEWED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.IPS_ACCEPTED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_IPS_ACCEPTED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.ACTIVE.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_ACTIVE), nameProperCase);
        } else if (EnumAdvisorMappingStatus.SUSPENDED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_SUSPENDED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.EXPIRED.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_EXPIRED), nameProperCase);
        } else if (EnumAdvisorMappingStatus.INVESTOR_WITHDRAW.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_INVITE_WITHDRAW), nameProperCase);
        }else if(EnumCustomerMappingStatus.CONTRACT_TERMINATED.getValue() == status){
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_CONTRACT_TERMINATED),nameProperCase);
        }

        return notificationMessage;
    }

    public static String getAdminNotificationMessage(int status) {
        String notificationMessage = null;
        if (EnumAdminNotificationStatus.REG_KIT_OUT_OF_STOCK.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADMIN_REGKIT_OUTOFSTOCK), EMPTY_STRING);
        } else if (EnumAdminNotificationStatus.REG_KIT_INADEQUATE.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADMIN_REGKIT_INADEQUATE), EMPTY_STRING);
        } else if (EnumAdminNotificationStatus.UPDATE_HOLIDAY_CALENDER.getValue() == status) {
            notificationMessage = String.format(NotificationsLoader.getPropertiesValue(ADMIN_HOLIDAY_CALENDER), EMPTY_STRING);
        }
        return notificationMessage;
    }
}
