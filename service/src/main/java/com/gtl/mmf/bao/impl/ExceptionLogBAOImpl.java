/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IExceptionLogBAO;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class ExceptionLogBAOImpl implements IExceptionLogBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ExceptionLogBAOImpl");

    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    /**
     * This method logs into DB when ever an exception occured
     *
     * @param errorMessage - message to save
     * @param exception Exception occured
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void logErrorDb(String errorMessage, String exception) {
        try {
            exceptionLogDAO.logErrorToDb(errorMessage, exception);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

}
