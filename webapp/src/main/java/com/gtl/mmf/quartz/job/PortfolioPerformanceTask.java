/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class PortfolioPerformanceTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ClientPortfolioRebalanceTask");
    @Autowired
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;

    /**
     * This class is used to for updating the performance of portfolio.
     *# Investor portfolio  90 days returns and 1 year returns
     *# Advisor portfolio 90 days returns and 1 year returns
     *# portfolio  benchmark 90 days returns and 1 year returns
     * 
     * returns 90 day = ((closeValue/startValue90Days)-1)*100;
     * returns 1 day = ((closeValue/startValue1Year)-1)*100;
     */
    public void updatePortfolioPerformanceDetails() {
        eodPortfolioReturnsBAO.computeCustomerPFPerformance();
        eodPortfolioReturnsBAO.computeRecmdPFPerformance();
        eodPortfolioReturnsBAO.updateBenchmarkPerformance();
    }
}
