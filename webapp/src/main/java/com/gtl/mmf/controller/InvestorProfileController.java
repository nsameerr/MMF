/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.ADVISOR_UNAVAILABLE_ALERT;
import static com.gtl.mmf.service.util.IConstants.BTN_ACCEPT;
import static com.gtl.mmf.service.util.IConstants.BTN_CANCEL;
import static com.gtl.mmf.service.util.IConstants.BTN_INVITE_CLIENT;
import static com.gtl.mmf.service.util.IConstants.BTN_REJECT;
import static com.gtl.mmf.service.util.IConstants.BTN_SUBMIT_QUESTIONNAIRE;
import static com.gtl.mmf.service.util.IConstants.BTN_WITHDRAW_INVITE;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_UNAVAILABLE;
import static com.gtl.mmf.service.util.IConstants.SELECTED_INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.SUCCESS;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IAdvisorMappingBAO;
import com.gtl.mmf.common.EnumAdvisorMappingStatus;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SHARED_CONNECTIONS_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SPACE;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;

/**
 *
 * @author 07958
 */
@Named("investorProfileController")
@Scope("view")
public class InvestorProfileController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.InvestorProfileController");
    private static final String PREVIOUS_PAGE = "/pages/advisor_network_view?faces-redirect=true";
    @Autowired
    private IAdvisorMappingBAO advisorMappingBAO;
    private InvestorDetailsVO investorProfile;
    private List<ButtonDetailsVo> buttonsList;
    private String redirectTo;
    private Integer advisorId;
    private String advisorName;
    private String sharedConnection;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        if (storedValues != null) {
            investorProfile = (InvestorDetailsVO) storedValues.get("selectedInvestor");
        } else {
            investorProfile = new InvestorDetailsVO();
        }
        if (userSessionBean != null) {
            advisorId = userSessionBean.getUserId();
            StringBuilder name = new StringBuilder();
            name.append(userSessionBean.getFirstName()).append(SPACE)
                    .append(userSessionBean.getMiddleName()).append(SPACE)
                    .append(userSessionBean.getLastName());
            advisorName = name.toString();
        }

        EnumAdvisorMappingStatus status = EnumAdvisorMappingStatus.fromInt(investorProfile.getStatus());
        EnumAdvisorMappingStatus buttonStatus = (status == EnumAdvisorMappingStatus.INVITE_DECLINED
                || status == EnumAdvisorMappingStatus.INVESTOR_WITHDRAW)
                        ? EnumAdvisorMappingStatus.NO_RELATION : status;
//        setButtonsList(LookupDataLoader.getAdvisorButtonsList().get(buttonStatus.getValue()));
        this.setButtonsList(getAdvButtonsList().get(buttonStatus.getValue()));
        // hiding invite button if invester already signed contract
        if (investorProfile.getCanDisableInvite()) {
            List<ButtonDetailsVo> allowedButtonList = new ArrayList<ButtonDetailsVo>();
            if (getButtonsList() != null) {
                allowedButtonList.addAll(getButtonsList());
                if (allowedButtonList != null) {
                    for (ButtonDetailsVo buttonDetails : allowedButtonList) {
                        if (buttonDetails.getButtonText().equalsIgnoreCase(BTN_INVITE_CLIENT)) {
                            allowedButtonList.remove(buttonDetails);
                        }
                    }
                    setButtonsList(allowedButtonList);
                }
            }
        }
        LinkedInVO linkedInVO = null;
        if (userSessionBean.isLinkedInConnected() && investorProfile == null ? true
                : investorProfile.isLinkedInConnected()) {
            ServiceParamVO serPVO = new ServiceParamVO();
            serPVO.setConvertedIds(investorProfile.getLinkedInId());
            linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(userSessionBean.getAccessToken(),
                    serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
            this.sharedConnection = (String) ((linkedInVO == null || linkedInVO.getConnections() == null)
                    ? "0" : linkedInVO.getConnections().getTotal());
        }
        if (linkedInVO != null) {
            investorProfile.setConnectionLevel((linkedInVO.getDistance() == null
                    || linkedInVO.getDistance().equals("0")
                    || linkedInVO.getDistance().equals("-1") || linkedInVO.getDistance().equals("100")) ? CONNECTION_LEVEL_DEFAULT : linkedInVO.getDistance());
        } else if (investorProfile.isLinkedInConnected()) {
            investorProfile.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
            this.sharedConnection = SHARED_CONNECTIONS_DEFAULT;
        } else if (!investorProfile.isLinkedInConnected()) {
            investorProfile.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
            this.sharedConnection = SHARED_CONNECTIONS_DEFAULT;
        }
        LOGGER.log(Level.INFO, "investor profile loaded with investor id {0} and advisor id {1}", new Object[]{investorProfile.getCustomerId(), advisorId});
    }

    public void doActionListner(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        redirectTo = EMPTY_STRING;
        if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            this.doActionCancel();
        } else if (buttonText.equalsIgnoreCase(BTN_INVITE_CLIENT)) {
            this.doActionInviteClient();
        } else if (buttonText.equalsIgnoreCase(BTN_WITHDRAW_INVITE)) {
            this.doActionWithdraw();
        } else if (buttonText.equalsIgnoreCase(BTN_REJECT)) {
            this.doActionDeclineInvite();
        } else if (buttonText.equalsIgnoreCase(BTN_ACCEPT)) {
            this.doActionAccept();
        } else if (buttonText.equalsIgnoreCase(BTN_SUBMIT_QUESTIONNAIRE)) {
            this.doActionSentQuestionnaire();
        }
    }

    private void doActionDeclineInvite() {
        try {
            advisorMappingBAO.declineInvestorInvite(investorProfile, advisorId, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        redirectTo = PREVIOUS_PAGE;
    }

    private void doActionAccept() {
        String response = EMPTY_STRING;
        try {
            response = advisorMappingBAO.acceptInvestor(investorProfile, advisorId, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        if (response.equalsIgnoreCase(SUCCESS)) {
            investorProfile.setStatus(EnumAdvisorMappingStatus.INVITE_ACCEPTED.getValue());
            Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            Map<String, Object> storedValues = new HashMap<String, Object>();
            storedValues.put(SELECTED_INVESTOR, investorProfile);
            sessionMap.put(STORED_VALUES, storedValues);
            redirectTo = "/pages/advisory_service_contract?faces-redirect=true";
        } else if (response.equalsIgnoreCase(INVESTOR_UNAVAILABLE)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ADVISOR_UNAVAILABLE_ALERT, "title"));
            redirectTo = EMPTY_STRING;
        }
    }

    private void doActionWithdraw() {
        try {
            advisorMappingBAO.withdrawInvestor(investorProfile, advisorId, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        redirectTo = PREVIOUS_PAGE;
    }

    private void doActionInviteClient() {
        String response = EMPTY_STRING;
        try {
            response = advisorMappingBAO.inviteInvestor(investorProfile, advisorId, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        if (response.equalsIgnoreCase(SUCCESS)) {
            redirectTo = PREVIOUS_PAGE;
        } else if (response.equalsIgnoreCase(INVESTOR_UNAVAILABLE)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, ADVISOR_UNAVAILABLE_ALERT, "title"));
            redirectTo = EMPTY_STRING;
        }
    }

    private void doActionSentQuestionnaire() {
        try {
            advisorMappingBAO.sentQuestionnaire(investorProfile, advisorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        redirectTo = PREVIOUS_PAGE;
    }

    private void doActionCancel() {
        redirectTo = PREVIOUS_PAGE;
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

    public String getSharedConnection() {
        return sharedConnection;
    }

    public void setSharedConnection(String sharedConnection) {
        this.sharedConnection = sharedConnection;
    }

    public Map<Integer, List<ButtonDetailsVo>> getAdvButtonsList() {
        return LookupDataLoader.getAdvisorButtonsList();
    }
}
