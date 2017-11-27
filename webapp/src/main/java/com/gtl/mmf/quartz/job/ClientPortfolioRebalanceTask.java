/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class ClientPortfolioRebalanceTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ClientPortfolioRebalanceTask");
    @Autowired
    private IPortfolioRebalanceBAO portfolioRebalanceBAO;
    private List<List<Object>> portfolios;

    public ClientPortfolioRebalanceTask() {
        portfolios = new ArrayList<List<Object>>();
    }

    public void checkClientPortfolioRebalance() throws Exception {
        // Get customer portfolios where status = true.
        portfolios = portfolioRebalanceBAO.getClientPortfoliosToCheck();
        int portfolioListsSize = portfolios == null ? ZERO : portfolios.size();
        if (portfolioListsSize == ZERO) {
            LOGGER.info("There is no client portfolio to perform rebalance task.");
        } else {
            // Checking customer portfolio rebalance with mutithreading.
            ExecutorService taskExecutor = Executors.newFixedThreadPool(portfolios == null ? ZERO : portfolios.size());
            for (List<Object> customerPortfolioVOs : portfolios) {
                String threadName = "ClientPFRebalanceThread_" + portfolios.indexOf(customerPortfolioVOs);
                taskExecutor.execute(new ClientPortfolioRebalanceCheckingRunnable(threadName, customerPortfolioVOs));
            }

            taskExecutor.shutdown();
            try {
                boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                if (awaitTermination) {
                    // Executor terminated and rebalance checking done.
                    LOGGER.info("Client portfolio rebalance - Threads completed execution and rebalance checking completed.");
                } else {
                    // Executor terminated due to time out.
                    LOGGER.severe("Client portfolio rebalance - Executor timeout elapsed before termination.");
                }
            } catch (InterruptedException ex) {
                LOGGER.log(Level.SEVERE, "Interrupted while waiting of completion of rebalance executor. Exception message : {0}", StackTraceWriter.getStackTrace(ex));
            }
        }
    }
}
