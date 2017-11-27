/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 0766  
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.util.List;

public interface IPortfolioDetailsBAO {

    public void getPortfolioDetailsPageData(CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> targetPieChartDataVOList,
            List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList);

    public void rebalancePortfolio(List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList,
            CustomerPortfolioVO customerPortfolioVO);

    public void getReturnsForSpecifiedPeriod(CustomerPortfolioVO customerPortfolioVO,
            List<PortfolioDetailsVO> portfolioDetailsVOList, Integer noOfdays);

    public List<PortfolioSecurityVO> getSecuritesOfAssetClass(CustomerPortfolioVO customerPortfolioVO,
            Integer assetClassId, Integer noOfdays);

    public CustomerPortfolioVO getAssetBreakdownPageDetails(CustomerPortfolioVO customerPortfolioVO);

    public void getTwrReturnsForSpecifiedPeriod(CustomerPortfolioVO customerPortfolioVO, Integer dateperiod);
    
    public List<PortfolioSecurityVO> getInvestorDashboardData(String omsid,Integer customerId,List<PortfolioSecurityVO> portfolioSecurityVOlist);
    
    public boolean checkForContractTermination(Integer customerId);

}
