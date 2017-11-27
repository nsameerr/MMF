/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ExceptionLogUtil {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.util.ExceptionLogUtil");
    private static final String MMFADMIN = "MMFAdmin";
    private static final String MAIL_SUBJECT = "MMF daily job execution failed ";

    public ExceptionLogUtil() {
    }

    public static void logError(String errorMessage, Exception exception) {
        LOGGER.log(Level.SEVERE, errorMessage, StackTraceWriter.getStackTrace(exception));
    }

    public static void mailError(String errorMessage, Exception exception, String mailContent) {
//        try {
//            String mailFormedContent = errorMessage.concat("\r\n ").concat(mailContent);
//            List<String> toAddress = new ArrayList<String>();
//            toAddress.add(PropertiesLoader.getPropertiesValue(MAIL_SMTP_TO));
//            MailUtil.getInstance().sendMail(PropertiesLoader.getPropertiesValue(MAIL_SMTP_FROM), 
//                                            toAddress, MAIL_SUBJECT, mailFormedContent);
//        } catch (ClassNotFoundException ex) {
//            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(exception));
//        } catch (UnsupportedEncodingException ex) {
//            LOGGER.log(Level.SEVERE, null, StackTraceWriter.getStackTrace(exception));
//        }
    }
}
