/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
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
 * Advisor portfolio re-balancing task. Checking portfolio re-balance with
 * multi-threading. Here all the portfolios are Partitioned into list and each
 * list is transfered into thread for processing
 *
 * @author 07958
 */
public class RecomdPortfolioRebalanceTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.RecomdPortfolioRebalanceTask");
    @Autowired
    private IPortfolioRebalanceBAO portfolioRebalanceBAO;
    private List<List<Object>> portfolios;

    public RecomdPortfolioRebalanceTask() {
        portfolios = new ArrayList<List<Object>>();
    }

    public void checkRecomdPortfolioRebalance() throws Exception {
        /*
         Get portfolios where status = true.
         Portfolios are Partitioned and the Partitioned list is returned
         */
        portfolios = portfolioRebalanceBAO.getPortfoliosToCheck(LookupDataLoader.getAssetClasses());
        int portfolioListsSize = portfolios == null ? ZERO : portfolios.size();
        if (portfolioListsSize == ZERO) {
            LOGGER.info("There is no portfolio to perform rebalance task.");
        } else {
            // Checking portfolio rebalance with mutithreading.
            ExecutorService taskExecutor = Executors.newFixedThreadPool(portfolios.size());
            for (List<Object> portfolioVOs : portfolios) {
                String threadName = "RecmdPFRebalanceThread_" + portfolios.indexOf(portfolioVOs);

                //Executing Thread
                taskExecutor.execute(new PortfolioRebalanceCheckingRunnable(threadName, portfolioVOs));
            }

            taskExecutor.shutdown();
            System.out.println("recommended portfoliorebalance finished.");
            try {
                boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                if (awaitTermination) {
                    // Executor terminated and rebalance checking done.
                    LOGGER.info("Recommended portfolio rebalance - Threads completed execution and rebalance checking completed.");
                } else {
                    // Executor terminated due to time out.
                    LOGGER.severe("Recommended portfolio rebalance - Executor timeout elapsed before termination.");
                }
            } catch (InterruptedException e) {
                LOGGER.log(Level.SEVERE, "Interrupted while waiting of completion of rebalance executor. Exception message : {0}", StackTraceWriter.getStackTrace(e));
            }
        }
    }
}
