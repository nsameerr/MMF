/**
 *
 */
package com.gtl.mmf.quartz.job;

import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.util.StackTraceWriter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import static com.gtl.mmf.service.util.IConstants.PRODUCTION;

/**
 * @author 08237
 *
 */
public class TxnAndCashBalDataProcessingJob extends QuartzJobBean {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.TxnAndCashBalDataProcessingJob");
    private TxnAndCashBalTask txnAndCashBalTask;
    private PortfolioReturnsTask portfolioReturnsTask;
    private ClientPortfolioRebalanceTask clientPortfolioRebalanceTask;
    private PortfolioPerformanceTask portfolioPerformanceTask;
    private FeeCalculationTask feeCalculationTask;
    private EODMMFTransactoionTransferTask eodMMFTransactoionTransferTask;
    private UpdateCashFlowTask updateCashFlowTask;

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        try {
            String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
            LOGGER.log(Level.INFO, "MMF application environment choosen {0}", environment);
            /**
             * Transfer data from back office DB to MMF DB condition for
             * transfer : omsEnabled(TRUE) and preProduction(FALSE)
             */
            if (environment.equalsIgnoreCase(PRODUCTION)) {
                LOGGER.log(Level.INFO, "Transfer Daily txn and cash task (from back office to mmf clone) started");
                txnAndCashBalTask.transferDataTask();
                LOGGER.log(Level.INFO, "Transfer txn and cash task (from back office to mmf clone completed");
            }
            LOGGER.log(Level.INFO, "Update Allocated Amount for customer");
            updateCashFlowTask.updateAllocationFund();
            LOGGER.log(Level.INFO, "Update Allocated Amount completed");

            LOGGER.log(Level.INFO, "Transfer Daily txn and cash task (from mmf clone to real) and portfolio returns task started");
            eodMMFTransactoionTransferTask.transferToRealTxn();
            LOGGER.log(Level.INFO, "Transfer txn and cash from mmf temp table task (from mmf clone to real) and portfolio returns task completed");

            LOGGER.info("Client portfolio rebalance started.");
            clientPortfolioRebalanceTask.checkClientPortfolioRebalance();
            LOGGER.info("Client portfolio rebalance job completed.");

            LOGGER.info("Master portfolio returns calculation started.");
            portfolioReturnsTask.calculateRecommendedPortfolioReturns();
            LOGGER.info("Master portfolio returns calculation terminated.");

            LOGGER.log(Level.INFO, "Rename table( mmf_daily_txn_summary_tb, mmf_daily_client_balance_tb, mmf_ret_portfolio_splitup");
            LOGGER.log(Level.INFO, "and 5 Day older data clear task started");
            txnAndCashBalTask.renameTableAndClaer5DayOlderData();
            LOGGER.log(Level.INFO, "Rename table 5 Day older data clear task completed");

            LOGGER.info("Client/Recommended Portfolio performance calculation started.");
            portfolioPerformanceTask.updatePortfolioPerformanceDetails();
            LOGGER.info("Client/Recommended Portfolio performance calculation completed.");

            LOGGER.info("FeeCalculationTask calculation started.");
            feeCalculationTask.calculateMMFFee();
            LOGGER.info("FeeCalculationTask calculation completed.");

            // ########### below code is commented for pre-production(test) working. ##########
            // ########### must uncomment for production ##########
//            LOGGER.info("Back office data clear task started.");
//            txnAndCashBalTask.clearBackOfficeData();
//            LOGGER.info("Back office data clear task completed");
            // ############   end of comment #####################
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public TxnAndCashBalTask getTxnAndCashBalTask() {
        return txnAndCashBalTask;
    }

    public void setTxnAndCashBalTask(TxnAndCashBalTask txnAndCashBalTask) {
        this.txnAndCashBalTask = txnAndCashBalTask;
    }

    public PortfolioReturnsTask getPortfolioReturnsTask() {
        return portfolioReturnsTask;
    }

    public void setPortfolioReturnsTask(PortfolioReturnsTask portfolioReturnsTask) {
        this.portfolioReturnsTask = portfolioReturnsTask;
    }

    public ClientPortfolioRebalanceTask getClientPortfolioRebalanceTask() {
        return clientPortfolioRebalanceTask;
    }

    public void setClientPortfolioRebalanceTask(ClientPortfolioRebalanceTask clientPortfolioRebalanceTask) {
        this.clientPortfolioRebalanceTask = clientPortfolioRebalanceTask;
    }

    public PortfolioPerformanceTask getPortfolioPerformanceTask() {
        return portfolioPerformanceTask;
    }

    public void setPortfolioPerformanceTask(PortfolioPerformanceTask portfolioPerformanceTask) {
        this.portfolioPerformanceTask = portfolioPerformanceTask;
    }

    public FeeCalculationTask getFeeCalculationTask() {
        return feeCalculationTask;
    }

    public void setFeeCalculationTask(FeeCalculationTask feeCalculationTask) {
        this.feeCalculationTask = feeCalculationTask;
    }

    public EODMMFTransactoionTransferTask getEodMMFTransactoionTransferTask() {
        return eodMMFTransactoionTransferTask;
    }

    public void setEodMMFTransactoionTransferTask(EODMMFTransactoionTransferTask eodMMFTransactoionTransferTask) {
        this.eodMMFTransactoionTransferTask = eodMMFTransactoionTransferTask;
    }

    public UpdateCashFlowTask getUpdateCashFlowTask() {
        return updateCashFlowTask;
    }

    public void setUpdateCashFlowTask(UpdateCashFlowTask updateCashFlowTask) {
        this.updateCashFlowTask = updateCashFlowTask;
    }

}
