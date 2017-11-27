/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */

package com.gtl.mmf.common;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gtl.mmf.bao.IUserProfileBAO;
import com.gtl.mmf.service.util.BASE64Encrption;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.PropertiesLoader;

public class RegistrationVerificationServlet extends HttpServlet implements IConstants{

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
	private static final String HOME_PAGE = "new.login.page";
	private static final String INV_LOGIN = "2";
	private static final String ADV_LOGIN = "3";
	private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.servlet.LoginServletV2");
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("Please wait. <br> We are verifing your email Address.");
        try {
            String parmeter = (String) request.getParameter(VERIFICATION_MAIL_URL_PARAM_1);
            String emailEncripted = (String) request.getParameter(EMAIL);
            IUserProfileBAO iUserProfileBAO = (IUserProfileBAO) BeanLoader.getBean("userProfileBAO");
            if (iUserProfileBAO.emailverification(parmeter,emailEncripted)) {
            	
            	String userType = BASE64Encrption.decrypt((String) request.getParameter(USERTYPE));
            	if(userType.equals(INVESTOR)){
            		boolean result = processInvestorVerification(emailEncripted,userType,iUserProfileBAO);
            		response.sendRedirect( request.getContextPath() +PropertiesLoader.getPropertiesValue(HOME_PAGE) + "?src="+INV_LOGIN + "&vr="+result);
            		
            	} else if (userType.equals(ADVISOR)) { 
            		response.sendRedirect( request.getContextPath() + PropertiesLoader.getPropertiesValue(HOME_PAGE) + "?src="+ADV_LOGIN + "&vr=true");
            	} else {
            		String url = request.getContextPath().concat("/faces/pages/login.xhtml?")
                            .concat(VERIFICATION_MAIL_URL_PARAM_1).concat(EQ_SIGN)
                            .concat(AMPERSAND).concat(MAIL_VERIFICATION).concat(EQ_SIGN).concat(BASE64Encrption.encrypt(TRUE))
                            .concat(AMPERSAND).concat(USERTYPE).concat(EQ_SIGN)
                            .concat((String) request.getParameter(USERTYPE));
                    response.sendRedirect(url);
            	}
                
            }else{
               // response.sendError(404);
                response.sendRedirect(request.getContextPath() + "/faces/pages/message.xhtml");
                return;
            }
        }catch(Exception e){
        	LOGGER.log(Level.SEVERE, "error in mail verification :",e);
        } 
        finally {
            out.close();
        }
    }

    private boolean processInvestorVerification(String emailEncripted, String userType, IUserProfileBAO iUserProfileBAO) {
		String email = BASE64Encrption.decrypt(emailEncripted);
		// check is record is present in master tb
		// if yes update mail verified bit and send mail 
		boolean detailsSubmitted = iUserProfileBAO.updateVerificationIfUserDetailsSubmitted(email,userType);
		if(detailsSubmitted){
			//mailed verified bit updated
			//send mail
			//response.sendRedirect();
		
		}
		return detailsSubmitted;
		// else skip
	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    
}
