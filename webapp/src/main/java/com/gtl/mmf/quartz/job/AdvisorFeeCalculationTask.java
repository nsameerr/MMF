/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IAdvisorySettlemetBAO;
import java.util.logging.Logger;

/**
 *
 * @author 09860
 */
public class AdvisorFeeCalculationTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.AdvisorFeeCalculationTask");
    private IAdvisorySettlemetBAO iAdvisorySettlemetBAO;
    public void loadCustomerFeeDetails() {
        LOGGER.info("Load customer fee details");
        iAdvisorySettlemetBAO.getCustomerFeeDetails();
        LOGGER.info("Load completed for customer fee details");
        

    }
}
