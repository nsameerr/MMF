/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.service.util;

import com.git.mds.common.InvalidData;
import com.git.mds.common.MDSMsg;
import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.git.oms.api.frontend.message.fieldtype.VenueCode;
import com.gtl.mmf.persist.vo.RebalanceStateAssetVO;
import com.gtl.mmf.persist.vo.RebalanceStateSecurityVO;
import com.gtl.mmf.persist.vo.RebalanceStateVO;
import static com.gtl.mmf.service.util.IConstants.ALLOCATEDFUND_TEMP;
import static com.gtl.mmf.service.util.IConstants.BLOCKED_CASH_ID;
import static com.gtl.mmf.service.util.IConstants.CLOSE_PRICE;
import static com.gtl.mmf.service.util.IConstants.CMP_TEXT_COLOR_DEFAULT;
import static com.gtl.mmf.service.util.IConstants.CMP_TEXT_COLOR_DOWN;
import static com.gtl.mmf.service.util.IConstants.CMP_TEXT_COLOR_UP;
import static com.gtl.mmf.service.util.IConstants.DEFAULT_MULTIPLIER_MF;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EQUITY_DIVERSIFIED;
import static com.gtl.mmf.service.util.IConstants.GEN_NUMERIC3;
import static com.gtl.mmf.service.util.IConstants.HUNDRED;
import static com.gtl.mmf.service.util.IConstants.INDEX;
import static com.gtl.mmf.service.util.IConstants.LOCKED_MESSAGE;
import static com.gtl.mmf.service.util.IConstants.LST_TRD_PRICE;
import static com.gtl.mmf.service.util.IConstants.MIDCAP;
import static com.gtl.mmf.service.util.IConstants.MIN_NOT_AVAIL;
import static com.gtl.mmf.service.util.IConstants.MIN_SUB_TEXT;
import static com.gtl.mmf.service.util.IConstants.NSE_MF_SCRIPCODE;
import static com.gtl.mmf.service.util.IConstants.SIX;
import static com.gtl.mmf.service.util.IConstants.TAX_PLANNING;
import static com.gtl.mmf.service.util.IConstants.TEXT_BALANCED;
import static com.gtl.mmf.service.util.IConstants.TEXT_CASH;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT;
import static com.gtl.mmf.service.util.IConstants.TEXT_DEBT_LIQUID;
import static com.gtl.mmf.service.util.IConstants.TEXT_EQUITY;
import static com.gtl.mmf.service.util.IConstants.TEXT_FNO;
import static com.gtl.mmf.service.util.IConstants.TEXT_GOLD;
import static com.gtl.mmf.service.util.IConstants.TEXT_INTERNATIONAL;
import static com.gtl.mmf.service.util.IConstants.TEXT_MUTUAL_FUND;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import static com.gtl.mmf.service.util.IConstants._SEND_SUBJECT;
import com.gtl.mmf.service.vo.AddRedeemFundsVO;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.PortfolioVO;
import com.gtl.mmf.service.vo.SymbolRecordVO;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Random;

/**
 * This is a utility class for performing various tasks related to portfolio
 *
 * @author 07958
 */
public class PortfolioUtil {

    private static final Double MAXIMUM_ALLOCATION = 100.00;
    private static final Logger LOGGER = Logger.getLogger(" com.gtl.mmf.service.util.PortfolioUtil");

    private static Double calculateCurrentAcquisitionValue(final PortfolioDetailsVO portfolioDetailsVO) {
        double acqValue = ZERO_POINT_ZERO;
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                acqValue = acqValue + portfolioSecurityVO.getAcquisitonValue();
                double gainLoss = PortfolioUtil.roundToTwoDecimal(portfolioSecurityVO.getCurrentValue() - portfolioSecurityVO.getAcquisitonValue());
                portfolioSecurityVO.setTotalGainLoss(gainLoss);
                portfolioSecurityVO.setGainOrLoss(gainLoss >= ZERO_POINT_ZERO ? true : false);
            }
        }
        return roundToTwoDecimal(acqValue);
    }

    private static Double calculateTotalAcquisitionValue(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        double acqValue = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            acqValue = acqValue + portfolioDetailsVO.getAcquisitionValue();
        }
        return roundToTwoDecimal(acqValue);
    }

    private static Double calculateTotalgainLoss(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        double gainLoss = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            for (PortfolioSecurityVO securityVO : portfolioDetailsVO.getSecurities()) {
                gainLoss = gainLoss + securityVO.getTotalGainLoss();
            }
        }
        return roundToTwoDecimal(gainLoss);
    }

    private static Double calculateTotalWeight(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        double weightSum = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            weightSum = weightSum + portfolioDetailsVO.getCurrentWeight();
        }
        return roundToTwoDecimal(weightSum);
    }

    private PortfolioUtil() {
    }

    /**
     * This method is used to set current value for each asset class and current
     * weight of total allocated amount used
     *
     * @param customerPortfolioVO - Portfolio assigned to investor
     */
    public static void updateValueAndWeight(CustomerPortfolioVO customerPortfolioVO) {
        List<PortfolioDetailsVO> portfolioDetailsVOs = customerPortfolioVO.getPortfolioDetailsVOs();

        //calculating current value(currentPrice * executed units)
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {

            //Checking asset class is CASH
            if (PortfolioUtil.getAssetClassType(portfolioDetailsVO.getAssetId()).equalsIgnoreCase(TEXT_CASH)) {
                portfolioDetailsVO.setCurrentValue(customerPortfolioVO.getCashAmount().doubleValue());
                portfolioDetailsVO.setAcquisitionValue((float) ZERO_POINT_ZERO.doubleValue());
            } else {

                //Calculating current value for Asset class
                portfolioDetailsVO.setCurrentValue(PortfolioUtil.calculateCurrentValuePFDetails(portfolioDetailsVO));
                portfolioDetailsVO.setAcquisitionValue((float) PortfolioUtil.calculateCurrentAcquisitionValue(portfolioDetailsVO).doubleValue());
            }
        }

        //Setting current portfolio value (current portfolio value = cash balance + total current value)
        customerPortfolioVO.setCurrentValue((float) calculateCurrentValuePortfolio(portfolioDetailsVOs).doubleValue());
        //Setting  total current value
        customerPortfolioVO.setTotalGainLossValue(calculateTotalgainLoss(portfolioDetailsVOs));
        //Setting  total acquisition value
        customerPortfolioVO.setTotalAcquisition(calculateTotalAcquisitionValue(portfolioDetailsVOs));

        // Calculate weights (value/total)
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            //weight = (security_current_value/portfolio value)*100
            //Setting weight of amount used for each security
            PortfolioUtil.assignPFSecurityWeight(portfolioDetailsVO, Double.valueOf(customerPortfolioVO.getCurrentValue()));

            //Setting weight amount used by asset class (asset class_current_value/portfolio value)*100
            portfolioDetailsVO.setCurrentWeight(roundToTwoDecimal((portfolioDetailsVO.getCurrentValue() / customerPortfolioVO.getCurrentValue()) * 100.0));
        }
        //Setting  total asset weight
        customerPortfolioVO.setToatalWeight(calculateTotalWeight(portfolioDetailsVOs));
    }

    /**
     * This method is used to set current value for each asset class and current
     * weight of total allocated amount used
     *
     * @param portfolioVO - Portfolio created by advisor
     */
    public static void updateValueAndWeight(PortfolioVO portfolioVO) {

        // Assumption portfolio value will always default(1000000) here. calculate weight an value and cash balance on that.
        // Calculate values (price * units).
        List<PortfolioDetailsVO> portfolioDetailsVOs = PortfolioUtil.getPortfolioDetails(portfolioVO);
        Double totalValue = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            portfolioDetailsVO.setCurrentValue(PortfolioUtil.calculateCurrentValuePFDetails(portfolioDetailsVO));
            totalValue = totalValue + portfolioDetailsVO.getCurrentValue();
        }

        // Caluculate weights. Above assumption.
        portfolioVO.getCash().setCurrentValue(ALLOCATEDFUND_TEMP - totalValue);
        portfolioVO.setCurrentValue(ALLOCATEDFUND_TEMP);

        /*
         Calculate
         1. Security Weight (security_current_value/portfolio value)*100
         2. Existing Weight (value/total)*100
         */
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            PortfolioUtil.assignPFSecurityWeight(portfolioDetailsVO, portfolioVO.getCurrentValue());
            portfolioDetailsVO.setCurrentWeight(roundToTwoDecimal((portfolioDetailsVO.getCurrentValue() / portfolioVO.getCurrentValue()) * 100.0));
            portfolioDetailsVO.setExistingWeight(roundToTwoDecimal((portfolioDetailsVO.getCurrentValue() / portfolioVO.getCurrentValue()) * 100.0));
        }
    }

    /**
     * This method is used to calculate the number of units that can be brought
     * for the specified allocation Transaction Count calculation Amount
     * allocated for security = (allocation percentage / 100) * total allocation
     * amount transaction count = Amount allocated for security / market price
     *
     * @param allocation - allocation % for the security
     * @param marketPrice - current market price
     * @param totalAmount - advisor allocated amount
     * @return int returns transaction count
     */
    public static double getTransactionCount(final Double allocation, final Double marketPrice, final Double totalAmount) {
        double transactionCount;
        if (marketPrice == null || marketPrice.doubleValue() == ZERO_POINT_ZERO.doubleValue()) {
            transactionCount = ZERO;
        } else {
            Double amountAvailable = (allocation == null || totalAmount == null)
                    ? ZERO_POINT_ZERO : (allocation / MAXIMUM_ALLOCATION) * totalAmount;
            transactionCount = (int) Math.floor(amountAvailable / marketPrice);
        }
        return transactionCount;
    }

    /**
     * This method is used to get securities assigned in the portfolio.
     *
     * @param portfolioDetailsVO - asset class
     * @return list of securities in the portfolio
     */
    public static List<PortfolioSecurityVO> getPortfolioSecurityVOs(PortfolioDetailsVO portfolioDetailsVO) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {
            if (portfolioSecurityVO.getStatus()) {
                portfolioSecurityVOs.add(portfolioSecurityVO);
            }
        }
        return portfolioSecurityVOs;
    }

    /**
     * This method is used to get securities assigned in the portfolio.
     *
     * @param portfolioVO - Portfolio created by advisor
     * @return list of securities in the portfolio
     */
    public static List<PortfolioSecurityVO> getPortfolioSecurityVOs(PortfolioVO portfolioVO) {
        List<PortfolioDetailsVO> portfolioDetails = getPortfolioDetails(portfolioVO);
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetails) {
            portfolioSecurityVOs.addAll(PortfolioUtil.getPortfolioSecurityVOs(portfolioDetailsVO));
        }
        return portfolioSecurityVOs;
    }

    /**
     * This method is used to get all asset classes in the portfolio created by
     * advisor
     *
     * @param portfolioVO Portfolio Details
     * @return list of portfolio asset classes
     */
    public static List<PortfolioDetailsVO> getPortfolioDetails(PortfolioVO portfolioVO) {
        List<PortfolioDetailsVO> portfolioDetailsVOs = new ArrayList<PortfolioDetailsVO>();

        //Idenitifying asset classes that included in this portfolio and adding into the list
        /* if (isEquityForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getEquity());
        }*/
        if (isEquityDiversifiedForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getEquityDiversified());
        }
        if (isIndexForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getIndex());
        }
        if (isMidcapForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getMidcap());
        }
        if (isInternationalForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getInternational());
        }
        if (isGoldForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getGold());
        }
//        if (isMFForAllocation(portfolioVO)) {
//            portfolioDetailsVOs.add(portfolioVO.getMutualfund());
//        }
        if (isDebtForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getDebt());
        }
        if (isFnoForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getFno());
        }
        if (isBalancedForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getBalanced());
        }
        if (isDebtLiquidForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getDebtLiquid());
        }
        if (isTaxPlanningForAllocation(portfolioVO)) {
            portfolioDetailsVOs.add(portfolioVO.getTax_planning());
        }
        portfolioDetailsVOs.add(portfolioVO.getCash());
        return portfolioDetailsVOs;
    }

    public static PortfolioDetailsVO getAssetClassCustomerPortfolio(String assetClass, List<PortfolioDetailsVO> portfolioDetailsVOs) {
        PortfolioDetailsVO portfolioDetailsVO = null;
        Map<String, Short> assets = LookupDataLoader.getAssetClasses();
        for (PortfolioDetailsVO portfolioDetails : portfolioDetailsVOs) {
            if (assets.get(TEXT_CASH) == portfolioDetails.getAssetId()) {
                portfolioDetailsVO = portfolioDetails;
            }
        }
        return portfolioDetailsVO;
    }

    /**
     * Used to return Asset class for a particular asset class ID
     *
     * @param assetId - ID of the specific asset class
     * @param portfolioVO - portfolio selected
     * @return object asset class
     */
    public static PortfolioDetailsVO getAssetclassVO(final Short assetId, final PortfolioVO portfolioVO) {

        //Getting Asset class type using asset class ID
        String assetClassType = PortfolioUtil.getAssetClassType(assetId);
        return PortfolioUtil.getAssetclassVO(assetClassType, portfolioVO);
    }

    /**
     * Used to return asset class for selected asset ID
     *
     * @param assetClass - Asset class looking for
     * @param portfolioVO - portfolio selected
     * @return object asset class
     */
    public static PortfolioDetailsVO getAssetclassVO(final String assetClass, final PortfolioVO portfolioVO) {
        return //assetClass.equals(TEXT_EQUITY) ? portfolioVO.getEquity()
                assetClass.equals(TEXT_DEBT) ? portfolioVO.getDebt()
                : assetClass.equals(TEXT_FNO) ? portfolioVO.getFno()
                : assetClass.equals(TEXT_INTERNATIONAL) ? portfolioVO.getInternational()
                : assetClass.equals(TEXT_GOLD) ? portfolioVO.getGold()
                //                : assetClass.equals(TEXT_MUTUAL_FUND) ? portfolioVO.getMutualfund()
                : assetClass.equals(EQUITY_DIVERSIFIED) ? portfolioVO.getEquityDiversified()
                : assetClass.equals(INDEX) ? portfolioVO.getIndex()
                : assetClass.equals(MIDCAP) ? portfolioVO.getMidcap()
                : assetClass.equals(TEXT_BALANCED) ? portfolioVO.getBalanced()
                : assetClass.equals(TEXT_DEBT_LIQUID) ? portfolioVO.getDebtLiquid()
                : assetClass.equals(TEXT_CASH) ? portfolioVO.getCash()
                : assetClass.equals(TAX_PLANNING) ? portfolioVO.getTax_planning()
                : null;
    }

    /**
     * used to get asset class type for a particular asset ID
     *
     * @param assetId - Asset ID from portfolio
     * @return asset class type corresponding to asset iD
     */
    public static String getAssetClassType(final Short assetId) {

        //reading all  asset class from  LookupDataLoader 
        Map<String, Short> assets = LookupDataLoader.getAssetClasses();
        return (assets.get(TEXT_EQUITY) == assetId) ? TEXT_EQUITY
                : (assets.get(TEXT_DEBT) == assetId) ? TEXT_DEBT
                : (assets.get(TEXT_FNO) == assetId) ? TEXT_FNO
                : (assets.get(TEXT_INTERNATIONAL) == assetId) ? TEXT_INTERNATIONAL
                : (assets.get(TEXT_GOLD) == assetId) ? TEXT_GOLD
                //                : (assets.get(TEXT_MUTUAL_FUND) == assetId) ? TEXT_MUTUAL_FUND
                : (assets.get(TEXT_CASH) == assetId) ? TEXT_CASH
                : (assets.get(EQUITY_DIVERSIFIED) == assetId) ? EQUITY_DIVERSIFIED
                : (assets.get(MIDCAP) == assetId) ? MIDCAP
                : (assets.get(INDEX) == assetId) ? INDEX
                : (assets.get(TEXT_BALANCED) == assetId) ? TEXT_BALANCED
                : (assets.get(TEXT_DEBT_LIQUID) == assetId) ? TEXT_DEBT_LIQUID
                : (assets.get(TAX_PLANNING) == assetId) ? TAX_PLANNING
                : null;
    }

    /**
     * Checking asset class Equity is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Equity is in portfolio for allocation
     */
    /* public static boolean isEquityForAllocation(final PortfolioVO portfolioVO) {
        
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_EQUITY);
    }*/
    /**
     * Checking asset class Equity is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Equity is in portfolio for allocation
     */
    public static boolean isDebtForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_DEBT);
    }

    /**
     * Checking asset class Future and options is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Future and options is in portfolio for allocation
     */
    public static boolean isFnoForAllocation(final PortfolioVO portfolioVO) {
        boolean flag = false;
        if (!portfolioVO.getPortfolioStyleSelectedKey().equals(SIX)) {
            flag = PortfolioAllocationChartUtil.isAssetForAllocation(
                    LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_FNO);
        } else {
            flag = false;
        }
        return flag;
    }

    /**
     * Checking asset class International is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if International is in portfolio for allocation
     */
    public static boolean isInternationalForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_INTERNATIONAL);
    }

    /**
     * Checking asset class Gold is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Gold is in portfolio for allocation
     */
    public static boolean isGoldForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_GOLD);
    }

    /**
     * Checking asset class MF is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if MF is in portfolio for allocation
     */
    /*  public static boolean isMFForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_MUTUAL_FUND);
    }*/
    /**
     * Checking asset class EquityDiversified is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if EquityDiversified is in portfolio for allocation
     */
    public static boolean isEquityDiversifiedForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), EQUITY_DIVERSIFIED);
    }

    /**
     * Checking asset class Index is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Index is in portfolio for allocation
     */
    public static boolean isIndexForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), INDEX);
    }

    /**
     * Checking asset class Midcap is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Midcap is in portfolio for allocation
     */
    public static boolean isMidcapForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), MIDCAP);
    }

    /**
     * Checking asset class Balancd is in portfolio for allocation
     *
     * @param portfolioVO - portfolio created
     * @return boolean true if Balancd is in portfolio for allocation
     */
    public static boolean isBalancedForAllocation(final PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_BALANCED);
    }

    /**
     * Checking asset class Debt - Liquid/Short is in portfolio for allocation
     *
     * @param portfolioVO
     * @return
     */
    private static boolean isDebtLiquidForAllocation(PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(
                LookupDataLoader.getPortfolioTypes().get(portfolioVO.getPortfolioTypeSelectedKey()), TEXT_DEBT_LIQUID);
    }

    /**
     * Checking asset class Tax Planning is in portfolio for allocation
     *
     * @param portfolioVO
     * @return
     */
    private static boolean isTaxPlanningForAllocation(PortfolioVO portfolioVO) {
        return PortfolioAllocationChartUtil.isAssetForAllocation(LookupDataLoader.getPortfolioTypes().
                get(portfolioVO.getPortfolioTypeSelectedKey()), TAX_PLANNING);
    }

    /**
     * This method is used to get securities assigned in the customer portfolio
     * portfolio.
     *
     * @param customerPortfolioVO - Portfolio assigned to the investor by
     * advisor
     * @return list of securities in the portfolio
     */
    public static List<PortfolioSecurityVO> getCustomerPortfolioSecurities(final CustomerPortfolioVO customerPortfolioVO) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = new ArrayList<PortfolioSecurityVO>();
        for (PortfolioDetailsVO portfolioDetailsVO : customerPortfolioVO.getPortfolioDetailsVOs()) {
            portfolioSecurityVOs.addAll(portfolioDetailsVO.getSecurities());
        }
        return portfolioSecurityVOs;
    }

    /**
     * This method is used to update market price for each security in the
     * portfolio
     *
     * @param portfolioVO - Portfolio created by advisor
     */
    public static void updatePortfolioMarketPrice(PortfolioVO portfolioVO) {
        // Getting securities in the portfolio
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        Map<String, Integer> securityMap = new HashMap<String, Integer>();
        // Create MDS Request.
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            if (portfolioSecurityVO.getStatus()) {
                if (portfolioSecurityVO.getVenueCode() != null) {

                    //Getting venue code from venue name
                    String venue = BenchmarkUtil.getVenueCode(portfolioSecurityVO.getVenueCode());

                    // create scrip code for each security for sending request to MDS
                    String scripCode = MarketDataUtil.getScripCode(venue, portfolioSecurityVO.getVenueScriptCode());
                    Integer securityIndex = portfolioSecurityVOs.indexOf(portfolioSecurityVO);
                    securityMap.put(scripCode, securityIndex);
                }
            }
        }

        //Assigning Current market price to securities
        PortfolioUtil.assignSecuritiesCMP(securityMap, portfolioSecurityVOs);
    }

    /**
     * This method is used to update market price for each security in the
     * portfolio assigned to investor
     *
     * @param customerPortfolioVO - Portfolio assigned to the customer
     */
    public static void updateCustomerPortfolioMarketPrice(CustomerPortfolioVO customerPortfolioVO) {

        // Getting securities in the investor portfolio
        List<PortfolioSecurityVO> customerPortfolioSecurities = PortfolioUtil.getCustomerPortfolioSecurities(customerPortfolioVO);
        Map<String, Integer> securityMap = new HashMap<String, Integer>();
        for (PortfolioSecurityVO portfolioSecurityVO : customerPortfolioSecurities) {

            //checking for security removed from portfolio by advisor
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {

                //Getting venue code from venue name
                String venue = BenchmarkUtil.getVenueCode(portfolioSecurityVO.getVenueCode());

                // create scrip code for each security for sending request to MDS
                String scripCode = MarketDataUtil.getScripCode(venue, portfolioSecurityVO.getVenueScriptCode());
                Integer securityIndex = customerPortfolioSecurities.indexOf(portfolioSecurityVO);
                securityMap.put(scripCode, securityIndex);
            }
        }

        //Assigning Current market price to securities
        PortfolioUtil.assignSecuritiesCMP(securityMap, customerPortfolioSecurities);
    }

    /**
     * updating transaction details
     *
     * @param newAllocatedFund - portfolio value
     * @param customerPortfolioVO - Investor portfolio
     */
    public static void updateTransactionDetails(CustomerPortfolioVO customerPortfolioVO) {
        List<PortfolioSecurityVO> customerPortfolioSecurities = PortfolioUtil.getCustomerPortfolioSecurities(customerPortfolioVO);
        Double totalTransactionAmount = ZERO_POINT_ZERO;
        Double totalTransactionCount = ZERO_POINT_ZERO;

        Set<String> venueNameSet = new HashSet<String>();
        for (PortfolioSecurityVO portfolioSecurityVO : customerPortfolioSecurities) {

            //Checking for security removed by advisor
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                if (portfolioSecurityVO.getStatus()) {
//                    Double allocationForSecurity = Math.floor(customerPortfolioVO.getCurrentValue() * (portfolioSecurityVO.getNewAllocation() / HUNDRED));
                    Double allocationForSecurity = roundToTwoDecimal(customerPortfolioVO.getCurrentValue() * (portfolioSecurityVO.getNewAllocation() / HUNDRED));

                    if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                        totalTransactionCount = portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) == ZERO ? ZERO : roundToTwoDecimal((allocationForSecurity / portfolioSecurityVO.getCurrentPrice()));
                    } else {
                        totalTransactionCount = portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) == ZERO ? ZERO : Math.floor(allocationForSecurity / portfolioSecurityVO.getCurrentPrice());
                    }

                    //int transactionUnits = totalTransactionCount == ZERO ? ZERO : portfolioSecurityVO.getExecutedUnits() - totalTransactionCount;
                    Double transactionUnits = totalTransactionCount == ZERO ? ZERO : portfolioSecurityVO.getExecutedUnits() - totalTransactionCount;
                    portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * transactionUnits);
                    portfolioSecurityVO.setTransactAmount(portfolioSecurityVO.getCurrentPrice() * Math.abs(transactionUnits));
                    if (portfolioSecurityVO.getStatus() && transactionUnits < ZERO
                            && (portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)) {
                        // To Buy
                        if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                            //check for NSEMF Buy
                            checkForMfOrderBuy(portfolioSecurityVO, transactionUnits, allocationForSecurity);
                        } else {
                            portfolioSecurityVO.setBuysellStatusValue(BuySell.BUY.value());
                            portfolioSecurityVO
                                    .setBuysellStatusText(BuySell.BUY.toString());
                            portfolioSecurityVO.setTransactUnits(roundToTwoDecimal(Math.abs(transactionUnits)));
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_UP);
                        }

                    } else if (portfolioSecurityVO.getStatus() && transactionUnits > ZERO
                            && (portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)) {
                        // To sell
                        portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                        portfolioSecurityVO.setBuysellStatusText(BuySell.SELL
                                .toString());
                        portfolioSecurityVO.setTransactUnits(transactionUnits);
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                        //For Security under ELSS
                            if (portfolioSecurityVO.getAssetClassId() == BLOCKED_CASH_ID) {
                                portfolioSecurityVO.setBuysellStatusValue(ZERO);
                                portfolioSecurityVO.setBuysellStatusText(LOCKED_MESSAGE);
                                portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                                portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);

                            }
                    } else {
                        portfolioSecurityVO.setBuysellStatusValue(ZERO);
                        portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                        portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                    }
                } else if (!portfolioSecurityVO.getStatus()) {
                    //To sell security removed from recommended portfolio. 
                    portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());
                    portfolioSecurityVO.setTransactAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());
                    if ((portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)) {
                        portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                        portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                        portfolioSecurityVO.setTransactUnits(portfolioSecurityVO.getExecutedUnits().doubleValue());
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                    } else {
                        portfolioSecurityVO.setBuysellStatusValue(ZERO);
                        portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                        portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                    }
                }

                // To display positive value in add or redeem fund page
                portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getTransactUnits());
//                totalTransactionAmount += portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getTransactUnits();
                totalTransactionAmount += portfolioSecurityVO.getTransactAmount();
                venueNameSet.add(portfolioSecurityVO.getVenueCode());
            }

        }
        customerPortfolioVO.setVenuName(venueNameSet);
        customerPortfolioVO.setTotalTransAmt(totalTransactionAmount);
//        return totalTransactionAmount;
    }

    /**
     *
     * @param oldPrice Old price for security
     * @param currentPrice updated price
     * @return String which represents test color old price is empty color is
     * grey old price > updated price red old price < updated price green
     */
    private static String getCMPTextColor(Double oldPrice, Double currentPrice) {
        Double currentPriceRounded = PortfolioUtil.roundToTwoDecimal(currentPrice);
        if (currentPriceRounded == null) {
            currentPriceRounded = ZERO_POINT_ZERO;
        }
        if (oldPrice == null) {
            oldPrice = currentPriceRounded;
        }
        return oldPrice.doubleValue() == currentPriceRounded.doubleValue() ? CMP_TEXT_COLOR_DEFAULT
                : oldPrice > currentPriceRounded ? CMP_TEXT_COLOR_DOWN
                        : CMP_TEXT_COLOR_UP;
    }

    /**
     * This method is used to calculate current weight of amount used by all
     * securities in the portfolio weight = (security_current)value/portfolio
     * value)*100
     *
     * @param portfolioDetailsVO - list of asset classes
     * @param portfolioValue - current portfolio value
     */
    private static void assignPFSecurityWeight(PortfolioDetailsVO portfolioDetailsVO, Double portfolioValue) {
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                portfolioSecurityVO.setCurrentWeight(roundToTwoDecimal((portfolioSecurityVO.getCurrentValue() / portfolioValue) * 100.0));
            }
        }
    }

    /**
     * This method is used to calculate the portfolio value
     *
     * @param portfolioDetailsVOs - list of asset class
     * @return Double - portfolio value sum of all asset class current_value
     */
    private static Double calculateCurrentValuePortfolio(List<PortfolioDetailsVO> portfolioDetailsVOs) {
        double currentValue = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            currentValue = currentValue + portfolioDetailsVO.getCurrentValue();
        }
        return roundToTwoDecimal(currentValue);
    }

    /**
     * This method is calculating current value for the asset class.
     * current_value =sum of all security in asset class (current_price *
     * executed_units)
     *
     * @param portfolioDetailsVO - Selected asset class
     * @return Double total value of asset class
     */
    private static Double calculateCurrentValuePFDetails(final PortfolioDetailsVO portfolioDetailsVO) {
        double currentValue = ZERO_POINT_ZERO;
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {

            //checking status of security to avoid security removed by advisor
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                portfolioSecurityVO.setCurrentValue(portfolioSecurityVO.getCurrentPrice() == null ? ZERO : portfolioSecurityVO.getCurrentPrice()
                        * (portfolioSecurityVO.getExecutedUnits() == null ? ZERO : portfolioSecurityVO.getExecutedUnits()));
                currentValue = currentValue + portfolioSecurityVO.getCurrentValue();
                portfolioSecurityVO.setAcquisitonValue(portfolioSecurityVO.getBoughtPrice() == null ? ZERO : portfolioSecurityVO.getBoughtPrice()
                        * (portfolioSecurityVO.getExecutedUnits() == null ? ZERO : portfolioSecurityVO.getExecutedUnits()));
            }
        }
        return roundToTwoDecimal(currentValue);
    }

    /**
     * This method assign current market price to each securities.
     *
     * @param securityMap - this map contain scrip codes for each security for
     * creating MDS request
     * @param portfolioSecurityVOs - list of securities in the portfolio
     * @throws InvalidData
     */
    private static void assignSecuritiesCMP(Map<String, Integer> securityMap, List<PortfolioSecurityVO> portfolioSecurityVOs) throws InvalidData {
        List<String> mdsRequest = new ArrayList<String>(securityMap.keySet());

        // Send MDS Request
        List<MDSMsg> mdsResponse = MarketDataUtil.getMDSResponse(mdsRequest);
        // Map MDS Response - Setting current price
        if (mdsResponse != null && mdsResponse.size() != 0) {
            for (MDSMsg mdsMsg : mdsResponse) {
                String scripCode = mdsMsg.getField(_SEND_SUBJECT).stringValue();
                Double marketPrice = ZERO_POINT_ZERO;
                //selecting portfolio secuirty based on scripcode
                PortfolioSecurityVO portfolioSecurityVO = portfolioSecurityVOs.get(securityMap.get(scripCode));
//                System.out.println("MDS message" + mdsMsg.toString());
                //for NSEMFz
                if (scripCode.startsWith(NSE_MF_SCRIPCODE)) {
                    marketPrice = mdsMsg.getField(GEN_NUMERIC3) != null ? mdsMsg.getField(GEN_NUMERIC3).doubleValue() : ZERO_POINT_ZERO;
                    if (portfolioSecurityVO.getAssetClass() != null) {
                        SymbolRecordVO selectedSymbol = SymbolListUtil.getSymbolRecordVO(portfolioSecurityVO.getAssetClass(), portfolioSecurityVO.getSecurityId());
                        portfolioSecurityVO.setSymbolRecordVO(selectedSymbol);
                        marketPrice = 120.02;
                    }
                } else {
                    marketPrice = mdsMsg.getField(LST_TRD_PRICE) != null ? mdsMsg.getField(LST_TRD_PRICE).doubleValue()
                            : mdsMsg.getField(CLOSE_PRICE) != null ? mdsMsg.getField(CLOSE_PRICE).doubleValue() : ZERO_POINT_ZERO;
                }
                // Change text color in ui
                portfolioSecurityVO.setCmpTextColor(PortfolioUtil.getCMPTextColor(portfolioSecurityVO.getCurrentPrice(), marketPrice));
                // Update market price     
                portfolioSecurityVO.setCurrentPrice(marketPrice == ZERO_POINT_ZERO.doubleValue() ? portfolioSecurityVO.getCurrentPrice() : marketPrice);
                System.out.println("price is set for - " + scripCode + " - to  -> " + portfolioSecurityVO.getCurrentPrice() + " market price recieved -> " + marketPrice);
            }
        }
        //***********suplying market data if MDS is down(for testing purpose)*********************
//        else {
//            LOGGER.log(Level.SEVERE, "MDS RESPONSE IS NULL." );
//            LOGGER.log(Level.SEVERE, "SUPPLYING MARKET VALUE PROGRAMATICALLY" );
//            Random t = new Random();
//            Double marketPrice = ZERO_POINT_ZERO;
//            for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
//                if (t.nextInt(20) > 10) {
//                    marketPrice = portfolioSecurityVO.getCurrentPrice()== null ? 100.45 :portfolioSecurityVO.getCurrentPrice() - 3;
//                } else {
//                    marketPrice = portfolioSecurityVO.getCurrentPrice() == null ? 1200.45 : portfolioSecurityVO.getCurrentPrice() + t.nextInt(20);
//                }
//                portfolioSecurityVO.setCmpTextColor(PortfolioUtil.getCMPTextColor(portfolioSecurityVO.getCurrentPrice(), marketPrice));
//                portfolioSecurityVO.setCurrentPrice(marketPrice);
//            }
//
//        }
        // ***************end of test********************************

    }

    public static Double roundToTwoDecimal(Double value) {
        Double roundedValue;
        if (value == null) {
            roundedValue = null;
        } else {
            roundedValue = BigDecimal.valueOf(value).setScale(2, RoundingMode.FLOOR).doubleValue();
        }
        return roundedValue;
    }

    /**
     * This method is used to check price for security is in specified range.
     *
     * @param portfolioSecurityVO - Security selected for checking
     * @return boolean true if price is in range
     */
    public static boolean isPortfolioSecurityPriceInRange(PortfolioSecurityVO portfolioSecurityVO) {
        Double price = portfolioSecurityVO.getInitialPrice();
        if (price == null) {
            return true;
        }
        //Avoiding securities with price 0 for rebalancing
        Double currentPrice = portfolioSecurityVO.getCurrentPrice() == null ? ZERO : portfolioSecurityVO.getCurrentPrice();

        /*
         checking price is in range
         calculate the maximum Range and minimum Range for price and check price is in this range
         maximum Range = current price + (current price * Tolerance Positive Range/ 100.00)
         minimum Range = current price- (current price * Tolerance Negative Range/ 100.00);
         */
        if (price != ZERO && currentPrice != ZERO) {
            double maximumRange = price + (price * portfolioSecurityVO.getInitialTolerancePositiveRange() / 100.00);
            double minimumRange = price - (price * portfolioSecurityVO.getInitialTolerancePositiveRange() / 100.00);
            LOGGER.log(Level.INFO, "Price tolerance test ,minRange[{1}] , cmp [{2}], maxRange [{0}] , recomdedprice is [{4}] for security -{3}",
                    new Object[]{maximumRange, minimumRange, currentPrice, portfolioSecurityVO.getScripDecription(), price});
            return currentPrice < maximumRange && currentPrice > minimumRange;
        }
        return true;
    }

    /**
     * This method is used to check current asset class weight is in the
     * specified range
     *
     * @param portfolioDetailsVO - asset class selected for checking
     * @return boolean true if it is in range
     */
    public static boolean isPortfolioDetailWeightInRange(PortfolioDetailsVO portfolioDetailsVO) {
        return portfolioDetailsVO.getCurrentWeight() <= portfolioDetailsVO.getMaximumRange()
                && portfolioDetailsVO.getCurrentWeight() >= portfolioDetailsVO.getMinimumRange();
    }

    public static void updatePortfolioMarketData(PortfolioVO portfolioVO) {
        PortfolioUtil.updatePortfolioMarketPrice(portfolioVO);
        PortfolioUtil.updateValueAndWeightForAdvisor(portfolioVO, false);
    }

    public static void updatePortfolioMarketData(CustomerPortfolioVO customerPortfolioVO) {
        PortfolioUtil.updateCustomerPortfolioMarketPrice(customerPortfolioVO);
        PortfolioUtil.updateValueAndWeight(customerPortfolioVO);
    }

    /**
     * This method is used to identify whether a given security require re
     * balancing this is done by checking current price for security is in
     * specified range
     *
     * @param portfolioSecurityVO Security selected for checking rebalance
     * @param portfolioSize
     * @return object rebalanceStateSecurityVO which contain whether security
     * require rebalance
     */
    public static RebalanceStateSecurityVO getRebalanceStateSecurityVO(PortfolioSecurityVO portfolioSecurityVO, Integer portfolioSize) {
        RebalanceStateSecurityVO rebalanceStateSecurityVO = new RebalanceStateSecurityVO();
        rebalanceStateSecurityVO.setSecurityId(portfolioSecurityVO.getPortfolioSecurityId());
        rebalanceStateSecurityVO.setAssetId(portfolioSecurityVO.getAssetClassId());
        rebalanceStateSecurityVO.setCurrentPrice(portfolioSecurityVO.getCurrentPrice());
        rebalanceStateSecurityVO.setCurrentValue(portfolioSecurityVO.getCurrentValue());
        rebalanceStateSecurityVO.setCurrentWeight(portfolioSecurityVO.getCurrentWeight());
        rebalanceStateSecurityVO.setYesterDayUnitCount(portfolioSecurityVO.getExecutedUnits());

        //tradability check
        if (portfolioSize != null) {
            LOGGER.info("Tradability test for security");
            boolean status = PortfolioUtil.isPortfolioSecurityTradable(portfolioSecurityVO, portfolioSize);
            if (!status) {
                rebalanceStateSecurityVO.setRebalanceRequired(true);
                rebalanceStateSecurityVO.setRebalanceRequiredDate(new Date());
            }
            LOGGER.log(Level.INFO, "trade signal for security [{0}] is [{1}] and rebalance status is [{2}]",
                    new Object[]{rebalanceStateSecurityVO.getSecurityId(), portfolioSecurityVO.getTradeSignal(), !status});
        }

        /*
         calculate the maximum Range and minimum Range for price and check price is in this range
         maximum Range = current price + (current price * Tolerance Positive Range/ 100.00)
         minimum Range = current price- (current price * Tolerance Negative Range/ 100.00)
         current price is not between maximum Range and minimum Range then rebalance required
         */
        LOGGER.info("Checking price tolerance test for rebalancing security");
        if (!PortfolioUtil.isPortfolioSecurityPriceInRange(portfolioSecurityVO)) {
            rebalanceStateSecurityVO.setRebalanceRequired(true);
            rebalanceStateSecurityVO.setRebalanceRequiredDate(new Date());
        }

        if (!rebalanceStateSecurityVO.isRebalanceRequired()) {
            rebalanceStateSecurityVO.setRebalanceRequiredDate(null);
        }
        return rebalanceStateSecurityVO;
    }

    /**
     * This method is used to identify whether a given asset class require re
     * balancing this is done by checking current weight for security is in
     * specified range
     *
     * @param portfolioDetailsVO asset selected for checking rebalance
     * @return object rebalanceStateAssetVO which contain whether asset class
     * require rebalance
     */
    public static RebalanceStateAssetVO getRebalanceStateAssetVO(PortfolioDetailsVO portfolioDetailsVO) {
        RebalanceStateAssetVO rebalanceStateAssetVO = new RebalanceStateAssetVO();
        rebalanceStateAssetVO.setAssetId(portfolioDetailsVO.getAssetId());
        rebalanceStateAssetVO.setCurrentValue(portfolioDetailsVO.getCurrentValue());
        rebalanceStateAssetVO.setCurrentWeight(portfolioDetailsVO.getCurrentWeight());

        /*
         Checking asset class  current weight is in range.It should be betweeen 
         maximum Range and minimum Range of weiight otherwise rebalance required
        
         For asset class CASH rebalanceing is not calculated
         */
        LOGGER.info("Checking asset weight test for rebalancing asset class");
        boolean status = PortfolioUtil.isPortfolioDetailWeightInRange(portfolioDetailsVO);
        if (!status
                && !PortfolioUtil.getAssetClassType(portfolioDetailsVO.getAssetId()).equalsIgnoreCase(TEXT_CASH)) {
            rebalanceStateAssetVO.setRebalanceRequired(true);
            rebalanceStateAssetVO.setRebalanceRequiredDate(new Date());
        } else {
            rebalanceStateAssetVO.setRebalanceRequired(false);
            rebalanceStateAssetVO.setRebalanceRequiredDate(null);
        }
        LOGGER.log(Level.INFO, "Asset minRange [{2}], current weight [{0}] ,maxRange [{1}]  for asset [{3}] and rebalance status is [{4}]",
                new Object[]{portfolioDetailsVO.getCurrentWeight(), portfolioDetailsVO.getMaximumRange(),
                    portfolioDetailsVO.getMinimumRange(), portfolioDetailsVO.getAssetId(), !status});
        return rebalanceStateAssetVO;
    }

    /**
     * Checking the given portfolio created by advisor required re-balance
     *
     * @param portfolioVO portfolio selected
     * @param rebalanceStateSecurityVOs object holding re-balance information
     * about security
     * @param rebalanceStateAssetVOs object holding re-balance information about
     * asset classes
     * @return boolean true if re-balance required
     */
    public static boolean isPortfolioRebalanceRequired(PortfolioVO portfolioVO, List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs,
            List<RebalanceStateAssetVO> rebalanceStateAssetVOs) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);

        /*
         #identify whether a given security require rebalancing this is done by
         checking current price for security is in specified range
         #Calculate the maximum Range and minimum Range for price and check price is in this range
         maximum Range = current price + (current price * Tolerance Positive Range/ 100.00)
         minimum Range = current price- (current price * Tolerance Negative Range/ 100.00)
         current price is not between maximum Range and minimum Range then rebalance required
         */
        boolean securitiesRebalanceRequired = PortfolioUtil.getPortfolioSecuritiesRebalanceState(rebalanceStateSecurityVOs, portfolioSecurityVOs,
                portfolioVO.getPortfolioSizeSelectedKey());
        List<PortfolioDetailsVO> portfolioDetails = PortfolioUtil.getPortfolioDetails(portfolioVO);

        /*
         Checking asset class  current weight is in range.It should be betweeen 
         maximum Range and minimum Range of weiight otherwise rebalance required
        
         For asset class CASH rebalanceing is not calculated
         */
        boolean detailsRebalanceRequired = PortfolioUtil.getPortfolioDetailsRebalanceState(rebalanceStateAssetVOs, portfolioDetails);

        //Either Secuirty or Asset class require rebalancing returns true
        return securitiesRebalanceRequired || detailsRebalanceRequired;
    }

    /**
     * Checking the given portfolio assigned to investor required re-balance
     *
     * @param customerPortfolioVO portfolio assigned to investor
     * @param rebalanceStateSecurityVOs object holding re-balance information
     * about security
     * @param rebalanceStateAssetVOs object holding re-balance information about
     * asset classes
     * @return boolean true if re-balance required
     */
    public static boolean isPortfolioRebalanceRequired(CustomerPortfolioVO customerPortfolioVO, List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs,
            List<RebalanceStateAssetVO> rebalanceStateAssetVOs) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getCustomerPortfolioSecurities(customerPortfolioVO);

        /*
         #identify whether a given security require rebalancing this is done by
         checking current price for security is in specified range
         #Calculate the maximum Range and minimum Range for price and check price is in this range
         maximum Range = current price + (current price * Tolerance Positive Range/ 100.00)
         minimum Range = current price- (current price * Tolerance Negative Range/ 100.00)
         current price is not between maximum Range and minimum Range then rebalance required
         */
        boolean securitiesRebalanceRequired = PortfolioUtil.getPortfolioSecuritiesRebalanceState(rebalanceStateSecurityVOs, portfolioSecurityVOs, null);
        List<PortfolioDetailsVO> portfolioDetails = customerPortfolioVO.getPortfolioDetailsVOs();

        /*
         Checking asset class  current weight is in range.It should be betweeen 
         maximum Range and minimum Range of weiight otherwise rebalance required
        
         For asset class CASH rebalanceing is not calculated
         */
        boolean detailsRebalanceRequired = PortfolioUtil.getPortfolioDetailsRebalanceState(rebalanceStateAssetVOs, portfolioDetails);

        //Either Secuirty or Asset class require rebalancing returns true
        return securitiesRebalanceRequired || detailsRebalanceRequired;
    }

    /**
     * Setting rebalance information
     *
     * @param rebalanceStateVO
     * @param rebalanceRequired boolean specifying rebalance required or not
     */
    public static void setPortfolioRebalanceState(RebalanceStateVO rebalanceStateVO, boolean rebalanceRequired) {
        if (rebalanceRequired) {
            rebalanceStateVO.setRebalanceRequired(true);
            rebalanceStateVO.setRebalanceRequiredDate(new Date());
            rebalanceStateVO.setRebalanceViwed(false);
        } else {
            rebalanceStateVO.setRebalanceRequired(false);
            rebalanceStateVO.setRebalanceRequiredDate(null);
            rebalanceStateVO.setRebalanceViwed(true);

        }
    }

    /**
     * This method is used to check whether portfolio security require
     * rebalancing
     *
     * @param rebalanceStateSecurityVOs list contains rebalancing information of
     * all securities
     * @param portfolioSecurityVOs - list contains securities in the portfolio
     * @return boolean true if rebalance required
     */
    private static boolean getPortfolioSecuritiesRebalanceState(List<RebalanceStateSecurityVO> rebalanceStateSecurityVOs, List<PortfolioSecurityVO> portfolioSecurityVOs,
            Integer portfolioSize) {
        boolean portfolioSecurityRebalanceRequired = false;
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {

            if (portfolioSecurityVO != null && portfolioSecurityVO.getAssetClassId() != null) {
                //Checking security needs rebalancing
                LOGGER.log(Level.INFO, "Recommended portfolio security rebalance test for: {0} - Asset Id {1} - SecurityId  {2}- portfolio id",
                        new Object[]{portfolioSecurityVO.getAssetClassId(), portfolioSecurityVO.getSecurityId(),
                            portfolioSecurityVO.getPortfolioId()});
                RebalanceStateSecurityVO rebalanceStateSecurityVO = PortfolioUtil.getRebalanceStateSecurityVO(portfolioSecurityVO, portfolioSize);
                if (rebalanceStateSecurityVO.isRebalanceRequired()) {
                    portfolioSecurityRebalanceRequired = true;
                    LOGGER.log(Level.INFO, "Recommended portfolio security rebalance : {0} - Asset Id {1} - SecurityId {2}-- currentprice {3}- portfolio id",
                            new Object[]{rebalanceStateSecurityVO.getAssetId(), rebalanceStateSecurityVO.getSecurityId(),
                                rebalanceStateSecurityVO.getCurrentPrice(), portfolioSecurityVO.getPortfolioId()});
                }
                rebalanceStateSecurityVOs.add(rebalanceStateSecurityVO);
            }

        }
        return portfolioSecurityRebalanceRequired;
    }

    /**
     * This method is used to check whether portfolio asset classes require
     * rebalancing
     *
     * @param rebalanceStateAssetVOs list contains rebalancing information of
     * all asset classes
     * @param portfolioDetails - list contains Asset classes in the portfolio
     * @return boolean true if rebalance required
     */
    private static boolean getPortfolioDetailsRebalanceState(List<RebalanceStateAssetVO> rebalanceStateAssetVOs, List<PortfolioDetailsVO> portfolioDetails) {
        boolean portfolioDetailsRebalanceRequired = false;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetails) {
            if (portfolioDetailsVO != null && portfolioDetailsVO.getAssetId() != null) {
                LOGGER.log(Level.INFO, "portfolio Deatails rebalance test  for: {0} - Asset Id {1} - portfolio Details id",
                        new Object[]{portfolioDetailsVO.getAssetId(), portfolioDetailsVO.getPortfolioDetailsId()});

                //Checking Asset class needs rebalancing
                RebalanceStateAssetVO rebalanceStateAssetVO = PortfolioUtil.getRebalanceStateAssetVO(portfolioDetailsVO);
                if (rebalanceStateAssetVO.isRebalanceRequired()) {
                    portfolioDetailsRebalanceRequired = true;
                    LOGGER.log(Level.INFO, "Recommended portfolio Details rebalance : {0} - Asset Id {1} - current weight "
                            + "{2}-- portfolio details id",
                            new Object[]{rebalanceStateAssetVO.getAssetId(),
                                rebalanceStateAssetVO.getCurrentWeight(), portfolioDetailsVO.getPortfolioDetailsId()});
                }
                rebalanceStateAssetVOs.add(rebalanceStateAssetVO);
            }

        }
        return portfolioDetailsRebalanceRequired;
    }

    /**
     * updating transaction details after adding or reducing fund
     *
     * @param addRedeemFundsVO - add/redeem fund details
     */
    public static void updateTransactionDetails(AddRedeemFundsVO addRedeemFundsVO) {

        //Getting customer portfolio securities
        List<PortfolioSecurityVO> customerPortfolioSecurities = PortfolioUtil.getCustomerPortfolioSecurities(addRedeemFundsVO);
        Double totalTransactionAmount = ZERO_POINT_ZERO;
        Double cashAmount = addRedeemFundsVO.getCashBalanceAmount();
        Set<String> venueNameSet = new HashSet<String>();
        for (PortfolioSecurityVO portfolioSecurityVO : customerPortfolioSecurities) {

            //checking for security removed by advisor
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() > ZERO) {

                if (portfolioSecurityVO.getStatus()) {
                    // Calculating allocation from the amount added/reduced with current weight inseted or recomended weight 
//                    Double allocationForSecurity = Math.floor(addRedeemFundsVO.getAddRedeemFund() * (portfolioSecurityVO.getCurrentWeight() / HUNDRED));

                    // Calculating allocation from the amount redeemed with current allocation
                    //previuosly we are using current weight,but on using this total transaction amount falls higher than the redeemed fund.
                    Double allocationForSecurity = Math.floor(addRedeemFundsVO.getAddRedeemFund() * (portfolioSecurityVO.getNewAllocation() / HUNDRED));

                    // Transaction Count = allocationForSecurity / CurrentPrice
                    Double totalTransactionCount = portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) == ZERO ? ZERO : Math.floor(allocationForSecurity / portfolioSecurityVO.getCurrentPrice());
                    Double transactionUnits = totalTransactionCount == ZERO ? ZERO : totalTransactionCount;

                    portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * transactionUnits);

                    if (portfolioSecurityVO.getStatus() && addRedeemFundsVO.getAddFund() && transactionUnits != ZERO) {
                        // To Buy
                        portfolioSecurityVO.setNewUnits(transactionUnits);
                        portfolioSecurityVO.setBuysellStatusValue(BuySell.BUY.value());
                        portfolioSecurityVO
                                .setBuysellStatusText(BuySell.BUY.toString());
                        portfolioSecurityVO.setTransactUnits(transactionUnits);
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_UP);

                        //In case of BUY transaction amount is reducing from total cash balance
                        cashAmount = cashAmount - portfolioSecurityVO.getTransactionAmount();
                    } else if (portfolioSecurityVO.getStatus() && !addRedeemFundsVO.getAddFund() && transactionUnits != ZERO
                            && portfolioSecurityVO.getExecutedUnits() > ZERO_POINT_ZERO) {
                        // To sell
                        // transactionUnits is greater than holdings
                        if (portfolioSecurityVO.getExecutedUnits() >= transactionUnits) {
                            portfolioSecurityVO.setNewUnits(transactionUnits);
                            portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                            portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                            portfolioSecurityVO.setTransactUnits(transactionUnits);
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                            cashAmount = cashAmount + portfolioSecurityVO.getTransactionAmount();
                        } else {
                            portfolioSecurityVO.setNewUnits(portfolioSecurityVO.getExecutedUnits());
                            portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                            portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                            portfolioSecurityVO.setTransactUnits(portfolioSecurityVO.getExecutedUnits());
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                            portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getExecutedUnits() * portfolioSecurityVO.getCurrentPrice());
                            cashAmount = cashAmount + portfolioSecurityVO.getTransactionAmount();
                        }

                    } else {
                        portfolioSecurityVO.setBuysellStatusValue(ZERO);
                        portfolioSecurityVO.setNewUnits(ZERO_POINT_ZERO);
                        portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                        portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                        portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                    }
                } else if (!portfolioSecurityVO.getStatus()) {

                    //to sell security removed from portfolio 
                    portfolioSecurityVO.setNewUnits(ZERO_POINT_ZERO);
                    portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                    portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                    portfolioSecurityVO.setTransactUnits(portfolioSecurityVO.getExecutedUnits().doubleValue());
                    portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                    portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());
                    cashAmount = cashAmount + portfolioSecurityVO.getTransactionAmount();
                }

                // To display positive value in add or redeem fund page
                portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getTransactUnits());
                totalTransactionAmount += portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getTransactUnits();
                venueNameSet.add(portfolioSecurityVO.getVenueCode());
            }
        }
        addRedeemFundsVO.setVenuName(venueNameSet);
        addRedeemFundsVO.setTotalTransAmt(totalTransactionAmount);
        addRedeemFundsVO.setCashBalanceAmount(cashAmount);
//        return totalTransactionAmount;
    }

    private static void checkForMfOrderBuy(PortfolioSecurityVO portfolioSecurityVO, double transactionUnits, double allocationForSecurity) {
        Double minimumSubscription = ZERO_POINT_ZERO;
        /**
         * if holdings greater than zero => ADDITIONAL BUY if holdings equals
         * zero => FRESH BUY
         */
        minimumSubscription = (portfolioSecurityVO.getExecutedUnits() > ZERO_POINT_ZERO ? portfolioSecurityVO.getSymbolRecordVO().getMinSubscraddl()
                : portfolioSecurityVO.getSymbolRecordVO().getMinSubScrFresh());
        // minimumSubscription = 1000.00; For Testing when there s no feed
        /**
         * check for BUY TransactAmount (Total buy amount) should be greater
         * than or equal to minimumSubscription
         */
        Double transAmt = Math.floor(portfolioSecurityVO.getTransactAmount());
       // Double transAmt = (portfolioSecurityVO.getExecutedUnits() > ZERO_POINT_ZERO ? portfolioSecurityVO.getTransactAmount()
       //         : allocationForSecurity);
        transAmt = Math.floor(transAmt);
        if (minimumSubscription != null) {
            if (transAmt >= minimumSubscription) {
                portfolioSecurityVO.setBuysellStatusValue(BuySell.BUY.value());
                portfolioSecurityVO.setBuysellStatusText(BuySell.BUY.toString());
                portfolioSecurityVO.setTransactUnits(Math.abs(transactionUnits));
                portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_UP);
                portfolioSecurityVO.setTransactAmount(transAmt);
                // If transactin Amount is less than DEFAULT_MULTIPLIER_MF(500) then amount kept same 
                //otherwise amount set to a muliplier of DEFAULT_MULTIPLIER_MF
                if (transAmt < DEFAULT_MULTIPLIER_MF) {
                    portfolioSecurityVO.setTransactAmount(transAmt);
                } else {
                    portfolioSecurityVO.setTransactAmount(Math.floor(transAmt - (transAmt % DEFAULT_MULTIPLIER_MF)));
                }
            } else {
                portfolioSecurityVO.setBuysellStatusText(MIN_SUB_TEXT);
                portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                portfolioSecurityVO.setTransactAmount(ZERO_POINT_ZERO);
            }
        } else {
            portfolioSecurityVO.setBuysellStatusText(MIN_NOT_AVAIL);
            portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
            portfolioSecurityVO.setTransactAmount(ZERO_POINT_ZERO);
        }

            
       /* } else {
            portfolioSecurityVO.setBuysellStatusText(MIN_NOT_AVAIL);
            portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
            portfolioSecurityVO.setTransactAmount(ZERO_POINT_ZERO);
        }*/
        
       
    }

    /**
     * This method is calculating current value for the asset class.
     * current_value =sum of all security in asset class (current_price *
     * executed_units)
     *
     * @param portfolioDetailsVO - Selected asset class
     * @return Double total value of asset class
     */
    private static Double calculateCurrentValuePFDetailsForAdvisor(final PortfolioDetailsVO portfolioDetailsVO) {
        double currentValue = ZERO_POINT_ZERO;
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {

            //checking status of security to avoid security removed by advisor
            if (portfolioSecurityVO.getStatus() && portfolioSecurityVO.getExecutedUnits() != ZERO) {
                portfolioSecurityVO.setCurrentValue(portfolioSecurityVO.getCurrentPrice() == null ? ZERO : portfolioSecurityVO.getCurrentPrice()
                        * (portfolioSecurityVO.getExecutedUnits() == null ? ZERO : portfolioSecurityVO.getExecutedUnits()));
                currentValue = currentValue + portfolioSecurityVO.getCurrentValue();
            } else {
                portfolioSecurityVO.setCurrentValue(ZERO_POINT_ZERO);
            }
        }
        return roundToTwoDecimal(currentValue);
    }

    /**
     * This method is used to calculate current weight of amount used by all
     * securities in the portfolio weight = (security_current)value/portfolio
     * value)*100
     *
     * @param portfolioDetailsVO - list of asset classes
     * @param portfolioValue - current portfolio value
     */
    private static void assignPFSecurityWeightForAdvisor(PortfolioDetailsVO portfolioDetailsVO, Double portfolioValue) {
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {
//            if (portfolioSecurityVO.getStatus() && portfolioSecurityVO.getExecutedUnits() != ZERO) {
//                portfolioSecurityVO.setCurrentWeight(roundToTwoDecimal((portfolioSecurityVO.getCurrentValue() / portfolioValue) * 100.0));
//            } else {
//                portfolioSecurityVO.setCurrentWeight(ZERO_POINT_ZERO);
//            }
            if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                portfolioSecurityVO.setCurrentWeight(roundToTwoDecimal((portfolioSecurityVO.getCurrentValue() / portfolioValue) * 100.0));
            }
        }
    }

    public static void updateValueAndWeightForAdvisor(PortfolioVO portfolioVO, boolean onSaveorUpdate) {

        // Assumption portfolio value will always default(1000000) here. calculate weight an value and cash balance on that.
        // Calculate values (price * units).
        List<PortfolioDetailsVO> portfolioDetailsVOs = PortfolioUtil.getPortfolioDetails(portfolioVO);
        Double totalValue = ZERO_POINT_ZERO;
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            portfolioDetailsVO.setCurrentValue(PortfolioUtil.calculateCurrentValuePFDetailsForAdvisor(portfolioDetailsVO));
            totalValue = totalValue + portfolioDetailsVO.getCurrentValue();
        }
//        Caluculate weights. Above assumption.
//        portfolioVO.getCash().setCurrentValue(ALLOCATEDFUND_TEMP - totalValue);
//        portfolioVO.setCurrentValue(ALLOCATEDFUND_TEMP);
        if (onSaveorUpdate) {
            portfolioVO.getCash().setCurrentValue(ALLOCATEDFUND_TEMP - totalValue);
            portfolioVO.setBalanceCashWhnLastUpdated(portfolioVO.getCash().getCurrentValue());
            portfolioVO.setCurrentValue(ALLOCATEDFUND_TEMP);
        } else {
            portfolioVO.setCurrentValue(totalValue + portfolioVO.getBalanceCashWhnLastUpdated());
            portfolioVO.getCash().setCurrentValue(portfolioVO.getCurrentValue() - totalValue);
        }

        /*
         Calculate
         1. Security Weight (security_current_value/portfolio value)*100
         2. Existing Weight (value/total)*100
         */
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOs) {
            PortfolioUtil.assignPFSecurityWeightForAdvisor(portfolioDetailsVO, portfolioVO.getCurrentValue());
            portfolioDetailsVO.setCurrentWeight(roundToTwoDecimal((portfolioDetailsVO.getCurrentValue() / portfolioVO.getCurrentValue()) * 100.0));
            portfolioDetailsVO.setExistingWeight(roundToTwoDecimal((portfolioDetailsVO.getCurrentValue() / portfolioVO.getCurrentValue()) * 100.0));
        }
    }

    /**
     * Checking transaction eligibility for advisor create portfolio
     *
     * @param portfolioVO
     */
    public static void checkTransactionEligibility(PortfolioVO portfolioVO) {
        List<PortfolioSecurityVO> portfolioSecurityVOs = PortfolioUtil.getPortfolioSecurityVOs(portfolioVO);
        int minAum = LookupDataLoader.getPortfolioSize().get(portfolioVO.getPortfolioSizeSelectedKey()).getMinAum();
        for (PortfolioSecurityVO portfolioSecurityVO : portfolioSecurityVOs) {
            Double cmp = portfolioSecurityVO.getCurrentPrice() == null ? ZERO : portfolioSecurityVO.getCurrentPrice();
            if (portfolioSecurityVO.getSecurityId() != null && portfolioSecurityVO.getVenueCode() != null) {
                SymbolRecordVO selectedSymbol = SymbolListUtil.getSymbolRecordVO(portfolioSecurityVO.getAssetClass(), portfolioSecurityVO.getSecurityId());
                double minAqustnCost = portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())
                        ? Math.max(cmp, selectedSymbol.getMinSubScrFresh()) : cmp;
                double proportionatePFValue = (portfolioSecurityVO.getNewAllocation() * minAum) / 100;
                double transtionalEligiblity = ZERO;
                double numOfUnits = ZERO;
                if (cmp > ZERO && minAqustnCost > ZERO) {
                    numOfUnits = Math.floor(proportionatePFValue / cmp);
                    transtionalEligiblity = Math.floor(proportionatePFValue / minAqustnCost);
                }
                if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                    portfolioSecurityVO.setMinInvestment(Math.max(cmp, selectedSymbol.getMinSubScrFresh()));
                } else {
                    portfolioSecurityVO.setMinInvestment(cmp);
                }
                if (transtionalEligiblity > 1.2) {
                    portfolioSecurityVO.setTradeSignal("green");
                } else if (transtionalEligiblity >= 1 && transtionalEligiblity < 1.2) {
                    portfolioSecurityVO.setTradeSignal("orange");
                } else {
                    portfolioSecurityVO.setTradeSignal("red");
                }
            } else {
                portfolioSecurityVO.setMinInvestment(cmp);
            }

        }
    }

    /**
     * Checking transaction eligibility to identify whether portfolio re-balance
     * required or not.
     *
     * @param portfolioSecurityVO
     * @param portfolioSize
     * @return
     */
    private static boolean isPortfolioSecurityTradable(PortfolioSecurityVO portfolioSecurityVO, Integer portfolioSize) {
        int minAum = LookupDataLoader.getPortfolioSize().get(portfolioSize).getMinAum();
        if (portfolioSecurityVO.getSecurityId() != null && portfolioSecurityVO.getVenueCode() != null) {
            Double cmp = portfolioSecurityVO.getCurrentPrice() == null ? ZERO : portfolioSecurityVO.getCurrentPrice();
            SymbolRecordVO selectedSymbol = SymbolListUtil.getSymbolRecordVO(portfolioSecurityVO.getAssetClass(), portfolioSecurityVO.getSecurityId());
            double minAqustnCost = portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())
                    ? Math.max(cmp, selectedSymbol.getMinSubScrFresh()) : cmp;
            double proportionatePFValue = (portfolioSecurityVO.getNewAllocation() * minAum) / 100;
            double transtionalEligiblity = ZERO;
            double numOfUnits = ZERO;
            if (cmp > ZERO && minAqustnCost > ZERO) {
                numOfUnits = Math.floor(proportionatePFValue / cmp);
                transtionalEligiblity = Math.floor(proportionatePFValue / minAqustnCost);
            }
            if (transtionalEligiblity > 1.2) {
                portfolioSecurityVO.setTradeSignal("green");
                return true;
            } else if (transtionalEligiblity >= 1 && transtionalEligiblity < 1.2) {
                portfolioSecurityVO.setTradeSignal("orange");
                return true;
            } else {
                portfolioSecurityVO.setTradeSignal("red");
                return false;
            }
        }
        return false;
    }

    /**
     * Method to update transaction details considering asset weight. A sell
     * should oly be placed if asset weight violates the required range.
     *
     * @param customerPortfolioVO
     */
    public static boolean updateTransactionDetailsConsideringAsset(CustomerPortfolioVO customerPortfolioVO) {
        boolean flag = false;
        List<PortfolioDetailsVO> portfolioDetailsVOList = customerPortfolioVO.getPortfolioDetailsVOs();
        Double totalTransactionAmount = ZERO_POINT_ZERO;
        Double totalTransactionCount = ZERO_POINT_ZERO;
        Double portfolio_securitities_value = ZERO_POINT_ZERO;
        Double cashAmt = customerPortfolioVO.getCashAmount().doubleValue();
        Float PortfolioAmount = (Float) ZERO_POINT_ZERO.floatValue();
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOList) {
            List<PortfolioSecurityVO> customerPortfolioSecurities = portfolioDetailsVO.getSecurities();
            for (PortfolioSecurityVO portfolioSecurityVO : customerPortfolioSecurities) {
                portfolio_securitities_value = portfolio_securitities_value + (portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());

            }
        }
        PortfolioAmount = PortfolioAmount + (Float) portfolio_securitities_value.floatValue() + (Float) cashAmt.floatValue();
        Set<String> venueNameSet = new HashSet<String>();
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOList) {
            List<PortfolioSecurityVO> customerPortfolioSecurities = portfolioDetailsVO.getSecurities();
            for (PortfolioSecurityVO portfolioSecurityVO : customerPortfolioSecurities) {
                //Checking for security removed by advisor
                if (portfolioSecurityVO.getStatus() || portfolioSecurityVO.getExecutedUnits() != ZERO) {
                    portfolioSecurityVO.setTransactAmount(ZERO_POINT_ZERO);
                    Double transactionUnits = ZERO_POINT_ZERO;
                    if (portfolioSecurityVO.getStatus()) {
                        Double allocationForSecurity = roundToTwoDecimal(PortfolioAmount * (portfolioSecurityVO.getNewAllocation() / HUNDRED));

                        if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                            totalTransactionCount = portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) == ZERO ? ZERO : roundToTwoDecimal((allocationForSecurity / portfolioSecurityVO.getCurrentPrice()));
                        } else {
                            totalTransactionCount = portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) == ZERO ? ZERO : Math.floor(allocationForSecurity / portfolioSecurityVO.getCurrentPrice());
                        }
                        transactionUnits = totalTransactionCount == ZERO ? ZERO : portfolioSecurityVO.getExecutedUnits() - totalTransactionCount;
                        portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * transactionUnits);
                        if (portfolioSecurityVO.getStatus() && transactionUnits < ZERO
                                && (portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)) {
                            // To Buy
                            if (cashAmt > ZERO_POINT_ZERO) {
                                if (portfolioSecurityVO.getCurrentPrice() * Math.abs(transactionUnits) > cashAmt) {
                                    Double transVal = roundToTwoDecimal(cashAmt / portfolioSecurityVO.getCurrentPrice());
                                    transactionUnits = transVal <= ZERO ? ZERO : transVal;
                                }
                                cashAmt = cashAmt - (portfolioSecurityVO.getCurrentPrice() * Math.abs(transactionUnits));
                                portfolioSecurityVO.setTransactAmount(portfolioSecurityVO.getCurrentPrice() * Math.abs(transactionUnits));
                                if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                                    //check for NSEMF Buy
                                    checkForMfOrderBuy(portfolioSecurityVO, transactionUnits, allocationForSecurity);
                                } else {
                                    portfolioSecurityVO.setBuysellStatusValue(BuySell.BUY.value());
                                    portfolioSecurityVO.setBuysellStatusText(BuySell.BUY.toString());
                                    portfolioSecurityVO.setTransactUnits(roundToTwoDecimal(Math.abs(transactionUnits)));
                                    portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_UP);
                                }
                            } else {
                                portfolioSecurityVO.setBuysellStatusValue(ZERO);
                                portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                                portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                                portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                            }
                        } else if (portfolioSecurityVO.getStatus() && (transactionUnits > ZERO)
                                && (portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)
                                && !((portfolioDetailsVO.getCurrentWeight() <= portfolioDetailsVO.getMaximumRange())
                                && (portfolioDetailsVO.getCurrentWeight() >= portfolioDetailsVO.getMinimumRange()))) {
                            // To sell
                            portfolioSecurityVO.setTransactAmount(portfolioSecurityVO.getCurrentPrice() * Math.abs(transactionUnits));
                            portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                            portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                            portfolioSecurityVO.setTransactUnits(transactionUnits);
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                            //For Security under ELSS
                            if (portfolioSecurityVO.getAssetClassId() == BLOCKED_CASH_ID) {
                                portfolioSecurityVO.setBuysellStatusValue(ZERO);
                                portfolioSecurityVO.setBuysellStatusText(LOCKED_MESSAGE);
                                portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                                portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);

                            }
                        } else {
                            portfolioSecurityVO.setBuysellStatusValue(ZERO);
                            portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                            portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                        }
                    } else if (!portfolioSecurityVO.getStatus()) {
                        //To sell security removed from recommended portfolio. 
                        portfolioSecurityVO.setTransactionAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());
                        if ((portfolioSecurityVO.getCurrentPrice().compareTo(ZERO_POINT_ZERO) != ZERO)) {
                            portfolioSecurityVO.setTransactAmount(portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getExecutedUnits());
                            portfolioSecurityVO.setBuysellStatusValue(BuySell.SELL.value());
                            portfolioSecurityVO.setBuysellStatusText(BuySell.SELL.toString());
                            portfolioSecurityVO.setTransactUnits(portfolioSecurityVO.getExecutedUnits().doubleValue());
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DOWN);
                        } else {
                            portfolioSecurityVO.setBuysellStatusValue(ZERO);
                            portfolioSecurityVO.setBuysellStatusText(EMPTY_STRING);
                            portfolioSecurityVO.setTransactUnits(ZERO_POINT_ZERO);
                            portfolioSecurityVO.setBuySellTextColor(CMP_TEXT_COLOR_DEFAULT);
                        }
                    }

                    // To display positive value in add or redeem fund page
                    if (totalTransactionAmount < PortfolioAmount) {
                        totalTransactionAmount += portfolioSecurityVO.getTransactAmount();
                    }
                    venueNameSet.add(portfolioSecurityVO.getVenueCode());

                }
                flag = (flag) || portfolioSecurityVO.getTransactAmount() == ZERO_POINT_ZERO;
            }

        }

        customerPortfolioVO.setVenuName(venueNameSet);
        if (totalTransactionAmount > PortfolioAmount) {
            customerPortfolioVO.setTotalTransAmt((Double) PortfolioAmount.doubleValue());
        }else{
            customerPortfolioVO.setTotalTransAmt((Double) totalTransactionAmount);
        }
//        return flag;
        return totalTransactionAmount == ZERO_POINT_ZERO;
    }
}
