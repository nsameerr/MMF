/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import java.util.List;

/**
 *
 * @author trainee3
 */
public interface IPortfolioUtil {

    Double portfolioValueCal(List<PortfolioSecuritiesTb> portfolioSecuritiesTbList);

    //String buyOrSell(Integer initialUnits, Integer currentUnits);
    List<PortfolioSecuritiesTb> transactionCount(Double allocatedFund, List<PortfolioSecuritiesTb> portfolioSecuritiesTbList);

}
