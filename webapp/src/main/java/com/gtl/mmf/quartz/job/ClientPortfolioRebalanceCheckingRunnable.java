/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.util.BeanLoader;
import com.gtl.mmf.service.util.PortfolioUtil;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 07958
 */
public class ClientPortfolioRebalanceCheckingRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.ClientPortfolioRebalanceCheckingRunnable");
    private IPortfolioRebalanceBAO portfolioRebalanceBAO;

    private String threadName;
    private List<Object> customerPortfolioVOs;

    public ClientPortfolioRebalanceCheckingRunnable(String threadName, List<Object> customerPortfolioVOs) {
        this.threadName = threadName;
        this.customerPortfolioVOs = customerPortfolioVOs;
        portfolioRebalanceBAO = (IPortfolioRebalanceBAO) BeanLoader.getBean("portfolioRebalanceBAO");
    }

    public void run() {
        LOGGER.log(Level.INFO, "Client portfolio rebalance - Running thread : {0}", this.threadName);
        for (Iterator<Object> it = customerPortfolioVOs.iterator(); it.hasNext();) {
            CustomerPortfolioVO customerPortfolioVO = (CustomerPortfolioVO) it.next();
            LOGGER.log(Level.INFO, "Client portfolio rebalance - Thread : {0} - checking portfolio id {1}", new Object[]{threadName, customerPortfolioVO.getCustomerPortfolioId()});
            RebalanceStateVO rebalanceStateVO = new RebalanceStateVO();
            rebalanceStateVO.setPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
            if (!this.isPortfolioRebalanceEnabled(customerPortfolioVO)) {
                LOGGER.log(Level.INFO, "Client portfolio rebalance - Thread : {0} - updating market data of portfolio id {1}", new Object[]{threadName, customerPortfolioVO.getCustomerPortfolioId()});
                customerPortfolioVO.setFromAddReedem(false);
                PortfolioUtil.updatePortfolioMarketData(customerPortfolioVO);
                boolean portfolioRebalanceRequired = PortfolioUtil.isPortfolioRebalanceRequired(customerPortfolioVO,
                        rebalanceStateVO.getRebalanceStateSecurityVOs(), rebalanceStateVO.getRebalanceStateAssetVOs());
                LOGGER.log(Level.INFO, "Client portfolio rebalance - Thread : {0} - portfolio id {1} - Rebalance required status {2}", new Object[]{threadName, customerPortfolioVO.getCustomerPortfolioId(), portfolioRebalanceRequired});
                PortfolioUtil.setPortfolioRebalanceState(rebalanceStateVO, portfolioRebalanceRequired);
            } else {
                // Set required false. (Since master portfolio rebalance requred enabled.)
                PortfolioUtil.setPortfolioRebalanceState(rebalanceStateVO, false);
            }
            
            /*
             This Check is for investor with no_rebalance_status = true.
             For those Investor's with fresh contract start and no_rebalance_status =  true,
             rebalance portfolio must be triggered (it's must).
             Even when rebalance is initiated for the advisor
             */
            if (customerPortfolioVO.isNoreBalanceStatus()!=null && customerPortfolioVO.isNoreBalanceStatus()) {
                PortfolioUtil.setPortfolioRebalanceState(rebalanceStateVO, true);
            }
            // end of investor no_rebalance_status check
            
            portfolioRebalanceBAO.updateRebalanceCustomerPortfolio(rebalanceStateVO);
        }
    }

    private boolean isPortfolioRebalanceEnabled(CustomerPortfolioVO customerPortfolioVO) {
        return customerPortfolioVO.isRebalanceEnabledForAdvisor() == null ? false
                : customerPortfolioVO.isRebalanceEnabledForAdvisor();
    }
}
