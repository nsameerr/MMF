/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import static com.gtl.mmf.service.util.IConstants.FIVE;
import com.gtl.mmf.bao.IClientInvestmentPerformanceBAO;
import com.gtl.mmf.bao.ICreateEditPortfolioBAO;
import com.gtl.mmf.bao.IInvestorDashBoardBAO;
import com.gtl.mmf.dao.IClientInvestmentPerformanceDAO;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerRiskProfileTb;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.PortfolioAllocationChartUtil;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.vo.InvestorDetailsVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 07958
 */
public class ClientInvestmentPerformanceBAOImpl implements IClientInvestmentPerformanceBAO {

    @Autowired
    private IClientInvestmentPerformanceDAO clientInvestmentPerformanceDAO;
    @Autowired
    private IInvestorDashBoardBAO investorDashBoardBAO;
    @Autowired
    private ICreateEditPortfolioBAO createEditPortfolioBAO;

    /**
     * Loading client investor details for displaying in the dashboard
     *
     * @param benchMarkLineChartDataVOList
     * @param recommendedLineChartDataVOList
     * @param selfLineChartDataVOList
     * @param investorDetailsVO
     * @param portfolioAllocationJSONObj
     * @param customerPortfolioId
     * @return PortfolioVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public PortfolioVO getClientInvestmentPerformanceDetails(
            List<LineChartDataVO> benchMarkLineChartDataVOList,
            List<LineChartDataVO> recommendedLineChartDataVOList,
            List<LineChartDataVO> selfLineChartDataVOList,
            InvestorDetailsVO investorDetailsVO,
            StringBuilder portfolioAllocationJSONObj,
            Integer customerportfolioId,Integer relationId) {

        List<Object> result = clientInvestmentPerformanceDAO.getCustomerPortfolioTb(customerportfolioId,relationId);
        //CustomerPortfolioTb customerPortfolioTb = clientInvestmentPerformanceDAO.getCustomerPortfolioTb(relationId);

        CustomerPortfolioTb customerPortfolioTb = (CustomerPortfolioTb) result.get(ZERO);
        CustomerRiskProfileTb customerRiskProfileTb = (CustomerRiskProfileTb) result.get(ONE);
        Integer clientId = customerPortfolioTb.getMasterCustomerTb().getCustomerId();
        investorDetailsVO.setRelationId(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationId());
        investorDetailsVO.setCustomerId(customerPortfolioTb.getMasterCustomerTb().getCustomerId());
        investorDetailsVO.setFirstName(customerPortfolioTb.getMasterCustomerTb().getFirstName());
        investorDetailsVO.setWorkOrganization(customerPortfolioTb.getMasterCustomerTb().getWorkOrganization());
        investorDetailsVO.setJobTitle(customerPortfolioTb.getMasterCustomerTb().getJobTitle());
        investorDetailsVO.setPictureURL(customerPortfolioTb.getMasterCustomerTb().getMasterApplicantTb().getPictureUrl());
        //investorDetailsVO.setPortFolioStyle(customerPortfolioTb.getPortfolioTb().getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());
        // Display system identified risk profile
        investorDetailsVO.setPortFolioStyle(customerRiskProfileTb.getMasterPortfolioStyleTbByInitialPortfolioStyle().getPortfolioStyle());
        investorDetailsVO.setEmail(customerPortfolioTb.getMasterCustomerTb().getEmail());
        investorDetailsVO.setRegId(customerPortfolioTb.getMasterCustomerTb().getMasterApplicantTb().getRegistrationId());
        investorDetailsVO.setStatus(customerPortfolioTb.getCustomerAdvisorMappingTb().getRelationStatus());
        investorDashBoardBAO.getLineChartData(clientId, FIVE, benchMarkLineChartDataVOList,
                recommendedLineChartDataVOList, selfLineChartDataVOList);

        PortfolioVO portfolioVO = createEditPortfolioBAO.getPortfolioById(
                customerPortfolioTb.getCustomerAdvisorMappingTb().getMasterAdvisorTb().getAdvisorId(),
                customerPortfolioTb.getPortfolioTb().getPortfolioId());
        portfolioAllocationJSONObj.append(
                PortfolioAllocationChartUtil.generatePiechartJSONString(PortfolioUtil.getPortfolioDetails(portfolioVO)));

        return portfolioVO;
    }
}
