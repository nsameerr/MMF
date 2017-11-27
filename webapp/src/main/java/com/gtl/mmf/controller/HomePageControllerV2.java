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

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author 07960
 */
@Named("homePageControllerV2")
//@Scope("application")
@Scope("prototype")
public class HomePageControllerV2 implements Serializable{

    private static final String ADVISOR_SELECTED = "advisor";
    private static final String EMAIL_ID = "emailId";
    private static final String FIRST_NAME = "firstName";
    private static final String MIDDLE_NAME = "middleName";
    private static final String LAST_NAME = "lastName";
    private static final String PHONE = "phone";
    private String first_name;
    private String middle_name;
    private String last_name;
    private String mobile;
    private String email;
    private boolean advisor;
    private boolean userExists;
    private String dummy = "dummy";
    @Autowired
    private IUserProfileBAO userProfileBAO;

    private String loginType;
    private static final String USER_LOGIN_TYPE = "user_login_type";

    @Context
    private HttpServletRequest httpRequest;
    @Context
    private HttpServletResponse httpresponse;
    
    @PostConstruct
    public void afterInit() {
    }

    public JSONObject doActionRegisterNow() {
    	JSONObject result = new JSONObject();
    	try {
    	
        	result.put("status", true);
            if (!email.isEmpty()) {
                StringBuilder logMessage = new StringBuilder("New user registration started with email id ");
                logMessage.append(email);
                UserRegStatusVO userRegStatusVO = userProfileBAO.isUserRegEmailExists(email);
                userExists = userRegStatusVO.isEmailExist();
                if ((userExists == false) && (userRegStatusVO.getRegStatus()== null)){
                	result.put("status", true);
                }
                else if (userExists && userRegStatusVO.getRegStatus() == EnumStatus.NEW_APPLICANT.getValue()) {
//                    FacesContext.getCurrentInstance().addMessage("frm_home:email_in",
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ACTIVATION_IN_PROGRESS, EMAIL_ID_ACTIVATION_IN_PROGRESS));
                	result.put("status", false);
                	result.put("errorMsg", EMAIL_ID_ACTIVATION_IN_PROGRESS);
                } else {
//                    FacesContext.getCurrentInstance().addMessage("frm_home:email_in",
//                            new FacesMessage(FacesMessage.SEVERITY_ERROR, EMAIL_ID_ALREADY_REGISTERED, EMAIL_ID_ALREADY_REGISTERED));
                	result.put("status", false);
                	result.put("errorMsg", EMAIL_ID_ALREADY_REGISTERED);
                }
                logMessage = new StringBuilder("Registration of new user failed - already registered email address : ");
                logMessage.append(email);
                return result;
            } else{
            	result.put("status", false);
            	result.put("errorMsg", "Invalid Email Adress.");
            }
		} catch (Exception e) {
			return null;
		}
    	
        return result;
    }

    public String registerNow() {
        String redirectTo;
        if (userExists) {
            redirectTo = EMPTY_STRING;
        } else {
            Map<String, Object> storedValues = new HashMap<String, Object>();
            boolean advisorUser = Boolean.valueOf(getDummy());
            this.setAdvisor(advisorUser);
            storedValues.put(EMAIL_ID, this.email);
            storedValues.put(IS_ADVISOR, this.isAdvisor());
            storedValues.put(FIRST_NAME, this.first_name);
            storedValues.put(MIDDLE_NAME,this.middle_name);
            storedValues.put(LAST_NAME,this.last_name);
            storedValues.put(PHONE,this.mobile);
            try{
//            	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
            	httpRequest.getSession().setAttribute(STORED_VALUES, storedValues);
            	redirectTo = "/pages/init_reg?faces-redirect=true";
            	
            }catch(Exception e){
            	redirectTo = "errir in facescontext";
            }
            
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

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean isAdvisor() {
		return advisor;
	}

	public void setAdvisor(boolean advisor) {
		this.advisor = advisor;
	}

	public HttpServletRequest getHttpRequest() {
		return httpRequest;
	}

	public void setHttpRequest(HttpServletRequest httpRequest) {
		this.httpRequest = httpRequest;
	}
}
