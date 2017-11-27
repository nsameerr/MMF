/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import static com.gtl.linkedin.util.INotificationMessages.ADVISOR_REBALANCE_REQD;
import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_REBALANCE_REQD;
import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_PORTFOLIO_CHANGED;
import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_RATE_ADVISOR;
import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_REBALCE_INITIATED;

import com.gtl.linkedin.util.NotificationsLoader;
import com.gtl.mmf.bao.IInvestorDashBoardBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.EnumCustomerNotification;
import com.gtl.mmf.dao.IInvestorDashBoardDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;

import static com.gtl.mmf.service.util.IConstants.BTN_VIEW;
import static com.gtl.mmf.service.util.IConstants.YYYY_MM_DD;
import static com.gtl.mmf.service.util.IConstants.NOSTATUSMSGVALUE;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;

import com.gtl.mmf.service.util.InvestorNotificationMsgService;
import com.gtl.mmf.service.util.StringCaseConverter;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.InvestorNotificationsVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.NotificationInvesterListVo;
import com.gtl.mmf.util.StackTraceWriter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
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
public class InvestorDashBoardBAOImpl implements IInvestorDashBoardBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.InvestorDashBoardBAOImpl");
    private static final Integer THIRTY = 30;
    private static final Integer ONE_YEAR = 365;
    private static final String RATE = "Rate";
    private Boolean portfolioAssigned = false;
    private Double maxYaxisValue = 0.0;
    private String portfolioAssignedDate;
    @Autowired
    private IInvestorDashBoardDAO investorDashBoardDAO;

    /**
     * This method is used to get the notification for the investor.
     *
     * @param clientId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public NotificationInvesterListVo getInvestorNotifications(int clientId) {

        //list to add notifications for dashboard
        List<InvestorNotificationsVO> investorNotificationsList = new ArrayList<InvestorNotificationsVO>();

        //List to add notification to show in the popup
        List<InvestorNotificationsVO> investorNotificationsListPopup = new ArrayList<InvestorNotificationsVO>();

        // Loading relation status notifications 
        List<CustomerAdvisorMappingTb> investorNotifications = investorDashBoardDAO.getInvestorNotifications(clientId, NOSTATUSMSGVALUE);
        if (!investorNotifications.isEmpty()) {
            for (CustomerAdvisorMappingTb customerAdvisorMapping : investorNotifications) {
                Integer relationStatus = Integer.valueOf(customerAdvisorMapping
                        .getRelationStatus());
                EnumCustomerMappingStatus status = EnumCustomerMappingStatus
                        .fromInt(relationStatus);
                AdvisorDetailsVO advisorDetails = new AdvisorDetailsVO();
                this.setAdvisorDetailsVO(advisorDetails,
                        customerAdvisorMapping, status);

                //Setting investor notification on relation status
                if (status.getValue() != EnumAdvisorMappingStatus.ORDER_PLACED_IN_OMS.getValue()) {
                    setInvestorNotificationOnStatus(status, customerAdvisorMapping,
                            investorNotificationsList, investorNotificationsListPopup, advisorDetails);
                }
                //Setting  notification for rating advisor
                setRateAdvisorNotification(status, advisorDetails,
                        customerAdvisorMapping, investorNotificationsList, investorNotificationsListPopup);
            }
        }

        //Loading porfolio Notifications
        CustomerPortfolioTb customerPortfolioTb = investorDashBoardDAO.getPortfolioDetails(clientId);
        if (customerPortfolioTb != null) {
            AdvisorDetailsVO advisorDetails = new AdvisorDetailsVO();
            advisorDetails.setRelationId(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationId());
            advisorDetails.setAdvisorId(customerPortfolioTb.getMasterAdvisorTb().getAdvisorId());
            //Setting rebalancing notification
            setRebalanceRequiredNotification(customerPortfolioTb, advisorDetails, investorNotificationsList, investorNotificationsListPopup);

            //Setting portfolio changed notification
            setPortfolioChangeNotificaion(customerPortfolioTb, advisorDetails, investorNotificationsList, investorNotificationsListPopup);
            portfolioAssigned = true;
        }
        // Sortfing notifications on date
        Collections.sort(investorNotificationsList, new Comparator<InvestorNotificationsVO>() {
            public int compare(InvestorNotificationsVO o1, InvestorNotificationsVO o2) {
                return o1.getNotificationDate().compareTo(o2.getNotificationDate());
            }
        });
        NotificationInvesterListVo investerListVo = new NotificationInvesterListVo();
        investerListVo.setNotifications(investorNotificationsList);
        investerListVo.setNotificationsPopup(investorNotificationsListPopup);

        return investerListVo;
    }

    /**
     * This method is used to add all the status that need notification
     *
     * @return list of status having relation notification
     */
    private static List<EnumCustomerMappingStatus> getInvNotificationStatusList() {
        List<EnumCustomerMappingStatus> statusHavingNotification = new ArrayList<EnumCustomerMappingStatus>();
        statusHavingNotification.add(EnumCustomerMappingStatus.INVITE_RECIEVED);
        statusHavingNotification.add(EnumCustomerMappingStatus.INVITE_ACCEPTED);
        statusHavingNotification.add(EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED);
        statusHavingNotification.add(EnumCustomerMappingStatus.INVITE_DECLINED);
        statusHavingNotification.add(EnumCustomerMappingStatus.ADVISOR_WITHDRAW);
        statusHavingNotification.add(EnumCustomerMappingStatus.CONTRACT_RECIEVED);
        statusHavingNotification.add(EnumCustomerMappingStatus.CONTRACT_REVIEW);
        statusHavingNotification.add(EnumCustomerMappingStatus.CONTRACT_SIGNED);
        statusHavingNotification.add(EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED);
        statusHavingNotification.add(EnumCustomerMappingStatus.RESPONSE_SENT);
        statusHavingNotification.add(EnumCustomerMappingStatus.RESPONSE_REVIEW);
        statusHavingNotification.add(EnumCustomerMappingStatus.IPS_SHARED);
        statusHavingNotification.add(EnumCustomerMappingStatus.IPS_REVIEWED);
        statusHavingNotification.add(EnumCustomerMappingStatus.IPS_ACCEPTED);
        statusHavingNotification.add(EnumCustomerMappingStatus.REBALANCE_INITIATED);
        statusHavingNotification.add(EnumCustomerMappingStatus.ACTIVE);
        statusHavingNotification.add(EnumCustomerMappingStatus.SUSPENDED);
        statusHavingNotification.add(EnumCustomerMappingStatus.EXPIRED);
        return statusHavingNotification;
    }

    private boolean checkViewButtonDispStatus(int status) {
        return !(EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue() == status
                || EnumCustomerMappingStatus.RESPONSE_SENT.getValue() == status
                || EnumCustomerMappingStatus.IPS_REVIEWED.getValue() == status);
    }

    /**
     *
     * This method is used to load data for performance graph displayed in the
     * investor dash board
     *
     * @param customerId
     * @param noOfDaysTobebacked
     * @param benchMarkLineChartDataVOList - data for benchmark line chart
     * @param recommendedLineChartDataVOList- data for recommended line chart
     * @param selfLineChartDataVOList- data for self line chart
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getLineChartData(Integer customerId, Integer noOfDaysTobebacked, List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList, List<LineChartDataVO> selfLineChartDataVOList) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -noOfDaysTobebacked);
        String groupCondition = EMPTY_STRING;
        if (isMoreThanOneMonth(noOfDaysTobebacked)) {
            groupCondition = "MONTH(T1.`datetime`)";
        } else if (isMoreThanOneYear(noOfDaysTobebacked)) {
            groupCondition = "YEAR(T1.`datetime`)";
        } else {
            groupCondition = "T1.`datetime`";
        }

        //loading benchmark,recomended,self data
        List<Object> responseList = investorDashBoardDAO.getBenchMarkPerfomance(new Date(), cal.getTime(), customerId, groupCondition);
        List<Object> responseItems = (List<Object>) responseList.get(ZERO);

        //Creating line chart data for Benchmark performance
        buildLineChartdat(responseItems, benchMarkLineChartDataVOList, noOfDaysTobebacked);
        responseItems = (List<Object>) responseList.get(ONE);

        //Creating line chart data for Recomended performance
        buildLineChartdat(responseItems, recommendedLineChartDataVOList, noOfDaysTobebacked);
        responseItems = (List<Object>) responseList.get(2);

        //Creating line chart data for Self performance
        buildLineChartdat(responseItems, selfLineChartDataVOList, noOfDaysTobebacked);
    }

    /**
     * Creating data for building performance line chart
     *
     * @param responseItems
     * @param linechartVOList
     * @param noOfDaysTobebacked
     */
    private void buildLineChartdat(List<Object> responseItems, List<LineChartDataVO> linechartVOList, Integer noOfDaysTobebacked) {
        try {
            Double yaxisValue = 0.0;
            boolean first = true;
            for (Iterator<Object> it = responseItems.iterator(); it.hasNext();) {
                Object[] objArr = (Object[]) it.next();
                if (objArr[2] != null) {
                    LineChartDataVO lineChartDataVO = new LineChartDataVO();
                    Date dt = DateUtil.stringToDate(objArr[ONE].toString(), YYYY_MM_DD);

                    //Setting first value for line chart as the date when the portfolio is assigned to the investor
                    if (first) {
                        LineChartDataVO lineChartDataFirst = new LineChartDataVO();
                        Long time = (long) ZERO;
                        Date portfolioAssiigned = DateUtil.stringToDate(objArr[2].toString(), YYYY_MM_DD);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(portfolioAssiigned);
                        // time = portfolioAssiigned.getTime();
                        time = cal.getTimeInMillis() + 19820000;

                        lineChartDataFirst.setxValue(time.toString());
                        Double yValue = ZERO_POINT_ZERO;
                        yValue = Math.floor(yValue);
                        lineChartDataFirst.setyValue(yValue.toString());
                        linechartVOList.add(lineChartDataFirst);
                        yaxisValue = yValue;
                        // }
                        first = false;
                        cal.add(Calendar.DATE, -1);
                        Long start = cal.getTimeInMillis() + 19820000;
                        portfolioAssignedDate = start.toString();
                    }
                    Calendar calender = Calendar.getInstance();
                    calender.setTime(dt);
                    calender.setTimeZone(null);
                    // Long date = dt.getTime();
                    Long date = calender.getTimeInMillis() + 19820000;
                    lineChartDataVO.setxValue(date.toString());
                    Double yValue = ZERO_POINT_ZERO;
                    if (objArr[ZERO] != null) {
                        yValue = Double.parseDouble(objArr[ZERO].toString());
                    }
                    yValue = Math.floor(yValue);
                    if (yValue > yaxisValue) {
                        yaxisValue = yValue;
                    }
                    lineChartDataVO.setyValue(yValue.toString());
                    linechartVOList.add(lineChartDataVO);
                }
            }
            if (yaxisValue > maxYaxisValue) {
                maxYaxisValue = yaxisValue;
            }
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    private boolean isMoreThanOneMonth(Integer noOfdays) {
        return (noOfdays > THIRTY && noOfdays <= ONE_YEAR);
    }

    private boolean isMoreThanOneYear(Integer noOfdays) {
        return (noOfdays > ONE_YEAR);
    }

    /**
     * Checking Whether portfolio is modified by the advisor
     *
     * @param customerPortfolioTb - portfolio assigned to investor
     * @param advisorDetails
     * @param investorNotificationsList - notification to show on dashboard
     * @param investorNotificationsListPopup - notification to show on popup
     */
    private void setPortfolioChangeNotificaion(CustomerPortfolioTb customerPortfolioTb, AdvisorDetailsVO advisorDetails, List<InvestorNotificationsVO> investorNotificationsList,
            List<InvestorNotificationsVO> investorNotificationsListPopup) {
        boolean portfolioModified = customerPortfolioTb.getPortfolioModified() == null ? false : customerPortfolioTb.getPortfolioModified().booleanValue();
        if (portfolioModified) {
            advisorDetails.setFirstName(customerPortfolioTb.getMasterAdvisorTb().getFirstName());
            InvestorNotificationsVO pfChangeNotification = new InvestorNotificationsVO();
            pfChangeNotification.setAdvisorDetailsVO(advisorDetails);
            pfChangeNotification.setNotificationDate(customerPortfolioTb.getPortfolioTb().getLastUpdated());

            //Setting elapsed notification time 
            pfChangeNotification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(pfChangeNotification.getNotificationDate()));
            String cNotificationMessage = NotificationsLoader.getPropertiesValue(INVESTOR_PORTFOLIO_CHANGED);
            pfChangeNotification.setNotificationMessage(String.format(cNotificationMessage, advisorDetails.getFirstName()));
            pfChangeNotification.setButtonText(BTN_REPLICATE);
            pfChangeNotification.setEnumCustomerNotification(EnumCustomerNotification.PORTFOLIO_CHANGED);
            pfChangeNotification.setViewButtonDispStatus(true);
            investorNotificationsList.add(pfChangeNotification);
            if (!customerPortfolioTb.getPortfolioChangeViewed()) {
                investorNotificationsListPopup.add(pfChangeNotification);
            }
        }
    }

    /**
     * This method is used for checking rebalancing notification
     *
     * @param customerPortfolioTb - portfolio assigned to investor
     * @param advisorDetails
     * @param investorNotificationsList - notification to show on dashboard
     * @param investorNotificationsListPopup - notification to show on popup
     */
    private void setRebalanceRequiredNotification(CustomerPortfolioTb customerPortfolioTb, AdvisorDetailsVO advisorDetails, List<InvestorNotificationsVO> investorNotificationsList,
            List<InvestorNotificationsVO> investorNotificationsListPopup) {
        boolean rebalanceRequired = customerPortfolioTb.getRebalanceRequired() == null ? false : customerPortfolioTb.getRebalanceRequired();

        //Checking rebalancing required
        if (rebalanceRequired) {
            InvestorNotificationsVO rNotification = new InvestorNotificationsVO();
            rNotification.setAdvisorDetailsVO(advisorDetails);
            rNotification.setNotificationDate(customerPortfolioTb.getRebalanceReqdDate());

            //Setting elapsed notification time 
            rNotification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(rNotification.getNotificationDate()));
            String msg = String.format(NotificationsLoader.getPropertiesValue(INVESTOR_REBALANCE_REQD), customerPortfolioTb.getPortfolioTb().getPortfolioName());
            rNotification.setNotificationMessage(msg);
//            rNotification.setNotificationMessage(NotificationsLoader.getPropertiesValue(INVESTOR_REBALANCE_REQD));
            rNotification.setButtonText(BTN_VIEW);

            //rNotification.setRebalanceNotification(true);
            rNotification.setEnumCustomerNotification(EnumCustomerNotification.REBALANCE_REQUIRED);
            rNotification.setViewButtonDispStatus(true);

            if (customerPortfolioTb.getNoRebalanceStatus() != null && customerPortfolioTb.getNoRebalanceStatus() != true) {
                investorNotificationsList.add(rNotification);

                //Only those notifications that RebalanceViewed flag is false is displayed in popup
                if (customerPortfolioTb.getRebalanceViewed() == null || !customerPortfolioTb.getRebalanceViewed()) {
                    investorNotificationsListPopup.add(rNotification);
                }
            }
        }
    }

    private void setAdvisorDetailsVO(AdvisorDetailsVO advisorDetails, CustomerAdvisorMappingTb customerAdvisorMapping, EnumCustomerMappingStatus status) {
        advisorDetails.setRelationId(customerAdvisorMapping.getRelationId());
        advisorDetails.setAdvisorId(customerAdvisorMapping.getMasterAdvisorTb().getAdvisorId());
        StringBuilder name = new StringBuilder();
        name.append(customerAdvisorMapping.getMasterAdvisorTb().getFirstName()).append(SPACE)
                .append(customerAdvisorMapping.getMasterAdvisorTb().getMiddleName()).append(SPACE)
                .append(customerAdvisorMapping.getMasterAdvisorTb().getLastName());
        advisorDetails.setFirstName(name.toString());
        advisorDetails.setWorkOrganization(customerAdvisorMapping.getMasterAdvisorTb().getWorkOrganization());
        advisorDetails.setJobTitle(customerAdvisorMapping.getMasterAdvisorTb().getJobTitle());
        advisorDetails.setQualification(String.valueOf(customerAdvisorMapping.getMasterAdvisorTb()
                .getMasterAdvisorQualificationTb().getPrimaryQualificationDegree()));
        advisorDetails.setStatus(status.getValue());
        advisorDetails.setStatusValue(StringCaseConverter.toProperCase(status.toString()));
        advisorDetails.setLinkedInMemberid(customerAdvisorMapping.getMasterAdvisorTb().getMasterApplicantTb().getLinkedinMemberId());
        advisorDetails.setEmail(customerAdvisorMapping.getMasterAdvisorTb().getMasterApplicantTb().getEmail());
        advisorDetails.setRegId(customerAdvisorMapping.getMasterAdvisorTb().getMasterApplicantTb().getRegistrationId());
        advisorDetails.setPictureURL(customerAdvisorMapping.getMasterAdvisorTb().getMasterApplicantTb().getPictureUrl());
        advisorDetails.setLinkedInProfile(customerAdvisorMapping.getMasterAdvisorTb()
                .getMasterApplicantTb().getLinkedinProfileUrl() == null ? EMPTY_STRING
                : customerAdvisorMapping.getMasterAdvisorTb().getMasterApplicantTb().getLinkedinProfileUrl());
    }

    /**
     * This method is used for setting relation notification for investor based
     * on current relation status
     *
     * @param status - current relation status
     * @param customerAdvisorMapping
     * @param investorNotificationsList - notification to show on dashboard
     * @param investorNotificationsListPopup - notification to show on popup
     * @param advisorDetailsVO
     */
    private void setInvestorNotificationOnStatus(EnumCustomerMappingStatus status, CustomerAdvisorMappingTb customerAdvisorMapping, List<InvestorNotificationsVO> investorNotificationsList, List<InvestorNotificationsVO> investorNotificationsListPopup, AdvisorDetailsVO advisorDetailsVO) {
        InvestorNotificationsVO notification = new InvestorNotificationsVO();

        //Checking status in Status notification list
        if (getInvNotificationStatusList().contains(status)) {
            String notificationMsg = InvestorNotificationMsgService.getMessage(customerAdvisorMapping.getAdvisorRequest(), advisorDetailsVO.getStatus(), customerAdvisorMapping.getMasterAdvisorTb().getFirstName());
            notification.setNotificationMessage(notificationMsg);
            notification.setAdvisorDetailsVO(advisorDetailsVO);
            notification.setButtonText(BTN_VIEW);
            notification.setViewButtonDispStatus(checkViewButtonDispStatus(status.getValue()));
            notification.setNotificationDate(customerAdvisorMapping.getStatusDate());

            //Setting elapsed notification time 
            notification.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(notification.getNotificationDate()));
            investorNotificationsList.add(notification);
            
            //Only those notifications that investor not viewed is displayed in popup
            if (customerAdvisorMapping.getInvestorViewed() == null || !customerAdvisorMapping.getInvestorViewed()) {
                investorNotificationsListPopup.add(notification);
            }
            
            if (status.getValue() == FOUR_HOUNDRED) {
                // This notification is done for "View Orders".
                notification = new InvestorNotificationsVO();
                notificationMsg = "View today's order details";
                notification.setNotificationMessage(notificationMsg);
                notification.setAdvisorDetailsVO(advisorDetailsVO);
                notification.setButtonText("View Orders");
                notification.setViewButtonDispStatus(checkViewButtonDispStatus(status.getValue()));
                notification.setNotificationDate(customerAdvisorMapping.getStatusDate());
                investorNotificationsList.add(notification);
            }
        }
    }

    /**
     * Checking for rate advisor notification
     *
     * @param status - current relation status
     * @param customerAdvisorMapping
     * @param investorNotificationsList - notification to show on dashboard
     * @param investorNotificationsListPopup - notification to show on popup
     * @param advisorDetailsVO
     */
    private void setRateAdvisorNotification(EnumCustomerMappingStatus status, AdvisorDetailsVO advisorDetails, CustomerAdvisorMappingTb customerAdvisorMapping, List<InvestorNotificationsVO> investorNotificationsList,
            List<InvestorNotificationsVO> investorNotificationsListPopup) {
        // Set rate adviosr notification
        if (this.getRelationActiveStatuses().contains(status.getValue())
                && (customerAdvisorMapping.getRateAdvisorFlag() == null ? false : customerAdvisorMapping.getRateAdvisorFlag())) {
            InvestorNotificationsVO investorNotificationsVO = new InvestorNotificationsVO();
            investorNotificationsVO.setAdvisorDetailsVO(advisorDetails);
            investorNotificationsVO.setEnumCustomerNotification(EnumCustomerNotification.RATE_ADVISOR);
            investorNotificationsVO.setNotificationDate(customerAdvisorMapping.getRateAdvisorLastDate());

            //Setting elapsed notification time 
            investorNotificationsVO.setElapsedTimeMsg(DateUtil.getElapsedTimeMsg(investorNotificationsVO.getNotificationDate()));
            investorNotificationsVO.setNotificationMessage(NotificationsLoader.getPropertiesValue(INVESTOR_RATE_ADVISOR));
            investorNotificationsVO.setButtonText(RATE);
            investorNotificationsVO.setViewButtonDispStatus(true);
            investorNotificationsList.add(investorNotificationsVO);

            //Only those notifications that RateAdvisorViewed flag is false is displayed in popup
            if (customerAdvisorMapping.getRateAdvisorViewed() != null && !customerAdvisorMapping.getRateAdvisorViewed()) {
                investorNotificationsListPopup.add(investorNotificationsVO);
            }
        }
    }

    /**
     * This method is used to get all active status for investor and advisor
     *
     * @return list of active status for investor,advisor relation
     */
    private static List<Integer> getRelationActiveStatuses() {
        List<Integer> relationActiveStatus = new ArrayList<Integer>();
        relationActiveStatus.add(EnumAdvisorMappingStatus.CONTRACT_SIGNED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.RESPONSE_RECIEVED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.RESPONSE_REVIEW.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.IPS_SHARED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.IPS_CREATED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.IPS_REVIEWED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.IPS_ACCEPTED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.REBALANCE_INITIATED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.ACTIVE.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.SUSPENDED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.EXPIRED.getValue());
        relationActiveStatus.add(EnumAdvisorMappingStatus.ORDER_PLACED_IN_OMS.getValue());
        return relationActiveStatus;
    }

    public Boolean getPortfolioAssigned() {
        return portfolioAssigned;
    }

    public Double getMaxYavalue() {
        return maxYaxisValue;
    }

    public String portfolioAssignedDate() {
        return portfolioAssignedDate;
    }
}
