/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserForgotPwdBAO;
import com.gtl.mmf.persist.vo.ForgotPwdVO;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.INVALID_USER_DETAILS;
import static com.gtl.mmf.service.util.IConstants.PWD_CHNG_SUCC;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author trainee3
 */
@Component("forgotPasswordController")
@Scope("view")
public class ForgotPasswordController {

    private static final String RESET_PASS_SUCCESS = "/pages/reset_pass_success.xhtml?faces-redirect=true";
    private static final String RESET_PASS_SUCCESS_ADMIN = "/pages/admin/index.xhtml?faces-redirect=true";
    private static final String FRM_RESET_BTN = "frm_reset:reset_btn";
    private String username;
    private String mobile;
    private String regid;
    private boolean advisor;
    private ForgotPwdVO fpvo;
    private String email;
    @Autowired
    private IUserForgotPwdBAO userForgotPwdBAO;
    private static final String USER_LOGIN_TYPE = "user_login_type";

    @PostConstruct
    public void init() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
        if (storedValues != null && storedValues.get(USER_LOGIN_TYPE) != null) {
            advisor = storedValues.get(USER_LOGIN_TYPE).equals(ADVISOR); 
        }
    }

    public void doActionFindPasswordListener(ActionEvent event) {
        fpvo = userForgotPwdBAO.changePasswordOnForgotPwd(username, regid, mobile, advisor);
    }

    public void doActionFindPasswordAdminListener(ActionEvent event) {
        fpvo = userForgotPwdBAO.changePasswordAdminOnForgotPwd(email, mobile, username);
    }

    public String forgotPwdRedirect() {
        String redirectTo = EMPTY_STRING;
        if (!fpvo.isPasswordChanged()) {
            FacesContext.getCurrentInstance().addMessage(FRM_RESET_BTN,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_USER_DETAILS, INVALID_USER_DETAILS));
        } else {
            redirectTo = RESET_PASS_SUCCESS;
        }
        return redirectTo;
    }

    public String forgotPwdRedirectAdmin() {
        String redirectTo = EMPTY_STRING;
        if (!fpvo.isPasswordChanged()) {
            FacesContext.getCurrentInstance().addMessage(FRM_RESET_BTN,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, INVALID_USER_DETAILS, INVALID_USER_DETAILS));
        } else {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = ec.getSessionMap();
            sessionMap.put(PWD_CHNG_SUCC, true);
            redirectTo = RESET_PASS_SUCCESS_ADMIN;
        }
        return redirectTo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRegid() {
        return regid;
    }

    public void setRegid(String regid) {
        this.regid = regid;
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
}
