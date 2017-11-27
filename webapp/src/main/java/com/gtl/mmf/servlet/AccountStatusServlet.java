package com.gtl.mmf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.entity.TempRegistrationTb;
import com.gtl.mmf.service.util.BeanLoader;

@WebServlet("/faces/accountStatus")
public class AccountStatusServlet extends HttpServlet{

	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.AccountStatusServlet");
	private IUserProfileBAO userProfileBAO = null;
	private static final String USERNAME = "username";
	private static final String EMAIL_ID = "emailId";
	
	@Override
	public void init() throws ServletException {
		userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		LOGGER.log(Level.INFO,"Enter in AccountStatusServlet");
		String email = null;
		 String STORED_VALUES = "storedValues";
		try {
			HttpSession session = req.getSession();
			 Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
		     email = (String) storedValues.get(EMAIL_ID);
		   //    email = (String) session.getAttribute("username");
//			email =  (String) req.getSession().getAttribute(USERNAME);
			LOGGER.log(Level.INFO,"email as input  = " + email);
			String result = userProfileBAO.getInvAccountStatus(email);
			LOGGER.log(Level.INFO,"account status = " + result);
			if(result.startsWith("ERROR")) {
				TempRegistrationTb tempReg = userProfileBAO.getTempRegistrationTableData(email, false);
				result = getDefaultAccountStatus();
				JSONArray processStatus = new JSONArray(result);
				JSONObject process = null;
				String PROCESS_LABEL = "process_label";
			    String PROCESS_NAME = "process_name";
			    String PROCESS_STATUS = "process_status";
			    
			    String PROCESS_LABEL_EMAIL_VERIFIED  = "Email Verified.";
			    String PROCESS_NAME_EMAIL_VERIFIED  = "email_verified";
			    process = new JSONObject();
			    process.put(PROCESS_LABEL,PROCESS_LABEL_EMAIL_VERIFIED);
			    process.put(PROCESS_NAME, PROCESS_NAME_EMAIL_VERIFIED);
			    process.put(PROCESS_STATUS, tempReg.getMailVerified());
			    processStatus.put(0, process);
			    result = processStatus.toString();
			}
				
			
			LOGGER.log(Level.INFO,"account final status = " + result);
			PrintWriter out = resp.getWriter();
			out.write(result);
			out.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error in getting account Status", e);
		}
	
		
	
	}
	
	private String getDefaultAccountStatus(){
		return "[{\"process_name\":\"online_detailsubmites\",\"process_status\":true,\"process_label\":\"Online Details Submitted\"},{\"process_name\":\"form_couriered_Client\",\"process_status\":false,\"process_label\":\"Form couriered to Client\"},{\"process_name\":\"form_received_client\",\"process_status\":false,\"process_label\":\"Form received from client\"},{\"process_name\":\"form_Validated\",\"process_status\":false,\"process_label\":\"Form Validated\"},{\"process_name\":\"accepted\",\"process_status\":false,\"process_label\":\"Accepted\"},{\"process_name\":\"rejected\",\"process_status\":false,\"process_label\":\"Rejected\",\"rejection_Resolved\":false},{\"process_name\":\"account_activated\",\"process_status\":false,\"process_label\":\"Trading & Demat Activated\"},{\"process_name\":\"uCC_created\",\"process_status\":false,\"process_label\":\"UCC created\"}]";
	}
}
