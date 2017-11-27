/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.git.oms.api.frontend.exceptions.FrontEndException;
import com.git.oms.api.frontend.exceptions.MapperException;
import com.git.oms.api.frontend.message.MessageTypes;
import com.git.oms.api.frontend.message.OMSDTO;
import com.git.oms.api.frontend.message.OMSDTOFactory;
import com.git.oms.api.frontend.message.as.request.LogoutRequestDTO;
import com.git.oms.api.frontend.message.as.response.LogoutResponseDTO;
import com.git.oms.api.frontend.message.fieldtype.BuySell;
import com.git.oms.api.frontend.message.fieldtype.Channel;
import com.git.oms.api.frontend.message.fieldtype.MethodNames;
import com.git.oms.api.frontend.message.fieldtype.OrderType;
import com.git.oms.api.frontend.message.fieldtype.PriceCondition;
import com.git.oms.api.frontend.message.fieldtype.ProductType;
import com.git.oms.api.frontend.message.fieldtype.PurchaseType;
import com.git.oms.api.frontend.message.fieldtype.RptResponseMsgType;
import com.git.oms.api.frontend.message.fieldtype.Segment;
import com.git.oms.api.frontend.message.fieldtype.VenueCode;
import com.git.oms.api.frontend.message.order.request.CancelOrderRequestDTO;
import com.git.oms.api.frontend.message.order.request.NewOrderRequestDTO;
import com.git.oms.api.frontend.message.order.response.CancelOrderResponseDTO;
import com.git.oms.api.frontend.message.report.request.OMSGeneralReportRequestDTO;
import com.git.oms.api.frontend.message.report.request.OrderBookRequestDTO;
import com.git.oms.api.frontend.message.report.request.ReportRequestDTO;
import com.git.oms.api.frontend.message.report.request.TradeSummaryRequestDTO;
import com.git.oms.api.frontend.message.report.request.UserPositionRequestDTO;
import com.git.oms.api.frontend.message.report.response.MetaData;
import com.git.oms.api.frontend.message.report.response.ReportResponseDTO;
import com.git.oms.api.frontend.message.report.response.ResultSet;
import com.gtl.mmf.bao.IRebalancePortfolioBAO;
import com.gtl.mmf.common.EnumBuySellStatus;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.EnumOMSChannel;
import com.gtl.mmf.common.OMSFilterType;
import com.gtl.mmf.dao.IRebalancePortfolioDAO;
import com.gtl.mmf.entity.AddRedeemLogTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerPortfolioDetailsTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTransactionExecutionDetailsTb;
import com.gtl.mmf.entity.CustomerTransactionOrderDetailsTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MasterAssetTb;
import com.gtl.mmf.entity.MasterCustomerTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import com.gtl.mmf.entity.PortfolioDetailsTb;
import com.gtl.mmf.entity.PortfolioSecuritiesTb;
import com.gtl.mmf.entity.PortfolioTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.HH_MM;
import static com.gtl.mmf.service.util.IConstants.OMS_DTO_HEADER_VERSION;
import static com.gtl.mmf.service.util.IConstants.OMS_ERROR_CODE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.SecuritySellMapper;
import com.gtl.mmf.service.vo.CustomerPortfolioVO;
import com.gtl.mmf.service.vo.OMSOrderVO;
import com.gtl.mmf.service.vo.PieChartDataVO;
import com.gtl.mmf.service.vo.PortfolioDetailsVO;
import com.gtl.mmf.service.vo.PortfolioSecurityVO;
import com.gtl.mmf.service.vo.TodaysDetailsVO;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is used for portfolio rebalance
 *
 * @author
 */
public class RebalancePortfolioBAOImpl implements IRebalancePortfolioBAO, IConstants {

    static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.RebalancePortfolioBAOImpl");
    private static final String DATE_DD_MMM_YY_HH_MM_SS_SSS_A = "dd-MMM-yy HH.mm.ss.SSS a";
    private static final String ORD_RST_MAP_INDEX = "Table";
    private static final String ORD_RST_MAP_RESULT = "Result";
    private static final String ORD_RST_MAP_BP = "BP";
    private static final String ORD_RST_MAP_PRDNAME = "PRODUCTNAME";
    private static final String ORD_RST_MAP_AVBP = "AVAILABLEBP";
    private static final String ORD_RST_MAP_LEDGER = "LEDGER";
    private static final String DPCODE = "DPCODE";
    private static final String DPCLIENTID = "DPCLIENTID";
    private static final String DPID = "DPID";
    private static final String HOLDINGNATURE = "HOLDINGNATURE";
    private static final String DP_SETTLEMENT = "Y"; // Y for demat
    @Autowired
    private IRebalancePortfolioDAO rebalancePortfolioDAO;
    PortfolioSecurityVO psvo1 = new PortfolioSecurityVO();
    Map<String, NewOrderRequestDTO> securityMap = new HashMap<String, NewOrderRequestDTO>();

    /**
     * This method is used to load data to be displayed in the rebalance page.
     * Construct data for Target pie chart and Customer portfolio pie chart.
     * Construct Securities in the asset classes in portfolio and details
     * required for sending execution order
     *
     * @param customerPortfolioVO portfolio assigned to the investor
     * @param targetPieChartDataVOList list that store data for target pie chart
     * @param customerPieChartDataVOList list that store data for Customer pie
     * chart
     * @param portfolioDetailsVOList list of asset classes in the investor
     * portfolio
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getPortfolioRebalancePageDetails(
            CustomerPortfolioVO customerPortfolioVO,
            List<PieChartDataVO> targetPieChartDataVOList,
            List<PieChartDataVO> customerPieChartDataVOList,
            List<PortfolioDetailsVO> portfolioDetailsVOList) {

        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
        masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
        customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);

        /*
         Returns 3 Objects.
         1. customer portfolio
         2. customer portfolio details
         3. customer portfolio securitieas
        
         */
        List<Object> responseItems = rebalancePortfolioDAO
                .getRebalancePortfolioPageDetails(customerPortfolioTb);

        // Getting customer portfolio informations 
        customerPortfolioTb = (CustomerPortfolioTb) responseItems.get(ZERO);
        customerPortfolioVO.setRelationId(customerPortfolioTb
                .getCustomerAdvisorMappingTb().getRelationId());
        customerPortfolioVO.setCustomerPortfolioId(customerPortfolioTb
                .getCustomerPortfolioId());
        customerPortfolioVO.setPortfolioId(customerPortfolioTb.getPortfolioTb()
                .getPortfolioId());
        customerPortfolioVO.setPorfolioStyleName(customerPortfolioTb
                .getPortfolioTb().getMasterPortfolioTypeTb()
                .getMasterPortfolioStyleTb().getPortfolioStyle());
        customerPortfolioVO.setBenchmarkName(customerPortfolioTb
                .getPortfolioTb().getMasterBenchmarkIndexTb().getValue());
        customerPortfolioVO.setPortfolioName(customerPortfolioTb
                .getPortfolioTb().getPortfolioName());
        customerPortfolioVO.setNoreBalanceStatus(customerPortfolioTb
                .getNoRebalanceStatus());
        customerPortfolioVO.setAlocatedFunds(customerPortfolioTb
                .getCustomerAdvisorMappingTb().getAllocatedFunds());
        customerPortfolioVO.setCustomerId(customerPortfolioTb
                .getMasterCustomerTb().getCustomerId());
        customerPortfolioVO.setRebalanceRequired(customerPortfolioTb
                .getRebalanceRequired());
        customerPortfolioVO.setLastExeDate(customerPortfolioTb
                .getLastExecutionUpdate());
        customerPortfolioVO.setCashAmount(customerPortfolioTb.getCashAmount());
        customerPortfolioVO.setOmsLoginId(customerPortfolioTb.getOmsLoginId());
        customerPortfolioVO.setOmsPAN(customerPortfolioTb.getMasterCustomerTb()
                .getPanNo());
        customerPortfolioVO.setOmsdob(customerPortfolioTb.getMasterCustomerTb()
                .getDob());
        customerPortfolioVO.setPortfolioModified(customerPortfolioTb.getPortfolioModified());
        customerPortfolioVO.setBuyingPower(customerPortfolioTb.getBuyingPower() == null ? ZERO_POINT_ZERO.floatValue() : customerPortfolioTb.getBuyingPower());
        customerPortfolioVO.setBlockedCash(customerPortfolioTb.getBlockedCash() == null ? ZERO_POINT_ZERO.floatValue() : customerPortfolioTb.getBlockedCash());

        // Getting each customer portfolio details(Asset classes) informations 
        Set<CustomerPortfolioDetailsTb> customerPortfolioDetailsTbs = (Set<CustomerPortfolioDetailsTb>) customerPortfolioTb.getCustomerPortfolioDetailsTbs();
        // Order to show in ui
        TreeSet<CustomerPortfolioDetailsTb> customerPortfolioDetailsSorted = new TreeSet<CustomerPortfolioDetailsTb>(new ComparatorImpl());
        customerPortfolioDetailsSorted.addAll(customerPortfolioDetailsTbs);
        for (CustomerPortfolioDetailsTb customerPortfolioDetailsTb : customerPortfolioDetailsSorted) {

            PortfolioDetailsVO portfolioDetailsVO = new PortfolioDetailsVO();
            targetPieChartDataVOList.add(buildPieChartdata(
                    customerPortfolioDetailsTb, Boolean.TRUE));
            customerPieChartDataVOList.add(buildPieChartdata(
                    customerPortfolioDetailsTb, Boolean.FALSE));

            portfolioDetailsVO.setAssetId(customerPortfolioDetailsTb
                    .getMasterAssetTb().getId());
            portfolioDetailsVO.setAssetClassName(customerPortfolioDetailsTb
                    .getMasterAssetTb().getAssetName());
            portfolioDetailsVO.setMinimumRange(customerPortfolioDetailsTb
                    .getRangeFrom() == null ? ZERO : customerPortfolioDetailsTb
                            .getRangeFrom().doubleValue());
            portfolioDetailsVO.setMaximumRange(customerPortfolioDetailsTb
                    .getRangeTo() == null ? ZERO : customerPortfolioDetailsTb
                            .getRangeTo().doubleValue());
            portfolioDetailsVO.setNewAllocation(customerPortfolioDetailsTb
                    .getNewAllocation() == null ? ZERO
                            : customerPortfolioDetailsTb.getNewAllocation()
                            .doubleValue());
            portfolioDetailsVO.setCurrentAllocation(customerPortfolioDetailsTb
                    .getCurrentAllocation() == null ? ZERO
                            : customerPortfolioDetailsTb.getCurrentAllocation());
            List<PortfolioSecurityVO> portfolioSecurityVOList = new ArrayList<PortfolioSecurityVO>();

            // Getting each customer portfolio security informations customerPortfolioTb.get
            for (CustomerPortfolioSecuritiesTb customerPortfolioSecuritiesTb : (List<CustomerPortfolioSecuritiesTb>) responseItems
                    .get(2)) {

                //Checking for security removed by advisor
                if (customerPortfolioSecuritiesTb.getMasterAssetTb().getId()
                        .equals(portfolioDetailsVO.getAssetId())
                        && (customerPortfolioSecuritiesTb.getStatus() || customerPortfolioSecuritiesTb.getExeUnits().doubleValue() != ZERO)) {

                    PortfolioSecurityVO portfolioSecurityVO = new PortfolioSecurityVO();
                    portfolioSecurityVO
                            .setCustomerPortfolioId(customerPortfolioSecuritiesTb
                                    .getCustomerPortfolioTb()
                                    .getCustomerPortfolioId());
                    portfolioSecurityVO
                            .setPortfolioId(customerPortfolioSecuritiesTb
                                    .getPortfolioTb().getPortfolioId());
                    portfolioSecurityVO
                            .setPortfolioDetailsId(customerPortfolioSecuritiesTb
                                    .getPortfolioDetailsTb()
                                    .getPortfolioDetailsId());
                    portfolioSecurityVO
                            .setPortfolioSecurityId(customerPortfolioSecuritiesTb
                                    .getPortfolioSecuritiesTb()
                                    .getPortfolioSecuritiesId());
                    portfolioSecurityVO
                            .setAssetClass(customerPortfolioSecuritiesTb
                                    .getMasterAssetTb().getAssetName());
                    portfolioSecurityVO
                            .setAssetClassId(customerPortfolioSecuritiesTb
                                    .getMasterAssetTb().getId());
                    portfolioSecurityVO
                            .setSecurityId(customerPortfolioSecuritiesTb
                                    .getSecurityId());
                    portfolioSecurityVO
                            .setScripDecription(customerPortfolioSecuritiesTb
                                    .getSecurityDescription());
                    portfolioSecurityVO
                            .setNewAllocation(customerPortfolioSecuritiesTb
                                    .getNewAllocation() == null ? ZERO
                                            : customerPortfolioSecuritiesTb
                                            .getNewAllocation().doubleValue());

                    portfolioSecurityVO
                            .setNewToleranceNegativeRange(customerPortfolioSecuritiesTb
                                    .getNewToleranceNegativeRange());
                    portfolioSecurityVO
                            .setNewTolerancePositiveRange(customerPortfolioSecuritiesTb
                                    .getNewTolerancePositiveRange());
                    portfolioSecurityVO
                            .setExecutedUnits(customerPortfolioSecuritiesTb
                                    .getExeUnits().doubleValue());
                    portfolioSecurityVO
                            .setRecommendedPrice(customerPortfolioSecuritiesTb
                                    .getRecommentedPrice());
                    portfolioSecurityVO
                            .setCurrentPrice((double) customerPortfolioSecuritiesTb
                                    .getCurrentPrice());
                    portfolioSecurityVO
                            .setVenueCode(customerPortfolioSecuritiesTb
                                    .getVenueCode());
                    portfolioSecurityVO
                            .setVenueScriptCode(customerPortfolioSecuritiesTb
                                    .getVenueScriptCode());
                    portfolioSecurityVO
                            .setInstrumentType(customerPortfolioSecuritiesTb
                                    .getInstrumentType());
                    portfolioSecurityVO
                            .setExpirationdate(customerPortfolioSecuritiesTb
                                    .getExpirationDate());
                    portfolioSecurityVO
                            .setStrikePrice(customerPortfolioSecuritiesTb
                                    .getStrikePrice() == null ? ZERO
                                            : customerPortfolioSecuritiesTb
                                            .getStrikePrice().doubleValue());
                    portfolioSecurityVO
                            .setSecurityCode(customerPortfolioSecuritiesTb
                                    .getSecurityCode());
                    portfolioSecurityVO
                            .setBenchMark(customerPortfolioSecuritiesTb
                                    .getPortfolioTb()
                                    .getMasterBenchmarkIndexTb().getId());
                    portfolioSecurityVO.setStatus(customerPortfolioSecuritiesTb.getStatus());
                    portfolioSecurityVO.setBoughtPrice(customerPortfolioSecuritiesTb.getLastBoughtPrice() == null
                            ? customerPortfolioSecuritiesTb.getCurrentPrice()
                            : customerPortfolioSecuritiesTb.getAverageRate());
                    portfolioSecurityVOList.add(portfolioSecurityVO);
                }
            }
            portfolioDetailsVO.setSecurities(portfolioSecurityVOList);
            portfolioDetailsVOList.add(portfolioDetailsVO);
        }
        customerPortfolioVO.setPortfolioDetailsVOs(portfolioDetailsVOList);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void rebalancePortfolio(
            List<PieChartDataVO> customerPieChartDataVOList,
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
        customerPieChartDataVOList.get(chasIndex).setData(
                (double) ONE - toatalValue);
    }

    /**
     * This method is used to send transaction order to OMS for execution.
     *
     * @param customerPortfolioVO Portfolio assigned to investor
     * @param portfolioDetailsVOList asset class informations
     * @param omsOrderVO
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void executeSecurityOrders(CustomerPortfolioVO customerPortfolioVO, List<PortfolioDetailsVO> portfolioDetailsVOList,
            OMSOrderVO omsOrderVO, boolean orderType) {

        /**
         * Before execute Transaction order mapping object is creating for
         * saving into DB before order is executing.
         */
        List<CustomerTransactionOrderDetailsTb> customerTransactionOrderDetailsTbList
                = createBeforeExecuteOrderlist(portfolioDetailsVOList, customerPortfolioVO, omsOrderVO, orderType);

        // Saving Transation Order details int to DB
        rebalancePortfolioDAO.savetransactionBeforeExecute(customerTransactionOrderDetailsTbList);

        /**
         * Reading application environment . if application environment is
         * production/preproduction , then order will send to OMS otherwise
         * Order will bypass OMS.
         *
         * When Bypass OMS we need to Insert data into
         * 1.mmf_daily_clien_balance_tb 2.mmf_daily_txnSummary_tb
         * 3.job_Schedule_tb so that EOD process can perform execution.
         */
        securityMap = new HashMap<String, NewOrderRequestDTO>();
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        final String mmfBrocker = PropertiesLoader.getPropertiesValue(MMF_BROCKER);
        if (!environment.equalsIgnoreCase(DEVELOPMENT) && mmfBrocker.equalsIgnoreCase("Geojit")) {
            /* Execute orders */
            List<PortfolioSecurityVO> sortedSecurityList = new ArrayList<PortfolioSecurityVO>();
            //List<PortfolioSecurityVO> sortedSecurityListForBuy = new ArrayList<PortfolioSecurityVO>();
            //List<PortfolioSecurityVO> sortedSecurityListForSell = new ArrayList<PortfolioSecurityVO>();
            for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOList) {

                //Checking for asset class CASH
                if (!portfolioDetailsVO.getAssetClassName().equalsIgnoreCase(TEXT_CASH)) {
                    Collections.sort(portfolioDetailsVO.getSecurities(), new SecuritySellMapper());
                    /*psvo1 = new PortfolioSecurityVO();
                    if (psvo1.getBuysellStatusText().equalsIgnoreCase(MF_SELL)) {
                        sortedSecurityListForSell.add(psvo1);
                    } else if (psvo1.getBuysellStatusText().equalsIgnoreCase(MF_BUY)) {
                        sortedSecurityListForBuy.add(psvo1);
                    }*/
                }
                sortedSecurityList.addAll(portfolioDetailsVO.getSecurities());
            }
            for (PortfolioSecurityVO portfolioSecurityVO : sortedSecurityList) {
                try {
                    if (checkingOrderPlacementStatus(portfolioSecurityVO, orderType)) {

                        /* Selecting only Securities with Units to BUY/SELL for executing */
                        //select mutual fund only before 3'o clock to place
                        if (!checkorderTimeForMutualFund(portfolioSecurityVO)) {

                            NewOrderRequestDTO newOrdDTO = OMSDTOFactory.getInstance().getNewOrderDTO();
                            newOrdDTO.setChannel(Channel.MMF.value());
                            newOrdDTO.setLmUniqueID(omsOrderVO.getOmsUserid().toUpperCase() + ":" + new Date().getTime());
                            newOrdDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
                            newOrdDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
                            newOrdDTO.setBuySell(portfolioSecurityVO.getBuysellStatusValue());
                            newOrdDTO.setPrice(portfolioSecurityVO.getCurrentPrice());
                            newOrdDTO.setPriceCondition(PriceCondition.MKT.value());
                            newOrdDTO.setProductType(ProductType.CASH.value());
                            newOrdDTO.setQty(portfolioSecurityVO.getTransactUnits());
                            newOrdDTO.setSecurityCode(portfolioSecurityVO.getSecurityCode());
                            newOrdDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
                            newOrdDTO.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());
                            newOrdDTO.setVenueCode(portfolioSecurityVO.getVenueCode());
                            // code for F&O securities
                            if (portfolioSecurityVO.getVenueCode().equals(VenueCode.BSEFO.value())
                                    || portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEFO.value())) {
                                newOrdDTO.setInstrumentType(portfolioSecurityVO.getInstrumentType());
                                newOrdDTO.setStrikePrice(portfolioSecurityVO.getStrikePrice());
                                newOrdDTO.setExpirationDate(portfolioSecurityVO.getExpirationdate());
                                newOrdDTO.setCallPut(EMPTY_STRING);
                                newOrdDTO.setStyle(EMPTY_STRING);
                            }

                            if (LookupDataLoader.getVenueStatusMap().get(portfolioSecurityVO.getVenueCode())) {
                                // Online oreder
                                newOrdDTO.setOrderType(ONE);
                            } else {
                                // Offline order
                                newOrdDTO.setOrderType(26);
                            }

                            // code for MF securities
                            if (portfolioSecurityVO.getVenueCode().equals(VenueCode.NSEMF.value())) {
                                newOrdDTO.setDepositorySettlement(DP_SETTLEMENT);
                                newOrdDTO.setDpId(omsOrderVO.getDpId());
                                newOrdDTO.setDpClientCode(omsOrderVO.getDpClientId());
                                newOrdDTO.setMOH(omsOrderVO.getMoh());
                                if (portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(BuySell.BUY.toString())) {
                                    if (portfolioSecurityVO.getExecutedUnits() > 0) {
                                        newOrdDTO.setPurchaseType(PurchaseType.ADDITIONAL.value());
                                    } else {
                                        newOrdDTO.setPurchaseType(PurchaseType.FRESH.value());
                                    }
                                }

                                if (portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(BuySell.BUY.toString())) {
                                    //in case of BUY order,pass amount within minimum range.
                                    newOrdDTO.setQty(0);
                                    newOrdDTO.setPrice(portfolioSecurityVO.getTransactAmount());
                                } else {
                                    //in case of SELL order,set no: of unit to be sold
                                    newOrdDTO.setQty(portfolioSecurityVO.getTransactUnits());
                                    newOrdDTO.setPrice(0);
                                }
                                newOrdDTO.setSeries(portfolioSecurityVO.getSecurityId().split("|")[1]);
                                newOrdDTO.setPriceCondition(PriceCondition.LMT.value());
                                newOrdDTO.setProClient("CLI");
                                newOrdDTO.setClientid2(EMPTY_STRING);
                                newOrdDTO.setClientid3(EMPTY_STRING);
                                newOrdDTO.setClient2Name(EMPTY_STRING);
                                newOrdDTO.setClient3Name(EMPTY_STRING);
                                newOrdDTO.setClient2Pan(EMPTY_STRING);
                                newOrdDTO.setClient3Pan(EMPTY_STRING);
                                newOrdDTO.setClient2Kyc(EMPTY_STRING);
                                newOrdDTO.setClient3Kyc(EMPTY_STRING);
                                newOrdDTO.setFolioNumber(EMPTY_STRING);
                                newOrdDTO.setMisc1(omsOrderVO.getOmsRoleId());
                                /**
                                 * below 4 fields must be provided with value
                                 * from client in case of SIP. For current use
                                 * '0' is supplied.
                                 */
                                newOrdDTO.setFrequency(0);
                                newOrdDTO.setMonthCombination(0);
                                newOrdDTO.setPeriodicity(0);
                                newOrdDTO.setDaysOrDates(0);

                            }
                            newOrdDTO.setVenueScripCode(portfolioSecurityVO.getVenueScriptCode());

                            //condition to set segment based on exchange
                            if (portfolioSecurityVO.getVenueCode().endsWith("FO")) {
                                newOrdDTO.setSegment(Segment.FO.value());
                            } else {
                                newOrdDTO.setSegment(Segment.CM.value());
                            }
                            LOGGER.log(Level.INFO, "*** OMS-ORDER-REQUEST-REQUEST ***[**{0} **]", newOrdDTO);
                            OMSDTO omsdtoReply = omsOrderVO.getOmsServices().placeOrder(newOrdDTO);
                            LOGGER.log(Level.INFO, "*** OMS-ORDER-REQUEST-RESULTS ***[**{0} **]", omsdtoReply);
                            String venue_script_code = newOrdDTO.getVenueScripCode();
                            Integer error_code = omsdtoReply.getDTOHeader().getErrorCode();
                            securityMap.put(venue_script_code, newOrdDTO);

                        }
                    }

                } catch (FrontEndException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                } catch (MapperException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                } catch (NullPointerException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
        }
        if (!environment.equalsIgnoreCase(PRODUCTION)) {
            this.populateTableWithValues(omsOrderVO, customerPortfolioVO, customerTransactionOrderDetailsTbList);
        }
        /* Save after execute */
        CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
        createExecutionDetails(omsOrderVO, customerPortfolioVO, customerPortfolioTb);
        rebalancePortfolioDAO.savetransactionAfterExecute(customerPortfolioTb);
        if (securityMap != null || securityMap.isEmpty()) {
            for (Map.Entry<String, NewOrderRequestDTO> entry : securityMap.entrySet()) {
                System.out.println(entry.getKey() + "##############" + entry.getValue());
                if (entry.getValue().getDTOHeader().getErrorCode() != 0) {
                    LOGGER.log(Level.INFO, "***Invoke block details method");
                    Integer noOfRowsUpdated = rebalancePortfolioDAO.updateBlockedAmountForSecurity(entry.getValue().getPrice(), entry.getValue().getQty(), entry.getValue().getLmUniqueID());
                    LOGGER.log(Level.INFO, "*** OMS-NO OF ROWS UPDATED ***[**{0} **]", noOfRowsUpdated);
                }
            }
        }
    }

    private PieChartDataVO buildPieChartdata(CustomerPortfolioDetailsTb customerPortfolioDetailsTb, boolean chartType) {
        PieChartDataVO pieChartDataVO = new PieChartDataVO();
        pieChartDataVO.setLabel(customerPortfolioDetailsTb.getMasterAssetTb().getAssetName());
        pieChartDataVO.setColor(customerPortfolioDetailsTb.getMasterAssetTb().getAssetColor());
        if (chartType) {
            pieChartDataVO.setData(customerPortfolioDetailsTb.getNewAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getNewAllocation().doubleValue());
        } else {
            pieChartDataVO.setData(customerPortfolioDetailsTb.getCurrentAllocation() == null ? ZERO
                    : customerPortfolioDetailsTb.getCurrentAllocation().doubleValue());
        }
        return pieChartDataVO;
    }

    /**
     * This method is used to create transaction order details object for saving
     * into DB
     *
     * @param portfolioDetailsVOList Asset class informations which contain
     * BUY/SELL status, number of units to BUY/SELL
     * @param customerPortfolioVO
     * @param omsOrderVO
     * @return list of mapping object corresponding to transaction order
     */
    private List<CustomerTransactionOrderDetailsTb> createBeforeExecuteOrderlist(
            List<PortfolioDetailsVO> portfolioDetailsVOList,
            CustomerPortfolioVO customerPortfolioVO, OMSOrderVO omsOrderVO, boolean orderType) {
        Double cashFlow = ZERO_POINT_ZERO;
        Double blocked_count = ZERO_POINT_ZERO;
        List<CustomerTransactionOrderDetailsTb> customerTransactionOrderDetailsTbList = new ArrayList<CustomerTransactionOrderDetailsTb>();
        for (PortfolioDetailsVO portfolioDetailsVO : portfolioDetailsVOList) {

            // Checking asset class CASH 
            if (!portfolioDetailsVO.getAssetClassName().equalsIgnoreCase(TEXT_CASH)) {
                for (PortfolioSecurityVO portfolioSecurityVO : portfolioDetailsVO.getSecurities()) {

                    if (checkingOrderPlacementStatus(portfolioSecurityVO, orderType)) {

                        /* Checking each security to find out which securities require to send ORDER
                         Avoid securities with units to BUY/SELL is zero and Buy Sell Status Value iz zero
                         */
                        //Allows placing order before 3ó clock for Mutual Fund
                        if (!checkorderTimeForMutualFund(portfolioSecurityVO)) {
                            CustomerTransactionOrderDetailsTb customerTransactionOrderDetailsTb = new CustomerTransactionOrderDetailsTb();

                            MasterAssetTb masterAssetTb = new MasterAssetTb();
                            MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
                            PortfolioTb portfolioTb = new PortfolioTb();
                            PortfolioDetailsTb portfolioDetailsTb = new PortfolioDetailsTb();
                            PortfolioSecuritiesTb portfolioSecuritiesTb = new PortfolioSecuritiesTb();

                            masterAssetTb.setId(portfolioSecurityVO.getAssetClassId());
                            masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
                            portfolioTb.setPortfolioId(portfolioSecurityVO.getPortfolioId());
                            portfolioDetailsTb.setPortfolioDetailsId(portfolioSecurityVO.getPortfolioDetailsId());
                            portfolioSecuritiesTb.setPortfolioSecuritiesId(portfolioSecurityVO.getPortfolioSecurityId());

                            customerTransactionOrderDetailsTb.setMasterAssetTb(masterAssetTb);
                            customerTransactionOrderDetailsTb.setMasterCustomerTb(masterCustomerTb);
                            customerTransactionOrderDetailsTb.setOmsUserid(omsOrderVO.getOmsUserid().toUpperCase());
                            customerTransactionOrderDetailsTb.setOrderDate(new Date());
                            customerTransactionOrderDetailsTb.setPortfolioDetailsTb(portfolioDetailsTb);
                            customerTransactionOrderDetailsTb.setPortfolioSecuritiesTb(portfolioSecuritiesTb);
                            customerTransactionOrderDetailsTb.setPortfolioTb(portfolioTb);
                            customerTransactionOrderDetailsTb.setSecurityPrice(new BigDecimal(portfolioSecurityVO.getCurrentPrice()));
                            customerTransactionOrderDetailsTb.setSecurityUnits(new BigDecimal(portfolioSecurityVO.getTransactUnits()));
                            customerTransactionOrderDetailsTb.setSecurityid(portfolioSecurityVO.getSecurityId());
                            customerTransactionOrderDetailsTb.setSecurityCode(portfolioSecurityVO.getSecurityCode());
                            customerTransactionOrderDetailsTb.setBuySell(portfolioSecurityVO.getBuysellStatusText() == null
                                    ? EnumBuySellStatus.NOT_APPLICABLE.getStatusCode()
                                    : portfolioSecurityVO.getBuysellStatusText());
                            customerTransactionOrderDetailsTb.setVenueCode(portfolioSecurityVO.getVenueCode());
                            customerTransactionOrderDetailsTb.setVenueScriptCode(portfolioSecurityVO.getVenueScriptCode());
                            customerTransactionOrderDetailsTb.setOrdertype(orderType);
                            customerTransactionOrderDetailsTbList.add(customerTransactionOrderDetailsTb);
                            //For Buy ledger:decrease but count: no change
                            if (portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(MF_BUY)) {
                                cashFlow = cashFlow + (portfolioSecurityVO.getCurrentPrice() * portfolioSecurityVO.getTransactUnits());
                            } else if (portfolioSecurityVO.getBuysellStatusText().equalsIgnoreCase(MF_SELL)) {
                                //for sell ledger:change but count:decrease
                                blocked_count = blocked_count + portfolioSecurityVO.getTransactUnits();
                                //To save block count in customerPortfolioSecuritiesTb
                                rebalancePortfolioDAO.updateBlockedCountForSecurity(customerPortfolioVO.getCustomerPortfolioId(), customerTransactionOrderDetailsTb);
                            }
                        }

                    }
                }
            }
        }
        LOGGER.log(Level.INFO, "*** SAVE BLOCKED CASH STARTING ***[**{0} **]", cashFlow);
        LOGGER.log(Level.INFO, "*** SAVE BLOCKED COUNT STARTING ***[**{0} **]", blocked_count);
        rebalancePortfolioDAO.saveBlockedCashDetails(customerPortfolioVO.getCustomerPortfolioId(), cashFlow, blocked_count);
        LOGGER.log(Level.INFO, "*** SAVE BLOCKED CASH COMPLETED***[**{0} **]", cashFlow);
        LOGGER.log(Level.INFO, "*** SAVE BLOCKED COUNT COMPLETED***[**{0} **]", blocked_count);

        return customerTransactionOrderDetailsTbList;
    }

    /**
     * Creating Execution details for saving into database
     *
     * @param omsOrderVO
     * @param customerPortfolioVO
     * @param customerPortfolioTb
     */
    private void createExecutionDetails(OMSOrderVO omsOrderVO,
            CustomerPortfolioVO customerPortfolioVO,
            CustomerPortfolioTb customerPortfolioTb) {
        try {
            CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
            MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
            customerAdvisorMappingTb.setRelationId(customerPortfolioVO.getRelationId());
            customerAdvisorMappingTb.setRelationStatus((short) EnumCustomerMappingStatus.REBALANCE_INITIATED.getValue());

            masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());

            customerPortfolioTb.setOmsLoginId(omsOrderVO.getOmsUserid().toUpperCase());
            customerPortfolioTb.setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
            customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);
            customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO.getCustomerPortfolioId());
            customerPortfolioTb.setNoRebalanceStatus(Boolean.FALSE);
            customerPortfolioTb.setRebalanceRequired(Boolean.FALSE);
            customerPortfolioTb.setLastExecutionUpdate(new Date());
            customerPortfolioTb.setPortfolioModified(Boolean.FALSE);
            customerPortfolioTb.setCurrentValue(customerPortfolioVO.getCurrentValue());
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    private CustomerTransactionExecutionDetailsTb setOrderExedetails(Map<String, String> map, CustomerPortfolioVO customerPortfolioVO) {

        CustomerTransactionExecutionDetailsTb customerTransactionExecutionDetailsTb = new CustomerTransactionExecutionDetailsTb();
        try {
            PortfolioTb portfolioTb = new PortfolioTb();
            MasterCustomerTb masterCustomerTb = new MasterCustomerTb();

            portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
            masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
            customerTransactionExecutionDetailsTb.setPortfolioTb(portfolioTb);
            customerTransactionExecutionDetailsTb.setTransId(Long.parseLong(map
                    .get(TRANSID)));
            customerTransactionExecutionDetailsTb.setOrderId(map.get(ORDERID));
            customerTransactionExecutionDetailsTb.setSecurityCode(map
                    .get(SECURITYCODE));
            customerTransactionExecutionDetailsTb.setVenueCode(map
                    .get(VENUECODE));
            customerTransactionExecutionDetailsTb.setVenueScriptCode(map
                    .get(VENUESCRIPCODE));
            customerTransactionExecutionDetailsTb
                    .setMasterCustomerTb(masterCustomerTb);
//            customerTransactionExecutionDetailsTb.setSecurityUnits(Integer
//                    .parseInt(map.get(EXECQTY)));
            customerTransactionExecutionDetailsTb.setSecurityUnits(new BigDecimal(map.get(EXECQTY)));
            customerTransactionExecutionDetailsTb
                    .setSecurityPrice(new BigDecimal(map.get(SECURITY_PRICE)));
            customerTransactionExecutionDetailsTb.setOrderDate(DateUtil
                    .stringToDate(map.get(ORDREPLAYTIME),
                            DATE_DD_MMM_YY_HH_MM_SS_SSS_A));
            customerTransactionExecutionDetailsTb.setOmsUserid(map
                    .get(ORDERINGUSERCODE));
            customerTransactionExecutionDetailsTb.setBuySell(BuySell
                    .getBuySell(Integer.parseInt(map.get(BUYORSELL))));
        } catch (NumberFormatException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return customerTransactionExecutionDetailsTb;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveTransEOD(CustomerPortfolioVO customerPortfolioVO) {
        try {
            CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
            CustomerAdvisorMappingTb customerAdvisorMappingTb = new CustomerAdvisorMappingTb();
            MasterCustomerTb masterCustomerTb = new MasterCustomerTb();
            PortfolioTb portfolioTb = new PortfolioTb();

            customerAdvisorMappingTb.setRelationId(customerPortfolioVO
                    .getRelationId());
            customerAdvisorMappingTb
                    .setRelationStatus((short) EnumCustomerMappingStatus.REBALANCE_INITIATED
                            .getValue());
            masterCustomerTb.setCustomerId(customerPortfolioVO.getCustomerId());
            portfolioTb.setPortfolioId(customerPortfolioVO.getPortfolioId());
            customerPortfolioTb.setOmsLoginId(customerPortfolioVO
                    .getOmsLoginId());
            customerPortfolioTb
                    .setCustomerAdvisorMappingTb(customerAdvisorMappingTb);
            customerPortfolioTb.setMasterCustomerTb(masterCustomerTb);
            customerPortfolioTb.setPortfolioTb(portfolioTb);
            customerPortfolioTb.setCustomerPortfolioId(customerPortfolioVO
                    .getCustomerPortfolioId());
            customerPortfolioTb.setNoRebalanceStatus(Boolean.FALSE);
            customerPortfolioTb.setRebalanceRequired(Boolean.FALSE);
            customerPortfolioTb.setLastExecutionUpdate(new Date());
            //  rebalancePortfolioDAO.saveTransactionsEOD(customerPortfolioTb);
        } catch (Exception ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * method to get Today's Trade details.
     *
     * @param omsOrderVO
     * @return
     */
    public List generatePortfolioReport(OMSOrderVO omsOrderVO) {
        float customerCahcAmt = ZERO;
        ResultSet resultset1 = new ResultSet();
        ResultSet Header = new ResultSet();
        List resultlist = new ArrayList();
        ReportRequestDTO requestDTO = OMSDTOFactory.getInstance()
                .getReportRequestDTO();
        requestDTO.getDTOHeader().setVersion(
                PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
        requestDTO.getDTOHeader().setMessageType(
                MessageTypes.REPORT_REQUEST.value());
        requestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        requestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setLmUniqueID(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setMethodName(MethodNames.GET_USER_PORTFOLIO.value());
        requestDTO
                .setRptResponseMsgType(RptResponseMsgType.XML_MESSAGE.value());
        requestDTO.setInitRequest(true);
        requestDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setTypeFilter(OMSFilterType.INTRADAY.getValue());
        requestDTO.setVenueCode("");
        requestDTO.setSecurityCode("");
        requestDTO.setRowNo(ZERO);
        requestDTO.setDirection(ZERO);
        try {
            ReportResponseDTO dto = (ReportResponseDTO) omsOrderVO
                    .getOmsServices().getUserPortfolio(requestDTO);
            LOGGER.log(Level.INFO, "OMS-PORTFOLIO-REPORT-REQUEST-SEND [**{0}**]", requestDTO);
            Map m = new HashMap();
            m.put("errorCode", "" + dto.getDTOHeader().getErrorCode());
            Header = setRptHeader(m);
            resultlist.add(0, Header);
            if (dto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, " ******OMS-PORTFOLIO-REPORT-RESPONSE *** Sucessfully  Got ******{0}", dto);
                if (dto.getOutput().getResultSet("Intraday") != null) {
                    resultset1 = dto.getOutput().getResultSet("Intraday");
                }
                resultlist.add(1, resultset1);//for todays trade details
                if (dto.getOutput().getResultSet(TEXT_CASH) != null) {
                    for (Map<String, String> en : dto.getOutput()
                            .getResultSet(TEXT_CASH).getResult()) {
                        if (en.get(ORD_RST_MAP_PRDNAME).equals(TEXT_CASH)) {
                            customerCahcAmt = Float
                                    .parseFloat(en.get(ORD_RST_MAP_AVBP));
                        }
                    }
                }
            } else {
                LOGGER.log(Level.WARNING, " ******OMS-PORTFOLIO-REPORT-RESPONSE-ERROR**** ERR:-----{0}",
                        dto.getDTOHeader().getErrorCode());
            }

        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
//        return customerCahcAmt;
        return resultlist;
    }

    private Float getUserPosition(OMSOrderVO omsOrderVO) {
        float cashAmt = ZERO;
        try {
            UserPositionRequestDTO userPositionRequestDTO = OMSDTOFactory
                    .getInstance().getUserPositionRequestDTO();
            userPositionRequestDTO.setUserCode(omsOrderVO.getOmsUserid().toUpperCase());
            userPositionRequestDTO.setVenueCode(VenueCode.NSE.value());
            userPositionRequestDTO.setEnteringUserCode(omsOrderVO
                    .getOmsUserid());
            userPositionRequestDTO.setTypeFilter(OMSFilterType.ALL.getValue());
            userPositionRequestDTO.setInitRequest(true);
            userPositionRequestDTO.getDTOHeader().setSessionKey(
                    omsOrderVO.getOmsSessionKey());
            userPositionRequestDTO.setRowNo(ZERO);
            userPositionRequestDTO.setDirection(ZERO);
            userPositionRequestDTO.setRoleID(omsOrderVO.getOmsRoleId());
            userPositionRequestDTO.setMisc1("E");
            LOGGER.log(Level.INFO,
                    "*** OMS-USER-POSITION-REQUEST ***[**{0} **]",
                    userPositionRequestDTO);
            ReportResponseDTO userPositonsResponseDTO = (ReportResponseDTO) omsOrderVO
                    .getOmsServices().getUserPosition(userPositionRequestDTO);
            LOGGER.log(Level.INFO,
                    "*** OMS-USER-POSITION-RESULTS ***[**{0} **]",
                    userPositonsResponseDTO);
            boolean result = userPositonsResponseDTO.getOutput().getResults()
                    .isEmpty();
            if (userPositonsResponseDTO.getDTOHeader().getErrorCode() == OMS_ERROR_CODE
                    && !result) {
                ResultSet resultset1 = userPositonsResponseDTO.getOutput()
                        .getResultSet(TEXT_CASH);
                LOGGER.log(Level.INFO, "*** User Balance Cash Amount **{0} **",
                        resultset1.getRow(ZERO).get(ORD_RST_MAP_BP));
                cashAmt = Float.parseFloat(resultset1.getRow(ZERO)
                        .get(ORD_RST_MAP_BP).toString());
            }
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return cashAmt;
    }

    private float getBPDrillDown(OMSOrderVO omsOrderVO) {
        float cashAmt = ZERO;
        try {
            ReportRequestDTO requestDTO = OMSDTOFactory.getInstance()
                    .getReportRequestDTO();
            requestDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
            requestDTO.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());
            requestDTO.setMisc1("Intraday");
            requestDTO.setRptResponseMsgType(MessageTypes.OMS_GENERAL.value());
            requestDTO.setMethodName(MethodNames.GET_BP_DRILL_DOWN.value());
            requestDTO.setInitRequest(true);
            requestDTO.getDTOHeader().setSessionKey(
                    omsOrderVO.getOmsSessionKey());
            ReportResponseDTO omsBPDrillDownResponseDTO;
            LOGGER.log(Level.INFO,
                    "*** OMS-USER-BPDRLLDOWN-REQUEST ***[**{0} **]", requestDTO);
            omsBPDrillDownResponseDTO = (ReportResponseDTO) omsOrderVO
                    .getOmsServices().getBPDrillDown(requestDTO);
            LOGGER.log(Level.INFO,
                    "*** OMS-USER-BPDRLLDOWN-RESULTS ***[**{0} **]",
                    omsBPDrillDownResponseDTO);
            cashAmt = Float.parseFloat(omsBPDrillDownResponseDTO.getOutput()
                    .getResultSet(ORD_RST_MAP_RESULT).getRow(0)
                    .get(ORD_RST_MAP_LEDGER).toString());
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
        return cashAmt;
    }

    /**
     * Method for getting DP client details such as, DPID,DPCODE,DPCLIENTID
     *
     * @param omsOrderVO
     */
    public void getDPClientMFdetails(OMSOrderVO omsOrderVO) {
        ReportRequestDTO requestDTO = OMSDTOFactory.getInstance()
                .getReportRequestDTO();
        requestDTO.getDTOHeader().setVersion(
                PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
        requestDTO.getDTOHeader().setMessageType(
                MessageTypes.REPORT_REQUEST.value());
        requestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        requestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setLmUniqueID(omsOrderVO.getOmsUserid().toUpperCase() + new Date());
        requestDTO.setRptResponseMsgType(RptResponseMsgType.STRING_MESSAGE
                .value());
        requestDTO.setMethodName(MethodNames.GET_MF_CLNTDPDETAIL.value());
        requestDTO.setInitRequest(true);
        requestDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        requestDTO.setRoleID(omsOrderVO.getOmsRoleId());
        requestDTO.setDepositorySettlement(DP_SETTLEMENT);
        try {
            LOGGER.log(Level.INFO, "OMS-PORTFOLIO-DP-CLIENT-REQUEST [**{0}**]", requestDTO);
            ReportResponseDTO dto = (ReportResponseDTO) omsOrderVO
                    .getOmsServices().getMFClntDpDetail(requestDTO);
            if (dto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "OMS-PORTFOLIO-DP-CLIENT-RESULTS [**{0}**]", dto);
                Map<String, String> map = (Map<String, String>) dto.getOutput()
                        .getResultSet(ORD_RST_MAP_INDEX).getResult().get(ZERO);
                omsOrderVO.setDpId(map.get(DPID));
                omsOrderVO.setDpcode(map.get(DPCODE));
                omsOrderVO.setDpClientId(map.get(DPCLIENTID));
                omsOrderVO.setMoh(map.get(HOLDINGNATURE));
            } else {
                LOGGER.log(Level.WARNING, "Error Code From OMS :-{0}", dto.getDTOHeader().getErrorCode());
            }
        } catch (FrontEndException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } catch (MapperException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * method to check the status of securities before placing in OMS.
     *
     * @param portfolioSecurityVO
     * @param placeOflineOrder
     * @return status,tells whether security should be placed or not.
     */
    private Boolean checkingOrderPlacementStatus(PortfolioSecurityVO portfolioSecurityVO, boolean placeOflineOrder) {
        boolean status = false;
        boolean holiday_NSE = LookupDataLoader.isHolyday_NSE();
        boolean holiday_BSE = LookupDataLoader.isHolyday_BSE();
        if (!(holiday_NSE && portfolioSecurityVO.getVenueCode().equalsIgnoreCase(NSE))
                || !(holiday_BSE && portfolioSecurityVO.getVenueCode().equalsIgnoreCase(BSE))) {
            if (placeOflineOrder) {
                status = true;
            } else if (!placeOflineOrder
                    && LookupDataLoader.getVenueStatusMap().get(portfolioSecurityVO.getVenueCode())) {
                status = true;
            } else if (!placeOflineOrder
                    && !LookupDataLoader.getVenueStatusMap().get(portfolioSecurityVO.getVenueCode())) {
                status = false;
            }
        }
        return status;
    }

    /**
     * method to get Today's order details.
     *
     * @param omsOrderVO
     * @return
     */
    public List getTodaysOrderDetails(OMSOrderVO omsOrderVO) {
        ResultSet resultset1 = new ResultSet();
        ResultSet Header = new ResultSet();
        List resultlist = new ArrayList();
        try {
            OrderBookRequestDTO objOrderBookRequestDTO = OMSDTOFactory.getInstance().getOrderBookRequestDTO();
            objOrderBookRequestDTO.setMisc1("");
            objOrderBookRequestDTO.setTransID(0);
            objOrderBookRequestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
            objOrderBookRequestDTO.setVenueCode("");
            objOrderBookRequestDTO.setSecurityCode("");
            objOrderBookRequestDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
            objOrderBookRequestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
            objOrderBookRequestDTO.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());

            /**
             * Channel is set to '0' inorder to get order details of the user
             * from all channels. ie, all order placed independent of channel.
             */
            //objOrderBookRequestDTO.setChannel(Channel.MMF.value());
            objOrderBookRequestDTO.setChannel(0);
            objOrderBookRequestDTO.setAppName("");
            objOrderBookRequestDTO.setInitRequest(true);
            objOrderBookRequestDTO.setDirection(0);

            LOGGER.log(Level.INFO, " *** OMS-ORDER-BOOK-REQUEST-SENT-- ****{0}", objOrderBookRequestDTO);
            ReportResponseDTO objReportResponse = (ReportResponseDTO) omsOrderVO.getOmsServices().getOrderBook(objOrderBookRequestDTO);
            Map m = new HashMap();
            m.put("errorCode", "" + objReportResponse.getDTOHeader().getErrorCode());
            Header = setRptHeader(m);
            resultlist.add(0, Header);
            if (objReportResponse.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, " ****** OMS-ORDER-BOOK-RESPONSE-DTO ******{0}", objReportResponse);
                resultset1 = objReportResponse.getOutput().getResultSet(ORD_RST_MAP_INDEX);

            } else {
                LOGGER.log(Level.WARNING, "OMS-ORDER-BOOK-REQUEST-RESPONSE-ERROR :-{0}", objReportResponse.getDTOHeader().getErrorCode());
            }
            resultlist.add(1, resultset1);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "OMS-ORDER-BOOK-REQUEST-RESPONSE-FrontEndException ", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "OMS-ORDER-BOOK-REQUEST-RESPONSE-MapperException ", ex);
        }
        return resultlist;
    }

    public ResultSet setRptHeader(Map m) {
        ResultSet Header = new ResultSet();
        MetaData headvals = new MetaData();
        headvals.addColumnName("errorCode");
        headvals.setTableName("Header");
        headvals.setRowCount(1);
        Header.setMetadata(headvals);
        Header.addRow(m);

        return Header;
    }

    /**
     * Method to cancel an Order placed in OMS
     *
     * @param omsOrderVO
     * @param todaysDetailsVO
     */
    public void cancelPlacedOrder(OMSOrderVO omsOrderVO, TodaysDetailsVO todaysDetailsVO) {
        CancelOrderRequestDTO cancelOrderRequest = OMSDTOFactory.getInstance().getCancelOrderDTO();
        cancelOrderRequest.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        cancelOrderRequest.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        cancelOrderRequest.setVenueCode(todaysDetailsVO.getVenueCode());
        cancelOrderRequest.setTransID(Long.parseLong(todaysDetailsVO.getTransId()));
        cancelOrderRequest.setOrderType(OrderType.CANCEL_ORDER.value());
        cancelOrderRequest.setOrderSource(todaysDetailsVO.getOrderSource());
        cancelOrderRequest.setLegNO(todaysDetailsVO.getLegno());
        cancelOrderRequest.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        cancelOrderRequest.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        cancelOrderRequest.setLmUniqueID(omsOrderVO.getOmsUserid().toUpperCase() + new Date());
        cancelOrderRequest.setChannel(todaysDetailsVO.getChannel());
        try {
            LOGGER.log(Level.INFO, "*** OMS-CANCEL-ORDER-REQUEST-SENT ***[**{0} **]", cancelOrderRequest);
            CancelOrderResponseDTO cancelOrderResponseDTO = (CancelOrderResponseDTO) omsOrderVO.getOmsServices().cancelOrder(cancelOrderRequest);

            if (cancelOrderResponseDTO.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "*** OMS-CANCEL-ORDER-RESPONSE-DTO ***[**{0} **]", cancelOrderResponseDTO);
            } else {
                LOGGER.log(Level.WARNING, "CANCEL-ORDER-REQUEST-RESPONSE-ERROR :-{0}", cancelOrderResponseDTO.getDTOHeader().getErrorCode());
            }
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "CANCEL-ORDER-REQUEST-EXCEPTION", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "CANCEL-ORDER-REQUEST-EXCEPTION", ex);
        }
    }

    /**
     * Method for LOgging out from OMS.
     *
     * @param omsOrderVO
     * @return
     */
    public String omslogout(OMSOrderVO omsOrderVO) {
        LogoutRequestDTO logoutReqDto = OMSDTOFactory.getInstance().getLogoutRequestDTO();
        logoutReqDto.getDTOHeader().setVersion(PropertiesLoader.getPropertiesValue(OMS_DTO_HEADER_VERSION));
        logoutReqDto.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        logoutReqDto.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        logoutReqDto.setUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        try {
            LOGGER.log(Level.INFO, "*** OMS-LOGOUT-REQUEST-SENT ***[**{0} **]", logoutReqDto);
            LogoutResponseDTO lrdto = (LogoutResponseDTO) omsOrderVO.getOmsServices().logout(logoutReqDto);
            LOGGER.log(Level.INFO, "*** OMS-LOGOUT-RESPONSE-DTO ***[**{0} **]", lrdto);
            if (lrdto.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "Logged out from OMS successfully for the [USER] :{0} [OMSSESSION] :{1}",
                        new Object[]{omsOrderVO.getOmsUserid().toUpperCase(), omsOrderVO.getOmsSessionKey()});
            } else {
                LOGGER.log(Level.INFO, "OMS returned error while Logging Out :-  [USER] :{0} [OMSSESSION] :{1} [ErrorCode] :{2}",
                        new Object[]{omsOrderVO.getOmsUserid().toUpperCase(), omsOrderVO.getOmsSessionKey(), lrdto.getDTOHeader().getErrorCode()});
            }
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "OMS-LOGOUT-EXCEPTION", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "OMS-LOGOUT-EXCEPTION", ex);
        }
        return null;
    }

    /**
     * Method to get Today Trade Summary
     *
     * @param todaysDetailsVO
     * @param omsOrderVO
     * @return
     */
    public List getTradeSummary(TodaysDetailsVO todaysDetailsVO, OMSOrderVO omsOrderVO) {
        ResultSet resultset1 = new ResultSet();
        ResultSet Header = new ResultSet();
        List resultlist = new ArrayList();
        TradeSummaryRequestDTO tradeDetailsRequestDTO = OMSDTOFactory.getInstance().getTradeSummaryRequestDTO();
        tradeDetailsRequestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        tradeDetailsRequestDTO.setEnteringUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        tradeDetailsRequestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsSessionKey());
        tradeDetailsRequestDTO.setOrderingUserCode(omsOrderVO.getOmsUserid().toUpperCase());
        tradeDetailsRequestDTO.setProductType(todaysDetailsVO.getProductTypeValue());
        tradeDetailsRequestDTO.setVenueCode(todaysDetailsVO.getVenueCode());
        tradeDetailsRequestDTO.setSecurityCode(todaysDetailsVO.getSecurityCode());
        tradeDetailsRequestDTO.setVenueScripCode(todaysDetailsVO.getVenueScripcode());
        tradeDetailsRequestDTO.setTypeFilter("I");
        tradeDetailsRequestDTO.setInitRequest(true);
        tradeDetailsRequestDTO.setDirection(0);
        tradeDetailsRequestDTO.setAppName("");
        tradeDetailsRequestDTO.setLmUniqueID(omsOrderVO.getOmsUserid().toUpperCase() + new Date());
        tradeDetailsRequestDTO.setInstrumentType(todaysDetailsVO.getInstrumentType());
        //tradeDetailsRequestDTO.setChannel(0);
        tradeDetailsRequestDTO.setChannel(EnumOMSChannel.MMF.value());
        try {
            LOGGER.log(Level.INFO, "*** OMS-TRADE-SUMMARY-REQUEST-SENT ***[**{0} **]", tradeDetailsRequestDTO);
            ReportResponseDTO objReportResponse = (ReportResponseDTO) omsOrderVO.getOmsServices().getTradeSummary(tradeDetailsRequestDTO);
            Map m = new HashMap();
            m.put("errorCode", "" + objReportResponse.getDTOHeader().getErrorCode());
            Header = setRptHeader(m);
            resultlist.add(0, Header);
            if (objReportResponse.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "*** OMS-TRADE-SUMMARY-RESPONSE-DTO ***[**{0} **]", objReportResponse);
                resultset1 = objReportResponse.getOutput().getResultSet("TrdSum");

            } else {
                LOGGER.log(Level.WARNING, "OMS-TRADE-SUMMARY-RESPONSE-DTO-ERROR :-{0}", objReportResponse.getDTOHeader().getErrorCode());
            }
            resultlist.add(1, resultset1);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "OMS-TRADE-SUMMARY-EXCEPTION", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "OMS-TRADE-SUMMARY-EXCEPTION", ex);
        }
        return resultlist;
    }

    /**
     * Method to get portfolio details using GeneralReportXL DB - defaultDB
     * SCHEMA - FLIPXL_WEB SP - SPFLIPXL_GETUSERPORTFOLIO
     *
     * @param todaysDetailsVO
     * @param omsOrderVO
     */
    public List getPortfolioSummary(OMSOrderVO omsOrderVO, boolean sinkbp) {
        ResultSet resultset1 = new ResultSet();
        ResultSet resultset2 = new ResultSet();
        ResultSet Header = new ResultSet();
        List resultlist = new ArrayList();
        StringBuilder queryStringBuilder = new StringBuilder("defaultDB");
        queryStringBuilder.append((char) 30).append("FLIPXL_WEB").append(DOT).append("SPFLIPXL_GETUSERPORTFOLIO")
                .append((char) 29).append("P_APP").append((char) 31).append(SPACE)
                .append((char) 30).append("P_ENTERINGUSERCODE").append((char) 31).append(omsOrderVO.getOmsUserid().toUpperCase())
                .append((char) 30).append("P_ORDERINGUSERCODE").append((char) 31).append(omsOrderVO.getOmsUserid().toUpperCase())
                .append((char) 30).append("P_VENUE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_TYPE").append((char) 31).append("A")//S was used before
                .append((char) 30).append("P_SECURITYCODE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_INSTRUMENTTYPE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_EXPIRATIONDATE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_CALLPUT").append((char) 31).append(SPACE)
                .append((char) 30).append("P_STYLE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_STRIKEPRICE").append((char) 31).append("0")
                .append((char) 30).append("P_VENUESCRIPCODE").append((char) 31).append(SPACE)
                .append((char) 30).append("P_PRODUCTTYPE").append((char) 31).append("0")
                .append((char) 30).append("P_DEALERROLEID").append((char) 31).append(omsOrderVO.getOmsRoleId())//roleId
                .append((char) 30).append("P_MISC1").append((char) 31).append("I")
                .append((char) 30).append("P_MISC2").append((char) 31).append(SPACE)
                .append((char) 30).append("P_MISC3").append((char) 31).append(SPACE);
        String queryString = queryStringBuilder.toString();
        OMSGeneralReportRequestDTO oMSGeneralReportRequestDTO;
        oMSGeneralReportRequestDTO = (OMSGeneralReportRequestDTO) OMSDTOFactory.getInstance().getOMSGeneralQueryRequestDTO();
        oMSGeneralReportRequestDTO.setMethodName(53);//methodName
        oMSGeneralReportRequestDTO.setInitRequest(true);
        oMSGeneralReportRequestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        oMSGeneralReportRequestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsUserid().toUpperCase());
        oMSGeneralReportRequestDTO.getDTOHeader().setMessageType(83);//messageType
        oMSGeneralReportRequestDTO.setRptResponseMsgType(RptResponseMsgType.XML_MESSAGE.value());
        /*
         * set query string with custom procedure
         */
        oMSGeneralReportRequestDTO.setGeneralReportStr(queryString);
        try {
            LOGGER.log(Level.INFO, "*** OMS-PORTFOLIO-SUMMARY-REQUEST-SENT ***[**{0} **]", oMSGeneralReportRequestDTO);
            ReportResponseDTO objResponseDTO = (ReportResponseDTO) omsOrderVO.getOmsServices().getGeneralReportXL(oMSGeneralReportRequestDTO);
            Map m = new HashMap();
            m.put("errorCode", "" + objResponseDTO.getDTOHeader().getErrorCode());
            Header = setRptHeader(m);
            resultlist.add(0, Header);
            if (objResponseDTO.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "*** OMS-PORTFOLIO-SUMMARY-RESPONSE-DTO ***[**{0} **]", objResponseDTO.getOutput().toString());
                resultset1 = objResponseDTO.getOutput().getResultSet("ResultSet3");
                if (sinkbp) {
                    resultset2 = objResponseDTO.getOutput().getResultSet("ResultSet5");
                }
            } else {
                LOGGER.log(Level.WARNING, "OMS-PORTFOLIO-SUMMARY-RESPONSE-DTO-ERROR :-{0}", objResponseDTO.getDTOHeader().getErrorCode());
            }
            resultlist.add(1, resultset1);
            resultlist.add(2, resultset2);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "OMS-PORTFOLIO-SUMMARY-REPORT-EXCEPTION", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "OMS-PORTFOLIO-SUMMARY-REPORT-EXCEPTION", ex);
        }
        return resultlist;
    }

    /**
     * ************* MANAGED FOR TESTING PURPOSE ******************* Method to
     * populate tables with value to manage order execution EOD job\ not
     * required in the live scenario entering data into tables :
     * 1.mmfDailyTxnSummaryTb 2.mmfDailyClientBalanceTb 3.JobScheduleTb
     * 4.cash_flow_tb
     */
    private void populateTableWithValues(OMSOrderVO omsOrderVO, CustomerPortfolioVO customerPortfolioVO,
            List<CustomerTransactionOrderDetailsTb> customerTransactionOrderDetailsTbList) {
        List<MmfDailyTxnSummaryTb> mmfDailyTxnSummaryTbList = new ArrayList<MmfDailyTxnSummaryTb>();
        double cashAmount = customerPortfolioVO.getCashAmount();
        MmfDailyClientBalanceTb mmfDailyClientBalanceTb = new MmfDailyClientBalanceTb();
        for (CustomerTransactionOrderDetailsTb customerTransactionOrderDetailsTb : customerTransactionOrderDetailsTbList) {

            if (!customerTransactionOrderDetailsTb.getBuySell().equalsIgnoreCase("N")) {

                // creating transaction summary data for saving in to DB
                MmfDailyTxnSummaryTb mmfDailyTxnSummaryTb = new MmfDailyTxnSummaryTb();
                mmfDailyTxnSummaryTb
                        .setTrndate(customerTransactionOrderDetailsTb
                                .getOrderDate());
                mmfDailyTxnSummaryTb
                        .setTradecode(omsOrderVO.getOmsUserid().toUpperCase());
                mmfDailyTxnSummaryTb
                        .setOrderno(customerTransactionOrderDetailsTb
                                .getCustomerTransactionOrderId().toString());
                mmfDailyTxnSummaryTb
                        .setProduct(customerTransactionOrderDetailsTb
                                .getVenueCode());
                mmfDailyTxnSummaryTb
                        .setVenueScriptCode(customerTransactionOrderDetailsTb
                                .getVenueScriptCode());
                mmfDailyTxnSummaryTb
                        .setSecurity(customerTransactionOrderDetailsTb
                                .getSecurityCode());
                mmfDailyTxnSummaryTb.setInstrument(EMPTY_STRING);
                mmfDailyTxnSummaryTb.setContract(EMPTY_STRING);
                mmfDailyTxnSummaryTb.setQuantity(new BigDecimal(0));
                mmfDailyTxnSummaryTb.setPrice(customerTransactionOrderDetailsTb
                        .getSecurityPrice());
                mmfDailyTxnSummaryTb.setUnits(customerTransactionOrderDetailsTb.getSecurityUnits());

                // Calculting transaction amount for each security (Security price * executed units)        
                double volume = customerTransactionOrderDetailsTb
                        .getSecurityPrice().doubleValue()
                        * customerTransactionOrderDetailsTb.getSecurityUnits().doubleValue();
                mmfDailyTxnSummaryTb.setVolume(new BigDecimal(volume));
                mmfDailyTxnSummaryTb.setBrokerage(new BigDecimal(20));
                mmfDailyTxnSummaryTb.setOthercharges(new BigDecimal(10));
                mmfDailyTxnSummaryTb.setChannel(MMF);
                mmfDailyTxnSummaryTb.setEuser(omsOrderVO.getOmsUserid().toUpperCase());
                mmfDailyTxnSummaryTb.setLastupdatedon(new Date());
                mmfDailyTxnSummaryTbList.add(mmfDailyTxnSummaryTb);

                /*Updating Cash balance
                 1.Sell 
                 Cash balance = Cash balance+(Security Price* Executed units) 
                 2. 
                 buy  Cash balance = Cash balance-(Security Price* Executed units)    */
                if (customerTransactionOrderDetailsTb.getBuySell()
                        .equalsIgnoreCase(BUY)) {
                    cashAmount = cashAmount - volume;
                    mmfDailyTxnSummaryTb.setBuysell(EnumBuySellStatus.BUY
                            .getStatusCode());
                } else if (customerTransactionOrderDetailsTb.getBuySell()
                        .equalsIgnoreCase(SELL)) {
                    if (!customerPortfolioVO.isFromAddReedem()) {
                        cashAmount = cashAmount + volume;
                    }
                    mmfDailyTxnSummaryTb.setBuysell(EnumBuySellStatus.SELL
                            .getStatusCode());
                } else {
                    mmfDailyTxnSummaryTb
                            .setBuysell(EnumBuySellStatus.NOT_APPLICABLE
                                    .getStatusCode());
                }

            }
        }

        // Inserting data into client balance table in DB
        mmfDailyClientBalanceTb.setLedgerbalanec(new BigDecimal(cashAmount));
        mmfDailyClientBalanceTb.setTrndate(new Date());
        mmfDailyClientBalanceTb.setTradecode(omsOrderVO.getOmsUserid().toUpperCase());
        mmfDailyClientBalanceTb.setEuser(omsOrderVO.getOmsUserid().toUpperCase());
        mmfDailyClientBalanceTb.setLastupdatedon(new Date());

        /*
         Inserting entries for Transaction and cash in job_Schedule_tb 
         and setting status as COMPLETD for EOD job processing
         */
        JobScheduleTb jobScheduleTb = new JobScheduleTb();
        jobScheduleTb.setJobType(TRANSACTION);
        jobScheduleTb.setStatus(COMPLETED);
        jobScheduleTb.setLastupdatedon(new Date());
        jobScheduleTb.setScheduledate(new Date());

        //Saving transaction summary data into DB
        rebalancePortfolioDAO.saveTransactionBypassOMS(mmfDailyTxnSummaryTbList, jobScheduleTb);
        JobScheduleTb jobScheduleTbCash = new JobScheduleTb();
        jobScheduleTbCash.setJobType(TEXT_CASH_CAPITAL);
        jobScheduleTbCash.setStatus(COMPLETED);
        jobScheduleTbCash.setScheduledate(new Date());
        jobScheduleTbCash.setLastupdatedon(new Date());

        //Saving client daily balance,job schedule  and CashFlowTb data into DB
        rebalancePortfolioDAO.saveDailyClientBalanceTb(mmfDailyClientBalanceTb, jobScheduleTbCash);
    }

    /**
     * Method to retrieve OMS order rejection detail using general report DB -
     * defaultDB SCHEMA - FLIP_FE_REPORT SP - FLIP_SPGETOMSORDERLOG
     *
     * @param omsOrderVO
     * @return
     */
    public List getOMSRejectionReport(OMSOrderVO omsOrderVO) {
        ResultSet resultset1 = new ResultSet();
        ResultSet Header = new ResultSet();
        List resultlist = new ArrayList();
        StringBuilder queryStringBuilder = new StringBuilder("defaultDB");
        queryStringBuilder.append((char) 30).append("FLIP_FE_REPORT").append(DOT).append("FLIP_SPGETOMSORDERLOG")
                .append((char) 29).append("P_APP")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_LOGINUSERCODE")
                .append((char) 31).append(omsOrderVO.getOmsUserid().toUpperCase())//login usercode
                .append((char) 30).append("P_ORDERINGUSERCODE")
                .append((char) 31).append(omsOrderVO.getOmsUserid().toUpperCase())//ordering usercode
                .append((char) 30).append("P_VENUECODE")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_ENTERINGUSERCODE")
                .append((char) 31).append(omsOrderVO.getOmsUserid().toUpperCase()) //entering usercode
                .append((char) 30).append("P_TYPE")
                .append((char) 31).append("OMSREJECTED")
                .append((char) 30).append("P_SECURITYCODE")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_BUYSELL")
                .append((char) 31).append(0)
                .append((char) 30).append("P_LANGID")
                .append((char) 31).append(0)
                .append((char) 30).append("P_DEALERROLEID")
                .append((char) 31).append(omsOrderVO.getOmsRoleId())//oms role id
                .append((char) 30).append("P_MISC1")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_MISC2")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_MISC3")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_FROMDATE")
                .append((char) 31).append(SPACE)
                .append((char) 30).append("P_TODATE")
                .append((char) 31).append(SPACE)
                .append((char) 30);

        String queryString = queryStringBuilder.toString();
        OMSGeneralReportRequestDTO oMSGeneralReportRequestDTO;
        oMSGeneralReportRequestDTO = (OMSGeneralReportRequestDTO) OMSDTOFactory.getInstance().getOMSGeneralQueryRequestDTO();
        oMSGeneralReportRequestDTO.setMethodName(53);//methodName
        oMSGeneralReportRequestDTO.setInitRequest(true);
        oMSGeneralReportRequestDTO.getDTOHeader().setLoginID(omsOrderVO.getOmsUserid().toUpperCase());
        oMSGeneralReportRequestDTO.getDTOHeader().setSessionKey(omsOrderVO.getOmsUserid().toUpperCase());
        oMSGeneralReportRequestDTO.getDTOHeader().setMessageType(83);//messageType
        oMSGeneralReportRequestDTO.setRptResponseMsgType(RptResponseMsgType.XML_MESSAGE.value());
        oMSGeneralReportRequestDTO.setGeneralReportStr(queryString);
        try {
            LOGGER.log(Level.INFO, "*** GET-OMS-REJECTION-REGUEST-SENT ***[**{0} **]", oMSGeneralReportRequestDTO);
            ReportResponseDTO objResponseDTO = (ReportResponseDTO) omsOrderVO.getOmsServices().getGeneralReport(oMSGeneralReportRequestDTO);
            Map m = new HashMap();
            m.put("errorCode", "" + objResponseDTO.getDTOHeader().getErrorCode());
            Header = setRptHeader(m);
            resultlist.add(0, Header);
            if (objResponseDTO.getDTOHeader().getErrorCode() == OMS_ERROR_CODE) {
                LOGGER.log(Level.INFO, "*** GET-OMS-REJECTION-REGUEST-RESPONSE-DTO ***[**{0} **]", objResponseDTO);
                resultset1 = objResponseDTO.getOutput().getResultSet("ResultSet1");
            } else {
                LOGGER.log(Level.WARNING, "GET-OMS-REJECTION-REGUEST-RESPONSE-DTO-ERROR :-{0}", objResponseDTO.getDTOHeader().getErrorCode());
            }
            resultlist.add(1, resultset1);
        } catch (FrontEndException ex) {
            LOGGER.log(Level.INFO, "GET-OMS-REJECTION-REGUEST-RESPONSE-EXCEPTION", ex);
        } catch (MapperException ex) {
            LOGGER.log(Level.INFO, "GET-OMS-REJECTION-REGUEST-RESPONSE-EXCEPTION", ex);
        }
        return resultlist;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveSellPortfolioDetails(List<PortfolioSecurityVO> filteredSecurityList, OMSOrderVO omsOrderVO) {
        for (PortfolioSecurityVO portfolioSecurityVO : filteredSecurityList) {
            AddRedeemLogTb addRedeemLogTb = new AddRedeemLogTb();
            CustomerPortfolioTb customerPortfolioTb = new CustomerPortfolioTb();
            customerPortfolioTb.setCustomerPortfolioId(portfolioSecurityVO.getCustomerPortfolioId());
            addRedeemLogTb.setAssetclass(portfolioSecurityVO.getAssetClassId());
            addRedeemLogTb.setCustomerPortfolioTb(customerPortfolioTb);
            addRedeemLogTb.setDatetime(new Date());
            addRedeemLogTb.setOmsId(omsOrderVO.getOmsUserid());
            addRedeemLogTb.setPrice(BigDecimal.valueOf(portfolioSecurityVO.getCurrentPrice()));
            addRedeemLogTb.setSecurityCode(portfolioSecurityVO.getSecurityCode());
            addRedeemLogTb.setVenue(portfolioSecurityVO.getVenueCode());
            addRedeemLogTb.setUnits(BigDecimal.valueOf(portfolioSecurityVO.getTransactUnits()));
            addRedeemLogTb.setVolume(BigDecimal.valueOf(portfolioSecurityVO.getTransactUnits() * portfolioSecurityVO.getCurrentPrice()));
            rebalancePortfolioDAO.saveSellPortfolioDetails(addRedeemLogTb);
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveCustomerBP(String omsLoginId, float bp) {
        rebalancePortfolioDAO.saveCustomerBP(omsLoginId, bp);
    }

    private class ComparatorImpl implements Comparator<CustomerPortfolioDetailsTb> {

        public int compare(CustomerPortfolioDetailsTb o1, CustomerPortfolioDetailsTb o2) {
            return o1.getMasterAssetTb().getAssetOrder().compareTo(o2.getMasterAssetTb().getAssetOrder());
        }
    }

    public boolean checkorderTimeForMutualFund(PortfolioSecurityVO portfolioSecurityVO) {
        boolean order_timeout_flag = false;
        Calendar cal = Calendar.getInstance();
        String datetime = DateUtil.dateToString(cal.getTime(), HH_MM);//TimeWed Jan 18 15:08:01 IST 2017
        String[] currentTime = datetime.split(":");
        Integer time_hour = Integer.parseInt(currentTime[0]);
        Integer time_minute = Integer.parseInt(currentTime[1]);
        if (portfolioSecurityVO.getVenueCode().equalsIgnoreCase(NSEMF) && time_hour >= 15) {
            order_timeout_flag = true;
        } else {
            order_timeout_flag = false;
        }
        return order_timeout_flag;
    }
}
