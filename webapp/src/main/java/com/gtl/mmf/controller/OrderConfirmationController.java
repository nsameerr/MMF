/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.git.oms.api.frontend.message.fieldtype.PurchaseType;
import com.git.oms.api.frontend.message.fieldtype.VenueCode;
import com.git.oms.api.frontend.message.report.response.ResultSet;
import com.gtl.mmf.bao.IAddRedeemFundsBAO;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.ALL_IN_CAPS;
import static com.gtl.mmf.service.util.IConstants.CONTINOUS;
import static com.gtl.mmf.service.util.IConstants.DEMAT;
import static com.gtl.mmf.service.util.IConstants.DOUBLE_DASH;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.HH_MM;
import static com.gtl.mmf.service.util.IConstants.LIMIT;
import static com.gtl.mmf.service.util.IConstants.MARKET;
import static com.gtl.mmf.service.util.IConstants.MMF_VENUE_OFFLINE_ENABLED;
import static com.gtl.mmf.service.util.IConstants.NSEMF;
import static com.gtl.mmf.service.util.IConstants.PLACE_OFFLINE_ORDER;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.REJECTION_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.TIME_ALERT;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.TODAYS_ORDER;
import static com.gtl.mmf.service.util.IConstants.TODAYS_PAGE;
import static com.gtl.mmf.service.util.IConstants.TODAYS_TRADE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import com.gtl.mmf.service.vo.ConfirmOrderVo;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
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

/**
 *
 * @author 09860
 */
@Named("orderconfirmationcontroller")
@Scope("view")
public class OrderConfirmationController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.OrderConfirmationController");
    private static final String RE_DIRECTION_OMS_LOGIN = "/pages/oms_login?faces-redirect=true";
    private static final String REBALANCE_PAGE = "/pages/rebalance_portfolio?faces-redirect=true";
    private static final String NAVIGATION_PAGE = "/pages/investordashboard?faces-redirect=true";

    private CustomerPortfolioVO customerPortfolioVO;
    private List<PortfolioDetailsVO> portfolioDetailsVOList;
    private List<PieChartDataVO> tragetPieChartDataVOList;
    private List<PieChartDataVO> customerPieChartDataVOList;
    private AddRedeemFundsVO addRedeemFundsVO;
    private List<PortfolioSecurityVO> filteredSecurityList;
    private OMSOrderVO omsOrderVO;
    private String reDirectionURL;
    boolean offlinee_Enabled;
    private List<ConfirmOrderVo> confirmList;
    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;
    @Autowired
    private IAddRedeemFundsBAO addRedeemFundsBAO;
    UserSessionBean userSessionBean;
    Set<String> venueNameSet;
    private boolean allOflineOrder;
    private String oflineMsg;
    private boolean allMfOrder;
    private String mfordermsg;

    @PostConstruct
    public void afterinit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        offlinee_Enabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_VENUE_OFFLINE_ENABLED));
        if (session != null && session.getAttribute("OMSOrderVO") != null) {
            omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
        }
        if ((Boolean) session.getAttribute("AddReedemOrderExecution")) {
            addRedeemFundsVO = (AddRedeemFundsVO) session.getAttribute("AddRedeemFundsVO");
            this.calculateTransactionDetailsForAddreedemTransaction();
            //Set venue name for indetify offline status
            setVenueNameSet(addRedeemFundsVO.getVenuName());
            this.filterList(addRedeemFundsVO.getPortfolioDetailsVOs());
        } else {
            //User trigger Re-balance
            customerPortfolioVO = new CustomerPortfolioVO();
            tragetPieChartDataVOList = new ArrayList<PieChartDataVO>();
            customerPieChartDataVOList = new ArrayList<PieChartDataVO>();
            portfolioDetailsVOList = new ArrayList<PortfolioDetailsVO>();
            customerPortfolioVO.setCustomerId(userSessionBean.getUserId());
            customerPortfolioVO.setFromAddReedem(false);
            if (userSessionBean.getRelationStatus() != 0) {
                rebalancePortfolioBAO.getPortfolioRebalancePageDetails(customerPortfolioVO, tragetPieChartDataVOList,
                        customerPieChartDataVOList, portfolioDetailsVOList);
                rebalancePortfolioBAO.rebalancePortfolio(customerPieChartDataVOList, portfolioDetailsVOList, customerPortfolioVO);
                this.updateMarketData();
                this.filterList(portfolioDetailsVOList);
            }
        }

    }

    public CustomerPortfolioVO getCustomerPortfolioVO() {
        return customerPortfolioVO;
    }

    public void setCustomerPortfolioVO(CustomerPortfolioVO customerPortfolioVO) {
        this.customerPortfolioVO = customerPortfolioVO;
    }

    public List<PortfolioDetailsVO> getPortfolioDetailsVOList() {
        return portfolioDetailsVOList;
    }

    public void setPortfolioDetailsVOList(List<PortfolioDetailsVO> portfolioDetailsVOList) {
        this.portfolioDetailsVOList = portfolioDetailsVOList;
    }

    public List<PieChartDataVO> getTragetPieChartDataVOList() {
        return tragetPieChartDataVOList;
    }

    public void setTragetPieChartDataVOList(List<PieChartDataVO> tragetPieChartDataVOList) {
        this.tragetPieChartDataVOList = tragetPieChartDataVOList;
    }

    public List<PieChartDataVO> getCustomerPieChartDataVOList() {
        return customerPieChartDataVOList;
    }

    public void setCustomerPieChartDataVOList(List<PieChartDataVO> customerPieChartDataVOList) {
        this.customerPieChartDataVOList = customerPieChartDataVOList;
    }

    public AddRedeemFundsVO getAddRedeemFundsVO() {
        return addRedeemFundsVO;
    }

    public void setAddRedeemFundsVO(AddRedeemFundsVO addRedeemFundsVO) {
        this.addRedeemFundsVO = addRedeemFundsVO;
    }

    public IRebalancePortfolioBAO getRebalancePortfolioBAO() {
        return rebalancePortfolioBAO;
    }

    public void setRebalancePortfolioBAO(IRebalancePortfolioBAO rebalancePortfolioBAO) {
        this.rebalancePortfolioBAO = rebalancePortfolioBAO;
    }

    public IAddRedeemFundsBAO getAddRedeemFundsBAO() {
        return addRedeemFundsBAO;
    }

    public void setAddRedeemFundsBAO(IAddRedeemFundsBAO addRedeemFundsBAO) {
        this.addRedeemFundsBAO = addRedeemFundsBAO;
    }

    public List<PortfolioSecurityVO> getFilteredSecurityList() {
        return filteredSecurityList;
    }

    public void setFilteredSecurityList(List<PortfolioSecurityVO> filteredSecurityList) {
        this.filteredSecurityList = filteredSecurityList;
    }

    public OMSOrderVO getOmsOrderVO() {
        return omsOrderVO;
    }

    public void setOmsOrderVO(OMSOrderVO omsOrderVO) {
        this.omsOrderVO = omsOrderVO;
    }

    public String getReDirectionURL() {
        return reDirectionURL;
    }

    public void setReDirectionURL(String reDirectionURL) {
        this.reDirectionURL = reDirectionURL;
    }

    public boolean isOfflinee_Enabled() {
        return offlinee_Enabled;
    }

    public void setOfflinee_Enabled(boolean offlinee_Enabled) {
        this.offlinee_Enabled = offlinee_Enabled;
    }

    public List<ConfirmOrderVo> getConfirmList() {
        return confirmList;
    }

    public void setConfirmList(List<ConfirmOrderVo> confirmList) {
        this.confirmList = confirmList;
    }

    private void updateMarketData() {
        PortfolioUtil.updateCustomerPortfolioMarketPrice(customerPortfolioVO);
        PortfolioUtil.updateValueAndWeight(customerPortfolioVO);
        PortfolioUtil.updateTransactionDetailsConsideringAsset(customerPortfolioVO);
        venueNameSet = customerPortfolioVO.getVenuName();
    }

    private void filterList(List<PortfolioDetailsVO> completeExecutionList) {
        Calendar cal = Calendar.getInstance();
        String datetime = DateUtil.dateToString(cal.getTime(), HH_MM);//TimeWed Jan 18 15:08:01 IST 2017
        String[] currentTime = datetime.split(":");
        Integer time_hour = Integer.parseInt(currentTime[0]);
        Integer time_minute = Integer.parseInt(currentTime[1]);
        boolean addItemStatus = false;
        filteredSecurityList = new ArrayList<PortfolioSecurityVO>();
        if (completeExecutionList != null) {
            for (PortfolioDetailsVO item : completeExecutionList) {
                List<PortfolioSecurityVO> securityDetails = new ArrayList<PortfolioSecurityVO>();
                securityDetails = item.getSecurities();
                for (PortfolioSecurityVO security : securityDetails) {
                    if (!security.getVenueCode().equalsIgnoreCase(NSEMF)) {
                        if (security.getBuysellStatusText() != null
                                && (security.getBuysellStatusText().equalsIgnoreCase(BuySell.BUY.toString())
                                || security.getBuysellStatusText().equalsIgnoreCase(BuySell.SELL.toString()))) {
                            filteredSecurityList.add(security);
                        }
                    } else if (time_hour < 15) {
                        if (security.getBuysellStatusText() != null
                                && (security.getBuysellStatusText().equalsIgnoreCase(BuySell.BUY.toString())
                                || security.getBuysellStatusText().equalsIgnoreCase(BuySell.SELL.toString()))) {
                            filteredSecurityList.add(security);
                        }
                    }
                }
            }
            getConfirmationList(filteredSecurityList, omsOrderVO);
        }
        if (filteredSecurityList.size() == 0 && time_hour >=15) {
            allMfOrder = true;
            mfordermsg = TIME_ALERT;
        }

        LOGGER.log(Level.INFO, "total filtered security list size : {0}", filteredSecurityList.size());
    }

    public void placeOrder(boolean oflineOrder) {
        String error;
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //Adviser - Investor mapping id
        int relationId = ZERO;
        //Handling transaction from ADD/REEDEM
        if ((Boolean) session.getAttribute("AddReedemOrderExecution")) {
            if (addRedeemFundsVO.getRelationId() != null) {
                relationId = addRedeemFundsVO.getRelationId();
            }
            rebalancePortfolioBAO.executeSecurityOrders(addRedeemFundsVO, addRedeemFundsVO.getPortfolioDetailsVOs(), omsOrderVO, oflineOrder);
            rebalancePortfolioBAO.saveSellPortfolioDetails(filteredSecurityList, omsOrderVO);
        } else {
            if (customerPortfolioVO.getRelationId() != null) {
                relationId = customerPortfolioVO.getRelationId();
            }
            //rebalancePortfolioBAO.executeSecurityOrders(customerPortfolioVO, portfolioDetailsVOList, omsOrderVO, oflineOrder);
            rebalancePortfolioBAO.executeSecurityOrders(customerPortfolioVO, portfolioDetailsVOList, omsOrderVO, oflineOrder);
            List rptData = new ArrayList<List<Map>>();
            rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, true);
            saveCustomerBP(rptData);
        }
        UserSessionBean userSessionBean = (UserSessionBean) fc.getExternalContext().getSessionMap().get(USER_SESSION);
        userSessionBean.setRelationId(relationId);
        userSessionBean.setRelationStatus(FOUR_HOUNDRED);
        session.setAttribute(USER_SESSION, userSessionBean);
        session.setAttribute("AddReedemOrderExecution", false);
        session.setAttribute(TODAYS_DETAILS, true);
        session.setAttribute(TODAYS_ORDER, true);
        session.setAttribute(TODAYS_TRADE, false);
        session.setAttribute("tradeBreakdown", false);
        session.setAttribute(PORTFOLIO_SUMMARY, false);
        session.setAttribute(REJECTION_SUMMARY, false);
        reDirectionURL = TODAYS_PAGE;
    }

    public void checkOflineOrderStatus() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        //placeOfflineOrdersuser - flag telling whether user wants to place offline order
        boolean placeOfflineOrders = false;
        if (session != null & session.getAttribute(PLACE_OFFLINE_ORDER) != null) {
            placeOfflineOrders = (Boolean) session.getAttribute(PLACE_OFFLINE_ORDER);
        }
        boolean online = getOnlineStatus();

        if (online && !offlinee_Enabled) {
            placeOrder(true);
        } else if (offlinee_Enabled && placeOfflineOrders) {
            //placing order as offline
            placeOrder(true);
        } else {
            placeOrder(false);
        }
    }

    private void getConfirmationList(List<PortfolioSecurityVO> newList, OMSOrderVO omsovo) {
        confirmList = new ArrayList<ConfirmOrderVo>();
        for (PortfolioSecurityVO item : newList) {
            ConfirmOrderVo cov = new ConfirmOrderVo();
            cov.setExchange(item.getVenueCode());
            cov.setSymbol(item.getSecurityCode());
            cov.setScripCode(item.getVenueScriptCode());
            cov.setPriceCondition(MARKET);
            cov.setBuyOrSell(item.getBuysellStatusText());
            cov.setQuantity(item.getTransactUnits());
            cov.setPrice(item.getCurrentPrice());
//            cov.setMoh(omsovo.getMoh());
            cov.setMoh(DOUBLE_DASH);
            cov.setBookType(CONTINOUS);
            cov.setDepositoryMode(DOUBLE_DASH);
            cov.setAmcCode(DOUBLE_DASH);
            cov.setCategory(DOUBLE_DASH);
            cov.setTriggerprice(DOUBLE_DASH);
            cov.setDisclosedQty(DOUBLE_DASH);
            Double volume = item.getTransactAmount();
            cov.setTotalOrderValue(volume);
            cov.setPurchaseType(DOUBLE_DASH);
            cov.setBuySellTextColor(item.getBuySellTextColor());
            if (item.getVenueCode().equalsIgnoreCase(VenueCode.NSEMF.value())) {
                cov.setDepositoryMode(DEMAT);
                cov.setPriceCondition(LIMIT);
                cov.setAmcCode(ALL_IN_CAPS);
                cov.setCategory(ALL_IN_CAPS);
                if (item.getExecutedUnits() > 0) {
                    cov.setPurchaseType(PurchaseType.ADDITIONAL.toString());
                } else {
                    cov.setPurchaseType(PurchaseType.FRESH.toString());
                }
                if (item.getBuysellStatusText().equalsIgnoreCase(BuySell.BUY.toString())) {
                    cov.setQuantity(ZERO_POINT_ZERO);
                    cov.setPrice(item.getTransactAmount());
                } else {
                    cov.setQuantity(item.getTransactUnits());
                    cov.setPrice(ZERO_POINT_ZERO);
                }
                cov.setBookType(DOUBLE_DASH);
                cov.setTriggerprice(DOUBLE_DASH);
                cov.setDisclosedQty(DOUBLE_DASH);
                cov.setTotalOrderValue(ZERO_POINT_ZERO);
            }
            confirmList.add(cov);
        }
    }

    public void onClickCancel() {
        if (userSessionBean != null && userSessionBean.getFromURL() != null) {
            reDirectionURL = userSessionBean.getFromURL();
        } else {
            reDirectionURL = REBALANCE_PAGE;
        }
    }

    public void onClickConfirm() {
        reDirectionURL = RE_DIRECTION_OMS_LOGIN;
    }

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
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

    public Set<String> getVenueNameSet() {
        return venueNameSet;
    }

    public void setVenueNameSet(Set<String> venueNameSet) {
        this.venueNameSet = venueNameSet;
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

    private void calculateTransactionDetailsForAddreedemTransaction() {
        addRedeemFundsVO.setFromAddReedem(true);
        PortfolioUtil.updateCustomerPortfolioMarketPrice(addRedeemFundsVO);
        PortfolioUtil.updateValueAndWeight(addRedeemFundsVO);
        PortfolioUtil.updateTransactionDetails(addRedeemFundsVO);
    }

    public void saveCustomerBP(List reportData) {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        ResultSet data = (ResultSet) reportData.get(2);
        float bp = 0;
        if (data != null) {
            List<Map> dataList = (List<Map>) data.getResult();
            for (Map element : dataList) {
                System.out.println("BUYING power for customer is :-" + String.valueOf(element.get("PLEDGER")));
                bp = Float.valueOf(String.valueOf(element.get("PLEDGER")));
                System.out.println("BUYING power for customer is :-" + bp);
            }
        }
        rebalancePortfolioBAO.saveCustomerBP(userSessionBean.getOmsLoginId(), bp);
    }

    public String getMfordermsg() {
        return mfordermsg;
    }

    public void setMfordermsg(String mfordermsg) {
        this.mfordermsg = mfordermsg;
    }

    public boolean isAllMfOrder() {
        return allMfOrder;
    }

    public void setAllMfOrder(boolean allMfOrder) {
        this.allMfOrder = allMfOrder;
    }
    

}
