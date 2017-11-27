/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.bao.impl;

import com.gtl.mmf.bao.IECSTransactionProcessingBAO;
import com.gtl.mmf.common.EnumMandate;
import com.gtl.mmf.dao.IECSTransactionProcessingDAO;
import com.gtl.mmf.entity.CustomerManagementFeeTb;
import com.gtl.mmf.entity.CustomerPerformanceFeeTb;
import com.gtl.mmf.entity.EcsDebtPaymentFileContentTb;
import com.gtl.mmf.entity.EcsPaymentStatusFileTb;
import com.gtl.mmf.entity.EcsRegistrationFiledataTb;
import com.gtl.mmf.entity.EcsTransmittalSheetTb;
import com.gtl.mmf.persist.vo.EcsDebtFileReplyVo;
import com.gtl.mmf.service.util.DateUtil;
import com.gtl.mmf.service.util.FtpFileTranferUtil;
import static com.gtl.mmf.service.util.IConstants.AC_HOLDER_NAME;
import static com.gtl.mmf.service.util.IConstants.AC_NO;
import static com.gtl.mmf.service.util.IConstants.AC_TYPE;
import static com.gtl.mmf.service.util.IConstants.AMOUNT;
import static com.gtl.mmf.service.util.IConstants.A_TWO;
import static com.gtl.mmf.service.util.IConstants.BANK_NAME;
import static com.gtl.mmf.service.util.IConstants.COLON;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.CUSTOMER_ID;
import static com.gtl.mmf.service.util.IConstants.CUS_ID;
import static com.gtl.mmf.service.util.IConstants.DDMMYYYY;
import static com.gtl.mmf.service.util.IConstants.DD_SLASH_MM_SLASH_YYYY;
import static com.gtl.mmf.service.util.IConstants.DEBIT_TYPE;
import static com.gtl.mmf.service.util.IConstants.DEBT_FILE;
import static com.gtl.mmf.service.util.IConstants.DOT;
import static com.gtl.mmf.service.util.IConstants.DUE_DATE;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.END_DATE;
import static com.gtl.mmf.service.util.IConstants.FREQUENCY_HEADER;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_DEBT;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_DEBT_PAYMENT;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_REGIGISTRATION;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_TRANSMITTALSHEET;
import static com.gtl.mmf.service.util.IConstants.FTP_FILE_PATH_TRANSMITTALSHEET_BILLDESK;
import static com.gtl.mmf.service.util.IConstants.FTP_SERVER;
import static com.gtl.mmf.service.util.IConstants.HEADER;
import static com.gtl.mmf.service.util.IConstants.IFSC;
import static com.gtl.mmf.service.util.IConstants.LINE_SEPERATOR;
import static com.gtl.mmf.service.util.IConstants.LOT_NO;
import static com.gtl.mmf.service.util.IConstants.MICR;
import static com.gtl.mmf.service.util.IConstants.MMF_ENVIRONMENT_SETUP;
import static com.gtl.mmf.service.util.IConstants.MMF_FILE_TRANSFER_SERVER;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.PAYMENT_FILE;
import static com.gtl.mmf.service.util.IConstants.PAYMENT_FILE_DIRECTORY;
import static com.gtl.mmf.service.util.IConstants.PRODUCTION;
import static com.gtl.mmf.service.util.IConstants.REGISTRATION_FILE;
import static com.gtl.mmf.service.util.IConstants.REGISTRATION_FILE_DIRECTORY;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_DEBT;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_DEBTPAYMENTFILE;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_REGISTRATION;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_TRANSMITTALSHEET;
import static com.gtl.mmf.service.util.IConstants.SFTP_FILE_PATH_TRANSMITTALSHEET_BILLDESK;
import static com.gtl.mmf.service.util.IConstants.SINGLE_SLASH;
import static com.gtl.mmf.service.util.IConstants.SR_NO;
import static com.gtl.mmf.service.util.IConstants.START_DATE;
import static com.gtl.mmf.service.util.IConstants.SUBMITTED;
import static com.gtl.mmf.service.util.IConstants.TRANSMITTAL_SHEET;
import static com.gtl.mmf.service.util.IConstants.TS_FILE_DIRECTORY;
import static com.gtl.mmf.service.util.IConstants.UNTIL_CANCELED;
import static com.gtl.mmf.service.util.IConstants.YYMMDDHHSS;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import static com.gtl.mmf.service.util.IConstants.ZERO_POINT_ZERO;
import static com.gtl.mmf.service.util.IConstants.dd_MM_yyyy;
import com.gtl.mmf.service.util.LookupDataLoader;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import static org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * This class is used for the processing of ECS mandate data preparation
 *
 * @author trainee8
 */
public class ECSTransactionProcessingBAOImpl implements IECSTransactionProcessingBAO {

    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.bao.impl.ECSTransactionProcessingBAOImpl");
    @Autowired
    IECSTransactionProcessingDAO iECSTransactionProcessingDAO;
    private static final String FILE_EXTENSION = "txt";
    private static final String DEBT_FILE_EXTENSION = "csv";
    private static final String PATTERN = "([0-9][0-9])(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])(([0-1][0-9])|([2][0-3]))([0-5][0-9])([0-5][0-9])";
    private static final String EXCEL_EXTENSION = "(xls|xlsx|XLS|XLSX)$";
    private static final String TRANSMITTAL_SHEET_EXTENSION = "xlsx";
    private static final String TS_RESPONSE_FILE_PATTERN = "(0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-([1-9][0-9]{3})";
    private static final String INPUT_STREAM_IS_NULL = "INPUT_STREAM_IS_NULL";
    private static final int TO_GET_EMPTY_DATA = -1;

    /**
     * Preparing data for ECS mandate file
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void createFileForECSDebtFile() {
        LOGGER.info("Creating content for Debt file for ECS.");
        List<Object> responseList = iECSTransactionProcessingDAO.createUserListforDebtPay();
        LOGGER.info("Complete creating content for Debt file for ECS.");
        HashMap<String, Object> managementFeeMap
                = this.convertListToMap((List<Object>) responseList.get(ZERO));

        HashMap<String, Object> performanceFeeMap
                = this.convertListToMap((List<Object>) responseList.get(ONE));
        List<EcsDebtPaymentFileContentTb> ecsDebtPaymentFileList
                = new ArrayList<EcsDebtPaymentFileContentTb>();

        LOGGER.info("Starting processing Debt file");
        if (managementFeeMap.size() > ZERO || performanceFeeMap.size() > ZERO) {
            StringBuffer debtFileContent = new StringBuffer();
            String file_reg_Time = DateUtil.dateToString(new Date(), YYMMDDHHSS);
            StringBuffer fileHeader = new StringBuffer();
            fileHeader.append(CUS_ID).append(COMA).append(DUE_DATE)
                    .append(COMA).append(AMOUNT);
            StringBuffer filename = new StringBuffer();
            String ftpServer = PropertiesLoader.getPropertiesValue(MMF_FILE_TRANSFER_SERVER);
            String ftpFilePath = ftpServer.equalsIgnoreCase(FTP_SERVER)
                    ? PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_DEBT)
                    : PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_DEBT);
            filename.append(ftpFilePath).append(SINGLE_SLASH).
                    append(DEBT_FILE).append(file_reg_Time).append(DOT)
                    .append(DEBT_FILE_EXTENSION);
            debtFileContent.append(fileHeader.toString()).append(System
                    .getProperty(LINE_SEPERATOR));
            if (managementFeeMap.size() > ZERO) {
                for (Map.Entry<String, Object> entry : managementFeeMap.entrySet()) {
                    String regId = entry.getKey();
                    EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb
                            = new EcsDebtPaymentFileContentTb();
                    CustomerManagementFeeTb customerManagementFee
                            = (CustomerManagementFeeTb) entry
                            .getValue();
                    Double debtAmount = ((BigDecimal) customerManagementFee.getMgmtFee())
                            .doubleValue();
                    if (debtAmount > ZERO_POINT_ZERO) {
                        Date debt = customerManagementFee.getEcsPayDate();
                        String DebtDate = DateUtil.dateToString(debt, DD_SLASH_MM_SLASH_YYYY);
                        if (performanceFeeMap.containsKey(regId)) {
                            CustomerPerformanceFeeTb customerPerformanceFee
                                    = (CustomerPerformanceFeeTb) performanceFeeMap
                                    .get(regId);
                            debtAmount = debtAmount + ((BigDecimal) customerPerformanceFee
                                    .getPerfFee())
                                    .doubleValue();
                        }
                        debtFileContent.append(customerManagementFee.getRegId())
                                .append(COMA)
                                .append(DebtDate)
                                .append(COMA)
                                .append(debtAmount.toString())
                                .append(System.getProperty(LINE_SEPERATOR));

                        ecsDebtPaymentFileContentTb.setRegId(regId);
                        ecsDebtPaymentFileContentTb.setDueDate(debt);
                        ecsDebtPaymentFileContentTb.setDebtAmount(new BigDecimal(debtAmount));
                        ecsDebtPaymentFileContentTb.setStatus(SUBMITTED);
                        ecsDebtPaymentFileContentTb.setFileUploadDate(new Date());
                        ecsDebtPaymentFileContentTb.setLastUpdated(new Date());
                        ecsDebtPaymentFileList.add(ecsDebtPaymentFileContentTb);
                        performanceFeeMap.remove(regId);
                    }
                }
            }
            if (performanceFeeMap.size() > ZERO) {
                for (Map.Entry<String, Object> entry : performanceFeeMap.entrySet()) {
                    String regId = entry.getKey();
                    EcsDebtPaymentFileContentTb ecsDebtPaymentFileContentTb
                            = new EcsDebtPaymentFileContentTb();
                    CustomerPerformanceFeeTb customerPerformanceFee
                            = (CustomerPerformanceFeeTb) entry.getValue();
                    Double debtAmount = ((BigDecimal) customerPerformanceFee.getPerfFee())
                            .doubleValue();
                    if (debtAmount > ZERO_POINT_ZERO) {
                        Date debt = customerPerformanceFee.getEcsPayDate();
                        String DebtDate = DateUtil.dateToString(debt, DD_SLASH_MM_SLASH_YYYY);
                        debtFileContent.append(customerPerformanceFee.getRegId())
                                .append(COMA)
                                .append(DebtDate)
                                .append(COMA)
                                .append(debtAmount.toString())
                                .append(System.getProperty(LINE_SEPERATOR));
                        ecsDebtPaymentFileContentTb.setRegId(regId);
                        ecsDebtPaymentFileContentTb.setDueDate(debt);
                        ecsDebtPaymentFileContentTb.setStatus(SUBMITTED);
                        ecsDebtPaymentFileContentTb.setDebtAmount(new BigDecimal(debtAmount));
                        ecsDebtPaymentFileContentTb.setFileUploadDate(new Date());
                        ecsDebtPaymentFileContentTb.setLastUpdated(new Date());
                        ecsDebtPaymentFileList.add(ecsDebtPaymentFileContentTb);
                    }
                }
            }
            LOGGER.info("Complted processing Debt file");
            if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
                LOGGER.info("Starting to write file into FTP");
                FtpFileTranferUtil.writeFileToFTP(debtFileContent.toString(), filename.toString());
                LOGGER.info("Complted  write file into FTP");
            } else {
                LOGGER.info("Starting to write file into SFTP");
                FtpFileTranferUtil.writeFileToSFTP(debtFileContent.toString(), filename.toString());
                LOGGER.info("Complted  write file into SFTP");
            }
            LOGGER.info("Write debt file contemnt into Table");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void deleteDataFromEcsRegistrationDataTable() {
        iECSTransactionProcessingDAO.deleteDataFromEcsRegistrationData();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void readExcelFileForDebtFromECS() {
        LOGGER.info("Checking Debt files in FTP ");
        String fileName[] = this.debtFileCreatedforToday();
        LOGGER.info("Complted Checking Debt files in FTP :" + fileName);
        List<EcsDebtFileReplyVo> ecsDebtFileReplyList = new ArrayList<EcsDebtFileReplyVo>();
        String line = "";
        if (fileName != null) {
            for (String excelFile : fileName) {
                FileInputStream file = null;
                LOGGER.info("Processing file:" + excelFile);
                try {
                    file = new FileInputStream(new File(excelFile));
                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(file);

                    //Get first sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    boolean first = true;
                    //Iterate through each rows from first sheet
                    Iterator<Row> rowIterator = sheet.iterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        //To skip row header
//                        if (first) {
//                            first = false;
//                            continue;
//                        }
                        EcsDebtFileReplyVo ecsDebtFileReplyVo = new EcsDebtFileReplyVo();
                        //For each row, iterate through each columns
                        List data = new ArrayList();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    data.add(cell.getBooleanCellValue());
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    if (isCellDateFormatted(cell)) {
                                        data.add(cell.getDateCellValue());
                                    } else {
                                        data.add(cell.getNumericCellValue());
                                    }
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    data.add(cell.getStringCellValue());
                                    break;
                            }

                        }
                        String regId = data.size() > ZERO
                                ? data.get(ZERO).toString() : EMPTY_STRING;
                        Date dueDate = data.size() > ONE
                                ? (Date) data.get(ONE) : null;
                        Double dbtAmountFee = data.size()
                                > 2 ? (Double) data.get(2) : 0.0;
                        String status = data.size() > 4
                                ? data.get(4).toString() : EMPTY_STRING;
                        String failureReason = data.size()
                                > 5 ? data.get(5).toString() : EMPTY_STRING;

                        LOGGER.info("##########   Debt file content   ########");
                        LOGGER.log(Level.INFO, "Reg id :[{0}] DueDate :[{1}] Status :[{2}] Failure Reson :[{3}]Debt anount :[{4}]", new Object[]{regId, dueDate, status, failureReason, dbtAmountFee});
                        List<EcsDebtPaymentFileContentTb> debtList = iECSTransactionProcessingDAO.getDebtFileContent(regId, dueDate);
                        for (EcsDebtPaymentFileContentTb debtListTb : debtList) {

                            ecsDebtFileReplyVo.setRegId(data.size() > ZERO
                                    ? data.get(ZERO).toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setDueDate(data.size() > ONE
                                    ? (Date) data.get(ONE) : null);
                            ecsDebtFileReplyVo.setStatus(data.size() > 4
                                    ? data.get(4).toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setFailureReason(data.size()
                                    > 5 ? data.get(5).toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setDebtAmountFee(data.size()
                                    > 2 ? (Double) data.get(2) : 0.0);
                            ecsDebtFileReplyVo.setFeeCalculateDate(debtListTb.getFeeCalculatedate());
                            ecsDebtFileReplyList.add(ecsDebtFileReplyVo);

                        }

                    }
                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                } finally {
                    try {
                        file.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                    }
                }
                LOGGER.log(Level.INFO, "Processing file:{0}", excelFile);
            }
            Date new_Due_Date = DateUtil.getECSPayDate();
            LOGGER.info("Updating table with file:");
            iECSTransactionProcessingDAO.updateEcsDebtFiledata(ecsDebtFileReplyList,
                    new_Due_Date);
            LOGGER.info("Completed updating table with file:");
        }
    }

    private String[] debtFileCreatedforToday() {
        StringBuffer root = new StringBuffer();
        String fileName[] = null;
        String ftpServer = PropertiesLoader.getPropertiesValue(MMF_FILE_TRANSFER_SERVER);
        String ftpFilePath = ftpServer.equalsIgnoreCase(FTP_SERVER)
                ? PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_DEBT_PAYMENT)
                : PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_DEBTPAYMENTFILE);
        root.append(ftpFilePath);
        StringBuffer pattern = new StringBuffer();
//        pattern.append(PAYMENT_FILE).append(PATTERN).append("\\")
//                .append(DOT).append(EXCEL_EXTENSION);
        pattern.append(PAYMENT_FILE).append(PATTERN).append("\\")
                .append(DOT).append(DEBT_FILE_EXTENSION);
        File directory = new File(LookupDataLoader.getResourcePath() + PAYMENT_FILE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
            fileName = FtpFileTranferUtil.listFilesMatching(root.toString(),
                    pattern.toString(), PAYMENT_FILE_DIRECTORY);
        } else {
            fileName = FtpFileTranferUtil.listFilesMatchingInSFTP(root.toString(),
                    pattern.toString(), PAYMENT_FILE_DIRECTORY);
        }

        return fileName;
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

    /**
     * Method Current status : Not Using Method for Reading P1 text files
     * generated by BillDesk (Response for TS File) The file contains only
     * customer Id whose mandates are verified
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void readRegistrationTextFileFromECS() {
        StringBuffer root = new StringBuffer();
        String[] fileName = null;
        String ftpServer = PropertiesLoader.getPropertiesValue(MMF_FILE_TRANSFER_SERVER);
        String ftpFilePath = ftpServer.equalsIgnoreCase(FTP_SERVER)
                ? PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_REGIGISTRATION)
                : PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_REGISTRATION);
        root.append(ftpFilePath);
        BufferedReader br = null;
        StringBuffer pattern = new StringBuffer();
        pattern.append(REGISTRATION_FILE)
                .append(PATTERN)
                .append("\\")
                .append(DOT)
                .append(FILE_EXTENSION);
        File directory = new File(LookupDataLoader.getResourcePath() + REGISTRATION_FILE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
            LOGGER.log(Level.INFO, "Starting transfering  Registration file from Ftp To MMF ");
            fileName = FtpFileTranferUtil.listFilesMatching(root.toString(),
                    pattern.toString(), REGISTRATION_FILE_DIRECTORY);
            LOGGER.log(Level.INFO, "Completed transfering  Registration file from Ftp To MMF  ");
        } else {
            LOGGER.log(Level.INFO, "Starting transfering  Registration file from SFTP To MMF ");
            fileName = FtpFileTranferUtil.listFilesMatchingInSFTP(root.toString(),
                    pattern.toString(), REGISTRATION_FILE_DIRECTORY);
            LOGGER.log(Level.INFO, "Completed transfering  Registration file from SFTP To MMF  ");
        }

        String regNum;
        StringBuffer regnumList = new StringBuffer();
        LOGGER.log(Level.INFO, "Starting processing text file from ECS");
        regnumList.append("IN(");
        if (fileName != null) {
            for (String textFile : fileName) {
                boolean first = true;
                try {
                    LOGGER.log(Level.INFO, "Parsingtext file :" + fileName);
                    StringBuffer regFileCOntent = new StringBuffer();
                    br = new BufferedReader(new FileReader(textFile));
                    while ((regNum = br.readLine()) != null) {
                        regFileCOntent.append(regNum).
                                append(System.getProperty(LINE_SEPERATOR));
                        if (first) {
                            first = false;
                            continue;
                        }
                        regnumList.append("'").append(regNum).append("',");
                    }
                    LOGGER.log(Level.INFO, "Parsing text file completed :");
                    EcsRegistrationFiledataTb ecsRegistrationFile = new EcsRegistrationFiledataTb();
                    ecsRegistrationFile.setEcsSendDate(new Date());
                    ecsRegistrationFile.setFileContent(regFileCOntent.toString());
                    ecsRegistrationFile.setFileName(textFile);
                    LOGGER.log(Level.INFO, "Text file Content is adding to table");
                    iECSTransactionProcessingDAO.insertNewEcsRegistrationFiledata(ecsRegistrationFile);
                    LOGGER.log(Level.INFO, "Completed adding to table");
                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                }
            }
            String reg_numberList = regnumList.toString().substring(0, regnumList.length() - 1) + ")";
            LOGGER.log(Level.INFO, "Updating mandate Status");
            iECSTransactionProcessingDAO.createUserListforMandateEcs(reg_numberList);
            LOGGER.log(Level.INFO, "Completed updating mandate Status");
            LOGGER.log(Level.INFO, "Completed processing text file from ECS");
        } else {
            LOGGER.log(Level.INFO, "No file to process");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void createECSTransmittalSheet() {
        boolean status = false;
        ByteArrayOutputStream bout = null;
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.DATE, -1);
        StringBuffer filename = new StringBuffer();
        Random r = new Random();
        String environment = PropertiesLoader.getPropertiesValue(MMF_ENVIRONMENT_SETUP);
        String ftpFilePath = environment.equalsIgnoreCase(PRODUCTION)
                ? PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_TRANSMITTALSHEET)
                : PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_TRANSMITTALSHEET);
        filename.append(ftpFilePath).append(SINGLE_SLASH)
                .append(DateUtil.dateToString(cal.getTime(), dd_MM_yyyy)).append(DOT)
                .append(TRANSMITTAL_SHEET_EXTENSION);
        LOGGER.info("Creating content for Transmittal Sheet.");
        List<Object[]> responseList = iECSTransactionProcessingDAO.createMandateVerificationList(cal.getTime());
        LOGGER.info("Completed creating Transmittal Sheet.");
        if (responseList != null && !responseList.isEmpty()) {
            try {
                LinkedHashMap<String, Object[]> data = this.convertListToExcelMap(responseList, cal.getTime());
                //Blank workbook
                XSSFWorkbook workbook = new XSSFWorkbook();

                //Create a blank sheet
                XSSFSheet sheet = workbook.createSheet(TRANSMITTAL_SHEET);
                //Iterate over data and write to sheet
                Set<String> keyset = data.keySet();
                int rownum = 0;
                for (String key : keyset) {
                    XSSFRow row = sheet.createRow(rownum++);
                    Object[] objArr = data.get(key);
                    int cellnum = 0;
                    for (Object obj : objArr) {
                        Cell cell = row.createCell(cellnum++);
                        if (obj instanceof String) {
                            cell.setCellValue((String) obj);
                        } else if (obj instanceof Integer) {
                            cell.setCellValue((Integer) obj);
                        } else if (obj instanceof Date) {
                            cell.setCellValue((Date) obj);
                        }
                    }
                }
                bout = new ByteArrayOutputStream();
                workbook.write(bout);
                InputStream is = new ByteArrayInputStream(bout.toByteArray());
                if (environment.equalsIgnoreCase(PRODUCTION)) {
                    status = FtpFileTranferUtil.writeExcelToSFTP(is, filename.toString());
                } else {
                    status = FtpFileTranferUtil.writeExcelToFTP(is, filename.toString());
                }
                bout.flush();
                bout.close();
                if (status) {
                    this.saveContenttoTb(data);
                }
            } catch (Exception ex) {
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            }

        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private LinkedHashMap<String, Object[]> convertListToExcelMap(List<Object[]> responseList, Date date) {
        LinkedHashMap<String, Object[]> data = new LinkedHashMap<String, Object[]>();
        Integer serialNumber = 0;
        String LotNumber = DateUtil.dateToString(date, DDMMYYYY);
        //Excel sheet header
        data.put(HEADER, new Object[]{SR_NO, LOT_NO, DEBIT_TYPE, AMOUNT, FREQUENCY_HEADER, START_DATE,
            END_DATE, AC_HOLDER_NAME, CUSTOMER_ID, A_TWO, BANK_NAME, AC_TYPE, AC_NO, MICR, IFSC});
        for (Object[] item : responseList) {
            serialNumber++;
            data.put(item[1].toString(), new Object[]{serialNumber, LotNumber, item[15].toString(), item[13].toString(), item[14].toString(), DateUtil.dateToString((Date) item[21], dd_MM_yyyy),
                UNTIL_CANCELED, item[25].toString(), item[1].toString(), EMPTY_STRING, item[9].toString(), item[7].toString(), item[8].toString(), item[24].toString(), item[11].toString()});
        }
        return data;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private void saveContenttoTb(Map<String, Object[]> data) {
        List<EcsTransmittalSheetTb> sheetTbs = new ArrayList<EcsTransmittalSheetTb>();
        for (String key : data.keySet()) {
            if (!key.equalsIgnoreCase(HEADER)) {
                EcsTransmittalSheetTb transmittalSheetTb = new EcsTransmittalSheetTb();
                Object[] objArr = data.get(key);
                transmittalSheetTb.setRegistrationId(key);
                transmittalSheetTb.setSerialNo(objArr[0].toString());
                transmittalSheetTb.setLotno(objArr[1].toString());
                transmittalSheetTb.setDebitType(objArr[2].toString());
                transmittalSheetTb.setAmount(objArr[3].toString());
                transmittalSheetTb.setFrequency(objArr[4].toString());
                transmittalSheetTb.setStartDate(objArr[5].toString());
                transmittalSheetTb.setEndDate(objArr[6].toString());
                transmittalSheetTb.setAcHolderName(objArr[7].toString());
                transmittalSheetTb.setBankName(objArr[10].toString());
                transmittalSheetTb.setAcType(objArr[11].toString());
                transmittalSheetTb.setAcno(objArr[12].toString());
                transmittalSheetTb.setMicr(objArr[13].toString());
                transmittalSheetTb.setIfsc(objArr[14].toString());
                transmittalSheetTb.setEcsSend(Boolean.TRUE);
                transmittalSheetTb.setVerified(Boolean.FALSE);
                transmittalSheetTb.setRejected(Boolean.FALSE);
                sheetTbs.add(transmittalSheetTb);
            }

        }
        LOGGER.info("Saving content to table ecs_transmittal_sheet_tb.");
        iECSTransactionProcessingDAO.saveTransmittalData(sheetTbs);
        LOGGER.log(Level.INFO, "Content saved .Total rows affected : {0}", sheetTbs.size());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void processApprovedMandates() {
        StringBuilder root = new StringBuilder();
        String[] fileName = null;
        String ftpServer = PropertiesLoader.getPropertiesValue(MMF_FILE_TRANSFER_SERVER);
        String ftpFilePath = ftpServer.equalsIgnoreCase(FTP_SERVER)
                ? PropertiesLoader.getPropertiesValue(FTP_FILE_PATH_TRANSMITTALSHEET_BILLDESK)
                : PropertiesLoader.getPropertiesValue(SFTP_FILE_PATH_TRANSMITTALSHEET_BILLDESK);
        root.append(ftpFilePath);
        BufferedReader br = null;
        StringBuilder pattern = new StringBuilder();
        pattern.append(TS_RESPONSE_FILE_PATTERN).append("\\")
                .append(DOT).append(TRANSMITTAL_SHEET_EXTENSION);
        File directory = new File(LookupDataLoader.getResourcePath() + TS_FILE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (ftpServer.equalsIgnoreCase(FTP_SERVER)) {
            LOGGER.log(Level.INFO, "Starting transfering  Registration file from Ftp To MMF ");
            fileName = FtpFileTranferUtil.listFilesMatching(root.toString(),
                    pattern.toString(), TS_FILE_DIRECTORY);
            LOGGER.log(Level.INFO, "Completed transfering  Registration file from Ftp To MMF  ");
        } else {
            LOGGER.log(Level.INFO, "Starting transfering  Registration file from SFTP To MMF ");
            fileName = FtpFileTranferUtil.listFilesMatchingInSFTP(root.toString(),
                    pattern.toString(), TS_FILE_DIRECTORY);
            LOGGER.log(Level.INFO, "Completed transfering  Registration file from SFTP To MMF  ");
        }
        StringBuffer regnumList = new StringBuffer();
        LOGGER.log(Level.INFO, "Starting processing file from ECS");
        regnumList.append("IN(");
        StringBuffer regnumRemarkList = new StringBuffer();
        if (fileName != null) {
            for (String excelFile : fileName) {
                FileInputStream file = null;
                LOGGER.log(Level.INFO, "Processing file:{0}", excelFile);
                try {
                    file = new FileInputStream(new File(excelFile));
                    //Create Workbook instance holding reference to .xlsx file
                    XSSFWorkbook workbook = new XSSFWorkbook(file);

                    //Get first sheet from the workbook
                    XSSFSheet sheet = workbook.getSheetAt(0);
                    boolean first = true;
                    //Iterate through each rows from first sheet
                    Iterator<Row> rowIterator = sheet.iterator();
                    while (rowIterator.hasNext()) {
                        Row row = rowIterator.next();
                        if (first) {
                            first = false;
                            continue;
                        }

                        //For each row, iterate through each columns
                        List data = new ArrayList();
                        Iterator<Cell> cellIterator = row.cellIterator();
                        while (cellIterator.hasNext()) {
                            Cell cell = cellIterator.next();
                            switch (cell.getCellType()) {
                                case Cell.CELL_TYPE_BOOLEAN:
                                    data.add(cell.getBooleanCellValue());
                                    break;
                                case Cell.CELL_TYPE_NUMERIC:
                                    data.add(cell.getNumericCellValue());
                                    break;
                                case Cell.CELL_TYPE_STRING:
                                    data.add(cell.getStringCellValue());
                                    break;
                                case Cell.CELL_TYPE_BLANK:
                                    data.add(EMPTY_STRING);
                                    break;
                            }

                        }
                        String regId = data.size() > 8
                                ? data.get(8).toString() : EMPTY_STRING;
                        String mandateStatus = data.size() > 15
                                ? data.get(15).toString() : EMPTY_STRING;
                        String remark = data.size() > 16
                                ? data.get(16).toString() : EMPTY_STRING;
                        if (mandateStatus.equalsIgnoreCase((EnumMandate.VERIFIED).toString())) {
                            regnumList.append("'").append(regId).append("',");
                        } else {
                            regnumRemarkList.append(regId).append(COLON).append(remark.trim()).append(COMA);
                        }
                    }
                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                } catch (IOException ex) {
                    LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
                } finally {
                    try {
                        file.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                    }
                }
            }
            if (regnumList.length() > 3) {
                String reg_numberList = regnumList.toString().substring(0, regnumList.length() - 1) + ")";
                LOGGER.log(Level.INFO, "Updating mandate Status");
                iECSTransactionProcessingDAO.createUserListforMandateEcs(reg_numberList);
                LOGGER.log(Level.INFO, "Completed updating mandate Status");
            }

            if (regnumRemarkList.toString().length() > 0) {
                LOGGER.log(Level.INFO, "Updating remarks for mandate rejected customers");
                iECSTransactionProcessingDAO.updateTransmittalSheetTableContent(regnumRemarkList.toString());
                LOGGER.log(Level.INFO, "Completed updating remarks");
            }
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    public void readCsvFileForDebitPaymentResponseFromECS() {
        LOGGER.info("Checking Debt files in FTP ");
        String fileName[] = this.debtFileCreatedforToday();
        LOGGER.log(Level.INFO, "Complted Checking Debt files in FTP :{0}", fileName);
        List<EcsDebtFileReplyVo> ecsDebtFileReplyList = new ArrayList<EcsDebtFileReplyVo>();
        List<EcsPaymentStatusFileTb> ecsPaymentStatusFileTbs = new ArrayList<EcsPaymentStatusFileTb>();
        if (fileName != null) {
            BufferedReader dataBuffReader = null;
            String row = null;
            for (String csvFile : fileName) {
                FileInputStream file = null;
                LOGGER.log(Level.INFO, "Processing file:{0}", csvFile);
                try {
                    file = new FileInputStream(new File(csvFile));
                    dataBuffReader = new BufferedReader(new InputStreamReader(file));
                    while ((row = dataBuffReader.readLine()) != null) {
                        EcsDebtFileReplyVo ecsDebtFileReplyVo = new EcsDebtFileReplyVo();
                        Object data[] = null;
                        data = row.split(COMA, TO_GET_EMPTY_DATA);
                        String regId = data.length > ZERO
                                ? data[ZERO].toString() : EMPTY_STRING;
                        Date dueDate = data.length > ONE
                                ? DateUtil.stringToDate(data[ONE].toString(), DD_SLASH_MM_SLASH_YYYY) : null;
                        Double dbtAmountFee = data.length
                                > 2 ? Double.parseDouble(data[2].toString()) : 0.0;
                        String status = data.length > 4
                                ? data[4].toString() : EMPTY_STRING;
                        String failureReason = data.length
                                > 5 ? data[5].toString() : EMPTY_STRING;

                        LOGGER.info("##########   Debt file content   ########");
                        ecsPaymentStatusFileTbs.add(convertToEcsPaymentStatusFileTb(data));
                        LOGGER.log(Level.INFO, "Reg id :[{0}] DueDate :[{1}] Status :[{2}] Failure Reson :[{3}]Debt anount :[{4}]", new Object[]{regId, dueDate, status, failureReason, dbtAmountFee});
                        List<EcsDebtPaymentFileContentTb> debtList = iECSTransactionProcessingDAO.getDebtFileContent(regId, dueDate);
                        for (EcsDebtPaymentFileContentTb debtListTb : debtList) {

                            ecsDebtFileReplyVo.setRegId(data.length > ZERO
                                    ? data[ZERO].toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setDueDate(data.length > ONE
                                    ? DateUtil.stringToDate(data[ONE].toString(), DD_SLASH_MM_SLASH_YYYY) : null);
                            ecsDebtFileReplyVo.setStatus(data.length > 4
                                    ? data[4].toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setFailureReason(data.length
                                    > 5 ? data[5].toString() : EMPTY_STRING);
                            ecsDebtFileReplyVo.setDebtAmountFee(data.length
                                    > 2 ? Double.parseDouble(data[2].toString()) : 0.0);
                            ecsDebtFileReplyVo.setFeeCalculateDate(debtListTb.getFeeCalculatedate());
                            ecsDebtFileReplyList.add(ecsDebtFileReplyVo);

                        }
                    }

                } catch (FileNotFoundException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                } catch (IOException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                } catch (ParseException ex) {
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                } finally {
                    try {
                        dataBuffReader.close();
                        file.close();
                    } catch (IOException ex) {
                        LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                    }
                }
                LOGGER.log(Level.INFO, "Processing file:{0}", csvFile);
            }

            Date new_Due_Date = DateUtil.getECSPayDate();
            LOGGER.info("Updating table with file:");
            iECSTransactionProcessingDAO.updateEcsDebtFiledata(ecsDebtFileReplyList, new_Due_Date);
            LOGGER.info("Completed updating table with file:");

            if (ecsPaymentStatusFileTbs != null) {
                LOGGER.info("Adding file content to table ecsPaymentStatusFileTbs");
                iECSTransactionProcessingDAO.saveEcsPaymentStatusFileTbs(ecsPaymentStatusFileTbs);
                LOGGER.info("Completed adding file content..");
            }
        }
    }

    private EcsPaymentStatusFileTb convertToEcsPaymentStatusFileTb(Object[] data) {
        EcsPaymentStatusFileTb paymentStatusFileTb = new EcsPaymentStatusFileTb();
        try {
            if (data != null) {
                paymentStatusFileTb.setRegId(data.length > ZERO
                        ? data[ZERO].toString() : EMPTY_STRING);
                paymentStatusFileTb.setDueDate(data.length > ONE
                        ? DateUtil.stringToDate(data[ONE].toString(), DD_SLASH_MM_SLASH_YYYY) : null);
                paymentStatusFileTb.setDebtAmountPaid(data.length > 2
                        ? Float.parseFloat(data[2].toString()) : ZERO);
                paymentStatusFileTb.setDebitRequestNmbr(data.length > 3
                        ? Integer.parseInt(data[3].toString()) : ZERO);
                paymentStatusFileTb.setStatus(data.length > 4
                        ? data[4].toString() : EMPTY_STRING);
                paymentStatusFileTb.setFailureReason(data.length
                        > 5 ? data[5].toString() : EMPTY_STRING);
            }

        } catch (ParseException ex) {
            LOGGER.log(Level.SEVERE, "Data convertion To EcsPaymentStatusFileTb failed", ex);
        }
        return paymentStatusFileTb;
    }
}
