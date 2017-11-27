/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.webapp.util;

import com.gtl.mmf.common.EnumAdminNotificationStatus;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.EnumCustomerNotification;
import com.gtl.mmf.controller.UserSessionBean;
import static com.gtl.mmf.service.util.IConstants.ADMIN_NOTIFY_STATUS_CODE;
import static com.gtl.mmf.service.util.IConstants.CREATE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FROM_NOTIFICATION;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_DASHBOARD;
import static com.gtl.mmf.service.util.IConstants.NOTIFY_ADMIN;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_ID;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.REJECTION_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.TODAYS_ORDER;
import static com.gtl.mmf.service.util.IConstants.TODAYS_TRADE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import com.gtl.mmf.service.vo.AdminNotificationVO;
import com.gtl.mmf.service.vo.AdvisorNotificationsVO;
import com.gtl.mmf.service.vo.InvestorNotificationsVO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author trainee3
 */
public class NotificationRedirect {

    private String redirectTo = EMPTY_STRING;

    public String onSelectNotificationINV(List<InvestorNotificationsVO> investorNotifications, int selectedNotificationIndex, boolean omsEnabled) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        InvestorNotificationsVO selectedNotification = investorNotifications.get(selectedNotificationIndex);
        if (selectedNotification.getEnumCustomerNotification() == EnumCustomerNotification.PORTFOLIO_CHANGED) {

            //redirecting based on omsEnabled flag   
//            if (omsEnabled) {
//                UserSessionBean userSessionBean = (UserSessionBean) sessionMap
//                        .get(USER_SESSION);
//                userSessionBean.setFromURL(INVESTOR_DASHBOARD);
//                redirectTo = "/pages/oms_login?faces-redirect=true";
//            } else {
//                redirectTo = "/pages/rebalance_portfolio?faces-redirect=true";
//            }
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            userSessionBean.setFromURL(INVESTOR_DASHBOARD);
            redirectTo = "/pages/rebalance_portfolio?faces-redirect=true";
            session.setAttribute("AddReedemOrderExecution", false);
            storedValues.put("rebalance", true);
        } else if (selectedNotification.getEnumCustomerNotification() == EnumCustomerNotification.REBALANCE_REQUIRED) {
            redirectTo = "/pages/rebalance_portfolio?faces-redirect=true";
            storedValues.put("rebalance", true);
        } else if (selectedNotification.getEnumCustomerNotification() == EnumCustomerNotification.RATE_ADVISOR) {
            redirectTo = "/pages/rate_advisor?faces-redirect=true";
            storedValues.put("rateAdvisor", true);
        } else {
            EnumCustomerMappingStatus relationStatus = EnumCustomerMappingStatus.fromInt(selectedNotification.getAdvisorDetailsVO().getStatus());
            if (relationStatus == EnumCustomerMappingStatus.INVITE_ACCEPTED
                    || relationStatus == EnumCustomerMappingStatus.INVITE_RECIEVED
                    || relationStatus == EnumCustomerMappingStatus.INVITE_SENT
                    || relationStatus == EnumCustomerMappingStatus.INVITE_DECLINED
                    || relationStatus == EnumCustomerMappingStatus.WITHDRAW
                    || relationStatus == EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED
                    || relationStatus == EnumCustomerMappingStatus.NO_RELATION
                    || relationStatus == EnumCustomerMappingStatus.ADVISOR_WITHDRAW) {
                storedValues.put("notification", true);
                redirectTo = "/pages/investmentadvisorprofile?faces-redirect=true";
            } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_RECIEVED
                    || relationStatus == EnumCustomerMappingStatus.CONTRACT_REVIEW) {
                storedValues.put("notification", true);
                redirectTo = "/pages/advisory_service_contract?faces-redirect=true";
            } else if (relationStatus == EnumCustomerMappingStatus.CONTRACT_SIGNED
                    || relationStatus == EnumCustomerMappingStatus.RESPONSE_SENT) {
                storedValues.put("notification", true);
                redirectTo = "/pages/investordashboard?faces-redirect=true";
            } else if (relationStatus == EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED) {
                storedValues.put("notification", true);
                redirectTo = "/pages/v2/riskProfile.jsp?faces-redirect=true";
                //redirectTo = "/pages/investor_questionnaire?faces-redirect=true";//-SumeetChanges
            } else if (relationStatus == EnumCustomerMappingStatus.IPS_SHARED) {
                storedValues.put("notification", true);
                redirectTo = "/pages/investment_policy_statement_view?faces-redirect=true";
            } else if (relationStatus == EnumCustomerMappingStatus.IPS_ACCEPTED) {
                storedValues.put("notification", true);
                redirectTo = "/pages/view_portfolio?faces-redirect=true";
            } else if (relationStatus == EnumCustomerMappingStatus.REBALANCE_INITIATED) {
                if (selectedNotification.getButtonText().equalsIgnoreCase("View Orders")) {
                    if (omsEnabled) {
                        session.setAttribute("AddReedemOrderExecution", false);
                        session.setAttribute(TODAYS_DETAILS, true);
                        session.setAttribute(TODAYS_ORDER, true);
                        session.setAttribute(TODAYS_TRADE, false);
                        session.setAttribute("tradeBreakdown", false);
                        session.setAttribute(REJECTION_SUMMARY, false);
                        session.setAttribute(PORTFOLIO_SUMMARY, false);
                        if (session.getAttribute("OMSOrderVO") != null) {
                            redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
                        } else {
                            redirectTo = "/pages/oms_login?faces-redirect=true";
                        }
                    } else {
                        redirectTo = "/pages/investordashboard?faces-redirect=true";
                    }
                } else {
                    redirectTo = "/pages/portfolio_details?faces-redirect=true";
                }

            }
        }
        if (!redirectTo.equalsIgnoreCase("")) {
            storedValues.put(SELECTED_ADVISOR, selectedNotification.getAdvisorDetailsVO());
        }
        sessionMap.put(STORED_VALUES, storedValues);
        return redirectTo;
    }

    public String onSelectNotificationADV(List<AdvisorNotificationsVO> advisorNotifications, int selectedNotificationIndex) {
        String redirectTo = EMPTY_STRING;
        Map<String, Object> storedValues = new HashMap<String, Object>();
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        AdvisorNotificationsVO selectedNotification = advisorNotifications.get(selectedNotificationIndex);
        if (selectedNotification.getInvestorDetailsVO() == null) {
            storedValues.put(CREATE, false);
            storedValues.put(PORTFOLIO_ID, selectedNotification.getPortfolioId());
            storedValues.put(FROM_NOTIFICATION, true);
            storedValues.remove(PORTFOLIO);
            redirectTo = "/pages/create_edit_portfolio?faces-redirect=true";
            storedValues.put("rebalance", true);
        } else {
            EnumAdvisorMappingStatus relationStatus = EnumAdvisorMappingStatus.fromInt(selectedNotification.getInvestorDetailsVO().getStatus());

            if (relationStatus == EnumAdvisorMappingStatus.INVITE_RECIEVED
                    || relationStatus == EnumAdvisorMappingStatus.INVITE_SENT
                    || relationStatus == EnumAdvisorMappingStatus.INVITE_DECLINED
                    || relationStatus == EnumAdvisorMappingStatus.WITHDRAW
                    || relationStatus == EnumAdvisorMappingStatus.NO_RELATION
                    || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SIGNED
                    || relationStatus == EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT
                    || relationStatus == EnumAdvisorMappingStatus.INVESTOR_WITHDRAW
                    || relationStatus == EnumAdvisorMappingStatus.IPS_ACCEPTED) {
                redirectTo = "/pages/investorprofile.xhtml?faces-redirect=true";
                storedValues.put("notification", true);
            } else if (relationStatus == EnumAdvisorMappingStatus.INVITE_ACCEPTED
                    || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SENT
                    || relationStatus == EnumAdvisorMappingStatus.CONTRACT_REVIEW) {
                redirectTo = "/pages/advisory_service_contract?faces-redirect=true";
                storedValues.put("notification", true);
            } else if (relationStatus == EnumAdvisorMappingStatus.RESPONSE_RECIEVED) {
                redirectTo = "/pages/investment_policy_statement_create?faces-redirect=true";
                storedValues.put("notification", true);
            } else if (relationStatus == EnumAdvisorMappingStatus.IPS_SHARED || relationStatus == EnumAdvisorMappingStatus.IPS_REVIEWED) {
                redirectTo = "/pages/assign_portfolio?faces-redirect=true";
                storedValues.put("notification", true);

            }
        }
        if (!redirectTo.equalsIgnoreCase(EMPTY_STRING)) {
            storedValues.put(SELECTED_INVESTOR, selectedNotification.getInvestorDetailsVO());
        }

        sessionMap.put(STORED_VALUES, storedValues);
        return redirectTo;
    }

    public String onSelectNotificationADM(List<AdminNotificationVO> adminNotificationspoup, int selectedNotificationIndex) {
        String redirectTo = EMPTY_STRING;
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> sessionMap = ec.getSessionMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        AdminNotificationVO selectedNotification = adminNotificationspoup.get(selectedNotificationIndex);
        storedValues.put(ADMIN_NOTIFY_STATUS_CODE, selectedNotification.getStatusCode());
        storedValues.put(NOTIFY_ADMIN, true);
        if (selectedNotification.getEnumAdminNotificationStatus() == EnumAdminNotificationStatus.REG_KIT_INADEQUATE
                || selectedNotification.getEnumAdminNotificationStatus() == EnumAdminNotificationStatus.REG_KIT_OUT_OF_STOCK) {
            redirectTo = "/pages/admin/allocate_reg_kit?faces-redirect=true";
        } else if (selectedNotification.getEnumAdminNotificationStatus() == EnumAdminNotificationStatus.UPDATE_HOLIDAY_CALENDER) {
            redirectTo = "/pages/admin/holidayCalender?faces-redirect=true";
        }
        sessionMap.put(STORED_VALUES, storedValues);
        return redirectTo;
    }
}
