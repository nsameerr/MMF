/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IUpdateCashFlowBAO;
import com.gtl.mmf.common.EnumCustomerMappingStatus;
import com.gtl.mmf.common.EnumMandate;
import com.gtl.mmf.dao.IECSTransactionProcessingDAO;
import com.gtl.mmf.dao.IUpdateCashFlowDAO;
import com.gtl.mmf.entity.CashFlowTb;
import com.gtl.mmf.entity.CustomerAdvisorMappingTb;
import com.gtl.mmf.entity.CustomerManagementFeeTb;
import com.gtl.mmf.entity.CustomerPerformanceFeeTb;
import com.gtl.mmf.entity.CustomerPortfolioTb;
import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.FtpFileTranferUtil;
import com.gtl.mmf.service.util.IConstants;
import static com.gtl.mmf.service.util.IConstants.AMOUNT;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CUS_ID;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEBIT_RQST_NMBR;
import static com.gtl.mmf.service.util.IConstants.DEBT_FILE;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.DUE_DATE;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_DEBT;
import static com.gtl.mmf.service.util.IConstants.FTP_SERVER;
import static com.gtl.mmf.service.util.IConstants.LINE_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.MINUS_ONE;
import static com.gtl.mmf.service.util.IConstants.MMF_DEBIT_REQUEST_NUMBER;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.MMF_FILE_TRANSFER_SERVER;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ON_DEMAND_BILL_DATE;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_DEBT;
import static com.gtl.mmf.service.util.IConstants.SINGLE_SLASH;
import static com.gtl.mmf.service.util.IConstants.YYMMDDHHSS;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.MailUtil;
import com.gtl.mmf.service.util.PortfolioUtil;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 *
 * @author 09860
 */
public class IUpdateCashFlowBAOImpl implements IUpdateCashFlowBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.quartz.job.IUpdateCashFlowBAOImpl");
    @Autowired
    private IUpdateCashFlowDAO iUpdateCashFlowDAO;
    @Autowired
    private IECSTransactionProcessingDAO iECSTransactionProcessingDAO;
    private static final String ADVISOR_NOTIFICATION_PERF_FEE = "Your mmf performance fee for investor %s is %s .";
    private static final String INVESTOR_NOTIFICATION_PERF_FEE = "Your mmf performance fee for this period is %s";
//    private static final String INVESTOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for investor %s is %s .";
//    private static final String ADVISOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for this period is %s";
    private static final String INVESTOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for this period is %s";
    private static final String ADVISOR_NOTIFICATION_MGMT_FEE = "Your mmf management fee for investor %s is %s .";
    private static final String DEBT_FILE_EXTENSION = "csv";
    private static final String ADMIN_NOTIFICATION_PERF_FEE = "On demand bill generated.Please check";

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void getCashFlowDetails() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, MINUS_ONE);
        List<Object[]> cashDetails = iUpdateCashFlowDAO.loadDailyCashFlow(cal.getTime());
        if (!cashDetails.isEmpty()) {
            LOGGER.log(Level.INFO, "Updating cash flow");
            for (Object[] cashFlow : cashDetails) {
                CashFlowTb cashFlowTb = new CashFlowTb();
                cashFlowTb.setTradeCode(cashFlow[0].toString());
                cashFlowTb.setPayIn((cashFlow[1] == null ? BigDecimal.ZERO : (BigDecimal) cashFlow[1]));
                cashFlowTb.setPayOut(cashFlow[2] == null ? BigDecimal.ZERO : (BigDecimal) cashFlow[2]);
                cashFlowTb.setTranDate(cal.getTime());
                String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
                iUpdateCashFlowDAO.recalculateAllocatedFund(cashFlowTb, environment);
            }
        }

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public List<EcsDebtPaymentFileContentTb> customerFeeCalculation() {
        boolean prepareBill = false;
        boolean quarter = DateUtil.isQuarter();
        Date ecsPaydate = DateUtil.getECSPayDate();
        String file_reg_Time = DateUtil.dateToString(new Date(), YYMMDDHHSS);
        int nmbrLastUsed = Integer.valueOf(LookupDataLoader.getConfigParamList().get(MMF_DEBIT_REQUEST_NUMBER));
        int debtRqstNmbrLastUsed = nmbrLastUsed;

        //Reading customers who have mapping with advisor.
        List<Object> mappedCustomer = iUpdateCashFlowDAO.getAllCustomersHavingRelation();
        List<CustomerAdvisorMappingTb> customerMapping = (List<CustomerAdvisorMappingTb>) mappedCustomer.get(ZERO);

        //Checking FTP/SFTP server connection required.
        String ftpServer = PropertiesLoader.getPropertiesValue(MMF_FILE_TRANSFER_SERVER);
        String ftpFilePath = ftpServer.equalsIgnoreCase(FTP_SERVER)
                ? PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_DEBT)
                : PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_DEBT);

        //Debt file name for the day.
        StringBuilder filename = new StringBuilder();
        filename.append(ftpFilePath).append(SINGLE_SLASH).append(DEBT_FILE)
                .append(file_reg_Time).append(DOT).append(DEBT_FILE_EXTENSION);

        //Debt file content - header columns
//        StringBuilder fileHeader = new StringBuilder();
//        fileHeader.append(CUS_ID).append(COMA).append(DUE_DATE).append(COMA)
//                .append(AMOUNT).append(COMA).append(DEBIT_RQST_NMBR);
        //For bill preparation - 1.Quarter 2. contract start 3.Cashflow
        StringBuilder debtFileContent = new StringBuilder();
//        debtFileContent.append(fileHeader.toString()).append(System.getProperty(LINE_SEPERATOR));
        List<EcsDebtPaymentFileContentTb> ecsDebtPaymentFileList = new ArrayList<EcsDebtPaymentFileContentTb>();

        //Below line of code is used to generate on demand bill for administrator
        StringBuilder adminBillLocation = new StringBuilder();
        adminBillLocation.append(ftpFilePath).append(SINGLE_SLASH).append(DEBT_FILE)
                .append(file_reg_Time).append(DOT).append(DEBT_FILE_EXTENSION);
        StringBuilder adminDebtFileContent = new StringBuilder();
//        adminDebtFileContent.append(fileHeader.toString()).append(System.getProperty(LINE_SEPERATOR));
        List<EcsDebtPaymentFileContentTb> ecsDebtPaymentFileListForAdmin = new ArrayList<EcsDebtPaymentFileContentTb>();
        //End of on demand bill generation 

        //bill will be generated for those users whose mandate is verified and billable amount os not ZERO.
        StringBuilder fileContentForBill = new StringBuilder();
//        fileContentForBill.append(fileHeader.toString()).append(System.getProperty(LINE_SEPERATOR));
        StringBuilder fileContentForAdminBill = new StringBuilder();
//        fileContentForAdminBill.append(fileHeader.toString()).append(System.getProperty(LINE_SEPERATOR));

        LOGGER.info("######################------   Fee Calculation Started ------ #####################");
        for (CustomerAdvisorMappingTb customer : customerMapping) {
            prepareBill = false;
            if (getStatusActiveUser().contains(EnumCustomerMappingStatus.fromInt(Integer.valueOf(customer.getRelationStatus())))) {
                boolean mfee = false;
                boolean pfee = false;
                double pendingFee = 0.0;
                double pendingPFFee = 0.0;//for on demand billing calculation
                LOGGER.info("###############         ------          Loading Pending Fee Details     ------          #####################");
                List<Object> createUserListforFailedDebtPay = iUpdateCashFlowDAO.createUserListforFailedDebtPay(customer.getRelationId());
                if (!createUserListforFailedDebtPay.isEmpty()) {
                    if (createUserListforFailedDebtPay.size() > 0) {
                        //MF pending
                        List<Object> data = (List<Object>) createUserListforFailedDebtPay.get(ZERO);
                        for (Object dataList : data) {
                            EcsDebtPaymentFileContentTb convertListToECSdataTb = convertListToECSdataTb(dataList, ecsPaydate);
                            pendingFee = pendingFee + convertListToECSdataTb.getDebtAmount().doubleValue();
                            LOGGER.log(Level.INFO, "############### Loading Pending Fee Details For user [{0}] Fee [{1}]", new Object[]{convertListToECSdataTb.getRegId(), convertListToECSdataTb.getDebtAmount()});
//                            ecsDebtPaymentFileList.add(convertListToECSdataTb);
                        }

                    }

                    if (createUserListforFailedDebtPay.size() > 1) {
                        //PF pending
                        List<Object> data = (List<Object>) createUserListforFailedDebtPay.get(ONE);
                        for (Object dataList : data) {
                            EcsDebtPaymentFileContentTb convertListToECSdataTb = convertListToECSdataTb(dataList, ecsPaydate);
                            pendingFee = pendingFee + convertListToECSdataTb.getDebtAmount().doubleValue();
                            pendingPFFee = pendingPFFee + convertListToECSdataTb.getDebtAmount().doubleValue();
                            LOGGER.log(Level.INFO, "############### Loading Pending Fee Details For user [{0}] Fee [{1}]", new Object[]{convertListToECSdataTb.getRegId(), convertListToECSdataTb.getDebtAmount()});
//                            ecsDebtPaymentFileList.add(convertListToECSdataTb);
//                            ecsDebtPaymentFileListForAdmin.add(convertListToECSdataTb);
                        }
                    }

                }
                LOGGER.info("###############      ------Loading Pending Fee Details Completed-------     #####################");
                LOGGER.log(Level.INFO, "Bill calculation for user [  {0}" + "" + "  ] Relation id [  {1}  ]", new Object[]{customer.getMasterCustomerTb().getCustomerId(), customer.getRelationId()});
                Double ecsBillAmount = 0.0;
                //calculate management fee Quarterly
                String reg_id = null;
                String customerName = null;
                String contractDate = null;
                String contractTerminateDate = null;

                boolean contractStart = false;
                boolean portfolioAssigned = false;
                boolean contractTerminate = false;
                Calendar cal = Calendar.getInstance();
                cal.setTime(new Date());
                cal.add(cal.DATE, -1);
                String cDate = DateUtil.formatDate(cal.getTime());
                if(customer.getContractStart() != null){
                     contractDate = DateUtil.formatDate(customer.getContractStart());
                }
                 if (cDate.equalsIgnoreCase(contractDate)) {
                    contractStart = true;
                }
                if(customer.getContractTerminateDate() != null){
                   contractTerminateDate = DateUtil.formatDate(customer.getContractTerminateDate());  
                    System.out.println("contract terminate date"+contractTerminateDate );
                }
                 System.out.println("contract terminate date"+contractTerminateDate );
                 if( contractTerminateDate != null && contractTerminateDate.isEmpty()){
                    if(cDate.equalsIgnoreCase(contractTerminateDate)){
                    contractTerminate = true;
                } 
                }
               
                LOGGER.log(Level.INFO, "Current date [{0}] contract start date [{1}] is contract start[{2}] for relation id [{3}]",
                        new Object[]{cDate, contractDate, contractStart, customer.getRelationId()});
                CustomerPortfolioTb customerTb = iUpdateCashFlowDAO.getCustomerPortfolio(customer.getRelationId());

                Object[] updatePerformanceFeeOfRelation = null;
                if (customerTb != null && customerTb.getPortfolioAssigned() != null && new Date().compareTo(customerTb.getPortfolioAssigned()) == ONE) {
                    //calculate performance fee Dailywise
                    LOGGER.log(Level.INFO, "######################------   Performance Fee Calculation Started ------ ##################### Generate Bill [{0}]", quarter);
                    updatePerformanceFeeOfRelation = iUpdateCashFlowDAO.calculateInvestorPerformanceFee(customer);
                    portfolioAssigned = true;
                    LOGGER.info("######################------   Performance Fee Calculation Completd ------ #####################");
                    LOGGER.log(Level.INFO, "Current date [{0}] portfolio assigned date [{1}] is porfolioassigned start[{2}]", new Object[]{new Date(), customerTb.getPortfolioAssigned(), portfolioAssigned});
                }
                LOGGER.log(Level.INFO, "Current date [{0}] is porfolioassigned start[{1}]", new Object[]{new Date(), portfolioAssigned});

                LOGGER.log(Level.INFO, "Current date [{0}] contract start date [{1}] is contract start[{2}]", new Object[]{cDate, contractDate, contractStart});
                LOGGER.info("######################------   Management  Fee Calculation Started ------ #####################");
                boolean cashFlowFlag = customerTb != null ? customerTb.getCashFlowDflag() != null ? customerTb.getCashFlowDflag() : false : false;
                
                if (quarter || cashFlowFlag || contractStart || contractTerminate) {

                    LOGGER.log(Level.INFO, "Quarter flag [{0}] cashflow flag [{1}]", new Object[]{quarter, cashFlowFlag});
                    prepareBill = true;
                    Object[] updateManagementFeeOfRelation = iUpdateCashFlowDAO.calculateInvestorManagementFee(customer, ecsPaydate, quarter, customerTb);

                    Double mgmtFee = ((BigDecimal) updateManagementFeeOfRelation[0]).doubleValue();
                    ecsBillAmount = ecsBillAmount + mgmtFee;
                    Integer advisorId = (Integer) updateManagementFeeOfRelation[1];
                    String advisorName = (String) updateManagementFeeOfRelation[2];
                    String advisorEmail = (String) updateManagementFeeOfRelation[3];
                    Integer customerId = (Integer) updateManagementFeeOfRelation[4];
                    customerName = (String) updateManagementFeeOfRelation[5];
                    String customerEmail = (String) updateManagementFeeOfRelation[6];
                    reg_id = (String) updateManagementFeeOfRelation[7];
                    setDataForMMFFeeMail(advisorName, customerName, advisorEmail,
                            customerEmail, mgmtFee, true);
                    mfee = true;
                    LOGGER.log(Level.INFO, "Relation id [{0}]  Customer name [{1}]  Management fee [{2}]", new Object[]{customer.getRelationId(), customerName, mgmtFee});
                    if (quarter && portfolioAssigned) {
                        Double prfFee = ((BigDecimal) updatePerformanceFeeOfRelation[0]).doubleValue();
                        ecsBillAmount = ecsBillAmount + prfFee;
                        pfee = true;
                        LOGGER.log(Level.INFO, "Performance fee Bill generated for Customer Name [{0}] Performance Fee [{1}]", new Object[]{customerName, prfFee});
                        setDataForMMFFeeMail(advisorName, customerName, advisorEmail,
                                customerEmail, prfFee, false);
                    }

                }

                LOGGER.info("######################------   ECS Data Preparation ------ #####################");
                if (prepareBill) {
                    LOGGER.log(Level.INFO, "Prepare bill flag [{0}] for customer [{1}]", new Object[]{prepareBill, reg_id});
                    String DebtDate = DateUtil.dateToString(ecsPaydate, DD_SLASH_MM_SLASH_YYYY);
                    ecsBillAmount = pendingFee + ecsBillAmount;
                    ecsBillAmount = PortfolioUtil.roundToTwoDecimal(ecsBillAmount);
                    debtFileContent.append(reg_id)
                            .append(COMA)
                            .append(DebtDate)
                            .append(COMA)
                            .append(ecsBillAmount.toString())
                            .append(COMA)
                            .append(debtRqstNmbrLastUsed)
                            .append(System.getProperty(LINE_SEPERATOR));
                    if (customer.getMasterCustomerTb().getMasterApplicantTb().getEcsMandateStatus() == EnumMandate.VERIFIED.getValue() && ecsBillAmount > 0) {
                        debtRqstNmbrLastUsed++;
                        fileContentForBill.append(reg_id)
                                .append(COMA)
                                .append(DebtDate)
                                .append(COMA)
                                .append(ecsBillAmount.toString())
                                .append(COMA)
                                .append(debtRqstNmbrLastUsed)
                                .append(System.getProperty(LINE_SEPERATOR));
                        LOGGER.log(Level.INFO, "#####################    ECS Bill date for user Custromer Name [{0}] Reg ID [{1}] is ------ [   {2}   ]", new Object[]{customerName, reg_id, debtFileContent.toString()});
                        EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb = createEcsContantTb(reg_id, ecsPaydate, ecsBillAmount, mfee, pfee, new Date(), debtRqstNmbrLastUsed);
                        ecsDebtPaymentFileList.add(ecsDebtPaymentFileContentTb);
                    }
                }

                LOGGER.info("######################------   ECS Data Preparation Completed ------ #####################");

                LOGGER.info("######################------   Bill Preparation For Administrator------ #####################");
                LinkedHashMap<String, String> hashMap = LookupDataLoader.getMmfsettings();
                Double billAmount = 0.0;
                if (updatePerformanceFeeOfRelation != null && !prepareBill && !hashMap.isEmpty() && hashMap.containsKey(ON_DEMAND_BILL_DATE)) {
                    try {
                        String value = hashMap.get(ON_DEMAND_BILL_DATE);
                        if (StringUtils.hasText(value)) {
                            Date demandBillDate = DateUtil.stringToDate(value, dd_MM_yyyy);
                            Date today = DateUtil.stringToDate(DateUtil.dateToString(new Date(), dd_MM_yyyy), dd_MM_yyyy);
                            if (today.compareTo(demandBillDate) == 0) {
                                LOGGER.log(Level.INFO, "Bill Preparation For Administrator,Bill generation date [{0}] - for customer id [{1}]",
                                        new Object[]{demandBillDate, customer.getMasterCustomerTb().getCustomerId()});
                                String DebtDate = DateUtil.dateToString(ecsPaydate, DD_SLASH_MM_SLASH_YYYY);
                                Double fee = updatePerformanceFeeOfRelation[0] == null ? 0.0 : ((BigDecimal) updatePerformanceFeeOfRelation[0]).doubleValue();
                                billAmount = pendingPFFee + fee;
                                billAmount = PortfolioUtil.roundToTwoDecimal(billAmount);
                                String cusid = updatePerformanceFeeOfRelation[8].toString();
                                adminDebtFileContent.append(cusid)
                                        .append(COMA)
                                        .append(DebtDate)
                                        .append(COMA)
                                        .append(billAmount.toString())
                                        .append(COMA)
                                        .append(debtRqstNmbrLastUsed)
                                        .append(System.getProperty(LINE_SEPERATOR));
                                if (billAmount > 0) {
                                    debtRqstNmbrLastUsed++;
                                    fileContentForAdminBill.append(cusid)
                                            .append(COMA)
                                            .append(DebtDate)
                                            .append(COMA)
                                            .append(billAmount.toString())
                                            .append(COMA)
                                            .append(debtRqstNmbrLastUsed)
                                            .append(System.getProperty(LINE_SEPERATOR));
                                    EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb = createEcsContantTb(cusid, ecsPaydate, billAmount, false, true, new Date(), debtRqstNmbrLastUsed);
                                    ecsDebtPaymentFileListForAdmin.add(ecsDebtPaymentFileContentTb);
                                }

                            }
                        }
                    } catch (ParseException ex) {
                        LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                        break;
                    }
                }
                LOGGER.info("######################------   Bill Preparation For Administrator------ #####################");
           
            }
        }

        //For testing
        System.out.print(" debt file content -----" + debtFileContent.toString());
        System.out.print(" Admin debt file content -----" + adminDebtFileContent.toString());
        //end of testing
        if (!ecsDebtPaymentFileList.isEmpty()) {
            LOGGER.info("Complted processing Debt file");
            if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
                LOGGER.info("Starting to write file into FTP");
                FtpFileTranferUtil.writeFileToFTP(fileContentForBill.toString(), filename.toString());
                LOGGER.info("Complted  write file into FTP");
            } else {
                LOGGER.info("Starting to write file into SFTP");
                FtpFileTranferUtil.writeFileToSFTP(fileContentForBill.toString(), filename.toString());
                LOGGER.info("Complted  write file into SFTP");
            }

            LOGGER.info("Write debt file contemnt into Table");
        }

        if (!ecsDebtPaymentFileListForAdmin.isEmpty()) {
            LOGGER.info("On demand billing calculated");
            if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
                LOGGER.info("Starting to write file into FTP");
                boolean status = FtpFileTranferUtil.writeFileToFTP(fileContentForAdminBill.toString(), adminBillLocation.toString());
                LOGGER.log(Level.INFO, "Complted  write file into FTP - status [{0}]", new Object[]{status});
            } else {
                LOGGER.info("Starting to write file into SFTP");
                boolean status = FtpFileTranferUtil.writeFileToSFTP(fileContentForAdminBill.toString(), adminBillLocation.toString());
                LOGGER.log(Level.INFO, "Complted  write file into SFTP - status [{0}]", new Object[]{status});
            }

        }

        LOGGER.log(Level.INFO, "Debt file entry size [{0}]", ecsDebtPaymentFileList.size());
        if (debtRqstNmbrLastUsed > nmbrLastUsed) {
            iECSTransactionProcessingDAO.updateDebitRequestNumber(debtRqstNmbrLastUsed, MMF_DEBIT_REQUEST_NUMBER);
        }
        return ecsDebtPaymentFileList;
    }

    private EcsDebtPaymentFileContentTb convertListToECSdataTb(Object data, Date ecsPaydate) {
        EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb = null;
        if (data instanceof CustomerManagementFeeTb) {
            CustomerManagementFeeTb customerManagementFee = (CustomerManagementFeeTb) data;
            String regid = customerManagementFee.getCustomerAdvisorMappingTb().getMasterCustomerTb().getRegistrationId();
            ecsDebtPaymentFileContentTb = createEcsContantTb(regid, ecsPaydate, customerManagementFee.getMgmtFee().doubleValue(), true, false, customerManagementFee.getFeeCalculateDate(), null);
        } else if (data instanceof CustomerPerformanceFeeTb) {
            CustomerPerformanceFeeTb customerPerformanceFee = (CustomerPerformanceFeeTb) data;
            String regid = customerPerformanceFee.getCustomerAdvisorMappingTb().getMasterCustomerTb().getRegistrationId();
            ecsDebtPaymentFileContentTb = createEcsContantTb(regid, ecsPaydate, customerPerformanceFee.getPerfFee().doubleValue(), false, true, customerPerformanceFee.getFeeCalculateDate(), null);

        }
        return ecsDebtPaymentFileContentTb;

    }

    private EcsDebtPaymentFileContentTb createEcsContantTb(String reg_id, Date ecsPaydate, Double ecsBillAmount, boolean mfee, boolean pfee, Date feeCalculatedate, Integer debitRqstNmbr) {
        EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb = new EcsDebtPaymentFileContentTb();
        ecsDebtPaymentFileContentTb.setRegId(reg_id);
        ecsDebtPaymentFileContentTb.setDueDate(ecsPaydate);
        ecsDebtPaymentFileContentTb.setDebtAmount(new BigDecimal(ecsBillAmount));
        ecsDebtPaymentFileContentTb.setStatus("SUBMITTED");

        //It is not row id just indicating management/performance fee exist  or not in that day
        ecsDebtPaymentFileContentTb.setMfee(mfee);
        ecsDebtPaymentFileContentTb.setPfee(pfee);
        ecsDebtPaymentFileContentTb.setFileUploadDate(new Date());
        ecsDebtPaymentFileContentTb.setLastUpdated(new Date());
        ecsDebtPaymentFileContentTb.setFeeCalculatedate(feeCalculatedate);
        ecsDebtPaymentFileContentTb.setDebitRequestNumber(debitRqstNmbr == null ? null : debitRqstNmbr);
        return ecsDebtPaymentFileContentTb;
    }

    private void sentMailOnMMFFee(String firstName, String email, String notificationMsg) {
        try {
            MailUtil.getInstance().sendNotificationMail(firstName, email, notificationMsg);
        } catch (ClassNotFoundException ex) {
            LOGGER.log(Level.SEVERE, "Mail sending on mmf fee calculation failed to {0} - Exception : {1}", new Object[]{email, StackTraceWriter.getStackTrace(ex)});
        }
    }

    private void setDataForMMFFeeMail(String advisorName, String customerName, String advisorEmail, String customerEmail, Double fee, boolean feeType) {
        String advisorNotification;
        String customerNotification;
        if (feeType) {
            advisorNotification = String.format(ADVISOR_NOTIFICATION_MGMT_FEE, customerName, fee);
            //customerNotification = String.format(INVESTOR_NOTIFICATION_MGMT_FEE, customerName, fee);
            customerNotification = String.format(INVESTOR_NOTIFICATION_MGMT_FEE, fee);
        } else {
            advisorNotification = String.format(ADVISOR_NOTIFICATION_PERF_FEE, customerName, fee);
            customerNotification = String.format(INVESTOR_NOTIFICATION_PERF_FEE, fee);
        }
        this.sentMailOnMMFFee(advisorName, advisorEmail, advisorNotification);
        this.sentMailOnMMFFee(customerName, customerEmail, customerNotification);

    }

    private HashMap<String, Object> convertListToMap(List<Object> debtList) {
        HashMap<String, Object> debtMap = new HashMap<String, Object>();
        String reg_id = null;

        if (!debtList.isEmpty()) {
            for (Object data : debtList) {
                if (data instanceof CustomerManagementFeeTb) {
                    CustomerManagementFeeTb customerManagementFee = (CustomerManagementFeeTb) data;
                    reg_id = customerManagementFee.getRegId();
                } else if (data instanceof CustomerPerformanceFeeTb) {
                    CustomerPerformanceFeeTb customerPerformanceFee = (CustomerPerformanceFeeTb) data;
                    reg_id = customerPerformanceFee.getRegId();
                }
                debtMap.put(reg_id, data);
            }
        }
        return debtMap;

    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void saveEcsDetails(List<EcsDebtPaymentFileContentTb> customerFeeCalculation) {
        iECSTransactionProcessingDAO.insertEcsDebtFiledata(customerFeeCalculation);
    }

    private List<EnumCustomerMappingStatus> getStatusActiveUser() {
        List<EnumCustomerMappingStatus> statuses = new ArrayList<EnumCustomerMappingStatus>();
        statuses.add(EnumCustomerMappingStatus.ORDER_PLACED_IN_OMS);
        statuses.add(EnumCustomerMappingStatus.REBALANCE_INITIATED);
        statuses.add(EnumCustomerMappingStatus.IPS_ACCEPTED);
        statuses.add(EnumCustomerMappingStatus.CONTRACT_SIGNED);
        statuses.add(EnumCustomerMappingStatus.QUESTIONNAIRE_RECIEVED);
        statuses.add(EnumCustomerMappingStatus.RESPONSE_SENT);
        statuses.add(EnumCustomerMappingStatus.IPS_SHARED);
        statuses.add(EnumCustomerMappingStatus.IPS_CREATED);
        statuses.add(EnumCustomerMappingStatus.IPS_REVIEWED);
        statuses.add(EnumCustomerMappingStatus.IPS_ACCEPTED);
        statuses.add(EnumCustomerMappingStatus.REBALANCE_INITIATED);
        statuses.add(EnumCustomerMappingStatus.CONTRACT_TERMINATED);
        return statuses;
    }
}
