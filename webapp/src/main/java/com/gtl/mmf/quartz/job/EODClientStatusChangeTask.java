package com.gtl.mmf.quartz.job;

import org.springframework.beans.factory.annotation.Autowired;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;

/**
 * Updating status of those who placed order for execution.
 *
 * @author trainee8
 */
public class EODClientStatusChangeTask {

    @Autowired
    private IPortfolioRebalanceBAO portfolioRebalanceBAO;

    public void changeCustomerNotificationStatus() throws Exception {
        portfolioRebalanceBAO.changeCustomerNotificationStatus();
    }

}
