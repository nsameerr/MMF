/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IEODTransactionTransferBAO;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.dao.IRebalancePortfolioDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.service.util.ClientTransactionTransferRunnable;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.PRODUCTION;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.ServiceThreadUtil;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class EODTrasactionTransferBAOImpl implements IEODTransactionTransferBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.EODPortfolioReturnsBAOImpl");

    @Autowired
    private IRebalancePortfolioDAO rebalancePortfolioDAO;

    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void buildTransactiontramsferThread(List<CustomerPortfolioTb> customerPortfolioTbList, Date eodDate) {
        try {
            Integer thredPollSize = ServiceThreadUtil.getPartitionedListSize(Long.valueOf(customerPortfolioTbList.size()));
            ExecutorService taskExecutor = Executors.newFixedThreadPool(thredPollSize);
            List<List<Object>> partitionedList = ServiceThreadUtil.getPartitionedList(thredPollSize);
            for (CustomerPortfolioTb customerPortfolioTb : customerPortfolioTbList) {
                Integer index = customerPortfolioTbList.indexOf(customerPortfolioTb);
                List<Object> customerPortfolioTbs = partitionedList.get(index % thredPollSize);
                customerPortfolioTbs.add(customerPortfolioTb);
            }
            for (List<Object> customerPortfolioTbs : partitionedList) {
                ClientTransactionTransferRunnable clientTransactionTransferRunnable = new ClientTransactionTransferRunnable();
                clientTransactionTransferRunnable.setCustomerPortfolioTbList(customerPortfolioTbs);
                clientTransactionTransferRunnable.setEodDate(eodDate);
                clientTransactionTransferRunnable.setThreadname("Thread Number:- " + partitionedList.listIterator().nextIndex());
                taskExecutor.execute(clientTransactionTransferRunnable);
            }
            taskExecutor.shutdown();
            boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (awaitTermination) {
                LOGGER.info("EOD Trasfer client trasaction to real : Execution terminated and customer portfolio returns computed.");
            } else {
                LOGGER.severe("EOD Trasfer client trasaction to real : Execuion timeout elapsed before termination.");
            }
        } catch (InterruptedException ex) {
            ExceptionLogUtil.logError("All clients trasaction from mmf clone to real table failed.", ex);
            ExceptionLogUtil.mailError("All clients trasaction from mmf clone to real table failed.", ex, "Please check db for more details.");
            exceptionLogDAO.logErrorToDb("All clients trasaction from mmf clone to real table failed.", StackTraceWriter.getStackTrace(ex));
        }

    }

    /**
     * This class is used to transfer daily transaction details into client
     * table
     *
     * @param customerPortfolioTb
     * @param eodDate
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void transferDaliyTransactionAndCash(CustomerPortfolioTb customerPortfolioTb, Date eodDate) {
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        rebalancePortfolioDAO.saveTransactionsEOD(customerPortfolioTb, eodDate, environment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void findingOutCashFlowForCustomer(CustomerPortfolioTb customerPortfolioTb) {
       rebalancePortfolioDAO.insertIntoCashTxnForTheDayTb(customerPortfolioTb);
    }
}
