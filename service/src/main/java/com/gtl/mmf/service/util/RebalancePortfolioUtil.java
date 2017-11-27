/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import static com.gtl.mmf.service.util.IConstants.HUNDRED;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * utility class for rebalancing
 *
 * @author trainee3
 */
public class RebalancePortfolioUtil implements IPortfolioUtil {

    double portfolioValue;
    Double tranCount;

    public Double portfolioValueCal(List<PortfolioSecuritiesTb> portfolioSecuritiesTbList) {

        for (PortfolioSecuritiesTb securityItem : portfolioSecuritiesTbList) {
            portfolioValue = portfolioValue + (securityItem.getExeUnits().doubleValue() * securityItem.getCurrentPrice().doubleValue());
        }

        return portfolioValue;
    }

    /**
     * 
     * @param newUnits - number of units that can BUY using current allocation
     * @param currentUnits -number of units already have
     * @return int 
     */
    public static int buyOrSell(Integer newUnits, Integer currentUnits) {

        if (newUnits == ZERO) {
            return ZERO;
        }
        if (newUnits < currentUnits) {
            return BuySell.SELL.value();
        } else if (newUnits > currentUnits) {
            return BuySell.BUY.value();
        } else {
            return ZERO;
        }
    }

    public List<PortfolioSecuritiesTb> transactionCount(Double allocatedFund, List<PortfolioSecuritiesTb> portfolioSecuritiesTbList) {
        for (PortfolioSecuritiesTb securityItem : portfolioSecuritiesTbList) {
            tranCount = ((allocatedFund * (securityItem.getNewAllocation().doubleValue() / HUNDRED)) / securityItem.getCurrentPrice().doubleValue());
            //securityItem.setExeUnits((int) Math.floor(tranCount));
            securityItem.setExeUnits(new BigDecimal(Math.floor(tranCount)));
        }
        return portfolioSecuritiesTbList;
    }

    /**
     * This method is used for calculation transaction units for a security
     *
     * @param allocatedFund -current portfolio value
     * @param allocation - allocation for security
     * @param price - current price for security
     * @return
     */
    public static int getTransactioUnits(double allocatedFund, double allocation, double price) {
        if (price == ZERO) {
            return ZERO;
        } else {
            return (int) Math.floor(((allocatedFund * (allocation / HUNDRED)) / price));
        }
    }

}
