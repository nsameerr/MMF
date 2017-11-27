/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * created by 07662
 */
package com.gtl.mmf.bao.impl;

import com.git.mds.common.MDSMsg;
import com.gtl.mmf.bao.IEODPortfolioReturnsBAO;
import com.gtl.mmf.dao.IEODPortfolioReturnsDAO;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerPortfolioSecuritiesTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.CustomerTwrPortfolioReturnTb;
import com.gtl.mmf.entity.RecomendedCustomerPortfolioSecuritiesTb;
import com.gtl.mmf.service.util.ClientPortfolioReturnsRunnable;
import com.gtl.mmf.service.util.ClientPFPerformanceUpdateRunnable;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import com.gtl.mmf.service.util.IConstants;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.MarketDataUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.RecmdPFPerformanceUpdateRunnable;
import com.gtl.mmf.service.util.ServiceThreadUtil;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class EODPortfolioReturnsBAOImpl implements IEODPortfolioReturnsBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.EODPortfolioReturnsBAOImpl");
    private static final String ADVISOR_NOTIFICATION_PERF_FEE = "Your mmf performance fee for investor %s is %s .";
    private static final String INVESTOR_NOTIFICATION_PERF_FEE = "Your mmf performance fee for this period is %s";
    private static final String INVESTOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for investor %s is %s .";
    private static final String ADVISOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for this period is %s";
    private static final String CLOSE_INDEX = "CLOSE_INDEX";
    public static final String[] TIME_PERIOD = new String[]{"5", "30", "90", "180", "ytd", "365", "1825", "3650", "0"};
    private static final String YEAR_TO_START_DATE = "ytd";
    @Autowired
    private IEODPortfolioReturnsDAO eodPortfolioReturnsDAO;

    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    /**
     * This method is used to load all customer portfolios for calculating
     * portfolio returns
     *
     * @return list objects which contain all customers portfolios and Job
     * scheduled that are completed
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Object> loadAllClientPortfolio() throws Exception {
        List<Object> resultList = new ArrayList<Object>();
        try {
            List<Object> responeList = eodPortfolioReturnsDAO.getAllClientPortfolioDetails();
            resultList.add((List<CustomerPortfolioTb>) responeList.get(ZERO));
            resultList.add((Date) responeList.get(ONE));
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return resultList;
    }

    /**
     * This method partition the list of customer portfolios and then send for
     * portfolio calculation
     *
     * @param customerPortfolioTbList - Contain all the customer portfolios
     * @param eodDate
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void buildClientPortfolioReturnThread(List<CustomerPortfolioTb> customerPortfolioTbList, Date eodDate) {
        try {
            Integer thredPollSize = ServiceThreadUtil.getPartitionedListSize(Long.valueOf(customerPortfolioTbList.size()));
            ExecutorService taskExecutor = Executors.newFixedThreadPool(thredPollSize);
            List<List<Object>> partitionedList = ServiceThreadUtil.getPartitionedList(thredPollSize);

            //Partitining list of customer portfolio 
            for (CustomerPortfolioTb customerPortfolioTb : customerPortfolioTbList) {
                Integer index = customerPortfolioTbList.indexOf(customerPortfolioTb);
                List<Object> customerPortfolioTbs = partitionedList.get(index % thredPollSize);
                customerPortfolioTbs.add(customerPortfolioTb);
            }
            for (List<Object> customerPortfolioTbs : partitionedList) {
                ClientPortfolioReturnsRunnable clientPortfolioReturnsRunnable = new ClientPortfolioReturnsRunnable();
                clientPortfolioReturnsRunnable.setCustomerPortfolioTbList(customerPortfolioTbs);
                clientPortfolioReturnsRunnable.setEodDate(eodDate);
                clientPortfolioReturnsRunnable.setThreadname("Thread Number:-" + partitionedList.indexOf(customerPortfolioTbs));
                taskExecutor.execute(clientPortfolioReturnsRunnable);
            }
            taskExecutor.shutdown();
            boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (awaitTermination) {
                LOGGER.info("EOD Compute customer portfolio returns - Executor terminated and customer portfolio returns computed.");
            } else {
                LOGGER.severe("EOD Compute customer portfolio returns - Executor timeout elapsed before termination.");
            }
        } catch (InterruptedException ex) {
            ExceptionLogUtil.logError("Client portfolio returns calculation task failed", ex);
            ExceptionLogUtil.mailError("Client portfolio returns calculation task failed", ex, "Please check db for more details");
            exceptionLogDAO.logErrorToDb("Client portfolio returns calculation task failed", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * This method calls for the calculation of Client portfolio returns
     * 1.Portfolio Returns Calculation 2.recomended customer Portfolio Returns
     * 3.Asset Class Portfolio Returns Calculation 4.Portfolio Securites Returns
     * Calculation
     *
     * @param customerPortfolioTb
     * @param eODdate
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void calculteClientPortfolioReturns(CustomerPortfolioTb customerPortfolioTb, String eODdate) {
        String CurrentDate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
        eodPortfolioReturnsDAO.calculateClientPortfolioReturns(customerPortfolioTb, eODdate);
    }

    /**
     * This method calls for the calculation of recommended portfolio returns 1.
     * Master Portfolio Returns Calculation 2. Master Portfolio Assets Class
     * Returns Calculation 3. Master Portfolio Securities Returns Calculation
     *
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void calculateRecommendedPortfolioReturns() throws Exception {
        try {
            String eODdate = eodPortfolioReturnsDAO.getEODDate() == null ? null
                    : DateUtil.dateToString(eodPortfolioReturnsDAO.getEODDate(), YYYY_MM_DD_HH_MM_SS);
            String CurrentDate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
            eodPortfolioReturnsDAO.calculateREcommendedPortfolioRerturns(eODdate, CurrentDate);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }

    /**
     * This Method is used to for updating the performance of investor
     * portfolio. Investor portfolio 90 days returns and 1 year returns returns
     * 90 day =((closeValue/startValue90Days)-1)*100; returns 1 day =
     * ((closeValue/startValue1Year)-1)*100;
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void computeCustomerPFPerformance() {
//    public void computeCustomerPFPerformance(ThreadPoolTaskExecutor taskExecutor) {
        try {
            List<Integer> allCustomerPortfolioIds = eodPortfolioReturnsDAO.getAllCustomerPortfolio();
            int partitionedListSize = ServiceThreadUtil.getPartitionedListSize(Long.valueOf(allCustomerPortfolioIds.size()));
            ExecutorService taskExecutor = Executors.newFixedThreadPool(partitionedListSize);
            List<List<Object>> partitionedList = ServiceThreadUtil.getPartitionedList(partitionedListSize);
            for (Integer portfolioId : allCustomerPortfolioIds) {
                int index = allCustomerPortfolioIds.indexOf(portfolioId);
                List<Object> customerPortfolios = partitionedList.get(index % partitionedListSize);
                customerPortfolios.add(portfolioId);
            }
            for (List<Object> customerPortfolioIds : partitionedList) {
                taskExecutor.execute(
                        new ClientPFPerformanceUpdateRunnable("ClientPFPerfomanceThread_" + partitionedList.indexOf(customerPortfolioIds), customerPortfolioIds));//
            }
            taskExecutor.shutdown();
            boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (awaitTermination) {
                LOGGER.info("EOD Compute customer portfolio performance - Executor terminated and customer portfolio performance computed.");
            } else {
                LOGGER.severe("EOD Compute customer portfolio performance - Executor timeout elapsed before termination.");
            }
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "EOD Customer portfolios performance failed - interrepted while waiting for execution. : {0}", StackTraceWriter.getStackTrace(ex));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD Customer portfolio performance starting failed {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    /**
     * This method is used to for updating the performance of portfolios created
     * by advisor. # Advisor portfolio 90 days returns and 1 year returns
     * returns 90 day = ((closeValue/startValue90Days)-1)*100; returns 1 day =
     * ((closeValue/startValue1Year)-1)*100;
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void computeRecmdPFPerformance() {
        try {
            List<Integer> recommededPortfolios = eodPortfolioReturnsDAO.getRecommededPortfolios();
            int partitionedListSize = ServiceThreadUtil.getPartitionedListSize(Long.valueOf(recommededPortfolios.size()));
            ExecutorService taskExecutor = Executors.newFixedThreadPool(partitionedListSize);
            List<List<Object>> partitionedList = ServiceThreadUtil.getPartitionedList(partitionedListSize);
            for (Integer portfolioId : recommededPortfolios) {
                int index = recommededPortfolios.indexOf(portfolioId);
                List<Object> portfolios = partitionedList.get(index % partitionedListSize);
                portfolios.add(portfolioId);
            }
            for (List<Object> portfolioIds : partitionedList) {
                taskExecutor.execute(
                        new RecmdPFPerformanceUpdateRunnable("RecmdPFPerfomanceThread_" + partitionedList.indexOf(portfolioIds), portfolioIds));
            }
            taskExecutor.shutdown();
            boolean awaitTermination = taskExecutor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            if (awaitTermination) {
                LOGGER.info("EOD Compute recmd portfolios performance - Executor terminated and recmd portfolio performance computed.");
            } else {
                LOGGER.severe("EOD Compute recmd portfolios performance - Executor timeout elapsed before termination.");
            }
        } catch (InterruptedException ex) {
            LOGGER.log(Level.SEVERE, "EOD Recmd portfolios performance failed - interrepted while waiting for execution. : {0}", StackTraceWriter.getStackTrace(ex));
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD Recmd portfolios performance failed : {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    /**
     * Calculating customer portfolio performance 1. Calculate 90 days return
     * :((closeValue/startValue90Days)-1)*100(INTERVAL 90 days) 2. Calculate 1
     * year return :((closeValue/startValue1Year)-1)*100 (INTERVAL 1 year)
     *
     * @param customerPrtfolioId
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateCustomerPFPerformance(Integer customerPrtfolioId) throws Exception {
        eodPortfolioReturnsDAO.updateCustomerPFPerformance(customerPrtfolioId);
    }

    /**
     * Calculating portfolio performance 1. Calculate 90 days return
     * :((closeValue/startValue90Days)-1)*100(INTERVAL 90 days) 2. Calculate 1
     * year return :((closeValue/startValue1Year)-1)*100 (INTERVAL 1 year)
     *
     * @param portfolioId
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateRecommededPFPerformance(Integer portfolioId) throws Exception {
        eodPortfolioReturnsDAO.updateRecmdPFPerformance(portfolioId);
    }

    /**
     * Calculating Benchmark performance 1. Calculate 90 days return
     * :((closeValue/startValue90Days)-1)*100(INTERVAL 90 days) 2. Calculate 1
     * year return :((closeValue/startValue1Year)-1)*100 (INTERVAL 1 year)
     *
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateBenchmarkPerformance() {
        try {
            eodPortfolioReturnsDAO.updateBenchmarkPerformance();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "EOD benchmark performance updation failed - Exception : {0}", StackTraceWriter.getStackTrace(e));
        }
    }

    /**
     * This method selects relations that need to pay management FEE. calculated
     * on INTERVAL of 3 MONTH in contract period
     *
     * @return list of integer which contains the relation ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Integer> getRelationsToCalculateMgmtFee() {
        Date eODdate = eodPortfolioReturnsDAO.getEODDate() == null ? null
                : eodPortfolioReturnsDAO.getEODDate();
        return eodPortfolioReturnsDAO.getRelationsToCalculateMgmtFee(new Date());
    }

    /**
     * This method selects relations that need to pay performance FEE.
     * calculated on INTERVAL can be 3,6,12 MONTH in contract period
     *
     * @return list of integer which contains the relation ID
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<Integer> getRelationsToCalculatePerfFee() {
        Date eODdate = eodPortfolioReturnsDAO.getEODDate() == null ? null
                : eodPortfolioReturnsDAO.getEODDate();
        return eodPortfolioReturnsDAO.getRelationsToCalculatePerfFee(eODdate);
    }

    /**
     * This method is used to call the calculation for Variable type management
     * fee/Fixed type management fee MgmtFee = CustomerPortfolioValue *
     * VariableFeeRate / 100.0;
     *
     * @param relationId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateManagementFeeOfRelation(Integer relationId) {
        Date currentDate = new Date();
        Date ecsPaydate = DateUtil.getECSPayDate();
        //Upadeting Variable type management fee/Fixed type management fee
        Object[] updateManagementFeeOfRelation = eodPortfolioReturnsDAO.updateManagementFeeOfRelation(relationId, currentDate, ecsPaydate);
        Double mgmtFee = ((BigDecimal) updateManagementFeeOfRelation[0]).doubleValue();
        Integer advisorId = (Integer) updateManagementFeeOfRelation[1];
        String advisorName = (String) updateManagementFeeOfRelation[2];
        String advisorEmail = (String) updateManagementFeeOfRelation[3];
        Integer customerId = (Integer) updateManagementFeeOfRelation[4];
        String customerName = (String) updateManagementFeeOfRelation[5];
        String customerEmail = (String) updateManagementFeeOfRelation[6];
        String advisorNotification = String.format(ADVISOR_NOTIFICATION_MGMT_FEE, customerName, mgmtFee.doubleValue());
        String customerNotification = String.format(INVESTOR_NOTIFICATION_MGMT_FEE, customerName, mgmtFee);
        this.sentMailOnMMFFee(advisorName, advisorEmail, advisorNotification);
        this.sentMailOnMMFFee(customerName, customerEmail, customerNotification);
    }

    /**
     * This method is used to call the calculation for Performance fee
     *
     * @param relationId
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updatePerformanceFeeOfRelation(Integer relationId) {
        Date currentDate = new Date();
        Date ecsPaydate = DateUtil.getECSPayDate();
        Object[] perfFeeUpdateDetails = eodPortfolioReturnsDAO.updatePerformanceFeeOfRelation(relationId, currentDate, ecsPaydate);
        Double performanceFee = ((BigDecimal) perfFeeUpdateDetails[0]).doubleValue();
        Integer relatioId = (Integer) perfFeeUpdateDetails[1];
        Integer advisorId = (Integer) perfFeeUpdateDetails[2];
        String advisorName = (String) perfFeeUpdateDetails[3];
        String advisorEmail = (String) perfFeeUpdateDetails[4];
        Integer customerId = (Integer) perfFeeUpdateDetails[5];
        String customerName = (String) perfFeeUpdateDetails[6];
        String customerEmail = (String) perfFeeUpdateDetails[7];
        String advisorNotification = String.format(ADVISOR_NOTIFICATION_PERF_FEE, customerName, performanceFee);
        String customerNotification = String.format(INVESTOR_NOTIFICATION_PERF_FEE, performanceFee);
        this.sentMailOnMMFFee(advisorName, advisorEmail, advisorNotification);
        this.sentMailOnMMFFee(customerName, customerEmail, customerNotification);
    }

    private void sentMailOnMMFFee(String firstName, String email, String notificationMsg) {
        try {
            MailUtil.getInstance().sendNotificationMail(firstName, email, notificationMsg);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Mail sending on mmf fee calculation failed to {0} - Exception : {1}", new Object[]{email, StackTraceWriter.getStackTrace(ex)});
        }
    }

    /**
     * Calculating BenchMark Unit value for showing benchmark in the performance
     * graph Benchmark unit = allocated_Fund/Current_benchmark_index In case of
     * Add/reduce 1:Add:Benchmark unit = Current_Benchmark_unit+(Fund
     * _added/Current_benchmark_index) 2:Reduce:Benchmark unit =
     * Current_Benchmark_unit+(Fund _added/Current_benchmark_index)
     *
     * @param customerPortfolioTb
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void calculteBenchmarkUnitValue(CustomerPortfolioTb customerPortfolioTb, Date eodDate) { //eoddate
//        List<Object[]> addRedeemLogTbList = eodPortfolioReturnsDAO.getAddOrReedemStatusForCustomer(customerPortfolioTb.getCustomerPortfolioId(), eodDate); //eoddate
        Double benchmarkUnit = customerPortfolioTb.getBenchmarkUnit() == null ? ZERO_POINT_ZERO : customerPortfolioTb.getBenchmarkUnit().doubleValue();
//        Boolean omsEnabled = Boolean.parseBoolean(PropertiesLoader.getPropertiesValue(MMF_OMS_ENABLED));
        Double current_index = ZEROD;
        Double ammount = customerPortfolioTb.getAllocatedFund();
//        String venue = eodPortfolioReturnsDAO.getVenueName(customerPortfolioTb.getCustomerPortfolioId());
        MDSMsg mdsResponse = MarketDataUtil.getBenchmarkDetails(customerPortfolioTb.getVenueName());
        if (mdsResponse != null) {
            current_index = mdsResponse.getField(CLOSE_INDEX) == null ? ZERO : mdsResponse.getField(CLOSE_INDEX).doubleValue();
        }
        BigDecimal forAmount;
        if (current_index != ZERO) {
            if (customerPortfolioTb.getPortfolioAssigned() != null && customerPortfolioTb.getPortfolioAssigned().equals(eodDate)) { //eoddate
                benchmarkUnit = ammount / current_index;
            }
            if (customerPortfolioTb.getCashFlowDflag()) {
                List<CashFlowTb> response = eodPortfolioReturnsDAO.getPayInOut(customerPortfolioTb.getOmsLoginId(), eodDate);
                if (response != null && !response.isEmpty()) {
                    for (CashFlowTb cash : response) {
                        Double unit = ZEROD;
                        if (cash.getPayIn() != null) {
                            unit = cash.getPayIn().doubleValue() / current_index;
                            benchmarkUnit = benchmarkUnit + unit;
                        } else if (cash.getPayOut() != null) {
                            unit = Math.abs(cash.getPayOut().doubleValue()) / current_index;
                            benchmarkUnit = benchmarkUnit - unit;
                        }

                    }
                }
            }
//            if (!addRedeemLogTbList.isEmpty()) {
//                Double unit = ZEROD;
//                for (Object[] addReedem : addRedeemLogTbList) {
//
//                    if (!(Boolean) addReedem[ZERO] && !omsEnabled) {
//
//                        //Getting transaction amount when sell order is placed.
//                        forAmount = eodPortfolioReturnsDAO.getTransactionAmount(customerPortfolioTb.getMasterCustomerTb().getCustomerId(), new Date());
//                        unit = forAmount.doubleValue() / current_index;
//                        eodPortfolioReturnsDAO.updateReedemAmount(customerPortfolioTb.getCustomerPortfolioId(), new Date(), forAmount);
//                    } else {
//                        forAmount = (BigDecimal) addReedem[1];
//                        unit = forAmount.doubleValue() / current_index;
//                    }
//                    if ((Boolean) addReedem[ZERO]) {
//                        benchmarkUnit = benchmarkUnit + unit;
//                    } else {
//                        benchmarkUnit = benchmarkUnit - unit;
//                    }
//                }
//            }

            // Setting Benchmark unit when running eod after portfolio is assigned or benchmark unit value is zero
            if (benchmarkUnit < ONE) {
                benchmarkUnit = ammount / current_index;
            }
        }
        customerPortfolioTb.setBenchmarkUnit(BigDecimal.valueOf(benchmarkUnit));
        eodPortfolioReturnsDAO.updateBenchmarkUnit(customerPortfolioTb);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateRecomendedCustomerPortfolio(CustomerPortfolioTb customerPortfolioTb) {
        List<Object> responseList = eodPortfolioReturnsDAO.getRecomendedPortfolioSecuritiesTB(customerPortfolioTb);
        List<RecomendedCustomerPortfolioSecuritiesTb> portfolioSecuritiesList
                = (List<RecomendedCustomerPortfolioSecuritiesTb>) responseList.get(0);
//        int minAum = (Integer)responseList.get(1);
        List<RecomendedCustomerPortfolioSecuritiesTb> securityList
                = new ArrayList<RecomendedCustomerPortfolioSecuritiesTb>();
        boolean isCashFlow = customerPortfolioTb.getCashFlowDflag() != null ? customerPortfolioTb.getCashFlowDflag() : false;
        for (RecomendedCustomerPortfolioSecuritiesTb portfolioSecuritiesTb : portfolioSecuritiesList) {
            BigDecimal newAllocation = portfolioSecuritiesTb.getPortfolioSecuritiesTb().getNewAllocation();
            BigDecimal currentPrice = portfolioSecuritiesTb.getPortfolioSecuritiesTb().getCurrentPrice();
            // calculating required_units only if there is a cash flow in/out from customer account
            if (currentPrice.doubleValue() != ZERO_POINT_ZERO && isCashFlow) {
                double newUnits = ((Double) (newAllocation.doubleValue() / 100)
                        * customerPortfolioTb.getAllocatedFund()) / currentPrice.doubleValue();
                portfolioSecuritiesTb.setRequiredUnits((new BigDecimal(newUnits)).setScale(2, RoundingMode.FLOOR));
            }
            portfolioSecuritiesTb.setCurrentPrice(currentPrice.floatValue());
            portfolioSecuritiesTb.setYesterDayUnitCount(portfolioSecuritiesTb.getRequiredUnits());
            securityList.add(portfolioSecuritiesTb);
        }

        eodPortfolioReturnsDAO.upadetRecomendedCustomerPortfolio(securityList, isCashFlow, customerPortfolioTb.getCustomerPortfolioId());
    }

    /**
     * Logging daily portfolio oppening and closing value
     *
     * @param customerPortfolioTb
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void eodClientPortflioDailyOpeningClosingCalculation(CustomerPortfolioTb customerPortfolioTb, Date eodDate) {
        eodPortfolioReturnsDAO.eodClientPortflioDailyOpeningClosingCalculation(customerPortfolioTb.getCustomerPortfolioId(), eodDate);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void updateCustomerPortfolioSecurities(CustomerPortfolioTb customerPortfolioTb) {
        Random t = new Random();
        List<CustomerPortfolioSecuritiesTb> cSecurityList
                = new ArrayList<CustomerPortfolioSecuritiesTb>();
        List<CustomerPortfolioSecuritiesTb> securityListCustomer
                = eodPortfolioReturnsDAO.getCustomerPortfolioSecuritiesTB(customerPortfolioTb.getCustomerPortfolioId());
        for (CustomerPortfolioSecuritiesTb cPportfolioSecuritiesTb : securityListCustomer) {
            BigDecimal currentPrice = cPportfolioSecuritiesTb.getPortfolioSecuritiesTb().getCurrentPrice();
            cPportfolioSecuritiesTb.setCurrentPrice(currentPrice.floatValue());
            //Testing for sub return with price changed 
//            if (t.nextInt(20) > 10) {
//                cPportfolioSecuritiesTb.setCurrentPrice(currentPrice.floatValue() - 3);
//            } else {
//                cPportfolioSecuritiesTb.setCurrentPrice(currentPrice.floatValue() + t.nextInt(20));
//            }
            //end of test
            cPportfolioSecuritiesTb.setYesterDayUnitCount(cPportfolioSecuritiesTb.getExeUnits());
            cSecurityList.add(cPportfolioSecuritiesTb);
        }
        eodPortfolioReturnsDAO.upadetCustomerPortfolioSecurites(cSecurityList);
    }

    /**
     * Method written to calculate customer return for different time period.
     *
     * @param customerPortfolioTb
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void calculateReturnsForSpecifiedPeriod(CustomerPortfolioTb customerPortfolioTb) {
        String eODdate = DateUtil.dateToString(new Date(), YYYY_MM_DD_HH_MM_SS);
        List<CustomerTwrPortfolioReturnTb> customerPortfolioSecuritiesTbs
                = eodPortfolioReturnsDAO.getCustomerPortfolioTwrReturnTB(customerPortfolioTb.getCustomerPortfolioId());
        List<CustomerTwrPortfolioReturnTb> customerTwrPortfolioReturnTbsList
                = new ArrayList<CustomerTwrPortfolioReturnTb>();
        for (CustomerTwrPortfolioReturnTb twrPortfolioReturnTb : customerPortfolioSecuritiesTbs) {
            for (String period : TIME_PERIOD) {
                if (period.equalsIgnoreCase(YEAR_TO_START_DATE)) {
                    int year = Calendar.getInstance().get(Calendar.YEAR);
                    Calendar mycal = new GregorianCalendar(year, ZERO, ONE);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(new Date());
                    long difference = (mycal.getTimeInMillis() - cal.getTimeInMillis()) / (24 * 60 * 60 * 1000);
                    List<Object> responseList
                            = eodPortfolioReturnsDAO.getReurnsforSpecifiedPeriod(
                                    customerPortfolioTb,
                                    twrPortfolioReturnTb.getAssetId(), (int) Math.abs(difference), eODdate);
                    if (!responseList.isEmpty()) {
                        Object[] items = (Object[]) responseList.get(ZERO);
                        if (items != null) {
                            twrPortfolioReturnTb.setMarketValueYtd(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                            twrPortfolioReturnTb.setAssetReturnYtd(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                            List<Object> responseItems = (List<Object>) responseList.get(ONE);
                            twrPortfolioReturnTb.setRcmReturnYtd((BigDecimal) responseItems.get(ZERO));
                        }
                    }
                } else {
                    Integer day = Integer.parseInt(period);
                    List<Object> responseList
                            = eodPortfolioReturnsDAO.getReurnsforSpecifiedPeriod(
                                    customerPortfolioTb,
                                    twrPortfolioReturnTb.getAssetId(), day, eODdate);
                    if (!responseList.isEmpty()) {
                        Object[] items = (Object[]) responseList.get(ZERO);
                        List<Object> responseItems = (List<Object>) responseList.get(ONE);
                        if (items != null) {
                            switch (day) {
                                case 0:
                                    twrPortfolioReturnTb.setMarketValueAll(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturnAll(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturnAll(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 5:
                                    twrPortfolioReturnTb.setMarketValue5d(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn5d(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn5d(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 30:
                                    twrPortfolioReturnTb.setMarketValue1m(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn1m(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn1m(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 90:
                                    twrPortfolioReturnTb.setMarketValue3m(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn3m(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn3m(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 180:
                                    twrPortfolioReturnTb.setMarketValue6m(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn6m(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn6m(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 365:
                                    twrPortfolioReturnTb.setMarketValue1y(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn1y(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn1y(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 1825:
                                    twrPortfolioReturnTb.setMarketValue5y(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn5y(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn5y(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                                case 3650:
                                    twrPortfolioReturnTb.setMarketValue10y(items[1] != null ? (BigDecimal) items[1] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setAssetReturn10y(items[0] != null ? (BigDecimal) items[0] : BigDecimal.ZERO);
                                    twrPortfolioReturnTb.setRcmReturn10y(responseItems.get(ZERO) != null ? (BigDecimal) responseItems.get(ZERO) : BigDecimal.ZERO);
                                    break;
                            }
                        }

                    }

                }

            }
            customerTwrPortfolioReturnTbsList.add(twrPortfolioReturnTb);
        }
        eodPortfolioReturnsDAO.updateCustomerTwrPortfolioReturnTbsList(customerTwrPortfolioReturnTbsList);
    }
}
