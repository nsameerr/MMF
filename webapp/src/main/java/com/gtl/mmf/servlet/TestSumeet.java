package com.gtl.mmf.servlet;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/faces/testSumeet")
public class TestSumeet  extends HttpServlet{
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.TestSumeet");
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doGet(req, resp);
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		LOGGER.log(Level.INFO,"fpStatus from sumeet" + session.getAttribute("fpStatus"));
		
	}

}
