/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import java.util.List;

public interface IInvestorPortfolioBAO {

    public void getViewPortfoliopageDetails(CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> pieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList);

}
