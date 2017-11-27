/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao;

import com.gtl.mmf.service.vo.AdvisorDetailsVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.CustomerRiskProfileVO;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.service.vo.QuestionResponseVO;
import com.gtl.mmf.util.MMFException;
import java.util.List;
import java.util.Map;

public interface IInvestmentPolicyStmtBAO {

    public void getPageDetails(int customerId,
            List<QuestionResponseVO> questionResponseVOList,
            CustomerRiskProfileVO customerRiskProfileVO);

    public void getPortfolioAssignPageDetails(int relationId, CustomerRiskProfileVO customerRiskProfileVO,
            Map<Integer, PortfolioVO> portfoliosMap, List<PieChartDataVO> pieChartDataVOList,
            CustomerPortfolioVO customerPortfolioVO, List<PortfolioSecurityVO> securityVo);

    public void customerViewIPS(int relationId, CustomerRiskProfileVO customerRiskProfileVO,
            Map<Integer, PortfolioVO> portfoliosMap, List<PieChartDataVO> pieChartDataVOList,
            CustomerPortfolioVO customerPortfolioVO, List<PortfolioSecurityVO> securityVo);

    public List<PieChartDataVO> refreshPieChartData(int portfolioId);

    public void saveInvestmentPolicy(CustomerRiskProfileVO customerRiskProfileVO);

    public void saveAssignPortFolio(CustomerPortfolioVO customerPortfolioVO, InvestorDetailsVO investorDetails,
            String advisorName, Boolean isPortfolioCahnged) throws MMFException;

    public void customerReviewIPS(CustomerPortfolioVO customerPortfolioVO, AdvisorDetailsVO advisorDetailsVO, String investorName) throws ClassNotFoundException;

    public void customerAcceptIPS(CustomerPortfolioVO customerPortfolioVO, AdvisorDetailsVO advisorDetailsVO, String investorName) throws ClassNotFoundException;

    public Integer getPortfolioByStyleAndSize(Integer portfolioStyleSelected, Integer portfolioSizeSelected);
    
    //public Integer getCustomerRelationstatus(Integer customerId);
}
