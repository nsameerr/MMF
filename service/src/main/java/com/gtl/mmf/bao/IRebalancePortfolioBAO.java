/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao;

import com.git.oms.api.frontend.message.as.response.AuthenticationResponseDTO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTransactionExecutionDetailsTb;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.TodaysDetailsVO;
import java.util.List;

public interface IRebalancePortfolioBAO {

    public void getPortfolioRebalancePageDetails(CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> targetPieChartDataVOList,
            List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList);

    public void rebalancePortfolio(List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList,
            CustomerPortfolioVO customerPortfolioVO);

    public void executeSecurityOrders(CustomerPortfolioVO customerPortfolioVO,
            List<PortfolioDetailsVO> portfolioDetailsVOList, OMSOrderVO omsOrderVO,boolean orderType);

    public List generatePortfolioReport(OMSOrderVO omsOrderVO);

    public void saveTransEOD(CustomerPortfolioVO customerPortfolioVO);

    public void getDPClientMFdetails(OMSOrderVO omsOrderVO);
    
    public List getTodaysOrderDetails(OMSOrderVO omsOrderVO);
    
    public void cancelPlacedOrder(OMSOrderVO omsOrderVO, TodaysDetailsVO todaysDetailsVO);
    
    public String omslogout(OMSOrderVO omsOrderVO);
    
    public List getTradeSummary(TodaysDetailsVO todaysDetailsVO, OMSOrderVO omsOrderVO);
    
    public List getPortfolioSummary(OMSOrderVO omsOrderVO, boolean sinkbp);
    
    public List getOMSRejectionReport(OMSOrderVO omsOrderVO);

    public void saveSellPortfolioDetails(List<PortfolioSecurityVO> filteredSecurityList, OMSOrderVO omsOrderVO);

    public void saveCustomerBP(String omsLoginId, float bp);
}
