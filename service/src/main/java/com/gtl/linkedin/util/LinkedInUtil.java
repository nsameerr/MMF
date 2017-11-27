/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.linkedin.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07960
 */
public final class LinkedInUtil {
    private static final Logger LOGGER = Logger.getLogger("com.gtl.linkedin.util.LinkedInUtil");
    private static LinkedInUtil instance = null;

    private LinkedInUtil() {}    

    public static LinkedInUtil getInstance() {
        if (instance == null) {
            synchronized (LinkedInUtil.class) {
                instance = new LinkedInUtil();
            }
        }
        return instance;
    }
    
    /**
     * Method parse LinkedIn response JSON and convert to LinkedInVo object
     * @param accessCode
     * @param serParamVO
     * @param typeEnum
     * @return 
     */
    public LinkedInVO getLinkedInResponseVO(String accessCode, ServiceParamVO serParamVO,
                    ServiceTypeEnum typeEnum){
        LinkedInVO linkedInVO = null;
        try{
            URLServiceImpl urlservice = new URLServiceImpl();
            String requestUrl = urlservice.getURL(accessCode, serParamVO, typeEnum);
            String responseJSON = urlservice.getResponseJSON(requestUrl);              
            linkedInVO = urlservice.getLinkedInVO(responseJSON);               
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
        }
        return linkedInVO;
    }
    
    /**
     * Method returns LinkedIn response as JSON
     * @param accessCode
     * @param serParamVO
     * @param typeEnum
     * @return 
     */
    
     public String getLinkedInResponseJSON(String accessCode, ServiceParamVO serParamVO,
                    ServiceTypeEnum typeEnum){
        String responseJSON = null;
        try{
            URLServiceImpl urlservice = new URLServiceImpl();
            String requestUrl = urlservice.getURL(accessCode, serParamVO, typeEnum);
            responseJSON = urlservice.getResponseJSON(requestUrl);   
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, ex.toString());
        }
        return responseJSON;
    }

}
