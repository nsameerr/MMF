package com.gtl.mmf.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FactoryFinder;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.FacesContextFactory;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.apache.bcel.generic.INVOKEINTERFACE;

import com.gtl.mmf.controller.RoboAdvisorController;
import com.gtl.mmf.service.util.BeanLoader;

import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
/**
 * 
 * @author bhagyashree.chavan
 *
 */
@WebServlet("/faces/selectRoboAdvisor")
public class RoboAdvisorServlet extends HttpServlet {
	private RoboAdvisorController roboAdvisorController;
	
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.RoboAdvisorServlet");	
		
	@Override
	public void init() throws ServletException {
		roboAdvisorController = (RoboAdvisorController) BeanLoader.getBean("roboAdvisorController");
	}
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}
	
	protected void process(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException {		
		try{	
			LOGGER.log(Level.INFO, "Inside RoboAdvisorServlet : process()");
			roboAdvisorController.setRequest(req);
			String redirectTo=roboAdvisorController.selectRoboAdvisor();
			LOGGER.log(Level.INFO, "redirectTo : "+redirectTo);
			if(!EMPTY_STRING.equals(redirectTo)){	
	            resp.sendRedirect("/faces"+redirectTo);				
			}
			else{
				resp.sendRedirect(req.getContextPath());
			}
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error", e);
		}
	}    
}