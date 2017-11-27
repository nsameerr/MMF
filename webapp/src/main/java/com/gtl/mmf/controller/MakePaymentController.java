/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.service.util.CheckSumGenerater;
import static com.gtl.mmf.service.util.IConstants.ADVISOR_DATA;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_MSG;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_URL;
import static com.gtl.mmf.service.util.IConstants.CHECKSUM_KEY;
import static com.gtl.mmf.service.util.IConstants.EQ_SIGN;
import static com.gtl.mmf.service.util.IConstants.FROM_PAYMENT_RESPONSE;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.MANDATE_DATA;
import static com.gtl.mmf.service.util.IConstants.MERCHANT_ID;
import static com.gtl.mmf.service.util.IConstants.NA;
import static com.gtl.mmf.service.util.IConstants.PIPE_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.QUESTION_MARK;
import static com.gtl.mmf.service.util.IConstants.REG_PAGE_DATA;
import static com.gtl.mmf.service.util.IConstants.RETURN_URL;
import static com.gtl.mmf.service.util.IConstants.SECURITY_ID;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TRANSACTIONRESPONSE;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.MandateVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TransactionResponseVo;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author trainee8
 */
@Component("paymentController")
@Scope("view")
public class MakePaymentController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.MakePaymentController");
    private static final String CURRENCY_TYPE = "INR";
    private final String TYPEFIELD1 = "R";
    private final String TYPEFIELD2 = "F";
    private final String USER_AGENT = "Mozilla/5.0";
    String transferAmount;
    Boolean paymentReponse;
    TransactionResponseVo transactionResponseVo;
    String msg;
    String returnUrlBilldesk;
    private RegistrationVo registrationVo;
    @Autowired
    private IUserProfileBAO userProfileBAO;
    private boolean status;
    private boolean isAdvisor;
    private UserProfileVO userProfile;
    private MandateVo mandateVo;

    @PostConstruct
    public void afterInit() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        paymentReponse = (Boolean) getStoredValues().get(FROM_PAYMENT_RESPONSE);
        if (paymentReponse) {
            transactionResponseVo = (TransactionResponseVo) getStoredValues().get(TRANSACTIONRESPONSE);
        }
        if (session.getAttribute(IS_ADVISOR) != null) {
            isAdvisor = (Boolean) session.getAttribute(IS_ADVISOR);
        }
    }

    public void doActionSubmit() {
        String clientID = null;
        String returnUrl = PropertiesLoader.getPropertiesValue(RETURN_URL);
        String merchantId = PropertiesLoader.getPropertiesValue(MERCHANT_ID);
        String securityID = PropertiesLoader.getPropertiesValue(SECURITY_ID);
        String checkSumKey = PropertiesLoader.getPropertiesValue(CHECKSUM_KEY);
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        StringBuilder billdeskUrl = new StringBuilder();
        billdeskUrl.append(PropertiesLoader.getPropertiesValue(BILLDESK_URL))
                .append(QUESTION_MARK);
        if (userSessionBean != null) {
            clientID = userSessionBean.getRegId();
        }
        StringBuilder msg = new StringBuilder();
        msg.append(BILLDESK_MSG).append(EQ_SIGN).append(merchantId)
                .append(PIPE_SEPERATOR).append(clientID)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append("2")
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(CURRENCY_TYPE)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(TYPEFIELD1)
                .append(PIPE_SEPERATOR).append(securityID)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(TYPEFIELD2)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(NA)
                .append(PIPE_SEPERATOR).append(returnUrl);
        //Generate CheckSumValue
        String checkSum = CheckSumGenerater.generateCheckSum(msg.toString(), checkSumKey);
        try {
            msg = msg.append(PIPE_SEPERATOR).append(checkSum);
            returnUrlBilldesk = billdeskUrl.append(msg).toString();
        } catch (Exception ex) {
            LOGGER.log(Logger.Level.ERROR, StackTraceWriter.getStackTrace(ex));
        }
        try {
            LOGGER.log(Logger.Level.INFO, "Sending billdesk request,Client ID: " + clientID);
            FacesContext.getCurrentInstance().getExternalContext().redirect(returnUrlBilldesk);
            LOGGER.log(Logger.Level.INFO, "Sending billdesk request success,Client ID: " + clientID);
        } catch (IOException ex) {
            LOGGER.log(Logger.Level.ERROR, StackTraceWriter.getStackTrace(ex));
        }
    }

    public String getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(String transferAmount) {
        this.transferAmount = transferAmount;
    }

    private Map<String, Object> getStoredValues() {
        return (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
    }

    public Boolean getPaymentReponse() {
        return paymentReponse;
    }

    public void setPaymentReponse(Boolean paymentReponse) {
        this.paymentReponse = paymentReponse;
    }

    public TransactionResponseVo getTransactionResponseVo() {
        return transactionResponseVo;
    }

    public void setTransactionResponseVo(TransactionResponseVo transactionResponseVo) {
        this.transactionResponseVo = transactionResponseVo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReturnUrlBilldesk() {
        return returnUrlBilldesk;
    }

    public void setReturnUrlBilldesk(String returnUrlBilldesk) {
        this.returnUrlBilldesk = returnUrlBilldesk;
    }

    public String returnPathURL() {
        return returnUrlBilldesk;
    }

    public void saveUserProfile(ActionEvent event) {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
//        if (isAdvisor) {
//            userProfile = (UserProfileVO) storedValues.get(ADVISOR_DATA);
//            this.userProfile.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
//            status = userProfileBAO.saveAdvisorProfile(userProfile, false);
//        } else {
            registrationVo = (RegistrationVo) storedValues.get(REG_PAGE_DATA);
            mandateVo = (MandateVo) storedValues.get(MANDATE_DATA);
            registrationVo.setUser_status(EnumStatus.NEW_APPLICANT.getValue());
            status = userProfileBAO.saveInvestorProfile(this.registrationVo, false);
            if (status) {
                userProfileBAO.saveMandate(this.mandateVo, registrationVo.getRegId());
            }
//        }

    }

    public void save() {
        String redirectionPage;
        if (status) {
            // redirectionPage = "/pages/init_reg_success?faces-redirect=true";
            this.doActionSubmit();
        }/* else {
         redirectionPage = "";
         }
         return redirectionPage;*/

    }

    public boolean isIsAdvisor() {
        return isAdvisor;
    }

    public void setIsAdvisor(boolean isAdvisor) {
        this.isAdvisor = isAdvisor;
    }

}
