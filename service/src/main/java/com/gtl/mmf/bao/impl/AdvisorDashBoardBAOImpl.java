/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import static com.gtl.linkedin.util.INotificationMessages.ADVISOR_REBALANCE_REQD;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.NotificationsLoader;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IAdvisorDashBoardBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.dao.IAdvisorDashBoardDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import com.gtl.mmf.entity.MasterPortfolioTypeTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.PortfolioPerformanceUtil;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.AdvisorNotificationsVO;
import com.gtl.mmf.service.vo.ClientDetailsVO;
import com.gtl.mmf.service.vo.ClientPerformanceVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.NotificationAdvisorListVO;
import com.gtl.mmf.service.vo.PortfolioPerformanceVO;
import com.gtl.mmf.service.vo.PortfolioRebalanceTriggerVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author 07958
 */
public class AdvisorDashBoardBAOImpl implements IAdvisorDashBoardBAO, IConstants {

    @Autowired
    private IAdvisorDashBoardDAO advisorDashBoardDAO;

    /**
     * #Checking for whether portfolio details require rebalancing #it returns
     * true either portfolio details require rebalancing or any of the security
     * added to that category require rebalancing.
     *
     * @param portfolioDetailsTb - Contain Details about the portfolio Assigned
     * to the investor
     * @return boolean specifying whether portfolio require rebalancing
     */
    private boolean isPortfolioDetailsRebalanceReqd(PortfolioDetailsTb portfolioDetailsTb) {
        boolean portfolioDetailsRebalanceReqd = false;
        boolean rebalanceAsset = portfolioDetailsTb.getRebalanceRequired() == null ? false
                : portfolioDetailsTb.getRebalanceRequired();
        if (rebalanceAsset) {
            portfolioDetailsRebalanceReqd = true;
        } else {

            // checking security for rebalancing
            Set<PortfolioSecuritiesTb> portfolioSecuritiesTbs = portfolioDetailsTb.getPortfolioSecuritiesTbs();
            for (PortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesTbs) {
                boolean rebalanceSecurity = portfolioSecuritiesTb.getRebalanceRequired() == null ? false
                        : portfolioSecuritiesTb.getRebalanceRequired();
                if (portfolioSecuritiesTb.getStatus() && rebalanceSecurity) {
                    portfolioDetailsRebalanceReqd = true;
                }
            }
        }
        return portfolioDetailsRebalanceReqd;
    }

    /**
     * Returning string representation of allocation range specified for each
     * portfolio details
     *
     * @param rangeFrom -Starting range Specified for a particular asset class
     * for allocation
     * @param rangeTo -Max range Specified for a particular asset class for
     * allocation
     * @return String representation which is in the form 'rangeFrom-rangeTo %'
     */
    private static String getAllocationAsString(Double rangeFrom, Double rangeTo) {
        int rangeFromVal = BigDecimal.valueOf(rangeFrom).setScale(ZERO, RoundingMode.FLOOR).intValue();
        int rangeToVal = BigDecimal.valueOf(rangeTo).setScale(ZERO, RoundingMode.FLOOR).intValue();
        return new StringBuilder(String.valueOf(rangeFromVal)).append(SPACE).append("-").append(SPACE).append(rangeToVal).toString();
    }

    /**
     * Returning client status information to advisor for displaying in the
     * dashboard. Only clients accepted invitation are displayed
     *
     * @param advisorId ID representing Selected advisor
     * @param accessToken - Which is used to get LinkedIN response to identify
     * their linkedIn relation status.
     * @return List<ClientDetailsVO> - Contain details about all the clients
     * assigned to the advisor.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<ClientDetailsVO> getClientStatusDetails(int advisorId, String accessToken) {
        List<ClientDetailsVO> listClientDetails = new ArrayList<ClientDetailsVO>();

        // reading client status information into list for a the advisor
        List<Object[]> clientStatusDetailsList = advisorDashBoardDAO.getClientStatusDetails(advisorId);
        for (Object[] clientItem : clientStatusDetailsList) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = (CustomerAdvisorMappingTb) clientItem[0];
            MasterPortfolioTypeTb masterPortfolioTypeTb = (MasterPortfolioTypeTb) clientItem[1];
            CustomerRiskProfileTb riskProfileTb = (CustomerRiskProfileTb) clientItem[2];
            EnumAdvisorMappingStatus relationStatus = EnumAdvisorMappingStatus.fromInt(Integer.valueOf(customerAdvisorMappingTb.getRelationStatus()));

            /*Checking for customers those who acceted invitation  with advisor and 
             avoiding all other clients */
            if (!getNoRelationStatuses().contains(relationStatus)) {
                ClientDetailsVO clientDetailsVO = new ClientDetailsVO();
                InvestorDetailsVO investorDetails = new InvestorDetailsVO();
                clientDetailsVO.setCompany(customerAdvisorMappingTb.getMasterCustomerTb().
                        getWorkOrganization());
                clientDetailsVO.setJobTitle(customerAdvisorMappingTb.getMasterCustomerTb().getJobTitle());
                clientDetailsVO.setName(customerAdvisorMappingTb.getMasterCustomerTb().getFirstName()
                        + SPACE + customerAdvisorMappingTb.getMasterCustomerTb().getMiddleName()
                        + SPACE + customerAdvisorMappingTb.getMasterCustomerTb().getLastName());
                clientDetailsVO.setRelationId(customerAdvisorMappingTb.getRelationId());
                LinkedInVO linkedInVO = null;
                if (customerAdvisorMappingTb.getMasterAdvisorTb().getMasterApplicantTb().
                        getLinkedInConnected()) {
//                    String linkedinMemberId = customerAdvisorMappingTb.getMasterAdvisorTb().
//                            getMasterApplicantTb().getLinkedinMemberId();
                    String linkedinMemberId = customerAdvisorMappingTb.getMasterCustomerTb().
                            getMasterApplicantTb().getLinkedinMemberId();
                    ServiceParamVO serPVO = new ServiceParamVO();

                    /* checking connection level of customer and advisor in linkedIn
                     Distance = 0,1,100 means no connection
                     Distance = "1" Friends
                     Distance = "2" Friends of Friends
                     */
                    serPVO.setConvertedIds(linkedinMemberId);
                    linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(accessToken, serPVO,
                            ServiceTypeEnum.SHARED_CONNECTIONS);
                }
                if (linkedInVO != null) {
                    clientDetailsVO.setConnectionLevel((linkedInVO.getDistance() == null
                            || linkedInVO.getDistance().equals("0")) || linkedInVO.getDistance().equals("-1")
                            || linkedInVO.getDistance().equals("100") ? CONNECTION_LEVEL_DEFAULT
                            : linkedInVO.getDistance());
                    investorDetails.setConnectionLevel((linkedInVO.getDistance() == null
                            || linkedInVO.getDistance().equals("0")) || linkedInVO.getDistance().equals("-1")
                            || linkedInVO.getDistance().equals("100") ? CONNECTION_LEVEL_DEFAULT
                            : linkedInVO.getDistance());
                    clientDetailsVO.setSharedConnection(linkedInVO.getConnections()
                            == null ? SHARED_CONNECTIONS_DEFAULT : linkedInVO.getConnections().getTotal());
                    investorDetails.setConnectionShared(linkedInVO.getConnections()
                            == null ? SHARED_CONNECTIONS_DEFAULT : linkedInVO.getConnections().getTotal());
                } else if (customerAdvisorMappingTb.getMasterCustomerTb().getMasterApplicantTb().
                        getLinkedInConnected()) {
                    clientDetailsVO.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    investorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    clientDetailsVO.setSharedConnection(SHARED_CONNECTIONS_DEFAULT);
                    investorDetails.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);
                } else {
                    clientDetailsVO.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    clientDetailsVO.setSharedConnection(LINKEDIN_NOT_CONNECTED);
                    investorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    investorDetails.setConnectionShared(LINKEDIN_NOT_CONNECTED);
                }
                String status = StringCaseConverter.toProperCase(relationStatus.toString());
                clientDetailsVO.setStatus(status);

                investorDetails.setRelationId(customerAdvisorMappingTb.getRelationId());
                investorDetails.setCustomerId(customerAdvisorMappingTb.getMasterCustomerTb().getCustomerId());
                investorDetails.setFirstName(clientDetailsVO.getName());
                investorDetails.setWorkOrganization(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getWorkOrganization());
                investorDetails.setJobTitle(customerAdvisorMappingTb.getMasterCustomerTb().getJobTitle());
                investorDetails.setStatus(relationStatus.getValue());
                investorDetails.setRegId(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getRegistrationId());
                investorDetails.setEmail(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getEmail());
                investorDetails.setPictureURL(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getPictureUrl());
//                investorDetails.setPortFolioStyle(masterPortfolioTypeTb == null ? EMPTY_STRING
//                        : masterPortfolioTypeTb.getMasterPortfolioStyleTb().getPortfolioStyle());
                //To display system valued resk profile style
                investorDetails.setPortFolioStyle(riskProfileTb == null ? EMPTY_STRING
                        : riskProfileTb.getMasterPortfolioStyleTbByInitialPortfolioStyle().getPortfolioStyle());
                investorDetails.setStatusValue(status);
                investorDetails.setLinkedInConnected(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getLinkedInConnected());
                investorDetails.setLinkedInId(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getLinkedinMemberId() == null ? EMPTY_STRING
                                : customerAdvisorMappingTb.getMasterCustomerTb()
                                .getMasterApplicantTb().getLinkedinMemberId());
                investorDetails.setAllocatedInvestments(customerAdvisorMappingTb.getAllocatedFunds() == null ? ZERO_POINT_ZERO
                        : customerAdvisorMappingTb.getAllocatedFunds());
                clientDetailsVO.setInvestorDetailsVO(investorDetails);
                listClientDetails.add(clientDetailsVO);
                if (relationStatus == EnumAdvisorMappingStatus.IPS_CREATED || relationStatus == EnumAdvisorMappingStatus.IPS_REVIEWED
                        || relationStatus == EnumAdvisorMappingStatus.IPS_SHARED || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SENT
                        || relationStatus == EnumAdvisorMappingStatus.CONTRACT_REVIEW || relationStatus == EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT
                        || relationStatus == EnumAdvisorMappingStatus.RESPONSE_RECIEVED || relationStatus == EnumAdvisorMappingStatus.CONTRACT_SIGNED
                        || relationStatus == EnumAdvisorMappingStatus.RESPONSE_REVIEW || relationStatus == EnumAdvisorMappingStatus.INVITE_RECIEVED
                        || relationStatus == EnumAdvisorMappingStatus.INVITE_SENT) {
                    clientDetailsVO.setStyleClass("grey align_center");
                } else if (relationStatus == EnumAdvisorMappingStatus.IPS_ACCEPTED || relationStatus == EnumAdvisorMappingStatus.ORDER_PLACED_IN_OMS
                        || relationStatus == EnumAdvisorMappingStatus.REBALANCE_INITIATED || relationStatus == EnumAdvisorMappingStatus.ACTIVE
                        || relationStatus == EnumAdvisorMappingStatus.INVITE_ACCEPTED) {
                    clientDetailsVO.setStyleClass("green align_center");
                } else if (relationStatus == EnumAdvisorMappingStatus.EXPIRED || relationStatus == EnumAdvisorMappingStatus.SUSPENDED
                        || relationStatus == EnumAdvisorMappingStatus.WITHDRAW || relationStatus == EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED
                        || relationStatus == EnumAdvisorMappingStatus.INVESTOR_WITHDRAW || relationStatus == EnumAdvisorMappingStatus.INVITE_DECLINED) {
                    clientDetailsVO.setStyleClass("red align_center");
                }
            }
        }
        return listClientDetails;
    }

    /**
     * These are the client status to avoid when selecting clients for
     * displaying on the dashboard
     *
     * @return List<EnumAdvisorMappingStatus> - List of relation status to avoid
     * while displaying clients in dashboard
     */
    private List<EnumAdvisorMappingStatus> getNoRelationStatuses() {
        List<EnumAdvisorMappingStatus> status = new ArrayList<EnumAdvisorMappingStatus>();
        status.add(EnumAdvisorMappingStatus.NO_RELATION);
        status.add(EnumAdvisorMappingStatus.INVESTOR_WITHDRAW);
        status.add(EnumAdvisorMappingStatus.WITHDRAW);
        status.add(EnumAdvisorMappingStatus.INVITE_DECLINED);
        status.add(EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED);
        return status;
    }

    /**
     * Loading Notification informations for advisor.
     *
     * @param advisorId ID representing Selected advisor
     * @return NotificationAdvisorListVO which contains notifications to be
     * displayed on popup and dashboard.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public NotificationAdvisorListVO getAdvisorNotifications(int advisorId) {
        List<AdvisorNotificationsVO> advisorNotificationsList = new ArrayList<AdvisorNotificationsVO>();

        /* advisorNotificationsListPopup: List for showing notification in popup
         that are not visited by user */
        List<AdvisorNotificationsVO> advisorNotificationsListPopup = new ArrayList<AdvisorNotificationsVO>();

        //loading client details from customerAdvisorMappingTb,masterPortfolioTypeTb
        List<Object[]> advisorNotifications = advisorDashBoardDAO.getClientStatusDetails(advisorId);
        for (Object[] mappingItem : advisorNotifications) {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = (CustomerAdvisorMappingTb) mappingItem[ZERO];
            MasterPortfolioTypeTb masterPortfolioTypeTb = (MasterPortfolioTypeTb) mappingItem[ONE];
            CustomerRiskProfileTb riskProfileTb = (CustomerRiskProfileTb) mappingItem[2];
            AdvisorNotificationsVO notification = new AdvisorNotificationsVO();
            Integer relationStatus = Integer.valueOf(customerAdvisorMappingTb.getRelationStatus());
            EnumAdvisorMappingStatus status = EnumAdvisorMappingStatus.fromInt(relationStatus);

            // Checking Customer current status 
            if (getAdvNotificationStatusList().contains(status)) {
                InvestorDetailsVO investorDetails = new InvestorDetailsVO();
                investorDetails.setRelationId(customerAdvisorMappingTb.getRelationId());
                investorDetails.setCustomerId(customerAdvisorMappingTb.getMasterCustomerTb().getCustomerId());
                investorDetails.setFirstName(customerAdvisorMappingTb.getMasterCustomerTb().getFirstName());
                investorDetails.setWorkOrganization(customerAdvisorMappingTb.getMasterCustomerTb().getWorkOrganization());
                investorDetails.setJobTitle(customerAdvisorMappingTb.getMasterCustomerTb().getJobTitle());
                investorDetails.setStatus(status.getValue());
                investorDetails.setRegId(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getRegistrationId());
                investorDetails.setEmail(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getEmail());
                investorDetails.setPictureURL(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getPictureUrl());
                /*investorDetails.setPortFolioStyle(masterPortfolioTypeTb == null ? EMPTY_STRING
                        : masterPortfolioTypeTb.getMasterPortfolioStyleTb().getPortfolioStyle());*/
                investorDetails.setPortFolioStyle(riskProfileTb == null ? EMPTY_STRING
                        : riskProfileTb.getMasterPortfolioStyleTbByInitialPortfolioStyle().getPortfolioStyle());
                String notificationMsg = InvestorNotificationMsgService.getAdvNotificationMessage(
                        customerAdvisorMappingTb.getAdvisorRequest().booleanValue(), investorDetails.getStatus(),
                        customerAdvisorMappingTb.getMasterCustomerTb().getFirstName());
                status = (status == EnumAdvisorMappingStatus.INVESTOR_WITHDRAW
                        || status == EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED)
                                ? EnumAdvisorMappingStatus.NO_RELATION : status;
                investorDetails.setStatusValue(StringCaseConverter.toProperCase(status.toString()));
                investorDetails.setLinkedinProfileUrl(customerAdvisorMappingTb.getMasterCustomerTb()
                        .getMasterApplicantTb().getLinkedinProfileUrl() == null
                                ? EMPTY_STRING : customerAdvisorMappingTb.getMasterCustomerTb()
                                .getMasterApplicantTb().getLinkedinProfileUrl());
                investorDetails.setAllocatedInvestments(customerAdvisorMappingTb.getAllocatedFunds() == null ? ZERO_POINT_ZERO
                        : customerAdvisorMappingTb.getAllocatedFunds());
                notification.setNotificationMessage(notificationMsg);
                notification.setInvestorDetailsVO(investorDetails);
                notification.setButtonText(BTN_VIEW);
                notification.setNotificationDate(customerAdvisorMappingTb.getStatusDate());

                //Setting elapsed notification time to display in the popup
                notification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(notification.getNotificationDate()));
                advisorNotificationsList.add(notification);
                if (customerAdvisorMappingTb.getAdvisorViewed() != null && !customerAdvisorMappingTb.getAdvisorViewed()) {
                    advisorNotificationsListPopup.add(notification);
                }
            }
        }

        // Setting notification for rebalance
        List<Object[]> rebalanceNotifications = advisorDashBoardDAO.getRebalanceNotification(advisorId);
        for (Object[] rebalanceNotification : rebalanceNotifications) {
            Date date = (Date) rebalanceNotification[0];
            Integer portfolioId = (Integer) rebalanceNotification[ONE];
            AdvisorNotificationsVO notification = new AdvisorNotificationsVO();
//            StringBuffer msg = new StringBuffer();
//            msg.append(NotificationsLoader.getPropertiesValue(ADVISOR_REBALANCE_REQD)).append(HYPHON)
//                    .append(SPACE).append((String) rebalanceNotification[3]);
            String msg = String.format(NotificationsLoader.getPropertiesValue(ADVISOR_REBALANCE_REQD), rebalanceNotification[3]);
            notification.setNotificationMessage(msg);
            notification.setButtonText(BTN_VIEW);
            notification.setNotificationDate(date);
            //Setting elapsed notification time 
            notification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(notification.getNotificationDate()));
            notification.setPortfolioId(portfolioId);
            advisorNotificationsList.add(notification);
            if ((Boolean) rebalanceNotification[2] == null || !(Boolean) rebalanceNotification[2]) {
                advisorNotificationsListPopup.add(notification);
            }
        }
        // Sortfing notifications on date
        Collections.sort(advisorNotificationsList, new Comparator<AdvisorNotificationsVO>() {
            public int compare(AdvisorNotificationsVO o1, AdvisorNotificationsVO o2) {
                return o1.getNotificationDate().compareTo(o2.getNotificationDate());
            }
        });
        NotificationAdvisorListVO advisorListVO = new NotificationAdvisorListVO();

        advisorListVO.setNotifications(advisorNotificationsList);
        advisorListVO.setNotificationsPopup(advisorNotificationsListPopup);

        return advisorListVO;
    }

    /**
     * This method returns a list which contains the status of Customer
     * notifications to be displayed on the popup
     *
     * @return List of customer notification
     */
    private static List<EnumAdvisorMappingStatus> getAdvNotificationStatusList() {
        List<EnumAdvisorMappingStatus> statusHavingNotification = new ArrayList<EnumAdvisorMappingStatus>();
        statusHavingNotification.add(EnumAdvisorMappingStatus.INVITE_RECIEVED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.INVITE_ACCEPTED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.INVESTOR_INVITE_DECLINED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.CONTRACT_REVIEW);
        statusHavingNotification.add(EnumAdvisorMappingStatus.CONTRACT_SIGNED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.RESPONSE_REVIEW);
        statusHavingNotification.add(EnumAdvisorMappingStatus.RESPONSE_RECIEVED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.IPS_REVIEWED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.IPS_ACCEPTED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.ACTIVE);
        statusHavingNotification.add(EnumAdvisorMappingStatus.SUSPENDED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.EXPIRED);
        statusHavingNotification.add(EnumAdvisorMappingStatus.INVESTOR_WITHDRAW);
        return statusHavingNotification;
    }

    /**
     * Collecting informations to be displayed on the advisor dashboard tables.
     *
     * @param advisorId - ID representing Selected advisor
     * @param clientPerformanceVOs - Client performance details
     * @param portfolioPerformanceVOs - Advisor portfolio performance details
     * @param portfoliosRebalanceTriggerVOs - Rebalance triggers needed for each
     * portfolio created by advisor
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getDashboardDatas(int advisorId, List<ClientPerformanceVO> clientPerformanceVOs,
            List<PortfolioPerformanceVO> portfolioPerformanceVOs, List<PortfolioRebalanceTriggerVO> portfoliosRebalanceTriggerVOs) {

        //Selecting all portfolios of the perticular advisor 
        List<PortfolioTb> advisorsPortfolios = advisorDashBoardDAO.getAdvisorsPortfolios(advisorId);
        for (PortfolioTb portfolioTb : advisorsPortfolios) {

            //Client performance details
            clientPerformanceVOs.addAll(this.getClientPerformanceVOs(portfolioTb));

            //Portfolio performance details
            portfolioPerformanceVOs.add(this.getPortfolioPerformanceVO(portfolioTb));

            //Checking rebalance for portfolio
            boolean rebalanceRequired = portfolioTb.getRebalanceRequired() == null ? false
                    : portfolioTb.getRebalanceRequired().booleanValue();
            if (rebalanceRequired) {

                // if rebalnce required adding details adbout securities which require rebalancing
                portfoliosRebalanceTriggerVOs.addAll(this.getPortfolioRebalanceTriggerVOs(portfolioTb));
            }
        }
    }

    /**
     * Checking securities for re-balance trigger. this information is displayed
     * on re-balance trigger table in advisor dashboard
     *
     * @param portfolioTb Portfolio Selected for checking re-balancing
     * @return List contains re-balance trigger required for assets and
     * securities in the portfolio selected.
     */
    private List<PortfolioRebalanceTriggerVO> getPortfolioRebalanceTriggerVOs(PortfolioTb portfolioTb) {
        List<PortfolioRebalanceTriggerVO> portfolioRebalanceTriggerVOs = new ArrayList<PortfolioRebalanceTriggerVO>();
        Set<PortfolioDetailsTb> portfolioDetailsTbs = portfolioTb.getPortfolioDetailsTbs();
        for (PortfolioDetailsTb portfolioDetailsTb : portfolioDetailsTbs) {
            if (this.isPortfolioDetailsRebalanceReqd(portfolioDetailsTb)) {
                PortfolioRebalanceTriggerVO portfolioRebalanceTriggersVO = new PortfolioRebalanceTriggerVO();
                portfolioRebalanceTriggersVO.setAllocation(
                        getAllocationAsString(portfolioDetailsTb.getRangeFrom().doubleValue(), portfolioDetailsTb.getRangeTo().doubleValue()));
                portfolioRebalanceTriggersVO.setAssetClassId(portfolioDetailsTb.getMasterAssetTb().getId());
                portfolioRebalanceTriggersVO.setAssetClassName(portfolioDetailsTb.getMasterAssetTb().getAssetName());
                portfolioRebalanceTriggersVO.setCurrentLevel(portfolioDetailsTb.getCurrentWeight().doubleValue());
                portfolioRebalanceTriggersVO.setDateBreached(DateUtil.dateToString(portfolioDetailsTb.getRebalanceReqdDate(), DD_SLASH_MM_SLASH_YYYY));
                portfolioRebalanceTriggersVO.setPortfolioId(portfolioDetailsTb.getPortfolioTb().getPortfolioId());
                portfolioRebalanceTriggersVO.setPortfolioName(portfolioDetailsTb.getPortfolioTb().getPortfolioName());
                portfolioRebalanceTriggerVOs.add(portfolioRebalanceTriggersVO);
            }
        }
        return portfolioRebalanceTriggerVOs;
    }

    /**
     * This method selects each portfolio created by advisor and check all the
     * customers assigned to that portfolio and calculate customer performance
     * details Returning customer performance details.
     *
     * @param portfolioTb Portfolio assigned to a particular investor by advisor
     * for calculating actual and recomended performance.
     * @return List of all customers performance details for a particular
     * portfolio.
     */
    private List<ClientPerformanceVO> getClientPerformanceVOs(PortfolioTb portfolioTb) {
        List<ClientPerformanceVO> clientPerformanceVOs = new ArrayList<ClientPerformanceVO>();
        Set<CustomerPortfolioTb> customerPortfolioTbs = portfolioTb.getCustomerPortfolioTbs();
        for (CustomerPortfolioTb customerPortfolioTb : customerPortfolioTbs) {
            if(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationStatus() != EnumAdvisorMappingStatus.CONTRACT_TERMINATED.getValue()){
                ClientPerformanceVO clientPerformanceVO = new ClientPerformanceVO();
            clientPerformanceVO.setPortfolioId(portfolioTb.getPortfolioId());
            clientPerformanceVO.setCustomerPortfolioId(customerPortfolioTb.getCustomerPortfolioId());
            clientPerformanceVO.setCustomerId(customerPortfolioTb.getMasterCustomerTb().getCustomerId());
            StringBuffer name = new StringBuffer();
            name.append(customerPortfolioTb.getMasterCustomerTb().getFirstName()).append(SPACE)
                    .append(customerPortfolioTb.getMasterCustomerTb().getMiddleName()).append(SPACE)
                    .append(customerPortfolioTb.getMasterCustomerTb().getLastName());
            clientPerformanceVO.setCustomerName(name.toString());
            clientPerformanceVO.setCompanyName(customerPortfolioTb.getMasterCustomerTb().getMasterApplicantTb().getWorkOrganization());
            clientPerformanceVO.setPortfolioName(portfolioTb.getPortfolioName());

            //returns sub-returns calculated for the particular customer portfolio
            List<BigDecimal> clientSubPeriodReturns = advisorDashBoardDAO.getClientPFSubPeriodReturns(customerPortfolioTb.getCustomerPortfolioId());

            // Calculating  Actual performance.
            clientPerformanceVO.setActualPerformance(PortfolioPerformanceUtil.getPortfolioPerformance(clientSubPeriodReturns));
            List<BigDecimal> recmdSubPeriodReturns = advisorDashBoardDAO.getRecmdPFSubPeriodReturns(portfolioTb.getPortfolioId());

            // Calculating Recomended performance.
            clientPerformanceVO.setRecmdPerformance(PortfolioPerformanceUtil.getPortfolioPerformance(recmdSubPeriodReturns));
            Date contractEnd = customerPortfolioTb.getCustomerAdvisorMappingTb().getContractEnd();
            clientPerformanceVO.setBalancePeriodDays(this.getBalanceDays(contractEnd));
            clientPerformanceVO.setRelationId(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationId());
            clientPerformanceVO.setClientStataus(((Short) customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationStatus()).intValue());
            clientPerformanceVO.setEmail(customerPortfolioTb.getMasterCustomerTb().getEmail());
            clientPerformanceVOs.add(clientPerformanceVO);
            }
            
        }
        return clientPerformanceVOs;
    }

    /**
     * This method selects each portfolio created by advisor and calculate
     * portfolio performance details and returning Portfolio performance
     * details.
     *
     * @param portfolioTb Portfolio created by advisor for calculating actual
     * and recomended performance.
     * @return an Object that contains performance data for the particular
     * portfolio.
     */
    private PortfolioPerformanceVO getPortfolioPerformanceVO(PortfolioTb portfolioTb) {
        PortfolioPerformanceVO portfolioPerformanceVO = new PortfolioPerformanceVO();
        portfolioPerformanceVO.setPortfolioId(portfolioTb.getPortfolioId());
        portfolioPerformanceVO.setPortfolioName(portfolioTb.getPortfolioName());
        List<BigDecimal> recmdSubPeriodReturns = advisorDashBoardDAO.getRecmdPFSubPeriodReturns(portfolioTb.getPortfolioId());

        // Calculating  Portfolio performance.
        portfolioPerformanceVO.setPortfolioReturns(PortfolioPerformanceUtil.getPortfolioPerformance(recmdSubPeriodReturns));
        Date dateCreated = portfolioTb.getDateCreated();

        //Reading benchmark  closing value for current_day and last_day.
        Object[] benchmarkPerformance = advisorDashBoardDAO.getBenchmarkPerformance(dateCreated, getYesterdayDate());
        Double benchmarkReturns;

        // benchmarkPerformance[ZERO] = benchmark  closing value for  last_day.
        //benchmarkPerformance[ONE] = benchmark  closing value for current_day.
        if (benchmarkPerformance[ZERO] == null || benchmarkPerformance[ONE] == null) {
            benchmarkReturns = ZERO_POINT_ZERO;
        } else {
            Double benchmarkStartValue = ((BigDecimal) benchmarkPerformance[ZERO]).doubleValue();
            Double benchmarkCloseValue = ((BigDecimal) benchmarkPerformance[ONE]).doubleValue();

            //benchmarkReturns = ((benchmarkCloseValue / benchmarkStartValue) - ONE) * 100.0;
            benchmarkReturns = PortfolioPerformanceUtil.getPerformanceWithValues(benchmarkCloseValue, benchmarkStartValue);
        }
        portfolioPerformanceVO.setBenchmarkReturns(benchmarkReturns);
        Date lastUpdated = portfolioTb.getLastUpdated();
        portfolioPerformanceVO.setLastModified(DateUtil.dateToString(lastUpdated, DD_SLASH_MM_SLASH_YYYY));
        portfolioPerformanceVO.setNoOfClients(portfolioTb.getNoOfCustomersAssigned());
        portfolioPerformanceVO.setPortfolio_90_day_returns((Double) (portfolioTb.getPortfolio90DayReturns() == null
                ? ZERO_POINT_ZERO : portfolioTb.getPortfolio90DayReturns().doubleValue()));
        portfolioPerformanceVO.setPortfolio_1_year_returns((Double) (portfolioTb.getPortfolio1YearReturns() == null
                ? ZERO_POINT_ZERO : portfolioTb.getPortfolio1YearReturns().doubleValue()));
        portfolioPerformanceVO.setBenchmark_90_days_returns((Double) (portfolioTb.getBenchmark90DaysReturns() == null
                ? ZERO_POINT_ZERO : portfolioTb.getBenchmark90DaysReturns().doubleValue()));
        portfolioPerformanceVO.setBenchmark_1_year_returns((Double) (portfolioTb.getBenchmark1YearReturns() == null
                ? ZERO_POINT_ZERO : portfolioTb.getBenchmark1YearReturns().doubleValue()));
        return portfolioPerformanceVO;
    }

    private Date getYesterdayDate() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }

    /**
     * Calculating remaining days in contact period
     *
     * @param contractEnd - Contract End date between investor and advisor
     * @return
     */
    private int getBalanceDays(Date contractEnd) {
        int balanceDays = ZERO;
        if (contractEnd != null) {
            Date currentDate = new Date();
            if (currentDate.equals(contractEnd) || currentDate.after(contractEnd)) {
                balanceDays = ZERO;
            } else {
                balanceDays = (int) ((contractEnd.getTime() - currentDate.getTime()) / (1000 * 60 * 60 * 24));
            }
        }
        return balanceDays;
    }
}
