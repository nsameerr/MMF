/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.bao.IExceptionLogBAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used for the calculation of recommended portfolio returns
 * @author trainee8
 */
public class PortfolioReturnsTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.PortfolioReturnsTask");

    @Autowired
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;

    @Autowired
    private IExceptionLogBAO exceptionLogBAO;

    public void calculateClientPortfolioReturns() {
        List<CustomerPortfolioTb> customerPortfolioTbList;
        Date eodDate = null;
        try {
            List<Object> resultList = eodPortfolioReturnsBAO.loadAllClientPortfolio();
            customerPortfolioTbList = (List<CustomerPortfolioTb>) resultList.get(0);
            eodDate = (Date) resultList.get(1);
            eodPortfolioReturnsBAO.buildClientPortfolioReturnThread(customerPortfolioTbList, eodDate);
        } catch (Exception ex) {
            ExceptionLogUtil.logError("Client portfolio returns calculation task failed", ex);
            ExceptionLogUtil.mailError("Client portfolio returns calculation task failed", ex, "Please check db for more details");
            exceptionLogBAO.logErrorDb("Client portfolio returns calculation task failed", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method calls for the calculation of recommended portfolio returns 1.
     * Master Portfolio Returns Calculation 2. Master Portfolio Assets Class
     * Returns Calculation 3. Master Portfolio Securities Returns Calculation
     */
    public void calculateRecommendedPortfolioReturns() {
        try {
            eodPortfolioReturnsBAO.calculateRecommendedPortfolioReturns();
        } catch (Exception ex) {
            ExceptionLogUtil.logError("Recommended portfolio returns calculation failed.", ex);
            ExceptionLogUtil.mailError("Recommended portfolio returns calculation failed.", ex, "Please check db for more details");
            exceptionLogBAO.logErrorDb("Recommended portfolio returns calculation failed.", StackTraceWriter.getStackTrace(ex));
        }
    }

}
