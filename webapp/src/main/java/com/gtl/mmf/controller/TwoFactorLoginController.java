package com.gtl.mmf.controller;

import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.YES;

import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.iterators.EnumerationIterator;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gtl.mmf.bao.IUserLoginBAO;
import com.gtl.mmf.bao.impl.UserLoginBAOImpl;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.vo.LoginVO;
import com.gtl.mmf.util.StackTraceWriter;

/**
 * 
 * @author Shubhada Mulay Created on 24-Feb-2017 for new html web pages. 
 *
 */

@RestController
public class TwoFactorLoginController {
	private static final String ERROR_MSG_LOGIN = "Invalid login credentials.";
	private static final String USER_LOGIN_TYPE = "user_login_type";
	private static final String USER_EMAIL = "userEmail";
	private static final String USER_PASSWORD = "userPassword";
	private static String RESPONSE_STATUS = "status";
	private static final String RESPONSE_MSG = "errorMsg";
	private static final String URL = "url";
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.TwoFactorLoginController");
	private Date dob;
	private LoginVO userLogin;
	@Autowired
	private IUserLoginBAO userLoginBAO;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/twoFactorLogin", method = RequestMethod.POST)
	public String validateDateOfBirth(HttpServletRequest request, HttpSession httpSession) {
		// String result1 = null;
		String sDate = request.getParameter("calendar").toString();
		System.out.println(" dob set inside " + sDate); 							// to be commented 
		try {
			dob = new SimpleDateFormat("dd-MM-yyyy").parse(sDate);
			System.out.println(" dob set inside " + dob);							// to be commented 
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		dob = new Date(sDate);
		System.out.println(" dob set inside " + dob.toString());					// to be commented 
		JSONObject result = new JSONObject();
		UserSessionBean userSessionBean = new UserSessionBean();
		LOGGER.log(Level.INFO, "started com.gtl.mmf.controller.TwoFactorLoginController.validateDateOfBirth()");
		try {
			httpSession = request.getSession();
			// httpSession = (HttpSession)
			// .getCurrentInstance().getExternalContext().getSession(true);
			LoginVO login = new LoginVO();
			/*
			 * Map<String, Object> valuesMap = null; valuesMap = (Map<String,
			 * Object>) FacesContext.getCurrentInstance().getExternalContext().
			 * getSessionMap().get(STORED_VALUES);
			 */
			Map<String, Object> valuesMap = (Map<String, Object>) httpSession.getAttribute(STORED_VALUES);
			System.out.println(" dob set inside " + dob);							// to be commented 
			login.setDob(dob);
			login.setUsername(valuesMap.get("username").toString());
			login.setUsertype(valuesMap.get("userType").toString());
			login.setPassword(valuesMap.get("password").toString());
			//userLogin = userLoginBAO.userTwoFactorLogin(login);
			LoginControllerV2 loginControllerV2 = (LoginControllerV2) BeanLoader.getBean("loginControllerV2");
			loginControllerV2.setDob(dob);
			loginControllerV2.setUserLogin(login);
			loginControllerV2.doActionTwoFactorLoginListnerV3(request, httpSession);
			
			String redirectTo = loginControllerV2.loginRedirectV3(httpSession);
			// JSONObject result = new JSONObject(); // outside this block
			// String redirectTo = loginControllerV2.loginRedirectFirst();
			
		if (redirectTo.toLowerCase().contains("Error".toLowerCase())){
				LOGGER.log(Level.INFO, "Error occured in Redirect method : " + redirectTo);
				redirectTo = "";
				result.put(RESPONSE_STATUS, false);
				result.put(RESPONSE_MSG, ERROR_MSG_LOGIN);
				LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
		}
		else if(redirectTo.startsWith("/")){
				result.put(RESPONSE_STATUS, true);
				result.put(RESPONSE_MSG, "");
		}
		LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
//		else if(redirectTo.equalsIgnoreCase("Error")){
//				result.put(RESPONSE_STATUS, false);
//				result.put(RESPONSE_MSG, ERROR_MSG_LOGIN);
//		}
		result.put(URL, redirectTo);
		} catch (NullPointerException ex) {
			LOGGER.log(Level.ERROR, StackTraceWriter.getStackTrace(ex));
		} catch (Exception e) {
			LOGGER.log(Level.INFO, StackTraceWriter.getStackTrace(e));
			e.printStackTrace();
		}
		return result.toString();
	}
}