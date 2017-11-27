/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.util.StackTraceWriter;
import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.bao.IEODTransactionTransferBAO;
import com.gtl.mmf.bao.IExceptionLogBAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import static com.gtl.mmf.service.util.IConstants.YYYY_MM_DD_HH_MM_SS;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientTransactionTransferRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.ClientPortfolioReturnsRunnable");
    private List<Object> customerPortfolioTbList;
    private String threadname;
    private IEODTransactionTransferBAO eodTransactionTransferBAO;
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;
    private IExceptionLogBAO exceptionLogBAO;
    private Date eodDate;

    public ClientTransactionTransferRunnable() {
        eodTransactionTransferBAO = (IEODTransactionTransferBAO) BeanLoader.getBean("eodTransactionTransferBAO");
        eodPortfolioReturnsBAO = (IEODPortfolioReturnsBAO) BeanLoader.getBean("eodPortfolioReturnsBAO");
        exceptionLogBAO = (IExceptionLogBAO) BeanLoader.getBean("exceptionLogBAO");
    }

    public void run() {
        for (Iterator<Object> it = customerPortfolioTbList.iterator(); it.hasNext();) {
            String EODdate = DateUtil.dateToString(eodDate, YYYY_MM_DD_HH_MM_SS);
            CustomerPortfolioTb customerPortfolioTb = (CustomerPortfolioTb) it.next();
            try {
                //Saving yesterday executed units in tb for return calculation
                LOGGER.log(Level.INFO, "Updating customer portfolio securities  {0} started ", customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.updateCustomerPortfolioSecurities(customerPortfolioTb);
                LOGGER.log(Level.INFO, "Updating customer portfolio securities {0} completed ", customerPortfolioTb.getOmsLoginId());
                // transfer daily transacton details from mmf transaction summary table to transaction execution table.
                LOGGER.log(Level.INFO, threadname);
                LOGGER.log(Level.INFO, "Transfer Transaction and Cash of client {0} started ", customerPortfolioTb.getOmsLoginId());
                eodTransactionTransferBAO.transferDaliyTransactionAndCash(customerPortfolioTb, eodDate);
                LOGGER.log(Level.INFO, "Transfer Transaction and Cash of client {0} completed ", customerPortfolioTb.getOmsLoginId());

                LOGGER.log(Level.INFO, "Updating blocked cash and finding out cash flow for the day for customer {0} started ", customerPortfolioTb.getOmsLoginId());
                eodTransactionTransferBAO.findingOutCashFlowForCustomer(customerPortfolioTb);
                LOGGER.log(Level.INFO, "Updating blocked cash and finding out cash flow for the day for customer {0} completed ", customerPortfolioTb.getOmsLoginId());
                
            } catch (Exception ex) {
                String errorMessage = "Transfer transaction and cash of client "
                        .concat(customerPortfolioTb.getOmsLoginId())
                        .concat(" from mmf clone to real table ")
                        .concat(" on the Date: ").concat(EODdate).concat(" Failed. ");
                ExceptionLogUtil.logError(errorMessage, ex);
                ExceptionLogUtil.mailError(errorMessage, ex, "Please check db log for more details");
                exceptionLogBAO.logErrorDb(errorMessage, StackTraceWriter.getStackTrace(ex));
            }
            try {

                LOGGER.log(Level.INFO, "benchmark unit calculation of client {0} started ", customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.calculteBenchmarkUnitValue(customerPortfolioTb, eodDate);
                LOGGER.log(Level.INFO, "Portfolio benchmark unit calculation of client {0} completed ", customerPortfolioTb.getOmsLoginId());

                LOGGER.log(Level.INFO, "recumended customer portfolio value calculation  calculation of client {0} started ", customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.updateRecomendedCustomerPortfolio(customerPortfolioTb);
                LOGGER.log(Level.INFO, "Portfolio customer portfolio value calculation of client {0} completed ", customerPortfolioTb.getOmsLoginId());

                LOGGER.log(Level.INFO, "Portfolio returns calculation of client {0} started ", customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.calculteClientPortfolioReturns(customerPortfolioTb, EODdate);
                LOGGER.log(Level.INFO, "Portfolio returns calculation of client {0} completed ", customerPortfolioTb.getOmsLoginId());

                LOGGER.log(Level.INFO, "Eod client portflio daily opening closing calculation {0} started ", customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.eodClientPortflioDailyOpeningClosingCalculation(customerPortfolioTb, eodDate);//
                LOGGER.log(Level.INFO, "Eod client portflio daily opening closing calculation {0} completed ", customerPortfolioTb.getOmsLoginId());
                
                LOGGER.log(Level.INFO, "portfolio return calculation of client {0} for different time period started ",customerPortfolioTb.getOmsLoginId());
                eodPortfolioReturnsBAO.calculateReturnsForSpecifiedPeriod(customerPortfolioTb);
                LOGGER.log(Level.INFO, "Returns of client {0} for different time period completed ", customerPortfolioTb.getOmsLoginId());

            } catch (Exception ex) {
                String error = "Client ".concat(customerPortfolioTb.getOmsLoginId()).concat(" portfolio returns calculation task failed");
                ExceptionLogUtil.logError(error, ex);
                ExceptionLogUtil.mailError(error, ex, "Please check db for more details");
                exceptionLogBAO.logErrorDb(error, StackTraceWriter.getStackTrace(ex));
            }
        }
    }

    public void setCustomerPortfolioTbList(List<Object> customerPortfolioTbList) {
        this.customerPortfolioTbList = customerPortfolioTbList;
    }

    public void setThreadname(String threadname) {
        this.threadname = threadname;
    }

    public void setEodDate(Date eodDate) {
        this.eodDate = eodDate;
    }
}
