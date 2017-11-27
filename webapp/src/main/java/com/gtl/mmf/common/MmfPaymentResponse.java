/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.common;

import com.gtl.mmf.bao.IBilldeskTransactionProcessorBAO;
import com.gtl.mmf.service.util.BeanLoader;
import static com.gtl.mmf.service.util.IConstants.BILLDESK_MSG;
import static com.gtl.mmf.service.util.IConstants.FROM_PAYMENT_RESPONSE;
import static com.gtl.mmf.service.util.IConstants.REDIRECT_TO_FUND_TRANFER_PAGE;
import static com.gtl.mmf.service.util.IConstants.STORED_VALUES;
import static com.gtl.mmf.service.util.IConstants.TRANSACTIONRESPONSE;
import com.gtl.mmf.service.vo.TransactionResponseVo;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jboss.logging.Logger;

/**
 *
 * @author trainee8
 */
public class MmfPaymentResponse extends HttpServlet {
private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.common.MmfPaymentResponse");
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try {
            LOGGER.log(Logger.Level.INFO,"In process request,reading response");
            String parameter = (String) request.getParameter(BILLDESK_MSG);
            LOGGER.log(Logger.Level.INFO,"Billdesk response "+parameter);
            IBilldeskTransactionProcessorBAO transactionProcessor = (IBilldeskTransactionProcessorBAO) BeanLoader.getBean("billdeskTransactionProcessorBAO");
            TransactionResponseVo transactionResponse = transactionProcessor.processBillDeskResponse(parameter);
            Map<String, Object> storedValues = null;
            if (transactionResponse != null) {
                storedValues = (Map<String, Object>) FacesContext.getCurrentInstance().getExternalContext().
                        getSessionMap().get(STORED_VALUES);
                storedValues.put(TRANSACTIONRESPONSE, transactionResponse);
                storedValues.put(FROM_PAYMENT_RESPONSE, true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(STORED_VALUES, storedValues);
                response.sendRedirect(REDIRECT_TO_FUND_TRANFER_PAGE);
            }
        } finally {
            out.close();
        }
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
