/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 *
 * @author 07960
 */
public interface IEmailUtil {

    public boolean sendMail(String from, List<String> to, String subject, String content) throws ClassNotFoundException, UnsupportedEncodingException;

    public void sendNotificationMail(String firstName, String email, String notificationMsg) throws ClassNotFoundException;
}
