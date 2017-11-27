/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.persist.vo.RebalanceStateVO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author 07958
 */
public interface IPortfolioRebalanceBAO {

    public List<List<Object>> getPortfoliosToCheck(Map<String, Short> assets);

    public List<List<Object>> getClientPortfoliosToCheck();

    public void updateRebalancePortfolio(RebalanceStateVO RebalanceStateVO);

    public void updateRebalanceCustomerPortfolio(RebalanceStateVO rebalanceStateVO);
    
    public void changeCustomerNotificationStatus();
}
