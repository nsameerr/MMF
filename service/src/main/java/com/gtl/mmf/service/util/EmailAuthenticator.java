/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

/**
 *
 * @author 07960
 */
public class EmailAuthenticator extends javax.mail.Authenticator {

    private String userName;
    private String passWord;

    public EmailAuthenticator(String userName, String passWord) {
        this.userName = userName;
        this.passWord = passWord;
    }

    @Override
    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
        return new javax.mail.PasswordAuthentication(userName, passWord);
    }

}
