/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.bao.IEODTransactionTransferBAO;
import com.gtl.mmf.bao.IExceptionLogBAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.Date;
import java.util.List;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class EODMMFTransactoionTransferTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.PortfolioReturnsTask");

    @Autowired
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;

    @Autowired
    private IExceptionLogBAO exceptionLogBAO;

    public void transferToRealTxn() {
        IEODTransactionTransferBAO eodTransactionTransferBAO = (IEODTransactionTransferBAO) BeanLoader.getBean("eodTransactionTransferBAO");
        List<CustomerPortfolioTb> customerPortfolioTbList;
        Date eodDate = null;
        try {
            List<Object> resultList = eodPortfolioReturnsBAO.loadAllClientPortfolio();
            customerPortfolioTbList = (List<CustomerPortfolioTb>) resultList.get(ZERO);
            eodDate = (Date) resultList.get(1);
            eodTransactionTransferBAO.buildTransactiontramsferThread(customerPortfolioTbList, eodDate);
        } catch (Exception ex) {
            String errorMsg = "Daily txn and cash transfer task (from mmf clone to real) failed.";
            ExceptionLogUtil.logError(errorMsg, ex);
            ExceptionLogUtil.mailError(errorMsg, ex, "Please check db for more details.");
            exceptionLogBAO.logErrorDb(errorMsg, StackTraceWriter.getStackTrace(ex));
        }
    }

}
