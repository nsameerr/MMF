/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.bao.IUpdateCashFlowBAO;
import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import com.gtl.mmf.bao.IFeeCalculationBAO;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.util.StackTraceWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author 07958
 */
public class FeeCalculationTask {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.FeeCalculationTask");
    @Autowired
    private IEODPortfolioReturnsBAO eodPortfolioReturnsBAO;
    @Autowired
    private IUpdateCashFlowBAO iUpdateCashFlowBAO;
    @Autowired
    private IFeeCalculationBAO iFeeCalculationBAO;

    public void calculateMMFFee() {
        
        List<EcsDebtPaymentFileContentTb> customerFeeCalculation = iUpdateCashFlowBAO.customerFeeCalculation();
        
        LOGGER.info("#############   Calculating Advisory settlement fee    ###########");
        if (DateUtil.isQuarter()) {
            calculateAdvisoryFee();
        }
        LOGGER.info("#############   Advisor Fee calculated   ###########");
        
        LOGGER.info("#############   Saving ECS Transaction data    ###########");
        if (!customerFeeCalculation.isEmpty()) {
            iUpdateCashFlowBAO.saveEcsDetails(customerFeeCalculation);
        }
        LOGGER.info("#############   Saving ECS Transaction data  Completed  ###########");
    }

    /**
     * This method is used to call the calculation for Performance fee. This
     * method selects relations that need to pay performance FEE. calculated on
     * INTERVAL can be 3,6,12 MONTH in contract period
     *
     */
    private void calculatePerformanceFee() {
        try {
            List<Integer> relationsToCalculateMgmtFee = eodPortfolioReturnsBAO.getRelationsToCalculatePerfFee();
            LOGGER.log(Level.INFO, "EOD-FeeCalculationTask : Performance fee : {0} investors.", relationsToCalculateMgmtFee.size());
            for (Integer relationId : relationsToCalculateMgmtFee) {
                LOGGER.log(Level.INFO, "FeeCalculationTask : Performance fee : updating with relationId {0}", relationId);
                eodPortfolioReturnsBAO.updatePerformanceFeeOfRelation(relationId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD-FeeCalculationTask : Performance fee : Exception - {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    /**
     * This method is used to call the calculation for Variable type management
     * fee/Fixed type management fee MgmtFee = CustomerPortfolioValue *
     * VariableFeeRate / 100.0;
     */
    private void calculateManagementFee() {
        try {
            List<Integer> relationsToCalculateMgmtFee = eodPortfolioReturnsBAO.getRelationsToCalculateMgmtFee();
            LOGGER.log(Level.INFO, "EOD-FeeCalculationTask: Management fee : {0} investors.", relationsToCalculateMgmtFee.size());
            for (Integer relationId : relationsToCalculateMgmtFee) {
                LOGGER.log(Level.INFO, "EOD-FeeCalculationTask: Management fee : updating with relationId {0}", relationId);
                eodPortfolioReturnsBAO.updateManagementFeeOfRelation(relationId);
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD-FeeCalculationTask: Management fee : Exception - {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    private void calculateAdvisoryFee() {
        try {
            LOGGER.log(Level.INFO, "********************************************EOD-FeeCalculationTask: Advisory fee******************************");
            iFeeCalculationBAO.CalculateQuarterlyAdvisoryFee();
            LOGGER.log(Level.INFO, "********************************************EOD-FeeCalculationTask: Advisory fee Completed******************************");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD-FeeCalculationTask: Advisory fee : Exception - {0}", StackTraceWriter.getStackTrace(e));
        }
    }
}
