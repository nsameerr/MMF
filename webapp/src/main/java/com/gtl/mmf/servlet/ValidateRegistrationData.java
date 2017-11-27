package com.gtl.mmf.servlet;

import static com.gtl.mmf.service.util.IConstants.EMAIL_ID_ACTIVATION_IN_PROGRESS;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.service.util.BeanLoader;


/***
 * @author sumeet.pol
 * methods check is field is already registred.
 * */

@WebServlet("/faces/validateRegInfo")
public class ValidateRegistrationData extends HttpServlet {

	private static final String EMAIL_ID_AVALIABLE = "Email id not registered,Avaliable for use.";
	private static final String EMAIL_ID_NOT_AVALIABLE = "Email id already registered";
	private static final String PAN_AVALIABLE = "PAN Avaliable.";
	private static  String RESPONSE_STATUS = "status";
	private static final String RESPONSE_MSG = "errorMsg";
	private static final String RESPONSE_REDIRCT_TO = "redirectTo";
	private static  String EMAIL_ID = "emailId";
	private static  String PAN = "panNumber";
	private static final String PANNO_MESSAGE = "PAN number already registered";
	private boolean userExists;
	private boolean userPanExists;
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.ValidateRegistrationData");
	private IUserProfileBAO userProfileBAO = null;
	@Override
	public void init() throws ServletException {
		IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {

		try
		{
			//check is bean already created or not
			PrintWriter out = resp.getWriter();
			if(userProfileBAO == null) {
				userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
			} else {
				LOGGER.log(Level.INFO,"userProfileBAO Bean already created.");
			}
			//get the field type to validate
			String type  =  request.getParameter("type");
			if(type.equalsIgnoreCase(EMAIL_ID)){
				checkIfEmailAlreadyRegistred(request,resp);
			}
			else if (type.equalsIgnoreCase(PAN)){
				checkIfPanAlreadyRegistred(request,resp);
			}
			else {
				out.write("fieldType not supported");
			}
			
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error in do post", e);
		}
	}
	
	/**
	 * @author sumeet.pol
	 * this method check the pan in masterApplicantTb
	 * return true if pan is already used by other user
	 * return false if pan avaliable for current user
	 */
	
	private void checkIfPanAlreadyRegistred(HttpServletRequest request, HttpServletResponse resp) throws JSONException, IOException {
		JSONObject result = new JSONObject();
		String pan = request.getParameter(PAN);
		PrintWriter out = resp.getWriter();
//		HttpSession session = req.getSession();
//        Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
//        String email = (String) storedValues.get(EMAIL_ID);
//		RegistrationVo regVo =userProfileBAO.getTempInvData(email);
//		userPanExists = userProfileBAO.isPanNoExists("", pan, regVo.getRegId());
		userPanExists = userProfileBAO.isPanNoExists(pan);
		result.put(RESPONSE_STATUS, userPanExists);
		if(userPanExists){
			//pan already registered
			result.put(RESPONSE_MSG, PANNO_MESSAGE);
		}
		else{
			//pan not registred
			result.put(RESPONSE_MSG, PAN_AVALIABLE);
		}
		out.write(result.toString());
	}

	/**
	 * @author sumeet.pol
	 * this method check the email in masterApplicantTb
	 * return true if email is already used by other user
	 * return false if email avaliable for current user
	 */
	private void checkIfEmailAlreadyRegistred(HttpServletRequest request, HttpServletResponse resp) throws JSONException, IOException {
		String email = request.getParameter(EMAIL_ID);
		JSONObject result = new JSONObject();
		PrintWriter out = resp.getWriter();
		boolean isAlreadyUsed = userProfileBAO.isEmailAlreadyRegistered(email);
		if(isAlreadyUsed){
			//already used true
			result.put(RESPONSE_STATUS, isAlreadyUsed);
			result.put(RESPONSE_MSG, EMAIL_ID_NOT_AVALIABLE);
			LOGGER.log(Level.INFO,email + " is already registered");
		}
		else{
			//no entry in regsitration check in master table
			LOGGER.log(Level.INFO,email + " entry not present in register data looking in master data");
			userExists = userProfileBAO.isEmailExists(email);
	        result.put(RESPONSE_STATUS, userExists);
	        if(userExists){
	        	//email id already exists
	        	result.put(RESPONSE_MSG, EMAIL_ID_ACTIVATION_IN_PROGRESS);
	        	LOGGER.log(Level.INFO,email +" "+ EMAIL_ID_ACTIVATION_IN_PROGRESS);
	        }
	        else{
	        	//email id not registred
	        	result.put(RESPONSE_MSG, EMAIL_ID_AVALIABLE);
	        	LOGGER.log(Level.INFO,email +" "+ EMAIL_ID_AVALIABLE);
	        }
		}
		
        out.write(result.toString());
	}

	
}