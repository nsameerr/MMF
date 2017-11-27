/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.dao;

import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.persist.vo.RebalanceStateAssetVO;
import com.gtl.mmf.persist.vo.RebalanceStateSecurityVO;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IPortfolioRebalanceDAO {

    public Long getPortfoliosToCheckSize();

    public Long getClientPortfoliosToCheckSize();

    public List<PortfolioTb> getPortfoliosToCheck();

    public List<CustomerPortfolioTb> getClientPortfoliosToCheck();

    public void updateRebalancePortfolio(RebalanceStateVO rebalanceStateVO);

    public void updateRebalancePortfolioSecurities(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs, int portfolioId);

    public void updateRebalancePortfolioDetails(List<RebalanceStateAssetVO> rebalanceStateAssetVOs, int portfolioId);

    public void updateRebalanceCustomerPortfolio(RebalanceStateVO rebalanceStateVO);

    public void updateRebalanceCustomerPortfolioDetails(List<RebalanceStateAssetVO> rebalanceStateAssetVOs, int cutomerPortfolioId);

    public void updateRebalanceCustomerPortfolioSecurities(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs, int cutomerPortfolioId);

    public CustomerPortfolioTb getCustomerDetails(Integer customerId);

    public PortfolioTb getAdvisorDetails(Integer portfolioId);

    public void updateNotificationStatus(Integer status,String jobType, Boolean dummyFlag);
    
}
