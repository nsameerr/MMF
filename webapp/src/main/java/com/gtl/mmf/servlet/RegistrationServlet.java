package com.gtl.mmf.servlet;

import static com.gtl.mmf.service.util.IConstants.EXPIRE_IN;
import static com.gtl.mmf.service.util.IConstants.IS_ADVISOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.controller.HomePageControllerV2;
import com.gtl.mmf.controller.InitUserRegistrationBeanV2;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.IdGenarator;
import com.gtl.mmf.service.vo.AdvisorRegistrationVo;
import com.gtl.mmf.service.vo.CompleteTempInvVo;
import com.gtl.mmf.service.vo.InvestorTempRegistrationVo;
import com.gtl.mmf.service.vo.RegistrationVo;
import com.gtl.mmf.service.vo.TempAdvVo;

@WebServlet("/faces/register")
public class RegistrationServlet extends HttpServlet {

	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.RegistrationServlet");
	private static final String EMAIL_ID = "emailId";
	
	private static String RESPONSE_STATUS = "status";
	private static final String RESPONSE_MSG = "errorMsg";
	private static final String URL = "url";
	private static final String USER_FIRSTNAME = "userFirstName";
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		LOGGER.log(Level.INFO, "entered doPost");
		try {
			String type = request.getParameter("advisor");
			LOGGER.log(Level.INFO, "is advisor : "+ request.getParameter("advisor"));
			boolean advisor = Boolean.valueOf(type);
			if (advisor) {
				// user is advisor
				doRegisterAdvisor(request, response);
			} else {
				doRegisterInvestor(request, response);
			}
			LOGGER.log(Level.INFO, "Error : User type is null");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in do post", e);
		}
	}

	private void doRegisterInvestor(HttpServletRequest request, HttpServletResponse response) {
		JSONObject jsonresult = new JSONObject();
		InitUserRegistrationBeanV2 initUserReg = null;
		HomePageControllerV2 homePageController = null;
		String first_name = request.getParameter("firstName");
		String middle_name = request.getParameter("middleName");
		String last_name = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String pass = request.getParameter("password");
		boolean advisor = Boolean.valueOf(request.getParameter("advisor"));
		homePageController = (HomePageControllerV2) BeanLoader.getBean("homePageControllerV2");

		homePageController.setEmail(email);
		homePageController.setFirst_name(first_name);
		homePageController.setMiddle_name(middle_name);
		homePageController.setLast_name(last_name);
		homePageController.setMobile(phone);
		homePageController.setAdvisor(advisor);
		homePageController.setHttpRequest(request);
		JSONObject result = homePageController.doActionRegisterNow();
		try {

			PrintWriter out = response.getWriter();

			if (result == null) {
				// TODO if some exception occured
			} else if (result.getBoolean("status")) {

				initUserReg = (InitUserRegistrationBeanV2) BeanLoader.getBean("initUserRegistrationBeanV2");

				initUserReg.setEmail(email);
				initUserReg.setAdvisor(advisor);
				initUserReg.setNewPassword(pass);
				initUserReg.setConfirmPassword(pass);
				initUserReg.setAgreeTermsCondition(true);
				initUserReg.doActionSignUp();
				String redirectTo = initUserReg.signUp();
				
				if (redirectTo == null) {

					LOGGER.log(Level.INFO, "redirectTo is null");
					jsonresult.put(RESPONSE_STATUS, false);
					jsonresult.put(RESPONSE_MSG, "Registration failed.");
					out.write(jsonresult.toString());
				} else {
					
					redirectTo = request.getContextPath() + redirectTo; 
                    /*********** generate a record in temp inv  ****/
                    InvestorTempRegistrationVo tempRegistrationVo = new InvestorTempRegistrationVo();
                    CompleteTempInvVo completeTempInvVo = new CompleteTempInvVo();
                    IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
                    RegistrationVo regVo = userProfileBAO.getTempInvData(email);
                    String regId = "";
                    boolean firsTimeAutoSave = false;
                    if(regVo.getRegId() == null){
                    	regId = IdGenarator.getUniqueId();
                    	firsTimeAutoSave = true;
                    }
                    else{
                    	regId = regVo.getRegId();
                    }
                    completeTempInvVo.setRegId(regId);
                    completeTempInvVo.setFirstname(first_name);
                    completeTempInvVo.setMiddlename(middle_name);
                    completeTempInvVo.setLastname(last_name);
                    completeTempInvVo.setEmail(email);
                    completeTempInvVo.setMobile(phone);
                    tempRegistrationVo.setCompleteRegPage(completeTempInvVo);
                    userProfileBAO.saveCompleteInvTemp(tempRegistrationVo);
                    LOGGER.log(Level.INFO, "User Record created in db" );
                    
					LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
					Map<String, Object> storedValues = new HashMap<String, Object>();
					storedValues.put(IS_ADVISOR, advisor);
					storedValues.put(EMAIL_ID, email);
					storedValues.put(EXPIRE_IN, false);
					storedValues.put(USER_FIRSTNAME,first_name);
					request.getSession().setAttribute(STORED_VALUES, storedValues);

					LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
					jsonresult.put(RESPONSE_STATUS, true);
					jsonresult.put(RESPONSE_MSG, "");
					jsonresult.put(URL, redirectTo);
					jsonresult.put("regId", regId);
					out.write(jsonresult.toString());

				}
			} else {

				// redirect with error msg
				jsonresult.put(RESPONSE_STATUS, false);
				jsonresult.put(RESPONSE_MSG, "Registration failed.");
				out.write(jsonresult.toString());
			}
			out.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error in doRegisterInvestor", e);
		}

	}

	private void doRegisterAdvisor(HttpServletRequest request, HttpServletResponse response) {
		// TODO do advisor registration
		JSONObject jsonresult = new JSONObject();

		 InitUserRegistrationBeanV2 initUserReg = null;
		 HomePageControllerV2 homePageController = null;
		String first_name = request.getParameter("firstName");
		String middle_name = request.getParameter("middleName");
		String last_name = request.getParameter("lastName");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String pass = request.getParameter("password");
		boolean advisor = Boolean.valueOf(request.getParameter("advisor"));
		homePageController = (HomePageControllerV2) BeanLoader.getBean("homePageControllerV2");

		homePageController.setEmail(email);
		homePageController.setFirst_name(first_name);
		homePageController.setMiddle_name(middle_name);
		homePageController.setLast_name(last_name);
		homePageController.setMobile(phone);
		homePageController.setAdvisor(advisor);
		homePageController.setHttpRequest(request);
		JSONObject result = homePageController.doActionRegisterNow();
		try {
			PrintWriter out = response.getWriter();
			if( result == null ) {
				
			} else if (result.getBoolean("status")) {
				initUserReg = (InitUserRegistrationBeanV2) BeanLoader.getBean("initUserRegistrationBeanV2");

				initUserReg.setEmail(email);
				initUserReg.setAdvisor(advisor);
				initUserReg.setNewPassword(pass);
				initUserReg.setConfirmPassword(pass);
				initUserReg.setAgreeTermsCondition(true);
				initUserReg.doActionSignUp();
				String redirectTo = initUserReg.signUp();
				if (redirectTo == null) {

					LOGGER.log(Level.INFO, "redirectTo is null");
					jsonresult.put(RESPONSE_STATUS, false);
					jsonresult.put(RESPONSE_MSG, "Registration failed.");
					out.write(jsonresult.toString());
				} else {
					
					redirectTo = request.getContextPath() + redirectTo;
					/******* create a record in tempAdv ***/
					IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
					TempAdvVo advisorRegistrationVo = new TempAdvVo();
					AdvisorRegistrationVo regVo = userProfileBAO.getTempAdvData(email);
					String regId = "";
                    if(regVo.getRegNO() == null){
                    	regId = IdGenarator.getUniqueId();
                    }
                    else{
                    	regId = regVo.getRegNO();
                    }
                    advisorRegistrationVo.setRegNO(regId);
                    advisorRegistrationVo.setRemail(email);
					advisorRegistrationVo.setFname(first_name);
					advisorRegistrationVo.setMiddle_name(middle_name);
					advisorRegistrationVo.setLast_name(last_name);
					advisorRegistrationVo.setOmobile(phone);
					advisorRegistrationVo.setOemail(email);
					advisorRegistrationVo.setBcountry("IN");
					advisorRegistrationVo.setRcountry("IN");
					advisorRegistrationVo.setOcountry("IN");
					advisorRegistrationVo.setIndvOrCprt(true);
					userProfileBAO.saveAdvTemp(advisorRegistrationVo);
					
					
					LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
					Map<String, Object> storedValues = new HashMap<String, Object>();
					storedValues.put(IS_ADVISOR, advisor);
					storedValues.put(EMAIL_ID, email);
					storedValues.put(EXPIRE_IN, false);
					storedValues.put(USER_FIRSTNAME,first_name);
					request.getSession().setAttribute(STORED_VALUES, storedValues);
					
					LOGGER.log(Level.INFO, "redirectTo : " + redirectTo);
					jsonresult.put(RESPONSE_STATUS, true);
					jsonresult.put(RESPONSE_MSG, "");
					jsonresult.put(URL, redirectTo);
					jsonresult.put("regId", regId);
					out.write(jsonresult.toString());

				}
			} else {
				// redirect with error msg
				jsonresult.put(RESPONSE_STATUS, false);
				jsonresult.put(RESPONSE_MSG, "Registration failed.");
				out.write(jsonresult.toString());
			}
			out.close();
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error in doRegisterInvestor", e);
		}
	}

}
