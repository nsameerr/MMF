/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.common.EnumStatus;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.EMAIL_ID_ACTIVATION_IN_PROGRESS;
import static com.gtl.mmf.service.util.IConstants.EMAIL_ID_ALREADY_REGISTERED;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import com.gtl.mmf.service.vo.UserRegStatusVO;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07960
 */
@Named("homePageController")
@Scope("view")
public class HomePageController {

    private static final String ADVISOR_SELECTED = "advisor";
    private static final String EMAIL_ID = "emailId";
    private String email;
    private boolean advisor;
    private boolean userExists;
    private String dummy = "dummy";
    @Autowired
    private IUserProfileBAO userProfileBAO;

    private String loginType;
    private static final String USER_LOGIN_TYPE = "user_login_type";

    @PostConstruct
    public void afterInit() {
    }

    public void doActionRegisterNow(ActionEvent event) {
        if (!email.isEmpty()) {
            StringBuilder logMessage = new StringBuilder("New user registration started with email id ");
            logMessage.append(email);
            UserRegStatusVO userRegStatusVO = userProfileBAO.isUserRegEmailExists(email);
            userExists = userRegStatusVO.isEmailExist();
            if (userExists && userRegStatusVO.getRegStatus() == EnumStatus.NEW_APPLICANT.getValue()) {
                FacesContext.getCurrentInstance().addMessage("frm_home:email_in",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ACTIVATION_IN_PROGRESS, EMAIL_ID_ACTIVATION_IN_PROGRESS));
            } else {
                FacesContext.getCurrentInstance().addMessage("frm_home:email_in",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ALREADY_REGISTERED, EMAIL_ID_ALREADY_REGISTERED));
            }
            logMessage = new StringBuilder("Registration of new user failed - already registered email address : ");
            logMessage.append(email);
        }
    }

    public String registerNow() {
        String redirectTo;
        if (userExists) {
            redirectTo = EMPTY_STRING;
        } else {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            boolean advisorUser = Boolean.valueOf(getDummy());
            this.advisor = advisorUser;
            storedValues.put(EMAIL_ID, this.email);
            storedValues.put(IS_ADVISOR, this.advisor);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            redirectTo = "/pages/init_reg?faces-redirect=true";
        }
        return redirectTo;
    }

    public void changeUserType() {
        boolean advisorUser = Boolean.valueOf(getDummy());
        this.advisor = advisorUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDummy() {
        return dummy;
    }

    public void setDummy(String dummy) {
        this.dummy = dummy;
    }

    public String onUserLogin() {
        String text = EMPTY_STRING;
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String, String> params = context.getExternalContext().getRequestParameterMap();
        Map<String, Object> storedValues = new HashMap<String, Object>();
        loginType = params.get(USER_LOGIN_TYPE);
        storedValues.put(USER_LOGIN_TYPE, this.loginType);
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
        if (loginType != null && loginType.equals(ADVISOR)) {
            text = "ut=a";
        } else {
            text = "ut=i";
        }
        return "/pages/login.xhtml?"+text+"faces-redirect=true";
    }
}
