/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.quartz.job;

import com.gtl.mmf.bao.IPortfolioRebalanceBAO;
import com.gtl.mmf.persist.vo.RebalanceStateAssetVO;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.service.util.BeanLoader;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import com.gtl.mmf.service.util.PortfolioUtil;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class for checking portfolio re-balancing
 *
 * @author 07958
 */
public class PortfolioRebalanceCheckingRunnable implements Runnable {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.PortfolioRebalanceCheckingRunnable");
    private IPortfolioRebalanceBAO portfolioRebalanceBAO;
    private String threadName;
    private List<Object> portfolioVOs;

    /**
     *
     * @param threadName
     * @param portfolioVOs
     */
    public PortfolioRebalanceCheckingRunnable(String threadName, List<Object> portfolioVOs) {
        this.threadName = threadName;
        this.portfolioVOs = portfolioVOs;
        portfolioRebalanceBAO = (IPortfolioRebalanceBAO) BeanLoader.getBean("portfolioRebalanceBAO");
        LOGGER.log(Level.INFO, "Recommended portfolio rebalance - Creating thread : {0}", this.threadName);
    }

    public void run() {
        LOGGER.log(Level.INFO, "Recommended portfolio rebalance - Running thread : {0}", this.threadName);
        for (Iterator<Object> it = portfolioVOs.iterator(); it.hasNext();) {
            PortfolioVO portfolioVO = (PortfolioVO) it.next();
            LOGGER.log(Level.INFO, "Recommended portfolio rebalance - Thread : {0} - checking portfolio id {1}", new Object[]{threadName, portfolioVO.getPortfolioId()});
            LOGGER.log(Level.INFO, "Recommended portfolio rebalance - Thread : {0} - updating market data of portfolio id {1}", new Object[]{threadName, portfolioVO.getPortfolioId()});
           
            /**
             * Updating portfolio market price to current value
             */
            PortfolioUtil.updatePortfolioMarketData(portfolioVO);
            RebalanceStateVO rebalanceStateVO = new RebalanceStateVO();
            rebalanceStateVO.setPortfolioId(portfolioVO.getPortfolioId());
            LOGGER.log(Level.INFO, "Recommended portfolio rebalance started for - Thread : {0} - portfolio id ,{1} - portfolio name {2}", new Object[]{threadName, portfolioVO.getPortfolioId(), portfolioVO.getPortfolioName()});
            boolean portfolioRebalanceRequired = PortfolioUtil.isPortfolioRebalanceRequired(portfolioVO,
                    rebalanceStateVO.getRebalanceStateSecurityVOs(), rebalanceStateVO.getRebalanceStateAssetVOs());
            rebalanceStateVO.setCurrentValue(getPortfolioValue(rebalanceStateVO.getRebalanceStateAssetVOs()));
            LOGGER.log(Level.INFO, "Recommended portfolio rebalance - Thread : {0} - portfolio id {1} - Rebalance required status {2}", new Object[]{threadName, portfolioVO.getPortfolioId(), portfolioRebalanceRequired});
            PortfolioUtil.setPortfolioRebalanceState(rebalanceStateVO, portfolioRebalanceRequired);
            portfolioRebalanceBAO.updateRebalancePortfolio(rebalanceStateVO);
        }
    }

    private Double getPortfolioValue(List<RebalanceStateAssetVO> rebalanceStateAssetVOs) {
        Double portfolioValue = ZERO_POINT_ZERO;
        for (RebalanceStateAssetVO rebalanceStateAssetVO : rebalanceStateAssetVOs) {
            portfolioValue += rebalanceStateAssetVO.getCurrentValue();
        }
        return portfolioValue;
    }
}
