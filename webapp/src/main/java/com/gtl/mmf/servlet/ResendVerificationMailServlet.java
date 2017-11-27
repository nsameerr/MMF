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

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.service.util.BeanLoader;

/**
 * @author Sumeet resend verification mail to user
 */

@WebServlet("/faces/resendVMail")
public class ResendVerificationMailServlet extends HttpServlet {

	private static final Logger logger = Logger.getLogger("com.gtl.mmf.servlet.ResendVerificationMailServlet");
	private static final String STORED_VALUES = "storedValues";
	private static final String EMAIL_ID = "emailId";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		try {
			logger.log(Level.INFO, "enter ResendVerificationMailServlet");

			HttpSession session = req.getSession();
			@SuppressWarnings("unchecked")
			Map<String, Object> storedValues = (Map<String, Object>) session.getAttribute(STORED_VALUES);
			String email = (String) storedValues.get(EMAIL_ID);
			boolean userType = (session.getAttribute("advisor") != null) ? (Boolean) session.getAttribute("advisor")
					: false;

			if (email != null) {
				logger.log(Level.INFO, "resending verification mail for user :" + email);
				IUserProfileBAO userProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
				userProfileBAO.resendVerificationEmail(email, userType);
				out.write("true");
			} else {
				logger.log(Level.INFO, "resending failed userType or email null");
				out.write("false");
			}

		} catch (Exception e) {
			logger.log(Level.SEVERE, "error in resending mail", e);
			out.write("false");
			out.close();
		}

	}
}
