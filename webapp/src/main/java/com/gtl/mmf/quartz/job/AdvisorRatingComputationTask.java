/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODAdvisorRatingBAO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class AdvisorRatingComputationTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.AdvisorRatingComputationTask");
    @Autowired
    private IEODAdvisorRatingBAO eodAdvisorRatingBAO;

    public void updateAdvisorRatingNotifications() {
        try {
            LOGGER.info("EOD : Advisor rating computation : Start");
            eodAdvisorRatingBAO.checkAdvisorRating();
            LOGGER.info("EOD : Advisor rating computation : Complete");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD : Advisor rating computation : Exception : {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    public void updateAdvisorDetails() {
        try {
            LOGGER.info("EOD : Advisor details updation : Start");
            eodAdvisorRatingBAO.updateAdvisorDetails();
            LOGGER.info("EOD : Advisor details updation : Complete");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD : Advisor details updation : Exception : {0}", StackTraceWriter.getStackTrace(e));
        }
    }
}
