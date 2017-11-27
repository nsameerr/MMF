/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.CREATE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.FOUR_HOUNDRED;
import static com.gtl.mmf.service.util.IConstants.FROM_PAYMENT_RESPONSE;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.IPS_SHARED;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_LINK_CREATE;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_LINK_C_OR_E;
import static com.gtl.mmf.service.util.IConstants.PORTFOLIO_LINK_EDIT;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.USER_SESSION;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author 07958
 */
@Component("sidebarUserController")
@Scope("view")
public class SidebarUserController {

    private String userType;
    private Integer userStatus;
    private Map<String, String> requestParameterMap;
    private String redirectTo;
    UserSessionBean userSessionBean;

    @PostConstruct
    public void afterInit() {
        userSessionBean = (UserSessionBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(USER_SESSION);
        if (userSessionBean != null) {
            userType = userSessionBean.getUserType();
            userStatus = userSessionBean.getRelationStatus();
        }

        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        requestParameterMap = ec.getRequestParameterMap();
    }

    public String onClickHome() {
        if (userType.equals(INVESTOR)) {
            redirectTo = "/pages/investordashboard.xhtml?faces-redirect=true";
            if (userSessionBean != null) {
                userSessionBean.setFromURL(redirectTo);
            }
        } else if (userType.equals(ADVISOR)) {
            redirectTo = "/pages/advisordashboard.xhtml?faces-redirect=true";
        }
        return redirectTo;
    }

    public String onClickNetwork() {
        if (userType.equals(INVESTOR)) {
            redirectTo = "/pages/investor_network_view.xhtml?faces-redirect=true";
        } else if (userType.equals(ADVISOR)) {
            redirectTo = "/pages/advisor_network_view.xhtml?faces-redirect=true";
        }
        return redirectTo;
    }

    public void onClickCreatePortfolio() {
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(CREATE, true);
        storedValues.put(PORTFOLIO_LINK_C_OR_E, PORTFOLIO_LINK_CREATE);
        getSessionMap().put(STORED_VALUES, storedValues);
        redirectTo = "/pages/create_edit_portfolio?faces-redirect=true";
    }

    private Map<String, Object> getSessionMap() {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    }

    public void onClickEditPortfolio() {
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(CREATE, false);
        storedValues.put(PORTFOLIO_LINK_C_OR_E, PORTFOLIO_LINK_EDIT);
        getSessionMap().put(STORED_VALUES, storedValues);
        redirectTo = "/pages/create_edit_portfolio?faces-redirect=true";
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String redirectURL() {
        return redirectTo;
    }

    public String onClickPayment() {
        Map<String, Object> storedValues = new HashMap<String, Object>();
        storedValues.put(FROM_PAYMENT_RESPONSE, false);
        getSessionMap().put(STORED_VALUES, storedValues);
        redirectTo = "/pages/make_payment?faces-redirect=true";
        return redirectTo;
    }

    public String onRedirectPortfolio() {
        if (userStatus.intValue() == IPS_SHARED.intValue() || userStatus >= FOUR_HOUNDRED.intValue()) {
            redirectTo = "/pages/portfolio_details?faces-redirect=true";
            return redirectTo;
        }
        return EMPTY_STRING;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public UserSessionBean getUserSessionBean() {
        return userSessionBean;
    }

    public void setUserSessionBean(UserSessionBean userSessionBean) {
        this.userSessionBean = userSessionBean;
    }

}
