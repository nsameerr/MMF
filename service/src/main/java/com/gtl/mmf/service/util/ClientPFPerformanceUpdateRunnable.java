/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07958
 */
public class ClientPFPerformanceUpdateRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.ClientPFPerformanceUpdateRunnable");
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;
    private String threadName;
    private List<Object> portfolioIds;

    public ClientPFPerformanceUpdateRunnable(String threadName, List<Object> portfolioIds) {
        this.threadName = threadName;
        this.portfolioIds = portfolioIds;
        eodPortfolioReturnsBAO = (IEODPortfolioReturnsBAO) BeanLoader.getBean("eodPortfolioReturnsBAO");
    }

    public void run() {
        LOGGER.log(Level.INFO, "Client portfolio performance calculation - Running thread : {0}", this.threadName);
        for (Iterator<Object> it = portfolioIds.iterator(); it.hasNext();) {
            Integer clientPortfolioId = (Integer) it.next();
            try {
                eodPortfolioReturnsBAO.updateCustomerPFPerformance(clientPortfolioId);
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Exception in portfolio performance calculation - Running thread : {0} - portfolio id : {1} \n Exception : {2}", new Object[]{this.threadName, clientPortfolioId,StackTraceWriter.getStackTrace(e)});
            }
        }
        LOGGER.log(Level.INFO, "Client portfolio performance calculation - Running thread : {0} - completed.", this.threadName);
    }
}
