/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserLoginBAO;
import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.CHANGE_PASSWORD;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.NO;
import static com.gtl.mmf.service.util.IConstants.PWD_CHNG_SUCC;
import static com.gtl.mmf.service.util.IConstants.RESET_PWD;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import static com.gtl.mmf.service.util.IConstants.YES;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07960
 */
@Named("changePasswordController")
@Scope("view")
public class ChangePasswordController {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.changePasswordController");
    private String username;
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private String userType;
    private boolean status;
    @Autowired
    private IUserLoginBAO userLoginBAO;

    @PostConstruct
    public void afterInit() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        UserSessionBean userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        if (session != null && session.getAttribute(RESET_PWD) != null && (Boolean) session.getAttribute(RESET_PWD)) {
            Map<String, Object> storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(STORED_VALUES);
            this.userType = storedValues.get("userType").toString();
            this.username = storedValues.get("username").toString();
        } else if(userSessionBean !=null){
            userSessionBean.setFromURL(CHANGE_PASSWORD);
            this.userType = userSessionBean.getUserType().equalsIgnoreCase(ADVISOR) ? YES : NO;
            this.username = userSessionBean.getUsername();
            setUsername(username);
        } else{
            try { 
                FacesContext.getCurrentInstance().getExternalContext().redirect(LookupDataLoader.getContextRoot() + "/index.xhtml");
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            }
        }
    }

    public void doActionChangePassword(ActionEvent event) {
        if (newPassword.equalsIgnoreCase(confirmPassword)) {
            status = userLoginBAO.changePasswordOnLogin(username, oldPassword, newPassword, userType);
            if (!status) {
                FacesContext.getCurrentInstance().addMessage("frm_changePass:btn_change",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid user details",
                                "Invalid user details"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage("frm_changePass:repassword",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "New Password and re-entered password do not match",
                            "New Password and re-entered password do not match"));

        }
    }

    public void doActionReset(ActionEvent event) {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        Map<String, Object> requestMap = ec.getRequestMap();
        this.username = EMPTY_STRING;
        this.oldPassword = EMPTY_STRING;
        this.newPassword = EMPTY_STRING;
        this.confirmPassword = EMPTY_STRING;
        requestMap.put("userType", this.userType);
    }

    public String reset() {
        return "/pages/change_password";
    }

    public String changePasswordRedirect() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        String redirectPage = EMPTY_STRING;
        if (status) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            Map<String, Object> sessionMap = ec.getSessionMap();
            sessionMap.put(PWD_CHNG_SUCC, true);
            session.setAttribute(RESET_PWD, false);
            redirectPage = userType.equalsIgnoreCase(ADVISOR)?"/pages/change_pass_success.xhtml":"/pages/change_pass_success_advisor.xhtml";
            ec.invalidateSession();
            return redirectPage;
        }
        return EMPTY_STRING;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
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

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public IUserLoginBAO getUserLoginBAO() {
        return userLoginBAO;
    }

    public void setUserLoginBAO(IUserLoginBAO userLoginBAO) {
        this.userLoginBAO = userLoginBAO;
    }

}
