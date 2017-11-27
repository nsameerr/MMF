/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.COLON;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEVELOPMENT;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.HH_MM;
import static com.gtl.mmf.service.util.IConstants.MF_BUY;
import static com.gtl.mmf.service.util.IConstants.MF_SELL;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.MMF_VENUE_OFFLINE_ENABLED;
import static com.gtl.mmf.service.util.IConstants.NSEMF;
import static com.gtl.mmf.service.util.IConstants.REBALANCE_PAGE;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TIME_ALERT;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZEROD;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("rebalanceportfoliocontroller")
@Scope("view")
public class RebalancePortfolioController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.RebalancePortfolioController");
    private static final String RE_DIRECTION_OMS_LOGIN = "/pages/oms_login?faces-redirect=true";
    private CustomerPortfolioVO customerPortfolioVO;
    private List<PieChartDataVO> tragetPieChartDataVOList;
    private List<PieChartDataVO> customerPieChartDataVOList;
    private List<PortfolioDetailsVO> portfolioDetailsVOList;
    private String reDirectionUrl;
    private Integer userStatus;
    private Boolean rebalaance;
    private String msgRebalance;
    private boolean holiday_Venue;
    private String venue_Holiday_Msg = "Venue Holiday";

    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;
    Set<String> venueNameSet;
    boolean online;
    boolean offlinee_Enabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_VENUE_OFFLINE_ENABLED));
    private boolean allOflineOrder;
    private String oflineMsg;
    private OMSOrderVO omsOrderVO;
    private boolean placeOrder = false;
    private boolean showExecuteButton = false;
    private String ledger;
    private String buyinPower;
    private String additionalAmt;
    private boolean disableButton;
    private String timeAlertMessage;
    private boolean timeFlag = false;
    PortfolioSecurityVO portfolioSecurityVO;
    private List<PortfolioSecurityVO> portfoliosecurityVOlist;
    private List<PortfolioSecurityVO> portfoliosecurityVOlist1;
    private boolean allMFflag = false;

    /**
     * This method create initial data that displayed on the rebalance page
     *
     */
    @PostConstruct
    public void afterinit() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            userSessionBean.setFromURL(REBALANCE_PAGE);
            customerPortfolioVO = new CustomerPortfolioVO();
            tragetPieChartDataVOList = new ArrayList<PieChartDataVO>();
            customerPieChartDataVOList = new ArrayList<PieChartDataVO>();
            portfolioDetailsVOList = new ArrayList<PortfolioDetailsVO>();
            customerPortfolioVO.setCustomerId(userSessionBean.getUserId());
            portfoliosecurityVOlist = new ArrayList<PortfolioSecurityVO>();
            portfoliosecurityVOlist1 = new ArrayList<PortfolioSecurityVO>();
            //Loading all the securities for portfolio.
            //creating customer and target pie chart data
            rebalancePortfolioBAO.getPortfolioRebalancePageDetails(customerPortfolioVO, tragetPieChartDataVOList, customerPieChartDataVOList, portfolioDetailsVOList);
            rebalancePortfolioBAO.rebalancePortfolio(customerPieChartDataVOList, portfolioDetailsVOList, customerPortfolioVO);
            userStatus = userSessionBean.getRelationStatus() == null ? ZERO : userSessionBean.getRelationStatus();
            holiday_Venue = LookupDataLoader.isHolyday_NSE() && LookupDataLoader.isHolyday_BSE();

            /* calcultae the weights */
            updateMarketData();
            if (session.getAttribute("sinkBp") != null && (Boolean) session.getAttribute("sinkBp")) {
                ledger = customerPortfolioVO.getCashAmount().toString();
                buyinPower = customerPortfolioVO.getBuyingPower().toString();
                Float amount = customerPortfolioVO.getBuyingPower() - customerPortfolioVO.getCashAmount();
                additionalAmt = amount.toString();
            } else {
                ledger = customerPortfolioVO.getCashAmount().toString();
                buyinPower = "--";
                additionalAmt = "--";
            }
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        portfoliosecurityVOlist1 = updateSecurityList(customerPortfolioVO);
        if (portfoliosecurityVOlist1.size() != 0) {
            Calendar cal = Calendar.getInstance();
            System.out.println("Time" + cal.getTime());
            String datetime = DateUtil.dateToString(cal.getTime(), HH_MM);//TimeWed Jan 18 15:08:01 IST 2017
            String[] currentTime = datetime.split(":");
            Integer time_hour = Integer.parseInt(currentTime[0]);
            Integer time_minute = Integer.parseInt(currentTime[1]);
            if (time_hour >= 15) {
                timeFlag = true;
                timeAlertMessage = TIME_ALERT;
                allMFflag = this.checkForSecurityType(customerPortfolioVO);
            }
        }
    }

    /**
     * This method is executed when user press execute button for re-balancing.
     * this place order in the OMS
     */
    public void execute() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        customerPortfolioVO.setFromAddReedem(false);
        /*
         Controlling oms login using flag.
         if flag is true order is place in OMS otherwise bypass OMS
         */
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        if (!environment.equalsIgnoreCase(DEVELOPMENT)) {
            // Setting Seesion variable for understanding the oreder is from rebalanceing     
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, false);
            if (session.getAttribute("OMSOrderVO") != null) {
                reDirectionUrl = RE_DIRECTION_OMS_LOGIN;
            } else {
                reDirectionUrl = RE_DIRECTION_OMS_LOGIN;
            }
        } else {
            //creating Dummy OMS order  
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            placeOrder = false;
            omsOrderVO = new OMSOrderVO();
            omsOrderVO.setOmsUserid(userSessionBean.getOmsLoginId() != null ? userSessionBean.getOmsLoginId() : "Dummy_" + customerPortfolioVO.getCustomerId());
            customerPortfolioVO.setOmsLoginId(omsOrderVO.getOmsUserid());

            // Sending order for execution  
            rebalancePortfolioBAO.executeSecurityOrders(customerPortfolioVO, portfolioDetailsVOList, omsOrderVO, true);
            userSessionBean.setRelationId(customerPortfolioVO.getRelationId() == null ? ZERO : customerPortfolioVO.getRelationId());
            userSessionBean.setRelationStatus(FOUR_HOUNDRED);
            session.setAttribute(USER_SESSION, userSessionBean);
            reDirectionUrl = "/pages/investordashboard?faces-redirect=true";
        }

    }

    /**
     * This method is used to update Customer portfolio details with market date
     * 1.This will Update security price for each security in customer portfolio
     * 2.Update total weight of allocated fund utilized by each asset class
     * 3.Calculate new transaction details
     */
    private void updateMarketData() {
        try {
            // insted of cashweight currentweight is used to disply in rebalance page
            PortfolioUtil.updateCustomerPortfolioMarketPrice(customerPortfolioVO);
            PortfolioUtil.updateValueAndWeight(customerPortfolioVO);
            customerPortfolioVO.setTotalTransAmt(ZEROD);
//            PortfolioUtil.updateTransactionDetails(customerPortfolioVO);
            disableButton = PortfolioUtil.updateTransactionDetailsConsideringAsset(customerPortfolioVO);
            venueNameSet = customerPortfolioVO.getVenuName();
            checkForEnableButton();
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * Updating transaction data with current market data
     */
    public void updateCMP() {
        updateMarketData();
    }

    /**
     * Creating Json string data for displaying pie chart
     *
     * @return Json string
     */
    public String targetPieChartData() {
        return PortfolioAllocationChartUtil.generatePieChartData(tragetPieChartDataVOList);
    }

    /**
     * Creating Json string data for displaying pie chart
     *
     * @return Json string
     */
    public String recommendedPieChartData() {
        return PortfolioAllocationChartUtil.generatePieChartData(customerPieChartDataVOList);
    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOList() {
        return portfolioDetailsVOList;
    }

    public String getreDirectionUrl() {
        return reDirectionUrl;
    }

    private boolean isrefreshOrderDetails(CustomerPortfolioVO customerPortfolioVO) {
        try {
            Date cur_date = new Date();
            Date last_exe_date = customerPortfolioVO.getLastExeDate() == null ? new Date()
                    : customerPortfolioVO.getLastExeDate();
            cur_date = DateUtil.stringToDate(
                    DateUtil.dateToString(cur_date, DD_SLASH_MM_SLASH_YYYY),
                    DD_SLASH_MM_SLASH_YYYY);
            last_exe_date = DateUtil.stringToDate(DateUtil.dateToString(
                    last_exe_date, DD_SLASH_MM_SLASH_YYYY),
                    DD_SLASH_MM_SLASH_YYYY);
            return cur_date.after(last_exe_date);
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            return Boolean.FALSE;
        }
    }

    public String cashTextName() {
        return TEXT_CASH;
    }

    /**
     * checking for re-balance execution . re-balance execution is allowed only
     * once in a day and execute button is disabling after order is placed
     *
     * @return boolean true of order is not placed already
     */
    public boolean isexecuteportfolio() {
        if ((customerPortfolioVO.getRebalanceRequired() != null && customerPortfolioVO
                .getRebalanceRequired() == true)
                || (customerPortfolioVO.getPortfolioModified() != null && customerPortfolioVO
                .getPortfolioModified() == true)) {
            rebalaance = false;
            if (userStatus != FOUR_HOUNDRED.intValue()) {
                rebalaance = true;
                msgRebalance = "Press Execute button to place orders in trading system";
                return true;
            }
        }
        if (userStatus == FOUR_HOUNDRED.intValue()) {
            msgRebalance = "Order already placed in trading system for the day";
        } else if (!showExecuteButton) {
            msgRebalance = "No change identified in portfolio";
            return false;
        } else if (disableButton) {
            msgRebalance = "No change identified in portfolio";
        } else {
            rebalaance = true;
            msgRebalance = "Press Execute button to place orders in trading system";
            return true;
        }
        return false;

    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Boolean getRebalaance() {
        return rebalaance;
    }

    public void setRebalaance(Boolean rebalaance) {
        this.rebalaance = rebalaance;
    }

    public String getMsgRebalance() {
        return msgRebalance;
    }

    public void setMsgRebalance(String msgRebalance) {
        this.msgRebalance = msgRebalance;
    }

    public boolean isHoliday_Venue() {
        return holiday_Venue;
    }

    public void setHoliday_Venue(boolean holiday_Venue) {
        this.holiday_Venue = holiday_Venue;
    }

    public String getVenue_Holiday_Msg() {
        return venue_Holiday_Msg;
    }

    private boolean getOnlineStatus() {
        boolean onlineStatus = true;
        boolean status = true;
        allOflineOrder = false;
        int count = 0;
        StringBuilder oflineVenues = new StringBuilder();
        if (venueNameSet != null) {
            for (String venue : venueNameSet) {
                if (!LookupDataLoader.getVenueStatusMap().get(venue)) {
                    count++;
                    oflineVenues.append(venue).append(SPACE);
                    status = false;
                } else {
                    try {
                        String[] venueTime = LookupDataLoader.getVenueTimeMap().get(venue).split("\\|");
                        String startTime = venueTime[0];
                        String closeTime = venueTime[1];
                        SimpleDateFormat parser = new SimpleDateFormat(HH_MM);
                        Date Start = parser.parse(startTime);
                        Date close = parser.parse(closeTime);
                        SimpleDateFormat localDateFormat = new SimpleDateFormat(HH_MM);
                        String time = localDateFormat.format(new Date());
                        Date current = parser.parse(time);
                        if (current.before(Start) || current.after(close)) {
                            count++;
                            oflineVenues.append(venue).append(SPACE);
                            status = false;
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(OMSLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            oflineMsg = "Offline venues:- " + oflineVenues.toString();
            if (venueNameSet.size() == count) {
                allOflineOrder = true;
                onlineStatus = false;//all venues are offline
                oflineMsg = "All exchanges are offline.";
            } else {
                //some of the venues are online
                onlineStatus = true;
            }
        }
        return onlineStatus;
    }

    //User allowed to place both online and offline order the exchnage
    public void placeOfflineYes() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        rebalancePortfolioBAO.executeSecurityOrders(customerPortfolioVO,
                portfolioDetailsVOList, omsOrderVO, true);
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        userSessionBean.setRelationId(customerPortfolioVO.getRelationId() == null ? ZERO : customerPortfolioVO.getRelationId());
        userSessionBean.setRelationStatus(FOUR_HOUNDRED);
        session.setAttribute(USER_SESSION, userSessionBean);
        reDirectionUrl = "/pages/investordashboard?faces-redirect=true";

    }

    //User only allowed to place online order
    public void placeOfflineNo() {
        reDirectionUrl = "/pages/investordashboard?faces-redirect=true";
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public String getReDirectionUrl() {
        return reDirectionUrl;
    }

    public void setReDirectionUrl(String reDirectionUrl) {
        this.reDirectionUrl = reDirectionUrl;
    }

    public boolean isOfflinee_Enabled() {
        return offlinee_Enabled;
    }

    public void setOfflinee_Enabled(boolean offlinee_Enabled) {
        this.offlinee_Enabled = offlinee_Enabled;
    }

    public boolean isAllOflineOrder() {
        return allOflineOrder;
    }

    public void setAllOflineOrder(boolean allOflineOrder) {
        this.allOflineOrder = allOflineOrder;
    }

    public String getOflineMsg() {
        return oflineMsg;
    }

    public void setOflineMsg(String oflineMsg) {
        this.oflineMsg = oflineMsg;
    }

    public boolean isPlaceOrder() {
        return placeOrder;
    }

    public void setPlaceOrder(boolean placeOrder) {
        this.placeOrder = placeOrder;
    }

    private boolean checkForEnableButton() {
        showExecuteButton = false;
        for (PortfolioDetailsVO portfolioDetailsVO : customerPortfolioVO.getPortfolioDetailsVOs()) {
            for (PortfolioSecurityVO securityVO : portfolioDetailsVO.getSecurities()) {
                if (securityVO.getBuysellStatusText()
                        .equalsIgnoreCase(BuySell.SELL.toString())
                        || securityVO.getBuysellStatusText()
                        .equalsIgnoreCase(BuySell.BUY.toString())) {
                    showExecuteButton = true;
                    break;
                }
                if (showExecuteButton) {
                    break;
                }
            }
            if (showExecuteButton) {
                break;
            }
        }
        return showExecuteButton;
    }

    public String getLedger() {
        return ledger;
    }

    public void setLedger(String ledger) {
        this.ledger = ledger;
    }

    public String getBuyinPower() {
        return buyinPower;
    }

    public void setBuyinPower(String buyinPower) {
        this.buyinPower = buyinPower;
    }

    public String getAdditionalAmt() {
        return additionalAmt;
    }

    public void setAdditionalAmt(String additionalAmt) {
        this.additionalAmt = additionalAmt;
    }

    public boolean isDisableButton() {
        return disableButton;
    }

    public void setDisableButton(boolean disableButton) {
        this.disableButton = disableButton;
    }

    public String getTimeAlertMessage() {
        return timeAlertMessage;
    }

    public void setTimeAlertMessage(String timeAlertMessage) {
        this.timeAlertMessage = timeAlertMessage;
    }

    public boolean isTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(boolean timeFlag) {
        this.timeFlag = timeFlag;
    }

    private List<PortfolioSecurityVO> updateSecurityList(CustomerPortfolioVO customerPortfolioVO) {
        portfoliosecurityVOlist = new ArrayList<PortfolioSecurityVO>();
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioDetailsVO portfolioDetailsVO : customerPortfolioVO.getPortfolioDetailsVOs()) {
            portfolioSecurityVOs.addAll(portfolioDetailsVO.getSecurities());
        }
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            if (portfolioSecurityVO.getVenueCode().equalsIgnoreCase(NSEMF)) {
                if (portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(MF_BUY)
                        || portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(MF_SELL)) {
                    portfoliosecurityVOlist.add(portfolioSecurityVO);
                }
            } else {
                allMFflag = false;
                break;
            }
        }
        return portfoliosecurityVOlist;
    }

    public boolean isAllMFflag() {
        return allMFflag;
    }

    public void setAllMFflag(boolean allMFflag) {
        this.allMFflag = allMFflag;
    }

//Hide execute button after 3pm if all securities are mutual fund
    private boolean checkForSecurityType(CustomerPortfolioVO customerPortfolioVO) {
        boolean mfFlag = true;
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioDetailsVO portfolioDetailsVO : customerPortfolioVO.getPortfolioDetailsVOs()) {
            portfolioSecurityVOs.addAll(portfolioDetailsVO.getSecurities());
        }
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            if (!portfolioSecurityVO.getVenueCode().equalsIgnoreCase(NSEMF)) {
                mfFlag = false;
                break;
            }
        }
        return mfFlag;
    }

}
