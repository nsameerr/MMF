/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * creted by 07662
 */
package com.gtl.mmf.bao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.gtl.mmf.bao.IPortfolioDetailsBAO;
import com.gtl.mmf.dao.IPortfolioDetailsDAO;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesPerformanceTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTwrPortfolioReturnTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesPerformanceTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class PortfolioDetailsBAOImpl implements IPortfolioDetailsBAO, IConstants {

    @Autowired
    private IPortfolioDetailsDAO portfolioDetailsDAO;

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getPortfolioDetailsPageData(CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> targetPieChartDataVOList,
            List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList) {

        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
        masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
        customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);

        List<Object> responseItems = portfolioDetailsDAO.getRebalancePortfolioPageDetails(customerPortfolioTb);

        buildCustomerPortfolioData(responseItems, customerPortfolioVO);

        for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : (List<CustomerPortfolioDetailsTb>) responseItems.get(1)) {

            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            targetPieChartDataVOList.add(buildPieChartdata(customerPortfolioDetailsTb, Boolean.TRUE));
            customerPieChartDataVOList.add(buildPieChartdata(customerPortfolioDetailsTb, Boolean.FALSE));

            portfolioDetailsVO.setAssetId(customerPortfolioDetailsTb.getMasterAssetTb().getId());
            portfolioDetailsVO.setAssetClassName(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
            portfolioDetailsVO.setMinimumRange(customerPortfolioDetailsTb.getRangeFrom() == null ? ZERO
                    : customerPortfolioDetailsTb.getRangeFrom().doubleValue());
            portfolioDetailsVO.setMaximumRange(customerPortfolioDetailsTb.getRangeTo() == null ? ZERO
                    : customerPortfolioDetailsTb.getRangeTo().doubleValue());
            portfolioDetailsVO.setNewAllocation(customerPortfolioDetailsTb.getNewAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getNewAllocation().doubleValue());
            portfolioDetailsVO.setCurrentAllocation(customerPortfolioDetailsTb.getCurrentAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getCurrentAllocation());
            portfolioDetailsVO.setCustomerTwrPortfolioReturnTbs(customerPortfolioDetailsTb.getCustomerTwrPortfolioReturnTbs());

            // To display Acquisition value,gainorloss            
            List<Object[]> customerAssetList;
            if (!customerPortfolioDetailsTb.getMasterAssetTb().getAssetName().equalsIgnoreCase(TEXT_CASH)) {
                customerAssetList = portfolioDetailsDAO.getaquisitionAndGainLoss((Integer) customerPortfolioDetailsTb.getMasterAssetTb().getId().intValue(),
                        customerPortfolioVO.getCustomerId());
                for (Object[] assetlist : customerAssetList) {
                    portfolioDetailsVO.setAcquisitionCost((Double) assetlist[0]);
                    Double currentVal = (Double) assetlist[1];
                    Double gainOrLoss = currentVal - ((Double) assetlist[0]);
                    portfolioDetailsVO.setTotalgainrLoss(gainOrLoss);
                    portfolioDetailsVOList.add(portfolioDetailsVO);
                }
            } else {
                portfolioDetailsVO.setAssetClassName(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
                portfolioDetailsVO.setAcquisitionCost(ZERO_POINT_ZERO);
                portfolioDetailsVO.setTotalgainrLoss(ZERO_POINT_ZERO);
                portfolioDetailsVOList.add(portfolioDetailsVO);
            }
        }
        customerPortfolioVO.setPortfolioDetailsVOs(portfolioDetailsVOList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void rebalancePortfolio(List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList,
            CustomerPortfolioVO customerPortfolioVO) {

        float toatalValue = ZERO;
        int chasIndex = ZERO;
        for (PieChartDataVO pieChartDataVOS : customerPieChartDataVOList) {
            if (pieChartDataVOS.getData() != ZERO) {
                toatalValue = (float) (toatalValue + pieChartDataVOS.getData());
            }
            if (pieChartDataVOS.getLabel().equals(TEXT_CASH)) {
                chasIndex = customerPieChartDataVOList.indexOf(pieChartDataVOS);
            }
        }
        customerPieChartDataVOList.get(chasIndex).setData((double) ONE - toatalValue);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getReturnsForSpecifiedPeriod(CustomerPortfolioVO customerPortfolioVO,
            List<PortfolioDetailsVO> portfolioDetailsVOList, Integer noOfdays) {
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerPortfolioDetailsTb customerPortfolioDetailsTb = new CustomerPortfolioDetailsTb();
        MasterAssetTb masterAssetTb = new MasterAssetTb();
        PortfolioTb portfolioTb = new PortfolioTb();
        customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
        portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
        customerPortfolioDetailsTb.setCustomerPortfolioTb(customerPortfolioTb);
        String eODdate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
        customerPortfolioVO.setPfTwrReturn(ZEROD);
        customerPortfolioVO.setRcmdPfTwrReturn(ZEROD);
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOList) {
            portfolioDetailsVO.setCustomerTWR(ZEROD);
            if(portfolioDetailsVO.getAssetId() == 10){
                portfolioDetailsVO.setMarketValue(portfolioDetailsVO.getAssetId() == 10 
                        ? new BigDecimal(customerPortfolioVO.getCashAmount()) : BigDecimal.ZERO);
            }
            portfolioDetailsVO.setRecommmendedTWR(ZEROD);
            masterAssetTb.setId(portfolioDetailsVO.getAssetId());
            customerPortfolioDetailsTb.setMasterAssetTb(masterAssetTb);
            customerPortfolioDetailsTb.setPortfolioTb(portfolioTb);
            List<Object> responseList = portfolioDetailsDAO.getReurnsforSpecifiedPeriod(customerPortfolioDetailsTb,
                    noOfdays, eODdate);
            if (!responseList.isEmpty()) {
                List<Object> responseItems = (List<Object>) responseList.get(ZERO);
                if (!responseItems.isEmpty()) {
                    Object[] items = (Object[]) responseItems.get(ZERO);
                    if (items.length != ZERO) {
                        portfolioDetailsVO.setCustomerTWR(Double.parseDouble(items[0].toString()) / HUNDRED);
                        portfolioDetailsVO.setMarketValue(BigDecimal.valueOf(Double.parseDouble(items[ONE].toString())));
                    }
                }
                responseItems = (List<Object>) responseList.get(ONE);
                portfolioDetailsVO.setRecommmendedTWR(Double.parseDouble(responseItems.get(ZERO).toString()));
            }
        }
        List<Object> responseList2 = portfolioDetailsDAO.getTotalReurnsforSpecifiedPeriod(customerPortfolioTb,
                noOfdays, eODdate);
        if (!responseList2.isEmpty()) {
            List<Object> responsedata = (List<Object>) responseList2.get(ZERO);
            customerPortfolioVO.setPfTwrReturn(Double.parseDouble(responsedata.get(ZERO).toString()));
            responsedata = (List<Object>) responseList2.get(ONE);
            customerPortfolioVO.setRcmdPfTwrReturn(Double.parseDouble(responsedata.get(ZERO).toString()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public CustomerPortfolioVO getAssetBreakdownPageDetails(CustomerPortfolioVO customerPortfolioVO) {

        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
        customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
        customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
        MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
        masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
        customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);
        List<Object> responseItems = portfolioDetailsDAO.getRebalancePortfolioPageDetails(customerPortfolioTb);

        buildCustomerPortfolioData(responseItems, customerPortfolioVO);
        return customerPortfolioVO;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<PortfolioSecurityVO> getSecuritesOfAssetClass(CustomerPortfolioVO customerPortfolioVO,
            Integer assetClassId, Integer noOfdays) {
        List<PortfolioSecurityVO> portfolioSecurityVOlist = new ArrayList<PortfolioSecurityVO>();
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        CustomerPortfolioDetailsTb customerPortfolioDetailsTb = new CustomerPortfolioDetailsTb();
        String eODdate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
        MasterAssetTb masterAssetTb = new MasterAssetTb();
        PortfolioTb portfolioTb = new PortfolioTb();
        masterAssetTb.setId(assetClassId.shortValue());
        customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
        portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
        customerPortfolioDetailsTb.setCustomerPortfolioTb(customerPortfolioTb);
        customerPortfolioDetailsTb.setMasterAssetTb(masterAssetTb);
        customerPortfolioDetailsTb.setPortfolioTb(portfolioTb);
        List<Object> responseList = portfolioDetailsDAO.getSecuritesOfAssetClass(customerPortfolioDetailsTb, noOfdays, eODdate);
        List<CustomerPortfolioSecuritiesPerformanceTb> customerSecuritiesList
                = (List<CustomerPortfolioSecuritiesPerformanceTb>) responseList.get(ZERO);
        List<PortfolioSecuritiesPerformanceTb> masterSecuritiesList
                = (List<PortfolioSecuritiesPerformanceTb>) responseList.get(ONE);
        Map<Integer, List<CustomerPortfolioSecuritiesPerformanceTb>> customerSecurityMap
                = new HashMap<Integer, List<CustomerPortfolioSecuritiesPerformanceTb>>();
        Map<Integer, List<PortfolioSecuritiesPerformanceTb>> masterSecurityMap
                = new HashMap<Integer, List<PortfolioSecuritiesPerformanceTb>>();

        for (CustomerPortfolioSecuritiesPerformanceTb customerPortfolioSecuritiesPerformanceTb : customerSecuritiesList) {
            // Checking whether the security is remove from customer portfolio and completely sold out
            if (customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getStatus()
                    || customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getExeUnits().doubleValue() != ZERO) {
                Integer securityId = customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getPortfolioSecuritiesTb().getPortfolioSecuritiesId();
                if (customerSecurityMap.get(securityId) == null) {
                    customerSecurityMap.put(securityId, new ArrayList<CustomerPortfolioSecuritiesPerformanceTb>());
                }
                customerSecurityMap.get(securityId).add(customerPortfolioSecuritiesPerformanceTb);
            }
        }

        for (PortfolioSecuritiesPerformanceTb portfolioSecuritiesPerformanceTb : masterSecuritiesList) {
            Integer securityId = portfolioSecuritiesPerformanceTb.getPortfolioSecuritiesTb().getPortfolioSecuritiesId();
            if (masterSecurityMap.get(securityId) == null) {
                masterSecurityMap.put(securityId, new ArrayList<PortfolioSecuritiesPerformanceTb>());
            }
            masterSecurityMap.get(securityId).add(portfolioSecuritiesPerformanceTb);
        }
        for (Map.Entry<Integer, List<CustomerPortfolioSecuritiesPerformanceTb>> entry : customerSecurityMap.entrySet()) {
            PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
            Double clienSecuritySubreturn = ONED;
            Double masterSecuritySubreturn = ONED;
            Double subreturn = ZEROD;
            for (CustomerPortfolioSecuritiesPerformanceTb customerPortfolioSecuritiesPerformanceTb : entry.getValue()) {
                Integer securityId = customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getPortfolioSecuritiesTb().getPortfolioSecuritiesId();
                portfolioSecurityVO.setPortfolioSecurityId(securityId);
                portfolioSecurityVO.setSecurityCode(customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getSecurityCode());
                /*Double currentvalue
                        = (customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getExeUnits().doubleValue()
                        * customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getCurrentPrice().doubleValue());*/
                Double currentvalue
                        = (customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getExeUnits().doubleValue()
                        * customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getCurrentPrice().doubleValue());
                Double blockedValue 
                        = (customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getBlockCount().doubleValue()
                        * customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getCurrentPrice().doubleValue());
                portfolioSecurityVO.setCurrentValue(currentvalue + blockedValue);
                portfolioSecurityVO.setCurrentValue(customerPortfolioSecuritiesPerformanceTb.getMarketValue().doubleValue());
                Double acquisitionCost = customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getExeUnits().doubleValue()
                        * customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getAverageRate().doubleValue();
                portfolioSecurityVO.setAcquisitionCost(acquisitionCost);
                portfolioSecurityVO.setCurrentPrice(customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getCurrentPrice().doubleValue());
                portfolioSecurityVO.setExecutedUnits(customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb().getExeUnits().doubleValue());
                Double portfolioCurentValue = customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioTb().getCurrentValue() == null
                        ? ONE : customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioTb().getCurrentValue().doubleValue();
                Double portfolioPercentage = (currentvalue / portfolioCurentValue) * HUNDRED;
                portfolioSecurityVO.setClientportfolioPercentage(PortfolioUtil.roundToTwoDecimal(portfolioPercentage));
                subreturn = customerPortfolioSecuritiesPerformanceTb.getSubPeriodReturn() == null
                        ? ZERO : customerPortfolioSecuritiesPerformanceTb.getSubPeriodReturn().doubleValue();
                clienSecuritySubreturn = clienSecuritySubreturn * (ONE + subreturn);
                portfolioSecurityVO.setScripDecription(customerPortfolioSecuritiesPerformanceTb.getCustomerPortfolioSecuritiesTb()
                        .getSecurityDescription());
                portfolioSecurityVO.setBlockedCount(customerPortfolioSecuritiesPerformanceTb.
                        getCustomerPortfolioSecuritiesTb().getBlockCount().doubleValue());

            }
            portfolioSecurityVO.setClientTWR(PortfolioUtil.roundToTwoDecimal((clienSecuritySubreturn - ONED)) * HUNDRED);
            for (PortfolioSecuritiesPerformanceTb portfolioSecuritiesPerformanceTb : masterSecurityMap.get(entry.getKey())) {
//                Double currentvalue
//                        = portfolioSecuritiesPerformanceTb.getPortfolioSecuritiesTb().getExeUnits().doubleValue()
//                        * portfolioSecuritiesPerformanceTb.getPortfolioSecuritiesTb().getCurrentPrice().doubleValue();
//                Double portfolioCurentValue = portfolioSecuritiesPerformanceTb.getPortfolioTb().getPortfolioValue() == null
//                        ? ZERO : portfolioSecuritiesPerformanceTb.getPortfolioTb().getPortfolioValue().doubleValue();
//                Double portfolioPercentage = (currentvalue / portfolioCurentValue) * HUNDRED;
//                portfolioSecurityVO.setMasterportfolioPercentage(portfolioPercentage);
                portfolioSecurityVO.setMasterportfolioPercentage(portfolioSecuritiesPerformanceTb.getPortfolioSecuritiesTb().getNewAllocation().doubleValue());
                subreturn = portfolioSecuritiesPerformanceTb.getSubPeriodReturn() == null
                        ? ZERO : portfolioSecuritiesPerformanceTb.getSubPeriodReturn().doubleValue();
                masterSecuritySubreturn = masterSecuritySubreturn * (ONE + subreturn);
            }
            portfolioSecurityVO.setRecommendedTWR((masterSecuritySubreturn - ONED) * HUNDRED);
            double gainLoss = PortfolioUtil.roundToTwoDecimal(portfolioSecurityVO.getCurrentValue() - portfolioSecurityVO.getAcquisitionCost());
            portfolioSecurityVO.setTotalGainLoss(gainLoss);
            portfolioSecurityVO.setGainOrLoss((gainLoss >= ZERO_POINT_ZERO));
            portfolioSecurityVOlist.add(portfolioSecurityVO);
        }
        return portfolioSecurityVOlist;
    }

    private PieChartDataVO buildPieChartdata(CustomerPortfolioDetailsTb customerPortfolioDetailsTb, boolean chartType) {
        PieChartDataVO pieChartDataVO = new PieChartDataVO();
        pieChartDataVO.setLabel(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
        pieChartDataVO.setColor(customerPortfolioDetailsTb.getMasterAssetTb().getAssetColor());
        if (chartType) {
            pieChartDataVO.setData(
                    customerPortfolioDetailsTb.getNewAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getNewAllocation().doubleValue());
        } else {
            pieChartDataVO.setData(
                    customerPortfolioDetailsTb.getCurrentAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getCurrentAllocation().doubleValue());
        }
        return pieChartDataVO;
    }

    private void buildCustomerPortfolioData(List<Object> responseItems, CustomerPortfolioVO customerPortfolioVO) {
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        customerPortfolioTb = (CustomerPortfolioTb) responseItems.get(ZERO);
        customerPortfolioVO.setCustomerPortfolioId(customerPortfolioTb.getCustomerPortfolioId());
        customerPortfolioVO.setPortfolioId(customerPortfolioTb.getPortfolioTb().getPortfolioId());
        customerPortfolioVO.setPorfolioStyleName(customerPortfolioTb.getPortfolioTb().getMasterPortfolioTypeTb().getMasterPortfolioStyleTb().getPortfolioStyle());
        customerPortfolioVO.setBenchmarkName(customerPortfolioTb.getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
        customerPortfolioVO.setPortfolioName(customerPortfolioTb.getPortfolioTb().getPortfolioName());
        customerPortfolioVO.setNoreBalanceStatus(customerPortfolioTb.getNoRebalanceStatus());
        customerPortfolioVO.setAlocatedFunds(customerPortfolioTb.getCustomerAdvisorMappingTb().getAllocatedFunds());
        customerPortfolioVO.setCustomerId(customerPortfolioTb.getMasterCustomerTb().getCustomerId());
        customerPortfolioVO.setRebalanceRequired(customerPortfolioTb.getRebalanceRequired());
        customerPortfolioVO.setLastExeDate(customerPortfolioTb.getLastExecutionUpdate());
        customerPortfolioVO.setCashAmount(customerPortfolioTb.getCashAmount());
        customerPortfolioVO.setOmsLoginId(customerPortfolioTb.getOmsLoginId());
        customerPortfolioVO.setOmsPAN(customerPortfolioTb.getMasterCustomerTb().getPanNo());
        customerPortfolioVO.setOmsdob(customerPortfolioTb.getMasterCustomerTb().getDob());
        customerPortfolioVO.setBlockedCash(customerPortfolioTb.getBlockedCash()==null ? ZERO : customerPortfolioTb.getBlockedCash()); 
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getTwrReturnsForSpecifiedPeriod(CustomerPortfolioVO customerPortfolioVO, Integer dateperiod) {
        customerPortfolioVO.setPfTwrReturn(ZEROD);
        customerPortfolioVO.setRcmdPfTwrReturn(ZEROD);
        String eODdate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
        PortfolioTb portfolioTb = new PortfolioTb();
        portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
        customerPortfolioTb.setPortfolioTb(portfolioTb);
        for (PortfolioDetailsVO portfolioDetailsVO : customerPortfolioVO.getPortfolioDetailsVOs()) {
            Set<CustomerTwrPortfolioReturnTb> CustomerTwrPortfolioReturnTb = portfolioDetailsVO.getCustomerTwrPortfolioReturnTbs();
            for (CustomerTwrPortfolioReturnTb twrPortfolioReturnTb : CustomerTwrPortfolioReturnTb) {
                switch (dateperiod) {
                    case 0:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturnAll().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturnAll().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValueAll());
                        break;
                    case 5:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn5d().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn5d().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue5d());
                        break;
                    case 30:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn1m().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn1m().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue1m());
                        break;
                    case 90:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn3m().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn3m().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue3m());
                        break;
                    case 180:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn6m().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn6m().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue6m());
                        break;
                    case 365:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn1y().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn1y().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue1y());
                        break;
                    case 1825:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn5y().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn5y().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue5y());
                        break;
                    case 3650:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturn10y().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturn10y().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValue10y());
                        break;
                    default:
                        portfolioDetailsVO.setCustomerTWR(twrPortfolioReturnTb.getAssetReturnYtd().doubleValue());
                        portfolioDetailsVO.setRecommmendedTWR(twrPortfolioReturnTb.getRcmReturnYtd().doubleValue());
                        portfolioDetailsVO.setMarketValue(twrPortfolioReturnTb.getMarketValueYtd());
                        break;
                }              
                /*portfolioDetailsVO.setMarketValue(portfolioDetailsVO.getAssetId() == 10
                        ? new BigDecimal(customerPortfolioVO.getCashAmount()) : portfolioDetailsVO.getMarketValue());*/
                
                //Changes to include blocked cash to ledger
                float blockedcash = customerPortfolioVO.getCashAmount()+ customerPortfolioVO.getBlockedCash();
                portfolioDetailsVO.setMarketValue(portfolioDetailsVO.getAssetId() == 10
                        ? new BigDecimal(blockedcash) : portfolioDetailsVO.getMarketValue());
            }
        }
        List<Object> responseList2 = portfolioDetailsDAO.getTotalReurnsforSpecifiedPeriod(customerPortfolioTb,
                dateperiod, eODdate);
        if (!responseList2.isEmpty()) {
            List<Object> responsedata = (List<Object>) responseList2.get(ZERO);
            customerPortfolioVO.setPfTwrReturn(Double.parseDouble(responsedata.get(ZERO).toString()));
            responsedata = (List<Object>) responseList2.get(ONE);
            customerPortfolioVO.setRcmdPfTwrReturn(Double.parseDouble(responsedata.get(ZERO).toString()));
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<PortfolioSecurityVO> getInvestorDashboardData(String omsid, Integer customerId, List<PortfolioSecurityVO> portfolioSecurityVOlist) {
        List<CustomerPortfolioTb> customerPortfolioTbs = new ArrayList<CustomerPortfolioTb>();
        PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
        List response = portfolioDetailsDAO.getcustomerPortfoliotableDetails(customerId);
        List<Object[]> customerportfoliolist = (List<Object[]>) response.get(0);
        Double AcquisitionVal = ZERO_POINT_ZERO;
        Double gainOrLoss = ZERO_POINT_ZERO;
        Float blocked_cash = (float) ZERO;
        for (Object[] objects : customerportfoliolist) {
            portfolioSecurityVO = new PortfolioSecurityVO();
            Float portfolioVal = objects[0] == null ? ZERO : (Float) objects[0];
            portfolioSecurityVO.setPortfolio_currentValue(PortfolioUtil.roundToTwoDecimal(portfolioVal.doubleValue()));
            AcquisitionVal = objects[2] == null ? ZERO_POINT_ZERO : (Double) objects[2];
            blocked_cash = objects[3] == null ? (float) ZERO : (Float) objects[3];
            if (objects[1] == null) {
                Object data = response.get(1);
                Double cash = data == null ? ZERO : (Double) data;
                portfolioSecurityVO.setCash_amount(cash.floatValue());
            } else {
                Float cash_amount = objects[1] == null ? ZERO : (Float) objects[1];
                portfolioSecurityVO.setCash_amount(cash_amount);
                gainOrLoss = portfolioVal - AcquisitionVal - cash_amount;
            }
            
            portfolioSecurityVO.setAcquisitonValue(PortfolioUtil.roundToTwoDecimal(AcquisitionVal));
            portfolioSecurityVO.setTotalGainLoss(PortfolioUtil.roundToTwoDecimal(gainOrLoss));
            Double returnValue = ZERO_POINT_ZERO;
            if (AcquisitionVal != 0) {
                returnValue = ((gainOrLoss / AcquisitionVal) * 100);
            }
            portfolioSecurityVO.setDashbrd_return(PortfolioUtil.roundToTwoDecimal(returnValue));
            portfolioSecurityVO.setBlockedcash(blocked_cash);
            portfolioSecurityVOlist.add(portfolioSecurityVO);
        }

        return portfolioSecurityVOlist;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean checkForContractTermination(Integer customerId) {
        boolean contract_terminated = false;
        Short user_status = portfolioDetailsDAO.checkUserStatus(customerId);
        if(user_status != null){
            short relation_status = user_status.shortValue();
            contract_terminated = relation_status  == 403 ? true : false;
        }
                  
        return contract_terminated;
    }

}
