package com.gtl.mmf.servlet;

import static com.gtl.mmf.service.util.IConstants.ADVISOR;
import static com.gtl.mmf.service.util.IConstants.INVESTOR;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;

import javax.faces.application.Application;

import javax.faces.application.ViewHandler;

import javax.faces.component.UIViewRoot;

import javax.faces.context.FacesContext;

import javax.faces.context.FacesContextFactory;

import javax.faces.lifecycle.LifecycleFactory;

import javax.faces.lifecycle.Lifecycle;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.gtl.mmf.controller.LoginControllerV2;
import com.gtl.mmf.service.util.BeanLoader;

/**
 * @author sumeet.pol
 * new version 2 login page for new UI 
 */

@WebServlet("/faces/userLogin")
public class LoginServletV2 extends HttpServlet{

	private LoginControllerV2 loginController = null;
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.LoginServletV2");
	
    private static final String ERROR_MSG_LOGIN = "Invalid login credentials.";
    private static final String USER_LOGIN_TYPE = "user_login_type";
    private static final String USER_EMAIL = "userEmail";
    private static final String USER_PASSWORD = "userPassword";
    private static  String RESPONSE_STATUS = "status";
	private static final String RESPONSE_MSG = "errorMsg";
	private static final String URL = "url";
    
    @Override
	public void init() throws ServletException {
		
		//loginController = (LoginControllerV2) BeanLoader.getBean("loginControllerV2");
	
	}
    
    @Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	//	if(loginController == null){
			loginController = (LoginControllerV2) BeanLoader.getBean("loginControllerV2");
	//	}
	//	else{
	//		LOGGER.log(Level.INFO,"LoginController Bean already created.");
	//	}
		
			String userType =  (req.getParameter(USER_LOGIN_TYPE)== null ) ? null : req.getParameter(USER_LOGIN_TYPE);
			String username =  (req.getParameter(USER_EMAIL)== null ) ? null : req.getParameter(USER_EMAIL);
	    	String password =  (req.getParameter(USER_PASSWORD)== null ) ? null : req.getParameter(USER_PASSWORD);
	    	
		if(userType == null){
			userType = loginController.getUserType(username, password);
		}
		if (userType != null && userType.equals(ADVISOR)) {
			
			doInvestorLogin(req,resp);
			
		} else if (userType != null && userType.equals(INVESTOR)) {
			doInvestorLogin(req,resp);
	    }
		
	}
	
    private void doInvestorLogin(HttpServletRequest req, HttpServletResponse resp) {
		try {
		
			String userType =  (req.getParameter(USER_LOGIN_TYPE)== null ) ? null : req.getParameter(USER_LOGIN_TYPE);
	    	String username =  (req.getParameter(USER_EMAIL)== null ) ? null : req.getParameter(USER_EMAIL);
	    	String password =  (req.getParameter(USER_PASSWORD)== null ) ? null : req.getParameter(USER_PASSWORD);
	    	loginController.setHttpServletRequestV1(req);
	    	loginController.setUsername(username);
	    	loginController.setPassword(password);
	    	String status = loginController.doActionLoginListner();
	    	PrintWriter out = resp.getWriter();
	    	JSONObject result = new JSONObject();
	    	if(status.startsWith("ERROR")){
	    		String[] statusMsg = status.split("ERROR:");
	    		result.put(RESPONSE_STATUS, false);
	    		result.put(RESPONSE_MSG, statusMsg[1]);
	    		LOGGER.log(Level.INFO, "login failed : "+ statusMsg[1]);
//	    		out.write(result.toString());
	    	}
	    	else{
	    		Map<String, Object> valuesMap = (Map<String, Object>) req.getSession().getAttribute(STORED_VALUES);
	    		String redirectTo = loginController.loginRedirectFirst();
		    	LOGGER.log(Level.INFO, "redirectTo : "+ redirectTo);
	    		result.put(RESPONSE_STATUS, true);
	    		result.put(RESPONSE_MSG, ERROR_MSG_LOGIN);
	    		result.put(URL,redirectTo);
//	    		out.write(result.toString());
	            FacesContext facesContext = FacesContext.getCurrentInstance();
	            
	            // Check current FacesContext.
	            if (facesContext == null) {
	   
	                // Create new Lifecycle.
	                LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY); 
	                Lifecycle lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
	   
	                // Create new FacesContext.
	                FacesContextFactory contextFactory  = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
	                facesContext = contextFactory.getFacesContext(req.getSession().getServletContext(), req, resp, lifecycle);
	   
	                // Create new View.
	                UIViewRoot view = facesContext.getApplication().getViewHandler().createView(facesContext, redirectTo);
	                facesContext.setViewRoot(view);
	            }
	    	}
	    	
	    	if(req.getParameter("callback")!=null)
	    	{
	    		out.write(req.getParameter("callback") + "(" + result.toString() + ");");
	    	}
	    	else{

		    	out.write(result.toString());
		    		
	    	}//resp.sendRedirect(redirectTo);
	    	out.close();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "error", e);
		}
    	
    	
	}
	
    private void doAdvisorLogin(HttpServletRequest req, HttpServletResponse resp) {
		// TODO Auto-generated method stub
		
	}
	
}
