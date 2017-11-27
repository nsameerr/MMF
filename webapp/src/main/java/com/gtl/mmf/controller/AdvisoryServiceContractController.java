/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IAdvisorMappingBAO;
import com.gtl.mmf.bao.IAdvisoryServiceContractBAO;
import com.gtl.mmf.bao.IInvestorMappingBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.ADVISOR_UNAVAILABLE_ALERT;
import static com.gtl.mmf.service.util.IConstants.BTN_ACCEPT;
import static com.gtl.mmf.service.util.IConstants.BTN_CANCEL;
import static com.gtl.mmf.service.util.IConstants.BTN_DECLINE;
import static com.gtl.mmf.service.util.IConstants.BTN_REVIEW;
import static com.gtl.mmf.service.util.IConstants.BTN_SUBMIT;
import static com.gtl.mmf.service.util.IConstants.BTN_WITHDRAW;
import static com.gtl.mmf.service.util.IConstants.COLON;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEFALUT_REVIEW_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.DEFALUT_REVIEW_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_UNAVAILABLE;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_UNAVAILABLE_ALERT;
import static com.gtl.mmf.service.util.IConstants.REVIEW_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.SUCCESS;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.ContractDetailsVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07958
 */
@Named("advisoryServiceContractController")
@Scope("view")
public class AdvisoryServiceContractController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AdvisoryServiceContractController");
    private static final String CONTRACT_DATE_FORMAT = "dd-MM-yyyy";
    private static final String ACCEPT_WITH_REVIEW = "Please click review button if you have any review";
    private String previousPage;
    private InvestorDetailsVO investorProfile;
    private AdvisorDetailsVO advisorProfile;
    private List<ButtonDetailsVo> buttonsList;
    private Map<String, Integer> aumPayableList;
    private Map<String, Integer> performanceFeeFreqList;
    private Map<String, Integer> durationFreqList;
    private Map<String, Integer> durationList;
    private Map<String, Integer> mgmtFeeFixedAmountList;
    private Map<String, BigDecimal> mgmtFeeVariableAmountList;
    private Map<String, BigDecimal> performanceFeeList;
    private Map<String, BigDecimal> performanceFeeThresholdList;
    private String redirectTo;
    private ContractDetailsVO contractDetailsVO;
    @Autowired
    private IAdvisoryServiceContractBAO advisoryServiceContractBAO;
    @Autowired
    private IAdvisorMappingBAO advisorMappingBAO;
    @Autowired
    private IInvestorMappingBAO investorMappingBAO;
    private Date contractStartDate;
    private boolean advisorUser;
    private boolean investorUnavailable;
    private int userId;
    private String advisorName;
    private String investorName;
    private String sharedConnection;
    boolean aggrementAccepted = true;
    private Date startDate;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            userSessionBean.setFromURL("/pages/advisory_service_contract?faces-redirect=true");
            if (userSessionBean.getUserType().equals(ADVISOR)) {
                this.advisorUser = true;
                this.userId = userSessionBean.getUserId();
                this.advisorName = userSessionBean.getFirstName() + " " + userSessionBean.getLastName();
                previousPage = "/pages/advisor_network_view?faces-redirect=true";
                if (storedValues != null) {
                    investorProfile = (InvestorDetailsVO) storedValues.get(SELECTED_INVESTOR);
                    contractDetailsVO = advisoryServiceContractBAO.getContractDetails(investorProfile.getRelationId(), investorProfile.getStatus());
                    this.contractStartDate = contractDetailsVO.getStartDate();
                } else {
                    investorProfile = new InvestorDetailsVO();
                }
                setButtonsList(LookupDataLoader.getAdvisorButtonsList().get(investorProfile.getStatus()));
                ServiceParamVO serPVO = new ServiceParamVO();
                serPVO.setConvertedIds(investorProfile.getLinkedInId());
                LinkedInVO linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(userSessionBean.getAccessToken(), serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
                this.sharedConnection = (String) ((linkedInVO == null || linkedInVO.getConnections() == null) ? "0" : linkedInVO.getConnections().getTotal());
                if (linkedInVO != null) {
                    investorProfile.setConnectionLevel((linkedInVO.getDistance() == null
                            || linkedInVO.getDistance().equals("0")
                            || linkedInVO.getDistance().equals("-1")) ? CONNECTION_LEVEL_DEFAULT : linkedInVO.getDistance());
                } else {
                    investorProfile.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                }

            } else if (userSessionBean.getUserType().equals(INVESTOR)) {
                this.advisorUser = false;
                this.userId = userSessionBean.getUserId();
                this.investorUnavailable = userSessionBean.getInvestorWithAdvisor();
                this.investorName = userSessionBean.getFirstName() + " " + userSessionBean.getLastName();
                previousPage = "/pages/investor_network_view?faces-redirect=true";
                if (storedValues != null) {
                    advisorProfile = (AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR);
                    contractDetailsVO = advisoryServiceContractBAO.getContractDetails(advisorProfile.getRelationId(), advisorProfile.getStatus());
                    this.contractStartDate = contractDetailsVO.getStartDate();
                } else {
                    advisorProfile = new AdvisorDetailsVO();
                }
                setButtonsList(LookupDataLoader.getInvestorButtonsList().get(advisorProfile.getStatus()));
                ServiceParamVO serPVO = new ServiceParamVO();
                serPVO.setConvertedIds(advisorProfile.getLinkedInMemberid());

                LinkedInVO linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(userSessionBean.getAccessToken(), serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
                this.sharedConnection = (String) ((linkedInVO == null || linkedInVO.getConnections() == null) ? "0" : linkedInVO.getConnections().getTotal());

                if (advisorProfile.getConnectionLevel() == null) {
                    advisorProfile.setConnectionLevel((linkedInVO == null || linkedInVO.getDistance() == null
                            || linkedInVO.getDistance().equals("0")
                            || linkedInVO.getDistance().equals("-1")) ? CONNECTION_LEVEL_DEFAULT : linkedInVO.getDistance());
                }

            }
        }
        setAumPayableList(LookupDataLoader.getAUMPayableFrequencyList());
        setMgmtFeeFixedAmountList(LookupDataLoader.getMgmtFeeFixedAmountList());
        setDurationFreqList(LookupDataLoader.getContractDurationFreqList());
        setDurationList(LookupDataLoader.getDurationList());
        setPerformanceFeeFreqList(LookupDataLoader.getPerformanceFeeFreqList());
        setMgmtFeeVariableAmountList(LookupDataLoader.getMgmtFeeVariableAmountList());
        setPerformanceFeeList(LookupDataLoader.getPerformanceFeeList());
        setPerformanceFeeThresholdList(LookupDataLoader.getPerformanceFeeThresholdList());
    }

    public void changeMgmtFeeType() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, String> requestParameterMap = ec.getRequestParameterMap();
        contractDetailsVO.setMgmtFeeTypeVariable(Boolean.valueOf(requestParameterMap.get("variable")));
    }

    public void doActionChangeDuration(AjaxBehaviorEvent event) {
        this.contractDetailsVO.setDurationFrequency(contractDetailsVO.getPerformanceFeeFrequency());
    }

    public void doActionListnerInvestor(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        redirectTo = EMPTY_STRING;
        if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            doActionCancel();
        } else if (buttonText.equalsIgnoreCase(BTN_WITHDRAW) || buttonText.equalsIgnoreCase(BTN_DECLINE)) {
            doActionWithdrawInvestor();
        } else {
            if (investorUnavailable) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(INVESTOR_UNAVAILABLE_ALERT));
            } else {
                if (buttonText.equalsIgnoreCase(BTN_ACCEPT)) {
                    doActionAcceptInvestor();
                } else if (buttonText.equalsIgnoreCase(BTN_REVIEW)) {
                    doActionReviewInvestor();
                }
            }
        }
    }

    public void doActionListnerAdvisor(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        redirectTo = EMPTY_STRING;
        if (buttonText.equalsIgnoreCase(BTN_WITHDRAW)) {
            doActionWithdrawAdvisor();
        } else if (buttonText.equalsIgnoreCase(BTN_SUBMIT)) {
            doActionSubmitAdvisor();
        } else if (buttonText.equalsIgnoreCase(BTN_DECLINE)) {
            doActionCancel();
        } else if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            doActionCancel();
        }
    }

    private void doActionWithdrawInvestor() {
        advisorMappingBAO.withdrawAdvisor(advisorProfile, userId);
        redirectTo = previousPage;
    }

    private void doActionWithdrawAdvisor() {
        try {
            advisorMappingBAO.withdrawInvestor(investorProfile, userId, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        redirectTo = previousPage;
    }

    private void doActionAcceptInvestor() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (contractDetailsVO.getInvestorReview().equals(DEFALUT_REVIEW_INVESTOR)) {
            UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
            this.advisorName = advisorProfile.getFirstName();
            investorProfile = new InvestorDetailsVO();
            investorProfile.setCustomerId(contractDetailsVO.getCustomerId());
            investorProfile.setAllocatedInvestments(contractDetailsVO.getAllocatedFunds());
            investorProfile.setRelationId(contractDetailsVO.getRelationId());
            StringBuilder name = new StringBuilder();
            name.append(userSessionBean.getFirstName()).append(SPACE)
                    .append(userSessionBean.getMiddleName()).append(SPACE)
                    .append(userSessionBean.getLastName());
            investorProfile.setFirstName(name.toString());
            investorProfile.setEmail(userSessionBean.getEmail()); 
            try {
                investorMappingBAO.contractAccept(contractDetailsVO, advisorProfile, investorName);
                userSessionBean.setRelationStatus(EnumCustomerMappingStatus.CONTRACT_SIGNED.getValue());
                advisorMappingBAO.sentQuestionnaire(investorProfile, advisorName);
                userSessionBean.setRelationStatus(EnumAdvisorMappingStatus.QUESTIONNAIRE_SENT.getValue()); 
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
            }
            userSessionBean.setInvestorWithAdvisor(true);
            // redirectTo = "/pages/investordashboard?faces-redirect=true";
            //redirectTo = "/pages/investor_questionnaire?faces-redirect=true";//-SumeetChanges
            redirectTo = "/pages/v2/riskProfile.jsp?faces-redirect=true";
            session.setAttribute(USER_SESSION, userSessionBean);
        } else {
            FacesContext.getCurrentInstance().addMessage("frm_service_contract:review",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ACCEPT_WITH_REVIEW, null));
            redirectTo = EMPTY_STRING;
        }

    }

    private void doActionReviewInvestor() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        if (contractDetailsVO.getInvestorReview().equals(DEFALUT_REVIEW_INVESTOR)) {
            FacesContext.getCurrentInstance().addMessage("frm_service_contract:review",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please write your review.", "Please write your review."));
            redirectTo = EMPTY_STRING;
        } else {
            StringBuilder reviewBuilder = new StringBuilder();
            UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
            reviewBuilder.append(userSessionBean.getFirstName()).append(COLON).append(contractDetailsVO.getInvestorReview());
            contractDetailsVO.setInvestorReview(reviewBuilder.toString());
            try {
                investorMappingBAO.contractReview(contractDetailsVO, advisorProfile, investorName);
                userSessionBean.setRelationStatus(EnumCustomerMappingStatus.CONTRACT_REVIEW.getValue()); 
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
            }
            redirectTo = previousPage;
            session.setAttribute(USER_SESSION, userSessionBean);
        }
    }

    private boolean isStartDateValid(Date startDate) {
        boolean valid = false;
        Date dateNow;
        try {
            dateNow = DateUtil.stringToDate(DateUtil.dateToString(new Date(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            valid = !(startDate.before(dateNow));
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }

        return valid;
    }

    private void doActionSubmitAdvisor() {
//        try {
//            Date startDate = DateUtil.stringToDate(contractStartDate, DD_SLASH_MM_SLASH_YYYY);
        if (isStartDateValid(contractStartDate)) {
            contractDetailsVO.setStartDate(contractStartDate);
            StringBuilder reviewBuilder = new StringBuilder();
            StringBuilder prevReview = new StringBuilder();
            UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
            if (contractDetailsVO.getPreviousReviews() != null) {
                for (String reviewItem : contractDetailsVO.getPreviousReviews()) {
                    prevReview.append(reviewItem).append(REVIEW_SEPERATOR);
                }
            }
            Integer reviewFrequency = contractDetailsVO.getReviewFrequency() == null ? 1 : +contractDetailsVO.getReviewFrequency() + 0;
            if (!contractDetailsVO.getInvestorReview().equals(DEFALUT_REVIEW_ADVISOR)) {
                reviewFrequency = (contractDetailsVO.getReviewFrequency() == null ? 1 : contractDetailsVO.getReviewFrequency() + 1);
                reviewBuilder.append(userSessionBean.getFirstName()).append(COLON).append(contractDetailsVO.getInvestorReview());
            }
            prevReview.append(reviewBuilder);
            contractDetailsVO.setInvestorReview(prevReview.toString());
            String response = EMPTY_STRING;
            try {
                response = advisorMappingBAO.submitContract(contractDetailsVO, reviewFrequency, investorProfile, advisorName);
            } catch (ClassNotFoundException ex) {
                LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
            }
            if (response.equalsIgnoreCase(SUCCESS)) {
                redirectTo = previousPage;
            } else if (response.equalsIgnoreCase(INVESTOR_UNAVAILABLE)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(ADVISOR_UNAVAILABLE_ALERT));
                redirectTo = EMPTY_STRING;
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("frm_service_contract:start_date",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Past date cannot used as start date.", "Past date cannot used as start date."));
        }
//        } catch (ParseException ex) {
//            Logger.getLogger(AdvisoryServiceContractController.class.getName()).log(Level.SEVERE, null,StackTraceWriter.getStackTrace(ex));
//        }
    }

    private void doActionCancel() {
        redirectTo = previousPage;
    }

    public String actionRedirect() {
        return redirectTo;
    }

    public InvestorDetailsVO getInvestorProfile() {
        return investorProfile;
    }

    public void setInvestorProfile(InvestorDetailsVO investorProfile) {
        this.investorProfile = investorProfile;
    }

    public List<ButtonDetailsVo> getButtonsList() {
        return buttonsList;
    }

    public void setButtonsList(List<ButtonDetailsVo> buttonsList) {
        this.buttonsList = buttonsList;
    }

    public Map<String, Integer> getAumPayableList() {
        return aumPayableList;
    }

    public void setAumPayableList(Map<String, Integer> aumPayableList) {
        this.aumPayableList = aumPayableList;
    }

    public Map<String, Integer> getPerformanceFeeFreqList() {
        return performanceFeeFreqList;
    }

    public void setPerformanceFeeFreqList(Map<String, Integer> performanceFeeFreqList) {
        this.performanceFeeFreqList = performanceFeeFreqList;
    }

    public Map<String, Integer> getDurationFreqList() {
        return durationFreqList;
    }

    public void setDurationFreqList(Map<String, Integer> durationFreqList) {
        this.durationFreqList = durationFreqList;
    }

    public Map<String, Integer> getMgmtFeeFixedAmountList() {
        return mgmtFeeFixedAmountList;
    }

    public void setMgmtFeeFixedAmountList(Map<String, Integer> mgmtFeeFixedAmountList) {
        this.mgmtFeeFixedAmountList = mgmtFeeFixedAmountList;
    }

    public Map<String, Integer> getDurationList() {
        return durationList;
    }

    public void setDurationList(Map<String, Integer> durationList) {
        this.durationList = durationList;
    }

    public Map<String, BigDecimal> getMgmtFeeVariableAmountList() {
        return mgmtFeeVariableAmountList;
    }

    public void setMgmtFeeVariableAmountList(Map<String, BigDecimal> mgmtFeeVariableAmountList) {
        this.mgmtFeeVariableAmountList = mgmtFeeVariableAmountList;
    }

    public Map<String, BigDecimal> getPerformanceFeeList() {
        return performanceFeeList;
    }

    public void setPerformanceFeeList(Map<String, BigDecimal> performanceFeeList) {
        this.performanceFeeList = performanceFeeList;
    }

    public Map<String, BigDecimal> getPerformanceFeeThresholdList() {
        return performanceFeeThresholdList;
    }

    public void setPerformanceFeeThresholdList(Map<String, BigDecimal> performanceFeeThresholdList) {
        this.performanceFeeThresholdList = performanceFeeThresholdList;
    }

    public ContractDetailsVO getContractDetailsVO() {
        return contractDetailsVO;
    }

    public void setContractDetailsVO(ContractDetailsVO contractDetailsVO) {
        this.contractDetailsVO = contractDetailsVO;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public boolean isAdvisorUser() {
        return advisorUser;
    }

    public void setAdvisorUser(boolean advisorUser) {
        this.advisorUser = advisorUser;
    }

    public AdvisorDetailsVO getAdvisorProfile() {
        return advisorProfile;
    }

    public void setAdvisorProfile(AdvisorDetailsVO advisorProfile) {
        this.advisorProfile = advisorProfile;
    }

    public String getSharedConnection() {
        return sharedConnection;
    }

    public void setSharedConnection(String sharedConnection) {
        this.sharedConnection = sharedConnection;
    }

    public boolean isAggrementAccepted() {
        return aggrementAccepted;
    }

    public void setAggrementAccepted(boolean aggrementAccepted) {
        this.aggrementAccepted = aggrementAccepted;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
}
