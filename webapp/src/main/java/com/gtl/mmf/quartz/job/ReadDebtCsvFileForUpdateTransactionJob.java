/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import java.util.logging.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 *
 * @author trainee8
 */
public class ReadDebtCsvFileForUpdateTransactionJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ReadDebtCsvFileForUpdateTransactionJob");
    ECSTransactionsTask eCSTransactionsTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        LOGGER.info("Reading Debt Excel file for ECS.");
        eCSTransactionsTask.readingDebtFileFromEcs();
        LOGGER.info("completed Reading Debt text file for ECS.");
    }

    public ECSTransactionsTask geteCSTransactionsTask() {
        return eCSTransactionsTask;
    }

    public void seteCSTransactionsTask(ECSTransactionsTask eCSTransactionsTask) {
        this.eCSTransactionsTask = eCSTransactionsTask;
    }
}
