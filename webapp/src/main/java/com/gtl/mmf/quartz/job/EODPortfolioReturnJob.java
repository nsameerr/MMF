/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.util.StackTraceWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class EODPortfolioReturnJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.EODRebalanceJob");
    private PortfolioReturnsTask portfolioReturnsTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        try {
            LOGGER.info("Client portfolio returns calculation started.");
            portfolioReturnsTask.calculateClientPortfolioReturns();
            LOGGER.info("Client portfolio returns calculation terminated.");
            LOGGER.info("Master portfolio returns calculation started.");
            portfolioReturnsTask.calculateRecommendedPortfolioReturns();
            LOGGER.info("Master portfolio returns calculation terminated.");
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, "Portfolio Returns EOD job execution failed. Exception : {0}",StackTraceWriter.getStackTrace(ex));
        }
    }

    public PortfolioReturnsTask getPortfolioReturnsTask() {
        return portfolioReturnsTask;
    }

    public void setPortfolioReturnsTask(PortfolioReturnsTask portfolioReturnsTask) {
        this.portfolioReturnsTask = portfolioReturnsTask;
    }

}
