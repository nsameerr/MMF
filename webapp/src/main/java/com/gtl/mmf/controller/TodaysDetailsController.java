/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.git.oms.api.frontend.message.fieldtype.PriceCondition;
import com.git.oms.api.frontend.message.report.response.ResultSet;
import static com.gtl.linkedin.util.INotificationMessages.INVESTOR_REBALCE_INITIATED;
import com.gtl.linkedin.util.NotificationsLoader;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.common.EnumProductType;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.MMF_OMS_ENABLED;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.REJECTION_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.TODAYS_ORDER;
import static com.gtl.mmf.service.util.IConstants.TODAYS_PAGE;
import static com.gtl.mmf.service.util.IConstants.TODAYS_TRADE;
import static com.gtl.mmf.service.util.IConstants.TRADE_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.TodaysDetailsVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 09860
 */
@Named("todaysdetailscontroller")
@Scope("view")
public class TodaysDetailsController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.TodaysDetailsController");
    private String reportType;
    private List<TodaysDetailsVO> todaysDetailsList;
    private HashMap<String, List> result;
    private String redirectTo;
    private boolean status;
    private OMSOrderVO omsOrderVO;
    private List<TodaysDetailsVO> tradeSummaryList;
    private String todaysOrder = "Today's Order";
    private String todaysTrade = "Today's Trade";
    private boolean showMsg = false;
    private String msg = NotificationsLoader.getPropertiesValue(INVESTOR_REBALCE_INITIATED);

    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;

    @PostConstruct
    public void afterinit() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        List rptData = new ArrayList<List<Map>>();

        if (session != null && session.getAttribute("OMSOrderVO") != null) {
            if (sessionMap.get(USER_SESSION) != null) {
                UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
                if (userSessionBean.getRelationStatus() == FOUR_HOUNDRED.intValue()) {
                    showMsg = true;
                }
                userSessionBean.setFromURL(TODAYS_PAGE);
            }
            omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
            if ((Boolean) session.getAttribute("tradeBreakdown") == null || !(Boolean) session.getAttribute("tradeBreakdown")) {
                if ((Boolean) session.getAttribute(TODAYS_ORDER)) {
                    rptData = rebalancePortfolioBAO.getTodaysOrderDetails(omsOrderVO);
                    reportType = TODAYS_ORDER;
                } else if ((Boolean) session.getAttribute(TODAYS_TRADE)) {
                    rptData = rebalancePortfolioBAO.generatePortfolioReport(omsOrderVO);
                    reportType = TODAYS_TRADE;
                } else if ((Boolean) session.getAttribute(PORTFOLIO_SUMMARY)) {
                    rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, false);
                    reportType = PORTFOLIO_SUMMARY;
                } else if ((Boolean) session.getAttribute(REJECTION_SUMMARY)) {
                    rptData = rebalancePortfolioBAO.getOMSRejectionReport(omsOrderVO);
                    reportType = REJECTION_SUMMARY;
                }
                this.processReportResponse(rptData, reportType);
            } else if (session.getAttribute("TodaysDetailsVO") != null && (Boolean) session.getAttribute("tradeBreakdown")) {
                this.getTradeBreakdownDetails((TodaysDetailsVO) session.getAttribute("TodaysDetailsVO"));
            }

        }

    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public List<TodaysDetailsVO> getTodaysDetailsList() {
        return todaysDetailsList;
    }

    public void setTodaysDetailsList(List<TodaysDetailsVO> todaysDetailsList) {
        this.todaysDetailsList = todaysDetailsList;
    }

    public Map<String, Object> processReportResponse(List response, String reportType) {
        //ResultSet header, ResultSet data,
        ResultSet header = (ResultSet) response.get(0);
        Map<String, Object> responseData = new HashMap<String, Object>();
        String errorCode = (String) header.getRow(ZERO).get("errorCode");
        if (errorCode.equals(String.valueOf(ZERO))) {
            ResultSet data = (ResultSet) response.get(1);
            if (data != null) {
                List<Map> dataList = (List<Map>) data.getResult();
                if (reportType.equalsIgnoreCase(TRADE_SUMMARY)) {
                    tradeSummaryList = replaceCodeWithValues(dataList, reportType);
                } else {
                    todaysDetailsList = replaceCodeWithValues(dataList, reportType);
                }
            }

            responseData.put("REPORTDATA", todaysDetailsList);
        }
        responseData.put("ERRORCODE", errorCode);
        LOGGER.log(Level.INFO, "*** RESPONSE-DATA-ERROR CODE ***[**{0} **]", errorCode);
        return responseData;
    }

    public List<TodaysDetailsVO> replaceCodeWithValues(List<Map> dataList, String rptCode) {
        List<TodaysDetailsVO> detailsList = new ArrayList<TodaysDetailsVO>();
        result = new HashMap<String, List>();
        int statCode = 0;
        for (Map element : dataList) {
            TodaysDetailsVO todaysDetailsVO = new TodaysDetailsVO();
            if (rptCode.equalsIgnoreCase(TODAYS_ORDER)) {
                statCode = (element.get("STATUS") == null) ? 0 : Integer.parseInt(String.valueOf(element.get("STATUS")));
                String statusCategory = this.getOrderStatusGroup(statCode, 1);
                String statusSubCategory = this.getOrderStatusGroup(statCode, 2);
                todaysDetailsVO.setStatusCategory(statusCategory);
                todaysDetailsVO.setStatusSubCategory(statusSubCategory);
                todaysDetailsVO.setCancelOrder(statusSubCategory.equalsIgnoreCase("Confirmed") ? true
                        : statusSubCategory.equalsIgnoreCase("Partially Executed"));
                todaysDetailsVO.setStatus(String.valueOf(element.get("STATUS")));
                todaysDetailsVO.setVenueCode(String.valueOf(element.get("VENUECODE")));
                todaysDetailsVO.setBuyorsell(BuySell.getBuySell(Integer.valueOf("" + element.get("BUYORSELL"))));
                todaysDetailsVO.setProductType(EnumProductType.getProductType(Integer.valueOf("" + element.get("PRODUCTTYPE"))));
                todaysDetailsVO.setPriceCondition(PriceCondition.getPriceCondition(Integer.valueOf("" + element.get("PRICECONDITION"))));
                todaysDetailsVO.setSecurityCode(String.valueOf(element.get("SECURITYCODE")));
                todaysDetailsVO.setPrice(String.valueOf(element.get("PRICE")));
                todaysDetailsVO.setBookedPrice(String.valueOf(element.get("BOOKEDPRICE")));
                todaysDetailsVO.setQuantity(String.valueOf(element.get("QUANTITY")));
                todaysDetailsVO.setPendingQty(String.valueOf(element.get("PENDINGQTY")));
                todaysDetailsVO.setInstrumentType(String.valueOf(element.get("INSTRUMENTTYPE")));
                String ioc = "" + (element.get("IOC") == null ? "" : element.get("IOC"));
                todaysDetailsVO.setIoc(ioc.equalsIgnoreCase("IOC") ? "Yes" : "No");
                todaysDetailsVO.setExecAvgRate(String.valueOf(element.get("EXECAVGRATE")));
                todaysDetailsVO.setExecQty(String.valueOf(element.get("EXECQTY")));
                todaysDetailsVO.setTriggerPrice(String.valueOf(element.get("TRIGGERPRICE")));
                todaysDetailsVO.setDisclosedQuantity(String.valueOf(element.get("DISCLOSEDQUANTITY")));
                todaysDetailsVO.setOrderingUsercode(String.valueOf(element.get("ORDERINGUSERCODE")));
                todaysDetailsVO.setTransId(String.valueOf(element.get("TRANSID")));
                todaysDetailsVO.setSegment(String.valueOf(element.get("SEGMENT")));
                todaysDetailsVO.setOrderType(Integer.parseInt(String.valueOf(element.get("ORDERTYPE"))));
                //todaysDetailsVO.setChannel(EnumOMSChannel.getChannel(Integer.valueOf("" + element.get("CHANNEL"))));
                todaysDetailsVO.setChannel(Integer.parseInt(String.valueOf(element.get("CHANNEL"))));
                todaysDetailsVO.setOrderSource(Integer.parseInt(String.valueOf(element.get("ORDERSOURCE"))));
                todaysDetailsVO.setLegno(Integer.parseInt(String.valueOf(element.get("LEGNO"))));

                List list = result.get(statusSubCategory);
                if (list != null) {
                    list.add(todaysDetailsVO);
                } else {
                    list = new LinkedList();
                    list.add(todaysDetailsVO);
                }
                result.put(statusSubCategory, list);

            } else if (rptCode.equalsIgnoreCase(TODAYS_TRADE)) {
                todaysDetailsVO.setInstrumentType(String.valueOf(element.get("INSTRUMENTTYPE")));
                todaysDetailsVO.setProductTypeValue(Integer.valueOf("" + element.get("PRODUCTTYPE")));
                todaysDetailsVO.setProductType(EnumProductType.getProductType(Integer.valueOf("" + element.get("PRODUCTTYPE"))));
                todaysDetailsVO.setSecurityCode(String.valueOf(element.get("SECURITYCODE")));
                todaysDetailsVO.setVenueCode(String.valueOf(element.get("VENUECODE")));
                todaysDetailsVO.setVenueScripcode(String.valueOf(element.get("VENUESCRIPCODE")));
                todaysDetailsVO.setPendingBoughtqty(String.valueOf(element.get("PENDINGBOUGHTQTY")));
                todaysDetailsVO.setBoughtQty(String.valueOf(element.get("BOUGHTQTY")));
                todaysDetailsVO.setSoldQty(String.valueOf(element.get("SOLDQTY")));
                todaysDetailsVO.setPendingSoldqty(String.valueOf(element.get("PENDINGSOLDQTY")));
                todaysDetailsVO.setUnits(String.valueOf(element.get("UNITS")));
                todaysDetailsVO.setOrderingUsercode(String.valueOf(element.get("ORDERINGUSERCODE")));
                todaysDetailsVO.setConvertedBuyqty(String.valueOf(element.get("CONVERTEDBUYQTY")));
                todaysDetailsVO.setReceivedBuyqty(String.valueOf(element.get("RECEIVEDBUYQTY")));
                todaysDetailsVO.setConvertedSellqty(String.valueOf(element.get("CONVERTEDSELLQTY")));
                todaysDetailsVO.setLastTradeprice(String.valueOf(element.get("LASTTRADEPRICE")));
                detailsList.add(todaysDetailsVO);
            } else if (rptCode.equalsIgnoreCase(TRADE_SUMMARY)) {
                todaysDetailsVO.setInstrumentType(String.valueOf(element.get("INSTRUMENTTYPE")));
                todaysDetailsVO.setProductTypeValue(Integer.valueOf("" + element.get("PRODUCTTYPE")));
                todaysDetailsVO.setProductType(EnumProductType.getProductType(Integer.valueOf("" + element.get("PRODUCTTYPE"))));
                todaysDetailsVO.setSecurityCode(String.valueOf(element.get("SECURITYCODE")));
                todaysDetailsVO.setVenueCode(String.valueOf(element.get("VENUECODE")));
                todaysDetailsVO.setVenueScripcode(String.valueOf(element.get("VENUESCRIPCODE")));
                todaysDetailsVO.setQuantity(String.valueOf(element.get("QUANTITY")));
                todaysDetailsVO.setExecQty(String.valueOf(element.get("EXECQTY")));
                todaysDetailsVO.setPendingQty(String.valueOf(element.get("PENDINGQTY")));
                todaysDetailsVO.setPrice(String.valueOf(element.get("PRICE")));//order price
                todaysDetailsVO.setOrderid(String.valueOf(element.get("ORDERID")));//orderid
                todaysDetailsVO.setOrderReplyTime(String.valueOf(element.get("ORDERREPLYTIME")));
                todaysDetailsVO.setTransId(String.valueOf(element.get("TRANSID")));//Trans no:
                todaysDetailsVO.setStrikePrice(String.valueOf(element.get("STRIKEPRICE")));
                todaysDetailsVO.setDisclosedQuantity(String.valueOf(element.get("DISCLOSEDQUANTITY")));
                todaysDetailsVO.setExecAvgRate(String.valueOf(element.get("EXECAVGRATE")));
                todaysDetailsVO.setUnits(String.valueOf(element.get("UNITS")));
                todaysDetailsVO.setOrderingUsercode(String.valueOf(element.get("ORDERINGUSERCODE")));
                todaysDetailsVO.setBuyorsell(BuySell.getBuySell(Integer.valueOf("" + element.get("BUYORSELL"))));
                todaysDetailsVO.setPriceCondition(PriceCondition.getPriceCondition(Integer.valueOf("" + element.get("PRICECONDITION"))));
                detailsList.add(todaysDetailsVO);
            } else if (rptCode.equalsIgnoreCase(PORTFOLIO_SUMMARY)) {
                todaysDetailsVO.setProductType(EnumProductType.getProductType(Integer.valueOf("" + element.get("PRODUCTTYPE"))));
                todaysDetailsVO.setSecurityCode(String.valueOf(element.get("SECURITYCODE")));
                todaysDetailsVO.setDescription(String.valueOf(element.get("SECURITYNAME")));
                todaysDetailsVO.setInstrumentType(element.get("INSTRUMENTTYPE") == null ? EMPTY_STRING
                        : String.valueOf(element.get("INSTRUMENTTYPE")).trim().equalsIgnoreCase("null")
                        ? EMPTY_STRING : String.valueOf(element.get("INSTRUMENTTYPE")));//contract
                todaysDetailsVO.setVenueCode(String.valueOf(element.get("VENUECODE")));
                todaysDetailsVO.setUnits(String.valueOf(element.get("AVAILABLENETQTY")));// net position
                todaysDetailsVO.setExecAvgRate(String.valueOf(element.get("AVGRATE")));
                todaysDetailsVO.setMktRate(String.valueOf(element.get("MKTRATE")));
                todaysDetailsVO.setQuantity(String.valueOf(element.get("DPQTY")));
                todaysDetailsVO.setPendingQty(String.valueOf(element.get("UNSETTLED")));
                todaysDetailsVO.setBoughtQty(String.valueOf(element.get("INTRADAYBUY")));//today' buy
                todaysDetailsVO.setSoldQty(String.valueOf(element.get("INTRADAYSELL")));//todays's sell
                detailsList.add(todaysDetailsVO);
            } else if (rptCode.equalsIgnoreCase(REJECTION_SUMMARY)) {
                todaysDetailsVO.setInstrumentType(String.valueOf(element.get("INSTRUMENTTYPE")));
                todaysDetailsVO.setProductTypeValue(Integer.valueOf("" + element.get("PRODUCTTYPE")));
                todaysDetailsVO.setProductType(EnumProductType.getProductType(Integer.valueOf("" + element.get("PRODUCTTYPE"))));
                todaysDetailsVO.setSecurityCode(String.valueOf(element.get("SECURITYCODE")));
                todaysDetailsVO.setVenueCode(String.valueOf(element.get("VENUECODE")));
                todaysDetailsVO.setVenueScripcode(String.valueOf(element.get("VENUESCRIPCODE")));
                todaysDetailsVO.setOrderingUsercode(String.valueOf(element.get("ORDERINGUSERCODE")));
                todaysDetailsVO.setEnteringUsercode(String.valueOf(element.get("ENTERINGUSERCODE")));
                todaysDetailsVO.setTransId(String.valueOf(element.get("TRANSID")));//Trans no:
                todaysDetailsVO.setOrderType(Integer.parseInt(String.valueOf(element.get("ORDERTYPE"))));
                todaysDetailsVO.setQuantity(String.valueOf(element.get("QUANTITY")));
                todaysDetailsVO.setPrice(String.valueOf(element.get("PRICE")));
                todaysDetailsVO.setPriceCondition(PriceCondition.getPriceCondition(Integer.valueOf("" + element.get("PRICECONDITION"))));
                todaysDetailsVO.setBuyorsell(BuySell.getBuySell(Integer.valueOf("" + element.get("BUYORSELL"))));
                String ioc = "" + (element.get("IOC") == null ? "" : element.get("IOC"));
                todaysDetailsVO.setIoc(ioc.equalsIgnoreCase("IOC") ? "Yes" : "No");
                todaysDetailsVO.setDisclosedQuantity(String.valueOf(element.get("DISCLOSEDQUANTITY")));
                todaysDetailsVO.setRequestTime(String.valueOf(element.get("REQUESTTIME")));
                String errMsg = String.valueOf(element.get("ERRORMESSAGES")).trim();
                int size = errMsg.length();
                todaysDetailsVO.setErroMsg(errMsg.substring(1, size));
                detailsList.add(todaysDetailsVO);
            }
        }
        return detailsList;
    }

    public String getOrderStatusGroup(int orderStatus, int level) {
        //level 1 top level
        //level 2 sublevel
        switch (orderStatus) {
            case 1:
                return (level == 1 ? "Open" : "Pending");//Pending
            case 2:
                return (level == 1 ? "Open" : "Send TO Exchange");//Send TO Exchange
            case 3:
                return (level == 1 ? "Open" : "Confirmed");//Confirmed
            case 4:
                return (level == 1 ? "Executed" : "Executed");//Executed
            case 5:
                return (level == 1 ? "Executed" : "Partially Executed");//Partially Executed
            case 6:
                return (level == 1 ? "Executed" : "Partially Executed and Cancelled");//Partially Executed and Cancelled
            case 7:
                return (level == 1 ? "Cancelled" : "Cancelled");//Cancelled
            case 8:
                return (level == 1 ? "Rejected" : "Rejected");//Rejected
            case 9:
                return (level == 1 ? "Cancelled" : "Cancelled By Venue");//Cancelled By Venue
            case 10:
                return (level == 1 ? "Executed" : "Partially Executed and Cancelled By Venue");//Partially Executed and Cancelled By Venue
            case 11:
                return (level == 1 ? "Open" : "Send To One Touch Receiver");//Send To One Touch Receiver
            case 12:
                return (level == 1 ? "Open" : "Open");//Open
            case 15:
                return (level == 1 ? "Open" : "Save");//Save
            case 16:
                return (level == 1 ? "Open" : "Queued");//Queued
            case 17:
                return (level == 1 ? "Open" : "Send");//Send
            case 18:
                return (level == 1 ? "Rejected" : "OMS Rejected");//OMS Rejected
            case 19:
                return (level == 1 ? "Others" : "Order Reply Timeout");//Order Reply Timeout
            case 20:
                return (level == 1 ? "Others" : "Deleted");//Deleted
            case 21:
                return (level == 1 ? "Open" : "Triggered");//Triggered
            case 22:
                return (level == 1 ? "Cancelled" : "Cancelled By OMS");//Cancelled by oms
            case 23:
                return (level == 1 ? "Open" : "In Process");//In proocess
            default:
                return null;
        }
    }

    public List<TodaysDetailsVO> groupOrderDetails(String status) {
        List<TodaysDetailsVO> subList = new ArrayList<TodaysDetailsVO>();
        if (reportType.equalsIgnoreCase(TODAYS_ORDER)) {
            for (TodaysDetailsVO list : todaysDetailsList) {
                if (list.getStatusSubCategory().equalsIgnoreCase(status)) {
                    subList.add(list);
                }
            }
        }
        return subList;
    }

    public HashMap<String, List> getResult() {
        return result;
    }

    public void setResult(HashMap<String, List> result) {
        this.result = result;
    }

    public String getRedirectTo() {
        return redirectTo;
    }

    public void setRedirectTo(String redirectTo) {
        this.redirectTo = redirectTo;
    }

    /**
     * Method to cancel an order(confirmed/partially executed).
     *
     * @param todaysDetailsVO
     * @return
     */
    public String cancelTodaysOrder(TodaysDetailsVO todaysDetailsVO) {
        redirectTo = EMPTY_STRING;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        boolean cancelOrder = true;
        if (todaysDetailsVO.getStatusSubCategory().equalsIgnoreCase("Confirmed")
                || todaysDetailsVO.getStatusSubCategory().equalsIgnoreCase("Partially Executed")) {
            Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
            if (omsEnabled) {
                session.setAttribute("cancelOrder", cancelOrder);
                session.setAttribute("cancelDetails", todaysDetailsVO);
                if (session.getAttribute("OMSOrderVO") != null) {
                    omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
                    rebalancePortfolioBAO.cancelPlacedOrder(omsOrderVO, todaysDetailsVO);
                    redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
                } else {
                    redirectTo = "/pages/oms_login?faces-redirect=true";
                }

            } else {
                redirectTo = EMPTY_STRING;
            }
        }

        return redirectTo;
    }

    public void onSelectTrade() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        int selectedIndex = Integer.valueOf(requestParameterMap.get("index"));
        TodaysDetailsVO todaysDetailsVO = this.todaysDetailsList.get(selectedIndex);
        session.setAttribute("tradeBreakdown", true);
        session.setAttribute("TodaysDetailsVO", todaysDetailsVO);
        redirectTo = "/pages/todays_trade_breakdown?faces-redirect=true";
    }

    public List<TodaysDetailsVO> getTradeSummaryList() {
        return tradeSummaryList;
    }

    public void setTradeSummaryList(List<TodaysDetailsVO> tradeSummaryList) {
        this.tradeSummaryList = tradeSummaryList;
    }

    public void onbackFromTradeBreakDown() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("tradeBreakdown", false);
        redirectTo = "/pages/todays_order_and_trade?faces-redirect=true";
    }

    public void getTradeBreakdownDetails(TodaysDetailsVO todaysDetailsVO) {
        List rptData = new ArrayList<List<Map>>();
        Map<String, Object> responseData = new HashMap<String, Object>();
        rptData = rebalancePortfolioBAO.getTradeSummary(todaysDetailsVO, omsOrderVO);
        responseData = this.processReportResponse(rptData, TRADE_SUMMARY);
    }

    public String getTodaysOrder() {
        return todaysOrder;
    }

    public void setTodaysOrder(String todaysOrder) {
        this.todaysOrder = todaysOrder;
    }

    public String getTodaysTrade() {
        return todaysTrade;
    }

    public void setTodaysTrade(String todaysTrade) {
        this.todaysTrade = todaysTrade;
    }

    public void refreshPage(String reportType) {
        List rptData = new ArrayList<List<Map>>();
        if (reportType.equalsIgnoreCase(TODAYS_ORDER)) {
            rptData = rebalancePortfolioBAO.getTodaysOrderDetails(omsOrderVO);
            reportType = TODAYS_ORDER;
        } else if (reportType.equalsIgnoreCase(TODAYS_TRADE)) {
            rptData = rebalancePortfolioBAO.generatePortfolioReport(omsOrderVO);
            reportType = TODAYS_TRADE;
        } else if (reportType.equalsIgnoreCase(PORTFOLIO_SUMMARY)) {
            rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, false);
            reportType = PORTFOLIO_SUMMARY;
        } else if (reportType.equalsIgnoreCase(REJECTION_SUMMARY)) {
            rptData = rebalancePortfolioBAO.getOMSRejectionReport(omsOrderVO);
            reportType = REJECTION_SUMMARY;
        }
        this.processReportResponse(rptData, reportType);
    }

    public void onClickTodaysTrade() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        session.setAttribute("AddReedemOrderExecution", false);
        session.setAttribute(TODAYS_DETAILS, true);
        session.setAttribute(TODAYS_TRADE, true);
        session.setAttribute(TODAYS_ORDER, false);
        session.setAttribute(REJECTION_SUMMARY, false);
        session.setAttribute("tradeBreakdown", false);
        session.setAttribute(PORTFOLIO_SUMMARY, false);
        redirectTo = TODAYS_PAGE;
    }

    public boolean isShowMsg() {
        return showMsg;
    }

    public void setShowMsg(boolean showMsg) {
        this.showMsg = showMsg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
