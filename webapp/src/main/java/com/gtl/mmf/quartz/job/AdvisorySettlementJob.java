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
 * @author 09860
 */
public class AdvisorySettlementJob extends QuartzJobBean{
    
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.AdvisorySettlementJob");
    AdvisorFeeCalculationTask advisorFeeCalculationTask;

    @Override
    protected void executeInternal(JobExecutionContext jec) throws JobExecutionException {
        
        LOGGER.info("Loading Customer Fee details");
        advisorFeeCalculationTask.loadCustomerFeeDetails();
        LOGGER.info("Completed loading task");
        
    }
    
}
