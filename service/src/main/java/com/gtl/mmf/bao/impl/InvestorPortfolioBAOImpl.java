/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IInvestorPortfolioBAO;
import com.gtl.mmf.dao.IInvestorPortfolioDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class InvestorPortfolioBAOImpl implements IInvestorPortfolioBAO {

    @Autowired
    private IInvestorPortfolioDAO investorPortfolioDAO;

    /**
     * This method is used to display the details of portfolio assigned by the
     * advisor to investor once the investor accept the IPS
     *
     * @param customerPortfolioVO
     * @param pieChartDataVOList
     * @param portfolioDetailsVOList
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getViewPortfoliopageDetails(CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> pieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList) {

        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
        customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);

        // Loading portfolio informations
        List<Object> responseItems = investorPortfolioDAO.getViewPortfolioPageDetails(customerPortfolioTb);
        /**
         * Checking the size of data returned it should be 3
         * 1.CustomerPortfolioTb 
         * 2.CustomerPortfolioDetailsTb  portfolio asset class details
         * 3.CustomerPortfolioSecuritiesTb portfolio securities in each asset class informations
         */
        if (responseItems.size() == 3) {
            customerPortfolioTb = (CustomerPortfolioTb) responseItems.get(ZERO);
            customerPortfolioVO.setPortfolioId(customerPortfolioTb.getPortfolioTb().getPortfolioId());
            customerPortfolioVO.setPorfolioStyleName(customerPortfolioTb.getPortfolioTb().getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());
            customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getId();
            customerPortfolioVO.setBenchmarkName(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
            customerPortfolioVO.setPortfolioName(customerPortfolioTb.getPortfolioTb().getPortfolioName());
            customerPortfolioVO.setNoreBalanceStatus(customerPortfolioTb.getNoRebalanceStatus());
            customerPortfolioVO.setRebalanceRequired(customerPortfolioTb.getRebalanceRequired());
            for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : (List<CustomerPortfolioDetailsTb>) responseItems.get(1)) {

            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            PieChartDataVO pieChartDataVO = new PieChartDataVO();
            pieChartDataVO.setLabel(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
            pieChartDataVO.setData(customerPortfolioDetailsTb.getNewAllocation().doubleValue());
            pieChartDataVO.setColor(customerPortfolioDetailsTb.getMasterAssetTb().getAssetColor());
            pieChartDataVOList.add(pieChartDataVO);

            portfolioDetailsVO.setAssetId(customerPortfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setAssetClassName(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
            portfolioDetailsVO.setMinimumRange(customerPortfolioDetailsTb.getRangeFrom() == null ? ZERO
                    : customerPortfolioDetailsTb.getRangeFrom().doubleValue());
            portfolioDetailsVO.setMaximumRange(customerPortfolioDetailsTb.getRangeTo() == null ? ZERO
                    : customerPortfolioDetailsTb.getRangeTo().doubleValue());
            portfolioDetailsVO.setNewAllocation(customerPortfolioDetailsTb.getNewAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getNewAllocation().doubleValue());

            List<PortfolioSecurityVO> portfolioSecurityVOList = new ArrayList<PortfolioSecurityVO>();
            for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : (List<CustomerPortfolioSecuritiesTb>) responseItems.get(2)) {
                if (customerPortfolioSecuritiesTb.getMasterAssetTb().getId().equals(portfolioDetailsVO.getAssetId()) &&
                        customerPortfolioSecuritiesTb.getStatus()) {
                    PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
                    portfolioSecurityVO.setAssetClass(customerPortfolioSecuritiesTb.getMasterAssetTb().getAssetName());
                    portfolioSecurityVO.setAssetClassId(customerPortfolioSecuritiesTb.getMasterAssetTb().getId());
                    portfolioSecurityVO.setSecurityId(customerPortfolioSecuritiesTb.getSecurityId());
                    portfolioSecurityVO.setScripDecription(customerPortfolioSecuritiesTb.getSecurityDescription());
                    portfolioSecurityVO.setNewAllocation(customerPortfolioSecuritiesTb.getNewAllocation() == null ? ZERO
                            : customerPortfolioSecuritiesTb.getNewAllocation().doubleValue());
                    portfolioSecurityVOList.add(portfolioSecurityVO);
                }
            }
            portfolioDetailsVO.setSecurities(portfolioSecurityVOList);
            portfolioDetailsVOList.add(portfolioDetailsVO);
        }
    }
    }
}
