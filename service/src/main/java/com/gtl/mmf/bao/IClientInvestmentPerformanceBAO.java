/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import java.util.List;

/**
 *
 * @author 07958
 */
public interface IClientInvestmentPerformanceBAO {

    public PortfolioVO getClientInvestmentPerformanceDetails(
            List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList,
            List<LineChartDataVO> selfLineChartDataVOList,
            InvestorDetailsVO investorDetailsVO,
            StringBuilder portfolioAllocationJSONObj,
            Integer customerportfolioId, Integer relationid);
}
