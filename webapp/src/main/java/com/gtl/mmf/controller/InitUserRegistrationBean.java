/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.common.EnumAdvLoginStatus;
import com.gtl.mmf.common.EnumCustLoginStatus;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.entity.TempRegistrationTb;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.CONTEXT_ROOT;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.EMAIL_DIFF;
import static com.gtl.mmf.service.util.IConstants.EMAIL_ID_ACTIVATION_IN_PROGRESS;
import static com.gtl.mmf.service.util.IConstants.EMAIL_ID_ALREADY_REGISTERED;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.FROM_DASHBOARD;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_CONNECTED;
import static com.gtl.mmf.service.util.IConstants.LINKED_IN_URL;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.VERIFICATION_MAIL;
import static com.gtl.mmf.service.util.IConstants.YES;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.FailedMailDetailsVO;
import com.gtl.mmf.service.vo.UserProfileVO;
import com.gtl.mmf.service.vo.UserRegStatusVO;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import com.gtl.mmf.service.util.LookupDataLoader;

/**
 *
 * @author 07958
 */
@Named("initUserRegistrationBean")
@Scope("view")
public class InitUserRegistrationBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.InitUserRegistrationBean");
    private static final String EMAIL_ID = "emailId";
    private static final String ADVISOR_SELECTED = "advisor";
    private static final String LOGIN_LINKEDIN_FRM = "login_linkedin_frm";
    @Autowired
    private IUserProfileBAO userProfileBAO;
    private String email;
    private String reEnterEmail;
    private boolean advisor;
    private boolean userExists;
    private boolean status;
    private String newPassword;
    private String confirmPassword;
    private String redirecrTo;
    private boolean agreeTermsCondition;

    private String url = PropertiesLoader.getPropertiesValue(LINKED_IN_URL).replaceAll(CONTEXT_ROOT, LookupDataLoader.getContextRoot());

    private String TWO_FACTOR_LOGIN = "/pages/twofactor_login.xhtml?faces-redirect=true";
    private String DASHBOARD_ADVISOR = "/pages/advisordashboard.xhtml?faces-redirect=true";
    private String DASHBOARD_INVESTOR = "/pages/investordashboard.xhtml?faces-redirect=true";
    private String INVESTOR_REG = "/pages/reg_investor_profile?faces-redirect=true";
    private String ADVISOR_REG = "/pages/reg_advisor_profile?faces-redirect=true";
    private static final String USER_LOGIN_TYPE = "user_login_type";

    @PostConstruct
    public void afterinit() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        if (storedValues != null) {
            if (storedValues.get(EMAIL_ID) != null) {
                this.email = (String) storedValues.get(EMAIL_ID);
            }
            if (storedValues.get(IS_ADVISOR) != null) {
                this.advisor = (Boolean) storedValues.get(IS_ADVISOR);
            }
            if (storedValues.get(EMAIL_DIFF) != null) {
                String emailDiffMsg = "The LinkedIn profile already registered in MMF for " + storedValues.get("linkedInIdMappedEmail");
                FacesContext.getCurrentInstance().addMessage(LOGIN_LINKEDIN_FRM,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, emailDiffMsg,
                                emailDiffMsg));
            }
        }
    }

    public void doActionSignUp() {
        status = false;
        
        if (agreeTermsCondition && !email.isEmpty()) {
            UserRegStatusVO userRegStatusVO = userProfileBAO.isUserRegEmailExists(email);
            userExists = userRegStatusVO.isEmailExist();
            if (userExists) {
                status = false;
                if (userExists && userRegStatusVO.getRegStatus() == EnumStatus.NEW_APPLICANT.getValue()) {
                    FacesContext.getCurrentInstance().addMessage("frm_init_reg:email",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ACTIVATION_IN_PROGRESS, EMAIL_ID_ACTIVATION_IN_PROGRESS));
                } else {
                    FacesContext.getCurrentInstance().addMessage("frm_init_reg:email",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ALREADY_REGISTERED, EMAIL_ID_ALREADY_REGISTERED));
                }
            } else {
                if (newPassword.equalsIgnoreCase(confirmPassword)) {
                    status = userProfileBAO.saveTempUserProfile(email, newPassword, advisor);
                    StringBuilder logMessage = new StringBuilder("User sign up for registration with email id ");
                    logMessage.append(email).append(" as advisor status as ").append(advisor);
                    LOGGER.info(logMessage.toString());
                    if (!status) {
                        FacesContext.getCurrentInstance().addMessage("frm_init_reg:email",
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registration Failed", "Registration Failed"));
                    } else {
                        logMessage = new StringBuilder("User with email id ");
                        logMessage.append(this.email).append(" registered advisor as ")
                                .append(this.isAdvisor()).append(DOT);
                        LOGGER.info(logMessage.toString());

                    }
                } else {
                    status = false;
                    FacesContext.getCurrentInstance().addMessage("frm_init_reg:repassword",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Password not matching", "Password not matching"));
                }
            }
        }else{
            status = false;
            FacesContext.getCurrentInstance().addMessage("frm_init_reg:chk_box_agree",
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Please accept Terms & Conditions and Privacy Policy", "Please accept Terms & Conditions and Privacy Policy"));
        }
        

    }

    public void doActionLoginLinkedIn(ActionEvent event) {
        StringBuilder logMessage = new StringBuilder("User with email id ");
        logMessage.append(email).append(" logged in with linked in account for registration.");
        UserProfileVO userProfile = new UserProfileVO();
        userProfile.setEmail(email);
        userProfile.setAdvisor(advisor);
        userProfile.setName("test");
        userProfile.setAddress("test");
        userProfile.setMobile("9846338165");
        userProfile.setPincode("12344");
        userProfile.setTelephone("04842471883");
        userProfile.setCity("test");
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put("userProfile", userProfile);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
        LOGGER.info(logMessage.toString());
    }

    public String loginLinkedIn() {
        return redirecrTo;

    }

    public String signUp() {
        String redirectTo = null;
        if (status) {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            storedValues.put(IS_ADVISOR, advisor);
            storedValues.put(EMAIL_ID, this.email);
            storedValues.put(EXPIRE_IN, false);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = "/pages/init_reg_verify?faces-redirect=true";
        }
        return redirectTo;
    }

    public Map<String, Object> getRequestMap() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> requestMap = ec.getRequestMap();
        return requestMap;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getReEnterEmail() {
        return reEnterEmail;
    }

    public void setReEnterEmail(String reEnterEmail) {
        this.reEnterEmail = reEnterEmail;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String loginLinkedInCancel() {
        String redirectTo = "../index.xhtml?faces-redirect=true";
        return redirectTo;
    }

    public void loginSkipLinkedInActionListener() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        if (storedValues != null) {
            Boolean advisor = (Boolean) storedValues.get(IS_ADVISOR);
            userProfileBAO.updateLinkedIn(storedValues.get("username"), advisor);
            storedValues.put(LINKEDIN_CONNECTED, false);
            if (storedValues.get(FROM_DASHBOARD) != null && (Boolean) storedValues.get(FROM_DASHBOARD)) {
                storedValues.remove(FROM_DASHBOARD);
                getSessionMap().put(STORED_VALUES, storedValues);
                if (!advisor) {
                    redirecrTo = DASHBOARD_INVESTOR;
                } else {
                    redirecrTo = DASHBOARD_ADVISOR;
                }
            } else {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                UserSessionBean userSessionBean = (UserSessionBean) externalContext.getSessionMap().get(USER_SESSION);
                String usertype = storedValues.get("userType").toString();
                String username = storedValues.get("username").toString();
                String page = "";
                if (userSessionBean != null) {
                    userSessionBean.setLinkedInConnected(false);
                }
                getSessionMap().put(STORED_VALUES, storedValues);
                //userProfileBAO.getCustomerInitLoginStatus(username, usertype);
                page = getCustomerInitLoginStatus(userSessionBean.getInitLogin() == ONE ? true : false, usertype);
                redirecrTo = "/pages/" + page + "?faces-redirect=true";
                userSessionBean.setFromURL(redirecrTo); 
            }
        }
    }

    private String getCustomerInitLoginStatus(boolean initLogin, String usertype) {
        String redirecTo = "";
        if (usertype.equalsIgnoreCase(YES)) {
            if (initLogin) {
                redirecTo = EnumAdvLoginStatus.fromInt(1).toString();
            } else {
                redirecTo = EnumAdvLoginStatus.fromInt(2).toString();
            }

        } else {
            if (initLogin) {
                redirecTo = EnumCustLoginStatus.fromInt(1).toString();
            } else {
                redirecTo = EnumCustLoginStatus.fromInt(2).toString();
            }
        }
        return redirecTo;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getRedirecrTo() {
        return redirecrTo;
    }

    public void setRedirecrTo(String redirecrTo) {
        this.redirecrTo = redirecrTo;
    }

    private Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public boolean isAgreeTermsCondition() {
        return agreeTermsCondition;
    }

    public void setAgreeTermsCondition(boolean agreeTermsCondition) {
        this.agreeTermsCondition = agreeTermsCondition;
    }
    
    public String signIn(){
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, Object> storedValues = (Map<String, Object>) context.getExternalContext().getSessionMap().get(STORED_VALUES);
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        boolean loginType = params.get(USER_LOGIN_TYPE).equals(EMPTY_STRING) ? true : Boolean.parseBoolean(params.get(USER_LOGIN_TYPE));
        String userType = loginType ? INVESTOR:ADVISOR;
        storedValues.put(USER_LOGIN_TYPE, userType);
        getSessionMap().put(STORED_VALUES, storedValues);
        return "/pages/login.xhtml?faces-redirect=true";
    }
    
    public String onClickResendMail() {
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        List<FailedMailDetailsVO> reGenerteMailList = new ArrayList<FailedMailDetailsVO>();
        FailedMailDetailsVO detailsVO = new FailedMailDetailsVO();
        TempRegistrationTb registrationTb = new TempRegistrationTb();
        String email = (String) storedValues.get(EMAIL_ID);
        boolean user = (Boolean) storedValues.get(IS_ADVISOR);
        registrationTb = userProfileBAO.getTempRegistrationTableData(email, user);
        detailsVO.setEmail_purpose(VERIFICATION_MAIL); 
        detailsVO.setEmail(registrationTb.getEmail());
        detailsVO.setUserType(registrationTb.getUserType());
        detailsVO.setVerificationCode(registrationTb.getVerificationCode());
        reGenerteMailList.add(detailsVO);
        userProfileBAO.reSendMail(reGenerteMailList);
        FacesContext.getCurrentInstance().addMessage("frm_verify_msg", new FacesMessage("Mail send successfully.Please check."));
        return EMPTY_STRING;
    }
}
