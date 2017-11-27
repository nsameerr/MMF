/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */

package com.gtl.mmf.common;

import com.octo.captcha.service.CaptchaServiceException;
import java.awt.image.BufferedImage; 
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class ImageCaptchaServlet extends HttpServlet {

 
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);
        CaptchaService.getInstance();
    }
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
        try {
            byte[] captchaChallengeAsJpeg = null;
            // the output stream to render the captcha image as jpeg into
            ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
            try {
                // get the session id that will identify the generated captcha.
                //the same id must be used to validate the response, the session id is a good candidate!
                String captchaId = request.getSession().getId();
                // call the ImageCaptchaService getChallenge method
                BufferedImage challenge = CaptchaService.getInstance().getImageChallengeForID(
                                                            captchaId,request.getLocale());
                // a jpeg encoder
                ImageIO.write(challenge, "jpeg",jpegOutputStream);
            } catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            } catch (CaptchaServiceException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
        captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        // flush it in the response
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream =
                response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
        } finally {
            response.getOutputStream().close();
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
        //processRequest(request, response);
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
