/**
 *
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IBODataLoaderBAO;
import com.gtl.mmf.common.JobScheduleType;
import com.gtl.mmf.common.JobType;
import com.gtl.mmf.dao.IBODataLoaderDAO;
import com.gtl.mmf.dao.IEODPortfolioReturnsDAO;
import com.gtl.mmf.dao.IExceptionLogDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.JobScheduleTb;
import com.gtl.mmf.entity.MmfDailyClientBalanceTb;
import com.gtl.mmf.entity.MmfDailyTxnSummaryTb;
import com.gtl.mmf.entity.MmfRetPortfolioSplitup;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.ExceptionLogUtil;
import com.gtl.mmf.service.util.HoliDayCalenderUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.APPLICATION_JSON;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.util.RestClient;
import com.gtl.mmf.util.StackTraceWriter;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 08237 This class is used to load transaction details from backoffice
 * database to mmf database
 */
public class BODataLoaderBAOImpl implements IBODataLoaderBAO, IConstants {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.BODataLoaderBAOImpl");

    @Autowired
    IBODataLoaderDAO dataLoaderDAO;

    @Autowired
    private IEODPortfolioReturnsDAO eodPortfolioReturnsDAO;

    @Autowired
    private IExceptionLogDAO exceptionLogDAO;

    /**
     * Transferiong Txn details from backoffice db to mmf db. 1. Change status
     * of txn in BO to INPROGRESS. 2. Selects transaction which are STAGED and
     * jobtype is TXN from BO db then entry into MMF db
     * mmf_daily_client_balance_tb and job_schedule_tb and set status to
     * INPROGRESS. 3.Change status of txn in BO to COMPLETED. 4. Once it is
     * completed change status of txn in BO to COMPLETED.
     *
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int transferTxnData() {
        return dataLoaderDAO.transferTxnData(new Date());
    }

    /**
     * Transferiong Cash balance details from backoffice db to mmf db. 1. Change
     * status of txn in BO to INPROGRESS. 2. Selects cash transaction which are
     * STAGED and jobtype is CASH from BO db then entry into MMF db
     * mmf_daily_client_balance_tb and job_schedule_tb and set status to
     * INPROGRESS. 3.Change status of txn in BO to COMPLETED. 4. Once it is
     * completed change status of txn in BO to COMPLETED.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public int transferCashbalance() {
        return dataLoaderDAO.transferCashbalance(new Date());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public boolean isJobScheduled(String type, String status) {
        int recCount = dataLoaderDAO.isJobScheduled(type, status);
        if (recCount > ZERO) {
            return true;
        }
        return false;
    }

    public boolean clearTxnAndCash(String type, String status) {
        int numRowRemoved = dataLoaderDAO.clearTxnAndCash(type, status);
        return true;
    }

    /**
     *
     * Daily transaction tranfer from BO db to MMF db. 1.Transaction data 2.Cash
     * data 3.Off day dummy data
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void transferDailyData() {
        try {
            /**
             * returns job schedule list from mmfdb and job schedule list from
             * mmf back office db;
             */
            LOGGER.log(Level.INFO, "Reading job schedule data");
            List<Object> list = dataLoaderDAO.jobScheduleList();
            List<JobScheduleTb> jobScheduleTbList = (List<JobScheduleTb>) list.get(ZERO); // job schedule list from mmfdb
            LOGGER.log(Level.INFO, "Back office data transfer service invocation starting");
            String jobtxn = callServiceToGetBOData(SERVICE_JOB_SCHEDULE);
            LOGGER.log(Level.INFO, "Convert job scedule details json to table object");
            List<JobScheduleTb> JobScheduleTxnTb = convertToListTxnJob(jobtxn);//job schedule list from back office
            LOGGER.log(Level.INFO, "Start processing each job");
            for (JobScheduleTb jobScheduleTb : JobScheduleTxnTb) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(jobScheduleTb.getScheduledate());
                cal.add(Calendar.DATE, MINUS_ONE); // just prevoius day for cheking whether previous day job completed
                LOGGER.log(Level.INFO, "Check previous day job completed");
                if (isTransferRequired(jobScheduleTbList, jobScheduleTb.getJobType(), cal.getTime())) {
                    Integer rowCount = ZERO;
                    if (jobScheduleTb.getJobType().equals(JobType.TXN.toString())) {
                        LOGGER.log(Level.INFO, "Processing job type TXN");
                        //Transfer txn summary
                        String txn = callServiceToGetBOData(SERVICE_TXN_SUMMARY);
                        List<MmfDailyTxnSummaryTb> transactionTbs = convertToListTxnSummary(txn);
                        if (transactionTbs != null && !transactionTbs.isEmpty()) {
                            LOGGER.log(Level.INFO, "Saving transaction data to mmf db");
                            dataLoaderDAO.transferTxnData(transactionTbs);
                            //To insert job TXN as completed
                            LOGGER.log(Level.INFO, "TXN data transfer completed, marking TXN job as completed");
                            dataLoaderDAO.transferOffDayDummydata(jobScheduleTb);
                            rowCount = transactionTbs.size();
                            LOGGER.log(Level.INFO, "{0} Day Transaction data transfered. Row count: {1}", new Object[]{jobScheduleTb.getScheduledate(), rowCount});
                        } else {
                            LOGGER.log(Level.INFO, "No Transaction data found to transfer");
                        }

                    } else if (jobScheduleTb.getJobType().equals(JobType.CASH.toString())
                            || jobScheduleTb.getJobType().equals(JobType.CASH_DUMMY.toString())) {
                        LOGGER.log(Level.INFO, "Processing job type CASH");
                        //Transfer cash balance
                        String cash = callServiceToGetBOData(SERVICE_CASH_BALANCE);
                        List<MmfDailyClientBalanceTb> clientBalanceTbs = convertToListClientBalance(cash);
                        if (clientBalanceTbs != null && !clientBalanceTbs.isEmpty()) {
                            LOGGER.log(Level.INFO, "Saving cash balance data to mmf db");
                            dataLoaderDAO.transferCashbalance(clientBalanceTbs);
                            //To insert job CASH as completed
                            LOGGER.log(Level.INFO, "CASH data transfer completed, marking CASH job as completed");
                            dataLoaderDAO.transferOffDayDummydata(jobScheduleTb);
                            rowCount = clientBalanceTbs.size();
                            LOGGER.log(Level.INFO, "{0} Day Client Cash balance data transfered. Row count: {1}", new Object[]{jobScheduleTb.getScheduledate(), rowCount});
                        } else {
                            LOGGER.log(Level.INFO, "No Cash Balance data found to transfer");
                        }
                    } else if (jobScheduleTb.getJobType().equals(JobType.TXN_DUMMY.toString())) {
                        LOGGER.log(Level.INFO, "Processing job type TXN_DUMMY");
                        //To insert job TXN_DUMMY as completed
                        LOGGER.log(Level.INFO, "TXN_DUMMY data transfer completed, marking TXN_DUMMY job as completed");
                        dataLoaderDAO.transferOffDayDummydata(jobScheduleTb);
                    } else if (jobScheduleTb.getJobType().equals(JobType.PAYINOUT.toString())) {
                        LOGGER.log(Level.INFO, "Processing job type PAYINOUT");
                        //Transfer pay-in pay-out details
                        String cashflow = callServiceToGetBOData(SERVICE_PAYINOUT);
                        List<CashFlowTb> cashData = convertToListPayinPayout(cashflow);
                        if (cashData != null && !cashData.isEmpty()) {
                            LOGGER.log(Level.INFO, "Saving pay-in pay-out data to mmf db");
                            dataLoaderDAO.transferCashFlowData(cashData);
                            LOGGER.log(Level.INFO, "PAYINOUT data transfer completed, marking PAYINOUT job as completed");
                            dataLoaderDAO.transferOffDayDummydata(jobScheduleTb);
                            rowCount = cashData.size();
                            LOGGER.log(Level.INFO, "{0} Day Payin Payout data transfered. Row count: {1}", new Object[]{jobScheduleTb.getScheduledate(), rowCount});
                        } else {
                            LOGGER.log(Level.INFO, "No pay-in pay-out data found to transfer");
                        }
                    } else if (jobScheduleTb.getJobType().equals(JobType.POSITION.toString())) {
                        LOGGER.log(Level.INFO, "Processing job type POSITION");
                        //Transfer position details
                        String pos = callServiceToGetBOData(SERVICE_POSITION_DETAILS);
                        List<MmfRetPortfolioSplitup> posData = convertToListPosition(pos);
                        if (posData != null && !posData.isEmpty()) {
                            LOGGER.log(Level.INFO, "Saving position data to mmf db");
                            dataLoaderDAO.transferPositionData(posData);
                            LOGGER.log(Level.INFO, "POSITION data transfer completed, marking POSITION job as completed");
                            dataLoaderDAO.transferOffDayDummydata(jobScheduleTb);
                            rowCount = posData.size();
                            LOGGER.log(Level.INFO, "{0} Day DP Position data transfered. Row count: {1}", new Object[]{jobScheduleTb.getScheduledate(), rowCount});
                        } else {
                            LOGGER.log(Level.INFO, "No position data found to transfer");
                        }

                    } else {
                        break; // succeeding records should be skipped.
                    }
                }
            }
            /*for (Object[] objects : (List<Object[]>) list.get(ONE)) { // job schedule list from back office db
             Calendar cal = Calendar.getInstance();
             cal.setTime((Date) objects[3]);
             cal.add(Calendar.DATE, MINUS_ONE); // just prevoius day
             if (isTransferRequired(jobScheduleTbList, objects[1].toString(), cal.getTime())) {
             Integer rowCount = ZERO;
             if (objects[ONE].equals(JobType.TXN.toString())) {
             rowCount = dataLoaderDAO.transferTxnData((Date) objects[3]);
             LOGGER.log(Level.INFO, "{0} Day Transaction data transfered. Row count: {1}", new Object[]{(Date) objects[3], rowCount});
             } else if (objects[ONE].toString().equals(JobType.CASH.toString())) {
             rowCount = dataLoaderDAO.transferCashbalance((Date) objects[3]);
             LOGGER.log(Level.INFO, "{0} Day Cash data transfered. Row count: {1}", new Object[]{(Date) objects[3], rowCount});
             } else if (objects[ONE].toString().equals(JobType.TXN_DUMMY.toString())) {
             rowCount = dataLoaderDAO.transferOffDayDummydata((Date) objects[3]);
             LOGGER.log(Level.INFO, "{0} Day Off day dummy data transfered. Row count: {1}", new Object[]{(Date) objects[3], rowCount});
             }
             } else {
             break; // succeeding records should be skipped.
             }
             }
             // to transfer cash flow data from backoffice db 
             int no_Of_Rows = dataLoaderDAO.transferCashFlowData();
             LOGGER.log(Level.INFO, "{0} Cash Flow data transfered. Row count: {1}", new Object[]{new Date(), no_Of_Rows});*/

        } catch (Exception ex) {
            ExceptionLogUtil.logError("Transfer Daily txn and cash task from back office to mmf clone failed.", ex);
            ExceptionLogUtil.mailError("Transfer Daily txn and cash task from back office to mmf clone failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer Daily txn and cash task from back office to mmf clone failed.", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * Method to clear 5 day older data from the database
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void renameAndClear5DayOlderData() {
        try {
            String dbname = "mmfdb";
            String token = "`";
            String renameTablename_1 = ".`mmf_daily_txn_summary_tb";
            String renameTablename_2 = ".`mmf_daily_client_balance_tb";
            String renameTablename_3 = ".`mmf_ret_portfolio_splitup";
            Date maxScheduledate = eodPortfolioReturnsDAO.getEODDate();
            String oldDate = DateUtil.dateToString(maxScheduledate, YYYY_MM_DD);
            String renameQuery_1 = "RENAME TABLE ".concat(dbname).concat(renameTablename_1).concat(token).concat(" TO ").concat(dbname).concat(renameTablename_1).concat("_").concat(oldDate).concat(token);
            String renameQuery_2 = "RENAME TABLE ".concat(dbname).concat(renameTablename_2).concat(token).concat(" TO ").concat(dbname).concat(renameTablename_2).concat("_").concat(oldDate).concat(token);
            String renameQuery_3 = "RENAME TABLE ".concat(dbname).concat(renameTablename_3).concat(token).concat(" TO ").concat(dbname).concat(renameTablename_3).concat("_").concat(oldDate).concat(token);
            String dropQuery = (String) dataLoaderDAO.getDeletedTableNames(maxScheduledate, dbname);
            if (oldDate == null || oldDate.equals(EMPTY_STRING)) {
                renameQuery_1 = EMPTY_STRING;
                renameQuery_2 = EMPTY_STRING;
                renameQuery_3 = EMPTY_STRING;
            }
            if (dropQuery == null) {
                dropQuery = EMPTY_STRING;
            }
            dataLoaderDAO.renameTablesAndClear5DayOlderData(renameQuery_1, renameQuery_2, renameQuery_3, dropQuery, maxScheduledate);
        } catch (Exception ex) {
            ExceptionLogUtil.logError("Rename table and clear 5 day older data task failed", ex);
            ExceptionLogUtil.mailError("Rename table and clear 5 day older data task failed", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Rename table and clear 5 day older data task failed", StackTraceWriter.getStackTrace(ex));
        }
    }

    /**
     * Logging exceptions in the application
     *
     * @param errorMessage
     * @param exception
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void logDb(String errorMessage, String exception) {
        exceptionLogDAO.logErrorToDb(errorMessage, exception);
    }

    /**
     * This method is used to check whether any transaction submitted to OMS is
     * completed and data need to transfer from back-office DB to MMF DB
     *
     * @param jobScheduleTbList
     * @param jobType
     * @param scheduleDate
     * @return boolean true if order submitted is processed.
     * @throws Exception
     */
    private Boolean isTransferRequired(List<JobScheduleTb> jobScheduleTbList, String jobType, Date scheduleDate) throws Exception {
        try {
            if (jobScheduleTbList.isEmpty()) {
                return Boolean.TRUE;
            } else {
                Boolean isTrns = Boolean.FALSE;
                for (JobScheduleTb jobScheduleTb : jobScheduleTbList) {
                    Date mmfDate = DateUtil.stringToDate(DateUtil.dateToString(jobScheduleTb.getScheduledate(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
                    Date boDate = DateUtil.stringToDate(DateUtil.dateToString(scheduleDate, DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
                    if ((mmfDate.compareTo(boDate) == 0)) {
                        if (jobScheduleTb.getStatus().equals(JobScheduleType.COMPLETED.toString())) {
                            isTrns = Boolean.TRUE;
                        } else {
                            isTrns = Boolean.FALSE;
                        }
                        break;
                    }
                }
                return isTrns;
            }
        } catch (Exception ex) {
            ExceptionLogUtil.logError("isTransferRequired check failed.", ex);
            ExceptionLogUtil.mailError("isTransferRequired check failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("isTransferRequired check failed.", StackTraceWriter.getStackTrace(ex));
            return Boolean.FALSE;
        }
    }

    private void logErrorRecord(Exception ex) {
        ExceptionLogUtil.logError("Error occured in transferDataTask (back office to mmf clone)", ex);
        ExceptionLogUtil.mailError("Error occured in transferDailyData (back office to mmf clone)", ex, "Please check db for more deatils");
        logDb("Error occured in transferDataTask --> transferDailyData", StackTraceWriter.getStackTrace(ex));
    }

    public boolean cashTransferRequired() {
        return true;
    }

    private List<MmfDailyClientBalanceTb> convertToListClientBalance(String jsonString) {
        List<MmfDailyClientBalanceTb> list = new ArrayList<MmfDailyClientBalanceTb>();
        try {
            JSONArray clientBalane = new JSONArray(jsonString);
            boolean balAvail = clientBalane.getJSONObject(0).has("error");
            if (!balAvail) {
                for (int i = 0; i < clientBalane.length(); i++) {
                    JSONObject object = clientBalane.getJSONObject(i);
                    JSONObject idObject = object.getJSONObject("id");
                    MmfDailyClientBalanceTb mdc = new MmfDailyClientBalanceTb();
                    mdc.setEuser(object.getString("euser"));
                    mdc.setLastupdatedon(new Date());
                    mdc.setLedgerbalanec(new BigDecimal(object.getString("ledgerBalance")));
                    mdc.setTradecode(idObject.getString("tradeCode"));
                    mdc.setTrndate(DateUtil.stringToDate(idObject.getString("trandate"), MMM_DD_YYYY));
                    list.add(mdc);
                }
            }

        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer cash balance from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer cash balance from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer cash balance from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer cash balance from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer cash balance from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer cash balance from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    private List<MmfDailyTxnSummaryTb> convertToListTxnSummary(String jsonString) {
        List<MmfDailyTxnSummaryTb> list = new ArrayList<MmfDailyTxnSummaryTb>();
        try {
            JSONArray transaction = new JSONArray(jsonString);
            boolean transAvail = transaction.getJSONObject(0).has("error");
            if (!transAvail) {
                for (int i = 0; i < transaction.length(); i++) {
                    JSONObject object = transaction.getJSONObject(i);
                    JSONObject idObject = object.getJSONObject("id");
                    MmfDailyTxnSummaryTb mdc = new MmfDailyTxnSummaryTb();
                    mdc.setBrokerage(new BigDecimal(object.getString("brokerage")));
                    mdc.setBuysell(object.has("buySell") ? object.getString("buySell") : EMPTY_STRING);
                    mdc.setChannel(object.has("channel") ? object.getString("channel") : EMPTY_STRING);
                    mdc.setContract(object.has("contract") ? object.getString("contract") : EMPTY_STRING);
                    mdc.setEuser(object.has("euser") ? object.getString("euser") : EMPTY_STRING);
                    mdc.setInstrument(object.has("instrument") ? object.getString("instrument") : EMPTY_STRING);
                    mdc.setLastupdatedon(new Date());
                    mdc.setOthercharges(object.has("otherCharges") ? new BigDecimal(object.getString("otherCharges")) : BigDecimal.ZERO);
                    mdc.setPrice(object.has("price") ? new BigDecimal(object.getString("price")) : BigDecimal.ZERO);
                    mdc.setQuantity(object.has("quantity") ? new BigDecimal(object.getString("quantity")) : BigDecimal.ZERO);
                    mdc.setSecurity(object.has("security") ? object.getString("security") : EMPTY_STRING);
                    mdc.setUnits(object.has("units") ? new BigDecimal(object.getString("units")) : BigDecimal.ZERO);
                    mdc.setVolume(object.has("volume") ? new BigDecimal(object.getString("volume")) : BigDecimal.ZERO);
                    mdc.setVenueScriptCode(object.has("venue_script_code") ? object.getString("venue_script_code") : EMPTY_STRING);
                    mdc.setTrndate(DateUtil.stringToDate(idObject.getString("tranDate"), MMM_DD_YYYY));
                    mdc.setTradecode(idObject.getString("tradeCode"));
                    mdc.setOrderno(idObject.getString("orderNo"));
                    mdc.setProduct(idObject.getString("product"));
                    list.add(mdc);
                }
            }
        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer MmfDailyTxnSummaryTb from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    private List<CashFlowTb> convertToListPayinPayout(String jsonString) {
        List<CashFlowTb> list = new ArrayList<CashFlowTb>();
        try {
            JSONArray payInOut = new JSONArray(jsonString);
            boolean availability = payInOut.getJSONObject(0).has("error");
            if (!availability) {
                for (int i = 0; i < payInOut.length(); i++) {
                    JSONObject object = payInOut.getJSONObject(i);
                    JSONObject idObject = object.getJSONObject("id");
                    CashFlowTb mdc = new CashFlowTb();
                    mdc.setPayIn(object.has("payInAmt") ? new BigDecimal(object.getString("payInAmt")) : BigDecimal.ZERO);
                    mdc.setPayOut(object.has("payOutAmt") ? new BigDecimal(object.getString("payOutAmt")) : BigDecimal.ZERO);
                    mdc.setTranDate(DateUtil.stringToDate(idObject.getString("voucherdate"), MMM_DD_YYYY));
                    mdc.setTradeCode(idObject.getString("tradeCode"));
                    mdc.setProcessed(false);
                    list.add(mdc);
                }
            }

        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer PayinPayout from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer PayinPayout from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer PayinPayout from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer PayinPayout from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer PayinPayout from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer PayinPayout from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    /**
     * Method to call web service in order to access back office data
     *
     * @param url - web service URL
     * @return
     */
    private String callServiceToGetBOData(String url) {
        ClientResponse response = null;
        try {
            Boolean proxyEnabled = Boolean.parseBoolean(com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(PROXY_ENABLED));
            if (proxyEnabled) {
                System.setProperty(HTTP_PROXYHOST, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYHOST));
                System.setProperty(HTTP_PROXYPORT, com.gtl.mmf.service.util.PropertiesLoader.getPropertiesValue(HTTPS_PROXYPORT));
            }
            LOGGER.log(Level.INFO, "Calling service {0} ", PropertiesLoader.getPropertiesValue(url));
            WebResource webResource = RestClient.getClient().resource(PropertiesLoader.getPropertiesValue(url));
            response = webResource.accept(APPLICATION_JSON).get(ClientResponse.class);
            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
            }
            LOGGER.log(Level.INFO, "mmfbowebservice {0} called ", PropertiesLoader.getPropertiesValue(url));
        } catch (RuntimeException ex) {
            ExceptionLogUtil.logError("Calling sertvice to get Backoffice data failed.", ex);
            ExceptionLogUtil.mailError("Calling sertvice to get Backoffice data failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Calling sertvice to get Backoffice data failed.", StackTraceWriter.getStackTrace(ex));
        }
        return response.getEntity(String.class);

    }

    private List<JobScheduleTb> convertToListTxnJob(String jsonString) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(cal.DATE, MINUS_ONE);
        List<JobScheduleTb> list = new ArrayList<JobScheduleTb>();
        try {
            JSONArray payInOut = new JSONArray(jsonString);
            boolean payAvail = payInOut.getJSONObject(0).has("error");
            if (!payAvail) {
                for (int i = 0; i < payInOut.length(); i++) {
                    JSONObject object = payInOut.getJSONObject(i);
                    JobScheduleTb mdc = new JobScheduleTb();
                    mdc.setJobType(object.getString("jobType"));
                    mdc.setLastupdatedon(new Date());
                    String dt1 = DateUtil.formatDate(DateUtil.stringToDate(object.getString("scheduledDate"), MMM_DD_YYYY));
                    String dt2 = DateUtil.formatDate(cal.getTime());
                    if (dt1.compareTo(dt2) == ZERO) {
                        mdc.setScheduledate(DateUtil.stringToDate(object.getString("scheduledDate"), MMM_DD_YYYY));
                    } else {
                        mdc.setScheduledate(cal.getTime());
                    }
                    mdc.setStatus(object.getString("status"));
                    list.add(mdc);
                }
            }
        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer Job details from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer Job details from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer Job details from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer Job details from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer Job details from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer Job details from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    private List<MmfRetPortfolioSplitup> convertToListPosition(String jsonString) {
        List<MmfRetPortfolioSplitup> list = new ArrayList<MmfRetPortfolioSplitup>();
        try {
            JSONArray portfolioPosition = new JSONArray(jsonString);
            boolean posAvail = portfolioPosition.getJSONObject(0).has("error");
            if (!posAvail) {
                for (int i = 0; i < portfolioPosition.length(); i++) {
                    JSONObject object = portfolioPosition.getJSONObject(i);
                    MmfRetPortfolioSplitup mdc = new MmfRetPortfolioSplitup();
                    mdc.setAvgRate(object.has("avgRate") ? new BigDecimal(object.getString("avgRate")) : BigDecimal.ZERO);
                    mdc.setBseFinalQty(object.has("bseFinalQty") ? new BigDecimal(object.getString("bseFinalQty")) : BigDecimal.ZERO);
                    mdc.setBsePend(object.has("bsePend") ? new BigDecimal(object.getString("bsePend")) : BigDecimal.ZERO);
                    mdc.setBsePool(object.has("bsePool") ? new BigDecimal(object.getString("bsePool")) : BigDecimal.ZERO);
                    mdc.setBseSeries(object.has("bseSeries") ? object.getString("bseSeries") : EMPTY_STRING);
                    mdc.setBseSymbol(object.has("bseSymbol") ? object.getString("bseSymbol") : EMPTY_STRING);
                    mdc.setBseUnAc(object.has("bseUnAc") ? new BigDecimal(object.getString("bseUnAc")) : BigDecimal.ZERO);
                    mdc.setBsecode(object.has("bsecode") ? object.getString("bsecode") : EMPTY_STRING);
                    mdc.setClientid(object.has("clientid") ? Integer.parseInt(object.getString("clientid")) : ZERO);
                    mdc.setDpHolding(object.has("dpHolding") ? new BigDecimal(object.getString("dpHolding")) : BigDecimal.ZERO);
                    mdc.setExpiryDate(object.has("expiryDate") ? DateUtil.stringToDate(object.getString("expiryDate"), MMM_DD_YYYY) : null);
                    mdc.setFinanceAvgCost(object.has("financeAvgCost") ? new BigDecimal(object.getString("financeAvgCost")) : BigDecimal.ZERO);
                    mdc.setFinanceAvgRate(object.has("financeAvgRate") ? new BigDecimal(object.getString("financeAvgRate")) : BigDecimal.ZERO);
                    mdc.setFinanceStock(object.has("financestock") ? Integer.parseInt(object.getString("financestock")) : ZERO);
                    mdc.setIsin(object.has("isin") ? object.getString("isin") : EMPTY_STRING);
                    mdc.setIssueDate(object.has("issueDate") ? object.getString("issueDate") : EMPTY_STRING);
                    mdc.setItClient(object.has("itClient") ? object.getString("itClient") : EMPTY_STRING);
                    mdc.setLocation(object.has("location") ? object.getString("location") : EMPTY_STRING);
                    mdc.setMcxFinalQty(object.has("mcxFinalQty") ? new BigDecimal(object.getString("mcxFinalQty")) : BigDecimal.ZERO);
                    mdc.setMcxPend(object.has("mcxPend") ? new BigDecimal(object.getString("mcxPend")) : BigDecimal.ZERO);
                    mdc.setMcxPool(object.has("mcxPool") ? new BigDecimal(object.getString("mcxPool")) : BigDecimal.ZERO);
                    mdc.setMcxSeries(object.has("mcxSeries") ? object.getString("mcxSeries") : EMPTY_STRING);
                    mdc.setMcxUnAc(object.has("mcxUnAc") ? new BigDecimal(object.getString("mcxUnAc")) : BigDecimal.ZERO);
                    mdc.setMtfDpQty(object.has("mtfDPQty") ? new BigDecimal(object.getString("mtfDPQty")) : BigDecimal.ZERO);
                    mdc.setMultiplier(object.has("multiplier") ? new BigDecimal(object.getString("multiplier")) : BigDecimal.ZERO);
                    mdc.setNseFinalQty(object.has("nseFinalQty") ? new BigDecimal(object.getString("nseFinalQty")) : BigDecimal.ZERO);
                    mdc.setNsePend(object.has("nsePend") ? new BigDecimal(object.getString("nsePend")) : BigDecimal.ZERO);
                    mdc.setNsePool(object.has("nsePool") ? new BigDecimal(object.getString("nsePool")) : BigDecimal.ZERO);
                    mdc.setNseSeries(object.has("nseSeries") ? object.getString("nseSeries") : EMPTY_STRING);
                    mdc.setNseSymbol(object.has("nseSymbol") ? object.getString("nseSymbol") : EMPTY_STRING);
                    mdc.setNseToken(object.has("nseToken") ? Integer.parseInt(object.getString("nseToken")) : ZERO);
                    mdc.setNseUnAc(object.has("nseUnAc") ? new BigDecimal(object.getString("nseUnAc")) : BigDecimal.ZERO);
                    mdc.setPledgeQty(object.has("pledgeQty") ? new BigDecimal(object.getString("pledgeQty")) : BigDecimal.ZERO);
                    mdc.setTradeCode(object.has("tradeCode") ? object.getString("tradeCode") : EMPTY_STRING);
                    mdc.setWeightedAverageRate(object.has("weightedAverageRate") ? new BigDecimal(object.getString("weightedAverageRate")) : BigDecimal.ZERO);
                    mdc.setMcxSymbol(object.has("mcxSymbol") ? object.getString("mcxSymbol") : EMPTY_STRING);
                    mdc.setProcessedQty(BigDecimal.ZERO);
                    list.add(mdc);
                }
            }

        } catch (JSONException ex) {
            ExceptionLogUtil.logError("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", ex);
            ExceptionLogUtil.mailError("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Transfer RetPortfolioSplitup from backoffice to MMF db failed.", StackTraceWriter.getStackTrace(ex));
        }
        return list;
    }

    /**
     * Method to call service to clear back office data
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void clearBackOfficeData() {
        LOGGER.log(Level.INFO, "Back office data truncation service invocation starting");
        try {
            LOGGER.log(Level.INFO, "Reading job schedule data from MMF db");
            List<Object> list = dataLoaderDAO.jobScheduleList();
            List<JobScheduleTb> jobScheduleTbList = (List<JobScheduleTb>) list.get(ZERO);
            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());
            cal.add(Calendar.DATE, MINUS_ONE);
            Date date = DateUtil.stringToDate(DateUtil.dateToString(cal.getTime(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
            for (JobScheduleTb jobScheduleTb : jobScheduleTbList) {
                Date mmfJobDate = DateUtil.stringToDate(DateUtil.dateToString(jobScheduleTb.getScheduledate(), DD_SLASH_MM_SLASH_YYYY), DD_SLASH_MM_SLASH_YYYY);
                LOGGER.log(Level.INFO, "Check backoffice transfer completion status");
                if (mmfJobDate.compareTo(date) == 0) {
                    if (jobScheduleTb.getJobType().equals(JobType.TXN.toString())) {
                        callServiceToGetBOData(SERVICE_CLEAR_TXN);
                        LOGGER.log(Level.INFO, "back office transaction data cleared");
                    } else if (jobScheduleTb.getJobType().equals(JobType.CASH.toString())) {
                        callServiceToGetBOData(SERVICE_CLEAR_CASH);
                        LOGGER.log(Level.INFO, "back office cash balance data cleared");
                    } else if (jobScheduleTb.getJobType().equals(JobType.PAYINOUT.toString())) {
                        callServiceToGetBOData(SERVICE_CLEAR_PAYINOUT);
                        LOGGER.log(Level.INFO, "back office pay-in pay-out data cleared");
                        dataLoaderDAO.clearMmfCashFlow();
                        LOGGER.log(Level.INFO, "mmf pay-in pay-out data cleared");
                    } else if (jobScheduleTb.getJobType().equals(JobType.POSITION.toString())) {
                        callServiceToGetBOData(SERVICE_CLEAR_POSITION);
                        LOGGER.log(Level.INFO, "back office position data cleared");
                    }
                }
            }
        } catch (ParseException ex) {
            ExceptionLogUtil.logError("Clear BackOffice data task failed.", ex);
            ExceptionLogUtil.mailError("ClearBackOfficeData task failed.", ex, "Please check db log for more details.");
            exceptionLogDAO.logErrorToDb("Clear BackOffice data task failed.", StackTraceWriter.getStackTrace(ex));
        }
    }
}
