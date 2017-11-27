/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07960
 */
@Component("userSessionBean")
@Scope("session")
public class UserSessionBean implements Serializable, HttpSessionBindingListener {

    private Integer userId;
    private String username;
    private String userType;
    private String email;
    private Short status;
    private String regId;
    private String toURL;
    private String fromURL;
    private Boolean investorWithAdvisor;
    private String accessToken;
    private String linkedInId;
    private String firstName;
    private String middleName;
    private String lastName;
    private Integer relationStatus;
    private Integer relationId;
    private boolean linkedInConnected;
    private Integer initLogin;
    private String omsLoginId;

    public static HashMap<HttpSession, UserSessionBean> logins = new HashMap<HttpSession, UserSessionBean>();
    public static HashMap<UserSessionBean, HttpSession> browserLogins = new HashMap<UserSessionBean, HttpSession>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getInvestorWithAdvisor() {
        return investorWithAdvisor;
    }

    public void setInvestorWithAdvisor(Boolean investorWithAdvisor) {
        this.investorWithAdvisor = investorWithAdvisor;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegId() {
        return regId;
    }

    public void setRegId(String regId) {
        this.regId = regId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getToURL() {
        return toURL;
    }

    public void setToURL(String toURL) {
        this.toURL = toURL;
    }

    public String getFromURL() {
        return fromURL;
    }

    public void setFromURL(String fromURL) {
        this.fromURL = fromURL;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getLinkedInId() {
        return linkedInId;
    }

    public void setLinkedInId(String linkedInId) {
        this.linkedInId = linkedInId;
    }

    public Integer getRelationStatus() {
        return relationStatus;
    }

    public void setRelationStatus(Integer relationStatus) {
        this.relationStatus = relationStatus;
    }

    public Integer getRelationId() {
        return relationId;
    }

    public void setRelationId(Integer relationId) {
        this.relationId = relationId;
    }

    public boolean isLinkedInConnected() {
        return linkedInConnected;
    }

    public void setLinkedInConnected(boolean linkedInConnected) {
        this.linkedInConnected = linkedInConnected;
    }

    public Integer getInitLogin() {
        return initLogin;
    }

    public void setInitLogin(Integer initLogin) {
        this.initLogin = initLogin;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof UserSessionBean) && (regId != null) ? regId.equals(((UserSessionBean) obj).regId) : (obj == this);
    }

    @Override
    public int hashCode() {
        return (regId != null) ? (this.getClass().hashCode() + regId.hashCode()) : super.hashCode();
    }

    public void valueBound(HttpSessionBindingEvent hsbe) {
        if (!logins.isEmpty() && logins.containsValue(this) 
                && !logins.containsKey(hsbe.getSession())) {
            for (Object session : logins.keySet()) {
                if (logins.get(session).equals(this)) {
                    browserLogins.put(this, (HttpSession) session);
                    HttpSession sesnId = browserLogins.remove(this);
                    if (sesnId != null) {
                        sesnId.invalidate();
                    }
                    logins.remove(session);
                }
            }
        }
        logins.put(hsbe.getSession(), this);
    }

    public void valueUnbound(HttpSessionBindingEvent hsbe) {
        logins.remove(hsbe.getSession());
    }

    public static HashMap<HttpSession, UserSessionBean> getLogins() {
        return logins;
    }

    public static void setLogins(HashMap<HttpSession, UserSessionBean> logins) {
        UserSessionBean.logins = logins;
    }

    public static HashMap<UserSessionBean, HttpSession> getBrowserLogins() {
        return browserLogins;
    }

    public static void setBrowserLogins(HashMap<UserSessionBean, HttpSession> browserLogins) {
        UserSessionBean.browserLogins = browserLogins;
    }

    public String getOmsLoginId() {
        return omsLoginId;
    }

    public void setOmsLoginId(String omsLoginId) {
        this.omsLoginId = omsLoginId;
    }
    
    

}
