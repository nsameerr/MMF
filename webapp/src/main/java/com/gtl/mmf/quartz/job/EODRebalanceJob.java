/*
 * To change this template, choose Tools | Templates
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
 * @author 07958
 */
public class EODRebalanceJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.EODJob");
    private RecomdPortfolioRebalanceTask recomdPortfolioRebalanceTask;
    private UpdateBenchmarkDetailsTask updateBenchmarkDetailsTask;
    private AdvisorRatingComputationTask advisorRatingComputationTask;
    private EODClientStatusChangeTask eodClientStatusChangeTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            LOGGER.info("Updating benchmark details.");
            updateBenchmarkDetailsTask.updateBenchmarkDetails();
            LOGGER.info("Benchmark details updated.");

            LOGGER.info("Portfolio rebalance job started.");
            LOGGER.info("Recommended portfolio rebalance started.");
            recomdPortfolioRebalanceTask.checkRecomdPortfolioRebalance();

            LOGGER.info("Rate advisor check started.");
            advisorRatingComputationTask.updateAdvisorRatingNotifications();
            LOGGER.info("Rate advisor check completd.");

            LOGGER.info("Advisor details updation job started.");
            advisorRatingComputationTask.updateAdvisorDetails();
            LOGGER.info("Advisor details updation job completed.");

            LOGGER.info("investor Status updation job started.");
            eodClientStatusChangeTask.changeCustomerNotificationStatus();
            LOGGER.info("investor Status updation job completed.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "EOD job execution failed. Exception : {0}", StackTraceWriter.getStackTrace(ex));
        }
    }

    public RecomdPortfolioRebalanceTask getRecomdPortfolioRebalanceTask() {
        return recomdPortfolioRebalanceTask;
    }

    public void setRecomdPortfolioRebalanceTask(RecomdPortfolioRebalanceTask recomdPortfolioRebalanceTask) {
        this.recomdPortfolioRebalanceTask = recomdPortfolioRebalanceTask;
    }

    public UpdateBenchmarkDetailsTask getUpdateBenchmarkDetailsTask() {
        return updateBenchmarkDetailsTask;
    }

    public void setUpdateBenchmarkDetailsTask(UpdateBenchmarkDetailsTask updateBenchmarkDetailsTask) {
        this.updateBenchmarkDetailsTask = updateBenchmarkDetailsTask;
    }

    public AdvisorRatingComputationTask getAdvisorRatingComputationTask() {
        return advisorRatingComputationTask;
    }

    public void setAdvisorRatingComputationTask(AdvisorRatingComputationTask advisorRatingComputationTask) {
        this.advisorRatingComputationTask = advisorRatingComputationTask;
    }

    public EODClientStatusChangeTask getEodClientStatusChangeTask() {
        return eodClientStatusChangeTask;
    }

    public void setEodClientStatusChangeTask(EODClientStatusChangeTask eodClientStatusChangeTask) {
        this.eodClientStatusChangeTask = eodClientStatusChangeTask;
    }

}
