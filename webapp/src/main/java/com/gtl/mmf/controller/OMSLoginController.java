/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.controller;

import com.git.oms.api.frontend.exceptions.FrontEndException;
import com.git.oms.api.frontend.exceptions.MapperException;
import com.git.oms.api.frontend.message.MessageTypes;
import com.git.oms.api.frontend.message.OMSDTOFactory;
import com.git.oms.api.frontend.message.as.request.AuthenticationRequestDTO;
import com.git.oms.api.frontend.message.as.request.ChangePasswordRequestDTO;
import com.git.oms.api.frontend.message.as.response.AuthenticationResponseDTO;
import com.git.oms.api.frontend.message.as.response.ChangePasswordResponseDTO;
import com.git.oms.api.frontend.message.fieldtype.AuthReqType;
import com.git.oms.api.frontend.message.fieldtype.Channel;
import com.git.oms.api.frontend.message.report.response.ResultSet;
import com.git.oms.common.error.AuthenticationErrorCodes;
import com.gtl.mmf.bao.IAddRedeemFundsBAO;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.common.EnumVenueCode;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DOB;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.HH_MM;
import static com.gtl.mmf.service.util.IConstants.MMF_VENUE_OFFLINE_ENABLED;
import static com.gtl.mmf.service.util.IConstants.OMS_DTO_HEADER_VERSION;
import static com.gtl.mmf.service.util.IConstants.OMS_ERROR_CATEGORY;
import static com.gtl.mmf.service.util.IConstants.OMS_ERROR_CODE;
import static com.gtl.mmf.service.util.IConstants.OMS_LOGIN_PAGE_FOOTER;
import static com.gtl.mmf.service.util.IConstants.ORDER_CONFIRMATION_PAGE;
import static com.gtl.mmf.service.util.IConstants.PAN;
import static com.gtl.mmf.service.util.IConstants.PIPE_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.PLACE_OFFLINE_ORDER;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.REJECTION_SUMMARY;
import static com.gtl.mmf.service.util.IConstants.SMS;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.TODAYS_DETAILS;
import static com.gtl.mmf.service.util.IConstants.TODAYS_ORDER;
import static com.gtl.mmf.service.util.IConstants.TODAYS_TRADE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.OMSAdapterRv;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.TodaysDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Named("omslogincontroller")
@Scope("view")
public class OMSLoginController {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.OMSLoginController");
    private static final String NAVIGATION_PAGE = "/pages/investordashboard?faces-redirect=true";
    private static final String TWO_FACTOR_PAGE = "/pages/oms_twofactor_login?faces-redirect=true";
    private static final String REBALANCE_PAGE = "/pages/rebalance_portfolio?faces-redirect=true";
    private static final String PROPERTIES_FILE_OMS_ER = "ErrorMessageBundle";
    private static final String DATE_FORMAT = "d MMM,yyyy";
    private OMSOrderVO omsOrderVO;
    private String twoFactorType;
    private String pan;
    private String sms;
    private String dob;
    private String reDirectionURL;
    //sets to true only if there is only initial OMS login
    private boolean omsLoginPageStatus;
    //tre for all venues are online else false
    boolean online;
    //To determine offline check required or not
    boolean offlinee_Enabled;
    private AuthenticationResponseDTO ardto;
    private CustomerPortfolioVO customerPortfolioVO;
    private List<PortfolioDetailsVO> portfolioDetailsVOList;
    private List<PieChartDataVO> tragetPieChartDataVOList;
    private List<PieChartDataVO> customerPieChartDataVOList;
    private AddRedeemFundsVO addRedeemFundsVO;
    private static final String TODAYS_PAGE = "/pages/todays_order_and_trade?faces-redirect=true";

    @Autowired
    private IRebalancePortfolioBAO rebalancePortfolioBAO;

    @Autowired
    private IAddRedeemFundsBAO addRedeemFundsBAO;

    private static final HashMap<String, Boolean> exchangeStatusMap = new HashMap<String, Boolean>();
    Set<String> venueNameSet;
    //flag set to true, if all the venues are offline.
    private boolean allOflineOrder;
    //To display offline ater messsage window
    private String oflineMsg;
    //flag set to false if any one of the exchange is offline else true
    private boolean onlineStatus;
    //sets to true only if there is TWO_FACTOR login
    private boolean twoFactorRequired;
    //exchange start end time map
    private static final HashMap<String, String> exchangeTimeMap = new HashMap<String, String>();
    private String omsLoginFooter = PropertiesLoader.getPropertiesValue(OMS_LOGIN_PAGE_FOOTER);

    @PostConstruct
    public void init() {
        try {
            online = true;
            omsOrderVO = new OMSOrderVO();
            //Retrive OMSServices instance after initialization
            omsOrderVO.setOmsServices(LookupDataLoader.getOmsServices());
            twoFactorType = PAN;
            omsLoginPageStatus = true;
            twoFactorRequired = false;
            offlinee_Enabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_VENUE_OFFLINE_ENABLED));
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            if (session != null && session.getAttribute("OMSOrderVO") != null) {
                omsOrderVO = (OMSOrderVO) session.getAttribute("OMSOrderVO");
                rebalancePortfolioBAO.omslogout(omsOrderVO);
                session.removeAttribute("OMSOrderVO");
                session.removeAttribute("sinkBp"); 
            }
            omsOrderVO.setOmsUserid(userSessionBean.getOmsLoginId());
            /* handling transaction from Add/Reduce and rebalance */
            //User trigger Add/Reduce feature
            if ((Boolean) session.getAttribute("AddReedemOrderExecution")) {
//                addRedeemFundsVO = addRedeemFundsBAO
//                        .getAddRedeemFundDetails(userSessionBean.getUserId());
//                addRedeemFundsVO.setCashBalanceAmount(addRedeemFundsVO.getCashAmount().doubleValue());
//                List<Object[]> addRedeemLogTbList = addRedeemFundsBAO.getlogAddRedeemFundDetails(addRedeemFundsVO);
//                this.setAddreedemFundVo(addRedeemLogTbList);
                addRedeemFundsVO = (AddRedeemFundsVO) session.getAttribute("AddRedeemFundsVO");
                this.calculateTransactionDetailsForAddreedemTransaction();
                //Set venue name for indetify offline status
                setVenueNameSet(addRedeemFundsVO.getVenuName());
            } else {//User trigger Re-balance
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
                    updateMarketData();
                }
            }
        } catch (Exception ex) {
            reDirectionURL = REBALANCE_PAGE;
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    private void updateMarketData() {
        PortfolioUtil.updateCustomerPortfolioMarketPrice(customerPortfolioVO);
        PortfolioUtil.updateValueAndWeight(customerPortfolioVO);
        PortfolioUtil.updateTransactionDetailsConsideringAsset(customerPortfolioVO);
        venueNameSet = customerPortfolioVO.getVenuName();
    }

    public void omsLoginRequest() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String error;
        AuthenticationRequestDTO requestDTO = OMSDTOFactory.getInstance().getAuthenticationRequestDTO();
        requestDTO.getDTOHeader().setVersion(PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
        requestDTO.getDTOHeader().setErrorCode(OMS_ERROR_CODE);
        requestDTO.getDTOHeader().setMessageType(MessageTypes.AUTHETICATION_REQUEST.value());
        requestDTO.getDTOHeader().setErrorCategory(OMS_ERROR_CATEGORY);
        requestDTO.setRequestType(AuthReqType.NORMAL.value());
        requestDTO.setLoginPassword(omsOrderVO.getOmsUserPassword());
        requestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setChannel(Channel.MMF.value());

        try {
            ardto = (AuthenticationResponseDTO) omsOrderVO.getOmsServices().login(requestDTO);

            if (ardto != null
                    && (ardto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE
                    || ardto.getDTOHeader().getErrorCode() == AuthenticationErrorCodes.INVALID_TFATOKEN.value())) {
                /**
                 * creating exchange offline/online status map for the first
                 * login of the day only. Using this map to determine offline
                 * status at the time of place an order. exchange status map
                 * resets at EOD resetexchangestatus task.
                 */
                if (LookupDataLoader.getVenueStatusMap().isEmpty()) {
                    getVenueOnlineStatus(ardto.getVenueSettings());
                }
                /**
                 * onlineStatus is set to false, if any of venues are offline
                 * else true.
                 */
                onlineStatus = getOnlineStatus();
                omsOrderVO.setOmsSessionKey(ardto.getDTOHeader().getSessionKey());
                omsOrderVO.setOmsRoleId(ardto.getRoleID());
                omsOrderVO.setOmsversion(ardto.getOmsVersion());
            }

            // for initial OMS login
            if (ardto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                OMSAdapterRv adapterRv = new OMSAdapterRv();
                adapterRv.subscribeSubject(omsOrderVO.getOmsUserid().toUpperCase(), omsOrderVO.getOmsSessionKey(), session);
                //call for getting DP client details
                rebalancePortfolioBAO.getDPClientMFdetails(omsOrderVO);
                omsLoginPageStatus = true;
                session.setAttribute("OMSOrderVO", omsOrderVO);

                if (session.getAttribute(TODAYS_DETAILS) != null && (Boolean) session.getAttribute(TODAYS_DETAILS)) {
                    if ((Boolean) session.getAttribute(REJECTION_SUMMARY)) {
                        reDirectionURL = "/pages/oms_rejection_summary?faces-redirect=true";;
                    } else {
                        reDirectionURL = TODAYS_PAGE;
                    }

                } else if (session.getAttribute("cancelOrder") != null && (Boolean) session.getAttribute("cancelOrder")) {
                    TodaysDetailsVO todaysDetailsVO = (TodaysDetailsVO) session.getAttribute("cancelDetails");
                    rebalancePortfolioBAO.cancelPlacedOrder(omsOrderVO, todaysDetailsVO);
                    session.removeAttribute("cancelOrder");
                    reDirectionURL = TODAYS_PAGE;
                } else if (session.getAttribute("sinkBp") != null && (Boolean) session.getAttribute("sinkBp")) {
                    List rptData = new ArrayList<List<Map>>();
                    rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, true);
                    saveCustomerBP(rptData);
                } else if (onlineStatus) {
//                    placeOrder(true);
                    reDirectionURL = ORDER_CONFIRMATION_PAGE;
                } else {
                    online = onlineStatus;
                    //Redirect to same page and display offlien alert message
                    reDirectionURL = EMPTY_STRING;
                }
            } //for two factor login
            else if (ardto.getDTOHeader().getErrorCode() == AuthenticationErrorCodes.INVALID_TFATOKEN.value()) {
                omsLoginPageStatus = false;
                twoFactorRequired = true;
                reDirectionURL = TWO_FACTOR_PAGE;
            } //In case if OMS initial login failed
            else {
                error = PropertiesLoader.getPropertiesValue(Integer.toString(ardto.getDTOHeader().getErrorCode()), PROPERTIES_FILE_OMS_ER);
                fc.addMessage("oms_login_form", new FacesMessage(SEVERITY_ERROR, error, null));
                reDirectionURL = EMPTY_STRING;
            }
            LOGGER.log(Level.INFO,
                    "*** OMS-Authentication-Response-DTO ***[**{0} **]",
                    ardto);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            reDirectionURL = EMPTY_STRING;
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            reDirectionURL = EMPTY_STRING;
        } catch (Exception ex) {
            Logger.getLogger(OMSLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        } else {
            if (customerPortfolioVO.getRelationId() != null) {
                relationId = customerPortfolioVO.getRelationId();
            }
            rebalancePortfolioBAO.executeSecurityOrders(customerPortfolioVO, portfolioDetailsVOList, omsOrderVO, oflineOrder);
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
        reDirectionURL = TODAYS_PAGE;
    }

    public void omsTFOLoginRequest() {
        if (twoFactorType.equals(PAN)) {
            omsTFOLogin(AuthReqType.TWOFACTORPAN.value(), pan);
        } else if (twoFactorType.equals(DOB)) {
            try {
                Date date = DateUtil.stringToDate(dob, DATE_FORMAT);
                omsTFOLogin(AuthReqType.TWOFACTORDOB.value(), DateUtil.dateToString(date, DD_SLASH_MM_SLASH_YYYY));
            } catch (ParseException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        } else if (twoFactorType.equals(SMS)) {
            omsTFOLogin(AuthReqType.TWOFACTOR.value(), sms);
        }
    }

    public void changeTwoFactorType(AjaxBehaviorEvent event) {
        if (twoFactorType.equals(SMS)) {
            ChangePasswordRequestDTO requestDTO = OMSDTOFactory.getInstance().getChangePasswordRequestDTO();
            requestDTO.getDTOHeader().setVersion(PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
            requestDTO.getDTOHeader().setErrorCode(OMS_ERROR_CODE);
            requestDTO.getDTOHeader().setMessageType(MessageTypes.CHANGE_PASSWORD_REQUEST.value());
            requestDTO.getDTOHeader().setErrorCategory(OMS_ERROR_CATEGORY);
            requestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
            requestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
            requestDTO.setOldPassword(SPACE);
            requestDTO.setNewPassword(SPACE);
            requestDTO.setMisc1("TFAToken");
            try {
                ChangePasswordResponseDTO ardto = (ChangePasswordResponseDTO) omsOrderVO.getOmsServices().changePassword(requestDTO);
            } catch (FrontEndException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            } catch (MapperException ex) {
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        }
    }

    private void omsTFOLogin(int requestType, String tfaToken) {

        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String error;
        AuthenticationRequestDTO requestDTO = OMSDTOFactory.getInstance().getAuthenticationRequestDTO();
        requestDTO.getDTOHeader().setVersion(PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
        requestDTO.getDTOHeader().setErrorCode(OMS_ERROR_CODE);
        requestDTO.getDTOHeader().setMessageType(MessageTypes.AUTHETICATION_REQUEST.value());
        requestDTO.getDTOHeader().setErrorCategory(OMS_ERROR_CATEGORY);
        requestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        requestDTO.setRequestType(requestType);
        requestDTO.setLoginPassword(omsOrderVO.getOmsUserPassword());
        requestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setTfaToken(tfaToken);
        requestDTO.setChannel(Channel.MMF.value());
        AuthenticationResponseDTO ardto;
        try {
            ardto = (AuthenticationResponseDTO) omsOrderVO.getOmsServices().login(requestDTO);
            if (ardto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {

                omsOrderVO.setOmsSessionKey(ardto.getDTOHeader().getSessionKey());
                omsOrderVO.setOmsRoleId(ardto.getRoleID());
                omsOrderVO.setOmsversion(ardto.getOmsVersion());
                rebalancePortfolioBAO.getDPClientMFdetails(omsOrderVO);
                session.setAttribute("OMSOrderVO", omsOrderVO);
                OMSAdapterRv adapterRv = new OMSAdapterRv();
                adapterRv.subscribeSubject(omsOrderVO.getOmsUserid().toUpperCase(), omsOrderVO.getOmsSessionKey(), session);
                if (session.getAttribute(TODAYS_DETAILS) != null && (Boolean) session.getAttribute(TODAYS_DETAILS)) {
                    reDirectionURL = TODAYS_PAGE;
                } else if (session.getAttribute("cancelOrder") != null && (Boolean) session.getAttribute("cancelOrder")) {
                    TodaysDetailsVO todaysDetailsVO = (TodaysDetailsVO) session.getAttribute("cancelDetails");
                    rebalancePortfolioBAO.cancelPlacedOrder(omsOrderVO, todaysDetailsVO);
                    session.removeAttribute("cancelOrder");
                    reDirectionURL = TODAYS_PAGE;
                } else if (session.getAttribute("sinkBp") != null && (Boolean) session.getAttribute("sinkBp")) {
                    List rptData = new ArrayList<List<Map>>();
                    rptData = rebalancePortfolioBAO.getPortfolioSummary(omsOrderVO, true);
                    saveCustomerBP(rptData);
                } else if (onlineStatus) {
                    reDirectionURL = ORDER_CONFIRMATION_PAGE;
                } else {
                    online = onlineStatus;
                    twoFactorRequired = false;
                    reDirectionURL = EMPTY_STRING;
                }
            } else {
                error = PropertiesLoader.getPropertiesValue(Integer.toString(ardto.getDTOHeader().getErrorCode()), PROPERTIES_FILE_OMS_ER);
                fc.addMessage("oms_login_form", new FacesMessage(SEVERITY_ERROR, error, null));
            }
            LOGGER.log(Level.INFO,
                    "*** OMS-Authentication-Response-DTO ***[**{0} **]",
                    ardto);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (Exception ex) {
            Logger.getLogger(OMSLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public OMSOrderVO getOmsOrderVO() {
        return omsOrderVO;
    }

    public void setOmsOrderVO(OMSOrderVO omsOrderVO) {
        this.omsOrderVO = omsOrderVO;
    }

    public String reDirectionURL() {
        return reDirectionURL;
    }

    public String getTwoFactorType() {
        return twoFactorType;
    }

    public void setTwoFactorType(String twoFactorType) {
        this.twoFactorType = twoFactorType;
    }

    public String getPan() {
        return pan;
    }

    public void setPan(String pan) {
        this.pan = pan;
    }

    public String getSms() {
        return sms;
    }

    public void setSms(String sms) {
        this.sms = sms;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isPAN() {
        return twoFactorType.equals(PAN);
    }

    public boolean isSMS() {
        return twoFactorType.equals(SMS);
    }

    public boolean isDOB() {
        return twoFactorType.equals(DOB);
    }

    public boolean isOmsLoginPageStatus() {
        return omsLoginPageStatus;
    }

    public void setOmsLoginPageStatus(boolean omsLoginPageStatus) {
        this.omsLoginPageStatus = omsLoginPageStatus;
    }

    public AddRedeemFundsVO getAddRedeemFundsVO() {
        return addRedeemFundsVO;
    }

    public void setAddRedeemFundsVO(AddRedeemFundsVO addRedeemFundsVO) {
        this.addRedeemFundsVO = addRedeemFundsVO;
    }

    private void calculateTransactionDetailsForAddreedemTransaction() {
        addRedeemFundsVO.setFromAddReedem(true);
        PortfolioUtil.updateCustomerPortfolioMarketPrice(addRedeemFundsVO);
        PortfolioUtil.updateValueAndWeight(addRedeemFundsVO);
        PortfolioUtil.updateTransactionDetails(addRedeemFundsVO);
    }

    private void setAddreedemFundVo(List<Object[]> addRedeemLogTbList) {

        if (!addRedeemLogTbList.isEmpty()) {
            for (Object[] addReedem : addRedeemLogTbList) {
                addRedeemFundsVO.setAddFund((Boolean) addReedem[0]);
                addRedeemFundsVO.setAddRedeemFund(((BigDecimal) addReedem[1]).doubleValue());
            }
        }
        /**
         * cash amount is set to previous value for calculating current weight
         * in add/redeem
         */
        addRedeemFundsVO.setCashAmount(addRedeemFundsVO.getAddFund() ? (addRedeemFundsVO.getCashAmount().floatValue() - addRedeemFundsVO.getAddRedeemFund().floatValue())
                : (addRedeemFundsVO.getCashAmount().floatValue()));
    }

    /**
     * method for creating exchangeStatusMap. key :- Exchange value:-
     * online/offline status. if exchange is offline value is false else true
     *
     * @param venueSettings
     */
    public void getVenueOnlineStatus(String venueSettings) {
        if (venueSettings != null) {
            try {
                String[] venue = venueSettings.split("\\|");
                boolean status;
                for (String venueSetting : venue) {
                    status = true;
                    String[] venueData = venueSetting.split("\\;");
                    Integer venueCode = Integer.parseInt(venueData[0]);
                    String exchange = EnumVenueCode.fromInt(venueCode).toString();
                    String[] venueStart = venueData[1].split(SPACE);
                    String[] venueClose = venueData[2].split(SPACE);
                    String startTime = venueStart[1];
                    String closeTime = venueClose[1];
                    StringBuilder venueTime = new StringBuilder();
                    venueTime.append(startTime).append(PIPE_SEPERATOR).append(closeTime);
                    SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
                    Date Start = parser.parse(venueStart[0]);
                    Date close = parser.parse(venueClose[0]);
                    SimpleDateFormat localDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    String time = localDateFormat.format(new Date());
                    Date current = parser.parse(time);
                    if (current.before(Start) || current.after(close)) {
                        status = false;
                    }
                    exchangeStatusMap.put(exchange, status);
                    exchangeTimeMap.put(exchange, venueTime.toString());
                }
                LookupDataLoader.setVenueStatusMap(exchangeStatusMap);
                LookupDataLoader.setVenueTimeMap(exchangeTimeMap);

            } catch (ParseException ex) {
                Logger.getLogger(OMSLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }

    public boolean isOfflinee_Enabled() {
        return offlinee_Enabled;
    }

    public void setOfflinee_Enabled(boolean offlinee_Enabled) {
        this.offlinee_Enabled = offlinee_Enabled;
    }

    public static HashMap<String, Boolean> getExchangeStatusMap() {
        return exchangeStatusMap;
    }

    /**
     * set flag allOflineOrder,if all exchanges of the order are offline String
     * oflineVenues - display exchanges that are offline.
     *
     * @return status
     */
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
                            exchangeStatusMap.remove(venue);
                            exchangeStatusMap.put(venue, status);
                        }
                    } catch (ParseException ex) {
                        Logger.getLogger(OMSLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                //uncomment this and comend line:597 ,for not placing any orders if any of the venues are offline.
                //onlineStatus = onlineStatus || status;
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
        session.setAttribute(PLACE_OFFLINE_ORDER, true);
        reDirectionURL = ORDER_CONFIRMATION_PAGE;
    }

    //User only allowed to place online order
    public void placeOfflineNo() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (allOflineOrder) {
            reDirectionURL = NAVIGATION_PAGE;
        } else {
            session.setAttribute(PLACE_OFFLINE_ORDER, false);
            reDirectionURL = ORDER_CONFIRMATION_PAGE;
        }
    }

    public Set<String> getVenueNameSet() {
        return venueNameSet;
    }

    public void setVenueNameSet(Set<String> venueNameSet) {
        this.venueNameSet = venueNameSet;
    }

    public String getOflineMsg() {
        return oflineMsg;
    }

    public void setOflineMsg(String oflineMsg) {
        this.oflineMsg = oflineMsg;
    }

    public boolean isTwoFactorRequired() {
        return twoFactorRequired;
    }

    public void setTwoFactorRequired(boolean twoFactorRequired) {
        this.twoFactorRequired = twoFactorRequired;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public void omsCancelRequest() {
        FacesContext fc = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        UserSessionBean userSessionBean = (UserSessionBean) fc.getExternalContext().getSessionMap().get(USER_SESSION);
        if (userSessionBean != null && userSessionBean.getRelationStatus() == 0) {
            reDirectionURL = (userSessionBean.getFromURL() != null ? userSessionBean.getFromURL() : NAVIGATION_PAGE);
        } else {
            reDirectionURL = (userSessionBean.getFromURL() != null ? userSessionBean.getFromURL() : REBALANCE_PAGE);
        }
    }

    public static HashMap<String, String> getExchangeTimeMap() {
        return exchangeTimeMap;
    }

    public String getOmsLoginFooter() {
        return omsLoginFooter;
    }

    public void setOmsLoginFooter(String omsLoginFooter) {
        this.omsLoginFooter = omsLoginFooter;
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
        rebalancePortfolioBAO.saveCustomerBP(userSessionBean.getOmsLoginId(),bp);
        reDirectionURL = NAVIGATION_PAGE;
    }
}
