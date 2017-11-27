/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IMailStatusBAO;
import com.gtl.mmf.dao.IMailStatusDAO;
import com.gtl.mmf.service.util.IConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author trainee7
 */
public class MailStatusBAOImpl implements IMailStatusBAO, IConstants {

    static final Logger logger = Logger.getLogger("com.gtl.mmf.bao.impl.MailStatusBAOImpl");
    @Autowired
    private IMailStatusDAO mailStatusDAO;

    /**
     * This method is used to update investor/advisor status when mail delivery
     * fails.
     *
     * @param tableClassName
     * @param email
     * @param status
     * @return
     */
    public Integer updateMailStatus(String tableClassName, String email, boolean status) {

        try {
            return mailStatusDAO.updateMailSendStatus(tableClassName, email, status);
        } catch (Exception ex) {
            Logger.getLogger(MailStatusBAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
