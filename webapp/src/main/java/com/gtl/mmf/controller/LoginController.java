/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserLoginBAO;
import com.gtl.mmf.common.EnumStatus;
import com.gtl.mmf.service.util.BASE64Encrption;
import com.gtl.mmf.service.util.DateUtil;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.COOKIE_USERNAME;
import static com.gtl.mmf.service.util.IConstants.COOKIE_VIRTUALCHECK;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.EXPIRY;
import static com.gtl.mmf.service.util.IConstants.FAILURE;
import static com.gtl.mmf.service.util.IConstants.FALSE;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.LINKEDIN_FAIL;
import static com.gtl.mmf.service.util.IConstants.MAIL_VERIFICATION;
import static com.gtl.mmf.service.util.IConstants.NO;
import static com.gtl.mmf.service.util.IConstants.PWD_CHNG_SUCC;
import static com.gtl.mmf.service.util.IConstants.RESET_PWD;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TRUE;
import static com.gtl.mmf.service.util.IConstants.USERTYPE;
import static com.gtl.mmf.service.util.IConstants.YES;
import static com.gtl.mmf.service.util.IConstants.YYYY_SLASH_MM_SLASH_DD;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.vo.LoginVO;
import com.gtl.mmf.util.StackTraceWriter;
import com.octo.captcha.service.CaptchaServiceException;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.jboss.logging.Logger;
import org.jboss.logging.Logger.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07960
 */
@Component("loginController")
@Scope("view")
public class LoginController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.LoginController");
    private static final String LOGIN_FAILED = "loginfailed";
    private static final String ERROR_MSG_FIELD = "frm_login:login_btn";
    private static final String ERROR_MSG_LOGIN = "Invalid login credentials.";
    private static final String PASSWORD_CHNG_SUCC_MSG = "Password changed successfully.Please login with new password.";
    private static final String TWOFACTOR_AUTH_FAIL_MSG = "Two-factor authentication failed.";
    private static final String REDIRECT_TWO_FACTOR_LOGIN_PAGE = "/pages/twofactor_login.xhtml?faces-redirect=true";
    private static final String LINKED_IN_COMMUNICATION_FAIL_MSG = "LinkedIn communication failed.Please login again.";
    private static final String CAPTCHA_IMAGE_URL = "/generateCaptcha";
    private static final String USER_TYPE = "user_type";
    private static final String ADVISOR_SELECTED = "advisor";
    private static final String EMAIL_ID = "emailId";
    private static final String REGISTRATION_PROGRESS_MSG = "Your Registration is in Progress..";
    private static final String MAIL_VERIFIED_LOGIN = "Mail verification completed.Please login to complete registration.";

    @Autowired
    private IUserLoginBAO userLoginBAO;
    private String username;
    private String password;
    private Date dob;
    private boolean advisor;
    private boolean status;
    private LoginVO userLogin;
    private boolean rememberMe;
    private boolean rememberMeStatus;
    private String virtualCheck = EMPTY_STRING;
    private Boolean isCaptchaValid;
    private String userType;
    private String selectedUserType;
    Boolean verification;
    private static final String USER_LOGIN_TYPE = "user_login_type";
    private String captchaKey = LookupDataLoader.getPublicCaptchaKey();

    @PostConstruct
    public void afterInit() {
        isRemembermeChecked();
        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        verification = false;
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(USERTYPE) != null) {
            String loginType = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get(USERTYPE);
            userType = BASE64Encrption.decrypt(loginType);
        }
        if (FacesContext.getCurrentInstance().getExternalContext()
                .getRequestParameterMap().get(MAIL_VERIFICATION) != null) {
            String parameter = FacesContext.getCurrentInstance().getExternalContext()
                    .getRequestParameterMap().get(MAIL_VERIFICATION);
            FacesContext.getCurrentInstance().addMessage("frm_login",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, MAIL_VERIFIED_LOGIN,
                            MAIL_VERIFIED_LOGIN));
        }
        if (!verification) {
            if (sessionMap.get(PWD_CHNG_SUCC) != null) {
                FacesContext.getCurrentInstance().addMessage("frm_login",
                        new FacesMessage(FacesMessage.SEVERITY_INFO, PASSWORD_CHNG_SUCC_MSG,
                                PASSWORD_CHNG_SUCC_MSG));
                sessionMap.remove(PWD_CHNG_SUCC);
            } else if (sessionMap.get(LINKEDIN_FAIL) != null) {
                FacesContext.getCurrentInstance().addMessage("frm_login",
                        new FacesMessage(FacesMessage.SEVERITY_INFO, LINKED_IN_COMMUNICATION_FAIL_MSG,
                                LINKED_IN_COMMUNICATION_FAIL_MSG));
                sessionMap.remove(LINKEDIN_FAIL);
            }

        }
        if (storedValues != null && storedValues.get(USER_LOGIN_TYPE) != null) {
            this.userType = (String) storedValues.get(USER_LOGIN_TYPE);
        }
    }

    public void doActionLoginListner(ActionEvent event) {
        Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        userType = userType != null ? userType : params.get(USER_LOGIN_TYPE);
        LoginVO login = new LoginVO();
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Integer investorStatus = null;
        login.setUsername(username);
        login.setPassword(password);
        if (userType != null && userType.equals(ADVISOR)) {
            advisor = Boolean.TRUE;
            login.setUsertype(YES);
        } else if (userType != null && userType.equals(INVESTOR)) {
            advisor = Boolean.FALSE;
            login.setUsertype(NO);
        }

        if (userType == null) {
            session.setAttribute("loginStatus", false);
            FacesContext.getCurrentInstance().addMessage(ERROR_MSG_FIELD,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, null, "Please select a user type"));
        } else {
            session.setAttribute("registration", false);
            investorStatus = userLoginBAO.getUserStatus(login.getUsername(), login.getPassword(), userType);
            if (investorStatus != null && investorStatus == EnumStatus.NEW_APPLICANT.getValue()) {
                // set session attribute for partialy activated customer
                session.setAttribute("loginStatus", false);
                session.setAttribute("newapplicant", true);
                session.setAttribute("advisor", advisor);
                FacesContext.getCurrentInstance().addMessage(ERROR_MSG_FIELD,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, REGISTRATION_PROGRESS_MSG, REGISTRATION_PROGRESS_MSG));
            } else if (investorStatus != null && investorStatus == EnumStatus.RESET_PWD.getValue()) {
                session.setAttribute("advisor", advisor);
                session.setAttribute("registration", true);
                session.setAttribute("loginStatus", true);
                session.setAttribute("changePassword", true);
            } else if (investorStatus != null && investorStatus == EnumStatus.MAIL_VERIFIED.getValue()) {
                session.setAttribute("advisor", advisor);
                session.setAttribute("registration", true);
                session.setAttribute("loginStatus", true);
                session.setAttribute("changePassword", false);

            } else {
                userLogin = userLoginBAO.userLogin(login);
                session.setAttribute("advisor", advisor);
                if (!userLogin.getRedirectPage().equals(FAILURE)) {

                    session.setAttribute("loginStatus", true);
                    //Remember
                    HttpServletResponse response = ((HttpServletResponse) FacesContext
                            .getCurrentInstance().getExternalContext().getResponse());
                    if (isRememberMe()) {
                        setVirtualCheck(TRUE);
                        Cookie cookieUserName = new Cookie(COOKIE_USERNAME, getUsername());
                        Cookie cookieVirtualCheck = new Cookie(COOKIE_VIRTUALCHECK, this.virtualCheck);
                        cookieUserName.setMaxAge(EXPIRY);
                        cookieVirtualCheck.setMaxAge(EXPIRY);
                        response.addCookie(cookieUserName);
                        response.addCookie(cookieVirtualCheck);
                    } else {
                        Cookie cookieVirtualCheck = new Cookie(COOKIE_VIRTUALCHECK, FALSE);
                        Cookie cookieUserName = new Cookie(COOKIE_USERNAME, null);
                        cookieUserName.setMaxAge(ZERO);
                        cookieVirtualCheck.setMaxAge(ZERO);
                        response.addCookie(cookieVirtualCheck);
                        response.addCookie(cookieUserName);
                    }

                } else {
                    session.setAttribute("loginStatus", false);
                    requestMap.put(LOGIN_FAILED, true);
                    if (requestMap.get(LOGIN_FAILED) != null && (Boolean) requestMap.get(LOGIN_FAILED)) {
                        if (investorStatus != null && investorStatus == EnumStatus.MAIL_NOT_VERIFIED.getValue()) {
                            FacesContext.getCurrentInstance().addMessage(ERROR_MSG_FIELD,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Mail not verified", "Mail not verified"));
                        } else {
                            FacesContext.getCurrentInstance().addMessage(ERROR_MSG_FIELD,
                                    new FacesMessage(FacesMessage.SEVERITY_ERROR, ERROR_MSG_LOGIN, ERROR_MSG_LOGIN));
                        }

                    }
                }

            }
        }
    }

    public String loginRedirectFirst() throws IOException {
        String redirectTo = EMPTY_STRING;
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Date curr_date = new Date();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        boolean linkedINexpiered;
        if ((Boolean) session.getAttribute("loginStatus") != null
                && (Boolean) session.getAttribute("loginStatus")) {
            if ((Boolean) session.getAttribute("registration") != null
                    && (Boolean) session.getAttribute("registration")) {
                if (advisor) {
                    storedValues.put(IS_ADVISOR, true);
                    storedValues.put(EMAIL_ID, username);
                    storedValues.put(EXPIRE_IN, false);
                    if ((Boolean) session.getAttribute("changePassword")) {
                        storedValues.put("userType", YES);
                        storedValues.put("username", username);
                        session.setAttribute(RESET_PWD, true);
                        storedValues.put(RESET_PWD, true);
                        redirectTo = "/pages/changePassword?faces-redirect=true";
                    } else {
                        redirectTo = "/pages/reg_advisor_profile?faces-redirect=true";
                    }
                } else {
                    storedValues.put(IS_ADVISOR, false);
                    storedValues.put(EMAIL_ID, username);
                    storedValues.put(EXPIRE_IN, false);
                    if ((Boolean) session.getAttribute("changePassword")) {
                        storedValues.put("userType", NO);
                        storedValues.put("username", username);
                        session.setAttribute(RESET_PWD, true);
                        storedValues.put(RESET_PWD, true);
                        redirectTo = "/pages/changePassword?faces-redirect=true";
                    } else {
                        redirectTo = "/pages/reg_investor_profile?faces-redirect=true";
                    }
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);

            } else if (userLogin.isLinkedInConnected()) {
                //Check linked in token expiered
                linkedINexpiered = userLoginBAO.isLinkedinExpireIn(DateUtil.dateToString(curr_date,
                        YYYY_SLASH_MM_SLASH_DD), userLogin.getUsername());

                //Linkedin token not expiered
                if (linkedINexpiered) {
                    storedValues.put("username", username);
                    storedValues.put(EXPIRE_IN, false);
                    if (advisor) {
                        storedValues.put(IS_ADVISOR, true);
                        storedValues.put("userType", YES);
                        storedValues.put("password", password);
                    } else {
                        storedValues.put(IS_ADVISOR, false);
                        storedValues.put("userType", NO);
                        storedValues.put("password", password);
                    }
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                    redirectTo = REDIRECT_TWO_FACTOR_LOGIN_PAGE;
                } else {
                    storedValues.put(EXPIRE_IN, true);
                    storedValues.put("username", username);
                    if (advisor) {
                        storedValues.put(IS_ADVISOR, true);
                        storedValues.put("userType", YES);
                        storedValues.put("password", password);
                    } else {
                        storedValues.put(IS_ADVISOR, false);
                        storedValues.put("userType", NO);
                        storedValues.put("password", password);
                    }
                    FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                    redirectTo = REDIRECT_TWO_FACTOR_LOGIN_PAGE;
                }
            } else if (!userLogin.isLinkedInConnected()) {
                storedValues.put("username", username);
                if (advisor) {
                    storedValues.put(IS_ADVISOR, true);
                    storedValues.put("userType", YES);
                    storedValues.put("password", password);
                } else {
                    storedValues.put(IS_ADVISOR, false);
                    storedValues.put("userType", NO);
                    storedValues.put("password", password);
                }
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                if (userLogin.getInitStatus() != null && userLogin.getInitStatus() == ZERO) {
                    //for redirecting user to change password
                    session.setAttribute(RESET_PWD, true);
                    storedValues.put(RESET_PWD, true);
                    redirectTo = "/pages/" + userLogin.getRedirectPage() + "?faces-redirect=true";
                } else {
                    redirectTo = REDIRECT_TWO_FACTOR_LOGIN_PAGE;
                }

            }
        } else {
            redirectTo = EMPTY_STRING;
        }
        return redirectTo;
    }

    public String loginRedirect() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String redirectTo = userLogin.getRedirectPage();
        if (!FAILURE.equals(redirectTo)) {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            UserSessionBean userSessionBean = null;
            userSessionBean = (UserSessionBean) session.getAttribute("userSession");
            setUsername(userSessionBean.getUsername());

            if (userSessionBean.getUserType().equalsIgnoreCase(ADVISOR)) {
                advisor = true;
                storedValues.put("userType", YES);
            } else if (userSessionBean.getUserType().equalsIgnoreCase(INVESTOR)) {
                advisor = false;
                storedValues.put("userType", NO);
            }
            storedValues.put("username", username);
            storedValues.put("password", userLogin.getPassword());
            storedValues.put(IS_ADVISOR, advisor);
            storedValues.put(EMAIL_ID, username);

            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = "/pages/" + redirectTo + "?faces-redirect=true";
            userSessionBean.setToURL(redirectTo);
        } else {
            FacesContext.getCurrentInstance().addMessage("frm_login:login_btn",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, TWOFACTOR_AUTH_FAIL_MSG,
                            TWOFACTOR_AUTH_FAIL_MSG));
            redirectTo = EMPTY_STRING;
        }
        return redirectTo;
    }

    public void doActionTwoFactorLoginListner(ActionEvent event) {
        try {
//            String HTTPS_PROXYHOST = "https.proxyHost";
//            String HTTPS_PROXYPORT = "https.proxyPort";
//            System.setProperty(HTTPS_PROXYHOST, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYHOST));
//            System.setProperty(HTTPS_PROXYPORT, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYPORT));

            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            LoginVO login = new LoginVO();
            Map<String, Object> valuesMap = null;
            valuesMap = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
            login.setDob(dob);
            login.setUsername(valuesMap.get("username").toString());
            login.setUsertype(valuesMap.get("userType").toString());
            login.setPassword(valuesMap.get("password").toString());

            userLogin = userLoginBAO.userTwoFactorLogin(login);
            if (valuesMap.get(EXPIRE_IN) != null && (Boolean) valuesMap.get(EXPIRE_IN)) {
                if (userLogin.getUsertype().equals(YES)) {
                    userLogin.setRedirectPage("login_linkedin_advisor");
                } else {
                    userLogin.setRedirectPage("login_linkedin_investor");
                }
            }
            if (userLogin != null) {
                if (!userLogin.getRedirectPage().equals(FAILURE)) {
                    UserSessionBean userSessionBean = new UserSessionBean();

                    if (userLogin.getUsertype().equals(YES)) {
                        userSessionBean.setUserId(userLogin.getMasterAdvisor().getAdvisorId());
                        userSessionBean.setEmail(userLogin.getMasterAdvisor().getEmail());
                        userSessionBean.setRegId(userLogin.getMasterAdvisor().getRegistrationId());
                        userSessionBean.setStatus(userLogin.getMasterAdvisor().getStatus());
                        userSessionBean.setUserType(ADVISOR);
                        userSessionBean.setUsername(userLogin.getUsername());
                        userSessionBean.setAccessToken(userLogin.getAccessToken());
                        userSessionBean.setLinkedInId(userLogin.getLinkedInId());
                        userSessionBean.setFirstName(userLogin.getFirstName());
                        userSessionBean.setMiddleName(userLogin.getMiddleName());
                        userSessionBean.setLastName(userLogin.getLastName());
                        userSessionBean.setRelationId(userLogin.getRelationId() == null ? 0 : userLogin.getRelationId());
                        userSessionBean.setRelationStatus(userLogin.getRelationStatus() == null ? 0 : userLogin.getRelationStatus());
                        userSessionBean.setLinkedInConnected(userLogin.isLinkedInConnected());
                        userSessionBean.setInitLogin(userLogin.getInitStatus());
                    } else {
                        userSessionBean.setUserId(userLogin.getMasterCustomer().getCustomerId());
                        userSessionBean.setEmail(userLogin.getMasterCustomer().getEmail());
                        userSessionBean.setRegId(userLogin.getMasterCustomer().getRegistrationId());
                        userSessionBean.setStatus(userLogin.getMasterCustomer().getStatus());
                        userSessionBean.setUserType(INVESTOR);
                        userSessionBean.setUsername(userLogin.getUsername());
                        userSessionBean.setInvestorWithAdvisor(userLogin.getInvestorWithAdvisor());
                        userSessionBean.setAccessToken(userLogin.getAccessToken());
                        userSessionBean.setLinkedInId(userLogin.getLinkedInId());
                        userSessionBean.setFirstName(userLogin.getFirstName());
                        userSessionBean.setMiddleName(userLogin.getMiddleName());
                        userSessionBean.setLastName(userLogin.getLastName());
                        userSessionBean.setRelationId(userLogin.getRelationId() == null ? 0 : userLogin.getRelationId());
                        userSessionBean.setRelationStatus(userLogin.getRelationStatus() == null ? 0 : userLogin.getRelationStatus());
                        userSessionBean.setLinkedInConnected(userLogin.isLinkedInConnected());
                        userSessionBean.setInitLogin(userLogin.getInitStatus());
                        userSessionBean.setOmsLoginId(userLogin.getMasterCustomer().getOmsLoginId());
                        userSessionBean.setFromURL(userLogin.getRedirectPage());
                    }
                    session.setAttribute("userSession", userSessionBean);
                }
            }
        } catch (CaptchaServiceException ex) {
            LOGGER.log(Level.ERROR, StackTraceWriter.getStackTrace(ex));
        } catch (NullPointerException ex) {
            LOGGER.log(Level.ERROR, StackTraceWriter.getStackTrace(ex));
        }
    }

    public String captchaImageURL() {
        return CAPTCHA_IMAGE_URL;
    }

    public void onChangeUser() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        userType = params.get(USER_TYPE);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isAdvisor() {
        return advisor;
    }

    public void setAdvisor(boolean advisor) {
        this.advisor = advisor;
    }

    public IUserLoginBAO getUserLoginBAO() {
        return userLoginBAO;
    }

    public void setUserLoginBAO(IUserLoginBAO userLoginBAO) {
        this.userLoginBAO = userLoginBAO;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public LoginVO getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(LoginVO userLogin) {
        this.userLogin = userLogin;
    }

    public boolean isRememberMeStatus() {
        return rememberMeStatus;
    }

    public void setRememberMeStatus(boolean rememberMeStatus) {
        this.rememberMeStatus = rememberMeStatus;
    }

    public String getVirtualCheck() {
        return virtualCheck;
    }

    public void setVirtualCheck(String virtualCheck) {
        this.virtualCheck = virtualCheck;
    }

    public Boolean isIsCaptchaValid() {
        return isCaptchaValid;
    }

    public void setIsCaptchaValid(Boolean isCaptchaValid) {
        this.isCaptchaValid = isCaptchaValid;
    }

    private boolean isRemembermeChecked() {
        boolean status = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        Cookie cookieAttr[] = ((HttpServletRequest) (faceContext.
                getExternalContext().getRequest())).getCookies();
        if (cookieAttr != null && cookieAttr.length > ZERO) {
            for (int i = ZERO; i < cookieAttr.length; i++) {
                String cookieName = cookieAttr[i].getName();
                String cookieValue = cookieAttr[i].getValue();
                if (cookieName.equals(COOKIE_USERNAME)) {
                    setUsername(cookieValue);
                }
                if (cookieName.equals(COOKIE_VIRTUALCHECK)) {
                    this.virtualCheck = cookieValue;
                    if (this.virtualCheck.equals(TRUE)) {
                        status = true;
                        this.rememberMe = true;
                    }

                }
            }
        }
        return status;
    }

    public Boolean isVerification() {
        return verification;
    }

    public void setVerification(Boolean verification) {
        this.verification = verification;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }

}
