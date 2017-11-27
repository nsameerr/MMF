package com.gtl.mmf.controller;

import com.git.oms.api.frontend.message.report.response.ResultSet;
import com.gtl.mmf.bao.IAdvisorDashBoardBAO;
import com.gtl.mmf.bao.IAdvisorMappingBAO;
import com.gtl.mmf.bao.IInvestorDashBoardBAO;
import com.gtl.mmf.bao.IInvestorMappingBAO;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.bao.IUserLogoutBAO;
import static com.gtl.mmf.service.util.IConstants.ADMIN_USER;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_ROOT;
import static com.gtl.mmf.service.util.IConstants.DAY_VALUE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.FIVE;
import static com.gtl.mmf.service.util.IConstants.FROM_DASHBOARD;
import static com.gtl.mmf.service.util.IConstants.FROM_LINKEDINCONNECTED;
import static com.gtl.mmf.service.util.IConstants.GEOJIT_FUND_TRANSFER_URL;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_CONNECTED;
import static com.gtl.mmf.service.util.IConstants.LINKED_IN_URL;
import static com.gtl.mmf.service.util.IConstants.MMF_OMS_ENABLED;
import static com.gtl.mmf.service.util.IConstants.NOTIFICATION;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.REBALANCE;
import static com.gtl.mmf.service.util.IConstants.REJECTION_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.THREESIXTYFIVE;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.TODAYS_ORDER;
import static com.gtl.mmf.service.util.IConstants.TODAYS_TRADE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.AdminNotificationVO;
import com.gtl.mmf.service.vo.AdvisorNotificationsVO;
import com.gtl.mmf.service.vo.InvestorNotificationsVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.NotificationAdminListVO;
import com.gtl.mmf.service.vo.NotificationAdvisorListVO;
import com.gtl.mmf.service.vo.NotificationInvesterListVo;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.webapp.util.NotificationRedirect;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("headerController")
@Scope("view")
public class HeaderController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.HeaderController");
    private static final String EMAILID = "emailId";
    @Autowired
    private IUserLogoutBAO userLogoutBAO;
    @Autowired
    private IAdvisorDashBoardBAO advisorDashBoardBAO;
    List<AdvisorNotificationsVO> advisorNotifications;
    List<AdvisorNotificationsVO> advisorNotificationsPopup;
    private boolean advisorUser;
    private int advisorId;
    private int clientId;
    private String redirectToINV = EMPTY_STRING;
    private String redirectToADV = EMPTY_STRING;
    @Autowired
    private IInvestorDashBoardBAO investorDashBoardBAO;
    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;
    @Autowired
    private IAdvisorMappingBAO advisorMappingBAO;
    @Autowired
    private IInvestorMappingBAO investorMappingBAO;

    List<InvestorNotificationsVO> investorNotifications;
    List<InvestorNotificationsVO> investorNotificationspoup;

    NotificationRedirect notificationRedirect = new NotificationRedirect();
    private List<LineChartDataVO> benchMarkLineChartDataVOList;
    private List<LineChartDataVO> recommendedLineChartDataVOList;
    private List<LineChartDataVO> selfLineChartDataVOList;
    private String benchMarkLineChartData = "[]";
    private String recommendedLineChartData = "[]";
    private String selfLineChartData = "[]";
    private String userProfileNav;
    private String jsonToolTipFormat;
    private Integer nodays;
    private String jsonCategory;
    private String jsonDateFormat;
    String userName;
    private Long fivedays;
    private Long oneMonth;
    private Long threeMonth;
    private Long sixMonth;
    private Long oneYear;
    private Long fiveYear;
    private Long tenYear;
    private String redirectPath;
    String email;
    boolean linkedInConnected;
    boolean linkedInConnectedSuccess;
    private final String url = PropertiesLoader.getPropertiesValue(LINKED_IN_URL).replaceAll(CONTEXT_ROOT, LookupDataLoader.getContextRoot());
    private String redirectTo;
    private String redirectToADM = EMPTY_STRING;
    private final String fundTransferUrl = PropertiesLoader.getPropertiesValue(GEOJIT_FUND_TRANSFER_URL);
    private String omsUserId;
    List<AdminNotificationVO> adminNotifications;
    List<AdminNotificationVO> adminNotificationspoup;
    private static final String NAVIGATION_PAGE = "/pages/investordashboard?faces-redirect=true";
    

    @PostConstruct
    public void afterinit() {

        benchMarkLineChartDataVOList = new ArrayList<LineChartDataVO>();
        recommendedLineChartDataVOList = new ArrayList<LineChartDataVO>();
        selfLineChartDataVOList = new ArrayList<LineChartDataVO>();
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        UserSessionBean userSessionBean = (UserSessionBean) externalContext.getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            userName = userSessionBean.getFirstName();
            linkedInConnected = userSessionBean.isLinkedInConnected();
            email = userSessionBean.getEmail();
            if (!userSessionBean.getUserType().equals(ADMIN_USER)) {
                this.advisorId = userSessionBean.getUserId();
                this.clientId = userSessionBean.getUserId();
                if (userSessionBean.getUserType().equals(ADVISOR)) {
                    NotificationAdvisorListVO advisorNotificationsvo = advisorDashBoardBAO.getAdvisorNotifications(advisorId);
                    setAdvisorNotifications(advisorNotificationsvo.getNotifications());

                    // Setting notification for showing in popup
                    setAdvisorNotificationsPopup(advisorNotificationsvo.getNotificationsPopup());
                    this.advisorUser = true;
                } else {
                    NotificationInvesterListVo investerListVo = investorDashBoardBAO.getInvestorNotifications(clientId);
                    setInvestorNotifications(investerListVo.getNotifications());
                    // Setting notification for showing in popup
                    setInvestorNotificationspoup(investerListVo.getNotificationsPopup());
                    clearLinchartList();
                    investorDashBoardBAO.getLineChartData(clientId, FIVE, benchMarkLineChartDataVOList,
                            recommendedLineChartDataVOList, selfLineChartDataVOList);
                    buildLineChartdata(benchMarkLineChartDataVOList, recommendedLineChartDataVOList, selfLineChartDataVOList);
                    setTolltipdateFormat();
                    this.setOmsUserId(userSessionBean.getOmsLoginId());
                }
            } else {
                NotificationAdminListVO adminListVO = userLogoutBAO.getAdminNotifications();
                setAdminNotifications(adminListVO.getNotifications());
                // Setting notification for showing in popup
                setAdminNotificationspoup(adminListVO.getNotificationsPopup());
            }
        }
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext
                .getCurrentInstance().getExternalContext().
                getSessionMap().get(STORED_VALUES);

        if (storedValues != null) {
            if (storedValues.get(FROM_LINKEDINCONNECTED) != null) {
                linkedInConnectedSuccess = (Boolean) storedValues.get(FROM_LINKEDINCONNECTED);
            }
        }
        if (!linkedInConnected) {
            this.connectLinkedIN();
        }
    }

    public String logout() throws IOException {
        String redirectPage = "/index.xhtml";
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context
                .getExternalContext().getRequest();
        HttpSession session = request.getSession(false);
        UserSessionBean userSessionBean = (UserSessionBean) session
                .getAttribute(USER_SESSION);
        if (userSessionBean != null) {
            if (userSessionBean.getUserType().equalsIgnoreCase(ADMIN_USER)) {
                redirectPage = "/pages/admin/index";
                LOGGER.info("Admin logged out status - success");
            } else {
                //logging out from OMS if already logged in.
                if (session.getAttribute("OMSOrderVO") != null) {
                    OMSOrderVO omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
                    rebalancePortfolioBAO.omslogout(omsOrderVO);
                }
                int userLogout = userLogoutBAO.userLogout(userSessionBean.getUserType(), userSessionBean.getUserId());
                redirectPage = "/index";
                LOGGER.log(Level.INFO, "{0} logged out update rows {1}", new Object[]{userSessionBean.getUserType(), userLogout});
            }
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove(STORED_VALUES);
        return redirectPage + "?faces-redirect=true";
    }

    public void onClickReturnDays() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Integer noOfdaysToBeBacked = params.get(DAY_VALUE) == null ? ZERO : Integer.parseInt(params.get(DAY_VALUE));
        clearLinchartList();

        investorDashBoardBAO.getLineChartData(clientId, noOfdaysToBeBacked, benchMarkLineChartDataVOList,
                recommendedLineChartDataVOList, selfLineChartDataVOList);
        buildLineChartdata(benchMarkLineChartDataVOList, recommendedLineChartDataVOList, selfLineChartDataVOList);
        //setTolltipdateFormat(noOfdaysToBeBacked);
    }

    public void doActionRedirectToINV() {
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String[] parms = requestParameterMap
                .get("index").split("_");
        int selectedNotificationIndex = Integer.parseInt(parms[ZERO]);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader
                .getPropertiesValue(MMF_OMS_ENABLED));
        Integer relationId = null;
        InvestorNotificationsVO selectedNotification = investorNotifications
                .get(selectedNotificationIndex);
        relationId = selectedNotification.getAdvisorDetailsVO().getRelationId();
        int advisorid = selectedNotification.getAdvisorDetailsVO().getAdvisorId();
        /*  checking for notification wheather it is from popup or dashboard*/
        if (parms[ONE].equalsIgnoreCase("true")) {
            redirectToINV = notificationRedirect.onSelectNotificationINV(
                    investorNotificationspoup, selectedNotificationIndex, omsEnabled);
        } else {
            redirectToINV = notificationRedirect.onSelectNotificationINV(
                    investorNotifications, selectedNotificationIndex, omsEnabled);
        }

        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        if (relationId != null) {

            //Checking contract signing notifications
            if (storedValues.get(NOTIFICATION) != null && (Boolean) storedValues.get(NOTIFICATION) == true) {
                investorMappingBAO.updateNotificationStatus(relationId);
                storedValues.remove(NOTIFICATION);
            } else if (storedValues.get(REBALANCE) != null && (Boolean) storedValues.get(REBALANCE) == true) {

                //Checking  rebalance notifications
                investorMappingBAO.updateNotificationStatusRebalance(relationId, advisorid);
                storedValues.remove(REBALANCE);
            } else if (storedValues.get("rateAdvisor") != null && (Boolean) storedValues.get("rateAdvisor") == true) {

                //Checking rate advisor notifications
                investorMappingBAO.updateNotificationStatusRateAdvisor(relationId);
                storedValues.remove("rateAdvisor");
            }
        }
    }

    public void doActionRedirectToADV() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String[] parms = requestParameterMap.get("index").split("_");
        int selectedNotificationIndex = Integer.parseInt(parms[ZERO]);
        AdvisorNotificationsVO selectedNotification = null;
        if (parms[ONE].equalsIgnoreCase("true")) {
            redirectToADV = notificationRedirect.onSelectNotificationADV(advisorNotificationsPopup, selectedNotificationIndex);
            selectedNotification = advisorNotificationsPopup.get(selectedNotificationIndex);
        }

        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);

        //Checking contract signing notifications
        if (storedValues.get(NOTIFICATION) != null && (Boolean) storedValues.get(NOTIFICATION) == true) {
            if (selectedNotification.getInvestorDetailsVO().getRelationId() != null) {
                Integer relationId = selectedNotification.getInvestorDetailsVO().getRelationId();
                advisorMappingBAO.updateNotificationStatus(relationId);
                storedValues.remove(NOTIFICATION);
            }
        } else if (storedValues.get(REBALANCE) != null && (Boolean) storedValues.get(REBALANCE) == true) {

            //Checking  rebalance notifications
            if (selectedNotification.getPortfolioId() != null) {
                Integer portfolioId = selectedNotification.getPortfolioId();
                advisorMappingBAO.updateNotificationStatusForRebalance(portfolioId);
            }
        }

    }

    public void onClickLineChart() {
        if (investorDashBoardBAO.getPortfolioAssigned()) {
            redirectToINV = "/pages/portfolio_details?faces-redirect=true";
        }
    }

    public void onClickViewAllNotificationINV() {
        redirectToINV = "/pages/investordashboard?faces-redirect=true";
    }

    public void onClickViewAllNotificationADV() {
        redirectToADV = "/pages/advisordashboard?faces-redirect=true";
    }

    public void onClickPersonalInfo() {
        if (advisorUser) {
            userProfileNav = "/pages/advisor_profile?faces-redirect=true";
        } else {
            userProfileNav = "/pages/user_profile?faces-redirect=true";
        }
    }

    public void onClickChangePassword() {
        userProfileNav = "/pages/change_password?faces-redirect=true";
    }

    public String navPersonalInfo() {
        return userProfileNav;
    }

    public String navChangePassword() {
        return userProfileNav;
    }

    public String getRedirectToINV() {
        return redirectToINV;
    }

    public String getRedirectToADV() {
        return redirectToADV;
    }

    public boolean isAdvisorUser() {
        return advisorUser;
    }

    public void setAdvisorUser(boolean advisorUser) {
        this.advisorUser = advisorUser;
    }

    public List<AdvisorNotificationsVO> getAdvisorNotifications() {
        return advisorNotifications;
    }

    public void setAdvisorNotifications(List<AdvisorNotificationsVO> advisorNotifications) {
        this.advisorNotifications = advisorNotifications;
    }

    public List<InvestorNotificationsVO> getInvestorNotifications() {
        return investorNotifications;
    }

    public void setInvestorNotifications(List<InvestorNotificationsVO> investorNotifications) {
        this.investorNotifications = investorNotifications;
    }

    public List<LineChartDataVO> getBenchMarkLineChartDataVOList() {
        return benchMarkLineChartDataVOList;
    }

    public void setBenchMarkLineChartDataVOList(List<LineChartDataVO> benchMarkLineChartDataVOList) {
        this.benchMarkLineChartDataVOList = benchMarkLineChartDataVOList;
    }

    public List<LineChartDataVO> getRecommendedLineChartDataVOList() {
        return recommendedLineChartDataVOList;
    }

    public void setRecommendedLineChartDataVOList(List<LineChartDataVO> recommendedLineChartDataVOList) {
        this.recommendedLineChartDataVOList = recommendedLineChartDataVOList;
    }

    public List<LineChartDataVO> getSelfLineChartDataVOList() {
        return selfLineChartDataVOList;
    }

    public void setSelfLineChartDataVOList(List<LineChartDataVO> selfLineChartDataVOList) {
        this.selfLineChartDataVOList = selfLineChartDataVOList;
    }

    public String getBenchMarkLineChartData() {
        return benchMarkLineChartData;
    }

    public String getRecommendedLineChartData() {
        return recommendedLineChartData;
    }

    public String getSelfLineChartData() {
        return selfLineChartData;
    }

    private void buildLineChartdata(List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList, List<LineChartDataVO> selfLineChartDataVOList) {
        benchMarkLineChartData = PortfolioAllocationChartUtil.generateLineChartData(benchMarkLineChartDataVOList);
        recommendedLineChartData = PortfolioAllocationChartUtil.generateLineChartData(recommendedLineChartDataVOList);
        selfLineChartData = PortfolioAllocationChartUtil.generateLineChartData(selfLineChartDataVOList);
    }

    private void clearLinchartList() {
        benchMarkLineChartDataVOList.clear();
        recommendedLineChartDataVOList.clear();
        selfLineChartDataVOList.clear();
    }

    private void setTolltipdateFormat() {
        String toolTipFormat;
        Long portfolioAssigned;
        int max = Math.max(benchMarkLineChartDataVOList.size(), Math.max(recommendedLineChartDataVOList.size(), selfLineChartDataVOList.size()));
        String category = null;
        String dateFormat;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (max <= THREESIXTYFIVE) {
            category = "month";
            nodays = ONE;
            dateFormat = "%Y/%b";
        } else {
            int year = max / THREESIXTYFIVE;
            if (year > FIVE) {
                category = "year";
                dateFormat = "%Y";
                nodays = ONE;
            } else {
                category = "month";
                nodays = FIVE;
                dateFormat = "%Y/%b";
            }

        }

        portfolioAssigned = new Date().getTime();
        if (investorDashBoardBAO.portfolioAssignedDate() != null) {
            portfolioAssigned = Long.valueOf(investorDashBoardBAO.portfolioAssignedDate());
        }

        //Calculating Starting date of performance graph. By default it is portfolio assigned date
        long currentTime = new Date().getTime();
        cal.add(Calendar.DATE, -5);
        fivedays = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -1);
        oneMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -3);
        threeMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.MONTH, -6);
        sixMonth = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -1);
        oneYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -5);
        fiveYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        cal.add(Calendar.YEAR, -10);
        tenYear = (cal.getTimeInMillis() + 19820000) > portfolioAssigned ? (cal.getTimeInMillis() + 19820000) : portfolioAssigned;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            jsonCategory = objectMapper.writeValueAsString(category);
            jsonDateFormat = objectMapper.writeValueAsString(dateFormat);
        } catch (IOException e) {
            LOGGER.severe(StackTraceWriter.getStackTrace(e));

        }
    }

    public String getJsonToolTipFormat() {
        return jsonToolTipFormat;
    }

    public void setJsonToolTipFormat(String jsonToolTipFormat) {
        this.jsonToolTipFormat = jsonToolTipFormat;
    }

    public Integer getNodays() {
        return nodays;
    }

    public void setNodays(Integer nodays) {
        this.nodays = nodays;
    }

    public List<AdvisorNotificationsVO> getAdvisorNotificationsPopup() {
        return advisorNotificationsPopup;
    }

    public void setAdvisorNotificationsPopup(List<AdvisorNotificationsVO> advisorNotificationsPopup) {
        this.advisorNotificationsPopup = advisorNotificationsPopup;
    }

    public List<InvestorNotificationsVO> getInvestorNotificationspoup() {
        return investorNotificationspoup;
    }

    public void setInvestorNotificationspoup(List<InvestorNotificationsVO> investorNotificationspoup) {
        this.investorNotificationspoup = investorNotificationspoup;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getJsonCategory() {
        return jsonCategory;
    }

    public void setJsonCategory(String jsonCategory) {
        this.jsonCategory = jsonCategory;
    }

    public String getJsonDateFormat() {
        return jsonDateFormat;
    }

    public void setJsonDateFormat(String jsonDateFormat) {
        this.jsonDateFormat = jsonDateFormat;
    }

    public Double getMaxYavalue() {
        return investorDashBoardBAO.getMaxYavalue() == null
                ? 0.0 : investorDashBoardBAO.getMaxYavalue();
    }

    public String getPortfolioAssignedDate() {
        return investorDashBoardBAO.portfolioAssignedDate() == null
                ? String.valueOf(new Date().getTime())
                : investorDashBoardBAO.portfolioAssignedDate();
    }

    public String getPortfolioDate() {
        if (investorDashBoardBAO.portfolioAssignedDate() != null) {
            long time = Long.parseLong(investorDashBoardBAO.portfolioAssignedDate());
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(time);
            cal.add(Calendar.MONTH, 1);

            // Subtracting 5 hr from date to make it to local time
            Long start = cal.getTimeInMillis() - 19820000;
            return start.toString();
        }
        return String.valueOf(new Date().getTime());
    }

    public Long getFivedays() {
        return fivedays;
    }

    public Long getOneMonth() {
        return oneMonth;
    }

    public Long getThreeMonth() {
        return threeMonth;
    }

    public Long getSixMonth() {
        return sixMonth;
    }

    public Long getOneYear() {
        return oneYear;
    }

    public Long getFiveYear() {
        return fiveYear;
    }

    public Long getTenYear() {
        return tenYear;
    }

    public void connectLinkedIN() {
        ExternalContext ec = FacesContext.getCurrentInstance()
                .getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        if (storedValues != null) {
            storedValues.put(IS_ADVISOR, advisorUser);
            storedValues.put(EMAILID, this.email);
            storedValues.put(EXPIRE_IN, false);
            storedValues.put(LINKEDIN_CONNECTED, linkedInConnected);
            storedValues.put(FROM_DASHBOARD, true);
            sessionMap.put(STORED_VALUES, storedValues);
        }
    }

    public String navConnectLinkedIn() {
        return redirectPath;
    }

    public String getUserProfileNav() {
        return userProfileNav;
    }

    public void setUserProfileNav(String userProfileNav) {
        this.userProfileNav = userProfileNav;
    }

    public String getUrl() {
        return url;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public boolean isLinkedInConnectedSuccess() {
        return linkedInConnectedSuccess;
    }

    public void setLinkedInConnectedSuccess(boolean linkedInConnectedSuccess) {
        this.linkedInConnectedSuccess = linkedInConnectedSuccess;
    }

    public void hideDialogLinkedinSuccess() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext
                .getCurrentInstance().getExternalContext().
                getSessionMap().get(STORED_VALUES);
        if (storedValues.get(FROM_LINKEDINCONNECTED) != null) {
            storedValues.remove(FROM_LINKEDINCONNECTED);
        }
        linkedInConnectedSuccess = false;
    }

    public void onClickTodaysOrder() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        if (omsEnabled) {
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, true);
            session.setAttribute(TODAYS_ORDER, true);
            session.setAttribute(TODAYS_TRADE, false);
            session.setAttribute("tradeBreakdown", false);
            session.setAttribute(PORTFOLIO_SUMMARY, false);
            session.setAttribute(REJECTION_SUMMARY, false);
            if (session.getAttribute("OMSOrderVO") != null) {
                redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
            } else {
                redirectTo = "/pages/oms_login?faces-redirect=true";
            }

        } else {
            redirectTo = EMPTY_STRING;
        }
    }

    public void onClickTodaysTrade() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        if (omsEnabled) {
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, true);
            session.setAttribute(TODAYS_TRADE, true);
            session.setAttribute(TODAYS_ORDER, false);
            session.setAttribute(REJECTION_SUMMARY, false);
            session.setAttribute("tradeBreakdown", false);
            session.setAttribute(PORTFOLIO_SUMMARY, false);

            if (session.getAttribute("OMSOrderVO") != null) {
                redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
            } else {
                redirectTo = "/pages/oms_login?faces-redirect=true";
            }

        } else {
            redirectTo = EMPTY_STRING;
        }
    }

    public void doActionRedirectToADM() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        String[] parms = requestParameterMap.get("index").split("_");
        int selectedNotificationIndex = Integer.parseInt(parms[ZERO]);
        AdminNotificationVO selectedNotification = null;
        if (parms[ONE].equalsIgnoreCase("true")) {
            redirectToADM = notificationRedirect.onSelectNotificationADM(adminNotificationspoup, selectedNotificationIndex);
            selectedNotification = adminNotificationspoup.get(selectedNotificationIndex);
        }
    }

    public String getRedirectToADM() {
        return redirectToADM;
    }

    public void setRedirectToADM(String redirectToADM) {
        this.redirectToADM = redirectToADM;
    }

    private Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public String redirectURL() {
        return redirectTo;
    }

    public String getFundTransferUrl() {
        return fundTransferUrl;
    }

    public String getOmsUserId() {
        return omsUserId;
    }

    public void setOmsUserId(String omsUserId) {
        this.omsUserId = omsUserId;
    }

    public void onClickPortfolioSummary() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        if (omsEnabled) {
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, true);
            session.setAttribute(TODAYS_TRADE, false);
            session.setAttribute(TODAYS_ORDER, false);
            session.setAttribute(REJECTION_SUMMARY, false);
            session.setAttribute(PORTFOLIO_SUMMARY, true);
            session.setAttribute("tradeBreakdown", false);

            if (session.getAttribute("OMSOrderVO") != null) {
                redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
            } else {
                redirectTo = "/pages/oms_login?faces-redirect=true";
            }

        } else {
            redirectTo = EMPTY_STRING;
        }
    }

    public void onClickRejectionReport() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        if (omsEnabled) {
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, true);
            session.setAttribute(TODAYS_TRADE, false);
            session.setAttribute(TODAYS_ORDER, false);
            session.setAttribute(PORTFOLIO_SUMMARY, false);
            session.setAttribute(REJECTION_SUMMARY, true);
            session.setAttribute("tradeBreakdown", false);

            if (session.getAttribute("OMSOrderVO") != null) {
                redirectTo = "/pages/oms_rejection_summary?faces-redirect=true";
            } else {
                redirectTo = "/pages/oms_login?faces-redirect=true";
            }

        } else {
            redirectTo = EMPTY_STRING;
        }
    }

    public List<AdminNotificationVO> getAdminNotifications() {
        return adminNotifications;
    }

    public void setAdminNotifications(List<AdminNotificationVO> adminNotifications) {
        this.adminNotifications = adminNotifications;
    }

    public List<AdminNotificationVO> getAdminNotificationspoup() {
        return adminNotificationspoup;
    }

    public void setAdminNotificationspoup(List<AdminNotificationVO> adminNotificationspoup) {
        this.adminNotificationspoup = adminNotificationspoup;
    }

    public String onClickUpload() {
        return "/pages/admin/upload_benchmark?faces-redirect=true";
    }

    public String onClickAllotKit() {
        return "/pages/admin/allocate_reg_kit?faces-redirect=true";
    }

    public String onClickSettings() {
        return "/pages/admin/advancedSettings?faces-redirect=true";
    }

    public String onClickHolidayCalender() {
        return "/pages/admin/holidayCalender?faces-redirect=true";
    }

    public void onClickSinkBp() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        if (omsEnabled) {
            session.setAttribute("AddReedemOrderExecution", false);
            session.setAttribute(TODAYS_DETAILS, false);
            session.setAttribute(TODAYS_TRADE, false);
            session.setAttribute(TODAYS_ORDER, false);
            session.setAttribute(PORTFOLIO_SUMMARY, false);
            session.setAttribute(REJECTION_SUMMARY, false);
            session.setAttribute("tradeBreakdown", false);
            session.setAttribute("sinkBp", true);
            if (session.getAttribute("OMSOrderVO") != null) {
                OMSOrderVO omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
                List rptData = new ArrayList<List<Map>>();
                rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, true);
                saveCustomerBP(rptData);
            } else {
                redirectTo = "/pages/oms_login?faces-redirect=true";
            }

        } else {
            redirectTo = EMPTY_STRING;
        }
    }

    public void saveCustomerBP(List reportData) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        ResultSet data = (ResultSet) reportData.get(2);
        float bp = 0;
        if (data != null) {
            List<Map> dataList = (List<Map>) data.getResult();
            for (Map element : dataList) {
                bp = Float.parseFloat(String.valueOf(element.get("PLEDGER")));
                System.out.println("BUYING power for customer is :-" + bp);
            }
        }
        rebalancePortfolioBAO.saveCustomerBP(userSessionBean.getOmsLoginId(), bp);
        redirectTo = NAVIGATION_PAGE;
    }
    
}
