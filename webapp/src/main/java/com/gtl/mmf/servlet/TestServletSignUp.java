package com.gtl.mmf.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gtl.mmf.controller.HomePageController;
import com.gtl.mmf.controller.HomePageControllerV2;
import com.gtl.mmf.service.util.BeanLoader;

@WebServlet("/faces/testingSigup")
public class TestServletSignUp extends HttpServlet{
	
	
	private FacesContextFactory m_facesContextFactory;
    private Lifecycle m_lifecycle;
     
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.TestServletSignUp");
	HomePageControllerV2 homePageController;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		

        mainMethod(request,response);
        
	
	}
	private void mainMethod(HttpServletRequest request, HttpServletResponse response) {
		
		FacesContext context = null;
		 
		try {
			String first_name = request.getParameter("firstName");
			String middle_name = request.getParameter("middleName");
			String last_name = request.getParameter("lastName");
			String email = request.getParameter("email");
			String phone = request.getParameter("phone");
			String pass = request.getParameter("password");
			boolean advisor = Boolean.getBoolean(request.getParameter("advisor"));
			
			PrintWriter writer = response.getWriter();
	        writer.println("<html>"+ email +"<br>"
	        					   + phone +"<br>"
	        					   + pass + "<br>");
	        writer.flush();
	       
	       context = m_facesContextFactory.getFacesContext(getServletContext(),request,response,m_lifecycle); 
	       
	       homePageController = (HomePageControllerV2) BeanLoader.getBean("homePageControllerV2");
	       homePageController.setEmail(email);
	       homePageController.setFirst_name(first_name);
	       homePageController.setMiddle_name(middle_name);
	       homePageController.setLast_name(last_name);
	       homePageController.setMobile(phone);
	       homePageController.setAdvisor(advisor);
	       homePageController.setHttpRequest(request);
	       homePageController.doActionRegisterNow();
	       String output = homePageController.registerNow();
	       writer.println("<html>" + output +"</html>");
	    
	       
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE,"error",e);
		}
	
	}
	
	@Override
	public void init() throws ServletException {
		LifecycleFactory lifecycleFactory = (LifecycleFactory) FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		m_facesContextFactory = (FacesContextFactory) FactoryFinder.getFactory(FactoryFinder.FACES_CONTEXT_FACTORY);
		m_lifecycle = lifecycleFactory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
	}
	
}
