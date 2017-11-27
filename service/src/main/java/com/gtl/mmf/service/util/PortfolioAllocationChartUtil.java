/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.TEXT_MUTUAL_FUND;
import static com.gtl.mmf.service.util.IConstants.TEXT_INDEX;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import com.gtl.mmf.service.vo.AssetClassVO;
import com.gtl.mmf.service.vo.LineChartDataVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioTypeVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.codehaus.jackson.map.ObjectMapper;

/**
 *
 * @author 07958
 */
public class PortfolioAllocationChartUtil implements IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.service.util.PortfolioAllocationChartUtil");

    private PortfolioAllocationChartUtil() {
    }

    /**
     * This method is used to check whether the asset class in the select portfolio
       style need to allocate security . it is done by checking the allocation of each asset. 
       if it is !=0 return true
     * @param portfolioStyle - Type of portfolio created like aggressive,moderate,conservative etc.
     * @param assetType Asset class selected for checking 
     * @return boolean true if asset class is set for allocation in the selected portfolio style
     */
    public static boolean isAssetForAllocation(PortfolioTypeVO portfolioStyle, String assetType) {
        boolean flag = false;
        int allocationSize = ZERO;
   
        // Checking the assetType for getting the allocation size
        if (assetType.equals(TEXT_DEBT)) {
            allocationSize = portfolioStyle.getDebt();
        } else if (assetType.equals(TEXT_FNO)) {
            allocationSize = portfolioStyle.getFando();
        } else if (assetType.equals(TEXT_INTERNATIONAL)) {
            allocationSize = portfolioStyle.getInternational();
        } else if (assetType.equals(TEXT_GOLD)) {
            allocationSize = portfolioStyle.getGold();
//        } else if (assetType.equals(TEXT_MUTUAL_FUND)) {
//            allocationSize = portfolioStyle.getMutualFund();
        } else if (assetType.equals(TEXT_CASH)) {
            allocationSize = portfolioStyle.getCash();
            return true;
        } else if (assetType.equals(TEXT_INDEX)) {
            allocationSize = portfolioStyle.getEquitiesIndex();
        } else if (assetType.equals(MIDCAP)) {
            allocationSize = portfolioStyle.getMidCap();
        } else if (assetType.equals(EQUITY_DIVERSIFIED)) {
            allocationSize = portfolioStyle.getDiversified_equity();
        } else if (assetType.equals(TEXT_BALANCED)) {
            allocationSize = portfolioStyle.getBalanced();
        } else if (assetType.equals(TEXT_DEBT_LIQUID)) {
            allocationSize = portfolioStyle.getDebt_liquid();
        }else if (assetType.equals(TAX_PLANNING)) {
            allocationSize = portfolioStyle.getTax_planning();
        }
        
        if(portfolioStyle.getMasterPortfolioStyleTb().getPortfolioStyleId().equals(SIX)){
           flag =  true;
        }else{
            flag =  allocationSize != ZERO;
        }
        //allocation  size is ZERO then it is not needed to display for  allocation when creating portfolio
       // return allocationSize != ZERO;
       return flag;
    }

    /**
     * This method is used to generate string representation of data for creating pie chart
     * Color for asset,allocation and asset name are used for creating pie chart data
     * @param portfolioDetailsVOs - asset classes in portfolio
     * @return JSON String
     */
    public static String generatePiechartJSONString(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        String jsonChart = null;
        try {
            List<PieChartDataVO> allocations = new ArrayList<PieChartDataVO>();
            for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
                Integer assetId = portfolioDetailsVO.getAssetId().intValue();
                AssetClassVO assetClassVO = LookupDataLoader.getAssetClassMap().get(assetId);
                PieChartDataVO pieChartDataVO = new PieChartDataVO();
                pieChartDataVO.setColor(assetClassVO.getAssetColor());
                pieChartDataVO.setData(portfolioDetailsVO.getNewAllocation());
                pieChartDataVO.setLabel(assetClassVO.getAssetName());
                allocations.add(pieChartDataVO);
            }
            ObjectMapper objectMapper = new ObjectMapper();
            jsonChart = objectMapper.writeValueAsString(allocations);
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "generatePiechartJSONString() : Pie chart genration failed. Exception {0}", StackTraceWriter.getStackTrace(ex));
        } finally {
            return jsonChart;
        }
    }
    public static String generatePieChartData(List<PieChartDataVO> pieChartDataVOList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(pieChartDataVOList);
        } catch (IOException e) {
             StackTraceWriter.getStackTrace(e);
            return null;
        }
    }

/**
     * This method is used to generate string representation of data for creating line chart
     * @param lineChartDataVOList - line chart data list
     * @return JSON String
     */
    public static String generateLineChartData(List<LineChartDataVO> lineChartDataVOList) {
        List<String[]> lineChartData = new ArrayList<String[]>();
        String[] array;
        for (LineChartDataVO lineChartDataVO : lineChartDataVOList) {
            array = new String[2];
            array[ZERO] = lineChartDataVO.getxValue();
            array[ONE] = lineChartDataVO.getyValue();
            lineChartData.add(array);
            if(lineChartData.isEmpty())
            {
            	return "[]";
            }
        }
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(lineChartData);
        } catch (IOException e) {
             StackTraceWriter.getStackTrace(e);
            return null;
        }
    }
}
