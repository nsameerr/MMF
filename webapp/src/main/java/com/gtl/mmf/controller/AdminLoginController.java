/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUserLoginBAO;
import static com.gtl.mmf.service.util.IConstants.ADMIN_USER;
import static com.gtl.mmf.service.util.IConstants.COOKIE_USERNAME;
import static com.gtl.mmf.service.util.IConstants.COOKIE_VIRTUALCHECK;
import static com.gtl.mmf.service.util.IConstants.EXPIRY;
import static com.gtl.mmf.service.util.IConstants.FAILURE;
import static com.gtl.mmf.service.util.IConstants.FALSE;
import static com.gtl.mmf.service.util.IConstants.PASWOED_RESET_SUCCESS;
import static com.gtl.mmf.service.util.IConstants.PWD_CHNG_SUCC;
import static com.gtl.mmf.service.util.IConstants.TRUE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.LoginVO;
import java.util.Map;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *This class is used for admin login
 * @author 07960
 */
@Component("adminLoginController")
@Scope("view")
public class AdminLoginController{

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.AdminLoginController");

    private String username;
    private String password;
    private String usertype;
    private boolean status;
    private boolean rememberMe;
    private String virtualCheck = "";
    @Autowired
    private IUserLoginBAO userLoginBAO;
    private LoginVO userLogin;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public String getVirtualCheck() {
        return virtualCheck;
    }

    public void setVirtualCheck(String virtualCheck) {
        this.virtualCheck = virtualCheck;
    }

    @PostConstruct
    public void afterInit() {
        isRemembermeChecked();
        Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();

        if (requestMap.get("loginFailed") != null) {
            Boolean loginFailed = (Boolean) requestMap.get("loginFailed");
            if (loginFailed.booleanValue()) {
                FacesContext.getCurrentInstance().addMessage("admin_login_id:password",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credentials", "Invalid login credentials"));
            }
        }

        Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
        if (sessionMap.get(PWD_CHNG_SUCC) != null) {
            FacesContext.getCurrentInstance().addMessage("admin_login_id",
                    new FacesMessage(FacesMessage.SEVERITY_INFO, PASWOED_RESET_SUCCESS,
                            PASWOED_RESET_SUCCESS));
            sessionMap.remove(PWD_CHNG_SUCC);
        }
    }

    public void doActionAdminLoginListner(ActionEvent event) {
        LoginVO login = new LoginVO();
        login.setUsername(username);
        login.setPassword(password);
        login.setUsertype(ADMIN_USER);

        userLogin = userLoginBAO.userLogin(login);

        if (!userLogin.getRedirectPage().equalsIgnoreCase(FAILURE)) {
            UserSessionBean userSessionBean = new UserSessionBean();
            userSessionBean.setUserType(ADMIN_USER);
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            session.setAttribute("userSession", userSessionBean);

            //Rememebr
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
        }
    }

    public String adminLoginRedirect() {
        String destination = userLogin.getRedirectPage();
        if (destination.equals(FAILURE)) {
            Map<String, Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
            requestMap.put("loginFailed", true);
            destination = "/pages/admin/index";
        } else {
            destination = destination + "?faces-redirect=true";
        }
        return destination;
    }

    private boolean isRemembermeChecked() {
        boolean status = false;
        FacesContext faceContext = FacesContext.getCurrentInstance();
        Cookie cookieAttr[] = ((HttpServletRequest) (faceContext.
                getExternalContext().getRequest())).getCookies();
        if (cookieAttr != null && cookieAttr.length > 0) {
            for (int i = 0; i < cookieAttr.length; i++) {
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
}
