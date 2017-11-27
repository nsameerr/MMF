/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.linkedin.util.LinkedInUtil;
import com.gtl.linkedin.util.LinkedInVO;
import com.gtl.linkedin.util.ServiceParamVO;
import com.gtl.linkedin.util.ServiceTypeEnum;
import com.gtl.mmf.bao.IInvestmentAdvProfileBAO;
import com.gtl.mmf.bao.IInvestorMappingBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import static com.gtl.mmf.service.util.IConstants.BTN_ACCEPT;
import static com.gtl.mmf.service.util.IConstants.BTN_CANCEL;
import static com.gtl.mmf.service.util.IConstants.BTN_INVITE_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.BTN_REJECT;
import static com.gtl.mmf.service.util.IConstants.BTN_WITHDRAW;
import static com.gtl.mmf.service.util.IConstants.CONNECTION_LEVEL_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.CONTRACT_TERMINATED_STAUS;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVESTMENTADVISORPROFILE;
import static com.gtl.mmf.service.util.IConstants.INVESTOR_UNAVAILABLE_ALERT;
import static com.gtl.mmf.service.util.IConstants.NO_LINKEDIN_CONNECTION;
import static com.gtl.mmf.service.util.IConstants.SECURITY_ALLOCATION_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.SELECTED_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.SHARED_CONNECTIONS_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.ButtonDetailsVo;
import com.gtl.mmf.service.vo.InvestmentRelationStatusVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07958
 */
@Component("investmentAdvProfileController")
@Scope("view")
public class InvestmentAdvProfileController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.InvestmentAdvProfileController");
    private static final String PREVIOUS_PAGE = "/pages/investor_network_view?faces-redirect=true";
    private AdvisorDetailsVO advisorDetails;
    private List<ButtonDetailsVo> buttonsList;
    private String redirectTo;
    @Autowired
    private IInvestorMappingBAO investorMappingBAO;
    @Autowired
    private IInvestmentAdvProfileBAO investmentAdvProfileBAO;
    private int customerId;
    private String investorName;
    private boolean investorUnavailable;
    private InvestmentRelationStatusVO advisorProfileVO;
    private InvestmentRelationStatusVO advisorRelationStatus;
    private boolean isContract = false;
    private int relationId;
    private boolean showContractTerminateButton = false;
    String email;

    @PostConstruct
    public void afterInit() {
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        Map<String, Object> storedValues = (Map<String, Object>) sessionMap.get(STORED_VALUES);
        UserSessionBean userSessionBean = (UserSessionBean) sessionMap.get(USER_SESSION);
        userSessionBean.setFromURL(INVESTMENTADVISORPROFILE);
        if (storedValues != null) {
            advisorDetails = (AdvisorDetailsVO) storedValues.get(SELECTED_ADVISOR);
            LinkedInVO linkedInVO = null;

            if (advisorDetails.getConnectionLevel() == null) {
                if (userSessionBean.isLinkedInConnected()) {
                    ServiceParamVO serPVO = new ServiceParamVO();
                    serPVO.setConvertedIds(advisorDetails.getLinkedInMemberid());
                    linkedInVO = LinkedInUtil.getInstance().getLinkedInResponseVO(userSessionBean
                            .getAccessToken(), serPVO, ServiceTypeEnum.SHARED_CONNECTIONS);
                }
                if (linkedInVO != null) {
                    advisorDetails.setConnectionLevel((linkedInVO.getDistance() == null
                            || linkedInVO.getDistance().equals("0")) || linkedInVO.getDistance()
                            .equals("-1") || linkedInVO.getDistance().equals("100") ? CONNECTION_LEVEL_DEFAULT : linkedInVO.getDistance());
                    advisorDetails.setConnectionShared(linkedInVO.getConnections() == null
                            ? SHARED_CONNECTIONS_DEFAULT : linkedInVO.getConnections().getTotal());
                } else if (advisorDetails.isLinkedInConnected()) {
                    advisorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    advisorDetails.setConnectionShared(SHARED_CONNECTIONS_DEFAULT);
                } else if (!advisorDetails.isLinkedInConnected()) {
                    advisorDetails.setConnectionLevel(CONNECTION_LEVEL_DEFAULT);
                    advisorDetails.setConnectionShared(NO_LINKEDIN_CONNECTION);
                }
            }
        } else {
            advisorDetails = new AdvisorDetailsVO();
        }
        if (userSessionBean != null) {
            customerId = userSessionBean.getUserId();
            relationId = userSessionBean.getRelationId();
            this.investorUnavailable = userSessionBean.getInvestorWithAdvisor();
            this.investorName = userSessionBean.getFirstName() + " " + userSessionBean.getLastName();
            email = userSessionBean.getEmail();
        }
        advisorRelationStatus = investmentAdvProfileBAO.getAdvisorRelationStatus(
                advisorDetails.getRelationId() == null ? ZERO : advisorDetails.getRelationId(), customerId);
        if(advisorRelationStatus.getContractStatus().equalsIgnoreCase(CONTRACT_TERMINATED_STAUS)){
             advisorRelationStatus.setAllocatedFund(advisorRelationStatus.getAllocatedFund());
        }else{
            advisorRelationStatus.setAllocatedFund(advisorRelationStatus.getTotalAvailableFund());
        }
        EnumCustomerMappingStatus status = EnumCustomerMappingStatus.fromInt(advisorDetails.getStatus());
        EnumCustomerMappingStatus buttonStatus = status == EnumCustomerMappingStatus.ADVISOR_INVITE_DECLINED
                || status == EnumCustomerMappingStatus.ADVISOR_WITHDRAW
                || status == EnumCustomerMappingStatus.INVITE_DECLINED
                || status == EnumCustomerMappingStatus.WITHDRAW
                || status == EnumCustomerMappingStatus.CONTRACT_TERMINATED
                ? EnumCustomerMappingStatus.NO_RELATION : status;
        setButtonsList(LookupDataLoader.getInvestorButtonsList().get(buttonStatus.getValue()));

        // hiding invite button if invester already signed contract
        if (advisorDetails.getCanDisableInvite()) {
            List<ButtonDetailsVo> clientButtonList = new ArrayList<ButtonDetailsVo>();
            if (getButtonsList() != null) {
                clientButtonList.addAll(getButtonsList());
                if (clientButtonList != null) {
                    for (ButtonDetailsVo buttonDetails : clientButtonList) {
                        if (buttonDetails.getButtonText().equalsIgnoreCase(BTN_INVITE_ADVISOR)) {
                            clientButtonList.remove(buttonDetails);
                        }
                    }
                    setButtonsList(clientButtonList);
                }
            }
        }

        //Show Terminate contract button when admin initialize contract termination
        if(investmentAdvProfileBAO.getContractTerminationStatus(relationId,advisorDetails.getAdvisorId())) {
            showContractTerminateButton = true;
        } else {
            showContractTerminateButton = false;
        }

    }

    public void doActionListner(ActionEvent event) {
        String buttonText = (String) event.getComponent().getAttributes().get("value");
        redirectTo = EMPTY_STRING;
        if (buttonText.equalsIgnoreCase(BTN_CANCEL)) {
            this.doActionCancel();
        } else if (buttonText.equalsIgnoreCase(BTN_INVITE_ADVISOR)) {
            this.doActionInviteAdvisor();
        } else if (buttonText.equalsIgnoreCase(BTN_WITHDRAW)) {
            this.doActionWithdraw();
        } else if (buttonText.equalsIgnoreCase(BTN_REJECT)) {
            this.doActionReject();
        } else if (buttonText.equalsIgnoreCase(BTN_ACCEPT)) {
            this.doActionAccept();
        }
    }

    private void doActionReject() {
        try {
            investorMappingBAO.declineAdvisorInvite(advisorDetails, customerId, investorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
        }
        redirectTo = PREVIOUS_PAGE;
    }

    private void doActionAccept() {
        if (investorUnavailable) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(INVESTOR_UNAVAILABLE_ALERT));
            redirectTo = EMPTY_STRING;
        } else {
            if (isAllcatedFundValid()) {
                try {
                    investorMappingBAO.acceptAdvisor(advisorDetails, customerId,
                            advisorRelationStatus.getAllocatedFund(), investorName);
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
                }
                redirectTo = PREVIOUS_PAGE;
            }
        }
    }

    private boolean isAllcatedFundValid() {
        boolean valid = false;
        if (advisorRelationStatus.getAllocatedFund() <= SECURITY_ALLOCATION_DEFAULT) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Please insert allocated funds."));
            redirectTo = EMPTY_STRING;
        } else if (advisorRelationStatus.getAllocatedFund() > advisorRelationStatus.getTotalAvailableFund()) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Allocated funds canot excede total available funds."));
            redirectTo = EMPTY_STRING;
        } else {
            valid = true;
        }
        return valid;
    }

    private void doActionWithdraw() {
        try {
            investorMappingBAO.withdrawAdvisor(advisorDetails, customerId, investorName);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        redirectTo = PREVIOUS_PAGE;
    }

    private void doActionInviteAdvisor() {
        if (investorUnavailable) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(INVESTOR_UNAVAILABLE_ALERT));
            redirectTo = EMPTY_STRING;
        } else {
            if (isAllcatedFundValid()) {
                try {
                    investorMappingBAO.inviteAdvisor(advisorDetails, customerId,
                            advisorRelationStatus.getAllocatedFund(), investorName);
                } catch (ClassNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(ex));
                }
                redirectTo = PREVIOUS_PAGE;
            }
        }
    }

    public void onTeminateContract() {
        boolean isContractTerminated = investorMappingBAO.contractTerminate(relationId,email,investorName);
        if (isContractTerminated) {
            System.out.println("Contract Terminated Successfully.");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Contract Terminated Successfully."));
            showContractTerminateButton = false;
        } else {
            System.out.println("There is no contract to terminate for this user!!");
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("There is no contract to terminate for this user!!"));
        }
        redirectTo = EMPTY_STRING;
    }

    private void doActionCancel() {
        redirectTo = PREVIOUS_PAGE;
    }

    public String actionRedirect() {
        return redirectTo;
    }

    public AdvisorDetailsVO getAdvisorDetails() {
        return advisorDetails;
    }

    public void setAdvisorDetails(AdvisorDetailsVO advisorDetails) {
        this.advisorDetails = advisorDetails;
    }

    public List<ButtonDetailsVo> getButtonsList() {
        return buttonsList;
    }

    public void setButtonsList(List<ButtonDetailsVo> buttonsList) {
        this.buttonsList = buttonsList;
    }

    public InvestmentRelationStatusVO getAdvisorProfileVO() {
        return advisorProfileVO;
    }

    public void setAdvisorProfileVO(InvestmentRelationStatusVO advisorProfileVO) {
        this.advisorProfileVO = advisorProfileVO;
    }

    public InvestmentRelationStatusVO getAdvisorRelationStatus() {
        return advisorRelationStatus;
    }

    public void setAdvisorRelationStatus(InvestmentRelationStatusVO advisorRelationStatus) {
        this.advisorRelationStatus = advisorRelationStatus;
    }

    public boolean isIsContract() {
        return isContract;
    }

    public void setIsContract(boolean isContract) {
        this.isContract = isContract;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public boolean isShowContractTerminateButton() {
        return showContractTerminateButton;
    }

    public void setShowContractTerminateButton(boolean showContractTerminateButton) {
        this.showContractTerminateButton = showContractTerminateButton;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

}
