/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.gtl.mmf.controller;

import com.gtl.mmf.bao.IUploadBenchmarkBAO;
import com.gtl.mmf.entity.BankTb;
import com.gtl.mmf.entity.IfcMicrMappingTb;
import com.gtl.mmf.service.util.CSVParser;
import static com.gtl.mmf.service.util.IConstants.COMA;
import static com.gtl.mmf.service.util.IConstants.EMPTY_STRING;
import static com.gtl.mmf.service.util.IConstants.EQ_SIGN;
import static com.gtl.mmf.service.util.IConstants.ONE;
import static com.gtl.mmf.service.util.IConstants.ZERO;
import com.gtl.mmf.service.util.PropertiesLoader;
import com.gtl.mmf.service.vo.BankVo;
import com.gtl.mmf.util.StackTraceWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.Part;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

/**
 *
 * @author trainee3
 */
@Named("uploadbenchmarkcontroller")
@Scope("view")
public class Uploadbenchmarkcontroller {

    private Part filepart;
    private static final Logger LOGGER = Logger.getLogger("com.gtl.mmf.controller.uploadbenchmarkcontroller");
    private String filename;
    private List<String> uploadfile;
    private String status;
    private boolean flag;
    @Autowired
    private IUploadBenchmarkBAO uploadBenchmarkBAO;

    public void uploadBenchmark() {

        try {
            String file = extractFileName(filepart);
            flag = false;
            String name = file.split("\\.")[ZERO];
            if (!filename.equalsIgnoreCase(name)) {
                String fileIdentifier[] = filename.split("_");
                FacesContext.getCurrentInstance().addMessage(
                        "upload_benchmark:filename",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "File Name for " + fileIdentifier[ONE] + " " + fileIdentifier[ZERO] + " should be Match with " + filename + ".csv",
                                null));
                return;
            }
            if (filepart.getSize() != ZERO
                    && (file.endsWith(".csv") || file.endsWith(".CSV"))) {
                InputStream inputStream = filepart.getInputStream();
                flag = CSVParser.parseAndPersistCSVData(inputStream, filename);

            } else {
                FacesContext.getCurrentInstance().addMessage(
                        "upload_benchmark:upload_btn",
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Please Select A .CSV File", null));
            }
            if (flag) {
                status = filename + " Uploaded Successfully";
                filename = EMPTY_STRING;
            } else {
                status = "Upload Failed";
            }
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        }
    }

    public Part getFilepart() {
        return filepart;
    }

    public void setFilepart(Part filepart) {
        this.filepart = filepart;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public List<String> getUploadfile() {
        uploadfile = new ArrayList<String>();
        String[] uploadFiles = PropertiesLoader.getPropertiesValue("uploadfiles").split(COMA);
        for (String fileName : uploadFiles) {
            uploadfile.add(fileName);
        }
        return uploadfile;
    }

    public void setUploadfile(List<String> uploadfile) {
        this.uploadfile = uploadfile;
    }

    private String extractFileName(Part part) {
        if (part != null) {
            String contentDisp = part.getHeader("content-disposition");
            String[] items = contentDisp.split(";");
            for (String s : items) {
                if (s.trim().startsWith("filename")) {
                    return s.substring(s.indexOf(EQ_SIGN) + 2, s.length() - ONE);
                }
            }
        }
        return EMPTY_STRING;
    }

    public void uploadBankDetails(String fileType) {
        InputStream is = null;
        try {
            flag = false;
            List<BankTb> bankTbList = new ArrayList<BankTb>();
            List<IfcMicrMappingTb> micrMappingTb = new ArrayList<IfcMicrMappingTb>();
            String excelFile = extractFileName(filepart);
            is = filepart.getInputStream();
            LOGGER.log(Level.INFO, "Processing Excel file:{0}", excelFile);
            try {
                //Create Workbook instance holding reference to .xlsx file
                XSSFWorkbook workbook = new XSSFWorkbook(is);

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
                    BankVo bnkvo = new BankVo();
                    IfcMicrMappingTb ifcMicrMappingTb = new IfcMicrMappingTb();
                    BankTb bankTb = new BankTb();
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
                    if (fileType.equalsIgnoreCase("FILE_BANK_LIST")) {
                        bnkvo.setBank(data.size() > ZERO
                                ? data.get(ZERO).toString().trim() : EMPTY_STRING);
                        bnkvo.setIfsc(data.size() > 1
                                ? data.get(1).toString().trim() : EMPTY_STRING);
                        bnkvo.setBranch(data.size() > 2
                                ? data.get(2).toString().trim() : EMPTY_STRING);
                        bnkvo.setAddress(data.size() > 3
                                ? data.get(3).toString().trim() : EMPTY_STRING);
                        bnkvo.setContact(data.size() > 4
                                ? EMPTY_STRING : EMPTY_STRING);
                        bnkvo.setCity(data.size() > 5
                                ? data.get(5).toString().trim() : EMPTY_STRING);
                        bnkvo.setDistrict(data.size() > 6
                                ? data.get(6).toString().trim() : EMPTY_STRING);
                        bnkvo.setState(data.size() > 7
                                ? data.get(7).toString().trim() : EMPTY_STRING);
//                int size = data.get(3).toString().length();
//                String pin = data.size() > 3 ?(String) data.get(3).toString().subSequence(size-6, size): EMPTY_STRING;
//                bnkvo.setPincode(EMPTY_STRING);
                        bankTb.setBank(bnkvo.getBank());
                        bankTb.setIfsc(bnkvo.getIfsc());
                        bankTb.setBranch(bnkvo.getBranch());
                        bankTb.setAddress(bnkvo.getAddress());
                        bankTb.setContact(bnkvo.getContact());
                        bankTb.setCity(bnkvo.getCity());
                        bankTb.setDistrict(bnkvo.getDistrict());
                        bankTb.setState(bnkvo.getState());
                        bankTbList.add(bankTb);
                    } else {
                        StringBuffer micrcombined = new StringBuffer();
                        micrcombined.append(data.get(0).toString().trim()).append(data.get(1).toString().trim()).append(data.get(2).toString().trim());
                        ifcMicrMappingTb.setIfsc(data.size() > 4 ? EMPTY_STRING  : data.get(1).toString().trim());
                        //ifcMicrMappingTb.setMicr(micrcombined.toString());
                        ifcMicrMappingTb.setMicr(data.size() > 4 ? EMPTY_STRING : data.get(2).toString().trim());
                        micrMappingTb.add(ifcMicrMappingTb);
                    }

                }
            } catch (FileNotFoundException ex) {
                flag = false;
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            } catch (IOException ex) {
                flag = false;
                LOGGER.log(Level.WARNING, StackTraceWriter.getStackTrace(ex));
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                    flag = false;
                    LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
                }
            }
            LOGGER.log(Level.INFO, "Processing file:{0}", excelFile);
            if (fileType.equalsIgnoreCase("FILE_BANK_LIST")) {
                LOGGER.info("Updating table with bank details file");
                uploadBenchmarkBAO.saveBankTbList(bankTbList);
                LOGGER.info("Completed updating table");
                flag = true;
            } else {
                LOGGER.info("Updating table with IFSC and MICR mapping");
                uploadBenchmarkBAO.saveIfscMappingDetails(micrMappingTb);
                LOGGER.info("Completed updating table");
                flag = true;
            }
            if (flag) {
                status = fileType + " -:" + excelFile + " Uploaded Successfully";
                filename = EMPTY_STRING;
            } else {
                status = "Upload Failed";
            }
        } catch (IOException ex) {
            flag = false;
            LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                flag = false;
                LOGGER.log(Level.SEVERE, StackTraceWriter.getStackTrace(ex));
            }
        }

    }

    public void uploadExactFile(String fileType) {
       /* if (fileType.equalsIgnoreCase("FILE_BANK_LIST") || fileType.equalsIgnoreCase("IFSC_MICR_MAPPING")) {
            uploadBankDetails(fileType);
        } else {*/
            uploadBenchmark();
        //}
    }
}
