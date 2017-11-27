/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author trainee12
 */
public class EODExchangeStatusResetJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.EODExchangeStatusResetJob");
    private ResetExchangeStatusTask resetExchangeStatusTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            
            LOGGER.info("Resetting  Exchange status ");
            resetExchangeStatusTask.resetOnlineStatus();
            LOGGER.info("Reset finished");

        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "EOD ExchangeStatusResetJob execution failed. Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    public ResetExchangeStatusTask getResetExchangeStatusTask() {
        return resetExchangeStatusTask;
    }

    public void setResetExchangeStatusTask(ResetExchangeStatusTask resetExchangeStatusTask) {
        this.resetExchangeStatusTask = resetExchangeStatusTask;
    }
    
}
